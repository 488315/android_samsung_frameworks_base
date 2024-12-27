package com.android.systemui.qp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.viewpager.widget.ViewPager;

public class RTLViewPager extends ViewPager {
    public RTLViewPager(Context context) {
        super(context);
    }

    @Override // android.view.View
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
