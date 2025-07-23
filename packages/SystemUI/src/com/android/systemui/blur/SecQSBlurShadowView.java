package com.android.systemui.blur;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecQSBlurShadowView extends FrameLayout {
    public float height;
    public float left;
    public final Paint paint;
    public float top;
    public float width;

    public SecQSBlurShadowView(Context context) {
        super(context);
        Paint paint = new Paint();
        paint.setColor(0);
        this.paint = paint;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        float f = this.left;
        float f2 = this.top;
        canvas.drawRoundRect(f, f2, f + this.width, f2 + this.height, getContext().getResources().getDimensionPixelSize(R.dimen.qs_pop_over_corner_radius), getContext().getResources().getDimensionPixelSize(R.dimen.qs_pop_over_corner_radius), this.paint);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setBackground(getContext().getDrawable(R.drawable.qs_blur_drawable));
    }

    public SecQSBlurShadowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        paint.setColor(0);
        this.paint = paint;
    }
}
