package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.List;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class ConversationHeaderLinearLayout extends LinearLayout {
    public ConversationHeaderLinearLayout(Context context) {
        super(context);
    }

    public ConversationHeaderLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ConversationHeaderLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private int calculateTotalChildLength() {
        int childCount = getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt != null && childAt.getVisibility() != 8) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                i += childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
        }
        return i + getPaddingLeft() + getPaddingRight();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int calculateTotalChildLength = calculateTotalChildLength() - getMeasuredWidth();
        if (calculateTotalChildLength <= 0) {
            return;
        }
        int childCount = getChildCount();
        ArrayList arrayList = null;
        float f = 0.0f;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt != null && childAt.getVisibility() != 8) {
                float f2 = ((LinearLayout.LayoutParams) childAt.getLayoutParams()).weight;
                if (f2 != 0.0f && childAt.getMeasuredWidth() != 0) {
                    if (arrayList == null) {
                        arrayList = new ArrayList(childCount);
                    }
                    arrayList.add(new ViewInfo(childAt));
                    f += Math.max(0.0f, f2);
                }
            }
        }
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        balanceViewWidths(arrayList, f, calculateTotalChildLength);
        remeasureChangedChildren(arrayList);
    }

    private void remeasureChangedChildren(List<ViewInfo> list) {
        for (ViewInfo viewInfo : list) {
            if (viewInfo.mWidth != viewInfo.mStartWidth) {
                viewInfo.mView.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, viewInfo.mWidth), 1073741824), View.MeasureSpec.makeMeasureSpec(viewInfo.mView.getMeasuredHeight(), 1073741824));
            }
        }
    }

    void balanceViewWidths(List<ViewInfo> list, float f, int i) {
        boolean z;
        for (boolean z2 = true; z2 && i > 0 && f > 0.0f; z2 = z) {
            float f2 = 0.0f;
            z = false;
            int i2 = 0;
            for (ViewInfo viewInfo : list) {
                if (viewInfo.mWeight > 0.0f && viewInfo.mWidth > 0) {
                    int i3 = (int) (viewInfo.mWidth - (i * (viewInfo.mWeight / f)));
                    if (i3 < 0) {
                        z = true;
                        i3 = 0;
                    }
                    i2 += viewInfo.mWidth - i3;
                    viewInfo.mWidth = i3;
                    if (viewInfo.mWidth > 0) {
                        f2 += viewInfo.mWeight;
                    }
                }
            }
            i -= i2;
            f = f2;
        }
    }

    static class ViewInfo {
        final int mStartWidth;
        final View mView;
        final float mWeight;
        int mWidth;

        ViewInfo(View view) {
            this.mView = view;
            this.mWeight = ((LinearLayout.LayoutParams) view.getLayoutParams()).weight;
            int measuredWidth = view.getMeasuredWidth();
            this.mWidth = measuredWidth;
            this.mStartWidth = measuredWidth;
        }
    }
}
