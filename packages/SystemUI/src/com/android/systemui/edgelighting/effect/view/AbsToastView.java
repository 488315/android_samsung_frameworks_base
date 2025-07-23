package com.android.systemui.edgelighting.effect.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class AbsToastView extends FrameLayout {
    public Boolean isAnimating;
    public boolean mIsHiding;
    public int mMaxWidth;
    public int mMinWidth;
    public int mScreenWidth;

    public AbsToastView(Context context) {
        super(context);
        this.isAnimating = Boolean.FALSE;
        this.mIsHiding = false;
        this.mMinWidth = 95;
        this.mMaxWidth = 730;
    }

    public AbsToastView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isAnimating = Boolean.FALSE;
        this.mIsHiding = false;
        this.mMinWidth = 95;
        this.mMaxWidth = 730;
    }

    public AbsToastView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isAnimating = Boolean.FALSE;
        this.mIsHiding = false;
        this.mMinWidth = 95;
        this.mMaxWidth = 730;
    }
}
