package com.android.systemui.edgelighting.effect.view;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;
import com.android.systemui.edgelighting.utils.Utils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class GradientEffectView extends AbsEdgeLightingMaskView {
    public GradientEffectView(Context context) {
        super(context);
    }

    @Override // com.android.systemui.edgelighting.effect.view.AbsEdgeLightingMaskView
    public final void init() {
        super.init();
        int width = Utils.getScreenSize(getContext()).getWidth();
        int height = Utils.getScreenSize(getContext()).getHeight();
        this.mWidth = width;
        this.mHeight = height;
        setImageDrawable();
        expandViewSize(this.mTopLayer);
    }

    public final void setImageDrawable() {
        if (this.mTopLayer.getDrawable() == null) {
            this.mTopLayer.setImageResource(R.drawable.color_gradation);
        }
        if (this.mBottomLayer.getDrawable() == null) {
            this.mBottomLayer.setVisibility(8);
        }
    }

    public GradientEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GradientEffectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
