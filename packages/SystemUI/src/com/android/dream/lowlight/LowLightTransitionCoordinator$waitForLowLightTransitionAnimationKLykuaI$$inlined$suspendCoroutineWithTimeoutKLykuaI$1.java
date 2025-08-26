package com.android.dream.lowlight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.dream.lowlight.util.TruncatedInterpolator;
import com.android.systemui.dreams.DreamOverlayAnimationsController;
import com.android.systemui.dreams.DreamOverlayContainerViewController;
import com.android.systemui.log.core.Logger;
import com.google.android.systemui.lowlightclock.LowLightClockDreamService;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;

/* renamed from: com.android.dream.lowlight.LowLightTransitionCoordinator$waitForLowLightTransitionAnimation-KLykuaI$$inlined$suspendCoroutineWithTimeout-KLykuaI$1, reason: invalid class name */
/* loaded from: classes.dex */
public final class LowLightTransitionCoordinator$waitForLowLightTransitionAnimationKLykuaI$$inlined$suspendCoroutineWithTimeoutKLykuaI$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $entering$inlined;
    Object L$0;
    int label;
    final /* synthetic */ LowLightTransitionCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LowLightTransitionCoordinator$waitForLowLightTransitionAnimationKLykuaI$$inlined$suspendCoroutineWithTimeoutKLykuaI$1(Continuation continuation, boolean z, LowLightTransitionCoordinator lowLightTransitionCoordinator) {
        super(2, continuation);
        this.$entering$inlined = z;
        this.this$0 = lowLightTransitionCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LowLightTransitionCoordinator$waitForLowLightTransitionAnimationKLykuaI$$inlined$suspendCoroutineWithTimeoutKLykuaI$1(continuation, this.$entering$inlined, this.this$0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LowLightTransitionCoordinator$waitForLowLightTransitionAnimationKLykuaI$$inlined$suspendCoroutineWithTimeoutKLykuaI$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        LowLightClockDreamService lowLightClockDreamService;
        DreamOverlayContainerViewController dreamOverlayContainerViewController;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        this.label = 1;
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
        cancellableContinuationImpl.initCancellability();
        boolean z = this.$entering$inlined;
        Animator animator = null;
        if (z && (dreamOverlayContainerViewController = this.this$0.mLowLightEnterListener) != null) {
            final DreamOverlayAnimationsController dreamOverlayAnimationsController = dreamOverlayContainerViewController.mDreamOverlayAnimationsController;
            dreamOverlayAnimationsController.cancelAnimations();
            AnimatorSet animatorSet = new AnimatorSet();
            Interpolator interpolator = Interpolators.EMPHASIZED;
            float f = dreamOverlayAnimationsController.mDreamInTranslationYDurationMs;
            long j = dreamOverlayAnimationsController.mDreamInComplicationsAnimDurationMs;
            Map map = dreamOverlayAnimationsController.mCurrentAlphaAtPosition;
            Float fValueOf = Float.valueOf(1.0f);
            animatorSet.playTogether(DreamOverlayAnimationsController.translationYAnimator$default(dreamOverlayAnimationsController, 0.0f, -dreamOverlayAnimationsController.mDreamInTranslationYDistance, j, new TruncatedInterpolator(interpolator, f, j), 16), DreamOverlayAnimationsController.alphaAnimator$default(dreamOverlayAnimationsController, ((Number) ((LinkedHashMap) map).getOrDefault(2, fValueOf)).floatValue(), 0.0f, dreamOverlayAnimationsController.mDreamInComplicationsAnimDurationMs, 2, null, 32), DreamOverlayAnimationsController.alphaAnimator$default(dreamOverlayAnimationsController, ((Number) ((LinkedHashMap) dreamOverlayAnimationsController.mCurrentAlphaAtPosition).getOrDefault(1, fValueOf)).floatValue(), 0.0f, dreamOverlayAnimationsController.mDreamInComplicationsAnimDurationMs, 1, null, 32));
            animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$startExitAnimations$lambda$7$$inlined$doOnEnd$1
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator2) {
                    DreamOverlayAnimationsController dreamOverlayAnimationsController2 = dreamOverlayAnimationsController;
                    dreamOverlayAnimationsController2.mAnimator = null;
                    DreamOverlayStateController dreamOverlayStateController = dreamOverlayAnimationsController2.mOverlayStateController;
                    dreamOverlayStateController.getClass();
                    dreamOverlayStateController.modifyState(1, 8);
                    Logger.d$default(dreamOverlayAnimationsController.logger, "Dream overlay exit animations finished.", null, 2, null);
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
            animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$startExitAnimations$lambda$7$$inlined$doOnCancel$1
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator2) {
                    Logger.d$default(dreamOverlayAnimationsController.logger, "Dream overlay exit animations canceled.", null, 2, null);
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
            Logger.d$default(dreamOverlayAnimationsController.logger, "Dream overlay exit animations started.", null, 2, null);
            dreamOverlayAnimationsController.mAnimator = animatorSet;
            dreamOverlayAnimationsController.mOverlayStateController.modifyState(2, 8);
            animator = (AnimatorSet) dreamOverlayAnimationsController.mAnimator;
        } else if (!z && (lowLightClockDreamService = this.this$0.mLowLightExitListener) != null) {
            Animator animatorProvideAnimationOut = lowLightClockDreamService.mAnimationProvider.provideAnimationOut(lowLightClockDreamService.mTextClock, lowLightClockDreamService.mChargingStatusTextView);
            lowLightClockDreamService.mAnimationOut = animatorProvideAnimationOut;
            animatorProvideAnimationOut.start();
            animator = lowLightClockDreamService.mAnimationOut;
        }
        if (animator == null) {
            int i2 = Result.$r8$clinit;
            cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
        } else {
            animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.dream.lowlight.LowLightTransitionCoordinator$waitForLowLightTransitionAnimation$2$listener$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator2) {
                    cancellableContinuationImpl.cancel(null);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator2) {
                    CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                    int i3 = Result.$r8$clinit;
                    cancellableContinuation.resumeWith(Unit.INSTANCE);
                }
            });
        }
        Object result = cancellableContinuationImpl.getResult();
        return result == coroutineSingletons ? coroutineSingletons : result;
    }
}
