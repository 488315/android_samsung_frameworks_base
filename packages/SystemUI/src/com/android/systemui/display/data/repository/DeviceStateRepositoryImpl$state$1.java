package com.android.systemui.display.data.repository;

import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.display.data.repository.DeviceStateRepositoryImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;
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
import kotlinx.coroutines.channels.SendChannel;

final class DeviceStateRepositoryImpl$state$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DeviceStateManager $deviceStateManager;
    final /* synthetic */ Executor $executor;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceStateRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceStateRepositoryImpl$state$1(DeviceStateManager deviceStateManager, Executor executor, DeviceStateRepositoryImpl deviceStateRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$deviceStateManager = deviceStateManager;
        this.$executor = executor;
        this.this$0 = deviceStateRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceStateRepositoryImpl$state$1 deviceStateRepositoryImpl$state$1 = new DeviceStateRepositoryImpl$state$1(this.$deviceStateManager, this.$executor, this.this$0, continuation);
        deviceStateRepositoryImpl$state$1.L$0 = obj;
        return deviceStateRepositoryImpl$state$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceStateRepositoryImpl$state$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceStateRepositoryImpl deviceStateRepositoryImpl = this.this$0;
            final DeviceStateManager.DeviceStateCallback deviceStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.display.data.repository.DeviceStateRepositoryImpl$state$1$callback$1
                public final void onDeviceStateChanged(DeviceState deviceState) {
                    Object obj2;
                    DeviceStateRepository.DeviceState deviceState2;
                    SendChannel sendChannel = ProducerScope.this;
                    DeviceStateRepositoryImpl deviceStateRepositoryImpl2 = deviceStateRepositoryImpl;
                    int identifier = deviceState.getIdentifier();
                    Iterator it = ((ArrayList) deviceStateRepositoryImpl2.deviceStateMap).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            obj2 = null;
                            break;
                        } else {
                            obj2 = it.next();
                            if (((DeviceStateRepositoryImpl.IdsPerDeviceState) obj2).ids.contains(Integer.valueOf(identifier))) {
                                break;
                            }
                        }
                    }
                    DeviceStateRepositoryImpl.IdsPerDeviceState idsPerDeviceState = (DeviceStateRepositoryImpl.IdsPerDeviceState) obj2;
                    if (idsPerDeviceState == null || (deviceState2 = idsPerDeviceState.deviceState) == null) {
                        deviceState2 = DeviceStateRepository.DeviceState.UNKNOWN;
                    }
                    ((ChannelCoroutine) sendChannel).mo2552trySendJP2dKIU(deviceState2);
                }
            };
            this.$deviceStateManager.registerCallback(this.$executor, deviceStateCallback);
            final DeviceStateManager deviceStateManager = this.$deviceStateManager;
            Function0 function0 = new Function0() { // from class: com.android.systemui.display.data.repository.DeviceStateRepositoryImpl$state$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    deviceStateManager.unregisterCallback(deviceStateCallback);
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
