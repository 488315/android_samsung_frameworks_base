package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes5.dex */
public interface KEMRecipient extends Recipient {
    RecipientOperator getRecipientOperator(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, byte[] bArr) throws CMSException;
}
