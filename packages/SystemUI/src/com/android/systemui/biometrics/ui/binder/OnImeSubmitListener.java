package com.android.systemui.biometrics.ui.binder;

import android.view.KeyEvent;
import android.widget.TextView;
import kotlin.jvm.functions.Function1;

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
