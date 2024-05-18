package GeniusLumen.Dashboard;

import GeniusLumen.Login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Dashboard extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public Dashboard() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("Tutor Management System Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        createComponents();

        setVisible(true);
        setResizable(true);
    }

    private void createComponents() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel buttonsPanel = new JPanel(new GridLayout(5, 1));

        JButton viewTutorsButton = new JButton("View Tutors");
        JButton addTutorButton = new JButton("Add Tutor");
        JButton viewSubjectsButton = new JButton("View Subjects & Assignments");
        JButton viewContactButton = new JButton("View Contact");
        JButton logoutButton = new JButton("Logout");

        int buttonWidth = 150;
        int buttonHeight = 30;

        viewTutorsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        addTutorButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        viewSubjectsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        viewContactButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        logoutButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        buttonsPanel.add(viewTutorsButton);
        buttonsPanel.add(addTutorButton);
        buttonsPanel.add(viewSubjectsButton);
        buttonsPanel.add(viewContactButton);
        buttonsPanel.add(logoutButton);

        mainPanel.add(buttonsPanel, BorderLayout.EAST);

        ViewTutorsPanel viewTutorsPanel = new ViewTutorsPanel();
        ViewContactPanel ViewContactPanel = new ViewContactPanel();

        JPanel addTutorPanel = new JPanel();
        addTutorPanel.add(new JLabel("Add Tutor Panel Content"));


        viewTutorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "viewTutorsPanel");
            }
        });

        addTutorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "RegisterTutor");
            }
        });


        viewSubjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "AssignmentManager");
            }
        });

        viewContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ViewContactPanel");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });


        ImageIcon logoImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pic3.jpg")));
        Image logo = logoImg.getImage();
        Image scaledImg = logo.getScaledInstance(400, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        JLabel imageLabel = new JLabel(scaledIcon);

        mainPanel.add(imageLabel, BorderLayout.WEST);

        //ViewTutorsPanel viewTutorsPanel = new ViewTutorsPanel();
        ViewContactPanel viewContactPanel = new ViewContactPanel();
        //JPanel addTutorPanel = new JPanel();
        //addTutorPanel.add(new JLabel("Add Tutor Panel Content"));
        JPanel assignmentManagerPanel = new AssignmentManager();
        RegisterTutor RegisterTutor = new RegisterTutor();

        cardPanel.add(buttonsPanel, BorderLayout.EAST);
        cardPanel.add(viewTutorsPanel, "viewTutorsPanel");
        cardPanel.add(RegisterTutor, "RegisterTutor");
        cardPanel.add(assignmentManagerPanel, "AssignmentManager");
        cardPanel.add(viewContactPanel, "ViewContactPanel");
        //cardPanel.add(registerTutor, "registerTutor");


        mainPanel.add(cardPanel, BorderLayout.CENTER);

        add(mainPanel);
    }


    private void logout() {
        dispose();
        new Login();
        //JOptionPane.showMessageDialog(this, "Logging out");
        //System.exit(0);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard());
    }
}

