import java.net.Socket;
import java.util.Scanner;

public class PeerHandler implements Runnable {
    private Socket socket;

    public PeerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Scanner in = new Scanner(socket.getInputStream())) {
            while (in.hasNextLine()) {
                String message = in.nextLine();
                System.out.println("Received: " + message);
            }
        } catch (Exception e) {
            System.err.println("Failed to handle peer connection");
            e.printStackTrace();
        }
    }
}
