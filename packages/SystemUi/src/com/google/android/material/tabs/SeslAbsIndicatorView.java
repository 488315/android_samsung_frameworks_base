package com.google.android.material.tabs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
abstract class SeslAbsIndicatorView extends View {
    public SeslAbsIndicatorView(Context context) {
        super(context);
    }

    public abstract void onHide();

    public abstract void onSetSelectedIndicatorColor(int i);

    public abstract void onShow();

    public abstract void startPressEffect();

    public abstract void startReleaseEffect();

    public SeslAbsIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SeslAbsIndicatorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public SeslAbsIndicatorView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
