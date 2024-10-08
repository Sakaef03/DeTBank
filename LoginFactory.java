public class LoginFactory
{
    private EncryptionAES cryptographer;
    public LoginFactory() throws Exception
    {
        cryptographer = new EncryptionAES("loginkeys.txt");
    }

    // public void registerUser() throws Exception
    // {
    //     String newUser = jTextFieldLogin.getText();
    //     String newPassword = new String(jPasswordField.getPassword());
    //     String info = newUser + "::" + newPassword;
    //     String encrypted = this.cryptographer.encrypt(info, this.cryptographer.getKey());
    //     boolean dupeLogin;
    //     dupeLogin = this.cryptographer.lineFound(encrypted, "login.txt");
    //     if(dupeLogin == false)
    //     {
    //         this.cryptographer.writeFile("login.txt", this.cryptographer.encrypt(info, this.cryptographer.getKey()));
    //         String message = "";
    //         switch(jComboBox1.getSelectedItem().toString())
    //             {
    //                 case "Português":
    //                     message = "Usuário cadastrado";
    //                     break;
    //                 case "English":
    //                     message = "Username registered";
    //                     break;
    //                 case "Русский":
    //                     message = "Идентификация зарегистрирована";
    //                     break;
    //             }
    //         jTextFieldResult.setText(message);
    //     }

    //     else
    //     {
    //         displayTakenUsernameMessage();
    //     }
    // }
}
