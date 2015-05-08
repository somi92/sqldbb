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
import java.util.List;

/**
 *
 * @author milos
 */
public class Main {
    
    public static void main(String[] args) throws SQLException {
        
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
        
        
        
        DBBroker broker = new DBBroker();
        /*broker.openDatabaseConnection();
        
        ClassD d = new ClassD();
        d.setD1(41);
        
        ClassC c = new ClassC();
        c.setC1(32);
        
        ClassB b = new ClassB();
        b.setB1(21);
        
        ClassA a = new ClassA();
        a.setA1(12);
        a.setA11(112);
        
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
        System.out.println("");
        broker.openDatabaseConnection();
        List<ClassA> list = broker.loadEntities(new ClassA());
        for(ClassA entity : list) {
            System.out.println(entity);
        }
        ClassB b1 = broker.loadEntity(new ClassB(23, null, c));
        ClassA forUpdate = broker.loadEntity(a);
        forUpdate.setA1(11);
        forUpdate.setA11(111);
        forUpdate.setA2("updated a2");
        forUpdate.setB(b1);
        int rowsAffected = broker.updateEntity(forUpdate);
        if(rowsAffected>0) {
            broker.commitTransaction();
        }
        
        broker.closeDatabaseConnection();
        System.out.println("");
    }
}
