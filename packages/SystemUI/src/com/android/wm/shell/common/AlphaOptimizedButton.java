package com.android.wm.shell.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class AlphaOptimizedButton extends Button {
    public AlphaOptimizedButton(Context context) {
        super(context);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public AlphaOptimizedButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AlphaOptimizedButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AlphaOptimizedButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
