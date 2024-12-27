package com.android.server.am;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.IProcessObserver;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Icon;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerExemptionManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.am.AppRestrictionController;
import com.android.server.am.BaseAppStateDurationsTracker;
import com.android.server.am.BaseAppStateEventsTracker;
import com.android.server.am.BaseAppStateTracker;
import com.android.server.am.MARsHandler;
import com.android.server.backup.BackupManagerConstants;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppFGSTracker extends BaseAppStateDurationsTracker implements ActivityManagerInternal.ForegroundServiceStateListener {
    public final UidProcessMap mFGSNotificationIDs;
    public final MyHandler mHandler;
    final NotificationListener mNotificationListener;
    public final AnonymousClass1 mProcessObserver;
    public final ArrayMap mTmpPkgDurations;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppFGSPolicy extends BaseAppStateEventsTracker.BaseAppStateEventsPolicy {
        public volatile long mBgFgsLocationThresholdMs;
        public volatile long mBgFgsLongRunningThresholdMs;
        public volatile long mBgFgsMediaPlaybackThresholdMs;

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        public final void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.println("APP FOREGROUND SERVICE TRACKER POLICY SETTINGS:");
            String str2 = "  " + str;
            super.dump(printWriter, str2);
            if (this.mTrackerEnabled) {
                printWriter.print(str2);
                printWriter.print("bg_fgs_long_running_threshold");
                printWriter.print('=');
                ActivityManagerConstants$$ExternalSyntheticOutline0.m(this.mBgFgsLongRunningThresholdMs, printWriter, str2, "bg_fgs_media_playback_threshold");
                printWriter.print('=');
                ActivityManagerConstants$$ExternalSyntheticOutline0.m(this.mBgFgsMediaPlaybackThresholdMs, printWriter, str2, "bg_fgs_location_threshold");
                printWriter.print('=');
                printWriter.println(this.mBgFgsLocationThresholdMs);
            }
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public final String getExemptionReasonString(int i, int i2, String str) {
            if (i2 != -1) {
                return PowerExemptionManager.reasonCodeToString(i2);
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j = this.mMaxTrackingDuration;
            long max = Math.max(0L, elapsedRealtime - this.mMaxTrackingDuration);
            StringBuilder sb = new StringBuilder("{mediaPlayback=");
            sb.append(shouldExemptMediaPlaybackFGS(i, elapsedRealtime, j, str));
            sb.append(", location=");
            long totalDurationsSince = ((AppFGSTracker) this.mTracker).mAppRestrictionController.mInjector.mAppFGSTracker.getTotalDurationsSince(i, AppFGSTracker.foregroundServiceTypeToIndex(8), str, max, elapsedRealtime);
            return OptionalBool$$ExternalSyntheticOutline0.m("}", sb, totalDurationsSince > 0 && totalDurationsSince >= this.mBgFgsLocationThresholdMs);
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public final void onMaxTrackingDurationChanged() {
            AppFGSTracker.m175$$Nest$monBgFgsLongRunningThresholdChanged((AppFGSTracker) this.mTracker);
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        public final void onPropertiesChanged(String str) {
            switch (str) {
                case "bg_fgs_location_threshold":
                    this.mBgFgsLocationThresholdMs = DeviceConfig.getLong("activity_manager", "bg_fgs_location_threshold", BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
                    break;
                case "bg_fgs_long_running_threshold":
                    long j = DeviceConfig.getLong("activity_manager", "bg_fgs_long_running_threshold", 72000000L);
                    if (j != this.mBgFgsLongRunningThresholdMs) {
                        this.mBgFgsLongRunningThresholdMs = j;
                        AppFGSTracker.m175$$Nest$monBgFgsLongRunningThresholdChanged((AppFGSTracker) this.mTracker);
                        break;
                    }
                    break;
                case "bg_fgs_media_playback_threshold":
                    this.mBgFgsMediaPlaybackThresholdMs = DeviceConfig.getLong("activity_manager", "bg_fgs_media_playback_threshold", BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
                    break;
                default:
                    super.onPropertiesChanged(str);
                    break;
            }
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        public final void onSystemReady() {
            super.onSystemReady();
            long j = DeviceConfig.getLong("activity_manager", "bg_fgs_long_running_threshold", 72000000L);
            if (j != this.mBgFgsLongRunningThresholdMs) {
                this.mBgFgsLongRunningThresholdMs = j;
                AppFGSTracker.m175$$Nest$monBgFgsLongRunningThresholdChanged((AppFGSTracker) this.mTracker);
            }
            this.mBgFgsMediaPlaybackThresholdMs = DeviceConfig.getLong("activity_manager", "bg_fgs_media_playback_threshold", BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
            this.mBgFgsLocationThresholdMs = DeviceConfig.getLong("activity_manager", "bg_fgs_location_threshold", BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void onTrackerEnabled(boolean z) {
            AppFGSTracker appFGSTracker = (AppFGSTracker) this.mTracker;
            appFGSTracker.getClass();
            if (z) {
                synchronized (appFGSTracker.mLock) {
                    appFGSTracker.scheduleDurationCheckLocked(SystemClock.elapsedRealtime());
                }
                try {
                    appFGSTracker.mNotificationListener.registerAsSystemService(appFGSTracker.mContext, new ComponentName(appFGSTracker.mContext, (Class<?>) NotificationListener.class), -1);
                    return;
                } catch (RemoteException unused) {
                    return;
                }
            }
            try {
                appFGSTracker.mNotificationListener.unregisterAsSystemService();
            } catch (RemoteException unused2) {
            }
            appFGSTracker.mHandler.removeMessages(4);
            synchronized (appFGSTracker.mLock) {
                appFGSTracker.mPkgEvents.clear();
            }
        }

        public final boolean shouldExemptMediaPlaybackFGS(int i, long j, long j2, String str) {
            AppRestrictionController appRestrictionController = ((AppFGSTracker) this.mTracker).mAppRestrictionController;
            appRestrictionController.getClass();
            long max = Math.max(0L, j - j2);
            long max2 = Math.max(appRestrictionController.mInjector.mAppMediaSessionTracker.getTotalDurationsSince(i, 0, str, max, j), appRestrictionController.mInjector.mAppFGSTracker.getTotalDurationsSince(i, AppFGSTracker.foregroundServiceTypeToIndex(2), str, max, j));
            return max2 > 0 && max2 >= this.mBgFgsMediaPlaybackThresholdMs;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHandler extends Handler {
        public final AppFGSTracker mTracker;

        public MyHandler(AppFGSTracker appFGSTracker) {
            super(appFGSTracker.mBgHandler.getLooper());
            this.mTracker = appFGSTracker;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int indexOfKey;
            ArrayMap arrayMap;
            int indexOfKey2;
            int indexOfKey3;
            boolean z = false;
            switch (message.what) {
                case 0:
                    AppFGSTracker.m174$$Nest$mhandleForegroundServicesChanged(this.mTracker, (String) message.obj, message.arg2, true);
                    return;
                case 1:
                    AppFGSTracker.m174$$Nest$mhandleForegroundServicesChanged(this.mTracker, (String) message.obj, message.arg2, false);
                    return;
                case 2:
                    AppFGSTracker appFGSTracker = this.mTracker;
                    String str = (String) message.obj;
                    int i = message.arg1;
                    int i2 = message.arg2;
                    if (((AppFGSPolicy) appFGSTracker.mInjector.mAppStatePolicy).mTrackerEnabled) {
                        int backgroundRestrictionExemptionReason = ((AppFGSPolicy) appFGSTracker.mInjector.mAppStatePolicy).mTracker.mAppRestrictionController.getBackgroundRestrictionExemptionReason(i);
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        synchronized (appFGSTracker.mLock) {
                            try {
                                PackageDurations packageDurations = (PackageDurations) appFGSTracker.mPkgEvents.get(i, str);
                                if (packageDurations == null) {
                                    packageDurations = new PackageDurations(i, str, (BaseAppStateEventsTracker.BaseAppStateEventsPolicy) appFGSTracker.mInjector.mAppStatePolicy, appFGSTracker);
                                    appFGSTracker.mPkgEvents.put(str, i, packageDurations);
                                }
                                packageDurations.setForegroundServiceType(i2, elapsedRealtime);
                                packageDurations.mExemptReason = backgroundRestrictionExemptionReason;
                            } finally {
                            }
                        }
                        return;
                    }
                    return;
                case 3:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    AppFGSTracker appFGSTracker2 = this.mTracker;
                    String str2 = (String) someArgs.arg1;
                    int i3 = someArgs.argi1;
                    int i4 = someArgs.argi2;
                    boolean booleanValue = ((Boolean) someArgs.arg2).booleanValue();
                    synchronized (appFGSTracker2.mLock) {
                        try {
                            SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) appFGSTracker2.mFGSNotificationIDs.get(i3, str2);
                            if (!booleanValue) {
                                if (sparseBooleanArray == null) {
                                    sparseBooleanArray = new SparseBooleanArray();
                                    appFGSTracker2.mFGSNotificationIDs.put(str2, i3, sparseBooleanArray);
                                }
                                sparseBooleanArray.put(i4, false);
                            } else if (sparseBooleanArray != null && (indexOfKey = sparseBooleanArray.indexOfKey(i4)) >= 0) {
                                boolean valueAt = sparseBooleanArray.valueAt(indexOfKey);
                                sparseBooleanArray.removeAt(indexOfKey);
                                if (sparseBooleanArray.size() == 0) {
                                    UidProcessMap uidProcessMap = appFGSTracker2.mFGSNotificationIDs;
                                    int indexOfKey4 = uidProcessMap.mMap.indexOfKey(i3);
                                    if (indexOfKey4 >= 0 && (arrayMap = (ArrayMap) uidProcessMap.mMap.valueAt(indexOfKey4)) != null) {
                                        arrayMap.remove(str2);
                                        if (arrayMap.isEmpty()) {
                                            uidProcessMap.mMap.removeAt(indexOfKey4);
                                        }
                                    }
                                }
                                for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
                                    if (!sparseBooleanArray.valueAt(size)) {
                                    }
                                }
                                if (valueAt) {
                                    appFGSTracker2.notifyListenersOnStateChange(i3, 8, SystemClock.elapsedRealtime(), str2, false);
                                }
                            }
                        } finally {
                        }
                    }
                    someArgs.recycle();
                    return;
                case 4:
                    AppFGSTracker.m173$$Nest$mcheckLongRunningFgs(this.mTracker);
                    return;
                case 5:
                    AppFGSTracker appFGSTracker3 = this.mTracker;
                    String str3 = (String) message.obj;
                    int i5 = message.arg1;
                    int i6 = message.arg2;
                    synchronized (appFGSTracker3.mLock) {
                        try {
                            SparseBooleanArray sparseBooleanArray2 = (SparseBooleanArray) appFGSTracker3.mFGSNotificationIDs.get(i5, str3);
                            if (sparseBooleanArray2 != null && (indexOfKey2 = sparseBooleanArray2.indexOfKey(i6)) >= 0) {
                                if (sparseBooleanArray2.valueAt(indexOfKey2)) {
                                    return;
                                }
                                int size2 = sparseBooleanArray2.size() - 1;
                                while (true) {
                                    if (size2 >= 0) {
                                        if (sparseBooleanArray2.valueAt(size2)) {
                                            z = true;
                                        } else {
                                            size2--;
                                        }
                                    }
                                }
                                sparseBooleanArray2.setValueAt(indexOfKey2, true);
                                if (!z) {
                                    appFGSTracker3.notifyListenersOnStateChange(i5, 8, SystemClock.elapsedRealtime(), str3, true);
                                }
                                return;
                            }
                            return;
                        } finally {
                        }
                    }
                case 6:
                    AppFGSTracker appFGSTracker4 = this.mTracker;
                    String str4 = (String) message.obj;
                    int i7 = message.arg1;
                    int i8 = message.arg2;
                    synchronized (appFGSTracker4.mLock) {
                        try {
                            SparseBooleanArray sparseBooleanArray3 = (SparseBooleanArray) appFGSTracker4.mFGSNotificationIDs.get(i7, str4);
                            if (sparseBooleanArray3 != null && (indexOfKey3 = sparseBooleanArray3.indexOfKey(i8)) >= 0) {
                                if (sparseBooleanArray3.valueAt(indexOfKey3)) {
                                    sparseBooleanArray3.setValueAt(indexOfKey3, false);
                                    for (int size3 = sparseBooleanArray3.size() - 1; size3 >= 0; size3--) {
                                        if (sparseBooleanArray3.valueAt(size3)) {
                                            return;
                                        }
                                    }
                                    appFGSTracker4.notifyListenersOnStateChange(i7, 8, SystemClock.elapsedRealtime(), str4, false);
                                    return;
                                }
                                return;
                            }
                            return;
                        } finally {
                        }
                    }
                default:
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class NotificationListener extends NotificationListenerService {
        public NotificationListener() {
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
            AppFGSTracker.this.mHandler.obtainMessage(5, statusBarNotification.getUid(), statusBarNotification.getId(), statusBarNotification.getPackageName()).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
            AppFGSTracker.this.mHandler.obtainMessage(6, statusBarNotification.getUid(), statusBarNotification.getId(), statusBarNotification.getPackageName()).sendToTarget();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageDurations extends BaseAppStateDurations {
        public static final int DEFAULT_INDEX = AppFGSTracker.foregroundServiceTypeToIndex(0);
        public int mForegroundServiceTypes;
        public boolean mIsLongRunning;
        public final AppFGSTracker mTracker;

        public PackageDurations(int i, String str, BaseAppStateEventsTracker.BaseAppStateEventsPolicy baseAppStateEventsPolicy, AppFGSTracker appFGSTracker) {
            super(i, str, 31, baseAppStateEventsPolicy);
            this.mEvents[DEFAULT_INDEX] = new LinkedList();
            this.mTracker = appFGSTracker;
        }

        public PackageDurations(PackageDurations packageDurations) {
            super(packageDurations);
            this.mIsLongRunning = packageDurations.mIsLongRunning;
            this.mForegroundServiceTypes = packageDurations.mForegroundServiceTypes;
            this.mTracker = packageDurations.mTracker;
        }

        public final void addEvent(long j, boolean z) {
            BaseAppStateTimeEvents$BaseTimeEvent baseAppStateTimeEvents$BaseTimeEvent = new BaseAppStateTimeEvents$BaseTimeEvent(j);
            int i = DEFAULT_INDEX;
            addEvent(z, baseAppStateTimeEvents$BaseTimeEvent, i);
            if (!z && !isActive(i)) {
                this.mIsLongRunning = false;
            }
            if (z || this.mForegroundServiceTypes == 0) {
                return;
            }
            int i2 = 1;
            while (true) {
                LinkedList[] linkedListArr = this.mEvents;
                if (i2 >= linkedListArr.length) {
                    this.mForegroundServiceTypes = 0;
                    return;
                }
                if (linkedListArr[i2] != null && isActive(i2)) {
                    this.mEvents[i2].add(new BaseAppStateTimeEvents$BaseTimeEvent(j));
                    notifyListenersOnStateChangeIfNecessary(i2 == i ? 0 : 1 << (i2 - 1), j, false);
                }
                i2++;
            }
        }

        @Override // com.android.server.am.BaseAppStateEvents
        public final String formatEventTypeLabel(int i) {
            int i2 = DEFAULT_INDEX;
            if (i == i2) {
                return "Overall foreground services: ";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(ServiceInfo.foregroundServiceTypeToLabel(i == i2 ? 0 : 1 << (i - 1)));
            sb.append(": ");
            return sb.toString();
        }

        public final void notifyListenersOnStateChangeIfNecessary(int i, long j, boolean z) {
            int i2 = 2;
            if (i != 2) {
                if (i != 8) {
                    return;
                } else {
                    i2 = 4;
                }
            }
            String str = this.mPackageName;
            this.mTracker.notifyListenersOnStateChange(this.mUid, i2, j, str, z);
        }

        public final void setForegroundServiceType(int i, long j) {
            if (i == this.mForegroundServiceTypes || !isActive(DEFAULT_INDEX)) {
                return;
            }
            int i2 = this.mForegroundServiceTypes ^ i;
            int highestOneBit = Integer.highestOneBit(i2);
            while (highestOneBit != 0) {
                int foregroundServiceTypeToIndex = AppFGSTracker.foregroundServiceTypeToIndex(highestOneBit);
                LinkedList[] linkedListArr = this.mEvents;
                if (foregroundServiceTypeToIndex < linkedListArr.length) {
                    if ((i & highestOneBit) != 0) {
                        if (linkedListArr[foregroundServiceTypeToIndex] == null) {
                            linkedListArr[foregroundServiceTypeToIndex] = new LinkedList();
                        }
                        if (!isActive(foregroundServiceTypeToIndex)) {
                            this.mEvents[foregroundServiceTypeToIndex].add(new BaseAppStateTimeEvents$BaseTimeEvent(j));
                            notifyListenersOnStateChangeIfNecessary(highestOneBit, j, true);
                        }
                    } else if (linkedListArr[foregroundServiceTypeToIndex] != null && isActive(foregroundServiceTypeToIndex)) {
                        this.mEvents[foregroundServiceTypeToIndex].add(new BaseAppStateTimeEvents$BaseTimeEvent(j));
                        notifyListenersOnStateChangeIfNecessary(highestOneBit, j, false);
                    }
                }
                i2 &= ~highestOneBit;
                highestOneBit = Integer.highestOneBit(i2);
            }
            this.mForegroundServiceTypes = i;
        }
    }

    /* renamed from: -$$Nest$mcheckLongRunningFgs, reason: not valid java name */
    public static void m173$$Nest$mcheckLongRunningFgs(AppFGSTracker appFGSTracker) {
        int i;
        PendingIntent pendingIntent;
        AppFGSPolicy appFGSPolicy;
        Integer[] numArr;
        ArrayMap arrayMap;
        boolean hasForegroundServiceNotificationsLocked;
        SparseArray sparseArray;
        long j;
        AppFGSPolicy appFGSPolicy2 = (AppFGSPolicy) appFGSTracker.mInjector.mAppStatePolicy;
        final ArrayMap arrayMap2 = appFGSTracker.mTmpPkgDurations;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = appFGSPolicy2.mBgFgsLongRunningThresholdMs;
        long max = Math.max(0L, elapsedRealtime - appFGSPolicy2.mMaxTrackingDuration);
        synchronized (appFGSTracker.mLock) {
            try {
                SparseArray sparseArray2 = appFGSTracker.mPkgEvents.mMap;
                for (int size = sparseArray2.size() - 1; size >= 0; size--) {
                    ArrayMap arrayMap3 = (ArrayMap) sparseArray2.valueAt(size);
                    int size2 = arrayMap3.size() - 1;
                    while (size2 >= 0) {
                        PackageDurations packageDurations = (PackageDurations) arrayMap3.valueAt(size2);
                        int i2 = PackageDurations.DEFAULT_INDEX;
                        if (packageDurations.isActive(i2)) {
                            sparseArray = sparseArray2;
                            if (!packageDurations.mIsLongRunning) {
                                long totalDurations = appFGSTracker.getTotalDurations(packageDurations, elapsedRealtime);
                                if (totalDurations >= j2) {
                                    arrayMap2.put(packageDurations, Long.valueOf(totalDurations));
                                    packageDurations.mIsLongRunning = true;
                                }
                            }
                        } else {
                            sparseArray = sparseArray2;
                        }
                        if (packageDurations.isActive(i2)) {
                            MARsHandler mARsHandler = MARsHandler.MARsHandlerHolder.INSTANCE;
                            String str = packageDurations.mPackageName;
                            int i3 = packageDurations.mUid;
                            j = j2;
                            if (mARsHandler.mMainHandler != null) {
                                Bundle bundle = new Bundle();
                                bundle.putString("pkgName", str);
                                bundle.putInt("uid", i3);
                                Message obtainMessage = mARsHandler.mMainHandler.obtainMessage(15);
                                obtainMessage.setData(bundle);
                                mARsHandler.mMainHandler.sendMessage(obtainMessage);
                            }
                        } else {
                            j = j2;
                        }
                        size2--;
                        sparseArray2 = sparseArray;
                        j2 = j;
                    }
                }
                appFGSTracker.trim(max);
            } finally {
            }
        }
        int size3 = arrayMap2.size();
        if (size3 > 0) {
            Integer[] numArr2 = new Integer[size3];
            int i4 = 0;
            for (int i5 = 0; i5 < size3; i5++) {
                numArr2[i5] = Integer.valueOf(i5);
            }
            Arrays.sort(numArr2, new Comparator() { // from class: com.android.server.am.AppFGSTracker$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    ArrayMap arrayMap4 = arrayMap2;
                    return Long.compare(((Long) arrayMap4.valueAt(((Integer) obj).intValue())).longValue(), ((Long) arrayMap4.valueAt(((Integer) obj2).intValue())).longValue());
                }
            });
            int i6 = size3 - 1;
            while (i6 >= 0) {
                PackageDurations packageDurations2 = (PackageDurations) arrayMap2.keyAt(numArr2[i6].intValue());
                String str2 = packageDurations2.mPackageName;
                int i7 = packageDurations2.mUid;
                if (packageDurations2.mExemptReason == -1) {
                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                    long j3 = appFGSPolicy2.mMaxTrackingDuration;
                    long max2 = Math.max(0L, elapsedRealtime2 - j3);
                    if (!appFGSPolicy2.shouldExemptMediaPlaybackFGS(i7, elapsedRealtime2, j3, str2)) {
                        long totalDurationsSince = ((AppFGSTracker) appFGSPolicy2.mTracker).mAppRestrictionController.mInjector.mAppFGSTracker.getTotalDurationsSince(i7, foregroundServiceTypeToIndex(8), str2, max2, elapsedRealtime2);
                        if (totalDurationsSince <= 0 || totalDurationsSince < appFGSPolicy2.mBgFgsLocationThresholdMs) {
                            AppRestrictionController.NotificationHelper notificationHelper = ((AppFGSTracker) appFGSPolicy2.mTracker).mAppRestrictionController.mNotificationHelper;
                            FrameworkStatsLog.write(FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO, i7, notificationHelper.mBgController.mRestrictionSettings.getRestrictionLevel(i7), 0, 3, notificationHelper.mInjector.mAppFGSTracker.getTrackerInfoForStatsd(i7), (byte[]) null, (byte[]) null, (byte[]) null, PowerExemptionManager.getExemptionReasonForStatsd(notificationHelper.mBgController.getBackgroundRestrictionExemptionReason(i7)), 0, 0, ActivityManager.isLowRamDeviceStatic(), notificationHelper.mBgController.mRestrictionSettings.getRestrictionLevel(i7));
                            if (notificationHelper.mBgController.mConstantsObserver.mBgPromptFgsOnLongRunning) {
                                if (!notificationHelper.mBgController.mConstantsObserver.mBgPromptFgsWithNotiOnLongRunning) {
                                    AppFGSTracker appFGSTracker2 = notificationHelper.mBgController.mInjector.mAppFGSTracker;
                                    synchronized (appFGSTracker2.mLock) {
                                        hasForegroundServiceNotificationsLocked = appFGSTracker2.hasForegroundServiceNotificationsLocked(i7, str2);
                                    }
                                    if (hasForegroundServiceNotificationsLocked) {
                                    }
                                }
                                PendingIntent broadcastAsUser = PendingIntent.getBroadcastAsUser(notificationHelper.mContext, i4, BatteryService$$ExternalSyntheticOutline0.m(16777216, "android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER"), 201326592, UserHandle.SYSTEM);
                                synchronized (notificationHelper.mSettingsLock) {
                                    try {
                                        AppRestrictionController.RestrictionSettings.PkgSettings restrictionSettingsLocked = notificationHelper.mBgController.mRestrictionSettings.getRestrictionSettingsLocked(i7, str2);
                                        if (restrictionSettingsLocked == null) {
                                            pendingIntent = broadcastAsUser;
                                            i = i6;
                                        } else {
                                            notificationHelper.mInjector.getClass();
                                            i = i6;
                                            long currentTimeMillis = System.currentTimeMillis();
                                            long[] jArr = restrictionSettingsLocked.mLastNotificationShownTime;
                                            long j4 = jArr == null ? 0L : jArr[1];
                                            if (j4 != 0) {
                                                pendingIntent = broadcastAsUser;
                                                if (j4 + notificationHelper.mBgController.mConstantsObserver.mBgLongFgsNotificationMinIntervalMs > currentTimeMillis) {
                                                    i4 = 0;
                                                }
                                            } else {
                                                pendingIntent = broadcastAsUser;
                                            }
                                            restrictionSettingsLocked.setLastNotificationTime(1, currentTimeMillis, true);
                                            int[] iArr = restrictionSettingsLocked.mNotificationId;
                                            i4 = iArr == null ? 0 : iArr[1];
                                            if (i4 <= 0) {
                                                i4 = notificationHelper.mNotificationIDStepper;
                                                notificationHelper.mNotificationIDStepper = i4 + 1;
                                                if (iArr == null) {
                                                    restrictionSettingsLocked.mNotificationId = new int[2];
                                                }
                                                restrictionSettingsLocked.mNotificationId[1] = i4;
                                            }
                                        }
                                    } finally {
                                    }
                                }
                                if (i4 <= 0) {
                                    appFGSPolicy = appFGSPolicy2;
                                    numArr = numArr2;
                                    arrayMap = arrayMap2;
                                    i6 = i - 1;
                                    arrayMap2 = arrayMap;
                                    appFGSPolicy2 = appFGSPolicy;
                                    numArr2 = numArr;
                                    i4 = 0;
                                } else {
                                    PackageManagerInternal packageManagerInternal = notificationHelper.mInjector.getPackageManagerInternal();
                                    PackageManager packageManager = notificationHelper.mInjector.mContext.getPackageManager();
                                    PendingIntent pendingIntent2 = pendingIntent;
                                    ApplicationInfo applicationInfo = packageManagerInternal.getApplicationInfo(1000, UserHandle.getUserId(i7), 819200L, str2);
                                    String string = notificationHelper.mContext.getString(R.string.silent_mode_ring);
                                    String string2 = notificationHelper.mContext.getString(R.string.share, applicationInfo != null ? applicationInfo.loadLabel(packageManager) : str2);
                                    Icon createWithResource = applicationInfo != null ? Icon.createWithResource(str2, applicationInfo.icon) : null;
                                    UserHandle of = UserHandle.of(UserHandle.getUserId(i7));
                                    appFGSPolicy = appFGSPolicy2;
                                    numArr = numArr2;
                                    notificationHelper.mNotificationManager.notifyAsUser(null, 203105544, new Notification.Builder(notificationHelper.mContext, SystemNotificationChannels.ABUSIVE_BACKGROUND_APPS).setGroup("com.android.app.abusive_bg_apps").setGroupSummary(true).setStyle(new Notification.BigTextStyle()).setSmallIcon(R.drawable.stat_sys_warning).setColor(notificationHelper.mContext.getColor(R.color.system_notification_accent_color)).build(), of);
                                    Notification.Builder group = new Notification.Builder(notificationHelper.mContext, SystemNotificationChannels.ABUSIVE_BACKGROUND_APPS).setAutoCancel(true).setGroup("com.android.app.abusive_bg_apps");
                                    notificationHelper.mInjector.getClass();
                                    arrayMap = arrayMap2;
                                    Notification.Builder contentIntent = group.setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.stat_sys_warning).setColor(notificationHelper.mContext.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(string2).setContentIntent(pendingIntent2);
                                    if (createWithResource != null) {
                                        contentIntent.setLargeIcon(createWithResource);
                                    }
                                    Notification build = contentIntent.build();
                                    build.extras.putString("android.intent.extra.PACKAGE_NAME", str2);
                                    notificationHelper.mNotificationManager.notifyAsUser(null, i4, build, of);
                                    i6 = i - 1;
                                    arrayMap2 = arrayMap;
                                    appFGSPolicy2 = appFGSPolicy;
                                    numArr2 = numArr;
                                    i4 = 0;
                                }
                            }
                        }
                    }
                }
                appFGSPolicy = appFGSPolicy2;
                numArr = numArr2;
                i = i6;
                arrayMap = arrayMap2;
                i6 = i - 1;
                arrayMap2 = arrayMap;
                appFGSPolicy2 = appFGSPolicy;
                numArr2 = numArr;
                i4 = 0;
            }
            arrayMap2.clear();
        }
        synchronized (appFGSTracker.mLock) {
            appFGSTracker.scheduleDurationCheckLocked(elapsedRealtime);
        }
    }

    /* renamed from: -$$Nest$mhandleForegroundServicesChanged, reason: not valid java name */
    public static void m174$$Nest$mhandleForegroundServicesChanged(AppFGSTracker appFGSTracker, String str, int i, boolean z) {
        int i2;
        boolean z2;
        if (((AppFGSPolicy) appFGSTracker.mInjector.mAppStatePolicy).mTrackerEnabled) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            int backgroundRestrictionExemptionReason = ((AppFGSPolicy) appFGSTracker.mInjector.mAppStatePolicy).mTracker.mAppRestrictionController.getBackgroundRestrictionExemptionReason(i);
            synchronized (appFGSTracker.mLock) {
                try {
                    PackageDurations packageDurations = (PackageDurations) appFGSTracker.mPkgEvents.get(i, str);
                    if (packageDurations == null) {
                        packageDurations = new PackageDurations(i, str, (BaseAppStateEventsTracker.BaseAppStateEventsPolicy) appFGSTracker.mInjector.mAppStatePolicy, appFGSTracker);
                        appFGSTracker.mPkgEvents.put(str, i, packageDurations);
                    }
                    boolean z3 = packageDurations.mIsLongRunning;
                    packageDurations.addEvent(elapsedRealtime, z);
                    i2 = 0;
                    z2 = z3 && !packageDurations.isActive(PackageDurations.DEFAULT_INDEX);
                    if (z2) {
                        packageDurations.mIsLongRunning = false;
                    }
                    packageDurations.mExemptReason = backgroundRestrictionExemptionReason;
                    appFGSTracker.scheduleDurationCheckLocked(elapsedRealtime);
                } finally {
                }
            }
            if (z2) {
                AppRestrictionController.NotificationHelper notificationHelper = ((AppFGSTracker) ((AppFGSPolicy) appFGSTracker.mInjector.mAppStatePolicy).mTracker).mAppRestrictionController.mNotificationHelper;
                synchronized (notificationHelper.mSettingsLock) {
                    try {
                        AppRestrictionController.RestrictionSettings.PkgSettings restrictionSettingsLocked = notificationHelper.mBgController.mRestrictionSettings.getRestrictionSettingsLocked(i, str);
                        if (restrictionSettingsLocked != null) {
                            int[] iArr = restrictionSettingsLocked.mNotificationId;
                            if (iArr != null) {
                                i2 = iArr[1];
                            }
                            if (i2 > 0) {
                                notificationHelper.mNotificationManager.cancel(i2);
                            }
                        }
                    } finally {
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$monBgFgsLongRunningThresholdChanged, reason: not valid java name */
    public static void m175$$Nest$monBgFgsLongRunningThresholdChanged(AppFGSTracker appFGSTracker) {
        synchronized (appFGSTracker.mLock) {
            try {
                if (((AppFGSPolicy) appFGSTracker.mInjector.mAppStatePolicy).mTrackerEnabled) {
                    appFGSTracker.scheduleDurationCheckLocked(SystemClock.elapsedRealtime());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r9v4, types: [com.android.server.am.AppFGSTracker$1] */
    public AppFGSTracker(Context context, AppRestrictionController appRestrictionController) {
        super(context, appRestrictionController);
        this.mFGSNotificationIDs = new UidProcessMap();
        this.mTmpPkgDurations = new ArrayMap();
        this.mNotificationListener = new NotificationListener();
        this.mProcessObserver = new IProcessObserver.Stub() { // from class: com.android.server.am.AppFGSTracker.1
            public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            }

            public final void onForegroundServicesChanged(int i, int i2, int i3) {
                String str;
                ApplicationInfo applicationInfo;
                ActivityManagerService activityManagerService = AppFGSTracker.this.mAppRestrictionController.mInjector.mAppRestrictionController.mActivityManagerService;
                synchronized (activityManagerService.mPidsSelfLocked) {
                    try {
                        ProcessRecord processRecord = activityManagerService.mPidsSelfLocked.get(i);
                        str = (processRecord == null || (applicationInfo = processRecord.info) == null) ? null : applicationInfo.packageName;
                    } finally {
                    }
                }
                if (str != null) {
                    AppFGSTracker.this.mHandler.obtainMessage(2, i2, i3, str).sendToTarget();
                }
            }

            public final void onProcessDied(int i, int i2) {
            }

            public final void onProcessStarted(int i, int i2, int i3, String str, String str2) {
            }
        };
        this.mHandler = new MyHandler(this);
        BaseAppStateTracker.Injector injector = this.mInjector;
        AppFGSPolicy appFGSPolicy = new AppFGSPolicy(injector, this, "bg_fgs_monitor_enabled", true, "bg_fgs_long_running_window", BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
        appFGSPolicy.mBgFgsLongRunningThresholdMs = 72000000L;
        appFGSPolicy.mBgFgsMediaPlaybackThresholdMs = BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS;
        appFGSPolicy.mBgFgsLocationThresholdMs = BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS;
        injector.mAppStatePolicy = appFGSPolicy;
    }

    public static int foregroundServiceTypeToIndex(int i) {
        if (i == 0) {
            return 0;
        }
        return Integer.numberOfTrailingZeros(i) + 1;
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public final BaseAppStateEvents createAppStateEvents(int i, String str) {
        return new PackageDurations(i, str, (BaseAppStateEventsTracker.BaseAppStateEventsPolicy) this.mInjector.mAppStatePolicy, this);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public final BaseAppStateEvents createAppStateEvents(BaseAppStateEvents baseAppStateEvents) {
        return new PackageDurations((PackageDurations) baseAppStateEvents);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    public final void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println("APP FOREGROUND SERVICE TRACKER:");
        super.dump(printWriter, "  " + str);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public final void dumpOthers(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println("APPS WITH ACTIVE FOREGROUND SERVICES:");
        String str2 = "  " + str;
        synchronized (this.mLock) {
            try {
                SparseArray sparseArray = this.mFGSNotificationIDs.mMap;
                if (sparseArray.size() == 0) {
                    printWriter.print(str2);
                    printWriter.println("(none)");
                }
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = sparseArray.keyAt(i);
                    String formatUid = UserHandle.formatUid(keyAt);
                    ArrayMap arrayMap = (ArrayMap) sparseArray.valueAt(i);
                    int size2 = arrayMap.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        String str3 = (String) arrayMap.keyAt(i2);
                        printWriter.print(str2);
                        printWriter.print(str3);
                        printWriter.print('/');
                        printWriter.print(formatUid);
                        printWriter.print(" notification=");
                        printWriter.println(hasForegroundServiceNotificationsLocked(keyAt, str3));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final long getTotalDurations(PackageDurations packageDurations, long j) {
        long j2;
        String str = packageDurations.mPackageName;
        int i = packageDurations.mUid;
        int foregroundServiceTypeToIndex = foregroundServiceTypeToIndex(0);
        synchronized (this.mLock) {
            try {
                BaseAppStateDurations baseAppStateDurations = (BaseAppStateDurations) this.mPkgEvents.get(i, str);
                j2 = 0;
                if (baseAppStateDurations != null) {
                    BaseAppStateDurationsTracker.UidStateDurations uidStateDurations = (BaseAppStateDurationsTracker.UidStateDurations) this.mUidStateDurations.get(i);
                    if (uidStateDurations == null || uidStateDurations.isEmpty()) {
                        j2 = baseAppStateDurations.getTotalDurationsSince(foregroundServiceTypeToIndex, baseAppStateDurations.getEarliest(0L), j);
                    } else {
                        BaseAppStateDurations baseAppStateDurations2 = (BaseAppStateDurations) createAppStateEvents(baseAppStateDurations);
                        baseAppStateDurations2.subtract(uidStateDurations, foregroundServiceTypeToIndex);
                        j2 = baseAppStateDurations2.getTotalDurationsSince(foregroundServiceTypeToIndex, baseAppStateDurations2.getEarliest(0L), j);
                    }
                }
            } finally {
            }
        }
        return j2;
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final byte[] getTrackerInfoForStatsd(int i) {
        long totalDurationsSince;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        boolean z = false;
        int foregroundServiceTypeToIndex = foregroundServiceTypeToIndex(0);
        synchronized (this.mLock) {
            try {
                BaseAppStateDurations baseAppStateDurations = (BaseAppStateDurations) getUidEventsLocked(i);
                if (baseAppStateDurations == null) {
                    totalDurationsSince = 0;
                } else {
                    BaseAppStateDurationsTracker.UidStateDurations uidStateDurations = (BaseAppStateDurationsTracker.UidStateDurations) this.mUidStateDurations.get(i);
                    if (uidStateDurations != null && !uidStateDurations.isEmpty()) {
                        baseAppStateDurations.subtract(uidStateDurations, foregroundServiceTypeToIndex);
                    }
                    totalDurationsSince = baseAppStateDurations.getTotalDurationsSince(foregroundServiceTypeToIndex, baseAppStateDurations.getEarliest(0L), elapsedRealtime);
                }
            } finally {
            }
        }
        if (totalDurationsSince == 0) {
            return null;
        }
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        synchronized (this.mLock) {
            try {
                ArrayMap arrayMap = (ArrayMap) this.mFGSNotificationIDs.mMap.get(i);
                if (arrayMap != null) {
                    for (int size = arrayMap.size() - 1; size >= 0; size--) {
                        if (hasForegroundServiceNotificationsLocked(i, (String) arrayMap.keyAt(size))) {
                            z = true;
                            break;
                        }
                    }
                }
            } finally {
            }
        }
        protoOutputStream.write(1133871366145L, z);
        protoOutputStream.write(1112396529666L, totalDurationsSince);
        protoOutputStream.flush();
        return protoOutputStream.getBytes();
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final int getType() {
        return 3;
    }

    public final boolean hasForegroundServiceNotificationsLocked(int i, String str) {
        SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mFGSNotificationIDs.get(i, str);
        if (sparseBooleanArray != null && sparseBooleanArray.size() != 0) {
            for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
                if (sparseBooleanArray.valueAt(size)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void onForegroundServiceNotificationUpdated(String str, int i, int i2, boolean z) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.argi1 = i;
        obtain.argi2 = i2;
        obtain.arg1 = str;
        obtain.arg2 = z ? Boolean.TRUE : Boolean.FALSE;
        this.mHandler.obtainMessage(3, obtain).sendToTarget();
    }

    public final void onForegroundServiceStateChanged(String str, int i, int i2, boolean z) {
        this.mHandler.obtainMessage(!z ? 1 : 0, i2, i, str).sendToTarget();
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onSystemReady() {
        super.onSystemReady();
        BaseAppStateTracker.Injector injector = this.mInjector;
        injector.mActivityManagerInternal.addForegroundServiceStateListener(this);
        injector.mActivityManagerInternal.registerProcessObserver(this.mProcessObserver);
    }

    @Override // com.android.server.am.BaseAppStateDurationsTracker, com.android.server.am.BaseAppStateEventsTracker
    public void reset() {
        this.mHandler.removeMessages(4);
        super.reset();
    }

    public final void scheduleDurationCheckLocked(long j) {
        SparseArray sparseArray = this.mPkgEvents.mMap;
        long j2 = -1;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            ArrayMap arrayMap = (ArrayMap) sparseArray.valueAt(size);
            for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                PackageDurations packageDurations = (PackageDurations) arrayMap.valueAt(size2);
                if (packageDurations.isActive(PackageDurations.DEFAULT_INDEX) && !packageDurations.mIsLongRunning) {
                    j2 = Math.max(getTotalDurations(packageDurations, j), j2);
                }
            }
        }
        this.mHandler.removeMessages(4);
        if (j2 >= 0) {
            this.mHandler.sendEmptyMessageDelayed(4, Math.max(0L, ((AppFGSPolicy) this.mInjector.mAppStatePolicy).mBgFgsLongRunningThresholdMs - j2) + this.mInjector.mActivityManagerInternal.getServiceStartForegroundTimeout());
        }
    }
}
