/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.query.builder;

import com.github.somi92.sqldbb.entity.DatabaseEntity;
import com.github.somi92.sqldbb.query.Query;
import java.util.List;

/**
 *
 * @author milos
 */
public class SelectQueryBuilder implements IQueryBuilder {
    
    private Query query;
    
    public SelectQueryBuilder() {
        query = new Query();
    }

    @Override
    public void setKeywords() {
        String[] keywords = new String[3];
        keywords[0] = "SELECT";
        keywords[1] = "FROM";
        keywords[2] = "WHERE";
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
        List<String> columns = dbe.getAllColumns();
        String columnsForQuery = "";
        for(int i=0; i<columns.size(); i++) {
            if(i==(columns.size()-1)) {
                columnsForQuery += table+"."+columns.get(i);
            } else {
                columnsForQuery += table+"."+columns.get(i)+", ";
            }
        }
        List<String> primaryKeys = dbe.getPrimaryKeys();
        String condition = "";
        for(int i=0; i<primaryKeys.size(); i++) {
            if(i==(primaryKeys.size()-1)) {
                condition += table+"."+primaryKeys.get(i)+"=?";
            } else {
                condition += table+"."+primaryKeys.get(i)+"=? AND ";
            }
        }
        String queryValue = String.format(query.getFormat(),
                query.getKeywords()[0],
                columnsForQuery,
                query.getKeywords()[1],
                table,
                query.getKeywords()[2],
                condition);
        query.setQuery(queryValue);
    }

    @Override
    public Query getQuery() {
        return query;
    }
}
