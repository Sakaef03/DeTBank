public class LoginTester {
    public static void main(String[] args) throws Exception {
        LoginFactory factory = new LoginFactory();
        factory.registerUser("1234", "senha");
        factory.decryptTest("3kn9pKGSigFiHsAuEvifWw==");
        factory.login("1234::senha");
    }
}
