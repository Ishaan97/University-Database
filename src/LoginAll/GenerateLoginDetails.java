package LoginAll;

import DataBaseConnection.DbConnect;
import java.sql.*;
import java.security.SecureRandom;
import javax.swing.JOptionPane;

public class GenerateLoginDetails {
    
    public static String passGen(String str)
    {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        int len = 5;
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) 
             sb.append( AB.charAt( rnd.nextInt(AB.length())));
        String check = sb.toString();
        
        if(check.equals(str))
        {
            passGen(str); 
        }
       return sb.toString();
    }
    public static String idGen(String str)
    {
        String AB = "0123456789";
        SecureRandom rnd = new SecureRandom();
        int len = 7;
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) 
             sb.append( AB.charAt( rnd.nextInt(AB.length())));
        String check = sb.toString();
        
        if(check.equals(str))
        {
            passGen(str); 
        }
       return sb.toString();
    }
    public static boolean checkId(String user)
    {
        try
        {
            DbConnect.Connect();
            String query = "select userid from AdminTable where userid = '"+user+"';";
            ResultSet rs = DbConnect.stmt.executeQuery(query);
            while(true)
            {
                String str = idGen(user);
                if(str.equals(user))
                    return true;
                else 
                    return false;
                
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        
    }
    
         
    
    
}
