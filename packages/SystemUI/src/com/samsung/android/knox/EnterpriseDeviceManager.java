package com.samsung.android.knox;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.accounts.DeviceAccountPolicy;
import com.samsung.android.knox.accounts.EmailAccountPolicy;
import com.samsung.android.knox.accounts.EmailPolicy;
import com.samsung.android.knox.accounts.ExchangeAccountPolicy;
import com.samsung.android.knox.accounts.LDAPAccountPolicy;
import com.samsung.android.knox.application.ApplicationPolicy;
import com.samsung.android.knox.bluetooth.BluetoothPolicy;
import com.samsung.android.knox.bluetooth.BluetoothSecureModePolicy;
import com.samsung.android.knox.browser.BrowserPolicy;
import com.samsung.android.knox.cmfa.CmfaManager;
import com.samsung.android.knox.container.BasePasswordPolicy;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.knox.datetime.DateTimePolicy;
import com.samsung.android.knox.ddar.DualDARPolicy;
import com.samsung.android.knox.deviceinfo.DeviceInventory;
import com.samsung.android.knox.devicesecurity.APMPolicy;
import com.samsung.android.knox.devicesecurity.DeviceSecurityPolicy;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.dex.DexManager;
import com.samsung.android.knox.display.Font;
import com.samsung.android.knox.hdm.HdmManager;
import com.samsung.android.knox.keystore.CertificateProvisioning;
import com.samsung.android.knox.kiosk.KioskMode;
import com.samsung.android.knox.kpcc.KPCCManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.location.Geofencing;
import com.samsung.android.knox.location.LocationPolicy;
import com.samsung.android.knox.lockscreen.BootBanner;
import com.samsung.android.knox.lockscreen.LockscreenOverlay;
import com.samsung.android.knox.multiuser.MultiUserManager;
import com.samsung.android.knox.net.GlobalProxy;
import com.samsung.android.knox.net.apn.ApnSettingsPolicy;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.vpn.VpnPolicy;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.samsung.android.knox.nfc.NfcPolicy;
import com.samsung.android.knox.profile.ProfilePolicy;
import com.samsung.android.knox.remotecontrol.RemoteInjection;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.knox.restriction.RoamingPolicy;
import com.samsung.android.knox.restriction.SPDControlPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EnterpriseDeviceManager {
    public static final String ACTION_ADD_DEVICE_ADMIN = "android.app.action.ADD_DEVICE_ADMIN";
    public static final String ACTION_CALL_STATE_CHANGED = "com.samsung.android.knox.intent.action.CALL_STATE_CHANGED";
    public static final String ACTION_CHECK_REENROLLMENT = "edm.intent.action.sec.CHECK_REENROLLMENT";
    public static final String ACTION_CHECK_REENROLLMENT_INTERNAL = "com.samsung.android.knox.intent.action.CHECK_REENROLLMENT_INTERNAL";
    public static final String ACTION_DO_KEYGUARD_INTERNAL = "com.samsung.android.knox.intent.action.DO_KEYGUARD_INTERNAL";
    public static final String ACTION_EDM_BOOT_COMPLETED = "edm.intent.action.ACTION_EDM_BOOT_COMPLETED";
    public static final String ACTION_EDM_BOOT_COMPLETED_INTERNAL = "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL";
    public static final String ACTION_HARD_KEY_PRESS = "com.samsung.android.knox.intent.action.HARD_KEY_PRESS";
    public static final String ACTION_KEYGUARD_REFRESH_INTERNAL = "com.samsung.android.knox.intent.action.KEYGUARD_REFRESH_INTERNAL";
    public static final String ACTION_KNOX_RESTRICTIONS_CHANGED = "com.samsung.android.knox.intent.action.KNOX_RESTRICTIONS_CHANGED_INTERNAL";
    public static final String ACTION_MAM_KNOX_PRIVACY_POLICY_CHANGED_BY_USER = "com.samsung.android.knox.intent.action.MAM_KNOX_PRIVACY_POLICY_CHANGED_BY_USER";
    public static final String ACTION_MTP_BLOCKED_INTERNAL = "com.samsung.android.knox.intent.action.MTP_BLOCKED_INTERNAL";
    public static final String ACTION_NOTIFY_STORAGE_CARD_INTERNAL = "com.samsung.android.knox.intent.action.NOTIFY_STORAGE_CARD_INTERNAL";
    public static final String ACTION_NO_USER_ACTIVITY = "com.samsung.android.knox.intent.action.NO_USER_ACTIVITY";
    public static final String ACTION_OPERATOR_NAME_INTERNAL = "com.samsung.android.knox.intent.action.OPERATOR_NAME_INTERNAL";
    public static final String ACTION_QUICKSETTING_REFRESH_INTERNAL = "com.samsung.android.knox.intent.action.QUICKSETTING_REFRESH_INTERNAL";
    public static final String ACTION_SEND_DTMF_INTERNAL = "com.samsung.android.knox.intent.action.SEND_DTMF_INTERNAL";
    public static final String ACTION_SET_KEYBOARD_MODE_INTERNAL = "com.samsung.android.knox.intent.action.SET_KEYBOARD_MODE_INTERNAL";
    public static final String ACTION_USER_ACTIVITY = "com.samsung.android.knox.intent.action.USER_ACTIVITY";
    public static final String APN_SETTINGS_POLICY_SERVICE = "apn_settings_policy";
    public static final String APPLICATION_POLICY_SERVICE = "application_policy";
    public static final String AUDIT_LOG = "auditlog";
    public static final String BLUETOOTH_POLICY_SERVICE = "bluetooth_policy";
    public static final String BROWSER_SETTINGS_POLICY_SERVICE = "browser_policy";
    public static final String BT_SECURE_MODE_POLICY_SERVICE = "bluetooth_secure_mode_policy";
    public static final String CERTIFICATE_POLICY_SERVICE = "certificate_policy";
    public static final String DATE_TIME_POLICY_SERVICE = "date_time_policy";
    public static final int DEFAULT_USER_ACTIVITY_TIMEOUT = 0;
    public static final String DEVICE_ACCOUNT_POLICY_SERVICE = "device_account_policy";
    public static final String DEVICE_INVENTORY_SERVICE = "device_info";
    public static final String DEX_POLICY_SERVICE = "dex_policy";
    public static final String EAS_ACCOUNT_POLICY_SERVICE = "eas_account_policy";
    public static final String EMAIL_ACCOUNT_POLICY_SERVICE = "email_account_policy";
    public static final String EMAIL_POLICY_SERVICE = "email_policy";
    public static final String ENTERPRISE_BILLING_POLICY_SERVICE = "enterprise_billing_policy";
    public static final String ENTERPRISE_LICENSE_POLICY_SERVICE = "enterprise_license_policy";
    public static final String ENTERPRISE_POLICY_SERVICE = "enterprise_policy";
    public static final int ERROR_CRYPTO_CHECK_FAILURE = -5;
    public static final int ERROR_INVALID_FILE = -3;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_ACTIVE_ADMIN = -2;
    public static final int ERROR_PACKAGE_NAME_MISMATCH = -4;
    public static final int ERROR_UNKNOWN = -1;
    public static final String EXTRA_ADD_EXPLANATION = "android.app.extra.ADD_EXPLANATION";
    public static final String EXTRA_CALL_STATE = "com.samsung.android.knox.intent.extra.CALL_STATE";
    public static final String EXTRA_CURRENT_VERSION = "com.samsung.android.knox.intent.extra.CURRENT_VERSION";
    public static final String EXTRA_DEVICE_ADMIN = "android.app.extra.DEVICE_ADMIN";
    public static final String EXTRA_DTMF_DURATION_INTERNAL = "com.samsung.android.knox.intent.extra.DTMF_DURATION_INTERNAL";
    public static final String EXTRA_DTMF_TONE_INTERNAL = "com.samsung.android.knox.intent.extra.DTMF_TONE_INTERNAL";
    public static final String EXTRA_KEYBOARD_MODE_INTERNAL = "com.samsung.android.knox.intent.extra.KEYBOARD_MODE_INTERNAL";
    public static final String EXTRA_KEY_CODE = "com.samsung.android.knox.intent.extra.KEY_CODE";
    public static final String EXTRA_MIGRATION_RESULT = "com.samsung.android.knox.intent.extra.MIGRATION_RESULT";
    public static final String EXTRA_PHONE_STATE = "com.samsung.android.knox.intent.extra.PHONE_STATE";
    public static final String FIREWALL_SERVICE = "firewall";
    public static final String GEOFENCING = "geofencing";
    public static final String HDM_SERVICE = "hdm_service";
    public static final String KIOSKMODE = "kioskmode";
    public static final int KNOX_2_7_1 = 21;
    public static final int KNOX_2_8 = 22;
    public static final String KNOX_CCM_POLICY_SERVICE = "knox_ccm_policy";
    public static final String KNOX_CUSTOM_MANAGER_SERVICE = "knoxcustom";
    public static final String KNOX_KPCC_MANAGER_SERVICE = "kpcc";
    public static final String KNOX_NETWORK_ANALYTICS_SERVICE = "knoxnap";
    public static final String KNOX_NETWORK_FILTER_SERVICE = "knox_nwFilterMgr_policy";
    public static final int KNOX_NOT_SUPPORTED = -1;
    public static final String KNOX_SCEP_POLICY_SERVICE = "knox_scep_policy";
    public static final String KNOX_TIMAKEYSTORE_POLICY_SERVICE = "knox_timakeystore_policy";
    public static final String KNOX_TRUSTED_PINPAD_POLICY_SERVICE = "knox_pinpad_service";
    public static final String KNOX_UCSM_POLICY_SERVICE = "knox_ucsm_policy";
    public static final String KPE_CORE_PACKAGE_NAME = "com.samsung.android.knox.kpecore";
    public static final String LDAP_ACCOUNT_POLICY_SERVICE = "ldap_account_policy";
    public static final String LICENSE_LOG_SERVICE = "license_log_service";
    public static final String LOCATION_POLICY_SERVICE = "location_policy";
    public static final String LOCKSCREEN_OVERLAY_SERVICE = "lockscreen_overlay";
    public static final String MISC_POLICY_SERVICE = "misc_policy";
    public static final String MULTI_USER_MANAGER_SERVICE = "multi_user_manager_service";
    public static final String MUM_CONTAINER_POLICY_SERVICE = "mum_container_policy";
    public static final String MUM_CONTAINER_RCP_POLICY_SERVICE = "mum_container_rcp_policy";
    public static final int NO_AUTHORIZATION = 0;
    public static final String PASSWORD_POLICY_SERVICE = "password_policy";
    public static final int PASSWORD_QUALITY_ALPHABETIC = 262144;
    public static final int PASSWORD_QUALITY_ALPHANUMERIC = 327680;
    public static final int PASSWORD_QUALITY_NUMERIC = 131072;
    public static final int PASSWORD_QUALITY_SOMETHING = 65536;
    public static final int PASSWORD_QUALITY_UNSPECIFIED = 0;
    public static final String PHONE_RESTRICTION_POLICY_SERVICE = "phone_restriction_policy";
    public static final String PROFILE_POLICY_SERVICE = "profilepolicy";
    public static final int PROVISION_CERT_MASK = 2;
    public static final int REMOTE_CONTROL_MASK = 1;
    public static final String REMOTE_INJECTION_SERVICE = "remoteinjection";
    public static final int RESET_PASSWORD_REQUIRE_ENTRY = 1;
    public static final String RESTRICTION_POLICY_SERVICE = "restriction_policy";
    public static final String ROAMING_POLICY_SERVICE = "roaming_policy";
    public static final String SECURITY_POLICY_SERVICE = "security_policy";
    public static final String SMARTCARD_BROWSER_POLICY_SERVICE = "smartcard_browser_policy";
    public static final String SMARTCARD_EMAIL_POLICY_SERVICE = "smartcard_email_policy";
    public static String TAG = "EnterpriseDeviceManager";
    public static final String THREAT_DEFENSE_SERVICE = "threat_defense_service";
    public static final int USER_ACTIVE = 91;
    public static final int USER_CREATION_IN_PROGRESS = 93;
    public static final int USER_DOESNT_EXISTS = -1;
    public static final int USER_LOCKED = 95;
    public static final String VPN_POLICY_SERVICE = "vpn_policy";
    public static final String WIFI_POLICY_SERVICE = "wifi_policy";
    public static final int WIPE_EXTERNAL_STORAGE = 1;
    public static EnterpriseDeviceManager mParentInstance;
    public static final Object mSync = new Object();
    public static EnterpriseDeviceManager sEnterpriseDeviceManager;
    public volatile APMPolicy mAPMPolicy;
    public volatile ApnSettingsPolicy mApnSettingsPolicy;
    public volatile ApplicationPolicy mApplicationPolicy;
    public volatile BluetoothSecureModePolicy mBTSecureModePolicy;
    public volatile BluetoothPolicy mBluetoothPolicy;
    public volatile BootBanner mBootBanner;
    public volatile BrowserPolicy mBrowserPolicy;
    public volatile CertificateProvisioning mCertificateProvisioning;
    public volatile CmfaManager mCmfaManager;
    public final Context mContext;
    public final ContextInfo mContextInfo;
    public DevicePolicyManager mDPM;
    public volatile DateTimePolicy mDateTimePolicy;
    public volatile DeviceAccountPolicy mDeviceAccountPolicy;
    public volatile DeviceInventory mDeviceInventory;
    public volatile DeviceSecurityPolicy mDeviceSecurityPolicy;
    public volatile DexManager mDexManager;
    public volatile DualDARPolicy mDualDARPolicy;
    public volatile ExchangeAccountPolicy mEasAccountPolicy;
    public volatile EmailAccountPolicy mEmailAccountPolicy;
    public volatile EmailPolicy mEmailPolicy;
    public volatile EnterpriseLicenseManager mEnterpriseLicenseManager;
    public volatile Firewall mFirewall;
    public volatile Font mFont;
    public volatile Geofencing mGeofencing;
    public volatile GlobalProxy mGlobalProxy;
    public volatile HdmManager mHdmManager;
    public volatile KPCCManager mKPCCManager;
    public volatile KioskMode mKioskMode;
    public volatile LDAPAccountPolicy mLDAPAccountPolicy;
    public volatile LocationPolicy mLocationPolicy;
    public volatile LockscreenOverlay mLockscreenOverlay;
    public volatile MultiUserManager mMultiUserManager;
    public volatile NfcPolicy mNfcPolicy;
    public volatile PasswordPolicy mPasswordPolicy;
    public volatile Map<String, PhoneRestrictionPolicy> mPhoneRestrictionMap;
    public volatile ProfilePolicy mProfilePolicy;
    public HashMap<Integer, ProfilePolicy> mProfilePolicyMap;
    public volatile RemoteInjection mRemoteInjection;
    public volatile RestrictionPolicy mRestrictionPolicy;
    public volatile RoamingPolicy mRoamingPolicy;
    public volatile SPDControlPolicy mSPDControlPolicy;
    public IEnterpriseDeviceManager mService;
    public volatile VpnPolicy mVpnPolicy;
    public volatile WifiPolicy mWifiPolicy;

    public enum EnterpriseKeyVersion {
        ENTERPRISE_KEY_VERSION_1;

        public static /* synthetic */ EnterpriseKeyVersion[] $values() {
            return new EnterpriseKeyVersion[]{ENTERPRISE_KEY_VERSION_1};
        }
    }

    public EnterpriseDeviceManager(Context context) {
        this(context, true, new ContextInfo(Process.myUid()));
    }

    public static EnterpriseDeviceManager create(Context context, Handler handler) {
        EnterpriseDeviceManager enterpriseDeviceManager = new EnterpriseDeviceManager(context, true, new ContextInfo(Process.myUid()));
        if (enterpriseDeviceManager.getService() != null) {
            return enterpriseDeviceManager;
        }
        return null;
    }

    public static boolean enforceWpcod() {
        return AccessController.enforceWpcod();
    }

    public static int getAPILevel() {
        try {
            return EdmUtils.getAPILevelForInternal();
        } catch (UnsupportedOperationException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    public static int getAPILevelForInternal() {
        return EdmUtils.getAPILevelForInternal();
    }

    public static int getCallingUserId(ContextInfo contextInfo) {
        return EdmUtils.getCallingUserId(contextInfo);
    }

    public static int getContainerId(int i) {
        return 0;
    }

    public static int getContainerType(int i) {
        return -1;
    }

    public static EdmConstants.EnterpriseSdkVersion getEnterpriseSdkVerInternal() {
        return EdmConstants.getEnterpriseSdkVerInternal();
    }

    public static EnterpriseDeviceManager getInstance(Context context) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        synchronized (mSync) {
            try {
                int iMyUid = Process.myUid();
                int iMyPid = Process.myPid();
                int iMyTid = Process.myTid();
                if (sEnterpriseDeviceManager == null) {
                    sEnterpriseDeviceManager = new EnterpriseDeviceManager(context);
                    KnoxsdkFileLog.d(TAG, "getInstance() : (" + iMyPid + "/" + iMyTid + ") create an instance with UID " + iMyUid);
                }
                if (sEnterpriseDeviceManager.mContextInfo.mCallerUid != iMyUid) {
                    KnoxsdkFileLog.w(TAG, "getInstance() : (" + iMyPid + "/" + iMyTid + ") currentUid is " + iMyUid + " but mCallerUid is " + sEnterpriseDeviceManager.mContextInfo.mCallerUid);
                }
                enterpriseDeviceManager = sEnterpriseDeviceManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return enterpriseDeviceManager;
    }

    public static EnterpriseDeviceManager getParentInstance(Context context) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        if (!AccessController.enforceWpcod()) {
            return null;
        }
        synchronized (mSync) {
            try {
                if (mParentInstance == null) {
                    mParentInstance = new EnterpriseDeviceManager(context, true);
                }
                enterpriseDeviceManager = mParentInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return enterpriseDeviceManager;
    }

    public static int getUserId(UserHandle userHandle) {
        if (userHandle == null) {
            return -1;
        }
        try {
            return userHandle.getIdentifier();
        } catch (Exception unused) {
            Log.w(TAG, "Failed to get user id by calling userHandle.getIdentifier()");
            return -1;
        }
    }

    public static boolean guardianMUsed() {
        return !TextUtils.isEmpty("");
    }

    public static boolean inHouseManufacturing() {
        return true;
    }

    public static boolean isOfficiallySupported() throws UnsupportedOperationException {
        return true;
    }

    public static boolean jdmManufacturing() {
        return false;
    }

    public static boolean sepBasicSupported() {
        return true;
    }

    public static boolean sepLiteNewSupported() {
        return false;
    }

    public static boolean sepLiteSupported() {
        return false;
    }

    public static void throwIfParentInstance(ContextInfo contextInfo, String str) {
        AccessController.throwIfParentInstance(contextInfo, str);
    }

    public boolean activateDevicePermissions(List<String> list) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.activateDevicePermissions(list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean addAuthorizedUid(int i, int i2) {
        if (getService() == null) {
            return false;
        }
        try {
            Log.d(TAG, "addAuthorizedUid");
            return this.mService.addAuthorizedUid(i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public int addPseudoAdminForParent(int i) {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.addPseudoAdminForParent(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return -1;
        }
    }

    public byte[] captureUmcLogs(String str, List<String> list) {
        if (getService() == null) {
            return null;
        }
        try {
            Log.d(TAG, "captureUmcLogs");
            return this.mService.captureUmcLogs(this.mContextInfo, str, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public boolean disableConstrainedState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.disableConstrainedState(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        } catch (Exception e2) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("exception occured! ", e2, TAG);
            return false;
        }
    }

    public boolean enableConstrainedState(String str, String str2, String str3, String str4, int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.enableConstrainedState(this.mContextInfo, str, str2, str3, str4, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        } catch (Exception e2) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("exception occured! ", e2, TAG);
            return false;
        }
    }

    public boolean enableWipe() {
        if (getService() == null) {
            return false;
        }
        try {
            Log.d(TAG, "enableWipe");
            return this.mService.enableWipe(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public void enforceActiveAdminPermission(String str) throws SecurityException {
        if (getService() != null) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                this.mService.enforceActiveAdminPermission(arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, String str) throws SecurityException {
        if (getService() != null) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                return this.mService.enforceActiveAdminPermissionByContext(contextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public void enforceComponentCheck(ContextInfo contextInfo, ComponentName componentName) throws SecurityException {
        if (getService() != null) {
            try {
                this.mService.enforceComponentCheck(contextInfo, componentName);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public ContextInfo enforceContainerOwnerShipPermissionByContext(ContextInfo contextInfo, String str) throws SecurityException {
        if (getService() != null) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                return this.mService.enforceContainerOwnerShipPermissionByContext(contextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public ContextInfo enforceDeviceOwnerAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) throws SecurityException {
        if (getService() != null) {
            try {
                return this.mService.enforceDeviceOwnerAndActiveAdminPermission(contextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public ContextInfo enforceDoPoOnlyPermissionByContext(ContextInfo contextInfo, List<String> list) throws SecurityException {
        if (getService() != null) {
            try {
                return this.mService.enforceDoPoOnlyPermissionByContext(contextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, String str) throws SecurityException {
        if (getService() != null) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                return this.mService.enforceOwnerOnlyAndActiveAdminPermission(contextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public ContextInfo enforcePermissionByContext(ContextInfo contextInfo, String str) throws SecurityException {
        if (getService() != null) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                return this.mService.enforcePermissionByContext(contextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public void enforceZtFwCaller(String str) {
        if (getService() != null) {
            try {
                this.mService.enforceZtFwCaller(new ContextInfo(Binder.getCallingUid()), str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public APMPolicy getAPMPolicy() {
        APMPolicy aPMPolicy;
        AccessController.throwIfParentInstance(this.mContextInfo, "getAPMPolicy");
        APMPolicy aPMPolicy2 = this.mAPMPolicy;
        if (aPMPolicy2 != null) {
            return aPMPolicy2;
        }
        synchronized (this) {
            try {
                aPMPolicy = this.mAPMPolicy;
                if (aPMPolicy == null) {
                    aPMPolicy = new APMPolicy(this.mContextInfo);
                    this.mAPMPolicy = aPMPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return aPMPolicy;
    }

    public ComponentName getActiveAdminComponent() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getActiveAdminComponent();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public List<ComponentName> getActiveAdmins(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getActiveAdmins(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public List<EnterpriseDeviceAdminInfo> getActiveAdminsInfo(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getActiveAdminsInfo(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public ContextInfo getAdminContextIfCallerInCertWhiteList(List<String> list) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAdminContextIfCallerInCertWhiteList(list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public boolean getAdminRemovable(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getAdminRemovable");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAdminRemovable(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return true;
        }
    }

    public int getAdminUidForAuthorizedUid(int i) {
        if (getService() == null) {
            return -1;
        }
        try {
            Log.d(TAG, "getAdminUidForAuthorizedUid");
            return this.mService.getAdminUidForAuthorizedUid(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return -1;
        }
    }

    public ApnSettingsPolicy getApnSettingsPolicy() {
        ApnSettingsPolicy apnSettingsPolicy;
        AccessController.throwIfParentInstance(this.mContextInfo, "getApnSettingsPolicy");
        ApnSettingsPolicy apnSettingsPolicy2 = this.mApnSettingsPolicy;
        if (apnSettingsPolicy2 != null) {
            return apnSettingsPolicy2;
        }
        synchronized (this) {
            try {
                apnSettingsPolicy = this.mApnSettingsPolicy;
                if (apnSettingsPolicy == null) {
                    apnSettingsPolicy = new ApnSettingsPolicy(this.mContextInfo);
                    this.mApnSettingsPolicy = apnSettingsPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return apnSettingsPolicy;
    }

    public ApplicationPolicy getApplicationPolicy() {
        ApplicationPolicy applicationPolicy;
        ApplicationPolicy applicationPolicy2 = this.mApplicationPolicy;
        if (applicationPolicy2 != null) {
            return applicationPolicy2;
        }
        synchronized (this) {
            try {
                applicationPolicy = this.mApplicationPolicy;
                if (applicationPolicy == null) {
                    applicationPolicy = new ApplicationPolicy(this.mContextInfo, this.mContext);
                    this.mApplicationPolicy = applicationPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return applicationPolicy;
    }

    public int getAuthorizedUidForAdminUid(int i) {
        if (getService() == null) {
            return -1;
        }
        try {
            Log.d(TAG, "getAuthorizedUidForAdminUid");
            return this.mService.getAuthorizedUidForAdminUid(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return -1;
        }
    }

    public BasePasswordPolicy getBasePasswordPolicy() {
        return getPasswordPolicy().getBasePasswordPolicy();
    }

    public BluetoothPolicy getBluetoothPolicy() {
        BluetoothPolicy bluetoothPolicy;
        BluetoothPolicy bluetoothPolicy2 = this.mBluetoothPolicy;
        if (bluetoothPolicy2 != null) {
            return bluetoothPolicy2;
        }
        synchronized (this) {
            try {
                bluetoothPolicy = this.mBluetoothPolicy;
                if (bluetoothPolicy == null) {
                    bluetoothPolicy = new BluetoothPolicy(this.mContextInfo);
                    this.mBluetoothPolicy = bluetoothPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return bluetoothPolicy;
    }

    public BluetoothSecureModePolicy getBluetoothSecureModePolicy() {
        BluetoothSecureModePolicy bluetoothSecureModePolicy;
        AccessController.throwIfParentInstance(this.mContextInfo, "getBluetoothSecureModePolicy");
        BluetoothSecureModePolicy bluetoothSecureModePolicy2 = this.mBTSecureModePolicy;
        if (bluetoothSecureModePolicy2 != null) {
            return bluetoothSecureModePolicy2;
        }
        synchronized (this) {
            try {
                bluetoothSecureModePolicy = this.mBTSecureModePolicy;
                if (bluetoothSecureModePolicy == null) {
                    bluetoothSecureModePolicy = new BluetoothSecureModePolicy(this.mContextInfo);
                    this.mBTSecureModePolicy = bluetoothSecureModePolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return bluetoothSecureModePolicy;
    }

    public BootBanner getBootBanner() {
        BootBanner bootBanner;
        BootBanner bootBanner2 = this.mBootBanner;
        if (bootBanner2 != null) {
            return bootBanner2;
        }
        synchronized (this) {
            try {
                bootBanner = this.mBootBanner;
                if (bootBanner == null) {
                    bootBanner = new BootBanner(this.mContextInfo);
                    this.mBootBanner = bootBanner;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return bootBanner;
    }

    public BrowserPolicy getBrowserPolicy() {
        BrowserPolicy browserPolicy;
        AccessController.throwIfParentInstance(this.mContextInfo, "getBrowserPolicy");
        BrowserPolicy browserPolicy2 = this.mBrowserPolicy;
        if (browserPolicy2 != null) {
            return browserPolicy2;
        }
        synchronized (this) {
            try {
                browserPolicy = this.mBrowserPolicy;
                if (browserPolicy == null) {
                    browserPolicy = new BrowserPolicy(this.mContextInfo);
                    this.mBrowserPolicy = browserPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return browserPolicy;
    }

    public CertificateProvisioning getCertificateProvisioning() {
        CertificateProvisioning certificateProvisioning;
        AccessController.throwIfParentInstance(this.mContextInfo, "getCertificateProvisioning");
        CertificateProvisioning certificateProvisioning2 = this.mCertificateProvisioning;
        if (certificateProvisioning2 != null) {
            return certificateProvisioning2;
        }
        synchronized (this) {
            try {
                certificateProvisioning = this.mCertificateProvisioning;
                if (certificateProvisioning == null) {
                    certificateProvisioning = new CertificateProvisioning(this.mContextInfo);
                    this.mCertificateProvisioning = certificateProvisioning;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return certificateProvisioning;
    }

    public CmfaManager getCmfaManager() {
        CmfaManager cmfaManager;
        int aPILevel = getAPILevel();
        if (aPILevel == -1 || aPILevel > 36) {
            return null;
        }
        CmfaManager cmfaManager2 = this.mCmfaManager;
        if (cmfaManager2 != null) {
            return cmfaManager2;
        }
        synchronized (this) {
            try {
                cmfaManager = this.mCmfaManager;
                if (cmfaManager == null) {
                    cmfaManager = new CmfaManager(this.mContext);
                    this.mCmfaManager = cmfaManager;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return cmfaManager;
    }

    public int getConstrainedState() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getConstrainedState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return 0;
        } catch (Exception e2) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("Security exception occured! ", e2, TAG);
            return 0;
        }
    }

    public int getCurrentFailedPasswordAttempts() {
        return this.mDPM.getCurrentFailedPasswordAttempts();
    }

    public DateTimePolicy getDateTimePolicy() {
        DateTimePolicy dateTimePolicy;
        DateTimePolicy dateTimePolicy2 = this.mDateTimePolicy;
        if (dateTimePolicy2 != null) {
            return dateTimePolicy2;
        }
        synchronized (this) {
            try {
                dateTimePolicy = this.mDateTimePolicy;
                if (dateTimePolicy == null) {
                    dateTimePolicy = new DateTimePolicy(this.mContextInfo);
                    this.mDateTimePolicy = dateTimePolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return dateTimePolicy;
    }

    public DeviceAccountPolicy getDeviceAccountPolicy() {
        DeviceAccountPolicy deviceAccountPolicy;
        DeviceAccountPolicy deviceAccountPolicy2 = this.mDeviceAccountPolicy;
        if (deviceAccountPolicy2 != null) {
            return deviceAccountPolicy2;
        }
        synchronized (this) {
            try {
                deviceAccountPolicy = this.mDeviceAccountPolicy;
                if (deviceAccountPolicy == null) {
                    deviceAccountPolicy = new DeviceAccountPolicy(this.mContextInfo);
                    this.mDeviceAccountPolicy = deviceAccountPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return deviceAccountPolicy;
    }

    public DeviceInventory getDeviceInventory() {
        DeviceInventory deviceInventory;
        DeviceInventory deviceInventory2 = this.mDeviceInventory;
        if (deviceInventory2 != null) {
            return deviceInventory2;
        }
        synchronized (this) {
            try {
                deviceInventory = this.mDeviceInventory;
                if (deviceInventory == null) {
                    deviceInventory = new DeviceInventory(this.mContextInfo, this.mContext, new ExternalDependencyInjectorImpl());
                    this.mDeviceInventory = deviceInventory;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return deviceInventory;
    }

    public DeviceSecurityPolicy getDeviceSecurityPolicy() {
        DeviceSecurityPolicy deviceSecurityPolicy;
        DeviceSecurityPolicy deviceSecurityPolicy2 = this.mDeviceSecurityPolicy;
        if (deviceSecurityPolicy2 != null) {
            return deviceSecurityPolicy2;
        }
        synchronized (this) {
            try {
                deviceSecurityPolicy = this.mDeviceSecurityPolicy;
                if (deviceSecurityPolicy == null) {
                    deviceSecurityPolicy = new DeviceSecurityPolicy(this.mContextInfo, this.mContext);
                    this.mDeviceSecurityPolicy = deviceSecurityPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return deviceSecurityPolicy;
    }

    public DexManager getDexManager() {
        DexManager dexManager;
        DexManager dexManager2 = this.mDexManager;
        if (dexManager2 != null) {
            return dexManager2;
        }
        synchronized (this) {
            try {
                dexManager = this.mDexManager;
                if (dexManager == null) {
                    dexManager = new DexManager(this.mContextInfo);
                    this.mDexManager = dexManager;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return dexManager;
    }

    public DualDARPolicy getDualDARPolicy() {
        DualDARPolicy dualDARPolicy;
        DualDARPolicy dualDARPolicy2 = this.mDualDARPolicy;
        if (dualDARPolicy2 != null) {
            return dualDARPolicy2;
        }
        synchronized (this) {
            try {
                dualDARPolicy = this.mDualDARPolicy;
                if (dualDARPolicy == null) {
                    dualDARPolicy = new DualDARPolicy(this.mContextInfo);
                    this.mDualDARPolicy = dualDARPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return dualDARPolicy;
    }

    public EmailAccountPolicy getEmailAccountPolicy() {
        EmailAccountPolicy emailAccountPolicy;
        AccessController.throwIfParentInstance(this.mContextInfo, "getEmailAccountPolicy");
        EmailAccountPolicy emailAccountPolicy2 = this.mEmailAccountPolicy;
        if (emailAccountPolicy2 != null) {
            return emailAccountPolicy2;
        }
        synchronized (this) {
            try {
                emailAccountPolicy = this.mEmailAccountPolicy;
                if (emailAccountPolicy == null) {
                    emailAccountPolicy = new EmailAccountPolicy(this.mContextInfo);
                    this.mEmailAccountPolicy = emailAccountPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return emailAccountPolicy;
    }

    public EmailPolicy getEmailPolicy() {
        EmailPolicy emailPolicy;
        AccessController.throwIfParentInstance(this.mContextInfo, "getEmailPolicy");
        EmailPolicy emailPolicy2 = this.mEmailPolicy;
        if (emailPolicy2 != null) {
            return emailPolicy2;
        }
        synchronized (this) {
            try {
                emailPolicy = this.mEmailPolicy;
                if (emailPolicy == null) {
                    emailPolicy = new EmailPolicy(this.mContextInfo);
                    this.mEmailPolicy = emailPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return emailPolicy;
    }

    public EnterpriseLicenseManager getEnterpriseLicenseManager() {
        EnterpriseLicenseManager enterpriseLicenseManager;
        AccessController.throwIfParentInstance(this.mContextInfo, "getEnterpriseLicenseManager");
        EnterpriseLicenseManager enterpriseLicenseManager2 = this.mEnterpriseLicenseManager;
        if (enterpriseLicenseManager2 != null) {
            return enterpriseLicenseManager2;
        }
        synchronized (this) {
            try {
                enterpriseLicenseManager = this.mEnterpriseLicenseManager;
                if (enterpriseLicenseManager == null) {
                    enterpriseLicenseManager = new EnterpriseLicenseManager();
                    this.mEnterpriseLicenseManager = enterpriseLicenseManager;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return enterpriseLicenseManager;
    }

    public EdmConstants.EnterpriseSdkVersion getEnterpriseSdkVer() {
        return EdmConstants.getEnterpriseSdkVerInternal();
    }

    public ExchangeAccountPolicy getExchangeAccountPolicy() {
        ExchangeAccountPolicy exchangeAccountPolicy;
        AccessController.throwIfParentInstance(this.mContextInfo, "getExchangeAccountPolicy");
        ExchangeAccountPolicy exchangeAccountPolicy2 = this.mEasAccountPolicy;
        if (exchangeAccountPolicy2 != null) {
            return exchangeAccountPolicy2;
        }
        synchronized (this) {
            try {
                exchangeAccountPolicy = this.mEasAccountPolicy;
                if (exchangeAccountPolicy == null) {
                    exchangeAccountPolicy = new ExchangeAccountPolicy(this.mContextInfo);
                    this.mEasAccountPolicy = exchangeAccountPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return exchangeAccountPolicy;
    }

    public Firewall getFirewall() {
        Firewall firewall;
        Firewall firewall2 = this.mFirewall;
        if (firewall2 != null) {
            return firewall2;
        }
        synchronized (this) {
            try {
                firewall = this.mFirewall;
                if (firewall == null) {
                    firewall = new Firewall(this.mContextInfo);
                    this.mFirewall = firewall;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return firewall;
    }

    public Font getFont() {
        Font font;
        Font font2 = this.mFont;
        if (font2 != null) {
            return font2;
        }
        synchronized (this) {
            try {
                font = this.mFont;
                if (font == null) {
                    font = new Font(this.mContextInfo, this.mContext);
                    this.mFont = font;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return font;
    }

    public Geofencing getGeofencing() {
        Geofencing geofencing;
        AccessController.throwIfParentInstance(this.mContextInfo, "getGeofencing");
        Geofencing geofencing2 = this.mGeofencing;
        if (geofencing2 != null) {
            return geofencing2;
        }
        synchronized (this) {
            try {
                geofencing = this.mGeofencing;
                if (geofencing == null) {
                    geofencing = new Geofencing(this.mContextInfo, this.mContext);
                    this.mGeofencing = geofencing;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return geofencing;
    }

    public GlobalProxy getGlobalProxy() {
        GlobalProxy globalProxy;
        AccessController.throwIfParentInstance(this.mContextInfo, "getGlobalProxy");
        GlobalProxy globalProxy2 = this.mGlobalProxy;
        if (globalProxy2 != null) {
            return globalProxy2;
        }
        synchronized (this) {
            try {
                globalProxy = this.mGlobalProxy;
                if (globalProxy == null) {
                    globalProxy = new GlobalProxy(this.mContextInfo);
                    this.mGlobalProxy = globalProxy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return globalProxy;
    }

    public HdmManager getHypervisorDeviceManager() {
        HdmManager hdmManager;
        HdmManager hdmManager2 = this.mHdmManager;
        if (hdmManager2 != null) {
            return hdmManager2;
        }
        synchronized (this) {
            try {
                hdmManager = this.mHdmManager;
                if (hdmManager == null) {
                    hdmManager = new HdmManager(this.mContextInfo);
                    this.mHdmManager = hdmManager;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return hdmManager;
    }

    public KPCCManager getKPCCManager() {
        KPCCManager kPCCManager;
        AccessController.throwIfParentInstance(this.mContextInfo, "getKPCCManager");
        KPCCManager kPCCManager2 = this.mKPCCManager;
        if (kPCCManager2 != null) {
            return kPCCManager2;
        }
        synchronized (this) {
            try {
                kPCCManager = this.mKPCCManager;
                if (kPCCManager == null) {
                    kPCCManager = new KPCCManager(this.mContextInfo);
                    this.mKPCCManager = kPCCManager;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return kPCCManager;
    }

    public String getKPUPackageName() {
        if (getService() == null) {
            return null;
        }
        try {
            Log.d(TAG, "getKPUPackageName");
            return this.mService.getKPUPackageName();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public KioskMode getKioskMode() {
        KioskMode kioskMode;
        KioskMode kioskMode2 = this.mKioskMode;
        if (kioskMode2 != null) {
            return kioskMode2;
        }
        synchronized (this) {
            try {
                kioskMode = this.mKioskMode;
                if (kioskMode == null) {
                    kioskMode = new KioskMode(this.mContextInfo, this.mContext);
                    this.mKioskMode = kioskMode;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return kioskMode;
    }

    public LDAPAccountPolicy getLDAPAccountPolicy() {
        LDAPAccountPolicy lDAPAccountPolicy;
        AccessController.throwIfParentInstance(this.mContextInfo, "getLDAPAccountPolicy");
        LDAPAccountPolicy lDAPAccountPolicy2 = this.mLDAPAccountPolicy;
        if (lDAPAccountPolicy2 != null) {
            return lDAPAccountPolicy2;
        }
        synchronized (this) {
            try {
                lDAPAccountPolicy = this.mLDAPAccountPolicy;
                if (lDAPAccountPolicy == null) {
                    lDAPAccountPolicy = new LDAPAccountPolicy(this.mContextInfo, this.mContext);
                    this.mLDAPAccountPolicy = lDAPAccountPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return lDAPAccountPolicy;
    }

    public LocationPolicy getLocationPolicy() {
        LocationPolicy locationPolicy;
        LocationPolicy locationPolicy2 = this.mLocationPolicy;
        if (locationPolicy2 != null) {
            return locationPolicy2;
        }
        synchronized (this) {
            try {
                locationPolicy = this.mLocationPolicy;
                if (locationPolicy == null) {
                    locationPolicy = new LocationPolicy(this.mContextInfo);
                    this.mLocationPolicy = locationPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return locationPolicy;
    }

    public LockscreenOverlay getLockscreenOverlay() {
        LockscreenOverlay lockscreenOverlay;
        LockscreenOverlay lockscreenOverlay2 = this.mLockscreenOverlay;
        if (lockscreenOverlay2 != null) {
            return lockscreenOverlay2;
        }
        synchronized (this) {
            try {
                lockscreenOverlay = this.mLockscreenOverlay;
                if (lockscreenOverlay == null) {
                    lockscreenOverlay = new LockscreenOverlay(this.mContextInfo, this.mContext);
                    this.mLockscreenOverlay = lockscreenOverlay;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return lockscreenOverlay;
    }

    public int getMaximumFailedPasswordsForWipe() {
        return this.mDPM.getMaximumFailedPasswordsForWipe(null);
    }

    public long getMaximumTimeToLock() {
        return this.mDPM.getMaximumTimeToLock(null);
    }

    public MultiUserManager getMultiUserManager() {
        MultiUserManager multiUserManager;
        MultiUserManager multiUserManager2 = this.mMultiUserManager;
        if (multiUserManager2 != null) {
            return multiUserManager2;
        }
        synchronized (this) {
            try {
                multiUserManager = this.mMultiUserManager;
                if (multiUserManager == null) {
                    multiUserManager = new MultiUserManager(this.mContextInfo, this.mContext);
                    this.mMultiUserManager = multiUserManager;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return multiUserManager;
    }

    public NfcPolicy getNfcPolicy() {
        NfcPolicy nfcPolicy;
        NfcPolicy nfcPolicy2 = this.mNfcPolicy;
        if (nfcPolicy2 != null) {
            return nfcPolicy2;
        }
        synchronized (this) {
            try {
                nfcPolicy = this.mNfcPolicy;
                if (nfcPolicy == null) {
                    nfcPolicy = new NfcPolicy(this.mContextInfo);
                    this.mNfcPolicy = nfcPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return nfcPolicy;
    }

    public ProfilePolicy getOtherProfilePolicy(int i) {
        throwIfParentInstance(this.mContextInfo, "getProfilePolicy");
        ProfilePolicy profilePolicy = this.mProfilePolicyMap.get(Integer.valueOf(i));
        if (profilePolicy != null) {
            return profilePolicy;
        }
        synchronized (this) {
            if (profilePolicy == null) {
                try {
                    profilePolicy = new ProfilePolicy(this.mContextInfo, i);
                    this.mProfilePolicyMap.put(Integer.valueOf(i), profilePolicy);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return profilePolicy;
    }

    public String getPassword(ComponentName componentName) {
        return "";
    }

    public int getPasswordMaximumLength(int i) {
        return this.mDPM.getPasswordMaximumLength(i);
    }

    public int getPasswordMinimumLength() {
        return this.mDPM.getPasswordMinimumLength(null);
    }

    public PasswordPolicy getPasswordPolicy() {
        PasswordPolicy passwordPolicy;
        PasswordPolicy passwordPolicy2 = this.mPasswordPolicy;
        if (passwordPolicy2 != null) {
            return passwordPolicy2;
        }
        synchronized (this) {
            try {
                passwordPolicy = this.mPasswordPolicy;
                if (passwordPolicy == null) {
                    passwordPolicy = new PasswordPolicy(this.mContextInfo, this.mContext);
                    this.mPasswordPolicy = passwordPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return passwordPolicy;
    }

    public int getPasswordQuality() {
        return this.mDPM.getPasswordQuality(null);
    }

    public PhoneRestrictionPolicy getPhoneRestrictionPolicy() {
        return getPhoneRestrictionPolicy(null);
    }

    public ProfilePolicy getProfilePolicy() {
        ProfilePolicy profilePolicy;
        AccessController.throwIfParentInstance(this.mContextInfo, "getProfilePolicy");
        ProfilePolicy profilePolicy2 = this.mProfilePolicy;
        if (profilePolicy2 != null) {
            return profilePolicy2;
        }
        synchronized (this) {
            try {
                profilePolicy = this.mProfilePolicy;
                if (profilePolicy == null) {
                    profilePolicy = new ProfilePolicy(this.mContextInfo);
                    this.mProfilePolicy = profilePolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return profilePolicy;
    }

    public RemoteInjection getRemoteInjection() {
        RemoteInjection remoteInjection;
        AccessController.throwIfParentInstance(this.mContextInfo, "getRemoteInjection");
        RemoteInjection remoteInjection2 = this.mRemoteInjection;
        if (remoteInjection2 != null) {
            return remoteInjection2;
        }
        synchronized (this) {
            try {
                remoteInjection = this.mRemoteInjection;
                if (remoteInjection == null) {
                    remoteInjection = new RemoteInjection();
                    this.mRemoteInjection = remoteInjection;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return remoteInjection;
    }

    public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) {
        if (getService() != null) {
            try {
                this.mService.getRemoveWarning(componentName, remoteCallback);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public RestrictionPolicy getRestrictionPolicy() {
        RestrictionPolicy restrictionPolicy;
        RestrictionPolicy restrictionPolicy2 = this.mRestrictionPolicy;
        if (restrictionPolicy2 != null) {
            return restrictionPolicy2;
        }
        synchronized (this) {
            try {
                restrictionPolicy = this.mRestrictionPolicy;
                if (restrictionPolicy == null) {
                    restrictionPolicy = new RestrictionPolicy(this.mContextInfo, this.mContext);
                    this.mRestrictionPolicy = restrictionPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return restrictionPolicy;
    }

    public RoamingPolicy getRoamingPolicy() {
        RoamingPolicy roamingPolicy;
        RoamingPolicy roamingPolicy2 = this.mRoamingPolicy;
        if (roamingPolicy2 != null) {
            return roamingPolicy2;
        }
        synchronized (this) {
            try {
                roamingPolicy = this.mRoamingPolicy;
                if (roamingPolicy == null) {
                    roamingPolicy = new RoamingPolicy(this.mContextInfo);
                    this.mRoamingPolicy = roamingPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return roamingPolicy;
    }

    public SPDControlPolicy getSPDControlPolicy() {
        SPDControlPolicy sPDControlPolicy;
        SPDControlPolicy sPDControlPolicy2 = this.mSPDControlPolicy;
        if (sPDControlPolicy2 != null) {
            return sPDControlPolicy2;
        }
        synchronized (this) {
            try {
                sPDControlPolicy = this.mSPDControlPolicy;
                if (sPDControlPolicy == null) {
                    sPDControlPolicy = new SPDControlPolicy(this.mContextInfo, this.mContext);
                    this.mSPDControlPolicy = sPDControlPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sPDControlPolicy;
    }

    public final IEnterpriseDeviceManager getService() {
        if (this.mService == null) {
            this.mService = IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        }
        return this.mService;
    }

    public int getUserStatus(int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getUserStatus");
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getUserStatus(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return -1;
        }
    }

    public VpnPolicy getVpnPolicy() {
        VpnPolicy vpnPolicy;
        AccessController.throwIfParentInstance(this.mContextInfo, "getVpnPolicy");
        VpnPolicy vpnPolicy2 = this.mVpnPolicy;
        if (vpnPolicy2 != null) {
            return vpnPolicy2;
        }
        synchronized (this) {
            try {
                vpnPolicy = this.mVpnPolicy;
                if (vpnPolicy == null) {
                    vpnPolicy = new VpnPolicy(this.mContextInfo);
                    this.mVpnPolicy = vpnPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return vpnPolicy;
    }

    public WifiPolicy getWifiPolicy() {
        WifiPolicy wifiPolicy;
        WifiPolicy wifiPolicy2 = this.mWifiPolicy;
        if (wifiPolicy2 != null) {
            return wifiPolicy2;
        }
        synchronized (this) {
            try {
                wifiPolicy = this.mWifiPolicy;
                if (wifiPolicy == null) {
                    wifiPolicy = new WifiPolicy(this.mContextInfo);
                    this.mWifiPolicy = wifiPolicy;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return wifiPolicy;
    }

    public boolean hasAnyActiveAdmin() {
        if (getService() == null) {
            Log.w(TAG, "No EnterpriseDeviceManager service");
            return false;
        }
        try {
            return this.mService.hasAnyActiveAdmin();
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to get hasAnyActiveAdmin");
            return false;
        }
    }

    public boolean hasGrantedPolicy(ComponentName componentName, int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.hasGrantedPolicy(componentName, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean isActivePasswordSufficient() {
        return this.mDPM.isActivePasswordSufficient();
    }

    public boolean isAdminActive(ComponentName componentName) {
        AccessController.throwIfParentInstance(this.mContextInfo, "isAdminActive");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isAdminActive(componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean isAdminRemovable(ComponentName componentName) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isAdminRemovable(componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return true;
        }
    }

    public boolean isCallerValidKPU(ContextInfo contextInfo) {
        if (getService() == null) {
            Log.w(TAG, "Failed talking with enterprise policy service");
            return false;
        }
        try {
            return this.mService.isCallerValidKPU(contextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean isKPUPlatformSigned(String str, int i) {
        if (getService() == null) {
            Log.w(TAG, "Failed talking with enterprise policy service");
            return false;
        }
        try {
            return this.mService.isKPUPlatformSigned(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean isMdmAdminPresent() {
        if (getService() == null) {
            Log.w(TAG, "Failed talking with enterprise policy service");
            return false;
        }
        try {
            return this.mService.isMdmAdminPresent();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean isRestrictedByConstrainedState(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isRestrictedByConstrainedState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        } catch (Exception e2) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("Security exception occured! ", e2, TAG);
            return false;
        }
    }

    public boolean isUserSelectable(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isUserSelectable(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean keychainMarkedReset(ContextInfo contextInfo) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.keychainMarkedReset(contextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public void lockNow() {
        this.mDPM.lockNow();
    }

    public boolean migrateKnoxPoliciesForWpcod(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.migrateKnoxPoliciesForWpcod(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean packageHasActiveAdmins(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.packageHasActiveAdmins(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean packageHasActiveAdminsAsUser(String str, int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.packageHasActiveAdminsAsUser(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public String readUmcEnrollmentData() {
        if (getService() == null) {
            return null;
        }
        try {
            Log.d(TAG, "readUmcEnrollmentData");
            return this.mService.readUmcEnrollmentData(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public void removeActiveAdmin(ComponentName componentName) {
        if (getService() != null) {
            try {
                this.mService.removeActiveAdmin(componentName);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public void removeActiveAdminFromDpm(ComponentName componentName) {
        if (getService() != null) {
            try {
                this.mService.removeActiveAdminFromDpm(componentName, UserHandle.getCallingUserId());
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public boolean removeAuthorizedUid(int i, int i2) {
        if (getService() == null) {
            return false;
        }
        try {
            Log.d(TAG, "removeAuthorizedUid");
            return this.mService.removeAuthorizedUid(i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean resetPassword(String str, int i) {
        return this.mDPM.resetPassword(str, i);
    }

    public void setActiveAdmin(ComponentName componentName, boolean z) {
        if (getService() != null) {
            try {
                this.mService.setActiveAdmin(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public void setActiveAdminSilent(ComponentName componentName) {
        if (getService() != null) {
            try {
                this.mService.setActiveAdminSilent(componentName);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public boolean setAdminRemovable(boolean z, String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setAdminRemovable");
        if (getService() == null) {
            return false;
        }
        try {
            EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseDeviceManager.setAdminRemovable(boolean, String)");
            return this.mService.setAdminRemovable(this.mContextInfo, z, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public void setAndroidLogProperty(String str) {
        if (getService() == null) {
            Log.e(TAG, "Trying to set android log property but service was not started yet!");
            return;
        }
        try {
            this.mService.setAndroidLogProperty(str);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to setAndroidLogProperty");
        }
    }

    public void setMaximumFailedPasswordsForWipe(int i) {
        this.mDPM.setMaximumFailedPasswordsForWipe(getActiveAdminComponent(), i);
    }

    public void setMaximumTimeToLock(long j) {
        this.mDPM.setMaximumTimeToLock(getActiveAdminComponent(), j);
    }

    public void setPasswordMinimumLength(int i) {
        this.mDPM.setPasswordMinimumLength(getActiveAdminComponent(), i);
    }

    public void setPasswordQuality(int i) {
        this.mDPM.setPasswordQuality(getActiveAdminComponent(), i);
    }

    public void setUserSelectable(int i, String str, boolean z) {
        if (getService() != null) {
            try {
                this.mService.setUserSelectable(i, str, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public void startDualDARServices() {
        if (getService() == null) {
            Log.w(TAG, "Failed talking with enterprise policy service");
            return;
        }
        try {
            this.mService.startDualDARServices();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
        }
    }

    public void updateNotificationExemption(String str) {
        if (getService() != null) {
            try {
                Log.d(TAG, "updateNotificationExemption");
                this.mService.updateNotificationExemption(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public void wipeData(int i) {
        this.mDPM.wipeData(i);
    }

    public boolean writeUmcEnrollmentData(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            Log.d(TAG, "writeUmcEnrollmentData");
            return this.mService.writeUmcEnrollmentData(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public EnterpriseDeviceManager(Context context, ContextInfo contextInfo, Handler handler) {
        this(context, false, contextInfo);
    }

    public PhoneRestrictionPolicy getPhoneRestrictionPolicy(String str) {
        if (str == null) {
            str = "";
        }
        if (this.mPhoneRestrictionMap == null) {
            this.mPhoneRestrictionMap = new HashMap();
        }
        if (this.mPhoneRestrictionMap.containsKey(str)) {
            return this.mPhoneRestrictionMap.get(str);
        }
        PhoneRestrictionPolicy phoneRestrictionPolicy = new PhoneRestrictionPolicy(this.mContextInfo, str);
        this.mPhoneRestrictionMap.put(str, phoneRestrictionPolicy);
        return phoneRestrictionPolicy;
    }

    public EnterpriseDeviceManager(Context context, Handler handler, boolean z) {
        this(context, z, new ContextInfo(Process.myUid()));
    }

    public EnterpriseDeviceManager(Context context, Handler handler) {
        this(context);
    }

    public EnterpriseDeviceManager(Context context, Handler handler, boolean z, ContextInfo contextInfo) {
        this(context, z, contextInfo);
    }

    public boolean getAdminRemovable() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getAdminRemovable");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAdminRemovable(this.mContextInfo, null);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return true;
        }
    }

    private EnterpriseDeviceManager(Context context, boolean z, ContextInfo contextInfo) {
        this.mProfilePolicyMap = new HashMap<>();
        this.mContext = context;
        this.mDPM = (DevicePolicyManager) context.getSystemService("device_policy");
        if (z) {
            int callingUid = contextInfo.mCallerUid;
            int i = contextInfo.mContainerId;
            int i2 = contextInfo.mDALessCallerUid;
            if (callingUid == 1000 && (callingUid = Binder.getCallingUid()) != 1000) {
                KnoxsdkFileLog.d(TAG, "(" + Process.myPid() + "/" + Process.myTid() + ") callerUid is SYSTEM_UID but Binder.getCallingUid() returns " + callingUid, new Exception("STACK TRACE"));
            }
            contextInfo = new ContextInfo(callingUid, i, contextInfo.mParent, i2);
        }
        this.mContextInfo = contextInfo;
    }

    public void enforceActiveAdminPermission(List<String> list) throws SecurityException {
        if (getService() != null) {
            try {
                this.mService.enforceActiveAdminPermission(list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, List<String> list) throws SecurityException {
        return AccessController.enforceActiveAdminPermissionByContext(contextInfo, list);
    }

    public ContextInfo enforceContainerOwnerShipPermissionByContext(ContextInfo contextInfo, List<String> list) throws SecurityException {
        if (getService() != null) {
            try {
                return this.mService.enforceContainerOwnerShipPermissionByContext(contextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) throws SecurityException {
        return AccessController.enforceOwnerOnlyAndActiveAdminPermission(contextInfo, list);
    }

    public ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List<String> list) throws SecurityException {
        if (getService() != null) {
            try {
                return this.mService.enforcePermissionByContext(contextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public boolean setAdminRemovable(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setAdminRemovable");
        if (getService() == null) {
            return false;
        }
        try {
            EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseDeviceManager.setAdminRemovable(boolean)");
            return this.mService.setAdminRemovable(this.mContextInfo, z, null);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public static EnterpriseDeviceManager getParentInstance(Context context, int i) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        String packageName = context.getPackageName();
        if (packageName != null && packageName.equals("com.samsung.android.knox.kpecore")) {
            if (!AccessController.enforceWpcod()) {
                return null;
            }
            synchronized (mSync) {
                enterpriseDeviceManager = new EnterpriseDeviceManager(context, true, i);
                mParentInstance = enterpriseDeviceManager;
            }
            return enterpriseDeviceManager;
        }
        throw new SecurityException("Can only be called by com.samsung.android.knox.kpecore");
    }

    public static EnterpriseDeviceManager getInstance(Context context, int i) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        String packageName = context.getPackageName();
        if (packageName != null && packageName.equals("com.samsung.android.knox.kpecore")) {
            synchronized (mSync) {
                sEnterpriseDeviceManager = new EnterpriseDeviceManager(context, false, i);
                KnoxsdkFileLog.d(TAG, "getInstance() : (" + Process.myPid() + "/" + Process.myTid() + ") create an instance with UID " + Process.myUid());
                enterpriseDeviceManager = sEnterpriseDeviceManager;
            }
            return enterpriseDeviceManager;
        }
        throw new SecurityException("Can only be called by com.samsung.android.knox.kpecore");
    }

    public EnterpriseDeviceManager(Context context, boolean z) {
        this(context, true, new ContextInfo(Process.myUid(), z));
    }

    public EnterpriseDeviceManager(Context context, boolean z, int i) {
        this(context, true, new ContextInfo(Process.myUid(), z, i));
    }

    public EnterpriseDeviceManager(Context context, ContextInfo contextInfo, IEnterpriseDeviceManager iEnterpriseDeviceManager) {
        this(context, false, contextInfo);
        this.mService = iEnterpriseDeviceManager;
    }

    public void deactivateAdminForUser(ComponentName componentName, int i) {
    }

    public void activateAdminForUser(ComponentName componentName, boolean z, int i) {
    }
}
