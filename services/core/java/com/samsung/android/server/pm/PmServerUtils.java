package com.samsung.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.os.RemoteException;

import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageUserStateUtils;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PmServerUtils {
    public static String getProcessNameForPid(int i) {
        String str;
        if (ActivityManagerNative.getDefault() == null || !ActivityManagerNative.isSystemReady()) {
            return "am is not ready yet";
        }
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
                    ActivityManagerNative.getDefault().getRunningAppProcesses();
            if (runningAppProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo :
                        runningAppProcesses) {
                    if (runningAppProcessInfo.pid == i) {
                        str = runningAppProcessInfo.processName;
                        break;
                    }
                }
            }
            str = "";
        } catch (RemoteException unused) {
            str = "couldn't find";
        }
        return str.split(":")[0];
    }

    public static boolean installedOnSdcardAsUser(
            PackageStateInternal packageStateInternal, long j, int i) {
        if (packageStateInternal == null || (packageStateInternal.getFlags() & 262144) == 0) {
            return false;
        }
        if (packageStateInternal.getPkg() != null) {
            return PackageUserStateUtils.isAvailable(
                    packageStateInternal.getUserStateOrDefault(i), j);
        }
        if ((4194304 & j) == 0 && (8192 & j) == 0) {
            return false;
        }
        return PackageUserStateUtils.isAvailable(packageStateInternal.getUserStateOrDefault(i), j);
    }
}
