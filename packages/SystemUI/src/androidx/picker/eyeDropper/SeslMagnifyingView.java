package androidx.picker.eyeDropper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslMagnifyingView extends View {
    public final Paint mBitmapPaint;
    public final Paint mCenterSquarePaint;
    public int mColorBorderColor;
    public final Paint mColorBorderPaint;
    public final int mColorBorderStrokeWidth;
    public final Paint mDividersPaint;
    public final Paint mInnerBorderPaint;
    public final int mInnerBorderStrokeWidth;
    public Bitmap mScreenShotBitmap;
    public float mTouchPosX;
    public float mTouchPosY;

    public SeslMagnifyingView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        super.onDraw(canvas);
        if (this.mScreenShotBitmap == null) {
            return;
        }
        float width = getWidth();
        float height = getHeight();
        float min = Math.min(width, height) / 2.0f;
        float f = this.mTouchPosX;
        float f2 = (width / 3.0f) / 2.0f;
        float f3 = this.mTouchPosY;
        float f4 = (height / 3.0f) / 2.0f;
        RectF rectF = new RectF(f - f2, f3 - f4, f + f2, f3 + f4);
        RectF rectF2 = new RectF(0.0f, 0.0f, width, height);
        Rect rect = new Rect();
        rectF.round(rect);
        Rect rect2 = new Rect();
        rectF2.round(rect2);
        canvas2.save();
        Path path = new Path();
        float f5 = width / 2.0f;
        float f6 = height / 2.0f;
        path.addCircle(f5, f6, min, Path.Direction.CW);
        canvas2.clipPath(path);
        canvas2.drawBitmap(this.mScreenShotBitmap, rect, rect2, this.mBitmapPaint);
        float f7 = 15;
        float f8 = width / f7;
        float f9 = height / f7;
        int i = 0;
        while (i < 15) {
            float f10 = i * f8;
            canvas2.drawLine(f10, 0.0f, f10, height, this.mDividersPaint);
            i++;
            canvas2 = canvas;
        }
        for (int i2 = 0; i2 < 15; i2++) {
            float f11 = i2 * f9;
            canvas.drawLine(0.0f, f11, width, f11, this.mDividersPaint);
        }
        float f12 = (width / 15.0f) / 2.0f;
        float f13 = (height / 15.0f) / 2.0f;
        float f14 = this.mInnerBorderStrokeWidth;
        canvas.drawRoundRect(f5 - f12, f6 - f13, f5 + f12, f13 + f6, f14, f14, this.mCenterSquarePaint);
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, min - ((this.mInnerBorderStrokeWidth / 2.0f) + this.mColorBorderStrokeWidth), this.mInnerBorderPaint);
        this.mColorBorderPaint.setColor(this.mColorBorderColor);
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, min - (this.mColorBorderStrokeWidth / 2.0f), this.mColorBorderPaint);
        canvas.restore();
    }

    public SeslMagnifyingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslMagnifyingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Paint paint = new Paint();
        this.mBitmapPaint = paint;
        Paint paint2 = new Paint();
        this.mColorBorderPaint = paint2;
        Paint paint3 = new Paint();
        this.mInnerBorderPaint = paint3;
        Paint paint4 = new Paint();
        this.mDividersPaint = paint4;
        Paint paint5 = new Paint();
        this.mCenterSquarePaint = paint5;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sesl_eyedropper_color_border_stroke_width);
        this.mColorBorderStrokeWidth = dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.sesl_eyedropper_inner_border_stroke_width);
        this.mInnerBorderStrokeWidth = dimensionPixelSize2;
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.sesl_eyedropper_dividers_stroke_width);
        int dimensionPixelSize4 = getResources().getDimensionPixelSize(R.dimen.sesl_eyedropper_center_square_stroke_width);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Paint.Style style = Paint.Style.STROKE;
        paint2.setStyle(style);
        paint2.setAntiAlias(true);
        paint2.setStrokeWidth(dimensionPixelSize);
        paint3.setStyle(style);
        paint3.setColor(getResources().getColor(R.color.sesl_color_picker_cursor_stroke_color));
        paint3.setAntiAlias(true);
        paint3.setStrokeWidth(dimensionPixelSize2);
        paint4.setStyle(style);
        paint4.setColor(getResources().getColor(R.color.sesl_color_picker_swatch_cursor_color));
        paint4.setAntiAlias(true);
        paint4.setStrokeWidth(dimensionPixelSize3);
        paint5.setStyle(style);
        paint5.setColor(getResources().getColor(R.color.sesl_color_picker_cursor_stroke_color));
        paint5.setAntiAlias(true);
        paint5.setStrokeWidth(dimensionPixelSize4);
    }
}
