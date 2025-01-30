package com.android.systemui.util.wrapper;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import com.airbnb.lottie.LottieAnimationView;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class LottieViewWrapper extends LottieAnimationView {
    public LottieViewWrapper(Context context) {
        super(context);
    }

    @Override // com.airbnb.lottie.LottieAnimationView, android.view.View
    public final void invalidate() {
        String str = Reflection.getOrCreateKotlinClass(getClass()) + " invalidate";
        if (!Trace.isTagEnabled(4096L)) {
            super.invalidate();
            return;
        }
        Trace.traceBegin(4096L, str);
        try {
            super.invalidate();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    public LottieViewWrapper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LottieViewWrapper(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
