package com.android.internal.org.bouncycastle.asn1.pkcs;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERNull;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class RSASSAPSSparams extends ASN1Object {
    public static final AlgorithmIdentifier DEFAULT_HASH_ALGORITHM;
    public static final AlgorithmIdentifier DEFAULT_MASK_GEN_FUNCTION;
    public static final ASN1Integer DEFAULT_SALT_LENGTH;
    public static final ASN1Integer DEFAULT_TRAILER_FIELD;
    private AlgorithmIdentifier hashAlgorithm;
    private AlgorithmIdentifier maskGenAlgorithm;
    private ASN1Integer saltLength;
    private ASN1Integer trailerField;

    static {
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
        DEFAULT_HASH_ALGORITHM = algorithmIdentifier;
        DEFAULT_MASK_GEN_FUNCTION = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier);
        DEFAULT_SALT_LENGTH = new ASN1Integer(20L);
        DEFAULT_TRAILER_FIELD = new ASN1Integer(1L);
    }

    public static RSASSAPSSparams getInstance(Object obj) {
        if (obj instanceof RSASSAPSSparams) {
            return (RSASSAPSSparams) obj;
        }
        if (obj != null) {
            return new RSASSAPSSparams(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public RSASSAPSSparams() {
        this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
        this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
        this.saltLength = DEFAULT_SALT_LENGTH;
        this.trailerField = DEFAULT_TRAILER_FIELD;
    }

    public RSASSAPSSparams(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, ASN1Integer aSN1Integer, ASN1Integer aSN1Integer2) {
        this.hashAlgorithm = algorithmIdentifier;
        this.maskGenAlgorithm = algorithmIdentifier2;
        this.saltLength = aSN1Integer;
        this.trailerField = aSN1Integer2;
    }

    private RSASSAPSSparams(ASN1Sequence aSN1Sequence) {
        this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
        this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
        this.saltLength = DEFAULT_SALT_LENGTH;
        this.trailerField = DEFAULT_TRAILER_FIELD;
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Sequence.getObjectAt(i);
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 0) {
                this.hashAlgorithm = AlgorithmIdentifier.getInstance(aSN1TaggedObject, true);
            } else if (tagNo == 1) {
                this.maskGenAlgorithm = AlgorithmIdentifier.getInstance(aSN1TaggedObject, true);
            } else if (tagNo == 2) {
                this.saltLength = ASN1Integer.getInstance(aSN1TaggedObject, true);
            } else if (tagNo == 3) {
                this.trailerField = ASN1Integer.getInstance(aSN1TaggedObject, true);
            } else {
                throw new IllegalArgumentException("unknown tag");
            }
        }
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public AlgorithmIdentifier getMaskGenAlgorithm() {
        return this.maskGenAlgorithm;
    }

    public BigInteger getSaltLength() {
        return this.saltLength.getValue();
    }

    public BigInteger getTrailerField() {
        return this.trailerField.getValue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(4);
        if (!this.hashAlgorithm.equals(DEFAULT_HASH_ALGORITHM)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, (ASN1Encodable) this.hashAlgorithm));
        }
        if (!this.maskGenAlgorithm.equals(DEFAULT_MASK_GEN_FUNCTION)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, (ASN1Encodable) this.maskGenAlgorithm));
        }
        if (!this.saltLength.equals((ASN1Primitive) DEFAULT_SALT_LENGTH)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, (ASN1Encodable) this.saltLength));
        }
        if (!this.trailerField.equals((ASN1Primitive) DEFAULT_TRAILER_FIELD)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 3, (ASN1Encodable) this.trailerField));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
