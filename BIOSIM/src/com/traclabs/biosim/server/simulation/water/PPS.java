package biosim.server.water;

public class PPS extends WaterRSSubSystem{
	private BWP myBWP;
	private RO myRO;
	private AES myAES;
	private boolean hasCollectedReferences = false;
	
	public PPS(WaterRSImpl pWaterRSImpl){
		super(pWaterRSImpl);
		powerNeeded =168; 
	}
	
	public float takeWater(){
		float potableWaterProduced = waterLevel;
		waterLevel = 0;
		return potableWaterProduced;
	}
	
	private void collectReferences(){
		if (!hasCollectedReferences){
			myBWP = myWaterRS.getBWP();
			myRO = myWaterRS.getRO();
			myAES = myWaterRS.getAES();
			hasCollectedReferences = true;
		}
	}

	public void tick(){
		collectReferences();
	}
}
