package Practical_6;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server extends UnicastRemoteObject implements ServerInterface {
   private static final long serialVersionUID = 1L;
   List<ServerInterface> servers;
   boolean isCoordinator;

   protected Server() throws RemoteException {
       super();
       servers = new ArrayList<>();
   }

   public void addServer(ServerInterface server) throws RemoteException {
       servers.add(server);
   }

   public void removeServer(ServerInterface server) throws RemoteException {
       servers.remove(server);
   }

   public void election() throws RemoteException {
       System.out.println("Starting Election");
       int myId = this.getId();
       int maxId = myId;
       for (ServerInterface server : servers) {
           if (server.getId() > maxId) {
               maxId = server.getId();
           }
       }
       if (maxId == myId) {
           System.out.println("I am the coordinator");
           isCoordinator = true;
           for (ServerInterface server : servers) {
               if (server.getId() != myId) {
                   server.coordinator(myId);
               }
           }
       } else {
           System.out.println("Sending Election Message");
           for (ServerInterface server : servers) {
               if (server.getId() > myId) {
                   server.election();
               }
           }
       }
   }

   public void coordinator(int id) throws RemoteException {
       System.out.println("Coordinator is Server " + id);
       isCoordinator = false;
   }

   public int getId() throws RemoteException {
       try {
		return Integer.parseInt(getClientHost().substring(12));
	} catch (NumberFormatException | ServerNotActiveException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return 0;
   }

   public static void main(String[] args) throws RemoteException {
       Scanner scanner = new Scanner(System.in);
       Server server = new Server();
       System.out.print("Enter Server Id: ");
       int id = scanner.nextInt();
       System.out.println("Server " + id + " started");
       java.rmi.registry.LocateRegistry.createRegistry(1099);
       try {
		java.rmi.Naming.rebind("rmi://localhost/server" + id, server);
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       while (true) {
           if (server.isCoordinator) {
               System.out.println("Press enter to send a message to all servers");
               scanner.nextLine();
               for (ServerInterface s : server.servers) {
                   s.receiveMessage("Hello from Server " + id);
               }
           } else {
               System.out.println("Waiting for coordinator to send message");
               scanner.nextLine();
           }
       }
   }

   public void receiveMessage(String message) throws RemoteException {
       System.out.println("Message Received: " + message);
   }

@Override
public boolean isCoordinator() throws RemoteException {
	// TODO Auto-generated method stub
	return false;
}
}

