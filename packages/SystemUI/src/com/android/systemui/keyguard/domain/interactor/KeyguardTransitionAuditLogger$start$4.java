package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.common.shared.model.NotificationContainerBounds;
import com.android.systemui.log.core.LogLevel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

final class KeyguardTransitionAuditLogger$start$4 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardTransitionAuditLogger this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardTransitionAuditLogger$start$4(KeyguardTransitionAuditLogger keyguardTransitionAuditLogger, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardTransitionAuditLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardTransitionAuditLogger$start$4(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardTransitionAuditLogger$start$4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow debounce = FlowKt.debounce((StateFlow) this.this$0.sharedNotificationContainerViewModel.bounds$delegate.getValue(), 20L);
            final KeyguardTransitionAuditLogger keyguardTransitionAuditLogger = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionAuditLogger$start$4.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    KeyguardTransitionAuditLogger.this.logger.log(KeyguardTransitionAuditLoggerKt.TAG, LogLevel.VERBOSE, "Notif: bounds (debounced)", (NotificationContainerBounds) obj2);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (debounce.collect(flowCollector, this) == coroutineSingletons) {
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
