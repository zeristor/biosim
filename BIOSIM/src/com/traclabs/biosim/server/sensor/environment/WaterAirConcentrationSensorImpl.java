package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class WaterAirConcentrationSensorImpl extends EnvironmentSensorImpl implements WaterAirConcentrationSensorOperations{
	public WaterAirConcentrationSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getWaterMoles() / getInput().getTotalMoles();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	public float getMax(){
		return 1f;
	}
	
	
	/**
	* Returns the name of this module (WaterAirConcentrationSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "WaterAirConcentrationSensor"+getID();
	}
}