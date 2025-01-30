package com.android.systemui.biometrics.p003ui.binder;

import android.view.KeyEvent;
import android.widget.TextView;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class OnImeSubmitListener implements TextView.OnEditorActionListener {
    public final Function1 onSubmit;

    public OnImeSubmitListener(Function1 function1) {
        this.onSubmit = function1;
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean z = keyEvent == null && (i == 0 || i == 6 || i == 5);
        boolean z2 = keyEvent != null && KeyEvent.isConfirmKey(keyEvent.getKeyCode()) && keyEvent.getAction() == 0;
        if (!z && !z2) {
            return false;
        }
        this.onSubmit.invoke(textView.getText());
        return true;
    }
}
