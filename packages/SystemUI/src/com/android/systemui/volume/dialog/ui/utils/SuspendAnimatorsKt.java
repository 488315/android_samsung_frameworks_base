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
                function1.mo781invoke(Float.valueOf(f2));
            }
        };
        final DynamicAnimation.OnAnimationEndListener onAnimationEndListener = new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt$suspendAnimate$7$endListener$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f2, float f3) {
                SuspendAnimatorsKt.access$resumeIfCan(cancellableContinuationImpl, Unit.INSTANCE);
            }
        };
        springAnimation.addUpdateListener(onAnimationUpdateListener);
        springAnimation.addEndListener(onAnimationEndListener);
        springAnimation.animateToFinalPosition(f);
        cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt$suspendAnimate$7$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                SpringAnimation springAnimation2 = springAnimation;
                ArrayList arrayList = springAnimation2.mUpdateListeners;
                int iIndexOf = arrayList.indexOf(onAnimationUpdateListener);
                if (iIndexOf >= 0) {
                    arrayList.set(iIndexOf, null);
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
