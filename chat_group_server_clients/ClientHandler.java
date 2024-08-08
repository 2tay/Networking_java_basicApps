import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final String clientName;
    private PrintWriter out;

    public ClientHandler(Socket socket, String name) {
        this.clientSocket = socket;
        this.clientName = name;
    }

    @Override
    public void run() {
        handleClient();
    }

    // Function to handle client communication
    private void handleClient() {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            this.out = out;
            Server.broadcastMessage(this, clientName + " has joined the chat");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from: " + clientName + " Message: " + inputLine);
                Server.broadcastMessage(this, clientName + ": " + inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Server.removeClient(this);
            closeClientSocket();
        }
    }

    // Function to send a message to the client
    public void sendMessage(String message) {
        out.println(message);
    }

    // Function to close client socket
    private void closeClientSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
