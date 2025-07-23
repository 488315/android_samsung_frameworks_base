package com.android.internal.widget;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class DisableImageView extends ImageView {
    public DisableImageView(Context context) {
        this(context, null, 0, 0);
    }

    public DisableImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public DisableImageView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DisableImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] array = colorMatrix.getArray();
        array[0] = 0.5f;
        array[6] = 0.5f;
        array[12] = 0.5f;
        float f = (int) 127.5f;
        array[4] = f;
        array[9] = f;
        array[14] = f;
        ColorMatrix colorMatrix2 = new ColorMatrix();
        colorMatrix2.setSaturation(0.0f);
        colorMatrix2.preConcat(colorMatrix);
        setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
    }
}
