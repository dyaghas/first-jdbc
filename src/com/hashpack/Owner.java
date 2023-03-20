package com.hashpack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Owner {

    public static int getOwnerId() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("insert owner ID: ");
        int id = scanner.nextInt();
        return id;
    }

    public static void registerOwner(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Owner first name: ");
        String first_name = scanner.nextLine();
        System.out.print("Owner second name: ");
        String second_name = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO owner(first_name, second_name, age) VALUE(?, ?, ?)");
            pstmt.setString(1, first_name);
            pstmt.setString(2, second_name);
            pstmt.setInt(3, age);
            pstmt.executeUpdate();
            System.out.println("Owner successfully added to database");
        } catch (Exception IllegalArgumentException) {
            System.out.println("Registering owner was not possible");
        }
    }

    public static void listOwners(Connection conn) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT owner_id, first_name, second_name, age FROM owner");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt(1);
                String first_name = rs.getString(2);
                String second_name = rs.getString(3);
                String age = rs.getString(4);
                System.out.println("ID: " + id + "\n" +
                        "First name:" + first_name + "\n" +
                        "Second name: " + second_name + "\n" +
                        "Age: " + age + "\n" +
                        "-------------------------------");
            }
            rs.close();
            pstmt.close();
        } catch (Exception IllegalArgumentException) {
            System.out.println("Listing owners was not possible");
        }
    }

    //search for a specific owner instance
    public static void searchOwner(Connection conn, int id) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT owner_id, first_name, second_name, age FROM owner WHERE owner_id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                id = rs.getInt(1);
                String first_name = rs.getString(2);
                String second_name = rs.getString(3);
                String age = rs.getString(4);
                System.out.println("ID: " + id + "\n" +
                        "First name:" + first_name + "\n" +
                        "Second name: " + second_name + "\n" +
                        "Age: " + age + "\n" +
                        "-------------------------------");
            }
            rs.close();
            pstmt.close();
        } catch (Exception IllegalArgumentException) {
            System.out.println("Searching for the car was not possible");
        }
    }
}
