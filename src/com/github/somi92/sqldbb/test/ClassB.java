/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.test;

import com.github.somi92.sqldbb.annotations.Column;
import com.github.somi92.sqldbb.annotations.PrimaryKey;
import com.github.somi92.sqldbb.annotations.Table;

/**
 *
 * @author milos
 */

@Table("TableB")
public class ClassB {
    
    @PrimaryKey("b1T")
    private int b1;
    @Column("b2T")
    private String b2;
}
