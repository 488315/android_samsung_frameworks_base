package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
