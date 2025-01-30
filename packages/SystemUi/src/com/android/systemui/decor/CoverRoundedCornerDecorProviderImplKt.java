package com.android.systemui.decor;

import android.graphics.Matrix;
import android.widget.ImageView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class CoverRoundedCornerDecorProviderImplKt {
    public static final void access$setRotation(int i, ImageView imageView) {
        Matrix matrix = new Matrix();
        if (i == 1) {
            matrix.postRotate(270.0f);
            matrix.postTranslate(0.0f, imageView.getDrawable().getIntrinsicWidth());
        } else if (i == 2) {
            matrix.postRotate(180.0f);
            matrix.postTranslate(imageView.getDrawable().getIntrinsicWidth(), imageView.getDrawable().getIntrinsicHeight());
        } else if (i == 3) {
            matrix.postRotate(90.0f);
            matrix.postTranslate(imageView.getDrawable().getIntrinsicHeight(), 0.0f);
        }
        imageView.setImageMatrix(matrix);
    }
}
