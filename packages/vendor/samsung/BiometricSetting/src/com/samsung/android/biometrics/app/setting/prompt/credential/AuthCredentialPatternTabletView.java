package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
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
