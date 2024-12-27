package com.android.systemui.screenshot.sep.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CaptureEffectViewB5 extends CaptureEffectView {
    public final PorterDuffXfermode MODE_ADD;
    public final PorterDuffXfermode MODE_DST_IN;
    public final Paint mBitmapPaint;
    public Bitmap mCoverShape;
    public Bitmap mCoverShapeBackground;
    public int mEffectSize;
    public Bitmap mMaskBgBitmap;
    public Bitmap mMaskBitmap;
    public final Paint mPaint;
    public final Path mPath;
    public float mRoundRectX;
    public float mRoundRectY;
    public final Matrix mScreenMatrix;

    public CaptureEffectViewB5(Context context) {
        this(context, null);
    }

    public final Bitmap getBitmapFromVectorDrawable(int i) {
        Drawable drawable = getResources().getDrawable(i, null);
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    @Override // com.android.systemui.screenshot.sep.widget.CaptureEffectView, android.view.View
    public final void onDraw(Canvas canvas) {
        updatePath(getWidth(), getHeight());
        canvas.drawPath(this.mPath, this.mPaint);
        this.mBitmapPaint.setXfermode(this.MODE_DST_IN);
        Bitmap bitmap = this.mMaskBitmap;
        int i = this.mEffectSize;
        canvas.drawBitmap(bitmap, i, i, this.mBitmapPaint);
        this.mBitmapPaint.setXfermode(this.MODE_ADD);
        Bitmap bitmap2 = this.mMaskBgBitmap;
        int i2 = this.mEffectSize;
        canvas.drawBitmap(bitmap2, i2, i2, this.mBitmapPaint);
    }

    @Override // com.android.systemui.screenshot.sep.widget.CaptureEffectView
    public final void setDegree(float f) {
        this.mScreenMatrix.setRotate(f);
        Bitmap bitmap = this.mCoverShape;
        this.mCoverShape = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.mCoverShape.getHeight(), this.mScreenMatrix, true);
        Bitmap bitmap2 = this.mCoverShapeBackground;
        this.mCoverShapeBackground = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), this.mCoverShapeBackground.getHeight(), this.mScreenMatrix, true);
    }

    @Override // com.android.systemui.screenshot.sep.widget.CaptureEffectView
    public final void setEffectParams(int i, int i2, int i3) {
        this.mEffectSize = i;
        float f = i / 2.0f;
        this.mRoundRectX = i2 - f;
        this.mRoundRectY = i3 - f;
    }

    @Override // com.android.systemui.screenshot.sep.widget.CaptureEffectView
    public final void updatePath(int i, int i2) {
        int i3 = this.mEffectSize;
        int i4 = i - i3;
        int i5 = i2 - i3;
        this.mPath.reset();
        float f = i3;
        float f2 = i3;
        this.mPath.moveTo(f, f2);
        this.mPath.addRoundRect(new RectF(f, f2, i4, i5), this.mRoundRectX, this.mRoundRectY, Path.Direction.CW);
        this.mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        int i6 = this.mEffectSize;
        int i7 = i - (i6 * 2);
        int i8 = i2 - (i6 * 2);
        this.mMaskBitmap = Bitmap.createScaledBitmap(this.mCoverShape, i7, i8, true);
        this.mMaskBgBitmap = Bitmap.createScaledBitmap(this.mCoverShapeBackground, i7, i8, true);
    }

    public CaptureEffectViewB5(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCoverShape = getBitmapFromVectorDrawable(R.drawable.b5_cover_screen_shape);
        this.mCoverShapeBackground = getBitmapFromVectorDrawable(R.drawable.b5_cover_screen_shape_background);
        this.MODE_DST_IN = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        this.MODE_ADD = new PorterDuffXfermode(PorterDuff.Mode.ADD);
        this.mScreenMatrix = new Matrix();
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(-16777216);
        Paint paint2 = new Paint(1);
        this.mBitmapPaint = paint2;
        paint2.setColor(-16777216);
        this.mPath = new Path();
    }
}
