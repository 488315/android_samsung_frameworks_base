package com.android.server.usage;

import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.app.usage.BroadcastResponseStats;
import android.content.Context;
import android.os.SystemClock;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LongArrayQueue;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.XmlUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class BroadcastResponseStatsTracker {
  public AppStandbyInternal mAppStandby;
  public RoleManager mRoleManager;
  public final Object mLock = new Object();
  public SparseArray mUserBroadcastEvents = new SparseArray();
  public SparseArray mUserResponseStats = new SparseArray();
  public SparseArray mExemptedRoleHoldersCache = new SparseArray();
  public final OnRoleHoldersChangedListener mRoleHoldersChangedListener =
      new OnRoleHoldersChangedListener() { // from class:
                                           // com.android.server.usage.BroadcastResponseStatsTracker$$ExternalSyntheticLambda0
        public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
          BroadcastResponseStatsTracker.this.onRoleHoldersChanged(str, userHandle);
        }
      };
  public BroadcastResponseStatsLogger mLogger = new BroadcastResponseStatsLogger();

  public BroadcastResponseStatsTracker(AppStandbyInternal appStandbyInternal) {
    this.mAppStandby = appStandbyInternal;
  }

  public void onSystemServicesReady(Context context) {
    RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
    this.mRoleManager = roleManager;
    roleManager.addOnRoleHoldersChangedListenerAsUser(
        BackgroundThread.getExecutor(), this.mRoleHoldersChangedListener, UserHandle.ALL);
  }

  public void reportBroadcastDispatchEvent(
      int i, String str, UserHandle userHandle, long j, long j2, int i2) {
    this.mLogger.logBroadcastDispatchEvent(i, str, userHandle, j, j2, i2);
    if (i2 <= this.mAppStandby.getBroadcastResponseFgThresholdState()
        || doesPackageHoldExemptedRole(str, userHandle)
        || doesPackageHoldExemptedPermission(str, userHandle)) {
      return;
    }
    synchronized (this.mLock) {
      BroadcastEvent orCreateBroadcastEvent =
          getOrCreateBroadcastEvent(
              getOrCreateBroadcastEventsLocked(str, userHandle),
              i,
              str,
              userHandle.getIdentifier(),
              j);
      orCreateBroadcastEvent.addTimestampMs(j2);
      recordAndPruneOldBroadcastDispatchTimestamps(orCreateBroadcastEvent);
    }
  }

  public void reportNotificationPosted(String str, UserHandle userHandle, long j) {
    reportNotificationEvent(0, str, userHandle, j);
  }

  public void reportNotificationUpdated(String str, UserHandle userHandle, long j) {
    reportNotificationEvent(1, str, userHandle, j);
  }

  public void reportNotificationCancelled(String str, UserHandle userHandle, long j) {
    reportNotificationEvent(2, str, userHandle, j);
  }

  public final void reportNotificationEvent(int i, String str, UserHandle userHandle, long j) {
    this.mLogger.logNotificationEvent(i, str, userHandle, j);
    synchronized (this.mLock) {
      ArraySet broadcastEventsLocked = getBroadcastEventsLocked(str, userHandle);
      if (broadcastEventsLocked == null) {
        return;
      }
      long broadcastResponseWindowDurationMs =
          this.mAppStandby.getBroadcastResponseWindowDurationMs();
      long broadcastSessionsWithResponseDurationMs =
          this.mAppStandby.getBroadcastSessionsWithResponseDurationMs();
      boolean shouldNoteResponseEventForAllBroadcastSessions =
          this.mAppStandby.shouldNoteResponseEventForAllBroadcastSessions();
      for (int size = broadcastEventsLocked.size() - 1; size >= 0; size--) {
        BroadcastEvent broadcastEvent = (BroadcastEvent) broadcastEventsLocked.valueAt(size);
        recordAndPruneOldBroadcastDispatchTimestamps(broadcastEvent);
        LongArrayQueue timestampsMs = broadcastEvent.getTimestampsMs();
        long j2 = 0;
        long j3 = 0;
        while (timestampsMs.size() > 0 && timestampsMs.peekFirst() < j) {
          long peekFirst = timestampsMs.peekFirst();
          if (j - peekFirst <= broadcastResponseWindowDurationMs && peekFirst >= j3) {
            if (j3 != j2 && !shouldNoteResponseEventForAllBroadcastSessions) {
              break;
            }
            BroadcastResponseStats orCreateBroadcastResponseStats =
                getOrCreateBroadcastResponseStats(broadcastEvent);
            orCreateBroadcastResponseStats.incrementBroadcastsDispatchedCount(1);
            long j4 = peekFirst + broadcastSessionsWithResponseDurationMs;
            if (i == 0) {
              orCreateBroadcastResponseStats.incrementNotificationsPostedCount(1);
            } else if (i == 1) {
              orCreateBroadcastResponseStats.incrementNotificationsUpdatedCount(1);
            } else if (i == 2) {
              orCreateBroadcastResponseStats.incrementNotificationsCancelledCount(1);
            } else {
              Slog.wtf("ResponseStatsTracker", "Unknown event: " + i);
            }
            j3 = j4;
          }
          timestampsMs.removeFirst();
          j2 = 0;
        }
        if (timestampsMs.size() == 0) {
          broadcastEventsLocked.removeAt(size);
        }
      }
    }
  }

  public final void recordAndPruneOldBroadcastDispatchTimestamps(BroadcastEvent broadcastEvent) {
    LongArrayQueue timestampsMs = broadcastEvent.getTimestampsMs();
    long broadcastResponseWindowDurationMs =
        this.mAppStandby.getBroadcastResponseWindowDurationMs();
    long broadcastSessionsDurationMs = this.mAppStandby.getBroadcastSessionsDurationMs();
    long elapsedRealtime = SystemClock.elapsedRealtime();
    long j = 0;
    while (timestampsMs.size() > 0
        && timestampsMs.peekFirst() < elapsedRealtime - broadcastResponseWindowDurationMs) {
      long peekFirst = timestampsMs.peekFirst();
      if (peekFirst >= j) {
        getOrCreateBroadcastResponseStats(broadcastEvent).incrementBroadcastsDispatchedCount(1);
        j = peekFirst + broadcastSessionsDurationMs;
      }
      timestampsMs.removeFirst();
    }
  }

  public List queryBroadcastResponseStats(int i, String str, long j, int i2) {
    ArrayList arrayList = new ArrayList();
    synchronized (this.mLock) {
      SparseArray sparseArray = (SparseArray) this.mUserResponseStats.get(i);
      if (sparseArray == null) {
        return arrayList;
      }
      UserBroadcastResponseStats userBroadcastResponseStats =
          (UserBroadcastResponseStats) sparseArray.get(i2);
      if (userBroadcastResponseStats == null) {
        return arrayList;
      }
      userBroadcastResponseStats.populateAllBroadcastResponseStats(arrayList, str, j);
      return arrayList;
    }
  }

  public void clearBroadcastResponseStats(int i, String str, long j, int i2) {
    synchronized (this.mLock) {
      SparseArray sparseArray = (SparseArray) this.mUserResponseStats.get(i);
      if (sparseArray == null) {
        return;
      }
      UserBroadcastResponseStats userBroadcastResponseStats =
          (UserBroadcastResponseStats) sparseArray.get(i2);
      if (userBroadcastResponseStats == null) {
        return;
      }
      userBroadcastResponseStats.clearBroadcastResponseStats(str, j);
    }
  }

  public void clearBroadcastEvents(int i, int i2) {
    synchronized (this.mLock) {
      UserBroadcastEvents userBroadcastEvents =
          (UserBroadcastEvents) this.mUserBroadcastEvents.get(i2);
      if (userBroadcastEvents == null) {
        return;
      }
      userBroadcastEvents.clear(i);
    }
  }

  public boolean doesPackageHoldExemptedRole(String str, UserHandle userHandle) {
    List broadcastResponseExemptedRoles = this.mAppStandby.getBroadcastResponseExemptedRoles();
    synchronized (this.mLock) {
      for (int size = broadcastResponseExemptedRoles.size() - 1; size >= 0; size--) {
        if (CollectionUtils.contains(
            getRoleHoldersLocked((String) broadcastResponseExemptedRoles.get(size), userHandle),
            str)) {
          return true;
        }
      }
      return false;
    }
  }

  public boolean doesPackageHoldExemptedPermission(String str, UserHandle userHandle) {
    List broadcastResponseExemptedPermissions =
        this.mAppStandby.getBroadcastResponseExemptedPermissions();
    for (int size = broadcastResponseExemptedPermissions.size() - 1; size >= 0; size--) {
      if (PermissionManager.checkPackageNamePermission(
              (String) broadcastResponseExemptedPermissions.get(size),
              str,
              userHandle.getIdentifier())
          == 0) {
        return true;
      }
    }
    return false;
  }

  public final List getRoleHoldersLocked(String str, UserHandle userHandle) {
    RoleManager roleManager;
    ArrayMap arrayMap = (ArrayMap) this.mExemptedRoleHoldersCache.get(userHandle.getIdentifier());
    if (arrayMap == null) {
      arrayMap = new ArrayMap();
      this.mExemptedRoleHoldersCache.put(userHandle.getIdentifier(), arrayMap);
    }
    List list = (List) arrayMap.get(str);
    if (list != null || (roleManager = this.mRoleManager) == null) {
      return list;
    }
    List roleHoldersAsUser = roleManager.getRoleHoldersAsUser(str, userHandle);
    arrayMap.put(str, roleHoldersAsUser);
    return roleHoldersAsUser;
  }

  public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
    synchronized (this.mLock) {
      ArrayMap arrayMap = (ArrayMap) this.mExemptedRoleHoldersCache.get(userHandle.getIdentifier());
      if (arrayMap == null) {
        return;
      }
      arrayMap.remove(str);
    }
  }

  public void onUserRemoved(int i) {
    synchronized (this.mLock) {
      this.mUserBroadcastEvents.remove(i);
      for (int size = this.mUserResponseStats.size() - 1; size >= 0; size--) {
        ((SparseArray) this.mUserResponseStats.valueAt(size)).remove(i);
      }
      this.mExemptedRoleHoldersCache.remove(i);
    }
  }

  public void onPackageRemoved(String str, int i) {
    synchronized (this.mLock) {
      UserBroadcastEvents userBroadcastEvents =
          (UserBroadcastEvents) this.mUserBroadcastEvents.get(i);
      if (userBroadcastEvents != null) {
        userBroadcastEvents.onPackageRemoved(str);
      }
      for (int size = this.mUserResponseStats.size() - 1; size >= 0; size--) {
        UserBroadcastResponseStats userBroadcastResponseStats =
            (UserBroadcastResponseStats)
                ((SparseArray) this.mUserResponseStats.valueAt(size)).get(i);
        if (userBroadcastResponseStats != null) {
          userBroadcastResponseStats.onPackageRemoved(str);
        }
      }
    }
  }

  public void onUidRemoved(int i) {
    synchronized (this.mLock) {
      for (int size = this.mUserBroadcastEvents.size() - 1; size >= 0; size--) {
        ((UserBroadcastEvents) this.mUserBroadcastEvents.valueAt(size)).onUidRemoved(i);
      }
      this.mUserResponseStats.remove(i);
    }
  }

  public final ArraySet getBroadcastEventsLocked(String str, UserHandle userHandle) {
    UserBroadcastEvents userBroadcastEvents =
        (UserBroadcastEvents) this.mUserBroadcastEvents.get(userHandle.getIdentifier());
    if (userBroadcastEvents == null) {
      return null;
    }
    return userBroadcastEvents.getBroadcastEvents(str);
  }

  public final ArraySet getOrCreateBroadcastEventsLocked(String str, UserHandle userHandle) {
    UserBroadcastEvents userBroadcastEvents =
        (UserBroadcastEvents) this.mUserBroadcastEvents.get(userHandle.getIdentifier());
    if (userBroadcastEvents == null) {
      userBroadcastEvents = new UserBroadcastEvents();
      this.mUserBroadcastEvents.put(userHandle.getIdentifier(), userBroadcastEvents);
    }
    return userBroadcastEvents.getOrCreateBroadcastEvents(str);
  }

  public final BroadcastResponseStats getOrCreateBroadcastResponseStats(
      BroadcastEvent broadcastEvent) {
    int sourceUid = broadcastEvent.getSourceUid();
    SparseArray sparseArray = (SparseArray) this.mUserResponseStats.get(sourceUid);
    if (sparseArray == null) {
      sparseArray = new SparseArray();
      this.mUserResponseStats.put(sourceUid, sparseArray);
    }
    UserBroadcastResponseStats userBroadcastResponseStats =
        (UserBroadcastResponseStats) sparseArray.get(broadcastEvent.getTargetUserId());
    if (userBroadcastResponseStats == null) {
      userBroadcastResponseStats = new UserBroadcastResponseStats();
      sparseArray.put(broadcastEvent.getTargetUserId(), userBroadcastResponseStats);
    }
    return userBroadcastResponseStats.getOrCreateBroadcastResponseStats(broadcastEvent);
  }

  public static BroadcastEvent getOrCreateBroadcastEvent(
      ArraySet arraySet, int i, String str, int i2, long j) {
    BroadcastEvent broadcastEvent = new BroadcastEvent(i, str, i2, j);
    int indexOf = arraySet.indexOf(broadcastEvent);
    if (indexOf >= 0) {
      return (BroadcastEvent) arraySet.valueAt(indexOf);
    }
    arraySet.add(broadcastEvent);
    return broadcastEvent;
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("Broadcast response stats:");
    indentingPrintWriter.increaseIndent();
    synchronized (this.mLock) {
      dumpBroadcastEventsLocked(indentingPrintWriter);
      indentingPrintWriter.println();
      dumpResponseStatsLocked(indentingPrintWriter);
      indentingPrintWriter.println();
      dumpRoleHoldersLocked(indentingPrintWriter);
      indentingPrintWriter.println();
      this.mLogger.dumpLogs(indentingPrintWriter);
    }
    indentingPrintWriter.decreaseIndent();
  }

  public final void dumpBroadcastEventsLocked(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("Broadcast events:");
    indentingPrintWriter.increaseIndent();
    for (int i = 0; i < this.mUserBroadcastEvents.size(); i++) {
      int keyAt = this.mUserBroadcastEvents.keyAt(i);
      UserBroadcastEvents userBroadcastEvents =
          (UserBroadcastEvents) this.mUserBroadcastEvents.valueAt(i);
      indentingPrintWriter.println("User " + keyAt + XmlUtils.STRING_ARRAY_SEPARATOR);
      indentingPrintWriter.increaseIndent();
      userBroadcastEvents.dump(indentingPrintWriter);
      indentingPrintWriter.decreaseIndent();
    }
    indentingPrintWriter.decreaseIndent();
  }

  public final void dumpResponseStatsLocked(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("Response stats:");
    indentingPrintWriter.increaseIndent();
    for (int i = 0; i < this.mUserResponseStats.size(); i++) {
      int keyAt = this.mUserResponseStats.keyAt(i);
      SparseArray sparseArray = (SparseArray) this.mUserResponseStats.valueAt(i);
      indentingPrintWriter.println("Uid " + keyAt + XmlUtils.STRING_ARRAY_SEPARATOR);
      indentingPrintWriter.increaseIndent();
      for (int i2 = 0; i2 < sparseArray.size(); i2++) {
        int keyAt2 = sparseArray.keyAt(i2);
        UserBroadcastResponseStats userBroadcastResponseStats =
            (UserBroadcastResponseStats) sparseArray.valueAt(i2);
        indentingPrintWriter.println("User " + keyAt2 + XmlUtils.STRING_ARRAY_SEPARATOR);
        indentingPrintWriter.increaseIndent();
        userBroadcastResponseStats.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
      }
      indentingPrintWriter.decreaseIndent();
    }
    indentingPrintWriter.decreaseIndent();
  }

  public final void dumpRoleHoldersLocked(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("Role holders:");
    indentingPrintWriter.increaseIndent();
    for (int i = 0; i < this.mExemptedRoleHoldersCache.size(); i++) {
      int keyAt = this.mExemptedRoleHoldersCache.keyAt(i);
      ArrayMap arrayMap = (ArrayMap) this.mExemptedRoleHoldersCache.valueAt(i);
      indentingPrintWriter.println("User " + keyAt + XmlUtils.STRING_ARRAY_SEPARATOR);
      indentingPrintWriter.increaseIndent();
      for (int i2 = 0; i2 < arrayMap.size(); i2++) {
        String str = (String) arrayMap.keyAt(i2);
        List list = (List) arrayMap.valueAt(i2);
        indentingPrintWriter.print(str + ": ");
        for (int i3 = 0; i3 < list.size(); i3++) {
          if (i3 > 0) {
            indentingPrintWriter.print(", ");
          }
          indentingPrintWriter.print((String) list.get(i3));
        }
        indentingPrintWriter.println();
      }
      indentingPrintWriter.decreaseIndent();
    }
    indentingPrintWriter.decreaseIndent();
  }
}
