package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConn() {
        String url = "jdbc:sqlserver://teamlsqlserver.database.windows.net:1433;database=inventory;user=azureuser@teamlsqlserver;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, "azureuser", "graphicera@123"); //TODO - dont store database user and password in plain text
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
