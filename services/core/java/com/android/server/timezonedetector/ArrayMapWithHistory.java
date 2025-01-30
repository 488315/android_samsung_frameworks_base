package com.android.server.timezonedetector;

import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;

/* loaded from: classes3.dex */
public final class ArrayMapWithHistory {
  public ArrayMap mMap;
  public final int mMaxHistorySize;

  public ArrayMapWithHistory(int i) {
    if (i < 1) {
      throw new IllegalArgumentException("maxHistorySize < 1: " + i);
    }
    this.mMaxHistorySize = i;
  }

  public Object put(Object obj, Object obj2) {
    if (this.mMap == null) {
      this.mMap = new ArrayMap();
    }
    ReferenceWithHistory referenceWithHistory = (ReferenceWithHistory) this.mMap.get(obj);
    if (referenceWithHistory == null) {
      referenceWithHistory = new ReferenceWithHistory(this.mMaxHistorySize);
      this.mMap.put(obj, referenceWithHistory);
    } else if (referenceWithHistory.getHistoryCount() == 0) {
      Log.w("ArrayMapWithHistory", "History for \"" + obj + "\" was unexpectedly empty");
    }
    return referenceWithHistory.set(obj2);
  }

  public Object get(Object obj) {
    ReferenceWithHistory referenceWithHistory;
    ArrayMap arrayMap = this.mMap;
    if (arrayMap == null
        || (referenceWithHistory = (ReferenceWithHistory) arrayMap.get(obj)) == null) {
      return null;
    }
    if (referenceWithHistory.getHistoryCount() == 0) {
      Log.w("ArrayMapWithHistory", "History for \"" + obj + "\" was unexpectedly empty");
    }
    return referenceWithHistory.get();
  }

  public int size() {
    ArrayMap arrayMap = this.mMap;
    if (arrayMap == null) {
      return 0;
    }
    return arrayMap.size();
  }

  public Object keyAt(int i) {
    ArrayMap arrayMap = this.mMap;
    if (arrayMap == null) {
      throw new ArrayIndexOutOfBoundsException(i);
    }
    return arrayMap.keyAt(i);
  }

  public Object valueAt(int i) {
    ArrayMap arrayMap = this.mMap;
    if (arrayMap == null) {
      throw new ArrayIndexOutOfBoundsException(i);
    }
    ReferenceWithHistory referenceWithHistory = (ReferenceWithHistory) arrayMap.valueAt(i);
    if (referenceWithHistory == null || referenceWithHistory.getHistoryCount() == 0) {
      Log.w("ArrayMapWithHistory", "valueAt(" + i + ") was unexpectedly null or empty");
      return null;
    }
    return referenceWithHistory.get();
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    if (this.mMap == null) {
      indentingPrintWriter.println("{Empty}");
    } else {
      for (int i = 0; i < this.mMap.size(); i++) {
        indentingPrintWriter.println("key idx: " + i + "=" + this.mMap.keyAt(i));
        ReferenceWithHistory referenceWithHistory = (ReferenceWithHistory) this.mMap.valueAt(i);
        indentingPrintWriter.println("val idx: " + i + "=" + referenceWithHistory);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Historic values=[");
        indentingPrintWriter.increaseIndent();
        referenceWithHistory.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("]");
        indentingPrintWriter.decreaseIndent();
      }
    }
    indentingPrintWriter.flush();
  }

  public int getHistoryCountForKeyForTests(Object obj) {
    ReferenceWithHistory referenceWithHistory;
    ArrayMap arrayMap = this.mMap;
    if (arrayMap == null
        || (referenceWithHistory = (ReferenceWithHistory) arrayMap.get(obj)) == null) {
      return 0;
    }
    if (referenceWithHistory.getHistoryCount() == 0) {
      Log.w(
          "ArrayMapWithHistory",
          "getValuesSizeForKeyForTests(\"" + obj + "\") was unexpectedly empty");
      return 0;
    }
    return referenceWithHistory.getHistoryCount();
  }

  public String toString() {
    return "ArrayMapWithHistory{mHistorySize=" + this.mMaxHistorySize + ", mMap=" + this.mMap + '}';
  }
}
