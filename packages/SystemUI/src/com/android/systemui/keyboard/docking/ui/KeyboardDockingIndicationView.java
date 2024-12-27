package com.android.systemui.keyboard.docking.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
