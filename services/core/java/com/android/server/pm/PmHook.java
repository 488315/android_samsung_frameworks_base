package com.android.server.pm;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.pm.pkg.AndroidPackage;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.license.IEnterpriseLicense;

/* loaded from: classes3.dex */
public abstract class PmHook {
    public static IDevicePolicyManager mDevicePolicyManager = null;
    public static boolean mSystemReady = false;

    public static void onSystemReady() {
        mSystemReady = true;
    }

    public static final void beginInstallLog(AndroidPackage androidPackage, int i) {
        AuditLog.logAsUser(5, 5, true, Process.myPid(), "PackageManagerService", String.format("Starting to install application %s version %s", androidPackage.getPackageName(), Long.valueOf(androidPackage.getLongVersionCode())), "", i);
    }

    public static final void uninstallLog(String str, boolean z, int i) {
        AuditLog.logAsUser(5, 5, true, Process.myPid(), "PackageManagerService", String.format(z ? "Uninstall application %s succeeded" : "Uninstall application %s failed", str), "", i);
    }

    public static final void installSuccesLog(String str, int i) {
        AuditLog.logAsUser(5, 5, true, Process.myPid(), "PackageManagerService", String.format("Install application %s succeeded", str), "", i);
    }

    public static final void installFailLog(Context context, AndroidPackage androidPackage, int i) {
        auditLogInstallFail(androidPackage.getPackageName(), i);
    }

    public static final void auditLogInstallFail(String str, int i) {
        auditLogInstallFail(str, i, true);
    }

    public static final void auditLogInstallFail(String str, int i, boolean z) {
        AuditLog.logAsUser(3, 5, false, Process.myPid(), "PackageManagerService", String.format("Install application %s failed", str), z ? "" : null, i);
    }

    public static final void enableDisableApplicationLog(String str, String str2, int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (str2 == null) {
                if (i != 1 && i != 0) {
                    if (i == 2 || i == 3) {
                        AuditLog.logAsUser(5, 5, true, Process.myPid(), "PackageManagerService", String.format("Application %s has been disabled.", str), "", i2);
                    }
                }
                AuditLog.logAsUser(5, 5, true, Process.myPid(), "PackageManagerService", String.format("Application %s has been enabled.", str), "", i2);
            }
            if (i != 1 && i != 0) {
                if (i == 2 || i == 3) {
                    AuditLog.logAsUser(5, 5, true, Process.myPid(), "PackageManagerService", String.format("Component %s/%s has been disabled.", str, str2), "", i2);
                }
            }
            AuditLog.logAsUser(5, 5, true, Process.myPid(), "PackageManagerService", String.format("Component %s/%s has been enabled.", str, str2), "", i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00ed A[Catch: all -> 0x00fd, TRY_LEAVE, TryCatch #2 {all -> 0x00fd, blocks: (B:7:0x006f, B:10:0x0077, B:12:0x0086, B:14:0x0090, B:16:0x0096, B:26:0x00b4, B:28:0x00ba, B:30:0x00c0, B:32:0x00ca, B:20:0x00ed, B:36:0x00d2), top: B:6:0x006f, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isUpdateAllowedByMdm(PackageManager.ComponentEnabledSetting componentEnabledSetting, int i, Context context) {
        EdmStorageProvider edmStorageProvider;
        int mUMContainerOwnerUid;
        ComponentName componentNameForUid;
        ComponentName profileOwnerAsUser;
        boolean z;
        String packageName = componentEnabledSetting.getPackageName();
        int enabledState = componentEnabledSetting.getEnabledState();
        IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        try {
            if (asInterface == null) {
                Log.w("PM_HOOK", "ApplicationPolicy is null");
            } else if (enabledState == 1 || enabledState == 0) {
                if (!asInterface.getApplicationStateEnabledAsUser(packageName, false, i)) {
                    Log.w("PM_HOOK", "This app can not be enabled due to EDM policy. packageName = " + packageName);
                    return false;
                }
                if (componentEnabledSetting.isComponent()) {
                    ComponentName componentName = componentEnabledSetting.getComponentName();
                    if (!asInterface.getApplicationComponentState(new ContextInfo(Binder.getCallingUid()), componentName)) {
                        Log.w("PM_HOOK", "This component can not be enabled due to EDM policy. componentName = " + componentName);
                        return false;
                    }
                }
            }
        } catch (RemoteException unused) {
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (SemPersonaManager.isKnoxId(i) && enabledState == 1 && (componentNameForUid = edmStorageProvider.getComponentNameForUid((mUMContainerOwnerUid = (edmStorageProvider = new EdmStorageProvider(context)).getMUMContainerOwnerUid(i)))) != null && componentNameForUid.getPackageName().equals(packageName) && UserHandle.getUserId(mUMContainerOwnerUid) != i) {
                Log.d("PM_HOOK", "try to enable admin for CL container: " + mUMContainerOwnerUid);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                IDevicePolicyManager devicePolicyManager = getDevicePolicyManager();
                if (devicePolicyManager != null) {
                    try {
                        profileOwnerAsUser = devicePolicyManager.getProfileOwnerAsUser(i);
                    } catch (RemoteException e) {
                        Log.d("PM_HOOK", "RemoteException: " + e.getMessage());
                    }
                    if (profileOwnerAsUser != null && profileOwnerAsUser.getPackageName() != null && packageName.equals(profileOwnerAsUser.getPackageName())) {
                        Log.d("PM_HOOK", "Can enable admin inside container to support AfW feature");
                        z = true;
                        if (!z) {
                            Log.w("PM_HOOK", "Cannot enable this package inside the container.");
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return false;
                        }
                    }
                }
                z = false;
                if (!z) {
                }
            }
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isPkgLicenseActivated(String str) {
        IEnterpriseLicense asInterface;
        if (!mSystemReady || (asInterface = IEnterpriseLicense.Stub.asInterface(ServiceManager.getService("enterprise_license_policy"))) == null) {
            return false;
        }
        try {
            return asInterface.getInstanceId(str) != null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        } catch (SecurityException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void updateAdminPermissions() {
        if (mSystemReady) {
            IEnterpriseLicense asInterface = IEnterpriseLicense.Stub.asInterface(ServiceManager.getService("enterprise_license_policy"));
            try {
                if (asInterface != null) {
                    asInterface.updateAdminPermissions();
                } else {
                    Log.d("PM_HOOK", "ENTERPRISE_LICENSE_POLICY_SERVICE is null");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NullPointerException e2) {
                Log.e("PM_HOOK", "NPE occurs for EnterpriseLicense", e2);
                e2.printStackTrace();
            }
        }
    }

    public static IDevicePolicyManager getDevicePolicyManager() {
        if (mDevicePolicyManager == null) {
            mDevicePolicyManager = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        }
        return mDevicePolicyManager;
    }

    public static boolean hasSelectivePermissionsForMDM(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return bundle.getBoolean("com.samsung.knoxlicense.permissions");
    }
}
