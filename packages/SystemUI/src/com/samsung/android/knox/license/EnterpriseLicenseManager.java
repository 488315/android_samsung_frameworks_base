package com.samsung.android.knox.license;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.IEnterpriseLicense;
import com.samsung.android.knox.license.ILicenseResultCallback;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EnterpriseLicenseManager {
    public static final String ACTION_LICENSE_STATUS = "com.samsung.android.knox.intent.action.LICENSE_STATUS";
    private static final String ACTIVATE_LICENSE_API_USAGE_NAME = "EnterpriseLicenseManager.activateLicense";
    private static final int API_MAX_LEN = 100;
    public static final int ERROR_INTERNAL = 301;
    public static final int ERROR_INTERNAL_SERVER = 401;
    public static final int ERROR_INVALID_LICENSE = 201;
    public static final int ERROR_INVALID_PACKAGE_NAME = 204;
    public static final int ERROR_LICENSE_TERMINATED = 203;
    public static final int ERROR_NETWORK_DISCONNECTED = 501;
    public static final int ERROR_NETWORK_GENERAL = 502;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_CURRENT_DATE = 205;
    public static final int ERROR_NO_MORE_REGISTRATION = 202;
    public static final int ERROR_NULL_PARAMS = 101;
    public static final int ERROR_SIGNATURE_MISMATCH = 206;
    public static final int ERROR_TOO_MANY_REQUESTS = 209;
    public static final int ERROR_UNKNOWN = 102;
    public static final int ERROR_USER_DISAGREES_LICENSE_AGREEMENT = 601;
    public static final int ERROR_VERSION_CODE_MISMATCH = 207;
    public static final String EXTRA_LICENSE_DATA_LICENSE_PERMISSIONS = "com.samsung.android.knox.intent.extra.LICENSE_DATA_LICENSE_PERMISSIONS";
    public static final String EXTRA_LICENSE_DATA_PACKAGENAME = "com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGENAME";
    public static final String EXTRA_LICENSE_DATA_PACKAGEVERSION = "com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGEVERSION";
    public static final String EXTRA_LICENSE_DATA_REQUEST_PACKAGENAME = "com.samsung.android.knox.intent.extra.LICENSE_DATA_REQUEST_PACKAGENAME";
    public static final String EXTRA_LICENSE_DATA_TIME_REMAINING_UNTIL_NEXT_LICENSE_REQUEST = "com.samsung.android.knox.intent.extra.EXTRA_LICENSE_DATA_TIME_REMAINING_UNTIL_NEXT_LICENSE_REQUEST";
    public static final String EXTRA_LICENSE_DATA_UUID = "com.samsung.android.knox.intent.extra.LICENSE_DATA_UUID";
    public static final String EXTRA_LICENSE_ERROR_CODE = "com.samsung.android.knox.intent.extra.LICENSE_ERROR_CODE";
    public static final String EXTRA_LICENSE_GRANTED_PERMISSIONS = "com.samsung.android.knox.intent.extra.LICENSE_GRANTED_PERMISSIONS";
    public static final String EXTRA_LICENSE_KEY = "com.samsung.android.knox.intent.extra.KNOX_LICENSE_KEY";
    public static final String EXTRA_LICENSE_PERM_GROUP = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
    public static final String EXTRA_LICENSE_RESULT_TYPE = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
    public static final String EXTRA_LICENSE_STATUS = "com.samsung.android.knox.intent.extra.LICENSE_STATUS";
    public static final String LICENSE_LOG_API = "api_call";
    public static final String LICENSE_LOG_DATE = "log_date";
    public static final String LICENSE_PERMISSIONS = "Permissions";
    public static final int LICENSE_RESULT_TYPE_ACTIVATION = 800;
    public static final int LICENSE_RESULT_TYPE_DEACTIVATION = 802;
    public static final int LICENSE_RESULT_TYPE_VALIDATION = 801;
    public static final String SUCCESS_STATUS_RESULT = "success";
    private static final String TAG = "EnterpriseLicenseManager";
    private static EnterpriseLicenseManager gLicenseMgrInst;
    private static IEnterpriseLicense lService;
    private static final Object mSync = new Object();
    private final ContextInfo contextInfo = new ContextInfo();

    public static EnterpriseLicenseManager getInstance(Context context) {
        EnterpriseLicenseManager enterpriseLicenseManager;
        synchronized (mSync) {
            try {
                if (gLicenseMgrInst == null) {
                    gLicenseMgrInst = new EnterpriseLicenseManager();
                }
                enterpriseLicenseManager = gLicenseMgrInst;
            } catch (Throwable th) {
                throw th;
            }
        }
        return enterpriseLicenseManager;
    }

    private static synchronized IEnterpriseLicense getService() {
        try {
            if (lService == null) {
                lService = IEnterpriseLicense.Stub.asInterface(ServiceManager.getService("enterprise_license_policy"));
            }
        } catch (Throwable th) {
            throw th;
        }
        return lService;
    }

    public static void log(ContextInfo contextInfo, String str) {
        if (str != null && str.length() > 100) {
            str = str.trim().substring(0, 100);
        }
        try {
            if (getService() != null) {
                lService.log(contextInfo, str, false, false);
            }
        } catch (Exception e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public void activateLicense(String str) {
        try {
            if (getService() != null) {
                log(this.contextInfo, ACTIVATE_LICENSE_API_USAGE_NAME);
                lService.activateLicense(str, null, null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public boolean deleteAllApiCallData() {
        try {
            if (getService() != null) {
                return lService.deleteAllApiCallData();
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "deleteAllApiCallData(). Failed talking to License policy service ", e);
            return false;
        }
    }

    public boolean deleteApiCallData(String str, String str2, Error error) {
        try {
            if (getService() != null) {
                return lService.deleteApiCallData(str, str2, error);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return false;
        }
    }

    public boolean deleteApiCallDataByAdmin(String str) {
        try {
            if (getService() != null) {
                return lService.deleteApiCallDataByAdmin(str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return false;
        }
    }

    public boolean deleteLicense(String str) {
        try {
            if (getService() != null) {
                return lService.deleteLicense(str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return false;
        }
    }

    public boolean deleteLicenseByAdmin(String str) {
        try {
            if (getService() != null) {
                return lService.deleteLicenseByAdmin(str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return false;
        }
    }

    public LicenseInfo[] getAllLicenseInfo() {
        try {
            if (getService() != null) {
                return lService.getAllLicenseInfo();
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return null;
        }
    }

    public JSONArray getApiCallData(String str) throws JSONException {
        try {
            if (getService() != null) {
                Bundle apiCallData = lService.getApiCallData(str);
                JSONArray jSONArray = new JSONArray();
                if (apiCallData != null) {
                    for (String str2 : apiCallData.keySet()) {
                        JSONObject jSONObject = new JSONObject();
                        JSONObject jSONObject2 = new JSONObject();
                        Bundle bundle = apiCallData.getBundle(str2);
                        if (bundle == null) {
                            Log.w(TAG, "Failed to get Bundle using key: " + str2);
                            return null;
                        }
                        for (String str3 : bundle.keySet()) {
                            jSONObject2.put(str3, bundle.getInt(str3));
                        }
                        jSONObject.put(LICENSE_LOG_DATE, str2);
                        jSONObject.put(LICENSE_LOG_API, jSONObject2);
                        jSONArray.put(jSONObject);
                    }
                }
                if (jSONArray.length() == 0) {
                    return null;
                }
                return jSONArray;
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        } catch (JSONException e2) {
            Log.w(TAG, "JSONException", e2);
        }
        return null;
    }

    public JSONArray getApiCallDataByAdmin(String str) throws JSONException {
        try {
            if (getService() != null) {
                Bundle apiCallDataByAdmin = lService.getApiCallDataByAdmin(str);
                JSONArray jSONArray = new JSONArray();
                if (apiCallDataByAdmin != null) {
                    for (String str2 : apiCallDataByAdmin.keySet()) {
                        JSONObject jSONObject = new JSONObject();
                        JSONObject jSONObject2 = new JSONObject();
                        Bundle bundle = apiCallDataByAdmin.getBundle(str2);
                        if (bundle == null) {
                            Log.w(TAG, "Failed to get Bundle using key: " + str2);
                            return null;
                        }
                        for (String str3 : bundle.keySet()) {
                            jSONObject2.put(str3, bundle.getInt(str3));
                        }
                        jSONObject.put(LICENSE_LOG_DATE, str2);
                        jSONObject.put(LICENSE_LOG_API, jSONObject2);
                        jSONArray.put(jSONObject);
                    }
                }
                if (jSONArray.length() == 0) {
                    return null;
                }
                return jSONArray;
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        } catch (JSONException e2) {
            Log.w(TAG, "JSONException", e2);
        }
        return null;
    }

    public List<String> getELMPermissions(String str) {
        try {
            if (getService() != null) {
                return lService.getELMPermissions(str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return null;
        }
    }

    public String getInstanceId(String str) {
        try {
            if (getService() != null) {
                return lService.getInstanceId(str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return null;
        }
    }

    public LicenseInfo getLicenseInfo(String str) {
        try {
            if (getService() != null) {
                return lService.getLicenseInfo(str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return null;
        }
    }

    public LicenseInfo getLicenseInfoByAdmin(String str) {
        try {
            if (getService() != null) {
                return lService.getLicenseInfoByAdmin(str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return null;
        }
    }

    public RightsObject getRightsObject(String str) {
        try {
            if (getService() != null) {
                return lService.getRightsObject(str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return null;
        }
    }

    public RightsObject getRightsObjectByAdmin(String str) {
        try {
            if (getService() != null) {
                return lService.getRightsObjectByAdmin(str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return null;
        }
    }

    public void notifyKlmObservers(String str, LicenseResult licenseResult) {
        try {
            if (getService() != null) {
                lService.notifyKlmObservers(str, licenseResult);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public boolean processLicenseActivationResponse(String str, String str2, String str3, List<String> list, String str4, String str5, RightsObject rightsObject, Error error, String str6, String str7) {
        try {
            if (getService() != null) {
                return lService.processLicenseActivationResponse(str, str2, str3, list, str4, str5, rightsObject, error, str6, str7);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return false;
        }
    }

    public boolean processLicenseValidationResult(String str, RightsObject rightsObject, Error error, String str2, String str3, String str4, List<String> list) {
        try {
            if (getService() != null) {
                return lService.processLicenseValidationResult(str, rightsObject, error, str2, str3, str4, list);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return false;
        }
    }

    public boolean resetLicense(String str) {
        try {
            if (getService() != null) {
                return lService.resetLicense(str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return false;
        }
    }

    public boolean resetLicenseByAdmin(String str) {
        try {
            if (getService() != null) {
                return lService.resetLicenseByAdmin(str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
            return false;
        }
    }

    public void activateLicense(String str, String str2) {
        try {
            if (getService() != null) {
                log(this.contextInfo, ACTIVATE_LICENSE_API_USAGE_NAME);
                lService.activateLicense(str, str2, null);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public static void log(ContextInfo contextInfo, String str, boolean z) {
        if (str != null && str.length() > 100) {
            str = str.trim().substring(0, 100);
        }
        try {
            if (getService() != null) {
                lService.log(contextInfo, str, z, false);
            }
        } catch (Exception e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public void activateLicense(String str, final LicenseResultCallback licenseResultCallback) {
        try {
            if (getService() != null) {
                ILicenseResultCallback.Stub stub = licenseResultCallback != null ? new ILicenseResultCallback.Stub(this) { // from class: com.samsung.android.knox.license.EnterpriseLicenseManager.1
                    @Override // com.samsung.android.knox.license.ILicenseResultCallback
                    public void onLicenseResult(LicenseResult licenseResult) {
                        licenseResultCallback.onLicenseResult(licenseResult);
                    }
                } : null;
                log(this.contextInfo, ACTIVATE_LICENSE_API_USAGE_NAME);
                lService.activateLicense(str, null, stub);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }

    public void activateLicense(String str, String str2, final LicenseResultCallback licenseResultCallback) {
        try {
            if (getService() != null) {
                ILicenseResultCallback.Stub stub = licenseResultCallback != null ? new ILicenseResultCallback.Stub(this) { // from class: com.samsung.android.knox.license.EnterpriseLicenseManager.2
                    @Override // com.samsung.android.knox.license.ILicenseResultCallback
                    public void onLicenseResult(LicenseResult licenseResult) {
                        licenseResultCallback.onLicenseResult(licenseResult);
                    }
                } : null;
                log(this.contextInfo, ACTIVATE_LICENSE_API_USAGE_NAME);
                lService.activateLicense(str, str2, stub);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to License policy service ", e);
        }
    }
}
