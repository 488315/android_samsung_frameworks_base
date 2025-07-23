package com.android.systemui.keyboard.docking.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyboardDockingIndicationView extends View {
    public Paint paint;

    public KeyboardDockingIndicationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        Paint paint;
        if (canvas.isHardwareAccelerated() && (paint = this.paint) != null) {
            canvas.drawPaint(paint);
        }
    }
}
