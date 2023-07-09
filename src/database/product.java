package database;

import UI.P0_UI_Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.*;

public class product {
    private static String query;
    public static Object[][] getAllproducts(String userType, Connection connection) {
        // SQL query to retrieve data from the "employee" table
        if(userType=="Administrator")
            query = new String("SELECT * FROM inventory");
        else
            query = new String("SELECT productID, productName, category, quantity, minimumStockLevel, maximumStockLevel, reorderPoint, manufacturer, manufacturerCode, leadTime FROM inventory");
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

    public static void addProduct(int productID, String productName, String category, BigDecimal costPrice,
                                  BigDecimal sellingPrice, int quantity, int minimumStockLevel, int maximumStockLevel,
                                  int reorderPoint, String manufacturer, String manufacturerCode, int leadTime,
                                  JTable table, DefaultTableModel model) {
        // Create an SQL INSERT statement for the inventory table
        String insertProductQuery = "INSERT INTO inventory (productID, productName, category, costPrice, sellingPrice, quantity, " +
                "minimumStockLevel, maximumStockLevel, reorderPoint, manufacturer, manufacturerCode, leadTime) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Check if the productID already exists
        if (isProductIDExists(productID)) {
            String errorMessage = "Product ID already exists. Please choose a different Product ID.";
            JOptionPane.showMessageDialog(null, errorMessage, "Duplicate Product ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConn()) {
            // Prepare the INSERT statement
            PreparedStatement insertProductStatement = connection.prepareStatement(insertProductQuery);

            // Add values to the INSERT statement
            insertProductStatement.setInt(1, productID);
            insertProductStatement.setString(2, productName);
            insertProductStatement.setString(3, category);
            insertProductStatement.setBigDecimal(4, costPrice);
            insertProductStatement.setBigDecimal(5, sellingPrice);
            insertProductStatement.setInt(6, quantity);
            insertProductStatement.setInt(7, minimumStockLevel);
            insertProductStatement.setInt(8, maximumStockLevel);
            insertProductStatement.setInt(9, reorderPoint);
            insertProductStatement.setString(10, manufacturer);
            insertProductStatement.setString(11, manufacturerCode);
            insertProductStatement.setInt(12, leadTime);

            // Execute the INSERT statement
            int rowsAffected = insertProductStatement.executeUpdate();

            // Close the insert statement
            insertProductStatement.close();

            if (rowsAffected > 0) {
                // Retrieve the updated data from the database
                Object[][] newData = getAllproducts("Administrator",connection);

                // Update the table model with the new data
                model.setDataVector(newData, getTableHeaders());
                model.fireTableDataChanged();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static String[] getTableHeaders() {
        return new String[]{"Product ID", "Product Name", "Category", "Cost Price", "Selling Price", "Quantity",
                "Minimum Stock Level", "Maximum Stock Level", "Reorder Point", "Manufacturer", "Manufacturer Code", "Lead Time"};
    }

    private static boolean isProductIDExists(int productID) {
        String selectQuery = "SELECT COUNT(*) FROM inventory WHERE productID = ?";
        try (Connection connection = DatabaseConnection.getConn();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, productID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
