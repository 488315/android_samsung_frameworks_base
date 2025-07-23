package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import java.io.PrintWriter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class BatteryControllerExtKt$getBatteryLevel$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ BatteryController $this_getBatteryLevel;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BatteryControllerExtKt$getBatteryLevel$1(BatteryController batteryController, Continuation continuation) {
        super(2, continuation);
        this.$this_getBatteryLevel = batteryController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(BatteryController batteryController, BatteryControllerExtKt$getBatteryLevel$1$batteryCallback$1 batteryControllerExtKt$getBatteryLevel$1$batteryCallback$1) {
        ((BatteryControllerImpl) batteryController).removeCallback(batteryControllerExtKt$getBatteryLevel$1$batteryCallback$1);
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BatteryControllerExtKt$getBatteryLevel$1 batteryControllerExtKt$getBatteryLevel$1 = new BatteryControllerExtKt$getBatteryLevel$1(this.$this_getBatteryLevel, continuation);
        batteryControllerExtKt$getBatteryLevel$1.L$0 = obj;
        return batteryControllerExtKt$getBatteryLevel$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            BatteryController.BatteryStateChangeCallback batteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.util.kotlin.BatteryControllerExtKt$getBatteryLevel$1$batteryCallback$1
                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback, com.android.systemui.Dumpable
                public void dump(PrintWriter printWriter, String[] strArr) {
                    printWriter.println(this);
                }

                public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2, int i3, int i4, int i5, boolean z3) {
                }

                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public /* bridge */ /* synthetic */ void onBatteryLevelChanged(int i2, boolean z, boolean z2, int i3, int i4, int i5, boolean z3, int i6) {
                }

                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public void onBatteryLevelChanged(int i2, boolean z, boolean z2) {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(Integer.valueOf(i2));
                }

                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public /* bridge */ /* synthetic */ void onBatteryUnknownStateChanged(boolean z) {
                }

                public /* bridge */ /* synthetic */ void onExtremeBatterySaverChanged(boolean z) {
                }

                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public /* bridge */ /* synthetic */ void onIsBatteryDefenderChanged(boolean z) {
                }

                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public /* bridge */ /* synthetic */ void onIsIncompatibleChargingChanged(boolean z) {
                }

                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public /* bridge */ /* synthetic */ void onPowerSaveChanged(boolean z) {
                }

                @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                public /* bridge */ /* synthetic */ void onWirelessChargingChanged(boolean z) {
                }

                public /* bridge */ /* synthetic */ void onReverseChanged(boolean z, int i2, String str) {
                }
            };
            ((BatteryControllerImpl) this.$this_getBatteryLevel).addCallback(batteryStateChangeCallback);
            BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0 batteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0 = new BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0(this.$this_getBatteryLevel, batteryStateChangeCallback, 0);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, batteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((BatteryControllerExtKt$getBatteryLevel$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
