package GeniusLumen.connection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class contactTable extends JPanel {

    private JTable table;

    public contactTable() {
        setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new Object[]{"ContactID", "TutorName", "City", "Country", "PhoneNumber"});

        fetchDataFromDatabase(model);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      //  MouseActionImpl mouseAction = new MouseActionImpl(table);
      //  table.addMouseListener(mouseAction);
    }

    private void fetchDataFromDatabase(DefaultTableModel model) {
        String url = "jdbc:mysql://localhost:3306/gl";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ContactInfo");
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String TutorName = rs.getString("Tutor Name");
                String City = rs.getString("City");
                String Country = rs.getString("Country");
                String PhoneNumber = rs.getString("PhoneNumber");

                model.addRow(new Object[]{ID, TutorName, City, Country, PhoneNumber});
            }

            rs.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
