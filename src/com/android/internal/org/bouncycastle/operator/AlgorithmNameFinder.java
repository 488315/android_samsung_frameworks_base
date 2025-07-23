package com.android.internal.org.bouncycastle.operator;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes5.dex */
public interface AlgorithmNameFinder {
    String getAlgorithmName(ASN1ObjectIdentifier aSN1ObjectIdentifier);

    String getAlgorithmName(AlgorithmIdentifier algorithmIdentifier);

    boolean hasAlgorithmName(ASN1ObjectIdentifier aSN1ObjectIdentifier);
}
