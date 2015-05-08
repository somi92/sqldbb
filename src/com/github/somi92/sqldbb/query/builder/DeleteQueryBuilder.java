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
public class DeleteQueryBuilder extends AbstractQueryBuilder {
    
    private boolean resetTable;
    
    public DeleteQueryBuilder(boolean resetTable) {
        query = new Query();
        this.resetTable = resetTable;
    }

    @Override
    public void setKeywords() {
        String[] keywords = new String[3];
        if(resetTable) {
            keywords[0] = "TRUNCATE";
            keywords[1] = "";
            keywords[2] = "";
        } else {
            keywords[0] = "DELETE";
            keywords[1] = "FROM";
            keywords[2] = "WHERE";
        }
        query.setKeywords(keywords);
    }

    @Override
    public void setQueryFormat() {
        String format = "";
        if(resetTable) {
            format = "%s %s;";
        } else {
            format = "%s %s %s %s %s;";
        }
        query.setFormat(format);
    }

    @Override
    public void formatQuery(DatabaseEntity dbe) {
        String tableName = dbe.getTableName();
        
        String condition = "";
        if(!resetTable) {
            List<String> primaryKeys = dbe.getPrimaryKeys();
            for(int i=0; i<primaryKeys.size(); i++) {
                if(i==(primaryKeys.size()-1)) {
                    condition += tableName+"."+primaryKeys.get(i)+"=?";
                } else {
                    condition += tableName+"."+primaryKeys.get(i)+"=? AND ";
                }
            }
        }
        
        String queryValue = "";
        if(resetTable) {
            queryValue = String.format(query.getFormat(), 
                query.getKeywords()[0],
                tableName);
        } else {
            queryValue = String.format(query.getFormat(),
                query.getKeywords()[0],
                query.getKeywords()[1],
                tableName,
                query.getKeywords()[2],
                condition);
        }
        query.setQuery(queryValue);
    }
    
    @Override
    public void fillPreparedStatement(PreparedStatement ps, DatabaseEntity dbe) throws SQLException {
        List<String> primaryKeys = dbe.getPrimaryKeys();
        for(int i=0; i<primaryKeys.size(); i++) {
            setPSValue(dbe, primaryKeys.get(i), i, ps);
        }
    }
}
