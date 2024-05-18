package GeniusLumen.Login;

import GeniusLumen.connection.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class signup extends JFrame {

    private JTextField signupUsernameField;
    private JPasswordField signupPasswordField;
    private JPasswordField reenterPasswordField;

    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/gl";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    public signup() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Signup Form");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UIManager.put("nimbusBase", new Color(169, 169, 169));

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        JLabel titleLabel = new JLabel("Signup at GeniusLumen");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(70, 130, 180)); // Set your preferred color
        titleLabel.setBounds(80, 10, 250, 30);
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Perform action when the title is clicked
                System.out.println("Title Clicked!");
            }
        });
        panel.add(titleLabel);

        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel signupUserLabel = new JLabel("Username:");
        signupUserLabel.setBounds(80, 50, 80, 25);
        panel.add(signupUserLabel);

        signupUsernameField = new JTextField(20);
        signupUsernameField.setBounds(180, 50, 165, 25);
        panel.add(signupUsernameField);

        JLabel signupPasswordLabel = new JLabel("Password:");
        signupPasswordLabel.setBounds(80, 80, 80, 25);
        panel.add(signupPasswordLabel);

        signupPasswordField = new JPasswordField(20);
        signupPasswordField.setBounds(180, 80, 165, 25);
        panel.add(signupPasswordField);

        JLabel reenterPasswordLabel = new JLabel("Re-enter Password:");
        reenterPasswordLabel.setBounds(30, 110, 140, 25);
        panel.add(reenterPasswordLabel);

        reenterPasswordField = new JPasswordField(20);
        reenterPasswordField.setBounds(180, 110, 165, 25);
        panel.add(reenterPasswordField);

        JButton signupButton = new JButton("Signup");
        signupButton.setBounds(200, 150, 120, 25);
        panel.add(signupButton);

        JButton backBtn = new JButton("Back To Login");
        backBtn.setBounds(80, 150, 120, 25);
        panel.add(backBtn);

        backBtn.addActionListener(e -> {
            Login loginPage = new Login();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            frame.dispose();
            loginPage.setVisible(true);
        });

        signupButton.addActionListener(e -> performSignup());
    }

    private void performSignup() {
        String signupUsername = signupUsernameField.getText();
        char[] signupPassword = signupPasswordField.getPassword();
        char[] reenterPassword = reenterPasswordField.getPassword();

        if (signupUsername.isEmpty() || signupPassword.length == 0 || reenterPassword.length == 0) {
            JOptionPane.showMessageDialog(this, "Username and password fields cannot be empty");
            return;
        }

        if (!Arrays.equals(signupPassword, reenterPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please re-enter.");
            signupPasswordField.setText("");
            reenterPasswordField.setText("");
            return;
        }

        if (authenticateUser(signupUsername, new String(signupPassword))) {
            JOptionPane.showMessageDialog(this, "Signup successful for: " + signupUsername);
        } else {
            JOptionPane.showMessageDialog(this, "Signup failed for: " + signupUsername + ". Please enter valid credentials.");
            signupUsernameField.setText("");
            signupPasswordField.setText("");
            reenterPasswordField.setText("");
        }
    }


    private boolean authenticateUser(String username, String password) {
        DatabaseConnection conObj1 = new DatabaseConnection();
        try (Connection dbConnector1 = conObj1.getConnection();
             PreparedStatement preparedStatement = dbConnector1.prepareStatement("INSERT INTO Users(email, pwd) VALUES (?, ?)")) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Tutor Data inserted successfully.");
                return true;
            } else {
                System.out.println("Tutor Data insertion failed.");
                return false;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        } finally {
            conObj1.closeConnection();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(signup::new);
    }
}
