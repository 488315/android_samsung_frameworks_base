package com.android.systemui.volume.dialog.settings.ui.viewmodel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.Drawable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.utils.LottieValueAnimator;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* loaded from: classes3.dex */
final class VolumeDialogSettingsButtonViewModel$icon$5 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public VolumeDialogSettingsButtonViewModel$icon$5(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeDialogSettingsButtonViewModel$icon$5 volumeDialogSettingsButtonViewModel$icon$5 = new VolumeDialogSettingsButtonViewModel$icon$5((Continuation) obj3);
        volumeDialogSettingsButtonViewModel$icon$5.L$0 = (Drawable) obj;
        volumeDialogSettingsButtonViewModel$icon$5.L$1 = (Drawable) obj2;
        return volumeDialogSettingsButtonViewModel$icon$5.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007a  */
    /* JADX WARN: Type inference failed for: r5v5, types: [android.animation.Animator$AnimatorListener, com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModelKt$awaitFinish$2$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        Object obj3;
        LottieDrawable lottieDrawable;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Drawable drawable = (Drawable) this.L$0;
            obj2 = (Drawable) this.L$1;
            final LottieDrawable lottieDrawable2 = drawable instanceof LottieDrawable ? (LottieDrawable) drawable : null;
            if (lottieDrawable2 != null) {
                this.L$0 = obj2;
                this.label = 1;
                final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                LottieValueAnimator lottieValueAnimator = lottieDrawable2.animator;
                if (lottieValueAnimator == null ? false : lottieValueAnimator.running) {
                    final ?? r5 = new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModelKt$awaitFinish$2$listener$1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {
                            CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                            int i2 = Result.$r8$clinit;
                            cancellableContinuation.resumeWith(Unit.INSTANCE);
                            lottieDrawable2.animator.removeListener(this);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                            int i2 = Result.$r8$clinit;
                            cancellableContinuation.resumeWith(Unit.INSTANCE);
                            lottieDrawable2.animator.removeListener(this);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator animator) {
                            CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                            int i2 = Result.$r8$clinit;
                            cancellableContinuation.resumeWith(Unit.INSTANCE);
                            lottieDrawable2.animator.removeListener(this);
                        }
                    };
                    lottieDrawable2.animator.addListener(r5);
                    cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModelKt$awaitFinish$2$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj4) {
                            lottieDrawable2.animator.removeListener(r5);
                            return Unit.INSTANCE;
                        }
                    });
                } else {
                    int i2 = Result.$r8$clinit;
                    cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
                }
                Object result = cancellableContinuationImpl.getResult();
                if (result != coroutineSingletons) {
                    result = Unit.INSTANCE;
                }
                if (result == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj3 = obj2;
            }
            lottieDrawable = obj2 instanceof LottieDrawable ? (LottieDrawable) obj2 : null;
            if (lottieDrawable != null) {
                lottieDrawable.start();
            }
            return obj2;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        obj3 = (Drawable) this.L$0;
        ResultKt.throwOnFailure(obj);
        obj2 = obj3;
        if (obj2 instanceof LottieDrawable) {
        }
        if (lottieDrawable != null) {
        }
        return obj2;
    }
}
