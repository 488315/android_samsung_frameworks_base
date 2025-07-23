package com.android.internal.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.android.internal.widget.ViewPager;

/* loaded from: classes5.dex */
public class ResolverViewPager extends ViewPager {
    private boolean mSwipingEnabled;

    public ResolverViewPager(Context context) {
        super(context);
        this.mSwipingEnabled = true;
    }

    public ResolverViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSwipingEnabled = true;
    }

    public ResolverViewPager(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSwipingEnabled = true;
    }

    public ResolverViewPager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSwipingEnabled = true;
    }

    @Override // com.android.internal.widget.ViewPager, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (View.MeasureSpec.getMode(i2) != Integer.MIN_VALUE) {
            return;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        int measuredHeight = getMeasuredHeight();
        int i3 = 0;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            View childAt = getChildAt(i4);
            childAt.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight, Integer.MIN_VALUE));
            if (i3 < childAt.getMeasuredHeight()) {
                i3 = childAt.getMeasuredHeight();
            }
        }
        if (i3 > 0) {
            measuredHeight = i3;
        }
        super.onMeasure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
    }

    void setSwipingEnabled(boolean z) {
        this.mSwipingEnabled = z;
    }

    @Override // com.android.internal.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return !isLayoutRtl() && this.mSwipingEnabled && super.onInterceptTouchEvent(motionEvent);
    }
}
