package biosim.server.power;

import biosim.idl.power.*;
import biosim.idl.util.log.*;
import biosim.idl.environment.*;
import biosim.idl.framework.*;
import java.util.*;
import biosim.server.util.*;
import biosim.server.framework.*;
/**
 * The Power Production System creates power from a generator (say a solar panel) and stores it in the power store.
 * This provides power to all the biomodules in the system.
 *
 * @author    Scott Bell
 */

public abstract class PowerPSImpl extends BioModuleImpl implements PowerPSOperations {
	//The power produced (in watts) by the Power PS at the current tick
	protected float currentPowerProduced = 0f;
	//Flag switched when the Power PS has collected references to other servers it need
	private boolean hasCollectedReferences = false;
	//References to the PowerStore the Power PS takes/puts power into
	protected PowerStore myPowerStore;
	protected SimEnvironment mySimEnvironment;
	private LogIndex myLogIndex;
	
	public PowerPSImpl(int pID){
		super(pID);
	}
	
	/**
	* When ticked, the PowerPS does the following:
	* 1) attempts to collect references to various server (if not already done).
	* 2) creates power and places it into the power store.
	*/
	public void tick(){
		collectReferences();
		currentPowerProduced = calculatePowerProduced();
		if (isMalfunctioning())
			performMalfunctions();
		myPowerStore.add(currentPowerProduced);
		if (moduleLogging)
			log();
	}
	
	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		StringBuffer returnBuffer = new StringBuffer();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnBuffer.append("Severe ");
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnBuffer.append("Medium ");
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnBuffer.append("Low ");
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnBuffer.append("Production Rate Decrease (Temporary)");
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnBuffer.append("Production Rate Decrease (Permanent)");
		return returnBuffer.toString();
	}
	
	private void performMalfunctions(){
		float productionRate = 1f;
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					productionRate *= 0.50;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					productionRate *= 0.25;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					productionRate *= 0.10;
			}
			else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					productionRate *= 0.50;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					productionRate *= 0.25;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					productionRate *= 0.10;
			}
		}
		currentPowerProduced *= productionRate;
	}
	
	protected abstract float calculatePowerProduced();
	
	/**
	* Reset does nothing right now
	*/
	public void reset(){
		super.reset();
		currentPowerProduced = 0f;
	}
	
	/**
	* Collects reference to PowerStore needed for putting/getting power.
	*/
	protected void collectReferences(){
		try{
			if (!hasCollectedReferences){
				mySimEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"+getID()));
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"+getID()));
				hasCollectedReferences = true;
			}
		}
		catch (org.omg.CORBA.UserException e){
			e.printStackTrace(System.out);
		}
	}
	
	/**
	* Returns the power produced (in watts) by the Power PS during the current tick
	* @return the power produced (in watts) by the Power PS during the current tick
	*/
	public  float getPowerProduced(){
		return currentPowerProduced;
	}
	
	/**
	* Returns the name of this module (PowerPS)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PowerPS"+getID();
	}
	
	protected void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode powerProducedHead = myLog.addChild("power_produced");
			myLogIndex.powerProducedIndex = powerProducedHead.addChild(""+currentPowerProduced);
			logInitialized = true;
		}
		else{
			myLogIndex.powerProducedIndex.setValue(""+currentPowerProduced);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode powerProducedIndex;
	}
}
