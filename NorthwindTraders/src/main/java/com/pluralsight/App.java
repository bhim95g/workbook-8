package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root"; // Change this if needed
        String password = "yearup"; // Change this if needed

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ProductName FROM Products")) {

            while (rs.next()) {
                System.out.println(rs.getString("ProductName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

