package com.android.server.knox;

import android.app.ActivityThread;
import android.app.admin.DevicePolicyManager;
import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArraySet;
import android.util.Log;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.devicepolicy.PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0;
import com.android.server.pm.PersonaManagerService;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.license.IEnterpriseLicense;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class IKnoxAnalyticsContainerImpl {
    public DevicePolicyManager devicePolicyManager;
    public final Context mContext;
    public final PersonaManagerService personaManagerService;
    public UserManager userManager;

    public IKnoxAnalyticsContainerImpl(
            Context context, PersonaManagerService personaManagerService) {
        this.personaManagerService = personaManagerService;
        this.mContext = context;
    }

    public static String getDeviceOwnerPackage() {
        IDevicePolicyManager asInterface =
                IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        String str = null;
        if (asInterface != null) {
            try {
                ComponentName deviceOwnerComponent = asInterface.getDeviceOwnerComponent(false);
                if (deviceOwnerComponent != null) {
                    str = deviceOwnerComponent.getPackageName();
                }
            } catch (Exception e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e, "getDeviceOwnerPackage exception -", "IFKnoxAnalyticsContainer");
            }
        }
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "getDeviceOwnerPackage packageName -", str, "IFKnoxAnalyticsContainer");
        return str;
    }

    public static PackageInfo getPackageInfo(int i, String str) {
        try {
            return ActivityThread.getPackageManager().getPackageInfo(str, 0L, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasKnoxPermission(String str) {
        try {
            IEnterpriseLicense asInterface =
                    IEnterpriseLicense.Stub.asInterface(
                            ServiceManager.getService("enterprise_license_policy"));
            if (asInterface != null) {
                List eLMPermissions = asInterface.getELMPermissions(str);
                if (eLMPermissions != null) {
                    return eLMPermissions.size() != 0;
                }
                return false;
            }
        } catch (Exception e) {
            Log.d("IFKnoxAnalyticsContainer", "hasKnoxPermission error : " + e);
        }
        Log.d("IFKnoxAnalyticsContainer", "EnterpriseLicenseService is null");
        return false;
    }

    public static boolean isAppSeparationUserId(int i) {
        return SemPersonaManager.isAppSeparationUserId(i) && i > 0;
    }

    public static void sendAnalyticsLog(KnoxAnalyticsData knoxAnalyticsData) {
        if (knoxAnalyticsData.getPayload() == null
                || knoxAnalyticsData.getPayload().getInt("cTp") != -1) {
            KnoxAnalytics.log(knoxAnalyticsData);
        }
    }

    public final String getProfileOwnerPackage(int i) {
        if (this.devicePolicyManager == null) {
            this.devicePolicyManager =
                    (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        }
        ComponentName profileOwnerAsUser =
                this.devicePolicyManager.getProfileOwnerAsUser(new UserHandle(i));
        if (profileOwnerAsUser != null) {
            return profileOwnerAsUser.getPackageName();
        }
        return null;
    }

    public final UserManager getUserManager() {
        if (this.userManager == null) {
            this.userManager = (UserManager) this.mContext.getSystemService("user");
        }
        return this.userManager;
    }

    public final Set getVisibleApps(int i) {
        List queryIntentActivitiesAsUser =
                this.mContext
                        .getPackageManager()
                        .queryIntentActivitiesAsUser(
                                PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m(
                                        "android.intent.action.MAIN",
                                        "android.intent.category.LAUNCHER"),
                                786432,
                                i);
        ArraySet arraySet = new ArraySet();
        Iterator it = queryIntentActivitiesAsUser.iterator();
        while (it.hasNext()) {
            arraySet.add(((ResolveInfo) it.next()).activityInfo.packageName);
        }
        return arraySet;
    }

    public final boolean isLoggingAllowedForUser(int i) {
        return getUserManager().isUserUnlocked()
                && ((SemPersonaManager.isKnoxId(i) && !SemPersonaManager.isSecureFolderId(i))
                        || "true".equals(SystemProperties.get("ro.organization_owned"))
                        || isAppSeparationUserId(i));
    }
}
