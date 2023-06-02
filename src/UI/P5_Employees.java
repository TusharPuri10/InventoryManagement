package UI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class P5_Employees extends JPanel {
    public P5_Employees() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Table headers
        String[] headers =  {"Employee ID", "First Name", "Last Name", "Position/Job Title", "Department",
                "Contact Email", "Contact Phone", "Address", "Date of Birth", "Date of Joining",
                "Employment Status", "Manager"};
        // Sample data for the table
        Object[][] data = {
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"},
                {"E001", "John", "Doe", "Software Engineer", "Engineering", "john.doe@example.com",
                        "123-456-7890", "123, Main Street", "1990-05-15", "2015-08-01",
                        "Full-time", "Jane Manager"},
                {"E002", "Jane", "Smith", "Sales Manager", "Sales", "jane.smith@example.com",
                        "987-654-3210", "456, Elm Street", "1988-12-10", "2017-02-15",
                        "Full-time", "John Manager"}
                // Add more rows as needed
        };


        // Create a table model with the data
        DefaultTableModel model = new DefaultTableModel(data, headers);

        // Create the table with the model
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font for table cells
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Set font for column headers
        table.setRowHeight(25); // Set row height
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto resizing of columns

        // Adjust column widths
        int[] columnWidths = {100, 150, 150, 200, 120, 180, 180, 120, 150, 120, 120,120};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove default border
        scrollPane.setPreferredSize(new Dimension(800, 300)); // Set preferred size

        // Add the scroll pane to the panel
        add(scrollPane, BorderLayout.CENTER);

        // Create the add button
        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Increase the font size

// Set the preferred size for the button
        addButton.setPreferredSize(new Dimension(80, 40)); // Adjust the size as needed

// Create a panel for the button and set its layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align the button to the right

// Add the button to the panel
        buttonPanel.add(addButton);

// Add the button panel to the top of the panel
        add(buttonPanel, BorderLayout.NORTH);
    }
}