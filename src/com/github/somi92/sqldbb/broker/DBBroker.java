/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.broker;

import com.github.somi92.sqldbb.entity.DatabaseEntity;
import com.github.somi92.sqldbb.entity.processor.EntityProcessor;
import com.github.somi92.sqldbb.query.Query;
import com.github.somi92.sqldbb.query.builder.DeleteQueryBuilder;
import com.github.somi92.sqldbb.query.builder.InsertQueryBuilder;
import com.github.somi92.sqldbb.query.builder.QueryBuilder;
import com.github.somi92.sqldbb.query.builder.SelectQueryBuilder;
import com.github.somi92.sqldbb.query.builder.UpdateQueryBuilder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
//        PreparedStatement ps = connection.fillPreparedStatement(query.toString());
//        if(query.toString().contains("?")) {
//            
//        }
        return null;
    }
    
    /* CRUD operations */
      
    public void saveOrUpdateEntity(Object o) {
        
    }
    
    public <T> T loadEntity(T o, boolean loadCollections) throws SQLException {
        DatabaseEntity dbe = EntityProcessor.createEntity(o.getClass());
        EntityProcessor.setEntityFieldValues(dbe, o);
        QueryBuilder qb = new QueryBuilder(new SelectQueryBuilder(true));
        qb.buildQuery(dbe);
        Query query = qb.getQuery();
        PreparedStatement ps = connection.prepareStatement(query.toString());
        qb.fillPreparedStatement(ps, dbe);
        ResultSet rs = ps.executeQuery();
        T entity = null;
        while(rs.next()) {
            entity = createTypeInstance(dbe, rs, loadCollections);
        }
        rs.close();
        ps.close();
        return entity;
    }
    
    public <T> List<T> loadEntities(T o, List<String> searchCriteria, boolean loadCollections) throws SQLException {
        DatabaseEntity dbe = EntityProcessor.createEntity(o.getClass());
        QueryBuilder qb;
        if(searchCriteria != null) {
            qb = new QueryBuilder(new SelectQueryBuilder(searchCriteria));
        } else {
            qb = new QueryBuilder(new SelectQueryBuilder(false));
        }
        qb.buildQuery(dbe);
        Query query = qb.getQuery();
        ResultSet rs;
        if(searchCriteria != null) {
            PreparedStatement ps = connection.prepareStatement(query.toString());
            EntityProcessor.setEntityFieldValues(dbe, o);
            qb.fillPreparedStatement(ps, dbe);
            rs = ps.executeQuery();
        } else {
            Statement stm = connection.createStatement();
            rs = stm.executeQuery(query.toString());
        }
        List<T> entities = new ArrayList<>();
        while(rs.next()) {
            T entity = createTypeInstance(dbe, rs, loadCollections);
            entities.add(entity);
        }
        return entities;
    }
    
    public <T> int insertEntity(T o) throws SQLException {
        DatabaseEntity dbe = EntityProcessor.createEntity(o.getClass());
        EntityProcessor.setEntityFieldValues(dbe, o);
        QueryBuilder qb = new QueryBuilder(new InsertQueryBuilder());
        qb.buildQuery(dbe);
        Query query = qb.getQuery();
        PreparedStatement ps = connection.prepareStatement(query.toString());
        qb.fillPreparedStatement(ps, dbe);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    
    public <T> int[] insertEntities(List<T> list) throws SQLException {
        DatabaseEntity dbe = EntityProcessor.createEntity(list.get(0).getClass());
        QueryBuilder qb = new QueryBuilder(new InsertQueryBuilder());
        qb.buildQuery(dbe);
        Query query = qb.getQuery();
        int[] returnValue = null;
        PreparedStatement ps = connection.prepareStatement(query.toString());
        for(int i=0; i<list.size(); i++) {
            EntityProcessor.setEntityFieldValues(dbe, list.get(i));
            qb.fillPreparedStatement(ps, dbe);
            ps.addBatch();
            if((i+1)%500 == 0) {
                ps.executeBatch();
            }
        }
        returnValue = ps.executeBatch();
        return returnValue;
    }

    public <T> int updateEntity(T o) throws SQLException {
        DatabaseEntity dbe = EntityProcessor.createEntity(o.getClass());
        EntityProcessor.setEntityFieldValues(dbe, o);
        QueryBuilder qb = new QueryBuilder(new UpdateQueryBuilder());
        qb.buildQuery(dbe);
        Query query = qb.getQuery();
        PreparedStatement ps = connection.prepareStatement(query.toString());
        qb.fillPreparedStatement(ps, dbe);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    
    public <T> int deleteEntity(T o) throws SQLException {
        DatabaseEntity dbe = EntityProcessor.createEntity(o.getClass());
        EntityProcessor.setEntityFieldValues(dbe, o);
        QueryBuilder qb = new QueryBuilder(new DeleteQueryBuilder(false));
        qb.buildQuery(dbe);
        Query query = qb.getQuery();
        PreparedStatement ps = connection.prepareStatement(query.toString());
        qb.fillPreparedStatement(ps, dbe);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    } 
    
    private <T> T createTypeInstance(DatabaseEntity dbe, ResultSet rs, boolean loadCollections) throws SQLException {
        try {
            T t = (T) dbe.getEntityClass().newInstance();
            List<String> fields = dbe.getAllFields();
            for(String field : fields) {
                Class fieldType = dbe.getFieldTypes().get(field);
                String column = dbe.getTableName()+"."+dbe.getFieldColumnMapping().get(field);
                Class entityClass = dbe.getEntityClass();
                String first = field.charAt(0)+"";
                String methodName = "set"+first.toUpperCase()+field.substring(1);
                Method method = entityClass.getDeclaredMethod(methodName, new Class[] {fieldType});
                
                if(dbe.getForeignKeys().contains(dbe.getFieldColumnMapping().get(field))) {
                    boolean isCollectionItem = dbe.getReferences().get(dbe.getFieldColumnMapping()
                            .get(field)).isIsCollectionItem();
                    if(!loadCollections || (loadCollections && !isCollectionItem)) {
                        DatabaseEntity dbe2 = dbe.getReferences().get(dbe.getFieldColumnMapping().get(field)).getDbe();
                        method.invoke(t, new Object[] {createTypeInstance(dbe2, rs, loadCollections)});
                        continue;
                    }
                }
                
//                if((dbe.getForeignKeys().contains(dbe.getFieldColumnMapping().get(field)) && 
//                        !dbe.getReferences().get(dbe.getFieldColumnMapping().get(field)).isIsCollectionItem()) 
//                        
//                        ||
//                        
//                        (dbe.getForeignKeys().contains(dbe.getFieldColumnMapping().get(field)) &&
//                        !dbe.getReferences().get(dbe.getFieldColumnMapping().get(field)).isIsCollectionItem() &&
//                        loadCollections)) {
//                    
//                    DatabaseEntity dbe2 = dbe.getReferences().get(dbe.getFieldColumnMapping().get(field)).getDbe();
//                    method.invoke(t, new Object[] {createTypeInstance(dbe2, rs, loadCollections)});
//                    continue;
//                } else {
//                    
//                }
                
                switch(fieldType.getSimpleName()) {
                    case "byte":
                        method.invoke(t, new Object[] {rs.getByte(column)});
                        break;
                    case "short":
                        method.invoke(t, new Object[] {rs.getShort(column)});
                        break;
                    case "int":
                        method.invoke(t, new Object[] {rs.getInt(column)});
                        break;
                    case "String":
                        method.invoke(t, new Object[] {rs.getString(column)});
                        break;
                    case "long":
                        method.invoke(t, new Object[] {rs.getLong(column)});
                        break;
                    case "float":
                        method.invoke(t, new Object[] {rs.getFloat(column)});
                        break;
                    case "double":
                        method.invoke(t, new Object[] {rs.getDouble(column)});
                        break;
                    case "boolean":
                        method.invoke(t, new Object[] {rs.getBoolean(column)});
                        break;
                    case "char":
                        method.invoke(t, new Object[] {rs.getString(column).charAt(0)});
                        break;
                    case "Date":
                        method.invoke(t, new Object[] {rs.getDate(column)});
                        break;
                    default:
//                        method.invoke(t, new Object[] {rs.getInt(column)});
                }
                
                if(dbe.getParentEntities().containsKey(field) && loadCollections) {
                    DatabaseEntity.CollectionEntity ce = dbe.getParentEntities().get(field);
                    List<String> searchCriteria = new ArrayList<>();
                    String referencingField = ce.getReferencingField();
                    searchCriteria.add(referencingField);
                    String setterName = "set"+(referencingField.charAt(0)+"").toUpperCase()+referencingField.substring(1);
                    Object temp = ce.getChildEntityClass().newInstance();
                    Method setterMethod = ce.getChildEntityClass().getDeclaredMethod(setterName, new Class[] {dbe.getEntityClass()});
                    setterMethod.invoke(temp, new Object[] {t});
                    List list = loadEntities(temp, searchCriteria, loadCollections);
                    method.invoke(t, new Object[] {list});
                    for(Object o : list) {
//                        Method setterMethod = ce.getChildEntityClass().getDeclaredMethod(setterName, new Class[] {fieldType});
                        setterMethod.invoke(o, new Object[] {t});
                    }
                }
            }
            
            return t;
        } catch (InstantiationException ex) {
            System.out.println(ex.getClass().getSimpleName()+": "+ex.getMessage());
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            System.out.println(ex.getClass().getSimpleName()+": "+ex.getMessage());
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            System.out.println(ex.getClass().getSimpleName()+": "+ex.getMessage());
            ex.printStackTrace();
        } catch (SecurityException ex) {
            System.out.println(ex.getClass().getSimpleName()+": "+ex.getMessage());
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getClass().getSimpleName()+": "+ex.getMessage());
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            System.out.println(ex.getClass().getSimpleName()+": "+ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
