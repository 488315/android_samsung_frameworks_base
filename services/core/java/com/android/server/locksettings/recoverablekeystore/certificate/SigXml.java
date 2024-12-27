package com.android.server.locksettings.recoverablekeystore.certificate;

import org.w3c.dom.Element;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public final class SigXml {
    public final List intermediateCerts;
    public final byte[] signature;
    public final X509Certificate signerCert;

    public SigXml(List list, X509Certificate x509Certificate, byte[] bArr) {
        this.intermediateCerts = list;
        this.signerCert = x509Certificate;
        this.signature = bArr;
    }

    public static SigXml parse(byte[] bArr) {
        Element xmlRootNode = CertUtils.getXmlRootNode(bArr);
        List xmlNodeContents =
                CertUtils.getXmlNodeContents(0, xmlRootNode, "intermediates", "cert");
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) xmlNodeContents).iterator();
        while (it.hasNext()) {
            arrayList.add(CertUtils.decodeCert(CertUtils.decodeBase64((String) it.next())));
        }
        return new SigXml(
                Collections.unmodifiableList(arrayList),
                CertUtils.decodeCert(
                        CertUtils.decodeBase64(
                                (String)
                                        ((ArrayList)
                                                        CertUtils.getXmlNodeContents(
                                                                1, xmlRootNode, "certificate"))
                                                .get(0))),
                CertUtils.decodeBase64(
                        (String)
                                ((ArrayList) CertUtils.getXmlNodeContents(1, xmlRootNode, "value"))
                                        .get(0)));
    }

    public final void verifyFileSignature(X509Certificate x509Certificate, byte[] bArr, Date date) {
        CertUtils.validateCert(date, x509Certificate, this.intermediateCerts, this.signerCert);
        PublicKey publicKey = this.signerCert.getPublicKey();
        byte[] bArr2 = this.signature;
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            try {
                signature.initVerify(publicKey);
                signature.update(bArr);
                if (signature.verify(bArr2)) {
                } else {
                    throw new CertValidationException("The signature is invalid");
                }
            } catch (InvalidKeyException | SignatureException e) {
                throw new CertValidationException(e);
            }
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        }
    }
}
