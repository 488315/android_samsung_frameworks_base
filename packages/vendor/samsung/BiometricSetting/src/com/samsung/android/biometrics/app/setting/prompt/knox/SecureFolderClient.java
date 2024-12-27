package com.samsung.android.biometrics.app.setting.prompt.knox;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;

import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

public final class SecureFolderClient extends KnoxPromptClient {
    public final SecureFolderSysUiClientHelper mClientHelper;

    public SecureFolderClient(
            Context context,
            int i,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback,
            Looper looper,
            Bundle bundle,
            String str,
            PromptConfig promptConfig) {
        super(context, i, iSemBiometricSysUiCallback, looper, bundle, str, promptConfig);
        this.mClientHelper = new SecureFolderSysUiClientHelper(context, promptConfig, str);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient
    public final SysUiWindow createAuthCredentialWindow() {
        return new KnoxAuthCredentialWindow(this.mContext, this.mPromptConfig, this.mClientHelper);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationFailed(String str) {
        super.onAuthenticationFailed(str);
        SecureFolderSysUiClientHelper secureFolderSysUiClientHelper = this.mClientHelper;
        secureFolderSysUiClientHelper.getClass();
        Log.d("KKG::SecureFolderSysUiClientHelper", "onAuthenticationFailed");
        DevicePolicyManager devicePolicyManager =
                secureFolderSysUiClientHelper.mDevicePolicyManager;
        PromptConfig promptConfig = secureFolderSysUiClientHelper.mPromptConfig;
        devicePolicyManager.reportFailedBiometricAttempt(promptConfig.mUserId);
        DevicePolicyManager devicePolicyManager2 =
                secureFolderSysUiClientHelper.mDevicePolicyManager;
        int i = promptConfig.mUserId;
        int currentFailedBiometricAttempts =
                devicePolicyManager2.getCurrentFailedBiometricAttempts(i);
        if (currentFailedBiometricAttempts >= 50) {
            Log.d(
                    "KKG::SecureFolderSysUiClientHelper",
                    "mFailedBiometricUnlockAttemptsForSecureFolder ( too many failed. )");
            secureFolderSysUiClientHelper.mLockPatternUtils.clearBiometricAttemptDeadline(i);
            secureFolderSysUiClientHelper.mLockPatternUtils.requireStrongAuth(4096, i);
            if (Utils.isDesktopMode(secureFolderSysUiClientHelper.mContext)) {
                ((BiometricPromptClient) promptConfig.mCallback).onDismissed(5, null);
                return;
            } else {
                promptConfig.mCallback.onDeviceCredentialPressed();
                return;
            }
        }
        if (currentFailedBiometricAttempts == 0 || currentFailedBiometricAttempts % 5 != 0) {
            return;
        }
        secureFolderSysUiClientHelper.mLockPatternUtils.setBiometricAttemptDeadline(i, 30000);
        if (Utils.isDesktopMode(secureFolderSysUiClientHelper.mContext)) {
            ((BiometricPromptClient) promptConfig.mCallback).onDismissed(5, null);
        } else {
            promptConfig.mCallback.onDeviceCredentialPressed();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient,
              // com.samsung.android.biometrics.app.setting.SysUiClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationSucceeded(String str) {
        Log.d("KKG::SecureFolderClient", "onAuthenticationSucceeded");
        super.onAuthenticationSucceeded(str);
        SecureFolderSysUiClientHelper secureFolderSysUiClientHelper = this.mClientHelper;
        secureFolderSysUiClientHelper.getClass();
        Log.d("KKG::SecureFolderSysUiClientHelper", "onAuthenticationSucceeded");
        secureFolderSysUiClientHelper.mKnoxEventList.add(
                KnoxSamsungAnalyticsLogger.addEvent(
                        100, 1000, secureFolderSysUiClientHelper.getCurrentLockType()));
        LockPatternUtils lockPatternUtils = secureFolderSysUiClientHelper.mLockPatternUtils;
        PromptConfig promptConfig = secureFolderSysUiClientHelper.mPromptConfig;
        lockPatternUtils.clearBiometricAttemptDeadline(promptConfig.mUserId);
        secureFolderSysUiClientHelper.mDevicePolicyManager.reportSuccessfulBiometricAttempt(
                promptConfig.mUserId);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient
    public final SysUiWindow createAuthCredentialWindow(boolean z) {
        if (Settings.System.getInt(this.mContext.getContentResolver(), "any_screen_running", 0)
                == 1) {
            return createAuthCredentialWindow();
        }
        return super.createAuthCredentialWindow(z);
    }
}
