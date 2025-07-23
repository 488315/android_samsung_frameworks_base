package com.android.internal.org.bouncycastle.operator.bc;

import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.crypto.ExtendedDigest;
import com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest;
import com.android.internal.org.bouncycastle.crypto.digests.SHA224Digest;
import com.android.internal.org.bouncycastle.crypto.digests.SHA256Digest;
import com.android.internal.org.bouncycastle.crypto.digests.SHA384Digest;
import com.android.internal.org.bouncycastle.crypto.digests.SHA512Digest;
import com.android.internal.org.bouncycastle.operator.OperatorCreationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class BcDefaultDigestProvider implements BcDigestProvider {
    private static final Map lookup = createTable();
    public static final BcDigestProvider INSTANCE = new BcDefaultDigestProvider();

    private static Map createTable() {
        HashMap hashMap = new HashMap();
        hashMap.put(OIWObjectIdentifiers.idSHA1, new BcDigestProvider() { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider.1
            @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
            public ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) {
                return new SHA1Digest();
            }
        });
        hashMap.put(NISTObjectIdentifiers.id_sha224, new BcDigestProvider() { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider.2
            @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
            public ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) {
                return new SHA224Digest();
            }
        });
        hashMap.put(NISTObjectIdentifiers.id_sha256, new BcDigestProvider() { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider.3
            @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
            public ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) {
                return new SHA256Digest();
            }
        });
        hashMap.put(NISTObjectIdentifiers.id_sha384, new BcDigestProvider() { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider.4
            @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
            public ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) {
                return new SHA384Digest();
            }
        });
        hashMap.put(NISTObjectIdentifiers.id_sha512, new BcDigestProvider() { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider.5
            @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
            public ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) {
                return new SHA512Digest();
            }
        });
        return Collections.unmodifiableMap(hashMap);
    }

    private BcDefaultDigestProvider() {
    }

    @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
    public ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) throws OperatorCreationException {
        BcDigestProvider bcDigestProvider = (BcDigestProvider) lookup.get(algorithmIdentifier.getAlgorithm());
        if (bcDigestProvider == null) {
            throw new OperatorCreationException("cannot recognise digest");
        }
        return bcDigestProvider.get(algorithmIdentifier);
    }
}
