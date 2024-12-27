package com.android.server.knox;

import android.app.ActivityThread;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDeleteObserver;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.telephony.SmsApplication;
import com.android.server.ServiceKeeper;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PersonaManagerService;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.pm.Settings;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.devicesecurity.IPasswordPolicy;
import com.samsung.android.server.pm.PmLog;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ContainerDependencyWrapper {
    public static Context context;
    public static UserManager mUserManager;
    public static ContainerDependencyWrapper sInstance;
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static EdmStorageProvider mEdmStorageProvider = null;

    public static void addAppPackageNameToAllowList(int i, Context context2, List list) {
        ContextInfo contextInfo = new ContextInfo(new EdmStorageProvider(context2).getMUMContainerOwnerUid(i), i);
        try {
            IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
            if (asInterface == null) {
                return;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                Log.d("ContainerDependencyWrapper", "add package to Allowlist : " + str);
                asInterface.addAppPackageNameToWhiteList(contextInfo, str);
            }
        } catch (RemoteException e) {
            ActivityManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), "ContainerDependencyWrapper");
        }
    }

    public static void checkCallerPermissionFor(Context context2, String str, String str2) {
        if (ServiceKeeper.isAuthorized(Binder.getCallingPid(), Binder.getCallingUid(), context2, str, str2) == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder("Security Exception Occurred while pid[");
        sb.append(Binder.getCallingPid());
        sb.append("] with uid[");
        sb.append(Binder.getCallingUid());
        sb.append("] trying to access methodName [");
        sb.append(str2);
        sb.append("] in [");
        SecurityException securityException = new SecurityException(AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, str, "] service"));
        securityException.printStackTrace();
        throw securityException;
    }

    public static void deletePackageAsUserAndPersona(final PackageManagerService packageManagerService, final String str, final PersonaManagerService.PackageDeleteObs packageDeleteObs, final int i) {
        final Context context2 = packageManagerService.mContext;
        List list = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
        Log.d("PersonaServiceHelper", "START PACKAGE DELETE: observer{" + Integer.valueOf(packageDeleteObs.hashCode()) + "}\npkg{<packageName>}\nuser{" + i + "}\n");
        context2.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
        int callingUid = Binder.getCallingUid();
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, i, "deletePackageAsUser : uid = ", " userId = ", "PersonaServiceHelper");
        if (UserHandle.getUserId(callingUid) != i) {
            context2.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "deletePackage for user " + i);
        }
        if (packageManagerService.isUserRestricted(i, "no_uninstall_apps")) {
            try {
                packageDeleteObs.packageDeleted(str, -3);
            } catch (RemoteException unused) {
            }
        } else {
            final Handler handler = packageManagerService.mHandler;
            final Settings settings = packageManagerService.mSettings;
            handler.post(new Runnable() { // from class: com.android.server.pm.PersonaServiceHelper.4
                public final /* synthetic */ Context val$context;
                public final /* synthetic */ int val$flags;
                public final /* synthetic */ Handler val$handler;
                public final /* synthetic */ IPackageDeleteObserver val$observer;
                public final /* synthetic */ String val$packageName;
                public final /* synthetic */ PackageManagerService val$pms;
                public final /* synthetic */ Settings val$settings;
                public final /* synthetic */ int val$userId;

                public AnonymousClass4(final Handler handler2, final String str2, final PackageManagerService packageManagerService2, final int i2, final Settings settings2, final PersonaManagerService.PackageDeleteObs packageDeleteObs2, final Context context22) {
                    r1 = handler2;
                    r2 = str2;
                    r3 = packageManagerService2;
                    r4 = i2;
                    r5 = settings2;
                    r6 = packageDeleteObs2;
                    r7 = context22;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i2;
                    r1.removeCallbacks(this);
                    try {
                        if (PersonaServiceHelper.getApplicationPolicyService() == null || PersonaServiceHelper.getApplicationPolicyService().getApplicationUninstallationEnabled(new ContextInfo(Binder.getCallingUid()), r2)) {
                            PackageManagerService packageManagerService2 = r3;
                            String str2 = r2;
                            int i3 = r4;
                            packageManagerService2.getClass();
                            PmLog.logDebugInfoAndLogcat("deletePackageXForKnox: pkg{" + str2 + "}, user{" + i3 + "}, flags{4}");
                            i2 = packageManagerService2.mDeletePackageHelper.deletePackageX(i3, 4, -1L, str2, true);
                            try {
                                if (r2.contains("jp.co.mmbi.app")) {
                                    r5.addSharedUserLPw(9106, 1, 0, "android.uid.mmbi");
                                }
                            } catch (RemoteException unused2) {
                            }
                        } else {
                            Log.w("PersonaServiceHelper", "This app uninstallation is not allowed");
                            i2 = -1;
                        }
                    } catch (RemoteException unused3) {
                        i2 = 1;
                    }
                    if (i2 < 0) {
                        PmHook.uninstallLog(r4, r2);
                    }
                    if (r6 != null) {
                        try {
                            Log.d("PersonaServiceHelper", "return delete result to caller: " + r6.hashCode());
                            Log.d("PersonaServiceHelper", "returnCode: " + i2);
                            r6.packageDeleted(r2, i2);
                        } catch (RemoteException unused4) {
                            Log.i("PersonaServiceHelper", "Observer no longer exists.");
                        }
                    }
                    SmsApplication.getDefaultSmsApplication(r7, true);
                }
            });
        }
    }

    public static boolean isDisallowedAppForKnox(int i, String str) {
        Bundle bundle;
        List list = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
        try {
            if (SemPersonaManager.isKnoxId(i)) {
                ApplicationInfo applicationInfo = ActivityThread.getPackageManager().getApplicationInfo(str, 786560L, i);
                if (applicationInfo == null) {
                    return false;
                }
                bundle = applicationInfo.metaData;
            } else {
                bundle = null;
            }
            return PersonaServiceHelper.isDisallowedAppForKnox(i, str, bundle);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isPwdChangeRequested(int i) {
        try {
            IPasswordPolicy asInterface = IPasswordPolicy.Stub.asInterface(ServiceManager.getService("password_policy"));
            if (asInterface != null) {
                return asInterface.isChangeRequestedAsUser(i) > 0;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isRequiredAppForKnox(int i, String str) {
        Bundle bundle;
        List list = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
        try {
            if (SemPersonaManager.isKnoxId(i)) {
                ApplicationInfo applicationInfo = ActivityThread.getPackageManager().getApplicationInfo(str, 786560L, 0);
                if (applicationInfo == null) {
                    return false;
                }
                bundle = applicationInfo.metaData;
            } else {
                bundle = null;
            }
            return PersonaServiceHelper.isRequiredAppForKnox(i, str, bundle);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isTablet() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains("tablet");
    }
}
