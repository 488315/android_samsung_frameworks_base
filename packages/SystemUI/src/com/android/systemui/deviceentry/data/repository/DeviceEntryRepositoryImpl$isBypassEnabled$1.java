package com.android.systemui.deviceentry.data.repository;

import com.android.systemui.statusbar.phone.KeyguardBypassController;
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

final class DeviceEntryRepositoryImpl$isBypassEnabled$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceEntryRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryRepositoryImpl$isBypassEnabled$1(DeviceEntryRepositoryImpl deviceEntryRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryRepositoryImpl$isBypassEnabled$1 deviceEntryRepositoryImpl$isBypassEnabled$1 = new DeviceEntryRepositoryImpl$isBypassEnabled$1(this.this$0, continuation);
        deviceEntryRepositoryImpl$isBypassEnabled$1.L$0 = obj;
        return deviceEntryRepositoryImpl$isBypassEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryRepositoryImpl$isBypassEnabled$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl$isBypassEnabled$1$listener$1, com.android.systemui.statusbar.phone.KeyguardBypassController$OnBypassStateChangedListener] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl$isBypassEnabled$1$listener$1
                @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
                public final void onBypassStateChanged(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            this.this$0.keyguardBypassController.registerOnBypassStateChangedListener(r1);
            final DeviceEntryRepositoryImpl deviceEntryRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl$isBypassEnabled$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DeviceEntryRepositoryImpl.this.keyguardBypassController.unregisterOnBypassStateChangedListener(r1);
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
