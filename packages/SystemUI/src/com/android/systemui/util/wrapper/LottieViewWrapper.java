package com.android.systemui.util.wrapper;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import com.airbnb.lottie.LottieAnimationView;
import com.android.app.tracing.TraceUtilsKt;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
