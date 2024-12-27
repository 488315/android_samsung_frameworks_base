package com.android.server.usb;

import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.hardware.usb.UsbDevice;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.SparseArray;
import android.util.sysfwutil.Slog;
import com.android.internal.util.dump.DualDumpOutputStream;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbPermissionManager {
    public final Context mContext;
    public final SparseArray mPermissionsByUser = new SparseArray();
    public final UsbService mUsbService;

    public UsbPermissionManager(Context context, UsbService usbService) {
        this.mContext = context;
        this.mUsbService = usbService;
    }

    public final void dump(DualDumpOutputStream dualDumpOutputStream) {
        long start = dualDumpOutputStream.start("permissions_manager", 1146756268038L);
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        synchronized (this.mPermissionsByUser) {
            try {
                List users = userManager.getUsers();
                int size = users.size();
                for (int i = 0; i < size; i++) {
                    getPermissionsForUser(((UserInfo) users.get(i)).id).dump(dualDumpOutputStream);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        dualDumpOutputStream.end(start);
    }

    public final UsbUserPermissionManager getPermissionsForUser(int i) {
        UsbUserPermissionManager usbUserPermissionManager;
        synchronized (this.mPermissionsByUser) {
            try {
                usbUserPermissionManager = (UsbUserPermissionManager) this.mPermissionsByUser.get(i);
                if (usbUserPermissionManager == null) {
                    usbUserPermissionManager = new UsbUserPermissionManager(this.mContext.createContextAsUser(UserHandle.of(i), 0), this.mUsbService.mSettingsManager.getSettingsForUser(i));
                    this.mPermissionsByUser.put(i, usbUserPermissionManager);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return usbUserPermissionManager;
    }

    public final void usbDeviceRemoved(UsbDevice usbDevice) {
        synchronized (this.mPermissionsByUser) {
            for (int i = 0; i < this.mPermissionsByUser.size(); i++) {
                try {
                    UsbUserPermissionManager usbUserPermissionManager = (UsbUserPermissionManager) this.mPermissionsByUser.valueAt(i);
                    synchronized (usbUserPermissionManager.mLock) {
                        usbUserPermissionManager.mDevicePermissionMap.remove(usbDevice.getDeviceName());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        Intent intent = new Intent("android.hardware.usb.action.USB_DEVICE_DETACHED");
        intent.addFlags(16777216);
        intent.putExtra("device", usbDevice);
        Slog.d("UsbPermissionManager", "usbDeviceRemoved, sending " + intent);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
    }
}
