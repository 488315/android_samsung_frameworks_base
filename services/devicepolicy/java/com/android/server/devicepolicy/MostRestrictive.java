package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class MostRestrictive extends ResolutionMechanism {
    public List mMostToLeastRestrictive;

    public MostRestrictive(List list) {
        this.mMostToLeastRestrictive = list;
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    /* renamed from: resolve */
    public PolicyValue mo5259resolve(LinkedHashMap linkedHashMap) {
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        for (PolicyValue policyValue : this.mMostToLeastRestrictive) {
            if (linkedHashMap.containsValue(policyValue)) {
                return policyValue;
            }
        }
        return (PolicyValue) ((Map.Entry) linkedHashMap.entrySet().stream().findFirst().get()).getValue();
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    /* renamed from: getParcelableResolutionMechanism, reason: merged with bridge method [inline-methods] */
    public android.app.admin.MostRestrictive mo5258getParcelableResolutionMechanism() {
        return new android.app.admin.MostRestrictive(this.mMostToLeastRestrictive);
    }

    public String toString() {
        return "MostRestrictive { mMostToLeastRestrictive= " + this.mMostToLeastRestrictive + " }";
    }
}
