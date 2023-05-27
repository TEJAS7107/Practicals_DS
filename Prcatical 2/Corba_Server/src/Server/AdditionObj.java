package Server;

import com.sun.corba.se.internal.POA.POAORB;
import com.sun.corba.se.internal.iiop.ORB;

import AdditionApp.AdditionPOA;

public class AdditionObj extends AdditionPOA {
	private org.omg.CORBA.ORB orb;
	public void setOrb(org.omg.CORBA.ORB orb2) {
		this.orb = orb2;
	}

	@Override
	public int add(int a, int b) {
		// TODO Auto-generated method stub
		int r = a+b;
		return r;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		orb.shutdown(false);
		
	}



}
