package com.android.internal.org.bouncycastle.cert;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.AltSignatureAlgorithm;
import com.android.internal.org.bouncycastle.asn1.x509.AltSignatureValue;
import com.android.internal.org.bouncycastle.asn1.x509.Certificate;
import com.android.internal.org.bouncycastle.asn1.x509.Extension;
import com.android.internal.org.bouncycastle.asn1.x509.Extensions;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate;
import com.android.internal.org.bouncycastle.operator.ContentVerifier;
import com.android.internal.org.bouncycastle.operator.ContentVerifierProvider;
import com.android.internal.org.bouncycastle.util.Encodable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
public class X509CertificateHolder implements Encodable, Serializable {
    private static final long serialVersionUID = 20170722001L;
    private transient Extensions extensions;
    private transient Certificate x509Certificate;

    private static Certificate parseBytes(byte[] bArr) throws IOException {
        try {
            return Certificate.getInstance(CertUtils.parseNonEmptyASN1(bArr));
        } catch (ClassCastException e) {
            throw new CertIOException("malformed data: " + e.getMessage(), e);
        } catch (IllegalArgumentException e2) {
            throw new CertIOException("malformed data: " + e2.getMessage(), e2);
        }
    }

    public X509CertificateHolder(byte[] bArr) throws IOException {
        this(parseBytes(bArr));
    }

    public X509CertificateHolder(Certificate certificate) {
        init(certificate);
    }

    private void init(Certificate certificate) {
        this.x509Certificate = certificate;
        this.extensions = certificate.getTBSCertificate().getExtensions();
    }

    public int getVersionNumber() {
        return this.x509Certificate.getVersionNumber();
    }

    public int getVersion() {
        return this.x509Certificate.getVersionNumber();
    }

    public boolean hasExtensions() {
        return this.extensions != null;
    }

    public Extension getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Extensions extensions = this.extensions;
        if (extensions != null) {
            return extensions.getExtension(aSN1ObjectIdentifier);
        }
        return null;
    }

    public Extensions getExtensions() {
        return this.extensions;
    }

    public List getExtensionOIDs() {
        return CertUtils.getExtensionOIDs(this.extensions);
    }

    public Set getCriticalExtensionOIDs() {
        return CertUtils.getCriticalExtensionOIDs(this.extensions);
    }

    public Set getNonCriticalExtensionOIDs() {
        return CertUtils.getNonCriticalExtensionOIDs(this.extensions);
    }

    public BigInteger getSerialNumber() {
        return this.x509Certificate.getSerialNumber().getValue();
    }

    public X500Name getIssuer() {
        return X500Name.getInstance(this.x509Certificate.getIssuer());
    }

    public X500Name getSubject() {
        return X500Name.getInstance(this.x509Certificate.getSubject());
    }

    public Date getNotBefore() {
        return this.x509Certificate.getStartDate().getDate();
    }

    public Date getNotAfter() {
        return this.x509Certificate.getEndDate().getDate();
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.x509Certificate.getSubjectPublicKeyInfo();
    }

    public Certificate toASN1Structure() {
        return this.x509Certificate;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.x509Certificate.getSignatureAlgorithm();
    }

    public byte[] getSignature() {
        return this.x509Certificate.getSignature().getOctets();
    }

    public boolean isValidOn(Date date) {
        return (date.before(this.x509Certificate.getStartDate().getDate()) || date.after(this.x509Certificate.getEndDate().getDate())) ? false : true;
    }

    public boolean isSignatureValid(ContentVerifierProvider contentVerifierProvider) throws CertException {
        TBSCertificate tBSCertificate = this.x509Certificate.getTBSCertificate();
        if (!CertUtils.isAlgIdEqual(tBSCertificate.getSignature(), this.x509Certificate.getSignatureAlgorithm())) {
            throw new CertException("signature invalid - algorithm identifier mismatch");
        }
        try {
            ContentVerifier contentVerifier = contentVerifierProvider.get(tBSCertificate.getSignature());
            OutputStream outputStream = contentVerifier.getOutputStream();
            tBSCertificate.encodeTo(outputStream, ASN1Encoding.DER);
            outputStream.close();
            return contentVerifier.verify(getSignature());
        } catch (Exception e) {
            throw new CertException("unable to process signature: " + e.getMessage(), e);
        }
    }

    public boolean isAlternativeSignatureValid(ContentVerifierProvider contentVerifierProvider) throws CertException {
        TBSCertificate tBSCertificate = this.x509Certificate.getTBSCertificate();
        AltSignatureAlgorithm fromExtensions = AltSignatureAlgorithm.fromExtensions(tBSCertificate.getExtensions());
        AltSignatureValue fromExtensions2 = AltSignatureValue.fromExtensions(tBSCertificate.getExtensions());
        try {
            ContentVerifier contentVerifier = contentVerifierProvider.get(AlgorithmIdentifier.getInstance(fromExtensions.toASN1Primitive()));
            OutputStream outputStream = contentVerifier.getOutputStream();
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(tBSCertificate.toASN1Primitive());
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            for (int i = 0; i != aSN1Sequence.size() - 1; i++) {
                if (i != 2) {
                    aSN1EncodableVector.add(aSN1Sequence.getObjectAt(i));
                }
            }
            aSN1EncodableVector.add(CertUtils.trimExtensions(3, tBSCertificate.getExtensions()));
            new DERSequence(aSN1EncodableVector).encodeTo(outputStream, ASN1Encoding.DER);
            outputStream.close();
            return contentVerifier.verify(fromExtensions2.getSignature().getOctets());
        } catch (Exception e) {
            throw new CertException("unable to process signature: " + e.getMessage(), e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof X509CertificateHolder) {
            return this.x509Certificate.equals(((X509CertificateHolder) obj).x509Certificate);
        }
        return false;
    }

    public int hashCode() {
        return this.x509Certificate.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() throws IOException {
        return this.x509Certificate.getEncoded();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        init(Certificate.getInstance(objectInputStream.readObject()));
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }
}
