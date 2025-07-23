package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class NotificationMaxHeightFrameLayout extends FrameLayout {
    private final int mNotificationMaxHeight;

    public NotificationMaxHeightFrameLayout(Context context) {
        this(context, null, 0, 0);
    }

    public NotificationMaxHeightFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public NotificationMaxHeightFrameLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationMaxHeightFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mNotificationMaxHeight = getFontScaledHeight(this.mContext, R.dimen.notification_min_height);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getSize(i2) > this.mNotificationMaxHeight) {
            i2 = View.MeasureSpec.makeMeasureSpec(this.mNotificationMaxHeight, View.MeasureSpec.getMode(i2));
        }
        super.onMeasure(i, i2);
    }

    private static int getFontScaledHeight(Context context, int i) {
        return (int) (context.getResources().getDimensionPixelSize(i) * Math.max(1.0f, context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density));
    }
}
