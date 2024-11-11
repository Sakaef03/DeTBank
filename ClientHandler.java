import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private CrudBD crud = new CrudBD();
    private ResourceBundle bundle;

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
                String language = input.readUTF();
                double amount = input.readDouble();

                if ("withdraw".equals(requestType)) {
                    handleWithdraw(agency, amount, language);
                } else if ("deposit".equals(requestType)) {
                    handleDeposit(agency, amount, language);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    private void handleWithdraw(int agency, double amount, String language) throws IOException {
        User user = crud.getUser(agency);
        Locale locale = getLocaleFromLanguage(language);
        bundle = ResourceBundle.getBundle("messages", locale);

        if (user == null) {
            output.writeUTF(bundle.getString("userNotFound"));
            output.writeDouble(0);
            return;
        }

        if (amount > user.getBalance()) {
            output.writeUTF(bundle.getString("insufficientFunds"));
            output.writeDouble(user.getBalance());
        } else {
            user.setBalance(user.getBalance() - amount);
            crud.updateAccount(user);
            output.writeUTF(bundle.getString("withdrawalSuccess"));
            output.writeDouble(user.getBalance());
        }
    }

    private void handleDeposit(int agency, double amount, String language) throws IOException {
        User user = crud.getUser(agency);
        Locale locale = getLocaleFromLanguage(language);
        bundle = ResourceBundle.getBundle("messages", locale);

        if (user == null) {
            output.writeUTF(bundle.getString("userNotFound"));
            output.writeDouble(0); 
            return;
        }

        user.setBalance(user.getBalance() + amount);
        crud.updateAccount(user);
        output.writeUTF(bundle.getString("depositSuccess"));
        output.writeDouble(user.getBalance());
    }

    private Locale getLocaleFromLanguage(String language) {
        switch (language) {
            case "Português (pt)":
                return new Locale("pt", "BR");
            case "English (en)":
                return new Locale("en", "US");
            case "Español (es)":
                return new Locale("es", "ES");
            case "Русский (ru)":
                return new Locale("ru", "RU");
            case "Italiano (it)":
                return new Locale("it", "IT");
            default:
                return new Locale("pt", "BR");
        }
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
