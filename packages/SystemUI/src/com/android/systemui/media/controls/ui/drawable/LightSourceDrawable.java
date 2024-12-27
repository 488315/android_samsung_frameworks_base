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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setStartDelay(133L);
        ofFloat.setDuration(800 - ofFloat.getStartDelay());
        Interpolator interpolator = Interpolators.LINEAR_OUT_SLOW_IN;
        ofFloat.setInterpolator(interpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.drawable.LightSourceDrawable$illuminate$1$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                RippleData rippleData;
                rippleData = LightSourceDrawable.this.rippleData;
                rippleData.alpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                LightSourceDrawable.this.invalidateSelf();
            }
        });
        Unit unit = Unit.INSTANCE;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(this.rippleData.progress, 1.0f);
        ofFloat2.setDuration(800L);
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.drawable.LightSourceDrawable$illuminate$1$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                RippleData rippleData;
                rippleData = LightSourceDrawable.this.rippleData;
                rippleData.progress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                LightSourceDrawable.this.invalidateSelf();
            }
        });
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.drawable.LightSourceDrawable$illuminate$1$3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                RippleData rippleData;
                rippleData = LightSourceDrawable.this.rippleData;
                rippleData.progress = 0.0f;
                LightSourceDrawable.this.rippleAnimation = null;
                LightSourceDrawable.this.invalidateSelf();
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
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.rippleData.alpha, 0.0f);
            ofFloat.setDuration(200L);
            ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.drawable.LightSourceDrawable$active$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RippleData rippleData2;
                    rippleData2 = LightSourceDrawable.this.rippleData;
                    rippleData2.alpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    LightSourceDrawable.this.invalidateSelf();
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.drawable.LightSourceDrawable$active$1$2
                public boolean cancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator3) {
                    this.cancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator3) {
                    RippleData rippleData2;
                    RippleData rippleData3;
                    if (this.cancelled) {
                        return;
                    }
                    rippleData2 = LightSourceDrawable.this.rippleData;
                    rippleData2.progress = 0.0f;
                    rippleData3 = LightSourceDrawable.this.rippleData;
                    rippleData3.alpha = 0.0f;
                    LightSourceDrawable.this.rippleAnimation = null;
                    LightSourceDrawable.this.invalidateSelf();
                }
            });
            ofFloat.start();
            this.rippleAnimation = ofFloat;
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
            TypedArray resolveAttributes = theme.resolveAttributes(iArr, R$styleable.IlluminationDrawable);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0008, code lost:
    
        if (r0.length <= 0) goto L6;
     */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean canApplyTheme() {
        /*
            r1 = this;
            int[] r0 = r1.themeAttrs
            if (r0 == 0) goto La
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            int r0 = r0.length
            if (r0 > 0) goto L10
        La:
            boolean r1 = super.canApplyTheme()
            if (r1 == 0) goto L12
        L10:
            r1 = 1
            goto L13
        L12:
            r1 = 0
        L13:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.drawable.LightSourceDrawable.canApplyTheme():boolean");
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        RippleData rippleData = this.rippleData;
        float lerp = MathUtils.lerp(rippleData.minSize, rippleData.maxSize, rippleData.progress);
        int alphaComponent = ColorUtils.setAlphaComponent(this.highlightColor, (int) (this.rippleData.alpha * 255));
        Paint paint = this.paint;
        RippleData rippleData2 = this.rippleData;
        paint.setShader(new RadialGradient(rippleData2.x, rippleData2.y, lerp, new int[]{alphaComponent, 0}, LightSourceDrawableKt.GRADIENT_STOPS, Shader.TileMode.CLAMP));
        RippleData rippleData3 = this.rippleData;
        canvas.drawCircle(rippleData3.x, rippleData3.y, lerp, this.paint);
    }

    @Override // android.graphics.drawable.Drawable
    public Rect getDirtyBounds() {
        RippleData rippleData = this.rippleData;
        float lerp = MathUtils.lerp(rippleData.minSize, rippleData.maxSize, rippleData.progress);
        RippleData rippleData2 = this.rippleData;
        float f = rippleData2.x;
        float f2 = rippleData2.y;
        Rect rect = new Rect((int) (f - lerp), (int) (f2 - lerp), (int) (f + lerp), (int) (f2 + lerp));
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
        TypedArray obtainAttributes = Drawable.obtainAttributes(resources, theme, attributeSet, R$styleable.IlluminationDrawable);
        this.themeAttrs = obtainAttributes.extractThemeAttrs();
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
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
        boolean onStateChange = super.onStateChange(iArr);
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
        return onStateChange;
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
