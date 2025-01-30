package com.android.server.pm;

import android.content.pm.SharedLibraryInfo;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.VersionedPackage;
import android.os.storage.StorageManager;
import android.util.ArraySet;
import android.util.PackageUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.ArrayUtils;
import com.android.server.SystemConfig;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.utils.Snappable;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedLongSparseArray;
import com.android.server.utils.Watcher;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import libcore.util.HexEncoding;

/* loaded from: classes3.dex */
public final class SharedLibrariesImpl implements SharedLibrariesRead, Watchable, Snappable {
  public DeletePackageHelper mDeletePackageHelper;
  public final PackageManagerServiceInjector mInjector;
  public final Watcher mObserver;
  public final PackageManagerService mPm;
  public final WatchedArrayMap mSharedLibraries;
  public final SnapshotCache mSharedLibrariesSnapshot;
  public final SnapshotCache mSnapshot;
  public final WatchedArrayMap mStaticLibsByDeclaringPackage;
  public final SnapshotCache mStaticLibsByDeclaringPackageSnapshot;
  public final WatchableImpl mWatchable;

  public final SnapshotCache makeCache() {
    return new SnapshotCache(
        this, this) { // from class: com.android.server.pm.SharedLibrariesImpl.2
      @Override // com.android.server.utils.SnapshotCache
      public SharedLibrariesImpl createSnapshot() {
        SharedLibrariesImpl sharedLibrariesImpl = new SharedLibrariesImpl();
        sharedLibrariesImpl.mWatchable.seal();
        return sharedLibrariesImpl;
      }
    };
  }

  public SharedLibrariesImpl(
      PackageManagerService packageManagerService,
      PackageManagerServiceInjector packageManagerServiceInjector) {
    this.mWatchable = new WatchableImpl();
    Watcher watcher = new Watcher() { // from class: com.android.server.pm.SharedLibrariesImpl.1
          @Override // com.android.server.utils.Watcher
          public void onChange(Watchable watchable) {
            SharedLibrariesImpl.this.dispatchChange(watchable);
          }
        };
    this.mObserver = watcher;
    this.mPm = packageManagerService;
    this.mInjector = packageManagerServiceInjector;
    WatchedArrayMap watchedArrayMap = new WatchedArrayMap();
    this.mSharedLibraries = watchedArrayMap;
    this.mSharedLibrariesSnapshot =
        new SnapshotCache.Auto(
            watchedArrayMap, watchedArrayMap, "SharedLibrariesImpl.mSharedLibraries");
    WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap();
    this.mStaticLibsByDeclaringPackage = watchedArrayMap2;
    this.mStaticLibsByDeclaringPackageSnapshot =
        new SnapshotCache.Auto(
            watchedArrayMap2,
            watchedArrayMap2,
            "SharedLibrariesImpl.mStaticLibsByDeclaringPackage");
    registerObservers();
    Watchable.verifyWatchedAttributes(this, watcher);
    this.mSnapshot = makeCache();
  }

  public void setDeletePackageHelper(DeletePackageHelper deletePackageHelper) {
    this.mDeletePackageHelper = deletePackageHelper;
  }

  public final void registerObservers() {
    this.mSharedLibraries.registerObserver(this.mObserver);
    this.mStaticLibsByDeclaringPackage.registerObserver(this.mObserver);
  }

  public SharedLibrariesImpl(SharedLibrariesImpl sharedLibrariesImpl) {
    this.mWatchable = new WatchableImpl();
    this.mObserver =
        new Watcher() { // from class: com.android.server.pm.SharedLibrariesImpl.1
          @Override // com.android.server.utils.Watcher
          public void onChange(Watchable watchable) {
            SharedLibrariesImpl.this.dispatchChange(watchable);
          }
        };
    this.mPm = sharedLibrariesImpl.mPm;
    this.mInjector = sharedLibrariesImpl.mInjector;
    this.mSharedLibraries =
        (WatchedArrayMap) sharedLibrariesImpl.mSharedLibrariesSnapshot.snapshot();
    this.mSharedLibrariesSnapshot = new SnapshotCache.Sealed();
    this.mStaticLibsByDeclaringPackage =
        (WatchedArrayMap) sharedLibrariesImpl.mStaticLibsByDeclaringPackageSnapshot.snapshot();
    this.mStaticLibsByDeclaringPackageSnapshot = new SnapshotCache.Sealed();
    this.mSnapshot = new SnapshotCache.Sealed();
  }

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

  @Override // com.android.server.utils.Snappable
  public SharedLibrariesRead snapshot() {
    return (SharedLibrariesRead) this.mSnapshot.snapshot();
  }

  @Override // com.android.server.pm.SharedLibrariesRead
  public WatchedArrayMap getAll() {
    return this.mSharedLibraries;
  }

  public WatchedLongSparseArray getSharedLibraryInfos(String str) {
    WatchedLongSparseArray watchedLongSparseArray;
    synchronized (this.mPm.mLock) {
      watchedLongSparseArray = (WatchedLongSparseArray) this.mSharedLibraries.get(str);
    }
    return watchedLongSparseArray;
  }

  public WatchedArrayMap getSharedLibraries() {
    return this.mSharedLibraries;
  }

  @Override // com.android.server.pm.SharedLibrariesRead
  public SharedLibraryInfo getSharedLibraryInfo(String str, long j) {
    WatchedLongSparseArray watchedLongSparseArray =
        (WatchedLongSparseArray) this.mSharedLibraries.get(str);
    if (watchedLongSparseArray == null) {
      return null;
    }
    return (SharedLibraryInfo) watchedLongSparseArray.get(j);
  }

  @Override // com.android.server.pm.SharedLibrariesRead
  public WatchedLongSparseArray getStaticLibraryInfos(String str) {
    return (WatchedLongSparseArray) this.mStaticLibsByDeclaringPackage.get(str);
  }

  public final PackageStateInternal getLibraryPackage(
      Computer computer, SharedLibraryInfo sharedLibraryInfo) {
    VersionedPackage declaringPackage = sharedLibraryInfo.getDeclaringPackage();
    if (sharedLibraryInfo.isStatic()) {
      return computer.getPackageStateInternal(
          computer.resolveInternalPackageName(
              declaringPackage.getPackageName(), declaringPackage.getLongVersionCode()));
    }
    if (sharedLibraryInfo.isSdk()) {
      return computer.getPackageStateInternal(declaringPackage.getPackageName());
    }
    return null;
  }

  public boolean pruneUnusedStaticSharedLibraries(Computer computer, long j, long j2) {
    int i;
    File findPathForUuid =
        ((StorageManager) this.mInjector.getSystemService(StorageManager.class))
            .findPathForUuid(StorageManager.UUID_PRIVATE_INTERNAL);
    ArrayList arrayList = new ArrayList();
    long currentTimeMillis = System.currentTimeMillis();
    WatchedArrayMap sharedLibraries = computer.getSharedLibraries();
    int size = sharedLibraries.size();
    int i2 = 0;
    while (i2 < size) {
      WatchedLongSparseArray watchedLongSparseArray =
          (WatchedLongSparseArray) sharedLibraries.valueAt(i2);
      if (watchedLongSparseArray != null) {
        int size2 = watchedLongSparseArray.size();
        int i3 = 0;
        while (i3 < size2) {
          SharedLibraryInfo sharedLibraryInfo =
              (SharedLibraryInfo) watchedLongSparseArray.valueAt(i3);
          PackageStateInternal libraryPackage = getLibraryPackage(computer, sharedLibraryInfo);
          if (libraryPackage == null
              || currentTimeMillis - libraryPackage.getLastUpdateTime() < j2
              || libraryPackage.isSystem()) {
            i = i2;
          } else {
            i = i2;
            arrayList.add(
                new VersionedPackage(
                    libraryPackage.getPkg().getPackageName(),
                    sharedLibraryInfo.getDeclaringPackage().getLongVersionCode()));
          }
          i3++;
          i2 = i;
        }
      }
      i2++;
    }
    int size3 = arrayList.size();
    for (int i4 = 0; i4 < size3; i4++) {
      VersionedPackage versionedPackage = (VersionedPackage) arrayList.get(i4);
      if (this.mDeletePackageHelper.deletePackageX(
                  versionedPackage.getPackageName(),
                  versionedPackage.getLongVersionCode(),
                  0,
                  2,
                  true)
              == 1
          && findPathForUuid.getUsableSpace() >= j) {
        return true;
      }
    }
    return false;
  }

  public SharedLibraryInfo getLatestStaticSharedLibraVersion(AndroidPackage androidPackage) {
    SharedLibraryInfo latestStaticSharedLibraVersionLPr;
    synchronized (this.mPm.mLock) {
      latestStaticSharedLibraVersionLPr = getLatestStaticSharedLibraVersionLPr(androidPackage);
    }
    return latestStaticSharedLibraVersionLPr;
  }

  public final SharedLibraryInfo getLatestStaticSharedLibraVersionLPr(
      AndroidPackage androidPackage) {
    WatchedLongSparseArray watchedLongSparseArray =
        (WatchedLongSparseArray)
            this.mSharedLibraries.get(androidPackage.getStaticSharedLibraryName());
    if (watchedLongSparseArray == null) {
      return null;
    }
    int size = watchedLongSparseArray.size();
    long j = -1;
    for (int i = 0; i < size; i++) {
      long keyAt = watchedLongSparseArray.keyAt(i);
      if (keyAt < androidPackage.getStaticSharedLibraryVersion()) {
        j = Math.max(j, keyAt);
      }
    }
    if (j >= 0) {
      return (SharedLibraryInfo) watchedLongSparseArray.get(j);
    }
    return null;
  }

  public PackageSetting getStaticSharedLibLatestVersionSetting(InstallRequest installRequest) {
    PackageSetting packageLPr;
    if (installRequest.getParsedPackage() == null) {
      return null;
    }
    synchronized (this.mPm.mLock) {
      SharedLibraryInfo latestStaticSharedLibraVersionLPr =
          getLatestStaticSharedLibraVersionLPr(installRequest.getParsedPackage());
      packageLPr =
          latestStaticSharedLibraVersionLPr != null
              ? this.mPm.mSettings.getPackageLPr(latestStaticSharedLibraVersionLPr.getPackageName())
              : null;
    }
    return packageLPr;
  }

  public final void applyDefiningSharedLibraryUpdateLPr(
      AndroidPackage androidPackage, SharedLibraryInfo sharedLibraryInfo, BiConsumer biConsumer) {
    if (AndroidPackageUtils.isLibrary(androidPackage)) {
      if (androidPackage.getSdkLibraryName() != null) {
        SharedLibraryInfo sharedLibraryInfo2 =
            getSharedLibraryInfo(
                androidPackage.getSdkLibraryName(), androidPackage.getSdkLibVersionMajor());
        if (sharedLibraryInfo2 != null) {
          biConsumer.accept(sharedLibraryInfo2, sharedLibraryInfo);
          return;
        }
        return;
      }
      if (androidPackage.getStaticSharedLibraryName() != null) {
        SharedLibraryInfo sharedLibraryInfo3 =
            getSharedLibraryInfo(
                androidPackage.getStaticSharedLibraryName(),
                androidPackage.getStaticSharedLibraryVersion());
        if (sharedLibraryInfo3 != null) {
          biConsumer.accept(sharedLibraryInfo3, sharedLibraryInfo);
          return;
        }
        return;
      }
      Iterator it = androidPackage.getLibraryNames().iterator();
      while (it.hasNext()) {
        SharedLibraryInfo sharedLibraryInfo4 = getSharedLibraryInfo((String) it.next(), -1L);
        if (sharedLibraryInfo4 != null) {
          biConsumer.accept(sharedLibraryInfo4, sharedLibraryInfo);
        }
      }
    }
  }

  public final void addSharedLibraryLPr(
      AndroidPackage androidPackage,
      Set set,
      SharedLibraryInfo sharedLibraryInfo,
      AndroidPackage androidPackage2,
      PackageSetting packageSetting) {
    if (sharedLibraryInfo.getPath() != null) {
      set.add(sharedLibraryInfo.getPath());
      return;
    }
    AndroidPackage androidPackage3 =
        (AndroidPackage) this.mPm.mPackages.get(sharedLibraryInfo.getPackageName());
    PackageSetting packageLPr =
        this.mPm.mSettings.getPackageLPr(sharedLibraryInfo.getPackageName());
    if (androidPackage2 == null
        || !androidPackage2.getPackageName().equals(sharedLibraryInfo.getPackageName())
        || (androidPackage3 != null
            && !androidPackage3.getPackageName().equals(androidPackage2.getPackageName()))) {
      androidPackage2 = androidPackage3;
      packageSetting = packageLPr;
    }
    if (androidPackage2 != null) {
      set.addAll(AndroidPackageUtils.getAllCodePaths(androidPackage2));
      applyDefiningSharedLibraryUpdateLPr(
          androidPackage,
          sharedLibraryInfo,
          new BiConsumer() { // from class:
            // com.android.server.pm.SharedLibrariesImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
              ((SharedLibraryInfo) obj).addDependency((SharedLibraryInfo) obj2);
            }
          });
      if (packageSetting != null) {
        set.addAll(packageSetting.getPkgState().getUsesLibraryFiles());
      }
    }
  }

  public void updateSharedLibraries(
      AndroidPackage androidPackage,
      PackageSetting packageSetting,
      AndroidPackage androidPackage2,
      PackageSetting packageSetting2,
      Map map) {
    ArrayList collectSharedLibraryInfos = collectSharedLibraryInfos(androidPackage, map, null);
    synchronized (this.mPm.mLock) {
      executeSharedLibrariesUpdateLPw(
          androidPackage,
          packageSetting,
          androidPackage2,
          packageSetting2,
          collectSharedLibraryInfos,
          this.mPm.mUserManager.getUserIds());
    }
  }

  public void executeSharedLibrariesUpdate(
      AndroidPackage androidPackage,
      PackageSetting packageSetting,
      AndroidPackage androidPackage2,
      PackageSetting packageSetting2,
      ArrayList arrayList,
      int[] iArr) {
    synchronized (this.mPm.mLock) {
      executeSharedLibrariesUpdateLPw(
          androidPackage, packageSetting, androidPackage2, packageSetting2, arrayList, iArr);
    }
  }

  public final void executeSharedLibrariesUpdateLPw(
      AndroidPackage androidPackage,
      PackageSetting packageSetting,
      AndroidPackage androidPackage2,
      PackageSetting packageSetting2,
      ArrayList arrayList,
      int[] iArr) {
    applyDefiningSharedLibraryUpdateLPr(
        androidPackage,
        null,
        new BiConsumer() { // from class:
          // com.android.server.pm.SharedLibrariesImpl$$ExternalSyntheticLambda0
          @Override // java.util.function.BiConsumer
          public final void accept(Object obj, Object obj2) {
            ((SharedLibraryInfo) obj).clearDependencies();
          }
        });
    if (arrayList != null) {
      packageSetting.getPkgState().setUsesLibraryInfos(arrayList);
      LinkedHashSet linkedHashSet = new LinkedHashSet();
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        addSharedLibraryLPr(
            androidPackage,
            linkedHashSet,
            (SharedLibraryInfo) it.next(),
            androidPackage2,
            packageSetting2);
      }
      packageSetting.setPkgStateLibraryFiles(linkedHashSet);
      int[] iArr2 = new int[iArr.length];
      int i = 0;
      for (int i2 = 0; i2 < iArr.length; i2++) {
        if (packageSetting.getInstalled(iArr[i2])) {
          iArr2[i] = iArr[i2];
          i++;
        }
      }
      Iterator it2 = arrayList.iterator();
      while (it2.hasNext()) {
        SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) it2.next();
        if (sharedLibraryInfo.isStatic()) {
          PackageSetting packageSettingForMutation =
              this.mPm.getPackageSettingForMutation(sharedLibraryInfo.getPackageName());
          if (packageSettingForMutation == null) {
            Slog.wtf("PackageManager", "Shared lib without setting: " + sharedLibraryInfo);
          } else {
            for (int i3 = 0; i3 < i; i3++) {
              packageSettingForMutation.setInstalled(true, iArr2[i3]);
            }
          }
        }
      }
      return;
    }
    packageSetting
        .getPkgState()
        .setUsesLibraryInfos(Collections.emptyList())
        .setUsesLibraryFiles(Collections.emptyList());
  }

  public static boolean hasString(List list, List list2) {
    if (list != null && list2 != null) {
      for (int size = list.size() - 1; size >= 0; size--) {
        for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
          if (((String) list2.get(size2)).equals(list.get(size))) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public ArrayList commitSharedLibraryChanges(
      AndroidPackage androidPackage, PackageSetting packageSetting, List list, Map map, int i) {
    if (ArrayUtils.isEmpty(list)) {
      return null;
    }
    synchronized (this.mPm.mLock) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        commitSharedLibraryInfoLPw((SharedLibraryInfo) it.next());
      }
      try {
        updateSharedLibraries(androidPackage, packageSetting, null, null, map);
      } catch (PackageManagerException e) {
        Slog.e("PackageManager", "updateSharedLibraries failed: ", e);
      }
      if ((i & 16) != 0) {
        return null;
      }
      return updateAllSharedLibrariesLPw(androidPackage, packageSetting, map);
    }
  }

  public ArrayList updateAllSharedLibrariesLPw(
      AndroidPackage androidPackage, PackageSetting packageSetting, Map map) {
    ArrayList arrayList;
    ArraySet arraySet;
    ArrayList arrayList2;
    if (androidPackage == null || packageSetting == null) {
      arrayList = null;
      arraySet = null;
      arrayList2 = null;
    } else {
      ArrayList arrayList3 = new ArrayList(1);
      arrayList3.add(Pair.create(androidPackage, packageSetting));
      arrayList2 = arrayList3;
      arrayList = null;
      arraySet = null;
    }
    do {
      Pair pair = arrayList2 == null ? null : (Pair) arrayList2.remove(0);
      AndroidPackage androidPackage2 = pair != null ? (AndroidPackage) pair.first : null;
      PackageSetting packageSetting2 = pair != null ? (PackageSetting) pair.second : null;
      for (int size = this.mPm.mPackages.size() - 1; size >= 0; size--) {
        AndroidPackage androidPackage3 = (AndroidPackage) this.mPm.mPackages.valueAt(size);
        PackageSetting packageLPr =
            this.mPm.mSettings.getPackageLPr(androidPackage3.getPackageName());
        if (androidPackage2 == null
            || hasString(androidPackage3.getUsesLibraries(), androidPackage2.getLibraryNames())
            || hasString(
                androidPackage3.getUsesOptionalLibraries(), androidPackage2.getLibraryNames())
            || ArrayUtils.contains(
                androidPackage3.getUsesStaticLibraries(),
                androidPackage2.getStaticSharedLibraryName())
            || ArrayUtils.contains(
                androidPackage3.getUsesSdkLibraries(), androidPackage2.getSdkLibraryName())) {
          if (arrayList == null) {
            arrayList = new ArrayList();
          }
          ArrayList arrayList4 = arrayList;
          arrayList4.add(androidPackage3);
          if (androidPackage2 != null) {
            if (arraySet == null) {
              arraySet = new ArraySet();
            }
            if (!arraySet.contains(androidPackage3.getPackageName())) {
              arraySet.add(androidPackage3.getPackageName());
              arrayList2.add(Pair.create(androidPackage3, packageLPr));
            }
          }
          ArraySet arraySet2 = arraySet;
          try {
            updateSharedLibraries(
                androidPackage3, packageLPr, androidPackage2, packageSetting2, map);
          } catch (PackageManagerException e) {
            if (!packageLPr.isSystem() || packageLPr.isUpdatedSystemApp()) {
              boolean isUpdatedSystemApp = packageLPr.isUpdatedSystemApp();
              synchronized (this.mPm.mInstallLock) {
                this.mDeletePackageHelper.deletePackageLIF(
                    androidPackage3.getPackageName(),
                    null,
                    true,
                    this.mPm.mUserManager.getUserIds(),
                    isUpdatedSystemApp ? 1 : 0,
                    null,
                    true);
              }
            }
            Slog.e("PackageManager", "updateAllSharedLibrariesLPw failed: " + e.getMessage());
          }
          arraySet = arraySet2;
          arrayList = arrayList4;
        }
      }
      if (arrayList2 == null) {
        break;
      }
    } while (arrayList2.size() > 0);
    return arrayList;
  }

  public void addBuiltInSharedLibraryLPw(SystemConfig.SharedLibraryEntry sharedLibraryEntry) {
    if (getSharedLibraryInfo(sharedLibraryEntry.name, -1L) != null) {
      return;
    }
    commitSharedLibraryInfoLPw(
        new SharedLibraryInfo(
            sharedLibraryEntry.filename,
            null,
            null,
            sharedLibraryEntry.name,
            -1L,
            0,
            new VersionedPackage("android", 0L),
            null,
            null,
            sharedLibraryEntry.isNative));
  }

  public void commitSharedLibraryInfoLPw(SharedLibraryInfo sharedLibraryInfo) {
    String name = sharedLibraryInfo.getName();
    WatchedLongSparseArray watchedLongSparseArray =
        (WatchedLongSparseArray) this.mSharedLibraries.get(name);
    if (watchedLongSparseArray == null) {
      watchedLongSparseArray = new WatchedLongSparseArray();
      this.mSharedLibraries.put(name, watchedLongSparseArray);
    }
    String packageName = sharedLibraryInfo.getDeclaringPackage().getPackageName();
    if (sharedLibraryInfo.getType() == 2) {
      this.mStaticLibsByDeclaringPackage.put(packageName, watchedLongSparseArray);
    }
    watchedLongSparseArray.put(sharedLibraryInfo.getLongVersion(), sharedLibraryInfo);
  }

  public boolean removeSharedLibrary(String str, long j) {
    int i;
    synchronized (this.mPm.mLock) {
      WatchedLongSparseArray watchedLongSparseArray =
          (WatchedLongSparseArray) this.mSharedLibraries.get(str);
      int i2 = 0;
      if (watchedLongSparseArray == null) {
        return false;
      }
      int indexOfKey = watchedLongSparseArray.indexOfKey(j);
      if (indexOfKey < 0) {
        return false;
      }
      SharedLibraryInfo sharedLibraryInfo =
          (SharedLibraryInfo) watchedLongSparseArray.valueAt(indexOfKey);
      Computer snapshotComputer = this.mPm.snapshotComputer();
      int[] userIds = this.mPm.mUserManager.getUserIds();
      int length = userIds.length;
      while (i2 < length) {
        int i3 = userIds[i2];
        int i4 = length;
        List packagesUsingSharedLibrary =
            snapshotComputer.getPackagesUsingSharedLibrary(sharedLibraryInfo, 0L, 1000, i3);
        if (packagesUsingSharedLibrary != null) {
          Iterator it = packagesUsingSharedLibrary.iterator();
          while (it.hasNext()) {
            PackageSetting packageLPr =
                this.mPm.mSettings.getPackageLPr(((VersionedPackage) it.next()).getPackageName());
            if (packageLPr != null) {
              i = i3;
              packageLPr.setOverlayPathsForLibrary(sharedLibraryInfo.getName(), null, i);
            } else {
              i = i3;
            }
            i3 = i;
          }
        }
        i2++;
        length = i4;
      }
      watchedLongSparseArray.remove(j);
      if (watchedLongSparseArray.size() <= 0) {
        this.mSharedLibraries.remove(str);
        if (sharedLibraryInfo.getType() == 2) {
          this.mStaticLibsByDeclaringPackage.remove(
              sharedLibraryInfo.getDeclaringPackage().getPackageName());
        }
      }
      return true;
    }
  }

  public List getAllowedSharedLibInfos(InstallRequest installRequest) {
    PackageSetting packageSetting;
    ParsedPackage parsedPackage = installRequest.getParsedPackage();
    if (installRequest.getSdkSharedLibraryInfo() == null
        && installRequest.getStaticSharedLibraryInfo() == null
        && installRequest.getDynamicSharedLibraryInfos() == null) {
      return null;
    }
    if (installRequest.getSdkSharedLibraryInfo() != null) {
      return Collections.singletonList(installRequest.getSdkSharedLibraryInfo());
    }
    if (installRequest.getStaticSharedLibraryInfo() != null) {
      return Collections.singletonList(installRequest.getStaticSharedLibraryInfo());
    }
    if (!((parsedPackage == null
            || !(installRequest.getScannedPackageSetting() != null
                && installRequest.getScannedPackageSetting().isSystem())
            || installRequest.getDynamicSharedLibraryInfos() == null)
        ? false
        : true)) {
      return null;
    }
    boolean z =
        installRequest.getScannedPackageSetting() != null
            && installRequest.getScannedPackageSetting().isUpdatedSystemApp();
    if (!z) {
      packageSetting = null;
    } else if (installRequest.getDisabledPackageSetting() == null) {
      packageSetting = installRequest.getScanRequestOldPackageSetting();
    } else {
      packageSetting = installRequest.getDisabledPackageSetting();
    }
    if (z
        && (packageSetting.getPkg() == null || packageSetting.getPkg().getLibraryNames() == null)) {
      Slog.w(
          "PackageManager",
          "Package "
              + parsedPackage.getPackageName()
              + " declares libraries that are not declared on the system image; skipping");
      return null;
    }
    ArrayList arrayList = new ArrayList(installRequest.getDynamicSharedLibraryInfos().size());
    for (SharedLibraryInfo sharedLibraryInfo : installRequest.getDynamicSharedLibraryInfos()) {
      String name = sharedLibraryInfo.getName();
      if (z && !packageSetting.getPkg().getLibraryNames().contains(name)) {
        Slog.w(
            "PackageManager",
            "Package "
                + parsedPackage.getPackageName()
                + " declares library "
                + name
                + " that is not declared on system image; skipping");
      } else {
        synchronized (this.mPm.mLock) {
          if (getSharedLibraryInfo(name, -1L) != null) {
            Slog.w(
                "PackageManager",
                "Package "
                    + parsedPackage.getPackageName()
                    + " declares library "
                    + name
                    + " that already exists; skipping");
          } else {
            arrayList.add(sharedLibraryInfo);
          }
        }
      }
    }
    return arrayList;
  }

  public ArrayList collectSharedLibraryInfos(AndroidPackage androidPackage, Map map, Map map2) {
    if (androidPackage == null) {
      return null;
    }
    PlatformCompat compatibility = this.mInjector.getCompatibility();
    ArrayList collectSharedLibraryInfos =
        androidPackage.getUsesLibraries().isEmpty()
            ? null
            : collectSharedLibraryInfos(
                androidPackage.getUsesLibraries(),
                null,
                null,
                androidPackage.getPackageName(),
                "shared",
                true,
                androidPackage.getTargetSdkVersion(),
                null,
                map,
                map2);
    if (!androidPackage.getUsesStaticLibraries().isEmpty()) {
      collectSharedLibraryInfos =
          collectSharedLibraryInfos(
              androidPackage.getUsesStaticLibraries(),
              androidPackage.getUsesStaticLibrariesVersions(),
              androidPackage.getUsesStaticLibrariesCertDigests(),
              androidPackage.getPackageName(),
              "static shared",
              true,
              androidPackage.getTargetSdkVersion(),
              collectSharedLibraryInfos,
              map,
              map2);
    }
    if (!androidPackage.getUsesOptionalLibraries().isEmpty()) {
      collectSharedLibraryInfos =
          collectSharedLibraryInfos(
              androidPackage.getUsesOptionalLibraries(),
              null,
              null,
              androidPackage.getPackageName(),
              "shared",
              false,
              androidPackage.getTargetSdkVersion(),
              collectSharedLibraryInfos,
              map,
              map2);
    }
    if (compatibility.isChangeEnabledInternal(
        142191088L, androidPackage.getPackageName(), androidPackage.getTargetSdkVersion())) {
      if (!androidPackage.getUsesNativeLibraries().isEmpty()) {
        collectSharedLibraryInfos =
            collectSharedLibraryInfos(
                androidPackage.getUsesNativeLibraries(),
                null,
                null,
                androidPackage.getPackageName(),
                "native shared",
                true,
                androidPackage.getTargetSdkVersion(),
                collectSharedLibraryInfos,
                map,
                map2);
      }
      if (!androidPackage.getUsesOptionalNativeLibraries().isEmpty()) {
        collectSharedLibraryInfos =
            collectSharedLibraryInfos(
                androidPackage.getUsesOptionalNativeLibraries(),
                null,
                null,
                androidPackage.getPackageName(),
                "native shared",
                false,
                androidPackage.getTargetSdkVersion(),
                collectSharedLibraryInfos,
                map,
                map2);
      }
    }
    return !androidPackage.getUsesSdkLibraries().isEmpty()
        ? collectSharedLibraryInfos(
            androidPackage.getUsesSdkLibraries(),
            androidPackage.getUsesSdkLibrariesVersionsMajor(),
            androidPackage.getUsesSdkLibrariesCertDigests(),
            androidPackage.getPackageName(),
            "sdk",
            true,
            androidPackage.getTargetSdkVersion(),
            collectSharedLibraryInfos,
            map,
            map2)
        : collectSharedLibraryInfos;
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Removed duplicated region for block: B:47:0x01e3  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final ArrayList collectSharedLibraryInfos(
      List list,
      long[] jArr,
      String[][] strArr,
      String str,
      String str2,
      boolean z,
      int i,
      ArrayList arrayList,
      Map map,
      Map map2) {
    SharedLibraryInfo sharedLibraryInfo;
    int i2;
    String[] computeSignaturesSha256Digests;
    SharedLibrariesImpl sharedLibrariesImpl = this;
    int size = list.size();
    int i3 = 0;
    ArrayList arrayList2 = arrayList;
    int i4 = 0;
    while (i4 < size) {
      String str3 = (String) list.get(i4);
      long j = jArr != null ? jArr[i4] : -1L;
      synchronized (sharedLibrariesImpl.mPm.mLock) {
        sharedLibraryInfo =
            SharedLibraryUtils.getSharedLibraryInfo(
                str3, j, sharedLibrariesImpl.mSharedLibraries, map2);
      }
      if (sharedLibraryInfo != null) {
        if (jArr != null && strArr != null) {
          if (sharedLibraryInfo.getLongVersion() != jArr[i4]) {
            throw new PackageManagerException(
                -9,
                "Package "
                    + str
                    + " requires unavailable "
                    + str2
                    + " library "
                    + str3
                    + " version "
                    + sharedLibraryInfo.getLongVersion()
                    + "; failing!");
          }
          AndroidPackage androidPackage =
              (AndroidPackage) map.get(sharedLibraryInfo.getPackageName());
          SigningDetails signingDetails =
              androidPackage == null ? null : androidPackage.getSigningDetails();
          if (signingDetails == null) {
            throw new PackageManagerException(
                -9, "Package " + str + " requires unavailable " + str2 + " library; failing!");
          }
          String[] strArr2 = strArr[i4];
          if (strArr2.length > 1) {
            if (i >= 27) {
              computeSignaturesSha256Digests =
                  PackageUtils.computeSignaturesSha256Digests(signingDetails.getSignatures());
            } else {
              computeSignaturesSha256Digests =
                  PackageUtils.computeSignaturesSha256Digests(
                      new Signature[] {signingDetails.getSignatures()[i3]});
            }
            if (strArr2.length != computeSignaturesSha256Digests.length) {
              throw new PackageManagerException(
                  -9,
                  "Package " + str + " requires differently signed " + str2 + " library; failing!");
            }
            Arrays.sort(computeSignaturesSha256Digests);
            Arrays.sort(strArr2);
            int length = computeSignaturesSha256Digests.length;
            int i5 = i3;
            while (i5 < length) {
              if (!computeSignaturesSha256Digests[i5].equalsIgnoreCase(strArr2[i5])) {
                throw new PackageManagerException(
                    -9,
                    "Package "
                        + str
                        + " requires differently signed "
                        + str2
                        + " library; failing!");
              }
              i5++;
              i3 = 0;
            }
          } else {
            boolean z2 = i3;
            try {
              boolean hasSha256Certificate =
                  signingDetails.hasSha256Certificate(HexEncoding.decode(strArr2[z2 ? 1 : 0], z2));
              i2 = z2;
              if (!hasSha256Certificate) {
                throw new PackageManagerException(
                    -9,
                    "Package "
                        + str
                        + " requires differently signed "
                        + str2
                        + " library; failing!");
              }
              if (arrayList2 == null) {
                arrayList2 = new ArrayList();
              }
              arrayList2.add(sharedLibraryInfo);
            } catch (IllegalArgumentException unused) {
              throw new PackageManagerException(
                  -130,
                  "Package "
                      + str
                      + " declares bad certificate digest for "
                      + str2
                      + " library "
                      + str3
                      + "; failing!");
            }
          }
        }
        i2 = i3;
        if (arrayList2 == null) {}
        arrayList2.add(sharedLibraryInfo);
      } else {
        if (z) {
          throw new PackageManagerException(
              -9,
              "Package "
                  + str
                  + " requires unavailable "
                  + str2
                  + " library "
                  + str3
                  + "; failing!");
        }
        i2 = i3;
      }
      i4++;
      i3 = i2;
      sharedLibrariesImpl = this;
    }
    return arrayList2;
  }

  @Override // com.android.server.pm.SharedLibrariesRead
  public void dump(PrintWriter printWriter, DumpState dumpState) {
    boolean isCheckIn = dumpState.isCheckIn();
    int size = this.mSharedLibraries.size();
    boolean z = false;
    for (int i = 0; i < size; i++) {
      WatchedLongSparseArray watchedLongSparseArray =
          (WatchedLongSparseArray)
              this.mSharedLibraries.get((String) this.mSharedLibraries.keyAt(i));
      if (watchedLongSparseArray != null) {
        int size2 = watchedLongSparseArray.size();
        for (int i2 = 0; i2 < size2; i2++) {
          SharedLibraryInfo sharedLibraryInfo =
              (SharedLibraryInfo) watchedLongSparseArray.valueAt(i2);
          if (!isCheckIn) {
            if (!z) {
              if (dumpState.onTitlePrinted()) {
                printWriter.println();
              }
              printWriter.println("Libraries:");
              z = true;
            }
            printWriter.print("  ");
          } else {
            printWriter.print("lib,");
          }
          printWriter.print(sharedLibraryInfo.getName());
          if (sharedLibraryInfo.isStatic()) {
            printWriter.print(" version=" + sharedLibraryInfo.getLongVersion());
          }
          if (!isCheckIn) {
            printWriter.print(" -> ");
          }
          if (sharedLibraryInfo.getPath() != null) {
            if (sharedLibraryInfo.isNative()) {
              printWriter.print(" (so) ");
            } else {
              printWriter.print(" (jar) ");
            }
            printWriter.print(sharedLibraryInfo.getPath());
          } else {
            printWriter.print(" (apk) ");
            printWriter.print(sharedLibraryInfo.getPackageName());
          }
          printWriter.println();
        }
      }
    }
  }

  @Override // com.android.server.pm.SharedLibrariesRead
  public void dumpProto(ProtoOutputStream protoOutputStream) {
    int size = this.mSharedLibraries.size();
    for (int i = 0; i < size; i++) {
      WatchedLongSparseArray watchedLongSparseArray =
          (WatchedLongSparseArray)
              this.mSharedLibraries.get((String) this.mSharedLibraries.keyAt(i));
      if (watchedLongSparseArray != null) {
        int size2 = watchedLongSparseArray.size();
        for (int i2 = 0; i2 < size2; i2++) {
          SharedLibraryInfo sharedLibraryInfo =
              (SharedLibraryInfo) watchedLongSparseArray.valueAt(i2);
          long start = protoOutputStream.start(2246267895811L);
          protoOutputStream.write(1138166333441L, sharedLibraryInfo.getName());
          boolean z = sharedLibraryInfo.getPath() != null;
          protoOutputStream.write(1133871366146L, z);
          if (z) {
            protoOutputStream.write(1138166333443L, sharedLibraryInfo.getPath());
          } else {
            protoOutputStream.write(1138166333444L, sharedLibraryInfo.getPackageName());
          }
          protoOutputStream.end(start);
        }
      }
    }
  }
}
