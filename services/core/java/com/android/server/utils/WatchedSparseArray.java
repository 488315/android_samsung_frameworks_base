package com.android.server.utils;

import android.util.SparseArray;

/* loaded from: classes3.dex */
public class WatchedSparseArray extends WatchableImpl implements Snappable {
  public final Watcher mObserver;
  public final SparseArray mStorage;
  public volatile boolean mWatching;

  public final void onChanged() {
    dispatchChange(this);
  }

  public final void registerChild(Object obj) {
    if (this.mWatching && (obj instanceof Watchable)) {
      ((Watchable) obj).registerObserver(this.mObserver);
    }
  }

  public final void unregisterChild(Object obj) {
    if (this.mWatching && (obj instanceof Watchable)) {
      ((Watchable) obj).unregisterObserver(this.mObserver);
    }
  }

  public final void unregisterChildIf(Object obj) {
    if (this.mWatching && (obj instanceof Watchable) && this.mStorage.indexOfValue(obj) == -1) {
      ((Watchable) obj).unregisterObserver(this.mObserver);
    }
  }

  @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
  public void registerObserver(Watcher watcher) {
    super.registerObserver(watcher);
    if (registeredObserverCount() == 1) {
      this.mWatching = true;
      int size = this.mStorage.size();
      for (int i = 0; i < size; i++) {
        registerChild(this.mStorage.valueAt(i));
      }
    }
  }

  @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
  public void unregisterObserver(Watcher watcher) {
    super.unregisterObserver(watcher);
    if (registeredObserverCount() == 0) {
      int size = this.mStorage.size();
      for (int i = 0; i < size; i++) {
        unregisterChild(this.mStorage.valueAt(i));
      }
      this.mWatching = false;
    }
  }

  public WatchedSparseArray() {
    this.mWatching = false;
    this.mObserver =
        new Watcher() { // from class: com.android.server.utils.WatchedSparseArray.1
          @Override // com.android.server.utils.Watcher
          public void onChange(Watchable watchable) {
            WatchedSparseArray.this.dispatchChange(watchable);
          }
        };
    this.mStorage = new SparseArray();
  }

  public WatchedSparseArray(int i) {
    this.mWatching = false;
    this.mObserver =
        new Watcher() { // from class: com.android.server.utils.WatchedSparseArray.1
          @Override // com.android.server.utils.Watcher
          public void onChange(Watchable watchable) {
            WatchedSparseArray.this.dispatchChange(watchable);
          }
        };
    this.mStorage = new SparseArray(i);
  }

  public WatchedSparseArray(WatchedSparseArray watchedSparseArray) {
    this.mWatching = false;
    this.mObserver =
        new Watcher() { // from class: com.android.server.utils.WatchedSparseArray.1
          @Override // com.android.server.utils.Watcher
          public void onChange(Watchable watchable) {
            WatchedSparseArray.this.dispatchChange(watchable);
          }
        };
    this.mStorage = watchedSparseArray.mStorage.clone();
  }

  public Object get(int i) {
    return this.mStorage.get(i);
  }

  public void delete(int i) {
    Object obj = this.mStorage.get(i);
    this.mStorage.delete(i);
    unregisterChildIf(obj);
    onChanged();
  }

  public Object removeReturnOld(int i) {
    Object removeReturnOld = this.mStorage.removeReturnOld(i);
    unregisterChildIf(removeReturnOld);
    return removeReturnOld;
  }

  public void remove(int i) {
    delete(i);
  }

  public void put(int i, Object obj) {
    Object obj2 = this.mStorage.get(i);
    this.mStorage.put(i, obj);
    unregisterChildIf(obj2);
    registerChild(obj);
    onChanged();
  }

  public int size() {
    return this.mStorage.size();
  }

  public int keyAt(int i) {
    return this.mStorage.keyAt(i);
  }

  public Object valueAt(int i) {
    return this.mStorage.valueAt(i);
  }

  public int hashCode() {
    return this.mStorage.hashCode();
  }

  public boolean equals(Object obj) {
    if (obj instanceof WatchedSparseArray) {
      return this.mStorage.equals(((WatchedSparseArray) obj).mStorage);
    }
    return false;
  }

  public String toString() {
    return this.mStorage.toString();
  }

  @Override // com.android.server.utils.Snappable
  public WatchedSparseArray snapshot() {
    WatchedSparseArray watchedSparseArray = new WatchedSparseArray(size());
    snapshot(watchedSparseArray, this);
    return watchedSparseArray;
  }

  public void snapshot(WatchedSparseArray watchedSparseArray) {
    snapshot(this, watchedSparseArray);
  }

  public static void snapshot(
      WatchedSparseArray watchedSparseArray, WatchedSparseArray watchedSparseArray2) {
    if (watchedSparseArray.size() != 0) {
      throw new IllegalArgumentException("snapshot destination is not empty");
    }
    int size = watchedSparseArray2.size();
    for (int i = 0; i < size; i++) {
      Object maybeSnapshot = Snapshots.maybeSnapshot(watchedSparseArray2.valueAt(i));
      watchedSparseArray.mStorage.put(watchedSparseArray2.keyAt(i), maybeSnapshot);
    }
    watchedSparseArray.seal();
  }
}
