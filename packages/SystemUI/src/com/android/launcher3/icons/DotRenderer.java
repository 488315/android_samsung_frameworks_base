package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.core.animation.PathInterpolator$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.ShadowGenerator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DotRenderer {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DrawParams {
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
        builder.bounds.set(0.0f, 0.0f, f, f);
        float f5 = max - f4;
        builder.bounds.offsetTo(f5, f5);
        int i3 = max * 2;
        GraphicsUtils$$ExternalSyntheticLambda0 graphicsUtils$$ExternalSyntheticLambda0 = GraphicsUtils.sOnNewBitmapRunnable;
        Picture picture = new Picture();
        Canvas beginRecording = picture.beginRecording(i3, i3);
        Paint paint = new Paint(3);
        int i4 = builder.color;
        paint.setColor(i4);
        float f6 = builder.shadowBlur;
        float f7 = builder.keyShadowDistance;
        int i5 = builder.keyShadowAlpha;
        if (i5 < 0) {
            i5 = 0;
        } else if (i5 > 255) {
            i5 = 255;
        }
        paint.setShadowLayer(f6, 0.0f, f7, i5 << 24);
        RectF rectF = builder.bounds;
        float f8 = builder.radius;
        beginRecording.drawRoundRect(rectF, f8, f8, paint);
        float f9 = builder.shadowBlur;
        int i6 = builder.ambientShadowAlpha;
        paint.setShadowLayer(f9, 0.0f, 0.0f, (i6 >= 0 ? i6 > 255 ? 255 : i6 : 0) << 24);
        RectF rectF2 = builder.bounds;
        float f10 = builder.radius;
        beginRecording.drawRoundRect(rectF2, f10, f10, paint);
        if (Color.alpha(i4) < 255) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            paint.clearShadowLayer();
            paint.setColor(-16777216);
            RectF rectF3 = builder.bounds;
            float f11 = builder.radius;
            beginRecording.drawRoundRect(rectF3, f11, f11, paint);
            paint.setXfermode(null);
            paint.setColor(i4);
            RectF rectF4 = builder.bounds;
            float f12 = builder.radius;
            beginRecording.drawRoundRect(rectF4, f12, f12, paint);
        }
        picture.endRecording();
        Bitmap.createBitmap(picture).getHeight();
        float f13 = i2;
        getPathPoint(path, f13, -1.0f);
        getPathPoint(path, f13, 1.0f);
    }

    public static void getPathPoint(Path path, float f, float f2) {
        float f3 = f / 2.0f;
        float f4 = (f2 * f3) + f3;
        Path m = PathInterpolator$$ExternalSyntheticOutline0.m(f3, f3);
        m.lineTo((f2 * 1.0f) + f4, 0.0f);
        m.lineTo(f4, -1.0f);
        m.close();
        m.op(path, Path.Op.INTERSECT);
        float[] fArr = new float[2];
        new PathMeasure(m, false).getPosTan(0.0f, fArr, null);
        fArr[0] = fArr[0] / f;
        fArr[1] = fArr[1] / f;
    }
}
