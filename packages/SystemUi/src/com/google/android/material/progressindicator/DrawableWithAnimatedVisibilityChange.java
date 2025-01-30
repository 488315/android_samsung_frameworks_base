package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.Property;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class DrawableWithAnimatedVisibilityChange extends Drawable implements Animatable2Compat {
    public static final C43383 GROW_FRACTION = new Property(Float.class, "growFraction") { // from class: com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange.3
        @Override // android.util.Property
        public final Object get(Object obj) {
            return Float.valueOf(((DrawableWithAnimatedVisibilityChange) obj).getGrowFraction());
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            DrawableWithAnimatedVisibilityChange drawableWithAnimatedVisibilityChange = (DrawableWithAnimatedVisibilityChange) obj;
            float floatValue = ((Float) obj2).floatValue();
            if (drawableWithAnimatedVisibilityChange.growFraction != floatValue) {
                drawableWithAnimatedVisibilityChange.growFraction = floatValue;
                drawableWithAnimatedVisibilityChange.invalidateSelf();
            }
        }
    };
    public List animationCallbacks;
    public final BaseProgressIndicatorSpec baseSpec;
    public final Context context;
    public float growFraction;
    public ValueAnimator hideAnimator;
    public boolean ignoreCallbacks;
    public float mockGrowFraction;
    public boolean mockHideAnimationRunning;
    public boolean mockShowAnimationRunning;
    public ValueAnimator showAnimator;
    public int totalAlpha;
    public final Paint paint = new Paint();
    public AnimatorDurationScaleProvider animatorDurationScaleProvider = new AnimatorDurationScaleProvider();

    public DrawableWithAnimatedVisibilityChange(Context context, BaseProgressIndicatorSpec baseProgressIndicatorSpec) {
        this.context = context;
        this.baseSpec = baseProgressIndicatorSpec;
        setAlpha(255);
    }

    @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat
    public final void clearAnimationCallbacks() {
        this.animationCallbacks.clear();
        this.animationCallbacks = null;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.totalAlpha;
    }

    public final float getGrowFraction() {
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.baseSpec;
        if (!(baseProgressIndicatorSpec.showAnimationBehavior != 0)) {
            if (!(baseProgressIndicatorSpec.hideAnimationBehavior != 0)) {
                return 1.0f;
            }
        }
        return (this.mockHideAnimationRunning || this.mockShowAnimationRunning) ? this.mockGrowFraction : this.growFraction;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    public final boolean isHiding() {
        ValueAnimator valueAnimator = this.hideAnimator;
        return (valueAnimator != null && valueAnimator.isRunning()) || this.mockHideAnimationRunning;
    }

    @Override // android.graphics.drawable.Animatable
    public final boolean isRunning() {
        return isShowing() || isHiding();
    }

    public final boolean isShowing() {
        ValueAnimator valueAnimator = this.showAnimator;
        return (valueAnimator != null && valueAnimator.isRunning()) || this.mockShowAnimationRunning;
    }

    @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat
    public final void registerAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        if (this.animationCallbacks == null) {
            this.animationCallbacks = new ArrayList();
        }
        if (this.animationCallbacks.contains(animationCallback)) {
            return;
        }
        this.animationCallbacks.add(animationCallback);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.totalAlpha = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setMockHideAnimationRunning(boolean z, float f) {
        this.mockHideAnimationRunning = z;
        this.mockGrowFraction = f;
    }

    public void setMockShowAnimationRunning(boolean z, float f) {
        this.mockShowAnimationRunning = z;
        this.mockGrowFraction = f;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        return setVisible(z, z2, true);
    }

    public boolean setVisibleInternal(boolean z, boolean z2, boolean z3) {
        if (this.showAnimator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, GROW_FRACTION, 0.0f, 1.0f);
            this.showAnimator = ofFloat;
            ofFloat.setDuration(500L);
            this.showAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            ValueAnimator valueAnimator = this.showAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                throw new IllegalArgumentException("Cannot set showAnimator while the current showAnimator is running.");
            }
            this.showAnimator = valueAnimator;
            valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    DrawableWithAnimatedVisibilityChange drawableWithAnimatedVisibilityChange = DrawableWithAnimatedVisibilityChange.this;
                    List list = drawableWithAnimatedVisibilityChange.animationCallbacks;
                    if (list == null || drawableWithAnimatedVisibilityChange.ignoreCallbacks) {
                        return;
                    }
                    Iterator it = ((ArrayList) list).iterator();
                    while (it.hasNext()) {
                        ((Animatable2Compat.AnimationCallback) it.next()).onAnimationStart(drawableWithAnimatedVisibilityChange);
                    }
                }
            });
        }
        if (this.hideAnimator == null) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, GROW_FRACTION, 1.0f, 0.0f);
            this.hideAnimator = ofFloat2;
            ofFloat2.setDuration(500L);
            this.hideAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            ValueAnimator valueAnimator2 = this.hideAnimator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                throw new IllegalArgumentException("Cannot set hideAnimator while the current hideAnimator is running.");
            }
            this.hideAnimator = valueAnimator2;
            valueAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    DrawableWithAnimatedVisibilityChange.super.setVisible(false, false);
                    DrawableWithAnimatedVisibilityChange drawableWithAnimatedVisibilityChange = DrawableWithAnimatedVisibilityChange.this;
                    List list = drawableWithAnimatedVisibilityChange.animationCallbacks;
                    if (list == null || drawableWithAnimatedVisibilityChange.ignoreCallbacks) {
                        return;
                    }
                    Iterator it = ((ArrayList) list).iterator();
                    while (it.hasNext()) {
                        ((Animatable2Compat.AnimationCallback) it.next()).onAnimationEnd(drawableWithAnimatedVisibilityChange);
                    }
                }
            });
        }
        if (!isVisible() && !z) {
            return false;
        }
        ValueAnimator valueAnimator3 = z ? this.showAnimator : this.hideAnimator;
        if (!z3) {
            if (valueAnimator3.isRunning()) {
                valueAnimator3.end();
            } else {
                boolean z4 = this.ignoreCallbacks;
                this.ignoreCallbacks = true;
                for (ValueAnimator valueAnimator4 : new ValueAnimator[]{valueAnimator3}) {
                    valueAnimator4.end();
                }
                this.ignoreCallbacks = z4;
            }
            return super.setVisible(z, false);
        }
        if (z3 && valueAnimator3.isRunning()) {
            return false;
        }
        boolean z5 = !z || super.setVisible(z, false);
        if (!z ? this.baseSpec.hideAnimationBehavior == 0 : this.baseSpec.showAnimationBehavior == 0) {
            if (z2 || !valueAnimator3.isPaused()) {
                valueAnimator3.start();
            } else {
                valueAnimator3.resume();
            }
            return z5;
        }
        boolean z6 = this.ignoreCallbacks;
        this.ignoreCallbacks = true;
        for (ValueAnimator valueAnimator5 : new ValueAnimator[]{valueAnimator3}) {
            valueAnimator5.end();
        }
        this.ignoreCallbacks = z6;
        return z5;
    }

    @Override // android.graphics.drawable.Animatable
    public final void start() {
        setVisibleInternal(true, true, false);
    }

    @Override // android.graphics.drawable.Animatable
    public final void stop() {
        setVisibleInternal(false, true, false);
    }

    public final boolean unregisterAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        List list = this.animationCallbacks;
        if (list == null || !list.contains(animationCallback)) {
            return false;
        }
        this.animationCallbacks.remove(animationCallback);
        if (!this.animationCallbacks.isEmpty()) {
            return true;
        }
        this.animationCallbacks = null;
        return true;
    }

    public final boolean setVisible(boolean z, boolean z2, boolean z3) {
        AnimatorDurationScaleProvider animatorDurationScaleProvider = this.animatorDurationScaleProvider;
        ContentResolver contentResolver = this.context.getContentResolver();
        animatorDurationScaleProvider.getClass();
        return setVisibleInternal(z, z2, z3 && Settings.Global.getFloat(contentResolver, "animator_duration_scale", 1.0f) > 0.0f);
    }
}
