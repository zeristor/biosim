package biosim.server.power;

import biosim.idl.power.*;
/**
 * Nuclear Power Production System
 * @author    Scott Bell
 */

public class NuclearPowerPS extends PowerPSImpl {
	
	/**
	* When ticked, the Food Processor does the following:
	* 1) attempts to collect references to various server (if not already done).
	* 2) creates power and places it into the power store.
	*/
	public void tick(){
		collectReferences();
		myPowerStore.add(currentPowerProduced);
		if (moduleLogging)
			log();
	}
	
}
