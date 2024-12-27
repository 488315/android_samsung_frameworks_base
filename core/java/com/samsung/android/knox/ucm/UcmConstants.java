package com.samsung.android.knox.ucm;

public interface UcmConstants {
    public static final String ACTION_UCM_COMPLETED_PROVISIONING_CANCELLED =
            "com.samsung.android.knox.ucm.action.UCM_COMPLETED_PROVISIONING_CANCELLED";
    public static final String ACTION_UCM_COMPLETED_PROVISIONING_ERROR =
            "com.samsung.android.knox.ucm.action.UCM_COMPLETED_PROVISIONING_ERROR";
    public static final String ACTION_UCM_COMPLETED_PROVISIONING_SUCCESS =
            "com.samsung.android.knox.ucm.action.UCM_COMPLETED_PROVISIONING_SUCCESS";
    public static final String ACTION_UCM_STARTED_PROVISIONING_CANCELLED =
            "com.samsung.android.knox.ucm.action.UCM_STARTED_PROVISIONING_CANCELLED";
    public static final String ACTION_UCM_STARTED_PROVISIONING_ERROR =
            "com.samsung.android.knox.ucm.action.UCM_STARTED_PROVISIONING_ERROR";
    public static final String ACTION_UCM_STARTED_PROVISIONING_SUCCESS =
            "com.samsung.android.knox.ucm.action.UCM_STARTED_PROVISIONING_SUCCESS";
    public static final String CANCEL_DATA_LOCK = "CANCEL_DATA_LOCK";
    public static final String CLEAR_APP_DATA = "CLEAR_APP_DATA";
    public static final String CLEAR_RESET_PASSWORD_TOKEN = "CLEAR_RESET_PASSWORD_TOKEN";
    public static final String CLEAR_TRIAL_PERIOD_TIME = "CLEAR_TRIAL_PERIOD_TIME";
    public static final String CLIENT_PACKAGE_NAME = "CLIENT_PACKAGE_NAME";
    public static final String CLIENT_PACKAGE_SIGNATURE = "CLIENT_PACKAGE_SIGNATURE";
    public static final String CLIENT_VERSION = "CLIENT_VERSION";
    public static final String CRYPTO_FILE_INFO = "CRYPTO_FILE_INFO";
    public static final String CRYPTO_PATH = "CRYPTO_PATH";
    public static final String CRYPTO_TYPE = "CRYPTO_TYPE";
    public static final String CRYPTO_TYPE_CUSTOM = "CRYPTO_TYPE_CUSTOM";
    public static final String CRYPTO_TYPE_NATIVE = "CRYPTO_TYPE_NATIVE";
    public static final String CURRENT_STATE = "CURRENT_STATE";
    public static final String ENSURE_DATA_UNLOCKED = "ENSURE_DATA_UNLOCKED";
    public static final String ERROR_CODE = "ERROR_CODE";
    public static final String EVICT = "EVICT";
    public static final String EXISTING_PASSWORD = "EXISTING_PASSWORD";
    public static final String EXPIRE_TRIAL_PERIOD_TIME = "EXPIRE_TRIAL_PERIOD_TIME";
    public static final String FEATURE = "FEATURE";
    public static final String FETCH_OUTERLAYER_KEY = "FETCH_OUTERLAYER_KEY";
    public static final int FLAG_UCM_CLIENT_SETUP_START = 500;
    public static final String FSLOG_FILE_INFO = "FSLOG_FILE_INFO";
    public static final String FWLOG_FILE_INFO = "FWLOG_FILE_INFO";
    public static final String GET_UCM_CONFIG = "GET_UCM_CONFIG";
    public static final String INNER_LAYER_SECRET = "INNER_LAYER_SECRET";
    public static final String IS_CREATION = "IS_CREATION";
    public static final String IS_DATA_LOCKED = "is_data_locked";
    public static final String IS_INNER_LAYER_UNLOCKED = "IS_INNER_LAYER_UNLOCKED";
    public static final String IS_READY = "IS_READY";
    public static final String IS_SUPPORTED = "IS_SUPPORTED";
    public static final String IS_UCM_CREATION_CRYPTO_TYPE = "IS_UCM_CREATION_CRYPTO_TYPE";
    public static final String IS_UCM_INTENT_PROVISIONING = "IS_UCM_INTENT_PROVISIONING";
    public static final String IS_UCM_MANAGED_DEVICE = "IS_UCM_MANAGED_DEVICE";
    public static final String IS_UCM_PROVISIONED_WITHOUT_LICENSE =
            "IS_UCM_PROVISIONED_WITHOUT_LICENSE";
    public static final String IS_UCM_TRIAL_PERIOD = "IS_UCM_TRIAL_PERIOD";
    public static final String IS_UCM_WPCOD = "IS_UCM_WPCOD";
    public static final String KEY_CLIENT_LIBRARY_NAME = "KEY_CLIENT_LIBRARY_NAME";
    public static final String NEW_PASSWORD = "NEW_PASSWORD";
    public static final String ON_BEFORE_UNLOCK_USER = "ON_BEFORE_UNLOCK_USER";
    public static final String ON_BRINGUP = "ON_BRINGUP";
    public static final String ON_DATA_LOCK_STATE_CHANGE = "ON_DATA_LOCK_STATE_CHANGE";
    public static final String ON_DDAR_STATE_CHANGED = "ON_DDAR_STATE_CHANGED";
    public static final String ON_DEVICE_OWNER_PROVISIONING = "ON_DEVICE_OWNER_PROVISIONING";
    public static final String ON_EVENT = "ON_EVENT";
    public static final String ON_PASSWORD1_CHANGE = "ON_PASSWORD1_CHANGE";
    public static final String ON_PASSWORD2_AUTH = "ON_PASSWORD2_AUTH";
    public static final String ON_PASSWORD2_CHANGE = "ON_PASSWORD2_CHANGE";
    public static final String ON_USER_REMOVED = "ON_USER_REMOVED";
    public static final String ON_USER_START = "ON_USER_START";
    public static final String ON_USER_STOPPED = "ON_USER_STOPPED";
    public static final String ON_WORKSPACE_CREATION = "ON_WORKSPACE_CREATION";
    public static final String ON_WORKSPACE_DESTROY = "ON_WORKSPACE_DESTROY";
    public static final String OUTER_LAYER_SECRET = "OUTER_LAYER_SECRET";
    public static final String PKG_BLOCKED_CLEARABLE = "pkg_blocked_clearable";
    public static final String PKG_CLEARABLE_SYSTEM = "pkg_clearable_system";
    public static final String PKG_NOT_CLEARABLE_SYSTEM = "pkg_not_clearable_system";
    public static final String PKG_NOT_SYSTEM = "pkg_not_system";
    public static final String PKG_SYSTEM = "pkg_sys";
    public static final String PLATFORM_VERSION = "PLATFORM_VERSION";
    public static final String PREVIOUS_STATE = "PREVIOUS_STATE";
    public static final String PROP_PERSIST_SYS_UCM_DO = "persist.sys.UCM.do";
    public static final String PROP_PERSIST_SYS_UCM_INFO_UPDATE = "persist.sys.UCMinfo.update";
    public static final String RELAY_FILE_INFO = "RELAY_FILE_INFO";
    public static final String RESET_PASSWORD_TOKEN = "RESET_PASSWORD_TOKEN";
    public static final String RESET_PASSWORD_TOKEN_HANDLE = "RESET_PASSWORD_TOKEN_HANDLE";
    public static final String RESET_PASSWORD_WITH_TOKEN = "RESET_PASSWORD_WITH_TOKEN";
    public static final String SCHEDULE_DATA_LOCK = "SCHEDULE_DATA_LOCK";
    public static final String SET_CLIENT_INFO = "SET_CLIENT_INFO";
    public static final String SET_RESET_PASSWORD_TOKEN = "SET_RESET_PASSWORD_TOKEN";
    public static final String SET_TRIAL_PERIOD_TIME = "SET_TRIAL_PERIOD_TIME";
    public static final String STORAGE_TYPE = "STORAGE_TYPE";
    public static final String TRIAL_SCHEDULED_TIME = "TRIAL_SCHEDULED_TIME";
    public static final String UCM_ADMIN_PACKAGE = "UCM_ADMIN_PACKAGE";
    public static final String UCM_AGENT_METADATA = "ucmproxyAgent.class";
    public static final String UCM_CORE_AGENT = "com.samsung.android.knox.containercore";
    public static final String UCM_CRYPTO_TYPE = "UCM_CRYPTO_TYPE";
    public static final String UCM_INTENT_PROVISIONING = "UCM_INTENT_PROVISIONING";
    public static final String UCM_IS_MANAGED_DEVICE = "UCM_IS_MANAGED_DEVICE";
    public static final String UCM_IS_WPCOD = "UCM_IS_WPCOD";
    public static final String UCM_PARAMS = "UCM_DAR_PARAMS";
    public static final String UCM_RESPONSE = "UCM_response";
    public static final String UCM_RESPONSE_MESSAGE = "UCM_response_message";
    public static final String UCM_SERVICE_EVENT_FLAG = "UCMServiceEventFlag";
    public static final String USERS = "USERS";
    public static final String USER_ID = "user_id";
}
