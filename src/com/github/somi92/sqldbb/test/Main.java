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
import java.util.HashMap;

/**
 *
 * @author milos
 */
public class Main {
    
    public static void main(String[] args) {
        
//        DBBroker broker = new DB?Broker();
        ClassC c = new ClassC(31, 3);
        ClassB b = new ClassB(21, "b1", c);
        ClassA a = new ClassA(11, 111, "a1", b);
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
        
        HashMap<String,Object> fieldValues = EntityProcessor.getEntityFieldValues(dbe1, a);
        System.out.println("Fields values of object: "+dbe1.getEntityClass());
        if(fieldValues != null) {
            for(String s : fieldValues.keySet()) {
                System.out.println("\t\tfield: "+s+" -> "+fieldValues.get(s));
            }
        } else {
            System.out.println("Type error!");
        }
    }
}
