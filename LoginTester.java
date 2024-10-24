public class LoginTester {
    public static void main(String[] args) throws Exception {
        LoginFactory factory = new LoginFactory();
        LoginScreen login = new LoginScreen(factory);
    }
}
