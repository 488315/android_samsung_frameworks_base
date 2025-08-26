package com.android.systemui.unfold.progress;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Trace;
import android.util.AndroidRuntimeException;
import android.util.FloatProperty;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.dynamicanimation.animation.AnimationHandler;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.progress.UnfoldFrameCallbackScheduler;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.DeviceFoldStateProviderKt;
import com.android.systemui.unfold.updates.FoldStateProvider;
import java.util.ArrayList;
import java.util.List;
import kotlin.text.StringsKt__IndentKt;

/* loaded from: classes3.dex */
public final class PhysicsBasedUnfoldTransitionProgressProvider implements UnfoldTransitionProgressProvider, FoldStateProvider.FoldUpdatesListener, DynamicAnimation.OnAnimationEndListener {
    public ValueAnimator cannedAnimator;
    public final Interpolator emphasizedInterpolator;
    public final FoldStateProvider foldStateProvider;
    public boolean isAnimatedCancelRunning;
    public boolean isTransitionRunning;
    public final List listeners;
    public final Handler progressHandler;
    public final UnfoldFrameCallbackScheduler.Factory schedulerFactory;
    public final SpringAnimation springAnimation;
    public float transitionProgress;

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
            ((PhysicsBasedUnfoldTransitionProgressProvider) obj).setTransitionProgress$1(f);
        }
    }

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

    public PhysicsBasedUnfoldTransitionProgressProvider(Context context, UnfoldFrameCallbackScheduler.Factory factory, FoldStateProvider foldStateProvider, Handler handler) {
        this.schedulerFactory = factory;
        this.foldStateProvider = foldStateProvider;
        this.progressHandler = handler;
        this.emphasizedInterpolator = AnimationUtils.loadInterpolator(context, R.interpolator.fast_out_extra_slow_in);
        AnimationProgressProperty animationProgressProperty = AnimationProgressProperty.INSTANCE;
        SpringAnimation springAnimation = new SpringAnimation(this, new FloatPropertyCompat.AnonymousClass1(animationProgressProperty.getName(), animationProgressProperty));
        springAnimation.addEndListener(this);
        this.springAnimation = springAnimation;
        this.listeners = new ArrayList();
        handler.post(new Runnable() { // from class: com.android.systemui.unfold.progress.PhysicsBasedUnfoldTransitionProgressProvider.1
            @Override // java.lang.Runnable
            public final void run() {
                PhysicsBasedUnfoldTransitionProgressProvider physicsBasedUnfoldTransitionProgressProvider = PhysicsBasedUnfoldTransitionProgressProvider.this;
                SpringAnimation springAnimation2 = physicsBasedUnfoldTransitionProgressProvider.springAnimation;
                UnfoldFrameCallbackScheduler unfoldFrameCallbackSchedulerCreate = physicsBasedUnfoldTransitionProgressProvider.schedulerFactory.create();
                AnimationHandler animationHandler = springAnimation2.mAnimationHandler;
                if (animationHandler == null || animationHandler.mScheduler != unfoldFrameCallbackSchedulerCreate) {
                    if (springAnimation2.mRunning) {
                        throw new AndroidRuntimeException("Animations are still running and the animationhandler should not be set at this timming");
                    }
                    springAnimation2.mAnimationHandler = new AnimationHandler(unfoldFrameCallbackSchedulerCreate);
                }
                PhysicsBasedUnfoldTransitionProgressProvider physicsBasedUnfoldTransitionProgressProvider2 = PhysicsBasedUnfoldTransitionProgressProvider.this;
                ((DeviceFoldStateProvider) physicsBasedUnfoldTransitionProgressProvider2.foldStateProvider).addCallback(physicsBasedUnfoldTransitionProgressProvider2);
                ((DeviceFoldStateProvider) PhysicsBasedUnfoldTransitionProgressProvider.this.foldStateProvider).start();
            }
        });
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        final UnfoldTransitionProgressProvider.TransitionProgressListener transitionProgressListener = (UnfoldTransitionProgressProvider.TransitionProgressListener) obj;
        this.progressHandler.post(new Runnable() { // from class: com.android.systemui.unfold.progress.PhysicsBasedUnfoldTransitionProgressProvider.addCallback.1
            @Override // java.lang.Runnable
            public final void run() {
                ((ArrayList) PhysicsBasedUnfoldTransitionProgressProvider.this.listeners).add(transitionProgressListener);
            }
        });
    }

    public final void assertInProgressThread$1() {
        Handler handler = this.progressHandler;
        if (handler.getLooper().isCurrentThread()) {
            return;
        }
        Thread thread = handler.getLooper().getThread();
        Thread threadCurrentThread = Thread.currentThread();
        throw new IllegalStateException(StringsKt__IndentKt.trimMargin$default("should be called from the progress thread.\n                progressThread=" + thread + " tid=" + thread.getId() + "\n                Thread.currentThread()=" + threadCurrentThread + " tid=" + threadCurrentThread.getId()).toString());
    }

    public final void cancelTransition(float f, boolean z) {
        assertInProgressThread$1();
        boolean z2 = this.isTransitionRunning;
        int i = 0;
        SpringAnimation springAnimation = this.springAnimation;
        if (!z2 || !z) {
            setTransitionProgress$1(f);
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
            ArrayList arrayList = (ArrayList) this.listeners;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) obj).onTransitionFinished();
            }
            Log.d("PhysicsBasedUnfoldTransitionProgressProvider", "onTransitionFinished");
            return;
        }
        if (f == 1.0f && !this.isAnimatedCancelRunning) {
            ArrayList arrayList2 = (ArrayList) this.listeners;
            int size2 = arrayList2.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) obj2).onTransitionFinishing();
            }
        }
        this.isAnimatedCancelRunning = true;
        assertInProgressThread$1();
        ValueAnimator valueAnimator3 = this.cannedAnimator;
        if (valueAnimator3 != null) {
            valueAnimator3.cancel();
        }
        this.cannedAnimator = null;
        springAnimation.removeEndListener(this);
        springAnimation.cancel();
        springAnimation.addEndListener(this);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, AnimationProgressProperty.INSTANCE, this.transitionProgress, 1.0f);
        objectAnimatorOfFloat.addListener(new CannedAnimationListener());
        objectAnimatorOfFloat.setDuration((long) ((1.0f - this.transitionProgress) * 1000.0f));
        objectAnimatorOfFloat.setInterpolator(this.emphasizedInterpolator);
        objectAnimatorOfFloat.start();
        this.cannedAnimator = objectAnimatorOfFloat;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        if (this.isAnimatedCancelRunning) {
            cancelTransition(f, false);
        }
    }

    @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
    public final void onFoldUpdate(int i) {
        assertInProgressThread$1();
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

    /* JADX WARN: Removed duplicated region for block: B:9:0x0014 A[PHI: r0
      0x0014: PHI (r0v5 float) = (r0v3 float), (r0v4 float) binds: [B:8:0x0012, B:11:0x001a] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onHingeAngleUpdate(float f) {
        assertInProgressThread$1();
        if (!this.isTransitionRunning || this.isAnimatedCancelRunning) {
            return;
        }
        float f2 = f / 165.0f;
        float f3 = 0.0f;
        if (f2 < 0.0f) {
            f2 = f3;
        } else {
            f3 = 1.0f;
            if (f2 > 1.0f) {
            }
        }
        this.springAnimation.animateToFinalPosition(f2);
    }

    @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
    public final void onUnfoldedScreenAvailable() {
        Integer num;
        startTransition(0.0f);
        DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) this.foldStateProvider;
        if (deviceFoldStateProvider.isFolded) {
            return;
        }
        Integer num2 = deviceFoldStateProvider.lastFoldUpdate;
        if ((num2 != null && num2.intValue() == 3) || ((num = deviceFoldStateProvider.lastFoldUpdate) != null && num.intValue() == 2)) {
            cancelTransition(1.0f, true);
        }
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        final UnfoldTransitionProgressProvider.TransitionProgressListener transitionProgressListener = (UnfoldTransitionProgressProvider.TransitionProgressListener) obj;
        this.progressHandler.post(new Runnable() { // from class: com.android.systemui.unfold.progress.PhysicsBasedUnfoldTransitionProgressProvider.removeCallback.1
            @Override // java.lang.Runnable
            public final void run() {
                ((ArrayList) PhysicsBasedUnfoldTransitionProgressProvider.this.listeners).remove(transitionProgressListener);
            }
        });
    }

    public final void setTransitionProgress$1(float f) {
        assertInProgressThread$1();
        if (this.isTransitionRunning) {
            ArrayList arrayList = (ArrayList) this.listeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) obj).onTransitionProgress(f);
            }
        }
        this.transitionProgress = f;
    }

    public final void startTransition(float f) {
        assertInProgressThread$1();
        if (!this.isTransitionRunning) {
            Trace.beginSection("PhysicsBasedUnfoldTransitionProgressProvider#onStartTransition");
            ArrayList arrayList = (ArrayList) this.listeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) obj).onTransitionStarted();
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
