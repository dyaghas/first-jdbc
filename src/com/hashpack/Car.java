package com.hashpack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class Car {

    //all valid brands for database INSERT and UPDATE
    private static String[] brands = new String[]{"bmw", "chevrolet", "ford", "honda", "hyundai", "porsche", "toyota",
    "volvo", "acura", "ferrari", "lamborghini", "nissan", "mazda", "volkswagen", "mercedes-benz", "audi", "renault",
            "peugeot", "opel", "citroen", "mitsubishi", "subaru", "suzuki", "fiat", "tesla", "mini", "jeep", "lexus",
            "chrysler", "jaguar", "dodge", "alfa romeo", "pontiac", "ram", "gmc", "lotus", "abarth", "bentley",
            "aston martin", "mclaren", "shelby", "bugatti", "koenigsegg"};

    //empty constructor only, methods are accessed directly through the class so objects are not needed in this case
    public Car() {

    }

    public static int getCarId() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("insert car ID: ");
        int id = scanner.nextInt();
        return id;
    }

    //verify if the specified brand is present in the array `brands`
    public static boolean verifyBrand(String b) {
        for(String e : brands) {
            if(e.equals(b)) {
                return true;
            }
        }
        return false;
    }

    //verify if the car model year is valid (there were no cars before 1886)
    public static boolean verifyYear(int y) {
        if(y > 1886 && y < 2023) {
            return true;
        } else {
            return false;
        }
    }

    //creates a car istance
    public static void createCarInstance(Connection myConn) {
        Scanner scanner = new Scanner(System.in);
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
                    System.out.println("Car successfully added to database");
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

    //shows all car instances available
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

    //search for a specific car instance
    public static void searchCar(Connection conn, int id) {
        try {
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

    //changes a car instance
    public static void updateCar(Connection conn, int id) {
        Scanner scanner = new Scanner(System.in);
        searchCar(conn, id);
        System.out.print("Car brand: ");
        String brand = scanner.nextLine();
        System.out.print("Car color: ");
        String color = scanner.nextLine();
        System.out.print("Car year: ");
        int model_year = scanner.nextInt();
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE car SET brand = ?, color = ?, model_year = ? WHERE car_id = ?");
            pstmt.setString(1, brand);
            pstmt.setString(2, color);
            pstmt.setInt(3, model_year);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Update done successfully");
        } catch (Exception IllegalArgumentException) {
            System.out.println("Updating car was not possible");
        }
    }

    //deletes a car instance
    public static void deleteCar(Connection conn, int id) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM car WHERE car_id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Car " + id + " successfully deleted from database");
        } catch (Exception IllegalArgumentException) {
            System.out.println("Deleting car was not possible");
        }
    }
}
