package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.R;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class LocalBluetoothCastProfileManager {
    public final AudioCastProfile mAudioCastProfile;
    public final CachedBluetoothCastDeviceManager mCastDeviceManager;
    public final BluetoothCastEventManager mCastEventManager;
    public final LocalBluetoothCastAdapter mLocalCastAdapter;

    public class StateChangedHandler implements BluetoothCastEventManager.Handler {
        public final LocalBluetoothCastProfile mBluetoothCastProfile;

        public StateChangedHandler(LocalBluetoothCastProfile localBluetoothCastProfile) {
            this.mBluetoothCastProfile = localBluetoothCastProfile;
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x00ae  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00b3  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00b5  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00bd  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00c7  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00df  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00f3  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00f6  */
        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onReceive(Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice) {
            char c;
            String string;
            int i = 0;
            int intExtra = intent.getIntExtra("com.samsung.android.bluetooth.cast.device.extra.REMOTEROLE", 0);
            if (intExtra != 2) {
                if (intExtra == 1) {
                    if (this.mBluetoothCastProfile == null) {
                        LocalBluetoothCastProfileManager.this.getClass();
                        Log.e("LocalBluetoothCastProfileManager", "StateChangedHandler :: mBluetoothCastProfile is null");
                        return;
                    }
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (bluetoothDevice == null) {
                        LocalBluetoothCastProfileManager.this.getClass();
                        Log.w("LocalBluetoothCastProfileManager", "StateChangedHandler :: Can't get bluetoothdevice");
                        return;
                    }
                    int intExtra2 = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0);
                    LocalBluetoothCastProfileManager.this.getClass();
                    Log.d("LocalBluetoothCastProfileManager", "Profiles StateChangedHandler device : " + bluetoothDevice.getName() + ", new state : " + intExtra2);
                    LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(context, null);
                    if (localBluetoothManager == null) {
                        LocalBluetoothCastProfileManager.this.getClass();
                        Log.w("LocalBluetoothCastProfileManager", "StateChangedHandler :: Can't get localBtManager");
                        return;
                    }
                    CachedBluetoothDevice cachedBluetoothDeviceFindDevice = localBluetoothManager.mCachedDeviceManager.findDevice(bluetoothDevice);
                    if (cachedBluetoothDeviceFindDevice == null) {
                        LocalBluetoothCastProfileManager.this.getClass();
                        Log.w("LocalBluetoothCastProfileManager", "StateChangedHandler :: Can't get cacheddevice");
                        return;
                    } else {
                        if (this.mBluetoothCastProfile instanceof AudioCastProfile) {
                            if (intExtra2 == 2) {
                                cachedBluetoothDeviceFindDevice.mBluetoothCastMsg = cachedBluetoothDeviceFindDevice.mContext.getString(R.string.bluetooth_cast_shared_with, semBluetoothCastDevice.getPeerName());
                            } else {
                                cachedBluetoothDeviceFindDevice.mBluetoothCastMsg = null;
                            }
                            cachedBluetoothDeviceFindDevice.refresh();
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            if (this.mBluetoothCastProfile == null) {
                LocalBluetoothCastProfileManager.this.getClass();
                Log.e("LocalBluetoothCastProfileManager", "StateChangedHandler :: mBluetoothCastProfile is null");
                return;
            }
            CachedBluetoothCastDevice cachedBluetoothCastDeviceFindCastDevice = LocalBluetoothCastProfileManager.this.mCastDeviceManager.findCastDevice(semBluetoothCastDevice);
            if (cachedBluetoothCastDeviceFindCastDevice == null) {
                LocalBluetoothCastProfileManager.this.getClass();
                Log.w("LocalBluetoothCastProfileManager", "StateChangedHandler :: Can't add castdevice");
                return;
            }
            int intExtra3 = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0);
            int intExtra4 = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.PREV_STATE", 0);
            int intExtra5 = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.ERROR", 0);
            LocalBluetoothCastProfileManager.this.getClass();
            StringBuilder sb = new StringBuilder("Profiles StateChangedHandler device : ");
            sb.append(cachedBluetoothCastDeviceFindCastDevice.getName());
            sb.append(", mProfile : ");
            sb.append(this.mBluetoothCastProfile);
            sb.append(", new state : ");
            sb.append(intExtra3);
            sb.append(", old state : ");
            KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(sb, intExtra4, ", reason: ", intExtra5, "LocalBluetoothCastProfileManager");
            if (intExtra3 == 0 && intExtra4 == 1) {
                LocalBluetoothCastProfileManager.this.getClass();
                Log.d("LocalBluetoothCastProfileManager", "Failed to connect " + this.mBluetoothCastProfile + " device");
                LocalBluetoothManager localBluetoothManager2 = LocalBluetoothManager.getInstance(context, null);
                if (localBluetoothManager2 == null) {
                    c = 65535;
                    if (c != 1 || c == 2) {
                        String string2 = c != 1 ? context.getString(R.string.sec_bluetooth_cast_error_summary_wrong) : context.getString(R.string.sec_bluetooth_cast_error_wrong);
                        if (intExtra5 == 5) {
                            if (intExtra5 == 11) {
                                string = c == 1 ? context.getString(R.string.sec_bluetooth_cast_error_summary_deny) : context.getString(R.string.sec_bluetooth_cast_error_deny);
                            }
                            if (c == 1) {
                                cachedBluetoothCastDeviceFindCastDevice.mErrorMsg = string2;
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
                } else {
                    if (localBluetoothManager2.semIsForegroundActivity()) {
                        c = 1;
                    } else if (LocalBluetoothManager.mSystemUiInstance) {
                        c = 2;
                    }
                    if (c != 1) {
                        if (c != 1) {
                        }
                        if (intExtra5 == 5) {
                        }
                        string2 = string;
                        if (c == 1) {
                        }
                    }
                }
            }
            cachedBluetoothCastDeviceFindCastDevice.onCastProfileStateChanged(this.mBluetoothCastProfile, intExtra3);
            BluetoothCastEventManager bluetoothCastEventManager = LocalBluetoothCastProfileManager.this.mCastEventManager;
            synchronized (bluetoothCastEventManager.mCallbacks) {
                try {
                    ArrayList arrayList = (ArrayList) bluetoothCastEventManager.mCallbacks;
                    int size = arrayList.size();
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((BluetoothCastCallback) obj).onCastProfileStateChanged(cachedBluetoothCastDeviceFindCastDevice);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public LocalBluetoothCastProfileManager(Context context, LocalBluetoothCastAdapter localBluetoothCastAdapter, CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager, BluetoothCastEventManager bluetoothCastEventManager) {
        HashMap map = new HashMap();
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
            map.put("AudioCast", audioCastProfile);
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
