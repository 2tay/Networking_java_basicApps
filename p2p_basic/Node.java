import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Node {
    private final String NODE_ROLE;
    private final int PORT;

    public Node(String nodeRole, int port) {
        NODE_ROLE = nodeRole;
        PORT = port;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println(NODE_ROLE + " started on port: " + PORT);
            while(true) {
                Socket clienSocket = new Socket();
                new ClientHandler(clienSocket, NODE_ROLE);
            }
        } catch (Exception e) {
            System.err.println("Failed to start " + NODE_ROLE + " on port " + PORT + ": " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void connectToNode(String ipAdd, int port) {
        try(Socket socket = new Socket(ipAdd, port)) {
            System.out.println("Connected Successfully with Node " + ipAdd + ", on port: " + port);
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println("I'm " + NODE_ROLE + " want to connect with you.");
                String response;
                while((response = in.readLine()) != null) {
                    System.out.println("Received message: " + response);
                }
            } 
            catch (Exception e) {
                System.err.println("Failed to start streans with Node " + ipAdd + ", port " + port + ": " + e.getMessage());
            }

        } 
        catch (Exception e) {
            System.err.println("Failed to connect to Node " + ipAdd + ", port " + port + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}