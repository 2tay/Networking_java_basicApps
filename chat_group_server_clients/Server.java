import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static int clientCount = 0;
    private static final List<ClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args) {
        startServer(1234);
    }

    // Function to start the server
    private static void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started, waiting for connection on port: " + port + " ......");

            while (true) {
                acceptClientConnection(serverSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to accept client connections
    private static void acceptClientConnection(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = serverSocket.accept();
        clientCount++;
        System.out.println("Client " + clientCount + " connected!");

        // Create a ClientHandler for each client and start it
        ClientHandler clientHandler = new ClientHandler(clientSocket, "Client" + clientCount);
        clientHandlers.add(clientHandler);
        clientHandler.start();
    }

    // Function to broadcast messages to all clients except the sender
    public static synchronized void broadcastMessage(ClientHandler sender, String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler != sender) {
                clientHandler.sendMessage(message);
            }
        }
    }

    // Function to remove a client from the list
    public static synchronized void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }
}
