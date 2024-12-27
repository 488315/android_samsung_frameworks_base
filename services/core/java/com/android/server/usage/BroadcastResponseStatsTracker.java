package com.android.server.usage;

import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.app.usage.BroadcastResponseStats;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LongArrayQueue;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.usage.BroadcastResponseStatsLogger;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class BroadcastResponseStatsTracker {
    public final AppStandbyInternal mAppStandby;
    public final Context mContext;
    public RoleManager mRoleManager;
    public final Object mLock = new Object();
    public final SparseArray mUserBroadcastEvents = new SparseArray();
    public final SparseArray mUserResponseStats = new SparseArray();
    public final SparseArray mExemptedRoleHoldersCache = new SparseArray();
    public final BroadcastResponseStatsTracker$$ExternalSyntheticLambda0 mRoleHoldersChangedListener = new OnRoleHoldersChangedListener() { // from class: com.android.server.usage.BroadcastResponseStatsTracker$$ExternalSyntheticLambda0
        public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
            BroadcastResponseStatsTracker broadcastResponseStatsTracker = BroadcastResponseStatsTracker.this;
            synchronized (broadcastResponseStatsTracker.mLock) {
                try {
                    ArrayMap arrayMap = (ArrayMap) broadcastResponseStatsTracker.mExemptedRoleHoldersCache.get(userHandle.getIdentifier());
                    if (arrayMap == null) {
                        return;
                    }
                    arrayMap.remove(str);
                } finally {
                }
            }
        }
    };
    public final BroadcastResponseStatsLogger mLogger = new BroadcastResponseStatsLogger();

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.usage.BroadcastResponseStatsTracker$$ExternalSyntheticLambda0] */
    public BroadcastResponseStatsTracker(AppStandbyInternal appStandbyInternal, Context context) {
        this.mAppStandby = appStandbyInternal;
        this.mContext = context;
    }

    public final boolean doesPackageHoldExemptedPermission(String str, UserHandle userHandle) {
        try {
            int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(str, userHandle.getIdentifier());
            List broadcastResponseExemptedPermissions = this.mAppStandby.getBroadcastResponseExemptedPermissions();
            for (int size = broadcastResponseExemptedPermissions.size() - 1; size >= 0; size--) {
                if (this.mContext.checkPermission((String) broadcastResponseExemptedPermissions.get(size), -1, packageUidAsUser) == 0) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public final boolean doesPackageHoldExemptedRole(String str, UserHandle userHandle) {
        RoleManager roleManager;
        List broadcastResponseExemptedRoles = this.mAppStandby.getBroadcastResponseExemptedRoles();
        synchronized (this.mLock) {
            try {
                for (int size = broadcastResponseExemptedRoles.size() - 1; size >= 0; size--) {
                    String str2 = (String) broadcastResponseExemptedRoles.get(size);
                    ArrayMap arrayMap = (ArrayMap) this.mExemptedRoleHoldersCache.get(userHandle.getIdentifier());
                    if (arrayMap == null) {
                        arrayMap = new ArrayMap();
                        this.mExemptedRoleHoldersCache.put(userHandle.getIdentifier(), arrayMap);
                    }
                    List list = (List) arrayMap.get(str2);
                    if (list == null && (roleManager = this.mRoleManager) != null) {
                        list = roleManager.getRoleHoldersAsUser(str2, userHandle);
                        arrayMap.put(str2, list);
                    }
                    if (CollectionUtils.contains(list, str)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
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
            UserBroadcastEvents userBroadcastEvents = (UserBroadcastEvents) this.mUserBroadcastEvents.valueAt(i);
            indentingPrintWriter.println("User " + keyAt + ":");
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < userBroadcastEvents.mBroadcastEvents.size(); i2++) {
                String str = (String) userBroadcastEvents.mBroadcastEvents.keyAt(i2);
                ArraySet arraySet = (ArraySet) userBroadcastEvents.mBroadcastEvents.valueAt(i2);
                indentingPrintWriter.println(str + ":");
                indentingPrintWriter.increaseIndent();
                if (arraySet.size() == 0) {
                    indentingPrintWriter.println("<empty>");
                } else {
                    for (int i3 = 0; i3 < arraySet.size(); i3++) {
                        BroadcastEvent broadcastEvent = (BroadcastEvent) arraySet.valueAt(i3);
                        indentingPrintWriter.println(broadcastEvent);
                        indentingPrintWriter.increaseIndent();
                        LongArrayQueue longArrayQueue = broadcastEvent.mTimestampsMs;
                        for (int i4 = 0; i4 < longArrayQueue.size(); i4++) {
                            if (i4 > 0) {
                                indentingPrintWriter.print(',');
                            }
                            TimeUtils.formatDuration(longArrayQueue.get(i4), indentingPrintWriter);
                        }
                        indentingPrintWriter.println();
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.decreaseIndent();
            }
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
            indentingPrintWriter.println("Uid " + keyAt + ":");
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                int keyAt2 = sparseArray.keyAt(i2);
                UserBroadcastResponseStats userBroadcastResponseStats = (UserBroadcastResponseStats) sparseArray.valueAt(i2);
                indentingPrintWriter.println("User " + keyAt2 + ":");
                indentingPrintWriter.increaseIndent();
                for (int i3 = 0; i3 < userBroadcastResponseStats.mResponseStats.size(); i3++) {
                    BroadcastEvent broadcastEvent = (BroadcastEvent) userBroadcastResponseStats.mResponseStats.keyAt(i3);
                    BroadcastResponseStats broadcastResponseStats = (BroadcastResponseStats) userBroadcastResponseStats.mResponseStats.valueAt(i3);
                    indentingPrintWriter.print(broadcastEvent);
                    indentingPrintWriter.print(" -> ");
                    indentingPrintWriter.println(broadcastResponseStats);
                }
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
            indentingPrintWriter.println("User " + keyAt + ":");
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

    public final ArraySet getOrCreateBroadcastEventsLocked(String str, UserHandle userHandle) {
        UserBroadcastEvents userBroadcastEvents = (UserBroadcastEvents) this.mUserBroadcastEvents.get(userHandle.getIdentifier());
        if (userBroadcastEvents == null) {
            userBroadcastEvents = new UserBroadcastEvents();
            userBroadcastEvents.mBroadcastEvents = new ArrayMap();
            this.mUserBroadcastEvents.put(userHandle.getIdentifier(), userBroadcastEvents);
        }
        ArraySet arraySet = (ArraySet) userBroadcastEvents.mBroadcastEvents.get(str);
        if (arraySet != null) {
            return arraySet;
        }
        ArraySet arraySet2 = new ArraySet();
        userBroadcastEvents.mBroadcastEvents.put(str, arraySet2);
        return arraySet2;
    }

    public final BroadcastResponseStats getOrCreateBroadcastResponseStats(BroadcastEvent broadcastEvent) {
        int i = broadcastEvent.mSourceUid;
        SparseArray sparseArray = (SparseArray) this.mUserResponseStats.get(i);
        if (sparseArray == null) {
            sparseArray = new SparseArray();
            this.mUserResponseStats.put(i, sparseArray);
        }
        int i2 = broadcastEvent.mTargetUserId;
        UserBroadcastResponseStats userBroadcastResponseStats = (UserBroadcastResponseStats) sparseArray.get(i2);
        if (userBroadcastResponseStats == null) {
            userBroadcastResponseStats = new UserBroadcastResponseStats();
            userBroadcastResponseStats.mResponseStats = new ArrayMap();
            sparseArray.put(i2, userBroadcastResponseStats);
        }
        BroadcastResponseStats broadcastResponseStats = (BroadcastResponseStats) userBroadcastResponseStats.mResponseStats.get(broadcastEvent);
        if (broadcastResponseStats != null) {
            return broadcastResponseStats;
        }
        BroadcastResponseStats broadcastResponseStats2 = new BroadcastResponseStats(broadcastEvent.mTargetPackage, broadcastEvent.mIdForResponseEvent);
        userBroadcastResponseStats.mResponseStats.put(broadcastEvent, broadcastResponseStats2);
        return broadcastResponseStats2;
    }

    public final void onUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                this.mUserBroadcastEvents.remove(i);
                for (int size = this.mUserResponseStats.size() - 1; size >= 0; size--) {
                    ((SparseArray) this.mUserResponseStats.valueAt(size)).remove(i);
                }
                this.mExemptedRoleHoldersCache.remove(i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void recordAndPruneOldBroadcastDispatchTimestamps(BroadcastEvent broadcastEvent) {
        LongArrayQueue longArrayQueue = broadcastEvent.mTimestampsMs;
        long broadcastResponseWindowDurationMs = this.mAppStandby.getBroadcastResponseWindowDurationMs();
        long broadcastSessionsDurationMs = this.mAppStandby.getBroadcastSessionsDurationMs();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = 0;
        while (longArrayQueue.size() > 0 && longArrayQueue.peekFirst() < elapsedRealtime - broadcastResponseWindowDurationMs) {
            long peekFirst = longArrayQueue.peekFirst();
            if (peekFirst >= j) {
                getOrCreateBroadcastResponseStats(broadcastEvent).incrementBroadcastsDispatchedCount(1);
                j = peekFirst + broadcastSessionsDurationMs;
            }
            longArrayQueue.removeFirst();
        }
    }

    public final void reportNotificationEvent(int i, String str, UserHandle userHandle, long j) {
        int i2;
        BroadcastResponseStatsTracker broadcastResponseStatsTracker = this;
        BroadcastResponseStatsLogger broadcastResponseStatsLogger = broadcastResponseStatsTracker.mLogger;
        synchronized (broadcastResponseStatsLogger.mLock) {
            try {
                if (UsageStatsService.DEBUG_RESPONSE_STATS) {
                    Slog.d("ResponseStatsTracker", BroadcastResponseStatsLogger.getNotificationEventLog(i, userHandle.getIdentifier(), j, str));
                }
                BroadcastResponseStatsLogger.Data data = (BroadcastResponseStatsLogger.Data) broadcastResponseStatsLogger.mNotificationEventsBuffer.getNextSlot();
                if (data != null) {
                    data.reset();
                    BroadcastResponseStatsLogger.NotificationEvent notificationEvent = (BroadcastResponseStatsLogger.NotificationEvent) data;
                    notificationEvent.type = i;
                    notificationEvent.packageName = str;
                    notificationEvent.userId = userHandle.getIdentifier();
                    notificationEvent.timestampMs = j;
                }
            } finally {
            }
        }
        synchronized (broadcastResponseStatsTracker.mLock) {
            try {
                UserBroadcastEvents userBroadcastEvents = (UserBroadcastEvents) broadcastResponseStatsTracker.mUserBroadcastEvents.get(userHandle.getIdentifier());
                ArraySet arraySet = userBroadcastEvents == null ? null : (ArraySet) userBroadcastEvents.mBroadcastEvents.get(str);
                if (arraySet == null) {
                    return;
                }
                long broadcastResponseWindowDurationMs = broadcastResponseStatsTracker.mAppStandby.getBroadcastResponseWindowDurationMs();
                long broadcastSessionsWithResponseDurationMs = broadcastResponseStatsTracker.mAppStandby.getBroadcastSessionsWithResponseDurationMs();
                boolean shouldNoteResponseEventForAllBroadcastSessions = broadcastResponseStatsTracker.mAppStandby.shouldNoteResponseEventForAllBroadcastSessions();
                int i3 = 1;
                int size = arraySet.size() - 1;
                while (size >= 0) {
                    BroadcastEvent broadcastEvent = (BroadcastEvent) arraySet.valueAt(size);
                    broadcastResponseStatsTracker.recordAndPruneOldBroadcastDispatchTimestamps(broadcastEvent);
                    LongArrayQueue longArrayQueue = broadcastEvent.mTimestampsMs;
                    long j2 = 0;
                    long j3 = 0;
                    while (longArrayQueue.size() > 0 && longArrayQueue.peekFirst() <= j) {
                        long peekFirst = longArrayQueue.peekFirst();
                        if (j - peekFirst <= broadcastResponseWindowDurationMs && peekFirst >= j3) {
                            if (j3 != j2 && !shouldNoteResponseEventForAllBroadcastSessions) {
                                break;
                            }
                            BroadcastResponseStats orCreateBroadcastResponseStats = broadcastResponseStatsTracker.getOrCreateBroadcastResponseStats(broadcastEvent);
                            orCreateBroadcastResponseStats.incrementBroadcastsDispatchedCount(i3);
                            j3 = peekFirst + broadcastSessionsWithResponseDurationMs;
                            if (i == 0) {
                                i2 = i3;
                                orCreateBroadcastResponseStats.incrementNotificationsPostedCount(i2);
                            } else if (i == i3) {
                                i2 = i3;
                                orCreateBroadcastResponseStats.incrementNotificationsUpdatedCount(i2);
                            } else if (i != 2) {
                                Slog.wtf("ResponseStatsTracker", "Unknown event: " + i);
                                i2 = 1;
                            } else {
                                i2 = 1;
                                orCreateBroadcastResponseStats.incrementNotificationsCancelledCount(1);
                            }
                        } else {
                            i2 = i3;
                        }
                        longArrayQueue.removeFirst();
                        i3 = i2;
                        j2 = 0;
                        broadcastResponseStatsTracker = this;
                    }
                    int i4 = i3;
                    if (longArrayQueue.size() == 0) {
                        arraySet.removeAt(size);
                    }
                    size--;
                    i3 = i4;
                    broadcastResponseStatsTracker = this;
                }
            } finally {
            }
        }
    }
}
