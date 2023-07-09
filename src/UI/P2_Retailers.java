package UI;

import database.employee;
import database.product;
import database.retailer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.math.BigDecimal;

public class P2_Retailers extends JPanel {
    private JTextField searchField;
    private JTable table;
    private JButton clearButton;
    private JPanel searchPanel;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JButton addButton;
    private JPanel topPanel;
    private String[] headers;
    private Object data[][];
    private int[] columnWidths;

    public P2_Retailers(String userType, Connection connection) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        if(userType.equals("Administrator"))
            headers = new String[]{"Retailer ID", "Retailer Name", "Contact Person", "Contact Email", "Contact Phone", "Contact Address", "Shipping Address", "Payment Terms", "Preferred Shipping Method", "Tax ID", "Current Balance"};
        else
            headers = new String[]{"Retailer ID", "Retailer Name", "Contact Person", "Contact Email", "Contact Phone", "Contact Address", "Shipping Address","Preferred Shipping Method"};

        // Retrieve data from MySQL database if not already retrieved
        if (data == null) {
            data = retailer.getAllRetailers(userType, connection);
        }


        // Create a table model with the data
        DefaultTableModel model = new DefaultTableModel(data, headers);

        // Create the table with the model
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font for table cells
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Set font for column headers
        table.setRowHeight(25); // Set row height
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto resizing of columns

        if(userType.equals("Administrator"))
            columnWidths = new int[]{100, 150, 150, 200, 120, 180, 180, 120, 150, 120, 120};
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

        // Initialize buttonPanel outside the if statement
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

//ADD BUTTON
        if(userType.equals("Administrator")) {
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
        if(userType.equals("Administrator"))
        {
            topPanel.add(buttonPanel, BorderLayout.EAST);
            topPanel.add(searchPanel, BorderLayout.WEST);
            // Add the top panel to the top of the main panel
            add(topPanel, BorderLayout.NORTH);
        }
        else
        {
            add(searchPanel, BorderLayout.NORTH);
        }


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

        // addButton action listener
        if(userType.equals("Administrator"))
        {
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            showAddRetailerDialog();
                        }
                    });
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
        }

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

    private void showAddRetailerDialog() {
        JFrame frame = new JFrame("Add New Retailer");
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel generalInfoPanel = new JPanel(new GridLayout(11, 2, 10, 10));
        generalInfoPanel.setBorder(BorderFactory.createTitledBorder("General Information"));

        generalInfoPanel.add(new JLabel("Retailer ID:"));
        JTextField retailerIdField = new JTextField();
        generalInfoPanel.add(retailerIdField);

        generalInfoPanel.add(new JLabel("Retailer Name:"));
        JTextField retailerNameField = new JTextField();
        generalInfoPanel.add(retailerNameField);

        generalInfoPanel.add(new JLabel("Contact Person:"));
        JTextField  contactPersonField = new JTextField();
        generalInfoPanel.add(contactPersonField);


        generalInfoPanel.add(new JLabel("Contact Email:"));
        JTextField emailField = new JTextField();
        generalInfoPanel.add(emailField);

        generalInfoPanel.add(new JLabel("Contact Phone:"));
        JTextField phoneField = new JTextField();
        generalInfoPanel.add(phoneField);

        generalInfoPanel.add(new JLabel("contact Address:"));
        JTextField addressField = new JTextField();
        generalInfoPanel.add(addressField);

        generalInfoPanel.add(new JLabel("Shipping address:"));
        JTextField shippingAddressField = new JTextField();
        generalInfoPanel.add(shippingAddressField);

        generalInfoPanel.add(new JLabel("payment terms:"));
        JTextField paymentTermsField = new JTextField();
        generalInfoPanel.add(paymentTermsField);

        generalInfoPanel.add(new JLabel("preferred shipping mode:"));
        JTextField preferredShippingModeField = new JTextField();
        generalInfoPanel.add(preferredShippingModeField);

        generalInfoPanel.add(new JLabel("taxID:"));
        JTextField taxIDField = new JTextField();
        generalInfoPanel.add(taxIDField);

        generalInfoPanel.add(new JLabel("currentBalance:"));
        JTextField currentBalanceField = new JTextField();
        generalInfoPanel.add(currentBalanceField);

//        panel.add(generalInfoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Retailer");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String retailerID = retailerIdField.getText();

                if (retailerID.isEmpty()) {
                    String errorMessage = "Please fill all the fields.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Empty Fields", JOptionPane.ERROR_MESSAGE);
                    // Dispose the dialog window
                    frame.dispose();
                } else {
                    try {
                        int retailerId = Integer.parseInt(retailerID);

                        String retailerName = retailerNameField.getText();
                        String contactPerson = contactPersonField.getText();
                        String email = emailField.getText();
                        String phone = phoneField.getText();
                        String address = addressField.getText();
                        String shippingAddress = shippingAddressField.getText();
                        String paymentTerms = paymentTermsField.getText();
                        String preferredShippingMode = preferredShippingModeField.getText();
                        String taxID = taxIDField.getText();
                        BigDecimal currentBalance = new BigDecimal(currentBalanceField.getText());



                        if (isNullOrEmpty(retailerName) || isNullOrEmpty(contactPerson) || isNullOrEmpty(email) || isNullOrEmpty(phone)
                                || isNullOrEmpty(address) || isNullOrEmpty(shippingAddress) || isNullOrEmpty(paymentTerms)
                                || isNullOrEmpty(preferredShippingMode) || isNullOrEmpty(taxID)) {
                            String errorMessage = "Please fill all the fields.";
                            JOptionPane.showMessageDialog(null, errorMessage, "Empty Fields", JOptionPane.ERROR_MESSAGE);
                            // Dispose the dialog window
                            frame.dispose();
                        }

                        // Adding row in database
                        retailer.addRetailer(retailerId, retailerName, contactPerson, email, phone, address,
                                shippingAddress, paymentTerms, preferredShippingMode, taxID, currentBalance, table, (DefaultTableModel) table.getModel());
                        // Dispose the dialog window
                        frame.dispose();
                    } catch (NumberFormatException ex) {
                        String errorMessage = "Invalid Employee ID format. Please enter a valid integer.";
                        JOptionPane.showMessageDialog(null, errorMessage, "Invalid Employee ID", JOptionPane.ERROR_MESSAGE);
                        // Dispose the dialog window
                        frame.dispose();
                    }
                }
            }
        });

        buttonPanel.add(addButton);

        panel.add(generalInfoPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private void clearHighlight() {
        table.clearSelection();
    }
}
