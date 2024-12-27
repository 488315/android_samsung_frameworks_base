package com.android.systemui.usb;

import android.hardware.usb.UsbDevice;
import android.util.Log;
import com.android.systemui.R;

public final class UsbAudioWarningDialogMessage {
    public UsbDialogHelper mDialogHelper;
    public int mDialogType;

    public final int getMessageId() {
        UsbDevice usbDevice;
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        if (!usbDialogHelper.mIsUsbDevice) {
            return this.mDialogType == 0 ? R.string.usb_accessory_permission_prompt : R.string.usb_accessory_confirm_prompt;
        }
        if (usbDialogHelper.packageHasAudioRecordingPermission() && isUsbAudioDevice()) {
            return R.string.usb_audio_device_prompt;
        }
        if (!this.mDialogHelper.packageHasAudioRecordingPermission() && isUsbAudioDevice() && (usbDevice = this.mDialogHelper.mDevice) != null && usbDevice.getHasAudioPlayback() && !this.mDialogHelper.deviceHasAudioCapture()) {
            return R.string.usb_audio_device_prompt;
        }
        if (!this.mDialogHelper.packageHasAudioRecordingPermission() && isUsbAudioDevice() && this.mDialogHelper.deviceHasAudioCapture()) {
            return R.string.usb_audio_device_prompt_warn;
        }
        Log.w("UsbAudioWarningDialogMessage", "Only shows title with empty content description!");
        return 0;
    }

    public final boolean isUsbAudioDevice() {
        UsbDevice usbDevice;
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        return usbDialogHelper.mIsUsbDevice && (usbDialogHelper.deviceHasAudioCapture() || ((usbDevice = this.mDialogHelper.mDevice) != null && usbDevice.getHasAudioPlayback()));
    }
}
