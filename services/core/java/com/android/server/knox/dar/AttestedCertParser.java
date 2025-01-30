package com.android.server.knox.dar;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;

/* loaded from: classes2.dex */
public class AttestedCertParser {
  public IntegrityStatus mKnoxIngetrity;

  public AttestedCertParser(X509Certificate x509Certificate) {
    ASN1Sequence knoxExtensionSequence = getKnoxExtensionSequence(x509Certificate);
    if (knoxExtensionSequence != null) {
      for (int i = 0; i < knoxExtensionSequence.size(); i++) {
        try {
          ASN1TaggedObject objectAt = knoxExtensionSequence.getObjectAt(i);
          if (objectAt instanceof ASN1TaggedObject) {
            if (objectAt.getTagNo() == 5) {
              this.mKnoxIngetrity = new IntegrityStatus(objectAt.getObject());
              Log.i(
                  "AttestedCertParser",
                  "Found [Integrity Status] :\n" + this.mKnoxIngetrity.toString());
            } else {
              Log.d("AttestedCertParser", "Skip unknown case : " + objectAt.getTagNo());
            }
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          Log.e(
              "AttestedCertParser", "sequence index is too small to get challenge or idAttest", e);
          e.printStackTrace();
          return;
        }
      }
    }
  }

  public final ASN1Sequence getKnoxExtensionSequence(X509Certificate x509Certificate) {
    byte[] extensionValue = x509Certificate.getExtensionValue("1.3.6.1.4.1.236.11.3.23.7");
    if (extensionValue == null) {
      Log.e("AttestedCertParser", "getKnoxExtensionSequence : not include knox extension");
      return null;
    }
    String bytesToHex = bytesToHex(extensionValue);
    String substring = bytesToHex.substring(2, 4);
    String substring2 = bytesToHex.substring(10, 12);
    int parseInt = Integer.parseInt(substring, 16);
    int parseInt2 = Integer.parseInt(substring2, 16);
    String str = SystemProperties.get("ro.product.first_api_level");
    Log.d("AttestedCertParser", "firstApiLevel = " + str);
    Log.d("AttestedCertParser", "lengthOfextenstion = " + parseInt);
    Log.d("AttestedCertParser", "lengthOfvalue = " + parseInt2);
    if (!TextUtils.isEmpty(str) && Integer.parseInt(str) < 26 && parseInt - 4 != parseInt2) {
      int i = parseInt2 + 6;
      byte[] bArr = new byte[i];
      System.arraycopy(extensionValue, 0, bArr, 0, i);
      System.arraycopy(Integer.toHexString(parseInt2 + 4).getBytes(), 1, bArr, 1, 1);
      System.arraycopy(Integer.toHexString(parseInt2 + 2).getBytes(), 1, bArr, 3, 1);
      extensionValue = bArr;
    }
    if (extensionValue.length == 0) {
      throw new CertificateParsingException(
          "Did not find extension with OID 1.3.6.1.4.1.236.11.3.23.7");
    }
    return Asn1Utils.getAsn1SequenceFromBytes(extensionValue);
  }

  public IntegrityStatus getIntegrityStatus() {
    return this.mKnoxIngetrity;
  }

  public final String bytesToHex(byte[] bArr) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bArr) {
      sb.append(String.format("%02x", Integer.valueOf(b & 255)));
    }
    return sb.toString();
  }
}
