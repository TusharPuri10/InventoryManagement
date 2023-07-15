package database;

import UI.P2_Retailers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.math.BigDecimal;

public class retailer {
    private static String query;
    public static Object[][] getAllRetailers(Connection connection) {
        // SQL query to retrieve data from the "retailers" table
        String query;
        query = "SELECT * FROM retailers";

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

    public static int addRetailer(int retailerID, String retailerName, String contactPerson, String contactEmail, String contactPhone,
                                   String contactAddress, String shippingAddress, String paymentTerms, String preferredShippingMethod,
                                   String taxID, BigDecimal currentBalance, JTable table, DefaultTableModel model) {
        // Create an SQL INSERT statement for the retailers table
        String insertRetailerQuery = "INSERT INTO retailers (retailerID, retailerName, contactPerson, contactEmail, contactPhone, " +
                "contactAddress, shippingAddress, paymentTerms, preferredShippingMethod, taxID, currentBalance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Check if the retailerID already exists
        if (isRetailerIDExists(retailerID)) {
            String errorMessage = "Retailer ID already exists. Please choose a different Retailer ID.";
            JOptionPane.showMessageDialog(null, errorMessage, "Duplicate Retailer ID", JOptionPane.ERROR_MESSAGE);
            return 1;
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
                Object[][] newData  = getAllRetailers(connection);

                // Update the table model with the new data
                model.setDataVector(newData, getTableHeaders());
                model.fireTableDataChanged();

                int[] columnWidths = {100, 150, 150, 200, 120, 180, 180, 120, 150, 120, 120};
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

    public static void deleteSelectedRow(int selectedRow, JTable table, DefaultTableModel model) {
        // Get the key of the selected row (assuming it's stored in a column named "id")
        Object rowKey = table.getValueAt(selectedRow, table.getColumnModel().getColumnIndex("Retailer ID"));

        // Perform the delete operation on the selected row
        // ...

        // Make a query to the database to delete the row
        String deleteQuery = "DELETE FROM retailers WHERE  retailerID= ?";

        try (Connection connection = DatabaseConnection.getConn()) {

            // Prepare the DELETE statement
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            // Set the key value in the prepared statement
            statement.setObject(1, rowKey);

            // Execute the delete query
            int rowsAffected = statement.executeUpdate();

            statement.close();

            if (rowsAffected>0) {
                // Retrieve the updated data from the database
                Object[][] newData = getAllRetailers(connection);;

                // Update the table model with the new data
                model.setDataVector(newData, getTableHeaders());
                model.fireTableDataChanged();

                int[] columnWidths = {100, 150, 150, 200, 120, 180, 180, 120, 150, 120, 120};
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
