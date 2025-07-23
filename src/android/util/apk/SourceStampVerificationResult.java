package android.util.apk;

import java.security.cert.Certificate;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class SourceStampVerificationResult {
    private final Certificate mCertificate;
    private final List<? extends Certificate> mCertificateLineage;
    private final boolean mPresent;
    private final boolean mVerified;

    private SourceStampVerificationResult(boolean z, boolean z2, Certificate certificate, List<? extends Certificate> list) {
        this.mPresent = z;
        this.mVerified = z2;
        this.mCertificate = certificate;
        this.mCertificateLineage = list;
    }

    public boolean isPresent() {
        return this.mPresent;
    }

    public boolean isVerified() {
        return this.mVerified;
    }

    public Certificate getCertificate() {
        return this.mCertificate;
    }

    public List<? extends Certificate> getCertificateLineage() {
        return this.mCertificateLineage;
    }

    public static SourceStampVerificationResult notPresent() {
        return new SourceStampVerificationResult(false, false, null, Collections.EMPTY_LIST);
    }

    public static SourceStampVerificationResult verified(Certificate certificate, List<? extends Certificate> list) {
        return new SourceStampVerificationResult(true, true, certificate, list);
    }

    public static SourceStampVerificationResult notVerified() {
        return new SourceStampVerificationResult(true, false, null, Collections.EMPTY_LIST);
    }
}
