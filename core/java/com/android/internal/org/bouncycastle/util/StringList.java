package com.android.internal.org.bouncycastle.util;

/* loaded from: classes5.dex */
public interface StringList extends Iterable<String> {
  boolean add(String str);

  String get(int i);

  int size();

  String[] toStringArray();

  String[] toStringArray(int i, int i2);
}
