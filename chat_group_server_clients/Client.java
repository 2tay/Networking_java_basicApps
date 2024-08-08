import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        startClient("localhost", 1234);
    }

    // Function to start the client
    private static void startClient(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            handleServerCommunication(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to handle communication with the server
    private static void handleServerCommunication(Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Create a new thread for receiving messages from the server
        new Thread(() -> receiveMessages(in)).start();

        // Send messages to the server
        sendMessages(out);

        // Close resources
        in.close();
        out.close();
    }

    // Function to receive messages from the server
    private static void receiveMessages(BufferedReader in) {
        try {
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to send messages to the server
    private static void sendMessages(PrintWriter out) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message you want to send to Server: ");

        while (true) {
            String clientMessage = scanner.nextLine();
            if (clientMessage.equals("q")) {
                break;
            }
            out.println(clientMessage);
        }

        scanner.close();
    }
}
