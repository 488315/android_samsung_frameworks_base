package com.android.systemui.dreams;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.DreamManager;
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
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController;
import com.android.systemui.ambient.touch.scrim.BouncerlessScrimController;
import com.android.systemui.ambient.touch.scrim.BouncerlessScrimController$$ExternalSyntheticLambda2;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.complication.ComplicationLayoutParams;
import com.android.systemui.doze.util.BurnInHelperKt;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.core.Logger;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.touch.TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda1;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class DreamOverlayContainerViewController extends ViewController {
    public boolean mAnyBouncerShowing;
    public final CoroutineDispatcher mBackgroundDispatcher;
    public final BlurUtils mBlurUtils;
    public boolean mBouncerAnimating;
    public final AnonymousClass2 mBouncerExpansionCallback;
    public final AnonymousClass1 mBouncerlessExpansionCallback;
    public final BouncerlessScrimController mBouncerlessScrimController;
    public final long mBurnInProtectionUpdateInterval;
    public final CommunalInteractor mCommunalInteractor;
    public final ComplicationHostViewController mComplicationHostViewController;
    public final DreamManager mDreamManager;
    public final DreamOverlayAnimationsController mDreamOverlayAnimationsController;
    public final ViewGroup mDreamOverlayContentView;
    public final int mDreamOverlayMaxTranslationY;
    public final DreamOverlayStateController.Callback mDreamOverlayStateCallback;
    public boolean mExitingLowLight;
    public final Handler mHandler;
    public long mJitterStartTimeMillis;
    public final KeyguardTransitionInteractor mKeyguardTransitionInteractor;
    public final LowLightTransitionCoordinator mLowLightTransitionCoordinator;
    public final int mMaxBurnInOffset;
    public final long mMillisUntilFullJitter;
    public final PrimaryBouncerCallbackInteractor mPrimaryBouncerCallbackInteractor;
    public final ShadeInteractor mShadeInteractor;
    public final DreamOverlayStateController mStateController;
    public final AmbientStatusBarViewController mStatusBarViewController;
    public final TouchInsetManager.TouchInsetSession mTouchInsetSession;
    public boolean mWakingUpFromSwipe;

    /* renamed from: com.android.systemui.dreams.DreamOverlayContainerViewController$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public static void $r8$lambda$jy_aQFBFeBC9Yqw2PjoN8Ww1J4Q(DreamOverlayContainerViewController dreamOverlayContainerViewController) {
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
        dreamOverlayContainerViewController.mHandler.postDelayed(new DreamOverlayContainerViewController$$ExternalSyntheticLambda0(dreamOverlayContainerViewController), dreamOverlayContainerViewController.mBurnInProtectionUpdateInterval);
    }

    /* renamed from: -$$Nest$mupdateTransitionState, reason: not valid java name */
    public static void m1931$$Nest$mupdateTransitionState(DreamOverlayContainerViewController dreamOverlayContainerViewController, float f) {
        float aboutToShowBouncerProgress;
        dreamOverlayContainerViewController.getClass();
        Iterator it = Arrays.asList(1, 2).iterator();
        while (true) {
            float f2 = 1.0f;
            if (!it.hasNext()) {
                break;
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
            dreamOverlayContainerViewController.mComplicationHostViewController.getViewsAtPosition(intValue).forEach(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController$$ExternalSyntheticLambda4
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
        ViewRootImpl viewRootImpl = ((DreamOverlayContainerView) dreamOverlayContainerViewController.mView).getViewRootImpl();
        dreamOverlayContainerViewController.mBlurUtils.blurRadiusOfRatio(1.0f - BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f));
        if (viewRootImpl != null) {
            viewRootImpl.getSurfaceControl().isValid();
        }
    }

    public DreamOverlayContainerViewController(DreamOverlayContainerView dreamOverlayContainerView, ComplicationHostViewController complicationHostViewController, ViewGroup viewGroup, View view, AmbientStatusBarViewController ambientStatusBarViewController, LowLightTransitionCoordinator lowLightTransitionCoordinator, TouchInsetManager.TouchInsetSession touchInsetSession, BlurUtils blurUtils, Handler handler, CoroutineDispatcher coroutineDispatcher, Resources resources, int i, long j, long j2, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, DreamOverlayAnimationsController dreamOverlayAnimationsController, DreamOverlayStateController dreamOverlayStateController, BouncerlessScrimController bouncerlessScrimController, KeyguardTransitionInteractor keyguardTransitionInteractor, ShadeInteractor shadeInteractor, CommunalInteractor communalInteractor, DreamManager dreamManager) {
        super(dreamOverlayContainerView);
        this.mBouncerlessExpansionCallback = new AnonymousClass1();
        this.mBouncerExpansionCallback = new PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController.2
            @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onExpansionChanged(float f) {
                DreamOverlayContainerViewController dreamOverlayContainerViewController = DreamOverlayContainerViewController.this;
                if (dreamOverlayContainerViewController.mBouncerAnimating) {
                    DreamOverlayContainerViewController.m1931$$Nest$mupdateTransitionState(dreamOverlayContainerViewController, f);
                }
            }

            @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onFullyHidden() {
                DreamOverlayContainerViewController.this.mBouncerAnimating = false;
            }

            @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onStartingToHide() {
                DreamOverlayContainerViewController.this.mBouncerAnimating = true;
            }

            @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onStartingToShow() {
                DreamOverlayContainerViewController.this.mBouncerAnimating = true;
            }

            @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onVisibilityChanged(boolean z) {
                if (z) {
                    return;
                }
                DreamOverlayContainerViewController.m1931$$Nest$mupdateTransitionState(DreamOverlayContainerViewController.this, 1.0f);
            }
        };
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController.3
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onExitLowLight() {
                DreamOverlayContainerViewController.this.mExitingLowLight = true;
            }
        };
        this.mDreamOverlayContentView = viewGroup;
        this.mStatusBarViewController = ambientStatusBarViewController;
        this.mTouchInsetSession = touchInsetSession;
        this.mBlurUtils = blurUtils;
        this.mDreamOverlayAnimationsController = dreamOverlayAnimationsController;
        this.mStateController = dreamOverlayStateController;
        this.mCommunalInteractor = communalInteractor;
        this.mLowLightTransitionCoordinator = lowLightTransitionCoordinator;
        this.mBouncerlessScrimController = bouncerlessScrimController;
        this.mKeyguardTransitionInteractor = keyguardTransitionInteractor;
        this.mShadeInteractor = shadeInteractor;
        this.mComplicationHostViewController = complicationHostViewController;
        this.mDreamOverlayMaxTranslationY = resources.getDimensionPixelSize(R.dimen.dream_overlay_y_offset);
        Flags.communalHub();
        viewGroup.addView(complicationHostViewController.getView(), new ViewGroup.LayoutParams(-1, -1));
        this.mHandler = handler;
        this.mBackgroundDispatcher = coroutineDispatcher;
        this.mMaxBurnInOffset = i;
        this.mBurnInProtectionUpdateInterval = j;
        this.mMillisUntilFullJitter = j2;
        this.mPrimaryBouncerCallbackInteractor = primaryBouncerCallbackInteractor;
        this.mDreamManager = dreamManager;
    }

    public final View getContainerView() {
        return this.mView;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        this.mStateController.addCallback(this.mDreamOverlayStateCallback);
        this.mStatusBarViewController.init();
        this.mComplicationHostViewController.init();
        T t = this.mView;
        DreamOverlayAnimationsController dreamOverlayAnimationsController = this.mDreamOverlayAnimationsController;
        dreamOverlayAnimationsController.view = t;
        RepeatWhenAttachedKt.repeatWhenAttached(t, EmptyCoroutineContext.INSTANCE, new DreamOverlayAnimationsController$init$1(dreamOverlayAnimationsController, null));
        this.mLowLightTransitionCoordinator.getClass();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mWakingUpFromSwipe = false;
        this.mJitterStartTimeMillis = System.currentTimeMillis();
        this.mHandler.postDelayed(new DreamOverlayContainerViewController$$ExternalSyntheticLambda0(this), this.mBurnInProtectionUpdateInterval);
        this.mPrimaryBouncerCallbackInteractor.addBouncerExpansionCallback(this.mBouncerExpansionCallback);
        BouncerlessScrimController bouncerlessScrimController = this.mBouncerlessScrimController;
        bouncerlessScrimController.mExecutor.execute(new BouncerlessScrimController$$ExternalSyntheticLambda2(bouncerlessScrimController, this.mBouncerlessExpansionCallback, 1));
        Region obtain = Region.obtain();
        ((DreamOverlayContainerView) this.mView).getRootSurfaceControl().setTouchableRegion(obtain);
        obtain.recycle();
        if (android.service.dreams.Flags.dreamHandlesBeingObscured()) {
            T t = this.mView;
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(JavaAdapterKt.combineFlows(FlowKt.distinctUntilChanged(new KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1(this.mKeyguardTransitionInteractor.finishedKeyguardState, new DreamOverlayContainerViewController$$ExternalSyntheticLambda1())), ((ShadeInteractorImpl) this.mShadeInteractor).baseShadeInteractor.isAnyExpanded(), this.mCommunalInteractor.isCommunalShowing, new Function3() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Boolean bool = (Boolean) obj;
                    Boolean bool2 = (Boolean) obj2;
                    Boolean bool3 = (Boolean) obj3;
                    DreamOverlayContainerViewController dreamOverlayContainerViewController = DreamOverlayContainerViewController.this;
                    dreamOverlayContainerViewController.getClass();
                    dreamOverlayContainerViewController.mAnyBouncerShowing = bool.booleanValue();
                    return Boolean.valueOf(bool.booleanValue() || bool2.booleanValue() || bool3.booleanValue());
                }
            }));
            final DreamManager dreamManager = this.mDreamManager;
            Objects.requireNonNull(dreamManager);
            JavaAdapterKt.collectFlow(t, distinctUntilChanged, new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    dreamManager.setDreamIsObscured(((Boolean) obj).booleanValue());
                }
            }, this.mBackgroundDispatcher);
        }
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
        dreamOverlayAnimationsController.cancelAnimations();
        dreamOverlayAnimationsController$startEntryAnimations$1.getClass();
        AnimatorSet animatorSet = new AnimatorSet();
        final int i = 3;
        Animator[] animatorArr = new Animator[3];
        final View view = dreamOverlayAnimationsController.view;
        if (view == null) {
            view = null;
        }
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
                BlurUtils blurUtils = DreamOverlayAnimationsController.this.mBlurUtils;
                ViewRootImpl viewRootImpl = view.getViewRootImpl();
                float f3 = DreamOverlayAnimationsController.this.mCurrentBlurRadius;
                blurUtils.getClass();
                if (viewRootImpl != null) {
                    viewRootImpl.getSurfaceControl().isValid();
                }
            }
        });
        animatorArr[0] = ofFloat;
        Interpolator interpolator2 = Interpolators.LINEAR;
        final float f3 = 1.0f;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat2.setDuration(dreamOverlayAnimationsController.mDreamInComplicationsAnimDurationMs);
        ofFloat2.setStartDelay(0L);
        ofFloat2.setInterpolator(interpolator2);
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
            public final void onAnimationEnd(Animator animator) {
                DreamOverlayAnimationsController dreamOverlayAnimationsController2 = DreamOverlayAnimationsController.this;
                dreamOverlayAnimationsController2.mAnimator = null;
                dreamOverlayAnimationsController2.mOverlayStateController.modifyState(2, 4);
                Logger.d$default(DreamOverlayAnimationsController.this.logger, "Dream overlay entry animations finished.", null, 2, null);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$startEntryAnimations$lambda$2$$inlined$doOnCancel$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                Logger.d$default(DreamOverlayAnimationsController.this.logger, "Dream overlay entry animations canceled.", null, 2, null);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        animatorSet.start();
        Logger.d$default(dreamOverlayAnimationsController.logger, "Dream overlay entry animations started.", null, 2, null);
        dreamOverlayAnimationsController.mAnimator = animatorSet;
        this.mExitingLowLight = false;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mPrimaryBouncerCallbackInteractor.expansionCallbacks.remove(this.mBouncerExpansionCallback);
        BouncerlessScrimController bouncerlessScrimController = this.mBouncerlessScrimController;
        bouncerlessScrimController.mExecutor.execute(new BouncerlessScrimController$$ExternalSyntheticLambda2(bouncerlessScrimController, this.mBouncerlessExpansionCallback, 0));
        TouchInsetManager.TouchInsetSession touchInsetSession = this.mTouchInsetSession;
        touchInsetSession.mExecutor.execute(new TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda1(touchInsetSession, 0));
        this.mDreamOverlayAnimationsController.cancelAnimations();
    }
}
