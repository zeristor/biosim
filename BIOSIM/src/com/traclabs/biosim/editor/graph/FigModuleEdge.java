package com.traclabs.biosim.editor.graph;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.apache.log4j.Logger;
import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadTriangle;
import org.tigris.gef.presentation.FigEdgeLine;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigText;

import com.traclabs.biosim.editor.ui.FlowratePropertiesFrame;
import com.traclabs.biosim.editor.ui.SimEnvironmentFlowratePropertiesFrame;
import com.traclabs.biosim.editor.ui.StoreFlowratePropertiesFrame;
import com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations;

public class FigModuleEdge extends FigEdgeLine implements MouseListener {
    private FigText mid;
    
    private FlowratePropertiesFrame myFlowratePropertiesFrame;
    
    private Logger myLogger;

    public FigModuleEdge() {
        super();
        myLogger = Logger.getLogger(FigModuleEdge.class);
        setDestArrowHead(new ArrowHeadTriangle());
        mid = new FigText(10, 30, 90, 20);
        mid.setText("");
        mid.setTextColor(Color.black);
        mid.setTextFilled(false);
        mid.setFilled(false);
        mid.setLineWidth(0);
        addPathItem(mid, new PathConvPercent(this, 50, 10));

        _useNearest = true;
    }

    public void setBetweenNearestPoints(boolean un) {
    }

    public String getText() {
        return mid.getText();
    }

    public void setText(String text) {
        mid.setText(text);
    }
    
    /** Compute the shape of the line that presents an Edge. */
    public void computeRoute() {
        Point srcPt = getSourcePortFig().center();
        Point dstPt = getDestPortFig().center();

        srcPt = _sourceFigNode.connectionPoint(dstPt);
        dstPt = _destFigNode.connectionPoint(srcPt);

        ((FigLine) _fig).setShape(srcPt, dstPt);
        calcBounds();
    }
    
    public void editProperties(int x, int y){
        if (myFlowratePropertiesFrame == null){
            ModuleEdge theEdge = (ModuleEdge)getOwner();
            if (theEdge.getOperations() instanceof StoreFlowRateControllableOperations)
            	myFlowratePropertiesFrame = new StoreFlowratePropertiesFrame(this, theEdge.getOperations(), theEdge.getStoreImpl());
            else
            	myFlowratePropertiesFrame = new SimEnvironmentFlowratePropertiesFrame(this, theEdge.getOperations(), theEdge.getSimEnvironmentImpl());
            myFlowratePropertiesFrame.pack();
        }
        myFlowratePropertiesFrame.setLocation(x, y);
        myFlowratePropertiesFrame.setVisible(true);
    }

    public void mouseClicked(MouseEvent me) {
        if (me.getClickCount() == 2){
            me.consume();
            editProperties(me.getX(), me.getY());
        }
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }
}