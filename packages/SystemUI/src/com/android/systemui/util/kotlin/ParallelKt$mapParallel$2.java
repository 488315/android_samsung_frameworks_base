package com.android.systemui.util.kotlin;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.AwaitAll;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Deferred;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ParallelKt$mapParallel$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Iterable<Object> $this_mapParallel;
    final /* synthetic */ Function2 $transform;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ParallelKt$mapParallel$2(Iterable<Object> iterable, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$this_mapParallel = iterable;
        this.$transform = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ParallelKt$mapParallel$2 parallelKt$mapParallel$2 = new ParallelKt$mapParallel$2(this.$this_mapParallel, this.$transform, continuation);
        parallelKt$mapParallel$2.L$0 = obj;
        return parallelKt$mapParallel$2;
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
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        Iterable<Object> iterable = this.$this_mapParallel;
        Function2 function2 = this.$transform;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        Iterator<Object> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(CoroutineTracingKt.asyncTraced$default(coroutineScope, null, CoroutineStart.LAZY, new ParallelKt$mapParallel$2$1$1(function2, it.next(), null), 3));
        }
        this.label = 1;
        Object await = arrayList.isEmpty() ? EmptyList.INSTANCE : new AwaitAll((Deferred[]) arrayList.toArray(new Deferred[0])).await(this);
        return await == coroutineSingletons ? coroutineSingletons : await;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ParallelKt$mapParallel$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
