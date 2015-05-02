/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.entity.processor;

import com.github.somi92.sqldbb.annotations.Column;
import com.github.somi92.sqldbb.annotations.ForeignKey;
import com.github.somi92.sqldbb.annotations.PrimaryKey;
import com.github.somi92.sqldbb.annotations.Table;
import com.github.somi92.sqldbb.entity.DatabaseEntity;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 *
 * @author milos
 */
public class EntityProcessor {
    
    public static DatabaseEntity createEntity(Class c) {
        DatabaseEntity dbEntity = new DatabaseEntity();
        try {
//            if(o == null) {
//                return null;
//            }
//            Class c = o.getClass();
            
            dbEntity.setEntityClass(c);
            
            if(c.isAnnotationPresent(Table.class)) {
                Table t = (Table) c.getAnnotation(Table.class);
                dbEntity.setTableName(t.value());
            }
            
            Field[] fields = c.getDeclaredFields();
            for(int i=0; i<fields.length; i++) {
                fields[i].setAccessible(true);
                Annotation[] annots = fields[i].getAnnotations();
                for(int j=0; j<annots.length; j++) {

                    if(annots[j] instanceof PrimaryKey) {
//                        dbEntity.addPrimaryKey(fields[i].getName(), fields[i].getType());
                        PrimaryKey pk = (PrimaryKey) annots[j];
                        dbEntity.addPrimaryKey(pk.value(), fields[i].getName(), fields[i].getType());
                        continue;
                    }

                    if(annots[j] instanceof Column) {
//                        dbEntity.addColumn(fields[i].getName(), fields[i].getType());
                        Column column = (Column) annots[j];
                        dbEntity.addColumn(column.value(), fields[i].getName(), fields[i].getType());
                        continue;
                    }
                    
                    if(annots[j] instanceof ForeignKey) {
                        DatabaseEntity e = createEntity(fields[i].getType());
                        ForeignKey fk = (ForeignKey) annots[j];
                        DatabaseEntity.ForeignKeyEntity fke = e.new ForeignKeyEntity(fk.referencingTable(), fk.referencingColumn(), e);
                        dbEntity.addForeignKey(fk.name(), fields[i].getName(), fields[i].getType(), fke);
                    }
                }
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error processing entity (illegal argument): "+e.getMessage());
//        } catch (IllegalAccessException e) {
//            System.out.println("Error processing entity (illegal access): "+e.getMessage());
        }
        
        return dbEntity;
    }
    
    public static HashMap<String,Object> getEntityColumnValues(DatabaseEntity dbe, Object o) {
        
        return null;
    }
    
    public static void printEntity(DatabaseEntity dbe) {
        if(dbe == null) {
            return;
        }
        System.out.println("Class: \t"+dbe.getEntityClass().getName());
        System.out.println("Table: \t"+dbe.getTableName());
        System.out.println("Primary keys: \t");
        for(String pk : dbe.getPrimaryKeys()) {
            System.out.println("\t\t"+pk);
        }
        System.out.println("Foreign references: ");
        String[] refsKeys = dbe.getForeignKeys().toArray(new String[dbe.getForeignKeys().size()]);
        for(int j=0; j<refsKeys.length; j++) {
            System.out.println("\t\t"+refsKeys[j]+" -> referencing -> table: "+
                    dbe.getReferences().get(refsKeys[j]).getReferencingTable()+
                    ", column: "+dbe.getReferences().get(refsKeys[j]).getReferencingColumn());
//            printEntity(dbe.getReferences().get(refsKeys[j]));
        }
        System.out.println("Columns: ");
        String[] columnKeys = dbe.getAllColumns().toArray(new String[dbe.getAllColumns().size()]);
        for(int i=0; i<columnKeys.length; i++) {
            System.out.println("\t\t"+columnKeys[i]+" -> mapping field -> "+dbe.getColumnFieldMapping().get(columnKeys[i]));
        }
        System.out.println("Fields:");
        String[] fieldKeys = dbe.getAllFields().toArray(new String[dbe.getAllFields().size()]);
        for(int k=0; k<fieldKeys.length; k++) {
            System.out.println("\t\t"+fieldKeys[k]+" "+dbe.getFieldTypes().get(fieldKeys[k]));
        }
    }
}
