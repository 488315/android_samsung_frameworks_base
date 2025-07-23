package com.android.internal.org.bouncycastle.cert.ocsp;

import com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.internal.org.bouncycastle.asn1.ocsp.RevokedInfo;
import com.android.internal.org.bouncycastle.asn1.x509.CRLReason;
import java.util.Date;

/* loaded from: classes5.dex */
public class RevokedStatus implements CertificateStatus {
    RevokedInfo info;

    public RevokedStatus(RevokedInfo revokedInfo) {
        this.info = revokedInfo;
    }

    public RevokedStatus(Date date) {
        this.info = new RevokedInfo(new ASN1GeneralizedTime(date));
    }

    public RevokedStatus(Date date, int i) {
        this.info = new RevokedInfo(new ASN1GeneralizedTime(date), CRLReason.lookup(i));
    }

    public Date getRevocationTime() {
        return OCSPUtils.extractDate(this.info.getRevocationTime());
    }

    public boolean hasRevocationReason() {
        return this.info.getRevocationReason() != null;
    }

    public int getRevocationReason() {
        if (this.info.getRevocationReason() == null) {
            throw new IllegalStateException("attempt to get a reason where none is available");
        }
        return this.info.getRevocationReason().getValue().intValue();
    }
}
