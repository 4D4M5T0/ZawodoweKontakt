package com.example.aplikacjazawodowekon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:app.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initialize() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            String createUsers = """
                    CREATE TABLE IF NOT EXISTS Users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        login TEXT UNIQUE NOT NULL,
                        password TEXT NOT NULL
                    );
                    """;

            String createContacts = """
                    CREATE TABLE IF NOT EXISTS Contacts (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        first_name TEXT NOT NULL,
                        last_name TEXT NOT NULL,
                        email TEXT NOT NULL,
                        phone TEXT NOT NULL,
                        user_id INTEGER NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES Users(id)
                    );
                    """;

            stmt.execute(createUsers);
            stmt.execute(createContacts);
            System.out.println("Database initialized.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showAllUsers() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Login: " + rs.getString("login"));
                System.out.println("Password hash: " + rs.getString("password"));
                System.out.println("---");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean registerUser(String login, String passwordHash) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (login, password) VALUES (?, ?)")) {
            stmt.setString(1, login);
            stmt.setString(2, passwordHash);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static int loginUser(String login, String passwordHash) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Users WHERE login = ? AND password = ?")) {
            stmt.setString(1, login);
            stmt.setString(2, passwordHash);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void addContact(String firstName, String lastName, String email, String phone, int userId) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Contacts (first_name, last_name, email, phone, user_id) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setInt(5, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Contact> getContacts(int userId) {
        List<Contact> contacts = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Contacts WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String fullName = rs.getString("first_name") + " " + rs.getString("last_name");
                contacts.add(new Contact(fullName, rs.getString("email"), rs.getString("phone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public static void deleteContact(String email, int userId) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Contacts WHERE email = ? AND user_id = ?")) {
            stmt.setString(1, email);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateContact(String originalEmail, String firstName, String lastName, String newEmail, String phone, int userId) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Contacts SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE email = ? AND user_id = ?")) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, newEmail);
            stmt.setString(4, phone);
            stmt.setString(5, originalEmail);
            stmt.setInt(6, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}