package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
