package org.eclipse.graphiti.testtool.sketch.features;

import java.util.List;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.impl.ReconnectionContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.features.impl.DefaultReconnectionFeature;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.ICreateService;
import org.eclipse.graphiti.testtool.sketch.SketchUtil;
import org.eclipse.graphiti.util.IColorConstant;

public class SketchReconnectionFeature extends DefaultReconnectionFeature {

	public SketchReconnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canReconnect(IReconnectionContext context) {
		boolean ret = false;

		PictogramElement targetPictogramElement = context.getTargetPictogramElement();
		if (getDiagram().equals(targetPictogramElement)) {
			return false;
		}

		ret = super.canReconnect(context);
		if (ret == true) {
			return ret;
		}

		if (targetPictogramElement instanceof Connection) {
			ret = true;
		}
		return ret;
	}

	@Override
	public void preReconnect(IReconnectionContext context) {
		PictogramElement targetPictogramElement = context.getTargetPictogramElement();
		if (targetPictogramElement instanceof FreeFormConnection) {
			Connection targetConnection = (Connection) targetPictogramElement;
			ICreateService createService = Graphiti.getCreateService();

			Shape connectionPointShape = SketchUtil.createConnectionPoint(context.getTargetLocation(), getDiagram());
			Anchor newAnchor = createService.createChopboxAnchor(connectionPointShape);

			Anchor targetStart = targetConnection.getStart();
			Anchor targetEnd = targetConnection.getEnd();

			FreeFormConnection newConn1 = createService.createFreeFormConnection(getDiagram());
			newConn1.setStart(targetStart);
			newConn1.setEnd(newAnchor);
			initConnection(newConn1);

			FreeFormConnection newConn2 = createService.createFreeFormConnection(getDiagram());
			newConn2.setStart(newAnchor);
			newConn2.setEnd(targetEnd);
			initConnection(newConn2);

			Graphiti.getPeService().deletePictogramElement(targetConnection);

			if (context instanceof ReconnectionContext) {
				ReconnectionContext rc = (ReconnectionContext) context;
				rc.setNewAnchor(newAnchor);
				rc.setTargetPictogramElement(connectionPointShape);
			}
		}
		super.preReconnect(context);
	}

	@Override
	public void postReconnect(IReconnectionContext context) {
		Anchor oldAnchor = context.getOldAnchor();
		AnchorContainer oldAnchorContainer = oldAnchor.getParent();
		if (SketchUtil.isConnectionPoint(oldAnchorContainer)) {
			List<Connection> allConnections = Graphiti.getPeService().getAllConnections(oldAnchor);
			if (allConnections.size() == 2) {
				Connection c = allConnections.get(0);
				Anchor start = oldAnchor.equals(c.getStart()) ? c.getEnd() : c.getStart();
				c = allConnections.get(1);
				Anchor end = oldAnchor.equals(c.getStart()) ? c.getEnd() : c.getStart();

				FreeFormConnection newConn = Graphiti.getPeCreateService().createFreeFormConnection(getDiagram());
				newConn.setStart(start);
				newConn.setEnd(end);
				initConnection(newConn);

				RemoveContext ctx = new RemoveContext(oldAnchorContainer);
				getFeatureProvider().getRemoveFeature(ctx).remove(ctx);
			}
		}
		super.postReconnect(context);
	}

	private void initConnection(Connection connection) {
		Polyline p = Graphiti.getGaCreateService().createPolyline(connection);
		p.setLineWidth(3);
		p.setForeground(manageColor(IColorConstant.LIGHT_BLUE));
		p.setLineStyle(LineStyle.DASHDOT);
	}
}
