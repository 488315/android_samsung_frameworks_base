package com.android.systemui.doze;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AODOverlayContainer extends FrameLayout {
    public int mVisibility;

    public AODOverlayContainer(Context context) {
        super(context);
        this.mVisibility = -1;
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        if (this.mVisibility == i) {
            return;
        }
        this.mVisibility = i;
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("setVisibility ", i, "AODOverlayContainer");
        super.setVisibility(i);
    }

    public AODOverlayContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mVisibility = -1;
    }

    public AODOverlayContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVisibility = -1;
    }
}
