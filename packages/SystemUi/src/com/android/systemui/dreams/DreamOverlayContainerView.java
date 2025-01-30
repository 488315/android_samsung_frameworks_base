package com.android.systemui.dreams;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DreamOverlayContainerView extends ConstraintLayout {
    public DreamOverlayContainerView(Context context) {
        this(context, null);
    }

    public DreamOverlayContainerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DreamOverlayContainerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DreamOverlayContainerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
