package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.util.wrapper.LottieViewWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BiometricPromptLottieViewWrapper extends LottieViewWrapper {
    public BiometricPromptLottieViewWrapper(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public /* synthetic */ BiometricPromptLottieViewWrapper(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public BiometricPromptLottieViewWrapper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
