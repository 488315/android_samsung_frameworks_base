package com.android.server.pm;

import android.util.ArrayMap;

import com.android.internal.util.ArrayUtils;

public final class PackageKeySetData {
    public final ArrayMap mKeySetAliases;
    public long mProperSigningKeySet;
    public long[] mUpgradeKeySets;

    public PackageKeySetData() {
        this.mKeySetAliases = new ArrayMap();
        this.mProperSigningKeySet = -1L;
    }

    public PackageKeySetData(PackageKeySetData packageKeySetData) {
        ArrayMap arrayMap = new ArrayMap();
        this.mKeySetAliases = arrayMap;
        this.mProperSigningKeySet = packageKeySetData.mProperSigningKeySet;
        this.mUpgradeKeySets = ArrayUtils.cloneOrNull(packageKeySetData.mUpgradeKeySets);
        arrayMap.putAll(packageKeySetData.mKeySetAliases);
    }
}
