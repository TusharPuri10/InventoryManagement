package UI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class P2_Retailers extends JPanel {
    private JTextField searchField;
    private JTable table;
    private JButton clearButton;
    private JPanel searchPanel;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JButton addButton;
    private JPanel topPanel;

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
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00},
                {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
                {2, "Retailer 2", "Jane Smith", "jane.smith@example.com", "987-654-3210", "Address 2", "Address 2", "Net 60", "Standard", "87654321", 2500.00}, {1, "Retailer 1", "John Doe", "john.doe@example.com", "123-456-7890", "Address 1", "Address 1", "Net 30", "Express", "12345678", 1000.00},
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


        // Create a table model with the data
        DefaultTableModel model = new DefaultTableModel(data, headers);

        // Create the table with the model
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font for table cells
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Set font for column headers
        table.setRowHeight(25); // Set row height
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto resizing of columns

        // Adjust column widths
        int[] columnWidths = {100, 150, 150, 200, 120, 180, 180, 120, 150, 120, 120};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        //Scroll Panel
        // Create a scroll pane for the table
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove default border
        scrollPane.setPreferredSize(new Dimension(800, 300)); // Set preferred size

        // Add the scroll pane to the panel
        add(scrollPane, BorderLayout.CENTER);

//ADD BUTTON TODO: @Divynash2042 add functionality and UI component
        // Create the add button
        addButton = new JButton("Add New");
        addButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Increase the font size
        // Set the preferred size for the button
        addButton.setPreferredSize(new Dimension(140, 28)); // Adjust the size as needed
        // Create a panel for the button and set its layout
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align the button to the right
        // Add the button to the panel
        buttonPanel.add(addButton);

//SEARCH BUTTON and TEXT FIELD
        // Create the search panel
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        clearButton = new JButton("Clear");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(clearButton);

        // Increase the height of searchField
        Dimension fieldSize = searchField.getPreferredSize();
        fieldSize.height = 30; // Set the desired height
        searchField.setPreferredSize(fieldSize);

        // Increase the size of text you can input in it
        Font font = searchField.getFont();
        Font largerFont = font.deriveFont(font.getSize() + 4f); // Increase the font size by 4
        searchField.setFont(largerFont);

// Create a container panel to hold both the button panel and search panel
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(searchPanel, BorderLayout.WEST);

// Add the top panel to the top of the main panel
        add(topPanel, BorderLayout.NORTH);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        searchField.setText("");
                    }
                });
                clearHighlight();
            }
        });

        // Add a document listener to the search field for real-time searching
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchAndHighlight();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchAndHighlight();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchAndHighlight();
            }
        });

//        pack(); Used if I Extended JFrame
        setVisible(true);

    }

    private void searchAndHighlight() {
        if (searchField == null) {
            System.out.println("searchField is null");
            return;
        }
        String searchText = searchField.getText();
        clearHighlight();


        if (searchText.isEmpty()) {
            return;
        }

        for (int row = 0; row < table.getRowCount(); row++) {
            for (int col = 0; col < table.getColumnCount(); col++) {
                Object value = table.getValueAt(row, col);
                if (value != null && value.toString().equalsIgnoreCase(searchText)) {
                    table.addRowSelectionInterval(row, row);
                    break;
                }
            }
        }
    }

    private void clearHighlight() {
        table.clearSelection();
    }
}
