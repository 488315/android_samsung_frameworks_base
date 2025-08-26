package com.samsung.android.knox.keystore;

import android.content.pm.Signature;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.keystore.ICertificatePolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class CertificatePolicy {
    public static final String ACTION_CERTIFICATE_FAILURE = "com.samsung.android.knox.intent.action.CERTIFICATE_FAILURE";
    public static final String ACTION_CERTIFICATE_REMOVED = "com.samsung.android.knox.intent.action.CERTIFICATE_REMOVED";
    public static final String ACTION_REFRESH_CREDENTIALS_UI_INTERNAL = "com.samsung.android.knox.intent.action.REFRESH_CREDENTIALS_UI_INTERNAL";
    public static final String APP_INFO_PKGNAME = "appInfoPkgName";
    public static final String BROWSER_MODULE = "browser_module";
    public static final int CERTIFICATE_FAIL_ALG_NON_FIPS_APPROVED = 9;
    public static final int CERTIFICATE_FAIL_ALTSUBJECT_MISMATCH = 6;
    public static final int CERTIFICATE_FAIL_BAD_CERTIFICATE = 7;
    public static final int CERTIFICATE_FAIL_EXPIRED = 4;
    public static final int CERTIFICATE_FAIL_INSTALL_PARSE_CERTIFICATE_ENCODING = 11;
    public static final int CERTIFICATE_FAIL_INSTALL_PARSE_INCONSISTENT_CERTIFICATES = 12;
    public static final int CERTIFICATE_FAIL_INSTALL_PARSE_NO_CERTIFICATES = 10;
    public static final int CERTIFICATE_FAIL_NOT_YET_VALID = 3;
    public static final int CERTIFICATE_FAIL_REVOKED = 2;
    public static final int CERTIFICATE_FAIL_SERVER_CHAIN_PROBE = 8;
    public static final int CERTIFICATE_FAIL_SUBJECT_MISMATCH = 5;
    public static final int CERTIFICATE_FAIL_UNABLE_TO_CHECK_REVOCATION_STATUS = 13;
    public static final int CERTIFICATE_FAIL_UNSPECIFIED = 0;
    public static final int CERTIFICATE_FAIL_UNTRUSTED = 1;
    public static final int CERTIFICATE_VALIDATED_SUCCESSFULLY = -1;
    public static final String EXTRA_CERTIFICATE_FAILURE_MESSAGE = "com.samsung.android.knox.intent.extra.CERTIFICATE_FAILURE_MESSAGE";
    public static final String EXTRA_CERTIFICATE_FAILURE_MODULE = "com.samsung.android.knox.intent.extra.CERTIFICATE_FAILURE_MODULE";
    public static final String EXTRA_CERTIFICATE_REMOVED_SUBJECT = "com.samsung.android.knox.intent.extra.CERTIFICATE_REMOVED_SUBJECT";
    public static final String EXTRA_USER_ID = "com.samsung.android.knox.intent.extra.USER_ID";
    public static final String INSTALLER_MODULE = "installer_module";
    public static final String IS_MARKET_INSTALLATION = "isMarketInstallation";
    public static final String PACKAGE_MODULE = "package_manager_module";
    public static String TAG = "CertificatePolicy";
    public static final String WIFI_MODULE = "wifi_module";
    public IApplicationPolicy mAppService;
    public ContextInfo mContextInfo;
    public IRestrictionPolicy mRestrictionService;
    public ICertificatePolicy mService;

    public CertificatePolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public boolean addPermissionApplicationPrivateKey(PermissionApplicationPrivateKey permissionApplicationPrivateKey) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.addPermissionApplicationPrivateKey");
        if ((KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION >= 16 || permissionApplicationPrivateKey == null || TextUtils.isEmpty(permissionApplicationPrivateKey.getStorageName())) && getService() != null) {
            try {
                return this.mService.addPermissionApplicationPrivateKey(this.mContextInfo, permissionApplicationPrivateKey);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
        return false;
    }

    public boolean addTrustedCaCertificateList(List<X509Certificate> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.addTrustedCaCertificateList");
        if (getService() != null && list != null) {
            try {
                ArrayList arrayList = new ArrayList();
                for (X509Certificate x509Certificate : list) {
                    if (x509Certificate == null) {
                        return false;
                    }
                    arrayList.add(new CertificateInfo(x509Certificate));
                }
                return this.mService.addTrustedCaCertificateList(this.mContextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
        return false;
    }

    public boolean addUntrustedCertificateList(List<X509Certificate> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.addUntrustedCertificateList");
        if (getService() != null && list != null) {
            try {
                ArrayList arrayList = new ArrayList();
                for (X509Certificate x509Certificate : list) {
                    if (x509Certificate == null) {
                        return false;
                    }
                    arrayList.add(new CertificateInfo(x509Certificate));
                }
                return this.mService.addUntrustedCertificateList(this.mContextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
        return false;
    }

    public boolean allowUserRemoveCertificates(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.allowUserRemoveCertificates");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowUserRemoveCertificates(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return false;
        }
    }

    public boolean clearPermissionApplicationPrivateKey() {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.clearPermissionApplicationPrivateKey");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearPermissionApplicationPrivateKey(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return false;
        }
    }

    public boolean clearTrustedCaCertificateList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.clearTrustedCaCertificateList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearTrustedCaCertificateList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return false;
        }
    }

    public boolean clearUntrustedCertificateList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.clearUntrustedCertificateList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearUntrustedCertificateList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return false;
        }
    }

    public boolean enableCertificateFailureNotification(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.enableCertificateFailureNotification");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.enableCertificateFailureNotification(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return false;
        }
    }

    public boolean enableCertificateValidationAtInstall(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.enableCertificateValidationAtInstall");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.enableCertificateValidationAtInstall(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return false;
        }
    }

    public boolean enableOcspCheck(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.enableOcspCheck");
        if (getAppService() == null) {
            return false;
        }
        try {
            return this.mAppService.enableOcspCheck(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean enableRevocationCheck(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.enableRevocationCheck");
        if (getAppService() == null) {
            return false;
        }
        try {
            return this.mAppService.enableRevocationCheck(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public final IApplicationPolicy getAppService() {
        if (this.mAppService == null) {
            this.mAppService = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        }
        return this.mAppService;
    }

    public List<String[]> getIdentitiesFromSignatures(Signature[] signatureArr) {
        if (getService() == null) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            if (signatureArr != null) {
                for (Signature signature : signatureArr) {
                    arrayList.add(signature.toCharsString());
                }
            }
            return this.mService.getIdentitiesFromSignatures(this.mContextInfo, arrayList);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return null;
        }
    }

    public List<PermissionApplicationPrivateKey> getListPermissionApplicationPrivateKey() {
        if (getService() != null) {
            try {
                return this.mService.getListPermissionApplicationPrivateKey(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
        return new ArrayList(0);
    }

    public final IRestrictionPolicy getRestrictionService() {
        if (this.mRestrictionService == null) {
            this.mRestrictionService = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        }
        return this.mRestrictionService;
    }

    public final ICertificatePolicy getService() {
        if (this.mService == null) {
            this.mService = ICertificatePolicy.Stub.asInterface(ServiceManager.getService("certificate_policy"));
        }
        return this.mService;
    }

    public List<CertificateControlInfo> getTrustedCaCertificateList() {
        if (getService() != null) {
            try {
                return this.mService.getTrustedCaCertificateList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
        return new ArrayList();
    }

    public List<CertificateControlInfo> getUntrustedCertificateList() {
        if (getService() != null) {
            try {
                return this.mService.getUntrustedCertificateList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
        return new ArrayList();
    }

    public boolean isCaCertificateDisabledAsUser(String str, int i) {
        try {
            if (getService() != null) {
                return this.mService.isCaCertificateDisabledAsUser(str, i);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Certificate policy API isCaCertificateDisabledAsUser ", e);
            return false;
        }
    }

    public boolean isCaCertificateTrustedAsUser(byte[] bArr, boolean z, int i) {
        if (getService() != null) {
            try {
                if (!this.mService.isCertificateTrustedUntrustedEnabledAsUser(i)) {
                    return true;
                }
                try {
                    try {
                        return this.mService.isCaCertificateTrustedAsUser(new CertificateInfo((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr))), z, i);
                    } catch (RemoteException e) {
                        Log.w(TAG, "Failed talking with certificate policy", e);
                    }
                } catch (Exception e2) {
                    Log.w(TAG, "Problem converting certificate! " + e2);
                    return false;
                }
            } catch (RemoteException e3) {
                Log.w(TAG, "Failed talking with certificate policy to check if isTrustedUntrustedEnabled", e3);
            }
        }
        return true;
    }

    public boolean isCertificateFailureNotificationEnabled() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isCertificateFailureNotificationEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return false;
        }
    }

    public boolean isCertificateValidationAtInstallEnabled() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isCertificateValidationAtInstallEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return false;
        }
    }

    public boolean isCertificateValidationAtInstallEnabledAsUser(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isCertificateValidationAtInstallEnabledAsUser(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return false;
        }
    }

    public boolean isNonTrustedAppInstallBlocked() {
        if (getRestrictionService() == null) {
            return false;
        }
        try {
            return this.mRestrictionService.isNonTrustedAppInstallBlocked(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public boolean isOcspCheckEnabled(String str) {
        if (getAppService() == null) {
            return false;
        }
        try {
            return this.mAppService.isOcspCheckEnabled(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public String isPrivateKeyApplicationPermitted(String str, String str2, int i, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.isPrivateKeyApplicationPermitted", true);
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.isPrivateKeyApplicationPermitted(this.mContextInfo, str, str2, i, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return null;
        }
    }

    public String isPrivateKeyApplicationPermittedAsUser(String str, String str2, int i, List<String> list, int i2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.isPrivateKeyApplicationPermittedAsUser", true);
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.isPrivateKeyApplicationPermittedAsUser(str, str2, i, list, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return null;
        }
    }

    public boolean isRevocationCheckEnabled(String str) {
        if (getAppService() == null) {
            return false;
        }
        try {
            return this.mAppService.isRevocationCheckEnabled(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean isUserRemoveCertificatesAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isUserRemoveCertificatesAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return true;
        }
    }

    public boolean isUserRemoveCertificatesAllowedAsUser(int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isUserRemoveCertificatesAllowedAsUser(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return true;
        }
    }

    public void notifyCertificateFailure(String str, String str2, boolean z) {
        if (getService() != null) {
            try {
                this.mService.notifyCertificateFailure(str, str2, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
    }

    public void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i) {
        if (getService() != null) {
            try {
                this.mService.notifyCertificateFailureAsUser(str, str2, z, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
    }

    public boolean removePermissionApplicationPrivateKey(PermissionApplicationPrivateKey permissionApplicationPrivateKey) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.removePermissionApplicationPrivateKey");
        if ((KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION >= 16 || permissionApplicationPrivateKey == null || TextUtils.isEmpty(permissionApplicationPrivateKey.getStorageName())) && getService() != null) {
            try {
                return this.mService.removePermissionApplicationPrivateKey(this.mContextInfo, permissionApplicationPrivateKey);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
        return false;
    }

    public boolean removeTrustedCaCertificateList(List<X509Certificate> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.removeTrustedCaCertificateList");
        if (getService() != null && list != null) {
            try {
                ArrayList arrayList = new ArrayList();
                for (X509Certificate x509Certificate : list) {
                    if (x509Certificate == null) {
                        return false;
                    }
                    arrayList.add(new CertificateInfo(x509Certificate));
                }
                return this.mService.removeTrustedCaCertificateList(this.mContextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
        return false;
    }

    public boolean removeUntrustedCertificateList(List<X509Certificate> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.removeUntrustedCertificateList");
        if (getService() != null && list != null) {
            try {
                ArrayList arrayList = new ArrayList();
                for (X509Certificate x509Certificate : list) {
                    if (x509Certificate == null) {
                        return false;
                    }
                    arrayList.add(new CertificateInfo(x509Certificate));
                }
                return this.mService.removeUntrustedCertificateList(this.mContextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
        return false;
    }

    public boolean setNonTrustedAppInstallBlock(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.setNonTrustedAppInstallBlock");
        if (getRestrictionService() == null) {
            return false;
        }
        try {
            return this.mRestrictionService.setNonTrustedAppInstallBlock(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public int validateCertificateAtInstall(byte[] bArr) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.validateCertificateAtInstall");
        if (getService() != null) {
            try {
                try {
                    return this.mService.validateCertificateAtInstall(new CertificateInfo((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr))));
                } catch (RemoteException e) {
                    Log.w(TAG, "Failed talking with certificate policy", e);
                }
            } catch (Exception e2) {
                Log.w(TAG, "Problem converting certificate! " + e2);
            }
        }
        return -1;
    }

    public int validateChainAtInstall(List<X509Certificate> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificatePolicy.validateChainAtInstall");
        if (getService() == null || list == null) {
            return -1;
        }
        try {
            if (list.size() == 0) {
                return -1;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<X509Certificate> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new CertificateInfo(it.next()));
            }
            return this.mService.validateChainAtInstall(arrayList);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with certificate policy", e);
            return -1;
        }
    }

    public boolean isNonTrustedAppInstallBlocked(int i) {
        if (getRestrictionService() == null) {
            return false;
        }
        try {
            return this.mRestrictionService.isNonTrustedAppInstallBlockedAsUser(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public void notifyCertificateFailure(String str, String str2) {
        if (getService() != null) {
            try {
                this.mService.notifyCertificateFailure(str, str2, false);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with certificate policy", e);
            }
        }
    }
}
