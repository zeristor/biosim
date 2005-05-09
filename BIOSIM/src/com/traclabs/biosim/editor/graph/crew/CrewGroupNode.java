package com.traclabs.biosim.editor.graph.crew;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.crew.CrewGroupImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class CrewGroupNode extends ActiveNode{
    private static int nameID = 0;
    
    private CrewGroupImpl myCrewGroupImpl;
    public CrewGroupNode() {
        myCrewGroupImpl = new CrewGroupImpl(0, "CrewGroup" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigCrewGroupNode node = new FigCrewGroupNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myCrewGroupImpl;
    }
}