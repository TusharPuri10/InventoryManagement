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
            query = new String("SELECT productID, productName, category, quantity, minimumStockLevel, maximumStockLevel, reorderPoint, leadTime FROM inventory");
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

    public static int addProduct(Connection connection,int productID, String productName, String category, BigDecimal costPrice,
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
            return 1;
        }

        try {
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

                int[] columnWidths = {100, 150, 150, 200, 120, 180, 180, 120, 150, 120, 120, 120};
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
    public static void deleteSelectedRow(Connection connection, int selectedRow, JTable table, DefaultTableModel model) {
        // Get the key of the selected row (assuming it's stored in a column named "id")
        Object rowKey = table.getValueAt(selectedRow, table.getColumnModel().getColumnIndex("Product ID"));

        // Perform the delete operation on the selected row
        // ...

        // Make a query to the database to delete the row
        String deleteQuery = "DELETE FROM  inventory WHERE productID = ?";

        try {

            // Prepare the DELETE statement
            PreparedStatement statement = connection.prepareStatement(deleteQuery);

            // Set the key value in the prepared statement
            statement.setObject(1, rowKey);

            // Execute the delete query
            int rowsAffected = statement.executeUpdate();

            statement.close();

            if (rowsAffected> 0) {
                // Retrieve the updated data from the database
                Object[][] newData = getAllproducts("Administrator",connection);

                // Update the table model with the new data
                model.setDataVector(newData, getTableHeaders());
                model.fireTableDataChanged();

                int[] columnWidths = {100, 150, 150, 200, 120, 180, 180, 120, 150, 120, 120, 120};
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
                }

            }

            // Handle any additional logic after deleting the row
            // TODO: logs for delete

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sellProduct(Connection connection, int quantity, int selectedProduct, int selectedRetailer, String username, int userid,JTable table, DefaultTableModel model) throws SQLException {
        // Get the key of the selected row (assuming it's stored in a column named "id")
        Object rowKey = table.getValueAt(selectedProduct, table.getColumnModel().getColumnIndex("Product ID"));
        query = new String("SELECT * FROM inventory WHERE productID=?");
        try{
            PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setObject(1, rowKey);
            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();
// Check if the result set is not empty
            if (resultSet.next()) {
                // Get the values from the result set for the product to be sold
                int productId = resultSet.getInt("productID");
                String productName = resultSet.getString("productName");
                String category = resultSet.getString("category");
                BigDecimal costPrice = resultSet.getBigDecimal("costPrice");
                BigDecimal sellingPrice = resultSet.getBigDecimal("sellingPrice");
                int leadTime = resultSet.getInt("leadTime");

                // Insert the sold product into the products_sold table
                String insertQuery = "INSERT INTO products_sold (productID, productName, category, costPrice, sellingPrice, quantity, leadTime, retailerID, employeeID, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setInt(1, productId);
                    insertStatement.setString(2, productName);
                    insertStatement.setString(3, category);
                    insertStatement.setBigDecimal(4, costPrice);
                    insertStatement.setBigDecimal(5, sellingPrice);
                    insertStatement.setInt(6, quantity);
                    insertStatement.setInt(7, leadTime);
                    insertStatement.setInt(8, selectedRetailer);
                    insertStatement.setInt(9, userid);
                    insertStatement.setTimestamp(10, new Timestamp(System.currentTimeMillis()));

                    // Execute the INSERT operation
                    int rowsInserted = insertStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Product sold and inserted successfully.");
                        // Decrease the quantity of the sold product in the inventory
                        int currentQuantity = resultSet.getInt("quantity");
                        int newQuantity = currentQuantity - quantity;
                        String updateQuery = "UPDATE inventory SET quantity=? WHERE productID=?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, newQuantity);
                            updateStatement.setInt(2, productId);
                            int rowsUpdated = updateStatement.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("Inventory quantity updated.");
                                // Update the table model to reflect the updated inventory quantity
                                model.setValueAt(newQuantity, selectedProduct, table.getColumnModel().getColumnIndex("quantity"));
                            }
                        }
                    } else {
                        System.out.println("Failed to insert product sold.");
                    }
                }
            } else {
                System.out.println("Product not found in the inventory.");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }

}
