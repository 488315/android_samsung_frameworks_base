package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.util.wrapper.LottieViewWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SideFpsLottieViewWrapper extends LottieViewWrapper {
    /* JADX WARN: Multi-variable type inference failed */
    public SideFpsLottieViewWrapper(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public SideFpsLottieViewWrapper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public /* synthetic */ SideFpsLottieViewWrapper(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }
}
