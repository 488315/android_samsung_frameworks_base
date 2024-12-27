package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class KeyguardArrowView extends FrameLayout {
    public KeyguardArrowView(Context context) {
        this(context, null, 0);
    }

    public KeyguardArrowView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardArrowView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
