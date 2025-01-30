package com.android.server.am;

import android.content.Context;
import android.os.SystemClock;
import android.util.SparseArray;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.LinkedList;

/* loaded from: classes.dex */
public abstract class BaseAppStateDurationsTracker extends BaseAppStateEventsTracker {
  public final SparseArray mUidStateDurations;

  public BaseAppStateDurationsTracker(
      Context context,
      AppRestrictionController appRestrictionController,
      Constructor constructor,
      Object obj) {
    super(context, appRestrictionController, constructor, obj);
    this.mUidStateDurations = new SparseArray();
  }

  @Override // com.android.server.am.BaseAppStateEventsTracker,
            // com.android.server.am.BaseAppStateTracker
  public void onUidProcStateChanged(int i, int i2) {
    synchronized (this.mLock) {
      if (this.mPkgEvents.getMap().indexOfKey(i) < 0) {
        return;
      }
      onUidProcStateChangedUncheckedLocked(i, i2);
      UidStateDurations uidStateDurations = (UidStateDurations) this.mUidStateDurations.get(i);
      if (uidStateDurations == null) {
        uidStateDurations =
            new UidStateDurations(
                i, (BaseAppStateEvents.MaxTrackingDurationConfig) this.mInjector.getPolicy());
        this.mUidStateDurations.put(i, uidStateDurations);
      }
      uidStateDurations.addEvent(i2 < 4, SystemClock.elapsedRealtime());
    }
  }

  @Override // com.android.server.am.BaseAppStateEventsTracker,
            // com.android.server.am.BaseAppStateTracker
  public void onUidGone(int i) {
    onUidProcStateChanged(i, 20);
  }

  @Override // com.android.server.am.BaseAppStateEventsTracker
  public void trimLocked(long j) {
    super.trimLocked(j);
    for (int size = this.mUidStateDurations.size() - 1; size >= 0; size--) {
      UidStateDurations uidStateDurations =
          (UidStateDurations) this.mUidStateDurations.valueAt(size);
      uidStateDurations.trim(j);
      if (uidStateDurations.isEmpty()) {
        this.mUidStateDurations.removeAt(size);
      }
    }
  }

  @Override // com.android.server.am.BaseAppStateEventsTracker
  public void onUntrackingUidLocked(int i) {
    this.mUidStateDurations.remove(i);
  }

  public long getTotalDurations(String str, int i, long j, int i2, boolean z) {
    UidStateDurations uidStateDurations;
    synchronized (this.mLock) {
      BaseAppStateDurations baseAppStateDurations =
          (BaseAppStateDurations) this.mPkgEvents.get(i, str);
      if (baseAppStateDurations == null) {
        return 0L;
      }
      if (z
          && (uidStateDurations = (UidStateDurations) this.mUidStateDurations.get(i)) != null
          && !uidStateDurations.isEmpty()) {
        BaseAppStateDurations baseAppStateDurations2 =
            (BaseAppStateDurations) createAppStateEvents(baseAppStateDurations);
        baseAppStateDurations2.subtract(uidStateDurations, i2, 0);
        return baseAppStateDurations2.getTotalDurations(j, i2);
      }
      return baseAppStateDurations.getTotalDurations(j, i2);
    }
  }

  public long getTotalDurations(String str, int i, long j, int i2) {
    return getTotalDurations(str, i, j, i2, true);
  }

  public long getTotalDurations(int i, long j, int i2, boolean z) {
    UidStateDurations uidStateDurations;
    synchronized (this.mLock) {
      BaseAppStateDurations baseAppStateDurations = (BaseAppStateDurations) getUidEventsLocked(i);
      if (baseAppStateDurations == null) {
        return 0L;
      }
      if (z
          && (uidStateDurations = (UidStateDurations) this.mUidStateDurations.get(i)) != null
          && !uidStateDurations.isEmpty()) {
        baseAppStateDurations.subtract(uidStateDurations, i2, 0);
      }
      return baseAppStateDurations.getTotalDurations(j, i2);
    }
  }

  public long getTotalDurations(int i, long j, int i2) {
    return getTotalDurations(i, j, i2, true);
  }

  public long getTotalDurationsSince(String str, int i, long j, long j2, int i2, boolean z) {
    UidStateDurations uidStateDurations;
    synchronized (this.mLock) {
      BaseAppStateDurations baseAppStateDurations =
          (BaseAppStateDurations) this.mPkgEvents.get(i, str);
      if (baseAppStateDurations == null) {
        return 0L;
      }
      if (z
          && (uidStateDurations = (UidStateDurations) this.mUidStateDurations.get(i)) != null
          && !uidStateDurations.isEmpty()) {
        BaseAppStateDurations baseAppStateDurations2 =
            (BaseAppStateDurations) createAppStateEvents(baseAppStateDurations);
        baseAppStateDurations2.subtract(uidStateDurations, i2, 0);
        return baseAppStateDurations2.getTotalDurationsSince(j, j2, i2);
      }
      return baseAppStateDurations.getTotalDurationsSince(j, j2, i2);
    }
  }

  public long getTotalDurationsSince(String str, int i, long j, long j2, int i2) {
    return getTotalDurationsSince(str, i, j, j2, i2, true);
  }

  public long getTotalDurationsSince(String str, int i, long j, long j2) {
    return getTotalDurationsSince(str, i, j, j2, 0);
  }

  public long getTotalDurationsSince(int i, long j, long j2, int i2, boolean z) {
    UidStateDurations uidStateDurations;
    synchronized (this.mLock) {
      BaseAppStateDurations baseAppStateDurations = (BaseAppStateDurations) getUidEventsLocked(i);
      if (baseAppStateDurations == null) {
        return 0L;
      }
      if (z
          && (uidStateDurations = (UidStateDurations) this.mUidStateDurations.get(i)) != null
          && !uidStateDurations.isEmpty()) {
        baseAppStateDurations.subtract(uidStateDurations, i2, 0);
      }
      return baseAppStateDurations.getTotalDurationsSince(j, j2, i2);
    }
  }

  public long getTotalDurationsSince(int i, long j, long j2, int i2) {
    return getTotalDurationsSince(i, j, j2, i2, true);
  }

  public long getTotalDurationsSince(int i, long j, long j2) {
    return getTotalDurationsSince(i, j, j2, 0);
  }

  @Override // com.android.server.am.BaseAppStateEventsTracker
  public void reset() {
    super.reset();
    synchronized (this.mLock) {
      this.mUidStateDurations.clear();
    }
  }

  @Override // com.android.server.am.BaseAppStateEventsTracker
  public void dumpEventLocked(
      PrintWriter printWriter, String str, BaseAppStateDurations baseAppStateDurations, long j) {
    UidStateDurations uidStateDurations =
        (UidStateDurations) this.mUidStateDurations.get(baseAppStateDurations.mUid);
    printWriter.print("  " + str);
    printWriter.println("(bg only)");
    if (uidStateDurations == null || uidStateDurations.isEmpty()) {
      baseAppStateDurations.dump(printWriter, "    " + str, j);
      return;
    }
    BaseAppStateDurations baseAppStateDurations2 =
        (BaseAppStateDurations) createAppStateEvents(baseAppStateDurations);
    baseAppStateDurations2.subtract(uidStateDurations, 0);
    baseAppStateDurations2.dump(printWriter, "    " + str, j);
    printWriter.print("  " + str);
    printWriter.println("(fg + bg)");
    baseAppStateDurations.dump(printWriter, "    " + str, j);
  }

  public class SimplePackageDurations extends BaseAppStateDurations {
    @Override // com.android.server.am.BaseAppStateEvents
    public String formatEventTypeLabel(int i) {
      return "";
    }

    public SimplePackageDurations(
        int i, String str, BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
      super(i, str, 1, "ActivityManager", maxTrackingDurationConfig);
      this.mEvents[0] = new LinkedList();
    }

    public SimplePackageDurations(SimplePackageDurations simplePackageDurations) {
      super(simplePackageDurations);
    }

    public void addEvent(boolean z, long j) {
      addEvent(z, new BaseAppStateTimeEvents.BaseTimeEvent(j), 0);
    }

    public boolean isActive() {
      return isActive(0);
    }
  }

  public class UidStateDurations extends SimplePackageDurations {
    public UidStateDurations(
        int i, BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
      super(i, "", maxTrackingDurationConfig);
    }
  }
}
