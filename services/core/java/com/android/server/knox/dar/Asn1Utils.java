package com.android.server.knox.dar;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERPrintableString;
import java.io.IOException;
import java.security.cert.CertificateParsingException;

/* loaded from: classes2.dex */
public class Asn1Utils {
  public static byte[] getByteArrayFromAsn1(ASN1Encodable aSN1Encodable) {
    if (aSN1Encodable == null) {
      throw new CertificateParsingException("Expected DEROctetString");
    }
    if (aSN1Encodable instanceof ASN1TaggedObject) {
      aSN1Encodable = ((ASN1TaggedObject) aSN1Encodable).getObject();
    }
    if (aSN1Encodable instanceof DEROctetString) {
      return ((ASN1OctetString) aSN1Encodable).getOctets();
    }
    if (aSN1Encodable instanceof DERPrintableString) {
      return ((DERPrintableString) aSN1Encodable).getOctets();
    }
    throw new CertificateParsingException("Expected DEROctetString");
  }

  public static ASN1Sequence getAsn1SequenceFromBytes(byte[] bArr) {
    try {
      ASN1InputStream aSN1InputStream = new ASN1InputStream(bArr);
      try {
        ASN1Sequence asn1SequenceFromStream = getAsn1SequenceFromStream(aSN1InputStream);
        aSN1InputStream.close();
        return asn1SequenceFromStream;
      } finally {
      }
    } catch (IOException e) {
      throw new CertificateParsingException("Failed to parse SEQUENCE", e);
    }
  }

  public static ASN1Sequence getAsn1SequenceFromStream(ASN1InputStream aSN1InputStream) {
    ASN1OctetString readObject = aSN1InputStream.readObject();
    if (!(readObject instanceof ASN1OctetString)) {
      throw new CertificateParsingException(
          "Expected octet stream, found " + readObject.getClass().getName());
    }
    ASN1InputStream aSN1InputStream2 = new ASN1InputStream(readObject.getOctets());
    try {
      ASN1Sequence readObject2 = aSN1InputStream2.readObject();
      if (!(readObject2 instanceof ASN1Sequence)) {
        throw new CertificateParsingException(
            "Expected sequence, found " + readObject2.getClass().getName());
      }
      ASN1Sequence aSN1Sequence = readObject2;
      aSN1InputStream2.close();
      return aSN1Sequence;
    } catch (Throwable th) {
      try {
        aSN1InputStream2.close();
      } catch (Throwable th2) {
        th.addSuppressed(th2);
      }
      throw th;
    }
  }
}
