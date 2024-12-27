package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MostRecent extends ResolutionMechanism {
    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public final android.app.admin.ResolutionMechanism getParcelableResolutionMechanism() {
        return new android.app.admin.MostRecent();
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public final PolicyValue resolve(LinkedHashMap linkedHashMap) {
        ArrayList arrayList = new ArrayList(linkedHashMap.entrySet());
        if (arrayList.isEmpty()) {
            return null;
        }
        return (PolicyValue) ((Map.Entry) arrayList.get(arrayList.size() - 1)).getValue();
    }

    public final String toString() {
        return "MostRecent {}";
    }
}
