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

@Table("TableD")
public class ClassD {
    
    @PrimaryKey("d1T")
    private int d1;
    @Column("d2T")
    private String d2;

    public ClassD() {
    }

    public ClassD(int d1, String d2) {
        this.d1 = d1;
        this.d2 = d2;
    }

    public int getD1() {
        return d1;
    }

    public void setD1(int d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.d1;
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
        final ClassD other = (ClassD) obj;
        if (this.d1 != other.d1) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClassD{" + "d1=" + d1 + ", d2=" + d2 + '}';
    }
    
    
}
