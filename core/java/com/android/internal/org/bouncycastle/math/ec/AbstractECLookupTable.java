package com.android.internal.org.bouncycastle.math.ec;

public abstract class AbstractECLookupTable implements ECLookupTable {
    @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
    public ECPoint lookupVar(int index) {
        return lookup(index);
    }
}
