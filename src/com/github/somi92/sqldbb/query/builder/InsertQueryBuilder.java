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
public class InsertQueryBuilder extends AbstractQueryBuilder {
    
    public InsertQueryBuilder() {
        query = new Query();
    }

    @Override
    public void setKeywords() {
        String[] keywords = new String[3];
        keywords[0] = "INSERT";
        keywords[1] = "INTO";
        keywords[2] = "VALUES";
        query.setKeywords(keywords);
    }

    @Override
    public void setQueryFormat() {
        String format = "%s %s %s %s %s %s;";
        query.setFormat(format);
    }

    @Override
    public void formatQuery(DatabaseEntity dbe) {
        String tableName = dbe.getTableName();
        
        List<String> columns = dbe.getAllColumns();
        String columnsForQuery = "(";
        String valuesForQuery = "(";
        for(int i=0; i<columns.size(); i++) {
            if(i==(columns.size()-1)) {
                columnsForQuery += columns.get(i)+")";
                valuesForQuery += "?)";
            } else {
                columnsForQuery += columns.get(i)+", ";
                valuesForQuery += "?, ";
            }
        }
        
        String queryValue = String.format(query.getFormat(),
                query.getKeywords()[0],
                query.getKeywords()[1],
                tableName,
                columnsForQuery,
                query.getKeywords()[2],
                valuesForQuery);
        query.setQuery(queryValue);
    }

    @Override
    public void fillPreparedStatement(PreparedStatement ps, DatabaseEntity dbe) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
