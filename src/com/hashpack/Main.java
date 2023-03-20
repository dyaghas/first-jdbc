package com.hashpack;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Properties props = new Properties();
        try (InputStream in = Main.class.getResourceAsStream("/com/hashpack/config/config.properties")) {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        try {
            //Connect to database
            //Database user and password are accessed through the getenv function. To make it work in a different
            //machine, create your own environment variables or hardcode them instead.
            Connection myConn = DriverManager.getConnection(url, username, password);
            Statement myStmt = myConn.createStatement();

            //input initialization, value has to be different from "finish"
            String input = "new";
            while(!input.equals("finish")) {
                System.out.print("""
                        commands:\s
                        owner:
                            'register-owner' - register new owner\s
                            'search-owner' - search for a specific owner\s
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
                    case "search-owner":
                        Owner.searchOwner(myConn, Owner.getOwnerId());
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
                        break;
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
            exc.printStackTrace();
            System.out.println("Something went wrong");
        }
    }
}
