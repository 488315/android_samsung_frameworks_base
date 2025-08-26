package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes3.dex */
public class SecPanelBackground extends View {
    public float mMaxAlpha;

    public SecPanelBackground(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxAlpha = 0.3f;
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        super.setAlpha(f * this.mMaxAlpha);
    }
}
