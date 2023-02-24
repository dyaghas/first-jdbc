package com.hashpack;

import java.sql.*;
import java.util.ArrayList;

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
            while(myRs.next()) {
                //instantiate car parameters
                int id = myRs.getInt("CarID");
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

            myRs.close();
            myStmt.close();
            myConn.close();

        } catch(Exception exc) {

        }
    }
}
