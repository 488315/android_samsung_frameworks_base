package com.android.systemui.biometrics.ui.binder;

import android.view.KeyEvent;
import android.view.View;
import android.window.OnBackInvokedCallback;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class OnBackButtonListener implements View.OnKeyListener {
    public final OnBackInvokedCallback onBackInvokedCallback;

    public OnBackButtonListener(OnBackInvokedCallback onBackInvokedCallback) {
        this.onBackInvokedCallback = onBackInvokedCallback;
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (keyEvent.getAction() == 1) {
            this.onBackInvokedCallback.onBackInvoked();
        }
        return true;
    }
}
