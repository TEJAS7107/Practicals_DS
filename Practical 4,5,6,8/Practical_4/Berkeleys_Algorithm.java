package Practical_4;

import java.io.*;
import java.net.*;
import java.util.*;

public class Berkeleys_Algorithm {
    public static void main(String[] args) {
        // Set up the server socket
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8094);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 8000.");
            System.exit(-1);
        }

        // Wait for the clients to connect
        int numClients = 1; // number of clients
        List<Socket> sockets = new ArrayList<>();
        System.out.println("Waiting for " + numClients + " clients to connect...");
        while (sockets.size() < numClients) {
            try {
                Socket clientSocket = serverSocket.accept();
                sockets.add(clientSocket);
                System.out.println("Client " + sockets.size() + " connected.");
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(-1);
            }
        }

        // Get the times from the clients
        long[] times = new long[numClients];
        for (int i = 0; i < numClients; i++) {
            try {
                // Send the request for time
                PrintWriter out = new PrintWriter(sockets.get(i).getOutputStream(), true);
                out.println("GetTime");

                // Get the response with the time
                BufferedReader in = new BufferedReader(new InputStreamReader(sockets.get(i).getInputStream()));
                String response = in.readLine();
                times[i] = Long.parseLong(response);
                System.out.println("Client " + (i + 1) + " time: " + times[i]);
            } catch (IOException e) {
                System.err.println("IO exception occurred.");
                System.exit(-1);
            }
        }

        // Calculate the average time
        long avgTime = Arrays.stream(times).sum() / numClients;

        // Calculate the offset for each clock
        long[] offsets = new long[numClients];
        for (int i = 0; i < numClients; i++) {
            offsets[i] = avgTime - times[i];
        }

        // Synchronize the clocks
        for (int i = 0; i < numClients; i++) {
            try {
                PrintWriter out = new PrintWriter(sockets.get(i).getOutputStream(), true);
                out.println("SetTime " + offsets[i]);
            } catch (IOException e) {
                System.err.println("IO exception occurred.");
                System.exit(-1);
            }
        }

        // Close the sockets
        for (int i = 0; i < numClients; i++) {
            try {
                sockets.get(i).close();
            } catch (IOException e) {
                System.err.println("Could not close socket.");
                System.exit(-1);
            }
        }

        // Print the synchronized times
        System.out.println("Synchronized Times:");
        for (int i = 0; i < numClients; i++) {
            System.out.println("Client " + (i + 1) + " time: " + (times[i] + offsets[i]));
        }
    }
}


