package com.android.internal.org.bouncycastle.math.p026ec;

/* loaded from: classes5.dex */
public abstract class AbstractECLookupTable implements ECLookupTable {
  @Override // com.android.internal.org.bouncycastle.math.p026ec.ECLookupTable
  public ECPoint lookupVar(int index) {
    return lookup(index);
  }
}
