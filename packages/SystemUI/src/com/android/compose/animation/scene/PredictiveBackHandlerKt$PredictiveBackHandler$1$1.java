package com.android.compose.animation.scene;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PredictiveBackHandlerKt$PredictiveBackHandler$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SceneTransitionLayoutImpl $layoutImpl;
    final /* synthetic */ UserActionResult $result;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PredictiveBackHandlerKt$PredictiveBackHandler$1$1(UserActionResult userActionResult, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Continuation continuation) {
        super(2, continuation);
        this.$result = userActionResult;
        this.$layoutImpl = sceneTransitionLayoutImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PredictiveBackHandlerKt$PredictiveBackHandler$1$1 predictiveBackHandlerKt$PredictiveBackHandler$1$1 = new PredictiveBackHandlerKt$PredictiveBackHandler$1$1(this.$result, this.$layoutImpl, continuation);
        predictiveBackHandlerKt$PredictiveBackHandler$1$1.L$0 = obj;
        return predictiveBackHandlerKt$PredictiveBackHandler$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PredictiveBackHandlerKt$PredictiveBackHandler$1$1) create((Flow) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0030, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.first(r2, r20) == r1) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00d8, code lost:
    
        if (r0 == r1) goto L42;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r21) {
        /*
            Method dump skipped, instructions count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.PredictiveBackHandlerKt$PredictiveBackHandler$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
