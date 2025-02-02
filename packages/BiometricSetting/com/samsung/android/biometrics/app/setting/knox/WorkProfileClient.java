package com.samsung.android.biometrics.app.setting.knox;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* loaded from: classes.dex */
public final class WorkProfileClient extends KnoxPromptClient {
    private final WorkProfileSysUiClientHelper mClientHelper;

    public WorkProfileClient(Context context, int i, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, Looper looper, Bundle bundle, String str, PromptConfig promptConfig) {
        super(context, i, iSemBiometricSysUiCallback, looper, bundle, str, promptConfig);
        this.mClientHelper = new WorkProfileSysUiClientHelper(getContext(), getPromptConfig());
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient
    protected final SysUiWindow createAuthCredentialWindow() {
        return new KnoxAuthCredentialWindow(this.mContext, getPromptConfig(), this.mClientHelper);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationFailed(String str) {
        this.mClientHelper.handleAuthenticationFailed();
        super.onAuthenticationFailed(str);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient, com.samsung.android.biometrics.app.setting.SysUiClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationSucceeded(String str) {
        Log.d("KKG::WorkProfileClient", "onAuthenticationSucceeded");
        this.mClientHelper.handleAuthenticationSucceeded();
        if (this.mClientHelper.interceptHandleAuthenticationSucceededIfNeeded()) {
            return;
        }
        super.onAuthenticationSucceeded(str);
    }
}
