package com.android.systemui.statusbar.phone.ongoingcall;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.animation.view.LaunchableLinearLayout;

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
            int iIntValue = num.intValue() - 1;
            if (measuredHeight > iIntValue) {
                measuredHeight = iIntValue;
            }
        } else {
            measuredHeight = getMeasuredHeight();
        }
        setMeasuredDimension(getMeasuredWidth(), measuredHeight);
    }
}
