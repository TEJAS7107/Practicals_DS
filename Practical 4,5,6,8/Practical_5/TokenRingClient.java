package Practical_5;

import java.io.*;
import java.net.*;

public class TokenRingClient {
    
    static int port = 8000;
    static String host = "localhost";

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Send request to enter critical section
        out.println("request");
        String response = in.readLine();
        if (response.equals("grant")) {
            // Entered critical section
            System.out.println("Entered critical section");
            // Do some work here
            // ...
            // Release critical section
            out.println("release");
        } else {
            // Failed to enter critical section
            System.out.println("Failed to enter critical section");
        }

        // Clean up
        in.close();
        out.close();
        socket.close();
    }
}

