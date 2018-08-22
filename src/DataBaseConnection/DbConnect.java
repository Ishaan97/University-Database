package DataBaseConnection;

import java.sql.*;
import javax.swing.JOptionPane;

public class DbConnect {
    public static Connection con;
    public static Statement stmt;
    public static void Connect()
    {
        try
        {
           Class.forName("java.sql.DriverManager");
           String url = "jdbc:mysql://localhost:3306/university";
           String user = "root";
           String pass = "ishaan";
           con = (Connection)DriverManager.getConnection(url,user,pass);
           stmt = (Statement)con.createStatement();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    public static void Disconnect()
    {
        try
        {
           stmt.close();
           con.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    public static class stmt {

        public stmt() {
        }
    }
}
