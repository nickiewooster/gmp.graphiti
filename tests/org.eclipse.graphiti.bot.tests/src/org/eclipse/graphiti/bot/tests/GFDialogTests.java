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
package org.eclipse.graphiti.bot.tests;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.asyncExec;

import org.eclipse.graphiti.bot.tests.util.ITestConstants;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IPrintFeature;
import org.eclipse.graphiti.ui.internal.Messages;
import org.eclipse.graphiti.ui.internal.action.PrintGraphicalViewerAction;
import org.eclipse.graphiti.ui.internal.editor.DiagramEditorInternal;
import org.eclipse.graphiti.ui.internal.services.GraphitiUiInternal;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Test;

public class GFDialogTests extends AbstractGFTests {

	/**
	 * 
	 */
	private static final int TIMEOUT = 5000;

	public GFDialogTests() {
		super();
	}

	// FIXME
	@Test
	public void testPrintDialog() throws Exception {
		// check if default printer is configured, otherwise SWT throws a
		// "no more handles" error in Printer.checkNull(..)
		if (Printer.getDefaultPrinterData() != null) {

			final DiagramEditorInternal diagramEditor = openDiagram(ITestConstants.DIAGRAM_TYPE_ID_SKETCH);
			asyncExec(new VoidResult() {
				@Override
				public void run() {
					IDiagramTypeProvider dtp = diagramEditor.getDiagramTypeProvider();
					IFeatureProvider fp = dtp.getFeatureProvider();
					IPrintFeature pf = fp.getPrintFeature();
					IAction createPrintGraphicalViewerAction = new PrintGraphicalViewerAction(diagramEditor.getConfigurationProvider(),
							diagramEditor, pf);
					createPrintGraphicalViewerAction.run();
				}
			});

			bot.waitUntil(Conditions.shellIsActive(Messages.PrintFigureDialog_3_xfld), TIMEOUT);
			SWTBotShell shell = bot.shell(Messages.PrintFigureDialog_3_xfld);
			Thread.sleep(2000);
			shell.bot().button("Cancel").click();
			Thread.sleep(300);
			bot.waitUntil(Conditions.shellCloses(shell), TIMEOUT);
			closeEditor(diagramEditor);

		} else {
			System.out.println("!-> GFDialogTests.testPrintDialog():  No default printer configured. Skip test.");
		}
	}

	@Test
	public void testSaveDialog() throws Exception {
		final DiagramEditorInternal diagramEditor = openDiagram(ITestConstants.DIAGRAM_TYPE_ID_SKETCH);

		asyncExec(new VoidResult() {
			@Override
			public void run() {
				GraphitiUiInternal.getUiService().startSaveAsImageDialog(diagramEditor.getGraphicalViewer());
			}
		});

		bot.waitUntil(Conditions.shellIsActive(Messages.AbstractFigureSelectionDialog_0_xtxt), TIMEOUT);
		SWTBotShell shell = bot.shell(Messages.AbstractFigureSelectionDialog_0_xtxt);
		Thread.sleep(2000);
		shell.bot().button("Cancel").click();
		Thread.sleep(300);
		bot.waitUntil(Conditions.shellCloses(shell), TIMEOUT);
		closeEditor(diagramEditor);

	}
}
