package UI;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JFrame {
    public String currentUser;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel label1;
    private JLabel label2;
    private JList<String> list;
    private JButton button1;
    private JPanel panel;

    public LoginPage() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400)); // Increase the window size

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        label1 = new JLabel("Wholesale Inventory Management System");
        label1.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(label1, gbc);

// Username label and text field
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Increase font size
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.ipady = 8; // Increase height
        panel.add(usernameLabel, gbc);

        JTextField usernameTextField = new JTextField(20);
        usernameTextField.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(usernameTextField, gbc);

// Password label and text field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Increase font size
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.ipady = 8; // Increase height
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(passwordField, gbc);



        // UserType list
        label2 = new JLabel("User Type:");
        label2.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(label2, gbc);

        String[] user = {"Administrator", "Employee"};
        list = new JList<>(user);
        list.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(list, gbc);

        button1 = new JButton("Login");
        button1.setPreferredSize(new Dimension(100, 30));
        button1.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(button1, gbc);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameTextField.getText();
                String password = new String(passwordField.getPassword());
                String userType = (String) list.getSelectedValue();
                checkLogin(username, password, userType);
            }
        });

        panel.setBackground(Color.WHITE); // Set panel background color
        getContentPane().setBackground(Color.WHITE); // Set frame background color
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    //    this function will check weather the user exist in the users table or not
    public void checkLogin(String username, String password, String userType) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND usertype = ?";

        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, userType);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String queryUserID = "SELECT userid FROM users WHERE username = ?";
                    PreparedStatement statementUserID = connection.prepareStatement(queryUserID);
                    statementUserID.setString(1, username);
                    ResultSet resultSetUserID = statementUserID.executeQuery();
                    int userId = -1;
                    if (resultSetUserID.next()) {
                        userId = resultSetUserID.getInt("userid");
                    } else {
                        // Close the ResultSet and the PreparedStatement before throwing the exception
                        resultSetUserID.close();
                        statementUserID.close();

                        throw new RuntimeException("User ID not found for username: " + username);
                    }
                    // Close the ResultSet and the PreparedStatement after use
                    resultSetUserID.close();
                    statementUserID.close();

                    P0_UI_Main mainPage = new P0_UI_Main(userType,username,userId,connection);
                    mainPage.setVisible(true);
                    dispose(); // Close the login page
                }
                else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid username or password.");
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
