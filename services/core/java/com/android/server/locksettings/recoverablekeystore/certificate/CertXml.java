package com.android.server.locksettings.recoverablekeystore.certificate;

import org.w3c.dom.Element;

import java.security.cert.CertPath;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CertXml {
    public final List endpointCerts;
    public final List intermediateCerts;
    public final long serial;

    public CertXml(long j, List list, List list2) {
        this.serial = j;
        this.intermediateCerts = list;
        this.endpointCerts = list2;
    }

    public static CertXml parse(byte[] bArr) {
        Element xmlRootNode = CertUtils.getXmlRootNode(bArr);
        long parseLong =
                Long.parseLong(
                        (String)
                                ((ArrayList)
                                                CertUtils.getXmlNodeContents(
                                                        1, xmlRootNode, "metadata", "serial"))
                                        .get(0));
        List xmlNodeContents =
                CertUtils.getXmlNodeContents(0, xmlRootNode, "intermediates", "cert");
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) xmlNodeContents).iterator();
        while (it.hasNext()) {
            arrayList.add(CertUtils.decodeCert(CertUtils.decodeBase64((String) it.next())));
        }
        List unmodifiableList = Collections.unmodifiableList(arrayList);
        List xmlNodeContents2 = CertUtils.getXmlNodeContents(2, xmlRootNode, "endpoints", "cert");
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = ((ArrayList) xmlNodeContents2).iterator();
        while (it2.hasNext()) {
            arrayList2.add(CertUtils.decodeCert(CertUtils.decodeBase64((String) it2.next())));
        }
        return new CertXml(parseLong, unmodifiableList, Collections.unmodifiableList(arrayList2));
    }

    public List getAllEndpointCerts() {
        return this.endpointCerts;
    }

    public List getAllIntermediateCerts() {
        return this.intermediateCerts;
    }

    public CertPath getEndpointCert(int i, Date date, X509Certificate x509Certificate)
            throws CertValidationException {
        return CertUtils.validateCert(
                date,
                x509Certificate,
                this.intermediateCerts,
                (X509Certificate) this.endpointCerts.get(i));
    }
}
