package com.android.systemui.edgelighting.effect.container;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.edgelighting.effect.container.EdgeLightingDialog;
import com.android.systemui.edgelighting.utils.Utils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AbsEdgeLightingView extends FrameLayout {
    public EdgeLightingDialog.C13144 mEdgeListener;
    public int mScreenHeight;
    public int mScreenWidth;

    public AbsEdgeLightingView(Context context) {
        super(context);
    }

    public final void resetScreenSize() {
        this.mScreenWidth = Utils.getScreenSize(getContext()).getWidth();
        this.mScreenHeight = Utils.getScreenSize(getContext()).getHeight();
    }

    public AbsEdgeLightingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AbsEdgeLightingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AbsEdgeLightingView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
