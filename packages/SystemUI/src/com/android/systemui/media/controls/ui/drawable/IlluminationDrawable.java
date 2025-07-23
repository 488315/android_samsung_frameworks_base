package com.android.systemui.media.controls.ui.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.res.R$styleable;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IlluminationDrawable extends Drawable {
    public static final int $stable = 8;
    private ValueAnimator backgroundAnimation;
    private int backgroundColor;
    private float cornerRadius;
    private float highlight;
    private int highlightColor;
    private int[] themeAttrs;
    private float cornerRadiusOverride = -1.0f;
    private float[] tmpHsl = {0.0f, 0.0f, 0.0f};
    private Paint paint = new Paint();
    private final ArrayList<LightSourceDrawable> lightSources = new ArrayList<>();

    private final void animateBackground() {
        ColorUtils.colorToHSL(this.backgroundColor, this.tmpHsl);
        float[] fArr = this.tmpHsl;
        float f = fArr[2];
        float f2 = this.highlight;
        fArr[2] = MathUtils.constrain(f < 1.0f - f2 ? f + f2 : f - f2, 0.0f, 1.0f);
        final int color = this.paint.getColor();
        final int i = this.highlightColor;
        final int HSLToColor = ColorUtils.HSLToColor(this.tmpHsl);
        ValueAnimator valueAnimator = this.backgroundAnimation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(370L);
        ofFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.drawable.IlluminationDrawable$animateBackground$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                Paint paint;
                int i2;
                ArrayList arrayList;
                int i3;
                float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                paint = IlluminationDrawable.this.paint;
                int i4 = color;
                i2 = IlluminationDrawable.this.backgroundColor;
                paint.setColor(ColorUtils.blendARGB(i4, i2, floatValue));
                IlluminationDrawable.this.highlightColor = ColorUtils.blendARGB(i, HSLToColor, floatValue);
                arrayList = IlluminationDrawable.this.lightSources;
                IlluminationDrawable illuminationDrawable = IlluminationDrawable.this;
                int size = arrayList.size();
                int i5 = 0;
                while (i5 < size) {
                    Object obj = arrayList.get(i5);
                    i5++;
                    i3 = illuminationDrawable.highlightColor;
                    ((LightSourceDrawable) obj).setHighlightColor(i3);
                }
                IlluminationDrawable.this.invalidateSelf();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.drawable.IlluminationDrawable$animateBackground$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                IlluminationDrawable.this.backgroundAnimation = null;
            }
        });
        ofFloat.start();
        this.backgroundAnimation = ofFloat;
    }

    private final void setBackgroundColor(int i) {
        if (i == this.backgroundColor) {
            return;
        }
        this.backgroundColor = i;
        animateBackground();
    }

    private final void updateStateFromTypedArray(TypedArray typedArray) {
        if (typedArray.hasValue(0)) {
            this.cornerRadius = typedArray.getDimension(0, getCornerRadius());
        }
        if (typedArray.hasValue(1)) {
            this.highlight = typedArray.getInteger(1, 0) / 100.0f;
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
        canvas.drawRoundRect(0.0f, 0.0f, getBounds().width(), getBounds().height(), getCornerRadius(), getCornerRadius(), this.paint);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.paint.getAlpha();
    }

    public final float getCornerRadius() {
        float f = this.cornerRadiusOverride;
        return f >= 0.0f ? f : this.cornerRadius;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        outline.setRoundRect(getBounds(), getCornerRadius());
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray obtainAttributes = Drawable.obtainAttributes(resources, theme, attributeSet, R$styleable.IlluminationDrawable);
        this.themeAttrs = obtainAttributes.extractThemeAttrs();
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
    }

    public final void registerLightSource(View view) {
        if (view.getBackground() instanceof LightSourceDrawable) {
            registerLightSource((LightSourceDrawable) view.getBackground());
        } else if (view.getForeground() instanceof LightSourceDrawable) {
            registerLightSource((LightSourceDrawable) view.getForeground());
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i == this.paint.getAlpha()) {
            return;
        }
        this.paint.setAlpha(i);
        invalidateSelf();
        ArrayList<LightSourceDrawable> arrayList = this.lightSources;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            LightSourceDrawable lightSourceDrawable = arrayList.get(i2);
            i2++;
            lightSourceDrawable.setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        throw new UnsupportedOperationException("Color filters are not supported");
    }

    public final void setCornerRadius(float f) {
        this.cornerRadius = f;
    }

    public final void setCornerRadiusOverride(Float f) {
        this.cornerRadiusOverride = f != null ? f.floatValue() : -1.0f;
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
        colorStateList.getClass();
        setBackgroundColor(colorStateList.getDefaultColor());
    }

    public void setXfermode(Xfermode xfermode) {
        if (Intrinsics.areEqual(xfermode, this.paint.getXfermode())) {
            return;
        }
        this.paint.setXfermode(xfermode);
        invalidateSelf();
    }

    private final void registerLightSource(LightSourceDrawable lightSourceDrawable) {
        lightSourceDrawable.setAlpha(this.paint.getAlpha());
        this.lightSources.add(lightSourceDrawable);
    }
}
