package com.android.server.usb;

import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.SparseArray;
import android.util.sysfwutil.Slog;
import com.android.internal.util.dump.DualDumpOutputStream;
import java.util.List;

/* loaded from: classes3.dex */
public class UsbPermissionManager {
  public static final String LOG_TAG = "UsbPermissionManager";
  public final Context mContext;
  public final SparseArray mPermissionsByUser = new SparseArray();
  public final UsbService mUsbService;

  public UsbPermissionManager(Context context, UsbService usbService) {
    this.mContext = context;
    this.mUsbService = usbService;
  }

  public UsbUserPermissionManager getPermissionsForUser(int i) {
    UsbUserPermissionManager usbUserPermissionManager;
    synchronized (this.mPermissionsByUser) {
      usbUserPermissionManager = (UsbUserPermissionManager) this.mPermissionsByUser.get(i);
      if (usbUserPermissionManager == null) {
        usbUserPermissionManager =
            new UsbUserPermissionManager(
                this.mContext.createContextAsUser(UserHandle.of(i), 0),
                this.mUsbService.getSettingsForUser(i));
        this.mPermissionsByUser.put(i, usbUserPermissionManager);
      }
    }
    return usbUserPermissionManager;
  }

  public UsbUserPermissionManager getPermissionsForUser(UserHandle userHandle) {
    return getPermissionsForUser(userHandle.getIdentifier());
  }

  public void usbDeviceRemoved(UsbDevice usbDevice) {
    synchronized (this.mPermissionsByUser) {
      for (int i = 0; i < this.mPermissionsByUser.size(); i++) {
        ((UsbUserPermissionManager) this.mPermissionsByUser.valueAt(i))
            .removeDevicePermissions(usbDevice);
      }
    }
    Intent intent = new Intent("android.hardware.usb.action.USB_DEVICE_DETACHED");
    intent.addFlags(16777216);
    intent.putExtra("device", usbDevice);
    Slog.d(LOG_TAG, "usbDeviceRemoved, sending " + intent);
    this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
  }

  public void usbAccessoryRemoved(UsbAccessory usbAccessory) {
    synchronized (this.mPermissionsByUser) {
      for (int i = 0; i < this.mPermissionsByUser.size(); i++) {
        ((UsbUserPermissionManager) this.mPermissionsByUser.valueAt(i))
            .removeAccessoryPermissions(usbAccessory);
      }
    }
    Intent intent = new Intent("android.hardware.usb.action.USB_ACCESSORY_DETACHED");
    intent.addFlags(16777216);
    intent.putExtra("accessory", usbAccessory);
    this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
  }

  public void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
    long start = dualDumpOutputStream.start(str, j);
    UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
    synchronized (this.mPermissionsByUser) {
      List users = userManager.getUsers();
      int size = users.size();
      for (int i = 0; i < size; i++) {
        getPermissionsForUser(((UserInfo) users.get(i)).id)
            .dump(dualDumpOutputStream, "user_permissions", 2246267895809L);
      }
    }
    dualDumpOutputStream.end(start);
  }
}
