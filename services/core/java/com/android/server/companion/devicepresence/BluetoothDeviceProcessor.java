package com.android.server.companion.devicepresence;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.companion.AssociationInfo;
import android.net.MacAddress;
import android.os.ParcelUuid;
import android.os.UserHandle;

import com.android.internal.util.ArrayUtils;
import com.android.server.companion.association.AssociationStore;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BluetoothDeviceProcessor extends BluetoothAdapter.BluetoothConnectionCallback
        implements AssociationStore.OnChangeListener {
    public final Map mAllConnectedDevices = new HashMap();
    public final AssociationStore mAssociationStore;
    public final DevicePresenceProcessor mCallback;
    public final ObservableUuidStore mObservableUuidStore;

    public BluetoothDeviceProcessor(
            AssociationStore associationStore,
            ObservableUuidStore observableUuidStore,
            DevicePresenceProcessor devicePresenceProcessor) {
        this.mAssociationStore = associationStore;
        this.mObservableUuidStore = observableUuidStore;
        this.mCallback = devicePresenceProcessor;
    }

    @Override // com.android.server.companion.association.AssociationStore.OnChangeListener
    public final void onAssociationAdded(AssociationInfo associationInfo) {
        if (((HashMap) this.mAllConnectedDevices)
                .containsKey(associationInfo.getDeviceMacAddress())) {
            this.mCallback.onBluetoothCompanionDeviceConnected(
                    associationInfo.getId(), associationInfo.getUserId());
        }
    }

    public final void onDeviceConnected(BluetoothDevice bluetoothDevice) {
        if (((HashMap) this.mAllConnectedDevices)
                        .put(MacAddress.fromString(bluetoothDevice.getAddress()), bluetoothDevice)
                != null) {
            return;
        }
        onDeviceConnectivityChanged(bluetoothDevice, true);
    }

    public final void onDeviceConnectivityChanged(BluetoothDevice bluetoothDevice, boolean z) {
        List<ObservableUuid> readObservableUuidsFromCache;
        int myUserId = UserHandle.myUserId();
        for (AssociationInfo associationInfo :
                this.mAssociationStore.getActiveAssociationsByAddress(
                        bluetoothDevice.getAddress())) {
            if (associationInfo.isNotifyOnDeviceNearby()) {
                int id = associationInfo.getId();
                if (z) {
                    this.mCallback.onBluetoothCompanionDeviceConnected(
                            id, associationInfo.getUserId());
                } else {
                    this.mCallback.onBluetoothCompanionDeviceDisconnected(
                            id, associationInfo.getUserId());
                }
            }
        }
        ObservableUuidStore observableUuidStore = this.mObservableUuidStore;
        synchronized (observableUuidStore.mLock) {
            readObservableUuidsFromCache =
                    observableUuidStore.readObservableUuidsFromCache(myUserId);
        }
        ParcelUuid[] uuids = bluetoothDevice.getUuids();
        List emptyList = ArrayUtils.isEmpty(uuids) ? Collections.emptyList() : Arrays.asList(uuids);
        for (ObservableUuid observableUuid : readObservableUuidsFromCache) {
            if (emptyList.contains(observableUuid.mUuid)) {
                this.mCallback.onDevicePresenceEventByUuid(observableUuid, z ? 2 : 3);
            }
        }
    }

    public final void onDeviceDisconnected(BluetoothDevice bluetoothDevice, int i) {
        if (((HashMap) this.mAllConnectedDevices)
                        .remove(MacAddress.fromString(bluetoothDevice.getAddress()))
                == null) {
            return;
        }
        onDeviceConnectivityChanged(bluetoothDevice, false);
    }
}
