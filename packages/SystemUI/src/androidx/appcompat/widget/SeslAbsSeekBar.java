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
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.view.SeslHapticFeedbackConstantsReflector;
import androidx.reflect.view.SeslViewReflector;
import com.android.systemui.R;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;

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
    public final boolean mIsHapticEnabled;
    public final boolean mIsLightTheme;
    public boolean mIsSetModeCalled;
    public final boolean mIsUserSeekable;
    public int mKeyProgressIncrement;
    public float mLevelDrawPadding;
    public final int mModeExpandThumbRadius;
    public final int mModeExpandTrackMaxWidth;
    public final int mModeExpandTrackMinWidth;
    public AnimatorSet mMuteAnimationSet;
    public final ColorStateList mOverlapActivatedProgressColor;
    public int mPreviousHoverPopupType;
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
    public final ValueAnimator mValueAnimator;

    /* renamed from: androidx.appcompat.widget.SeslAbsSeekBar$1, reason: invalid class name */
    public class AnonymousClass1 implements ValueAnimator.AnimatorUpdateListener {
        public AnonymousClass1() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            SeslAbsSeekBar.super.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
    }

    public class SliderDrawable extends Drawable {
        public int mAlpha;
        public int mColor;
        public ColorStateList mColorStateList;
        public boolean mIsStateChanged;
        public final boolean mIsVertical;
        public final Paint mPaint;
        public final ValueAnimator mPressedAnimator;
        public float mRadius;
        public final ValueAnimator mReleasedAnimator;
        public final float mSliderMaxWidth;
        public final float mSliderMinWidth;
        public final SliderState mState;

        public class SliderState extends Drawable.ConstantState {
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

            public /* synthetic */ SliderState(SliderDrawable sliderDrawable, AnonymousClass1 anonymousClass1) {
                this();
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
                int width = SeslAbsSeekBar.this.getWidth();
                SeslAbsSeekBar seslAbsSeekBar = SeslAbsSeekBar.this;
                seslAbsSeekBar.getClass();
                int field_mPaddingLeft = width - SeslViewReflector.getField_mPaddingLeft(seslAbsSeekBar);
                SeslAbsSeekBar seslAbsSeekBar2 = SeslAbsSeekBar.this;
                seslAbsSeekBar2.getClass();
                float field_mPaddingRight = field_mPaddingLeft - SeslViewReflector.getField_mPaddingRight(seslAbsSeekBar2);
                float height = (SeslAbsSeekBar.this.getHeight() - SeslAbsSeekBar.this.getPaddingTop()) - SeslAbsSeekBar.this.getPaddingBottom();
                float f = this.mRadius;
                float f2 = field_mPaddingRight / 2.0f;
                canvas.drawLine(f2, height - f, f2, f, this.mPaint);
            } else {
                int width2 = SeslAbsSeekBar.this.getWidth();
                SeslAbsSeekBar seslAbsSeekBar3 = SeslAbsSeekBar.this;
                seslAbsSeekBar3.getClass();
                int field_mPaddingLeft2 = width2 - SeslViewReflector.getField_mPaddingLeft(seslAbsSeekBar3);
                SeslAbsSeekBar seslAbsSeekBar4 = SeslAbsSeekBar.this;
                seslAbsSeekBar4.getClass();
                float field_mPaddingRight2 = field_mPaddingLeft2 - SeslViewReflector.getField_mPaddingRight(seslAbsSeekBar4);
                float f3 = this.mRadius;
                canvas.drawLine(f3, SeslAbsSeekBar.this.getHeight() / 2.0f, field_mPaddingRight2 - f3, SeslAbsSeekBar.this.getHeight() / 2.0f, this.mPaint);
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
            boolean zOnStateChange = super.onStateChange(iArr);
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
            return zOnStateChange;
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
            this.mState = new SliderState(this, null);
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
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
            this.mPressedAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.setDuration(250L);
            ValueAnimator valueAnimator = this.mPressedAnimator;
            Interpolator interpolator = SeslAnimationUtils.SINE_IN_OUT_80;
            valueAnimator.setInterpolator(interpolator);
            this.mPressedAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.widget.SeslAbsSeekBar.SliderDrawable.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    SliderDrawable sliderDrawable = SliderDrawable.this;
                    sliderDrawable.mPaint.setStrokeWidth(fFloatValue);
                    sliderDrawable.mRadius = fFloatValue / 2.0f;
                    sliderDrawable.invalidateSelf();
                }
            });
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(f2, f);
            this.mReleasedAnimator = valueAnimatorOfFloat2;
            valueAnimatorOfFloat2.setDuration(250L);
            this.mReleasedAnimator.setInterpolator(interpolator);
            this.mReleasedAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.widget.SeslAbsSeekBar.SliderDrawable.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    SliderDrawable sliderDrawable = SliderDrawable.this;
                    sliderDrawable.mPaint.setStrokeWidth(fFloatValue);
                    sliderDrawable.mRadius = fFloatValue / 2.0f;
                    sliderDrawable.invalidateSelf();
                }
            });
        }
    }

    public class ThumbDrawable extends Drawable {
        public int mAlpha;
        public int mColor;
        public ColorStateList mColorStateList;
        public boolean mIsStateChanged;
        public final boolean mIsVertical;
        public final Paint mPaint;
        public final Paint mPaintInner;
        public final int mRadius;
        public int mRadiusForAni;
        public final int mStrokeWidth;
        public final ValueAnimator mThumbPressed;
        public final ValueAnimator mThumbReleased;

        public ThumbDrawable(int i, ColorStateList colorStateList, boolean z) {
            Paint paint = new Paint(1);
            this.mPaint = paint;
            Paint paint2 = new Paint(1);
            this.mPaintInner = paint2;
            this.mIsStateChanged = false;
            this.mAlpha = 255;
            this.mIsVertical = false;
            this.mStrokeWidth = SeslAbsSeekBar.this.getContext().getResources().getDimensionPixelSize(R.dimen.sesl_seekbar_thumb_stroke);
            this.mRadiusForAni = i;
            this.mRadius = i;
            this.mColorStateList = colorStateList;
            this.mColor = colorStateList.getDefaultColor();
            Paint.Style style = Paint.Style.FILL;
            paint.setStyle(style);
            paint2.setStyle(style);
            paint.setColor(this.mColor);
            paint2.setColor(SeslAbsSeekBar.this.getContext().getResources().getColor(R.color.sesl_thumb_control_fill_color_activated));
            this.mIsVertical = z;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(i, 0.0f);
            this.mThumbPressed = valueAnimatorOfFloat;
            valueAnimatorOfFloat.setDuration(100L);
            this.mThumbPressed.setInterpolator(new LinearInterpolator());
            this.mThumbPressed.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.widget.SeslAbsSeekBar.ThumbDrawable.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    ThumbDrawable thumbDrawable = ThumbDrawable.this;
                    thumbDrawable.mRadiusForAni = (int) fFloatValue;
                    thumbDrawable.invalidateSelf();
                }
            });
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, i);
            this.mThumbReleased = valueAnimatorOfFloat2;
            valueAnimatorOfFloat2.setDuration(300L);
            this.mThumbReleased.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_90);
            this.mThumbReleased.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.widget.SeslAbsSeekBar.ThumbDrawable.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    ThumbDrawable thumbDrawable = ThumbDrawable.this;
                    thumbDrawable.mRadiusForAni = (int) fFloatValue;
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
            Paint paint2 = this.mPaintInner;
            int i2 = this.mAlpha;
            paint2.setAlpha(((i2 + (i2 >>> 7)) * alpha) >>> 8);
            canvas.save();
            if (this.mIsVertical) {
                int width = SeslAbsSeekBar.this.getWidth();
                SeslAbsSeekBar seslAbsSeekBar = SeslAbsSeekBar.this;
                seslAbsSeekBar.getClass();
                int field_mPaddingLeft = width - SeslViewReflector.getField_mPaddingLeft(seslAbsSeekBar);
                SeslAbsSeekBar.this.getClass();
                float field_mPaddingRight = (field_mPaddingLeft - SeslViewReflector.getField_mPaddingRight(r3)) / 2.0f;
                SeslAbsSeekBar seslAbsSeekBar2 = SeslAbsSeekBar.this;
                canvas.drawCircle(field_mPaddingRight, seslAbsSeekBar2.mThumbPosX - SeslViewReflector.getField_mPaddingLeft(seslAbsSeekBar2), this.mRadiusForAni, this.mPaint);
                SeslAbsSeekBar seslAbsSeekBar3 = SeslAbsSeekBar.this;
                canvas.drawCircle(field_mPaddingRight, seslAbsSeekBar3.mThumbPosX - SeslViewReflector.getField_mPaddingLeft(seslAbsSeekBar3), this.mRadiusForAni - this.mStrokeWidth, this.mPaintInner);
            } else {
                SeslAbsSeekBar seslAbsSeekBar4 = SeslAbsSeekBar.this;
                canvas.drawCircle(seslAbsSeekBar4.mThumbPosX, seslAbsSeekBar4.getHeight() / 2.0f, this.mRadiusForAni, this.mPaint);
                SeslAbsSeekBar seslAbsSeekBar5 = SeslAbsSeekBar.this;
                canvas.drawCircle(seslAbsSeekBar5.mThumbPosX, seslAbsSeekBar5.getHeight() / 2.0f, this.mRadiusForAni - this.mStrokeWidth, this.mPaintInner);
            }
            canvas.restore();
            this.mPaint.setAlpha(alpha);
            this.mPaintInner.setAlpha(alpha);
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
            boolean zOnStateChange = super.onStateChange(iArr);
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
            return zOnStateChange;
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
        this.mUserGestureExclusionRects = Collections.EMPTY_LIST;
        this.mGestureExclusionRects = new ArrayList();
        this.mThumbRect = new Rect();
        this.mIsDraggingForSliding = false;
        this.mPreviousHoverPopupType = 0;
        this.mIsSetModeCalled = false;
        this.mLevelDrawPadding = 0.0f;
    }

    public static ColorStateList colorToColorStateList$1(int i) {
        return new ColorStateList(new int[][]{new int[0]}, new int[]{i});
    }

    public static boolean isHoverPopupTypeUserCustom(int i) throws NoSuchMethodException, SecurityException {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("com.samsung.android.widget.SemHoverPopupWindow", "hidden_TYPE_USER_CUSTOM", new Class[0]);
        Object objInvoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        return i == (objInvoke instanceof Integer ? ((Integer) objInvoke).intValue() : 3);
    }

    public final void applyThumbTint() {
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            if (this.mHasThumbTint || this.mHasThumbTintMode) {
                Drawable drawableMutate = drawable.mutate();
                this.mThumb = drawableMutate;
                if (this.mHasThumbTint) {
                    drawableMutate.setTintList(this.mThumbTintList);
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
                Drawable drawableMutate = drawable.mutate();
                this.mTickMark = drawableMutate;
                if (this.mHasTickMarkTint) {
                    drawableMutate.setTintList(this.mTickMarkTintList);
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
                float width = (((getWidth() - SeslViewReflector.getField_mPaddingLeft(this)) - SeslViewReflector.getField_mPaddingRight(this)) - (this.mLevelDrawPadding * 2.0f)) / max;
                int iSave = canvas.save();
                canvas.translate(this.mLevelDrawPadding + SeslViewReflector.getField_mPaddingLeft(this), getHeight() / 2.0f);
                for (int i3 = 0; i3 <= max; i3++) {
                    this.mTickMark.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(iSave);
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
        rect.offset(SeslViewReflector.getField_mPaddingLeft(this) - this.mThumbOffset, getPaddingTop());
        rect.left += opticalBounds.left;
        rect.right -= opticalBounds.right;
        int iSave = canvas.save();
        canvas.clipRect(rect, Region.Op.DIFFERENCE);
        super.drawTrack(canvas);
        drawTickMarks(canvas);
        canvas.restoreToCount(iSave);
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
        return super.getMax();
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized int getMin() {
        return super.getMin();
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized int getProgress() {
        return super.getProgress();
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
            ValueAnimator valueAnimatorOfInt = z ? ValueAnimator.ofInt(0, i) : ValueAnimator.ofInt(i, 0);
            valueAnimatorOfInt.setDuration(62);
            valueAnimatorOfInt.setInterpolator(new LinearInterpolator());
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.widget.SeslAbsSeekBar.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SeslAbsSeekBar.this.mCurrentProgressLevel = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    SeslAbsSeekBar seslAbsSeekBar = SeslAbsSeekBar.this;
                    int i3 = seslAbsSeekBar.mCurrentProgressLevel;
                    Drawable drawable = seslAbsSeekBar.mCurrentDrawable;
                    if (drawable != null) {
                        Drawable drawableFindDrawableByLayerId = drawable instanceof LayerDrawable ? ((LayerDrawable) drawable).findDrawableByLayerId(android.R.id.progress) : null;
                        if (drawableFindDrawableByLayerId != null) {
                            drawableFindDrawableByLayerId.setLevel(i3);
                        }
                    }
                    float f = i3 / 10000.0f;
                    Drawable drawable2 = seslAbsSeekBar.mThumb;
                    if (drawable2 != null) {
                        seslAbsSeekBar.setThumbPos(seslAbsSeekBar.getWidth(), drawable2, f, Integer.MIN_VALUE);
                        seslAbsSeekBar.invalidate();
                    }
                }
            });
            arrayList.add(valueAnimatorOfInt);
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // androidx.appcompat.widget.SeslProgressBar, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized void onDraw(Canvas canvas) {
        boolean zBooleanValue;
        try {
            super.onDraw(canvas);
            Class cls = SeslViewReflector.mClass;
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(cls, "isHoveringUIEnabled", new Class[0]);
            if (declaredMethod != null) {
                Object objInvoke = SeslBaseReflector.invoke(this, declaredMethod, new Object[0]);
                zBooleanValue = objInvoke instanceof Boolean ? ((Boolean) objInvoke).booleanValue() : false;
            }
            if (zBooleanValue) {
                Method method = SeslBaseReflector.getMethod(cls, "semGetHoverPopupType", new Class[0]);
                if (method != null) {
                    Object objInvoke2 = SeslBaseReflector.invoke(this, method, new Object[0]);
                    int iIntValue = objInvoke2 instanceof Integer ? ((Integer) objInvoke2).intValue() : 0;
                    if (isHoverPopupTypeUserCustom(iIntValue) && this.mPreviousHoverPopupType != iIntValue) {
                        this.mPreviousHoverPopupType = iIntValue;
                        Object objSemGetHoverPopup = SeslViewReflector.semGetHoverPopup(this);
                        Class cls2 = Integer.TYPE;
                        Method declaredMethod2 = SeslBaseReflector.getDeclaredMethod("com.samsung.android.widget.SemHoverPopupWindow", "hidden_setGravity", cls2);
                        if (declaredMethod2 != null) {
                            SeslBaseReflector.invoke(objSemGetHoverPopup, declaredMethod2, 12849);
                        }
                        int measuredHeight = getMeasuredHeight() / 2;
                        Object objSemGetHoverPopup2 = SeslViewReflector.semGetHoverPopup(this);
                        Method declaredMethod3 = SeslBaseReflector.getDeclaredMethod("com.samsung.android.widget.SemHoverPopupWindow", "hidden_setOffset", cls2, cls2);
                        if (declaredMethod3 != null) {
                            SeslBaseReflector.invoke(objSemGetHoverPopup2, declaredMethod3, 0, Integer.valueOf(measuredHeight));
                        }
                        Object objSemGetHoverPopup3 = SeslViewReflector.semGetHoverPopup(this);
                        Method declaredMethod4 = SeslBaseReflector.getDeclaredMethod("com.samsung.android.widget.SemHoverPopupWindow", "hidden_setHoverDetectTime", cls2);
                        if (declaredMethod4 != null) {
                            SeslBaseReflector.invoke(objSemGetHoverPopup3, declaredMethod4, 200);
                        }
                    }
                }
            }
            if (this.mCurrentMode == 4) {
                this.mSplitProgress.draw(canvas);
                this.mDivider.draw(canvas);
            }
            if (this.mThumb != null) {
                int iSave = canvas.save();
                int i = this.mCurrentMode;
                if (i == 3 || i == 6) {
                    canvas.translate(SeslViewReflector.getField_mPaddingLeft(this), getPaddingTop() - this.mThumbOffset);
                } else {
                    canvas.translate(SeslViewReflector.getField_mPaddingLeft(this) - this.mThumbOffset, getPaddingTop());
                }
                this.mThumb.draw(canvas);
                canvas.restoreToCount(iSave);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001e  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onHoverEvent(MotionEvent motionEvent) throws NoSuchMethodException, SecurityException {
        boolean zBooleanValue;
        Class cls = SeslViewReflector.mClass;
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(cls, "isHoveringUIEnabled", new Class[0]);
        if (declaredMethod != null) {
            Object objInvoke = SeslBaseReflector.invoke(this, declaredMethod, new Object[0]);
            zBooleanValue = objInvoke instanceof Boolean ? ((Boolean) objInvoke).booleanValue() : false;
        }
        if (zBooleanValue) {
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            motionEvent.getY();
            if (action == 7) {
                trackHoverEvent(x);
                Method method = SeslBaseReflector.getMethod(cls, "semGetHoverPopupType", new Class[0]);
                if (method != null) {
                    Object objInvoke2 = SeslBaseReflector.invoke(this, method, new Object[0]);
                    int iIntValue = objInvoke2 instanceof Integer ? ((Integer) objInvoke2).intValue() : 0;
                    if (isHoverPopupTypeUserCustom(iIntValue)) {
                        int rawX = (int) motionEvent.getRawX();
                        int rawY = (int) motionEvent.getRawY();
                        Object objSemGetHoverPopup = SeslViewReflector.semGetHoverPopup(this);
                        Class cls2 = Integer.TYPE;
                        Method method2 = SeslBaseReflector.getMethod("com.samsung.android.widget.SemHoverPopupWindow", "setHoveringPoint", cls2, cls2);
                        if (method2 != null) {
                            SeslBaseReflector.invoke(objSemGetHoverPopup, method2, Integer.valueOf(rawX), Integer.valueOf(rawY));
                        }
                        Object objSemGetHoverPopup2 = SeslViewReflector.semGetHoverPopup(this);
                        Method declaredMethod2 = SeslBaseReflector.getDeclaredMethod("com.samsung.android.widget.SemHoverPopupWindow", "hidden_update", new Class[0]);
                        if (declaredMethod2 != null) {
                            SeslBaseReflector.invoke(objSemGetHoverPopup2, declaredMethod2, new Object[0]);
                        }
                    }
                }
            } else if (action == 9) {
                trackHoverEvent(x);
            }
        }
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0038, code lost:
    
        if (setProgressInternal(getProgress() + r0, true, true) == false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x005b, code lost:
    
        if (setProgressInternal(getProgress() + r0, true, true) != false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x005d, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x002e  */
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
                    } else if (i == 70 || i == 81) {
                    }
                }
                if (getLayoutDirection() == 1) {
                    i2 = -i2;
                }
            } else {
                if (i != 21) {
                    if (i != 22) {
                        if (i != 69) {
                            if (i == 70 || i == 81) {
                            }
                        }
                    }
                    if (getLayoutDirection() == 1) {
                        i2 = -i2;
                    }
                }
                i2 = -i2;
                if (getLayoutDirection() == 1) {
                }
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // androidx.appcompat.widget.SeslProgressBar, android.view.View
    public final synchronized void onMeasure(int i, int i2) {
        int iMax;
        int iMax2;
        try {
            Drawable drawable = this.mCurrentDrawable;
            if (drawable != null) {
                int i3 = this.mCurrentMode;
                if (i3 == 3 || i3 == 6) {
                    Drawable drawable2 = this.mThumb;
                    int intrinsicHeight = drawable2 == null ? 0 : drawable2.getIntrinsicHeight();
                    int iMax3 = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, drawable.getIntrinsicHeight()));
                    iMax = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, drawable.getIntrinsicWidth()));
                    iMax2 = Math.max(intrinsicHeight, iMax3);
                } else {
                    Drawable drawable3 = this.mThumb;
                    int intrinsicHeight2 = drawable3 == null ? 0 : drawable3.getIntrinsicHeight();
                    iMax2 = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, drawable.getIntrinsicWidth()));
                    iMax = Math.max(intrinsicHeight2, Math.max(this.mMinHeight, Math.min(this.mMaxHeight, drawable.getIntrinsicHeight())));
                }
            } else {
                iMax = 0;
                iMax2 = 0;
            }
            setMeasuredDimension(View.resolveSizeAndState(SeslViewReflector.getField_mPaddingLeft(this) + SeslViewReflector.getField_mPaddingRight(this) + iMax2, i, 0), View.resolveSizeAndState(getPaddingTop() + getPaddingBottom() + iMax, i2, 0));
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public void onProgressRefresh(float f, boolean z, int i) {
        int i2 = (int) (10000.0f * f);
        AnimatorSet animatorSet = this.mMuteAnimationSet;
        if (animatorSet != null && animatorSet.isRunning()) {
            this.mMuteAnimationSet.cancel();
        }
        this.mCurrentProgressLevel = i2;
        super.onProgressRefresh(f, z, i);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            setThumbPos(getWidth(), drawable, f, Integer.MIN_VALUE);
            invalidate();
        }
        if (z && this.mCurrentMode == 8) {
            performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(41));
            return;
        }
        if (z && this.mIsHapticEnabled) {
            int i3 = this.mCurrentMode;
            if (i3 == 5 || i3 == 0 || i3 == 6 || i3 == 3) {
                if (i == getMin() || i == getMax()) {
                    performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(41));
                }
            }
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
            setThumbPos(getWidth(), drawable, getScale(), Integer.MIN_VALUE);
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
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        boolean zBooleanValue = false;
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
                    Object objInvoke = SeslBaseReflector.invoke(this, method, new Object[0]);
                    if (objInvoke instanceof Boolean) {
                        zBooleanValue = ((Boolean) objInvoke).booleanValue();
                    }
                }
                if (!zBooleanValue) {
                    startDrag(motionEvent);
                    return true;
                }
            }
            this.mTouchDownX = motionEvent.getX();
            this.mTouchDownY = motionEvent.getY();
            return true;
        }
        if (action == 1) {
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
            return true;
        }
        if (action == 2) {
            this.mIsDraggingForSliding = true;
            if (this.mIsDragging) {
                trackTouchEvent(motionEvent);
                return true;
            }
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int i3 = this.mCurrentMode;
            if ((i3 != 3 && i3 != 6 && Math.abs(x - this.mTouchDownX) > this.mScaledTouchSlop) || (((i = this.mCurrentMode) == 3 || i == 6) && Math.abs(y - this.mTouchDownY) > this.mScaledTouchSlop)) {
                startDrag(motionEvent);
            }
        } else if (action == 3) {
            this.mIsDraggingForSliding = false;
            if (this.mIsDragging) {
                onStopTrackingTouch();
                setPressed(false);
            }
            invalidate();
            return true;
        }
        return true;
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final void onVisualProgressChanged(float f, int i) {
        Drawable drawable;
        if (i != 16908301 || (drawable = this.mThumb) == null) {
            return;
        }
        setThumbPos(getWidth(), drawable, f, Integer.MIN_VALUE);
        invalidate();
    }

    @Override // android.view.View
    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        boolean z;
        boolean z2;
        if (!super.performAccessibilityAction(i, bundle)) {
            if (isEnabled()) {
                if (i == 4096 || i == 8192) {
                    synchronized (this) {
                        z = this.mIndeterminate;
                    }
                    if (!z && isEnabled()) {
                        int iMax = Math.max(1, Math.round((getMax() - getMin()) / 20.0f));
                        if (i == 8192) {
                            iMax = -iMax;
                        }
                        if (setProgressInternal(getProgress() + iMax, true, true)) {
                        }
                    }
                } else if (i == 16908349) {
                    synchronized (this) {
                        z2 = this.mIndeterminate;
                    }
                    if (!z2 && isEnabled() && bundle != null && bundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
                        return setProgressInternal((int) bundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE"), true, true);
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized void setMax(int i) {
        try {
            super.setMax(i);
            int max = getMax() - getMin();
            int i2 = this.mKeyProgressIncrement;
            if (i2 == 0 || max / i2 > 20) {
                int iMax = Math.max(1, Math.round(max / 20.0f));
                if (iMax < 0) {
                    iMax = -iMax;
                }
                this.mKeyProgressIncrement = iMax;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized void setMin(int i) {
        try {
            super.setMin(i);
            int max = getMax() - getMin();
            int i2 = this.mKeyProgressIncrement;
            if (i2 == 0 || max / i2 > 20) {
                int iMax = Math.max(1, Math.round(max / 20.0f));
                if (iMax < 0) {
                    iMax = -iMax;
                }
                this.mKeyProgressIncrement = iMax;
            }
        } catch (Throwable th) {
            throw th;
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
            drawable = getContext().getDrawable(R.drawable.sesl_scrubber_progress_vertical);
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
            drawable = getContext().getDrawable(R.drawable.sesl_split_seekbar_background_progress);
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
        super.setProgress(i);
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

    @Override // androidx.appcompat.widget.SeslProgressBar
    public final synchronized void setSecondaryProgress(int i) {
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
                drawable.setLayoutDirection(getLayoutDirection());
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
        int field_mPaddingLeft = ((i - SeslViewReflector.getField_mPaddingLeft(this)) - SeslViewReflector.getField_mPaddingRight(this)) - ((int) (this.mLevelDrawPadding * 2.0f));
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int i5 = (this.mThumbOffset * 2) + (field_mPaddingLeft - intrinsicWidth);
        int i6 = (int) ((f * i5) + 0.5f);
        if (i2 == Integer.MIN_VALUE) {
            Rect bounds = drawable.getBounds();
            i2 = bounds.top;
            i3 = bounds.bottom;
        } else {
            i3 = i2 + intrinsicHeight;
        }
        int i7 = (int) this.mLevelDrawPadding;
        if (getLayoutDirection() == 1 && this.mMirrorForRtl) {
            i6 = i5 - i6;
        }
        int i8 = i7 + i6;
        int i9 = i8 + intrinsicWidth;
        Drawable background = getBackground();
        if (background != null) {
            int field_mPaddingLeft2 = SeslViewReflector.getField_mPaddingLeft(this) - this.mThumbOffset;
            int paddingTop = getPaddingTop();
            background.setHotspotBounds(i8 + field_mPaddingLeft2, i2 + paddingTop, field_mPaddingLeft2 + i9, paddingTop + i3);
        }
        drawable.setBounds(i8, i2, i9, i3);
        updateGestureExclusionRects();
        this.mThumbPosX = (SeslViewReflector.getField_mPaddingLeft(this) + i8) - (SeslViewReflector.getField_mPaddingLeft(this) - (intrinsicWidth / 2));
        updateSplitProgress();
    }

    public final void setThumbPosInVertical(int i, Drawable drawable, float f, int i2) {
        int i3;
        int paddingTop = (i - getPaddingTop()) - getPaddingBottom();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int i4 = (this.mThumbOffset * 2) + (paddingTop - intrinsicHeight);
        int i5 = (int) ((f * i4) + 0.5f);
        if (i2 == Integer.MIN_VALUE) {
            Rect bounds = drawable.getBounds();
            i2 = bounds.left;
            i3 = bounds.right;
        } else {
            i3 = i2 + intrinsicWidth;
        }
        int i6 = i4 - i5;
        int i7 = intrinsicHeight + i6;
        Drawable background = getBackground();
        if (background != null) {
            int field_mPaddingLeft = SeslViewReflector.getField_mPaddingLeft(this);
            int paddingTop2 = getPaddingTop() - this.mThumbOffset;
            background.setHotspotBounds(i2 + field_mPaddingLeft, i6 + paddingTop2, field_mPaddingLeft + i3, paddingTop2 + i7);
        }
        drawable.setBounds(i2, i6, i3, i7);
        this.mThumbPosX = SeslViewReflector.getField_mPaddingLeft(this) + (intrinsicWidth / 2) + i6;
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
            drawable.setLayoutDirection(getLayoutDirection());
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

    public final void trackHoverEvent(int i) {
        int width = getWidth();
        SeslViewReflector.getField_mPaddingLeft(this);
        SeslViewReflector.getField_mPaddingRight(this);
        if (i >= SeslViewReflector.getField_mPaddingLeft(this) && i <= width - SeslViewReflector.getField_mPaddingRight(this)) {
            SeslViewReflector.getField_mPaddingLeft(this);
        }
        getMax();
    }

    public final void trackTouchEvent(MotionEvent motionEvent) {
        float field_mPaddingLeft;
        float f;
        int i = this.mCurrentMode;
        if (i == 3 || i == 6) {
            int height = getHeight();
            int paddingTop = (height - getPaddingTop()) - getPaddingBottom();
            int iRound = Math.round(motionEvent.getX());
            int iRound2 = height - Math.round(motionEvent.getY());
            float paddingBottom = iRound2 < getPaddingBottom() ? 0.0f : iRound2 > height - getPaddingTop() ? 1.0f : (iRound2 - getPaddingBottom()) / paddingTop;
            float max = getMax() - getMin();
            float f2 = 1.0f / max;
            if (paddingBottom > 0.0f && paddingBottom < 1.0f) {
                float f3 = paddingBottom % f2;
                if (f3 > f2 / 2.0f) {
                    paddingBottom += f2 - f3;
                }
            }
            float min = (paddingBottom * max) + getMin() + 0.0f;
            float f4 = iRound;
            float f5 = iRound2;
            Drawable background = getBackground();
            if (background != null) {
                background.setHotspot(f4, f5);
            }
            setProgressInternal(Math.round(min), true, false);
            return;
        }
        int iRound3 = Math.round(motionEvent.getX());
        int iRound4 = Math.round(motionEvent.getY());
        int width = getWidth();
        int field_mPaddingLeft2 = (width - SeslViewReflector.getField_mPaddingLeft(this)) - SeslViewReflector.getField_mPaddingRight(this);
        if (getLayoutDirection() == 1 && this.mMirrorForRtl) {
            if (iRound3 <= width - SeslViewReflector.getField_mPaddingRight(this)) {
                if (iRound3 >= SeslViewReflector.getField_mPaddingLeft(this)) {
                    field_mPaddingLeft = SeslViewReflector.getField_mPaddingLeft(this) + (field_mPaddingLeft2 - iRound3);
                    f = field_mPaddingLeft / field_mPaddingLeft2;
                }
                f = 1.0f;
            }
            f = 0.0f;
        } else {
            if (iRound3 >= SeslViewReflector.getField_mPaddingLeft(this)) {
                if (iRound3 <= width - SeslViewReflector.getField_mPaddingRight(this)) {
                    field_mPaddingLeft = iRound3 - SeslViewReflector.getField_mPaddingLeft(this);
                    f = field_mPaddingLeft / field_mPaddingLeft2;
                }
                f = 1.0f;
            }
            f = 0.0f;
        }
        float max2 = getMax() - getMin();
        float f6 = 1.0f / max2;
        if (f > 0.0f && f < 1.0f) {
            float f7 = f % f6;
            if (f7 > f6 / 2.0f) {
                f += f6 - f7;
            }
        }
        float min2 = (f * max2) + getMin() + 0.0f;
        float f8 = iRound3;
        float f9 = iRound4;
        Drawable background2 = getBackground();
        if (background2 != null) {
            background2.setHotspot(f8, f9);
        }
        setProgressInternal(Math.round(min2), true, false);
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
            if (this.mMirrorForRtl && getLayoutDirection() == 1) {
                drawable.setBounds(this.mThumbPosX, bounds.top, getWidth() - SeslViewReflector.getField_mPaddingRight(this), bounds.bottom);
            } else {
                drawable.setBounds(SeslViewReflector.getField_mPaddingLeft(this), bounds.top, this.mThumbPosX, bounds.bottom);
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
        int iM;
        int iM2;
        int iM3;
        int iM4;
        int i3 = this.mCurrentMode;
        if (i3 == 3 || i3 == 6) {
            int field_mPaddingLeft = (i - SeslViewReflector.getField_mPaddingLeft(this)) - SeslViewReflector.getField_mPaddingRight(this);
            Drawable drawable = this.mCurrentDrawable;
            Drawable drawable2 = this.mThumb;
            int iMin = Math.min(this.mMaxWidth, field_mPaddingLeft);
            int intrinsicWidth = drawable2 == null ? 0 : drawable2.getIntrinsicWidth();
            if (intrinsicWidth > iMin) {
                iM = (field_mPaddingLeft - intrinsicWidth) / 2;
                iM2 = AbsActionBarView$$ExternalSyntheticOutline0.m(intrinsicWidth, iMin, 2, iM);
            } else {
                int i4 = (field_mPaddingLeft - iMin) / 2;
                iM = AbsActionBarView$$ExternalSyntheticOutline0.m(iMin, intrinsicWidth, 2, i4);
                iM2 = i4;
            }
            if (drawable != null) {
                drawable.setBounds(iM2, 0, field_mPaddingLeft - iM2, (i2 - getPaddingBottom()) - getPaddingTop());
            }
            if (drawable2 != null) {
                setThumbPosInVertical(i2, drawable2, getScale(), iM);
                return;
            }
            return;
        }
        int paddingTop = (i2 - getPaddingTop()) - getPaddingBottom();
        Drawable drawable3 = this.mCurrentDrawable;
        Drawable drawable4 = this.mThumb;
        int iMin2 = Math.min(this.mMaxHeight, paddingTop);
        int intrinsicHeight = drawable4 == null ? 0 : drawable4.getIntrinsicHeight();
        if (intrinsicHeight > iMin2) {
            iM4 = (paddingTop - intrinsicHeight) / 2;
            iM3 = AbsActionBarView$$ExternalSyntheticOutline0.m(intrinsicHeight, iMin2, 2, iM4);
        } else {
            int i5 = (paddingTop - iMin2) / 2;
            iM3 = i5;
            iM4 = AbsActionBarView$$ExternalSyntheticOutline0.m(iMin2, intrinsicHeight, 2, i5);
        }
        if (drawable3 != null) {
            drawable3.setBounds(0, iM3, (i - SeslViewReflector.getField_mPaddingRight(this)) - SeslViewReflector.getField_mPaddingLeft(this), iMin2 + iM3);
        }
        if (drawable4 != null) {
            setThumbPos(i, drawable4, getScale(), iM4);
        }
        updateSplitProgress();
    }

    public final void updateWarningMode(int i) {
        if (this.mCurrentMode == 1) {
            if (i != getMax()) {
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
        this.mUserGestureExclusionRects = Collections.EMPTY_LIST;
        this.mGestureExclusionRects = new ArrayList();
        this.mThumbRect = new Rect();
        this.mIsDraggingForSliding = false;
        this.mPreviousHoverPopupType = 0;
        this.mIsSetModeCalled = false;
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
        this.mUserGestureExclusionRects = Collections.EMPTY_LIST;
        this.mGestureExclusionRects = new ArrayList();
        this.mThumbRect = new Rect();
        this.mIsDraggingForSliding = false;
        this.mPreviousHoverPopupType = 0;
        this.mIsSetModeCalled = false;
        this.mLevelDrawPadding = 0.0f;
        int[] iArr = R$styleable.AppCompatSeekBar;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, i2);
        try {
            saveAttributeDataForStyleable(context, iArr, attributeSet, typedArrayObtainStyledAttributes, i, i2);
            Resources resources = context.getResources();
            setThumb(typedArrayObtainStyledAttributes.getDrawable(0));
            if (typedArrayObtainStyledAttributes.hasValue(4)) {
                this.mThumbTintMode = DrawableUtils.parseTintMode(typedArrayObtainStyledAttributes.getInt(4, -1), null);
                this.mHasThumbTintMode = true;
            }
            if (typedArrayObtainStyledAttributes.hasValue(3)) {
                this.mThumbTintList = typedArrayObtainStyledAttributes.getColorStateList(3);
                this.mHasThumbTint = true;
            }
            setTickMark(typedArrayObtainStyledAttributes.getDrawable(10));
            if (typedArrayObtainStyledAttributes.hasValue(12)) {
                this.mTickMarkTintMode = DrawableUtils.parseTintMode(typedArrayObtainStyledAttributes.getInt(12, -1), null);
                this.mHasTickMarkTintMode = true;
            }
            if (typedArrayObtainStyledAttributes.hasValue(11)) {
                this.mTickMarkTintList = typedArrayObtainStyledAttributes.getColorStateList(11);
                this.mHasTickMarkTint = true;
            }
            this.mSplitTrack = typedArrayObtainStyledAttributes.getBoolean(2, false);
            this.mIsHapticEnabled = typedArrayObtainStyledAttributes.getBoolean(5, true);
            this.mTrackMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(9, Math.round(resources.getDimension(R.dimen.sesl_seekbar_track_height)));
            this.mTrackMaxWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(8, Math.round(resources.getDimension(R.dimen.sesl_seekbar_track_height_expand)));
            this.mModeExpandTrackMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(9, Math.round(resources.getDimension(R.dimen.sesl_seekbar_mode_expand_track_height)));
            this.mModeExpandTrackMaxWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(8, Math.round(resources.getDimension(R.dimen.sesl_seekbar_mode_expand_track_height_expand)));
            this.mThumbRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(7, Math.round(resources.getDimension(R.dimen.sesl_seekbar_thumb_radius)));
            this.mModeExpandThumbRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(7, Math.round(resources.getDimension(R.dimen.sesl_seekbar_mode_expand_thumb_radius)));
            this.mThumbOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(1, this.mThumbOffset);
            invalidate();
            if (typedArrayObtainStyledAttributes.hasValue(6)) {
                this.mCurrentMode = typedArrayObtainStyledAttributes.getInt(6, 0);
            }
            if (typedArrayObtainStyledAttributes.getBoolean(13, true)) {
                typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AppCompatTheme, 0, 0);
                try {
                    this.mDisabledAlpha = typedArrayObtainStyledAttributes.getFloat(0, 0.5f);
                    typedArrayObtainStyledAttributes.recycle();
                } finally {
                    typedArrayObtainStyledAttributes.recycle();
                }
            } else {
                this.mDisabledAlpha = 1.0f;
            }
            applyThumbTint();
            applyTickMarkTint();
            this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
            boolean zIsLightTheme = SeslMisc.isLightTheme(context);
            this.mIsLightTheme = zIsLightTheme;
            this.mDefaultNormalProgressColor = colorToColorStateList$1(resources.getColor(zIsLightTheme ? R.color.sesl_seekbar_control_color_default : R.color.sesl_seekbar_control_color_default_dark));
            this.mDefaultSecondaryProgressColor = colorToColorStateList$1(resources.getColor(R.color.sesl_seekbar_control_color_secondary));
            this.mDefaultActivatedProgressColor = colorToColorStateList$1(resources.getColor(R.color.sesl_seekbar_control_color_activated));
            colorToColorStateList$1(resources.getColor(zIsLightTheme ? R.color.sesl_seekbar_overlap_color_default_light : R.color.sesl_seekbar_overlap_color_default_dark));
            this.mOverlapActivatedProgressColor = colorToColorStateList$1(resources.getColor(zIsLightTheme ? R.color.sesl_seekbar_overlap_color_activated_light : R.color.sesl_seekbar_overlap_color_activated_dark));
            ColorStateList colorStateList = this.mThumbTintList;
            this.mDefaultActivatedThumbColor = colorStateList;
            if (colorStateList == null) {
                this.mDefaultActivatedThumbColor = new ColorStateList(new int[][]{new int[]{android.R.attr.state_enabled}, new int[]{-16842910}}, new int[]{resources.getColor(R.color.sesl_thumb_control_color_activated), resources.getColor(zIsLightTheme ? R.color.sesl_seekbar_disable_color_activated_light : R.color.sesl_seekbar_disable_color_activated_dark)});
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
        }
    }
}
