package com.android.internal.org.bouncycastle.asn1.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1Set;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public class SignerInfo extends ASN1Object {
    private ASN1Set authenticatedAttributes;
    private AlgorithmIdentifier digAlgorithm;
    private AlgorithmIdentifier digEncryptionAlgorithm;
    private ASN1OctetString encryptedDigest;
    private SignerIdentifier sid;
    private ASN1Set unauthenticatedAttributes;
    private ASN1Integer version;

    public static SignerInfo getInstance(Object obj) throws IllegalArgumentException {
        if (obj instanceof SignerInfo) {
            return (SignerInfo) obj;
        }
        if (obj != null) {
            return new SignerInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public SignerInfo(SignerIdentifier signerIdentifier, AlgorithmIdentifier algorithmIdentifier, ASN1Set aSN1Set, AlgorithmIdentifier algorithmIdentifier2, ASN1OctetString aSN1OctetString, ASN1Set aSN1Set2) {
        if (signerIdentifier.isTagged()) {
            this.version = new ASN1Integer(3L);
        } else {
            this.version = new ASN1Integer(1L);
        }
        this.sid = signerIdentifier;
        this.digAlgorithm = algorithmIdentifier;
        this.authenticatedAttributes = aSN1Set;
        this.digEncryptionAlgorithm = algorithmIdentifier2;
        this.encryptedDigest = aSN1OctetString;
        this.unauthenticatedAttributes = aSN1Set2;
    }

    public SignerInfo(SignerIdentifier signerIdentifier, AlgorithmIdentifier algorithmIdentifier, Attributes attributes, AlgorithmIdentifier algorithmIdentifier2, ASN1OctetString aSN1OctetString, Attributes attributes2) {
        if (signerIdentifier.isTagged()) {
            this.version = new ASN1Integer(3L);
        } else {
            this.version = new ASN1Integer(1L);
        }
        this.sid = signerIdentifier;
        this.digAlgorithm = algorithmIdentifier;
        this.authenticatedAttributes = ASN1Set.getInstance(attributes);
        this.digEncryptionAlgorithm = algorithmIdentifier2;
        this.encryptedDigest = aSN1OctetString;
        this.unauthenticatedAttributes = ASN1Set.getInstance(attributes2);
    }

    public SignerInfo(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.version = (ASN1Integer) objects.nextElement();
        this.sid = SignerIdentifier.getInstance(objects.nextElement());
        this.digAlgorithm = AlgorithmIdentifier.getInstance(objects.nextElement());
        Object nextElement = objects.nextElement();
        if (nextElement instanceof ASN1TaggedObject) {
            this.authenticatedAttributes = ASN1Set.getInstance((ASN1TaggedObject) nextElement, false);
            this.digEncryptionAlgorithm = AlgorithmIdentifier.getInstance(objects.nextElement());
        } else {
            this.authenticatedAttributes = null;
            this.digEncryptionAlgorithm = AlgorithmIdentifier.getInstance(nextElement);
        }
        this.encryptedDigest = DEROctetString.getInstance(objects.nextElement());
        if (objects.hasMoreElements()) {
            this.unauthenticatedAttributes = ASN1Set.getInstance((ASN1TaggedObject) objects.nextElement(), false);
        } else {
            this.unauthenticatedAttributes = null;
        }
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public SignerIdentifier getSID() {
        return this.sid;
    }

    public ASN1Set getAuthenticatedAttributes() {
        return this.authenticatedAttributes;
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.digAlgorithm;
    }

    public ASN1OctetString getEncryptedDigest() {
        return this.encryptedDigest;
    }

    public AlgorithmIdentifier getDigestEncryptionAlgorithm() {
        return this.digEncryptionAlgorithm;
    }

    public ASN1Set getUnauthenticatedAttributes() {
        return this.unauthenticatedAttributes;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(7);
        aSN1EncodableVector.add(this.version);
        aSN1EncodableVector.add(this.sid);
        aSN1EncodableVector.add(this.digAlgorithm);
        if (this.authenticatedAttributes != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, (ASN1Encodable) this.authenticatedAttributes));
        }
        aSN1EncodableVector.add(this.digEncryptionAlgorithm);
        aSN1EncodableVector.add(this.encryptedDigest);
        if (this.unauthenticatedAttributes != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, (ASN1Encodable) this.unauthenticatedAttributes));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
