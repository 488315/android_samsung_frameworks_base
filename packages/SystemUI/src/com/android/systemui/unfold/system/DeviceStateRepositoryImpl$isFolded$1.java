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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DeviceStateRepositoryImpl$isFolded$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceStateRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.unfold.system.DeviceStateRepositoryImpl$isFolded$1$callback$1, com.android.systemui.unfold.updates.FoldProvider$FoldCallback] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new FoldProvider.FoldCallback() { // from class: com.android.systemui.unfold.system.DeviceStateRepositoryImpl$isFolded$1$callback$1
                @Override // com.android.systemui.unfold.updates.FoldProvider.FoldCallback
                public final void onFoldUpdated(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            DeviceStateRepositoryImpl deviceStateRepositoryImpl = this.this$0;
            deviceStateRepositoryImpl.foldProvider.registerCallback(r1, deviceStateRepositoryImpl.executor);
            final DeviceStateRepositoryImpl deviceStateRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.unfold.system.DeviceStateRepositoryImpl$isFolded$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DeviceStateRepositoryImpl.this.foldProvider.unregisterCallback(r1);
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
