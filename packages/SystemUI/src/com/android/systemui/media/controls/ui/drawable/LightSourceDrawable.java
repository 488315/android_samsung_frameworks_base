package com.android.systemui.media.controls.ui.drawable;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.res.R$styleable;
import kotlin.Unit;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public final class LightSourceDrawable extends Drawable {
    public static final int $stable = 8;
    private boolean active;
    private boolean pressed;
    private Animator rippleAnimation;
    private int[] themeAttrs;
    private final RippleData rippleData = new RippleData(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    private Paint paint = new Paint();
    private int highlightColor = -1;

    private final void illuminate() {
        this.rippleData.alpha = 1.0f;
        invalidateSelf();
        Animator animator = this.rippleAnimation;
        if (animator != null) {
            animator.cancel();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.setStartDelay(133L);
        valueAnimatorOfFloat.setDuration(800 - valueAnimatorOfFloat.getStartDelay());
        Interpolator interpolator = Interpolators.LINEAR_OUT_SLOW_IN;
        valueAnimatorOfFloat.setInterpolator(interpolator);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.drawable.LightSourceDrawable$illuminate$1$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.this$0.rippleData.alpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                this.this$0.invalidateSelf();
            }
        });
        Unit unit = Unit.INSTANCE;
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(this.rippleData.progress, 1.0f);
        valueAnimatorOfFloat2.setDuration(800L);
        valueAnimatorOfFloat2.setInterpolator(interpolator);
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.drawable.LightSourceDrawable$illuminate$1$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.this$0.rippleData.progress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                this.this$0.invalidateSelf();
            }
        });
        animatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.drawable.LightSourceDrawable$illuminate$1$3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                this.this$0.rippleData.progress = 0.0f;
                this.this$0.rippleAnimation = null;
                this.this$0.invalidateSelf();
            }
        });
        animatorSet.start();
        this.rippleAnimation = animatorSet;
    }

    private final void setActive(boolean z) {
        if (z == this.active) {
            return;
        }
        this.active = z;
        if (z) {
            Animator animator = this.rippleAnimation;
            if (animator != null) {
                animator.cancel();
            }
            RippleData rippleData = this.rippleData;
            rippleData.alpha = 1.0f;
            rippleData.progress = 0.05f;
        } else {
            Animator animator2 = this.rippleAnimation;
            if (animator2 != null) {
                animator2.cancel();
            }
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.rippleData.alpha, 0.0f);
            valueAnimatorOfFloat.setDuration(200L);
            valueAnimatorOfFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.drawable.LightSourceDrawable$active$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.this$0.rippleData.alpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    this.this$0.invalidateSelf();
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.drawable.LightSourceDrawable$active$1$2
                public boolean cancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator3) {
                    this.cancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator3) {
                    if (this.cancelled) {
                        return;
                    }
                    this.this$0.rippleData.progress = 0.0f;
                    this.this$0.rippleData.alpha = 0.0f;
                    this.this$0.rippleAnimation = null;
                    this.this$0.invalidateSelf();
                }
            });
            valueAnimatorOfFloat.start();
            this.rippleAnimation = valueAnimatorOfFloat;
        }
        invalidateSelf();
    }

    private final void updateStateFromTypedArray(TypedArray typedArray) {
        if (typedArray.hasValue(3)) {
            this.rippleData.minSize = typedArray.getDimension(3, 0.0f);
        }
        if (typedArray.hasValue(2)) {
            this.rippleData.maxSize = typedArray.getDimension(2, 0.0f);
        }
        if (typedArray.hasValue(1)) {
            this.rippleData.highlight = typedArray.getInteger(1, 0) / 100.0f;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        int[] iArr = this.themeAttrs;
        if (iArr != null) {
            TypedArray typedArrayResolveAttributes = theme.resolveAttributes(iArr, R$styleable.IlluminationDrawable);
            updateStateFromTypedArray(typedArrayResolveAttributes);
            typedArrayResolveAttributes.recycle();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        int[] iArr = this.themeAttrs;
        if (iArr != null) {
            iArr.getClass();
            if (iArr.length > 0) {
                return true;
            }
        }
        return super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        RippleData rippleData = this.rippleData;
        float fLerp = MathUtils.lerp(rippleData.minSize, rippleData.maxSize, rippleData.progress);
        int alphaComponent = ColorUtils.setAlphaComponent(this.highlightColor, (int) (this.rippleData.alpha * 255));
        Paint paint = this.paint;
        RippleData rippleData2 = this.rippleData;
        paint.setShader(new RadialGradient(rippleData2.x, rippleData2.y, fLerp, new int[]{alphaComponent, 0}, LightSourceDrawableKt.GRADIENT_STOPS, Shader.TileMode.CLAMP));
        RippleData rippleData3 = this.rippleData;
        canvas.drawCircle(rippleData3.x, rippleData3.y, fLerp, this.paint);
    }

    @Override // android.graphics.drawable.Drawable
    public Rect getDirtyBounds() {
        RippleData rippleData = this.rippleData;
        float fLerp = MathUtils.lerp(rippleData.minSize, rippleData.maxSize, rippleData.progress);
        RippleData rippleData2 = this.rippleData;
        float f = rippleData2.x;
        float f2 = rippleData2.y;
        Rect rect = new Rect((int) (f - fLerp), (int) (f2 - fLerp), (int) (f + fLerp), (int) (f2 + fLerp));
        rect.union(super.getDirtyBounds());
        return rect;
    }

    public final int getHighlightColor() {
        return this.highlightColor;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray typedArrayObtainAttributes = Drawable.obtainAttributes(resources, theme, attributeSet, R$styleable.IlluminationDrawable);
        this.themeAttrs = typedArrayObtainAttributes.extractThemeAttrs();
        updateStateFromTypedArray(typedArrayObtainAttributes);
        typedArrayObtainAttributes.recycle();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isProjected() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        boolean zOnStateChange = super.onStateChange(iArr);
        boolean z = this.pressed;
        boolean z2 = false;
        this.pressed = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        for (int i : iArr) {
            switch (i) {
                case R.attr.state_focused:
                    z4 = true;
                    break;
                case R.attr.state_enabled:
                    z3 = true;
                    break;
                case R.attr.state_pressed:
                    this.pressed = true;
                    break;
                case R.attr.state_hovered:
                    z5 = true;
                    break;
            }
        }
        if (z3 && (this.pressed || z4 || z5)) {
            z2 = true;
        }
        setActive(z2);
        if (z && !this.pressed) {
            illuminate();
        }
        return zOnStateChange;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i == this.paint.getAlpha()) {
            return;
        }
        this.paint.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        throw new UnsupportedOperationException("Color filters are not supported");
    }

    public final void setHighlightColor(int i) {
        if (this.highlightColor == i) {
            return;
        }
        this.highlightColor = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        RippleData rippleData = this.rippleData;
        rippleData.x = f;
        rippleData.y = f2;
        if (this.active) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
    }
}
