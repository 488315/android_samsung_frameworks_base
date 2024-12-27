package com.samsung.vekit.Common.Object;

import java.util.Vector;

public class Vector3<T> {
    private final int X = 0;
    private final int Y = 1;
    private final int Z = 2;
    Vector<T> data = new Vector<>();

    public Vector3(T x, T y, T z) {
        this.data.add(x);
        this.data.add(y);
        this.data.add(z);
    }

    public Vector3(T[] array) {
        this.data.add(array[0]);
        this.data.add(array[1]);
        this.data.add(array[2]);
    }

    public Vector3(Vector3<T> array) {
        set(array.getX(), array.getY(), array.getZ());
    }

    public T[] toArray() {
        return (T[]) this.data.stream().toArray();
    }

    public void set(T x, T y, T z) {
        this.data.clear();
        this.data.add(x);
        this.data.add(y);
        this.data.add(z);
    }

    public T getX() {
        return this.data.get(0);
    }

    public void setX(T x) {
        this.data.set(0, x);
    }

    public T getY() {
        return this.data.get(1);
    }

    public void setY(T y) {
        this.data.set(1, y);
    }

    public T getZ() {
        return this.data.get(2);
    }

    public void setZ(T z) {
        this.data.set(2, z);
    }
}
