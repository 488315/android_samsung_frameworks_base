package com.samsung.android.knox.container;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ExternalDependencyInjectorImpl;
import com.samsung.android.knox.IEnterpriseContainerCallback;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.accounts.DeviceAccountPolicy;
import com.samsung.android.knox.accounts.EmailAccountPolicy;
import com.samsung.android.knox.accounts.EmailPolicy;
import com.samsung.android.knox.accounts.ExchangeAccountPolicy;
import com.samsung.android.knox.accounts.LDAPAccountPolicy;
import com.samsung.android.knox.application.ApplicationPolicy;
import com.samsung.android.knox.browser.BrowserPolicy;
import com.samsung.android.knox.container.IKnoxContainerManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.datetime.DateTimePolicy;
import com.samsung.android.knox.ddar.DualDARPolicy;
import com.samsung.android.knox.deviceinfo.DeviceInventory;
import com.samsung.android.knox.devicesecurity.APMPolicy;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.keystore.CertificatePolicy;
import com.samsung.android.knox.keystore.CertificateProvisioning;
import com.samsung.android.knox.keystore.ClientCertificateManager;
import com.samsung.android.knox.keystore.EnterpriseCertEnrollPolicy;
import com.samsung.android.knox.kiosk.KioskMode;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.location.Geofencing;
import com.samsung.android.knox.location.LocationPolicy;
import com.samsung.android.knox.lockscreen.BootBanner;
import com.samsung.android.knox.lockscreen.LSOUtils;
import com.samsung.android.knox.log.AuditLog;
import com.samsung.android.knox.net.billing.EnterpriseBillingPolicy;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.nap.NetworkAnalytics;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.samsung.android.knox.restriction.AdvancedRestrictionPolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KnoxContainerManager {
    public static final String ACTION_CONTAINER_ADMIN_LOCK = "com.samsung.android.knox.intent.action.CONTAINER_ADMIN_LOCK";
    public static final String ACTION_CONTAINER_CREATION_STATUS = "com.samsung.android.knox.intent.action.CONTAINER_CREATION_STATUS";
    public static final String ACTION_CONTAINER_REMOVED = "com.samsung.android.knox.intent.action.CONTAINER_REMOVED";
    public static final String ACTION_CONTAINER_STATE_CHANGED = "com.samsung.android.knox.intent.action.CONTAINER_STATE_CHANGED";
    public static final String APP_SEPARATION_APP_LIST = "APP_SEPARATION_APP_LIST";
    public static final String APP_SEPARATION_COEXISTANCE_LIST = "APP_SEPARATION_COEXISTANCE_LIST";
    public static final String APP_SEPARATION_OUTSIDE = "APP_SEPARATION_OUTSIDE";
    public static final String CONFIGURATION_TYPE_DO_BASIC = "knox-do-basic";
    public static final String CONFIGURATION_TYPE_PO_BASIC = "knox-po-basic";
    public static final int CONTAINER_ACTIVE = 91;
    public static final String CONTAINER_CREATION_FAILED_SPECIFIC_ERROR_TYPE = "specificErrorCode";
    public static final int CONTAINER_CREATION_IN_PROGRESS = 93;
    public static final String CONTAINER_CREATION_REQUEST_ID = "requestId";
    public static final String CONTAINER_CREATION_STATUS_CODE = "code";
    public static final int CONTAINER_DOESNT_EXISTS = -1;
    public static final String CONTAINER_ID = "containerid";
    public static final int CONTAINER_INACTIVE = 90;
    public static final int CONTAINER_LAYOUT_TYPE_CLASSIC = 2;
    public static final int CONTAINER_LAYOUT_TYPE_FOLDER = 1;
    public static final int CONTAINER_LOCKED = 95;
    public static final String CONTAINER_NEW_STATE = "container_new_state";
    public static final String CONTAINER_OLD_STATE = "container_old_state";
    public static final int CONTAINER_REMOVE_IN_PROGRESS = 94;
    public static final int ERROR_ADMIN_ACTIVATION_FAILED = -1009;
    public static final int ERROR_ADMIN_INSTALLATION_FAILED = -1008;
    public static final int ERROR_CONTAINER_MODE_CREATION_FAILED_BYOD_NOT_ALLOWED = -1023;
    public static final int ERROR_CONTAINER_MODE_CREATION_FAILED_CONTAINER_EXIST = -1021;
    public static final int ERROR_CONTAINER_MODE_CREATION_FAILED_KIOSK_ON_OWNER_EXIST = -1022;
    public static final int ERROR_CONTAINER_TYPE_NOT_ALLOWED = -9999;
    public static final int ERROR_CREATION_ALREADY_IN_PROGRESS = -1016;
    public static final int ERROR_CREATION_CANCELLED = -1017;
    public static final int ERROR_CREATION_FAILED_CONTAINER_MODE_EXIST = -1020;
    public static final int ERROR_CREATION_FAILED_DO_EXISTS = -1201;
    public static final int ERROR_CREATION_FAILED_EMERGENCY_MODE = -1031;
    public static final int ERROR_CREATION_FAILED_GENERATE_CMK = -1034;
    public static final int ERROR_CREATION_FAILED_INVALID_KNOX_CONFIGURATION_TYPE = -1030;
    public static final int ERROR_CREATION_FAILED_INVALID_PARAM = -1026;
    public static final int ERROR_CREATION_FAILED_INVALID_PARAM_LIST = -1029;
    public static final int ERROR_CREATION_FAILED_INVALID_USER_INFO = -1032;
    public static final int ERROR_CREATION_FAILED_RESERVED_CONFIGURATION_TYPE_USED = -1028;
    public static final int ERROR_CREATION_FAILED_SUB_USER = -1027;
    public static final int ERROR_CREATION_FAILED_TIMA_DISABLED = -1018;
    public static final int ERROR_CREATION_FAILED_TIMA_PWD_KEY = -1033;
    public static final int ERROR_DOES_NOT_EXIST = -1202;
    public static final int ERROR_EC_MAX_LIMIT_REACHED = -1037;
    public static final int ERROR_FILESYSTEM_ERROR = -1011;
    public static final int ERROR_HANDLER_INSTALLATION_FAILED = -1006;
    public static final int ERROR_INTEGRITY_CHECK_FAILED = -1024;
    public static final int ERROR_INTERNAL_ERROR = -1014;
    public static final int ERROR_INVALID_PASSWORD_RESET_TOKEN = -1025;
    public static final int ERROR_KLMS_LICENCE_CHECK_ERROR = -1015;
    public static final int ERROR_MAX_LIMIT_REACHED = -1012;
    public static final int ERROR_NOT_CONTAINER_OWNER = -1203;
    public static final int ERROR_NO_ADMIN_APK = -1004;
    public static final int ERROR_NO_CONFIGURATION_TYPE = -1005;
    public static final int ERROR_NO_HANDLER_APK = -1002;
    public static final int ERROR_NO_NAME = -1001;
    public static final int ERROR_NO_SETUPWIZARD_APK = -1003;
    public static final int ERROR_PLATFORM_VERSION_MISMATCH_IN_CONFIGURATION_TYPE = -1019;
    public static final int ERROR_POLICY_ENFORCEMENT_FAILED = -1013;
    public static final int ERROR_REMOVE_FAILED = -1201;
    public static final int ERROR_SDP_NOTSUPPORTED = -1024;
    public static final int ERROR_SECURE_FOLDER_MAX_LIMIT_REACHED = -1036;
    public static final int ERROR_SETUPWIZARD_INSTALLATION_FAILED = -1007;
    public static final int ERROR_SYSTEM_APP_INSTALLATION_FAILED = -1010;
    public static final String EXTRA_ADMIN_UID = "com.samsung.knox.container.adminUid";
    public static final String EXTRA_CONFIG_TYPE = "com.samsung.knox.container.configType";
    public static final String EXTRA_CONTAINER_ID = "containerid";
    public static final String EXTRA_IS_CL_TYPE = "com.samsung.knox.container.isCLType";
    public static final String EXTRA_IS_MY_KNOX = "com.samsung.knox.container.isMyKnox";
    public static final String EXTRA_PWD_RST_TOKEN = "com.samsung.knox.container.pwdRstToken";
    public static final String EXTRA_REQUEST_ID = "com.samsung.knox.container.requestId";
    public static final String FEATURE_TYPE_MY_KNOX = "MY_KNOX";
    public static final int FLAG_ADMIN_TYPE_APK = 16;
    public static final int FLAG_ADMIN_TYPE_NONE = 64;
    public static final int FLAG_ADMIN_TYPE_PACKAGENAME = 32;
    public static final int FLAG_BASE = 1;
    public static final int FLAG_CREATOR_SELF_DESTROY = 8;
    public static final int FLAG_ECRYPT_FILESYSTEM = 2;
    public static final int FLAG_MIGRATION = 256;
    public static final int FLAG_SECURE_FOLDER_CONTAINER = 8192;
    public static final int FLAG_TIMA_STORAGE = 4;
    public static final String INTENT_BUNDLE = "intent";
    public static final String INTENT_CONTAINER_CREATION_STATUS = "com.samsung.knox.container.creation.status";
    public static final int MAX_CONTAINERS = 2;
    public static final int PROV_STATE_BASE = 0;
    public static final int PROV_STATE_CANCELLED = 12;
    public static final int PROV_STATE_FAILED = 11;
    public static final int PROV_STATE_FINISHED = 10;
    public static final int PROV_STATE_IDLE = 0;
    public static final int PROV_STATE_KNOXCORE_EXTENSION = 3;
    public static final int PROV_STATE_MANAGED_PROVISIONING = 2;
    public static final int PROV_STATE_REQUESTED = 1;
    public static final int PROV_STATE_SILENT_PROVISIONING = 2;
    public static final int REMOVE_CONTAINER_SUCCESS = 0;
    public static final String TAG = "KnoxContainerManager";
    public static final int TIMA_VALIDATION_SUCCESS_CODE = 0;
    public static final int TZ_COMMON_CLOSE_COMMUNICATION_ERROR = -65538;
    public static final int TZ_COMMON_COMMUNICATION_ERROR = -65537;
    public static final int TZ_COMMON_INIT_ERROR = -65546;
    public static final int TZ_COMMON_INIT_ERROR_TAMPER_FUSE_FAIL = -65548;
    public static final int TZ_COMMON_INIT_MSR_MISMATCH = -65549;
    public static final int TZ_COMMON_INIT_MSR_MODIFIED = -65550;
    public static final int TZ_COMMON_INIT_UNINITIALIZED_SECURE_MEM = -65547;
    public static final int TZ_COMMON_INTERNAL_ERROR = -65541;
    public static final int TZ_COMMON_NULL_POINTER_EXCEPTION = -65542;
    public static final int TZ_COMMON_RESPONSE_REQUEST_MISMATCH = -65539;
    public static final int TZ_COMMON_UNDEFINED_ERROR = -65543;
    public static final int TZ_KEYSTORE_ERROR = -1;
    public static final int TZ_KEYSTORE_INIT_FAILED = -2;
    public static IKnoxContainerManager mContainerService;
    public volatile APMPolicy mAPMPolicy;
    public volatile AdvancedRestrictionPolicy mAdvancedRestrictionPolicy;
    public volatile ApplicationPolicy mApplicationPolicy;
    public volatile AuditLog mAuditLogPolicy;
    public volatile BasePasswordPolicy mBasePasswordPolicy;
    public volatile BootBanner mBootBanner;
    public volatile BrowserPolicy mBrowserPolicy;
    public volatile CertificatePolicy mCertificatePolicy;
    public volatile CertificateProvisioning mCertificateProvisioning;
    public volatile ClientCertificateManager mClientCertificateManagerPolicy;
    public ContainerConfigurationPolicy mContainerConfigurationPolicy;
    public final Context mContext;
    public final ContextInfo mContextInfo;
    public volatile DateTimePolicy mDateTimePolicy;
    public volatile DeviceAccountPolicy mDeviceAccountPolicy;
    public volatile DeviceInventory mDeviceInventory;
    public volatile DualDARPolicy mDualDARPolicy;
    public volatile ExchangeAccountPolicy mEasAccountPolicy;
    public volatile EmailAccountPolicy mEmailAccountPolicy;
    public volatile EmailPolicy mEmailPolicy;
    public EnterpriseBillingPolicy mEnterpriseBillingPolicy;
    public volatile Firewall mFirewall;
    public volatile Geofencing mGeofencing;
    public volatile KioskMode mKioskMode;
    public volatile LDAPAccountPolicy mLDAPAccountPolicy;
    public volatile LocationPolicy mLocationPolicy;
    public NetworkAnalytics mNap;
    public volatile PasswordPolicy mPasswordPolicy;
    public RCPPolicy mRCPPolicy;
    public volatile RestrictionPolicy mRestrictionPolicy;
    public volatile WifiPolicy mWifiPolicy;
    public volatile boolean mEnterpriseBillingPolicyCreated = false;
    public boolean mNAPCreated = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ConfigType {
        LIGHTWEIGHT("lightweight"),
        KIOSK("kiosk"),
        LAUNCHER("launcher"),
        BBC("bbc"),
        SECUREFOLDER("securefolder");

        private final String mTypeString;

        ConfigType(String str) {
            this.mTypeString = str;
        }

        public final ConfigType getType(String str) {
            for (ConfigType configType : values()) {
                if (configType.mTypeString.equals(str)) {
                    return configType;
                }
            }
            return null;
        }

        @Override // java.lang.Enum
        public final String toString() {
            return this.mTypeString;
        }
    }

    public KnoxContainerManager(Context context, ContextInfo contextInfo) {
        try {
            if (checkContainerType(contextInfo.mContainerId, 32)) {
                this.mContextInfo = contextInfo;
                this.mContext = context;
            } else {
                throw new NoSuchFieldException("Container with Id " + contextInfo.mContainerId + " does not exists");
            }
        } catch (Exception e) {
            throw new NoSuchFieldException("Container with Id " + contextInfo.mContainerId + " does not exists. / " + e);
        }
    }

    public static boolean addConfigurationType(Context context, KnoxConfigurationType knoxConfigurationType) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "KnoxContainerManager.addConfigurationType");
        return addConfigurationType(context, null, knoxConfigurationType);
    }

    public static boolean cancelCreateContainer(ContainerCreationParams containerCreationParams) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return containerService.cancelCreateContainer(containerCreationParams);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API cancelCreateContainer "), TAG);
            return false;
        }
    }

    public static int checkProvisioningPreCondition(String str, int i) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return ERROR_INTERNAL_ERROR;
        }
        try {
            return containerService.checkProvisioningPreCondition(str, i);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API getProvisioningCondition "), TAG);
            return 0;
        }
    }

    public static int createContainer(String str, String str2) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "KnoxContainerManager.createContainer(String, String)");
        return createContainer((ContextInfo) null, str, str2);
    }

    public static int createContainerForMigration(ContextInfo contextInfo, String str) {
        IKnoxContainerManager containerService = getContainerService();
        int i = ERROR_INTERNAL_ERROR;
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return ERROR_INTERNAL_ERROR;
        }
        CreationParams creationParams = new CreationParams();
        creationParams.mConfigName = str;
        try {
            i = containerService.createContainer(contextInfo, creationParams, 326);
            return processCreateReturn(i);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API createContainerForMigration "), TAG);
            return i;
        }
    }

    public static int createContainerInternal(ContainerCreationParams containerCreationParams) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return ERROR_INTERNAL_ERROR;
        }
        try {
            return containerService.createContainerInternal(containerCreationParams);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API createContainerInternal "), TAG);
            return ERROR_INTERNAL_ERROR;
        }
    }

    public static boolean createContainerMarkSuccess(ContainerCreationParams containerCreationParams) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return containerService.createContainerMarkSuccess(containerCreationParams);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API createContainerMarkSuccess "), TAG);
            return false;
        }
    }

    public static void doSelfUninstall() {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return;
        }
        try {
            containerService.doSelfUninstall();
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API getContainers :"), TAG);
        }
    }

    public static Bundle getAppSeparationConfig() {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            return null;
        }
        try {
            return containerService.getAppSeparationConfig();
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed to call isAppSeparationEnabled "), TAG);
            return null;
        }
    }

    public static KnoxConfigurationType getConfigurationType(int i) {
        return getConfigurationType(null, i);
    }

    public static KnoxConfigurationType getConfigurationTypeByName(String str) {
        return getConfigurationTypeByName(null, str);
    }

    public static List<KnoxConfigurationType> getConfigurationTypes() {
        return getConfigurationTypes(null);
    }

    public static ContainerCreationParams getContainerCreationParams(int i) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return null;
        }
        try {
            return containerService.getContainerCreationParams(i);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API getConfigurationType by id:"), TAG);
            return null;
        }
    }

    public static synchronized IKnoxContainerManager getContainerService() {
        IKnoxContainerManager iKnoxContainerManager;
        synchronized (KnoxContainerManager.class) {
            if (mContainerService == null) {
                mContainerService = IKnoxContainerManager.Stub.asInterface(ServiceManager.getService("mum_container_policy"));
            }
            iKnoxContainerManager = mContainerService;
        }
        return iKnoxContainerManager;
    }

    public static List<Integer> getContainers() {
        return getContainers(null);
    }

    public static String getCustomResource(int i, String str) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return null;
        }
        try {
            return containerService.getCustomResource(i, str);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API getCustomResource: "), TAG);
            return null;
        }
    }

    public static List<KnoxConfigurationType> getDefaultConfigurationTypes() {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return null;
        }
        try {
            return containerService.getDefaultConfigurationTypes();
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API getConfigurationType by id:"), TAG);
            return null;
        }
    }

    public static Bundle getProvisioningState() {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return new Bundle();
        }
        try {
            return containerService.getProvisioningState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API getProvisioningState " + Log.getStackTraceString(e));
            return new Bundle();
        }
    }

    public static boolean isEmergencyModeSupported() {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return true;
        }
        try {
            return containerService.isEmergencyModeSupported();
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API isEmergencyModeSupported "), TAG);
            return true;
        }
    }

    public static int processCreateReturn(int i) {
        if (i == -1013 || i == -1010 || i == -1007 || i == -1006) {
            return ERROR_INTERNAL_ERROR;
        }
        switch (i) {
            case ERROR_NO_SETUPWIZARD_APK /* -1003 */:
            case ERROR_NO_HANDLER_APK /* -1002 */:
            case ERROR_NO_NAME /* -1001 */:
                return ERROR_INTERNAL_ERROR;
            default:
                return i;
        }
    }

    public static void processNewTypeObject(Context context, KnoxConfigurationType knoxConfigurationType) {
        Log.d(TAG, "Images before copy:" + knoxConfigurationType.getCustomBadgeIcon() + " " + knoxConfigurationType.getCustomHomeScreenWallpaper() + " " + knoxConfigurationType.getCustomLockScreenWallpaper() + " " + knoxConfigurationType.getCustomStatusIcon());
        StringBuilder sb = new StringBuilder("Images value conditions:");
        sb.append((knoxConfigurationType.getCustomBadgeIcon() == null || knoxConfigurationType.getCustomBadgeIcon().equals("")) ? false : true);
        sb.append(" ");
        sb.append((knoxConfigurationType.getCustomHomeScreenWallpaper() == null || knoxConfigurationType.getCustomHomeScreenWallpaper().equals("")) ? false : true);
        sb.append(" ");
        sb.append((knoxConfigurationType.getCustomLockScreenWallpaper() == null || knoxConfigurationType.getCustomLockScreenWallpaper().equals("")) ? false : true);
        sb.append(" ");
        sb.append((knoxConfigurationType.getCustomStatusIcon() == null || knoxConfigurationType.getCustomStatusIcon().equals("")) ? false : true);
        Log.d(TAG, sb.toString());
        String str = null;
        String copyFileToDataLocalDirectory = (knoxConfigurationType.getCustomBadgeIcon() == null || knoxConfigurationType.getCustomBadgeIcon().equals("")) ? null : LSOUtils.copyFileToDataLocalDirectory(context, knoxConfigurationType.getCustomBadgeIcon(), "icon");
        String copyFileToDataLocalDirectory2 = (knoxConfigurationType.getCustomStatusIcon() == null || knoxConfigurationType.getCustomStatusIcon().equals("")) ? null : LSOUtils.copyFileToDataLocalDirectory(context, knoxConfigurationType.getCustomStatusIcon(), "icon");
        knoxConfigurationType.setCustomBadgeIcon(copyFileToDataLocalDirectory);
        knoxConfigurationType.setCustomStatusIcon(copyFileToDataLocalDirectory2);
        if (knoxConfigurationType instanceof LightweightConfigurationType) {
            LightweightConfigurationType lightweightConfigurationType = (LightweightConfigurationType) knoxConfigurationType;
            String str2 = lightweightConfigurationType.mFolderHeaderIcon;
            AbstractC0000x2c234b15.m3m("folder header icon: ", str2, TAG);
            if (str2 != null && !str2.isEmpty()) {
                str = LSOUtils.copyFileToDataLocalDirectory(context, str2, "icon");
            }
            AbstractC0000x2c234b15.m3m("folder header icon after copy: ", str, TAG);
            lightweightConfigurationType.mFolderHeaderIcon = str;
        }
        Log.d(TAG, "Images after copy:" + knoxConfigurationType.getCustomBadgeIcon() + " " + knoxConfigurationType.getCustomHomeScreenWallpaper() + " " + knoxConfigurationType.getCustomLockScreenWallpaper() + " " + knoxConfigurationType.getCustomStatusIcon());
    }

    public static int processRemoveReturn(int i) {
        return i != -1201 ? i : ERROR_INTERNAL_ERROR;
    }

    public static boolean removeConfigurationType(String str) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "KnoxContainerManager.removeConfigurationType");
        return removeConfigurationType(null, str);
    }

    public static int removeContainer(int i) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "KnoxContainerManager.removeContainer");
        return removeContainer(new ContextInfo(Process.myUid(), i));
    }

    public static int removeContainerInternal(int i) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return ERROR_INTERNAL_ERROR;
        }
        try {
            return containerService.removeContainerInternal(i);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API removeContainerInternal "), TAG);
            return ERROR_INTERNAL_ERROR;
        }
    }

    public static boolean setAppSeparationCoexistentApps(List<String> list) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            return false;
        }
        try {
            return containerService.setAppSeparationCoexistentApps(new ContextInfo(Process.myUid()), list);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed to call setAppSeparationCoexistentApps "), TAG);
            return false;
        }
    }

    public static boolean setAppSeparationConfig(Bundle bundle) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            return false;
        }
        try {
            return containerService.setAppSeparationConfig(new ContextInfo(Process.myUid()), bundle);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed to call setAppSeparationWhiteList "), TAG);
            return false;
        }
    }

    public static boolean setAppSeparationWhitelistedApps(List<String> list) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            return false;
        }
        try {
            return containerService.setAppSeparationWhitelistedApps(new ContextInfo(Process.myUid()), list);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed to call setAppSeparationWhitelistedApps "), TAG);
            return false;
        }
    }

    public static boolean updateProvisioningState(Bundle bundle) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return containerService.updateProvisioningState(bundle);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API updateProvisioningState "), TAG);
            return false;
        }
    }

    public final boolean activateDevicePermissions(List<String> list) {
        try {
            return IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy")).activateDevicePermissions(list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public final boolean checkContainerType(int i, int i2) {
        String str = SystemProperties.get("persist.sys.knox.userinfo");
        if (str != null && str.length() > 0) {
            for (String str2 : str.split(":")) {
                String[] split = str2.split(",");
                if (split != null && split.length == 2) {
                    int parseInt = Integer.parseInt(split[0]);
                    int parseInt2 = Integer.parseInt(split[1]);
                    if (parseInt == i && (parseInt2 & i2) > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void enforceMultifactorAuthentication(boolean z) {
        boolean z2;
        EnterpriseLicenseManager.log(this.mContextInfo, "KnoxContainerManager.enforceMultifactorAuthentication");
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return;
        }
        try {
            z2 = containerService.enforceMultifactorAuthentication(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API unlock ", e);
            z2 = false;
        }
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("enforceMultifactorAuthentication result = ", z2, TAG);
    }

    public final APMPolicy getAPMPolicy() {
        if (this.mAPMPolicy == null) {
            synchronized (this) {
                if (this.mAPMPolicy == this.mAPMPolicy) {
                    this.mAPMPolicy = new APMPolicy(this.mContextInfo);
                }
            }
        }
        return this.mAPMPolicy;
    }

    public final AdvancedRestrictionPolicy getAdvancedRestrictionPolicy() {
        AdvancedRestrictionPolicy advancedRestrictionPolicy = this.mAdvancedRestrictionPolicy;
        if (advancedRestrictionPolicy == null) {
            synchronized (this) {
                advancedRestrictionPolicy = this.mAdvancedRestrictionPolicy;
                if (advancedRestrictionPolicy == null) {
                    advancedRestrictionPolicy = new AdvancedRestrictionPolicy(this.mContextInfo, this.mContext);
                    this.mAdvancedRestrictionPolicy = advancedRestrictionPolicy;
                }
            }
        }
        return advancedRestrictionPolicy;
    }

    public final ApplicationPolicy getApplicationPolicy() {
        ApplicationPolicy applicationPolicy = this.mApplicationPolicy;
        if (applicationPolicy == null) {
            synchronized (this) {
                applicationPolicy = this.mApplicationPolicy;
                if (applicationPolicy == null) {
                    applicationPolicy = new ApplicationPolicy(this.mContextInfo, this.mContext, new ExternalDependencyInjectorImpl());
                    this.mApplicationPolicy = applicationPolicy;
                }
            }
        }
        return applicationPolicy;
    }

    public final AuditLog getAuditLogPolicy() {
        if (KnoxInternalFeature.KNOX_CONFIG_VERSION < 14) {
            Log.i(TAG, "KnoxContainerManager.getAuditLogPolicy() : This device doesn't support this API.");
            return null;
        }
        if (this.mAuditLogPolicy == null) {
            synchronized (this) {
                if (this.mAuditLogPolicy == null) {
                    this.mAuditLogPolicy = AuditLog.createInstance(this.mContextInfo, this.mContext);
                }
            }
        }
        return this.mAuditLogPolicy;
    }

    public final BasePasswordPolicy getBasePasswordPolicy() {
        BasePasswordPolicy basePasswordPolicy = this.mBasePasswordPolicy;
        if (basePasswordPolicy == null) {
            synchronized (this) {
                basePasswordPolicy = this.mBasePasswordPolicy;
                if (basePasswordPolicy == null) {
                    basePasswordPolicy = new BasePasswordPolicy(this.mContextInfo);
                    this.mBasePasswordPolicy = basePasswordPolicy;
                }
            }
        }
        return basePasswordPolicy;
    }

    public final BootBanner getBootBanner() {
        BootBanner bootBanner = this.mBootBanner;
        if (bootBanner == null) {
            synchronized (this) {
                bootBanner = this.mBootBanner;
                if (bootBanner == null) {
                    bootBanner = new BootBanner(this.mContextInfo);
                    this.mBootBanner = bootBanner;
                }
            }
        }
        return bootBanner;
    }

    public final BrowserPolicy getBrowserPolicy() {
        BrowserPolicy browserPolicy = this.mBrowserPolicy;
        if (browserPolicy == null) {
            synchronized (this) {
                browserPolicy = this.mBrowserPolicy;
                if (browserPolicy == null) {
                    browserPolicy = new BrowserPolicy(this.mContextInfo);
                    this.mBrowserPolicy = browserPolicy;
                }
            }
        }
        return browserPolicy;
    }

    public final CertificatePolicy getCertificatePolicy() {
        CertificatePolicy certificatePolicy = this.mCertificatePolicy;
        if (certificatePolicy == null) {
            synchronized (this) {
                certificatePolicy = this.mCertificatePolicy;
                if (certificatePolicy == null) {
                    certificatePolicy = new CertificatePolicy(this.mContextInfo);
                    this.mCertificatePolicy = certificatePolicy;
                }
            }
        }
        return certificatePolicy;
    }

    public final CertificateProvisioning getCertificateProvisioning() {
        CertificateProvisioning certificateProvisioning = this.mCertificateProvisioning;
        if (certificateProvisioning == null) {
            synchronized (this) {
                certificateProvisioning = this.mCertificateProvisioning;
                if (certificateProvisioning == null) {
                    certificateProvisioning = new CertificateProvisioning(this.mContextInfo);
                    this.mCertificateProvisioning = certificateProvisioning;
                }
            }
        }
        return certificateProvisioning;
    }

    public final ClientCertificateManager getClientCertificateManagerPolicy() {
        ClientCertificateManager clientCertificateManager = this.mClientCertificateManagerPolicy;
        if (clientCertificateManager == null) {
            synchronized (this) {
                clientCertificateManager = this.mClientCertificateManagerPolicy;
                if (clientCertificateManager == null) {
                    clientCertificateManager = new ClientCertificateManager(this.mContextInfo);
                    this.mClientCertificateManagerPolicy = clientCertificateManager;
                }
            }
        }
        return clientCertificateManager;
    }

    public final ContainerConfigurationPolicy getContainerConfigurationPolicy() {
        ContainerConfigurationPolicy containerConfigurationPolicy = this.mContainerConfigurationPolicy;
        if (containerConfigurationPolicy == null) {
            synchronized (this) {
                containerConfigurationPolicy = this.mContainerConfigurationPolicy;
                if (containerConfigurationPolicy == null) {
                    containerConfigurationPolicy = new ContainerConfigurationPolicy(this.mContextInfo);
                    this.mContainerConfigurationPolicy = containerConfigurationPolicy;
                }
            }
        }
        return containerConfigurationPolicy;
    }

    public final DateTimePolicy getDateTimePolicy() {
        DateTimePolicy dateTimePolicy = this.mDateTimePolicy;
        if (dateTimePolicy == null) {
            synchronized (this) {
                dateTimePolicy = this.mDateTimePolicy;
                if (dateTimePolicy == null) {
                    dateTimePolicy = new DateTimePolicy(this.mContextInfo);
                    this.mDateTimePolicy = dateTimePolicy;
                }
            }
        }
        return dateTimePolicy;
    }

    public final DeviceAccountPolicy getDeviceAccountPolicy() {
        DeviceAccountPolicy deviceAccountPolicy = this.mDeviceAccountPolicy;
        if (deviceAccountPolicy == null) {
            synchronized (this) {
                deviceAccountPolicy = this.mDeviceAccountPolicy;
                if (deviceAccountPolicy == null) {
                    deviceAccountPolicy = new DeviceAccountPolicy(this.mContextInfo);
                    this.mDeviceAccountPolicy = deviceAccountPolicy;
                }
            }
        }
        return deviceAccountPolicy;
    }

    public final DualDARPolicy getDualDARPolicy() {
        DualDARPolicy dualDARPolicy = this.mDualDARPolicy;
        if (dualDARPolicy == null) {
            synchronized (this) {
                dualDARPolicy = this.mDualDARPolicy;
                if (dualDARPolicy == null) {
                    dualDARPolicy = new DualDARPolicy(this.mContextInfo);
                    this.mDualDARPolicy = dualDARPolicy;
                }
            }
        }
        return dualDARPolicy;
    }

    public final EmailAccountPolicy getEmailAccountPolicy() {
        EmailAccountPolicy emailAccountPolicy = this.mEmailAccountPolicy;
        if (emailAccountPolicy == null) {
            synchronized (this) {
                emailAccountPolicy = this.mEmailAccountPolicy;
                if (emailAccountPolicy == null) {
                    emailAccountPolicy = new EmailAccountPolicy(this.mContextInfo);
                    this.mEmailAccountPolicy = emailAccountPolicy;
                }
            }
        }
        return emailAccountPolicy;
    }

    public final EmailPolicy getEmailPolicy() {
        EmailPolicy emailPolicy = this.mEmailPolicy;
        if (emailPolicy == null) {
            synchronized (this) {
                emailPolicy = this.mEmailPolicy;
                if (emailPolicy == null) {
                    emailPolicy = new EmailPolicy(this.mContextInfo);
                    this.mEmailPolicy = emailPolicy;
                }
            }
        }
        return emailPolicy;
    }

    public final EnterpriseBillingPolicy getEnterpriseBillingPolicy() {
        if (!this.mEnterpriseBillingPolicyCreated) {
            synchronized (EnterpriseBillingPolicy.class) {
                if (!this.mEnterpriseBillingPolicyCreated) {
                    EnterpriseBillingPolicy enterpriseBillingPolicy = new EnterpriseBillingPolicy(this.mContextInfo);
                    this.mEnterpriseBillingPolicy = enterpriseBillingPolicy;
                    Objects.toString(enterpriseBillingPolicy);
                    this.mEnterpriseBillingPolicyCreated = true;
                }
            }
        }
        return this.mEnterpriseBillingPolicy;
    }

    public final EnterpriseCertEnrollPolicy getEnterpriseCertEnrollPolicy(String str) {
        return null;
    }

    public final ExchangeAccountPolicy getExchangeAccountPolicy() {
        ExchangeAccountPolicy exchangeAccountPolicy = this.mEasAccountPolicy;
        if (exchangeAccountPolicy == null) {
            synchronized (this) {
                exchangeAccountPolicy = this.mEasAccountPolicy;
                if (exchangeAccountPolicy == null) {
                    exchangeAccountPolicy = new ExchangeAccountPolicy(this.mContextInfo);
                    this.mEasAccountPolicy = exchangeAccountPolicy;
                }
            }
        }
        return exchangeAccountPolicy;
    }

    public final Firewall getFirewall() {
        Firewall firewall = this.mFirewall;
        if (firewall == null) {
            synchronized (this) {
                firewall = this.mFirewall;
                if (firewall == null) {
                    firewall = new Firewall(this.mContextInfo);
                    this.mFirewall = firewall;
                }
            }
        }
        return firewall;
    }

    public final Geofencing getGeofencing() {
        Geofencing geofencing = this.mGeofencing;
        if (geofencing == null) {
            synchronized (this) {
                geofencing = this.mGeofencing;
                if (geofencing == null) {
                    geofencing = Geofencing.getInstance(this.mContextInfo, this.mContext);
                    this.mGeofencing = geofencing;
                }
            }
        }
        return geofencing;
    }

    public final KioskMode getKioskMode() {
        KioskMode kioskMode = this.mKioskMode;
        if (kioskMode == null) {
            synchronized (this) {
                kioskMode = this.mKioskMode;
                if (kioskMode == null) {
                    kioskMode = KioskMode.getInstance(this.mContextInfo, this.mContext);
                    this.mKioskMode = kioskMode;
                }
            }
        }
        return kioskMode;
    }

    public final List<String> getKnoxCustomBadgePolicy() {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            return null;
        }
        try {
            return containerService.getKnoxCustomBadgePolicy();
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API getKnoxCustomBadgePolicy "), TAG);
            return null;
        }
    }

    public final LDAPAccountPolicy getLDAPAccountPolicy() {
        LDAPAccountPolicy lDAPAccountPolicy = this.mLDAPAccountPolicy;
        if (lDAPAccountPolicy == null) {
            synchronized (this) {
                lDAPAccountPolicy = this.mLDAPAccountPolicy;
                if (lDAPAccountPolicy == null) {
                    lDAPAccountPolicy = new LDAPAccountPolicy(this.mContextInfo, this.mContext);
                    this.mLDAPAccountPolicy = lDAPAccountPolicy;
                }
            }
        }
        return lDAPAccountPolicy;
    }

    public final LocationPolicy getLocationPolicy() {
        LocationPolicy locationPolicy = this.mLocationPolicy;
        if (locationPolicy == null) {
            synchronized (this) {
                locationPolicy = this.mLocationPolicy;
                if (locationPolicy == null) {
                    locationPolicy = new LocationPolicy(this.mContextInfo);
                    this.mLocationPolicy = locationPolicy;
                }
            }
        }
        return locationPolicy;
    }

    public final NetworkAnalytics getNetworkAnalytics() {
        synchronized (NetworkAnalytics.class) {
            if (!this.mNAPCreated) {
                this.mNap = NetworkAnalytics.getInstance(this.mContextInfo, this.mContext);
                this.mNAPCreated = true;
            }
        }
        return this.mNap;
    }

    public final PasswordPolicy getPasswordPolicy() {
        PasswordPolicy passwordPolicy = this.mPasswordPolicy;
        if (passwordPolicy == null) {
            synchronized (this) {
                passwordPolicy = this.mPasswordPolicy;
                if (passwordPolicy == null) {
                    passwordPolicy = new PasswordPolicy(this.mContextInfo, this.mContext);
                    this.mPasswordPolicy = passwordPolicy;
                }
            }
        }
        return passwordPolicy;
    }

    public final RCPPolicy getRCPPolicy() {
        RCPPolicy rCPPolicy = this.mRCPPolicy;
        if (rCPPolicy == null) {
            synchronized (this) {
                rCPPolicy = this.mRCPPolicy;
                if (rCPPolicy == null) {
                    rCPPolicy = new RCPPolicy(this.mContextInfo);
                    this.mRCPPolicy = rCPPolicy;
                }
            }
        }
        return rCPPolicy;
    }

    public final RestrictionPolicy getRestrictionPolicy() {
        RestrictionPolicy restrictionPolicy = this.mRestrictionPolicy;
        if (restrictionPolicy == null) {
            synchronized (this) {
                restrictionPolicy = this.mRestrictionPolicy;
                if (restrictionPolicy == null) {
                    restrictionPolicy = new RestrictionPolicy(this.mContextInfo, this.mContext);
                    this.mRestrictionPolicy = restrictionPolicy;
                }
            }
        }
        return restrictionPolicy;
    }

    public final int getStatus() {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return -1;
        }
        try {
            return containerService.getStatus(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API getStatus(" + this.mContextInfo.mContainerId + ") :" + Log.getStackTraceString(e));
            return -1;
        }
    }

    public final WifiPolicy getWifiPolicy() {
        WifiPolicy wifiPolicy = this.mWifiPolicy;
        if (wifiPolicy == null) {
            synchronized (this) {
                wifiPolicy = this.mWifiPolicy;
                if (wifiPolicy == null) {
                    wifiPolicy = new WifiPolicy(this.mContextInfo);
                    this.mWifiPolicy = wifiPolicy;
                }
            }
        }
        return wifiPolicy;
    }

    public final boolean isMultifactorAuthenticationEnforced() {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return containerService.isMultifactorAuthenticationEnforced(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API unlock ", e);
            return false;
        }
    }

    public final boolean lock() {
        EnterpriseLicenseManager.log(this.mContextInfo, "KnoxContainerManager.lock");
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return containerService.lockContainer(this.mContextInfo, null);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API lock ", e);
            return false;
        }
    }

    public final boolean registerBroadcastReceiverIntent(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KnoxContainerManager.registerBroadcastReceiverIntent");
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return containerService.registerBroadcastReceiverIntent(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API registerBroadcastReceiverIntent ", e);
            return false;
        }
    }

    public final boolean unlock() {
        EnterpriseLicenseManager.log(this.mContextInfo, "KnoxContainerManager.unlock");
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return containerService.unlockContainer(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API unlock ", e);
            return false;
        }
    }

    public final boolean unregisterBroadcastReceiverIntent(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KnoxContainerManager.unregisterBroadcastReceiverIntent");
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return containerService.unregisterBroadcastReceiverIntent(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API unregisterBroadcastReceiverIntent ", e);
            return false;
        }
    }

    public static KnoxConfigurationType getConfigurationType(ContextInfo contextInfo, int i) {
        List list;
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return null;
        }
        try {
            list = containerService.getConfigurationType(contextInfo, i);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API getConfigurationType by id:"), TAG);
            list = null;
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        return (KnoxConfigurationType) list.get(0);
    }

    public static KnoxConfigurationType getConfigurationTypeByName(ContextInfo contextInfo, String str) {
        List list;
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return null;
        }
        try {
            list = containerService.getConfigurationTypeByName(contextInfo, str);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("Failed at KnoxContainerManager API getContainer(", str, ") :"), TAG);
            list = null;
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        return (KnoxConfigurationType) list.get(0);
    }

    public static List<KnoxConfigurationType> getConfigurationTypes(ContextInfo contextInfo) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return null;
        }
        try {
            return containerService.getConfigurationTypes(contextInfo);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API getConfigurationType:"), TAG);
            return null;
        }
    }

    public static List<Integer> getContainers(ContextInfo contextInfo) {
        ArrayList arrayList = new ArrayList();
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return arrayList;
        }
        try {
            return containerService.getContainers(contextInfo);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API getContainers :"), TAG);
            return arrayList;
        }
    }

    public static boolean addConfigurationType(Context context, ContextInfo contextInfo, KnoxConfigurationType knoxConfigurationType) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return false;
        }
        if (knoxConfigurationType == null) {
            Log.e(TAG, "type object is NULL!!, returning..");
            return false;
        }
        if (context == null) {
            Log.e(TAG, "Context is NULL!!, returning..");
            return false;
        }
        try {
            knoxConfigurationType.dumpState();
            processNewTypeObject(context, knoxConfigurationType);
            return containerService.addConfigurationType(contextInfo, Arrays.asList(knoxConfigurationType));
        } catch (RemoteException e) {
            Log.e(TAG, "Failed at KnoxContainerManager API addContainer:");
            Log.e(TAG, Log.getStackTraceString(e));
            return false;
        }
    }

    public static int createContainer(ContextInfo contextInfo, String str, String str2) {
        IKnoxContainerManager containerService = getContainerService();
        int i = ERROR_INTERNAL_ERROR;
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return ERROR_INTERNAL_ERROR;
        }
        CreationParams creationParams = new CreationParams();
        creationParams.mConfigName = str;
        creationParams.mAdminPkgName = str2;
        try {
            i = containerService.createContainer(contextInfo, creationParams, str.equals("secure-folder") ? 8238 : 46);
            return processCreateReturn(i);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API createContainer "), TAG);
            return i;
        }
    }

    public static boolean removeConfigurationType(ContextInfo contextInfo, String str) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return false;
        }
        if (str == null) {
            Log.e(TAG, "type string is NULL!!, returning..");
            return false;
        }
        try {
            return containerService.removeConfigurationType(contextInfo, str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed at KnoxContainerManager API removeConfigurationType:");
            Log.e(TAG, Log.getStackTraceString(e));
            return false;
        }
    }

    public static int removeContainer(ContextInfo contextInfo) {
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return -1201;
        }
        try {
            return containerService.removeContainer(contextInfo);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API removeContainer "), TAG);
            return -1201;
        }
    }

    public final boolean lock(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KnoxContainerManager.lock");
        IKnoxContainerManager containerService = getContainerService();
        if (containerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return containerService.lockContainer(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API lock ", e);
            return false;
        }
    }

    public static int createContainerForMigration(ContextInfo contextInfo, String str, Uri uri) {
        IKnoxContainerManager containerService = getContainerService();
        int i = ERROR_INTERNAL_ERROR;
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return ERROR_INTERNAL_ERROR;
        }
        CreationParams creationParams = new CreationParams();
        creationParams.mConfigName = str;
        creationParams.mAdminPkgName = uri.toString();
        try {
            i = containerService.createContainer(contextInfo, creationParams, IKnoxCustomManager.Stub.TRANSACTION_stopProKioskMode);
            return processCreateReturn(i);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API createContainerForMigration "), TAG);
            return i;
        }
    }

    public static int createContainer(CreationParams creationParams) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "KnoxContainerManager.createContainer(CreationParams)");
        return createContainer((ContextInfo) null, creationParams);
    }

    public static int createContainer(ContextInfo contextInfo, CreationParams creationParams) {
        IKnoxContainerManager containerService = getContainerService();
        int i = ERROR_INTERNAL_ERROR;
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return ERROR_INTERNAL_ERROR;
        }
        if (creationParams == null) {
            return ERROR_INTERNAL_ERROR;
        }
        String str = creationParams.mPwdResetToken;
        if (str == null || str.isEmpty()) {
            return ERROR_INVALID_PASSWORD_RESET_TOKEN;
        }
        try {
            i = containerService.createContainer(contextInfo, creationParams, creationParams.mAdminPkgName == null ? 70 : 46);
            return processCreateReturn(i);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API createContainer "), TAG);
            return i;
        }
    }

    public static int createContainer(String str, Uri uri) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "KnoxContainerManager.createContainer");
        return createContainer((ContextInfo) null, str, uri);
    }

    public static int createContainer(ContextInfo contextInfo, String str, Uri uri) {
        IKnoxContainerManager containerService = getContainerService();
        int i = ERROR_INTERNAL_ERROR;
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return ERROR_INTERNAL_ERROR;
        }
        CreationParams creationParams = new CreationParams();
        creationParams.mConfigName = str;
        creationParams.mAdminPkgName = uri.toString();
        try {
            i = containerService.createContainer(contextInfo, creationParams, 30);
            return processCreateReturn(i);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API createContainer "), TAG);
            return i;
        }
    }

    public static int createContainer(String str) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "KnoxContainerManager.createContainer(String)");
        return createContainer((ContextInfo) null, str);
    }

    public static int createContainer(ContextInfo contextInfo, String str) {
        IKnoxContainerManager containerService = getContainerService();
        int i = ERROR_INTERNAL_ERROR;
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return ERROR_INTERNAL_ERROR;
        }
        CreationParams creationParams = new CreationParams();
        creationParams.mConfigName = str;
        try {
            i = containerService.createContainer(contextInfo, creationParams, 70);
            return processCreateReturn(i);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API createContainer "), TAG);
            return i;
        }
    }

    public static int createContainer(ContextInfo contextInfo, String str, IEnterpriseContainerCallback iEnterpriseContainerCallback) {
        IKnoxContainerManager containerService = getContainerService();
        int i = ERROR_INTERNAL_ERROR;
        if (containerService == null) {
            Log.e(TAG, "KnoxMUMContainerPolicy Service is not yet ready!!!");
            return ERROR_INTERNAL_ERROR;
        }
        CreationParams creationParams = new CreationParams();
        creationParams.mConfigName = str;
        try {
            i = containerService.createContainerWithCallback(contextInfo, creationParams, 70, iEnterpriseContainerCallback);
            return processCreateReturn(i);
        } catch (RemoteException e) {
            KnoxContainerManager$$ExternalSyntheticOutline0.m243m(e, new StringBuilder("Failed at KnoxContainerManager API createContainer "), TAG);
            return i;
        }
    }
}
