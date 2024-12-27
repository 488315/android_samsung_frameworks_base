package com.android.server.pm;

import android.util.ArrayMap;

import com.android.internal.util.ArrayUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
