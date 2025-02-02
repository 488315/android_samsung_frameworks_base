package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.ClientCallback;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* loaded from: classes.dex */
public final class FpCheckToEnrollClient extends BiometricPromptClient {
    private final FpCheckToEnrollClient$$ExternalSyntheticLambda0 mHandleTimeout;

    /* renamed from: $r8$lambda$WvyY0SpZfQt5u82QIjxp2KRy-Ec, reason: not valid java name */
    public static void m89$r8$lambda$WvyY0SpZfQt5u82QIjxp2KRyEc(FpCheckToEnrollClient fpCheckToEnrollClient) {
        fpCheckToEnrollClient.mPromptWindow.showHelpMessage(0, FingerprintManager.getErrorString(fpCheckToEnrollClient.mContext, 3, 0));
        fpCheckToEnrollClient.mHandler.postDelayed(new FpCheckToEnrollClient$$ExternalSyntheticLambda0(fpCheckToEnrollClient, 1), 2000L);
    }

    public FpCheckToEnrollClient(Context context, int i, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, Looper looper, Bundle bundle, String str, PromptConfig promptConfig) {
        super(context, i, iSemBiometricSysUiCallback, looper, bundle, str, promptConfig);
        this.mHandleTimeout = new FpCheckToEnrollClient$$ExternalSyntheticLambda0(this, 0);
    }

    private void restartTimeout() {
        this.mHandler.removeCallbacks(this.mHandleTimeout);
        this.mHandler.postDelayed(this.mHandleTimeout, 60000L);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient, com.samsung.android.biometrics.app.setting.SysUiClient
    public final int getUiType() {
        return 8;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationFailed(String str) {
        super.onAuthenticationFailed(str);
        restartTimeout();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationHelp(int i, String str) {
        super.onAuthenticationHelp(i, str);
        restartTimeout();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient, com.samsung.android.biometrics.app.setting.SysUiClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationSucceeded(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mPromptWindow.showBiometricName(str);
        }
        onTryAgainPressed();
        restartTimeout();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void start(ClientCallback clientCallback) {
        super.start(clientCallback);
        restartTimeout();
    }
}
