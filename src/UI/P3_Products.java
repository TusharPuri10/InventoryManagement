package UI;

import database.employee;
import database.product;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class P3_Products extends JPanel {
    private JTextField searchField;
    private static JTable table;
    private JButton clearButton;
    private JPanel searchPanel;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JButton addButton,deleteButton,editButton;
    private JPanel topPanel;
    private String[] headers;
    private Object data[][];
    private int[] columnWidths;
    static String username;
    static int userid;
    static Connection connection;

    public P3_Products(String userType, Connection connection,String username, int userid) {
        this.connection = connection;
        this.userid = userid;
        this.username = username;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Table headers
        if(userType.equals("Administrator"))
            headers = new String[]{"Product ID", "Product Name:", "Category", "Cost Price", "Selling Price", "Quantity", "Minimum Stock Level", "Maximum Stock Level", "Reorder Point", "Manufacturer", "Manufacturer Code", "Lead Time"};
        else
            headers = new String[]{"Product ID", "Product Name:", "Category", "Quantity", "Minimum Stock Level", "Maximum Stock Level", "Reorder Point", "Lead Time"};

        // Retrieve data from MySQL database if not already retrieved
        if (data == null) {
            data = product.getAllproducts(userType, connection);
        }


        // Create a table model with the data
        DefaultTableModel model = new DefaultTableModel(data, headers);

        // Create the table with the model
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font for table cells
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Set font for column headers
        table.setRowHeight(25); // Set row height
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto resizing of columns

        // Adjust column widths
        if(userType.equals("Administrator"))
            columnWidths = new int[]{100, 150, 150, 200, 120, 180, 180, 120, 150, 120, 120, 120};
        else
            columnWidths = new int[]{100, 150, 150, 180, 180, 120, 150, 120};
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
// Create a panel for the buttons and set its layout
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align the buttons to the right

// PURCHASE BUTTON
       if (userType.equals("Administrator")) {
        // Create the add button
        addButton = new JButton("Purchase");
        addButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Increase the font size
        // Set the preferred size for the button
        addButton.setPreferredSize(new Dimension(140, 28)); // Adjust the size as needed
        // Add the add button to the panel
        buttonPanel.add(addButton);
        }


// DELETE BUTTON
        String text ;
        if (userType.equals("Administrator")) {
            text = "Delete";
        }
        else
            text = "Sell";
         //Create the delete button
        deleteButton = new JButton(text);
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Increase the font size
        // Set the preferred size for the button
        deleteButton.setPreferredSize(new Dimension(140, 28)); // Adjust the size as needed
        // Add the delete button to the panel
        buttonPanel.add(deleteButton);

// EDIT BUTTON
        if (userType.equals("Administrator")) {
            editButton = new JButton("Edit");
            editButton.setFont(new Font("Arial", Font.PLAIN, 14));
            editButton.setPreferredSize(new Dimension(140, 28));
            buttonPanel.add(editButton);
        }

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

        if (userType.equals("Administrator")) {
            // addButton action listener
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            showAddProductDialog(connection);
                        }
                    });
                }
            });
            // editButton action listener

            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    int selectedColumn = table.getSelectedColumn();
                    if (selectedRow != -1 && selectedColumn != -1) {
                        // Get the value of the selected cell
                        Object cellValue = table.getValueAt(selectedRow, selectedColumn);
                        // Perform the edit operation on the cellValue
                        // ...
                        // Update the table model or underlying data source if needed
                        // ...
                    }
                }
            });
        }
            // deleteButton action listener

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Perform the delete operation on the selected row
                        if(userType.equals("Employee"))
                        {
                            SwingUtilities.invokeLater(new Runnable() { public void run() {
                                    sellProductDialog(selectedRow);
                                }
                            });
                        }
                        else {
                            product.deleteSelectedRow(connection,selectedRow, table, (DefaultTableModel) table.getModel());
                        }
                    }
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

    private void clearHighlight() { table.clearSelection();}

    //addProduct
    private void showAddProductDialog(Connection connection) {
        JFrame frame = new JFrame("Add New Product");
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel generalInfoPanel = new JPanel(new GridLayout(12, 2, 10, 10));
        generalInfoPanel.setBorder(BorderFactory.createTitledBorder("General Information"));

        generalInfoPanel.add(new JLabel("Product ID:"));
        JTextField productIdField = new JTextField();
        generalInfoPanel.add(productIdField);

        generalInfoPanel.add(new JLabel("Product Name:"));
        JTextField productNameField = new JTextField();
        generalInfoPanel.add(productNameField);

        generalInfoPanel.add(new JLabel("Category:"));
        JTextField categoryField = new JTextField();
        generalInfoPanel.add(categoryField);

        generalInfoPanel.add(new JLabel("Cost Price:"));
        JTextField costPriceField = new JTextField();
        generalInfoPanel.add(costPriceField);

        generalInfoPanel.add(new JLabel("Selling Price:"));
        JTextField sellingPriceField = new JTextField();
        generalInfoPanel.add(sellingPriceField);

        generalInfoPanel.add(new JLabel("Quantity:"));
        JTextField quantityField = new JTextField();
        generalInfoPanel.add(quantityField);

        generalInfoPanel.add(new JLabel("Minimum Stock Level:"));
        JTextField minimumStockLevelField = new JTextField();
        generalInfoPanel.add(minimumStockLevelField);

        generalInfoPanel.add(new JLabel("Maximum Stock Level:"));
        JTextField maximumStockLevelField = new JTextField();
        generalInfoPanel.add(maximumStockLevelField);

        generalInfoPanel.add(new JLabel("Reorder Point:"));
        JTextField reorderPointField = new JTextField();
        generalInfoPanel.add(reorderPointField);

        generalInfoPanel.add(new JLabel("Manufacturer:"));
        JTextField manufacturerField = new JTextField();
        generalInfoPanel.add(manufacturerField);

        generalInfoPanel.add(new JLabel("Manufacturer Code:"));
        JTextField manufacturerCodeField = new JTextField();
        generalInfoPanel.add(manufacturerCodeField);

        generalInfoPanel.add(new JLabel("Lead Time:"));
        JTextField leadTimeField = new JTextField();
        generalInfoPanel.add(leadTimeField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Product");

        addButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
                try {
                    String productID = productIdField.getText();
                    String productName = productNameField.getText();
                    String category = categoryField.getText();
                    BigDecimal costPrice = new BigDecimal(costPriceField.getText());
                    BigDecimal sellingPrice = new BigDecimal(sellingPriceField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    int minimumStockLevel = Integer.parseInt(minimumStockLevelField.getText());
                    int maximumStockLevel = Integer.parseInt(maximumStockLevelField.getText());
                    int reorderPoint = Integer.parseInt(reorderPointField.getText());
                    String manufacturer = manufacturerField.getText();
                    String manufacturerCode = manufacturerCodeField.getText();
                    int leadTime = Integer.parseInt(leadTimeField.getText());

                    if (isNullOrEmpty(productName) || isNullOrEmpty(category) || isNullOrEmpty(costPriceField.getText())
                            || isNullOrEmpty(sellingPriceField.getText()) || isNullOrEmpty(quantityField.getText())
                            || isNullOrEmpty(minimumStockLevelField.getText()) || isNullOrEmpty(maximumStockLevelField.getText())
                            || isNullOrEmpty(reorderPointField.getText()) || isNullOrEmpty(manufacturer) || isNullOrEmpty(manufacturerCode)
                            || isNullOrEmpty(leadTimeField.getText())) {
                        String errorMessage = "Please fill all the fields.";
                        JOptionPane.showMessageDialog(null, errorMessage, "Empty Fields", JOptionPane.ERROR_MESSAGE);
                        // Dispose the dialog window
                        frame.dispose();
                        showAddProductDialog(connection);
                    }
                    else
                    {
                        // Adding row in database
                        int productId = Integer.parseInt(productID);
                        if(product.addProduct(connection,productId, productName, category, costPrice, sellingPrice, quantity, minimumStockLevel,
                                maximumStockLevel, reorderPoint, manufacturer, manufacturerCode, leadTime, table, (DefaultTableModel) table.getModel())==1)
                        {
                            frame.dispose();
                            showAddProductDialog(connection);
                        }
                        else{
                            // Dispose the dialog window
                            frame.dispose();
                        }
                    }
                } catch (NumberFormatException ex) {
                    String errorMessage = "Invalid field input format. Please enter a valid input.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Invalid field input format", JOptionPane.ERROR_MESSAGE);
                    // Dispose the dialog window
                    frame.dispose();
                    showAddProductDialog(connection);
                }
            }
        });
        buttonPanel.add(addButton);

        panel.add(generalInfoPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    //sellButton
    private void sellProductDialog(int selectedProduct){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                P0_UI_Main.PageButtonActionListener listener = new P0_UI_Main.PageButtonActionListener("Retailers","Products",1,selectedProduct);
                ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");
                listener.actionPerformed(event);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "Please select a retailer from the table.", "Choose Retailer", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        t1.start();
        t2.start();
    }

    public static void sellDialog(int selectedProduct, int selectedRetailer) {

        // Create and display the sell dialog
        JDialog sellDialog = new JDialog();
        sellDialog.setTitle("Sell Product");
        sellDialog.setSize(400, 300);
        sellDialog.setLocationRelativeTo(null);
        sellDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create and add components to the sell dialog
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
        panel.add(quantityLabel);
        panel.add(quantityField);

        // Sell button action listener
        JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(e -> {
            // Get the quantity
            int quantity = Integer.parseInt(quantityField.getText());

            // Perform the sell operation using the selected product, retailer, and quantity
            // ...
            try {
                product.sellProduct(quantity,selectedProduct,selectedRetailer,username,userid,table,(DefaultTableModel) table.getModel());
                // Close the sell dialog
                SwingUtilities.invokeLater(() -> sellDialog.dispose());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


        });
        panel.add(sellButton);
        sellDialog.add(panel);
        sellDialog.setVisible(true);
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
