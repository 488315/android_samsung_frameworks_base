package com.samsung.vekit.Common.Object;

import java.util.Vector;

/* loaded from: classes6.dex */
public class Rect<T> {
    private final int HEIGHT;
    private final int WIDTH;
    private final int X;
    private final int Y;
    Vector<T> data;

    public Rect(T t, T t2, T t3, T t4) {
        this.X = 0;
        this.Y = 1;
        this.WIDTH = 2;
        this.HEIGHT = 3;
        Vector<T> vector = new Vector<>();
        this.data = vector;
        vector.add(t);
        this.data.add(t2);
        this.data.add(t3);
        this.data.add(t4);
    }

    public Rect(T[] tArr) {
        this.X = 0;
        this.Y = 1;
        this.WIDTH = 2;
        this.HEIGHT = 3;
        Vector<T> vector = new Vector<>();
        this.data = vector;
        vector.add(tArr[0]);
        this.data.add(tArr[1]);
        this.data.add(tArr[2]);
        this.data.add(tArr[3]);
    }

    public Rect(Rect<T> rect) {
        this.X = 0;
        this.Y = 1;
        this.WIDTH = 2;
        this.HEIGHT = 3;
        this.data = new Vector<>();
        set(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
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

    public T getWidth() {
        return this.data.get(2);
    }

    public void setWidth(T t) {
        this.data.set(2, t);
    }

    public T getHeight() {
        return this.data.get(3);
    }

    public void setHeight(T t) {
        this.data.set(3, t);
    }
}
