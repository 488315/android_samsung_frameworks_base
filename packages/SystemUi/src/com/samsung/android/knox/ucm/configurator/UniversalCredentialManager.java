package com.samsung.android.knox.ucm.configurator;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.multiuser.MultiUserManager$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class UniversalCredentialManager {
    public static final String ACTION_UCM_CONFIG_STATUS = "com.samsung.android.knox.intent.action.UCM_CONFIG_STATUS";
    public static final String ACTION_UCM_KEYGUARD_SET = "com.samsung.android.knox.intent.action.UCM_KEYGUARD_SET";
    public static final String ACTION_UCM_KEYGUARD_UNSET = "com.samsung.android.knox.intent.action.UCM_KEYGUARD_UNSET";
    public static final String ACTION_UCM_NOTIFY_EVENT = "com.samsung.android.knox.intent.action.UCM_NOTIFY_EVENT";
    public static final String ACTION_UCM_REFRESH_AGENT_DONE = "com.samsung.android.knox.intent.action.UCM_REFRESH_AGENT_DONE";
    public static final String APPLET_FORM_FACTOR_ESE = "eSE";
    public static final String APPLET_FORM_FACTOR_ESE1 = "eSE1";
    public static final String APPLET_FORM_FACTOR_SD = "SD";
    public static final String APPLET_FORM_FACTOR_SD1 = "SD1";
    public static final String APPLET_FORM_FACTOR_SIM = "SIM";
    public static final String APPLET_FORM_FACTOR_SIM1 = "SIM1";
    public static final String APPLET_FORM_FACTOR_SIM2 = "SIM2";
    public static final String BUNDLE_CA_CERT_TYPE = "cert_type";
    public static final String BUNDLE_EXTRA_ACCESS_TYPE = "access_type";
    public static final String BUNDLE_EXTRA_ADD_PIN_CACHE_EXEMPTLIST = "add_pin_cache_exemptlist";
    public static final String BUNDLE_EXTRA_ALIAS = "alias";
    public static final String BUNDLE_EXTRA_ALLOW_WIFI = "allow_wifi";
    public static final String BUNDLE_EXTRA_AUTH_TYPE = "auth_type";
    public static final String BUNDLE_EXTRA_ENFORCE_LOCK_TYPE_DIRECT_SET = "enforce_lock_type_direct_set";
    public static final String BUNDLE_EXTRA_ESE_STORAGE_OPTION = "ese_storage_option";
    public static final String BUNDLE_EXTRA_EVENT_ID = "event_id";
    public static final String BUNDLE_EXTRA_ODE_CA_CERT = "ode_ca_cert";
    public static final String BUNDLE_EXTRA_PACKAGE = "package_name";
    public static final String BUNDLE_EXTRA_PIN_CACHE = "pin_cache";
    public static final String BUNDLE_EXTRA_PIN_CACHE_TIMEOUT_MINUTES = "timeout";
    public static final String BUNDLE_EXTRA_REMOVE_PIN_CACHE_EXEMPTLIST = "remove_pin_cache_exemptlist";
    public static final String BUNDLE_EXTRA_REQUEST_ID = "request_id";
    public static final String BUNDLE_EXTRA_STATUS_CODE = "status_code";
    public static final String BUNDLE_EXTRA_USER_ID = "userId";
    public static final int ERROR_UCM_ALIAS_ALREADY_EXIST = -20;
    public static final int ERROR_UCM_ALIAS_EMPTY = -16;
    public static final int ERROR_UCM_ALIAS_NOT_EXIST = -14;
    public static final int ERROR_UCM_APP_SIGNATURE_INVALID = -18;
    public static final int ERROR_UCM_CALLER_NOT_ALLOWED_TO_MANAGE_STORAGE = -23;
    public static final int ERROR_UCM_CALLER_NOT_CONTAINER_OWNER = -24;
    public static final int ERROR_UCM_FAILURE = -1;
    public static final int ERROR_UCM_INSTALL_DELEGATION_NOT_ALLOWED = -29;
    public static final int ERROR_UCM_INVALID_ACCESS_TYPE = -15;
    public static final int ERROR_UCM_INVALID_AUTH_TYPE = -17;
    public static final int ERROR_UCM_INVALID_CERTIFICATE = -25;
    public static final int ERROR_UCM_INVALID_EXEMPT_TYPE = -21;
    public static final int ERROR_UCM_INVALID_STORAGE_OPTION = -19;
    public static final int ERROR_UCM_KEYGUARD_CONFIGURED = -26;
    public static final int ERROR_UCM_MISSING_ARGUMENT = -11;
    public static final int ERROR_UCM_PASSWORD_QUALITY_NOT_UNSPECIFIED = -28;
    public static final int ERROR_UCM_PASSWORD_UNSUPPORTED_STORAGE = -27;
    public static final int ERROR_UCM_STORAGE_ALREADY_CONFIGURED = -10;
    public static final int ERROR_UCM_STORAGE_DELEGATION_NOT_ALLOWED = -30;
    public static final int ERROR_UCM_STORAGE_NOT_ENABLED = -12;
    public static final int ERROR_UCM_STORAGE_NOT_MANAGEABLE = -22;
    public static final int ERROR_UCM_STORAGE_NOT_VALID = -13;
    public static int EVENT_PLUGIN_APPLET_DELETE_COMPLETED = 1011;
    public static int EVENT_PLUGIN_APPLET_DELETE_FAILED = 1012;
    public static int EVENT_PLUGIN_APPLET_INSTALL_COMPLETED = 1001;
    public static int EVENT_PLUGIN_APPLET_INSTALL_FAILED = 1002;
    public static int EVENT_PLUGIN_APPLET_UPDATE_COMPLETED = 1021;
    public static int EVENT_PLUGIN_APPLET_UPDATE_FAILED = 1022;
    public static final int EVENT_PLUGIN_LICENSE_EXPIRED = 2;
    public static final int EVENT_PLUGIN_UNINSTALLED = 1;
    public static String KEY_PLUGIN_EVENT = "key_plugin_event";
    public static final int PIN_CACHE_KEYGUARD_TIMEOUT = 2;
    public static final int PIN_CACHE_TIMEOUT = 1;
    public static final String RESET_APPLET_FORM_FACTOR = "reset";
    public static final String SCP_SD = "SCP_SD";
    public static String TAG = "UniversalCredentialManager";
    public static final int UCM_ACCESS_TYPE_CERTIFICATE = 104;
    public static final int UCM_ACCESS_TYPE_INSTALL = 107;
    public static final int UCM_ACCESS_TYPE_STORAGE = 103;
    public static final String UCM_APPLET_DELETE = "DELETE_APPLET";
    public static final String UCM_APPLET_ID = "applet_id";
    public static final String UCM_APPLET_INSTALL_LOCATION = "applet_location";
    public static final String UCM_APPLET_UPGRADE = "UPGRADE_APPLET";
    public static final int UCM_AUTH_TYPE_LOCKED = 100;
    public static final int UCM_AUTH_TYPE_NONE = 105;
    public static final int UCM_EXEMPT_TYPE_AUTH = 106;
    public static final int UCM_STORAGE_OPTION_INSIDE = 101;
    public static final int UCM_STORAGE_OPTION_OUTSIDE = 102;
    public static final int UCM_SUCCESS = 0;
    public static final int UCM_SUCCESS_KEYGUARD_ALREADY_CONFIGURED = 10;
    public static final String WIFI_VIRTUAL_PACKAGE = "com.samsung.knox.virtual.wifi";
    public static IUniversalCredentialManager mUCMService;
    public ContextInfo mContextInfo;

    private UniversalCredentialManager(ContextInfo contextInfo, Context context) {
        this.mContextInfo = contextInfo;
        Log.w(TAG, "UniversalCredentialStorageManager API [" + this.mContextInfo.mContainerId + "," + this.mContextInfo.mCallerUid + "] ");
    }

    public static synchronized UniversalCredentialManager getUCMManager(Context context) {
        synchronized (UniversalCredentialManager.class) {
            if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
                Log.i(TAG, "getUCMManager : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
                return null;
            }
            int userId = UserHandle.getUserId(Process.myUid());
            if (isValidUser(context, userId)) {
                return new UniversalCredentialManager(new ContextInfo(Process.myUid()), context);
            }
            Log.i(TAG, "getUCMManager : Invalid user request userId-" + userId);
            return null;
        }
    }

    public static synchronized IUniversalCredentialManager getUCMService() {
        IUniversalCredentialManager iUniversalCredentialManager;
        synchronized (UniversalCredentialManager.class) {
            if (mUCMService == null) {
                mUCMService = IUniversalCredentialManager.Stub.asInterface(ServiceManager.getService("knox_ucsm_policy"));
            }
            iUniversalCredentialManager = mUCMService;
        }
        return iUniversalCredentialManager;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001a, code lost:
    
        if (r3.exists(r4) != false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isValidUser(Context context, int i) {
        boolean z;
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("isValidUser userId-", i, TAG);
        boolean z2 = true;
        if (i != 0) {
            z = false;
            try {
                SemPersonaManager semPersonaManager = (SemPersonaManager) context.getSystemService("persona");
                if (semPersonaManager != null) {
                }
                z2 = false;
            } catch (Exception e) {
                Log.i(TAG, "The exception occurs " + e.getMessage());
            }
        }
        z = z2;
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("isValidUser status-", z, TAG);
        return z;
    }

    public final int addPackagesToExemptList(CredentialStorage credentialStorage, int i, List<AppIdentity> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.addPackagesToExemptList");
        Log.i(TAG, "UniversalCredentialManager.addPackagesToExemptList is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "addPackagesToExemptList : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.addPackagesToExemptList(this.mContextInfo, credentialStorage, i, list);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.addPackagesToExemptList getUCMService is null....");
        return -1;
    }

    public final int addPackagesToWhiteList(CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.addPackagesToWhiteList");
        Log.i(TAG, "UniversalCredentialManager.addPackagesToWhiteList is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "addPackagesToWhiteList : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.addPackagesToWhiteList(this.mContextInfo, credentialStorage, list, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.addPackagesToWhiteList getUCMService is null....");
        return -1;
    }

    public final int addPackagesToWhiteListInternal(int i, int i2, CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.addPackagesToWhiteListInternal");
        Log.i(TAG, "UniversalCredentialManager.addPackagesToWhiteListInternal is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "addPackagesToWhiteListInternal : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.addPackagesToWhiteListInternal(i, i2, credentialStorage, list, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.addPackagesToWhiteListInternal getUCMService is null....");
        return -1;
    }

    public final int changeKeyguardPin(CredentialStorage credentialStorage, String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.changeKeyguardPin");
        Log.i(TAG, "UniversalCredentialManager.changeKeyguardPin is called....");
        if (isNotSupportKnoxSdk(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_9)) {
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.changeKeyguardPin(this.mContextInfo, credentialStorage, str, str2);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.changeKeyguardPin getUCMService is null....");
        return -1;
    }

    public final int clearWhiteList(CredentialStorage credentialStorage, Bundle bundle) {
        Log.i(TAG, "UniversalCredentialManager.clearWhiteList is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "clearWhiteList : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.clearWhiteList(this.mContextInfo, credentialStorage, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.clearWhiteList getUCMService is null....");
        return -1;
    }

    public final int configureCredentialStorageForODESettings(CredentialStorage credentialStorage, Bundle bundle) {
        Log.i(TAG, "UniversalCredentialManager.configureCredentialStorageForODESettings is called....");
        try {
            if (getUCMService() != null) {
                return mUCMService.configureCredentialStorageForODESettings(this.mContextInfo, credentialStorage, bundle);
            }
            Log.i(TAG, "UniversalCredentialStorageManager.configureCredentialStorageForODESettings getUCMService is null....");
            return -1;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
            return -1;
        }
    }

    public final int configureCredentialStoragePlugin(CredentialStorage credentialStorage, Bundle bundle) {
        Log.i(TAG, "UniversalCredentialManager.configureCredentialStoragePlugin is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "configureCredentialStoragePlugin : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.configureCredentialStoragePlugin(this.mContextInfo, credentialStorage, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.configureCredentialStoragePlugin getUCMService is null....");
        return -1;
    }

    public final int deleteCACertificate(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.deleteCACertificate");
        Log.i(TAG, "UniversalCredentialManager.deleteCACertificate is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "deleteCACertificate : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.deleteCACertificate(this.mContextInfo, str);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.deleteCACertificate getUCMService is null....");
        return -1;
    }

    public final int deleteCertificate(CredentialStorage credentialStorage, String str) {
        Log.i(TAG, "UniversalCredentialManager.deleteCertificate is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "deleteCertificate : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.deleteCertificate(this.mContextInfo, credentialStorage, str);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.deleteCertificate getUCMService is null....");
        return -1;
    }

    public final int deleteCertificateInternal(int i, int i2, CredentialStorage credentialStorage, String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.deleteCertificateInternal");
        Log.i(TAG, "UniversalCredentialManager.deleteCertificateInternal is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "deleteCertificateInternal : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.deleteCertificateInternal(i, i2, credentialStorage, str);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.deleteCertificateInternal getUCMService is null....");
        return -1;
    }

    public final int enableCredentialStorageForLockType(CredentialStorage credentialStorage, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.enableCredentialStorageForLockType");
        Log.i(TAG, "UniversalCredentialManager.enableCredentialStorageForLockType is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7) < 0) {
            Log.i(TAG, "enableCredentialStorageForLockType : support above KNOX_ENTERPRISE_SDK_VERSION_2_7");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.enableCredentialStorageForLockType(this.mContextInfo, credentialStorage, z);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.enableCredentialStorageForLockType getUCMService is null....");
        return -1;
    }

    public final int enforceCredentialStorageAsLockType(CredentialStorage credentialStorage) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.enforceCredentialStorageAsLockType(CredentialStorage)");
        Bundle bundle = new Bundle();
        Log.i(TAG, "UniversalCredentialManager.enforceCredentialStorageAsLockType(CredentialStorage) is called....");
        return enforceCredentialStorageAsLockType(credentialStorage, bundle);
    }

    public final String[] getAliases(CredentialStorage credentialStorage) {
        Log.i(TAG, "UniversalCredentialManager.getAliases is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "getAliases : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getAliases(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getAliases getUCMService is null....");
        return null;
    }

    public final int getAuthType(CredentialStorage credentialStorage) {
        Log.i(TAG, "UniversalCredentialManager.getAuthType is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "getAuthType : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return 105;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getAuthType(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getStorageOption getAuthType is null....");
        return 105;
    }

    public final CredentialStorage[] getAvailableCredentialStorages() {
        Log.i(TAG, "UniversalCredentialManager.getAvailableCredentialStorages is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "getAvailableCredentialStorages : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getAvailableCredentialStorages(this.mContextInfo);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getAvailableCredentialStorages getUCMService is null....");
        return null;
    }

    public final CACertificateInfo getCACertificate(String str) {
        Log.i(TAG, "UniversalCredentialManager.getCACertificate is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "getCACertificate : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getCACertificate(this.mContextInfo, str);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getCACertificate getUCMService is null....");
        return null;
    }

    public final String[] getCACertificateAliases(Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.getCACertificateAliases");
        Log.i(TAG, "UniversalCredentialManager.getCACertificateAliases is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "getCACertificateAliases : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getCACertificateAliases(this.mContextInfo, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getCACertificateAliases getUCMService is null....");
        return null;
    }

    public final Bundle getCredentialStoragePluginConfiguration(CredentialStorage credentialStorage) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.getCredentialStoragePluginConfiguration", true);
        Log.i(TAG, "UniversalCredentialManager.getCredentialStoragePluginConfiguration is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "getCredentialStoragePluginConfiguration : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getCredentialStoragePluginConfiguration(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getCredentialStoragePluginConfiguration getUCMService is null....");
        return null;
    }

    public final Bundle getCredentialStorageProperty(CredentialStorage credentialStorage, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.getCredentialStorageProperty", true);
        Log.i(TAG, "UniversalCredentialManager.getCredentialStorageProperty is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7) < 0) {
            Log.i(TAG, "getPackageSetting : support above KNOX_ENTERPRISE_SDK_VERSION_2_7");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getCredentialStorageProperty(this.mContextInfo, credentialStorage, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getStorageOption getCredentialStorageProperty is null....");
        return null;
    }

    public final CredentialStorage[] getCredentialStorages(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.getCredentialStorages");
        Log.i(TAG, "UniversalCredentialManager.getCredentialStorages is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "getCredentialStorages : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getCredentialStorages(this.mContextInfo, str);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getCredentialStorages getUCMService");
        return null;
    }

    public final CredentialStorage getDefaultInstallStorage() {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.getDefaultInstallStorage");
        Log.i(TAG, "UniversalCredentialManager.getDefaultInstallStorage is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "getDefaultInstallStorage : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getDefaultInstallStorage(this.mContextInfo);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getDefaultInstallStorage getUCMService is null....");
        return null;
    }

    public final CredentialStorage getEnforcedCredentialStorageForLockType() {
        Log.i(TAG, "UniversalCredentialManager.getEnforcedCredentialStorageForLockType is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7) < 0) {
            Log.i(TAG, "getEnforcedCredentialStorageForLockType : support above KNOX_ENTERPRISE_SDK_VERSION_2_7");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getEnforcedCredentialStorageForLockType(this.mContextInfo);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getEnforcedCredentialStorageForLockType getUCMService is null....");
        return null;
    }

    public final int getKeyguardPinCurrentRetryCount(CredentialStorage credentialStorage) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.getKeyguardPinCurrentRetryCount");
        Log.i(TAG, "UniversalCredentialManager.getKeyguardPinCurrentRetryCount is called....");
        if (isNotSupportKnoxSdk(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_9)) {
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getKeyguardPinCurrentRetryCount(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getKeyguardPinCurrentRetryCount getUCMService is null....");
        return -1;
    }

    public final int getKeyguardPinMaximumLength(CredentialStorage credentialStorage) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.getKeyguardPinMaximumLength");
        Log.i(TAG, "UniversalCredentialManager.getKeyguardPinMaximumLength is called....");
        if (isNotSupportKnoxSdk(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_9)) {
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getKeyguardPinMaximumLength(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getKeyguardPinMaximumLength getUCMService is null....");
        return -1;
    }

    public final int getKeyguardPinMaximumRetryCount(CredentialStorage credentialStorage) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.getKeyguardPinMaximumRetryCount");
        Log.i(TAG, "UniversalCredentialManager.getKeyguardPinMaximumRetryCount is called....");
        if (isNotSupportKnoxSdk(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_9)) {
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getKeyguardPinMaximumRetryCount(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getKeyguardPinMaximumRetryCount getUCMService is null....");
        return -1;
    }

    public final int getKeyguardPinMinimumLength(CredentialStorage credentialStorage) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.getKeyguardPinMinimumLength");
        Log.i(TAG, "UniversalCredentialManager.getKeyguardPinMinimumLength is called....");
        if (isNotSupportKnoxSdk(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_9)) {
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getKeyguardPinMinimumLength(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getKeyguardPinMinimumLength getUCMService is null....");
        return -1;
    }

    public final Bundle getODESettingsConfiguration() {
        Log.i(TAG, "IUniversalCredentialManager.getODESettingsConfiguration is called....");
        try {
            if (getUCMService() != null) {
                return mUCMService.getODESettingsConfiguration(this.mContextInfo);
            }
            Log.i(TAG, "UniversalCredentialManager.getODESettingsConfiguration getUCMService is null....");
            return null;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
            return null;
        }
    }

    public final List<AppIdentity> getPackagesFromExemptList(CredentialStorage credentialStorage, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.getPackagesFromExemptList", true);
        Log.i(TAG, "UniversalCredentialManager.getPackagesFromExemptList is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "getPackagesFromExemptList : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getPackagesFromExemptList(this.mContextInfo, credentialStorage, i);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getPackagesFromExemptList getUCMService is null....");
        return null;
    }

    public final List<AppIdentity> getPackagesFromWhiteList(CredentialStorage credentialStorage, Bundle bundle) {
        Log.i(TAG, "UniversalCredentialManager.getPackagesFromWhiteList is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "getPackagesFromWhiteList : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getPackagesFromWhiteList(this.mContextInfo, credentialStorage, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getPackagesFromWhiteList getUCMService is null....");
        return null;
    }

    public final String[] getSupportedAlgorithms(CredentialStorage credentialStorage) {
        Log.i(TAG, "UniversalCredentialManager.getSupportedAlgorithms is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "getSupportedAlgorithms : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.getSupportedAlgorithms(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getSupportedAlgorithms getUCMService is null....");
        return null;
    }

    public final int initKeyguardPin(CredentialStorage credentialStorage, String str, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.initKeyguardPin");
        Log.i(TAG, "UniversalCredentialManager.initKeyguardPin is called....");
        if (isNotSupportKnoxSdk(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_9)) {
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.initKeyguardPin(this.mContextInfo, credentialStorage, str, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.initKeyguardPin getUCMService is null....");
        return -1;
    }

    public final int installCACertificate(byte[] bArr, String str, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.installCACertificate");
        Log.i(TAG, "UniversalCredentialManager.installCACertificate is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "installCACertificate : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.installCACertificate(this.mContextInfo, bArr, str, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.installCACertificate getUCMService is null....");
        return -1;
    }

    public final int installCertificate(CredentialStorage credentialStorage, byte[] bArr, String str, String str2, Bundle bundle) {
        Log.i(TAG, "UniversalCredentialManager.installCertificate is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "installCertificate : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.installCertificate(this.mContextInfo, credentialStorage, bArr, str, str2, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.installCertificate getUCMService is null....");
        return -1;
    }

    public final int installCertificateInternal(int i, int i2, CredentialStorage credentialStorage, byte[] bArr, String str, Bundle bundle, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.installCertificateInternal");
        Log.i(TAG, "UniversalCredentialManager.installCertificateInternal is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "installCertificateInternal : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.installCertificateInternal(i, i2, credentialStorage, bArr, str, bundle, z);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.installCertificateInternal getUCMService is null....");
        return -1;
    }

    public final boolean isCredentialStorageEnabledForLockType(CredentialStorage credentialStorage) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.isCredentialStorageEnabledForLockType", true);
        Log.i(TAG, "UniversalCredentialManager.isCredentialStorageEnabledForLockType is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7) < 0) {
            Log.i(TAG, "isCredentialStorageEnabledForLockType : support above KNOX_ENTERPRISE_SDK_VERSION_2_7");
            return false;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.isCredentialStorageEnabledForLockType(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.isCredentialStorageEnabledForLockType getUCMService is null....");
        return false;
    }

    public final boolean isCredentialStorageLocked(CredentialStorage credentialStorage) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.isCredentialStorageLocked");
        Log.i(TAG, "UniversalCredentialManager.isCredentialStorageLocked is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "isCredentialStorageLocked : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return false;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.isCredentialStorageLocked(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.getStorageOption isCredentialStorageLocked is null....");
        return false;
    }

    public final boolean isCredentialStorageManaged(CredentialStorage credentialStorage) {
        Log.i(TAG, "UniversalCredentialManager.isCredentialStorageManaged is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "isCredentialStorageManaged : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return false;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.isCredentialStorageManaged(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.isCredentialStorageManaged getUCMService is null....");
        return false;
    }

    public final boolean isNotSupportKnoxSdk(EdmConstants.EnterpriseKnoxSdkVersion enterpriseKnoxSdkVersion) {
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(enterpriseKnoxSdkVersion) >= 0) {
            return false;
        }
        Log.i(TAG, "checkSdkVersion : support above " + enterpriseKnoxSdkVersion);
        return true;
    }

    public final int lockCredentialStorage(CredentialStorage credentialStorage, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.lockCredentialStorage");
        Log.i(TAG, "UniversalCredentialManager.lockCredentialStorage is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "lockCredentialStorage : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.lockCredentialStorage(this.mContextInfo, credentialStorage, z);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.lockCredentialStorage getUCMService is null....");
        return -1;
    }

    public final int manageCredentialStorage(CredentialStorage credentialStorage, boolean z) {
        Log.i(TAG, "UniversalCredentialManager.manageCredentialStorage is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "manageCredentialStorage : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.manageCredentialStorage(this.mContextInfo, credentialStorage, z);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.manageCredentialStorage getUCMService is null....");
        return -1;
    }

    public final int removePackagesFromExemptList(CredentialStorage credentialStorage, int i, List<AppIdentity> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.removePackagesFromExemptList");
        Log.i(TAG, "UniversalCredentialManager.removePackagesFromExemptList is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "removePackagesFromExemptList : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.removePackagesFromExemptList(this.mContextInfo, credentialStorage, i, list);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.removePackagesFromExemptList getUCMService is null....");
        return -1;
    }

    public final int removePackagesFromWhiteList(CredentialStorage credentialStorage, List<AppIdentity> list, Bundle bundle) {
        Log.i(TAG, "UniversalCredentialManager.removePackagesFromWhiteList is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "removePackagesFromWhiteList : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.removePackagesFromWhiteList(this.mContextInfo, credentialStorage, list, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.removePackagesFromWhiteList getUCMService is null....");
        return -1;
    }

    public final int setAuthType(CredentialStorage credentialStorage, int i) {
        Log.i(TAG, "UniversalCredentialManager.setAuthType is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "setAuthType : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.setAuthType(this.mContextInfo, credentialStorage, i);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.setAuthType getUCMService is null....");
        return -1;
    }

    public final Bundle setCredentialStorageProperty(CredentialStorage credentialStorage, Bundle bundle) {
        Log.i(TAG, "UniversalCredentialManager.setCredentialStorageProperty is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7) < 0) {
            Log.i(TAG, "setPackageSetting : support above KNOX_ENTERPRISE_SDK_VERSION_2_7");
            return null;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.setCredentialStorageProperty(this.mContextInfo, credentialStorage, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.setCredentialStorageProperty getUCMService is null....");
        return null;
    }

    public final int setDefaultInstallStorage(CredentialStorage credentialStorage) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.setDefaultInstallStorage");
        Log.i(TAG, "UniversalCredentialManager.setDefaultInstallStorage is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
            Log.i(TAG, "setDefaultInstallStorage : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.setDefaultInstallStorage(this.mContextInfo, credentialStorage);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.setDefaultInstallStorage getUCMService is null....");
        return -1;
    }

    public final int setKeyguardPinMaximumLength(CredentialStorage credentialStorage, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.setKeyguardPinMaximumLength");
        Log.i(TAG, "UniversalCredentialManager.setKeyguardPinMaximumLength is called....");
        if (isNotSupportKnoxSdk(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_9)) {
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.setKeyguardPinMaximumLength(this.mContextInfo, credentialStorage, i);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.setKeyguardPinMaximumLength getUCMService is null....");
        return -1;
    }

    public final int setKeyguardPinMaximumRetryCount(CredentialStorage credentialStorage, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.setKeyguardPinMaximumRetryCount");
        Log.i(TAG, "UniversalCredentialManager.setKeyguardPinMaximumRetryCount is called....");
        if (isNotSupportKnoxSdk(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_9)) {
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.setKeyguardPinMaximumRetryCount(this.mContextInfo, credentialStorage, i);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.setKeyguardPinMaximumRetryCount getUCMService is null....");
        return -1;
    }

    public final int setKeyguardPinMinimumLength(CredentialStorage credentialStorage, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.setKeyguardPinMinimumLength");
        Log.i(TAG, "UniversalCredentialManager.setKeyguardPinMinimumLength is called....");
        if (isNotSupportKnoxSdk(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_9)) {
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.setKeyguardPinMinimumLength(this.mContextInfo, credentialStorage, i);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.setKeyguardPinMinimumLength getUCMService is null....");
        return -1;
    }

    public final int enforceCredentialStorageAsLockType(CredentialStorage credentialStorage, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialManager.enforceCredentialStorageAsLockType(CredentialStorage, Bundle)");
        Log.i(TAG, "UniversalCredentialManager.enforceCredentialStorageAsLockType(CredentialStorage, Bundle) is called....");
        if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7) < 0) {
            Log.i(TAG, "enforceCredentialStorageAsLockType : support above KNOX_ENTERPRISE_SDK_VERSION_2_7");
            return -1;
        }
        try {
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("The exception occurs "), TAG);
        }
        if (getUCMService() != null) {
            return mUCMService.enforceCredentialStorageAsLockType(this.mContextInfo, credentialStorage, bundle);
        }
        Log.i(TAG, "UniversalCredentialStorageManager.enforceCredentialStorageAsLockType getUCMService is null....");
        return -1;
    }

    public static synchronized UniversalCredentialManager getUCMManager(Context context, int i) {
        synchronized (UniversalCredentialManager.class) {
            if (EdmConstants.getEnterpriseKnoxSdkVersion().compareTo(EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6) < 0) {
                Log.i(TAG, "getUCMManager : support above KNOX_ENTERPRISE_SDK_VERSION_2_6");
                return null;
            }
            if (!isValidUser(context, i)) {
                Log.i(TAG, "getUCMManager : Invalid user request userId-" + i);
                return null;
            }
            return new UniversalCredentialManager(new ContextInfo(Process.myUid(), i), context);
        }
    }
}
