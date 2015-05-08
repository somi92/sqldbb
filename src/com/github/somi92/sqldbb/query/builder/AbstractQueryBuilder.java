/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.query.builder;

import com.github.somi92.sqldbb.entity.DatabaseEntity;
import com.github.somi92.sqldbb.entity.processor.EntityProcessor;
import com.github.somi92.sqldbb.query.Query;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author milos
 */
public abstract class AbstractQueryBuilder {
    
    protected Query query;
    
    protected void setPSValue(DatabaseEntity dbe, String column, int i, PreparedStatement ps) throws SQLException {
        
        String field = dbe.getColumnFieldMapping().get(column);
        Object fieldValue = dbe.getFieldValues().get(field);
        Class fieldType = dbe.getFieldTypes().get(field);
        
        switch(fieldType.getSimpleName()) {
            case "int":
                ps.setInt(i+1, (int) fieldValue);
                break;
            case "String":
                ps.setString(i+1, (String) fieldValue);
                break;
            case "long":
                ps.setLong(i+1, (long) fieldValue);
                break;
            case "float":
                ps.setFloat(i+1, (float) fieldValue);
                break;
            case "double":
                ps.setDouble(i+1, (double) fieldValue);
                break;
            case "boolean":
                ps.setBoolean(i+1, (boolean) fieldValue);
                break;
            case "Date":
                ps.setDate(i+1, (java.sql.Date) fieldValue);
                break;
            default: {
                DatabaseEntity.ForeignKeyEntity fke = dbe.getReferences().get(column);
                DatabaseEntity newDbe = fke.getDbe();
                String referencingColumn = fke.getReferencingColumn();
                EntityProcessor.setEntityFieldValues(newDbe, fieldValue);
                setPSValue(newDbe, referencingColumn, i, ps);
            }
        }
    }
    
    public abstract void setKeywords();
    public abstract void setQueryFormat();
    public abstract void formatQuery(DatabaseEntity dbe);
    public abstract void fillPreparedStatement(PreparedStatement ps, DatabaseEntity dbe) throws SQLException;
    
    public Query getQuery() {
        return query;
    }
}
