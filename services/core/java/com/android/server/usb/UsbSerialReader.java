package com.android.server.usb;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.IUsbSerialReader;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.os.Binder;
import android.os.RemoteException;
import android.os.UserHandle;

import com.android.internal.util.ArrayUtils;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbSerialReader extends IUsbSerialReader.Stub {
    public final Context mContext;
    public Object mDevice;
    public final UsbPermissionManager mPermissionManager;
    public final String mSerialNumber;

    public UsbSerialReader(Context context, UsbPermissionManager usbPermissionManager, String str) {
        this.mContext = context;
        this.mPermissionManager = usbPermissionManager;
        this.mSerialNumber = str;
    }

    public final String getSerial(String str) {
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000) {
            if (!ArrayUtils.contains(
                    this.mContext.getPackageManager().getPackagesForUid(callingUid), str)) {
                throw new IllegalArgumentException(
                        VpnManagerService$$ExternalSyntheticOutline0.m(
                                callingUid, str, " does to belong to the "));
            }
            UserHandle callingUserHandle = Binder.getCallingUserHandle();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    if (this.mContext
                                            .getPackageManager()
                                            .getPackageInfoAsUser(
                                                    str, 0, callingUserHandle.getIdentifier())
                                            .applicationInfo
                                            .targetSdkVersion
                                    >= 29
                            && this.mContext.checkPermission(
                                            "android.permission.MANAGE_USB", callingPid, callingUid)
                                    == -1) {
                        int userId = UserHandle.getUserId(callingUid);
                        if (this.mDevice instanceof UsbDevice) {
                            this.mPermissionManager
                                    .getPermissionsForUser(userId)
                                    .checkPermission(
                                            (UsbDevice) this.mDevice, str, callingPid, callingUid);
                        } else {
                            this.mPermissionManager
                                    .getPermissionsForUser(userId)
                                    .checkPermission(
                                            (UsbAccessory) this.mDevice, callingPid, callingUid);
                        }
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (PackageManager.NameNotFoundException unused) {
                    throw new RemoteException("package " + str + " cannot be found");
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return this.mSerialNumber;
    }
}
