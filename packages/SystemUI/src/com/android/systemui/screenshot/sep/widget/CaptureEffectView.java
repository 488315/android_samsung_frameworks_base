package com.android.systemui.screenshot.sep.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CaptureEffectView extends View {
    public int mEffectSize;
    public final Paint mPaint;
    public final Path mPath;
    public float mRoundRectX;
    public float mRoundRectY;

    public CaptureEffectView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        updatePath(getWidth(), getHeight());
        canvas.drawPath(this.mPath, this.mPaint);
        super.onDraw(canvas);
    }

    public void setEffectParams(int i, int i2, int i3) {
        this.mEffectSize = i;
        this.mRoundRectX = Math.max(0.0f, i2 - i);
        this.mRoundRectY = Math.max(0.0f, i3 - this.mEffectSize);
    }

    public void updatePath(int i, int i2) {
        int i3 = this.mEffectSize;
        int i4 = i - i3;
        int i5 = i2 - i3;
        this.mPath.reset();
        float f = i3;
        float f2 = i3;
        this.mPath.moveTo(f, f2);
        this.mPath.addRoundRect(new RectF(f, f2, i4, i5), this.mRoundRectX, this.mRoundRectY, Path.Direction.CW);
        this.mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
    }

    public CaptureEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(-16777216);
        this.mPath = new Path();
    }

    public void setDegree(float f) {
    }
}
