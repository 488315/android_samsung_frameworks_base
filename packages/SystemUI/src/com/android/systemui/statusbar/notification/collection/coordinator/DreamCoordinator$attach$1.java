package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class DreamCoordinator$attach$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DreamCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DreamCoordinator$attach$1(DreamCoordinator dreamCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dreamCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DreamCoordinator$attach$1(this.this$0, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object attachFilterOnDreamingStateChange;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DreamCoordinator dreamCoordinator = this.this$0;
            this.label = 1;
            attachFilterOnDreamingStateChange = dreamCoordinator.attachFilterOnDreamingStateChange(this);
            if (attachFilterOnDreamingStateChange == coroutineSingletons) {
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((DreamCoordinator$attach$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
