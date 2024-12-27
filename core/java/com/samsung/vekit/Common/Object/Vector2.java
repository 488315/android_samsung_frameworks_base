package com.samsung.vekit.Common.Object;

import java.util.Vector;

/* loaded from: classes6.dex */
public class Vector2<T> {
    private final int X = 0;
    private final int Y = 1;
    Vector<T> data = new Vector<>();

    public Vector2(T x, T y) {
        this.data.add(x);
        this.data.add(y);
    }

    public Vector2(T[] array) {
        this.data.add(array[0]);
        this.data.add(array[1]);
    }

    public Vector2(Vector2<T> array) {
        set(array.getX(), array.getY());
    }

    public T[] toArray() {
        return (T[]) this.data.stream().toArray();
    }

    public void set(T x, T y) {
        this.data.clear();
        this.data.add(x);
        this.data.add(y);
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
}
