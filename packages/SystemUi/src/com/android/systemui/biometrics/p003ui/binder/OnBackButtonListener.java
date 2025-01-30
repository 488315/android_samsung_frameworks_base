package com.android.systemui.biometrics.p003ui.binder;

import android.view.KeyEvent;
import android.view.View;
import android.window.OnBackInvokedCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
