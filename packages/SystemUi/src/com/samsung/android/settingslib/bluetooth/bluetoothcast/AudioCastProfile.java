package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.bluetooth.SemBluetoothCastProfile;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AudioCastProfile implements LocalBluetoothCastProfile {
    public final String TAG = "AudioCastProfile";
    public final C47661 mAudioCastProfileListener;
    public final CachedBluetoothCastDeviceManager mCastDeviceManager;
    public final LocalBluetoothCastProfileManager mCastProfileManager;
    public final Context mContext;
    public SemBluetoothAudioCast mService;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.bluetooth.SemBluetoothCastProfile$BluetoothCastProfileListener, com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile$1] */
    public AudioCastProfile(Context context, CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager, LocalBluetoothCastProfileManager localBluetoothCastProfileManager) {
        ?? r1 = new SemBluetoothCastProfile.BluetoothCastProfileListener() { // from class: com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile.1
            public final void onServiceConnected(SemBluetoothCastProfile semBluetoothCastProfile) {
                int connectionState;
                Log.d(AudioCastProfile.this.TAG, "AudioCastProfile Proxy object connected");
                SemBluetoothAudioCast semBluetoothAudioCast = (SemBluetoothAudioCast) semBluetoothCastProfile;
                AudioCastProfile.this.mService = semBluetoothAudioCast;
                List<SemBluetoothCastDevice> audioCastDevices = semBluetoothAudioCast.getAudioCastDevices();
                if (audioCastDevices == null || audioCastDevices.size() <= 0) {
                    return;
                }
                for (SemBluetoothCastDevice semBluetoothCastDevice : audioCastDevices) {
                    int remoteDeviceRole = semBluetoothCastDevice.getRemoteDeviceRole();
                    if (remoteDeviceRole == 2) {
                        CachedBluetoothCastDevice findCastDevice = AudioCastProfile.this.mCastDeviceManager.findCastDevice(semBluetoothCastDevice);
                        if (findCastDevice == null && ((connectionState = AudioCastProfile.this.mService.getConnectionState(semBluetoothCastDevice)) == 2 || connectionState == 1)) {
                            AudioCastProfile audioCastProfile = AudioCastProfile.this;
                            findCastDevice = audioCastProfile.mCastDeviceManager.addCastDevice(audioCastProfile.mCastProfileManager, semBluetoothCastDevice);
                        }
                        if (findCastDevice != null) {
                            Log.d(AudioCastProfile.this.TAG, "add castdevice " + semBluetoothCastDevice.getAddressForLog() + "/" + String.valueOf(AudioCastProfile.this.mService.getConnectionState(semBluetoothCastDevice)));
                            AudioCastProfile audioCastProfile2 = AudioCastProfile.this;
                            findCastDevice.onCastProfileStateChanged(audioCastProfile2, audioCastProfile2.mService.getConnectionState(semBluetoothCastDevice));
                            findCastDevice.dispatchAttributesChanged();
                        }
                    } else if (remoteDeviceRole == 1) {
                        LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(AudioCastProfile.this.mContext, null);
                        BluetoothDevice remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(semBluetoothCastDevice.getAddress());
                        if (localBluetoothManager != null) {
                            CachedBluetoothDevice findDevice = localBluetoothManager.mCachedDeviceManager.findDevice(remoteDevice);
                            if (findDevice == null) {
                                Log.d(AudioCastProfile.this.TAG, "cacheddevice is null");
                            } else {
                                Log.d(AudioCastProfile.this.TAG, "change cacheddevice " + findDevice.mDevice.getAddressForLogging() + "/" + String.valueOf(AudioCastProfile.this.mService.getConnectionState(semBluetoothCastDevice)));
                                findDevice.onCastProfileStateChanged(semBluetoothCastDevice, AudioCastProfile.this.mService.getConnectionState(semBluetoothCastDevice));
                            }
                        }
                    }
                }
            }

            public final void onServiceDisconnected() {
                Log.d(AudioCastProfile.this.TAG, "AudioCastProfile Proxy object disconnected");
                AudioCastProfile audioCastProfile = AudioCastProfile.this;
                if (audioCastProfile.mService != null) {
                    audioCastProfile.mService = null;
                }
            }
        };
        this.mAudioCastProfileListener = r1;
        Log.d("AudioCastProfile", "AudioCastProfile");
        this.mContext = context;
        this.mCastDeviceManager = cachedBluetoothCastDeviceManager;
        this.mCastProfileManager = localBluetoothCastProfileManager;
        SemBluetoothAudioCast.getProxy(context, (SemBluetoothCastProfile.BluetoothCastProfileListener) r1);
    }

    public final void finalize() {
        super.finalize();
        SemBluetoothAudioCast semBluetoothAudioCast = this.mService;
        if (semBluetoothAudioCast != null) {
            semBluetoothAudioCast.closeProxy();
        }
    }
}
