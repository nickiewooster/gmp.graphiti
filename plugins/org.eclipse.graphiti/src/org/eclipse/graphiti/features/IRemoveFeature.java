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
 *    Volker Wegert - Bug 336828: patterns should support delete,
 *                    remove, direct editing and conditional palette
 *                    creation entry
 *
 * </copyright>
 *
 *******************************************************************************/
/*
 * Created on 06.07.2005
 */
package org.eclipse.graphiti.features;

import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;
import org.eclipse.graphiti.func.IRemove;

/**
 * This feature is for removing pictogram elements from a diagram. Remove
 * features must not modify any business content.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients,
 *              extend {@link DefaultRemoveFeature} instead.
 */
public interface IRemoveFeature extends IFeature, IRemove {
}