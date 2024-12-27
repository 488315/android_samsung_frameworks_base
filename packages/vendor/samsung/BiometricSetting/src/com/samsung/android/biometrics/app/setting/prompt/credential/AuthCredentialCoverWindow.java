package com.samsung.android.biometrics.app.setting.prompt.credential;

import com.samsung.android.biometrics.app.setting.R;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class AuthCredentialCoverWindow extends AuthCredentialWindow {
    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialWindow
    public final int getPasswordLayoutId() {
        return R.layout.biometric_prompt_credential_password_cover;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialWindow
    public final int getPatternLayoutId() {
        return R.layout.biometric_prompt_credential_pattern_cover;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialWindow
    public final boolean isApplyingTabletGUI() {
        return false;
    }
}
