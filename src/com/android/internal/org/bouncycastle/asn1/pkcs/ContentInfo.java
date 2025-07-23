package com.android.internal.org.bouncycastle.asn1.pkcs;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.BERSequence;
import com.android.internal.org.bouncycastle.asn1.BERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.DLSequence;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public class ContentInfo extends ASN1Object implements PKCSObjectIdentifiers {
    private ASN1Encodable content;
    private ASN1ObjectIdentifier contentType;
    private boolean isBer;

    public static ContentInfo getInstance(Object obj) {
        if (obj instanceof ContentInfo) {
            return (ContentInfo) obj;
        }
        if (obj != null) {
            return new ContentInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private ContentInfo(ASN1Sequence aSN1Sequence) {
        this.isBer = true;
        Enumeration objects = aSN1Sequence.getObjects();
        this.contentType = (ASN1ObjectIdentifier) objects.nextElement();
        if (objects.hasMoreElements()) {
            this.content = ((ASN1TaggedObject) objects.nextElement()).getExplicitBaseObject();
        }
        this.isBer = aSN1Sequence instanceof BERSequence;
    }

    public ContentInfo(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.isBer = true;
        this.contentType = aSN1ObjectIdentifier;
        this.content = aSN1Encodable;
    }

    public ASN1ObjectIdentifier getContentType() {
        return this.contentType;
    }

    public ASN1Encodable getContent() {
        return this.content;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.contentType);
        if (this.content != null) {
            aSN1EncodableVector.add(new BERTaggedObject(true, 0, this.content));
        }
        if (this.isBer) {
            return new BERSequence(aSN1EncodableVector);
        }
        return new DLSequence(aSN1EncodableVector);
    }
}
