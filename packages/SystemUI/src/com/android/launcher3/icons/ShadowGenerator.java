package com.android.launcher3.icons;

import android.graphics.BlurMaskFilter;
import android.graphics.Paint;
import android.graphics.RectF;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ShadowGenerator {
    public final BlurMaskFilter mDefaultBlurMaskFilter;
    public final int mIconSize;
    public final Paint mBlurPaint = new Paint(3);
    public final Paint mDrawPaint = new Paint(3);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Builder {
        public final int color;
        public float keyShadowDistance;
        public float radius;
        public float shadowBlur;
        public final RectF bounds = new RectF();
        public int ambientShadowAlpha = 25;
        public final int keyShadowAlpha = 7;

        public Builder(int i) {
            this.color = i;
        }
    }

    public ShadowGenerator(int i) {
        this.mIconSize = i;
        this.mDefaultBlurMaskFilter = new BlurMaskFilter(i * 0.035f, BlurMaskFilter.Blur.NORMAL);
    }
}
