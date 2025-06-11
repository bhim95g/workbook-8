package com.pluralsight;


import java.sql.*;
import java.util.Scanner;


public class App {
    public static void main (String[] args) {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUser(username);
        dataSource.setPassword(password);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter actor's last name: ");
            String lastName = scanner.nextLine();

            try (Connection conn = dataSource.getConnection()) {
                String actorQuery = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";
                try (PreparedStatement stmt = conn.prepareStatement(actorQuery)) {
                    stmt.setString(1, lastName);
                    try (ResultSet results = stmt.executeQuery()) {
                        if (results.next()) {
                            System.out.println("Your matches are:");
                            do {
                                int id = results.getInt("actor_id");
                                String first = results.getString("first_name");
                                String last = results.getString("last_name");
                                System.out.printf("ID: %d - %s %s%n", id, first, last);
                            } while (results.next());
                        } else {
                            System.out.println("No matches!");
                            return;
                        }
                    }
                }

                System.out.print("Enter actor's first name: ");
                String firstName = scanner.nextLine();



                try (PreparedStatement stmt = conn.prepareStatement()) {
                    stmt.setString(1, firstName);
                    stmt.setString(2, lastName);
                    try (ResultSet results = stmt.executeQuery()) {
                        if (results.next()) {
                            System.out.println("Movies by that actor:");
                            do {
                                System.out.println("- " + results.getString("title"));
                            } while (results.next());
                        } else {
                            System.out.println("This actor has not appeared in any films.");
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        dataSource.close();
    }
}

