package com.android.systemui.display.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class PerDisplayStoreImpl$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ PerDisplayStoreImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PerDisplayStoreImpl$start$1(PerDisplayStoreImpl perDisplayStoreImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = perDisplayStoreImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PerDisplayStoreImpl$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PerDisplayStoreImpl$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow displayRemovalEvent = ((DisplayRepositoryImpl) this.this$0.displayRepository).displayRepositoryFromLib.getDisplayRemovalEvent();
            final PerDisplayStoreImpl perDisplayStoreImpl = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.display.data.repository.PerDisplayStoreImpl$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object onDisplayRemovalAction;
                    int intValue = ((Number) obj2).intValue();
                    PerDisplayStoreImpl perDisplayStoreImpl2 = PerDisplayStoreImpl.this;
                    Object remove = perDisplayStoreImpl2.perDisplayInstances.remove(new Integer(intValue));
                    return (remove == null || (onDisplayRemovalAction = perDisplayStoreImpl2.onDisplayRemovalAction(remove)) != CoroutineSingletons.COROUTINE_SUSPENDED) ? Unit.INSTANCE : onDisplayRemovalAction;
                }
            };
            this.label = 1;
            if (displayRemovalEvent.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
