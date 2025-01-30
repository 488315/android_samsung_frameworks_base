package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LocalBluetoothCastProfileManager {
    public final String TAG = "LocalBluetoothCastProfileManager";
    public AudioCastProfile mAudioCastProfile;
    public final CachedBluetoothCastDeviceManager mCastDeviceManager;
    public final BluetoothCastEventManager mCastEventManager;
    public final LocalBluetoothCastAdapter mLocalCastAdapter;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StateChangedHandler implements BluetoothCastEventManager.Handler {
        public final LocalBluetoothCastProfile mBluetoothCastProfile;

        public StateChangedHandler(LocalBluetoothCastProfile localBluetoothCastProfile) {
            this.mBluetoothCastProfile = localBluetoothCastProfile;
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x00a9  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00bb  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00e6  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00e9  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00d2  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00b1  */
        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onReceive(Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice) {
            char c;
            String string;
            int intExtra = intent.getIntExtra("com.samsung.android.bluetooth.cast.device.extra.REMOTEROLE", 0);
            if (intExtra != 2) {
                if (intExtra == 1) {
                    if (this.mBluetoothCastProfile == null) {
                        Log.e(LocalBluetoothCastProfileManager.this.TAG, "StateChangedHandler :: mBluetoothCastProfile is null");
                        return;
                    }
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (bluetoothDevice == null) {
                        Log.w(LocalBluetoothCastProfileManager.this.TAG, "StateChangedHandler :: Can't get bluetoothdevice");
                        return;
                    }
                    int intExtra2 = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0);
                    Log.d(LocalBluetoothCastProfileManager.this.TAG, "Profiles StateChangedHandler device : " + bluetoothDevice.getName() + ", new state : " + intExtra2);
                    LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(context, null);
                    if (localBluetoothManager == null) {
                        Log.w(LocalBluetoothCastProfileManager.this.TAG, "StateChangedHandler :: Can't get localBtManager");
                        return;
                    }
                    CachedBluetoothDevice findDevice = localBluetoothManager.mCachedDeviceManager.findDevice(bluetoothDevice);
                    if (findDevice == null) {
                        Log.w(LocalBluetoothCastProfileManager.this.TAG, "StateChangedHandler :: Can't get cacheddevice");
                        return;
                    } else {
                        if (this.mBluetoothCastProfile instanceof AudioCastProfile) {
                            findDevice.onCastProfileStateChanged(semBluetoothCastDevice, intExtra2);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            if (this.mBluetoothCastProfile == null) {
                Log.e(LocalBluetoothCastProfileManager.this.TAG, "StateChangedHandler :: mBluetoothCastProfile is null");
                return;
            }
            CachedBluetoothCastDevice findCastDevice = LocalBluetoothCastProfileManager.this.mCastDeviceManager.findCastDevice(semBluetoothCastDevice);
            if (findCastDevice == null) {
                Log.w(LocalBluetoothCastProfileManager.this.TAG, "StateChangedHandler :: Can't add castdevice");
                return;
            }
            int intExtra3 = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0);
            int intExtra4 = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.PREV_STATE", 0);
            int intExtra5 = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.ERROR", 0);
            String str = LocalBluetoothCastProfileManager.this.TAG;
            StringBuilder sb = new StringBuilder("Profiles StateChangedHandler device : ");
            sb.append(findCastDevice.getName());
            sb.append(", mProfile : ");
            sb.append(this.mBluetoothCastProfile);
            sb.append(", new state : ");
            sb.append(intExtra3);
            sb.append(", old state : ");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(sb, intExtra4, ", reason: ", intExtra5, str);
            if (intExtra3 == 0 && intExtra4 == 1) {
                Log.d(LocalBluetoothCastProfileManager.this.TAG, "Failed to connect " + this.mBluetoothCastProfile + " device");
                LocalBluetoothManager localBluetoothManager2 = LocalBluetoothManager.getInstance(context, null);
                if (localBluetoothManager2 != null) {
                    if (localBluetoothManager2.semIsForegroundActivity()) {
                        c = 1;
                    } else if (LocalBluetoothManager.mSystemUiInstance) {
                        c = 2;
                    }
                    if (c != 1 || c == 2) {
                        String string2 = c != 1 ? context.getString(R.string.sec_bluetooth_cast_error_summary_wrong) : context.getString(R.string.sec_bluetooth_cast_error_wrong);
                        if (intExtra5 == 5) {
                            if (intExtra5 == 11) {
                                string = c == 1 ? context.getString(R.string.sec_bluetooth_cast_error_summary_deny) : context.getString(R.string.sec_bluetooth_cast_error_deny);
                            }
                            if (c == 1) {
                                findCastDevice.mErrorMsg = string2;
                            } else {
                                BluetoothUtils.showToast(context, string2);
                            }
                        } else {
                            string = c == 1 ? context.getString(R.string.sec_bluetooth_cast_error_summary_not_respond) : context.getString(R.string.sec_bluetooth_cast_error_not_respond);
                        }
                        string2 = string;
                        if (c == 1) {
                        }
                    }
                }
                c = 65535;
                if (c != 1) {
                }
                if (c != 1) {
                }
                if (intExtra5 == 5) {
                }
                string2 = string;
                if (c == 1) {
                }
            }
            findCastDevice.onCastProfileStateChanged(this.mBluetoothCastProfile, intExtra3);
            BluetoothCastEventManager bluetoothCastEventManager = LocalBluetoothCastProfileManager.this.mCastEventManager;
            synchronized (bluetoothCastEventManager.mCallbacks) {
                Iterator it = ((ArrayList) bluetoothCastEventManager.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((SBluetoothControllerImpl) ((BluetoothCastCallback) it.next())).mHandler.obtainMessage(6, findCastDevice).sendToTarget();
                }
            }
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
                if (bluetoothCastEventManager.mReceivers.contains(bluetoothCastEventManager.mCastProfileReceiver)) {
                    bluetoothCastEventManager.mContext.unregisterReceiver(bluetoothCastEventManager.mCastProfileReceiver);
                    bluetoothCastEventManager.mReceivers.remove(bluetoothCastEventManager.mCastProfileReceiver);
                    Log.e(bluetoothCastEventManager.TAG, "registerCastProfileIntentReceiver :: mProfileConnectionReceiver was registered already. Receiver will refresh.");
                }
                bluetoothCastEventManager.mContext.registerReceiver(bluetoothCastEventManager.mCastProfileReceiver, bluetoothCastEventManager.mCastProfileFilter);
                bluetoothCastEventManager.mReceivers.add(bluetoothCastEventManager.mCastProfileReceiver);
            }
        }
    }
}
