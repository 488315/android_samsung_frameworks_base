package com.android.internal.org.bouncycastle.asn1.ocsp;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERTaggedObject;
import com.android.internal.org.bouncycastle.asn1.x509.Extensions;
import com.android.internal.org.bouncycastle.asn1.x509.GeneralName;
import com.android.internal.org.bouncycastle.asn1.x509.X509Extensions;

/* loaded from: classes5.dex */
public class TBSRequest extends ASN1Object {
    private static final ASN1Integer V1 = new ASN1Integer(0);
    Extensions requestExtensions;
    ASN1Sequence requestList;
    GeneralName requestorName;
    ASN1Integer version;
    boolean versionSet;

    public TBSRequest(GeneralName generalName, ASN1Sequence aSN1Sequence, X509Extensions x509Extensions) {
        this.version = V1;
        this.requestorName = generalName;
        this.requestList = aSN1Sequence;
        this.requestExtensions = Extensions.getInstance(x509Extensions);
    }

    public TBSRequest(GeneralName generalName, ASN1Sequence aSN1Sequence, Extensions extensions) {
        this.version = V1;
        this.requestorName = generalName;
        this.requestList = aSN1Sequence;
        this.requestExtensions = extensions;
    }

    private TBSRequest(ASN1Sequence aSN1Sequence) {
        int i = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject) {
            if (((ASN1TaggedObject) aSN1Sequence.getObjectAt(0)).getTagNo() == 0) {
                this.versionSet = true;
                this.version = ASN1Integer.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(0), true);
                i = 1;
            } else {
                this.version = V1;
            }
        } else {
            this.version = V1;
        }
        if (aSN1Sequence.getObjectAt(i) instanceof ASN1TaggedObject) {
            this.requestorName = GeneralName.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(i), true);
            i++;
        }
        int i2 = i + 1;
        this.requestList = (ASN1Sequence) aSN1Sequence.getObjectAt(i);
        if (aSN1Sequence.size() == i + 2) {
            this.requestExtensions = Extensions.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(i2), true);
        }
    }

    public static TBSRequest getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static TBSRequest getInstance(Object obj) {
        if (obj instanceof TBSRequest) {
            return (TBSRequest) obj;
        }
        if (obj != null) {
            return new TBSRequest(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public GeneralName getRequestorName() {
        return this.requestorName;
    }

    public ASN1Sequence getRequestList() {
        return this.requestList;
    }

    public Extensions getRequestExtensions() {
        return this.requestExtensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(4);
        if (!this.version.equals((ASN1Primitive) V1) || this.versionSet) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, (ASN1Encodable) this.version));
        }
        if (this.requestorName != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, (ASN1Encodable) this.requestorName));
        }
        aSN1EncodableVector.add(this.requestList);
        if (this.requestExtensions != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, (ASN1Encodable) this.requestExtensions));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
