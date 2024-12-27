package com.android.server;

import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.CpuUsageInfo;
import android.os.IHardwarePropertiesManager;
import android.os.UserHandle;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.utils.ManagedApplicationService;
import com.android.server.vr.VrManagerService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class HardwarePropertiesManagerService extends IHardwarePropertiesManager.Stub {
    public final AppOpsManager mAppOps;
    public final Context mContext;
    public final Object mLock;

    public HardwarePropertiesManagerService(Context context) {
        Object obj = new Object();
        this.mLock = obj;
        this.mContext = context;
        this.mAppOps = (AppOpsManager) context.getSystemService("appops");
        synchronized (obj) {
            nativeInit();
        }
    }

    private static native CpuUsageInfo[] nativeGetCpuUsages();

    private static native float[] nativeGetDeviceTemperatures(int i, int i2);

    private static native float[] nativeGetFanSpeeds();

    private static native void nativeInit();

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String nameForUid;
        if (DumpUtils.checkDumpPermission(
                this.mContext, "HardwarePropertiesManagerService", printWriter)) {
            printWriter.println("****** Dump of HardwarePropertiesManagerService ******");
            PackageManager packageManager = this.mContext.getPackageManager();
            int callingUid = Binder.getCallingUid();
            String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
            if (packagesForUid == null || packagesForUid.length <= 0) {
                nameForUid = packageManager.getNameForUid(callingUid);
                if (nameForUid == null) {
                    nameForUid = String.valueOf(callingUid);
                }
            } else {
                nameForUid = packagesForUid[0];
            }
            dumpTempValues(0, printWriter, nameForUid, "CPU ");
            dumpTempValues(1, printWriter, nameForUid, "GPU ");
            dumpTempValues(2, printWriter, nameForUid, "Battery ");
            dumpTempValues(3, printWriter, nameForUid, "Skin ");
            printWriter.println("Fan speed: " + Arrays.toString(getFanSpeeds(nameForUid)) + "\n");
            CpuUsageInfo[] cpuUsages = getCpuUsages(nameForUid);
            for (int i = 0; i < cpuUsages.length; i++) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i, "Cpu usage of core: ", ", active = ");
                m.append(cpuUsages[i].getActive());
                m.append(", total = ");
                m.append(cpuUsages[i].getTotal());
                printWriter.println(m.toString());
            }
            printWriter.println("****** End of HardwarePropertiesManagerService dump ******");
        }
    }

    public final void dumpTempValues(int i, PrintWriter printWriter, String str, String str2) {
        dumpTempValues(str, printWriter, i, str2, "temperatures: ", 0);
        dumpTempValues(str, printWriter, i, str2, "throttling temperatures: ", 1);
        dumpTempValues(str, printWriter, i, str2, "shutdown temperatures: ", 2);
        dumpTempValues(str, printWriter, i, str2, "vr throttling temperatures: ", 3);
    }

    public final void dumpTempValues(
            String str, PrintWriter printWriter, int i, String str2, String str3, int i2) {
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str2, str3);
        m.append(Arrays.toString(getDeviceTemperatures(str, i, i2)));
        printWriter.println(m.toString());
    }

    public final void enforceHardwarePropertiesRetrievalAllowed(String str) {
        DevicePolicyManager devicePolicyManager;
        this.mAppOps.checkPackage(Binder.getCallingUid(), str);
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        VrManagerService.LocalService localService =
                (VrManagerService.LocalService)
                        LocalServices.getService(VrManagerService.LocalService.class);
        boolean z = false;
        if (Flags.systemServerRoleControllerEnabled()
                && CompatChanges.isChangeEnabled(307233716L)) {
            devicePolicyManager =
                    (DevicePolicyManager)
                            this.mContext
                                    .createContextAsUser(new UserHandle(userId), 0)
                                    .getSystemService(DevicePolicyManager.class);
        } else {
            devicePolicyManager =
                    (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
        }
        if (devicePolicyManager.isDeviceOwnerApp(str)
                || this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER")
                        == 0) {
            return;
        }
        if (localService != null) {
            VrManagerService vrManagerService = VrManagerService.this;
            synchronized (vrManagerService.mLock) {
                try {
                    ManagedApplicationService managedApplicationService =
                            vrManagerService.mCurrentVrService;
                    if (managedApplicationService != null) {
                        if (managedApplicationService.mComponent.getPackageName().equals(str)
                                && userId == vrManagerService.mCurrentVrService.mUserId) {
                            z = true;
                        }
                    }
                } finally {
                }
            }
            if (z) {
                return;
            }
        }
        throw new SecurityException(
                "The caller is neither a device owner, nor holding the DEVICE_POWER permission, nor"
                    + " the current VrListener.");
    }

    public final CpuUsageInfo[] getCpuUsages(String str) {
        CpuUsageInfo[] nativeGetCpuUsages;
        enforceHardwarePropertiesRetrievalAllowed(str);
        synchronized (this.mLock) {
            nativeGetCpuUsages = nativeGetCpuUsages();
        }
        return nativeGetCpuUsages;
    }

    public final float[] getDeviceTemperatures(String str, int i, int i2) {
        float[] nativeGetDeviceTemperatures;
        enforceHardwarePropertiesRetrievalAllowed(str);
        synchronized (this.mLock) {
            nativeGetDeviceTemperatures = nativeGetDeviceTemperatures(i, i2);
        }
        return nativeGetDeviceTemperatures;
    }

    public final float[] getFanSpeeds(String str) {
        float[] nativeGetFanSpeeds;
        enforceHardwarePropertiesRetrievalAllowed(str);
        synchronized (this.mLock) {
            nativeGetFanSpeeds = nativeGetFanSpeeds();
        }
        return nativeGetFanSpeeds;
    }
}
