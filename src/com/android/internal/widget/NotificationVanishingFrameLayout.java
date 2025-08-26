package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class NotificationVanishingFrameLayout extends FrameLayout {
    public NotificationVanishingFrameLayout(Context context) {
        this(context, null, 0, 0);
    }

    public NotificationVanishingFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public NotificationVanishingFrameLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationVanishingFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (allChildrenGone()) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 1073741824);
            super.onMeasure(iMakeMeasureSpec, iMakeMeasureSpec);
        } else {
            super.onMeasure(i, i2);
        }
    }

    private boolean allChildrenGone() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null && childAt.getVisibility() != 8) {
                return false;
            }
        }
        return true;
    }
}
