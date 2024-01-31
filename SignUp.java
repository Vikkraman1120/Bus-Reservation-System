import java.util.Scanner;
import java.sql.*;
public class SignUp extends ConnectionDb {
    public static void signup() throws Exception{
        Scanner sc = new Scanner(System.in);
        Connection con =null;
        Statement st = null;

        Class.forName(JDBC_DRIVER);
        con = DriverManager.getConnection(DB_URL, user, password);
        st = con.createStatement();

        System.out.print("First Name : ");
        String FIRST_NAME = sc.nextLine();

        System.out.print("Last Name : ");
        String LAST_NAME = sc.nextLine();

        System.out.print("Age : ");
        int AGE = sc.nextInt();
        sc.nextLine();

        System.out.print("Gender : ");
        String GENDER = sc.nextLine();

        System.out.print("Phone number : ");
        String PHONE_NUMBER = sc.nextLine();

        System.out.print("Mail ID : ");
        String MAIL_ID = sc.nextLine();

        System.out.print("Password : ");
        String PASSWORD = sc.nextLine();

        String sql;
        sql = "INSERT INTO PASSANGER (FIRST_NAME, LAST_NAME, AGE, GENDER, PHONE_NUMBER, MAIL_ID, PASSWORD) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, FIRST_NAME);
        pst.setString(2, LAST_NAME);
        pst.setInt(3, AGE);
        pst.setString(4, GENDER);
        pst.setString(5, PHONE_NUMBER);
        pst.setString(6, MAIL_ID);
        pst.setString(7, PASSWORD);

        int row = pst.executeUpdate();
        System.out.println("---------------------------------------");
        System.out.println("Number of Query affected : "+row);
        st.close();
        con.close();
       // sc.close();
    }
}
