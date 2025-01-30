package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class TopPriority extends ResolutionMechanism {
  public final List mHighestToLowestPriorityAuthorities;

  public TopPriority(List list) {
    Objects.requireNonNull(list);
    this.mHighestToLowestPriorityAuthorities = list;
  }

  @Override // com.android.server.devicepolicy.ResolutionMechanism
  /* renamed from: resolve */
  public PolicyValue mo5259resolve(LinkedHashMap linkedHashMap) {
    if (linkedHashMap.isEmpty()) {
      return null;
    }
    for (final String str : this.mHighestToLowestPriorityAuthorities) {
      Optional findFirst =
          linkedHashMap.keySet().stream()
              .filter(
                  new Predicate() { // from class:
                    // com.android.server.devicepolicy.TopPriority$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                      boolean lambda$resolve$0;
                      lambda$resolve$0 = TopPriority.lambda$resolve$0(str, (EnforcingAdmin) obj);
                      return lambda$resolve$0;
                    }
                  })
              .findFirst();
      if (findFirst.isPresent()) {
        return (PolicyValue) linkedHashMap.get(findFirst.get());
      }
    }
    return (PolicyValue)
        ((Map.Entry) linkedHashMap.entrySet().stream().findFirst().get()).getValue();
  }

  public static /* synthetic */ boolean lambda$resolve$0(
      String str, EnforcingAdmin enforcingAdmin) {
    return enforcingAdmin.hasAuthority(str);
  }

  @Override // com.android.server.devicepolicy.ResolutionMechanism
  /* renamed from: getParcelableResolutionMechanism, reason: merged with bridge method [inline-methods] */
  public android.app.admin.TopPriority mo5258getParcelableResolutionMechanism() {
    return new android.app.admin.TopPriority(getParcelableAuthorities());
  }

  public final List getParcelableAuthorities() {
    ArrayList arrayList = new ArrayList();
    Iterator it = this.mHighestToLowestPriorityAuthorities.iterator();
    while (it.hasNext()) {
      arrayList.add(EnforcingAdmin.getParcelableAuthority((String) it.next()));
    }
    return arrayList;
  }

  public String toString() {
    return "TopPriority { mHighestToLowestPriorityAuthorities= "
        + this.mHighestToLowestPriorityAuthorities
        + " }";
  }
}
