package com.android.launcher3.icons;

import android.graphics.BlurMaskFilter;
import android.graphics.Paint;
import android.graphics.RectF;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ShadowGenerator {
    public final BlurMaskFilter mDefaultBlurMaskFilter;
    public final int mIconSize;
    public final Paint mBlurPaint = new Paint(3);
    public final Paint mDrawPaint = new Paint(3);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
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
