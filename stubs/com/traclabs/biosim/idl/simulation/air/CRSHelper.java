package com.traclabs.biosim.idl.simulation.air;


/**
 *	Generated from IDL interface "CRS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CRSHelper
{
	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.air.CRS s)
	{
			any.insert_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.air.CRS extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/simulation/air/CRS:1.0", "CRS");
	}
	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/air/CRS:1.0";
	}
	public static CRS read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.traclabs.biosim.idl.simulation.air.CRS s)
	{
		_out.write_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.air.CRS narrow(final java.lang.Object obj)
	{
		if (obj instanceof com.traclabs.biosim.idl.simulation.air.CRS)
		{
			return (com.traclabs.biosim.idl.simulation.air.CRS)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static com.traclabs.biosim.idl.simulation.air.CRS narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (com.traclabs.biosim.idl.simulation.air.CRS)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:com/traclabs/biosim/idl/simulation/air/CRS:1.0"))
			{
				com.traclabs.biosim.idl.simulation.air._CRSStub stub;
				stub = new com.traclabs.biosim.idl.simulation.air._CRSStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static com.traclabs.biosim.idl.simulation.air.CRS unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (com.traclabs.biosim.idl.simulation.air.CRS)obj;
		}
		catch (ClassCastException c)
		{
				com.traclabs.biosim.idl.simulation.air._CRSStub stub;
				stub = new com.traclabs.biosim.idl.simulation.air._CRSStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
