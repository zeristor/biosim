package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;

public class CO2AirEnvironmentOutFlowRateActuatorImpl extends GenericActuatorImpl implements CO2AirEnvironmentOutFlowRateActuatorOperations{
	private CO2AirProducer myProducer;
	private int myIndex;
	
	public CO2AirEnvironmentOutFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setCO2AirEnvironmentOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(CO2AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	protected BioModule getModuleOutput(){
		return (BioModule)(myProducer);
	}
	
	public float getMax(){
		return myProducer.getCO2AirEnvironmentOutputMaxFlowRate(myIndex);
	}
	
	public CO2AirProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (CO2AirEnvironmentOutFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2AirEnvironmentOutFlowRateActuator"+getID();
	}
}
