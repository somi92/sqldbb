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
import java.util.List;

/**
 *
 * @author milos
 */
public class UpdateQueryBuilder implements IQueryBuilder {
    
    private Query query;
    
    public UpdateQueryBuilder() {
        query = new Query();
    }

    @Override
    public void setKeywords() {
        String[] keywords = new String[3];
        keywords[0] = "UPDATE";
        keywords[1] = "SET";
        keywords[2] = "WHERE";
        query.setKeywords(keywords);
    }

    @Override
    public void setQueryFormat() {
        String queryFormat = "%s %s %s %s %s %s;";
        query.setFormat(queryFormat);
    }

    @Override
    public void formatQuery(DatabaseEntity dbe) {
        String tableName = dbe.getTableName();
        
        List<String> columns = dbe.getAllColumns();
        String columnsForQuery = "";
        for(int i=0; i<columns.size(); i++) {
            if(i==(columns.size()-1)) {
                columnsForQuery += columns.get(i)+"=?";
            } else {
                columnsForQuery += columns.get(i)+"=?, ";
            }
        }
        
        List<String> primaryKeys = dbe.getPrimaryKeys();
        String condition = "";
        for(int i=0; i<primaryKeys.size(); i++) {
            if(i==(primaryKeys.size()-1)) {
                condition += tableName+"."+primaryKeys.get(i)+"=?";
            } else {
                condition += tableName+"."+primaryKeys.get(i)+"=? AND ";
            }
        }
        
        String queryValue = String.format(query.getFormat(),
                query.getKeywords()[0],
                tableName,
                query.getKeywords()[1],
                columnsForQuery,
                query.getKeywords()[2],
                condition);
        query.setQuery(queryValue); 
    }

    @Override
    public Query getQuery() {
        return query;
    }
    
    @Override
    public void fillPreparedStatement(PreparedStatement ps, DatabaseEntity dbe) throws SQLException {
        List<String> columns = dbe.getAllColumns();
        int counter = 0;
        for(int i=0; i<columns.size(); i++) {
            setPSValue(dbe, columns.get(i), i, ps);
            counter++;
        }
        
        List<String> primaryKeys = dbe.getPrimaryKeys();
        for(int i=counter; i<(primaryKeys.size()+counter); i++) {
            setPSValue(dbe, primaryKeys.get(i-counter), i, ps);
        }
    }
    
    private void setPSValue(DatabaseEntity dbe, String column, int i, PreparedStatement ps) throws SQLException {
        
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
}
