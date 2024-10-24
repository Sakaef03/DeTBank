import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(3333)) {
            System.out.println("Bank Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
