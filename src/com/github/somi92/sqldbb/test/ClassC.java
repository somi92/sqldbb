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

@Table("TableC")
public class ClassC {
    
    @PrimaryKey("c1T")
    private int c1;
    @Column("c2T")
    private int c2;

    public ClassC() {
    }

    public ClassC(int c1, int c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public int getC1() {
        return c1;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }

    public int getC2() {
        return c2;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.c1;
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
        final ClassC other = (ClassC) obj;
        if (this.c1 != other.c1) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClassC{" + "c1=" + c1 + ", c2=" + c2 + '}';
    }
    
    
}
