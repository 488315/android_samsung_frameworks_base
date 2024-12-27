package com.android.systemui.scrim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;
import com.android.internal.graphics.ColorUtils;

public final class ScrimDrawable extends Drawable {
    public ValueAnimator mColorAnimation;
    public ConcaveInfo mConcaveInfo;
    public float mCornerRadius;
    public boolean mCornerRadiusEnabled;
    public int mMainColor;
    public int mMainColorTo;
    public final Paint mPaint;
    public boolean mShouldUseLargeScreenSize;
    public final Path mPath = new Path();
    public final RectF mBoundsRectF = new RectF();
    public int mAlpha = 255;
    public float mBottomEdgeRadius = -1.0f;

    public final class ConcaveInfo {
        public float mPathOverlap;
        public final Path mPath = new Path();
        public final float[] mCornerRadii = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    }

    public ScrimDrawable() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mShouldUseLargeScreenSize = false;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        this.mPaint.setColor(this.mMainColor);
        this.mPaint.setAlpha(this.mAlpha);
        ConcaveInfo concaveInfo = this.mConcaveInfo;
        if (concaveInfo != null) {
            canvas.clipOutPath(concaveInfo.mPath);
            canvas.drawRect(getBounds().left, getBounds().top, getBounds().right, 0 + this.mConcaveInfo.mPathOverlap, this.mPaint);
            return;
        }
        if (this.mCornerRadiusEnabled) {
            float f = this.mCornerRadius;
            if (f > 0.0f) {
                float f2 = this.mBottomEdgeRadius;
                if (f2 == -1.0d) {
                    f2 = f;
                }
                this.mBoundsRectF.set(getBounds());
                if (!this.mShouldUseLargeScreenSize && this.mBottomEdgeRadius != -1.0f) {
                    this.mBoundsRectF.bottom -= f2;
                }
                RectF rectF = this.mBoundsRectF;
                if (rectF.bottom - rectF.top > f2) {
                    this.mPath.reset();
                    Path path = this.mPath;
                    RectF rectF2 = this.mBoundsRectF;
                    path.moveTo(rectF2.right, rectF2.top + f);
                    Path path2 = this.mPath;
                    RectF rectF3 = this.mBoundsRectF;
                    float f3 = rectF3.right;
                    float f4 = rectF3.top;
                    path2.cubicTo(f3, f4 + f, f3, f4, f3 - f, f4);
                    Path path3 = this.mPath;
                    RectF rectF4 = this.mBoundsRectF;
                    path3.lineTo(rectF4.left + f, rectF4.top);
                    Path path4 = this.mPath;
                    RectF rectF5 = this.mBoundsRectF;
                    float f5 = rectF5.left;
                    float f6 = rectF5.top;
                    path4.cubicTo(f5 + f, f6, f5, f6, f5, f6 + f);
                    Path path5 = this.mPath;
                    RectF rectF6 = this.mBoundsRectF;
                    path5.lineTo(rectF6.left, rectF6.bottom - f2);
                    Path path6 = this.mPath;
                    RectF rectF7 = this.mBoundsRectF;
                    float f7 = rectF7.left;
                    float f8 = rectF7.bottom;
                    path6.cubicTo(f7, f8 - f2, f7, f8, f7 + f2, f8);
                    Path path7 = this.mPath;
                    RectF rectF8 = this.mBoundsRectF;
                    path7.lineTo(rectF8.right - f2, rectF8.bottom);
                    Path path8 = this.mPath;
                    RectF rectF9 = this.mBoundsRectF;
                    float f9 = rectF9.right;
                    float f10 = rectF9.bottom;
                    path8.cubicTo(f9 - f2, f10, f9, f10, f9, f10 - f2);
                    this.mPath.close();
                    canvas.drawPath(this.mPath, this.mPaint);
                    return;
                }
                return;
            }
        }
        canvas.drawRect(getBounds().left, getBounds().top, getBounds().right, getBounds().bottom, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public int getMainColor() {
        return this.mMainColor;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        updatePath();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        if (i != this.mAlpha) {
            this.mAlpha = i;
            invalidateSelf();
        }
    }

    public final void setColor(final int i, boolean z) {
        if (i == this.mMainColorTo) {
            return;
        }
        ValueAnimator valueAnimator = this.mColorAnimation;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mColorAnimation.cancel();
        }
        this.mMainColorTo = i;
        if (!z) {
            this.mMainColor = i;
            invalidateSelf();
            return;
        }
        final int i2 = this.mMainColor;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(360L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.scrim.ScrimDrawable$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ScrimDrawable scrimDrawable = ScrimDrawable.this;
                int i3 = i2;
                int i4 = i;
                scrimDrawable.getClass();
                scrimDrawable.mMainColor = ColorUtils.blendARGB(i3, i4, ((Float) valueAnimator2.getAnimatedValue()).floatValue());
                scrimDrawable.invalidateSelf();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.scrim.ScrimDrawable.1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator, boolean z2) {
                ScrimDrawable scrimDrawable = ScrimDrawable.this;
                if (scrimDrawable.mColorAnimation == animator) {
                    scrimDrawable.mColorAnimation = null;
                }
            }
        });
        ofFloat.setInterpolator(new DecelerateInterpolator());
        ofFloat.start();
        this.mColorAnimation = ofFloat;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public final void setXfermode(Xfermode xfermode) {
        this.mPaint.setXfermode(xfermode);
        invalidateSelf();
    }

    public final void updatePath() {
        ConcaveInfo concaveInfo = this.mConcaveInfo;
        if (concaveInfo == null) {
            return;
        }
        concaveInfo.mPath.reset();
        float f = 0;
        ConcaveInfo concaveInfo2 = this.mConcaveInfo;
        concaveInfo2.mPath.addRoundRect(getBounds().left, f, getBounds().right, f + concaveInfo2.mPathOverlap, this.mConcaveInfo.mCornerRadii, Path.Direction.CW);
    }
}
