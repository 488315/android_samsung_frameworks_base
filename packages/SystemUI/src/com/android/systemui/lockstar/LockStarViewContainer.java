package com.android.systemui.lockstar;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
