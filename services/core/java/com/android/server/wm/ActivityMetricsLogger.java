package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.dex.ArtManagerInternal;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.am.KillPolicyManager;
import com.android.server.apphibernation.AppHibernationService;
import com.android.server.chimera.ChimeraManagerService;
import com.android.server.chimera.genie.GenieMemoryManager;
import com.android.server.chimera.umr.ForegroundAppTracker;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;

import com.samsung.android.ipm.SecIpmManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ActivityMetricsLogger {
    public static final String[] TRON_WINDOW_STATE_VARZ_STRINGS = {
        "window_time_0", "window_time_1", "window_time_2", "window_time_3", "window_time_4"
    };
    public AppHibernationService.LocalService mAppHibernationManagerInternal;
    public ArtManagerInternal mArtManagerInternal;
    public final KillPolicyManager mKillPolicyManager;
    public final LaunchObserverRegistryImpl mLaunchObserver;
    public SecIpmManager mSecIpmManager;
    public final ActivityTaskSupervisor mSupervisor;
    public int mWindowState = 0;
    public final MetricsLogger mMetricsLogger = new MetricsLogger();
    public final Handler mLoggerHandler = FgThread.getHandler();
    public final ArrayList mTransitionInfoList = new ArrayList();
    public final ArrayList mInTransitionInfoList = new ArrayList();
    public final ArrayMap mLastTransitionInfo = new ArrayMap();
    public final SparseArray mPackageUidToCompatStateInfo = new SparseArray(0);
    public final StringBuilder mStringBuilder = new StringBuilder();
    public final ArrayMap mLastHibernationStates = new ArrayMap();
    public final HashMap mConvertInt2Str =
            new HashMap() { // from class: com.android.server.wm.ActivityMetricsLogger.1
                {
                    put(1, "SPLASH_SCREEN");
                    put(2, "WINDOWS_DRAWN");
                    put(3, "T_TIMEOUT");
                    put(4, "T_SNAPSHOT");
                    put(5, "R_ANIM");
                    put(107, "COLD");
                    put(108, "WARM");
                    put(109, "HOT");
                    put(200, "error");
                    put(202, "assume-verified");
                    put(203, "extract");
                    put(204, "verify");
                    put(205, "quicken");
                    put(206, "space-profile");
                    put(207, "space");
                    put(208, "speed-profile");
                    put(209, "speed");
                    put(210, "everything-profile");
                    put(211, "everything");
                    put(212, "run-from-apk");
                    put(213, "run-from-apk-fallback");
                    put(
                            Integer.valueOf(
                                    FrameworkStatsLog
                                            .DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FINISHED),
                            "run-from-vdex-fallback");
                }
            };
    public long mLastLogTimeSecs = SystemClock.elapsedRealtime() / 1000;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LaunchingState {
        public static int sTraceSeqId;
        public ActivityRecord mAssociatedActivity;
        public TransitionInfo mAssociatedTransitionInfo;
        public String mTraceName;
        public final long mStartUptimeNs = SystemClock.uptimeNanos();
        public final long mStartRealtimeNs = SystemClock.elapsedRealtimeNanos();

        public LaunchingState() {
            if (Trace.isTagEnabled(64L)) {
                sTraceSeqId++;
                String str = "launchingActivity#" + sTraceSeqId;
                this.mTraceName = str;
                Trace.asyncTraceBegin(64L, str, 0);
            }
        }

        public boolean allDrawn() {
            TransitionInfo transitionInfo = this.mAssociatedTransitionInfo;
            return transitionInfo != null && transitionInfo.mIsDrawn;
        }

        public final void stopTrace(boolean z, TransitionInfo transitionInfo) {
            String str;
            String sb;
            String str2 = this.mTraceName;
            if (str2 == null) {
                return;
            }
            if (z || transitionInfo == this.mAssociatedTransitionInfo) {
                Trace.asyncTraceEnd(64L, str2, 0);
                TransitionInfo transitionInfo2 = this.mAssociatedTransitionInfo;
                if (transitionInfo2 == null) {
                    sb = ":failed";
                } else {
                    if (z) {
                        str = ":canceled:";
                    } else if (transitionInfo2.mProcessSwitch) {
                        int i = transitionInfo.mTransitionType;
                        str =
                                i == 9
                                        ? ":completed-hot:"
                                        : i == 8 ? ":completed-warm:" : ":completed-cold:";
                    } else {
                        str = ":completed-same-process:";
                    }
                    StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
                    m.append(this.mAssociatedTransitionInfo.mLastLaunchedActivity.packageName);
                    sb = m.toString();
                }
                Trace.instant(64L, this.mTraceName + sb);
                this.mTraceName = null;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageCompatStateInfo {
        public ActivityRecord mLastLoggedActivity;
        public final ArrayList mVisibleActivities = new ArrayList();
        public int mLastLoggedState = 1;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TransitionInfo {
        public int mCurrentTransitionDelayMs;
        public boolean mIsDrawn;
        public final boolean mIsInTaskActivityStart;
        public ActivityRecord mLastLaunchedActivity;
        public String mLaunchTraceName;
        public final LaunchingState mLaunchingState;
        public boolean mLoggedStartingWindowDrawn;
        public boolean mLoggedTransitionStarting;
        public ActivityMetricsLogger$$ExternalSyntheticLambda3 mPendingFullyDrawn;
        public final int mProcessOomAdj;
        public boolean mProcessRunning;
        public final int mProcessState;
        public final boolean mProcessSwitch;
        public boolean mRelaunched;
        public final int mSourceEventDelayMs;
        public final int mSourceType;
        public int mTransitionType;
        public int mWindowsDrawnDelayMs;
        public int mStartingWindowDelayMs = -1;
        public int mBindApplicationDelayMs = -1;
        public int mReason = 3;
        public int mMultiWindowLaunchType = 0;

        public TransitionInfo(
                ActivityRecord activityRecord,
                LaunchingState launchingState,
                ActivityOptions activityOptions,
                int i,
                boolean z,
                boolean z2,
                int i2,
                int i3,
                boolean z3) {
            ActivityOptions.SourceInfo sourceInfo;
            this.mSourceEventDelayMs = -1;
            this.mLaunchingState = launchingState;
            this.mTransitionType = i;
            this.mProcessRunning = z;
            this.mProcessSwitch = z2;
            this.mProcessState = i2;
            this.mProcessOomAdj = i3;
            this.mIsInTaskActivityStart = z3;
            ActivityRecord activityRecord2 = this.mLastLaunchedActivity;
            if (activityRecord2 != activityRecord) {
                if (activityRecord2 != null) {
                    activityRecord.mLaunchCookie = activityRecord2.mLaunchCookie;
                    activityRecord2.mLaunchCookie = null;
                    activityRecord.mLaunchRootTask = activityRecord2.mLaunchRootTask;
                    activityRecord2.mLaunchRootTask = null;
                }
                this.mLastLaunchedActivity = activityRecord;
                this.mIsDrawn = activityRecord.mReportedDrawn;
            }
            if (launchingState.mAssociatedTransitionInfo == null) {
                launchingState.mAssociatedTransitionInfo = this;
                if (launchingState.mAssociatedActivity != null) {
                    Slog.d(
                            "ActivityTaskManager",
                            "TransitionInfo: copy mLastLaunchedActivity from mAssociatedActivity="
                                    + launchingState.mAssociatedActivity);
                    this.mLastLaunchedActivity = launchingState.mAssociatedActivity;
                }
            }
            if (activityOptions == null || (sourceInfo = activityOptions.getSourceInfo()) == null) {
                return;
            }
            this.mSourceType = sourceInfo.type;
            this.mSourceEventDelayMs =
                    (int)
                            (TimeUnit.NANOSECONDS.toMillis(launchingState.mStartUptimeNs)
                                    - sourceInfo.eventTimeMs);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("TransitionInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" a=");
            sb.append(this.mLastLaunchedActivity);
            sb.append(" d=");
            return OptionalBool$$ExternalSyntheticOutline0.m("}", sb, this.mIsDrawn);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TransitionInfoSnapshot {
        public final int activityRecordIdHashCode;
        public final ApplicationInfo applicationInfo;
        public final int bindApplicationDelayMs;
        public final String launchedActivityAppRecordRequiredAbi;
        public final String launchedActivityLaunchToken;
        public final String launchedActivityLaunchedFromPackage;
        public final String launchedActivityName;
        public final String launchedActivityShortComponentName;
        public final int multiWindowLaunchType;
        public final String packageName;
        public final String processName;
        public final WindowProcessController processRecord;
        public final int reason;
        public final boolean relaunched;
        final int sourceEventDelayMs;
        final int sourceType;
        public final int startingWindowDelayMs;
        public final long timestampNs;
        public final int type;
        public final int userId;
        public final int windowsDrawnDelayMs;
        public final int windowsFullyDrawnDelayMs;

        public TransitionInfoSnapshot(
                TransitionInfo transitionInfo, ActivityRecord activityRecord, int i) {
            ActivityInfo activityInfo = activityRecord.info;
            this.applicationInfo = activityInfo.applicationInfo;
            this.packageName = activityRecord.packageName;
            this.launchedActivityName = activityInfo.name;
            this.launchedActivityLaunchedFromPackage = activityRecord.launchedFromPackage;
            this.launchedActivityLaunchToken = activityInfo.launchToken;
            WindowProcessController windowProcessController = activityRecord.app;
            this.launchedActivityAppRecordRequiredAbi =
                    windowProcessController == null ? null : windowProcessController.mRequiredAbi;
            this.reason = transitionInfo.mReason;
            this.sourceEventDelayMs = transitionInfo.mSourceEventDelayMs;
            this.startingWindowDelayMs = transitionInfo.mStartingWindowDelayMs;
            this.bindApplicationDelayMs = transitionInfo.mBindApplicationDelayMs;
            this.windowsDrawnDelayMs = transitionInfo.mWindowsDrawnDelayMs;
            this.type = transitionInfo.mTransitionType;
            this.processRecord = activityRecord.app;
            this.processName = activityRecord.processName;
            this.sourceType = transitionInfo.mSourceType;
            this.userId = activityRecord.mUserId;
            this.launchedActivityShortComponentName = activityRecord.shortComponentName;
            this.activityRecordIdHashCode = System.identityHashCode(activityRecord);
            this.windowsFullyDrawnDelayMs = i;
            this.relaunched = transitionInfo.mRelaunched;
            this.timestampNs = transitionInfo.mLaunchingState.mStartRealtimeNs;
            this.multiWindowLaunchType = transitionInfo.mMultiWindowLaunchType;
        }
    }

    public ActivityMetricsLogger(ActivityTaskSupervisor activityTaskSupervisor, Looper looper) {
        this.mKillPolicyManager = null;
        this.mSupervisor = activityTaskSupervisor;
        this.mLaunchObserver = new LaunchObserverRegistryImpl(looper);
        int[] iArr = KillPolicyManager.SWAPPINESS;
        this.mKillPolicyManager = KillPolicyManager.KpmClassLazy.INSTANCE;
    }

    public static int getAppStartTransitionType(int i, boolean z) {
        if (i == 7) {
            return 3;
        }
        if (i == 8) {
            return 1;
        }
        if (i == 9) {
            return z ? 4 : 2;
        }
        return 0;
    }

    public static void logAppCompatStateInternal(
            ActivityRecord activityRecord, int i, PackageCompatStateInfo packageCompatStateInfo) {
        packageCompatStateInfo.mLastLoggedState = i;
        packageCompatStateInfo.mLastLoggedActivity = activityRecord;
        int i2 = activityRecord.info.applicationInfo.uid;
        int i3 = 3;
        int i4 = 5;
        int i5 = 1;
        if (i == 5 || i == 4 || i == 3) {
            AppCompatReachabilityOverrides appCompatReachabilityOverrides =
                    activityRecord
                            .mAppCompatController
                            .mAppCompatOverrides
                            .mAppCompatReachabilityOverrides;
            boolean isHorizontalReachabilityEnabled =
                    appCompatReachabilityOverrides.isHorizontalReachabilityEnabled(
                            appCompatReachabilityOverrides
                                    .mActivityRecord
                                    .getParent()
                                    .getConfiguration());
            AppCompatDeviceStateQuery appCompatDeviceStateQuery =
                    appCompatReachabilityOverrides.mAppCompatDeviceStateQuery;
            AppCompatConfiguration appCompatConfiguration =
                    appCompatReachabilityOverrides.mAppCompatConfiguration;
            if (isHorizontalReachabilityEnabled) {
                int letterboxPositionForHorizontalReachability =
                        appCompatConfiguration.mAppCompatConfigurationPersister
                                .getLetterboxPositionForHorizontalReachability(
                                        appCompatDeviceStateQuery.isDisplayFullScreenAndInPosture(
                                                false));
                if (letterboxPositionForHorizontalReachability != 0) {
                    if (letterboxPositionForHorizontalReachability == 1) {
                        i3 = 2;
                    } else {
                        if (letterboxPositionForHorizontalReachability != 2) {
                            throw new AssertionError(
                                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                                            letterboxPositionForHorizontalReachability,
                                            "Unexpected letterbox horizontal reachability position"
                                                + " type: "));
                        }
                        i3 = 4;
                    }
                }
                i5 = i3;
            } else if (appCompatReachabilityOverrides.isVerticalReachabilityEnabled(
                    appCompatReachabilityOverrides
                            .mActivityRecord
                            .getParent()
                            .getConfiguration())) {
                int letterboxPositionForVerticalReachability =
                        appCompatConfiguration.mAppCompatConfigurationPersister
                                .getLetterboxPositionForVerticalReachability(
                                        appCompatDeviceStateQuery.isDisplayFullScreenAndInPosture(
                                                true));
                if (letterboxPositionForVerticalReachability != 0) {
                    if (letterboxPositionForVerticalReachability == 1) {
                        i4 = 2;
                    } else {
                        if (letterboxPositionForVerticalReachability != 2) {
                            throw new AssertionError(
                                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                                            letterboxPositionForVerticalReachability,
                                            "Unexpected letterbox vertical reachability position"
                                                + " type: "));
                        }
                        i4 = 6;
                    }
                }
                i5 = i4;
            } else {
                i5 = 0;
            }
        }
        FrameworkStatsLog.write(FrameworkStatsLog.APP_COMPAT_STATE_CHANGED, i2, i, i5);
    }

    public static void startLaunchTrace(TransitionInfo transitionInfo) {
        String str = transitionInfo.mLastLaunchedActivity.packageName;
        GenieMemoryManager genieMemoryManager = ChimeraManagerService.mGenieMemoryManager;
        if (genieMemoryManager != null
                && genieMemoryManager.mSessionStatus == 1
                && !str.equals(genieMemoryManager.mName)) {
            Log.i("GenieMemoryManager", "Session stopping due to App Launch ".concat(str));
            genieMemoryManager.setGenieSessionEnd();
        }
        boolean z = UnifiedMemoryReclaimer.MODEL_UMR_ENABLED;
        ForegroundAppTracker.ForegroundMonitor foregroundMonitor =
                ForegroundAppTracker.mForegroundMonitor;
        if (!KnoxCustomManagerService.LAUNCHER_PACKAGE.equals(str)) {
            if (ForegroundAppTracker.IS_DEBUG_ENABLED) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        "B|app_launch ", str, "ForegroundAppTracker");
            }
            ForegroundAppTracker.ForegroundMonitor foregroundMonitor2 =
                    ForegroundAppTracker.getForegroundMonitor();
            if (foregroundMonitor2 != null) {
                if ("com.sec.android.app.camera".equals(str)) {
                    ForegroundAppTracker.ForegroundMonitor.setCameraLaunch(true);
                }
                foregroundMonitor2.setAppLaunch(true);
            }
        }
        LaunchingState launchingState = transitionInfo.mLaunchingState;
        if (launchingState.mTraceName == null) {
            return;
        }
        String str2 = "launching: " + transitionInfo.mLastLaunchedActivity.packageName;
        transitionInfo.mLaunchTraceName = str2;
        Trace.asyncTraceBegin(64L, str2, (int) launchingState.mStartRealtimeNs);
    }

    public static void stopLaunchTrace(TransitionInfo transitionInfo) {
        String str = transitionInfo.mLastLaunchedActivity.packageName;
        GenieMemoryManager genieMemoryManager = ChimeraManagerService.mGenieMemoryManager;
        boolean z = UnifiedMemoryReclaimer.MODEL_UMR_ENABLED;
        if (ForegroundAppTracker.IS_DEBUG_ENABLED) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "E|app_launch ", str, "ForegroundAppTracker");
        }
        String str2 = transitionInfo.mLaunchTraceName;
        if (str2 == null) {
            return;
        }
        Trace.asyncTraceEnd(64L, str2, (int) transitionInfo.mLaunchingState.mStartRealtimeNs);
        transitionInfo.mLaunchTraceName = null;
    }

    public final void abort(LaunchingState launchingState, String str) {
        TransitionInfo transitionInfo = launchingState.mAssociatedTransitionInfo;
        if (transitionInfo != null) {
            done(true, transitionInfo, str, 0L);
            return;
        }
        launchingState.stopTrace(true, null);
        Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyIntentFailed");
        this.mLaunchObserver.onIntentFailed(launchingState.mStartUptimeNs);
        Trace.traceEnd(64L);
    }

    public final String convertFilter(int i) {
        String str = (String) this.mConvertInt2Str.get(Integer.valueOf(i + 200));
        if (str == null) {
            str = "unknown";
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "(");
        m.append(Integer.toString(i));
        m.append(")");
        return m.toString();
    }

    public final String convertReason(int i) {
        String str = (String) this.mConvertInt2Str.get(Integer.valueOf(i));
        if (str == null) {
            str = Integer.toString(i);
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "(");
        m.append(Integer.toString(i));
        m.append(")");
        return m.toString();
    }

    public final String convertTRtype(int i) {
        String str = (String) this.mConvertInt2Str.get(Integer.valueOf(i + 100));
        if (str == null) {
            str = "UNKNOWN";
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "(");
        m.append(Integer.toString(i));
        m.append(")");
        return m.toString();
    }

    public final void done(boolean z, final TransitionInfo transitionInfo, String str, long j) {
        ActivityMetricsLogger activityMetricsLogger;
        transitionInfo.mLaunchingState.stopTrace(z, transitionInfo);
        stopLaunchTrace(transitionInfo);
        Boolean bool =
                (Boolean)
                        this.mLastHibernationStates.remove(
                                transitionInfo.mLastLaunchedActivity.packageName);
        ActivityTaskSupervisor activityTaskSupervisor = this.mSupervisor;
        LaunchingState launchingState = transitionInfo.mLaunchingState;
        if (z) {
            this.mLastTransitionInfo.remove(transitionInfo.mLastLaunchedActivity);
            activityTaskSupervisor.reportActivityLaunched(
                    false, transitionInfo.mLastLaunchedActivity, -1L, 0);
            Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyActivityLaunchCancelled");
            this.mLaunchObserver.onActivityLaunchCancelled(launchingState.mStartUptimeNs);
            Trace.traceEnd(64L);
        } else {
            if (transitionInfo.mProcessSwitch) {
                Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyActivityLaunchFinished");
                long j2 = launchingState.mStartUptimeNs;
                ActivityRecord activityRecord = transitionInfo.mLastLaunchedActivity;
                this.mLaunchObserver.onActivityLaunchFinished(
                        j2, activityRecord.mActivityComponent, j, activityRecord.launchMode);
                Trace.traceEnd(64L);
            }
            final boolean booleanValue = bool != null ? bool.booleanValue() : false;
            final TransitionInfoSnapshot transitionInfoSnapshot =
                    new TransitionInfoSnapshot(
                            transitionInfo, transitionInfo.mLastLaunchedActivity, -1);
            final boolean z2 = transitionInfo.mLastLaunchedActivity.mStyleFillsParent;
            final long j3 = launchingState.mStartUptimeNs;
            final int i = transitionInfo.mCurrentTransitionDelayMs;
            Handler handler = this.mLoggerHandler;
            final int i2 = transitionInfo.mProcessState;
            final int i3 = transitionInfo.mProcessOomAdj;
            handler.post(
                    new Runnable() { // from class:
                        // com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda0
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Removed duplicated region for block: B:108:0x044b  */
                        /* JADX WARN: Removed duplicated region for block: B:114:0x0466  */
                        /* JADX WARN: Removed duplicated region for block: B:119:0x0481  */
                        /* JADX WARN: Removed duplicated region for block: B:121:0x048c  */
                        /* JADX WARN: Removed duplicated region for block: B:124:0x04a3  */
                        /* JADX WARN: Removed duplicated region for block: B:127:0x04ac  */
                        /* JADX WARN: Removed duplicated region for block: B:130:0x04b9  */
                        /* JADX WARN: Removed duplicated region for block: B:144:0x0581  */
                        /* JADX WARN: Removed duplicated region for block: B:201:0x073e  */
                        /* JADX WARN: Removed duplicated region for block: B:204:0x0750 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:219:0x082c  */
                        /* JADX WARN: Removed duplicated region for block: B:226:0x0889  */
                        /* JADX WARN: Removed duplicated region for block: B:235:0x08eb  */
                        /* JADX WARN: Removed duplicated region for block: B:238:0x0903 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:264:? A[RETURN, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:265:0x08ef  */
                        /* JADX WARN: Removed duplicated region for block: B:266:0x0891  */
                        /* JADX WARN: Removed duplicated region for block: B:267:0x0854  */
                        /* JADX WARN: Removed duplicated region for block: B:285:0x04af  */
                        /* JADX WARN: Removed duplicated region for block: B:286:0x04a5  */
                        /* JADX WARN: Removed duplicated region for block: B:64:0x0277  */
                        /* JADX WARN: Removed duplicated region for block: B:79:0x02b5  */
                        /* JADX WARN: Removed duplicated region for block: B:81:0x02c6  */
                        /* JADX WARN: Type inference failed for: r7v21, types: [boolean, int] */
                        /* JADX WARN: Type inference failed for: r7v63 */
                        /* JADX WARN: Type inference failed for: r7v66 */
                        /* JADX WARN: Type inference failed for: r7v67 */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void run() {
                            /*
                                Method dump skipped, instructions count: 2425
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda0.run():void");
                        }
                    });
            ActivityMetricsLogger$$ExternalSyntheticLambda3
                    activityMetricsLogger$$ExternalSyntheticLambda3 =
                            transitionInfo.mPendingFullyDrawn;
            if (activityMetricsLogger$$ExternalSyntheticLambda3 != null) {
                activityMetricsLogger$$ExternalSyntheticLambda3.run();
            }
            final ActivityRecord activityRecord2 = transitionInfo.mLastLaunchedActivity;
            activityRecord2.info.launchToken = null;
            if (transitionInfo.mReason == 5) {
                final int i4 =
                        transitionInfo.mSourceEventDelayMs + transitionInfo.mWindowsDrawnDelayMs;
                final long j4 = activityRecord2.topResumedStateLossTime;
                final WindowManagerService windowManagerService =
                        activityTaskSupervisor.mService.mWindowManager;
                final RecentsAnimationController recentsAnimationController =
                        windowManagerService.mRecentsAnimationController;
                handler.postDelayed(
                        new Runnable() { // from class:
                                         // com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                long j5 = j4;
                                ActivityRecord activityRecord3 = activityRecord2;
                                Object obj = recentsAnimationController;
                                WindowManagerService windowManagerService2 = windowManagerService;
                                int i5 = i4;
                                if (j5 == activityRecord3.topResumedStateLossTime
                                        && obj
                                                == windowManagerService2
                                                        .mRecentsAnimationController) {
                                    windowManagerService2.mLatencyTracker.logAction(8, i5);
                                }
                            }
                        },
                        300L);
            }
        }
        if (z || !transitionInfo.mLastLaunchedActivity.inTransition()) {
            activityMetricsLogger = this;
        } else {
            activityMetricsLogger = this;
            activityMetricsLogger.mInTransitionInfoList.add(transitionInfo);
            Slog.i(
                    "ActivityTaskManager",
                    "done cause=" + str + " timestamp=" + j + " info=" + transitionInfo);
        }
        activityMetricsLogger.mTransitionInfoList.remove(transitionInfo);
    }

    public final TransitionInfo getActiveTransitionInfo(ActivityRecord activityRecord) {
        for (int size = this.mTransitionInfoList.size() - 1; size >= 0; size--) {
            TransitionInfo transitionInfo = (TransitionInfo) this.mTransitionInfoList.get(size);
            if (activityRecord == transitionInfo.mLastLaunchedActivity) {
                return transitionInfo;
            }
        }
        return null;
    }

    public final void logAppTransitionCancel(TransitionInfo transitionInfo) {
        int i = transitionInfo.mTransitionType;
        ActivityRecord activityRecord = transitionInfo.mLastLaunchedActivity;
        LogMaker logMaker = new LogMaker(EndpointMonitorConst.TRACE_EVENT_SYS_ENTER_SETGID);
        logMaker.setPackageName(activityRecord.packageName);
        logMaker.setType(i);
        logMaker.addTaggedData(871, activityRecord.info.name);
        this.mMetricsLogger.write(logMaker);
        FrameworkStatsLog.write(
                49,
                activityRecord.info.applicationInfo.uid,
                activityRecord.packageName,
                getAppStartTransitionType(i, transitionInfo.mRelaunched),
                activityRecord.info.name);
    }

    public void logInTaskActivityStart(
            TransitionInfoSnapshot transitionInfoSnapshot, boolean z, int i) {
        FrameworkStatsLog.write(
                FrameworkStatsLog.IN_TASK_ACTIVITY_STARTED,
                transitionInfoSnapshot.applicationInfo.uid,
                getAppStartTransitionType(
                        transitionInfoSnapshot.type, transitionInfoSnapshot.relaunched),
                z,
                i,
                transitionInfoSnapshot.windowsDrawnDelayMs,
                TimeUnit.NANOSECONDS.toMillis(transitionInfoSnapshot.timestampNs));
    }

    public final void notifyActivityLaunched(
            LaunchingState launchingState,
            int i,
            boolean z,
            ActivityRecord activityRecord,
            ActivityOptions activityOptions) {
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z2;
        if (activityRecord == null || activityRecord.task == null) {
            abort(launchingState, "nothing launched");
            return;
        }
        WindowProcessController windowProcessController = activityRecord.app;
        if (windowProcessController == null) {
            windowProcessController =
                    this.mSupervisor.mService.getProcessController(
                            activityRecord.info.applicationInfo.uid, activityRecord.processName);
        }
        int i6 = 1;
        boolean z3 = windowProcessController != null;
        boolean z4 =
                (z3 && windowProcessController.hasStartedActivity(activityRecord)) ? false : true;
        if (z3) {
            int i7 = windowProcessController.mCurProcState;
            i3 = windowProcessController.mCurAdj;
            i2 = i7;
        } else {
            i2 = 20;
            i3 = -10000;
        }
        TransitionInfo transitionInfo = launchingState.mAssociatedTransitionInfo;
        if (activityRecord.mReportedDrawn && activityRecord.mVisible) {
            abort(launchingState, "launched activity already visible");
            return;
        }
        TransitionInfo transitionInfo2 = null;
        if (transitionInfo != null) {
            ActivityRecord activityRecord2 = transitionInfo.mLastLaunchedActivity;
            if (activityRecord2.mDisplayContent == activityRecord.mDisplayContent
                    && activityRecord2.getWindowingMode() == activityRecord.getWindowingMode()) {
                ActivityRecord activityRecord3 = transitionInfo.mLastLaunchedActivity;
                Task task = activityRecord3.task;
                Task task2 = activityRecord.task;
                z2 =
                        (task == null || task2 == null)
                                ? activityRecord3.isUid(activityRecord.launchedFromUid)
                                : task == task2 ? true : task.getBounds().equals(task2.getBounds());
            } else {
                z2 = false;
            }
            if (z2) {
                boolean z5 =
                        !transitionInfo.mLastLaunchedActivity.packageName.equals(
                                activityRecord.packageName);
                if (z5) {
                    stopLaunchTrace(transitionInfo);
                }
                this.mLastTransitionInfo.remove(transitionInfo.mLastLaunchedActivity);
                ActivityRecord activityRecord4 = transitionInfo.mLastLaunchedActivity;
                if (activityRecord4 != activityRecord) {
                    if (activityRecord4 != null) {
                        activityRecord.mLaunchCookie = activityRecord4.mLaunchCookie;
                        activityRecord4.mLaunchCookie = null;
                        activityRecord.mLaunchRootTask = activityRecord4.mLaunchRootTask;
                        activityRecord4.mLaunchRootTask = null;
                    }
                    transitionInfo.mLastLaunchedActivity = activityRecord;
                    transitionInfo.mIsDrawn = activityRecord.mReportedDrawn;
                }
                this.mLastTransitionInfo.put(activityRecord, transitionInfo);
                if (z5) {
                    startLaunchTrace(transitionInfo);
                }
                if (activityRecord.mDisplayContent.mSleeping) {
                    scheduleCheckActivityToBeDrawn(activityRecord, 3000L);
                    return;
                }
                return;
            }
        }
        boolean isVisible = activityRecord.task.isVisible();
        if (i == 0 || i == 2) {
            i4 = 9;
            transitionInfo2 =
                    new TransitionInfo(
                            activityRecord,
                            launchingState,
                            activityOptions,
                            z3 ? (z || !activityRecord.attachedToProcess()) ? 8 : 9 : 7,
                            z3,
                            z4,
                            i2,
                            i3,
                            isVisible);
        } else {
            i4 = 9;
        }
        if (transitionInfo2 == null) {
            abort(launchingState, "unrecognized launch");
            return;
        }
        Task adjacentTask = transitionInfo2.mLastLaunchedActivity.task.getAdjacentTask();
        if (adjacentTask != null) {
            for (int size = this.mTransitionInfoList.size() - 1; size >= 0; size--) {
                TransitionInfo transitionInfo3 =
                        (TransitionInfo) this.mTransitionInfoList.get(size);
                if (transitionInfo3 != transitionInfo2
                        && transitionInfo3.mLastLaunchedActivity.task.isDescendantOf(
                                adjacentTask)) {
                    transitionInfo2.mMultiWindowLaunchType = 1;
                    transitionInfo3.mMultiWindowLaunchType = 1;
                }
            }
        }
        this.mTransitionInfoList.add(transitionInfo2);
        this.mLastTransitionInfo.put(activityRecord, transitionInfo2);
        startLaunchTrace(transitionInfo2);
        if (transitionInfo2.mProcessSwitch) {
            ActivityManagerPerformance activityManagerPerformance =
                    this.mSupervisor.mService.mAMBooster;
            if (activityManagerPerformance != null) {
                int i8 = transitionInfo2.mTransitionType;
                if (i8 == 7 || i8 == 8) {
                    activityManagerPerformance.onAppLaunch(false, activityRecord);
                } else if (i8 == i4) {
                    activityManagerPerformance.onAppLaunch(true, activityRecord);
                }
            }
            Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyActivityLaunched");
            int i9 = transitionInfo2.mTransitionType;
            if (i9 != 7) {
                if (i9 != 8) {
                    i6 = i9 != i4 ? -1 : 3;
                } else {
                    i5 = 2;
                    long j = transitionInfo2.mLaunchingState.mStartUptimeNs;
                    ActivityRecord activityRecord5 = transitionInfo2.mLastLaunchedActivity;
                    this.mLaunchObserver.onActivityLaunched(
                            i5, activityRecord5.mUserId, j, activityRecord5.mActivityComponent);
                    Trace.traceEnd(64L);
                }
            }
            i5 = i6;
            long j2 = transitionInfo2.mLaunchingState.mStartUptimeNs;
            ActivityRecord activityRecord52 = transitionInfo2.mLastLaunchedActivity;
            this.mLaunchObserver.onActivityLaunched(
                    i5, activityRecord52.mUserId, j2, activityRecord52.mActivityComponent);
            Trace.traceEnd(64L);
        } else {
            long j3 = transitionInfo2.mLaunchingState.mStartUptimeNs;
            Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyIntentFailed");
            this.mLaunchObserver.onIntentFailed(j3);
            Trace.traceEnd(64L);
        }
        if (activityRecord.mDisplayContent.mSleeping) {
            scheduleCheckActivityToBeDrawn(activityRecord, 3000L);
        }
        for (int size2 = this.mTransitionInfoList.size() - 2; size2 >= 0; size2--) {
            TransitionInfo transitionInfo4 = (TransitionInfo) this.mTransitionInfoList.get(size2);
            if (transitionInfo4.mIsDrawn
                    || !transitionInfo4.mLastLaunchedActivity.isVisibleRequested()) {
                scheduleCheckActivityToBeDrawn(transitionInfo4.mLastLaunchedActivity, 0L);
            }
        }
    }

    public final LaunchingState notifyActivityLaunching(
            Intent intent, ActivityRecord activityRecord, int i) {
        TransitionInfo transitionInfo;
        TransitionInfo transitionInfo2 = null;
        if (i != -1) {
            int size = this.mTransitionInfoList.size() - 1;
            TransitionInfo transitionInfo3 = null;
            while (true) {
                if (size < 0) {
                    break;
                }
                TransitionInfo transitionInfo4 =
                        (TransitionInfo) this.mTransitionInfoList.get(size);
                if (activityRecord != null
                        && activityRecord == transitionInfo4.mLastLaunchedActivity) {
                    transitionInfo3 = transitionInfo4;
                    break;
                }
                if (transitionInfo3 == null
                        && i == transitionInfo4.mLastLaunchedActivity.getUid()) {
                    transitionInfo3 = transitionInfo4;
                }
                size--;
            }
            if (transitionInfo3 == null) {
                for (int size2 = this.mInTransitionInfoList.size() - 1; size2 >= 0; size2--) {
                    transitionInfo = (TransitionInfo) this.mInTransitionInfoList.get(size2);
                    if (activityRecord != null
                            && activityRecord == transitionInfo.mLastLaunchedActivity) {
                        break;
                    }
                    if (transitionInfo2 == null
                            && i == transitionInfo.mLastLaunchedActivity.getUid()) {
                        transitionInfo2 = transitionInfo;
                    }
                }
            }
            transitionInfo = transitionInfo2;
            transitionInfo2 = transitionInfo3;
        } else {
            transitionInfo = null;
        }
        if (transitionInfo2 != null) {
            return transitionInfo2.mLaunchingState;
        }
        LaunchingState launchingState = new LaunchingState();
        Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyIntentStarted");
        this.mLaunchObserver.onIntentStarted(intent, launchingState.mStartUptimeNs);
        Trace.traceEnd(64L);
        if (transitionInfo != null) {
            Slog.d(
                    "ActivityTaskManager",
                    "notifyActivityLaunching: update associated activity from transitioning="
                            + transitionInfo.mLastLaunchedActivity);
            launchingState.mAssociatedActivity = transitionInfo.mLastLaunchedActivity;
        }
        return launchingState;
    }

    public final void notifyBindApplication(ApplicationInfo applicationInfo) {
        for (int size = this.mTransitionInfoList.size() - 1; size >= 0; size--) {
            TransitionInfo transitionInfo = (TransitionInfo) this.mTransitionInfoList.get(size);
            if (transitionInfo.mLastLaunchedActivity.info.applicationInfo == applicationInfo) {
                long uptimeNanos = SystemClock.uptimeNanos();
                TimeUnit timeUnit = TimeUnit.NANOSECONDS;
                LaunchingState launchingState = transitionInfo.mLaunchingState;
                transitionInfo.mBindApplicationDelayMs =
                        (int) timeUnit.toMillis(uptimeNanos - launchingState.mStartUptimeNs);
                if (transitionInfo.mProcessRunning) {
                    transitionInfo.mProcessRunning = false;
                    transitionInfo.mTransitionType = 7;
                    String str =
                            "Process "
                                    + transitionInfo.mLastLaunchedActivity.info.processName
                                    + " restarted";
                    Slog.i("ActivityTaskManager", str);
                    if (launchingState.mTraceName != null) {
                        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "#");
                        m.append(LaunchingState.sTraceSeqId);
                        Trace.instant(64L, m.toString());
                    }
                }
            }
        }
    }

    public final void notifyFullyDrawn(boolean z, ActivityRecord activityRecord) {
        TransitionInfo transitionInfo =
                (TransitionInfo) this.mLastTransitionInfo.get(activityRecord);
        if (transitionInfo == null) {
            return;
        }
        if (!transitionInfo.mIsDrawn && transitionInfo.mPendingFullyDrawn == null) {
            transitionInfo.mPendingFullyDrawn =
                    new ActivityMetricsLogger$$ExternalSyntheticLambda3(
                            this, activityRecord, z, transitionInfo, 1);
            return;
        }
        long uptimeNanos = SystemClock.uptimeNanos();
        ActivityMetricsLogger$$ExternalSyntheticLambda3
                activityMetricsLogger$$ExternalSyntheticLambda3 = transitionInfo.mPendingFullyDrawn;
        LaunchingState launchingState = transitionInfo.mLaunchingState;
        final TransitionInfoSnapshot transitionInfoSnapshot =
                new TransitionInfoSnapshot(
                        transitionInfo,
                        activityRecord,
                        (int)
                                (activityMetricsLogger$$ExternalSyntheticLambda3 != null
                                        ? transitionInfo.mWindowsDrawnDelayMs
                                        : TimeUnit.NANOSECONDS.toMillis(
                                                uptimeNanos - launchingState.mStartUptimeNs)));
        int i = transitionInfoSnapshot.type;
        boolean z2 = i == 8 || i == 7;
        Handler handler = this.mLoggerHandler;
        if (z2) {
            handler.post(
                    new Runnable() { // from class:
                                     // com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActivityMetricsLogger activityMetricsLogger =
                                    ActivityMetricsLogger.this;
                            ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot2 =
                                    transitionInfoSnapshot;
                            StringBuilder sb = activityMetricsLogger.mStringBuilder;
                            sb.setLength(0);
                            sb.append("Fully drawn ");
                            sb.append(transitionInfoSnapshot2.launchedActivityShortComponentName);
                            sb.append(": ");
                            TimeUtils.formatDuration(
                                    transitionInfoSnapshot2.windowsFullyDrawnDelayMs, sb);
                            Log.i("ActivityTaskManager", sb.toString());
                        }
                    });
        }
        this.mLastTransitionInfo.remove(activityRecord);
        if (transitionInfo.mProcessSwitch) {
            Trace.traceBegin(
                    64L,
                    "ActivityManager:ReportingFullyDrawn "
                            + transitionInfo.mLastLaunchedActivity.packageName);
            handler.post(
                    new ActivityMetricsLogger$$ExternalSyntheticLambda3(
                            this, transitionInfoSnapshot, z, transitionInfo, 0));
            Trace.traceEnd(64L);
            Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyReportFullyDrawn");
            this.mLaunchObserver.onReportFullyDrawn(launchingState.mStartUptimeNs, uptimeNanos);
            Trace.traceEnd(64L);
        }
    }

    public final void notifyTransitionStarting(ArrayMap arrayMap) {
        long uptimeNanos = SystemClock.uptimeNanos();
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) arrayMap.keyAt(size);
            ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
            if (asActivityRecord == null) {
                asActivityRecord = windowContainer.getTopActivity(false, true);
            }
            TransitionInfo activeTransitionInfo = getActiveTransitionInfo(asActivityRecord);
            if (activeTransitionInfo != null && !activeTransitionInfo.mLoggedTransitionStarting) {
                activeTransitionInfo.mCurrentTransitionDelayMs =
                        (int)
                                TimeUnit.NANOSECONDS.toMillis(
                                        uptimeNanos
                                                - activeTransitionInfo
                                                        .mLaunchingState
                                                        .mStartUptimeNs);
                activeTransitionInfo.mReason = ((Integer) arrayMap.valueAt(size)).intValue();
                activeTransitionInfo.mLoggedTransitionStarting = true;
                if (activeTransitionInfo.mIsDrawn) {
                    done(
                            false,
                            activeTransitionInfo,
                            "notifyTransitionStarting drawn",
                            uptimeNanos);
                }
            }
        }
    }

    public final void scheduleCheckActivityToBeDrawn(ActivityRecord activityRecord, long j) {
        activityRecord.mAtmService.mH.sendMessageDelayed(
                PooledLambda.obtainMessage(
                        new ActivityMetricsLogger$$ExternalSyntheticLambda4(1),
                        this,
                        activityRecord.task,
                        activityRecord),
                j);
    }
}
