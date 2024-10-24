import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private CrudBD crud = new CrudBD();

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
                int agency = input.readInt();  
                String userName = input.readUTF();
                double amount = input.readDouble();
    
                if (requestType.equals("withdraw")) {
                    handleWithdraw(agency, amount);  
                } else if (requestType.equals("deposit")) {
                    handleDeposit(agency, amount);  
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }
    

    private void handleWithdraw(int agency, double amount) throws IOException {
        User user = crud.getUser(agency);  // Obter o usuário com base na agência
        if (user == null) {
            output.writeUTF("User not found");
            return;
        }
    
        if (amount > user.getBalance()) {
            output.writeUTF("Insufficient funds");
        } else {
            user.setBalance(user.getBalance() - amount);
            crud.updateAccount(user);  // Atualizar o saldo no banco de dados
            output.writeUTF("Withdrawal successful");
            output.writeDouble(user.getBalance());
        }
    }
    
    private void handleDeposit(int agency, double amount) throws IOException {
        User user = crud.getUser(agency);  // Obter o usuário com base na agência
        if (user == null) {
            output.writeUTF("User not found");
            return;
        }
    
        user.setBalance(user.getBalance() + amount);
        crud.updateAccount(user);  // Atualizar o saldo no banco de dados
        output.writeUTF("Deposit successful");
        output.writeDouble(user.getBalance());
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
