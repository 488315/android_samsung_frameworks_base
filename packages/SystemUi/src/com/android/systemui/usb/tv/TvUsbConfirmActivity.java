package com.android.systemui.usb.tv;

import com.android.systemui.R;
import com.android.systemui.usb.UsbDialogHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TvUsbConfirmActivity extends TvUsbDialogActivity {
    @Override // com.android.systemui.usb.tv.TvUsbDialogActivity
    public final void onConfirm() {
        this.mDialogHelper.grantUidAccessPermission();
        this.mDialogHelper.confirmDialogStartActivity();
        finish();
    }

    @Override // com.android.systemui.usb.tv.TvUsbDialogActivity, android.app.Activity
    public final void onResume() {
        int i;
        super.onResume();
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        if (usbDialogHelper.mIsUsbDevice) {
            i = usbDialogHelper.deviceHasAudioCapture() && !this.mDialogHelper.packageHasAudioRecordingPermission() ? R.string.usb_device_confirm_prompt_warn : R.string.usb_device_confirm_prompt;
        } else {
            i = R.string.usb_accessory_confirm_prompt;
        }
        UsbDialogHelper usbDialogHelper2 = this.mDialogHelper;
        initUI(this.mDialogHelper.mAppName, getString(i, new Object[]{usbDialogHelper2.mAppName, usbDialogHelper2.getDeviceDescription()}));
    }
}
