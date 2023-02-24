package com.hashpack;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Car> cars = new ArrayList<>();

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
                id = myRs.getInt("CarID");
                String brand = myRs.getString("brand");
                String color = myRs.getString("color");
                int modelYear = myRs.getInt("modelYear");

                //create a car object
                Car car = new Car(id, brand, color, modelYear);
                //add car object to cars arrayList
                cars.add(car);
                System.out.println(cars.get(i).getCarParameters());
                i++;

            }

            String input = "new";

            while(!input.equals("exit")) {
                System.out.print("type 'new' to add a new car or 'exit' to finish: ");
                Scanner scan = new Scanner(System.in);
                input = scan.nextLine();
                if (input.equals("new")) {
                    System.out.print("Car brand: ");
                    String brand = scan.nextLine();
                    System.out.print("Car color: ");
                    String color = scan.nextLine();
                    System.out.print("Car year: ");
                    int modelYear = scan.nextInt();

                    try {
                        Car car = new Car(id++, brand, color, modelYear);
                        PreparedStatement pstmt = myConn.prepareStatement("INSERT INTO car(brand, color, modelYear)" +
                                "VALUES(?, ?, ?)");
                        pstmt.setString(1, brand);
                        pstmt.setString(2, color);
                        pstmt.setInt(3, modelYear);
                        pstmt.executeUpdate();
                    } catch (Exception IllegalArgumentException) {
                        System.out.println("Adding the car was not possible");
                    }
                }
            }

            myRs.close();
            myStmt.close();
            myConn.close();

        } catch(Exception exc) {

        }
    }
}
