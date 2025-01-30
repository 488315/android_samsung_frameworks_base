package com.android.wm.shell.startingsurface;

import android.animation.AnimatorListenerAdapter;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.window.SplashScreenView;
import java.util.function.LongConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplashscreenIconDrawableFactory$AnimatableIconAnimateListener extends SplashscreenIconDrawableFactory$AdaptiveForegroundDrawable implements SplashScreenView.IconAnimateListener {
    public final Animatable mAnimatableIcon;
    public boolean mAnimationTriggered;
    public AnimatorListenerAdapter mJankMonitoringListener;
    public boolean mRunning;
    public LongConsumer mStartListener;

    public SplashscreenIconDrawableFactory$AnimatableIconAnimateListener(Drawable drawable) {
        super(drawable);
        this.mForegroundDrawable.setCallback(new Drawable.Callback() { // from class: com.android.wm.shell.startingsurface.SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.1
            @Override // android.graphics.drawable.Drawable.Callback
            public final void invalidateDrawable(Drawable drawable2) {
                SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.invalidateSelf();
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public final void scheduleDrawable(Drawable drawable2, Runnable runnable, long j) {
                SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.scheduleSelf(runnable, j);
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public final void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
                SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.unscheduleSelf(runnable);
            }
        });
        this.mAnimatableIcon = (Animatable) this.mForegroundDrawable;
    }

    @Override // com.android.wm.shell.startingsurface.SplashscreenIconDrawableFactory$AdaptiveForegroundDrawable, com.android.wm.shell.startingsurface.SplashscreenIconDrawableFactory$MaskBackgroundDrawable, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (!this.mAnimationTriggered) {
            if (!this.mRunning) {
                AnimatorListenerAdapter animatorListenerAdapter = this.mJankMonitoringListener;
                if (animatorListenerAdapter != null) {
                    animatorListenerAdapter.onAnimationStart(null);
                }
                long j = 0;
                try {
                    this.mAnimatableIcon.start();
                    Animatable animatable = this.mAnimatableIcon;
                    if (!(animatable instanceof AnimatedVectorDrawable) || ((AnimatedVectorDrawable) animatable).getTotalDuration() <= 0) {
                        Animatable animatable2 = this.mAnimatableIcon;
                        if ((animatable2 instanceof AnimationDrawable) && ((AnimationDrawable) animatable2).getTotalDuration() > 0) {
                            j = ((AnimationDrawable) this.mAnimatableIcon).getTotalDuration();
                        }
                    } else {
                        j = ((AnimatedVectorDrawable) this.mAnimatableIcon).getTotalDuration();
                    }
                    this.mRunning = true;
                    LongConsumer longConsumer = this.mStartListener;
                    if (longConsumer != null) {
                        longConsumer.accept(j);
                    }
                } catch (Exception e) {
                    Log.e("ShellStartingWindow", "Error while running the splash screen animated icon", e);
                    this.mRunning = false;
                    AnimatorListenerAdapter animatorListenerAdapter2 = this.mJankMonitoringListener;
                    if (animatorListenerAdapter2 != null) {
                        animatorListenerAdapter2.onAnimationCancel(null);
                    }
                    LongConsumer longConsumer2 = this.mStartListener;
                    if (longConsumer2 != null) {
                        longConsumer2.accept(0L);
                    }
                }
            }
            this.mAnimationTriggered = true;
        }
        super.draw(canvas);
    }

    public final void prepareAnimate(LongConsumer longConsumer) {
        stopAnimation();
        this.mStartListener = longConsumer;
    }

    public final void setAnimationJankMonitoring(AnimatorListenerAdapter animatorListenerAdapter) {
        this.mJankMonitoringListener = animatorListenerAdapter;
    }

    public final void stopAnimation() {
        if (this.mRunning) {
            this.mAnimatableIcon.stop();
            AnimatorListenerAdapter animatorListenerAdapter = this.mJankMonitoringListener;
            if (animatorListenerAdapter != null) {
                animatorListenerAdapter.onAnimationEnd(null);
            }
            this.mStartListener = null;
            this.mRunning = false;
            this.mJankMonitoringListener = null;
        }
    }
}
