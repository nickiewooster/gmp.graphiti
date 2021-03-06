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
 *    Patch 185019 from Bug 332360 contributed by Volker Wegert
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.graphiti.features;

/**
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients,
 *              extend {@link DefaultResizeConfiguration} instead.
 */
public interface IResizeConfiguration {
	/**
	 * Defines resize behavior
	 * 
	 * @return true if element can be resized in vertical direction
	 */
	boolean isVerticalResizeAllowed();

	/**
	 * Defines resize behavior
	 * 
	 * @return true if element can be resized in horizontal direction
	 */
	boolean isHorizontalResizeAllowed();
}
