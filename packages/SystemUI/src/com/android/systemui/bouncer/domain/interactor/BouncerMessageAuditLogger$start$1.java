package com.android.systemui.bouncer.domain.interactor;

import android.util.Log;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepositoryImpl;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

final class BouncerMessageAuditLogger$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ BouncerMessageAuditLogger this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageAuditLogger$start$1(BouncerMessageAuditLogger bouncerMessageAuditLogger, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerMessageAuditLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BouncerMessageAuditLogger$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerMessageAuditLogger$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            StateFlowImpl stateFlowImpl = ((BouncerMessageRepositoryImpl) this.this$0.repository).bouncerMessage;
            this.label = 1;
            if (stateFlowImpl.collect(new FlowCollector() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerMessageAuditLogger$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Log.d(BouncerMessageAuditLoggerKt.TAG, "bouncerMessage: " + ((BouncerMessageModel) obj2));
                    return Unit.INSTANCE;
                }
            }, this) == coroutineSingletons) {
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
