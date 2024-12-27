package com.android.systemui.util.kotlin;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.AwaitAll;
import kotlinx.coroutines.AwaitAll.AwaitAllNode;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.NotCompleted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        Object result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Iterable<Object> iterable = this.$this_mapParallel;
            Function2 function2 = this.$transform;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
            Iterator<Object> it = iterable.iterator();
            while (it.hasNext()) {
                arrayList.add(BuildersKt.async$default(coroutineScope, null, CoroutineStart.LAZY, new ParallelKt$mapParallel$2$1$1(function2, it.next(), null), 1));
            }
            this.label = 1;
            if (arrayList.isEmpty()) {
                result = EmptyList.INSTANCE;
            } else {
                AwaitAll awaitAll = new AwaitAll((Deferred[]) arrayList.toArray(new Deferred[0]));
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                Object[] objArr = awaitAll.deferreds;
                int length = objArr.length;
                AwaitAll.AwaitAllNode[] awaitAllNodeArr = new AwaitAll.AwaitAllNode[length];
                for (int i2 = 0; i2 < length; i2++) {
                    JobSupport jobSupport = (JobSupport) objArr[i2];
                    jobSupport.start();
                    AwaitAll.AwaitAllNode awaitAllNode = awaitAll.new AwaitAllNode(cancellableContinuationImpl);
                    awaitAllNode.handle = jobSupport.invokeOnCompletion(false, true, awaitAllNode);
                    Unit unit = Unit.INSTANCE;
                    awaitAllNodeArr[i2] = awaitAllNode;
                }
                AwaitAll.DisposeHandlersOnCancel disposeHandlersOnCancel = new AwaitAll.DisposeHandlersOnCancel(awaitAll, awaitAllNodeArr);
                for (int i3 = 0; i3 < length; i3++) {
                    awaitAllNodeArr[i3]._disposer.setValue(disposeHandlersOnCancel);
                }
                if (!(cancellableContinuationImpl._state.value instanceof NotCompleted)) {
                    disposeHandlersOnCancel.disposeAll();
                } else {
                    cancellableContinuationImpl.invokeOnCancellation(disposeHandlersOnCancel);
                }
                result = cancellableContinuationImpl.getResult();
                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            }
            obj = result;
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ParallelKt$mapParallel$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
