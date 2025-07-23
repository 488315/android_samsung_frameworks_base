package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.util.Maybe;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuildScopeImpl$observe$handle$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Maybe $output;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildScopeImpl$observe$handle$1$1$1(Maybe maybe, Continuation continuation) {
        super(2, continuation);
        this.$output = maybe;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BuildScopeImpl$observe$handle$1$1$1 buildScopeImpl$observe$handle$1$1$1 = new BuildScopeImpl$observe$handle$1$1$1(this.$output, continuation);
        buildScopeImpl$observe$handle$1$1$1.L$0 = obj;
        return buildScopeImpl$observe$handle$1$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuildScopeImpl$observe$handle$1$1$1) create((EvalScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((EvalScope) this.L$0).scheduleDeactivation((Output) ((Maybe.Present) this.$output).value);
        return Unit.INSTANCE;
    }
}
