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
package org.eclipse.graphiti.testtool.ecore.features.clazz;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

/**
 * The Class TestUpdateClassFeature.
 */
public class TestUpdateClassFeature extends AbstractUpdateFeature {

	/**
	 * The Constructor.
	 * 
	 * @param fp
	 *            the fp
	 */
	public TestUpdateClassFeature(IFeatureProvider fp) {
		super(fp);
	}

	public boolean canUpdate(IUpdateContext context) {
		// return true, if linked business object is a Class
		Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
		return (bo instanceof EClass);
	}

	public IReason updateNeeded(IUpdateContext context) {
		// two strings that have to be retrieved and compared
		String pictogramName = null;
		String businessName = null;

		// Retrieve value from pictogram model
		org.eclipse.graphiti.mm.pictograms.PictogramElement pictogramElement = context.getPictogramElement();
		if (pictogramElement instanceof ContainerShape) {
			ContainerShape cs = (ContainerShape) pictogramElement;
			Collection<Shape> children = cs.getChildren();
			for (Shape shape : children) {
				if (shape.getGraphicsAlgorithm() instanceof Text) {
					Text text = (Text) shape.getGraphicsAlgorithm();
					pictogramName = text.getValue();
				}
			}
		}

		// Retrieve value from business model
		Object bo = getBusinessObjectForPictogramElement(pictogramElement);
		if (bo instanceof EClass) {
			EClass eClass = (EClass) bo;
			businessName = eClass.getName();
		}

		// compare values
		boolean needed = (pictogramName == null || !pictogramName.equals(businessName));
		if (needed) {
			return Reason.createTrueReason("Name is out of date");
		} else {
			return Reason.createFalseReason();
		}
	}

	public boolean update(IUpdateContext context) {

		String businessName = null;

		// Retrieve value from business model
		PictogramElement pictogramElement = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pictogramElement);
		if (bo instanceof EClass) {
			EClass c = (EClass) bo;
			businessName = c.getName();
		}

		// Set value in pictogram model
		if (pictogramElement instanceof ContainerShape) {
			ContainerShape cs = (ContainerShape) pictogramElement;

			Collection<Shape> children = cs.getChildren();
			for (Shape shape : children) {
				if (shape.getGraphicsAlgorithm() instanceof Text) {
					Text text = (Text) shape.getGraphicsAlgorithm();
					text.setValue(businessName);
					return true;
				}
			}
		}

		return false;
	}

}
