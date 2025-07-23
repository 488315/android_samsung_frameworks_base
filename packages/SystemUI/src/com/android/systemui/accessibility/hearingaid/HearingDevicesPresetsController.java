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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class HearingDevicesPresetsController implements LocalBluetoothProfileManager.ServiceListener, BluetoothHapClient.Callback {
    public CachedBluetoothDevice mDevice;
    public final HapClientProfile mHapClientProfile;
    public final PresetCallback mPresetCallback;
    public final LocalBluetoothProfileManager mProfileManager;
    public int mSelectedPresetIndex;
    public List mPresetInfos = new ArrayList();
    public int mActivePresetIndex = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void refreshPresetInfo() {
        /*
            r5 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            com.android.settingslib.bluetooth.HapClientProfile r1 = r5.mHapClientProfile
            r2 = 0
            if (r1 == 0) goto L4c
            com.android.settingslib.bluetooth.CachedBluetoothDevice r3 = r5.mDevice
            if (r3 == 0) goto L4c
            android.bluetooth.BluetoothDevice r0 = r3.mDevice
            android.bluetooth.BluetoothHapClient r1 = r1.mService
            java.lang.String r3 = "HapClientProfile"
            if (r1 != 0) goto L21
            java.lang.String r0 = "Proxy not attached to service. Cannot get all preset info."
            android.util.Log.w(r3, r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            goto L25
        L21:
            java.util.List r0 = r1.getAllPresetInfo(r0)
        L25:
            java.util.stream.Stream r0 = r0.stream()
            com.android.systemui.accessibility.hearingaid.HearingDevicesPresetsController$$ExternalSyntheticLambda0 r1 = new com.android.systemui.accessibility.hearingaid.HearingDevicesPresetsController$$ExternalSyntheticLambda0
            r4 = 0
            r1.<init>(r4)
            java.util.stream.Stream r0 = r0.filter(r1)
            java.util.List r0 = r0.toList()
            com.android.settingslib.bluetooth.HapClientProfile r1 = r5.mHapClientProfile
            com.android.settingslib.bluetooth.CachedBluetoothDevice r4 = r5.mDevice
            android.bluetooth.BluetoothDevice r4 = r4.mDevice
            android.bluetooth.BluetoothHapClient r1 = r1.mService
            if (r1 != 0) goto L47
            java.lang.String r1 = "Proxy not attached to service. Cannot get active preset index."
            android.util.Log.w(r3, r1)
            goto L4c
        L47:
            int r1 = r1.getActivePresetIndex(r4)
            goto L4d
        L4c:
            r1 = r2
        L4d:
            java.util.List r3 = r5.mPresetInfos
            boolean r3 = r3.equals(r0)
            int r4 = r5.mActivePresetIndex
            if (r4 == r1) goto L58
            r2 = 1
        L58:
            r5.mPresetInfos = r0
            r5.mActivePresetIndex = r1
            if (r3 == 0) goto L60
            if (r2 == 0) goto L72
        L60:
            com.android.systemui.accessibility.hearingaid.HearingDevicesPresetsController$PresetCallback r5 = r5.mPresetCallback
            if (r5 == 0) goto L72
            com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$1 r5 = (com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate.AnonymousClass1) r5
            com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate r2 = com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate.this
            java.util.concurrent.Executor r2 = r2.mMainExecutor
            com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$1$$ExternalSyntheticLambda1 r3 = new com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$1$$ExternalSyntheticLambda1
            r3.<init>()
            r2.execute(r3)
        L72:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.hearingaid.HearingDevicesPresetsController.refreshPresetInfo():void");
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
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "selectPresetIndependently, presetIndex: ", ", device: ");
        m.append(this.mDevice.mDevice.getAddress());
        Log.d("HearingDevicesPresetsController", m.toString());
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
