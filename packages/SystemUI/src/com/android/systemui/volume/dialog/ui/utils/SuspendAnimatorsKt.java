package com.android.systemui.volume.dialog.ui.utils;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import java.util.ArrayList;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class SuspendAnimatorsKt {
    public static final void access$resumeIfCan(CancellableContinuation cancellableContinuation, Unit unit) {
        if (cancellableContinuation.isCancelled$1() || cancellableContinuation.isCompleted()) {
            return;
        }
        int i = Result.$r8$clinit;
        cancellableContinuation.resumeWith(unit);
    }

    public static Object suspendAnimate$default(final SpringAnimation springAnimation, float f, final Function1 function1, SuspendLambda suspendLambda, int i) {
        if ((i & 1) != 0) {
            f = 1.0f;
        }
        if ((i & 2) != 0) {
            function1 = new SuspendAnimatorsKt$$ExternalSyntheticLambda0();
        }
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(suspendLambda), 1);
        cancellableContinuationImpl.initCancellability();
        final DynamicAnimation.OnAnimationUpdateListener onAnimationUpdateListener = new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt$suspendAnimate$7$updateListener$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f2, float f3) {
                Function1.this.mo779invoke(Float.valueOf(f2));
            }
        };
        final DynamicAnimation.OnAnimationEndListener onAnimationEndListener = new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt$suspendAnimate$7$endListener$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f2, float f3) {
                SuspendAnimatorsKt.access$resumeIfCan(CancellableContinuation.this, Unit.INSTANCE);
            }
        };
        springAnimation.addUpdateListener(onAnimationUpdateListener);
        springAnimation.addEndListener(onAnimationEndListener);
        springAnimation.animateToFinalPosition(f);
        cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt$suspendAnimate$7$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                SpringAnimation springAnimation2 = SpringAnimation.this;
                ArrayList arrayList = springAnimation2.mUpdateListeners;
                int indexOf = arrayList.indexOf(onAnimationUpdateListener);
                if (indexOf >= 0) {
                    arrayList.set(indexOf, null);
                }
                springAnimation2.removeEndListener(onAnimationEndListener);
                springAnimation2.cancel();
                return Unit.INSTANCE;
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        return result == CoroutineSingletons.COROUTINE_SUSPENDED ? result : Unit.INSTANCE;
    }
}
