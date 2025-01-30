package com.samsung.android.knox.devicesecurity;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.container.AuthenticationConfig;
import com.samsung.android.knox.container.BasePasswordPolicy;
import com.samsung.android.knox.devicesecurity.IPasswordPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PasswordPolicy {
    public static final String ACTION_PASSWORD_CHANGED_INTERNAL = "com.samsung.android.knox.intent.action.PASSWORD_CHANGED_INTERNAL";
    public static final String ACTION_PASSWORD_EXPIRING_NOTIFICATION_INTERNAL = "com.samsung.android.knox.intent.action.NOTIFICATION_PASSWORD_EXPIRED_INTERNAL";
    public static final String ACTION_PWD_CHANGE_TIMEOUT_INTERNAL = "com.samsung.android.knox.intent.action.PWD_CHANGE_TIMEOUT_INTERNAL";
    public static final int BIOMETRIC_AUTHENTICATION_FACE = 4;
    public static final int BIOMETRIC_AUTHENTICATION_FINGERPRINT = 1;
    public static final int BIOMETRIC_AUTHENTICATION_IRIS = 2;
    public static final int PWD_CHANGE_ENFORCED = 1;
    public static final int PWD_CHANGE_ENFORCED_CANCELLED = 2;
    public static final int PWD_CHANGE_ENFORCED_INCALL = -2;
    public static final int PWD_CHANGE_ENFORCED_INCALL_CANCELLED = -3;
    public static final int PWD_CHANGE_ENFORCED_INCALL_NEW_PASSWORD = -4;
    public static final int PWD_CHANGE_ENFORCED_NEW_PASSWORD = 3;
    public static final int PWD_CHANGE_ENFORCE_CANCELLING = -1;
    public static final int PWD_CHANGE_NOT_ENFORCED = 0;
    public static String TAG = "PasswordPolicy";
    public static final String[] enforcePwdExceptions = {"com.android.settings.SubSettings", "com.android.settings.password.ChooseLockPassword", "com.android.settings.password.ChooseLockPattern", "com.samsung.android.settings.ChooseLockEnterpriseIdentity", "com.sec.android.service.singlesignon.activity.KerberosLoginActivity", "com.sec.android.service.singlesignon.activity.ChangePasswordActivity", "com.google.android.gsf.update.SystemUpdateInstallDialog", "com.google.android.gsf.update.SystemUpdateDownloadDialog", "com.android.phone.EmergencyDialer", "com.android.phone.OutgoingCallBroadcaster", "com.android.phone.EmergencyOutgoingCallBroadcaster", "com.android.phone.InCallScreen", "com.android.internal.policy.impl.LockScreen", "com.android.internal.policy.impl.PatternUnlockScreen", "com.android.internal.policy.impl.PasswordUnlockScreen", "com.android.incallui.InCallActivity", "com.android.server.telecom.EmergencyCallActivity", "com.samsung.android.app.telephonyui.emergencydialer.view.EmergencyDialerActivity", "com.android.incallui.SecInCallActivity", "com.android.incallui.call.InCallActivity", "com.samsung.phone.EmergencyDialer", "com.skt.prod.phone.activities.phone.OutgoingCallBroadcaster", "com.skt.prod.phone.activities.phone.TPhoneActivity", "com.sec.android.inputmethod.implement.setting.OnBoardingSettingsActivity", "com.android.settings.password.ChooseLockGeneric", "com.android.settings.password.ChooseLockGeneric$InternalActivity", "com.samsung.android.settings.knox.KnoxChooseLockTwoFactor", "com.android.settings.Settings$RegisterIrisSettingsActivity", "com.samsung.android.server.iris.enroll.IrisEnrollActivity", "com.samsung.android.server.iris.guide.IrisGuideActivity", "com.samsung.android.settings.iris.IrisLockSettings", "com.samsung.android.settings.iris.UseIrisLockSettings", "com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings", "com.samsung.android.settings.biometrics.fingerprint.RegisterFingerprint", "com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity", "com.samsung.android.settings.biometrics.fingerprint.UseFingerprintLockSettings", "com.android.settings.password.ConfirmLockPassword$InternalActivity", "com.android.settings.password.ConfirmLockPattern$InternalActivity", "com.samsung.android.settings.notification.SecRedactionInterstitial", "com.samsung.android.settings.biometrics.BiometricsAuthenticationActivity", "com.samsung.android.settings.biometrics.BiometricsSelectBackupPassword", "com.samsung.android.settings.biometrics.BiometricsBackupPassword", "com.samsung.android.settings.biometrics.BiometricsSetScreenLockActivity", "com.osp.app.signin.AccountView", "com.osp.app.signin.ui.AccountHelpPreference", "com.osp.app.signin.ui.WebContentView", "com.osp.app.signin.ui.AccountSetupCompleteView", "com.osp.app.signin.ui.UserValidateCheck", "com.samsung.android.biometrics.service.enroll.IntelligentScanEnrollActivity", "com.samsung.android.settings.biometrics.BiometricsLockSetup", "com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity", "com.samsung.android.settings.biometrics.BiometricsRotationGuide", "com.samsung.android.settings.biometrics.UseBiometricsLockSettingsGeneric", "com.samsung.android.settings.face.FaceFasterRecognition", "com.samsung.android.settings.biometrics.face.FaceLockSettings", "com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity", "com.samsung.android.settings.smartscan.SmartScanLockSettings", "com.samsung.android.settings.smartscan.SmartScanIntroduce", "com.android.settings.enterprise.ActionDisabledByAdminDialog", "com.samsung.android.knox.containercore.knoxkeyguard.LockdownActivity", "com.android.settings.password.SetNewPasswordActivity", "com.android.systemui.keyguard.KioskWorkLockActivity", "com.android.settings.password.ConfirmDeviceCredentialActivity$InternalActivity", "com.android.settings.password.ConfirmLockPattern", "com.android.settings.password.ConfirmLockPassword", "com.android.settings.Settings$RegisterIrisSettingsActivity", "com.samsung.android.settings.biometrics.iris.IrisLockSettings", "com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment$FactoryResetprotectionWarningDialogActivity"};
    public BasePasswordPolicy mBasePasswordPolicy;
    public final Context mContext;
    public ContextInfo mContextInfo;
    public IPasswordPolicy mService;

    public PasswordPolicy(ContextInfo contextInfo, Context context) {
        this.mContext = context;
        this.mContextInfo = contextInfo;
    }

    public final boolean deleteAllRestrictions() {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.deleteAllRestrictions");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.deleteAllRestrictions(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean enforcePwdChange() {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.enforcePwdChange");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.enforcePwdChange(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean excludeExternalStorageForFailedPasswordsWipe(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.excludeExternalStorageForFailedPasswordsWipe");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.excludeExternalStorageForFailedPasswordsWipe(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final BasePasswordPolicy getBasePasswordPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getBasePasswordPolicy");
        BasePasswordPolicy basePasswordPolicy = this.mBasePasswordPolicy;
        if (basePasswordPolicy == null) {
            synchronized (this) {
                basePasswordPolicy = this.mBasePasswordPolicy;
                if (basePasswordPolicy == null) {
                    basePasswordPolicy = new BasePasswordPolicy(this.mContextInfo);
                    this.mBasePasswordPolicy = basePasswordPolicy;
                }
            }
        }
        return basePasswordPolicy;
    }

    public final AuthenticationConfig getEnterpriseIdentityAuthentication() {
        return null;
    }

    public final List<String> getForbiddenStrings(boolean z) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getForbiddenStrings(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getDataForbidden!!!", e);
            return null;
        }
    }

    public final int getKeyguardDisabledFeaturesInternal(ComponentName componentName, int i) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getKeyguardDisabledFeaturesInternal(componentName, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final int getMaximumCharacterOccurences() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getMaximumCharacterOccurences(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getMaxRepeatedCharacters!!!", e);
            return 0;
        }
    }

    public final int getMaximumCharacterSequenceLength() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getMaximumCharacterSequenceLength(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getMaximumCharacterSequenceLength!!!", e);
            return 0;
        }
    }

    public final int getMaximumFailedPasswordsForDeviceDisable() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getMaximumFailedPasswordsForDisable(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getMaximumFailedPasswordsForDeviceDisable!!!", e);
            return 0;
        }
    }

    public final int getMaximumNumericSequenceLength() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getMaximumNumericSequenceLength(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getNumericSequencesForbidden!!!", e);
            return 0;
        }
    }

    public final int getMinPasswordComplexChars(ComponentName componentName) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getMinPasswordComplexChars");
        return getBasePasswordPolicy().getPasswordMinimumNonLetter(componentName);
    }

    public final int getMinimumCharacterChangeLength() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getMinimumCharacterChangeLength(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getMinCharacterChangeLength!!!", e);
            return 0;
        }
    }

    public final String getPassword(ComponentName componentName) {
        return "";
    }

    public final int getPasswordChangeTimeout() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getPasswordChangeTimeout(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return 0;
        }
    }

    public final int getPasswordExpires(ComponentName componentName) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPasswordExpires");
        long passwordExpirationTimeout = getBasePasswordPolicy().getPasswordExpirationTimeout(componentName);
        if (passwordExpirationTimeout > 0) {
            return (int) (passwordExpirationTimeout / 86400000);
        }
        return 0;
    }

    public final int getPasswordHistory(ComponentName componentName) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPasswordHistory");
        return getBasePasswordPolicy().getPasswordHistoryLength(componentName);
    }

    public final int getPasswordLockDelay() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getPasswordLockDelay(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getPasswordLockDelay!!!", e);
            return -1;
        }
    }

    public final String getRequiredPwdPatternRestrictions(boolean z) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getRequiredPwdPatternRestrictions(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return null;
        }
    }

    public final IPasswordPolicy getService() {
        if (this.mService == null) {
            this.mService = IPasswordPolicy.Stub.asInterface(ServiceManager.getService("password_policy"));
        }
        return this.mService;
    }

    public final Map<Integer, String> getSupportedBiometricAuthentications() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getSupportedBiometricAuthentications(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return null;
        }
    }

    public final boolean hasForbiddenCharacterSequence(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.hasForbiddenCharacterSequence(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean hasForbiddenData(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.hasForbiddenData(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean hasForbiddenNumericSequence(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.hasForbiddenNumericSequence(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean hasForbiddenStringDistance(String str, String str2) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.hasForbiddenStringDistance(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean hasMaxRepeatedCharacters(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.hasMaxRepeatedCharacters(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean isBiometricAuthenticationEnabled(int i) {
        if (getService() != null) {
            try {
                return this.mService.isBiometricAuthenticationEnabled(this.mContextInfo, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
        Log.d(TAG, "PasswordPolicy.isBiometricAuthenticationEnabled : getService() == null");
        return false;
    }

    public final boolean isBiometricAuthenticationEnabledAsUser(int i, int i2) {
        if (getService() != null) {
            try {
                return this.mService.isBiometricAuthenticationEnabledAsUser(i, i2);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
        Log.d(TAG, "PasswordPolicy.isBiometricAuthenticationEnabled : getService() == null");
        return false;
    }

    public final int isChangeRequested() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.isChangeRequested(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return 0;
        }
    }

    public final int isChangeRequestedForInner() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.isChangeRequestedForInner();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return 0;
        }
    }

    public final boolean isClearLockAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isClearLockAllowed();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return true;
        }
    }

    public final boolean isExternalStorageForFailedPasswordsWipeExcluded() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isExternalStorageForFailedPasswordsWipeExcluded(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final boolean isMDMDisabledFP(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isMDMDisabledFP(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final boolean isMultifactorAuthenticationEnabled() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isMultifactorAuthenticationEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final boolean isPasswordPatternMatched(String str) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isPasswordPatternMatched(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isPasswordTableExist() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isPasswordTableExist(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final boolean isPasswordVisibilityEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isPasswordVisibilityEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return true;
        }
    }

    public final boolean isScreenLockPatternVisibilityEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isScreenLockPatternVisibilityEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return true;
        }
    }

    public final boolean isServiceRunning() {
        if (getService() == null) {
            return false;
        }
        Log.d(TAG, "isServiceRunning()");
        return true;
    }

    public final boolean lock() {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.lock");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.lock(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final void reboot(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "reboot");
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.reboot");
        Log.w(TAG, "password policy : reboot");
        if (getService() != null) {
            try {
                Log.w(TAG, "password policy : reboot getService()");
                this.mService.reboot(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final boolean setBiometricAuthenticationEnabled(int i, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setBiometricAuthenticationEnabled");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setBiometricAuthenticationEnabled(this.mContextInfo, i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final int setEnterpriseIdentityAuthentication(AuthenticationConfig authenticationConfig) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setEnterpriseIdentityAuthentication");
        return -1;
    }

    public final boolean setForbiddenStrings(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setForbiddenStrings");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setForbiddenStrings(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setDataForbidden!!!", e);
            return false;
        }
    }

    public final void setKeyguardDisabledFeaturesInternal(ComponentName componentName, int i, int i2) {
        if (getService() != null) {
            try {
                this.mService.setKeyguardDisabledFeaturesInternal(componentName, i, i2);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final boolean setMaximumCharacterOccurrences(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setMaximumCharacterOccurrences");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMaximumCharacterOccurrences(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setMaxRepeatedCharacters!!!", e);
            return false;
        }
    }

    public final boolean setMaximumCharacterSequenceLength(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setMaximumCharacterSequenceLength");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMaximumCharacterSequenceLength(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setMaximumCharacterSequenceLength!!!", e);
            return false;
        }
    }

    public final boolean setMaximumFailedPasswordsForDeviceDisable(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setMaximumFailedPasswordsForDeviceDisable");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMaximumFailedPasswordsForDisable(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setMaximumFailedPasswordsForDeviceDisable", e);
            return false;
        }
    }

    public final void setMaximumFailedPasswordsForWipe(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setMaximumFailedPasswordsForWipe");
        if (getService() != null) {
            try {
                this.mService.setMaximumFailedPasswordsForWipe(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final boolean setMaximumNumericSequenceLength(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setMaximumNumericSequenceLength");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMaximumNumericSequenceLength(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setNumericSequencesForbidden!!!", e);
            return false;
        }
    }

    public final void setMinPasswordComplexChars(ComponentName componentName, int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setMinPasswordComplexChars");
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setMinPasswordComplexChars");
        getBasePasswordPolicy().setPasswordMinimumNonLetter(componentName, i);
        if (i < 4) {
            getBasePasswordPolicy().setPasswordMinimumUpperCase(componentName, 0);
            getBasePasswordPolicy().setPasswordMinimumLowerCase(componentName, 0);
        } else {
            Log.d(TAG, "length is bigger than 4! set Upper & lower case");
            getBasePasswordPolicy().setPasswordMinimumUpperCase(componentName, 1);
            getBasePasswordPolicy().setPasswordMinimumLowerCase(componentName, 1);
        }
    }

    public final boolean setMinimumCharacterChangeLength(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setMinimumCharacterChangeLength");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMinimumCharacterChangeLength(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setMinCharacterChangeLength!!!", e);
            return false;
        }
    }

    public final void setMultifactorAuthenticationEnabled(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setMultifactorAuthenticationEnabled");
        if (getService() != null) {
            try {
                this.mService.setMultifactorAuthenticationEnabled(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final boolean setPasswordChangeTimeout(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setPasswordChangeTimeout");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setPasswordChangeTimeout(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final void setPasswordExpires(ComponentName componentName, int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setPasswordExpires");
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setPasswordExpires");
        getBasePasswordPolicy().setPasswordExpirationTimeout(componentName, i * 86400000);
    }

    public final void setPasswordHistory(ComponentName componentName, int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setPasswordHistory");
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setPasswordHistory");
        if (i < 0) {
            return;
        }
        getBasePasswordPolicy().setPasswordHistoryLength(componentName, i);
    }

    public final boolean setPasswordLockDelay(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setPasswordLockDelay");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setPasswordLockDelay(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setPasswordLockDelay", e);
            return false;
        }
    }

    public final void setPasswordMinimumLength(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setPasswordMinimumLength");
        if (getService() != null) {
            try {
                this.mService.setPasswordMinimumLength(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordMinimumLetters(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setPasswordMinimumLetters");
        if (getService() != null) {
            try {
                this.mService.setPasswordMinimumLetters(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordMinimumNonLetter(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setPasswordMinimumNonLetter");
        if (getService() != null) {
            try {
                this.mService.setPasswordMinimumNonLetter(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordQuality(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setPasswordQuality");
        if (getService() != null) {
            try {
                this.mService.setPasswordQuality(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final boolean setPasswordVisibilityEnabled(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setPasswordVisibilityEnabled");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setPasswordVisibilityEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final boolean setPwdChangeRequested(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setPwdChangeRequested(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setPwdChangeRequestedForInner(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setPwdChangeRequestedForInner(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setRequiredPasswordPattern(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setRequiredPasswordPattern");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setRequiredPasswordPattern(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setScreenLockPatternVisibilityEnabled(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.setScreenLockPatternVisibilityEnabled");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setScreenLockPatternVisibilityEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final boolean unlock() {
        EnterpriseLicenseManager.log(this.mContextInfo, "PasswordPolicy.unlock");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.unlock(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }
}
