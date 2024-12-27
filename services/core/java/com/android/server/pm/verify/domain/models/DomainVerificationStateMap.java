package com.android.server.pm.verify.domain.models;

import android.util.ArrayMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DomainVerificationStateMap {
    public final ArrayMap mPackageNameMap = new ArrayMap();
    public final ArrayMap mDomainSetIdMap = new ArrayMap();

    public final void put(String str, UUID uuid, DomainVerificationPkgState domainVerificationPkgState) {
        Object remove;
        int indexOfValue;
        if (this.mPackageNameMap.containsKey(str) && (remove = this.mPackageNameMap.remove(str)) != null && (indexOfValue = this.mDomainSetIdMap.indexOfValue(remove)) >= 0) {
            this.mDomainSetIdMap.removeAt(indexOfValue);
        }
        this.mPackageNameMap.put(str, domainVerificationPkgState);
        this.mDomainSetIdMap.put(uuid, domainVerificationPkgState);
    }

    public final String toString() {
        return "DomainVerificationStateMap{packageNameMap=" + this.mPackageNameMap + ", domainSetIdMap=" + this.mDomainSetIdMap + '}';
    }

    public Collection values() {
        return new ArrayList(this.mPackageNameMap.values());
    }
}
