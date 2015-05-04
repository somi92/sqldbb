/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.query.builder;

import com.github.somi92.sqldbb.entity.DatabaseEntity;
import com.github.somi92.sqldbb.query.Query;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author milos
 */
public class SelectQueryBuilder implements IQueryBuilder {
    
    private Query query;
    private boolean usePKCondition;
    
    public SelectQueryBuilder(boolean usePKCondition) {
        query = new Query();
        this.usePKCondition = usePKCondition;
    }

    @Override
    public void setKeywords() {
        String[] keywords = new String[3];
        keywords[0] = "SELECT";
        keywords[1] = "FROM";
        if(usePKCondition) {
            keywords[2] = "WHERE";
        } else {
            keywords[2] = "";
        }
        query.setKeywords(keywords);
    }

    @Override
    public void setQueryFormat() {
        String selectFormat = "%s %s %s %s %s %s;";
        query.setFormat(selectFormat);
    }

    @Override
    public void formatQuery(DatabaseEntity dbe) {
        String table = dbe.getTableName();
        
        String columnsForQuery = getColumnsForQuery(dbe);
        
        List<String> primaryKeys = dbe.getPrimaryKeys();
        String condition = "";
        if(usePKCondition) {
            for(int i=0; i<primaryKeys.size(); i++) {
            if(i==(primaryKeys.size()-1)) {
                condition += table+"."+primaryKeys.get(i)+"=?";
            } else {
                condition += table+"."+primaryKeys.get(i)+"=? AND ";
                }
            }
        }
        
        String queryValue = String.format(query.getFormat(),
                query.getKeywords()[0],
                columnsForQuery,
                query.getKeywords()[1],
                table+getSourceForQuery(dbe),
                query.getKeywords()[2],
                condition);
        query.setQuery(queryValue);
    }

    @Override
    public Query getQuery() {
        return query;
    }
    
    @Override
    public void prepareStatement(PreparedStatement ps, DatabaseEntity dbe) throws SQLException {
        if(usePKCondition) {
            List<String> primaryKeys = dbe.getPrimaryKeys();
            for(int i=0; i<primaryKeys.size(); i++) {
                String field = dbe.getColumnFieldMapping().get(primaryKeys.get(i));
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
                    default:
                        ps.setString(i+1, (String) fieldValue);
                }
            }
        }
    }
    
    private String getColumnsForQuery(DatabaseEntity dbe) {
        String table = dbe.getTableName();
        List<String> columns = dbe.getAllColumns();
        List<String> foreingKeys = dbe.getForeignKeys();
        String columnsForQuery = "";
        for(int i=0; i<columns.size(); i++) {
            if(foreingKeys.contains(columns.get(i))) {
                continue;
            }
            if(i==(columns.size()-1)) {
                columnsForQuery += table+"."+columns.get(i);
            } else {
                columnsForQuery += table+"."+columns.get(i)+", ";
            }
        }
        if(columnsForQuery.endsWith(", ")) {
            columnsForQuery = columnsForQuery.substring(0, columnsForQuery.length()-2);
        }
        for(String s : foreingKeys) {
            DatabaseEntity.ForeignKeyEntity fke = dbe.getReferences().get(s);
            DatabaseEntity foreignDbe = fke.getDbe();
            columnsForQuery += ", "+getColumnsForQuery(foreignDbe);
        }
        return columnsForQuery;
    }
    
    private String getSourceForQuery(DatabaseEntity dbe) {
        String dataSource = "";
        List<String> foreignKeys = dbe.getForeignKeys();
        for(String s : foreignKeys) {
            DatabaseEntity.ForeignKeyEntity fke = dbe.getReferences().get(s);
            String referencingTable = fke.getReferencingTable();
            String referencingColumn = fke.getReferencingColumn();
            String join = " JOIN "+referencingTable+" ON ("+
                    dbe.getTableName()+"."+s+"="+referencingTable+"."+referencingColumn+")";
            dataSource += join + getSourceForQuery(fke.getDbe());
        }
        return dataSource;
    }
}
