package com.android.server.pm;

import android.content.pm.ChangedPackages;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public class ChangedPackagesTracker {
  public int mChangedPackagesSequenceNumber;
  public final Object mLock = new Object();
  public final SparseArray mUserIdToSequenceToPackage = new SparseArray();
  public final SparseArray mChangedPackagesSequenceNumbers = new SparseArray();

  public ChangedPackages getChangedPackages(int i, int i2) {
    synchronized (this.mLock) {
      ChangedPackages changedPackages = null;
      if (i >= this.mChangedPackagesSequenceNumber) {
        return null;
      }
      SparseArray sparseArray = (SparseArray) this.mUserIdToSequenceToPackage.get(i2);
      if (sparseArray == null) {
        return null;
      }
      ArrayList arrayList = new ArrayList(this.mChangedPackagesSequenceNumber - i);
      while (i < this.mChangedPackagesSequenceNumber) {
        String str = (String) sparseArray.get(i);
        if (str != null) {
          arrayList.add(str);
        }
        i++;
      }
      if (!arrayList.isEmpty()) {
        changedPackages = new ChangedPackages(this.mChangedPackagesSequenceNumber, arrayList);
      }
      return changedPackages;
    }
  }

  public int getSequenceNumber() {
    return this.mChangedPackagesSequenceNumber;
  }

  public void iterateAll(BiConsumer biConsumer) {
    synchronized (this.mLock) {
      biConsumer.accept(
          Integer.valueOf(this.mChangedPackagesSequenceNumber), this.mUserIdToSequenceToPackage);
    }
  }

  public void updateSequenceNumber(String str, int[] iArr) {
    synchronized (this.mLock) {
      for (int length = iArr.length - 1; length >= 0; length--) {
        int i = iArr[length];
        SparseArray sparseArray = (SparseArray) this.mUserIdToSequenceToPackage.get(i);
        if (sparseArray == null) {
          sparseArray = new SparseArray();
          this.mUserIdToSequenceToPackage.put(i, sparseArray);
        }
        Map map = (Map) this.mChangedPackagesSequenceNumbers.get(i);
        if (map == null) {
          map = new HashMap();
          this.mChangedPackagesSequenceNumbers.put(i, map);
        }
        Integer num = (Integer) map.get(str);
        if (num != null) {
          sparseArray.remove(num.intValue());
        }
        sparseArray.put(this.mChangedPackagesSequenceNumber, str);
        map.put(str, Integer.valueOf(this.mChangedPackagesSequenceNumber));
      }
      this.mChangedPackagesSequenceNumber++;
    }
  }
}
