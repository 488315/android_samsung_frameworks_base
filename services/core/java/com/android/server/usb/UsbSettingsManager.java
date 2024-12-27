package com.android.server.usb;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.SparseArray;

import com.android.internal.util.dump.DualDumpOutputStream;

import java.util.List;

public final class UsbSettingsManager {
    public final Context mContext;
    public final UsbHandlerManager mUsbHandlerManager;
    public final UsbService mUsbService;
    public final UserManager mUserManager;
    public final SparseArray mSettingsByUser = new SparseArray();
    public final SparseArray mSettingsByProfileGroup = new SparseArray();

    public UsbSettingsManager(Context context, UsbService usbService) {
        this.mContext = context;
        this.mUsbService = usbService;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mUsbHandlerManager = new UsbHandlerManager(context);
    }

    public final void dump(DualDumpOutputStream dualDumpOutputStream) {
        int i;
        long start = dualDumpOutputStream.start("settings_manager", 1146756268037L);
        synchronized (this.mSettingsByUser) {
            try {
                List users = this.mUserManager.getUsers();
                int size = users.size();
                for (int i2 = 0; i2 < size; i2++) {
                    getSettingsForUser(((UserInfo) users.get(i2)).id).dump(dualDumpOutputStream);
                }
            } finally {
            }
        }
        synchronized (this.mSettingsByProfileGroup) {
            try {
                int size2 = this.mSettingsByProfileGroup.size();
                for (i = 0; i < size2; i++) {
                    ((UsbProfileGroupSettingsManager) this.mSettingsByProfileGroup.valueAt(i))
                            .dump(dualDumpOutputStream);
                }
            } finally {
            }
        }
        dualDumpOutputStream.end(start);
    }

    public final UsbProfileGroupSettingsManager getSettingsForProfileGroup(UserHandle userHandle) {
        UsbProfileGroupSettingsManager usbProfileGroupSettingsManager;
        UserInfo profileParent = this.mUserManager.getProfileParent(userHandle.getIdentifier());
        if (profileParent != null) {
            userHandle = profileParent.getUserHandle();
        }
        synchronized (this.mSettingsByProfileGroup) {
            try {
                usbProfileGroupSettingsManager =
                        (UsbProfileGroupSettingsManager)
                                this.mSettingsByProfileGroup.get(userHandle.getIdentifier());
                if (usbProfileGroupSettingsManager == null) {
                    usbProfileGroupSettingsManager =
                            new UsbProfileGroupSettingsManager(
                                    this.mContext, userHandle, this, this.mUsbHandlerManager);
                    this.mSettingsByProfileGroup.put(
                            userHandle.getIdentifier(), usbProfileGroupSettingsManager);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return usbProfileGroupSettingsManager;
    }

    public final UsbUserSettingsManager getSettingsForUser(int i) {
        UsbUserSettingsManager usbUserSettingsManager;
        synchronized (this.mSettingsByUser) {
            try {
                usbUserSettingsManager = (UsbUserSettingsManager) this.mSettingsByUser.get(i);
                if (usbUserSettingsManager == null) {
                    usbUserSettingsManager =
                            new UsbUserSettingsManager(this.mContext, UserHandle.of(i));
                    this.mSettingsByUser.put(i, usbUserSettingsManager);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return usbUserSettingsManager;
    }
}
