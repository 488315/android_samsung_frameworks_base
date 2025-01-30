package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import java.math.BigInteger;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public class DSAParameter extends ASN1Object {

    /* renamed from: g */
    ASN1Integer f714g;

    /* renamed from: p */
    ASN1Integer f715p;

    /* renamed from: q */
    ASN1Integer f716q;

    public static DSAParameter getInstance(ASN1TaggedObject obj, boolean explicit) {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    public static DSAParameter getInstance(Object obj) {
        if (obj instanceof DSAParameter) {
            return (DSAParameter) obj;
        }
        if (obj != null) {
            return new DSAParameter(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DSAParameter(BigInteger p, BigInteger q, BigInteger g) {
        this.f715p = new ASN1Integer(p);
        this.f716q = new ASN1Integer(q);
        this.f714g = new ASN1Integer(g);
    }

    private DSAParameter(ASN1Sequence seq) {
        if (seq.size() != 3) {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }
        Enumeration e = seq.getObjects();
        this.f715p = ASN1Integer.getInstance(e.nextElement());
        this.f716q = ASN1Integer.getInstance(e.nextElement());
        this.f714g = ASN1Integer.getInstance(e.nextElement());
    }

    public BigInteger getP() {
        return this.f715p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.f716q.getPositiveValue();
    }

    public BigInteger getG() {
        return this.f714g.getPositiveValue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector(3);
        v.add(this.f715p);
        v.add(this.f716q);
        v.add(this.f714g);
        return new DERSequence(v);
    }
}
