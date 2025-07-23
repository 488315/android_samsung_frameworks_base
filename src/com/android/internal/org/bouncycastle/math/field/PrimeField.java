package com.android.internal.org.bouncycastle.math.field;

import java.math.BigInteger;

/* loaded from: classes5.dex */
class PrimeField implements FiniteField {
    protected final BigInteger characteristic;

    @Override // com.android.internal.org.bouncycastle.math.field.FiniteField
    public int getDimension() {
        return 1;
    }

    PrimeField(BigInteger bigInteger) {
        this.characteristic = bigInteger;
    }

    @Override // com.android.internal.org.bouncycastle.math.field.FiniteField
    public BigInteger getCharacteristic() {
        return this.characteristic;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PrimeField) {
            return this.characteristic.equals(((PrimeField) obj).characteristic);
        }
        return false;
    }

    public int hashCode() {
        return this.characteristic.hashCode();
    }
}
