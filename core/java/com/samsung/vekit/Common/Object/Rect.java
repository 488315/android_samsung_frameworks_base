package com.samsung.vekit.Common.Object;

import java.util.Vector;

public class Rect<T> {
    private final int X = 0;
    private final int Y = 1;
    private final int WIDTH = 2;
    private final int HEIGHT = 3;
    Vector<T> data = new Vector<>();

    public Rect(T x, T y, T width, T height) {
        this.data.add(x);
        this.data.add(y);
        this.data.add(width);
        this.data.add(height);
    }

    public Rect(T[] array) {
        this.data.add(array[0]);
        this.data.add(array[1]);
        this.data.add(array[2]);
        this.data.add(array[3]);
    }

    public Rect(Rect<T> array) {
        set(array.getX(), array.getY(), array.getWidth(), array.getHeight());
    }

    public T[] toArray() {
        return (T[]) this.data.stream().toArray();
    }

    public void set(T x, T y, T width, T height) {
        this.data.clear();
        this.data.add(x);
        this.data.add(y);
        this.data.add(width);
        this.data.add(height);
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

    public T getWidth() {
        return this.data.get(2);
    }

    public void setWidth(T width) {
        this.data.set(2, width);
    }

    public T getHeight() {
        return this.data.get(3);
    }

    public void setHeight(T height) {
        this.data.set(3, height);
    }
}
