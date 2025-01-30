package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
