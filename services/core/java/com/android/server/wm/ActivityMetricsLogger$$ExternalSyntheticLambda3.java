package com.android.server.wm;

import android.content.pm.IncrementalStatesInfo;
import android.content.pm.dex.ArtManagerInternal;
import android.content.pm.dex.PackageOptimizationInfo;
import android.metrics.LogMaker;
import android.os.incremental.IncrementalManager;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ActivityMetricsLogger$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityMetricsLogger f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ ActivityMetricsLogger.TransitionInfo f$3;

    public /* synthetic */ ActivityMetricsLogger$$ExternalSyntheticLambda3(
            ActivityMetricsLogger activityMetricsLogger,
            Object obj,
            boolean z,
            ActivityMetricsLogger.TransitionInfo transitionInfo,
            int i) {
        this.$r8$classId = i;
        this.f$0 = activityMetricsLogger;
        this.f$1 = obj;
        this.f$2 = z;
        this.f$3 = transitionInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        boolean z2;
        String str;
        switch (this.$r8$classId) {
            case 0:
                ActivityMetricsLogger activityMetricsLogger = this.f$0;
                ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot =
                        (ActivityMetricsLogger.TransitionInfoSnapshot) this.f$1;
                boolean z3 = this.f$2;
                ActivityMetricsLogger.TransitionInfo transitionInfo = this.f$3;
                activityMetricsLogger.getClass();
                boolean z4 = transitionInfo.mProcessRunning;
                LogMaker logMaker = new LogMaker(1090);
                logMaker.setPackageName(transitionInfoSnapshot.packageName);
                String str2 = transitionInfoSnapshot.launchedActivityName;
                logMaker.addTaggedData(871, str2);
                long j = transitionInfoSnapshot.windowsFullyDrawnDelayMs;
                logMaker.addTaggedData(1091, Long.valueOf(j));
                logMaker.setType(z3 ? 13 : 12);
                logMaker.addTaggedData(
                        FrameworkStatsLog
                                .APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ACTIVE_DEVICE_ADMIN,
                        Integer.valueOf(z4 ? 1 : 0));
                activityMetricsLogger.mMetricsLogger.write(logMaker);
                if (activityMetricsLogger.mArtManagerInternal == null) {
                    activityMetricsLogger.mArtManagerInternal =
                            (ArtManagerInternal) LocalServices.getService(ArtManagerInternal.class);
                }
                ArtManagerInternal artManagerInternal = activityMetricsLogger.mArtManagerInternal;
                PackageOptimizationInfo createWithNoInfo =
                        (artManagerInternal == null
                                        || (str =
                                                        transitionInfoSnapshot
                                                                .launchedActivityAppRecordRequiredAbi)
                                                == null)
                                ? PackageOptimizationInfo.createWithNoInfo()
                                : artManagerInternal.getPackageOptimizationInfo(
                                        transitionInfoSnapshot.applicationInfo, str, str2);
                String codePath = transitionInfoSnapshot.applicationInfo.getCodePath();
                boolean z5 = false;
                if (codePath == null || !IncrementalManager.isIncrementalPath(codePath)) {
                    z = false;
                    z2 = false;
                } else {
                    IncrementalStatesInfo incrementalStatesInfo =
                            activityMetricsLogger
                                    .mSupervisor
                                    .mService
                                    .getPackageManagerInternalLocked()
                                    .getIncrementalStatesInfo(
                                            0,
                                            transitionInfoSnapshot.userId,
                                            transitionInfoSnapshot.packageName);
                    if (incrementalStatesInfo != null && incrementalStatesInfo.isLoading()) {
                        z5 = true;
                    }
                    z2 = z5;
                    z = true;
                }
                FrameworkStatsLog.write(
                        50,
                        transitionInfoSnapshot.applicationInfo.uid,
                        transitionInfoSnapshot.packageName,
                        z3 ? 1 : 2,
                        transitionInfoSnapshot.launchedActivityName,
                        z4,
                        j,
                        createWithNoInfo.getCompilationReason(),
                        createWithNoInfo.getCompilationFilter(),
                        transitionInfoSnapshot.sourceType,
                        transitionInfoSnapshot.sourceEventDelayMs,
                        z,
                        z2,
                        str2.hashCode(),
                        TimeUnit.NANOSECONDS.toMillis(transitionInfoSnapshot.timestampNs));
                break;
            default:
                ActivityMetricsLogger activityMetricsLogger2 = this.f$0;
                ActivityRecord activityRecord = (ActivityRecord) this.f$1;
                boolean z6 = this.f$2;
                ActivityMetricsLogger.TransitionInfo transitionInfo2 = this.f$3;
                activityMetricsLogger2.notifyFullyDrawn(z6, activityRecord);
                transitionInfo2.mPendingFullyDrawn = null;
                break;
        }
    }
}
