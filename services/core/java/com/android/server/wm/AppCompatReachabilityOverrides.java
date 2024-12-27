package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Rect;

import com.android.window.flags.Flags;

import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppCompatReachabilityOverrides {
    public final ActivityRecord mActivityRecord;
    public final AppCompatConfiguration mAppCompatConfiguration;
    public final AppCompatDeviceStateQuery mAppCompatDeviceStateQuery;
    public final ReachabilityState mReachabilityState = new ReachabilityState();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReachabilityState {
        public boolean mIsDoubleTapEvent;
        public boolean mIsSingleTapEvent;
    }

    public AppCompatReachabilityOverrides(
            ActivityRecord activityRecord,
            AppCompatConfiguration appCompatConfiguration,
            AppCompatDeviceStateQuery appCompatDeviceStateQuery) {
        this.mActivityRecord = activityRecord;
        this.mAppCompatConfiguration = appCompatConfiguration;
        this.mAppCompatDeviceStateQuery = appCompatDeviceStateQuery;
    }

    public final float getHorizontalPositionMultiplier(Configuration configuration) {
        boolean z = false;
        boolean isDisplayFullScreenAndInPosture =
                this.mAppCompatDeviceStateQuery.isDisplayFullScreenAndInPosture(false);
        AppCompatConfiguration appCompatConfiguration = this.mAppCompatConfiguration;
        if (isDisplayFullScreenAndInPosture
                && appCompatConfiguration.mIsAutomaticReachabilityInBookModeEnabled) {
            z = true;
        }
        return isHorizontalReachabilityEnabled(configuration)
                ? appCompatConfiguration.getHorizontalMultiplierForReachability(z)
                : z
                        ? appCompatConfiguration.mLetterboxBookModePositionMultiplier
                        : appCompatConfiguration.mLetterboxHorizontalPositionMultiplier;
    }

    public final boolean isHorizontalReachabilityEnabled(Configuration configuration) {
        if (!(!Flags.disableThinLetterboxingPolicy() ? true : !isHorizontalThinLetterboxed())) {
            return false;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        Rect copyOrNull =
                Rect.copyOrNull(activityRecord.mResolveConfigHint.mParentAppBoundsOverride);
        if (copyOrNull == null) {
            copyOrNull = configuration.windowConfiguration.getAppBounds();
        }
        TransparentPolicy.TransparentPolicyState transparentPolicyState =
                activityRecord.mAppCompatController.mTransparentPolicy.mTransparentPolicyState;
        Rect rect =
                (Rect)
                        (transparentPolicyState.isRunning()
                                        ? Optional.ofNullable(
                                                transparentPolicyState.mFirstOpaqueActivity)
                                        : Optional.empty())
                                .map(new AppCompatReachabilityOverrides$$ExternalSyntheticLambda0())
                                .orElse(activityRecord.getScreenResolvedBounds());
        return this.mAppCompatConfiguration.mIsHorizontalReachabilityEnabled
                && configuration.windowConfiguration.getWindowingMode() == 1
                && copyOrNull.height() <= rect.height()
                && copyOrNull.width() > rect.width();
    }

    public final boolean isHorizontalThinLetterboxed() {
        ActivityRecord activityRecord;
        Task task;
        int asInt = this.mAppCompatConfiguration.mThinLetterboxWidthPxSupplier.getAsInt();
        return asInt >= 0
                && (task = (activityRecord = this.mActivityRecord).task) != null
                && Math.abs(task.getBounds().width() - activityRecord.getBounds().width()) / 2
                        <= asInt;
    }

    public final boolean isVerticalReachabilityEnabled(Configuration configuration) {
        if (!(!Flags.disableThinLetterboxingPolicy() ? true : !isVerticalThinLetterboxed())) {
            return false;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        Rect copyOrNull =
                Rect.copyOrNull(activityRecord.mResolveConfigHint.mParentAppBoundsOverride);
        if (copyOrNull == null) {
            copyOrNull = configuration.windowConfiguration.getAppBounds();
        }
        TransparentPolicy.TransparentPolicyState transparentPolicyState =
                activityRecord.mAppCompatController.mTransparentPolicy.mTransparentPolicyState;
        Rect rect =
                (Rect)
                        (transparentPolicyState.isRunning()
                                        ? Optional.ofNullable(
                                                transparentPolicyState.mFirstOpaqueActivity)
                                        : Optional.empty())
                                .map(new AppCompatReachabilityOverrides$$ExternalSyntheticLambda0())
                                .orElse(activityRecord.getScreenResolvedBounds());
        return this.mAppCompatConfiguration.mIsVerticalReachabilityEnabled
                && configuration.windowConfiguration.getWindowingMode() == 1
                && copyOrNull.width() <= rect.width()
                && copyOrNull.height() > rect.height();
    }

    public final boolean isVerticalThinLetterboxed() {
        ActivityRecord activityRecord;
        Task task;
        int asInt = this.mAppCompatConfiguration.mThinLetterboxHeightPxSupplier.getAsInt();
        return asInt >= 0
                && (task = (activityRecord = this.mActivityRecord).task) != null
                && Math.abs(task.getBounds().height() - activityRecord.getBounds().height()) / 2
                        <= asInt;
    }
}
