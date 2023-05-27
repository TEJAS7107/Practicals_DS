package Practical_6;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
   public static void main(String[] args) throws RemoteException {
       Scanner scanner = new Scanner(System.in);
       System.out.print("Enter Server Id: ");
       int id = scanner.nextInt();
       try {
           ServerInterface server = (ServerInterface) Naming.lookup("rmi://localhost/server" + id);
           server.addServer(server);
           server.election();
           while (true) {
               if (server.isCoordinator()) {
                   System.out.println("Press enter to send a message to all servers");
                   scanner.nextLine();
                   server.receiveMessage("Hello from Server " + id);
               } else {
                   System.out.println("Waiting for coordinator to send message");
                   scanner.nextLine();
               }
           }
       } 
       catch (Exception e) {
           System.out.println("Exception occurred: ");
       }
   }
}
