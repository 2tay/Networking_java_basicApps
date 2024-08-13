import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Person person = new Person("Alice", 30);

        try (Socket socket = new Socket("localhost", 1234);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            out.writeObject(person);
            System.out.println("Person object sent to server");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
