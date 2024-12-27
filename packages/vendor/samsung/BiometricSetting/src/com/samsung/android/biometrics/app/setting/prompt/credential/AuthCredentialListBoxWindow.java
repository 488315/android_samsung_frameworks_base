package com.samsung.android.biometrics.app.setting.prompt.credential;

import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptWindow;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
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
