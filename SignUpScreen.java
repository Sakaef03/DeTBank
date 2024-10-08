package detbank;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

public class SignUpScreen extends JFrame {
    private ResourceBundle bundle;
    private JLabel nameLabel;
    private JLabel agencyLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JButton createAccountButton;
    private JButton backButton;

    private JComboBox<String> languageBox;

    public SignUpScreen(LoginScreen loginScreen) {
        setTitle("Criar Conta - DeTBank");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

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
        JTextField nameField = new JTextField(15);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        mainPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);

        agencyLabel = new JLabel("Agência:");
        JTextField agencyField = new JTextField(15);
        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(agencyLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(agencyField, gbc);

        passwordLabel = new JLabel("Senha:");
        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridy = 4;
        gbc.gridx = 0;
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        confirmPasswordLabel = new JLabel("Confirmar Senha:");
        JPasswordField confirmPasswordField = new JPasswordField(15);
        gbc.gridy = 5;
        gbc.gridx = 0;
        mainPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(confirmPasswordField, gbc);

        createAccountButton = new JButton("Criar Conta");
        createAccountButton.setFocusPainted(false);
        createAccountButton.setBackground(new Color(34, 139, 34));
        createAccountButton.setForeground(Color.WHITE);
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(createAccountButton, gbc);

        backButton = new JButton("Voltar");
        backButton.setFocusPainted(false);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        gbc.gridy = 7;
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
                String name = nameField.getText();
                String agency = agencyField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (name.isEmpty() || agency.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "As senhas não coincidem.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(SignUpScreen.this, "Conta criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
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

    private void updateTexts(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
        nameLabel.setText(bundle.getString("name"));
        agencyLabel.setText(bundle.getString("agency"));
        passwordLabel.setText(bundle.getString("password"));
        confirmPasswordLabel.setText(bundle.getString("confirm_password"));
        createAccountButton.setText(bundle.getString("create_account"));
        backButton.setText(bundle.getString("back"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            SignUpScreen signUpScreen = new SignUpScreen(loginScreen);
            signUpScreen.setVisible(true);
        });
    }
}
