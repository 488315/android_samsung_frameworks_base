package com.android.systemui.qs.customize;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BottomButtonFrameLayout extends FrameLayout {
    public BottomButtonFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchWindowFocusChanged(boolean z) {
        if (isPressed()) {
            return;
        }
        super.dispatchWindowFocusChanged(z);
    }
}
