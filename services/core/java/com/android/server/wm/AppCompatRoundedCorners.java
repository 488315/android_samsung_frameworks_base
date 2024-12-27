package com.android.server.wm;

import android.graphics.Rect;
import android.view.InsetsState;
import android.view.RoundedCorner;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.rune.CoreRune;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppCompatRoundedCorners {
    public final ActivityRecord mActivityRecord;
    public final Predicate mIsLetterboxedNotForDisplayCutout;

    public AppCompatRoundedCorners(
            ActivityRecord activityRecord,
            AppCompatLetterboxPolicy$$ExternalSyntheticLambda1
                    appCompatLetterboxPolicy$$ExternalSyntheticLambda1) {
        this.mActivityRecord = activityRecord;
        this.mIsLetterboxedNotForDisplayCutout = appCompatLetterboxPolicy$$ExternalSyntheticLambda1;
    }

    public Rect getCropBoundsIfNeeded(WindowState windowState) {
        if (requiresRoundedCorners(windowState)) {
            ActivityRecord activityRecord = this.mActivityRecord;
            if (!activityRecord.isInLetterboxAnimation()) {
                Rect rect = new Rect(activityRecord.getBounds());
                if (activityRecord.mAppCompatController.mTransparentPolicy.mTransparentPolicyState
                                .isRunning()
                        && (rect.width() != windowState.mRequestedWidth
                                || rect.height() != windowState.mRequestedHeight)) {
                    return null;
                }
                AppCompatUtils.adjustBoundsForTaskbar(windowState, rect);
                float f = windowState.mInvGlobalScale;
                if (f != 1.0f && f > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    rect.scale(f);
                }
                rect.offsetTo(0, 0);
                return rect;
            }
        }
        return null;
    }

    public final int getRoundedCornersRadius(WindowState windowState) {
        int min;
        if (!requiresRoundedCorners(windowState)) {
            return 0;
        }
        AppCompatLetterboxOverrides appCompatLetterboxOverrides =
                this.mActivityRecord
                        .mAppCompatController
                        .mAppCompatOverrides
                        .mAppCompatLetterboxOverrides;
        appCompatLetterboxOverrides.getClass();
        boolean z = CoreRune.MT_APP_COMPAT_CONFIGURATION;
        AppCompatConfiguration appCompatConfiguration =
                appCompatLetterboxOverrides.mAppCompatConfiguration;
        ActivityRecord activityRecord = appCompatLetterboxOverrides.mActivityRecord;
        if ((z
                        ? MultiTaskingAppCompatConfiguration.getConfig(activityRecord)
                                .getLetterboxActivityCornersRadius()
                        : appCompatConfiguration.mLetterboxActivityCornersRadius)
                >= 0) {
            min =
                    z
                            ? MultiTaskingAppCompatConfiguration.getConfig(activityRecord)
                                    .getLetterboxActivityCornersRadius()
                            : appCompatConfiguration.mLetterboxActivityCornersRadius;
        } else {
            InsetsState insetsState = windowState.getInsetsState(false);
            RoundedCorner roundedCorner = insetsState.getRoundedCorners().getRoundedCorner(3);
            int radius = roundedCorner == null ? 0 : roundedCorner.getRadius();
            RoundedCorner roundedCorner2 = insetsState.getRoundedCorners().getRoundedCorner(2);
            min = Math.min(radius, roundedCorner2 != null ? roundedCorner2.getRadius() : 0);
        }
        float f = windowState.mInvGlobalScale;
        return (f == 1.0f || f <= FullScreenMagnificationGestureHandler.MAX_SCALE)
                ? min
                : (int) (f * min);
    }

    public final boolean requiresRoundedCorners(WindowState windowState) {
        AppCompatLetterboxOverrides appCompatLetterboxOverrides =
                this.mActivityRecord
                        .mAppCompatController
                        .mAppCompatOverrides
                        .mAppCompatLetterboxOverrides;
        if (this.mIsLetterboxedNotForDisplayCutout.test(windowState)) {
            appCompatLetterboxOverrides.getClass();
            if (!CoreRune.MT_APP_COMPAT_CONFIGURATION
                    ? appCompatLetterboxOverrides
                                    .mAppCompatConfiguration
                                    .mLetterboxActivityCornersRadius
                            != 0
                    : MultiTaskingAppCompatConfiguration.getConfig(
                                            appCompatLetterboxOverrides.mActivityRecord)
                                    .getLetterboxActivityCornersRadius()
                            != 0) {
                return true;
            }
        }
        return false;
    }
}
