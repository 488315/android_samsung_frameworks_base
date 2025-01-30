package com.android.systemui.p016qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QuickTileLayout extends LinearLayout {
    public QuickTileLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        int i2 = layoutParams.height;
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i2, i2);
        layoutParams2.weight = 1.0f;
        super.addView(view, i, layoutParams2);
    }

    public QuickTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setGravity(17);
    }
}
