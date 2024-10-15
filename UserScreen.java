import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

public class UserScreen extends JFrame {
    private JLabel welcomeLabel;
    private JLabel balanceLabel;
    private JComboBox<String> languageBox;
    private ResourceBundle bundle;
    private String userName;
    private double userBalance = 1000.00; 
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton printButton;

    public UserScreen(String userName) {
        this.userName = userName;
        
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
        buttonPanel.add(withdrawButton);

        depositButton = new JButton();
        depositButton.setFocusPainted(false);
        depositButton.setBackground(new Color(34, 139, 34));
        depositButton.setForeground(Color.WHITE);
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
        setVisible(true);
    }

    private void updateTexts(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
        welcomeLabel.setText(bundle.getString("welcome_back") + " " + userName);
        balanceLabel.setText(bundle.getString("current_balance") + ": $" + String.format("%.2f", userBalance));
        withdrawButton.setText(bundle.getString("withdraw"));
        depositButton.setText(bundle.getString("deposit"));
        printButton.setText(bundle.getString("print"));
    }

}
