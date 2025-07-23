package android.os;

import android.accounts.AccountManager;
import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.PropertyInvalidatedCache;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.SemUserInfo;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.multiuser.Flags;
import android.os.IUserManager;
import android.os.IpcDataCache;
import android.os.Parcelable;
import android.os.UserManager;
import android.provider.Settings;
import android.util.AndroidException;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import com.android.internal.R;
import com.samsung.android.core.pm.multiuser.MultiUserSupportsHelper;
import com.samsung.android.rune.PMRune;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class UserManager {

    @SystemApi
    public static final String ACTION_CREATE_SUPERVISED_USER = "android.os.action.CREATE_SUPERVISED_USER";
    private static final String ACTION_CREATE_USER = "android.os.action.CREATE_USER";

    @SystemApi
    public static final String ACTION_USER_RESTRICTIONS_CHANGED = "android.os.action.USER_RESTRICTIONS_CHANGED";
    public static final String ALLOW_PARENT_PROFILE_APP_LINKING = "allow_parent_profile_app_linking";
    public static final long ALWAYS_USE_CONTEXT_USER = 183155436;
    private static final String CACHE_KEY_STATIC_USER_PROPERTIES = PropertyInvalidatedCache.createPropertyName("system_server", "static_user_props");
    public static final String DEV_CREATE_OVERRIDE_PROPERTY = "debug.user.creation_override";
    public static final String DISALLOW_ADD_CLONE_PROFILE = "no_add_clone_profile";

    @Deprecated
    public static final String DISALLOW_ADD_MANAGED_PROFILE = "no_add_managed_profile";
    public static final String DISALLOW_ADD_PRIVATE_PROFILE = "no_add_private_profile";
    public static final String DISALLOW_ADD_USER = "no_add_user";
    public static final String DISALLOW_ADD_WIFI_CONFIG = "no_add_wifi_config";
    public static final String DISALLOW_ADJUST_VOLUME = "no_adjust_volume";
    public static final String DISALLOW_AIRPLANE_MODE = "no_airplane_mode";
    public static final String DISALLOW_AMBIENT_DISPLAY = "no_ambient_display";
    public static final String DISALLOW_APPS_CONTROL = "no_control_apps";
    public static final String DISALLOW_ASSIST_CONTENT = "no_assist_content";
    public static final String DISALLOW_AUTOFILL = "no_autofill";
    public static final String DISALLOW_BIOMETRIC = "disallow_biometric";
    public static final String DISALLOW_BLUETOOTH = "no_bluetooth";
    public static final String DISALLOW_BLUETOOTH_SHARING = "no_bluetooth_sharing";
    public static final String DISALLOW_CAMERA = "no_camera";
    public static final String DISALLOW_CAMERA_TOGGLE = "disallow_camera_toggle";
    public static final String DISALLOW_CELLULAR_2G = "no_cellular_2g";
    public static final String DISALLOW_CHANGE_NEAR_FIELD_COMMUNICATION_RADIO = "no_change_near_field_communication_radio";
    public static final String DISALLOW_CHANGE_WIFI_STATE = "no_change_wifi_state";
    public static final String DISALLOW_CONFIG_BLUETOOTH = "no_config_bluetooth";
    public static final String DISALLOW_CONFIG_BRIGHTNESS = "no_config_brightness";
    public static final String DISALLOW_CONFIG_CELL_BROADCASTS = "no_config_cell_broadcasts";
    public static final String DISALLOW_CONFIG_CREDENTIALS = "no_config_credentials";
    public static final String DISALLOW_CONFIG_DATE_TIME = "no_config_date_time";
    public static final String DISALLOW_CONFIG_DEFAULT_APPS = "disallow_config_default_apps";
    public static final String DISALLOW_CONFIG_LOCALE = "no_config_locale";
    public static final String DISALLOW_CONFIG_LOCATION = "no_config_location";
    public static final String DISALLOW_CONFIG_MOBILE_NETWORKS = "no_config_mobile_networks";
    public static final String DISALLOW_CONFIG_PRIVATE_DNS = "disallow_config_private_dns";
    public static final String DISALLOW_CONFIG_SCREEN_TIMEOUT = "no_config_screen_timeout";
    public static final String DISALLOW_CONFIG_TETHERING = "no_config_tethering";
    public static final String DISALLOW_CONFIG_VPN = "no_config_vpn";
    public static final String DISALLOW_CONFIG_WIFI = "no_config_wifi";
    public static final String DISALLOW_CONTENT_CAPTURE = "no_content_capture";
    public static final String DISALLOW_CONTENT_SUGGESTIONS = "no_content_suggestions";
    public static final String DISALLOW_CREATE_WINDOWS = "no_create_windows";
    public static final String DISALLOW_CROSS_PROFILE_COPY_PASTE = "no_cross_profile_copy_paste";
    public static final String DISALLOW_DATA_ROAMING = "no_data_roaming";
    public static final String DISALLOW_DEBUGGING_FEATURES = "no_debugging_features";
    public static final String DISALLOW_FACTORY_RESET = "no_factory_reset";
    public static final String DISALLOW_FUN = "no_fun";
    public static final String DISALLOW_GRANT_ADMIN = "no_grant_admin";
    public static final String DISALLOW_INSTALL_APPS = "no_install_apps";
    public static final String DISALLOW_INSTALL_UNKNOWN_SOURCES = "no_install_unknown_sources";
    public static final String DISALLOW_INSTALL_UNKNOWN_SOURCES_GLOBALLY = "no_install_unknown_sources_globally";
    public static final String DISALLOW_MICROPHONE_TOGGLE = "disallow_microphone_toggle";
    public static final String DISALLOW_MODIFY_ACCOUNTS = "no_modify_accounts";
    public static final String DISALLOW_MOUNT_PHYSICAL_MEDIA = "no_physical_media";
    public static final String DISALLOW_NEAR_FIELD_COMMUNICATION_RADIO = "no_near_field_communication_radio";
    public static final String DISALLOW_NETWORK_RESET = "no_network_reset";
    public static final String DISALLOW_NON_MARKET_APP_BY_KNOX = "no_non_market_app_by_knox";

    @SystemApi
    @Deprecated
    public static final String DISALLOW_OEM_UNLOCK = "no_oem_unlock";
    public static final String DISALLOW_OUTGOING_BEAM = "no_outgoing_beam";
    public static final String DISALLOW_OUTGOING_CALLS = "no_outgoing_calls";
    public static final String DISALLOW_PRINTING = "no_printing";
    public static final String DISALLOW_RECORD_AUDIO = "no_record_audio";

    @Deprecated
    public static final String DISALLOW_REMOVE_MANAGED_PROFILE = "no_remove_managed_profile";
    public static final String DISALLOW_REMOVE_USER = "no_remove_user";

    @SystemApi
    public static final String DISALLOW_RUN_IN_BACKGROUND = "no_run_in_background";
    public static final String DISALLOW_SAFE_BOOT = "no_safe_boot";
    public static final String DISALLOW_SET_USER_ICON = "no_set_user_icon";
    public static final String DISALLOW_SET_WALLPAPER = "no_set_wallpaper";
    public static final String DISALLOW_SHARE_INTO_MANAGED_PROFILE = "no_sharing_into_profile";
    public static final String DISALLOW_SHARE_LOCATION = "no_share_location";
    public static final String DISALLOW_SHARING_ADMIN_CONFIGURED_WIFI = "no_sharing_admin_configured_wifi";
    public static final String DISALLOW_SIM_GLOBALLY = "no_sim_globally";
    public static final String DISALLOW_SMS = "no_sms";
    public static final String DISALLOW_SYSTEM_ERROR_DIALOGS = "no_system_error_dialogs";
    public static final String DISALLOW_THREAD_NETWORK = "no_thread_network";
    public static final String DISALLOW_ULTRA_WIDEBAND_RADIO = "no_ultra_wideband_radio";
    public static final String DISALLOW_UNIFIED_PASSWORD = "no_unified_password";
    public static final String DISALLOW_UNINSTALL_APPS = "no_uninstall_apps";
    public static final String DISALLOW_UNMUTE_DEVICE = "disallow_unmute_device";
    public static final String DISALLOW_UNMUTE_MICROPHONE = "no_unmute_microphone";
    public static final String DISALLOW_USB_FILE_TRANSFER = "no_usb_file_transfer";
    public static final String DISALLOW_USER_SWITCH = "no_user_switch";
    public static final String DISALLOW_WALLPAPER = "no_wallpaper";
    public static final String DISALLOW_WIFI_DIRECT = "no_wifi_direct";
    public static final String DISALLOW_WIFI_TETHERING = "no_wifi_tethering";
    public static final String ENSURE_VERIFY_APPS = "ensure_verify_apps";
    public static final String EXTRA_USER_ACCOUNT_NAME = "android.os.extra.USER_ACCOUNT_NAME";
    public static final String EXTRA_USER_ACCOUNT_OPTIONS = "android.os.extra.USER_ACCOUNT_OPTIONS";
    public static final String EXTRA_USER_ACCOUNT_TYPE = "android.os.extra.USER_ACCOUNT_TYPE";
    public static final String EXTRA_USER_NAME = "android.os.extra.USER_NAME";
    public static final String KEY_RESTRICTIONS_PENDING = "restrictions_pending";
    public static final int LOGOUTABILITY_STATUS_CANNOT_LOGOUT_SYSTEM_USER = 1;
    public static final int LOGOUTABILITY_STATUS_CANNOT_SWITCH = 3;
    public static final int LOGOUTABILITY_STATUS_NO_SUITABLE_USER_TO_LOGOUT_TO = 2;
    public static final int LOGOUTABILITY_STATUS_OK = 0;
    public static final int MAX_ACCOUNT_OPTIONS_LENGTH = 1000;
    public static final int MAX_ACCOUNT_STRING_LENGTH = 500;
    public static final int MAX_USER_NAME_LENGTH = 100;
    public static final int MIN_VIRTUAL_USER_ID = 1000;
    public static final int PIN_VERIFICATION_FAILED_INCORRECT = -3;
    public static final int PIN_VERIFICATION_FAILED_NOT_SET = -2;
    public static final int PIN_VERIFICATION_SUCCESS = -1;
    public static final int QUIET_MODE_DISABLE_DONT_ASK_CREDENTIAL = 2;
    public static final int QUIET_MODE_DISABLE_ONLY_IF_CREDENTIAL_NOT_REQUIRED = 1;

    @SystemApi
    public static final int REMOVE_RESULT_ALREADY_BEING_REMOVED = 2;

    @SystemApi
    public static final int REMOVE_RESULT_DEFERRED = 1;

    @SystemApi
    public static final int REMOVE_RESULT_ERROR_MAIN_USER_PERMANENT_ADMIN = -5;

    @SystemApi
    public static final int REMOVE_RESULT_ERROR_SYSTEM_USER = -4;

    @SystemApi
    public static final int REMOVE_RESULT_ERROR_UNKNOWN = -1;

    @SystemApi
    public static final int REMOVE_RESULT_ERROR_USER_NOT_FOUND = -3;

    @SystemApi
    public static final int REMOVE_RESULT_ERROR_USER_RESTRICTION = -2;

    @SystemApi
    public static final int REMOVE_RESULT_REMOVED = 0;
    public static final int REMOVE_RESULT_USER_IS_REMOVABLE = 3;

    @SystemApi
    public static final int RESTRICTION_NOT_SET = 0;

    @SystemApi
    public static final int RESTRICTION_SOURCE_DEVICE_OWNER = 2;

    @SystemApi
    public static final int RESTRICTION_SOURCE_PROFILE_OWNER = 4;

    @SystemApi
    public static final int RESTRICTION_SOURCE_SYSTEM = 1;
    public static final int SEM_RESTRICTION_NOT_SET = 0;
    public static final int SEM_RESTRICTION_SOURCE_DEVICE_OWNER = 2;
    public static final int SEM_RESTRICTION_SOURCE_PROFILE_OWNER = 4;
    public static final int SEM_RESTRICTION_SOURCE_SYSTEM = 1;

    @SystemApi
    public static final int SWITCHABILITY_STATUS_OK = 0;

    @SystemApi
    public static final int SWITCHABILITY_STATUS_SYSTEM_USER_LOCKED = 4;

    @SystemApi
    public static final int SWITCHABILITY_STATUS_USER_IN_CALL = 1;

    @SystemApi
    public static final int SWITCHABILITY_STATUS_USER_SWITCH_DISALLOWED = 2;
    public static final String SYSTEM_USER_MODE_EMULATION_DEFAULT = "default";
    public static final String SYSTEM_USER_MODE_EMULATION_FULL = "full";
    public static final String SYSTEM_USER_MODE_EMULATION_HEADLESS = "headless";
    public static final String SYSTEM_USER_MODE_EMULATION_PROPERTY = "persist.debug.user_mode_emulation";
    private static final String TAG = "UserManager";
    public static final int USER_CREATION_FAILED_NOT_PERMITTED = 1;
    public static final int USER_CREATION_FAILED_NO_MORE_USERS = 2;
    public static final int USER_OPERATION_ERROR_CURRENT_USER = 4;
    public static final int USER_OPERATION_ERROR_DISABLED_USER = 8;
    public static final int USER_OPERATION_ERROR_LOW_STORAGE = 5;
    public static final int USER_OPERATION_ERROR_MANAGED_PROFILE = 2;
    public static final int USER_OPERATION_ERROR_MAX_RUNNING_USERS = 3;
    public static final int USER_OPERATION_ERROR_MAX_USERS = 6;
    public static final int USER_OPERATION_ERROR_PRIVATE_PROFILE = 9;
    public static final int USER_OPERATION_ERROR_UNKNOWN = 1;

    @SystemApi
    public static final int USER_OPERATION_ERROR_USER_ACCOUNT_ALREADY_EXISTS = 7;
    public static final int USER_OPERATION_ERROR_USER_RESTRICTED = 10;
    public static final int USER_OPERATION_SUCCESS = 0;
    public static final String USER_TYPE_FULL_DEMO = "android.os.usertype.full.DEMO";

    @SystemApi
    public static final String USER_TYPE_FULL_GUEST = "android.os.usertype.full.GUEST";
    public static final String USER_TYPE_FULL_RESTRICTED = "android.os.usertype.full.RESTRICTED";

    @SystemApi
    public static final String USER_TYPE_FULL_SECONDARY = "android.os.usertype.full.SECONDARY";

    @SystemApi
    public static final String USER_TYPE_FULL_SYSTEM = "android.os.usertype.full.SYSTEM";
    public static final String USER_TYPE_PROFILE_CLONE = "android.os.usertype.profile.CLONE";
    public static final String USER_TYPE_PROFILE_COMMUNAL = "android.os.usertype.profile.COMMUNAL";
    public static final String USER_TYPE_PROFILE_MANAGED = "android.os.usertype.profile.MANAGED";
    public static final String USER_TYPE_PROFILE_PRIVATE = "android.os.usertype.profile.PRIVATE";

    @SystemApi
    public static final String USER_TYPE_PROFILE_SUPERVISING = "android.os.usertype.profile.SUPERVISING";
    public static final String USER_TYPE_PROFILE_TEST = "android.os.usertype.profile.TEST";

    @SystemApi
    public static final String USER_TYPE_SYSTEM_HEADLESS = "android.os.usertype.system.HEADLESS";
    private static Boolean sIsHeadlessSystemUser;
    private final Context mContext;
    private final IUserManager mService;
    private final int mUserId;
    private String mProfileTypeOfProcessUser = null;
    private final Object mIpcDataCache = new UserManagerCache();
    private final PropertyInvalidatedCache<Integer, String> mProfileTypeCache = new PropertyInvalidatedCache<Integer, String>(32, CACHE_KEY_STATIC_USER_PROPERTIES) { // from class: android.os.UserManager.1
        @Override // android.app.PropertyInvalidatedCache
        public String recompute(Integer num) {
            try {
                String profileType = UserManager.this.mService.getProfileType(num.intValue());
                return profileType != null ? profileType.intern() : profileType;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.app.PropertyInvalidatedCache
        public boolean bypass(Integer num) {
            return num.intValue() < 0;
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface QuietModeFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RemoveResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserLogoutability {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserOperationResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserRestrictionKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserRestrictionSource {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserSwitchabilityResult {
    }

    @SystemApi
    public static boolean isRemoveResultSuccessful(int i) {
        return i >= 0;
    }

    public static boolean isVirtualUserId(int i) {
        return i >= 1000;
    }

    @Deprecated
    public boolean setRestrictionsChallenge(String str) {
        return false;
    }

    public static class UserOperationException extends RuntimeException {
        private final int mUserOperationResult;

        public UserOperationException(String str, int i) {
            super(str);
            this.mUserOperationResult = i;
        }

        public int getUserOperationResult() {
            return this.mUserOperationResult;
        }

        public static UserOperationException from(ServiceSpecificException serviceSpecificException) {
            return new UserOperationException(serviceSpecificException.getMessage(), serviceSpecificException.errorCode);
        }
    }

    private <T> T returnNullOrThrowUserOperationException(ServiceSpecificException serviceSpecificException, boolean z) throws UserOperationException {
        if (z) {
            throw UserOperationException.from(serviceSpecificException);
        }
        return null;
    }

    public static class CheckedUserOperationException extends AndroidException {
        private final int mUserOperationResult;

        public CheckedUserOperationException(String str, int i) {
            super(str);
            this.mUserOperationResult = i;
        }

        public int getUserOperationResult() {
            return this.mUserOperationResult;
        }

        public ServiceSpecificException toServiceSpecificException() {
            return new ServiceSpecificException(this.mUserOperationResult, getMessage());
        }
    }

    private int getContextUserIfAppropriate() {
        if (CompatChanges.isChangeEnabled(ALWAYS_USE_CONTEXT_USER)) {
            return this.mUserId;
        }
        int myUserId = UserHandle.myUserId();
        if (myUserId != this.mUserId) {
            Log.w(TAG, "Using the calling user " + myUserId + ", rather than the specified context user " + this.mUserId + ", because API is only UserHandleAware on higher targetSdkVersions.", new Throwable());
        }
        return myUserId;
    }

    public static UserManager get(Context context) {
        return (UserManager) context.getSystemService("user");
    }

    public UserManager(Context context, IUserManager iUserManager) {
        this.mService = iUserManager;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext == null ? context : applicationContext;
        this.mUserId = context.getUserId();
    }

    public static boolean supportsMultipleUsers() {
        return MultiUserSupportsHelper.supportsMultipleUsers();
    }

    public static boolean isGuestUserAlwaysEphemeral() {
        return Resources.getSystem().getBoolean(R.bool.config_guestUserEphemeral);
    }

    public static boolean isGuestUserAllowEphemeralStateChange() {
        return Resources.getSystem().getBoolean(R.bool.config_guestUserAllowEphemeralStateChange);
    }

    public static boolean isCommunalProfileEnabled() {
        return SystemProperties.getBoolean("persist.fw.omnipresent_communal_user", Resources.getSystem().getBoolean(R.bool.config_omnipresentCommunalUser));
    }

    public static boolean isPrivateProfileEnabled() {
        if (Flags.blockPrivateSpaceCreation()) {
            return !ActivityManager.isLowRamDeviceStatic();
        }
        return true;
    }

    public static boolean isMultipleAdminEnabled() {
        return Resources.getSystem().getBoolean(R.bool.config_enableMultipleAdmins);
    }

    public static boolean isHeadlessSystemUserMode() {
        if (sIsHeadlessSystemUser == null) {
            try {
                sIsHeadlessSystemUser = Boolean.valueOf(IUserManager.Stub.asInterface(ServiceManager.getService("user")).isHeadlessSystemUserMode());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return sIsHeadlessSystemUser.booleanValue();
    }

    @Deprecated
    public boolean canSwitchUsers() {
        try {
            return this.mService.getUserSwitchability(this.mUserId) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getUserSwitchability() {
        return getUserSwitchability(UserHandle.of(getContextUserIfAppropriate()));
    }

    public int getUserSwitchability(UserHandle userHandle) {
        try {
            return this.mService.getUserSwitchability(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserLogoutability(int i) {
        try {
            return this.mService.getUserLogoutability(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int getUserHandle() {
        return getContextUserIfAppropriate();
    }

    @Deprecated
    public int getProcessUserId() {
        return UserHandle.myUserId();
    }

    public String getUserType() {
        UserInfo userInfo = getUserInfo(this.mUserId);
        return userInfo == null ? "" : userInfo.userType;
    }

    public String getUserName() {
        int myUserId = UserHandle.myUserId();
        int i = this.mUserId;
        if (myUserId == i) {
            try {
                return this.mService.getUserName();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        UserInfo userInfo = getUserInfo(i);
        if (userInfo != null && userInfo.name != null) {
            return userInfo.name;
        }
        return "";
    }

    @SystemApi
    public boolean isUserNameSet() {
        try {
            return this.mService.isUserNameSet(getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserAGoat() {
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 30) {
            return false;
        }
        return this.mContext.getPackageManager().isPackageAvailable("com.coffeestainstudios.goatsimulator");
    }

    @SystemApi
    @Deprecated
    public boolean isPrimaryUser() {
        UserInfo userInfo = getUserInfo(getContextUserIfAppropriate());
        return userInfo != null && userInfo.isPrimary();
    }

    public boolean isSystemUser() {
        return getContextUserIfAppropriate() == 0;
    }

    @SystemApi
    public boolean isMainUser() {
        UserInfo userInfo = getUserInfo(this.mUserId);
        return userInfo != null && userInfo.isMain();
    }

    @SystemApi
    public UserHandle getMainUser() {
        try {
            int mainUserId = this.mService.getMainUserId();
            if (mainUserId == -10000) {
                return null;
            }
            return UserHandle.of(mainUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public UserHandle getCommunalProfile() {
        try {
            int communalProfileId = this.mService.getCommunalProfileId();
            if (communalProfileId == -10000) {
                return null;
            }
            return UserHandle.of(communalProfileId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCommunalProfile() {
        return isCommunalProfile(this.mUserId);
    }

    private boolean isCommunalProfile(int i) {
        return isUserTypeCommunalProfile(getProfileType(i));
    }

    public boolean isAdminUser() {
        try {
            return this.mService.isAdminUser(getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserAdmin(int i) {
        UserInfo userInfo = getUserInfo(i);
        return userInfo != null && userInfo.isAdmin();
    }

    public boolean isForegroundUserAdmin() {
        try {
            return this.mService.isForegroundUserAdmin();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isUserOfType(String str) {
        try {
            return this.mService.isUserOfType(this.mUserId, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isUserTypeManagedProfile(String str) {
        return USER_TYPE_PROFILE_MANAGED.equals(str);
    }

    public static boolean isUserTypeGuest(String str) {
        return USER_TYPE_FULL_GUEST.equals(str);
    }

    public static boolean isUserTypeRestricted(String str) {
        return USER_TYPE_FULL_RESTRICTED.equals(str);
    }

    public static boolean isUserTypeDemo(String str) {
        return USER_TYPE_FULL_DEMO.equals(str);
    }

    public static boolean isUserTypeCloneProfile(String str) {
        return USER_TYPE_PROFILE_CLONE.equals(str);
    }

    public static boolean isUserTypeCommunalProfile(String str) {
        return USER_TYPE_PROFILE_COMMUNAL.equals(str);
    }

    public static boolean isUserTypePrivateProfile(String str) {
        return USER_TYPE_PROFILE_PRIVATE.equals(str);
    }

    public static boolean isUserTypeSupervisingProfile(String str) {
        return USER_TYPE_PROFILE_SUPERVISING.equals(str);
    }

    @Deprecated
    public boolean isLinkedUser() {
        return isRestrictedProfile();
    }

    @SystemApi
    public boolean isRestrictedProfile() {
        try {
            return this.mService.isRestricted(getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isRestrictedProfile(UserHandle userHandle) {
        try {
            return this.mService.isRestricted(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean canHaveRestrictedProfile() {
        try {
            return this.mService.canHaveRestrictedProfile(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canAddPrivateProfile() {
        if (!Flags.enablePrivateSpaceFeatures()) {
            return false;
        }
        if (!Flags.blockPrivateSpaceCreation()) {
            return true;
        }
        try {
            return this.mService.canAddPrivateProfile(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean hasRestrictedProfiles() {
        try {
            return this.mService.hasRestrictedProfiles(getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public UserHandle getRestrictedProfileParent() {
        int i;
        UserInfo userInfo = getUserInfo(this.mUserId);
        if (userInfo == null || !userInfo.isRestricted() || (i = userInfo.restrictedProfileParentId) == -10000) {
            return null;
        }
        return UserHandle.of(i);
    }

    public boolean isGuestUser(int i) {
        UserInfo userInfo = getUserInfo(i);
        return userInfo != null && userInfo.isGuest();
    }

    @SystemApi
    public boolean isGuestUser() {
        UserInfo userInfo = getUserInfo(getContextUserIfAppropriate());
        return userInfo != null && userInfo.isGuest();
    }

    public boolean isDemoUser() {
        try {
            return this.mService.isDemoUser(getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVirtualUser(int i) {
        UserInfo userInfo = getUserInfo(i);
        if (userInfo != null) {
            return userInfo.isVirtualUser();
        }
        return false;
    }

    public boolean isProfile() {
        return isProfile(this.mUserId);
    }

    public boolean isProfile(int i) {
        String profileType = getProfileType(i);
        return (profileType == null || profileType.equals("")) ? false : true;
    }

    private String getProfileType() {
        return getProfileType(this.mUserId);
    }

    private String getProfileType(int i) {
        if (i == UserHandle.myUserId()) {
            String str = this.mProfileTypeOfProcessUser;
            if (str != null) {
                return str;
            }
            try {
                String profileType = this.mService.getProfileType(i);
                if (profileType != null) {
                    String intern = profileType.intern();
                    this.mProfileTypeOfProcessUser = intern;
                    return intern;
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mProfileTypeCache.query(Integer.valueOf(i));
    }

    public boolean isManagedProfile() {
        return isManagedProfile(getContextUserIfAppropriate());
    }

    @SystemApi
    public boolean isManagedProfile(int i) {
        return isUserTypeManagedProfile(getProfileType(i));
    }

    @SystemApi
    public boolean isCloneProfile() {
        return isUserTypeCloneProfile(getProfileType());
    }

    @SystemApi
    public boolean isPrivateProfile() {
        return isUserTypePrivateProfile(getProfileType());
    }

    public boolean isEphemeralUser() {
        return isUserEphemeral(this.mUserId);
    }

    public boolean isUserEphemeral(int i) {
        UserInfo userInfo = getUserInfo(i);
        return userInfo != null && userInfo.isEphemeral();
    }

    public boolean isUserRunning(UserHandle userHandle) {
        return isUserRunning(userHandle.getIdentifier());
    }

    public boolean isUserRunning(int i) {
        try {
            return this.mService.isUserRunning(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserRunningOrStopping(UserHandle userHandle) {
        try {
            return ActivityManager.getService().isUserRunning(userHandle.getIdentifier(), 1);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserForeground() {
        try {
            return this.mService.isUserForeground(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isVisibleBackgroundUsersEnabled() {
        return SystemProperties.getBoolean("fw.visible_bg_users", Resources.getSystem().getBoolean(R.bool.config_multiuserVisibleBackgroundUsers));
    }

    public boolean isVisibleBackgroundUsersSupported() {
        return isVisibleBackgroundUsersEnabled();
    }

    public static boolean isVisibleBackgroundUsersOnDefaultDisplayEnabled() {
        return SystemProperties.getBoolean("fw.visible_bg_users_on_default_display", Resources.getSystem().getBoolean(R.bool.config_multiuserVisibleBackgroundUsersOnDefaultDisplay));
    }

    public boolean isVisibleBackgroundUsersOnDefaultDisplaySupported() {
        return isVisibleBackgroundUsersOnDefaultDisplayEnabled();
    }

    @SystemApi
    public boolean isUserVisible() {
        try {
            return this.mService.isUserVisible(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Set<UserHandle> getVisibleUsers() {
        ArraySet arraySet = new ArraySet();
        try {
            int[] visibleUsers = this.mService.getVisibleUsers();
            if (visibleUsers != null) {
                for (int i : visibleUsers) {
                    arraySet.add(UserHandle.of(i));
                }
            }
            return arraySet;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMainDisplayIdAssignedToUser() {
        try {
            return this.mService.getMainDisplayIdAssignedToUser(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserUnlocked() {
        return isUserUnlocked(getContextUserIfAppropriate());
    }

    public boolean isUserUnlocked(UserHandle userHandle) {
        return isUserUnlocked(userHandle.getIdentifier());
    }

    public boolean isUserUnlocked(int i) {
        final IUserManager iUserManager = this.mService;
        Objects.requireNonNull(iUserManager);
        return UserManagerCache.isUserUnlocked(new IpcDataCache.RemoteCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda5
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                return Boolean.valueOf(IUserManager.this.isUserUnlocked(((Integer) obj).intValue()));
            }
        }, Integer.valueOf(i)).booleanValue();
    }

    public static final void invalidateIsUserUnlockedCache() {
        UserManagerCache.invalidateUserUnlocked();
    }

    @SystemApi
    public boolean isUserUnlockingOrUnlocked(UserHandle userHandle) {
        return isUserUnlockingOrUnlocked(userHandle.getIdentifier());
    }

    public boolean isUserUnlockingOrUnlocked(int i) {
        final IUserManager iUserManager = this.mService;
        Objects.requireNonNull(iUserManager);
        return UserManagerCache.isUserUnlockingOrUnlocked(new IpcDataCache.RemoteCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda10
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                return Boolean.valueOf(IUserManager.this.isUserUnlockingOrUnlocked(((Integer) obj).intValue()));
            }
        }, Integer.valueOf(i)).booleanValue();
    }

    public long getUserStartRealtime() {
        if (getContextUserIfAppropriate() != UserHandle.myUserId()) {
            throw new IllegalArgumentException("Calling from a context differing from the calling user is not currently supported.");
        }
        try {
            return this.mService.getUserStartRealtime();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getUserUnlockRealtime() {
        if (getContextUserIfAppropriate() != UserHandle.myUserId()) {
            throw new IllegalArgumentException("Calling from a context differing from the calling user is not currently supported.");
        }
        try {
            return this.mService.getUserUnlockRealtime();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public UserInfo getUserInfo(int i) {
        if (Flags.cacheUserInfoReadOnly()) {
            final IUserManager iUserManager = this.mService;
            Objects.requireNonNull(iUserManager);
            return UserManagerCache.getUserInfo(new IpcDataCache.RemoteCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda9
                @Override // android.os.IpcDataCache.RemoteCall
                public final Object apply(Object obj) {
                    return IUserManager.this.getUserInfo(((Integer) obj).intValue());
                }
            }, Integer.valueOf(i));
        }
        try {
            return this.mService.getUserInfo(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public UserProperties getUserProperties(UserHandle userHandle) {
        int identifier = userHandle.getIdentifier();
        if (identifier < 0 && Flags.fixGetUserPropertyCache()) {
            throw new IllegalArgumentException("Cannot access properties for user " + identifier);
        }
        if (!Flags.cacheUserPropertiesCorrectlyReadOnly() || identifier < 0) {
            try {
                return this.mService.getUserPropertiesCopy(identifier);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        int callingUid = Binder.getCallingUid();
        int myUid = Process.myUid();
        if (myUid == 1000 && callingUid != myUid) {
            Log.w(TAG, "The System (uid " + myUid + ") is fetching a copy of UserProperties on behalf of callingUid " + callingUid + ". Possibly it should carefully first clearCallingIdentity or perhaps use UserManagerInternal.getUserProperties() instead?", new Throwable());
        }
        return getUserPropertiesFromQuery(new QueryUserId(identifier));
    }

    public static final void invalidateUserPropertiesCache() {
        UserManagerCache.invalidateUserPropertiesFromQuery();
    }

    private UserProperties getUserPropertiesFromQuery(QueryUserId queryUserId) {
        return UserManagerCache.getUserPropertiesFromQuery(new IpcDataCache.RemoteCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda6
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                UserProperties lambda$getUserPropertiesFromQuery$0;
                lambda$getUserPropertiesFromQuery$0 = UserManager.this.lambda$getUserPropertiesFromQuery$0((UserManager.QueryUserId) obj);
                return lambda$getUserPropertiesFromQuery$0;
            }
        }, queryUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ UserProperties lambda$getUserPropertiesFromQuery$0(QueryUserId queryUserId) throws RemoteException {
        return this.mService.getUserPropertiesCopy(queryUserId.getUserId());
    }

    static final class QueryUserId extends Pair<Integer, Integer> {
        public QueryUserId(int i) {
            super(Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(i));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int getUserId() {
            return ((Integer) this.second).intValue();
        }
    }

    @SystemApi
    @Deprecated
    public int getUserRestrictionSource(String str, UserHandle userHandle) {
        try {
            return this.mService.getUserRestrictionSource(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<EnforcingUser> getUserRestrictionSources(String str, UserHandle userHandle) {
        try {
            return this.mService.getUserRestrictionSources(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getUserRestrictions() {
        try {
            return this.mService.getUserRestrictions(getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getUserRestrictions(UserHandle userHandle) {
        try {
            return this.mService.getUserRestrictions(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasBaseUserRestriction(String str, UserHandle userHandle) {
        try {
            return this.mService.hasBaseUserRestriction(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setUserRestrictions(Bundle bundle) {
        throw new UnsupportedOperationException("This method is no longer supported");
    }

    @Deprecated
    public void setUserRestrictions(Bundle bundle, UserHandle userHandle) {
        throw new UnsupportedOperationException("This method is no longer supported");
    }

    @Deprecated
    public void setUserRestriction(String str, boolean z) {
        try {
            this.mService.setUserRestriction(str, z, getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setUserRestriction(String str, boolean z, UserHandle userHandle) {
        try {
            this.mService.setUserRestriction(str, z, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasUserRestriction(String str) {
        return hasUserRestrictionForUser(str, getContextUserIfAppropriate());
    }

    @Deprecated
    public boolean hasUserRestriction(String str, UserHandle userHandle) {
        return hasUserRestrictionForUser(str, userHandle);
    }

    @SystemApi
    public boolean hasUserRestrictionForUser(String str, UserHandle userHandle) {
        return hasUserRestrictionForUser(str, userHandle.getIdentifier());
    }

    private boolean hasUserRestrictionForUser(String str, int i) {
        return getUserRestrictionFromQuery(new Pair<>(str, Integer.valueOf(i)));
    }

    private boolean getUserRestrictionFromQuery(Pair<String, Integer> pair) {
        return UserManagerCache.getUserRestrictionFromQuery(new IpcDataCache.RemoteCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda0
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                Boolean lambda$getUserRestrictionFromQuery$1;
                lambda$getUserRestrictionFromQuery$1 = UserManager.this.lambda$getUserRestrictionFromQuery$1((Pair) obj);
                return lambda$getUserRestrictionFromQuery$1;
            }
        }, new IpcDataCache.BypassCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda1
            @Override // android.os.IpcDataCache.BypassCall
            public final Boolean apply(Object obj) {
                Boolean valueOf;
                valueOf = Boolean.valueOf(!Flags.cacheUserRestrictionsReadOnly());
                return valueOf;
            }
        }, pair).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ Boolean lambda$getUserRestrictionFromQuery$1(Pair pair) throws RemoteException {
        return Boolean.valueOf(this.mService.hasUserRestriction((String) pair.first, ((Integer) pair.second).intValue()));
    }

    public static final void invalidateUserRestriction() {
        if (Flags.cacheUserRestrictionsReadOnly()) {
            UserManagerCache.invalidateUserRestrictionFromQuery();
        }
    }

    public boolean hasUserRestrictionOnAnyUser(String str) {
        try {
            return this.mService.hasUserRestrictionOnAnyUser(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSettingRestrictedForUser(String str, int i, String str2, int i2) {
        try {
            return this.mService.isSettingRestrictedForUser(str, i, str2, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addUserRestrictionsListener(IUserRestrictionsListener iUserRestrictionsListener) {
        try {
            this.mService.addUserRestrictionsListener(iUserRestrictionsListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getSerialNumberForUser(UserHandle userHandle) {
        return getUserSerialNumber(userHandle.getIdentifier());
    }

    public UserHandle getUserForSerialNumber(long j) {
        int userHandle = getUserHandle((int) j);
        if (userHandle >= 0) {
            return new UserHandle(userHandle);
        }
        return null;
    }

    @Deprecated
    public UserInfo createUser(String str, int i) {
        return createUser(str, UserInfo.getDefaultUserType(i), i);
    }

    public UserInfo createUser(String str, String str2, int i) {
        try {
            return this.mService.createUserWithThrow(str, str2, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException unused) {
            return null;
        }
    }

    @SystemApi
    public NewUserResponse createUser(NewUserRequest newUserRequest) {
        try {
            return new NewUserResponse(this.mService.createUserWithAttributes(newUserRequest.getName(), newUserRequest.getUserType(), newUserRequest.getFlags(), newUserRequest.getUserIcon(), newUserRequest.getAccountName(), newUserRequest.getAccountType(), newUserRequest.getAccountOptions()), 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            Log.w(TAG, "Exception while creating user " + newUserRequest, e2);
            return new NewUserResponse(null, e2.errorCode);
        }
    }

    @Deprecated
    public UserInfo preCreateUser(String str) throws UserOperationException {
        Log.w(TAG, "preCreateUser(): Pre-created user is deprecated.");
        try {
            return this.mService.preCreateUserWithThrow(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw UserOperationException.from(e2);
        }
    }

    public UserInfo createGuest(Context context) {
        try {
            UserInfo createUserWithThrow = this.mService.createUserWithThrow(null, USER_TYPE_FULL_GUEST, 0);
            Settings.Secure.putStringForUser(context.getContentResolver(), Settings.Secure.SKIP_FIRST_USE_HINTS, "1", createUserWithThrow.id);
            if (isGuestUserAllowEphemeralStateChange() && Settings.Global.getInt(context.getContentResolver(), Settings.Global.REMOVE_GUEST_ON_EXIT, 1) == 1 && !createUserWithThrow.isEphemeral()) {
                setUserEphemeral(createUserWithThrow.id, true);
            }
            return createUserWithThrow;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException unused) {
            return null;
        }
    }

    @Deprecated
    public UserInfo findCurrentGuestUser() {
        try {
            List<UserInfo> guestUsers = this.mService.getGuestUsers();
            if (guestUsers.size() == 0) {
                return null;
            }
            return guestUsers.get(0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<UserInfo> getGuestUsers() {
        try {
            return this.mService.getGuestUsers();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public UserHandle createProfile(String str, String str2, Set<String> set) throws UserOperationException {
        try {
            return this.mService.createProfileForUserWithThrow(str, str2, 0, this.mUserId, (String[]) set.toArray(new String[set.size()])).getUserHandle();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            return (UserHandle) this.returnNullOrThrowUserOperationException(e2, this.mContext.getApplicationInfo().targetSdkVersion >= 30);
        }
    }

    @Deprecated
    public UserInfo createProfileForUser(String str, int i, int i2) {
        return createProfileForUser(str, UserInfo.getDefaultUserType(i), i, i2, null);
    }

    public UserInfo createProfileForUser(String str, String str2, int i, int i2) {
        return createProfileForUser(str, str2, i, i2, null);
    }

    public UserInfo createProfileForUser(String str, String str2, int i, int i2, String[] strArr) {
        try {
            return this.mService.createProfileForUserWithThrow(str, str2, i, i2, strArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException unused) {
            return null;
        }
    }

    public UserInfo createProfileForUserEvenWhenDisallowed(String str, String str2, int i, int i2, String[] strArr) {
        try {
            return this.mService.createProfileForUserEvenWhenDisallowedWithThrow(str, str2, i, i2, strArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException unused) {
            return null;
        }
    }

    public UserInfo createRestrictedProfile(String str) {
        try {
            int i = this.mUserId;
            UserInfo createRestrictedProfileWithThrow = this.mService.createRestrictedProfileWithThrow(str, i);
            AccountManager.get(this.mContext).addSharedAccountsFromParentUser(UserHandle.of(i), UserHandle.of(createRestrictedProfileWithThrow.id));
            return createRestrictedProfileWithThrow;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException unused) {
            return null;
        }
    }

    public static Intent createUserCreationIntent(String str, String str2, String str3, PersistableBundle persistableBundle) {
        Intent intent = new Intent(ACTION_CREATE_USER);
        if (str != null) {
            intent.putExtra(EXTRA_USER_NAME, str);
        }
        if (str2 != null && str3 == null) {
            throw new IllegalArgumentException("accountType must be specified if accountName is specified");
        }
        if (str2 != null) {
            intent.putExtra(EXTRA_USER_ACCOUNT_NAME, str2);
        }
        if (str3 != null) {
            intent.putExtra(EXTRA_USER_ACCOUNT_TYPE, str3);
        }
        if (persistableBundle != null) {
            intent.putExtra(EXTRA_USER_ACCOUNT_OPTIONS, persistableBundle);
        }
        return intent;
    }

    public Set<String> getPreInstallableSystemPackages(String str) {
        try {
            String[] preInstallableSystemPackages = this.mService.getPreInstallableSystemPackages(str);
            if (preInstallableSystemPackages == null) {
                return null;
            }
            return new ArraySet(preInstallableSystemPackages);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getSeedAccountName() {
        try {
            return this.mService.getSeedAccountName(getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getSeedAccountType() {
        try {
            return this.mService.getSeedAccountType(getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public PersistableBundle getSeedAccountOptions() {
        try {
            return this.mService.getSeedAccountOptions(getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSeedAccountData(int i, String str, String str2, PersistableBundle persistableBundle) {
        try {
            this.mService.setSeedAccountData(i, str, str2, persistableBundle, true);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void clearSeedAccountData() {
        try {
            this.mService.clearSeedAccountData(getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean markGuestForDeletion(int i) {
        try {
            return this.mService.markGuestForDeletion(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserEnabled(int i) {
        try {
            this.mService.setUserEnabled(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserAdmin(int i) {
        try {
            this.mService.setUserAdmin(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void revokeUserAdmin(int i) {
        try {
            this.mService.revokeUserAdmin(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void evictCredentialEncryptionKey(int i) {
        try {
            this.mService.evictCredentialEncryptionKey(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserCount() {
        List<UserInfo> users = getUsers();
        if (users != null) {
            return users.size();
        }
        return 1;
    }

    public List<UserInfo> getUsers() {
        return getUsers(true, false, true);
    }

    public List<UserInfo> getAliveUsers() {
        return getUsers(true, true, true);
    }

    @Deprecated
    public List<UserInfo> getUsers(boolean z) {
        return getUsers(true, z, true);
    }

    @Deprecated
    public List<UserInfo> getUsers(boolean z, boolean z2, boolean z3) {
        try {
            return this.mService.getUsers(z, z2, z3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<UserHandle> getUserHandles(boolean z) {
        List<UserInfo> users = getUsers(true, z, true);
        ArrayList arrayList = new ArrayList(users.size());
        Iterator<UserInfo> it = users.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getUserHandle());
        }
        return arrayList;
    }

    @SystemApi
    public long[] getSerialNumbersOfUsers(boolean z) {
        int size = getUsers(true, z, true).size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = r4.get(i).serialNumber;
        }
        return jArr;
    }

    public String getUserAccount(int i) {
        try {
            return this.mService.getUserAccount(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserAccount(int i, String str) {
        try {
            this.mService.setUserAccount(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public UserInfo getPrimaryUser() {
        try {
            return this.mService.getPrimaryUser();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public UserHandle getPreviousForegroundUser() {
        try {
            int previousFullUserToEnterForeground = this.mService.getPreviousFullUserToEnterForeground();
            if (previousFullUserToEnterForeground == -10000) {
                return null;
            }
            return UserHandle.of(previousFullUserToEnterForeground);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean canAddMoreUsers() {
        List<UserInfo> aliveUsers = getAliveUsers();
        int size = aliveUsers.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (!aliveUsers.get(i2).isGuest()) {
                i++;
            }
        }
        return i < getMaxSupportedUsers();
    }

    public boolean canAddMoreUsers(String str) {
        try {
            if (str.equals(USER_TYPE_FULL_GUEST)) {
                return this.mService.canAddMoreUsersOfType(str);
            }
            return canAddMoreUsers() && this.mService.canAddMoreUsersOfType(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getRemainingCreatableUserCount(String str) {
        Objects.requireNonNull(str, "userType must not be null");
        try {
            return this.mService.getRemainingCreatableUserCount(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getRemainingCreatableProfileCount(String str) {
        Objects.requireNonNull(str, "userType must not be null");
        try {
            return this.mService.getRemainingCreatableProfileCount(str, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canAddMoreManagedProfiles(int i, boolean z) {
        try {
            return this.mService.canAddMoreManagedProfiles(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canAddMoreProfilesToUser(String str, int i) {
        try {
            return this.mService.canAddMoreProfilesToUser(str, i, false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserTypeEnabled(String str) {
        try {
            return this.mService.isUserTypeEnabled(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<UserInfo> getProfiles(int i) {
        if (Flags.cacheProfilesReadOnly()) {
            return UserManagerCache.getProfiles(new IpcDataCache.RemoteCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda3
                @Override // android.os.IpcDataCache.RemoteCall
                public final Object apply(Object obj) {
                    List lambda$getProfiles$3;
                    lambda$getProfiles$3 = UserManager.this.lambda$getProfiles$3((Integer) obj);
                    return lambda$getProfiles$3;
                }
            }, Integer.valueOf(i));
        }
        try {
            return this.mService.getProfiles(i, false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List lambda$getProfiles$3(Integer num) throws RemoteException {
        return this.mService.getProfiles(num.intValue(), false);
    }

    public List<UserInfo> getProfilesIncludingCommunal(int i) {
        UserInfo userInfo;
        List<UserInfo> profiles = getProfiles(i);
        UserHandle communalProfile = getCommunalProfile();
        if (communalProfile != null && (userInfo = getUserInfo(communalProfile.getIdentifier())) != null) {
            profiles.add(userInfo);
        }
        return profiles;
    }

    @SystemApi
    public boolean isSameProfileGroup(UserHandle userHandle, UserHandle userHandle2) {
        return isSameProfileGroup(userHandle.getIdentifier(), userHandle2.getIdentifier());
    }

    public boolean isSameProfileGroup(int i, int i2) {
        try {
            return this.mService.isSameProfileGroup(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public List<UserInfo> getEnabledProfiles(int i) {
        try {
            return this.mService.getProfiles(i, true);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<UserHandle> getUserProfiles() {
        return convertUserIdsToUserHandles(getProfileIds(getContextUserIfAppropriate(), true));
    }

    @SystemApi
    public List<UserHandle> getEnabledProfiles() {
        return getProfiles(true);
    }

    @SystemApi
    public List<UserHandle> getAllProfiles() {
        return getProfiles(false);
    }

    private List<UserHandle> getProfiles(boolean z) {
        return convertUserIdsToUserHandles(getProfileIds(this.mUserId, z));
    }

    private List<UserHandle> convertUserIdsToUserHandles(int[] iArr) {
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(UserHandle.of(i));
        }
        return arrayList;
    }

    public int[] getProfileIds(int i, boolean z) {
        if (Flags.cacheProfileIdsReadOnly()) {
            return z ? getEnabledProfileIds(i) : getProfileIdsWithDisabled(i);
        }
        try {
            return this.mService.getProfileIds(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getProfileIdsWithDisabled(int i) {
        if (Flags.cacheProfileIdsReadOnly()) {
            return UserManagerCache.getProfileIdsWithDisabled(new IpcDataCache.RemoteCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda15
                @Override // android.os.IpcDataCache.RemoteCall
                public final Object apply(Object obj) {
                    int[] lambda$getProfileIdsWithDisabled$4;
                    lambda$getProfileIdsWithDisabled$4 = UserManager.this.lambda$getProfileIdsWithDisabled$4((Integer) obj);
                    return lambda$getProfileIdsWithDisabled$4;
                }
            }, Integer.valueOf(i));
        }
        return getProfileIds(i, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int[] lambda$getProfileIdsWithDisabled$4(Integer num) throws RemoteException {
        return this.mService.getProfileIds(num.intValue(), false);
    }

    public int[] getEnabledProfileIds(int i) {
        if (Flags.cacheProfileIdsReadOnly()) {
            return UserManagerCache.getEnabledProfileIds(new IpcDataCache.RemoteCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda8
                @Override // android.os.IpcDataCache.RemoteCall
                public final Object apply(Object obj) {
                    int[] lambda$getEnabledProfileIds$5;
                    lambda$getEnabledProfileIds$5 = UserManager.this.lambda$getEnabledProfileIds$5((Integer) obj);
                    return lambda$getEnabledProfileIds$5;
                }
            }, Integer.valueOf(i));
        }
        return getProfileIds(i, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int[] lambda$getEnabledProfileIds$5(Integer num) throws RemoteException {
        return this.mService.getProfileIds(num.intValue(), true);
    }

    public static final void invalidateEnabledProfileIds() {
        if (Flags.cacheProfileIdsReadOnly()) {
            UserManagerCache.invalidateEnabledProfileIds();
        }
    }

    public int[] getProfileIdsExcludingHidden(int i, boolean z) {
        try {
            return this.mService.getProfileIdsExcludingHidden(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getCredentialOwnerProfile(int i) {
        try {
            return this.mService.getCredentialOwnerProfile(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public UserInfo getProfileParent(int i) {
        try {
            return this.mService.getProfileParent(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public UserHandle getProfileParent(UserHandle userHandle) {
        if (Flags.cacheProfileParentReadOnly()) {
            UserHandle profileParent = UserManagerCache.getProfileParent(new IpcDataCache.RemoteCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda2
                @Override // android.os.IpcDataCache.RemoteCall
                public final Object apply(Object obj) {
                    UserHandle lambda$getProfileParent$6;
                    lambda$getProfileParent$6 = UserManager.this.lambda$getProfileParent$6((UserHandle) obj);
                    return lambda$getProfileParent$6;
                }
            }, userHandle);
            if (profileParent.getIdentifier() == -10000) {
                return null;
            }
            return profileParent;
        }
        UserInfo profileParent2 = getProfileParent(userHandle.getIdentifier());
        if (profileParent2 == null) {
            return null;
        }
        return UserHandle.of(profileParent2.id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ UserHandle lambda$getProfileParent$6(UserHandle userHandle) throws RemoteException {
        UserInfo profileParent = getProfileParent(userHandle.getIdentifier());
        if (profileParent == null) {
            return UserHandle.of(-10000);
        }
        return UserHandle.of(profileParent.id);
    }

    public boolean requestQuietModeEnabled(boolean z, UserHandle userHandle) {
        return requestQuietModeEnabled(z, userHandle, (IntentSender) null);
    }

    public boolean requestQuietModeEnabled(boolean z, UserHandle userHandle, int i) {
        return requestQuietModeEnabled(z, userHandle, null, i);
    }

    public boolean requestQuietModeEnabled(boolean z, UserHandle userHandle, IntentSender intentSender) {
        return requestQuietModeEnabled(z, userHandle, intentSender, 0);
    }

    public boolean requestQuietModeEnabled(boolean z, UserHandle userHandle, IntentSender intentSender, int i) {
        try {
            return this.mService.requestQuietModeEnabled(this.mContext.getPackageName(), z, userHandle.getIdentifier(), intentSender, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final void invalidateQuietModeEnabledCache() {
        UserManagerCache.invalidateQuietModeEnabled();
    }

    public boolean isQuietModeEnabled(UserHandle userHandle) {
        if (Flags.cacheQuietModeState()) {
            if (userHandle.getIdentifier() < 0) {
                return false;
            }
            return ((UserManagerCache) this.mIpcDataCache).isQuietModeEnabled(new IpcDataCache.RemoteCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda4
                @Override // android.os.IpcDataCache.RemoteCall
                public final Object apply(Object obj) {
                    Boolean lambda$isQuietModeEnabled$7;
                    lambda$isQuietModeEnabled$7 = UserManager.this.lambda$isQuietModeEnabled$7((UserHandle) obj);
                    return lambda$isQuietModeEnabled$7;
                }
            }, userHandle).booleanValue();
        }
        try {
            return this.mService.isQuietModeEnabled(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$isQuietModeEnabled$7(UserHandle userHandle) throws RemoteException {
        return Boolean.valueOf(this.mService.isQuietModeEnabled(userHandle.getIdentifier()));
    }

    public boolean hasBadge(int i) {
        if (!isProfile(i)) {
            return false;
        }
        try {
            return this.mService.hasBadge(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasBadge() {
        return hasBadge(this.mUserId);
    }

    public int getUserBadgeColor(int i) {
        try {
            return Resources.getSystem().getColor(this.mService.getUserBadgeColorResId(i), null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserBadgeDarkColor(int i) {
        try {
            return Resources.getSystem().getColor(this.mService.getUserBadgeDarkColorResId(i), null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserIconBadgeResId(int i) {
        try {
            return this.mService.getUserIconBadgeResId(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserBadgeResId(int i) {
        try {
            return this.mService.getUserBadgeResId(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserBadgeNoBackgroundResId(int i) {
        try {
            return this.mService.getUserBadgeNoBackgroundResId(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserStatusBarIconResId(int i) {
        try {
            return this.mService.getUserStatusBarIconResId(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Drawable getBadgedIconForUser(Drawable drawable, UserHandle userHandle) {
        return this.mContext.getPackageManager().getUserBadgedIcon(drawable, userHandle);
    }

    public Drawable getBadgedDrawableForUser(Drawable drawable, UserHandle userHandle, Rect rect, int i) {
        return this.mContext.getPackageManager().getUserBadgedDrawableForDensity(drawable, userHandle, rect, i);
    }

    @SystemApi
    public Drawable getUserBadge() {
        if (!isProfile(this.mUserId)) {
            throw new Resources.NotFoundException("No badge found for this user.");
        }
        if (isManagedProfile(this.mUserId)) {
            return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getDrawable(DevicePolicyResources.Drawables.WORK_PROFILE_ICON_BADGE, DevicePolicyResources.Drawables.Style.SOLID_COLORED, new Supplier() { // from class: android.os.UserManager$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    Drawable lambda$getUserBadge$8;
                    lambda$getUserBadge$8 = UserManager.this.lambda$getUserBadge$8();
                    return lambda$getUserBadge$8;
                }
            });
        }
        return getDefaultUserBadge(this.mUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Drawable lambda$getUserBadge$8() {
        return getDefaultUserBadge(this.mUserId);
    }

    private Drawable getDefaultUserBadge(int i) {
        return this.mContext.getResources().getDrawable(getUserBadgeResId(i), this.mContext.getTheme());
    }

    public CharSequence getBadgedLabelForUser(final CharSequence charSequence, UserHandle userHandle) {
        final int identifier = userHandle.getIdentifier();
        return !hasBadge(identifier) ? charSequence : ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString(getUpdatableUserBadgedLabelId(identifier), new Supplier() { // from class: android.os.UserManager$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getBadgedLabelForUser$9;
                lambda$getBadgedLabelForUser$9 = UserManager.this.lambda$getBadgedLabelForUser$9(charSequence, identifier);
                return lambda$getBadgedLabelForUser$9;
            }
        }, charSequence);
    }

    private String getUpdatableUserBadgedLabelId(int i) {
        return isManagedProfile(i) ? DevicePolicyResources.Strings.Core.WORK_PROFILE_BADGED_LABEL : DevicePolicyResources.UNDEFINED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultUserBadgedLabel, reason: merged with bridge method [inline-methods] */
    public String lambda$getBadgedLabelForUser$9(CharSequence charSequence, int i) {
        try {
            return Resources.getSystem().getString(this.mService.getUserBadgeLabelResId(i), charSequence);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getProfileLabel() {
        if (isManagedProfile(this.mUserId)) {
            return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB, new Supplier() { // from class: android.os.UserManager$$ExternalSyntheticLambda12
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$getProfileLabel$10;
                    lambda$getProfileLabel$10 = UserManager.this.lambda$getProfileLabel$10();
                    return lambda$getProfileLabel$10;
                }
            });
        }
        int i = this.mUserId;
        if (i >= 150 && i <= 160) {
            String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), Settings.Secure.SECURE_FOLDER_NAME, 0);
            Log.e(TAG, "getProfileLabel userid " + this.mUserId + "  name" + stringForUser);
            if (stringForUser != null) {
                Log.e(TAG, "it is custom case.  name" + stringForUser);
                return stringForUser;
            }
            return this.mContext.getString(R.string.profile_label_SecureFolder);
        }
        return getDefaultProfileLabel(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getProfileLabel$10() {
        return getDefaultProfileLabel(this.mUserId);
    }

    private String getDefaultProfileLabel(int i) {
        try {
            return Resources.getSystem().getString(this.mService.getProfileLabelResId(i));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getProfileAccessibilityString(final int i) {
        if (isManagedProfile(this.mUserId)) {
            ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.SystemUi.STATUS_BAR_WORK_ICON_ACCESSIBILITY, new Supplier() { // from class: android.os.UserManager$$ExternalSyntheticLambda14
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$getProfileAccessibilityString$11;
                    lambda$getProfileAccessibilityString$11 = UserManager.this.lambda$getProfileAccessibilityString$11(i);
                    return lambda$getProfileAccessibilityString$11;
                }
            });
        }
        return lambda$getProfileAccessibilityString$11(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getProfileAccessibilityLabel, reason: merged with bridge method [inline-methods] */
    public String lambda$getProfileAccessibilityString$11(int i) {
        try {
            return Resources.getSystem().getString(this.mService.getProfileAccessibilityLabelResId(i));
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Accessibility label not defined for user " + i);
            throw e;
        } catch (RemoteException e2) {
            throw new RuntimeException(e2);
        }
    }

    @SystemApi
    @Deprecated
    public boolean isMediaSharedWithParent() {
        try {
            return getUserProperties(UserHandle.of(this.mUserId)).isMediaSharedWithParent();
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public boolean isCredentialSharableWithParent() {
        try {
            return getUserProperties(UserHandle.of(this.mUserId)).isCredentialShareableWithParent();
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public boolean removeUser(int i) {
        try {
            return this.mService.removeUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean removeUser(UserHandle userHandle) {
        if (userHandle == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        return removeUser(userHandle.getIdentifier());
    }

    public boolean removeUserEvenWhenDisallowed(int i) {
        try {
            return this.mService.removeUserEvenWhenDisallowed(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int removeUserWhenPossible(UserHandle userHandle, boolean z) {
        try {
            return this.mService.removeUserWhenPossible(userHandle.getIdentifier(), z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserName(int i, String str) {
        try {
            this.mService.setUserName(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setUserEphemeral(int i, boolean z) {
        try {
            return this.mService.setUserEphemeral(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setUserName(String str) {
        setUserName(this.mUserId, str);
    }

    public void setUserIcon(int i, Bitmap bitmap) {
        try {
            this.mService.setUserIcon(i, bitmap);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException unused) {
        }
    }

    @SystemApi
    public void setUserIcon(Bitmap bitmap) throws UserOperationException {
        setUserIcon(this.mUserId, bitmap);
    }

    public Bitmap getUserIcon(int i) {
        Bitmap bmodeIconIfValidUser;
        try {
            ParcelFileDescriptor userIcon = this.mService.getUserIcon(i);
            if (userIcon != null) {
                try {
                    return BitmapFactory.decodeFileDescriptor(userIcon.getFileDescriptor());
                } finally {
                    try {
                        userIcon.close();
                    } catch (IOException unused) {
                    }
                }
            }
            if (!PMRune.UM_BMODE || (bmodeIconIfValidUser = MultiUserSupportsHelper.getBmodeIconIfValidUser(getUsers(), i, this.mContext)) == null) {
                return null;
            }
            return bmodeIconIfValidUser;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Bitmap getUserIcon() {
        return getUserIcon(this.mUserId);
    }

    public static int getMaxSupportedUsers() {
        return MultiUserSupportsHelper.getMaxSupportedUsers();
    }

    public boolean isUserSwitcherEnabled() {
        return isUserSwitcherEnabled(true);
    }

    public boolean isUserSwitcherEnabled(boolean z) {
        try {
            return this.mService.isUserSwitcherEnabled(z, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isDeviceInDemoMode(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.DEVICE_DEMO_MODE, 0) > 0;
    }

    public static final void invalidateCacheOnUserListChange() {
        UserManagerCache.invalidateUserSerialNumber();
        if (Flags.cacheProfileParentReadOnly()) {
            UserManagerCache.invalidateProfileParent();
        }
        invalidateEnabledProfileIds();
        invalidateUserRestriction();
    }

    public static final void invalidateOnUserInfoFlagChange(int i) {
        if ((i & 64) > 0) {
            invalidateEnabledProfileIds();
        }
    }

    public static final void invalidateCacheOnUserDataChanged() {
        if (Flags.cacheProfilesReadOnly() || Flags.cacheUserInfoReadOnly()) {
            UserManagerCache.invalidateProfiles();
        }
    }

    public int getUserSerialNumber(int i) {
        if (!Flags.cacheUserSerialNumberReadOnly() && !Flags.cacheUserSerialNumber()) {
            try {
                return this.mService.getUserSerialNumber(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (i == 0) {
            return 0;
        }
        UserManagerCache userManagerCache = (UserManagerCache) this.mIpcDataCache;
        final IUserManager iUserManager = this.mService;
        Objects.requireNonNull(iUserManager);
        return userManagerCache.getUserSerialNumber(new IpcDataCache.RemoteCall() { // from class: android.os.UserManager$$ExternalSyntheticLambda13
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                return Integer.valueOf(IUserManager.this.getUserSerialNumber(((Integer) obj).intValue()));
            }
        }, Integer.valueOf(i)).intValue();
    }

    public int getUserHandle(int i) {
        try {
            return this.mService.getUserHandle(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getApplicationRestrictions(String str) {
        try {
            return this.mService.getApplicationRestrictionsForUser(str, getContextUserIfAppropriate());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getApplicationRestrictions(String str, UserHandle userHandle) {
        try {
            return this.mService.getApplicationRestrictionsForUser(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setApplicationRestrictions(String str, Bundle bundle, UserHandle userHandle) {
        try {
            this.mService.setApplicationRestrictions(str, bundle, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDefaultGuestRestrictions(Bundle bundle) {
        try {
            this.mService.setDefaultGuestRestrictions(bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getDefaultGuestRestrictions() {
        try {
            return this.mService.getDefaultGuestRestrictions();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getUserCreationTime(UserHandle userHandle) {
        try {
            return this.mService.getUserCreationTime(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean someUserHasSeedAccount(String str, String str2) {
        try {
            return this.mService.someUserHasSeedAccount(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean someUserHasAccount(String str, String str2) {
        Objects.requireNonNull(str, "accountName must not be null");
        Objects.requireNonNull(str2, "accountType must not be null");
        try {
            return this.mService.someUserHasAccount(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setBootUser(UserHandle userHandle) {
        try {
            this.mService.setBootUser(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public UserHandle getBootUser() {
        try {
            return UserHandle.of(this.mService.getBootUser());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final void invalidateStaticUserProperties() {
        PropertyInvalidatedCache.invalidateCache(CACHE_KEY_STATIC_USER_PROPERTIES);
    }

    @SystemApi
    public static final class EnforcingUser implements Parcelable {
        public static final Parcelable.Creator<EnforcingUser> CREATOR = new Parcelable.Creator<EnforcingUser>() { // from class: android.os.UserManager.EnforcingUser.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EnforcingUser createFromParcel(Parcel parcel) {
                return new EnforcingUser(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EnforcingUser[] newArray(int i) {
                return new EnforcingUser[i];
            }
        };
        private final int userId;
        private final int userRestrictionSource;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public EnforcingUser(int i, int i2) {
            this.userId = i;
            this.userRestrictionSource = i2;
        }

        private EnforcingUser(Parcel parcel) {
            this.userId = parcel.readInt();
            this.userRestrictionSource = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.userId);
            parcel.writeInt(this.userRestrictionSource);
        }

        public UserHandle getUserHandle() {
            return UserHandle.of(this.userId);
        }

        public int getUserRestrictionSource() {
            return this.userRestrictionSource;
        }
    }

    public List<SemUserInfo> semGetUsers() {
        try {
            List<UserInfo> users = this.mService.getUsers(true, false, true);
            ArrayList arrayList = new ArrayList();
            Iterator<UserInfo> it = users.iterator();
            while (it.hasNext()) {
                arrayList.add(new SemUserInfo(it.next()));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semIsLinkedUser() {
        return isLinkedUser();
    }

    public boolean semIsManagedProfile() {
        return isManagedProfile();
    }

    public SemUserInfo semGetSemUserInfo(int i) {
        UserInfo userInfo = getUserInfo(i);
        if (userInfo == null) {
            return null;
        }
        return new SemUserInfo(userInfo);
    }

    public boolean semHasBaseUserRestriction(String str, UserHandle userHandle) {
        try {
            return this.mService.hasBaseUserRestriction(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semHasUserRestriction(String str, UserHandle userHandle) {
        try {
            return this.mService.hasUserRestriction(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semIsGuestUser() {
        return isGuestUser();
    }

    public SemUserInfo semCreateUser(String str, int i) {
        UserInfo createUser = createUser(str, UserInfo.getDefaultUserType(i), i);
        if (createUser == null) {
            return null;
        }
        return new SemUserInfo(createUser);
    }

    public boolean semRemoveUser(int i) {
        return removeUser(i);
    }

    public boolean updateUserInfo(int i, Bundle bundle) {
        try {
            return this.mService.updateUserInfo(i, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
