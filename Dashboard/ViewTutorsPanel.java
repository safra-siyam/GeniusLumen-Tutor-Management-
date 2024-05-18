package GeniusLumen.Dashboard;

import GeniusLumen.connection.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewTutorsPanel extends JPanel {
    private JTextArea tutorsTextArea;

    public ViewTutorsPanel() {
        setLayout(new BorderLayout());
        initialize();
        fetchAndDisplayTutors();
    }

     private void initialize() {

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);


        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }

            private void goBack() {
               // CardLayout cardLayout = (CardLayout) getParent().getLayout();
               // cardLayout.show(getParent(), "Dashboard");
                //new Dashboard();
                new Dashboard();

                Container parent = ViewTutorsPanel.this.getParent();
                while (parent != null && !(parent instanceof JFrame)) {
                    parent = parent.getParent();
                }
                if (parent != null) {
                    JFrame frame = (JFrame) parent;
                    frame.dispose();
                    //new Dashboard();
                }
            }

        });

        panel.add(goBackButton, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(400, 300));

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new BorderLayout());

        tutorsTextArea = new JTextArea();
        tutorsTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(tutorsTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private void fetchAndDisplayTutors() {
        try {
            String url = "jdbc:mysql://localhost:3306/gl";
            String username = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            try {
                System.out.println("Connected to the database.");
                String sql = "SELECT FirstName, LastName, Email, Phone, Subjects FROM Tutors";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    ResultSet resultSet = preparedStatement.executeQuery();

                    StringBuilder tutorInfo = new StringBuilder();
                    while (resultSet.next()) {
                        String firstName = resultSet.getString("FirstName");
                        String lastName = resultSet.getString("LastName");
                        String email = resultSet.getString("Email");
                        String phone = resultSet.getString("Phone");
                        String subjects = resultSet.getString("Subjects");

                        tutorInfo.append("Name: ").append(firstName).append(" ").append(lastName).append("\n");
                        tutorInfo.append("Email: ").append(email).append("\n");
                        tutorInfo.append("Phone: ").append(phone).append("\n");
                        tutorInfo.append("Subjects: ").append(subjects).append("\n\n");
                    }

                    tutorsTextArea.setText(tutorInfo.toString());
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(ViewTutorsPanel.this, "Error fetching tutors. Please try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewTutorsPanel();
            }
        });
    }
}


