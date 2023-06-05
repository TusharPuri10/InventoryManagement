package database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.math.BigDecimal;

import static UI.P5_Employees.table;

public class retailer {
    private static String query;
    public static Object[][] getAllRetailers(String userType, Connection connection) {
        // SQL query to retrieve data from the "retailers" table
        String query;
        if (userType.equals("Administrator")) {
            query = "SELECT * FROM retailers";
        } else {
            query = "SELECT retailerID, retailerName, contactPerson, contactEmail, contactPhone, contactAddress, shippingAddress, paymentTerms, preferredShippingMethod, taxID, currentBalance FROM retailers";
        }

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

    public static void addRetailer(int retailerID, String retailerName, String contactPerson, String contactEmail, String contactPhone,
                                   String contactAddress, String shippingAddress, String paymentTerms, String preferredShippingMethod,
                                   String taxID, BigDecimal currentBalance) {
        // Create an SQL INSERT statement for the retailers table
        String insertRetailerQuery = "INSERT INTO retailers (retailerID, retailerName, contactPerson, contactEmail, contactPhone, " +
                "contactAddress, shippingAddress, paymentTerms, preferredShippingMethod, taxID, currentBalance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Check if the retailerID already exists
        if (isRetailerIDExists(retailerID)) {
            String errorMessage = "Retailer ID already exists. Please choose a different Retailer ID.";
            JOptionPane.showMessageDialog(null, errorMessage, "Duplicate Retailer ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConn()) {
            // Prepare the INSERT statement
            PreparedStatement insertRetailerStatement = connection.prepareStatement(insertRetailerQuery);

            // Add values to the INSERT statement
            insertRetailerStatement.setInt(1, retailerID);
            insertRetailerStatement.setString(2, retailerName);
            insertRetailerStatement.setString(3, contactPerson);
            insertRetailerStatement.setString(4, contactEmail);
            insertRetailerStatement.setString(5, contactPhone);
            insertRetailerStatement.setString(6, contactAddress);
            insertRetailerStatement.setString(7, shippingAddress);
            insertRetailerStatement.setString(8, paymentTerms);
            insertRetailerStatement.setString(9, preferredShippingMethod);
            insertRetailerStatement.setString(10, taxID);
            insertRetailerStatement.setBigDecimal(11, currentBalance);

            // Execute the INSERT statement
            int rowsAffected = insertRetailerStatement.executeUpdate();

            // Close the insert statement
            insertRetailerStatement.close();

            if (rowsAffected > 0) {
                // Retrieve the updated data from the database
                Object[][] newData = getAllRetailers("Administrator",connection);

                // Update the table model with the new data
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setDataVector(newData, getTableHeaders());
                model.fireTableDataChanged();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] getTableHeaders() {
        return new String[]{"Retailer ID", "Retailer Name", "Contact Person", "Contact Email", "Contact Phone", "Contact Address", "Shipping Address", "Payment Terms", "Preferred Shipping Method", "Tax ID", "Current Balance"};
    }


    private static boolean isRetailerIDExists(int retailerID) {
        String selectQuery = "SELECT COUNT(*) FROM retailers WHERE retailerID = ?";
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, retailerID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
