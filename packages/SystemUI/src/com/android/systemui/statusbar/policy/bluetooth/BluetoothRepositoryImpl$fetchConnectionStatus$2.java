package com.android.systemui.statusbar.policy.bluetooth;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class BluetoothRepositoryImpl$fetchConnectionStatus$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Collection<CachedBluetoothDevice> $currentDevices;
    int label;
    final /* synthetic */ BluetoothRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BluetoothRepositoryImpl$fetchConnectionStatus$2(BluetoothRepositoryImpl bluetoothRepositoryImpl, Collection<? extends CachedBluetoothDevice> collection, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bluetoothRepositoryImpl;
        this.$currentDevices = collection;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BluetoothRepositoryImpl$fetchConnectionStatus$2(this.this$0, this.$currentDevices, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BluetoothRepositoryImpl$fetchConnectionStatus$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        LocalBluetoothAdapter localBluetoothAdapter;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LocalBluetoothManager localBluetoothManager = this.this$0.localBluetoothManager;
        int connectionState = (localBluetoothManager == null || (localBluetoothAdapter = localBluetoothManager.mLocalAdapter) == null) ? 0 : localBluetoothAdapter.mAdapter.getConnectionState();
        if (!this.$currentDevices.isEmpty()) {
            Iterator<T> it = this.$currentDevices.iterator();
            if (!it.hasNext()) {
                throw new NoSuchElementException();
            }
            int maxConnectionState = ((CachedBluetoothDevice) it.next()).getMaxConnectionState();
            while (it.hasNext()) {
                int maxConnectionState2 = ((CachedBluetoothDevice) it.next()).getMaxConnectionState();
                if (maxConnectionState < maxConnectionState2) {
                    maxConnectionState = maxConnectionState2;
                }
            }
            if (maxConnectionState >= connectionState) {
                connectionState = maxConnectionState;
            }
        }
        Collection<CachedBluetoothDevice> collection = this.$currentDevices;
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : collection) {
            if (((CachedBluetoothDevice) obj2).isConnected()) {
                arrayList.add(obj2);
            }
        }
        return new ConnectionStatusModel((arrayList.isEmpty() && connectionState == 2) ? 0 : connectionState, arrayList);
    }
}
