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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class SessionAudioPathViewModel$select$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ AudioDevice $device;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SessionAudioPathViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SessionAudioPathViewModel$select$1(SessionAudioPathViewModel sessionAudioPathViewModel, AudioDevice audioDevice, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sessionAudioPathViewModel;
        this.$device = audioDevice;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SessionAudioPathViewModel$select$1 sessionAudioPathViewModel$select$1 = new SessionAudioPathViewModel$select$1(this.this$0, this.$device, continuation);
        sessionAudioPathViewModel$select$1.L$0 = obj;
        return sessionAudioPathViewModel$select$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SessionAudioPathViewModel$select$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        if (SessionAudioPathViewModel.access$checkForBudsTogether(this.this$0, this.$device, true)) {
            coroutineScope = null;
        }
        if (coroutineScope == null) {
            return Unit.INSTANCE;
        }
        DeviceController deviceController = (DeviceController) ((LinkedHashMap) this.this$0.controllerMap).get(this.$device.getFinalControllerType());
        if (deviceController != null) {
            deviceController.select(this.$device);
        }
        return Unit.INSTANCE;
    }
}
