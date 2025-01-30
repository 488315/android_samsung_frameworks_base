package com.samsung.android.knox.license;

import android.content.Context;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.IEnterpriseLicense;
import com.samsung.android.knox.license.ILicenseResultCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KnoxEnterpriseLicenseManager {
    public static final String ACTION_LICENSE_REGISTRATION_UMC_INTERNAL = "com.samsung.android.knox.intent.action.KNOX_LICENSE_REGISTRATION_UMC_INTERNAL";
    public static final String ACTION_LICENSE_STATUS = "com.samsung.android.knox.intent.action.KNOX_LICENSE_STATUS";
    public static final int ERROR_ANOTHER_PROCESS_IN_PLACE = 602;
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
    public static final int ERROR_UNKNOWN = 102;
    public static final int ERROR_USER_DISAGREES_LICENSE_AGREEMENT = 601;
    public static final int ERROR_VERSION_CODE_MISMATCH = 207;
    public static final String EXTRA_LICENSE_ACTIVATION_INITIATOR = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_ACTIVATION_INITIATOR";
    public static final String EXTRA_LICENSE_ATTESTATION_STATUS = "com.samsung.android.knox.intent.extra.LICENSE_ATTESTATION_STATUS";
    public static final String EXTRA_LICENSE_DATA_PACKAGENAME = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_PACKAGENAME";
    public static final String EXTRA_LICENSE_DATA_REQUEST_PACKAGENAME = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_REQUEST_PACKAGENAME";
    public static final String EXTRA_LICENSE_ERROR_CODE = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_ERROR_CODE";
    public static final String EXTRA_LICENSE_GRANTED_PERMISSIONS = "com.samsung.android.knox.intent.extra.LICENSE_GRANTED_PERMISSIONS";
    public static final String EXTRA_LICENSE_KEY = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_KEY";
    public static final String EXTRA_LICENSE_RESULT_TYPE = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_RESULT_TYPE";
    public static final String EXTRA_LICENSE_STATUS = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_STATUS";
    public static final int LICENSE_ACTIVATION_INITIATOR_ADMIN = 900;
    public static final int LICENSE_RESULT_TYPE_ACTIVATION = 800;
    public static final int LICENSE_RESULT_TYPE_DEACTIVATION = 802;
    public static final int LICENSE_RESULT_TYPE_VALIDATION = 801;
    public static final String TAG = "KnoxKnoxEnterpriseLicenseManager";
    public static KnoxEnterpriseLicenseManager gLicenseMgrInst;
    public static IEnterpriseLicense lService;
    public static final Object mSync = new Object();
    public final Context mContext;
    public final ContextInfo mContextInfo;

    public KnoxEnterpriseLicenseManager(ContextInfo contextInfo, Context context) {
        lService = IEnterpriseLicense.Stub.asInterface(ServiceManager.getService("enterprise_license_policy"));
        this.mContext = context;
        this.mContextInfo = contextInfo;
    }

    public static KnoxEnterpriseLicenseManager createInstance(ContextInfo contextInfo, Context context) {
        return new KnoxEnterpriseLicenseManager(contextInfo, context);
    }

    public static KnoxEnterpriseLicenseManager getInstance(Context context) {
        KnoxEnterpriseLicenseManager knoxEnterpriseLicenseManager;
        synchronized (mSync) {
            if (gLicenseMgrInst == null) {
                gLicenseMgrInst = new KnoxEnterpriseLicenseManager(new ContextInfo(Process.myUid()), context);
            }
            knoxEnterpriseLicenseManager = gLicenseMgrInst;
        }
        return knoxEnterpriseLicenseManager;
    }

    public static IEnterpriseLicense getService() {
        if (lService == null) {
            lService = IEnterpriseLicense.Stub.asInterface(ServiceManager.getService("enterprise_license_policy"));
        }
        return lService;
    }

    public final void activateLicense(String str) {
        try {
            if (getService() != null) {
                lService.activateKnoxLicense(this.mContextInfo, str, null, null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public final void activateLicenseForUMC(String str, String str2) {
        activateLicense(str, str2);
    }

    public final void deActivateLicense(String str) {
        try {
            if (getService() != null) {
                lService.deActivateKnoxLicense(this.mContextInfo, str, null, null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public final void deActivateLicenseForUMC(String str, String str2) {
        deActivateLicense(str, str2);
    }

    public final List<ActivationInfo> getAllLicenseActivationsInfos() {
        try {
            if (getService() != null) {
                return lService.getAllLicenseActivationsInfos();
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service to get all licenses statuses", e);
        }
        return new ArrayList();
    }

    public final ActivationInfo getLicenseActivationInfo() {
        try {
            if (getService() != null) {
                return lService.getLicenseActivationInfo(this.mContextInfo, null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service to get license status", e);
        }
        return null;
    }

    public final boolean isEulaBypassAllowed(String str) {
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

    public final boolean isServiceAvailable(String str, String str2) {
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

    public final boolean processLicenseResponse(String str, String str2, Error error, int i, int i2, String str3, RightsObject rightsObject, int i3) {
        return processLicenseResponse(str, "-1", str2, error, i, i2, str3, rightsObject, i3);
    }

    public final boolean processLicenseResponse(String str, String str2, String str3, Error error, int i, int i2, String str4, RightsObject rightsObject, int i3) {
        try {
            if (getService() != null) {
                return lService.processKnoxLicenseResponse(str, str2, str3, error, i, i2, str4, rightsObject, i3);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return false;
        }
    }

    public final void activateLicense(String str, String str2) {
        try {
            if (getService() != null) {
                lService.activateKnoxLicense(this.mContextInfo, str, str2, null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public final void deActivateLicense(String str, String str2) {
        try {
            if (getService() != null) {
                lService.deActivateKnoxLicense(this.mContextInfo, str, str2, null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public final ActivationInfo getLicenseActivationInfo(String str) {
        try {
            if (getService() != null) {
                return lService.getLicenseActivationInfo(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service to get license status for package " + str, e);
            return null;
        }
    }

    public static KnoxEnterpriseLicenseManager getInstance(ContextInfo contextInfo, Context context) {
        KnoxEnterpriseLicenseManager knoxEnterpriseLicenseManager;
        synchronized (mSync) {
            if (gLicenseMgrInst == null) {
                gLicenseMgrInst = new KnoxEnterpriseLicenseManager(contextInfo, context);
            }
            knoxEnterpriseLicenseManager = gLicenseMgrInst;
        }
        return knoxEnterpriseLicenseManager;
    }

    public final void activateLicense(String str, String str2, final LicenseResultCallback licenseResultCallback) {
        try {
            if (getService() != null) {
                lService.activateKnoxLicense(this.mContextInfo, str, str2, licenseResultCallback != null ? new ILicenseResultCallback.Stub() { // from class: com.samsung.android.knox.license.KnoxEnterpriseLicenseManager.1
                    @Override // com.samsung.android.knox.license.ILicenseResultCallback
                    public final void onLicenseResult(LicenseResult licenseResult) {
                        licenseResultCallback.onLicenseResult(licenseResult);
                    }
                } : null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public final void deActivateLicense(String str, String str2, final LicenseResultCallback licenseResultCallback) {
        try {
            if (getService() != null) {
                lService.deActivateKnoxLicense(this.mContextInfo, str, str2, licenseResultCallback != null ? new ILicenseResultCallback.Stub() { // from class: com.samsung.android.knox.license.KnoxEnterpriseLicenseManager.3
                    @Override // com.samsung.android.knox.license.ILicenseResultCallback
                    public final void onLicenseResult(LicenseResult licenseResult) {
                        licenseResultCallback.onLicenseResult(licenseResult);
                    }
                } : null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public final void activateLicense(String str, final LicenseResultCallback licenseResultCallback) {
        try {
            if (getService() != null) {
                lService.activateKnoxLicense(this.mContextInfo, str, null, licenseResultCallback != null ? new ILicenseResultCallback.Stub() { // from class: com.samsung.android.knox.license.KnoxEnterpriseLicenseManager.2
                    @Override // com.samsung.android.knox.license.ILicenseResultCallback
                    public final void onLicenseResult(LicenseResult licenseResult) {
                        licenseResultCallback.onLicenseResult(licenseResult);
                    }
                } : null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public final void deActivateLicense(String str, final LicenseResultCallback licenseResultCallback) {
        try {
            if (getService() != null) {
                lService.deActivateKnoxLicense(this.mContextInfo, str, null, licenseResultCallback != null ? new ILicenseResultCallback.Stub() { // from class: com.samsung.android.knox.license.KnoxEnterpriseLicenseManager.4
                    @Override // com.samsung.android.knox.license.ILicenseResultCallback
                    public final void onLicenseResult(LicenseResult licenseResult) {
                        licenseResultCallback.onLicenseResult(licenseResult);
                    }
                } : null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }
}
