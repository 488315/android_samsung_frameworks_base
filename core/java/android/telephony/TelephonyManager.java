package android.telephony;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.PropertyInvalidatedCache;
import android.app.admin.DevicePolicyResources;
import android.app.blob.XmlTags;
import android.app.role.RoleManager;
import android.compat.Compatibility;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextParams;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.OutcomeReceiver;
import android.os.ParcelFileDescriptor;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.WorkSource;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Telephony;
import android.security.Credentials;
import android.security.keystore.KeyProperties;
import android.service.carrier.CarrierIdentifier;
import android.service.timezone.TimeZoneProviderService;
import android.sysprop.TelephonyProperties;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.data.ApnSetting;
import android.telephony.data.NetworkSlicingConfig;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.gba.UaSecurityProtocolIdentifier;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.satellite.SemSatelliteState;
import android.text.TextUtils;
import android.util.Log;
import android.util.NtpTrustedTime;
import android.util.Pair;

import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.os.BackgroundThread;
import com.android.internal.telephony.CellNetworkScanResult;
import com.android.internal.telephony.DctConstants;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.IBooleanConsumer;
import com.android.internal.telephony.ICallForwardingInfoCallback;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.INumberVerificationCallback;
import com.android.internal.telephony.IOns;
import com.android.internal.telephony.IPhoneSubInfo;
import com.android.internal.telephony.ISemPhoneSubInfo;
import com.android.internal.telephony.ISemTelephony;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import com.android.internal.telephony.ISms;
import com.android.internal.telephony.ISub;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.IUpdateAvailableNetworksCallback;
import com.android.internal.telephony.IccLogicalChannelRequest;
import com.android.internal.telephony.OperatorInfo;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.RILConstants;
import com.android.internal.telephony.SemGsmAlphabet;
import com.android.internal.telephony.SemTelephonyUtils;
import com.android.internal.telephony.SmsApplication;
import com.android.internal.telephony.TelephonyFeatures;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.server.telecom.flags.Flags;

import com.samsung.android.common.AsPackageName;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.telephony.SemNetworkQualityInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TelephonyManager {

    @SystemApi
    public static final String ACTION_ANOMALY_REPORTED =
            "android.telephony.action.ANOMALY_REPORTED";

    public static final String ACTION_CALL_DISCONNECT_CAUSE_CHANGED =
            "android.intent.action.CALL_DISCONNECT_CAUSE";
    public static final String ACTION_CARRIER_MESSAGING_CLIENT_SERVICE =
            "android.telephony.action.CARRIER_MESSAGING_CLIENT_SERVICE";
    public static final String ACTION_CARRIER_SIGNAL_DEFAULT_NETWORK_AVAILABLE =
            "android.telephony.action.CARRIER_SIGNAL_DEFAULT_NETWORK_AVAILABLE";
    public static final String ACTION_CARRIER_SIGNAL_PCO_VALUE =
            "android.telephony.action.CARRIER_SIGNAL_PCO_VALUE";
    public static final String ACTION_CARRIER_SIGNAL_REDIRECTED =
            "android.telephony.action.CARRIER_SIGNAL_REDIRECTED";
    public static final String ACTION_CARRIER_SIGNAL_REQUEST_NETWORK_FAILED =
            "android.telephony.action.CARRIER_SIGNAL_REQUEST_NETWORK_FAILED";
    public static final String ACTION_CARRIER_SIGNAL_RESET =
            "android.telephony.action.CARRIER_SIGNAL_RESET";
    public static final String ACTION_CONFIGURE_VOICEMAIL =
            "android.telephony.action.CONFIGURE_VOICEMAIL";
    public static final String ACTION_DATA_STALL_DETECTED =
            "android.intent.action.DATA_STALL_DETECTED";

    @SystemApi
    public static final String ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED =
            "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED";

    @SystemApi
    public static final String ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED =
            "android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED";

    @SystemApi
    public static final String ACTION_EMERGENCY_ASSISTANCE =
            "android.telephony.action.EMERGENCY_ASSISTANCE";

    @SystemApi
    public static final String ACTION_EMERGENCY_CALLBACK_MODE_CHANGED =
            "android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED";

    @SystemApi
    public static final String ACTION_EMERGENCY_CALL_STATE_CHANGED =
            "android.intent.action.EMERGENCY_CALL_STATE_CHANGED";

    public static final String ACTION_MULTI_SIM_CONFIG_CHANGED =
            "android.telephony.action.MULTI_SIM_CONFIG_CHANGED";
    public static final String ACTION_NETWORK_COUNTRY_CHANGED =
            "android.telephony.action.NETWORK_COUNTRY_CHANGED";
    public static final String ACTION_PHONE_STATE_CHANGED = "android.intent.action.PHONE_STATE";
    public static final String ACTION_PRIMARY_SUBSCRIPTION_LIST_CHANGED =
            "android.telephony.action.PRIMARY_SUBSCRIPTION_LIST_CHANGED";

    @SystemApi
    public static final String ACTION_REQUEST_OMADM_CONFIGURATION_UPDATE =
            "com.android.omadm.service.CONFIGURATION_UPDATE";

    public static final String ACTION_RESET_MOBILE_NETWORK_SETTINGS =
            "android.telephony.action.RESET_MOBILE_NETWORK_SETTINGS";
    public static final String ACTION_RESPOND_VIA_MESSAGE =
            "android.intent.action.RESPOND_VIA_MESSAGE";
    public static final String ACTION_SECRET_CODE = "android.telephony.action.SECRET_CODE";
    public static final String ACTION_SERVICE_PROVIDERS_UPDATED =
            "android.telephony.action.SERVICE_PROVIDERS_UPDATED";

    @SystemApi
    public static final String ACTION_SHOW_NOTICE_ECM_BLOCK_OTHERS =
            "android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS";

    public static final String ACTION_SHOW_VOICEMAIL_NOTIFICATION =
            "android.telephony.action.SHOW_VOICEMAIL_NOTIFICATION";

    @SystemApi
    public static final String ACTION_SIM_APPLICATION_STATE_CHANGED =
            "android.telephony.action.SIM_APPLICATION_STATE_CHANGED";

    @SystemApi
    public static final String ACTION_SIM_CARD_STATE_CHANGED =
            "android.telephony.action.SIM_CARD_STATE_CHANGED";

    @SystemApi
    public static final String ACTION_SIM_SLOT_STATUS_CHANGED =
            "android.telephony.action.SIM_SLOT_STATUS_CHANGED";

    public static final String ACTION_SUBSCRIPTION_CARRIER_IDENTITY_CHANGED =
            "android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED";
    public static final String ACTION_SUBSCRIPTION_SPECIFIC_CARRIER_IDENTITY_CHANGED =
            "android.telephony.action.SUBSCRIPTION_SPECIFIC_CARRIER_IDENTITY_CHANGED";
    public static final int ALLOWED_NETWORK_TYPES_REASON_CARRIER = 2;

    @SystemApi public static final int ALLOWED_NETWORK_TYPES_REASON_ENABLE_2G = 3;

    @SystemApi public static final int ALLOWED_NETWORK_TYPES_REASON_POWER = 1;
    public static final int ALLOWED_NETWORK_TYPES_REASON_USER = 0;
    public static final int APPTYPE_CSIM = 4;
    public static final int APPTYPE_ISIM = 5;
    public static final int APPTYPE_RUIM = 3;
    public static final int APPTYPE_SIM = 1;
    public static final int APPTYPE_UNKNOWN = 0;
    public static final int APPTYPE_USIM = 2;
    public static final int ASSISTED_DIAL_FROM_CONTACT_LIST = 1;
    public static final int ASSISTED_DIAL_FROM_DIAL_PAD = 2;
    public static final int ASSISTED_DIAL_FROM_NONE = 0;
    public static final int AUTHTYPE_EAP_AKA = 129;
    public static final int AUTHTYPE_EAP_SIM = 128;
    public static final int AUTHTYPE_GBA_BOOTSTRAP = 132;
    public static final int AUTHTYPE_GBA_NAF_KEY_EXTERNAL = 133;
    public static final String CACHE_KEY_PHONE_ACCOUNT_TO_SUBID =
            "cache_key.telephony.phone_account_to_subid";
    private static final int CACHE_MAX_SIZE = 4;
    private static final long CALLBACK_ON_MORE_ERROR_CODE_CHANGE = 130595455;
    public static final int CALL_COMPOSER_STATUS_BUSINESS_ONLY = 2;
    public static final int CALL_COMPOSER_STATUS_OFF = 0;
    public static final int CALL_COMPOSER_STATUS_ON = 1;
    public static final int CALL_STATE_IDLE = 0;
    public static final int CALL_STATE_OFFHOOK = 2;
    public static final int CALL_STATE_RINGING = 1;

    @SystemApi public static final int CALL_WAITING_STATUS_DISABLED = 2;

    @SystemApi public static final int CALL_WAITING_STATUS_ENABLED = 1;

    @SystemApi public static final int CALL_WAITING_STATUS_FDN_CHECK_FAILURE = 5;

    @SystemApi public static final int CALL_WAITING_STATUS_NOT_SUPPORTED = 4;

    @SystemApi public static final int CALL_WAITING_STATUS_UNKNOWN_ERROR = 3;

    @SystemApi
    public static final String CAPABILITY_NR_DUAL_CONNECTIVITY_CONFIGURATION_AVAILABLE =
            "CAPABILITY_NR_DUAL_CONNECTIVITY_CONFIGURATION_AVAILABLE";

    public static final String CAPABILITY_PHYSICAL_CHANNEL_CONFIG_1_6_SUPPORTED =
            "CAPABILITY_PHYSICAL_CHANNEL_CONFIG_1_6_SUPPORTED";

    @SystemApi
    public static final String CAPABILITY_SECONDARY_LINK_BANDWIDTH_VISIBLE =
            "CAPABILITY_SECONDARY_LINK_BANDWIDTH_VISIBLE";

    public static final String CAPABILITY_SIM_PHONEBOOK_IN_MODEM =
            "CAPABILITY_SIM_PHONEBOOK_IN_MODEM";
    public static final String CAPABILITY_SLICING_CONFIG_SUPPORTED =
            "CAPABILITY_SLICING_CONFIG_SUPPORTED";

    @SystemApi
    public static final String CAPABILITY_THERMAL_MITIGATION_DATA_THROTTLING =
            "CAPABILITY_THERMAL_MITIGATION_DATA_THROTTLING";

    @SystemApi
    public static final String CAPABILITY_USES_ALLOWED_NETWORK_TYPES_BITMASK =
            "CAPABILITY_USES_ALLOWED_NETWORK_TYPES_BITMASK";

    public static final int CARD_POWER_DOWN = 0;
    public static final int CARD_POWER_UP = 1;
    public static final int CARD_POWER_UP_PASS_THROUGH = 2;

    @SystemApi public static final int CARRIER_PRIVILEGE_STATUS_ERROR_LOADING_RULES = -2;

    @SystemApi public static final int CARRIER_PRIVILEGE_STATUS_HAS_ACCESS = 1;

    @SystemApi public static final int CARRIER_PRIVILEGE_STATUS_NO_ACCESS = 0;

    @SystemApi public static final int CARRIER_PRIVILEGE_STATUS_RULES_NOT_LOADED = -1;
    public static final int CARRIER_RESTRICTION_STATUS_NOT_RESTRICTED = 1;
    public static final int CARRIER_RESTRICTION_STATUS_RESTRICTED = 2;
    public static final int CARRIER_RESTRICTION_STATUS_RESTRICTED_TO_CALLER = 3;
    public static final int CARRIER_RESTRICTION_STATUS_UNKNOWN = 0;
    public static final int CDMA_ROAMING_MODE_AFFILIATED = 1;
    public static final int CDMA_ROAMING_MODE_ANY = 2;
    public static final int CDMA_ROAMING_MODE_HOME = 0;
    public static final int CDMA_ROAMING_MODE_RADIO_DEFAULT = -1;

    @SystemApi public static final int CDMA_SUBSCRIPTION_NV = 1;

    @SystemApi public static final int CDMA_SUBSCRIPTION_RUIM_SIM = 0;

    @SystemApi public static final int CDMA_SUBSCRIPTION_UNKNOWN = -1;

    @SystemApi public static final int CELL_BROADCAST_RESULT_FAIL_ACTIVATION = 3;

    @SystemApi public static final int CELL_BROADCAST_RESULT_FAIL_CONFIG = 2;

    @SystemApi public static final int CELL_BROADCAST_RESULT_SUCCESS = 0;

    @SystemApi public static final int CELL_BROADCAST_RESULT_UNKNOWN = -1;

    @SystemApi public static final int CELL_BROADCAST_RESULT_UNSUPPORTED = 1;
    public static final int CHANGE_ICC_LOCK_SUCCESS = Integer.MAX_VALUE;
    public static final int DATA_ACTIVITY_DORMANT = 4;
    public static final int DATA_ACTIVITY_IN = 1;
    public static final int DATA_ACTIVITY_INOUT = 3;
    public static final int DATA_ACTIVITY_NONE = 0;
    public static final int DATA_ACTIVITY_OUT = 2;
    public static final int DATA_CONNECTED = 2;
    public static final int DATA_CONNECTING = 1;
    public static final int DATA_DISCONNECTED = 0;
    public static final int DATA_DISCONNECTING = 4;
    public static final int DATA_ENABLED_REASON_CARRIER = 2;
    public static final int DATA_ENABLED_REASON_NETWORK_SCAN = 5;
    public static final int DATA_ENABLED_REASON_OVERRIDE = 4;
    public static final int DATA_ENABLED_REASON_POLICY = 1;
    public static final int DATA_ENABLED_REASON_THERMAL = 3;
    public static final int DATA_ENABLED_REASON_UNKNOWN = -1;
    public static final int DATA_ENABLED_REASON_USER = 0;
    public static final int DATA_HANDOVER_IN_PROGRESS = 5;
    public static final int DATA_SUSPENDED = 3;
    public static final int DATA_UNKNOWN = -1;
    public static final int DEFAULT_PORT_INDEX = 0;
    public static final boolean EMERGENCY_ASSISTANCE_ENABLED = true;
    public static final int EMERGENCY_CALLBACK_MODE_CALL = 1;
    public static final int EMERGENCY_CALLBACK_MODE_SMS = 2;
    public static final long ENABLE_FEATURE_MAPPING = 297989574;

    @SystemApi public static final int ENABLE_NR_DUAL_CONNECTIVITY_INVALID_STATE = 4;

    @SystemApi public static final int ENABLE_NR_DUAL_CONNECTIVITY_NOT_SUPPORTED = 1;

    @SystemApi public static final int ENABLE_NR_DUAL_CONNECTIVITY_RADIO_ERROR = 3;

    @SystemApi public static final int ENABLE_NR_DUAL_CONNECTIVITY_RADIO_NOT_AVAILABLE = 2;

    @SystemApi public static final int ENABLE_NR_DUAL_CONNECTIVITY_SUCCESS = 0;
    public static final int ENABLE_VONR_RADIO_ERROR = 3;
    public static final int ENABLE_VONR_RADIO_INVALID_STATE = 4;
    public static final int ENABLE_VONR_RADIO_NOT_AVAILABLE = 2;
    public static final int ENABLE_VONR_REQUEST_NOT_SUPPORTED = 5;
    public static final int ENABLE_VONR_SUCCESS = 0;
    public static final int ERI_CUSTOM = 3;
    public static final int ERI_FLASH = 2;
    public static final int ERI_ICON_MODE_FLASH = 1;
    public static final int ERI_ICON_MODE_NORMAL = 0;
    public static final int ERI_OFF = 1;
    public static final int ERI_ON = 0;
    public static final String EVENT_CALL_FORWARDED =
            "android.telephony.event.EVENT_CALL_FORWARDED";
    public static final String EVENT_DISPLAY_EMERGENCY_MESSAGE =
            "android.telephony.event.DISPLAY_EMERGENCY_MESSAGE";
    public static final String EVENT_DOWNGRADE_DATA_DISABLED =
            "android.telephony.event.EVENT_DOWNGRADE_DATA_DISABLED";
    public static final String EVENT_DOWNGRADE_DATA_LIMIT_REACHED =
            "android.telephony.event.EVENT_DOWNGRADE_DATA_LIMIT_REACHED";
    public static final String EVENT_HANDOVER_TO_WIFI_FAILED =
            "android.telephony.event.EVENT_HANDOVER_TO_WIFI_FAILED";
    public static final String EVENT_HANDOVER_VIDEO_FROM_LTE_TO_WIFI =
            "android.telephony.event.EVENT_HANDOVER_VIDEO_FROM_LTE_TO_WIFI";
    public static final String EVENT_HANDOVER_VIDEO_FROM_WIFI_TO_LTE =
            "android.telephony.event.EVENT_HANDOVER_VIDEO_FROM_WIFI_TO_LTE";
    public static final String EVENT_NOTIFY_INTERNATIONAL_CALL_ON_WFC =
            "android.telephony.event.EVENT_NOTIFY_INTERNATIONAL_CALL_ON_WFC";
    public static final String EVENT_SUPPLEMENTARY_SERVICE_NOTIFICATION =
            "android.telephony.event.EVENT_SUPPLEMENTARY_SERVICE_NOTIFICATION";
    public static final String EXCEPTION_RESULT_KEY = "exception";
    public static final String EXTRA_ACTIVE_SIM_SUPPORTED_COUNT =
            "android.telephony.extra.ACTIVE_SIM_SUPPORTED_COUNT";

    @SystemApi
    public static final String EXTRA_ANOMALY_DESCRIPTION =
            "android.telephony.extra.ANOMALY_DESCRIPTION";

    @SystemApi public static final String EXTRA_ANOMALY_ID = "android.telephony.extra.ANOMALY_ID";
    public static final String EXTRA_APN_PROTOCOL = "android.telephony.extra.APN_PROTOCOL";
    public static final String EXTRA_APN_TYPE = "android.telephony.extra.APN_TYPE";
    public static final String EXTRA_CALL_VOICEMAIL_INTENT =
            "android.telephony.extra.CALL_VOICEMAIL_INTENT";
    public static final String EXTRA_CARRIER_ID = "android.telephony.extra.CARRIER_ID";
    public static final String EXTRA_CARRIER_NAME = "android.telephony.extra.CARRIER_NAME";
    public static final String EXTRA_DATA_FAIL_CAUSE = "android.telephony.extra.DATA_FAIL_CAUSE";
    public static final String EXTRA_DATA_SPN = "android.telephony.extra.DATA_SPN";
    public static final String EXTRA_DEFAULT_NETWORK_AVAILABLE =
            "android.telephony.extra.DEFAULT_NETWORK_AVAILABLE";
    public static final String EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE =
            "android.telephony.extra.DEFAULT_SUBSCRIPTION_SELECT_TYPE";
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_ALL = 4;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_DATA = 1;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_DISMISS = 5;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_NONE = 0;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_SMS = 3;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_VOICE = 2;

    @Deprecated public static final String EXTRA_DISCONNECT_CAUSE = "disconnect_cause";
    public static final String EXTRA_EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE =
            "android.telephony.extra.EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE";
    public static final String EXTRA_EMERGENCY_CALL_TO_SATELLITE_LAUNCH_INTENT =
            "android.telephony.extra.EMERGENCY_CALL_TO_SATELLITE_LAUNCH_INTENT";
    public static final String EXTRA_HIDE_PUBLIC_SETTINGS =
            "android.telephony.extra.HIDE_PUBLIC_SETTINGS";

    @Deprecated public static final String EXTRA_INCOMING_NUMBER = "incoming_number";
    public static final String EXTRA_IS_REFRESH = "android.telephony.extra.IS_REFRESH";
    public static final String EXTRA_LAST_KNOWN_NETWORK_COUNTRY =
            "android.telephony.extra.LAST_KNOWN_NETWORK_COUNTRY";
    public static final String EXTRA_LAUNCH_VOICEMAIL_SETTINGS_INTENT =
            "android.telephony.extra.LAUNCH_VOICEMAIL_SETTINGS_INTENT";
    public static final String EXTRA_NETWORK_COUNTRY = "android.telephony.extra.NETWORK_COUNTRY";
    public static final String EXTRA_NOTIFICATION_CODE =
            "android.telephony.extra.NOTIFICATION_CODE";
    public static final String EXTRA_NOTIFICATION_COUNT =
            "android.telephony.extra.NOTIFICATION_COUNT";
    public static final String EXTRA_NOTIFICATION_MESSAGE =
            "android.telephony.extra.NOTIFICATION_MESSAGE";
    public static final String EXTRA_NOTIFICATION_TYPE =
            "android.telephony.extra.NOTIFICATION_TYPE";
    public static final String EXTRA_PCO_ID = "android.telephony.extra.PCO_ID";
    public static final String EXTRA_PCO_VALUE = "android.telephony.extra.PCO_VALUE";
    public static final String EXTRA_PHONE_ACCOUNT_HANDLE =
            "android.telephony.extra.PHONE_ACCOUNT_HANDLE";

    @SystemApi
    public static final String EXTRA_PHONE_IN_ECM_STATE =
            "android.telephony.extra.PHONE_IN_ECM_STATE";

    @SystemApi
    public static final String EXTRA_PHONE_IN_EMERGENCY_CALL =
            "android.telephony.extra.PHONE_IN_EMERGENCY_CALL";

    public static final String EXTRA_PLMN = "android.telephony.extra.PLMN";
    public static final String EXTRA_PRECISE_DISCONNECT_CAUSE = "precise_disconnect_cause";
    public static final String EXTRA_RECOVERY_ACTION = "recoveryAction";
    public static final String EXTRA_REDIRECTION_URL = "android.telephony.extra.REDIRECTION_URL";
    public static final String EXTRA_SHOW_PLMN = "android.telephony.extra.SHOW_PLMN";
    public static final String EXTRA_SHOW_SPN = "android.telephony.extra.SHOW_SPN";
    public static final String EXTRA_SIM_COMBINATION_NAMES =
            "android.telephony.extra.SIM_COMBINATION_NAMES";
    public static final String EXTRA_SIM_COMBINATION_WARNING_TYPE =
            "android.telephony.extra.SIM_COMBINATION_WARNING_TYPE";
    public static final int EXTRA_SIM_COMBINATION_WARNING_TYPE_DUAL_CDMA = 1;
    public static final int EXTRA_SIM_COMBINATION_WARNING_TYPE_NONE = 0;

    @SystemApi public static final String EXTRA_SIM_STATE = "android.telephony.extra.SIM_STATE";
    public static final String EXTRA_SPECIFIC_CARRIER_ID =
            "android.telephony.extra.SPECIFIC_CARRIER_ID";
    public static final String EXTRA_SPECIFIC_CARRIER_NAME =
            "android.telephony.extra.SPECIFIC_CARRIER_NAME";
    public static final String EXTRA_SPN = "android.telephony.extra.SPN";
    public static final String EXTRA_STATE = "state";
    public static final String EXTRA_SUBSCRIPTION_ID = "android.telephony.extra.SUBSCRIPTION_ID";

    @SystemApi
    public static final String EXTRA_VISUAL_VOICEMAIL_ENABLED_BY_USER_BOOL =
            "android.telephony.extra.VISUAL_VOICEMAIL_ENABLED_BY_USER_BOOL";

    public static final String EXTRA_VOICEMAIL_NUMBER = "android.telephony.extra.VOICEMAIL_NUMBER";

    @SystemApi
    public static final String EXTRA_VOICEMAIL_SCRAMBLED_PIN_STRING =
            "android.telephony.extra.VOICEMAIL_SCRAMBLED_PIN_STRING";

    @SystemApi public static final int GBA_FAILURE_REASON_FEATURE_NOT_READY = 2;

    @SystemApi public static final int GBA_FAILURE_REASON_FEATURE_NOT_SUPPORTED = 1;

    @SystemApi public static final int GBA_FAILURE_REASON_INCORRECT_NAF_ID = 4;

    @SystemApi public static final int GBA_FAILURE_REASON_NETWORK_FAILURE = 3;

    @SystemApi public static final int GBA_FAILURE_REASON_SECURITY_PROTOCOL_NOT_SUPPORTED = 5;

    @SystemApi public static final int GBA_FAILURE_REASON_UNKNOWN = 0;
    private static final long GET_DATA_STATE_R_VERSION = 148534348;
    public static final int HAL_SERVICE_DATA = 1;
    public static final int HAL_SERVICE_IMS = 7;
    public static final int HAL_SERVICE_MESSAGING = 2;
    public static final int HAL_SERVICE_MODEM = 3;
    public static final int HAL_SERVICE_NETWORK = 4;
    public static final int HAL_SERVICE_RADIO = 0;
    public static final int HAL_SERVICE_SIM = 5;
    public static final int HAL_SERVICE_VOICE = 6;
    public static final int INCLUDE_LOCATION_DATA_COARSE = 1;
    public static final int INCLUDE_LOCATION_DATA_FINE = 2;
    public static final int INCLUDE_LOCATION_DATA_NONE = 0;
    public static final int INDICATION_FILTER_DATA_CALL_DORMANCY_CHANGED = 4;
    public static final int INDICATION_FILTER_FULL_NETWORK_STATE = 2;
    public static final int INDICATION_FILTER_LINK_CAPACITY_ESTIMATE = 8;
    public static final int INDICATION_FILTER_PHYSICAL_CHANNEL_CONFIG = 16;
    public static final int INDICATION_FILTER_SIGNAL_STRENGTH = 1;

    @SystemApi public static final int INVALID_EMERGENCY_NUMBER_DB_VERSION = -1;
    public static final int INVALID_PORT_INDEX = -1;
    public static final String KEY_CALL_COMPOSER_PICTURE_HANDLE = "call_composer_picture_handle";
    public static final String KEY_SLICING_CONFIG_HANDLE = "slicing_config_handle";

    @SystemApi public static final int KEY_TYPE_EPDG = 1;

    @SystemApi public static final int KEY_TYPE_WLAN = 2;
    private static final int MAXIMUM_CALL_COMPOSER_PICTURE_SIZE = 80000;
    private static final long MAX_NUMBER_VERIFICATION_TIMEOUT_MILLIS = 60000;
    public static final String METADATA_HIDE_VOICEMAIL_SETTINGS_MENU =
            "android.telephony.HIDE_VOICEMAIL_SETTINGS_MENU";

    @SystemApi public static final int MOBILE_DATA_POLICY_AUTO_DATA_SWITCH = 3;

    @SystemApi public static final int MOBILE_DATA_POLICY_DATA_ON_NON_DEFAULT_DURING_VOICE_CALL = 1;

    @SystemApi public static final int MOBILE_DATA_POLICY_MMS_ALWAYS_ALLOWED = 2;
    public static final String MODEM_ACTIVITY_RESULT_KEY = "controller_activity";
    public static final int MULTISIM_ALLOWED = 0;
    public static final int MULTISIM_NOT_SUPPORTED_BY_CARRIER = 2;
    public static final int MULTISIM_NOT_SUPPORTED_BY_HARDWARE = 1;
    public static final long NETWORK_CLASS_BITMASK_2G = 32843;
    public static final long NETWORK_CLASS_BITMASK_3G = 93108;
    public static final long NETWORK_CLASS_BITMASK_4G = 397312;
    public static final long NETWORK_CLASS_BITMASK_5G = 524288;
    public static final int NETWORK_MODE_CDMA_EVDO = 4;
    public static final int NETWORK_MODE_CDMA_NO_EVDO = 5;
    public static final int NETWORK_MODE_EVDO_NO_CDMA = 6;
    public static final int NETWORK_MODE_GLOBAL = 7;
    public static final int NETWORK_MODE_GSM_ONLY = 1;
    public static final int NETWORK_MODE_GSM_UMTS = 3;
    public static final int NETWORK_MODE_LTE_CDMA_EVDO = 8;
    public static final int NETWORK_MODE_LTE_CDMA_EVDO_GSM_WCDMA = 10;
    public static final int NETWORK_MODE_LTE_GSM_WCDMA = 9;
    public static final int NETWORK_MODE_LTE_ONLY = 11;
    public static final int NETWORK_MODE_LTE_TDSCDMA = 15;
    public static final int NETWORK_MODE_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 22;
    public static final int NETWORK_MODE_LTE_TDSCDMA_GSM = 17;
    public static final int NETWORK_MODE_LTE_TDSCDMA_GSM_WCDMA = 20;
    public static final int NETWORK_MODE_LTE_TDSCDMA_WCDMA = 19;
    public static final int NETWORK_MODE_LTE_WCDMA = 12;
    public static final int NETWORK_MODE_NR_LTE = 24;
    public static final int NETWORK_MODE_NR_LTE_CDMA_EVDO = 25;
    public static final int NETWORK_MODE_NR_LTE_CDMA_EVDO_GSM_WCDMA = 27;
    public static final int NETWORK_MODE_NR_LTE_GSM_WCDMA = 26;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA = 29;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 33;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_GSM = 30;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_GSM_WCDMA = 32;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_WCDMA = 31;
    public static final int NETWORK_MODE_NR_LTE_WCDMA = 28;
    public static final int NETWORK_MODE_NR_ONLY = 23;
    public static final int NETWORK_MODE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 21;
    public static final int NETWORK_MODE_TDSCDMA_GSM = 16;
    public static final int NETWORK_MODE_TDSCDMA_GSM_WCDMA = 18;
    public static final int NETWORK_MODE_TDSCDMA_ONLY = 13;
    public static final int NETWORK_MODE_TDSCDMA_WCDMA = 14;
    public static final int NETWORK_MODE_WCDMA_ONLY = 2;
    public static final int NETWORK_MODE_WCDMA_PREF = 0;
    public static final int NETWORK_SELECTION_MODE_AUTO = 1;
    public static final int NETWORK_SELECTION_MODE_MANUAL = 2;
    public static final int NETWORK_SELECTION_MODE_UNKNOWN = 0;
    public static final long NETWORK_STANDARDS_FAMILY_BITMASK_3GPP = 906119;
    public static final long NETWORK_STANDARDS_FAMILY_BITMASK_3GPP2 = 10360;
    public static final int NETWORK_TYPE_1xRTT = 7;
    public static final long NETWORK_TYPE_BITMASK_1xRTT = 64;
    public static final long NETWORK_TYPE_BITMASK_CDMA = 8;
    public static final long NETWORK_TYPE_BITMASK_EDGE = 2;
    public static final long NETWORK_TYPE_BITMASK_EHRPD = 8192;
    public static final long NETWORK_TYPE_BITMASK_EVDO_0 = 16;
    public static final long NETWORK_TYPE_BITMASK_EVDO_A = 32;
    public static final long NETWORK_TYPE_BITMASK_EVDO_B = 2048;
    public static final long NETWORK_TYPE_BITMASK_GPRS = 1;
    public static final long NETWORK_TYPE_BITMASK_GSM = 32768;
    public static final long NETWORK_TYPE_BITMASK_HSDPA = 128;
    public static final long NETWORK_TYPE_BITMASK_HSPA = 512;
    public static final long NETWORK_TYPE_BITMASK_HSPAP = 16384;
    public static final long NETWORK_TYPE_BITMASK_HSUPA = 256;
    public static final long NETWORK_TYPE_BITMASK_IDEN = 1024;
    public static final long NETWORK_TYPE_BITMASK_IWLAN = 131072;
    public static final long NETWORK_TYPE_BITMASK_LTE = 4096;

    @Deprecated public static final long NETWORK_TYPE_BITMASK_LTE_CA = 262144;
    public static final long NETWORK_TYPE_BITMASK_NR = 524288;
    public static final long NETWORK_TYPE_BITMASK_TD_SCDMA = 65536;
    public static final long NETWORK_TYPE_BITMASK_UMTS = 4;
    public static final long NETWORK_TYPE_BITMASK_UNKNOWN = 0;
    public static final int NETWORK_TYPE_CDMA = 4;
    public static final int NETWORK_TYPE_EDGE = 2;
    public static final int NETWORK_TYPE_EHRPD = 14;
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    public static final int NETWORK_TYPE_EVDO_A = 6;
    public static final int NETWORK_TYPE_EVDO_B = 12;
    public static final int NETWORK_TYPE_GPRS = 1;
    public static final int NETWORK_TYPE_GSM = 16;
    public static final int NETWORK_TYPE_HSDPA = 8;
    public static final int NETWORK_TYPE_HSPA = 10;
    public static final int NETWORK_TYPE_HSPAP = 15;
    public static final int NETWORK_TYPE_HSUPA = 9;

    @Deprecated public static final int NETWORK_TYPE_IDEN = 11;
    public static final int NETWORK_TYPE_IWLAN = 18;
    public static final int NETWORK_TYPE_LTE = 13;
    public static final int NETWORK_TYPE_LTE_CA = 19;
    public static final int NETWORK_TYPE_NR = 20;
    public static final int NETWORK_TYPE_TD_SCDMA = 17;
    public static final int NETWORK_TYPE_UMTS = 3;
    public static final int NETWORK_TYPE_UNKNOWN = 0;

    @SystemApi public static final int NR_DUAL_CONNECTIVITY_DISABLE = 2;

    @SystemApi public static final int NR_DUAL_CONNECTIVITY_DISABLE_IMMEDIATE = 3;

    @SystemApi public static final int NR_DUAL_CONNECTIVITY_ENABLE = 1;
    private static final long NULL_TELEPHONY_THROW_NO_CB = 182185642;
    public static final int OTASP_NEEDED = 2;
    public static final int OTASP_NOT_NEEDED = 3;
    public static final int OTASP_SIM_UNPROVISIONED = 5;
    public static final int OTASP_UNINITIALIZED = 0;
    public static final int OTASP_UNKNOWN = 1;
    public static final String PHONE_PROCESS_NAME = "com.android.phone";
    public static final int PHONE_TYPE_CDMA = 2;
    public static final int PHONE_TYPE_GSM = 1;
    public static final int PHONE_TYPE_IMS = 5;
    public static final int PHONE_TYPE_NONE = 0;
    public static final int PHONE_TYPE_SIP = 3;
    public static final int PHONE_TYPE_THIRD_PARTY = 4;
    public static final int PREMIUM_CAPABILITY_PRIORITIZE_LATENCY = 34;

    @SystemApi public static final int PREPARE_UNATTENDED_REBOOT_ERROR = 2;

    @SystemApi public static final int PREPARE_UNATTENDED_REBOOT_PIN_REQUIRED = 1;

    @SystemApi public static final int PREPARE_UNATTENDED_REBOOT_SUCCESS = 0;
    public static final String PROPERTY_ENABLE_NULL_CIPHER_TOGGLE = "enable_null_cipher_toggle";
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_ALREADY_IN_PROGRESS = 4;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_ALREADY_PURCHASED = 3;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_CARRIER_DISABLED = 7;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_CARRIER_ERROR = 8;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_ENTITLEMENT_CHECK_FAILED = 13;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_FEATURE_NOT_SUPPORTED = 10;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_NETWORK_NOT_AVAILABLE = 12;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_NOT_DEFAULT_DATA_SUBSCRIPTION = 14;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_NOT_FOREGROUND = 5;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_PENDING_NETWORK_SETUP = 15;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_REQUEST_FAILED = 11;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_SUCCESS = 1;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_THROTTLED = 2;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_TIMEOUT = 9;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_USER_CANCELED = 6;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_USER_DISABLED = 16;

    @SystemApi public static final int RADIO_POWER_OFF = 0;

    @SystemApi public static final int RADIO_POWER_ON = 1;

    @SystemApi public static final int RADIO_POWER_REASON_CARRIER = 2;

    @SystemApi public static final int RADIO_POWER_REASON_NEARBY_DEVICE = 3;

    @SystemApi public static final int RADIO_POWER_REASON_THERMAL = 1;

    @SystemApi public static final int RADIO_POWER_REASON_USER = 0;

    @SystemApi public static final int RADIO_POWER_UNAVAILABLE = 2;
    public static final String SEM_CALL_EXTRA_CALL_FORWARDING_NUMBER =
            "com.samsung.telephony.extra.CALL_FORWARDING_REDIRECT_NUMBER";
    public static final String SEM_CALL_EXTRA_CALL_FORWARDING_NUMBER_PRESENTATION =
            "com.samsung.telephony.extra.CALL_FORWARDING_PRESENTATION";
    public static final String SEM_CALL_EXTRA_CMC_CALL_STATE =
            "com.samsung.telephony.extra.CMC_CALL_STATE";
    public static final String SEM_CALL_EXTRA_CMC_EXTERNAL_CALL =
            "com.samsung.telephony.extra.CMC_EXTERNAL_CALL";
    public static final String SEM_CALL_EXTRA_CMC_PRIMARY_DEVICE_CALL_CONNECT_TIME =
            "com.samsung.telephony.extra.CMC_PD_CALL_CONNECT_TIME";
    public static final String SEM_CALL_EXTRA_CMC_PRIMARY_DEVICE_CALL_TYPE =
            "com.samsung.telephony.extra.CMC_PD_CALL_TYPE";
    public static final String SEM_CALL_EXTRA_CMC_PULLABLE =
            "com.samsung.telephony.extra.CMC_PULLABLE";
    public static final String SEM_CALL_EXTRA_DUAL_NUMBER =
            "com.samsung.telephony.extra.DUAL_NUMBER";
    public static final String SEM_CALL_EXTRA_INCOMING_CONFERENCE_CALL =
            "com.samsung.telephony.extra.MT_CONFERENCE";
    public static final String SEM_CALL_EXTRA_IS_TWO_PHONE_MODE =
            "com.samsung.telephony.extra.IS_TWO_PHONE_MODE";
    public static final String SEM_CALL_EXTRA_PHOTO_RING_SERVICE_PARAMETER =
            "com.samsung.telephony.extra.PHOTO_RING_AVAILABLE";
    public static final String SEM_CALL_EXTRA_SATELLITE_CALL =
            "com.samsung.telephony.extra.SATELLITE_CALL";
    public static final String SEM_CALL_EXTRA_SEM_CMC_TYPE = "com.samsung.telephony.extra.CMC_TYPE";
    public static final String SEM_CALL_EXTRA_SHOW_ME_SERVICE_PARAMETER =
            "com.samsung.telephony.extra.ALERT_INFO";
    public static final String SEM_CALL_EXTRA_START_CALL_WITH_PREFERRED_DOMAIN =
            "com.samsung.telephony.extra.START_CALL_WITH_DOMAIN";
    public static final String SEM_CALL_EXTRA_TELECOM_CALL_ID =
            "com.samsung.telephony.extra.ims.IMSDC_TELECOM_CALL_ID";
    public static final String SEM_CALL_EXTRA_VALIDATION_STATE =
            "com.samsung.telephony.extra.ims.VERSTAT";
    public static final String SEM_CALL_EXTRA_VCS_ACTION =
            "com.samsung.telephony.extra.ims.VCS_ACTION";
    public static final String SEM_CALL_EXTRA_VCS_DURATION =
            "com.samsung.telephony.extra.ims.VCS_DURATION";
    public static final String SEM_CALL_EXTRA_VCS_SCREEN_SHARING_ONOFF =
            "com.samsung.telephony.extra.ims.VCS_SCREENSHARING_ONOFF";
    public static final String SEM_CALL_EXTRA_VCS_SLIDING_STAGE =
            "com.samsung.telephony.extra.ims.VCS_SLIDING_STAGE";
    public static final String SEM_CALL_EXTRA_VCS_TIMESTAMP =
            "com.samsung.telephony.extra.ims.VCS_TIMESTAMP";
    public static final String SEM_CALL_EXTRA_VCS_X_POS =
            "com.samsung.telephony.extra.ims.VCS_X_POS";
    public static final String SEM_CALL_EXTRA_VCS_Y_POS =
            "com.samsung.telephony.extra.ims.VCS_Y_POS";
    public static final String SEM_CALL_EXTRA_VIDEO_CALL_RECORDING_STATE =
            "com.samsung.telephony.extra.VT_RECORDING_STATE";
    public static final String SEM_CALL_EXTRA_WAITING_TONE_PLAY_TYPE =
            "com.samsung.telephony.extra.CALL_WAITING_TONE_SIGNAL";
    public static final String SEM_CALL_FORWARDING_NUMBER_PRESENTATION_ALLOWED = "0";
    public static final String SEM_CALL_FORWARDING_NUMBER_PRESENTATION_NOT_ALLOWED = "1";
    public static final int SEM_CARRIER_PRIVILEGE_STATUS_ERROR_LOADING_RULES = -2;
    public static final int SEM_CARRIER_PRIVILEGE_STATUS_HAS_ACCESS = 1;
    public static final int SEM_CARRIER_PRIVILEGE_STATUS_NO_ACCESS = 0;
    public static final int SEM_CARRIER_PRIVILEGE_STATUS_RULES_NOT_LOADED = -1;
    public static final int SEM_CMC_PRIMARY_DEVICE_CALL_TYPE_CONFERENCE = 1;
    public static final int SEM_CMC_PRIMARY_DEVICE_CALL_TYPE_NONE = 0;
    public static final int SEM_CMC_TYPE_NONE = 0;
    public static final int SEM_CMC_TYPE_PRIMARY_DEVICE = 1;
    public static final int SEM_CMC_TYPE_SECONDARY_DEVICE = 2;
    public static final int SEM_ENABLE_VONR_RADIO_ERROR = 3;
    public static final int SEM_ENABLE_VONR_RADIO_INVALID_STATE = 4;
    public static final int SEM_ENABLE_VONR_RADIO_NOT_AVAILABLE = 2;
    public static final int SEM_ENABLE_VONR_REQUEST_NOT_SUPPORTED = 5;
    public static final int SEM_ENABLE_VONR_SUCCESS = 0;
    public static final String SEM_EVENT_CALL_CMC_PRIMARY_DEVICE_CONNECTED_TIME =
            "com.samsung.telephony.event.EVENT_CALL_CMC_PRIMARY_DEVICE_CONNECTED_TIME";
    public static final String SEM_EVENT_CALL_CMC_SECONDARY_DEVICE_PULL_COMPLETED =
            "com.samsung.telephony.event.EVENT_CALL_CMC_SECONDARY_DEVICE_PULL_COMPLETED";
    public static final String SEM_EVENT_CALL_CMC_SECONDARY_DEVICE_REQUEST_TYPE =
            "com.samsung.telephony.event.EVENT_CALL_CMC_SECONDARY_DEVICE_REQUEST_TYPE";
    public static final String SEM_EVENT_IMSDC_UPDATE_TELECOM_CALLID =
            "com.samsung.telephony.event.IMSDC_UPDATETELECOMCALLID";
    public static final String SEM_EVENT_VCS_SCREEN_SHARING =
            "com.samsung.telephony.event.VCS_SCREENSHARING";
    public static final String SEM_EVENT_VCS_TOUCH_SCREEN =
            "com.samsung.telephony.event.VCS_TOUCHSCREEN";
    public static final String SEM_EXTRA_ASSISTED_DIAL_FROM =
            "com.samsung.telephony.extra.EXTRA_ASSISTED_DIAL_FROM";
    public static final String SEM_EXTRA_CAN_TRANSFER_CALL =
            "com.samsung.telephony.extra.CAN_TRANSFER_CALL";
    public static final String SEM_EXTRA_CMC_BOUND_SESSION_ID =
            "com.samsung.telephony.extra.CMC_BOUND_SESSION_ID";
    public static final String SEM_EXTRA_CMC_CALL_SD_REQUEST_TYPE =
            "com.samsung.telephony.extra.CMC_CALL_REQUEST_TYPE";
    public static final String SEM_EXTRA_CMC_CALL_TYPE =
            "com.samsung.telephony.extra.CMC_CALL_TYPE";
    public static final String SEM_EXTRA_CMC_DEVICE_ID =
            "com.samsung.telephony.extra.CMC_DEVICE_ID";
    public static final String SEM_EXTRA_CMC_DIAL_FROM =
            "com.samsung.telephony.extra.CMC_DIAL_FROM";
    public static final String SEM_EXTRA_CMC_DIAL_TO = "com.samsung.telephony.extra.CMC_DIAL_TO";
    public static final String SEM_EXTRA_CMC_PHONE_ID = "com.samsung.telephony.extra.CMC_PHONE_ID";
    public static final String SEM_EXTRA_CMC_REPLACE_CALL_ID =
            "com.samsung.telephony.extra.CMC_REPLACE_CALL_ID";
    public static final String SEM_EXTRA_CMC_SD_CALL_MANAGE =
            "com.samsung.telephony.extra.CMC_CALL_MANAGE";
    public static final String SEM_EXTRA_CMC_SD_DTMF_KEY =
            "com.samsung.telephony.extra.CMC_CS_DTMF_KEY";
    public static final String SEM_EXTRA_CMC_SERVICE_TYPE =
            "com.samsung.telephony.extra.CMC_SERVICE_TYPE";
    public static final String SEM_EXTRA_CMC_SESSION_ID =
            "com.samsung.telephony.extra.CMC_SESSION_ID";
    public static final String SEM_EXTRA_DIAL_CONFERENCE_CALL =
            "com.samsung.telephony.extra.DIAL_CONFERENCE_CALL";
    public static final String SEM_EXTRA_FORWARDED_CALL =
            "com.samsung.telephony.extra.SEM_EXTRA_FORWARDED_CALL";
    public static final String SEM_EXTRA_SKT_CONFERENCE_CALL_SUPPORT =
            "com.samsung.telephony.extra.SKT_CONFERENCE_CALL_SUPPORT";
    public static final String SEM_EXTRA_START_CALL_WITH_EMERGENCY_SERVICE_CATEGORY =
            "com.samsung.telephony.extra.START_CALL_WITH_EMERGENCY_SERVICE_CATEGORY";
    public static final String SEM_EXTRA_VCRBT_CAUSE = "com.samsung.telephony.extra.VCRBT_CAUSE";
    public static final String SEM_EXTRA_VCRBT_REASON_PROTOCOL =
            "com.samsung.telephony.extra.VCRBT_REASON_PROTOCOL";
    public static final String SEM_EXTRA_VCRBT_TEXT_DESCRIPTION =
            "com.samsung.telephony.extra.TEXT_DESCRIPTION";
    public static final String SEM_EXTRA_VIDEO_CRBT = "com.samsung.telephony.extra.VIDEO_CRBT";
    public static final String SEM_EXTRA_VIDEO_CRT_IS_ALERTING =
            "com.samsung.telephony.extra.VIDEO_CRT_IS_ALERTING";
    public static final String SEM_EXTRA_VIDEO_CRT_MT = "com.samsung.telephony.extra.VIDEO_CRT_MT";
    public static final int SEM_NR_MODE_NSA = 1;
    public static final int SEM_NR_MODE_SA = 2;
    public static final int SEM_NR_MODE_SA_NSA = 0;
    public static final String SEM_PREFERRED_DOMAIN_CS = "CS";
    public static final String SEM_PREFERRED_DOMAIN_PS = "PS";
    public static final int SEM_SET_SATELLITE_RESULT_INVALID_STATE = 2;
    public static final int SEM_SET_SATELLITE_RESULT_MODEM_ERROR = 1;
    public static final int SEM_SET_SATELLITE_RESULT_RADIOS_OFF_ERROR = 3;
    public static final int SEM_SET_SATELLITE_RESULT_SUCCESS = 0;
    public static final String SEM_VALIDATION_STATE_FAILED = "TN-Validation-Failed";
    public static final String SEM_VALIDATION_STATE_NONE = "No-TN-Validation";
    public static final String SEM_VALIDATION_STATE_PASSED = "TN-Validation-Passed";
    public static final int SEM_VIDEO_CALL_RECORDING_STATE_IN_PROGRESS = 1;
    public static final int SEM_VIDEO_CALL_RECORDING_STATE_NOT_IN_PROGRESS = 0;
    public static final int SEM_WAITING_TONE_PLAY_TYPE_APP = 7;
    public static final int SEM_WAITING_TONE_PLAY_TYPE_NETWORK = 0;

    @SystemApi public static final int SET_CARRIER_RESTRICTION_ERROR = 2;

    @SystemApi public static final int SET_CARRIER_RESTRICTION_NOT_SUPPORTED = 1;

    @SystemApi public static final int SET_CARRIER_RESTRICTION_SUCCESS = 0;
    public static final int SET_OPPORTUNISTIC_SUB_INACTIVE_SUBSCRIPTION = 2;
    public static final int SET_OPPORTUNISTIC_SUB_NO_OPPORTUNISTIC_SUB_AVAILABLE = 3;
    public static final int SET_OPPORTUNISTIC_SUB_REMOTE_SERVICE_EXCEPTION = 4;
    public static final int SET_OPPORTUNISTIC_SUB_SUCCESS = 0;
    public static final int SET_OPPORTUNISTIC_SUB_VALIDATION_FAILED = 1;

    @SystemApi public static final int SET_SIM_POWER_STATE_ALREADY_IN_STATE = 1;

    @SystemApi public static final int SET_SIM_POWER_STATE_MODEM_ERROR = 2;

    @SystemApi public static final int SET_SIM_POWER_STATE_NOT_SUPPORTED = 4;

    @SystemApi public static final int SET_SIM_POWER_STATE_SIM_ERROR = 3;

    @SystemApi public static final int SET_SIM_POWER_STATE_SUCCESS = 0;
    private static final int SIMSLOT1 = 0;
    private static final int SIMSLOT2 = 1;

    @SystemApi public static final int SIM_ACTIVATION_STATE_ACTIVATED = 2;

    @SystemApi public static final int SIM_ACTIVATION_STATE_ACTIVATING = 1;

    @SystemApi public static final int SIM_ACTIVATION_STATE_DEACTIVATED = 3;

    @SystemApi public static final int SIM_ACTIVATION_STATE_RESTRICTED = 4;

    @SystemApi public static final int SIM_ACTIVATION_STATE_UNKNOWN = 0;
    public static final int SIM_STATE_ABSENT = 1;
    public static final int SIM_STATE_CARD_IO_ERROR = 8;
    public static final int SIM_STATE_CARD_RESTRICTED = 9;
    public static final int SIM_STATE_DETECTED = 16;

    @SystemApi public static final int SIM_STATE_LOADED = 10;
    public static final int SIM_STATE_NETWORK_LOCKED = 4;
    public static final int SIM_STATE_NETWORK_SUBSET_LOCKED = 13;
    public static final int SIM_STATE_NOT_READY = 6;
    public static final int SIM_STATE_PERM_DISABLED = 7;
    public static final int SIM_STATE_PERSO_LOCKED = 12;
    public static final int SIM_STATE_PIN_REQUIRED = 2;

    @SystemApi public static final int SIM_STATE_PRESENT = 11;
    public static final int SIM_STATE_PUK_REQUIRED = 3;
    public static final int SIM_STATE_READY = 5;
    public static final int SIM_STATE_REGIONAL_LOCKED = 15;
    public static final int SIM_STATE_SIM_SERVICE_PROVIDER_LOCKED = 14;
    public static final int SIM_STATE_UNKNOWN = 0;

    @SystemApi public static final int SRVCC_STATE_HANDOVER_CANCELED = 3;

    @SystemApi public static final int SRVCC_STATE_HANDOVER_COMPLETED = 1;

    @SystemApi public static final int SRVCC_STATE_HANDOVER_FAILED = 2;

    @SystemApi public static final int SRVCC_STATE_HANDOVER_NONE = -1;

    @SystemApi public static final int SRVCC_STATE_HANDOVER_STARTED = 0;
    public static final int STOP_REASON_EMERGENCY_SMS_SENT = 4;
    public static final int STOP_REASON_NORMAL_SMS_SENT = 2;
    public static final int STOP_REASON_OUTGOING_EMERGENCY_CALL_INITIATED = 3;
    public static final int STOP_REASON_OUTGOING_NORMAL_CALL_INITIATED = 1;
    public static final int STOP_REASON_TIMER_EXPIRED = 5;
    public static final int STOP_REASON_UNKNOWN = 0;
    public static final int STOP_REASON_USER_ACTION = 6;
    private static final String TAG = "TelephonyManager";

    @SystemApi public static final int THERMAL_MITIGATION_RESULT_INVALID_STATE = 3;

    @SystemApi public static final int THERMAL_MITIGATION_RESULT_MODEM_ERROR = 1;

    @SystemApi public static final int THERMAL_MITIGATION_RESULT_MODEM_NOT_AVAILABLE = 2;

    @SystemApi public static final int THERMAL_MITIGATION_RESULT_SUCCESS = 0;

    @SystemApi public static final int THERMAL_MITIGATION_RESULT_UNKNOWN_ERROR = 4;
    public static final int UNINITIALIZED_CARD_ID = -2;
    public static final int UNKNOWN_CARRIER_ID = -1;
    public static final int UNKNOWN_CARRIER_ID_LIST_VERSION = -1;
    public static final int UNSUPPORTED_CARD_ID = -1;
    public static final int UPDATE_AVAILABLE_NETWORKS_ABORTED = 2;
    public static final int UPDATE_AVAILABLE_NETWORKS_DISABLE_MODEM_FAIL = 5;
    public static final int UPDATE_AVAILABLE_NETWORKS_ENABLE_MODEM_FAIL = 6;
    public static final int UPDATE_AVAILABLE_NETWORKS_INVALID_ARGUMENTS = 3;
    public static final int UPDATE_AVAILABLE_NETWORKS_MULTIPLE_NETWORKS_NOT_SUPPORTED = 7;
    public static final int UPDATE_AVAILABLE_NETWORKS_NO_CARRIER_PRIVILEGE = 4;
    public static final int UPDATE_AVAILABLE_NETWORKS_NO_OPPORTUNISTIC_SUB_AVAILABLE = 8;
    public static final int UPDATE_AVAILABLE_NETWORKS_REMOTE_SERVICE_EXCEPTION = 9;
    public static final int UPDATE_AVAILABLE_NETWORKS_SERVICE_IS_DISABLED = 10;
    public static final int UPDATE_AVAILABLE_NETWORKS_SIM_PORT_NOT_AVAILABLE = 11;
    public static final int UPDATE_AVAILABLE_NETWORKS_SUCCESS = 0;
    public static final int UPDATE_AVAILABLE_NETWORKS_UNKNOWN_FAILURE = 1;
    public static final int USSD_ERROR_SERVICE_UNAVAIL = -2;
    public static final String USSD_RESPONSE = "USSD_RESPONSE";
    public static final int USSD_RETURN_FAILURE = -1;
    public static final int USSD_RETURN_SUCCESS = 100;
    public static final String VVM_TYPE_CVVM = "vvm_type_cvvm";
    public static final String VVM_TYPE_OMTP = "vvm_type_omtp";
    private static final int mOpportunisticActiveSubsCount = 2;
    private static IPhoneSubInfo sIPhoneSubInfo;
    private static ISemPhoneSubInfo sISemPhoneSubInfo;
    private static ISemTelephony sISemTelephony;
    private static ISms sISms;
    private static ISub sISub;
    private static ITelephony sITelephony;
    private final Context mContext;
    private Document mDocument;
    private PropertyInvalidatedCache<PhoneAccountHandle, Integer> mPhoneAccountHandleToSubIdCache;
    private final int mSubId;
    private SubscriptionManager mSubscriptionManager;
    private TelephonyRegistryManager mTelephonyRegistryMgr;
    private TelephonyScanManager mTelephonyScanManager;
    public static boolean isSelecttelecomDF = false;
    private static final Object sCacheLock = new Object();
    private static boolean sServiceHandleCacheEnabled = true;
    private static final DeathRecipient sServiceDeath = new DeathRecipient();
    private static TelephonyManager sInstance = new TelephonyManager();
    private static boolean mIsCheckedMdec = false;
    private static boolean mIsInstalledMdec = false;
    public static final String EXTRA_STATE_IDLE = PhoneConstants.State.IDLE.toString();
    public static final String EXTRA_STATE_RINGING = PhoneConstants.State.RINGING.toString();
    public static final String EXTRA_STATE_OFFHOOK = PhoneConstants.State.OFFHOOK.toString();
    private static final int[] NETWORK_TYPES = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20
    };
    public static final int DEFAULT_PREFERRED_NETWORK_MODE = RILConstants.PREFERRED_NETWORK_MODE;
    public static final Pair HAL_VERSION_UNKNOWN = new Pair(-1, -1);
    public static final Pair HAL_VERSION_UNSUPPORTED = new Pair(-2, -2);

    @Retention(RetentionPolicy.SOURCE)
    public @interface AllowedNetworkTypesReason {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthenticationFailureReason {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallComposerStatus {}

    @SystemApi
    public interface CallForwardingInfoCallback {
        public static final int RESULT_ERROR_FDN_CHECK_FAILURE = 2;
        public static final int RESULT_ERROR_NOT_SUPPORTED = 3;
        public static final int RESULT_ERROR_UNKNOWN = 1;
        public static final int RESULT_SUCCESS = 0;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CallForwardingError {}

        void onCallForwardingInfoAvailable(CallForwardingInfo callForwardingInfo);

        void onError(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallWaitingStatus {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface CarrierRestrictionStatus {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface CdmaRoamingMode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface CdmaSubscription {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface CellBroadcastResult {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataEnabledChangedReason {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataEnabledReason {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataState {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface DefaultSubscriptionSelectType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface EmergencyCallbackModeStopReason {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface EmergencyCallbackModeType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnableNrDualConnectivityResult {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnableVoNrResult {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface EriIconIndex {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface EriIconMode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface HalService {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface IncludeLocationData {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface IsMultiSimSupportedResult {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface MobileDataPolicy {}

    public enum MultiSimVariants {
        DSDS,
        DSDA,
        TSTS,
        UNKNOWN
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkSelectionMode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkTypeBitMask {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface NrDualConnectivityState {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrefNetworkMode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface PremiumCapability {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrepareUnattendedRebootResult {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface PurchasePremiumCapabilityResult {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface RadioInterfaceCapability {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface RadioPowerReason {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemEnableVoNrResult {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetCarrierRestrictionResult {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetOpportunisticSubscriptionResult {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetSatelliteModeResult {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetSimPowerStateResult {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SimCombinationWarningType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SimPowerState {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SimState {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface UpdateAvailableNetworksResult {}

    public interface WifiCallingChoices {
        public static final int ALWAYS_USE = 0;
        public static final int ASK_EVERY_TIME = 1;
        public static final int NEVER_USE = 2;
    }

    public static String srvccStateToString(int state) {
        switch (state) {
            case -1:
                return KeyProperties.DIGEST_NONE;
            case 0:
                return "STARTED";
            case 1:
                return "COMPLETED";
            case 2:
                return "FAILED";
            case 3:
                return "CANCELED";
            default:
                return "UNKNOWN(" + state + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public TelephonyManager(Context context) {
        this(context, Integer.MAX_VALUE);
    }

    public TelephonyManager(Context context, int subId) {
        this.mDocument = null;
        this.mPhoneAccountHandleToSubIdCache =
                new PropertyInvalidatedCache<PhoneAccountHandle, Integer>(
                        4,
                        CACHE_KEY_PHONE_ACCOUNT_TO_SUBID) { // from class:
                                                            // android.telephony.TelephonyManager.1
                    @Override // android.app.PropertyInvalidatedCache
                    public Integer recompute(PhoneAccountHandle phoneAccountHandle) {
                        try {
                            ITelephony telephony = TelephonyManager.getITelephony();
                            if (telephony != null) {
                                return Integer.valueOf(
                                        telephony.getSubIdForPhoneAccountHandle(
                                                phoneAccountHandle,
                                                TelephonyManager.this.mContext.getOpPackageName(),
                                                TelephonyManager.this.mContext
                                                        .getAttributionTag()));
                            }
                            return -1;
                        } catch (RemoteException e) {
                            throw e.rethrowAsRuntimeException();
                        }
                    }
                };
        this.mSubId = subId;
        this.mContext =
                mergeAttributionAndRenouncedPermissions(context.getApplicationContext(), context);
        this.mSubscriptionManager = SubscriptionManager.from(this.mContext);
    }

    private TelephonyManager() {
        this.mDocument = null;
        this.mPhoneAccountHandleToSubIdCache =
                new PropertyInvalidatedCache<PhoneAccountHandle, Integer>(
                        4,
                        CACHE_KEY_PHONE_ACCOUNT_TO_SUBID) { // from class:
                                                            // android.telephony.TelephonyManager.1
                    @Override // android.app.PropertyInvalidatedCache
                    public Integer recompute(PhoneAccountHandle phoneAccountHandle) {
                        try {
                            ITelephony telephony = TelephonyManager.getITelephony();
                            if (telephony != null) {
                                return Integer.valueOf(
                                        telephony.getSubIdForPhoneAccountHandle(
                                                phoneAccountHandle,
                                                TelephonyManager.this.mContext.getOpPackageName(),
                                                TelephonyManager.this.mContext
                                                        .getAttributionTag()));
                            }
                            return -1;
                        } catch (RemoteException e) {
                            throw e.rethrowAsRuntimeException();
                        }
                    }
                };
        this.mContext = null;
        this.mSubId = -1;
    }

    @Deprecated
    public static TelephonyManager getDefault() {
        return sInstance;
    }

    private Context mergeAttributionAndRenouncedPermissions(Context to, Context from) {
        Context contextToReturn;
        if (to == null) {
            return from;
        }
        if (!Objects.equals(from.getAttributionTag(), to.getAttributionTag())) {
            contextToReturn = to.createAttributionContext(from.getAttributionTag());
        } else {
            contextToReturn = to;
        }
        Set<String> renouncedPermissions = from.getAttributionSource().getRenouncedPermissions();
        if (!renouncedPermissions.isEmpty()) {
            if (to.getParams() != null) {
                return contextToReturn.createContext(
                        new ContextParams.Builder(to.getParams())
                                .setRenouncedPermissions(renouncedPermissions)
                                .build());
            }
            return contextToReturn.createContext(
                    new ContextParams.Builder()
                            .setRenouncedPermissions(renouncedPermissions)
                            .build());
        }
        return contextToReturn;
    }

    private String getOpPackageName() {
        if (this.mContext != null) {
            return this.mContext.getOpPackageName();
        }
        ITelephony telephony = getITelephony();
        if (telephony == null) {
            return null;
        }
        try {
            return telephony.getCurrentPackageName();
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    private String getAttributionTag() {
        if (this.mContext != null) {
            return this.mContext.getAttributionTag();
        }
        return null;
    }

    private Set<String> getRenouncedPermissions() {
        if (this.mContext != null) {
            return this.mContext.getAttributionSource().getRenouncedPermissions();
        }
        return Collections.emptySet();
    }

    private static void runOnBackgroundThread(Runnable r) {
        try {
            BackgroundThread.getExecutor().execute(r);
        } catch (RejectedExecutionException e) {
            throw new IllegalStateException(
                    "Failed to post a callback from the caller's thread context.", e);
        }
    }

    public MultiSimVariants getMultiSimConfiguration() {
        String mSimConfig = TelephonyProperties.multi_sim_config().orElse("");
        if (mSimConfig.equals("dsds")) {
            return MultiSimVariants.DSDS;
        }
        if (mSimConfig.equals("dsda")) {
            return MultiSimVariants.DSDA;
        }
        if (mSimConfig.equals("tsts")) {
            return MultiSimVariants.TSTS;
        }
        return MultiSimVariants.UNKNOWN;
    }

    @Deprecated
    public int getPhoneCount() {
        return getActiveModemCount();
    }

    public int getActiveModemCount() {
        int modemCount;
        switch (getMultiSimConfiguration()) {
            case UNKNOWN:
                modemCount = 1;
                if (!isVoiceCapable() && !isSmsCapable() && !isDataCapable()) {
                    modemCount = 0;
                }
                if (modemCount != 0 || !checkCmcInstalled(this.mContext)) {}
                break;
        }
        return modemCount;
    }

    private static boolean checkCmcInstalled(Context context) {
        if (context != null && !mIsCheckedMdec) {
            mIsCheckedMdec = true;
            PackageManager pm = context.getPackageManager();
            try {
                pm.getPackageInfo(AsPackageName.CMC, 0);
                mIsInstalledMdec = true;
            } catch (PackageManager.NameNotFoundException e) {
                com.android.telephony.Rlog.d(TAG, "mdecservice package NameNotFoundException");
            }
        }
        return mIsInstalledMdec;
    }

    public int getSupportedModemCount() {
        return TelephonyProperties.max_active_modems()
                .orElse(Integer.valueOf(getActiveModemCount()))
                .intValue();
    }

    @SystemApi
    public int getMaxNumberOfSimultaneouslyActiveSims() {
        switch (getMultiSimConfiguration()) {
        }
        return 1;
    }

    public static TelephonyManager from(Context context) {
        return (TelephonyManager) context.getSystemService("phone");
    }

    public TelephonyManager createForSubscriptionId(int subId) {
        return new TelephonyManager(this.mContext, subId);
    }

    public TelephonyManager createForPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle) {
        int subId = getSubscriptionId(phoneAccountHandle);
        if (!SubscriptionManager.isValidSubscriptionId(subId)) {
            return null;
        }
        return new TelephonyManager(this.mContext, subId);
    }

    public boolean isMultiSimEnabled() {
        return getPhoneCount() > 1;
    }

    public static long getMaximumCallComposerPictureSize() {
        return 80000L;
    }

    public String getDeviceSoftwareVersion() {
        return getDeviceSoftwareVersion(getSlotIndex());
    }

    @SystemApi
    public String getDeviceSoftwareVersion(int slotIndex) {
        ITelephony telephony = getITelephony();
        if (telephony == null) {
            return null;
        }
        try {
            return telephony.getDeviceSoftwareVersionForSlot(
                    slotIndex, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @Deprecated
    public String getDeviceId() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return null;
            }
            return telephony.getDeviceIdWithFeature(
                    this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @Deprecated
    public String getDeviceId(int slotIndex) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getDeviceIdForPhone(
                    slotIndex, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getImei() {
        return getImei(getSlotIndex());
    }

    public String getImei(int slotIndex) {
        ITelephony telephony = getITelephony();
        if (telephony == null) {
            return null;
        }
        try {
            return telephony.getImeiForSlot(slotIndex, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getTypeAllocationCode() {
        return getTypeAllocationCode(getSlotIndex());
    }

    public String getTypeAllocationCode(int slotIndex) {
        ITelephony telephony = getITelephony();
        if (telephony == null) {
            return null;
        }
        try {
            return telephony.getTypeAllocationCodeForSlot(slotIndex);
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getMeid() {
        return getMeid(getSlotIndex());
    }

    public String getMeid(int slotIndex) {
        ITelephony telephony = getITelephony();
        if (telephony == null) {
            return null;
        }
        try {
            String meid =
                    telephony.getMeidForSlot(slotIndex, getOpPackageName(), getAttributionTag());
            if (TextUtils.isEmpty(meid)) {
                Log.d(TAG, "getMeid: return null because MEID is not available");
                return null;
            }
            return meid;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getManufacturerCode() {
        return getManufacturerCode(getSlotIndex());
    }

    public String getManufacturerCode(int slotIndex) {
        ITelephony telephony = getITelephony();
        if (telephony == null) {
            return null;
        }
        try {
            return telephony.getManufacturerCodeForSlot(slotIndex);
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getNai() {
        return getNaiBySubscriberId(getSubId());
    }

    private String getNaiBySubscriberId(int subId) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            String nai =
                    info.getNaiForSubscriber(
                            subId,
                            this.mContext.getOpPackageName(),
                            this.mContext.getAttributionTag());
            if (Log.isLoggable(TAG, 2)) {
                com.android.telephony.Rlog.v(TAG, "Nai = " + nai);
            }
            return nai;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @Deprecated
    public CellLocation getCellLocation() {
        logWithCallerInfo("getCellLocation");
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                com.android.telephony.Rlog.d(
                        TAG, "getCellLocation returning null because telephony is null");
                return null;
            }
            CellIdentity cellIdentity =
                    telephony.getCellLocation(
                            this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            CellLocation cl = cellIdentity.asCellLocation();
            if (cl != null && !cl.isEmpty()) {
                return cl;
            }
            com.android.telephony.Rlog.d(
                    TAG,
                    "getCellLocation returning null because CellLocation is empty or phone type"
                        + " doesn't match CellLocation type");
            return null;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.d(
                    TAG, "getCellLocation returning null due to RemoteException " + ex);
            return null;
        }
    }

    public CellLocation getCellLocationBySubId(int subId) {
        logWithCallerInfo("getCellLocationBySubId");
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony == null) {
                com.android.telephony.Rlog.d(
                        TAG,
                        "getCellLocationBySubId returning null because SemTelephonyService is"
                            + " null");
                return null;
            }
            CellIdentity cellIdentity =
                    semTelephony.getCellLocationBySubId(
                            subId, this.mContext.getOpPackageName(), getAttributionTag());
            CellLocation cl = cellIdentity.asCellLocation();
            if (cl != null && !cl.isEmpty()) {
                return cl;
            }
            com.android.telephony.Rlog.d(
                    TAG,
                    "getCellLocationBySubId returning null because CellLocation is empty or phone"
                        + " type doesn't match CellLocation type");
            return null;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.d(
                    TAG, "getCellLocationBySubId returning null due to RemoteException " + ex);
            return null;
        }
    }

    public CellLocation getCellLocationForPhone(int phoneId) {
        logWithCallerInfo("getCellLocationForPhone");
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony == null) {
                com.android.telephony.Rlog.d(
                        TAG,
                        "getCellLocationForPhone returning null because SemTelephonyService is"
                            + " null");
                return null;
            }
            CellIdentity cellIdentity =
                    semTelephony.getCellLocationForPhone(
                            phoneId, this.mContext.getOpPackageName(), getAttributionTag());
            CellLocation cl = cellIdentity.asCellLocation();
            if (cl != null && !cl.isEmpty()) {
                return cl;
            }
            com.android.telephony.Rlog.d(
                    TAG,
                    "getCellLocationForPhone returning null because CellLocation is empty or phone"
                        + " type doesn't match CellLocation type");
            return null;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.d(
                    TAG, "getCellLocationForPhone returning null due to RemoteException " + ex);
            return null;
        }
    }

    @Deprecated
    public List<NeighboringCellInfo> getNeighboringCellInfo() {
        logWithCallerInfo("getNeighboringCellInfo");
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return null;
            }
            return telephony.getNeighboringCellInfo(
                    this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @SystemApi
    public int getCurrentPhoneType() {
        return getCurrentPhoneType(getSubId());
    }

    @SystemApi
    public int getCurrentPhoneType(int subId) {
        int phoneId;
        if (subId == -1) {
            phoneId = 0;
        } else {
            phoneId = SubscriptionManager.getPhoneId(subId);
        }
        return getCurrentPhoneTypeForSlot(phoneId);
    }

    public int getCurrentPhoneTypeForSlot(int slotIndex) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getActivePhoneTypeForSlot(slotIndex);
            }
            return getPhoneTypeFromProperty(slotIndex);
        } catch (RemoteException e) {
            return getPhoneTypeFromProperty(slotIndex);
        } catch (NullPointerException e2) {
            return getPhoneTypeFromProperty(slotIndex);
        }
    }

    public int getPhoneType() {
        if (!isVoiceCapable()) {
            return 0;
        }
        return getCurrentPhoneType();
    }

    private int getPhoneTypeFromProperty(int phoneId) {
        Integer type =
                (Integer)
                        getTelephonyProperty(
                                phoneId, TelephonyProperties.current_active_phone(), (Object) null);
        return type != null ? type.intValue() : getPhoneTypeFromNetworkType(phoneId);
    }

    private int getPhoneTypeFromNetworkType(int phoneId) {
        Integer mode =
                (Integer)
                        getTelephonyProperty(
                                phoneId, TelephonyProperties.default_network(), (Object) null);
        if (mode != null) {
            return getPhoneType(mode.intValue());
        }
        return 0;
    }

    public static int getPhoneType(int networkMode) {
        switch (networkMode) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 22:
                return 1;
            case 4:
            case 5:
            case 6:
                return 2;
            case 7:
            case 8:
            case 21:
            case 25:
                return 2;
            case 11:
            case 24:
                return TelephonyProperties.lte_on_cdma_device().orElse(0).intValue() == 1 ? 2 : 1;
            case 23:
            default:
                return 1;
        }
    }

    @SystemApi
    public static long getMaxNumberVerificationTimeoutMillis() {
        return 60000L;
    }

    public String getNetworkOperatorName() {
        return getNetworkOperatorName(getSubId());
    }

    public String getNetworkOperatorName(int subId) {
        int phoneId = SubscriptionManager.getPhoneId(subId);
        return (String) getTelephonyProperty(phoneId, TelephonyProperties.operator_alpha(), "");
    }

    public String getNetworkOperator() {
        return getNetworkOperatorForPhone(getPhoneId());
    }

    public String getNetworkOperator(int subId) {
        int phoneId = SubscriptionManager.getPhoneId(subId);
        return getNetworkOperatorForPhone(phoneId);
    }

    public String getNetworkOperatorForPhone(int phoneId) {
        return (String) getTelephonyProperty(phoneId, TelephonyProperties.operator_numeric(), "");
    }

    public String getNetworkSpecifier() {
        return String.valueOf(getSubId());
    }

    public PersistableBundle getCarrierConfig() {
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) this.mContext.getSystemService(CarrierConfigManager.class);
        if (carrierConfigManager == null) {
            throw new UnsupportedOperationException(
                    "getCarrierConfig is unsupported without"
                        + " android.hardware.telephony.subscription");
        }
        return carrierConfigManager.getConfigForSubId(getSubId());
    }

    public boolean isNetworkRoaming() {
        return isNetworkRoaming(getSubId());
    }

    public boolean isNetworkRoaming(int subId) {
        int phoneId = SubscriptionManager.getPhoneId(subId);
        return ((Boolean)
                        getTelephonyProperty(
                                phoneId,
                                (List<boolean>) TelephonyProperties.operator_is_roaming(),
                                false))
                .booleanValue();
    }

    public String getNetworkCountryIso() {
        return getNetworkCountryIso(getSlotIndex());
    }

    public String getNetworkCountryIso(int slotIndex) {
        if (slotIndex != Integer.MAX_VALUE) {
            try {
                if (!SubscriptionManager.isValidSlotIndex(slotIndex)) {
                    throw new IllegalArgumentException("invalid slot index " + slotIndex);
                }
            } catch (RemoteException e) {
                return "";
            }
        }
        ITelephony telephony = getITelephony();
        return telephony == null ? "" : telephony.getNetworkCountryIsoForPhone(slotIndex);
    }

    @Deprecated
    public String getNetworkCountryIsoForPhone(int phoneId) {
        return getNetworkCountryIso(phoneId);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static int[] getAllNetworkTypes() {
        return (int[]) NETWORK_TYPES.clone();
    }

    @Deprecated
    public int getNetworkType() {
        return getNetworkType(getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
    }

    public int getNetworkType(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return 0;
            }
            return telephony.getNetworkTypeForSubscriber(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return 0;
        } catch (NullPointerException e2) {
            return 0;
        }
    }

    public int semGetNetworkType(int subId) {
        return getNetworkType(subId);
    }

    public int getDataNetworkType() {
        return getDataNetworkType(getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
    }

    public int getDataNetworkType(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return 0;
            }
            return telephony.getDataNetworkTypeForSubscriber(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return 0;
        } catch (NullPointerException e2) {
            return 0;
        }
    }

    public int getVoiceNetworkType() {
        return getVoiceNetworkType(getSubId());
    }

    public int getVoiceNetworkType(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return 0;
            }
            return telephony.getVoiceNetworkTypeForSubscriber(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return 0;
        } catch (NullPointerException e2) {
            return 0;
        }
    }

    public String getNetworkTypeName() {
        return getNetworkTypeName(getNetworkType());
    }

    public static String getNetworkTypeName(int type) {
        switch (type) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "CDMA - EvDo rev. 0";
            case 6:
                return "CDMA - EvDo rev. A";
            case 7:
                return "CDMA - 1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "iDEN";
            case 12:
                return "CDMA - EvDo rev. B";
            case 13:
                return DctConstants.RAT_NAME_LTE;
            case 14:
                return "CDMA - eHRPD";
            case 15:
                return "HSPA+";
            case 16:
                return "GSM";
            case 17:
                return "TD_SCDMA";
            case 18:
                return "IWLAN";
            case 19:
                return "LTE_CA";
            case 20:
                return "NR";
            default:
                return "UNKNOWN(" + type + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static long getBitMaskForNetworkType(int networkType) {
        switch (networkType) {
            case 1:
                return 1L;
            case 2:
                return 2L;
            case 3:
                return 4L;
            case 4:
                return 8L;
            case 5:
                return 16L;
            case 6:
                return 32L;
            case 7:
                return 64L;
            case 8:
                return 128L;
            case 9:
                return 256L;
            case 10:
                return 512L;
            case 11:
                return 1024L;
            case 12:
                return 2048L;
            case 13:
            case 19:
                return 4096L;
            case 14:
                return 8192L;
            case 15:
                return 16384L;
            case 16:
                return 32768L;
            case 17:
                return 65536L;
            case 18:
                return 131072L;
            case 20:
                return 524288L;
            default:
                return 0L;
        }
    }

    public boolean hasIccCard() {
        return hasIccCard(getSlotIndex());
    }

    public boolean hasIccCard(int slotIndex) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return false;
            }
            return telephony.hasIccCardUsingSlotIndex(slotIndex);
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    public boolean semHasIccCard(int slotIndex) {
        return hasIccCard(slotIndex);
    }

    public int getSimState() {
        int simState = getSimStateIncludingLoaded();
        if (simState == 10) {
            return 5;
        }
        if (simState == 12 || simState == 13 || simState == 14 || simState == 15) {
            return 4;
        }
        if (simState == 16) {
            return 6;
        }
        return simState;
    }

    private int getSimStateIncludingLoaded() {
        int slotIndex = getSlotIndex();
        if (slotIndex < 0) {
            for (int i = 0; i < getPhoneCount(); i++) {
                int simState = getSimState(i);
                if (simState != 1) {
                    com.android.telephony.Rlog.d(
                            TAG,
                            "getSimState: default sim:"
                                    + slotIndex
                                    + ", sim state for slotIndex="
                                    + i
                                    + " is "
                                    + simState
                                    + ", return state as unknown");
                    return 0;
                }
            }
            com.android.telephony.Rlog.d(
                    TAG,
                    "getSimState: default sim:"
                            + slotIndex
                            + ", all SIMs absent, return state as absent");
            return 1;
        }
        return getSimStateForSlotIndex(slotIndex);
    }

    @SystemApi
    public int getSimCardState() {
        int simState = getSimState();
        return getSimCardStateFromSimState(simState);
    }

    @SystemApi
    @Deprecated
    public int getSimCardState(int physicalSlotIndex) {
        int activePort = getFirstActivePortIndex(physicalSlotIndex);
        int simState = getSimState(getLogicalSlotIndex(physicalSlotIndex, activePort));
        return getSimCardStateFromSimState(simState);
    }

    @SystemApi
    public int getSimCardState(int physicalSlotIndex, int portIndex) {
        int simState = getSimState(getLogicalSlotIndex(physicalSlotIndex, portIndex));
        return getSimCardStateFromSimState(simState);
    }

    private int getSimCardStateFromSimState(int simState) {
        switch (simState) {
            case 0:
            case 1:
            case 8:
            case 9:
                return simState;
            default:
                return 11;
        }
    }

    private int getLogicalSlotIndex(int physicalSlotIndex, int portIndex) {
        UiccSlotInfo[] slotInfos = getUiccSlotsInfo();
        if (slotInfos != null
                && physicalSlotIndex >= 0
                && physicalSlotIndex < slotInfos.length
                && slotInfos[physicalSlotIndex] != null) {
            for (UiccPortInfo portInfo : slotInfos[physicalSlotIndex].getPorts()) {
                if (portInfo.getPortIndex() == portIndex) {
                    return portInfo.getLogicalSlotIndex();
                }
            }
            return -1;
        }
        return -1;
    }

    @SystemApi
    public int getSimApplicationState() {
        int simState = getSimStateIncludingLoaded();
        return getSimApplicationStateFromSimState(simState);
    }

    @SystemApi
    @Deprecated
    public int getSimApplicationState(int physicalSlotIndex) {
        int activePort = getFirstActivePortIndex(physicalSlotIndex);
        int simState = getSimStateForSlotIndex(getLogicalSlotIndex(physicalSlotIndex, activePort));
        return getSimApplicationStateFromSimState(simState);
    }

    @SystemApi
    public int getSimApplicationState(int physicalSlotIndex, int portIndex) {
        int simState = getSimStateForSlotIndex(getLogicalSlotIndex(physicalSlotIndex, portIndex));
        return getSimApplicationStateFromSimState(simState);
    }

    private int getSimApplicationStateFromSimState(int simState) {
        switch (simState) {
            case 0:
            case 1:
            case 8:
            case 9:
                return 0;
            case 5:
                return 6;
            default:
                return simState;
        }
    }

    @SystemApi
    public boolean isApplicationOnUicc(int appType) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.isApplicationOnUicc(getSubId(), appType);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isApplicationOnUicc", e);
            return false;
        }
    }

    public int getSimState(int slotIndex) {
        int simState = getSimStateForSlotIndex(slotIndex);
        if (simState == 10) {
            return 5;
        }
        if (simState == 12 || simState == 13 || simState == 14 || simState == 15) {
            return 4;
        }
        if (simState == 16) {
            return 6;
        }
        return simState;
    }

    public String getSimOperator() {
        return getSimOperatorNumeric();
    }

    public String getSimOperator(int subId) {
        return getSimOperatorNumeric(subId);
    }

    public String getSimOperatorNumeric() {
        int subId = this.mSubId;
        if (!SubscriptionManager.isUsableSubIdValue(subId)) {
            subId = SubscriptionManager.getDefaultDataSubscriptionId();
            if (!SubscriptionManager.isUsableSubIdValue(subId)) {
                subId = SubscriptionManager.getDefaultSmsSubscriptionId();
                if (!SubscriptionManager.isUsableSubIdValue(subId)) {
                    subId = SubscriptionManager.getDefaultVoiceSubscriptionId();
                    if (!SubscriptionManager.isUsableSubIdValue(subId)) {
                        subId = SubscriptionManager.getDefaultSubscriptionId();
                    }
                }
            }
        }
        return getSimOperatorNumeric(subId);
    }

    public String getSimOperatorNumeric(int subId) {
        int phoneId = SubscriptionManager.getPhoneId(subId);
        return getSimOperatorNumericForPhone(phoneId);
    }

    public String getSimOperatorNumericForPhone(int phoneId) {
        return (String)
                getTelephonyProperty(phoneId, TelephonyProperties.icc_operator_numeric(), "");
    }

    public String getSimOperatorName() {
        return getSimOperatorNameForPhone(getPhoneId());
    }

    public String getSimOperatorName(int subId) {
        int phoneId = SubscriptionManager.getPhoneId(subId);
        return getSimOperatorNameForPhone(phoneId);
    }

    public String getSimOperatorNameForPhone(int phoneId) {
        return (String) getTelephonyProperty(phoneId, TelephonyProperties.icc_operator_alpha(), "");
    }

    public String getSimCountryIso() {
        return getSimCountryIsoForPhone(getPhoneId());
    }

    public static String getSimCountryIso(int subId) {
        int phoneId = SubscriptionManager.getPhoneId(subId);
        return getSimCountryIsoForPhone(phoneId);
    }

    public static String getSimCountryIsoForPhone(int phoneId) {
        return (String)
                getTelephonyProperty(phoneId, TelephonyProperties.icc_operator_iso_country(), "");
    }

    public String getSimSerialNumber() {
        return getSimSerialNumber(getSubId());
    }

    public String getSimSerialNumber(int subId) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getIccSerialNumberForSubscriber(
                    subId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @SystemApi
    public boolean isLteCdmaEvdoGsmWcdmaEnabled() {
        return getLteOnCdmaMode(getSubId()) == 1;
    }

    public int getLteOnCdmaMode(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return -1;
            }
            return telephony.getLteOnCdmaModeForSubscriber(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return -1;
        } catch (NullPointerException e2) {
            return -1;
        }
    }

    public int getCardIdForDefaultEuicc() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return -2;
            }
            return telephony.getCardIdForDefaultEuicc(
                    this.mSubId, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            return -2;
        }
    }

    public List<UiccCardInfo> getUiccCardsInfo() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                Log.e(TAG, "Error in getUiccCardsInfo: unable to connect to Telephony service.");
                return new ArrayList();
            }
            return telephony.getUiccCardsInfo(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            Log.e(TAG, "Error in getUiccCardsInfo: " + e);
            return new ArrayList();
        }
    }

    @SystemApi
    public UiccSlotInfo[] getUiccSlotsInfo() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return null;
            }
            return telephony.getUiccSlotsInfo(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            return null;
        }
    }

    public void refreshUiccProfile() {
        try {
            ITelephony telephony = getITelephony();
            telephony.refreshUiccProfile(this.mSubId);
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.w(TAG, "RemoteException", ex);
        }
    }

    @SystemApi
    @Deprecated
    public boolean switchSlots(int[] physicalSlots) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return false;
            }
            return telephony.switchSlots(physicalSlots);
        } catch (RemoteException e) {
            return false;
        }
    }

    private static boolean isSlotMappingValid(Collection<UiccSlotMapping> slotMapping) {
        Map<Integer, List<UiccSlotMapping>> slotMappingInfo =
                (Map)
                        slotMapping.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                new Function() { // from class:
                                                                 // android.telephony.TelephonyManager$$ExternalSyntheticLambda14
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return Integer.valueOf(
                                                                ((UiccSlotMapping) obj)
                                                                        .getLogicalSlotIndex());
                                                    }
                                                }));
        for (Map.Entry<Integer, List<UiccSlotMapping>> entry : slotMappingInfo.entrySet()) {
            List<UiccSlotMapping> logicalSlotMap = entry.getValue();
            if (logicalSlotMap.size() > 1) {
                return false;
            }
        }
        Map<List<Integer>, List<UiccSlotMapping>> slotMapInfos =
                (Map)
                        slotMapping.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                new Function() { // from class:
                                                                 // android.telephony.TelephonyManager$$ExternalSyntheticLambda15
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        List asList;
                                                        asList =
                                                                Arrays.asList(
                                                                        Integer.valueOf(
                                                                                r1
                                                                                        .getPhysicalSlotIndex()),
                                                                        Integer.valueOf(
                                                                                ((UiccSlotMapping)
                                                                                                obj)
                                                                                        .getPortIndex()));
                                                        return asList;
                                                    }
                                                }));
        for (Map.Entry<List<Integer>, List<UiccSlotMapping>> entry2 : slotMapInfos.entrySet()) {
            List<UiccSlotMapping> portAndPhysicalSlotList = entry2.getValue();
            if (portAndPhysicalSlotList.size() > 1) {
                return false;
            }
        }
        return true;
    }

    @SystemApi
    public void setSimSlotMapping(Collection<UiccSlotMapping> slotMapping) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (isSlotMappingValid(slotMapping)) {
                    boolean result = telephony.setSimSlotMapping(new ArrayList(slotMapping));
                    if (!result) {
                        throw new IllegalStateException("setSimSlotMapping has failed");
                    }
                    return;
                }
                throw new IllegalArgumentException("Duplicate UiccSlotMapping data found");
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    @Deprecated
    public Map<Integer, Integer> getLogicalToPhysicalSlotMapping() {
        Map<Integer, Integer> slotMapping = new HashMap<>();
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                List<UiccSlotMapping> simSlotsMapping =
                        telephony.getSlotsMapping(this.mContext.getOpPackageName());
                for (UiccSlotMapping slotMap : simSlotsMapping) {
                    slotMapping.put(
                            Integer.valueOf(slotMap.getLogicalSlotIndex()),
                            Integer.valueOf(slotMap.getPhysicalSlotIndex()));
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "getSlotsMapping RemoteException", e);
        }
        return slotMapping;
    }

    @SystemApi
    public Collection<UiccSlotMapping> getSimSlotMapping() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                List<UiccSlotMapping> slotMap =
                        telephony.getSlotsMapping(this.mContext.getOpPackageName());
                return slotMap;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public String getSubscriberId() {
        return getSubscriberId(getSubId());
    }

    public String getSubscriberId(int subId) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getSubscriberIdForSubscriber(
                    subId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @SystemApi
    public ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int keyType) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                com.android.telephony.Rlog.e(TAG, "IMSI error: Subscriber Info is null");
                return null;
            }
            int subId = getSubId(SubscriptionManager.getDefaultDataSubscriptionId());
            if (keyType != 1 && keyType != 2) {
                throw new IllegalArgumentException("IMSI error: Invalid key type");
            }
            ImsiEncryptionInfo imsiEncryptionInfo =
                    info.getCarrierInfoForImsiEncryption(
                            subId, keyType, this.mContext.getOpPackageName());
            if (imsiEncryptionInfo == null && isImsiEncryptionRequired(subId, keyType)) {
                com.android.telephony.Rlog.e(TAG, "IMSI error: key is required but not found");
                throw new IllegalArgumentException("IMSI error: key is required but not found");
            }
            return imsiEncryptionInfo;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "getCarrierInfoForImsiEncryption RemoteException" + ex);
            return null;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "getCarrierInfoForImsiEncryption NullPointerException" + ex2);
            return null;
        }
    }

    @SystemApi
    public void resetCarrierKeysForImsiEncryption() {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                throw new RuntimeException("IMSI error: Subscriber Info is null");
            }
            int subId = getSubId(SubscriptionManager.getDefaultDataSubscriptionId());
            info.resetCarrierKeysForImsiEncryption(subId, this.mContext.getOpPackageName());
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "Telephony#getCarrierInfoForImsiEncryption RemoteException" + ex);
        }
    }

    private static boolean isKeyEnabled(int keyAvailability, int keyType) {
        int returnValue = (keyAvailability >> (keyType - 1)) & 1;
        return returnValue == 1;
    }

    private boolean isImsiEncryptionRequired(int subId, int keyType) {
        PersistableBundle pb;
        CarrierConfigManager configManager =
                (CarrierConfigManager) this.mContext.getSystemService("carrier_config");
        if (configManager == null || (pb = configManager.getConfigForSubId(subId)) == null) {
            return false;
        }
        int keyAvailability = pb.getInt(CarrierConfigManager.IMSI_KEY_AVAILABILITY_INT);
        return isKeyEnabled(keyAvailability, keyType);
    }

    public void setCarrierInfoForImsiEncryption(ImsiEncryptionInfo imsiEncryptionInfo) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return;
            }
            info.setCarrierInfoForImsiEncryption(
                    this.mSubId, this.mContext.getOpPackageName(), imsiEncryptionInfo);
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "setCarrierInfoForImsiEncryption RemoteException", ex);
        } catch (NullPointerException e) {
        }
    }

    public static class CallComposerException extends Exception {
        public static final int ERROR_AUTHENTICATION_FAILED = 3;
        public static final int ERROR_FILE_TOO_LARGE = 2;
        public static final int ERROR_INPUT_CLOSED = 4;
        public static final int ERROR_IO_EXCEPTION = 5;
        public static final int ERROR_NETWORK_UNAVAILABLE = 6;
        public static final int ERROR_REMOTE_END_CLOSED = 1;
        public static final int ERROR_UNKNOWN = 0;
        public static final int SUCCESS = -1;
        private final int mErrorCode;
        private final IOException mIOException;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CallComposerError {}

        public CallComposerException(int errorCode, IOException ioException) {
            this.mErrorCode = errorCode;
            this.mIOException = ioException;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public IOException getIOException() {
            return this.mIOException;
        }
    }

    public void uploadCallComposerPicture(
            final Path pictureToUpload,
            final String contentType,
            final Executor executor,
            final OutcomeReceiver<ParcelUuid, CallComposerException> callback) {
        Objects.requireNonNull(pictureToUpload);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        RoleManager rm = (RoleManager) this.mContext.getSystemService(RoleManager.class);
        if (!rm.isRoleHeld("android.app.role.DIALER")) {
            throw new SecurityException("You must hold RoleManager.ROLE_DIALER to do this");
        }
        executor.execute(
                new Runnable() { // from class:
                                 // android.telephony.TelephonyManager$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyManager.this.lambda$uploadCallComposerPicture$1(
                                pictureToUpload, callback, contentType, executor);
                    }
                });
    }

    public /* synthetic */ void lambda$uploadCallComposerPicture$1(
            Path pictureToUpload,
            final OutcomeReceiver callback,
            String contentType,
            Executor executor) {
        try {
            if (Looper.getMainLooper().isCurrentThread()) {
                Log.w(TAG, "Uploading call composer picture on main thread! hic sunt dracones!");
            }
            long size = Files.size(pictureToUpload);
            if (size > getMaximumCallComposerPictureSize()) {
                callback.onError(new CallComposerException(2, null));
                return;
            }
            final InputStream fileStream = Files.newInputStream(pictureToUpload, new OpenOption[0]);
            try {
                uploadCallComposerPicture(
                        fileStream,
                        contentType,
                        executor,
                        new OutcomeReceiver<ParcelUuid, CallComposerException>() { // from class:
                            // android.telephony.TelephonyManager.2
                            @Override // android.os.OutcomeReceiver
                            public void onResult(ParcelUuid result) {
                                try {
                                    fileStream.close();
                                } catch (IOException e) {
                                    Log.e(
                                            TelephonyManager.TAG,
                                            "Error closing file input stream when uploading call"
                                                + " composer pic");
                                }
                                callback.onResult(result);
                            }

                            @Override // android.os.OutcomeReceiver
                            public void onError(CallComposerException error) {
                                try {
                                    fileStream.close();
                                } catch (IOException e) {
                                    Log.e(
                                            TelephonyManager.TAG,
                                            "Error closing file input stream when uploading call"
                                                + " composer pic");
                                }
                                callback.onError(error);
                            }
                        });
            } catch (Exception e) {
                Log.e(
                        TAG,
                        "Got exception calling into stream-version of uploadCallComposerPicture: "
                                + e);
                try {
                    fileStream.close();
                } catch (IOException e2) {
                    Log.e(TAG, "Error closing file input stream when uploading call composer pic");
                }
            }
        } catch (IOException e3) {
            Log.e(TAG, "IOException when uploading call composer pic:" + e3);
            callback.onError(new CallComposerException(5, e3));
        }
    }

    public void uploadCallComposerPicture(
            final InputStream pictureToUpload,
            String contentType,
            Executor executor,
            final OutcomeReceiver<ParcelUuid, CallComposerException> callback) {
        Objects.requireNonNull(pictureToUpload);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        ITelephony telephony = getITelephony();
        if (telephony == null) {
            throw new IllegalStateException("Telephony service not available.");
        }
        try {
            ParcelFileDescriptor[] pipe = ParcelFileDescriptor.createReliablePipe();
            final ParcelFileDescriptor writeFd = pipe[1];
            ParcelFileDescriptor readFd = pipe[0];
            final OutputStream output = new ParcelFileDescriptor.AutoCloseOutputStream(writeFd);
            try {
                telephony.uploadCallComposerPicture(
                        getSubId(),
                        this.mContext.getOpPackageName(),
                        contentType,
                        readFd,
                        new AnonymousClass3(null, executor, callback));
            } catch (RemoteException e) {
                Log.e(TAG, "Remote exception uploading call composer pic:" + e);
                e.rethrowAsRuntimeException();
            }
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda23
                        @Override // java.lang.Runnable
                        public final void run() {
                            TelephonyManager.lambda$uploadCallComposerPicture$3(
                                    pictureToUpload, callback, writeFd, output);
                        }
                    });
        } catch (IOException e2) {
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda22
                        @Override // java.lang.Runnable
                        public final void run() {
                            OutcomeReceiver.this.onError(
                                    new TelephonyManager.CallComposerException(5, e2));
                        }
                    });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$3, reason: invalid class name */
    class AnonymousClass3 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass3(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle result) {
            if (resultCode != -1) {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$3$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                OutcomeReceiver.this.onError(
                                        new TelephonyManager.CallComposerException(
                                                resultCode, null));
                            }
                        });
                return;
            }
            final ParcelUuid resultUuid =
                    (ParcelUuid)
                            result.getParcelable(
                                    TelephonyManager.KEY_CALL_COMPOSER_PICTURE_HANDLE,
                                    ParcelUuid.class);
            if (resultUuid == null) {
                Log.e(
                        TelephonyManager.TAG,
                        "Got null uuid without an error while uploading call composer pic");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$3$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                OutcomeReceiver.this.onError(
                                        new TelephonyManager.CallComposerException(0, null));
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$3$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            OutcomeReceiver.this.onResult(resultUuid);
                        }
                    });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x002b, code lost:

       android.util.Log.e(android.telephony.TelephonyManager.TAG, "Read too many bytes from call composer pic stream: " + r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0041, code lost:

       r10.onError(new android.telephony.TelephonyManager.CallComposerException(2, null));
       r11.closeWithError("too large");
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0053, code lost:

       r5 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0054, code lost:

       android.util.Log.e(android.telephony.TelephonyManager.TAG, "Error closing fd pipe: " + r5);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ void lambda$uploadCallComposerPicture$3(
            java.io.InputStream r9,
            android.os.OutcomeReceiver r10,
            android.os.ParcelFileDescriptor r11,
            java.io.OutputStream r12) {
        /*
            java.lang.String r0 = "Error closing fd pipe: "
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            boolean r1 = r1.isCurrentThread()
            java.lang.String r2 = "TelephonyManager"
            if (r1 == 0) goto L13
            java.lang.String r1 = "Uploading call composer picture on main thread! hic sunt dracones!"
            android.util.Log.w(r2, r1)
        L13:
            r1 = 0
            r3 = 16384(0x4000, float:2.2959E-41)
            byte[] r3 = new byte[r3]
        L18:
            int r4 = r9.read(r3)     // Catch: java.lang.Throwable -> L97 java.io.IOException -> L99
            if (r4 >= 0) goto L21
            goto Ld5
        L21:
            int r1 = r1 + r4
            long r5 = (long) r1
            long r7 = getMaximumCallComposerPictureSize()     // Catch: java.lang.Throwable -> L97
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 <= 0) goto L6a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L97
            r5.<init>()     // Catch: java.lang.Throwable -> L97
            java.lang.String r6 = "Read too many bytes from call composer pic stream: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> L97
            java.lang.StringBuilder r5 = r5.append(r1)     // Catch: java.lang.Throwable -> L97
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L97
            android.util.Log.e(r2, r5)     // Catch: java.lang.Throwable -> L97
            android.telephony.TelephonyManager$CallComposerException r5 = new android.telephony.TelephonyManager$CallComposerException     // Catch: java.io.IOException -> L53 java.lang.Throwable -> L97
            r6 = 2
            r7 = 0
            r5.<init>(r6, r7)     // Catch: java.io.IOException -> L53 java.lang.Throwable -> L97
            r10.onError(r5)     // Catch: java.io.IOException -> L53 java.lang.Throwable -> L97
            java.lang.String r5 = "too large"
            r11.closeWithError(r5)     // Catch: java.io.IOException -> L53 java.lang.Throwable -> L97
            goto Ld5
        L53:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L97
            r6.<init>()     // Catch: java.lang.Throwable -> L97
            java.lang.StringBuilder r0 = r6.append(r0)     // Catch: java.lang.Throwable -> L97
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch: java.lang.Throwable -> L97
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L97
            android.util.Log.e(r2, r0)     // Catch: java.lang.Throwable -> L97
            goto Ld5
        L6a:
            r5 = 0
            r12.write(r3, r5, r4)     // Catch: java.io.IOException -> L70 java.lang.Throwable -> L97
            goto L18
        L70:
            r5 = move-exception
            android.telephony.TelephonyManager$CallComposerException r6 = new android.telephony.TelephonyManager$CallComposerException     // Catch: java.lang.Throwable -> L97
            r7 = 1
            r6.<init>(r7, r5)     // Catch: java.lang.Throwable -> L97
            r10.onError(r6)     // Catch: java.lang.Throwable -> L97
            java.lang.String r6 = "remote end closed"
            r11.closeWithError(r6)     // Catch: java.io.IOException -> L81 java.lang.Throwable -> L97
            goto L96
        L81:
            r6 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L97
            r7.<init>()     // Catch: java.lang.Throwable -> L97
            java.lang.StringBuilder r0 = r7.append(r0)     // Catch: java.lang.Throwable -> L97
            java.lang.StringBuilder r0 = r0.append(r6)     // Catch: java.lang.Throwable -> L97
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L97
            android.util.Log.e(r2, r0)     // Catch: java.lang.Throwable -> L97
        L96:
            goto Ld5
        L97:
            r0 = move-exception
            goto Ldc
        L99:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L97
            r5.<init>()     // Catch: java.lang.Throwable -> L97
            java.lang.String r6 = "IOException reading from input while uploading pic: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> L97
            java.lang.StringBuilder r5 = r5.append(r4)     // Catch: java.lang.Throwable -> L97
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L97
            android.util.Log.e(r2, r5)     // Catch: java.lang.Throwable -> L97
            android.telephony.TelephonyManager$CallComposerException r5 = new android.telephony.TelephonyManager$CallComposerException     // Catch: java.lang.Throwable -> L97
            r6 = 4
            r5.<init>(r6, r4)     // Catch: java.lang.Throwable -> L97
            r10.onError(r5)     // Catch: java.lang.Throwable -> L97
            java.lang.String r5 = "input closed"
            r11.closeWithError(r5)     // Catch: java.lang.Throwable -> L97 java.io.IOException -> Lbf
            goto Ld4
        Lbf:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L97
            r6.<init>()     // Catch: java.lang.Throwable -> L97
            java.lang.StringBuilder r0 = r6.append(r0)     // Catch: java.lang.Throwable -> L97
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch: java.lang.Throwable -> L97
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L97
            android.util.Log.e(r2, r0)     // Catch: java.lang.Throwable -> L97
        Ld4:
        Ld5:
            r12.close()     // Catch: java.io.IOException -> Ld9
            goto Ldb
        Ld9:
            r0 = move-exception
        Ldb:
            return
        Ldc:
            r12.close()     // Catch: java.io.IOException -> Le0
            goto Le1
        Le0:
            r2 = move-exception
        Le1:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.telephony.TelephonyManager.lambda$uploadCallComposerPicture$3(java.io.InputStream,"
                    + " android.os.OutcomeReceiver, android.os.ParcelFileDescriptor,"
                    + " java.io.OutputStream):void");
    }

    public String getGroupIdLevel1() {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getGroupIdLevel1ForSubscriber(
                    getSubId(),
                    this.mContext.getOpPackageName(),
                    this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getGroupIdLevel1(int subId) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getGroupIdLevel1ForSubscriber(
                    subId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @Deprecated
    public String getLine1Number() {
        return getLine1Number(getSubId());
    }

    public String getLine1Number(int subId) {
        String number = null;
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                number =
                        telephony.getLine1NumberForDisplay(
                                subId,
                                this.mContext.getOpPackageName(),
                                this.mContext.getAttributionTag());
            }
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
        if (number != null) {
            return number;
        }
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getLine1NumberForSubscriber(
                    subId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e3) {
            return null;
        } catch (NullPointerException e4) {
            return null;
        }
    }

    @Deprecated
    public boolean setLine1NumberForDisplay(String alphaTag, String number) {
        return setLine1NumberForDisplay(getSubId(), alphaTag, number);
    }

    public boolean setLine1NumberForDisplay(int subId, String alphaTag, String number) {
        try {
            this.mSubscriptionManager.setCarrierPhoneNumber(subId, number == null ? "" : number);
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.setLine1NumberForDisplayForSubscriber(subId, alphaTag, number);
            }
            return false;
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    public String getLine1AlphaTag() {
        return getLine1AlphaTag(getSubId());
    }

    public String getLine1AlphaTag(int subId) {
        String alphaTag = null;
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                alphaTag =
                        telephony.getLine1AlphaTagForDisplay(
                                subId, getOpPackageName(), getAttributionTag());
            }
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
        if (alphaTag != null) {
            return alphaTag;
        }
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getLine1AlphaTagForSubscriber(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e3) {
            return null;
        } catch (NullPointerException e4) {
            return null;
        }
    }

    @Deprecated
    public String[] getMergedSubscriberIds() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getMergedSubscriberIds(
                        getSubId(), getOpPackageName(), getAttributionTag());
            }
            return null;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @SystemApi
    public String[] getMergedImsisFromGroup() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                String[] mergedImsisFromGroup =
                        telephony.getMergedImsisFromGroup(getSubId(), getOpPackageName());
                if (mergedImsisFromGroup != null) {
                    return mergedImsisFromGroup;
                }
            }
        } catch (RemoteException e) {
        }
        return new String[0];
    }

    public String getMsisdn() {
        return getMsisdn(getSubId());
    }

    public String getMsisdn(int subId) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getMsisdnForSubscriber(subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getVoiceMailNumber() {
        return getVoiceMailNumber(getSubId());
    }

    public String getVoiceMailNumber(int subId) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getVoiceMailNumberForSubscriber(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public boolean setVoiceMailNumber(String alphaTag, String number) {
        return setVoiceMailNumber(getSubId(), alphaTag, number);
    }

    public boolean setVoiceMailNumber(int subId, String alphaTag, String number) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.setVoiceMailNumber(subId, alphaTag, number);
            }
            return false;
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public void setVisualVoicemailEnabled(PhoneAccountHandle phoneAccountHandle, boolean enabled) {}

    @SystemApi
    @Deprecated
    public boolean isVisualVoicemailEnabled(PhoneAccountHandle phoneAccountHandle) {
        return false;
    }

    @SystemApi
    public Bundle getVisualVoicemailSettings() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getVisualVoicemailSettings(
                        this.mContext.getOpPackageName(), this.mSubId);
            }
            return null;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getVisualVoicemailPackageName() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getVisualVoicemailPackageName(
                        this.mContext.getOpPackageName(), getAttributionTag(), getSubId());
            }
            return null;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public void setVisualVoicemailSmsFilterSettings(VisualVoicemailSmsFilterSettings settings) {
        if (settings == null) {
            disableVisualVoicemailSmsFilter(this.mSubId);
        } else {
            enableVisualVoicemailSmsFilter(this.mSubId, settings);
        }
    }

    public void sendVisualVoicemailSms(
            String number, int port, String text, PendingIntent sentIntent) {
        sendVisualVoicemailSmsForSubscriber(this.mSubId, number, port, text, sentIntent);
    }

    public void enableVisualVoicemailSmsFilter(
            int subId, VisualVoicemailSmsFilterSettings settings) {
        if (settings == null) {
            throw new IllegalArgumentException("Settings cannot be null");
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.enableVisualVoicemailSmsFilter(
                        this.mContext.getOpPackageName(), subId, settings);
            }
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    public void disableVisualVoicemailSmsFilter(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.disableVisualVoicemailSmsFilter(this.mContext.getOpPackageName(), subId);
            }
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    public VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getVisualVoicemailSmsFilterSettings(
                        this.mContext.getOpPackageName(), subId);
            }
            return null;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getActiveVisualVoicemailSmsFilterSettings(subId);
            }
            return null;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public void sendVisualVoicemailSmsForSubscriber(
            int subId, String number, int port, String text, PendingIntent sentIntent) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.sendVisualVoicemailSmsForSubscriber(
                        this.mContext.getOpPackageName(),
                        this.mContext.getAttributionTag(),
                        subId,
                        number,
                        port,
                        text,
                        sentIntent);
            }
        } catch (RemoteException e) {
        }
    }

    @SystemApi
    public void setVoiceActivationState(int activationState) {
        setVoiceActivationState(getSubId(), activationState);
    }

    public void setVoiceActivationState(int subId, int activationState) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setVoiceActivationState(subId, activationState);
            }
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    @SystemApi
    public void setDataActivationState(int activationState) {
        setDataActivationState(getSubId(), activationState);
    }

    public void setDataActivationState(int subId, int activationState) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setDataActivationState(subId, activationState);
            }
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    @SystemApi
    public int getVoiceActivationState() {
        return getVoiceActivationState(getSubId());
    }

    public int getVoiceActivationState(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getVoiceActivationState(subId, getOpPackageName());
            }
            return 0;
        } catch (RemoteException e) {
            return 0;
        } catch (NullPointerException e2) {
            return 0;
        }
    }

    @SystemApi
    public int getDataActivationState() {
        return getDataActivationState(getSubId());
    }

    public int getDataActivationState(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getDataActivationState(subId, getOpPackageName());
            }
            return 0;
        } catch (RemoteException e) {
            return 0;
        } catch (NullPointerException e2) {
            return 0;
        }
    }

    public int getVoiceMessageCount() {
        return getVoiceMessageCount(getSubId());
    }

    public int getVoiceMessageCount(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return 0;
            }
            return telephony.getVoiceMessageCountForSubscriber(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return 0;
        } catch (NullPointerException e2) {
            return 0;
        }
    }

    public int semGetVoiceMessageCount() {
        return getVoiceMessageCount();
    }

    public String getVoiceMailAlphaTag() {
        return getVoiceMailAlphaTag(getSubId());
    }

    public String getVoiceMailAlphaTag(int subId) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getVoiceMailAlphaTagForSubscriber(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public void sendDialerSpecialCode(String inputCode) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return;
            }
            telephony.sendDialerSpecialCode(this.mContext.getOpPackageName(), inputCode);
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "Telephony#sendDialerSpecialCode RemoteException" + ex);
        }
    }

    public String getIsimImpi() {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getIsimImpi(getSubId());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getImsPrivateUserIdentity() {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                com.android.telephony.Rlog.e(
                        TAG, "getImsPrivateUserIdentity(): IPhoneSubInfo instance is NULL");
                throw new RuntimeException("IMPI error: Subscriber Info is null");
            }
            return info.getImsPrivateUserIdentity(
                    getSubId(), getOpPackageName(), getAttributionTag());
        } catch (RemoteException | IllegalArgumentException | NullPointerException ex) {
            com.android.telephony.Rlog.e(TAG, "getImsPrivateUserIdentity() Exception = " + ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    @SystemApi
    public String getIsimDomain() {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getIsimDomain(getSubId());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String[] getIsimImpu() {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getIsimImpu(getSubId());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public String[] semGetIsimImpu() {
        return getIsimImpu();
    }

    public List<Uri> getImsPublicUserIdentities() {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                throw new RuntimeException("IMPU error: Subscriber Info is null");
            }
            return info.getImsPublicUserIdentities(
                    getSubId(), getOpPackageName(), getAttributionTag());
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getImsPublicUserIdentities Exception = " + ex);
            ex.rethrowAsRuntimeException();
            return Collections.EMPTY_LIST;
        } catch (IllegalArgumentException | NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "getImsPublicUserIdentities Exception = " + ex2);
            return Collections.EMPTY_LIST;
        }
    }

    @Deprecated
    public int getCallState() {
        TelecomManager telecomManager;
        if (this.mContext != null
                && (telecomManager =
                                (TelecomManager)
                                        this.mContext.getSystemService(TelecomManager.class))
                        != null) {
            return telecomManager.getCallState();
        }
        return 0;
    }

    public int getCallStateForSubscription() {
        return getCallState(getSubId());
    }

    public int getCallState(int subId) {
        ITelephony telephony = getITelephony();
        if (telephony == null) {
            return 0;
        }
        try {
            return telephony.getCallStateForSubscription(
                    subId, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            return 0;
        }
    }

    public int semGetCallState(int subId) {
        return getCallState(subId);
    }

    public int getDataActivity() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return 0;
            }
            return telephony.getDataActivityForSubId(
                    getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
        } catch (RemoteException e) {
            return 0;
        } catch (NullPointerException e2) {
            return 0;
        }
    }

    public int getDataState() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return 0;
            }
            int state =
                    telephony.getDataStateForSubId(
                            getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
            if (state == 4) {
                if (!Compatibility.isChangeEnabled(GET_DATA_STATE_R_VERSION)) {
                    return 2;
                }
            }
            return state;
        } catch (RemoteException e) {
            return 0;
        } catch (NullPointerException e2) {
            return 0;
        }
    }

    public int semGetDataState(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return 0;
            }
            int state = telephony.getDataStateForSubId(subId);
            if (state == 4) {
                if (!Compatibility.isChangeEnabled(GET_DATA_STATE_R_VERSION)) {
                    return 2;
                }
            }
            return state;
        } catch (RemoteException e) {
            return 0;
        } catch (NullPointerException e2) {
            return 0;
        }
    }

    public static ITelephony getITelephony() {
        if (!sServiceHandleCacheEnabled) {
            return ITelephony.Stub.asInterface(
                    TelephonyFrameworkInitializer.getTelephonyServiceManager()
                            .getTelephonyServiceRegisterer()
                            .get());
        }
        if (sITelephony == null) {
            ITelephony temp =
                    ITelephony.Stub.asInterface(
                            TelephonyFrameworkInitializer.getTelephonyServiceManager()
                                    .getTelephonyServiceRegisterer()
                                    .get());
            synchronized (sCacheLock) {
                if (sITelephony == null && temp != null) {
                    try {
                        sITelephony = temp;
                        sITelephony.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception e) {
                        sITelephony = null;
                    }
                }
            }
        }
        return sITelephony;
    }

    private ISemTelephony getISemTelephony() {
        if (!sServiceHandleCacheEnabled) {
            return ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
        }
        if (sISemTelephony == null) {
            ISemTelephony temp =
                    ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
            synchronized (sCacheLock) {
                if (sISemTelephony == null && temp != null) {
                    try {
                        sISemTelephony = temp;
                        sISemTelephony.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception e) {
                        sISemTelephony = null;
                    }
                }
            }
        }
        return sISemTelephony;
    }

    private IOns getIOns() {
        return IOns.Stub.asInterface(
                TelephonyFrameworkInitializer.getTelephonyServiceManager()
                        .getOpportunisticNetworkServiceRegisterer()
                        .get());
    }

    @Deprecated
    public void listen(PhoneStateListener listener, int events) {
        if (this.mContext == null) {
            return;
        }
        boolean notifyNow = getITelephony() != null;
        TelephonyRegistryManager telephonyRegistry =
                (TelephonyRegistryManager)
                        this.mContext.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistry != null) {
            Set<String> renouncedPermissions = getRenouncedPermissions();
            boolean renounceFineLocationAccess =
                    renouncedPermissions.contains(Manifest.permission.ACCESS_FINE_LOCATION);
            boolean renounceCoarseLocationAccess =
                    renouncedPermissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION);
            telephonyRegistry.listenFromListener(
                    this.mSubId,
                    renounceFineLocationAccess,
                    renounceCoarseLocationAccess,
                    getOpPackageName(),
                    getAttributionTag(),
                    listener,
                    events,
                    notifyNow);
            return;
        }
        com.android.telephony.Rlog.w(TAG, "telephony registry not ready.");
    }

    @SystemApi
    public int getCdmaEnhancedRoamingIndicatorDisplayNumber() {
        return getCdmaEriIconIndex(getSubId());
    }

    public int getCdmaEriIconIndex(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return -1;
            }
            return telephony.getCdmaEriIconIndexForSubscriber(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return -1;
        } catch (NullPointerException e2) {
            return -1;
        }
    }

    public int getCdmaEriIconMode(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return -1;
            }
            return telephony.getCdmaEriIconModeForSubscriber(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return -1;
        } catch (NullPointerException e2) {
            return -1;
        }
    }

    public String getCdmaEriText() {
        return getCdmaEriText(getSubId());
    }

    public String getCdmaEriText(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return null;
            }
            return telephony.getCdmaEriTextForSubscriber(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @Deprecated
    public boolean isVoiceCapable() {
        if (this.mContext == null) {
            return true;
        }
        return this.mContext.getResources().getBoolean(R.bool.config_voice_capable);
    }

    public boolean isDeviceVoiceCapable() {
        return isVoiceCapable();
    }

    public boolean isSmsCapable() {
        ActivityManager am;
        ComponentName topActivity;
        String mccmnc;
        if (this.mContext == null) {
            return true;
        }
        boolean isNoSmsTablet = false;
        if (TelephonyFeatures.IS_TABLET) {
            if ("XAA".equals(SystemProperties.get("ro.boot.carrierid", ""))
                    || "N14".equals(SystemProperties.get("ro.boot.carrierid", ""))
                    || "VZW".equals(TelephonyFeatures.getSalesCode())
                    || "USC".equals(TelephonyFeatures.getSalesCode())
                    || "ATT".equals(TelephonyFeatures.getSalesCode())
                    || "CHA".equals(TelephonyFeatures.getSalesCode())
                    || "CCT".equals(TelephonyFeatures.getSalesCode())
                    || "DSA".equals(TelephonyFeatures.getSalesCode())
                    || "DSG".equals(TelephonyFeatures.getSalesCode())
                    || "DSH".equals(TelephonyFeatures.getSalesCode())
                    || "TFN".equals(TelephonyFeatures.getSalesCode())) {
                isNoSmsTablet = true;
            } else if (TelephonyFeatures.isSubOperatorSpecific(
                            getPhoneId(),
                            "VZW",
                            "USC",
                            "ATT",
                            "CHA",
                            "CCT",
                            "DSA",
                            "DSG",
                            "DSH",
                            "TFN")
                    && (mccmnc = getSimOperator()) != null
                    && mccmnc.length() > 4) {
                String mcc = mccmnc.substring(0, 3);
                String[] USAmcc = {"310", "311", "312", "313", "314", "315", "316"};
                isNoSmsTablet = Arrays.asList(USAmcc).contains(mcc);
            }
        }
        if (isNoSmsTablet) {
            com.android.telephony.Rlog.d(TAG, "US No SMS Tablet Model");
            if (!"ATT".equals(TelephonyFeatures.getSubOperatorName(getPhoneId()))
                    || (am = (ActivityManager) this.mContext.getSystemService("activity")) == null
                    || !ActivityManager.isSystemReady()) {
                return false;
            }
            List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
            if (runningTasks.isEmpty() || (topActivity = runningTasks.get(0).topActivity) == null) {
                return false;
            }
            com.android.telephony.Rlog.d(
                    TAG, "getTopPackageName = " + topActivity.getPackageName());
            return "com.android.vending".equals(topActivity.getPackageName());
        }
        return this.mContext.getResources().getBoolean(R.bool.config_sms_capable);
    }

    public boolean setDefaultSmsApplicationByForce(String packageName) {
        com.android.telephony.Rlog.i(
                TAG, "setDefaultSmsApplicationByForce is called. package = " + packageName);
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return false;
            }
            return info.setDefaultSmsApplicationByForce(packageName);
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "setDefaultSmsApplicationByForce is fail due to RemoteException. " + ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG,
                    "setDefaultSmsApplicationByForce is fail due to NullPointerException. " + ex2);
            return false;
        }
    }

    public boolean isDeviceSmsCapable() {
        return isSmsCapable();
    }

    public List<CellInfo> getAllCellInfo() {
        logWithCallerInfo("getAllCellInfo");
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return null;
            }
            return telephony.getAllCellInfo(getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException e) {
            return null;
        }
    }

    public List<CellInfo> getAllCellInfoBySubId(int subId) {
        logWithCallerInfo("getAllCellInfoBySubId");
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony == null) {
                com.android.telephony.Rlog.d(
                        TAG,
                        "getAllCellInfoBySubId returning null because SemTelephonyService is null");
                return null;
            }
            return semTelephony.getAllCellInfoBySubId(
                    subId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.d(
                    TAG, "getAllCellInfoBySubId is fail due to RemoteException. " + ex);
            return null;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.d(
                    TAG, "getAllCellInfoBySubId is fail due to NullPointerException. " + ex2);
            return null;
        }
    }

    public List<CellInfo> getAllCellInfoForPhone(int phoneId) {
        logWithCallerInfo("getAllCellInfoForPhone");
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony == null) {
                com.android.telephony.Rlog.d(
                        TAG,
                        "getAllCellInfoForPhone returning null because SemTelephonyService is"
                            + " null");
                return null;
            }
            return semTelephony.getAllCellInfoForPhone(
                    phoneId, getOpPackageName(), getAttributionTag());
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.d(
                    TAG, "getAllCellInfoForPhone is fail due to RemoteException. " + ex);
            return null;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.d(
                    TAG, "getAllCellInfoForPhone is fail due to NullPointerException. " + ex2);
            return null;
        }
    }

    public abstract static class CellInfoCallback {
        public static final int ERROR_MODEM_ERROR = 2;
        public static final int ERROR_TIMEOUT = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CellInfoCallbackError {}

        public abstract void onCellInfo(List<CellInfo> list);

        public void onError(int errorCode, Throwable detail) {
            onCellInfo(new ArrayList());
        }
    }

    public void requestCellInfoUpdate(final Executor executor, final CellInfoCallback callback) {
        logWithCallerInfo("requestCellInfoUpdate");
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Telephony is null");
                }
            } else {
                telephony.requestCellInfoUpdate(
                        getSubId(),
                        new AnonymousClass4(executor, callback),
                        getOpPackageName(),
                        getAttributionTag());
            }
        } catch (RemoteException ex) {
            runOnBackgroundThread(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda18
                        @Override // java.lang.Runnable
                        public final void run() {
                            executor.execute(
                                    new Runnable() { // from class:
                                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda25
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            TelephonyManager.CellInfoCallback.this.onError(2, r2);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$4, reason: invalid class name */
    class AnonymousClass4 extends ICellInfoCallback.Stub {
        final /* synthetic */ CellInfoCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass4(Executor executor, CellInfoCallback cellInfoCallback) {
            this.val$executor = executor;
            this.val$callback = cellInfoCallback;
        }

        @Override // android.telephony.ICellInfoCallback
        public void onCellInfo(final List<CellInfo> cellInfo) {
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$4$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyManager.CellInfoCallback.this.onCellInfo(cellInfo);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        @Override // android.telephony.ICellInfoCallback
        public void onError(final int errorCode, final String exceptionName, final String message) {
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$4$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyManager.CellInfoCallback.this.onError(
                                        errorCode,
                                        TelephonyManager.createThrowableByClassName(
                                                exceptionName, message));
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    @SystemApi
    public void requestCellInfoUpdate(
            WorkSource workSource, final Executor executor, final CellInfoCallback callback) {
        logWithCallerInfo("requestCellInfoUpdate");
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Telephony is null");
                }
            } else {
                telephony.requestCellInfoUpdateWithWorkSource(
                        getSubId(),
                        new AnonymousClass5(executor, callback),
                        getOpPackageName(),
                        getAttributionTag(),
                        workSource);
            }
        } catch (RemoteException ex) {
            runOnBackgroundThread(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda20
                        @Override // java.lang.Runnable
                        public final void run() {
                            executor.execute(
                                    new Runnable() { // from class:
                                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda24
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            TelephonyManager.CellInfoCallback.this.onError(2, r2);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$5, reason: invalid class name */
    class AnonymousClass5 extends ICellInfoCallback.Stub {
        final /* synthetic */ CellInfoCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass5(Executor executor, CellInfoCallback cellInfoCallback) {
            this.val$executor = executor;
            this.val$callback = cellInfoCallback;
        }

        @Override // android.telephony.ICellInfoCallback
        public void onCellInfo(final List<CellInfo> cellInfo) {
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$5$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyManager.CellInfoCallback.this.onCellInfo(cellInfo);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        @Override // android.telephony.ICellInfoCallback
        public void onError(final int errorCode, final String exceptionName, final String message) {
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$5$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyManager.CellInfoCallback.this.onError(
                                        errorCode,
                                        TelephonyManager.createThrowableByClassName(
                                                exceptionName, message));
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    public static Throwable createThrowableByClassName(String className, String message) {
        if (className == null) {
            return null;
        }
        try {
            Class<?> c = Class.forName(className);
            return (Throwable) c.getConstructor(String.class).newInstance(message);
        } catch (ClassCastException | ReflectiveOperationException e) {
            return new RuntimeException(className + ": " + message);
        }
    }

    public void setCellInfoListRate(int rateInMillis, int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setCellInfoListRate(rateInMillis, subId);
            }
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    public String getMmsUserAgent() {
        String sUserAgent = "SAMSUNG-ANDROID-MMS/" + Build.MODEL;
        String ua = null;
        SemCarrierFeature carrierFeature = SemCarrierFeature.getInstance();
        int phoneId = SubscriptionManager.getPhoneId(getSubId());
        String mno =
                SmsManager.getSmsManagerForContextAndSubscriptionId(this.mContext, getSubId())
                        .getMnoName()
                        .toUpperCase();
        if (mno.contains("GENERICIR92_US")) {
            ua =
                    Settings.System.getString(
                            this.mContext.getContentResolver(), Settings.System.MMS_USER_AGENT);
        }
        if (TextUtils.isEmpty(ua)) {
            ua =
                    carrierFeature.getString(
                            getPhoneId(), "CarrierFeature_Message_UserAgent", "", true);
        }
        if (TextUtils.isEmpty(ua)) {
            String mccmnc = getSimOperator();
            String gid1 = getGroupIdLevel1();
            ua = getValuefromCSC("MessageUserAgent", mccmnc, gid1, phoneId);
            com.android.telephony.Rlog.i(TAG, "Customer.xml userAgent:" + ua);
        }
        String model = Build.MODEL;
        if ("ggsm-tmo-ua"
                .equals(
                        carrierFeature.getString(
                                getPhoneId(),
                                "CarrierFeature_Message_EnableMmsUaUapAutoCreate",
                                "",
                                true))) {
            StringBuffer buffer = new StringBuffer();
            if (model.length() > 0) {
                buffer.append(model);
            }
            buffer.append('-');
            String Incremental = Build.VERSION.INCREMENTAL;
            if (Incremental.length() > 0) {
                buffer.append(Incremental);
            }
            ua = String.format("SAMSUNG-ANDROID-MMS/%s", buffer);
        }
        if (!TextUtils.isEmpty(ua)) {
            sUserAgent = ua;
        }
        String version = SystemProperties.get("ro.build.version.incremental");
        if (mno.contains("ATT_US")) {
            PackageManager pm = this.mContext.getPackageManager();
            String msgAppVersion = "";
            try {
                if (SmsApplication.isDefaultSmsApplication(
                        this.mContext, "com.google.android.apps.messaging")) {
                    PackageInfo pkgInfo =
                            pm.getPackageInfo("com.google.android.apps.messaging", 128);
                    msgAppVersion = pkgInfo.versionName;
                }
            } catch (PackageManager.NameNotFoundException e) {
                com.android.telephony.Rlog.e(
                        TAG, "com.google.android.apps.messaging package NameNotFoundException");
            }
            if (!model.contains("SAMSUNG")) {
                model = "SAMSUNG-" + Build.MODEL;
            }
            if (TextUtils.isEmpty(msgAppVersion)) {
                sUserAgent = model + "/" + version + " Mozilla/5.0 SMM-MMS/1.2.0";
            } else {
                sUserAgent =
                        model + "/" + version + "/" + msgAppVersion + " Mozilla/5.0 SMM-MMS/1.2.0";
            }
        } else if (mno.contains("SPRINT_US")) {
            String resellerID = SemSystemProperties.get("ro.home.operator.carrierid");
            sUserAgent = model + " " + resellerID.toUpperCase();
        }
        if (sUserAgent != null) {
            return sUserAgent;
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getMmsUserAgent(getSubId());
            }
            return null;
        } catch (RemoteException e2) {
            return null;
        } catch (NullPointerException e3) {
            return null;
        }
    }

    public String getMmsUAProfUrl() {
        String sUaProfUrl = "http://wap.samsungmobile.com/uaprof/SAMSUNGUAPROF.xml";
        String uapUrl = null;
        SemCarrierFeature carrierFeature = SemCarrierFeature.getInstance();
        int phoneId = SubscriptionManager.getPhoneId(getSubId());
        String mno =
                SmsManager.getSmsManagerForContextAndSubscriptionId(this.mContext, getSubId())
                        .getMnoName()
                        .toUpperCase();
        com.android.telephony.Rlog.i(TAG, "Mno = " + mno + " phoneId: " + phoneId);
        if (mno.contains("GENERICIR92_US")) {
            uapUrl =
                    Settings.System.getString(
                            this.mContext.getContentResolver(),
                            Settings.System.MMS_X_WAP_PROFILE_URL);
        }
        if (TextUtils.isEmpty(uapUrl)) {
            uapUrl =
                    carrierFeature.getString(
                            getPhoneId(), "CarrierFeature_Message_UaProfUrl", "", true);
            com.android.telephony.Rlog.i(TAG, "carrierFeature uapUrl:" + uapUrl);
        }
        if (TextUtils.isEmpty(uapUrl)) {
            String mccmnc = getSimOperator();
            String gid1 = getGroupIdLevel1();
            uapUrl = getValuefromCSC("MessageUaProfUrl", mccmnc, gid1, phoneId);
            com.android.telephony.Rlog.i(TAG, "Customer.xml uapUrl:" + uapUrl);
        }
        if (!TextUtils.isEmpty(uapUrl)) {
            sUaProfUrl = uapUrl;
        }
        if (mno.contains("SPRINT_US")) {
            String uapUrl2 = null;
            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(this.mContext);
            String version = SystemProperties.get("ro.build.version.incremental");
            if (version.length() > 3) {
                version = version.substring(version.length() - 3);
            }
            if (sharedPrefs.contains("pref_key_browser_uaprofurl")) {
                uapUrl2 = sharedPrefs.getString("pref_key_browser_uaprofurl", "");
            }
            if (TextUtils.isEmpty(uapUrl2)) {
                String wapUrl = SemSystemProperties.get("ro.device.wapprofile.url");
                if (!TextUtils.isEmpty(wapUrl)) {
                    sUaProfUrl = wapUrl;
                } else {
                    String resellerID = SemSystemProperties.get("ro.home.operator.carrierid");
                    sUaProfUrl =
                            "http://device.sprintpcs.com/Samsung/"
                                    + Build.MODEL
                                    + NativeLibraryHelper.CLEAR_ABI_OVERRIDE
                                    + resellerID.toUpperCase()
                                    + "/"
                                    + version
                                    + ".rdf";
                }
            } else {
                sUaProfUrl = uapUrl2 + version + ".rdf";
            }
        }
        if (sUaProfUrl != null) {
            com.android.telephony.Rlog.i("MMS_UA" + phoneId, "sUaProfUrl:" + sUaProfUrl);
            return sUaProfUrl;
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getMmsUAProfUrl(getSubId());
            }
            return null;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    private int getFirstActivePortIndex(int physicalSlotIndex) {
        UiccSlotInfo[] slotInfos = getUiccSlotsInfo();
        if (slotInfos != null
                && physicalSlotIndex >= 0
                && physicalSlotIndex < slotInfos.length
                && slotInfos[physicalSlotIndex] != null) {
            Optional<UiccPortInfo> result =
                    slotInfos[physicalSlotIndex].getPorts().stream()
                            .filter(
                                    new Predicate() { // from class:
                                                      // android.telephony.TelephonyManager$$ExternalSyntheticLambda21
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            boolean isActive;
                                            isActive = ((UiccPortInfo) obj).isActive();
                                            return isActive;
                                        }
                                    })
                            .findFirst();
            if (result.isPresent()) {
                return result.get().getPortIndex();
            }
            return -1;
        }
        return -1;
    }

    @Deprecated
    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(String AID) {
        return iccOpenLogicalChannel(getSubId(), AID, -1);
    }

    @SystemApi
    @Deprecated
    public IccOpenLogicalChannelResponse iccOpenLogicalChannelBySlot(
            int slotIndex, String aid, int p2) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IccLogicalChannelRequest request = new IccLogicalChannelRequest();
                request.slotIndex = slotIndex;
                request.portIndex = getFirstActivePortIndex(slotIndex);
                request.aid = aid;
                request.p2 = p2;
                request.callingPackage = getOpPackageName();
                request.binder = new Binder();
                return telephony.iccOpenLogicalChannel(request);
            }
            return null;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @SystemApi
    public IccOpenLogicalChannelResponse iccOpenLogicalChannelByPort(
            int slotIndex, int portIndex, String aid, int p2) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IccLogicalChannelRequest request = new IccLogicalChannelRequest();
                request.slotIndex = slotIndex;
                request.portIndex = portIndex;
                request.aid = aid;
                request.p2 = p2;
                request.callingPackage = getOpPackageName();
                request.binder = new Binder();
                return telephony.iccOpenLogicalChannel(request);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            throw ex.rethrowAsRuntimeException();
        }
    }

    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(String AID, int p2) {
        return iccOpenLogicalChannel(getSubId(), AID, p2);
    }

    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(int subId, String AID, int p2) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IccLogicalChannelRequest request = new IccLogicalChannelRequest();
                request.subId = subId;
                request.callingPackage = getOpPackageName();
                request.aid = AID;
                request.p2 = p2;
                request.binder = new Binder();
                return telephony.iccOpenLogicalChannel(request);
            }
            return null;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @SystemApi
    @Deprecated
    public boolean iccCloseLogicalChannelBySlot(int slotIndex, int channel) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IccLogicalChannelRequest request = new IccLogicalChannelRequest();
                request.slotIndex = slotIndex;
                request.portIndex = getFirstActivePortIndex(slotIndex);
                request.channel = channel;
                return telephony.iccCloseLogicalChannel(request);
            }
            return false;
        } catch (RemoteException e) {
            return false;
        } catch (IllegalStateException ex) {
            com.android.telephony.Rlog.e(TAG, "iccCloseLogicalChannel IllegalStateException", ex);
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    @SystemApi
    public void iccCloseLogicalChannelByPort(int slotIndex, int portIndex, int channel) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IccLogicalChannelRequest request = new IccLogicalChannelRequest();
                request.slotIndex = slotIndex;
                request.portIndex = portIndex;
                request.channel = channel;
                telephony.iccCloseLogicalChannel(request);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            throw ex.rethrowAsRuntimeException();
        }
    }

    public boolean iccCloseLogicalChannel(int channel) {
        try {
            return iccCloseLogicalChannel(getSubId(), channel);
        } catch (IllegalStateException ex) {
            com.android.telephony.Rlog.e(TAG, "iccCloseLogicalChannel IllegalStateException", ex);
            return false;
        }
    }

    public boolean iccCloseLogicalChannel(int subId, int channel) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IccLogicalChannelRequest request = new IccLogicalChannelRequest();
                request.subId = subId;
                request.channel = channel;
                return telephony.iccCloseLogicalChannel(request);
            }
            return false;
        } catch (RemoteException e) {
            return false;
        } catch (IllegalStateException ex) {
            com.android.telephony.Rlog.e(TAG, "iccCloseLogicalChannel IllegalStateException", ex);
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public String iccTransmitApduLogicalChannelBySlot(
            int slotIndex,
            int channel,
            int cla,
            int instruction,
            int p1,
            int p2,
            int p3,
            String data) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.iccTransmitApduLogicalChannelByPort(
                        slotIndex,
                        getFirstActivePortIndex(slotIndex),
                        channel,
                        cla,
                        instruction,
                        p1,
                        p2,
                        p3,
                        data);
            }
            return null;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @SystemApi
    public String iccTransmitApduLogicalChannelByPort(
            int slotIndex,
            int portIndex,
            int channel,
            int cla,
            int instruction,
            int p1,
            int p2,
            int p3,
            String data) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                String response =
                        telephony.iccTransmitApduLogicalChannelByPort(
                                slotIndex, portIndex, channel, cla, instruction, p1, p2, p3, data);
                return response;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            throw ex.rethrowAsRuntimeException();
        }
    }

    public String iccTransmitApduLogicalChannel(
            int channel, int cla, int instruction, int p1, int p2, int p3, String data) {
        return iccTransmitApduLogicalChannel(
                getSubId(), channel, cla, instruction, p1, p2, p3, data);
    }

    public String iccTransmitApduLogicalChannel(
            int subId, int channel, int cla, int instruction, int p1, int p2, int p3, String data) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.iccTransmitApduLogicalChannel(
                        subId, channel, cla, instruction, p1, p2, p3, data);
            }
            return "";
        } catch (RemoteException e) {
            return "";
        } catch (NullPointerException e2) {
            return "";
        }
    }

    @SystemApi
    @Deprecated
    public String iccTransmitApduBasicChannelBySlot(
            int slotIndex, int cla, int instruction, int p1, int p2, int p3, String data) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.iccTransmitApduBasicChannelByPort(
                        slotIndex,
                        getFirstActivePortIndex(slotIndex),
                        getOpPackageName(),
                        cla,
                        instruction,
                        p1,
                        p2,
                        p3,
                        data);
            }
            return null;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @SystemApi
    public String iccTransmitApduBasicChannelByPort(
            int slotIndex,
            int portIndex,
            int cla,
            int instruction,
            int p1,
            int p2,
            int p3,
            String data) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                String response =
                        telephony.iccTransmitApduBasicChannelByPort(
                                slotIndex,
                                portIndex,
                                getOpPackageName(),
                                cla,
                                instruction,
                                p1,
                                p2,
                                p3,
                                data);
                return response;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            throw ex.rethrowAsRuntimeException();
        }
    }

    public String iccTransmitApduBasicChannel(
            int cla, int instruction, int p1, int p2, int p3, String data) {
        return iccTransmitApduBasicChannel(getSubId(), cla, instruction, p1, p2, p3, data);
    }

    public String iccTransmitApduBasicChannel(
            int subId, int cla, int instruction, int p1, int p2, int p3, String data) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.iccTransmitApduBasicChannel(
                        subId, getOpPackageName(), cla, instruction, p1, p2, p3, data);
            }
            return "";
        } catch (RemoteException e) {
            return "";
        } catch (NullPointerException e2) {
            return "";
        }
    }

    public byte[] iccExchangeSimIO(
            int fileID, int command, int p1, int p2, int p3, String filePath) {
        return iccExchangeSimIO(getSubId(), fileID, command, p1, p2, p3, filePath);
    }

    public byte[] iccExchangeSimIO(
            int subId, int fileID, int command, int p1, int p2, int p3, String filePath) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.iccExchangeSimIO(subId, fileID, command, p1, p2, p3, filePath);
            }
            return null;
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String sendEnvelopeWithStatus(String content) {
        return sendEnvelopeWithStatus(getSubId(), content);
    }

    public String sendEnvelopeWithStatus(int subId, String content) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.sendEnvelopeWithStatus(subId, content);
            }
            return "";
        } catch (RemoteException e) {
            return "";
        } catch (NullPointerException e2) {
            return "";
        }
    }

    public String nvReadItem(int itemID) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.nvReadItem(itemID);
            }
            return "";
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "nvReadItem RemoteException", ex);
            return "";
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "nvReadItem NPE", ex2);
            return "";
        }
    }

    public boolean nvWriteItem(int itemID, String itemValue) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.nvWriteItem(itemID, itemValue);
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "nvWriteItem RemoteException", ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "nvWriteItem NPE", ex2);
            return false;
        }
    }

    public boolean nvWriteCdmaPrl(byte[] preferredRoamingList) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.nvWriteCdmaPrl(preferredRoamingList);
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "nvWriteCdmaPrl RemoteException", ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "nvWriteCdmaPrl NPE", ex2);
            return false;
        }
    }

    public boolean nvResetConfig(int resetType) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (resetType == 1) {
                    return telephony.rebootModem(getSlotIndex());
                }
                if (resetType != 3) {
                    com.android.telephony.Rlog.e(TAG, "nvResetConfig unsupported reset type");
                    return false;
                }
                return telephony.resetModemConfig(getSlotIndex());
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "nvResetConfig RemoteException", ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "nvResetConfig NPE", ex2);
            return false;
        }
    }

    @SystemApi
    public boolean resetRadioConfig() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.resetModemConfig(getSlotIndex());
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "resetRadioConfig RemoteException", ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "resetRadioConfig NPE", ex2);
            return false;
        }
    }

    @SystemApi
    public boolean rebootRadio() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.rebootModem(getSlotIndex());
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "rebootRadio RemoteException", ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "rebootRadio NPE", ex2);
            return false;
        }
    }

    public void rebootModem() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            telephony.rebootModem(getSlotIndex());
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "rebootRadio RemoteException", ex);
            throw ex.rethrowAsRuntimeException();
        }
    }

    public int getSubscriptionId() {
        return getSubId();
    }

    private int getSubId() {
        if (SubscriptionManager.isUsableSubIdValue(this.mSubId)) {
            return this.mSubId;
        }
        return SubscriptionManager.getDefaultSubscriptionId();
    }

    private int getSubId(int preferredSubId) {
        if (SubscriptionManager.isUsableSubIdValue(this.mSubId)) {
            return this.mSubId;
        }
        return preferredSubId;
    }

    private int getPhoneId() {
        return SubscriptionManager.getPhoneId(getSubId());
    }

    private int getPhoneId(int preferredSubId) {
        return SubscriptionManager.getPhoneId(getSubId(preferredSubId));
    }

    public int getSlotIndex() {
        int slotIndex = SubscriptionManager.getSlotIndex(getSubId());
        if (slotIndex == -1) {
            return Integer.MAX_VALUE;
        }
        return slotIndex;
    }

    @SystemApi
    public void requestNumberVerification(
            PhoneNumberRange range,
            long timeoutMillis,
            final Executor executor,
            final NumberVerificationCallback callback) {
        if (executor == null) {
            throw new NullPointerException("Executor must be non-null");
        }
        if (callback == null) {
            throw new NullPointerException("Callback must be non-null");
        }
        INumberVerificationCallback internalCallback = new AnonymousClass6(executor, callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Telephony is null");
                }
            } else {
                telephony.requestNumberVerification(
                        range, timeoutMillis, internalCallback, getOpPackageName());
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "requestNumberVerification RemoteException", ex);
            runOnBackgroundThread(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda19
                        @Override // java.lang.Runnable
                        public final void run() {
                            executor.execute(
                                    new Runnable() { // from class:
                                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda11
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            NumberVerificationCallback.this.onVerificationFailed(0);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$6, reason: invalid class name */
    class AnonymousClass6 extends INumberVerificationCallback.Stub {
        final /* synthetic */ NumberVerificationCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass6(Executor executor, NumberVerificationCallback numberVerificationCallback) {
            this.val$executor = executor;
            this.val$callback = numberVerificationCallback;
        }

        @Override // com.android.internal.telephony.INumberVerificationCallback
        public void onCallReceived(final String phoneNumber) {
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final NumberVerificationCallback numberVerificationCallback = this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$6$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                NumberVerificationCallback.this.onCallReceived(phoneNumber);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        @Override // com.android.internal.telephony.INumberVerificationCallback
        public void onVerificationFailed(final int reason) {
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final NumberVerificationCallback numberVerificationCallback = this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$6$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                NumberVerificationCallback.this.onVerificationFailed(reason);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    private static <T> List<T> updateTelephonyProperty(List<T> prop, int phoneId, T value) {
        List<T> ret = new ArrayList<>(prop);
        while (ret.size() <= phoneId) {
            ret.add(null);
        }
        ret.set(phoneId, value);
        return ret;
    }

    public static int getIntAtIndex(ContentResolver cr, String name, int index)
            throws Settings.SettingNotFoundException {
        String v = Settings.Global.getString(cr, name);
        if (v != null) {
            String[] valArray = v.split(",");
            if (index >= 0 && index < valArray.length && valArray[index] != null) {
                try {
                    return Integer.parseInt(valArray[index]);
                } catch (NumberFormatException e) {
                }
            }
        }
        throw new Settings.SettingNotFoundException(name);
    }

    public static boolean putIntAtIndex(ContentResolver cr, String name, int index, int value) {
        String data = "";
        String[] valArray = null;
        String v = Settings.Global.getString(cr, name);
        if (index == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("putIntAtIndex index == MAX_VALUE index=" + index);
        }
        if (index < 0) {
            throw new IllegalArgumentException("putIntAtIndex index < 0 index=" + index);
        }
        if (v != null) {
            valArray = v.split(",");
        }
        for (int i = 0; i < index; i++) {
            String str = "";
            if (valArray != null && i < valArray.length) {
                str = valArray[i];
            }
            data = data + str + ",";
        }
        String data2 = data + value;
        if (valArray != null) {
            for (int i2 = index + 1; i2 < valArray.length; i2++) {
                data2 = data2 + "," + valArray[i2];
            }
        }
        return Settings.Global.putString(cr, name, data2);
    }

    public static String getTelephonyProperty(int phoneId, String property, String defaultVal) {
        String propVal = null;
        String prop = SystemProperties.get(property);
        if (prop != null && prop.length() > 0) {
            String[] values = prop.split(",");
            if (phoneId >= 0 && phoneId < values.length && values[phoneId] != null) {
                propVal = values[phoneId];
            }
        }
        return propVal == null ? defaultVal : propVal;
    }

    public static String semGetTelephonyProperty(int phoneId, String property, String defaultVal) {
        String convertedProp = SemTelephonyUtils.getKorDomesticPropForDS(property, phoneId);
        if (convertedProp != null) {
            return getTelephonyProperty(convertedProp, defaultVal);
        }
        return getTelephonyProperty(phoneId, property, defaultVal);
    }

    private static <T> T getTelephonyProperty(int phoneId, List<T> prop, T defaultValue) {
        T ret = null;
        if (phoneId >= 0 && phoneId < prop.size()) {
            ret = prop.get(phoneId);
        }
        return ret != null ? ret : defaultValue;
    }

    public static String getTelephonyProperty(String property, String defaultVal) {
        String propVal = SystemProperties.get(property);
        return TextUtils.isEmpty(propVal) ? defaultVal : propVal;
    }

    public int getSimCount() {
        return getPhoneCount();
    }

    @SystemApi
    public String getIsimIst() {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getIsimIst(getSubId());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String[] getIsimPcscf() {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getIsimPcscf(getSubId());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getIccAuthentication(int appType, int authType, String data) {
        return getIccAuthentication(getSubId(), appType, authType, data);
    }

    public String getIccAuthentication(int subId, int appType, int authType, String data) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getIccSimChallengeResponse(
                    subId, appType, authType, data, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String[] getForbiddenPlmns() {
        return getForbiddenPlmns(getSubId(), 2);
    }

    public String[] getForbiddenPlmns(int subId, int appType) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return null;
            }
            return telephony.getForbiddenPlmns(
                    subId, appType, this.mContext.getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public int setForbiddenPlmns(List<String> fplmns) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return -1;
            }
            return telephony.setForbiddenPlmns(
                    getSubId(), 2, fplmns, getOpPackageName(), getAttributionTag());
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "setForbiddenPlmns RemoteException: " + ex.getMessage());
            return -1;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "setForbiddenPlmns NullPointerException: " + ex2.getMessage());
            return -1;
        }
    }

    public String getSimServiceTable(int appType) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                com.android.telephony.Rlog.e(TAG, "getSimServiceTable(): IPhoneSubInfo is null");
                return null;
            }
            if (appType == 5) {
                return info.getIsimIst(getSubId());
            }
            if (appType == 2) {
                return info.getSimServiceTable(getSubId(), 2);
            }
            return null;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "getSimServiceTable(): RemoteException=" + ex.getMessage());
            return null;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "getSimServiceTable(): NullPointerException=" + ex2.getMessage());
            return null;
        }
    }

    @SystemApi
    public void resetIms(int slotIndex) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.resetIms(slotIndex);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "toggleImsOnOff, RemoteException: " + e.getMessage());
        }
    }

    public void enableIms(int slotId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.enableIms(slotId);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "enableIms, RemoteException: " + e.getMessage());
        }
    }

    public void disableIms(int slotId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.disableIms(slotId);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "disableIms, RemoteException: " + e.getMessage());
        }
    }

    public IImsRegistration getImsRegistration(int slotIndex, int feature) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getImsRegistration(slotIndex, feature);
            }
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(
                    TAG, "getImsRegistration, RemoteException: " + e.getMessage());
            return null;
        }
    }

    public IImsConfig getImsConfig(int slotIndex, int feature) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getImsConfig(slotIndex, feature);
            }
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(
                    TAG, "getImsRegistration, RemoteException: " + e.getMessage());
            return null;
        }
    }

    public void setImsRegistrationState(boolean registered) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setImsRegistrationState(registered);
            }
        } catch (RemoteException e) {
        }
    }

    @Deprecated
    public int getPreferredNetworkType(int subId) {
        return RadioAccessFamily.getNetworkTypeFromRaf((int) getAllowedNetworkTypesBitmask());
    }

    @SystemApi
    @Deprecated
    public long getPreferredNetworkTypeBitmask() {
        return getAllowedNetworkTypesBitmask();
    }

    @SystemApi
    public long getAllowedNetworkTypesBitmask() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getAllowedNetworkTypesBitmask(getSubId());
            }
            return 0L;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getAllowedNetworkTypesBitmask RemoteException", ex);
            return 0L;
        }
    }

    @SystemApi
    @Deprecated
    public long getAllowedNetworkTypes() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getAllowedNetworkTypesForReason(getSubId(), 2);
            }
            return -1L;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getAllowedNetworkTypes RemoteException", ex);
            return -1L;
        }
    }

    public void setNetworkSelectionModeAutomatic() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setNetworkSelectionModeAutomatic(getSubId());
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "setNetworkSelectionModeAutomatic RemoteException", ex);
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "setNetworkSelectionModeAutomatic NPE", ex2);
        }
    }

    public CellNetworkScanResult getAvailableNetworks() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getCellNetworkScanResults(
                        getSubId(), getOpPackageName(), getAttributionTag());
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getAvailableNetworks RemoteException", ex);
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "getAvailableNetworks NPE", ex2);
        }
        return new CellNetworkScanResult(4, (List<OperatorInfo>) null);
    }

    public NetworkScan requestNetworkScan(
            NetworkScanRequest request,
            Executor executor,
            TelephonyScanManager.NetworkScanCallback callback) {
        return requestNetworkScan(2, request, executor, callback);
    }

    public NetworkScan requestNetworkScan(
            int includeLocationData,
            NetworkScanRequest request,
            Executor executor,
            TelephonyScanManager.NetworkScanCallback callback) {
        synchronized (sCacheLock) {
            if (this.mTelephonyScanManager == null) {
                this.mTelephonyScanManager = new TelephonyScanManager();
            }
        }
        return this.mTelephonyScanManager.requestNetworkScan(
                getSubId(),
                includeLocationData != 2,
                request,
                executor,
                callback,
                getOpPackageName(),
                getAttributionTag());
    }

    @Deprecated
    public NetworkScan requestNetworkScan(
            NetworkScanRequest request, TelephonyScanManager.NetworkScanCallback callback) {
        return requestNetworkScan(request, AsyncTask.SERIAL_EXECUTOR, callback);
    }

    public boolean setNetworkSelectionModeManual(String operatorNumeric, boolean persistSelection) {
        return setNetworkSelectionModeManual(
                new OperatorInfo("", "", operatorNumeric), persistSelection);
    }

    public boolean setNetworkSelectionModeManual(
            String operatorNumeric, boolean persistSelection, int ran) {
        return setNetworkSelectionModeManual(
                new OperatorInfo("", "", operatorNumeric, ran), persistSelection);
    }

    public boolean setNetworkSelectionModeManual(
            OperatorInfo operatorInfo, boolean persistSelection) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.setNetworkSelectionModeManual(
                        getSubId(), operatorInfo, persistSelection);
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "setNetworkSelectionModeManual RemoteException", ex);
            return false;
        }
    }

    public int getNetworkSelectionMode() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return 0;
            }
            int mode = telephony.getNetworkSelectionMode(getSubId());
            return mode;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getNetworkSelectionMode RemoteException", ex);
            return 0;
        }
    }

    public String getManualNetworkSelectionPlmn() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null && isManualNetworkSelectionAllowed()) {
                return telephony.getManualNetworkSelectionPlmn(getSubId());
            }
            return "";
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getManualNetworkSelectionPlmn RemoteException", ex);
            return "";
        }
    }

    @SystemApi
    public boolean isInEmergencySmsMode() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isInEmergencySmsMode();
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "isInEmergencySmsMode RemoteException", ex);
            return false;
        }
    }

    @Deprecated
    public boolean setPreferredNetworkType(int subId, int networkType) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.setAllowedNetworkTypesForReason(
                        subId, 0, RadioAccessFamily.getRafFromNetworkType(networkType));
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "setPreferredNetworkType RemoteException", ex);
        }
        return false;
    }

    @SystemApi
    @Deprecated
    public boolean setPreferredNetworkTypeBitmask(long networkTypeBitmask) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.setAllowedNetworkTypesForReason(
                        getSubId(), 0, checkNetworkTypeBitmask(networkTypeBitmask));
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "setPreferredNetworkTypeBitmask RemoteException", ex);
        }
        return false;
    }

    private long checkNetworkTypeBitmask(long networkTypeBitmask) {
        if ((networkTypeBitmask & 262144) != 0) {
            return (networkTypeBitmask ^ 262144) | 4096;
        }
        return networkTypeBitmask;
    }

    @SystemApi
    @Deprecated
    public boolean setAllowedNetworkTypes(long allowedNetworkTypes) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.setAllowedNetworkTypesForReason(
                        getSubId(), 2, checkNetworkTypeBitmask(allowedNetworkTypes));
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "setAllowedNetworkTypes RemoteException", ex);
            return false;
        }
    }

    public void setAllowedNetworkTypesForReason(int reason, long allowedNetworkTypes) {
        if (!isValidAllowedNetworkTypesReason(reason)) {
            throw new IllegalArgumentException("invalid AllowedNetworkTypesReason.");
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setAllowedNetworkTypesForReason(
                        getSubId(), reason, checkNetworkTypeBitmask(allowedNetworkTypes));
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "setAllowedNetworkTypesForReason RemoteException", ex);
            ex.rethrowFromSystemServer();
        }
    }

    public long getAllowedNetworkTypesForReason(int reason) {
        if (!isValidAllowedNetworkTypesReason(reason)) {
            throw new IllegalArgumentException("invalid AllowedNetworkTypesReason.");
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getAllowedNetworkTypesForReason(getSubId(), reason);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "getAllowedNetworkTypesForReason RemoteException", ex);
            ex.rethrowFromSystemServer();
            return -1L;
        }
    }

    public static boolean isValidAllowedNetworkTypesReason(int reason) {
        switch (reason) {
            case 0:
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    public static long getAllNetworkTypesBitmask() {
        return 916479L;
    }

    public static String convertNetworkTypeBitmaskToString(final long networkTypeBitmask) {
        String networkTypeName =
                (String)
                        IntStream.rangeClosed(1, 20)
                                .filter(
                                        new IntPredicate() { // from class:
                                                             // android.telephony.TelephonyManager$$ExternalSyntheticLambda4
                                            @Override // java.util.function.IntPredicate
                                            public final boolean test(int i) {
                                                return TelephonyManager
                                                        .lambda$convertNetworkTypeBitmaskToString$11(
                                                                networkTypeBitmask, i);
                                            }
                                        })
                                .mapToObj(
                                        new IntFunction() { // from class:
                                                            // android.telephony.TelephonyManager$$ExternalSyntheticLambda5
                                            @Override // java.util.function.IntFunction
                                            public final Object apply(int i) {
                                                String networkTypeName2;
                                                networkTypeName2 =
                                                        TelephonyManager.getNetworkTypeName(i);
                                                return networkTypeName2;
                                            }
                                        })
                                .collect(
                                        Collectors.joining(
                                                NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER));
        return TextUtils.isEmpty(networkTypeName) ? "UNKNOWN" : networkTypeName;
    }

    static /* synthetic */ boolean lambda$convertNetworkTypeBitmaskToString$11(
            long networkTypeBitmask, int x) {
        return (getBitMaskForNetworkType(x) & networkTypeBitmask) == getBitMaskForNetworkType(x);
    }

    public boolean setPreferredNetworkTypeToGlobal() {
        return setPreferredNetworkTypeToGlobal(getSubId());
    }

    public boolean setPreferredNetworkTypeToGlobal(int subId) {
        int phoneId = 0;
        if (subId != -1) {
            phoneId = SubscriptionManager.getPhoneId(subId);
        }
        int supportedRat = getSupportedRat(phoneId);
        if (supportedRat != -1) {
            boolean supportTdscdma = (supportedRat & 16) == 16;
            boolean supportNr = (supportedRat & 64) == 64;
            int newNetworkType = -1;
            if (TelephonyFeatures.IS_EXYNOS && supportTdscdma) {
                com.android.telephony.Rlog.d(
                        TAG, "setPreferredNetworkTypeToGlobal - ignore supportTdscdma");
                supportTdscdma = false;
            }
            if (supportTdscdma && supportNr) {
                newNetworkType = 33;
            } else if (supportTdscdma) {
                newNetworkType = 22;
            } else if (!supportNr) {
                newNetworkType = 10;
            }
            if (newNetworkType != -1) {
                com.android.telephony.Rlog.d(
                        TAG,
                        "setPreferredNetworkTypeToGlobal - subId: "
                                + subId
                                + ". Use extended global mode network type: "
                                + newNetworkType);
                return setPreferredNetworkType(subId, newNetworkType);
            }
        }
        com.android.telephony.Rlog.d(TAG, "setPreferredNetworkTypeToGlobal - subId: " + subId);
        return setPreferredNetworkType(subId, 27);
    }

    @SystemApi
    public boolean isTetheringApnRequired() {
        return isTetheringApnRequired(getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
    }

    public boolean isTetheringApnRequired(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isTetheringApnRequiredForSubscriber(subId);
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "hasMatchedTetherApnSetting RemoteException", ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "hasMatchedTetherApnSetting NPE", ex2);
            return false;
        }
    }

    public boolean hasCarrierPrivileges() {
        return hasCarrierPrivileges(getSubId());
    }

    public boolean hasCarrierPrivileges(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getCarrierPrivilegeStatus(subId) == 1;
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "hasCarrierPrivileges RemoteException", ex);
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "hasCarrierPrivileges NPE", ex2);
        }
        return false;
    }

    public boolean setOperatorBrandOverride(String brand) {
        return setOperatorBrandOverride(getSubId(), brand);
    }

    public boolean setOperatorBrandOverride(int subId, String brand) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.setOperatorBrandOverride(subId, brand);
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "setOperatorBrandOverride RemoteException", ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "setOperatorBrandOverride NPE", ex2);
            return false;
        }
    }

    public boolean setRoamingOverride(
            List<String> gsmRoamingList,
            List<String> gsmNonRoamingList,
            List<String> cdmaRoamingList,
            List<String> cdmaNonRoamingList) {
        return setRoamingOverride(
                getSubId(), gsmRoamingList, gsmNonRoamingList, cdmaRoamingList, cdmaNonRoamingList);
    }

    public boolean setRoamingOverride(
            int subId,
            List<String> gsmRoamingList,
            List<String> gsmNonRoamingList,
            List<String> cdmaRoamingList,
            List<String> cdmaNonRoamingList) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.setRoamingOverride(
                        subId,
                        gsmRoamingList,
                        gsmNonRoamingList,
                        cdmaRoamingList,
                        cdmaNonRoamingList);
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "setRoamingOverride RemoteException", ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "setRoamingOverride NPE", ex2);
            return false;
        }
    }

    @SystemApi
    public String getCdmaMdn() {
        return getCdmaMdn(getSubId());
    }

    @SystemApi
    public String getCdmaMdn(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return null;
            }
            return telephony.getCdmaMdn(subId);
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @SystemApi
    public String getCdmaMin() {
        return getCdmaMin(getSubId());
    }

    @SystemApi
    public String getCdmaMin(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return null;
            }
            return telephony.getCdmaMin(subId);
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    @SystemApi
    public int checkCarrierPrivilegesForPackage(String pkgName) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.checkCarrierPrivilegesForPackage(getSubId(), pkgName);
            }
            return 0;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "checkCarrierPrivilegesForPackage RemoteException", ex);
            return 0;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "checkCarrierPrivilegesForPackage NPE", ex2);
            return 0;
        }
    }

    @SystemApi
    public int checkCarrierPrivilegesForPackageAnyPhone(String pkgName) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.checkCarrierPrivilegesForPackageAnyPhone(pkgName);
            }
            return 0;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "checkCarrierPrivilegesForPackageAnyPhone RemoteException", ex);
            return 0;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "checkCarrierPrivilegesForPackageAnyPhone NPE", ex2);
            return 0;
        }
    }

    public int semCheckCarrierPrivilegesForPackageAnyPhone(String pkgName) {
        return checkCarrierPrivilegesForPackageAnyPhone(pkgName);
    }

    @SystemApi
    public List<String> getCarrierPackageNamesForIntent(Intent intent) {
        return getCarrierPackageNamesForIntentAndPhone(intent, getPhoneId());
    }

    @SystemApi
    public List<String> getCarrierPackageNamesForIntentAndPhone(Intent intent, int phoneId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getCarrierPackageNamesForIntentAndPhone(intent, phoneId);
            }
            return null;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "getCarrierPackageNamesForIntentAndPhone RemoteException", ex);
            return null;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "getCarrierPackageNamesForIntentAndPhone NPE", ex2);
            return null;
        }
    }

    @SystemApi
    public String getCarrierServicePackageName() {
        return getCarrierServicePackageNameForLogicalSlot(getPhoneId());
    }

    @SystemApi
    public String getCarrierServicePackageNameForLogicalSlot(int logicalSlotIndex) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getCarrierServicePackageNameForLogicalSlot(logicalSlotIndex);
            }
            return null;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "getCarrierServicePackageNameForLogicalSlot RemoteException", ex);
            return null;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "getCarrierServicePackageNameForLogicalSlot NPE", ex2);
            return null;
        }
    }

    public List<String> getPackagesWithCarrierPrivileges() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getPackagesWithCarrierPrivileges(getPhoneId());
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "getPackagesWithCarrierPrivileges RemoteException", ex);
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "getPackagesWithCarrierPrivileges NPE", ex2);
        }
        return Collections.EMPTY_LIST;
    }

    @SystemApi
    public List<String> getCarrierPrivilegedPackagesForAllActiveSubscriptions() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getPackagesWithCarrierPrivilegesForAllPhones();
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG,
                    "getCarrierPrivilegedPackagesForAllActiveSubscriptions RemoteException",
                    ex);
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "getCarrierPrivilegedPackagesForAllActiveSubscriptions NPE", ex2);
        }
        return Collections.EMPTY_LIST;
    }

    public void setCallComposerStatus(int status) {
        if (Flags.businessCallComposer()) {
            if (status > 2 || status < 0) {
                throw new IllegalArgumentException("requested status is invalid");
            }
        } else if (status > 1 || status < 0) {
            throw new IllegalArgumentException("requested status is invalid");
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setCallComposerStatus(getSubId(), status);
            }
        } catch (RemoteException ex) {
            Log.e(TAG, "Error calling ITelephony#setCallComposerStatus", ex);
            ex.rethrowFromSystemServer();
        }
    }

    public int getCallComposerStatus() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getCallComposerStatus(getSubId());
            }
            return 0;
        } catch (RemoteException ex) {
            Log.e(TAG, "Error calling ITelephony#getCallComposerStatus", ex);
            ex.rethrowFromSystemServer();
            return 0;
        }
    }

    @SystemApi
    public void dial(String number) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.dial(number);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#dial", e);
        }
    }

    public void dial(int subId, String number) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                semTelephony.dialForSubscriber(subId, number);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ISemTelephony#dialForSubscriber", e);
        }
    }

    @SystemApi
    @Deprecated
    public void call(String callingPackage, String number) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.call(callingPackage, number);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#call", e);
        }
    }

    @SystemApi
    @Deprecated
    public boolean endCall() {
        return false;
    }

    @SystemApi
    @Deprecated
    public void answerRingingCall() {}

    @SystemApi
    @Deprecated
    public void silenceRinger() {}

    @SystemApi
    @Deprecated
    public boolean isOffhook() {
        TelecomManager tm =
                (TelecomManager) this.mContext.getSystemService(Context.TELECOM_SERVICE);
        return tm.isInCall();
    }

    @SystemApi
    @Deprecated
    public boolean isRinging() {
        TelecomManager tm =
                (TelecomManager) this.mContext.getSystemService(Context.TELECOM_SERVICE);
        return tm.isRinging();
    }

    @SystemApi
    @Deprecated
    public boolean isIdle() {
        TelecomManager tm =
                (TelecomManager) this.mContext.getSystemService(Context.TELECOM_SERVICE);
        return !tm.isInCall();
    }

    @SystemApi
    @Deprecated
    public boolean isRadioOn() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isRadioOnWithFeature(getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isRadioOn", e);
            return false;
        }
    }

    @SystemApi
    public boolean supplyPin(String pin) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.supplyPinForSubscriber(getSubId(), pin);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyPinForSubscriber", e);
            return false;
        }
    }

    @SystemApi
    public boolean supplyPuk(String puk, String pin) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.supplyPukForSubscriber(getSubId(), puk, pin);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyPukForSubscriber", e);
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public int[] supplyPinReportResult(String pin) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.supplyPinReportResultForSubscriber(getSubId(), pin);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyPinReportResultForSubscriber", e);
        }
        return new int[0];
    }

    @SystemApi
    @Deprecated
    public int[] supplyPukReportResult(String puk, String pin) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.supplyPukReportResultForSubscriber(getSubId(), puk, pin);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyPukReportResultForSubscriber", e);
        }
        return new int[0];
    }

    @SystemApi
    public PinResult supplyIccLockPin(String pin) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                int[] result = telephony.supplyPinReportResultForSubscriber(getSubId(), pin);
                return new PinResult(result[0], result[1]);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyIccLockPin", e);
            e.rethrowFromSystemServer();
            return PinResult.getDefaultFailedResult();
        }
    }

    @SystemApi
    public PinResult supplyIccLockPuk(String puk, String pin) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                int[] result = telephony.supplyPukReportResultForSubscriber(getSubId(), puk, pin);
                return new PinResult(result[0], result[1]);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyIccLockPuk", e);
            e.rethrowFromSystemServer();
            return PinResult.getDefaultFailedResult();
        }
    }

    public abstract static class UssdResponseCallback {
        public void onReceiveUssdResponse(
                TelephonyManager telephonyManager, String request, CharSequence response) {}

        public void onReceiveUssdResponseFailed(
                TelephonyManager telephonyManager, String request, int failureCode) {}
    }

    public void sendUssdRequest(
            String ussdRequest, final UssdResponseCallback callback, Handler handler) {
        Preconditions.checkNotNull(callback, "UssdResponseCallback cannot be null.");
        ResultReceiver wrappedCallback =
                new ResultReceiver(handler) { // from class: android.telephony.TelephonyManager.7
                    @Override // android.os.ResultReceiver
                    protected void onReceiveResult(int resultCode, Bundle ussdResponse) {
                        com.android.telephony.Rlog.d(TelephonyManager.TAG, "USSD:" + resultCode);
                        Preconditions.checkNotNull(ussdResponse, "ussdResponse cannot be null.");
                        UssdResponse response =
                                (UssdResponse)
                                        ussdResponse.getParcelable(
                                                TelephonyManager.USSD_RESPONSE, UssdResponse.class);
                        if (resultCode == 100) {
                            callback.onReceiveUssdResponse(
                                    telephonyManager,
                                    response.getUssdRequest(),
                                    response.getReturnMessage());
                        } else {
                            callback.onReceiveUssdResponseFailed(
                                    telephonyManager, response.getUssdRequest(), resultCode);
                        }
                    }
                };
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.handleUssdRequest(getSubId(), ussdRequest, wrappedCallback);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#sendUSSDCode", e);
            UssdResponse response = new UssdResponse(ussdRequest, "");
            Bundle returnData = new Bundle();
            returnData.putParcelable(USSD_RESPONSE, response);
            wrappedCallback.send(-2, returnData);
        }
    }

    public boolean isConcurrentVoiceAndDataSupported() {
        boolean z = false;
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                z = telephony.isConcurrentVoiceAndDataAllowed(getSubId());
            }
            return z;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isConcurrentVoiceAndDataAllowed", e);
            return false;
        }
    }

    @SystemApi
    public boolean handlePinMmi(String dialString) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.handlePinMmi(dialString);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#handlePinMmi", e);
            return false;
        }
    }

    @SystemApi
    public boolean handlePinMmiForSubscriber(int subId, String dialString) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.handlePinMmiForSubscriber(subId, dialString);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#handlePinMmi", e);
            return false;
        }
    }

    public boolean semHandlePinMmiForSubscriber(int subId, String dialString) {
        return handlePinMmiForSubscriber(subId, dialString);
    }

    @SystemApi
    public void toggleRadioOnOff() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.toggleRadioOnOff();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#toggleRadioOnOff", e);
        }
    }

    @SystemApi
    @Deprecated
    public boolean setRadio(boolean turnOn) {
        try {
            if (turnOn) {
                clearRadioPowerOffForReason(0);
            } else {
                requestRadioPowerOffForReason(0);
            }
            return true;
        } catch (Exception e) {
            String calledFunction =
                    turnOn ? "clearRadioPowerOffForReason" : "requestRadioPowerOffForReason";
            Log.e(TAG, "Error calling " + calledFunction, e);
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public boolean setRadioPower(boolean turnOn) {
        try {
            if (turnOn) {
                clearRadioPowerOffForReason(0);
            } else {
                requestRadioPowerOffForReason(0);
            }
            return true;
        } catch (Exception e) {
            String calledFunction =
                    turnOn ? "clearRadioPowerOffForReason" : "requestRadioPowerOffForReason";
            Log.e(TAG, "Error calling " + calledFunction, e);
            return false;
        }
    }

    @SystemApi
    public void requestRadioPowerOffForReason(int reason) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (!telephony.requestRadioPowerOffForReason(getSubId(), reason)) {
                    throw new IllegalStateException("Telephony service is not available.");
                }
                return;
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#requestRadioPowerOffForReason", e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void clearRadioPowerOffForReason(int reason) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (!telephony.clearRadioPowerOffForReason(getSubId(), reason)) {
                    throw new IllegalStateException("Telephony service is not available.");
                }
                return;
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#clearRadioPowerOffForReason", e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public Set<Integer> getRadioPowerOffReasons() {
        ITelephony telephony;
        Set<Integer> result = new HashSet<>();
        try {
            telephony = getITelephony();
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getRadioPowerOffReasons", e);
            e.rethrowAsRuntimeException();
        }
        if (telephony != null) {
            result.addAll(
                    telephony.getRadioPowerOffReasons(
                            getSubId(),
                            this.mContext.getOpPackageName(),
                            this.mContext.getAttributionTag()));
            return result;
        }
        throw new IllegalStateException("Telephony service is null.");
    }

    @SystemApi
    public void shutdownAllRadios() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.shutdownMobileRadios();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#shutdownAllRadios", e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public boolean isAnyRadioPoweredOn() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.needMobileRadioShutdown();
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isAnyRadioPoweredOn", e);
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    @SystemApi
    public int getRadioPowerState() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getRadioPowerState(
                        getSlotIndex(),
                        this.mContext.getOpPackageName(),
                        this.mContext.getAttributionTag());
            }
            return 2;
        } catch (RemoteException e) {
            return 2;
        }
    }

    @SystemApi
    public void updateServiceLocation() {
        Log.e(TAG, "Do not call TelephonyManager#updateServiceLocation()");
    }

    @SystemApi
    public boolean enableDataConnectivity() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.enableDataConnectivity(getOpPackageName());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#enableDataConnectivity", e);
            return false;
        }
    }

    @SystemApi
    public boolean disableDataConnectivity() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.disableDataConnectivity(getOpPackageName());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#disableDataConnectivity", e);
            return false;
        }
    }

    @SystemApi
    public boolean isDataConnectivityPossible() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isDataConnectivityPossible(
                        getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isDataAllowed", e);
            return false;
        }
    }

    @SystemApi
    public boolean needsOtaServiceProvisioning() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.needsOtaServiceProvisioning();
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#needsOtaServiceProvisioning", e);
            return false;
        }
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public boolean semNeedsOtaServiceProvisioning() {
        return needsOtaServiceProvisioning();
    }

    public String getMobileProvisioningUrl() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getMobileProvisioningUrl();
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "Telephony#getMobileProvisioningUrl RemoteException" + ex);
            return null;
        }
    }

    public boolean isOpportunisticSubscription() {
        if (this.mSubscriptionManager != null
                && this.mSubscriptionManager.getActiveSubscriptionInfoCount() == 2) {
            int[] subId1 = SubscriptionManager.getSubId(0);
            int[] subId2 = SubscriptionManager.getSubId(1);
            SubscriptionInfo info1 = null;
            SubscriptionInfo info2 = null;
            if (!ArrayUtils.isEmpty(subId1) && !ArrayUtils.isEmpty(subId2)) {
                info1 = this.mSubscriptionManager.getActiveSubscriptionInfo(subId1[0]);
                info2 = this.mSubscriptionManager.getActiveSubscriptionInfo(subId2[0]);
            }
            if (info1 != null
                    && info2 != null
                    && info1.getGroupUuid() != null
                    && info1.getGroupUuid().equals(info2.getGroupUuid())
                    && (info2.isOpportunistic() || info1.isOpportunistic())) {
                Log.d(TAG, "isOpportunisticSubscription true");
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public void setDataEnabled(boolean enable) {
        if (isOpportunisticSubscription()
                && (("DSA".equals(TelephonyFeatures.getSalesCode())
                                && TelephonyFeatures.isMainOperatorSpecific(1, "DSG"))
                        || TelephonyFeatures.isSubOperatorSpecific(1, "CHA", "CCT")
                        || TelephonyFeatures.isSubOperatorSpecific(0, "CHA", "CCT"))) {
            int oppSubId = SubscriptionManager.getActiveDataSubscriptionId();
            if (SubscriptionManager.isUsableSubIdValue(oppSubId)) {
                setDataEnabled(oppSubId, enable);
                return;
            }
        }
        setDataEnabled(getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), enable);
    }

    @SystemApi
    @Deprecated
    public void setDataEnabled(int subId, boolean enable) {
        try {
            setDataEnabledForReason(subId, 0, enable);
        } catch (RuntimeException e) {
            Log.e(TAG, "Error calling setDataEnabledForReason e:" + e);
        }
    }

    @SystemApi
    @Deprecated
    public boolean getDataEnabled() {
        return isDataEnabled();
    }

    public boolean isDataEnabled() {
        try {
            return isDataEnabledForReason(0);
        } catch (IllegalStateException ise) {
            Log.e(TAG, "Error calling #isDataEnabled, returning default (false).", ise);
            return false;
        }
    }

    public boolean isDataRoamingEnabled() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return false;
            }
            boolean isDataRoamingEnabled =
                    telephony.isDataRoamingEnabled(
                            getSubId(SubscriptionManager.getDefaultDataSubscriptionId()));
            return isDataRoamingEnabled;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isDataRoamingEnabled", e);
            return false;
        }
    }

    @SystemApi
    public int getCdmaRoamingMode() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                int mode = telephony.getCdmaRoamingMode(getSubId());
                return mode;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "Error calling ITelephony#getCdmaRoamingMode", ex);
            ex.rethrowFromSystemServer();
            return -1;
        }
    }

    @SystemApi
    public void setCdmaRoamingMode(int mode) {
        if (getPhoneType() != 2) {
            throw new IllegalStateException("Phone does not support CDMA.");
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                boolean result = telephony.setCdmaRoamingMode(getSubId(), mode);
                if (!result) {
                    throw new IllegalStateException("radio is unavailable.");
                }
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "Error calling ITelephony#setCdmaRoamingMode", ex);
            ex.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getCdmaSubscriptionMode() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                int mode = telephony.getCdmaSubscriptionMode(getSubId());
                return mode;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "Error calling ITelephony#getCdmaSubscriptionMode", ex);
            ex.rethrowFromSystemServer();
            return 0;
        }
    }

    @SystemApi
    public void setCdmaSubscriptionMode(int mode) {
        if (getPhoneType() != 2) {
            throw new IllegalStateException("Phone does not support CDMA.");
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                boolean result = telephony.setCdmaSubscriptionMode(getSubId(), mode);
                if (!result) {
                    throw new IllegalStateException("radio is unavailable.");
                }
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "Error calling ITelephony#setCdmaSubscriptionMode", ex);
            ex.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDataRoamingEnabled(boolean isEnabled) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setDataRoamingEnabled(
                        getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), isEnabled);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setDataRoamingEnabled", e);
        }
    }

    @SystemApi
    @Deprecated
    public boolean getDataEnabled(int subId) {
        try {
            return isDataEnabledForReason(subId, 0);
        } catch (RuntimeException e) {
            Log.e(TAG, "Error calling isDataEnabledForReason e:" + e);
            return false;
        }
    }

    public int invokeOemRilRequestRawForPhone(int phoneId, byte[] oemReq, byte[] oemResp) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.invokeOemRilRequestRawForPhone(phoneId, oemReq, oemResp);
            }
            return -1;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "invokeOemRilRequestRawForPhone is fail due to RemoteException. " + ex);
            return -1;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG,
                    "invokeOemRilRequestRawForPhone is fail due to NullPointerException. " + ex2);
            return -1;
        }
    }

    public int invokeOemRilRequestRawForSubscriber(int subId, byte[] oemReq, byte[] oemResp) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.invokeOemRilRequestRawForSubscriber(subId, oemReq, oemResp);
            }
            return -1;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG,
                    "invokeOemRilRequestRawForSubscriber is fail due to RemoteException. " + ex);
            return -1;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG,
                    "invokeOemRilRequestRawForSubscriber is fail due to NullPointerException. "
                            + ex2);
            return -1;
        }
    }

    @SystemApi
    @Deprecated
    public void enableVideoCalling(boolean enable) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.enableVideoCalling(enable);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#enableVideoCalling", e);
        }
    }

    @SystemApi
    @Deprecated
    public boolean isVideoCallingEnabled() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isVideoCallingEnabled(getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isVideoCallingEnabled", e);
            return false;
        }
    }

    public boolean canChangeDtmfToneLength() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.canChangeDtmfToneLength(
                        this.mSubId, getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#canChangeDtmfToneLength", e);
            return false;
        } catch (SecurityException e2) {
            Log.e(TAG, "Permission error calling ITelephony#canChangeDtmfToneLength", e2);
            return false;
        }
    }

    public boolean isWorldPhone() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isWorldPhone(this.mSubId, getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isWorldPhone", e);
            return false;
        } catch (SecurityException e2) {
            Log.e(TAG, "Permission error calling ITelephony#isWorldPhone", e2);
            return false;
        }
    }

    @Deprecated
    public boolean isTtyModeSupported() {
        TelecomManager telecomManager = null;
        try {
            if (this.mContext != null) {
                telecomManager =
                        (TelecomManager) this.mContext.getSystemService(TelecomManager.class);
            }
            if (telecomManager != null) {
                return telecomManager.isTtySupported();
            }
            return false;
        } catch (SecurityException e) {
            Log.e(TAG, "Permission error calling TelecomManager#isTtySupported", e);
            return false;
        }
    }

    public boolean isRttSupported() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isRttSupported(this.mSubId);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isRttSupported", e);
            return false;
        } catch (SecurityException e2) {
            Log.e(TAG, "Permission error calling ITelephony#isWorldPhone", e2);
            return false;
        }
    }

    public boolean isHearingAidCompatibilitySupported() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isHearingAidCompatibilitySupported();
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isHearingAidCompatibilitySupported", e);
            return false;
        } catch (SecurityException e2) {
            Log.e(
                    TAG,
                    "Permission error calling ITelephony#isHearingAidCompatibilitySupported",
                    e2);
            return false;
        }
    }

    public boolean isImsRegistered(int subId) {
        try {
            return getITelephony().isImsRegistered(subId);
        } catch (RemoteException | NullPointerException e) {
            return false;
        }
    }

    public boolean isImsRegistered() {
        try {
            return getITelephony().isImsRegistered(getSubId());
        } catch (RemoteException | NullPointerException e) {
            return false;
        }
    }

    public boolean isVolteAvailable() {
        try {
            return getITelephony().isAvailable(getSubId(), 1, 0);
        } catch (RemoteException | NullPointerException e) {
            return false;
        }
    }

    public boolean isVideoTelephonyAvailable() {
        try {
            return getITelephony().isVideoTelephonyAvailable(getSubId());
        } catch (RemoteException | NullPointerException e) {
            return false;
        }
    }

    public boolean isWifiCallingAvailable() {
        try {
            return getITelephony().isWifiCallingAvailable(getSubId());
        } catch (RemoteException | NullPointerException e) {
            return false;
        }
    }

    public int getImsRegTechnologyForMmTel() {
        try {
            return getITelephony().getImsRegTechnologyForMmTel(getSubId());
        } catch (RemoteException e) {
            return -1;
        }
    }

    public void setSimOperatorNumericForPhone(int phoneId, String numeric) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            List<String> newList =
                    updateTelephonyProperty(
                            TelephonyProperties.icc_operator_numeric(), phoneId, numeric);
            TelephonyProperties.icc_operator_numeric(newList);
        }
    }

    public void setSimOperatorNameForPhone(int phoneId, String name) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            List<String> newList =
                    updateTelephonyProperty(
                            TelephonyProperties.icc_operator_alpha(), phoneId, name);
            TelephonyProperties.icc_operator_alpha(newList);
        }
    }

    public void setSimCountryIso(String iso) {
        int phoneId = getPhoneId();
        setSimCountryIsoForPhone(phoneId, iso);
    }

    public void setSimCountryIsoForPhone(int phoneId, String iso) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            List<String> newList =
                    updateTelephonyProperty(
                            TelephonyProperties.icc_operator_iso_country(), phoneId, iso);
            TelephonyProperties.icc_operator_iso_country(newList);
        }
    }

    public void setSimState(String state) {
        int phoneId = getPhoneId();
        setSimStateForPhone(phoneId, state);
    }

    public void setSimStateForPhone(int phoneId, String state) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            List<String> newList =
                    updateTelephonyProperty(TelephonyProperties.sim_state(), phoneId, state);
            TelephonyProperties.sim_state(newList);
        }
    }

    @SystemApi
    @Deprecated
    public void setSimPowerState(int state) {
        setSimPowerStateForSlot(getSlotIndex(), state);
    }

    @SystemApi
    @Deprecated
    public void setSimPowerStateForSlot(int slotIndex, int state) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setSimPowerStateForSlot(slotIndex, state);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setSimPowerStateForSlot", e);
        } catch (SecurityException e2) {
            Log.e(TAG, "Permission error calling ITelephony#setSimPowerStateForSlot", e2);
        }
    }

    @SystemApi
    public void setSimPowerState(int state, Executor executor, Consumer<Integer> callback) {
        setSimPowerStateForSlot(getSlotIndex(), state, executor, callback);
    }

    @SystemApi
    public void setSimPowerStateForSlot(
            int slotIndex, int state, final Executor executor, final Consumer<Integer> callback) {
        if (state != 0 && state != 1 && state != 2) {
            throw new IllegalArgumentException("requested SIM state is invalid");
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                throw new IllegalStateException("Telephony is null.");
            }
            com.android.internal.telephony.IIntegerConsumer internalCallback =
                    new AnonymousClass8(executor, callback);
            if (telephony == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Telephony is null");
                }
            } else {
                telephony.setSimPowerStateForSlotWithCallback(slotIndex, state, internalCallback);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setSimPowerStateForSlot", e);
            runOnBackgroundThread(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda16
                        @Override // java.lang.Runnable
                        public final void run() {
                            executor.execute(
                                    new Runnable() { // from class:
                                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda7
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            r1.accept(2);
                                        }
                                    });
                        }
                    });
        } catch (SecurityException e2) {
            Log.e(TAG, "Permission error calling ITelephony#setSimPowerStateForSlot", e2);
        }
    }

    /* renamed from: android.telephony.TelephonyManager$8, reason: invalid class name */
    class AnonymousClass8 extends IIntegerConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass8(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$8$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.TelephonyManager$8$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void setBasebandVersionForPhone(int phoneId, String version) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            List<String> newList =
                    updateTelephonyProperty(
                            TelephonyProperties.baseband_version(), phoneId, version);
            TelephonyProperties.baseband_version(newList);
        }
    }

    public String getBasebandVersion() {
        int phoneId = getPhoneId();
        return getBasebandVersionForPhone(phoneId);
    }

    public String getBasebandVersionForPhone(int phoneId) {
        return (String) getTelephonyProperty(phoneId, TelephonyProperties.baseband_version(), "");
    }

    public void setPhoneType(int phoneId, int type) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            List<Integer> newList =
                    updateTelephonyProperty(
                            TelephonyProperties.current_active_phone(),
                            phoneId,
                            Integer.valueOf(type));
            TelephonyProperties.current_active_phone(newList);
        }
    }

    public String getOtaSpNumberSchemaForPhone(int phoneId, String defaultValue) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            return (String)
                    getTelephonyProperty(
                            phoneId, TelephonyProperties.otasp_num_schema(), defaultValue);
        }
        return defaultValue;
    }

    public boolean getSmsReceiveCapableForPhone(int phoneId, boolean defaultValue) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            return ((Boolean)
                            getTelephonyProperty(
                                    phoneId,
                                    TelephonyProperties.sms_receive(),
                                    Boolean.valueOf(defaultValue)))
                    .booleanValue();
        }
        return defaultValue;
    }

    public boolean getSmsSendCapableForPhone(int phoneId, boolean defaultValue) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            return ((Boolean)
                            getTelephonyProperty(
                                    phoneId,
                                    TelephonyProperties.sms_send(),
                                    Boolean.valueOf(defaultValue)))
                    .booleanValue();
        }
        return defaultValue;
    }

    @SystemApi
    public ComponentName getAndUpdateDefaultRespondViaMessageApplication() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getDefaultRespondViaMessageApplication(getSubId(), true);
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error in getAndUpdateDefaultRespondViaMessageApplication: " + e);
            return null;
        }
    }

    @SystemApi
    public ComponentName getDefaultRespondViaMessageApplication() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getDefaultRespondViaMessageApplication(getSubId(), false);
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error in getDefaultRespondViaMessageApplication: " + e);
            return null;
        }
    }

    public void setNetworkOperatorNameForPhone(int phoneId, String name) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            List<String> newList =
                    updateTelephonyProperty(TelephonyProperties.operator_alpha(), phoneId, name);
            try {
                TelephonyProperties.operator_alpha(newList);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "setNetworkOperatorNameForPhone: ", e);
                int numberOfEntries = newList.size();
                int maxOperatorLength = (91 - numberOfEntries) / numberOfEntries;
                for (int i = 0; i < newList.size(); i++) {
                    if (newList.get(i) != null) {
                        newList.set(
                                i,
                                TextUtils.truncateStringForUtf8Storage(
                                        newList.get(i), maxOperatorLength));
                    }
                }
                TelephonyProperties.operator_alpha(newList);
                Log.e(TAG, "successfully truncated operator_alpha: " + newList);
            }
        }
    }

    public void setNetworkOperatorNumericForPhone(int phoneId, String numeric) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            List<String> newList =
                    updateTelephonyProperty(
                            TelephonyProperties.operator_numeric(), phoneId, numeric);
            TelephonyProperties.operator_numeric(newList);
        }
    }

    public void setNetworkRoamingForPhone(int phoneId, boolean isRoaming) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            logWithCallerInfo(
                    "setNetworkRoamingForPhone - phoneId: "
                            + phoneId
                            + ", isRoaming: "
                            + isRoaming);
            List<Boolean> newList =
                    updateTelephonyProperty(
                            TelephonyProperties.operator_is_roaming(),
                            phoneId,
                            Boolean.valueOf(isRoaming));
            TelephonyProperties.operator_is_roaming(newList);
            return;
        }
        logWithCallerInfo("setNetworkRoamingForPhone - Invalid phoneId: " + phoneId);
    }

    public void setDataNetworkType(int type) {
        int phoneId = getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
        setDataNetworkTypeForPhone(phoneId, type);
    }

    public void setDataNetworkTypeForPhone(int phoneId, int type) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            List<String> newList =
                    updateTelephonyProperty(
                            TelephonyProperties.data_network_type(),
                            phoneId,
                            ServiceState.rilRadioTechnologyToString(type));
            TelephonyProperties.data_network_type(newList);
        }
    }

    public int getSubIdForPhoneAccount(PhoneAccount phoneAccount) {
        if (phoneAccount == null || !phoneAccount.hasCapabilities(4)) {
            return -1;
        }
        int retval = getSubscriptionId(phoneAccount.getAccountHandle());
        return retval;
    }

    public PhoneAccountHandle getPhoneAccountHandle() {
        return getPhoneAccountHandleForSubscriptionId(getSubId());
    }

    public PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int subscriptionId) {
        try {
            ITelephony service = getITelephony();
            if (service == null) {
                return null;
            }
            PhoneAccountHandle returnValue =
                    service.getPhoneAccountHandleForSubscriptionId(subscriptionId);
            return returnValue;
        } catch (RemoteException e) {
            return null;
        }
    }

    public int getSubscriptionId(PhoneAccountHandle phoneAccountHandle) {
        return this.mPhoneAccountHandleToSubIdCache.query(phoneAccountHandle).intValue();
    }

    public void factoryReset(int subId) {
        try {
            Log.d(TAG, "factoryReset: subId=" + subId);
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.factoryReset(subId, getOpPackageName());
            }
        } catch (RemoteException e) {
        }
    }

    @SystemApi
    public void resetSettings() {
        try {
            Log.d(TAG, "resetSettings: subId=" + getSubId());
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.factoryReset(getSubId(), getOpPackageName());
                semSetNrMode(getPhoneId(), 240, false);
            }
        } catch (RemoteException e) {
        }
    }

    @SystemApi
    public Locale getSimLocale() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                String languageTag = telephony.getSimLocaleForSubscriber(getSubId());
                if (!TextUtils.isEmpty(languageTag)) {
                    return Locale.forLanguageTag(languageTag);
                }
                return null;
            }
            return null;
        } catch (RemoteException e) {
            return null;
        }
    }

    @SystemApi
    public static class ModemActivityInfoException extends Exception {
        public static final int ERROR_INVALID_INFO_RECEIVED = 2;
        public static final int ERROR_MODEM_RESPONSE_ERROR = 3;
        public static final int ERROR_PHONE_NOT_AVAILABLE = 1;
        public static final int ERROR_UNKNOWN = 0;
        private final int mErrorCode;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ModemActivityInfoError {}

        public ModemActivityInfoException(int errorCode) {
            this.mErrorCode = errorCode;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Override // java.lang.Throwable
        public String toString() {
            switch (this.mErrorCode) {
                case 0:
                    return "ERROR_UNKNOWN";
                case 1:
                    return "ERROR_PHONE_NOT_AVAILABLE";
                case 2:
                    return "ERROR_INVALID_INFO_RECEIVED";
                case 3:
                    return "ERROR_MODEM_RESPONSE_ERROR";
                default:
                    return DevicePolicyResources.UNDEFINED;
            }
        }
    }

    @SystemApi
    public void requestModemActivityInfo(
            Executor executor,
            final OutcomeReceiver<ModemActivityInfo, ModemActivityInfoException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        ResultReceiver wrapperResultReceiver = new AnonymousClass9(null, executor, callback);
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                StringBuilder sb = new StringBuilder(this.mContext.getOpPackageName());
                if (!SemTelephonyUtils.SHIP_BUILD) {
                    sb.append(":").append(Debug.getCaller());
                }
                semTelephony.requestModemActivityInfo(wrapperResultReceiver, sb.toString());
                return;
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getModemActivityInfo", e);
        }
        executor.execute(
                new Runnable() { // from class:
                                 // android.telephony.TelephonyManager$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(
                                new TelephonyManager.ModemActivityInfoException(1));
                    }
                });
    }

    /* renamed from: android.telephony.TelephonyManager$9, reason: invalid class name */
    class AnonymousClass9 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass9(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int resultCode, Bundle data) {
            if (data == null) {
                Log.w(TelephonyManager.TAG, "requestModemActivityInfo: received null bundle");
                sendErrorToListener(0);
                return;
            }
            data.setDefusable(true);
            if (data.containsKey("exception")) {
                int receivedErrorCode = data.getInt("exception");
                sendErrorToListener(receivedErrorCode);
                return;
            }
            if (!data.containsKey("controller_activity")) {
                Log.w(
                        TelephonyManager.TAG,
                        "requestModemActivityInfo: Bundle did not contain expected key");
                sendErrorToListener(0);
                return;
            }
            Parcelable receivedResult = data.getParcelable("controller_activity");
            if (!(receivedResult instanceof ModemActivityInfo)) {
                Log.w(
                        TelephonyManager.TAG,
                        "requestModemActivityInfo: Bundle contained something that wasn't a"
                            + " ModemActivityInfo.");
                sendErrorToListener(0);
                return;
            }
            ModemActivityInfo modemActivityInfo = (ModemActivityInfo) receivedResult;
            if (!modemActivityInfo.isValid()) {
                Log.w(
                        TelephonyManager.TAG,
                        "requestModemActivityInfo: Received an invalid ModemActivityInfo");
                sendErrorToListener(2);
            } else {
                Log.d(
                        TelephonyManager.TAG,
                        "requestModemActivityInfo: Sending result to app: " + modemActivityInfo);
                sendResultToListener(modemActivityInfo);
            }
        }

        private void sendResultToListener(final ModemActivityInfo info) {
            final Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$callback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // android.telephony.TelephonyManager$9$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                                     // android.telephony.TelephonyManager$9$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OutcomeReceiver.this.onResult(r2);
                                        }
                                    });
                        }
                    });
        }

        private void sendErrorToListener(int code) {
            final ModemActivityInfoException e = new ModemActivityInfoException(code);
            final Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$callback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // android.telephony.TelephonyManager$9$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                                     // android.telephony.TelephonyManager$9$$ExternalSyntheticLambda2
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OutcomeReceiver.this.onError(r2);
                                        }
                                    });
                        }
                    });
        }
    }

    public ServiceState getServiceState() {
        return getServiceState(getLocationData());
    }

    public ServiceState getServiceState(int includeLocationData) {
        return getServiceStateForSlot(
                SubscriptionManager.getSlotIndex(getSubId()),
                includeLocationData != 2,
                includeLocationData == 0);
    }

    private ServiceState getServiceStateForSlot(
            int slotIndex,
            boolean renounceFineLocationAccess,
            boolean renounceCoarseLocationAccess) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getServiceStateForSlot(
                        slotIndex,
                        renounceFineLocationAccess,
                        renounceCoarseLocationAccess,
                        getOpPackageName(),
                        getAttributionTag());
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getServiceStateForSlot", e);
            return null;
        } catch (NullPointerException e2) {
            AnomalyReporter.reportAnomaly(
                    UUID.fromString("e2bed88e-def9-476e-bd71-3e572a8de6d1"),
                    "getServiceStateForSlot " + slotIndex + " NPE");
            return null;
        }
    }

    public ServiceState getServiceStateForSubscriber(int subId) {
        return getServiceStateForSlot(SubscriptionManager.getSlotIndex(subId), false, false);
    }

    public ServiceState getServiceStateForSlot(int slotIndex) {
        return getServiceStateForSlot(slotIndex, false, false);
    }

    public Uri getVoicemailRingtoneUri(PhoneAccountHandle accountHandle) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getVoicemailRingtoneUri(accountHandle);
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getVoicemailRingtoneUri", e);
            return null;
        }
    }

    @Deprecated
    public void setVoicemailRingtoneUri(PhoneAccountHandle phoneAccountHandle, Uri uri) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.setVoicemailRingtoneUri(getOpPackageName(), phoneAccountHandle, uri);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setVoicemailRingtoneUri", e);
        }
    }

    public boolean isVoicemailVibrationEnabled(PhoneAccountHandle accountHandle) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.isVoicemailVibrationEnabled(accountHandle);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isVoicemailVibrationEnabled", e);
            return false;
        }
    }

    @Deprecated
    public void setVoicemailVibrationEnabled(
            PhoneAccountHandle phoneAccountHandle, boolean enabled) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.setVoicemailVibrationEnabled(
                        getOpPackageName(), phoneAccountHandle, enabled);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isVoicemailVibrationEnabled", e);
        }
    }

    public int getSimCarrierId() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getSubscriptionCarrierId(getSubId());
            }
            return -1;
        } catch (RemoteException e) {
            return -1;
        }
    }

    public CharSequence getSimCarrierIdName() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getSubscriptionCarrierName(getSubId());
            }
            return null;
        } catch (RemoteException e) {
            return null;
        }
    }

    public int getSimSpecificCarrierId() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getSubscriptionSpecificCarrierId(getSubId());
            }
            return -1;
        } catch (RemoteException e) {
            return -1;
        }
    }

    public CharSequence getSimSpecificCarrierIdName() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getSubscriptionSpecificCarrierName(getSubId());
            }
            return null;
        } catch (RemoteException e) {
            return null;
        }
    }

    public int getCarrierIdFromSimMccMnc() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getCarrierIdFromMccMnc(getSlotIndex(), getSimOperator(), true);
            }
            return -1;
        } catch (RemoteException e) {
            return -1;
        }
    }

    public int getCarrierIdFromMccMnc(String mccmnc) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getCarrierIdFromMccMnc(getSlotIndex(), mccmnc, false);
            }
            return -1;
        } catch (RemoteException e) {
            return -1;
        }
    }

    public List<String> getCertsFromCarrierPrivilegeAccessRules() {
        List<String> certs = null;
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                certs = service.getCertsFromCarrierPrivilegeAccessRules(getSubId());
            }
        } catch (RemoteException e) {
        }
        return certs == null ? Collections.emptyList() : certs;
    }

    @SystemApi
    public String getAidForAppType(int appType) {
        return getAidForAppType(getSubId(), appType);
    }

    public String getAidForAppType(int subId, int appType) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getAidForAppType(subId, appType);
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getAidForAppType", e);
            return null;
        }
    }

    public String getEsn() {
        return getEsn(getSubId());
    }

    public String getEsn(int subId) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getEsn(subId);
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getEsn", e);
            return null;
        }
    }

    @SystemApi
    public String getCdmaPrlVersion() {
        return getCdmaPrlVersion(getSubId());
    }

    public String getCdmaPrlVersion(int subId) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getCdmaPrlVersion(subId);
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getCdmaPrlVersion", e);
            return null;
        }
    }

    @SystemApi
    public List<TelephonyHistogram> getTelephonyHistograms() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getTelephonyHistograms();
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getTelephonyHistograms", e);
            return null;
        }
    }

    @SystemApi
    public int setAllowedCarriers(int slotIndex, List<CarrierIdentifier> carriers) {
        int i;
        if (carriers == null || !SubscriptionManager.isValidPhoneId(slotIndex)) {
            return -1;
        }
        CarrierRestrictionRules.Builder allowedCarriers =
                CarrierRestrictionRules.newBuilder().setAllowedCarriers(carriers);
        if (carriers.isEmpty()) {
            i = 1;
        } else {
            i = 0;
        }
        CarrierRestrictionRules carrierRestrictionRules =
                allowedCarriers.setDefaultCarrierRestriction(i).build();
        int result = setCarrierRestrictionRules(carrierRestrictionRules);
        if (result != 0) {
            return -1;
        }
        return carriers.size();
    }

    @SystemApi
    public int setCarrierRestrictionRules(CarrierRestrictionRules rules) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.setAllowedCarriers(rules);
            }
            return 2;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setAllowedCarriers", e);
            return 2;
        } catch (NullPointerException e2) {
            Log.e(TAG, "Error calling ITelephony#setAllowedCarriers", e2);
            return 2;
        }
    }

    @SystemApi
    @Deprecated
    public List<CarrierIdentifier> getAllowedCarriers(int slotIndex) {
        CarrierRestrictionRules carrierRestrictionRule;
        if (SubscriptionManager.isValidPhoneId(slotIndex)
                && (carrierRestrictionRule = getCarrierRestrictionRules()) != null) {
            return carrierRestrictionRule.getAllowedCarriers();
        }
        return new ArrayList(0);
    }

    @SystemApi
    public CarrierRestrictionRules getCarrierRestrictionRules() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getAllowedCarriers();
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getAllowedCarriers", e);
            return null;
        } catch (NullPointerException e2) {
            Log.e(TAG, "Error calling ITelephony#getAllowedCarriers", e2);
            return null;
        }
    }

    public void getCarrierRestrictionStatus(Executor executor, Consumer<Integer> resultListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(resultListener);
        com.android.internal.telephony.IIntegerConsumer internalCallback =
                new AnonymousClass10(executor, resultListener);
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.getCarrierRestrictionStatus(internalCallback, getOpPackageName());
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "getCarrierRestrictionStatus: RemoteException = " + ex);
            throw ex.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.TelephonyManager$10, reason: invalid class name */
    class AnonymousClass10 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass10(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$10$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.TelephonyManager$10$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public List<String> getShaIdFromAllowList(String pkgName, int carrierId) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getShaIdFromAllowList(pkgName, carrierId);
            }
            return Collections.EMPTY_LIST;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getShaIdFromAllowList: RemoteException = " + ex);
            throw ex.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    @Deprecated
    public void setCarrierDataEnabled(boolean enabled) {
        try {
            setDataEnabledForReason(2, enabled);
        } catch (RuntimeException e) {
            Log.e(TAG, "Error calling setDataEnabledForReason e:" + e);
        }
    }

    @SystemApi
    @Deprecated
    public void setRadioEnabled(boolean enabled) {
        if (enabled) {
            clearRadioPowerOffForReason(2);
        } else {
            requestRadioPowerOffForReason(2);
        }
    }

    public int setVoNrEnabled(boolean enabled) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.setVoNrEnabled(
                        getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), enabled);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setVoNrEnabled", e);
            return 4;
        }
    }

    public int semSetVoNrEnabled(boolean enabled) {
        return setVoNrEnabled(enabled);
    }

    public boolean isVoNrEnabled() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isVoNrEnabled(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "isVoNrEnabled RemoteException", ex);
            ex.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean semIsVoNrEnabled() {
        return isVoNrEnabled();
    }

    @SystemApi
    public void reportDefaultNetworkStatus(boolean report) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.carrierActionReportDefaultNetworkStatus(
                        getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), report);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#carrierActionReportDefaultNetworkStatus", e);
        }
    }

    @SystemApi
    public void resetAllCarrierActions() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.carrierActionResetAll(
                        getSubId(SubscriptionManager.getDefaultDataSubscriptionId()));
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#carrierActionResetAll", e);
        }
        setRadioEnabled(true);
    }

    @Deprecated
    public void setPolicyDataEnabled(boolean enabled) {
        try {
            setDataEnabledForReason(1, enabled);
        } catch (RuntimeException e) {
            Log.e(TAG, "Error calling setDataEnabledForReason e:" + e);
        }
    }

    public void setDataEnabledForReason(int reason, boolean enabled) {
        setDataEnabledForReason(getSubId(), reason, enabled);
    }

    private void setDataEnabledForReason(int subId, int reason, boolean enabled) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.setDataEnabledForReason(subId, reason, enabled, getOpPackageName());
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "Telephony#setDataEnabledForReason RemoteException", ex);
            ex.rethrowFromSystemServer();
        }
    }

    public boolean isDataEnabledForReason(int reason) {
        return isDataEnabledForReason(getSubId(), reason);
    }

    private boolean isDataEnabledForReason(int subId, int reason) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.isDataEnabledForReason(subId, reason);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "Telephony#isDataEnabledForReason RemoteException", ex);
            ex.rethrowFromSystemServer();
            return false;
        }
    }

    public List<ClientRequestStats> getClientRequestStats(int subId) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getClientRequestStats(
                        getOpPackageName(), getAttributionTag(), subId);
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getClientRequestStats", e);
            return null;
        }
    }

    @SystemApi
    public boolean getEmergencyCallbackMode() {
        return getEmergencyCallbackMode(getSubId());
    }

    public boolean getEmergencyCallbackMode(int subId) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return false;
            }
            return telephony.getEmergencyCallbackMode(subId);
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getEmergencyCallbackMode", e);
            return false;
        }
    }

    public boolean isManualNetworkSelectionAllowed() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isManualNetworkSelectionAllowed(getSubId());
            }
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isManualNetworkSelectionAllowed", e);
            return true;
        }
    }

    public SignalStrength getSignalStrength() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getSignalStrength(getSubId());
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getSignalStrength", e);
            return null;
        }
    }

    public boolean isDataConnectionAllowed() {
        try {
            int subId = getSubId(SubscriptionManager.getDefaultDataSubscriptionId());
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return false;
            }
            boolean retVal = telephony.isDataEnabled(subId);
            return retVal;
        } catch (RemoteException e) {
            Log.e(TAG, "Error isDataConnectionAllowed", e);
            return false;
        }
    }

    public boolean isDataCapable() {
        if (this.mContext == null) {
            return true;
        }
        return this.mContext.getResources().getBoolean(R.bool.config_mobile_data_capable);
    }

    @Deprecated
    public void setCarrierTestOverride(
            String mccmnc,
            String imsi,
            String iccid,
            String gid1,
            String gid2,
            String plmn,
            String spn) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setCarrierTestOverride(
                        getSubId(), mccmnc, imsi, iccid, gid1, gid2, plmn, spn, null, null);
            }
        } catch (RemoteException e) {
        }
    }

    public void setCarrierTestOverride(
            String mccmnc,
            String imsi,
            String iccid,
            String gid1,
            String gid2,
            String plmn,
            String spn,
            String carrierPriviledgeRules,
            String apn) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setCarrierTestOverride(
                        getSubId(),
                        mccmnc,
                        imsi,
                        iccid,
                        gid1,
                        gid2,
                        plmn,
                        spn,
                        carrierPriviledgeRules,
                        apn);
            }
        } catch (RemoteException e) {
        }
    }

    public int getCarrierIdListVersion() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getCarrierIdListVersion(getSubId());
            }
            return -1;
        } catch (RemoteException e) {
            return -1;
        }
    }

    public int getNumberOfModemsWithSimultaneousDataConnections() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getNumberOfModemsWithSimultaneousDataConnections(
                        getSubId(), getOpPackageName(), getAttributionTag());
            }
            return 0;
        } catch (RemoteException e) {
            return 0;
        }
    }

    @SystemApi
    public boolean setOpportunisticNetworkState(boolean enable) {
        String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        try {
            IOns iOpportunisticNetworkService = getIOns();
            if (iOpportunisticNetworkService == null) {
                return false;
            }
            boolean ret = iOpportunisticNetworkService.setEnable(enable, pkgForDebug);
            return ret;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "enableOpportunisticNetwork RemoteException", ex);
            return false;
        }
    }

    @SystemApi
    public boolean isOpportunisticNetworkEnabled() {
        String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        try {
            IOns iOpportunisticNetworkService = getIOns();
            if (iOpportunisticNetworkService == null) {
                return false;
            }
            boolean isEnabled = iOpportunisticNetworkService.isEnabled(pkgForDebug);
            return isEnabled;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "enableOpportunisticNetwork RemoteException", ex);
            return false;
        }
    }

    public long getSupportedRadioAccessFamily() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return 0L;
            }
            return telephony.getRadioAccessFamily(getSlotIndex(), getOpPackageName());
        } catch (RemoteException e) {
            return 0L;
        } catch (NullPointerException e2) {
            return 0L;
        }
    }

    @SystemApi
    public void notifyOtaEmergencyNumberDbInstalled() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.notifyOtaEmergencyNumberDbInstalled();
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "notifyOtaEmergencyNumberDatabaseInstalled RemoteException", ex);
            ex.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void updateOtaEmergencyNumberDbFilePath(ParcelFileDescriptor otaParcelFileDescriptor) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.updateOtaEmergencyNumberDbFilePath(otaParcelFileDescriptor);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "updateOtaEmergencyNumberDbFilePath RemoteException", ex);
            ex.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void resetOtaEmergencyNumberDbFilePath() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.resetOtaEmergencyNumberDbFilePath();
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "resetOtaEmergencyNumberDbFilePath RemoteException", ex);
            ex.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public boolean isEmergencyAssistanceEnabled() {
        this.mContext.enforceCallingOrSelfPermission(
                Manifest.permission.READ_PRIVILEGED_PHONE_STATE, "isEmergencyAssistanceEnabled");
        return true;
    }

    @SystemApi
    public String getEmergencyAssistancePackageName() {
        if (!isEmergencyAssistanceEnabled() || !isVoiceCapable()) {
            throw new IllegalStateException(
                    "isEmergencyAssistanceEnabled() is false or device not voice capable.");
        }
        return ((RoleManager) this.mContext.getSystemService(RoleManager.class))
                .getEmergencyRoleHolder(this.mContext.getUserId());
    }

    public Map<Integer, List<EmergencyNumber>> getEmergencyNumberList() {
        Map<Integer, List<EmergencyNumber>> emergencyNumberList = new HashMap<>();
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getEmergencyNumberList(
                        this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "getEmergencyNumberList RemoteException", ex);
            ex.rethrowAsRuntimeException();
            return emergencyNumberList;
        }
    }

    public Map<Integer, List<EmergencyNumber>> getEmergencyNumberList(int categories) {
        Map<Integer, List<EmergencyNumber>> emergencyNumberListForCategories = new HashMap<>();
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                Map<Integer, List<EmergencyNumber>> emergencyNumberList =
                        telephony.getEmergencyNumberList(
                                this.mContext.getOpPackageName(),
                                this.mContext.getAttributionTag());
                Map<Integer, List<EmergencyNumber>> emergencyNumberListForCategories2 =
                        filterEmergencyNumbersByCategories(emergencyNumberList, categories);
                return emergencyNumberListForCategories2;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "getEmergencyNumberList with Categories RemoteException", ex);
            ex.rethrowAsRuntimeException();
            return emergencyNumberListForCategories;
        }
    }

    public Map<Integer, List<EmergencyNumber>> filterEmergencyNumbersByCategories(
            Map<Integer, List<EmergencyNumber>> emergencyNumberList, int categories) {
        Map<Integer, List<EmergencyNumber>> emergencyNumberListForCategories = new HashMap<>();
        if (emergencyNumberList != null) {
            for (Integer subscriptionId : emergencyNumberList.keySet()) {
                List<EmergencyNumber> allNumbersForSub = emergencyNumberList.get(subscriptionId);
                List<EmergencyNumber> numbersForCategoriesPerSub = new ArrayList<>();
                for (EmergencyNumber number : allNumbersForSub) {
                    if (number.isInEmergencyServiceCategories(categories)) {
                        numbersForCategoriesPerSub.add(number);
                    }
                }
                emergencyNumberListForCategories.put(subscriptionId, numbersForCategoriesPerSub);
            }
        }
        return emergencyNumberListForCategories;
    }

    public boolean isEmergencyNumber(String number) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isEmergencyNumber(number, true);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "isEmergencyNumber RemoteException", ex);
            ex.rethrowAsRuntimeException();
            return false;
        }
    }

    public boolean isEmergencyNumber(int subId, String number) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.isEmergencyNumberBySubId(subId, number, true);
            }
            throw new IllegalStateException("SemTelephonyService is null");
        } catch (RemoteException ex) {
            Log.e(TAG, "isEmergencyNumberBySubId RemoteException", ex);
            ex.rethrowAsRuntimeException();
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public boolean isPotentialEmergencyNumber(String number) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isEmergencyNumber(number, false);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "isEmergencyNumber RemoteException", ex);
            ex.rethrowAsRuntimeException();
            return false;
        }
    }

    @Deprecated
    public boolean isPotentialEmergencyNumber(int subId, String number) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.isEmergencyNumberBySubId(subId, number, false);
            }
            throw new IllegalStateException("SemTelephonyService is null");
        } catch (RemoteException ex) {
            Log.e(TAG, "isEmergencyNumberBySubId RemoteException", ex);
            ex.rethrowAsRuntimeException();
            return false;
        }
    }

    @SystemApi
    public int getEmergencyNumberDbVersion() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getEmergencyNumberDbVersion(getSubId());
            }
            return -1;
        } catch (RemoteException ex) {
            Log.e(TAG, "getEmergencyNumberDbVersion RemoteException", ex);
            ex.rethrowAsRuntimeException();
            return -1;
        }
    }

    public void setPreferredOpportunisticDataSubscription(
            int subId,
            boolean needValidation,
            final Executor executor,
            final Consumer<Integer> callback) {
        String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        try {
            IOns iOpportunisticNetworkService = getIOns();
            if (iOpportunisticNetworkService == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Opportunistic Network Service is null");
                }
                throw new RemoteException("Null Opportunistic Network Service!");
            }
            ISetOpportunisticDataCallback callbackStub = new AnonymousClass11(executor, callback);
            iOpportunisticNetworkService.setPreferredDataSubscriptionId(
                    subId, needValidation, callbackStub, pkgForDebug);
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "setPreferredOpportunisticDataSubscription RemoteException", ex);
            if (executor == null || callback == null) {
                return;
            }
            runOnBackgroundThread(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda17
                        @Override // java.lang.Runnable
                        public final void run() {
                            executor.execute(
                                    new Runnable() { // from class:
                                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda10
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            TelephonyManager
                                                    .lambda$setPreferredOpportunisticDataSubscription$16(
                                                            r1);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$11, reason: invalid class name */
    class AnonymousClass11 extends ISetOpportunisticDataCallback.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass11(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.ISetOpportunisticDataCallback
        public void onComplete(final int result) {
            if (this.val$executor == null || this.val$callback == null) {
                return;
            }
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$11$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(Integer.valueOf(result));
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    static /* synthetic */ void lambda$setPreferredOpportunisticDataSubscription$16(
            Consumer callback) {
        if (Compatibility.isChangeEnabled(CALLBACK_ON_MORE_ERROR_CODE_CHANGE)) {
            callback.accept(4);
        } else {
            callback.accept(2);
        }
    }

    public int getPreferredOpportunisticDataSubscription() {
        String packageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        String attributionTag = this.mContext != null ? this.mContext.getAttributionTag() : null;
        try {
            IOns iOpportunisticNetworkService = getIOns();
            if (iOpportunisticNetworkService == null) {
                return -1;
            }
            int subId =
                    iOpportunisticNetworkService.getPreferredDataSubscriptionId(
                            packageName, attributionTag);
            return subId;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getPreferredDataSubscriptionId RemoteException", ex);
            return -1;
        }
    }

    public void updateAvailableNetworks(
            List<AvailableNetworkInfo> availableNetworks,
            final Executor executor,
            final Consumer<Integer> callback) {
        String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        Objects.requireNonNull(availableNetworks, "availableNetworks must not be null.");
        try {
            IOns iOpportunisticNetworkService = getIOns();
            if (iOpportunisticNetworkService == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Opportunistic Network Service is null");
                }
                throw new RemoteException("Null Opportunistic Network Service!");
            }
            IUpdateAvailableNetworksCallback callbackStub =
                    new AnonymousClass12(executor, callback);
            iOpportunisticNetworkService.updateAvailableNetworks(
                    availableNetworks, callbackStub, pkgForDebug);
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "updateAvailableNetworks RemoteException", ex);
            if (executor == null || callback == null) {
                return;
            }
            runOnBackgroundThread(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda12
                        @Override // java.lang.Runnable
                        public final void run() {
                            executor.execute(
                                    new Runnable() { // from class:
                                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda6
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            TelephonyManager.lambda$updateAvailableNetworks$18(r1);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$12, reason: invalid class name */
    class AnonymousClass12 extends IUpdateAvailableNetworksCallback.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass12(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IUpdateAvailableNetworksCallback
        public void onComplete(final int result) {
            if (this.val$executor == null || this.val$callback == null) {
                return;
            }
            final Executor executor = this.val$executor;
            final Consumer consumer = this.val$callback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // android.telephony.TelephonyManager$12$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                                     // android.telephony.TelephonyManager$12$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    static /* synthetic */ void lambda$updateAvailableNetworks$18(Consumer callback) {
        if (Compatibility.isChangeEnabled(CALLBACK_ON_MORE_ERROR_CODE_CHANGE)) {
            callback.accept(9);
        } else {
            callback.accept(1);
        }
    }

    @SystemApi
    public boolean enableModemForSlot(int slotIndex, boolean enable) {
        if (this.mContext != null) {
            com.android.telephony.Rlog.i(
                    TAG,
                    "enableModemForSlot is called. package: "
                            + this.mContext.getOpPackageName()
                            + " , enable: "
                            + enable
                            + " , slotIndex: "
                            + slotIndex);
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                return false;
            }
            boolean ret = telephony.enableModemForSlot(slotIndex, enable);
            return ret;
        } catch (RemoteException ex) {
            Log.e(TAG, "enableModem RemoteException", ex);
            return false;
        }
    }

    public boolean isModemEnabledForSlot(int slotIndex) {
        if (this.mContext != null) {
            com.android.telephony.Rlog.i(
                    TAG,
                    "isModemEnabledForSlot is called. package: "
                            + this.mContext.getOpPackageName()
                            + " , slotIndex: "
                            + slotIndex);
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isModemEnabledForSlot(
                        slotIndex,
                        this.mContext.getOpPackageName(),
                        this.mContext.getAttributionTag());
            }
            return false;
        } catch (RemoteException ex) {
            Log.e(TAG, "enableModem RemoteException", ex);
            return false;
        }
    }

    @SystemApi
    public void setMultiSimCarrierRestriction(boolean isMultiSimCarrierRestricted) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.setMultiSimCarrierRestriction(isMultiSimCarrierRestricted);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "setMultiSimCarrierRestriction RemoteException", e);
        }
    }

    public int isMultiSimSupported() {
        if (getSupportedModemCount() < 2) {
            return 1;
        }
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.isMultiSimSupported(getOpPackageName(), getAttributionTag());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "isMultiSimSupported RemoteException", e);
        }
        return 1;
    }

    public void switchMultiSimConfig(int numOfSims) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.switchMultiSimConfig(numOfSims);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "switchMultiSimConfig RemoteException", ex);
        }
    }

    public boolean doesSwitchMultiSimConfigTriggerReboot() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.doesSwitchMultiSimConfigTriggerReboot(
                        getSubId(), getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "doesSwitchMultiSimConfigTriggerReboot RemoteException", e);
            return false;
        }
    }

    @Deprecated
    public Pair<Integer, Integer> getRadioHalVersion() {
        return getHalVersion(0);
    }

    public Pair<Integer, Integer> getHalVersion(int halService) {
        ITelephony service;
        try {
            service = getITelephony();
        } catch (RemoteException e) {
            Log.e(TAG, "getHalVersion() RemoteException", e);
            e.rethrowAsRuntimeException();
        }
        if (service != null) {
            int version = service.getHalVersion(halService);
            if (version != -1) {
                return new Pair<>(Integer.valueOf(version / 100), Integer.valueOf(version % 100));
            }
            return HAL_VERSION_UNKNOWN;
        }
        throw new IllegalStateException("telephony service is null.");
    }

    @SystemApi
    public int getCarrierPrivilegeStatus(int uid) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getCarrierPrivilegeStatusForUid(getSubId(), uid);
            }
            return 0;
        } catch (RemoteException ex) {
            Log.e(TAG, "getCarrierPrivilegeStatus RemoteException", ex);
            return 0;
        }
    }

    public List<ApnSetting> getDevicePolicyOverrideApns(Context context) {
        Cursor cursor =
                context.getContentResolver()
                        .query(Telephony.Carriers.DPC_URI, null, null, null, null);
        try {
            if (cursor == null) {
                List<ApnSetting> emptyList = Collections.emptyList();
                if (cursor != null) {
                    cursor.close();
                }
                return emptyList;
            }
            List<ApnSetting> apnList = new ArrayList<>();
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                ApnSetting apn = ApnSetting.makeApnSetting(cursor);
                apnList.add(apn);
            }
            if (cursor != null) {
                cursor.close();
            }
            return apnList;
        } catch (Throwable th) {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public int addDevicePolicyOverrideApn(Context context, ApnSetting apnSetting) {
        Uri resultUri =
                context.getContentResolver()
                        .insert(Telephony.Carriers.DPC_URI, apnSetting.toContentValues());
        if (resultUri == null) {
            return -1;
        }
        try {
            int resultId = Integer.parseInt(resultUri.getLastPathSegment());
            return resultId;
        } catch (NumberFormatException e) {
            com.android.telephony.Rlog.e(
                    TAG,
                    "Failed to parse inserted override APN id: " + resultUri.getLastPathSegment());
            return -1;
        }
    }

    public boolean modifyDevicePolicyOverrideApn(
            Context context, int apnId, ApnSetting apnSetting) {
        return context.getContentResolver()
                        .update(
                                Uri.withAppendedPath(
                                        Telephony.Carriers.DPC_URI, Integer.toString(apnId)),
                                apnSetting.toContentValues(),
                                null,
                                null)
                > 0;
    }

    @SystemApi
    public boolean isDataEnabledForApn(int apnType) {
        String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.isDataEnabledForApn(apnType, getSubId(), pkgForDebug);
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "Telephony#isDataEnabledForApn RemoteException" + ex);
            return false;
        }
    }

    @SystemApi
    public boolean isApnMetered(int apnType) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.isApnMetered(apnType, getSubId());
            }
            return true;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "Telephony#isApnMetered RemoteException" + ex);
            return true;
        }
    }

    @SystemApi
    public void setSystemSelectionChannels(
            List<RadioAccessSpecifier> specifiers, Executor executor, Consumer<Boolean> callback) {
        Objects.requireNonNull(specifiers, "Specifiers must not be null.");
        Objects.requireNonNull(executor, "Executor must not be null.");
        Objects.requireNonNull(callback, "Callback must not be null.");
        setSystemSelectionChannelsInternal(specifiers, executor, callback);
    }

    @SystemApi
    public void setSystemSelectionChannels(List<RadioAccessSpecifier> specifiers) {
        Objects.requireNonNull(specifiers, "Specifiers must not be null.");
        setSystemSelectionChannelsInternal(specifiers, null, null);
    }

    /* renamed from: android.telephony.TelephonyManager$13, reason: invalid class name */
    class AnonymousClass13 extends IBooleanConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass13(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IBooleanConsumer
        public void accept(final boolean result) {
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$13$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(Boolean.valueOf(result));
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    private void setSystemSelectionChannelsInternal(
            List<RadioAccessSpecifier> specifiers, Executor executor, Consumer<Boolean> callback) {
        com.android.internal.telephony.IBooleanConsumer aidlConsumer =
                callback == null ? null : new AnonymousClass13(executor, callback);
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.setSystemSelectionChannels(specifiers, getSubId(), aidlConsumer);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "Telephony#setSystemSelectionChannels RemoteException" + ex);
        }
    }

    @SystemApi
    public List<RadioAccessSpecifier> getSystemSelectionChannels() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getSystemSelectionChannels(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "Telephony#getSystemSelectionChannels RemoteException" + ex);
            return new ArrayList();
        }
    }

    @SystemApi
    public boolean matchesCurrentSimOperator(String mccmnc, int mvnoType, String mvnoMatchData) {
        ITelephony service;
        try {
            if (mccmnc.equals(getSimOperator()) && (service = getITelephony()) != null) {
                return service.isMvnoMatched(getSlotIndex(), mvnoType, mvnoMatchData);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "Telephony#matchesCurrentSimOperator RemoteException" + ex);
        }
        return false;
    }

    @SystemApi
    public void getCallForwarding(
            int callForwardingReason, Executor executor, CallForwardingInfoCallback callback) {
        if (callForwardingReason < 0 || callForwardingReason > 5) {
            throw new IllegalArgumentException("callForwardingReason is out of range");
        }
        ICallForwardingInfoCallback internalCallback = new AnonymousClass14(executor, callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.getCallForwarding(getSubId(), callForwardingReason, internalCallback);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getCallForwarding RemoteException", ex);
            ex.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.TelephonyManager$14, reason: invalid class name */
    class AnonymousClass14 extends ICallForwardingInfoCallback.Stub {
        final /* synthetic */ CallForwardingInfoCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass14(Executor executor, CallForwardingInfoCallback callForwardingInfoCallback) {
            this.val$executor = executor;
            this.val$callback = callForwardingInfoCallback;
        }

        @Override // com.android.internal.telephony.ICallForwardingInfoCallback
        public void onCallForwardingInfoAvailable(final CallForwardingInfo info) {
            Executor executor = this.val$executor;
            final CallForwardingInfoCallback callForwardingInfoCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$14$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.TelephonyManager$14$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            TelephonyManager.CallForwardingInfoCallback.this
                                                    .onCallForwardingInfoAvailable(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // com.android.internal.telephony.ICallForwardingInfoCallback
        public void onError(final int error) {
            Executor executor = this.val$executor;
            final CallForwardingInfoCallback callForwardingInfoCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$14$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.TelephonyManager$14$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            TelephonyManager.CallForwardingInfoCallback.this
                                                    .onError(r2);
                                        }
                                    });
                        }
                    });
        }
    }

    @SystemApi
    public void setCallForwarding(
            CallForwardingInfo callForwardingInfo,
            Executor executor,
            Consumer<Integer> resultListener) {
        if (callForwardingInfo == null) {
            throw new IllegalArgumentException("callForwardingInfo is null");
        }
        int callForwardingReason = callForwardingInfo.getReason();
        if (callForwardingReason < 0 || callForwardingReason > 5) {
            throw new IllegalArgumentException("callForwardingReason is out of range");
        }
        if (callForwardingInfo.isEnabled()) {
            if (callForwardingInfo.getNumber() == null) {
                throw new IllegalArgumentException("callForwarding number is null");
            }
            if (callForwardingReason == 2 && callForwardingInfo.getTimeoutSeconds() <= 0) {
                throw new IllegalArgumentException("callForwarding timeout isn't positive");
            }
        }
        if (resultListener != null) {
            Objects.requireNonNull(executor);
        }
        com.android.internal.telephony.IIntegerConsumer internalCallback =
                new AnonymousClass15(executor, resultListener);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setCallForwarding(getSubId(), callForwardingInfo, internalCallback);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "setCallForwarding RemoteException", ex);
            ex.rethrowAsRuntimeException();
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "setCallForwarding NPE", ex2);
            throw ex2;
        }
    }

    /* renamed from: android.telephony.TelephonyManager$15, reason: invalid class name */
    class AnonymousClass15 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass15(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$15$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.TelephonyManager$15$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    @SystemApi
    public void getCallWaitingStatus(Executor executor, Consumer<Integer> resultListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(resultListener);
        com.android.internal.telephony.IIntegerConsumer internalCallback =
                new AnonymousClass16(executor, resultListener);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.getCallWaitingStatus(getSubId(), internalCallback);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getCallWaitingStatus RemoteException", ex);
            ex.rethrowAsRuntimeException();
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "getCallWaitingStatus NPE", ex2);
            throw ex2;
        }
    }

    /* renamed from: android.telephony.TelephonyManager$16, reason: invalid class name */
    class AnonymousClass16 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass16(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$16$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.TelephonyManager$16$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    @SystemApi
    public void setCallWaitingEnabled(
            boolean enabled, Executor executor, Consumer<Integer> resultListener) {
        if (resultListener != null) {
            Objects.requireNonNull(executor);
        }
        com.android.internal.telephony.IIntegerConsumer internalCallback =
                new AnonymousClass17(executor, resultListener);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setCallWaitingStatus(getSubId(), enabled, internalCallback);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "setCallWaitingStatus RemoteException", ex);
            ex.rethrowAsRuntimeException();
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "setCallWaitingStatus NPE", ex2);
            throw ex2;
        }
    }

    /* renamed from: android.telephony.TelephonyManager$17, reason: invalid class name */
    class AnonymousClass17 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass17(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$17$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.TelephonyManager$17$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    @SystemApi
    public void setMobileDataPolicyEnabled(int policy, boolean enabled) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.setMobileDataPolicyEnabled(getSubId(), policy, enabled);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "Telephony#setMobileDataPolicyEnabled RemoteException" + ex);
        }
    }

    @SystemApi
    public boolean isMobileDataPolicyEnabled(int policy) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.isMobileDataPolicyEnabled(getSubId(), policy);
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "Telephony#isMobileDataPolicyEnabled RemoteException" + ex);
            return false;
        }
    }

    @SystemApi
    public boolean isIccLockEnabled() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isIccLockEnabled(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "isIccLockEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @SystemApi
    public PinResult setIccLockEnabled(boolean enabled, String pin) {
        Preconditions.checkNotNull(pin, "setIccLockEnabled pin can't be null.");
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                int result = telephony.setIccLockEnabled(getSubId(), enabled, pin);
                if (result == Integer.MAX_VALUE) {
                    return new PinResult(0, 0);
                }
                if (result < 0) {
                    return PinResult.getDefaultFailedResult();
                }
                return new PinResult(1, result);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "setIccLockEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return PinResult.getDefaultFailedResult();
        }
    }

    @SystemApi
    public PinResult changeIccLockPin(String oldPin, String newPin) {
        Preconditions.checkNotNull(oldPin, "changeIccLockPin oldPin can't be null.");
        Preconditions.checkNotNull(newPin, "changeIccLockPin newPin can't be null.");
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                int result = telephony.changeIccLockPassword(getSubId(), oldPin, newPin);
                if (result == Integer.MAX_VALUE) {
                    return new PinResult(0, 0);
                }
                if (result < 0) {
                    return PinResult.getDefaultFailedResult();
                }
                return new PinResult(1, result);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "changeIccLockPin RemoteException", e);
            e.rethrowFromSystemServer();
            return PinResult.getDefaultFailedResult();
        }
    }

    public void notifyUserActivity() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.userActivity();
            }
        } catch (RemoteException e) {
            Log.w(TAG, "notifyUserActivity exception: " + e.getMessage());
        }
    }

    @SystemApi
    public int setNrDualConnectivityState(int nrDualConnectivityState) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.setNrDualConnectivityState(getSubId(), nrDualConnectivityState);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "setNrDualConnectivityState RemoteException", ex);
            ex.rethrowFromSystemServer();
            return 4;
        }
    }

    @SystemApi
    public boolean isNrDualConnectivityEnabled() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isNrDualConnectivityEnabled(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "isNRDualConnectivityEnabled RemoteException", ex);
            ex.rethrowFromSystemServer();
            return false;
        }
    }

    private static class DeathRecipient implements IBinder.DeathRecipient {
        private DeathRecipient() {}

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            TelephonyManager.resetServiceCache();
        }
    }

    public static void resetServiceCache() {
        synchronized (sCacheLock) {
            if (sITelephony != null) {
                sITelephony.asBinder().unlinkToDeath(sServiceDeath, 0);
                sITelephony = null;
            }
            if (sISemTelephony != null) {
                sISemTelephony.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISemTelephony = null;
            }
            if (sISub != null) {
                sISub.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISub = null;
                SubscriptionManager.clearCaches();
            }
            if (sISms != null) {
                sISms.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISms = null;
            }
            if (sIPhoneSubInfo != null) {
                sIPhoneSubInfo.asBinder().unlinkToDeath(sServiceDeath, 0);
                sIPhoneSubInfo = null;
            }
            if (sISemPhoneSubInfo != null) {
                sISemPhoneSubInfo.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISemPhoneSubInfo = null;
            }
        }
    }

    static IPhoneSubInfo getSubscriberInfoService() {
        if (!sServiceHandleCacheEnabled) {
            return IPhoneSubInfo.Stub.asInterface(
                    TelephonyFrameworkInitializer.getTelephonyServiceManager()
                            .getPhoneSubServiceRegisterer()
                            .get());
        }
        if (sIPhoneSubInfo == null) {
            IPhoneSubInfo temp =
                    IPhoneSubInfo.Stub.asInterface(
                            TelephonyFrameworkInitializer.getTelephonyServiceManager()
                                    .getPhoneSubServiceRegisterer()
                                    .get());
            synchronized (sCacheLock) {
                if (sIPhoneSubInfo == null && temp != null) {
                    try {
                        sIPhoneSubInfo = temp;
                        sIPhoneSubInfo.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception e) {
                        sIPhoneSubInfo = null;
                    }
                }
            }
        }
        return sIPhoneSubInfo;
    }

    static ISemPhoneSubInfo getSemSubscriberInfoService() {
        if (!sServiceHandleCacheEnabled) {
            return ISemPhoneSubInfo.Stub.asInterface(ServiceManager.getService("isemphonesubinfo"));
        }
        if (sISemPhoneSubInfo == null) {
            ISemPhoneSubInfo temp =
                    ISemPhoneSubInfo.Stub.asInterface(
                            ServiceManager.getService("isemphonesubinfo"));
            synchronized (sCacheLock) {
                if (sISemPhoneSubInfo == null && temp != null) {
                    try {
                        sISemPhoneSubInfo = temp;
                        sISemPhoneSubInfo.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception e) {
                        sISemPhoneSubInfo = null;
                    }
                }
            }
        }
        return sISemPhoneSubInfo;
    }

    static ISub getSubscriptionService() {
        if (!sServiceHandleCacheEnabled) {
            return ISub.Stub.asInterface(
                    TelephonyFrameworkInitializer.getTelephonyServiceManager()
                            .getSubscriptionServiceRegisterer()
                            .get());
        }
        if (sISub == null) {
            ISub temp =
                    ISub.Stub.asInterface(
                            TelephonyFrameworkInitializer.getTelephonyServiceManager()
                                    .getSubscriptionServiceRegisterer()
                                    .get());
            synchronized (sCacheLock) {
                if (sISub == null && temp != null) {
                    try {
                        sISub = temp;
                        sISub.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception e) {
                        sISub = null;
                    }
                }
            }
        }
        return sISub;
    }

    static ISms getSmsService() {
        if (!sServiceHandleCacheEnabled) {
            return ISms.Stub.asInterface(
                    TelephonyFrameworkInitializer.getTelephonyServiceManager()
                            .getSmsServiceRegisterer()
                            .get());
        }
        if (sISms == null) {
            ISms temp =
                    ISms.Stub.asInterface(
                            TelephonyFrameworkInitializer.getTelephonyServiceManager()
                                    .getSmsServiceRegisterer()
                                    .get());
            synchronized (sCacheLock) {
                if (sISms == null && temp != null) {
                    try {
                        sISms = temp;
                        sISms.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception e) {
                        sISms = null;
                    }
                }
            }
        }
        return sISms;
    }

    public static void disableServiceHandleCaching() {
        sServiceHandleCacheEnabled = false;
    }

    public static void enableServiceHandleCaching() {
        sServiceHandleCacheEnabled = true;
    }

    public static void setupITelephonyForTest(ITelephony telephony) {
        sITelephony = telephony;
    }

    public static void setupIPhoneSubInfoForTest(IPhoneSubInfo iPhoneSubInfo) {
        synchronized (sCacheLock) {
            sIPhoneSubInfo = iPhoneSubInfo;
        }
    }

    public static void setupISubForTest(ISub iSub) {
        synchronized (sCacheLock) {
            sISub = iSub;
        }
    }

    public boolean canConnectTo5GInDsdsMode() {
        ITelephony telephony = getITelephony();
        if (telephony == null) {
            return true;
        }
        try {
            return telephony.canConnectTo5GInDsdsMode();
        } catch (RemoteException e) {
            return true;
        } catch (NullPointerException e2) {
            return true;
        }
    }

    public List<String> getEquivalentHomePlmns() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getEquivalentHomePlmns(
                        getSubId(), this.mContext.getOpPackageName(), getAttributionTag());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "Telephony#getEquivalentHomePlmns RemoteException" + ex);
            return Collections.emptyList();
        }
    }

    public boolean isRadioInterfaceCapabilitySupported(String capability) {
        if (capability == null) {
            return false;
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isRadioInterfaceCapabilitySupported(capability);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "Telephony#isRadioInterfaceCapabilitySupported RemoteException" + ex);
            return false;
        }
    }

    @SystemApi
    public int sendThermalMitigationRequest(ThermalMitigationRequest thermalMitigationRequest) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.sendThermalMitigationRequest(
                        getSubId(), thermalMitigationRequest, getOpPackageName());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            Log.e(TAG, "Telephony#thermalMitigationRequest RemoteException", ex);
            ex.rethrowFromSystemServer();
            return 4;
        }
    }

    public void registerTelephonyCallback(Executor executor, TelephonyCallback callback) {
        registerTelephonyCallback(getLocationData(), executor, callback);
    }

    private int getLocationData() {
        boolean renounceCoarseLocation =
                getRenouncedPermissions().contains(Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean renounceFineLocation =
                getRenouncedPermissions().contains(Manifest.permission.ACCESS_FINE_LOCATION);
        if (renounceCoarseLocation) {
            return 0;
        }
        if (renounceFineLocation) {
            return 1;
        }
        return 2;
    }

    public void registerTelephonyCallback(
            int includeLocationData, Executor executor, TelephonyCallback callback) {
        if (this.mContext == null) {
            throw new IllegalStateException("telephony service is null.");
        }
        if (executor == null || callback == null) {
            throw new IllegalArgumentException("TelephonyCallback and executor must be non-null");
        }
        this.mTelephonyRegistryMgr =
                (TelephonyRegistryManager)
                        this.mContext.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (this.mTelephonyRegistryMgr != null) {
            this.mTelephonyRegistryMgr.registerTelephonyCallback(
                    includeLocationData != 2,
                    includeLocationData == 0,
                    executor,
                    this.mSubId,
                    getOpPackageName(),
                    getAttributionTag(),
                    callback,
                    getITelephony() != null);
            return;
        }
        throw new IllegalStateException("telephony service is null.");
    }

    public void unregisterTelephonyCallback(TelephonyCallback callback) {
        if (this.mContext == null) {
            throw new IllegalStateException("telephony service is null.");
        }
        if (callback.callback == null) {
            return;
        }
        this.mTelephonyRegistryMgr =
                (TelephonyRegistryManager)
                        this.mContext.getSystemService(TelephonyRegistryManager.class);
        if (this.mTelephonyRegistryMgr != null) {
            this.mTelephonyRegistryMgr.unregisterTelephonyCallback(
                    this.mSubId,
                    getOpPackageName(),
                    getAttributionTag(),
                    callback,
                    getITelephony() != null);
            return;
        }
        throw new IllegalStateException("telephony service is null.");
    }

    @SystemApi
    public static class BootstrapAuthenticationCallback {
        public void onKeysAvailable(byte[] gbaKey, String transactionId) {}

        public void onAuthenticationFailure(int reason) {}
    }

    @SystemApi
    public void bootstrapAuthenticationRequest(
            int appType,
            Uri nafId,
            UaSecurityProtocolIdentifier securityProtocol,
            boolean forceBootStrapping,
            Executor e,
            final BootstrapAuthenticationCallback callback) {
        try {
            ITelephony service = getITelephony();
            if (service == null) {
                e.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyManager.BootstrapAuthenticationCallback.this
                                        .onAuthenticationFailure(2);
                            }
                        });
            } else {
                service.bootstrapAuthenticationRequest(
                        getSubId(),
                        appType,
                        nafId,
                        securityProtocol,
                        forceBootStrapping,
                        new AnonymousClass18(e, callback));
            }
        } catch (RemoteException exception) {
            Log.e(TAG, "Error calling ITelephony#bootstrapAuthenticationRequest", exception);
            e.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            TelephonyManager.BootstrapAuthenticationCallback.this
                                    .onAuthenticationFailure(2);
                        }
                    });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$18, reason: invalid class name */
    class AnonymousClass18 extends IBootstrapAuthenticationCallback.Stub {
        final /* synthetic */ BootstrapAuthenticationCallback val$callback;
        final /* synthetic */ Executor val$e;

        AnonymousClass18(
                Executor executor,
                BootstrapAuthenticationCallback bootstrapAuthenticationCallback) {
            this.val$e = executor;
            this.val$callback = bootstrapAuthenticationCallback;
        }

        @Override // android.telephony.IBootstrapAuthenticationCallback
        public void onKeysAvailable(int token, final byte[] gbaKey, final String transactionId) {
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$e;
                final BootstrapAuthenticationCallback bootstrapAuthenticationCallback =
                        this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$18$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyManager.BootstrapAuthenticationCallback.this
                                        .onKeysAvailable(gbaKey, transactionId);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        @Override // android.telephony.IBootstrapAuthenticationCallback
        public void onAuthenticationFailure(int token, final int reason) {
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$e;
                final BootstrapAuthenticationCallback bootstrapAuthenticationCallback =
                        this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$18$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyManager.BootstrapAuthenticationCallback.this
                                        .onAuthenticationFailure(reason);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    public static boolean isNetworkTypeValid(int networkType) {
        return networkType >= 0 && networkType <= 20;
    }

    public void setSignalStrengthUpdateRequest(SignalStrengthUpdateRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.setSignalStrengthUpdateRequest(getSubId(), request, getOpPackageName());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setSignalStrengthUpdateRequest", e);
        }
    }

    public void clearSignalStrengthUpdateRequest(SignalStrengthUpdateRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                service.clearSignalStrengthUpdateRequest(getSubId(), request, getOpPackageName());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#clearSignalStrengthUpdateRequest", e);
        }
    }

    @SystemApi
    public PhoneCapability getPhoneCapability() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getPhoneCapability();
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            ex.rethrowAsRuntimeException();
            if (getActiveModemCount() > 1) {
                return PhoneCapability.DEFAULT_DSDS_CAPABILITY;
            }
            return PhoneCapability.DEFAULT_SSSS_CAPABILITY;
        }
    }

    @SystemApi
    public int prepareForUnattendedReboot() {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.prepareForUnattendedReboot();
            }
            return 2;
        } catch (RemoteException e) {
            Log.e(TAG, "Telephony#prepareForUnattendedReboot RemoteException", e);
            e.rethrowFromSystemServer();
            return 2;
        }
    }

    public static class NetworkSlicingException extends Exception {
        public static final int ERROR_MODEM_ERROR = 2;
        public static final int ERROR_TIMEOUT = 1;
        public static final int SUCCESS = 0;
        private final int mErrorCode;

        @Retention(RetentionPolicy.SOURCE)
        public @interface NetworkSlicingError {}

        public NetworkSlicingException(int errorCode) {
            this.mErrorCode = errorCode;
        }

        @Override // java.lang.Throwable
        public String toString() {
            switch (this.mErrorCode) {
                case 1:
                    return "ERROR_TIMEOUT";
                case 2:
                    return "ERROR_MODEM_ERROR";
                default:
                    return DevicePolicyResources.UNDEFINED;
            }
        }
    }

    public class TimeoutException extends NetworkSlicingException {
        public TimeoutException(int errorCode) {
            super(errorCode);
        }
    }

    public class ModemErrorException extends NetworkSlicingException {
        public ModemErrorException(int errorCode) {
            super(errorCode);
        }
    }

    public void getNetworkSlicingConfiguration(
            Executor executor,
            OutcomeReceiver<NetworkSlicingConfig, NetworkSlicingException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            telephony.getSlicingConfig(new AnonymousClass19(null, executor, callback));
        } catch (RemoteException ex) {
            ex.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.TelephonyManager$19, reason: invalid class name */
    class AnonymousClass19 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass19(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle result) {
            if (resultCode == 1) {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$19$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyManager.AnonymousClass19.this.lambda$onReceiveResult$0(
                                        outcomeReceiver, resultCode);
                            }
                        });
            } else if (resultCode == 2) {
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$19$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyManager.AnonymousClass19.this.lambda$onReceiveResult$1(
                                        outcomeReceiver2, resultCode);
                            }
                        });
            } else {
                final NetworkSlicingConfig slicingConfig =
                        (NetworkSlicingConfig)
                                result.getParcelable(
                                        TelephonyManager.KEY_SLICING_CONFIG_HANDLE,
                                        NetworkSlicingConfig.class);
                Executor executor3 = this.val$executor;
                final OutcomeReceiver outcomeReceiver3 = this.val$callback;
                executor3.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$19$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                OutcomeReceiver.this.onResult(slicingConfig);
                            }
                        });
            }
        }

        public /* synthetic */ void lambda$onReceiveResult$0(
                OutcomeReceiver callback, int resultCode) {
            callback.onError(TelephonyManager.this.new TimeoutException(resultCode));
        }

        public /* synthetic */ void lambda$onReceiveResult$1(
                OutcomeReceiver callback, int resultCode) {
            callback.onError(TelephonyManager.this.new ModemErrorException(resultCode));
        }
    }

    public static String convertPremiumCapabilityToString(int capability) {
        switch (capability) {
            case 34:
                return "PRIORITIZE_LATENCY";
            default:
                return "UNKNOWN (" + capability + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public boolean isPremiumCapabilityAvailableForPurchase(int capability) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            return telephony.isPremiumCapabilityAvailableForPurchase(capability, getSubId());
        } catch (RemoteException ex) {
            ex.rethrowAsRuntimeException();
            return false;
        }
    }

    public static String convertPurchaseResultToString(int result) {
        switch (result) {
            case 1:
                return TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
            case 2:
                return "THROTTLED";
            case 3:
                return "ALREADY_PURCHASED";
            case 4:
                return "ALREADY_IN_PROGRESS";
            case 5:
                return "NOT_FOREGROUND";
            case 6:
                return "USER_CANCELED";
            case 7:
                return "CARRIER_DISABLED";
            case 8:
                return "CARRIER_ERROR";
            case 9:
                return "TIMEOUT";
            case 10:
                return "FEATURE_NOT_SUPPORTED";
            case 11:
                return "REQUEST_FAILED";
            case 12:
                return "NETWORK_NOT_AVAILABLE";
            case 13:
                return "ENTITLEMENT_CHECK_FAILED";
            case 14:
                return "NOT_DEFAULT_DATA_SUBSCRIPTION";
            case 15:
                return "PENDING_NETWORK_SETUP";
            default:
                return "UNKNOWN (" + result + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public void purchasePremiumCapability(
            int capability, Executor executor, Consumer<Integer> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        com.android.internal.telephony.IIntegerConsumer internalCallback =
                new AnonymousClass20(executor, callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                callback.accept(11);
            } else {
                telephony.purchasePremiumCapability(capability, internalCallback, getSubId());
            }
        } catch (RemoteException e) {
            callback.accept(11);
        }
    }

    /* renamed from: android.telephony.TelephonyManager$20, reason: invalid class name */
    class AnonymousClass20 extends IIntegerConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass20(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$20$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            consumer.accept(Integer.valueOf(result));
                        }
                    });
        }
    }

    @SystemApi
    public CellIdentity getLastKnownCellIdentity() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            return telephony.getLastKnownCellIdentity(
                    getSubId(), getOpPackageName(), getAttributionTag());
        } catch (RemoteException ex) {
            ex.rethrowAsRuntimeException();
            return null;
        }
    }

    @SystemApi
    public interface CarrierPrivilegesCallback {
        void onCarrierPrivilegesChanged(Set<String> set, Set<Integer> set2);

        default void onCarrierServiceChanged(
                String carrierServicePackageName, int carrierServiceUid) {}
    }

    @SystemApi
    public void setVoiceServiceStateOverride(boolean hasService) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                throw new IllegalStateException("Telephony service is null");
            }
            telephony.setVoiceServiceStateOverride(getSubId(), hasService, getOpPackageName());
        } catch (RemoteException ex) {
            ex.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void registerCarrierPrivilegesCallback(
            int logicalSlotIndex, Executor executor, CarrierPrivilegesCallback callback) {
        if (this.mContext == null) {
            throw new IllegalStateException("Telephony service is null");
        }
        if (executor == null || callback == null) {
            throw new IllegalArgumentException(
                    "CarrierPrivilegesCallback and executor must be non-null");
        }
        this.mTelephonyRegistryMgr =
                (TelephonyRegistryManager)
                        this.mContext.getSystemService(TelephonyRegistryManager.class);
        if (this.mTelephonyRegistryMgr == null) {
            throw new IllegalStateException("Telephony registry service is null");
        }
        this.mTelephonyRegistryMgr.addCarrierPrivilegesCallback(
                logicalSlotIndex, executor, callback);
    }

    @SystemApi
    public void unregisterCarrierPrivilegesCallback(CarrierPrivilegesCallback callback) {
        if (this.mContext == null) {
            throw new IllegalStateException("Telephony service is null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("CarrierPrivilegesCallback must be non-null");
        }
        this.mTelephonyRegistryMgr =
                (TelephonyRegistryManager)
                        this.mContext.getSystemService(TelephonyRegistryManager.class);
        if (this.mTelephonyRegistryMgr == null) {
            throw new IllegalStateException("Telephony registry service is null");
        }
        this.mTelephonyRegistryMgr.removeCarrierPrivilegesCallback(callback);
    }

    public void setRemovableEsimAsDefaultEuicc(boolean isDefault) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setRemovableEsimAsDefaultEuicc(isDefault, getOpPackageName());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error in setRemovableEsimAsDefault: " + e);
        }
    }

    public boolean isRemovableEsimDefaultEuicc() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isRemovableEsimDefaultEuicc(getOpPackageName());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error in isRemovableEsimDefaultEuicc: " + e);
            return false;
        }
    }

    public static int getSimStateForSlotIndex(int slotIndex) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getSimStateForSlotIndex(slotIndex);
            }
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "Error in getSimStateForSlotIndex: " + e);
            return 0;
        }
    }

    @SystemApi
    public static final class EmergencyCallDiagnosticData {
        private static long sUnsetLogcatStartTime = -1;
        private boolean mCollectLogcat;
        private boolean mCollectTelecomDumpsys;
        private boolean mCollectTelephonyDumpsys;
        private long mLogcatStartTimeMillis;

        public static final class Builder {
            private boolean mCollectTelecomDumpsys;
            private boolean mCollectTelephonyDumpsys;
            private long mLogcatStartTimeMillis = EmergencyCallDiagnosticData.sUnsetLogcatStartTime;

            public Builder setTelecomDumpsysCollectionEnabled(boolean collectTelecomDumpsys) {
                this.mCollectTelecomDumpsys = collectTelecomDumpsys;
                return this;
            }

            public Builder setTelephonyDumpsysCollectionEnabled(boolean collectTelephonyDumpsys) {
                this.mCollectTelephonyDumpsys = collectTelephonyDumpsys;
                return this;
            }

            public Builder setLogcatCollectionStartTimeMillis(long startTimeMillis) {
                this.mLogcatStartTimeMillis = startTimeMillis;
                return this;
            }

            public EmergencyCallDiagnosticData build() {
                return new EmergencyCallDiagnosticData(
                        this.mCollectTelecomDumpsys,
                        this.mCollectTelephonyDumpsys,
                        this.mLogcatStartTimeMillis);
            }
        }

        private EmergencyCallDiagnosticData(
                boolean collectTelecomDumpsys,
                boolean collectTelephonyDumpsys,
                long logcatStartTimeMillis) {
            this.mCollectTelecomDumpsys = collectTelecomDumpsys;
            this.mCollectTelephonyDumpsys = collectTelephonyDumpsys;
            this.mLogcatStartTimeMillis = logcatStartTimeMillis;
            this.mCollectLogcat = logcatStartTimeMillis != sUnsetLogcatStartTime;
        }

        public boolean isTelecomDumpsysCollectionEnabled() {
            return this.mCollectTelecomDumpsys;
        }

        public boolean isTelephonyDumpsysCollectionEnabled() {
            return this.mCollectTelephonyDumpsys;
        }

        public boolean isLogcatCollectionEnabled() {
            return this.mCollectLogcat;
        }

        public long getLogcatCollectionStartTimeMillis() {
            return this.mLogcatStartTimeMillis;
        }

        public String toString() {
            return "EmergencyCallDiagnosticData{mCollectTelecomDumpsys="
                    + this.mCollectTelecomDumpsys
                    + ", mCollectTelephonyDumpsys="
                    + this.mCollectTelephonyDumpsys
                    + ", mCollectLogcat="
                    + this.mCollectLogcat
                    + ", mLogcatStartTimeMillis="
                    + this.mLogcatStartTimeMillis
                    + '}';
        }
    }

    @SystemApi
    public void persistEmergencyCallDiagnosticData(
            String dropboxTag, EmergencyCallDiagnosticData data) {
        try {
            ITelephony telephony =
                    ITelephony.Stub.asInterface(
                            TelephonyFrameworkInitializer.getTelephonyServiceManager()
                                    .getTelephonyServiceRegisterer()
                                    .get());
            if (telephony != null) {
                telephony.persistEmergencyCallDiagnosticData(
                        dropboxTag,
                        data.isLogcatCollectionEnabled(),
                        data.getLogcatCollectionStartTimeMillis(),
                        data.isTelecomDumpsysCollectionEnabled(),
                        data.isTelephonyDumpsysCollectionEnabled());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error while persistEmergencyCallDiagnosticData: " + e);
        }
    }

    public void setNullCipherAndIntegrityEnabled(boolean enabled) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setNullCipherAndIntegrityEnabled(enabled);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "setNullCipherAndIntegrityEnabled RemoteException", ex);
            ex.rethrowFromSystemServer();
        }
    }

    public boolean isNullCipherAndIntegrityPreferenceEnabled() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isNullCipherAndIntegrityPreferenceEnabled();
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "isNullCipherAndIntegrityPreferenceEnabled RemoteException", ex);
            ex.rethrowFromSystemServer();
            return true;
        }
    }

    @SystemApi
    public void setEnableCellularIdentifierDisclosureNotifications(boolean enable) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setEnableCellularIdentifierDisclosureNotifications(enable);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "setEnableCellularIdentifierDisclosureNotifications RemoteException", ex);
            ex.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isCellularIdentifierDisclosureNotificationsEnabled() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isCellularIdentifierDisclosureNotificationsEnabled();
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "isCellularIdentifierDisclosureNotificationsEnabled RemoteException", ex);
            ex.rethrowFromSystemServer();
            return false;
        }
    }

    @SystemApi
    public void setNullCipherNotificationsEnabled(boolean enable) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setNullCipherNotificationsEnabled(enable);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "setEnableNullCipherNotifications RemoteException", ex);
            ex.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isNullCipherNotificationsEnabled() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isNullCipherNotificationsEnabled();
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "isNullCipherNotificationsEnabled RemoteException", ex);
            ex.rethrowFromSystemServer();
            return false;
        }
    }

    @SystemApi
    public List<CellBroadcastIdRange> getCellBroadcastIdRanges() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getCellBroadcastIdRanges(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
            return new ArrayList();
        }
    }

    /* renamed from: android.telephony.TelephonyManager$21, reason: invalid class name */
    class AnonymousClass21 extends IIntegerConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass21(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$callback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$21$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(Integer.valueOf(result));
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    @SystemApi
    public void setCellBroadcastIdRanges(
            List<CellBroadcastIdRange> ranges, Executor executor, Consumer<Integer> callback) {
        com.android.internal.telephony.IIntegerConsumer consumer =
                callback == null ? null : new AnonymousClass21(executor, callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setCellBroadcastIdRanges(getSubId(), ranges, consumer);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isDomainSelectionSupported() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isDomainSelectionSupported();
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.w(TAG, "RemoteException", ex);
            return false;
        }
    }

    public boolean isAospDomainSelectionService() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.isAospDomainSelectionService();
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.w(TAG, "RemoteException", ex);
            return false;
        }
    }

    public String getPrimaryImei() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                com.android.telephony.Rlog.e(
                        TAG, "getPrimaryImei(): IPhoneSubInfo instance is NULL");
                throw new IllegalStateException("Telephony service not available.");
            }
            return telephony.getPrimaryImei(getOpPackageName(), getAttributionTag());
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getPrimaryImei() RemoteException : " + ex);
            throw ex.rethrowAsRuntimeException();
        }
    }

    public String getSecondaryImei() {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony == null) {
                com.android.telephony.Rlog.e(
                        TAG, "getSecondaryImei(): ISemTelephony instance is NULL");
                throw new IllegalStateException("SemTelephony service not available.");
            }
            return semTelephony.getSecondaryImei(getOpPackageName(), getAttributionTag());
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getSecondaryImei() RemoteException : " + ex);
            throw ex.rethrowAsRuntimeException();
        }
    }

    public String getSatelliteImei() {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony == null) {
                com.android.telephony.Rlog.e(
                        TAG, "getSatelliteImei(): ISemTelephony instance is NULL");
                throw new IllegalStateException("SemTelephony service not available.");
            }
            return semTelephony.getSatelliteImei(getOpPackageName(), getAttributionTag());
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getSatelliteImei() RemoteException : " + ex);
            throw ex.rethrowAsRuntimeException();
        }
    }

    public static String simStateToString(int state) {
        switch (state) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "ABSENT";
            case 2:
                return "PIN_REQUIRED";
            case 3:
                return "PUK_REQUIRED";
            case 4:
                return "NETWORK_LOCKED";
            case 5:
                return "READY";
            case 6:
                return "NOT_READY";
            case 7:
                return "PERM_DISABLED";
            case 8:
                return "CARD_IO_ERROR";
            case 9:
                return "CARD_RESTRICTED";
            case 10:
                return "LOADED";
            case 11:
                return "PRESENT";
            case 12:
                return "PERSO_LOCKED";
            default:
                return "UNKNOWN(" + state + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    private void logWithCallerInfo(String s) {
        com.android.telephony.Rlog.d(
                TAG,
                s
                        + " / caller PID: "
                        + Process.myPid()
                        + ", UID: "
                        + Process.myUid()
                        + ", TID: "
                        + Process.myTid());
    }

    public boolean hasCall(String callType) {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return false;
            }
            return info.hasCall(callType);
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "hasCall is fail due to RemoteException. " + ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "hasCall is fail due to NullPointerException. " + ex2);
            return false;
        }
    }

    public void reloadTestEmergencyNumber() {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                semTelephony.reloadTestEmergencyNumber();
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "reloadTestEmergencyNumber is fail due to RemoteException. " + ex);
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "reloadTestEmergencyNumber is fail due to NullPointerException. " + ex2);
        }
    }

    public boolean setDrxMode(int drxMode) {
        if (Binder.getCallingUid() != 1000) {
            Log.e(TAG, "setDrxMode was accessed by non privileged user");
            throw new SecurityException("setDrxMode was accessed by non privileged user");
        }
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return false;
            }
            return info.setDrxMode(drxMode);
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "setDrxMode is fail due to RemoteException. " + ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "setDrxMode is fail due to NullPointerException. " + ex2);
            return false;
        }
    }

    public int getDrxMode() {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return 0;
            }
            return info.getDrxMode();
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getDrxMode is fail due to RemoteException. " + ex);
            return 0;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "getDrxMode is fail due to NullPointerException. " + ex2);
            return 0;
        }
    }

    public boolean semSetNrMode(int mode) {
        return semSetNrMode(Integer.MAX_VALUE, mode, false);
    }

    public boolean semSetNrMode(int phoneId, int mode, boolean force) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.setNrMode(
                        phoneId, mode, force, this.mContext.getOpPackageName());
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "semSetNrMode is fail due to RemoteException. " + ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semSetNrMode is fail due to NullPointerException. " + ex2);
            return false;
        }
    }

    public int semGetNrMode() {
        return semGetNrMode(Integer.MAX_VALUE);
    }

    public int semGetNrMode(int phoneId) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.getNrMode(phoneId);
            }
            return -1;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "semGetNrMode is fail due to RemoteException. " + ex);
            return -1;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semGetNrMode is fail due to NullPointerException. " + ex2);
            return -1;
        }
    }

    public int getSupportedRat(int phoneId) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony
                        .getVendorConfigState(phoneId)
                        .getDataAsInt(VendorConfigurationState.CONFIG_SUPPORTED_RAT);
            }
            return -1;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "getSupportedRat is fail due to RemoteException. " + ex);
            return -1;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "getSupportedRat is fail due to NullPointerException. " + ex2);
            return -1;
        }
    }

    public ServiceState semGetServiceState(int phoneId) {
        try {
            ITelephony service = getITelephony();
            if (service != null) {
                return service.getServiceStateForSlot(
                        phoneId, false, false, getOpPackageName(), getAttributionTag());
            }
            return null;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "semGetServiceState is fail due to RemoteException. " + ex);
            return null;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semGetServiceState is fail due to NullPointerException. " + ex2);
            return null;
        }
    }

    public int semGetDataServiceState() {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return 0;
            }
            int state = info.getDataServiceState();
            return state;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "semGetDataServiceState is fail due to RemoteException. " + ex);
            return 0;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semGetDataServiceState is fail due to NullPointerException. " + ex2);
            return 0;
        }
    }

    public int semGetDataServiceState(int subId) {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return 0;
            }
            int state = info.getDataServiceStateUsingSubId(subId);
            return state;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "semGetDataServiceState is fail due to RemoteException. " + ex);
            return 0;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semGetDataServiceState is fail due to NullPointerException. " + ex2);
            return 0;
        }
    }

    public boolean semIsVideoCall() {
        boolean retVal = false;
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                retVal = semTelephony.isVideoCall();
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "semIsVideoCall is fail due to RemoteException. " + ex);
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semIsVideoCall is fail due to NullPointerException. " + ex2);
        }
        com.android.telephony.Rlog.d(TAG, "semIsVideoCall - retVal: " + retVal);
        return retVal;
    }

    public boolean semIsInEmergencyCallbackMode() {
        return TelephonyProperties.in_ecm_mode().orElse(false).booleanValue();
    }

    @Deprecated
    public boolean semIs2gNetworkDisabled() {
        int supportedRat = getSupportedRat(getPhoneId());
        boolean support2g = false;
        if (supportedRat != -1) {
            support2g = (supportedRat & 1) == 1;
        }
        int MODEM_CAPA_RAT_2G = getPhoneId();
        if (!TelephonyFeatures.isMainOperatorSpecific(MODEM_CAPA_RAT_2G, "ATT") || !support2g) {
            throw new UnsupportedOperationException();
        }
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.getDisable2g() > 0;
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "semIs2gNetworkDisabled is fail due to RemoteException. " + ex);
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semIs2gNetworkDisabled is fail due to NullPointerException. " + ex2);
        }
        return false;
    }

    @Deprecated
    public boolean semSet2gNetworkDisabled(boolean disabled) {
        int supportedRat = getSupportedRat(getPhoneId());
        boolean support2g = false;
        int i = 1;
        if (supportedRat != -1) {
            support2g = (supportedRat & 1) == 1;
        }
        int MODEM_CAPA_RAT_2G = getPhoneId();
        if (!TelephonyFeatures.isMainOperatorSpecific(MODEM_CAPA_RAT_2G, "ATT") || !support2g) {
            throw new UnsupportedOperationException();
        }
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                if (!disabled) {
                    i = 0;
                }
                return semTelephony.setDisable2g(i);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "semSet2gNetworkDisabled is fail due to RemoteException. " + ex);
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semSet2gNetworkDisabled is fail due to NullPointerException. " + ex2);
        }
        return false;
    }

    public boolean semIsMmiForSubscriber(int subId, String dialString) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.isMmiForSubscriber(subId, dialString);
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "semIsMmiForSubscriber is fail due to RemoteException. " + ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semIsMmiForSubscriber is fail due to NullPointerException. " + ex2);
            return false;
        }
    }

    public SemNetworkQualityInfo SemGetNetworkQualityInfo() {
        return SemGetNetworkQualityInfo(getPhoneId());
    }

    public SemNetworkQualityInfo SemGetNetworkQualityInfo(int phoneId) {
        String simOp = getSimOperatorNumericForPhone(phoneId);
        SemNetworkQualityInfo networkInfo = new SemNetworkQualityInfo();
        try {
            String mobileInfo =
                    getISemTelephony()
                            .getMobileQualityInformation(
                                    phoneId, this.mContext.getOpPackageName(), getAttributionTag());
            String[] Values = mobileInfo.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
            try {
                String mobileIP = getISemTelephony().getIpAddressFromLinkProp("MOBILE");
                if (!SemTelephonyUtils.SHIP_BUILD) {
                    com.android.telephony.Rlog.d(
                            TAG,
                            "mobileInfo["
                                    + mobileInfo
                                    + "] length("
                                    + Values.length
                                    + NavigationBarInflaterView.KEY_CODE_END);
                    com.android.telephony.Rlog.d(
                            TAG, "mobileIP[" + mobileIP + NavigationBarInflaterView.SIZE_MOD_END);
                }
                if (TelephonyFeatures.isMainOperatorSpecific(phoneId, "SKT")) {
                    networkInfo.put("ip", mobileIP, "2");
                } else if (TelephonyFeatures.isMainOperatorSpecific(phoneId, "KTT")) {
                    networkInfo.put("internet_ip", mobileIP, "2");
                }
                for (String str : Values) {
                    String[] elements = str.split(":");
                    if (!SemTelephonyUtils.SHIP_BUILD) {
                        com.android.telephony.Rlog.d(
                                TAG,
                                "getMobileQualityInfo elements["
                                        + elements[0]
                                        + ":"
                                        + elements[1]
                                        + ":"
                                        + elements[2]
                                        + NavigationBarInflaterView.SIZE_MOD_END);
                    }
                    if (TelephonyFeatures.isMainOperatorSpecific(phoneId, "SKT")
                            || (TelephonyFeatures.isMainOperatorSpecific(phoneId, "KOO")
                                    && simOp.equals("45005"))) {
                        elements[0] = changeNetworkInformationString(elements[0]);
                    } else {
                        elements[2] = "2";
                    }
                    networkInfo.put(elements[0], elements[1], elements[2]);
                }
                return networkInfo;
            } catch (RemoteException ex) {
                com.android.telephony.Rlog.e(
                        TAG, "getIpAddressFromLinkProp() - RemoteException occured : " + ex);
                return null;
            }
        } catch (RemoteException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "getMobileQualityInformation() - RemoteException occured : " + ex2);
            return null;
        }
    }

    private String changeNetworkInformationString(String str) {
        String retStr = str.toLowerCase(Locale.ROOT);
        if (retStr.equals("earfcn")) {
            retStr = "earfcn_downlink";
        } else if (retStr.equals("earfcn_ul")) {
            retStr = "earfcn_uplink";
        } else if (retStr.equals("pusch")) {
            retStr = "tx_power";
        } else if (retStr.equals("rrc_state")) {
            retStr = "rrc";
        } else if (retStr.equals("s1_ca")) {
            retStr = Credentials.CERTIFICATE_USAGE_CA;
        } else if (retStr.startsWith("s1")) {
            retStr = retStr.replace("s1", XmlTags.TAG_SESSION);
        } else if (retStr.startsWith("n1")) {
            retStr = retStr.replace("n1", "neighborset");
        } else if (retStr.startsWith("n2")) {
            retStr = retStr.replace("n2", "neighborset").concat("_2");
        } else if (retStr.startsWith("n3")) {
            retStr = retStr.replace("n3", "neighborset").concat("_3");
        }
        if (retStr.endsWith("_earfcn")) {
            return retStr.replace("earfcn", "freq");
        }
        return retStr;
    }

    public static byte[] semStringToGsm8BitPacked(String s) {
        return GsmAlphabet.stringToGsm8BitPacked(s);
    }

    public static int semCharToGsm(char c, boolean throwException) throws Exception {
        return GsmAlphabet.charToGsm(c, throwException);
    }

    public static int semFindGsmSeptetLimitIndex(
            String s, int start, int limit, int langTable, int langShiftTable) {
        return GsmAlphabet.findGsmSeptetLimitIndex(s, start, limit, langTable, langShiftTable);
    }

    public static char semConvertEachCharacter(char c) {
        return SemGsmAlphabet.convertEachCharacter(c);
    }

    public boolean semGetSdnAvailable() {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.getSdnAvailable();
            }
            return true;
        } catch (RemoteException e) {
            return true;
        } catch (NullPointerException e2) {
            return true;
        }
    }

    public boolean semIsSimFdnEnabled() {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.isSimFDNEnabledForSubscriber(getSubId());
            }
            return false;
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    public void semClearMwiNotificationAndVoicemailCount(int phoneId) {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return;
            }
            info.clearMwiNotificationAndVoicemailCount(phoneId, getOpPackageName());
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    private String getValuefromCSC(String type, String mccmnc, String gid1, int phoneId) {
        Node tempNode;
        String value;
        String value2;
        if (this.mDocument == null) {
            initDocument(phoneId);
        }
        String NetworkName = getNetworkName(mccmnc, gid1);
        com.android.telephony.Rlog.i(
                TAG, "getValuefromCSC : type = " + type + ", NetworkName = " + NetworkName);
        Node node = search("Settings.Messages.MMS");
        if (node == null) {
            com.android.telephony.Rlog.d(
                    TAG, "getValuefromCSC : return null by not found Settings.Messages.MMS");
            return null;
        }
        NodeList nodeList = searchList(node, "MMSView");
        if (nodeList == null) {
            com.android.telephony.Rlog.d(TAG, "getValuefromCSC : return null by not found MMSView");
            return null;
        }
        if (NetworkName != null && !TextUtils.isEmpty(NetworkName)) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (NetworkName.equals(getValue(search(nodeList.item(i), "NetworkName")))
                        && (value2 = getValue(search(nodeList.item(i), type))) != null
                        && !TextUtils.isEmpty(value2)) {
                    com.android.telephony.Rlog.d(
                            TAG, "getValuefromCSC : Found " + type + " = " + value2);
                    return value2;
                }
            }
        }
        for (int i2 = 0; i2 < nodeList.getLength(); i2++) {
            if ("Network Name".equals(getValue(search(nodeList.item(i2), "NetworkName")))
                    && (value = getValue(search(nodeList.item(i2), type))) != null
                    && !TextUtils.isEmpty(value)) {
                com.android.telephony.Rlog.d(
                        TAG, "getValuefromCSC : Default Found " + type + " = " + value);
                return value;
            }
        }
        if (nodeList.getLength() < 1
                || nodeList.item(0) == null
                || (tempNode = search(nodeList.item(0), type)) == null) {
            return null;
        }
        String value3 = getValue(tempNode);
        com.android.telephony.Rlog.d(TAG, "getValuefromCSC: " + type + " = " + value3);
        return value3;
    }

    private String getNetworkName(String mccmnc, String gid1) {
        com.android.telephony.Rlog.d(
                TAG, "getNetworkName: MCCMNC = " + mccmnc + "  gid1 = " + gid1);
        if (TextUtils.isEmpty(mccmnc)) {
            com.android.telephony.Rlog.d(TAG, "getNetworkName: MCCMNC is null");
            return null;
        }
        Node node = search("GeneralInfo");
        if (node == null) {
            com.android.telephony.Rlog.d(TAG, "getNetworkName: No GenralInfo node");
            return null;
        }
        NodeList nodeList = searchList(node, "NetworkInfo");
        if (nodeList == null || nodeList.getLength() == 0) {
            com.android.telephony.Rlog.d(TAG, "getNetworkName: No NetworkInfo node");
            return null;
        }
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node tempNode = search(nodeList.item(i), "MCCMNC");
            if (mccmnc.equals(getValue(tempNode))) {
                Node subsetCodeNode = null;
                boolean getNetworkNameBySubSetCode = false;
                if (!TextUtils.isEmpty(gid1)) {
                    subsetCodeNode = search(nodeList.item(i), "SubsetCode");
                }
                if (subsetCodeNode != null) {
                    String subsetCode = getValue(subsetCodeNode);
                    if (subsetCode != null) {
                        int length = subsetCode.length();
                        if (gid1.length() >= length
                                && gid1.substring(0, length).equalsIgnoreCase(subsetCode)) {
                            getNetworkNameBySubSetCode = true;
                        }
                    } else {
                        continue;
                    }
                }
                if (subsetCodeNode == null || getNetworkNameBySubSetCode) {
                    com.android.telephony.Rlog.d(
                            TAG, "getNetworkName: Found matched network name by " + mccmnc);
                    Node tempNode2 = search(nodeList.item(i), "NetworkName");
                    return getValue(tempNode2);
                }
            }
        }
        return null;
    }

    private Node search(String path) {
        if (path == null || this.mDocument == null) {
            return null;
        }
        Node node = this.mDocument.getDocumentElement();
        StringTokenizer tokenizer = new StringTokenizer(path, MediaMetrics.SEPARATOR);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (node == null) {
                return null;
            }
            node = search(node, token);
        }
        return node;
    }

    private static Node search(Node parent, String name) {
        NodeList children;
        if (parent != null && (children = parent.getChildNodes()) != null) {
            int n = children.getLength();
            for (int i = 0; i < n; i++) {
                Node child = children.item(i);
                if (child.getNodeName().equals(name)) {
                    return child;
                }
            }
        }
        return null;
    }

    private NodeList searchList(Node parent, String name) {
        if (parent == null || this.mDocument == null) {
            return null;
        }
        try {
            Element list = this.mDocument.createElement(parent.getNodeName());
            NodeList children = parent.getChildNodes();
            if (children != null) {
                int n = children.getLength();
                for (int i = 0; i < n; i++) {
                    Node child = children.item(i);
                    if (child.getNodeName().equals(name)) {
                        try {
                            Node cloneNode = child.cloneNode(true);
                            list.appendChild(cloneNode);
                        } catch (Exception e) {
                            com.android.telephony.Rlog.e(TAG, "Exception : " + e.getMessage());
                        }
                    }
                }
            }
            return list.getChildNodes();
        } catch (Exception e2) {
            return null;
        }
    }

    private static String getValue(Node node) {
        if (node == null) {
            return null;
        }
        if (node.getChildNodes().getLength() > 1) {
            StringBuilder stringValue = new StringBuilder();
            int count = node.getChildNodes().getLength();
            for (int idx = 0; idx < count; idx++) {
                stringValue.append(node.getChildNodes().item(idx).getNodeValue());
            }
            return stringValue.toString();
        }
        return node.getFirstChild().getNodeValue();
    }

    private void initDocument(int phoneId) {
        com.android.telephony.Rlog.i(TAG, "initDocument");
        String omcCustomerPath = getCustomerPath(phoneId);
        if (!TextUtils.isEmpty(omcCustomerPath) && new File(omcCustomerPath).exists()) {
            this.mDocument = load(omcCustomerPath);
        } else if (new File("/data/omc/customer.xml").exists()) {
            this.mDocument = load("/data/omc/customer.xml");
        } else {
            this.mDocument = load("/system/csc/customer.xml");
        }
    }

    private Document load(String fileName) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            File file = new File(fileName);
            if (!file.exists()) {
                com.android.telephony.Rlog.e(TAG, "File didn't exist");
                return null;
            }
            try {
                Document doc = builder.parse(file);
                com.android.telephony.Rlog.d(TAG, "load done form : " + fileName);
                return doc;
            } catch (IOException | SAXException e) {
                com.android.telephony.Rlog.e(TAG, "Exception : " + e.getMessage());
                return null;
            }
        } catch (ParserConfigurationException e2) {
            com.android.telephony.Rlog.e(TAG, "ParserConfigurationException is occurred");
            return null;
        }
    }

    private static String getOmcCustomerPath() {
        String omc_path = SemSystemProperties.get("persist.sys.omc_path", "");
        if (omc_path != null) {
            return omc_path + "/customer.xml";
        }
        return null;
    }

    private static String getOmcCustomerPathV2(int phoneId) {
        String mps_nwCode = SemSystemProperties.get("persist.sys.omcnw_path", "");
        String mps_nwCode2 = SemSystemProperties.get("persist.sys.omcnw_path2", "");
        String omcNwPath = phoneId == 1 ? mps_nwCode2 : mps_nwCode;
        if (!TextUtils.isEmpty(omcNwPath)) {
            Log.i("MMS_UA" + phoneId, "omcNwPath =" + omcNwPath);
            return omcNwPath + "/customer.xml";
        }
        return null;
    }

    private static String getCustomerPath(int phoneId) {
        String customer_path = "/system/csc/customer.xml";
        String customer_omcV2path = getOmcCustomerPathV2(phoneId);
        String customer_omcPath = getOmcCustomerPath();
        if (!TextUtils.isEmpty(customer_omcV2path) && new File(customer_omcV2path).exists()) {
            customer_path = customer_omcV2path;
        } else if (!TextUtils.isEmpty(customer_omcPath) && new File(customer_omcPath).exists()) {
            Log.i("MMS_UA" + phoneId, "omc_path =" + customer_omcPath);
            customer_path = customer_omcPath;
        }
        Log.i("MMS_UA" + phoneId, "customer_path =" + customer_path);
        return customer_path;
    }

    public static boolean isSelectTelecomDF() {
        return isSelecttelecomDF;
    }

    public String[] getHomePlmns() {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getHomePlmns(getSubId());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getSubscriberIdForUiccAppType(int UiccAppType) {
        return getSubscriberIdForUiccAppType(getSubId(), UiccAppType);
    }

    public String getSubscriberIdForUiccAppType(int subId, int UiccAppType) {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getSubscriberIdForUiccAppType(
                    subId, UiccAppType, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public byte[] getPsismsc() {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getPsismsc(getOpPackageName());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public byte[] getPsismsc(int phoneId) {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getPsismscWithPhoneId(phoneId, getOpPackageName());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public boolean isGbaSupported() {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return false;
            }
            return info.isGbaSupported();
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    public boolean isGbaSupported(int subid) {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return false;
            }
            return info.isGbaSupportedForSubscriber(subid);
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    public byte[] getRand() {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getRand(getSubId());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getBtid() {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getBtid(getSubId());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public String getKeyLifetime() {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getKeyLifetime(getSubId());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public void setGbaBootstrappingParams(byte[] rand, String btid, String keyLifetime) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                semTelephony.setGbaBootstrappingParams(getSubId(), rand, btid, keyLifetime);
            }
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    public String getGroupIdLevel2(int subId) {
        try {
            ISemPhoneSubInfo info = getSemSubscriberInfoService();
            if (info == null) {
                return null;
            }
            return info.getGroupIdLevel2ForSubscriber(
                    subId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    public void setEPSLOCI(byte[] newEpsloci) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                semTelephony.setEPSLOCI(newEpsloci);
            }
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
    }

    public String checkCallControl(String dialNum) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.checkCallControl(getSubId(), dialNum);
            }
        } catch (RemoteException e) {
        } catch (NullPointerException e2) {
        }
        return dialNum;
    }

    public void setPcoValue(int newPco) {
        int subId = getSubId(SubscriptionManager.getDefaultDataSubscriptionId());
        setPcoValue(subId, newPco);
    }

    public void setPcoValue(int subId, int newPco) {
        try {
            IPhoneSubInfo info = getSubscriberInfoService();
            if (info == null) {
                com.android.telephony.Rlog.e(TAG, "setPcoValue error: Subscriber Info is null");
            } else {
                info.setPcoValue(subId, newPco, this.mContext.getOpPackageName());
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "setPcoValue RemoteException " + ex);
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(TAG, "setPcoValue NullPointerException " + ex2);
        }
    }

    public void semSetAllowDataDuringCall(int enable) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                semTelephony.setAllowDataDuringCall(enable);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "semSetAllowDataDuringCall is fail due to RemoteException. " + ex);
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semSetAllowDataDuringCall is fail due to NullPointerException. " + ex2);
        }
    }

    public boolean semSetVoNRMode(int phoneId, int mode) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.setVoNRMode(phoneId, mode);
            }
            return false;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "semSetVoNRMode is fail due to RemoteException. " + ex);
            return false;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semSetVoNRMode is fail due to NullPointerException. " + ex2);
            return false;
        }
    }

    public int semGetVoNRMode(int phoneId) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.getVoNRMode(phoneId);
            }
            return -1;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(
                    TAG, "semGetVoNRMode is fail due to RemoteException. " + ex);
            return -1;
        } catch (NullPointerException ex2) {
            com.android.telephony.Rlog.e(
                    TAG, "semGetVoNRMode is fail due to NullPointerException. " + ex2);
            return -1;
        }
    }

    public long getNextRetryTime() {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony == null) {
                com.android.telephony.Rlog.e(
                        TAG, "getNextRetryTime(): ISemTelephony instance is NULL");
                throw new IllegalStateException("SemTelephony service not available.");
            }
            long retryTime = semTelephony.getNextRetryTime();
            com.android.telephony.Rlog.e(TAG, "getNextRetryTime(): " + retryTime);
            return retryTime;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getNextRetryTime() RemoteException : " + ex);
            throw ex.rethrowAsRuntimeException();
        }
    }

    public byte[] simCheck(int slotIndex) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "simCheck(): ISemTelephony instance is NULL");
                throw new IllegalStateException("SemTelephony service not available.");
            }
            return semTelephony.simCheck(slotIndex);
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "simCheck() RemoteException : " + ex);
            throw ex.rethrowAsRuntimeException();
        }
    }

    public String getLastNetworkCountryIso() {
        return getLastNetworkCountryIso(getSlotIndex());
    }

    public String getLastNetworkCountryIso(int slotIndex) {
        if (slotIndex != Integer.MAX_VALUE) {
            try {
                if (!SubscriptionManager.isValidSlotIndex(slotIndex)) {
                    throw new IllegalArgumentException("invalid slot index " + slotIndex);
                }
            } catch (RemoteException e) {
                return "";
            }
        }
        ISemTelephony semTelephony = getISemTelephony();
        return semTelephony == null ? "" : semTelephony.getLastNetworkCountryIsoForPhone(slotIndex);
    }

    public void semRequestSatelliteMode(
            int phoneId, boolean enable, Executor executor, final Consumer<Integer> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "semRequestSatelliteMode() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.TelephonyManager$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.TelephonyManager$$ExternalSyntheticLambda8
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                r1.accept(2);
                                            }
                                        });
                            }
                        });
            } else {
                com.android.internal.telephony.IIntegerConsumer errorCallback =
                        new AnonymousClass22(executor, callback);
                semTelephony.semRequestSatelliteMode(phoneId, enable, errorCallback);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "semRequestSatelliteMode is fail. " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.TelephonyManager$$ExternalSyntheticLambda26
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(2);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$22, reason: invalid class name */
    class AnonymousClass22 extends IIntegerConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass22(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.TelephonyManager$22$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.TelephonyManager$22$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public SemSatelliteState semGetSatelliteState(int phoneId) {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.semGetSatelliteState(phoneId);
            }
            return null;
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "semGetSatelliteState is fail. " + ex);
            return null;
        }
    }
}
