package com.android.server.permission.jarjar.kotlin;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import java.io.Serializable;

/* compiled from: Tuples.kt */
/* loaded from: classes2.dex */
public final class Pair implements Serializable {
  private final Object first;
  private final Object second;

  public final Object component1() {
    return this.first;
  }

  public final Object component2() {
    return this.second;
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Pair)) {
      return false;
    }
    Pair pair = (Pair) obj;
    return Intrinsics.areEqual(this.first, pair.first)
        && Intrinsics.areEqual(this.second, pair.second);
  }

  public int hashCode() {
    Object obj = this.first;
    int hashCode = (obj == null ? 0 : obj.hashCode()) * 31;
    Object obj2 = this.second;
    return hashCode + (obj2 != null ? obj2.hashCode() : 0);
  }

  public Pair(Object obj, Object obj2) {
    this.first = obj;
    this.second = obj2;
  }

  public String toString() {
    return '(' + this.first + ", " + this.second + ')';
  }
}
