package com.android.app.displaylib;

import android.util.Log;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PerDisplayInstanceRepositoryImpl$start$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PerDisplayInstanceRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PerDisplayInstanceRepositoryImpl$start$2(PerDisplayInstanceRepositoryImpl perDisplayInstanceRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = perDisplayInstanceRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PerDisplayInstanceRepositoryImpl$start$2 perDisplayInstanceRepositoryImpl$start$2 = new PerDisplayInstanceRepositoryImpl$start$2(this.this$0, continuation);
        perDisplayInstanceRepositoryImpl$start$2.L$0 = obj;
        return perDisplayInstanceRepositoryImpl$start$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PerDisplayInstanceRepositoryImpl$start$2) create((Set) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Set<Integer> minus = SetsKt___SetsKt.minus(this.this$0.perDisplayInstances.keySet(), (Iterable) this.L$0);
        PerDisplayInstanceRepositoryImpl perDisplayInstanceRepositoryImpl = this.this$0;
        for (Integer num : minus) {
            Log.d("PerDisplayInstanceRepo", "<" + perDisplayInstanceRepositoryImpl.debugName + "> destroying instance for displayId=" + num + ".");
            Object remove = perDisplayInstanceRepositoryImpl.perDisplayInstances.remove(num);
            if (remove != null) {
                PerDisplayInstanceProvider perDisplayInstanceProvider = perDisplayInstanceRepositoryImpl.instanceProvider;
                PerDisplayInstanceProviderWithTeardown perDisplayInstanceProviderWithTeardown = perDisplayInstanceProvider instanceof PerDisplayInstanceProviderWithTeardown ? (PerDisplayInstanceProviderWithTeardown) perDisplayInstanceProvider : null;
                if (perDisplayInstanceProviderWithTeardown != null) {
                    perDisplayInstanceProviderWithTeardown.destroyInstance(remove);
                }
            }
        }
        return Unit.INSTANCE;
    }
}
