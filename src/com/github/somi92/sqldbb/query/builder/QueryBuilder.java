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

/**
 *
 * @author milos
 */
public class QueryBuilder {
    
    private AbstractQueryBuilder builder;
    
    public QueryBuilder(AbstractQueryBuilder builder) {
        this.builder = builder;
    }
    
    public void buildQuery(DatabaseEntity dbe) {
        builder.setKeywords();
        builder.setQueryFormat();
        builder.formatQuery(dbe);
    }
    
    public Query getQuery() {
        return builder.getQuery();
    }
    
    public void fillPreparedStatement(PreparedStatement ps, DatabaseEntity dbe) throws SQLException {
        builder.fillPreparedStatement(ps, dbe);
    }
}
