/*
 * Created on May 12, 2005
 *
 */
package com.traclabs.biosim.client.simulation.power.schematic.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode;
import com.traclabs.biosim.editor.graph.environment.FigSimEnvironmentNode;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;

/**
 * @author scott
 *
 */
public class SimEnvironmentPropertiesFrame extends JFrame {
    private JTextField myNameField;

    private JFormattedTextField myVolumeField;
    
    private JCheckBox mySensorCheckBox;
    
    private JButton myOKButton;

    private SimEnvironmentImpl mySimEnvironmentImpl;
    
    private FigSimEnvironmentNode myFigEnvironmentNode;

    public SimEnvironmentPropertiesFrame(FigSimEnvironmentNode pNode) {
        myFigEnvironmentNode = pNode;
        ModuleNode owner = (ModuleNode) pNode.getOwner();
        mySimEnvironmentImpl = (SimEnvironmentImpl) owner.getSimBioModuleImpl();
        myNameField = new JTextField(mySimEnvironmentImpl.getModuleName());
        myVolumeField = new JFormattedTextField(NumberFormat.getNumberInstance());
        myVolumeField.setValue(new Float(mySimEnvironmentImpl.getInitialVolume()));
        mySensorCheckBox = new JCheckBox();
        myOKButton = new JButton(new OKAction());

        setLayout(new GridLayout(4, 2));
        add(new JLabel("Name"));
        add(myNameField);
        add(new JLabel("Volume"));
        add(myVolumeField);
        add(new JLabel("Sensed"));
        add(mySensorCheckBox);
        add(myOKButton);
        setTitle(pNode.getText() + " Properties");
    }
    
    /**
     * Action that auto-arranges internal menus
     */
    private class OKAction extends AbstractAction {
        public OKAction() {
            super("OK");
        }

        public void actionPerformed(ActionEvent ae) {
            mySimEnvironmentImpl.setModuleName(myNameField.getText());
            float volume = ((Number)myVolumeField.getValue()).floatValue();
            mySimEnvironmentImpl.setInitialVolumeAtSeaLevel(volume);
            myFigEnvironmentNode.setIsSensed(mySensorCheckBox.isSelected());
            myFigEnvironmentNode.damage();
            dispose();
        }
    }
}