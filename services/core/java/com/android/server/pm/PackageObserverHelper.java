package com.android.server.pm;

import android.content.p000pm.PackageManagerInternal;
import android.util.ArraySet;

/* loaded from: classes3.dex */
public class PackageObserverHelper {
  public final Object mLock = new Object();
  public ArraySet mActiveSnapshot = new ArraySet();

  public void addObserver(PackageManagerInternal.PackageListObserver packageListObserver) {
    synchronized (this.mLock) {
      ArraySet arraySet = new ArraySet(this.mActiveSnapshot);
      arraySet.add(packageListObserver);
      this.mActiveSnapshot = arraySet;
    }
  }

  public void removeObserver(PackageManagerInternal.PackageListObserver packageListObserver) {
    synchronized (this.mLock) {
      ArraySet arraySet = new ArraySet(this.mActiveSnapshot);
      arraySet.remove(packageListObserver);
      this.mActiveSnapshot = arraySet;
    }
  }

  public void notifyAdded(String str, int i) {
    ArraySet arraySet;
    synchronized (this.mLock) {
      arraySet = this.mActiveSnapshot;
    }
    int size = arraySet.size();
    for (int i2 = 0; i2 < size; i2++) {
      ((PackageManagerInternal.PackageListObserver) arraySet.valueAt(i2)).onPackageAdded(str, i);
    }
  }

  public void notifyChanged(String str, int i) {
    ArraySet arraySet;
    synchronized (this.mLock) {
      arraySet = this.mActiveSnapshot;
    }
    int size = arraySet.size();
    for (int i2 = 0; i2 < size; i2++) {
      ((PackageManagerInternal.PackageListObserver) arraySet.valueAt(i2)).onPackageChanged(str, i);
    }
  }

  public void notifyRemoved(String str, int i) {
    ArraySet arraySet;
    synchronized (this.mLock) {
      arraySet = this.mActiveSnapshot;
    }
    int size = arraySet.size();
    for (int i2 = 0; i2 < size; i2++) {
      ((PackageManagerInternal.PackageListObserver) arraySet.valueAt(i2)).onPackageRemoved(str, i);
    }
  }
}
