package com.android.systemui.statusbar.policy.bluetooth;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.systemui.statusbar.policy.BluetoothControllerImpl;
import com.android.systemui.statusbar.policy.BluetoothControllerImpl$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class BluetoothRepositoryImpl implements BluetoothRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final LocalBluetoothManager localBluetoothManager;
    public final CoroutineScope scope;

    /* renamed from: com.android.systemui.statusbar.policy.bluetooth.BluetoothRepositoryImpl$fetchConnectionStatusInBackground$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ConnectionStatusFetchedCallback $callback;
        final /* synthetic */ Collection<CachedBluetoothDevice> $currentDevices;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(Collection<? extends CachedBluetoothDevice> collection, ConnectionStatusFetchedCallback connectionStatusFetchedCallback, Continuation continuation) {
            super(2, continuation);
            this.$currentDevices = collection;
            this.$callback = connectionStatusFetchedCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BluetoothRepositoryImpl.this.new AnonymousClass1(this.$currentDevices, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                BluetoothRepositoryImpl bluetoothRepositoryImpl = BluetoothRepositoryImpl.this;
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
            final BluetoothControllerImpl bluetoothControllerImpl = ((BluetoothControllerImpl$$ExternalSyntheticLambda0) this.$callback).f$0;
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
            bluetoothControllerImpl.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.BluetoothControllerImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothControllerImpl bluetoothControllerImpl2 = bluetoothControllerImpl;
                    LocalBluetoothManager localBluetoothManager = bluetoothControllerImpl2.mLocalBluetoothManager;
                    boolean z = false;
                    boolean z2 = false;
                    boolean z3 = false;
                    for (CachedBluetoothDevice cachedBluetoothDevice : localBluetoothManager != null ? localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy() : Collections.EMPTY_LIST) {
                        for (LocalBluetoothProfile localBluetoothProfile : cachedBluetoothDevice.getProfiles()) {
                            int profileId = localBluetoothProfile.getProfileId();
                            boolean zIsConnectedProfile = cachedBluetoothDevice.isConnectedProfile(localBluetoothProfile);
                            if (profileId == 1 || profileId == 2 || profileId == 21 || profileId == 22) {
                                z2 |= zIsConnectedProfile;
                            } else {
                                z3 |= zIsConnectedProfile;
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

    public BluetoothRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, LocalBluetoothManager localBluetoothManager) {
        this.scope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.localBluetoothManager = localBluetoothManager;
    }

    public final void fetchConnectionStatusInBackground(Collection collection, BluetoothControllerImpl$$ExternalSyntheticLambda0 bluetoothControllerImpl$$ExternalSyntheticLambda0) {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(collection, bluetoothControllerImpl$$ExternalSyntheticLambda0, null), 7);
    }
}
