import java.util.Scanner;
import java.sql.*;

class Login extends ConnectionDb{
    public static boolean loginUser() throws Exception{
        Class.forName(JDBC_DRIVER);
        Connection con = null;
        Statement logiStatement = null;
        ResultSet resultSet = null;

        con = DriverManager.getConnection(DB_URL,user, password);
        logiStatement = con.createStatement();

        Scanner sc = new Scanner(System.in);
        System.out.print("Mail Id  : ");
        String userMailId = sc.next();
        System.out.print("Password : ");
        String userPassword = sc.next();
       
        String sql;
        sql = "SELECT MAIL_ID,PASSWORD FROM PASSANGER ";

        resultSet = logiStatement.executeQuery(sql);
        while(resultSet.next()){
            String dbMailID = resultSet.getString("MAIL_ID");
            String dbPassword = resultSet.getString("PASSWORD");

            if(userMailId.equalsIgnoreCase(dbMailID) && userPassword.equals(dbPassword)){
                System.out.println("Login Successful!");
                return true;
            }
        }
        con.close();
        logiStatement.close();
        resultSet.close();
        
        return false;
        
    }
}
