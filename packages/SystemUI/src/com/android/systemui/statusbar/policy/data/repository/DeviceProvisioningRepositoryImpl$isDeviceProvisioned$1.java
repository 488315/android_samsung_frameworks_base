package com.android.systemui.statusbar.policy.data.repository;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
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

final class DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceProvisioningRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1(DeviceProvisioningRepositoryImpl deviceProvisioningRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceProvisioningRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1 deviceProvisioningRepositoryImpl$isDeviceProvisioned$1 = new DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1(this.this$0, continuation);
        deviceProvisioningRepositoryImpl$isDeviceProvisioned$1.L$0 = obj;
        return deviceProvisioningRepositoryImpl$isDeviceProvisioned$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.policy.data.repository.DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1$listener$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceProvisioningRepositoryImpl deviceProvisioningRepositoryImpl = this.this$0;
            final ?? r1 = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.policy.data.repository.DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1$listener$1
                @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
                public final void onDeviceProvisionedChanged() {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(((DeviceProvisionedControllerImpl) deviceProvisioningRepositoryImpl.deviceProvisionedController).deviceProvisioned.get()));
                }
            };
            ((DeviceProvisionedControllerImpl) this.this$0.deviceProvisionedController).addCallback(r1);
            ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.valueOf(((DeviceProvisionedControllerImpl) this.this$0.deviceProvisionedController).deviceProvisioned.get()));
            final DeviceProvisioningRepositoryImpl deviceProvisioningRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.data.repository.DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((DeviceProvisionedControllerImpl) DeviceProvisioningRepositoryImpl.this.deviceProvisionedController).removeCallback(r1);
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
