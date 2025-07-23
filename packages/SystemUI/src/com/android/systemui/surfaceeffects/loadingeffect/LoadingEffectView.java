package com.android.systemui.surfaceeffects.loadingeffect;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class LoadingEffectView extends View {
    public BlendMode blendMode;
    public Paint paint;

    public LoadingEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.blendMode = BlendMode.SRC_OVER;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        Paint paint;
        if (canvas.isHardwareAccelerated() && (paint = this.paint) != null) {
            canvas.drawPaint(paint);
        }
    }
}
