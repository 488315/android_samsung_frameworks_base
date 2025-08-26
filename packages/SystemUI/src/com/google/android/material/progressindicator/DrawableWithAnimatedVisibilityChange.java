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
import com.android.systemui.util.SettingsHelper;
import com.google.android.material.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class DrawableWithAnimatedVisibilityChange extends Drawable implements Animatable2Compat {
    public static final AnonymousClass3 GROW_FRACTION = new Property(Float.class, "growFraction") { // from class: com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange.3
        @Override // android.util.Property
        public final Object get(Object obj) {
            return Float.valueOf(((DrawableWithAnimatedVisibilityChange) obj).getGrowFraction());
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            DrawableWithAnimatedVisibilityChange drawableWithAnimatedVisibilityChange = (DrawableWithAnimatedVisibilityChange) obj;
            float fFloatValue = ((Float) obj2).floatValue();
            if (drawableWithAnimatedVisibilityChange.growFraction != fFloatValue) {
                drawableWithAnimatedVisibilityChange.growFraction = fFloatValue;
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
        if (baseProgressIndicatorSpec.showAnimationBehavior == 0 && baseProgressIndicatorSpec.hideAnimationBehavior == 0) {
            return 1.0f;
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
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, GROW_FRACTION, 0.0f, 1.0f);
            this.showAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(500L);
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
                    ArrayList arrayList = (ArrayList) list;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((Animatable2Compat.AnimationCallback) obj).onAnimationStart(drawableWithAnimatedVisibilityChange);
                    }
                }
            });
        }
        if (this.hideAnimator == null) {
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, GROW_FRACTION, 1.0f, 0.0f);
            this.hideAnimator = objectAnimatorOfFloat2;
            objectAnimatorOfFloat2.setDuration(500L);
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
                    ArrayList arrayList = (ArrayList) list;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((Animatable2Compat.AnimationCallback) obj).onAnimationEnd(drawableWithAnimatedVisibilityChange);
                    }
                }
            });
        }
        if (isVisible() || z) {
            ValueAnimator valueAnimator3 = z ? this.showAnimator : this.hideAnimator;
            ValueAnimator valueAnimator4 = z ? this.hideAnimator : this.showAnimator;
            if (!z3) {
                if (valueAnimator4.isRunning()) {
                    ValueAnimator[] valueAnimatorArr = new ValueAnimator[1];
                    boolean z4 = this.ignoreCallbacks;
                    this.ignoreCallbacks = true;
                    valueAnimator4.cancel();
                    this.ignoreCallbacks = z4;
                }
                if (valueAnimator3.isRunning()) {
                    valueAnimator3.end();
                } else {
                    ValueAnimator[] valueAnimatorArr2 = new ValueAnimator[1];
                    boolean z5 = this.ignoreCallbacks;
                    this.ignoreCallbacks = true;
                    valueAnimator3.end();
                    this.ignoreCallbacks = z5;
                }
                return super.setVisible(z, false);
            }
            if (!valueAnimator3.isRunning()) {
                boolean z6 = !z || super.setVisible(z, false);
                if (!z ? this.baseSpec.hideAnimationBehavior != 0 : this.baseSpec.showAnimationBehavior != 0) {
                    ValueAnimator[] valueAnimatorArr3 = new ValueAnimator[1];
                    boolean z7 = this.ignoreCallbacks;
                    this.ignoreCallbacks = true;
                    valueAnimator3.end();
                    this.ignoreCallbacks = z7;
                    return z6;
                }
                if (z2 || !valueAnimator3.isPaused()) {
                    valueAnimator3.start();
                    return z6;
                }
                valueAnimator3.resume();
                return z6;
            }
        }
        return false;
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
        return setVisibleInternal(z, z2, z3 && Settings.Global.getFloat(contentResolver, SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f) > 0.0f);
    }
}
