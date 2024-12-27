package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.animation.view.LaunchableLinearLayout;

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
