package com.samsung.android.server.hwrs.samba;

import android.os.SystemProperties;
import android.os.SystemService;
import android.util.Log;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.samsung.android.server.hwrs.utils.StorageServiceException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ServerUserManager {
    public static void addUser(String str, String str2) {
        DualAppManagerService$$ExternalSyntheticOutline0.m("addUser - UserName:", str, "[HWRS_SYS]ServerUserManager");
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty()) {
            throw new StorageServiceException("fields cannot be null or empty!!!");
        }
        SystemProperties.set("ksmbd.au.u", str);
        SystemProperties.set("ksmbd.au.p", str2);
        if (SystemService.isRunning("ksmbd_add_user")) {
            Log.d("[HWRS_SYS]ServerUserManager", "KSMBD add user already running...");
        } else {
            SystemService.start("ksmbd_add_user");
            Log.d("[HWRS_SYS]ServerUserManager", "wait for  ksmbd add user to stop");
            ServerManager.waitForState("ksmbd_add_user", SystemService.State.STOPPED);
            Log.d("[HWRS_SYS]ServerUserManager", "KSMBD add user Successful!!!");
        }
        SystemProperties.set("ksmbd.au.u", "");
        SystemProperties.set("ksmbd.au.p", "");
        Log.i("[HWRS_SYS]ServerUserManager", "addUser success!!!");
    }

    public static void delUser(String str) {
        DualAppManagerService$$ExternalSyntheticOutline0.m("deleteUser - UserName:", str, "[HWRS_SYS]ServerUserManager");
        if (str == null || str.isEmpty()) {
            throw new StorageServiceException("fields cannot be null or empty!!!");
        }
        SystemProperties.set("ksmbd.au.u", str);
        if (SystemService.isRunning("ksmbd_delete_user")) {
            Log.d("[HWRS_SYS]ServerUserManager", "KSMBD delete user already running...");
        } else {
            SystemService.start("ksmbd_delete_user");
            Log.d("[HWRS_SYS]ServerUserManager", "wait for  ksmbd delete user to stop");
            ServerManager.waitForState("ksmbd_delete_user", SystemService.State.STOPPED);
            Log.d("[HWRS_SYS]ServerUserManager", "KSMBD delete user Successful!!!");
        }
        SystemProperties.set("ksmbd.au.u", "");
        Log.i("[HWRS_SYS]ServerUserManager", "delUser success!!!");
    }
}
