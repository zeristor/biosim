package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class CO2AirPressureSensorImpl extends EnvironmentSensorImpl implements CO2AirPressureSensorOperations{
	public CO2AirPressureSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getCO2Moles() / getInput().getTotalMoles();
		myValue = randomFilter(preFilteredValue);
	}

	protected void notifyListeners(){
	}
	
	public float getMax(){
		return 1f;
	}
	
	/**
	* Returns the name of this module (CO2AirPressureSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2AirPressureSensor"+getID();
	}
}