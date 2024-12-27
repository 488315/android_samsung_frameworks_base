package com.android.internal.org.bouncycastle.operator;

import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface DigestAlgorithmIdentifierFinder {
    AlgorithmIdentifier find(AlgorithmIdentifier algorithmIdentifier);

    AlgorithmIdentifier find(String str);
}
