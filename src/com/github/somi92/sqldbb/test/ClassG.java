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
import java.util.Objects;

/**
 *
 * @author milos
 */

@Table("TableG")
public class ClassG {
    
    @PrimaryKey("fT")
    @ForeignKey(column = "fT", referencingTable = "TableF", referencingColumn = "f1T", isCollectionItem = true)
    private ClassF f;
    
    @PrimaryKey("g2T")
    private int g2;
    
    @Column("g3T")
    private String g3;

    public ClassG() {
    }

    public ClassG(ClassF parent, int fc1, String fc2) {
        this.f = parent;
        this.g2 = fc1;
        this.g3 = fc2;
    }

    public ClassF getF() {
        return f;
    }

    public void setF(ClassF f) {
        this.f = f;
    }

    public int getG2() {
        return g2;
    }

    public void setG2(int g2) {
        this.g2 = g2;
    }

    public String getG3() {
        return g3;
    }

    public void setG3(String g3) {
        this.g3 = g3;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.f);
        hash = 97 * hash + this.g2;
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
        final ClassG other = (ClassG) obj;
        if (!Objects.equals(this.f, other.f)) {
            return false;
        }
        if (this.g2 != other.g2) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClassG{" + "f={ + f.getF1() + " + " + f.getF2() + }, fc1=" + g2 + ", fc2=" + g3 + '}';
    }

    
}
