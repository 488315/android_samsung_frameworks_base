package com.android.server.om;

import android.content.om.CriticalOverlayInfo;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.content.pm.UserPackage;
import android.content.pm.overlay.OverlayPaths;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.os.Environment;
import android.os.FabricatedOverlayInfo;
import android.os.FabricatedOverlayInternal;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.content.om.OverlayConfig;
import com.android.internal.util.CollectionUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.AndroidPackageSplit;
import com.android.server.pm.pkg.PackageState;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class OverlayManagerServiceImpl {
  public final String[] mDefaultOverlays;
  public final IdmapManager mIdmapManager;
  public final OverlayConfig mOverlayConfig;
  public IOverlayManagerExt mOverlayManagerExt;
  public final PackageManagerHelper mPackageManager;
  public final OverlayManagerSettings mSettings;

  public final boolean mustReinitializeOverlay(
      AndroidPackage androidPackage, OverlayInfo overlayInfo) {
    boolean isPackageConfiguredMutable;
    if (overlayInfo != null
        && Objects.equals(androidPackage.getOverlayTarget(), overlayInfo.targetPackageName)
        && Objects.equals(
            androidPackage.getOverlayTargetOverlayableName(), overlayInfo.targetOverlayableName)
        && !overlayInfo.isFabricated
        && (isPackageConfiguredMutable = isPackageConfiguredMutable(androidPackage))
            == overlayInfo.isMutable) {
      return (isPackageConfiguredMutable
              || isPackageConfiguredEnabled(androidPackage) == overlayInfo.isEnabled())
          ? false
          : true;
    }
    return true;
  }

  public final boolean mustReinitializeOverlay(
      FabricatedOverlayInfo fabricatedOverlayInfo, OverlayInfo overlayInfo) {
    return (overlayInfo != null
            && Objects.equals(
                fabricatedOverlayInfo.targetPackageName, overlayInfo.targetPackageName)
            && Objects.equals(
                fabricatedOverlayInfo.targetOverlayable, overlayInfo.targetOverlayableName))
        ? false
        : true;
  }

  public OverlayManagerServiceImpl(
      PackageManagerHelper packageManagerHelper,
      IdmapManager idmapManager,
      OverlayManagerSettings overlayManagerSettings,
      OverlayConfig overlayConfig,
      String[] strArr) {
    this.mPackageManager = packageManagerHelper;
    this.mIdmapManager = idmapManager;
    this.mSettings = overlayManagerSettings;
    this.mOverlayConfig = overlayConfig;
    this.mDefaultOverlays = strArr;
  }

  public ArraySet updateOverlaysForUser(int i) {
    ArraySet arraySet = new ArraySet();
    final ArrayMap initializeForUser = this.mPackageManager.initializeForUser(i);
    CollectionUtils.addAll(
        arraySet,
        removeOverlaysForUser(
            new Predicate() { // from class:
                              // com.android.server.om.OverlayManagerServiceImpl$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$updateOverlaysForUser$0;
                lambda$updateOverlaysForUser$0 =
                    OverlayManagerServiceImpl.lambda$updateOverlaysForUser$0(
                        initializeForUser, (OverlayInfo) obj);
                return lambda$updateOverlaysForUser$0;
              }
            },
            i));
    ArraySet arraySet2 = new ArraySet();
    Iterator it = initializeForUser.values().iterator();
    while (it.hasNext()) {
      AndroidPackage androidPackage = ((PackageState) it.next()).getAndroidPackage();
      String overlayTarget = androidPackage == null ? null : androidPackage.getOverlayTarget();
      if (!TextUtils.isEmpty(overlayTarget)) {
        arraySet2.add(overlayTarget);
      }
    }
    int size = initializeForUser.size();
    for (int i2 = 0; i2 < size; i2++) {
      PackageState packageState = (PackageState) initializeForUser.valueAt(i2);
      AndroidPackage androidPackage2 = packageState.getAndroidPackage();
      if (androidPackage2 != null) {
        String packageName = packageState.getPackageName();
        try {
          CollectionUtils.addAll(arraySet, updatePackageOverlays(androidPackage2, i, 0));
          if (arraySet2.contains(packageName)) {
            arraySet.add(UserPackage.of(i, packageName));
          }
        } catch (OperationFailedException e) {
          Slog.e(
              "OverlayManager",
              "failed to initialize overlays of '" + packageName + "' for user " + i + "",
              e);
        }
      }
    }
    for (FabricatedOverlayInfo fabricatedOverlayInfo : getFabricatedOverlayInfos()) {
      try {
        if (MaintenanceModeManager.isInMaintenanceMode()
            && (SemSamsungThemeUtils.isColorThemeOverlay(fabricatedOverlayInfo)
                || SemSamsungThemeUtils.isDynamicColorOverlay(fabricatedOverlayInfo))) {
          if (SemSamsungThemeUtils.shouldBeDisabledInMaintenanceMode(fabricatedOverlayInfo)
              && isColorThemeApplied()) {
            disableOverlayInMaintenanceMode(fabricatedOverlayInfo);
          }
        } else {
          CollectionUtils.addAll(arraySet, registerFabricatedOverlay(fabricatedOverlayInfo, i));
        }
      } catch (OperationFailedException e2) {
        Slog.e(
            "OverlayManager",
            "failed to initialize fabricated overlay of '"
                + fabricatedOverlayInfo.path
                + "' for user "
                + i
                + "",
            e2);
      }
    }
    ArraySet arraySet3 = new ArraySet();
    ArrayMap overlaysForUser = this.mSettings.getOverlaysForUser(i);
    int size2 = overlaysForUser.size();
    for (int i3 = 0; i3 < size2; i3++) {
      List list = (List) overlaysForUser.valueAt(i3);
      int size3 = list != null ? list.size() : 0;
      for (int i4 = 0; i4 < size3; i4++) {
        OverlayInfo overlayInfo = (OverlayInfo) list.get(i4);
        if (overlayInfo.isEnabled()) {
          arraySet3.add(overlayInfo.category);
        }
      }
    }
    for (String str : this.mDefaultOverlays) {
      try {
        OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
        OverlayInfo overlayInfo2 = this.mSettings.getOverlayInfo(overlayIdentifier, i);
        if (!arraySet3.contains(overlayInfo2.category)) {
          Slog.w(
              "OverlayManager",
              "Enabling default overlay '"
                  + str
                  + "' for target '"
                  + overlayInfo2.targetPackageName
                  + "' in category '"
                  + overlayInfo2.category
                  + "' for user "
                  + i);
          this.mSettings.setEnabled(overlayIdentifier, i, true);
          if (updateState(overlayInfo2, i, 0)) {
            CollectionUtils.add(
                arraySet, UserPackage.of(overlayInfo2.userId, overlayInfo2.targetPackageName));
          }
        }
      } catch (OverlayManagerSettings.BadKeyException e3) {
        Slog.e("OverlayManager", "Failed to set default overlay '" + str + "' for user " + i, e3);
      }
    }
    cleanStaleResourceCache();
    return arraySet;
  }

  public static /* synthetic */ boolean lambda$updateOverlaysForUser$0(
      ArrayMap arrayMap, OverlayInfo overlayInfo) {
    return (arrayMap.containsKey(overlayInfo.packageName)
            || OverlayInfoExt.isOverlayInfoExt(overlayInfo))
        ? false
        : true;
  }

  public final void disableOverlayInMaintenanceMode(FabricatedOverlayInfo fabricatedOverlayInfo) {
    try {
      setEnabled(
          new OverlayIdentifier(
              fabricatedOverlayInfo.packageName, fabricatedOverlayInfo.overlayName),
          false,
          0);
    } catch (OperationFailedException e) {
      throw new RuntimeException(e);
    }
  }

  public void onUserRemoved(int i) {
    Slog.d("OverlayManager", "onUserRemoved userId=" + i);
    this.mSettings.removeUser(i);
  }

  public Set onPackageAdded(String str, int i) {
    ArraySet arraySet = new ArraySet();
    arraySet.add(UserPackage.of(i, str));
    arraySet.addAll(reconcileSettingsForPackage(str, i, 0));
    return arraySet;
  }

  public Set onPackageChanged(String str, int i) {
    return reconcileSettingsForPackage(str, i, 8);
  }

  public Set onPackageReplacing(String str, boolean z, int i) {
    return reconcileSettingsForPackage(str, i, z ? 6 : 2);
  }

  public Set onPackageReplaced(String str, int i) {
    return reconcileSettingsForPackage(str, i, 0);
  }

  public Set onPackageRemoved(String str, int i) {
    return onPackageRemoved(str, i, false);
  }

  public Set onPackageRemoved(final String str, int i, boolean z) {
    Slog.d(
        "OverlayManager",
        "onPackageRemoved: pkgName = ["
            + str
            + "], userId = ["
            + i
            + "], packageHidden = ["
            + z
            + "]");
    return CollectionUtils.addAll(
        updateOverlaysForTarget(str, i, z ? 16 : 0),
        removeOverlaysForUser(
            new Predicate() { // from class:
                              // com.android.server.om.OverlayManagerServiceImpl$$ExternalSyntheticLambda1
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$onPackageRemoved$2;
                lambda$onPackageRemoved$2 =
                    OverlayManagerServiceImpl.lambda$onPackageRemoved$2(str, (OverlayInfo) obj);
                return lambda$onPackageRemoved$2;
              }
            },
            i));
  }

  public static /* synthetic */ boolean lambda$onPackageRemoved$2(
      String str, OverlayInfo overlayInfo) {
    return str.equals(overlayInfo.packageName) && !OverlayInfoExt.isOverlayInfoExt(overlayInfo);
  }

  public final Set removeOverlaysForUser(final Predicate predicate, final int i) {
    List removeIf =
        this.mSettings.removeIf(
            new Predicate() { // from class:
                              // com.android.server.om.OverlayManagerServiceImpl$$ExternalSyntheticLambda2
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$removeOverlaysForUser$4;
                lambda$removeOverlaysForUser$4 =
                    OverlayManagerServiceImpl.lambda$removeOverlaysForUser$4(
                        i, predicate, (OverlayInfo) obj);
                return lambda$removeOverlaysForUser$4;
              }
            });
    Set emptySet = Collections.emptySet();
    int size = removeIf.size();
    for (int i2 = 0; i2 < size; i2++) {
      OverlayInfo overlayInfo = (OverlayInfo) removeIf.get(i2);
      emptySet = CollectionUtils.add(emptySet, UserPackage.of(i, overlayInfo.targetPackageName));
      removeIdmapIfPossible(overlayInfo);
    }
    return emptySet;
  }

  public static /* synthetic */ boolean lambda$removeOverlaysForUser$4(
      int i, Predicate predicate, OverlayInfo overlayInfo) {
    return i == overlayInfo.userId && predicate.test(overlayInfo);
  }

  public final Set updateOverlaysForTarget(String str, int i, int i2) {
    boolean remove;
    Slog.i(
        "OverlayManager",
        "updateOverlaysForTarget() called with: targetPackage = ["
            + str
            + "], userId = ["
            + i
            + "], flags = ["
            + i2
            + "]");
    List overlaysForTarget = this.mSettings.getOverlaysForTarget(str, i);
    int size = overlaysForTarget.size();
    boolean z = false;
    for (int i3 = 0; i3 < size; i3++) {
      OverlayInfo overlayInfo = (OverlayInfo) overlaysForTarget.get(i3);
      try {
        remove = updateState(overlayInfo, i, i2);
      } catch (OverlayManagerSettings.BadKeyException e) {
        Slog.e("OverlayManager", "failed to update settings", e);
        remove = this.mSettings.remove(overlayInfo.getOverlayIdentifier(), i);
      }
      z |= remove;
    }
    if (!z) {
      return Collections.emptySet();
    }
    return Set.of(UserPackage.of(i, str));
  }

  public final Set updatePackageOverlays(AndroidPackage androidPackage, int i, int i2) {
    if (androidPackage.getOverlayTarget() == null) {
      return Collections.emptySet();
    }
    Set emptySet = Collections.emptySet();
    OverlayIdentifier overlayIdentifier = new OverlayIdentifier(androidPackage.getPackageName());
    int packageConfiguredPriority = getPackageConfiguredPriority(androidPackage);
    try {
      OverlayInfo nullableOverlayInfo = this.mSettings.getNullableOverlayInfo(overlayIdentifier, i);
      if (mustReinitializeOverlay(androidPackage, nullableOverlayInfo)) {
        if (nullableOverlayInfo != null) {
          emptySet =
              CollectionUtils.add(
                  emptySet, UserPackage.of(i, nullableOverlayInfo.targetPackageName));
        }
        nullableOverlayInfo =
            this.mSettings.init(
                overlayIdentifier,
                i,
                androidPackage.getOverlayTarget(),
                androidPackage.getOverlayTargetOverlayableName(),
                ((AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath(),
                isPackageConfiguredMutable(androidPackage),
                isPackageConfiguredEnabled(androidPackage),
                getPackageConfiguredPriority(androidPackage),
                androidPackage.getOverlayCategory(),
                false);
      } else if (packageConfiguredPriority != nullableOverlayInfo.priority) {
        this.mSettings.setPriority(overlayIdentifier, i, packageConfiguredPriority);
        emptySet =
            CollectionUtils.add(emptySet, UserPackage.of(i, nullableOverlayInfo.targetPackageName));
      }
      return updateState(nullableOverlayInfo, i, i2)
          ? CollectionUtils.add(emptySet, UserPackage.of(i, nullableOverlayInfo.targetPackageName))
          : emptySet;
    } catch (OverlayManagerSettings.BadKeyException e) {
      throw new OperationFailedException("failed to update settings", e);
    }
  }

  public final Set reconcileSettingsForPackage(String str, int i, int i2) {
    Slog.d("OverlayManager", "reconcileSettingsForPackage pkgName=" + str + " userId=" + i);
    Set addAll =
        CollectionUtils.addAll(Collections.emptySet(), updateOverlaysForTarget(str, i, i2));
    PackageState packageStateForUser = this.mPackageManager.getPackageStateForUser(str, i);
    AndroidPackage androidPackage =
        packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
    if (androidPackage == null) {
      return onPackageRemoved(str, i);
    }
    return CollectionUtils.addAll(addAll, updatePackageOverlays(androidPackage, i, i2));
  }

  public OverlayInfo getOverlayInfo(OverlayIdentifier overlayIdentifier, int i) {
    try {
      return this.mSettings.getOverlayInfo(overlayIdentifier, i);
    } catch (OverlayManagerSettings.BadKeyException unused) {
      return null;
    }
  }

  public List getOverlayInfosForTarget(String str, int i) {
    return this.mSettings.getOverlaysForTarget(str, i);
  }

  public Map getOverlaysForUser(int i) {
    return this.mSettings.getOverlaysForUser(i);
  }

  public Set setEnabled(OverlayIdentifier overlayIdentifier, boolean z, int i) {
    Slog.d(
        "OverlayManager",
        String.format(
            "setEnabled overlay=%s enable=%s userId=%d",
            overlayIdentifier, Boolean.valueOf(z), Integer.valueOf(i)));
    try {
      OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
      if (!overlayInfo.isMutable) {
        throw new OperationFailedException("cannot enable immutable overlay packages in runtime");
      }
      if (updateState(overlayInfo, i, 0) | this.mSettings.setEnabled(overlayIdentifier, i, z)) {
        return Set.of(UserPackage.of(i, overlayInfo.targetPackageName));
      }
      return Set.of();
    } catch (OverlayManagerSettings.BadKeyException e) {
      throw new OperationFailedException("failed to update settings", e);
    }
  }

  public Optional setEnabledExclusive(OverlayIdentifier overlayIdentifier, boolean z, int i) {
    Slog.d(
        "OverlayManager",
        String.format(
            "setEnabledExclusive overlay=%s withinCategory=%s userId=%d",
            overlayIdentifier, Boolean.valueOf(z), Integer.valueOf(i)));
    try {
      OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
      if (!overlayInfo.isMutable) {
        throw new OperationFailedException("cannot enable immutable overlay packages in runtime");
      }
      List overlayInfosForTarget = getOverlayInfosForTarget(overlayInfo.targetPackageName, i);
      overlayInfosForTarget.remove(overlayInfo);
      boolean z2 = false;
      for (int i2 = 0; i2 < overlayInfosForTarget.size(); i2++) {
        OverlayInfo overlayInfo2 = (OverlayInfo) overlayInfosForTarget.get(i2);
        OverlayIdentifier overlayIdentifier2 = overlayInfo2.getOverlayIdentifier();
        if (overlayInfo2.isMutable
            && (!z || Objects.equals(overlayInfo2.category, overlayInfo.category))) {
          z2 =
              z2
                  | this.mSettings.setEnabled(overlayIdentifier2, i, false)
                  | updateState(overlayInfo2, i, 0);
        }
      }
      if (updateState(overlayInfo, i, 0)
          | this.mSettings.setEnabled(overlayIdentifier, i, true)
          | z2) {
        return Optional.of(UserPackage.of(i, overlayInfo.targetPackageName));
      }
      return Optional.empty();
    } catch (OverlayManagerSettings.BadKeyException e) {
      throw new OperationFailedException("failed to update settings", e);
    }
  }

  public Set registerFabricatedOverlay(FabricatedOverlayInternal fabricatedOverlayInternal) {
    if (FrameworkParsingPackageUtils.validateName(
            fabricatedOverlayInternal.overlayName, false, true)
        != null) {
      throw new OperationFailedException(
          "overlay name can only consist of alphanumeric characters, '_', and '.'");
    }
    FabricatedOverlayInfo createFabricatedOverlay =
        this.mIdmapManager.createFabricatedOverlay(fabricatedOverlayInternal);
    if (createFabricatedOverlay == null) {
      throw new OperationFailedException("failed to create fabricated overlay");
    }
    ArraySet arraySet = new ArraySet();
    for (int i : this.mSettings.getUsers()) {
      arraySet.addAll(registerFabricatedOverlay(createFabricatedOverlay, i));
    }
    return arraySet;
  }

  public final Set registerFabricatedOverlay(FabricatedOverlayInfo fabricatedOverlayInfo, int i) {
    ArraySet arraySet;
    boolean z;
    boolean z2;
    OverlayIdentifier overlayIdentifier =
        new OverlayIdentifier(fabricatedOverlayInfo.packageName, fabricatedOverlayInfo.overlayName);
    ArraySet arraySet2 = new ArraySet();
    OverlayInfo nullableOverlayInfo = this.mSettings.getNullableOverlayInfo(overlayIdentifier, i);
    if (nullableOverlayInfo != null && !nullableOverlayInfo.isFabricated) {
      throw new OperationFailedException(
          "non-fabricated overlay with name '"
              + nullableOverlayInfo.overlayName
              + "' already present in '"
              + nullableOverlayInfo.packageName
              + "'");
    }
    try {
      if (mustReinitializeOverlay(fabricatedOverlayInfo, nullableOverlayInfo)) {
        if (nullableOverlayInfo != null) {
          arraySet2.add(UserPackage.of(i, nullableOverlayInfo.targetPackageName));
        }
        String str = fabricatedOverlayInfo.overlayName;
        if (str == null || !str.startsWith("SemWT_")) {
          z2 = false;
        } else {
          OverlayInfo nullableOverlayInfo2 =
              this.mSettings.getNullableOverlayInfo(overlayIdentifier, 0);
          z2 = nullableOverlayInfo2 != null && nullableOverlayInfo2.isEnabled();
        }
        arraySet = arraySet2;
        z = true;
        nullableOverlayInfo =
            this.mSettings.init(
                overlayIdentifier,
                i,
                fabricatedOverlayInfo.targetPackageName,
                fabricatedOverlayInfo.targetOverlayable,
                fabricatedOverlayInfo.path,
                true,
                z2,
                Integer.MAX_VALUE,
                null,
                true);
      } else {
        arraySet = arraySet2;
        z = true;
        this.mSettings.setBaseCodePath(overlayIdentifier, i, fabricatedOverlayInfo.path);
      }
      if (!MaintenanceModeManager.isInMaintenanceMode()
          && SemSamsungThemeUtils.shouldBeDisabledInMaintenanceMode(fabricatedOverlayInfo)
          && isColorThemeApplied()) {
        setEnabled(overlayIdentifier, z, i);
      }
      if (!updateState(nullableOverlayInfo, i, 0)) {
        return arraySet;
      }
      ArraySet arraySet3 = arraySet;
      arraySet3.add(UserPackage.of(i, nullableOverlayInfo.targetPackageName));
      return arraySet3;
    } catch (OverlayManagerSettings.BadKeyException e) {
      throw new OperationFailedException("failed to update settings", e);
    }
  }

  public Set unregisterFabricatedOverlay(OverlayIdentifier overlayIdentifier) {
    ArraySet arraySet = new ArraySet();
    for (int i : this.mSettings.getUsers()) {
      arraySet.addAll(unregisterFabricatedOverlay(overlayIdentifier, i));
    }
    return arraySet;
  }

  public final Set unregisterFabricatedOverlay(OverlayIdentifier overlayIdentifier, int i) {
    OverlayInfo nullableOverlayInfo = this.mSettings.getNullableOverlayInfo(overlayIdentifier, i);
    if (nullableOverlayInfo != null) {
      this.mSettings.remove(overlayIdentifier, i);
      if (nullableOverlayInfo.isEnabled()) {
        return Set.of(UserPackage.of(i, nullableOverlayInfo.targetPackageName));
      }
    }
    return Set.of();
  }

  public final void cleanStaleResourceCache() {
    Set allBaseCodePaths = this.mSettings.getAllBaseCodePaths();
    for (FabricatedOverlayInfo fabricatedOverlayInfo :
        this.mIdmapManager.getFabricatedOverlayInfos()) {
      if (!allBaseCodePaths.contains(fabricatedOverlayInfo.path)) {
        this.mIdmapManager.deleteFabricatedOverlay(fabricatedOverlayInfo.path);
      }
    }
  }

  public final List getFabricatedOverlayInfos() {
    final Set allBaseCodePaths = this.mSettings.getAllBaseCodePaths();
    ArrayList arrayList = new ArrayList(this.mIdmapManager.getFabricatedOverlayInfos());
    arrayList.removeIf(
        new Predicate() { // from class:
                          // com.android.server.om.OverlayManagerServiceImpl$$ExternalSyntheticLambda3
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$getFabricatedOverlayInfos$5;
            lambda$getFabricatedOverlayInfos$5 =
                OverlayManagerServiceImpl.lambda$getFabricatedOverlayInfos$5(
                    allBaseCodePaths, (FabricatedOverlayInfo) obj);
            return lambda$getFabricatedOverlayInfos$5;
          }
        });
    return arrayList;
  }

  public static /* synthetic */ boolean lambda$getFabricatedOverlayInfos$5(
      Set set, FabricatedOverlayInfo fabricatedOverlayInfo) {
    return !set.contains(fabricatedOverlayInfo.path);
  }

  public final boolean isPackageConfiguredMutable(AndroidPackage androidPackage) {
    return this.mOverlayConfig.isMutable(androidPackage.getPackageName());
  }

  public final int getPackageConfiguredPriority(AndroidPackage androidPackage) {
    return this.mOverlayConfig.getPriority(androidPackage.getPackageName());
  }

  public final boolean isPackageConfiguredEnabled(AndroidPackage androidPackage) {
    return this.mOverlayConfig.isEnabled(androidPackage.getPackageName());
  }

  public void setOverlayManagerExt(IOverlayManagerExt iOverlayManagerExt) {
    this.mOverlayManagerExt = iOverlayManagerExt;
  }

  public Optional setPriority(
      OverlayIdentifier overlayIdentifier, OverlayIdentifier overlayIdentifier2, int i) {
    try {
      Slog.d(
          "OverlayManager",
          "setPriority overlay="
              + overlayIdentifier
              + " newParentOverlay="
              + overlayIdentifier2
              + " userId="
              + i);
      OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
      if (!overlayInfo.isMutable) {
        throw new OperationFailedException(
            "cannot change priority of an immutable overlay package at runtime");
      }
      if (this.mSettings.setPriority(overlayIdentifier, overlayIdentifier2, i)) {
        return Optional.of(UserPackage.of(i, overlayInfo.targetPackageName));
      }
      return Optional.empty();
    } catch (OverlayManagerSettings.BadKeyException e) {
      throw new OperationFailedException("failed to update settings", e);
    }
  }

  public Set setHighestPriority(OverlayIdentifier overlayIdentifier, int i) {
    try {
      Slog.d("OverlayManager", "setHighestPriority overlay=" + overlayIdentifier + " userId=" + i);
      OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
      if (!overlayInfo.isMutable) {
        throw new OperationFailedException(
            "cannot change priority of an immutable overlay package at runtime");
      }
      if (this.mSettings.setHighestPriority(overlayIdentifier, i)) {
        return Set.of(UserPackage.of(i, overlayInfo.targetPackageName));
      }
      return Set.of();
    } catch (OverlayManagerSettings.BadKeyException e) {
      throw new OperationFailedException("failed to update settings", e);
    }
  }

  public Optional setLowestPriority(OverlayIdentifier overlayIdentifier, int i) {
    try {
      Slog.d(
          "OverlayManager", "setLowestPriority packageName=" + overlayIdentifier + " userId=" + i);
      OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
      if (!overlayInfo.isMutable) {
        throw new OperationFailedException(
            "cannot change priority of an immutable overlay package at runtime");
      }
      if (this.mSettings.setLowestPriority(overlayIdentifier, i)) {
        return Optional.of(UserPackage.of(i, overlayInfo.targetPackageName));
      }
      return Optional.empty();
    } catch (OverlayManagerSettings.BadKeyException e) {
      throw new OperationFailedException("failed to update settings", e);
    }
  }

  public void dump(PrintWriter printWriter, DumpState dumpState) {
    OverlayIdentifier overlayIdentifier;
    OverlayInfo nullableOverlayInfo;
    Pair pair =
        (dumpState.getPackageName() == null
                || (nullableOverlayInfo =
                        this.mSettings.getNullableOverlayInfo(
                            (overlayIdentifier =
                                new OverlayIdentifier(
                                    dumpState.getPackageName(), dumpState.getOverlayName())),
                            0))
                    == null)
            ? null
            : new Pair(overlayIdentifier, nullableOverlayInfo.baseCodePath);
    this.mSettings.dump(printWriter, dumpState);
    if (dumpState.getField() == null) {
      for (Pair pair2 :
          pair != null ? Set.of(pair) : this.mSettings.getAllIdentifiersAndBaseCodePaths()) {
        printWriter.println("IDMAP OF " + pair2.first);
        String dumpIdmap = this.mIdmapManager.dumpIdmap((String) pair2.second);
        if (dumpIdmap != null) {
          printWriter.println(dumpIdmap);
        } else {
          OverlayInfo nullableOverlayInfo2 =
              this.mSettings.getNullableOverlayInfo((OverlayIdentifier) pair2.first, 0);
          printWriter.println(
              (nullableOverlayInfo2 == null || this.mIdmapManager.idmapExists(nullableOverlayInfo2))
                  ? "<internal error>"
                  : "<missing idmap>");
        }
      }
    }
    if (pair == null) {
      printWriter.println(
          "Default overlays: "
              + TextUtils.join(KnoxVpnFirewallHelper.DELIMITER, this.mDefaultOverlays));
    }
    if (dumpState.getPackageName() == null) {
      this.mOverlayConfig.dump(printWriter);
    }
    dumpFile(printWriter, getDataFile("log", "lom_log.txt"), "lom_log.txt", 0);
    for (int i : this.mSettings.getUsers()) {
      dumpFile(
          printWriter,
          getDataFile(
              getPathForUser("/overlays/current_locale_apks/locale_preferences_%s/", i),
              "current_locale_overlays"),
          "current_locale_overlays",
          i);
    }
    dumpFile(
        printWriter,
        getDataFile(
            getPathForUser("/overlays/current_locale_apks/locale_preferences_%s/", 0),
            "locale_overlay_preferences.xml"),
        "locale_overlay_preferences.xml",
        0);
  }

  public static String getPathForUser(String str, int i) {
    return String.format(str, Integer.valueOf(i));
  }

  public static File getDataFile(String str, String str2) {
    return new File(new File(Environment.getDataDirectory(), str), str2);
  }

  public String[] getDefaultOverlayPackages() {
    return this.mDefaultOverlays;
  }

  public void removeIdmapForOverlay(OverlayIdentifier overlayIdentifier, int i) {
    try {
      removeIdmapIfPossible(this.mSettings.getOverlayInfo(overlayIdentifier, i));
    } catch (OverlayManagerSettings.BadKeyException e) {
      throw new OperationFailedException("failed to update settings", e);
    }
  }

  public OverlayPaths getEnabledOverlayPaths(String str, int i, boolean z) {
    List overlaysForTarget = this.mSettings.getOverlaysForTarget(str, i);
    OverlayPaths.Builder builder = new OverlayPaths.Builder();
    int size = overlaysForTarget.size();
    ArrayList<OverlayInfo> arrayList = new ArrayList();
    for (int i2 = 0; i2 < size; i2++) {
      OverlayInfo overlayInfo = (OverlayInfo) overlaysForTarget.get(i2);
      if (overlayInfo.isEnabled() && (z || overlayInfo.isMutable)) {
        if (overlayInfo.isFabricated()) {
          if (overlayInfo.overlayName.startsWith("ThemePark_")) {
            arrayList.add(overlayInfo);
          } else {
            builder.addNonApkPath(overlayInfo.baseCodePath);
          }
        } else if (OverlayInfoExt.isOverlayInfoExtOfCategory(overlayInfo, 2)) {
          arrayList.add(overlayInfo);
        } else {
          builder.addApkPath(overlayInfo.baseCodePath);
        }
      }
    }
    for (OverlayInfo overlayInfo2 : arrayList) {
      if (overlayInfo2.isFabricated) {
        builder.addNonApkPath(overlayInfo2.baseCodePath);
      } else {
        builder.addApkPath(overlayInfo2.baseCodePath);
      }
    }
    return builder.build();
  }

  /* JADX WARN: Removed duplicated region for block: B:56:0x0175  */
  /* JADX WARN: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean updateState(CriticalOverlayInfo criticalOverlayInfo, int i, int i2) {
    OverlayInfo overlayInfo;
    String str;
    int i3;
    int state;
    int calculateNewState;
    OverlayInfo nullableOverlayInfo;
    OverlayIdentifier overlayIdentifier = criticalOverlayInfo.getOverlayIdentifier();
    PackageState packageStateForUser =
        this.mPackageManager.getPackageStateForUser(criticalOverlayInfo.getTargetPackageName(), i);
    AndroidPackage androidPackage =
        packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
    PackageState packageStateForUser2 =
        this.mPackageManager.getPackageStateForUser(criticalOverlayInfo.getPackageName(), i);
    AndroidPackage androidPackage2 =
        packageStateForUser2 == null ? null : packageStateForUser2.getAndroidPackage();
    boolean z =
        androidPackage2 != null
            && androidPackage2.getPackageName().startsWith("com.samsung.themedesigner")
            && SemSamsungThemeUtils.isValidThemeParkOverlay(
                androidPackage2, packageStateForUser2.getLastUpdateTime());
    boolean z2 = androidPackage2 == null || z;
    Slog.i(
        "OverlayManager",
        "Overlay " + criticalOverlayInfo.getPackageName() + " handled by Ext ? " + z2);
    if (z2) {
      if (this.mOverlayManagerExt != null) {
        if (z
            && (nullableOverlayInfo =
                    this.mSettings.getNullableOverlayInfo(
                        criticalOverlayInfo.getOverlayIdentifier(), i))
                != null
            && OverlayInfoExt.initFromInfo(nullableOverlayInfo) == null) {
          return false;
        }
        int handleStateUpdate =
            this.mOverlayManagerExt.handleStateUpdate(androidPackage, criticalOverlayInfo, i, i2);
        if (handleStateUpdate != 0) {
          return (handleStateUpdate & 2) != 0;
        }
      }
      Slog.e("OverlayManager", "Removing package - " + overlayIdentifier + " " + i2);
      removeIdmapIfPossible(this.mSettings.getOverlayInfo(overlayIdentifier, i));
      return this.mSettings.remove(overlayIdentifier, i);
    }
    boolean category =
        this.mSettings.setCategory(overlayIdentifier, i, androidPackage2.getOverlayCategory())
            | false;
    if (!criticalOverlayInfo.isFabricated()) {
      category |=
          this.mSettings.setBaseCodePath(
              overlayIdentifier,
              i,
              ((AndroidPackageSplit) androidPackage2.getSplits().get(0)).getPath());
    }
    OverlayInfo overlayInfo2 = this.mSettings.getOverlayInfo(overlayIdentifier, i);
    if (androidPackage != null
        && (!"android".equals(criticalOverlayInfo.getTargetPackageName())
            || isPackageConfiguredMutable(androidPackage2))) {
      if (i != 0 && this.mIdmapManager.idmapExists(overlayInfo2)) {
        Slog.d(
            "OverlayManager",
            "skip idmap creation for " + overlayIdentifier.getOverlayName() + " of user " + i);
      } else {
        overlayInfo = overlayInfo2;
        str = "OverlayManager";
        int createIdmap =
            this.mIdmapManager.createIdmap(
                androidPackage,
                packageStateForUser2,
                androidPackage2,
                overlayInfo2.baseCodePath,
                overlayIdentifier.getOverlayName(),
                i);
        category |= (createIdmap & 2) != 0;
        i3 = createIdmap;
        boolean z3 = category;
        state = this.mSettings.getState(overlayIdentifier, i);
        String str2 = str;
        calculateNewState = calculateNewState(overlayInfo, androidPackage, i, i2, i3);
        if (state != calculateNewState) {
          return z3;
        }
        Slog.d(
            str2,
            String.format(
                "%s:%d: %s -> %s",
                overlayIdentifier,
                Integer.valueOf(i),
                OverlayInfo.stateToString(state),
                OverlayInfo.stateToString(calculateNewState)));
        return z3 | this.mSettings.setState(overlayIdentifier, i, calculateNewState);
      }
    }
    overlayInfo = overlayInfo2;
    str = "OverlayManager";
    i3 = 0;
    boolean z32 = category;
    state = this.mSettings.getState(overlayIdentifier, i);
    String str22 = str;
    calculateNewState = calculateNewState(overlayInfo, androidPackage, i, i2, i3);
    if (state != calculateNewState) {}
  }

  public final int calculateNewState(
      OverlayInfo overlayInfo, AndroidPackage androidPackage, int i, int i2, int i3) {
    if ((i2 & 1) != 0) {
      return 4;
    }
    if ((i2 & 2) != 0) {
      return 5;
    }
    if ((i2 & 4) != 0) {
      return 7;
    }
    if (androidPackage == null) {
      return 0;
    }
    if ((i3 & 1) != 0 || this.mIdmapManager.idmapExists(overlayInfo)) {
      return this.mSettings.getEnabled(overlayInfo.getOverlayIdentifier(), i) ? 3 : 2;
    }
    return 1;
  }

  public final void removeIdmapIfPossible(OverlayInfo overlayInfo) {
    if (this.mIdmapManager.idmapExists(overlayInfo)) {
      for (int i : this.mSettings.getUsers()) {
        try {
          OverlayInfo overlayInfo2 =
              this.mSettings.getOverlayInfo(overlayInfo.getOverlayIdentifier(), i);
          if (overlayInfo2 != null && overlayInfo2.isEnabled()) {
            return;
          }
        } catch (OverlayManagerSettings.BadKeyException unused) {
        }
      }
      this.mIdmapManager.removeIdmap(overlayInfo, overlayInfo.userId);
    }
  }

  public static void dumpFile(PrintWriter printWriter, File file, String str, int i) {
    printWriter.println("uid= " + Integer.toString(i) + " " + str + " Dump");
    long length = file.length() - 1048576;
    try {
      BufferedReader bufferedReader =
          new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
      if (length > 0) {
        try {
          if (bufferedReader.skip(length) != length) {
            printWriter.println("Error in skipping file contents.");
          }
        } finally {
        }
      }
      while (true) {
        String readLine = bufferedReader.readLine();
        if (readLine != null) {
          printWriter.println(readLine);
        } else {
          bufferedReader.close();
          return;
        }
      }
    } catch (IOException unused) {
    }
  }

  public void createLocaleOverlayPreferenceDir(int i) {
    SemSamsungThemeUtils.createLocaleOverlayPreferenceDir(
        getDataFile(
            getPathForUser("/overlays/current_locale_apks/locale_preferences_%s/", i), "/"));
  }

  final class OperationFailedException extends Exception {
    public OperationFailedException(String str) {
      super(str);
    }

    public OperationFailedException(String str, Throwable th) {
      super(str, th);
    }
  }

  public final boolean isColorThemeApplied() {
    OverlayInfo nullableOverlayInfo =
        this.mSettings.getNullableOverlayInfo(
            new OverlayIdentifier("android", "SemWT_G_MonetPalette"), 0);
    return nullableOverlayInfo != null && nullableOverlayInfo.isEnabled();
  }
}
