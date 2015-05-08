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
public class UpdateQueryBuilder extends AbstractQueryBuilder {
    
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
}
