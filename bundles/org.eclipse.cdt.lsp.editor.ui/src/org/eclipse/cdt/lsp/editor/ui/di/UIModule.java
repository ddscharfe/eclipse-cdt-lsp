package org.eclipse.cdt.lsp.editor.ui.di;

import org.eclipse.cdt.lsp.editor.ui.util.EditorUtil;
import org.eclipse.cdt.lsp.editor.ui.util.UriProvider;

import com.google.inject.AbstractModule;

public class UIModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UriProvider.class).to(EditorUtil.class);
	}

}
