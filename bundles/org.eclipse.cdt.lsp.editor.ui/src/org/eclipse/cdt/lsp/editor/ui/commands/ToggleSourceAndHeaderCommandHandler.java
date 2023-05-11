/*******************************************************************************
 * Copyright (c) 2023 COSEDA Technologies GmbH and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Dominic Scharfe (COSEDA Technologies GmbH) - initial implementation
 *******************************************************************************/
package org.eclipse.cdt.lsp.editor.ui.commands;

import java.net.URI;
import java.util.Optional;

import org.eclipse.cdt.lsp.LspPlugin;
import org.eclipse.cdt.lsp.editor.ui.LspEditorUiPlugin;
import org.eclipse.cdt.lsp.editor.ui.util.UriProvider;
import org.eclipse.cdt.lsp.services.ClangdLanguageServer;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.text.IDocument;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.texteditor.ITextEditor;

import com.google.inject.Inject;

public class ToggleSourceAndHeaderCommandHandler extends AbstractHandler implements IHandler {

	private final UriProvider uriProvider;

	@Inject
	public ToggleSourceAndHeaderCommandHandler(UriProvider uriProvider) {
		this.uriProvider = uriProvider;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		return execute(HandlerUtil.getActiveEditor(event));
	}

	@SuppressWarnings("restriction")
	private Object execute(IEditorPart activeEditor) {
		// Try to adapt to ITextEditor (e.g. to support editors embedded in
		// MultiPageEditorParts), otherwise use activeEditor.
		IEditorPart innerEditor = Optional.ofNullable((IEditorPart) Adapters.adapt(activeEditor, ITextEditor.class))
				.orElse(activeEditor);

		uriProvider.getUri(innerEditor).ifPresent(fileUri -> {
			IDocument document = org.eclipse.lsp4e.LSPEclipseUtils.getDocument(innerEditor.getEditorInput());
			org.eclipse.lsp4e.LanguageServers.forDocument(document)
					.computeFirst(
							server -> server instanceof ClangdLanguageServer
									? ((ClangdLanguageServer) server)
											.switchSourceHeader(new TextDocumentIdentifier(fileUri.toString()))
									: null)
					.thenAccept(otherFileUri -> otherFileUri
							.ifPresent(uri -> openEditor(innerEditor.getEditorSite().getPage(), URI.create(uri))));
		});

		return null;
	}

	private static void openEditor(IWorkbenchPage page, URI fileUri) {
		page.getWorkbenchWindow().getShell().getDisplay().asyncExec(() -> {
			try {
				IDE.openEditor(page, fileUri, LspPlugin.LSP_C_EDITOR_ID, true);
			} catch (PartInitException e) {
				StatusManager.getManager().handle(e, LspEditorUiPlugin.PLUGIN_ID);
			}
		});
	}
}