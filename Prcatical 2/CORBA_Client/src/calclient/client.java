package calclient;

import java.rmi.Naming;
import java.util.Scanner;

import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextHelper;

import com.sun.corba.se.org.omg.CORBA.ORB;

import AdditionApp.Addition;
import AdditionApp.AdditionHelper;

public class client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			org.omg.CORBA.ORB orb = ORB.init(args, null);
			Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt scRef = NamingContextExtHelper.narrow(objRef);
			Addition addobj = AdditionHelper.narrow(scRef.resolve_str("ABC"));
			
			Scanner sc = new Scanner(System.in);
			for (; ; ) {
				System.out.println("Enter a:");
				String aa = sc.nextLine();
				System.out.println("Enetr b:");
				String bb =sc.nextLine();
				int a = Integer.parseInt(aa);
				int b = Integer.parseInt(bb);
				int r = addobj.add(a, b);
				System.out.println("result of Addition------> "+ r);
				System.out.println("--------------------------------------------");
					
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
