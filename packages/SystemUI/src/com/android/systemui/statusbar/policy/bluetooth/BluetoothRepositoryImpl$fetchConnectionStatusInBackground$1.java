package com.android.systemui.statusbar.policy.bluetooth;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.systemui.statusbar.policy.BluetoothControllerImpl;
import com.android.systemui.statusbar.policy.BluetoothControllerImpl$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

final class BluetoothRepositoryImpl$fetchConnectionStatusInBackground$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConnectionStatusFetchedCallback $callback;
    final /* synthetic */ Collection<CachedBluetoothDevice> $currentDevices;
    int label;
    final /* synthetic */ BluetoothRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BluetoothRepositoryImpl$fetchConnectionStatusInBackground$1(BluetoothRepositoryImpl bluetoothRepositoryImpl, Collection<? extends CachedBluetoothDevice> collection, ConnectionStatusFetchedCallback connectionStatusFetchedCallback, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bluetoothRepositoryImpl;
        this.$currentDevices = collection;
        this.$callback = connectionStatusFetchedCallback;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BluetoothRepositoryImpl$fetchConnectionStatusInBackground$1(this.this$0, this.$currentDevices, this.$callback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BluetoothRepositoryImpl$fetchConnectionStatusInBackground$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BluetoothRepositoryImpl bluetoothRepositoryImpl = this.this$0;
            Collection<CachedBluetoothDevice> collection = this.$currentDevices;
            this.label = 1;
            bluetoothRepositoryImpl.getClass();
            obj = BuildersKt.withContext(bluetoothRepositoryImpl.bgDispatcher, new BluetoothRepositoryImpl$fetchConnectionStatus$2(bluetoothRepositoryImpl, collection, null), this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        ConnectionStatusModel connectionStatusModel = (ConnectionStatusModel) obj;
        final BluetoothControllerImpl bluetoothControllerImpl = ((BluetoothControllerImpl$$ExternalSyntheticLambda1) this.$callback).f$0;
        bluetoothControllerImpl.getClass();
        List list = connectionStatusModel.connectedDevices;
        int i2 = connectionStatusModel.maxConnectionState;
        synchronized (bluetoothControllerImpl.mConnectedDevices) {
            ((ArrayList) bluetoothControllerImpl.mConnectedDevices).clear();
            ((ArrayList) bluetoothControllerImpl.mConnectedDevices).addAll(list);
        }
        if (i2 != bluetoothControllerImpl.mConnectionState) {
            bluetoothControllerImpl.mConnectionState = i2;
            bluetoothControllerImpl.mHandler.sendEmptyMessage(2);
        }
        bluetoothControllerImpl.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.BluetoothControllerImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothControllerImpl bluetoothControllerImpl2 = BluetoothControllerImpl.this;
                boolean z = false;
                boolean z2 = false;
                boolean z3 = false;
                for (CachedBluetoothDevice cachedBluetoothDevice : bluetoothControllerImpl2.getDevices()) {
                    for (LocalBluetoothProfile localBluetoothProfile : cachedBluetoothDevice.getProfiles()) {
                        int profileId = localBluetoothProfile.getProfileId();
                        boolean isConnectedProfile = cachedBluetoothDevice.isConnectedProfile(localBluetoothProfile);
                        if (profileId == 1 || profileId == 2 || profileId == 21 || profileId == 22) {
                            z2 |= isConnectedProfile;
                        } else {
                            z3 |= isConnectedProfile;
                        }
                    }
                }
                if (z2 && !z3) {
                    z = true;
                }
                if (z != bluetoothControllerImpl2.mAudioProfileOnly) {
                    bluetoothControllerImpl2.mAudioProfileOnly = z;
                    bluetoothControllerImpl2.mHandler.sendEmptyMessage(2);
                }
            }
        });
        return Unit.INSTANCE;
    }
}
