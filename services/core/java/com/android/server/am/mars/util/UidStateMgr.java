package com.android.server.am.mars.util;

import android.app.IUidObserver;
import android.content.Context;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.FreecessController;
import com.android.server.am.FreecessPkgStatus;
import com.android.server.am.MARsPolicyManager;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public class UidStateMgr {
  public final String TAG;
  public Context mContext;
  public ConcurrentList mRunningList;
  public ConcurrentList mTopUidList;
  public ConcurrentList mUidCached;
  public ConcurrentList mUidForegroundList;
  public ConcurrentList mUidGoneList;
  public ConcurrentList mUidIdleList;
  public final IUidObserver mUidObserver;

  public abstract class UidStateMgrHolder {
    public static final UidStateMgr INSTANCE = new UidStateMgr();
  }

  public UidStateMgr() {
    this.TAG = "MARs:" + UidStateMgr.class.getSimpleName();
    this.mTopUidList = new ConcurrentList(new ArrayList());
    this.mUidForegroundList = new ConcurrentList(new ArrayList());
    this.mRunningList = new ConcurrentList(new ArrayList());
    this.mUidCached = new ConcurrentList(new ArrayList());
    this.mUidIdleList = new ConcurrentList(new ArrayList());
    this.mUidGoneList = new ConcurrentList(new ArrayList());
    this.mUidObserver =
        new IUidObserver.Stub() { // from class: com.android.server.am.mars.util.UidStateMgr.1
          public void onUidCachedChanged(int i, boolean z) {}

          public void onUidProcAdjChanged(int i, int i2) {}

          public void onUidIdle(int i, boolean z) {
            UidStateMgr.this.addToUidIdleList(i);
            UidStateMgr.this.addToRunningList(i);
          }

          public void onUidGone(int i, boolean z) {
            UidStateMgr.this.removeFromUidIdleList(i);
            UidStateMgr.this.removeFromRunningList(i);
            UidStateMgr.this.addToUidGone(i);
          }

          public void onUidActive(int i) {
            UidStateMgr.this.removeFromUidIdleList(i);
            UidStateMgr.this.addToRunningList(i);
            UidStateMgr.this.removeFromUidGone(i);
          }

          public void onUidStateChanged(int i, int i2, long j, int i3) {
            if (i2 == 2) {
              UidStateMgr.this.addToUidTopList(i);
            } else {
              UidStateMgr.this.removeFromUidTopList(i);
            }
            if (i2 == 4 || i2 == 5) {
              UidStateMgr.this.addToUidForegroundService(i);
            } else {
              UidStateMgr.this.removeFromUidForegroundService(i);
            }
            if (i2 >= 16 && i2 <= 18) {
              UidStateMgr.this.addToUidCached(i);
            } else {
              UidStateMgr.this.removeFromUidCached(i);
            }
          }
        };
  }

  public static UidStateMgr getInstance() {
    return UidStateMgrHolder.INSTANCE;
  }

  public final void setContext(Context context) {
    this.mContext = context;
  }

  public void init(ActivityManagerService activityManagerService, Context context) {
    setContext(context);
    Map activeUids = activityManagerService.getActiveUids();
    for (Integer num : activeUids.keySet()) {
      addToRunningList(num.intValue());
      if (((Boolean) activeUids.get(num)).booleanValue()) {
        addToUidIdleList(num.intValue());
      }
    }
    activityManagerService.registerUidObserver(this.mUidObserver, 15, -1, null);
  }

  public boolean isUidTop(int i) {
    return this.mTopUidList.contains(Integer.valueOf(i));
  }

  public boolean isUidForegroundService(int i) {
    return this.mUidForegroundList.contains(Integer.valueOf(i));
  }

  public boolean isUidIdle(int i) {
    return this.mUidIdleList.contains(Integer.valueOf(i));
  }

  public boolean isUidRunning(int i) {
    return this.mRunningList.contains(Integer.valueOf(i));
  }

  public boolean isUidActive(int i) {
    return this.mRunningList.contains(Integer.valueOf(i))
        && !this.mUidIdleList.contains(Integer.valueOf(i));
  }

  public boolean isUidGone(int i) {
    return this.mUidGoneList.contains(Integer.valueOf(i));
  }

  public final void addToUidTopList(int i) {
    if (this.mTopUidList.contains(Integer.valueOf(i))) {
      return;
    }
    this.mTopUidList.add(Integer.valueOf(i));
  }

  public final void removeFromUidTopList(int i) {
    this.mTopUidList.remove(Integer.valueOf(i));
  }

  public final void addToUidForegroundService(int i) {
    if (this.mUidForegroundList.contains(Integer.valueOf(i))) {
      return;
    }
    this.mUidForegroundList.add(Integer.valueOf(i));
  }

  public final void removeFromUidForegroundService(int i) {
    this.mUidForegroundList.remove(Integer.valueOf(i));
  }

  public final void addToUidIdleList(int i) {
    if (this.mUidIdleList.contains(Integer.valueOf(i))) {
      return;
    }
    this.mUidIdleList.add(Integer.valueOf(i));
  }

  public final void removeFromUidIdleList(int i) {
    this.mUidIdleList.remove(Integer.valueOf(i));
  }

  public final void addToRunningList(int i) {
    if (this.mRunningList.contains(Integer.valueOf(i))) {
      return;
    }
    this.mRunningList.add(Integer.valueOf(i));
    notifyUidRunning(i);
  }

  public void notifyUidRunning(int i) {
    FreecessPkgStatus packageStatus = FreecessController.getInstance().getPackageStatus(i);
    if (packageStatus != null) {
      synchronized (MARsPolicyManager.MARsLock) {
        packageStatus.freezedRecord.mFreezeEventRecorder.onUidStart(System.currentTimeMillis());
      }
    }
  }

  public final void removeFromRunningList(int i) {
    if (this.mRunningList.remove(Integer.valueOf(i))) {
      notifyUidStopped(i);
    }
  }

  public void notifyUidStopped(int i) {
    FreecessPkgStatus packageStatus = FreecessController.getInstance().getPackageStatus(i);
    if (packageStatus != null) {
      synchronized (MARsPolicyManager.MARsLock) {
        packageStatus.freezedRecord.mFreezeEventRecorder.onUidStop(i, System.currentTimeMillis());
      }
    }
  }

  public final void addToUidCached(int i) {
    if (this.mUidCached.contains(Integer.valueOf(i))) {
      return;
    }
    this.mUidCached.add(Integer.valueOf(i));
  }

  public final void removeFromUidCached(int i) {
    this.mUidCached.remove(Integer.valueOf(i));
  }

  public final void addToUidGone(int i) {
    if (this.mUidGoneList.contains(Integer.valueOf(i))) {
      return;
    }
    this.mUidGoneList.add(Integer.valueOf(i));
  }

  public final void removeFromUidGone(int i) {
    this.mUidGoneList.remove(Integer.valueOf(i));
  }
}
