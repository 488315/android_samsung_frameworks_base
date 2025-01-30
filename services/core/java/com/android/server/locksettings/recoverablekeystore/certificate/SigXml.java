package com.android.server.locksettings.recoverablekeystore.certificate;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;

/* loaded from: classes2.dex */
public final class SigXml {
  public final List intermediateCerts;
  public final byte[] signature;
  public final X509Certificate signerCert;

  public SigXml(List list, X509Certificate x509Certificate, byte[] bArr) {
    this.intermediateCerts = list;
    this.signerCert = x509Certificate;
    this.signature = bArr;
  }

  public void verifyFileSignature(X509Certificate x509Certificate, byte[] bArr) {
    verifyFileSignature(x509Certificate, bArr, null);
  }

  public void verifyFileSignature(X509Certificate x509Certificate, byte[] bArr, Date date) {
    CertUtils.validateCert(date, x509Certificate, this.intermediateCerts, this.signerCert);
    CertUtils.verifyRsaSha256Signature(this.signerCert.getPublicKey(), this.signature, bArr);
  }

  public static SigXml parse(byte[] bArr) {
    Element xmlRootNode = CertUtils.getXmlRootNode(bArr);
    return new SigXml(
        parseIntermediateCerts(xmlRootNode),
        parseSignerCert(xmlRootNode),
        parseFileSignature(xmlRootNode));
  }

  public static List parseIntermediateCerts(Element element) {
    List xmlNodeContents = CertUtils.getXmlNodeContents(0, element, "intermediates", "cert");
    ArrayList arrayList = new ArrayList();
    Iterator it = xmlNodeContents.iterator();
    while (it.hasNext()) {
      arrayList.add(CertUtils.decodeCert(CertUtils.decodeBase64((String) it.next())));
    }
    return Collections.unmodifiableList(arrayList);
  }

  public static X509Certificate parseSignerCert(Element element) {
    return CertUtils.decodeCert(
        CertUtils.decodeBase64(
            (String) CertUtils.getXmlNodeContents(1, element, "certificate").get(0)));
  }

  public static byte[] parseFileSignature(Element element) {
    return CertUtils.decodeBase64(
        (String) CertUtils.getXmlNodeContents(1, element, "value").get(0));
  }
}
