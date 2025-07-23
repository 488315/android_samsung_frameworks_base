package com.android.systemui.decor;

import android.graphics.Matrix;
import android.widget.ImageView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CoverRoundedCornerDecorProviderImplKt {
    public static final void access$setRotation(ImageView imageView, int i) {
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
