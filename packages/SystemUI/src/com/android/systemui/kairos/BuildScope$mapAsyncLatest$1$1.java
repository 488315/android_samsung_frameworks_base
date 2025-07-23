package com.android.systemui.kairos;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuildScope$mapAsyncLatest$1$1 extends SuspendLambda implements Function1 {
    final /* synthetic */ Object $a;
    final /* synthetic */ Function2 $transform;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildScope$mapAsyncLatest$1$1(Function2 function2, Object obj, Continuation continuation) {
        super(1, continuation);
        this.$transform = function2;
        this.$a = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new BuildScope$mapAsyncLatest$1$1(this.$transform, this.$a, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((BuildScope$mapAsyncLatest$1$1) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
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
        Function2 function2 = this.$transform;
        Object obj2 = this.$a;
        this.label = 1;
        Object invoke = function2.invoke(obj2, this);
        return invoke == coroutineSingletons ? coroutineSingletons : invoke;
    }
}
