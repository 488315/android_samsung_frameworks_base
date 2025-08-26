package com.android.systemui.audio.soundcraft.view.volume;

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

/* loaded from: classes.dex */
public final class SoundCraftRoundedCornerSeekBarDrawable extends InsetDrawable {
    public static final PathInterpolator ALPHA_INTERPOLATOR;
    public static final LinearInterpolator LINEAR_INTERPOLATOR;
    public ValueAnimator buttonAnimator;
    public int buttonColor;
    public int buttonStartColor;
    public Context context;
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
            return new SoundCraftRoundedCornerSeekBarDrawable(((DrawableWrapper) this.wrappedState.newDrawable(resources, theme)).getDrawable());
        }
    }

    static {
        new Companion(null);
        ALPHA_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        LINEAR_INTERPOLATOR = new LinearInterpolator();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SoundCraftRoundedCornerSeekBarDrawable() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public static boolean isBlurEnable() {
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled()) {
            return false;
        }
        return BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean canApplyTheme() {
        Drawable drawable = getDrawable();
        return (drawable != null ? drawable.canApplyTheme() : false) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Rect bounds = drawable != null ? drawable.getBounds() : null;
        bounds.getClass();
        float fHeight = getBounds().height() / 2.0f;
        int iWidth = getBounds().width();
        int iHeight = iWidth != 0 ? (bounds.height() * 10000) / iWidth : 0;
        Paint paint = new Paint();
        paint.setColor(this.buttonColor);
        paint.setStyle(Paint.Style.FILL);
        if (this.level > iHeight || iHeight == 0) {
            super.draw(canvas);
            Drawable drawable2 = getDrawable();
            (drawable2 != null ? drawable2.getBounds() : null).getClass();
            canvas.drawCircle(r1.right - (getBounds().height() / 2.0f), fHeight, getBounds().height() / 2.0f, paint);
            return;
        }
        Paint paint2 = new Paint();
        float fHeight2 = ((((bounds.height() * 2) * this.level) / iHeight) - bounds.height()) / 2.0f;
        paint2.setShader(new LinearGradient(fHeight2 - (bounds.height() / 2.0f), 0.0f, (bounds.height() / 2.0f) + fHeight2, 0.0f, this.startColor, this.endColor, Shader.TileMode.CLAMP));
        Path path = new Path();
        path.addCircle(getBounds().height() / 2.0f, fHeight, getBounds().height() / 2.0f, Path.Direction.CW);
        canvas.clipPath(path);
        RectF rectF = new RectF(fHeight2 - (getBounds().height() / 2.0f), (getBounds().height() / 2.0f) + fHeight, (getBounds().height() / 2.0f) + fHeight2, fHeight - (getBounds().height() / 2.0f));
        canvas.drawRoundRect(rectF, fHeight2, fHeight2, paint2);
        canvas.drawRoundRect(rectF, fHeight2, fHeight2, paint);
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
        this.context = context;
        int color = context.getColor(R.color.tw_progress_color_control_activated_end);
        this.endColor = color;
        float f = 255;
        this.startColor = ColorUtils.setAlphaComponent(color, (int) ((isBlurEnable() ? 0.5f : 0.4f) * f));
        this.gradientColor = ColorUtils.setAlphaComponent(this.endColor, (int) (f * (isBlurEnable() ? 0.5f : 0.4f)));
        this.shockValueAnimator = ValueAnimator.ofArgb(this.startColor, this.endColor).setDuration(450L);
        this.buttonStartColor = ColorUtils.setAlphaComponent(this.endColor, 0);
        this.buttonColor = ColorUtils.setAlphaComponent(this.endColor, 0);
        this.buttonAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(200L);
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
        this.shockValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftRoundedCornerSeekBarDrawable.setShockColor.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SoundCraftRoundedCornerSeekBarDrawable.this.gradientColor = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                SoundCraftRoundedCornerSeekBarDrawable soundCraftRoundedCornerSeekBarDrawable = SoundCraftRoundedCornerSeekBarDrawable.this;
                soundCraftRoundedCornerSeekBarDrawable.onLevelChange(soundCraftRoundedCornerSeekBarDrawable.level);
            }
        });
    }

    public /* synthetic */ SoundCraftRoundedCornerSeekBarDrawable(Drawable drawable, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : drawable);
    }

    public SoundCraftRoundedCornerSeekBarDrawable(Drawable drawable) {
        super(drawable, 0);
        int color = Color.parseColor("#FFFFFF");
        this.endColor = color;
        float f = 255;
        this.startColor = ColorUtils.setAlphaComponent(color, (int) ((isBlurEnable() ? 0.5f : 0.4f) * f));
        this.gradientColor = ColorUtils.setAlphaComponent(this.endColor, (int) (f * (isBlurEnable() ? 0.5f : 0.4f)));
        this.shockValueAnimator = ValueAnimator.ofArgb(this.startColor, this.endColor).setDuration(450L);
        this.buttonStartColor = ColorUtils.setAlphaComponent(this.endColor, 0);
        this.buttonColor = ColorUtils.setAlphaComponent(this.endColor, 0);
        this.buttonAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(200L);
    }
}
