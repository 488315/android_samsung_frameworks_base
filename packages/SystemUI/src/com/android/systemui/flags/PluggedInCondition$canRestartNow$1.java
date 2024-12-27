package com.android.systemui.flags;

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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class PluggedInCondition$canRestartNow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PluggedInCondition this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PluggedInCondition$canRestartNow$1(PluggedInCondition pluggedInCondition, Continuation continuation) {
        super(2, continuation);
        this.this$0 = pluggedInCondition;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PluggedInCondition$canRestartNow$1 pluggedInCondition$canRestartNow$1 = new PluggedInCondition$canRestartNow$1(this.this$0, continuation);
        pluggedInCondition$canRestartNow$1.L$0 = obj;
        return pluggedInCondition$canRestartNow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PluggedInCondition$canRestartNow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.flags.PluggedInCondition$canRestartNow$1$batteryCallback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.flags.PluggedInCondition$canRestartNow$1$batteryCallback$1
                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public final void onBatteryLevelChanged(int i2, boolean z, boolean z2) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            ((BatteryControllerImpl) ((BatteryController) this.this$0.batteryControllerLazy.get())).addCallback(r1);
            ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.valueOf(((BatteryControllerImpl) ((BatteryController) this.this$0.batteryControllerLazy.get())).mPluggedIn));
            final PluggedInCondition pluggedInCondition = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.flags.PluggedInCondition$canRestartNow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((BatteryControllerImpl) ((BatteryController) PluggedInCondition.this.batteryControllerLazy.get())).removeCallback(r1);
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
