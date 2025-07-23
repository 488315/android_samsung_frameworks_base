package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.content.Context;
import android.util.Log;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LocalBluetoothCastProfileManager {
    public final AudioCastProfile mAudioCastProfile;
    public final CachedBluetoothCastDeviceManager mCastDeviceManager;
    public final BluetoothCastEventManager mCastEventManager;
    public final LocalBluetoothCastAdapter mLocalCastAdapter;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class StateChangedHandler implements BluetoothCastEventManager.Handler {
        public final LocalBluetoothCastProfile mBluetoothCastProfile;

        public StateChangedHandler(LocalBluetoothCastProfile localBluetoothCastProfile) {
            this.mBluetoothCastProfile = localBluetoothCastProfile;
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x00b5  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00c7  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00f3  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00f6  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00df  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00bd  */
        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r10, android.content.Intent r11, com.samsung.android.bluetooth.SemBluetoothCastDevice r12) {
            /*
                Method dump skipped, instructions count: 447
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfileManager.StateChangedHandler.onReceive(android.content.Context, android.content.Intent, com.samsung.android.bluetooth.SemBluetoothCastDevice):void");
        }
    }

    public LocalBluetoothCastProfileManager(Context context, LocalBluetoothCastAdapter localBluetoothCastAdapter, CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager, BluetoothCastEventManager bluetoothCastEventManager) {
        HashMap hashMap = new HashMap();
        Log.d("LocalBluetoothCastProfileManager", "LocalBluetoothCastProfileManager ");
        this.mLocalCastAdapter = localBluetoothCastAdapter;
        this.mCastDeviceManager = cachedBluetoothCastDeviceManager;
        this.mCastEventManager = bluetoothCastEventManager;
        localBluetoothCastAdapter.mCastProfileManager = this;
        if (localBluetoothCastAdapter.mCastAdapter == null) {
            Log.d(localBluetoothCastAdapter.TAG, "Cannot set BluetoothCastStateOn");
        }
        bluetoothCastEventManager.mBluetoothCastProfileManager = this;
        Log.d("LocalBluetoothCastProfileManager", "updateLocalCastProfiles");
        if (this.mAudioCastProfile == null) {
            Log.d("LocalBluetoothCastProfileManager", "updateLocalCastProfiles mAudioCastProfile");
            AudioCastProfile audioCastProfile = new AudioCastProfile(context, cachedBluetoothCastDeviceManager, this);
            this.mAudioCastProfile = audioCastProfile;
            ((HashMap) bluetoothCastEventManager.mHandlerMap).put("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED", new StateChangedHandler(audioCastProfile));
            bluetoothCastEventManager.mCastProfileFilter.addAction("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
            hashMap.put("AudioCast", audioCastProfile);
            synchronized (bluetoothCastEventManager.mReceivers) {
                try {
                    if (bluetoothCastEventManager.mReceivers.contains(bluetoothCastEventManager.mCastProfileReceiver)) {
                        bluetoothCastEventManager.mContext.unregisterReceiver(bluetoothCastEventManager.mCastProfileReceiver);
                        bluetoothCastEventManager.mReceivers.remove(bluetoothCastEventManager.mCastProfileReceiver);
                        Log.e(bluetoothCastEventManager.TAG, "registerCastProfileIntentReceiver :: mProfileConnectionReceiver was registered already. Receiver will refresh.");
                    }
                    bluetoothCastEventManager.mContext.registerReceiver(bluetoothCastEventManager.mCastProfileReceiver, bluetoothCastEventManager.mCastProfileFilter);
                    bluetoothCastEventManager.mReceivers.add(bluetoothCastEventManager.mCastProfileReceiver);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
