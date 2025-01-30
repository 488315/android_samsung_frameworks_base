package com.android.server.am;

import android.util.ArrayMap;
import android.util.SparseArray;

/* loaded from: classes.dex */
public class UidProcessMap {
  public final SparseArray mMap = new SparseArray();

  public Object get(int i, String str) {
    ArrayMap arrayMap = (ArrayMap) this.mMap.get(i);
    if (arrayMap == null) {
      return null;
    }
    return arrayMap.get(str);
  }

  public Object put(int i, String str, Object obj) {
    ArrayMap arrayMap = (ArrayMap) this.mMap.get(i);
    if (arrayMap == null) {
      arrayMap = new ArrayMap(2);
      this.mMap.put(i, arrayMap);
    }
    arrayMap.put(str, obj);
    return obj;
  }

  public Object remove(int i, String str) {
    ArrayMap arrayMap;
    int indexOfKey = this.mMap.indexOfKey(i);
    if (indexOfKey < 0 || (arrayMap = (ArrayMap) this.mMap.valueAt(indexOfKey)) == null) {
      return null;
    }
    Object remove = arrayMap.remove(str);
    if (arrayMap.isEmpty()) {
      this.mMap.removeAt(indexOfKey);
    }
    return remove;
  }

  public SparseArray getMap() {
    return this.mMap;
  }

  public void clear() {
    this.mMap.clear();
  }
}
