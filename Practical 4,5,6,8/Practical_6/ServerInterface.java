package Practical_6;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
   void addServer(ServerInterface server) throws RemoteException;
   void removeServer(ServerInterface server) throws RemoteException;
   void election() throws RemoteException;
   void coordinator(int id) throws RemoteException;
   int getId() throws RemoteException;
   void receiveMessage(String message) throws RemoteException;
   boolean isCoordinator() throws RemoteException;
}

