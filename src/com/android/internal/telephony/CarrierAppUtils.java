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
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public final class CarrierAppUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "CarrierAppUtils";

    static /* synthetic */ void lambda$disableCarrierAppsUntilPrivileged$0(Boolean bool) {
    }

    private CarrierAppUtils() {
    }

    public static synchronized void disableCarrierAppsUntilPrivileged(String str, TelephonyManager telephonyManager, int i, Context context) {
        synchronized (CarrierAppUtils.class) {
            SystemConfigManager systemConfigManager = (SystemConfigManager) context.getSystemService(SystemConfigManager.class);
            disableCarrierAppsUntilPrivileged(str, telephonyManager, getContentResolverForUser(context, i), i, systemConfigManager.getDisabledUntilUsedPreinstalledCarrierApps(), systemConfigManager.getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries(), context);
        }
    }

    public static synchronized void disableCarrierAppsUntilPrivileged(String str, int i, Context context) {
        synchronized (CarrierAppUtils.class) {
            SystemConfigManager systemConfigManager = (SystemConfigManager) context.getSystemService(SystemConfigManager.class);
            disableCarrierAppsUntilPrivileged(str, null, getContentResolverForUser(context, i), i, systemConfigManager.getDisabledUntilUsedPreinstalledCarrierApps(), systemConfigManager.getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries(), context);
        }
    }

    private static ContentResolver getContentResolverForUser(Context context, int i) {
        return context.createContextAsUser(UserHandle.of(i), 0).getContentResolver();
    }

    private static boolean isUpdatedSystemApp(ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 128) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x007c A[Catch: NameNotFoundException -> 0x029f, TryCatch #0 {NameNotFoundException -> 0x029f, blocks: (B:12:0x004a, B:13:0x004e, B:15:0x0054, B:17:0x0062, B:20:0x0070, B:22:0x007c, B:23:0x0080, B:25:0x0086, B:27:0x009f, B:30:0x00be, B:34:0x00ca, B:36:0x0109, B:37:0x010d, B:39:0x0113, B:50:0x014a, B:54:0x0198, B:60:0x01a6, B:63:0x01ae, B:65:0x01b4, B:67:0x01df, B:68:0x01e3, B:70:0x01e9, B:73:0x01f3, B:75:0x01f8, B:77:0x01fc, B:81:0x0206, B:87:0x022c, B:102:0x0281, B:104:0x0287, B:109:0x0278), top: B:11:0x004a }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00be A[Catch: NameNotFoundException -> 0x029f, TRY_ENTER, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x029f, blocks: (B:12:0x004a, B:13:0x004e, B:15:0x0054, B:17:0x0062, B:20:0x0070, B:22:0x007c, B:23:0x0080, B:25:0x0086, B:27:0x009f, B:30:0x00be, B:34:0x00ca, B:36:0x0109, B:37:0x010d, B:39:0x0113, B:50:0x014a, B:54:0x0198, B:60:0x01a6, B:63:0x01ae, B:65:0x01b4, B:67:0x01df, B:68:0x01e3, B:70:0x01e9, B:73:0x01f3, B:75:0x01f8, B:77:0x01fc, B:81:0x0206, B:87:0x022c, B:102:0x0281, B:104:0x0287, B:109:0x0278), top: B:11:0x004a }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0225  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void disableCarrierAppsUntilPrivileged(java.lang.String r23, android.telephony.TelephonyManager r24, android.content.ContentResolver r25, int r26, java.util.Set<java.lang.String> r27, java.util.Map<java.lang.String, java.util.List<android.os.CarrierAssociatedAppEntry>> r28, android.content.Context r29) {
        /*
            Method dump skipped, instructions count: 678
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.CarrierAppUtils.disableCarrierAppsUntilPrivileged(java.lang.String, android.telephony.TelephonyManager, android.content.ContentResolver, int, java.util.Set, java.util.Map, android.content.Context):void");
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
                    List list = (List) arrayMap.get(key);
                    if (list == null) {
                        list = new ArrayList();
                        arrayMap.put(key, list);
                    }
                    list.add(new AssociatedAppInfo(applicationInfoIfSystemApp, carrierAssociatedAppEntry.addedInSdk));
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
