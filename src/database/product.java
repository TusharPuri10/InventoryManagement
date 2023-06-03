package database;

import UI.P0_UI_Main;

import javax.swing.*;
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


}
