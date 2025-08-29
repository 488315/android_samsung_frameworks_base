package com.android.systemui.display.data.repository;

import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import com.android.systemui.display.data.repository.DeviceStateRepository;
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

/* loaded from: classes2.dex */
final class DeviceStateRepositoryImpl$state$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Executor $executor;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceStateRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceStateRepositoryImpl$state$1(DeviceStateRepositoryImpl deviceStateRepositoryImpl, Executor executor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceStateRepositoryImpl;
        this.$executor = executor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceStateRepositoryImpl$state$1 deviceStateRepositoryImpl$state$1 = new DeviceStateRepositoryImpl$state$1(this.this$0, this.$executor, continuation);
        deviceStateRepositoryImpl$state$1.L$0 = obj;
        return deviceStateRepositoryImpl$state$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceStateRepositoryImpl$state$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.hardware.devicestate.DeviceStateManager$DeviceStateCallback, com.android.systemui.display.data.repository.DeviceStateRepositoryImpl$state$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceStateRepositoryImpl deviceStateRepositoryImpl = this.this$0;
            final ?? r1 = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.display.data.repository.DeviceStateRepositoryImpl$state$1$callback$1
                /* JADX WARN: Removed duplicated region for block: B:32:0x007b  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onDeviceStateChanged(DeviceState deviceState) {
                    Object next;
                    DeviceStateRepository.DeviceState deviceState2;
                    SendChannel sendChannel = producerScope;
                    DeviceStateRepositoryImpl deviceStateRepositoryImpl2 = deviceStateRepositoryImpl;
                    int identifier = deviceState.getIdentifier();
                    Iterator it = deviceStateRepositoryImpl2.deviceStateManager.getSupportedDeviceStates().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        } else {
                            next = it.next();
                            if (((DeviceState) next).getIdentifier() == identifier) {
                                break;
                            }
                        }
                    }
                    DeviceState deviceState3 = (DeviceState) next;
                    if (deviceState3 == null) {
                        deviceState2 = DeviceStateRepository.DeviceState.UNKNOWN;
                    } else {
                        deviceState2 = deviceState3.hasProperties(new int[]{16, 1001}) ? DeviceStateRepository.DeviceState.REAR_DISPLAY_OUTER_DEFAULT : deviceState3.hasProperty(16) ? DeviceStateRepository.DeviceState.REAR_DISPLAY : deviceState3.hasProperty(17) ? DeviceStateRepository.DeviceState.CONCURRENT_DISPLAY : deviceState3.hasProperty(11) ? DeviceStateRepository.DeviceState.FOLDED : deviceState3.hasProperties(new int[]{12, 2}) ? DeviceStateRepository.DeviceState.HALF_FOLDED : deviceState3.hasProperty(12) ? DeviceStateRepository.DeviceState.UNFOLDED : DeviceStateRepository.DeviceState.UNKNOWN;
                        if (deviceState2 == null) {
                        }
                    }
                    ((ChannelCoroutine) sendChannel).mo3475trySendJP2dKIU(deviceState2);
                }
            };
            this.this$0.deviceStateManager.registerCallback(this.$executor, (DeviceStateManager.DeviceStateCallback) r1);
            final DeviceStateRepositoryImpl deviceStateRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.display.data.repository.DeviceStateRepositoryImpl$state$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    deviceStateRepositoryImpl2.deviceStateManager.unregisterCallback(r1);
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
