package com.android.server.companion.presence;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.companion.AssociationInfo;
import android.net.MacAddress;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.companion.AssociationStore;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class BluetoothCompanionDeviceConnectionListener extends BluetoothAdapter.BluetoothConnectionCallback implements AssociationStore.OnChangeListener {
    public final AssociationStore mAssociationStore;
    public final Callback mCallback;
    public final UserManager mUserManager;
    public final Map mAllConnectedDevices = new HashMap();
    public final SparseArray mPendingConnectedDevices = new SparseArray();

    public interface Callback {
        void onBluetoothCompanionDeviceConnected(int i);

        void onBluetoothCompanionDeviceDisconnected(int i);
    }

    @Override // com.android.server.companion.AssociationStore.OnChangeListener
    public void onAssociationRemoved(AssociationInfo associationInfo) {
    }

    public BluetoothCompanionDeviceConnectionListener(UserManager userManager, AssociationStore associationStore, Callback callback) {
        this.mAssociationStore = associationStore;
        this.mCallback = callback;
        this.mUserManager = userManager;
    }

    public void init(BluetoothAdapter bluetoothAdapter) {
        bluetoothAdapter.registerBluetoothConnectionCallback(new HandlerExecutor(Handler.getMain()), this);
        this.mAssociationStore.registerListener(this);
    }

    public void onDeviceConnected(BluetoothDevice bluetoothDevice) {
        MacAddress fromString = MacAddress.fromString(bluetoothDevice.getAddress());
        int myUserId = UserHandle.myUserId();
        if (this.mAllConnectedDevices.put(fromString, bluetoothDevice) != null) {
            return;
        }
        if (!this.mUserManager.isUserUnlockingOrUnlocked(UserHandle.myUserId())) {
            Slog.i("CDM_BluetoothCompanionDeviceConnectionListener", "Current user is not in unlocking or unlocked stage yet. Notify the application when the phone is unlocked");
            synchronized (this.mPendingConnectedDevices) {
                Set set = (Set) this.mPendingConnectedDevices.get(myUserId, new HashSet());
                set.add(bluetoothDevice);
                this.mPendingConnectedDevices.put(myUserId, set);
            }
            return;
        }
        onDeviceConnectivityChanged(bluetoothDevice, true);
    }

    public void onDeviceDisconnected(BluetoothDevice bluetoothDevice, int i) {
        MacAddress fromString = MacAddress.fromString(bluetoothDevice.getAddress());
        int myUserId = UserHandle.myUserId();
        if (this.mAllConnectedDevices.remove(fromString) == null) {
            return;
        }
        if (!this.mUserManager.isUserUnlockingOrUnlocked(myUserId)) {
            synchronized (this.mPendingConnectedDevices) {
                Set set = (Set) this.mPendingConnectedDevices.get(myUserId);
                if (set != null) {
                    set.remove(bluetoothDevice);
                }
            }
            return;
        }
        onDeviceConnectivityChanged(bluetoothDevice, false);
    }

    public final void onDeviceConnectivityChanged(BluetoothDevice bluetoothDevice, boolean z) {
        Iterator it = this.mAssociationStore.getAssociationsByAddress(bluetoothDevice.getAddress()).iterator();
        while (it.hasNext()) {
            int id = ((AssociationInfo) it.next()).getId();
            if (z) {
                this.mCallback.onBluetoothCompanionDeviceConnected(id);
            } else {
                this.mCallback.onBluetoothCompanionDeviceDisconnected(id);
            }
        }
    }

    @Override // com.android.server.companion.AssociationStore.OnChangeListener
    public void onAssociationAdded(AssociationInfo associationInfo) {
        if (this.mAllConnectedDevices.containsKey(associationInfo.getDeviceMacAddress())) {
            this.mCallback.onBluetoothCompanionDeviceConnected(associationInfo.getId());
        }
    }

    @Override // com.android.server.companion.AssociationStore.OnChangeListener
    public void onAssociationUpdated(AssociationInfo associationInfo, boolean z) {
        if (z) {
            throw new IllegalArgumentException("Address changes are not supported.");
        }
    }
}
