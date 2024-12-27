package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import java.io.PrintWriter;
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
/* loaded from: classes3.dex */
final class BatteryControllerExtKt$isDevicePluggedIn$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ BatteryController $this_isDevicePluggedIn;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BatteryControllerExtKt$isDevicePluggedIn$1(BatteryController batteryController, Continuation continuation) {
        super(2, continuation);
        this.$this_isDevicePluggedIn = batteryController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BatteryControllerExtKt$isDevicePluggedIn$1 batteryControllerExtKt$isDevicePluggedIn$1 = new BatteryControllerExtKt$isDevicePluggedIn$1(this.$this_isDevicePluggedIn, continuation);
        batteryControllerExtKt$isDevicePluggedIn$1.L$0 = obj;
        return batteryControllerExtKt$isDevicePluggedIn$1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.util.kotlin.BatteryControllerExtKt$isDevicePluggedIn$1$batteryCallback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.util.kotlin.BatteryControllerExtKt$isDevicePluggedIn$1$batteryCallback$1
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
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(z));
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
            ((BatteryControllerImpl) this.$this_isDevicePluggedIn).addCallback(r1);
            final BatteryController batteryController = this.$this_isDevicePluggedIn;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.kotlin.BatteryControllerExtKt$isDevicePluggedIn$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Object invoke() {
                    m2314invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: collision with other method in class */
                public final void m2314invoke() {
                    ((BatteryControllerImpl) BatteryController.this).removeCallback(r1);
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((BatteryControllerExtKt$isDevicePluggedIn$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
