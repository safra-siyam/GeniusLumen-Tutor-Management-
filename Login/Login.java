package GeniusLumen.Login;

import GeniusLumen.Dashboard.Dashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel titleLabel;

    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/gl";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    public Login() {
        setTitle("GeniusLumen Login Form");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        titleLabel = new JLabel("Login at GeniusLumen");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setBounds(50, 50, 300, 30);
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Title Clicked!");
            }
        });
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        panel.add(userLabel);

        usernameField = new JTextField(20);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        JButton signupButton = new JButton("Signup");
        panel.add(signupButton);

        userLabel.setBounds(80, 150, 80, 25);
        usernameField.setBounds(180, 150, 165, 25);
        passwordLabel.setBounds(80, 180, 80, 25);
        passwordField.setBounds(180, 180, 165, 25);
        loginButton.setBounds(100, 220, 80, 25);
        signupButton.setBounds(200, 220, 80, 25);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openSignupPage();
            }
        });
    }

    private void performLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        if (authenticateUser(username, new String(password))) {
            //JOptionPane.showMessageDialog(this, "Login successful for: " + username);
            System.out.println("Login successful for: " + username);
            openDashboard();

        } else {
            JOptionPane.showMessageDialog(this, "Login failed. Invalid credentials.");
            System.out.println("Login failed. Invalid credentials.");
        }
    }

    private boolean authenticateUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM Users WHERE email = ? AND Pwd = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openSignupPage() {
        new signup();
    }

    private void openDashboard() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Dashboard().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
    }
}
