package com.android.systemui.blur;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* loaded from: classes.dex */
public final class SecQSBlurShadowView extends FrameLayout {
    public int color;
    public float dx;
    public float dy;
    public boolean enabled;
    public float height;
    public float left;
    public final Paint paint;
    public float radius;
    public float top;
    public float width;

    public SecQSBlurShadowView(Context context) {
        super(context);
        this.enabled = true;
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

    @Override // android.view.View
    public final void setAlpha(float f) {
        if (this.enabled) {
            super.setAlpha(f);
            this.paint.setShadowLayer(this.radius * f, this.dx, this.dy, this.color);
        } else {
            super.setAlpha(0.0f);
            this.paint.setShadowLayer(this.radius * 0.0f, this.dx, this.dy, this.color);
        }
        invalidate();
    }

    public SecQSBlurShadowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.enabled = true;
        Paint paint = new Paint();
        paint.setColor(0);
        this.paint = paint;
    }
}
