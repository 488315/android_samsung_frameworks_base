package com.android.server;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseSetArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsService;
import com.android.internal.util.jobs.ArrayUtils;
import com.android.internal.util.jobs.StatLogger;
import com.android.server.AppStateTracker;
import com.android.server.usage.AppStandbyInternal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppStateTrackerImpl implements AppStateTracker {
    static final int TARGET_OP = 70;
    public ActivityManagerInternal mActivityManagerInternal;
    public AppOpsManager mAppOpsManager;
    public IAppOpsService mAppOpsService;
    public AppStandbyInternal mAppStandbyInternal;
    public boolean mBatterySaverEnabled;
    public final Context mContext;
    FeatureFlagsObserver mFlagsObserver;
    public boolean mForceAllAppStandbyForSmallBattery;
    public boolean mForceAllAppsStandby;
    public final MyHandler mHandler;
    public IActivityManager mIActivityManager;
    public boolean mIsPluggedIn;
    public int[] mPowerExemptAllAppIds;
    public PowerManagerInternal mPowerManagerInternal;
    public boolean mStarted;
    public int[] mTempExemptAppIds;
    public final Object mLock = new Object();
    public final ArraySet mRunAnyRestrictedPackages = new ArraySet();
    public final SparseBooleanArray mActiveUids = new SparseBooleanArray();
    public int[] mPowerExemptUserAppIds = new int[0];
    final SparseSetArray mExemptedBucketPackages = new SparseSetArray();
    public final ArraySet mListeners = new ArraySet();
    public volatile Set mBackgroundRestrictedUidPackages = Collections.emptySet();
    public final StatLogger mStatLogger = new StatLogger(new String[]{"UID_FG_STATE_CHANGED", "UID_ACTIVE_STATE_CHANGED", "RUN_ANY_CHANGED", "ALL_UNEXEMPTED", "ALL_EXEMPTION_LIST_CHANGED", "TEMP_EXEMPTION_LIST_CHANGED", "EXEMPTED_BUCKET_CHANGED", "FORCE_ALL_CHANGED", "IS_UID_ACTIVE_CACHED", "IS_UID_ACTIVE_RAW"});
    public final AnonymousClass2 mAppBackgroundRestrictionListener = new ActivityManagerInternal.AppBackgroundRestrictionListener() { // from class: com.android.server.AppStateTrackerImpl.2
        public final void onAutoRestrictedBucketFeatureFlagChanged(boolean z) {
            MyHandler myHandler = AppStateTrackerImpl.this.mHandler;
            myHandler.removeMessages(11);
            myHandler.obtainMessage(11, z ? 1 : 0, 0).sendToTarget();
        }
    };
    public final AnonymousClass3 mReceiver = new BroadcastReceiver() { // from class: com.android.server.AppStateTrackerImpl.3
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            char c;
            boolean z = true;
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            String action = intent.getAction();
            action.getClass();
            switch (action.hashCode()) {
                case -2061058799:
                    if (action.equals("android.intent.action.USER_REMOVED")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1538406691:
                    if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 525384130:
                    if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    if (intExtra > 0) {
                        AppStateTrackerImpl.this.mHandler.obtainMessage(8, intExtra, 0).sendToTarget();
                        return;
                    }
                    return;
                case 1:
                    synchronized (AppStateTrackerImpl.this.mLock) {
                        AppStateTrackerImpl appStateTrackerImpl = AppStateTrackerImpl.this;
                        if (intent.getIntExtra("plugged", 0) == 0) {
                            z = false;
                        }
                        appStateTrackerImpl.mIsPluggedIn = z;
                    }
                    AppStateTrackerImpl.this.updateForceAllAppStandbyState();
                    return;
                case 2:
                    if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                        return;
                    }
                    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                    synchronized (AppStateTrackerImpl.this.mLock) {
                        AppStateTrackerImpl.this.mExemptedBucketPackages.remove(intExtra, schemeSpecificPart);
                        AppStateTrackerImpl.this.mRunAnyRestrictedPackages.remove(Pair.create(Integer.valueOf(intExtra2), schemeSpecificPart));
                        AppStateTrackerImpl.this.updateBackgroundRestrictedUidPackagesLocked();
                        AppStateTrackerImpl.this.mActiveUids.delete(intExtra2);
                    }
                    return;
                default:
                    return;
            }
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppOpsWatcher extends IAppOpsCallback.Stub {
        public AppOpsWatcher() {
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x001e A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void opChanged(int r5, int r6, java.lang.String r7, java.lang.String r8) {
            /*
                r4 = this;
                r5 = 1
                r8 = 0
                com.android.server.AppStateTrackerImpl r0 = com.android.server.AppStateTrackerImpl.this     // Catch: android.os.RemoteException -> L10
                com.android.internal.app.IAppOpsService r0 = r0.mAppOpsService     // Catch: android.os.RemoteException -> L10
                r1 = 70
                int r0 = r0.checkOperation(r1, r6, r7)     // Catch: android.os.RemoteException -> L10
                if (r0 == 0) goto L10
                r0 = r5
                goto L11
            L10:
                r0 = r8
            L11:
                if (r0 == 0) goto L19
                java.lang.String r1 = "battery.value_app_background_restricted"
                com.android.modules.expresslog.Counter.logIncrementWithUid(r1, r6)
            L19:
                com.android.server.AppStateTrackerImpl r1 = com.android.server.AppStateTrackerImpl.this
                java.lang.Object r1 = r1.mLock
                monitor-enter(r1)
                com.android.server.AppStateTrackerImpl r2 = com.android.server.AppStateTrackerImpl.this     // Catch: java.lang.Throwable -> L51
                int r3 = r2.findForcedAppStandbyUidPackageIndexLocked(r6, r7)     // Catch: java.lang.Throwable -> L51
                if (r3 < 0) goto L27
                goto L28
            L27:
                r5 = r8
            L28:
                if (r5 != r0) goto L2b
                goto L4f
            L2b:
                if (r0 == 0) goto L3b
                android.util.ArraySet r5 = r2.mRunAnyRestrictedPackages     // Catch: java.lang.Throwable -> L51
                java.lang.Integer r0 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Throwable -> L51
                android.util.Pair r0 = android.util.Pair.create(r0, r7)     // Catch: java.lang.Throwable -> L51
                r5.add(r0)     // Catch: java.lang.Throwable -> L51
                goto L40
            L3b:
                android.util.ArraySet r5 = r2.mRunAnyRestrictedPackages     // Catch: java.lang.Throwable -> L51
                r5.removeAt(r3)     // Catch: java.lang.Throwable -> L51
            L40:
                r2.updateBackgroundRestrictedUidPackagesLocked()     // Catch: java.lang.Throwable -> L51
                com.android.server.AppStateTrackerImpl r4 = com.android.server.AppStateTrackerImpl.this     // Catch: java.lang.Throwable -> L51
                com.android.server.AppStateTrackerImpl$MyHandler r4 = r4.mHandler     // Catch: java.lang.Throwable -> L51
                r5 = 3
                android.os.Message r4 = r4.obtainMessage(r5, r6, r8, r7)     // Catch: java.lang.Throwable -> L51
                r4.sendToTarget()     // Catch: java.lang.Throwable -> L51
            L4f:
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L51
                return
            L51:
                r4 = move-exception
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L51
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.AppStateTrackerImpl.AppOpsWatcher.opChanged(int, int, java.lang.String, java.lang.String):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class FeatureFlagsObserver extends ContentObserver {
        public FeatureFlagsObserver() {
            super(null);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (!Settings.Global.getUriFor("forced_app_standby_for_small_battery_enabled").equals(uri)) {
                Slog.w("AppStateTracker", "Unexpected feature flag uri encountered: " + uri);
                return;
            }
            boolean z2 = AppStateTrackerImpl.this.injectGetGlobalSettingInt("forced_app_standby_for_small_battery_enabled", 0) == 1;
            synchronized (AppStateTrackerImpl.this.mLock) {
                try {
                    AppStateTrackerImpl appStateTrackerImpl = AppStateTrackerImpl.this;
                    if (appStateTrackerImpl.mForceAllAppStandbyForSmallBattery == z2) {
                        return;
                    }
                    appStateTrackerImpl.mForceAllAppStandbyForSmallBattery = z2;
                    appStateTrackerImpl.updateForceAllAppStandbyState();
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Listener {
        public void handleUidCachedChanged(int i, boolean z) {
        }

        public void removeAlarmsForUid(int i) {
        }

        public void unblockAlarmsForUid(int i) {
        }

        public void unblockAlarmsForUidPackage(int i, String str) {
        }

        public void unblockAllUnrestrictedAlarms() {
        }

        public void updateAlarmsForUid(int i) {
        }

        public void updateAllAlarms() {
        }

        public void updateAllJobs() {
        }

        public void updateBackgroundRestrictedForUidPackage(int i, String str, boolean z) {
        }

        public void updateJobsForUid(int i, boolean z) {
        }

        public void updateJobsForUidPackage(int i, boolean z) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            if (message.what == 8) {
                AppStateTrackerImpl.this.handleUserRemoved(message.arg1);
                return;
            }
            synchronized (AppStateTrackerImpl.this.mLock) {
                try {
                    AppStateTrackerImpl appStateTrackerImpl = AppStateTrackerImpl.this;
                    if (appStateTrackerImpl.mStarted) {
                        long time = appStateTrackerImpl.mStatLogger.getTime();
                        int i = 0;
                        switch (message.what) {
                            case 0:
                                Listener[] m39$$Nest$mcloneListeners = AppStateTrackerImpl.m39$$Nest$mcloneListeners(AppStateTrackerImpl.this);
                                int length = m39$$Nest$mcloneListeners.length;
                                while (i < length) {
                                    Listener listener = m39$$Nest$mcloneListeners[i];
                                    int i2 = message.arg1;
                                    listener.getClass();
                                    boolean isUidActive = appStateTrackerImpl.isUidActive(i2);
                                    listener.updateJobsForUid(i2, isUidActive);
                                    listener.updateAlarmsForUid(i2);
                                    if (isUidActive) {
                                        listener.unblockAlarmsForUid(i2);
                                    }
                                    i++;
                                }
                                AppStateTrackerImpl.this.mStatLogger.logDurationStat(1, time);
                                return;
                            case 1:
                            case 2:
                            case 9:
                            default:
                                return;
                            case 3:
                                for (Listener listener2 : AppStateTrackerImpl.m39$$Nest$mcloneListeners(AppStateTrackerImpl.this)) {
                                    int i3 = message.arg1;
                                    String str = (String) message.obj;
                                    listener2.getClass();
                                    listener2.updateJobsForUidPackage(i3, appStateTrackerImpl.isUidActive(i3));
                                    if (!appStateTrackerImpl.areAlarmsRestricted(i3, str)) {
                                        listener2.unblockAlarmsForUidPackage(i3, str);
                                    }
                                    if (appStateTrackerImpl.isRunAnyInBackgroundAppOpsAllowed(i3, str)) {
                                        Slog.v("AppStateTracker", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i3, "Package ", str, "/", " toggled out of fg service restriction"));
                                        listener2.updateBackgroundRestrictedForUidPackage(i3, str, false);
                                    } else {
                                        Slog.v("AppStateTracker", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i3, "Package ", str, "/", " toggled into fg service restriction"));
                                        listener2.updateBackgroundRestrictedForUidPackage(i3, str, true);
                                    }
                                }
                                AppStateTrackerImpl.this.mStatLogger.logDurationStat(2, time);
                                return;
                            case 4:
                                Listener[] m39$$Nest$mcloneListeners2 = AppStateTrackerImpl.m39$$Nest$mcloneListeners(AppStateTrackerImpl.this);
                                int length2 = m39$$Nest$mcloneListeners2.length;
                                while (i < length2) {
                                    Listener listener3 = m39$$Nest$mcloneListeners2[i];
                                    listener3.updateAllJobs();
                                    listener3.updateAllAlarms();
                                    i++;
                                }
                                AppStateTrackerImpl.this.mStatLogger.logDurationStat(3, time);
                                return;
                            case 5:
                                Listener[] m39$$Nest$mcloneListeners3 = AppStateTrackerImpl.m39$$Nest$mcloneListeners(AppStateTrackerImpl.this);
                                int length3 = m39$$Nest$mcloneListeners3.length;
                                while (i < length3) {
                                    Listener listener4 = m39$$Nest$mcloneListeners3[i];
                                    listener4.updateAllJobs();
                                    listener4.updateAllAlarms();
                                    listener4.unblockAllUnrestrictedAlarms();
                                    i++;
                                }
                                AppStateTrackerImpl.this.mStatLogger.logDurationStat(4, time);
                                return;
                            case 6:
                                Listener[] m39$$Nest$mcloneListeners4 = AppStateTrackerImpl.m39$$Nest$mcloneListeners(AppStateTrackerImpl.this);
                                int length4 = m39$$Nest$mcloneListeners4.length;
                                while (i < length4) {
                                    m39$$Nest$mcloneListeners4[i].updateAllJobs();
                                    i++;
                                }
                                AppStateTrackerImpl.this.mStatLogger.logDurationStat(5, time);
                                return;
                            case 7:
                                Listener[] m39$$Nest$mcloneListeners5 = AppStateTrackerImpl.m39$$Nest$mcloneListeners(AppStateTrackerImpl.this);
                                int length5 = m39$$Nest$mcloneListeners5.length;
                                while (i < length5) {
                                    Listener listener5 = m39$$Nest$mcloneListeners5[i];
                                    listener5.updateAllJobs();
                                    listener5.updateAllAlarms();
                                    i++;
                                }
                                AppStateTrackerImpl.this.mStatLogger.logDurationStat(7, time);
                                return;
                            case 8:
                                AppStateTrackerImpl.this.handleUserRemoved(message.arg1);
                                return;
                            case 10:
                                Listener[] m39$$Nest$mcloneListeners6 = AppStateTrackerImpl.m39$$Nest$mcloneListeners(AppStateTrackerImpl.this);
                                int length6 = m39$$Nest$mcloneListeners6.length;
                                while (i < length6) {
                                    Listener listener6 = m39$$Nest$mcloneListeners6[i];
                                    listener6.updateAllJobs();
                                    listener6.updateAllAlarms();
                                    i++;
                                }
                                AppStateTrackerImpl.this.mStatLogger.logDurationStat(6, time);
                                return;
                            case 11:
                                z = message.arg1 == 1;
                                Listener[] m39$$Nest$mcloneListeners7 = AppStateTrackerImpl.m39$$Nest$mcloneListeners(AppStateTrackerImpl.this);
                                int length7 = m39$$Nest$mcloneListeners7.length;
                                while (i < length7) {
                                    Listener listener7 = m39$$Nest$mcloneListeners7[i];
                                    listener7.updateAllJobs();
                                    if (z) {
                                        listener7.unblockAllUnrestrictedAlarms();
                                    }
                                    i++;
                                }
                                return;
                            case 12:
                                int i4 = message.arg1;
                                synchronized (AppStateTrackerImpl.this.mLock) {
                                    SparseBooleanArray sparseBooleanArray = AppStateTrackerImpl.this.mActiveUids;
                                    if (!UserHandle.isCore(i4) && !sparseBooleanArray.get(i4)) {
                                        sparseBooleanArray.put(i4, true);
                                        AppStateTrackerImpl.this.mHandler.obtainMessage(0, i4, 0).sendToTarget();
                                    }
                                }
                                return;
                            case 13:
                                removeUid(message.arg1, true);
                                if (message.arg2 != 0) {
                                    int i5 = message.arg1;
                                    Listener[] m39$$Nest$mcloneListeners8 = AppStateTrackerImpl.m39$$Nest$mcloneListeners(AppStateTrackerImpl.this);
                                    int length8 = m39$$Nest$mcloneListeners8.length;
                                    while (i < length8) {
                                        m39$$Nest$mcloneListeners8[i].removeAlarmsForUid(i5);
                                        i++;
                                    }
                                    return;
                                }
                                return;
                            case 14:
                                removeUid(message.arg1, false);
                                if (message.arg2 != 0) {
                                    int i6 = message.arg1;
                                    Listener[] m39$$Nest$mcloneListeners9 = AppStateTrackerImpl.m39$$Nest$mcloneListeners(AppStateTrackerImpl.this);
                                    int length9 = m39$$Nest$mcloneListeners9.length;
                                    while (i < length9) {
                                        m39$$Nest$mcloneListeners9[i].removeAlarmsForUid(i6);
                                        i++;
                                    }
                                    return;
                                }
                                return;
                            case 15:
                                int i7 = message.arg1;
                                z = message.arg2 != 0;
                                Listener[] m39$$Nest$mcloneListeners10 = AppStateTrackerImpl.m39$$Nest$mcloneListeners(AppStateTrackerImpl.this);
                                int length10 = m39$$Nest$mcloneListeners10.length;
                                while (i < length10) {
                                    m39$$Nest$mcloneListeners10[i].handleUidCachedChanged(i7, z);
                                    i++;
                                }
                                return;
                        }
                    }
                } finally {
                }
            }
        }

        public final void removeUid(int i, boolean z) {
            synchronized (AppStateTrackerImpl.this.mLock) {
                SparseBooleanArray sparseBooleanArray = AppStateTrackerImpl.this.mActiveUids;
                if (!UserHandle.isCore(i) && sparseBooleanArray.get(i)) {
                    if (z) {
                        sparseBooleanArray.delete(i);
                    } else {
                        sparseBooleanArray.put(i, false);
                    }
                    AppStateTrackerImpl.this.mHandler.obtainMessage(0, i, 0).sendToTarget();
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StandbyTracker extends AppStandbyInternal.AppIdleStateChangeListener {
        public StandbyTracker() {
        }

        public final void onAppIdleStateChanged(String str, int i, boolean z, int i2, int i3) {
            synchronized (AppStateTrackerImpl.this.mLock) {
                try {
                    if (i2 == 5 ? AppStateTrackerImpl.this.mExemptedBucketPackages.add(i, str) : AppStateTrackerImpl.this.mExemptedBucketPackages.remove(i, str)) {
                        MyHandler myHandler = AppStateTrackerImpl.this.mHandler;
                        myHandler.removeMessages(10);
                        myHandler.obtainMessage(10).sendToTarget();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidObserver extends android.app.UidObserver {
        public UidObserver() {
        }

        public final void onUidActive(int i) {
            AppStateTrackerImpl.this.mHandler.obtainMessage(12, i, 0).sendToTarget();
        }

        public final void onUidCachedChanged(int i, boolean z) {
            AppStateTrackerImpl.this.mHandler.obtainMessage(15, i, z ? 1 : 0).sendToTarget();
        }

        public final void onUidGone(int i, boolean z) {
            AppStateTrackerImpl.this.mHandler.obtainMessage(13, i, z ? 1 : 0).sendToTarget();
        }

        public final void onUidIdle(int i, boolean z) {
            AppStateTrackerImpl.this.mHandler.obtainMessage(14, i, z ? 1 : 0).sendToTarget();
        }
    }

    /* renamed from: -$$Nest$mcloneListeners, reason: not valid java name */
    public static Listener[] m39$$Nest$mcloneListeners(AppStateTrackerImpl appStateTrackerImpl) {
        Listener[] listenerArr;
        synchronized (appStateTrackerImpl.mLock) {
            ArraySet arraySet = appStateTrackerImpl.mListeners;
            listenerArr = (Listener[]) arraySet.toArray(new Listener[arraySet.size()]);
        }
        return listenerArr;
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.server.AppStateTrackerImpl$3] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.server.AppStateTrackerImpl$2] */
    public AppStateTrackerImpl(Context context, Looper looper) {
        int[] iArr = new int[0];
        this.mPowerExemptAllAppIds = iArr;
        this.mTempExemptAppIds = iArr;
        this.mContext = context;
        this.mHandler = new MyHandler(looper);
    }

    public static boolean isAnyAppIdUnexempt(int[] iArr, int[] iArr2) {
        boolean z;
        boolean z2;
        int i = 0;
        int i2 = 0;
        while (true) {
            z = i >= iArr.length;
            z2 = i2 >= iArr2.length;
            if (z || z2) {
                break;
            }
            int i3 = iArr[i];
            int i4 = iArr2[i2];
            if (i3 == i4) {
                i++;
            } else if (i3 < i4) {
                return true;
            }
            i2++;
        }
        if (z) {
            return false;
        }
        return z2;
    }

    public final void addBackgroundRestrictedAppListener(final AppStateTracker.BackgroundRestrictedAppListener backgroundRestrictedAppListener) {
        addListener(new Listener() { // from class: com.android.server.AppStateTrackerImpl.1
            @Override // com.android.server.AppStateTrackerImpl.Listener
            public final void updateBackgroundRestrictedForUidPackage(int i, String str, boolean z) {
                backgroundRestrictedAppListener.updateBackgroundRestrictedForUidPackage(i, str, z);
            }
        });
    }

    public final void addListener(Listener listener) {
        synchronized (this.mLock) {
            this.mListeners.add(listener);
        }
    }

    public final boolean areAlarmsRestricted(int i, String str) {
        boolean z = false;
        if (isUidActive(i)) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (ArrayUtils.contains(this.mPowerExemptAllAppIds, UserHandle.getAppId(i))) {
                    return false;
                }
                if (!this.mActivityManagerInternal.isBgAutoRestrictedBucketFeatureFlagEnabled() && findForcedAppStandbyUidPackageIndexLocked(i, str) >= 0) {
                    z = true;
                }
                return z;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean areAlarmsRestrictedByBatterySaver(int i, String str) {
        if (isUidActive(i)) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (ArrayUtils.contains(this.mPowerExemptAllAppIds, UserHandle.getAppId(i))) {
                    return false;
                }
                int userId = UserHandle.getUserId(i);
                if (this.mAppStandbyInternal.isAppIdleEnabled() && !this.mAppStandbyInternal.isInParole() && this.mExemptedBucketPackages.contains(userId, str)) {
                    return false;
                }
                return this.mForceAllAppsStandby;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean areJobsRestricted(int i, String str, boolean z) {
        if (isUidActive(i)) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                int appId = UserHandle.getAppId(i);
                if (!ArrayUtils.contains(this.mPowerExemptAllAppIds, appId) && !ArrayUtils.contains(this.mTempExemptAppIds, appId)) {
                    if (!this.mActivityManagerInternal.isBgAutoRestrictedBucketFeatureFlagEnabled() && findForcedAppStandbyUidPackageIndexLocked(i, str) >= 0) {
                        return true;
                    }
                    if (z) {
                        return false;
                    }
                    int userId = UserHandle.getUserId(i);
                    if (this.mAppStandbyInternal.isAppIdleEnabled() && !this.mAppStandbyInternal.isInParole() && this.mExemptedBucketPackages.contains(userId, str)) {
                        return false;
                    }
                    return this.mForceAllAppsStandby;
                }
                return false;
            } finally {
            }
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        boolean z;
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Current AppStateTracker State:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("Force all apps standby: ");
                synchronized (this.mLock) {
                    z = this.mForceAllAppsStandby;
                }
                indentingPrintWriter.println(z);
                indentingPrintWriter.print("Small Battery Device: ");
                indentingPrintWriter.println(isSmallBatteryDevice());
                indentingPrintWriter.print("Force all apps standby for small battery device: ");
                indentingPrintWriter.println(this.mForceAllAppStandbyForSmallBattery);
                indentingPrintWriter.print("Plugged In: ");
                indentingPrintWriter.println(this.mIsPluggedIn);
                indentingPrintWriter.print("Active uids: ");
                SparseBooleanArray sparseBooleanArray = this.mActiveUids;
                indentingPrintWriter.print("[");
                String str = "";
                for (int i = 0; i < sparseBooleanArray.size(); i++) {
                    if (sparseBooleanArray.valueAt(i)) {
                        indentingPrintWriter.print(str);
                        indentingPrintWriter.print(UserHandle.formatUid(sparseBooleanArray.keyAt(i)));
                        str = " ";
                    }
                }
                indentingPrintWriter.println("]");
                indentingPrintWriter.print("Except-idle + user exemption list appids: ");
                indentingPrintWriter.println(Arrays.toString(this.mPowerExemptAllAppIds));
                indentingPrintWriter.print("User exemption list appids: ");
                indentingPrintWriter.println(Arrays.toString(this.mPowerExemptUserAppIds));
                indentingPrintWriter.print("Temp exemption list appids: ");
                indentingPrintWriter.println(Arrays.toString(this.mTempExemptAppIds));
                indentingPrintWriter.println("Exempted bucket packages:");
                indentingPrintWriter.increaseIndent();
                for (int i2 = 0; i2 < this.mExemptedBucketPackages.size(); i2++) {
                    indentingPrintWriter.print("User ");
                    indentingPrintWriter.print(this.mExemptedBucketPackages.keyAt(i2));
                    indentingPrintWriter.println();
                    indentingPrintWriter.increaseIndent();
                    for (int i3 = 0; i3 < this.mExemptedBucketPackages.sizeAt(i2); i3++) {
                        indentingPrintWriter.print((String) this.mExemptedBucketPackages.valueAt(i2, i3));
                        indentingPrintWriter.println();
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Restricted packages:");
                indentingPrintWriter.increaseIndent();
                Iterator it = this.mRunAnyRestrictedPackages.iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    indentingPrintWriter.print(UserHandle.formatUid(((Integer) pair.first).intValue()));
                    indentingPrintWriter.print(" ");
                    indentingPrintWriter.print((String) pair.second);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.decreaseIndent();
                this.mStatLogger.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpProto(ProtoOutputStream protoOutputStream, long j) {
        boolean z;
        synchronized (this.mLock) {
            try {
                long start = protoOutputStream.start(j);
                synchronized (this.mLock) {
                    z = this.mForceAllAppsStandby;
                }
                protoOutputStream.write(1133871366145L, z);
                protoOutputStream.write(1133871366150L, isSmallBatteryDevice());
                protoOutputStream.write(1133871366151L, this.mForceAllAppStandbyForSmallBattery);
                protoOutputStream.write(1133871366152L, this.mIsPluggedIn);
                for (int i = 0; i < this.mActiveUids.size(); i++) {
                    if (this.mActiveUids.valueAt(i)) {
                        protoOutputStream.write(2220498092034L, this.mActiveUids.keyAt(i));
                    }
                }
                for (int i2 : this.mPowerExemptAllAppIds) {
                    protoOutputStream.write(2220498092035L, i2);
                }
                for (int i3 : this.mPowerExemptUserAppIds) {
                    protoOutputStream.write(2220498092044L, i3);
                }
                for (int i4 : this.mTempExemptAppIds) {
                    protoOutputStream.write(2220498092036L, i4);
                }
                for (int i5 = 0; i5 < this.mExemptedBucketPackages.size(); i5++) {
                    for (int i6 = 0; i6 < this.mExemptedBucketPackages.sizeAt(i5); i6++) {
                        long start2 = protoOutputStream.start(2246267895818L);
                        protoOutputStream.write(1120986464257L, this.mExemptedBucketPackages.keyAt(i5));
                        protoOutputStream.write(1138166333442L, (String) this.mExemptedBucketPackages.valueAt(i5, i6));
                        protoOutputStream.end(start2);
                    }
                }
                Iterator it = this.mRunAnyRestrictedPackages.iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    long start3 = protoOutputStream.start(2246267895813L);
                    protoOutputStream.write(1120986464257L, ((Integer) pair.first).intValue());
                    protoOutputStream.write(1138166333442L, (String) pair.second);
                    protoOutputStream.end(start3);
                }
                this.mStatLogger.dumpProto(protoOutputStream, 1146756268041L);
                protoOutputStream.end(start);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int findForcedAppStandbyUidPackageIndexLocked(int i, String str) {
        int size = this.mRunAnyRestrictedPackages.size();
        if (size > 8) {
            return this.mRunAnyRestrictedPackages.indexOf(Pair.create(Integer.valueOf(i), str));
        }
        for (int i2 = 0; i2 < size; i2++) {
            Pair pair = (Pair) this.mRunAnyRestrictedPackages.valueAt(i2);
            if (((Integer) pair.first).intValue() == i && str.equals(pair.second)) {
                return i2;
            }
        }
        return -1;
    }

    public final void handleUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mRunAnyRestrictedPackages.size() - 1; size >= 0; size--) {
                    if (UserHandle.getUserId(((Integer) ((Pair) this.mRunAnyRestrictedPackages.valueAt(size)).first).intValue()) == i) {
                        this.mRunAnyRestrictedPackages.removeAt(size);
                    }
                }
                updateBackgroundRestrictedUidPackagesLocked();
                SparseBooleanArray sparseBooleanArray = this.mActiveUids;
                for (int size2 = sparseBooleanArray.size() - 1; size2 >= 0; size2--) {
                    if (UserHandle.getUserId(sparseBooleanArray.keyAt(size2)) == i) {
                        sparseBooleanArray.removeAt(size2);
                    }
                }
                this.mExemptedBucketPackages.remove(i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ActivityManagerInternal injectActivityManagerInternal() {
        return (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
    }

    public AppOpsManager injectAppOpsManager() {
        return (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
    }

    public AppStandbyInternal injectAppStandbyInternal() {
        return (AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class);
    }

    public int injectGetGlobalSettingInt(String str, int i) {
        return Settings.Global.getInt(this.mContext.getContentResolver(), str, i);
    }

    public IActivityManager injectIActivityManager() {
        return ActivityManager.getService();
    }

    public IAppOpsService injectIAppOpsService() {
        return IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
    }

    public PowerManagerInternal injectPowerManagerInternal() {
        return (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
    }

    public final boolean isAppBackgroundRestricted(int i, String str) {
        return this.mBackgroundRestrictedUidPackages.contains(Pair.create(Integer.valueOf(i), str));
    }

    public final boolean isRunAnyInBackgroundAppOpsAllowed(int i, String str) {
        boolean z;
        synchronized (this.mLock) {
            z = !(findForcedAppStandbyUidPackageIndexLocked(i, str) >= 0);
        }
        return z;
    }

    public boolean isSmallBatteryDevice() {
        return ActivityManager.isSmallBatteryDevice();
    }

    public final boolean isUidActive(int i) {
        boolean z;
        if (UserHandle.isCore(i)) {
            return true;
        }
        synchronized (this.mLock) {
            z = this.mActiveUids.get(i);
        }
        return z;
    }

    public final void onSystemServicesReady() {
        synchronized (this.mLock) {
            try {
                if (this.mStarted) {
                    return;
                }
                boolean z = true;
                this.mStarted = true;
                IActivityManager injectIActivityManager = injectIActivityManager();
                Objects.requireNonNull(injectIActivityManager);
                this.mIActivityManager = injectIActivityManager;
                ActivityManagerInternal injectActivityManagerInternal = injectActivityManagerInternal();
                Objects.requireNonNull(injectActivityManagerInternal);
                this.mActivityManagerInternal = injectActivityManagerInternal;
                AppOpsManager injectAppOpsManager = injectAppOpsManager();
                Objects.requireNonNull(injectAppOpsManager);
                this.mAppOpsManager = injectAppOpsManager;
                IAppOpsService injectIAppOpsService = injectIAppOpsService();
                Objects.requireNonNull(injectIAppOpsService);
                this.mAppOpsService = injectIAppOpsService;
                PowerManagerInternal injectPowerManagerInternal = injectPowerManagerInternal();
                Objects.requireNonNull(injectPowerManagerInternal);
                this.mPowerManagerInternal = injectPowerManagerInternal;
                AppStandbyInternal injectAppStandbyInternal = injectAppStandbyInternal();
                Objects.requireNonNull(injectAppStandbyInternal);
                this.mAppStandbyInternal = injectAppStandbyInternal;
                FeatureFlagsObserver featureFlagsObserver = new FeatureFlagsObserver();
                this.mFlagsObserver = featureFlagsObserver;
                this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("forced_app_standby_for_small_battery_enabled"), false, featureFlagsObserver);
                if (AppStateTrackerImpl.this.injectGetGlobalSettingInt("forced_app_standby_for_small_battery_enabled", 0) != 1) {
                    z = false;
                }
                this.mForceAllAppStandbyForSmallBattery = z;
                this.mAppStandbyInternal.addListener(new StandbyTracker());
                this.mActivityManagerInternal.addAppBackgroundRestrictionListener(this.mAppBackgroundRestrictionListener);
                try {
                    this.mIActivityManager.registerUidObserver(new UidObserver(), 30, -1, (String) null);
                    this.mAppOpsService.startWatchingMode(70, (String) null, new AppOpsWatcher());
                } catch (RemoteException unused) {
                }
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.USER_REMOVED");
                intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
                this.mContext.registerReceiver(this.mReceiver, intentFilter);
                IntentFilter intentFilter2 = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
                intentFilter2.addDataScheme("package");
                this.mContext.registerReceiver(this.mReceiver, intentFilter2);
                refreshForcedAppStandbyUidPackagesLocked();
                this.mPowerManagerInternal.registerLowPowerModeObserver(11, new Consumer() { // from class: com.android.server.AppStateTrackerImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AppStateTrackerImpl appStateTrackerImpl = AppStateTrackerImpl.this;
                        PowerSaveState powerSaveState = (PowerSaveState) obj;
                        synchronized (appStateTrackerImpl.mLock) {
                            appStateTrackerImpl.mBatterySaverEnabled = powerSaveState.batterySaverEnabled;
                            appStateTrackerImpl.updateForceAllAppStandbyState();
                        }
                    }
                });
                this.mBatterySaverEnabled = this.mPowerManagerInternal.getLowPowerState(11).batterySaverEnabled;
                updateForceAllAppStandbyState();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void refreshForcedAppStandbyUidPackagesLocked() {
        this.mRunAnyRestrictedPackages.clear();
        List packagesForOps = this.mAppOpsManager.getPackagesForOps(new int[]{70});
        if (packagesForOps == null) {
            return;
        }
        int size = packagesForOps.size();
        for (int i = 0; i < size; i++) {
            AppOpsManager.PackageOps packageOps = (AppOpsManager.PackageOps) packagesForOps.get(i);
            List ops = ((AppOpsManager.PackageOps) packagesForOps.get(i)).getOps();
            for (int i2 = 0; i2 < ops.size(); i2++) {
                AppOpsManager.OpEntry opEntry = (AppOpsManager.OpEntry) ops.get(i2);
                if (opEntry.getOp() == 70 && opEntry.getMode() != 0) {
                    this.mRunAnyRestrictedPackages.add(Pair.create(Integer.valueOf(packageOps.getUid()), packageOps.getPackageName()));
                }
            }
        }
        updateBackgroundRestrictedUidPackagesLocked();
    }

    public final void updateBackgroundRestrictedUidPackagesLocked() {
        ArraySet arraySet = new ArraySet();
        int size = this.mRunAnyRestrictedPackages.size();
        for (int i = 0; i < size; i++) {
            arraySet.add((Pair) this.mRunAnyRestrictedPackages.valueAt(i));
        }
        this.mBackgroundRestrictedUidPackages = Collections.unmodifiableSet(arraySet);
    }

    public final void updateForceAllAppStandbyState() {
        synchronized (this.mLock) {
            try {
                if (this.mForceAllAppStandbyForSmallBattery && isSmallBatteryDevice()) {
                    boolean z = !this.mIsPluggedIn;
                    if (z != this.mForceAllAppsStandby) {
                        this.mForceAllAppsStandby = z;
                        MyHandler myHandler = this.mHandler;
                        myHandler.removeMessages(7);
                        myHandler.obtainMessage(7).sendToTarget();
                    }
                } else {
                    boolean z2 = this.mBatterySaverEnabled;
                    if (z2 != this.mForceAllAppsStandby) {
                        this.mForceAllAppsStandby = z2;
                        MyHandler myHandler2 = this.mHandler;
                        myHandler2.removeMessages(7);
                        myHandler2.obtainMessage(7).sendToTarget();
                    }
                }
            } finally {
            }
        }
    }
}
