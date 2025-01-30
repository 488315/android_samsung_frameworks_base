package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.bluetooth.BluetoothDevice;
import android.media.AudioManager;
import android.media.IVolumeController;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AudioManagerWrapper {
    private static final AudioManagerWrapper sInstance = new AudioManagerWrapper();
    private static final AudioManager mAudioManager = (AudioManager) AppGlobals.getInitialApplication().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);

    private AudioManagerWrapper() {
    }

    public static AudioManagerWrapper getInstance() {
        return sInstance;
    }

    public void forceVolumeControlStream(int i) {
        mAudioManager.forceVolumeControlStream(i);
    }

    public int getDevicesForStream(int i) {
        return mAudioManager.getDevicesForStream(i);
    }

    public int getFineVolume(int i, int i2) {
        return mAudioManager.getFineVolume(i, i2);
    }

    public String getPinAppName(int i) {
        return mAudioManager.getPinAppName(i);
    }

    public String getPinDeviceName(int i) {
        return mAudioManager.getPinDeviceName(i);
    }

    public int getRingerModeInternal() {
        return mAudioManager.getRingerModeInternal();
    }

    public int getStreamMinVolumeInt(int i) {
        return mAudioManager.getStreamMinVolumeInt(i);
    }

    public boolean isMicrophoneMute() {
        return mAudioManager.isMicrophoneMute();
    }

    public boolean isSafeMediaVolumeDeviceOn(int i) {
        return mAudioManager.isSafeMediaVolumeDeviceOn(i);
    }

    public int semGetFineVolume(BluetoothDevice bluetoothDevice, int i) {
        return mAudioManager.semGetFineVolume(bluetoothDevice, i);
    }

    public int semGetPinDevice() {
        return mAudioManager.semGetPinDevice();
    }

    public void semSetFineVolume(BluetoothDevice bluetoothDevice, int i, int i2, int i3) {
        mAudioManager.semSetFineVolume(bluetoothDevice, i, i2, i3);
    }

    public void setFineVolume(int i, int i2, int i3, int i4) {
        mAudioManager.setFineVolume(i, i2, i3, i4);
    }

    public void setRingerModeInternal(int i) {
        mAudioManager.setRingerModeInternal(i);
    }

    public void setVolumeController(IVolumeController iVolumeController) {
        mAudioManager.setVolumeController(iVolumeController);
    }

    public Boolean shouldShowRingtoneVolume() {
        return Boolean.valueOf(mAudioManager.shouldShowRingtoneVolume());
    }
}
