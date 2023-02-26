package com.hashpack;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static Car car = new Car() {

    };

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
                        'list' - list cars\s
                        'finish' - finish application\s
                        'search' - search for a specific car

                        """
                );
                Scanner scan = new Scanner(System.in);
                input = scan.nextLine();
                switch(input) {
                    case "new":
                        car.createCarInstance(id, myConn);
                        break;
                    case "list":
                        car.listCars(myConn);
                        break;
                    case "search":
                        car.searchCar(myConn);
                        break;
                    default:
                        System.out.println("invalid command");
                }
            }
            myStmt.close();
            myConn.close();
        } catch(Exception exc) {

        }
    }
}
