package com.android.systemui.deviceentry.ui.binder;

import com.android.systemui.util.sensors.AsyncSensorManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class LiftToRunFaceAuthBinder$init$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ LiftToRunFaceAuthBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LiftToRunFaceAuthBinder$init$1(LiftToRunFaceAuthBinder liftToRunFaceAuthBinder, Continuation continuation) {
        super(2, continuation);
        this.this$0 = liftToRunFaceAuthBinder;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LiftToRunFaceAuthBinder$init$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LiftToRunFaceAuthBinder$init$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final LiftToRunFaceAuthBinder liftToRunFaceAuthBinder = this.this$0;
            FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = liftToRunFaceAuthBinder.listenForPickupSensor;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$init$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Boolean bool = (Boolean) obj2;
                    boolean booleanValue = bool.booleanValue();
                    LiftToRunFaceAuthBinder liftToRunFaceAuthBinder2 = LiftToRunFaceAuthBinder.this;
                    if (liftToRunFaceAuthBinder2.pickupSensor != null) {
                        StateFlowImpl stateFlowImpl = liftToRunFaceAuthBinder2.isListening;
                        if (booleanValue != ((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                            stateFlowImpl.updateState(null, bool);
                            LiftToRunFaceAuthBinder$listener$1 liftToRunFaceAuthBinder$listener$1 = liftToRunFaceAuthBinder2.listener;
                            AsyncSensorManager asyncSensorManager = liftToRunFaceAuthBinder2.asyncSensorManager;
                            if (booleanValue) {
                                asyncSensorManager.requestTriggerSensor(liftToRunFaceAuthBinder$listener$1, liftToRunFaceAuthBinder2.pickupSensor);
                            } else {
                                asyncSensorManager.cancelTriggerSensor(liftToRunFaceAuthBinder$listener$1, liftToRunFaceAuthBinder2.pickupSensor);
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1.collect(flowCollector, this) == coroutineSingletons) {
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
