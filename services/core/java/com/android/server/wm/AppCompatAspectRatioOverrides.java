package com.android.server.wm;

import android.R;
import android.app.AppGlobals;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.wm.utils.OptPropFactory;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCompatAspectRatioOverrides {
    public final ActivityRecord mActivityRecord;
    public final OptPropFactory.OptProp mAllowMinAspectRatioOverrideOptProp;
    public final OptPropFactory.OptProp mAllowOrientationOverrideOptProp;
    public final OptPropFactory.OptProp mAllowUserAspectRatioFullscreenOverrideOptProp;
    public final OptPropFactory.OptProp mAllowUserAspectRatioOverrideOptProp;
    public final AppCompatConfiguration mAppCompatConfiguration;
    public final AppCompatDeviceStateQuery mAppCompatDeviceStateQuery;
    public final AppCompatReachabilityOverrides mAppCompatReachabilityOverrides;
    public final UserAspectRatioState mUserAspectRatioState;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserAspectRatioState {
        public int mUserAspectRatio;
    }

    public AppCompatAspectRatioOverrides(ActivityRecord activityRecord, final AppCompatConfiguration appCompatConfiguration, OptPropFactory optPropFactory, AppCompatDeviceStateQuery appCompatDeviceStateQuery, AppCompatReachabilityOverrides appCompatReachabilityOverrides) {
        this.mActivityRecord = activityRecord;
        this.mAppCompatConfiguration = appCompatConfiguration;
        this.mAppCompatDeviceStateQuery = appCompatDeviceStateQuery;
        UserAspectRatioState userAspectRatioState = new UserAspectRatioState();
        userAspectRatioState.mUserAspectRatio = 0;
        this.mUserAspectRatioState = userAspectRatioState;
        this.mAppCompatReachabilityOverrides = appCompatReachabilityOverrides;
        this.mAllowMinAspectRatioOverrideOptProp = optPropFactory.create("android.window.PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE");
        Objects.requireNonNull(appCompatConfiguration);
        final int i = 0;
        this.mAllowUserAspectRatioOverrideOptProp = optPropFactory.create("android.window.PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_OVERRIDE", new BooleanSupplier() { // from class: com.android.server.wm.AppCompatAspectRatioOverrides$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                int i2 = i;
                AppCompatConfiguration appCompatConfiguration2 = appCompatConfiguration;
                switch (i2) {
                    case 0:
                        return appCompatConfiguration2.isUserAppAspectRatioSettingsEnabled();
                    default:
                        return appCompatConfiguration2.isUserAppAspectRatioFullscreenEnabled();
                }
            }
        });
        final int i2 = 1;
        this.mAllowUserAspectRatioFullscreenOverrideOptProp = optPropFactory.create("android.window.PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_FULLSCREEN_OVERRIDE", new BooleanSupplier() { // from class: com.android.server.wm.AppCompatAspectRatioOverrides$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                int i22 = i2;
                AppCompatConfiguration appCompatConfiguration2 = appCompatConfiguration;
                switch (i22) {
                    case 0:
                        return appCompatConfiguration2.isUserAppAspectRatioSettingsEnabled();
                    default:
                        return appCompatConfiguration2.isUserAppAspectRatioFullscreenEnabled();
                }
            }
        });
        this.mAllowOrientationOverrideOptProp = optPropFactory.create("android.window.PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE");
    }

    public final float getDefaultMinAspectRatioForUnresizableApps() {
        AppCompatConfiguration appCompatConfiguration = this.mAppCompatConfiguration;
        boolean z = appCompatConfiguration.mIsSplitScreenAspectRatioForUnresizableAppsEnabled;
        ActivityRecord activityRecord = this.mActivityRecord;
        if (z && activityRecord.getDisplayArea() != null) {
            return getSplitScreenAspectRatio();
        }
        float f = appCompatConfiguration.mDefaultMinAspectRatioForUnresizableApps;
        if (f > 1.0f) {
            return f;
        }
        return (activityRecord.getDisplayArea() == null || !appCompatConfiguration.mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox) ? appCompatConfiguration.mFixedOrientationLetterboxAspectRatio : getDisplaySizeMinAspectRatio();
    }

    public float getDisplaySizeMinAspectRatio() {
        ActivityRecord activityRecord = this.mActivityRecord;
        TaskDisplayArea displayArea = activityRecord.getDisplayArea();
        return displayArea == null ? activityRecord.info.getMinAspectRatio() : AppCompatUtils.computeAspectRatio(new Rect(displayArea.getWindowConfiguration().getAppBounds()));
    }

    public final float getSplitScreenAspectRatio() {
        ActivityRecord activityRecord = this.mActivityRecord;
        TaskDisplayArea displayArea = activityRecord.getDisplayArea();
        if (displayArea == null) {
            return getDefaultMinAspectRatioForUnresizableApps();
        }
        int dimensionPixelSize = activityRecord.mWmService.mContext.getResources().getDimensionPixelSize(R.dimen.harmful_app_padding_top) - (activityRecord.mWmService.mContext.getResources().getDimensionPixelSize(R.dimen.harmful_app_name_padding_top) * 2);
        Rect rect = new Rect(displayArea.getWindowConfiguration().getAppBounds());
        if (rect.width() >= rect.height()) {
            rect.inset(dimensionPixelSize / 2, 0);
            rect.right = rect.centerX();
        } else {
            rect.inset(0, dimensionPixelSize / 2);
            rect.bottom = rect.centerY();
        }
        return AppCompatUtils.computeAspectRatio(rect);
    }

    public final float getUserMinAspectRatio() {
        UserAspectRatioState userAspectRatioState = this.mUserAspectRatioState;
        int i = userAspectRatioState.mUserAspectRatio;
        if (i == 1) {
            return getSplitScreenAspectRatio();
        }
        if (i == 2) {
            return getDisplaySizeMinAspectRatio();
        }
        if (i == 3) {
            return 1.3333334f;
        }
        if (i == 4) {
            return 1.7777778f;
        }
        if (i == 5) {
            return 1.5f;
        }
        throw new AssertionError("Unexpected user min aspect ratio override: " + userAspectRatioState.mUserAspectRatio);
    }

    public final int getUserMinAspectRatioOverrideCode() {
        ActivityRecord activityRecord = this.mActivityRecord;
        try {
            activityRecord.mAtmService.getClass();
            return AppGlobals.getPackageManager().getUserMinAspectRatio(activityRecord.packageName, activityRecord.mUserId);
        } catch (RemoteException e) {
            Slog.w("ActivityTaskManager", "Exception thrown retrieving aspect ratio user override " + this, e);
            return this.mUserAspectRatioState.mUserAspectRatio;
        }
    }

    public final boolean isSystemOverrideToFullscreenEnabled() {
        int i;
        return this.mActivityRecord.info.isChangeEnabled(310816437L) && !this.mAllowOrientationOverrideOptProp.isFalse() && ((i = this.mUserAspectRatioState.mUserAspectRatio) == 0 || i == 6);
    }

    public final boolean shouldApplyUserFullscreenOverride() {
        if (!((this.mAllowUserAspectRatioOverrideOptProp.isFalse() || this.mAllowUserAspectRatioFullscreenOverrideOptProp.isFalse() || !this.mAppCompatConfiguration.isUserAppAspectRatioFullscreenEnabled()) ? false : true)) {
            return false;
        }
        int userMinAspectRatioOverrideCode = getUserMinAspectRatioOverrideCode();
        this.mUserAspectRatioState.mUserAspectRatio = userMinAspectRatioOverrideCode;
        return userMinAspectRatioOverrideCode == 6;
    }

    public final boolean shouldApplyUserMinAspectRatioOverride() {
        if (!shouldEnableUserAspectRatioSettings()) {
            return false;
        }
        if (CoreRune.MT_APP_COMPAT_ASPECT_RATIO_POLICY && this.mAllowMinAspectRatioOverrideOptProp.isFalse()) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder(), this.mActivityRecord.packageName, " declared PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE false. User aspect ratio should not applied because the package is not displayed in user settings.", "ActivityTaskManager");
            return false;
        }
        int userMinAspectRatioOverrideCode = getUserMinAspectRatioOverrideCode();
        this.mUserAspectRatioState.mUserAspectRatio = userMinAspectRatioOverrideCode;
        return (userMinAspectRatioOverrideCode == 0 || userMinAspectRatioOverrideCode == 7 || userMinAspectRatioOverrideCode == 6) ? false : true;
    }

    public final boolean shouldEnableUserAspectRatioSettings() {
        DisplayContent displayContent;
        return !this.mAllowUserAspectRatioOverrideOptProp.isFalse() && this.mAppCompatConfiguration.isUserAppAspectRatioSettingsEnabled() && (displayContent = this.mActivityRecord.mDisplayContent) != null && displayContent.getIgnoreOrientationRequest();
    }
}
