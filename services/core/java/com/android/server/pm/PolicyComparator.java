package com.android.server.pm;

import android.util.Slog;
import java.util.Collections;
import java.util.Comparator;

/* compiled from: SELinuxMMAC.java */
/* loaded from: classes3.dex */
public final class PolicyComparator implements Comparator {
  public boolean duplicateFound = false;

  public boolean foundDuplicate() {
    return this.duplicateFound;
  }

  @Override // java.util.Comparator
  public int compare(Policy policy, Policy policy2) {
    if (policy.hasInnerPackages() != policy2.hasInnerPackages()) {
      return policy.hasInnerPackages() ? -1 : 1;
    }
    if (!policy.getSignatures().equals(policy2.getSignatures())) {
      return 0;
    }
    if (policy.hasGlobalSeinfo()) {
      this.duplicateFound = true;
      Slog.e("SELinuxMMAC", "Duplicate policy entry: " + policy.toString());
    }
    if (Collections.disjoint(
        policy.getInnerPackages().keySet(), policy2.getInnerPackages().keySet())) {
      return 0;
    }
    this.duplicateFound = true;
    Slog.e("SELinuxMMAC", "Duplicate policy entry: " + policy.toString());
    return 0;
  }
}
