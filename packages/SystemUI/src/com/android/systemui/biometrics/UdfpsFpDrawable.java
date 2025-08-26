package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Canvas;

/* loaded from: classes.dex */
public final class UdfpsFpDrawable extends UdfpsDrawable {
    public UdfpsFpDrawable(Context context) {
        super(context);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        this.fingerprintDrawable.draw(canvas);
    }
}
