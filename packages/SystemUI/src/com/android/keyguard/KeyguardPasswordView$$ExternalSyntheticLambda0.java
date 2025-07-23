package com.android.keyguard;

import android.view.WindowInsets;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardPasswordView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ KeyguardPasswordView f$0;

    public /* synthetic */ KeyguardPasswordView$$ExternalSyntheticLambda0(KeyguardPasswordView keyguardPasswordView) {
        this.f$0 = keyguardPasswordView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardPasswordView keyguardPasswordView = this.f$0;
        if (keyguardPasswordView.mPasswordEntry.isAttachedToWindow() && keyguardPasswordView.mPasswordEntry.getRootWindowInsets().isVisible(WindowInsets.Type.ime())) {
            keyguardPasswordView.mPasswordEntry.clearFocus();
            keyguardPasswordView.mPasswordEntry.getWindowInsetsController().hide(WindowInsets.Type.ime());
        }
    }
}
