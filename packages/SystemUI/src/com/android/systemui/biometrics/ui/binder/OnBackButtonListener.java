package com.android.systemui.biometrics.ui.binder;

import android.view.KeyEvent;
import android.view.View;
import android.window.OnBackInvokedCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
