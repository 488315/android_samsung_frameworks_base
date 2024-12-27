package com.android.server.pm;

import android.util.Slog;

import java.util.Collections;
import java.util.Comparator;

public final class PolicyComparator implements Comparator {
    public boolean duplicateFound;

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        Policy policy = (Policy) obj;
        Policy policy2 = (Policy) obj2;
        if ((!policy.mPkgMap.isEmpty()) != (!policy2.mPkgMap.isEmpty())) {
            return policy.mPkgMap.isEmpty() ^ true ? -1 : 1;
        }
        if (policy.mCerts.equals(policy2.mCerts)) {
            if (policy.mSeinfo != null) {
                this.duplicateFound = true;
                Slog.e("SELinuxMMAC", "Duplicate policy entry: " + policy.toString());
            }
            if (!Collections.disjoint(policy.mPkgMap.keySet(), policy2.mPkgMap.keySet())) {
                this.duplicateFound = true;
                Slog.e("SELinuxMMAC", "Duplicate policy entry: " + policy.toString());
            }
        }
        return 0;
    }
}
