package com.samsung.android.sesl.visualeffect.surfaceeffects.ripple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MultiRippleView extends View {
    public final Paint ripplePaint;
    public final ArrayList ripples;
    public int runningAnimationCount;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultiRippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context.getClass();
        this.ripples = new ArrayList();
        this.ripplePaint = new Paint();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (canvas.isHardwareAccelerated()) {
            if (this.runningAnimationCount != this.ripples.size()) {
                this.runningAnimationCount = this.ripples.size();
            }
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
}
