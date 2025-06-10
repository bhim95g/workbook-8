import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Choose display format:");
                    System.out.println("1) Stacked Information");
                    System.out.println("2) Rows of Information");
                    System.out.print("Select an option: ");
                    int formatOption = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    displayProducts(formatOption);
                    break;
                case 2:
                    displayCustomers();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    public static void displayProducts(int formatOption) {
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = "yearup";

        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

            if (formatOption == 1) {
                // Stacked Information Format
                while (results.next()) {
                    System.out.println("Product Id: " + results.getInt("ProductID"));
                    System.out.println("Name: " + results.getString("ProductName"));
                    System.out.println("Price: " + results.getDouble("UnitPrice"));
                    System.out.println("Stock: " + results.getInt("UnitsInStock"));
                    System.out.println("------------------");
                }
            } else {
                // Rows of Information Format
                System.out.printf("%-10s %-30s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
                System.out.println("----------------------------------------------------------");
                while (results.next()) {
                    System.out.printf("%-10d %-30s %-10.2f %-10d%n",
                            results.getInt("ProductID"),
                            results.getString("ProductName"),
                            results.getDouble("UnitPrice"),
                            results.getInt("UnitsInStock"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching product data: " + e.getMessage());
        }
    }

    public static void displayCustomers() {
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root"; // Replace with actual username
        String password = "yearup"; // Replace with actual password

        String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

            System.out.printf("%-25s %-30s %-20s %-15s %-15s%n", "Contact Name", "Company Name", "City", "Country", "Phone");
            System.out.println("----------------------------------------------------------------------------------------------------");

            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");

                System.out.printf("%-25s %-30s %-20s %-15s %-15s%n", contactName, companyName, city, country, phone);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching customer data: " + e.getMessage());
        }
    }
}