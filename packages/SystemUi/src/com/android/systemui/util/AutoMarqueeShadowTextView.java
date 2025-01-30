package com.android.systemui.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AutoMarqueeShadowTextView extends SafeMarqueeShadowTextView {
    public boolean mAggregatedVisible;

    public AutoMarqueeShadowTextView(Context context) {
        super(context);
        this.mAggregatedVisible = false;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setSelected(true);
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setSelected(false);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        onVisibilityAggregated(isVisibleToUser());
    }

    @Override // android.widget.TextView, android.view.View
    public final void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (z == this.mAggregatedVisible) {
            return;
        }
        this.mAggregatedVisible = z;
        if (z) {
            setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            setEllipsize(TextUtils.TruncateAt.END);
        }
    }

    public AutoMarqueeShadowTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAggregatedVisible = false;
    }

    public AutoMarqueeShadowTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAggregatedVisible = false;
    }

    public AutoMarqueeShadowTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mAggregatedVisible = false;
    }
}
