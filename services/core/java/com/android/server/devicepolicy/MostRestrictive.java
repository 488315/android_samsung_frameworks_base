package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MostRestrictive extends ResolutionMechanism {
    public final List mMostToLeastRestrictive;

    public MostRestrictive(List list) {
        this.mMostToLeastRestrictive = list;
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public final android.app.admin.ResolutionMechanism getParcelableResolutionMechanism() {
        return new android.app.admin.MostRestrictive(this.mMostToLeastRestrictive);
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public final PolicyValue resolve(LinkedHashMap linkedHashMap) {
        return resolve(new ArrayList(linkedHashMap.values()));
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public final PolicyValue resolve(List list) {
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.isEmpty()) {
            return null;
        }
        for (PolicyValue policyValue : this.mMostToLeastRestrictive) {
            if (arrayList.contains(policyValue)) {
                return policyValue;
            }
        }
        return (PolicyValue) arrayList.get(0);
    }

    public final String toString() {
        return "MostRestrictive { mMostToLeastRestrictive= " + this.mMostToLeastRestrictive + " }";
    }
}
