package com.samsung.android.biometrics.app.setting.prompt;

import android.content.Context;

import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialCoverWindow;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class BiometricPromptCoverClient extends BiometricPromptClient {
    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient
    public final SysUiWindow createAuthCredentialWindow() {
        return new AuthCredentialCoverWindow(this.mContext, this.mPromptConfig);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient
    public final BiometricPromptWindow createBiometricPromptWindow() {
        Context context = this.mContext;
        return new BiometricPromptCoverWindow(
                context, this.mPromptConfig, context.getMainLooper(), null);
    }
}
