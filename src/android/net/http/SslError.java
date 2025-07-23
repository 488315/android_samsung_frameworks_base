package android.net.http;

import java.security.cert.X509Certificate;

/* loaded from: classes3.dex */
public class SslError {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int SSL_DATE_INVALID = 4;
    public static final int SSL_EXPIRED = 1;
    public static final int SSL_IDMISMATCH = 2;
    public static final int SSL_INVALID = 5;

    @Deprecated
    public static final int SSL_MAX_ERROR = 6;
    public static final int SSL_NOTYETVALID = 0;
    public static final int SSL_UNTRUSTED = 3;
    final SslCertificate mCertificate;
    int mErrors;
    final String mUrl;

    @Deprecated
    public SslError(int i, SslCertificate sslCertificate) {
        this(i, sslCertificate, "");
    }

    @Deprecated
    public SslError(int i, X509Certificate x509Certificate) {
        this(i, x509Certificate, "");
    }

    public SslError(int i, SslCertificate sslCertificate, String str) {
        addError(i);
        this.mCertificate = sslCertificate;
        this.mUrl = str;
    }

    public SslError(int i, X509Certificate x509Certificate, String str) {
        this(i, new SslCertificate(x509Certificate), str);
    }

    public static SslError SslErrorFromChromiumErrorCode(int i, SslCertificate sslCertificate, String str) {
        if (i == -200) {
            return new SslError(2, sslCertificate, str);
        }
        if (i == -201) {
            return new SslError(4, sslCertificate, str);
        }
        if (i == -202) {
            return new SslError(3, sslCertificate, str);
        }
        return new SslError(5, sslCertificate, str);
    }

    public SslCertificate getCertificate() {
        return this.mCertificate;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public boolean addError(int i) {
        boolean z = i >= 0 && i < 6;
        if (z) {
            this.mErrors = (1 << i) | this.mErrors;
        }
        return z;
    }

    public boolean hasError(int i) {
        boolean z = i >= 0 && i < 6;
        return z ? (this.mErrors & (1 << i)) != 0 : z;
    }

    public int getPrimaryError() {
        if (this.mErrors == 0) {
            return -1;
        }
        for (int i = 5; i >= 0; i--) {
            if ((this.mErrors & (1 << i)) != 0) {
                return i;
            }
        }
        return -1;
    }

    public String toString() {
        return "primary error: " + getPrimaryError() + " certificate: " + getCertificate() + " on URL: " + getUrl();
    }
}
