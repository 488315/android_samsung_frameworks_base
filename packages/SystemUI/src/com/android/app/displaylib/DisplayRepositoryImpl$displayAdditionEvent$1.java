package com.android.app.displaylib;

import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DisplayRepositoryImpl$displayAdditionEvent$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public DisplayRepositoryImpl$displayAdditionEvent$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DisplayRepositoryImpl$displayAdditionEvent$1 displayRepositoryImpl$displayAdditionEvent$1 = new DisplayRepositoryImpl$displayAdditionEvent$1((Continuation) obj3);
        displayRepositoryImpl$displayAdditionEvent$1.L$0 = (Set) obj;
        displayRepositoryImpl$displayAdditionEvent$1.L$1 = (Set) obj2;
        return displayRepositoryImpl$displayAdditionEvent$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return SetsKt___SetsKt.minus((Set) this.L$1, (Iterable) this.L$0);
    }
}
