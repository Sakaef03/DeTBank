import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UserScreen extends JFrame {
    private JLabel welcomeLabel;
    private JLabel balanceLabel;
    private JComboBox<String> languageBox;
    private ResourceBundle bundle;
    private String userName;
    private double userBalance;
    private User user;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton printButton;
    private Socket clientSocket;
    private int agency;
    private CrudBD DB;

    public UserScreen(int agency) {
        this.agency=agency;
        this.DB = new CrudBD();
        this.user = new User(agency);
        this.userName= DB.getUserName(user);
        DB.showBalance(user);
        this.userBalance = DB.getBalance(user);
        this.user.setBalance(userBalance);

        setTitle("DETBANK");
        setSize(400, 300);
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

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("<html><span style='color:green;'>De</span><span style='color:black;'>TB</span><span style='color:green;'>ank</span></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(titleLabel, gbc);

        welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        contentPanel.add(welcomeLabel, gbc);

        JPanel balancePanel = new JPanel();
        balancePanel.setBackground(Color.LIGHT_GRAY);
        balancePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        balancePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 26));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balancePanel.add(balanceLabel);

        gbc.gridy = 2;
        contentPanel.add(balancePanel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        withdrawButton = new JButton();
        withdrawButton.setFocusPainted(false);
        withdrawButton.setBackground(new Color(34, 139, 34));
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog(bundle.getString("withdraw_prompt"));
                if (amountStr != null) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        new Thread(() -> handleWithdraw(amount)).start();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, bundle.getString("error_message"));
                    }
                }
            }
        });
        buttonPanel.add(withdrawButton);

        depositButton = new JButton();
        depositButton.setFocusPainted(false);
        depositButton.setBackground(new Color(34, 139, 34));
        depositButton.setForeground(Color.WHITE);
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog(bundle.getString("deposit_prompt"));
                if (amountStr != null) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        new Thread(() -> handleDeposit(amount)).start();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, bundle.getString("error_message"));
                    }
                }
            }
        });
        buttonPanel.add(depositButton);

        printButton = new JButton();
        printButton.setFocusPainted(false);
        printButton.setBackground(new Color(34, 139, 34));
        printButton.setForeground(Color.WHITE);
        buttonPanel.add(printButton);

        gbc.gridy = 3;
        contentPanel.add(buttonPanel, gbc);

        mainPanel.add(languagePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
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

        updateTexts(new Locale("pt", "BR"));
        updateBalance(userBalance);
        setVisible(true);

        try {
            clientSocket = new Socket("localhost", 3333);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleWithdraw(double amount) {
        try {
            ServerConnection connection = new ServerConnection(clientSocket);
            connection.sendRequest("withdraw", agency, userName, amount); 
            String response = connection.getResponse();
            JOptionPane.showMessageDialog(null, response);
            updateBalance(connection.getUpdatedBalance());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void handleDeposit(double amount) {
        try {
            ServerConnection connection = new ServerConnection(clientSocket);
            connection.sendRequest("deposit", agency, userName, amount);  
            String response = connection.getResponse();
            JOptionPane.showMessageDialog(null, response);
            updateBalance(connection.getUpdatedBalance());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    

    private void updateBalance(double newBalance) {
        this.userBalance = newBalance;
        //balanceLabel.setText(bundle.getString("current_balance") + ": $" + String.format("%.2f", userBalance));
    }

    private void updateTexts(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
        welcomeLabel.setText(bundle.getString("welcome_back") + ", " + userName);
        balanceLabel.setText(bundle.getString("current_balance") + ": $" + String.format("%.2f", userBalance));
        withdrawButton.setText(bundle.getString("withdraw"));
        depositButton.setText(bundle.getString("deposit"));
        printButton.setText(bundle.getString("print"));
    }

    public int getAgency() {
        return agency;
    }

    public void setAgency(int agency) {
        this.agency = agency;
    }
}
