package GeniusLumen.Dashboard;
import GeniusLumen.connection.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AssignmentManager extends JPanel {
    private JTextArea tutorsTextArea;
    private JTextField tutorIdField;
    private JTextField courseIdField;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField dueDateField;
    private JTextField maxPointsField;

    public AssignmentManager() {
        setLayout(new BorderLayout());
        initialize();
        fetchAndDisplayAssignment();
    }
    private void initialize() {

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        JButton addAssignmentButton = new JButton("Add Assignment");
        addAssignmentButton.setPreferredSize(new Dimension(100, 30));
        addAssignmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAssignment();
            }
        });

        JButton goBackButton = new JButton("Go Back");

        goBackButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

                goBack();
            }
            private void goBack() {
                new Dashboard();

                Container parent = AssignmentManager.this.getParent();
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

        panel.add(addAssignmentButton);

        panel.add(goBackButton, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(20, 400));

        setVisible(true);
        }
    private void placeComponents(JPanel panel) {
        panel.setLayout(new BorderLayout());

        tutorsTextArea = new JTextArea();
        tutorsTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(tutorsTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        tutorIdField = new JTextField();
        courseIdField = new JTextField();
        titleField = new JTextField();
        descriptionArea = new JTextArea();
        dueDateField = new JTextField();
        maxPointsField = new JTextField();

        inputPanel.add(new JLabel("Tutor ID:    (eg-201)"));
        inputPanel.add(tutorIdField);
        inputPanel.add(new JLabel("Course ID:  (eg-101)"));
        inputPanel.add(courseIdField);
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(new JScrollPane(descriptionArea));
        inputPanel.add(new JLabel("Due Date:  (0000-00-00)"));
        inputPanel.add(dueDateField);
        inputPanel.add(new JLabel("Maximum Points:"));
        inputPanel.add(maxPointsField);

        panel.add(inputPanel, BorderLayout.NORTH);
    }

    private void addAssignment() {
        try {
            String url = "jdbc:mysql://localhost:3306/gl";
            String username = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            try {
                String sql = "INSERT INTO assignments (tutor_id, course_id, title, description, due_date, max_points) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, tutorIdField.getText());
                    preparedStatement.setString(2, courseIdField.getText());
                    preparedStatement.setString(3, titleField.getText());
                    preparedStatement.setString(4, descriptionArea.getText());
                    preparedStatement.setDate(5, java.sql.Date.valueOf(dueDateField.getText())); // Assuming dueDateField contains 'yyyy-MM-dd'
                    preparedStatement.setString(6, maxPointsField.getText());

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Assignment added successfully.");
                        // Optionally, you can fetch and display assignments again to show the updated list
                        fetchAndDisplayAssignment();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add assignment.");
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding assignment. Please try again.");
        }
    }

    private void fetchAndDisplayAssignment() {
        try {
            String url = "jdbc:mysql://localhost:3306/gl";
            String username = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            try {
                System.out.println("Connected to the database.");
                String sql = "SELECT id,tutor_id, course_id, title, description,due_date,max_points  FROM assignments";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    ResultSet resultSet = preparedStatement.executeQuery();

                    StringBuilder tutorInfo = new StringBuilder();
                    while (resultSet.next()) {
                        String id = resultSet.getString("ID");
                        String tutor_id = resultSet.getString("Tutor_ID");
                        String course_id = resultSet.getString("Course_ID");
                        String title = resultSet.getString("Title");
                        String description = resultSet.getString("Description");
                        String due_date = resultSet.getString("Due_Date");
                        String max_points = resultSet.getString("Max_Points");

                        tutorInfo.append("Assignment Number: ").append(id).append("\n");
                        tutorInfo.append("Tutor ID: ").append(tutor_id).append("\n");
                        tutorInfo.append("Course ID: ").append(course_id).append("\n");
                        tutorInfo.append("Title: ").append(title).append("\n\n");
                        tutorInfo.append("Description: ").append(description).append("\n");
                        tutorInfo.append("Due Date: ").append(due_date).append("\n");
                        tutorInfo.append("Maximum points: ").append(max_points).append("\n");
                    }

                    tutorsTextArea.setText(tutorInfo.toString());
                    tutorsTextArea.revalidate();
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(AssignmentManager.this, "Error fetching tutors. Please try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AssignmentManager();
            }
        });
    }
}



