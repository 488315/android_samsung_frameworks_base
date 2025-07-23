package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SensitiveContentCoordinatorImpl$attach$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SensitiveContentCoordinatorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SensitiveContentCoordinatorImpl$attach$2(SensitiveContentCoordinatorImpl sensitiveContentCoordinatorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sensitiveContentCoordinatorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SensitiveContentCoordinatorImpl$attach$2(this.this$0, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        DeviceEntryInteractor deviceEntryInteractor;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            deviceEntryInteractor = this.this$0.deviceEntryInteractor;
            StateFlow stateFlow = (StateFlow) deviceEntryInteractor.canSwipeToEnter$delegate.getValue();
            final SensitiveContentCoordinatorImpl sensitiveContentCoordinatorImpl = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Boolean bool, Continuation continuation) {
                    boolean z;
                    boolean booleanValue = bool != null ? bool.booleanValue() : false;
                    z = SensitiveContentCoordinatorImpl.this.canSwipeToEnter;
                    if (z != booleanValue) {
                        SensitiveContentCoordinatorImpl.this.canSwipeToEnter = booleanValue;
                        SensitiveContentCoordinatorImpl.this.invalidateList("canSwipeToEnterChanged");
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (stateFlow.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SensitiveContentCoordinatorImpl$attach$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
