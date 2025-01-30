package com.android.launcher3.icons;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import com.android.launcher3.icons.ShadowGenerator;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class ShadowGenerator$Builder$$ExternalSyntheticLambda0 implements BitmapRenderer {
    public final /* synthetic */ ShadowGenerator.Builder f$0;

    public /* synthetic */ ShadowGenerator$Builder$$ExternalSyntheticLambda0(ShadowGenerator.Builder builder) {
        this.f$0 = builder;
    }

    public final void draw(Canvas canvas) {
        ShadowGenerator.Builder builder = this.f$0;
        builder.getClass();
        Paint paint = new Paint(3);
        int i = builder.color;
        paint.setColor(i);
        float f = builder.shadowBlur;
        float f2 = builder.keyShadowDistance;
        GraphicsUtils$$ExternalSyntheticLambda0 graphicsUtils$$ExternalSyntheticLambda0 = GraphicsUtils.sOnNewBitmapRunnable;
        int i2 = builder.keyShadowAlpha;
        if (i2 < 0) {
            i2 = 0;
        } else if (i2 > 255) {
            i2 = 255;
        }
        paint.setShadowLayer(f, 0.0f, f2, (i2 << 24) | 0);
        RectF rectF = builder.bounds;
        float f3 = builder.radius;
        canvas.drawRoundRect(rectF, f3, f3, paint);
        float f4 = builder.shadowBlur;
        int i3 = builder.ambientShadowAlpha;
        if (i3 < 0) {
            i3 = 0;
        } else if (i3 > 255) {
            i3 = 255;
        }
        paint.setShadowLayer(f4, 0.0f, 0.0f, 0 | (i3 << 24));
        float f5 = builder.radius;
        canvas.drawRoundRect(rectF, f5, f5, paint);
        if (Color.alpha(i) < 255) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            paint.clearShadowLayer();
            paint.setColor(EmergencyPhoneWidget.BG_COLOR);
            float f6 = builder.radius;
            canvas.drawRoundRect(rectF, f6, f6, paint);
            paint.setXfermode(null);
            paint.setColor(i);
            float f7 = builder.radius;
            canvas.drawRoundRect(rectF, f7, f7, paint);
        }
    }
}
