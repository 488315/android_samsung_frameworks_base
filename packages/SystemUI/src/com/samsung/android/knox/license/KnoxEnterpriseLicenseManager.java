package com.samsung.android.knox.license;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.IEnterpriseLicense;
import com.samsung.android.knox.license.ILicenseResultCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class KnoxEnterpriseLicenseManager {
    public static final String ACTION_LICENSE_STATUS = "com.samsung.android.knox.intent.action.KNOX_LICENSE_STATUS";
    private static final String ACTIVATE_LICENSE_API_USAGE_NAME = "KnoxEnterpriseLicenseManager.activateLicense";
    private static final String DEACTIVATE_LICENSE_API_USAGE_NAME = "KnoxEnterpriseLicenseManager.deActivateLicense";
    public static final int ERROR_INTERNAL = 301;
    public static final int ERROR_INTERNAL_SERVER = 401;
    public static final int ERROR_INVALID_BINDING = 208;
    public static final int ERROR_INVALID_LICENSE = 201;
    public static final int ERROR_INVALID_PACKAGE_NAME = 204;
    public static final int ERROR_LICENSE_ACTIVATION_NOT_FOUND = 703;
    public static final int ERROR_LICENSE_DEACTIVATED = 700;
    public static final int ERROR_LICENSE_EXPIRED = 701;
    public static final int ERROR_LICENSE_QUANTITY_EXHAUSTED = 702;
    public static final int ERROR_LICENSE_QUANTITY_EXHAUSTED_ON_AUTO_RELEASE = 704;
    public static final int ERROR_LICENSE_TERMINATED = 203;
    public static final int ERROR_NETWORK_DISCONNECTED = 501;
    public static final int ERROR_NETWORK_GENERAL = 502;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_CURRENT_DATE = 205;
    public static final int ERROR_NULL_PARAMS = 101;
    public static final int ERROR_SIGNATURE_MISMATCH = 206;
    public static final int ERROR_TOO_MANY_REQUESTS = 209;
    public static final int ERROR_UNKNOWN = 102;
    public static final int ERROR_USER_DISAGREES_LICENSE_AGREEMENT = 601;
    public static final int ERROR_VERSION_CODE_MISMATCH = 207;
    public static final String EXTRA_LICENSE_ACTIVATION_INITIATOR = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_ACTIVATION_INITIATOR";
    public static final String EXTRA_LICENSE_DATA_PACKAGENAME = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_PACKAGENAME";
    public static final String EXTRA_LICENSE_DATA_REQUEST_PACKAGENAME = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_REQUEST_PACKAGENAME";
    public static final String EXTRA_LICENSE_DATA_TIME_REMAINING_UNTIL_NEXT_LICENSE_REQUEST = "com.samsung.android.knox.intent.extra.EXTRA_LICENSE_DATA_TIME_REMAINING_UNTIL_NEXT_LICENSE_REQUEST";
    public static final String EXTRA_LICENSE_DATA_UUID = "com.samsung.android.knox.intent.extra.LICENSE_DATA_UUID";
    public static final String EXTRA_LICENSE_ERROR_CODE = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_ERROR_CODE";
    public static final String EXTRA_LICENSE_GRANTED_PERMISSIONS = "com.samsung.android.knox.intent.extra.LICENSE_GRANTED_PERMISSIONS";
    public static final String EXTRA_LICENSE_KEY = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_KEY";
    public static final String EXTRA_LICENSE_RESULT_TYPE = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_RESULT_TYPE";
    public static final String EXTRA_LICENSE_STATUS = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_STATUS";
    public static final int LICENSE_ACTIVATION_INITIATOR_ADMIN = 900;
    public static final int LICENSE_RESULT_TYPE_ACTIVATION = 800;
    public static final int LICENSE_RESULT_TYPE_DEACTIVATION = 802;
    public static final int LICENSE_RESULT_TYPE_VALIDATION = 801;
    private static final String TAG = "KnoxEnterpriseLicenseManager";
    private static KnoxEnterpriseLicenseManager gLicenseMgrInst;
    private static IEnterpriseLicense lService;
    private static final Object mSync = new Object();
    private final ContextInfo contextInfo;

    public KnoxEnterpriseLicenseManager() {
        lService = IEnterpriseLicense.Stub.asInterface(ServiceManager.getService("enterprise_license_policy"));
        this.contextInfo = new ContextInfo();
    }

    public static KnoxEnterpriseLicenseManager getInstance(Context context) {
        KnoxEnterpriseLicenseManager knoxEnterpriseLicenseManager;
        synchronized (mSync) {
            try {
                if (gLicenseMgrInst == null) {
                    gLicenseMgrInst = new KnoxEnterpriseLicenseManager();
                }
                knoxEnterpriseLicenseManager = gLicenseMgrInst;
            } catch (Throwable th) {
                throw th;
            }
        }
        return knoxEnterpriseLicenseManager;
    }

    private static IEnterpriseLicense getService() {
        if (lService == null) {
            lService = IEnterpriseLicense.Stub.asInterface(ServiceManager.getService("enterprise_license_policy"));
        }
        return lService;
    }

    public void activateLicense(String str) {
        try {
            if (getService() != null) {
                EnterpriseLicenseManager.log(this.contextInfo, ACTIVATE_LICENSE_API_USAGE_NAME);
                lService.activateKnoxLicense(str, null, null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public void deActivateLicense(String str) {
        try {
            if (getService() != null) {
                EnterpriseLicenseManager.log(this.contextInfo, DEACTIVATE_LICENSE_API_USAGE_NAME);
                lService.deActivateKnoxLicense(str, null, null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public List<ActivationInfo> getAllLicenseActivationsInfos() {
        try {
            if (getService() != null) {
                return lService.getAllLicenseActivationsInfos();
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service to get all licenses statuses", e);
        }
        return new ArrayList();
    }

    public ActivationInfo getLicenseActivationInfo() {
        try {
            if (getService() != null) {
                return lService.getLicenseActivationInfo(null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service to get license status", e);
        }
        return null;
    }

    public boolean isEulaBypassAllowed(String str) {
        try {
            if (getService() != null) {
                return lService.isEulaBypassAllowed(str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service to check for EULA bypass", e);
            return false;
        }
    }

    public boolean isServiceAvailable(String str, String str2) {
        try {
            if (getService() != null) {
                return lService.isServiceAvailable(str, str2);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return false;
        }
    }

    public boolean processLicenseResponse(String str, String str2, String str3, List<String> list, String str4, Error error, int i, String str5, RightsObject rightsObject) {
        try {
            if (getService() != null) {
                return lService.processKnoxLicenseResponse(str, str2, str3, list, str4, error, i, str5, rightsObject);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return false;
        }
    }

    public ActivationInfo getLicenseActivationInfo(String str) {
        try {
            if (getService() != null) {
                return lService.getLicenseActivationInfo(str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service to get license status for package " + str, e);
            return null;
        }
    }

    public void activateLicense(String str, String str2) {
        try {
            if (getService() != null) {
                EnterpriseLicenseManager.log(this.contextInfo, ACTIVATE_LICENSE_API_USAGE_NAME);
                lService.activateKnoxLicense(str, str2, null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public void deActivateLicense(String str, String str2) {
        try {
            if (getService() != null) {
                EnterpriseLicenseManager.log(this.contextInfo, DEACTIVATE_LICENSE_API_USAGE_NAME);
                lService.deActivateKnoxLicense(str, str2, null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public void activateLicense(String str, String str2, final LicenseResultCallback licenseResultCallback) {
        try {
            if (getService() != null) {
                ILicenseResultCallback.Stub stub = licenseResultCallback != null ? new ILicenseResultCallback.Stub(this) { // from class: com.samsung.android.knox.license.KnoxEnterpriseLicenseManager.1
                    @Override // com.samsung.android.knox.license.ILicenseResultCallback
                    public void onLicenseResult(LicenseResult licenseResult) {
                        licenseResultCallback.onLicenseResult(licenseResult);
                    }
                } : null;
                EnterpriseLicenseManager.log(this.contextInfo, ACTIVATE_LICENSE_API_USAGE_NAME);
                lService.activateKnoxLicense(str, str2, stub);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public void deActivateLicense(String str, String str2, final LicenseResultCallback licenseResultCallback) {
        try {
            if (getService() != null) {
                ILicenseResultCallback.Stub stub = licenseResultCallback != null ? new ILicenseResultCallback.Stub(this) { // from class: com.samsung.android.knox.license.KnoxEnterpriseLicenseManager.3
                    @Override // com.samsung.android.knox.license.ILicenseResultCallback
                    public void onLicenseResult(LicenseResult licenseResult) {
                        licenseResultCallback.onLicenseResult(licenseResult);
                    }
                } : null;
                EnterpriseLicenseManager.log(this.contextInfo, DEACTIVATE_LICENSE_API_USAGE_NAME);
                lService.deActivateKnoxLicense(str, str2, stub);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public void activateLicense(String str, final LicenseResultCallback licenseResultCallback) {
        try {
            if (getService() != null) {
                ILicenseResultCallback.Stub stub = licenseResultCallback != null ? new ILicenseResultCallback.Stub(this) { // from class: com.samsung.android.knox.license.KnoxEnterpriseLicenseManager.2
                    @Override // com.samsung.android.knox.license.ILicenseResultCallback
                    public void onLicenseResult(LicenseResult licenseResult) {
                        licenseResultCallback.onLicenseResult(licenseResult);
                    }
                } : null;
                EnterpriseLicenseManager.log(this.contextInfo, ACTIVATE_LICENSE_API_USAGE_NAME);
                lService.activateKnoxLicense(str, null, stub);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public void deActivateLicense(String str, final LicenseResultCallback licenseResultCallback) {
        try {
            if (getService() != null) {
                ILicenseResultCallback.Stub stub = licenseResultCallback != null ? new ILicenseResultCallback.Stub(this) { // from class: com.samsung.android.knox.license.KnoxEnterpriseLicenseManager.4
                    @Override // com.samsung.android.knox.license.ILicenseResultCallback
                    public void onLicenseResult(LicenseResult licenseResult) {
                        licenseResultCallback.onLicenseResult(licenseResult);
                    }
                } : null;
                EnterpriseLicenseManager.log(this.contextInfo, DEACTIVATE_LICENSE_API_USAGE_NAME);
                lService.deActivateKnoxLicense(str, null, stub);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }
}
