package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Canvas;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
