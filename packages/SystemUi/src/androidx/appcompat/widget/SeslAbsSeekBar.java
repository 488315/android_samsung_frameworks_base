package androidx.appcompat.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AbsSeekBar;
import androidx.appcompat.R$styleable;
import androidx.appcompat.animation.SeslAnimationUtils;
import androidx.appcompat.graphics.drawable.DrawableWrapperCompat;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.widget.SeslProgressBar;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.view.SeslHapticFeedbackConstantsReflector;
import androidx.reflect.view.SeslViewReflector;
import com.android.systemui.R;
import com.samsung.android.nexus.video.VideoPlayer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SeslAbsSeekBar extends SeslProgressBar {
    public int mCurrentProgressLevel;
    public ColorStateList mDefaultActivatedProgressColor;
    public ColorStateList mDefaultActivatedThumbColor;
    public final ColorStateList mDefaultNormalProgressColor;
    public final ColorStateList mDefaultSecondaryProgressColor;
    public final float mDisabledAlpha;
    public Drawable mDivider;
    public final List mGestureExclusionRects;
    public boolean mHasThumbTint;
    public final boolean mHasThumbTintMode;
    public final boolean mHasTickMarkTint;
    public final boolean mHasTickMarkTintMode;
    public boolean mIsDragging;
    public boolean mIsDraggingForSliding;
    public boolean mIsFirstSetProgress;
    public final boolean mIsLightTheme;
    public boolean mIsSeamless;
    public boolean mIsSetModeCalled;
    public final boolean mIsUserSeekable;
    public int mKeyProgressIncrement;
    public float mLevelDrawPadding;
    public final int mModeExpandThumbRadius;
    public final int mModeExpandTrackMaxWidth;
    public final int mModeExpandTrackMinWidth;
    public AnimatorSet mMuteAnimationSet;
    public final ColorStateList mOverlapActivatedProgressColor;
    public final int mScaledTouchSlop;
    public Drawable mSplitProgress;
    public final boolean mSplitTrack;
    public final Rect mTempRect;
    public Drawable mThumb;
    public int mThumbOffset;
    public int mThumbPosX;
    public final int mThumbRadius;
    public final Rect mThumbRect;
    public ColorStateList mThumbTintList;
    public final PorterDuff.Mode mThumbTintMode;
    public Drawable mTickMark;
    public final ColorStateList mTickMarkTintList;
    public final PorterDuff.Mode mTickMarkTintMode;
    public float mTouchDownX;
    public float mTouchDownY;
    public final int mTrackMaxWidth;
    public final int mTrackMinWidth;
    public List mUserGestureExclusionRects;
    public ValueAnimator mValueAnimator;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SliderDrawable extends Drawable {
        public int mAlpha;
        public int mColor;
        public ColorStateList mColorStateList;
        public boolean mIsStateChanged;
        public final boolean mIsVertical;
        public final Paint mPaint;
        public ValueAnimator mPressedAnimator;
        public float mRadius;
        public ValueAnimator mReleasedAnimator;
        public final float mSliderMaxWidth;
        public final float mSliderMinWidth;
        public final SliderState mState;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SliderState extends Drawable.ConstantState {
            private SliderState() {
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public final int getChangingConfigurations() {
                return 0;
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public final Drawable newDrawable() {
                return SliderDrawable.this;
            }
        }

        public SliderDrawable(SeslAbsSeekBar seslAbsSeekBar, float f, float f2, ColorStateList colorStateList) {
            this(f, f2, colorStateList, false);
        }

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            int alpha = this.mPaint.getAlpha();
            Paint paint = this.mPaint;
            int i = this.mAlpha;
            paint.setAlpha(((i + (i >>> 7)) * alpha) >>> 8);
            canvas.save();
            if (this.mIsVertical) {
                float width = (SeslAbsSeekBar.this.getWidth() - SeslAbsSeekBar.this.getPaddingLeft()) - SeslAbsSeekBar.this.getPaddingRight();
                float height = (SeslAbsSeekBar.this.getHeight() - SeslAbsSeekBar.this.getPaddingTop()) - SeslAbsSeekBar.this.getPaddingBottom();
                float f = this.mRadius;
                float f2 = width / 2.0f;
                canvas.drawLine(f2, height - f, f2, f, this.mPaint);
            } else {
                float width2 = (SeslAbsSeekBar.this.getWidth() - SeslAbsSeekBar.this.getPaddingLeft()) - SeslAbsSeekBar.this.getPaddingRight();
                float f3 = this.mRadius;
                canvas.drawLine(f3, SeslAbsSeekBar.this.getHeight() / 2.0f, width2 - f3, SeslAbsSeekBar.this.getHeight() / 2.0f, this.mPaint);
            }
            canvas.restore();
            this.mPaint.setAlpha(alpha);
        }

        @Override // android.graphics.drawable.Drawable
        public final Drawable.ConstantState getConstantState() {
            return this.mState;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicHeight() {
            return (int) this.mSliderMaxWidth;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicWidth() {
            return (int) this.mSliderMaxWidth;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            Paint paint = this.mPaint;
            if (paint.getXfermode() != null) {
                return -3;
            }
            int alpha = paint.getAlpha();
            if (alpha == 0) {
                return -2;
            }
            return alpha == 255 ? -1 : -3;
        }

        @Override // android.graphics.drawable.Drawable
        public final boolean isStateful() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable
        public final boolean onStateChange(int[] iArr) {
            boolean onStateChange = super.onStateChange(iArr);
            int colorForState = this.mColorStateList.getColorForState(iArr, this.mColor);
            if (this.mColor != colorForState) {
                this.mColor = colorForState;
                this.mPaint.setColor(colorForState);
                invalidateSelf();
            }
            boolean z = false;
            boolean z2 = false;
            for (int i : iArr) {
                if (i == 16842910) {
                    z = true;
                } else if (i == 16842919) {
                    z2 = true;
                }
            }
            boolean z3 = z && z2;
            if (this.mIsStateChanged != z3) {
                if (z3) {
                    if (!this.mPressedAnimator.isRunning()) {
                        if (this.mReleasedAnimator.isRunning()) {
                            this.mReleasedAnimator.cancel();
                        }
                        this.mPressedAnimator.setFloatValues(this.mSliderMinWidth, this.mSliderMaxWidth);
                        this.mPressedAnimator.start();
                    }
                } else if (!this.mReleasedAnimator.isRunning()) {
                    if (this.mPressedAnimator.isRunning()) {
                        this.mPressedAnimator.cancel();
                    }
                    this.mReleasedAnimator.setFloatValues(this.mSliderMaxWidth, this.mSliderMinWidth);
                    this.mReleasedAnimator.start();
                }
                this.mIsStateChanged = z3;
            }
            return onStateChange;
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
            this.mAlpha = i;
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {
            this.mPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public final void setTintList(ColorStateList colorStateList) {
            super.setTintList(colorStateList);
            if (colorStateList != null) {
                this.mColorStateList = colorStateList;
                int defaultColor = colorStateList.getDefaultColor();
                this.mColor = defaultColor;
                this.mPaint.setColor(defaultColor);
                invalidateSelf();
            }
        }

        public SliderDrawable(float f, float f2, ColorStateList colorStateList, boolean z) {
            Paint paint = new Paint();
            this.mPaint = paint;
            this.mIsStateChanged = false;
            this.mAlpha = 255;
            this.mState = new SliderState();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            this.mColorStateList = colorStateList;
            int defaultColor = colorStateList.getDefaultColor();
            this.mColor = defaultColor;
            paint.setColor(defaultColor);
            paint.setStrokeWidth(f);
            this.mSliderMinWidth = f;
            this.mSliderMaxWidth = f2;
            this.mRadius = f / 2.0f;
            this.mIsVertical = z;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
            this.mPressedAnimator = ofFloat;
            ofFloat.setDuration(250L);
            ValueAnimator valueAnimator = this.mPressedAnimator;
            Interpolator interpolator = SeslAnimationUtils.SINE_IN_OUT_80;
            valueAnimator.setInterpolator(interpolator);
            this.mPressedAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.widget.SeslAbsSeekBar.SliderDrawable.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    SliderDrawable sliderDrawable = SliderDrawable.this;
                    sliderDrawable.mPaint.setStrokeWidth(floatValue);
                    sliderDrawable.mRadius = floatValue / 2.0f;
                    sliderDrawable.invalidateSelf();
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(f2, f);
            this.mReleasedAnimator = ofFloat2;
            ofFloat2.setDuration(250L);
            this.mReleasedAnimator.setInterpolator(interpolator);
            this.mReleasedAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.widget.SeslAbsSeekBar.SliderDrawable.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    SliderDrawable sliderDrawable = SliderDrawable.this;
                    sliderDrawable.mPaint.setStrokeWidth(floatValue);
                    sliderDrawable.mRadius = floatValue / 2.0f;
                    sliderDrawable.invalidateSelf();
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ThumbDrawable extends Drawable {
        public int mAlpha;
        public int mColor;
        public ColorStateList mColorStateList;
        public boolean mIsStateChanged;
        public final boolean mIsVertical;
        public final Paint mPaint;
        public final Paint mPaintFill;
        public final int mRadius;
        public int mRadiusForAni;
        public ValueAnimator mThumbPressed;
        public ValueAnimator mThumbReleased;

        public ThumbDrawable(int i, ColorStateList colorStateList, boolean z) {
            Paint paint = new Paint(1);
            this.mPaint = paint;
            Paint paint2 = new Paint(1);
            this.mPaintFill = paint2;
            this.mIsStateChanged = false;
            this.mAlpha = 255;
            this.mIsVertical = false;
            this.mRadiusForAni = i;
            this.mRadius = i;
            this.mColorStateList = colorStateList;
            this.mColor = colorStateList.getDefaultColor();
            paint.setStyle(Paint.Style.STROKE);
            paint2.setStyle(Paint.Style.FILL);
            paint.setColor(this.mColor);
            paint.setStrokeWidth(SeslAbsSeekBar.this.getContext().getResources().getDimension(R.dimen.sesl_seekbar_thumb_stroke));
            paint2.setColor(SeslAbsSeekBar.this.getContext().getResources().getColor(R.color.sesl_thumb_control_fill_color_activated));
            this.mIsVertical = z;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(i, 0.0f);
            this.mThumbPressed = ofFloat;
            ofFloat.setDuration(100L);
            this.mThumbPressed.setInterpolator(new LinearInterpolator());
            this.mThumbPressed.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.widget.SeslAbsSeekBar.ThumbDrawable.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    ThumbDrawable thumbDrawable = ThumbDrawable.this;
                    thumbDrawable.mRadiusForAni = (int) floatValue;
                    thumbDrawable.invalidateSelf();
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, i);
            this.mThumbReleased = ofFloat2;
            ofFloat2.setDuration(300L);
            this.mThumbReleased.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_90);
            this.mThumbReleased.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.widget.SeslAbsSeekBar.ThumbDrawable.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    ThumbDrawable thumbDrawable = ThumbDrawable.this;
                    thumbDrawable.mRadiusForAni = (int) floatValue;
                    thumbDrawable.invalidateSelf();
                }
            });
        }

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            int alpha = this.mPaint.getAlpha();
            Paint paint = this.mPaint;
            int i = this.mAlpha;
            paint.setAlpha(((i + (i >>> 7)) * alpha) >>> 8);
            Paint paint2 = this.mPaintFill;
            int i2 = this.mAlpha;
            paint2.setAlpha(((i2 + (i2 >>> 7)) * alpha) >>> 8);
            canvas.save();
            if (this.mIsVertical) {
                float width = ((SeslAbsSeekBar.this.getWidth() - SeslAbsSeekBar.this.getPaddingLeft()) - SeslAbsSeekBar.this.getPaddingRight()) / 2.0f;
                SeslAbsSeekBar seslAbsSeekBar = SeslAbsSeekBar.this;
                canvas.drawCircle(width, seslAbsSeekBar.mThumbPosX - seslAbsSeekBar.getPaddingLeft(), this.mRadiusForAni, this.mPaintFill);
                SeslAbsSeekBar seslAbsSeekBar2 = SeslAbsSeekBar.this;
                canvas.drawCircle(width, seslAbsSeekBar2.mThumbPosX - seslAbsSeekBar2.getPaddingLeft(), this.mRadiusForAni, this.mPaint);
            } else {
                SeslAbsSeekBar seslAbsSeekBar3 = SeslAbsSeekBar.this;
                canvas.drawCircle(seslAbsSeekBar3.mThumbPosX, seslAbsSeekBar3.getHeight() / 2.0f, this.mRadiusForAni, this.mPaintFill);
                SeslAbsSeekBar seslAbsSeekBar4 = SeslAbsSeekBar.this;
                canvas.drawCircle(seslAbsSeekBar4.mThumbPosX, seslAbsSeekBar4.getHeight() / 2.0f, this.mRadiusForAni, this.mPaint);
            }
            canvas.restore();
            this.mPaint.setAlpha(alpha);
            this.mPaintFill.setAlpha(alpha);
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicHeight() {
            return this.mRadius * 2;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicWidth() {
            return this.mRadius * 2;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            Paint paint = this.mPaint;
            if (paint.getXfermode() != null) {
                return -3;
            }
            int alpha = paint.getAlpha();
            if (alpha == 0) {
                return -2;
            }
            return alpha == 255 ? -1 : -3;
        }

        @Override // android.graphics.drawable.Drawable
        public final boolean isStateful() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable
        public final boolean onStateChange(int[] iArr) {
            boolean onStateChange = super.onStateChange(iArr);
            int colorForState = this.mColorStateList.getColorForState(iArr, this.mColor);
            if (this.mColor != colorForState) {
                this.mColor = colorForState;
                this.mPaint.setColor(colorForState);
                invalidateSelf();
            }
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            for (int i : iArr) {
                if (i == 16842910) {
                    z2 = true;
                } else if (i == 16842919) {
                    z3 = true;
                }
            }
            if (z2 && z3) {
                z = true;
            }
            if (this.mIsStateChanged != z) {
                if (z) {
                    if (!this.mThumbPressed.isRunning()) {
                        if (this.mThumbReleased.isRunning()) {
                            this.mThumbReleased.cancel();
                        }
                        this.mThumbPressed.start();
                    }
                } else if (!this.mThumbReleased.isRunning()) {
                    if (this.mThumbPressed.isRunning()) {
                        this.mThumbPressed.cancel();
                    }
                    this.mThumbReleased.start();
                }
                this.mIsStateChanged = z;
            }
            return onStateChange;
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
            this.mAlpha = i;
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {
            this.mPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public final void setTintList(ColorStateList colorStateList) {
            super.setTintList(colorStateList);
            if (colorStateList != null) {
                this.mColorStateList = colorStateList;
                int colorForState = colorStateList.getColorForState(SeslAbsSeekBar.this.getDrawableState(), this.mColor);
                this.mColor = colorForState;
                this.mPaint.setColor(colorForState);
                invalidateSelf();
            }
        }
    }

    public SeslAbsSeekBar(Context context) {
        super(context);
        this.mTempRect = new Rect();
        this.mThumbTintList = null;
        this.mThumbTintMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbTintMode = false;
        this.mTickMarkTintList = null;
        this.mTickMarkTintMode = null;
        this.mHasTickMarkTint = false;
        this.mHasTickMarkTintMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
        this.mUserGestureExclusionRects = Collections.emptyList();
        this.mGestureExclusionRects = new ArrayList();
        this.mThumbRect = new Rect();
        this.mIsFirstSetProgress = false;
        this.mIsDraggingForSliding = false;
        this.mIsSetModeCalled = false;
        this.mIsSeamless = false;
        this.mLevelDrawPadding = 0.0f;
    }

    private static ColorStateList colorToColorStateList(int i) {
        return new ColorStateList(new int[][]{new int[0]}, new int[]{i});
    }

    public final void applyThumbTint() {
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            if (this.mHasThumbTint || this.mHasThumbTintMode) {
                Drawable mutate = drawable.mutate();
                this.mThumb = mutate;
                if (this.mHasThumbTint) {
                    mutate.setTintList(this.mThumbTintList);
                }
                if (this.mHasThumbTintMode) {
                    this.mThumb.setTintMode(this.mThumbTintMode);
                }
                if (this.mThumb.isStateful()) {
                    this.mThumb.setState(getDrawableState());
                }
            }
        }
    }

    public final void applyTickMarkTint() {
        Drawable drawable = this.mTickMark;
        if (drawable != null) {
            if (this.mHasTickMarkTint || this.mHasTickMarkTintMode) {
                Drawable mutate = drawable.mutate();
                this.mTickMark = mutate;
                if (this.mHasTickMarkTint) {
                    mutate.setTintList(this.mTickMarkTintList);
                }
                if (this.mHasTickMarkTintMode) {
                    this.mTickMark.setTintMode(this.mTickMarkTintMode);
                }
                if (this.mTickMark.isStateful()) {
                    this.mTickMark.setState(getDrawableState());
                }
            }
        }
    }

    public final void drawTickMarks(Canvas canvas) {
        if (this.mTickMark != null) {
            int max = getMax() - getMin();
            if (max > 1) {
                int intrinsicWidth = this.mTickMark.getIntrinsicWidth();
                int intrinsicHeight = this.mTickMark.getIntrinsicHeight();
                int i = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                int i2 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
                this.mTickMark.setBounds(-i, -i2, i, i2);
                float width = (((getWidth() - getPaddingLeft()) - getPaddingRight()) - (this.mLevelDrawPadding * 2.0f)) / max;
                int save = canvas.save();
                canvas.translate(this.mLevelDrawPadding + getPaddingLeft(), getHeight() / 2.0f);
                for (int i3 = 0; i3 <= max; i3++) {
                    this.mTickMark.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(save);
            }
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final void drawTrack(Canvas canvas) {
        Drawable drawable = this.mThumb;
        if (drawable == null || !this.mSplitTrack) {
            super.drawTrack(canvas);
            drawTickMarks(canvas);
            return;
        }
        Rect opticalBounds = DrawableUtils.getOpticalBounds(drawable);
        Rect rect = this.mTempRect;
        drawable.copyBounds(rect);
        rect.offset(getPaddingLeft() - this.mThumbOffset, getPaddingTop());
        rect.left += opticalBounds.left;
        rect.right -= opticalBounds.right;
        int save = canvas.save();
        canvas.clipRect(rect, Region.Op.DIFFERENCE);
        super.drawTrack(canvas);
        drawTickMarks(canvas);
        canvas.restoreToCount(save);
    }

    @Override // androidx.appcompat.widget.SeslProgressBar, android.view.View
    public final void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null && this.mDisabledAlpha < 1.0f) {
            drawable.setAlpha(isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0f));
        }
        if (this.mThumb != null && this.mHasThumbTint) {
            if (isEnabled()) {
                this.mThumb.setTintList(this.mDefaultActivatedThumbColor);
            } else {
                this.mThumb.setTintList(null);
            }
        }
        Drawable drawable2 = this.mThumb;
        if (drawable2 != null && drawable2.isStateful() && drawable2.setState(getDrawableState())) {
            invalidateDrawable(drawable2);
        }
        Drawable drawable3 = this.mTickMark;
        if (drawable3 != null && drawable3.isStateful() && drawable3.setState(getDrawableState())) {
            invalidateDrawable(drawable3);
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar, android.view.View
    public CharSequence getAccessibilityClassName() {
        Log.d("SeslAbsSeekBar", "Stack:", new Throwable("stack dump"));
        return AbsSeekBar.class.getName();
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized int getMax() {
        return this.mIsSeamless ? Math.round(super.getMax() / 1000.0f) : super.getMax();
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized int getMin() {
        return this.mIsSeamless ? Math.round(super.getMin() / 1000.0f) : super.getMin();
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized int getProgress() {
        return this.mIsSeamless ? Math.round(super.getProgress() / 1000.0f) : super.getProgress();
    }

    public final float getScale() {
        int max = getMax() - getMin();
        if (max > 0) {
            return (getProgress() - r0) / max;
        }
        return 0.0f;
    }

    public final void initMuteAnimation() {
        this.mMuteAnimationSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        int i = 400;
        for (int i2 = 0; i2 < 8; i2++) {
            boolean z = i2 % 2 == 0;
            ValueAnimator ofInt = z ? ValueAnimator.ofInt(0, i) : ValueAnimator.ofInt(i, 0);
            ofInt.setDuration(62);
            ofInt.setInterpolator(new LinearInterpolator());
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.widget.SeslAbsSeekBar.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SeslAbsSeekBar.this.mCurrentProgressLevel = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    SeslAbsSeekBar seslAbsSeekBar = SeslAbsSeekBar.this;
                    int i3 = seslAbsSeekBar.mCurrentProgressLevel;
                    Drawable drawable = seslAbsSeekBar.mCurrentDrawable;
                    if (drawable != null) {
                        Drawable findDrawableByLayerId = drawable instanceof LayerDrawable ? ((LayerDrawable) drawable).findDrawableByLayerId(android.R.id.progress) : null;
                        if (findDrawableByLayerId != null) {
                            findDrawableByLayerId.setLevel(i3);
                        }
                    }
                    float f = i3 / 10000.0f;
                    Drawable drawable2 = seslAbsSeekBar.mThumb;
                    if (drawable2 != null) {
                        seslAbsSeekBar.setThumbPos(seslAbsSeekBar.getWidth(), drawable2, f, VideoPlayer.MEDIA_ERROR_SYSTEM);
                        seslAbsSeekBar.invalidate();
                    }
                }
            });
            arrayList.add(ofInt);
            if (z) {
                i = (int) (i * 0.6d);
            }
        }
        this.mMuteAnimationSet.playSequentially(arrayList);
    }

    public final void initializeExpandMode() {
        SliderDrawable sliderDrawable = new SliderDrawable(this, this.mTrackMinWidth, this.mTrackMaxWidth, this.mDefaultNormalProgressColor);
        SliderDrawable sliderDrawable2 = new SliderDrawable(this, this.mTrackMinWidth, this.mTrackMaxWidth, this.mDefaultSecondaryProgressColor);
        SliderDrawable sliderDrawable3 = new SliderDrawable(this, this.mTrackMinWidth, this.mTrackMaxWidth, this.mDefaultActivatedProgressColor);
        Drawable drawableWrapperCompat = new DrawableWrapperCompat(new ThumbDrawable(this.mThumbRadius, this.mDefaultActivatedThumbColor, false));
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{sliderDrawable, new ClipDrawable(sliderDrawable2, 19, 1), new ClipDrawable(sliderDrawable3, 19, 1)});
        layerDrawable.setPaddingMode(1);
        layerDrawable.setId(0, android.R.id.background);
        layerDrawable.setId(1, android.R.id.secondaryProgress);
        layerDrawable.setId(2, android.R.id.progress);
        super.setProgressDrawable(layerDrawable);
        setThumb(drawableWrapperCompat);
        setBackgroundResource(R.drawable.sesl_seekbar_background_borderless_expand);
        int i = this.mMaxHeight;
        int i2 = this.mTrackMaxWidth;
        if (i > i2) {
            this.mMaxHeight = i2;
            requestLayout();
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.mTickMark;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar, android.view.View
    public final synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mCurrentMode == 4) {
            this.mSplitProgress.draw(canvas);
            this.mDivider.draw(canvas);
        }
        if (this.mThumb != null) {
            int save = canvas.save();
            int i = this.mCurrentMode;
            if (i != 3 && i != 6) {
                canvas.translate(getPaddingLeft() - this.mThumbOffset, getPaddingTop());
                this.mThumb.draw(canvas);
                canvas.restoreToCount(save);
            }
            canvas.translate(getPaddingLeft(), getPaddingTop() - this.mThumbOffset);
            this.mThumb.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        return super.onHoverEvent(motionEvent);
    }

    @Override // androidx.appcompat.widget.SeslProgressBar, android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (isEnabled()) {
            int progress = getProgress();
            if (progress > getMin()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
            }
            if (progress < getMax()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
    
        if (r9 != 81) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0059, code lost:
    
        if (r9 != 81) goto L47;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x004c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0041  */
    @Override // android.view.View, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (isEnabled()) {
            int i2 = this.mKeyProgressIncrement;
            int i3 = this.mCurrentMode;
            if (i3 == 3 || i3 == 6) {
                if (i != 19) {
                    if (i == 20 || i == 69) {
                        i2 = -i2;
                    } else if (i != 70) {
                    }
                }
                if (ViewUtils.isLayoutRtl(this)) {
                    i2 = -i2;
                }
                if (setProgressInternal(this.mIsSeamless ? Math.round((getProgress() + i2) * 1000.0f) : i2 + getProgress(), true, true)) {
                    return true;
                }
            } else {
                if (i != 21) {
                    if (i != 22) {
                        if (i != 69) {
                            if (i != 70) {
                            }
                        }
                    }
                    if (ViewUtils.isLayoutRtl(this)) {
                        i2 = -i2;
                    }
                    if (setProgressInternal(!this.mIsSeamless ? Math.round((getProgress() + i2) * 1000.0f) : i2 + getProgress(), true, true)) {
                        return true;
                    }
                }
                i2 = -i2;
                if (ViewUtils.isLayoutRtl(this)) {
                }
                if (setProgressInternal(!this.mIsSeamless ? Math.round((getProgress() + i2) * 1000.0f) : i2 + getProgress(), true, true)) {
                }
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // androidx.appcompat.widget.SeslProgressBar, android.view.View
    public final synchronized void onMeasure(int i, int i2) {
        int i3;
        int i4;
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != null) {
            int i5 = this.mCurrentMode;
            if (i5 != 3 && i5 != 6) {
                Drawable drawable2 = this.mThumb;
                int intrinsicHeight = drawable2 == null ? 0 : drawable2.getIntrinsicHeight();
                i4 = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, drawable.getIntrinsicWidth()));
                i3 = Math.max(intrinsicHeight, Math.max(this.mMinHeight, Math.min(this.mMaxHeight, drawable.getIntrinsicHeight())));
            }
            Drawable drawable3 = this.mThumb;
            int intrinsicHeight2 = drawable3 == null ? 0 : drawable3.getIntrinsicHeight();
            int max = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, drawable.getIntrinsicHeight()));
            i3 = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, drawable.getIntrinsicWidth()));
            i4 = Math.max(intrinsicHeight2, max);
        } else {
            i3 = 0;
            i4 = 0;
        }
        setMeasuredDimension(View.resolveSizeAndState(getPaddingLeft() + getPaddingRight() + i4, i, 0), View.resolveSizeAndState(getPaddingTop() + getPaddingBottom() + i3, i2, 0));
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public void onProgressRefresh(float f, int i, boolean z) {
        int i2 = (int) (10000.0f * f);
        AnimatorSet animatorSet = this.mMuteAnimationSet;
        if (animatorSet != null && animatorSet.isRunning()) {
            this.mMuteAnimationSet.cancel();
        }
        this.mIsFirstSetProgress = false;
        this.mCurrentProgressLevel = i2;
        super.onProgressRefresh(f, i, z);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            setThumbPos(getWidth(), drawable, f, VideoPlayer.MEDIA_ERROR_SYSTEM);
            invalidate();
        }
        if (z && this.mCurrentMode == 8) {
            performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(41));
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final void onResolveDrawables(int i) {
        super.onResolveDrawables(i);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            drawable.setLayoutDirection(i);
        }
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            setThumbPos(getWidth(), drawable, getScale(), VideoPlayer.MEDIA_ERROR_SYSTEM);
            invalidate();
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar, android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        updateDrawableBounds(i, i2);
        updateThumbAndTrackPos(i, i2);
    }

    public void onStartTrackingTouch() {
        this.mIsDragging = true;
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public void onStopTrackingTouch() {
        this.mIsDragging = false;
        if (!this.mIsSeamless || !isPressed()) {
            if (this.mIsSeamless) {
                setProgress(Math.round(super.getProgress() / 1000.0f));
                return;
            }
            return;
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(super.getProgress(), (int) (Math.round(super.getProgress() / 1000.0f) * 1000.0f));
        this.mValueAnimator = ofInt;
        ofInt.setDuration(300L);
        this.mValueAnimator.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_90);
        this.mValueAnimator.start();
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.widget.SeslAbsSeekBar.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SeslAbsSeekBar.super.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        boolean z = false;
        if (!this.mIsUserSeekable || !isEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mIsDraggingForSliding = false;
            int i2 = this.mCurrentMode;
            if (i2 != 5 && i2 != 6 && i2 != 0) {
                Method method = SeslBaseReflector.getMethod(SeslViewReflector.mClass, "hidden_isInScrollingContainer", new Class[0]);
                if (method != null) {
                    Object invoke = SeslBaseReflector.invoke(this, method, new Object[0]);
                    if (invoke instanceof Boolean) {
                        z = ((Boolean) invoke).booleanValue();
                    }
                }
                if (!z) {
                    startDrag(motionEvent);
                }
            }
            this.mTouchDownX = motionEvent.getX();
            this.mTouchDownY = motionEvent.getY();
        } else if (action == 1) {
            if (this.mIsDraggingForSliding) {
                this.mIsDraggingForSliding = false;
            }
            if (this.mIsDragging) {
                trackTouchEvent(motionEvent);
                onStopTrackingTouch();
                setPressed(false);
            } else {
                onStartTrackingTouch();
                trackTouchEvent(motionEvent);
                onStopTrackingTouch();
            }
            invalidate();
        } else if (action == 2) {
            this.mIsDraggingForSliding = true;
            if (this.mIsDragging) {
                trackTouchEvent(motionEvent);
            } else {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                int i3 = this.mCurrentMode;
                if ((i3 != 3 && i3 != 6 && Math.abs(x - this.mTouchDownX) > this.mScaledTouchSlop) || (((i = this.mCurrentMode) == 3 || i == 6) && Math.abs(y - this.mTouchDownY) > this.mScaledTouchSlop)) {
                    startDrag(motionEvent);
                }
            }
        } else if (action == 3) {
            this.mIsDraggingForSliding = false;
            if (this.mIsDragging) {
                onStopTrackingTouch();
                setPressed(false);
            }
            invalidate();
        }
        return true;
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final void onVisualProgressChanged(float f, int i) {
        Drawable drawable;
        if (i != 16908301 || (drawable = this.mThumb) == null) {
            return;
        }
        setThumbPos(getWidth(), drawable, f, VideoPlayer.MEDIA_ERROR_SYSTEM);
        invalidate();
    }

    @Override // android.view.View
    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        boolean z;
        boolean z2;
        if (super.performAccessibilityAction(i, bundle)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        if (i == 4096 || i == 8192) {
            synchronized (this) {
                z = this.mIndeterminate;
            }
            if (!(!z && isEnabled())) {
                return false;
            }
            int max = Math.max(1, Math.round((getMax() - getMin()) / 20.0f));
            if (i == 8192) {
                max = -max;
            }
            return setProgressInternal(this.mIsSeamless ? Math.round(((float) (getProgress() + max)) * 1000.0f) : getProgress() + max, true, true);
        }
        if (i != 16908349) {
            return false;
        }
        synchronized (this) {
            z2 = this.mIndeterminate;
        }
        if (!(!z2 && isEnabled()) || bundle == null || !bundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
            return false;
        }
        float f = bundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
        return setProgressInternal(this.mIsSeamless ? Math.round(f * 1000.0f) : (int) f, true, true);
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized void setMax(int i) {
        if (this.mIsSeamless) {
            i = Math.round(i * 1000.0f);
        }
        super.setMax(i);
        this.mIsFirstSetProgress = true;
        int max = getMax() - getMin();
        int i2 = this.mKeyProgressIncrement;
        if (i2 == 0 || max / i2 > 20) {
            int max2 = Math.max(1, Math.round(max / 20.0f));
            if (max2 < 0) {
                max2 = -max2;
            }
            this.mKeyProgressIncrement = max2;
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized void setMin(int i) {
        if (this.mIsSeamless) {
            i = Math.round(i * 1000.0f);
        }
        super.setMin(i);
        int max = getMax() - getMin();
        int i2 = this.mKeyProgressIncrement;
        if (i2 == 0 || max / i2 > 20) {
            int max2 = Math.max(1, Math.round(max / 20.0f));
            if (max2 < 0) {
                max2 = -max2;
            }
            this.mKeyProgressIncrement = max2;
        }
    }

    public final void setMode(int i) {
        Drawable drawable;
        if (this.mCurrentMode == i && this.mIsSetModeCalled) {
            Log.w("SeslAbsSeekBar", "Seekbar mode is already set. Do not call this method redundant");
            return;
        }
        this.mCurrentMode = i;
        if (i == 3) {
            Context context = getContext();
            Object obj = ContextCompat.sLock;
            drawable = context.getDrawable(R.drawable.sesl_scrubber_progress_vertical);
        } else if (i != 4) {
            if (i == 7) {
                this.mOnlyIndeterminate = false;
                setIndeterminate(false);
                LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{new SeslProgressBar.CirCleProgressDrawable(true, new ColorStateList(new int[][]{new int[0]}, new int[]{getResources().getColor(R.color.sesl_progress_control_color_background)})), new SeslProgressBar.CirCleProgressDrawable(false, new ColorStateList(new int[][]{new int[0]}, new int[]{getResources().getColor(R.color.sesl_progress_control_color_activated_light)}))});
                layerDrawable.setPaddingMode(1);
                layerDrawable.setId(0, android.R.id.background);
                layerDrawable.setId(1, android.R.id.progress);
                super.setProgressDrawable(layerDrawable);
            }
            drawable = null;
        } else {
            Context context2 = getContext();
            Object obj2 = ContextCompat.sLock;
            drawable = context2.getDrawable(R.drawable.sesl_split_seekbar_background_progress);
        }
        if (drawable != null) {
            setProgressDrawable(tileify(drawable, false));
        }
        this.mLevelDrawPadding = 0.0f;
        if (i == 0) {
            setProgressTintList(this.mDefaultActivatedProgressColor);
            setThumbTintList(this.mDefaultActivatedThumbColor);
        } else if (i == 1) {
            updateWarningMode(getProgress());
        } else if (i == 3) {
            setThumb(getContext().getResources().getDrawable(this.mIsLightTheme ? R.drawable.sesl_scrubber_control_anim_light : R.drawable.sesl_scrubber_control_anim_dark));
            setBackgroundResource(R.drawable.sesl_seek_bar_background_borderless);
        } else if (i == 4) {
            this.mSplitProgress = getContext().getResources().getDrawable(R.drawable.sesl_split_seekbar_primary_progress);
            this.mDivider = getContext().getResources().getDrawable(R.drawable.sesl_split_seekbar_vertical_bar);
            updateSplitProgress();
        } else if (i == 5) {
            SliderDrawable sliderDrawable = new SliderDrawable(this, this.mModeExpandTrackMinWidth, this.mModeExpandTrackMaxWidth, this.mDefaultNormalProgressColor);
            SliderDrawable sliderDrawable2 = new SliderDrawable(this, this.mModeExpandTrackMinWidth, this.mModeExpandTrackMaxWidth, this.mDefaultSecondaryProgressColor);
            SliderDrawable sliderDrawable3 = new SliderDrawable(this, this.mModeExpandTrackMinWidth, this.mModeExpandTrackMaxWidth, this.mDefaultActivatedProgressColor);
            Drawable drawableWrapperCompat = new DrawableWrapperCompat(new ThumbDrawable(this.mModeExpandThumbRadius, this.mDefaultActivatedThumbColor, false));
            LayerDrawable layerDrawable2 = new LayerDrawable(new Drawable[]{sliderDrawable, new ClipDrawable(sliderDrawable2, 19, 1), new ClipDrawable(sliderDrawable3, 19, 1)});
            layerDrawable2.setPaddingMode(1);
            layerDrawable2.setId(0, android.R.id.background);
            layerDrawable2.setId(1, android.R.id.secondaryProgress);
            layerDrawable2.setId(2, android.R.id.progress);
            super.setProgressDrawable(layerDrawable2);
            setThumb(drawableWrapperCompat);
            setBackgroundResource(R.drawable.sesl_seekbar_background_borderless_expand);
            int i2 = this.mMaxHeight;
            int i3 = this.mModeExpandTrackMaxWidth;
            if (i2 > i3) {
                this.mMaxHeight = i3;
                requestLayout();
            }
            this.mLevelDrawPadding = getContext().getResources().getDimension(R.dimen.sesl_seekbar_level_progress_padding_start_end);
        } else if (i == 6) {
            SliderDrawable sliderDrawable4 = new SliderDrawable(this.mTrackMinWidth, this.mTrackMaxWidth, this.mDefaultNormalProgressColor, true);
            SliderDrawable sliderDrawable5 = new SliderDrawable(this.mTrackMinWidth, this.mTrackMaxWidth, this.mDefaultSecondaryProgressColor, true);
            SliderDrawable sliderDrawable6 = new SliderDrawable(this.mTrackMinWidth, this.mTrackMaxWidth, this.mDefaultActivatedProgressColor, true);
            Drawable drawableWrapperCompat2 = new DrawableWrapperCompat(new ThumbDrawable(this.mThumbRadius, this.mDefaultActivatedThumbColor, true));
            LayerDrawable layerDrawable3 = new LayerDrawable(new Drawable[]{sliderDrawable4, new ClipDrawable(sliderDrawable5, 81, 2), new ClipDrawable(sliderDrawable6, 81, 2)});
            layerDrawable3.setPaddingMode(1);
            layerDrawable3.setId(0, android.R.id.background);
            layerDrawable3.setId(1, android.R.id.secondaryProgress);
            layerDrawable3.setId(2, android.R.id.progress);
            super.setProgressDrawable(layerDrawable3);
            setThumb(drawableWrapperCompat2);
            setBackgroundResource(R.drawable.sesl_seekbar_background_borderless_expand);
            int i4 = this.mMaxWidth;
            int i5 = this.mTrackMaxWidth;
            if (i4 > i5) {
                this.mMaxWidth = i5;
                requestLayout();
            }
        } else if (i == 8) {
            this.mLevelDrawPadding = getContext().getResources().getDimension(R.dimen.sesl_seekbar_level_progress_padding_start_end);
            super.setProgressDrawable(getContext().getResources().getDrawable(R.drawable.sesl_level_seekbar_progress));
            setTickMark(getContext().getResources().getDrawable(R.drawable.sesl_level_seekbar_tick_mark));
            setThumb(getContext().getResources().getDrawable(R.drawable.sesl_level_seekbar_thumb));
            setBackgroundResource(R.drawable.sesl_seek_bar_background_borderless);
        }
        invalidate();
        this.mIsSetModeCalled = true;
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized void setProgress(int i) {
        if (this.mIsSeamless) {
            i = Math.round(i * 1000.0f);
        }
        super.setProgress(i);
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final void setProgressDrawable(Drawable drawable) {
        super.setProgressDrawable(drawable);
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final boolean setProgressInternal(int i, boolean z, boolean z2) {
        boolean progressInternal = super.setProgressInternal(i, z, z2);
        updateWarningMode(i);
        return progressInternal;
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final void setProgressTintList(ColorStateList colorStateList) {
        super.setProgressTintList(colorStateList);
        this.mDefaultActivatedProgressColor = colorStateList;
    }

    public final void setSeamless() {
        int i;
        if (!this.mIsSeamless) {
            this.mIsSeamless = true;
            super.setMax(Math.round(super.getMax() * 1000.0f));
            super.setMin(Math.round(super.getMin() * 1000.0f));
            super.setProgress(Math.round(super.getProgress() * 1000.0f));
            synchronized (this) {
                i = this.mIndeterminate ? 0 : this.mSecondaryProgress;
            }
            super.setSecondaryProgress(Math.round(i * 1000.0f));
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized void setSecondaryProgress(int i) {
        if (this.mIsSeamless) {
            i = Math.round(i * 1000.0f);
        }
        super.setSecondaryProgress(i);
    }

    @Override // android.view.View
    public final void setSystemGestureExclusionRects(List list) {
        Preconditions.checkNotNull(list, "rects must not be null");
        this.mUserGestureExclusionRects = list;
        updateGestureExclusionRects();
    }

    public final void setThumb(Drawable drawable) {
        boolean z;
        Drawable drawable2 = this.mThumb;
        if (drawable2 == null || drawable == drawable2) {
            z = false;
        } else {
            drawable2.setCallback(null);
            z = true;
        }
        if (drawable != null) {
            drawable.setCallback(this);
            if (canResolveLayoutDirection()) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                drawable.setLayoutDirection(ViewCompat.Api17Impl.getLayoutDirection(this));
            }
            int i = this.mCurrentMode;
            if (i == 3 || i == 6) {
                this.mThumbOffset = drawable.getIntrinsicHeight() / 2;
            } else {
                this.mThumbOffset = drawable.getIntrinsicWidth() / 2;
            }
            if (z && (drawable.getIntrinsicWidth() != this.mThumb.getIntrinsicWidth() || drawable.getIntrinsicHeight() != this.mThumb.getIntrinsicHeight())) {
                requestLayout();
            }
        }
        this.mThumb = drawable;
        applyThumbTint();
        invalidate();
        if (z) {
            updateThumbAndTrackPos(getWidth(), getHeight());
            if (drawable == null || !drawable.isStateful()) {
                return;
            }
            drawable.setState(getDrawableState());
        }
    }

    public final void setThumbPos(int i, Drawable drawable, float f, int i2) {
        int i3;
        int i4 = this.mCurrentMode;
        if (i4 == 3 || i4 == 6) {
            setThumbPosInVertical(getHeight(), drawable, f, i2);
            return;
        }
        int paddingLeft = ((i - getPaddingLeft()) - getPaddingRight()) - ((int) (this.mLevelDrawPadding * 2.0f));
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int i5 = (this.mThumbOffset * 2) + (paddingLeft - intrinsicWidth);
        int i6 = (int) ((f * i5) + 0.5f);
        if (i2 == Integer.MIN_VALUE) {
            Rect bounds = drawable.getBounds();
            i2 = bounds.top;
            i3 = bounds.bottom;
        } else {
            i3 = i2 + intrinsicHeight;
        }
        int i7 = (int) this.mLevelDrawPadding;
        if (ViewUtils.isLayoutRtl(this) && this.mMirrorForRtl) {
            i6 = i5 - i6;
        }
        int i8 = i7 + i6;
        int i9 = i8 + intrinsicWidth;
        Drawable background = getBackground();
        if (background != null) {
            int paddingLeft2 = getPaddingLeft() - this.mThumbOffset;
            int paddingTop = getPaddingTop();
            background.setHotspotBounds(i8 + paddingLeft2, i2 + paddingTop, paddingLeft2 + i9, paddingTop + i3);
        }
        drawable.setBounds(i8, i2, i9, i3);
        updateGestureExclusionRects();
        this.mThumbPosX = (getPaddingLeft() + i8) - (getPaddingLeft() - (intrinsicWidth / 2));
        updateSplitProgress();
    }

    public final void setThumbPosInVertical(int i, Drawable drawable, float f, int i2) {
        int i3;
        int paddingTop = (i - getPaddingTop()) - getPaddingBottom();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicHeight2 = drawable.getIntrinsicHeight();
        int i4 = (this.mThumbOffset * 2) + (paddingTop - intrinsicHeight2);
        int i5 = (int) ((f * i4) + 0.5f);
        if (i2 == Integer.MIN_VALUE) {
            Rect bounds = drawable.getBounds();
            i2 = bounds.left;
            i3 = bounds.right;
        } else {
            i3 = i2 + intrinsicHeight;
        }
        int i6 = i4 - i5;
        int i7 = intrinsicHeight2 + i6;
        Drawable background = getBackground();
        if (background != null) {
            int paddingLeft = getPaddingLeft();
            int paddingTop2 = getPaddingTop() - this.mThumbOffset;
            background.setHotspotBounds(i2 + paddingLeft, i6 + paddingTop2, paddingLeft + i3, paddingTop2 + i7);
        }
        drawable.setBounds(i2, i6, i3, i7);
        this.mThumbPosX = getPaddingLeft() + (intrinsicHeight / 2) + i6;
    }

    public final void setThumbTintList(ColorStateList colorStateList) {
        this.mThumbTintList = colorStateList;
        this.mHasThumbTint = true;
        applyThumbTint();
        this.mDefaultActivatedThumbColor = colorStateList;
    }

    public final void setTickMark(Drawable drawable) {
        Drawable drawable2 = this.mTickMark;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mTickMark = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            drawable.setLayoutDirection(ViewCompat.Api17Impl.getLayoutDirection(this));
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            applyTickMarkTint();
        }
        invalidate();
    }

    public final void startDrag(MotionEvent motionEvent) {
        setPressed(true);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            invalidate(drawable.getBounds());
        }
        onStartTrackingTouch();
        trackTouchEvent(motionEvent);
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    public final void trackTouchEvent(MotionEvent motionEvent) {
        float f;
        int min;
        float paddingLeft;
        float f2;
        float f3;
        int min2;
        int i = this.mCurrentMode;
        if (i == 3 || i == 6) {
            int height = getHeight();
            int paddingTop = (height - getPaddingTop()) - getPaddingBottom();
            int round = Math.round(motionEvent.getX());
            int round2 = height - Math.round(motionEvent.getY());
            float paddingBottom = round2 < getPaddingBottom() ? 0.0f : round2 > height - getPaddingTop() ? 1.0f : (round2 - getPaddingBottom()) / paddingTop;
            if (this.mIsSeamless) {
                float max = super.getMax() - super.getMin();
                float f4 = 1.0f / max;
                if (paddingBottom > 0.0f && paddingBottom < 1.0f) {
                    float f5 = paddingBottom % f4;
                    if (f5 > f4 / 2.0f) {
                        paddingBottom += f4 - f5;
                    }
                }
                f = paddingBottom * max;
                min = super.getMin();
            } else {
                float max2 = getMax() - getMin();
                float f6 = 1.0f / max2;
                if (paddingBottom > 0.0f && paddingBottom < 1.0f) {
                    float f7 = paddingBottom % f6;
                    if (f7 > f6 / 2.0f) {
                        paddingBottom += f6 - f7;
                    }
                }
                f = paddingBottom * max2;
                min = getMin();
            }
            float f8 = f + min + 0.0f;
            float f9 = round;
            float f10 = round2;
            Drawable background = getBackground();
            if (background != null) {
                background.setHotspot(f9, f10);
            }
            setProgressInternal(Math.round(f8), true, false);
            return;
        }
        int round3 = Math.round(motionEvent.getX());
        int round4 = Math.round(motionEvent.getY());
        int width = getWidth();
        int paddingLeft2 = (width - getPaddingLeft()) - getPaddingRight();
        if (ViewUtils.isLayoutRtl(this) && this.mMirrorForRtl) {
            if (round3 <= width - getPaddingRight()) {
                if (round3 >= getPaddingLeft()) {
                    paddingLeft = getPaddingLeft() + (paddingLeft2 - round3);
                    f2 = paddingLeft / paddingLeft2;
                }
                f2 = 1.0f;
            }
            f2 = 0.0f;
        } else {
            if (round3 >= getPaddingLeft()) {
                if (round3 <= width - getPaddingRight()) {
                    paddingLeft = round3 - getPaddingLeft();
                    f2 = paddingLeft / paddingLeft2;
                }
                f2 = 1.0f;
            }
            f2 = 0.0f;
        }
        if (this.mIsSeamless) {
            float max3 = super.getMax() - super.getMin();
            float f11 = 1.0f / max3;
            if (f2 > 0.0f && f2 < 1.0f) {
                float f12 = f2 % f11;
                if (f12 > f11 / 2.0f) {
                    f2 += f11 - f12;
                }
            }
            f3 = f2 * max3;
            min2 = super.getMin();
        } else {
            float max4 = getMax() - getMin();
            float f13 = 1.0f / max4;
            if (f2 > 0.0f && f2 < 1.0f) {
                float f14 = f2 % f13;
                if (f14 > f13 / 2.0f) {
                    f2 += f13 - f14;
                }
            }
            f3 = f2 * max4;
            min2 = getMin();
        }
        float f15 = f3 + min2 + 0.0f;
        float f16 = round3;
        float f17 = round4;
        Drawable background2 = getBackground();
        if (background2 != null) {
            background2.setHotspot(f16, f17);
        }
        setProgressInternal(Math.round(f15), true, false);
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final void updateDrawableBounds(int i, int i2) {
        super.updateDrawableBounds(i, i2);
        updateThumbAndTrackPos(i, i2);
    }

    public final void updateGestureExclusionRects() {
        Drawable drawable = this.mThumb;
        if (drawable == null) {
            super.setSystemGestureExclusionRects(this.mUserGestureExclusionRects);
            return;
        }
        this.mGestureExclusionRects.clear();
        drawable.copyBounds(this.mThumbRect);
        this.mGestureExclusionRects.add(this.mThumbRect);
        this.mGestureExclusionRects.addAll(this.mUserGestureExclusionRects);
        super.setSystemGestureExclusionRects(this.mGestureExclusionRects);
    }

    public final void updateSplitProgress() {
        if (this.mCurrentMode != 4) {
            return;
        }
        Drawable drawable = this.mSplitProgress;
        Rect bounds = this.mCurrentDrawable.getBounds();
        if (drawable != null) {
            if (this.mMirrorForRtl && ViewUtils.isLayoutRtl(this)) {
                drawable.setBounds(this.mThumbPosX, bounds.top, getWidth() - getPaddingRight(), bounds.bottom);
            } else {
                drawable.setBounds(getPaddingLeft(), bounds.top, this.mThumbPosX, bounds.bottom);
            }
        }
        int width = getWidth();
        int height = getHeight();
        Drawable drawable2 = this.mDivider;
        if (drawable2 != null) {
            float f = width / 2.0f;
            float f2 = this.mDensity;
            float f3 = height / 2.0f;
            drawable2.setBounds((int) (f - ((f2 * 4.0f) / 2.0f)), (int) (f3 - ((f2 * 22.0f) / 2.0f)), (int) (((4.0f * f2) / 2.0f) + f), (int) (((f2 * 22.0f) / 2.0f) + f3));
        }
    }

    public final void updateThumbAndTrackPos(int i, int i2) {
        int m8m;
        int i3;
        int i4;
        int i5;
        int i6 = this.mCurrentMode;
        if (i6 == 3 || i6 == 6) {
            int paddingLeft = (i - getPaddingLeft()) - getPaddingRight();
            Drawable drawable = this.mCurrentDrawable;
            Drawable drawable2 = this.mThumb;
            int min = Math.min(this.mMaxWidth, paddingLeft);
            int intrinsicWidth = drawable2 == null ? 0 : drawable2.getIntrinsicWidth();
            if (intrinsicWidth > min) {
                m8m = (paddingLeft - intrinsicWidth) / 2;
                i3 = AbsActionBarView$$ExternalSyntheticOutline0.m8m(intrinsicWidth, min, 2, m8m);
            } else {
                int i7 = (paddingLeft - min) / 2;
                m8m = AbsActionBarView$$ExternalSyntheticOutline0.m8m(min, intrinsicWidth, 2, i7);
                i3 = i7;
            }
            if (drawable != null) {
                drawable.setBounds(i3, 0, paddingLeft - i3, (i2 - getPaddingBottom()) - getPaddingTop());
            }
            if (drawable2 != null) {
                setThumbPosInVertical(i2, drawable2, getScale(), m8m);
                return;
            }
            return;
        }
        int paddingTop = (i2 - getPaddingTop()) - getPaddingBottom();
        Drawable drawable3 = this.mCurrentDrawable;
        Drawable drawable4 = this.mThumb;
        int min2 = Math.min(this.mMaxHeight, paddingTop);
        int intrinsicHeight = drawable4 == null ? 0 : drawable4.getIntrinsicHeight();
        if (intrinsicHeight > min2) {
            i5 = (paddingTop - intrinsicHeight) / 2;
            i4 = AbsActionBarView$$ExternalSyntheticOutline0.m8m(intrinsicHeight, min2, 2, i5);
        } else {
            int i8 = (paddingTop - min2) / 2;
            int m8m2 = AbsActionBarView$$ExternalSyntheticOutline0.m8m(min2, intrinsicHeight, 2, i8);
            i4 = i8;
            i5 = m8m2;
        }
        if (drawable3 != null) {
            drawable3.setBounds(0, i4, (i - getPaddingRight()) - getPaddingLeft(), min2 + i4);
        }
        if (drawable4 != null) {
            setThumbPos(i, drawable4, getScale(), i5);
        }
        updateSplitProgress();
    }

    public final void updateWarningMode(int i) {
        if (this.mCurrentMode == 1) {
            if (!(i == getMax())) {
                setProgressTintList(this.mDefaultActivatedProgressColor);
                setThumbTintList(this.mDefaultActivatedThumbColor);
            } else {
                super.setProgressTintList(this.mOverlapActivatedProgressColor);
                this.mThumbTintList = this.mOverlapActivatedProgressColor;
                this.mHasThumbTint = true;
                applyThumbTint();
            }
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar, android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mThumb || drawable == this.mTickMark || super.verifyDrawable(drawable);
    }

    public SeslAbsSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempRect = new Rect();
        this.mThumbTintList = null;
        this.mThumbTintMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbTintMode = false;
        this.mTickMarkTintList = null;
        this.mTickMarkTintMode = null;
        this.mHasTickMarkTint = false;
        this.mHasTickMarkTintMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
        this.mUserGestureExclusionRects = Collections.emptyList();
        this.mGestureExclusionRects = new ArrayList();
        this.mThumbRect = new Rect();
        this.mIsFirstSetProgress = false;
        this.mIsDraggingForSliding = false;
        this.mIsSetModeCalled = false;
        this.mIsSeamless = false;
        this.mLevelDrawPadding = 0.0f;
    }

    public SeslAbsSeekBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslAbsSeekBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTempRect = new Rect();
        this.mThumbTintList = null;
        this.mThumbTintMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbTintMode = false;
        this.mTickMarkTintList = null;
        this.mTickMarkTintMode = null;
        this.mHasTickMarkTint = false;
        this.mHasTickMarkTintMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
        this.mUserGestureExclusionRects = Collections.emptyList();
        this.mGestureExclusionRects = new ArrayList();
        this.mThumbRect = new Rect();
        this.mIsFirstSetProgress = false;
        this.mIsDraggingForSliding = false;
        this.mIsSetModeCalled = false;
        this.mIsSeamless = false;
        this.mLevelDrawPadding = 0.0f;
        int[] iArr = R$styleable.AppCompatSeekBar;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, i2);
        try {
            saveAttributeDataForStyleable(context, iArr, attributeSet, obtainStyledAttributes, i, i2);
            Resources resources = context.getResources();
            setThumb(obtainStyledAttributes.getDrawable(0));
            if (obtainStyledAttributes.hasValue(4)) {
                this.mThumbTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(4, -1), null);
                this.mHasThumbTintMode = true;
            }
            if (obtainStyledAttributes.hasValue(3)) {
                this.mThumbTintList = obtainStyledAttributes.getColorStateList(3);
                this.mHasThumbTint = true;
            }
            setTickMark(obtainStyledAttributes.getDrawable(9));
            if (obtainStyledAttributes.hasValue(11)) {
                this.mTickMarkTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(11, -1), null);
                this.mHasTickMarkTintMode = true;
            }
            if (obtainStyledAttributes.hasValue(10)) {
                this.mTickMarkTintList = obtainStyledAttributes.getColorStateList(10);
                this.mHasTickMarkTint = true;
            }
            this.mSplitTrack = obtainStyledAttributes.getBoolean(2, false);
            this.mTrackMinWidth = obtainStyledAttributes.getDimensionPixelSize(8, Math.round(resources.getDimension(R.dimen.sesl_seekbar_track_height)));
            this.mTrackMaxWidth = obtainStyledAttributes.getDimensionPixelSize(7, Math.round(resources.getDimension(R.dimen.sesl_seekbar_track_height_expand)));
            this.mModeExpandTrackMinWidth = obtainStyledAttributes.getDimensionPixelSize(8, Math.round(resources.getDimension(R.dimen.sesl_seekbar_mode_expand_track_height)));
            this.mModeExpandTrackMaxWidth = obtainStyledAttributes.getDimensionPixelSize(7, Math.round(resources.getDimension(R.dimen.sesl_seekbar_mode_expand_track_height_expand)));
            this.mThumbRadius = obtainStyledAttributes.getDimensionPixelSize(6, Math.round(resources.getDimension(R.dimen.sesl_seekbar_thumb_radius)));
            this.mModeExpandThumbRadius = obtainStyledAttributes.getDimensionPixelSize(6, Math.round(resources.getDimension(R.dimen.sesl_seekbar_mode_expand_thumb_radius)));
            this.mThumbOffset = obtainStyledAttributes.getDimensionPixelOffset(1, this.mThumbOffset);
            invalidate();
            if (obtainStyledAttributes.hasValue(5)) {
                this.mCurrentMode = obtainStyledAttributes.getInt(5, 0);
            }
            if (obtainStyledAttributes.getBoolean(12, true)) {
                obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AppCompatTheme, 0, 0);
                this.mDisabledAlpha = obtainStyledAttributes.getFloat(0, 0.5f);
                obtainStyledAttributes.recycle();
            } else {
                this.mDisabledAlpha = 1.0f;
            }
            applyThumbTint();
            applyTickMarkTint();
            this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
            boolean isLightTheme = SeslMisc.isLightTheme(context);
            this.mIsLightTheme = isLightTheme;
            this.mDefaultNormalProgressColor = colorToColorStateList(resources.getColor(R.color.sesl_seekbar_control_color_default));
            this.mDefaultSecondaryProgressColor = colorToColorStateList(resources.getColor(R.color.sesl_seekbar_control_color_secondary));
            this.mDefaultActivatedProgressColor = colorToColorStateList(resources.getColor(R.color.sesl_seekbar_control_color_activated));
            colorToColorStateList(resources.getColor(isLightTheme ? R.color.sesl_seekbar_overlap_color_default_light : R.color.sesl_seekbar_overlap_color_default_dark));
            this.mOverlapActivatedProgressColor = colorToColorStateList(resources.getColor(isLightTheme ? R.color.sesl_seekbar_overlap_color_activated_light : R.color.sesl_seekbar_overlap_color_activated_dark));
            ColorStateList colorStateList = this.mThumbTintList;
            this.mDefaultActivatedThumbColor = colorStateList;
            if (colorStateList == null) {
                int[][] iArr2 = {new int[]{android.R.attr.state_enabled}, new int[]{-16842910}};
                int[] iArr3 = new int[2];
                iArr3[0] = resources.getColor(R.color.sesl_thumb_control_color_activated);
                iArr3[1] = resources.getColor(isLightTheme ? R.color.sesl_seekbar_disable_color_activated_light : R.color.sesl_seekbar_disable_color_activated_dark);
                this.mDefaultActivatedThumbColor = new ColorStateList(iArr2, iArr3);
            }
            if (resources.getBoolean(R.bool.sesl_seekbar_sliding_animation)) {
                initMuteAnimation();
            }
            int i3 = this.mCurrentMode;
            if (i3 != 0) {
                setMode(i3);
            } else {
                initializeExpandMode();
            }
        } catch (Throwable th) {
            throw th;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }
}
