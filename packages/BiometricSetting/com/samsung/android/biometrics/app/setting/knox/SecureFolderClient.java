package com.samsung.android.biometrics.app.setting.knox;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* loaded from: classes.dex */
public final class SecureFolderClient extends KnoxPromptClient {
    private final SecureFolderSysUiClientHelper mClientHelper;

    public SecureFolderClient(Context context, int i, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, Looper looper, Bundle bundle, String str, PromptConfig promptConfig) {
        super(context, i, iSemBiometricSysUiCallback, looper, bundle, str, promptConfig);
        this.mClientHelper = new SecureFolderSysUiClientHelper(context, promptConfig, str);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient
    protected final SysUiWindow createAuthCredentialWindow() {
        return new KnoxAuthCredentialWindow(this.mContext, getPromptConfig(), this.mClientHelper);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationFailed(String str) {
        super.onAuthenticationFailed(str);
        this.mClientHelper.handleAuthenticationFailed();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient, com.samsung.android.biometrics.app.setting.SysUiClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationSucceeded(String str) {
        Log.d("KKG::SecureFolderClient", "onAuthenticationSucceeded");
        super.onAuthenticationSucceeded(str);
        this.mClientHelper.handleAuthenticationSucceeded();
    }
}
