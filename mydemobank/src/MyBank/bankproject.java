package MyBank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class bankproject {
	
	String url = "jdbc:mysql://localhost:3307/my_bank";
    String user = "root";   
    String password = "root"; 
    Scanner sc = new Scanner(System.in);

    Connection conn = null;
    PreparedStatement preparedStatement = null;

	void addCustomer() {
		System.out.println("Add customer");	

	        

		
		  try {
	
	            Class.forName("com.mysql.cj.jdbc.Driver");

	       
	            conn = DriverManager.getConnection(url, user, password);

	        
	            String sql = "INSERT INTO customer (accountNo,name ,balance) VALUES (?, ?, ?)";
	            preparedStatement = conn.prepareStatement(sql);

	       
	            System.out.print("Enter Account Number ");
	            int value1 = sc.nextInt();

	            System.out.print("Enter Name of Customer): ");
	            String value2 = sc.next();
	            sc.nextLine(); 

	            System.out.print("Enter balance of customer ");
	            Integer value3 = sc.nextInt();

	       
	            preparedStatement.setInt(1, value1);
	            preparedStatement.setString(2, value2);
	            preparedStatement.setInt(3, value3);

	     
	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Record inserted successfully!");
	            } else {
	                System.out.println("Failed to insert record.");
	            }

	        } catch (ClassNotFoundException e) {
	            System.err.println("JDBC Driver not found: " + e.getMessage());
	        } catch (SQLException e) {
	            System.err.println("Database error: " + e.getMessage());
	        } finally {

	            try {
	                if (preparedStatement != null) preparedStatement.close();
	                if (conn != null) conn.close();
	          
	            } catch (SQLException e) {
	                System.err.println("Error closing resources: " + e.getMessage());
	            }
	        }

	}


	
	public void creditMoney() {
	    System.out.println("Credit money");

	    Connection conn = null;

	    try {
	        // 1. Load Driver
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        // 2. Connect to DB
	        conn = DriverManager.getConnection(url, user, password);

	        // 3. Input
	        System.out.print("Enter account number: ");
	        int accNo = sc.nextInt();

	        System.out.print("Enter amount to deposit: ");
	        double amount = sc.nextDouble();

	        // 4. Check if account exists
	        String checkQuery = "SELECT balance FROM customer WHERE accountNo = ?";
	        PreparedStatement psCheck = conn.prepareStatement(checkQuery);
	        psCheck.setInt(1, accNo);

	        ResultSet rs = psCheck.executeQuery();

	        if (!rs.next()) {     // <-- If no row found
	            System.out.println("Account not found!");
	            rs.close();
	            psCheck.close();
	            return;           // <-- Exit function, no further update
	        }

	        // 5. If account exists, get balance
	        double currentBalance = rs.getDouble("balance");
	        double newBalance = currentBalance + amount;

	        // 6. Update balance
	        String updateQuery = "UPDATE customer SET balance = ? WHERE accountNo = ?";
	        PreparedStatement psUpdate = conn.prepareStatement(updateQuery);
	        psUpdate.setDouble(1, newBalance);
	        psUpdate.setInt(2, accNo);

	        int rows = psUpdate.executeUpdate();

	        if (rows > 0) {
	            System.out.println("Amount credited successfully!");
	            System.out.println("Updated Balance: " + newBalance);
	        }

	        rs.close();
	        psCheck.close();
	        psUpdate.close();

	    } catch (Exception e) {
	        System.out.println("Error during credit operation!");
	        e.printStackTrace();
	    } finally {
	        try { if (conn != null) conn.close(); } catch (Exception e) {}
	    }
	}

	public void debitMoney() {
		System.out.println("Debit mponey");	
		 try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(url, user, password);

	            System.out.print("Enter account number: ");
	            int accNo = sc.nextInt();
	            System.out.print("Enter amount to withdraw: ");
	            double amount = sc.nextDouble();

	        
	            String checkQuery = "SELECT balance FROM customer WHERE accountNo = ?";
	            PreparedStatement psCheck = conn.prepareStatement(checkQuery);
	            psCheck.setInt(1, accNo);
	            ResultSet rs = psCheck.executeQuery();

	            if (rs.next()) {
	                double currentBalance = rs.getDouble("balance");

	                if (currentBalance >= amount) {
	                    double newBalance = currentBalance - amount;

	        
	                    String updateQuery = "UPDATE customer SET balance = ? WHERE accountNo = ?";
	                    PreparedStatement psUpdate = conn.prepareStatement(updateQuery);
	                    psUpdate.setDouble(1, newBalance);
	                    psUpdate.setInt(2, accNo);
	                    psUpdate.executeUpdate();

	                    System.out.println("Amount debited successfully!");
	                    System.out.println("Updated Balance: " + newBalance);

	                    psUpdate.close();
	                } else {
	                    System.out.println("Insufficient balance! Available: " + currentBalance);
	                }
	            } else {
	                System.out.println("Account not found!");
	            }

	            rs.close();
	            psCheck.close();
	            conn.close();

	        } catch (Exception e) {
	            System.out.println("Error during debit operation!");
	            e.printStackTrace();
		
	}
		
		
	}

	public void checkBalance() {
		System.out.println("Check Balance");
		

        try {
         
            Class.forName("com.mysql.cj.jdbc.Driver");

         
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL successfully!");

     
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
            System.out.println("Id AccountNo  Name  Balance");

            while (rs.next()) {
            	
                System.out.println(rs.getInt("id") + " - "+rs.getInt("accountNo") +" - "+ rs.getString("name")+" - "+rs.getDouble("balance"));
            }

            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
	
		
	}
	
	public void deleteCustomer() {
	    System.out.println("Delete Customer");

	    Connection conn = null;

	    try {
	        // Connect to DB
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection(url, user, password);

	        // Input account number
	        System.out.print("Enter account number to delete: ");
	        int accNo = sc.nextInt();

	        // 1. Check if account exists
	        String checkQuery = "SELECT * FROM customer WHERE accountNo = ?";
	        PreparedStatement psCheck = conn.prepareStatement(checkQuery);
	        psCheck.setInt(1, accNo);
	        ResultSet rs = psCheck.executeQuery();

	        if (!rs.next()) {
	            System.out.println("Account does not exist!");
	            rs.close();
	            psCheck.close();
	            return;  // Stop here
	        }

	        rs.close();
	        psCheck.close();

	        // 2. Delete the customer
	        String deleteQuery = "DELETE FROM customer WHERE accountNo = ?";
	        PreparedStatement psDelete = conn.prepareStatement(deleteQuery);
	        psDelete.setInt(1, accNo);

	        int rows = psDelete.executeUpdate();

	        if (rows > 0) {
	            System.out.println("Customer deleted successfully!");
	        } else {
	            System.out.println("Failed to delete customer!");
	        }

	        psDelete.close();

	    } catch (Exception e) {
	        System.out.println("Error while deleting customer!");
	        e.printStackTrace();
	    } finally {
	        try { if (conn != null) conn.close(); } catch (Exception e) {}
	    }
	}


}
