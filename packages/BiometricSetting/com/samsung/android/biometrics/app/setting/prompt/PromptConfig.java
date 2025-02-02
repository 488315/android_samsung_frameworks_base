package com.samsung.android.biometrics.app.setting.prompt;

import android.content.Context;
import android.hardware.biometrics.PromptInfo;
import android.os.Bundle;
import android.text.TextUtils;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.LockPatternUtils;

/* loaded from: classes.dex */
public final class PromptConfig {
    private final int mAvailableBiometric;
    private BiometricPromptCallback mCallback;
    private final int mCredentialType;
    private final int mDisplayId;
    private final Bundle mExtraInfo;
    private final boolean mIsKnoxManagedProfile;
    private final boolean mIsManagedProfile;
    private final boolean mIsSecureFolder;
    private final LockPatternUtils mLockPatternUtils;
    private final int mNumberOfAvailableBiometrics;
    private final long mOperationId;
    private final int mOrganizationColor;
    private final int mPrimaryBiometric;
    private final PromptInfo mPromptInfo;
    private Bundle mSavedInstanceState;
    private final int mUserId;

    @VisibleForTesting
    public PromptConfig(Context context, PromptInfo promptInfo, Bundle bundle, int i, int i2, long j, LockPatternUtils lockPatternUtils) {
        this.mPromptInfo = promptInfo;
        bundle = bundle == null ? new Bundle() : bundle;
        this.mExtraInfo = bundle;
        this.mLockPatternUtils = lockPatternUtils;
        this.mUserId = i2;
        this.mOperationId = j;
        int keyguardStoredPasswordQuality = lockPatternUtils.getKeyguardStoredPasswordQuality(i2);
        this.mCredentialType = keyguardStoredPasswordQuality != 65536 ? (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) ? 1 : keyguardStoredPasswordQuality != 458752 ? 3 : 6 : 2;
        this.mAvailableBiometric = bundle.getInt("KEY_AVAILABILITY_BIOMETRIC", 0);
        if (!canUseFingerprint() || i == 8) {
            this.mPrimaryBiometric = i;
        } else {
            this.mPrimaryBiometric = 8;
        }
        int i3 = bundle.getInt("KEY_AVAILABILITY_BIOMETRIC", 0);
        int i4 = (i3 & 2) == 0 ? 0 : 1;
        this.mNumberOfAvailableBiometrics = (i3 & 8) != 0 ? i4 + 1 : i4;
        this.mDisplayId = bundle.getInt("DISPLAY_TYPE", -1);
        this.mIsManagedProfile = bundle.getBoolean("MANAGED_PROFILE", false);
        this.mOrganizationColor = bundle.getInt("MANAGED_PROFILE_COLOR");
        this.mIsKnoxManagedProfile = bundle.getBoolean("MANAGED_PROFILE_KNOX", false);
        this.mIsSecureFolder = bundle.getBoolean("SECURE_FOLDER", false);
        this.mCallback = new C03001();
    }

    public final boolean canUseFace() {
        return (this.mAvailableBiometric & 8) != 0;
    }

    public final boolean canUseFingerprint() {
        return (this.mAvailableBiometric & 2) != 0;
    }

    public final BiometricPromptCallback getCallback() {
        return this.mCallback;
    }

    public final int getCredentialType() {
        return this.mCredentialType;
    }

    public final CharSequence getDescription() {
        return this.mPromptInfo.getDescription();
    }

    public final int getDisplayId() {
        return this.mDisplayId;
    }

    public final Bundle getExtraInfo() {
        return this.mExtraInfo;
    }

    public final LockPatternUtils getLockPatternUtils() {
        return this.mLockPatternUtils;
    }

    public final CharSequence getNegativeButtonText() {
        return this.mPromptInfo.getNegativeButtonText();
    }

    public final int getNumberOfAvailableBiometrics() {
        return this.mNumberOfAvailableBiometrics;
    }

    public final long getOperationId() {
        return this.mOperationId;
    }

    public final int getOrganizationColor() {
        return this.mOrganizationColor;
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

    public final PromptInfo getPromptInfo() {
        return this.mPromptInfo;
    }

    public final Bundle getSavedInstanceState() {
        return this.mSavedInstanceState;
    }

    public final CharSequence getSubtitle() {
        return this.mPromptInfo.getSubtitle();
    }

    public final CharSequence getTitle() {
        return this.mPromptInfo.getTitle();
    }

    public final int getUserId() {
        return this.mUserId;
    }

    public final boolean hasNegativeButton() {
        return !TextUtils.isEmpty(this.mPromptInfo.getNegativeButtonText());
    }

    public final boolean isAllowBackgroundAuthentication() {
        return this.mPromptInfo.isAllowBackgroundAuthentication();
    }

    public final boolean isCheckToEnrollMode() {
        return (this.mPromptInfo.semGetPrivilegedFlag() & 1) != 0;
    }

    public final boolean isConfirmationRequested() {
        return this.mPromptInfo.isConfirmationRequested();
    }

    public final boolean isCredentialPrimary() {
        return (this.mPrimaryBiometric & 32768) != 0;
    }

    public final boolean isDeviceCredentialAllowed() {
        return (this.mPromptInfo.getAuthenticators() & 32768) != 0;
    }

    public final boolean isKnoxManagedProfile() {
        return this.mIsKnoxManagedProfile;
    }

    public final boolean isKnoxOnlyConfirmBiometric() {
        return this.mExtraInfo.getBoolean("MANAGED_PROFILE_KNOX_ONLY_CONFIRM_BIOMETRIC", false);
    }

    public final boolean isKnoxProfile() {
        return this.mIsKnoxManagedProfile || this.mIsSecureFolder;
    }

    public final boolean isManagedProfile() {
        return this.mIsManagedProfile;
    }

    public final boolean isMultiSensorMode() {
        return this.mNumberOfAvailableBiometrics > 1;
    }

    public final boolean isSecureFolder() {
        return this.mIsSecureFolder;
    }

    public final boolean isSingleSensorMode() {
        return this.mNumberOfAvailableBiometrics == 1;
    }

    public final void onSaveInstanceState(Bundle bundle) {
        this.mSavedInstanceState = bundle;
    }

    public final void setCallback(BiometricPromptCallback biometricPromptCallback) {
        this.mCallback = biometricPromptCallback;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PromptInfo[Authenticators=");
        PromptInfo promptInfo = this.mPromptInfo;
        sb.append(promptInfo.getAuthenticators());
        sb.append(", PrivilegedFlag=");
        sb.append(promptInfo.semGetPrivilegedFlag());
        sb.append("] , PrimaryBiometric=");
        sb.append(this.mPrimaryBiometric);
        sb.append(", Available=");
        sb.append(this.mAvailableBiometric);
        sb.append(", UserId=");
        sb.append(this.mUserId);
        sb.append(", OperationId=");
        sb.append(this.mOperationId);
        sb.append(", CredentialType=");
        sb.append(this.mCredentialType);
        sb.append(", ManagedProfile=");
        sb.append(this.mIsManagedProfile);
        sb.append(", KnoxManagedProfile=");
        sb.append(this.mIsKnoxManagedProfile);
        sb.append(", SecureFolder=");
        sb.append(this.mIsSecureFolder);
        sb.append("\n   extra=[");
        sb.append(this.mExtraInfo);
        sb.append("]");
        return sb.toString();
    }

    /* renamed from: com.samsung.android.biometrics.app.setting.prompt.PromptConfig$1 */
    final class C03001 implements BiometricPromptCallback {
        @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
        public final void onConfirmPressed() {
        }

        @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
        public final void onDeviceCredentialPressed() {
        }

        @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
        public final void onNegativeButtonPressed() {
        }

        @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
        public final void onSystemEvent() {
        }

        @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
        public final void onTryAgainPressed() {
        }

        @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
        public final void onModalitySwitched(int i) {
        }

        @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
        public final void onPromptError(int i) {
        }

        @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
        public final void onUserCancel(int i) {
        }

        @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
        public final void onDismissed(int i, byte[] bArr) {
        }
    }
}
