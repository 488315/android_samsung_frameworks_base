package com.android.systemui.statusbar.phone.ongoingcall;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.animation.view.LaunchableLinearLayout;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OngoingCallBackgroundContainer extends LaunchableLinearLayout {
    public Function0 maxHeightFetcher;

    public OngoingCallBackgroundContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int measuredHeight;
        super.onMeasure(i, i2);
        Function0 function0 = this.maxHeightFetcher;
        Integer num = function0 != null ? (Integer) function0.invoke() : null;
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
