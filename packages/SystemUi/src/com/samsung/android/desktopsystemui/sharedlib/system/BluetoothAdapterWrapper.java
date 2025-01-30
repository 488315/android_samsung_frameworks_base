package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BluetoothAdapterWrapper {
    private static final BluetoothAdapter sBluetoothAdapter;
    private static final BluetoothManager sBluetoothService;
    private static final BluetoothAdapterWrapper sInstance = new BluetoothAdapterWrapper();

    static {
        BluetoothManager bluetoothManager = (BluetoothManager) AppGlobals.getInitialApplication().getSystemService(BluetoothManager.class);
        sBluetoothService = bluetoothManager;
        sBluetoothAdapter = bluetoothManager.getAdapter();
    }

    private BluetoothAdapterWrapper() {
    }

    public static BluetoothAdapterWrapper getInstance() {
        return sInstance;
    }

    public List<BluetoothDevice> getConnectedDevices() {
        Set<BluetoothDevice> bondedDevices = sBluetoothAdapter.getBondedDevices();
        if (bondedDevices != null) {
            return (List) bondedDevices.stream().filter(new BluetoothAdapterWrapper$$ExternalSyntheticLambda0()).collect(Collectors.toList());
        }
        return null;
    }
}
