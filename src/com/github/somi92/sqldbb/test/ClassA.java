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
    @PrimaryKey("a11T")
    private int a11;
    @Column("a2T")
    private String a2;
    @ForeignKey(name = "bT", referencingTable = "TableB", referencingColumn = "b1T")
    private ClassB b;
    
    public ClassA() {
    }

    public ClassA(int a1, int a11, String a2, ClassB b) {
        this.a1 = a1;
        this.a11 = a11;
        this.a2 = a2;
        this.b = b;
    }

    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public int getA11() {
        return a11;
    }

    public void setA11(int a11) {
        this.a11 = a11;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public ClassB getB() {
        return b;
    }

    public void setB(ClassB b) {
        this.b = b;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.a1;
        hash = 23 * hash + this.a11;
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
        final ClassA other = (ClassA) obj;
        if (this.a1 != other.a1) {
            return false;
        }
        if (this.a11 != other.a11) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClassA{" + "a1=" + a1 + ", a11=" + a11 + ", a2=" + a2 + ", b=" + b + '}';
    }
    
    
}
