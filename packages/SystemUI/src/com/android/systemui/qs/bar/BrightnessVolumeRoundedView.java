package com.android.systemui.qs.bar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class BrightnessVolumeRoundedView extends FrameLayout {
    public int mHeight;
    public final boolean mIsCornerRound;
    public final Path mPath;
    public final int mRadius;
    public final RectF mRect;

    public BrightnessVolumeRoundedView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHeight = 0;
        this.mIsCornerRound = true;
        this.mPath = new Path();
        this.mRect = new RectF();
        this.mRadius = context.getResources().getDimensionPixelSize(R.dimen.sec_brightness_slider_corner_radius);
        setBackgroundColor(0);
        setClipToOutline(true);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        if (this.mIsCornerRound) {
            if (this.mHeight == 0 || this.mRect.height() == 0.0f) {
                this.mRect.set(0.0f, 0.0f, getWidth(), getHeight());
            }
            this.mPath.reset();
            Path path = this.mPath;
            RectF rectF = this.mRect;
            int i = this.mRadius;
            path.addRoundRect(rectF, i, i, Path.Direction.CCW);
            this.mPath.close();
            canvas.clipPath(this.mPath);
            canvas.restoreToCount(canvas.save());
        }
        super.draw(canvas);
    }

    @Override // android.view.View
    public final void setClipBounds(Rect rect) {
        super.setClipBounds(rect);
        this.mRect.set(rect.left, rect.top, rect.right, rect.bottom);
        this.mHeight = rect.height();
        invalidate();
    }
}
