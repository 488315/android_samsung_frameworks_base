package com.android.systemui.keyboard.docking.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public final class KeyboardDockingIndicationView extends View {
    public final Paint paint;

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
