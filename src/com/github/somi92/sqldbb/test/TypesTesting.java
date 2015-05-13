/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.somi92.sqldbb.test;

import com.github.somi92.sqldbb.annotations.Column;
import com.github.somi92.sqldbb.annotations.PrimaryKey;
import com.github.somi92.sqldbb.annotations.Table;
import java.util.Date;

/**
 *
 * @author milos
 */

@Table("Types")
public class TypesTesting {
    
    @PrimaryKey("l")
    private long l;
    @Column("b")
    private byte b;
    @Column("s")
    private short s;
    @Column("i")
    private int i;
    @Column("str")
    private String str;
    @Column("f")
    private float f;
    @Column("d")
    private double d;
    @Column("bool")
    private boolean bool;
    @Column("c")
    private char c;
    @Column("date")
    private Date date;

    public TypesTesting() {
    }

    public long getL() {
        return l;
    }

    public void setL(long l) {
        this.l = l;
    }

    public byte getB() {
        return b;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public short getS() {
        return s;
    }

    public void setS(short s) {
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TypesTesting{" + "l=" + l + ", b=" + b + ", s=" + s + ", i=" + i + ", str=" + str + ", f=" + f + ", d=" + d + ", bool=" + bool + ", c=" + c + ", date=" + date + '}';
    }
    
    
}
