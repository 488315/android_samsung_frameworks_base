package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.samsung.android.biometrics.app.setting.R;

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
