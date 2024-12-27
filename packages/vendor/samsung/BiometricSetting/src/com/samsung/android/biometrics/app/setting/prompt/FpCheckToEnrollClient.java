package com.samsung.android.biometrics.app.setting.prompt;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;

import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.SysUiManager;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class FpCheckToEnrollClient extends BiometricPromptClient {
    public final FpCheckToEnrollClient$$ExternalSyntheticLambda0 mHandleTimeout;
    public final FpCheckToEnrollClient$$ExternalSyntheticLambda0 mHandlerRetry;

    public FpCheckToEnrollClient(
            Context context,
            int i,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback,
            Looper looper,
            Bundle bundle,
            String str,
            PromptConfig promptConfig) {
        super(context, i, iSemBiometricSysUiCallback, looper, bundle, str, promptConfig);
        this.mHandleTimeout = new FpCheckToEnrollClient$$ExternalSyntheticLambda0(this, 0);
        this.mHandlerRetry = new FpCheckToEnrollClient$$ExternalSyntheticLambda0(this, 1);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationFailed(String str) {
        super.onAuthenticationFailed(str);
        restartTimeout();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationHelp(int i, String str) {
        super.onAuthenticationHelp(i, str);
        restartTimeout();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient,
              // com.samsung.android.biometrics.app.setting.SysUiClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationSucceeded(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mPromptWindow.mPromptGuiHelper.showBiometricName(str);
        }
        this.mHandler.postDelayed(this.mHandlerRetry, 500L);
    }

    public final void restartTimeout() {
        this.mHandler.removeCallbacks(this.mHandleTimeout);
        this.mHandler.postDelayed(this.mHandleTimeout, 60000L);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void start(SysUiManager.AnonymousClass1 anonymousClass1) {
        super.start(anonymousClass1);
        restartTimeout();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient,
              // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void stop() {
        this.mHandler.removeCallbacks(this.mHandleTimeout);
        this.mHandler.removeCallbacks(this.mHandlerRetry);
        super.stop();
    }
}
