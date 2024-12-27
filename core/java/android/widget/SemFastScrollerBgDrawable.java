package android.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/* loaded from: classes4.dex */
public class SemFastScrollerBgDrawable extends Drawable {
    private float mValue = 0.0f;
    private Paint mPaint = new Paint(1);

    public SemFastScrollerBgDrawable() {
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setAlpha(8);
        this.mPaint.setColor(-16777216);
    }

    public void setValue(float value) {
        this.mValue = value;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mPaint.setStrokeWidth(this.mValue);
        canvas.drawLine(canvas.getWidth() / 2, this.mPaint.getStrokeWidth() / 2.0f, canvas.getWidth() / 2, canvas.getHeight() - (this.mPaint.getStrokeWidth() / 2.0f), this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    public void setArgb(int Argb) {
        this.mPaint.setColor(Argb);
    }
}
