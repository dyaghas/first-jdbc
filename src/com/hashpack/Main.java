package com.hashpack;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        try {
            //Connect to database
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_database",
                    "root", "");

            Statement myStmt = myConn.createStatement();

            ResultSet myRs = myStmt.executeQuery("select * from car");

            while(myRs.next()) {
                System.out.println(myRs.getString("brand"));
            }

        } catch(Exception exc) {

        }
    }
}
