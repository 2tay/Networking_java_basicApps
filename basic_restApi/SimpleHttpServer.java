import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {
    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("Server started on port 8000...");
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClientRequest(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();

        // Read the HTTP request
        String requestLine = in.readLine();
        System.out.println("Received: " + requestLine);

        // Handle GET request
        if (requestLine != null && requestLine.startsWith("GET")) {
            sendHttpResponse(out, "200 OK", "text/plain", "Hello, this is a simple GET response!");
        } else {
            sendHttpResponse(out, "404 Not Found", "text/plain", "Not Found");
        }

        in.close();
        out.close();
    }

    private static void sendHttpResponse(OutputStream out, String status, String contentType, String content) throws IOException {
        // Write the HTTP response headers
        out.write(("HTTP/1.1 " + status + "\r\n").getBytes());
        out.write(("Content-Type: " + contentType + "\r\n").getBytes());
        out.write("Content-Length: ".getBytes());
        out.write((content.length() + "\r\n").getBytes());
        out.write("\r\n".getBytes());

        // Write the response body
        out.write(content.getBytes());
        out.flush();
    }
}
