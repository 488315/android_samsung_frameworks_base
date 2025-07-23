package com.android.internal.org.bouncycastle.asn1.bc;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x509.DigestInfo;
import com.android.internal.org.bouncycastle.asn1.x509.GeneralName;
import com.android.internal.org.bouncycastle.asn1.x509.GeneralNames;

/* loaded from: classes5.dex */
public class LinkedCertificate extends ASN1Object {
    private GeneralNames cACerts;
    private X500Name certIssuer;
    private final GeneralName certLocation;
    private final DigestInfo digest;

    public LinkedCertificate(DigestInfo digestInfo, GeneralName generalName) {
        this(digestInfo, generalName, null, null);
    }

    public LinkedCertificate(DigestInfo digestInfo, GeneralName generalName, X500Name x500Name, GeneralNames generalNames) {
        this.digest = digestInfo;
        this.certLocation = generalName;
        this.certIssuer = x500Name;
        this.cACerts = generalNames;
    }

    private LinkedCertificate(ASN1Sequence aSN1Sequence) {
        this.digest = DigestInfo.getInstance(aSN1Sequence.getObjectAt(0));
        this.certLocation = GeneralName.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() > 2) {
            for (int i = 2; i != aSN1Sequence.size(); i++) {
                ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
                int tagNo = aSN1TaggedObject.getTagNo();
                if (tagNo == 0) {
                    this.certIssuer = X500Name.getInstance(aSN1TaggedObject, false);
                } else if (tagNo == 1) {
                    this.cACerts = GeneralNames.getInstance(aSN1TaggedObject, false);
                } else {
                    throw new IllegalArgumentException("unknown tag in tagged field");
                }
            }
        }
    }

    public static LinkedCertificate getInstance(Object obj) {
        if (obj instanceof LinkedCertificate) {
            return (LinkedCertificate) obj;
        }
        if (obj != null) {
            return new LinkedCertificate(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DigestInfo getDigest() {
        return this.digest;
    }

    public GeneralName getCertLocation() {
        return this.certLocation;
    }

    public X500Name getCertIssuer() {
        return this.certIssuer;
    }

    public GeneralNames getCACerts() {
        return this.cACerts;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(4);
        aSN1EncodableVector.add(this.digest);
        aSN1EncodableVector.add(this.certLocation);
        if (this.certIssuer != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, (ASN1Encodable) this.certIssuer));
        }
        if (this.cACerts != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, (ASN1Encodable) this.cACerts));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
