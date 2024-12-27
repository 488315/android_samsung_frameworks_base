package com.android.systemui.usb;

import android.app.PendingIntent;
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
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public UsbDialogHelper(Context context, Intent intent) throws IllegalStateException {
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

    public final boolean deviceHasAudioCapture() {
        UsbDevice usbDevice = this.mDevice;
        return usbDevice != null && usbDevice.getHasAudioCapture();
    }

    public final String getDeviceDescription() {
        if (this.mIsUsbDevice) {
            String productName = this.mDevice.getProductName();
            return productName == null ? this.mDevice.getDeviceName() : productName;
        }
        String description = this.mAccessory.getDescription();
        return description == null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.mAccessory.getManufacturer(), " ", this.mAccessory.getModel()) : description;
    }

    public final void grantUidAccessPermission() {
        try {
            boolean z = this.mIsUsbDevice;
            int i = this.mUid;
            if (z) {
                this.mUsbService.grantDevicePermission(this.mDevice, i);
            } else {
                this.mUsbService.grantAccessoryPermission(this.mAccessory, i);
            }
        } catch (RemoteException e) {
            Log.e("UsbDialogHelper", "IUsbService connection failed", e);
        }
    }

    public final boolean packageHasAudioRecordingPermission() {
        return PermissionChecker.checkPermissionForPreflight(this.mContext, "android.permission.RECORD_AUDIO", -1, this.mUid, this.mPackageName) == 0;
    }

    public final void setDefaultPackage() {
        int myUserId = UserHandle.myUserId();
        try {
            boolean z = this.mIsUsbDevice;
            String str = this.mPackageName;
            if (z) {
                this.mUsbService.setDevicePackage(this.mDevice, str, myUserId);
            } else {
                this.mUsbService.setAccessoryPackage(this.mAccessory, str, myUserId);
            }
        } catch (RemoteException e) {
            Log.e("UsbDialogHelper", "IUsbService connection failed", e);
        }
    }
}
