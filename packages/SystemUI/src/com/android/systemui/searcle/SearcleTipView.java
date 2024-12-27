package com.android.systemui.searcle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
