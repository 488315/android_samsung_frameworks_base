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
import kotlinx.coroutines.DelayKt;

/* loaded from: classes2.dex */
final class BluetoothDeviceController$adjustVolume$2$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LocalBluetoothManager $manager;
    int label;
    final /* synthetic */ BluetoothDeviceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BluetoothDeviceController$adjustVolume$2$1$1$1(BluetoothDeviceController bluetoothDeviceController, LocalBluetoothManager localBluetoothManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bluetoothDeviceController;
        this.$manager = localBluetoothManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BluetoothDeviceController$adjustVolume$2$1$1$1(this.this$0, this.$manager, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BluetoothDeviceController$adjustVolume$2$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0073, code lost:
    
        if (r9.updateDevices(r1, false, r8) != r0) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005c  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(300L, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            if (i == 2) {
                ResultKt.throwOnFailure(obj);
                this.label = 3;
                if (DelayKt.delay(200L, this) != coroutineSingletons) {
                    BluetoothDeviceController bluetoothDeviceController = this.this$0;
                    List listSorted = CollectionsKt___CollectionsKt.sorted(this.$manager.mCachedDeviceManager.getCachedDevicesCopy());
                    this.label = 4;
                    BluetoothDeviceController.Companion companion = BluetoothDeviceController.Companion;
                }
                return coroutineSingletons;
            }
            if (i != 3) {
                if (i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.updateJob = null;
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            BluetoothDeviceController bluetoothDeviceController2 = this.this$0;
            List listSorted2 = CollectionsKt___CollectionsKt.sorted(this.$manager.mCachedDeviceManager.getCachedDevicesCopy());
            this.label = 4;
            BluetoothDeviceController.Companion companion2 = BluetoothDeviceController.Companion;
        }
        BluetoothDeviceController bluetoothDeviceController3 = this.this$0;
        List listSorted3 = CollectionsKt___CollectionsKt.sorted(this.$manager.mCachedDeviceManager.getCachedDevicesCopy());
        this.label = 2;
        BluetoothDeviceController.Companion companion3 = BluetoothDeviceController.Companion;
        if (bluetoothDeviceController3.updateDevices(listSorted3, true, this) != coroutineSingletons) {
            this.label = 3;
            if (DelayKt.delay(200L, this) != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }
}
