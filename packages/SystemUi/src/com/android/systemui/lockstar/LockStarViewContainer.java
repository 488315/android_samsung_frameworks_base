package com.android.systemui.lockstar;

import android.content.Context;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LockStarViewContainer extends FrameLayout {
    public int mVisibility;

    public LockStarViewContainer(Context context) {
        super(context);
        this.mVisibility = -1;
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        if (this.mVisibility == i) {
            return;
        }
        this.mVisibility = i;
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(10, AbstractC0000x2c234b15.m1m("setVisibility ", i, ", "), "LockStarViewContainer");
        super.setVisibility(i);
    }

    public LockStarViewContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mVisibility = -1;
    }

    public LockStarViewContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVisibility = -1;
    }

    public LockStarViewContainer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mVisibility = -1;
    }
}
