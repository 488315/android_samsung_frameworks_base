package com.android.wm.shell.windowdecor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/* loaded from: classes3.dex */
public class PopupHorizontalScrollView extends HorizontalScrollView {
    public PopupHorizontalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setHorizontalScrollBarEnabled(false);
        setSmoothScrollingEnabled(true);
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        scrollTo(getMaxScrollAmount(), getScrollY());
    }
}
