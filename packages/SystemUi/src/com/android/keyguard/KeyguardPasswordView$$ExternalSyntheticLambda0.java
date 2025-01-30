package com.android.keyguard;

import android.view.WindowInsets;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
