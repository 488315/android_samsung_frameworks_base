package com.samsung.android.sesl.visualeffect.surfaceeffects.turbulencenoise;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TurbulenceNoiseView extends View {
    public final Paint paint;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TurbulenceNoiseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TurbulenceNoiseShader turbulenceNoiseShader = new TurbulenceNoiseShader(false, 1, null);
        Paint paint = new Paint();
        paint.setShader(turbulenceNoiseShader);
        this.paint = paint;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (canvas.isHardwareAccelerated()) {
            canvas.drawPaint(this.paint);
        }
    }
}
