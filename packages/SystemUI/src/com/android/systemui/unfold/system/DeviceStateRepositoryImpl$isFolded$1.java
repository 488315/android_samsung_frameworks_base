package com.android.systemui.unfold.system;

import com.android.systemui.unfold.updates.FoldProvider;
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

final class DeviceStateRepositoryImpl$isFolded$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceStateRepositoryImpl this$0;

    public DeviceStateRepositoryImpl$isFolded$1(DeviceStateRepositoryImpl deviceStateRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceStateRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceStateRepositoryImpl$isFolded$1 deviceStateRepositoryImpl$isFolded$1 = new DeviceStateRepositoryImpl$isFolded$1(this.this$0, continuation);
        deviceStateRepositoryImpl$isFolded$1.L$0 = obj;
        return deviceStateRepositoryImpl$isFolded$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceStateRepositoryImpl$isFolded$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final FoldProvider.FoldCallback foldCallback = new FoldProvider.FoldCallback() { // from class: com.android.systemui.unfold.system.DeviceStateRepositoryImpl$isFolded$1$callback$1
                @Override // com.android.systemui.unfold.updates.FoldProvider.FoldCallback
                public final void onFoldUpdated(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            DeviceStateRepositoryImpl deviceStateRepositoryImpl = this.this$0;
            deviceStateRepositoryImpl.foldProvider.registerCallback(foldCallback, deviceStateRepositoryImpl.executor);
            final DeviceStateRepositoryImpl deviceStateRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.unfold.system.DeviceStateRepositoryImpl$isFolded$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DeviceStateRepositoryImpl.this.foldProvider.unregisterCallback(foldCallback);
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
