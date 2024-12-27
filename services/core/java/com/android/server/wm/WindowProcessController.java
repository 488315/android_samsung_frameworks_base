package com.android.server.wm;

import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.app.compat.CompatChanges;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ConfigurationChangeItem;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.DeadObjectException;
import android.os.Debug;
import android.os.InputConstants;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Slog;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessRecord;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.notification.NotificationManagerService;
import com.android.server.wm.VisibleActivityProcessTracker.CpuTimeRecord;
import com.android.window.flags.Flags;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntPredicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowProcessController extends ConfigurationContainer
        implements ConfigurationContainerListener {
    public boolean mAdjustBindAppToDexConfig;
    public int mAnimatingReasons;
    public final ActivityTaskManagerService mAtm;
    public final BackgroundLaunchProcessController mBgLaunchController;
    public ActivityRecord mConfigActivityRecord;
    public Task mConfigTask;
    public volatile boolean mCrashing;
    public volatile int mCurSchedGroup;
    public volatile boolean mDebugging;
    public volatile long mFgInteractionTime;
    public volatile boolean mHasActivities;
    public volatile boolean mHasCachedConfiguration;
    public volatile boolean mHasClientActivities;
    public volatile boolean mHasForegroundServices;
    public volatile boolean mHasImeService;
    public volatile boolean mHasOverlayUi;
    public boolean mHasPendingConfigurationChange;
    public volatile boolean mHasRecentTasks;
    public volatile boolean mHasTopUi;
    public ArrayList mInactiveActivities;
    public final ApplicationInfo mInfo;
    public volatile boolean mInstrumenting;
    public volatile boolean mInstrumentingWithBackgroundActivityStartPrivileges;
    public volatile long mInteractionEventTime;
    public volatile boolean mIsActivityConfigOverrideAllowed;
    public volatile long mLastActivityFinishTime;
    public volatile long mLastActivityLaunchTime;
    public final ProcessRecord mListener;
    public final String mName;
    public volatile boolean mNotResponding;
    public final Object mOwner;
    public int mPauseConfigurationDispatchCount;
    public volatile boolean mPendingUiClean;
    public volatile boolean mPerceptible;
    public volatile boolean mPersistent;
    public volatile int mPid;
    public int mRapidActivityLaunchCount;
    public ArrayMap mRemoteActivities;
    public volatile String mRequiredAbi;
    public volatile int mStoppedState;
    public IApplicationThread mThread;
    public final int mUid;
    public final boolean mUseFifoUiScheduling;
    public final int mUserId;
    public volatile boolean mUsingWrapper;
    public int mVrThreadTid;
    public volatile boolean mWasStoppedLogged;
    public volatile long mWhenUnimportant;
    public Session mWindowSession;
    public final ArrayList mPkgList = new ArrayList(1);
    public volatile int mCurProcState = 20;
    public volatile int mRepProcState = 20;
    public volatile int mCurAdj = -10000;
    public volatile int mInstrumentationSourceUid = -1;
    public final ArrayList mActivities = new ArrayList();
    public final ArrayList mRecentTasks = new ArrayList();
    public ActivityRecord mPreQTopResumedActivity = null;
    public final Configuration mLastReportedConfiguration = new Configuration();
    public int mLastTopActivityDeviceId = 0;
    public volatile int mActivityStateFlags = GnssNative.GNSS_AIDING_TYPE_ALL;
    public String mReason = null;

    /* JADX WARN: Type inference failed for: r10v1, types: [com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda4] */
    public WindowProcessController(
            final ActivityTaskManagerService activityTaskManagerService,
            ApplicationInfo applicationInfo,
            String str,
            int i,
            int i2,
            Object obj,
            ProcessRecord processRecord) {
        boolean z = true;
        this.mIsActivityConfigOverrideAllowed = true;
        this.mInfo = applicationInfo;
        this.mName = str;
        this.mUid = i;
        this.mUserId = i2;
        this.mOwner = obj;
        this.mListener = processRecord;
        this.mAtm = activityTaskManagerService;
        Objects.requireNonNull(activityTaskManagerService);
        this.mBgLaunchController =
                new BackgroundLaunchProcessController(
                        new IntPredicate() { // from class:
                                             // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda4
                            @Override // java.util.function.IntPredicate
                            public final boolean test(int i3) {
                                return ActivityTaskManagerService.this.hasActiveVisibleWindow(i3);
                            }
                        },
                        activityTaskManagerService.mBackgroundActivityStartCallback);
        boolean equals =
                applicationInfo.packageName.equals(
                        activityTaskManagerService
                                .getSysUiServiceComponentLocked()
                                .getPackageName());
        if (equals || UserHandle.getAppId(i) == 1000) {
            this.mIsActivityConfigOverrideAllowed = false;
            if (applicationInfo.packageName.equals("com.sec.android.app.desktoplauncher")) {
                this.mIsActivityConfigOverrideAllowed = true;
            }
        }
        if (!Flags.fifoPriorityForMajorUiProcesses()
                || (!equals && !activityTaskManagerService.mRecentTasks.isCallerRecents(i))) {
            z = false;
        }
        this.mUseFifoUiScheduling = z;
        onConfigurationChanged(activityTaskManagerService.getGlobalConfiguration());
        activityTaskManagerService.mPackageConfigPersister.updateConfigIfNeeded(
                this, i2, applicationInfo.packageName);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0023, code lost:

       if (com.android.server.grammaticalinflection.GrammaticalInflectionUtils.checkSystemGrammaticalGenderPermission(r2.mPermissionManager, r7) != false) goto L11;
    */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0040 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean applyConfigGenderOverride(
            android.content.res.Configuration r4,
            int r5,
            com.android.server.grammaticalinflection.GrammaticalInflectionService
                            .GrammaticalInflectionManagerInternalImpl
                    r6,
            int r7) {
        /*
            r0 = 0
            r1 = 1
            if (r6 == 0) goto L27
            r2 = 1000(0x3e8, float:1.401E-42)
            if (r7 != r2) goto L9
            goto L25
        L9:
            android.content.AttributionSource$Builder r2 = new android.content.AttributionSource$Builder
            r2.<init>(r7)
            android.content.AttributionSource r7 = r2.build()
            com.android.server.grammaticalinflection.GrammaticalInflectionService r2 = com.android.server.grammaticalinflection.GrammaticalInflectionService.this
            r2.getClass()
            boolean r3 = com.android.server.grammaticalinflection.GrammaticalInflectionService.checkSystemTermsOfAddressIsEnabled()
            if (r3 == 0) goto L27
            android.permission.PermissionManager r2 = r2.mPermissionManager
            boolean r7 = com.android.server.grammaticalinflection.GrammaticalInflectionUtils.checkSystemGrammaticalGenderPermission(r2, r7)
            if (r7 == 0) goto L27
        L25:
            r7 = r1
            goto L28
        L27:
            r7 = r0
        L28:
            if (r5 == 0) goto L2b
            goto L3a
        L2b:
            if (r7 == 0) goto L2f
            r5 = -1
            goto L3a
        L2f:
            if (r6 == 0) goto L39
            java.lang.String r5 = "persist.sys.grammatical_gender"
            int r5 = android.os.SystemProperties.getInt(r5, r0)
            goto L3a
        L39:
            r5 = r0
        L3a:
            int r6 = r4.getGrammaticalGenderRaw()
            if (r6 != r5) goto L41
            return r0
        L41:
            r4.setGrammaticalGender(r5)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowProcessController.applyConfigGenderOverride(android.content.res.Configuration,"
                    + " int,"
                    + " com.android.server.grammaticalinflection.GrammaticalInflectionService$GrammaticalInflectionManagerInternalImpl,"
                    + " int):boolean");
    }

    public final void addBoundClientUid(int i, String str, long j) {
        BackgroundLaunchProcessController backgroundLaunchProcessController =
                this.mBgLaunchController;
        backgroundLaunchProcessController.getClass();
        if (CompatChanges.isChangeEnabled(261072174L, str, UserHandle.getUserHandleForUid(i))
                && (j & 512) == 0) {
            return;
        }
        if (backgroundLaunchProcessController.mBalOptInBoundClientUids == null) {
            backgroundLaunchProcessController.mBalOptInBoundClientUids = new IntArray();
        }
        if (backgroundLaunchProcessController.mBalOptInBoundClientUids.indexOf(i) == -1) {
            backgroundLaunchProcessController.mBalOptInBoundClientUids.add(i);
        }
    }

    public final void addToPendingTop() {
        this.mAtm.mAmInternal.addPendingTopUid(this.mUid, this.mPid, this.mThread);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0024, code lost:

       if (r5.mPid != com.android.server.wm.WindowManagerService.MY_PID) goto L18;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void appEarlyNotResponding(
            java.lang.String r6,
            com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda0 r7) {
        /*
            r5 = this;
            com.android.server.wm.ActivityTaskManagerService r0 = r5.mAtm
            com.android.server.wm.WindowManagerGlobalLock r0 = r0.mGlobalLock
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
            monitor-enter(r0)
            com.android.server.wm.ActivityTaskManagerService r1 = r5.mAtm     // Catch: java.lang.Throwable -> L13
            android.app.IActivityController r1 = r1.mController     // Catch: java.lang.Throwable -> L13
            if (r1 != 0) goto L15
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L13
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            return
        L13:
            r5 = move-exception
            goto L3f
        L15:
            r2 = 0
            java.lang.String r3 = r5.mName     // Catch: java.lang.Throwable -> L13 android.os.RemoteException -> L2a
            int r4 = r5.mPid     // Catch: java.lang.Throwable -> L13 android.os.RemoteException -> L2a
            int r6 = r1.appEarlyNotResponding(r3, r4, r6)     // Catch: java.lang.Throwable -> L13 android.os.RemoteException -> L2a
            if (r6 >= 0) goto L27
            int r6 = r5.mPid     // Catch: java.lang.Throwable -> L13 android.os.RemoteException -> L2a
            int r5 = com.android.server.wm.WindowManagerService.MY_PID     // Catch: java.lang.Throwable -> L13 android.os.RemoteException -> L2a
            if (r6 == r5) goto L27
            goto L28
        L27:
            r7 = r2
        L28:
            r2 = r7
            goto L35
        L2a:
            com.android.server.wm.ActivityTaskManagerService r5 = r5.mAtm     // Catch: java.lang.Throwable -> L13
            r5.mController = r2     // Catch: java.lang.Throwable -> L13
            com.android.server.Watchdog r5 = com.android.server.Watchdog.getInstance()     // Catch: java.lang.Throwable -> L13
            r5.setActivityController(r2)     // Catch: java.lang.Throwable -> L13
        L35:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L13
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            if (r2 == 0) goto L3e
            r2.run()
        L3e:
            return
        L3f:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L13
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowProcessController.appEarlyNotResponding(java.lang.String,"
                    + " com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda0):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0027, code lost:

       if (r6.mPid != com.android.server.wm.WindowManagerService.MY_PID) goto L20;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean appNotResponding(
            java.lang.String r7,
            com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda4 r8,
            com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda4 r9) {
        /*
            r6 = this;
            com.android.server.wm.ActivityTaskManagerService r0 = r6.mAtm
            com.android.server.wm.WindowManagerGlobalLock r0 = r0.mGlobalLock
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
            monitor-enter(r0)
            com.android.server.wm.ActivityTaskManagerService r1 = r6.mAtm     // Catch: java.lang.Throwable -> L14
            android.app.IActivityController r1 = r1.mController     // Catch: java.lang.Throwable -> L14
            r2 = 0
            if (r1 != 0) goto L16
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L14
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            return r2
        L14:
            r6 = move-exception
            goto L49
        L16:
            r3 = 0
            java.lang.String r4 = r6.mName     // Catch: java.lang.Throwable -> L14 android.os.RemoteException -> L39
            int r5 = r6.mPid     // Catch: java.lang.Throwable -> L14 android.os.RemoteException -> L39
            int r7 = r1.appNotResponding(r4, r5, r7)     // Catch: java.lang.Throwable -> L14 android.os.RemoteException -> L39
            if (r7 == 0) goto L2c
            if (r7 >= 0) goto L2a
            int r7 = r6.mPid     // Catch: java.lang.Throwable -> L14 android.os.RemoteException -> L39
            int r6 = com.android.server.wm.WindowManagerService.MY_PID     // Catch: java.lang.Throwable -> L14 android.os.RemoteException -> L39
            if (r7 == r6) goto L2a
            goto L2d
        L2a:
            r8 = r9
            goto L2d
        L2c:
            r8 = r3
        L2d:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L14
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            if (r8 == 0) goto L38
            r8.run()
            r6 = 1
            return r6
        L38:
            return r2
        L39:
            com.android.server.wm.ActivityTaskManagerService r6 = r6.mAtm     // Catch: java.lang.Throwable -> L14
            r6.mController = r3     // Catch: java.lang.Throwable -> L14
            com.android.server.Watchdog r6 = com.android.server.Watchdog.getInstance()     // Catch: java.lang.Throwable -> L14
            r6.setActivityController(r3)     // Catch: java.lang.Throwable -> L14
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L14
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            return r2
        L49:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L14
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowProcessController.appNotResponding(java.lang.String,"
                    + " com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda4,"
                    + " com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda4):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x004d, code lost:

       r15 = true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.wm.BackgroundActivityStartController.BalVerdict
            areBackgroundActivityStartsAllowed(int r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 237
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowProcessController.areBackgroundActivityStartsAllowed(int,"
                    + " boolean):com.android.server.wm.BackgroundActivityStartController$BalVerdict");
    }

    public final boolean canCloseSystemDialogsByToken() {
        BackgroundLaunchProcessController backgroundLaunchProcessController =
                this.mBgLaunchController;
        int i = this.mUid;
        boolean z = false;
        if (backgroundLaunchProcessController.mBackgroundActivityStartCallback != null) {
            synchronized (backgroundLaunchProcessController) {
                try {
                    ArrayMap arrayMap =
                            backgroundLaunchProcessController.mBackgroundStartPrivileges;
                    if (arrayMap != null && !arrayMap.isEmpty()) {
                        NotificationManagerService.AnonymousClass2 anonymousClass2 =
                                backgroundLaunchProcessController.mBackgroundActivityStartCallback;
                        List originatingTokensThatAllowBal =
                                backgroundLaunchProcessController
                                        .getOriginatingTokensThatAllowBal();
                        anonymousClass2.getClass();
                        if (((ArrayList) originatingTokensThatAllowBal)
                                        .contains(NotificationManagerService.ALLOWLIST_TOKEN)
                                && !CompatChanges.isChangeEnabled(167676448L, i)) {
                            z = true;
                        }
                    }
                } finally {
                }
            }
        }
        return z;
    }

    public final void computeProcessActivityState() {
        int i;
        VisibleActivityProcessTracker.CpuTimeRecord cpuTimeRecord;
        VisibleActivityProcessTracker.CpuTimeRecord cpuTimeRecord2;
        ActivityRecord.State state;
        int i2;
        ActivityRecord.State state2 = ActivityRecord.State.DESTROYED;
        boolean hasResumedActivity = hasResumedActivity();
        boolean z = (this.mActivityStateFlags & 1114112) != 0;
        int i3 = Integer.MAX_VALUE;
        boolean z2 = true;
        int i4 = 0;
        boolean z3 = false;
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size);
            if (activityRecord.mVisible) {
                i4 |= 1048576;
            }
            Task task = activityRecord.task;
            if (task != null && task.mLayerRank != -1) {
                i4 |= 4194304;
            }
            if (activityRecord.isVisibleRequested()) {
                if (activityRecord.isState(ActivityRecord.State.RESUMED)) {
                    i4 |= 2097152;
                }
                if (task != null && i3 > 0 && (i2 = task.mLayerRank) >= 0 && i3 > i2) {
                    i3 = i2;
                }
                z3 = true;
            } else if (!z3 && state2 != (state = ActivityRecord.State.PAUSING)) {
                if (!activityRecord.isState(state, ActivityRecord.State.PAUSED)) {
                    state = ActivityRecord.State.STOPPING;
                    if (activityRecord.isState(state)) {
                        z2 &= activityRecord.finishing;
                    }
                }
                state2 = state;
            }
        }
        ArrayMap arrayMap = this.mRemoteActivities;
        if (arrayMap != null) {
            for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                if ((((int[]) this.mRemoteActivities.valueAt(size2))[0] & 2) != 0
                        && ((ActivityRecord) this.mRemoteActivities.keyAt(size2))
                                .isVisibleRequested()) {
                    i4 |= EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
                }
            }
        }
        int i5 = (65535 & i3) | i4;
        if (z3) {
            i5 |= EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
        } else {
            if (state2 == ActivityRecord.State.PAUSING) {
                i = 131072;
            } else if (state2 == ActivityRecord.State.STOPPING) {
                int i6 = 262144 | i5;
                if (z2) {
                    i = 786432;
                } else {
                    i5 = i6;
                }
            }
            i5 |= i;
        }
        this.mActivityStateFlags = i5;
        boolean z4 = (i5 & 1114112) != 0;
        if (!z && z4) {
            VisibleActivityProcessTracker visibleActivityProcessTracker =
                    this.mAtm.mVisibleActivityProcessTracker;
            visibleActivityProcessTracker.getClass();
            VisibleActivityProcessTracker.CpuTimeRecord cpuTimeRecord3 =
                    visibleActivityProcessTracker.new CpuTimeRecord(this);
            synchronized (visibleActivityProcessTracker.mProcMap) {
                visibleActivityProcessTracker.mProcMap.put(this, cpuTimeRecord3);
            }
            if (hasResumedActivity()) {
                cpuTimeRecord3.mShouldGetCpuTime = true;
                visibleActivityProcessTracker.mBgExecutor.execute(cpuTimeRecord3);
            }
            this.mAtm.mWindowManager.onProcessActivityVisibilityChanged(this.mUid, true);
            return;
        }
        if (z && !z4) {
            VisibleActivityProcessTracker visibleActivityProcessTracker2 =
                    this.mAtm.mVisibleActivityProcessTracker;
            synchronized (visibleActivityProcessTracker2.mProcMap) {
                cpuTimeRecord2 =
                        (VisibleActivityProcessTracker.CpuTimeRecord)
                                visibleActivityProcessTracker2.mProcMap.remove(this);
            }
            if (cpuTimeRecord2 != null && cpuTimeRecord2.mShouldGetCpuTime) {
                visibleActivityProcessTracker2.mBgExecutor.execute(cpuTimeRecord2);
            }
            this.mAtm.mWindowManager.onProcessActivityVisibilityChanged(this.mUid, false);
            return;
        }
        if (z && !hasResumedActivity && hasResumedActivity()) {
            VisibleActivityProcessTracker visibleActivityProcessTracker3 =
                    this.mAtm.mVisibleActivityProcessTracker;
            synchronized (visibleActivityProcessTracker3.mProcMap) {
                cpuTimeRecord =
                        (VisibleActivityProcessTracker.CpuTimeRecord)
                                visibleActivityProcessTracker3.mProcMap.get(this);
            }
            if (cpuTimeRecord == null || cpuTimeRecord.mShouldGetCpuTime) {
                return;
            }
            cpuTimeRecord.mShouldGetCpuTime = true;
            visibleActivityProcessTracker3.mBgExecutor.execute(cpuTimeRecord);
        }
    }

    public final int computeRelaunchReason() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                for (int size = this.mActivities.size() - 1; size >= 0; size--) {
                    int i = ((ActivityRecord) this.mActivities.get(size)).mRelaunchReason;
                    if (i != 0) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return i;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return 0;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean containsPackage(String str) {
        boolean contains;
        synchronized (this.mPkgList) {
            contains = this.mPkgList.contains(str);
        }
        return contains;
    }

    public final ProfilerInfo createProfilerInfoIfNeeded() {
        String str;
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        ProfilerInfo profilerInfo = activityTaskManagerService.mProfilerInfo;
        if (profilerInfo == null
                || profilerInfo.profileFile == null
                || (str = activityTaskManagerService.mProfileApp) == null
                || !str.equals(this.mName)) {
            return null;
        }
        WindowProcessController windowProcessController = activityTaskManagerService.mProfileProc;
        if (windowProcessController != null && windowProcessController != this) {
            return null;
        }
        ParcelFileDescriptor parcelFileDescriptor = profilerInfo.profileFd;
        if (parcelFileDescriptor != null) {
            try {
                profilerInfo.profileFd = parcelFileDescriptor.dup();
            } catch (IOException unused) {
                profilerInfo.closeFd();
            }
        }
        return new ProfilerInfo(profilerInfo);
    }

    public final void dispatchConfiguration(Configuration configuration) {
        this.mHasPendingConfigurationChange = false;
        IApplicationThread iApplicationThread = this.mThread;
        if (iApplicationThread == null) {
            if (Build.IS_DEBUGGABLE && this.mHasImeService) {
                ProfileService$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Unable to send config for IME proc "),
                        this.mName,
                        ": no app thread",
                        "ActivityTaskManager");
                return;
            }
            return;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        int i = activityTaskManagerService.mConfigurationSeq + 1;
        activityTaskManagerService.mConfigurationSeq = i;
        int max = Math.max(i, 1);
        activityTaskManagerService.mConfigurationSeq = max;
        configuration.seq = max;
        setLastReportedConfiguration(configuration);
        if (this.mRepProcState >= 16) {
            this.mHasCachedConfiguration = true;
            if (this.mRepProcState >= 16) {
                return;
            }
        }
        onConfigurationChangePreScheduled(configuration);
        scheduleClientTransactionItem(
                iApplicationThread,
                ConfigurationChangeItem.obtain(configuration, this.mLastTopActivityDeviceId));
    }

    public final void dump(PrintWriter printWriter, String str) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mActivities.size() > 0) {
                    printWriter.print("    ");
                    printWriter.println("Activities:");
                    for (int i = 0; i < this.mActivities.size(); i++) {
                        printWriter.print("    ");
                        printWriter.print("  - ");
                        printWriter.println(this.mActivities.get(i));
                    }
                }
                ArrayMap arrayMap = this.mRemoteActivities;
                if (arrayMap != null && !arrayMap.isEmpty()) {
                    printWriter.print("    ");
                    printWriter.println("Remote Activities:");
                    for (int size = this.mRemoteActivities.size() - 1; size >= 0; size--) {
                        printWriter.print("    ");
                        printWriter.print("  - ");
                        printWriter.print(this.mRemoteActivities.keyAt(size));
                        printWriter.print(" flags=");
                        int i2 = ((int[]) this.mRemoteActivities.valueAt(size))[0];
                        if ((i2 & 1) != 0) {
                            printWriter.print("host ");
                        }
                        if ((i2 & 2) != 0) {
                            printWriter.print("embedded");
                        }
                        printWriter.println();
                    }
                }
                if (this.mRecentTasks.size() > 0) {
                    printWriter.println("    Recent Tasks:");
                    for (int i3 = 0; i3 < this.mRecentTasks.size(); i3++) {
                        printWriter.println("      - " + this.mRecentTasks.get(i3));
                    }
                }
                if (this.mVrThreadTid != 0) {
                    printWriter.print("    ");
                    printWriter.print("mVrThreadTid=");
                    printWriter.println(this.mVrThreadTid);
                }
                this.mBgLaunchController.dump(printWriter);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        printWriter.println("     Configuration=" + getConfiguration());
        printWriter.println("     OverrideConfiguration=" + getRequestedOverrideConfiguration());
        StringBuilder sb = new StringBuilder("     mLastReportedConfiguration=");
        sb.append(
                this.mHasCachedConfiguration
                        ? "(cached) " + this.mLastReportedConfiguration
                        : this.mLastReportedConfiguration);
        printWriter.println(sb.toString());
        int i4 = this.mAnimatingReasons;
        if (i4 != 0) {
            printWriter.print("     mAnimatingReasons=");
            if ((i4 & 1) != 0) {
                printWriter.print("remote-animation|");
            }
            if ((i4 & 2) != 0) {
                printWriter.print("wakefulness|");
            }
            if ((i4 & 4) != 0) {
                printWriter.print("legacy-recents");
            }
            printWriter.println();
        }
        if (this.mUseFifoUiScheduling) {
            printWriter.println("     mUseFifoUiScheduling=true");
        }
        int i5 = this.mActivityStateFlags;
        if (i5 != 65535) {
            printWriter.print("     mActivityStateFlags=");
            if ((1048576 & i5) != 0) {
                printWriter.print("W|");
            }
            if ((65536 & i5) != 0) {
                printWriter.print("V|");
                if ((2097152 & i5) != 0) {
                    printWriter.print("R|");
                }
            } else if ((131072 & i5) != 0) {
                printWriter.print("P|");
            } else if ((262144 & i5) != 0) {
                printWriter.print("S|");
                if ((524288 & i5) != 0) {
                    printWriter.print("F|");
                }
            }
            if ((4194304 & i5) != 0) {
                printWriter.print("VT|");
            }
            int i6 = i5 & GnssNative.GNSS_AIDING_TYPE_ALL;
            if (i6 != 65535) {
                printWriter.print("taskLayer=" + i6);
            }
            printWriter.println();
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final ConfigurationContainer getChildAt(int i) {
        return null;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final int getChildCount() {
        return 0;
    }

    public DisplayArea getDisplayArea() {
        return null;
    }

    public final void getDisplayContextsWithErrorDialogs(List list) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                RootWindowContainer rootWindowContainer = this.mAtm.mWindowManager.mRoot;
                int i = this.mPid;
                for (int size = rootWindowContainer.mChildren.size() - 1; size >= 0; size--) {
                    DisplayContent displayContent =
                            (DisplayContent) rootWindowContainer.mChildren.get(size);
                    if (displayContent.getWindow(
                                    new RootWindowContainer$$ExternalSyntheticLambda13(i, 0))
                            != null) {
                        ((ArrayList) list).add(displayContent.mDisplayPolicy.mUiContext);
                    }
                }
                for (int size2 = this.mActivities.size() - 1; size2 >= 0; size2--) {
                    ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size2);
                    int displayId = activityRecord.getDisplayId();
                    Context context =
                            rootWindowContainer.getDisplayContent(displayId) != null
                                    ? rootWindowContainer.getDisplayContent(displayId)
                                            .mDisplayPolicy
                                            .mUiContext
                                    : null;
                    if (context != null && activityRecord.isVisibleRequested()) {
                        ArrayList arrayList = (ArrayList) list;
                        if (!arrayList.contains(context)) {
                            arrayList.add(context);
                        }
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final long getInputDispatchingTimeoutMillis() {
        long j;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                j =
                        (this.mInstrumenting || this.mUsingWrapper)
                                ? 60000L
                                : InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return j;
    }

    public final List getPackageList() {
        ArrayList arrayList;
        synchronized (this.mPkgList) {
            arrayList = new ArrayList(this.mPkgList);
        }
        return arrayList;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final ConfigurationContainer getParent() {
        return this.mAtm.mRootWindowContainer;
    }

    public final int[] getRemoteActivityFlags(ActivityRecord activityRecord) {
        if (this.mRemoteActivities == null) {
            this.mRemoteActivities = new ArrayMap();
        }
        int[] iArr = (int[]) this.mRemoteActivities.get(activityRecord);
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[1];
        this.mRemoteActivities.put(activityRecord, iArr2);
        return iArr2;
    }

    public final TaskDisplayArea getTopActivityDisplayArea() {
        if (this.mActivities.isEmpty()) {
            return null;
        }
        int size = this.mActivities.size();
        ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size - 1);
        TaskDisplayArea displayArea = activityRecord.getDisplayArea();
        for (int i = size - 2; i >= 0; i--) {
            ActivityRecord activityRecord2 = (ActivityRecord) this.mActivities.get(i);
            TaskDisplayArea displayArea2 = activityRecord2.getDisplayArea();
            if (activityRecord2.compareTo((WindowContainer) activityRecord) > 0
                    && displayArea2 != null) {
                activityRecord = activityRecord2;
                displayArea = displayArea2;
            }
        }
        return displayArea;
    }

    public final void handleAppCrash() {
        ArrayList arrayList = new ArrayList(this.mActivities);
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord = (ActivityRecord) arrayList.get(size);
            Slog.w(
                    "ActivityTaskManager",
                    "  Force finishing activity "
                            + activityRecord.mActivityComponent.flattenToShortString());
            activityRecord.detachFromProcess();
            DisplayContent displayContent = activityRecord.mDisplayContent;
            displayContent.prepareAppTransition(2, 16);
            displayContent.mTransitionController.requestTransitionIfNeeded(
                    2, 16, null, displayContent);
            activityRecord.destroyIfPossible("handleAppCrashed");
        }
    }

    public final boolean handleAppDied$1() {
        int i;
        Task task;
        ActivityRecord activityRecord;
        Task task2;
        WindowProcessController windowProcessController;
        boolean z;
        ActivityTaskSupervisor activityTaskSupervisor = this.mAtm.mTaskSupervisor;
        ActivityTaskSupervisor.removeHistoryRecords(
                activityTaskSupervisor.mStoppingActivities, this);
        ActivityTaskSupervisor.removeHistoryRecords(
                activityTaskSupervisor.mFinishingActivities, this);
        ActivityTaskSupervisor.removeHistoryRecords(
                activityTaskSupervisor.mNoHistoryActivities, this);
        ArrayList arrayList = this.mInactiveActivities;
        boolean z2 = (arrayList == null || arrayList.isEmpty()) ? false : true;
        ArrayList arrayList2 = (this.mHasActivities || z2) ? new ArrayList() : this.mActivities;
        if (this.mHasActivities) {
            arrayList2.addAll(this.mActivities);
        }
        if (z2) {
            arrayList2.addAll(this.mInactiveActivities);
        }
        if (this.mListener.mRemoved) {
            for (int size = arrayList2.size() - 1; size >= 0; size--) {
                ((ActivityRecord) arrayList2.get(size)).makeFinishingLocked();
            }
        }
        boolean z3 = false;
        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
            ActivityRecord activityRecord2 = (ActivityRecord) arrayList2.get(size2);
            if (activityRecord2.isVisibleRequested() || activityRecord2.mVisible) {
                z3 = true;
            }
            TaskFragment taskFragment = activityRecord2.getTaskFragment();
            if (taskFragment != null) {
                ActivityRecord activityRecord3 = taskFragment.mPausingActivity;
                if (activityRecord3 == null || activityRecord3.app != this) {
                    z = false;
                } else {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_STATES,
                                646076184396185067L,
                                0,
                                null,
                                String.valueOf(activityRecord3));
                    }
                    taskFragment.mPausingActivity = null;
                    z = true;
                }
                ActivityRecord activityRecord4 = taskFragment.mLastPausedActivity;
                if (activityRecord4 != null && activityRecord4.app == this) {
                    taskFragment.mLastPausedActivity = null;
                }
                z3 |= z;
            }
            boolean z4 =
                    Process.isSdkSandboxUid(activityRecord2.getUid())
                            || ((!((i = activityRecord2.mRelaunchReason) == 1 || i == 2)
                                            || activityRecord2.launchCount >= 3
                                            || activityRecord2.finishing)
                                    && (!(activityRecord2.mHaveState
                                                    || activityRecord2.stateNotNeeded
                                                    || activityRecord2.isState(
                                                            ActivityRecord.State
                                                                    .RESTARTING_PROCESS))
                                            || activityRecord2.finishing
                                            || (!activityRecord2.mVisibleRequested
                                                    && activityRecord2.launchCount > 2
                                                    && activityRecord2.lastLaunchTime
                                                            > SystemClock.uptimeMillis() - 60000)));
            if (z4) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[2]) {
                    ProtoLogImpl_54989576.i(
                            ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
                            587363723665813898L,
                            204,
                            null,
                            String.valueOf(activityRecord2),
                            Boolean.valueOf(activityRecord2.mHaveState),
                            String.valueOf(activityRecord2.stateNotNeeded),
                            Boolean.valueOf(activityRecord2.finishing),
                            String.valueOf(activityRecord2.mState),
                            String.valueOf(Debug.getCallers(5)));
                }
                if (!activityRecord2.finishing
                        || ((windowProcessController = activityRecord2.app) != null
                                && windowProcessController.mListener.mRemoved)) {
                    Slog.w(
                            "ActivityTaskManager",
                            "Force removing " + activityRecord2 + ": app died, no saved state");
                    int i2 = activityRecord2.mUserId;
                    int identityHashCode = System.identityHashCode(activityRecord2);
                    Task task3 = activityRecord2.task;
                    EventLog.writeEvent(
                            30001,
                            Integer.valueOf(i2),
                            Integer.valueOf(identityHashCode),
                            Integer.valueOf(task3 != null ? task3.mTaskId : -1),
                            activityRecord2.shortComponentName,
                            "proc died without state saved");
                }
            }
            Task task4 = activityRecord2.task;
            if (task4 != null && task4.mKillProcessesOnDestroyed) {
                ActivityTaskSupervisor activityTaskSupervisor2 = activityRecord2.mTaskSupervisor;
                activityTaskSupervisor2.getClass();
                if (activityRecord2.packageName.equals(task4.getBasePackageName())) {
                    task4.mKillProcessesOnDestroyed = false;
                    activityTaskSupervisor2.mHandler.removeMessages(206, task4);
                }
            }
            WindowContainer windowContainer =
                    (z4 && (task2 = activityRecord2.task) != null && task2.getChildCount() == 1)
                            ? activityRecord2.task
                            : activityRecord2;
            Transition requestCloseTransitionIfNeeded =
                    activityRecord2.mTransitionController.requestCloseTransitionIfNeeded(
                            windowContainer);
            if (requestCloseTransitionIfNeeded != null) {
                requestCloseTransitionIfNeeded.collectClose(windowContainer);
            } else if (activityRecord2.mTransitionController.isCollecting()) {
                activityRecord2.mTransitionController.mCollectingTransition.collectClose(
                        windowContainer);
            }
            activityRecord2.cleanUp(true, true);
            if (z4) {
                if (activityRecord2.mStartingData != null
                        && activityRecord2.mVisible
                        && (task = activityRecord2.task) != null
                        && (activityRecord = task.topRunningActivity(false)) != null
                        && !activityRecord.mVisible
                        && activityRecord.shouldBeVisible(false)) {
                    activityRecord.transferStartingWindow(activityRecord2);
                }
                activityRecord2.removeFromHistory("appDied");
            }
        }
        for (int size3 = this.mRecentTasks.size() - 1; size3 >= 0; size3--) {
            ((Task) this.mRecentTasks.get(size3)).clearRootProcess();
        }
        this.mRecentTasks.clear();
        this.mHasRecentTasks = false;
        this.mInactiveActivities = null;
        this.mActivities.clear();
        this.mHasActivities = false;
        updateActivityConfigurationListener(null);
        return z3;
    }

    public final boolean hasActivityInVisibleTask() {
        return (this.mActivityStateFlags & 4194304) != 0;
    }

    public final boolean hasResumedActivity() {
        return (this.mActivityStateFlags & 2097152) != 0;
    }

    public final boolean hasStartedActivity(ActivityRecord activityRecord) {
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord2 = (ActivityRecord) this.mActivities.get(size);
            if (activityRecord != activityRecord2 && !activityRecord2.mAppStopped) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasThread() {
        return this.mThread != null;
    }

    public final void onConfigurationChangePreScheduled(Configuration configuration) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_CONFIGURATION_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_CONFIGURATION,
                    -4629255026637000251L,
                    0,
                    null,
                    String.valueOf(this.mName),
                    String.valueOf(configuration));
        }
        if (Build.IS_DEBUGGABLE && this.mHasImeService) {
            Slog.v(
                    "ActivityTaskManager",
                    "Sending to IME proc " + this.mName + " new config " + configuration);
        }
        this.mHasCachedConfiguration = false;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final void onConfigurationChanged(Configuration configuration) {
        DisplayContent displayContent;
        super.onConfigurationChanged(configuration);
        ActivityRecord activityRecord = null;
        if (!this.mActivities.isEmpty()) {
            int size = this.mActivities.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                if (!((ActivityRecord) this.mActivities.get(size)).finishing) {
                    activityRecord = (ActivityRecord) this.mActivities.get(size);
                    break;
                }
                size--;
            }
        }
        boolean z = false;
        int deviceIdForDisplayId =
                (activityRecord == null
                                || (displayContent = activityRecord.mDisplayContent) == null)
                        ? 0
                        : this.mAtm.mTaskSupervisor.getDeviceIdForDisplayId(
                                displayContent.mDisplayId);
        if (deviceIdForDisplayId != this.mLastTopActivityDeviceId) {
            this.mLastTopActivityDeviceId = deviceIdForDisplayId;
            z = true;
        }
        Configuration configuration2 = getConfiguration();
        if (!(!z) || !this.mLastReportedConfiguration.equals(configuration2)) {
            if (this.mPauseConfigurationDispatchCount > 0) {
                this.mHasPendingConfigurationChange = true;
                return;
            } else {
                dispatchConfiguration(configuration2);
                return;
            }
        }
        if (configuration2.isOtherSeqNewer(this.mLastReportedConfiguration)) {
            configuration2.seq = this.mLastReportedConfiguration.seq;
        }
        if (Build.IS_DEBUGGABLE && this.mHasImeService) {
            StringBuilder sb = new StringBuilder("Current config: ");
            sb.append(configuration2);
            sb.append(" unchanged for IME proc ");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    sb, this.mName, "ActivityTaskManager");
        }
    }

    public final void onTopProcChanged() {
        if ((this.mAtm.mVrController.mVrState & 3) != 0) {
            this.mAtm.mH.post(
                    new Runnable() { // from class:
                                     // com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            WindowProcessController windowProcessController =
                                    WindowProcessController.this;
                            WindowManagerGlobalLock windowManagerGlobalLock =
                                    windowProcessController.mAtm.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock) {
                                try {
                                    VrController vrController =
                                            windowProcessController.mAtm.mVrController;
                                    vrController.getClass();
                                    int i = windowProcessController.mCurSchedGroup;
                                    if (i == 3) {
                                        vrController.setVrRenderThreadLocked(
                                                windowProcessController.mVrThreadTid, i, true);
                                    } else if (windowProcessController.mVrThreadTid
                                            == vrController.mVrRenderThreadTid) {
                                        vrController.updateVrRenderThreadLocked(0, true);
                                    }
                                } catch (Throwable th) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    });
        }
    }

    public final void registerActivityConfigurationListener(ActivityRecord activityRecord) {
        if (activityRecord == null
                || activityRecord.containsListener(this)
                || !this.mIsActivityConfigOverrideAllowed) {
            return;
        }
        unregisterConfigurationListeners();
        this.mConfigActivityRecord = activityRecord;
        this.mConfigTask = activityRecord.task;
        activityRecord.registerConfigurationChangeListener(this);
    }

    public boolean registeredForActivityConfigChanges() {
        return this.mConfigActivityRecord != null;
    }

    public final void releaseSomeActivities() {
        ArrayList arrayList = null;
        for (int i = 0; i < this.mActivities.size(); i++) {
            ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(i);
            if (activityRecord.finishing
                    || activityRecord.isState(
                            ActivityRecord.State.DESTROYING, ActivityRecord.State.DESTROYED)) {
                return;
            }
            if (!activityRecord.isVisibleRequested()
                    && activityRecord.mAppStopped
                    && activityRecord.mHaveState
                    && activityRecord.isDestroyable()
                    && !activityRecord.isState$1(
                            ActivityRecord.State.RESUMED,
                            ActivityRecord.State.PAUSING,
                            ActivityRecord.State.PAUSED,
                            ActivityRecord.State.STOPPING)
                    && activityRecord.getParent() != null) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(activityRecord);
            }
        }
        if (arrayList != null) {
            arrayList.sort(new WindowProcessController$$ExternalSyntheticLambda0());
            int max = Math.max(arrayList.size(), 1);
            do {
                ((ActivityRecord) arrayList.remove(0)).destroyImmediately("low-mem");
                max--;
            } while (max > 0);
        }
    }

    public final void removeActivity(boolean z, ActivityRecord activityRecord) {
        if (z) {
            ArrayList arrayList = this.mInactiveActivities;
            if (arrayList == null) {
                ArrayList arrayList2 = new ArrayList();
                this.mInactiveActivities = arrayList2;
                arrayList2.add(activityRecord);
            } else if (!arrayList.contains(activityRecord)) {
                this.mInactiveActivities.add(activityRecord);
            }
        } else {
            ArrayList arrayList3 = this.mInactiveActivities;
            if (arrayList3 != null) {
                arrayList3.remove(activityRecord);
            }
        }
        this.mActivities.remove(activityRecord);
        this.mHasActivities = !this.mActivities.isEmpty();
        updateActivityConfigurationListener(null);
    }

    public final void removeAnimatingReason(int i) {
        int i2 = this.mAnimatingReasons;
        int i3 = (~i) & i2;
        this.mAnimatingReasons = i3;
        if (i2 == 0 || i3 != 0) {
            return;
        }
        this.mAtm.mH.post(new WindowProcessController$$ExternalSyntheticLambda1(this, false));
    }

    public final void removeRemoteActivityFlags(int i, ActivityRecord activityRecord) {
        int indexOfKey;
        ArrayMap arrayMap = this.mRemoteActivities;
        if (arrayMap != null && (indexOfKey = arrayMap.indexOfKey(activityRecord)) >= 0) {
            int[] iArr = (int[]) this.mRemoteActivities.valueAt(indexOfKey);
            int i2 = (~i) & iArr[0];
            iArr[0] = i2;
            if (i2 == 0) {
                this.mRemoteActivities.removeAt(indexOfKey);
            }
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final void resolveOverrideConfiguration(Configuration configuration) {
        Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
        int i = requestedOverrideConfiguration.assetsSeq;
        if (i != 0 && configuration.assetsSeq > i) {
            requestedOverrideConfiguration.assetsSeq = 0;
        }
        super.resolveOverrideConfiguration(configuration);
        Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        resolvedOverrideConfiguration.windowConfiguration.setActivityType(0);
        resolvedOverrideConfiguration.seq = configuration.seq;
        if (this.mIsActivityConfigOverrideAllowed) {
            resolvedOverrideConfiguration.overrideUndefinedFrom(this.mLastReportedConfiguration);
        }
        if (this.mConfigActivityRecord != null) {
            return;
        }
        WindowManagerService windowManagerService = this.mAtm.mWindowManager;
        ConfigurationContainer.applySizeOverrideIfNeeded(
                windowManagerService != null
                        ? windowManagerService.getDefaultDisplayContentLocked()
                        : null,
                this.mInfo,
                configuration,
                resolvedOverrideConfiguration,
                false,
                false,
                false,
                null);
    }

    public final boolean resumeConfigurationDispatch() {
        int i = this.mPauseConfigurationDispatchCount;
        if (i == 0) {
            return false;
        }
        this.mPauseConfigurationDispatchCount = i - 1;
        return this.mHasPendingConfigurationChange;
    }

    public final void scheduleClientTransactionItem(
            IApplicationThread iApplicationThread, ClientTransactionItem clientTransactionItem) {
        Object obj = this.mOwner;
        try {
            Session session = this.mWindowSession;
            ActivityTaskManagerService activityTaskManagerService = this.mAtm;
            if (session == null || !(!session.mAddedWindows.isEmpty())) {
                activityTaskManagerService.mLifecycleManager.getClass();
                ClientTransaction obtain = ClientTransaction.obtain(iApplicationThread);
                obtain.addTransactionItem(clientTransactionItem);
                ClientLifecycleManager.scheduleTransaction(obtain);
            } else {
                activityTaskManagerService.mLifecycleManager.scheduleTransactionItem(
                        iApplicationThread, clientTransactionItem);
            }
        } catch (DeadObjectException unused) {
            Slog.w(
                    "ActivityTaskManager",
                    "Failed for dead process. ClientTransactionItem="
                            + clientTransactionItem
                            + " owner="
                            + obj);
        } catch (Exception e) {
            Slog.e(
                    "ActivityTaskManager",
                    "Failed to schedule ClientTransactionItem="
                            + clientTransactionItem
                            + " owner="
                            + obj,
                    e);
        }
    }

    public void scheduleClientTransactionItem(ClientTransactionItem clientTransactionItem) {
        IApplicationThread iApplicationThread = this.mThread;
        if (iApplicationThread != null) {
            scheduleClientTransactionItem(iApplicationThread, clientTransactionItem);
        } else if (Build.IS_DEBUGGABLE) {
            ProfileService$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Unable to send transaction to client proc "),
                    this.mName,
                    ": no app thread",
                    "ActivityTaskManager");
        }
    }

    public final void setLastReportedConfiguration(Configuration configuration) {
        synchronized (this.mLastReportedConfiguration) {
            this.mLastReportedConfiguration.setTo(configuration);
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final boolean setOverrideGender(Configuration configuration, int i) {
        return applyConfigGenderOverride(
                configuration, i, this.mAtm.mGrammaticalManagerInternal, this.mUid);
    }

    public final void setThread(IApplicationThread iApplicationThread) {
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                this.mThread = iApplicationThread;
                if (iApplicationThread != null) {
                    setLastReportedConfiguration(getConfiguration());
                } else {
                    VisibleActivityProcessTracker visibleActivityProcessTracker =
                            this.mAtm.mVisibleActivityProcessTracker;
                    synchronized (visibleActivityProcessTracker.mProcMap) {
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopFreezingActivities() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int size = this.mActivities.size();
                while (size > 0) {
                    size--;
                    ((ActivityRecord) this.mActivities.get(size)).stopFreezingScreen(true);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final String toString() {
        Object obj = this.mOwner;
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public final void unregisterActivityConfigurationListener() {
        ActivityRecord activityRecord = this.mConfigActivityRecord;
        if (activityRecord == null) {
            return;
        }
        activityRecord.unregisterConfigurationChangeListener(this);
        this.mConfigTask = null;
        this.mConfigActivityRecord = null;
        if (this.mIsActivityConfigOverrideAllowed) {
            onRequestedOverrideConfigurationChanged(Configuration.EMPTY);
            return;
        }
        Configuration configuration = new Configuration();
        configuration.windowConfiguration.setPopOverState(2);
        onRequestedOverrideConfigurationChanged(configuration);
    }

    public final void unregisterConfigurationListeners() {
        unregisterActivityConfigurationListener();
        unregisterDisplayAreaConfigurationListener();
    }

    public void unregisterDisplayAreaConfigurationListener() {}

    public final void updateActivityConfigurationListener(ActivityRecord activityRecord) {
        if (this.mIsActivityConfigOverrideAllowed) {
            if (activityRecord != null) {
                if (activityRecord.task.equals(this.mConfigTask)) {
                    return;
                }
                registerActivityConfigurationListener(activityRecord);
                return;
            }
            for (int size = this.mActivities.size() - 1; size >= 0; size--) {
                ActivityRecord activityRecord2 = (ActivityRecord) this.mActivities.get(size);
                if (!activityRecord2.finishing) {
                    registerActivityConfigurationListener(activityRecord2);
                    return;
                }
            }
            unregisterActivityConfigurationListener();
        }
    }

    public final void updateActivityInfo(ApplicationInfo applicationInfo) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                for (int size = this.mActivities.size() - 1; size >= 0; size--) {
                    ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size);
                    ActivityInfo activityInfo = activityRecord.info;
                    if (activityInfo != null && activityInfo.applicationInfo != null) {
                        if (applicationInfo.packageName.equals(activityRecord.packageName)) {
                            ApplicationInfo applicationInfo2 = activityRecord.info.applicationInfo;
                            applicationInfo2.resourceDirs = applicationInfo.resourceDirs;
                            applicationInfo2.overlayPaths = applicationInfo.overlayPaths;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void updateAssetConfiguration(int i) {
        if (!this.mHasActivities || !this.mIsActivityConfigOverrideAllowed) {
            Configuration configuration = new Configuration(getRequestedOverrideConfiguration());
            configuration.assetsSeq = i;
            onRequestedOverrideConfigurationChanged(configuration);
            return;
        }
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord = (ActivityRecord) this.mActivities.get(size);
            Configuration configuration2 =
                    new Configuration(activityRecord.getRequestedOverrideConfiguration());
            configuration2.assetsSeq = i;
            activityRecord.onRequestedOverrideConfigurationChanged(configuration2);
            if (activityRecord.isVisibleRequested()) {
                activityRecord.ensureActivityConfiguration(false);
            }
        }
    }

    public final void updateProcessInfo(boolean z, boolean z2, boolean z3, boolean z4) {
        if (z4) {
            addToPendingTop();
        }
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        if (z3) {
            activityTaskManagerService.mRootWindowContainer.rankTaskLayers();
            activityTaskManagerService.mTaskSupervisor.computeProcessActivityStateBatch();
        }
        activityTaskManagerService.mH.sendMessage(
                PooledLambda.obtainMessage(
                        new WindowProcessController$$ExternalSyntheticLambda2(),
                        this.mListener,
                        Boolean.valueOf(z),
                        Boolean.valueOf(z2),
                        Boolean.valueOf(z3)));
    }
}
