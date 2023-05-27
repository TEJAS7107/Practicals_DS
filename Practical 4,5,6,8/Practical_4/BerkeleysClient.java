package Practical_4;

import java.io.*;
import java.net.*;
import java.util.*;

public class BerkeleysClient {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java BerkeleyClient <server>");
            System.exit(-1);
        }

        String server = args[0];

        try (
            // Set up the socket
            Socket clientSocket = new Socket(server, 8094);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            // Get the request from the server
            String request = in.readLine();

            if (request.equals("GetTime")) {
                // Send the current time to the server
                long time = System.currentTimeMillis();
                out.println(Long.toString(time));

                // Get the offset from the server
                String response = in.readLine();
                long offset = Long.parseLong(response.split(" ")[1]);
                System.out.println("Offset: " + offset);

                // Synchronize the clock
                long newTime = time + offset;
                System.out.println("New time: " + newTime);
                System.out.println("Synchronized with server.");
            } else {
                System.err.println("Invalid request from server.");
                System.exit(-1);
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + server);
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("IO exception occurred.");
            System.exit(-1);
        }
    }
}
