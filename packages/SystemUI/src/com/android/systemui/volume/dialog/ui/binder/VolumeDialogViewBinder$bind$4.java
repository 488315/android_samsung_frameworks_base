package com.android.systemui.volume.dialog.ui.binder;

import android.animation.Animator;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogViewBinder$bind$4 extends SuspendLambda implements Function3 {
    final /* synthetic */ ViewGroup $root;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ VolumeDialogViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogViewBinder$bind$4(VolumeDialogViewBinder volumeDialogViewBinder, ViewGroup viewGroup, Continuation continuation) {
        super(3, continuation);
        this.this$0 = volumeDialogViewBinder;
        this.$root = viewGroup;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        VolumeDialogViewBinder$bind$4 volumeDialogViewBinder$bind$4 = new VolumeDialogViewBinder$bind$4(this.this$0, this.$root, (Continuation) obj3);
        volumeDialogViewBinder$bind$4.L$0 = (Boolean) obj;
        volumeDialogViewBinder$bind$4.Z$0 = booleanValue;
        return volumeDialogViewBinder$bind$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object result;
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Boolean bool = (Boolean) this.L$0;
            boolean z2 = this.Z$0;
            VolumeDialogViewBinder volumeDialogViewBinder = this.this$0;
            ViewGroup viewGroup = this.$root;
            float f = z2 ? volumeDialogViewBinder.halfOpenedOffsetPx : 0.0f;
            boolean z3 = bool != null;
            this.Z$0 = z2;
            this.label = 1;
            volumeDialogViewBinder.getClass();
            if (z3) {
                final ViewPropertyAnimator translationY = viewGroup.animate().setDuration(150L).translationY(f);
                final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                translationY.start();
                final Animator.AnimatorListener animatorListener = null;
                translationY.setListener(new Animator.AnimatorListener() { // from class: com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt$suspendAnimate$2$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        SuspendAnimatorsKt.access$resumeIfCan(cancellableContinuationImpl, Unit.INSTANCE);
                        Animator.AnimatorListener animatorListener2 = animatorListener;
                        if (animatorListener2 != null) {
                            animatorListener2.onAnimationCancel(animator);
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        SuspendAnimatorsKt.access$resumeIfCan(cancellableContinuationImpl, Unit.INSTANCE);
                        Animator.AnimatorListener animatorListener2 = animatorListener;
                        if (animatorListener2 != null) {
                            animatorListener2.onAnimationEnd(animator);
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {
                        Animator.AnimatorListener animatorListener2 = animatorListener;
                        if (animatorListener2 != null) {
                            animatorListener2.onAnimationRepeat(animator);
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        Animator.AnimatorListener animatorListener2 = animatorListener;
                        if (animatorListener2 != null) {
                            animatorListener2.onAnimationStart(animator);
                        }
                    }
                });
                cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt$suspendAnimate$2$2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        translationY.cancel();
                        return Unit.INSTANCE;
                    }
                });
                result = cancellableContinuationImpl.getResult();
                if (result != coroutineSingletons) {
                    result = Unit.INSTANCE;
                }
                if (result != coroutineSingletons) {
                    result = Unit.INSTANCE;
                }
            } else {
                viewGroup.setTranslationY(f);
                result = Unit.INSTANCE;
            }
            if (result == coroutineSingletons) {
                return coroutineSingletons;
            }
            z = z2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z = this.Z$0;
            ResultKt.throwOnFailure(obj);
        }
        return Boolean.valueOf(z);
    }
}
