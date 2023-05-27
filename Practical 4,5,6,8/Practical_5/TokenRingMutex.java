package Practical_5;

import java.io.*;
import java.net.*;

public class TokenRingMutex {

    static int port = 8090;
    static String host = "localhost";
    static int numProcesses = 3;
    static int processId;
    static int tokenValue = 0;
    static boolean hasToken = false;
    static Socket socket;
    static BufferedReader in;
    static PrintWriter out;

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            processId = Integer.parseInt(args[0]);
        } else {
            System.out.println("Usage: java TokenRingMutex <processId>");
            System.exit(1);
        }

        // Connect to next process in the ring
        int nextProcessId = (processId + 1) % numProcesses;
        socket = new Socket(host, port + nextProcessId);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            // Wait for token
            while (!hasToken) {
                String message = in.readLine();
                if (message != null) {
                    int token = Integer.parseInt(message);
                    if (token == tokenValue) {
                        hasToken = true;
                        System.out.println("Process " + processId + " has the token");
                    }
                }
            }

            // Critical section
            System.out.println("Process " + processId + " is in the critical section");

            // Release token
            hasToken = false;
            tokenValue = (tokenValue + 1) % numProcesses;
            out.println(tokenValue);
            System.out.println("Process " + processId + " released the token");
        }
    }
}
