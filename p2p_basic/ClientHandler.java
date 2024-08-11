import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final String nodeName;

    public ClientHandler(Socket clienSocket, String nodeName) {
        this.clientSocket = clienSocket;
        this.nodeName = nodeName;
    }

    @Override
    public void run() {
        handleClient();
    }

    public void handleClient(){
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) 
        {
            out.println("heey");
            String inputLine;
            while((inputLine = in.readLine()) != null) {
                System.out.println("Recieved message: " + inputLine);
            }
        } 
        catch (Exception e) {
            System.err.println("Failed To start Output Input streams Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
