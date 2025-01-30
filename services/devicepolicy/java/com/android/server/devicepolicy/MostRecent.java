package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class MostRecent extends ResolutionMechanism {
  public String toString() {
    return "MostRecent {}";
  }

  @Override // com.android.server.devicepolicy.ResolutionMechanism
  /* renamed from: resolve */
  public PolicyValue mo5259resolve(LinkedHashMap linkedHashMap) {
    ArrayList arrayList = new ArrayList(linkedHashMap.entrySet());
    if (arrayList.isEmpty()) {
      return null;
    }
    return (PolicyValue) ((Map.Entry) arrayList.get(arrayList.size() - 1)).getValue();
  }

  @Override // com.android.server.devicepolicy.ResolutionMechanism
  /* renamed from: getParcelableResolutionMechanism, reason: merged with bridge method [inline-methods] */
  public android.app.admin.MostRecent mo5258getParcelableResolutionMechanism() {
    return new android.app.admin.MostRecent();
  }
}
