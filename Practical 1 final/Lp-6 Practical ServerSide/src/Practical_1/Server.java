package Practical_1;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;



public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			BankAccImp bankAcc = new BankAccImp();
			BankAccount exportObject = (BankAccount)UnicastRemoteObject.exportObject(bankAcc, 0);
			
			Registry registry = LocateRegistry.createRegistry(9107);
			System.out.println(InetAddress.getLocalHost());
			String url = "rmi://"+InetAddress.getLocalHost()+":9107/BankAccount";
			System.setProperty("java.rmi.server.hostname","127.0.0.1");
			
			registry.rebind(url,exportObject);
			
			System.out.println("Waiting for clients call");
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error while trying to connect to bank Account");
		}

	}

}
