package com.android.systemui.searcle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SearcleTipView extends FrameLayout {
    public SearcleTipPopup$makeRootLayout$1$1 dismiss;

    public SearcleTipView(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent == null || keyEvent.getAction() != 0 || (keyEvent.getKeyCode() != 4 && keyEvent.getKeyCode() != 111 && keyEvent.getKeyCode() != 67)) {
            return super.dispatchKeyEvent(keyEvent);
        }
        SearcleTipPopup$makeRootLayout$1$1 searcleTipPopup$makeRootLayout$1$1 = this.dismiss;
        if (searcleTipPopup$makeRootLayout$1$1 == null) {
            return true;
        }
        searcleTipPopup$makeRootLayout$1$1.run();
        return true;
    }

    public SearcleTipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SearcleTipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
