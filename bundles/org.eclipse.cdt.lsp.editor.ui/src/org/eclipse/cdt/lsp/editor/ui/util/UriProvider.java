package org.eclipse.cdt.lsp.editor.ui.util;

import java.net.URI;
import java.util.Optional;

import org.eclipse.ui.IEditorPart;

public interface UriProvider {
	/**
	 * Returns the URI of the given editor depending on the type of its
	 * {@link IEditorPart#getEditorInput()}.
	 * 
	 * @return the URI or an empty {@link Optional} if the URI couldn't be
	 *         determined.
	 */
	Optional<URI> getUri(IEditorPart editor);

}
