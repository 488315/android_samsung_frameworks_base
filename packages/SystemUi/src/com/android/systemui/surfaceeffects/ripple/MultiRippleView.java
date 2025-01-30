package com.android.systemui.surfaceeffects.ripple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultiRippleView extends View {
    public final Paint ripplePaint;
    public final ArrayList ripples;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
