import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

public class DetBankServer {
    public static final String address = "127.0.0.1";
    public static final int port = 3334;
    private static ServerSocket server;
    private static Socket acceptedClient;
    private static Scanner input;
    private static PrintStream output;

    public static void main(String[] args) {
        System.out.println("===== DETBANK SERVER CONSOLE =====");
        try {
            startServer();
            awaitClientConnection();
            clientInteraction();
            endConnection();
            endServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startServer() throws IOException
    {
        server = new ServerSocket(port);
        System.out.println("Servidor iniciado e escutando a porta " + port);
    }

    private static void awaitClientConnection() throws IOException
    {
        acceptedClient = server.accept();
        System.out.println("Cliente IP " + acceptedClient.getInetAddress().getHostAddress() + " conectado ao servidor pela porta " + port);
        input = new Scanner(acceptedClient.getInputStream());
        output = new PrintStream(acceptedClient.getOutputStream());
    }

    private static void clientInteraction() throws IOException{
        
    }

    private static void endConnection() throws IOException
    {
        input.close();
        System.out.println("Cliente desconectou do servidor");
    }

    private static void endServer() throws IOException
    {
        server.close();
        System.out.println("Servidor finalizado");
    }
}
