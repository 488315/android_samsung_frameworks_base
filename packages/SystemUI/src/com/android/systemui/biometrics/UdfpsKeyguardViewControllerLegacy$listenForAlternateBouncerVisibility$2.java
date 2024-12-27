package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.View;
import android.widget.ImageView;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

final class UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerVisibility$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UdfpsKeyguardViewControllerLegacy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerVisibility$2(UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy, Continuation continuation) {
        super(2, continuation);
        this.this$0 = udfpsKeyguardViewControllerLegacy;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerVisibility$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerVisibility$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = udfpsKeyguardViewControllerLegacy.alternateBouncerInteractor.isVisible;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerVisibility$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy2 = UdfpsKeyguardViewControllerLegacy.this;
                    if (udfpsKeyguardViewControllerLegacy2.showingUdfpsBouncer != booleanValue) {
                        boolean shouldPauseAuth = udfpsKeyguardViewControllerLegacy2.shouldPauseAuth();
                        udfpsKeyguardViewControllerLegacy2.showingUdfpsBouncer = booleanValue;
                        if (booleanValue) {
                            final UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy = udfpsKeyguardViewControllerLegacy2.view;
                            if (shouldPauseAuth && !udfpsKeyguardViewLegacy.mBackgroundInAnimator.isRunning() && udfpsKeyguardViewLegacy.mFullyInflated) {
                                AnimatorSet animatorSet = new AnimatorSet();
                                udfpsKeyguardViewLegacy.mBackgroundInAnimator = animatorSet;
                                animatorSet.playTogether(ObjectAnimator.ofFloat(udfpsKeyguardViewLegacy.mBgProtection, (Property<ImageView, Float>) View.ALPHA, 0.0f, 1.0f), ObjectAnimator.ofFloat(udfpsKeyguardViewLegacy.mBgProtection, (Property<ImageView, Float>) View.SCALE_X, 0.0f, 1.0f), ObjectAnimator.ofFloat(udfpsKeyguardViewLegacy.mBgProtection, (Property<ImageView, Float>) View.SCALE_Y, 0.0f, 1.0f));
                                udfpsKeyguardViewLegacy.mBackgroundInAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                                udfpsKeyguardViewLegacy.mBackgroundInAnimator.setDuration(500L);
                                final Runnable runnable = null;
                                udfpsKeyguardViewLegacy.mBackgroundInAnimator.addListener(new AnimatorListenerAdapter(udfpsKeyguardViewLegacy, runnable) { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewLegacy.1
                                    public final /* synthetic */ Runnable val$onEndAnimation;

                                    public AnonymousClass1(final UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy2, final Runnable runnable2) {
                                        this.val$onEndAnimation = runnable2;
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                        Runnable runnable2 = this.val$onEndAnimation;
                                        if (runnable2 != null) {
                                            runnable2.run();
                                        }
                                    }
                                });
                                udfpsKeyguardViewLegacy2.mBackgroundInAnimator.start();
                            }
                            udfpsKeyguardViewLegacy2.announceForAccessibility(udfpsKeyguardViewLegacy2.getContext().getString(R.string.accessibility_fingerprint_bouncer));
                        }
                        udfpsKeyguardViewControllerLegacy2.updateAlpha();
                        udfpsKeyguardViewControllerLegacy2.updatePauseAuth();
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
