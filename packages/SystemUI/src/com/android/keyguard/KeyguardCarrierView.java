package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyguardCarrierView extends KeyguardInputView {
    public KeyguardCarrierView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (isShown()) {
            getRootView().setSystemUiVisibility(getRootView().getSystemUiVisibility() | 4194304);
        }
    }

    public KeyguardCarrierView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final void startAppearAnimation() {
    }
}
