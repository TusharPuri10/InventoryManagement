package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class P2_Retailers extends JPanel {
    public P2_Retailers() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Table headers
        String[] headers = {"Retailer ID", "Retailer Name", "Contact Person", "Contact Email", "Contact Phone", "Contact Address", "Shipping Address", "Payment Terms", "Preferred Shipping Method", "Tax ID", "Current Balance"};

        // Sample data for the table
        Object[][] data = {
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},{1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},

                // Add more rows as needed
        };

        // Add 100 rows of sample data
        for (int i = 3; i <= 40; i++) {
            Object[] row = new Object[11];
            row[0] = i;
            row[1] = "Retailer " + i;
            row[2] = "Contact Person " + i;
            row[3] = "contact" + i + "@example.com";
            row[4] = "123-456-" + String.format("%04d", i);
            row[5] = "Address " + i;
            row[6] = "Shipping Address " + i;
            row[7] = "Net " + (30 + i);
            row[8] = i % 2 == 0 ? "Express" : "Standard";
            row[9] = String.valueOf(1000000 + i);
            row[10] = 1000.00 + i * 100;
            data[i - 1] = row;
        }

        // Create a table model with the data
        DefaultTableModel model = new DefaultTableModel(data, headers);

        // Create the table with the model
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font for table cells
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Set font for column headers
        table.setRowHeight(25); // Set row height
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto resizing of columns

        // Adjust column widths
        int[] columnWidths = {100, 150, 150, 200, 120, 180, 180, 120, 150, 120, 120};
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
