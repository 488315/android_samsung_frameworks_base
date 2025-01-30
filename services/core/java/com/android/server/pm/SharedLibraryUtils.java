package com.android.server.pm;

import android.content.pm.SharedLibraryInfo;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.SharedLibraryWrapper;
import com.android.server.utils.WatchedLongSparseArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public abstract class SharedLibraryUtils {
    public static boolean addSharedLibraryToPackageVersionMap(Map map, SharedLibraryInfo sharedLibraryInfo) {
        String name = sharedLibraryInfo.getName();
        if (map.containsKey(name)) {
            if (sharedLibraryInfo.getType() != 2 || ((WatchedLongSparseArray) map.get(name)).indexOfKey(sharedLibraryInfo.getLongVersion()) >= 0) {
                return false;
            }
        } else {
            map.put(name, new WatchedLongSparseArray());
        }
        ((WatchedLongSparseArray) map.get(name)).put(sharedLibraryInfo.getLongVersion(), sharedLibraryInfo);
        return true;
    }

    public static SharedLibraryInfo getSharedLibraryInfo(String str, long j, Map map, Map map2) {
        if (map2 != null) {
            WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) map2.get(str);
            SharedLibraryInfo sharedLibraryInfo = watchedLongSparseArray != null ? (SharedLibraryInfo) watchedLongSparseArray.get(j) : null;
            if (sharedLibraryInfo != null) {
                return sharedLibraryInfo;
            }
        }
        WatchedLongSparseArray watchedLongSparseArray2 = (WatchedLongSparseArray) map.get(str);
        if (watchedLongSparseArray2 == null) {
            return null;
        }
        return (SharedLibraryInfo) watchedLongSparseArray2.get(j);
    }

    public static List findSharedLibraries(PackageStateInternal packageStateInternal) {
        if (!packageStateInternal.getTransientState().getUsesLibraryInfos().isEmpty()) {
            ArrayList arrayList = new ArrayList();
            HashSet hashSet = new HashSet();
            Iterator it = packageStateInternal.getTransientState().getUsesLibraryInfos().iterator();
            while (it.hasNext()) {
                findSharedLibrariesRecursive(((SharedLibraryWrapper) it.next()).getInfo(), arrayList, hashSet);
            }
            return arrayList;
        }
        return Collections.emptyList();
    }

    public static void findSharedLibrariesRecursive(SharedLibraryInfo sharedLibraryInfo, ArrayList arrayList, Set set) {
        if (set.contains(sharedLibraryInfo.getName())) {
            return;
        }
        set.add(sharedLibraryInfo.getName());
        arrayList.add(sharedLibraryInfo);
        if (sharedLibraryInfo.getDependencies() != null) {
            Iterator it = sharedLibraryInfo.getDependencies().iterator();
            while (it.hasNext()) {
                findSharedLibrariesRecursive((SharedLibraryInfo) it.next(), arrayList, set);
            }
        }
    }
}
