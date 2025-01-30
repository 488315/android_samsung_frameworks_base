package com.android.systemui.usb;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.PermissionChecker;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.usb.IUsbManager;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UsbDialogHelper {
    public final UsbAccessory mAccessory;
    public final CharSequence mAppName;
    public final boolean mCanBeDefault;
    public final Context mContext;
    public final UsbDevice mDevice;
    public UsbDisconnectedReceiver mDisconnectedReceiver;
    public final boolean mIsUsbDevice;
    public final String mPackageName;
    public final PendingIntent mPendingIntent;
    public final ResolveInfo mResolveInfo;
    public boolean mResponseSent;
    public final int mUid;
    public final IUsbManager mUsbService;

    public UsbDialogHelper(Context context, Intent intent) {
        this.mContext = context;
        UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
        this.mDevice = usbDevice;
        UsbAccessory usbAccessory = (UsbAccessory) intent.getParcelableExtra("accessory");
        this.mAccessory = usbAccessory;
        this.mCanBeDefault = intent.getBooleanExtra("android.hardware.usb.extra.CAN_BE_DEFAULT", false);
        if (usbDevice == null && usbAccessory == null) {
            throw new IllegalStateException("Device and accessory are both null.");
        }
        if (usbDevice != null) {
            this.mIsUsbDevice = true;
        }
        ResolveInfo resolveInfo = (ResolveInfo) intent.getParcelableExtra("rinfo");
        this.mResolveInfo = resolveInfo;
        PackageManager packageManager = context.getPackageManager();
        if (resolveInfo != null) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            this.mUid = activityInfo.applicationInfo.uid;
            this.mPackageName = activityInfo.packageName;
            this.mPendingIntent = null;
        } else {
            this.mUid = intent.getIntExtra("android.intent.extra.UID", -1);
            this.mPackageName = intent.getStringExtra("android.hardware.usb.extra.PACKAGE");
            this.mPendingIntent = (PendingIntent) intent.getParcelableExtra("android.intent.extra.INTENT");
        }
        try {
            this.mAppName = packageManager.getApplicationInfo(this.mPackageName, 0).loadLabel(packageManager);
            this.mUsbService = IUsbManager.Stub.asInterface(ServiceManager.getService("usb"));
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("unable to look up package name", e);
        }
    }

    public final void confirmDialogStartActivity() {
        Intent intent;
        int myUserId = UserHandle.myUserId();
        if (this.mIsUsbDevice) {
            intent = new Intent("android.hardware.usb.action.USB_DEVICE_ATTACHED");
            intent.putExtra("device", this.mDevice);
        } else {
            intent = new Intent("android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
            intent.putExtra("accessory", this.mAccessory);
        }
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        ActivityInfo activityInfo = this.mResolveInfo.activityInfo;
        intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
        try {
            this.mContext.startActivityAsUser(intent, new UserHandle(myUserId));
        } catch (Exception e) {
            Log.e("UsbDialogHelper", "Unable to start activity", e);
        }
    }

    public final boolean deviceHasAudioCapture() {
        UsbDevice usbDevice = this.mDevice;
        return usbDevice != null && usbDevice.getHasAudioCapture();
    }

    public final String getDeviceDescription() {
        if (this.mIsUsbDevice) {
            UsbDevice usbDevice = this.mDevice;
            String productName = usbDevice.getProductName();
            return productName == null ? usbDevice.getDeviceName() : productName;
        }
        UsbAccessory usbAccessory = this.mAccessory;
        String description = usbAccessory.getDescription();
        return description == null ? String.format("%s %s", usbAccessory.getManufacturer(), usbAccessory.getModel()) : description;
    }

    public final void grantUidAccessPermission() {
        try {
            boolean z = this.mIsUsbDevice;
            int i = this.mUid;
            IUsbManager iUsbManager = this.mUsbService;
            if (z) {
                iUsbManager.grantDevicePermission(this.mDevice, i);
            } else {
                iUsbManager.grantAccessoryPermission(this.mAccessory, i);
            }
        } catch (RemoteException e) {
            Log.e("UsbDialogHelper", "IUsbService connection failed", e);
        }
    }

    public final boolean packageHasAudioRecordingPermission() {
        return PermissionChecker.checkPermissionForPreflight(this.mContext, "android.permission.RECORD_AUDIO", -1, this.mUid, this.mPackageName) == 0;
    }

    public final void sendPermissionDialogResponse(boolean z) {
        if (this.mResponseSent) {
            return;
        }
        Intent intent = new Intent();
        if (this.mIsUsbDevice) {
            intent.putExtra("device", this.mDevice);
        } else {
            intent.putExtra("accessory", this.mAccessory);
        }
        intent.putExtra("permission", z);
        try {
            this.mPendingIntent.send(this.mContext, 0, intent);
            this.mResponseSent = true;
        } catch (PendingIntent.CanceledException unused) {
            Log.w("UsbDialogHelper", "PendingIntent was cancelled");
        }
    }

    public final void setDefaultPackage() {
        int myUserId = UserHandle.myUserId();
        try {
            boolean z = this.mIsUsbDevice;
            String str = this.mPackageName;
            IUsbManager iUsbManager = this.mUsbService;
            if (z) {
                iUsbManager.setDevicePackage(this.mDevice, str, myUserId);
            } else {
                iUsbManager.setAccessoryPackage(this.mAccessory, str, myUserId);
            }
        } catch (RemoteException e) {
            Log.e("UsbDialogHelper", "IUsbService connection failed", e);
        }
    }
}
