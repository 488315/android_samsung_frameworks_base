package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.DERSet;
import com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.SignedData;
import com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes5.dex */
public class PKIXCertPath extends CertPath {
    static final List certPathEncodings;
    private List certificates;
    private final JcaJceHelper helper;

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add("PkiPath");
        arrayList.add("PKCS7");
        certPathEncodings = Collections.unmodifiableList(arrayList);
    }

    private List sortCerts(List list) {
        if (list.size() >= 2) {
            X500Principal issuerX500Principal = ((X509Certificate) list.get(0)).getIssuerX500Principal();
            for (int i = 1; i != list.size(); i++) {
                if (issuerX500Principal.equals(((X509Certificate) list.get(i)).getSubjectX500Principal())) {
                    issuerX500Principal = ((X509Certificate) list.get(i)).getIssuerX500Principal();
                } else {
                    ArrayList arrayList = new ArrayList(list.size());
                    ArrayList arrayList2 = new ArrayList(list);
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        X509Certificate x509Certificate = (X509Certificate) list.get(i2);
                        X500Principal subjectX500Principal = x509Certificate.getSubjectX500Principal();
                        int i3 = 0;
                        while (true) {
                            if (i3 != list.size()) {
                                if (((X509Certificate) list.get(i3)).getIssuerX500Principal().equals(subjectX500Principal)) {
                                    break;
                                }
                                i3++;
                            } else {
                                arrayList.add(x509Certificate);
                                list.remove(i2);
                                break;
                            }
                        }
                    }
                    if (arrayList.size() <= 1) {
                        for (int i4 = 0; i4 != arrayList.size(); i4++) {
                            X500Principal issuerX500Principal2 = ((X509Certificate) arrayList.get(i4)).getIssuerX500Principal();
                            int i5 = 0;
                            while (true) {
                                if (i5 < list.size()) {
                                    X509Certificate x509Certificate2 = (X509Certificate) list.get(i5);
                                    if (issuerX500Principal2.equals(x509Certificate2.getSubjectX500Principal())) {
                                        arrayList.add(x509Certificate2);
                                        list.remove(i5);
                                        break;
                                    }
                                    i5++;
                                }
                            }
                        }
                        if (list.size() <= 0) {
                            return arrayList;
                        }
                    }
                    return arrayList2;
                }
            }
        }
        return list;
    }

    PKIXCertPath(List list) {
        super("X.509");
        this.helper = new BCJcaJceHelper();
        this.certificates = sortCerts(new ArrayList(list));
    }

    PKIXCertPath(InputStream inputStream, String str) throws CertificateException {
        super("X.509");
        BCJcaJceHelper bCJcaJceHelper = new BCJcaJceHelper();
        this.helper = bCJcaJceHelper;
        try {
            if (str.equalsIgnoreCase("PkiPath")) {
                ASN1Primitive readObject = new ASN1InputStream(inputStream).readObject();
                if (!(readObject instanceof ASN1Sequence)) {
                    throw new CertificateException("input stream does not contain a ASN1 SEQUENCE while reading PkiPath encoded data to load CertPath");
                }
                Enumeration objects = ((ASN1Sequence) readObject).getObjects();
                this.certificates = new ArrayList();
                java.security.cert.CertificateFactory createCertificateFactory = bCJcaJceHelper.createCertificateFactory("X.509");
                while (objects.hasMoreElements()) {
                    this.certificates.add(0, createCertificateFactory.generateCertificate(new ByteArrayInputStream(((ASN1Encodable) objects.nextElement()).toASN1Primitive().getEncoded(ASN1Encoding.DER))));
                }
            } else {
                if (!str.equalsIgnoreCase("PKCS7") && !str.equalsIgnoreCase("PEM")) {
                    throw new CertificateException("unsupported encoding: " + str);
                }
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                this.certificates = new ArrayList();
                java.security.cert.CertificateFactory createCertificateFactory2 = bCJcaJceHelper.createCertificateFactory("X.509");
                while (true) {
                    Certificate generateCertificate = createCertificateFactory2.generateCertificate(bufferedInputStream);
                    if (generateCertificate == null) {
                        break;
                    } else {
                        this.certificates.add(generateCertificate);
                    }
                }
            }
            this.certificates = sortCerts(this.certificates);
        } catch (IOException e) {
            throw new CertificateException("IOException throw while decoding CertPath:\n" + e.toString());
        } catch (NoSuchProviderException e2) {
            throw new CertificateException("BouncyCastle provider not found while trying to get a CertificateFactory:\n" + e2.toString());
        }
    }

    @Override // java.security.cert.CertPath
    public Iterator getEncodings() {
        return certPathEncodings.iterator();
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded() throws CertificateEncodingException {
        Iterator encodings = getEncodings();
        if (!encodings.hasNext()) {
            return null;
        }
        Object next = encodings.next();
        if (next instanceof String) {
            return getEncoded((String) next);
        }
        return null;
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded(String str) throws CertificateEncodingException {
        if (str.equalsIgnoreCase("PkiPath")) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            List list = this.certificates;
            ListIterator listIterator = list.listIterator(list.size());
            while (listIterator.hasPrevious()) {
                aSN1EncodableVector.add(toASN1Object((X509Certificate) listIterator.previous()));
            }
            return toDEREncoded(new DERSequence(aSN1EncodableVector));
        }
        if (str.equalsIgnoreCase("PKCS7")) {
            ContentInfo contentInfo = new ContentInfo(PKCSObjectIdentifiers.data, null);
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            for (int i = 0; i != this.certificates.size(); i++) {
                aSN1EncodableVector2.add(toASN1Object((X509Certificate) this.certificates.get(i)));
            }
            return toDEREncoded(new ContentInfo(PKCSObjectIdentifiers.signedData, new SignedData(new ASN1Integer(1L), new DERSet(), contentInfo, new DERSet(aSN1EncodableVector2), null, new DERSet())));
        }
        throw new CertificateEncodingException("unsupported encoding: " + str);
    }

    @Override // java.security.cert.CertPath
    public List getCertificates() {
        return Collections.unmodifiableList(new ArrayList(this.certificates));
    }

    private ASN1Primitive toASN1Object(X509Certificate x509Certificate) throws CertificateEncodingException {
        try {
            return new ASN1InputStream(x509Certificate.getEncoded()).readObject();
        } catch (Exception e) {
            throw new CertificateEncodingException("Exception while encoding certificate: " + e.toString());
        }
    }

    private byte[] toDEREncoded(ASN1Encodable aSN1Encodable) throws CertificateEncodingException {
        try {
            return aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new CertificateEncodingException("Exception thrown: " + e);
        }
    }
}
