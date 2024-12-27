package com.android.systemui.media.mediaoutput.controller.device;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;

final class AudioMirroringDeviceController$packageName$4 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ AudioMirroringDeviceController this$0;

    public AudioMirroringDeviceController$packageName$4(AudioMirroringDeviceController audioMirroringDeviceController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioMirroringDeviceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AudioMirroringDeviceController$packageName$4(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioMirroringDeviceController$packageName$4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AudioMirroringDeviceController audioMirroringDeviceController = this.this$0;
            StateFlowImpl stateFlowImpl = audioMirroringDeviceController.refreshRoutes;
            String str = audioMirroringDeviceController.mediaPackageName;
            this.label = 1;
            stateFlowImpl.setValue(str);
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
