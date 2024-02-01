import java.util.Scanner;
import java.sql.*;

public class Reserved extends ConnectionDb{
    
    public static void reserved() {
        Connection con = null;
        PreparedStatement bookingStatement = null;
        PreparedStatement updateBusStatement = null;
        PreparedStatement displayBookingId = null;
        ResultSet resultSet = null;
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DB_URL, user, password);
            con.setAutoCommit(false);  // Disable auto-commit to perform a transaction

            // Get user input for booking details
            System.out.print("Passenger ID: ");
            int passengerId = sc.nextInt();

            System.out.print("Bus ID: ");
            int busId = sc.nextInt();

            // For simplicity, I'm using the current timestamp.
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            System.out.print("Sources: ");
            String sources = sc.next();

            System.out.print("Destination: ");
            String destination = sc.next();


            // For simplicity, I'm using the current date.
            Date bookedDate = new Date(System.currentTimeMillis());

            System.out.print("Number of Seats to Book: ");
            int numberOfSeats = sc.nextInt();

            // Perform SQL query to check the current seat count
            String checkSeatCountSql = "SELECT SEAT_COUNT FROM BUS WHERE B_ID = ?";
            updateBusStatement = con.prepareStatement(checkSeatCountSql);
            updateBusStatement.setInt(1, busId);
            resultSet = updateBusStatement.executeQuery();

            int currentTotalSeats = 0;

            if (resultSet.next()) {
                currentTotalSeats = resultSet.getInt("SEAT_COUNT");
            }

            // Check if there are enough available seats
            if (currentTotalSeats >= numberOfSeats) {
                // Prepare and execute the SQL statement for the booking
                String bookingSql = "INSERT INTO BOOKING (P_ID, B_ID, DATE_TIME, SOURCES, DESIGNATION, BOOKED_DATE, NUMBER_SEAT) VALUES (?, ?, ?, ?, ?, ?, ?)";
                bookingStatement = con.prepareStatement(bookingSql);
                bookingStatement.setInt(1, passengerId);
                bookingStatement.setInt(2, busId);
                bookingStatement.setTimestamp(3, timestamp);
                bookingStatement.setString(4, sources);
                bookingStatement.setString(5, destination);
                bookingStatement.setDate(6, bookedDate);
                bookingStatement.setInt(7, numberOfSeats);
                bookingStatement.executeUpdate();

                // Update the BUS table with the new seat count
                String updateBusSql = "UPDATE BUS SET SEAT_COUNT = ? WHERE B_ID = ?";
                updateBusStatement = con.prepareStatement(updateBusSql);
                updateBusStatement.setInt(1, currentTotalSeats - numberOfSeats);
                updateBusStatement.setInt(2, busId);
                updateBusStatement.executeUpdate();

                System.out.println("Booking successfully added!");

                String bookingIdQuerry = "SELECT BOOKING_ID FROM BOOKING WHERE P_ID = ? AND B_ID = ?";
                displayBookingId = con.prepareStatement(bookingIdQuerry);
                displayBookingId.setInt(1,passengerId);
                displayBookingId.setInt(2,busId);
                resultSet = displayBookingId.executeQuery();
                //int bookingId = 0;

                while(resultSet.next()){
                    System.out.println("Reservation ID : "+ resultSet.getInt("BOOKING_ID"));
                }

               
                con.commit();  // Commit the transaction
            } else {
                System.out.println("Not enough available seats!");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();  // Handle the exceptions appropriately
            try {
                if (con != null) {
                    con.rollback();  // Rollback the transaction in case of an exception
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } 
    }
}
