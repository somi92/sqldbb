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

@Table("TableCD")
public class ClassCD {
    
    @PrimaryKey("cd1")
    @ForeignKey(column = "cd1", referencingTable = "TableC", referencingColumn = "c1T")
    private ClassC c;
    
    @PrimaryKey("cd2")
    @ForeignKey(column = "cd2", referencingTable = "TableD", referencingColumn = "d1T")
    private ClassD d;
    
    @Column("cd")
    private String cd;

    public ClassCD() {
    }

    public ClassC getC() {
        return c;
    }

    public void setC(ClassC c) {
        this.c = c;
    }

    public ClassD getD() {
        return d;
    }

    public void setD(ClassD d) {
        this.d = d;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.c);
        hash = 37 * hash + Objects.hashCode(this.d);
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
        final ClassCD other = (ClassCD) obj;
        if (!Objects.equals(this.c, other.c)) {
            return false;
        }
        if (!Objects.equals(this.d, other.d)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClassCD{" + "c=" + c + ", d=" + d + ", cd=" + cd + '}';
    }
    
    
}
