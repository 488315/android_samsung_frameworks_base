package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.DevicePostureController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DevicePostureControllerExtKt$devicePosture$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DevicePostureController $this_devicePosture;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DevicePostureControllerExtKt$devicePosture$1(DevicePostureController devicePostureController, Continuation continuation) {
        super(2, continuation);
        this.$this_devicePosture = devicePostureController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DevicePostureControllerExtKt$devicePosture$1 devicePostureControllerExtKt$devicePosture$1 = new DevicePostureControllerExtKt$devicePosture$1(this.$this_devicePosture, continuation);
        devicePostureControllerExtKt$devicePosture$1.L$0 = obj;
        return devicePostureControllerExtKt$devicePosture$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DevicePostureControllerExtKt$devicePosture$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.policy.DevicePostureControllerExtKt$devicePosture$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new DevicePostureController.Callback() { // from class: com.android.systemui.statusbar.policy.DevicePostureControllerExtKt$devicePosture$1$callback$1
                @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
                public final void onPostureChanged(int i2) {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(Integer.valueOf(i2));
                }
            };
            ((DevicePostureControllerImpl) this.$this_devicePosture).addCallback(r1);
            final DevicePostureController devicePostureController = this.$this_devicePosture;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.DevicePostureControllerExtKt$devicePosture$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((DevicePostureControllerImpl) DevicePostureController.this).removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
