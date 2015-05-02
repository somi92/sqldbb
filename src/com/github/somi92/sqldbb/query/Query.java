/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.query;

/**
 *
 * @author milos
 */
public class Query {
    
    private String[] keywords;
//    private String condition;
    private String format;
    private String query;
    
    public Query() {
        
    }
    
    public Query(String[] keywords, String condition, String format) {
        this.keywords = keywords;
//        this.condition = condition;
        this.format = format;
        this.query = "";
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

//    public String getCondition() {
//        return condition;
//    }
//
//    public void setCondition(String condition) {
//        this.condition = condition;
//    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    @Override
    public String toString() {
        return query;
    }    
}
