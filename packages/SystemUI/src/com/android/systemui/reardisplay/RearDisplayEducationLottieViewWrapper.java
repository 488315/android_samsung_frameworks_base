package com.android.systemui.reardisplay;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.util.wrapper.LottieViewWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class RearDisplayEducationLottieViewWrapper extends LottieViewWrapper {
    /* JADX WARN: Multi-variable type inference failed */
    public RearDisplayEducationLottieViewWrapper(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public /* synthetic */ RearDisplayEducationLottieViewWrapper(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public RearDisplayEducationLottieViewWrapper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
