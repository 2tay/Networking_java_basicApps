package basic_server_client;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 12345;
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started and waiting for connections...");
            
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            // Create input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read message from client
            String message = in.readLine();
            System.out.println("Received from client: " + message);

            // Send response to client
            out.println("Message received: " + message);

            // Close resources
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
