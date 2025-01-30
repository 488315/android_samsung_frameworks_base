package com.android.server.am;

import android.content.Context;
import android.os.PowerExemptionManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;

/* loaded from: classes.dex */
public abstract class BaseAppStateEventsTracker extends BaseAppStateTracker
    implements BaseAppStateEvents.Factory {
  public final UidProcessMap mPkgEvents;
  public final ArraySet mTopUids;

  public void dumpOthers(PrintWriter printWriter, String str) {}

  public void onUntrackingUidLocked(int i) {}

  public BaseAppStateEventsTracker(
      Context context,
      AppRestrictionController appRestrictionController,
      Constructor constructor,
      Object obj) {
    super(context, appRestrictionController, constructor, obj);
    this.mPkgEvents = new UidProcessMap();
    this.mTopUids = new ArraySet();
  }

  public void reset() {
    synchronized (this.mLock) {
      this.mPkgEvents.clear();
      this.mTopUids.clear();
    }
  }

  public BaseAppStateEvents getUidEventsLocked(int i) {
    ArrayMap arrayMap = (ArrayMap) this.mPkgEvents.getMap().get(i);
    BaseAppStateEvents baseAppStateEvents = null;
    if (arrayMap == null) {
      return null;
    }
    for (int size = arrayMap.size() - 1; size >= 0; size--) {
      BaseAppStateEvents baseAppStateEvents2 = (BaseAppStateEvents) arrayMap.valueAt(size);
      if (baseAppStateEvents2 != null) {
        if (baseAppStateEvents == null) {
          baseAppStateEvents = createAppStateEvents(i, baseAppStateEvents2.mPackageName);
        }
        baseAppStateEvents.add(baseAppStateEvents2);
      }
    }
    return baseAppStateEvents;
  }

  public void trim(long j) {
    synchronized (this.mLock) {
      trimLocked(j);
    }
  }

  public void trimLocked(long j) {
    SparseArray map = this.mPkgEvents.getMap();
    for (int size = map.size() - 1; size >= 0; size--) {
      ArrayMap arrayMap = (ArrayMap) map.valueAt(size);
      for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
        BaseAppStateEvents baseAppStateEvents = (BaseAppStateEvents) arrayMap.valueAt(size2);
        baseAppStateEvents.trim(j);
        if (baseAppStateEvents.isEmpty()) {
          arrayMap.removeAt(size2);
        }
      }
      if (arrayMap.size() == 0) {
        map.removeAt(size);
      }
    }
  }

  public boolean isUidOnTop(int i) {
    boolean contains;
    synchronized (this.mLock) {
      contains = this.mTopUids.contains(Integer.valueOf(i));
    }
    return contains;
  }

  @Override // com.android.server.am.BaseAppStateTracker
  public void onUidProcStateChanged(int i, int i2) {
    synchronized (this.mLock) {
      if (this.mPkgEvents.getMap().indexOfKey(i) < 0) {
        return;
      }
      onUidProcStateChangedUncheckedLocked(i, i2);
    }
  }

  public void onUidProcStateChangedUncheckedLocked(int i, int i2) {
    if (i2 < 4) {
      this.mTopUids.add(Integer.valueOf(i));
    } else {
      this.mTopUids.remove(Integer.valueOf(i));
    }
  }

  @Override // com.android.server.am.BaseAppStateTracker
  public void onUidGone(int i) {
    synchronized (this.mLock) {
      this.mTopUids.remove(Integer.valueOf(i));
    }
  }

  @Override // com.android.server.am.BaseAppStateTracker
  public void onUidRemoved(int i) {
    synchronized (this.mLock) {
      this.mPkgEvents.getMap().remove(i);
      onUntrackingUidLocked(i);
    }
  }

  @Override // com.android.server.am.BaseAppStateTracker
  public void onUserRemoved(int i) {
    synchronized (this.mLock) {
      SparseArray map = this.mPkgEvents.getMap();
      for (int size = map.size() - 1; size >= 0; size--) {
        int keyAt = map.keyAt(size);
        if (UserHandle.getUserId(keyAt) == i) {
          map.removeAt(size);
          onUntrackingUidLocked(keyAt);
        }
      }
    }
  }

  @Override // com.android.server.am.BaseAppStateTracker
  public void dump(PrintWriter printWriter, String str) {
    BaseAppStateEventsPolicy baseAppStateEventsPolicy =
        (BaseAppStateEventsPolicy) this.mInjector.getPolicy();
    synchronized (this.mLock) {
      long elapsedRealtime = SystemClock.elapsedRealtime();
      SparseArray map = this.mPkgEvents.getMap();
      for (int size = map.size() - 1; size >= 0; size--) {
        int keyAt = map.keyAt(size);
        ArrayMap arrayMap = (ArrayMap) map.valueAt(size);
        for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
          String str2 = (String) arrayMap.keyAt(size2);
          BaseAppStateEvents baseAppStateEvents = (BaseAppStateEvents) arrayMap.valueAt(size2);
          dumpEventHeaderLocked(
              printWriter, str, str2, keyAt, baseAppStateEvents, baseAppStateEventsPolicy);
          dumpEventLocked(printWriter, str, baseAppStateEvents, elapsedRealtime);
        }
      }
    }
    dumpOthers(printWriter, str);
    baseAppStateEventsPolicy.dump(printWriter, str);
  }

  public void dumpEventHeaderLocked(
      PrintWriter printWriter,
      String str,
      String str2,
      int i,
      BaseAppStateEvents baseAppStateEvents,
      BaseAppStateEventsPolicy baseAppStateEventsPolicy) {
    printWriter.print(str);
    printWriter.print("* ");
    printWriter.print(str2);
    printWriter.print('/');
    printWriter.print(UserHandle.formatUid(i));
    printWriter.print(" exemption=");
    printWriter.println(
        baseAppStateEventsPolicy.getExemptionReasonString(
            str2, i, baseAppStateEvents.mExemptReason));
  }

  public void dumpEventLocked(
      PrintWriter printWriter, String str, BaseAppStateEvents baseAppStateEvents, long j) {
    baseAppStateEvents.dump(printWriter, "  " + str, j);
  }

  public abstract class BaseAppStateEventsPolicy extends BaseAppStatePolicy
      implements BaseAppStateEvents.MaxTrackingDurationConfig {
    public final long mDefaultMaxTrackingDuration;
    public final String mKeyMaxTrackingDuration;
    public volatile long mMaxTrackingDuration;

    public abstract void onMaxTrackingDurationChanged(long j);

    public BaseAppStateEventsPolicy(
        BaseAppStateTracker.Injector injector,
        BaseAppStateEventsTracker baseAppStateEventsTracker,
        String str,
        boolean z,
        String str2,
        long j) {
      super(injector, baseAppStateEventsTracker, str, z);
      this.mKeyMaxTrackingDuration = str2;
      this.mDefaultMaxTrackingDuration = j;
    }

    @Override // com.android.server.am.BaseAppStatePolicy
    public void onPropertiesChanged(String str) {
      if (this.mKeyMaxTrackingDuration.equals(str)) {
        updateMaxTrackingDuration();
      } else {
        super.onPropertiesChanged(str);
      }
    }

    @Override // com.android.server.am.BaseAppStatePolicy
    public void onSystemReady() {
      super.onSystemReady();
      updateMaxTrackingDuration();
    }

    public void updateMaxTrackingDuration() {
      long j =
          DeviceConfig.getLong(
              "activity_manager", this.mKeyMaxTrackingDuration, this.mDefaultMaxTrackingDuration);
      if (j != this.mMaxTrackingDuration) {
        this.mMaxTrackingDuration = j;
        onMaxTrackingDurationChanged(j);
      }
    }

    @Override // com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig
    public long getMaxTrackingDuration() {
      return this.mMaxTrackingDuration;
    }

    public String getExemptionReasonString(String str, int i, int i2) {
      return PowerExemptionManager.reasonCodeToString(i2);
    }

    @Override // com.android.server.am.BaseAppStatePolicy
    public void dump(PrintWriter printWriter, String str) {
      super.dump(printWriter, str);
      if (isEnabled()) {
        printWriter.print(str);
        printWriter.print(this.mKeyMaxTrackingDuration);
        printWriter.print('=');
        printWriter.println(this.mMaxTrackingDuration);
      }
    }
  }
}
