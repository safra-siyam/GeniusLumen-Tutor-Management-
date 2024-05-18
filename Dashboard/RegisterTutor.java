package GeniusLumen.Dashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//import static jdk.internal.agent.Agent.getText;

public class RegisterTutor extends JPanel {
    private JTextField firstNameField, lastNameField, emailField, phoneField, subjectsField;

    public RegisterTutor() {
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setPreferredSize(new Dimension(400, 300));

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new GridLayout(12, 2));

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();
        panel.add(firstNameLabel);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();
        panel.add(lastNameLabel);
        panel.add(lastNameField);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        panel.add(emailLabel);
        panel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();
        panel.add(phoneLabel);
        panel.add(phoneField);

        JLabel subjectsLabel = new JLabel("Subjects:");
        subjectsField = new JTextField();
        panel.add(subjectsLabel);
        panel.add(subjectsField);

        JButton registerButton = new JButton("Register Tutor");
        panel.add(registerButton);

        JButton backButton = new JButton("Back");
        panel.add(backButton);

        JLabel TutorsNameLabel = new JLabel("Tutor's Name:");
        JTextField TutorsNameField = new JTextField();
        panel.add(TutorsNameLabel);
        panel.add(TutorsNameField);

        JLabel cityLabel = new JLabel("City:");
        JTextField cityField = new JTextField();
        panel.add(cityLabel);
        panel.add(cityField);

        JLabel countryLabel = new JLabel("Country:");
        JTextField countryField = new JTextField();
        panel.add(countryLabel);
        panel.add(countryField);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        JTextField phoneNumberField = new JTextField();
        panel.add(phoneNumberLabel);
        panel.add(phoneNumberField);

        JLabel tutorIDLabel = new JLabel("Tutor ID:");
        JTextField tutorIDField = new JTextField();

        JButton submitButton = new JButton("Submit Contact Info.");
        panel.add(submitButton);

        JButton clearButton = new JButton("Clear");
        panel.add(clearButton);

        JButton deleteButton = new JButton("Delete");
        panel.add(deleteButton);

        JButton updateButton = new JButton("Update");
        panel.add(updateButton);


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTutorContactInfo(TutorsNameField.getText());
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTutorContactInfo(
                        TutorsNameField.getText(),
                        cityField.getText(),
                        countryField.getText(),
                        phoneNumberField.getText()
                );
            }
        });


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerTutor();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Dashboard();

                Container parent = RegisterTutor.this.getParent();
                while (parent != null && !(parent instanceof JFrame)) {
                    parent = parent.getParent();
                }
                if (parent != null) {
                    JFrame frame = (JFrame) parent;
                    frame.dispose();
                }
            }
        });


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitTutorContactInfo(
                        TutorsNameField.getText(),
                        cityField.getText(),
                        countryField.getText(),
                        phoneNumberField.getText(),
                        tutorIDField.getText()
                );
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstNameField.setText("");
                lastNameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                subjectsField.setText("");
                TutorsNameField.setText("");
                cityField.setText("");
                countryField.setText("");
                phoneNumberField.setText("");
                tutorIDField.setText("");
            }
        });
    }


    private void deleteTutorContactInfo(String tutorName) {
        try {
            String url = "jdbc:mysql://localhost:3306/gl";
            String username = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            try {
                String sql = "DELETE FROM ContactInfo WHERE TutorName = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, tutorName);

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(RegisterTutor.this, "Tutor contact info deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(RegisterTutor.this, "Tutor contact info not found.");
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(RegisterTutor.this, "Error deleting tutor contact info. Please try again.");
        }
    }

    private void updateTutorContactInfo(String tutorName, String city, String country, String phoneNumber) {
        try {
            String url = "jdbc:mysql://localhost:3306/gl";
            String username = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            try {
                String sql = "UPDATE ContactInfo SET City = ?, Country = ?, PhoneNumber = ? WHERE TutorName = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, city);
                    preparedStatement.setString(2, country);
                    preparedStatement.setString(3, phoneNumber);
                    preparedStatement.setString(4, tutorName);

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(RegisterTutor.this, "Tutor contact info updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(RegisterTutor.this, "Tutor contact info not found.");
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(RegisterTutor.this, "Error updating tutor contact info. Please try again.");
        }
    }

    private void registerTutor() {
        try {
            String url = "jdbc:mysql://localhost:3306/gl";
            String username = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            try {
                String sql = "INSERT INTO Tutors (FirstName, LastName, Email, Phone, Subjects) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, firstNameField.getText());
                    preparedStatement.setString(2, lastNameField.getText());
                    preparedStatement.setString(3, emailField.getText());
                    preparedStatement.setString(4, phoneField.getText());
                    preparedStatement.setString(5, subjectsField.getText());

                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(RegisterTutor.this, "Tutor registered successfully!");
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(RegisterTutor.this, "Error registering tutor. Please try again.");
        }
    }

    private void submitTutorContactInfo( String TutorsName,String city, String country, String phoneNumber, String tutorID) {
        /*
        if (!validateLabelFields()) {

            return;
        }*/

        if (TutorsName.isEmpty() || city.isEmpty() || country.isEmpty() || phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(RegisterTutor.this, "Please fill in all the fields.");
            return;
        }

        try {
            String url = "jdbc:mysql://localhost:3306/gl";
            String username = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            try {
                String sql = "INSERT INTO ContactInfo (TutorName, City, Country, PhoneNumber) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, TutorsName);
                    preparedStatement.setString(2, city);
                    preparedStatement.setString(3, country);
                    preparedStatement.setString(4, phoneNumber);
                    //preparedStatement.setString(5, tutorID);

                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(RegisterTutor.this, "Tutor contact info submitted successfully!");
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(RegisterTutor.this, "Error submitting tutor contact info. Please try again.");
        }
    }
    private boolean validateLabelFields() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || emailField.getText().isEmpty()
                || phoneField.getText().isEmpty() || subjectsField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(RegisterTutor.this, "Please fill in all the fields.");
            return false;
        }
        return true;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegisterTutor();
            }
        });
    }

    public JTextField getFirstNameField() {
        return firstNameField;
    }

    public JTextField getLastNameField() {
        return lastNameField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getPhoneField() {
        return phoneField;
    }

    public JTextField getSubjectsField() {
        return subjectsField;
    }

}
