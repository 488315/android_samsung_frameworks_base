package com.android.server.pm.permission;

import android.util.ArrayMap;

/* loaded from: classes3.dex */
public final class PermissionAllowlist {
  public final ArrayMap mOemAppAllowlist = new ArrayMap();
  public final ArrayMap mPrivilegedAppAllowlist = new ArrayMap();
  public final ArrayMap mVendorPrivilegedAppAllowlist = new ArrayMap();
  public final ArrayMap mProductPrivilegedAppAllowlist = new ArrayMap();
  public final ArrayMap mSystemExtPrivilegedAppAllowlist = new ArrayMap();
  public final ArrayMap mApexPrivilegedAppAllowlists = new ArrayMap();

  public ArrayMap getOemAppAllowlist() {
    return this.mOemAppAllowlist;
  }

  public ArrayMap getPrivilegedAppAllowlist() {
    return this.mPrivilegedAppAllowlist;
  }

  public ArrayMap getVendorPrivilegedAppAllowlist() {
    return this.mVendorPrivilegedAppAllowlist;
  }

  public ArrayMap getProductPrivilegedAppAllowlist() {
    return this.mProductPrivilegedAppAllowlist;
  }

  public ArrayMap getSystemExtPrivilegedAppAllowlist() {
    return this.mSystemExtPrivilegedAppAllowlist;
  }

  public ArrayMap getApexPrivilegedAppAllowlists() {
    return this.mApexPrivilegedAppAllowlists;
  }

  public Boolean getOemAppAllowlistState(String str, String str2) {
    ArrayMap arrayMap = (ArrayMap) this.mOemAppAllowlist.get(str);
    if (arrayMap == null) {
      return null;
    }
    return (Boolean) arrayMap.get(str2);
  }

  public Boolean getPrivilegedAppAllowlistState(String str, String str2) {
    ArrayMap arrayMap = (ArrayMap) this.mPrivilegedAppAllowlist.get(str);
    if (arrayMap == null) {
      return null;
    }
    return (Boolean) arrayMap.get(str2);
  }

  public Boolean getVendorPrivilegedAppAllowlistState(String str, String str2) {
    ArrayMap arrayMap = (ArrayMap) this.mVendorPrivilegedAppAllowlist.get(str);
    if (arrayMap == null) {
      return null;
    }
    return (Boolean) arrayMap.get(str2);
  }

  public Boolean getProductPrivilegedAppAllowlistState(String str, String str2) {
    ArrayMap arrayMap = (ArrayMap) this.mProductPrivilegedAppAllowlist.get(str);
    if (arrayMap == null) {
      return null;
    }
    return (Boolean) arrayMap.get(str2);
  }

  public Boolean getSystemExtPrivilegedAppAllowlistState(String str, String str2) {
    ArrayMap arrayMap = (ArrayMap) this.mSystemExtPrivilegedAppAllowlist.get(str);
    if (arrayMap == null) {
      return null;
    }
    return (Boolean) arrayMap.get(str2);
  }

  public Boolean getApexPrivilegedAppAllowlistState(String str, String str2, String str3) {
    ArrayMap arrayMap;
    ArrayMap arrayMap2 = (ArrayMap) this.mApexPrivilegedAppAllowlists.get(str);
    if (arrayMap2 == null || (arrayMap = (ArrayMap) arrayMap2.get(str2)) == null) {
      return null;
    }
    return (Boolean) arrayMap.get(str3);
  }
}
