package com.android.systemui.accessibility.hearingaid;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HapClientProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HearingDevicesPresetsController implements LocalBluetoothProfileManager.ServiceListener, BluetoothHapClient.Callback {
    public CachedBluetoothDevice mActiveHearingDevice;
    public final HapClientProfile mHapClientProfile;
    public final PresetCallback mPresetCallback;
    public final LocalBluetoothProfileManager mProfileManager;
    public int mSelectedPresetIndex;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface PresetCallback {
    }

    public HearingDevicesPresetsController(LocalBluetoothProfileManager localBluetoothProfileManager, PresetCallback presetCallback) {
        this.mProfileManager = localBluetoothProfileManager;
        this.mHapClientProfile = localBluetoothProfileManager.mHapClientProfile;
        this.mPresetCallback = presetCallback;
    }

    public final int getActivePresetIndex() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mActiveHearingDevice;
        if (cachedBluetoothDevice == null) {
            return 0;
        }
        HapClientProfile hapClientProfile = this.mHapClientProfile;
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
        if (bluetoothHapClient != null) {
            return bluetoothHapClient.getActivePresetIndex(bluetoothDevice);
        }
        Log.w("HapClientProfile", "Proxy not attached to service. Cannot get active preset index.");
        return 0;
    }

    public final List getAllPresetInfo() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mActiveHearingDevice;
        if (cachedBluetoothDevice == null) {
            return Collections.emptyList();
        }
        HapClientProfile hapClientProfile = this.mHapClientProfile;
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
        if (bluetoothHapClient != null) {
            return bluetoothHapClient.getAllPresetInfo(bluetoothDevice);
        }
        Log.w("HapClientProfile", "Proxy not attached to service. Cannot get all preset info.");
        return new ArrayList();
    }

    public final void onPresetInfoChanged(BluetoothDevice bluetoothDevice, List list, int i) {
        CachedBluetoothDevice cachedBluetoothDevice = this.mActiveHearingDevice;
        if (cachedBluetoothDevice != null && bluetoothDevice.equals(cachedBluetoothDevice.mDevice)) {
            Log.d("HearingDevicesPresetsController", "onPresetInfoChanged, device: " + bluetoothDevice.getAddress() + ", reason: " + i + ", infoList: " + list);
            HearingDevicesDialogDelegate.AnonymousClass1 anonymousClass1 = (HearingDevicesDialogDelegate.AnonymousClass1) this.mPresetCallback;
            HearingDevicesDialogDelegate.this.mMainHandler.post(new HearingDevicesDialogDelegate$1$$ExternalSyntheticLambda0(anonymousClass1, getAllPresetInfo(), getActivePresetIndex(), 1));
        }
    }

    public final void onPresetSelected(BluetoothDevice bluetoothDevice, int i, int i2) {
        CachedBluetoothDevice cachedBluetoothDevice = this.mActiveHearingDevice;
        if (cachedBluetoothDevice != null && bluetoothDevice.equals(cachedBluetoothDevice.mDevice)) {
            StringBuilder sb = new StringBuilder("onPresetSelected, device: ");
            sb.append(bluetoothDevice.getAddress());
            sb.append(", presetIndex: ");
            sb.append(i);
            sb.append(", reason: ");
            RecyclerView$$ExternalSyntheticOutline0.m(i2, "HearingDevicesPresetsController", sb);
            PresetCallback presetCallback = this.mPresetCallback;
            HearingDevicesDialogDelegate.AnonymousClass1 anonymousClass1 = (HearingDevicesDialogDelegate.AnonymousClass1) presetCallback;
            HearingDevicesDialogDelegate.this.mMainHandler.post(new HearingDevicesDialogDelegate$1$$ExternalSyntheticLambda0(anonymousClass1, getAllPresetInfo(), getActivePresetIndex(), 1));
        }
    }

    public final void onPresetSelectionFailed(BluetoothDevice bluetoothDevice, int i) {
        CachedBluetoothDevice cachedBluetoothDevice = this.mActiveHearingDevice;
        if (cachedBluetoothDevice != null && bluetoothDevice.equals(cachedBluetoothDevice.mDevice)) {
            Log.w("HearingDevicesPresetsController", "onPresetSelectionFailed, device: " + bluetoothDevice.getAddress() + ", reason: " + i);
            ((HearingDevicesDialogDelegate.AnonymousClass1) this.mPresetCallback).onPresetCommandFailed();
        }
    }

    public final void onPresetSelectionForGroupFailed(int i, int i2) {
        int hapGroup;
        CachedBluetoothDevice cachedBluetoothDevice = this.mActiveHearingDevice;
        if (cachedBluetoothDevice == null) {
            return;
        }
        HapClientProfile hapClientProfile = this.mHapClientProfile;
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
        PresetCallback presetCallback = this.mPresetCallback;
        HearingDevicesDialogDelegate.AnonymousClass1 anonymousClass1 = (HearingDevicesDialogDelegate.AnonymousClass1) presetCallback;
        HearingDevicesDialogDelegate.this.mMainHandler.post(new HearingDevicesDialogDelegate$1$$ExternalSyntheticLambda0(anonymousClass1, getAllPresetInfo(), getActivePresetIndex(), 1));
    }

    public final void onSetPresetNameFailed(BluetoothDevice bluetoothDevice, int i) {
        CachedBluetoothDevice cachedBluetoothDevice = this.mActiveHearingDevice;
        if (cachedBluetoothDevice != null && bluetoothDevice.equals(cachedBluetoothDevice.mDevice)) {
            Log.w("HearingDevicesPresetsController", "onSetPresetNameFailed, device: " + bluetoothDevice.getAddress() + ", reason: " + i);
            ((HearingDevicesDialogDelegate.AnonymousClass1) this.mPresetCallback).onPresetCommandFailed();
        }
    }

    public final void onSetPresetNameForGroupFailed(int i, int i2) {
        int hapGroup;
        CachedBluetoothDevice cachedBluetoothDevice = this.mActiveHearingDevice;
        if (cachedBluetoothDevice == null) {
            return;
        }
        HapClientProfile hapClientProfile = this.mHapClientProfile;
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
        ((HearingDevicesDialogDelegate.AnonymousClass1) this.mPresetCallback).onPresetCommandFailed();
    }

    public final void registerHapCallback() {
        HapClientProfile hapClientProfile = this.mHapClientProfile;
        if (hapClientProfile != null) {
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
        if (this.mActiveHearingDevice == null) {
            return;
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "selectPresetIndependently, presetIndex: ", ", device: ");
        m.append(this.mActiveHearingDevice.mDevice.getAddress());
        Log.d("HearingDevicesPresetsController", m.toString());
        this.mHapClientProfile.selectPreset(this.mActiveHearingDevice.mDevice, i);
        CachedBluetoothDevice cachedBluetoothDevice = this.mActiveHearingDevice.mSubDevice;
        if (cachedBluetoothDevice != null) {
            Log.d("HearingDevicesPresetsController", "selectPreset for subDevice, device: " + cachedBluetoothDevice);
            this.mHapClientProfile.selectPreset(cachedBluetoothDevice.mDevice, i);
        }
        for (CachedBluetoothDevice cachedBluetoothDevice2 : this.mActiveHearingDevice.mMemberDevices) {
            Log.d("HearingDevicesPresetsController", "selectPreset for memberDevice, device: " + cachedBluetoothDevice2);
            this.mHapClientProfile.selectPreset(cachedBluetoothDevice2.mDevice, i);
        }
    }
}
