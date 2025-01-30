package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes5.dex */
public interface CMSSignatureEncryptionAlgorithmFinder {
  AlgorithmIdentifier findEncryptionAlgorithm(AlgorithmIdentifier algorithmIdentifier);
}
