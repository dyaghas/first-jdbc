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

            //input initialization, value has to be different from "finish"
            String input = "new";
            while(!input.equals("finish")) {
                System.out.print("""
                        commands:\s
                        owner:
                            'register-owner' - register new owner\s
                        car:
                            'new-car' - add new car\s
                            'delete-car' - delete a car\s
                            'update-car' - update car information\s
                            'list-cars' - list cars\s
                            'search-car' - search for a specific car\s
                            
                        'finish' - finish application\s
                                                
                        """
                );

                Scanner scan = new Scanner(System.in);

                //select which action will be done
                input = scan.nextLine();
                switch(input) {
                    case "register-owner":
                        Owner.registerOwner(myConn);
                        break;
                    case "list-owners":
                        Owner.listOwners(myConn);
                        break;
                    case "new-car":
                        Car.createCarInstance(myConn);
                        break;
                    case "list-cars":
                        Car.listCars(myConn);
                        break;
                    case "search-car":
                        Car.searchCar(myConn, Car.getCarId());
                        break;
                    case "update-car":
                        Car.updateCar(myConn, Car.getCarId());
                    case "delete-car":
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
