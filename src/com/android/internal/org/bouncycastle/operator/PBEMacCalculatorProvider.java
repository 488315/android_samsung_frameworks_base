package com.android.internal.org.bouncycastle.operator;

import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes5.dex */
public interface PBEMacCalculatorProvider {
    MacCalculator get(AlgorithmIdentifier algorithmIdentifier, char[] cArr) throws OperatorCreationException;
}
