package com.android.systemui.surfaceeffects.loadingeffect;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class LoadingEffectView extends View {
    public BlendMode blendMode;
    public final Paint paint;

    public LoadingEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        BlendMode blendMode = BlendMode.SRC_OVER;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        Paint paint;
        if (canvas.isHardwareAccelerated() && (paint = this.paint) != null) {
            canvas.drawPaint(paint);
        }
    }
}
