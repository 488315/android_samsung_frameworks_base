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
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class WorkProfileClient extends KnoxPromptClient {
    public final WorkProfileSysUiClientHelper mClientHelper;

    public WorkProfileClient(
            Context context,
            int i,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback,
            Looper looper,
            Bundle bundle,
            String str,
            PromptConfig promptConfig) {
        super(context, i, iSemBiometricSysUiCallback, looper, bundle, str, promptConfig);
        this.mClientHelper = new WorkProfileSysUiClientHelper(this.mContext, this.mPromptConfig);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient
    public final SysUiWindow createAuthCredentialWindow() {
        return new KnoxAuthCredentialWindow(this.mContext, this.mPromptConfig, this.mClientHelper);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationFailed(String str) {
        WorkProfileSysUiClientHelper workProfileSysUiClientHelper = this.mClientHelper;
        workProfileSysUiClientHelper.getClass();
        Log.d("KKG::WorkProfileSysUiClientHelper", "onAuthenticationFailed");
        DevicePolicyManager devicePolicyManager = workProfileSysUiClientHelper.mDevicePolicyManager;
        PromptConfig promptConfig = workProfileSysUiClientHelper.mPromptConfig;
        devicePolicyManager.reportFailedBiometricAttempt(promptConfig.mUserId);
        DevicePolicyManager devicePolicyManager2 =
                workProfileSysUiClientHelper.mDevicePolicyManager;
        int i = promptConfig.mUserId;
        int currentFailedBiometricAttempts =
                devicePolicyManager2.getCurrentFailedBiometricAttempts(i);
        if (currentFailedBiometricAttempts >= 50) {
            Log.d(
                    "KKG::WorkProfileSysUiClientHelper",
                    "isBiometricDeadlineForWorkProfile ( too many failed. )");
            workProfileSysUiClientHelper.mLockPatternUtils.clearBiometricAttemptDeadline(i);
            if (KnoxUtils.isMultifactorEnabledForWork(workProfileSysUiClientHelper.mContext, i)) {
                Bundle bundle = new Bundle();
                bundle.putInt("android.intent.extra.user_handle", i);
                ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_ADMIN_LOCK", bundle);
                ((BiometricPromptClient) promptConfig.mCallback).onDismissed(5, null);
            } else {
                workProfileSysUiClientHelper.mLockPatternUtils.requireStrongAuth(4096, i);
                if (Utils.isDesktopMode(workProfileSysUiClientHelper.mContext)) {
                    ((BiometricPromptClient) promptConfig.mCallback).onDismissed(5, null);
                } else {
                    promptConfig.mCallback.onDeviceCredentialPressed();
                }
            }
        } else if (currentFailedBiometricAttempts != 0 && currentFailedBiometricAttempts % 5 == 0) {
            workProfileSysUiClientHelper.mLockPatternUtils.setBiometricAttemptDeadline(i, 30000);
            if (KnoxUtils.isMultifactorEnabledForWork(workProfileSysUiClientHelper.mContext, i)) {
                ((BiometricPromptClient) promptConfig.mCallback).onDismissed(5, null);
            } else if (Utils.isDesktopMode(workProfileSysUiClientHelper.mContext)) {
                ((BiometricPromptClient) promptConfig.mCallback).onDismissed(5, null);
            } else {
                promptConfig.mCallback.onDeviceCredentialPressed();
            }
        }
        super.onAuthenticationFailed(str);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient,
              // com.samsung.android.biometrics.app.setting.SysUiClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationSucceeded(String str) {
        Log.d("KKG::WorkProfileClient", "onAuthenticationSucceeded");
        WorkProfileSysUiClientHelper workProfileSysUiClientHelper = this.mClientHelper;
        workProfileSysUiClientHelper.getClass();
        Log.d("KKG::WorkProfileSysUiClientHelper", "onAuthenticationSucceeded");
        LockPatternUtils lockPatternUtils = workProfileSysUiClientHelper.mLockPatternUtils;
        PromptConfig promptConfig = workProfileSysUiClientHelper.mPromptConfig;
        lockPatternUtils.clearBiometricAttemptDeadline(promptConfig.mUserId);
        workProfileSysUiClientHelper.mDevicePolicyManager.reportSuccessfulBiometricAttempt(
                promptConfig.mUserId);
        WorkProfileSysUiClientHelper workProfileSysUiClientHelper2 = this.mClientHelper;
        workProfileSysUiClientHelper2.getClass();
        Log.d(
                "KKG::WorkProfileSysUiClientHelper",
                "interceptHandleAuthenticationSucceededIfNeeded");
        Context context = workProfileSysUiClientHelper2.mContext;
        PromptConfig promptConfig2 = workProfileSysUiClientHelper2.mPromptConfig;
        if (KnoxUtils.isMultifactorEnabledForWork(context, promptConfig2.mUserId)) {
            if (promptConfig2.mExtraInfo.getBoolean(
                    "MANAGED_PROFILE_KNOX_ONLY_CONFIRM_BIOMETRIC", false)) {
                Log.d(
                        "KKG::WorkProfileSysUiClientHelper",
                        "Only confirm biometric case when two-factor.");
            } else {
                if (!SemPersonaManager.appliedPasswordPolicy(promptConfig2.mUserId)) {
                    promptConfig2.mCallback.onDeviceCredentialPressed();
                    return;
                }
                Log.d(
                        "KKG::WorkProfileSysUiClientHelper",
                        "Applied password policy with multificator enabled.");
            }
        }
        super.onAuthenticationSucceeded(str);
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
