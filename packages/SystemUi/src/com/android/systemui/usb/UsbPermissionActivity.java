package com.android.systemui.usb;

import android.os.Bundle;
import android.widget.CheckBox;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class UsbPermissionActivity extends UsbDialogActivity {
    public boolean mPermissionGranted = false;
    public final UsbAudioWarningDialogMessage mUsbPermissionMessageHandler;

    public UsbPermissionActivity(UsbAudioWarningDialogMessage usbAudioWarningDialogMessage) {
        this.mUsbPermissionMessageHandler = usbAudioWarningDialogMessage;
    }

    @Override // com.android.systemui.usb.UsbDialogActivity
    public final void onConfirm() {
        this.mDialogHelper.grantUidAccessPermission();
        CheckBox checkBox = this.mAlwaysUse;
        if (checkBox != null && checkBox.isChecked()) {
            this.mDialogHelper.setDefaultPackage();
        }
        this.mPermissionGranted = true;
        finish();
    }

    @Override // com.android.systemui.usb.UsbDialogActivity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        UsbAudioWarningDialogMessage usbAudioWarningDialogMessage = this.mUsbPermissionMessageHandler;
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        usbAudioWarningDialogMessage.mDialogType = 0;
        usbAudioWarningDialogMessage.mDialogHelper = usbDialogHelper;
    }

    @Override // com.android.systemui.usb.UsbDialogActivity
    public final void onPause() {
        if (isFinishing()) {
            this.mDialogHelper.sendPermissionDialogResponse(this.mPermissionGranted);
        }
        super.onPause();
    }

    @Override // com.android.systemui.usb.UsbDialogActivity
    public final void onResume() {
        String str;
        super.onResume();
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        boolean z = usbDialogHelper.mIsUsbDevice && usbDialogHelper.deviceHasAudioCapture() && !this.mDialogHelper.packageHasAudioRecordingPermission();
        int i = this.mUsbPermissionMessageHandler.mDialogType == 0 ? R.string.usb_audio_device_permission_prompt_title : R.string.usb_audio_device_confirm_prompt_title;
        UsbDialogHelper usbDialogHelper2 = this.mDialogHelper;
        String string = getString(i, new Object[]{usbDialogHelper2.mAppName, usbDialogHelper2.getDeviceDescription()});
        int messageId = this.mUsbPermissionMessageHandler.getMessageId();
        if (messageId != 0) {
            UsbDialogHelper usbDialogHelper3 = this.mDialogHelper;
            str = getString(messageId, new Object[]{usbDialogHelper3.mAppName, usbDialogHelper3.getDeviceDescription()});
        } else {
            str = null;
        }
        setAlertParams(string, str);
        if (!z && this.mDialogHelper.mCanBeDefault) {
            addAlwaysUseCheckbox();
        }
        setupAlert();
    }
}
