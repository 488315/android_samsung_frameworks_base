package com.android.server.pm;

import android.content.Intent;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.internal.pm.pkg.component.ParsedIntentInfo;
import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.util.ArrayUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.utils.WatchedArraySet;

import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public abstract class AppsFilterUtils {

    public final class ParallelComputeComponentVisibility {
        public final ArrayMap mExistingSettings;
        public final ArraySet mForceQueryable;
        public final WatchedArraySet mProtectedBroadcasts;

        public ParallelComputeComponentVisibility(
                ArrayMap arrayMap, ArraySet arraySet, WatchedArraySet watchedArraySet) {
            this.mExistingSettings = arrayMap;
            this.mForceQueryable = arraySet;
            this.mProtectedBroadcasts = watchedArraySet;
        }
    }

    public static boolean canQueryAsInstaller(
            AndroidPackage androidPackage, PackageStateInternal packageStateInternal) {
        InstallSource installSource = packageStateInternal.getInstallSource();
        if (androidPackage.getPackageName().equals(installSource.mInstallerPackageName)) {
            return true;
        }
        return !installSource.mIsInitiatingPackageUninstalled
                && androidPackage.getPackageName().equals(installSource.mInitiatingPackageName);
    }

    public static boolean canQueryViaComponents(
            AndroidPackage androidPackage,
            AndroidPackage androidPackage2,
            WatchedArraySet watchedArraySet) {
        if (!androidPackage.getQueriesIntents().isEmpty()) {
            for (Intent intent : androidPackage.getQueriesIntents()) {
                if (matchesAnyComponents(intent, androidPackage2.getServices(), null)
                        || matchesAnyComponents(intent, androidPackage2.getActivities(), null)
                        || matchesAnyComponents(
                                intent, androidPackage2.getReceivers(), watchedArraySet)
                        || matchesAnyComponents(intent, androidPackage2.getProviders(), null)) {
                    return true;
                }
            }
        }
        if (!androidPackage.getQueriesProviders().isEmpty()) {
            Set queriesProviders = androidPackage.getQueriesProviders();
            for (int size = ArrayUtils.size(androidPackage2.getProviders()) - 1;
                    size >= 0;
                    size--) {
                ParsedProvider parsedProvider =
                        (ParsedProvider) androidPackage2.getProviders().get(size);
                if (parsedProvider.isExported() && parsedProvider.getAuthority() != null) {
                    StringTokenizer stringTokenizer =
                            new StringTokenizer(parsedProvider.getAuthority(), ";", false);
                    while (stringTokenizer.hasMoreElements()) {
                        if (queriesProviders.contains(stringTokenizer.nextToken())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean canQueryViaUsesLibrary(
            AndroidPackage androidPackage, AndroidPackage androidPackage2) {
        if (androidPackage2.getLibraryNames().isEmpty()) {
            return false;
        }
        List libraryNames = androidPackage2.getLibraryNames();
        int size = libraryNames.size();
        for (int i = 0; i < size; i++) {
            String str = (String) libraryNames.get(i);
            if (androidPackage.getUsesLibraries().contains(str)
                    || androidPackage.getUsesOptionalLibraries().contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean matchesAnyComponents(
            Intent intent, List list, WatchedArraySet watchedArraySet) {
        for (int size = ArrayUtils.size(list) - 1; size >= 0; size--) {
            ParsedMainComponent parsedMainComponent = (ParsedMainComponent) list.get(size);
            if (parsedMainComponent.isExported()) {
                List intents = parsedMainComponent.getIntents();
                for (int size2 = ArrayUtils.size(intents) - 1; size2 >= 0; size2--) {
                    if (((ParsedIntentInfo) intents.get(size2))
                                    .getIntentFilter()
                                    .match(
                                            intent.getAction(),
                                            intent.getType(),
                                            intent.getScheme(),
                                            intent.getData(),
                                            intent.getCategories(),
                                            "AppsFilter",
                                            true,
                                            watchedArraySet != null
                                                    ? watchedArraySet.mStorage
                                                    : null)
                            > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
