package UI;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Arrays;
import database.employee;
import javax.swing.text.MaskFormatter;

public class P5_Employees extends JPanel {
    private JTextField searchField;
    public static JTable table;
    private JButton clearButton;
    private JPanel searchPanel;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JButton addButton;
    private JPanel topPanel;
    private JFormattedTextField dobField;

    private Object[][] data; // Stores the retrieved data
    public P5_Employees(Connection connection) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Table headers
        String[] headers = {"Employee ID", "First Name", "Last Name",
                "Contact Email", "Contact Phone", "Address", "Date of Birth", "Date of Joining",
                "Employment Status"};

        // Retrieve data from MySQL database if not already retrieved
        if (data == null) {
            data = employee.getAllEmployees(connection);
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
        int[] columnWidths = {100, 150, 150, 180, 180, 120, 150, 120, 120};
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

//ADD BUTTON
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

        // addButton action listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        showAddEmployeeDialog();
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
    private void showAddEmployeeDialog() {
        JFrame frame = new JFrame("Add New Employee");
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel generalInfoPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        generalInfoPanel.setBorder(BorderFactory.createTitledBorder("General Information"));

        generalInfoPanel.add(new JLabel("Employee ID:"));
        JTextField employeeIdField = new JTextField();
        generalInfoPanel.add(employeeIdField);

        generalInfoPanel.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField();
        generalInfoPanel.add(firstNameField);

        generalInfoPanel.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField();
        generalInfoPanel.add(lastNameField);

        generalInfoPanel.add(new JLabel("Contact Email:"));
        JTextField emailField = new JTextField();
        generalInfoPanel.add(emailField);

        generalInfoPanel.add(new JLabel("Contact Phone:"));
        JTextField phoneField = new JTextField();
        generalInfoPanel.add(phoneField);

        generalInfoPanel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField();
        generalInfoPanel.add(addressField);

        MaskFormatter dobFormatter;
        try {
            dobFormatter = new MaskFormatter("####-##-##");
            dobFormatter.setPlaceholderCharacter('_');
            dobField = new JFormattedTextField(dobFormatter);
            dobField.setFont(new Font("Arial", Font.PLAIN, 12));
            dobField.setPreferredSize(new Dimension(150, 25));
        } catch (Exception e) {
            e.printStackTrace();
        }

        generalInfoPanel.add(new JLabel("Date of Birth:"));
        generalInfoPanel.add(dobField);

        generalInfoPanel.add(new JLabel("Employment Status:"));
        JTextField employmentStatusField = new JTextField();
        generalInfoPanel.add(employmentStatusField);

        JPanel credentialsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        credentialsPanel.setBorder(BorderFactory.createTitledBorder("Setting up credentials"));

        credentialsPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        Font usernameFont = usernameField.getFont().deriveFont(Font.PLAIN, 16); // Increase text size
        usernameField.setFont(usernameFont);
        Dimension usernameFieldSize = usernameField.getPreferredSize();
        usernameFieldSize.height = 20; // Decrease text field height
        usernameField.setPreferredSize(usernameFieldSize);
        credentialsPanel.add(usernameField);

        credentialsPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        Font passwordFont = passwordField.getFont().deriveFont(Font.PLAIN, 16); // Increase text size
        passwordField.setFont(passwordFont);
        Dimension passwordFieldSize = passwordField.getPreferredSize();
        passwordFieldSize.height = 20; // Decrease text field height
        passwordField.setPreferredSize(passwordFieldSize);
        credentialsPanel.add(passwordField);

        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String employeeID = employeeIdField.getText();

                if (employeeID.isEmpty()) {
                    String errorMessage = "Please fill all the fields.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Empty Fields", JOptionPane.ERROR_MESSAGE);
                    // Dispose the dialog window
                    frame.dispose();
                } else {
                    try {
                        int employeeId = Integer.parseInt(employeeID);

                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String email = emailField.getText();
                        String phone = phoneField.getText();
                        String address = addressField.getText();
                        String dob = dobField.getText();
                        String employmentStatus = employmentStatusField.getText();
                        String username = usernameField.getText();
                        String password = new String(passwordField.getPassword());

                        if (isNullOrEmpty(firstName) || isNullOrEmpty(lastName) || isNullOrEmpty(email) || isNullOrEmpty(phone)
                                || isNullOrEmpty(address) || isNullOrEmpty(dob) || isNullOrEmpty(employmentStatus)
                                || isNullOrEmpty(username) || isNullOrEmpty(password)) {
                            String errorMessage = "Please fill all the fields.";
                            JOptionPane.showMessageDialog(null, errorMessage, "Empty Fields", JOptionPane.ERROR_MESSAGE);
                            // Dispose the dialog window
                            frame.dispose();
                        }

                        // Adding row in database
                        employee.addEmployee(employeeId, firstName, lastName, email, phone, address, dob, employmentStatus,
                                username, password);

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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);

        panel.add(generalInfoPanel, BorderLayout.NORTH);
        panel.add(credentialsPanel, BorderLayout.CENTER);
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