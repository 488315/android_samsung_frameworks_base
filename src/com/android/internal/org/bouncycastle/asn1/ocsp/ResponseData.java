package com.android.internal.org.bouncycastle.asn1.ocsp;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.x509.Extensions;
import com.android.internal.org.bouncycastle.asn1.x509.X509Extensions;

/* loaded from: classes5.dex */
public class ResponseData extends ASN1Object {
    private static final ASN1Integer V1 = new ASN1Integer(0);
    private ASN1GeneralizedTime producedAt;
    private ResponderID responderID;
    private Extensions responseExtensions;
    private ASN1Sequence responses;
    private ASN1Integer version;
    private boolean versionPresent;

    public ResponseData(ASN1Integer aSN1Integer, ResponderID responderID, ASN1GeneralizedTime aSN1GeneralizedTime, ASN1Sequence aSN1Sequence, Extensions extensions) {
        this.version = aSN1Integer;
        this.responderID = responderID;
        this.producedAt = aSN1GeneralizedTime;
        this.responses = aSN1Sequence;
        this.responseExtensions = extensions;
    }

    public ResponseData(ResponderID responderID, ASN1GeneralizedTime aSN1GeneralizedTime, ASN1Sequence aSN1Sequence, X509Extensions x509Extensions) {
        this(V1, responderID, ASN1GeneralizedTime.getInstance(aSN1GeneralizedTime), aSN1Sequence, Extensions.getInstance(x509Extensions));
    }

    public ResponseData(ResponderID responderID, ASN1GeneralizedTime aSN1GeneralizedTime, ASN1Sequence aSN1Sequence, Extensions extensions) {
        this(V1, responderID, aSN1GeneralizedTime, aSN1Sequence, extensions);
    }

    private ResponseData(ASN1Sequence aSN1Sequence) {
        int i = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject) {
            if (((ASN1TaggedObject) aSN1Sequence.getObjectAt(0)).getTagNo() == 0) {
                this.versionPresent = true;
                this.version = ASN1Integer.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(0), true);
                i = 1;
            } else {
                this.version = V1;
            }
        } else {
            this.version = V1;
        }
        this.responderID = ResponderID.getInstance(aSN1Sequence.getObjectAt(i));
        int i2 = i + 2;
        this.producedAt = ASN1GeneralizedTime.getInstance(aSN1Sequence.getObjectAt(i + 1));
        int i3 = i + 3;
        this.responses = (ASN1Sequence) aSN1Sequence.getObjectAt(i2);
        if (aSN1Sequence.size() > i3) {
            this.responseExtensions = Extensions.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(i3), true);
        }
    }

    public static ResponseData getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static ResponseData getInstance(Object obj) {
        if (obj instanceof ResponseData) {
            return (ResponseData) obj;
        }
        if (obj != null) {
            return new ResponseData(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public ResponderID getResponderID() {
        return this.responderID;
    }

    public ASN1GeneralizedTime getProducedAt() {
        return this.producedAt;
    }

    public ASN1Sequence getResponses() {
        return this.responses;
    }

    public Extensions getResponseExtensions() {
        return this.responseExtensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(5);
        if (this.versionPresent || !this.version.equals((ASN1Primitive) V1)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, (ASN1Encodable) this.version));
        }
        aSN1EncodableVector.add(this.responderID);
        aSN1EncodableVector.add(this.producedAt);
        aSN1EncodableVector.add(this.responses);
        if (this.responseExtensions != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, (ASN1Encodable) this.responseExtensions));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
