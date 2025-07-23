package com.android.systemui.statusbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class AlphaOptimizedImageView extends ImageView {
    public AlphaOptimizedImageView(Context context) {
        this(context, null);
    }

    @Override // android.widget.ImageView, android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public AlphaOptimizedImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AlphaOptimizedImageView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AlphaOptimizedImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
