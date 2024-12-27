package com.android.systemui.usb;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.widget.CheckBox;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class UsbConfirmActivity extends UsbDialogActivity {
    public final UsbAudioWarningDialogMessage mUsbConfirmMessageHandler;

    public UsbConfirmActivity(UsbAudioWarningDialogMessage usbAudioWarningDialogMessage) {
        this.mUsbConfirmMessageHandler = usbAudioWarningDialogMessage;
    }

    @Override // com.android.systemui.usb.UsbDialogActivity
    public final void onConfirm() {
        Intent intent;
        this.mDialogHelper.grantUidAccessPermission();
        CheckBox checkBox = this.mAlwaysUse;
        if (checkBox != null && checkBox.isChecked()) {
            this.mDialogHelper.setDefaultPackage();
        } else {
            UsbDialogHelper usbDialogHelper = this.mDialogHelper;
            usbDialogHelper.getClass();
            int myUserId = UserHandle.myUserId();
            try {
                if (usbDialogHelper.mIsUsbDevice) {
                    usbDialogHelper.mUsbService.setDevicePackage(usbDialogHelper.mDevice, (String) null, myUserId);
                } else {
                    usbDialogHelper.mUsbService.setAccessoryPackage(usbDialogHelper.mAccessory, (String) null, myUserId);
                }
            } catch (RemoteException e) {
                Log.e("UsbDialogHelper", "IUsbService connection failed", e);
            }
        }
        UsbDialogHelper usbDialogHelper2 = this.mDialogHelper;
        usbDialogHelper2.getClass();
        int myUserId2 = UserHandle.myUserId();
        if (usbDialogHelper2.mIsUsbDevice) {
            intent = new Intent("android.hardware.usb.action.USB_DEVICE_ATTACHED");
            intent.putExtra("device", usbDialogHelper2.mDevice);
        } else {
            intent = new Intent("android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
            intent.putExtra("accessory", usbDialogHelper2.mAccessory);
        }
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        ActivityInfo activityInfo = usbDialogHelper2.mResolveInfo.activityInfo;
        intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
        try {
            usbDialogHelper2.mContext.startActivityAsUser(intent, new UserHandle(myUserId2));
        } catch (Exception e2) {
            Log.e("UsbDialogHelper", "Unable to start activity", e2);
        }
        finish();
    }

    @Override // com.android.systemui.usb.UsbDialogActivity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        UsbAudioWarningDialogMessage usbAudioWarningDialogMessage = this.mUsbConfirmMessageHandler;
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        usbAudioWarningDialogMessage.mDialogType = 1;
        usbAudioWarningDialogMessage.mDialogHelper = usbDialogHelper;
    }

    @Override // com.android.systemui.usb.UsbDialogActivity
    public final void onResume() {
        String str;
        super.onResume();
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        boolean z = usbDialogHelper.mIsUsbDevice && usbDialogHelper.deviceHasAudioCapture() && !this.mDialogHelper.packageHasAudioRecordingPermission();
        int i = this.mUsbConfirmMessageHandler.mDialogType == 0 ? R.string.usb_audio_device_permission_prompt_title : R.string.usb_audio_device_confirm_prompt_title;
        UsbDialogHelper usbDialogHelper2 = this.mDialogHelper;
        String string = getString(i, new Object[]{usbDialogHelper2.mAppName, usbDialogHelper2.getDeviceDescription()});
        int messageId = this.mUsbConfirmMessageHandler.getMessageId();
        if (messageId != 0) {
            UsbDialogHelper usbDialogHelper3 = this.mDialogHelper;
            str = getString(messageId, new Object[]{usbDialogHelper3.mAppName, usbDialogHelper3.getDeviceDescription()});
        } else {
            str = null;
        }
        setAlertParams(string, str);
        if (!z) {
            addAlwaysUseCheckbox();
        }
        setupAlert();
    }
}
