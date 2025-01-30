package com.android.systemui.statusbar.phone.ongoingcall;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.animation.view.LaunchableLinearLayout;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
