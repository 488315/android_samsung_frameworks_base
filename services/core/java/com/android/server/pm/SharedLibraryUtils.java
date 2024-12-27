package com.android.server.pm;

import android.content.pm.SharedLibraryInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class SharedLibraryUtils {
    public static void findSharedLibrariesRecursive(
            SharedLibraryInfo sharedLibraryInfo, ArrayList arrayList, Set set) {
        HashSet hashSet = (HashSet) set;
        if (hashSet.contains(sharedLibraryInfo.getName())) {
            return;
        }
        hashSet.add(sharedLibraryInfo.getName());
        arrayList.add(sharedLibraryInfo);
        if (sharedLibraryInfo.getDependencies() != null) {
            Iterator it = sharedLibraryInfo.getDependencies().iterator();
            while (it.hasNext()) {
                findSharedLibrariesRecursive((SharedLibraryInfo) it.next(), arrayList, set);
            }
        }
    }
}
