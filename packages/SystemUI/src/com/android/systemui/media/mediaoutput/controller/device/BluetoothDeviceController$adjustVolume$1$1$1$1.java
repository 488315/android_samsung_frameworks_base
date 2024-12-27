package com.android.systemui.media.mediaoutput.controller.device;

import com.android.settingslib.bluetooth.LocalBluetoothManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class BluetoothDeviceController$adjustVolume$1$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LocalBluetoothManager $manager;
    int label;
    final /* synthetic */ BluetoothDeviceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BluetoothDeviceController$adjustVolume$1$1$1$1(BluetoothDeviceController bluetoothDeviceController, LocalBluetoothManager localBluetoothManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bluetoothDeviceController;
        this.$manager = localBluetoothManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BluetoothDeviceController$adjustVolume$1$1$1$1(this.this$0, this.$manager, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BluetoothDeviceController$adjustVolume$1$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0075 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005b A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == 0) goto L2a
            if (r1 == r5) goto L26
            if (r1 == r4) goto L22
            if (r1 == r3) goto L1e
            if (r1 != r2) goto L16
            kotlin.ResultKt.throwOnFailure(r9)
            goto L76
        L16:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L1e:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L5c
        L22:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L51
        L26:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L38
        L2a:
            kotlin.ResultKt.throwOnFailure(r9)
            r8.label = r5
            r6 = 300(0x12c, double:1.48E-321)
            java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r6, r8)
            if (r9 != r0) goto L38
            return r0
        L38:
            com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController r9 = r8.this$0
            com.android.settingslib.bluetooth.LocalBluetoothManager r1 = r8.$manager
            com.android.settingslib.bluetooth.CachedBluetoothDeviceManager r1 = r1.mCachedDeviceManager
            java.util.Collection r1 = r1.getCachedDevicesCopy()
            java.util.List r1 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r1)
            r8.label = r4
            com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$Companion r4 = com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController.Companion
            java.lang.Object r9 = r9.updateDevices(r1, r5, r8)
            if (r9 != r0) goto L51
            return r0
        L51:
            r8.label = r3
            r3 = 200(0xc8, double:9.9E-322)
            java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r3, r8)
            if (r9 != r0) goto L5c
            return r0
        L5c:
            com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController r9 = r8.this$0
            com.android.settingslib.bluetooth.LocalBluetoothManager r1 = r8.$manager
            com.android.settingslib.bluetooth.CachedBluetoothDeviceManager r1 = r1.mCachedDeviceManager
            java.util.Collection r1 = r1.getCachedDevicesCopy()
            java.util.List r1 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r1)
            r8.label = r2
            com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$Companion r2 = com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController.Companion
            r2 = 0
            java.lang.Object r9 = r9.updateDevices(r1, r2, r8)
            if (r9 != r0) goto L76
            return r0
        L76:
            com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController r8 = r8.this$0
            r9 = 0
            r8.updateJob = r9
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$adjustVolume$1$1$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
