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

@Table("TableB")
public class ClassB {
    
    @PrimaryKey("b1T")
    private int b1;
    
    @Column("b2T")
    private String b2;
    
    @ForeignKey(column = "cT", referencingTable = "TableC", referencingColumn = "c1T")
    private ClassC c;

    public ClassB() {
    }

    public ClassB(int b1, String b2, ClassC c) {
        this.b1 = b1;
        this.b2 = b2;
        this.c = c;
    }

    public int getB1() {
        return b1;
    }

    public void setB1(int b1) {
        this.b1 = b1;
    }

    public String getB2() {
        return b2;
    }

    public void setB2(String b2) {
        this.b2 = b2;
    }

    public ClassC getC() {
        return c;
    }

    public void setC(ClassC c) {
        this.c = c;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.b1;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClassB other = (ClassB) obj;
        if (this.b1 != other.b1) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClassB{" + "b1=" + b1 + ", b2=" + b2 + ", c=" + c + '}';
    }
}
