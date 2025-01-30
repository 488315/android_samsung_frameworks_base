package com.android.server.locksettings.recoverablekeystore.certificate;

import java.security.SecureRandom;
import java.security.cert.CertPath;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;

/* loaded from: classes2.dex */
public final class CertXml {
  public final List endpointCerts;
  public final List intermediateCerts;
  public final long serial;

  public CertXml(long j, List list, List list2) {
    this.serial = j;
    this.intermediateCerts = list;
    this.endpointCerts = list2;
  }

  public long getSerial() {
    return this.serial;
  }

  public List getAllIntermediateCerts() {
    return this.intermediateCerts;
  }

  public List getAllEndpointCerts() {
    return this.endpointCerts;
  }

  public CertPath getRandomEndpointCert(X509Certificate x509Certificate) {
    return getEndpointCert(
        new SecureRandom().nextInt(this.endpointCerts.size()), null, x509Certificate);
  }

  public CertPath getEndpointCert(int i, Date date, X509Certificate x509Certificate) {
    return CertUtils.validateCert(
        date, x509Certificate, this.intermediateCerts, (X509Certificate) this.endpointCerts.get(i));
  }

  public static CertXml parse(byte[] bArr) {
    Element xmlRootNode = CertUtils.getXmlRootNode(bArr);
    return new CertXml(
        parseSerial(xmlRootNode),
        parseIntermediateCerts(xmlRootNode),
        parseEndpointCerts(xmlRootNode));
  }

  public static long parseSerial(Element element) {
    return Long.parseLong(
        (String) CertUtils.getXmlNodeContents(1, element, "metadata", "serial").get(0));
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

  public static List parseEndpointCerts(Element element) {
    List xmlNodeContents = CertUtils.getXmlNodeContents(2, element, "endpoints", "cert");
    ArrayList arrayList = new ArrayList();
    Iterator it = xmlNodeContents.iterator();
    while (it.hasNext()) {
      arrayList.add(CertUtils.decodeCert(CertUtils.decodeBase64((String) it.next())));
    }
    return Collections.unmodifiableList(arrayList);
  }
}
