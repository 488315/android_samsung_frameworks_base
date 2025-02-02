package com.android.server.permission.access.permission;

import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.SigningDetails;
import android.os.UserHandle;
import android.os.IInstalld;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.util.FrameworkStatsLog;
import com.android.modules.utils.BinaryXmlPullParser;
import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.SystemConfig;
import com.android.server.audio.CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.AccessUri;
import com.android.server.permission.access.GetStateScope;
import com.android.server.permission.access.MutateStateScope;
import com.android.server.permission.access.SchemePolicy;
import com.android.server.permission.access.SystemState;
import com.android.server.permission.access.UserState;
import com.android.server.permission.access.WritableState;
import com.android.server.permission.access.collection.IndexedListSet;
import com.android.server.permission.access.collection.IntSet;
import com.android.server.permission.access.util.IntExtensionsKt;
import com.android.server.permission.jarjar.kotlin.Unit;
import com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysJvmKt;
import com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt;
import com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__MutableCollectionsKt;
import com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.permission.jarjar.kotlin.ranges.RangesKt___RangesKt;
import com.android.server.permission.jarjar.kotlin.text.StringsKt__StringsJVMKt;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.permission.CompatibilityPermissionInfo;
import com.android.server.pm.permission.PermissionAllowlist;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageUserState;
import com.android.server.pm.pkg.component.ParsedPermission;
import com.android.server.pm.pkg.component.ParsedPermissionGroup;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: UidPermissionPolicy.kt */
/* loaded from: classes2.dex */
public final class UidPermissionPolicy extends SchemePolicy {
  public static final Companion Companion = new Companion(null);
  public static final String LOG_TAG = UidPermissionPolicy.class.getSimpleName();
  public static final ArraySet RETAIN_IMPLICIT_FLAGS_PERMISSIONS =
      new ArraySet(
          ArraysKt___ArraysJvmKt.asList(
              new String[] {
                "android.permission.ACCESS_MEDIA_LOCATION",
                "android.permission.ACTIVITY_RECOGNITION",
                "android.permission.READ_MEDIA_AUDIO",
                "android.permission.READ_MEDIA_IMAGES",
                "android.permission.READ_MEDIA_VIDEO"
              }));
  public static final ArraySet NEARBY_DEVICES_PERMISSIONS =
      new ArraySet(
          ArraysKt___ArraysJvmKt.asList(
              new String[] {
                "android.permission.BLUETOOTH_ADVERTISE",
                "android.permission.BLUETOOTH_CONNECT",
                "android.permission.BLUETOOTH_SCAN",
                "android.permission.NEARBY_WIFI_DEVICES"
              }));
  public static final ArraySet NOTIFICATIONS_PERMISSIONS =
      new ArraySet(
          ArraysKt___ArraysJvmKt.asList(new String[] {"android.permission.POST_NOTIFICATIONS"}));
  public static final ArraySet STORAGE_AND_MEDIA_PERMISSIONS =
      new ArraySet(
          ArraysKt___ArraysJvmKt.asList(
              new String[] {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_MEDIA_AUDIO",
                "android.permission.READ_MEDIA_VIDEO",
                "android.permission.READ_MEDIA_IMAGES",
                "android.permission.ACCESS_MEDIA_LOCATION",
                "android.permission.READ_MEDIA_VISUAL_USER_SELECTED"
              }));
  public final UidPermissionPersistence persistence = new UidPermissionPersistence();
  public volatile IndexedListSet onPermissionFlagsChangedListeners = new IndexedListSet();
  public final Object onPermissionFlagsChangedListenersLock = new Object();
  public final ArraySet privilegedPermissionAllowlistViolations = new ArraySet();

  /* compiled from: UidPermissionPolicy.kt */
  public final class Companion {
    public Companion() {}

    public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
      this();
    }
  }

  /* compiled from: UidPermissionPolicy.kt */
  public abstract class OnPermissionFlagsChangedListener {
    public abstract void onPermissionFlagsChanged(int i, int i2, String str, int i3, int i4);

    public abstract void onStateMutated();
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public String getObjectScheme() {
    return "permission";
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public String getSubjectScheme() {
    return "uid";
  }

  public final boolean shouldGrantPrivilegedOrOemPermission(
      MutateStateScope mutateStateScope, PackageState packageState, Permission permission) {
    String str = permission.getPermissionInfo().name;
    String packageName = packageState.getPackageName();
    if (!IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 16)) {
      if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 16384)
          && packageState.isOem()) {
        Boolean oemAppAllowlistState =
            mutateStateScope
                .getNewState()
                .getSystemState()
                .getPermissionAllowlist()
                .getOemAppAllowlistState(packageName, str);
        if (oemAppAllowlistState == null) {
          throw new IllegalStateException(
              ("OEM permission "
                      + str
                      + " requested by package "
                      + packageName
                      + " must be explicitly declared granted or not")
                  .toString());
        }
        return oemAppAllowlistState.booleanValue();
      }
    } else if (packageState.isPrivileged()) {
      if (!packageState.isVendor()
          || IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 32768)) {
        return true;
      }
      Log.w(
          LOG_TAG,
          "Permission "
              + str
              + " cannot be granted to privileged vendor app "
              + packageName
              + " because it isn't a vendorPrivileged permission");
      return false;
    }
    return false;
  }

  public final Permission updatePermissionIfDynamic(
      MutateStateScope mutateStateScope, Permission permission) {
    Permission findPermissionTree;
    if (!(permission.getType() == 2)
        || (findPermissionTree =
                findPermissionTree(mutateStateScope, permission.getPermissionInfo().name))
            == null) {
      return permission;
    }
    PermissionInfo permissionInfo = new PermissionInfo(permission.getPermissionInfo());
    permissionInfo.packageName = findPermissionTree.getPermissionInfo().packageName;
    return Permission.copy$default(
        permission, permissionInfo, true, 0, findPermissionTree.getAppId(), null, false, 52, null);
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public int getDecision(GetStateScope getStateScope, AccessUri accessUri, AccessUri accessUri2) {
    Intrinsics.checkNotNull(
        accessUri,
        "null cannot be cast to non-null type com.android.server.permission.access.UidUri");
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri);
    Intrinsics.checkNotNull(
        accessUri2,
        "null cannot be cast to non-null type com.android.server.permission.access.PermissionUri");
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri2);
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri);
    throw null;
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void setDecision(
      MutateStateScope mutateStateScope, AccessUri accessUri, AccessUri accessUri2, int i) {
    Intrinsics.checkNotNull(
        accessUri,
        "null cannot be cast to non-null type com.android.server.permission.access.UidUri");
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri);
    Intrinsics.checkNotNull(
        accessUri2,
        "null cannot be cast to non-null type com.android.server.permission.access.PermissionUri");
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri2);
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri);
    throw null;
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void onStateMutated(GetStateScope getStateScope) {
    IndexedListSet indexedListSet = this.onPermissionFlagsChangedListeners;
    int size = indexedListSet.size();
    for (int i = 0; i < size; i++) {
      ((OnPermissionFlagsChangedListener) indexedListSet.elementAt(i)).onStateMutated();
    }
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void onInitialized(MutateStateScope mutateStateScope) {
    Permission permission;
    for (Map.Entry entry :
        mutateStateScope.getNewState().getSystemState().getConfigPermissions().entrySet()) {
      String str = (String) entry.getKey();
      SystemConfig.PermissionEntry permissionEntry =
          (SystemConfig.PermissionEntry) entry.getValue();
      ArrayMap permissions = mutateStateScope.getNewState().getSystemState().getPermissions();
      Permission permission2 = (Permission) permissions.get(str);
      if (permission2 != null) {
        int[] iArr = permissionEntry.gids;
        if (iArr != null) {
          permission =
              Permission.copy$default(
                  permission2, null, false, 0, 0, iArr, permissionEntry.perUser, 15, null);
        }
      } else {
        PermissionInfo permissionInfo = new PermissionInfo();
        permissionInfo.name = str;
        permissionInfo.packageName = "android";
        permissionInfo.protectionLevel = 2;
        if (permissionEntry.gids != null) {
          permission =
              new Permission(
                  permissionInfo, false, 1, 0, permissionEntry.gids, permissionEntry.perUser);
        } else {
          permission = new Permission(permissionInfo, false, 1, 0, null, false, 48, null);
        }
      }
      permissions.put(str, permission);
    }
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void onUserAdded(MutateStateScope mutateStateScope, int i) {
    Iterator it =
        mutateStateScope.getNewState().getSystemState().getPackageStates().entrySet().iterator();
    while (it.hasNext()) {
      evaluateAllPermissionStatesForPackageAndUser(
          mutateStateScope, (PackageState) ((Map.Entry) it.next()).getValue(), i, null);
    }
    SparseArray appIds = mutateStateScope.getNewState().getSystemState().getAppIds();
    int size = appIds.size();
    for (int i2 = 0; i2 < size; i2++) {
      inheritImplicitPermissionStates(mutateStateScope, appIds.keyAt(i2), i);
    }
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void onAppIdRemoved(MutateStateScope mutateStateScope, int i) {
    SparseArray userStates = mutateStateScope.getNewState().getUserStates();
    int size = userStates.size();
    for (int i2 = 0; i2 < size; i2++) {
      UserState userState = (UserState) userStates.valueAt(i2);
      userState.getUidPermissionFlags().remove(i);
      WritableState.requestWrite$default(userState, false, 1, null);
    }
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void onStorageVolumeMounted(MutateStateScope mutateStateScope, String str, boolean z) {
    ArraySet arraySet = new ArraySet();
    Iterator it =
        mutateStateScope.getNewState().getSystemState().getPackageStates().entrySet().iterator();
    while (it.hasNext()) {
      PackageState packageState = (PackageState) ((Map.Entry) it.next()).getValue();
      AndroidPackage androidPackage = packageState.getAndroidPackage();
      if (androidPackage != null && Intrinsics.areEqual(androidPackage.getVolumeUuid(), str)) {
        adoptPermissions(mutateStateScope, packageState, arraySet);
        addPermissionGroups(mutateStateScope, packageState);
        addPermissions(mutateStateScope, packageState, arraySet);
        trimPermissions(mutateStateScope, packageState.getPackageName(), arraySet);
        trimPermissionStates(mutateStateScope, packageState.getAppId());
        revokePermissionsOnPackageUpdate(mutateStateScope, packageState.getAppId());
      }
    }
    int size = arraySet.size();
    for (int i = 0; i < size; i++) {
      evaluatePermissionStateForAllPackages(mutateStateScope, (String) arraySet.valueAt(i), null);
    }
    Iterator it2 =
        mutateStateScope.getNewState().getSystemState().getPackageStates().entrySet().iterator();
    while (it2.hasNext()) {
      PackageState packageState2 = (PackageState) ((Map.Entry) it2.next()).getValue();
      AndroidPackage androidPackage2 = packageState2.getAndroidPackage();
      if (androidPackage2 != null && Intrinsics.areEqual(androidPackage2.getVolumeUuid(), str)) {
        evaluateAllPermissionStatesForPackage(
            mutateStateScope, packageState2, z ? packageState2 : null);
      }
    }
    Iterator it3 =
        mutateStateScope.getNewState().getSystemState().getPackageStates().entrySet().iterator();
    while (it3.hasNext()) {
      PackageState packageState3 = (PackageState) ((Map.Entry) it3.next()).getValue();
      AndroidPackage androidPackage3 = packageState3.getAndroidPackage();
      if (androidPackage3 != null && Intrinsics.areEqual(androidPackage3.getVolumeUuid(), str)) {
        IntSet userIds = mutateStateScope.getNewState().getSystemState().getUserIds();
        int size2 = userIds.getSize();
        for (int i2 = 0; i2 < size2; i2++) {
          inheritImplicitPermissionStates(
              mutateStateScope, packageState3.getAppId(), userIds.elementAt(i2));
        }
      }
    }
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void onPackageAdded(MutateStateScope mutateStateScope, PackageState packageState) {
    ArraySet arraySet = new ArraySet();
    adoptPermissions(mutateStateScope, packageState, arraySet);
    addPermissionGroups(mutateStateScope, packageState);
    addPermissions(mutateStateScope, packageState, arraySet);
    trimPermissions(mutateStateScope, packageState.getPackageName(), arraySet);
    trimPermissionStates(mutateStateScope, packageState.getAppId());
    revokePermissionsOnPackageUpdate(mutateStateScope, packageState.getAppId());
    int size = arraySet.size();
    for (int i = 0; i < size; i++) {
      evaluatePermissionStateForAllPackages(mutateStateScope, (String) arraySet.valueAt(i), null);
    }
    evaluateAllPermissionStatesForPackage(mutateStateScope, packageState, packageState);
    IntSet userIds = mutateStateScope.getNewState().getSystemState().getUserIds();
    int size2 = userIds.getSize();
    for (int i2 = 0; i2 < size2; i2++) {
      inheritImplicitPermissionStates(
          mutateStateScope, packageState.getAppId(), userIds.elementAt(i2));
    }
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void onPackageRemoved(MutateStateScope mutateStateScope, String str, int i) {
    if (!(!mutateStateScope
        .getNewState()
        .getSystemState()
        .getDisabledSystemPackageStates()
        .containsKey(str))) {
      throw new IllegalStateException(
          ("Package " + str + " reported as removed before disabled system package is enabled")
              .toString());
    }
    ArraySet arraySet = new ArraySet();
    trimPermissions(mutateStateScope, str, arraySet);
    if (mutateStateScope.getNewState().getSystemState().getAppIds().contains(i)) {
      trimPermissionStates(mutateStateScope, i);
    }
    int size = arraySet.size();
    for (int i2 = 0; i2 < size; i2++) {
      evaluatePermissionStateForAllPackages(mutateStateScope, (String) arraySet.valueAt(i2), null);
    }
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void onPackageUninstalled(MutateStateScope mutateStateScope, String str, int i, int i2) {
    resetRuntimePermissions(mutateStateScope, str, i, i2);
  }

  public final void resetRuntimePermissions(
      MutateStateScope mutateStateScope, String str, int i, int i2) {
    AndroidPackage androidPackage;
    boolean z;
    PackageState packageState =
        (PackageState) mutateStateScope.getNewState().getSystemState().getPackageStates().get(str);
    if (packageState == null || (androidPackage = packageState.getAndroidPackage()) == null) {
      return;
    }
    List requestedPermissions = androidPackage.getRequestedPermissions();
    int size = requestedPermissions.size();
    for (int i3 = 0; i3 < size; i3++) {
      String str2 = (String) requestedPermissions.get(i3);
      Permission permission =
          (Permission) mutateStateScope.getNewState().getSystemState().getPermissions().get(str2);
      if (permission != null && !IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 2)) {
        AccessState newState = mutateStateScope.getNewState();
        IndexedListSet indexedListSet =
            (IndexedListSet) newState.getSystemState().getAppIds().get(i);
        int size2 = indexedListSet.size();
        int i4 = 0;
        while (true) {
          if (i4 >= size2) {
            z = false;
            break;
          }
          Object obj =
              newState
                  .getSystemState()
                  .getPackageStates()
                  .get((String) indexedListSet.elementAt(i4));
          Intrinsics.checkNotNull(obj);
          PackageState packageState2 = (PackageState) obj;
          AndroidPackage androidPackage2 = packageState2.getAndroidPackage();
          z = true;
          if (androidPackage2 != null
              && androidPackage2.getRequestedPermissions().contains(str2)
              && (Intrinsics.areEqual(packageState2.getPackageName(), str) ^ true)) {
            break;
          } else {
            i4++;
          }
        }
        if (!z) {
          int permissionFlags = getPermissionFlags(mutateStateScope, i, i2, str2);
          if (!IntExtensionsKt.hasAnyBit(
              permissionFlags, FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT)) {
            int andInv =
                IntExtensionsKt.andInv(
                    (IntExtensionsKt.hasBits(permissionFlags, 8)
                            || IntExtensionsKt.hasBits(permissionFlags, 512))
                        ? permissionFlags | 16
                        : IntExtensionsKt.andInv(permissionFlags, 16),
                    15728736);
            if (IntExtensionsKt.hasBits(andInv, 1024)) {
              andInv |= IInstalld.FLAG_USE_QUOTA;
            }
            setPermissionFlags(mutateStateScope, i, i2, str2, andInv);
          }
        }
      }
    }
  }

  public final void adoptPermissions(
      MutateStateScope mutateStateScope, PackageState packageState, ArraySet arraySet) {
    AndroidPackage androidPackage = packageState.getAndroidPackage();
    Intrinsics.checkNotNull(androidPackage);
    List adoptPermissions = androidPackage.getAdoptPermissions();
    int size = adoptPermissions.size();
    for (int i = 0; i < size; i++) {
      String str = (String) adoptPermissions.get(i);
      String packageName = androidPackage.getPackageName();
      if (canAdoptPermissions(mutateStateScope, packageName, str)) {
        SystemState systemState = mutateStateScope.getNewState().getSystemState();
        ArrayMap permissions = systemState.getPermissions();
        int size2 = permissions.size();
        for (int i2 = 0; i2 < size2; i2++) {
          Object keyAt = permissions.keyAt(i2);
          Permission permission = (Permission) permissions.valueAt(i2);
          String str2 = (String) keyAt;
          if (Intrinsics.areEqual(permission.getPermissionInfo().packageName, str)) {
            PermissionInfo permissionInfo = new PermissionInfo();
            permissionInfo.name = permission.getPermissionInfo().name;
            permissionInfo.packageName = packageName;
            permissionInfo.protectionLevel = permission.getPermissionInfo().protectionLevel;
            permissions.setValueAt(
                i2,
                Permission.copy$default(
                    permission, permissionInfo, false, 0, 0, null, false, 52, null));
            WritableState.requestWrite$default(systemState, false, 1, null);
            arraySet.add(str2);
          }
        }
      }
    }
  }

  public final boolean canAdoptPermissions(
      MutateStateScope mutateStateScope, String str, String str2) {
    PackageState packageState =
        (PackageState) mutateStateScope.getNewState().getSystemState().getPackageStates().get(str2);
    if (packageState == null) {
      return false;
    }
    if (!packageState.isSystem()) {
      Log.w(
          LOG_TAG,
          "Unable to adopt permissions from "
              + str2
              + " to "
              + str
              + ": original package not in system partition");
      return false;
    }
    if (packageState.getAndroidPackage() == null) {
      return true;
    }
    Log.w(
        LOG_TAG,
        "Unable to adopt permissions from "
            + str2
            + " to "
            + str
            + ": original package still exists");
    return false;
  }

  public final void addPermissionGroups(
      MutateStateScope mutateStateScope, PackageState packageState) {
    boolean z;
    SparseArray userStates = packageState.getUserStates();
    int size = userStates.size();
    int i = 0;
    while (true) {
      if (i >= size) {
        z = true;
        break;
      }
      userStates.keyAt(i);
      if (!((PackageUserState) userStates.valueAt(i)).isInstantApp()) {
        z = false;
        break;
      }
      i++;
    }
    if (z) {
      Log.w(
          LOG_TAG,
          "Ignoring permission groups declared in package "
              + packageState.getPackageName()
              + ": instant apps cannot declare permission groups");
      return;
    }
    AndroidPackage androidPackage = packageState.getAndroidPackage();
    Intrinsics.checkNotNull(androidPackage);
    List permissionGroups = androidPackage.getPermissionGroups();
    int size2 = permissionGroups.size();
    for (int i2 = 0; i2 < size2; i2++) {
      PermissionGroupInfo generatePermissionGroupInfo =
          PackageInfoUtils.generatePermissionGroupInfo(
              (ParsedPermissionGroup) permissionGroups.get(i2), 128L);
      Intrinsics.checkNotNull(generatePermissionGroupInfo);
      String str = generatePermissionGroupInfo.name;
      PermissionGroupInfo permissionGroupInfo =
          (PermissionGroupInfo)
              mutateStateScope.getNewState().getSystemState().getPermissionGroups().get(str);
      if (permissionGroupInfo != null
          && !Intrinsics.areEqual(
              generatePermissionGroupInfo.packageName, permissionGroupInfo.packageName)) {
        String str2 = generatePermissionGroupInfo.packageName;
        String str3 = permissionGroupInfo.packageName;
        if (!packageState.isSystem()) {
          Log.w(
              LOG_TAG,
              "Ignoring permission group "
                  + str
                  + " declared in package "
                  + str2
                  + ": already declared in another package "
                  + str3);
        } else {
          PackageState packageState2 =
              (PackageState)
                  mutateStateScope.getNewState().getSystemState().getPackageStates().get(str3);
          if (packageState2 != null && packageState2.isSystem()) {
            Log.w(
                LOG_TAG,
                "Ignoring permission group "
                    + str
                    + " declared in system package "
                    + str2
                    + ": already declared in another system package "
                    + str3);
          } else {
            Log.w(
                LOG_TAG,
                "Overriding permission group "
                    + str
                    + " with new declaration in system package "
                    + str2
                    + ": originally declared in another package "
                    + str3);
          }
        }
      }
      mutateStateScope
          .getNewState()
          .getSystemState()
          .getPermissionGroups()
          .put(str, generatePermissionGroupInfo);
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:66:0x0202, code lost:

     if ((r7.getPermissionInfo().getProtection() == 1) != false) goto L61;
  */
  /* JADX WARN: Code restructure failed: missing block: B:67:0x021f, code lost:

     r16 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:94:0x021d, code lost:

     if ((r7.getPermissionInfo().getProtection() == 4) == false) goto L71;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void addPermissions(
      MutateStateScope mutateStateScope, PackageState packageState, ArraySet arraySet) {
    List list;
    int i;
    Permission copy$default;
    int i2;
    int i3;
    IntSet intSet;
    boolean z;
    String str;
    AndroidPackage androidPackage = packageState.getAndroidPackage();
    Intrinsics.checkNotNull(androidPackage);
    List permissions = androidPackage.getPermissions();
    int size = permissions.size();
    int i4 = 0;
    while (i4 < size) {
      ParsedPermission parsedPermission = (ParsedPermission) permissions.get(i4);
      PermissionInfo generatePermissionInfo =
          PackageInfoUtils.generatePermissionInfo(parsedPermission, 128L);
      Intrinsics.checkNotNull(generatePermissionInfo);
      SystemState systemState = mutateStateScope.getNewState().getSystemState();
      String str2 = generatePermissionInfo.name;
      Permission permission =
          parsedPermission.isTree()
              ? (Permission) systemState.getPermissionTrees().get(str2)
              : (Permission) systemState.getPermissions().get(str2);
      Permission findPermissionTree = findPermissionTree(mutateStateScope, str2);
      String str3 = generatePermissionInfo.packageName;
      if (findPermissionTree == null
          || Intrinsics.areEqual(str3, findPermissionTree.getPermissionInfo().packageName)) {
        if (permission == null
            || Intrinsics.areEqual(str3, permission.getPermissionInfo().packageName)) {
          if (permission != null) {
            boolean z2 =
                (!generatePermissionInfo.isRuntime()
                        || (str = generatePermissionInfo.group) == null
                        || Intrinsics.areEqual(str, permission.getPermissionInfo().group))
                    ? false
                    : true;
            if (permission.getType() != 1) {
              if (generatePermissionInfo.isRuntime()) {}
              if (generatePermissionInfo.getProtection() == 4) {}
            }
            boolean z3 = false;
            if (z2 || z3) {
              IntSet userIds = systemState.getUserIds();
              int size2 = userIds.getSize();
              int i5 = 0;
              while (i5 < size2) {
                int elementAt = userIds.elementAt(i5);
                SparseArray appIds = systemState.getAppIds();
                int size3 = appIds.size();
                int i6 = 0;
                while (i6 < size3) {
                  List list2 = permissions;
                  int keyAt = appIds.keyAt(i6);
                  int i7 = size;
                  int i8 = size3;
                  SparseArray sparseArray = appIds;
                  if (z2) {
                    String str4 = LOG_TAG;
                    i2 = i5;
                    String str5 = permission.getPermissionInfo().group;
                    i3 = size2;
                    String str6 = generatePermissionInfo.group;
                    intSet = userIds;
                    StringBuilder sb = new StringBuilder();
                    z = z2;
                    sb.append("Revoking runtime permission ");
                    sb.append(str2);
                    sb.append(" for appId ");
                    sb.append(keyAt);
                    sb.append(" and userId ");
                    sb.append(elementAt);
                    sb.append(" as the permission group changed from ");
                    sb.append(str5);
                    sb.append(" to ");
                    sb.append(str6);
                    Log.w(str4, sb.toString());
                  } else {
                    i2 = i5;
                    i3 = size2;
                    intSet = userIds;
                    z = z2;
                  }
                  if (z3) {
                    Log.w(
                        LOG_TAG,
                        "Revoking permission "
                            + str2
                            + " for appId "
                            + keyAt
                            + " and userId "
                            + elementAt
                            + " as the permission type changed.");
                  }
                  int i9 = elementAt;
                  setPermissionFlags(mutateStateScope, keyAt, i9, str2, 0);
                  i6++;
                  i5 = i2;
                  size3 = i8;
                  appIds = sparseArray;
                  permissions = list2;
                  size = i7;
                  elementAt = i9;
                  size2 = i3;
                  userIds = intSet;
                  z2 = z;
                }
                i5++;
                permissions = permissions;
              }
            }
          }
          list = permissions;
          i = size;
          copy$default =
              permission != null
                  ? Permission.copy$default(
                      permission,
                      generatePermissionInfo,
                      true,
                      0,
                      packageState.getAppId(),
                      null,
                      false,
                      52,
                      null)
                  : new Permission(
                      generatePermissionInfo,
                      true,
                      0,
                      packageState.getAppId(),
                      null,
                      false,
                      48,
                      null);
        } else {
          String str7 = permission.getPermissionInfo().packageName;
          if (packageState.isSystem()) {
            if (permission.getType() != 1 || permission.isReconciled()) {
              PackageState packageState2 = (PackageState) systemState.getPackageStates().get(str7);
              if (packageState2 != null && packageState2.isSystem()) {
                Log.w(
                    LOG_TAG,
                    "Ignoring permission "
                        + str2
                        + " declared in system package "
                        + str3
                        + ": already declared in another system package "
                        + str7);
              } else {
                Log.w(
                    LOG_TAG,
                    "Overriding permission "
                        + str2
                        + " with new declaration in system package "
                        + str3
                        + ": originally declared in another package "
                        + str7);
                IntSet userIds2 = systemState.getUserIds();
                int size4 = userIds2.getSize();
                int i10 = 0;
                while (i10 < size4) {
                  int elementAt2 = userIds2.elementAt(i10);
                  SparseArray appIds2 = systemState.getAppIds();
                  int size5 = appIds2.size();
                  int i11 = 0;
                  while (i11 < size5) {
                    setPermissionFlags(mutateStateScope, appIds2.keyAt(i11), elementAt2, str2, 0);
                    i11++;
                    appIds2 = appIds2;
                    size5 = size5;
                    i10 = i10;
                    size4 = size4;
                  }
                  i10++;
                }
                copy$default =
                    new Permission(
                        generatePermissionInfo,
                        true,
                        0,
                        packageState.getAppId(),
                        permission.getGids(),
                        permission.getAreGidsPerUser());
              }
            } else {
              copy$default =
                  Permission.copy$default(
                      permission,
                      generatePermissionInfo,
                      true,
                      0,
                      packageState.getAppId(),
                      null,
                      false,
                      52,
                      null);
            }
            list = permissions;
            i = size;
          } else {
            Log.w(
                LOG_TAG,
                "Ignoring permission "
                    + str2
                    + " declared in package "
                    + str3
                    + ": already declared in another package "
                    + str7);
          }
        }
        if (parsedPermission.isTree()) {
          systemState.getPermissionTrees().put(str2, copy$default);
        } else {
          systemState.getPermissions().put(str2, copy$default);
        }
        WritableState.requestWrite$default(systemState, false, 1, null);
        arraySet.add(str2);
        i4++;
        permissions = list;
        size = i;
      } else {
        Log.w(
            LOG_TAG,
            "Ignoring permission "
                + str2
                + " declared in package "
                + str3
                + ": base permission tree "
                + findPermissionTree.getPermissionInfo().name
                + " is declared in another package "
                + findPermissionTree.getPermissionInfo().packageName);
      }
      list = permissions;
      i = size;
      i4++;
      permissions = list;
      size = i;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:28:0x008c, code lost:

     if (r12 != false) goto L32;
  */
  /* JADX WARN: Code restructure failed: missing block: B:79:0x0143, code lost:

     if (r3 != 0) goto L76;
  */
  /* JADX WARN: Removed duplicated region for block: B:107:0x017c  */
  /* JADX WARN: Removed duplicated region for block: B:49:0x00c5  */
  /* JADX WARN: Removed duplicated region for block: B:51:0x00ca  */
  /* JADX WARN: Removed duplicated region for block: B:54:0x00ce A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:83:0x01d0  */
  /* JADX WARN: Removed duplicated region for block: B:86:0x01d3 A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void trimPermissions(
      MutateStateScope mutateStateScope, String str, ArraySet arraySet) {
    int i;
    int i2;
    int i3;
    int i4;
    List permissions;
    int i5;
    int i6;
    boolean z;
    boolean z2;
    List permissions2;
    boolean z3;
    boolean z4;
    SystemState systemState = mutateStateScope.getNewState().getSystemState();
    PackageState packageState = (PackageState) systemState.getPackageStates().get(str);
    AndroidPackage androidPackage = packageState != null ? packageState.getAndroidPackage() : null;
    if (packageState == null || androidPackage != null) {
      PackageState packageState2 =
          (PackageState) systemState.getDisabledSystemPackageStates().get(str);
      AndroidPackage androidPackage2 =
          packageState2 != null ? packageState2.getAndroidPackage() : null;
      ArrayMap permissionTrees = systemState.getPermissionTrees();
      int i7 = 1;
      int size = permissionTrees.size() - 1;
      int i8 = 0;
      boolean z5 = false;
      while (true) {
        if (-1 >= size) {
          break;
        }
        String str2 = (String) permissionTrees.keyAt(size);
        if (Intrinsics.areEqual(
            ((Permission) permissionTrees.valueAt(size)).getPermissionInfo().packageName, str)) {
          if (packageState != null) {
            Intrinsics.checkNotNull(androidPackage);
            List permissions3 = androidPackage.getPermissions();
            int size2 = permissions3.size();
            int i9 = 0;
            while (true) {
              if (i9 >= size2) {
                z4 = true;
                break;
              }
              ParsedPermission parsedPermission = (ParsedPermission) permissions3.get(i9);
              if (parsedPermission.isTree()
                  && Intrinsics.areEqual(parsedPermission.getName(), str2)) {
                z4 = false;
                break;
              }
              i9++;
            }
          }
          if (androidPackage2 != null
              && (permissions2 = androidPackage2.getPermissions()) != null) {
            int size3 = permissions2.size();
            int i10 = 0;
            while (true) {
              if (i10 >= size3) {
                z3 = false;
                break;
              }
              ParsedPermission parsedPermission2 = (ParsedPermission) permissions2.get(i10);
              if (parsedPermission2.isTree()
                  && Intrinsics.areEqual(parsedPermission2.getName(), str2)) {
                z3 = true;
                break;
              }
              i10++;
            }
            if (z3) {
              z2 = true;
              if (!z2) {
                z = true;
                if (!z) {
                  permissionTrees.removeAt(size);
                  z5 = true;
                }
                size--;
              }
            }
          }
          z2 = false;
          if (!z2) {}
        }
        z = false;
        if (!z) {}
        size--;
      }
      if (z5) {
        WritableState.requestWrite$default(systemState, false, 1, null);
      }
      ArrayMap permissions4 = systemState.getPermissions();
      int size4 = permissions4.size() - 1;
      for (i = -1; i < size4; i = -1) {
        String str3 = (String) permissions4.keyAt(size4);
        Permission updatePermissionIfDynamic =
            updatePermissionIfDynamic(mutateStateScope, (Permission) permissions4.valueAt(size4));
        mutateStateScope
            .getNewState()
            .getSystemState()
            .getPermissions()
            .setValueAt(size4, updatePermissionIfDynamic);
        if (Intrinsics.areEqual(updatePermissionIfDynamic.getPermissionInfo().packageName, str)) {
          if (packageState != null) {
            Intrinsics.checkNotNull(androidPackage);
            List permissions5 = androidPackage.getPermissions();
            int size5 = permissions5.size();
            int i11 = i8;
            while (true) {
              if (i11 >= size5) {
                i6 = i7;
                break;
              }
              ParsedPermission parsedPermission3 = (ParsedPermission) permissions5.get(i11);
              if (((parsedPermission3.isTree()
                          || !Intrinsics.areEqual(parsedPermission3.getName(), str3))
                      ? i8
                      : i7)
                  != 0) {
                i6 = i8;
                break;
              }
              i11++;
            }
          }
          if (androidPackage2 != null && (permissions = androidPackage2.getPermissions()) != null) {
            int size6 = permissions.size();
            int i12 = i8;
            while (true) {
              if (i12 >= size6) {
                i5 = i8;
                break;
              }
              ParsedPermission parsedPermission4 = (ParsedPermission) permissions.get(i12);
              if (((parsedPermission4.isTree()
                          || !Intrinsics.areEqual(parsedPermission4.getName(), str3))
                      ? i8
                      : i7)
                  != 0) {
                i5 = i7;
                break;
              }
              i12++;
            }
            if (i5 == i7) {
              i4 = i7;
              if (i4 == 0) {
                IntSet userIds = systemState.getUserIds();
                int size7 = userIds.getSize();
                int i13 = i8;
                while (i13 < size7) {
                  int elementAt = userIds.elementAt(i13);
                  SparseArray appIds = systemState.getAppIds();
                  int size8 = appIds.size();
                  while (i8 < size8) {
                    setPermissionFlags(mutateStateScope, appIds.keyAt(i8), elementAt, str3, 0);
                    i8++;
                    appIds = appIds;
                  }
                  i13++;
                  i8 = 0;
                }
                arraySet.add(str3);
                i8 = 0;
                i2 = 1;
                WritableState.requestWrite$default(systemState, false, 1, null);
                i3 = 1;
                if (i3 == 0) {
                  permissions4.removeAt(size4);
                }
                size4--;
                i7 = i2;
              }
              i2 = i7;
            }
          }
          i4 = i8;
          if (i4 == 0) {}
          i2 = i7;
        } else {
          i2 = i7;
        }
        i3 = i8;
        if (i3 == 0) {}
        size4--;
        i7 = i2;
      }
    }
  }

  public final void trimPermissionStates(MutateStateScope mutateStateScope, int i) {
    ArraySet arraySet = new ArraySet();
    AccessState newState = mutateStateScope.getNewState();
    Object obj = newState.getSystemState().getAppIds().get(i);
    Intrinsics.checkNotNull(obj);
    IndexedListSet indexedListSet = (IndexedListSet) obj;
    int size = indexedListSet.size();
    for (int i2 = 0; i2 < size; i2++) {
      Object obj2 =
          newState.getSystemState().getPackageStates().get((String) indexedListSet.elementAt(i2));
      Intrinsics.checkNotNull(obj2);
      PackageState packageState = (PackageState) obj2;
      if (packageState.getAndroidPackage() != null) {
        AndroidPackage androidPackage = packageState.getAndroidPackage();
        Intrinsics.checkNotNull(androidPackage);
        CollectionsKt__MutableCollectionsKt.addAll(
            arraySet, androidPackage.getRequestedPermissions());
      }
    }
    SparseArray userStates = mutateStateScope.getNewState().getUserStates();
    int size2 = userStates.size();
    for (int i3 = 0; i3 < size2; i3++) {
      int keyAt = userStates.keyAt(i3);
      ArrayMap arrayMap =
          (ArrayMap) ((UserState) userStates.valueAt(i3)).getUidPermissionFlags().get(i);
      if (arrayMap != null) {
        for (int size3 = arrayMap.size() - 1; -1 < size3; size3--) {
          Object keyAt2 = arrayMap.keyAt(size3);
          ((Number) arrayMap.valueAt(size3)).intValue();
          String str = (String) keyAt2;
          if (!arraySet.contains(str)) {
            setPermissionFlags(mutateStateScope, i, keyAt, str, 0);
          }
        }
      }
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:28:0x00cf A[LOOP:2: B:19:0x008c->B:28:0x00cf, LOOP_END] */
  /* JADX WARN: Removed duplicated region for block: B:29:0x00cd A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:41:0x0131 A[LOOP:3: B:32:0x00f0->B:41:0x0131, LOOP_END] */
  /* JADX WARN: Removed duplicated region for block: B:42:0x012f A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void revokePermissionsOnPackageUpdate(MutateStateScope mutateStateScope, int i) {
    int i2;
    boolean z;
    boolean z2;
    boolean z3;
    boolean z4;
    UidPermissionPolicy uidPermissionPolicy = this;
    SparseArray userStates = mutateStateScope.getNewState().getUserStates();
    int size = userStates.size();
    int i3 = 0;
    while (i3 < size) {
      int keyAt = userStates.keyAt(i3);
      ArrayMap arrayMap =
          (ArrayMap) ((UserState) userStates.valueAt(i3)).getUidPermissionFlags().get(i);
      if (arrayMap != null) {
        boolean z5 = true;
        int size2 = arrayMap.size() - 1;
        while (-1 < size2) {
          Object keyAt2 = arrayMap.keyAt(size2);
          int intValue = ((Number) arrayMap.valueAt(size2)).intValue();
          String str = (String) keyAt2;
          if (!STORAGE_AND_MEDIA_PERMISSIONS.contains(str) || intValue == 0) {
            i2 = size2;
          } else {
            int appIdTargetSdkVersion =
                uidPermissionPolicy.getAppIdTargetSdkVersion(
                    mutateStateScope, i, str, mutateStateScope.getOldState());
            int appIdTargetSdkVersion2 =
                uidPermissionPolicy.getAppIdTargetSdkVersion(
                    mutateStateScope, i, str, mutateStateScope.getNewState());
            boolean z6 = (appIdTargetSdkVersion < 29 || appIdTargetSdkVersion2 >= 29) ? false : z5;
            boolean z7 = (appIdTargetSdkVersion >= 29 || appIdTargetSdkVersion2 < 29) ? false : z5;
            AccessState oldState = mutateStateScope.getOldState();
            IndexedListSet indexedListSet =
                (IndexedListSet) oldState.getSystemState().getAppIds().get(i);
            int size3 = indexedListSet.size();
            int i4 = 0;
            while (true) {
              if (i4 >= size3) {
                z = false;
                break;
              }
              IndexedListSet indexedListSet2 = indexedListSet;
              AccessState accessState = oldState;
              Object obj =
                  oldState
                      .getSystemState()
                      .getPackageStates()
                      .get((String) indexedListSet.elementAt(i4));
              Intrinsics.checkNotNull(obj);
              PackageState packageState = (PackageState) obj;
              AndroidPackage androidPackage = packageState.getAndroidPackage();
              if (androidPackage != null
                  && androidPackage.getRequestedPermissions().contains(str)) {
                AndroidPackage androidPackage2 = packageState.getAndroidPackage();
                Intrinsics.checkNotNull(androidPackage2);
                if (androidPackage2.isRequestLegacyExternalStorage()) {
                  z4 = true;
                  if (!z4) {
                    z = true;
                    break;
                  } else {
                    i4++;
                    indexedListSet = indexedListSet2;
                    oldState = accessState;
                  }
                }
              }
              z4 = false;
              if (!z4) {}
            }
            AccessState newState = mutateStateScope.getNewState();
            IndexedListSet indexedListSet3 =
                (IndexedListSet) newState.getSystemState().getAppIds().get(i);
            int size4 = indexedListSet3.size();
            int i5 = size2;
            int i6 = 0;
            while (true) {
              if (i6 >= size4) {
                z2 = false;
                break;
              }
              AccessState accessState2 = newState;
              Object obj2 =
                  newState
                      .getSystemState()
                      .getPackageStates()
                      .get((String) indexedListSet3.elementAt(i6));
              Intrinsics.checkNotNull(obj2);
              PackageState packageState2 = (PackageState) obj2;
              AndroidPackage androidPackage3 = packageState2.getAndroidPackage();
              if (androidPackage3 != null
                  && androidPackage3.getRequestedPermissions().contains(str)) {
                AndroidPackage androidPackage4 = packageState2.getAndroidPackage();
                Intrinsics.checkNotNull(androidPackage4);
                if (androidPackage4.isRequestLegacyExternalStorage()) {
                  z3 = true;
                  if (!z3) {
                    z2 = true;
                    break;
                  } else {
                    i6++;
                    newState = accessState2;
                  }
                }
              }
              z3 = false;
              if (!z3) {}
            }
            if ((((z7 || z || !z2) ? false : true) || z6)
                && IntExtensionsKt.hasBits(intValue, 16)) {
              i2 = i5;
              setPermissionFlags(
                  mutateStateScope, i, keyAt, str, IntExtensionsKt.andInv(intValue, 15728752));
            } else {
              i2 = i5;
            }
          }
          size2 = i2 - 1;
          z5 = true;
          uidPermissionPolicy = this;
        }
      }
      i3++;
      uidPermissionPolicy = this;
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:13:0x0071 A[LOOP:2: B:6:0x003d->B:13:0x0071, LOOP_END] */
  /* JADX WARN: Removed duplicated region for block: B:14:0x0077 A[EDGE_INSN: B:14:0x0077->B:15:0x0077 BREAK  A[LOOP:2: B:6:0x003d->B:13:0x0071], SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void evaluatePermissionStateForAllPackages(
      MutateStateScope mutateStateScope, String str, PackageState packageState) {
    boolean z;
    boolean z2;
    SystemState systemState = mutateStateScope.getNewState().getSystemState();
    IntSet userIds = systemState.getUserIds();
    int size = userIds.getSize();
    for (int i = 0; i < size; i++) {
      int elementAt = userIds.elementAt(i);
      SparseArray appIds = systemState.getAppIds();
      int size2 = appIds.size();
      for (int i2 = 0; i2 < size2; i2++) {
        int keyAt = appIds.keyAt(i2);
        AccessState newState = mutateStateScope.getNewState();
        IndexedListSet indexedListSet =
            (IndexedListSet) newState.getSystemState().getAppIds().get(keyAt);
        int size3 = indexedListSet.size();
        int i3 = 0;
        while (true) {
          if (i3 >= size3) {
            z = false;
            break;
          }
          Object obj =
              newState
                  .getSystemState()
                  .getPackageStates()
                  .get((String) indexedListSet.elementAt(i3));
          Intrinsics.checkNotNull(obj);
          AndroidPackage androidPackage = ((PackageState) obj).getAndroidPackage();
          z = true;
          if (androidPackage != null && androidPackage.getRequestedPermissions().contains(str)) {
            z2 = true;
            if (!z2) {
              break;
            } else {
              i3++;
            }
          }
          z2 = false;
          if (!z2) {}
        }
        if (z) {
          evaluatePermissionState(mutateStateScope, keyAt, elementAt, str, packageState);
        }
      }
    }
  }

  public final void evaluateAllPermissionStatesForPackage(
      MutateStateScope mutateStateScope, PackageState packageState, PackageState packageState2) {
    IntSet userIds = mutateStateScope.getNewState().getSystemState().getUserIds();
    int size = userIds.getSize();
    for (int i = 0; i < size; i++) {
      evaluateAllPermissionStatesForPackageAndUser(
          mutateStateScope, packageState, userIds.elementAt(i), packageState2);
    }
  }

  public final void evaluateAllPermissionStatesForPackageAndUser(
      MutateStateScope mutateStateScope,
      PackageState packageState,
      int i,
      PackageState packageState2) {
    List requestedPermissions;
    AndroidPackage androidPackage = packageState.getAndroidPackage();
    if (androidPackage == null
        || (requestedPermissions = androidPackage.getRequestedPermissions()) == null) {
      return;
    }
    int size = requestedPermissions.size();
    for (int i2 = 0; i2 < size; i2++) {
      evaluatePermissionState(
          mutateStateScope,
          packageState.getAppId(),
          i,
          (String) requestedPermissions.get(i2),
          packageState2);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:118:0x045a  */
  /* JADX WARN: Removed duplicated region for block: B:120:0x045f  */
  /* JADX WARN: Removed duplicated region for block: B:137:0x04ce  */
  /* JADX WARN: Removed duplicated region for block: B:149:0x0509 A[ADDED_TO_REGION] */
  /* JADX WARN: Removed duplicated region for block: B:154:0x0506 A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:160:0x045c  */
  /* JADX WARN: Removed duplicated region for block: B:215:0x026d A[LOOP:6: B:206:0x022a->B:215:0x026d, LOOP_END] */
  /* JADX WARN: Removed duplicated region for block: B:216:0x026b A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:228:0x02f6  */
  /* JADX WARN: Removed duplicated region for block: B:237:0x0324  */
  /* JADX WARN: Removed duplicated region for block: B:244:0x0343 A[ADDED_TO_REGION] */
  /* JADX WARN: Removed duplicated region for block: B:247:0x034f  */
  /* JADX WARN: Removed duplicated region for block: B:252:0x02fa  */
  /* JADX WARN: Removed duplicated region for block: B:36:0x00cc  */
  /* JADX WARN: Removed duplicated region for block: B:49:0x011d  */
  /* JADX WARN: Removed duplicated region for block: B:57:0x0158 A[LOOP:2: B:48:0x011b->B:57:0x0158, LOOP_END] */
  /* JADX WARN: Removed duplicated region for block: B:58:0x0156 A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:69:0x015b A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:72:0x0103 A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void evaluatePermissionState(
      MutateStateScope mutateStateScope, int i, int i2, String str, PackageState packageState) {
    boolean z;
    boolean z2;
    int i3;
    boolean z3;
    boolean z4;
    int size;
    int i4;
    boolean z5;
    int i5;
    boolean z6;
    boolean z7;
    boolean z8;
    boolean z9;
    boolean z10;
    int andInv;
    int i6;
    boolean z11;
    boolean z12;
    boolean z13;
    int size2;
    int i7;
    boolean z14;
    int size3;
    int i8;
    boolean z15;
    boolean z16;
    IndexedListSet indexedListSet =
        (IndexedListSet) mutateStateScope.getNewState().getSystemState().getAppIds().get(i);
    int size4 = indexedListSet.size();
    int i9 = 0;
    while (true) {
      if (i9 >= size4) {
        z = false;
        break;
      }
      Object obj =
          mutateStateScope
              .getNewState()
              .getSystemState()
              .getPackageStates()
              .get((String) indexedListSet.elementAt(i9));
      Intrinsics.checkNotNull(obj);
      if (((PackageState) obj).getAndroidPackage() == null) {
        z = true;
        break;
      }
      i9++;
    }
    if (indexedListSet.size() == 1 && z) {
      return;
    }
    Permission permission =
        (Permission) mutateStateScope.getNewState().getSystemState().getPermissions().get(str);
    int permissionFlags = getPermissionFlags(mutateStateScope, i, i2, str);
    if (permission == null) {
      if (permissionFlags == 0) {
        setPermissionFlags(mutateStateScope, i, i2, str, 2);
        return;
      }
      return;
    }
    if (permission.getPermissionInfo().getProtection() == 0) {
      if (IntExtensionsKt.hasBits(permissionFlags, 1)) {
        return;
      }
      boolean hasBits = IntExtensionsKt.hasBits(permissionFlags, 2);
      if (packageState != null) {
        AndroidPackage androidPackage = packageState.getAndroidPackage();
        Intrinsics.checkNotNull(androidPackage);
        if (androidPackage.getRequestedPermissions().contains(str)) {
          z13 = true;
          AccessState newState = mutateStateScope.getNewState();
          IndexedListSet indexedListSet2 =
              (IndexedListSet) newState.getSystemState().getAppIds().get(i);
          size2 = indexedListSet2.size();
          i7 = 0;
          while (true) {
            if (i7 < size2) {
              z14 = false;
              break;
            }
            Object obj2 =
                newState
                    .getSystemState()
                    .getPackageStates()
                    .get((String) indexedListSet2.elementAt(i7));
            Intrinsics.checkNotNull(obj2);
            PackageState packageState2 = (PackageState) obj2;
            AndroidPackage androidPackage2 = packageState2.getAndroidPackage();
            if (androidPackage2 != null
                && androidPackage2.getRequestedPermissions().contains(str)
                && packageState2.isSystem()) {
              z14 = true;
              break;
            }
            i7++;
          }
          AccessState newState2 = mutateStateScope.getNewState();
          IndexedListSet indexedListSet3 =
              (IndexedListSet) newState2.getSystemState().getAppIds().get(i);
          size3 = indexedListSet3.size();
          i8 = 0;
          while (true) {
            if (i8 < size3) {
              z15 = false;
              break;
            }
            Object obj3 =
                newState2
                    .getSystemState()
                    .getPackageStates()
                    .get((String) indexedListSet3.elementAt(i8));
            Intrinsics.checkNotNull(obj3);
            PackageState packageState3 = (PackageState) obj3;
            AndroidPackage androidPackage3 = packageState3.getAndroidPackage();
            if (androidPackage3 != null
                && androidPackage3.getRequestedPermissions().contains(str)) {
              AndroidPackage androidPackage4 = packageState3.getAndroidPackage();
              Intrinsics.checkNotNull(androidPackage4);
              if (isCompatibilityPermissionForPackage(androidPackage4, str)) {
                z16 = true;
                if (!z16) {
                  z15 = true;
                  break;
                }
                i8++;
              }
            }
            z16 = false;
            if (!z16) {}
          }
          setPermissionFlags(mutateStateScope, i, i2, str, (hasBits || z13 || z14 || z15) ? 1 : 2);
          return;
        }
      }
      z13 = false;
      AccessState newState3 = mutateStateScope.getNewState();
      IndexedListSet indexedListSet22 =
          (IndexedListSet) newState3.getSystemState().getAppIds().get(i);
      size2 = indexedListSet22.size();
      i7 = 0;
      while (true) {
        if (i7 < size2) {}
        i7++;
      }
      AccessState newState22 = mutateStateScope.getNewState();
      IndexedListSet indexedListSet32 =
          (IndexedListSet) newState22.getSystemState().getAppIds().get(i);
      size3 = indexedListSet32.size();
      i8 = 0;
      while (true) {
        if (i8 < size3) {}
        i8++;
      }
      setPermissionFlags(mutateStateScope, i, i2, str, (hasBits || z13 || z14 || z15) ? 1 : 2);
      return;
    }
    if (permission.getPermissionInfo().getProtection() == 2) {
      z2 = true;
      i3 = 4;
    } else {
      if (!(permission.getPermissionInfo().getProtection() == 4)) {
        if (!(permission.getPermissionInfo().getProtection() == 1)) {
          Log.e(
              LOG_TAG,
              "Unknown protection level "
                  + permission.getPermissionInfo().protectionLevel
                  + "for permission "
                  + permission.getPermissionInfo().name
                  + " while evaluating permission statefor appId "
                  + i
                  + " and userId "
                  + i2);
          return;
        }
        int i10 = permissionFlags & 16777208;
        if (getAppIdTargetSdkVersion$default(this, mutateStateScope, i, str, null, 4, null) < 23) {
          if (IntExtensionsKt.hasBits(
              permission.getPermissionInfo().getProtectionFlags(), IInstalld.FLAG_FORCE)) {
            andInv = i10 & 229376;
          } else {
            andInv = i10 | 1024;
            if (getOldStatePermissionFlags(mutateStateScope, i, i2, str) == 0) {
              andInv |= IInstalld.FLAG_USE_QUOTA;
            }
          }
          i6 = 229376;
          z9 = true;
        } else {
          boolean hasBits2 = IntExtensionsKt.hasBits(i10, 1024);
          int andInv2 = IntExtensionsKt.andInv(i10, 1024);
          boolean hasBits3 =
              IntExtensionsKt.hasBits(andInv2, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
          boolean z17 =
              mutateStateScope.getNewState().getSystemState().isLeanback()
                  && NOTIFICATIONS_PERMISSIONS.contains(str);
          AccessState newState4 = mutateStateScope.getNewState();
          IndexedListSet indexedListSet4 =
              (IndexedListSet) newState4.getSystemState().getAppIds().get(i);
          int size5 = indexedListSet4.size();
          int i11 = 0;
          while (true) {
            if (i11 >= size5) {
              z8 = false;
              break;
            }
            Object obj4 =
                newState4
                    .getSystemState()
                    .getPackageStates()
                    .get((String) indexedListSet4.elementAt(i11));
            Intrinsics.checkNotNull(obj4);
            PackageState packageState4 = (PackageState) obj4;
            AndroidPackage androidPackage5 = packageState4.getAndroidPackage();
            if (androidPackage5 != null
                && androidPackage5.getRequestedPermissions().contains(str)) {
              AndroidPackage androidPackage6 = packageState4.getAndroidPackage();
              Intrinsics.checkNotNull(androidPackage6);
              if (androidPackage6.getImplicitPermissions().contains(str)) {
                z12 = true;
                if (!z12) {
                  z8 = true;
                  break;
                }
                i11++;
              }
            }
            z12 = false;
            if (!z12) {}
          }
          IndexedListSet<String> indexedListSet5 =
              (IndexedListSet)
                  mutateStateScope
                      .getNewState()
                      .getSystemState()
                      .getImplicitToSourcePermissions()
                      .get(str);
          if (indexedListSet5 != null) {
            if (!indexedListSet5.isEmpty()) {
              for (String str2 : indexedListSet5) {
                Permission permission2 =
                    (Permission)
                        mutateStateScope.getNewState().getSystemState().getPermissions().get(str2);
                if (permission2 == null) {
                  throw new IllegalStateException(
                      ("Unknown source permission " + str2 + " in split permissions").toString());
                }
                int protection = permission2.getPermissionInfo().getProtection();
                z9 = true;
                if (!(protection == 1)) {
                  z10 = true;
                  break;
                }
              }
            } else {
              z10 = false;
              z9 = true;
            }
            boolean z18 = (!z17 || (z8 && z10)) ? z9 : false;
            int andInv3 =
                !z18
                    ? andInv2 | IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES
                    : IntExtensionsKt.andInv(
                        andInv2, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
            andInv =
                ((!hasBits2 || hasBits3) && !z18)
                    ? IntExtensionsKt.andInv(andInv3, 1048576)
                    : andInv3;
            boolean hasBits4 = IntExtensionsKt.hasBits(andInv, IInstalld.FLAG_USE_QUOTA);
            if (!z8 && hasBits4) {
              andInv = IntExtensionsKt.andInv(andInv, IInstalld.FLAG_USE_QUOTA);
              if (NEARBY_DEVICES_PERMISSIONS.contains(str)) {
                int permissionFlags2 =
                    getPermissionFlags(
                        mutateStateScope, i, i2, "android.permission.ACCESS_BACKGROUND_LOCATION");
                if (PermissionFlags.INSTANCE.isAppOpGranted(permissionFlags2)
                    && !IntExtensionsKt.hasBits(permissionFlags2, IInstalld.FLAG_USE_QUOTA)) {
                  z11 = z9;
                  boolean hasAnyBit =
                      IntExtensionsKt.hasAnyBit(
                          andInv, FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
                  if (z11 && !hasAnyBit) {
                    andInv = IntExtensionsKt.andInv(andInv, 112);
                  } else if (hasBits3) {
                    andInv |= 16;
                  }
                }
              }
              z11 = false;
              boolean hasAnyBit2 =
                  IntExtensionsKt.hasAnyBit(
                      andInv, FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
              if (z11) {}
              if (hasBits3) {}
            }
            i6 = 229376;
          }
          z9 = true;
          z10 = false;
          if (z17) {}
          if (!z18) {}
          if (hasBits2) {}
          boolean hasBits42 = IntExtensionsKt.hasBits(andInv, IInstalld.FLAG_USE_QUOTA);
          if (!z8) {
            andInv = IntExtensionsKt.andInv(andInv, IInstalld.FLAG_USE_QUOTA);
            if (NEARBY_DEVICES_PERMISSIONS.contains(str)) {}
            z11 = false;
            boolean hasAnyBit22 =
                IntExtensionsKt.hasAnyBit(
                    andInv, FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
            if (z11) {}
            if (hasBits3) {}
          }
          i6 = 229376;
        }
        boolean hasAnyBit3 = IntExtensionsKt.hasAnyBit(andInv, i6);
        int andInv4 =
            (!IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 4) || hasAnyBit3)
                ? false
                : z9 ? andInv | 262144 : IntExtensionsKt.andInv(andInv, 262144);
        if (!IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 8) || hasAnyBit3) {
          z9 = false;
        }
        setPermissionFlags(
            mutateStateScope,
            i,
            i2,
            str,
            z9 ? andInv4 | 524288 : IntExtensionsKt.andInv(andInv4, 524288));
        return;
      }
      i3 = 4;
      z2 = true;
    }
    boolean hasBits5 = IntExtensionsKt.hasBits(permissionFlags, i3);
    if (z && hasBits5) {
      i5 = i3;
    } else {
      if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 16)) {
        AccessState newState5 = mutateStateScope.getNewState();
        IndexedListSet indexedListSet6 =
            (IndexedListSet) newState5.getSystemState().getAppIds().get(i);
        int size6 = indexedListSet6.size();
        int i12 = 0;
        while (true) {
          if (i12 >= size6) {
            z7 = false;
            break;
          }
          Object obj5 =
              newState5
                  .getSystemState()
                  .getPackageStates()
                  .get((String) indexedListSet6.elementAt(i12));
          Intrinsics.checkNotNull(obj5);
          PackageState packageState5 = (PackageState) obj5;
          AndroidPackage androidPackage7 = packageState5.getAndroidPackage();
          if ((androidPackage7 != null
                  && androidPackage7.getRequestedPermissions().contains(str)
                  && checkPrivilegedPermissionAllowlist(
                      mutateStateScope, packageState5, permission))
              ? z2
              : false) {
            z7 = z2;
            break;
          }
          i12++;
        }
        if (!z7) {
          z3 = false;
          if (permission.getPermissionInfo().getProtection() != 2 ? z2 : false) {
            AccessState newState6 = mutateStateScope.getNewState();
            IndexedListSet indexedListSet7 =
                (IndexedListSet) newState6.getSystemState().getAppIds().get(i);
            int size7 = indexedListSet7.size();
            int i13 = 0;
            while (true) {
              if (i13 >= size7) {
                z6 = false;
                break;
              }
              Object obj6 =
                  newState6
                      .getSystemState()
                      .getPackageStates()
                      .get((String) indexedListSet7.elementAt(i13));
              Intrinsics.checkNotNull(obj6);
              PackageState packageState6 = (PackageState) obj6;
              AndroidPackage androidPackage8 = packageState6.getAndroidPackage();
              if ((androidPackage8 != null
                      && androidPackage8.getRequestedPermissions().contains(str)
                      && shouldGrantPermissionBySignature(
                          mutateStateScope, packageState6, permission))
                  ? z2
                  : false) {
                z6 = z2;
                break;
              }
              i13++;
            }
            if (z6) {
              z4 = z2;
              AccessState newState7 = mutateStateScope.getNewState();
              IndexedListSet indexedListSet8 =
                  (IndexedListSet) newState7.getSystemState().getAppIds().get(i);
              size = indexedListSet8.size();
              i4 = 0;
              while (true) {
                if (i4 >= size) {
                  z5 = false;
                  break;
                }
                Object obj7 =
                    newState7
                        .getSystemState()
                        .getPackageStates()
                        .get((String) indexedListSet8.elementAt(i4));
                Intrinsics.checkNotNull(obj7);
                PackageState packageState7 = (PackageState) obj7;
                AndroidPackage androidPackage9 = packageState7.getAndroidPackage();
                if ((androidPackage9 != null
                        && androidPackage9.getRequestedPermissions().contains(str)
                        && shouldGrantPermissionByProtectionFlags(
                            mutateStateScope, packageState7, permission))
                    ? z2
                    : false) {
                  z5 = z2;
                  break;
                }
                i4++;
              }
              i5 = (z3 || !(z4 || z5)) ? 0 : 4;
            }
          }
          z4 = false;
          AccessState newState72 = mutateStateScope.getNewState();
          IndexedListSet indexedListSet82 =
              (IndexedListSet) newState72.getSystemState().getAppIds().get(i);
          size = indexedListSet82.size();
          i4 = 0;
          while (true) {
            if (i4 >= size) {}
            i4++;
          }
          if (z3) {}
        }
      }
      z3 = z2;
      if (permission.getPermissionInfo().getProtection() != 2 ? z2 : false) {}
      z4 = false;
      AccessState newState722 = mutateStateScope.getNewState();
      IndexedListSet indexedListSet822 =
          (IndexedListSet) newState722.getSystemState().getAppIds().get(i);
      size = indexedListSet822.size();
      i4 = 0;
      while (true) {
        if (i4 >= size) {}
        i4++;
      }
      if (z3) {}
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 32)) {
      i5 |= permissionFlags & 16;
    }
    setPermissionFlags(
        mutateStateScope,
        i,
        i2,
        str,
        IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 67108864)
            ? (permissionFlags & 24) | i5
            : i5);
  }

  public final void inheritImplicitPermissionStates(
      MutateStateScope mutateStateScope, int i, int i2) {
    IndexedListSet indexedListSet;
    ArraySet arraySet = new ArraySet();
    AccessState newState = mutateStateScope.getNewState();
    Object obj = newState.getSystemState().getAppIds().get(i);
    Intrinsics.checkNotNull(obj);
    IndexedListSet indexedListSet2 = (IndexedListSet) obj;
    int size = indexedListSet2.size();
    for (int i3 = 0; i3 < size; i3++) {
      Object obj2 =
          newState.getSystemState().getPackageStates().get((String) indexedListSet2.elementAt(i3));
      Intrinsics.checkNotNull(obj2);
      PackageState packageState = (PackageState) obj2;
      if (packageState.getAndroidPackage() != null) {
        AndroidPackage androidPackage = packageState.getAndroidPackage();
        Intrinsics.checkNotNull(androidPackage);
        CollectionsKt__MutableCollectionsKt.addAll(
            arraySet, androidPackage.getImplicitPermissions());
      }
    }
    int size2 = arraySet.size();
    for (int i4 = 0; i4 < size2; i4++) {
      String str = (String) arraySet.valueAt(i4);
      Permission permission =
          (Permission) mutateStateScope.getNewState().getSystemState().getPermissions().get(str);
      if (permission != null) {
        if (permission.getPermissionInfo().getProtection() == 1) {
          if ((getOldStatePermissionFlags(mutateStateScope, i, i2, str) == 0)
              && (indexedListSet =
                      (IndexedListSet)
                          mutateStateScope
                              .getNewState()
                              .getSystemState()
                              .getImplicitToSourcePermissions()
                              .get(str))
                  != null) {
            int permissionFlags = getPermissionFlags(mutateStateScope, i, i2, str);
            int size3 = indexedListSet.size();
            for (int i5 = 0; i5 < size3; i5++) {
              String str2 = (String) indexedListSet.elementAt(i5);
              if (((Permission)
                      mutateStateScope.getNewState().getSystemState().getPermissions().get(str2))
                  == null) {
                throw new IllegalStateException(
                    ("Unknown source permission " + str2 + " in split permissions").toString());
              }
              int permissionFlags2 = getPermissionFlags(mutateStateScope, i, i2, str2);
              PermissionFlags permissionFlags3 = PermissionFlags.INSTANCE;
              boolean isPermissionGranted = permissionFlags3.isPermissionGranted(permissionFlags2);
              boolean isPermissionGranted2 = permissionFlags3.isPermissionGranted(permissionFlags);
              boolean z = isPermissionGranted && !isPermissionGranted2;
              if (isPermissionGranted == isPermissionGranted2 || z) {
                if (z) {
                  permissionFlags = 0;
                }
                permissionFlags = (permissionFlags2 & 16777208) | permissionFlags;
              }
            }
            setPermissionFlags(
                mutateStateScope,
                i,
                i2,
                str,
                RETAIN_IMPLICIT_FLAGS_PERMISSIONS.contains(str)
                    ? IntExtensionsKt.andInv(permissionFlags, IInstalld.FLAG_USE_QUOTA)
                    : permissionFlags | IInstalld.FLAG_USE_QUOTA);
          }
        }
      } else {
        throw new IllegalStateException(
            ("Unknown implicit permission " + str + " in split permissions").toString());
      }
    }
  }

  public final boolean isCompatibilityPermissionForPackage(
      AndroidPackage androidPackage, String str) {
    for (CompatibilityPermissionInfo compatibilityPermissionInfo :
        CompatibilityPermissionInfo.COMPAT_PERMS) {
      if (Intrinsics.areEqual(compatibilityPermissionInfo.getName(), str)
          && androidPackage.getTargetSdkVersion() < compatibilityPermissionInfo.getSdkVersion()) {
        Log.i(
            LOG_TAG, "Auto-granting " + str + " to old package " + androidPackage.getPackageName());
        return true;
      }
    }
    return false;
  }

  public final boolean shouldGrantPermissionBySignature(
      MutateStateScope mutateStateScope, PackageState packageState, Permission permission) {
    AndroidPackage androidPackage;
    AndroidPackage androidPackage2 = packageState.getAndroidPackage();
    Intrinsics.checkNotNull(androidPackage2);
    SigningDetails signingDetails = androidPackage2.getSigningDetails();
    PackageState packageState2 =
        (PackageState)
            mutateStateScope
                .getNewState()
                .getSystemState()
                .getPackageStates()
                .get(permission.getPermissionInfo().packageName);
    SigningDetails signingDetails2 =
        (packageState2 == null || (androidPackage = packageState2.getAndroidPackage()) == null)
            ? null
            : androidPackage.getSigningDetails();
    Object obj = mutateStateScope.getNewState().getSystemState().getPackageStates().get("android");
    Intrinsics.checkNotNull(obj);
    AndroidPackage androidPackage3 = ((PackageState) obj).getAndroidPackage();
    Intrinsics.checkNotNull(androidPackage3);
    SigningDetails signingDetails3 = androidPackage3.getSigningDetails();
    return (signingDetails2 != null
            && signingDetails2.hasCommonSignerWithCapability(signingDetails, 4))
        || signingDetails.hasAncestorOrSelf(signingDetails3)
        || signingDetails3.checkCapability(signingDetails, 4);
  }

  public final boolean checkPrivilegedPermissionAllowlist(
      MutateStateScope mutateStateScope, PackageState packageState, Permission permission) {
    if (RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_DISABLE
        || Intrinsics.areEqual(packageState.getPackageName(), "android")
        || !packageState.isSystem()
        || !packageState.isPrivileged()
        || !mutateStateScope
            .getNewState()
            .getSystemState()
            .getPrivilegedPermissionAllowlistPackages()
            .contains(permission.getPermissionInfo().packageName)) {
      return true;
    }
    Boolean privilegedPermissionAllowlistState =
        getPrivilegedPermissionAllowlistState(
            mutateStateScope, packageState, permission.getPermissionInfo().name);
    if (privilegedPermissionAllowlistState != null) {
      return privilegedPermissionAllowlistState.booleanValue();
    }
    if (packageState.isUpdatedSystemApp()) {
      return true;
    }
    if (!mutateStateScope.getNewState().getSystemState().isSystemReady()
        && !packageState.isApkInUpdatedApex()) {
      Log.w(
          LOG_TAG,
          "Privileged permission "
              + permission.getPermissionInfo().name
              + " for package "
              + packageState.getPackageName()
              + " ("
              + packageState.getPath()
              + ") not in privileged permission allowlist");
      if (RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_ENFORCE) {
        this.privilegedPermissionAllowlistViolations.add(
            packageState.getPackageName()
                + " ("
                + packageState.getPath()
                + "): "
                + permission.getPermissionInfo().name);
      }
    }
    return !RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_ENFORCE;
  }

  public final Boolean getPrivilegedPermissionAllowlistState(
      MutateStateScope mutateStateScope, PackageState packageState, String str) {
    PermissionAllowlist permissionAllowlist =
        mutateStateScope.getNewState().getSystemState().getPermissionAllowlist();
    String apexModuleName = packageState.getApexModuleName();
    String packageName = packageState.getPackageName();
    if (packageState.isVendor()) {
      return permissionAllowlist.getVendorPrivilegedAppAllowlistState(packageName, str);
    }
    if (packageState.isProduct()) {
      return permissionAllowlist.getProductPrivilegedAppAllowlistState(packageName, str);
    }
    if (packageState.isSystemExt()) {
      return permissionAllowlist.getSystemExtPrivilegedAppAllowlistState(packageName, str);
    }
    if (apexModuleName != null) {
      Boolean privilegedAppAllowlistState =
          permissionAllowlist.getPrivilegedAppAllowlistState(packageName, str);
      if (privilegedAppAllowlistState != null) {
        Log.w(
            LOG_TAG,
            "Package "
                + packageName
                + " is an APK in APEX but has permission allowlist on the system image, please"
                + " bundle the allowlist in the "
                + apexModuleName
                + " APEX instead");
      }
      Boolean apexPrivilegedAppAllowlistState =
          permissionAllowlist.getApexPrivilegedAppAllowlistState(apexModuleName, packageName, str);
      return apexPrivilegedAppAllowlistState == null
          ? privilegedAppAllowlistState
          : apexPrivilegedAppAllowlistState;
    }
    return permissionAllowlist.getPrivilegedAppAllowlistState(packageName, str);
  }

  public static /* synthetic */ int getAppIdTargetSdkVersion$default(
      UidPermissionPolicy uidPermissionPolicy,
      MutateStateScope mutateStateScope,
      int i,
      String str,
      AccessState accessState,
      int i2,
      Object obj) {
    if ((i2 & 4) != 0) {
      accessState = mutateStateScope.getNewState();
    }
    return uidPermissionPolicy.getAppIdTargetSdkVersion(mutateStateScope, i, str, accessState);
  }

  public final int getAppIdTargetSdkVersion(
      MutateStateScope mutateStateScope, int i, String str, AccessState accessState) {
    IndexedListSet indexedListSet =
        (IndexedListSet) accessState.getSystemState().getAppIds().get(i);
    int size = indexedListSet.size();
    int i2 = 10000;
    for (int i3 = 0; i3 < size; i3++) {
      Object obj =
          accessState
              .getSystemState()
              .getPackageStates()
              .get((String) indexedListSet.elementAt(i3));
      Intrinsics.checkNotNull(obj);
      PackageState packageState = (PackageState) obj;
      AndroidPackage androidPackage = packageState.getAndroidPackage();
      if (androidPackage != null && androidPackage.getRequestedPermissions().contains(str)) {
        AndroidPackage androidPackage2 = packageState.getAndroidPackage();
        Intrinsics.checkNotNull(androidPackage2);
        i2 = RangesKt___RangesKt.coerceAtMost(i2, androidPackage2.getTargetSdkVersion());
      }
    }
    return i2;
  }

  public final boolean shouldGrantPermissionByProtectionFlags(
      MutateStateScope mutateStateScope, PackageState packageState, Permission permission) {
    boolean shouldGrantPrivilegedOrOemPermission;
    AndroidPackage androidPackage = packageState.getAndroidPackage();
    Intrinsics.checkNotNull(androidPackage);
    SparseArray knownPackages = mutateStateScope.getNewState().getSystemState().getKnownPackages();
    String packageName = packageState.getPackageName();
    if ((IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 16)
            || IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 16384))
        && packageState.isSystem()) {
      if (packageState.isUpdatedSystemApp()) {
        PackageState packageState2 =
            (PackageState)
                mutateStateScope
                    .getNewState()
                    .getSystemState()
                    .getDisabledSystemPackageStates()
                    .get(packageState.getPackageName());
        AndroidPackage androidPackage2 =
            packageState2 != null ? packageState2.getAndroidPackage() : null;
        shouldGrantPrivilegedOrOemPermission =
            androidPackage2 != null
                && androidPackage2
                    .getRequestedPermissions()
                    .contains(permission.getPermissionInfo().name)
                && shouldGrantPrivilegedOrOemPermission(
                    mutateStateScope, packageState2, permission);
      } else {
        shouldGrantPrivilegedOrOemPermission =
            shouldGrantPrivilegedOrOemPermission(mutateStateScope, packageState, permission);
      }
      if (shouldGrantPrivilegedOrOemPermission) {
        return true;
      }
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 128)
        && androidPackage.getTargetSdkVersion() < 23) {
      return true;
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 256)
        && (ArraysKt___ArraysKt.contains((Object[]) knownPackages.get(2), packageName)
            || ArraysKt___ArraysKt.contains((Object[]) knownPackages.get(7), packageName))) {
      return true;
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 512)
        && ArraysKt___ArraysKt.contains((Object[]) knownPackages.get(4), packageName)) {
      return true;
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 1024)
        && packageState.isSystem()) {
      return true;
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 134217728)
        && androidPackage
            .getSigningDetails()
            .hasAncestorOrSelfWithDigest(permission.getPermissionInfo().knownCerts)) {
      return true;
    }
    if (IntExtensionsKt.hasBits(
            permission.getPermissionInfo().getProtectionFlags(),
            IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES)
        && ArraysKt___ArraysKt.contains((Object[]) knownPackages.get(1), packageName)) {
      return true;
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 65536)
        && ArraysKt___ArraysKt.contains((Object[]) knownPackages.get(6), packageName)) {
      return true;
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 524288)
        && ArraysKt___ArraysKt.contains((Object[]) knownPackages.get(10), packageName)) {
      return true;
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 1048576)
        && ArraysKt___ArraysKt.contains((Object[]) knownPackages.get(11), packageName)) {
      return true;
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 2097152)
        && ArraysKt___ArraysKt.contains((Object[]) knownPackages.get(12), packageName)) {
      return true;
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 8388608)
        && ArraysKt___ArraysKt.contains((Object[]) knownPackages.get(15), packageName)) {
      return true;
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 16777216)
        && ArraysKt___ArraysKt.contains((Object[]) knownPackages.get(16), packageName)
        && isDeviceOrProfileOwnerUid(mutateStateScope, packageState.getAppId())) {
      return true;
    }
    if (IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 33554432)
        && ArraysKt___ArraysKt.contains((Object[]) knownPackages.get(17), packageName)) {
      return true;
    }
    return IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 4194304)
        && packageState.getApexModuleName() != null;
  }

  public final boolean isDeviceOrProfileOwnerUid(MutateStateScope mutateStateScope, int i) {
    PackageState packageState;
    int userId = UserHandle.getUserId(i);
    String str =
        (String)
            mutateStateScope.getNewState().getSystemState().getDeviceAndProfileOwners().get(userId);
    return (str == null
            || (packageState =
                    (PackageState)
                        mutateStateScope.getNewState().getSystemState().getPackageStates().get(str))
                == null
            || i != UserHandle.getUid(userId, packageState.getAppId()))
        ? false
        : true;
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void onSystemReady(MutateStateScope mutateStateScope) {
    if (this.privilegedPermissionAllowlistViolations.isEmpty()) {
      return;
    }
    throw new IllegalStateException(
        "Signature|privileged permissions not in privileged permission allowlist: "
            + this.privilegedPermissionAllowlistViolations);
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void parseSystemState(BinaryXmlPullParser binaryXmlPullParser, AccessState accessState) {
    this.persistence.parseSystemState(binaryXmlPullParser, accessState);
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void serializeSystemState(
      BinaryXmlSerializer binaryXmlSerializer, AccessState accessState) {
    this.persistence.serializeSystemState(binaryXmlSerializer, accessState);
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void parseUserState(
      BinaryXmlPullParser binaryXmlPullParser, AccessState accessState, int i) {
    this.persistence.parseUserState(binaryXmlPullParser, accessState, i);
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void serializeUserState(
      BinaryXmlSerializer binaryXmlSerializer, AccessState accessState, int i) {
    this.persistence.serializeUserState(binaryXmlSerializer, accessState, i);
  }

  public final ArrayMap getPermissionTrees(GetStateScope getStateScope) {
    return getStateScope.getState().getSystemState().getPermissionTrees();
  }

  public final Permission findPermissionTree(GetStateScope getStateScope, String str) {
    Permission permission;
    ArrayMap permissionTrees = getStateScope.getState().getSystemState().getPermissionTrees();
    int size = permissionTrees.size();
    int i = 0;
    while (true) {
      permission = null;
      if (i >= size) {
        break;
      }
      Object keyAt = permissionTrees.keyAt(i);
      Permission permission2 = (Permission) permissionTrees.valueAt(i);
      String str2 = (String) keyAt;
      if (StringsKt__StringsJVMKt.startsWith$default(str, str2, false, 2, null)
          && str.length() > str2.length()
          && str.charAt(str2.length()) == '.') {
        permission = permission2;
      }
      if (permission != null) {
        break;
      }
      i++;
    }
    return permission;
  }

  public final ArrayMap getPermissionGroups(GetStateScope getStateScope) {
    return getStateScope.getState().getSystemState().getPermissionGroups();
  }

  public final ArrayMap getPermissions(GetStateScope getStateScope) {
    return getStateScope.getState().getSystemState().getPermissions();
  }

  public final void addPermission(
      MutateStateScope mutateStateScope, Permission permission, boolean z) {
    mutateStateScope
        .getNewState()
        .getSystemState()
        .getPermissions()
        .put(permission.getPermissionInfo().name, permission);
    mutateStateScope.getNewState().getSystemState().requestWrite(z);
  }

  public final void removePermission(MutateStateScope mutateStateScope, Permission permission) {
    mutateStateScope
        .getNewState()
        .getSystemState()
        .getPermissions()
        .remove(permission.getPermissionInfo().name);
    WritableState.requestWrite$default(
        mutateStateScope.getNewState().getSystemState(), false, 1, null);
  }

  public final ArrayMap getUidPermissionFlags(GetStateScope getStateScope, int i, int i2) {
    SparseArray uidPermissionFlags;
    UserState userState = (UserState) getStateScope.getState().getUserStates().get(i2);
    if (userState == null || (uidPermissionFlags = userState.getUidPermissionFlags()) == null) {
      return null;
    }
    return (ArrayMap) uidPermissionFlags.get(i);
  }

  public final int getPermissionFlags(GetStateScope getStateScope, int i, int i2, String str) {
    return getPermissionFlags(getStateScope.getState(), i, i2, str);
  }

  public final int getOldStatePermissionFlags(
      MutateStateScope mutateStateScope, int i, int i2, String str) {
    return getPermissionFlags(mutateStateScope.getOldState(), i, i2, str);
  }

  public final int getPermissionFlags(AccessState accessState, int i, int i2, String str) {
    int indexOfKey;
    SparseArray uidPermissionFlags;
    UserState userState = (UserState) accessState.getUserStates().get(i2);
    ArrayMap arrayMap =
        (userState == null || (uidPermissionFlags = userState.getUidPermissionFlags()) == null)
            ? null
            : (ArrayMap) uidPermissionFlags.get(i);
    Object obj = 0;
    if (arrayMap != null && (indexOfKey = arrayMap.indexOfKey(str)) >= 0) {
      obj = arrayMap.valueAt(indexOfKey);
    }
    return ((Number) obj).intValue();
  }

  public final boolean setPermissionFlags(
      MutateStateScope mutateStateScope, int i, int i2, String str, int i3) {
    return updatePermissionFlags(mutateStateScope, i, i2, str, -1, i3);
  }

  public final boolean updatePermissionFlags(
      MutateStateScope mutateStateScope, int i, int i2, String str, int i3, int i4) {
    Object obj;
    int indexOfKey;
    UserState userState = (UserState) mutateStateScope.getNewState().getUserStates().get(i2);
    SparseArray uidPermissionFlags = userState.getUidPermissionFlags();
    ArrayMap arrayMap = (ArrayMap) uidPermissionFlags.get(i);
    if (arrayMap != null && (indexOfKey = arrayMap.indexOfKey(str)) >= 0) {
      obj = arrayMap.valueAt(indexOfKey);
    } else {
      obj = 0;
    }
    int intValue = ((Number) obj).intValue();
    int andInv = IntExtensionsKt.andInv(intValue, i3) | (i4 & i3);
    if (intValue == andInv) {
      return false;
    }
    if (arrayMap == null) {
      arrayMap = new ArrayMap();
      uidPermissionFlags.set(i, arrayMap);
    }
    Integer valueOf = Integer.valueOf(andInv);
    int indexOfKey2 = arrayMap.indexOfKey(str);
    if (indexOfKey2 >= 0) {
      if (!Intrinsics.areEqual(valueOf, arrayMap.valueAt(indexOfKey2))) {
        if (Intrinsics.areEqual(valueOf, 0)) {
          arrayMap.removeAt(indexOfKey2);
        } else {
          arrayMap.setValueAt(indexOfKey2, valueOf);
        }
      }
    } else if (!Intrinsics.areEqual(valueOf, 0)) {
      arrayMap.put(str, valueOf);
    }
    if (arrayMap.isEmpty()) {
      uidPermissionFlags.remove(i);
    }
    WritableState.requestWrite$default(userState, false, 1, null);
    IndexedListSet indexedListSet = this.onPermissionFlagsChangedListeners;
    int size = indexedListSet.size();
    for (int i5 = 0; i5 < size; i5++) {
      ((OnPermissionFlagsChangedListener) indexedListSet.elementAt(i5))
          .onPermissionFlagsChanged(i, i2, str, intValue, andInv);
    }
    return true;
  }

  public final void addOnPermissionFlagsChangedListener(
      OnPermissionFlagsChangedListener onPermissionFlagsChangedListener) {
    synchronized (this.onPermissionFlagsChangedListenersLock) {
      IndexedListSet copy = this.onPermissionFlagsChangedListeners.copy();
      copy.add(onPermissionFlagsChangedListener);
      this.onPermissionFlagsChangedListeners = copy;
      Unit unit = Unit.INSTANCE;
    }
  }
}
