import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private double userBalance = 1000.00;  // Default user balance

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String requestType = input.readUTF();
                String userName = input.readUTF();
                double amount = input.readDouble();

                if (requestType.equals("withdraw")) {
                    handleWithdraw(amount);
                } else if (requestType.equals("deposit")) {
                    handleDeposit(amount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    private void handleWithdraw(double amount) throws IOException {
        if (amount > userBalance) {
            output.writeUTF("Insufficient funds");
        } else {
            userBalance -= amount;
            output.writeUTF("Withdrawal successful");
            output.writeDouble(userBalance);
        }
    }

    private void handleDeposit(double amount) throws IOException {
        userBalance += amount;
        output.writeUTF("Deposit successful");
        output.writeDouble(userBalance);
    }

    private void closeConnections() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
