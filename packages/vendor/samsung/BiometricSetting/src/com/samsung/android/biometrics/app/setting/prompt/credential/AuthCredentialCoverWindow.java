package com.samsung.android.biometrics.app.setting.prompt.credential;

import com.samsung.android.biometrics.app.setting.R;

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
