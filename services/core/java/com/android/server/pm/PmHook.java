package com.android.server.pm;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.IApplicationPolicy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PmHook {
    public static IDevicePolicyManager mDevicePolicyManager;
    public static boolean mSystemReady;

    public static final void auditLogInstallFail(int i, String str, boolean z) {
        AuditLog.logAsUser(
                3,
                5,
                false,
                Process.myPid(),
                "PackageManagerService",
                XmlUtils$$ExternalSyntheticOutline0.m("Install application ", str, " failed"),
                z ? "" : null,
                i);
    }

    public static final void enableDisableApplicationLog(int i, int i2, String str, String str2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (str2 == null) {
                if (i != 1 && i != 0) {
                    if (i == 2 || i == 3) {
                        AuditLog.logAsUser(
                                5,
                                5,
                                true,
                                Process.myPid(),
                                "PackageManagerService",
                                "Application " + str + " has been disabled.",
                                "",
                                i2);
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
                AuditLog.logAsUser(
                        5,
                        5,
                        true,
                        Process.myPid(),
                        "PackageManagerService",
                        "Application " + str + " has been enabled.",
                        "",
                        i2);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            if (i != 1 && i != 0) {
                if (i == 2 || i == 3) {
                    AuditLog.logAsUser(
                            5,
                            5,
                            true,
                            Process.myPid(),
                            "PackageManagerService",
                            "Component " + str + "/" + str2 + " has been disabled.",
                            "",
                            i2);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            AuditLog.logAsUser(
                    5,
                    5,
                    true,
                    Process.myPid(),
                    "PackageManagerService",
                    "Component " + str + "/" + str2 + " has been enabled.",
                    "",
                    i2);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static boolean isUpdateAllowedByMdm(
            PackageManager.ComponentEnabledSetting componentEnabledSetting,
            int i,
            Context context) {
        EdmStorageProvider edmStorageProvider;
        int mUMContainerOwnerUid;
        ComponentName componentNameForUid;
        String packageName = componentEnabledSetting.getPackageName();
        int enabledState = componentEnabledSetting.getEnabledState();
        IApplicationPolicy asInterface =
                IApplicationPolicy.Stub.asInterface(
                        ServiceManager.getService("application_policy"));
        try {
            if (asInterface == null) {
                Log.w("PM_HOOK", "ApplicationPolicy is null");
            } else if (enabledState == 1 || enabledState == 0) {
                if (!asInterface.getApplicationStateEnabledAsUser(packageName, false, i)) {
                    Log.w(
                            "PM_HOOK",
                            "This app can not be enabled due to EDM policy. packageName = "
                                    + packageName);
                    return false;
                }
                if (componentEnabledSetting.isComponent()) {
                    ComponentName componentName = componentEnabledSetting.getComponentName();
                    if (!asInterface.getApplicationComponentState(
                            new ContextInfo(Binder.getCallingUid()), componentName)) {
                        Log.w(
                                "PM_HOOK",
                                "This component can not be enabled due to EDM policy. componentName"
                                    + " = "
                                        + componentName);
                        return false;
                    }
                }
            }
        } catch (RemoteException unused) {
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (SemPersonaManager.isKnoxId(i)
                    && enabledState == 1
                    && (componentNameForUid =
                                    edmStorageProvider.getComponentNameForUid(
                                            (mUMContainerOwnerUid =
                                                    (edmStorageProvider =
                                                                    new EdmStorageProvider(context))
                                                            .getMUMContainerOwnerUid(i))))
                            != null
                    && componentNameForUid.getPackageName().equals(packageName)
                    && UserHandle.getUserId(mUMContainerOwnerUid) != i) {
                Log.d("PM_HOOK", "try to enable admin for CL container: " + mUMContainerOwnerUid);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (mDevicePolicyManager == null) {
                    mDevicePolicyManager =
                            IDevicePolicyManager.Stub.asInterface(
                                    ServiceManager.getService("device_policy"));
                }
                IDevicePolicyManager iDevicePolicyManager = mDevicePolicyManager;
                if (iDevicePolicyManager != null) {
                    try {
                        ComponentName profileOwnerAsUser =
                                iDevicePolicyManager.getProfileOwnerAsUser(i);
                        if (profileOwnerAsUser != null
                                && profileOwnerAsUser.getPackageName() != null
                                && packageName.equals(profileOwnerAsUser.getPackageName())) {
                            Log.d(
                                    "PM_HOOK",
                                    "Can enable admin inside container to support AfW feature");
                        }
                    } catch (RemoteException e) {
                        Log.d("PM_HOOK", "RemoteException: " + e.getMessage());
                    }
                }
                Log.w("PM_HOOK", "Cannot enable this package inside the container.");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static final void uninstallLog(int i, String str) {
        AuditLog.logAsUser(
                5,
                5,
                true,
                Process.myPid(),
                "PackageManagerService",
                XmlUtils$$ExternalSyntheticOutline0.m("Uninstall application ", str, " failed"),
                "",
                i);
    }
}
