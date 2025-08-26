package com.android.systemui.util.wrapper;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import com.airbnb.lottie.LottieAnimationView;
import com.android.app.tracing.TraceUtilsKt;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes3.dex */
public class LottieViewWrapper extends LottieAnimationView {
    public static final int $stable = 0;

    public LottieViewWrapper(Context context) {
        super(context);
    }

    @Override // com.airbnb.lottie.LottieAnimationView, android.view.View
    public void invalidate() {
        String str = Reflection.getOrCreateKotlinClass(getClass()) + " invalidate";
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice(str);
        }
        try {
            super.invalidate();
        } finally {
            if (zIsEnabled) {
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
