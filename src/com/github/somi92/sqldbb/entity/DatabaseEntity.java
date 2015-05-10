/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author milos
 */
public class DatabaseEntity {
    
    private Class entityClass;
    private String tableName;
    
    private List<String> primaryKeys;
    private List<String> foreignKeys;
    private List<String> allColumns;
    
    private List<String> allFields;
    private HashMap<String,String> columnFieldMapping;
    
    private HashMap<String,Class> fieldTypes;
    private HashMap<String,Object> fieldValues;
    private HashMap<String,ForeignKeyEntity> references;

    public DatabaseEntity() {
        
        entityClass = Object.class;
        tableName = "";
        
        primaryKeys = new ArrayList<>();
        foreignKeys = new ArrayList<>();
        allColumns = new ArrayList<>();
        
        allFields = new ArrayList<>();
        columnFieldMapping = new HashMap<>();
        
        fieldTypes = new HashMap<>();
        fieldValues = new HashMap<>();
        references = new HashMap<>();
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }
    
    public void addPrimaryKey(String primaryKey, String field, Class c) {
        primaryKeys.add(primaryKey);
        if(!allColumns.contains(primaryKey)) {
            allColumns.add(primaryKey);
        }
        if(!allFields.contains(field)) {
            allFields.add(field);
        }
        columnFieldMapping.put(primaryKey, field);
        fieldTypes.put(field, c);
    }
    
    public List<String> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<String> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
    
    public void addForeignKey(String foreignKey, String field, Class c, ForeignKeyEntity fke) {
        foreignKeys.add(foreignKey);
        if(!allColumns.contains(foreignKey)) {
            allColumns.add(foreignKey);
        }
        if(!allFields.contains(field)) {
            allFields.add(field);
        }
        columnFieldMapping.put(foreignKey, field);
        references.put(foreignKey, fke);
        fieldTypes.put(field, c);
    }
    
    public List<String> getAllColumns() {
        return allColumns;
    }

    public void setAllColumns(List<String> allColumns) {
        this.allColumns = allColumns;
    }
    
    public void addColumn(String column, String field, Class c) {
        allColumns.add(column);
        allFields.add(field);
        fieldTypes.put(field, c);
        columnFieldMapping.put(column, field);
    }
    
    public List<String> getAllFields() {
        return allFields;
    }

    public void setAllFields(List<String> allFields) {
        this.allFields = allFields;
    }

    public HashMap<String,String> getColumnFieldMapping() {
        return columnFieldMapping;
    }

    public void setColumnFieldMapping(HashMap<String,String> columnFieldMapping) {
        this.columnFieldMapping = columnFieldMapping;
    }
    
    public HashMap<String,String> getFieldColumnMapping() {
        HashMap<String,String> fieldColumnMapping = new HashMap<>();
        for(String s : columnFieldMapping.keySet()) {
            String key = s;
            String value = columnFieldMapping.get(key);
            fieldColumnMapping.put(value, key);
        }
        return fieldColumnMapping;
    }
    
    public HashMap<String,Class> getFieldTypes() {
        return fieldTypes;
    }

    public void setFieldTypes(HashMap<String,Class> fieldTypes) {
        this.fieldTypes = fieldTypes;
    }
    
    public HashMap<String,Object> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(HashMap<String,Object> fieldValues) {
        this.fieldValues = fieldValues;
    }
    
    public HashMap<String,ForeignKeyEntity> getReferences() {
        return references;
    }

    public void setReferences(HashMap<String,ForeignKeyEntity> references) {
        this.references = references;
    }
    
    public class ForeignKeyEntity {
        
        private String referencingTable;
        private String referencingColumn;
        private DatabaseEntity dbe;
        
        public ForeignKeyEntity(String referencingTable, String referencingColumn, DatabaseEntity dbe) {
            this.referencingTable = referencingTable;
            this.referencingColumn = referencingColumn;
            this.dbe = dbe;
        }

        public String getReferencingTable() {
            return referencingTable;
        }

        public void setReferencingTable(String referencingTable) {
            this.referencingTable = referencingTable;
        }

        public String getReferencingColumn() {
            return referencingColumn;
        }

        public void setReferencingColumn(String referencingColumn) {
            this.referencingColumn = referencingColumn;
        }

        public DatabaseEntity getDbe() {
            return dbe;
        }

        public void setDbe(DatabaseEntity dbe) {
            this.dbe = dbe;
        }
        
        
    }
    
//    public HashMap<String,Object> getColumns() {
//        return columns;
//    }
//
//    public void setColumns(HashMap<String,Object> columns) {
//        this.columns = columns;
//    }
//    
//    public void addColumn(String columnName, Object columnValue) {
//        columns.put(columnName, columnValue);
//    }
//
//    public HashMap<String,DatabaseEntity> getReferences() {
//        return references;
//    }
//
//    public void setReferences(HashMap<String,DatabaseEntity> references) {
//        this.references = references;
//    }
//    
//    public void addReference(String foreignKey, DatabaseEntity entity) {
//        references.put(foreignKey, entity);
//    }

}
