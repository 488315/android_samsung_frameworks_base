package com.android.systemui.display.data.repository;

import android.hardware.devicestate.DeviceStateManager;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                /* JADX WARN: Code restructure failed: missing block: B:12:0x0078, code lost:
                
                    if (r3 != null) goto L33;
                 */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onDeviceStateChanged(android.hardware.devicestate.DeviceState r4) {
                    /*
                        r3 = this;
                        kotlinx.coroutines.channels.ProducerScope r0 = kotlinx.coroutines.channels.ProducerScope.this
                        com.android.systemui.display.data.repository.DeviceStateRepositoryImpl r3 = r2
                        int r4 = r4.getIdentifier()
                        android.hardware.devicestate.DeviceStateManager r3 = r3.deviceStateManager
                        java.util.List r3 = r3.getSupportedDeviceStates()
                        java.lang.Iterable r3 = (java.lang.Iterable) r3
                        java.util.Iterator r3 = r3.iterator()
                    L14:
                        boolean r1 = r3.hasNext()
                        if (r1 == 0) goto L28
                        java.lang.Object r1 = r3.next()
                        r2 = r1
                        android.hardware.devicestate.DeviceState r2 = (android.hardware.devicestate.DeviceState) r2
                        int r2 = r2.getIdentifier()
                        if (r2 != r4) goto L14
                        goto L29
                    L28:
                        r1 = 0
                    L29:
                        android.hardware.devicestate.DeviceState r1 = (android.hardware.devicestate.DeviceState) r1
                        if (r1 == 0) goto L7b
                        r3 = 16
                        r4 = 1001(0x3e9, float:1.403E-42)
                        int[] r4 = new int[]{r3, r4}
                        boolean r4 = r1.hasProperties(r4)
                        if (r4 == 0) goto L3e
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r3 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.REAR_DISPLAY_OUTER_DEFAULT
                        goto L78
                    L3e:
                        boolean r3 = r1.hasProperty(r3)
                        if (r3 == 0) goto L47
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r3 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.REAR_DISPLAY
                        goto L78
                    L47:
                        r3 = 17
                        boolean r3 = r1.hasProperty(r3)
                        if (r3 == 0) goto L52
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r3 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.CONCURRENT_DISPLAY
                        goto L78
                    L52:
                        r3 = 11
                        boolean r3 = r1.hasProperty(r3)
                        if (r3 == 0) goto L5d
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r3 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.FOLDED
                        goto L78
                    L5d:
                        r3 = 12
                        r4 = 2
                        int[] r4 = new int[]{r3, r4}
                        boolean r4 = r1.hasProperties(r4)
                        if (r4 == 0) goto L6d
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r3 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.HALF_FOLDED
                        goto L78
                    L6d:
                        boolean r3 = r1.hasProperty(r3)
                        if (r3 == 0) goto L76
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r3 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.UNFOLDED
                        goto L78
                    L76:
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r3 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.UNKNOWN
                    L78:
                        if (r3 == 0) goto L7b
                        goto L7d
                    L7b:
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r3 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.UNKNOWN
                    L7d:
                        kotlinx.coroutines.channels.ChannelCoroutine r0 = (kotlinx.coroutines.channels.ChannelCoroutine) r0
                        r0.mo3456trySendJP2dKIU(r3)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DeviceStateRepositoryImpl$state$1$callback$1.onDeviceStateChanged(android.hardware.devicestate.DeviceState):void");
                }
            };
            this.this$0.deviceStateManager.registerCallback(this.$executor, (DeviceStateManager.DeviceStateCallback) r1);
            final DeviceStateRepositoryImpl deviceStateRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.display.data.repository.DeviceStateRepositoryImpl$state$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DeviceStateRepositoryImpl.this.deviceStateManager.unregisterCallback(r1);
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
