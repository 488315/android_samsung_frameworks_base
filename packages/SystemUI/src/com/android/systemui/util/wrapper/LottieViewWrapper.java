package com.android.systemui.util.wrapper;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import com.airbnb.lottie.LottieAnimationView;
import com.android.app.tracing.TraceUtilsKt;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class LottieViewWrapper extends LottieAnimationView {
    public static final int $stable = 0;

    public LottieViewWrapper(Context context) {
        super(context);
    }

    @Override // com.airbnb.lottie.LottieAnimationView, android.view.View
    public void invalidate() {
        String str = Reflection.getOrCreateKotlinClass(getClass()) + " invalidate";
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice(str);
        }
        try {
            super.invalidate();
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public LottieViewWrapper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LottieViewWrapper(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
