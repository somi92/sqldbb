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
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author milos
 */
public class SelectQueryBuilder extends AbstractQueryBuilder {
    
    private boolean usePKCondition;
    private List<String> searchCriteria;
    
    public SelectQueryBuilder(boolean usePKCondition) {
        query = new Query();
        this.usePKCondition = usePKCondition;
        searchCriteria = null;
    }

    public SelectQueryBuilder(List<String> searchCriteria) {
        query = new Query();
        usePKCondition = false;
        this.searchCriteria = searchCriteria;
    }
    
    @Override
    public void setKeywords() {
        String[] keywords = new String[3];
        keywords[0] = "SELECT";
        keywords[1] = "FROM";
        if(usePKCondition || searchCriteria != null) {
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
        
        if(searchCriteria != null) {
            String[] criteriaKeys = searchCriteria.
                    toArray(new String[searchCriteria.size()]);
            for(int i=0; i<criteriaKeys.length; i++) {
                String column = dbe.getFieldColumnMapping().get(criteriaKeys[i]);
                if(i==(criteriaKeys.length-1)) {
                    condition += table+"."+column+"=?";
                } else {
                    condition += table+"."+column+"=? AND ";
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
    public void fillPreparedStatement(PreparedStatement ps, DatabaseEntity dbe) throws SQLException {
        if(usePKCondition) {
            List<String> primaryKeys = dbe.getPrimaryKeys();
            for(int i=0; i<primaryKeys.size(); i++) {    
                setPSValue(dbe, primaryKeys.get(i), i, ps);    
            }
        }
        
        if(searchCriteria != null) {
            String[] criteriaKeys = searchCriteria.
                    toArray(new String[searchCriteria.size()]);
            for(int i=0; i<criteriaKeys.length; i++) {
                String column = dbe.getFieldColumnMapping().get(criteriaKeys[i]);
                setPSValue(dbe, column, i, ps);
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
