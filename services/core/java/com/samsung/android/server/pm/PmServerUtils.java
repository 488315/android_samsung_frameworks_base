package com.samsung.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.content.ApexEnvironment;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageUserStateUtils;
import java.io.File;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class PmServerUtils {
    public static String getProcessNameForPid(int i) {
        String str;
        if (ActivityManagerNative.getDefault() == null || !ActivityManagerNative.isSystemReady()) {
            return "am is not ready yet";
        }
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ActivityManagerNative.getDefault().getRunningAppProcesses();
            if (runningAppProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
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
        return str.split(XmlUtils.STRING_ARRAY_SEPARATOR)[0];
    }

    public static Bundle getAppMetaData(PackageSetting packageSetting) {
        AndroidPackageInternal pkg;
        ApplicationInfo generateApplicationInfo;
        if (packageSetting == null || (pkg = packageSetting.getPkg()) == null || (generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(pkg, 128L, packageSetting.readUserState(-1), -1, packageSetting)) == null) {
            return null;
        }
        return generateApplicationInfo.metaData;
    }

    public static boolean isMetaDataInBundle(Bundle bundle, String str) {
        if (bundle != null) {
            return bundle.getBoolean(str, false);
        }
        return false;
    }

    public static boolean installedOnSdcardAsUser(PackageStateInternal packageStateInternal, long j, int i) {
        if (packageStateInternal == null || (packageStateInternal.getFlags() & 262144) == 0) {
            return false;
        }
        if (packageStateInternal.getPkg() != null) {
            return PackageUserStateUtils.isAvailable(packageStateInternal.getUserStateOrDefault(i), j);
        }
        if ((4194304 & j) == 0 && (8192 & j) == 0) {
            return false;
        }
        return PackageUserStateUtils.isAvailable(packageStateInternal.getUserStateOrDefault(i), j);
    }

    public static void deletePermissionApexFile(int i, String str) {
        getPermissionApexFile(i, str).delete();
    }

    public static File getPermissionApexFile(int i, String str) {
        return new File(ApexEnvironment.getApexEnvironment("com.android.permission").getDeviceProtectedDataDirForUser(UserHandle.of(i)), str);
    }
}
