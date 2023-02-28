package com.hashpack;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            //Connect to database
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_database",
                    "root", "");
            Statement myStmt = myConn.createStatement();

            int id = 0;
            //input initialization, value has to be different from "finish"
            String input = "new";
            while(!input.equals("finish")) {
                System.out.print("""
                        commands:\s
                        'new' - add new car\s
                        'delete' - delete a car\s
                        'update' - update car information\s
                        'list' - list cars\s
                        'search' - search for a specific car\s
                        'finish' - finish application\s
                                                
                        """
                );

                //select which action will be done
                Scanner scan = new Scanner(System.in);
                input = scan.nextLine();
                switch(input) {
                    case "new":
                        Car.createCarInstance(id, myConn);
                        break;
                    case "list":
                        Car.listCars(myConn);
                        break;
                    case "search":
                        Car.searchCar(myConn, Car.getCarId());
                        break;
                    case "update":
                        Car.updateCar(myConn, Car.getCarId());
                    case "delete":
                        Car.deleteCar(myConn, Car.getCarId());
                        break;
                    default:
                        System.out.println("invalid command");
                }
            }
            //close database connection
            myStmt.close();
            myConn.close();
        } catch(Exception exc) {
            System.out.println("Something went wrong");
        }
    }
}
