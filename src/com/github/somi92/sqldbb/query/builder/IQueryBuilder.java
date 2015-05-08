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
public interface IQueryBuilder {
    
    public void setKeywords();
    public void setQueryFormat();
    public void formatQuery(DatabaseEntity dbe);
    public Query getQuery();
    public void fillPreparedStatement(PreparedStatement ps, DatabaseEntity dbe) throws SQLException;
}
