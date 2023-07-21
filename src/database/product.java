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

    private static String[] getTableHeaders_emp() {
        return new String[]{"Product ID", "Product Name", "Category", "Cost Price", "Selling Price", "Quantity",
                "Minimum Stock Level", "Maximum Stock Level", "Reorder Point","Lead Time"};
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

    public static void sellProduct(int quantity, int selectedProduct, int selectedRetailer, String username, int userid, JTable table, DefaultTableModel model) throws SQLException {
        // Get the key of the selected row (assuming it's stored in a column named "Product ID")
        int productId = (int) table.getValueAt(selectedProduct, table.getColumnModel().getColumnIndex("Product ID"));

        // Check if the quantity is valid and not negative
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(null, "Invalid quantity entered.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
            return;
        }


        try (Connection connection = DatabaseConnection.getConn()){
            // Check if the product exists in the inventory
            String query = "SELECT * FROM inventory WHERE productID=?";
            PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            // Check if the product exists in the inventory
            if (resultSet.next()) {
                // Get the available quantity in the inventory
                int availableQuantity = resultSet.getInt("quantity");

                // Check if the available quantity is sufficient for selling
                if (quantity > availableQuantity) {
                    JOptionPane.showMessageDialog(null, "Not enough quantity available for sale.", "Quantity Not Enough", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Insert the sold product into the products_sold table
                    String insertQuery = "INSERT INTO products_sold (productID, productName, category, costPrice, sellingPrice, quantity, leadTime, retailerID, employeeID, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setInt(1, productId);
                        insertStatement.setString(2, resultSet.getString("productName"));
                        insertStatement.setString(3, resultSet.getString("category"));
                        insertStatement.setBigDecimal(4, resultSet.getBigDecimal("costPrice"));
                        insertStatement.setBigDecimal(5, resultSet.getBigDecimal("sellingPrice"));
                        insertStatement.setInt(6, quantity);
                        insertStatement.setInt(7, resultSet.getInt("leadTime"));
                        insertStatement.setInt(8, selectedRetailer);
                        insertStatement.setInt(9, userid);
                        insertStatement.setTimestamp(10, new Timestamp(System.currentTimeMillis()));

                        // Execute the INSERT operation
                        int rowsInserted = insertStatement.executeUpdate();

                        if (rowsInserted > 0) {
                            System.out.println("Product sold and inserted successfully.");
                            // Update the inventory quantity
                            int newQuantity = availableQuantity - quantity;
                            String updateQuery = "UPDATE inventory SET quantity=? WHERE productID=?";
                            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                updateStatement.setInt(1, newQuantity);
                                updateStatement.setInt(2, productId);
                                int rowsUpdated = updateStatement.executeUpdate();
                                if (rowsUpdated > 0) {
                                    Object[][] newData = getAllproducts("Employee",connection);

                                    // Update the table model with the new data
                                    model.setDataVector(newData, getTableHeaders_emp());
                                    model.fireTableDataChanged();

                                    int[] columnWidths = {100, 150, 150, 180, 180, 120, 150, 120};
                                    for (int i = 0; i < table.getColumnCount(); i++) {
                                        table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
                                    }
                                } else {
                                    System.out.println("Failed to update inventory quantity.");
                                    // Rollback the transaction if the inventory update fails
                                    connection.rollback();
                                }
                            }
                        } else {
                            System.out.println("Failed to insert product sold.");
                            // Rollback the transaction if the product insertion fails
                            connection.rollback();
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Product not found in the inventory.", "Product Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
