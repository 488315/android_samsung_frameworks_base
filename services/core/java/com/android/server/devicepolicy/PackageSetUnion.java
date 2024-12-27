package com.android.server.devicepolicy;

import android.app.admin.PackageSetPolicyValue;
import android.app.admin.PolicyValue;
import android.app.admin.StringSetUnion;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PackageSetUnion extends ResolutionMechanism {
    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public final android.app.admin.ResolutionMechanism getParcelableResolutionMechanism() {
        return new StringSetUnion();
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public final PolicyValue resolve(LinkedHashMap linkedHashMap) {
        Objects.requireNonNull(linkedHashMap);
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Iterator it = linkedHashMap.values().iterator();
        while (it.hasNext()) {
            hashSet.addAll((Collection) ((PolicyValue) it.next()).getValue());
        }
        return new PackageSetPolicyValue(hashSet);
    }

    public final String toString() {
        return "PackageSetUnion {}";
    }
}
