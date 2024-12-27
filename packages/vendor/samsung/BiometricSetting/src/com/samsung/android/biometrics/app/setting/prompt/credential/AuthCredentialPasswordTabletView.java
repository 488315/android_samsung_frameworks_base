package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.samsung.android.biometrics.app.setting.R;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public class AuthCredentialPasswordTabletView extends AuthCredentialPasswordView {
    public AuthCredentialPasswordTabletView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void showMoreOptionButton() {
        TextView textView = (TextView) findViewById(R.id.customized_view_more_options_button);
        if (textView != null) {
            textView.setVisibility(0);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void setDescriptionMargins() {}

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView
    public final void updateScrollView() {}
}
