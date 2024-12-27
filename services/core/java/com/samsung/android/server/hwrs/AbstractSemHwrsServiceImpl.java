package com.samsung.android.server.hwrs;

import android.content.Context;
import android.net.INetd;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.os.SystemService;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.hwrs.ISemHwrsManager;
import com.samsung.android.server.hwrs.common.HwrsUtils;
import com.samsung.android.server.hwrs.common.HwrsUtils$$ExternalSyntheticLambda0;
import com.samsung.android.server.hwrs.samba.ServerConfiguration;
import com.samsung.android.server.hwrs.samba.ServerManager;
import com.samsung.android.server.hwrs.samba.ServerUserManager;
import com.samsung.android.server.hwrs.utils.FileUtil;
import com.samsung.android.server.hwrs.utils.StorageServiceException;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AbstractSemHwrsServiceImpl extends ISemHwrsManager.Stub {
    public final Context mContext;
    public int mCurrentUserId = -10000;
    public final PreconditionObserver mPrecondManager;
    public final UserManager mUserManager;

    public AbstractSemHwrsServiceImpl(Context context, PreconditionObserver preconditionObserver) {
        Log.d("[HWRS_SYS]SemHwrsService", "AbstractSemHwrsServiceImpl entered");
        new ThreadPoolExecutor(
                        64,
                        64,
                        60L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue(),
                        new HwrsUtils$$ExternalSyntheticLambda0())
                .allowCoreThreadTimeOut(true);
        HwrsUtils.sHandler = new Handler(Looper.getMainLooper());
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mPrecondManager = preconditionObserver;
        this.mContext = context;
    }

    public final boolean addShare(String str, String str2, String str3, String str4, String str5) {
        if (!permissionCheck().booleanValue()) {
            return false;
        }
        try {
            new ServerConfiguration().addShare(str, str2, str3, str4, str5);
            return true;
        } catch (StorageServiceException e) {
            Log.e("[HWRS_SYS]SemHwrsService", "addShare failed- " + e);
            return true;
        }
    }

    public final boolean addUser(String str, String str2) {
        if (!permissionCheck().booleanValue()) {
            return false;
        }
        try {
            ServerUserManager.addUser(str, str2);
            return true;
        } catch (StorageServiceException e) {
            Log.e("[HWRS_SYS]SemHwrsService", "addUser failed- " + e);
            return true;
        }
    }

    public final boolean deleteUser(String str) {
        if (!permissionCheck().booleanValue()) {
            return false;
        }
        try {
            ServerUserManager.delUser(str);
            return true;
        } catch (StorageServiceException e) {
            Log.e("[HWRS_SYS]SemHwrsService", "deleteUser failed- " + e);
            return true;
        }
    }

    public final String getKsmbdServerStatus() {
        if (!permissionCheck().booleanValue()) {
            return null;
        }
        try {
            return SystemService.getState("ksmbd_start").equals(SystemService.State.RUNNING)
                    ? INetd.IF_FLAG_RUNNING
                    : !SystemService.getState("ksmbd_stop").equals(SystemService.State.STOPPED)
                            ? "stopping"
                            : "stopped";
        } catch (StorageServiceException e) {
            Log.e("[HWRS_SYS]SemHwrsService", "getKsmbdServerStatus failed- " + e);
            return null;
        }
    }

    public final boolean ksmbdServerCleanup() {
        if (!permissionCheck().booleanValue()) {
            return false;
        }
        try {
            Log.d("[HWRS_SYS]ServerManager", "ksmbdServerCleanup");
            Log.d("[HWRS_SYS]ServerUserManager", "cleanup");
            FileUtil.deleteFile("/data/misc/hwrs/ksmbd/ksmbdpwd.db");
            return true;
        } catch (StorageServiceException e) {
            Log.e("[HWRS_SYS]SemHwrsService", "ksmbdServerCleanup failed- " + e);
            return true;
        }
    }

    public final Boolean permissionCheck() {
        if (!(Build.TYPE.equals("user"))) {
            return Boolean.TRUE;
        }
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(callingUid);
        String str =
                (packagesForUid == null || packagesForUid.length <= 0) ? null : packagesForUid[0];
        if (this.mContext.checkPermission(
                        "com.samsung.android.permission.HW_RESOURCE_SHARE", callingPid, callingUid)
                == 0) {
            return Boolean.TRUE;
        }
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "Unauthorized access attempt by package : ", str, "[HWRS_SYS]SemHwrsService");
        return Boolean.FALSE;
    }

    public final boolean reloadKmbdServerConfiguration() {
        if (!permissionCheck().booleanValue()) {
            return false;
        }
        try {
            Log.d("[HWRS_SYS]ServerManager", "Reload configuration for ksmbd server");
            SystemProperties.set("ksmbd.r", "true");
            return true;
        } catch (StorageServiceException e) {
            Log.e("[HWRS_SYS]SemHwrsService", "reloadKmbdServerConfiguration failed- " + e);
            return true;
        }
    }

    public final boolean restartKsmbdServer() {
        if (!permissionCheck().booleanValue()) {
            return false;
        }
        try {
            Log.d("[HWRS_SYS]ServerManager", "restartSmbServer");
            ServerManager.stopSmbServer();
            ServerManager.startSmbServer();
            Log.d("[HWRS_SYS]ServerManager", "Restart KSMBD Successful!!!");
            return true;
        } catch (StorageServiceException e) {
            Log.e("[HWRS_SYS]SemHwrsService", "restartKsmbdServer failed- " + e);
            return true;
        }
    }

    public abstract void setCurrentUserHandle(UserHandle userHandle);

    public final boolean startKsmbdServer() {
        if (!permissionCheck().booleanValue()) {
            return false;
        }
        try {
            ServerManager.startSmbServer();
            return true;
        } catch (StorageServiceException e) {
            Log.e("[HWRS_SYS]SemHwrsService", "startKsmbdServer failed- " + e);
            return true;
        }
    }

    public final boolean stopKsmbdServer() {
        if (!permissionCheck().booleanValue()) {
            return false;
        }
        try {
            ServerManager.stopSmbServer();
            return true;
        } catch (StorageServiceException e) {
            Log.e("[HWRS_SYS]SemHwrsService", "stopKsmbdServer failed- " + e);
            return true;
        }
    }
}
