package android.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/* loaded from: classes5.dex */
public class SemFastScrollerBgDrawable extends Drawable {
    private Paint mPaint;
    private float mValue = 0.0f;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public SemFastScrollerBgDrawable() {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setAlpha(8);
        this.mPaint.setColor(-16777216);
    }

    public void setValue(float f) {
        this.mValue = f;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mPaint.setStrokeWidth(this.mValue);
        canvas.drawLine(canvas.getWidth() / 2, this.mPaint.getStrokeWidth() / 2.0f, canvas.getWidth() / 2, canvas.getHeight() - (this.mPaint.getStrokeWidth() / 2.0f), this.mPaint);
    }

    public void setArgb(int i) {
        this.mPaint.setColor(i);
    }
}
