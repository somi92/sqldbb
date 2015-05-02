/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.broker;

import com.github.somi92.sqldbb.annotations.Table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author milos
 */
public class DBBroker {
    
    private Connection connection;
    
    private String databaseDriver = "";
    private String hostPort = "";
    private String database = "";
    private String username = "";
    private String password = "";
    
    private void loadDBDriver() throws ClassNotFoundException {
        Class.forName(databaseDriver);
    }
    
    public void openDatabaseConnection() {
        try {
            loadDBDriver();
            connection = DriverManager.
                    getConnection("jdbc:mysql://"+hostPort+"/"+database+"?useUnicode=yes&characterEncoding=UTF-8", username, password);
            System.out.println("Connected to database: "+hostPort+"/"+database);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error loading driver: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Error opening database: "+ex.getMessage());
        }
    }
    
    public void closeDatabaseConnection() {
        try {
            connection.close();
            System.out.println("Disconnected from database: "+hostPort+"/"+database);
        } catch (SQLException ex) {
            System.out.println("Error closing database: "+ex.getMessage());
        }
    }
    
    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException ex) {
            System.out.println("Error commiting transaction: "+ex.getMessage());
        }
    }
    
    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            System.out.println("Error on transaction rollback: "+ex.getMessage());
        }
    }
    
    /* CRUD operations */
    
    public void saveOrUpdateEntity(Object o) {
        Class c = o.getClass();
        if(c.isAnnotationPresent(Table.class)) {
            Table t = (Table) c.getAnnotation(Table.class);
            System.out.println(t.value());
        } else {
            // error: the object is not annotated as a database entity
        }
    }
    
    public void loadEntity(Object o) {
        
    }
}
