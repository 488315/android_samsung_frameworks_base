package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothPbap;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PbapServerProfile implements LocalBluetoothProfile {
    public static final String NAME = "PBAP Server";
    public boolean mIsProfileReady;
    public BluetoothPbap mService;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PbapServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ PbapServiceListener(PbapServerProfile pbapServerProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            PbapServerProfile pbapServerProfile = PbapServerProfile.this;
            pbapServerProfile.mService = (BluetoothPbap) bluetoothProfile;
            pbapServerProfile.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            PbapServerProfile.this.mIsProfileReady = false;
        }

        private PbapServiceListener() {
        }
    }

    static {
        ParcelUuid[] parcelUuidArr = new ParcelUuid[3];
        ParcelUuid parcelUuid = BluetoothUuid.HSP;
        ParcelUuid parcelUuid2 = BluetoothUuid.HFP;
        ParcelUuid parcelUuid3 = BluetoothUuid.PBAP_PCE;
    }

    public PbapServerProfile(Context context) {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new PbapServiceListener(this, 0), 6);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("PbapServerProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(6, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("PbapServerProfile", "Error cleaning up PBAP proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothPbap bluetoothPbap = this.mService;
        if (bluetoothPbap == null) {
            return 0;
        }
        return bluetoothPbap.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.list_ic_accessphonebook;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 6;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        return false;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public final String toString() {
        return NAME;
    }
}
