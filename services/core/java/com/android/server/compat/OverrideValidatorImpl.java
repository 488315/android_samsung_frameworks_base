package com.android.server.compat;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;

import com.android.internal.compat.AndroidBuildClassifier;
import com.android.internal.compat.IOverrideValidator;
import com.android.internal.compat.OverrideAllowedState;

public final class OverrideValidatorImpl extends IOverrideValidator.Stub {
    public final AndroidBuildClassifier mAndroidBuildClassifier;
    public final CompatConfig mCompatConfig;
    public final Context mContext;
    public boolean mForceNonDebuggableFinalBuild = false;

    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            OverrideValidatorImpl overrideValidatorImpl = OverrideValidatorImpl.this;
            overrideValidatorImpl.mForceNonDebuggableFinalBuild =
                    Settings.Global.getInt(
                                    overrideValidatorImpl.mContext.getContentResolver(),
                                    "force_non_debuggable_final_build_for_compat",
                                    0)
                            == 1;
        }
    }

    public OverrideValidatorImpl(
            AndroidBuildClassifier androidBuildClassifier,
            Context context,
            CompatConfig compatConfig) {
        this.mAndroidBuildClassifier = androidBuildClassifier;
        this.mContext = context;
        this.mCompatConfig = compatConfig;
    }

    public final OverrideAllowedState getOverrideAllowedState(long j, String str) {
        return getOverrideAllowedStateInternal(str, j, false);
    }

    public final OverrideAllowedState getOverrideAllowedStateInternal(
            String str, long j, boolean z) {
        CompatChange compatChange = (CompatChange) this.mCompatConfig.mChanges.get(Long.valueOf(j));
        if (compatChange != null && compatChange.getLoggingOnly()) {
            return new OverrideAllowedState(5, -1, -1);
        }
        boolean z2 =
                this.mAndroidBuildClassifier.isDebuggableBuild()
                        && !this.mForceNonDebuggableFinalBuild;
        boolean z3 =
                this.mAndroidBuildClassifier.isFinalBuild() || this.mForceNonDebuggableFinalBuild;
        CompatChange compatChange2 =
                (CompatChange) this.mCompatConfig.mChanges.get(Long.valueOf(j));
        int enableSinceTargetSdk =
                (compatChange2 == null || compatChange2.getEnableSinceTargetSdk() == -1)
                        ? -1
                        : compatChange2.getEnableSinceTargetSdk() - 1;
        CompatChange compatChange3 =
                (CompatChange) this.mCompatConfig.mChanges.get(Long.valueOf(j));
        boolean z4 = compatChange3 != null && compatChange3.getDisabled();
        if (z2) {
            return new OverrideAllowedState(0, -1, -1);
        }
        if (enableSinceTargetSdk >= this.mAndroidBuildClassifier.platformTargetSdk()) {
            return new OverrideAllowedState(6, -1, enableSinceTargetSdk);
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            throw new IllegalStateException("No PackageManager!");
        }
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 4194304);
            CompatChange compatChange4 =
                    (CompatChange) this.mCompatConfig.mChanges.get(Long.valueOf(j));
            if (compatChange4 != null
                    && compatChange4.getOverridable()
                    && (z
                            || this.mContext.checkCallingOrSelfPermission(
                                            "android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD")
                                    == 0)) {
                return new OverrideAllowedState(0, -1, -1);
            }
            int i = applicationInfo.targetSdkVersion;
            return (applicationInfo.flags & 2) == 0
                    ? new OverrideAllowedState(1, -1, -1)
                    : !z3
                            ? new OverrideAllowedState(0, i, enableSinceTargetSdk)
                            : (enableSinceTargetSdk != -1 || z4)
                                    ? (z4 || i <= enableSinceTargetSdk)
                                            ? new OverrideAllowedState(0, i, enableSinceTargetSdk)
                                            : new OverrideAllowedState(3, i, enableSinceTargetSdk)
                                    : new OverrideAllowedState(2, i, enableSinceTargetSdk);
        } catch (PackageManager.NameNotFoundException unused) {
            return new OverrideAllowedState(4, -1, -1);
        }
    }
}
