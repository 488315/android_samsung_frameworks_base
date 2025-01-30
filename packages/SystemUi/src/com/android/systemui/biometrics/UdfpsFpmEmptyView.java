package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
