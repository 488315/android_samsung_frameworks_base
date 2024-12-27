package com.android.server.pm;

import android.content.pm.SigningDetails;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.om.OverlayReferenceMapper;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;
import com.android.server.utils.WatchedSparseBooleanMatrix;
import com.android.server.utils.WatchedSparseSetArray;

import com.samsung.android.server.pm.PmServerUtils;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class AppsFilterBase {
    public FeatureConfig mFeatureConfig;
    public WatchedArraySet mForceQueryable;
    public String[] mForceQueryableByDevicePackageNames;
    public SnapshotCache mForceQueryableSnapshot;
    public Handler mHandler;
    public SnapshotCache mImplicitQueryableSnapshot;
    public WatchedSparseSetArray mImplicitlyQueryable;
    public OverlayReferenceMapper mOverlayReferenceMapper;
    public WatchedArraySet mProtectedBroadcasts;
    public SnapshotCache mProtectedBroadcastsSnapshot;
    public WatchedSparseSetArray mQueriesViaComponent;
    public SnapshotCache mQueriesViaComponentSnapshot;
    public WatchedSparseSetArray mQueriesViaPackage;
    public SnapshotCache mQueriesViaPackageSnapshot;
    public WatchedSparseSetArray mQueryableViaUsesLibrary;
    public SnapshotCache mQueryableViaUsesLibrarySnapshot;
    public WatchedSparseSetArray mQueryableViaUsesPermission;
    public SnapshotCache mQueryableViaUsesPermissionSnapshot;
    public WatchedSparseSetArray mRetainedImplicitlyQueryable;
    public SnapshotCache mRetainedImplicitlyQueryableSnapshot;
    public WatchedSparseBooleanMatrix mShouldFilterCache;
    public SnapshotCache mShouldFilterCacheSnapshot;
    public boolean mSystemAppsQueryable;
    public SigningDetails mSystemSigningDetails;
    public AtomicBoolean mQueriesViaComponentRequireRecompute = new AtomicBoolean(false);
    public volatile boolean mCacheReady = false;
    public volatile boolean mCacheEnabled = true;
    public volatile boolean mNeedToUpdateCacheForImplicitAccess = false;
    public final AtomicBoolean mCacheValid = new AtomicBoolean(false);

    public static void dumpPackageSet(
            PrintWriter printWriter,
            Object obj,
            ArraySet arraySet,
            String str,
            String str2,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        if (arraySet == null || arraySet.size() <= 0) {
            return;
        }
        if (obj == null || arraySet.contains(obj)) {
            printWriter.append((CharSequence) str2).append((CharSequence) str).println(":");
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (obj == null || obj.equals(next)) {
                    printWriter
                            .append((CharSequence) str2)
                            .append("  ")
                            .println(
                                    (Object)
                                            appsFilterBase$$ExternalSyntheticLambda0.toString(
                                                    next));
                }
            }
        }
    }

    public static void dumpQueriesMap(
            PrintWriter printWriter,
            Integer num,
            WatchedSparseSetArray watchedSparseSetArray,
            String str,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        for (int i = 0; i < watchedSparseSetArray.mStorage.size(); i++) {
            int keyAt = watchedSparseSetArray.mStorage.keyAt(i);
            Integer valueOf = Integer.valueOf(keyAt);
            if (valueOf.equals(num)) {
                dumpPackageSet(
                        printWriter,
                        null,
                        watchedSparseSetArray.mStorage.get(keyAt),
                        appsFilterBase$$ExternalSyntheticLambda0.toString(valueOf),
                        str,
                        appsFilterBase$$ExternalSyntheticLambda0);
            } else {
                dumpPackageSet(
                        printWriter,
                        num,
                        watchedSparseSetArray.mStorage.get(keyAt),
                        appsFilterBase$$ExternalSyntheticLambda0.toString(valueOf),
                        str,
                        appsFilterBase$$ExternalSyntheticLambda0);
            }
        }
    }

    public final boolean canQueryPackage(AndroidPackage androidPackage, String str) {
        if (UserHandle.getAppId(androidPackage.getUid()) >= 10000
                && this.mFeatureConfig.packageIsEnabled(androidPackage)
                && !androidPackage
                        .getRequestedPermissions()
                        .contains("android.permission.QUERY_ALL_PACKAGES")) {
            return !androidPackage.getQueriesPackages().isEmpty()
                    && androidPackage.getQueriesPackages().contains(str);
        }
        return true;
    }

    public void dumpForceQueryable(
            PrintWriter printWriter,
            Integer num,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        printWriter.println("  queries via forceQueryable:");
        dumpPackageSet(
                printWriter,
                num,
                this.mForceQueryable.mStorage,
                "forceQueryable",
                "  ",
                appsFilterBase$$ExternalSyntheticLambda0);
    }

    public void dumpQueriesViaComponent(
            PrintWriter printWriter,
            Integer num,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        printWriter.println("  queries via component:");
        dumpQueriesMap(
                printWriter,
                num,
                this.mQueriesViaComponent,
                "    ",
                appsFilterBase$$ExternalSyntheticLambda0);
    }

    public void dumpQueriesViaImplicitlyQueryable(
            PrintWriter printWriter,
            Integer num,
            int[] iArr,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        printWriter.println("  queryable via interaction:");
        for (int i : iArr) {
            printWriter.append("    User ").append((CharSequence) Integer.toString(i)).println(":");
            Integer num2 = null;
            dumpQueriesMap(
                    printWriter,
                    num == null ? null : Integer.valueOf(UserHandle.getUid(i, num.intValue())),
                    this.mImplicitlyQueryable,
                    "      ",
                    appsFilterBase$$ExternalSyntheticLambda0);
            if (num != null) {
                num2 = Integer.valueOf(UserHandle.getUid(i, num.intValue()));
            }
            dumpQueriesMap(
                    printWriter,
                    num2,
                    this.mRetainedImplicitlyQueryable,
                    "      ",
                    appsFilterBase$$ExternalSyntheticLambda0);
        }
    }

    public void dumpQueriesViaPackage(
            PrintWriter printWriter,
            Integer num,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        printWriter.println("  queries via package name:");
        dumpQueriesMap(
                printWriter,
                num,
                this.mQueriesViaPackage,
                "    ",
                appsFilterBase$$ExternalSyntheticLambda0);
    }

    public void dumpQueriesViaUsesLibrary(
            PrintWriter printWriter,
            Integer num,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        printWriter.println("  queryable via uses-library:");
        dumpQueriesMap(
                printWriter,
                num,
                this.mQueryableViaUsesLibrary,
                "    ",
                appsFilterBase$$ExternalSyntheticLambda0);
    }

    public final SparseArray getVisibilityAllowList(
            PackageDataSnapshot packageDataSnapshot,
            PackageStateInternal packageStateInternal,
            int[] iArr,
            ArrayMap arrayMap) {
        int binarySearch;
        int i;
        int i2;
        int[] iArr2 = null;
        if (isForceQueryable(packageStateInternal.getAppId())) {
            return null;
        }
        SparseArray sparseArray = new SparseArray(iArr.length);
        int i3 = 0;
        int i4 = 0;
        while (i4 < iArr.length) {
            int i5 = iArr[i4];
            int size = arrayMap.size();
            int[] iArr3 = new int[size];
            int size2 = arrayMap.size() - 1;
            int[] iArr4 = iArr2;
            int i6 = i3;
            while (size2 >= 0) {
                PackageStateInternal packageStateInternal2 =
                        (PackageStateInternal) arrayMap.valueAt(size2);
                int appId = packageStateInternal2.getAppId();
                if (appId >= 10000
                        && (binarySearch = Arrays.binarySearch(iArr3, i3, i6, appId)) < 0) {
                    i = i6;
                    if (shouldFilterApplication(
                            packageDataSnapshot,
                            UserHandle.getUid(i5, appId),
                            packageStateInternal2,
                            packageStateInternal,
                            i5)) {
                        i2 = 0;
                    } else {
                        int[] iArr5 = iArr4 == null ? new int[size] : iArr4;
                        int i7 = ~binarySearch;
                        int i8 = i - i7;
                        i2 = 0;
                        System.arraycopy(iArr3, i7, iArr5, 0, i8);
                        iArr3[i7] = appId;
                        System.arraycopy(iArr5, 0, iArr3, i7 + 1, i8);
                        i6 = i + 1;
                        iArr4 = iArr5;
                        size2--;
                        i3 = i2;
                    }
                } else {
                    i2 = i3;
                    i = i6;
                }
                i6 = i;
                size2--;
                i3 = i2;
            }
            sparseArray.put(i5, Arrays.copyOf(iArr3, i6));
            i4++;
            i3 = i3;
            iArr2 = null;
        }
        return sparseArray;
    }

    public SparseArray getVisibilityAllowList(
            PackageDataSnapshot packageDataSnapshot,
            PackageStateInternal packageStateInternal,
            int[] iArr,
            WatchedArrayMap watchedArrayMap) {
        return getVisibilityAllowList(
                packageDataSnapshot, packageStateInternal, iArr, watchedArrayMap.mStorage);
    }

    public boolean isForceQueryable(int i) {
        WatchedArraySet watchedArraySet = this.mForceQueryable;
        return watchedArraySet.mStorage.contains(Integer.valueOf(i));
    }

    public boolean isImplicitlyQueryable(int i, int i2) {
        WatchedSparseSetArray watchedSparseSetArray = this.mImplicitlyQueryable;
        return watchedSparseSetArray.mStorage.contains(i, Integer.valueOf(i2));
    }

    public boolean isQueryableViaComponent(int i, int i2) {
        WatchedSparseSetArray watchedSparseSetArray = this.mQueriesViaComponent;
        return watchedSparseSetArray.mStorage.contains(i, Integer.valueOf(i2));
    }

    public boolean isQueryableViaComponentWhenRequireRecompute(
            ArrayMap arrayMap,
            PackageStateInternal packageStateInternal,
            ArraySet arraySet,
            AndroidPackage androidPackage,
            int i,
            int i2) {
        if (packageStateInternal != null) {
            return packageStateInternal.getPkg() != null
                    && AppsFilterUtils.canQueryViaComponents(
                            packageStateInternal.getPkg(),
                            androidPackage,
                            this.mProtectedBroadcasts);
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            AndroidPackageInternal pkg = ((PackageStateInternal) arraySet.valueAt(size)).getPkg();
            if (pkg != null
                    && AppsFilterUtils.canQueryViaComponents(
                            pkg, androidPackage, this.mProtectedBroadcasts)) {
                return true;
            }
        }
        return false;
    }

    public boolean isQueryableViaPackage(int i, int i2) {
        WatchedSparseSetArray watchedSparseSetArray = this.mQueriesViaPackage;
        return watchedSparseSetArray.mStorage.contains(i, Integer.valueOf(i2));
    }

    public boolean isQueryableViaUsesLibrary(int i, int i2) {
        WatchedSparseSetArray watchedSparseSetArray = this.mQueryableViaUsesLibrary;
        return watchedSparseSetArray.mStorage.contains(i, Integer.valueOf(i2));
    }

    public boolean isQueryableViaUsesPermission(int i, int i2) {
        WatchedSparseSetArray watchedSparseSetArray = this.mQueryableViaUsesPermission;
        return watchedSparseSetArray.mStorage.contains(i, Integer.valueOf(i2));
    }

    public boolean isRetainedImplicitlyQueryable(int i, int i2) {
        WatchedSparseSetArray watchedSparseSetArray = this.mRetainedImplicitlyQueryable;
        return watchedSparseSetArray.mStorage.contains(i, Integer.valueOf(i2));
    }

    public final boolean shouldFilterApplication(
            PackageDataSnapshot packageDataSnapshot,
            int i,
            Object obj,
            PackageStateInternal packageStateInternal,
            int i2) {
        int appId = UserHandle.getAppId(i);
        if (appId < 10000
                || packageStateInternal.getAppId() < 10000
                || appId == packageStateInternal.getAppId()) {
            return false;
        }
        if (Process.isSdkSandboxUid(appId)) {
            int uid = UserHandle.getUid(i2, packageStateInternal.getAppId());
            if (isForceQueryable(packageStateInternal.getAppId())
                    || isImplicitlyQueryable(i, uid)) {
                return false;
            }
            return (Flags.allowSdkSandboxQueryIntentActivities()
                            && uid == Process.getAppUidForSdkSandboxUid(i))
                    ? false
                    : true;
        }
        if (this.mCacheReady && this.mCacheEnabled) {
            if (!shouldFilterApplicationUsingCache(i, packageStateInternal.getAppId(), i2)) {
                return false;
            }
        } else if (!shouldFilterApplicationInternal(
                (Computer) packageDataSnapshot, i, obj, packageStateInternal, i2)) {
            return false;
        }
        if (this.mFeatureConfig.isLoggingEnabled(appId)) {
            StringBuilder sb = new StringBuilder("interaction: ");
            sb.append(obj == null ? "system" : obj);
            sb.append(" -> ");
            sb.append(packageStateInternal);
            sb.append(" BLOCKED");
            Slog.i("AppsFilter", sb.toString());
        }
        return true;
    }

    public final boolean shouldFilterApplicationInternal(
            Computer computer,
            int i,
            Object obj,
            PackageStateInternal packageStateInternal,
            int i2) {
        if (!this.mFeatureConfig.isGloballyEnabled()) {
            return false;
        }
        if (obj == null) {
            Slog.wtf("AppsFilter", "No setting found for non system uid " + i);
            return true;
        }
        int appId = UserHandle.getAppId(i);
        int appId2 = packageStateInternal.getAppId();
        if (appId == appId2 || appId < 10000 || appId2 < 10000) {
            return false;
        }
        ArraySet arraySet = new ArraySet();
        PackageStateInternal packageStateInternal2 = null;
        if (obj instanceof PackageStateInternal) {
            PackageStateInternal packageStateInternal3 = (PackageStateInternal) obj;
            if (packageStateInternal3.hasSharedUser()) {
                SharedUserSetting sharedUser =
                        computer.getSharedUser(packageStateInternal3.getSharedUserAppId());
                if (sharedUser != null) {
                    arraySet.addAll(sharedUser.mPackages.mStorage);
                }
            } else {
                packageStateInternal2 = packageStateInternal3;
            }
        } else {
            arraySet.addAll(((SharedUserSetting) obj).mPackages.mStorage);
        }
        PackageStateInternal packageStateInternal4 = packageStateInternal2;
        if (packageStateInternal4 == null) {
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                AndroidPackage pkg = ((PackageStateInternal) arraySet.valueAt(size)).getPkg();
                if (pkg != null && !this.mFeatureConfig.packageIsEnabled(pkg)) {
                    return false;
                }
            }
        } else if (packageStateInternal4.getPkg() != null
                && !this.mFeatureConfig.packageIsEnabled(packageStateInternal4.getPkg())) {
            return false;
        }
        if (packageStateInternal4 == null) {
            for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                AndroidPackageInternal pkg2 =
                        ((PackageStateInternal) arraySet.valueAt(size2)).getPkg();
                if (pkg2 != null
                        && pkg2.getRequestedPermissions()
                                .contains("android.permission.QUERY_ALL_PACKAGES")) {
                    return false;
                }
            }
        } else if (packageStateInternal4.getPkg() != null
                && packageStateInternal4
                        .getPkg()
                        .getRequestedPermissions()
                        .contains("android.permission.QUERY_ALL_PACKAGES")) {
            return false;
        }
        AndroidPackageInternal pkg3 = packageStateInternal.getPkg();
        if (pkg3 == null) {
            return !PmServerUtils.installedOnSdcardAsUser(packageStateInternal, 8192L, i2);
        }
        if (pkg3.isStaticSharedLibrary()
                || isForceQueryable(appId2)
                || isQueryableViaPackage(appId, appId2)) {
            return false;
        }
        if (this.mQueriesViaComponentRequireRecompute.get()) {
            if (isQueryableViaComponentWhenRequireRecompute(
                    computer.getPackageStates(),
                    packageStateInternal4,
                    arraySet,
                    pkg3,
                    appId,
                    appId2)) {
                return false;
            }
        } else if (isQueryableViaComponent(appId, appId2)) {
            return false;
        }
        if (isImplicitlyQueryable(i, UserHandle.getUid(i2, appId2))
                || isRetainedImplicitlyQueryable(i, UserHandle.getUid(i2, appId2))) {
            return false;
        }
        String packageName = pkg3.getPackageName();
        if (!arraySet.isEmpty()) {
            int size3 = arraySet.size();
            for (int i3 = 0; i3 < size3; i3++) {
                if (this.mOverlayReferenceMapper.isValidActor(
                        packageName,
                        ((PackageStateInternal) arraySet.valueAt(i3)).getPackageName())) {
                    return false;
                }
            }
        } else if (this.mOverlayReferenceMapper.isValidActor(
                packageName, packageStateInternal4.getPackageName())) {
            return false;
        }
        return (isQueryableViaUsesLibrary(appId, appId2)
                        || isQueryableViaUsesPermission(appId, appId2))
                ? false
                : true;
    }

    public boolean shouldFilterApplicationUsingCache(int i, int i2, int i3) {
        int indexOfKey = this.mShouldFilterCache.indexOfKey(i);
        if (indexOfKey < 0) {
            Slog.wtf("AppsFilter", "Encountered calling uid with no cached rules: " + i);
            return true;
        }
        int uid = UserHandle.getUid(i3, i2);
        int indexOfKey2 = this.mShouldFilterCache.indexOfKey(uid);
        if (indexOfKey2 < 0) {
            PendingIntentController$$ExternalSyntheticOutline0.m(
                    i,
                    uid,
                    "Encountered calling -> target with no cached rules: ",
                    " -> ",
                    "AppsFilter");
            return true;
        }
        WatchedSparseBooleanMatrix watchedSparseBooleanMatrix = this.mShouldFilterCache;
        watchedSparseBooleanMatrix.validateIndex(indexOfKey);
        watchedSparseBooleanMatrix.validateIndex(indexOfKey2);
        int[] iArr = watchedSparseBooleanMatrix.mMap;
        return watchedSparseBooleanMatrix.valueAtInternal(iArr[indexOfKey], iArr[indexOfKey2]);
    }
}
