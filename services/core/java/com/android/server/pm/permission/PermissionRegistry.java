package com.android.server.pm.permission;

import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.server.pm.pkg.component.ParsedPermissionGroup;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class PermissionRegistry {
  public final ArrayMap mPermissions = new ArrayMap();
  public final ArrayMap mPermissionTrees = new ArrayMap();
  public final ArrayMap mPermissionGroups = new ArrayMap();
  public final ArrayMap mAppOpPermissionPackages = new ArrayMap();

  public Collection getPermissions() {
    return this.mPermissions.values();
  }

  public Permission getPermission(String str) {
    return (Permission) this.mPermissions.get(str);
  }

  public void addPermission(Permission permission) {
    this.mPermissions.put(permission.getName(), permission);
  }

  public void removePermission(String str) {
    this.mPermissions.remove(str);
  }

  public Collection getPermissionTrees() {
    return this.mPermissionTrees.values();
  }

  public Permission getPermissionTree(String str) {
    return (Permission) this.mPermissionTrees.get(str);
  }

  public void addPermissionTree(Permission permission) {
    this.mPermissionTrees.put(permission.getName(), permission);
  }

  public void transferPermissions(String str, String str2) {
    int i = 0;
    while (i < 2) {
      Iterator it = (i == 0 ? this.mPermissionTrees : this.mPermissions).values().iterator();
      while (it.hasNext()) {
        ((Permission) it.next()).transfer(str, str2);
      }
      i++;
    }
  }

  public Collection getPermissionGroups() {
    return this.mPermissionGroups.values();
  }

  public ParsedPermissionGroup getPermissionGroup(String str) {
    return (ParsedPermissionGroup) this.mPermissionGroups.get(str);
  }

  public void addPermissionGroup(ParsedPermissionGroup parsedPermissionGroup) {
    this.mPermissionGroups.put(parsedPermissionGroup.getName(), parsedPermissionGroup);
  }

  public ArrayMap getAllAppOpPermissionPackages() {
    return this.mAppOpPermissionPackages;
  }

  public ArraySet getAppOpPermissionPackages(String str) {
    return (ArraySet) this.mAppOpPermissionPackages.get(str);
  }

  public void addAppOpPermissionPackage(String str, String str2) {
    ArraySet arraySet = (ArraySet) this.mAppOpPermissionPackages.get(str);
    if (arraySet == null) {
      arraySet = new ArraySet();
      this.mAppOpPermissionPackages.put(str, arraySet);
    }
    arraySet.add(str2);
  }

  public void removeAppOpPermissionPackage(String str, String str2) {
    ArraySet arraySet = (ArraySet) this.mAppOpPermissionPackages.get(str);
    if (arraySet != null && arraySet.remove(str2) && arraySet.isEmpty()) {
      this.mAppOpPermissionPackages.remove(str);
    }
  }

  public Permission enforcePermissionTree(String str, int i) {
    return Permission.enforcePermissionTree(this.mPermissionTrees.values(), str, i);
  }
}
