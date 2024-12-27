package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.util.wrapper.LottieViewWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BiometricPromptLottieViewWrapper extends LottieViewWrapper {
    public BiometricPromptLottieViewWrapper(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public BiometricPromptLottieViewWrapper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public /* synthetic */ BiometricPromptLottieViewWrapper(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }
}
