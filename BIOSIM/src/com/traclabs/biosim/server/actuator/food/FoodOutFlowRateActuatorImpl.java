package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.framework.*;

public class FoodOutFlowRateActuatorImpl extends GenericActuatorImpl implements FoodOutFlowRateActuatorOperations{
	private FoodProducer myProducer;
	private int myIndex;
	
	public FoodOutFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setFoodOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(FoodProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	protected BioModule getModuleOutput(){
		return (BioModule)(myProducer);
	}
	
	public FoodProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myProducer.getFoodOutputMaxFlowRate(myIndex);
	}
	
	/**
	* Returns the name of this module (FoodOutFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "FoodOutFlowRateActuator"+getID();
	}
}
