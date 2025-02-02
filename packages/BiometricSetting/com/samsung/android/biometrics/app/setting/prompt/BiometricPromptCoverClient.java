package com.samsung.android.biometrics.app.setting.prompt;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.credential.AuthCredentialCoverWindow;

/* loaded from: classes.dex */
public final class BiometricPromptCoverClient extends BiometricPromptClient {
    public BiometricPromptCoverClient(Context context, int i, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, Looper looper, Bundle bundle, String str, PromptConfig promptConfig) {
        super(context, i, iSemBiometricSysUiCallback, looper, bundle, str, promptConfig);
        Log.i("BSS_BiometricPromptCoverClient", "BiometricPromptCoverClient: ");
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient
    protected final SysUiWindow createAuthCredentialWindow() {
        return new AuthCredentialCoverWindow(this.mContext, getPromptConfig());
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient
    protected final BiometricPromptWindow createBiometricPromptWindow() {
        return new BiometricPromptCoverWindow(this.mContext, getPromptConfig(), this.mContext.getMainLooper());
    }
}
