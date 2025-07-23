package com.android.internal.org.bouncycastle.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.x509.Extension;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Selector;
import com.android.internal.org.bouncycastle.x509.extension.X509ExtensionUtil;
import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRL;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;

/* loaded from: classes5.dex */
public class X509CRLStoreSelector extends X509CRLSelector implements Selector {
    private X509AttributeCertificate attrCertChecking;
    private boolean deltaCRLIndicator = false;
    private boolean completeCRLEnabled = false;
    private BigInteger maxBaseCRLNumber = null;
    private byte[] issuingDistributionPoint = null;
    private boolean issuingDistributionPointEnabled = false;

    public boolean isIssuingDistributionPointEnabled() {
        return this.issuingDistributionPointEnabled;
    }

    public void setIssuingDistributionPointEnabled(boolean z) {
        this.issuingDistributionPointEnabled = z;
    }

    public void setAttrCertificateChecking(X509AttributeCertificate x509AttributeCertificate) {
        this.attrCertChecking = x509AttributeCertificate;
    }

    public X509AttributeCertificate getAttrCertificateChecking() {
        return this.attrCertChecking;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(Object obj) {
        if (!(obj instanceof X509CRL)) {
            return false;
        }
        X509CRL x509crl = (X509CRL) obj;
        try {
            byte[] extensionValue = x509crl.getExtensionValue(Extension.deltaCRLIndicator.getId());
            ASN1Integer aSN1Integer = extensionValue != null ? ASN1Integer.getInstance(X509ExtensionUtil.fromExtensionValue(extensionValue)) : null;
            if (isDeltaCRLIndicatorEnabled() && aSN1Integer == null) {
                return false;
            }
            if (isCompleteCRLEnabled() && aSN1Integer != null) {
                return false;
            }
            if (aSN1Integer != null && this.maxBaseCRLNumber != null && aSN1Integer.getPositiveValue().compareTo(this.maxBaseCRLNumber) == 1) {
                return false;
            }
            if (this.issuingDistributionPointEnabled) {
                byte[] extensionValue2 = x509crl.getExtensionValue(Extension.issuingDistributionPoint.getId());
                byte[] bArr = this.issuingDistributionPoint;
                if (bArr == null) {
                    if (extensionValue2 != null) {
                        return false;
                    }
                } else if (!Arrays.areEqual(extensionValue2, bArr)) {
                    return false;
                }
            }
            return super.match((CRL) x509crl);
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // java.security.cert.X509CRLSelector, java.security.cert.CRLSelector
    public boolean match(CRL crl) {
        return match((Object) crl);
    }

    public boolean isDeltaCRLIndicatorEnabled() {
        return this.deltaCRLIndicator;
    }

    public void setDeltaCRLIndicatorEnabled(boolean z) {
        this.deltaCRLIndicator = z;
    }

    public static X509CRLStoreSelector getInstance(X509CRLSelector x509CRLSelector) {
        if (x509CRLSelector == null) {
            throw new IllegalArgumentException("cannot create from null selector");
        }
        X509CRLStoreSelector x509CRLStoreSelector = new X509CRLStoreSelector();
        x509CRLStoreSelector.setCertificateChecking(x509CRLSelector.getCertificateChecking());
        x509CRLStoreSelector.setDateAndTime(x509CRLSelector.getDateAndTime());
        try {
            x509CRLStoreSelector.setIssuerNames(x509CRLSelector.getIssuerNames());
            x509CRLStoreSelector.setIssuers(x509CRLSelector.getIssuers());
            x509CRLStoreSelector.setMaxCRLNumber(x509CRLSelector.getMaxCRL());
            x509CRLStoreSelector.setMinCRLNumber(x509CRLSelector.getMinCRL());
            return x509CRLStoreSelector;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override // java.security.cert.X509CRLSelector, java.security.cert.CRLSelector, com.android.internal.org.bouncycastle.util.Selector
    public Object clone() {
        X509CRLStoreSelector x509CRLStoreSelector = getInstance(this);
        x509CRLStoreSelector.deltaCRLIndicator = this.deltaCRLIndicator;
        x509CRLStoreSelector.completeCRLEnabled = this.completeCRLEnabled;
        x509CRLStoreSelector.maxBaseCRLNumber = this.maxBaseCRLNumber;
        x509CRLStoreSelector.attrCertChecking = this.attrCertChecking;
        x509CRLStoreSelector.issuingDistributionPointEnabled = this.issuingDistributionPointEnabled;
        x509CRLStoreSelector.issuingDistributionPoint = Arrays.clone(this.issuingDistributionPoint);
        return x509CRLStoreSelector;
    }

    public boolean isCompleteCRLEnabled() {
        return this.completeCRLEnabled;
    }

    public void setCompleteCRLEnabled(boolean z) {
        this.completeCRLEnabled = z;
    }

    public BigInteger getMaxBaseCRLNumber() {
        return this.maxBaseCRLNumber;
    }

    public void setMaxBaseCRLNumber(BigInteger bigInteger) {
        this.maxBaseCRLNumber = bigInteger;
    }

    public byte[] getIssuingDistributionPoint() {
        return Arrays.clone(this.issuingDistributionPoint);
    }

    public void setIssuingDistributionPoint(byte[] bArr) {
        this.issuingDistributionPoint = Arrays.clone(bArr);
    }
}
