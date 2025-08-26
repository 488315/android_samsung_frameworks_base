package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class RemeasuringLinearLayout extends NotificationOptimizedLinearLayout {
    private ArrayList<View> mMatchParentViews;

    public RemeasuringLinearLayout(Context context) {
        super(context);
        this.mMatchParentViews = new ArrayList<>();
    }

    public RemeasuringLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMatchParentViews = new ArrayList<>();
    }

    public RemeasuringLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMatchParentViews = new ArrayList<>();
    }

    public RemeasuringLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMatchParentViews = new ArrayList<>();
    }

    @Override // com.android.internal.widget.NotificationOptimizedLinearLayout, android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        boolean z = getOrientation() == 1;
        boolean z2 = getLayoutParams().height == -2;
        int iMax = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt != null && childAt.getVisibility() != 8) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                if (!z2 || layoutParams.height != -1 || z) {
                    int measuredHeight = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                    if (z) {
                        measuredHeight += iMax;
                    }
                    iMax = Math.max(iMax, measuredHeight);
                } else {
                    this.mMatchParentViews.add(childAt);
                }
            }
        }
        if (this.mMatchParentViews.size() > 0) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMax, 1073741824);
            Iterator<View> it = this.mMatchParentViews.iterator();
            while (it.hasNext()) {
                View next = it.next();
                next.measure(getChildMeasureSpec(i, getPaddingStart() + getPaddingEnd(), next.getLayoutParams().width), iMakeMeasureSpec);
            }
        }
        this.mMatchParentViews.clear();
        setMeasuredDimension(getMeasuredWidth(), iMax);
    }
}
