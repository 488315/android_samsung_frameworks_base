package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.R;
import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public class AuthCredentialBottomBarButton extends AppCompatButton {
    public AuthCredentialBottomBarButton(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        setAlpha(z ? 1.0f : 0.4f);
    }

    public AuthCredentialBottomBarButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle);
    }

    public AuthCredentialBottomBarButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setEnabled(isEnabled());
        semSetButtonShapeEnabled(true);
    }
}
