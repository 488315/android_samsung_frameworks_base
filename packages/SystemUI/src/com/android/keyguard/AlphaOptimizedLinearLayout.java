package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AlphaOptimizedLinearLayout extends LinearLayout {
    public AlphaOptimizedLinearLayout(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public AlphaOptimizedLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AlphaOptimizedLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AlphaOptimizedLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
