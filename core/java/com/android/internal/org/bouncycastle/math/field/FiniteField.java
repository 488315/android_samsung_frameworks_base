package com.android.internal.org.bouncycastle.math.field;

import java.math.BigInteger;

public interface FiniteField {
    BigInteger getCharacteristic();

    int getDimension();
}
