package com.android.internal.org.bouncycastle.asn1.x9;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.math.field.PolynomialExtensionField;
import com.android.internal.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class X9ECParameters extends ASN1Object implements X9ObjectIdentifiers {
  private static final BigInteger ONE = BigInteger.valueOf(1);
  private ECCurve curve;
  private X9FieldID fieldID;

  /* renamed from: g */
  private X9ECPoint f738g;

  /* renamed from: h */
  private BigInteger f739h;

  /* renamed from: n */
  private BigInteger f740n;
  private byte[] seed;

  private X9ECParameters(ASN1Sequence seq) {
    if (!(seq.getObjectAt(0) instanceof ASN1Integer)
        || !((ASN1Integer) seq.getObjectAt(0)).hasValue(ONE)) {
      throw new IllegalArgumentException("bad version in X9ECParameters");
    }
    this.f740n = ((ASN1Integer) seq.getObjectAt(4)).getValue();
    if (seq.size() == 6) {
      this.f739h = ((ASN1Integer) seq.getObjectAt(5)).getValue();
    }
    X9Curve x9c =
        new X9Curve(
            X9FieldID.getInstance(seq.getObjectAt(1)),
            this.f740n,
            this.f739h,
            ASN1Sequence.getInstance(seq.getObjectAt(2)));
    this.curve = x9c.getCurve();
    Object p = seq.getObjectAt(3);
    if (p instanceof X9ECPoint) {
      this.f738g = (X9ECPoint) p;
    } else {
      this.f738g = new X9ECPoint(this.curve, (ASN1OctetString) p);
    }
    this.seed = x9c.getSeed();
  }

  public static X9ECParameters getInstance(Object obj) {
    if (obj instanceof X9ECParameters) {
      return (X9ECParameters) obj;
    }
    if (obj != null) {
      return new X9ECParameters(ASN1Sequence.getInstance(obj));
    }
    return null;
  }

  public X9ECParameters(ECCurve curve, X9ECPoint g, BigInteger n) {
    this(curve, g, n, null, null);
  }

  public X9ECParameters(ECCurve curve, X9ECPoint g, BigInteger n, BigInteger h) {
    this(curve, g, n, h, null);
  }

  public X9ECParameters(ECCurve curve, X9ECPoint g, BigInteger n, BigInteger h, byte[] seed) {
    this.curve = curve;
    this.f738g = g;
    this.f740n = n;
    this.f739h = h;
    this.seed = Arrays.clone(seed);
    if (ECAlgorithms.isFpCurve(curve)) {
      this.fieldID = new X9FieldID(curve.getField().getCharacteristic());
      return;
    }
    if (ECAlgorithms.isF2mCurve(curve)) {
      PolynomialExtensionField field = (PolynomialExtensionField) curve.getField();
      int[] exponents = field.getMinimalPolynomial().getExponentsPresent();
      if (exponents.length == 3) {
        this.fieldID = new X9FieldID(exponents[2], exponents[1]);
        return;
      } else {
        if (exponents.length == 5) {
          this.fieldID = new X9FieldID(exponents[4], exponents[1], exponents[2], exponents[3]);
          return;
        }
        throw new IllegalArgumentException("Only trinomial and pentomial curves are supported");
      }
    }
    throw new IllegalArgumentException("'curve' is of an unsupported type");
  }

  public ECCurve getCurve() {
    return this.curve;
  }

  public ECPoint getG() {
    return this.f738g.getPoint();
  }

  public BigInteger getN() {
    return this.f740n;
  }

  public BigInteger getH() {
    return this.f739h;
  }

  public byte[] getSeed() {
    return Arrays.clone(this.seed);
  }

  public boolean hasSeed() {
    return this.seed != null;
  }

  public X9Curve getCurveEntry() {
    return new X9Curve(this.curve, this.seed);
  }

  public X9FieldID getFieldIDEntry() {
    return this.fieldID;
  }

  public X9ECPoint getBaseEntry() {
    return this.f738g;
  }

  @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object,
            // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
  public ASN1Primitive toASN1Primitive() {
    ASN1EncodableVector v = new ASN1EncodableVector(6);
    v.add(new ASN1Integer(ONE));
    v.add(this.fieldID);
    v.add(new X9Curve(this.curve, this.seed));
    v.add(this.f738g);
    v.add(new ASN1Integer(this.f740n));
    BigInteger bigInteger = this.f739h;
    if (bigInteger != null) {
      v.add(new ASN1Integer(bigInteger));
    }
    return new DERSequence(v);
  }
}
