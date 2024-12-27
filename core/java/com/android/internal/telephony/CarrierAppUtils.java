package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.CarrierAssociatedAppEntry;
import android.os.SystemConfigManager;
import android.os.UserHandle;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CarrierAppUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "CarrierAppUtils";

    private CarrierAppUtils() {}

    public static synchronized void disableCarrierAppsUntilPrivileged(
            String callingPackage, TelephonyManager telephonyManager, int userId, Context context) {
        synchronized (CarrierAppUtils.class) {
            SystemConfigManager config =
                    (SystemConfigManager) context.getSystemService(SystemConfigManager.class);
            Set<String> systemCarrierAppsDisabledUntilUsed =
                    config.getDisabledUntilUsedPreinstalledCarrierApps();
            Map<String, List<CarrierAssociatedAppEntry>>
                    systemCarrierAssociatedAppsDisabledUntilUsed =
                            config.getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries();
            ContentResolver contentResolver = getContentResolverForUser(context, userId);
            disableCarrierAppsUntilPrivileged(
                    callingPackage,
                    telephonyManager,
                    contentResolver,
                    userId,
                    systemCarrierAppsDisabledUntilUsed,
                    systemCarrierAssociatedAppsDisabledUntilUsed,
                    context);
        }
    }

    public static synchronized void disableCarrierAppsUntilPrivileged(
            String callingPackage, int userId, Context context) {
        synchronized (CarrierAppUtils.class) {
            SystemConfigManager config =
                    (SystemConfigManager) context.getSystemService(SystemConfigManager.class);
            Set<String> systemCarrierAppsDisabledUntilUsed =
                    config.getDisabledUntilUsedPreinstalledCarrierApps();
            Map<String, List<CarrierAssociatedAppEntry>>
                    systemCarrierAssociatedAppsDisabledUntilUsed =
                            config.getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries();
            ContentResolver contentResolver = getContentResolverForUser(context, userId);
            disableCarrierAppsUntilPrivileged(
                    callingPackage,
                    null,
                    contentResolver,
                    userId,
                    systemCarrierAppsDisabledUntilUsed,
                    systemCarrierAssociatedAppsDisabledUntilUsed,
                    context);
        }
    }

    private static ContentResolver getContentResolverForUser(Context context, int userId) {
        Context userContext = context.createContextAsUser(UserHandle.of(userId), 0);
        return userContext.getContentResolver();
    }

    private static boolean isUpdatedSystemApp(ApplicationInfo ai) {
        return (ai.flags & 128) != 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:126:0x00fe, code lost:

       if ((r0.flags & 8388608) == 0) goto L59;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void disableCarrierAppsUntilPrivileged(
            java.lang.String r32,
            android.telephony.TelephonyManager r33,
            android.content.ContentResolver r34,
            int r35,
            java.util.Set<java.lang.String> r36,
            java.util.Map<java.lang.String, java.util.List<android.os.CarrierAssociatedAppEntry>>
                    r37,
            android.content.Context r38) {
        /*
            Method dump skipped, instructions count: 941
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.telephony.CarrierAppUtils.disableCarrierAppsUntilPrivileged(java.lang.String,"
                    + " android.telephony.TelephonyManager, android.content.ContentResolver, int,"
                    + " java.util.Set, java.util.Map, android.content.Context):void");
    }

    static /* synthetic */ void lambda$disableCarrierAppsUntilPrivileged$0(Boolean isSuccess) {}

    public static List<ApplicationInfo> getDefaultCarrierApps(
            TelephonyManager telephonyManager, int userId, Context context) {
        List<ApplicationInfo> candidates = getDefaultCarrierAppCandidates(userId, context);
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        for (int i = candidates.size() - 1; i >= 0; i--) {
            ApplicationInfo ai = candidates.get(i);
            String packageName = ai.packageName;
            boolean hasPrivileges =
                    telephonyManager.checkCarrierPrivilegesForPackageAnyPhone(packageName) == 1;
            if (!hasPrivileges) {
                candidates.remove(i);
            }
        }
        return candidates;
    }

    public static List<ApplicationInfo> getDefaultCarrierAppCandidates(
            int userId, Context context) {
        Set<String> systemCarrierAppsDisabledUntilUsed =
                ((SystemConfigManager) context.getSystemService(SystemConfigManager.class))
                        .getDisabledUntilUsedPreinstalledCarrierApps();
        return getDefaultCarrierAppCandidatesHelper(
                userId, systemCarrierAppsDisabledUntilUsed, context);
    }

    private static List<ApplicationInfo> getDefaultCarrierAppCandidatesHelper(
            int userId, Set<String> systemCarrierAppsDisabledUntilUsed, Context context) {
        if (systemCarrierAppsDisabledUntilUsed == null
                || systemCarrierAppsDisabledUntilUsed.isEmpty()) {
            return null;
        }
        List<ApplicationInfo> apps = new ArrayList<>(systemCarrierAppsDisabledUntilUsed.size());
        for (String packageName : systemCarrierAppsDisabledUntilUsed) {
            ApplicationInfo ai = getApplicationInfoIfSystemApp(userId, packageName, context);
            if (ai != null) {
                apps.add(ai);
            }
        }
        return apps;
    }

    private static Map<String, List<AssociatedAppInfo>> getDefaultCarrierAssociatedAppsHelper(
            int userId,
            Map<String, List<CarrierAssociatedAppEntry>>
                    systemCarrierAssociatedAppsDisabledUntilUsed,
            Context context) {
        int size = systemCarrierAssociatedAppsDisabledUntilUsed.size();
        Map<String, List<AssociatedAppInfo>> associatedApps = new ArrayMap<>(size);
        for (Map.Entry<String, List<CarrierAssociatedAppEntry>> entry :
                systemCarrierAssociatedAppsDisabledUntilUsed.entrySet()) {
            String carrierAppPackage = entry.getKey();
            List<CarrierAssociatedAppEntry> associatedAppPackages = entry.getValue();
            for (int j = 0; j < associatedAppPackages.size(); j++) {
                CarrierAssociatedAppEntry associatedApp = associatedAppPackages.get(j);
                ApplicationInfo ai =
                        getApplicationInfoIfSystemApp(userId, associatedApp.packageName, context);
                if (ai != null && !isUpdatedSystemApp(ai)) {
                    List<AssociatedAppInfo> appList = associatedApps.get(carrierAppPackage);
                    if (appList == null) {
                        appList = new ArrayList<>();
                        associatedApps.put(carrierAppPackage, appList);
                    }
                    appList.add(new AssociatedAppInfo(ai, associatedApp.addedInSdk));
                }
            }
        }
        return associatedApps;
    }

    private static ApplicationInfo getApplicationInfoIfSystemApp(
            int userId, String packageName, Context context) {
        try {
            ApplicationInfo ai =
                    context.createContextAsUser(UserHandle.of(userId), 0)
                            .getPackageManager()
                            .getApplicationInfo(packageName, 537952256);
            if (ai != null) {
                return ai;
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "Could not reach PackageManager", e);
            return null;
        }
    }

    private static final class AssociatedAppInfo {
        public final int addedInSdk;
        public final ApplicationInfo appInfo;

        AssociatedAppInfo(ApplicationInfo appInfo, int addedInSdk) {
            this.appInfo = appInfo;
            this.addedInSdk = addedInSdk;
        }
    }
}
