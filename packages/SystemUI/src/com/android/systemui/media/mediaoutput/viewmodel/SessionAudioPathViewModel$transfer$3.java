package com.android.systemui.media.mediaoutput.viewmodel;

import com.android.systemui.media.mediaoutput.controller.device.DeviceController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class SessionAudioPathViewModel$transfer$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ AudioDevice $device;
    int label;
    final /* synthetic */ SessionAudioPathViewModel this$0;

    public SessionAudioPathViewModel$transfer$3(SessionAudioPathViewModel sessionAudioPathViewModel, AudioDevice audioDevice, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sessionAudioPathViewModel;
        this.$device = audioDevice;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SessionAudioPathViewModel$transfer$3(this.this$0, this.$device, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SessionAudioPathViewModel$transfer$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DeviceController deviceController = (DeviceController) ((LinkedHashMap) this.this$0.controllerMap).get(this.$device.getFinalControllerType());
        if (deviceController != null) {
            deviceController.transfer(this.$device);
        }
        return Unit.INSTANCE;
    }
}
