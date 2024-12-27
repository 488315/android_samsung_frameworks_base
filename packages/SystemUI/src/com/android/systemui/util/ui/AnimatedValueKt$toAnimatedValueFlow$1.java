package com.android.systemui.util.ui;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

final class AnimatedValueKt$toAnimatedValueFlow$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    int label;

    public AnimatedValueKt$toAnimatedValueFlow$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 3
            r3 = 2
            r4 = 1
            r5 = 0
            if (r1 == 0) goto L36
            if (r1 == r4) goto L27
            if (r1 == r3) goto L1d
            if (r1 != r2) goto L15
            kotlin.ResultKt.throwOnFailure(r10)
            goto L90
        L15:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L1d:
            java.lang.Object r1 = r9.L$1
            java.lang.Object r3 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r10)
            goto L7c
        L27:
            java.lang.Object r1 = r9.L$2
            kotlinx.coroutines.CompletableDeferred r1 = (kotlinx.coroutines.CompletableDeferred) r1
            java.lang.Object r4 = r9.L$1
            java.lang.Object r6 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r6
            goto L69
        L36:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            java.lang.Object r1 = r9.L$1
            com.android.systemui.util.ui.AnimatableEvent r1 = (com.android.systemui.util.ui.AnimatableEvent) r1
            java.lang.Object r6 = r1.component1()
            boolean r1 = r1.component2()
            if (r1 == 0) goto L7e
            kotlinx.coroutines.CompletableDeferredImpl r1 = kotlinx.coroutines.CompletableDeferredKt.CompletableDeferred$default()
            com.android.systemui.util.ui.AnimatedValue$Animating r7 = new com.android.systemui.util.ui.AnimatedValue$Animating
            com.android.systemui.util.ui.AnimatedValueKt$toAnimatedValueFlow$1$1 r8 = new com.android.systemui.util.ui.AnimatedValueKt$toAnimatedValueFlow$1$1
            r8.<init>()
            r7.<init>(r6, r8)
            r9.L$0 = r10
            r9.L$1 = r6
            r9.L$2 = r1
            r9.label = r4
            java.lang.Object r4 = r10.emit(r7, r9)
            if (r4 != r0) goto L68
            return r0
        L68:
            r4 = r6
        L69:
            r9.L$0 = r10
            r9.L$1 = r4
            r9.L$2 = r5
            r9.label = r3
            kotlinx.coroutines.CompletableDeferredImpl r1 = (kotlinx.coroutines.CompletableDeferredImpl) r1
            java.lang.Object r1 = r1.awaitInternal(r9)
            if (r1 != r0) goto L7a
            return r0
        L7a:
            r3 = r10
            r1 = r4
        L7c:
            r6 = r1
            r10 = r3
        L7e:
            com.android.systemui.util.ui.AnimatedValue$NotAnimating r1 = new com.android.systemui.util.ui.AnimatedValue$NotAnimating
            r1.<init>(r6)
            r9.L$0 = r5
            r9.L$1 = r5
            r9.label = r2
            java.lang.Object r9 = r10.emit(r1, r9)
            if (r9 != r0) goto L90
            return r0
        L90:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.ui.AnimatedValueKt$toAnimatedValueFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(FlowCollector flowCollector, AnimatableEvent<Object> animatableEvent, Continuation continuation) {
        AnimatedValueKt$toAnimatedValueFlow$1 animatedValueKt$toAnimatedValueFlow$1 = new AnimatedValueKt$toAnimatedValueFlow$1(continuation);
        animatedValueKt$toAnimatedValueFlow$1.L$0 = flowCollector;
        animatedValueKt$toAnimatedValueFlow$1.L$1 = animatableEvent;
        return animatedValueKt$toAnimatedValueFlow$1.invokeSuspend(Unit.INSTANCE);
    }
}
