package com.android.systemui.unfold.progress;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Trace;
import android.util.FloatProperty;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.DeviceFoldStateProviderKt;
import com.android.systemui.unfold.updates.FoldStateProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PhysicsBasedUnfoldTransitionProgressProvider implements UnfoldTransitionProgressProvider, FoldStateProvider.FoldUpdatesListener, DynamicAnimation.OnAnimationEndListener {
    public ValueAnimator cannedAnimator;
    public final Interpolator emphasizedInterpolator;
    public final FoldStateProvider foldStateProvider;
    public boolean isAnimatedCancelRunning;
    public boolean isTransitionRunning;
    public final List listeners;
    public final SpringAnimation springAnimation;
    public float transitionProgress;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimationProgressProperty extends FloatProperty {
        public static final AnimationProgressProperty INSTANCE = new AnimationProgressProperty();

        private AnimationProgressProperty() {
            super("animation_progress");
        }

        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((PhysicsBasedUnfoldTransitionProgressProvider) obj).transitionProgress);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            ((PhysicsBasedUnfoldTransitionProgressProvider) obj).setTransitionProgress(f);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CannedAnimationListener extends AnimatorListenerAdapter {
        public CannedAnimationListener() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            PhysicsBasedUnfoldTransitionProgressProvider.this.cancelTransition(1.0f, false);
            Trace.endAsyncSection("PhysicsBasedUnfoldTransitionProgressProvider#cannedAnimatorRunning", 0);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            Trace.beginAsyncSection("PhysicsBasedUnfoldTransitionProgressProvider#cannedAnimatorRunning", 0);
        }
    }

    public PhysicsBasedUnfoldTransitionProgressProvider(Context context, FoldStateProvider foldStateProvider) {
        this.foldStateProvider = foldStateProvider;
        this.emphasizedInterpolator = AnimationUtils.loadInterpolator(context, R.interpolator.fast_out_extra_slow_in);
        AnimationProgressProperty animationProgressProperty = AnimationProgressProperty.INSTANCE;
        SpringAnimation springAnimation = new SpringAnimation(this, new FloatPropertyCompat.C01991(animationProgressProperty.getName(), animationProgressProperty));
        springAnimation.addEndListener(this);
        this.springAnimation = springAnimation;
        this.listeners = new ArrayList();
        DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) foldStateProvider;
        deviceFoldStateProvider.addCallback(this);
        deviceFoldStateProvider.start();
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        ((ArrayList) this.listeners).add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public final void cancelTransition(float f, boolean z) {
        boolean z2 = this.isTransitionRunning;
        List list = this.listeners;
        SpringAnimation springAnimation = this.springAnimation;
        if (!z2 || !z) {
            setTransitionProgress(f);
            this.isAnimatedCancelRunning = false;
            this.isTransitionRunning = false;
            springAnimation.cancel();
            ValueAnimator valueAnimator = this.cannedAnimator;
            if (valueAnimator != null) {
                valueAnimator.removeAllListeners();
            }
            ValueAnimator valueAnimator2 = this.cannedAnimator;
            if (valueAnimator2 != null) {
                valueAnimator2.cancel();
            }
            this.cannedAnimator = null;
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionFinished();
            }
            Log.d("PhysicsBasedUnfoldTransitionProgressProvider", "onTransitionFinished");
            return;
        }
        if ((f == 1.0f) && !this.isAnimatedCancelRunning) {
            Iterator it2 = ((ArrayList) list).iterator();
            while (it2.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it2.next()).onTransitionFinishing();
            }
        }
        this.isAnimatedCancelRunning = true;
        ValueAnimator valueAnimator3 = this.cannedAnimator;
        if (valueAnimator3 != null) {
            valueAnimator3.cancel();
        }
        this.cannedAnimator = null;
        springAnimation.removeEndListener(this);
        springAnimation.cancel();
        springAnimation.addEndListener(this);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, AnimationProgressProperty.INSTANCE, this.transitionProgress, 1.0f);
        ofFloat.addListener(new CannedAnimationListener());
        ofFloat.setDuration((long) ((1.0f - this.transitionProgress) * 1000.0f));
        ofFloat.setInterpolator(this.emphasizedInterpolator);
        ofFloat.start();
        this.cannedAnimator = ofFloat;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        if (this.isAnimatedCancelRunning) {
            cancelTransition(f, false);
        }
    }

    @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
    public final void onFoldUpdate(int i) {
        if (i != 1) {
            if (i == 2 || i == 3) {
                if (this.isTransitionRunning) {
                    cancelTransition(1.0f, true);
                }
            } else if (i == 4) {
                cancelTransition(0.0f, false);
            }
        } else if (!this.isTransitionRunning) {
            startTransition(1.0f);
        } else if (this.isAnimatedCancelRunning) {
            this.isAnimatedCancelRunning = false;
            this.springAnimation.animateToFinalPosition(1.0f);
            ValueAnimator valueAnimator = this.cannedAnimator;
            if (valueAnimator != null) {
                valueAnimator.removeAllListeners();
            }
            ValueAnimator valueAnimator2 = this.cannedAnimator;
            if (valueAnimator2 != null) {
                valueAnimator2.cancel();
            }
            this.cannedAnimator = null;
        }
        Log.d("PhysicsBasedUnfoldTransitionProgressProvider", "onFoldUpdate = ".concat(DeviceFoldStateProviderKt.name(i)));
        Trace.setCounter("fold_update", i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0017, code lost:
    
        if (r3 > 1.0f) goto L9;
     */
    @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onHingeAngleUpdate(float f) {
        if (!this.isTransitionRunning || this.isAnimatedCancelRunning) {
            return;
        }
        float f2 = f / 165.0f;
        float f3 = f2 >= 0.0f ? 1.0f : 0.0f;
        f2 = f3;
        this.springAnimation.animateToFinalPosition(f2);
    }

    @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
    public final void onUnfoldedScreenAvailable() {
        Integer num;
        Integer num2;
        startTransition(0.0f);
        DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) this.foldStateProvider;
        if (!deviceFoldStateProvider.isFolded && (((num = deviceFoldStateProvider.lastFoldUpdate) != null && num.intValue() == 3) || ((num2 = deviceFoldStateProvider.lastFoldUpdate) != null && num2.intValue() == 2))) {
            cancelTransition(1.0f, true);
        }
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        ((ArrayList) this.listeners).remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public final void setTransitionProgress(float f) {
        if (this.isTransitionRunning) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionProgress(f);
            }
        }
        this.transitionProgress = f;
    }

    public final void startTransition(float f) {
        if (!this.isTransitionRunning) {
            Trace.beginSection("PhysicsBasedUnfoldTransitionProgressProvider#onStartTransition");
            Iterator it = ((ArrayList) this.listeners).iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionStarted();
            }
            Trace.endSection();
            this.isTransitionRunning = true;
            Log.d("PhysicsBasedUnfoldTransitionProgressProvider", "onTransitionStarted");
        }
        SpringForce springForce = new SpringForce();
        springForce.mFinalPosition = f;
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(600.0f);
        SpringAnimation springAnimation = this.springAnimation;
        springAnimation.mSpring = springForce;
        springAnimation.setMinimumVisibleChange(0.001f);
        springAnimation.setStartValue(f);
        springAnimation.mMinValue = 0.0f;
        springAnimation.mMaxValue = 1.0f;
        springAnimation.start();
    }
}
