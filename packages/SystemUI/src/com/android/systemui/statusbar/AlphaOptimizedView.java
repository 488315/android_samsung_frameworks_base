package com.android.systemui.statusbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class AlphaOptimizedView extends View {
    public AlphaOptimizedView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public AlphaOptimizedView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AlphaOptimizedView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AlphaOptimizedView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
