import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Random;
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

public class SignUpScreen extends JFrame {
    private ResourceBundle bundle;
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JButton createAccountButton;
    private JButton backButton;
    private JComboBox<String> languageBox;
    private LoginFactory loginFactory;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public SignUpScreen(LoginScreen loginScreen) throws Exception {
        setTitle("Criar Conta - DeTBank");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.loginFactory = new LoginFactory();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("<html><span style='color:green;'>De</span><span style='color:black;'>TB</span><span style='color:green;'>ank</span>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        String[] languages = {"Português (pt)", "English (en)", "Español (es)", "Русский (ru)", "Italiano (it)"};
        languageBox = new JComboBox<>(languages);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        mainPanel.add(languageBox, gbc);

        nameLabel = new JLabel("Nome:");
        this.nameField = new JTextField(15);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        mainPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);

        passwordLabel = new JLabel("Senha:");
        this.passwordField = new JPasswordField(15);
        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        confirmPasswordLabel = new JLabel("Confirmar Senha:");
        this.confirmPasswordField = new JPasswordField(15);
        gbc.gridy = 4;
        gbc.gridx = 0;
        mainPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(confirmPasswordField, gbc);

        createAccountButton = new JButton("Criar Conta");
        createAccountButton.setFocusPainted(false);
        createAccountButton.setBackground(new Color(34, 139, 34));
        createAccountButton.setForeground(Color.WHITE);
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(createAccountButton, gbc);

        backButton = new JButton("Voltar");
        backButton.setFocusPainted(false);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(backButton, gbc);

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

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registerNewUser();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginScreen.setVisible(true);
                dispose();
            }
        });

        updateTexts(new Locale("pt", "BR"));
    }

    private String generateAgencyNumber() {
        Random random = new Random();
        int agencyNumber = 100000 + random.nextInt(900000); 
        return String.valueOf(agencyNumber);
    }

    private void updateTexts(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
        nameLabel.setText(bundle.getString("name"));
        passwordLabel.setText(bundle.getString("password"));
        confirmPasswordLabel.setText(bundle.getString("confirm_password"));
        createAccountButton.setText(bundle.getString("create_account"));
        backButton.setText(bundle.getString("back"));
    }

    public void registerNewUser() throws Exception
    {
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(SignUpScreen.this, bundle.getString("error_message"), bundle.getString("error_title"), JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(SignUpScreen.this, bundle.getString("password_mismatch"), bundle.getString("error_title"), JOptionPane.ERROR_MESSAGE);
            } else {
                String agency = generateAgencyNumber(); 
                JOptionPane.showMessageDialog(SignUpScreen.this, bundle.getString("success_message") + "\n" + bundle.getString("user") + ": " + name + "\n" + bundle.getString("agency") + ": " + agency, bundle.getString("success_title"), JOptionPane.INFORMATION_MESSAGE);
                this.loginFactory.registerUser(agency,password);
            }
    }
}
