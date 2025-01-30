package com.android.server.permission.access;

import android.content.pm.PermissionGroupInfo;
import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.server.permission.access.collection.IndexedListSet;
import com.android.server.permission.access.collection.IntSet;
import com.android.server.permission.access.permission.Permission;
import com.android.server.permission.jarjar.kotlin.collections.MapsKt__MapsKt;
import com.android.server.pm.permission.PermissionAllowlist;
import java.util.Map;

/* compiled from: AccessState.kt */
/* loaded from: classes2.dex */
public final class SystemState extends WritableState {
  public final SparseArray appIds;
  public Map configPermissions;
  public SparseArray deviceAndProfileOwners;
  public Map disabledSystemPackageStates;
  public ArrayMap implicitToSourcePermissions;
  public boolean isLeanback;
  public boolean isSystemReady;
  public SparseArray knownPackages;
  public Map packageStates;
  public PermissionAllowlist permissionAllowlist;
  public final ArrayMap permissionGroups;
  public final ArrayMap permissionTrees;
  public final ArrayMap permissions;
  public IndexedListSet privilegedPermissionAllowlistPackages;
  public final IntSet userIds;

  public final IntSet getUserIds() {
    return this.userIds;
  }

  public final Map getPackageStates() {
    return this.packageStates;
  }

  public final void setPackageStates(Map map) {
    this.packageStates = map;
  }

  public final Map getDisabledSystemPackageStates() {
    return this.disabledSystemPackageStates;
  }

  public final void setDisabledSystemPackageStates(Map map) {
    this.disabledSystemPackageStates = map;
  }

  public final SparseArray getAppIds() {
    return this.appIds;
  }

  public final SparseArray getKnownPackages() {
    return this.knownPackages;
  }

  public final void setKnownPackages(SparseArray sparseArray) {
    this.knownPackages = sparseArray;
  }

  public final boolean isLeanback() {
    return this.isLeanback;
  }

  public final void setLeanback(boolean z) {
    this.isLeanback = z;
  }

  public final Map getConfigPermissions() {
    return this.configPermissions;
  }

  public final void setConfigPermissions(Map map) {
    this.configPermissions = map;
  }

  public final IndexedListSet getPrivilegedPermissionAllowlistPackages() {
    return this.privilegedPermissionAllowlistPackages;
  }

  public final void setPrivilegedPermissionAllowlistPackages(IndexedListSet indexedListSet) {
    this.privilegedPermissionAllowlistPackages = indexedListSet;
  }

  public final PermissionAllowlist getPermissionAllowlist() {
    return this.permissionAllowlist;
  }

  public final void setPermissionAllowlist(PermissionAllowlist permissionAllowlist) {
    this.permissionAllowlist = permissionAllowlist;
  }

  public final ArrayMap getImplicitToSourcePermissions() {
    return this.implicitToSourcePermissions;
  }

  public final void setImplicitToSourcePermissions(ArrayMap arrayMap) {
    this.implicitToSourcePermissions = arrayMap;
  }

  public final boolean isSystemReady() {
    return this.isSystemReady;
  }

  public final void setSystemReady(boolean z) {
    this.isSystemReady = z;
  }

  public final SparseArray getDeviceAndProfileOwners() {
    return this.deviceAndProfileOwners;
  }

  public final ArrayMap getPermissionGroups() {
    return this.permissionGroups;
  }

  public final ArrayMap getPermissionTrees() {
    return this.permissionTrees;
  }

  public final ArrayMap getPermissions() {
    return this.permissions;
  }

  public SystemState(
      IntSet intSet,
      Map map,
      Map map2,
      SparseArray sparseArray,
      SparseArray sparseArray2,
      boolean z,
      Map map3,
      IndexedListSet indexedListSet,
      PermissionAllowlist permissionAllowlist,
      ArrayMap arrayMap,
      boolean z2,
      SparseArray sparseArray3,
      ArrayMap arrayMap2,
      ArrayMap arrayMap3,
      ArrayMap arrayMap4) {
    this.userIds = intSet;
    this.packageStates = map;
    this.disabledSystemPackageStates = map2;
    this.appIds = sparseArray;
    this.knownPackages = sparseArray2;
    this.isLeanback = z;
    this.configPermissions = map3;
    this.privilegedPermissionAllowlistPackages = indexedListSet;
    this.permissionAllowlist = permissionAllowlist;
    this.implicitToSourcePermissions = arrayMap;
    this.isSystemReady = z2;
    this.deviceAndProfileOwners = sparseArray3;
    this.permissionGroups = arrayMap2;
    this.permissionTrees = arrayMap3;
    this.permissions = arrayMap4;
  }

  public SystemState() {
    this(
        new IntSet(),
        MapsKt__MapsKt.emptyMap(),
        MapsKt__MapsKt.emptyMap(),
        new SparseArray(),
        new SparseArray(),
        false,
        MapsKt__MapsKt.emptyMap(),
        new IndexedListSet(),
        new PermissionAllowlist(),
        new ArrayMap(),
        false,
        new SparseArray(),
        new ArrayMap(),
        new ArrayMap(),
        new ArrayMap());
  }

  public final SystemState copy() {
    IntSet copy = this.userIds.copy();
    Map map = this.packageStates;
    Map map2 = this.disabledSystemPackageStates;
    SparseArray clone = this.appIds.clone();
    int size = clone.size();
    for (int i = 0; i < size; i++) {
      clone.setValueAt(i, ((IndexedListSet) clone.valueAt(i)).copy());
    }
    SparseArray sparseArray = this.knownPackages;
    boolean z = this.isLeanback;
    Map map3 = this.configPermissions;
    IndexedListSet indexedListSet = this.privilegedPermissionAllowlistPackages;
    PermissionAllowlist permissionAllowlist = this.permissionAllowlist;
    ArrayMap arrayMap = this.implicitToSourcePermissions;
    boolean z2 = this.isSystemReady;
    SparseArray sparseArray2 = this.deviceAndProfileOwners;
    ArrayMap arrayMap2 = new ArrayMap(this.permissionGroups);
    int i2 = 0;
    for (int size2 = arrayMap2.size(); i2 < size2; size2 = size2) {
      arrayMap2.setValueAt(i2, (PermissionGroupInfo) arrayMap2.valueAt(i2));
      i2++;
    }
    ArrayMap arrayMap3 = new ArrayMap(this.permissionTrees);
    int i3 = 0;
    for (int size3 = arrayMap3.size(); i3 < size3; size3 = size3) {
      arrayMap3.setValueAt(i3, (Permission) arrayMap3.valueAt(i3));
      i3++;
    }
    ArrayMap arrayMap4 = new ArrayMap(this.permissions);
    int i4 = 0;
    for (int size4 = arrayMap4.size(); i4 < size4; size4 = size4) {
      arrayMap4.setValueAt(i4, (Permission) arrayMap4.valueAt(i4));
      i4++;
    }
    return new SystemState(
        copy,
        map,
        map2,
        clone,
        sparseArray,
        z,
        map3,
        indexedListSet,
        permissionAllowlist,
        arrayMap,
        z2,
        sparseArray2,
        arrayMap2,
        arrayMap3,
        arrayMap4);
  }
}
