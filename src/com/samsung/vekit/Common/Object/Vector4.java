package com.samsung.vekit.Common.Object;

import java.util.Vector;

/* loaded from: classes6.dex */
public class Vector4<T> {
    private final int W;
    private final int X;
    private final int Y;
    private final int Z;
    Vector<T> data;

    public Vector4(T t, T t2, T t3, T t4) {
        this.X = 0;
        this.Y = 1;
        this.Z = 2;
        this.W = 3;
        Vector<T> vector = new Vector<>();
        this.data = vector;
        vector.add(t);
        this.data.add(t2);
        this.data.add(t3);
        this.data.add(t4);
    }

    public Vector4(T[] tArr) {
        this.X = 0;
        this.Y = 1;
        this.Z = 2;
        this.W = 3;
        Vector<T> vector = new Vector<>();
        this.data = vector;
        vector.add(tArr[0]);
        this.data.add(tArr[1]);
        this.data.add(tArr[2]);
        this.data.add(tArr[3]);
    }

    public Vector4(Vector4<T> vector4) {
        this.X = 0;
        this.Y = 1;
        this.Z = 2;
        this.W = 3;
        this.data = new Vector<>();
        set(vector4.getX(), vector4.getY(), vector4.getZ(), vector4.getW());
    }

    public T[] toArray() {
        return (T[]) this.data.stream().toArray();
    }

    public void set(T t, T t2, T t3, T t4) {
        this.data.clear();
        this.data.add(t);
        this.data.add(t2);
        this.data.add(t3);
        this.data.add(t4);
    }

    public T getX() {
        return this.data.get(0);
    }

    public void setX(T t) {
        this.data.set(0, t);
    }

    public T getY() {
        return this.data.get(1);
    }

    public void setY(T t) {
        this.data.set(1, t);
    }

    public T getZ() {
        return this.data.get(2);
    }

    public void setZ(T t) {
        this.data.set(2, t);
    }

    public T getW() {
        return this.data.get(3);
    }

    public void setW(T t) {
        this.data.set(3, t);
    }
}
