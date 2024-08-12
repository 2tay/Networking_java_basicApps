import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Peer {
    private static final String IP_ADD = "localhost";
    private static int port;
    private static Map<Integer, Socket> peerSockets = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter Port to run server: ");
        port = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        try {
            ServerSocket serverSocket = new ServerSocket(port); // Use the user-provided port
            System.out.println("Peer is running on Port " + port);

            // Start server thread
            startServer(serverSocket);

            // Thread for sending messages
            while (true) {
                System.out.println("Enter peer Port to connect (0 to quit): ");
                int peerPort = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                if (peerPort == 0) {
                    break;
                }

                Socket socket = new Socket(IP_ADD, peerPort);
                peerSockets.put(peerPort, socket);
                new Thread(new PeerHandler(socket)).start();

                // Sending message to the connected peer
                System.out.println("Enter message to send to Peer on Port: " + peerPort);
                String message = scanner.nextLine();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
            }
        } catch (Exception e) {
            System.err.println("Failed to run server");
            e.printStackTrace();
        } finally {
            // Close all open sockets
            for (Socket s : peerSockets.values()) {
                try {
                    if (s != null && !s.isClosed()) {
                        s.close();
                    }
                } catch (Exception e) {
                    System.err.println("Failed to close peer socket");
                    e.printStackTrace();
                }
            }

            // Close the scanner
            scanner.close();
        }
    }

    private static void startServer(ServerSocket serverSocket) {
        new Thread(() -> {
            while (true) {
                try {
                    // Pass the accepted socket directly to the PeerHandler constructor
                    new Thread(new PeerHandler(serverSocket.accept())).start();
                } catch (Exception e) {
                    System.err.println("Failed to accept client socket");
                    e.printStackTrace();
                }
            }
        }).start();
    }
}