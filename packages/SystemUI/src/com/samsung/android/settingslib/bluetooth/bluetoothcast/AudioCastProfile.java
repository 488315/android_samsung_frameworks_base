package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.R;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.bluetooth.SemBluetoothCastProfile;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AudioCastProfile implements LocalBluetoothCastProfile {
    public final String TAG;
    public final AnonymousClass1 mAudioCastProfileListener;
    public final CachedBluetoothCastDeviceManager mCastDeviceManager;
    public final LocalBluetoothCastProfileManager mCastProfileManager;
    public final Context mContext;
    public SemBluetoothAudioCast mService;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.bluetooth.SemBluetoothCastProfile$BluetoothCastProfileListener, com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile$1] */
    public AudioCastProfile(Context context, CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager, LocalBluetoothCastProfileManager localBluetoothCastProfileManager) {
        String simpleName = getClass().getSimpleName();
        this.TAG = simpleName;
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
                                Log.d(AudioCastProfile.this.TAG, "change cacheddevice " + findDevice.mDevice + "/" + String.valueOf(AudioCastProfile.this.mService.getConnectionState(semBluetoothCastDevice)));
                                if (AudioCastProfile.this.mService.getConnectionState(semBluetoothCastDevice) == 2) {
                                    findDevice.mBluetoothCastMsg = findDevice.mContext.getString(R.string.bluetooth_cast_shared_with, semBluetoothCastDevice.getPeerName());
                                } else {
                                    findDevice.mBluetoothCastMsg = null;
                                }
                                findDevice.refresh();
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
        Log.d(simpleName, "AudioCastProfile");
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

    public final List getConnectedDevices() {
        SemBluetoothAudioCast semBluetoothAudioCast = this.mService;
        return semBluetoothAudioCast == null ? new ArrayList() : semBluetoothAudioCast.getConnectedDevices();
    }
}
