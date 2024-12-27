package com.android.internal.org.bouncycastle.operator;

import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes5.dex */
public interface DigestCalculatorProvider {
    DigestCalculator get(AlgorithmIdentifier algorithmIdentifier) throws OperatorCreationException;
}
