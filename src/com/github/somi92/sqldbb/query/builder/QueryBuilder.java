/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.query.builder;

import com.github.somi92.sqldbb.entity.DatabaseEntity;
import com.github.somi92.sqldbb.query.Query;
import java.sql.PreparedStatement;

/**
 *
 * @author milos
 */
public class QueryBuilder {
    
    private IQueryBuilder builder;
    
    public QueryBuilder(IQueryBuilder builder) {
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
    
    public void prepareStatement(PreparedStatement ps, DatabaseEntity dbe) {
        builder.prepareStatement(ps, dbe);
    }
}
