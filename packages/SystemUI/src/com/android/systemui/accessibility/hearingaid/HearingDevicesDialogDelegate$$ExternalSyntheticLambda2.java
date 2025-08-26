package com.android.systemui.accessibility.hearingaid;

import android.bluetooth.BluetoothHapClient;
import android.content.ContentResolver;
import android.util.ArraySet;
import android.util.Log;
import android.widget.Toast;
import com.android.settingslib.bluetooth.AmbientVolumeUiController;
import com.android.settingslib.bluetooth.AmbientVolumeUiController$$ExternalSyntheticLambda0;
import com.android.settingslib.bluetooth.HapClientProfile;
import com.android.settingslib.bluetooth.HearingDeviceLocalDataManager;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.R;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public final /* synthetic */ class HearingDevicesDialogDelegate$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ HearingDevicesDialogDelegate$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                HearingDevicesDialogDelegate hearingDevicesDialogDelegate = (HearingDevicesDialogDelegate) obj;
                LocalBluetoothManager localBluetoothManager = hearingDevicesDialogDelegate.mLocalBluetoothManager;
                if (localBluetoothManager != null) {
                    localBluetoothManager.mEventManager.unregisterCallback(hearingDevicesDialogDelegate);
                }
                HearingDevicesPresetsController hearingDevicesPresetsController = hearingDevicesDialogDelegate.mPresetController;
                if (hearingDevicesPresetsController != null) {
                    ((CopyOnWriteArrayList) hearingDevicesPresetsController.mProfileManager.mServiceListeners).remove(hearingDevicesPresetsController);
                    HapClientProfile hapClientProfile = hearingDevicesPresetsController.mHapClientProfile;
                    if (hapClientProfile != null) {
                        try {
                            BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
                            if (bluetoothHapClient == null) {
                                Log.w("HapClientProfile", "Proxy not attached to service. Cannot unregister callback.");
                            } else {
                                bluetoothHapClient.unregisterCallback(hearingDevicesPresetsController);
                            }
                        } catch (IllegalArgumentException e) {
                            Log.w("HearingDevicesPresetsController", "Cannot unregister callback: " + e.getMessage());
                        }
                    }
                }
                AmbientVolumeUiController ambientVolumeUiController = hearingDevicesDialogDelegate.mAmbientController;
                if (ambientVolumeUiController != null) {
                    ambientVolumeUiController.mEventManager.unregisterCallback(ambientVolumeUiController);
                    HearingDeviceLocalDataManager hearingDeviceLocalDataManager = ambientVolumeUiController.mLocalDataManager;
                    synchronized (hearingDeviceLocalDataManager) {
                        if (hearingDeviceLocalDataManager.mIsStarted) {
                            hearingDeviceLocalDataManager.putAmbientVolumeSettings();
                            HearingDeviceLocalDataManager.SettingsObserver settingsObserver = hearingDeviceLocalDataManager.mSettingsObserver;
                            ContentResolver contentResolver = hearingDeviceLocalDataManager.mContext.getContentResolver();
                            settingsObserver.getClass();
                            contentResolver.unregisterContentObserver(settingsObserver);
                            hearingDeviceLocalDataManager.mIsStarted = false;
                        }
                    }
                    ((ArraySet) ambientVolumeUiController.mCachedDevices).forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda0(ambientVolumeUiController, 1));
                    return;
                }
                return;
            case 1:
                AmbientVolumeUiController ambientVolumeUiController2 = ((HearingDevicesDialogDelegate) obj).mAmbientController;
                ambientVolumeUiController2.mEventManager.registerCallback(ambientVolumeUiController2);
                ambientVolumeUiController2.mLocalDataManager.start();
                ((ArraySet) ambientVolumeUiController2.mCachedDevices).forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda0(ambientVolumeUiController2, 4));
                return;
            case 2:
                ((HearingDevicesDialogDelegate) obj).mPresetController.registerHapCallback();
                return;
            default:
                Toast.makeText(HearingDevicesDialogDelegate.this.mDialog.getContext(), R.string.hearing_devices_presets_error, 0).show();
                return;
        }
    }
}
