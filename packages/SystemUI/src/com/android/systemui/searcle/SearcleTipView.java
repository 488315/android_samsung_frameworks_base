package com.android.systemui.searcle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public final class SearcleTipView extends FrameLayout {
    public Runnable dismiss;

    public SearcleTipView(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent == null || keyEvent.getAction() != 0 || ((keyEvent == null || keyEvent.getKeyCode() != 4) && ((keyEvent == null || keyEvent.getKeyCode() != 111) && (keyEvent == null || keyEvent.getKeyCode() != 67)))) {
            return super.dispatchKeyEvent(keyEvent);
        }
        Runnable runnable = this.dismiss;
        if (runnable == null) {
            return true;
        }
        runnable.run();
        return true;
    }

    public SearcleTipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SearcleTipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
