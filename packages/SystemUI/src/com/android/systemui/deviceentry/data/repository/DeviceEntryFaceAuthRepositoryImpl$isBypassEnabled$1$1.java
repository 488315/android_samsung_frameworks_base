package com.android.systemui.deviceentry.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class DeviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardBypassController $it;
    private /* synthetic */ Object L$0;
    int label;

    public DeviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1(KeyguardBypassController keyguardBypassController, Continuation continuation) {
        super(2, continuation);
        this.$it = keyguardBypassController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1 deviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1 = new DeviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1(this.$it, continuation);
        deviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1.L$0 = obj;
        return deviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1$callback$1
                @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
                public final void onBypassStateChanged(boolean z) {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    Boolean valueOf = Boolean.valueOf(z);
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, valueOf, "DeviceEntryFaceAuthRepository", "BypassStateChanged");
                }
            };
            this.$it.registerOnBypassStateChangedListener(r1);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            Boolean valueOf = Boolean.valueOf(this.$it.getBypassEnabled());
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, valueOf, "DeviceEntryFaceAuthRepository", "BypassStateChanged");
            final KeyguardBypassController keyguardBypassController = this.$it;
            Function0 function0 = new Function0() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$isBypassEnabled$1$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    KeyguardBypassController.this.unregisterOnBypassStateChangedListener(r1);
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
