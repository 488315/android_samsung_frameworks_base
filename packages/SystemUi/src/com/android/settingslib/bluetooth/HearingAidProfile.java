package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import com.android.systemui.R;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HearingAidProfile implements LocalBluetoothProfile {
    public final BluetoothAdapter mBluetoothAdapter;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHearingAid mService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HearingAidServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ HearingAidServiceListener(HearingAidProfile hearingAidProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothHearingAid bluetoothHearingAid = (BluetoothHearingAid) bluetoothProfile;
            HearingAidProfile.this.mService = bluetoothHearingAid;
            List<BluetoothDevice> connectedDevices = bluetoothHearingAid.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice remove = connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = HearingAidProfile.this.mDeviceManager.findDevice(remove);
                if (findDevice == null) {
                    Log.d("HearingAidProfile", "HearingAidProfile found new device: " + remove);
                    HearingAidProfile hearingAidProfile = HearingAidProfile.this;
                    findDevice = hearingAidProfile.mDeviceManager.addDevice(hearingAidProfile.mProfileManager, remove);
                }
                findDevice.onProfileStateChanged(HearingAidProfile.this, 2);
                findDevice.refresh();
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = HearingAidProfile.this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                cachedBluetoothDeviceManager.mHearingAidDeviceManager.updateHearingAidsDevices();
            }
            HearingAidProfile hearingAidProfile2 = HearingAidProfile.this;
            hearingAidProfile2.mIsProfileReady = true;
            hearingAidProfile2.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            HearingAidProfile hearingAidProfile = HearingAidProfile.this;
            hearingAidProfile.mIsProfileReady = false;
            hearingAidProfile.mProfileManager.callServiceDisconnectedListeners();
        }

        private HearingAidServiceListener() {
        }
    }

    public HearingAidProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        defaultAdapter.getProfileProxy(context, new HearingAidServiceListener(this, 0), 21);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final void finalize() {
        Log.d("HearingAidProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(21, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HearingAidProfile", "Error cleaning up Hearing Aid proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHearingAid bluetoothHearingAid = this.mService;
        if (bluetoothHearingAid == null) {
            return 0;
        }
        return bluetoothHearingAid.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.list_ic_hearing_aid;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 21;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothHearingAid bluetoothHearingAid = this.mService;
        if (bluetoothHearingAid == null || bluetoothDevice == null) {
            return false;
        }
        return bluetoothHearingAid.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "HearingAid";
    }
}
