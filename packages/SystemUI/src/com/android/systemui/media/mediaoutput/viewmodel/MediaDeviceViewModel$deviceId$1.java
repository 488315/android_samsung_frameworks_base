package com.android.systemui.media.mediaoutput.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;

final class MediaDeviceViewModel$deviceId$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MediaDeviceViewModel this$0;

    public MediaDeviceViewModel$deviceId$1(MediaDeviceViewModel mediaDeviceViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaDeviceViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaDeviceViewModel$deviceId$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaDeviceViewModel$deviceId$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            StateFlowImpl stateFlowImpl = this.this$0.updateDeviceInfo;
            Long l = new Long(System.currentTimeMillis());
            this.label = 1;
            stateFlowImpl.updateState(null, l);
            if (Unit.INSTANCE == coroutineSingletons) {
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
