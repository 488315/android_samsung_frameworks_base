package com.android.server.media;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothProfile;
import android.content.Context;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BluetoothProfileMonitor {
    public BluetoothA2dp mA2dpProfile;
    public final BluetoothAdapter mBluetoothAdapter;
    public final Context mContext;
    public BluetoothHearingAid mHearingAidProfile;
    public BluetoothLeAudio mLeAudioProfile;
    public final ProfileListener mProfileListener = new ProfileListener();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProfileListener implements BluetoothProfile.ServiceListener {
        public ProfileListener() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            synchronized (BluetoothProfileMonitor.this) {
                try {
                    if (i == 2) {
                        BluetoothProfileMonitor.this.mA2dpProfile =
                                (BluetoothA2dp) bluetoothProfile;
                    } else if (i == 21) {
                        BluetoothProfileMonitor.this.mHearingAidProfile =
                                (BluetoothHearingAid) bluetoothProfile;
                    } else if (i == 22) {
                        BluetoothProfileMonitor.this.mLeAudioProfile =
                                (BluetoothLeAudio) bluetoothProfile;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            synchronized (BluetoothProfileMonitor.this) {
                try {
                    if (i == 2) {
                        BluetoothProfileMonitor.this.mA2dpProfile = null;
                    } else if (i == 21) {
                        BluetoothProfileMonitor.this.mHearingAidProfile = null;
                    } else if (i == 22) {
                        BluetoothProfileMonitor.this.mLeAudioProfile = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public BluetoothProfileMonitor(Context context, BluetoothAdapter bluetoothAdapter) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mBluetoothAdapter = bluetoothAdapter;
    }

    public final long getGroupId(BluetoothDevice bluetoothDevice, int i) {
        synchronized (this) {
            long j = -1;
            try {
                if (i == 2) {
                    return -1L;
                }
                if (i == 21) {
                    BluetoothHearingAid bluetoothHearingAid = this.mHearingAidProfile;
                    if (bluetoothHearingAid != null) {
                        j = bluetoothHearingAid.getHiSyncId(bluetoothDevice);
                    }
                    return j;
                }
                if (i == 22) {
                    BluetoothLeAudio bluetoothLeAudio = this.mLeAudioProfile;
                    if (bluetoothLeAudio != null) {
                        j = bluetoothLeAudio.getGroupId(bluetoothDevice);
                    }
                    return j;
                }
                throw new IllegalArgumentException(i + " is not supported as Bluetooth profile");
            } finally {
            }
        }
    }

    public final boolean isProfileSupported(BluetoothDevice bluetoothDevice, int i) {
        BluetoothProfile bluetoothProfile;
        synchronized (this) {
            try {
                if (i == 2) {
                    bluetoothProfile = this.mA2dpProfile;
                } else if (i == 21) {
                    bluetoothProfile = this.mHearingAidProfile;
                } else {
                    if (i != 22) {
                        throw new IllegalArgumentException(
                                i + " is not supported as Bluetooth profile");
                    }
                    bluetoothProfile = this.mLeAudioProfile;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (bluetoothProfile == null) {
            return false;
        }
        return bluetoothProfile.getConnectedDevices().contains(bluetoothDevice);
    }
}
