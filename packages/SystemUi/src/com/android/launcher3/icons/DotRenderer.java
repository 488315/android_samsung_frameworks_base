package com.android.launcher3.icons;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import com.android.launcher3.icons.ShadowGenerator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DotRenderer {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DrawParams {
        public DrawParams() {
            new Rect();
        }
    }

    public DotRenderer(int i, Path path, int i2) {
        new Paint(3);
        int round = Math.round(i * 0.228f);
        round = round <= 0 ? 1 : round;
        ShadowGenerator.Builder builder = new ShadowGenerator.Builder(0);
        builder.ambientShadowAlpha = 88;
        float f = round;
        float f2 = f * 1.0f;
        float f3 = f2 / 24.0f;
        builder.shadowBlur = f3;
        builder.keyShadowDistance = f2 / 16.0f;
        float f4 = f / 2.0f;
        builder.radius = f4;
        int max = Math.max(Math.round(f3 + f4), Math.round(builder.radius + builder.shadowBlur + builder.keyShadowDistance));
        RectF rectF = builder.bounds;
        rectF.set(0.0f, 0.0f, f, f);
        float f5 = max - f4;
        rectF.offsetTo(f5, f5);
        int i3 = max * 2;
        BitmapRenderer.createHardwareBitmap(i3, i3, new ShadowGenerator$Builder$$ExternalSyntheticLambda0(builder)).getHeight();
        float f6 = i2;
        getPathPoint(path, f6, -1.0f);
        getPathPoint(path, f6, 1.0f);
    }

    public static void getPathPoint(Path path, float f, float f2) {
        float f3 = f / 2.0f;
        float f4 = (f2 * f3) + f3;
        Path path2 = new Path();
        path2.moveTo(f3, f3);
        path2.lineTo((f2 * 1.0f) + f4, 0.0f);
        path2.lineTo(f4, -1.0f);
        path2.close();
        path2.op(path, Path.Op.INTERSECT);
        float[] fArr = new float[2];
        new PathMeasure(path2, false).getPosTan(0.0f, fArr, null);
        fArr[0] = fArr[0] / f;
        fArr[1] = fArr[1] / f;
    }
}
