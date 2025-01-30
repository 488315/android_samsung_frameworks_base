package com.android.systemui.usb;

import android.hardware.usb.UsbDevice;
import android.util.Log;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UsbAudioWarningDialogMessage {
    public UsbDialogHelper mDialogHelper;
    public int mDialogType;

    public final int getMessageId() {
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        if (!usbDialogHelper.mIsUsbDevice) {
            return this.mDialogType == 0 ? R.string.usb_accessory_permission_prompt : R.string.usb_accessory_confirm_prompt;
        }
        if (usbDialogHelper.packageHasAudioRecordingPermission() && isUsbAudioDevice()) {
            return R.string.usb_audio_device_prompt;
        }
        if (!this.mDialogHelper.packageHasAudioRecordingPermission() && isUsbAudioDevice()) {
            UsbDevice usbDevice = this.mDialogHelper.mDevice;
            if ((usbDevice != null && usbDevice.getHasAudioPlayback()) && !this.mDialogHelper.deviceHasAudioCapture()) {
                return R.string.usb_audio_device_prompt;
            }
        }
        if (!this.mDialogHelper.packageHasAudioRecordingPermission() && isUsbAudioDevice() && this.mDialogHelper.deviceHasAudioCapture()) {
            return R.string.usb_audio_device_prompt_warn;
        }
        Log.w("UsbAudioWarningDialogMessage", "Only shows title with empty content description!");
        return 0;
    }

    public final boolean isUsbAudioDevice() {
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        if (!usbDialogHelper.mIsUsbDevice) {
            return false;
        }
        if (!usbDialogHelper.deviceHasAudioCapture()) {
            UsbDevice usbDevice = this.mDialogHelper.mDevice;
            if (!(usbDevice != null && usbDevice.getHasAudioPlayback())) {
                return false;
            }
        }
        return true;
    }
}
