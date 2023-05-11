package org.eclipse.cdt.lsp.editor.ui.util;

import java.net.URI;
import java.util.Optional;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IURIEditorInput;

public class EditorUtil implements UriProvider {

	@Override
	public Optional<URI> getUri(IEditorPart editor) {
		IEditorInput editorInput = editor.getEditorInput();

		if (editorInput instanceof IFileEditorInput) {
			return Optional.of(((IFileEditorInput) editor.getEditorInput()).getFile().getLocationURI());
		} else if (editorInput instanceof IURIEditorInput) {
			return Optional.of(((IURIEditorInput) editorInput).getURI());
		} else {
			return Optional.empty();
		}
	}

}
