import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginScreen extends JFrame {
    private JLabel agencyLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton createAccountButton;
    private JComboBox<String> languageBox;
    private ResourceBundle bundle;
    private LoginFactory loginFactory;
    private CrudBD crudBD;


    public LoginScreen(LoginFactory loginFactory) {
        this.loginFactory = loginFactory;
        this.crudBD = new CrudBD();
        setTitle("LoginScreen");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel languagePanel = new JPanel();
        languagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        String[] languages = {"Português (pt)", "English (en)", "Español (es)", "Русский (ru)", "Italiano (it)"};
        languageBox = new JComboBox<>(languages);
        languagePanel.add(languageBox);
        languagePanel.setBackground(Color.WHITE);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel();
        titleLabel.setText("<html><span style='color:green;'>De</span><span style='color:black;'>TB</span><span style='color:green;'>ank</span></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        agencyLabel = new JLabel();
        JTextField agencyField = new JTextField(15);
        agencyLabel.setLabelFor(agencyField);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        loginPanel.add(agencyLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(agencyField, gbc);

        passwordLabel = new JLabel();
        JPasswordField passwordField = new JPasswordField(15);
        passwordLabel.setLabelFor(passwordField);

        gbc.gridy = 2;
        gbc.gridx = 0;
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        loginButton = new JButton();
        loginButton.setFocusPainted(false);
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.WHITE);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        createAccountButton = new JButton();
        createAccountButton.setFocusPainted(false);
        createAccountButton.setBackground(Color.LIGHT_GRAY);
        createAccountButton.setForeground(Color.BLACK);
        gbc.gridy = 4;
        loginPanel.add(createAccountButton, gbc);

        mainPanel.add(languagePanel, BorderLayout.NORTH);
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        add(mainPanel);

        languageBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLanguage = (String) languageBox.getSelectedItem();
                switch (selectedLanguage) {
                    case "Português (pt)":
                        updateTexts(new Locale("pt", "BR"));
                        break;
                    case "English (en)":
                        updateTexts(new Locale("en", "US"));
                        break;
                    case "Español (es)":
                        updateTexts(new Locale("es", "ES"));
                        break;
                    case "Русский (ru)":
                        updateTexts(new Locale("ru", "RU"));
                        break;
                    case "Italiano (it)":
                        updateTexts(new Locale("it", "IT"));
                        break;
                }
            }
        });


        loginButton.addActionListener(e -> {
            String password = String.valueOf(passwordField.getPassword());
            String agency = agencyField.getText();
            String info = agency + "::" + password;
            boolean loginSuccessful = false;
            
            try {
                loginSuccessful = this.loginFactory.login(info);
                if(loginSuccessful)
                {
                    User user = new User(Integer.parseInt(agency), password);
                    setVisible(false);
                    UserScreen userScreen = new UserScreen(Integer.parseInt(agency));
                }

                else
                {
                    agencyField.setText("");
                    passwordField.setText("");
                    String selectedLanguage = (String) languageBox.getSelectedItem();
                    String errorMessage = "errorMessage";
                    switch (selectedLanguage) {
                        case "Português (pt)":
                            errorMessage = "Login inválido";
                            break;
                        case "English (en)":
                            errorMessage = "Invalid login";
                            break;
                        case "Español (es)":
                            errorMessage = "Login inválido";
                            break;
                        case "Русский (ru)":
                            errorMessage = "Недействительная информация";
                            break;
                        case "Italiano (it)":
                            errorMessage = "Accesso non valido";
                            break;
                    }
                    JOptionPane.showMessageDialog(null, errorMessage);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });


        createAccountButton.addActionListener(e -> {
            try {
                OpenSignUpScreen();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        updateTexts(new Locale("pt", "BR"));
        setVisible(true);
    }

    private void updateTexts(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
        agencyLabel.setText(bundle.getString("agency"));
        passwordLabel.setText(bundle.getString("password"));
        loginButton.setText(bundle.getString("login"));
        createAccountButton.setText(bundle.getString("create_account"));
    }

    private void OpenSignUpScreen() throws Exception {
        SignUpScreen signUpScreen = new SignUpScreen(this);
        signUpScreen.setVisible(true); 
        this.setVisible(false);
    }
}
