package com.android.internal.org.bouncycastle.crypto.generators;

import com.android.internal.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class SM2KeyPairGenerator extends ECKeyPairGenerator {
    public SM2KeyPairGenerator() {
        super("SM2KeyGen");
    }

    @Override // com.android.internal.org.bouncycastle.crypto.generators.ECKeyPairGenerator
    protected boolean isOutOfRangeD(BigInteger bigInteger, BigInteger bigInteger2) {
        return bigInteger.compareTo(ONE) < 0 || bigInteger.compareTo(bigInteger2.subtract(BigIntegers.ONE)) >= 0;
    }
}
