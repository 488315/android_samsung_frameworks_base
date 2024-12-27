package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class UdfpsFpmEmptyView extends UdfpsAnimationView {
    public final UdfpsFpDrawable fingerprintDrawable;

    public UdfpsFpmEmptyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.fingerprintDrawable = new UdfpsFpDrawable(context);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public final UdfpsFpDrawable getDrawable() {
        return this.fingerprintDrawable;
    }
}
