package com.hashpack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class Car {

    private static String[] brands = new String[]{"bmw", "chevrolet", "ford", "honda", "hyundai", "porsche", "toyota"};

    public Car() {

    }

    public static boolean verifyBrand(String b) {
        for(String e : brands) {
            if(e.equals(b)) {
                return true;
            }
        }
        return false;
    }

    public static boolean verifyYear(int y) {
        if(y > 1886 && y < 2023) {
            return true;
        } else {
            return false;
        }
    }

    public static void createCarInstance(int id, Connection myConn) {
        Scanner scanner = new Scanner(System.in);
        id = id + 1;
        System.out.print("Car brand: ");
        String brand = scanner.nextLine();
        System.out.print("Car color: ");
        String color = scanner.nextLine();
        System.out.print("Car year: ");
        int model_year = scanner.nextInt();

        //verify if car brand is valid
        if(verifyBrand(brand)) {
            //verify if car manufacture year is valid
            if(verifyYear(model_year)) {
                System.out.println(id);
                System.out.println(brand);
                System.out.println(color);
                System.out.println(model_year);

                try {
                    PreparedStatement pstmt = myConn.prepareStatement(
                            "INSERT INTO car(brand, color, model_year) VALUE(?, ?, ?)");
                    pstmt.setString(1, brand);
                    pstmt.setString(2, color);
                    pstmt.setInt(3, model_year);
                    pstmt.executeUpdate();
                } catch (Exception IllegalArgumentException) {
                    System.out.println("Adding the car was not possible");
                }
            } else {
                System.out.println("Invalid year");
            }
        } else {
            System.out.println("Invalid brand");
        }
    }

    public static void listCars(Connection conn){
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT car_id, color, model_year, brand FROM car");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt(1);
                String color = rs.getString(2);
                String modelYear = rs.getString(3);
                String brand = rs.getString(4);
                System.out.println("ID: " + id + "\n" +
                        "Brand:" + brand + "\n" +
                        "Color: " + color + "\n" +
                        "Model year: " + modelYear + "\n" +
                        "-------------------------------");
            }
            rs.close();
            pstmt.close();
        } catch (Exception IllegalArgumentException) {
            System.out.println("Listing cars was not possible");
        }
    }

    public static void searchCar(Connection conn) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("insert car ID: ");
            int id = scanner.nextInt();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT car_id, color, model_year, brand FROM car WHERE car_id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                id = rs.getInt(1);
                String color = rs.getString(2);
                String modelYear = rs.getString(3);
                String brand = rs.getString(4);
                System.out.println("ID: " + id + "\n" +
                        "Brand:" + brand + "\n" +
                        "Color: " + color + "\n" +
                        "Model year: " + modelYear + "\n" +
                        "-------------------------------");
            }
            rs.close();
            pstmt.close();
        } catch (Exception IllegalArgumentException) {
            System.out.println("Searching for the car was not possible");
        }
    }

    public static void deleteCar(int id) {

    }
}
