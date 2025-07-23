package android.sec.enterprise.certificate;

import android.os.RemoteException;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;

/* loaded from: classes3.dex */
public class CertificatePolicy {
    public static final String BROWSER_MODULE = "browser_module";
    public static final int CERTIFICATE_VALIDATED_SUCCESSFULLY = -1;
    public static final int CERT_ERROR_REVOKED = -206;
    public static final int CERT_ERROR_UNABLE_TO_CHECK_REVOCATION = -205;
    public static final String MSG_ADDITIONAL_CHECKER = "Additional certificate path checker failed.";
    public static final String MSG_CRL_DIST_COULD_NOT_BE_READ = "CRL distribution point extension could not be read";
    public static final String MSG_CRL_NOT_VALID = "No valid CRL found.";
    public static final String MSG_DIST_POINT_COULD_NOT_BE_READ = "Distribution points could not be read.";
    public static final String MSG_EXPIRED_CERT = ", expiration time";
    public static final String MSG_IS_REVOKED_CERT = "is revoked";
    public static final String MSG_NOT_YET_VALID_CERT = ", validation time";
    public static final String MSG_NO_ADDITIONAL_CRL_DECODED = "No additional CRL locations could be decoded from CRL distribution point extension.";
    public static final String MSG_REVOKED_CERT = "Certificate revocation after";
    public static final String MSG_UNABLE_CHECK_OCSP_STATUS = "OCSP check failed!";
    public static final String MSG_UNABLE_CHECK_REVOCATION_STATUS = "Certificate status could not be determined.";
    public static final String MSG_UNABLE_GET_CRL = "Unable to get CRL for certificate";
    public static final String PACKAGE_MODULE = "package_manager_module";
    public static final int SBROWSER_VERIFY_NO_TRUSTED_ROOT = 2;
    public static final int SBROWSER_VERIFY_REVOKED = 1;
    public static final int SBROWSER_VERIFY_UNABLE_TO_CHECK_REVOCATION = 0;
    private static String TAG = "CertificatePolicy";
    public static final String WIFI_MODULE = "wifi_module";

    public void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                service.notifyCertificateFailureAsUser(str, str2, z, i);
            }
        } catch (Exception unused) {
        }
    }

    public boolean isCaCertificateTrustedAsUser(byte[] bArr, boolean z, int i) {
        return isCaCertificateTrustedAsUser(bArr, z, true, i);
    }

    public boolean isCaCertificateTrustedAsUser(byte[] bArr, boolean z, boolean z2, int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isCaCertificateTrustedAsUser(bArr, z, z2, i);
            }
            return true;
        } catch (RemoteException unused) {
            return true;
        }
    }

    public boolean isCertificateTrustedUntrustedEnabledAsUser(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isCertificateTrustedUntrustedEnabledAsUser(i);
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean isRevocationCheckEnabled() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isRevocationCheckEnabled();
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean isOcspCheckEnabled() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isOcspCheckEnabled();
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public void notifyCertificateRemovedAsUser(String str, int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                service.notifyCertificateRemovedAsUser(str, i);
            }
        } catch (Exception unused) {
        }
    }

    public int validateCertificateAtInstallAsUser(byte[] bArr, int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.validateCertificateAtInstallAsUser(bArr, i);
            }
            return -1;
        } catch (Exception unused) {
            return -1;
        }
    }

    public boolean isCertificateValidationAtInstallEnabledAsUser(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isCertificateValidationAtInstallEnabledAsUser(i);
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }
}
