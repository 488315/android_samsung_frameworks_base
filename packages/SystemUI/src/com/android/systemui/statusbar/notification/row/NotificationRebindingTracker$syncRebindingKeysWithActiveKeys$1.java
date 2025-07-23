package com.android.systemui.statusbar.notification.row;

import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationRebindingTracker$syncRebindingKeysWithActiveKeys$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ NotificationRebindingTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationRebindingTracker$syncRebindingKeysWithActiveKeys$1(NotificationRebindingTracker notificationRebindingTracker, Continuation continuation) {
        super(2, continuation);
        this.this$0 = notificationRebindingTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NotificationRebindingTracker$syncRebindingKeysWithActiveKeys$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationRebindingTracker$syncRebindingKeysWithActiveKeys$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final NotificationRebindingTracker notificationRebindingTracker = this.this$0;
            FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = notificationRebindingTracker.activeKeys;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.row.NotificationRebindingTracker$syncRebindingKeysWithActiveKeys$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object value;
                    Set set = (Set) obj2;
                    StateFlowImpl stateFlowImpl = NotificationRebindingTracker.this.rebindingKeys;
                    do {
                        value = stateFlowImpl.getValue();
                    } while (!stateFlowImpl.compareAndSet(value, CollectionsKt___CollectionsKt.intersect((Set) value, set)));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__TransformKt$onEach$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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
