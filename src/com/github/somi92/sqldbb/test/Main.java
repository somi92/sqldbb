/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.test;

import com.github.somi92.sqldbb.broker.DBBroker;
import com.github.somi92.sqldbb.entity.DatabaseEntity;
import com.github.somi92.sqldbb.entity.processor.EntityProcessor;

/**
 *
 * @author milos
 */
public class Main {
    
    public static void main(String[] args) {
        
//        DBBroker broker = new DBBroker();
        ClassA a = new ClassA();
        ClassB b = new ClassB();
//        broker.saveOrUpdateEntity(a);
        
        DatabaseEntity dbe1 = EntityProcessor.createEntity(a.getClass());
        EntityProcessor.printEntity(dbe1);
        
        System.out.println("===================================================");
        
        DatabaseEntity dbe2 = EntityProcessor.createEntity(b.getClass());
        EntityProcessor.printEntity(dbe2);
    }
}
