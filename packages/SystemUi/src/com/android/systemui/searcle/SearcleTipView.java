package com.android.systemui.searcle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SearcleTipView extends FrameLayout {
    public Runnable dismiss;

    public SearcleTipView(Context context) {
        super(context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0037, code lost:
    
        if (r1 != false) goto L26;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean z = false;
        if (keyEvent != null && keyEvent.getAction() == 0) {
            if (!(keyEvent != null && keyEvent.getKeyCode() == 4)) {
                if (!(keyEvent != null && keyEvent.getKeyCode() == 111)) {
                    if (keyEvent != null && keyEvent.getKeyCode() == 67) {
                        z = true;
                    }
                }
            }
            Runnable runnable = this.dismiss;
            if (runnable != null) {
                runnable.run();
            }
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public SearcleTipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SearcleTipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
