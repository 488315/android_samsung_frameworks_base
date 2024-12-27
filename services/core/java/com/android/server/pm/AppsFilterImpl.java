package com.android.server.pm;

import android.content.pm.PackageManagerInternal;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseSetArray;

import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.pkg.component.ParsedInstrumentation;
import com.android.internal.pm.pkg.component.ParsedPermission;
import com.android.internal.pm.pkg.component.ParsedUsesPermission;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.FgThread;
import com.android.server.compat.CompatChange;
import com.android.server.om.OverlayReferenceMapper;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.utils.Snappable;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.WatchedArraySet;
import com.android.server.utils.WatchedSparseBooleanMatrix;
import com.android.server.utils.WatchedSparseSetArray;
import com.android.server.utils.Watcher;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public final class AppsFilterImpl extends AppsFilterBase implements Watchable, Snappable {
    public final ArrayMap mPermissionToUids;
    public final AnonymousClass1 mSnapshot;
    public final ArrayMap mUsesPermissionToUids;
    public final PackageManagerTracedLock mForceQueryableLock = new PackageManagerTracedLock(null);
    public final PackageManagerTracedLock mQueriesViaPackageLock =
            new PackageManagerTracedLock(null);
    public final PackageManagerTracedLock mQueriesViaComponentLock =
            new PackageManagerTracedLock(null);
    public final PackageManagerTracedLock mImplicitlyQueryableLock =
            new PackageManagerTracedLock(null);
    public final PackageManagerTracedLock mQueryableViaUsesLibraryLock =
            new PackageManagerTracedLock(null);
    public final PackageManagerTracedLock mProtectedBroadcastsLock =
            new PackageManagerTracedLock(null);
    public final PackageManagerTracedLock mQueryableViaUsesPermissionLock =
            new PackageManagerTracedLock(null);
    public final PackageManagerTracedLock mCacheLock = new PackageManagerTracedLock(null);
    public final WatchableImpl mWatchable = new WatchableImpl();

    public final class FeatureConfigImpl implements FeatureConfig, CompatChange.ChangeListener {
        public AppsFilterImpl mAppsFilter;
        public final ArraySet mDisabledPackages;
        public volatile boolean mFeatureEnabled;
        public final PackageManagerServiceInjector mInjector;
        public SparseBooleanArray mLoggingEnabled;
        public final PackageManagerInternal mPmInternal;

        public FeatureConfigImpl(
                PackageManagerInternal packageManagerInternal,
                PackageManagerServiceInjector packageManagerServiceInjector) {
            this.mFeatureEnabled = true;
            this.mDisabledPackages = new ArraySet();
            this.mLoggingEnabled = null;
            this.mPmInternal = packageManagerInternal;
            this.mInjector = packageManagerServiceInjector;
        }

        public FeatureConfigImpl(FeatureConfigImpl featureConfigImpl) {
            this.mFeatureEnabled = true;
            ArraySet arraySet = new ArraySet();
            this.mDisabledPackages = arraySet;
            this.mLoggingEnabled = null;
            this.mInjector = null;
            this.mPmInternal = null;
            this.mFeatureEnabled = featureConfigImpl.mFeatureEnabled;
            synchronized (featureConfigImpl.mDisabledPackages) {
                arraySet.addAll(featureConfigImpl.mDisabledPackages);
            }
            this.mLoggingEnabled = featureConfigImpl.mLoggingEnabled;
        }

        @Override // com.android.server.pm.FeatureConfig
        public final void enableLogging(int i, boolean z) {
            int indexOfKey;
            if (z) {
                if (this.mLoggingEnabled == null) {
                    this.mLoggingEnabled = new SparseBooleanArray();
                }
                this.mLoggingEnabled.put(i, true);
                return;
            }
            SparseBooleanArray sparseBooleanArray = this.mLoggingEnabled;
            if (sparseBooleanArray == null || (indexOfKey = sparseBooleanArray.indexOfKey(i)) < 0) {
                return;
            }
            this.mLoggingEnabled.removeAt(indexOfKey);
            if (this.mLoggingEnabled.size() == 0) {
                this.mLoggingEnabled = null;
            }
        }

        @Override // com.android.server.pm.FeatureConfig
        public final boolean isGloballyEnabled() {
            return this.mFeatureEnabled;
        }

        @Override // com.android.server.pm.FeatureConfig
        public final boolean isLoggingEnabled(int i) {
            SparseBooleanArray sparseBooleanArray = this.mLoggingEnabled;
            return sparseBooleanArray != null && sparseBooleanArray.indexOfKey(i) >= 0;
        }

        @Override // com.android.server.compat.CompatChange.ChangeListener
        public final void onCompatChange(String str) {
            Computer snapshotComputer =
                    ((PackageManagerService.PackageManagerInternalImpl) this.mPmInternal)
                            .mService.snapshotComputer();
            AndroidPackage androidPackage = snapshotComputer.getPackage(str);
            if (androidPackage == null) {
                return;
            }
            long currentTimeMicro = SystemClock.currentTimeMicro();
            updateEnabledState(androidPackage);
            AppsFilterImpl appsFilterImpl = this.mAppsFilter;
            if (appsFilterImpl.mCacheReady) {
                ArrayMap packageStates = snapshotComputer.getPackageStates();
                UserInfo[] userInfos = snapshotComputer.getUserInfos();
                PackageManagerTracedLock packageManagerTracedLock = appsFilterImpl.mCacheLock;
                boolean z = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        appsFilterImpl.updateShouldFilterCacheForPackage(
                                snapshotComputer,
                                null,
                                (PackageStateInternal) packageStates.get(str),
                                packageStates,
                                userInfos,
                                -1,
                                packageStates.size());
                    } catch (Throwable th) {
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
                appsFilterImpl.dispatchChange(appsFilterImpl);
            }
            AppsFilterImpl appsFilterImpl2 = this.mAppsFilter;
            long currentTimeMicro2 = SystemClock.currentTimeMicro() - currentTimeMicro;
            int length = snapshotComputer.getUserInfos().length;
            int size = snapshotComputer.getPackageStates().size();
            int uid = androidPackage.getUid();
            if (appsFilterImpl2.mCacheReady) {
                FrameworkStatsLog.write(
                        FrameworkStatsLog.PACKAGE_MANAGER_APPS_FILTER_CACHE_UPDATE_REPORTED,
                        4,
                        uid,
                        currentTimeMicro2,
                        length,
                        size,
                        appsFilterImpl2.mShouldFilterCache.mSize);
            }
        }

        @Override // com.android.server.pm.FeatureConfig
        public final void onSystemReady() {
            this.mFeatureEnabled =
                    DeviceConfig.getBoolean(
                            "package_manager_service", "package_query_filtering_enabled", true);
            DeviceConfig.addOnPropertiesChangedListener(
                    "package_manager_service",
                    FgThread.getExecutor(),
                    new DeviceConfig
                            .OnPropertiesChangedListener() { // from class:
                                                             // com.android.server.pm.AppsFilterImpl$FeatureConfigImpl$$ExternalSyntheticLambda0
                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            AppsFilterImpl.FeatureConfigImpl featureConfigImpl =
                                    AppsFilterImpl.FeatureConfigImpl.this;
                            featureConfigImpl.getClass();
                            if (properties
                                    .getKeyset()
                                    .contains("package_query_filtering_enabled")) {
                                synchronized (featureConfigImpl) {
                                    featureConfigImpl.mFeatureEnabled =
                                            properties.getBoolean(
                                                    "package_query_filtering_enabled", true);
                                }
                            }
                        }
                    });
            this.mInjector.getCompatibility().registerListener(135549675L, this);
        }

        @Override // com.android.server.pm.FeatureConfig
        public final boolean packageIsEnabled(AndroidPackage androidPackage) {
            boolean z;
            synchronized (this.mDisabledPackages) {
                z = !this.mDisabledPackages.contains(androidPackage.getPackageName());
            }
            return z;
        }

        @Override // com.android.server.pm.FeatureConfig
        public final FeatureConfigImpl snapshot() {
            return new FeatureConfigImpl(this);
        }

        public final void updateEnabledState(AndroidPackage androidPackage) {
            boolean isChangeEnabledInternalNoLogging =
                    this.mInjector
                            .getCompatibility()
                            .isChangeEnabledInternalNoLogging(
                                    135549675L,
                                    ((AndroidPackageHidden) androidPackage)
                                            .toAppInfoWithoutState());
            synchronized (this.mDisabledPackages) {
                try {
                    if (isChangeEnabledInternalNoLogging) {
                        this.mDisabledPackages.remove(androidPackage.getPackageName());
                    } else {
                        this.mDisabledPackages.add(androidPackage.getPackageName());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Watchable watchable = this.mAppsFilter;
            if (watchable != null) {
                watchable.dispatchChange(watchable);
            }
        }

        @Override // com.android.server.pm.FeatureConfig
        public final void updatePackageState(PackageStateInternal packageStateInternal, boolean z) {
            enableLogging(
                    packageStateInternal.getAppId(),
                    (packageStateInternal.getPkg() == null
                                    || z
                                    || (!packageStateInternal.getPkg().isTestOnly()
                                            && !packageStateInternal.getPkg().isDebuggable()))
                            ? false
                            : true);
            if (!z) {
                if (packageStateInternal.getPkg() != null) {
                    updateEnabledState(packageStateInternal.getPkg());
                }
            } else {
                synchronized (this.mDisabledPackages) {
                    this.mDisabledPackages.remove(packageStateInternal.getPackageName());
                }
                Watchable watchable = this.mAppsFilter;
                if (watchable != null) {
                    watchable.dispatchChange(watchable);
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v22, types: [com.android.server.pm.AppsFilterImpl$1] */
    public AppsFilterImpl(
            FeatureConfig featureConfig,
            String[] strArr,
            boolean z,
            OverlayReferenceMapper.Provider provider,
            Handler handler) {
        this.mFeatureConfig = featureConfig;
        this.mForceQueryableByDevicePackageNames = strArr;
        this.mSystemAppsQueryable = z;
        this.mOverlayReferenceMapper = new OverlayReferenceMapper(provider);
        this.mHandler = handler;
        this.mShouldFilterCache = new WatchedSparseBooleanMatrix();
        WatchedSparseBooleanMatrix watchedSparseBooleanMatrix = this.mShouldFilterCache;
        this.mShouldFilterCacheSnapshot =
                new SnapshotCache.Auto(
                        watchedSparseBooleanMatrix,
                        watchedSparseBooleanMatrix,
                        "AppsFilter.mShouldFilterCache",
                        0);
        this.mImplicitlyQueryable = new WatchedSparseSetArray();
        WatchedSparseSetArray watchedSparseSetArray = this.mImplicitlyQueryable;
        this.mImplicitQueryableSnapshot =
                new SnapshotCache.Auto(
                        watchedSparseSetArray,
                        watchedSparseSetArray,
                        "AppsFilter.mImplicitlyQueryable",
                        0);
        this.mRetainedImplicitlyQueryable = new WatchedSparseSetArray();
        WatchedSparseSetArray watchedSparseSetArray2 = this.mRetainedImplicitlyQueryable;
        this.mRetainedImplicitlyQueryableSnapshot =
                new SnapshotCache.Auto(
                        watchedSparseSetArray2,
                        watchedSparseSetArray2,
                        "AppsFilter.mRetainedImplicitlyQueryable",
                        0);
        this.mQueriesViaPackage = new WatchedSparseSetArray();
        WatchedSparseSetArray watchedSparseSetArray3 = this.mQueriesViaPackage;
        this.mQueriesViaPackageSnapshot =
                new SnapshotCache.Auto(
                        watchedSparseSetArray3,
                        watchedSparseSetArray3,
                        "AppsFilter.mQueriesViaPackage",
                        0);
        this.mQueriesViaComponent = new WatchedSparseSetArray();
        WatchedSparseSetArray watchedSparseSetArray4 = this.mQueriesViaComponent;
        this.mQueriesViaComponentSnapshot =
                new SnapshotCache.Auto(
                        watchedSparseSetArray4,
                        watchedSparseSetArray4,
                        "AppsFilter.mQueriesViaComponent",
                        0);
        this.mQueryableViaUsesLibrary = new WatchedSparseSetArray();
        WatchedSparseSetArray watchedSparseSetArray5 = this.mQueryableViaUsesLibrary;
        this.mQueryableViaUsesLibrarySnapshot =
                new SnapshotCache.Auto(
                        watchedSparseSetArray5,
                        watchedSparseSetArray5,
                        "AppsFilter.mQueryableViaUsesLibrary",
                        0);
        this.mQueryableViaUsesPermission = new WatchedSparseSetArray();
        WatchedSparseSetArray watchedSparseSetArray6 = this.mQueryableViaUsesPermission;
        this.mQueryableViaUsesPermissionSnapshot =
                new SnapshotCache.Auto(
                        watchedSparseSetArray6,
                        watchedSparseSetArray6,
                        "AppsFilter.mQueryableViaUsesPermission",
                        0);
        this.mForceQueryable = new WatchedArraySet();
        WatchedArraySet watchedArraySet = this.mForceQueryable;
        this.mForceQueryableSnapshot =
                new SnapshotCache.Auto(
                        watchedArraySet, watchedArraySet, "AppsFilter.mForceQueryable", 0);
        this.mProtectedBroadcasts = new WatchedArraySet();
        WatchedArraySet watchedArraySet2 = this.mProtectedBroadcasts;
        this.mProtectedBroadcastsSnapshot =
                new SnapshotCache.Auto(
                        watchedArraySet2, watchedArraySet2, "AppsFilter.mProtectedBroadcasts", 0);
        this.mPermissionToUids = new ArrayMap();
        this.mUsesPermissionToUids = new ArrayMap();
        this.mSnapshot =
                new SnapshotCache(
                        this, this) { // from class: com.android.server.pm.AppsFilterImpl.1
                    @Override // com.android.server.utils.SnapshotCache
                    public final Object createSnapshot() {
                        AppsFilterImpl appsFilterImpl = AppsFilterImpl.this;
                        AppsFilterSnapshotImpl appsFilterSnapshotImpl =
                                new AppsFilterSnapshotImpl();
                        PackageManagerTracedLock packageManagerTracedLock =
                                appsFilterImpl.mImplicitlyQueryableLock;
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        synchronized (packageManagerTracedLock) {
                            try {
                                appsFilterSnapshotImpl.mImplicitlyQueryable =
                                        (WatchedSparseSetArray)
                                                appsFilterImpl.mImplicitQueryableSnapshot
                                                        .snapshot();
                                appsFilterSnapshotImpl.mRetainedImplicitlyQueryable =
                                        (WatchedSparseSetArray)
                                                appsFilterImpl.mRetainedImplicitlyQueryableSnapshot
                                                        .snapshot();
                            } finally {
                                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                            }
                        }
                        appsFilterSnapshotImpl.mImplicitQueryableSnapshot =
                                new SnapshotCache.Auto();
                        appsFilterSnapshotImpl.mRetainedImplicitlyQueryableSnapshot =
                                new SnapshotCache.Auto();
                        synchronized (appsFilterImpl.mQueriesViaPackageLock) {
                            try {
                                appsFilterSnapshotImpl.mQueriesViaPackage =
                                        (WatchedSparseSetArray)
                                                appsFilterImpl.mQueriesViaPackageSnapshot
                                                        .snapshot();
                            } finally {
                                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                            }
                        }
                        appsFilterSnapshotImpl.mQueriesViaPackageSnapshot =
                                new SnapshotCache.Auto();
                        synchronized (appsFilterImpl.mQueriesViaComponentLock) {
                            try {
                                appsFilterSnapshotImpl.mQueriesViaComponent =
                                        (WatchedSparseSetArray)
                                                appsFilterImpl.mQueriesViaComponentSnapshot
                                                        .snapshot();
                            } finally {
                                boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                            }
                        }
                        appsFilterSnapshotImpl.mQueriesViaComponentSnapshot =
                                new SnapshotCache.Auto();
                        synchronized (appsFilterImpl.mQueryableViaUsesLibraryLock) {
                            try {
                                appsFilterSnapshotImpl.mQueryableViaUsesLibrary =
                                        (WatchedSparseSetArray)
                                                appsFilterImpl.mQueryableViaUsesLibrarySnapshot
                                                        .snapshot();
                            } finally {
                                boolean z6 = PackageManagerService.DEBUG_COMPRESSION;
                            }
                        }
                        appsFilterSnapshotImpl.mQueryableViaUsesLibrarySnapshot =
                                new SnapshotCache.Auto();
                        synchronized (appsFilterImpl.mQueryableViaUsesPermissionLock) {
                            try {
                                appsFilterSnapshotImpl.mQueryableViaUsesPermission =
                                        (WatchedSparseSetArray)
                                                appsFilterImpl.mQueryableViaUsesPermissionSnapshot
                                                        .snapshot();
                            } finally {
                                boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
                            }
                        }
                        appsFilterSnapshotImpl.mQueryableViaUsesPermissionSnapshot =
                                new SnapshotCache.Auto();
                        synchronized (appsFilterImpl.mForceQueryableLock) {
                            try {
                                appsFilterSnapshotImpl.mForceQueryable =
                                        (WatchedArraySet)
                                                appsFilterImpl.mForceQueryableSnapshot.snapshot();
                            } finally {
                                boolean z8 = PackageManagerService.DEBUG_COMPRESSION;
                            }
                        }
                        appsFilterSnapshotImpl.mForceQueryableSnapshot = new SnapshotCache.Auto();
                        synchronized (appsFilterImpl.mProtectedBroadcastsLock) {
                            try {
                                appsFilterSnapshotImpl.mProtectedBroadcasts =
                                        (WatchedArraySet)
                                                appsFilterImpl.mProtectedBroadcastsSnapshot
                                                        .snapshot();
                            } finally {
                            }
                        }
                        appsFilterSnapshotImpl.mProtectedBroadcastsSnapshot =
                                new SnapshotCache.Auto();
                        appsFilterSnapshotImpl.mQueriesViaComponentRequireRecompute =
                                appsFilterImpl.mQueriesViaComponentRequireRecompute;
                        String[] strArr2 = appsFilterImpl.mForceQueryableByDevicePackageNames;
                        appsFilterSnapshotImpl.mForceQueryableByDevicePackageNames =
                                (String[]) Arrays.copyOf(strArr2, strArr2.length);
                        appsFilterSnapshotImpl.mSystemAppsQueryable =
                                appsFilterImpl.mSystemAppsQueryable;
                        appsFilterSnapshotImpl.mFeatureConfig =
                                appsFilterImpl.mFeatureConfig.snapshot();
                        appsFilterSnapshotImpl.mOverlayReferenceMapper =
                                appsFilterImpl.mOverlayReferenceMapper;
                        appsFilterSnapshotImpl.mSystemSigningDetails =
                                appsFilterImpl.mSystemSigningDetails;
                        appsFilterSnapshotImpl.mCacheReady = appsFilterImpl.mCacheReady;
                        if (appsFilterSnapshotImpl.mCacheReady) {
                            synchronized (appsFilterImpl.mCacheLock) {
                                try {
                                    appsFilterSnapshotImpl.mShouldFilterCache =
                                            (WatchedSparseBooleanMatrix)
                                                    appsFilterImpl.mShouldFilterCacheSnapshot
                                                            .snapshot();
                                } finally {
                                }
                            }
                        } else {
                            appsFilterSnapshotImpl.mShouldFilterCache =
                                    new WatchedSparseBooleanMatrix();
                        }
                        appsFilterSnapshotImpl.mCacheEnabled = appsFilterImpl.mCacheEnabled;
                        appsFilterSnapshotImpl.mShouldFilterCacheSnapshot =
                                new SnapshotCache.Auto();
                        appsFilterSnapshotImpl.mHandler = null;
                        return appsFilterSnapshotImpl;
                    }
                };
        this.mCacheEnabled = SystemProperties.getBoolean("debug.pm.use_app_filter_cache", true);
        SystemProperties.addChangeCallback(
                new Runnable() { // from class:
                                 // com.android.server.pm.AppsFilterImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppsFilterImpl appsFilterImpl = AppsFilterImpl.this;
                        appsFilterImpl.getClass();
                        appsFilterImpl.mCacheEnabled =
                                SystemProperties.getBoolean("debug.pm.use_app_filter_cache", true);
                    }
                });
    }

    public static boolean pkgInstruments(
            AndroidPackage androidPackage, AndroidPackage androidPackage2) {
        String packageName = androidPackage2.getPackageName();
        List instrumentations = androidPackage.getInstrumentations();
        for (int size = ArrayUtils.size(instrumentations) - 1; size >= 0; size--) {
            if (Objects.equals(
                    ((ParsedInstrumentation) instrumentations.get(size)).getTargetPackage(),
                    packageName)) {
                return true;
            }
        }
        return false;
    }

    public final void addPackage(
            Computer computer, PackageStateInternal packageStateInternal, boolean z, boolean z2) {
        int i;
        long currentTimeMicro = SystemClock.currentTimeMicro();
        int i2 = z ? 3 : 1;
        if (z) {
            try {
                removePackageInternal(computer, packageStateInternal, true, z2);
            } finally {
                dispatchChange(this);
            }
        }
        ArrayMap packageStates = computer.getPackageStates();
        UserInfo[] userInfos = computer.getUserInfos();
        ArraySet addPackageInternal = addPackageInternal(packageStates, packageStateInternal);
        if (this.mCacheReady) {
            PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
            try {
                synchronized (packageManagerTracedLock) {
                    try {
                        updateShouldFilterCacheForPackage(
                                computer,
                                null,
                                packageStateInternal,
                                packageStates,
                                userInfos,
                                -1,
                                packageStates.size());
                        if (addPackageInternal != null) {
                            for (int i3 = 0; i3 < addPackageInternal.size(); i3 = i + 1) {
                                PackageStateInternal packageStateInternal2 =
                                        (PackageStateInternal)
                                                packageStates.get(
                                                        (String) addPackageInternal.valueAt(i3));
                                if (packageStateInternal2 == null) {
                                    i = i3;
                                } else {
                                    i = i3;
                                    updateShouldFilterCacheForPackage(
                                            computer,
                                            null,
                                            packageStateInternal2,
                                            packageStates,
                                            userInfos,
                                            -1,
                                            packageStates.size());
                                }
                            }
                        }
                        boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                        long currentTimeMicro2 = SystemClock.currentTimeMicro() - currentTimeMicro;
                        int length = userInfos.length;
                        int size = packageStates.size();
                        int appId = packageStateInternal.getAppId();
                        if (this.mCacheReady) {
                            FrameworkStatsLog.write(
                                    FrameworkStatsLog
                                            .PACKAGE_MANAGER_APPS_FILTER_CACHE_UPDATE_REPORTED,
                                    i2,
                                    appId,
                                    currentTimeMicro2,
                                    length,
                                    size,
                                    this.mShouldFilterCache.mSize);
                        }
                    } catch (Throwable th) {
                        th = th;
                        boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } else {
            invalidateCache("addPackage: " + packageStateInternal.getPackageName());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:197:0x02a5, code lost:

       if (r1.getPackageName().equals(r5.getInstallSource().mUpdateOwnerPackageName) != false) goto L152;
    */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x0356, code lost:

       if (r6.getPackageName().equals(r14.getInstallSource().mUpdateOwnerPackageName) != false) goto L198;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.util.ArraySet addPackageInternal(
            android.util.ArrayMap r13, com.android.server.pm.pkg.PackageStateInternal r14) {
        /*
            Method dump skipped, instructions count: 1065
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.AppsFilterImpl.addPackageInternal(android.util.ArrayMap,"
                    + " com.android.server.pm.pkg.PackageStateInternal):android.util.ArraySet");
    }

    public final void collectProtectedBroadcasts(ArrayMap arrayMap, String str) {
        PackageManagerTracedLock packageManagerTracedLock = this.mProtectedBroadcastsLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                this.mProtectedBroadcasts.clear();
                for (int size = arrayMap.size() - 1; size >= 0; size--) {
                    PackageStateInternal packageStateInternal =
                            (PackageStateInternal) arrayMap.valueAt(size);
                    if (packageStateInternal.getPkg() != null
                            && !packageStateInternal.getPkg().getPackageName().equals(str)) {
                        List protectedBroadcasts =
                                packageStateInternal.getPkg().getProtectedBroadcasts();
                        if (!protectedBroadcasts.isEmpty()) {
                            WatchedArraySet watchedArraySet = this.mProtectedBroadcasts;
                            watchedArraySet.mStorage.addAll(protectedBroadcasts);
                            watchedArraySet.dispatchChange(watchedArraySet);
                        }
                    }
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }

    @Override // com.android.server.utils.Watchable
    public final void dispatchChange(Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final void dumpForceQueryable(
            PrintWriter printWriter,
            Integer num,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        PackageManagerTracedLock packageManagerTracedLock = this.mForceQueryableLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpForceQueryable(
                        printWriter, num, appsFilterBase$$ExternalSyntheticLambda0);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final void dumpQueriesViaComponent(
            PrintWriter printWriter,
            Integer num,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        PackageManagerTracedLock packageManagerTracedLock = this.mQueriesViaComponentLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpQueriesViaComponent(
                        printWriter, num, appsFilterBase$$ExternalSyntheticLambda0);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final void dumpQueriesViaImplicitlyQueryable(
            PrintWriter printWriter,
            Integer num,
            int[] iArr,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        PackageManagerTracedLock packageManagerTracedLock = this.mImplicitlyQueryableLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpQueriesViaImplicitlyQueryable(
                        printWriter, num, iArr, appsFilterBase$$ExternalSyntheticLambda0);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final void dumpQueriesViaPackage(
            PrintWriter printWriter,
            Integer num,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        PackageManagerTracedLock packageManagerTracedLock = this.mQueriesViaPackageLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpQueriesViaPackage(
                        printWriter, num, appsFilterBase$$ExternalSyntheticLambda0);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final void dumpQueriesViaUsesLibrary(
            PrintWriter printWriter,
            Integer num,
            AppsFilterBase$$ExternalSyntheticLambda0 appsFilterBase$$ExternalSyntheticLambda0) {
        PackageManagerTracedLock packageManagerTracedLock = this.mQueryableViaUsesLibraryLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpQueriesViaUsesLibrary(
                        printWriter, num, appsFilterBase$$ExternalSyntheticLambda0);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    public final void invalidateCache(String str) {
        if (this.mCacheValid.compareAndSet(true, false)) {
            Slog.i("AppsFilter", "Invalidating cache: " + str);
        }
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final boolean isForceQueryable(int i) {
        boolean isForceQueryable;
        PackageManagerTracedLock packageManagerTracedLock = this.mForceQueryableLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                isForceQueryable = super.isForceQueryable(i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return isForceQueryable;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final boolean isImplicitlyQueryable(int i, int i2) {
        boolean isImplicitlyQueryable;
        PackageManagerTracedLock packageManagerTracedLock = this.mImplicitlyQueryableLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                isImplicitlyQueryable = super.isImplicitlyQueryable(i, i2);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return isImplicitlyQueryable;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final boolean isQueryableViaComponent(int i, int i2) {
        boolean isQueryableViaComponent;
        PackageManagerTracedLock packageManagerTracedLock = this.mQueriesViaComponentLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                isQueryableViaComponent = super.isQueryableViaComponent(i, i2);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return isQueryableViaComponent;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final boolean isQueryableViaComponentWhenRequireRecompute(
            ArrayMap arrayMap,
            PackageStateInternal packageStateInternal,
            ArraySet arraySet,
            AndroidPackage androidPackage,
            int i,
            int i2) {
        WatchedArraySet watchedArraySet;
        ArraySet arraySet2;
        PackageManagerTracedLock packageManagerTracedLock = this.mProtectedBroadcastsLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                watchedArraySet = new WatchedArraySet(this.mProtectedBroadcasts);
            } finally {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            }
        }
        synchronized (this.mForceQueryableLock) {
            try {
                arraySet2 = new ArraySet(this.mForceQueryable.mStorage);
            } finally {
            }
        }
        final AppsFilterUtils.ParallelComputeComponentVisibility
                parallelComputeComponentVisibility =
                        new AppsFilterUtils.ParallelComputeComponentVisibility(
                                arrayMap, arraySet2, watchedArraySet);
        long currentTimeMillis = System.currentTimeMillis();
        SparseSetArray sparseSetArray = new SparseSetArray();
        ExecutorService newFixedThreadPool =
                ConcurrentUtils.newFixedThreadPool(
                        4,
                        AppsFilterUtils.ParallelComputeComponentVisibility.class.getSimpleName(),
                        0);
        try {
            ArrayList arrayList = new ArrayList();
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                final PackageStateInternal packageStateInternal2 =
                        (PackageStateInternal)
                                parallelComputeComponentVisibility.mExistingSettings.valueAt(size);
                AndroidPackageInternal pkg = packageStateInternal2.getPkg();
                if (pkg != null
                        && !pkg.getRequestedPermissions()
                                .contains("android.permission.QUERY_ALL_PACKAGES")
                        && (!pkg.getQueriesIntents().isEmpty()
                                || !pkg.getQueriesProviders().isEmpty())) {
                    arrayList.add(
                            new Pair(
                                    packageStateInternal2,
                                    newFixedThreadPool.submit(
                                            new Callable() { // from class:
                                                             // com.android.server.pm.AppsFilterUtils$ParallelComputeComponentVisibility$$ExternalSyntheticLambda0
                                                @Override // java.util.concurrent.Callable
                                                public final Object call() {
                                                    AppsFilterUtils
                                                                    .ParallelComputeComponentVisibility
                                                            parallelComputeComponentVisibility2 =
                                                                    AppsFilterUtils
                                                                            .ParallelComputeComponentVisibility
                                                                            .this;
                                                    parallelComputeComponentVisibility2.getClass();
                                                    ArraySet arraySet3 = new ArraySet();
                                                    for (int size2 =
                                                                    parallelComputeComponentVisibility2
                                                                                    .mExistingSettings
                                                                                    .size()
                                                                            - 1;
                                                            size2 >= 0;
                                                            size2--) {
                                                        PackageStateInternal packageStateInternal3 =
                                                                (PackageStateInternal)
                                                                        parallelComputeComponentVisibility2
                                                                                .mExistingSettings
                                                                                .valueAt(size2);
                                                        PackageStateInternal packageStateInternal4 =
                                                                packageStateInternal2;
                                                        if (packageStateInternal4.getAppId()
                                                                        != packageStateInternal3
                                                                                .getAppId()
                                                                && packageStateInternal3.getPkg()
                                                                        != null
                                                                && !parallelComputeComponentVisibility2
                                                                        .mForceQueryable.contains(
                                                                        Integer.valueOf(
                                                                                packageStateInternal3
                                                                                        .getAppId()))
                                                                && AppsFilterUtils
                                                                        .canQueryViaComponents(
                                                                                packageStateInternal4
                                                                                        .getPkg(),
                                                                                packageStateInternal3
                                                                                        .getPkg(),
                                                                                parallelComputeComponentVisibility2
                                                                                        .mProtectedBroadcasts)) {
                                                            arraySet3.add(
                                                                    Integer.valueOf(
                                                                            packageStateInternal3
                                                                                    .getAppId()));
                                                        }
                                                    }
                                                    return arraySet3;
                                                }
                                            })));
                }
            }
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                int appId = ((PackageState) ((Pair) arrayList.get(i3)).first).getAppId();
                try {
                    ArraySet arraySet3 =
                            (ArraySet) ((Future) ((Pair) arrayList.get(i3)).second).get();
                    if (arraySet3.size() != 0) {
                        sparseSetArray.addAll(appId, arraySet3);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    throw new IllegalStateException(e);
                }
            }
            newFixedThreadPool.shutdownNow();
            Slog.i(
                    "AppsFilter",
                    "Recomputing visibility takes "
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms");
            PackageManagerTracedLock packageManagerTracedLock2 = this.mQueriesViaComponentLock;
            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock2) {
                try {
                    this.mQueriesViaComponent = new WatchedSparseSetArray(sparseSetArray);
                    WatchedSparseSetArray watchedSparseSetArray = this.mQueriesViaComponent;
                    this.mQueriesViaComponentSnapshot =
                            new SnapshotCache.Auto(
                                    watchedSparseSetArray,
                                    watchedSparseSetArray,
                                    "AppsFilter.mQueriesViaComponent",
                                    0);
                } finally {
                }
            }
            this.mQueriesViaComponentRequireRecompute.set(false);
            dispatchChange(this);
            return isQueryableViaComponent(i, i2);
        } catch (Throwable th) {
            newFixedThreadPool.shutdownNow();
            throw th;
        }
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final boolean isQueryableViaPackage(int i, int i2) {
        boolean isQueryableViaPackage;
        PackageManagerTracedLock packageManagerTracedLock = this.mQueriesViaPackageLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                isQueryableViaPackage = super.isQueryableViaPackage(i, i2);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return isQueryableViaPackage;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final boolean isQueryableViaUsesLibrary(int i, int i2) {
        boolean isQueryableViaUsesLibrary;
        PackageManagerTracedLock packageManagerTracedLock = this.mQueryableViaUsesLibraryLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                isQueryableViaUsesLibrary = super.isQueryableViaUsesLibrary(i, i2);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return isQueryableViaUsesLibrary;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final boolean isQueryableViaUsesPermission(int i, int i2) {
        boolean isQueryableViaUsesPermission;
        PackageManagerTracedLock packageManagerTracedLock = this.mQueryableViaUsesPermissionLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                isQueryableViaUsesPermission = super.isQueryableViaUsesPermission(i, i2);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return isQueryableViaUsesPermission;
    }

    @Override // com.android.server.utils.Watchable
    public final boolean isRegisteredObserver(Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final boolean isRetainedImplicitlyQueryable(int i, int i2) {
        boolean isRetainedImplicitlyQueryable;
        PackageManagerTracedLock packageManagerTracedLock = this.mImplicitlyQueryableLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                isRetainedImplicitlyQueryable = super.isRetainedImplicitlyQueryable(i, i2);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return isRetainedImplicitlyQueryable;
    }

    public final void onUserCreated(Computer computer, int i) {
        if (this.mCacheReady) {
            long currentTimeMicro = SystemClock.currentTimeMicro();
            ArrayMap packageStates = computer.getPackageStates();
            UserInfo[] userInfos = computer.getUserInfos();
            int i2 = 0;
            while (true) {
                if (i2 >= userInfos.length) {
                    i = -10000;
                    break;
                } else if (i == userInfos[i2].id) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i == -10000) {
                Slog.e(
                        "AppsFilter",
                        "We encountered a new user that isn't a member of known users, updating the"
                            + " whole cache");
                i = -1;
            }
            updateEntireShouldFilterCacheInner(computer, packageStates, userInfos, i);
            dispatchChange(this);
            FrameworkStatsLog.write(
                    FrameworkStatsLog.PACKAGE_MANAGER_APPS_FILTER_CACHE_BUILD_REPORTED,
                    2,
                    SystemClock.currentTimeMicro() - currentTimeMicro,
                    computer.getUserInfos().length,
                    computer.getPackageStates().size(),
                    this.mShouldFilterCache.mSize);
        }
    }

    public final void onUserDeleted(Computer computer, int i) {
        if (this.mCacheReady) {
            long currentTimeMicro = SystemClock.currentTimeMicro();
            PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    WatchedSparseBooleanMatrix watchedSparseBooleanMatrix = this.mShouldFilterCache;
                    int[] copyOf =
                            Arrays.copyOf(
                                    watchedSparseBooleanMatrix.mKeys,
                                    watchedSparseBooleanMatrix.mSize);
                    int length = copyOf.length;
                    int binarySearch = Arrays.binarySearch(copyOf, UserHandle.getUid(i, 0));
                    if (binarySearch < 0) {
                        binarySearch = ~binarySearch;
                    }
                    if (binarySearch < length && UserHandle.getUserId(copyOf[binarySearch]) == i) {
                        int binarySearch2 =
                                Arrays.binarySearch(copyOf, UserHandle.getUid(i + 1, 0) - 1);
                        int i2 = binarySearch2 >= 0 ? binarySearch2 + 1 : ~binarySearch2;
                        if (binarySearch < i2 && UserHandle.getUserId(copyOf[i2 - 1]) == i) {
                            this.mShouldFilterCache.removeRange(binarySearch, i2);
                            this.mShouldFilterCache.compact();
                        }
                        Slog.w(
                                "AppsFilter",
                                "Failed to remove should filter cache for user "
                                        + i
                                        + ", fromIndex="
                                        + binarySearch
                                        + ", toIndex="
                                        + i2);
                    }
                    Slog.w(
                            "AppsFilter",
                            "Failed to remove should filter cache for user "
                                    + i
                                    + ", fromIndex="
                                    + binarySearch);
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            dispatchChange(this);
            FrameworkStatsLog.write(
                    FrameworkStatsLog.PACKAGE_MANAGER_APPS_FILTER_CACHE_BUILD_REPORTED,
                    3,
                    SystemClock.currentTimeMicro() - currentTimeMicro,
                    computer.getUserInfos().length,
                    computer.getPackageStates().size(),
                    this.mShouldFilterCache.mSize);
        }
    }

    @Override // com.android.server.utils.Watchable
    public final void registerObserver(Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    public final void removeAppIdFromVisibilityCache(int i) {
        PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            int i2 = 0;
            while (true) {
                try {
                    WatchedSparseBooleanMatrix watchedSparseBooleanMatrix = this.mShouldFilterCache;
                    if (i2 < watchedSparseBooleanMatrix.mSize) {
                        watchedSparseBooleanMatrix.validateIndex(i2);
                        if (UserHandle.getAppId(watchedSparseBooleanMatrix.mKeys[i2]) == i) {
                            this.mShouldFilterCache.removeAt(i2);
                            i2--;
                        }
                        i2++;
                    }
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }

    /* JADX WARN: Finally extract failed */
    public final void removePackageInternal(
            Computer computer, PackageStateInternal packageStateInternal, boolean z, boolean z2) {
        boolean z3;
        ArrayMap packageStates = computer.getPackageStates();
        UserInfo[] userInfos = computer.getUserInfos();
        if (!z || !z2) {
            PackageManagerTracedLock packageManagerTracedLock = this.mImplicitlyQueryableLock;
            boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                for (UserInfo userInfo : userInfos) {
                    try {
                        int uid = UserHandle.getUid(userInfo.id, packageStateInternal.getAppId());
                        this.mImplicitlyQueryable.remove(uid);
                        for (int size = this.mImplicitlyQueryable.mStorage.size() - 1;
                                size >= 0;
                                size--) {
                            WatchedSparseSetArray watchedSparseSetArray = this.mImplicitlyQueryable;
                            watchedSparseSetArray.remove(
                                    watchedSparseSetArray.mStorage.keyAt(size),
                                    Integer.valueOf(uid));
                        }
                        if (!z) {
                            this.mRetainedImplicitlyQueryable.remove(uid);
                            for (int size2 = this.mRetainedImplicitlyQueryable.mStorage.size() - 1;
                                    size2 >= 0;
                                    size2--) {
                                WatchedSparseSetArray watchedSparseSetArray2 =
                                        this.mRetainedImplicitlyQueryable;
                                watchedSparseSetArray2.remove(
                                        watchedSparseSetArray2.mStorage.keyAt(size2),
                                        Integer.valueOf(uid));
                            }
                        }
                    } finally {
                        boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                    }
                }
            }
            boolean z6 = PackageManagerService.DEBUG_COMPRESSION;
        }
        if (!this.mQueriesViaComponentRequireRecompute.get()) {
            PackageManagerTracedLock packageManagerTracedLock2 = this.mQueriesViaComponentLock;
            boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock2) {
                try {
                    this.mQueriesViaComponent.remove(packageStateInternal.getAppId());
                    for (int size3 = this.mQueriesViaComponent.mStorage.size() - 1;
                            size3 >= 0;
                            size3--) {
                        WatchedSparseSetArray watchedSparseSetArray3 = this.mQueriesViaComponent;
                        watchedSparseSetArray3.remove(
                                watchedSparseSetArray3.mStorage.keyAt(size3),
                                Integer.valueOf(packageStateInternal.getAppId()));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            boolean z8 = PackageManagerService.DEBUG_COMPRESSION;
        }
        PackageManagerTracedLock packageManagerTracedLock3 = this.mQueriesViaPackageLock;
        boolean z9 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock3) {
            try {
                this.mQueriesViaPackage.remove(packageStateInternal.getAppId());
                for (int size4 = this.mQueriesViaPackage.mStorage.size() - 1; size4 >= 0; size4--) {
                    WatchedSparseSetArray watchedSparseSetArray4 = this.mQueriesViaPackage;
                    watchedSparseSetArray4.remove(
                            watchedSparseSetArray4.mStorage.keyAt(size4),
                            Integer.valueOf(packageStateInternal.getAppId()));
                }
            } finally {
                boolean z10 = PackageManagerService.DEBUG_COMPRESSION;
            }
        }
        boolean z11 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (this.mQueryableViaUsesLibraryLock) {
            try {
                this.mQueryableViaUsesLibrary.remove(packageStateInternal.getAppId());
                for (int size5 = this.mQueryableViaUsesLibrary.mStorage.size() - 1;
                        size5 >= 0;
                        size5--) {
                    WatchedSparseSetArray watchedSparseSetArray5 = this.mQueryableViaUsesLibrary;
                    watchedSparseSetArray5.remove(
                            watchedSparseSetArray5.mStorage.keyAt(size5),
                            Integer.valueOf(packageStateInternal.getAppId()));
                }
            } finally {
                boolean z12 = PackageManagerService.DEBUG_COMPRESSION;
            }
        }
        boolean z13 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (this.mQueryableViaUsesPermissionLock) {
            try {
                if (packageStateInternal.getPkg() != null
                        && !packageStateInternal.getPkg().getPermissions().isEmpty()) {
                    Iterator it = packageStateInternal.getPkg().getPermissions().iterator();
                    while (it.hasNext()) {
                        String name = ((ParsedPermission) it.next()).getName();
                        if (this.mPermissionToUids.containsKey(name)) {
                            ((ArraySet) this.mPermissionToUids.get(name))
                                    .remove(Integer.valueOf(packageStateInternal.getAppId()));
                            if (((ArraySet) this.mPermissionToUids.get(name)).isEmpty()) {
                                this.mPermissionToUids.remove(name);
                            }
                        }
                    }
                }
                if (packageStateInternal.getPkg() != null
                        && !packageStateInternal.getPkg().getUsesPermissions().isEmpty()) {
                    Iterator it2 = packageStateInternal.getPkg().getUsesPermissions().iterator();
                    while (it2.hasNext()) {
                        String name2 = ((ParsedUsesPermission) it2.next()).getName();
                        if (this.mUsesPermissionToUids.containsKey(name2)) {
                            ((ArraySet) this.mUsesPermissionToUids.get(name2))
                                    .remove(Integer.valueOf(packageStateInternal.getAppId()));
                            if (((ArraySet) this.mUsesPermissionToUids.get(name2)).isEmpty()) {
                                this.mUsesPermissionToUids.remove(name2);
                            }
                        }
                    }
                }
                this.mQueryableViaUsesPermission.remove(packageStateInternal.getAppId());
            } finally {
                boolean z14 = PackageManagerService.DEBUG_COMPRESSION;
            }
        }
        boolean z15 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (this.mForceQueryableLock) {
            try {
                this.mForceQueryable.remove(Integer.valueOf(packageStateInternal.getAppId()));
            } finally {
                boolean z16 = PackageManagerService.DEBUG_COMPRESSION;
            }
        }
        synchronized (this.mProtectedBroadcastsLock) {
            try {
                if (packageStateInternal.getPkg() != null
                        && !packageStateInternal.getPkg().getProtectedBroadcasts().isEmpty()) {
                    String packageName = packageStateInternal.getPkg().getPackageName();
                    ArrayList arrayList = new ArrayList(this.mProtectedBroadcasts.mStorage);
                    collectProtectedBroadcasts(packageStates, packageName);
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (!this.mProtectedBroadcasts.mStorage.contains(arrayList.get(i))) {
                            z3 = true;
                            break;
                        }
                    }
                }
                z3 = false;
            } finally {
                boolean z17 = PackageManagerService.DEBUG_COMPRESSION;
            }
        }
        boolean z18 = PackageManagerService.DEBUG_COMPRESSION;
        if (z3) {
            Slog.i(
                    "AppsFilter",
                    "Protected-Broadcast is changed by Removing "
                            + packageStateInternal.getPackageName());
            this.mQueriesViaComponentRequireRecompute.set(true);
        }
        ArraySet removePkg =
                this.mOverlayReferenceMapper.removePkg(packageStateInternal.getPackageName());
        this.mFeatureConfig.updatePackageState(packageStateInternal, true);
        SharedUserSetting sharedUser =
                packageStateInternal.hasSharedUser()
                        ? computer.getSharedUser(packageStateInternal.getSharedUserAppId())
                        : null;
        if (sharedUser != null) {
            ArraySet arraySet = sharedUser.mPackages.mStorage;
            for (int size6 = arraySet.size() - 1; size6 >= 0; size6--) {
                if (arraySet.valueAt(size6) != packageStateInternal) {
                    addPackageInternal(
                            packageStates, (PackageStateInternal) arraySet.valueAt(size6));
                }
            }
        }
        if (this.mCacheReady) {
            removeAppIdFromVisibilityCache(packageStateInternal.getAppId());
            if (sharedUser != null) {
                ArraySet arraySet2 = sharedUser.mPackages.mStorage;
                int size7 = arraySet2.size() - 1;
                while (true) {
                    if (size7 < 0) {
                        break;
                    }
                    PackageStateInternal packageStateInternal2 =
                            (PackageStateInternal) arraySet2.valueAt(size7);
                    if (packageStateInternal2 != packageStateInternal) {
                        PackageManagerTracedLock packageManagerTracedLock4 = this.mCacheLock;
                        boolean z19 = PackageManagerService.DEBUG_COMPRESSION;
                        synchronized (packageManagerTracedLock4) {
                            try {
                                updateShouldFilterCacheForPackage(
                                        computer,
                                        packageStateInternal.getPackageName(),
                                        packageStateInternal2,
                                        packageStates,
                                        userInfos,
                                        -1,
                                        packageStates.size());
                            } finally {
                                boolean z20 = PackageManagerService.DEBUG_COMPRESSION;
                            }
                        }
                        break;
                    }
                    size7--;
                }
            }
            for (int i2 = 0; i2 < removePkg.size(); i2++) {
                PackageStateInternal packageStateInternal3 =
                        (PackageStateInternal) packageStates.get((String) removePkg.valueAt(i2));
                if (packageStateInternal3 != null) {
                    PackageManagerTracedLock packageManagerTracedLock5 = this.mCacheLock;
                    boolean z21 = PackageManagerService.DEBUG_COMPRESSION;
                    synchronized (packageManagerTracedLock5) {
                        try {
                            updateShouldFilterCacheForPackage(
                                    computer,
                                    null,
                                    packageStateInternal3,
                                    packageStates,
                                    userInfos,
                                    -1,
                                    packageStates.size());
                        } finally {
                            boolean z22 = PackageManagerService.DEBUG_COMPRESSION;
                        }
                    }
                }
            }
        } else {
            invalidateCache("removePackage: " + packageStateInternal.getPackageName());
        }
        dispatchChange(this);
    }

    @Override // com.android.server.pm.AppsFilterBase
    public final boolean shouldFilterApplicationUsingCache(int i, int i2, int i3) {
        boolean shouldFilterApplicationUsingCache;
        PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                shouldFilterApplicationUsingCache =
                        super.shouldFilterApplicationUsingCache(i, i2, i3);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return shouldFilterApplicationUsingCache;
    }

    @Override // com.android.server.utils.Snappable
    public final AppsFilterBase snapshot() {
        return (AppsFilterBase) snapshot();
    }

    @Override // com.android.server.utils.Watchable
    public final void unregisterObserver(Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }

    public final void updateEntireShouldFilterCacheInner(
            Computer computer, ArrayMap arrayMap, UserInfo[] userInfoArr, int i) {
        PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            if (i == -1) {
                try {
                    WatchedSparseBooleanMatrix watchedSparseBooleanMatrix = this.mShouldFilterCache;
                    watchedSparseBooleanMatrix.mSize = 0;
                    Arrays.fill(watchedSparseBooleanMatrix.mInUse, false);
                    watchedSparseBooleanMatrix.dispatchChange(watchedSparseBooleanMatrix);
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            WatchedSparseBooleanMatrix watchedSparseBooleanMatrix2 = this.mShouldFilterCache;
            int length = userInfoArr.length * arrayMap.size();
            if (length > watchedSparseBooleanMatrix2.mOrder) {
                if (length % 64 != 0) {
                    length = ((length / 64) + 1) * 64;
                }
                watchedSparseBooleanMatrix2.resizeMatrix(length);
            }
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                updateShouldFilterCacheForPackage(
                        computer,
                        null,
                        (PackageStateInternal) arrayMap.valueAt(size),
                        arrayMap,
                        userInfoArr,
                        i,
                        size);
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }

    public final void updateShouldFilterCacheForImplicitAccess(
            WatchedSparseSetArray watchedSparseSetArray) {
        PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            for (int i = 0; i < watchedSparseSetArray.mStorage.size(); i++) {
                try {
                    int keyAt = watchedSparseSetArray.mStorage.keyAt(i);
                    Iterator it = watchedSparseSetArray.mStorage.get(keyAt).iterator();
                    while (it.hasNext()) {
                        this.mShouldFilterCache.put(keyAt, ((Integer) it.next()).intValue(), false);
                    }
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }

    public final void updateShouldFilterCacheForPackage(
            Computer computer,
            String str,
            PackageStateInternal packageStateInternal,
            ArrayMap arrayMap,
            UserInfo[] userInfoArr,
            int i,
            int i2) {
        for (int min = Math.min(i2, arrayMap.size() - 1); min >= 0; min--) {
            PackageStateInternal packageStateInternal2 =
                    (PackageStateInternal) arrayMap.valueAt(min);
            if (packageStateInternal.getAppId() != packageStateInternal2.getAppId()
                    && packageStateInternal.getPackageName() != str
                    && packageStateInternal2.getPackageName() != str) {
                if (i == -1) {
                    for (UserInfo userInfo : userInfoArr) {
                        updateShouldFilterCacheForUser(
                                computer,
                                packageStateInternal,
                                userInfoArr,
                                packageStateInternal2,
                                userInfo.id);
                    }
                } else {
                    updateShouldFilterCacheForUser(
                            computer, packageStateInternal, userInfoArr, packageStateInternal2, i);
                }
            }
        }
    }

    public final void updateShouldFilterCacheForUser(
            Computer computer,
            PackageStateInternal packageStateInternal,
            UserInfo[] userInfoArr,
            PackageStateInternal packageStateInternal2,
            int i) {
        for (UserInfo userInfo : userInfoArr) {
            int i2 = userInfo.id;
            int uid = UserHandle.getUid(i, packageStateInternal.getAppId());
            int uid2 = UserHandle.getUid(i2, packageStateInternal2.getAppId());
            boolean shouldFilterApplicationInternal =
                    shouldFilterApplicationInternal(
                            computer, uid, packageStateInternal, packageStateInternal2, i2);
            boolean shouldFilterApplicationInternal2 =
                    shouldFilterApplicationInternal(
                            computer, uid2, packageStateInternal2, packageStateInternal, i);
            this.mShouldFilterCache.put(uid, uid2, shouldFilterApplicationInternal);
            this.mShouldFilterCache.put(uid2, uid, shouldFilterApplicationInternal2);
        }
    }
}
