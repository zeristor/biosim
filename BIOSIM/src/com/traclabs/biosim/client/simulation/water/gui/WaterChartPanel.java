package biosim.client.water.gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import biosim.idl.water.*;
import biosim.idl.environment.*;

/**
 * This is the JPanel that displays a chart about the Water
 *
 * @author    Scott Bell
 */
public class WaterChartPanel extends BioTabPanel
{
	private WaterStorePanel myWaterStorePanel;

	public WaterChartPanel(BioSimulator pBioSimulator) {
		setLayout(new BorderLayout());
		myWaterStorePanel = new WaterStorePanel(pBioSimulator);
		add(myWaterStorePanel, BorderLayout.CENTER);
		myWaterStorePanel.start();
	}
	
	public void lostFocus(){
			myWaterStorePanel.pause();
	}
	
	public void gotFocus(){
			myWaterStorePanel.start();
	}
	
	public void visibilityChange(boolean isVisible){
		if (isVisible)
			myWaterStorePanel.start();
		else
			myWaterStorePanel.stop();
	}
}
