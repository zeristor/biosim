package com.traclabs.biosim.idl.simulation.air;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "OGS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class OGSPOATie
	extends OGSPOA
{
	private OGSOperations _delegate;

	private POA _poa;
	public OGSPOATie(OGSOperations delegate)
	{
		_delegate = delegate;
	}
	public OGSPOATie(OGSOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.air.OGS _this()
	{
		return com.traclabs.biosim.idl.simulation.air.OGSHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.air.OGS _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.air.OGSHelper.narrow(_this_object(orb));
	}
	public OGSOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(OGSOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		else
		{
			return super._default_POA();
		}
	}
	public float getTickLength()
	{
		return _delegate.getTickLength();
	}

	public void setLogLevel(com.traclabs.biosim.idl.framework.LogLevel pLogLevel)
	{
_delegate.setLogLevel(pLogLevel);
	}

	public void clearMalfunction(long id)
	{
_delegate.clearMalfunction(id);
	}

	public void notifyCommandSent(java.lang.String commandName)
	{
_delegate.notifyCommandSent(commandName);
	}

	public com.traclabs.biosim.idl.simulation.air.O2ProducerDefinition getO2ProducerDefinition()
	{
		return _delegate.getO2ProducerDefinition();
	}

	public boolean isMalfunctioning()
	{
		return _delegate.isMalfunctioning();
	}

	public void clearAllMalfunctions()
	{
_delegate.clearAllMalfunctions();
	}

	public void setEnableFailure(boolean pValue)
	{
_delegate.setEnableFailure(pValue);
	}

	public float randomFilter(float preFilteredValue)
	{
		return _delegate.randomFilter(preFilteredValue);
	}

	public void reset()
	{
_delegate.reset();
	}

	public int getMyTicks()
	{
		return _delegate.getMyTicks();
	}

	public void registerCommandListener(com.traclabs.biosim.idl.simulation.framework.CommandListener listener)
	{
_delegate.registerCommandListener(listener);
	}

	public void maintain()
	{
_delegate.maintain();
	}

	public com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition getPowerConsumerDefinition()
	{
		return _delegate.getPowerConsumerDefinition();
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public com.traclabs.biosim.idl.simulation.air.H2ProducerDefinition getH2ProducerDefinition()
	{
		return _delegate.getH2ProducerDefinition();
	}

	public java.lang.String getModuleName()
	{
		return _delegate.getModuleName();
	}

	public void scheduleMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength, int pTickToMalfunction)
	{
_delegate.scheduleMalfunction(pIntensity,pLength,pTickToMalfunction);
	}

	public void tick()
	{
_delegate.tick();
	}

	public void fixMalfunction(long id)
	{
_delegate.fixMalfunction(id);
	}

	public java.lang.String[] getMalfunctionNames()
	{
		return _delegate.getMalfunctionNames();
	}

	public void setTickLength(float pInterval)
	{
_delegate.setTickLength(pInterval);
	}

	public boolean isFailureEnabled()
	{
		return _delegate.isFailureEnabled();
	}

	public com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
		return _delegate.startMalfunction(pIntensity,pLength);
	}

	public com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinition getPotableWaterConsumerDefinition()
	{
		return _delegate.getPotableWaterConsumerDefinition();
	}

	public void log()
	{
_delegate.log();
	}

	public com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions()
	{
		return _delegate.getMalfunctions();
	}

	public void fixAllMalfunctions()
	{
_delegate.fixAllMalfunctions();
	}

}
