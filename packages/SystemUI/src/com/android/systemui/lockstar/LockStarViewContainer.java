package com.android.systemui.lockstar;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

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
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(10, "LockStarViewContainer", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "setVisibility ", ", "));
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
