/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.broker;

import com.github.somi92.sqldbb.entity.DatabaseEntity;
import com.github.somi92.sqldbb.entity.processor.EntityProcessor;
import com.github.somi92.sqldbb.query.Query;
import com.github.somi92.sqldbb.query.builder.QueryBuilder;
import com.github.somi92.sqldbb.query.builder.SelectQueryBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author milos
 */
public class DBBroker {
    
    private Connection connection;
    
    private String databaseDriver = "com.mysql.jdbc.Driver";
    private String hostPort = "localhost:3306";
    private String database = "sqldbb-test";
    private String username = "seecsk";
    private String password = "seecsk@2015";
    
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
    
    /* Utility methods */
    private ResultSet executePreparedStatementQuery(DatabaseEntity dbe, Query query) throws SQLException {
//        PreparedStatement ps = connection.prepareStatement(query.toString());
//        if(query.toString().contains("?")) {
//            
//        }
        return null;
    }
    
    /* CRUD operations */
      
    public void saveOrUpdateEntity(Object o) {
        
    }
    
    public void loadEntity(Object o) throws SQLException {
        DatabaseEntity dbe = EntityProcessor.createEntity(o.getClass());
        EntityProcessor.setEntityFieldValues(dbe, o);
        QueryBuilder qb = new QueryBuilder(new SelectQueryBuilder(true));
        qb.buildQuery(dbe);
        Query query = qb.getQuery();
        PreparedStatement ps = connection.prepareStatement(query.toString());
        qb.prepareStatement(ps, dbe);
        ResultSet rs = ps.executeQuery();
        
    }

}
