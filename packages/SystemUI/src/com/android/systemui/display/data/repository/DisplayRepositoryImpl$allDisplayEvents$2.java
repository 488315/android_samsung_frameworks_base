package com.android.systemui.display.data.repository;

import com.android.systemui.display.data.DisplayEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class DisplayRepositoryImpl$allDisplayEvents$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public DisplayRepositoryImpl$allDisplayEvents$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisplayRepositoryImpl$allDisplayEvents$2 displayRepositoryImpl$allDisplayEvents$2 = new DisplayRepositoryImpl$allDisplayEvents$2(continuation);
        displayRepositoryImpl$allDisplayEvents$2.L$0 = obj;
        return displayRepositoryImpl$allDisplayEvents$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplayRepositoryImpl$allDisplayEvents$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            DisplayEvent.Changed changed = new DisplayEvent.Changed(0);
            this.label = 1;
            if (flowCollector.emit(changed, this) == coroutineSingletons) {
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
