package com.android.systemui.p014qp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.viewpager.widget.ViewPager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RTLViewPager extends ViewPager {
    public RTLViewPager(Context context) {
        super(context);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (i == 1) {
            setRotationY(180.0f);
        } else {
            setRotationY(0.0f);
        }
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        if (getLayoutDirection() == 1) {
            view.setRotationY(180.0f);
        } else {
            view.setRotationY(0.0f);
        }
        super.onViewAdded(view);
    }

    public RTLViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
