package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.RotationLockController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class RotationLockControllerExtKt$isRotationLockEnabled$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ RotationLockController $this_isRotationLockEnabled;
    private /* synthetic */ Object L$0;
    int label;

    public RotationLockControllerExtKt$isRotationLockEnabled$2(RotationLockController rotationLockController, Continuation continuation) {
        super(2, continuation);
        this.$this_isRotationLockEnabled = rotationLockController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RotationLockControllerExtKt$isRotationLockEnabled$2 rotationLockControllerExtKt$isRotationLockEnabled$2 = new RotationLockControllerExtKt$isRotationLockEnabled$2(this.$this_isRotationLockEnabled, continuation);
        rotationLockControllerExtKt$isRotationLockEnabled$2.L$0 = obj;
        return rotationLockControllerExtKt$isRotationLockEnabled$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean valueOf = Boolean.valueOf(this.$this_isRotationLockEnabled.isRotationLocked());
            this.label = 1;
            if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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
    public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
        return ((RotationLockControllerExtKt$isRotationLockEnabled$2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
