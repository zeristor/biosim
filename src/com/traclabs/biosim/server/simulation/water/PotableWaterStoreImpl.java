package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.PotableWaterStoreOperations;

/**
 * The Potable Water Store Implementation. Filled with clean potable water from
 * the WaterRS and used by the crew memebers to drink.
 * 
 * @author Scott Bell
 */

public class PotableWaterStoreImpl extends WaterStoreImpl implements
        PotableWaterStoreOperations {
    public PotableWaterStoreImpl(int pID, String pName) {
        super(pID, pName);
    }

	public PotableWaterStoreImpl() {
		this(0, "Unnamed PotableWaterStore");
	}
}