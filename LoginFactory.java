public class LoginFactory
{
    private EncryptionAES cryptographer;

    public LoginFactory() throws Exception
    {
        cryptographer = new EncryptionAES("encryptionkey.txt");
    }

    public void registerUser(String username, String password) throws Exception
    {
        String info = username + "::" + password;
        String encryptedInfo = this.cryptographer.encrypt(info, this.cryptographer.getKey());
        this.cryptographer.writeFile("loginkeys.txt", encryptedInfo);
    }

    public boolean login(String info) throws Exception
    {
        boolean loginSuccessful = false;
        String search = this.cryptographer.encrypt(info, this.cryptographer.getKey());
        loginSuccessful = this.cryptographer.lineFound(search, "loginkeys.txt");
        if(loginSuccessful)
        {
            System.out.println("DEBUG: login successful");
            return true;
        }
        else
        {
            System.out.println("DEBUG: login invalid");
            return false;
        }
    }

    public void decryptTest(String info) throws Exception
    {
        String result = this.cryptographer.decrypt(info, this.cryptographer.getKey());
        System.out.println(result);
    }
}
