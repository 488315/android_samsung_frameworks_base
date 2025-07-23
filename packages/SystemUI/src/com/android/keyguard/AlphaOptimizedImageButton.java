package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AlphaOptimizedImageButton extends ImageButton {
    public AlphaOptimizedImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.ImageView, android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }
}
