package com.android.internal.org.bouncycastle.asn1.p019x9;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
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
public class DomainParameters extends ASN1Object {

    /* renamed from: g */
    private final ASN1Integer f734g;

    /* renamed from: j */
    private final ASN1Integer f735j;

    /* renamed from: p */
    private final ASN1Integer f736p;

    /* renamed from: q */
    private final ASN1Integer f737q;
    private final ValidationParams validationParams;

    public static DomainParameters getInstance(ASN1TaggedObject obj, boolean explicit) {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    public static DomainParameters getInstance(Object obj) {
        if (obj instanceof DomainParameters) {
            return (DomainParameters) obj;
        }
        if (obj != null) {
            return new DomainParameters(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DomainParameters(BigInteger p, BigInteger g, BigInteger q, BigInteger j, ValidationParams validationParams) {
        if (p == null) {
            throw new IllegalArgumentException("'p' cannot be null");
        }
        if (g == null) {
            throw new IllegalArgumentException("'g' cannot be null");
        }
        if (q == null) {
            throw new IllegalArgumentException("'q' cannot be null");
        }
        this.f736p = new ASN1Integer(p);
        this.f734g = new ASN1Integer(g);
        this.f737q = new ASN1Integer(q);
        if (j != null) {
            this.f735j = new ASN1Integer(j);
        } else {
            this.f735j = null;
        }
        this.validationParams = validationParams;
    }

    private DomainParameters(ASN1Sequence seq) {
        if (seq.size() < 3 || seq.size() > 5) {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }
        Enumeration e = seq.getObjects();
        this.f736p = ASN1Integer.getInstance(e.nextElement());
        this.f734g = ASN1Integer.getInstance(e.nextElement());
        this.f737q = ASN1Integer.getInstance(e.nextElement());
        ASN1Encodable next = getNext(e);
        if (next != null && (next instanceof ASN1Integer)) {
            this.f735j = ASN1Integer.getInstance(next);
            next = getNext(e);
        } else {
            this.f735j = null;
        }
        if (next != null) {
            this.validationParams = ValidationParams.getInstance(next.toASN1Primitive());
        } else {
            this.validationParams = null;
        }
    }

    private static ASN1Encodable getNext(Enumeration e) {
        if (e.hasMoreElements()) {
            return (ASN1Encodable) e.nextElement();
        }
        return null;
    }

    public BigInteger getP() {
        return this.f736p.getPositiveValue();
    }

    public BigInteger getG() {
        return this.f734g.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.f737q.getPositiveValue();
    }

    public BigInteger getJ() {
        ASN1Integer aSN1Integer = this.f735j;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.getPositiveValue();
    }

    public ValidationParams getValidationParams() {
        return this.validationParams;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector(5);
        v.add(this.f736p);
        v.add(this.f734g);
        v.add(this.f737q);
        ASN1Integer aSN1Integer = this.f735j;
        if (aSN1Integer != null) {
            v.add(aSN1Integer);
        }
        ValidationParams validationParams = this.validationParams;
        if (validationParams != null) {
            v.add(validationParams);
        }
        return new DERSequence(v);
    }
}
