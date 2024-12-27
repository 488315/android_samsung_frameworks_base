package com.android.server.wm;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;

import com.android.window.flags.Flags;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppCompatSizeCompatModePolicy {
    public final ActivityRecord mActivityRecord;
    public AppCompatDisplayInsets mAppCompatDisplayInsets;
    public AppCompatDisplayInsets mPreCreatedAppCompatDisplayInsets;
    public Rect mReturnSizeCompatBounds;
    public Rect mSizeCompatBounds;
    public Rect mTmpRect;
    public int mViewportStableTop;
    public boolean mInSizeCompatModeForBounds = false;
    public float mSizeCompatScale = 1.0f;

    public AppCompatSizeCompatModePolicy(ActivityRecord activityRecord) {
        this.mActivityRecord = activityRecord;
    }

    public final void clearSizeCompatMode(boolean z, boolean z2) {
        clearSizeCompatModeAttributes();
        ActivityRecord activityRecord = this.mActivityRecord;
        int activityType = activityRecord.getActivityType();
        Configuration requestedOverrideConfiguration =
                activityRecord.getRequestedOverrideConfiguration();
        if (z2) {
            requestedOverrideConfiguration.unset();
            requestedOverrideConfiguration.windowConfiguration.setActivityType(activityType);
        }
        if (z) {
            return;
        }
        activityRecord.onRequestedOverrideConfigurationChanged(requestedOverrideConfiguration);
    }

    public final void clearSizeCompatModeAttributes() {
        this.mInSizeCompatModeForBounds = false;
        float f = this.mSizeCompatScale;
        this.mSizeCompatScale = 1.0f;
        ActivityRecord activityRecord = this.mActivityRecord;
        if (1.0f != f) {
            activityRecord.forAllWindows(
                    (Consumer) new AppCompatSizeCompatModePolicy$$ExternalSyntheticLambda0(),
                    false);
        }
        this.mSizeCompatBounds = null;
        this.mAppCompatDisplayInsets = null;
        activityRecord
                        .mAppCompatController
                        .mTransparentPolicy
                        .mTransparentPolicyState
                        .mInheritedAppCompatDisplayInsets =
                null;
    }

    public final boolean hasAppCompatDisplayInsetsWithoutInheritance() {
        return this.mAppCompatDisplayInsets != null;
    }

    public final boolean hasSizeCompatBounds() {
        return this.mSizeCompatBounds != null;
    }

    public final void updateAppCompatDisplayInsets() {
        this.mPreCreatedAppCompatDisplayInsets = null;
        ActivityRecord activityRecord = this.mActivityRecord;
        TransparentPolicy transparentPolicy =
                activityRecord.mAppCompatController.mTransparentPolicy;
        AppCompatDisplayInsets appCompatDisplayInsets =
                transparentPolicy.mTransparentPolicyState.isRunning()
                        ? transparentPolicy.mTransparentPolicyState.mInheritedAppCompatDisplayInsets
                        : this.mAppCompatDisplayInsets;
        if (appCompatDisplayInsets == null) {
            appCompatDisplayInsets = this.mPreCreatedAppCompatDisplayInsets;
        }
        if (appCompatDisplayInsets == null && activityRecord.shouldCreateAppCompatDisplayInsets()) {
            Configuration requestedOverrideConfiguration =
                    activityRecord.getRequestedOverrideConfiguration();
            Configuration configuration = activityRecord.getConfiguration();
            requestedOverrideConfiguration.colorMode = configuration.colorMode;
            requestedOverrideConfiguration.densityDpi = configuration.densityDpi;
            requestedOverrideConfiguration.smallestScreenWidthDp =
                    configuration.smallestScreenWidthDp;
            if (ActivityInfo.isFixedOrientation(activityRecord.getOverrideOrientation())) {
                requestedOverrideConfiguration.windowConfiguration.setRotation(
                        configuration.windowConfiguration.getRotation());
            }
            AppCompatAspectRatioPolicy.AppCompatAspectRatioState appCompatAspectRatioState =
                    activityRecord
                            .mAppCompatController
                            .mAppCompatAspectRatioPolicy
                            .mAppCompatAspectRatioState;
            Rect rect = appCompatAspectRatioState.mLetterboxBoundsForFixedOrientationAndAspectRatio;
            if (rect == null) {
                rect = appCompatAspectRatioState.mLetterboxBoundsForAspectRatio;
            }
            this.mAppCompatDisplayInsets =
                    new AppCompatDisplayInsets(
                            activityRecord.mDisplayContent,
                            activityRecord,
                            rect,
                            activityRecord.mResolveConfigHint.mUseOverrideInsetsForConfig);
        }
    }

    public final void updateSizeCompatScale(final Rect rect, final Rect rect2) {
        this.mSizeCompatScale =
                ((Float)
                                this.mActivityRecord
                                        .mAppCompatController
                                        .mTransparentPolicy
                                        .mTransparentPolicyState
                                        .findOpaqueNotFinishingActivityBelow()
                                        .map(
                                                new Function() { // from class:
                                                                 // com.android.server.wm.AppCompatSizeCompatModePolicy$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return Float.valueOf(
                                                                AppCompatSizeCompatModePolicy.this
                                                                        .mSizeCompatScale);
                                                    }
                                                })
                                        .orElseGet(
                                                new Supplier() { // from class:
                                                                 // com.android.server.wm.AppCompatSizeCompatModePolicy$$ExternalSyntheticLambda2
                                                    @Override // java.util.function.Supplier
                                                    public final Object get() {
                                                        float min;
                                                        AppCompatSizeCompatModePolicy
                                                                appCompatSizeCompatModePolicy =
                                                                        AppCompatSizeCompatModePolicy
                                                                                .this;
                                                        Rect rect3 = rect;
                                                        Rect rect4 = rect2;
                                                        appCompatSizeCompatModePolicy.getClass();
                                                        int width = rect3.width();
                                                        int height = rect3.height();
                                                        int width2 = rect4.width();
                                                        int height2 = rect4.height();
                                                        if (width <= width2 && height <= height2) {
                                                            ActivityRecord activityRecord =
                                                                    appCompatSizeCompatModePolicy
                                                                            .mActivityRecord;
                                                            Context context =
                                                                    activityRecord
                                                                            .mAtmService
                                                                            .mContext;
                                                            boolean z =
                                                                    DesktopModeLaunchParamsModifier
                                                                            .ENFORCE_DEVICE_RESTRICTIONS;
                                                            if (!Flags.enableDesktopWindowingMode()
                                                                    || ((DesktopModeLaunchParamsModifier
                                                                                            .enforceDeviceRestrictions()
                                                                                    && !DesktopModeLaunchParamsModifier
                                                                                            .isDesktopModeSupported(
                                                                                                    context))
                                                                            || activityRecord
                                                                                            .getWindowingMode()
                                                                                    != 5)) {
                                                                min = 1.0f;
                                                                return Float.valueOf(min);
                                                            }
                                                        }
                                                        min =
                                                                Math.min(
                                                                        width2 / width,
                                                                        height2 / height);
                                                        return Float.valueOf(min);
                                                    }
                                                }))
                        .floatValue();
    }
}
