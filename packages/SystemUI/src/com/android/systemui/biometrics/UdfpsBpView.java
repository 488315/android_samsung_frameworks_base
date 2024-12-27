package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;

public final class UdfpsBpView extends UdfpsAnimationView {
    public final UdfpsFpDrawable fingerprintDrawable;

    public UdfpsBpView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.fingerprintDrawable = new UdfpsFpDrawable(context);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public final UdfpsFpDrawable getDrawable() {
        return this.fingerprintDrawable;
    }
}
