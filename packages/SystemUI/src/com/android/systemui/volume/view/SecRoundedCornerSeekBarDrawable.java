package com.android.systemui.volume.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class SecRoundedCornerSeekBarDrawable extends InsetDrawable {
    public static final PathInterpolator ALPHA_INTERPOLATOR;
    public static final LinearInterpolator LINEAR_INTERPOLATOR;
    public ValueAnimator buttonAnimator;
    public int buttonColor;
    public int buttonStartColor;
    public Context context;
    public int defaultColor;
    public int endColor;
    public int gradientColor;
    public int level;
    public ValueAnimator shockValueAnimator;
    public int startColor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class RoundedCornerState extends Drawable.ConstantState {
        public final Drawable.ConstantState wrappedState;

        public RoundedCornerState(Drawable.ConstantState constantState) {
            this.wrappedState = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final boolean canApplyTheme() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return this.wrappedState.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable() {
            return newDrawable(null, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable(Resources resources, Resources.Theme theme) {
            return new SecRoundedCornerSeekBarDrawable(((DrawableWrapper) this.wrappedState.newDrawable(resources, theme)).getDrawable());
        }
    }

    static {
        new Companion(null);
        ALPHA_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        LINEAR_INTERPOLATOR = new LinearInterpolator();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SecRoundedCornerSeekBarDrawable() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public static boolean isBlurEnable() {
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled()) {
            return false;
        }
        return BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR;
    }

    public final void animateButton(boolean z) {
        if (this.buttonAnimator.isRunning()) {
            this.buttonAnimator.cancel();
        }
        this.buttonAnimator.setInterpolator(LINEAR_INTERPOLATOR);
        if (z) {
            int i = this.buttonColor;
            int i2 = this.defaultColor;
            if (i != i2) {
                ValueAnimator duration = ValueAnimator.ofArgb(i, i2).setDuration(200L);
                this.buttonAnimator = duration;
                duration.start();
            }
        } else {
            int i3 = this.buttonColor;
            int i4 = this.buttonStartColor;
            if (i3 != i4) {
                ValueAnimator duration2 = ValueAnimator.ofArgb(i3, i4).setDuration(200L);
                this.buttonAnimator = duration2;
                duration2.start();
            }
        }
        this.buttonAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.SecRoundedCornerSeekBarDrawable.animateButton.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SecRoundedCornerSeekBarDrawable.this.buttonColor = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                SecRoundedCornerSeekBarDrawable.this.invalidateSelf();
            }
        });
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean canApplyTheme() {
        Drawable drawable = getDrawable();
        return (drawable != null ? drawable.canApplyTheme() : false) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        float fWidth = getBounds().width() / 2.0f;
        Paint paint = new Paint();
        paint.setColor(this.buttonColor);
        paint.setStyle(Paint.Style.FILL);
        int iWidth = getBounds().width();
        int iHeight = iWidth != 0 ? (getBounds().height() * 10000) / iWidth : 0;
        if (this.level > iHeight || iHeight == 0) {
            super.draw(canvas);
            Drawable drawable = getDrawable();
            (drawable != null ? drawable.getBounds() : null).getClass();
            canvas.drawCircle(r3.right - (getBounds().height() / 2.0f), fWidth, getBounds().height() / 2.0f, paint);
            return;
        }
        Paint paint2 = new Paint();
        float fHeight = ((((getBounds().height() * 2) * this.level) / iHeight) - getBounds().height()) / 2.0f;
        paint2.setShader(new LinearGradient(fHeight - (getBounds().height() / 2.0f), 0.0f, (getBounds().height() / 2.0f) + fHeight, 0.0f, this.startColor, this.endColor, Shader.TileMode.CLAMP));
        Path path = new Path();
        path.addCircle(getBounds().height() / 2.0f, fWidth, getBounds().height() / 2.0f, Path.Direction.CW);
        canvas.clipPath(path);
        RectF rectF = new RectF(fHeight - (getBounds().height() / 2.0f), (getBounds().height() / 2.0f) + fWidth, (getBounds().height() / 2.0f) + fHeight, fWidth - (getBounds().height() / 2.0f));
        canvas.drawRoundRect(rectF, fHeight, fHeight, paint2);
        canvas.drawRoundRect(rectF, fHeight, fHeight, paint);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getChangingConfigurations() {
        return super.getChangingConfigurations() | 4096;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        Drawable.ConstantState constantState = super.getConstantState();
        constantState.getClass();
        return new RoundedCornerState(constantState);
    }

    @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        onLevelChange(this.level);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean onLayoutDirectionChanged(int i) {
        onLevelChange(this.level);
        return super.onLayoutDirectionChanged(i);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        Drawable drawable = getDrawable();
        Rect bounds = drawable != null ? drawable.getBounds() : null;
        bounds.getClass();
        ((GradientDrawable) ((LayerDrawable) getDrawable()).findDrawableByLayerId(R.id.volume_seekbar_progress)).setColors(new int[]{this.gradientColor, this.endColor});
        this.level = i;
        int iWidth = getBounds().width();
        if (i >= (iWidth != 0 ? (bounds.height() * 10000) / iWidth : 0)) {
            int iWidth2 = (getBounds().width() * i) / 10000;
            Drawable drawable2 = getDrawable();
            if (drawable2 != null) {
                drawable2.setBounds(getBounds().left, bounds.top, getBounds().left + iWidth2, bounds.bottom);
            }
        }
        return super.onLevelChange(i);
    }

    public final void setContext(Context context) {
        if (this.context != null) {
            this.context = context;
            return;
        }
        int color = context.getColor(R.color.volume_seekbar_progress_color);
        this.defaultColor = color;
        this.context = context;
        this.endColor = ColorUtils.setAlphaComponent(color, 255);
        int alphaComponent = ColorUtils.setAlphaComponent(this.defaultColor, (int) (255 * (isBlurEnable() ? 0.5f : 0.4f)));
        this.startColor = alphaComponent;
        this.gradientColor = alphaComponent;
        this.shockValueAnimator = ValueAnimator.ofArgb(alphaComponent, this.endColor).setDuration(450L);
        this.buttonStartColor = ColorUtils.setAlphaComponent(this.defaultColor, 0);
        int alphaComponent2 = ColorUtils.setAlphaComponent(this.defaultColor, 0);
        this.buttonColor = alphaComponent2;
        this.buttonAnimator = ValueAnimator.ofArgb(alphaComponent2, this.defaultColor).setDuration(200L);
    }

    public final void setShockColor(boolean z) {
        ValueAnimator valueAnimator = this.shockValueAnimator;
        PathInterpolator pathInterpolator = ALPHA_INTERPOLATOR;
        valueAnimator.setInterpolator(pathInterpolator);
        if (this.shockValueAnimator.isRunning()) {
            this.shockValueAnimator.pause();
        }
        if (z) {
            int i = this.endColor;
            int i2 = this.gradientColor;
            if (i != i2) {
                ValueAnimator duration = ValueAnimator.ofArgb(i2, i).setDuration(450L);
                this.shockValueAnimator = duration;
                duration.setInterpolator(pathInterpolator);
                this.shockValueAnimator.start();
            }
        } else {
            int i3 = this.startColor;
            int i4 = this.gradientColor;
            if (i3 != i4) {
                ValueAnimator duration2 = ValueAnimator.ofArgb(i4, i3).setDuration(450L);
                this.shockValueAnimator = duration2;
                duration2.setInterpolator(pathInterpolator);
                this.shockValueAnimator.start();
            }
        }
        this.shockValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.SecRoundedCornerSeekBarDrawable.setShockColor.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SecRoundedCornerSeekBarDrawable.this.gradientColor = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                SecRoundedCornerSeekBarDrawable secRoundedCornerSeekBarDrawable = SecRoundedCornerSeekBarDrawable.this;
                secRoundedCornerSeekBarDrawable.onLevelChange(secRoundedCornerSeekBarDrawable.level);
            }
        });
    }

    public /* synthetic */ SecRoundedCornerSeekBarDrawable(Drawable drawable, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : drawable);
    }

    public SecRoundedCornerSeekBarDrawable(Drawable drawable) {
        super(drawable, 0);
        this.defaultColor = Color.parseColor("#FFFFFF");
        this.endColor = Color.parseColor("#FFFFFF");
        float f = 255;
        this.startColor = ColorUtils.setAlphaComponent(this.defaultColor, (int) ((isBlurEnable() ? 0.5f : 0.4f) * f));
        this.gradientColor = ColorUtils.setAlphaComponent(this.defaultColor, (int) (f * (isBlurEnable() ? 0.5f : 0.4f)));
        this.shockValueAnimator = ValueAnimator.ofArgb(this.startColor, this.endColor).setDuration(450L);
        this.buttonStartColor = ColorUtils.setAlphaComponent(this.defaultColor, 0);
        int alphaComponent = ColorUtils.setAlphaComponent(this.defaultColor, 0);
        this.buttonColor = alphaComponent;
        this.buttonAnimator = ValueAnimator.ofArgb(this.buttonStartColor, alphaComponent).setDuration(200L);
    }
}
