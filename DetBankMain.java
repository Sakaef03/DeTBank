public class DetBankMain {
    public static void main(String[] args) throws Exception {
        LoginFactory factory = new LoginFactory();
        LoginScreen login = new LoginScreen(factory);
    }
}
