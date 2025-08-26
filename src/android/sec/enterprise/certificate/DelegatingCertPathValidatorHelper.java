package android.sec.enterprise.certificate;

import android.os.UserHandle;
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

/* loaded from: classes3.dex */
public class DelegatingCertPathValidatorHelper {
    public static final int ALERT = 1;
    public static final int AUDIT_LOG_GROUP_APPLICATION = 5;
    public static final int AUDIT_LOG_GROUP_EVENTS = 4;
    public static final int AUDIT_LOG_GROUP_NETWORK = 3;
    public static final int AUDIT_LOG_GROUP_SECURITY = 1;
    public static final int AUDIT_LOG_GROUP_SYSTEM = 2;
    public static final int CRITICAL = 2;
    private static boolean DEBUG = false;
    public static final int ERROR = 3;
    public static final int NOTICE = 5;
    private static final String PEM_CERT_BEGIN = "-----BEGIN CERTIFICATE-----\n";
    private static final String PEM_CERT_END = "\n-----END CERTIFICATE-----\n";
    private static String TAG = "DelegatingCertPathValidatorHelper";
    public static final int WARNING = 4;

    public static boolean isRevocationCheckEnabled() {
        CertificatePolicy certificatePolicy = EnterpriseDeviceManager.getInstance().getCertificatePolicy();
        boolean zIsRevocationCheckEnabled = certificatePolicy != null ? certificatePolicy.isRevocationCheckEnabled() : false;
        if (DEBUG) {
            Log.d(TAG, "isRevocationCheckEnabled " + zIsRevocationCheckEnabled);
        }
        return zIsRevocationCheckEnabled;
    }

    public static boolean isOcspCheckEnabled() {
        CertificatePolicy certificatePolicy = EnterpriseDeviceManager.getInstance().getCertificatePolicy();
        if (certificatePolicy != null) {
            return certificatePolicy.isOcspCheckEnabled();
        }
        return false;
    }

    public static void setRevocationChecker(PKIXRevocationChecker pKIXRevocationChecker, PKIXParameters pKIXParameters) {
        if (DEBUG) {
            Log.d(TAG, "setRevocationChecker");
        }
        if (isRevocationCheckEnabled()) {
            ArrayList arrayList = new ArrayList();
            for (PKIXCertPathChecker pKIXCertPathChecker : pKIXParameters.getCertPathCheckers()) {
                if (!(pKIXCertPathChecker instanceof PKIXRevocationChecker)) {
                    arrayList.add(pKIXCertPathChecker);
                }
            }
            pKIXParameters.setCertPathCheckers(arrayList);
            if (!isOcspCheckEnabled()) {
                HashSet hashSet = new HashSet();
                hashSet.add(PKIXRevocationChecker.Option.NO_FALLBACK);
                hashSet.add(PKIXRevocationChecker.Option.PREFER_CRLS);
                pKIXRevocationChecker.setOptions(hashSet);
            }
            pKIXParameters.addCertPathChecker(pKIXRevocationChecker);
        }
    }

    public static boolean isChainTrustedByMdm(List<X509Certificate> list) {
        boolean zIsCaCertificateTrustedAsUser = true;
        try {
            CertificatePolicy certificatePolicy = EnterpriseDeviceManager.getInstance().getCertificatePolicy();
            int iMyUserId = UserHandle.myUserId();
            if (certificatePolicy != null ? certificatePolicy.isCertificateTrustedUntrustedEnabledAsUser(iMyUserId) : false) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                for (X509Certificate x509Certificate : list) {
                    byteArrayOutputStream.write(PEM_CERT_BEGIN.getBytes());
                    byteArrayOutputStream.write(java.util.Base64.getEncoder().encode(x509Certificate.getEncoded()));
                    byteArrayOutputStream.write(PEM_CERT_END.getBytes());
                }
                zIsCaCertificateTrustedAsUser = certificatePolicy.isCaCertificateTrustedAsUser(byteArrayOutputStream.toByteArray(), false, false, iMyUserId);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to call isCaCertificateTrustedAsUser() " + e.getMessage());
        }
        if (DEBUG) {
            Log.d(TAG, "isChainTrustedByMdm: " + zIsCaCertificateTrustedAsUser);
        }
        return zIsCaCertificateTrustedAsUser;
    }
}
