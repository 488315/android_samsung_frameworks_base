package com.android.internal.org.bouncycastle.math.field;

import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
class GF2Polynomial implements Polynomial {
    protected final int[] exponents;

    GF2Polynomial(int[] iArr) {
        this.exponents = Arrays.clone(iArr);
    }

    @Override // com.android.internal.org.bouncycastle.math.field.Polynomial
    public int getDegree() {
        return this.exponents[r1.length - 1];
    }

    @Override // com.android.internal.org.bouncycastle.math.field.Polynomial
    public int[] getExponentsPresent() {
        return Arrays.clone(this.exponents);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GF2Polynomial) {
            return Arrays.areEqual(this.exponents, ((GF2Polynomial) obj).exponents);
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.exponents);
    }
}
