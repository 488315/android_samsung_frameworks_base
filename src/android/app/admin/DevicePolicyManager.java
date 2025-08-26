package android.app.admin;

import android.Manifest;
import android.accounts.Account;
import android.annotation.SystemApi;
import android.app.IServiceConnection;
import android.app.admin.IAuditLogEventsCallback;
import android.app.admin.PreferentialNetworkServiceConfig;
import android.app.admin.SecurityLog;
import android.app.admin.StartInstallingUpdateCallback;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.net.PrivateDnsConnectivityChecker;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IpcDataCache;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.os.UserManager;
import android.sec.enterprise.content.SecContentProviderURI;
import android.security.AttestedKeyPair;
import android.security.Credentials;
import android.security.KeyChain;
import android.security.KeyChainException;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keystore.AttestationUtils;
import android.security.keystore.KeyAttestationException;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.ParcelableKeyGenParameterSpec;
import android.security.keystore.StrongBoxUnavailableException;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.Log;
import android.util.Pair;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.Flags;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.net.NetworkUtilsInternal;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.Zygote;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.org.conscrypt.TrustedCertificateStore;
import com.samsung.android.security.SemSdCardEncryption;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class DevicePolicyManager {

    @SystemApi
    public static final String ACCOUNT_FEATURE_DEVICE_OR_PROFILE_OWNER_ALLOWED = "android.account.DEVICE_OR_PROFILE_OWNER_ALLOWED";

    @SystemApi
    public static final String ACCOUNT_FEATURE_DEVICE_OR_PROFILE_OWNER_DISALLOWED = "android.account.DEVICE_OR_PROFILE_OWNER_DISALLOWED";
    public static final String ACTION_ADD_DEVICE_ADMIN = "android.app.action.ADD_DEVICE_ADMIN";
    public static final String ACTION_ADMIN_POLICY_COMPLIANCE = "android.app.action.ADMIN_POLICY_COMPLIANCE";
    public static final String ACTION_APPLICATION_DELEGATION_SCOPES_CHANGED = "android.app.action.APPLICATION_DELEGATION_SCOPES_CHANGED";

    @SystemApi
    public static final String ACTION_BIND_SECONDARY_LOCKSCREEN_SERVICE = "android.app.action.BIND_SECONDARY_LOCKSCREEN_SERVICE";
    public static final String ACTION_BUGREPORT_SHARING_ACCEPTED = "com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED";
    public static final String ACTION_BUGREPORT_SHARING_DECLINED = "com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED";
    public static final String ACTION_CHECK_POLICY_COMPLIANCE = "android.app.action.CHECK_POLICY_COMPLIANCE";
    public static final String ACTION_DATA_SHARING_RESTRICTION_APPLIED = "android.app.action.DATA_SHARING_RESTRICTION_APPLIED";
    public static final String ACTION_DEVICE_ADMIN_SERVICE = "android.app.action.DEVICE_ADMIN_SERVICE";
    public static final String ACTION_DEVICE_FINANCING_STATE_CHANGED = "android.app.admin.action.DEVICE_FINANCING_STATE_CHANGED";
    public static final String ACTION_DEVICE_OWNER_CHANGED = "android.app.action.DEVICE_OWNER_CHANGED";
    public static final String ACTION_DEVICE_POLICY_CONSTANTS_CHANGED = "android.app.action.DEVICE_POLICY_CONSTANTS_CHANGED";
    public static final String ACTION_DEVICE_POLICY_MANAGER_PASSWORD_CHANGED = "com.samsung.knox.app.action.DEVICE_POLICY_MANAGER_PASSWORD_CHANGED";
    public static final String ACTION_DEVICE_POLICY_MANAGER_STATE_CHANGED = "android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED";
    public static final String ACTION_DEVICE_POLICY_RESOURCE_UPDATED = "android.app.action.DEVICE_POLICY_RESOURCE_UPDATED";

    @SystemApi
    public static final String ACTION_ESTABLISH_NETWORK_CONNECTION = "android.app.action.ESTABLISH_NETWORK_CONNECTION";
    public static final String ACTION_GET_PROVISIONING_MODE = "android.app.action.GET_PROVISIONING_MODE";

    @SystemApi
    public static final String ACTION_LOST_MODE_LOCATION_UPDATE = "android.app.action.LOST_MODE_LOCATION_UPDATE";
    public static final String ACTION_MANAGED_PROFILE_PROVISIONED = "android.app.action.MANAGED_PROFILE_PROVISIONED";
    public static final String ACTION_PROFILE_OWNER_CHANGED = "android.app.action.PROFILE_OWNER_CHANGED";
    public static final String ACTION_PROVISIONING_COMPLETED = "android.app.action.PROVISIONING_COMPLETED";
    public static final String ACTION_PROVISIONING_SUCCESSFUL = "android.app.action.PROVISIONING_SUCCESSFUL";

    @SystemApi
    public static final String ACTION_PROVISION_FINALIZATION = "android.app.action.PROVISION_FINALIZATION";

    @SystemApi
    public static final String ACTION_PROVISION_FINANCED_DEVICE = "android.app.action.PROVISION_FINANCED_DEVICE";

    @Deprecated
    public static final String ACTION_PROVISION_MANAGED_DEVICE = "android.app.action.PROVISION_MANAGED_DEVICE";

    @SystemApi
    public static final String ACTION_PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE = "android.app.action.PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE";
    public static final String ACTION_PROVISION_MANAGED_PROFILE = "android.app.action.PROVISION_MANAGED_PROFILE";
    public static final String ACTION_PROVISION_MANAGED_USER = "android.app.action.PROVISION_MANAGED_USER";
    public static final String ACTION_REMOTE_BUGREPORT_DISPATCH = "android.intent.action.REMOTE_BUGREPORT_DISPATCH";

    @SystemApi
    public static final String ACTION_RESET_PROTECTION_POLICY_CHANGED = "android.app.action.RESET_PROTECTION_POLICY_CHANGED";

    @SystemApi
    public static final String ACTION_ROLE_HOLDER_PROVISION_FINALIZATION = "android.app.action.ROLE_HOLDER_PROVISION_FINALIZATION";

    @SystemApi
    public static final String ACTION_ROLE_HOLDER_PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE = "android.app.action.ROLE_HOLDER_PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE";

    @SystemApi
    public static final String ACTION_ROLE_HOLDER_PROVISION_MANAGED_PROFILE = "android.app.action.ROLE_HOLDER_PROVISION_MANAGED_PROFILE";
    public static final String ACTION_SET_NEW_PARENT_PROFILE_PASSWORD = "android.app.action.SET_NEW_PARENT_PROFILE_PASSWORD";
    public static final String ACTION_SET_NEW_PASSWORD = "android.app.action.SET_NEW_PASSWORD";

    @SystemApi
    public static final String ACTION_SET_PROFILE_OWNER = "android.app.action.SET_PROFILE_OWNER";
    public static final String ACTION_SHOW_DEVICE_MONITORING_DIALOG = "android.app.action.SHOW_DEVICE_MONITORING_DIALOG";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final String ACTION_SHOW_NEW_USER_DISCLAIMER = "android.app.action.SHOW_NEW_USER_DISCLAIMER";
    public static final String ACTION_START_ENCRYPTION = "android.app.action.START_ENCRYPTION";

    @SystemApi
    @Deprecated
    public static final String ACTION_STATE_USER_SETUP_COMPLETE = "android.app.action.STATE_USER_SETUP_COMPLETE";
    public static final String ACTION_SYSTEM_UPDATE_POLICY_CHANGED = "android.app.action.SYSTEM_UPDATE_POLICY_CHANGED";

    @SystemApi
    public static final String ACTION_UPDATE_DEVICE_POLICY_MANAGEMENT_ROLE_HOLDER = "android.app.action.UPDATE_DEVICE_POLICY_MANAGEMENT_ROLE_HOLDER";
    public static final String ADD_ISFINANCED_DEVICE_FLAG = "add-isfinanced-device";
    public static final boolean ADD_ISFINANCED_FEVICE_DEFAULT = true;
    public static final int APP_FUNCTIONS_DISABLED = 1;
    public static final int APP_FUNCTIONS_DISABLED_CROSS_PROFILE = 2;
    public static final int APP_FUNCTIONS_NOT_CONTROLLED_BY_POLICY = 0;
    public static final int AUTO_TIME_DISABLED = 1;
    public static final int AUTO_TIME_ENABLED = 2;
    public static final int AUTO_TIME_NOT_CONTROLLED_BY_POLICY = 0;
    public static final int AUTO_TIME_ZONE_DISABLED = 1;
    public static final int AUTO_TIME_ZONE_ENABLED = 2;
    public static final int AUTO_TIME_ZONE_NOT_CONTROLLED_BY_POLICY = 0;
    public static final int CODE_DEVICE_COMPROMISED = 20;
    public static final int CODE_KNOXGUARD_DEVICE_OWNER_BLOCK = 23;
    public static final int CODE_KNOX_SERVICE_KEY_UNAVAILABLE = 21;
    public static final int CODE_RAMPART_DEVICE_ADMIN_BLOCK = 22;
    public static final int CONTENT_PROTECTION_DISABLED = 1;
    public static final int CONTENT_PROTECTION_ENABLED = 2;
    public static final int CONTENT_PROTECTION_NOT_CONTROLLED_BY_POLICY = 0;
    public static final long DEFAULT_STRONG_AUTH_TIMEOUT_MS = 259200000;
    public static final String DELEGATION_APP_RESTRICTIONS = "delegation-app-restrictions";
    public static final String DELEGATION_BLOCK_UNINSTALL = "delegation-block-uninstall";
    public static final String DELEGATION_CERT_INSTALL = "delegation-cert-install";
    public static final String DELEGATION_CERT_SELECTION = "delegation-cert-selection";
    public static final String DELEGATION_ENABLE_SYSTEM_APP = "delegation-enable-system-app";
    public static final String DELEGATION_INSTALL_EXISTING_PACKAGE = "delegation-install-existing-package";
    public static final String DELEGATION_KEEP_UNINSTALLED_PACKAGES = "delegation-keep-uninstalled-packages";
    public static final String DELEGATION_NETWORK_LOGGING = "delegation-network-logging";
    public static final String DELEGATION_PACKAGE_ACCESS = "delegation-package-access";
    public static final String DELEGATION_PERMISSION_GRANT = "delegation-permission-grant";
    public static final String DELEGATION_SECURITY_LOGGING = "delegation-security-logging";
    public static final boolean DEPRECATE_USERMANAGERINTERNAL_DEVICEPOLICY_DEFAULT = true;
    public static final String DEPRECATE_USERMANAGERINTERNAL_DEVICEPOLICY_FLAG = "deprecate_usermanagerinternal_devicepolicy";
    public static final int DEVICE_OWNER_TYPE_DEFAULT = 0;
    public static final int DEVICE_OWNER_TYPE_FINANCED = 1;

    @Deprecated
    public static final int ENCRYPTION_STATUS_ACTIVATING = 2;
    public static final int ENCRYPTION_STATUS_ACTIVE = 3;
    public static final int ENCRYPTION_STATUS_ACTIVE_DEFAULT_KEY = 4;
    public static final int ENCRYPTION_STATUS_ACTIVE_PER_USER = 5;
    public static final int ENCRYPTION_STATUS_INACTIVE = 1;
    public static final int ENCRYPTION_STATUS_UNSUPPORTED = 0;
    public static final int ERROR_PACKAGE_NAME_NOT_FOUND = 1;
    public static final int ERROR_VPN_PACKAGE_NOT_FOUND = 1;

    @SystemApi
    public static final int EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION = 2;

    @SystemApi
    public static final int EXEMPT_FROM_DISMISSIBLE_NOTIFICATIONS = 1;

    @SystemApi
    public static final int EXEMPT_FROM_HIBERNATION = 3;

    @SystemApi
    public static final int EXEMPT_FROM_POWER_RESTRICTIONS = 4;

    @SystemApi
    public static final int EXEMPT_FROM_SUSPENSION = 0;
    public static final String EXTRA_ADD_EXPLANATION = "android.app.extra.ADD_EXPLANATION";
    public static final String EXTRA_BUGREPORT_NOTIFICATION_TYPE = "android.app.extra.bugreport_notification_type";
    public static final String EXTRA_DELEGATION_SCOPES = "android.app.extra.DELEGATION_SCOPES";
    public static final String EXTRA_DEVICE_ADMIN = "android.app.extra.DEVICE_ADMIN";
    public static final String EXTRA_DEVICE_PASSWORD_REQUIREMENT_ONLY = "android.app.extra.DEVICE_PASSWORD_REQUIREMENT_ONLY";
    public static final String EXTRA_DO_CHANGED_STATUS = "com.samsung.android.knox.intent.extra.EXTRA_DO_CHANGED_STATUS";
    public static final String EXTRA_DO_PO_PACKAGE_NAME = "com.samsung.android.knox.intent.extra.EXTRA_DO_PO_PACKAGE_NAME";

    @SystemApi
    public static final String EXTRA_FORCE_UPDATE_ROLE_HOLDER = "android.app.extra.FORCE_UPDATE_ROLE_HOLDER";
    public static final String EXTRA_IS_BT_CHANGED = "isBTChanged";
    public static final String EXTRA_IS_WIFI_CHANGED = "isWifiChanged";

    @SystemApi
    public static final String EXTRA_LOST_MODE_LOCATION = "android.app.extra.LOST_MODE_LOCATION";
    public static final String EXTRA_NOTIFY_FROM_LOCKSCREEN = "isNotiFromLockScreen";
    public static final String EXTRA_PASSWORD_COMPLEXITY = "android.app.extra.PASSWORD_COMPLEXITY";

    @SystemApi
    public static final String EXTRA_PROFILE_OWNER_NAME = "android.app.extra.PROFILE_OWNER_NAME";
    public static final String EXTRA_PROVISIONING_ACCOUNT_TO_MIGRATE = "android.app.extra.PROVISIONING_ACCOUNT_TO_MIGRATE";
    public static final String EXTRA_PROVISIONING_ACTION = "android.app.extra.PROVISIONING_ACTION";
    public static final String EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE = "android.app.extra.PROVISIONING_ADMIN_EXTRAS_BUNDLE";
    public static final String EXTRA_PROVISIONING_ALLOWED_PROVISIONING_MODES = "android.app.extra.PROVISIONING_ALLOWED_PROVISIONING_MODES";
    public static final String EXTRA_PROVISIONING_ALLOW_OFFLINE = "android.app.extra.PROVISIONING_ALLOW_OFFLINE";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME = "android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE = "android.app.extra.PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_COOKIE_HEADER = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_COOKIE_HEADER";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION";

    @SystemApi
    @Deprecated
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_ICON_URI = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_ICON_URI";

    @SystemApi
    @Deprecated
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_LABEL = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_LABEL";

    @Deprecated
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM = "android.app.extra.PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM";
    public static final String EXTRA_PROVISIONING_DISCLAIMERS = "android.app.extra.PROVISIONING_DISCLAIMERS";
    public static final String EXTRA_PROVISIONING_DISCLAIMER_CONTENT = "android.app.extra.PROVISIONING_DISCLAIMER_CONTENT";
    public static final String EXTRA_PROVISIONING_DISCLAIMER_HEADER = "android.app.extra.PROVISIONING_DISCLAIMER_HEADER";

    @Deprecated
    public static final String EXTRA_PROVISIONING_EMAIL_ADDRESS = "android.app.extra.PROVISIONING_EMAIL_ADDRESS";
    public static final String EXTRA_PROVISIONING_IMEI = "android.app.extra.PROVISIONING_IMEI";
    public static final String EXTRA_PROVISIONING_KEEP_ACCOUNT_ON_MIGRATION = "android.app.extra.PROVISIONING_KEEP_ACCOUNT_ON_MIGRATION";

    @Deprecated
    public static final String EXTRA_PROVISIONING_KEEP_SCREEN_ON = "android.app.extra.PROVISIONING_KEEP_SCREEN_ON";
    public static final String EXTRA_PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED = "android.app.extra.PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED";
    public static final String EXTRA_PROVISIONING_LOCALE = "android.app.extra.PROVISIONING_LOCALE";
    public static final String EXTRA_PROVISIONING_LOCAL_TIME = "android.app.extra.PROVISIONING_LOCAL_TIME";

    @Deprecated
    public static final String EXTRA_PROVISIONING_LOGO_URI = "android.app.extra.PROVISIONING_LOGO_URI";

    @Deprecated
    public static final String EXTRA_PROVISIONING_MAIN_COLOR = "android.app.extra.PROVISIONING_MAIN_COLOR";
    public static final String EXTRA_PROVISIONING_MODE = "android.app.extra.PROVISIONING_MODE";

    @SystemApi
    public static final String EXTRA_PROVISIONING_ORGANIZATION_NAME = "android.app.extra.PROVISIONING_ORGANIZATION_NAME";

    @SystemApi
    public static final String EXTRA_PROVISIONING_RETURN_BEFORE_POLICY_COMPLIANCE = "android.app.extra.PROVISIONING_RETURN_BEFORE_POLICY_COMPLIANCE";

    @SystemApi
    public static final String EXTRA_PROVISIONING_ROLE_HOLDER_CUSTOM_USER_CONSENT_INTENT = "android.app.extra.PROVISIONING_ROLE_HOLDER_CUSTOM_USER_CONSENT_INTENT";

    @SystemApi
    public static final String EXTRA_PROVISIONING_ROLE_HOLDER_EXTRAS_BUNDLE = "android.app.extra.PROVISIONING_ROLE_HOLDER_EXTRAS_BUNDLE";

    @SystemApi
    public static final String EXTRA_PROVISIONING_ROLE_HOLDER_PACKAGE_DOWNLOAD_COOKIE_HEADER = "android.app.extra.PROVISIONING_ROLE_HOLDER_PACKAGE_DOWNLOAD_COOKIE_HEADER";

    @SystemApi
    public static final String EXTRA_PROVISIONING_ROLE_HOLDER_PACKAGE_DOWNLOAD_LOCATION = "android.app.extra.PROVISIONING_ROLE_HOLDER_PACKAGE_DOWNLOAD_LOCATION";

    @SystemApi
    public static final String EXTRA_PROVISIONING_ROLE_HOLDER_SIGNATURE_CHECKSUM = "android.app.extra.PROVISIONING_ROLE_HOLDER_SIGNATURE_CHECKSUM";
    public static final String EXTRA_PROVISIONING_SENSORS_PERMISSION_GRANT_OPT_OUT = "android.app.extra.PROVISIONING_SENSORS_PERMISSION_GRANT_OPT_OUT";
    public static final String EXTRA_PROVISIONING_SERIAL_NUMBER = "android.app.extra.PROVISIONING_SERIAL_NUMBER";
    public static final String EXTRA_PROVISIONING_SHOULD_LAUNCH_RESULT_INTENT = "android.app.extra.PROVISIONING_SHOULD_LAUNCH_RESULT_INTENT";
    public static final String EXTRA_PROVISIONING_SKIP_EDUCATION_SCREENS = "android.app.extra.PROVISIONING_SKIP_EDUCATION_SCREENS";
    public static final String EXTRA_PROVISIONING_SKIP_ENCRYPTION = "android.app.extra.PROVISIONING_SKIP_ENCRYPTION";

    @SystemApi
    public static final String EXTRA_PROVISIONING_SKIP_OWNERSHIP_DISCLAIMER = "android.app.extra.PROVISIONING_SKIP_OWNERSHIP_DISCLAIMER";

    @Deprecated
    public static final String EXTRA_PROVISIONING_SKIP_USER_CONSENT = "android.app.extra.PROVISIONING_SKIP_USER_CONSENT";

    @SystemApi
    public static final String EXTRA_PROVISIONING_SUPPORTED_MODES = "android.app.extra.PROVISIONING_SUPPORTED_MODES";

    @SystemApi
    public static final String EXTRA_PROVISIONING_SUPPORT_URL = "android.app.extra.PROVISIONING_SUPPORT_URL";
    public static final String EXTRA_PROVISIONING_TIME_ZONE = "android.app.extra.PROVISIONING_TIME_ZONE";

    @SystemApi
    public static final String EXTRA_PROVISIONING_TRIGGER = "android.app.extra.PROVISIONING_TRIGGER";
    public static final String EXTRA_PROVISIONING_USE_MOBILE_DATA = "android.app.extra.PROVISIONING_USE_MOBILE_DATA";
    public static final String EXTRA_PROVISIONING_WIFI_ANONYMOUS_IDENTITY = "android.app.extra.PROVISIONING_WIFI_ANONYMOUS_IDENTITY";
    public static final String EXTRA_PROVISIONING_WIFI_CA_CERTIFICATE = "android.app.extra.PROVISIONING_WIFI_CA_CERTIFICATE";
    public static final String EXTRA_PROVISIONING_WIFI_DOMAIN = "android.app.extra.PROVISIONING_WIFI_DOMAIN";
    public static final String EXTRA_PROVISIONING_WIFI_EAP_METHOD = "android.app.extra.PROVISIONING_WIFI_EAP_METHOD";
    public static final String EXTRA_PROVISIONING_WIFI_HIDDEN = "android.app.extra.PROVISIONING_WIFI_HIDDEN";
    public static final String EXTRA_PROVISIONING_WIFI_IDENTITY = "android.app.extra.PROVISIONING_WIFI_IDENTITY";
    public static final String EXTRA_PROVISIONING_WIFI_PAC_URL = "android.app.extra.PROVISIONING_WIFI_PAC_URL";
    public static final String EXTRA_PROVISIONING_WIFI_PASSWORD = "android.app.extra.PROVISIONING_WIFI_PASSWORD";
    public static final String EXTRA_PROVISIONING_WIFI_PHASE2_AUTH = "android.app.extra.PROVISIONING_WIFI_PHASE2_AUTH";
    public static final String EXTRA_PROVISIONING_WIFI_PROXY_BYPASS = "android.app.extra.PROVISIONING_WIFI_PROXY_BYPASS";
    public static final String EXTRA_PROVISIONING_WIFI_PROXY_HOST = "android.app.extra.PROVISIONING_WIFI_PROXY_HOST";
    public static final String EXTRA_PROVISIONING_WIFI_PROXY_PORT = "android.app.extra.PROVISIONING_WIFI_PROXY_PORT";
    public static final String EXTRA_PROVISIONING_WIFI_SECURITY_TYPE = "android.app.extra.PROVISIONING_WIFI_SECURITY_TYPE";
    public static final String EXTRA_PROVISIONING_WIFI_SSID = "android.app.extra.PROVISIONING_WIFI_SSID";
    public static final String EXTRA_PROVISIONING_WIFI_USER_CERTIFICATE = "android.app.extra.PROVISIONING_WIFI_USER_CERTIFICATE";
    public static final String EXTRA_REMOTE_BUGREPORT_HASH = "android.intent.extra.REMOTE_BUGREPORT_HASH";
    public static final String EXTRA_REMOTE_BUGREPORT_NONCE = "android.intent.extra.REMOTE_BUGREPORT_NONCE";
    public static final String EXTRA_RESOURCE_IDS = "android.app.extra.RESOURCE_IDS";
    public static final String EXTRA_RESOURCE_TYPE = "android.app.extra.RESOURCE_TYPE";
    public static final int EXTRA_RESOURCE_TYPE_DRAWABLE = 1;
    public static final int EXTRA_RESOURCE_TYPE_STRING = 2;

    @SystemApi
    public static final String EXTRA_RESTRICTION = "android.app.extra.RESTRICTION";
    public static final String EXTRA_RESULT_LAUNCH_INTENT = "android.app.extra.RESULT_LAUNCH_INTENT";

    @SystemApi
    public static final String EXTRA_ROLE_HOLDER_PROVISIONING_INITIATOR_PACKAGE = "android.app.extra.ROLE_HOLDER_PROVISIONING_INITIATOR_PACKAGE";

    @SystemApi
    public static final String EXTRA_ROLE_HOLDER_STATE = "android.app.extra.ROLE_HOLDER_STATE";
    public static final String EXTRA_ROLE_HOLDER_UPDATE_FAILURE_STRATEGY = "android.app.extra.ROLE_HOLDER_UPDATE_FAILURE_STRATEGY";

    @SystemApi
    public static final String EXTRA_ROLE_HOLDER_UPDATE_RESULT_CODE = "android.app.extra.ROLE_HOLDER_UPDATE_RESULT_CODE";
    public static final int FLAG_EVICT_CREDENTIAL_ENCRYPTION_KEY = 1;
    public static final int FLAG_MANAGED_CAN_ACCESS_PARENT = 2;
    public static final int FLAG_PARENT_CAN_ACCESS_MANAGED = 1;

    @SystemApi
    public static final int FLAG_SUPPORTED_MODES_DEVICE_OWNER = 4;

    @SystemApi
    public static final int FLAG_SUPPORTED_MODES_ORGANIZATION_OWNED = 1;

    @SystemApi
    public static final int FLAG_SUPPORTED_MODES_PERSONALLY_OWNED = 2;
    public static final int ID_TYPE_BASE_INFO = 1;
    public static final int ID_TYPE_IMEI = 4;
    public static final int ID_TYPE_INDIVIDUAL_ATTESTATION = 16;
    public static final int ID_TYPE_MEID = 8;
    public static final int ID_TYPE_SERIAL = 2;
    public static final int INSTALLKEY_REQUEST_CREDENTIALS_ACCESS = 1;
    public static final int INSTALLKEY_SET_USER_SELECTABLE = 2;
    public static final long IS_DEVICE_OWNER_USER_AWARE = 307233716;
    public static final int KEYGUARD_DISABLE_BIOMETRICS = 416;
    public static final int KEYGUARD_DISABLE_FACE = 128;
    public static final int KEYGUARD_DISABLE_FEATURES_ALL = Integer.MAX_VALUE;
    public static final int KEYGUARD_DISABLE_FEATURES_NONE = 0;
    public static final int KEYGUARD_DISABLE_FINGERPRINT = 32;
    public static final int KEYGUARD_DISABLE_IRIS = 256;

    @Deprecated
    public static final int KEYGUARD_DISABLE_REMOTE_INPUT = 64;
    public static final int KEYGUARD_DISABLE_SECURE_CAMERA = 2;
    public static final int KEYGUARD_DISABLE_SECURE_NOTIFICATIONS = 4;
    public static final int KEYGUARD_DISABLE_SHORTCUTS_ALL = 512;
    public static final int KEYGUARD_DISABLE_TRUST_AGENTS = 16;
    public static final int KEYGUARD_DISABLE_UNREDACTED_NOTIFICATIONS = 8;
    public static final int KEYGUARD_DISABLE_WIDGETS_ALL = 1;
    public static final int KEY_GEN_STRONGBOX_UNAVAILABLE = 1;
    public static final int LEAVE_ALL_SYSTEM_APPS_ENABLED = 16;
    public static final int LOCK_TASK_FEATURE_BLOCK_ACTIVITY_START_IN_TASK = 64;
    public static final int LOCK_TASK_FEATURE_GLOBAL_ACTIONS = 16;
    public static final int LOCK_TASK_FEATURE_HOME = 4;
    public static final int LOCK_TASK_FEATURE_KEYGUARD = 32;
    public static final int LOCK_TASK_FEATURE_NONE = 0;
    public static final int LOCK_TASK_FEATURE_NOTIFICATIONS = 2;
    public static final int LOCK_TASK_FEATURE_OVERVIEW = 8;
    public static final int LOCK_TASK_FEATURE_SYSTEM_INFO = 1;
    public static final int MAKE_USER_DEMO = 4;
    public static final int MAKE_USER_EPHEMERAL = 2;
    public static final int MAX_PASSWORD_LENGTH = 256;
    public static final String MIME_TYPE_PROVISIONING_NFC = "application/com.android.managedprovisioning";
    public static final int MTE_DISABLED = 2;
    public static final int MTE_ENABLED = 1;
    public static final int MTE_NOT_CONTROLLED_BY_POLICY = 0;
    public static final int NEARBY_STREAMING_DISABLED = 1;
    public static final int NEARBY_STREAMING_ENABLED = 2;
    public static final int NEARBY_STREAMING_NOT_CONTROLLED_BY_POLICY = 0;
    public static final int NEARBY_STREAMING_SAME_MANAGED_ACCOUNT_ONLY = 3;
    public static final int NON_ORG_OWNED_PROFILE_KEYGUARD_FEATURES_AFFECT_OWNER = 432;
    public static final int NOTIFICATION_BUGREPORT_ACCEPTED_NOT_FINISHED = 2;
    public static final int NOTIFICATION_BUGREPORT_FINISHED_NOT_ACCEPTED = 3;
    public static final int NOTIFICATION_BUGREPORT_STARTED = 1;
    public static final int OPERATION_CLEAR_APPLICATION_USER_DATA = 23;
    public static final int OPERATION_CREATE_AND_MANAGE_USER = 5;
    public static final int OPERATION_INSTALL_CA_CERT = 24;
    public static final int OPERATION_INSTALL_KEY_PAIR = 25;
    public static final int OPERATION_INSTALL_SYSTEM_UPDATE = 26;
    public static final int OPERATION_LOCK_NOW = 1;
    public static final int OPERATION_LOGOUT_USER = 9;
    public static final int OPERATION_REBOOT = 7;
    public static final int OPERATION_REMOVE_ACTIVE_ADMIN = 27;
    public static final int OPERATION_REMOVE_KEY_PAIR = 28;
    public static final int OPERATION_REMOVE_USER = 6;
    public static final int OPERATION_REQUEST_BUGREPORT = 29;
    public static final int OPERATION_SAFETY_REASON_DRIVING_DISTRACTION = 1;
    public static final int OPERATION_SAFETY_REASON_NONE = -1;
    public static final int OPERATION_SET_ALWAYS_ON_VPN_PACKAGE = 30;
    public static final int OPERATION_SET_APPLICATION_HIDDEN = 15;
    public static final int OPERATION_SET_APPLICATION_RESTRICTIONS = 16;
    public static final int OPERATION_SET_APP_FUNCTIONS_POLICY = 42;
    public static final int OPERATION_SET_CAMERA_DISABLED = 31;
    public static final int OPERATION_SET_CONTENT_PROTECTION_POLICY = 41;
    public static final int OPERATION_SET_FACTORY_RESET_PROTECTION_POLICY = 32;
    public static final int OPERATION_SET_GLOBAL_PRIVATE_DNS = 33;
    public static final int OPERATION_SET_KEEP_UNINSTALLED_PACKAGES = 17;
    public static final int OPERATION_SET_KEYGUARD_DISABLED = 12;
    public static final int OPERATION_SET_LOCK_TASK_FEATURES = 18;
    public static final int OPERATION_SET_LOCK_TASK_PACKAGES = 19;
    public static final int OPERATION_SET_LOGOUT_ENABLED = 34;
    public static final int OPERATION_SET_MASTER_VOLUME_MUTED = 35;
    public static final int OPERATION_SET_OVERRIDE_APNS_ENABLED = 36;
    public static final int OPERATION_SET_PACKAGES_SUSPENDED = 20;
    public static final int OPERATION_SET_PERMISSION_GRANT_STATE = 37;
    public static final int OPERATION_SET_PERMISSION_POLICY = 38;
    public static final int OPERATION_SET_RESTRICTIONS_PROVIDER = 39;
    public static final int OPERATION_SET_STATUS_BAR_DISABLED = 13;
    public static final int OPERATION_SET_SYSTEM_SETTING = 11;
    public static final int OPERATION_SET_SYSTEM_UPDATE_POLICY = 14;
    public static final int OPERATION_SET_TRUST_AGENT_CONFIGURATION = 21;
    public static final int OPERATION_SET_USER_CONTROL_DISABLED_PACKAGES = 22;
    public static final int OPERATION_SET_USER_RESTRICTION = 10;
    public static final int OPERATION_START_USER_IN_BACKGROUND = 3;
    public static final int OPERATION_STOP_USER = 4;
    public static final int OPERATION_SWITCH_USER = 2;
    public static final int OPERATION_UNINSTALL_CA_CERT = 40;
    public static final int OPERATION_WIPE_DATA = 8;
    public static final int ORG_OWNED_PROFILE_KEYGUARD_FEATURES_PARENT_ONLY = 519;
    public static final int PASSWORD_COMPLEXITY_HIGH = 327680;
    public static final int PASSWORD_COMPLEXITY_LOW = 65536;
    public static final int PASSWORD_COMPLEXITY_MEDIUM = 196608;
    public static final int PASSWORD_COMPLEXITY_NONE = 0;
    public static final int PASSWORD_QUALITY_ALPHABETIC = 262144;
    public static final int PASSWORD_QUALITY_ALPHANUMERIC = 327680;
    public static final int PASSWORD_QUALITY_BIOMETRIC_WEAK = 32768;
    public static final int PASSWORD_QUALITY_COMPLEX = 393216;
    public static final int PASSWORD_QUALITY_MANAGED = 524288;
    public static final int PASSWORD_QUALITY_NUMERIC = 131072;
    public static final int PASSWORD_QUALITY_NUMERIC_COMPLEX = 196608;
    public static final int PASSWORD_QUALITY_SMARTCARDNUMERIC = 458752;
    public static final int PASSWORD_QUALITY_SOMETHING = 65536;
    public static final int PASSWORD_QUALITY_UNSPECIFIED = 0;
    public static final int PERMISSION_GRANT_STATE_DEFAULT = 0;
    public static final int PERMISSION_GRANT_STATE_DENIED = 2;
    public static final int PERMISSION_GRANT_STATE_GRANTED = 1;
    public static final int PERMISSION_POLICY_AUTO_DENY = 2;
    public static final int PERMISSION_POLICY_AUTO_GRANT = 1;
    public static final int PERMISSION_POLICY_PROMPT = 0;
    public static final int PERSONAL_APPS_NOT_SUSPENDED = 0;
    public static final int PERSONAL_APPS_SUSPENDED_EXPLICITLY = 1;
    public static final int PERSONAL_APPS_SUSPENDED_PROFILE_TIMEOUT = 2;
    public static final String POLICY_DISABLE_CAMERA = "policy_disable_camera";
    public static final String POLICY_DISABLE_SCREEN_CAPTURE = "policy_disable_screen_capture";
    public static final String POLICY_SUSPEND_PACKAGES = "policy_suspend_packages";
    public static final boolean PREFERENTIAL_NETWORK_SERVICE_ENABLED_DEFAULT = false;
    private static final String PREFIX_OPERATION = "OPERATION_";
    private static final String PREFIX_OPERATION_SAFETY_REASON = "OPERATION_SAFETY_REASON_";
    public static final int PRIVATE_DNS_MODE_OFF = 1;
    public static final int PRIVATE_DNS_MODE_OPPORTUNISTIC = 2;
    public static final int PRIVATE_DNS_MODE_PROVIDER_HOSTNAME = 3;
    public static final int PRIVATE_DNS_MODE_UNKNOWN = 0;
    public static final int PRIVATE_DNS_SET_ERROR_FAILURE_SETTING = 2;
    public static final int PRIVATE_DNS_SET_ERROR_HOST_NOT_SERVING = 1;
    public static final int PRIVATE_DNS_SET_NO_ERROR = 0;
    public static final int PROFILE_KEYGUARD_FEATURES_AFFECT_OWNER = 951;
    public static final String PROPERTY_NON_REQUIRED_APPS_TASK = "persist.sys.knox.non_required_apps_task";
    public static final int PROVISIONING_MODE_FULLY_MANAGED_DEVICE = 1;
    public static final int PROVISIONING_MODE_MANAGED_PROFILE = 2;
    public static final int PROVISIONING_MODE_MANAGED_PROFILE_ON_PERSONAL_DEVICE = 3;

    @SystemApi
    public static final int PROVISIONING_TRIGGER_CLOUD_ENROLLMENT = 1;

    @SystemApi
    public static final int PROVISIONING_TRIGGER_MANAGED_ACCOUNT = 4;

    @SystemApi
    public static final int PROVISIONING_TRIGGER_NFC = 5;

    @SystemApi
    @Deprecated
    public static final int PROVISIONING_TRIGGER_PERSISTENT_DEVICE_OWNER = 3;

    @SystemApi
    public static final int PROVISIONING_TRIGGER_QR_CODE = 2;

    @SystemApi
    public static final int PROVISIONING_TRIGGER_UNSPECIFIED = 0;

    @SystemApi
    public static final String REQUIRED_APP_MANAGED_DEVICE = "android.app.REQUIRED_APP_MANAGED_DEVICE";

    @SystemApi
    public static final String REQUIRED_APP_MANAGED_PROFILE = "android.app.REQUIRED_APP_MANAGED_PROFILE";

    @SystemApi
    public static final String REQUIRED_APP_MANAGED_USER = "android.app.REQUIRED_APP_MANAGED_USER";
    public static final int RESET_PASSWORD_DO_NOT_ASK_CREDENTIALS_ON_BOOT = 2;
    public static final int RESET_PASSWORD_REQUIRE_ENTRY = 1;

    @SystemApi
    public static final int RESULT_DEVICE_OWNER_SET = 123;

    @SystemApi
    public static final int RESULT_UPDATE_DEVICE_POLICY_MANAGEMENT_ROLE_HOLDER_PROVISIONING_DISABLED = 3;

    @SystemApi
    public static final int RESULT_UPDATE_DEVICE_POLICY_MANAGEMENT_ROLE_HOLDER_RECOVERABLE_ERROR = 1;

    @SystemApi
    public static final int RESULT_UPDATE_DEVICE_POLICY_MANAGEMENT_ROLE_HOLDER_UNRECOVERABLE_ERROR = 2;

    @SystemApi
    public static final int RESULT_UPDATE_ROLE_HOLDER = 2;

    @SystemApi
    public static final int RESULT_WORK_PROFILE_CREATED = 122;
    public static final int ROLE_HOLDER_UPDATE_FAILURE_STRATEGY_FAIL_PROVISIONING = 1;
    public static final int ROLE_HOLDER_UPDATE_FAILURE_STRATEGY_FALLBACK_TO_PLATFORM_PROVISIONING = 2;
    public static final int SEM_PASSWORD_QUALITY_FINGERPRINT = 397312;
    public static final int SEM_PASSWORD_QUALITY_SMART_UNLOCK = 589824;
    public static final int SKIP_SETUP_WIZARD = 1;

    @SystemApi
    public static final int STATE_USER_PROFILE_COMPLETE = 4;

    @SystemApi
    public static final int STATE_USER_PROFILE_FINALIZED = 5;

    @SystemApi
    public static final int STATE_USER_SETUP_COMPLETE = 2;

    @SystemApi
    public static final int STATE_USER_SETUP_FINALIZED = 3;

    @SystemApi
    public static final int STATE_USER_SETUP_INCOMPLETE = 1;

    @SystemApi
    public static final int STATE_USER_UNMANAGED = 0;

    @SystemApi
    public static final int STATUS_ACCOUNTS_NOT_EMPTY = 6;

    @SystemApi
    public static final int STATUS_CANNOT_ADD_MANAGED_PROFILE = 11;

    @SystemApi
    public static final int STATUS_DEVICE_ADMIN_NOT_SUPPORTED = 13;

    @SystemApi
    public static final int STATUS_HAS_DEVICE_OWNER = 1;

    @SystemApi
    public static final int STATUS_HAS_PAIRED = 8;

    @SystemApi
    public static final int STATUS_HEADLESS_ONLY_SYSTEM_USER = 17;

    @SystemApi
    public static final int STATUS_HEADLESS_SYSTEM_USER_MODE_NOT_SUPPORTED = 16;

    @SystemApi
    public static final int STATUS_MANAGED_USERS_NOT_SUPPORTED = 9;

    @SystemApi
    public static final int STATUS_NONSYSTEM_USER_EXISTS = 5;

    @SystemApi
    public static final int STATUS_NOT_SYSTEM_USER = 7;

    @SystemApi
    public static final int STATUS_OK = 0;

    @SystemApi
    public static final int STATUS_PROVISIONING_NOT_ALLOWED_FOR_NON_DEVELOPER_USERS = 15;

    @Deprecated
    public static final int STATUS_SPLIT_SYSTEM_USER_DEVICE_SYSTEM_USER = 14;

    @SystemApi
    public static final int STATUS_SYSTEM_USER = 10;

    @SystemApi
    public static final int STATUS_UNKNOWN_ERROR = -1;

    @SystemApi
    public static final int STATUS_USER_HAS_PROFILE_OWNER = 2;

    @SystemApi
    public static final int STATUS_USER_NOT_RUNNING = 3;

    @SystemApi
    public static final int STATUS_USER_SETUP_COMPLETED = 4;
    private static String TAG = "DevicePolicyManager";
    public static final int WIFI_SECURITY_ENTERPRISE_192 = 3;
    public static final int WIFI_SECURITY_ENTERPRISE_EAP = 2;
    public static final int WIFI_SECURITY_OPEN = 0;
    public static final int WIFI_SECURITY_PERSONAL = 1;
    public static final int WIPE_EUICC = 4;
    public static final int WIPE_EXTERNAL_STORAGE = 1;
    public static final int WIPE_RESET_PROTECTION_DATA = 2;
    public static final int WIPE_SILENTLY = 8;
    private static final IpcDataCache.Config sDpmCaches = new IpcDataCache.Config(8, "system_server", "DevicePolicyManagerCaches").cacheNulls(true);
    private final int ALLOW_BLUETOOTH_MODE_VALUE_ALLOW;
    private final int ALLOW_BLUETOOTH_MODE_VALUE_DISABLE;
    private final int ALLOW_BLUETOOTH_MODE_VALUE_HANDSFREE_ONLY;
    private final Context mContext;
    private final IpcDataCache<Void, CharSequence> mGetDeviceOwnerOrganizationNameCache;
    private IpcDataCache<Pair<ComponentName, Integer>, Integer> mGetKeyGuardDisabledFeaturesCache;
    private final IpcDataCache<Integer, CharSequence> mGetOrganizationNameForUserCache;
    private final IpcDataCache<UserHandle, ComponentName> mGetProfileOwnerOrDeviceOwnerSupervisionComponentCache;
    private IpcDataCache<Void, Boolean> mHasDeviceOwnerCache;
    private IpcDataCache<ComponentName, Boolean> mIsNetworkLoggingEnabledCache;
    private final IpcDataCache<Void, Boolean> mIsOrganizationOwnedDeviceWithManagedProfileCache;
    private final boolean mParentInstance;
    private final DevicePolicyResourcesManager mResourcesManager;
    private final IDevicePolicyManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AppFunctionsPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplicationExemptionConstants {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttestationIdType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoTimePolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoTimeZonePolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentProtectionPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CreateAndManageUserFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceOwnerType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DevicePolicyOperation {
    }

    public static abstract class InstallSystemUpdateCallback {
        public static final int UPDATE_ERROR_BATTERY_LOW = 5;
        public static final int UPDATE_ERROR_FILE_NOT_FOUND = 4;
        public static final int UPDATE_ERROR_INCORRECT_OS_VERSION = 2;
        public static final int UPDATE_ERROR_UNKNOWN = 1;
        public static final int UPDATE_ERROR_UPDATE_FILE_INVALID = 3;

        public void onInstallUpdateError(int i, String str) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InstallUpdateCallbackErrorConstants {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LockNowFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LockTaskFeature {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MtePolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NearbyStreamingPolicy {
    }

    public interface OnClearApplicationUserDataListener {
        void onApplicationUserDataCleared(String str, boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OperationSafetyReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PasswordComplexity {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionGrantState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PersonalAppsSuspensionReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrivateDnsMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrivateDnsModeErrorCodes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProvisioningConfiguration {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProvisioningPrecondition {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProvisioningTrigger {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RoleHolderUpdateFailureStrategy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemSettingsWhitelist {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserProvisioningState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WifiSecurity {
    }

    private String getErrorMessage(ServiceSpecificException serviceSpecificException) {
        return null;
    }

    public static boolean isValidOperationSafetyReason(int i) {
        return i == 1;
    }

    public DevicePolicyManager(Context context, IDevicePolicyManager iDevicePolicyManager) {
        this(context, iDevicePolicyManager, false);
    }

    protected DevicePolicyManager(Context context, IDevicePolicyManager iDevicePolicyManager, boolean z) {
        this.ALLOW_BLUETOOTH_MODE_VALUE_DISABLE = 0;
        this.ALLOW_BLUETOOTH_MODE_VALUE_HANDSFREE_ONLY = 1;
        this.ALLOW_BLUETOOTH_MODE_VALUE_ALLOW = 2;
        IpcDataCache.Config config = sDpmCaches;
        this.mGetKeyGuardDisabledFeaturesCache = new IpcDataCache<>(config.child("getKeyguardDisabledFeatures"), new IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda2
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                return this.f$0.lambda$new$2((Pair) obj);
            }
        });
        this.mHasDeviceOwnerCache = new IpcDataCache<>(config.child("hasDeviceOwner"), new IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda3
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                return this.f$0.lambda$new$3((Void) obj);
            }
        });
        this.mGetProfileOwnerOrDeviceOwnerSupervisionComponentCache = new IpcDataCache<>(config.child("getProfileOwnerOrDeviceOwnerSupervisionComponent"), new IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda4
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                return this.f$0.lambda$new$4((UserHandle) obj);
            }
        });
        this.mIsOrganizationOwnedDeviceWithManagedProfileCache = new IpcDataCache<>(config.child("isOrganizationOwnedDeviceWithManagedProfile"), new IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda5
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                return this.f$0.lambda$new$5(obj);
            }
        });
        this.mGetDeviceOwnerOrganizationNameCache = new IpcDataCache<>(config.child("getDeviceOwnerOrganizationName"), new IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda6
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                return this.f$0.lambda$new$8(obj);
            }
        });
        this.mGetOrganizationNameForUserCache = new IpcDataCache<>(config.child("getOrganizationNameForUser"), new IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda7
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                return this.f$0.lambda$new$9((Integer) obj);
            }
        });
        this.mIsNetworkLoggingEnabledCache = new IpcDataCache<>(config.child("isNetworkLoggingEnabled"), new IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda8
            @Override // android.os.IpcDataCache.RemoteCall
            public final Object apply(Object obj) {
                return this.f$0.lambda$new$10((ComponentName) obj);
            }
        });
        this.mContext = context;
        this.mService = iDevicePolicyManager;
        this.mParentInstance = z;
        this.mResourcesManager = new DevicePolicyResourcesManager(context, iDevicePolicyManager);
    }

    private IDevicePolicyManager getService() {
        return this.mService;
    }

    private boolean isParentInstance() {
        return this.mParentInstance;
    }

    private Context getContext() {
        return this.mContext;
    }

    protected int myUserId() {
        return this.mContext.getUserId();
    }

    public static String operationToString(int i) {
        return DebugUtils.constantToString(DevicePolicyManager.class, PREFIX_OPERATION, i);
    }

    public void setMtePolicy(int i) {
        throwIfParentInstance("setMtePolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setMtePolicy(i, this.mContext.getPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setMtePolicy(String str, int i) {
        throwIfParentInstance("setMtePolicyForUser");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setMtePolicyBySystem(str, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getMtePolicy() {
        throwIfParentInstance("setMtePolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getMtePolicy(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isMtePolicyEnforced() {
        return Zygote.nativeSupportsMemoryTagging();
    }

    public void setContentProtectionPolicy(ComponentName componentName, int i) {
        throwIfParentInstance("setContentProtectionPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setContentProtectionPolicy(componentName, this.mContext.getPackageName(), i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getContentProtectionPolicy(ComponentName componentName) {
        throwIfParentInstance("getContentProtectionPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 1;
        }
        try {
            return iDevicePolicyManager.getContentProtectionPolicy(componentName, this.mContext.getPackageName(), myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAppFunctionsPolicy(int i) {
        throwIfParentInstance("setAppFunctionsPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setAppFunctionsPolicy(this.mContext.getPackageName(), i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getAppFunctionsPolicy() {
        throwIfParentInstance("getAppFunctionsPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getAppFunctionsPolicy(this.mContext.getPackageName(), myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void invalidateBinderCaches() {
        sDpmCaches.invalidateCache();
    }

    public static void disableLocalCaches() {
        sDpmCaches.disableAllForCurrentProcess();
    }

    public static String operationSafetyReasonToString(int i) {
        return DebugUtils.constantToString(DevicePolicyManager.class, PREFIX_OPERATION_SAFETY_REASON, i);
    }

    public boolean isSafeOperation(int i) {
        throwIfParentInstance("isSafeOperation");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isSafeOperation(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void acknowledgeNewUserDisclaimer() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.acknowledgeNewUserDisclaimer(this.mContext.getUserId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isNewUserDisclaimerAcknowledged() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isNewUserDisclaimerAcknowledged(this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAdminActive(ComponentName componentName) {
        throwIfParentInstance("isAdminActive");
        return isAdminActiveAsUser(componentName, myUserId());
    }

    public boolean isAdminActiveAsUser(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isAdminActive(componentName, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRemovingAdmin(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isRemovingAdmin(componentName, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ComponentName> getActiveAdmins() {
        throwIfParentInstance(SecContentProviderURI.ENTERPRISEDEVICEMANAGERPOLICY_ACTIVEADMINS_METHOD);
        return getActiveAdminsAsUser(myUserId());
    }

    public List<ComponentName> getActiveAdminsAsUser(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getActiveAdmins(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean packageHasActiveAdmins(String str) {
        return packageHasActiveAdmins(str, myUserId());
    }

    public boolean packageHasActiveAdmins(String str, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.packageHasActiveAdmins(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semHasActiveAdminForPackage(String str) {
        return packageHasActiveAdmins(str);
    }

    public void removeActiveAdmin(ComponentName componentName) {
        throwIfParentInstance("removeActiveAdmin");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.removeActiveAdmin(componentName, myUserId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean hasGrantedPolicy(ComponentName componentName, int i) {
        throwIfParentInstance("hasGrantedPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.hasGrantedPolicy(componentName, i, myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setPasswordQuality(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordQuality(componentName, i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public int getPasswordQuality(ComponentName componentName) {
        return getPasswordQuality(componentName, myUserId());
    }

    public int getPasswordQuality(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getPasswordQuality(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setPasswordMinimumLength(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumLength(componentName, i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public int getPasswordMinimumLength(ComponentName componentName) {
        return getPasswordMinimumLength(componentName, myUserId());
    }

    public int getPasswordMinimumLength(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getPasswordMinimumLength(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setPasswordMinimumUpperCase(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumUpperCase(componentName, i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public int getPasswordMinimumUpperCase(ComponentName componentName) {
        return getPasswordMinimumUpperCase(componentName, myUserId());
    }

    public int getPasswordMinimumUpperCase(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getPasswordMinimumUpperCase(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setPasswordMinimumLowerCase(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumLowerCase(componentName, i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public int getPasswordMinimumLowerCase(ComponentName componentName) {
        return getPasswordMinimumLowerCase(componentName, myUserId());
    }

    public int getPasswordMinimumLowerCase(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getPasswordMinimumLowerCase(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setPasswordMinimumLetters(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumLetters(componentName, i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public int getPasswordMinimumLetters(ComponentName componentName) {
        return getPasswordMinimumLetters(componentName, myUserId());
    }

    public int getPasswordMinimumLetters(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getPasswordMinimumLetters(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setPasswordMinimumNumeric(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumNumeric(componentName, i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public int getPasswordMinimumNumeric(ComponentName componentName) {
        return getPasswordMinimumNumeric(componentName, myUserId());
    }

    public int getPasswordMinimumNumeric(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getPasswordMinimumNumeric(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setPasswordMinimumSymbols(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumSymbols(componentName, i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public int getPasswordMinimumSymbols(ComponentName componentName) {
        return getPasswordMinimumSymbols(componentName, myUserId());
    }

    public int getPasswordMinimumSymbols(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getPasswordMinimumSymbols(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setPasswordMinimumNonLetter(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumNonLetter(componentName, i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public int getPasswordMinimumNonLetter(ComponentName componentName) {
        return getPasswordMinimumNonLetter(componentName, myUserId());
    }

    public int getPasswordMinimumNonLetter(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getPasswordMinimumNonLetter(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PasswordMetrics getPasswordMinimumMetrics(int i) {
        return getPasswordMinimumMetrics(i, false);
    }

    public PasswordMetrics getPasswordMinimumMetrics(int i, boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getPasswordMinimumMetrics(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPasswordHistoryLength(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordHistoryLength(componentName, i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setPasswordExpirationTimeout(ComponentName componentName, long j) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordExpirationTimeout(componentName, this.mContext.getPackageName(), j, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long getPasswordExpirationTimeout(ComponentName componentName) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0L;
        }
        try {
            return iDevicePolicyManager.getPasswordExpirationTimeout(componentName, myUserId(), this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getPasswordExpiration(ComponentName componentName) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0L;
        }
        try {
            return iDevicePolicyManager.getPasswordExpiration(componentName, myUserId(), this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPasswordHistoryLength(ComponentName componentName) {
        return getPasswordHistoryLength(componentName, myUserId());
    }

    public int getPasswordHistoryLength(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getPasswordHistoryLength(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPasswordMaximumLength(int i) {
        return !this.mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SECURE_LOCK_SCREEN) ? 0 : 256;
    }

    public boolean isActivePasswordSufficient() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isActivePasswordSufficient(this.mContext.getPackageName(), myUserId(), this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isActivePasswordSufficientForDeviceRequirement() {
        if (!this.mParentInstance) {
            throw new SecurityException("only callable on the parent instance");
        }
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isActivePasswordSufficientForDeviceRequirement();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPasswordComplexity() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getPasswordComplexity(this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRequiredPasswordComplexity(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.setRequiredPasswordComplexity(this.mContext.getPackageName(), i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRequiredPasswordComplexity() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getRequiredPasswordComplexity(this.mContext.getPackageName(), this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAggregatedPasswordComplexityForUser(int i) {
        return getAggregatedPasswordComplexityForUser(i, false);
    }

    public int getAggregatedPasswordComplexityForUser(int i, boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getAggregatedPasswordComplexityForUser(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUsingUnifiedPassword(ComponentName componentName) {
        throwIfParentInstance("isUsingUnifiedPassword");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.isUsingUnifiedPassword(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPasswordSufficientAfterProfileUnification(int i, int i2) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isPasswordSufficientAfterProfileUnification(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getCurrentFailedPasswordAttempts() {
        return getCurrentFailedPasswordAttempts(myUserId());
    }

    public int getCurrentFailedPasswordAttempts(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return -1;
        }
        try {
            return iDevicePolicyManager.getCurrentFailedPasswordAttempts(this.mContext.getPackageName(), i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getCurrentFailedBiometricAttempts(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return -1;
        }
        try {
            return iDevicePolicyManager.getCurrentFailedBiometricAttempts(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getDoNotAskCredentialsOnBoot() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getDoNotAskCredentialsOnBoot();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMaximumFailedPasswordsForWipe(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setMaximumFailedPasswordsForWipe(componentName, this.mContext.getPackageName(), i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getMaximumFailedPasswordsForWipe(ComponentName componentName) {
        return getMaximumFailedPasswordsForWipe(componentName, myUserId());
    }

    public int getMaximumFailedPasswordsForWipe(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getMaximumFailedPasswordsForWipe(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getProfileWithMinimumFailedPasswordsForWipe(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return -10000;
        }
        try {
            return iDevicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean resetPassword(String str, int i) {
        throwIfParentInstance("resetPassword");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.resetPassword(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setResetPasswordToken(ComponentName componentName, byte[] bArr) {
        throwIfParentInstance("setResetPasswordToken");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.setResetPasswordToken(componentName, this.mContext.getPackageName(), bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean clearResetPasswordToken(ComponentName componentName) {
        throwIfParentInstance("clearResetPasswordToken");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.clearResetPasswordToken(componentName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isResetPasswordTokenActive(ComponentName componentName) {
        throwIfParentInstance("isResetPasswordTokenActive");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isResetPasswordTokenActive(componentName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean resetPasswordWithToken(ComponentName componentName, String str, byte[] bArr, int i) {
        throwIfParentInstance("resetPassword");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.resetPasswordWithToken(componentName, this.mContext.getPackageName(), str, bArr, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMaximumTimeToLock(ComponentName componentName, long j) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setMaximumTimeToLock(componentName, this.mContext.getPackageName(), j, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long getMaximumTimeToLock(ComponentName componentName) {
        return getMaximumTimeToLock(componentName, myUserId());
    }

    public long getMaximumTimeToLock(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0L;
        }
        try {
            return iDevicePolicyManager.getMaximumTimeToLock(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRequiredStrongAuthTimeout(ComponentName componentName, long j) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setRequiredStrongAuthTimeout(componentName, this.mContext.getPackageName(), j, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long getRequiredStrongAuthTimeout(ComponentName componentName) {
        return getRequiredStrongAuthTimeout(componentName, myUserId());
    }

    public long getRequiredStrongAuthTimeout(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 259200000L;
        }
        try {
            return iDevicePolicyManager.getRequiredStrongAuthTimeout(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void lockNow() {
        lockNow(0);
    }

    public void lockNow(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.lockNow(i, this.mContext.getPackageName(), this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void wipeData(int i) {
        wipeDataInternal(i, "", false);
    }

    public void wipeData(int i, CharSequence charSequence) {
        Objects.requireNonNull(charSequence, "reason string is null");
        Preconditions.checkStringNotEmpty(charSequence, "reason string is empty");
        Preconditions.checkArgument((i & 8) == 0, "WIPE_SILENTLY cannot be set");
        wipeDataInternal(i, charSequence.toString(), false);
    }

    public void wipeDevice(int i) {
        wipeDataInternal(i, "", true);
    }

    private void wipeDataInternal(int i, String str, boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.wipeDataWithReason(this.mContext.getPackageName(), i, str, this.mParentInstance, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setFactoryResetProtectionPolicy(ComponentName componentName, FactoryResetProtectionPolicy factoryResetProtectionPolicy) {
        throwIfParentInstance("setFactoryResetProtectionPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setFactoryResetProtectionPolicy(componentName, this.mContext.getPackageName(), factoryResetProtectionPolicy);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(ComponentName componentName) {
        throwIfParentInstance("getFactoryResetProtectionPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getFactoryResetProtectionPolicy(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void sendLostModeLocationUpdate(Executor executor, Consumer<Boolean> consumer) {
        throwIfParentInstance("sendLostModeLocationUpdate");
        if (this.mService == null) {
            executeCallback(AndroidFuture.completedFuture(false), executor, consumer);
            return;
        }
        try {
            AndroidFuture<Boolean> androidFuture = new AndroidFuture<>();
            this.mService.sendLostModeLocationUpdate(androidFuture);
            executeCallback(androidFuture, executor, consumer);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void executeCallback(AndroidFuture<Boolean> androidFuture, final Executor executor, final Consumer<Boolean> consumer) {
        androidFuture.whenComplete(new BiConsumer() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                executor.execute(new Runnable() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DevicePolicyManager.lambda$executeCallback$0(th, consumer, bool);
                    }
                });
            }
        });
    }

    static /* synthetic */ void lambda$executeCallback$0(Throwable th, Consumer consumer, Boolean bool) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (th != null) {
                consumer.accept(false);
            } else {
                consumer.accept(bool);
            }
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public ComponentName setGlobalProxy(ComponentName componentName, Proxy proxy, List<String> list) {
        String str;
        throwIfParentInstance("setGlobalProxy");
        proxy.getClass();
        String str2 = null;
        if (this.mService == null) {
            return null;
        }
        try {
            if (proxy.equals(Proxy.NO_PROXY)) {
                str = null;
            } else {
                if (!proxy.type().equals(Proxy.Type.HTTP)) {
                    throw new IllegalArgumentException();
                }
                Pair<String, String> proxyParameters = getProxyParameters(proxy, list);
                str2 = proxyParameters.first;
                str = proxyParameters.second;
            }
            return this.mService.setGlobalProxy(componentName, str2, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Pair<String, String> getProxyParameters(Proxy proxy, List<String> list) {
        List list2;
        InetSocketAddress inetSocketAddress = (InetSocketAddress) proxy.address();
        String hostName = inetSocketAddress.getHostName();
        int port = inetSocketAddress.getPort();
        if (list == null) {
            list2 = Collections.EMPTY_LIST;
        } else {
            ArrayList arrayList = new ArrayList(list.size());
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().trim());
            }
            list2 = arrayList;
        }
        ProxyInfo proxyInfoBuildDirectProxy = ProxyInfo.buildDirectProxy(hostName, port, list2);
        if (port == 0 || TextUtils.isEmpty(hostName) || !proxyInfoBuildDirectProxy.isValid()) {
            throw new IllegalArgumentException();
        }
        return new Pair<>(hostName + ":" + port, TextUtils.join(",", list2));
    }

    public void setRecommendedGlobalProxy(ComponentName componentName, ProxyInfo proxyInfo) {
        throwIfParentInstance("setRecommendedGlobalProxy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setRecommendedGlobalProxy(componentName, proxyInfo);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public ComponentName getGlobalProxyAdmin() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getGlobalProxyAdmin(myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int setStorageEncryption(ComponentName componentName, boolean z) {
        throwIfParentInstance("setStorageEncryption");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.setStorageEncryption(componentName, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean getStorageEncryption(ComponentName componentName) {
        throwIfParentInstance("getStorageEncryption");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getStorageEncryption(componentName, myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getStorageEncryptionStatus() {
        throwIfParentInstance("getStorageEncryptionStatus");
        return getStorageEncryptionStatus(myUserId());
    }

    public int getStorageEncryptionStatus(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getStorageEncryptionStatus(this.mContext.getPackageName(), i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean approveCaCert(String str, int i, boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.approveCaCert(str, i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCaCertApproved(String str, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isCaCertApproved(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean installCaCert(ComponentName componentName, byte[] bArr) {
        throwIfParentInstance("installCaCert");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.installCaCert(componentName, this.mContext.getPackageName(), bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void uninstallCaCert(ComponentName componentName, byte[] bArr) {
        throwIfParentInstance("uninstallCaCert");
        if (this.mService != null) {
            try {
                this.mService.uninstallCaCerts(componentName, this.mContext.getPackageName(), new String[]{getCaCertAlias(bArr)});
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (CertificateException e2) {
                Log.w(TAG, "Unable to parse certificate", e2);
            }
        }
    }

    public List<byte[]> getInstalledCaCerts(ComponentName componentName) {
        ArrayList arrayList = new ArrayList();
        throwIfParentInstance("getInstalledCaCerts");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.enforceCanManageCaCerts(componentName, this.mContext.getPackageName());
                TrustedCertificateStore trustedCertificateStore = new TrustedCertificateStore();
                for (String str : trustedCertificateStore.userAliases()) {
                    try {
                        arrayList.add(trustedCertificateStore.getCertificate(str).getEncoded());
                    } catch (CertificateException e) {
                        Log.w(TAG, "Could not encode certificate: " + str, e);
                    }
                }
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
        return arrayList;
    }

    public void uninstallAllUserCaCerts(ComponentName componentName) {
        throwIfParentInstance("uninstallAllUserCaCerts");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.uninstallCaCerts(componentName, this.mContext.getPackageName(), (String[]) new TrustedCertificateStore().userAliases().toArray(new String[0]));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean hasCaCertInstalled(ComponentName componentName, byte[] bArr) {
        throwIfParentInstance("hasCaCertInstalled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.enforceCanManageCaCerts(componentName, this.mContext.getPackageName());
                return getCaCertAlias(bArr) != null;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (CertificateException e2) {
                Log.w(TAG, "Could not parse certificate", e2);
            }
        }
        return false;
    }

    public boolean installKeyPair(ComponentName componentName, PrivateKey privateKey, Certificate certificate, String str) {
        return installKeyPair(componentName, privateKey, new Certificate[]{certificate}, str, false);
    }

    public boolean installKeyPair(ComponentName componentName, PrivateKey privateKey, Certificate[] certificateArr, String str, boolean z) {
        return installKeyPair(componentName, privateKey, certificateArr, str, z ? 3 : 2);
    }

    public boolean installKeyPair(ComponentName componentName, PrivateKey privateKey, Certificate[] certificateArr, String str, int i) {
        throwIfParentInstance("installKeyPair");
        try {
            return this.mService.installKeyPair(componentName, this.mContext.getPackageName(), ((PKCS8EncodedKeySpec) KeyFactory.getInstance(privateKey.getAlgorithm()).getKeySpec(privateKey, PKCS8EncodedKeySpec.class)).getEncoded(), Credentials.convertToPem(certificateArr[0]), certificateArr.length > 1 ? Credentials.convertToPem((Certificate[]) Arrays.copyOfRange(certificateArr, 1, certificateArr.length)) : null, str, (i & 1) == 1, (i & 2) == 2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (IOException | CertificateException e2) {
            Log.w(TAG, "Could not pem-encode certificate", e2);
            return false;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e3) {
            Log.w(TAG, "Failed to obtain private key material", e3);
            return false;
        }
    }

    public boolean removeKeyPair(ComponentName componentName, String str) {
        throwIfParentInstance("removeKeyPair");
        try {
            return this.mService.removeKeyPair(componentName, this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasKeyPair(String str) {
        throwIfParentInstance("hasKeyPair");
        try {
            return this.mService.hasKeyPair(this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AttestedKeyPair generateKeyPair(ComponentName componentName, String str, KeyGenParameterSpec keyGenParameterSpec, int i) {
        throwIfParentInstance("generateKeyPair");
        try {
            ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec = new ParcelableKeyGenParameterSpec(keyGenParameterSpec);
            KeymasterCertificateChain keymasterCertificateChain = new KeymasterCertificateChain();
            if (!this.mService.generateKeyPair(componentName, this.mContext.getPackageName(), str, parcelableKeyGenParameterSpec, i, keymasterCertificateChain)) {
                Log.e(TAG, "Error generating key via DevicePolicyManagerService.");
                return null;
            }
            String keystoreAlias = keyGenParameterSpec.getKeystoreAlias();
            try {
                return new AttestedKeyPair(KeyChain.getKeyPair(this.mContext, keystoreAlias), AttestationUtils.isChainValid(keymasterCertificateChain) ? AttestationUtils.parseCertificateChain(keymasterCertificateChain) : null);
            } catch (KeyAttestationException e) {
                Log.e(TAG, "Error parsing attestation chain for alias " + keystoreAlias, e);
                this.mService.removeKeyPair(componentName, this.mContext.getPackageName(), keystoreAlias);
                return null;
            }
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        } catch (ServiceSpecificException e3) {
            Log.w(TAG, String.format("Key Generation failure: %d", Integer.valueOf(e3.errorCode)));
            if (e3.errorCode == 1) {
                throw new StrongBoxUnavailableException("No StrongBox for key generation.");
            }
            throw new RuntimeException(String.format("Unknown error while generating key: %d", Integer.valueOf(e3.errorCode)));
        } catch (KeyChainException e4) {
            Log.w(TAG, "Failed to generate key", e4);
            return null;
        } catch (InterruptedException e5) {
            Log.w(TAG, "Interrupted while generating key", e5);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public boolean grantKeyPairToApp(ComponentName componentName, String str, String str2) {
        throwIfParentInstance("grantKeyPairToApp");
        try {
            return this.mService.setKeyGrantForApp(componentName, this.mContext.getPackageName(), str, str2, true);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public Map<Integer, Set<String>> getKeyPairGrants(String str) {
        throwIfParentInstance("getKeyPairGrants");
        try {
            return this.mService.getKeyPairGrants(this.mContext.getPackageName(), str).getPackagesByUid();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public boolean revokeKeyPairFromApp(ComponentName componentName, String str, String str2) {
        throwIfParentInstance("revokeKeyPairFromApp");
        try {
            return this.mService.setKeyGrantForApp(componentName, this.mContext.getPackageName(), str, str2, false);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean grantKeyPairToWifiAuth(String str) {
        throwIfParentInstance("grantKeyPairToWifiAuth");
        try {
            return this.mService.setKeyGrantToWifiAuth(this.mContext.getPackageName(), str, true);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean revokeKeyPairFromWifiAuth(String str) {
        throwIfParentInstance("revokeKeyPairFromWifiAuth");
        try {
            return this.mService.setKeyGrantToWifiAuth(this.mContext.getPackageName(), str, false);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isKeyPairGrantedToWifiAuth(String str) {
        throwIfParentInstance("isKeyPairGrantedToWifiAuth");
        try {
            return this.mService.isKeyPairGrantedToWifiAuth(this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isDeviceIdAttestationSupported() {
        return this.mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_DEVICE_ID_ATTESTATION);
    }

    public boolean isUniqueDeviceAttestationSupported() {
        return this.mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_DEVICE_UNIQUE_ATTESTATION);
    }

    public boolean setKeyPairCertificate(ComponentName componentName, String str, List<Certificate> list, boolean z) {
        throwIfParentInstance("setKeyPairCertificate");
        try {
            return this.mService.setKeyPairCertificate(componentName, this.mContext.getPackageName(), str, Credentials.convertToPem(list.get(0)), list.size() > 1 ? Credentials.convertToPem((Certificate[]) list.subList(1, list.size()).toArray(new Certificate[0])) : null, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (IOException | CertificateException e2) {
            Log.w(TAG, "Could not pem-encode certificate", e2);
            return false;
        }
    }

    private static String getCaCertAlias(byte[] bArr) throws CertificateException {
        return new TrustedCertificateStore().getCertificateAlias((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr)));
    }

    @Deprecated
    public void setCertInstallerPackage(ComponentName componentName, String str) throws SecurityException {
        throwIfParentInstance("setCertInstallerPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCertInstallerPackage(componentName, str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public String getCertInstallerPackage(ComponentName componentName) throws SecurityException {
        throwIfParentInstance("getCertInstallerPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getCertInstallerPackage(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDelegatedScopes(ComponentName componentName, String str, List<String> list) {
        throwIfParentInstance("setDelegatedScopes");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setDelegatedScopes(componentName, str, list);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<String> getDelegatedScopes(ComponentName componentName, String str) {
        throwIfParentInstance("getDelegatedScopes");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getDelegatedScopes(componentName, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getDelegatePackages(ComponentName componentName, String str) {
        throwIfParentInstance("getDelegatePackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getDelegatePackages(componentName, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAlwaysOnVpnPackage(ComponentName componentName, String str, boolean z) throws PackageManager.NameNotFoundException {
        setAlwaysOnVpnPackage(componentName, str, z, Collections.EMPTY_SET);
    }

    public void setAlwaysOnVpnPackage(ComponentName componentName, String str, boolean z, Set<String> set) throws PackageManager.NameNotFoundException {
        ArrayList arrayList;
        throwIfParentInstance("setAlwaysOnVpnPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            if (set == null) {
                arrayList = null;
            } else {
                try {
                    arrayList = new ArrayList(set);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                } catch (ServiceSpecificException e2) {
                    if (e2.errorCode == 1) {
                        throw new PackageManager.NameNotFoundException(e2.getMessage());
                    }
                    throw new RuntimeException("Unknown error setting always-on VPN: " + e2.errorCode, e2);
                }
            }
            iDevicePolicyManager.setAlwaysOnVpnPackage(componentName, str, z, arrayList);
        }
    }

    public boolean isAlwaysOnVpnLockdownEnabled(ComponentName componentName) {
        throwIfParentInstance("isAlwaysOnVpnLockdownEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isAlwaysOnVpnLockdownEnabled(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAlwaysOnVpnLockdownEnabled() {
        throwIfParentInstance("isAlwaysOnVpnLockdownEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isAlwaysOnVpnLockdownEnabledForUser(myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<String> getAlwaysOnVpnLockdownWhitelist(ComponentName componentName) {
        throwIfParentInstance("getAlwaysOnVpnLockdownWhitelist");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            List<String> alwaysOnVpnLockdownAllowlist = iDevicePolicyManager.getAlwaysOnVpnLockdownAllowlist(componentName);
            if (alwaysOnVpnLockdownAllowlist == null) {
                return null;
            }
            return new HashSet(alwaysOnVpnLockdownAllowlist);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getAlwaysOnVpnPackage(ComponentName componentName) {
        throwIfParentInstance("getAlwaysOnVpnPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getAlwaysOnVpnPackage(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getAlwaysOnVpnPackage() {
        throwIfParentInstance("getAlwaysOnVpnPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getAlwaysOnVpnPackageForUser(myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCameraDisabled(ComponentName componentName, boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCameraDisabled(componentName, this.mContext.getPackageName(), z, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean getCameraDisabled(ComponentName componentName) {
        return getCameraDisabled(componentName, myUserId());
    }

    public boolean getCameraDisabled(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getCameraDisabled(componentName, this.mContext.getPackageName(), i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestBugreport(ComponentName componentName) {
        throwIfParentInstance("requestBugreport");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.requestBugreport(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setScreenCaptureDisabled(ComponentName componentName, boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setScreenCaptureDisabled(componentName, this.mContext.getPackageName(), z, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean getScreenCaptureDisabled(ComponentName componentName) {
        return getScreenCaptureDisabled(componentName, myUserId());
    }

    public boolean getScreenCaptureDisabled(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getScreenCaptureDisabled(componentName, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNearbyNotificationStreamingPolicy(int i) {
        throwIfParentInstance("setNearbyNotificationStreamingPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.setNearbyNotificationStreamingPolicy(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getNearbyNotificationStreamingPolicy() {
        return getNearbyNotificationStreamingPolicy(myUserId());
    }

    public int getNearbyNotificationStreamingPolicy(int i) {
        throwIfParentInstance("getNearbyNotificationStreamingPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getNearbyNotificationStreamingPolicy(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNearbyAppStreamingPolicy(int i) {
        throwIfParentInstance("setNearbyAppStreamingPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.setNearbyAppStreamingPolicy(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getNearbyAppStreamingPolicy() {
        return getNearbyAppStreamingPolicy(myUserId());
    }

    public int getNearbyAppStreamingPolicy(int i) {
        throwIfParentInstance("getNearbyAppStreamingPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getNearbyAppStreamingPolicy(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setAutoTimeRequired(ComponentName componentName, boolean z) {
        throwIfParentInstance("setAutoTimeRequired");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setAutoTimeRequired(componentName, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public boolean getAutoTimeRequired() {
        throwIfParentInstance("getAutoTimeRequired");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getAutoTimeRequired();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAutoTimeEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setAutoTimeEnabled");
        if (this.mService != null) {
            try {
                if (Flags.setAutoTimeEnabledCoexistence()) {
                    this.mService.setAutoTimePolicy(this.mContext.getPackageName(), z ? 2 : 1);
                } else {
                    this.mService.setAutoTimeEnabled(componentName, this.mContext.getPackageName(), z);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean getAutoTimeEnabled(ComponentName componentName) {
        throwIfParentInstance("getAutoTimeEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getAutoTimeEnabled(componentName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAutoTimePolicy(int i) {
        throwIfParentInstance("setAutoTimePolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setAutoTimePolicy(this.mContext.getPackageName(), i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getAutoTimePolicy() {
        throwIfParentInstance("getAutoTimePolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getAutoTimePolicy(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAutoTimeZoneEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setAutoTimeZone");
        if (this.mService != null) {
            try {
                if (Flags.setAutoTimeZoneEnabledCoexistence()) {
                    this.mService.setAutoTimeZonePolicy(this.mContext.getPackageName(), z ? 2 : 1);
                } else {
                    this.mService.setAutoTimeZoneEnabled(componentName, this.mContext.getPackageName(), z);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean getAutoTimeZoneEnabled(ComponentName componentName) {
        throwIfParentInstance("getAutoTimeZone");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getAutoTimeZoneEnabled(componentName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAutoTimeZonePolicy(int i) {
        throwIfParentInstance("setAutoTimeZonePolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setAutoTimeZonePolicy(this.mContext.getPackageName(), i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getAutoTimeZonePolicy() {
        throwIfParentInstance("getAutoTimeZonePolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getAutoTimeZonePolicy(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setForceEphemeralUsers(ComponentName componentName, boolean z) {
        throwIfParentInstance("setForceEphemeralUsers");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setForceEphemeralUsers(componentName, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean getForceEphemeralUsers(ComponentName componentName) {
        throwIfParentInstance("getForceEphemeralUsers");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getForceEphemeralUsers(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeyguardDisabledFeatures(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setKeyguardDisabledFeatures(componentName, this.mContext.getPackageName(), i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getKeyguardDisabledFeatures(ComponentName componentName) {
        return getKeyguardDisabledFeatures(componentName, myUserId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ Integer lambda$new$2(Pair pair) throws RemoteException {
        return Integer.valueOf(getService().getKeyguardDisabledFeatures((ComponentName) pair.first, ((Integer) pair.second).intValue(), isParentInstance()));
    }

    public int getKeyguardDisabledFeatures(ComponentName componentName, int i) {
        if (this.mService != null) {
            return this.mGetKeyGuardDisabledFeaturesCache.query(new Pair<>(componentName, Integer.valueOf(i))).intValue();
        }
        return 0;
    }

    public void setActiveAdmin(ComponentName componentName, boolean z) {
        setActiveAdmin(componentName, z, myUserId());
    }

    public void setActiveAdmin(ComponentName componentName, boolean z, int i) {
        setActiveAdminInternal(componentName, z, i, null);
    }

    public void setActiveAdmin(ComponentName componentName, boolean z, int i, String str) {
        setActiveAdminInternal(componentName, z, i, str);
    }

    private void setActiveAdminInternal(ComponentName componentName, boolean z, int i, String str) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setActiveAdmin(componentName, z, i, str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.getRemoveWarning(componentName, remoteCallback, myUserId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportPasswordChanged(PasswordMetrics passwordMetrics, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportPasswordChanged(passwordMetrics, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportFailedPasswordAttemptWithFailureCount(int i, int i2) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportFailedPasswordAttemptWithFailureCount(i, i2, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportFailedPasswordAttempt(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportFailedPasswordAttempt(i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportSuccessfulPasswordAttempt(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportSuccessfulPasswordAttempt(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportFailedBiometricAttempt(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportFailedBiometricAttempt(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportSuccessfulBiometricAttempt(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportSuccessfulBiometricAttempt(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportKeyguardDismissed(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportKeyguardDismissed(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportKeyguardSecured(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportKeyguardSecured(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean setDeviceOwner(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.setDeviceOwner(componentName, i, true);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setDeviceOwnerOnly(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.setDeviceOwner(componentName, i, false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isDeviceOwnerApp(String str) {
        throwIfParentInstance("isDeviceOwnerApp");
        if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.systemServerRoleControllerEnabled() && CompatChanges.isChangeEnabled(IS_DEVICE_OWNER_USER_AWARE)) {
            return isDeviceOwnerAppOnContextUser(str);
        }
        return isDeviceOwnerAppOnCallingUser(str);
    }

    public boolean isDeviceOwnerAppOnCallingUser(String str) {
        return isDeviceOwnerAppOnAnyUserInner(str, true);
    }

    public boolean isDeviceOwnerAppOnAnyUser(String str) {
        return isDeviceOwnerAppOnAnyUserInner(str, false);
    }

    public ComponentName getDeviceOwnerComponentOnCallingUser() {
        return getDeviceOwnerComponentInner(true);
    }

    @SystemApi
    public ComponentName getDeviceOwnerComponentOnAnyUser() {
        return getDeviceOwnerComponentInner(false);
    }

    private boolean isDeviceOwnerAppOnAnyUserInner(String str, boolean z) {
        ComponentName deviceOwnerComponentInner;
        if (str == null || (deviceOwnerComponentInner = getDeviceOwnerComponentInner(z)) == null) {
            return false;
        }
        return str.equals(deviceOwnerComponentInner.getPackageName());
    }

    private boolean isDeviceOwnerAppOnContextUser(String str) {
        ComponentName deviceOwnerComponentOnUser;
        if (str == null) {
            return false;
        }
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                deviceOwnerComponentOnUser = iDevicePolicyManager.getDeviceOwnerComponentOnUser(myUserId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            deviceOwnerComponentOnUser = null;
        }
        if (deviceOwnerComponentOnUser == null) {
            return false;
        }
        return str.equals(deviceOwnerComponentOnUser.getPackageName());
    }

    private ComponentName getDeviceOwnerComponentInner(boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getDeviceOwnerComponent(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public UserHandle getDeviceOwnerUser() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            int deviceOwnerUserId = iDevicePolicyManager.getDeviceOwnerUserId();
            if (deviceOwnerUserId != -10000) {
                return UserHandle.of(deviceOwnerUserId);
            }
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDeviceOwnerUserId() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return -10000;
        }
        try {
            return iDevicePolicyManager.getDeviceOwnerUserId();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void clearDeviceOwnerApp(String str) {
        throwIfParentInstance("clearDeviceOwnerApp");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.clearDeviceOwner(str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public String getDeviceOwner() {
        throwIfParentInstance("getDeviceOwner");
        ComponentName deviceOwnerComponentOnCallingUser = getDeviceOwnerComponentOnCallingUser();
        if (deviceOwnerComponentOnCallingUser != null) {
            return deviceOwnerComponentOnCallingUser.getPackageName();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$new$3(Void r1) throws RemoteException {
        return Boolean.valueOf(getService().hasDeviceOwner());
    }

    @SystemApi
    public boolean isDeviceManaged() {
        return this.mHasDeviceOwnerCache.query(null).booleanValue();
    }

    @SystemApi
    public String getDeviceOwnerNameOnAnyUser() {
        throwIfParentInstance("getDeviceOwnerNameOnAnyUser");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getDeviceOwnerName();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public boolean setActiveProfileOwner(ComponentName componentName, String str) throws IllegalArgumentException {
        throwIfParentInstance("setActiveProfileOwner");
        if (this.mService == null) {
            return false;
        }
        try {
            int iMyUserId = myUserId();
            this.mService.setActiveAdmin(componentName, false, iMyUserId, null);
            return this.mService.setProfileOwner(componentName, iMyUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void clearProfileOwner(ComponentName componentName) {
        throwIfParentInstance("clearProfileOwner");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.clearProfileOwner(componentName);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean hasUserSetupCompleted() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.hasUserSetupCompleted();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setProfileOwner(ComponentName componentName, int i) throws IllegalArgumentException {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.setProfileOwner(componentName, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDeviceOwnerLockScreenInfo(ComponentName componentName, CharSequence charSequence) {
        throwIfParentInstance("setDeviceOwnerLockScreenInfo");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setDeviceOwnerLockScreenInfo(componentName, charSequence);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public CharSequence getDeviceOwnerLockScreenInfo() {
        throwIfParentInstance("getDeviceOwnerLockScreenInfo");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getDeviceOwnerLockScreenInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String[] setPackagesSuspended(ComponentName componentName, String[] strArr, boolean z) {
        throwIfParentInstance("setPackagesSuspended");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return strArr;
        }
        try {
            return iDevicePolicyManager.setPackagesSuspended(componentName, this.mContext.getPackageName(), strArr, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPackageSuspended(ComponentName componentName, String str) throws PackageManager.NameNotFoundException {
        throwIfParentInstance("isPackageSuspended");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isPackageSuspended(componentName, this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, "IllegalArgumentException checking isPackageSuspended", e2);
            throw new PackageManager.NameNotFoundException(str);
        }
    }

    public void setProfileEnabled(ComponentName componentName) {
        throwIfParentInstance("setProfileEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setProfileEnabled(componentName);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setProfileName(ComponentName componentName, String str) {
        throwIfParentInstance("setProfileName");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setProfileName(componentName, str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isProfileOwnerApp(String str) {
        throwIfParentInstance("isProfileOwnerApp");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            ComponentName profileOwnerAsUser = iDevicePolicyManager.getProfileOwnerAsUser(myUserId());
            if (profileOwnerAsUser != null) {
                if (profileOwnerAsUser.getPackageName().equals(str)) {
                    return true;
                }
            }
            return false;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public ComponentName getProfileOwner() throws IllegalArgumentException {
        throwIfParentInstance("getProfileOwner");
        return getProfileOwnerAsUser(this.mContext.getUserId());
    }

    public ComponentName getProfileOwnerAsUser(UserHandle userHandle) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getProfileOwnerAsUser(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ComponentName getProfileOwnerAsUser(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getProfileOwnerAsUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ComponentName lambda$new$4(UserHandle userHandle) throws RemoteException {
        return getService().getProfileOwnerOrDeviceOwnerSupervisionComponent(userHandle);
    }

    public ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle userHandle) {
        if (this.mService != null) {
            return this.mGetProfileOwnerOrDeviceOwnerSupervisionComponentCache.query(userHandle);
        }
        return null;
    }

    public boolean isSupervisionComponent(ComponentName componentName) {
        if (this.mService == null) {
            return false;
        }
        try {
            return getService().isSupervisionComponent(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getProfileOwnerName() throws IllegalArgumentException {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getProfileOwnerName(this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getProfileOwnerNameAsUser(int i) throws IllegalArgumentException {
        throwIfParentInstance("getProfileOwnerNameAsUser");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getProfileOwnerName(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$new$5(Object obj) throws RemoteException {
        return Boolean.valueOf(getService().isOrganizationOwnedDeviceWithManagedProfile());
    }

    public boolean isOrganizationOwnedDeviceWithManagedProfile() {
        throwIfParentInstance("isOrganizationOwnedDeviceWithManagedProfile");
        if (this.mService != null) {
            return this.mIsOrganizationOwnedDeviceWithManagedProfileCache.query(null).booleanValue();
        }
        return false;
    }

    public boolean hasDeviceIdentifierAccess(String str, int i, int i2) {
        IDevicePolicyManager iDevicePolicyManager;
        throwIfParentInstance("hasDeviceIdentifierAccess");
        if (str == null || (iDevicePolicyManager = this.mService) == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.checkDeviceIdentifierAccess(str, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addPersistentPreferredActivity(ComponentName componentName, IntentFilter intentFilter, ComponentName componentName2) {
        throwIfParentInstance("addPersistentPreferredActivity");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.addPersistentPreferredActivity(componentName, this.mContext.getPackageName(), intentFilter, componentName2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void clearPackagePersistentPreferredActivities(ComponentName componentName, String str) {
        throwIfParentInstance("clearPackagePersistentPreferredActivities");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.clearPackagePersistentPreferredActivities(componentName, this.mContext.getPackageName(), str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setDefaultSmsApplication(ComponentName componentName, String str) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setDefaultSmsApplication(componentName, this.mContext.getPackageName(), str, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setDefaultDialerApplication(String str) {
        throwIfParentInstance("setDefaultDialerApplication");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setDefaultDialerApplication(str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public void setApplicationRestrictionsManagingPackage(ComponentName componentName, String str) throws PackageManager.NameNotFoundException {
        throwIfParentInstance("setApplicationRestrictionsManagingPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                if (iDevicePolicyManager.setApplicationRestrictionsManagingPackage(componentName, str)) {
                } else {
                    throw new PackageManager.NameNotFoundException(str);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public String getApplicationRestrictionsManagingPackage(ComponentName componentName) {
        throwIfParentInstance("getApplicationRestrictionsManagingPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getApplicationRestrictionsManagingPackage(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean isCallerApplicationRestrictionsManagingPackage() {
        throwIfParentInstance("isCallerApplicationRestrictionsManagingPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isCallerApplicationRestrictionsManagingPackage(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setApplicationRestrictions(ComponentName componentName, String str, Bundle bundle) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setApplicationRestrictions(componentName, this.mContext.getPackageName(), str, bundle, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setTrustAgentConfiguration(componentName, this.mContext.getPackageName(), componentName2, persistableBundle, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<PersistableBundle> getTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2) {
        return getTrustAgentConfiguration(componentName, componentName2, myUserId());
    }

    public List<PersistableBundle> getTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                return iDevicePolicyManager.getTrustAgentConfiguration(componentName, componentName2, i, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return new ArrayList();
    }

    @Deprecated
    public void setCrossProfileCallerIdDisabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setCrossProfileCallerIdDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCrossProfileCallerIdDisabled(componentName, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public boolean getCrossProfileCallerIdDisabled(ComponentName componentName) {
        throwIfParentInstance("getCrossProfileCallerIdDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getCrossProfileCallerIdDisabled(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean getCrossProfileCallerIdDisabled(UserHandle userHandle) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getCrossProfileCallerIdDisabledForUser(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCredentialManagerPolicy(PackagePolicy packagePolicy) {
        throwIfParentInstance("setCredentialManagerPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCredentialManagerPolicy(packagePolicy);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public PackagePolicy getCredentialManagerPolicy() {
        throwIfParentInstance("getCredentialManagerPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getCredentialManagerPolicy(myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setManagedProfileCallerIdAccessPolicy(PackagePolicy packagePolicy) {
        throwIfParentInstance("setManagedProfileCallerIdAccessPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setManagedProfileCallerIdAccessPolicy(packagePolicy);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public PackagePolicy getManagedProfileCallerIdAccessPolicy() {
        throwIfParentInstance("getManagedProfileCallerIdAccessPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getManagedProfileCallerIdAccessPolicy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semGetCrossProfileCallerIdDisabled(UserHandle userHandle) {
        return getCrossProfileCallerIdDisabled(userHandle);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean hasManagedProfileCallerIdAccess(UserHandle userHandle, String str) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.hasManagedProfileCallerIdAccess(userHandle.getIdentifier(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setManagedProfileContactsAccessPolicy(PackagePolicy packagePolicy) {
        throwIfParentInstance("setManagedProfileContactsAccessPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setManagedProfileContactsAccessPolicy(packagePolicy);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public PackagePolicy getManagedProfileContactsAccessPolicy() {
        throwIfParentInstance("getManagedProfileContactsAccessPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getManagedProfileContactsAccessPolicy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean hasManagedProfileContactsAccess(UserHandle userHandle, String str) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.hasManagedProfileContactsAccess(userHandle.getIdentifier(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setCrossProfileContactsSearchDisabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setCrossProfileContactsSearchDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCrossProfileContactsSearchDisabled(componentName, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public boolean getCrossProfileContactsSearchDisabled(ComponentName componentName) {
        throwIfParentInstance("getCrossProfileContactsSearchDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getCrossProfileContactsSearchDisabled(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semGetCrossProfileContactsSearchDisabled(UserHandle userHandle) {
        return getCrossProfileContactsSearchDisabled(userHandle);
    }

    @Deprecated
    public boolean getCrossProfileContactsSearchDisabled(UserHandle userHandle) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getCrossProfileContactsSearchDisabledForUser(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startManagedQuickContact(String str, long j, boolean z, long j2, Intent intent) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.startManagedQuickContact(str, j, z, j2, intent);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void startManagedQuickContact(String str, long j, Intent intent) {
        startManagedQuickContact(str, j, false, 0L, intent);
    }

    public void setBluetoothContactSharingDisabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setBluetoothContactSharingDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setBluetoothContactSharingDisabled(componentName, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean semGetBluetoothContactSharingDisabled(UserHandle userHandle) {
        return getBluetoothContactSharingDisabled(userHandle);
    }

    public boolean getBluetoothContactSharingDisabled(ComponentName componentName) {
        throwIfParentInstance("getBluetoothContactSharingDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.getBluetoothContactSharingDisabled(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean getBluetoothContactSharingDisabled(UserHandle userHandle) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.getBluetoothContactSharingDisabledForUser(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addCrossProfileIntentFilter(ComponentName componentName, IntentFilter intentFilter, int i) {
        throwIfParentInstance("addCrossProfileIntentFilter");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.addCrossProfileIntentFilter(componentName, this.mContext.getPackageName(), intentFilter, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void clearCrossProfileIntentFilters(ComponentName componentName) {
        throwIfParentInstance("clearCrossProfileIntentFilters");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.clearCrossProfileIntentFilters(componentName, this.mContext.getPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean setPermittedAccessibilityServices(ComponentName componentName, List<String> list) {
        throwIfParentInstance("setPermittedAccessibilityServices");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.setPermittedAccessibilityServices(componentName, list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public List<String> semGetPermittedAccessibilityServices(int i) {
        return getPermittedAccessibilityServices(i);
    }

    public List<String> getPermittedAccessibilityServices(ComponentName componentName) {
        throwIfParentInstance("getPermittedAccessibilityServices");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getPermittedAccessibilityServices(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAccessibilityServicePermittedByAdmin(ComponentName componentName, String str, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isAccessibilityServicePermittedByAdmin(componentName, str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getPermittedAccessibilityServices(int i) {
        throwIfParentInstance("getPermittedAccessibilityServices");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getPermittedAccessibilityServicesForUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setPermittedInputMethods(ComponentName componentName, List<String> list) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.setPermittedInputMethods(componentName, this.mContext.getPackageName(), list, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getPermittedInputMethods(ComponentName componentName) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getPermittedInputMethods(componentName, this.mContext.getPackageName(), this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isInputMethodPermittedByAdmin(ComponentName componentName, String str, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isInputMethodPermittedByAdmin(componentName, str, i, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getPermittedInputMethodsForCurrentUser() {
        throwIfParentInstance("getPermittedInputMethodsForCurrentUser");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getPermittedInputMethodsAsUser(UserHandle.myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getPermittedInputMethods() {
        throwIfParentInstance("getPermittedInputMethods");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getPermittedInputMethodsAsUser(myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setPermittedCrossProfileNotificationListeners(ComponentName componentName, List<String> list) {
        throwIfParentInstance("setPermittedCrossProfileNotificationListeners");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.setPermittedCrossProfileNotificationListeners(componentName, list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getPermittedCrossProfileNotificationListeners(ComponentName componentName) {
        throwIfParentInstance("getPermittedCrossProfileNotificationListeners");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getPermittedCrossProfileNotificationListeners(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationListenerServicePermitted(String str, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.isNotificationListenerServicePermitted(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getKeepUninstalledPackages(ComponentName componentName) {
        throwIfParentInstance("getKeepUninstalledPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getKeepUninstalledPackages(componentName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeepUninstalledPackages(ComponentName componentName, List<String> list) {
        throwIfParentInstance("setKeepUninstalledPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setKeepUninstalledPackages(componentName, this.mContext.getPackageName(), list);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public UserHandle createAndManageUser(ComponentName componentName, String str, ComponentName componentName2, PersistableBundle persistableBundle, int i) {
        throwIfParentInstance("createAndManageUser");
        try {
            return this.mService.createAndManageUser(componentName, str, componentName2, persistableBundle, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw new UserManager.UserOperationException(e2.getMessage(), e2.errorCode);
        }
    }

    public boolean removeUser(ComponentName componentName, UserHandle userHandle) {
        throwIfParentInstance("removeUser");
        try {
            return this.mService.removeUser(componentName, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean switchUser(ComponentName componentName, UserHandle userHandle) {
        throwIfParentInstance("switchUser");
        try {
            return this.mService.switchUser(componentName, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int startUserInBackground(ComponentName componentName, UserHandle userHandle) {
        throwIfParentInstance("startUserInBackground");
        try {
            return this.mService.startUserInBackground(componentName, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int stopUser(ComponentName componentName, UserHandle userHandle) {
        throwIfParentInstance("stopUser");
        try {
            return this.mService.stopUser(componentName, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int logoutUser(ComponentName componentName) {
        throwIfParentInstance("logoutUser");
        try {
            return this.mService.logoutUser(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setManagedSubscriptionsPolicy(ManagedSubscriptionsPolicy managedSubscriptionsPolicy) {
        throwIfParentInstance("setManagedSubscriptionsPolicy");
        try {
            this.mService.setManagedSubscriptionsPolicy(managedSubscriptionsPolicy);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy() {
        throwIfParentInstance("getManagedSubscriptionsPolicy");
        try {
            return this.mService.getManagedSubscriptionsPolicy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public int logoutUser() {
        try {
            return this.mService.logoutUserInternal();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public UserHandle getLogoutUser() {
        try {
            int logoutUserId = this.mService.getLogoutUserId();
            if (logoutUserId == -10000) {
                return null;
            }
            return UserHandle.of(logoutUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<UserHandle> getSecondaryUsers(ComponentName componentName) {
        throwIfParentInstance("getSecondaryUsers");
        try {
            return this.mService.getSecondaryUsers(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isEphemeralUser(ComponentName componentName) {
        throwIfParentInstance("isEphemeralUser");
        try {
            return this.mService.isEphemeralUser(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getApplicationRestrictions(ComponentName componentName, String str) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getApplicationRestrictions(componentName, this.mContext.getPackageName(), str, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addUserRestriction(ComponentName componentName, String str) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserRestriction(componentName, this.mContext.getPackageName(), str, true, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void addUserRestriction(String str, String str2, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserRestrictionForUser(str, str2, true, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void addUserRestrictionGlobally(String str) {
        throwIfParentInstance("addUserRestrictionGlobally");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserRestrictionGlobally(this.mContext.getPackageName(), str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void addUserRestrictionGlobally(String str, String str2) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserRestrictionGloballyFromSystem(str, str2, true);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void clearUserRestriction(ComponentName componentName, String str) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserRestriction(componentName, this.mContext.getPackageName(), str, false, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void clearUserRestriction(String str, String str2, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserRestrictionForUser(str, str2, false, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void clearUserRestrictionGlobally(String str, String str2) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserRestrictionGloballyFromSystem(str, str2, false);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public Bundle getUserRestrictions(ComponentName componentName) {
        Bundle userRestrictions;
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                userRestrictions = iDevicePolicyManager.getUserRestrictions(componentName, this.mContext.getPackageName(), this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            userRestrictions = null;
        }
        return userRestrictions == null ? new Bundle() : userRestrictions;
    }

    public Bundle getUserRestrictionsGlobally() {
        Bundle userRestrictionsGlobally;
        throwIfParentInstance("createAdminSupportIntent");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                userRestrictionsGlobally = iDevicePolicyManager.getUserRestrictionsGlobally(this.mContext.getPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            userRestrictionsGlobally = null;
        }
        return userRestrictionsGlobally == null ? new Bundle() : userRestrictionsGlobally;
    }

    public Intent createAdminSupportIntent(String str) {
        throwIfParentInstance("createAdminSupportIntent");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            Intent intentCreateAdminSupportIntent = iDevicePolicyManager.createAdminSupportIntent(str);
            if (intentCreateAdminSupportIntent != null) {
                intentCreateAdminSupportIntent.prepareToEnterProcess(32, this.mContext.getAttributionSource());
            }
            return intentCreateAdminSupportIntent;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getEnforcingAdminAndUserDetails(int i, String str) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getEnforcingAdminAndUserDetails(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public EnforcingAdmin getEnforcingAdmin(int i, String str) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getEnforcingAdmin(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<EnforcingAdmin> getEnforcingAdminsForRestriction(int i, String str) {
        if (this.mService == null) {
            return null;
        }
        try {
            return new HashSet(this.mService.getEnforcingAdminsForRestriction(i, str));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setApplicationHidden(ComponentName componentName, String str, boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.setApplicationHidden(componentName, this.mContext.getPackageName(), str, z, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isApplicationHidden(ComponentName componentName, String str) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isApplicationHidden(componentName, this.mContext.getPackageName(), str, this.mParentInstance);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enableSystemApp(ComponentName componentName, String str) {
        throwIfParentInstance("enableSystemApp");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.enableSystemApp(componentName, this.mContext.getPackageName(), str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int enableSystemApp(ComponentName componentName, Intent intent) {
        throwIfParentInstance("enableSystemApp");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.enableSystemAppWithIntent(componentName, this.mContext.getPackageName(), intent);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean installExistingPackage(ComponentName componentName, String str) {
        throwIfParentInstance("installExistingPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.installExistingPackage(componentName, this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAccountManagementDisabled(ComponentName componentName, String str, boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setAccountManagementDisabled(componentName, this.mContext.getPackageName(), str, z, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public String[] getAccountTypesWithManagementDisabled() {
        return getAccountTypesWithManagementDisabledAsUser(myUserId(), this.mParentInstance);
    }

    public String[] getAccountTypesWithManagementDisabledAsUser(int i) {
        return getAccountTypesWithManagementDisabledAsUser(i, false);
    }

    public String[] getAccountTypesWithManagementDisabledAsUser(int i, boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getAccountTypesWithManagementDisabledAsUser(i, this.mContext.getPackageName(), z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public void setSecondaryLockscreenEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setSecondaryLockscreenEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setSecondaryLockscreenEnabled(componentName, z, null);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void setSecondaryLockscreenEnabled(boolean z, PersistableBundle persistableBundle) {
        throwIfParentInstance("setSecondaryLockscreenEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setSecondaryLockscreenEnabled(null, z, persistableBundle);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean isSecondaryLockscreenEnabled(UserHandle userHandle) {
        throwIfParentInstance("isSecondaryLockscreenEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isSecondaryLockscreenEnabled(userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setLockTaskPackages(ComponentName componentName, String[] strArr) throws SecurityException {
        throwIfParentInstance("setLockTaskPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setLockTaskPackages(componentName, this.mContext.getPackageName(), strArr);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public String[] getLockTaskPackages(ComponentName componentName) {
        throwIfParentInstance("getLockTaskPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                return iDevicePolicyManager.getLockTaskPackages(componentName, this.mContext.getPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return new String[0];
    }

    public boolean isLockTaskPermitted(String str) {
        throwIfParentInstance("isLockTaskPermitted");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isLockTaskPermitted(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setLockTaskFeatures(ComponentName componentName, int i) {
        throwIfParentInstance("setLockTaskFeatures");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setLockTaskFeatures(componentName, this.mContext.getPackageName(), i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getLockTaskFeatures(ComponentName componentName) {
        throwIfParentInstance("getLockTaskFeatures");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getLockTaskFeatures(componentName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPreferentialNetworkServiceEnabled(boolean z) {
        throwIfParentInstance("setPreferentialNetworkServiceEnabled");
        PreferentialNetworkServiceConfig.Builder builder = new PreferentialNetworkServiceConfig.Builder();
        builder.setEnabled(z);
        if (z) {
            builder.setNetworkId(1);
        }
        setPreferentialNetworkServiceConfigs(List.of(builder.build()));
    }

    public boolean isPreferentialNetworkServiceEnabled() {
        throwIfParentInstance("isPreferentialNetworkServiceEnabled");
        return getPreferentialNetworkServiceConfigs().stream().anyMatch(new Predicate() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((PreferentialNetworkServiceConfig) obj).isEnabled();
            }
        });
    }

    public void setPreferentialNetworkServiceConfigs(List<PreferentialNetworkServiceConfig> list) {
        throwIfParentInstance("setPreferentialNetworkServiceConfigs");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.setPreferentialNetworkServiceConfigs(list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs() {
        throwIfParentInstance("getPreferentialNetworkServiceConfigs");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return List.of(PreferentialNetworkServiceConfig.DEFAULT);
        }
        try {
            return iDevicePolicyManager.getPreferentialNetworkServiceConfigs();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setGlobalSetting(ComponentName componentName, String str, String str2) {
        throwIfParentInstance("setGlobalSetting");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setGlobalSetting(componentName, str, str2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setSystemSetting(ComponentName componentName, String str, String str2) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setSystemSetting(componentName, str, str2, this.mParentInstance);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setConfiguredNetworksLockdownState(ComponentName componentName, boolean z) {
        throwIfParentInstance("setConfiguredNetworksLockdownState");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setConfiguredNetworksLockdownState(componentName, this.mContext.getPackageName(), z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean hasLockdownAdminConfiguredNetworks(ComponentName componentName) {
        throwIfParentInstance("hasLockdownAdminConfiguredNetworks");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.hasLockdownAdminConfiguredNetworks(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setTime(ComponentName componentName, long j) {
        throwIfParentInstance("setTime");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.setTime(componentName, this.mContext.getPackageName(), j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setTimeZone(ComponentName componentName, String str) {
        throwIfParentInstance("setTimeZone");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.setTimeZone(componentName, this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setLocationEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setLocationEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setLocationEnabled(componentName, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setSecureSetting(ComponentName componentName, String str, String str2) {
        throwIfParentInstance("setSecureSetting");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setSecureSetting(componentName, str, str2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setRestrictionsProvider(ComponentName componentName, ComponentName componentName2) {
        throwIfParentInstance("setRestrictionsProvider");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setRestrictionsProvider(componentName, componentName2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setMasterVolumeMuted(ComponentName componentName, boolean z) {
        throwIfParentInstance("setMasterVolumeMuted");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setMasterVolumeMuted(componentName, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isMasterVolumeMuted(ComponentName componentName) {
        throwIfParentInstance("isMasterVolumeMuted");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isMasterVolumeMuted(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUninstallBlocked(ComponentName componentName, String str, boolean z) {
        throwIfParentInstance("setUninstallBlocked");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUninstallBlocked(componentName, this.mContext.getPackageName(), str, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isUninstallBlocked(ComponentName componentName, String str) {
        throwIfParentInstance("isUninstallBlocked");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isUninstallBlocked(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addCrossProfileWidgetProvider(ComponentName componentName, String str) {
        throwIfParentInstance("addCrossProfileWidgetProvider");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.addCrossProfileWidgetProvider(componentName, this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeCrossProfileWidgetProvider(ComponentName componentName, String str) {
        throwIfParentInstance("removeCrossProfileWidgetProvider");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.removeCrossProfileWidgetProvider(componentName, this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getCrossProfileWidgetProviders(ComponentName componentName) {
        throwIfParentInstance("getCrossProfileWidgetProviders");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                List<String> crossProfileWidgetProviders = iDevicePolicyManager.getCrossProfileWidgetProviders(componentName, this.mContext.getPackageName());
                if (crossProfileWidgetProviders != null) {
                    return crossProfileWidgetProviders;
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_LIST;
    }

    public void setUserIcon(ComponentName componentName, Bitmap bitmap) {
        throwIfParentInstance("setUserIcon");
        try {
            this.mService.setUserIcon(componentName, bitmap);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSystemUpdatePolicy(ComponentName componentName, SystemUpdatePolicy systemUpdatePolicy) {
        throwIfParentInstance("setSystemUpdatePolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setSystemUpdatePolicy(componentName, this.mContext.getPackageName(), systemUpdatePolicy);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public SystemUpdatePolicy getSystemUpdatePolicy() {
        throwIfParentInstance("getSystemUpdatePolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getSystemUpdatePolicy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearSystemUpdatePolicyFreezePeriodRecord() {
        throwIfParentInstance("clearSystemUpdatePolicyFreezePeriodRecord");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.clearSystemUpdatePolicyFreezePeriodRecord();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setKeyguardDisabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setKeyguardDisabled");
        try {
            return this.mService.setKeyguardDisabled(componentName, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setStatusBarDisabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setStatusBarDisabled");
        try {
            return this.mService.setStatusBarDisabled(componentName, this.mContext.getPackageName(), z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isStatusBarDisabled() {
        throwIfParentInstance("isStatusBarDisabled");
        try {
            return this.mService.isStatusBarDisabled(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semNotifyPendingSystemUpdate(long j) {
        notifyPendingSystemUpdate(j);
    }

    @SystemApi
    public void notifyPendingSystemUpdate(long j) {
        throwIfParentInstance("notifyPendingSystemUpdate");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.notifyPendingSystemUpdate(SystemUpdateInfo.of(j));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void notifyPendingSystemUpdate(long j, boolean z) {
        throwIfParentInstance("notifyPendingSystemUpdate");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.notifyPendingSystemUpdate(SystemUpdateInfo.of(j, z));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public SystemUpdateInfo getPendingSystemUpdate(ComponentName componentName) {
        throwIfParentInstance("getPendingSystemUpdate");
        try {
            return this.mService.getPendingSystemUpdate(componentName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPermissionPolicy(ComponentName componentName, int i) {
        throwIfParentInstance("setPermissionPolicy");
        try {
            this.mService.setPermissionPolicy(componentName, this.mContext.getPackageName(), i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPermissionPolicy(ComponentName componentName) {
        throwIfParentInstance("getPermissionPolicy");
        try {
            return this.mService.getPermissionPolicy(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setPermissionGrantState(ComponentName componentName, String str, String str2, int i) {
        throwIfParentInstance("setPermissionGrantState");
        try {
            final CompletableFuture completableFuture = new CompletableFuture();
            this.mService.setPermissionGrantState(componentName, this.mContext.getPackageName(), str, str2, i, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda10
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(Bundle bundle) {
                    completableFuture.complete(Boolean.valueOf(bundle != null));
                }
            }));
            BackgroundThread.getHandler().sendMessageDelayed(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda11
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((CompletableFuture) obj).complete((Boolean) obj2);
                }
            }, completableFuture, false), 20000L);
            return ((Boolean) completableFuture.get()).booleanValue();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (InterruptedException | ExecutionException e2) {
            throw new RuntimeException(e2);
        }
    }

    public int getPermissionGrantState(ComponentName componentName, String str, String str2) {
        throwIfParentInstance("getPermissionGrantState");
        try {
            return this.mService.getPermissionGrantState(componentName, this.mContext.getPackageName(), str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isProvisioningAllowed(String str) {
        throwIfParentInstance("isProvisioningAllowed");
        try {
            return this.mService.isProvisioningAllowed(str, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int checkProvisioningPrecondition(String str, String str2) {
        try {
            return this.mService.checkProvisioningPrecondition(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isManagedProfile(ComponentName componentName) {
        throwIfParentInstance("isManagedProfile");
        try {
            return this.mService.isManagedProfile(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiMacAddress(ComponentName componentName) {
        throwIfParentInstance("getWifiMacAddress");
        try {
            return this.mService.getWifiMacAddress(componentName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reboot(ComponentName componentName) {
        throwIfParentInstance("reboot");
        try {
            this.mService.reboot(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setShortSupportMessage(ComponentName componentName, CharSequence charSequence) {
        throwIfParentInstance("setShortSupportMessage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setShortSupportMessage(componentName, this.mContext.getPackageName(), charSequence);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public CharSequence getShortSupportMessage(ComponentName componentName) {
        throwIfParentInstance("getShortSupportMessage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getShortSupportMessage(componentName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setLongSupportMessage(ComponentName componentName, CharSequence charSequence) {
        throwIfParentInstance("setLongSupportMessage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setLongSupportMessage(componentName, charSequence);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public CharSequence getLongSupportMessage(ComponentName componentName) {
        throwIfParentInstance("getLongSupportMessage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getLongSupportMessage(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public CharSequence getShortSupportMessageForUser(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getShortSupportMessageForUser(componentName, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public CharSequence getLongSupportMessageForUser(ComponentName componentName, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getLongSupportMessageForUser(componentName, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public DevicePolicyManager getParentProfileInstance(ComponentName componentName) {
        throwIfParentInstance("getParentProfileInstance");
        if (!((UserManager) this.mContext.getSystemService(UserManager.class)).isManagedProfile()) {
            throw new SecurityException("The current user does not have a parent profile.");
        }
        return new DevicePolicyManager(this.mContext, this.mService, true);
    }

    public void setSecurityLoggingEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setSecurityLoggingEnabled");
        try {
            this.mService.setSecurityLoggingEnabled(componentName, this.mContext.getPackageName(), z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSecurityLoggingEnabled(ComponentName componentName) {
        throwIfParentInstance("isSecurityLoggingEnabled");
        try {
            return this.mService.isSecurityLoggingEnabled(componentName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setAuditLogEnabled(boolean z) {
        throwIfParentInstance("setAuditLogEnabled");
        try {
            this.mService.setAuditLogEnabled(this.mContext.getPackageName(), z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isAuditLogEnabled() {
        throwIfParentInstance(SecContentProviderURI.AUDITLOGPOLICY_AUDITLOGENABLED_METHOD);
        try {
            return this.mService.isAuditLogEnabled(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setAuditLogEventCallback(Executor executor, Consumer<List<SecurityLog.SecurityEvent>> consumer) {
        throwIfParentInstance("setAuditLogEventCallback");
        try {
            this.mService.setAuditLogEventsCallback(this.mContext.getPackageName(), new AnonymousClass1(this, executor, consumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.admin.DevicePolicyManager$1, reason: invalid class name */
    class AnonymousClass1 extends IAuditLogEventsCallback.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(DevicePolicyManager devicePolicyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // android.app.admin.IAuditLogEventsCallback
        public void onNewAuditLogEvents(final List<SecurityLog.SecurityEvent> list) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$callback;
            executor.execute(new Runnable() { // from class: android.app.admin.DevicePolicyManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(list);
                }
            });
        }
    }

    @SystemApi
    public void clearAuditLogEventCallback() {
        throwIfParentInstance("clearAuditLogEventCallback");
        try {
            this.mService.setAuditLogEventsCallback(this.mContext.getPackageName(), null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SecurityLog.SecurityEvent> retrieveSecurityLogs(ComponentName componentName) {
        throwIfParentInstance("retrieveSecurityLogs");
        try {
            ParceledListSlice parceledListSliceRetrieveSecurityLogs = this.mService.retrieveSecurityLogs(componentName, this.mContext.getPackageName());
            if (parceledListSliceRetrieveSecurityLogs != null) {
                return parceledListSliceRetrieveSecurityLogs.getList();
            }
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long forceNetworkLogs() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return -1L;
        }
        try {
            return iDevicePolicyManager.forceNetworkLogs();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long forceSecurityLogs() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0L;
        }
        try {
            return iDevicePolicyManager.forceSecurityLogs();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public DevicePolicyManager getParentProfileInstance(UserInfo userInfo) {
        this.mContext.checkSelfPermission(Manifest.permission.MANAGE_PROFILE_AND_DEVICE_OWNERS);
        if (!userInfo.isManagedProfile()) {
            throw new SecurityException("The user " + userInfo.id + " does not have a parent profile.");
        }
        return new DevicePolicyManager(this.mContext, this.mService, true);
    }

    public List<String> setMeteredDataDisabledPackages(ComponentName componentName, List<String> list) {
        throwIfParentInstance("setMeteredDataDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return list;
        }
        try {
            return iDevicePolicyManager.setMeteredDataDisabledPackages(componentName, list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getMeteredDataDisabledPackages(ComponentName componentName) {
        throwIfParentInstance("getMeteredDataDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                return iDevicePolicyManager.getMeteredDataDisabledPackages(componentName);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return new ArrayList();
    }

    public boolean isMeteredDataDisabledPackageForUser(ComponentName componentName, String str, int i) {
        throwIfParentInstance("getMeteredDataDisabledForUser");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isMeteredDataDisabledPackageForUser(componentName, str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SecurityLog.SecurityEvent> retrievePreRebootSecurityLogs(ComponentName componentName) {
        throwIfParentInstance("retrievePreRebootSecurityLogs");
        try {
            ParceledListSlice parceledListSliceRetrievePreRebootSecurityLogs = this.mService.retrievePreRebootSecurityLogs(componentName, this.mContext.getPackageName());
            if (parceledListSliceRetrievePreRebootSecurityLogs != null) {
                return parceledListSliceRetrievePreRebootSecurityLogs.getList();
            }
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setOrganizationColor(ComponentName componentName, int i) {
        throwIfParentInstance("setOrganizationColor");
        try {
            this.mService.setOrganizationColor(componentName, i | (-16777216));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setOrganizationColorForUser(int i, int i2) {
        try {
            this.mService.setOrganizationColorForUser(i | (-16777216), i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int getOrganizationColor(ComponentName componentName) {
        throwIfParentInstance("getOrganizationColor");
        try {
            return this.mService.getOrganizationColor(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int getOrganizationColorForUser(int i) {
        try {
            return this.mService.getOrganizationColorForUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setOrganizationName(ComponentName componentName, CharSequence charSequence) {
        throwIfParentInstance("setOrganizationName");
        try {
            this.mService.setOrganizationName(componentName, this.mContext.getPackageName(), charSequence);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public CharSequence getOrganizationName(ComponentName componentName) {
        throwIfParentInstance("getOrganizationName");
        try {
            return this.mService.getOrganizationName(componentName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$new$8(Object obj) throws RemoteException {
        return getService().getDeviceOwnerOrganizationName();
    }

    @SystemApi
    public CharSequence getDeviceOwnerOrganizationName() {
        return this.mGetDeviceOwnerOrganizationNameCache.query(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CharSequence lambda$new$9(Integer num) throws RemoteException {
        return getService().getOrganizationNameForUser(num.intValue());
    }

    public CharSequence getOrganizationNameForUser(int i) {
        return this.mGetOrganizationNameForUserCache.query(Integer.valueOf(i));
    }

    @SystemApi
    public int getUserProvisioningState() {
        throwIfParentInstance("getUserProvisioningState");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getUserProvisioningState(this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserProvisioningState(int i, int i2) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserProvisioningState(i, i2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void setUserProvisioningState(int i, UserHandle userHandle) {
        setUserProvisioningState(i, userHandle.getIdentifier());
    }

    public void setAffiliationIds(ComponentName componentName, Set<String> set) {
        throwIfParentInstance("setAffiliationIds");
        if (set == null) {
            throw new IllegalArgumentException("ids must not be null");
        }
        try {
            this.mService.setAffiliationIds(componentName, new ArrayList(set));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<String> getAffiliationIds(ComponentName componentName) {
        throwIfParentInstance("getAffiliationIds");
        try {
            return new ArraySet(this.mService.getAffiliationIds(componentName));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAffiliatedUser() {
        throwIfParentInstance("isAffiliatedUser");
        try {
            return this.mService.isCallingUserAffiliated();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAffiliatedUser(int i) {
        try {
            return this.mService.isAffiliatedUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUninstallInQueue(String str) {
        try {
            return this.mService.isUninstallInQueue(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void uninstallPackageWithActiveAdmins(String str) {
        try {
            this.mService.uninstallPackageWithActiveAdmins(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceRemoveActiveAdmin(ComponentName componentName, int i) {
        try {
            this.mService.forceRemoveActiveAdmin(componentName, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isDeviceProvisioned() {
        try {
            return this.mService.isDeviceProvisioned();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDeviceProvisioningConfigApplied() {
        try {
            this.mService.setDeviceProvisioningConfigApplied();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isDeviceProvisioningConfigApplied() {
        try {
            return this.mService.isDeviceProvisioningConfigApplied();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceUpdateUserSetupComplete(int i) {
        try {
            this.mService.forceUpdateUserSetupComplete(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void throwIfParentInstance(String str) {
        if (this.mParentInstance) {
            throw new SecurityException(str + " cannot be called on the parent instance");
        }
    }

    public void setBackupServiceEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setBackupServiceEnabled");
        try {
            this.mService.setBackupServiceEnabled(componentName, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBackupServiceEnabled(ComponentName componentName) {
        throwIfParentInstance("isBackupServiceEnabled");
        try {
            return this.mService.isBackupServiceEnabled(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNetworkLoggingEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setNetworkLoggingEnabled");
        try {
            this.mService.setNetworkLoggingEnabled(componentName, this.mContext.getPackageName(), z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$new$10(ComponentName componentName) throws RemoteException {
        return Boolean.valueOf(getService().isNetworkLoggingEnabled(componentName, getContext().getPackageName()));
    }

    public boolean isNetworkLoggingEnabled(ComponentName componentName) {
        throwIfParentInstance("isNetworkLoggingEnabled");
        return this.mIsNetworkLoggingEnabledCache.query(componentName).booleanValue();
    }

    public List<NetworkEvent> retrieveNetworkLogs(ComponentName componentName, long j) {
        throwIfParentInstance("retrieveNetworkLogs");
        try {
            return this.mService.retrieveNetworkLogs(componentName, this.mContext.getPackageName(), j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean bindDeviceAdminServiceAsUser(ComponentName componentName, Intent intent, ServiceConnection serviceConnection, int i, UserHandle userHandle) {
        throwIfParentInstance("bindDeviceAdminServiceAsUser");
        try {
            Context context = this.mContext;
            IServiceConnection serviceDispatcher = context.getServiceDispatcher(serviceConnection, context.getMainThreadHandler(), Integer.toUnsignedLong(i));
            intent.prepareToLeaveProcess(this.mContext);
            return this.mService.bindDeviceAdminServiceAsUser(componentName, this.mContext.getIApplicationThread(), this.mContext.getActivityToken(), intent, serviceDispatcher, Integer.toUnsignedLong(i), userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean bindDeviceAdminServiceAsUser(ComponentName componentName, Intent intent, ServiceConnection serviceConnection, Context.BindServiceFlags bindServiceFlags, UserHandle userHandle) {
        throwIfParentInstance("bindDeviceAdminServiceAsUser");
        try {
            Context context = this.mContext;
            IServiceConnection serviceDispatcher = context.getServiceDispatcher(serviceConnection, context.getMainThreadHandler(), bindServiceFlags.getValue());
            intent.prepareToLeaveProcess(this.mContext);
            return this.mService.bindDeviceAdminServiceAsUser(componentName, this.mContext.getIApplicationThread(), this.mContext.getActivityToken(), intent, serviceDispatcher, bindServiceFlags.getValue(), userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName componentName) {
        throwIfParentInstance("getBindDeviceAdminTargetUsers");
        try {
            return this.mService.getBindDeviceAdminTargetUsers(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getLastSecurityLogRetrievalTime() {
        try {
            return this.mService.getLastSecurityLogRetrievalTime();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getLastBugReportRequestTime() {
        try {
            return this.mService.getLastBugReportRequestTime();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getLastNetworkLogRetrievalTime() {
        try {
            return this.mService.getLastNetworkLogRetrievalTime();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCurrentInputMethodSetByOwner() {
        try {
            return this.mService.isCurrentInputMethodSetByOwner();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getOwnerInstalledCaCerts(UserHandle userHandle) {
        try {
            return this.mService.getOwnerInstalledCaCerts(userHandle).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isFactoryResetProtectionPolicySupported() {
        try {
            return this.mService.isFactoryResetProtectionPolicySupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearApplicationUserData(ComponentName componentName, String str, Executor executor, OnClearApplicationUserDataListener onClearApplicationUserDataListener) {
        throwIfParentInstance("clearAppData");
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onClearApplicationUserDataListener);
        try {
            this.mService.clearApplicationUserData(componentName, str, new AnonymousClass2(this, executor, onClearApplicationUserDataListener));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.admin.DevicePolicyManager$2, reason: invalid class name */
    class AnonymousClass2 extends IPackageDataObserver.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OnClearApplicationUserDataListener val$listener;

        AnonymousClass2(DevicePolicyManager devicePolicyManager, Executor executor, OnClearApplicationUserDataListener onClearApplicationUserDataListener) {
            this.val$executor = executor;
            this.val$listener = onClearApplicationUserDataListener;
        }

        @Override // android.content.pm.IPackageDataObserver
        public void onRemoveCompleted(final String str, final boolean z) {
            Executor executor = this.val$executor;
            final OnClearApplicationUserDataListener onClearApplicationUserDataListener = this.val$listener;
            executor.execute(new Runnable() { // from class: android.app.admin.DevicePolicyManager$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    onClearApplicationUserDataListener.onApplicationUserDataCleared(str, z);
                }
            });
        }
    }

    public void setLogoutEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setLogoutEnabled");
        try {
            this.mService.setLogoutEnabled(componentName, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isLogoutEnabled() {
        throwIfParentInstance("isLogoutEnabled");
        try {
            return this.mService.isLogoutEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<String> getDisallowedSystemApps(ComponentName componentName, int i, String str) {
        try {
            return new ArraySet(this.mService.getDisallowedSystemApps(componentName, i, str));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void transferOwnership(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) {
        throwIfParentInstance("transferOwnership");
        try {
            this.mService.transferOwnership(componentName, componentName2, persistableBundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setStartUserSessionMessage(ComponentName componentName, CharSequence charSequence) {
        throwIfParentInstance("setStartUserSessionMessage");
        try {
            this.mService.setStartUserSessionMessage(componentName, charSequence);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setEndUserSessionMessage(ComponentName componentName, CharSequence charSequence) {
        throwIfParentInstance("setEndUserSessionMessage");
        try {
            this.mService.setEndUserSessionMessage(componentName, charSequence);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public CharSequence getStartUserSessionMessage(ComponentName componentName) {
        throwIfParentInstance("getStartUserSessionMessage");
        try {
            return this.mService.getStartUserSessionMessage(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public CharSequence getEndUserSessionMessage(ComponentName componentName) {
        throwIfParentInstance("getEndUserSessionMessage");
        try {
            return this.mService.getEndUserSessionMessage(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int addOverrideApn(ComponentName componentName, ApnSetting apnSetting) {
        throwIfParentInstance("addOverrideApn");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return -1;
        }
        try {
            return iDevicePolicyManager.addOverrideApn(componentName, apnSetting);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateOverrideApn(ComponentName componentName, int i, ApnSetting apnSetting) {
        throwIfParentInstance("updateOverrideApn");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.updateOverrideApn(componentName, i, apnSetting);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeOverrideApn(ComponentName componentName, int i) {
        throwIfParentInstance("removeOverrideApn");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.removeOverrideApn(componentName, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ApnSetting> getOverrideApns(ComponentName componentName) {
        throwIfParentInstance("getOverrideApns");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                return iDevicePolicyManager.getOverrideApns(componentName);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_LIST;
    }

    public void setOverrideApnsEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setOverrideApnEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setOverrideApnsEnabled(componentName, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isOverrideApnEnabled(ComponentName componentName) {
        throwIfParentInstance("isOverrideApnEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isOverrideApnEnabled(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PersistableBundle getTransferOwnershipBundle() {
        throwIfParentInstance("getTransferOwnershipBundle");
        try {
            return this.mService.getTransferOwnershipBundle();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setGlobalPrivateDnsModeOpportunistic(ComponentName componentName) {
        throwIfParentInstance("setGlobalPrivateDnsModeOpportunistic");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 2;
        }
        try {
            return iDevicePolicyManager.setGlobalPrivateDns(componentName, 2, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setGlobalPrivateDnsModeSpecifiedHost(ComponentName componentName, String str) {
        throwIfParentInstance("setGlobalPrivateDnsModeSpecifiedHost");
        Objects.requireNonNull(str, "dns resolver is null");
        if (this.mService == null) {
            return 2;
        }
        if (NetworkUtilsInternal.isWeaklyValidatedHostname(str) && !PrivateDnsConnectivityChecker.canConnectToPrivateDnsServer(str)) {
            return 1;
        }
        try {
            return this.mService.setGlobalPrivateDns(componentName, 3, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void installSystemUpdate(ComponentName componentName, Uri uri, final Executor executor, final InstallSystemUpdateCallback installSystemUpdateCallback) {
        throwIfParentInstance("installUpdate");
        if (this.mService == null) {
            return;
        }
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = this.mContext.getContentResolver().openFileDescriptor(uri, "r");
            try {
                this.mService.installUpdateFromFile(componentName, this.mContext.getPackageName(), parcelFileDescriptorOpenFileDescriptor, new StartInstallingUpdateCallback.Stub() { // from class: android.app.admin.DevicePolicyManager.3
                    @Override // android.app.admin.StartInstallingUpdateCallback
                    public void onStartInstallingUpdateError(int i, String str) {
                        DevicePolicyManager.this.executeCallback(i, str, executor, installSystemUpdateCallback);
                    }
                });
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                }
            } catch (Throwable th) {
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    try {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (FileNotFoundException e2) {
            Log.w(TAG, e2);
            executeCallback(4, Log.getStackTraceString(e2), executor, installSystemUpdateCallback);
        } catch (IOException e3) {
            Log.w(TAG, e3);
            executeCallback(1, Log.getStackTraceString(e3), executor, installSystemUpdateCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeCallback(final int i, final String str, Executor executor, final InstallSystemUpdateCallback installSystemUpdateCallback) {
        executor.execute(new Runnable() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                installSystemUpdateCallback.onInstallUpdateError(i, str);
            }
        });
    }

    public int getGlobalPrivateDnsMode(ComponentName componentName) {
        throwIfParentInstance("setGlobalPrivateDns");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getGlobalPrivateDnsMode(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getGlobalPrivateDnsHost(ComponentName componentName) {
        throwIfParentInstance("setGlobalPrivateDns");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getGlobalPrivateDnsHost(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public void setProfileOwnerCanAccessDeviceIds(ComponentName componentName) {
        if (this.mContext.getApplicationInfo().targetSdkVersion > 29) {
            throw new UnsupportedOperationException("This method is deprecated. use markProfileOwnerOnOrganizationOwnedDevice instead.");
        }
        setProfileOwnerOnOrganizationOwnedDevice(componentName, true);
    }

    public void setProfileOwnerOnOrganizationOwnedDevice(ComponentName componentName, boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.setProfileOwnerOnOrganizationOwnedDevice(componentName, myUserId(), z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setCrossProfileCalendarPackages(ComponentName componentName, Set<String> set) {
        ArrayList arrayList;
        throwIfParentInstance("setCrossProfileCalendarPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            if (set == null) {
                arrayList = null;
            } else {
                try {
                    arrayList = new ArrayList(set);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            iDevicePolicyManager.setCrossProfileCalendarPackages(componentName, arrayList);
        }
    }

    @Deprecated
    public Set<String> getCrossProfileCalendarPackages(ComponentName componentName) {
        throwIfParentInstance("getCrossProfileCalendarPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                List<String> crossProfileCalendarPackages = iDevicePolicyManager.getCrossProfileCalendarPackages(componentName);
                if (crossProfileCalendarPackages == null) {
                    return null;
                }
                return new ArraySet(crossProfileCalendarPackages);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_SET;
    }

    public boolean isPackageAllowedToAccessCalendar(String str) {
        throwIfParentInstance("isPackageAllowedToAccessCalendar");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isPackageAllowedToAccessCalendarForUser(str, myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<String> getCrossProfileCalendarPackages() {
        throwIfParentInstance("getCrossProfileCalendarPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                List<String> crossProfileCalendarPackagesForUser = iDevicePolicyManager.getCrossProfileCalendarPackagesForUser(myUserId());
                if (crossProfileCalendarPackagesForUser == null) {
                    return null;
                }
                return new ArraySet(crossProfileCalendarPackagesForUser);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_SET;
    }

    public void setCrossProfilePackages(ComponentName componentName, Set<String> set) {
        throwIfParentInstance("setCrossProfilePackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCrossProfilePackages(componentName, new ArrayList(set));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public Set<String> getCrossProfilePackages(ComponentName componentName) {
        throwIfParentInstance("getCrossProfilePackages");
        if (this.mService != null) {
            try {
                return new ArraySet(this.mService.getCrossProfilePackages(componentName));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_SET;
    }

    public Set<String> getAllCrossProfilePackages() {
        throwIfParentInstance("getAllCrossProfilePackages");
        if (this.mService != null) {
            try {
                return new ArraySet(this.mService.getAllCrossProfilePackages(this.mContext.getUserId()));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_SET;
    }

    public Set<String> getDefaultCrossProfilePackages() {
        throwIfParentInstance("getDefaultCrossProfilePackages");
        if (this.mService != null) {
            try {
                return new ArraySet(this.mService.getDefaultCrossProfilePackages());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_SET;
    }

    @SystemApi
    public boolean isManagedKiosk() {
        throwIfParentInstance("isManagedKiosk");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isManagedKiosk();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isUnattendedManagedKiosk() {
        throwIfParentInstance("isUnattendedManagedKiosk");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isUnattendedManagedKiosk();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean startViewCalendarEventInManagedProfile(long j, long j2, long j3, boolean z, int i) {
        throwIfParentInstance("startViewCalendarEventInManagedProfile");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.startViewCalendarEventInManagedProfile(this.mContext.getPackageName(), j, j2, j3, z, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setApplicationExemptions(String str, Set<Integer> set) throws PackageManager.NameNotFoundException {
        throwIfParentInstance("setApplicationExemptions");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setApplicationExemptions(this.mContext.getPackageName(), str, ArrayUtils.convertToIntArray((ArraySet<Integer>) new ArraySet(set)));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (ServiceSpecificException e2) {
                if (e2.errorCode == 1) {
                    throw new PackageManager.NameNotFoundException(e2.getMessage());
                }
                throw new RuntimeException("Unknown error setting application exemptions: " + e2.errorCode, e2);
            }
        }
    }

    @SystemApi
    public Set<Integer> getApplicationExemptions(String str) throws PackageManager.NameNotFoundException {
        throwIfParentInstance("getApplicationExemptions");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return Collections.EMPTY_SET;
        }
        try {
            return intArrayToSet(iDevicePolicyManager.getApplicationExemptions(str));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 1) {
                throw new PackageManager.NameNotFoundException(e2.getMessage());
            }
            throw new RuntimeException("Unknown error getting application exemptions: " + e2.errorCode, e2);
        }
    }

    private Set<Integer> intArrayToSet(int[] iArr) {
        ArraySet arraySet = new ArraySet();
        for (int i : iArr) {
            arraySet.add(Integer.valueOf(i));
        }
        return arraySet;
    }

    public void setUserControlDisabledPackages(ComponentName componentName, List<String> list) {
        throwIfParentInstance("setUserControlDisabledPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserControlDisabledPackages(componentName, this.mContext.getPackageName(), list);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<String> getUserControlDisabledPackages(ComponentName componentName) {
        throwIfParentInstance("getUserControlDisabledPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                return iDevicePolicyManager.getUserControlDisabledPackages(componentName, this.mContext.getPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_LIST;
    }

    public void setCommonCriteriaModeEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("setCommonCriteriaModeEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCommonCriteriaModeEnabled(componentName, this.mContext.getPackageName(), z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isCommonCriteriaModeEnabled(ComponentName componentName) {
        throwIfParentInstance("isCommonCriteriaModeEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isCommonCriteriaModeEnabled(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPersonalAppsSuspendedReasons(ComponentName componentName) {
        throwIfParentInstance("getPersonalAppsSuspendedReasons");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getPersonalAppsSuspendedReasons(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPersonalAppsSuspended(ComponentName componentName, boolean z) {
        throwIfParentInstance("setPersonalAppsSuspended");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPersonalAppsSuspended(componentName, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setManagedProfileMaximumTimeOff(ComponentName componentName, long j) {
        throwIfParentInstance("setManagedProfileMaximumTimeOff");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setManagedProfileMaximumTimeOff(componentName, j);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long getManagedProfileMaximumTimeOff(ComponentName componentName) {
        throwIfParentInstance("getManagedProfileMaximumTimeOff");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0L;
        }
        try {
            return iDevicePolicyManager.getManagedProfileMaximumTimeOff(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void acknowledgeDeviceCompliant() {
        throwIfParentInstance("acknowledgeDeviceCompliant");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.acknowledgeDeviceCompliant();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isComplianceAcknowledgementRequired() {
        throwIfParentInstance("isComplianceAcknowledgementRequired");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isComplianceAcknowledgementRequired();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canProfileOwnerResetPasswordWhenLocked(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.canProfileOwnerResetPasswordWhenLocked(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getSamsungSDcardEncryptionStatus(ComponentName componentName) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.getSamsungSDcardEncryptionStatus(componentName, UserHandle.myUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return false;
        }
    }

    public void setNextOperationSafety(int i, int i2) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setNextOperationSafety(i, i2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public String getEnrollmentSpecificId() {
        throwIfParentInstance("getEnrollmentSpecificId");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return "";
        }
        try {
            return iDevicePolicyManager.getEnrollmentSpecificId(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setOrganizationId(String str) {
        throwIfParentInstance("setOrganizationId");
        setOrganizationIdForUser(this.mContext.getPackageName(), str, myUserId());
    }

    public void setOrganizationIdForUser(String str, String str2, int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.setOrganizationIdForUser(str, str2, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearOrganizationId() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.clearOrganizationIdForUser(myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public UserHandle createAndProvisionManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams) throws ProvisioningException {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.createAndProvisionManagedProfile(managedProfileProvisioningParams, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw new ProvisioningException(e2, e2.errorCode, this.getErrorMessage(e2));
        }
    }

    @SystemApi
    public UserHandle createManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams) throws ProvisioningException {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.createManagedProfile(managedProfileProvisioningParams, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw new ProvisioningException(e2, e2.errorCode, this.getErrorMessage(e2));
        }
    }

    @SystemApi
    public void finalizeCreateManagedProfile(ManagedProfileProvisioningParams managedProfileProvisioningParams, UserHandle userHandle) throws ProvisioningException {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.finalizeCreateManagedProfile(managedProfileProvisioningParams, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean removeManagedProfile() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            throw new IllegalStateException("Could not find DevicePolicyManagerService");
        }
        try {
            return iDevicePolicyManager.removeManagedProfile(myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void finalizeWorkProfileProvisioning(UserHandle userHandle, Account account) {
        Objects.requireNonNull(userHandle, "managedProfileUser can't be null");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            throw new IllegalStateException("Could not find DevicePolicyManagerService");
        }
        try {
            iDevicePolicyManager.finalizeWorkProfileProvisioning(userHandle, account);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void provisionFullyManagedDevice(FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams) throws ProvisioningException {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.provisionFullyManagedDevice(fullyManagedDeviceProvisioningParams, this.mContext.getPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (ServiceSpecificException e2) {
                throw new ProvisioningException(e2, e2.errorCode, getErrorMessage(e2));
            }
        }
    }

    public void resetDefaultCrossProfileIntentFilters(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.resetDefaultCrossProfileIntentFilters(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean canAdminGrantSensorsPermissions() {
        throwIfParentInstance("canAdminGrantSensorsPermissions");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.canAdminGrantSensorsPermissions();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDeviceOwnerType(ComponentName componentName, int i) {
        throwIfParentInstance("setDeviceOwnerType");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setDeviceOwnerType(componentName, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public int getDeviceOwnerType(ComponentName componentName) {
        throwIfParentInstance("getDeviceOwnerType");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getDeviceOwnerType(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isFinancedDevice() {
        try {
            if (isDeviceManaged()) {
                if (getDeviceOwnerType(getDeviceOwnerComponentOnAnyUser()) == 1) {
                    return true;
                }
            }
        } catch (IllegalStateException unused) {
        }
        return false;
    }

    public void setUsbDataSignalingEnabled(boolean z) {
        throwIfParentInstance("setUsbDataSignalingEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUsbDataSignalingEnabled(this.mContext.getPackageName(), z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isUsbDataSignalingEnabled() {
        throwIfParentInstance("isUsbDataSignalingEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.isUsbDataSignalingEnabled(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canUsbDataSignalingBeDisabled() {
        throwIfParentInstance("canUsbDataSignalingBeDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.canUsbDataSignalingBeDisabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<UserHandle> listForegroundAffiliatedUsers() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            return iDevicePolicyManager.listForegroundAffiliatedUsers();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<String> getPolicyExemptApps() {
        if (this.mService == null) {
            return Collections.EMPTY_SET;
        }
        try {
            return new HashSet(this.mService.listPolicyExemptApps());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Intent createProvisioningIntentFromNfcIntent(Intent intent) {
        return ProvisioningIntentHelper.createProvisioningIntentFromNfcIntent(intent);
    }

    public void setMinimumRequiredWifiSecurityLevel(int i) {
        throwIfParentInstance("setMinimumRequiredWifiSecurityLevel");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setMinimumRequiredWifiSecurityLevel(this.mContext.getPackageName(), i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getMinimumRequiredWifiSecurityLevel() {
        throwIfParentInstance("getMinimumRequiredWifiSecurityLevel");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getMinimumRequiredWifiSecurityLevel();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiSsidPolicy(WifiSsidPolicy wifiSsidPolicy) {
        throwIfParentInstance("setWifiSsidPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.setWifiSsidPolicy(this.mContext.getPackageName(), wifiSsidPolicy);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public WifiSsidPolicy getWifiSsidPolicy() {
        throwIfParentInstance("getWifiSsidPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getWifiSsidPolicy(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isDevicePotentiallyStolen() {
        throwIfParentInstance("isDevicePotentiallyStolen");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isDevicePotentiallyStolen(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public DevicePolicyResourcesManager getResources() {
        return this.mResourcesManager;
    }

    @SystemApi
    public boolean isDpcDownloaded() {
        throwIfParentInstance("isDpcDownloaded");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isDpcDownloaded();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDpcDownloaded(boolean z) {
        throwIfParentInstance("setDpcDownloaded");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setDpcDownloaded(z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public String getDevicePolicyManagementRoleHolderPackage() {
        return extractPackageNameFromDeviceManagerConfig(this.mContext.getString(17039421));
    }

    public String getDevicePolicyManagementRoleHolderUpdaterPackage() {
        String string = this.mContext.getString(R.string.config_devicePolicyManagementUpdater);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    @SystemApi
    public List<UserHandle> getPolicyManagedProfiles(UserHandle userHandle) {
        Objects.requireNonNull(userHandle);
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                return iDevicePolicyManager.getPolicyManagedProfiles(userHandle);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_LIST;
    }

    private String extractPackageNameFromDeviceManagerConfig(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.contains(":") ? str.split(":")[0] : str;
    }

    public void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void calculateHasIncompatibleAccounts() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.calculateHasIncompatibleAccounts();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean shouldAllowBypassingDevicePolicyManagementRoleQualification() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.shouldAllowBypassingDevicePolicyManagementRoleQualification();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public DevicePolicyState getDevicePolicyState() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getDevicePolicyState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean triggerDevicePolicyEngineMigration(boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.triggerDevicePolicyEngineMigration(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isDeviceFinanced() {
        throwIfParentInstance("isDeviceFinanced");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.isDeviceFinanced(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getFinancedDeviceKioskRoleHolder() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            return iDevicePolicyManager.getFinancedDeviceKioskRoleHolder(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOnboardingBugreportV2FlagEnabled() {
        return Flags.onboardingBugreportV2Enabled();
    }

    public boolean isOnboardingConsentlessBugreportFlagEnabled() {
        return Flags.onboardingConsentlessBugreports();
    }

    public Set<Integer> getSubscriptionIds() {
        throwIfParentInstance("getSubscriptionIds");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                return intArrayToSet(iDevicePolicyManager.getSubscriptionIds(this.mContext.getPackageName()));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return new HashSet();
    }

    @SystemApi
    public void setMaxPolicyStorageLimit(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setMaxPolicyStorageLimit(this.mContext.getPackageName(), i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public int getMaxPolicyStorageLimit() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return -1;
        }
        try {
            return iDevicePolicyManager.getMaxPolicyStorageLimit(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceSetMaxPolicyStorageLimit(int i) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.forceSetMaxPolicyStorageLimit(this.mContext.getPackageName(), i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getPolicySizeForAdmin(EnforcingAdmin enforcingAdmin) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return -1;
        }
        try {
            return iDevicePolicyManager.getPolicySizeForAdmin(this.mContext.getPackageName(), enforcingAdmin);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getHeadlessDeviceOwnerMode() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            return iDevicePolicyManager.getHeadlessDeviceOwnerMode(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semSetPasswordQuality(ComponentName componentName, int i) {
        throwIfParentInstance("semSetPasswordQuality");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetPasswordQuality(componentName, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semSetPasswordMinimumLength(ComponentName componentName, int i) {
        throwIfParentInstance("semSetPasswordMinimumLength");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetPasswordMinimumLength(componentName, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semSetPasswordMinimumUpperCase(ComponentName componentName, int i) {
        throwIfParentInstance("semSetPasswordMinimumUpperCase");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetPasswordMinimumUpperCase(componentName, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semSetPasswordMinimumLowerCase(ComponentName componentName, int i) {
        throwIfParentInstance("semSetPasswordMinimumLowerCase");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetPasswordMinimumLowerCase(componentName, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semSetPasswordMinimumNonLetter(ComponentName componentName, int i) {
        throwIfParentInstance("semSetPasswordMinimumNonLetter");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetPasswordMinimumNonLetter(componentName, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semSetPasswordHistoryLength(ComponentName componentName, int i) {
        throwIfParentInstance("semSetPasswordHistoryLength");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetPasswordHistoryLength(componentName, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semSetPasswordExpirationTimeout(ComponentName componentName, long j) {
        throwIfParentInstance("semSetPasswordExpirationTimeout");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetPasswordExpirationTimeout(componentName, j);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean semIsActivePasswordSufficient() {
        throwIfParentInstance("semIsActivePasswordSufficient");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.semIsActivePasswordSufficient(myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semSetSimplePasswordEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance("semSetSimplePasswordEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetSimplePasswordEnabled(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public boolean semIsSimplePasswordEnabled(ComponentName componentName) {
        throwIfParentInstance("semIsSimplePasswordEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.semIsSimplePasswordEnabled(componentName, myUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return true;
        }
    }

    public void semSetKeyguardDisabledFeatures(ComponentName componentName, int i) {
        throwIfParentInstance("semSetKeyguardDisabledFeatures");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetKeyguardDisabledFeatures(componentName, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semSetAllowStorageCard(ComponentName componentName, boolean z) {
        throwIfParentInstance(EasPolicyIdentifiers.EAS_POLICY_ALLOW_STORAGE_CARD);
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetAllowStorageCard(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public boolean semGetAllowStorageCard(ComponentName componentName) {
        throwIfParentInstance("semGetAllowStorageCard");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.semGetAllowStorageCard(componentName, myUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return true;
        }
    }

    public void semSetAllowWifi(ComponentName componentName, boolean z) {
        throwIfParentInstance(EasPolicyIdentifiers.EAS_POLICY_ALLOW_WIFI_POLICY);
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetAllowWifi(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public boolean semGetAllowWifi(ComponentName componentName) {
        throwIfParentInstance("semGetAllowWifi");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.semGetAllowWifi(componentName, myUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return true;
        }
    }

    public void semSetAllowTextMessaging(ComponentName componentName, boolean z) {
        throwIfParentInstance(EasPolicyIdentifiers.EAS_POLICY_ALLOW_TEXT_MESSAGING);
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetAllowTextMessaging(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public boolean semGetAllowTextMessaging(ComponentName componentName) {
        throwIfParentInstance("semGetAllowTextMessaging");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.semGetAllowTextMessaging(componentName, myUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return true;
        }
    }

    public void semSetAllowPopImapEmail(ComponentName componentName, boolean z) {
        throwIfParentInstance(EasPolicyIdentifiers.EAS_POLICY_ALLOW_POP_IMAP_EMAIL);
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetAllowPopImapEmail(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public boolean semGetAllowPopImapEmail(ComponentName componentName) {
        throwIfParentInstance("semGetAllowPopImapEmail");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.semGetAllowPopImapEmail(componentName, myUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return true;
        }
    }

    public void semSetAllowBrowser(ComponentName componentName, boolean z) {
        throwIfParentInstance(EasPolicyIdentifiers.EAS_POLICY_ALLOW_BROWSER);
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetAllowBrowser(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public boolean semGetAllowBrowser(ComponentName componentName) {
        throwIfParentInstance("semGetAllowBrowser");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.semGetAllowBrowser(componentName, myUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return true;
        }
    }

    public void semSetAllowInternetSharing(ComponentName componentName, boolean z) {
        throwIfParentInstance(EasPolicyIdentifiers.EAS_POLICY_ALLOW_INTERNET_SHARING);
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetAllowInternetSharing(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public boolean semGetAllowInternetSharing(ComponentName componentName) {
        throwIfParentInstance("semGetAllowInternetSharing");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.semGetAllowInternetSharing(componentName, myUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return true;
        }
    }

    public void semSetAllowBluetoothMode(ComponentName componentName, int i) {
        throwIfParentInstance(EasPolicyIdentifiers.EAS_POLICY_ALLOW_BLUETOOTH_MODE);
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetAllowBluetoothMode(componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public int semGetAllowBluetoothMode(ComponentName componentName) {
        throwIfParentInstance("semGetAllowBluetoothMode");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 2;
        }
        try {
            return iDevicePolicyManager.semGetAllowBluetoothMode(componentName, myUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return 2;
        }
    }

    public void semSetAllowDesktopSync(ComponentName componentName, boolean z) {
        throwIfParentInstance(EasPolicyIdentifiers.EAS_POLICY_ALLOW_DESKTOP_SYNC);
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetAllowDesktopSync(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public boolean semGetAllowDesktopSync(ComponentName componentName) {
        throwIfParentInstance("semGetAllowDesktopSync");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.semGetAllowDesktopSync(componentName, myUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return true;
        }
    }

    public void semSetAllowIrda(ComponentName componentName, boolean z) {
        throwIfParentInstance(EasPolicyIdentifiers.EAS_POLICY_ALLOW_IRDA);
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetAllowIrda(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public boolean semGetAllowIrda(ComponentName componentName) {
        throwIfParentInstance("semGetAllowIrda");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return true;
        }
        try {
            return iDevicePolicyManager.semGetAllowIrda(componentName, myUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return true;
        }
    }

    public void semSetRequireStorageCardEncryption(ComponentName componentName, boolean z) {
        throwIfParentInstance(EasPolicyIdentifiers.EAS_POLICY_SET_REQUIRE_STORAGE_CARD_ENCRYPTION);
        semSetRequireStorageCardEncryption(componentName, z, false);
    }

    public void semSetRequireStorageCardEncryption(ComponentName componentName, boolean z, boolean z2) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetRequireStorageCardEncryption(componentName, z, z2);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public boolean semGetRequireStorageCardEncryption(ComponentName componentName) {
        throwIfParentInstance("semGetRequireStorageCardEncryption");
        return semGetRequireStorageCardEncryption(componentName, false);
    }

    public boolean semGetRequireStorageCardEncryption(ComponentName componentName, boolean z) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return false;
        }
        try {
            return iDevicePolicyManager.semGetRequireStorageCardEncryption(componentName, myUserId(), z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return false;
        }
    }

    public void semSetChangeNotificationEnabled(ComponentName componentName, boolean z) {
        throwIfParentInstance(EasPolicyIdentifiers.EAS_POLICY_SET_CHANGE_NOTIFICATION_ENABLED);
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.semSetChangeNotificationEnabled(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public void semSetAllowThirdPartyAppList(ComponentName componentName, String str) {
        throwIfParentInstance("semSetAllowThirdPartyAppList");
        Log.w(TAG, "semSetAllowThirdPartyAppList - No more support from R OS");
    }

    public void semSetBlockPreloadedPackages(ComponentName componentName, String str) {
        throwIfParentInstance("semSetBlockPreloadedPackages");
        Log.w(TAG, "semGetAllowThirdPartyAppList - No more support from R OS");
    }

    public void semSetAllowUnsignedApp(ComponentName componentName, boolean z) {
        throwIfParentInstance("semSetAllowUnsignedApp");
        Log.w(TAG, "semSetAllowUnsignedApp - No more support from R OS");
    }

    public boolean semGetAllowUnsignedApp(ComponentName componentName) {
        throwIfParentInstance("semGetAllowUnsignedApp");
        Log.w(TAG, "semGetAllowUnsignedApp - No more support from R OS");
        return true;
    }

    public void semSetAllowUnsignedInstallationPackage(ComponentName componentName, boolean z) {
        throwIfParentInstance("semSetAllowUnsignedInstallationPackage");
        Log.w(TAG, "semSetAllowUnsignedInstallationPackage - No more support from R OS");
    }

    public boolean semGetAllowUnsignedInstallationPackage(ComponentName componentName) {
        throwIfParentInstance("semGetAllowUnsignedInstallationPackage");
        Log.w(TAG, "semGetAllowUnsignedInstallationPackage - No more support from R OS");
        return true;
    }

    public int semGetExternalSdCardEncryptionStatus() {
        SemSdCardEncryption semSdCardEncryption = new SemSdCardEncryption(this.mContext);
        if (semSdCardEncryption.isEncryptionSupported()) {
            return semSdCardEncryption.isStorageCardEncryptionPoliciesApplied() ? 3 : 1;
        }
        return 0;
    }

    public String semGetDeviceOwner() {
        return getDeviceOwner();
    }

    public void setCrossProfileAppToIgnored(int i, String str) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCrossProfileAppToIgnored(i, str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
