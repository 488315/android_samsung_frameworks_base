package com.android.server.pm;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SparseSetArray;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.component.ParsedComponent;
import com.android.server.pm.pkg.component.ParsedIntentInfo;
import com.android.server.pm.pkg.component.ParsedMainComponent;
import com.android.server.pm.pkg.component.ParsedProvider;
import com.android.server.utils.WatchedArraySet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/* loaded from: classes3.dex */
public abstract class AppsFilterUtils {
  public static boolean requestsQueryAllPackages(AndroidPackage androidPackage) {
    return androidPackage
        .getRequestedPermissions()
        .contains("android.permission.QUERY_ALL_PACKAGES");
  }

  public static boolean canQueryViaComponents(
      AndroidPackage androidPackage,
      AndroidPackage androidPackage2,
      WatchedArraySet watchedArraySet) {
    if (!androidPackage.getQueriesIntents().isEmpty()) {
      Iterator it = androidPackage.getQueriesIntents().iterator();
      while (it.hasNext()) {
        if (matchesPackage((Intent) it.next(), androidPackage2, watchedArraySet)) {
          return true;
        }
      }
    }
    return !androidPackage.getQueriesProviders().isEmpty()
        && matchesProviders(androidPackage.getQueriesProviders(), androidPackage2);
  }

  public static boolean canQueryViaPackage(
      AndroidPackage androidPackage, AndroidPackage androidPackage2) {
    return !androidPackage.getQueriesPackages().isEmpty()
        && androidPackage.getQueriesPackages().contains(androidPackage2.getPackageName());
  }

  public static boolean canQueryAsInstaller(
      PackageStateInternal packageStateInternal, AndroidPackage androidPackage) {
    InstallSource installSource = packageStateInternal.getInstallSource();
    if (androidPackage.getPackageName().equals(installSource.mInstallerPackageName)) {
      return true;
    }
    return !installSource.mIsInitiatingPackageUninstalled
        && androidPackage.getPackageName().equals(installSource.mInitiatingPackageName);
  }

  public static boolean canQueryAsUpdateOwner(
      PackageStateInternal packageStateInternal, AndroidPackage androidPackage) {
    return androidPackage
        .getPackageName()
        .equals(packageStateInternal.getInstallSource().mUpdateOwnerPackageName);
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

  public static boolean matchesProviders(Set set, AndroidPackage androidPackage) {
    for (int size = ArrayUtils.size(androidPackage.getProviders()) - 1; size >= 0; size--) {
      ParsedProvider parsedProvider = (ParsedProvider) androidPackage.getProviders().get(size);
      if (parsedProvider.isExported() && parsedProvider.getAuthority() != null) {
        StringTokenizer stringTokenizer =
            new StringTokenizer(
                parsedProvider.getAuthority(), KnoxVpnFirewallHelper.DELIMITER, false);
        while (stringTokenizer.hasMoreElements()) {
          if (set.contains(stringTokenizer.nextToken())) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static boolean matchesPackage(
      Intent intent, AndroidPackage androidPackage, WatchedArraySet watchedArraySet) {
    return matchesAnyComponents(intent, androidPackage.getServices(), null)
        || matchesAnyComponents(intent, androidPackage.getActivities(), null)
        || matchesAnyComponents(intent, androidPackage.getReceivers(), watchedArraySet)
        || matchesAnyComponents(intent, androidPackage.getProviders(), null);
  }

  public static boolean matchesAnyComponents(
      Intent intent, List list, WatchedArraySet watchedArraySet) {
    for (int size = ArrayUtils.size(list) - 1; size >= 0; size--) {
      ParsedMainComponent parsedMainComponent = (ParsedMainComponent) list.get(size);
      if (parsedMainComponent.isExported()
          && matchesAnyFilter(intent, parsedMainComponent, watchedArraySet)) {
        return true;
      }
    }
    return false;
  }

  public static boolean matchesAnyFilter(
      Intent intent, ParsedComponent parsedComponent, WatchedArraySet watchedArraySet) {
    List intents = parsedComponent.getIntents();
    for (int size = ArrayUtils.size(intents) - 1; size >= 0; size--) {
      if (matchesIntentFilter(
          intent, ((ParsedIntentInfo) intents.get(size)).getIntentFilter(), watchedArraySet)) {
        return true;
      }
    }
    return false;
  }

  public static boolean matchesIntentFilter(
      Intent intent, IntentFilter intentFilter, WatchedArraySet watchedArraySet) {
    return intentFilter.match(
            intent.getAction(),
            intent.getType(),
            intent.getScheme(),
            intent.getData(),
            intent.getCategories(),
            "AppsFilter",
            true,
            watchedArraySet != null ? watchedArraySet.untrackedStorage() : null)
        > 0;
  }

  public final class ParallelComputeComponentVisibility {
    public final ArrayMap mExistingSettings;
    public final WatchedArraySet mForceQueryable;
    public final WatchedArraySet mProtectedBroadcasts;

    public ParallelComputeComponentVisibility(
        ArrayMap arrayMap, WatchedArraySet watchedArraySet, WatchedArraySet watchedArraySet2) {
      this.mExistingSettings = arrayMap;
      this.mForceQueryable = watchedArraySet;
      this.mProtectedBroadcasts = watchedArraySet2;
    }

    public SparseSetArray execute() {
      SparseSetArray sparseSetArray = new SparseSetArray();
      ExecutorService newFixedThreadPool =
          ConcurrentUtils.newFixedThreadPool(
              4, ParallelComputeComponentVisibility.class.getSimpleName(), 0);
      try {
        ArrayList arrayList = new ArrayList();
        for (int size = this.mExistingSettings.size() - 1; size >= 0; size--) {
          final PackageStateInternal packageStateInternal =
              (PackageStateInternal) this.mExistingSettings.valueAt(size);
          AndroidPackageInternal pkg = packageStateInternal.getPkg();
          if (pkg != null
              && !AppsFilterUtils.requestsQueryAllPackages(pkg)
              && (!pkg.getQueriesIntents().isEmpty() || !pkg.getQueriesProviders().isEmpty())) {
            arrayList.add(
                new Pair(
                    packageStateInternal,
                    newFixedThreadPool.submit(
                        new Callable() { // from class:
                          // com.android.server.pm.AppsFilterUtils$ParallelComputeComponentVisibility$$ExternalSyntheticLambda0
                          @Override // java.util.concurrent.Callable
                          public final Object call() {
                            ArraySet lambda$execute$0;
                            lambda$execute$0 =
                                AppsFilterUtils.ParallelComputeComponentVisibility.this
                                    .lambda$execute$0(packageStateInternal);
                            return lambda$execute$0;
                          }
                        })));
          }
        }
        for (int i = 0; i < arrayList.size(); i++) {
          int appId = ((PackageState) ((Pair) arrayList.get(i)).first).getAppId();
          try {
            ArraySet arraySet = (ArraySet) ((Future) ((Pair) arrayList.get(i)).second).get();
            if (arraySet.size() != 0) {
              sparseSetArray.addAll(appId, arraySet);
            }
          } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
          }
        }
        return sparseSetArray;
      } finally {
        newFixedThreadPool.shutdownNow();
      }
    }

    /* renamed from: getVisibleListOfQueryViaComponents, reason: merged with bridge method [inline-methods] */
    public final ArraySet lambda$execute$0(PackageStateInternal packageStateInternal) {
      ArraySet arraySet = new ArraySet();
      for (int size = this.mExistingSettings.size() - 1; size >= 0; size--) {
        PackageStateInternal packageStateInternal2 =
            (PackageStateInternal) this.mExistingSettings.valueAt(size);
        if (packageStateInternal.getAppId() != packageStateInternal2.getAppId()
            && packageStateInternal2.getPkg() != null
            && !this.mForceQueryable.contains(Integer.valueOf(packageStateInternal2.getAppId()))
            && AppsFilterUtils.canQueryViaComponents(
                packageStateInternal.getPkg(),
                packageStateInternal2.getPkg(),
                this.mProtectedBroadcasts)) {
          arraySet.add(Integer.valueOf(packageStateInternal2.getAppId()));
        }
      }
      return arraySet;
    }
  }
}
