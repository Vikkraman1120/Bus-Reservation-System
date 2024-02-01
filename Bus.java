import java.sql.*;
public class Bus extends ConnectionDb {
    public static void display_bus_details() throws Exception{
        Connection con = null;
        Statement st = null;

        Class.forName(JDBC_DRIVER);
        con = DriverManager.getConnection(DB_URL,user,password);
        st = con.createStatement();

        String sql;

        sql = " SELECT B_ID,B_NAME,B_TYPE,SEAT_TYPE,SEAT_COUNT FROM BUS";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            System.out.print("BUS_ID : "+rs.getInt("B_ID"));
            System.out.print(", BUS_NAME : "+rs.getString("B_NAME"));
            System.out.print(", BYS_TYPE : "+rs.getString("B_TYPE"));
            System.out.print(", SEAT_TYPE : "+rs.getString("SEAT_TYPE"));
            System.out.print(", SEAT_COUNT : "+rs.getInt("SEAT_COUNT"));
            System.out.println();
        }
        rs.close();
        st.close();
        con.close();
    }
}
