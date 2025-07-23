package com.samsung.vekit.Common.Object;

import java.util.Vector;

/* loaded from: classes6.dex */
public class Vector2<T> {
    private final int X;
    private final int Y;
    Vector<T> data;

    public Vector2(T t, T t2) {
        this.X = 0;
        this.Y = 1;
        Vector<T> vector = new Vector<>();
        this.data = vector;
        vector.add(t);
        this.data.add(t2);
    }

    public Vector2(T[] tArr) {
        this.X = 0;
        this.Y = 1;
        Vector<T> vector = new Vector<>();
        this.data = vector;
        vector.add(tArr[0]);
        this.data.add(tArr[1]);
    }

    public Vector2(Vector2<T> vector2) {
        this.X = 0;
        this.Y = 1;
        this.data = new Vector<>();
        set(vector2.getX(), vector2.getY());
    }

    public T[] toArray() {
        return (T[]) this.data.stream().toArray();
    }

    public void set(T t, T t2) {
        this.data.clear();
        this.data.add(t);
        this.data.add(t2);
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
}
