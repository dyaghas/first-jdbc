package com.hashpack;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Car> cars = new ArrayList<>();

    public static void createCarInstance(int id, Connection myConn) {
        Scanner scanner = new Scanner(System.in);
        id = id + 1;
        System.out.print("Car brand: ");
        String brand = scanner.nextLine();
        System.out.print("Car color: ");
        String color = scanner.nextLine();
        System.out.print("Car year: ");
        int model_year = scanner.nextInt();

        System.out.println(id);
        System.out.println(brand);
        System.out.println(color);
        System.out.println(model_year);

        try {
            Car car = new Car(id++, brand, color, model_year);
            PreparedStatement pstmt = myConn.prepareStatement(
                    "INSERT INTO car(brand, color, model_year) VALUE(?, ?, ?)");
            pstmt.setString(1, brand);
            pstmt.setString(2, color);
            pstmt.setInt(3, model_year);
            pstmt.executeUpdate();
        } catch (Exception IllegalArgumentException) {
            System.out.println("Adding the car was not possible");
        }
    }

    public static void listCars(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("select * from car");
        int j = 0;
        while(rs.next()) {
            System.out.println(cars.get(j).getCarParameters());
            j++;
        }
    }

    public static void main(String[] args) {
        try {
            //Connect to database
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_database",
                    "root", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("select * from car");

            int i = 0;
            int id = 0;
            while(myRs.next()) {
                //instantiate car parameters
                id = myRs.getInt("Car_id");
                String brand = myRs.getString("brand");
                String color = myRs.getString("color");
                int modelYear = myRs.getInt("model_year");

                //create a car object
                Car car = new Car(id, brand, color, modelYear);
                //add car object to cars arrayList
                cars.add(car);
                System.out.println(cars.get(i).getCarParameters());
                i++;
            }

            String input = "new";

            while(!input.equals("finish")) {
                System.out.print("commands: " +
                        "\n 'new' - add new element " +
                        "\n 'list' - list elements " +
                        "\n'finish' - finish application \n\n"
                );
                Scanner scan = new Scanner(System.in);
                input = scan.nextLine();

                //create new element
                if (input.equals("new")) {
                    createCarInstance(id, myConn);
                }

                //list elements
                if(input.equals("list")) {
                    listCars(myStmt);
                }
            }

            myRs.close();
            myStmt.close();
            myConn.close();

        } catch(Exception exc) {

        }
    }
}
