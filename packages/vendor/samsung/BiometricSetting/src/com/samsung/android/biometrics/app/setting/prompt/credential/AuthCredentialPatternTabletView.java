package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.content.Context;
import android.util.AttributeSet;

public class AuthCredentialPatternTabletView extends AuthCredentialPatternView {
    public AuthCredentialPatternTabletView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final boolean isTwoPaneLandScape() {
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPatternView
    public final void resizePattern() {}

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void setDescriptionMargins() {}
}
