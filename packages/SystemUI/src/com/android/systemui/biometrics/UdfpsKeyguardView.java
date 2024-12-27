package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UdfpsKeyguardView extends UdfpsAnimationView {
    public final UdfpsFpDrawable fingerprintDrawablePlaceHolder;

    public UdfpsKeyguardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.fingerprintDrawablePlaceHolder = new UdfpsFpDrawable(context);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public final int calculateAlpha() {
        return this.mPauseAuth ? 0 : 255;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public final UdfpsFpDrawable getDrawable() {
        return this.fingerprintDrawablePlaceHolder;
    }
}
