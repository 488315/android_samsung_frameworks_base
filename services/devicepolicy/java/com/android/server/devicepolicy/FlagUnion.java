package com.android.server.devicepolicy;

import android.app.admin.IntegerPolicyValue;
import android.app.admin.PolicyValue;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class FlagUnion extends ResolutionMechanism {
  public String toString() {
    return "IntegerUnion {}";
  }

  @Override // com.android.server.devicepolicy.ResolutionMechanism
  /* renamed from: resolve, reason: merged with bridge method [inline-methods] */
  public IntegerPolicyValue mo5259resolve(LinkedHashMap linkedHashMap) {
    Objects.requireNonNull(linkedHashMap);
    if (linkedHashMap.isEmpty()) {
      return null;
    }
    Integer num = 0;
    Iterator it = linkedHashMap.values().iterator();
    while (it.hasNext()) {
      num =
          Integer.valueOf(
              num.intValue() | ((Integer) ((PolicyValue) it.next()).getValue()).intValue());
    }
    return new IntegerPolicyValue(num.intValue());
  }

  @Override // com.android.server.devicepolicy.ResolutionMechanism
  /* renamed from: getParcelableResolutionMechanism, reason: merged with bridge method [inline-methods] */
  public android.app.admin.FlagUnion mo5258getParcelableResolutionMechanism() {
    return android.app.admin.FlagUnion.FLAG_UNION;
  }
}
