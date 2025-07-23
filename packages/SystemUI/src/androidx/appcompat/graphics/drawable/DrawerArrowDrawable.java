package androidx.appcompat.graphics.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.appcompat.R$styleable;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DrawerArrowDrawable extends Drawable {
    public static final float ARROW_HEAD_ANGLE = (float) Math.toRadians(45.0d);
    public final float mArrowHeadLength;
    public final float mArrowShaftLength;
    public final float mBarGap;
    public final float mBarLength;
    public final int mDirection;
    public final float mMaxCutForBarSize;
    public final Paint mPaint;
    public final Path mPath;
    public float mProgress;
    public final int mSize;
    public final boolean mSpin;

    public DrawerArrowDrawable(Context context) {
        Paint paint = new Paint();
        this.mPaint = paint;
        this.mPath = new Path();
        this.mDirection = 2;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R$styleable.DrawerArrowToggle, R.attr.drawerArrowStyle, 2132017500);
        int color = obtainStyledAttributes.getColor(3, 0);
        if (color != paint.getColor()) {
            paint.setColor(color);
            invalidateSelf();
        }
        float dimension = obtainStyledAttributes.getDimension(7, 0.0f);
        if (paint.getStrokeWidth() != dimension) {
            paint.setStrokeWidth(dimension);
            this.mMaxCutForBarSize = (float) (Math.cos(ARROW_HEAD_ANGLE) * (dimension / 2.0f));
            invalidateSelf();
        }
        boolean z = obtainStyledAttributes.getBoolean(6, true);
        if (this.mSpin != z) {
            this.mSpin = z;
            invalidateSelf();
        }
        float round = Math.round(obtainStyledAttributes.getDimension(5, 0.0f));
        if (round != this.mBarGap) {
            this.mBarGap = round;
            invalidateSelf();
        }
        this.mSize = obtainStyledAttributes.getDimensionPixelSize(4, 0);
        this.mBarLength = Math.round(obtainStyledAttributes.getDimension(2, 0.0f));
        this.mArrowHeadLength = Math.round(obtainStyledAttributes.getDimension(0, 0.0f));
        this.mArrowShaftLength = obtainStyledAttributes.getDimension(1, 0.0f);
        obtainStyledAttributes.recycle();
    }

    public static float lerp(float f, float f2, float f3) {
        return DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f2, f, f3, f);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int i = this.mDirection;
        boolean z = false;
        if (i != 0 && (i == 1 || (i == 3 ? getLayoutDirection() == 0 : getLayoutDirection() == 1))) {
            z = true;
        }
        float f = this.mArrowHeadLength;
        float lerp = lerp(this.mBarLength, (float) Math.sqrt(f * f * 2.0f), this.mProgress);
        float lerp2 = lerp(this.mBarLength, this.mArrowShaftLength, this.mProgress);
        float round = Math.round(lerp(0.0f, this.mMaxCutForBarSize, this.mProgress));
        float lerp3 = lerp(0.0f, ARROW_HEAD_ANGLE, this.mProgress);
        float lerp4 = lerp(z ? 0.0f : -180.0f, z ? 180.0f : 0.0f, this.mProgress);
        double d = lerp;
        double d2 = lerp3;
        boolean z2 = z;
        float round2 = Math.round(Math.cos(d2) * d);
        float round3 = Math.round(Math.sin(d2) * d);
        this.mPath.rewind();
        float lerp5 = lerp(this.mPaint.getStrokeWidth() + this.mBarGap, -this.mMaxCutForBarSize, this.mProgress);
        float f2 = (-lerp2) / 2.0f;
        this.mPath.moveTo(f2 + round, 0.0f);
        this.mPath.rLineTo(lerp2 - (round * 2.0f), 0.0f);
        this.mPath.moveTo(f2, lerp5);
        this.mPath.rLineTo(round2, round3);
        this.mPath.moveTo(f2, -lerp5);
        this.mPath.rLineTo(round2, -round3);
        this.mPath.close();
        canvas.save();
        float strokeWidth = this.mPaint.getStrokeWidth();
        float height = bounds.height() - (3.0f * strokeWidth);
        canvas.translate(bounds.centerX(), DrawerArrowDrawable$$ExternalSyntheticOutline0.m(strokeWidth, 1.5f, this.mBarGap, (((int) (height - (r5 * 2.0f))) / 4) * 2));
        if (this.mSpin) {
            canvas.rotate(lerp4 * (z2 ? -1 : 1));
        } else if (z2) {
            canvas.rotate(180.0f);
        }
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.mSize;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.mSize;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        if (i != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }
}
