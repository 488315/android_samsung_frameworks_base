package com.android.server.devicepolicy;

import android.app.admin.IntegerPolicyValue;
import android.app.admin.PolicyValue;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FlagUnion extends ResolutionMechanism {
    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public final android.app.admin.ResolutionMechanism getParcelableResolutionMechanism() {
        return android.app.admin.FlagUnion.FLAG_UNION;
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public final PolicyValue resolve(LinkedHashMap linkedHashMap) {
        Objects.requireNonNull(linkedHashMap);
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        Integer num = 0;
        Iterator it = linkedHashMap.values().iterator();
        while (it.hasNext()) {
            num =
                    Integer.valueOf(
                            num.intValue()
                                    | ((Integer) ((PolicyValue) it.next()).getValue()).intValue());
        }
        return new IntegerPolicyValue(num.intValue());
    }

    public final String toString() {
        return "IntegerUnion {}";
    }
}
