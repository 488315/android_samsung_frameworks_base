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
import com.android.compose.animation.scene.OverlayKey;
import com.android.dream.lowlight.LowLightTransitionCoordinator;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.systemui.R;
import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController;
import com.android.systemui.ambient.touch.scrim.BouncerlessScrimController;
import com.android.systemui.ambient.touch.scrim.BouncerlessScrimController$$ExternalSyntheticLambda0;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.doze.util.BurnInHelperKt;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.core.Logger;
import com.android.systemui.scene.shared.model.Overlays;
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
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public class DreamOverlayContainerViewController extends ViewController {
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
    public DisposableHandle mFlowHandle;
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
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public static void $r8$lambda$jy_aQFBFeBC9Yqw2PjoN8Ww1J4Q(DreamOverlayContainerViewController dreamOverlayContainerViewController) {
        long jCurrentTimeMillis = System.currentTimeMillis() - dreamOverlayContainerViewController.mJitterStartTimeMillis;
        long j = dreamOverlayContainerViewController.mMillisUntilFullJitter;
        int iRound = dreamOverlayContainerViewController.mMaxBurnInOffset;
        if (jCurrentTimeMillis < j) {
            iRound = Math.round(MathUtils.lerp(0.0f, iRound, jCurrentTimeMillis / j));
        }
        int i = iRound / 2;
        int burnInOffset = BurnInHelperKt.getBurnInOffset(iRound, true) - i;
        int burnInOffset2 = BurnInHelperKt.getBurnInOffset(iRound, false) - i;
        ((DreamOverlayContainerView) dreamOverlayContainerViewController.mView).setTranslationX(burnInOffset);
        ((DreamOverlayContainerView) dreamOverlayContainerViewController.mView).setTranslationY(burnInOffset2);
        dreamOverlayContainerViewController.mHandler.postDelayed(new DreamOverlayContainerViewController$$ExternalSyntheticLambda0(dreamOverlayContainerViewController), dreamOverlayContainerViewController.mBurnInProtectionUpdateInterval);
    }

    /* renamed from: -$$Nest$mupdateTransitionState, reason: not valid java name */
    public static void m2567$$Nest$mupdateTransitionState(DreamOverlayContainerViewController dreamOverlayContainerViewController, float f) {
        float fAboutToShowBouncerProgress;
        dreamOverlayContainerViewController.getClass();
        Iterator it = Arrays.asList(1, 2).iterator();
        while (true) {
            float fAboutToShowBouncerProgress2 = 1.0f;
            if (!it.hasNext()) {
                break;
            }
            int iIntValue = ((Integer) it.next()).intValue();
            Interpolator interpolator = Interpolators.LINEAR_OUT_SLOW_IN;
            if (iIntValue == 1) {
                int i = BouncerPanelExpansionCalculator.$r8$clinit;
                fAboutToShowBouncerProgress = MathUtils.constrain((f - 0.94f) / 0.06f, 0.0f, 1.0f);
            } else {
                fAboutToShowBouncerProgress = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f + 0.03f);
            }
            PathInterpolator pathInterpolator = (PathInterpolator) interpolator;
            final float interpolation = pathInterpolator.getInterpolation(fAboutToShowBouncerProgress);
            if (iIntValue != 1) {
                fAboutToShowBouncerProgress2 = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(0.03f + f);
            } else if (f < 0.98f) {
                fAboutToShowBouncerProgress2 = ((double) f) < 0.93d ? 0.0f : (f - 0.93f) / 0.05f;
            }
            final float fLerp = MathUtils.lerp(-dreamOverlayContainerViewController.mDreamOverlayMaxTranslationY, 0, pathInterpolator.getInterpolation(fAboutToShowBouncerProgress2));
            dreamOverlayContainerViewController.mComplicationHostViewController.getViewsAtPosition(iIntValue).forEach(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    float f2 = interpolation;
                    float f3 = fLerp;
                    View view = (View) obj;
                    view.setAlpha(f2);
                    view.setTranslationY(f3);
                }
            });
        }
        ViewRootImpl viewRootImpl = ((DreamOverlayContainerView) dreamOverlayContainerViewController.mView).getViewRootImpl();
        dreamOverlayContainerViewController.mBlurUtils.blurRadiusOfRatio(1.0f - BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f));
        if (viewRootImpl != null) {
            viewRootImpl.getSurfaceControl().isValid();
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.dreams.DreamOverlayContainerViewController$2] */
    public DreamOverlayContainerViewController(DreamOverlayContainerView dreamOverlayContainerView, ComplicationHostViewController complicationHostViewController, ViewGroup viewGroup, AmbientStatusBarViewController ambientStatusBarViewController, LowLightTransitionCoordinator lowLightTransitionCoordinator, TouchInsetManager.TouchInsetSession touchInsetSession, BlurUtils blurUtils, Handler handler, CoroutineDispatcher coroutineDispatcher, Resources resources, int i, long j, long j2, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, DreamOverlayAnimationsController dreamOverlayAnimationsController, DreamOverlayStateController dreamOverlayStateController, BouncerlessScrimController bouncerlessScrimController, KeyguardTransitionInteractor keyguardTransitionInteractor, ShadeInteractor shadeInteractor, CommunalInteractor communalInteractor, DreamManager dreamManager) {
        super(dreamOverlayContainerView);
        this.mBouncerlessExpansionCallback = new AnonymousClass1();
        this.mBouncerExpansionCallback = new PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController.2
            @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onExpansionChanged(float f) {
                DreamOverlayContainerViewController dreamOverlayContainerViewController = DreamOverlayContainerViewController.this;
                if (dreamOverlayContainerViewController.mBouncerAnimating) {
                    DreamOverlayContainerViewController.m2567$$Nest$mupdateTransitionState(dreamOverlayContainerViewController, f);
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
                DreamOverlayContainerViewController.m2567$$Nest$mupdateTransitionState(DreamOverlayContainerViewController.this, 1.0f);
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
        viewGroup.addView(complicationHostViewController.getView(), new ViewGroup.LayoutParams(-1, -1));
        this.mHandler = handler;
        this.mBackgroundDispatcher = coroutineDispatcher;
        this.mMaxBurnInOffset = i;
        this.mBurnInProtectionUpdateInterval = j;
        this.mMillisUntilFullJitter = j2;
        this.mPrimaryBouncerCallbackInteractor = primaryBouncerCallbackInteractor;
        this.mDreamManager = dreamManager;
    }

    @Override // com.android.systemui.util.ViewController
    public final void destroy() {
        this.mStateController.removeCallback(this.mDreamOverlayStateCallback);
        this.mStatusBarViewController.destroy();
        this.mComplicationHostViewController.destroy();
        RepeatWhenAttachedKt.C09181 c09181 = this.mDreamOverlayAnimationsController.mLifecycleFlowHandle;
        if (c09181 != null) {
            c09181.dispose();
        }
        this.mLowLightTransitionCoordinator.mLowLightEnterListener = null;
        super.destroy();
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
        DreamOverlayAnimationsController$init$1 dreamOverlayAnimationsController$init$1 = new DreamOverlayAnimationsController$init$1(dreamOverlayAnimationsController, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        dreamOverlayAnimationsController.mLifecycleFlowHandle = RepeatWhenAttachedKt.repeatWhenAttached(t, EmptyCoroutineContext.INSTANCE, dreamOverlayAnimationsController$init$1);
        this.mLowLightTransitionCoordinator.mLowLightEnterListener = this;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mWakingUpFromSwipe = false;
        this.mJitterStartTimeMillis = System.currentTimeMillis();
        this.mHandler.postDelayed(new DreamOverlayContainerViewController$$ExternalSyntheticLambda0(this), this.mBurnInProtectionUpdateInterval);
        this.mPrimaryBouncerCallbackInteractor.addBouncerExpansionCallback(this.mBouncerExpansionCallback);
        BouncerlessScrimController bouncerlessScrimController = this.mBouncerlessScrimController;
        bouncerlessScrimController.mExecutor.execute(new BouncerlessScrimController$$ExternalSyntheticLambda0(bouncerlessScrimController, this.mBouncerlessExpansionCallback, 1));
        Region regionObtain = Region.obtain();
        ((DreamOverlayContainerView) this.mView).getRootSurfaceControl().setTouchableRegion(regionObtain);
        regionObtain.recycle();
        T t = this.mView;
        OverlayKey overlayKey = Overlays.Bouncer;
        KeyguardState keyguardState = KeyguardState.PRIMARY_BOUNCER;
        KeyguardTransitionInteractor keyguardTransitionInteractor = this.mKeyguardTransitionInteractor;
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(JavaAdapterKt.combineFlows(keyguardTransitionInteractor.isFinishedIn(keyguardState), keyguardTransitionInteractor.isFinishedIn$1(KeyguardState.ALTERNATE_BOUNCER), ((ShadeInteractorImpl) this.mShadeInteractor).baseShadeInteractor.isAnyExpanded(), this.mCommunalInteractor.isCommunalShowing, new Function4() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                Boolean bool = (Boolean) obj2;
                Boolean bool2 = (Boolean) obj3;
                Boolean bool3 = (Boolean) obj4;
                DreamOverlayContainerViewController dreamOverlayContainerViewController = this.f$0;
                dreamOverlayContainerViewController.getClass();
                boolean z = true;
                boolean z2 = ((Boolean) obj).booleanValue() || bool.booleanValue();
                dreamOverlayContainerViewController.mAnyBouncerShowing = z2;
                if (!z2 && !bool2.booleanValue() && !bool3.booleanValue()) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        }));
        final DreamManager dreamManager = this.mDreamManager;
        Objects.requireNonNull(dreamManager);
        this.mFlowHandle = JavaAdapterKt.collectFlow(t, flowDistinctUntilChanged, new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                dreamManager.setDreamIsObscured(((Boolean) obj).booleanValue());
            }
        }, this.mBackgroundDispatcher);
        if (this.mStateController.containsState(2)) {
            return;
        }
        boolean z = this.mExitingLowLight;
        final DreamOverlayAnimationsController dreamOverlayAnimationsController = this.mDreamOverlayAnimationsController;
        dreamOverlayAnimationsController.cancelAnimations();
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[3];
        final View view = dreamOverlayAnimationsController.view;
        if (view == null) {
            view = null;
        }
        float f = dreamOverlayAnimationsController.mDreamBlurRadius;
        Interpolator interpolator = Interpolators.EMPHASIZED_DECELERATE;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, 0.0f);
        valueAnimatorOfFloat.setDuration(dreamOverlayAnimationsController.mDreamInBlurAnimDurationMs);
        valueAnimatorOfFloat.setStartDelay(0L);
        valueAnimatorOfFloat.setInterpolator(interpolator);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$blurAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                dreamOverlayAnimationsController.mCurrentBlurRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                BlurUtils blurUtils = dreamOverlayAnimationsController.mBlurUtils;
                ViewRootImpl viewRootImpl = view.getViewRootImpl();
                float f2 = dreamOverlayAnimationsController.mCurrentBlurRadius;
                blurUtils.getClass();
                if (viewRootImpl != null) {
                    viewRootImpl.getSurfaceControl().isValid();
                }
            }
        });
        animatorArr[0] = valueAnimatorOfFloat;
        animatorArr[1] = DreamOverlayAnimationsController.alphaAnimator$default(dreamOverlayAnimationsController, 0.0f, 1.0f, dreamOverlayAnimationsController.mDreamInComplicationsAnimDurationMs, 0, Interpolators.LINEAR, 24);
        animatorArr[2] = DreamOverlayAnimationsController.translationYAnimator$default(dreamOverlayAnimationsController, dreamOverlayAnimationsController.mDreamInTranslationYDistance * (z ? -1 : 1), 0.0f, dreamOverlayAnimationsController.mDreamInTranslationYDurationMs, interpolator, 24);
        animatorSet.playTogether(animatorArr);
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$startEntryAnimations$lambda$3$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DreamOverlayAnimationsController dreamOverlayAnimationsController2 = dreamOverlayAnimationsController;
                dreamOverlayAnimationsController2.mAnimator = null;
                dreamOverlayAnimationsController2.mOverlayStateController.modifyState(2, 4);
                Logger.d$default(dreamOverlayAnimationsController.logger, "Dream overlay entry animations finished.", null, 2, null);
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
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$startEntryAnimations$lambda$3$$inlined$doOnCancel$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                Logger.d$default(dreamOverlayAnimationsController.logger, "Dream overlay entry animations canceled.", null, 2, null);
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
        DisposableHandle disposableHandle = this.mFlowHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
            this.mFlowHandle = null;
        }
        this.mHandler.removeCallbacksAndMessages(null);
        this.mPrimaryBouncerCallbackInteractor.expansionCallbacks.remove(this.mBouncerExpansionCallback);
        BouncerlessScrimController bouncerlessScrimController = this.mBouncerlessScrimController;
        bouncerlessScrimController.mExecutor.execute(new BouncerlessScrimController$$ExternalSyntheticLambda0(bouncerlessScrimController, this.mBouncerlessExpansionCallback, 0));
        TouchInsetManager.TouchInsetSession touchInsetSession = this.mTouchInsetSession;
        touchInsetSession.mExecutor.execute(new TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda1(touchInsetSession, 0));
        this.mDreamOverlayAnimationsController.cancelAnimations();
    }
}
