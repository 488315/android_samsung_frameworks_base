package com.android.keyguard;

import android.view.WindowInsets;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
