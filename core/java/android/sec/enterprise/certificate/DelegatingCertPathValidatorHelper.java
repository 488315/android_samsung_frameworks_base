package android.sec.enterprise.certificate;

import android.p009os.UserHandle;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public class DelegatingCertPathValidatorHelper {
    public static final int ALERT = 1;
    public static final int AUDIT_LOG_GROUP_APPLICATION = 5;
    public static final int AUDIT_LOG_GROUP_EVENTS = 4;
    public static final int AUDIT_LOG_GROUP_NETWORK = 3;
    public static final int AUDIT_LOG_GROUP_SECURITY = 1;
    public static final int AUDIT_LOG_GROUP_SYSTEM = 2;
    public static final int CRITICAL = 2;
    public static final int ERROR = 3;
    public static final int NOTICE = 5;
    private static final String PEM_CERT_BEGIN = "-----BEGIN CERTIFICATE-----\n";
    private static final String PEM_CERT_END = "\n-----END CERTIFICATE-----\n";
    public static final int WARNING = 4;
    private static String TAG = "DelegatingCertPathValidatorHelper";
    private static boolean DEBUG = false;

    public static boolean isRevocationCheckEnabled() {
        boolean ret = false;
        CertificatePolicy cp = EnterpriseDeviceManager.getInstance().getCertificatePolicy();
        if (cp != null) {
            ret = cp.isRevocationCheckEnabled();
        }
        if (DEBUG) {
            Log.m94d(TAG, "isRevocationCheckEnabled " + ret);
        }
        return ret;
    }

    public static boolean isOcspCheckEnabled() {
        CertificatePolicy cp = EnterpriseDeviceManager.getInstance().getCertificatePolicy();
        if (cp == null) {
            return false;
        }
        boolean ret = cp.isOcspCheckEnabled();
        return ret;
    }

    public static void setRevocationChecker(PKIXRevocationChecker revChecker, PKIXParameters params) {
        if (DEBUG) {
            Log.m94d(TAG, "setRevocationChecker");
        }
        if (!isRevocationCheckEnabled()) {
            return;
        }
        List<PKIXCertPathChecker> tmpList = new ArrayList<>();
        for (PKIXCertPathChecker checker : params.getCertPathCheckers()) {
            if (!(checker instanceof PKIXRevocationChecker)) {
                tmpList.add(checker);
            }
        }
        params.setCertPathCheckers(tmpList);
        if (!isOcspCheckEnabled()) {
            Set<PKIXRevocationChecker.Option> options = new HashSet<>();
            options.add(PKIXRevocationChecker.Option.NO_FALLBACK);
            options.add(PKIXRevocationChecker.Option.PREFER_CRLS);
            revChecker.setOptions(options);
        }
        params.addCertPathChecker(revChecker);
    }

    public static boolean isChainTrustedByMdm(List<X509Certificate> chain) {
        boolean ret = true;
        try {
            CertificatePolicy cp = EnterpriseDeviceManager.getInstance().getCertificatePolicy();
            int userId = UserHandle.myUserId();
            boolean isTrustedUntrustedEnabled = false;
            if (cp != null) {
                isTrustedUntrustedEnabled = cp.isCertificateTrustedUntrustedEnabledAsUser(userId);
            }
            if (isTrustedUntrustedEnabled) {
                ByteArrayOutputStream certByteStream = new ByteArrayOutputStream();
                for (X509Certificate cert : chain) {
                    certByteStream.write(PEM_CERT_BEGIN.getBytes());
                    certByteStream.write(java.util.Base64.getEncoder().encode(cert.getEncoded()));
                    certByteStream.write(PEM_CERT_END.getBytes());
                }
                ret = cp.isCaCertificateTrustedAsUser(certByteStream.toByteArray(), false, false, userId);
            }
        } catch (Exception e) {
            Log.m96e(TAG, "Failed to call isCaCertificateTrustedAsUser() " + e.getMessage());
        }
        if (DEBUG) {
            Log.m94d(TAG, "isChainTrustedByMdm: " + ret);
        }
        return ret;
    }
}
