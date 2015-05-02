/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.test;

import com.github.somi92.sqldbb.annotations.Column;
import com.github.somi92.sqldbb.annotations.ForeignKey;
import com.github.somi92.sqldbb.annotations.PrimaryKey;
import com.github.somi92.sqldbb.annotations.Table;

/**
 *
 * @author milos
 */

@Table("TableA")
public class ClassA {
    
    @PrimaryKey("a1T")
    private int a1;
    @Column("a2T")
    private String a2;
    @ForeignKey(name = "bT", referencingTable = "TableB", referencingColumn = "b1T")
    private ClassB b;
    
    public ClassA() {
    }
}
