package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.animation.view.LaunchableLinearLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingCardBackgroundContainer extends LaunchableLinearLayout {
    public OngoingCardBackgroundContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }
}
