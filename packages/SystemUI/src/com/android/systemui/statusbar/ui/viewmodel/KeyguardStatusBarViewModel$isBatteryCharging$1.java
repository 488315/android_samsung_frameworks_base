package com.android.systemui.statusbar.ui.viewmodel;

import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
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

final class KeyguardStatusBarViewModel$isBatteryCharging$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ BatteryController $batteryController;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardStatusBarViewModel$isBatteryCharging$1(BatteryController batteryController, Continuation continuation) {
        super(2, continuation);
        this.$batteryController = batteryController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardStatusBarViewModel$isBatteryCharging$1 keyguardStatusBarViewModel$isBatteryCharging$1 = new KeyguardStatusBarViewModel$isBatteryCharging$1(this.$batteryController, continuation);
        keyguardStatusBarViewModel$isBatteryCharging$1.L$0 = obj;
        return keyguardStatusBarViewModel$isBatteryCharging$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardStatusBarViewModel$isBatteryCharging$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.ui.viewmodel.KeyguardStatusBarViewModel$isBatteryCharging$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.ui.viewmodel.KeyguardStatusBarViewModel$isBatteryCharging$1$callback$1
                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public final void onBatteryLevelChanged(int i2, boolean z, boolean z2) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(z2));
                }
            };
            ((BatteryControllerImpl) this.$batteryController).addCallback(r1);
            final BatteryController batteryController = this.$batteryController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.ui.viewmodel.KeyguardStatusBarViewModel$isBatteryCharging$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((BatteryControllerImpl) BatteryController.this).removeCallback(r1);
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
