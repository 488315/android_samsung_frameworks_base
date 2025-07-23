package com.samsung.vekit.Common.Object;

import java.util.Vector;

/* loaded from: classes6.dex */
public class Vector3<T> {
    private final int X;
    private final int Y;
    private final int Z;
    Vector<T> data;

    public Vector3(T t, T t2, T t3) {
        this.X = 0;
        this.Y = 1;
        this.Z = 2;
        Vector<T> vector = new Vector<>();
        this.data = vector;
        vector.add(t);
        this.data.add(t2);
        this.data.add(t3);
    }

    public Vector3(T[] tArr) {
        this.X = 0;
        this.Y = 1;
        this.Z = 2;
        Vector<T> vector = new Vector<>();
        this.data = vector;
        vector.add(tArr[0]);
        this.data.add(tArr[1]);
        this.data.add(tArr[2]);
    }

    public Vector3(Vector3<T> vector3) {
        this.X = 0;
        this.Y = 1;
        this.Z = 2;
        this.data = new Vector<>();
        set(vector3.getX(), vector3.getY(), vector3.getZ());
    }

    public T[] toArray() {
        return (T[]) this.data.stream().toArray();
    }

    public void set(T t, T t2, T t3) {
        this.data.clear();
        this.data.add(t);
        this.data.add(t2);
        this.data.add(t3);
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
}
