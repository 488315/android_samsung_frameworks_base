package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CarrierAssociatedAppEntry;
import android.os.SystemConfigManager;
import android.os.UserHandle;
import android.permission.LegacyPermissionManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import com.android.internal.telephony.util.TelephonyUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public final class CarrierAppUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "CarrierAppUtils";

    static /* synthetic */ void lambda$disableCarrierAppsUntilPrivileged$0(Boolean bool) {
    }

    private CarrierAppUtils() {
    }

    public static synchronized void disableCarrierAppsUntilPrivileged(String str, TelephonyManager telephonyManager, int i, Context context) {
        SystemConfigManager systemConfigManager = (SystemConfigManager) context.getSystemService(SystemConfigManager.class);
        disableCarrierAppsUntilPrivileged(str, telephonyManager, getContentResolverForUser(context, i), i, systemConfigManager.getDisabledUntilUsedPreinstalledCarrierApps(), systemConfigManager.getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries(), context);
    }

    public static synchronized void disableCarrierAppsUntilPrivileged(String str, int i, Context context) {
        SystemConfigManager systemConfigManager = (SystemConfigManager) context.getSystemService(SystemConfigManager.class);
        disableCarrierAppsUntilPrivileged(str, null, getContentResolverForUser(context, i), i, systemConfigManager.getDisabledUntilUsedPreinstalledCarrierApps(), systemConfigManager.getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries(), context);
    }

    private static ContentResolver getContentResolverForUser(Context context, int i) {
        return context.createContextAsUser(UserHandle.of(i), 0).getContentResolver();
    }

    private static boolean isUpdatedSystemApp(ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 128) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x007c A[Catch: NameNotFoundException -> 0x029f, TryCatch #0 {NameNotFoundException -> 0x029f, blocks: (B:15:0x004a, B:16:0x004e, B:18:0x0054, B:20:0x0062, B:25:0x0070, B:27:0x007c, B:28:0x0080, B:30:0x0086, B:31:0x009f, B:34:0x00be, B:38:0x00ca, B:41:0x0109, B:42:0x010d, B:44:0x0113, B:54:0x014a, B:56:0x0198, B:60:0x01a6, B:63:0x01ae, B:65:0x01b4, B:67:0x01df, B:68:0x01e3, B:70:0x01e9, B:73:0x01f3, B:75:0x01f8, B:77:0x01fc, B:82:0x0206, B:89:0x022c, B:97:0x0281, B:99:0x0287, B:96:0x0278), top: B:104:0x004a }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00be A[Catch: NameNotFoundException -> 0x029f, TRY_ENTER, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x029f, blocks: (B:15:0x004a, B:16:0x004e, B:18:0x0054, B:20:0x0062, B:25:0x0070, B:27:0x007c, B:28:0x0080, B:30:0x0086, B:31:0x009f, B:34:0x00be, B:38:0x00ca, B:41:0x0109, B:42:0x010d, B:44:0x0113, B:54:0x014a, B:56:0x0198, B:60:0x01a6, B:63:0x01ae, B:65:0x01b4, B:67:0x01df, B:68:0x01e3, B:70:0x01e9, B:73:0x01f3, B:75:0x01f8, B:77:0x01fc, B:82:0x0206, B:89:0x022c, B:97:0x0281, B:99:0x0287, B:96:0x0278), top: B:104:0x004a }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x014a A[Catch: NameNotFoundException -> 0x029f, TryCatch #0 {NameNotFoundException -> 0x029f, blocks: (B:15:0x004a, B:16:0x004e, B:18:0x0054, B:20:0x0062, B:25:0x0070, B:27:0x007c, B:28:0x0080, B:30:0x0086, B:31:0x009f, B:34:0x00be, B:38:0x00ca, B:41:0x0109, B:42:0x010d, B:44:0x0113, B:54:0x014a, B:56:0x0198, B:60:0x01a6, B:63:0x01ae, B:65:0x01b4, B:67:0x01df, B:68:0x01e3, B:70:0x01e9, B:73:0x01f3, B:75:0x01f8, B:77:0x01fc, B:82:0x0206, B:89:0x022c, B:97:0x0281, B:99:0x0287, B:96:0x0278), top: B:104:0x004a }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void disableCarrierAppsUntilPrivileged(String str, TelephonyManager telephonyManager, ContentResolver contentResolver, int i, Set<String> set, Map<String, List<CarrierAssociatedAppEntry>> map, Context context) {
        boolean z;
        List<AssociatedAppInfo> list;
        boolean z2;
        List<AssociatedAppInfo> list2;
        String str2;
        String str3 = str;
        TelephonyManager telephonyManager2 = telephonyManager;
        PackageManager packageManager = context.getPackageManager();
        LegacyPermissionManager legacyPermissionManager = (LegacyPermissionManager) context.getSystemService(Context.LEGACY_PERMISSION_SERVICE);
        List<ApplicationInfo> defaultCarrierAppCandidatesHelper = getDefaultCarrierAppCandidatesHelper(i, set, context);
        if (defaultCarrierAppCandidatesHelper == null || defaultCarrierAppCandidatesHelper.isEmpty()) {
            return;
        }
        Map<String, List<AssociatedAppInfo>> defaultCarrierAssociatedAppsHelper = getDefaultCarrierAssociatedAppsHelper(i, map, context);
        ArrayList arrayList = new ArrayList();
        int intForUser = Settings.Secure.getIntForUser(contentResolver, Settings.Secure.CARRIER_APPS_HANDLED, 0, contentResolver.getUserId());
        boolean z3 = intForUser != 0;
        boolean z4 = intForUser == Build.VERSION.SDK_INT;
        try {
            Iterator<ApplicationInfo> it = defaultCarrierAppCandidatesHelper.iterator();
            while (it.hasNext()) {
                ApplicationInfo next = it.next();
                Iterator<ApplicationInfo> it2 = it;
                String str4 = next.packageName;
                if (telephonyManager2 != null) {
                    z = z4;
                    boolean z5 = telephonyManager2.checkCarrierPrivilegesForPackageAnyPhone(str4) == 1;
                    packageManager.setSystemAppState(str4, 0);
                    list = defaultCarrierAssociatedAppsHelper.get(str4);
                    if (list != null) {
                        Iterator<AssociatedAppInfo> it3 = list.iterator();
                        while (it3.hasNext()) {
                            packageManager.setSystemAppState(it3.next().appInfo.packageName, 0);
                            defaultCarrierAssociatedAppsHelper = defaultCarrierAssociatedAppsHelper;
                            z5 = z5;
                        }
                    }
                    z2 = z5;
                    Map<String, List<AssociatedAppInfo>> map2 = defaultCarrierAssociatedAppsHelper;
                    int applicationEnabledSetting = context.createContextAsUser(UserHandle.of(i), 0).getPackageManager().getApplicationEnabledSetting(str4);
                    PackageManager packageManager2 = packageManager;
                    if (z2) {
                        if (!z3 && !isUpdatedSystemApp(next) && applicationEnabledSetting == 0 && (next.flags & 8388608) != 0) {
                            Log.i(TAG, "Update state (" + str4 + "): DISABLED_UNTIL_USED for user " + i);
                            context.createContextAsUser(UserHandle.of(i), 0).getPackageManager().setSystemAppState(str4, 3);
                        }
                        if (list != null) {
                            for (AssociatedAppInfo associatedAppInfo : list) {
                                boolean z6 = !z3 || (!z && associatedAppInfo.addedInSdk != -1 && associatedAppInfo.addedInSdk > intForUser && associatedAppInfo.addedInSdk <= Build.VERSION.SDK_INT);
                                int applicationEnabledSetting2 = context.createContextAsUser(UserHandle.of(i), 0).getPackageManager().getApplicationEnabledSetting(associatedAppInfo.appInfo.packageName);
                                boolean z7 = (associatedAppInfo.appInfo.flags & 8388608) != 0;
                                if (z6 && applicationEnabledSetting2 == 0 && z7) {
                                    Log.i(TAG, "Update associated state (" + associatedAppInfo.appInfo.packageName + "): DISABLED_UNTIL_USED for user " + i);
                                    context.createContextAsUser(UserHandle.of(i), 0).getPackageManager().setSystemAppState(associatedAppInfo.appInfo.packageName, 3);
                                }
                            }
                        }
                    } else {
                        boolean zShouldUpdateEnabledState = shouldUpdateEnabledState(next, applicationEnabledSetting);
                        String str5 = "): ENABLED for user ";
                        if (zShouldUpdateEnabledState) {
                            list2 = list;
                            Log.i(TAG, "Update state (" + str4 + "): ENABLED for user " + i);
                            context.createContextAsUser(UserHandle.of(i), 0).getPackageManager().setSystemAppState(str4, 2);
                            context.createPackageContextAsUser(str3, 0, UserHandle.of(i)).getPackageManager().setApplicationEnabledSetting(str4, 1, 1);
                        } else {
                            list2 = list;
                        }
                        if (list2 != null) {
                            Iterator<AssociatedAppInfo> it4 = list2.iterator();
                            while (it4.hasNext()) {
                                AssociatedAppInfo next2 = it4.next();
                                Iterator<AssociatedAppInfo> it5 = it4;
                                int applicationEnabledSetting3 = context.createContextAsUser(UserHandle.of(i), 0).getPackageManager().getApplicationEnabledSetting(next2.appInfo.packageName);
                                boolean z8 = (next2.appInfo.flags & 8388608) != 0;
                                if (applicationEnabledSetting3 != 0) {
                                    boolean z9 = z8;
                                    if (applicationEnabledSetting3 == 4 || !z9) {
                                        Log.i(TAG, "Update associated state (" + next2.appInfo.packageName + str5 + i);
                                        str2 = str5;
                                        context.createContextAsUser(UserHandle.of(i), 0).getPackageManager().setSystemAppState(next2.appInfo.packageName, 2);
                                        context.createPackageContextAsUser(str3, 0, UserHandle.of(i)).getPackageManager().setApplicationEnabledSetting(next2.appInfo.packageName, 1, 1);
                                    } else {
                                        str2 = str5;
                                    }
                                }
                                it4 = it5;
                                str5 = str2;
                            }
                        }
                        arrayList.add(next.packageName);
                    }
                    str3 = str;
                    telephonyManager2 = telephonyManager;
                    it = it2;
                    z4 = z;
                    packageManager = packageManager2;
                    defaultCarrierAssociatedAppsHelper = map2;
                } else {
                    z = z4;
                }
                packageManager.setSystemAppState(str4, 0);
                list = defaultCarrierAssociatedAppsHelper.get(str4);
                if (list != null) {
                }
                z2 = z5;
                Map<String, List<AssociatedAppInfo>> map22 = defaultCarrierAssociatedAppsHelper;
                int applicationEnabledSetting4 = context.createContextAsUser(UserHandle.of(i), 0).getPackageManager().getApplicationEnabledSetting(str4);
                PackageManager packageManager22 = packageManager;
                if (z2) {
                }
                str3 = str;
                telephonyManager2 = telephonyManager;
                it = it2;
                z4 = z;
                packageManager = packageManager22;
                defaultCarrierAssociatedAppsHelper = map22;
            }
            boolean z10 = z4;
            if (!z3 || !z10) {
                Settings.Secure.putIntForUser(contentResolver, Settings.Secure.CARRIER_APPS_HANDLED, Build.VERSION.SDK_INT, contentResolver.getUserId());
            }
            if (arrayList.isEmpty()) {
                return;
            }
            String[] strArr = new String[arrayList.size()];
            arrayList.toArray(strArr);
            legacyPermissionManager.grantDefaultPermissionsToEnabledCarrierApps(strArr, UserHandle.of(i), TelephonyUtils.DIRECT_EXECUTOR, new Consumer() { // from class: com.android.internal.telephony.CarrierAppUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CarrierAppUtils.lambda$disableCarrierAppsUntilPrivileged$0((Boolean) obj);
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "Could not reach PackageManager", e);
        }
    }

    private static boolean shouldUpdateEnabledState(ApplicationInfo applicationInfo, int i) {
        return Flags.cleanupCarrierAppUpdateEnabledStateLogic() ? !isUpdatedSystemApp(applicationInfo) && (i == 0 || i == 4 || (applicationInfo.flags & 8388608) == 0) : (!isUpdatedSystemApp(applicationInfo) && i == 0) || i == 4 || (applicationInfo.flags & 8388608) == 0;
    }

    public static List<ApplicationInfo> getDefaultCarrierApps(TelephonyManager telephonyManager, int i, Context context) {
        List<ApplicationInfo> defaultCarrierAppCandidates = getDefaultCarrierAppCandidates(i, context);
        if (defaultCarrierAppCandidates == null || defaultCarrierAppCandidates.isEmpty()) {
            return null;
        }
        for (int size = defaultCarrierAppCandidates.size() - 1; size >= 0; size--) {
            if (telephonyManager.checkCarrierPrivilegesForPackageAnyPhone(defaultCarrierAppCandidates.get(size).packageName) != 1) {
                defaultCarrierAppCandidates.remove(size);
            }
        }
        return defaultCarrierAppCandidates;
    }

    public static List<ApplicationInfo> getDefaultCarrierAppCandidates(int i, Context context) {
        return getDefaultCarrierAppCandidatesHelper(i, ((SystemConfigManager) context.getSystemService(SystemConfigManager.class)).getDisabledUntilUsedPreinstalledCarrierApps(), context);
    }

    private static List<ApplicationInfo> getDefaultCarrierAppCandidatesHelper(int i, Set<String> set, Context context) {
        if (set == null || set.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList(set.size());
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            ApplicationInfo applicationInfoIfSystemApp = getApplicationInfoIfSystemApp(i, it.next(), context);
            if (applicationInfoIfSystemApp != null) {
                arrayList.add(applicationInfoIfSystemApp);
            }
        }
        return arrayList;
    }

    private static Map<String, List<AssociatedAppInfo>> getDefaultCarrierAssociatedAppsHelper(int i, Map<String, List<CarrierAssociatedAppEntry>> map, Context context) {
        ArrayMap arrayMap = new ArrayMap(map.size());
        for (Map.Entry<String, List<CarrierAssociatedAppEntry>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<CarrierAssociatedAppEntry> value = entry.getValue();
            for (int i2 = 0; i2 < value.size(); i2++) {
                CarrierAssociatedAppEntry carrierAssociatedAppEntry = value.get(i2);
                ApplicationInfo applicationInfoIfSystemApp = getApplicationInfoIfSystemApp(i, carrierAssociatedAppEntry.packageName, context);
                if (applicationInfoIfSystemApp != null && !isUpdatedSystemApp(applicationInfoIfSystemApp)) {
                    List arrayList = (List) arrayMap.get(key);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        arrayMap.put(key, arrayList);
                    }
                    arrayList.add(new AssociatedAppInfo(applicationInfoIfSystemApp, carrierAssociatedAppEntry.addedInSdk));
                }
            }
        }
        return arrayMap;
    }

    private static ApplicationInfo getApplicationInfoIfSystemApp(int i, String str, Context context) {
        try {
            ApplicationInfo applicationInfo = context.createContextAsUser(UserHandle.of(i), 0).getPackageManager().getApplicationInfo(str, 537952256);
            if (applicationInfo != null) {
                return applicationInfo;
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

        AssociatedAppInfo(ApplicationInfo applicationInfo, int i) {
            this.appInfo = applicationInfo;
            this.addedInSdk = i;
        }
    }
}
