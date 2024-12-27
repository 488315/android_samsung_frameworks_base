package com.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManagerInternal;
import android.util.ArraySet;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.util.ArrayUtils;
import com.android.server.LocalServices;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppStateHelper {
    public static final long ACTIVE_NETWORK_DURATION_MILLIS = TimeUnit.MINUTES.toMillis(10);
    public final Context mContext;

    public AppStateHelper(Context context) {
        this.mContext = context;
    }

    public static boolean containsAny(String[] strArr, List list) {
        int length = strArr.length;
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i < length && i2 < size) {
            int compareTo = strArr[i].compareTo((String) arrayList.get(i2));
            if (compareTo == 0) {
                return true;
            }
            if (compareTo < 0) {
                i++;
            } else {
                i2++;
            }
        }
        return false;
    }

    public final List getDependencyPackages(List list) {
        final ArraySet arraySet = new ArraySet();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) this.mContext.getSystemService(ActivityManager.class)).getRunningAppProcesses()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (ArrayUtils.contains(runningAppProcessInfo.pkgList, str) || ArrayUtils.contains(runningAppProcessInfo.pkgDeps, str)) {
                    for (String str2 : runningAppProcessInfo.pkgList) {
                        arraySet.add(str2);
                    }
                }
            }
        }
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            arraySet.addAll(activityManagerInternal.getClientPackages((String) it2.next()));
        }
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        Iterator it3 = list.iterator();
        while (it3.hasNext()) {
            AndroidPackage androidPackage = ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal).mService.snapshotComputer().getPackage((String) it3.next());
            if (androidPackage != null) {
                arrayList.addAll(androidPackage.getLibraryNames());
                String staticSharedLibraryName = androidPackage.getStaticSharedLibraryName();
                if (staticSharedLibraryName != null) {
                    arrayList2.add(staticSharedLibraryName);
                }
                String sdkLibraryName = androidPackage.getSdkLibraryName();
                if (sdkLibraryName != null) {
                    arrayList3.add(sdkLibraryName);
                }
            }
        }
        if (!arrayList.isEmpty() || !arrayList2.isEmpty() || !arrayList3.isEmpty()) {
            Collections.sort(arrayList);
            Collections.sort(arrayList3);
            Collections.sort(arrayList2);
            packageManagerInternal.forEachPackageState(new Consumer() { // from class: com.android.server.pm.AppStateHelper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ArrayList arrayList4 = arrayList;
                    ArrayList arrayList5 = arrayList2;
                    ArrayList arrayList6 = arrayList3;
                    ArraySet arraySet2 = arraySet;
                    AndroidPackageInternal pkg = ((PackageStateInternal) obj).getPkg();
                    if (pkg == null) {
                        return;
                    }
                    if (AppStateHelper.containsAny(pkg.getUsesLibrariesSorted(), arrayList4) || AppStateHelper.containsAny(pkg.getUsesOptionalLibrariesSorted(), arrayList4) || AppStateHelper.containsAny(pkg.getUsesStaticLibrariesSorted(), arrayList5) || AppStateHelper.containsAny(pkg.getUsesSdkLibrariesSorted(), arrayList6)) {
                        arraySet2.add(pkg.getPackageName());
                    }
                }
            });
        }
        return new ArrayList(arraySet);
    }

    public final boolean hasActiveNetwork(int i, List list) {
        IPackageManager packageManager = ActivityThread.getPackageManager();
        NetworkStatsManager networkStatsManager = (NetworkStatsManager) this.mContext.getSystemService(NetworkStatsManager.class);
        long currentTimeMillis = System.currentTimeMillis();
        try {
            NetworkStats querySummary = networkStatsManager.querySummary(i, null, currentTimeMillis - ACTIVE_NETWORK_DURATION_MILLIS, currentTimeMillis);
            try {
                NetworkStats.Bucket bucket = new NetworkStats.Bucket();
                while (querySummary.hasNextBucket()) {
                    querySummary.getNextBucket(bucket);
                    if (((ArrayList) list).contains(packageManager.getNameForUid(bucket.getUid())) && (bucket.getRxPackets() > 0 || bucket.getTxPackets() > 0)) {
                        querySummary.close();
                        return true;
                    }
                }
                querySummary.close();
                return false;
            } finally {
            }
        } catch (Exception unused) {
            return false;
        }
    }
}
