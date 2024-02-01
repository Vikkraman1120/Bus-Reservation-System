import java.util.Scanner;
import java.sql.*;

class Cancled extends ConnectionDb {
    public static void cancel() {
        Scanner sc = new Scanner(System.in);
        Connection con = null;
        PreparedStatement cancelBookingStatement = null;
        PreparedStatement updateBusStatement = null;
        PreparedStatement updateBookingTable = null;
        ResultSet resultSet = null;

        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DB_URL, user, password);
            con.setAutoCommit(false);  // Disable auto-commit to perform a transaction

            // Get user input for cancellation details
            System.out.println("Enter Booking ID to cancel: ");
            int bookingIdToCancel = sc.nextInt();

            // Check if the booking exists
            String checkBookingSql = "SELECT * FROM BOOKING WHERE BOOKING_ID = ?";
            cancelBookingStatement = con.prepareStatement(checkBookingSql);
            cancelBookingStatement.setInt(1, bookingIdToCancel);
            resultSet = cancelBookingStatement.executeQuery();

            if (resultSet.next()) {
                // Booking exists, proceed with cancellation

                int canceledSeats = resultSet.getInt("NUMBER_SEAT");

                // Update the BUS table with the new seat count
                int busId = resultSet.getInt("B_ID");
                String updateBusSql = "UPDATE BUS SET SEAT_COUNT = SEAT_COUNT + ? WHERE B_ID = ?";
                updateBusStatement = con.prepareStatement(updateBusSql);
                updateBusStatement.setInt(1, canceledSeats);
                updateBusStatement.setInt(2, busId);
                updateBusStatement.executeUpdate();

                // Delete the booking record from the BOOKING table
                String deleteBookingSql = "DELETE FROM BOOKING WHERE BOOKING_ID = ?";
                updateBookingTable = con.prepareStatement(deleteBookingSql);
                updateBookingTable.setInt(1, bookingIdToCancel);
                updateBookingTable.executeUpdate();

                con.commit();  // Commit the transaction
                System.out.println("Booking successfully canceled!");
            } else {
                System.out.println("Booking with ID " + bookingIdToCancel + " not found.");
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
