package com.samsung.android.biometrics.app.setting.credential;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;

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
