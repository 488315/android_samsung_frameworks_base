package com.samsung.android.biometrics.app.setting.prompt.credential;

import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptWindow;

public final class AuthCredentialListBoxWindow extends BiometricPromptWindow {
    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptWindow
    public final BiometricPromptGuiHelper createGuiHelper(int i) {
        AuthCredentialListBoxGuiHelper authCredentialListBoxGuiHelper =
                new AuthCredentialListBoxGuiHelper(
                        this.mContext, this.mBaseView, this.mPromptConfig, null);
        authCredentialListBoxGuiHelper.TAG += ".Cr";
        return authCredentialListBoxGuiHelper;
    }
}
