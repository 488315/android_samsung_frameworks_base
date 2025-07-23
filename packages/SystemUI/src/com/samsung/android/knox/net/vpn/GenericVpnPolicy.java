package com.samsung.android.knox.net.vpn;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.container.RCPPolicy$$ExternalSyntheticOutline0;
import com.samsung.android.knox.keystore.CertificateInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.vpn.IKnoxVpnPolicy;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class GenericVpnPolicy {
    public static final float ERROR_SUPPORTED_VERSION = 2.4f;
    public static final int INVALID_CONTAINER_ID = 0;
    public static final String KEY_TETHER_AUTH_LOGIN_PAGE = "key-tether-auth-login-page";
    public static final String KEY_TETHER_AUTH_RESPONSE_PAGE = "key-tether-auth-response-page";
    public static final String KEY_TETHER_CAPTIVE_PORTAL_ALIAS = "key-tether-captive-portal-alias";
    public static final String KEY_TETHER_CAPTIVE_PORTAL_CERTIFICATE = "key-tether-captive-portal-certificate";
    public static final String KEY_TETHER_CAPTIVE_PORTAL_CERT_PASSWORD = "key-tether-captive-portal-cert-password";
    public static final String KEY_TETHER_CA_ALIAS = "key-tether-ca-alias";
    public static final String KEY_TETHER_CA_CERTIFICATE = "key-tether-ca-certificate";
    public static final String KEY_TETHER_CA_CERT_PASSWORD = "key-tether-ca-cert-password";
    public static final String KEY_TETHER_CLIENT_CERTIFICATE_ISSUED_CN = "key-tether-client-certificate-issued-cn";
    public static final String KEY_TETHER_CLIENT_CERTIFICATE_ISSUER_CN = "key-tether-client-certificate-issuer-cn";
    public static final String KEY_TETHER_USER_ALIAS = "key-tether-user-alias";
    public static final String KEY_TETHER_USER_CERTIFICATE = "key-tether-user-certificate";
    public static final String KEY_TETHER_USER_CERT_PASSWORD = "key-tether-user-cert-password";
    public static final String KNOX_SDK_VERSION_CHARACTER = "KNOX_ENTERPRISE_SDK_VERSION_";
    public static String TAG = "GenericVpnPolicy";
    public static boolean VPN_RETURN_BOOL_ERROR = false;
    public static int VPN_RETURN_INT_ERROR = -1;
    public static int VPN_RETURN_INT_SUCCESS;
    public static IKnoxVpnPolicy mKnoxVpnPolicyService;
    public String vendorName;
    public KnoxVpnContext vpnContext;
    public static final boolean DBG = Debug.semIsProductDev();
    public static Context mContext = null;
    public static IEnterpriseDeviceManager mEnterpriseDeviceManager = null;
    public static HashMap<String, GenericVpnPolicy> genericVpnObjectMap = new HashMap<>();

    private GenericVpnPolicy(Context context, String str) {
        this.vpnContext = null;
        this.vendorName = null;
        mContext = context;
        if (DBG) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("GenericVpnPolicy ctor : vendorName = ", str, TAG);
        }
        this.vendorName = str;
    }

    public static synchronized GenericVpnPolicy getInstance(Context context, KnoxVpnContext knoxVpnContext) {
        GenericVpnPolicy genericVpnPolicy;
        GenericVpnPolicy genericVpnPolicy2;
        synchronized (GenericVpnPolicy.class) {
            String str = knoxVpnContext.vendorName;
            String transformedVendorName = getTransformedVendorName(str, knoxVpnContext.personaId);
            boolean z = DBG;
            if (z) {
                Log.d(TAG, "GenericVpnPolicy getInstance : vendorName = " + transformedVendorName);
            }
            genericVpnPolicy = null;
            if (transformedVendorName != null) {
                try {
                    synchronized (GenericVpnPolicy.class) {
                        try {
                            if (genericVpnObjectMap.containsKey(transformedVendorName)) {
                                genericVpnPolicy2 = genericVpnObjectMap.get(transformedVendorName);
                            } else {
                                GenericVpnPolicy genericVpnPolicy3 = new GenericVpnPolicy(context, knoxVpnContext);
                                genericVpnObjectMap.put(transformedVendorName, genericVpnPolicy3);
                                genericVpnPolicy2 = genericVpnPolicy3;
                            }
                            if (genericVpnPolicy2 != null && !KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(str)) {
                                boolean bindKnoxVpnInterface = getKnoxVpnPolicyService().bindKnoxVpnInterface(knoxVpnContext);
                                if (z) {
                                    Log.d(TAG, "GenericVpnPolicy getInstance : bindSuccess = " + bindKnoxVpnInterface);
                                }
                                if (!bindKnoxVpnInterface) {
                                    genericVpnObjectMap.remove(transformedVendorName);
                                    genericVpnPolicy2 = null;
                                }
                            }
                            genericVpnPolicy = genericVpnPolicy2;
                        } finally {
                        }
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "GenericVpnPolicy getInstance : returning null for vendorName = " + str + "; Exception = " + Log.getStackTraceString(e));
                }
            }
        }
        return genericVpnPolicy;
    }

    public static IKnoxVpnPolicy getKnoxVpnPolicyService() {
        if (mKnoxVpnPolicyService == null) {
            mKnoxVpnPolicyService = IKnoxVpnPolicy.Stub.asInterface(ServiceManager.getService(KnoxVpnPolicyConstants.KNOX_VPN_POLICY_SERVICE));
        }
        if (DBG) {
            Log.d(TAG, "KnoxVpnPolicy getService : mKnoxVpnPolicyService = " + mKnoxVpnPolicyService);
        }
        return mKnoxVpnPolicyService;
    }

    public static String getTransformedVendorName(String str, int i) {
        return i + "_" + str;
    }

    public int activateVpnProfile(String str, boolean z) {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "activateVpnProfile >> mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.activateVpnProfile");
            EnterpriseResponseData activateVpnProfile = mKnoxVpnPolicyService.activateVpnProfile(this.vpnContext, str, z);
            if (activateVpnProfile == null) {
                Log.e(TAG, "activateVpnProfile >> mEnterpriseResponseData == null");
                return -1;
            }
            int intValue = ((Integer) activateVpnProfile.getData()).intValue();
            if (intValue == 0 && z) {
                Intent intent = new Intent();
                int userId = UserHandle.getUserId(Binder.getCallingUid());
                intent.setClassName("com.android.vpndialogs", "com.android.vpndialogs.KnoxVpnPPDialog");
                intent.addFlags(1350565888);
                if (mContext != null) {
                    Log.d(TAG, "startActivityAsUser  KnoxVpnPPDialog userId = " + userId);
                    mContext.startActivityAsUser(intent, new UserHandle(userId));
                    return intValue;
                }
            }
            return intValue;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API activateVpnProfile-Exception"), TAG);
            return -1;
        }
    }

    public int addAllContainerPackagesToVpn(int i, String str) throws IllegalArgumentException {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "addAllContainerPackagesToVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.addAllContainerPackagesToVpn");
            EnterpriseResponseData addAllContainerPackagesToVpn = mKnoxVpnPolicyService.addAllContainerPackagesToVpn(this.vpnContext, i, str);
            if (addAllContainerPackagesToVpn == null) {
                Log.e(TAG, "addAllContainerPackagesToVpn > mEnterpriseResponseData == null");
                return -1;
            }
            if (addAllContainerPackagesToVpn.getFailureState() != 11) {
                return ((Integer) addAllContainerPackagesToVpn.getData()).intValue();
            }
            Log.d(TAG, "The container id entered is invalid and throwing an exception");
            throw new IllegalArgumentException();
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API addAllContainerPackagesToVpn-Exception"), TAG);
            return -1;
        }
    }

    public int addAllPackagesToVpn(String str) {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "addAllPackagesToVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.addAllPackagesToVpn");
            EnterpriseResponseData addAllPackagesToVpn = mKnoxVpnPolicyService.addAllPackagesToVpn(this.vpnContext, str);
            if (addAllPackagesToVpn != null) {
                return ((Integer) addAllPackagesToVpn.getData()).intValue();
            }
            Log.e(TAG, "addAllPackagesToVpn > mEnterpriseResponseData == null");
            return -1;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at GenericVpnPolicy API addAllPackagesToVpn:"), TAG);
            return -1;
        }
    }

    public int addContainerPackagesToVpn(int i, String[] strArr, String str) throws IllegalArgumentException {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "addContainerPackageToVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.addContainerPackagesToVpn");
            EnterpriseResponseData addContainerPackagesToVpn = mKnoxVpnPolicyService.addContainerPackagesToVpn(this.vpnContext, i, strArr, str);
            if (addContainerPackagesToVpn == null) {
                Log.e(TAG, "addContainerPackageToVpn > mEnterpriseResponseData == null");
                return -1;
            }
            if (addContainerPackagesToVpn.getFailureState() != 11) {
                return ((Integer) addContainerPackagesToVpn.getData()).intValue();
            }
            Log.d(TAG, "The container id entered is invalid and throwing an exception");
            throw new IllegalArgumentException();
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API addContainerPackageToVpn-Exception"), TAG);
            return -1;
        }
    }

    public int addPackagesToVpn(String[] strArr, String str) {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "addPackageToVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.addPackagesToVpn");
            EnterpriseResponseData addPackagesToVpn = mKnoxVpnPolicyService.addPackagesToVpn(this.vpnContext, strArr, str);
            if (addPackagesToVpn != null) {
                return ((Integer) addPackagesToVpn.getData()).intValue();
            }
            Log.e(TAG, "addPackageToVpn > mEnterpriseResponseData == null");
            return -1;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API addPackagetoDatabase-Exception"), TAG);
            return -1;
        }
    }

    public int allowUsbTetheringOverVpn(String str, boolean z, Bundle bundle) {
        int i;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.allowUsbTetheringOverVpn");
                if (z) {
                    if (bundle != null && !bundle.isEmpty()) {
                        i = !bundle.isEmpty() ? mKnoxVpnPolicyService.allowAuthUsbTetheringOverVpn(this.vpnContext, str, bundle) : 100;
                    }
                    i = mKnoxVpnPolicyService.allowNoAuthUsbTetheringOverVpn(this.vpnContext, str);
                } else {
                    i = mKnoxVpnPolicyService.disallowUsbTetheringOverVpn(this.vpnContext, str);
                }
            } else {
                Log.e(TAG, "KVES not started");
                i = 110;
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at GenericVpnPolicy API allowUsbTetheringOverVpn:"), TAG);
            i = 101;
        }
        if (i == 100) {
            i = VPN_RETURN_INT_SUCCESS;
        }
        if (i != 141) {
            return i;
        }
        throw new SecurityException();
    }

    public int createVpnProfile(String str) {
        int i = VPN_RETURN_INT_ERROR;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.createVpnProfile");
                EnterpriseResponseData createVpnProfile = mKnoxVpnPolicyService.createVpnProfile(this.vpnContext, str);
                if (createVpnProfile != null) {
                    i = ((Integer) createVpnProfile.getData()).intValue();
                } else {
                    Log.e(TAG, "createVpnProfile Error> mEnterpriseResponseData == null");
                }
            } else {
                Log.e(TAG, "createVpnProfile Error > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API createVpnProfile-Exception"), TAG);
        }
        if (i != 141) {
            return i;
        }
        throw new SecurityException();
    }

    public String[] getAllContainerPackagesInVpnProfile(int i, String str) throws IllegalArgumentException {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "getAllContainerPackagesInVpnProfile > mService == null");
                return null;
            }
            EnterpriseResponseData allContainerPackagesInVpnProfile = mKnoxVpnPolicyService.getAllContainerPackagesInVpnProfile(this.vpnContext, i, str);
            if (allContainerPackagesInVpnProfile == null) {
                Log.e(TAG, "getAllContainerPackagesInVpnProfile > mEnterpriseResponseData == null");
                return null;
            }
            if (allContainerPackagesInVpnProfile.getFailureState() == 11) {
                Log.d(TAG, "The container id entered is invalid and throwing an exception");
                throw new IllegalArgumentException();
            }
            if (allContainerPackagesInVpnProfile.getFailureState() == 0) {
                return (String[]) allContainerPackagesInVpnProfile.getData();
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed at EnterpriseContainerManager API getAllContainerPackagesInVpnProfile ", e);
            return null;
        }
    }

    public String[] getAllPackagesInVpnProfile(String str) {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "getAllPackagesInVpnProfile > mService == null");
                return null;
            }
            EnterpriseResponseData allPackagesInVpnProfile = mKnoxVpnPolicyService.getAllPackagesInVpnProfile(this.vpnContext, str);
            if (allPackagesInVpnProfile == null) {
                Log.e(TAG, "getAllPackagesInVpnProfile > mEnterpriseResponseData == null");
                return null;
            }
            if (allPackagesInVpnProfile.getFailureState() == 0) {
                return (String[]) allPackagesInVpnProfile.getData();
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed at EnterpriseContainerManager API getAllPackagesInVpnProfile ", e);
            return null;
        }
    }

    public List<String> getAllVpnProfiles() {
        try {
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API getAllVpnProfiles-Exception"), TAG);
        }
        if (getKnoxVpnPolicyService() == null) {
            Log.e(TAG, "getAllVpnProfiles > mService == null");
            return null;
        }
        EnterpriseResponseData allVpnProfiles = mKnoxVpnPolicyService.getAllVpnProfiles(this.vpnContext);
        if (allVpnProfiles == null) {
            Log.e(TAG, "getAllVpnProfiles > mEnterpriseResponseData == null");
            return null;
        }
        if (allVpnProfiles.getFailureState() == 0) {
            return (List) allVpnProfiles.getData();
        }
        return null;
    }

    public CertificateInfo getCACertificate(String str) {
        try {
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API getCACertificate-Exception"), TAG);
        }
        if (getKnoxVpnPolicyService() == null) {
            Log.e(TAG, "getCACertificate > mService == null");
            return null;
        }
        EnterpriseResponseData cACertificate = mKnoxVpnPolicyService.getCACertificate(this.vpnContext, str);
        if (cACertificate == null) {
            Log.e(TAG, "getCACertificate > mEnterpriseResponseData == null");
            return null;
        }
        if (cACertificate.getFailureState() == 0) {
            return (CertificateInfo) cACertificate.getData();
        }
        return null;
    }

    public String getErrorString(String str) {
        try {
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API getErrorString-Exception"), TAG);
        }
        if (getKnoxVpnPolicyService() == null) {
            Log.e(TAG, "getErrorString > mService == null");
            return null;
        }
        EnterpriseResponseData errorString = mKnoxVpnPolicyService.getErrorString(this.vpnContext, str);
        if (errorString == null) {
            Log.e(TAG, "getErrorString > mEnterpriseResponseData == null");
            return null;
        }
        if (errorString.getStatus() != 0) {
            if (errorString.getStatus() == 2) {
            }
            return null;
        }
        return (String) errorString.getData();
    }

    public int getNotificationDismissibleFlag(int i) {
        try {
            if (getKnoxVpnPolicyService() != null) {
                return mKnoxVpnPolicyService.getNotificationDismissibleFlag(this.vpnContext, i);
            }
            Log.e(TAG, "getNotificationDismissibleFlag > mService == null");
            return 1;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API getNotificationDismissibleFlag-Exception"), TAG);
            return 1;
        }
    }

    public int getState(String str) {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "getState >> mService == null");
                return -1;
            }
            EnterpriseResponseData state = mKnoxVpnPolicyService.getState(this.vpnContext, str);
            if (state != null) {
                return ((Integer) state.getData()).intValue();
            }
            Log.e(TAG, "getState >> mEnterpriseResponseData == null");
            return -1;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API getState-Exception"), TAG);
            return -1;
        }
    }

    public CertificateInfo getUserCertificate(String str) {
        try {
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API getUserCertificate-Exception"), TAG);
        }
        if (getKnoxVpnPolicyService() == null) {
            Log.e(TAG, "getUserCertificate > mService == null");
            return null;
        }
        EnterpriseResponseData userCertificate = mKnoxVpnPolicyService.getUserCertificate(this.vpnContext, str);
        if (userCertificate == null) {
            Log.e(TAG, "getUserCertificate > mEnterpriseResponseData == null");
            return null;
        }
        if (userCertificate.getFailureState() == 0) {
            return (CertificateInfo) userCertificate.getData();
        }
        return null;
    }

    public int getVpnModeOfOperation(String str) {
        try {
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API getVpnModeOfOperation-Exception"), TAG);
        }
        if (getKnoxVpnPolicyService() == null) {
            Log.e(TAG, "getVpnModeOfOperation > mService == null");
            return -1;
        }
        EnterpriseResponseData vpnModeOfOperation = mKnoxVpnPolicyService.getVpnModeOfOperation(this.vpnContext, str);
        if (vpnModeOfOperation == null) {
            Log.e(TAG, "getVpnModeOfOperation > mEnterpriseResponseData == null");
            return -1;
        }
        if (vpnModeOfOperation.getFailureState() == 0) {
            return ((Integer) vpnModeOfOperation.getData()).intValue();
        }
        return -1;
    }

    public String getVpnProfile(String str) {
        try {
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API getVpnProfile-Exception"), TAG);
        }
        if (getKnoxVpnPolicyService() == null) {
            Log.e(TAG, "getVpnProfile Error > mService == null");
            return null;
        }
        EnterpriseResponseData vpnProfile = mKnoxVpnPolicyService.getVpnProfile(this.vpnContext, str);
        if (vpnProfile == null) {
            Log.e(TAG, "getVpnProfile Error> mEnterpriseResponseData == null");
            return null;
        }
        if (vpnProfile.getFailureState() == 0) {
            return (String) vpnProfile.getData();
        }
        return null;
    }

    public int isUsbTetheringOverVpnEnabled(String str) {
        int i;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.isUsbTetheringOverVpnEnabled");
                i = mKnoxVpnPolicyService.isUsbTetheringOverVpnEnabled(this.vpnContext, str);
            } else {
                Log.e(TAG, "KVES not started");
                i = 110;
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at GenericVpnPolicy API isUsbTetheringOverVpnEnabled:"), TAG);
            i = 101;
        }
        if (i == 100) {
            i = VPN_RETURN_INT_SUCCESS;
        }
        if (i != 141) {
            return i;
        }
        throw new SecurityException();
    }

    public int removeAllContainerPackagesFromVpn(int i, String str) throws IllegalArgumentException {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "removeAllContainerPackagesFromVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.removeAllContainerPackagesFromVpn");
            EnterpriseResponseData removeAllContainerPackagesFromVpn = mKnoxVpnPolicyService.removeAllContainerPackagesFromVpn(this.vpnContext, i, str);
            if (removeAllContainerPackagesFromVpn == null) {
                Log.e(TAG, "removeAllContainerPackagesFromVpn > mEnterpriseResponseData == null");
                return -1;
            }
            if (removeAllContainerPackagesFromVpn.getFailureState() != 11) {
                return ((Integer) removeAllContainerPackagesFromVpn.getData()).intValue();
            }
            Log.d(TAG, "The container id entered is invalid and throwing an exception");
            throw new IllegalArgumentException();
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at GenericVpnPolicy API removeAllContainerPackagesFromVpn:"), TAG);
            return -1;
        }
    }

    public int removeAllPackagesFromVpn(String str) {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "removeAllPackagesFromVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.removeAllPackagesFromVpn");
            EnterpriseResponseData removeAllPackagesFromVpn = mKnoxVpnPolicyService.removeAllPackagesFromVpn(this.vpnContext, str);
            if (removeAllPackagesFromVpn != null) {
                return ((Integer) removeAllPackagesFromVpn.getData()).intValue();
            }
            Log.e(TAG, "removeAllPackagesFromVpn > mEnterpriseResponseData == null");
            return -1;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at GenericVpnPolicy API removeAllPackagesFromVpn:"), TAG);
            return -1;
        }
    }

    public int removeContainerPackagesFromVpn(int i, String[] strArr, String str) throws IllegalArgumentException {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "removeContainerPackageFromVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.removeContainerPackagesFromVpn");
            EnterpriseResponseData removeContainerPackagesFromVpn = mKnoxVpnPolicyService.removeContainerPackagesFromVpn(this.vpnContext, i, strArr, str);
            if (removeContainerPackagesFromVpn == null) {
                Log.e(TAG, "removeContainerPackageFromVpn > mEnterpriseResponseData == null");
                return -1;
            }
            if (removeContainerPackagesFromVpn.getFailureState() != 11) {
                return ((Integer) removeContainerPackagesFromVpn.getData()).intValue();
            }
            Log.d(TAG, "The container id entered is invalid and throwing an exception");
            throw new IllegalArgumentException();
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at GenericVpnPolicy API removeContainerPackageFromVpn:"), TAG);
            return -1;
        }
    }

    public int removePackagesFromVpn(String[] strArr, String str) {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "removePackageFromVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.removePackagesFromVpn");
            EnterpriseResponseData removePackagesFromVpn = mKnoxVpnPolicyService.removePackagesFromVpn(this.vpnContext, strArr, str);
            if (removePackagesFromVpn != null) {
                return ((Integer) removePackagesFromVpn.getData()).intValue();
            }
            Log.e(TAG, "removePackageFromVpn > mEnterpriseResponseData == null");
            return -1;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at GenericVpnPolicy API removePackageFromVpn:"), TAG);
            return -1;
        }
    }

    public int removeVpnProfile(String str) {
        int i = VPN_RETURN_INT_ERROR;
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "removeVpnProfile  Error > mService == null");
                return i;
            }
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.removeVpnProfile");
            EnterpriseResponseData removeVpnProfile = mKnoxVpnPolicyService.removeVpnProfile(this.vpnContext, str);
            if (removeVpnProfile != null) {
                return ((Integer) removeVpnProfile.getData()).intValue();
            }
            Log.e(TAG, "removeVpnProfile  Error> mEnterpriseResponseData == null");
            return i;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API removeVpnProfile -Exception"), TAG);
            return i;
        }
    }

    public boolean setAutoRetryOnConnectionError(String str, boolean z) {
        try {
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API setAutoRetryOnConnectionError-Exception"), TAG);
        }
        if (getKnoxVpnPolicyService() == null) {
            Log.e(TAG, "setAutoRetryOnConnection Error > mService == null");
            return false;
        }
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.setAutoRetryOnConnectionError");
        EnterpriseResponseData autoRetryOnConnectionError = mKnoxVpnPolicyService.setAutoRetryOnConnectionError(this.vpnContext, str, z);
        if (autoRetryOnConnectionError == null) {
            Log.e(TAG, "setAutoRetryOnConnection Error > mEnterpriseResponseData == null");
            return false;
        }
        if (autoRetryOnConnectionError.getFailureState() == 0) {
            return ((Boolean) autoRetryOnConnectionError.getData()).booleanValue();
        }
        return false;
    }

    public boolean setCACertificate(String str, byte[] bArr) {
        try {
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API setCACertificate-Exception"), TAG);
        }
        if (getKnoxVpnPolicyService() == null) {
            Log.e(TAG, "setCACertificate > mService == null");
            return false;
        }
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.setCACertificate");
        EnterpriseResponseData cACertificate = mKnoxVpnPolicyService.setCACertificate(this.vpnContext, str, bArr);
        if (cACertificate == null) {
            Log.e(TAG, "setCACertificate > mEnterpriseResponseData == null");
            return false;
        }
        if (cACertificate.getFailureState() == 0) {
            return ((Boolean) cACertificate.getData()).booleanValue();
        }
        return false;
    }

    public int setNotificationDismissibleFlag(String str, int i, int i2) {
        try {
            if (getKnoxVpnPolicyService() != null) {
                return mKnoxVpnPolicyService.setNotificationDismissibleFlag(this.vpnContext, str, i, i2);
            }
            Log.e(TAG, "setNotificationDismissibleFlag > mService == null");
            return -1;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API setNotificationDismissibleFlag-Exception"), TAG);
            return -1;
        }
    }

    public boolean setServerCertValidationUserAcceptanceCriteria(String str, boolean z, List<Integer> list, int i) {
        try {
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API setServerCertValidationUserAcceptanceCriteria-Exception"), TAG);
        }
        if (getKnoxVpnPolicyService() == null) {
            Log.e(TAG, "setServerCertValidationUserAcceptanceCriteria Error > mService == null");
            return false;
        }
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.setServerCertValidationUserAcceptanceCriteria");
        EnterpriseResponseData serverCertValidationUserAcceptanceCriteria = mKnoxVpnPolicyService.setServerCertValidationUserAcceptanceCriteria(this.vpnContext, str, z, list, i);
        if (serverCertValidationUserAcceptanceCriteria == null) {
            Log.e(TAG, "setServerCertValidationUserAcceptanceCriteria Error > mEnterpriseResponseData == null");
            return false;
        }
        if (serverCertValidationUserAcceptanceCriteria.getFailureState() == 0) {
            return ((Boolean) serverCertValidationUserAcceptanceCriteria.getData()).booleanValue();
        }
        return false;
    }

    public boolean setUserCertificate(String str, byte[] bArr, String str2) {
        try {
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API setUserCertificate-Exception"), TAG);
        }
        if (getKnoxVpnPolicyService() == null) {
            Log.e(TAG, "setUserCertificate > mService == null");
            return false;
        }
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.setUserCertificate");
        EnterpriseResponseData userCertificate = mKnoxVpnPolicyService.setUserCertificate(this.vpnContext, str, bArr, str2);
        if (userCertificate == null) {
            Log.e(TAG, "setUserCertificate > mEnterpriseResponseData == null");
            return false;
        }
        if (userCertificate.getFailureState() == 0) {
            return ((Boolean) userCertificate.getData()).booleanValue();
        }
        return false;
    }

    public int setVpnModeOfOperation(String str, int i) {
        try {
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at GenericVpnPolicy API setVpnModeOfOperation-Exception"), TAG);
        }
        if (getKnoxVpnPolicyService() == null) {
            Log.e(TAG, "setVpnModeOfOperation > mService == null");
            return -1;
        }
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnPolicy.setVpnModeOfOperation");
        EnterpriseResponseData vpnModeOfOperation = mKnoxVpnPolicyService.setVpnModeOfOperation(this.vpnContext, str, i);
        if (vpnModeOfOperation == null) {
            Log.e(TAG, "setVpnModeOfOperation > mEnterpriseResponseData == null");
            return -1;
        }
        if (vpnModeOfOperation.getFailureState() == 0) {
            return ((Integer) vpnModeOfOperation.getData()).intValue();
        }
        return -1;
    }

    private GenericVpnPolicy(Context context, KnoxVpnContext knoxVpnContext) {
        this.vpnContext = null;
        this.vendorName = null;
        mContext = context;
        if (DBG) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("GenericVpnPolicy ctor : vendorName = "), knoxVpnContext.vendorName, TAG);
        }
        this.vpnContext = knoxVpnContext;
    }
}
