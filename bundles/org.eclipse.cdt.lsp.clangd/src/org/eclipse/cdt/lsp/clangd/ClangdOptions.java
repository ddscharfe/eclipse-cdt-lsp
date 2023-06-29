/*******************************************************************************
 * Copyright (c) 2023 ArSysOp.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Alexander Fedorov (ArSysOp) - Initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.lsp.clangd;

import java.util.List;

import org.eclipse.lsp4e.server.ProcessStreamConnectionProvider;

/**
 * Options to configure clangd
 *
 */
public interface ClangdOptions {

	/**
	 * Prefer to use clangd language server and related editor implementation
	 *
	 * @return if clangd language server should be preferred
	 */
	boolean preferClangd();

	/**
	 * Path to clangd executable to be launched, must not return <code>null</code>
	 *
	 * @return path to clangd
	 */
	String clangdPath();

	/**
	 * Use clang-tidy diagnostics
	 *
	 * @return if clang-tidy diagnostics is enabled
	 */
	boolean useTidy();

	/**
	 * Index project code in the background and persist index on disk
	 *
	 * @return if background index is enabled
	 */
	boolean useBackgroundIndex();

	/**
	 * Granularity of code completion suggestions either <code>detailed</code> or <code>bundled</code>, must not return <code>null</code>
	 *
	 * @return granularity of code completion suggestions
	 */
	String completionStyle();

	/**
	 * Pretty-print JSON output
	 *
	 * @return if pretty-print for JSON output is enabled
	 */
	boolean prettyPrint();

	/**
	 * Comma separated list of globs for white-listing gcc-compatible drivers that are safe to execute, must not return <code>null</code>
	 *
	 * @return comma separated list of globs
	 */
	String queryDriver();

	/**
	 * List of custom options for clangd.
	 *
	 * @return list of custom options
	 */
	List<String> customOptions();

	/**
	 * Provides list of commands suitable for {@link ProcessStreamConnectionProvider}
	 *
	 * @return list of commands
	 */
	List<String> toList();

}
