package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.Picture;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface BitmapRenderer {
    static Bitmap createHardwareBitmap(int i, int i2, ShadowGenerator$Builder$$ExternalSyntheticLambda0 shadowGenerator$Builder$$ExternalSyntheticLambda0) {
        GraphicsUtils$$ExternalSyntheticLambda0 graphicsUtils$$ExternalSyntheticLambda0 = GraphicsUtils.sOnNewBitmapRunnable;
        Picture picture = new Picture();
        shadowGenerator$Builder$$ExternalSyntheticLambda0.draw(picture.beginRecording(i, i2));
        picture.endRecording();
        return Bitmap.createBitmap(picture);
    }
}
