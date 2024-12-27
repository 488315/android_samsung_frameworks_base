package com.android.server.wm;

import com.android.server.wm.AppCompatUtils;
import com.android.server.wm.CompatChangeableAppsCache;
import com.android.server.wm.utils.OptPropFactory;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCompatCameraOverrides {
    public final ActivityRecord mActivityRecord;
    public final OptPropFactory.OptProp mAllowMinAspectRatioOverrideOptProp;
    public final AppCompatCameraOverridesState mAppCompatCameraOverridesState;
    public final AppCompatConfiguration mAppCompatConfiguration;
    public final OptPropFactory.OptProp mCameraCompatAllowForceRotationOptProp;
    public final OptPropFactory.OptProp mCameraCompatAllowRefreshOptProp;
    public final OptPropFactory.OptProp mCameraCompatEnableRefreshViaPauseOptProp;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppCompatCameraOverridesState {
        public int mFreeformCameraCompatMode;
        public boolean mIsRefreshRequested;
    }

    public AppCompatCameraOverrides(ActivityRecord activityRecord, final AppCompatConfiguration appCompatConfiguration, OptPropFactory optPropFactory) {
        this.mActivityRecord = activityRecord;
        this.mAppCompatConfiguration = appCompatConfiguration;
        AppCompatCameraOverridesState appCompatCameraOverridesState = new AppCompatCameraOverridesState();
        appCompatCameraOverridesState.mFreeformCameraCompatMode = 0;
        this.mAppCompatCameraOverridesState = appCompatCameraOverridesState;
        this.mAllowMinAspectRatioOverrideOptProp = optPropFactory.create("android.window.PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE");
        Objects.requireNonNull(appCompatConfiguration);
        AppCompatUtils.AnonymousClass1 anonymousClass1 = new AppCompatUtils.AnonymousClass1(new BooleanSupplier() { // from class: com.android.server.wm.AppCompatCameraOverrides$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return AppCompatConfiguration.this.mDeviceConfig.getFlagValue("enable_compat_camera_treatment");
            }
        });
        this.mCameraCompatAllowRefreshOptProp = optPropFactory.create("android.window.PROPERTY_CAMERA_COMPAT_ALLOW_REFRESH", anonymousClass1);
        this.mCameraCompatEnableRefreshViaPauseOptProp = optPropFactory.create("android.window.PROPERTY_CAMERA_COMPAT_ENABLE_REFRESH_VIA_PAUSE", anonymousClass1);
        this.mCameraCompatAllowForceRotationOptProp = optPropFactory.create("android.window.PROPERTY_CAMERA_COMPAT_ALLOW_FORCE_ROTATION", anonymousClass1);
    }

    public final boolean isCameraActive() {
        DisplayRotationCompatPolicy displayRotationCompatPolicy;
        ActivityRecord activityRecord = this.mActivityRecord;
        DisplayContent displayContent = activityRecord.mDisplayContent;
        return (displayContent == null || (displayRotationCompatPolicy = displayContent.mAppCompatCameraPolicy.mDisplayRotationCompatPolicy) == null || !displayRotationCompatPolicy.isCameraActive(true, activityRecord)) ? false : true;
    }

    public final boolean isCameraCompatSplitScreenAspectRatioAllowed() {
        return this.mAppCompatConfiguration.mIsCameraCompatSplitScreenAspectRatioEnabled && !this.mActivityRecord.shouldCreateAppCompatDisplayInsets();
    }

    public final boolean shouldForceRotateForCameraCompat() {
        boolean z = CoreRune.MT_APP_COMPAT_CAMERA_POLICY;
        ActivityRecord activityRecord = this.mActivityRecord;
        if (z && CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY) {
            Task task = activityRecord.task;
            if (task != null) {
                int i = task.mRespectOrientationRequestOverride;
                boolean z2 = MultiTaskingAppCompatOrientationOverrides.SUPPORTS_DEFAULT_ENABLED;
                if (i == 0 || i == 32) {
                    return false;
                }
            }
            MultiTaskingAppCompatOrientationOverrides multiTaskingAppCompatOrientationOverrides = activityRecord.mAtmService.mMultiTaskingAppCompatController.mOrientationOverrides;
            String str = activityRecord.packageName;
            int i2 = activityRecord.mUserId;
            multiTaskingAppCompatOrientationOverrides.getClass();
            if (str == null || CompatChangeableAppsCache.LazyHolder.sCache.query(new CompatChangeableAppsCache$$ExternalSyntheticLambda0(str, 0), i2)) {
                return false;
            }
        }
        boolean isChangeEnabled = activityRecord.info.isChangeEnabled(263959004L);
        OptPropFactory.OptProp optProp = this.mCameraCompatAllowForceRotationOptProp;
        return (!optProp.mCondition.getAsBoolean() || optProp.getValue() == 0 || isChangeEnabled) ? false : true;
    }

    public final boolean shouldOverrideMinAspectRatioForCamera() {
        if (isCameraActive()) {
            if (this.mAllowMinAspectRatioOverrideOptProp.shouldEnableWithOptInOverrideAndOptOutProperty(this.mActivityRecord.info.isChangeEnabled(325586858L))) {
                return true;
            }
        }
        return false;
    }
}
