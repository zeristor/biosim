package biosim.server.framework;

import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.crew.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.water.*;
import biosim.idl.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.actuator.crew.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.actuator.food.*;
import biosim.idl.actuator.power.*;
import biosim.idl.actuator.water.*;
import biosim.idl.framework.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.*;
/*
 *
 * @author    Scott Bell
 */

public class BioDriverImpl extends BioDriverPOA implements Runnable
{
	//Module Names, only used for initialization

	//
	//framework
	private String myLoggerName;
	//

	//
	//simulation
	//
	private String myCrewGroupName;
	private String myPowerPSName;
	private String myPowerStoreName;
	private String myAirRSName;
	private String myCO2StoreName;
	private String myO2StoreName;
	private String myH2StoreName;
	private String myBiomassRSName;
	private String myBiomassStoreName;
	private String myFoodProcessorName;
	private String myFoodStoreName;
	private String myWaterRSName;
	private String myDirtyWaterStoreName;
	private String myPotableWaterStoreName;
	private String myGreyWaterStoreName;
	private String myCrewEnvironmentName;
	private String myPlantEnvironmentName;
	private String myAccumulatorName;
	private String myInjectorName;

	//
	//sensor
	//
	//Air
	//AirRs
	private String myAirRSPowerInFlowRateSensorName;
	private String myAirRSPotableWaterInFlowRateSensorName;
	private String myAirRSPotableWaterOutFlowRateSensorName;
	private String myAirRSAirInFlowRateSensorName;
	private String myAirRSAirOutFlowRateSensorName;
	private String myAirRSO2OutFlowRateSensorName;
	private String myAirRSCO2InFlowRateSensorName;
	private String myAirRSCO2OutFlowRateSensorName;
	private String myAirRSH2InFlowRateSensorName;
	private String myAirRSH2OutFlowRateSensorName;
	//Stores
	private String myO2StoreLevelSensorName;
	private String myCO2StoreLevelSensorName;
	private String myH2StoreLevelSensorName;
	//Crew
	private String myCrewGroupDeathSensorName;
	private String myCrewGroupPotableWaterInFlowRateSensorName;
	private String myCrewGroupGreyWaterOutFlowRateSensorName;
	private String myCrewGroupDirtyWaterOutFlowRateSensorName;
	//Power
	//PowerPS
	private String myPowerPSPowerOutFlowRateSensorName;
	//Stores
	private String myPowerStoreLevelSensorName;
	//Environment
	//Crew
	private String myCrewEnvironmentOtherAirConcentrationSensorName;
	private String myCrewEnvironmentCO2AirConcentrationSensorName;
	private String myCrewEnvironmentO2AirConcentrationSensorName;
	private String myCrewEnvironmentWaterAirConcentrationSensorName;
	private String myCrewEnvironmentOtherAirPressureSensorName;
	private String myCrewEnvironmentCO2AirPressureSensorName;
	private String myCrewEnvironmentO2AirPressureSensorName;
	private String myCrewEnvironmentWaterAirPressureSensorName;
	//Plant
	private String myPlantEnvironmentOtherAirConcentrationSensorName;
	private String myPlantEnvironmentCO2AirConcentrationSensorName;
	private String myPlantEnvironmentO2AirConcentrationSensorName;
	private String myPlantEnvironmentWaterAirConcentrationSensorName;
	private String myPlantEnvironmentOtherAirPressureSensorName;
	private String myPlantEnvironmentCO2AirPressureSensorName;
	private String myPlantEnvironmentO2AirPressureSensorName;
	private String myPlantEnvironmentWaterAirPressureSensorName;
	//Water
	//WaterRS
	private String myWaterRSDirtyWaterInFlowRateSensorName;
	private String myWaterRSGreyWaterInFlowRateSensorName;
	private String myWaterRSPowerInFlowRateSensorName;
	private String myWaterRSPotableWaterOutFlowRateSensorName;
	//Stores
	private String myPotableWaterStoreLevelSensorName;
	private String myGreyWaterStoreLevelSensorName;
	private String myDirtyWaterStoreLevelSensorName;
	//Food
	//BiomassRS
	private String myBiomassRSAirInFlowRateSensorName;
	private String myBiomassRSAirOutFlowRateSensorName;
	private String myBiomassRSPotableWaterInFlowRateSensorName;
	private String myBiomassRSGreyWaterInFlowRateSensorName;
	private String myBiomassRSDirtyWaterOutFlowRateSensorName;
	private String myBiomassRSBiomassOutFlowRateSensorName;
	private String myBiomassRSPowerInFlowRateSensorName;
	//Food Processor
	private String myFoodProcessorPowerInFlowRateSensorName;
	private String myFoodProcessorBiomassInFlowRateSensorName;
	private String myFoodProcessorFoodOutFlowRateSensorName;
	//Stores
	private String myBiomassStoreLevelSensorName;
	private String myFoodStoreLevelSensorName;
	//Framework
	//Accumulator
	private String myAccumulatorCO2AirEnvironmentInFlowRateSensorName;
	private String myAccumulatorO2AirEnvironmentInFlowRateSensorName;
	private String myAccumulatorCO2AirStoreOutFlowRateSensorName;
	private String myAccumulatorO2AirStoreOutFlowRateSensorName;
	//Injector
	private String myInjectorCO2AirStoreInFlowRateSensorName;
	private String myInjectorO2AirStoreInFlowRateSensorName;
	private String myInjectorCO2AirEnvironmentOutFlowRateSensorName;
	private String myInjectorO2AirEnvironmentOutFlowRateSensorName;

	//
	//actuator
	//
	//Air
	//AirRs
	private String myAirRSPowerInFlowRateActuatorName;
	private String myAirRSAirInFlowRateActuatorName;
	private String myAirRSAirOutFlowRateActuatorName;
	private String myAirRSO2OutFlowRateActuatorName;
	private String myAirRSCO2InFlowRateActuatorName;
	private String myAirRSCO2OutFlowRateActuatorName;
	private String myAirRSH2InFlowRateActuatorName;
	private String myAirRSH2OutFlowRateActuatorName;
	private String myAirRSPotableWaterInFlowRateActuatorName;
	private String myAirRSPotableWaterOutFlowRateActuatorName;
	//Power
	//PowerPS
	private String myPowerPSPowerOutFlowRateActuatorName;
	//Water
	//WaterRS
	private String myWaterRSDirtyWaterInFlowRateActuatorName;
	private String myWaterRSGreyWaterInFlowRateActuatorName;
	private String myWaterRSPowerInFlowRateActuatorName;
	private String myWaterRSPotableWaterOutFlowRateActuatorName;
	//Crew
	private String myCrewGroupPotableWaterInFlowRateActuatorName;
	private String myCrewGroupGreyWaterOutFlowRateActuatorName;
	private String myCrewGroupDirtyWaterOutFlowRateActuatorName;
	//Food
	//BiomassRS
	private String myBiomassRSAirInFlowRateActuatorName;
	private String myBiomassRSAirOutFlowRateActuatorName;
	private String myBiomassRSPotableWaterInFlowRateActuatorName;
	private String myBiomassRSGreyWaterInFlowRateActuatorName;
	private String myBiomassRSBiomassOutFlowRateActuatorName;
	private String myBiomassRSPowerInFlowRateActuatorName;
	//Food Processor
	private String myFoodProcessorPowerInFlowRateActuatorName;
	private String myFoodProcessorBiomassInFlowRateActuatorName;
	private String myFoodProcessorFoodOutFlowRateActuatorName;
	//Framework
	//Accumulator
	private String myAccumulatorCO2AirEnvironmentInFlowRateActuatorName;
	private String myAccumulatorO2AirEnvironmentInFlowRateActuatorName;
	private String myAccumulatorCO2AirStoreOutFlowRateActuatorName;
	private String myAccumulatorO2AirStoreOutFlowRateActuatorName;
	//Injector
	private String myInjectorCO2AirStoreInFlowRateActuatorName;
	private String myInjectorO2AirStoreInFlowRateActuatorName;
	private String myInjectorCO2AirEnvironmentOutFlowRateActuatorName;
	private String myInjectorO2AirEnvironmentOutFlowRateActuatorName;

	//A hastable containing the server references
	private Map modules;
	private Map sensors;
	private Map actuators;
	//A reference to the naming service
	private NamingContextExt ncRef;
	//The thread to run the simulation
	private Thread myTickThread;
	//Flag to see whether the BioDriverImpl is paused (started but not ticking)
	private boolean simulationIsPaused = false;
	//Flag to see whether the BioDriverImpl is started at all
	private boolean simulationStarted = false;
	private boolean usedDefaultModules = true;
	//Flag to see if user wants to use default intialization (i.e., fill tanks with x amount gas, generate crew memebers, etc)
	private BioDriverInit initializationToUse = BioDriverInit.DEFAULT_INIT;
	//Tells whether simulation runs until crew death
	private boolean runTillDead = false;
	//Tells whether simulation runs till a fixed number of ticks
	private boolean runTillN = false;
	//If <runTillN == true, this is the number of ticks to run for.
	private int nTicks = 0;
	//The number of ticks gone by
	private int ticksGoneBy = 0;
	//Tells whether all the modules known are logging or not (only checked when logging turned on/off)
	private boolean fullLogging = false;
	private boolean actuatorsLogging = false;
	private boolean sensorsLogging = false;
	//The logger module
	private Logger myLogger;
	//Tells whether BioDriver has collected references to modules it needs from the nameserver (used for initializations)
	private boolean hasCollectedReferences = false;
	//How long BioDriver should pause between ticks
	private int driverPauseLength = 0;
	//The ID of this instance of BioSim
	private int myID = 0;
	//If the initialization has createdCrewalready created the crew or not.
	private boolean createdCrew = false;
	//If the initialization has already created the plants or not.
	private boolean createdPlants = false;
	//If we loop after end conditions of a simulation run have been met (crew death or n-ticks)
	private boolean looping = false;
	private List myModuleNames;
	private List mySensorNames;
	private List myActuatorNames;

	/**
	* Constructs the BioDriver
	* @param pID The ID of this instance of the BioSim (must be the same for all modules in the instance)
	*/
	public BioDriverImpl(int pID){
		myID = pID;
		myModuleNames = new Vector();
		mySensorNames = new Vector();
		myActuatorNames = new Vector();
		//
		//framework
		//
		myLoggerName = "Logger"+myID;

		//
		//simulation
		//
		myCrewGroupName = "CrewGroup"+myID;
		myPowerPSName = "PowerPS"+myID;
		myPowerStoreName = "PowerStore"+myID;
		myAirRSName = "AirRS"+myID;
		myCO2StoreName = "CO2Store"+myID;
		myO2StoreName = "O2Store"+myID;
		myH2StoreName = "H2Store"+myID;
		myBiomassRSName = "BiomassRS"+myID;
		myBiomassStoreName = "BiomassStore"+myID;
		myFoodProcessorName = "FoodProcessor"+myID;
		myFoodStoreName = "FoodStore"+myID;
		myWaterRSName = "WaterRS"+myID;
		myDirtyWaterStoreName = "DirtyWaterStore"+myID;
		myPotableWaterStoreName = "PotableWaterStore"+myID;
		myGreyWaterStoreName = "GreyWaterStore"+myID;
		myCrewEnvironmentName = "CrewEnvironment"+myID;
		myPlantEnvironmentName = "PlantEnvironment"+myID;
		myAccumulatorName = "Accumulator"+myID;
		myInjectorName = "Injector"+myID;
		myModuleNames.add(myCrewGroupName);
		myModuleNames.add(myPowerPSName);
		myModuleNames.add(myPowerStoreName);
		myModuleNames.add(myAirRSName);
		myModuleNames.add(myCO2StoreName);
		myModuleNames.add(myO2StoreName);
		myModuleNames.add(myH2StoreName);
		myModuleNames.add(myBiomassRSName);
		myModuleNames.add(myBiomassStoreName);
		myModuleNames.add(myFoodProcessorName);
		myModuleNames.add(myFoodStoreName);
		myModuleNames.add(myWaterRSName);
		myModuleNames.add(myDirtyWaterStoreName);
		myModuleNames.add(myPotableWaterStoreName);
		myModuleNames.add(myGreyWaterStoreName);
		myModuleNames.add(myCrewEnvironmentName);
		myModuleNames.add(myPlantEnvironmentName);
		myModuleNames.add(myAccumulatorName);
		myModuleNames.add(myInjectorName);

		//
		//sensors
		//
		//Air
		//AirRS
		myAirRSPowerInFlowRateSensorName = "AirRSPowerInFlowRateSensor"+myID;
		myAirRSAirInFlowRateSensorName = "AirRSAirInFlowRateSensor"+myID;
		myAirRSAirOutFlowRateSensorName = "AirRSAirOutFlowRateSensor"+myID;
		myAirRSO2OutFlowRateSensorName = "AirRSO2OutFlowRateSensor"+myID;
		myAirRSCO2InFlowRateSensorName = "AirRSCO2InFlowRateSensor"+myID;
		myAirRSCO2OutFlowRateSensorName = "AirRSCO2OutFlowRateSensor"+myID;
		myAirRSH2InFlowRateSensorName = "AirRSH2InFlowRateSensor"+myID;
		myAirRSH2OutFlowRateSensorName = "AirRSH2OutFlowRateSensor"+myID;
		myAirRSPotableWaterInFlowRateSensorName = "AirRSPotableWaterInFlowRateSensor"+myID;
		myAirRSPotableWaterOutFlowRateSensorName = "AirRSPotableWaterOutFlowRateSensor"+myID;
		myModuleNames.add(myAirRSPowerInFlowRateSensorName);
		myModuleNames.add(myAirRSPotableWaterInFlowRateSensorName);
		myModuleNames.add(myAirRSPotableWaterOutFlowRateSensorName);
		myModuleNames.add(myAirRSAirInFlowRateSensorName);
		myModuleNames.add(myAirRSAirOutFlowRateSensorName);
		myModuleNames.add(myAirRSO2OutFlowRateSensorName);
		myModuleNames.add(myAirRSCO2InFlowRateSensorName);
		myModuleNames.add(myAirRSCO2OutFlowRateSensorName);
		myModuleNames.add(myAirRSH2InFlowRateSensorName);
		myModuleNames.add(myAirRSH2OutFlowRateSensorName);
		mySensorNames.add(myAirRSPowerInFlowRateSensorName);
		mySensorNames.add(myAirRSAirInFlowRateSensorName);
		mySensorNames.add(myAirRSAirOutFlowRateSensorName);
		mySensorNames.add(myAirRSO2OutFlowRateSensorName);
		mySensorNames.add(myAirRSCO2InFlowRateSensorName);
		mySensorNames.add(myAirRSCO2OutFlowRateSensorName);
		mySensorNames.add(myAirRSH2InFlowRateSensorName);
		mySensorNames.add(myAirRSH2OutFlowRateSensorName);
		mySensorNames.add(myAirRSPotableWaterInFlowRateSensorName);
		mySensorNames.add(myAirRSPotableWaterOutFlowRateSensorName);
		//Stores
		myO2StoreLevelSensorName = "O2StoreLevelSensor"+myID;
		myCO2StoreLevelSensorName = "CO2StoreLevelSensor"+myID;
		myH2StoreLevelSensorName = "H2StoreLevelSensor"+myID;
		myModuleNames.add(myO2StoreLevelSensorName);
		myModuleNames.add(myCO2StoreLevelSensorName);
		myModuleNames.add(myH2StoreLevelSensorName);
		mySensorNames.add(myO2StoreLevelSensorName);
		mySensorNames.add(myCO2StoreLevelSensorName);
		mySensorNames.add(myH2StoreLevelSensorName);
		//Crew
		myCrewGroupDeathSensorName = "CrewGroupDeathSensor"+myID;
		myCrewGroupPotableWaterInFlowRateSensorName = "CrewGroupPotableWaterInFlowRateSensor"+myID;
		myCrewGroupGreyWaterOutFlowRateSensorName = "CrewGroupGreyWaterOutFlowRateSensor"+myID;
		myCrewGroupDirtyWaterOutFlowRateSensorName = "CrewGroupDirtyWaterOutFlowRateSensor"+myID;
		myModuleNames.add(myCrewGroupDeathSensorName);
		myModuleNames.add(myCrewGroupPotableWaterInFlowRateSensorName);
		myModuleNames.add(myCrewGroupGreyWaterOutFlowRateSensorName);
		myModuleNames.add(myCrewGroupDirtyWaterOutFlowRateSensorName);
		mySensorNames.add(myCrewGroupDeathSensorName);
		mySensorNames.add(myCrewGroupPotableWaterInFlowRateSensorName);
		mySensorNames.add(myCrewGroupGreyWaterOutFlowRateSensorName);
		mySensorNames.add(myCrewGroupDirtyWaterOutFlowRateSensorName);
		//Power
		//PowerPS
		myPowerPSPowerOutFlowRateSensorName = "PowerPSPowerOutFlowRateSensor"+myID;
		myModuleNames.add(myPowerPSPowerOutFlowRateSensorName);
		mySensorNames.add(myPowerPSPowerOutFlowRateSensorName);
		//Stores
		myPowerStoreLevelSensorName = "PowerStoreLevelSensor"+myID;
		myModuleNames.add(myPowerStoreLevelSensorName);
		mySensorNames.add(myPowerStoreLevelSensorName);
		//Environment
		//Crew
		myCrewEnvironmentOtherAirConcentrationSensorName = "CrewEnvironmentOtherAirConcentrationSensor"+myID;
		myCrewEnvironmentCO2AirConcentrationSensorName = "CrewEnvironmentCO2AirConcentrationSensor"+myID;
		myCrewEnvironmentO2AirConcentrationSensorName = "CrewEnvironmentO2AirConcentrationSensor"+myID;
		myCrewEnvironmentWaterAirConcentrationSensorName = "CrewEnvironmentWaterAirConcentrationSensor"+myID;
		myCrewEnvironmentOtherAirPressureSensorName = "CrewEnvironmentOtherAirPressureSensor"+myID;
		myCrewEnvironmentCO2AirPressureSensorName = "CrewEnvironmentCO2AirPressureSensor"+myID;
		myCrewEnvironmentO2AirPressureSensorName = "CrewEnvironmentO2AirPressureSensor"+myID;
		myCrewEnvironmentWaterAirPressureSensorName = "CrewEnvironmentWaterAirPressureSensor"+myID;
		myModuleNames.add(myCrewEnvironmentOtherAirConcentrationSensorName);
		myModuleNames.add(myCrewEnvironmentCO2AirConcentrationSensorName);
		myModuleNames.add(myCrewEnvironmentO2AirConcentrationSensorName);
		myModuleNames.add(myCrewEnvironmentWaterAirConcentrationSensorName);
		myModuleNames.add(myCrewEnvironmentOtherAirPressureSensorName);
		myModuleNames.add(myCrewEnvironmentCO2AirPressureSensorName);
		myModuleNames.add(myCrewEnvironmentO2AirPressureSensorName);
		myModuleNames.add(myCrewEnvironmentWaterAirPressureSensorName);
		mySensorNames.add(myCrewEnvironmentOtherAirConcentrationSensorName);
		mySensorNames.add(myCrewEnvironmentCO2AirConcentrationSensorName);
		mySensorNames.add(myCrewEnvironmentO2AirConcentrationSensorName);
		mySensorNames.add(myCrewEnvironmentWaterAirConcentrationSensorName);
		mySensorNames.add(myCrewEnvironmentOtherAirPressureSensorName);
		mySensorNames.add(myCrewEnvironmentCO2AirPressureSensorName);
		mySensorNames.add(myCrewEnvironmentO2AirPressureSensorName);
		mySensorNames.add(myCrewEnvironmentWaterAirPressureSensorName);
		//Plant
		myPlantEnvironmentOtherAirConcentrationSensorName = "PlantEnvironmentOtherAirConcentrationSensor"+myID;
		myPlantEnvironmentCO2AirConcentrationSensorName = "PlantEnvironmentCO2AirConcentrationSensor"+myID;
		myPlantEnvironmentO2AirConcentrationSensorName = "PlantEnvironmentO2AirConcentrationSensor"+myID;
		myPlantEnvironmentWaterAirConcentrationSensorName = "PlantEnvironmentWaterAirConcentrationSensor"+myID;
		myPlantEnvironmentOtherAirPressureSensorName = "PlantEnvironmentOtherAirPressureSensor"+myID;
		myPlantEnvironmentCO2AirPressureSensorName = "PlantEnvironmentCO2AirPressureSensor"+myID;
		myPlantEnvironmentO2AirPressureSensorName = "PlantEnvironmentO2AirPressureSensor"+myID;
		myPlantEnvironmentWaterAirPressureSensorName = "PlantEnvironmentWaterAirPressureSensor"+myID;
		myModuleNames.add(myPlantEnvironmentOtherAirConcentrationSensorName);
		myModuleNames.add(myPlantEnvironmentCO2AirConcentrationSensorName);
		myModuleNames.add(myPlantEnvironmentO2AirConcentrationSensorName);
		myModuleNames.add(myPlantEnvironmentWaterAirConcentrationSensorName);
		myModuleNames.add(myPlantEnvironmentOtherAirPressureSensorName);
		myModuleNames.add(myPlantEnvironmentCO2AirPressureSensorName);
		myModuleNames.add(myPlantEnvironmentO2AirPressureSensorName);
		myModuleNames.add(myPlantEnvironmentWaterAirPressureSensorName);
		mySensorNames.add(myPlantEnvironmentOtherAirConcentrationSensorName);
		mySensorNames.add(myPlantEnvironmentCO2AirConcentrationSensorName);
		mySensorNames.add(myPlantEnvironmentO2AirConcentrationSensorName);
		mySensorNames.add(myPlantEnvironmentWaterAirConcentrationSensorName);
		mySensorNames.add(myPlantEnvironmentOtherAirPressureSensorName);
		mySensorNames.add(myPlantEnvironmentCO2AirPressureSensorName);
		mySensorNames.add(myPlantEnvironmentO2AirPressureSensorName);
		mySensorNames.add(myPlantEnvironmentWaterAirPressureSensorName);
		//Water
		//WaterRS
		myWaterRSDirtyWaterInFlowRateSensorName = "WaterRSDirtyWaterInFlowRateSensor"+myID;
		myWaterRSGreyWaterInFlowRateSensorName = "WaterRSGreyWaterInFlowRateSensor"+myID;
		myWaterRSPowerInFlowRateSensorName = "WaterRSPowerInFlowRateSensor"+myID;
		myWaterRSPotableWaterOutFlowRateSensorName = "WaterRSPotableWaterOutFlowRateSensor"+myID;
		myModuleNames.add(myWaterRSDirtyWaterInFlowRateSensorName);
		myModuleNames.add(myWaterRSGreyWaterInFlowRateSensorName);
		myModuleNames.add(myWaterRSPowerInFlowRateSensorName);
		myModuleNames.add(myWaterRSPotableWaterOutFlowRateSensorName);
		mySensorNames.add(myWaterRSDirtyWaterInFlowRateSensorName);
		mySensorNames.add(myWaterRSGreyWaterInFlowRateSensorName);
		mySensorNames.add(myWaterRSPowerInFlowRateSensorName);
		mySensorNames.add(myWaterRSPotableWaterOutFlowRateSensorName);
		//Stores
		myPotableWaterStoreLevelSensorName = "PotableWaterStoreLevelSensor"+myID;
		myGreyWaterStoreLevelSensorName = "GreyWaterStoreLevelSensor"+myID;
		myDirtyWaterStoreLevelSensorName = "DirtyWaterStoreLevelSensor"+myID;
		myModuleNames.add(myPotableWaterStoreLevelSensorName);
		myModuleNames.add(myGreyWaterStoreLevelSensorName);
		myModuleNames.add(myDirtyWaterStoreLevelSensorName);
		mySensorNames.add(myPotableWaterStoreLevelSensorName);
		mySensorNames.add(myGreyWaterStoreLevelSensorName);
		mySensorNames.add(myDirtyWaterStoreLevelSensorName);
		//Food
		//BiomassRS
		myBiomassRSAirInFlowRateSensorName = "BiomassRSAirInFlowRateSensor"+myID;
		myBiomassRSPowerInFlowRateSensorName = "BiomassRSPowerInFlowRateSensor"+myID;
		myBiomassRSAirOutFlowRateSensorName = "BiomassRSAirOutFlowRateSensor"+myID;
		myBiomassRSPotableWaterInFlowRateSensorName = "BiomassRSPotableWaterInFlowRateSensor"+myID;
		myBiomassRSGreyWaterInFlowRateSensorName = "BiomassRSGreyWaterInFlowRateSensor"+myID;
		myBiomassRSDirtyWaterOutFlowRateSensorName = "BiomassRSDirtyWaterOutFlowRateSensor"+myID;
		myBiomassRSBiomassOutFlowRateSensorName = "BiomassRSBiomassOutFlowRateSensor"+myID;
		myModuleNames.add(myBiomassRSAirInFlowRateSensorName);
		myModuleNames.add(myBiomassRSPowerInFlowRateSensorName);
		myModuleNames.add(myBiomassRSAirOutFlowRateSensorName);
		myModuleNames.add(myBiomassRSPotableWaterInFlowRateSensorName);
		myModuleNames.add(myBiomassRSGreyWaterInFlowRateSensorName);
		myModuleNames.add(myBiomassRSDirtyWaterOutFlowRateSensorName);
		myModuleNames.add(myBiomassRSBiomassOutFlowRateSensorName);
		mySensorNames.add(myBiomassRSAirInFlowRateSensorName);
		mySensorNames.add(myBiomassRSPowerInFlowRateSensorName);
		mySensorNames.add(myBiomassRSAirOutFlowRateSensorName);
		mySensorNames.add(myBiomassRSPotableWaterInFlowRateSensorName);
		mySensorNames.add(myBiomassRSGreyWaterInFlowRateSensorName);
		mySensorNames.add(myBiomassRSDirtyWaterOutFlowRateSensorName);
		mySensorNames.add(myBiomassRSBiomassOutFlowRateSensorName);
		//Food Processor
		myFoodProcessorPowerInFlowRateSensorName = "FoodProcessorPowerInFlowRateSensor"+myID;
		myFoodProcessorBiomassInFlowRateSensorName = "FoodProcessorBiomassInFlowRateSensor"+myID;
		myFoodProcessorFoodOutFlowRateSensorName = "FoodProcessorFoodOutFlowRateSensor"+myID;
		myModuleNames.add(myFoodProcessorPowerInFlowRateSensorName);
		myModuleNames.add(myFoodProcessorBiomassInFlowRateSensorName);
		myModuleNames.add(myFoodProcessorFoodOutFlowRateSensorName);
		mySensorNames.add(myFoodProcessorPowerInFlowRateSensorName);
		mySensorNames.add(myFoodProcessorBiomassInFlowRateSensorName);
		mySensorNames.add(myFoodProcessorFoodOutFlowRateSensorName);
		//Stores
		myBiomassStoreLevelSensorName = "BiomassStoreLevelSensor"+myID;
		myFoodStoreLevelSensorName = "FoodStoreLevelSensor"+myID;
		myModuleNames.add(myBiomassStoreLevelSensorName);
		myModuleNames.add(myFoodStoreLevelSensorName);
		mySensorNames.add(myBiomassStoreLevelSensorName);
		mySensorNames.add(myFoodStoreLevelSensorName);
		//Framework
		//Accumulator
		myAccumulatorCO2AirEnvironmentInFlowRateSensorName = "AccumulatorCO2AirEnvironmentInFlowRateSensor"+myID;
		myAccumulatorO2AirEnvironmentInFlowRateSensorName = "AccumulatorO2AirEnvironmentInFlowRateSensor"+myID;
		myAccumulatorCO2AirStoreOutFlowRateSensorName = "AccumulatorCO2AirStoreOutFlowRateSensor"+myID;
		myAccumulatorO2AirStoreOutFlowRateSensorName = "AccumulatorO2AirStoreOutFlowRateSensor"+myID;
		myModuleNames.add(myAccumulatorCO2AirEnvironmentInFlowRateSensorName);
		myModuleNames.add(myAccumulatorO2AirEnvironmentInFlowRateSensorName);
		myModuleNames.add(myAccumulatorCO2AirStoreOutFlowRateSensorName);
		myModuleNames.add(myAccumulatorO2AirStoreOutFlowRateSensorName);
		mySensorNames.add(myAccumulatorCO2AirEnvironmentInFlowRateSensorName);
		mySensorNames.add(myAccumulatorO2AirEnvironmentInFlowRateSensorName);
		mySensorNames.add(myAccumulatorCO2AirStoreOutFlowRateSensorName);
		mySensorNames.add(myAccumulatorO2AirStoreOutFlowRateSensorName);
		//Injector
		myInjectorCO2AirStoreInFlowRateSensorName = "InjectorCO2AirStoreInFlowRateSensor"+myID;
		myInjectorO2AirStoreInFlowRateSensorName = "InjectorO2AirStoreInFlowRateSensor"+myID;
		myInjectorCO2AirEnvironmentOutFlowRateSensorName = "InjectorCO2AirEnvironmentOutFlowRateSensor"+myID;
		myInjectorO2AirEnvironmentOutFlowRateSensorName = "InjectorO2AirEnvironmentOutFlowRateSensor"+myID;
		myModuleNames.add(myInjectorCO2AirStoreInFlowRateSensorName);
		myModuleNames.add(myInjectorO2AirStoreInFlowRateSensorName);
		myModuleNames.add(myInjectorCO2AirEnvironmentOutFlowRateSensorName);
		myModuleNames.add(myInjectorO2AirEnvironmentOutFlowRateSensorName);
		mySensorNames.add(myInjectorCO2AirStoreInFlowRateSensorName);
		mySensorNames.add(myInjectorO2AirStoreInFlowRateSensorName);
		mySensorNames.add(myInjectorCO2AirEnvironmentOutFlowRateSensorName);
		mySensorNames.add(myInjectorO2AirEnvironmentOutFlowRateSensorName);

		//
		//actuators
		//
		//Air
		//AirRS
		myAirRSPowerInFlowRateActuatorName = "AirRSPowerInFlowRateActuator"+myID;
		myAirRSAirInFlowRateActuatorName = "AirRSAirInFlowRateActuator"+myID;
		myAirRSAirOutFlowRateActuatorName = "AirRSAirOutFlowRateActuator"+myID;
		myAirRSO2OutFlowRateActuatorName = "AirRSO2OutFlowRateActuator"+myID;
		myAirRSCO2InFlowRateActuatorName = "AirRSCO2InFlowRateActuator"+myID;
		myAirRSCO2OutFlowRateActuatorName = "AirRSCO2OutFlowRateActuator"+myID;
		myAirRSH2InFlowRateActuatorName = "AirRSH2InFlowRateActuator"+myID;
		myAirRSH2OutFlowRateActuatorName = "AirRSH2OutFlowRateActuator"+myID;
		myAirRSPotableWaterInFlowRateActuatorName = "AirRSPotableWaterInFlowRateActuator"+myID;
		myAirRSPotableWaterOutFlowRateActuatorName = "AirRSPotableWaterOutFlowRateActuator"+myID;
		myModuleNames.add(myAirRSPowerInFlowRateActuatorName);
		myModuleNames.add(myAirRSAirInFlowRateActuatorName);
		myModuleNames.add(myAirRSAirOutFlowRateActuatorName);
		myModuleNames.add(myAirRSO2OutFlowRateActuatorName);
		myModuleNames.add(myAirRSCO2InFlowRateActuatorName);
		myModuleNames.add(myAirRSCO2OutFlowRateActuatorName);
		myModuleNames.add(myAirRSH2InFlowRateActuatorName);
		myModuleNames.add(myAirRSH2OutFlowRateActuatorName);
		myModuleNames.add(myAirRSPotableWaterInFlowRateActuatorName);
		myModuleNames.add(myAirRSPotableWaterOutFlowRateActuatorName);
		myActuatorNames.add(myAirRSPowerInFlowRateActuatorName);
		myActuatorNames.add(myAirRSAirInFlowRateActuatorName);
		myActuatorNames.add(myAirRSAirOutFlowRateActuatorName);
		myActuatorNames.add(myAirRSO2OutFlowRateActuatorName);
		myActuatorNames.add(myAirRSCO2InFlowRateActuatorName);
		myActuatorNames.add(myAirRSCO2OutFlowRateActuatorName);
		myActuatorNames.add(myAirRSH2InFlowRateActuatorName);
		myActuatorNames.add(myAirRSH2OutFlowRateActuatorName);
		myActuatorNames.add(myAirRSPotableWaterInFlowRateActuatorName);
		myActuatorNames.add(myAirRSPotableWaterOutFlowRateActuatorName);
		//Power
		//PowerPS
		myPowerPSPowerOutFlowRateActuatorName = "PowerPSPowerOutFlowRateActuator"+myID;
		myModuleNames.add(myPowerPSPowerOutFlowRateActuatorName);
		myActuatorNames.add(myPowerPSPowerOutFlowRateActuatorName);
		//Water
		//WaterRS
		myWaterRSDirtyWaterInFlowRateActuatorName = "WaterRSDirtyWaterInFlowRateActuator"+myID;
		myWaterRSGreyWaterInFlowRateActuatorName = "WaterRSGreyWaterInFlowRateActuator"+myID;
		myWaterRSPowerInFlowRateActuatorName = "WaterRSPowerInFlowRateActuator"+myID;
		myWaterRSPotableWaterOutFlowRateActuatorName = "WaterRSPotableWaterOutFlowRateActuator"+myID;
		myModuleNames.add(myWaterRSDirtyWaterInFlowRateActuatorName);
		myModuleNames.add(myWaterRSGreyWaterInFlowRateActuatorName);
		myModuleNames.add(myWaterRSPowerInFlowRateActuatorName);
		myModuleNames.add(myWaterRSPotableWaterOutFlowRateActuatorName);
		myActuatorNames.add(myWaterRSDirtyWaterInFlowRateActuatorName);
		myActuatorNames.add(myWaterRSGreyWaterInFlowRateActuatorName);
		myActuatorNames.add(myWaterRSPowerInFlowRateActuatorName);
		myActuatorNames.add(myWaterRSPotableWaterOutFlowRateActuatorName);
		//Crew
		myCrewGroupPotableWaterInFlowRateActuatorName = "CrewGroupPotableWaterInFlowRateActuator"+myID;
		myCrewGroupGreyWaterOutFlowRateActuatorName = "CrewGroupGreyWaterOutFlowRateActuator"+myID;
		myCrewGroupDirtyWaterOutFlowRateActuatorName = "CrewGroupDirtyWaterOutFlowRateActuator"+myID;
		myModuleNames.add(myCrewGroupPotableWaterInFlowRateActuatorName);
		myModuleNames.add(myCrewGroupGreyWaterOutFlowRateActuatorName);
		myModuleNames.add(myCrewGroupDirtyWaterOutFlowRateActuatorName);
		myActuatorNames.add(myCrewGroupPotableWaterInFlowRateActuatorName);
		myActuatorNames.add(myCrewGroupGreyWaterOutFlowRateActuatorName);
		myActuatorNames.add(myCrewGroupDirtyWaterOutFlowRateActuatorName);
		//Food
		//BiomassRS
		myBiomassRSAirInFlowRateActuatorName = "BiomassRSAirInFlowRateActuator"+myID;
		myBiomassRSPowerInFlowRateActuatorName = "BiomassRSPowerInFlowRateActuator"+myID;
		myBiomassRSAirOutFlowRateActuatorName = "BiomassRSAirOutFlowRateActuator"+myID;
		myBiomassRSPotableWaterInFlowRateActuatorName = "BiomassRSPotableWaterInFlowRateActuator"+myID;
		myBiomassRSGreyWaterInFlowRateActuatorName = "BiomassRSGreyWaterInFlowRateActuator"+myID;
		myBiomassRSBiomassOutFlowRateActuatorName = "BiomassRSBiomassOutFlowRateActuator"+myID;
		myModuleNames.add(myBiomassRSAirInFlowRateActuatorName);
		myModuleNames.add(myBiomassRSPowerInFlowRateActuatorName);
		myModuleNames.add(myBiomassRSAirOutFlowRateActuatorName);
		myModuleNames.add(myBiomassRSPotableWaterInFlowRateActuatorName);
		myModuleNames.add(myBiomassRSGreyWaterInFlowRateActuatorName);
		myModuleNames.add(myBiomassRSBiomassOutFlowRateActuatorName);
		myActuatorNames.add(myBiomassRSAirInFlowRateActuatorName);
		myActuatorNames.add(myBiomassRSPowerInFlowRateActuatorName);
		myActuatorNames.add(myBiomassRSAirOutFlowRateActuatorName);
		myActuatorNames.add(myBiomassRSPotableWaterInFlowRateActuatorName);
		myActuatorNames.add(myBiomassRSGreyWaterInFlowRateActuatorName);
		myActuatorNames.add(myBiomassRSBiomassOutFlowRateActuatorName);
		//Food Processor
		myFoodProcessorPowerInFlowRateActuatorName = "FoodProcessorPowerInFlowRateActuator"+myID;
		myFoodProcessorBiomassInFlowRateActuatorName = "FoodProcessorBiomassInFlowRateActuator"+myID;
		myFoodProcessorFoodOutFlowRateActuatorName = "FoodProcessorFoodOutFlowRateActuator"+myID;
		myModuleNames.add(myFoodProcessorPowerInFlowRateActuatorName);
		myModuleNames.add(myFoodProcessorBiomassInFlowRateActuatorName);
		myModuleNames.add(myFoodProcessorFoodOutFlowRateActuatorName);
		myActuatorNames.add(myFoodProcessorPowerInFlowRateActuatorName);
		myActuatorNames.add(myFoodProcessorBiomassInFlowRateActuatorName);
		myActuatorNames.add(myFoodProcessorFoodOutFlowRateActuatorName);
		//Framework
		//Accumulator
		myAccumulatorCO2AirEnvironmentInFlowRateActuatorName = "AccumulatorCO2AirEnvironmentInFlowRateActuator"+myID;
		myAccumulatorO2AirEnvironmentInFlowRateActuatorName = "AccumulatorO2AirEnvironmentInFlowRateActuator"+myID;
		myAccumulatorCO2AirStoreOutFlowRateActuatorName = "AccumulatorCO2AirStoreOutFlowRateActuator"+myID;
		myAccumulatorO2AirStoreOutFlowRateActuatorName = "AccumulatorO2AirStoreOutFlowRateActuator"+myID;
		myModuleNames.add(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName);
		myModuleNames.add(myAccumulatorO2AirEnvironmentInFlowRateActuatorName);
		myModuleNames.add(myAccumulatorCO2AirStoreOutFlowRateActuatorName);
		myModuleNames.add(myAccumulatorO2AirStoreOutFlowRateActuatorName);
		myActuatorNames.add(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName);
		myActuatorNames.add(myAccumulatorO2AirEnvironmentInFlowRateActuatorName);
		myActuatorNames.add(myAccumulatorCO2AirStoreOutFlowRateActuatorName);
		myActuatorNames.add(myAccumulatorO2AirStoreOutFlowRateActuatorName);
		//Injector
		myInjectorCO2AirStoreInFlowRateActuatorName = "InjectorCO2AirStoreInFlowRateActuator"+myID;
		myInjectorO2AirStoreInFlowRateActuatorName = "InjectorO2AirStoreInFlowRateActuator"+myID;
		myInjectorCO2AirEnvironmentOutFlowRateActuatorName = "InjectorCO2AirEnvironmentOutFlowRateActuator"+myID;
		myInjectorO2AirEnvironmentOutFlowRateActuatorName = "InjectorO2AirEnvironmentOutFlowRateActuator"+myID;
		myModuleNames.add(myInjectorCO2AirStoreInFlowRateActuatorName);
		myModuleNames.add(myInjectorO2AirStoreInFlowRateActuatorName);
		myModuleNames.add(myInjectorCO2AirEnvironmentOutFlowRateActuatorName);
		myModuleNames.add(myInjectorO2AirEnvironmentOutFlowRateActuatorName);
		myActuatorNames.add(myInjectorCO2AirStoreInFlowRateActuatorName);
		myActuatorNames.add(myInjectorO2AirStoreInFlowRateActuatorName);
		myActuatorNames.add(myInjectorCO2AirEnvironmentOutFlowRateActuatorName);
		myActuatorNames.add(myInjectorO2AirEnvironmentOutFlowRateActuatorName);

		usedDefaultModules = true;
		checkMachineType();
	}

	/**
	* Constructs the BioDriver
	* @param pModuleNames The module names this BioDriver should tick.
	* @param pID The ID of this instance of the BioSim (must be the same for all modules in the instance)
	*/
	public BioDriverImpl(String[] pModuleNames, int pID){
		myID = pID;
		myModuleNames = Arrays.asList(pModuleNames);
		myLoggerName = "Logger"+myID;
		usedDefaultModules = false;
		initializationToUse = BioDriverInit.NO_INIT;
	}

	/**
	* Returns the name of this instance of BioDriver
	* @return The name of this instance (BioDriver + ID)
	*/
	public String getName(){
		return "BioDriver"+myID;
	}

	/**
	* Attempts to discover the machine type we're running on.  If it's windows, set a driver pause between ticks
	* to keep from starving windows GUI.  Checked by looking at the Java System Property "MACHINE_TYPE"
	*/
	private void checkMachineType(){
		String machineType = null;
		machineType = System.getProperty("MACHINE_TYPE");
		if (machineType != null){
			if (machineType.indexOf("CYGWIN") != -1){
				setDriverPauseLength(5);
			}
			else{
				setDriverPauseLength(0);
			}
		}
		else{
			setDriverPauseLength(0);
		}
	}

	/**
	* Checks to see if the simulation is paused.
	* @return <code>true</code> if paused, <code>false</code> if not
	*/
	public boolean isPaused(){
		return simulationIsPaused;
	}

	/**
	* Checks to see if the simulation has started.
	* @return <code>true</code> if started, <code>false</code> if not
	*/
	public boolean isStarted(){
		return simulationStarted;
	}

	/**
	* Run a simulation on a different thread (paused at first);
	*/
	public void spawnSimulation(){
		runTillDead = false;
		runTillN = false;
		pauseSimulation();
		collectReferences();
		configureSimulation();
		myTickThread = new Thread(this);
		myTickThread.start();
	}

	/**
	* Run a simulation on a different thread and runs continuously (ticks till signalled to end)
	*/
	public void spawnSimulationAndRun(){
		runTillDead = false;
		runTillN = false;
		collectReferences();
		configureSimulation();
		myTickThread = new Thread(this);
		myTickThread.start();
	}

	/**
	* Run a simulation on a different thread and runs continously till the crew dies
	*/
	public void spawnSimulationAndRunTillDead(){
		runTillDead = true;
		collectReferences();
		configureSimulation();
		myTickThread = new Thread(this);
		myTickThread.start();
	}

	/**
	* Run a simulation on a different thread and runs continously till n-Ticks
	* @param pTicks The number of ticks to run the simulation
	*/
	public void spawnSimulationAndRunTillN(int pTicks){
		nTicks = pTicks;
		runTillN = true;
		runTillDead = false;
		collectReferences();
		configureSimulation();
		myTickThread = new Thread(this);
		myTickThread.start();
	}

	/**
	* If n-ticks have been reached or the crew is dead, this method restarts the simulation
	*/
	private void loopSimulation(){
		collectReferences();
		configureSimulation();
		myTickThread = new Thread(this);
		myTickThread.start();
	}

	/**
	* Tells BioDriver which initialization to use
	* @param pInitializationToUse the initialization to use:<br>
	* &nbsp;&nbsp;&nbsp;<code>BioDriverInit.DEFAULT_INIT</code> - default initialization (sets resource flows, initializes buffers, adds crew, etc)<br>
	* &nbsp;&nbsp;&nbsp;<code>BioDriverInit.OPTIMAL_INIT</code> - optimal initialization, same as default, but with bigger buffers, smaller crew, etc<br>
	* &nbsp;&nbsp;&nbsp;<code>BioDriverInit.NONE_INIT</code> - no initialization.  everything must be set up manually. use this if you've reconfigured the simulation
	*/
	public void setInitialization(BioDriverInit pInitializationToUse){
		if (!usedDefaultModules){
			System.err.println("BioDriverImpl:"+myID+" Attempting to change initialization after not using default modules, not allowing!");
			return;
		}
		initializationToUse = pInitializationToUse;
	}

	public void configureSimulation(){
		ticksGoneBy = 0;
		if (initializationToUse == BioDriverInit.DEFAULT_INIT){
			System.out.println("BioDriverImpl:"+myID+" Initializing default simulation...");
			defaultInitialization();
		}
		else if (initializationToUse == BioDriverInit.OPTIMAL_INIT){
			System.out.println("BioDriverImpl:"+myID+" Initializing optimal simulation...");
			optimalInitialization();
		}
		else if (initializationToUse == BioDriverInit.FLOWS_ONLY_INIT){
			System.out.println("BioDriverImpl:"+myID+" Initializing flows only simulation...");
		}
		System.out.println("BioDriverImpl:"+myID+" configuring module flows");
		configureModuleFlows();
		System.out.println("BioDriverImpl:"+myID+" configuring sensor inputs");
		configureSensorsInputs();
		System.out.println("BioDriverImpl:"+myID+" configuring actuator outputs");
		configureActuatorOutputs();
	}

	/**
	* Invoked by the myTickThread.start() method call and necessary to implement Runnable.
	* Sets flag that simulation is running, intializes servers (if applicable), then begins ticking them.
	*/
	public void run(){
		System.out.println("BioDriverImpl:"+myID+" Running simulation...");
		simulationStarted = true;
		runSimulation();
	}

	/**
	* Initializes the various servers with various dummy data.
	* Crew is created, stores are filled, etc.
	*/
	private void defaultInitialization(){
		//reset servers
		reset();
		//Make some crew members
		CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(myCrewGroupName));
		//myCrew.setStochasticIntensity(StochasticIntensity.MEDIUM_STOCH);
		if (!createdCrew){
			CrewPerson myCrewPerson1 = myCrew.createCrewPerson("Bob Roberts", 43, 170, Sex.male);
			CrewPerson myCrewPerson2 = myCrew.createCrewPerson("Stephanie Stevens", 25, 125, Sex.female);
			CrewPerson myCrewPerson3 = myCrew.createCrewPerson("Bill Williams", 30, 165, Sex.male);
			CrewPerson myCrewPerson4 = myCrew.createCrewPerson("Janet Janey", 22, 130, Sex.female);
			createdCrew = true;
		}
		//stagger actvities
		CrewPerson[] myCrewPeople = myCrew.getCrewPeople();
		for (int i = 0; i < myCrewPeople.length; i++)
			myCrewPeople[i].setCurrentActivity(myCrewPeople[i].getScheduledActivityByOrder(i));
		//Fill the clean water stores to the brim (20 liters), and all stores' capacities
		DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(myDirtyWaterStoreName));
		PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(myPotableWaterStoreName));
		GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(myGreyWaterStoreName));
		myDirtyWaterStore.setCapacity(500f);
		myDirtyWaterStore.setLevel(250f);
		myPotableWaterStore.setCapacity(500f);
		myPotableWaterStore.setLevel(500f);
		myGreyWaterStore.setCapacity(500f);
		myGreyWaterStore.setLevel(500f);

		//Fill the air tanks
		CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(myCO2StoreName));
		O2Store myO2Store = O2StoreHelper.narrow(getBioModule(myO2StoreName));
		H2Store myH2Store = H2StoreHelper.narrow(getBioModule(myH2StoreName));
		myCO2Store.setCapacity(1000f);
		myO2Store.setCapacity(1000f);
		myH2Store.setCapacity(5000f);
		myH2Store.setLevel(2000f);
		myCO2Store.setLevel(500f);
		myO2Store.setLevel(250f);

		//Put some air in the crew cabin
		SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(myCrewEnvironmentName));
		float environmentCapacity = 1.54893f * pow(10, 6);
		//float environmentCapacity = 1.54893f * pow(10, 10);
		myCrewEnvironment.setInitialVolumeAtSeaLevel(environmentCapacity);

		//Put some air in the plant cabin
		SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(myPlantEnvironmentName));
		myPlantEnvironment.setInitialVolumeAtSeaLevel(environmentCapacity);

		//Add some crops and food
		BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(myBiomassStoreName));
		FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(myFoodStoreName));
		BiomassRS myBiomassRS = BiomassRSHelper.narrow(getBioModule(myBiomassRSName));
		if (!createdPlants){
			myBiomassRS.createNewShelf(PlantType.WHEAT, 10);
			createdPlants = true;
		}
		myBiomassStore.setCapacity(500f);
		myFoodStore.setCapacity(2000f);
		myBiomassStore.addBioMatter(0.8f, PlantType.TOMATO);
		myBiomassStore.addBioMatter(1.5f, PlantType.WHEAT);
		myBiomassStore.addBioMatter(2.0f, PlantType.SOYBEAN);
		myBiomassStore.addBioMatter(200f, PlantType.RICE);
		myFoodStore.addFoodMatterMass(0.5f, PlantType.DRY_BEAN);
		myFoodStore.addFoodMatterMass(5.0f, PlantType.SWEET_POTATO);
		myFoodStore.addFoodMatterMass(3.8f, PlantType.LETTUCE);
		myFoodStore.addFoodMatterMass(300f, PlantType.PEANUT);

		//Add some power
		PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(myPowerStoreName));
		myPowerStore.setCapacity(10000f);
		myPowerStore.setLevel(10000f);
	}

	private float pow(float a, float b){
		return (new Double(Math.pow(a,b))).floatValue();
	}

	/**
	* Configures the simulation.  By default, 2 environments are used along with one module of everything else.
	*/
	private void configureModuleFlows(){
		BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(myBiomassStoreName));
		PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(myPowerStoreName));
		FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(myFoodStoreName));
		PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(myPotableWaterStoreName));
		DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(myDirtyWaterStoreName));
		GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(myGreyWaterStoreName));
		SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(myCrewEnvironmentName));
		SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(myPlantEnvironmentName));
		O2Store myO2Store = O2StoreHelper.narrow(getBioModule(myO2StoreName));
		CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(myCO2StoreName));
		H2Store myH2Store = H2StoreHelper.narrow(getBioModule(myH2StoreName));

		//Hook up Food Processor to other modules
		{
			BiomassStore[] biomassStoreInput = {myBiomassStore};
			PowerStore[] powerStoreInput = {myPowerStore};
			FoodStore[] foodStoreOutput = {myFoodStore};
			float[] biomassMaxFlowRates = {1f};
			float[] powerMaxFlowRates = {100f};
			float[] foodMaxFlowRates = {1f};
			float[] biomassDesiredFlowRates = {1f};
			float[] powerDesiredFlowRates = {100f};
			float[] foodDesiredFlowRates = {1f};
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(getBioModule(myFoodProcessorName));
			myFoodProcessor.setBiomassInputs(biomassStoreInput, biomassMaxFlowRates, biomassDesiredFlowRates);
			myFoodProcessor.setPowerInputs(powerStoreInput, powerMaxFlowRates, powerDesiredFlowRates);
			myFoodProcessor.setFoodOutputs(foodStoreOutput, foodMaxFlowRates, foodDesiredFlowRates);
		}

		//Hook up BiomassRS to other modules
		{
			PowerStore[] powerStoreInput = {myPowerStore};
			PotableWaterStore[] potableWaterStoreInput = {myPotableWaterStore};
			GreyWaterStore[] greyWaterStoreInput = {myGreyWaterStore};
			BiomassStore[] biomassStoreOutput = {myBiomassStore};
			DirtyWaterStore[] dirtyWaterStoreOutput = {myDirtyWaterStore};
			SimEnvironment[] simEnvironmentInput = {myPlantEnvironment};
			SimEnvironment[] simEnvironmentOutput = {myPlantEnvironment};
			float[] powerMaxFlowRates = {400f};
			float[] potableWaterMaxFlowRates = {100f};
			float[] greyWaterMaxFlowRates = {100f};
			float[] biomassMaxFlowRates = {100f};
			float[] simEnvironmentInputMaxFlowRates = {0f};
			float[] simEnvironmentOutputMaxFlowRates = {0f};
			float[] dirtyWaterOutputMaxFlowRates = {100f};
			float[] powerDesiredFlowRates = {400f};
			float[] potableWaterDesiredFlowRates = {100f};
			float[] greyWaterDesiredFlowRates = {100f};
			float[] dirtyWaterOutputDesiredFlowRates = {100f};
			float[] biomassDesiredFlowRates = {100f};
			float[] simEnvironmentInputDesiredFlowRates = {0f};
			float[] simEnvironmentOutputDesiredFlowRates = {0f};
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(getBioModule(myBiomassRSName));
			myBiomassRS.setPowerInputs(powerStoreInput, powerMaxFlowRates, powerDesiredFlowRates);
			myBiomassRS.setPotableWaterInputs(potableWaterStoreInput, potableWaterMaxFlowRates, potableWaterDesiredFlowRates);
			myBiomassRS.setGreyWaterInputs(greyWaterStoreInput, greyWaterMaxFlowRates, greyWaterDesiredFlowRates);
			myBiomassRS.setBiomassOutputs(biomassStoreOutput, biomassMaxFlowRates, biomassDesiredFlowRates);
			myBiomassRS.setAirInputs(simEnvironmentInput, simEnvironmentInputMaxFlowRates, simEnvironmentInputDesiredFlowRates);
			myBiomassRS.setAirOutputs(simEnvironmentOutput, simEnvironmentOutputMaxFlowRates, simEnvironmentOutputDesiredFlowRates);
			myBiomassRS.setDirtyWaterOutputs(dirtyWaterStoreOutput, dirtyWaterOutputMaxFlowRates, dirtyWaterOutputDesiredFlowRates);
		}

		//Hook up Air RS to other modules
		{
			PowerStore[] powerStoreInput = {myPowerStore};
			SimEnvironment[] simEnvironmentInput = {myCrewEnvironment};
			SimEnvironment[] simEnvironmentOutput = {myCrewEnvironment};
			O2Store[] O2StoreOutput = {myO2Store};
			CO2Store[] CO2StoreOutput = {myCO2Store};
			CO2Store[] CO2StoreInput = {myCO2Store};
			H2Store[] H2StoreOutput = {myH2Store};
			H2Store[] H2StoreInput = {myH2Store};
			PotableWaterStore[] potableWaterStoreOutput = {myPotableWaterStore};
			PotableWaterStore[] potableWaterStoreInput = {myPotableWaterStore};
			float[] powerMaxFlowRates = {300f};
			float[] simEnvironmentInputMaxFlowRates = {100f};
			float[] simEnvironmentOutputMaxFlowRates = {100f};
			float[] O2StoreMaxFlowRates = {100f};
			float[] CO2StoreInputMaxFlowRates = {100f};
			float[] CO2StoreOutputMaxFlowRates = {100f};
			float[] H2StoreInputMaxFlowRates = {100f};
			float[] H2StoreOutputMaxFlowRates = {100f};
			float[] powerDesiredFlowRates = {300f};
			float[] simEnvironmentInputDesiredFlowRates = {100f};
			float[] simEnvironmentOutputDesiredFlowRates = {100f};
			float[] O2StoreDesiredFlowRates = {100f};
			float[] CO2StoreInputDesiredFlowRates = {100f};
			float[] CO2StoreOutputDesiredFlowRates = {100f};
			float[] H2StoreInputDesiredFlowRates = {100f};
			float[] H2StoreOutputDesiredFlowRates = {100f};
			float[] potableWaterStoreInputDesiredFlowRates = {100f};
			float[] potableWaterStoreOutputDesiredFlowRates = {100f};
			float[] potableWaterStoreInputMaxFlowRates = {100f};
			float[] potableWaterStoreOutputMaxFlowRates = {100f};
			AirRS myAirRS = AirRSHelper.narrow(getBioModule(myAirRSName));
			myAirRS.setPowerInputs(powerStoreInput, powerMaxFlowRates, powerDesiredFlowRates);
			myAirRS.setAirInputs(simEnvironmentInput, simEnvironmentInputMaxFlowRates, simEnvironmentInputDesiredFlowRates);
			myAirRS.setAirOutputs(simEnvironmentOutput, simEnvironmentOutputMaxFlowRates, simEnvironmentOutputDesiredFlowRates);
			myAirRS.setO2Outputs(O2StoreOutput, O2StoreMaxFlowRates, O2StoreDesiredFlowRates);
			myAirRS.setCO2Outputs(CO2StoreOutput, CO2StoreOutputMaxFlowRates, CO2StoreInputDesiredFlowRates);
			myAirRS.setCO2Inputs(CO2StoreInput, CO2StoreInputMaxFlowRates, CO2StoreOutputDesiredFlowRates);
			myAirRS.setH2Outputs(H2StoreOutput, H2StoreOutputMaxFlowRates, H2StoreInputDesiredFlowRates);
			myAirRS.setH2Inputs(H2StoreInput, H2StoreInputMaxFlowRates, H2StoreOutputDesiredFlowRates);
			myAirRS.setPotableWaterOutputs(potableWaterStoreOutput, potableWaterStoreOutputMaxFlowRates, potableWaterStoreOutputDesiredFlowRates);
			myAirRS.setPotableWaterInputs(potableWaterStoreInput, potableWaterStoreInputMaxFlowRates, potableWaterStoreInputDesiredFlowRates);
		
		}

		//Hook up Crew to other modules
		{
			SimEnvironment[] airInputs = {myCrewEnvironment};
			SimEnvironment[] airOutputs = {myCrewEnvironment};
			PotableWaterStore[] potableWaterInput = {myPotableWaterStore};
			FoodStore[] foodInputs = {myFoodStore};
			GreyWaterStore[] greyWaterOutputs = {myGreyWaterStore};
			DirtyWaterStore[] dirtyWaterOutputs = {myDirtyWaterStore};
			float[] airInputMaxFlowRates = {0f};
			float[] airOutputMaxFlowRates = {0f};
			float[] foodInputMaxFlowRates = {2000f};
			float[] potableWaterInputMaxFlowRates = {100f};
			float[] dirtyWaterOutputMaxFlowRates = {100f};
			float[] greyWaterOutputMaxFlowRates = {100f};
			float[] airInputDesiredFlowRates = {0f};
			float[] airOutputDesiredFlowRates = {0f};
			float[] foodInputDesiredFlowRates = {1000f};
			float[] potableWaterInputDesiredFlowRates = {100f};
			float[] dirtyWaterOutputDesiredFlowRates = {100f};
			float[] greyWaterOutputDesiredFlowRates = {100f};
			CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(myCrewGroupName));
			myCrew.setAirInputs(airInputs, airInputMaxFlowRates, airInputDesiredFlowRates);
			myCrew.setAirOutputs(airOutputs, airOutputMaxFlowRates, airOutputDesiredFlowRates);
			myCrew.setFoodInputs(foodInputs, foodInputMaxFlowRates, foodInputDesiredFlowRates);
			myCrew.setPotableWaterInputs(potableWaterInput, potableWaterInputMaxFlowRates, potableWaterInputDesiredFlowRates);
			myCrew.setDirtyWaterOutputs(dirtyWaterOutputs, dirtyWaterOutputMaxFlowRates, dirtyWaterOutputDesiredFlowRates);
			myCrew.setGreyWaterOutputs(greyWaterOutputs, greyWaterOutputMaxFlowRates, greyWaterOutputDesiredFlowRates);
		}

		//Hook up Water to other modules
		{
			PotableWaterStore[] potableWaterOutputs = {myPotableWaterStore};
			GreyWaterStore[] greyWaterInputs = {myGreyWaterStore};
			DirtyWaterStore[] dirtyWaterInputs = {myDirtyWaterStore};
			PowerStore[] powerInput = {myPowerStore};
			float[] potableWaterOutputMaxFlowRates = {100f};
			float[] greyWaterInputMaxFlowRates = {100};
			float[] dirtyWaterInputMaxFlowRates = {100f};
			float[] powerInputMaxFlowRates = {500f};
			float[] potableWaterOutputDesiredFlowRates = {100f};
			float[] greyWaterInputDesiredFlowRates = {100f};
			float[] dirtyWaterInputDesiredFlowRates = {100f};
			float[] powerInputDesiredFlowRates = {500f};
			WaterRS myWaterRS = WaterRSHelper.narrow(getBioModule(myWaterRSName));
			myWaterRS.setPotableWaterOutputs(potableWaterOutputs, potableWaterOutputMaxFlowRates, potableWaterOutputDesiredFlowRates);
			myWaterRS.setGreyWaterInputs(greyWaterInputs, greyWaterInputMaxFlowRates, greyWaterInputDesiredFlowRates);
			myWaterRS.setDirtyWaterInputs(dirtyWaterInputs, dirtyWaterInputMaxFlowRates, dirtyWaterInputDesiredFlowRates);
			myWaterRS.setPowerInputs(powerInput, powerInputMaxFlowRates, powerInputDesiredFlowRates);
		}

		//Hook up Power to other modules
		{
			PowerStore[] powerOutput = {myPowerStore};
			SimEnvironment lightInput = myCrewEnvironment;
			float[] powerOuputsMaxFlowRates = {10000f};
			float[] powerOuputsDesiredFlowRates = {10000f};
			PowerPS myPowerPS = PowerPSHelper.narrow(getBioModule(myPowerPSName));
			myPowerPS.setPowerOutputs(powerOutput, powerOuputsMaxFlowRates, powerOuputsDesiredFlowRates);
			myPowerPS.setLightInput(lightInput);
		}

		//Hook up Accumulator to other modules
		{
			//Accumulate O2 from plant environment, put it in O2 store
			SimEnvironment[] O2AirInput = {myPlantEnvironment};
			O2Store[] O2AirOutput = {myO2Store};
			//2.5 slpm (standard liters per minute) used for optimum in phase 3, fan spin capable of 3x this speed (WAG)
			//so 7.5 slpm for all flow rates
			// (7.5 liters O2 / min)(1000 grams/liters)(1 mole O2/ 31.9988 grams O2)(60 min/ hour)
			// 7.5 O2 slpm == 14063.0 moles per hour
			float[] O2AirInputMaxFlowRates = {14063f};
			float[] O2AirOutputMaxFlowRates = {14063f};
			float[] O2AirInputDesiredFlowRates = {10f};
			float[] O2AirOutputDesiredFlowRates = {10f};
			Accumulator myAccumulator = AccumulatorHelper.narrow(getBioModule(myAccumulatorName));
			myAccumulator.setO2AirEnvironmentInputs(O2AirInput, O2AirInputMaxFlowRates, O2AirInputDesiredFlowRates);
			myAccumulator.setO2AirStoreOutputs(O2AirOutput, O2AirOutputMaxFlowRates, O2AirOutputDesiredFlowRates);

			//Accumulate CO2 from crew environment, put it in CO2 store
			// (7.5 liters CO2 / min)(1000 grams/liters)(1 mole CO2/ 40.0098 grams CO2)(60 min/ hour)
			// 7.5 CO2 slpm == 11247.2 moles per hour
			SimEnvironment[] CO2AirInput = {myCrewEnvironment};
			CO2Store[] CO2AirOutput = {myCO2Store};
			float[] CO2AirInputMaxFlowRates = {14063f};
			float[] CO2AirOutputMaxFlowRates = {14063f};
			float[] CO2AirInputDesiredFlowRates = {10f};
			float[] CO2AirOutputDesiredFlowRates = {10f};
			myAccumulator.setCO2AirEnvironmentInputs(CO2AirInput, CO2AirInputMaxFlowRates, CO2AirInputDesiredFlowRates);
			myAccumulator.setCO2AirStoreOutputs(CO2AirOutput, CO2AirOutputMaxFlowRates, CO2AirOutputDesiredFlowRates);
		}

		//Hook up Injector to other modules
		{
			//Take O2 from store, inject it into crew environment
			O2Store[] O2AirInput = {myO2Store};
			SimEnvironment[] O2AirOutput = {myCrewEnvironment};
			//2.5 slpm (standard liters per minute) used for optimum in phase 3, fan spin capable of 3x this speed (WAG)
			//so 7.5 slpm for all flow rates
			// (7.5 liters O2 / min)(1000 grams/liters)(1 mole O2/ 31.9988 grams O2)(60 min/ hour)
			// 7.5 O2 slpm == 14063.0 moles per hour
			float[] O2AirInputMaxFlowRates = {14063f};
			float[] O2AirOutputMaxFlowRates = {14063f};
			float[] O2AirInputDesiredFlowRates = {10f};
			float[] O2AirOutputDesiredFlowRates = {10f};
			Injector myInjector = InjectorHelper.narrow(getBioModule(myInjectorName));
			myInjector.setO2AirStoreInputs(O2AirInput, O2AirInputMaxFlowRates, O2AirInputDesiredFlowRates);
			myInjector.setO2AirEnvironmentOutputs(O2AirOutput, O2AirOutputMaxFlowRates, O2AirOutputDesiredFlowRates);

			//Take CO2 from store, inject it into plant environment
			// (7.5 liters CO2 / min)(1000 grams/liters)(1 mole CO2/ 40.0098 grams CO2)(60 min/ hour)
			// 7.5 CO2 slpm == 11247.2 moles per hour
			CO2Store[] CO2AirInput = {myCO2Store};
			SimEnvironment[] CO2AirOutput = {myPlantEnvironment};
			float[] CO2AirInputMaxFlowRates = {14063f};
			float[] CO2AirOutputMaxFlowRates = {14063f};
			float[] CO2AirInputDesiredFlowRates = {10f};
			float[] CO2AirOutputDesiredFlowRates = {10f};
			myInjector.setCO2AirStoreInputs(CO2AirInput, CO2AirInputMaxFlowRates, CO2AirInputDesiredFlowRates);
			myInjector.setCO2AirEnvironmentOutputs(CO2AirOutput, CO2AirOutputMaxFlowRates, CO2AirOutputDesiredFlowRates);
		}
	}

	private void configureSensorsInputs(){
		//
		//Air
		//
		{
			//AirRS
			{

				AirRS myAirRS = AirRSHelper.narrow(getBioModule(myAirRSName));
				PowerInFlowRateSensor myAirRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(getBioModule(myAirRSPowerInFlowRateSensorName));
				PotableWaterInFlowRateSensor myAirRSPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper.narrow(getBioModule(myAirRSPotableWaterInFlowRateSensorName));
				PotableWaterOutFlowRateSensor myAirRSPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper.narrow(getBioModule(myAirRSPotableWaterOutFlowRateSensorName));
				AirInFlowRateSensor myAirRSAirInFlowRateSensor = AirInFlowRateSensorHelper.narrow(getBioModule(myAirRSAirInFlowRateSensorName));
				AirOutFlowRateSensor myAirRSAirOutFlowRateSensor = AirOutFlowRateSensorHelper.narrow(getBioModule(myAirRSAirOutFlowRateSensorName));
				O2OutFlowRateSensor myAirRSO2OutFlowRateSensor = O2OutFlowRateSensorHelper.narrow(getBioModule(myAirRSO2OutFlowRateSensorName));
				CO2InFlowRateSensor myAirRSCO2InFlowRateSensor = CO2InFlowRateSensorHelper.narrow(getBioModule(myAirRSCO2InFlowRateSensorName));
				CO2OutFlowRateSensor myAirRSCO2OutFlowRateSensor = CO2OutFlowRateSensorHelper.narrow(getBioModule(myAirRSCO2OutFlowRateSensorName));
				H2InFlowRateSensor myAirRSH2InFlowRateSensor = H2InFlowRateSensorHelper.narrow(getBioModule(myAirRSH2InFlowRateSensorName));
				H2OutFlowRateSensor myAirRSH2OutFlowRateSensor = H2OutFlowRateSensorHelper.narrow(getBioModule(myAirRSH2OutFlowRateSensorName));
				myAirRSPotableWaterInFlowRateSensor.setInput(myAirRS, 0);
				myAirRSPotableWaterOutFlowRateSensor.setInput(myAirRS, 0);
				myAirRSPowerInFlowRateSensor.setInput(myAirRS, 0);
				myAirRSAirInFlowRateSensor.setInput(myAirRS, 0);
				myAirRSAirOutFlowRateSensor.setInput(myAirRS, 0);
				myAirRSO2OutFlowRateSensor.setInput(myAirRS, 0);
				myAirRSCO2InFlowRateSensor.setInput(myAirRS, 0);
				myAirRSCO2OutFlowRateSensor.setInput(myAirRS, 0);
				myAirRSH2InFlowRateSensor.setInput(myAirRS, 0);
				myAirRSH2OutFlowRateSensor.setInput(myAirRS, 0);
			}
			//Store
			{

				O2Store myO2Store = O2StoreHelper.narrow(getBioModule(myO2StoreName));
				CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(myCO2StoreName));
				H2Store myH2Store = H2StoreHelper.narrow(getBioModule(myH2StoreName));
				O2StoreLevelSensor myO2StoreLevelSensor = O2StoreLevelSensorHelper.narrow(getBioModule(myO2StoreLevelSensorName));
				H2StoreLevelSensor myH2StoreLevelSensor = H2StoreLevelSensorHelper.narrow(getBioModule(myH2StoreLevelSensorName));
				CO2StoreLevelSensor myCO2StoreLevelSensor = CO2StoreLevelSensorHelper.narrow(getBioModule(myCO2StoreLevelSensorName));
				myO2StoreLevelSensor.setInput(myO2Store);
				myCO2StoreLevelSensor.setInput(myCO2Store);
				myH2StoreLevelSensor.setInput(myH2Store);
			}
		}

		//
		//Power
		//
		{
			//PowerPS
			{

				PowerPS myPowerPS = PowerPSHelper.narrow(getBioModule(myPowerPSName));
				PowerOutFlowRateSensor myPowerPSPowerOutFlowRateSensor = PowerOutFlowRateSensorHelper.narrow(getBioModule(myPowerPSPowerOutFlowRateSensorName));
				myPowerPSPowerOutFlowRateSensor.setInput(myPowerPS, 0);
			}
			//Stores
			{
				PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(myPowerStoreName));
				PowerStoreLevelSensor myPowerStoreLevelSensor = PowerStoreLevelSensorHelper.narrow(getBioModule(myPowerStoreLevelSensorName));
				myPowerStoreLevelSensor.setInput(myPowerStore);
			}
		}

		//
		//Crew
		//
		{
			CrewGroup myCrewGroup = CrewGroupHelper.narrow(getBioModule(myCrewGroupName));
			CrewGroupDeathSensor myCrewGroupDeathSensor = CrewGroupDeathSensorHelper.narrow(getBioModule(myCrewGroupDeathSensorName));
			PotableWaterInFlowRateSensor myCrewGroupPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper.narrow(getBioModule(myCrewGroupPotableWaterInFlowRateSensorName));
			GreyWaterOutFlowRateSensor myCrewGroupGreyWaterOutFlowRateSensor = GreyWaterOutFlowRateSensorHelper.narrow(getBioModule(myCrewGroupGreyWaterOutFlowRateSensorName));
			DirtyWaterOutFlowRateSensor myCrewGroupDirtyWaterOutFlowRateSensor = DirtyWaterOutFlowRateSensorHelper.narrow(getBioModule(myCrewGroupDirtyWaterOutFlowRateSensorName));
			myCrewGroupDeathSensor.setInput(myCrewGroup);
			myCrewGroupPotableWaterInFlowRateSensor.setInput(myCrewGroup, 0);
			myCrewGroupGreyWaterOutFlowRateSensor.setInput(myCrewGroup, 0);
			myCrewGroupDirtyWaterOutFlowRateSensor.setInput(myCrewGroup, 0);
		}

		//
		//Environment
		//
		{
			//Crew
			{
				SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(myCrewEnvironmentName));
				O2AirConcentrationSensor myCrewEnvironmentO2AirConcentrationSensor = O2AirConcentrationSensorHelper.narrow(getBioModule(myCrewEnvironmentO2AirConcentrationSensorName));
				CO2AirConcentrationSensor myCrewEnvironmentCO2AirConcentrationSensor = CO2AirConcentrationSensorHelper.narrow(getBioModule(myCrewEnvironmentCO2AirConcentrationSensorName));
				OtherAirConcentrationSensor myCrewEnvironmentOtherAirConcentrationSensor = OtherAirConcentrationSensorHelper.narrow(getBioModule(myCrewEnvironmentOtherAirConcentrationSensorName));
				WaterAirConcentrationSensor myCrewEnvironmentWaterAirConcentrationSensor = WaterAirConcentrationSensorHelper.narrow(getBioModule(myCrewEnvironmentWaterAirConcentrationSensorName));
				O2AirPressureSensor myCrewEnvironmentO2AirPressureSensor = O2AirPressureSensorHelper.narrow(getBioModule(myCrewEnvironmentO2AirPressureSensorName));
				CO2AirPressureSensor myCrewEnvironmentCO2AirPressureSensor = CO2AirPressureSensorHelper.narrow(getBioModule(myCrewEnvironmentCO2AirPressureSensorName));
				OtherAirPressureSensor myCrewEnvironmentOtherAirPressureSensor = OtherAirPressureSensorHelper.narrow(getBioModule(myCrewEnvironmentOtherAirPressureSensorName));
				WaterAirPressureSensor myCrewEnvironmentWaterAirPressureSensor = WaterAirPressureSensorHelper.narrow(getBioModule(myCrewEnvironmentWaterAirPressureSensorName));
				myCrewEnvironmentO2AirConcentrationSensor.setInput(myCrewEnvironment);
				myCrewEnvironmentCO2AirConcentrationSensor.setInput(myCrewEnvironment);
				myCrewEnvironmentOtherAirConcentrationSensor.setInput(myCrewEnvironment);
				myCrewEnvironmentWaterAirConcentrationSensor.setInput(myCrewEnvironment);
				myCrewEnvironmentO2AirPressureSensor.setInput(myCrewEnvironment);
				myCrewEnvironmentCO2AirPressureSensor.setInput(myCrewEnvironment);
				myCrewEnvironmentOtherAirPressureSensor.setInput(myCrewEnvironment);
				myCrewEnvironmentWaterAirPressureSensor.setInput(myCrewEnvironment);
			
			}
			//Plant
			{
				SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(myPlantEnvironmentName));
				O2AirConcentrationSensor myPlantEnvironmentO2AirConcentrationSensor = O2AirConcentrationSensorHelper.narrow(getBioModule(myPlantEnvironmentO2AirConcentrationSensorName));
				CO2AirConcentrationSensor myPlantEnvironmentCO2AirConcentrationSensor = CO2AirConcentrationSensorHelper.narrow(getBioModule(myPlantEnvironmentCO2AirConcentrationSensorName));
				OtherAirConcentrationSensor myPlantEnvironmentOtherAirConcentrationSensor = OtherAirConcentrationSensorHelper.narrow(getBioModule(myPlantEnvironmentOtherAirConcentrationSensorName));
				WaterAirConcentrationSensor myPlantEnvironmentWaterAirConcentrationSensor = WaterAirConcentrationSensorHelper.narrow(getBioModule(myPlantEnvironmentWaterAirConcentrationSensorName));
				O2AirPressureSensor myPlantEnvironmentO2AirPressureSensor = O2AirPressureSensorHelper.narrow(getBioModule(myPlantEnvironmentO2AirPressureSensorName));
				CO2AirPressureSensor myPlantEnvironmentCO2AirPressureSensor = CO2AirPressureSensorHelper.narrow(getBioModule(myPlantEnvironmentCO2AirPressureSensorName));
				OtherAirPressureSensor myPlantEnvironmentOtherAirPressureSensor = OtherAirPressureSensorHelper.narrow(getBioModule(myPlantEnvironmentOtherAirPressureSensorName));
				WaterAirPressureSensor myPlantEnvironmentWaterAirPressureSensor = WaterAirPressureSensorHelper.narrow(getBioModule(myPlantEnvironmentWaterAirPressureSensorName));
				myPlantEnvironmentO2AirConcentrationSensor.setInput(myPlantEnvironment);
				myPlantEnvironmentCO2AirConcentrationSensor.setInput(myPlantEnvironment);
				myPlantEnvironmentOtherAirConcentrationSensor.setInput(myPlantEnvironment);
				myPlantEnvironmentWaterAirConcentrationSensor.setInput(myPlantEnvironment);
				myPlantEnvironmentO2AirPressureSensor.setInput(myPlantEnvironment);
				myPlantEnvironmentCO2AirPressureSensor.setInput(myPlantEnvironment);
				myPlantEnvironmentOtherAirPressureSensor.setInput(myPlantEnvironment);
				myPlantEnvironmentWaterAirPressureSensor.setInput(myPlantEnvironment);
			}
		}
		//
		//Food
		//
		{
			//BiomassRS
			{
				BiomassRS myBiomassRS = BiomassRSHelper.narrow(getBioModule(myBiomassRSName));
				AirInFlowRateSensor myBiomassRSAirInFlowRateSensor = AirInFlowRateSensorHelper.narrow(getBioModule(myBiomassRSAirInFlowRateSensorName));
				AirOutFlowRateSensor myBiomassRSAirOutFlowRateSensor = AirOutFlowRateSensorHelper.narrow(getBioModule(myBiomassRSAirOutFlowRateSensorName));
				PotableWaterInFlowRateSensor myBiomassRSPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper.narrow(getBioModule(myBiomassRSPotableWaterInFlowRateSensorName));
				GreyWaterInFlowRateSensor myBiomassRSGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper.narrow(getBioModule(myBiomassRSGreyWaterInFlowRateSensorName));
				DirtyWaterOutFlowRateSensor myBiomassRSDirtyWaterOutFlowRateSensor = DirtyWaterOutFlowRateSensorHelper.narrow(getBioModule(myBiomassRSDirtyWaterOutFlowRateSensorName));
				BiomassOutFlowRateSensor myBiomassRSBiomassOutFlowRateSensor = BiomassOutFlowRateSensorHelper.narrow(getBioModule(myBiomassRSBiomassOutFlowRateSensorName));
				PowerInFlowRateSensor myBiomassRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(getBioModule(myBiomassRSPowerInFlowRateSensorName));
				myBiomassRSGreyWaterInFlowRateSensor.setInput(myBiomassRS, 0);
				myBiomassRSDirtyWaterOutFlowRateSensor.setInput(myBiomassRS, 0);
				myBiomassRSPotableWaterInFlowRateSensor.setInput(myBiomassRS, 0);
				myBiomassRSAirOutFlowRateSensor.setInput(myBiomassRS, 0);
				myBiomassRSAirInFlowRateSensor.setInput(myBiomassRS, 0);
				myBiomassRSBiomassOutFlowRateSensor.setInput(myBiomassRS, 0);
				myBiomassRSPowerInFlowRateSensor.setInput(myBiomassRS, 0);
			}
			//FoodProcessor
			{
				FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(getBioModule(myFoodProcessorName));
				BiomassInFlowRateSensor myFoodProcessorBiomassInFlowRateSensor = BiomassInFlowRateSensorHelper.narrow(getBioModule(myFoodProcessorBiomassInFlowRateSensorName));
				FoodOutFlowRateSensor myFoodProcessorFoodOutFlowRateSensor = FoodOutFlowRateSensorHelper.narrow(getBioModule(myFoodProcessorFoodOutFlowRateSensorName));
				PowerInFlowRateSensor myFoodProcessorPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(getBioModule(myFoodProcessorPowerInFlowRateSensorName));
				myFoodProcessorPowerInFlowRateSensor.setInput(myFoodProcessor, 0);
				myFoodProcessorFoodOutFlowRateSensor.setInput(myFoodProcessor, 0);
				myFoodProcessorBiomassInFlowRateSensor.setInput(myFoodProcessor, 0);
			}
			//Stores
			{
				FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(myFoodStoreName));
				BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(myBiomassStoreName));
				BiomassStoreLevelSensor myBiomassStoreLevelSensor = BiomassStoreLevelSensorHelper.narrow(getBioModule(myBiomassStoreLevelSensorName));
				FoodStoreLevelSensor myFoodStoreLevelSensor = FoodStoreLevelSensorHelper.narrow(getBioModule(myFoodStoreLevelSensorName));
				myFoodStoreLevelSensor.setInput(myFoodStore);
				myBiomassStoreLevelSensor.setInput(myBiomassStore);
			}
		}

		//
		//Water
		//
		{
			//WaterRS
			{
				WaterRS myWaterRS = WaterRSHelper.narrow(getBioModule(myWaterRSName));
				GreyWaterInFlowRateSensor myWaterRSGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper.narrow(getBioModule(myWaterRSGreyWaterInFlowRateSensorName));
				DirtyWaterInFlowRateSensor myWaterRSDirtyWaterInFlowRateSensor = DirtyWaterInFlowRateSensorHelper.narrow(getBioModule(myWaterRSDirtyWaterInFlowRateSensorName));
				PotableWaterOutFlowRateSensor myWaterRSPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper.narrow(getBioModule(myWaterRSPotableWaterOutFlowRateSensorName));
				PowerInFlowRateSensor myWaterRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(getBioModule(myWaterRSPowerInFlowRateSensorName));
				myWaterRSPotableWaterOutFlowRateSensor.setInput(myWaterRS, 0);
				myWaterRSDirtyWaterInFlowRateSensor.setInput(myWaterRS, 0);
				myWaterRSGreyWaterInFlowRateSensor.setInput(myWaterRS, 0);
				myWaterRSPowerInFlowRateSensor.setInput(myWaterRS, 0);
			}
			//Stores
			{
				PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(myPotableWaterStoreName));
				DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(myDirtyWaterStoreName));
				GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(myGreyWaterStoreName));
				PotableWaterStoreLevelSensor myPotableWaterStoreLevelSensor = PotableWaterStoreLevelSensorHelper.narrow(getBioModule(myPotableWaterStoreLevelSensorName));
				GreyWaterStoreLevelSensor myGreyWaterStoreLevelSensor = GreyWaterStoreLevelSensorHelper.narrow(getBioModule(myGreyWaterStoreLevelSensorName));
				DirtyWaterStoreLevelSensor myDirtyWaterStoreLevelSensor = DirtyWaterStoreLevelSensorHelper.narrow(getBioModule(myDirtyWaterStoreLevelSensorName));
				myPotableWaterStoreLevelSensor.setInput(myPotableWaterStore);
				myGreyWaterStoreLevelSensor.setInput(myGreyWaterStore);
				myDirtyWaterStoreLevelSensor.setInput(myDirtyWaterStore);
			}
		}

		//
		//Framework
		//
		{
			//Accumulator
			{

				Accumulator myAccumulator = AccumulatorHelper.narrow(getBioModule(myAccumulatorName));
				CO2AirStoreOutFlowRateSensor myAccumulatorCO2AirStoreOutFlowRateSensor = CO2AirStoreOutFlowRateSensorHelper.narrow(getBioModule(myAccumulatorCO2AirStoreOutFlowRateSensorName));
				CO2AirEnvironmentInFlowRateSensor myAccumulatorCO2AirEnvironmentInFlowRateSensor = CO2AirEnvironmentInFlowRateSensorHelper.narrow(getBioModule(myAccumulatorCO2AirEnvironmentInFlowRateSensorName));
				O2AirEnvironmentInFlowRateSensor myAccumulatorO2AirEnvironmentInFlowRateSensor = O2AirEnvironmentInFlowRateSensorHelper.narrow(getBioModule(myAccumulatorO2AirEnvironmentInFlowRateSensorName));
				O2AirStoreOutFlowRateSensor myAccumulatorO2AirStoreOutFlowRateSensor = O2AirStoreOutFlowRateSensorHelper.narrow(getBioModule(myAccumulatorO2AirStoreOutFlowRateSensorName));
				myAccumulatorCO2AirEnvironmentInFlowRateSensor.setInput(myAccumulator, 0);
				myAccumulatorO2AirEnvironmentInFlowRateSensor.setInput(myAccumulator, 0);
				myAccumulatorO2AirStoreOutFlowRateSensor.setInput(myAccumulator, 0);
				myAccumulatorCO2AirStoreOutFlowRateSensor.setInput(myAccumulator, 0);
			}
			//Injector
			{
				Injector myInjector = InjectorHelper.narrow(getBioModule(myInjectorName));
				CO2AirStoreInFlowRateSensor myInjectorCO2AirStoreInFlowRateSensor = CO2AirStoreInFlowRateSensorHelper.narrow(getBioModule(myInjectorCO2AirStoreInFlowRateSensorName));
				O2AirStoreInFlowRateSensor myInjectorO2AirStoreInFlowRateSensor = O2AirStoreInFlowRateSensorHelper.narrow(getBioModule(myInjectorO2AirStoreInFlowRateSensorName));
				O2AirEnvironmentOutFlowRateSensor myInjectorO2AirEnvironmentOutFlowRateSensor = O2AirEnvironmentOutFlowRateSensorHelper.narrow(getBioModule(myInjectorO2AirEnvironmentOutFlowRateSensorName));
				CO2AirEnvironmentOutFlowRateSensor myInjectorCO2AirEnvironmentOutFlowRateSensor = CO2AirEnvironmentOutFlowRateSensorHelper.narrow(getBioModule(myInjectorCO2AirEnvironmentOutFlowRateSensorName));
				myInjectorCO2AirStoreInFlowRateSensor.setInput(myInjector, 0);
				myInjectorO2AirStoreInFlowRateSensor.setInput(myInjector, 0);
				myInjectorCO2AirEnvironmentOutFlowRateSensor.setInput(myInjector, 0);
				myInjectorO2AirEnvironmentOutFlowRateSensor.setInput(myInjector, 0);
			}
		}
	}

	private void configureActuatorOutputs(){
		//
		//Air
		//
		{
			//AirRS
			{

				AirRS myAirRS = AirRSHelper.narrow(getBioModule(myAirRSName));
				PowerInFlowRateActuator myAirRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(getBioModule(myAirRSPowerInFlowRateActuatorName));
				PotableWaterInFlowRateActuator myAirRSPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper.narrow(getBioModule(myAirRSPotableWaterInFlowRateActuatorName));
				PotableWaterOutFlowRateActuator myAirRSPotableWaterOutFlowRateActuator = PotableWaterOutFlowRateActuatorHelper.narrow(getBioModule(myAirRSPotableWaterOutFlowRateActuatorName));
				AirInFlowRateActuator myAirRSAirInFlowRateActuator = AirInFlowRateActuatorHelper.narrow(getBioModule(myAirRSAirInFlowRateActuatorName));
				AirOutFlowRateActuator myAirRSAirOutFlowRateActuator = AirOutFlowRateActuatorHelper.narrow(getBioModule(myAirRSAirOutFlowRateActuatorName));
				O2OutFlowRateActuator myAirRSO2OutFlowRateActuator = O2OutFlowRateActuatorHelper.narrow(getBioModule(myAirRSO2OutFlowRateActuatorName));
				CO2InFlowRateActuator myAirRSCO2InFlowRateActuator = CO2InFlowRateActuatorHelper.narrow(getBioModule(myAirRSCO2InFlowRateActuatorName));
				CO2OutFlowRateActuator myAirRSCO2OutFlowRateActuator = CO2OutFlowRateActuatorHelper.narrow(getBioModule(myAirRSCO2OutFlowRateActuatorName));
				H2InFlowRateActuator myAirRSH2InFlowRateActuator = H2InFlowRateActuatorHelper.narrow(getBioModule(myAirRSH2InFlowRateActuatorName));
				H2OutFlowRateActuator myAirRSH2OutFlowRateActuator = H2OutFlowRateActuatorHelper.narrow(getBioModule(myAirRSH2OutFlowRateActuatorName));
				myAirRSPowerInFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSAirInFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSAirOutFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSO2OutFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSCO2InFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSCO2OutFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSH2InFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSH2OutFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSPotableWaterOutFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSPotableWaterInFlowRateActuator.setOutput(myAirRS, 0);
			}
		}

		//
		//Power
		//
		{
			//PowerPS
			{

				PowerPS myPowerPS = PowerPSHelper.narrow(getBioModule(myPowerPSName));
				PowerOutFlowRateActuator myPowerPSPowerOutFlowRateActuator = PowerOutFlowRateActuatorHelper.narrow(getBioModule(myPowerPSPowerOutFlowRateActuatorName));
				myPowerPSPowerOutFlowRateActuator.setOutput(myPowerPS, 0);
			}
		}
		//
		//Food
		//
		{
			//BiomassRS
			{
				BiomassRS myBiomassRS = BiomassRSHelper.narrow(getBioModule(myBiomassRSName));
				AirInFlowRateActuator myBiomassRSAirInFlowRateActuator = AirInFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSAirInFlowRateActuatorName));
				AirOutFlowRateActuator myBiomassRSAirOutFlowRateActuator = AirOutFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSAirOutFlowRateActuatorName));
				PotableWaterInFlowRateActuator myBiomassRSPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSPotableWaterInFlowRateActuatorName));
				GreyWaterInFlowRateActuator myBiomassRSGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSGreyWaterInFlowRateActuatorName));
				BiomassOutFlowRateActuator myBiomassRSBiomassOutFlowRateActuator = BiomassOutFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSBiomassOutFlowRateActuatorName));
				PowerInFlowRateActuator myBiomassRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSPowerInFlowRateActuatorName));
				myBiomassRSGreyWaterInFlowRateActuator.setOutput(myBiomassRS, 0);
				myBiomassRSPotableWaterInFlowRateActuator.setOutput(myBiomassRS, 0);
				myBiomassRSAirOutFlowRateActuator.setOutput(myBiomassRS, 0);
				myBiomassRSAirInFlowRateActuator.setOutput(myBiomassRS, 0);
				myBiomassRSBiomassOutFlowRateActuator.setOutput(myBiomassRS, 0);
				myBiomassRSPowerInFlowRateActuator.setOutput(myBiomassRS, 0);
			}
			//FoodProcessor
			{
				FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(getBioModule(myFoodProcessorName));
				BiomassInFlowRateActuator myFoodProcessorBiomassInFlowRateActuator = BiomassInFlowRateActuatorHelper.narrow(getBioModule(myFoodProcessorBiomassInFlowRateActuatorName));
				FoodOutFlowRateActuator myFoodProcessorFoodOutFlowRateActuator = FoodOutFlowRateActuatorHelper.narrow(getBioModule(myFoodProcessorFoodOutFlowRateActuatorName));
				PowerInFlowRateActuator myFoodProcessorPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(getBioModule(myFoodProcessorPowerInFlowRateActuatorName));
				myFoodProcessorPowerInFlowRateActuator.setOutput(myFoodProcessor, 0);
				myFoodProcessorFoodOutFlowRateActuator.setOutput(myFoodProcessor, 0);
				myFoodProcessorBiomassInFlowRateActuator.setOutput(myFoodProcessor, 0);
			}
		}
		
		//
		//Crew
		//
		{
			CrewGroup myCrewGroup = CrewGroupHelper.narrow(getBioModule(myCrewGroupName));
			PotableWaterInFlowRateActuator myCrewGroupPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper.narrow(getBioModule(myCrewGroupPotableWaterInFlowRateActuatorName));
			GreyWaterOutFlowRateActuator myCrewGroupGreyWaterOutFlowRateActuator = GreyWaterOutFlowRateActuatorHelper.narrow(getBioModule(myCrewGroupGreyWaterOutFlowRateActuatorName));
			DirtyWaterOutFlowRateActuator myCrewGroupDirtyWaterOutFlowRateActuator = DirtyWaterOutFlowRateActuatorHelper.narrow(getBioModule(myCrewGroupDirtyWaterOutFlowRateActuatorName));
			myCrewGroupPotableWaterInFlowRateActuator.setOutput(myCrewGroup, 0);
			myCrewGroupGreyWaterOutFlowRateActuator.setOutput(myCrewGroup, 0);
			myCrewGroupDirtyWaterOutFlowRateActuator.setOutput(myCrewGroup, 0);
		}

		//
		//Water
		//
		{
			//WaterRS
			{
				WaterRS myWaterRS = WaterRSHelper.narrow(getBioModule(myWaterRSName));
				GreyWaterInFlowRateActuator myWaterRSGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper.narrow(getBioModule(myWaterRSGreyWaterInFlowRateActuatorName));
				DirtyWaterInFlowRateActuator myWaterRSDirtyWaterInFlowRateActuator = DirtyWaterInFlowRateActuatorHelper.narrow(getBioModule(myWaterRSDirtyWaterInFlowRateActuatorName));
				PotableWaterOutFlowRateActuator myWaterRSPotableWaterOutFlowRateActuator = PotableWaterOutFlowRateActuatorHelper.narrow(getBioModule(myWaterRSPotableWaterOutFlowRateActuatorName));
				PowerInFlowRateActuator myWaterRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(getBioModule(myWaterRSPowerInFlowRateActuatorName));
				myWaterRSPotableWaterOutFlowRateActuator.setOutput(myWaterRS, 0);
				myWaterRSDirtyWaterInFlowRateActuator.setOutput(myWaterRS, 0);
				myWaterRSGreyWaterInFlowRateActuator.setOutput(myWaterRS, 0);
				myWaterRSPowerInFlowRateActuator.setOutput(myWaterRS, 0);
			}
		}

		//
		//Framework
		//
		{
			//Accumulator
			{

				Accumulator myAccumulator = AccumulatorHelper.narrow(getBioModule(myAccumulatorName));
				CO2AirStoreOutFlowRateActuator myAccumulatorCO2AirStoreOutFlowRateActuator = CO2AirStoreOutFlowRateActuatorHelper.narrow(getBioModule(myAccumulatorCO2AirStoreOutFlowRateActuatorName));
				CO2AirEnvironmentInFlowRateActuator myAccumulatorCO2AirEnvironmentInFlowRateActuator = CO2AirEnvironmentInFlowRateActuatorHelper.narrow(getBioModule(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName));
				O2AirEnvironmentInFlowRateActuator myAccumulatorO2AirEnvironmentInFlowRateActuator = O2AirEnvironmentInFlowRateActuatorHelper.narrow(getBioModule(myAccumulatorO2AirEnvironmentInFlowRateActuatorName));
				O2AirStoreOutFlowRateActuator myAccumulatorO2AirStoreOutFlowRateActuator = O2AirStoreOutFlowRateActuatorHelper.narrow(getBioModule(myAccumulatorO2AirStoreOutFlowRateActuatorName));
				myAccumulatorCO2AirEnvironmentInFlowRateActuator.setOutput(myAccumulator, 0);
				myAccumulatorO2AirEnvironmentInFlowRateActuator.setOutput(myAccumulator, 0);
				myAccumulatorO2AirStoreOutFlowRateActuator.setOutput(myAccumulator, 0);
				myAccumulatorCO2AirStoreOutFlowRateActuator.setOutput(myAccumulator, 0);
			}
			//Injector
			{
				Injector myInjector = InjectorHelper.narrow(getBioModule(myInjectorName));
				CO2AirStoreInFlowRateActuator myInjectorCO2AirStoreInFlowRateActuator = CO2AirStoreInFlowRateActuatorHelper.narrow(getBioModule(myInjectorCO2AirStoreInFlowRateActuatorName));
				O2AirStoreInFlowRateActuator myInjectorO2AirStoreInFlowRateActuator = O2AirStoreInFlowRateActuatorHelper.narrow(getBioModule(myInjectorO2AirStoreInFlowRateActuatorName));
				O2AirEnvironmentOutFlowRateActuator myInjectorO2AirEnvironmentOutFlowRateActuator = O2AirEnvironmentOutFlowRateActuatorHelper.narrow(getBioModule(myInjectorO2AirEnvironmentOutFlowRateActuatorName));
				CO2AirEnvironmentOutFlowRateActuator myInjectorCO2AirEnvironmentOutFlowRateActuator = CO2AirEnvironmentOutFlowRateActuatorHelper.narrow(getBioModule(myInjectorCO2AirEnvironmentOutFlowRateActuatorName));
				myInjectorCO2AirStoreInFlowRateActuator.setOutput(myInjector, 0);
				myInjectorO2AirStoreInFlowRateActuator.setOutput(myInjector, 0);
				myInjectorCO2AirEnvironmentOutFlowRateActuator.setOutput(myInjector, 0);
				myInjectorO2AirEnvironmentOutFlowRateActuator.setOutput(myInjector, 0);
			}
		}
	}

	/**
	* Initializes the various servers with various optimal data.
	* One crew member created, stores filled high, etc.
	*/
	private void optimalInitialization(){
		//reset servers
		reset();

		//Make some crew members
		CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(myCrewGroupName));
		if (!createdCrew){
			CrewPerson myCrewPerson1 = myCrew.createCrewPerson("Alice Optimal", 50, 80, Sex.female);
			createdCrew = true;
		}

		//Fill the clean water stores to the brim (20 liters), and all stores' capacities
		DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(myDirtyWaterStoreName));
		PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(myPotableWaterStoreName));
		GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(myGreyWaterStoreName));
		myDirtyWaterStore.setCapacity(100f);
		myDirtyWaterStore.setLevel(100f);
		myPotableWaterStore.setCapacity(100f);
		myPotableWaterStore.setLevel(100f);
		myGreyWaterStore.setCapacity(100f);
		myGreyWaterStore.setLevel(100f);

		//Fill the air tanks
		CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(myCO2StoreName));
		O2Store myO2Store = O2StoreHelper.narrow(getBioModule(myO2StoreName));
		myCO2Store.setCapacity(100f);
		myO2Store.setCapacity(100f);
		myCO2Store.setLevel(100f);
		myO2Store.setLevel(100f);

		//Put some air in the cabin
		SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(myCrewEnvironmentName));
		myCrewEnvironment.setInitialVolumeAtSeaLevel(10000000f);
		SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(myPlantEnvironmentName));
		myPlantEnvironment.setInitialVolumeAtSeaLevel(10000000f);

		//Add some crops and food
		BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(myBiomassStoreName));
		FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(myFoodStoreName));
		myBiomassStore.setCapacity(100f);
		myFoodStore.setCapacity(100f);
		myBiomassStore.setLevel(100f);
		myFoodStore.setLevel(100f);

		//Add some power
		PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(myPowerStoreName));
		myPowerStore.setCapacity(100f);
		myPowerStore.setLevel(100f);
	}

	/**
	* The ticking simulation loop.  Uses a variety of semaphores to pause/resume/end without causing deadlock.
	* Essentially runs till told to pause or die.
	*/
	private void runSimulation(){
		Thread theCurrentThread = Thread.currentThread();
		if (!simulationStarted)
			reset();
		while (myTickThread == theCurrentThread) {
			try {
				myTickThread.sleep(driverPauseLength);
				synchronized(this) {
					while (simulationIsPaused && (myTickThread==theCurrentThread)){
						wait();
					}
				}
			}
			catch (InterruptedException e){}
			tick();
			if (isDone()){
				endSimulation();
				if (looping)
					loopSimulation();
			}
		}
	}

	/**
	* Sets how long BioDriver should pause between full simulation ticks (e.g., tick all modules, wait, tick all modules, wait, etc.)
	* @param pDriverPauseLength the length (in milliseconds) for the driver to pause between ticks
	*/
	public void setDriverPauseLength(int pDriverPauseLength){
		if (pDriverPauseLength > 0)
			System.out.println("BioDriverImpl:"+myID+" driver pause of "+pDriverPauseLength+" milliseconds");
		driverPauseLength = pDriverPauseLength;
	}

	/**
	* Tells how long the simulation pauses between full simulation ticks.
	* @return How long the simulation pauses between full simulation ticks.
	*/
	public int getDriverPauseLength(){
		return driverPauseLength;
	}

	/**
	* Tells whether BioDriver is looping the simulation after end conditions have been met.
	* @return Whether BioDriver is looping the simulation after end conditions have been met.
	*/
	public boolean isLooping(){
		return looping;
	}

	/**
	* Tells whether BioDriver should loop the simulation after end conditions have been met.
	* @param pLoop Whether BioDriver should loop the simulation after end conditions have been met.
	*/
	public void setLooping(boolean pLoop){
		looping = pLoop;
	}

	/**
	* Tells whether the simulation has met an end condition and has stopped.
	* @return Whether the simulation has met an end condition and has stopped. 
	*/
	public boolean isDone(){
		if (runTillN){
			if (ticksGoneBy >= nTicks){
				System.out.println("BioDriverImpl:"+myID+"Too many ticks?");
				return true;
			}
		}
		else if (runTillDead){
			CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(myCrewGroupName));
			if (myCrew.isDead()){
				return true;
			}
		}
		return false;
	}

	/**
	* Tells the number of times BioDriver has ticked the simulation
	* @return The number of times BioDriver has ticked the simulation
	*/
	public int getTicks(){
		return ticksGoneBy;
	}

	/**
	* Fetches a BioModule (e.g. AirRS, FoodProcessor, PotableWaterStore) that has been collected by the BioDriverImpl
	* @return the BioModule requested, null if not found
	*/
	private BioModule getBioModule(String type){
		return (BioModule)(modules.get(type));
	}

	/**
	* Pauses the simulation.  Does nothing if already paused.
	*/
	public synchronized void pauseSimulation(){
		simulationIsPaused = true;
	}

	/**
	* Ends the simulation entirely. 
	*/
	public synchronized void endSimulation(){
		myTickThread = null;
		notify();
		simulationStarted = false;
		myLogger.endLog();
		System.out.println("BioDriverImpl"+myID+": simulation ended");
	}

	/**
	* Check whether simulation has begun.
	* @return <code>true</code> if simulation has started, <code>false</code> if not
	*/
	public boolean simulationHasStarted(){
		return simulationStarted;
	}

	/**
	* Advances the simulation once tick.
	* NOTICE: not pausing the simulation before using this method can be very risky.  Don't do it.
	*/
	public synchronized void advanceOneTick(){
		if (!simulationIsPaused)
			pauseSimulation();
		collectReferences();
		tick();
	}

	/**
	* Resumes the simulation.  Does nothing if already running.
	*/
	public synchronized void resumeSimulation(){
		simulationIsPaused = false;
		notify();
	}

	/**
	* Iterates through the modules setting their logs
	* @param pLogSim Whether or not modules should log.
	*/
	public synchronized void setFullLogging(boolean pLogSim){
		collectReferences();
		fullLogging = pLogSim;
		if (fullLogging){
			System.out.println("BioDriverImpl:"+myID+" Enabling fullLogging");
		}
		else{
			System.out.println("BioDriverImpl:"+myID+" Disabling fullLogging");
		}
		for (Iterator iter = modules.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
			currentBioModule.setLogging(pLogSim);
		}
		myLogger.setProcessingLogs(pLogSim);
	}
	
	/**
	* Iterates through the sensors setting their logs
	* @param pLogSim Whether or not sensors should log.
	*/
	public synchronized void setSensorLogging(boolean pLogSim){
		collectReferences();
		sensorsLogging = pLogSim;
		if (sensorsLogging){
			System.out.println("BioDriverImpl:"+myID+" Enabling sensor logging");
		}
		else{
			System.out.println("BioDriverImpl:"+myID+" Disabling sensor logging");
		}
		for (Iterator iter = sensors.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
			currentBioModule.setLogging(pLogSim);
		}
		myLogger.setProcessingLogs(pLogSim);
	}
	
	/**
	* Iterates through the actuators setting their logs
	* @param pLogSim Whether or not actuators should log.
	*/
	public synchronized void setActuatorLogging(boolean pLogSim){
		collectReferences();
		actuatorsLogging = pLogSim;
		if (actuatorsLogging){
			System.out.println("BioDriverImpl:"+myID+" Enabling actuator logging");
		}
		else{
			System.out.println("BioDriverImpl:"+myID+" Disabling actuator logging");
		}
		for (Iterator iter = actuators.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
			currentBioModule.setLogging(pLogSim);
		}
		myLogger.setProcessingLogs(pLogSim);
	}

	/**
	* Tells the amount of stochastic intensity the modules will undergo.
	* @param pValue The amount of stochastic intensity the modules will undergo.
	* Options are:
	* <code>StochasticIntensity.HIGH_STOCH</code>
	* <code>StochasticIntensity.MEDIUM_STOCH</code>
	* <code>StochasticIntensity.LOW_STOCH</code>
	* <code>StochasticIntensity.NONE_STOCH</code>
	*/
	public void setStochasticIntensity(StochasticIntensity pValue){
		for (Iterator iter = modules.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
			currentBioModule.setStochasticIntensity(pValue);
		}
	}

	/**
	* Starts a malfunction on every module
	* @param pIntensity The intensity of the malfunction<br>
	* Options are:<br>
	* &nbsp;&nbsp;&nbsp;<code>MalfunctionIntensity.SEVERE_MALF</code><br>
	* &nbsp;&nbsp;&nbsp;<code>MalfunctionIntensity.MEDIUM_MALF</code><br>
	* &nbsp;&nbsp;&nbsp;<code>MalfunctionIntensity.LOW_MALF</code><br>
	* @param pLength The length (time-wise) of the malfunction<br>
	* Options are:<br>
	* &nbsp;&nbsp;&nbsp;<code>MalfunctionLength.TEMPORARY_MALF</code><br>
	* &nbsp;&nbsp;&nbsp;<code>MalfunctionLength.PERMANENT_MALF</code><br>
	*/
	public void startMalfunction(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		for (Iterator iter = modules.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
			currentBioModule.startMalfunction(pIntensity, pLength);
		}
	}

	/**
	* Tells whether the modules are full Logging or not
	* @return Whether the modules are full Logging or not
	*/
	public boolean isFullLogging(){
		return fullLogging;
	}
	
	/**
	* Tells whether the sensors are logging or not
	* @return Whether the sensors are logging or not
	*/
	public boolean isSensorLogging(){
		return sensorsLogging;
	}
	
	/**
	* Tells whether the actuators are logging or not
	* @return Whether the actuators are logging or not
	*/
	public boolean isActuatorLogging(){
		return actuatorsLogging;
	}

	/**
	* Tries to collect references to all the servers and adds them to a hashtable than can be accessed by outside classes.
	*/
	private void collectReferences(){
		if (hasCollectedReferences)
			return;
		// resolve the Objects Reference in Naming
		modules = new Hashtable();
		sensors = new Hashtable();
		actuators = new Hashtable();
		System.out.println("BioDriverImpl:"+myID+" Collecting references to modules...");
		for (Iterator iter = myModuleNames.iterator(); iter.hasNext();){
			String currentModuleName = (String)(iter.next());
			try{
				BioModule currentModule = BioModuleHelper.narrow(OrbUtils.getNCRef().resolve_str(currentModuleName));
				modules.put(currentModuleName, currentModule);
				if (mySensorNames.contains(currentModuleName))
					sensors.put(currentModuleName, currentModule);
				else if (myActuatorNames.contains(currentModuleName))
					actuators.put(currentModuleName, currentModule);
			}
			catch (Exception e){
				System.err.println("BioDriverImpl:"+myID+" Couldn't locate element called "+currentModuleName+", skipping...");
			}
		}
		try{
			myLogger = LoggerHelper.narrow(OrbUtils.getNCRef().resolve_str(myLoggerName));
		}
		catch (Exception e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate logger named "+myLoggerName+", skipping...");
		}
		hasCollectedReferences = true;
	}

	/**
	* Resets the simulation by calling every known server's reset method.
	* Typically this means resetting the various gas levels, crew people, water levels, etc.
	*/
	public void reset(){
		collectReferences();
		System.out.println("BioDriverImpl:"+myID+" Resetting simulation");
		for (Iterator iter = modules.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
			currentBioModule.reset();
		}
	}

	/**
	* Ticks every server.  The SimEnvironment is ticked first as it keeps track of time for the rest of the server.
	* The other server are ticked in no particular order by enumerating through the module hashtable.
	* When every server has been ticked, BioDriverImpl notifies all it's listeners that this has happened.
	*/
	private void tick(){
		//Iterate through the rest of the modules and tick them
		for (Iterator iter = modules.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
			currentBioModule.tick();
		}
		ticksGoneBy++;
	}

}

