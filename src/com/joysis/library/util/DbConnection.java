/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysis.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DbConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/dblibms";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    static {
        try {
            Class.forName(DRIVER); // load the driver
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load JDBC Driver: " + e.getMessage());
        }
    }
    
    public Connection connect() throws SQLException{ // connection OBJECT
        return DriverManager.getConnection(URL, USERNAME, PASSWORD); // register the driver
    }
    
}
