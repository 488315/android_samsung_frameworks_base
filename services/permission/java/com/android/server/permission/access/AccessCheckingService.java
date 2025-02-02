package com.android.server.permission.access;

import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.SystemProperties;
import android.permission.PermissionManager;
import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.SystemConfig;
import com.android.server.SystemService;
import com.android.server.appop.AppOpsCheckingServiceInterface;
import com.android.server.permission.access.appop.AppOpService;
import com.android.server.permission.access.collection.IndexedListSet;
import com.android.server.permission.access.collection.IntSet;
import com.android.server.permission.access.collection.IntSetKt;
import com.android.server.permission.access.permission.PermissionService;
import com.android.server.permission.jarjar.kotlin.Pair;
import com.android.server.permission.jarjar.kotlin.TuplesKt;
import com.android.server.permission.jarjar.kotlin.Unit;
import com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt;
import com.android.server.permission.jarjar.kotlin.jvm.functions.Function1;
import com.android.server.permission.jarjar.kotlin.jvm.internal.InlineMarker;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.permission.PermissionAllowlist;
import com.android.server.pm.permission.PermissionManagerServiceInterface;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.util.Map;

/* compiled from: AccessCheckingService.kt */
/* loaded from: classes2.dex */
public final class AccessCheckingService extends SystemService {
  private AppOpService appOpService;
  private PackageManagerInternal packageManagerInternal;
  private PackageManagerLocal packageManagerLocal;
  private PermissionService permissionService;
  private final AccessPersistence persistence;
  private final AccessPolicy policy;
  private volatile AccessState state;
  private final Object stateLock;
  private SystemConfig systemConfig;
  private UserManagerService userManagerService;

  public AccessCheckingService(Context context) {
    super(context);
    this.stateLock = new Object();
    AccessPolicy accessPolicy = new AccessPolicy();
    this.policy = accessPolicy;
    this.persistence = new AccessPersistence(accessPolicy);
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    this.appOpService = new AppOpService(this);
    this.permissionService = new PermissionService(this);
    AppOpService appOpService = this.appOpService;
    PermissionService permissionService = null;
    if (appOpService == null) {
      Intrinsics.throwUninitializedPropertyAccessException("appOpService");
      appOpService = null;
    }
    LocalServices.addService(AppOpsCheckingServiceInterface.class, appOpService);
    PermissionService permissionService2 = this.permissionService;
    if (permissionService2 == null) {
      Intrinsics.throwUninitializedPropertyAccessException("permissionService");
    } else {
      permissionService = permissionService2;
    }
    LocalServices.addService(PermissionManagerServiceInterface.class, permissionService);
  }

  public final void initialize() {
    this.packageManagerInternal =
        (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    this.packageManagerLocal =
        (PackageManagerLocal) LocalManagerRegistry.getManagerOrThrow(PackageManagerLocal.class);
    this.userManagerService = UserManagerService.getInstance();
    this.systemConfig = SystemConfig.getInstance();
    UserManagerService userManagerService = this.userManagerService;
    PermissionService permissionService = null;
    if (userManagerService == null) {
      Intrinsics.throwUninitializedPropertyAccessException("userManagerService");
      userManagerService = null;
    }
    IntSet IntSet = IntSetKt.IntSet(userManagerService.getUserIdsIncludingPreCreated());
    PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
    if (packageManagerLocal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
      packageManagerLocal = null;
    }
    Pair allPackageStates = getAllPackageStates(packageManagerLocal);
    Map map = (Map) allPackageStates.component1();
    Map map2 = (Map) allPackageStates.component2();
    PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
    if (packageManagerInternal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
      packageManagerInternal = null;
    }
    SparseArray knownPackages = getKnownPackages(packageManagerInternal);
    SystemConfig systemConfig = this.systemConfig;
    if (systemConfig == null) {
      Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
      systemConfig = null;
    }
    boolean isLeanback = isLeanback(systemConfig);
    SystemConfig systemConfig2 = this.systemConfig;
    if (systemConfig2 == null) {
      Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
      systemConfig2 = null;
    }
    ArrayMap permissions = systemConfig2.getPermissions();
    SystemConfig systemConfig3 = this.systemConfig;
    if (systemConfig3 == null) {
      Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
      systemConfig3 = null;
    }
    IndexedListSet privilegedPermissionAllowlistPackages =
        getPrivilegedPermissionAllowlistPackages(systemConfig3);
    SystemConfig systemConfig4 = this.systemConfig;
    if (systemConfig4 == null) {
      Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
      systemConfig4 = null;
    }
    PermissionAllowlist permissionAllowlist = systemConfig4.getPermissionAllowlist();
    SystemConfig systemConfig5 = this.systemConfig;
    if (systemConfig5 == null) {
      Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
      systemConfig5 = null;
    }
    ArrayMap implicitToSourcePermissions = getImplicitToSourcePermissions(systemConfig5);
    AccessState accessState = new AccessState();
    this.policy.initialize(
        accessState,
        IntSet,
        map,
        map2,
        knownPackages,
        isLeanback,
        permissions,
        privilegedPermissionAllowlistPackages,
        permissionAllowlist,
        implicitToSourcePermissions);
    this.persistence.initialize();
    this.persistence.read(accessState);
    this.state = accessState;
    synchronized (this.stateLock) {
      AccessState accessState2 = this.state;
      if (accessState2 == null) {
        Intrinsics.throwUninitializedPropertyAccessException(
            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        accessState2 = null;
      }
      AccessState copy = accessState2.copy();
      this.policy.onInitialized(new MutateStateScope(accessState2, copy));
      this.persistence.write(copy);
      this.state = copy;
      this.policy.onStateMutated(new GetStateScope(copy));
      Unit unit = Unit.INSTANCE;
    }
    AppOpService appOpService = this.appOpService;
    if (appOpService == null) {
      Intrinsics.throwUninitializedPropertyAccessException("appOpService");
      appOpService = null;
    }
    appOpService.initialize();
    PermissionService permissionService2 = this.permissionService;
    if (permissionService2 == null) {
      Intrinsics.throwUninitializedPropertyAccessException("permissionService");
    } else {
      permissionService = permissionService2;
    }
    permissionService.initialize();
  }

  private final boolean isLeanback(SystemConfig systemConfig) {
    return systemConfig.getAvailableFeatures().containsKey("android.software.leanback");
  }

  private final IndexedListSet getPrivilegedPermissionAllowlistPackages(SystemConfig systemConfig) {
    IndexedListSet indexedListSet = new IndexedListSet();
    indexedListSet.add("android");
    if (systemConfig.getAvailableFeatures().containsKey("android.hardware.type.automotive")) {
      String str = SystemProperties.get("ro.android.car.carservice.package");
      if (str.length() > 0) {
        indexedListSet.add(str);
      }
    }
    return indexedListSet;
  }

  private final ArrayMap getImplicitToSourcePermissions(SystemConfig systemConfig) {
    ArrayMap arrayMap = new ArrayMap();
    for (PermissionManager.SplitPermissionInfo splitPermissionInfo :
        systemConfig.getSplitPermissions()) {
      String splitPermission = splitPermissionInfo.getSplitPermission();
      for (String str : splitPermissionInfo.getNewPermissions()) {
        Object obj = arrayMap.get(str);
        if (obj == null) {
          obj = new IndexedListSet();
          arrayMap.put(str, obj);
        }
        ((IndexedListSet) obj).add(splitPermission);
      }
    }
    return arrayMap;
  }

  /* renamed from: onStorageVolumeMounted$frameworks__base__services__permission__android_common__services_permission */
  public final void m59xe2cba220(String str, boolean z) {
    PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
    AccessState accessState = null;
    if (packageManagerLocal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
      packageManagerLocal = null;
    }
    Pair allPackageStates = getAllPackageStates(packageManagerLocal);
    Map map = (Map) allPackageStates.component1();
    Map map2 = (Map) allPackageStates.component2();
    PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
    if (packageManagerInternal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
      packageManagerInternal = null;
    }
    SparseArray knownPackages = getKnownPackages(packageManagerInternal);
    synchronized (this.stateLock) {
      AccessState accessState2 = this.state;
      if (accessState2 == null) {
        Intrinsics.throwUninitializedPropertyAccessException(
            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
      } else {
        accessState = accessState2;
      }
      AccessState copy = accessState.copy();
      this.policy.onStorageVolumeMounted(
          new MutateStateScope(accessState, copy), map, map2, knownPackages, str, z);
      this.persistence.write(copy);
      this.state = copy;
      this.policy.onStateMutated(new GetStateScope(copy));
      Unit unit = Unit.INSTANCE;
    }
  }

  /* renamed from: onPackageAdded$frameworks__base__services__permission__android_common__services_permission */
  public final void m55x9a5a53b7(String str) {
    PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
    AccessState accessState = null;
    if (packageManagerLocal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
      packageManagerLocal = null;
    }
    Pair allPackageStates = getAllPackageStates(packageManagerLocal);
    Map map = (Map) allPackageStates.component1();
    Map map2 = (Map) allPackageStates.component2();
    PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
    if (packageManagerInternal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
      packageManagerInternal = null;
    }
    SparseArray knownPackages = getKnownPackages(packageManagerInternal);
    synchronized (this.stateLock) {
      AccessState accessState2 = this.state;
      if (accessState2 == null) {
        Intrinsics.throwUninitializedPropertyAccessException(
            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
      } else {
        accessState = accessState2;
      }
      AccessState copy = accessState.copy();
      this.policy.onPackageAdded(
          new MutateStateScope(accessState, copy), map, map2, knownPackages, str);
      this.persistence.write(copy);
      this.state = copy;
      this.policy.onStateMutated(new GetStateScope(copy));
      Unit unit = Unit.INSTANCE;
    }
  }

  /* renamed from: onPackageRemoved$frameworks__base__services__permission__android_common__services_permission */
  public final void m57xfbe388d7(String str, int i) {
    PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
    AccessState accessState = null;
    if (packageManagerLocal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
      packageManagerLocal = null;
    }
    Pair allPackageStates = getAllPackageStates(packageManagerLocal);
    Map map = (Map) allPackageStates.component1();
    Map map2 = (Map) allPackageStates.component2();
    PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
    if (packageManagerInternal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
      packageManagerInternal = null;
    }
    SparseArray knownPackages = getKnownPackages(packageManagerInternal);
    synchronized (this.stateLock) {
      AccessState accessState2 = this.state;
      if (accessState2 == null) {
        Intrinsics.throwUninitializedPropertyAccessException(
            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
      } else {
        accessState = accessState2;
      }
      AccessState copy = accessState.copy();
      this.policy.onPackageRemoved(
          new MutateStateScope(accessState, copy), map, map2, knownPackages, str, i);
      this.persistence.write(copy);
      this.state = copy;
      this.policy.onStateMutated(new GetStateScope(copy));
      Unit unit = Unit.INSTANCE;
    }
  }

  /* renamed from: onPackageInstalled$frameworks__base__services__permission__android_common__services_permission */
  public final void m56x89669eb1(String str, int i) {
    PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
    AccessState accessState = null;
    if (packageManagerLocal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
      packageManagerLocal = null;
    }
    Pair allPackageStates = getAllPackageStates(packageManagerLocal);
    Map map = (Map) allPackageStates.component1();
    Map map2 = (Map) allPackageStates.component2();
    PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
    if (packageManagerInternal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
      packageManagerInternal = null;
    }
    SparseArray knownPackages = getKnownPackages(packageManagerInternal);
    synchronized (this.stateLock) {
      AccessState accessState2 = this.state;
      if (accessState2 == null) {
        Intrinsics.throwUninitializedPropertyAccessException(
            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
      } else {
        accessState = accessState2;
      }
      AccessState copy = accessState.copy();
      this.policy.onPackageInstalled(
          new MutateStateScope(accessState, copy), map, map2, knownPackages, str, i);
      this.persistence.write(copy);
      this.state = copy;
      this.policy.onStateMutated(new GetStateScope(copy));
      Unit unit = Unit.INSTANCE;
    }
  }

  /* renamed from: onPackageUninstalled$frameworks__base__services__permission__android_common__services_permission */
  public final void m58x2b8d4f78(String str, int i, int i2) {
    PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
    AccessState accessState = null;
    if (packageManagerLocal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
      packageManagerLocal = null;
    }
    Pair allPackageStates = getAllPackageStates(packageManagerLocal);
    Map map = (Map) allPackageStates.component1();
    Map map2 = (Map) allPackageStates.component2();
    PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
    if (packageManagerInternal == null) {
      Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
      packageManagerInternal = null;
    }
    SparseArray knownPackages = getKnownPackages(packageManagerInternal);
    synchronized (this.stateLock) {
      AccessState accessState2 = this.state;
      if (accessState2 == null) {
        Intrinsics.throwUninitializedPropertyAccessException(
            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
      } else {
        accessState = accessState2;
      }
      AccessState copy = accessState.copy();
      this.policy.onPackageUninstalled(
          new MutateStateScope(accessState, copy), map, map2, knownPackages, str, i, i2);
      this.persistence.write(copy);
      this.state = copy;
      this.policy.onStateMutated(new GetStateScope(copy));
      Unit unit = Unit.INSTANCE;
    }
  }

  private final Pair getAllPackageStates(PackageManagerLocal packageManagerLocal) {
    PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
        packageManagerLocal.withUnfilteredSnapshot();
    try {
      Pair m63to =
          TuplesKt.m63to(
              withUnfilteredSnapshot.getPackageStates(),
              withUnfilteredSnapshot.getDisabledSystemPackageStates());
      AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
      return m63to;
    } finally {
    }
  }

  private final SparseArray getKnownPackages(PackageManagerInternal packageManagerInternal) {
    SparseArray sparseArray = new SparseArray();
    sparseArray.set(2, packageManagerInternal.getKnownPackageNames(2, 0));
    sparseArray.set(7, packageManagerInternal.getKnownPackageNames(7, 0));
    sparseArray.set(4, packageManagerInternal.getKnownPackageNames(4, 0));
    sparseArray.set(1, packageManagerInternal.getKnownPackageNames(1, 0));
    sparseArray.set(6, packageManagerInternal.getKnownPackageNames(6, 0));
    sparseArray.set(10, packageManagerInternal.getKnownPackageNames(10, 0));
    sparseArray.set(11, packageManagerInternal.getKnownPackageNames(11, 0));
    sparseArray.set(12, packageManagerInternal.getKnownPackageNames(12, 0));
    sparseArray.set(15, packageManagerInternal.getKnownPackageNames(15, 0));
    sparseArray.set(16, packageManagerInternal.getKnownPackageNames(16, 0));
    sparseArray.set(17, packageManagerInternal.getKnownPackageNames(17, 0));
    return sparseArray;
  }

  public final int getDecision(AccessUri accessUri, AccessUri accessUri2) {
    AccessState accessState = this.state;
    if (accessState == null) {
      Intrinsics.throwUninitializedPropertyAccessException(
          LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
      accessState = null;
    }
    return this.policy.getDecision(new GetStateScope(accessState), accessUri, accessUri2);
  }

  /* renamed from: getState$frameworks__base__services__permission__android_common__services_permission */
  public final Object m53xf45137b9(Function1 function1) {
    AccessState accessState = this.state;
    if (accessState == null) {
      Intrinsics.throwUninitializedPropertyAccessException(
          LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
      accessState = null;
    }
    return function1.invoke(new GetStateScope(accessState));
  }

  /* renamed from: mutateState$frameworks__base__services__permission__android_common__services_permission */
  public final void m54x9188f5c9(Function1 function1) {
    synchronized (this.stateLock) {
      try {
        AccessState accessState = this.state;
        if (accessState == null) {
          Intrinsics.throwUninitializedPropertyAccessException(
              LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
          accessState = null;
        }
        AccessState copy = accessState.copy();
        function1.invoke(new MutateStateScope(accessState, copy));
        this.persistence.write(copy);
        this.state = copy;
        this.policy.onStateMutated(new GetStateScope(copy));
        Unit unit = Unit.INSTANCE;
        InlineMarker.finallyStart(1);
      } catch (Throwable th) {
        InlineMarker.finallyStart(1);
        InlineMarker.finallyEnd(1);
        throw th;
      }
    }
    InlineMarker.finallyEnd(1);
  }

  /* renamed from: onSystemReady$frameworks__base__services__permission__android_common__services_permission */
  public final void m60x9f7a1b33() {
    synchronized (this.stateLock) {
      AccessState accessState = this.state;
      if (accessState == null) {
        Intrinsics.throwUninitializedPropertyAccessException(
            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        accessState = null;
      }
      AccessState copy = accessState.copy();
      this.policy.onSystemReady(new MutateStateScope(accessState, copy));
      this.persistence.write(copy);
      this.state = copy;
      this.policy.onStateMutated(new GetStateScope(copy));
      Unit unit = Unit.INSTANCE;
    }
  }

  /* renamed from: onUserAdded$frameworks__base__services__permission__android_common__services_permission */
  public final void m61xd61c3d34(int i) {
    synchronized (this.stateLock) {
      AccessState accessState = this.state;
      if (accessState == null) {
        Intrinsics.throwUninitializedPropertyAccessException(
            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        accessState = null;
      }
      AccessState copy = accessState.copy();
      this.policy.onUserAdded(new MutateStateScope(accessState, copy), i);
      this.persistence.write(copy);
      this.state = copy;
      this.policy.onStateMutated(new GetStateScope(copy));
      Unit unit = Unit.INSTANCE;
    }
  }

  /* renamed from: onUserRemoved$frameworks__base__services__permission__android_common__services_permission */
  public final void m62x4ed10714(int i) {
    synchronized (this.stateLock) {
      AccessState accessState = this.state;
      if (accessState == null) {
        Intrinsics.throwUninitializedPropertyAccessException(
            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        accessState = null;
      }
      AccessState copy = accessState.copy();
      this.policy.onUserRemoved(new MutateStateScope(accessState, copy), i);
      this.persistence.write(copy);
      this.state = copy;
      this.policy.onStateMutated(new GetStateScope(copy));
      Unit unit = Unit.INSTANCE;
    }
  }

  public final void setDecision(AccessUri accessUri, AccessUri accessUri2, int i) {
    synchronized (this.stateLock) {
      AccessState accessState = this.state;
      if (accessState == null) {
        Intrinsics.throwUninitializedPropertyAccessException(
            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        accessState = null;
      }
      AccessState copy = accessState.copy();
      this.policy.setDecision(new MutateStateScope(accessState, copy), accessUri, accessUri2, i);
      this.persistence.write(copy);
      this.state = copy;
      this.policy.onStateMutated(new GetStateScope(copy));
      Unit unit = Unit.INSTANCE;
    }
  }

  /* renamed from: getSchemePolicy$frameworks__base__services__permission__android_common__services_permission */
  public final SchemePolicy m52x318e5b8b(String str, String str2) {
    return this.policy.getSchemePolicy(str, str2);
  }
}
