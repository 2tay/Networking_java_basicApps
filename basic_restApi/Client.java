import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        // Send a GET request
        sendGetRequest("localhost", 8000);

        // Send a POST request
        sendPostRequest("localhost", 8000);
    }

    private static void sendGetRequest(String host, int port) {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send the GET request
            out.println("GET / HTTP/1.1");
            out.println("Host: " + host);
            out.println("Connection: close");
            out.println(); // End of headers

            // Read the response
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }

            System.out.println("GET Response:");
            System.out.println(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendPostRequest(String host, int port) {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send the POST request
            out.println("POST / HTTP/1.1");
            out.println("Host: " + host);
            out.println("Content-Type: application/x-www-form-urlencoded");
            out.println("Content-Length: 0"); // No content in the body for simplicity
            out.println("Connection: close");
            out.println(); // End of headers

            // Read the response
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }

            System.out.println("POST Response:");
            System.out.println(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
