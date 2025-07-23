package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.cert.selector.X509CertificateHolderSelector;
import com.android.internal.org.bouncycastle.util.Selector;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class SignerId implements Selector {
    private X509CertificateHolderSelector baseSelector;

    private SignerId(X509CertificateHolderSelector x509CertificateHolderSelector) {
        this.baseSelector = x509CertificateHolderSelector;
    }

    public SignerId(byte[] bArr) {
        this(null, null, bArr);
    }

    public SignerId(X500Name x500Name, BigInteger bigInteger) {
        this(x500Name, bigInteger, null);
    }

    public SignerId(X500Name x500Name, BigInteger bigInteger, byte[] bArr) {
        this(new X509CertificateHolderSelector(x500Name, bigInteger, bArr));
    }

    public X500Name getIssuer() {
        return this.baseSelector.getIssuer();
    }

    public BigInteger getSerialNumber() {
        return this.baseSelector.getSerialNumber();
    }

    public byte[] getSubjectKeyIdentifier() {
        return this.baseSelector.getSubjectKeyIdentifier();
    }

    public int hashCode() {
        return this.baseSelector.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof SignerId) {
            return this.baseSelector.equals(((SignerId) obj).baseSelector);
        }
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(Object obj) {
        if (obj instanceof SignerInformation) {
            return ((SignerInformation) obj).getSID().equals(this);
        }
        return this.baseSelector.match(obj);
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public Object clone() {
        return new SignerId(this.baseSelector);
    }
}
