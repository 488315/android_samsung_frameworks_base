package com.android.server.hdmi;

import android.util.SparseArray;

/* loaded from: classes2.dex */
public final class UnmodifiableSparseArray {
  public final SparseArray mArray;

  public UnmodifiableSparseArray(SparseArray sparseArray) {
    this.mArray = sparseArray;
  }

  public Object get(int i) {
    return this.mArray.get(i);
  }

  public Object get(int i, Object obj) {
    return this.mArray.get(i, obj);
  }

  public String toString() {
    return this.mArray.toString();
  }
}
