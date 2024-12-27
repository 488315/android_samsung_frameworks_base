package com.android.systemui.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

public class AutoMarqueeTextView extends SafeMarqueeTextView {
    private boolean mAggregatedVisible;

    public AutoMarqueeTextView(Context context) {
        super(context);
        this.mAggregatedVisible = false;
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setSelected(true);
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setSelected(false);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        onVisibilityAggregated(isVisibleToUser());
    }

    @Override // android.widget.TextView, android.view.View
    public void onVisibilityAggregated(boolean z) {
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

    public AutoMarqueeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAggregatedVisible = false;
    }

    public AutoMarqueeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAggregatedVisible = false;
    }

    public AutoMarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mAggregatedVisible = false;
    }
}
