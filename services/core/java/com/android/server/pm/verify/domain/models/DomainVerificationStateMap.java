package com.android.server.pm.verify.domain.models;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public final class DomainVerificationStateMap {
    public final ArrayMap mPackageNameMap = new ArrayMap();
    public final ArrayMap mDomainSetIdMap = new ArrayMap();

    public final void put(
            String str, UUID uuid, DomainVerificationPkgState domainVerificationPkgState) {
        Object remove;
        int indexOfValue;
        if (this.mPackageNameMap.containsKey(str)
                && (remove = this.mPackageNameMap.remove(str)) != null
                && (indexOfValue = this.mDomainSetIdMap.indexOfValue(remove)) >= 0) {
            this.mDomainSetIdMap.removeAt(indexOfValue);
        }
        this.mPackageNameMap.put(str, domainVerificationPkgState);
        this.mDomainSetIdMap.put(uuid, domainVerificationPkgState);
    }

    public final String toString() {
        return "DomainVerificationStateMap{packageNameMap="
                + this.mPackageNameMap
                + ", domainSetIdMap="
                + this.mDomainSetIdMap
                + '}';
    }

    public Collection values() {
        return new ArrayList(this.mPackageNameMap.values());
    }
}
