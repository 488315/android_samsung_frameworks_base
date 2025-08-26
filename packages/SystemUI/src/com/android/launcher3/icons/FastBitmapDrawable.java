package com.android.launcher3.icons;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.FloatProperty;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* loaded from: classes.dex */
public class FastBitmapDrawable extends Drawable implements Drawable.Callback {
    protected static final float HOVERED_SCALE = 1.1f;
    protected static final float PRESSED_SCALE = 1.1f;
    public int mAlpha;
    public Drawable mBadge;
    public final BitmapInfo mBitmapInfo;
    public ColorFilter mColorFilter;
    public int mCreationFlags;
    protected boolean mIsHovered;
    protected boolean mIsPressed;
    public final Paint mPaint;
    public float mScale;
    protected ObjectAnimator mScaleAnimation;
    public static final Interpolator ACCEL = new AccelerateInterpolator();
    public static final Interpolator DEACCEL = new DecelerateInterpolator();
    public static final Interpolator HOVER_EMPHASIZED_DECELERATE_INTERPOLATOR = new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);
    protected static final FloatProperty<FastBitmapDrawable> SCALE = new FloatProperty("scale") { // from class: com.android.launcher3.icons.FastBitmapDrawable.1
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((FastBitmapDrawable) obj).mScale);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            FastBitmapDrawable fastBitmapDrawable = (FastBitmapDrawable) obj;
            fastBitmapDrawable.mScale = f;
            fastBitmapDrawable.invalidateSelf();
        }
    };

    public FastBitmapDrawable(BitmapInfo bitmapInfo) {
        this.mPaint = new Paint(3);
        this.mCreationFlags = 0;
        this.mScale = 1.0f;
        this.mAlpha = 255;
        this.mBitmapInfo = bitmapInfo;
        setFilterBitmap(true);
    }

    public static ColorFilter getDisabledColorFilter(float f) {
        ColorMatrix colorMatrix = new ColorMatrix();
        ColorMatrix colorMatrix2 = new ColorMatrix();
        colorMatrix2.setSaturation(0.0f);
        float[] array = colorMatrix.getArray();
        array[0] = 0.5f;
        array[6] = 0.5f;
        array[12] = 0.5f;
        float f2 = 127;
        array[4] = f2;
        array[9] = f2;
        array[14] = f2;
        array[18] = f;
        colorMatrix2.preConcat(colorMatrix);
        return new ColorMatrixColorFilter(colorMatrix2);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (this.mScale == 1.0f) {
            drawInternal(canvas, getBounds());
            Drawable drawable = this.mBadge;
            if (drawable != null) {
                drawable.draw(canvas);
                return;
            }
            return;
        }
        int iSave = canvas.save();
        Rect bounds = getBounds();
        float f = this.mScale;
        canvas.scale(f, f, bounds.exactCenterX(), bounds.exactCenterY());
        drawInternal(canvas, bounds);
        Drawable drawable2 = this.mBadge;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        canvas.restoreToCount(iSave);
    }

    public void drawInternal(Canvas canvas, Rect rect) {
        canvas.drawBitmap(this.mBitmapInfo.icon, (Rect) null, rect, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.mAlpha;
    }

    public Drawable getBadge() {
        return this.mBadge;
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        FastBitmapConstantState fastBitmapConstantStateNewConstantState = newConstantState();
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            fastBitmapConstantStateNewConstantState.mBadgeConstantState = drawable.getConstantState();
        }
        fastBitmapConstantStateNewConstantState.mCreationFlags = this.mCreationFlags;
        return fastBitmapConstantStateNewConstantState;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.mBitmapInfo.icon.getHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.mBitmapInfo.icon.getWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getMinimumHeight() {
        return getBounds().height();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getMinimumWidth() {
        return getBounds().width();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        if (drawable == this.mBadge) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        return true;
    }

    public FastBitmapConstantState newConstantState() {
        return new FastBitmapConstantState(this.mBitmapInfo);
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            int iWidth = rect.width();
            float f = BaseIconFactory.LEGACY_ICON_SCALE;
            int i = (int) (iWidth * 0.444f);
            int i2 = rect.right;
            int i3 = rect.bottom;
            drawable.setBounds(i2 - i, i3 - i, i2, i3);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        boolean z;
        int length = iArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            if (iArr[i] == 16842919) {
                z = true;
                break;
            }
            i++;
        }
        if (this.mIsPressed == z && !this.mIsHovered) {
            return false;
        }
        ObjectAnimator objectAnimator = this.mScaleAnimation;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        float f = z ? 1.1f : 1.0f;
        if (this.mScale != f) {
            if (isVisible()) {
                boolean z2 = this.mIsPressed;
                Interpolator interpolator = z != z2 ? z ? ACCEL : DEACCEL : HOVER_EMPHASIZED_DECELERATE_INTERPOLATOR;
                int i2 = z != z2 ? 200 : 300;
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, SCALE, f);
                this.mScaleAnimation = objectAnimatorOfFloat;
                objectAnimatorOfFloat.setDuration(i2);
                this.mScaleAnimation.setInterpolator(interpolator);
                this.mScaleAnimation.start();
            } else {
                this.mScale = f;
                invalidateSelf();
            }
        }
        this.mIsPressed = z;
        this.mIsHovered = false;
        return true;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        if (drawable == this.mBadge) {
            scheduleSelf(runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.mAlpha != i) {
            this.mAlpha = i;
            this.mPaint.setAlpha(i);
            invalidateSelf();
            Drawable drawable = this.mBadge;
            if (drawable != null) {
                drawable.setAlpha(i);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        updateFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        this.mPaint.setAntiAlias(z);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    public void updateFilter() {
        this.mPaint.setColorFilter(this.mColorFilter);
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            drawable.setColorFilter(this.mPaint.getColorFilter());
        }
        invalidateSelf();
    }

    public class FastBitmapConstantState extends Drawable.ConstantState {
        public Drawable.ConstantState mBadgeConstantState;
        public final BitmapInfo mBitmapInfo;
        public int mCreationFlags;

        public FastBitmapConstantState(Bitmap bitmap, int i) {
            this(new BitmapInfo(bitmap, i));
        }

        public FastBitmapDrawable createDrawable() {
            return new FastBitmapDrawable(this.mBitmapInfo);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable() {
            FastBitmapDrawable fastBitmapDrawableCreateDrawable = createDrawable();
            Drawable.ConstantState constantState = this.mBadgeConstantState;
            if (constantState != null) {
                Drawable drawableNewDrawable = constantState.newDrawable();
                Drawable drawable = fastBitmapDrawableCreateDrawable.mBadge;
                if (drawable != null) {
                    drawable.setCallback(null);
                }
                fastBitmapDrawableCreateDrawable.mBadge = drawableNewDrawable;
                if (drawableNewDrawable != null) {
                    drawableNewDrawable.setCallback(fastBitmapDrawableCreateDrawable);
                }
                Rect bounds = fastBitmapDrawableCreateDrawable.getBounds();
                Drawable drawable2 = fastBitmapDrawableCreateDrawable.mBadge;
                if (drawable2 != null) {
                    int iWidth = bounds.width();
                    float f = BaseIconFactory.LEGACY_ICON_SCALE;
                    int i = (int) (iWidth * 0.444f);
                    int i2 = bounds.right;
                    int i3 = bounds.bottom;
                    drawable2.setBounds(i2 - i, i3 - i, i2, i3);
                }
                fastBitmapDrawableCreateDrawable.updateFilter();
            }
            fastBitmapDrawableCreateDrawable.mCreationFlags = this.mCreationFlags;
            return fastBitmapDrawableCreateDrawable;
        }

        public FastBitmapConstantState(BitmapInfo bitmapInfo) {
            this.mCreationFlags = 0;
            this.mBitmapInfo = bitmapInfo;
        }
    }

    public FastBitmapDrawable(Bitmap bitmap) {
        this(new BitmapInfo(bitmap, 0));
    }

    public FastBitmapDrawable(Bitmap bitmap, int i) {
        this(new BitmapInfo(bitmap, i));
    }
}
