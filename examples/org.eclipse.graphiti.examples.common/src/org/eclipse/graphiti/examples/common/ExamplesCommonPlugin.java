/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    SAP AG - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.graphiti.examples.common;

import java.net.URL;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.graphiti.examples.common.outline.ContentOutlinePageAdapterFactory;
import org.eclipse.graphiti.examples.common.property.PropertySourceAdapterFactory;
import org.eclipse.graphiti.examples.common.util.uiprovider.IUIProvider;
import org.eclipse.graphiti.examples.common.util.uiprovider.UIProvider;
import org.eclipse.graphiti.ui.internal.config.IConfigurationProviderHolder;
import org.eclipse.graphiti.ui.internal.editor.DiagramEditorInternal;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class ExamplesCommonPlugin extends AbstractUIPlugin {

	private static ExamplesCommonPlugin _plugin;

	private UIProvider _uiProvider;

	/**
	 * Creates the Plugin and caches its default instance.
	 */
	public ExamplesCommonPlugin() {
		_plugin = this;
	}

	// ============ overwritten methods of AbstractUIPlugin ====================

	/**
	 * This method is called upon plug-in activation.
	 * 
	 * @param context
	 *            the context
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);

		_uiProvider = new UIProvider();

		IAdapterManager manager = Platform.getAdapterManager();
		manager.registerAdapters(new PropertySourceAdapterFactory(), IConfigurationProviderHolder.class);
		manager.registerAdapters(new ContentOutlinePageAdapterFactory(), DiagramEditorInternal.class);
	}

	/**
	 * This method is called when the plug-in is stopped.
	 * 
	 * @param context
	 *            the context
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		if (_uiProvider != null)
			_uiProvider.dispose();

		super.stop(context);
	}

	// ======================== static access methods ==========================

	/**
	 * Gets the default-instance of this plugin. Actually the default-instance
	 * should always be the only instance -> Singleton.
	 * 
	 * @return the default
	 */
	public static ExamplesCommonPlugin getDefault() {
		return _plugin;
	}

	// =========================== public helper methods ======================

	/**
	 * Returns the current Workspace.
	 * 
	 * @return The current Workspace.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * Returns the URL, which points to where this Plugin is installed.
	 * 
	 * @return The URL, which points to where this Plugin is installed.
	 */
	public static URL getInstallURL() {
		return getDefault().getBundle().getEntry("/");
	}

	/**
	 * Returns the Plugin-ID.
	 * 
	 * @return The Plugin-ID.
	 */
	public static String getID() {
		return getDefault().getBundle().getSymbolicName();
	}

	/**
	 * Returns the currently active WorkbenchPage.
	 * 
	 * @return The currently active WorkbenchPage.
	 */
	public static IWorkbenchPage getActivePage() {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbenchWindow != null)
			return workbenchWindow.getActivePage();
		return null;
	}

	/**
	 * Returns the currently active Shell.
	 * 
	 * @return The currently active Shell.
	 */
	public static Shell getShell() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	/**
	 * Returns the IUIProvider.
	 * 
	 * @return The IUIProvider.
	 */
	public IUIProvider getUIProvider() {
		return _uiProvider;
	}
}
