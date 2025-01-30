package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LocalBluetoothAdapter {
    public static LocalBluetoothAdapter sInstance;
    public final BluetoothAdapter mAdapter;
    public long mLastScan;
    public LocalBluetoothProfileManager mProfileManager;
    public int mState = VideoPlayer.MEDIA_ERROR_SYSTEM;

    private LocalBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.mAdapter = bluetoothAdapter;
    }

    public static synchronized LocalBluetoothAdapter getInstance() {
        LocalBluetoothAdapter localBluetoothAdapter;
        BluetoothAdapter defaultAdapter;
        synchronized (LocalBluetoothAdapter.class) {
            if (sInstance == null && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
                sInstance = new LocalBluetoothAdapter(defaultAdapter);
            }
            localBluetoothAdapter = sInstance;
        }
        return localBluetoothAdapter;
    }

    public final void setBluetoothEnabled(boolean z) {
        BluetoothAdapter bluetoothAdapter = this.mAdapter;
        if (z ? bluetoothAdapter.enable() : bluetoothAdapter.disable()) {
            setBluetoothStateInt(z ? 11 : 13);
        } else if (bluetoothAdapter.getState() != this.mState) {
            setBluetoothStateInt(bluetoothAdapter.getState());
        }
    }

    public final synchronized void setBluetoothStateInt(int i) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        synchronized (this) {
            if (this.mState == i) {
                return;
            }
            this.mState = i;
            if (i == 12 && (localBluetoothProfileManager = this.mProfileManager) != null) {
                localBluetoothProfileManager.updateLocalProfiles();
                BluetoothEventManager bluetoothEventManager = localBluetoothProfileManager.mEventManager;
                bluetoothEventManager.readRestoredDevices();
                bluetoothEventManager.readPairedDevices();
            }
        }
    }
}
