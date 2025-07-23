package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.systemui.R;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SecShelfNotificationIconContainer extends NotificationIconContainer {
    public final ArrayList mBgViews;
    public int mDotWidth;
    public int mPaddingBetweenIcons;
    public int mPaddingForDot;

    public SecShelfNotificationIconContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBgViews = new ArrayList();
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a7, code lost:
    
        if (((r6 - (r8 > -1 ? r8 : 0)) + 1) > 4) goto L50;
     */
    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void calculateIconXTranslations() {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.SecShelfNotificationIconContainer.calculateIconXTranslations():void");
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer
    public final void initResources() {
        super.initResources();
        this.mPaddingBetweenIcons = getResources().getDimensionPixelSize(R.dimen.padding_between_icons_in_shelf);
        this.mPaddingForDot = getResources().getDimensionPixelSize(R.dimen.padding_for_dot_in_shelf);
        this.mDotWidth = getResources().getDimensionPixelSize(R.dimen.overflow_dot_width);
        setIconSize(getResources().getDimensionPixelSize(R.dimen.icon_size_in_shelf));
        int i = this.mShelfIconColor;
        Log.d("SecShelfNotificationIconContainer", " onClockColorChanged - current : " + Integer.toHexString(this.mShelfIconColor) + " new : " + Integer.toHexString(i) + " F : true");
        this.mShelfIconColor = i;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        float height = getHeight() / 2.0f;
        for (int i6 = 0; i6 < getChildCount(); i6++) {
            View childAt = getChildAt(i6);
            int i7 = this.mIconSize;
            if (childAt instanceof TextView) {
                i5 = childAt.getMeasuredWidth();
                i7 = childAt.getMeasuredHeight();
            } else {
                i5 = i7;
            }
            int i8 = (int) (height - (i7 / 2.0f));
            childAt.layout(0, i8, i5, i7 + i8);
        }
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer, android.view.View
    public final void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mIconSize, Integer.MIN_VALUE);
        for (int i3 = 0; i3 < childCount; i3++) {
            measureChild(getChildAt(i3), makeMeasureSpec, i2);
        }
        setMeasuredDimension(ViewGroup.resolveSize(getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_width), i), View.MeasureSpec.getSize(i2));
    }
}
