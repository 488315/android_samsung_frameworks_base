package com.samsung.vekit.Common.Object;

import java.util.Vector;

/* loaded from: classes6.dex */
public class Vector3<T> {

  /* renamed from: X */
  private final int f3108X;

  /* renamed from: Y */
  private final int f3109Y;

  /* renamed from: Z */
  private final int f3110Z;
  Vector<T> data;

  public Vector3(T x, T y, T z) {
    this.f3108X = 0;
    this.f3109Y = 1;
    this.f3110Z = 2;
    Vector<T> vector = new Vector<>();
    this.data = vector;
    vector.add(x);
    this.data.add(y);
    this.data.add(z);
  }

  public Vector3(T[] array) {
    this.f3108X = 0;
    this.f3109Y = 1;
    this.f3110Z = 2;
    Vector<T> vector = new Vector<>();
    this.data = vector;
    vector.add(array[0]);
    this.data.add(array[1]);
    this.data.add(array[2]);
  }

  public Vector3(Vector3<T> array) {
    this.f3108X = 0;
    this.f3109Y = 1;
    this.f3110Z = 2;
    this.data = new Vector<>();
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
