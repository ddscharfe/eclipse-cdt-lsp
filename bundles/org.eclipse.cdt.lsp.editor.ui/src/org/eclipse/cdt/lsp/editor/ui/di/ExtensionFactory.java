package org.eclipse.cdt.lsp.editor.ui.di;

import org.eclipse.cdt.lsp.di.AbstractGuiceAwareExecutableExtensionFactory;
import org.eclipse.cdt.lsp.editor.ui.LspEditorUiPlugin;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

public class ExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
	@Override
	protected Bundle getBundle() {
		return LspEditorUiPlugin.getDefault().getBundle();
	}

	@Override
	protected Injector getInjector() {
		return LspEditorUiPlugin.getInjector();
	}
}
