package com.android.server.pm.verify.domain.models;

import android.util.ArrayMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/* loaded from: classes3.dex */
public class DomainVerificationStateMap {
  public final ArrayMap mPackageNameMap = new ArrayMap();
  public final ArrayMap mDomainSetIdMap = new ArrayMap();

  public int size() {
    return this.mPackageNameMap.size();
  }

  public Object valueAt(int i) {
    return this.mPackageNameMap.valueAt(i);
  }

  public Object get(String str) {
    return this.mPackageNameMap.get(str);
  }

  public Object get(UUID uuid) {
    return this.mDomainSetIdMap.get(uuid);
  }

  public void put(String str, UUID uuid, Object obj) {
    if (this.mPackageNameMap.containsKey(str)) {
      remove(str);
    }
    this.mPackageNameMap.put(str, obj);
    this.mDomainSetIdMap.put(uuid, obj);
  }

  public Object remove(String str) {
    int indexOfValue;
    Object remove = this.mPackageNameMap.remove(str);
    if (remove != null && (indexOfValue = this.mDomainSetIdMap.indexOfValue(remove)) >= 0) {
      this.mDomainSetIdMap.removeAt(indexOfValue);
    }
    return remove;
  }

  public Object remove(UUID uuid) {
    int indexOfValue;
    Object remove = this.mDomainSetIdMap.remove(uuid);
    if (remove != null && (indexOfValue = this.mPackageNameMap.indexOfValue(remove)) >= 0) {
      this.mPackageNameMap.removeAt(indexOfValue);
    }
    return remove;
  }

  public Collection values() {
    return new ArrayList(this.mPackageNameMap.values());
  }

  public String toString() {
    return "DomainVerificationStateMap{packageNameMap="
        + this.mPackageNameMap
        + ", domainSetIdMap="
        + this.mDomainSetIdMap
        + '}';
  }
}
