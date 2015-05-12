/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.test;

import com.github.somi92.sqldbb.annotations.Column;
import com.github.somi92.sqldbb.annotations.Collection;
import com.github.somi92.sqldbb.annotations.PrimaryKey;
import com.github.somi92.sqldbb.annotations.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milos
 */

@Table("TableF")
public class ClassF {
    
    @PrimaryKey("f1T")
    private long f1;
    
    @Column("f2T")
    private String f2;
    
    @Collection(childEntityClass = ClassG.class, referencingField = "f")
    private List<ClassG> fchild;

    public ClassF() {
        fchild = new ArrayList<>();
    }

    public ClassF(long f1, String f2, List<ClassG> fchild) {
        this();
        this.f1 = f1;
        this.f2 = f2;
        this.fchild = fchild;
    }

    public long getF1() {
        return f1;
    }

    public void setF1(long f1) {
        this.f1 = f1;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public List<ClassG> getFchild() {
        return fchild;
    }

    public void setFchild(List<ClassG> fchild) {
        this.fchild = fchild;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.f1 ^ (this.f1 >>> 32));
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
        final ClassF other = (ClassF) obj;
        if (this.f1 != other.f1) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClassF{" + "f1=" + f1 + ", f2=" + f2 + ", fchild=" + fchild + '}';
    }

    
}
