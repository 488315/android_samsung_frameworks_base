package com.samsung.android.biometrics.app.setting.prompt;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.biometrics.PromptInfo;
import android.os.Bundle;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;

import com.samsung.android.biometrics.app.setting.Utils;

import java.util.List;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class PromptConfig {
    public final int mAvailableBiometric;
    public BiometricPromptCallback mCallback;
    public final int mCredentialType;
    public final Bundle mExtraInfo;
    public final boolean mIsKnoxManagedProfile;
    public final boolean mIsManagedProfile;
    public final boolean mIsSecureFolder;
    public final LockPatternUtils mLockPatternUtils;
    public final int mNumberOfAvailableBiometrics;
    public final long mOperationId;
    public final int mOrganizationColor;
    public final String mPackageName;
    public final int mPrimaryBiometric;
    public final PromptInfo mPromptInfo;
    public Bundle mSavedInstanceState;
    public final int mUserId;

    public PromptConfig(
            Context context,
            PromptInfo promptInfo,
            Bundle bundle,
            int i,
            int i2,
            long j,
            String str) {
        this(context, promptInfo, bundle, i, i2, j, str, new LockPatternUtils(context));
    }

    public final boolean canUseFingerprint() {
        return (this.mAvailableBiometric & 2) != 0;
    }

    public final ApplicationInfo getApplicationInfoForLogo(
            Context context, ComponentName componentName) {
        String str;
        String str2 = this.mPackageName;
        if (componentName != null) {
            str = componentName.getPackageName();
        } else {
            if (!this.mPromptInfo.isAllowBackgroundAuthentication()) {
                boolean z = Utils.DEBUG;
                if (!"android".contentEquals(str2)) {
                    str = null;
                }
            }
            str = str2;
        }
        if (str == null) {
            Log.w("BSS_PromptConfig", "Cannot find application info for " + str2);
        } else {
            try {
                return context.getPackageManager().getApplicationInfo(str, 4194816);
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("BSS_PromptConfig", "Cannot find application info for " + str2, e);
            }
        }
        return null;
    }

    public final ComponentName getComponentNameForLogo() {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        if (this.mPromptInfo.getComponentNameForConfirmDeviceCredentialActivity() != null) {
            return this.mPromptInfo.getComponentNameForConfirmDeviceCredentialActivity();
        }
        List tasks = ActivityTaskManager.getInstance().getTasks(1);
        if (tasks == null
                || (runningTaskInfo = (ActivityManager.RunningTaskInfo) tasks.get(0)) == null) {
            return null;
        }
        ComponentName componentName = runningTaskInfo.topActivity;
        Log.d("BSS_PromptConfig", "getComponentNameForLogo: topActivity is " + componentName);
        if (componentName == null
                || !componentName.getPackageName().contentEquals(this.mPackageName)) {
            return null;
        }
        return componentName;
    }

    public final int getPrimaryBiometricAuthenticator() {
        int i = this.mPrimaryBiometric;
        if (i == 8) {
            return 2;
        }
        if (i == 4096) {
            return 8;
        }
        if (i == 8192) {
            return 4;
        }
        if (i != 16384) {
            return i != 32768 ? 0 : 1;
        }
        return 256;
    }

    public final boolean isCheckToEnrollMode() {
        return (this.mPromptInfo.semGetPrivilegedFlag() & 1) != 0;
    }

    public final boolean isDeviceCredentialAllowed() {
        return (this.mPromptInfo.getAuthenticators() & 32768) != 0;
    }

    public final boolean isKnoxProfile() {
        return this.mIsKnoxManagedProfile || this.mIsSecureFolder;
    }

    public final String toString() {
        return "PromptInfo[Authenticators="
                + this.mPromptInfo.getAuthenticators()
                + ", PrivilegedFlag="
                + this.mPromptInfo.semGetPrivilegedFlag()
                + "] , PrimaryBiometric="
                + this.mPrimaryBiometric
                + ", Available="
                + this.mAvailableBiometric
                + ", UserId="
                + this.mUserId
                + ", OperationId="
                + this.mOperationId
                + ", CredentialType="
                + this.mCredentialType
                + ", ManagedProfile="
                + this.mIsManagedProfile
                + ", KnoxManagedProfile="
                + this.mIsKnoxManagedProfile
                + ", SecureFolder="
                + this.mIsSecureFolder
                + "\n   extra=["
                + this.mExtraInfo
                + "]";
    }

    public PromptConfig(
            Context context,
            PromptInfo promptInfo,
            Bundle bundle,
            int i,
            int i2,
            long j,
            String str,
            LockPatternUtils lockPatternUtils) {
        this.mPromptInfo = promptInfo;
        bundle = bundle == null ? new Bundle() : bundle;
        this.mExtraInfo = bundle;
        this.mLockPatternUtils = lockPatternUtils;
        this.mUserId = i2;
        this.mOperationId = j;
        int keyguardStoredPasswordQuality = lockPatternUtils.getKeyguardStoredPasswordQuality(i2);
        this.mCredentialType =
                keyguardStoredPasswordQuality != 65536
                        ? (keyguardStoredPasswordQuality == 131072
                                        || keyguardStoredPasswordQuality == 196608)
                                ? 1
                                : keyguardStoredPasswordQuality != 458752 ? 3 : 6
                        : 2;
        this.mAvailableBiometric = bundle.getInt("KEY_AVAILABILITY_BIOMETRIC", 0);
        this.mPackageName = str;
        if (!canUseFingerprint() || i == 8) {
            this.mPrimaryBiometric = i;
        } else {
            this.mPrimaryBiometric = 8;
        }
        int i3 = bundle.getInt("KEY_AVAILABILITY_BIOMETRIC", 0);
        int i4 = (i3 & 2) == 0 ? 0 : 1;
        this.mNumberOfAvailableBiometrics = (i3 & 8) != 0 ? i4 + 1 : i4;
        bundle.getInt("DISPLAY_TYPE", -1);
        this.mIsManagedProfile = bundle.getBoolean("MANAGED_PROFILE", false);
        this.mOrganizationColor = bundle.getInt("MANAGED_PROFILE_COLOR");
        this.mIsKnoxManagedProfile = bundle.getBoolean("MANAGED_PROFILE_KNOX", false);
        this.mIsSecureFolder = bundle.getBoolean("SECURE_FOLDER", false);
    }
}
