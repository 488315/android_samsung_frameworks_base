package com.android.systemui.edgelighting.effect.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class AbsToastView extends FrameLayout {
    public Boolean isAnimating;
    public boolean mIsHiding;
    public int mMaxWidth;
    public int mMinWidth;
    public int mScreenWidth;
    public boolean mToastFullColor;

    public AbsToastView(Context context) {
        super(context);
        this.isAnimating = Boolean.FALSE;
        this.mToastFullColor = false;
        this.mIsHiding = false;
        this.mMinWidth = 95;
        this.mMaxWidth = 730;
    }

    public AbsToastView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isAnimating = Boolean.FALSE;
        this.mToastFullColor = false;
        this.mIsHiding = false;
        this.mMinWidth = 95;
        this.mMaxWidth = 730;
    }

    public AbsToastView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isAnimating = Boolean.FALSE;
        this.mToastFullColor = false;
        this.mIsHiding = false;
        this.mMinWidth = 95;
        this.mMaxWidth = 730;
    }
}
