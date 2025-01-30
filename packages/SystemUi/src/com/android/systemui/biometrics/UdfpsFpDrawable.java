package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Canvas;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsFpDrawable extends UdfpsDrawable {
    public UdfpsFpDrawable(Context context) {
        super(context);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (this.isDisplayConfigured) {
            return;
        }
        this.fingerprintDrawable.draw(canvas);
    }
}
