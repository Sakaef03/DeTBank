public class LoginTester {
    public static void main(String[] args) throws Exception {
        LoginFactory factory = new LoginFactory();
        factory.registerUser("1234", "senha");
        LoginScreen login = new LoginScreen(factory);
    }
}
