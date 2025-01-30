package com.samsung.vekit.Common.Object;

import java.util.Vector;

/* loaded from: classes6.dex */
public class Vector2<T> {

  /* renamed from: X */
  private final int f3106X;

  /* renamed from: Y */
  private final int f3107Y;
  Vector<T> data;

  public Vector2(T x, T y) {
    this.f3106X = 0;
    this.f3107Y = 1;
    Vector<T> vector = new Vector<>();
    this.data = vector;
    vector.add(x);
    this.data.add(y);
  }

  public Vector2(T[] array) {
    this.f3106X = 0;
    this.f3107Y = 1;
    Vector<T> vector = new Vector<>();
    this.data = vector;
    vector.add(array[0]);
    this.data.add(array[1]);
  }

  public Vector2(Vector2<T> array) {
    this.f3106X = 0;
    this.f3107Y = 1;
    this.data = new Vector<>();
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
