package android.net.http;

import android.security.net.config.UserCertificateSource;
import com.android.internal.hidden_from_bootclasspath.com.android.org.conscrypt.flags.Flags;
import com.android.org.conscrypt.TrustManagerImpl;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes3.dex */
public class X509TrustManagerExtensions {
    private final Method mCheckServerTrusted;
    private final Method mCheckServerTrustedOcspAndTlsData;
    private final TrustManagerImpl mDelegate;
    private final Method mIsSameTrustConfiguration;
    private final X509TrustManager mTrustManager;

    public X509TrustManagerExtensions(X509TrustManager x509TrustManager) throws IllegalArgumentException {
        Method method;
        Method method2 = null;
        if (x509TrustManager instanceof TrustManagerImpl) {
            this.mDelegate = (TrustManagerImpl) x509TrustManager;
            this.mTrustManager = null;
            this.mCheckServerTrusted = null;
            this.mCheckServerTrustedOcspAndTlsData = null;
            this.mIsSameTrustConfiguration = null;
            return;
        }
        this.mDelegate = null;
        this.mTrustManager = x509TrustManager;
        try {
            this.mCheckServerTrusted = x509TrustManager.getClass().getMethod("checkServerTrusted", X509Certificate[].class, String.class, String.class);
            try {
                method = x509TrustManager.getClass().getMethod("checkServerTrusted", X509Certificate[].class, byte[].class, byte[].class, String.class, String.class);
            } catch (ReflectiveOperationException unused) {
                method = null;
            }
            this.mCheckServerTrustedOcspAndTlsData = method;
            try {
                method2 = x509TrustManager.getClass().getMethod("isSameTrustConfiguration", String.class, String.class);
            } catch (ReflectiveOperationException unused2) {
            }
            this.mIsSameTrustConfiguration = method2;
        } catch (NoSuchMethodException unused3) {
            throw new IllegalArgumentException("Required method checkServerTrusted(X509Certificate[], String, String) missing");
        }
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] x509CertificateArr, String str, String str2) throws CertificateException {
        TrustManagerImpl trustManagerImpl = this.mDelegate;
        if (trustManagerImpl != null) {
            return trustManagerImpl.checkServerTrusted(x509CertificateArr, str, str2);
        }
        try {
            return (List) this.mCheckServerTrusted.invoke(this.mTrustManager, x509CertificateArr, str, str2);
        } catch (IllegalAccessException e) {
            throw new CertificateException("Failed to call checkServerTrusted", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof CertificateException) {
                throw ((CertificateException) e2.getCause());
            }
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new CertificateException("checkServerTrusted failed", e2.getCause());
        }
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] x509CertificateArr, byte[] bArr, byte[] bArr2, String str, String str2) throws CertificateException {
        if (this.mDelegate != null) {
            if (Flags.certificateTransparencyCheckservertrustedApi()) {
                List<X509Certificate> checkServerTrusted = this.mDelegate.checkServerTrusted(x509CertificateArr, bArr, bArr2, str, str2);
                return checkServerTrusted == null ? Collections.EMPTY_LIST : checkServerTrusted;
            }
            throw new IllegalArgumentException("Required method checkServerTrusted(X509Certificate[], byte[], byte[], String, String) not available in TrustManagerImpl");
        }
        Method method = this.mCheckServerTrustedOcspAndTlsData;
        if (method == null) {
            throw new IllegalArgumentException("Required method checkServerTrusted(X509Certificate[], byte[], byte[], String, String) missing");
        }
        try {
            List<X509Certificate> list = (List) method.invoke(this.mTrustManager, x509CertificateArr, bArr, bArr2, str, str2);
            return list == null ? Collections.EMPTY_LIST : list;
        } catch (IllegalAccessException e) {
            throw new CertificateException("Failed to call checkServerTrusted", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof CertificateException) {
                throw ((CertificateException) e2.getCause());
            }
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new CertificateException("checkServerTrusted failed", e2.getCause());
        }
    }

    public boolean isUserAddedCertificate(X509Certificate x509Certificate) {
        return UserCertificateSource.getInstance().findBySubjectAndPublicKey(x509Certificate) != null;
    }

    public boolean isSameTrustConfiguration(String str, String str2) {
        Method method = this.mIsSameTrustConfiguration;
        if (method == null) {
            return true;
        }
        try {
            return ((Boolean) method.invoke(this.mTrustManager, str, str2)).booleanValue();
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to call isSameTrustConfiguration", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new RuntimeException("isSameTrustConfiguration failed", e2.getCause());
        }
    }
}
