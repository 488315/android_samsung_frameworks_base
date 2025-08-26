package com.android.systemui.accessibility.hearingaid;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HapClientProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class HearingDevicesPresetsController implements LocalBluetoothProfileManager.ServiceListener, BluetoothHapClient.Callback {
    public CachedBluetoothDevice mDevice;
    public final HapClientProfile mHapClientProfile;
    public final PresetCallback mPresetCallback;
    public final LocalBluetoothProfileManager mProfileManager;
    public int mSelectedPresetIndex;
    public List mPresetInfos = new ArrayList();
    public int mActivePresetIndex = 0;

    public interface PresetCallback {
    }

    public HearingDevicesPresetsController(LocalBluetoothProfileManager localBluetoothProfileManager, PresetCallback presetCallback) {
        this.mProfileManager = localBluetoothProfileManager;
        this.mHapClientProfile = localBluetoothProfileManager.mHapClientProfile;
        this.mPresetCallback = presetCallback;
    }

    public final boolean isPresetControlAvailable() {
        HapClientProfile hapClientProfile;
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        boolean z = (cachedBluetoothDevice == null || (hapClientProfile = cachedBluetoothDevice.mProfileManager.mHapClientProfile) == null || hapClientProfile.getConnectionStatus(cachedBluetoothDevice.mDevice) != 2) ? false : true;
        List list = this.mPresetInfos;
        return z && (list != null && !list.isEmpty());
    }

    public final void onPresetInfoChanged(BluetoothDevice bluetoothDevice, List list, int i) {
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        if (cachedBluetoothDevice != null && bluetoothDevice.equals(cachedBluetoothDevice.mDevice)) {
            Log.d("HearingDevicesPresetsController", "onPresetInfoChanged, device: " + bluetoothDevice.getAddress() + ", reason: " + i + ", infoList: " + list);
            refreshPresetInfo();
        }
    }

    public final void onPresetSelected(BluetoothDevice bluetoothDevice, int i, int i2) {
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        if (cachedBluetoothDevice != null && bluetoothDevice.equals(cachedBluetoothDevice.mDevice)) {
            Log.d("HearingDevicesPresetsController", "onPresetSelected, device: " + bluetoothDevice.getAddress() + ", presetIndex: " + i + ", reason: " + i2);
            refreshPresetInfo();
        }
    }

    public final void onPresetSelectionFailed(BluetoothDevice bluetoothDevice, int i) {
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        if (cachedBluetoothDevice != null && bluetoothDevice.equals(cachedBluetoothDevice.mDevice)) {
            Log.w("HearingDevicesPresetsController", "onPresetSelectionFailed, device: " + bluetoothDevice.getAddress() + ", reason: " + i);
            PresetCallback presetCallback = this.mPresetCallback;
            if (presetCallback != null) {
                ((HearingDevicesDialogDelegate.AnonymousClass1) presetCallback).onPresetCommandFailed();
            }
        }
    }

    public final void onPresetSelectionForGroupFailed(int i, int i2) {
        HapClientProfile hapClientProfile;
        int hapGroup;
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        if (cachedBluetoothDevice == null || (hapClientProfile = this.mHapClientProfile) == null) {
            return;
        }
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
        if (bluetoothHapClient == null) {
            Log.w("HapClientProfile", "Proxy not attached to service. Cannot get hap group.");
            hapGroup = -1;
        } else {
            hapGroup = bluetoothHapClient.getHapGroup(bluetoothDevice);
        }
        if (i == hapGroup) {
            Log.w("HearingDevicesPresetsController", "onPresetSelectionForGroupFailed, group: " + i + ", reason: " + i2);
            selectPresetIndependently(this.mSelectedPresetIndex);
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceConnected() {
        HapClientProfile hapClientProfile = this.mHapClientProfile;
        if (hapClientProfile == null || !hapClientProfile.mIsProfileReady) {
            return;
        }
        ((CopyOnWriteArrayList) this.mProfileManager.mServiceListeners).remove(this);
        registerHapCallback();
        refreshPresetInfo();
    }

    public final void onSetPresetNameFailed(BluetoothDevice bluetoothDevice, int i) {
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        if (cachedBluetoothDevice != null && bluetoothDevice.equals(cachedBluetoothDevice.mDevice)) {
            Log.w("HearingDevicesPresetsController", "onSetPresetNameFailed, device: " + bluetoothDevice.getAddress() + ", reason: " + i);
            PresetCallback presetCallback = this.mPresetCallback;
            if (presetCallback != null) {
                ((HearingDevicesDialogDelegate.AnonymousClass1) presetCallback).onPresetCommandFailed();
            }
        }
    }

    public final void onSetPresetNameForGroupFailed(int i, int i2) {
        HapClientProfile hapClientProfile;
        int hapGroup;
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        if (cachedBluetoothDevice == null || (hapClientProfile = this.mHapClientProfile) == null) {
            return;
        }
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
        if (bluetoothHapClient == null) {
            Log.w("HapClientProfile", "Proxy not attached to service. Cannot get hap group.");
            hapGroup = -1;
        } else {
            hapGroup = bluetoothHapClient.getHapGroup(bluetoothDevice);
        }
        if (i == hapGroup) {
            Log.w("HearingDevicesPresetsController", "onSetPresetNameForGroupFailed, group: " + i + ", reason: " + i2);
        }
        PresetCallback presetCallback = this.mPresetCallback;
        if (presetCallback != null) {
            ((HearingDevicesDialogDelegate.AnonymousClass1) presetCallback).onPresetCommandFailed();
        }
    }

    public final void refreshPresetInfo() {
        final int activePresetIndex;
        PresetCallback presetCallback;
        CachedBluetoothDevice cachedBluetoothDevice;
        List allPresetInfo;
        final List arrayList = new ArrayList();
        HapClientProfile hapClientProfile = this.mHapClientProfile;
        if (hapClientProfile == null || (cachedBluetoothDevice = this.mDevice) == null) {
            activePresetIndex = 0;
        } else {
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
            if (bluetoothHapClient == null) {
                Log.w("HapClientProfile", "Proxy not attached to service. Cannot get all preset info.");
                allPresetInfo = new ArrayList();
            } else {
                allPresetInfo = bluetoothHapClient.getAllPresetInfo(bluetoothDevice);
            }
            arrayList = allPresetInfo.stream().filter(new HearingDevicesPresetsController$$ExternalSyntheticLambda0(0)).toList();
            HapClientProfile hapClientProfile2 = this.mHapClientProfile;
            BluetoothDevice bluetoothDevice2 = this.mDevice.mDevice;
            BluetoothHapClient bluetoothHapClient2 = hapClientProfile2.mService;
            if (bluetoothHapClient2 == null) {
                Log.w("HapClientProfile", "Proxy not attached to service. Cannot get active preset index.");
                activePresetIndex = 0;
            } else {
                activePresetIndex = bluetoothHapClient2.getActivePresetIndex(bluetoothDevice2);
            }
        }
        boolean zEquals = this.mPresetInfos.equals(arrayList);
        boolean z = this.mActivePresetIndex != activePresetIndex;
        this.mPresetInfos = arrayList;
        this.mActivePresetIndex = activePresetIndex;
        if ((!zEquals || z) && (presetCallback = this.mPresetCallback) != null) {
            final HearingDevicesDialogDelegate.AnonymousClass1 anonymousClass1 = (HearingDevicesDialogDelegate.AnonymousClass1) presetCallback;
            HearingDevicesDialogDelegate.this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    HearingDevicesDialogDelegate.AnonymousClass1 anonymousClass12 = anonymousClass1;
                    List list = arrayList;
                    int i = activePresetIndex;
                    anonymousClass12.getClass();
                    String str = HearingDevicesDialogDelegate.ACTION_BLUETOOTH_DEVICE_DETAILS;
                    HearingDevicesDialogDelegate.this.refreshPresetUi(i, list);
                }
            });
        }
    }

    public final void registerHapCallback() {
        HapClientProfile hapClientProfile = this.mHapClientProfile;
        if (hapClientProfile != null) {
            if (!hapClientProfile.mIsProfileReady) {
                ((CopyOnWriteArrayList) this.mProfileManager.mServiceListeners).add(this);
                Log.w("HearingDevicesPresetsController", "Profile is not ready yet, the callback will be registered once the profile is ready.");
                return;
            }
            try {
                ListeningExecutorService backgroundExecutor = ThreadUtils.getBackgroundExecutor();
                BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
                if (bluetoothHapClient == null) {
                    Log.w("HapClientProfile", "Proxy not attached to service. Cannot register callback.");
                } else {
                    bluetoothHapClient.registerCallback(backgroundExecutor, this);
                }
            } catch (IllegalArgumentException e) {
                Log.w("HearingDevicesPresetsController", "Cannot register callback: " + e.getMessage());
            }
        }
    }

    public final void selectPresetIndependently(int i) {
        if (this.mDevice == null || this.mHapClientProfile == null) {
            return;
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "selectPresetIndependently, presetIndex: ", ", device: ");
        sbM.append(this.mDevice.mDevice.getAddress());
        Log.d("HearingDevicesPresetsController", sbM.toString());
        this.mHapClientProfile.selectPreset(this.mDevice.mDevice, i);
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice.mSubDevice;
        if (cachedBluetoothDevice != null) {
            Log.d("HearingDevicesPresetsController", "selectPreset for subDevice, device: " + cachedBluetoothDevice);
            this.mHapClientProfile.selectPreset(cachedBluetoothDevice.mDevice, i);
        }
        for (CachedBluetoothDevice cachedBluetoothDevice2 : this.mDevice.mMemberDevices) {
            Log.d("HearingDevicesPresetsController", "selectPreset for memberDevice, device: " + cachedBluetoothDevice2);
            this.mHapClientProfile.selectPreset(cachedBluetoothDevice2.mDevice, i);
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {
    }
}
