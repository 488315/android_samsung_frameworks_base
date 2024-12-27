package com.android.server.wm;

import android.graphics.Rect;

import com.android.internal.util.FrameworkStatsLog;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCompatReachabilityPolicy {
    public final ActivityRecord mActivityRecord;
    public final AppCompatConfiguration mAppCompatConfiguration;
    Supplier mLetterboxInnerBoundsSupplier;
    public final AppCompatReachabilityPolicy$$ExternalSyntheticLambda0 mOnSingleTap =
            new Runnable() { // from class:
                             // com.android.server.wm.AppCompatReachabilityPolicy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppCompatReachabilityPolicy appCompatReachabilityPolicy =
                            AppCompatReachabilityPolicy.this;
                    WindowManagerGlobalLock windowManagerGlobalLock =
                            appCompatReachabilityPolicy.mActivityRecord.mAtmService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            ActivityRecord activityRecord =
                                    appCompatReachabilityPolicy.mActivityRecord;
                            Task task = activityRecord.task;
                            if (task == null) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            AppCompatReachabilityOverrides appCompatReachabilityOverrides =
                                    activityRecord
                                            .mAppCompatController
                                            .mAppCompatOverrides
                                            .mAppCompatReachabilityOverrides;
                            appCompatReachabilityOverrides.mReachabilityState.mIsSingleTapEvent =
                                    true;
                            try {
                                task.dispatchTaskInfoChangedIfNeeded(true);
                                WindowManagerService.resetPriorityAfterLockedSection();
                            } finally {
                                appCompatReachabilityOverrides
                                                .mReachabilityState
                                                .mIsSingleTapEvent =
                                        false;
                            }
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.wm.AppCompatReachabilityPolicy$$ExternalSyntheticLambda0] */
    public AppCompatReachabilityPolicy(
            ActivityRecord activityRecord, AppCompatConfiguration appCompatConfiguration) {
        this.mActivityRecord = activityRecord;
        this.mAppCompatConfiguration = appCompatConfiguration;
    }

    public final void handleDoubleTap(int i, int i2) {
        ActivityRecord activityRecord = this.mActivityRecord;
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
        AppCompatConfiguration appCompatConfiguration = this.mAppCompatConfiguration;
        if (isHorizontalReachabilityEnabled && !activityRecord.inTransitionSelfOrParent()) {
            Supplier supplier = this.mLetterboxInnerBoundsSupplier;
            Rect rect = supplier != null ? (Rect) supplier.get() : new Rect();
            if (rect.left > i || rect.right < i) {
                boolean z =
                        activityRecord.mAppCompatController.mAppCompatDeviceStateQuery
                                        .isDisplayFullScreenAndSeparatingHinge()
                                && appCompatConfiguration.mIsAutomaticReachabilityInBookModeEnabled;
                int letterboxPositionForHorizontalReachability =
                        appCompatConfiguration.mAppCompatConfigurationPersister
                                .getLetterboxPositionForHorizontalReachability(z);
                int i3 = rect.left;
                AppCompatReachabilityOverrides.ReachabilityState reachabilityState =
                        appCompatReachabilityOverrides.mReachabilityState;
                AppCompatConfigurationPersister appCompatConfigurationPersister =
                        appCompatConfiguration.mAppCompatConfigurationPersister;
                if (i3 > i) {
                    appCompatConfigurationPersister.setLetterboxPositionForHorizontalReachability(
                            Integer.valueOf(
                                            Math.max(
                                                    Integer.valueOf(
                                                                            appCompatConfigurationPersister
                                                                                    .getLetterboxPositionForHorizontalReachability(
                                                                                            z))
                                                                    .intValue()
                                                            - (z ? 2 : 1),
                                                    0))
                                    .intValue(),
                            z);
                    logLetterboxPositionChange(
                            letterboxPositionForHorizontalReachability == 1 ? 1 : 4);
                    reachabilityState.mIsDoubleTapEvent = true;
                } else if (rect.right < i) {
                    appCompatConfigurationPersister.setLetterboxPositionForHorizontalReachability(
                            Integer.valueOf(
                                            Math.min(
                                                    Integer.valueOf(
                                                                            appCompatConfigurationPersister
                                                                                    .getLetterboxPositionForHorizontalReachability(
                                                                                            z))
                                                                    .intValue()
                                                            + (z ? 2 : 1),
                                                    2))
                                    .intValue(),
                            z);
                    logLetterboxPositionChange(
                            letterboxPositionForHorizontalReachability == 1 ? 3 : 2);
                    reachabilityState.mIsDoubleTapEvent = true;
                }
                activityRecord.recomputeConfiguration();
            }
        }
        AppCompatReachabilityOverrides appCompatReachabilityOverrides2 =
                activityRecord
                        .mAppCompatController
                        .mAppCompatOverrides
                        .mAppCompatReachabilityOverrides;
        if (!appCompatReachabilityOverrides2.isVerticalReachabilityEnabled(
                        appCompatReachabilityOverrides2
                                .mActivityRecord
                                .getParent()
                                .getConfiguration())
                || activityRecord.inTransitionSelfOrParent()) {
            return;
        }
        Supplier supplier2 = this.mLetterboxInnerBoundsSupplier;
        Rect rect2 = supplier2 != null ? (Rect) supplier2.get() : new Rect();
        if (rect2.top > i2 || rect2.bottom < i2) {
            boolean isDisplayFullScreenAndSeparatingHinge =
                    activityRecord.mAppCompatController.mAppCompatDeviceStateQuery
                            .isDisplayFullScreenAndSeparatingHinge();
            int letterboxPositionForVerticalReachability =
                    appCompatConfiguration.mAppCompatConfigurationPersister
                            .getLetterboxPositionForVerticalReachability(
                                    isDisplayFullScreenAndSeparatingHinge);
            int i4 = rect2.top;
            AppCompatReachabilityOverrides.ReachabilityState reachabilityState2 =
                    appCompatReachabilityOverrides2.mReachabilityState;
            AppCompatConfigurationPersister appCompatConfigurationPersister2 =
                    appCompatConfiguration.mAppCompatConfigurationPersister;
            if (i4 > i2) {
                appCompatConfigurationPersister2.setLetterboxPositionForVerticalReachability(
                        Integer.valueOf(
                                        Math.max(
                                                Integer.valueOf(
                                                                        appCompatConfigurationPersister2
                                                                                .getLetterboxPositionForVerticalReachability(
                                                                                        isDisplayFullScreenAndSeparatingHinge))
                                                                .intValue()
                                                        - (isDisplayFullScreenAndSeparatingHinge
                                                                ? 2
                                                                : 1),
                                                0))
                                .intValue(),
                        isDisplayFullScreenAndSeparatingHinge);
                logLetterboxPositionChange(letterboxPositionForVerticalReachability == 1 ? 5 : 8);
                reachabilityState2.mIsDoubleTapEvent = true;
            } else if (rect2.bottom < i2) {
                appCompatConfigurationPersister2.setLetterboxPositionForVerticalReachability(
                        Integer.valueOf(
                                        Math.min(
                                                Integer.valueOf(
                                                                        appCompatConfigurationPersister2
                                                                                .getLetterboxPositionForVerticalReachability(
                                                                                        isDisplayFullScreenAndSeparatingHinge))
                                                                .intValue()
                                                        + (isDisplayFullScreenAndSeparatingHinge
                                                                ? 2
                                                                : 1),
                                                2))
                                .intValue(),
                        isDisplayFullScreenAndSeparatingHinge);
                logLetterboxPositionChange(letterboxPositionForVerticalReachability == 1 ? 7 : 6);
                reachabilityState2.mIsDoubleTapEvent = true;
            }
            activityRecord.recomputeConfiguration();
        }
    }

    public final void logLetterboxPositionChange(int i) {
        ActivityRecord activityRecord = this.mActivityRecord;
        ActivityMetricsLogger activityMetricsLogger =
                activityRecord.mTaskSupervisor.mActivityMetricsLogger;
        activityMetricsLogger.getClass();
        int i2 = activityRecord.info.applicationInfo.uid;
        FrameworkStatsLog.write(FrameworkStatsLog.LETTERBOX_POSITION_CHANGED, i2, i);
        if (activityMetricsLogger.mPackageUidToCompatStateInfo.contains(i2)) {
            ActivityMetricsLogger.PackageCompatStateInfo packageCompatStateInfo =
                    (ActivityMetricsLogger.PackageCompatStateInfo)
                            activityMetricsLogger.mPackageUidToCompatStateInfo.get(i2);
            if (activityRecord != packageCompatStateInfo.mLastLoggedActivity) {
                return;
            }
            ActivityMetricsLogger.logAppCompatStateInternal(
                    activityRecord,
                    activityRecord.getAppCompatState(false),
                    packageCompatStateInfo);
        }
    }
}
