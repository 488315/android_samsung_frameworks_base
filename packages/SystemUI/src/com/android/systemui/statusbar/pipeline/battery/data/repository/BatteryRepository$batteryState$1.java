package com.android.systemui.statusbar.pipeline.battery.data.repository;

import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class BatteryRepository$batteryState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BatteryRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BatteryRepository$batteryState$1(BatteryRepository batteryRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = batteryRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BatteryRepository$batteryState$1 batteryRepository$batteryState$1 = new BatteryRepository$batteryState$1(this.this$0, continuation);
        batteryRepository$batteryState$1.L$0 = obj;
        return batteryRepository$batteryState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BatteryRepository$batteryState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$batteryState$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$batteryState$1$callback$1
                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public final void onBatteryLevelChanged(final int i2, final boolean z, boolean z2) {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(new Function1() { // from class: com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$batteryState$1$callback$1$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj2) {
                            return BatteryCallbackState.copy$default((BatteryCallbackState) obj2, Integer.valueOf(i2), z, false, false, 28);
                        }
                    });
                }

                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public final void onBatteryUnknownStateChanged(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(new BatteryRepository$batteryState$1$callback$1$$ExternalSyntheticLambda0(z, 1));
                }

                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public final void onIsBatteryDefenderChanged(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(new BatteryRepository$batteryState$1$callback$1$$ExternalSyntheticLambda0(z, 2));
                }

                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public final void onPowerSaveChanged(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(new BatteryRepository$batteryState$1$callback$1$$ExternalSyntheticLambda0(z, 0));
                }
            };
            ((BatteryControllerImpl) this.this$0.controller).addCallback(r1);
            final BatteryRepository batteryRepository = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$batteryState$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((BatteryControllerImpl) BatteryRepository.this.controller).removeCallback(r1);
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
