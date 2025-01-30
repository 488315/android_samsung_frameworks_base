package com.android.systemui.dreams;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Region;
import android.os.Handler;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.dream.lowlight.LowLightTransitionCoordinator;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.systemui.R;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.complication.ComplicationLayoutParams;
import com.android.systemui.doze.util.BurnInHelperKt;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.touch.scrim.BouncerlessScrimController;
import com.android.systemui.dreams.touch.scrim.BouncerlessScrimController$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamOverlayContainerViewController extends ViewController {
    public final BlurUtils mBlurUtils;
    public boolean mBouncerAnimating;
    public final C12722 mBouncerExpansionCallback;
    public final long mBurnInProtectionUpdateInterval;
    public final ComplicationHostViewController mComplicationHostViewController;
    public final DreamOverlayAnimationsController mDreamOverlayAnimationsController;
    public final ViewGroup mDreamOverlayContentView;
    public final int mDreamOverlayMaxTranslationY;
    public final C12733 mDreamOverlayStateCallback;
    public boolean mExitingLowLight;
    public final Handler mHandler;
    public long mJitterStartTimeMillis;
    public final LowLightTransitionCoordinator mLowLightTransitionCoordinator;
    public final int mMaxBurnInOffset;
    public final long mMillisUntilFullJitter;
    public final PrimaryBouncerCallbackInteractor mPrimaryBouncerCallbackInteractor;
    public final DreamOverlayStateController mStateController;
    public final DreamOverlayStatusBarViewController mStatusBarViewController;
    public boolean mWakingUpFromSwipe;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.dreams.DreamOverlayContainerViewController$1 */
    public final class C12711 {
        public C12711() {
        }
    }

    public static void $r8$lambda$7fQChniGqN2SW5ngNLqKLDVuFIU(DreamOverlayContainerViewController dreamOverlayContainerViewController) {
        dreamOverlayContainerViewController.getClass();
        long currentTimeMillis = System.currentTimeMillis() - dreamOverlayContainerViewController.mJitterStartTimeMillis;
        long j = dreamOverlayContainerViewController.mMillisUntilFullJitter;
        int i = dreamOverlayContainerViewController.mMaxBurnInOffset;
        if (currentTimeMillis < j) {
            i = Math.round(MathUtils.lerp(0.0f, i, currentTimeMillis / j));
        }
        int i2 = i / 2;
        int burnInOffset = BurnInHelperKt.getBurnInOffset(i, true) - i2;
        int burnInOffset2 = BurnInHelperKt.getBurnInOffset(i, false) - i2;
        ((DreamOverlayContainerView) dreamOverlayContainerViewController.mView).setTranslationX(burnInOffset);
        ((DreamOverlayContainerView) dreamOverlayContainerViewController.mView).setTranslationY(burnInOffset2);
        dreamOverlayContainerViewController.mHandler.postDelayed(new DreamOverlayContainerViewController$$ExternalSyntheticLambda0(dreamOverlayContainerViewController, 2), dreamOverlayContainerViewController.mBurnInProtectionUpdateInterval);
    }

    /* renamed from: -$$Nest$mupdateTransitionState, reason: not valid java name */
    public static void m1531$$Nest$mupdateTransitionState(DreamOverlayContainerViewController dreamOverlayContainerViewController, float f) {
        float aboutToShowBouncerProgress;
        dreamOverlayContainerViewController.getClass();
        Iterator it = Arrays.asList(1, 2).iterator();
        while (true) {
            float f2 = 1.0f;
            if (!it.hasNext()) {
                ViewRootImpl viewRootImpl = ((DreamOverlayContainerView) dreamOverlayContainerViewController.mView).getViewRootImpl();
                float aboutToShowBouncerProgress2 = 1.0f - BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f);
                BlurUtils blurUtils = dreamOverlayContainerViewController.mBlurUtils;
                blurUtils.applyBlur(viewRootImpl, (int) blurUtils.blurRadiusOfRatio(aboutToShowBouncerProgress2), false);
                return;
            }
            int intValue = ((Integer) it.next()).intValue();
            Interpolator interpolator = Interpolators.LINEAR_OUT_SLOW_IN;
            if (intValue == 1) {
                int i = BouncerPanelExpansionCalculator.$r8$clinit;
                aboutToShowBouncerProgress = MathUtils.constrain((f - 0.94f) / 0.06f, 0.0f, 1.0f);
            } else {
                aboutToShowBouncerProgress = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f + 0.03f);
            }
            PathInterpolator pathInterpolator = (PathInterpolator) interpolator;
            final float interpolation = pathInterpolator.getInterpolation(aboutToShowBouncerProgress);
            if (intValue != 1) {
                f2 = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(0.03f + f);
            } else if (f < 0.98f) {
                f2 = ((double) f) < 0.93d ? 0.0f : (f - 0.93f) / 0.05f;
            }
            final float lerp = MathUtils.lerp(-dreamOverlayContainerViewController.mDreamOverlayMaxTranslationY, 0, pathInterpolator.getInterpolation(f2));
            dreamOverlayContainerViewController.mComplicationHostViewController.getViewsAtPosition(intValue).forEach(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    float f3 = interpolation;
                    float f4 = lerp;
                    View view = (View) obj;
                    view.setAlpha(f3);
                    view.setTranslationY(f4);
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.systemui.dreams.DreamOverlayContainerViewController$2] */
    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.dreams.DreamOverlayContainerViewController$3] */
    public DreamOverlayContainerViewController(DreamOverlayContainerView dreamOverlayContainerView, ComplicationHostViewController complicationHostViewController, ViewGroup viewGroup, DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, LowLightTransitionCoordinator lowLightTransitionCoordinator, BlurUtils blurUtils, Handler handler, Resources resources, int i, long j, long j2, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, DreamOverlayAnimationsController dreamOverlayAnimationsController, DreamOverlayStateController dreamOverlayStateController, BouncerlessScrimController bouncerlessScrimController) {
        super(dreamOverlayContainerView);
        C12711 c12711 = new C12711();
        this.mBouncerExpansionCallback = new PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController.2
            @Override // com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onExpansionChanged(float f) {
                DreamOverlayContainerViewController dreamOverlayContainerViewController = DreamOverlayContainerViewController.this;
                if (dreamOverlayContainerViewController.mBouncerAnimating) {
                    DreamOverlayContainerViewController.m1531$$Nest$mupdateTransitionState(dreamOverlayContainerViewController, f);
                }
            }

            @Override // com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onFullyHidden() {
                DreamOverlayContainerViewController.this.mBouncerAnimating = false;
            }

            @Override // com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onStartingToHide() {
                DreamOverlayContainerViewController.this.mBouncerAnimating = true;
            }

            @Override // com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onStartingToShow() {
                DreamOverlayContainerViewController.this.mBouncerAnimating = true;
            }

            @Override // com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onVisibilityChanged(boolean z) {
                if (z) {
                    return;
                }
                DreamOverlayContainerViewController.m1531$$Nest$mupdateTransitionState(DreamOverlayContainerViewController.this, 1.0f);
            }
        };
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController.3
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onExitLowLight() {
                DreamOverlayContainerViewController.this.mExitingLowLight = true;
            }
        };
        this.mDreamOverlayContentView = viewGroup;
        this.mStatusBarViewController = dreamOverlayStatusBarViewController;
        this.mBlurUtils = blurUtils;
        this.mDreamOverlayAnimationsController = dreamOverlayAnimationsController;
        this.mStateController = dreamOverlayStateController;
        this.mLowLightTransitionCoordinator = lowLightTransitionCoordinator;
        bouncerlessScrimController.getClass();
        bouncerlessScrimController.mExecutor.execute(new BouncerlessScrimController$$ExternalSyntheticLambda0(bouncerlessScrimController, c12711, 0));
        this.mComplicationHostViewController = complicationHostViewController;
        this.mDreamOverlayMaxTranslationY = resources.getDimensionPixelSize(R.dimen.dream_overlay_y_offset);
        viewGroup.addView(complicationHostViewController.mView, new ViewGroup.LayoutParams(-1, -1));
        this.mHandler = handler;
        this.mMaxBurnInOffset = i;
        this.mBurnInProtectionUpdateInterval = j;
        this.mMillisUntilFullJitter = j2;
        this.mPrimaryBouncerCallbackInteractor = primaryBouncerCallbackInteractor;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        this.mStateController.addCallback((DreamOverlayStateController.Callback) this.mDreamOverlayStateCallback);
        this.mStatusBarViewController.init();
        this.mComplicationHostViewController.init();
        View view = this.mView;
        DreamOverlayAnimationsController dreamOverlayAnimationsController = this.mDreamOverlayAnimationsController;
        dreamOverlayAnimationsController.view = view;
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new DreamOverlayAnimationsController$init$1(dreamOverlayAnimationsController, view, null));
        this.mLowLightTransitionCoordinator.getClass();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mWakingUpFromSwipe = false;
        this.mJitterStartTimeMillis = System.currentTimeMillis();
        this.mHandler.postDelayed(new DreamOverlayContainerViewController$$ExternalSyntheticLambda0(this, 0), this.mBurnInProtectionUpdateInterval);
        ArrayList arrayList = this.mPrimaryBouncerCallbackInteractor.expansionCallbacks;
        C12722 c12722 = this.mBouncerExpansionCallback;
        if (!arrayList.contains(c12722)) {
            arrayList.add(c12722);
        }
        Region obtain = Region.obtain();
        ((DreamOverlayContainerView) this.mView).getRootSurfaceControl().setTouchableRegion(obtain);
        obtain.recycle();
        if (this.mStateController.containsState(2)) {
            return;
        }
        boolean z = this.mExitingLowLight;
        DreamOverlayAnimationsController$startEntryAnimations$1 dreamOverlayAnimationsController$startEntryAnimations$1 = new Function0() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$startEntryAnimations$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new AnimatorSet();
            }
        };
        final DreamOverlayAnimationsController dreamOverlayAnimationsController = this.mDreamOverlayAnimationsController;
        Animator animator = dreamOverlayAnimationsController.mAnimator;
        if (animator != null) {
            animator.cancel();
        }
        dreamOverlayAnimationsController.mAnimator = null;
        dreamOverlayAnimationsController$startEntryAnimations$1.getClass();
        AnimatorSet animatorSet = new AnimatorSet();
        final int i = 3;
        Animator[] animatorArr = new Animator[3];
        View view = dreamOverlayAnimationsController.view;
        final View view2 = view != null ? view : null;
        float f = dreamOverlayAnimationsController.mDreamBlurRadius;
        Interpolator interpolator = Interpolators.EMPHASIZED_DECELERATE;
        final float f2 = 0.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, 0.0f);
        ofFloat.setDuration(dreamOverlayAnimationsController.mDreamInBlurAnimDurationMs);
        ofFloat.setStartDelay(0L);
        ofFloat.setInterpolator(interpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$blurAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DreamOverlayAnimationsController.this.mCurrentBlurRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                DreamOverlayAnimationsController.this.mBlurUtils.applyBlur(view2.getViewRootImpl(), (int) DreamOverlayAnimationsController.this.mCurrentBlurRadius, false);
            }
        });
        animatorArr[0] = ofFloat;
        Interpolator interpolator2 = Interpolators.LINEAR;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat2.setDuration(dreamOverlayAnimationsController.mDreamInComplicationsAnimDurationMs);
        ofFloat2.setStartDelay(0L);
        ofFloat2.setInterpolator(interpolator2);
        final float f3 = 1.0f;
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$alphaAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(final ValueAnimator valueAnimator) {
                final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = dreamOverlayAnimationsController;
                final float f4 = f3;
                final float f5 = f2;
                ComplicationLayoutParams.iteratePositions(i, new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$alphaAnimator$1$1.1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DreamOverlayAnimationsController.access$setElementsAlphaAtPosition(DreamOverlayAnimationsController.this, ((Float) valueAnimator.getAnimatedValue()).floatValue(), ((Number) obj).intValue(), f4 < f5);
                    }
                });
            }
        });
        animatorArr[1] = ofFloat2;
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(dreamOverlayAnimationsController.mDreamInTranslationYDistance * (z ? -1 : 1), 0.0f);
        ofFloat3.setDuration(dreamOverlayAnimationsController.mDreamInTranslationYDurationMs);
        ofFloat3.setStartDelay(0L);
        ofFloat3.setInterpolator(interpolator);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$translationYAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(final ValueAnimator valueAnimator) {
                final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = dreamOverlayAnimationsController;
                ComplicationLayoutParams.iteratePositions(i, new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$translationYAnimator$1$1.1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DreamOverlayAnimationsController.access$setElementsTranslationYAtPosition(DreamOverlayAnimationsController.this, ((Float) valueAnimator.getAnimatedValue()).floatValue(), ((Number) obj).intValue());
                    }
                });
            }
        });
        animatorArr[2] = ofFloat3;
        animatorSet.playTogether(animatorArr);
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$startEntryAnimations$lambda$2$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                DreamOverlayAnimationsController dreamOverlayAnimationsController2 = DreamOverlayAnimationsController.this;
                dreamOverlayAnimationsController2.mAnimator = null;
                DreamOverlayStateController dreamOverlayStateController = dreamOverlayAnimationsController2.mOverlayStateController;
                dreamOverlayStateController.getClass();
                dreamOverlayStateController.modifyState(2, 4);
                DreamOverlayAnimationsController.this.mLogger.m132d("Dream overlay entry animations finished.");
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
            }
        });
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$startEntryAnimations$lambda$2$$inlined$doOnCancel$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator2) {
                DreamOverlayAnimationsController.this.mLogger.m132d("Dream overlay entry animations canceled.");
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
            }
        });
        animatorSet.start();
        dreamOverlayAnimationsController.mLogger.m132d("Dream overlay entry animations started.");
        dreamOverlayAnimationsController.mAnimator = animatorSet;
        this.mExitingLowLight = false;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mHandler.removeCallbacks(new DreamOverlayContainerViewController$$ExternalSyntheticLambda0(this, 1));
        this.mPrimaryBouncerCallbackInteractor.expansionCallbacks.remove(this.mBouncerExpansionCallback);
        DreamOverlayAnimationsController dreamOverlayAnimationsController = this.mDreamOverlayAnimationsController;
        Animator animator = dreamOverlayAnimationsController.mAnimator;
        if (animator != null) {
            animator.cancel();
        }
        dreamOverlayAnimationsController.mAnimator = null;
    }
}
