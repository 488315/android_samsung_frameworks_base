package com.android.systemui.statusbar.phone.ongoingcall;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.animation.view.LaunchableLinearLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingCallBackgroundContainer extends LaunchableLinearLayout {
    public OngoingCallController$$ExternalSyntheticLambda0 maxHeightFetcher;

    public OngoingCallBackgroundContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int measuredHeight;
        super.onMeasure(i, i2);
        OngoingCallController$$ExternalSyntheticLambda0 ongoingCallController$$ExternalSyntheticLambda0 = this.maxHeightFetcher;
        Integer num = ongoingCallController$$ExternalSyntheticLambda0 != null ? (Integer) ongoingCallController$$ExternalSyntheticLambda0.invoke() : null;
        if (num != null) {
            measuredHeight = getMeasuredHeight();
            int intValue = num.intValue() - 1;
            if (measuredHeight > intValue) {
                measuredHeight = intValue;
            }
        } else {
            measuredHeight = getMeasuredHeight();
        }
        setMeasuredDimension(getMeasuredWidth(), measuredHeight);
    }
}
