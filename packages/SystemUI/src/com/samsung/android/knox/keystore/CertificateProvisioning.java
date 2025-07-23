package com.samsung.android.knox.keystore;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ISecurityPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CertificateProvisioning {
    public static final String CA_CERTIFICATE = "CACERT_";
    public static final int ERROR_EXTRACT_CERT = -3;
    public static final int ERROR_INSTALL_DEFAULT = -4;
    public static final int ERROR_INSTALL_VPN_AND_APPS = -5;
    public static final int ERROR_INSTALL_WIFI = -6;
    public static final int ERROR_INVALID_PARAMETERS = -1;
    public static final int ERROR_KEYSTORE_KEY_NOT_FOUND = 7;
    public static final int ERROR_KEYSTORE_NONE = 1;
    public static final int ERROR_KEYSTORE_PERMISSION_DENIED = 6;
    public static final int ERROR_KEYSTORE_PROTOCOL = 5;
    public static final int ERROR_KEYSTORE_UNDEFINED_ACTION = 9;
    public static final int ERROR_KEYSTORE_VALUE_CORRUPTED = 8;
    public static final int ERROR_KEYSTORE_WRONG_PASSWORD = 10;
    public static final int ERROR_PARSE_CERT = -2;
    public static final int ERROR_SERVICE_UNAVAILABLE = -7;
    public static final int GLOBAL_KEYSTORE_PARAMS = 2;
    public static final int KEYSTORE_DEFAULT = 1;
    public static final int KEYSTORE_FOR_VPN_AND_APPS = 4;
    public static final int KEYSTORE_FOR_WIFI = 2;
    public static int MAXIMUM_CERTIFICATE_NUMBERS = 30;
    public static final int NO_ERROR = 0;
    public static String TAG = "CertificateProvisioning";
    public static final String TYPE_CERTIFICATE = "CERT";
    public static final String TYPE_PKCS12 = "PKCS12";
    public static final String USER_CERTIFICATE = "USRCERT_";
    public static final int USER_KEYSTORE_PARAMS = 5;
    public ContextInfo mContextInfo;
    public ISecurityPolicy mSecurityService;

    public CertificateProvisioning(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public static int generateToken(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    public boolean addPackagesToCertificateWhiteList(List<AppIdentity> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificateProvisioning.addPackagesToCertificateWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mSecurityService.addPackagesToCertificateWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with CertificateProvisioning", e);
            return false;
        }
    }

    public boolean deleteCertificateFromKeystore(CertificateInfo certificateInfo, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificateProvisioning.deleteCertificateFromKeystore");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mSecurityService.deleteCertificateFromKeystore(this.mContextInfo, certificateInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with CertificateProvisioning", e);
            return false;
        }
    }

    public List<CertificateInfo> getCertificatesFromKeystore(int i) {
        List<CertificateInfo> certificatesFromKeystore;
        if (getService() != null) {
            ArrayList arrayList = new ArrayList();
            try {
                int generateToken = generateToken(0, 100);
                do {
                    certificatesFromKeystore = this.mSecurityService.getCertificatesFromKeystore(this.mContextInfo, i, generateToken);
                    if (certificatesFromKeystore != null) {
                        arrayList.addAll(certificatesFromKeystore);
                    }
                    if (certificatesFromKeystore == null) {
                        break;
                    }
                } while (certificatesFromKeystore.size() == MAXIMUM_CERTIFICATE_NUMBERS);
                if (certificatesFromKeystore == null) {
                    if (arrayList.isEmpty()) {
                        return null;
                    }
                }
                return arrayList;
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with CertificateProvisioning", e);
            }
        }
        return new ArrayList(0);
    }

    public int getCredentialStorageStatus() {
        return 1;
    }

    public List<AppIdentity> getPackagesFromCertificateWhiteList() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mSecurityService.getPackagesFromCertificateWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with CertificateProvisioning", e);
            return null;
        }
    }

    public final ISecurityPolicy getService() {
        if (this.mSecurityService == null) {
            this.mSecurityService = ISecurityPolicy.Stub.asInterface(ServiceManager.getService("security_policy"));
        }
        return this.mSecurityService;
    }

    public List<CertificateInfo> getSystemCertificates() {
        if (getService() != null) {
            try {
                return this.mSecurityService.getSystemCertificates(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with CertificateProvisioning", e);
            }
        }
        return new ArrayList(0);
    }

    public boolean installCertificateToKeystore(String str, byte[] bArr, String str2, String str3, int i) {
        return installCertificateToKeystore(str, bArr, str2, str3, i, false) == 0;
    }

    public void installCertificateWithType(String str, byte[] bArr) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificateProvisioning.installCertificateWithType");
        if (getService() != null) {
            try {
                this.mSecurityService.installCertificateWithType(this.mContextInfo, str, bArr);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with CertificateProvisioning", e);
            }
        }
    }

    public void installCertificatesFromSdCard() {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificateProvisioning.installCertificatesFromSdCard");
        if (getService() != null) {
            try {
                this.mSecurityService.installCertificatesFromSdCard(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with CertificateProvisioning", e);
            }
        }
    }

    public boolean removePackagesFromCertificateWhiteList(List<AppIdentity> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificateProvisioning.removePackagesFromCertificateWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mSecurityService.removePackagesFromCertificateWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with CertificateProvisioning", e);
            return false;
        }
    }

    public boolean resetCredentialStorage() {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificateProvisioning.resetCredentialStorage");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mSecurityService.resetCredentialStorage(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with CertificateProvisioning", e);
            return false;
        }
    }

    public boolean unlockCredentialStorage(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificateProvisioning.unlockCredentialStorage");
        return false;
    }

    public int installCertificateToKeystore(byte[] bArr, String str, String str2, int i) {
        return installCertificateToKeystore(null, bArr, str, str2, i, true);
    }

    public final int installCertificateToKeystore(String str, byte[] bArr, String str2, String str3, int i, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "CertificateProvisioning.installCertificateToKeystore");
        if (getService() == null) {
            return -7;
        }
        try {
            return this.mSecurityService.installCertificateToKeystore(this.mContextInfo, str, bArr, str2, str3, i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with CertificateProvisioning", e);
            return -7;
        }
    }
}
