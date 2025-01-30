package com.samsung.vekit.Common.Object;

import java.util.Vector;

/* loaded from: classes6.dex */
public class Rect<T> {
  private final int HEIGHT;
  private final int WIDTH;

  /* renamed from: X */
  private final int f3104X;

  /* renamed from: Y */
  private final int f3105Y;
  Vector<T> data;

  public Rect(T x, T y, T width, T height) {
    this.f3104X = 0;
    this.f3105Y = 1;
    this.WIDTH = 2;
    this.HEIGHT = 3;
    Vector<T> vector = new Vector<>();
    this.data = vector;
    vector.add(x);
    this.data.add(y);
    this.data.add(width);
    this.data.add(height);
  }

  public Rect(T[] array) {
    this.f3104X = 0;
    this.f3105Y = 1;
    this.WIDTH = 2;
    this.HEIGHT = 3;
    Vector<T> vector = new Vector<>();
    this.data = vector;
    vector.add(array[0]);
    this.data.add(array[1]);
    this.data.add(array[2]);
    this.data.add(array[3]);
  }

  public Rect(Rect<T> array) {
    this.f3104X = 0;
    this.f3105Y = 1;
    this.WIDTH = 2;
    this.HEIGHT = 3;
    this.data = new Vector<>();
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
