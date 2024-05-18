package GeniusLumen.Dashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewContactPanel extends JPanel {

    private JTextArea tutorsTextArea;

    public ViewContactPanel() {
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

                Container parent = ViewContactPanel.this.getParent();
                while (parent != null && !(parent instanceof JFrame)) {
                    parent = parent.getParent();
                }
                if (parent != null) {
                    JFrame frame = (JFrame) parent;
                    frame.dispose();
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
                String sql = "SELECT ContactID,TutorName,City,Country,PhoneNumber  FROM ContactInfo";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    ResultSet resultSet = preparedStatement.executeQuery();

                    StringBuilder tutorContactInfo = new StringBuilder();
                    while (resultSet.next()) {
                        String ContactID = resultSet.getString("ContactID");
                        String TutorName = resultSet.getString("TutorName");
                        String City = resultSet.getString("City");
                        String Country = resultSet.getString("Country");
                        String PhoneNumber = resultSet.getString("PhoneNumber");

                        tutorContactInfo.append("ID: ").append(ContactID).append("\n");
                        tutorContactInfo.append("Name: ").append(TutorName).append("\n");
                        tutorContactInfo.append("City: ").append(City).append("\n");
                        tutorContactInfo.append("Country: ").append(Country).append("\n");
                        tutorContactInfo.append("PhoneNumber: ").append(PhoneNumber).append("\n\n\n");
                    }

                    tutorsTextArea.setText(tutorContactInfo.toString());
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(ViewContactPanel.this, "Error fetching tutors. Please try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewContactPanel();
            }
        });
    }
}
