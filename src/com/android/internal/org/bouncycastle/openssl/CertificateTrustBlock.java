package com.android.internal.org.bouncycastle.openssl;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.ASN1UTF8String;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERUTF8String;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes5.dex */
public class CertificateTrustBlock {
    private String alias;
    private ASN1Sequence prohibitions;
    private ASN1Sequence uses;

    public CertificateTrustBlock(Set<ASN1ObjectIdentifier> set) {
        this(null, set, null);
    }

    public CertificateTrustBlock(String str, Set<ASN1ObjectIdentifier> set) {
        this(str, set, null);
    }

    public CertificateTrustBlock(String str, Set<ASN1ObjectIdentifier> set, Set<ASN1ObjectIdentifier> set2) {
        this.alias = str;
        this.uses = toSequence(set);
        this.prohibitions = toSequence(set2);
    }

    CertificateTrustBlock(byte[] bArr) {
        Enumeration objects = ASN1Sequence.getInstance(bArr).getObjects();
        while (objects.hasMoreElements()) {
            ASN1Encodable aSN1Encodable = (ASN1Encodable) objects.nextElement();
            if (aSN1Encodable instanceof ASN1Sequence) {
                this.uses = ASN1Sequence.getInstance(aSN1Encodable);
            } else if (aSN1Encodable instanceof ASN1TaggedObject) {
                this.prohibitions = ASN1Sequence.getInstance((ASN1TaggedObject) aSN1Encodable, false);
            } else if (aSN1Encodable instanceof ASN1UTF8String) {
                this.alias = ASN1UTF8String.getInstance(aSN1Encodable).getString();
            }
        }
    }

    public String getAlias() {
        return this.alias;
    }

    public Set<ASN1ObjectIdentifier> getUses() {
        return toSet(this.uses);
    }

    public Set<ASN1ObjectIdentifier> getProhibitions() {
        return toSet(this.prohibitions);
    }

    private Set<ASN1ObjectIdentifier> toSet(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence != null) {
            HashSet hashSet = new HashSet(aSN1Sequence.size());
            Enumeration objects = aSN1Sequence.getObjects();
            while (objects.hasMoreElements()) {
                hashSet.add(ASN1ObjectIdentifier.getInstance(objects.nextElement()));
            }
            return hashSet;
        }
        return Collections.EMPTY_SET;
    }

    private ASN1Sequence toSequence(Set<ASN1ObjectIdentifier> set) {
        if (set == null || set.isEmpty()) {
            return null;
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Iterator<ASN1ObjectIdentifier> it = set.iterator();
        while (it.hasNext()) {
            aSN1EncodableVector.add(it.next());
        }
        return new DERSequence(aSN1EncodableVector);
    }

    ASN1Sequence toASN1Sequence() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1Sequence aSN1Sequence = this.uses;
        if (aSN1Sequence != null) {
            aSN1EncodableVector.add(aSN1Sequence);
        }
        if (this.prohibitions != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, (ASN1Encodable) this.prohibitions));
        }
        if (this.alias != null) {
            aSN1EncodableVector.add(new DERUTF8String(this.alias));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
