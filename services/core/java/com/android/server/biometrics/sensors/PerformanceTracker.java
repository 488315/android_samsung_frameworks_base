package com.android.server.biometrics.sensors;

import android.util.SparseArray;

/* loaded from: classes.dex */
public class PerformanceTracker {
  public static SparseArray sTrackers;
  public final SparseArray mAllUsersInfo = new SparseArray();
  public int mHALDeathCount;

  public class Info {
    public int mAccept;
    public int mAcceptCrypto;
    public int mAcquire;
    public int mAcquireCrypto;
    public SparseArray mNoMatchReason;
    public int mPermanentLockout;
    public int mQuality;
    public int mQualityCrypto;
    public int mReject;
    public int mRejectCrypto;
    public int mTimedLockout;

    public Info() {}
  }

  public static synchronized PerformanceTracker getInstanceForSensorId(int i) {
    PerformanceTracker performanceTracker;
    synchronized (PerformanceTracker.class) {
      if (sTrackers == null) {
        sTrackers = new SparseArray();
      }
      if (!sTrackers.contains(i)) {
        sTrackers.put(i, new PerformanceTracker());
      }
      performanceTracker = (PerformanceTracker) sTrackers.get(i);
    }
    return performanceTracker;
  }

  public final void createUserEntryIfNecessary(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return;
    }
    Info info = new Info();
    info.mNoMatchReason = new SparseArray();
    this.mAllUsersInfo.put(i, info);
  }

  public void incrementAuthForUser(int i, boolean z) {
    createUserEntryIfNecessary(i);
    if (z) {
      ((Info) this.mAllUsersInfo.get(i)).mAccept++;
    } else {
      ((Info) this.mAllUsersInfo.get(i)).mReject++;
    }
  }

  public void incrementCryptoAuthForUser(int i, boolean z) {
    createUserEntryIfNecessary(i);
    if (z) {
      ((Info) this.mAllUsersInfo.get(i)).mAcceptCrypto++;
    } else {
      ((Info) this.mAllUsersInfo.get(i)).mRejectCrypto++;
    }
  }

  public void incrementAcquireForUser(int i, boolean z) {
    createUserEntryIfNecessary(i);
    if (z) {
      ((Info) this.mAllUsersInfo.get(i)).mAcquireCrypto++;
    } else {
      ((Info) this.mAllUsersInfo.get(i)).mAcquire++;
    }
  }

  public void incrementTimedLockoutForUser(int i) {
    createUserEntryIfNecessary(i);
    ((Info) this.mAllUsersInfo.get(i)).mTimedLockout++;
  }

  public void incrementPermanentLockoutForUser(int i) {
    createUserEntryIfNecessary(i);
    ((Info) this.mAllUsersInfo.get(i)).mPermanentLockout++;
  }

  public void incrementHALDeathCount() {
    this.mHALDeathCount++;
  }

  public void clear() {
    this.mAllUsersInfo.clear();
    this.mHALDeathCount = 0;
  }

  public int getAcceptForUser(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return ((Info) this.mAllUsersInfo.get(i)).mAccept;
    }
    return 0;
  }

  public int getRejectForUser(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return ((Info) this.mAllUsersInfo.get(i)).mReject;
    }
    return 0;
  }

  public int getAcquireForUser(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return ((Info) this.mAllUsersInfo.get(i)).mAcquire;
    }
    return 0;
  }

  public int getAcceptCryptoForUser(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return ((Info) this.mAllUsersInfo.get(i)).mAcceptCrypto;
    }
    return 0;
  }

  public int getRejectCryptoForUser(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return ((Info) this.mAllUsersInfo.get(i)).mRejectCrypto;
    }
    return 0;
  }

  public int getAcquireCryptoForUser(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return ((Info) this.mAllUsersInfo.get(i)).mAcquireCrypto;
    }
    return 0;
  }

  public int getTimedLockoutForUser(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return ((Info) this.mAllUsersInfo.get(i)).mTimedLockout;
    }
    return 0;
  }

  public int getPermanentLockoutForUser(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return ((Info) this.mAllUsersInfo.get(i)).mPermanentLockout;
    }
    return 0;
  }

  public int getHALDeathCount() {
    return this.mHALDeathCount;
  }

  public void semIncrementQualityForUser(int i, boolean z) {
    createUserEntryIfNecessary(i);
    if (z) {
      ((Info) this.mAllUsersInfo.get(i)).mQualityCrypto++;
    } else {
      ((Info) this.mAllUsersInfo.get(i)).mQuality++;
    }
  }

  public void semIncrementNoMatchReason(int i, int i2) {
    createUserEntryIfNecessary(i);
    if (((Info) this.mAllUsersInfo.get(i)).mNoMatchReason.get(i2) == null) {
      ((Info) this.mAllUsersInfo.get(i)).mNoMatchReason.put(i2, 1);
    } else {
      ((Info) this.mAllUsersInfo.get(i))
          .mNoMatchReason.put(
              i2,
              Integer.valueOf(
                  ((Integer) ((Info) this.mAllUsersInfo.get(i)).mNoMatchReason.get(i2)).intValue()
                      + 1));
    }
  }

  public int semGetQualityForUser(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return ((Info) this.mAllUsersInfo.get(i)).mQuality;
    }
    return 0;
  }

  public int semGetQualityCryptoForUser(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return ((Info) this.mAllUsersInfo.get(i)).mQualityCrypto;
    }
    return 0;
  }

  public SparseArray semGetNoMatchReason(int i) {
    if (this.mAllUsersInfo.contains(i)) {
      return ((Info) this.mAllUsersInfo.get(i)).mNoMatchReason;
    }
    return null;
  }
}
