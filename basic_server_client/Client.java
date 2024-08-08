package basic_server_client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Change to server's IP if not running locally
        int port = 12345;
        
        try (Socket socket = new Socket(serverAddress, port)) {
            // Create input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send a message to the server
            String message = "Hello, server!";
            out.println(message);

            // Read response from server
            String response = in.readLine();
            System.out.println("Received from server: " + response);

            // Close resources
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
