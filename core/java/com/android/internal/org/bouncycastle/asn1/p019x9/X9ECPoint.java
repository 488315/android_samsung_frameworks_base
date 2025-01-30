package com.android.internal.org.bouncycastle.asn1.p019x9;

import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.math.p026ec.ECCurve;
import com.android.internal.org.bouncycastle.math.p026ec.ECPoint;
import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
public class X9ECPoint extends ASN1Object {

  /* renamed from: c */
  private ECCurve f741c;
  private final ASN1OctetString encoding;

  /* renamed from: p */
  private ECPoint f742p;

  public X9ECPoint(ECPoint p, boolean compressed) {
    this.f742p = p.normalize();
    this.encoding = new DEROctetString(p.getEncoded(compressed));
  }

  public X9ECPoint(ECCurve c, byte[] encoding) {
    this.f741c = c;
    this.encoding = new DEROctetString(Arrays.clone(encoding));
  }

  public X9ECPoint(ECCurve c, ASN1OctetString s) {
    this(c, s.getOctets());
  }

  public byte[] getPointEncoding() {
    return Arrays.clone(this.encoding.getOctets());
  }

  public synchronized ECPoint getPoint() {
    if (this.f742p == null) {
      this.f742p = this.f741c.decodePoint(this.encoding.getOctets()).normalize();
    }
    return this.f742p;
  }

  public boolean isPointCompressed() {
    byte[] octets = this.encoding.getOctets();
    if (octets == null || octets.length <= 0) {
      return false;
    }
    return octets[0] == 2 || octets[0] == 3;
  }

  @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object,
            // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
  public ASN1Primitive toASN1Primitive() {
    return this.encoding;
  }
}
