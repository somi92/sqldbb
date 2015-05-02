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
import com.github.somi92.sqldbb.query.builder.IQueryBuilder;
import com.github.somi92.sqldbb.query.builder.QueryBuilder;
import com.github.somi92.sqldbb.query.builder.SelectQueryBuilder;

/**
 *
 * @author milos
 */
public class Main {
    
    public static void main(String[] args) {
        
//        DBBroker broker = new DB?Broker();
        ClassA a = new ClassA();
        ClassB b = new ClassB();
        ClassC c = new ClassC();
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
        
        IQueryBuilder iqb = new SelectQueryBuilder(true);
        QueryBuilder qb = new QueryBuilder(iqb);
        qb.buildQuery(dbe1);
        Query query = qb.getQuery();
        System.out.println(query);
        
        System.out.println("");
    }
}
