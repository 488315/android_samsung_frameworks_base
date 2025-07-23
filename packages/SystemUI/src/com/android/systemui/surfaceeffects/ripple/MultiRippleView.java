package com.android.systemui.surfaceeffects.ripple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiRippleView extends View {
    public final Paint ripplePaint;
    public final ArrayList ripples;

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

    public MultiRippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ripples = new ArrayList();
        this.ripplePaint = new Paint();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (canvas.isHardwareAccelerated()) {
            ArrayList arrayList = this.ripples;
            int size = arrayList.size();
            boolean z = false;
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                RippleAnimation rippleAnimation = (RippleAnimation) obj;
                this.ripplePaint.setShader(rippleAnimation.rippleShader);
                canvas.drawPaint(this.ripplePaint);
                z = z || rippleAnimation.animator.isRunning();
            }
            if (z) {
                invalidate();
            }
        }
    }

    public static /* synthetic */ void getRipples$annotations() {
    }
}
