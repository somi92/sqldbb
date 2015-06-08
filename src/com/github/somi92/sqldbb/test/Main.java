/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.test;

import com.github.somi92.sqldbb.broker.DBBroker;
import com.github.somi92.sqldbb.entity.DatabaseEntity;
import com.github.somi92.sqldbb.entity.processor.EntityProcessor;
import com.github.somi92.sqldbb.query.Query;
import com.github.somi92.sqldbb.query.builder.DeleteQueryBuilder;
import com.github.somi92.sqldbb.query.builder.InsertQueryBuilder;
import com.github.somi92.sqldbb.query.builder.QueryBuilder;
import com.github.somi92.sqldbb.query.builder.SelectQueryBuilder;
import com.github.somi92.sqldbb.query.builder.UpdateQueryBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author milos
 */
public class Main {
    
    private static DBBroker broker;
    
    public static void main(String[] args) throws SQLException, Exception {
        /*
//        DBBroker broker = new DB?Broker();
        ClassD d = new ClassD(41, "d1");
        ClassC c = new ClassC(31, 3);
        ClassB b = new ClassB(21, "b1", c);
        ClassA a = new ClassA(11, 111, "a1", b, d);
//        broker.saveOrUpdateEntity(a);
        
        DatabaseEntity dbe1 = EntityProcessor.createEntity(a.getClass());
        EntityProcessor.printEntity(dbe1);
        
        System.out.println("===================================================="
                + "===================================\n");
        
        DatabaseEntity dbe2 = EntityProcessor.createEntity(b.getClass());
        EntityProcessor.printEntity(dbe2);
        
        System.out.println("===================================================="
                + "===================================\n");
        
        DatabaseEntity dbe3 = EntityProcessor.createEntity(c.getClass());
        EntityProcessor.printEntity(dbe3);
        
        System.out.println("===================================================="
                + "===================================\n");
        
        QueryBuilder qb = new QueryBuilder(new SelectQueryBuilder(true));
        qb.buildQuery(dbe1);
        Query query = qb.getQuery();
        System.out.println(query);
        System.out.println("");
        
        System.out.println("===================================================="
                + "===================================\n");
        
        qb = new QueryBuilder(new UpdateQueryBuilder());
        qb.buildQuery(dbe1);
        query = qb.getQuery();
        System.out.println(query);
        System.out.println("");
        
        System.out.println("===================================================="
                + "===================================\n");
        
        qb = new QueryBuilder(new InsertQueryBuilder());
        qb.buildQuery(dbe1);
        query = qb.getQuery();
        System.out.println(query);
        System.out.println("");
        
        System.out.println("===================================================="
                + "===================================\n");
        
        qb = new QueryBuilder(new DeleteQueryBuilder(false));
        qb.buildQuery(dbe1);
        query = qb.getQuery();
        System.out.println(query);
        System.out.println("");
        
        System.out.println("===================================================="
                + "===================================\n");
        
        EntityProcessor.setEntityFieldValues(dbe1, a);
        System.out.println("Fields values of object: "+dbe1.getEntityClass());
        if(dbe1.getFieldValues() != null) {
            for(String s : dbe1.getFieldValues().keySet()) {
                System.out.println("\t\tfield: "+s+" -> "+dbe1.getFieldValues().get(s));
            }
        } else {
            System.out.println("Type error!");
        }
        */
        
        
        /*DBBroker broker = new DBBroker();
        broker.openDatabaseConnection();
        
        ClassD d = new ClassD();
        d.setD1(44);
        
        ClassC c = new ClassC();
        c.setC1(32);
        
        ClassB b = new ClassB();
        b.setB1(21);
        
        ClassA a = new ClassA();
        a.setA1(11);
        a.setA11(111);
        
        Object[] loaded = new Object[4];
        
        loaded[0] = broker.loadEntity(d);
        loaded[1] = broker.loadEntity(c);
        loaded[2] = broker.loadEntity(b);
        loaded[3] = broker.loadEntity(a);
        
        broker.closeDatabaseConnection();
        
        System.out.println("");
        for(Object o : loaded) {
            System.out.println(o);
        }
        */
//        System.out.println("");
//        broker.openDatabaseConnection();
//        ClassD searchD = broker.loadEntity(new ClassD(45, "search"));
//        searchD.setD2("search");
//        ClassA searchA = new ClassA();
//        searchA.setA2("search");
//        searchA.setD(searchD);
//        
//        List<String> searchCriteria = new ArrayList<>();
////        searchCriteria.add("a2");
//        searchCriteria.add("d");
//        List<ClassA> list = broker.loadEntities(searchA, searchCriteria);
//        for(ClassA entity : list) {
//            System.out.println(entity);
//        }
        
//        ClassB b1 = broker.loadEntity(new ClassB(23, null, c));
//        ClassA forUpdate = broker.loadEntity(a);
//        forUpdate.setA1(11);
//        forUpdate.setA11(111);
//        forUpdate.setA2("UPDATED a2");
//        forUpdate.setB(b1);
//        int rowsAffected = broker.updateEntity(forUpdate);
//        if(rowsAffected>0) {
//            broker.commitTransaction();
//        }
//        
//        broker.closeDatabaseConnection();
        /*
        ClassD insertD = new ClassD(45, "d5");
        ClassC insertC = new ClassC(34, 304);
        ClassB insertB = new ClassB(24, "b4", insertC);
        ClassA insertA = new ClassA(13, 113, "a3", insertB, insertD);
        
//        broker.openDatabaseConnection();
//        if(broker.insertEntity(insertA)>0) {
//            broker.commitTransaction();
//            System.out.println("Inserted "+insertA);
//        }
//        broker.closeDatabaseConnection();
        
        ClassA deleteA = new ClassA();
        deleteA.setA1(14);
        deleteA.setA11(114);
        broker.openDatabaseConnection();
        if(broker.deleteEntity(deleteA)>0) {
            broker.commitTransaction();
        }
        broker.closeDatabaseConnection();
        
        System.out.println("");*/
        
        broker = new DBBroker();
        broker.openDatabaseConnection();
        
//        testEntityProcessorAndQueryBuilders();
//        testComplexKeys();
//        testEntityLoading();
        testCollections();
//        testInsert();
//        testTypes();
        
//        String s = broker.getMaxColumnValue(new ClassA(), "a1");
//        System.out.println(s);
        
        broker.closeDatabaseConnection();
        
    }
    
    public static void testEntityProcessorAndQueryBuilders() {
        ClassD d = new ClassD(41, "d1");
        ClassC c = new ClassC(31, 3);
        ClassB b = new ClassB(21, "b1", c);
        ClassA a = new ClassA(11, 111, "a1", b, d);
        
        ClassF f = new ClassF();
        
        DatabaseEntity dbe1 = EntityProcessor.createEntity(a.getClass());
        EntityProcessor.printEntity(dbe1);
        
        System.out.println("===================================================="
                + "===================================\n");
        
//        DatabaseEntity dbe2 = EntityProcessor.createEntity(b.getClass());
//        EntityProcessor.printEntity(dbe2);
//        
//        System.out.println("===================================================="
//                + "===================================\n");
//        
//        DatabaseEntity dbe3 = EntityProcessor.createEntity(c.getClass());
//        EntityProcessor.printEntity(dbe3);
//        
//        System.out.println("===================================================="
//                + "===================================\n");
//        
//        EntityProcessor.setEntityFieldValues(dbe1, a);
//        System.out.println("Fields values of object: "+dbe1.getEntityClass());
//        if(dbe1.getFieldValues() != null) {
//            for(String s : dbe1.getFieldValues().keySet()) {
//                System.out.println("\t\tfield: "+s+" -> "+dbe1.getFieldValues().get(s));
//            }
//        } else {
//            System.out.println("Type error!");
//        }
//        
//        System.out.println("===================================================="
//                + "===================================\n");
        
        QueryBuilder qb = new QueryBuilder(new SelectQueryBuilder(true));
        qb.buildQuery(dbe1);
        Query query = qb.getQuery();
        System.out.println(query);
        System.out.println("");
        
        System.out.println("===================================================="
                + "===================================\n");
        
        qb = new QueryBuilder(new UpdateQueryBuilder());
        qb.buildQuery(dbe1);
        query = qb.getQuery();
        System.out.println(query);
        System.out.println("");
        
        System.out.println("===================================================="
                + "===================================\n");
        
        qb = new QueryBuilder(new InsertQueryBuilder());
        qb.buildQuery(dbe1);
        query = qb.getQuery();
        System.out.println(query);
        System.out.println("");
        
        System.out.println("===================================================="
                + "===================================\n");
        
        qb = new QueryBuilder(new DeleteQueryBuilder(false));
        qb.buildQuery(dbe1);
        query = qb.getQuery();
        System.out.println(query);
        System.out.println("");
        
        System.out.println("===================================================="
                + "===================================\n");
    }
    
    public static void testEntityLoading() throws SQLException {
        ClassD d = new ClassD();
        d.setD1(44);
        
        ClassC c = new ClassC();
        c.setC1(32);
        
        ClassB b = new ClassB();
        b.setB1(21);
        
        ClassA a = new ClassA();
        a.setA1(11);
        a.setA11(111);
        
        Object[] loaded = new Object[4];
        
        loaded[0] = broker.loadEntity(d, false);
        loaded[1] = broker.loadEntity(c, false);
        loaded[2] = broker.loadEntity(b, false);
        loaded[3] = broker.loadEntity(a, false);
        
        System.out.println("");
        for(Object o : loaded) {
            System.out.println(o);
        }
    }
    
    public static void testComplexKeys() throws SQLException {
//        List<String> search = new ArrayList<>();
//        search.add("d");
//        ClassCD newCD = new ClassCD();
//        newCD.setD(new ClassD(42, null));
//        List<ClassCD> list = broker.loadEntities(newCD, null);
//        for(ClassCD cd : list) {
//            System.out.println(cd);
//        }
        ClassCD loaded = new ClassCD();
        loaded.setC(new ClassC(31, 0));
        loaded.setD(new ClassD(44, null));
        loaded = broker.loadEntity(loaded, false);
        System.out.println(loaded);
    }
    
    public static void testInsert() {
        try {
            ClassD d1 = new ClassD(41, null);
            ClassD d2 = new ClassD(43, null);
            ClassD d3 = new ClassD(45, null);
            ClassB b1 = new ClassB(21, null, null);
            ClassB b2 = new ClassB(23, null, null);
            ClassA a1 = new ClassA(14, 114, "a4-b3-d5", b2, d3);
            ClassA a2 = new ClassA(15, 115, "a5-b1-d3", b1, d2);
            ClassA a3 = new ClassA(16, 116, "a6-b3-d1", b2, d1);
            List<ClassA> list = new ArrayList<>();
            list.add(a1);
            list.add(a2);
            list.add(a3);
//            broker.insertEntities(list);
            
            ClassA a4 = new ClassA(17, 117, "a6-b3-d1", b2, d1);
            broker.insertEntity(a4);
            
            broker.commitTransaction();
            System.out.println("Inserting entities successful.");
        } catch (SQLException ex) {
            broker.rollbackTransaction();
            System.out.println("Inserting entities - transaction rollback: "+ex.getMessage());
        }
    }
    
    public static void testCollections() throws SQLException {
        ClassF f = new ClassF();
        f.setF2("F1");
//        ClassG g = new ClassG(null, 1, "s");
        List<String> search = new ArrayList<>();
        search.add("f2");
        List<ClassF> loadedF = broker.loadEntities(f, null, true);
//        System.out.println(loadedF);
        for(ClassF fe : loadedF) {
            System.out.println(fe);
        }
    }
    
    public static void testTypes() {
        try {
            TypesTesting tt = new TypesTesting();
            tt.setL(1000);
//            byte b = 1;
//            tt.setB(b);
//            short s = 2;
//            tt.setS(b);
//            tt.setI(3);
//            tt.setStr("String");
//            tt.setF(4.4f);
//            tt.setD(5.5d);
//            tt.setBool(true);
//            tt.setC('c');
//            tt.setDate(new Date());
            
//            broker.insertEntity(tt);
            
            TypesTesting loaded = broker.loadEntity(tt, false);
            System.out.println(loaded);
            
            broker.commitTransaction();
            System.out.println("Types testing - OK!");
        } catch (SQLException ex) {
            broker.rollbackTransaction();
            System.out.println("Types testing - Error: "+ex.getMessage());
        }
    }
}
