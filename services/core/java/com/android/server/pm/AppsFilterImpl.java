package com.android.server.pm;

import android.R;
import android.content.pm.PackageManagerInternal;
import android.content.pm.SigningDetails;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseSetArray;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.FgThread;
import com.android.server.compat.CompatChange;
import com.android.server.om.OverlayReferenceMapper;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.SharedUserApi;
import com.android.server.pm.pkg.component.ParsedInstrumentation;
import com.android.server.pm.pkg.component.ParsedPermission;
import com.android.server.pm.pkg.component.ParsedUsesPermission;
import com.android.server.utils.Snappable;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.WatchedArraySet;
import com.android.server.utils.WatchedSparseBooleanMatrix;
import com.android.server.utils.WatchedSparseSetArray;
import com.android.server.utils.Watcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class AppsFilterImpl extends AppsFilterLocked implements Watchable, Snappable {
  public final ArrayMap mPermissionToUids;
  public final SnapshotCache mSnapshot;
  public final ArrayMap mUsesPermissionToUids;
  public final WatchableImpl mWatchable = new WatchableImpl();

  @Override // com.android.server.utils.Watchable
  public void registerObserver(Watcher watcher) {
    this.mWatchable.registerObserver(watcher);
  }

  @Override // com.android.server.utils.Watchable
  public void unregisterObserver(Watcher watcher) {
    this.mWatchable.unregisterObserver(watcher);
  }

  @Override // com.android.server.utils.Watchable
  public boolean isRegisteredObserver(Watcher watcher) {
    return this.mWatchable.isRegisteredObserver(watcher);
  }

  @Override // com.android.server.utils.Watchable
  public void dispatchChange(Watchable watchable) {
    this.mWatchable.dispatchChange(watchable);
  }

  public final void onChanged() {
    dispatchChange(this);
  }

  public final void invalidateCache(String str) {
    if (this.mCacheValid.compareAndSet(true, false)) {
      Slog.i("AppsFilter", "Invalidating cache: " + str);
    }
  }

  public AppsFilterImpl(
      FeatureConfig featureConfig,
      String[] strArr,
      boolean z,
      OverlayReferenceMapper.Provider provider,
      Handler handler) {
    this.mFeatureConfig = featureConfig;
    this.mForceQueryableByDevicePackageNames = strArr;
    this.mSystemAppsQueryable = z;
    this.mOverlayReferenceMapper = new OverlayReferenceMapper(true, provider);
    this.mHandler = handler;
    this.mShouldFilterCache = new WatchedSparseBooleanMatrix();
    WatchedSparseBooleanMatrix watchedSparseBooleanMatrix = this.mShouldFilterCache;
    this.mShouldFilterCacheSnapshot =
        new SnapshotCache.Auto(
            watchedSparseBooleanMatrix,
            watchedSparseBooleanMatrix,
            "AppsFilter.mShouldFilterCache");
    this.mImplicitlyQueryable = new WatchedSparseSetArray();
    WatchedSparseSetArray watchedSparseSetArray = this.mImplicitlyQueryable;
    this.mImplicitQueryableSnapshot =
        new SnapshotCache.Auto(
            watchedSparseSetArray, watchedSparseSetArray, "AppsFilter.mImplicitlyQueryable");
    this.mRetainedImplicitlyQueryable = new WatchedSparseSetArray();
    WatchedSparseSetArray watchedSparseSetArray2 = this.mRetainedImplicitlyQueryable;
    this.mRetainedImplicitlyQueryableSnapshot =
        new SnapshotCache.Auto(
            watchedSparseSetArray2,
            watchedSparseSetArray2,
            "AppsFilter.mRetainedImplicitlyQueryable");
    this.mQueriesViaPackage = new WatchedSparseSetArray();
    WatchedSparseSetArray watchedSparseSetArray3 = this.mQueriesViaPackage;
    this.mQueriesViaPackageSnapshot =
        new SnapshotCache.Auto(
            watchedSparseSetArray3, watchedSparseSetArray3, "AppsFilter.mQueriesViaPackage");
    this.mQueriesViaComponent = new WatchedSparseSetArray();
    WatchedSparseSetArray watchedSparseSetArray4 = this.mQueriesViaComponent;
    this.mQueriesViaComponentSnapshot =
        new SnapshotCache.Auto(
            watchedSparseSetArray4, watchedSparseSetArray4, "AppsFilter.mQueriesViaComponent");
    this.mQueryableViaUsesLibrary = new WatchedSparseSetArray();
    WatchedSparseSetArray watchedSparseSetArray5 = this.mQueryableViaUsesLibrary;
    this.mQueryableViaUsesLibrarySnapshot =
        new SnapshotCache.Auto(
            watchedSparseSetArray5, watchedSparseSetArray5, "AppsFilter.mQueryableViaUsesLibrary");
    this.mQueryableViaUsesPermission = new WatchedSparseSetArray();
    WatchedSparseSetArray watchedSparseSetArray6 = this.mQueryableViaUsesPermission;
    this.mQueryableViaUsesPermissionSnapshot =
        new SnapshotCache.Auto(
            watchedSparseSetArray6,
            watchedSparseSetArray6,
            "AppsFilter.mQueryableViaUsesPermission");
    this.mForceQueryable = new WatchedArraySet();
    WatchedArraySet watchedArraySet = this.mForceQueryable;
    this.mForceQueryableSnapshot =
        new SnapshotCache.Auto(watchedArraySet, watchedArraySet, "AppsFilter.mForceQueryable");
    this.mProtectedBroadcasts = new WatchedArraySet();
    WatchedArraySet watchedArraySet2 = this.mProtectedBroadcasts;
    this.mProtectedBroadcastsSnapshot =
        new SnapshotCache.Auto(
            watchedArraySet2, watchedArraySet2, "AppsFilter.mProtectedBroadcasts");
    this.mPermissionToUids = new ArrayMap();
    this.mUsesPermissionToUids = new ArrayMap();
    this.mSnapshot =
        new SnapshotCache(this, this) { // from class: com.android.server.pm.AppsFilterImpl.1
          @Override // com.android.server.utils.SnapshotCache
          public AppsFilterSnapshot createSnapshot() {
            return new AppsFilterSnapshotImpl(AppsFilterImpl.this);
          }
        };
    readCacheEnabledSysProp();
    SystemProperties.addChangeCallback(
        new Runnable() { // from class:
          // com.android.server.pm.AppsFilterImpl$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            AppsFilterImpl.this.readCacheEnabledSysProp();
          }
        });
  }

  public final void readCacheEnabledSysProp() {
    this.mCacheEnabled = SystemProperties.getBoolean("debug.pm.use_app_filter_cache", true);
  }

  @Override // com.android.server.utils.Snappable
  public AppsFilterSnapshot snapshot() {
    return (AppsFilterSnapshot) this.mSnapshot.snapshot();
  }

  public class FeatureConfigImpl implements FeatureConfig, CompatChange.ChangeListener {
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

    public void setAppsFilter(AppsFilterImpl appsFilterImpl) {
      this.mAppsFilter = appsFilterImpl;
    }

    @Override // com.android.server.pm.FeatureConfig
    public void onSystemReady() {
      this.mFeatureEnabled =
          DeviceConfig.getBoolean(
              "package_manager_service", "package_query_filtering_enabled", true);
      DeviceConfig.addOnPropertiesChangedListener(
          "package_manager_service",
          FgThread.getExecutor(),
          new DeviceConfig.OnPropertiesChangedListener() { // from class:
            // com.android.server.pm.AppsFilterImpl$FeatureConfigImpl$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
              AppsFilterImpl.FeatureConfigImpl.this.lambda$onSystemReady$0(properties);
            }
          });
      this.mInjector.getCompatibility().registerListener(135549675L, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$0(DeviceConfig.Properties properties) {
      if (properties.getKeyset().contains("package_query_filtering_enabled")) {
        synchronized (this) {
          this.mFeatureEnabled = properties.getBoolean("package_query_filtering_enabled", true);
        }
      }
    }

    @Override // com.android.server.pm.FeatureConfig
    public boolean isGloballyEnabled() {
      return this.mFeatureEnabled;
    }

    @Override // com.android.server.pm.FeatureConfig
    public boolean packageIsEnabled(AndroidPackage androidPackage) {
      boolean z;
      synchronized (this.mDisabledPackages) {
        z = !this.mDisabledPackages.contains(androidPackage.getPackageName());
      }
      return z;
    }

    @Override // com.android.server.pm.FeatureConfig
    public boolean isLoggingEnabled(int i) {
      SparseBooleanArray sparseBooleanArray = this.mLoggingEnabled;
      return sparseBooleanArray != null && sparseBooleanArray.indexOfKey(i) >= 0;
    }

    @Override // com.android.server.pm.FeatureConfig
    public void enableLogging(int i, boolean z) {
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

    @Override // com.android.server.compat.CompatChange.ChangeListener
    public void onCompatChange(String str) {
      Computer computer = (Computer) this.mPmInternal.snapshot();
      AndroidPackage androidPackage = computer.getPackage(str);
      if (androidPackage == null) {
        return;
      }
      long currentTimeMicro = SystemClock.currentTimeMicro();
      updateEnabledState(androidPackage);
      this.mAppsFilter.updateShouldFilterCacheForPackage(computer, str);
      this.mAppsFilter.logCacheUpdated(
          4,
          SystemClock.currentTimeMicro() - currentTimeMicro,
          computer.getUserInfos().length,
          computer.getPackageStates().size(),
          androidPackage.getUid());
    }

    public final void updateEnabledState(AndroidPackage androidPackage) {
      boolean isChangeEnabledInternalNoLogging =
          this.mInjector
              .getCompatibility()
              .isChangeEnabledInternalNoLogging(
                  135549675L, AndroidPackageUtils.generateAppInfoWithoutState(androidPackage));
      synchronized (this.mDisabledPackages) {
        if (isChangeEnabledInternalNoLogging) {
          this.mDisabledPackages.remove(androidPackage.getPackageName());
        } else {
          this.mDisabledPackages.add(androidPackage.getPackageName());
        }
      }
      AppsFilterImpl appsFilterImpl = this.mAppsFilter;
      if (appsFilterImpl != null) {
        appsFilterImpl.onChanged();
      }
    }

    @Override // com.android.server.pm.FeatureConfig
    public void updatePackageState(PackageStateInternal packageStateInternal, boolean z) {
      enableLogging(
          packageStateInternal.getAppId(),
          (packageStateInternal.getPkg() == null
                  || z
                  || (!packageStateInternal.getPkg().isTestOnly()
                      && !packageStateInternal.getPkg().isDebuggable()))
              ? false
              : true);
      if (z) {
        synchronized (this.mDisabledPackages) {
          this.mDisabledPackages.remove(packageStateInternal.getPackageName());
        }
        AppsFilterImpl appsFilterImpl = this.mAppsFilter;
        if (appsFilterImpl != null) {
          appsFilterImpl.onChanged();
          return;
        }
        return;
      }
      if (packageStateInternal.getPkg() != null) {
        updateEnabledState(packageStateInternal.getPkg());
      }
    }

    @Override // com.android.server.pm.FeatureConfig
    public FeatureConfig snapshot() {
      return new FeatureConfigImpl(this);
    }
  }

  public static AppsFilterImpl create(
      PackageManagerServiceInjector packageManagerServiceInjector,
      PackageManagerInternal packageManagerInternal) {
    String[] strArr;
    boolean z =
        packageManagerServiceInjector
            .getContext()
            .getResources()
            .getBoolean(R.bool.config_enableStylusPointerIcon);
    FeatureConfigImpl featureConfigImpl =
        new FeatureConfigImpl(packageManagerInternal, packageManagerServiceInjector);
    if (z) {
      strArr = new String[0];
    } else {
      String[] stringArray =
          packageManagerServiceInjector
              .getContext()
              .getResources()
              .getStringArray(R.array.non_removable_euicc_slots);
      for (int i = 0; i < stringArray.length; i++) {
        stringArray[i] = stringArray[i].intern();
      }
      strArr = stringArray;
    }
    AppsFilterImpl appsFilterImpl =
        new AppsFilterImpl(
            featureConfigImpl, strArr, z, null, packageManagerServiceInjector.getHandler());
    featureConfigImpl.setAppsFilter(appsFilterImpl);
    return appsFilterImpl;
  }

  public FeatureConfig getFeatureConfig() {
    return this.mFeatureConfig;
  }

  public boolean grantImplicitAccess(int i, int i2, boolean z) {
    boolean add;
    if (i == i2) {
      return false;
    }
    synchronized (this.mImplicitlyQueryableLock) {
      if (z) {
        add = this.mRetainedImplicitlyQueryable.add(i, Integer.valueOf(i2));
      } else {
        add = this.mImplicitlyQueryable.add(i, Integer.valueOf(i2));
      }
      if (!this.mCacheReady && add) {
        this.mNeedToUpdateCacheForImplicitAccess = true;
        Slog.i("AppsFilter", "Will update cache: " + i + " -> " + i2);
      }
    }
    if (this.mCacheReady) {
      synchronized (this.mCacheLock) {
        this.mShouldFilterCache.put(i, i2, false);
      }
    }
    if (add) {
      onChanged();
    }
    return add;
  }

  public void onSystemReady(PackageManagerInternal packageManagerInternal) {
    this.mOverlayReferenceMapper.rebuildIfDeferred();
    this.mFeatureConfig.onSystemReady();
    updateEntireShouldFilterCacheAsync(packageManagerInternal, 1);
  }

  public void addPackage(
      Computer computer, PackageStateInternal packageStateInternal, boolean z, boolean z2) {
    int i;
    long currentTimeMicro = SystemClock.currentTimeMicro();
    int i2 = z ? 3 : 1;
    if (z) {
      try {
        removePackageInternal(computer, packageStateInternal, true, z2);
      } finally {
        onChanged();
      }
    }
    ArrayMap packageStates = computer.getPackageStates();
    UserInfo[] userInfos = computer.getUserInfos();
    ArraySet addPackageInternal = addPackageInternal(packageStateInternal, packageStates);
    if (this.mCacheReady) {
      synchronized (this.mCacheLock) {
        try {
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
              int i3 = 0;
              while (i3 < addPackageInternal.size()) {
                PackageStateInternal packageStateInternal2 =
                    (PackageStateInternal)
                        packageStates.get((String) addPackageInternal.valueAt(i3));
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
                i3 = i + 1;
              }
            }
            logCacheUpdated(
                i2,
                SystemClock.currentTimeMicro() - currentTimeMicro,
                userInfos.length,
                packageStates.size(),
                packageStateInternal.getAppId());
          } catch (Throwable th) {
            th = th;
            throw th;
          }
        } catch (Throwable th2) {
          th = th2;
          throw th;
        }
      }
    } else {
      invalidateCache("addPackage: " + packageStateInternal.getPackageName());
    }
  }

  public final ArraySet addPackageInternal(
      PackageStateInternal packageStateInternal, ArrayMap arrayMap) {
    boolean z;
    boolean contains;
    SigningDetails signingDetails;
    boolean z2;
    if ("android".equals(packageStateInternal.getPackageName())) {
      this.mSystemSigningDetails = packageStateInternal.getSigningDetails();
      for (PackageStateInternal packageStateInternal2 : arrayMap.values()) {
        if (isSystemSigned(this.mSystemSigningDetails, packageStateInternal2)) {
          synchronized (this.mForceQueryableLock) {
            this.mForceQueryable.add(Integer.valueOf(packageStateInternal2.getAppId()));
          }
        }
      }
    }
    AndroidPackageInternal pkg = packageStateInternal.getPkg();
    if (pkg == null) {
      return null;
    }
    List protectedBroadcasts = pkg.getProtectedBroadcasts();
    if (protectedBroadcasts.size() != 0) {
      synchronized (this.mProtectedBroadcastsLock) {
        int size = this.mProtectedBroadcasts.size();
        this.mProtectedBroadcasts.addAll(protectedBroadcasts);
        z2 = this.mProtectedBroadcasts.size() != size;
      }
      if (z2) {
        Slog.i(
            "AppsFilter",
            "Protected-Broadcast is changed by Installing "
                + packageStateInternal.getPackageName());
        this.mQueriesViaComponentRequireRecompute.set(true);
      }
    }
    synchronized (this.mForceQueryableLock) {
      if (!this.mForceQueryable.contains(Integer.valueOf(packageStateInternal.getAppId()))
          && !packageStateInternal.isForceQueryableOverride()
          && (!packageStateInternal.isSystem()
              || (!this.mSystemAppsQueryable
                  && !pkg.isForceQueryable()
                  && !ArrayUtils.contains(
                      this.mForceQueryableByDevicePackageNames, pkg.getPackageName())))) {
        z = false;
        if (!z
            || ((signingDetails = this.mSystemSigningDetails) != null
                && isSystemSigned(signingDetails, packageStateInternal))) {
          this.mForceQueryable.add(Integer.valueOf(packageStateInternal.getAppId()));
        }
      }
      z = true;
      if (!z) {}
      this.mForceQueryable.add(Integer.valueOf(packageStateInternal.getAppId()));
    }
    if (!pkg.getUsesPermissions().isEmpty()) {
      synchronized (this.mQueryableViaUsesPermissionLock) {
        Iterator it = pkg.getUsesPermissions().iterator();
        while (it.hasNext()) {
          String name = ((ParsedUsesPermission) it.next()).getName();
          if (this.mPermissionToUids.containsKey(name)) {
            ArraySet arraySet = (ArraySet) this.mPermissionToUids.get(name);
            for (int i = 0; i < arraySet.size(); i++) {
              int intValue = ((Integer) arraySet.valueAt(i)).intValue();
              if (intValue != packageStateInternal.getAppId()) {
                this.mQueryableViaUsesPermission.add(
                    packageStateInternal.getAppId(), Integer.valueOf(intValue));
              }
            }
          }
          if (!this.mUsesPermissionToUids.containsKey(name)) {
            this.mUsesPermissionToUids.put(name, new ArraySet());
          }
          ((ArraySet) this.mUsesPermissionToUids.get(name))
              .add(Integer.valueOf(packageStateInternal.getAppId()));
        }
      }
    }
    if (!pkg.getPermissions().isEmpty()) {
      synchronized (this.mQueryableViaUsesPermissionLock) {
        Iterator it2 = pkg.getPermissions().iterator();
        while (it2.hasNext()) {
          String name2 = ((ParsedPermission) it2.next()).getName();
          if (this.mUsesPermissionToUids.containsKey(name2)) {
            ArraySet arraySet2 = (ArraySet) this.mUsesPermissionToUids.get(name2);
            for (int i2 = 0; i2 < arraySet2.size(); i2++) {
              int intValue2 = ((Integer) arraySet2.valueAt(i2)).intValue();
              if (intValue2 != packageStateInternal.getAppId()) {
                this.mQueryableViaUsesPermission.add(
                    intValue2, Integer.valueOf(packageStateInternal.getAppId()));
              }
            }
          }
          if (!this.mPermissionToUids.containsKey(name2)) {
            this.mPermissionToUids.put(name2, new ArraySet());
          }
          ((ArraySet) this.mPermissionToUids.get(name2))
              .add(Integer.valueOf(packageStateInternal.getAppId()));
        }
      }
    }
    for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
      PackageStateInternal packageStateInternal3 = (PackageStateInternal) arrayMap.valueAt(size2);
      if (packageStateInternal3.getAppId() != packageStateInternal.getAppId()
          && packageStateInternal3.getPkg() != null) {
        AndroidPackageInternal pkg2 = packageStateInternal3.getPkg();
        if (!z) {
          if (!this.mQueriesViaComponentRequireRecompute.get()
              && AppsFilterUtils.canQueryViaComponents(pkg2, pkg, this.mProtectedBroadcasts)) {
            synchronized (this.mQueriesViaComponentLock) {
              this.mQueriesViaComponent.add(
                  packageStateInternal3.getAppId(),
                  Integer.valueOf(packageStateInternal.getAppId()));
            }
          }
          if (AppsFilterUtils.canQueryViaPackage(pkg2, pkg)
              || AppsFilterUtils.canQueryAsInstaller(packageStateInternal3, pkg)
              || AppsFilterUtils.canQueryAsUpdateOwner(packageStateInternal3, pkg)) {
            synchronized (this.mQueriesViaPackageLock) {
              this.mQueriesViaPackage.add(
                  packageStateInternal3.getAppId(),
                  Integer.valueOf(packageStateInternal.getAppId()));
            }
          }
          if (AppsFilterUtils.canQueryViaUsesLibrary(pkg2, pkg)) {
            synchronized (this.mQueryableViaUsesLibraryLock) {
              this.mQueryableViaUsesLibrary.add(
                  packageStateInternal3.getAppId(),
                  Integer.valueOf(packageStateInternal.getAppId()));
            }
          }
        }
        synchronized (this.mForceQueryableLock) {
          contains =
              this.mForceQueryable.contains(Integer.valueOf(packageStateInternal3.getAppId()));
        }
        if (!contains) {
          if (!this.mQueriesViaComponentRequireRecompute.get()
              && AppsFilterUtils.canQueryViaComponents(pkg, pkg2, this.mProtectedBroadcasts)) {
            synchronized (this.mQueriesViaComponentLock) {
              this.mQueriesViaComponent.add(
                  packageStateInternal.getAppId(),
                  Integer.valueOf(packageStateInternal3.getAppId()));
            }
          }
          if (AppsFilterUtils.canQueryViaPackage(pkg, pkg2)
              || AppsFilterUtils.canQueryAsInstaller(packageStateInternal, pkg2)
              || AppsFilterUtils.canQueryAsUpdateOwner(packageStateInternal, pkg2)) {
            synchronized (this.mQueriesViaPackageLock) {
              this.mQueriesViaPackage.add(
                  packageStateInternal.getAppId(),
                  Integer.valueOf(packageStateInternal3.getAppId()));
            }
          }
          if (AppsFilterUtils.canQueryViaUsesLibrary(pkg, pkg2)) {
            synchronized (this.mQueryableViaUsesLibraryLock) {
              this.mQueryableViaUsesLibrary.add(
                  packageStateInternal.getAppId(),
                  Integer.valueOf(packageStateInternal3.getAppId()));
            }
          }
        }
        if (packageStateInternal.getPkg() != null
            && packageStateInternal3.getPkg() != null
            && (pkgInstruments(packageStateInternal.getPkg(), packageStateInternal3.getPkg())
                || pkgInstruments(packageStateInternal3.getPkg(), packageStateInternal.getPkg()))) {
          synchronized (this.mQueriesViaPackageLock) {
            this.mQueriesViaPackage.add(
                packageStateInternal.getAppId(), Integer.valueOf(packageStateInternal3.getAppId()));
            this.mQueriesViaPackage.add(
                packageStateInternal3.getAppId(), Integer.valueOf(packageStateInternal.getAppId()));
          }
        }
      }
    }
    int size3 = arrayMap.size();
    ArrayMap arrayMap2 = new ArrayMap(size3);
    for (int i3 = 0; i3 < size3; i3++) {
      PackageStateInternal packageStateInternal4 = (PackageStateInternal) arrayMap.valueAt(i3);
      if (packageStateInternal4.getPkg() != null) {
        arrayMap2.put(packageStateInternal4.getPackageName(), packageStateInternal4.getPkg());
      }
    }
    ArraySet addPkg = this.mOverlayReferenceMapper.addPkg(packageStateInternal.getPkg(), arrayMap2);
    this.mFeatureConfig.updatePackageState(packageStateInternal, false);
    return addPkg;
  }

  public final void removeAppIdFromVisibilityCache(int i) {
    synchronized (this.mCacheLock) {
      int i2 = 0;
      while (i2 < this.mShouldFilterCache.size()) {
        if (UserHandle.getAppId(this.mShouldFilterCache.keyAt(i2)) == i) {
          this.mShouldFilterCache.removeAt(i2);
          i2--;
        }
        i2++;
      }
    }
  }

  public final void updateEntireShouldFilterCache(Computer computer, int i) {
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
          "We encountered a new user that isn't a member of known users, updating the whole cache");
      i = -1;
    }
    updateEntireShouldFilterCacheInner(computer, packageStates, userInfos, i);
    onChanged();
  }

  public final void updateEntireShouldFilterCacheInner(
      Computer computer, ArrayMap arrayMap, UserInfo[] userInfoArr, int i) {
    synchronized (this.mCacheLock) {
      if (i == -1) {
        this.mShouldFilterCache.clear();
      }
      this.mShouldFilterCache.setCapacity(userInfoArr.length * arrayMap.size());
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
  }

  public final void updateEntireShouldFilterCacheAsync(
      PackageManagerInternal packageManagerInternal, int i) {
    updateEntireShouldFilterCacheAsync(packageManagerInternal, 10000L, i);
  }

  public final void updateEntireShouldFilterCacheAsync(
      final PackageManagerInternal packageManagerInternal, final long j, final int i) {
    this.mHandler.postDelayed(
        new Runnable() { // from class:
          // com.android.server.pm.AppsFilterImpl$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            AppsFilterImpl.this.lambda$updateEntireShouldFilterCacheAsync$0(
                packageManagerInternal, i, j);
          }
        },
        j);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateEntireShouldFilterCacheAsync$0(
      PackageManagerInternal packageManagerInternal, int i, long j) {
    if (this.mCacheValid.compareAndSet(false, true)) {
      long currentTimeMicro = SystemClock.currentTimeMicro();
      ArrayMap arrayMap = new ArrayMap();
      Computer computer = (Computer) packageManagerInternal.snapshot();
      ArrayMap packageStates = computer.getPackageStates();
      UserInfo[] userInfos = computer.getUserInfos();
      arrayMap.ensureCapacity(packageStates.size());
      UserInfo[][] userInfoArr = {userInfos};
      int size = packageStates.size();
      for (int i2 = 0; i2 < size; i2++) {
        arrayMap.put(
            (String) packageStates.keyAt(i2),
            ((PackageStateInternal) packageStates.valueAt(i2)).getPkg());
      }
      updateEntireShouldFilterCacheInner(computer, packageStates, userInfoArr[0], -1);
      logCacheRebuilt(
          i,
          SystemClock.currentTimeMicro() - currentTimeMicro,
          userInfos.length,
          packageStates.size());
      if (!this.mCacheValid.compareAndSet(true, true)) {
        Slog.i("AppsFilter", "Cache invalidated while building, retrying.");
        updateEntireShouldFilterCacheAsync(packageManagerInternal, Math.min(2 * j, 10000L), i);
        return;
      }
      synchronized (this.mImplicitlyQueryableLock) {
        if (this.mNeedToUpdateCacheForImplicitAccess) {
          updateShouldFilterCacheForImplicitAccess();
          this.mNeedToUpdateCacheForImplicitAccess = false;
        }
        this.mCacheReady = true;
        Slog.i("AppsFilter", "Updated cache and cache is ready");
      }
      onChanged();
    }
  }

  public void onUserCreated(Computer computer, int i) {
    if (this.mCacheReady) {
      long currentTimeMicro = SystemClock.currentTimeMicro();
      updateEntireShouldFilterCache(computer, i);
      logCacheRebuilt(
          2,
          SystemClock.currentTimeMicro() - currentTimeMicro,
          computer.getUserInfos().length,
          computer.getPackageStates().size());
    }
  }

  public void onUserDeleted(Computer computer, int i) {
    if (this.mCacheReady) {
      long currentTimeMicro = SystemClock.currentTimeMicro();
      removeShouldFilterCacheForUser(i);
      onChanged();
      logCacheRebuilt(
          3,
          SystemClock.currentTimeMicro() - currentTimeMicro,
          computer.getUserInfos().length,
          computer.getPackageStates().size());
    }
  }

  public final void updateShouldFilterCacheForImplicitAccess() {
    updateShouldFilterCacheForImplicitAccess(this.mRetainedImplicitlyQueryable);
    updateShouldFilterCacheForImplicitAccess(this.mImplicitlyQueryable);
  }

  public final void updateShouldFilterCacheForImplicitAccess(
      WatchedSparseSetArray watchedSparseSetArray) {
    synchronized (this.mCacheLock) {
      for (int i = 0; i < watchedSparseSetArray.size(); i++) {
        Integer valueOf = Integer.valueOf(watchedSparseSetArray.keyAt(i));
        Iterator it = watchedSparseSetArray.get(valueOf.intValue()).iterator();
        while (it.hasNext()) {
          this.mShouldFilterCache.put(valueOf.intValue(), ((Integer) it.next()).intValue(), false);
        }
      }
    }
  }

  public final void updateShouldFilterCacheForPackage(Computer computer, String str) {
    if (this.mCacheReady) {
      ArrayMap packageStates = computer.getPackageStates();
      UserInfo[] userInfos = computer.getUserInfos();
      synchronized (this.mCacheLock) {
        updateShouldFilterCacheForPackage(
            computer,
            null,
            (PackageStateInternal) packageStates.get(str),
            packageStates,
            userInfos,
            -1,
            packageStates.size());
      }
      onChanged();
    }
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
      PackageStateInternal packageStateInternal2 = (PackageStateInternal) arrayMap.valueAt(min);
      if (packageStateInternal.getAppId() != packageStateInternal2.getAppId()
          && packageStateInternal.getPackageName() != str
          && packageStateInternal2.getPackageName() != str) {
        if (i == -1) {
          for (UserInfo userInfo : userInfoArr) {
            updateShouldFilterCacheForUser(
                computer, packageStateInternal, userInfoArr, packageStateInternal2, userInfo.id);
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

  public final void removeShouldFilterCacheForUser(int i) {
    synchronized (this.mCacheLock) {
      int[] keys = this.mShouldFilterCache.keys();
      int length = keys.length;
      int binarySearch = Arrays.binarySearch(keys, UserHandle.getUid(i, 0));
      if (binarySearch < 0) {
        binarySearch = ~binarySearch;
      }
      if (binarySearch < length && UserHandle.getUserId(keys[binarySearch]) == i) {
        int binarySearch2 = Arrays.binarySearch(keys, UserHandle.getUid(i + 1, 0) - 1);
        int i2 = binarySearch2 >= 0 ? binarySearch2 + 1 : ~binarySearch2;
        if (binarySearch < i2 && UserHandle.getUserId(keys[i2 - 1]) == i) {
          this.mShouldFilterCache.removeRange(binarySearch, i2);
          this.mShouldFilterCache.compact();
          return;
        }
        Slog.w(
            "AppsFilter",
            "Failed to remove should filter cache for user "
                + i
                + ", fromIndex="
                + binarySearch
                + ", toIndex="
                + i2);
        return;
      }
      Slog.w(
          "AppsFilter",
          "Failed to remove should filter cache for user " + i + ", fromIndex=" + binarySearch);
    }
  }

  public static boolean isSystemSigned(
      SigningDetails signingDetails, PackageStateInternal packageStateInternal) {
    return packageStateInternal.isSystem()
        && packageStateInternal.getSigningDetails().signaturesMatchExactly(signingDetails);
  }

  public final void collectProtectedBroadcasts(ArrayMap arrayMap, String str) {
    synchronized (this.mProtectedBroadcastsLock) {
      this.mProtectedBroadcasts.clear();
      for (int size = arrayMap.size() - 1; size >= 0; size--) {
        PackageStateInternal packageStateInternal = (PackageStateInternal) arrayMap.valueAt(size);
        if (packageStateInternal.getPkg() != null
            && !packageStateInternal.getPkg().getPackageName().equals(str)) {
          List protectedBroadcasts = packageStateInternal.getPkg().getProtectedBroadcasts();
          if (!protectedBroadcasts.isEmpty()) {
            this.mProtectedBroadcasts.addAll(protectedBroadcasts);
          }
        }
      }
    }
  }

  @Override // com.android.server.pm.AppsFilterBase
  public boolean isQueryableViaComponentWhenRequireRecompute(
      ArrayMap arrayMap,
      PackageStateInternal packageStateInternal,
      ArraySet arraySet,
      AndroidPackage androidPackage,
      int i,
      int i2) {
    recomputeComponentVisibility(arrayMap);
    return isQueryableViaComponent(i, i2);
  }

  public final void recomputeComponentVisibility(ArrayMap arrayMap) {
    WatchedArraySet snapshot;
    WatchedArraySet snapshot2;
    synchronized (this.mProtectedBroadcastsLock) {
      snapshot = this.mProtectedBroadcasts.snapshot();
    }
    synchronized (this.mForceQueryableLock) {
      snapshot2 = this.mForceQueryable.snapshot();
    }
    AppsFilterUtils.ParallelComputeComponentVisibility parallelComputeComponentVisibility =
        new AppsFilterUtils.ParallelComputeComponentVisibility(arrayMap, snapshot2, snapshot);
    long currentTimeMillis = System.currentTimeMillis();
    SparseSetArray execute = parallelComputeComponentVisibility.execute();
    Slog.i(
        "AppsFilter",
        "Recomputing visibility takes " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
    synchronized (this.mQueriesViaComponentLock) {
      this.mQueriesViaComponent = new WatchedSparseSetArray(execute);
      WatchedSparseSetArray watchedSparseSetArray = this.mQueriesViaComponent;
      this.mQueriesViaComponentSnapshot =
          new SnapshotCache.Auto(
              watchedSparseSetArray, watchedSparseSetArray, "AppsFilter.mQueriesViaComponent");
    }
    this.mQueriesViaComponentRequireRecompute.set(false);
    onChanged();
  }

  public void addPackage(Computer computer, PackageStateInternal packageStateInternal) {
    addPackage(computer, packageStateInternal, false, false);
  }

  public void removePackage(Computer computer, PackageStateInternal packageStateInternal) {
    long currentTimeMicro = SystemClock.currentTimeMicro();
    removePackageInternal(computer, packageStateInternal, false, false);
    logCacheUpdated(
        2,
        SystemClock.currentTimeMicro() - currentTimeMicro,
        computer.getUserInfos().length,
        computer.getPackageStates().size(),
        packageStateInternal.getAppId());
  }

  public final void removePackageInternal(
      Computer computer, PackageStateInternal packageStateInternal, boolean z, boolean z2) {
    boolean z3;
    ArrayMap packageStates = computer.getPackageStates();
    UserInfo[] userInfos = computer.getUserInfos();
    if (!z || !z2) {
      synchronized (this.mImplicitlyQueryableLock) {
        for (UserInfo userInfo : userInfos) {
          int uid = UserHandle.getUid(userInfo.id, packageStateInternal.getAppId());
          this.mImplicitlyQueryable.remove(uid);
          for (int size = this.mImplicitlyQueryable.size() - 1; size >= 0; size--) {
            WatchedSparseSetArray watchedSparseSetArray = this.mImplicitlyQueryable;
            watchedSparseSetArray.remove(watchedSparseSetArray.keyAt(size), Integer.valueOf(uid));
          }
          if (!z) {
            this.mRetainedImplicitlyQueryable.remove(uid);
            for (int size2 = this.mRetainedImplicitlyQueryable.size() - 1; size2 >= 0; size2--) {
              WatchedSparseSetArray watchedSparseSetArray2 = this.mRetainedImplicitlyQueryable;
              watchedSparseSetArray2.remove(
                  watchedSparseSetArray2.keyAt(size2), Integer.valueOf(uid));
            }
          }
        }
      }
    }
    if (!this.mQueriesViaComponentRequireRecompute.get()) {
      synchronized (this.mQueriesViaComponentLock) {
        this.mQueriesViaComponent.remove(packageStateInternal.getAppId());
        for (int size3 = this.mQueriesViaComponent.size() - 1; size3 >= 0; size3--) {
          WatchedSparseSetArray watchedSparseSetArray3 = this.mQueriesViaComponent;
          watchedSparseSetArray3.remove(
              watchedSparseSetArray3.keyAt(size3),
              Integer.valueOf(packageStateInternal.getAppId()));
        }
      }
    }
    synchronized (this.mQueriesViaPackageLock) {
      this.mQueriesViaPackage.remove(packageStateInternal.getAppId());
      for (int size4 = this.mQueriesViaPackage.size() - 1; size4 >= 0; size4--) {
        WatchedSparseSetArray watchedSparseSetArray4 = this.mQueriesViaPackage;
        watchedSparseSetArray4.remove(
            watchedSparseSetArray4.keyAt(size4), Integer.valueOf(packageStateInternal.getAppId()));
      }
    }
    synchronized (this.mQueryableViaUsesLibraryLock) {
      this.mQueryableViaUsesLibrary.remove(packageStateInternal.getAppId());
      for (int size5 = this.mQueryableViaUsesLibrary.size() - 1; size5 >= 0; size5--) {
        WatchedSparseSetArray watchedSparseSetArray5 = this.mQueryableViaUsesLibrary;
        watchedSparseSetArray5.remove(
            watchedSparseSetArray5.keyAt(size5), Integer.valueOf(packageStateInternal.getAppId()));
      }
    }
    synchronized (this.mQueryableViaUsesPermissionLock) {
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
    }
    synchronized (this.mForceQueryableLock) {
      this.mForceQueryable.remove(Integer.valueOf(packageStateInternal.getAppId()));
    }
    synchronized (this.mProtectedBroadcastsLock) {
      if (packageStateInternal.getPkg() != null
          && !packageStateInternal.getPkg().getProtectedBroadcasts().isEmpty()) {
        String packageName = packageStateInternal.getPkg().getPackageName();
        ArrayList arrayList = new ArrayList(this.mProtectedBroadcasts.untrackedStorage());
        collectProtectedBroadcasts(packageStates, packageName);
        for (int i = 0; i < arrayList.size(); i++) {
          if (!this.mProtectedBroadcasts.contains(arrayList.get(i))) {
            z3 = true;
            break;
          }
        }
      }
      z3 = false;
    }
    if (z3) {
      Slog.i(
          "AppsFilter",
          "Protected-Broadcast is changed by Removing " + packageStateInternal.getPackageName());
      this.mQueriesViaComponentRequireRecompute.set(true);
    }
    ArraySet removePkg =
        this.mOverlayReferenceMapper.removePkg(packageStateInternal.getPackageName());
    this.mFeatureConfig.updatePackageState(packageStateInternal, true);
    SharedUserApi sharedUser =
        packageStateInternal.hasSharedUser()
            ? computer.getSharedUser(packageStateInternal.getSharedUserAppId())
            : null;
    if (sharedUser != null) {
      ArraySet packageStates2 = sharedUser.getPackageStates();
      for (int size6 = packageStates2.size() - 1; size6 >= 0; size6--) {
        if (packageStates2.valueAt(size6) != packageStateInternal) {
          addPackageInternal((PackageStateInternal) packageStates2.valueAt(size6), packageStates);
        }
      }
    }
    if (this.mCacheReady) {
      removeAppIdFromVisibilityCache(packageStateInternal.getAppId());
      if (sharedUser != null) {
        ArraySet packageStates3 = sharedUser.getPackageStates();
        int size7 = packageStates3.size() - 1;
        while (true) {
          if (size7 < 0) {
            break;
          }
          PackageStateInternal packageStateInternal2 =
              (PackageStateInternal) packageStates3.valueAt(size7);
          if (packageStateInternal2 != packageStateInternal) {
            synchronized (this.mCacheLock) {
              updateShouldFilterCacheForPackage(
                  computer,
                  packageStateInternal.getPackageName(),
                  packageStateInternal2,
                  packageStates,
                  userInfos,
                  -1,
                  packageStates.size());
            }
            break;
          }
          size7--;
        }
      }
      if (removePkg != null) {
        for (int i2 = 0; i2 < removePkg.size(); i2++) {
          PackageStateInternal packageStateInternal3 =
              (PackageStateInternal) packageStates.get((String) removePkg.valueAt(i2));
          if (packageStateInternal3 != null) {
            synchronized (this.mCacheLock) {
              updateShouldFilterCacheForPackage(
                  computer,
                  null,
                  packageStateInternal3,
                  packageStates,
                  userInfos,
                  -1,
                  packageStates.size());
            }
          }
        }
      }
    } else {
      invalidateCache("removePackage: " + packageStateInternal.getPackageName());
    }
    onChanged();
  }

  public static boolean pkgInstruments(
      AndroidPackage androidPackage, AndroidPackage androidPackage2) {
    String packageName = androidPackage2.getPackageName();
    List instrumentations = androidPackage.getInstrumentations();
    for (int size = ArrayUtils.size(instrumentations) - 1; size >= 0; size--) {
      if (Objects.equals(
          ((ParsedInstrumentation) instrumentations.get(size)).getTargetPackage(), packageName)) {
        return true;
      }
    }
    return false;
  }

  public final void logCacheRebuilt(int i, long j, int i2, int i3) {
    FrameworkStatsLog.write(
        FrameworkStatsLog.PACKAGE_MANAGER_APPS_FILTER_CACHE_BUILD_REPORTED,
        i,
        j,
        i2,
        i3,
        this.mShouldFilterCache.size());
  }

  public final void logCacheUpdated(int i, long j, int i2, int i3, int i4) {
    if (this.mCacheReady) {
      FrameworkStatsLog.write(
          FrameworkStatsLog.PACKAGE_MANAGER_APPS_FILTER_CACHE_UPDATE_REPORTED,
          i,
          i4,
          j,
          i2,
          i3,
          this.mShouldFilterCache.size());
    }
  }
}
