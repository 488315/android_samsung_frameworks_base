package com.google.android.material.tabs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SeslAbsIndicatorView extends View {
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
