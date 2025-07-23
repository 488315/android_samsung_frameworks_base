package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RoundedBitmapDrawable extends Drawable {
    public final Bitmap mBitmap;
    public final int mBitmapHeight;
    public final BitmapShader mBitmapShader;
    public final int mBitmapWidth;
    public float mCornerRadius;
    public boolean mIsCircular;
    public final int mTargetDensity;
    public final int mGravity = 119;
    public final Paint mPaint = new Paint(3);
    public final Matrix mShaderMatrix = new Matrix();
    public final Rect mDstRect = new Rect();
    public final RectF mDstRectF = new RectF();
    public boolean mApplyGravity = true;

    public RoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        this.mTargetDensity = 160;
        if (resources != null) {
            this.mTargetDensity = resources.getDisplayMetrics().densityDpi;
        }
        this.mBitmap = bitmap;
        if (bitmap == null) {
            this.mBitmapHeight = -1;
            this.mBitmapWidth = -1;
            this.mBitmapShader = null;
        } else {
            this.mBitmapWidth = bitmap.getScaledWidth(this.mTargetDensity);
            this.mBitmapHeight = bitmap.getScaledHeight(this.mTargetDensity);
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            this.mBitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return;
        }
        updateDstRect();
        if (this.mPaint.getShader() == null) {
            canvas.drawBitmap(bitmap, (Rect) null, this.mDstRect, this.mPaint);
            return;
        }
        RectF rectF = this.mDstRectF;
        float f = this.mCornerRadius;
        canvas.drawRoundRect(rectF, f, f, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        Bitmap bitmap;
        return (this.mGravity != 119 || this.mIsCircular || (bitmap = this.mBitmap) == null || bitmap.hasAlpha() || this.mPaint.getAlpha() < 255 || this.mCornerRadius > 0.05f) ? -3 : -1;
    }

    public void gravityCompatApply(Rect rect, Rect rect2, int i, int i2, int i3) {
        throw new UnsupportedOperationException();
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (this.mIsCircular) {
            this.mCornerRadius = Math.min(this.mBitmapHeight, this.mBitmapWidth) / 2;
        }
        this.mApplyGravity = true;
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

    public final void setCornerRadius(float f) {
        if (this.mCornerRadius == f) {
            return;
        }
        this.mIsCircular = false;
        if (f > 0.05f) {
            this.mPaint.setShader(this.mBitmapShader);
        } else {
            this.mPaint.setShader(null);
        }
        this.mCornerRadius = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setDither(boolean z) {
        this.mPaint.setDither(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    public final void updateDstRect() {
        RoundedBitmapDrawable roundedBitmapDrawable;
        if (this.mApplyGravity) {
            if (this.mIsCircular) {
                int min = Math.min(this.mBitmapWidth, this.mBitmapHeight);
                roundedBitmapDrawable = this;
                roundedBitmapDrawable.gravityCompatApply(getBounds(), this.mDstRect, this.mGravity, min, min);
                int min2 = Math.min(roundedBitmapDrawable.mDstRect.width(), roundedBitmapDrawable.mDstRect.height());
                roundedBitmapDrawable.mDstRect.inset(Math.max(0, (roundedBitmapDrawable.mDstRect.width() - min2) / 2), Math.max(0, (roundedBitmapDrawable.mDstRect.height() - min2) / 2));
                roundedBitmapDrawable.mCornerRadius = min2 * 0.5f;
            } else {
                roundedBitmapDrawable = this;
                roundedBitmapDrawable.gravityCompatApply(roundedBitmapDrawable.getBounds(), roundedBitmapDrawable.mDstRect, roundedBitmapDrawable.mGravity, roundedBitmapDrawable.mBitmapWidth, roundedBitmapDrawable.mBitmapHeight);
            }
            roundedBitmapDrawable.mDstRectF.set(roundedBitmapDrawable.mDstRect);
            if (roundedBitmapDrawable.mBitmapShader != null) {
                Matrix matrix = roundedBitmapDrawable.mShaderMatrix;
                RectF rectF = roundedBitmapDrawable.mDstRectF;
                matrix.setTranslate(rectF.left, rectF.top);
                roundedBitmapDrawable.mShaderMatrix.preScale(roundedBitmapDrawable.mDstRectF.width() / roundedBitmapDrawable.mBitmap.getWidth(), roundedBitmapDrawable.mDstRectF.height() / roundedBitmapDrawable.mBitmap.getHeight());
                roundedBitmapDrawable.mBitmapShader.setLocalMatrix(roundedBitmapDrawable.mShaderMatrix);
                roundedBitmapDrawable.mPaint.setShader(roundedBitmapDrawable.mBitmapShader);
            }
            roundedBitmapDrawable.mApplyGravity = false;
        }
    }
}
