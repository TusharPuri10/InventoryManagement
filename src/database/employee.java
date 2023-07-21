package database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class employee {
    public static Object[][] getAllEmployees(Connection connection) {
        // SQL query to retrieve data from the "employee" table
        String query = "SELECT * FROM employees";

        try {
            PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Calculate the number of rows returned
            resultSet.last();
            int numRows = resultSet.getRow();
            resultSet.beforeFirst();

            // Get number of columns returned
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numCols = metaData.getColumnCount();

            // Create a 2D object array to hold data from the result set
            Object[][] data = new Object[numRows][numCols];

            // Populate the 2D object array with data from the result set
            int i = 0;
            while (resultSet.next()) {
                for (int j = 0; j < numCols; j++) {
                    data[i][j] = resultSet.getObject(j + 1);
                }
                i++;
            }

            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


// Adding new employee
    public static int addEmployee(int employeeId, String firstName, String lastName, String email, String phone,
                                   String address, String dob, String employmentStatus, String username, String password,JTable table, DefaultTableModel model) {
        // Create an SQL INSERT statement for the employees table
        String insertEmployeeQuery = "INSERT INTO employees (employeeID, firstName, lastName, Email, Phone, Address, DateofBirth, DateofJoining, Status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //is userid exists
        if(isUserIdExists(employeeId)){
            String errorMessage = "User ID already exists. Please choose a different User ID.";
            JOptionPane.showMessageDialog(null, errorMessage, "Duplicate User ID", JOptionPane.ERROR_MESSAGE);
            return 1;
        }

        //is username exists
        if(isUsernameExists(username)){
            String errorMessage = "User ID already exists. Please choose a different User ID.";
            JOptionPane.showMessageDialog(null, errorMessage, "Duplicate User ID", JOptionPane.ERROR_MESSAGE);
            return 1;
        }


        // Create an SQL INSERT statement for the users table
        String insertUserQuery = "INSERT INTO users (userid, username, password,usertype) " +
                "VALUES (?, ?, ?,?)";
        try (Connection connection = DatabaseConnection.getConn()) {
        // Prepare the INSERT statements
        PreparedStatement insertUserStatement = connection.prepareStatement(insertUserQuery);

            // Add values to the INSERT statement for users

            insertUserStatement.setInt(1, employeeId);
            insertUserStatement.setString(2, username);
            insertUserStatement.setString(3, password);
            insertUserStatement.setString(4, "Employee");

            // Execute the INSERT statement for users
            int rowsAffectedUser = insertUserStatement.executeUpdate();

            // Close the insert statement for users
            insertUserStatement.close();

            PreparedStatement insertEmployeeStatement = connection.prepareStatement(insertEmployeeQuery);

            // Add values to the INSERT statement for employees
            insertEmployeeStatement.setInt(1, employeeId);
            insertEmployeeStatement.setString(2, firstName);
            insertEmployeeStatement.setString(3, lastName);
            insertEmployeeStatement.setString(4, email);
            insertEmployeeStatement.setString(5, phone);
            insertEmployeeStatement.setString(6, address);

            // Convert the date of birth string to a LocalDate object
            LocalDate dobDate;
            try {
                dobDate = LocalDate.parse(dob);
            } catch (DateTimeParseException e) {
                String errorMessage = "Invalid date format for Date of Birth. Please use yyyy-MM-dd format.";
                JOptionPane.showMessageDialog(null, errorMessage, "Invalid Date", JOptionPane.ERROR_MESSAGE);
                return 1;
            }
            insertEmployeeStatement.setDate(7, Date.valueOf(dobDate));

            // Get the current date and time
            LocalDate now = LocalDate.now();
            insertEmployeeStatement.setDate(8, Date.valueOf(now));
            insertEmployeeStatement.setString(9, employmentStatus);

            // Execute the INSERT statement for employees
            int rowsAffectedEmployee = insertEmployeeStatement.executeUpdate();

            // Close the insert statements
            insertEmployeeStatement.close();
            insertUserStatement.close();

            if (rowsAffectedEmployee > 0 && rowsAffectedUser > 0) {
                // Retrieve the updated data from the database
                Object[][] newData = getAllEmployees(connection);

                // Update the table model with the new data
                model.setDataVector(newData, getTableHeaders());
                model.fireTableDataChanged();

                int[] columnWidths = {100, 150, 150, 180, 180, 120, 150, 120, 120};
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    private static String[] getTableHeaders() {
        return new String[]{"Employee ID", "First Name", "Last Name", "Contact Email", "Contact Phone", "Address",
                "Date of Birth", "Date of Joining", "Employment Status"};
    }

    private static boolean isUserIdExists(int employeeId) {
        String selectQuery = "SELECT COUNT(*) FROM users WHERE userid = ?";
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isUsernameExists(String username) {
        String selectQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteSelectedRow(int selectedRow, JTable table, DefaultTableModel model) {
        // Get the key of the selected row (assuming it's stored in a column named "id")
        Object rowKey = table.getValueAt(selectedRow, table.getColumnModel().getColumnIndex("Employee ID"));

        // Perform the delete operation on the selected row
        // ...

        // Make a query to the database to delete the row
        String deleteQueryEmp = "DELETE FROM employees WHERE employeeID = ?";
        String deleteQueryUser = "DELETE FROM users WHERE userid = ?";

        try (Connection connection = DatabaseConnection.getConn()) {

            // Prepare the DELETE statement
            PreparedStatement statementEmp = connection.prepareStatement(deleteQueryEmp);
            PreparedStatement statementUser = connection.prepareStatement(deleteQueryUser);

            // Set the key value in the prepared statement
            statementEmp.setObject(1, rowKey);
            statementUser.setObject(1, rowKey);

            // Execute the delete query
            int rowsAffectedEmp = statementEmp.executeUpdate();
            int rowsAffectedUser = statementUser.executeUpdate();

            statementEmp.close();
            statementUser.close();

            if (rowsAffectedEmp> 0 && rowsAffectedUser>0) {
                // Retrieve the updated data from the database
                Object[][] newData = getAllEmployees(connection);

                // Update the table model with the new data
                model.setDataVector(newData, getTableHeaders());
                model.fireTableDataChanged();

                int[] columnWidths = {100, 150, 150, 180, 180, 120, 150, 120, 120};
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
                }

            }

            // Handle any additional logic after deleting the row


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
