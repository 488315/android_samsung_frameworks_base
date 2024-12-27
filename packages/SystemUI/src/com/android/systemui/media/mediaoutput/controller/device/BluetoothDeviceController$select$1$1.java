package com.android.systemui.media.mediaoutput.controller.device;

import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class BluetoothDeviceController$select$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LocalBluetoothManager $manager;
    int label;
    final /* synthetic */ BluetoothDeviceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BluetoothDeviceController$select$1$1(BluetoothDeviceController bluetoothDeviceController, LocalBluetoothManager localBluetoothManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bluetoothDeviceController;
        this.$manager = localBluetoothManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BluetoothDeviceController$select$1$1(this.this$0, this.$manager, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BluetoothDeviceController$select$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BluetoothDeviceController bluetoothDeviceController = this.this$0;
            List list = CollectionsKt___CollectionsKt.toList(this.$manager.mCachedDeviceManager.getCachedDevicesCopy());
            this.label = 1;
            BluetoothDeviceController.Companion companion = BluetoothDeviceController.Companion;
            if (bluetoothDeviceController.updateDevices(list, false, this) == coroutineSingletons) {
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
