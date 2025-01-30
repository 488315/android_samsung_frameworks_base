package com.android.server.enterprise.firewall;

import android.util.SparseBooleanArray;

/* loaded from: classes2.dex */
public class KnoxNetIdManager {
  public int mLastNetId;
  public final int mMinNetId;
  public final SparseBooleanArray mNetIdInUse;

  public KnoxNetIdManager() {
    this(40000);
  }

  public KnoxNetIdManager(int i) {
    this.mNetIdInUse = new SparseBooleanArray();
    this.mLastNetId = 64510;
    this.mMinNetId = i;
  }

  public final int getNextAvailableNetIdLocked(int i, SparseBooleanArray sparseBooleanArray) {
    int i2 = 64510;
    while (true) {
      int i3 = this.mMinNetId;
      if (i2 >= i3) {
        i = i > i3 ? i - 1 : 64510;
        if (!sparseBooleanArray.get(i)) {
          return i;
        }
        i2--;
      } else {
        throw new IllegalStateException("No free netIds");
      }
    }
  }

  public int reserveNetId() {
    int i;
    synchronized (this.mNetIdInUse) {
      int nextAvailableNetIdLocked = getNextAvailableNetIdLocked(this.mLastNetId, this.mNetIdInUse);
      this.mLastNetId = nextAvailableNetIdLocked;
      this.mNetIdInUse.put(nextAvailableNetIdLocked, true);
      i = this.mLastNetId;
    }
    return i;
  }

  public void releaseNetId(int i) {
    synchronized (this.mNetIdInUse) {
      this.mNetIdInUse.delete(i);
    }
  }
}
