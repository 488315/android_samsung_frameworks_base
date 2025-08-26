package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.util.wrapper.LottieViewWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class BiometricPromptLottieViewWrapper extends LottieViewWrapper {
    /* JADX WARN: Multi-variable type inference failed */
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
