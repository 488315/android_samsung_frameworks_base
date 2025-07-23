package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.ASN1UTCTime;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/* loaded from: classes5.dex */
public class TBSCertList extends ASN1Object {
    Extensions crlExtensions;
    X500Name issuer;
    Time nextUpdate;
    ASN1Sequence revokedCertificates;
    AlgorithmIdentifier signature;
    Time thisUpdate;
    ASN1Integer version;

    public static class CRLEntry extends ASN1Object {
        Extensions crlEntryExtensions;
        ASN1Sequence seq;

        private CRLEntry(ASN1Sequence aSN1Sequence) {
            if (aSN1Sequence.size() < 2 || aSN1Sequence.size() > 3) {
                throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
            }
            this.seq = aSN1Sequence;
        }

        public static CRLEntry getInstance(Object obj) {
            if (obj instanceof CRLEntry) {
                return (CRLEntry) obj;
            }
            if (obj != null) {
                return new CRLEntry(ASN1Sequence.getInstance(obj));
            }
            return null;
        }

        public ASN1Integer getUserCertificate() {
            return ASN1Integer.getInstance(this.seq.getObjectAt(0));
        }

        public Time getRevocationDate() {
            return Time.getInstance(this.seq.getObjectAt(1));
        }

        public Extensions getExtensions() {
            if (this.crlEntryExtensions == null && this.seq.size() == 3) {
                this.crlEntryExtensions = Extensions.getInstance(this.seq.getObjectAt(2));
            }
            return this.crlEntryExtensions;
        }

        @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
        public ASN1Primitive toASN1Primitive() {
            return this.seq;
        }

        public boolean hasExtensions() {
            return this.seq.size() == 3;
        }
    }

    private static class RevokedCertificatesEnumeration implements Enumeration {
        private final Enumeration en;

        RevokedCertificatesEnumeration(Enumeration enumeration) {
            this.en = enumeration;
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.en.hasMoreElements();
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            return CRLEntry.getInstance(this.en.nextElement());
        }
    }

    private static class EmptyEnumeration implements Enumeration {
        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return false;
        }

        private EmptyEnumeration() {
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            throw new NoSuchElementException("Empty Enumeration");
        }
    }

    public static TBSCertList getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static TBSCertList getInstance(Object obj) {
        if (obj instanceof TBSCertList) {
            return (TBSCertList) obj;
        }
        if (obj != null) {
            return new TBSCertList(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public TBSCertList(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 3 || aSN1Sequence.size() > 7) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        int i = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1Integer) {
            this.version = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
            i = 1;
        } else {
            this.version = null;
        }
        this.signature = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i));
        this.issuer = X500Name.getInstance(aSN1Sequence.getObjectAt(i + 1));
        int i2 = i + 3;
        this.thisUpdate = Time.getInstance(aSN1Sequence.getObjectAt(i + 2));
        if (i2 < aSN1Sequence.size() && ((aSN1Sequence.getObjectAt(i2) instanceof ASN1UTCTime) || (aSN1Sequence.getObjectAt(i2) instanceof ASN1GeneralizedTime) || (aSN1Sequence.getObjectAt(i2) instanceof Time))) {
            this.nextUpdate = Time.getInstance(aSN1Sequence.getObjectAt(i2));
            i2 = i + 4;
        }
        if (i2 < aSN1Sequence.size() && !(aSN1Sequence.getObjectAt(i2) instanceof ASN1TaggedObject)) {
            this.revokedCertificates = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(i2));
            i2++;
        }
        if (i2 >= aSN1Sequence.size() || !(aSN1Sequence.getObjectAt(i2) instanceof ASN1TaggedObject)) {
            return;
        }
        this.crlExtensions = Extensions.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(i2), true));
    }

    public int getVersionNumber() {
        ASN1Integer aSN1Integer = this.version;
        if (aSN1Integer == null) {
            return 1;
        }
        return aSN1Integer.intValueExact() + 1;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public X500Name getIssuer() {
        return this.issuer;
    }

    public Time getThisUpdate() {
        return this.thisUpdate;
    }

    public Time getNextUpdate() {
        return this.nextUpdate;
    }

    public CRLEntry[] getRevokedCertificates() {
        ASN1Sequence aSN1Sequence = this.revokedCertificates;
        if (aSN1Sequence == null) {
            return new CRLEntry[0];
        }
        int size = aSN1Sequence.size();
        CRLEntry[] cRLEntryArr = new CRLEntry[size];
        for (int i = 0; i < size; i++) {
            cRLEntryArr[i] = CRLEntry.getInstance(this.revokedCertificates.getObjectAt(i));
        }
        return cRLEntryArr;
    }

    public Enumeration getRevokedCertificateEnumeration() {
        if (this.revokedCertificates == null) {
            return new EmptyEnumeration();
        }
        return new RevokedCertificatesEnumeration(this.revokedCertificates.getObjects());
    }

    public Extensions getExtensions() {
        return this.crlExtensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(7);
        ASN1Integer aSN1Integer = this.version;
        if (aSN1Integer != null) {
            aSN1EncodableVector.add(aSN1Integer);
        }
        aSN1EncodableVector.add(this.signature);
        aSN1EncodableVector.add(this.issuer);
        aSN1EncodableVector.add(this.thisUpdate);
        Time time = this.nextUpdate;
        if (time != null) {
            aSN1EncodableVector.add(time);
        }
        ASN1Sequence aSN1Sequence = this.revokedCertificates;
        if (aSN1Sequence != null) {
            aSN1EncodableVector.add(aSN1Sequence);
        }
        if (this.crlExtensions != null) {
            aSN1EncodableVector.add(new DERTaggedObject(0, this.crlExtensions));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
