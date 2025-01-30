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

  /* renamed from: g */
  ASN1Integer f691g;

  /* renamed from: l */
  ASN1Integer f692l;

  /* renamed from: p */
  ASN1Integer f693p;

  public DHParameter(BigInteger p, BigInteger g, int l) {
    this.f693p = new ASN1Integer(p);
    this.f691g = new ASN1Integer(g);
    if (l != 0) {
      this.f692l = new ASN1Integer(l);
    } else {
      this.f692l = null;
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

  private DHParameter(ASN1Sequence seq) {
    Enumeration e = seq.getObjects();
    this.f693p = ASN1Integer.getInstance(e.nextElement());
    this.f691g = ASN1Integer.getInstance(e.nextElement());
    if (e.hasMoreElements()) {
      this.f692l = (ASN1Integer) e.nextElement();
    } else {
      this.f692l = null;
    }
  }

  public BigInteger getP() {
    return this.f693p.getPositiveValue();
  }

  public BigInteger getG() {
    return this.f691g.getPositiveValue();
  }

  public BigInteger getL() {
    ASN1Integer aSN1Integer = this.f692l;
    if (aSN1Integer == null) {
      return null;
    }
    return aSN1Integer.getPositiveValue();
  }

  @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object,
            // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
  public ASN1Primitive toASN1Primitive() {
    ASN1EncodableVector v = new ASN1EncodableVector(3);
    v.add(this.f693p);
    v.add(this.f691g);
    if (getL() != null) {
      v.add(this.f692l);
    }
    return new DERSequence(v);
  }
}
