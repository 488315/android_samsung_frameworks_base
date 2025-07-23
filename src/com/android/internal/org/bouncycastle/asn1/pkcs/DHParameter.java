package com.android.internal.org.bouncycastle.asn1.pkcs;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import java.math.BigInteger;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public class DHParameter extends ASN1Object {
    ASN1Integer g;
    ASN1Integer l;
    ASN1Integer p;

    public DHParameter(BigInteger bigInteger, BigInteger bigInteger2, int i) {
        this.p = new ASN1Integer(bigInteger);
        this.g = new ASN1Integer(bigInteger2);
        if (i != 0) {
            this.l = new ASN1Integer(i);
        } else {
            this.l = null;
        }
    }

    public static DHParameter getInstance(Object obj) {
        if (obj instanceof DHParameter) {
            return (DHParameter) obj;
        }
        if (obj != null) {
            return new DHParameter(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private DHParameter(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.p = ASN1Integer.getInstance(objects.nextElement());
        this.g = ASN1Integer.getInstance(objects.nextElement());
        if (objects.hasMoreElements()) {
            this.l = (ASN1Integer) objects.nextElement();
        } else {
            this.l = null;
        }
    }

    public BigInteger getP() {
        return this.p.getPositiveValue();
    }

    public BigInteger getG() {
        return this.g.getPositiveValue();
    }

    public BigInteger getL() {
        ASN1Integer aSN1Integer = this.l;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.getPositiveValue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.p);
        aSN1EncodableVector.add(this.g);
        if (getL() != null) {
            aSN1EncodableVector.add(this.l);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
