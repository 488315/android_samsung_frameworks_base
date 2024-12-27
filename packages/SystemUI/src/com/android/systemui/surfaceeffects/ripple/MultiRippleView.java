package com.android.systemui.surfaceeffects.ripple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MultiRippleView extends View {
    public final Paint ripplePaint;
    public final ArrayList ripples;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        boolean z;
        if (canvas.isHardwareAccelerated()) {
            loop0: while (true) {
                for (RippleAnimation rippleAnimation : this.ripples) {
                    this.ripplePaint.setShader(rippleAnimation.rippleShader);
                    canvas.drawPaint(this.ripplePaint);
                    z = z || rippleAnimation.animator.isRunning();
                }
            }
            if (z) {
                invalidate();
            }
        }
    }

    public static /* synthetic */ void getRipples$annotations() {
    }
}
