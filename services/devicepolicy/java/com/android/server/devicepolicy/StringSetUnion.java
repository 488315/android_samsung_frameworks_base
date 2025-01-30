package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;
import android.app.admin.StringSetPolicyValue;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class StringSetUnion extends ResolutionMechanism {
  public String toString() {
    return "SetUnion {}";
  }

  @Override // com.android.server.devicepolicy.ResolutionMechanism
  /* renamed from: resolve */
  public PolicyValue mo5259resolve(LinkedHashMap linkedHashMap) {
    Objects.requireNonNull(linkedHashMap);
    if (linkedHashMap.isEmpty()) {
      return null;
    }
    HashSet hashSet = new HashSet();
    Iterator it = linkedHashMap.values().iterator();
    while (it.hasNext()) {
      hashSet.addAll((Collection) ((PolicyValue) it.next()).getValue());
    }
    return new StringSetPolicyValue(hashSet);
  }

  @Override // com.android.server.devicepolicy.ResolutionMechanism
  /* renamed from: getParcelableResolutionMechanism, reason: merged with bridge method [inline-methods] */
  public android.app.admin.StringSetUnion mo5258getParcelableResolutionMechanism() {
    return new android.app.admin.StringSetUnion();
  }
}
