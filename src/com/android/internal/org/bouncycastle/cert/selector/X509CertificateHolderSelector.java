package com.android.internal.org.bouncycastle.cert.selector;

import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x509.Extension;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Selector;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class X509CertificateHolderSelector implements Selector {
    private X500Name issuer;
    private BigInteger serialNumber;
    private byte[] subjectKeyId;

    public X509CertificateHolderSelector(byte[] bArr) {
        this(null, null, bArr);
    }

    public X509CertificateHolderSelector(X500Name x500Name, BigInteger bigInteger) {
        this(x500Name, bigInteger, null);
    }

    public X509CertificateHolderSelector(X500Name x500Name, BigInteger bigInteger, byte[] bArr) {
        this.issuer = x500Name;
        this.serialNumber = bigInteger;
        this.subjectKeyId = bArr;
    }

    public X500Name getIssuer() {
        return this.issuer;
    }

    public BigInteger getSerialNumber() {
        return this.serialNumber;
    }

    public byte[] getSubjectKeyIdentifier() {
        return Arrays.clone(this.subjectKeyId);
    }

    public int hashCode() {
        int hashCode = Arrays.hashCode(this.subjectKeyId);
        BigInteger bigInteger = this.serialNumber;
        if (bigInteger != null) {
            hashCode ^= bigInteger.hashCode();
        }
        X500Name x500Name = this.issuer;
        return x500Name != null ? x500Name.hashCode() ^ hashCode : hashCode;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof X509CertificateHolderSelector)) {
            return false;
        }
        X509CertificateHolderSelector x509CertificateHolderSelector = (X509CertificateHolderSelector) obj;
        return Arrays.areEqual(this.subjectKeyId, x509CertificateHolderSelector.subjectKeyId) && equalsObj(this.serialNumber, x509CertificateHolderSelector.serialNumber) && equalsObj(this.issuer, x509CertificateHolderSelector.issuer);
    }

    private boolean equalsObj(Object obj, Object obj2) {
        if (obj != null) {
            return obj.equals(obj2);
        }
        return obj2 == null;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(Object obj) {
        if (obj instanceof X509CertificateHolder) {
            X509CertificateHolder x509CertificateHolder = (X509CertificateHolder) obj;
            if (getSerialNumber() != null) {
                IssuerAndSerialNumber issuerAndSerialNumber = new IssuerAndSerialNumber(x509CertificateHolder.toASN1Structure());
                return issuerAndSerialNumber.getName().equals(this.issuer) && issuerAndSerialNumber.getSerialNumber().hasValue(this.serialNumber);
            }
            if (this.subjectKeyId != null) {
                Extension extension = x509CertificateHolder.getExtension(Extension.subjectKeyIdentifier);
                if (extension == null) {
                    return Arrays.areEqual(this.subjectKeyId, MSOutlookKeyIdCalculator.calculateKeyId(x509CertificateHolder.getSubjectPublicKeyInfo()));
                }
                return Arrays.areEqual(this.subjectKeyId, ASN1OctetString.getInstance(extension.getParsedValue()).getOctets());
            }
        } else if (obj instanceof byte[]) {
            return Arrays.areEqual(this.subjectKeyId, (byte[]) obj);
        }
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public Object clone() {
        return new X509CertificateHolderSelector(this.issuer, this.serialNumber, this.subjectKeyId);
    }
}
