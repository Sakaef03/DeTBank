import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public ServerConnection(Socket socket) {
        this.socket = socket;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(String requestType, int agency, String userName, double amount) throws IOException {
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        output.writeUTF(requestType);
        output.writeInt(agency);       
        output.writeUTF(userName);     
        output.writeDouble(amount);    
        output.flush();
    }
    

    public String sendWithdrawRequest(String userName, double amount) {
        try {
            output.writeUTF("withdraw");
            output.writeUTF(userName);
            output.writeDouble(amount);
            return input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during withdrawal";
        }
    }

    public String sendDepositRequest(String userName, double amount) {
        try {
            output.writeUTF("deposit");
            output.writeUTF(userName);
            output.writeDouble(amount);
            return input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during deposit";
        }
    }

    public String getResponse() throws IOException {
        return input.readUTF();
    }

    public void sendLocale(String selectedLanguage) throws IOException {
        output.writeUTF(selectedLanguage); 
    }
    
    public double getUpdatedBalance() {
        try {
            return input.readDouble();
        } catch (IOException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}