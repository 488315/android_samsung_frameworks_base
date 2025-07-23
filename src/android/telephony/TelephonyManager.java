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
import android.telephony.IBootstrapAuthenticationCallback;
import android.telephony.ICellInfoCallback;
import android.telephony.TelephonyManager;
import android.telephony.TelephonyScanManager;
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
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
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
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.util.TelephonyUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.telephony.SemNetworkQualityInfo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Iterator;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class TelephonyManager {

    @SystemApi
    public static final String ACTION_ANOMALY_REPORTED = "android.telephony.action.ANOMALY_REPORTED";
    public static final String ACTION_CALL_DISCONNECT_CAUSE_CHANGED = "android.intent.action.CALL_DISCONNECT_CAUSE";
    public static final String ACTION_CARRIER_MESSAGING_CLIENT_SERVICE = "android.telephony.action.CARRIER_MESSAGING_CLIENT_SERVICE";
    public static final String ACTION_CARRIER_SIGNAL_DEFAULT_NETWORK_AVAILABLE = "android.telephony.action.CARRIER_SIGNAL_DEFAULT_NETWORK_AVAILABLE";
    public static final String ACTION_CARRIER_SIGNAL_PCO_VALUE = "android.telephony.action.CARRIER_SIGNAL_PCO_VALUE";
    public static final String ACTION_CARRIER_SIGNAL_REDIRECTED = "android.telephony.action.CARRIER_SIGNAL_REDIRECTED";
    public static final String ACTION_CARRIER_SIGNAL_REQUEST_NETWORK_FAILED = "android.telephony.action.CARRIER_SIGNAL_REQUEST_NETWORK_FAILED";
    public static final String ACTION_CARRIER_SIGNAL_RESET = "android.telephony.action.CARRIER_SIGNAL_RESET";
    public static final String ACTION_CONFIGURE_VOICEMAIL = "android.telephony.action.CONFIGURE_VOICEMAIL";
    public static final String ACTION_DATA_STALL_DETECTED = "android.intent.action.DATA_STALL_DETECTED";

    @SystemApi
    public static final String ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED = "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED";

    @SystemApi
    public static final String ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED = "android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED";

    @SystemApi
    public static final String ACTION_EMERGENCY_ASSISTANCE = "android.telephony.action.EMERGENCY_ASSISTANCE";

    @SystemApi
    public static final String ACTION_EMERGENCY_CALLBACK_MODE_CHANGED = "android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED";

    @SystemApi
    public static final String ACTION_EMERGENCY_CALL_STATE_CHANGED = "android.intent.action.EMERGENCY_CALL_STATE_CHANGED";
    public static final String ACTION_MULTI_SIM_CONFIG_CHANGED = "android.telephony.action.MULTI_SIM_CONFIG_CHANGED";
    public static final String ACTION_NETWORK_COUNTRY_CHANGED = "android.telephony.action.NETWORK_COUNTRY_CHANGED";
    public static final String ACTION_PHONE_STATE_CHANGED = "android.intent.action.PHONE_STATE";
    public static final String ACTION_PRIMARY_SUBSCRIPTION_LIST_CHANGED = "android.telephony.action.PRIMARY_SUBSCRIPTION_LIST_CHANGED";

    @SystemApi
    public static final String ACTION_REQUEST_OMADM_CONFIGURATION_UPDATE = "com.android.omadm.service.CONFIGURATION_UPDATE";
    public static final String ACTION_RESET_MOBILE_NETWORK_SETTINGS = "android.telephony.action.RESET_MOBILE_NETWORK_SETTINGS";
    public static final String ACTION_RESPOND_VIA_MESSAGE = "android.intent.action.RESPOND_VIA_MESSAGE";
    public static final String ACTION_SECRET_CODE = "android.telephony.action.SECRET_CODE";
    public static final String ACTION_SERVICE_PROVIDERS_UPDATED = "android.telephony.action.SERVICE_PROVIDERS_UPDATED";

    @SystemApi
    public static final String ACTION_SHOW_NOTICE_ECM_BLOCK_OTHERS = "android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS";
    public static final String ACTION_SHOW_VOICEMAIL_NOTIFICATION = "android.telephony.action.SHOW_VOICEMAIL_NOTIFICATION";

    @SystemApi
    public static final String ACTION_SIM_APPLICATION_STATE_CHANGED = "android.telephony.action.SIM_APPLICATION_STATE_CHANGED";

    @SystemApi
    public static final String ACTION_SIM_CARD_STATE_CHANGED = "android.telephony.action.SIM_CARD_STATE_CHANGED";

    @SystemApi
    public static final String ACTION_SIM_SLOT_STATUS_CHANGED = "android.telephony.action.SIM_SLOT_STATUS_CHANGED";
    public static final String ACTION_SUBSCRIPTION_CARRIER_IDENTITY_CHANGED = "android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED";
    public static final String ACTION_SUBSCRIPTION_SPECIFIC_CARRIER_IDENTITY_CHANGED = "android.telephony.action.SUBSCRIPTION_SPECIFIC_CARRIER_IDENTITY_CHANGED";
    public static final int ALLOWED_NETWORK_TYPES_REASON_CARRIER = 2;

    @SystemApi
    public static final int ALLOWED_NETWORK_TYPES_REASON_ENABLE_2G = 3;

    @SystemApi
    public static final int ALLOWED_NETWORK_TYPES_REASON_POWER = 1;
    public static final int ALLOWED_NETWORK_TYPES_REASON_TEST = 4;
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
    public static final String CACHE_KEY_PHONE_ACCOUNT_TO_SUBID = "cache_key.telephony.phone_account_to_subid";
    private static final int CACHE_MAX_SIZE = 4;
    private static final long CALLBACK_ON_MORE_ERROR_CODE_CHANGE = 130595455;
    public static final int CALL_COMPOSER_STATUS_BUSINESS_ONLY = 2;
    public static final int CALL_COMPOSER_STATUS_OFF = 0;
    public static final int CALL_COMPOSER_STATUS_ON = 1;
    public static final int CALL_STATE_IDLE = 0;
    public static final int CALL_STATE_OFFHOOK = 2;
    public static final int CALL_STATE_RINGING = 1;

    @SystemApi
    public static final int CALL_WAITING_STATUS_DISABLED = 2;

    @SystemApi
    public static final int CALL_WAITING_STATUS_ENABLED = 1;

    @SystemApi
    public static final int CALL_WAITING_STATUS_FDN_CHECK_FAILURE = 5;

    @SystemApi
    public static final int CALL_WAITING_STATUS_NOT_SUPPORTED = 4;

    @SystemApi
    public static final int CALL_WAITING_STATUS_UNKNOWN_ERROR = 3;

    @SystemApi
    public static final String CAPABILITY_NR_DUAL_CONNECTIVITY_CONFIGURATION_AVAILABLE = "CAPABILITY_NR_DUAL_CONNECTIVITY_CONFIGURATION_AVAILABLE";
    public static final String CAPABILITY_PHYSICAL_CHANNEL_CONFIG_1_6_SUPPORTED = "CAPABILITY_PHYSICAL_CHANNEL_CONFIG_1_6_SUPPORTED";

    @SystemApi
    public static final String CAPABILITY_SECONDARY_LINK_BANDWIDTH_VISIBLE = "CAPABILITY_SECONDARY_LINK_BANDWIDTH_VISIBLE";
    public static final String CAPABILITY_SIM_PHONEBOOK_IN_MODEM = "CAPABILITY_SIM_PHONEBOOK_IN_MODEM";
    public static final String CAPABILITY_SLICING_CONFIG_SUPPORTED = "CAPABILITY_SLICING_CONFIG_SUPPORTED";

    @SystemApi
    public static final String CAPABILITY_THERMAL_MITIGATION_DATA_THROTTLING = "CAPABILITY_THERMAL_MITIGATION_DATA_THROTTLING";

    @SystemApi
    public static final String CAPABILITY_USES_ALLOWED_NETWORK_TYPES_BITMASK = "CAPABILITY_USES_ALLOWED_NETWORK_TYPES_BITMASK";
    public static final int CARD_POWER_DOWN = 0;
    public static final int CARD_POWER_UP = 1;
    public static final int CARD_POWER_UP_PASS_THROUGH = 2;

    @SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_ERROR_LOADING_RULES = -2;

    @SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_HAS_ACCESS = 1;

    @SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_NO_ACCESS = 0;

    @SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_RULES_NOT_LOADED = -1;
    public static final int CARRIER_RESTRICTION_STATUS_NOT_RESTRICTED = 1;
    public static final int CARRIER_RESTRICTION_STATUS_RESTRICTED = 2;
    public static final int CARRIER_RESTRICTION_STATUS_RESTRICTED_TO_CALLER = 3;
    public static final int CARRIER_RESTRICTION_STATUS_UNKNOWN = 0;

    @Deprecated
    public static final int CDMA_ROAMING_MODE_AFFILIATED = 1;

    @Deprecated
    public static final int CDMA_ROAMING_MODE_ANY = 2;

    @Deprecated
    public static final int CDMA_ROAMING_MODE_HOME = 0;

    @Deprecated
    public static final int CDMA_ROAMING_MODE_RADIO_DEFAULT = -1;

    @SystemApi
    @Deprecated
    public static final int CDMA_SUBSCRIPTION_NV = 1;

    @SystemApi
    @Deprecated
    public static final int CDMA_SUBSCRIPTION_RUIM_SIM = 0;

    @SystemApi
    @Deprecated
    public static final int CDMA_SUBSCRIPTION_UNKNOWN = -1;

    @SystemApi
    public static final int CELL_BROADCAST_RESULT_FAIL_ACTIVATION = 3;

    @SystemApi
    public static final int CELL_BROADCAST_RESULT_FAIL_CONFIG = 2;

    @SystemApi
    public static final int CELL_BROADCAST_RESULT_SUCCESS = 0;

    @SystemApi
    public static final int CELL_BROADCAST_RESULT_UNKNOWN = -1;

    @SystemApi
    public static final int CELL_BROADCAST_RESULT_UNSUPPORTED = 1;
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

    @SystemApi
    public static final int EMERGENCY_CALLBACK_MODE_CALL = 1;

    @SystemApi
    public static final int EMERGENCY_CALLBACK_MODE_SMS = 2;
    public static final long ENABLE_FEATURE_MAPPING = 297989574;

    @SystemApi
    public static final int ENABLE_NR_DUAL_CONNECTIVITY_INVALID_STATE = 4;

    @SystemApi
    public static final int ENABLE_NR_DUAL_CONNECTIVITY_NOT_SUPPORTED = 1;

    @SystemApi
    public static final int ENABLE_NR_DUAL_CONNECTIVITY_RADIO_ERROR = 3;

    @SystemApi
    public static final int ENABLE_NR_DUAL_CONNECTIVITY_RADIO_NOT_AVAILABLE = 2;

    @SystemApi
    public static final int ENABLE_NR_DUAL_CONNECTIVITY_SUCCESS = 0;
    public static final int ENABLE_VONR_RADIO_ERROR = 3;
    public static final int ENABLE_VONR_RADIO_INVALID_STATE = 4;
    public static final int ENABLE_VONR_RADIO_NOT_AVAILABLE = 2;
    public static final int ENABLE_VONR_REQUEST_NOT_SUPPORTED = 5;
    public static final int ENABLE_VONR_SUCCESS = 0;
    public static final int ERI_CUSTOM = 3;

    @Deprecated
    public static final int ERI_FLASH = 2;

    @Deprecated
    public static final int ERI_ICON_MODE_FLASH = 1;

    @Deprecated
    public static final int ERI_ICON_MODE_NORMAL = 0;

    @Deprecated
    public static final int ERI_OFF = 1;

    @Deprecated
    public static final int ERI_ON = 0;
    public static final String EVENT_CALL_FORWARDED = "android.telephony.event.EVENT_CALL_FORWARDED";
    public static final String EVENT_DISPLAY_EMERGENCY_MESSAGE = "android.telephony.event.DISPLAY_EMERGENCY_MESSAGE";
    public static final String EVENT_DOWNGRADE_DATA_DISABLED = "android.telephony.event.EVENT_DOWNGRADE_DATA_DISABLED";
    public static final String EVENT_DOWNGRADE_DATA_LIMIT_REACHED = "android.telephony.event.EVENT_DOWNGRADE_DATA_LIMIT_REACHED";
    public static final String EVENT_HANDOVER_TO_WIFI_FAILED = "android.telephony.event.EVENT_HANDOVER_TO_WIFI_FAILED";
    public static final String EVENT_HANDOVER_VIDEO_FROM_LTE_TO_WIFI = "android.telephony.event.EVENT_HANDOVER_VIDEO_FROM_LTE_TO_WIFI";
    public static final String EVENT_HANDOVER_VIDEO_FROM_WIFI_TO_LTE = "android.telephony.event.EVENT_HANDOVER_VIDEO_FROM_WIFI_TO_LTE";
    public static final String EVENT_NOTIFY_INTERNATIONAL_CALL_ON_WFC = "android.telephony.event.EVENT_NOTIFY_INTERNATIONAL_CALL_ON_WFC";
    public static final String EVENT_SUPPLEMENTARY_SERVICE_NOTIFICATION = "android.telephony.event.EVENT_SUPPLEMENTARY_SERVICE_NOTIFICATION";
    public static final String EXCEPTION_RESULT_KEY = "exception";
    public static final String EXTRA_ACTIVE_SIM_SUPPORTED_COUNT = "android.telephony.extra.ACTIVE_SIM_SUPPORTED_COUNT";

    @SystemApi
    public static final String EXTRA_ANOMALY_DESCRIPTION = "android.telephony.extra.ANOMALY_DESCRIPTION";

    @SystemApi
    public static final String EXTRA_ANOMALY_ID = "android.telephony.extra.ANOMALY_ID";
    public static final String EXTRA_APN_PROTOCOL = "android.telephony.extra.APN_PROTOCOL";
    public static final String EXTRA_APN_TYPE = "android.telephony.extra.APN_TYPE";
    public static final String EXTRA_CALL_VOICEMAIL_INTENT = "android.telephony.extra.CALL_VOICEMAIL_INTENT";
    public static final String EXTRA_CARRIER_ID = "android.telephony.extra.CARRIER_ID";
    public static final String EXTRA_CARRIER_NAME = "android.telephony.extra.CARRIER_NAME";
    public static final String EXTRA_DATA_FAIL_CAUSE = "android.telephony.extra.DATA_FAIL_CAUSE";
    public static final String EXTRA_DATA_SPN = "android.telephony.extra.DATA_SPN";
    public static final String EXTRA_DEFAULT_NETWORK_AVAILABLE = "android.telephony.extra.DEFAULT_NETWORK_AVAILABLE";
    public static final String EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE = "android.telephony.extra.DEFAULT_SUBSCRIPTION_SELECT_TYPE";
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_ALL = 4;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_DATA = 1;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_DISMISS = 5;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_NONE = 0;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_SMS = 3;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_VOICE = 2;

    @Deprecated
    public static final String EXTRA_DISCONNECT_CAUSE = "disconnect_cause";
    public static final String EXTRA_EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE = "android.telephony.extra.EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE";
    public static final String EXTRA_EMERGENCY_CALL_TO_SATELLITE_LAUNCH_INTENT = "android.telephony.extra.EMERGENCY_CALL_TO_SATELLITE_LAUNCH_INTENT";
    public static final String EXTRA_HIDE_PUBLIC_SETTINGS = "android.telephony.extra.HIDE_PUBLIC_SETTINGS";

    @Deprecated
    public static final String EXTRA_INCOMING_NUMBER = "incoming_number";
    public static final String EXTRA_IS_REFRESH = "android.telephony.extra.IS_REFRESH";
    public static final String EXTRA_LAST_KNOWN_NETWORK_COUNTRY = "android.telephony.extra.LAST_KNOWN_NETWORK_COUNTRY";
    public static final String EXTRA_LAUNCH_VOICEMAIL_SETTINGS_INTENT = "android.telephony.extra.LAUNCH_VOICEMAIL_SETTINGS_INTENT";
    public static final String EXTRA_NETWORK_COUNTRY = "android.telephony.extra.NETWORK_COUNTRY";
    public static final String EXTRA_NOTIFICATION_CODE = "android.telephony.extra.NOTIFICATION_CODE";
    public static final String EXTRA_NOTIFICATION_COUNT = "android.telephony.extra.NOTIFICATION_COUNT";
    public static final String EXTRA_NOTIFICATION_MESSAGE = "android.telephony.extra.NOTIFICATION_MESSAGE";
    public static final String EXTRA_NOTIFICATION_TYPE = "android.telephony.extra.NOTIFICATION_TYPE";
    public static final String EXTRA_PCO_ID = "android.telephony.extra.PCO_ID";
    public static final String EXTRA_PCO_VALUE = "android.telephony.extra.PCO_VALUE";
    public static final String EXTRA_PHONE_ACCOUNT_HANDLE = "android.telephony.extra.PHONE_ACCOUNT_HANDLE";

    @SystemApi
    public static final String EXTRA_PHONE_IN_ECM_STATE = "android.telephony.extra.PHONE_IN_ECM_STATE";

    @SystemApi
    public static final String EXTRA_PHONE_IN_EMERGENCY_CALL = "android.telephony.extra.PHONE_IN_EMERGENCY_CALL";
    public static final String EXTRA_PLMN = "android.telephony.extra.PLMN";
    public static final String EXTRA_PRECISE_DISCONNECT_CAUSE = "precise_disconnect_cause";
    public static final String EXTRA_RECOVERY_ACTION = "recoveryAction";
    public static final String EXTRA_REDIRECTION_URL = "android.telephony.extra.REDIRECTION_URL";
    public static final String EXTRA_SHOW_PLMN = "android.telephony.extra.SHOW_PLMN";
    public static final String EXTRA_SHOW_SPN = "android.telephony.extra.SHOW_SPN";
    public static final String EXTRA_SIM_COMBINATION_NAMES = "android.telephony.extra.SIM_COMBINATION_NAMES";

    @Deprecated
    public static final String EXTRA_SIM_COMBINATION_WARNING_TYPE = "android.telephony.extra.SIM_COMBINATION_WARNING_TYPE";

    @Deprecated
    public static final int EXTRA_SIM_COMBINATION_WARNING_TYPE_DUAL_CDMA = 1;

    @Deprecated
    public static final int EXTRA_SIM_COMBINATION_WARNING_TYPE_NONE = 0;

    @SystemApi
    public static final String EXTRA_SIM_STATE = "android.telephony.extra.SIM_STATE";
    public static final String EXTRA_SPECIFIC_CARRIER_ID = "android.telephony.extra.SPECIFIC_CARRIER_ID";
    public static final String EXTRA_SPECIFIC_CARRIER_NAME = "android.telephony.extra.SPECIFIC_CARRIER_NAME";
    public static final String EXTRA_SPN = "android.telephony.extra.SPN";
    public static final String EXTRA_STATE = "state";
    public static final String EXTRA_SUBSCRIPTION_ID = "android.telephony.extra.SUBSCRIPTION_ID";

    @SystemApi
    public static final String EXTRA_VISUAL_VOICEMAIL_ENABLED_BY_USER_BOOL = "android.telephony.extra.VISUAL_VOICEMAIL_ENABLED_BY_USER_BOOL";
    public static final String EXTRA_VOICEMAIL_NUMBER = "android.telephony.extra.VOICEMAIL_NUMBER";

    @SystemApi
    public static final String EXTRA_VOICEMAIL_SCRAMBLED_PIN_STRING = "android.telephony.extra.VOICEMAIL_SCRAMBLED_PIN_STRING";

    @SystemApi
    public static final int GBA_FAILURE_REASON_FEATURE_NOT_READY = 2;

    @SystemApi
    public static final int GBA_FAILURE_REASON_FEATURE_NOT_SUPPORTED = 1;

    @SystemApi
    public static final int GBA_FAILURE_REASON_INCORRECT_NAF_ID = 4;

    @SystemApi
    public static final int GBA_FAILURE_REASON_NETWORK_FAILURE = 3;

    @SystemApi
    public static final int GBA_FAILURE_REASON_SECURITY_PROTOCOL_NOT_SUPPORTED = 5;

    @SystemApi
    public static final int GBA_FAILURE_REASON_UNKNOWN = 0;
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

    @SystemApi
    public static final int INVALID_EMERGENCY_NUMBER_DB_VERSION = -1;
    public static final int INVALID_PORT_INDEX = -1;
    public static final String KEY_CALL_COMPOSER_PICTURE_HANDLE = "call_composer_picture_handle";
    public static final String KEY_SLICING_CONFIG_HANDLE = "slicing_config_handle";

    @SystemApi
    public static final int KEY_TYPE_EPDG = 1;

    @SystemApi
    public static final int KEY_TYPE_WLAN = 2;
    private static final int MAXIMUM_CALL_COMPOSER_PICTURE_SIZE = 80000;
    private static final long MAX_NUMBER_VERIFICATION_TIMEOUT_MILLIS = 60000;
    public static final String METADATA_HIDE_VOICEMAIL_SETTINGS_MENU = "android.telephony.HIDE_VOICEMAIL_SETTINGS_MENU";

    @SystemApi
    public static final int MOBILE_DATA_POLICY_AUTO_DATA_SWITCH = 3;

    @SystemApi
    public static final int MOBILE_DATA_POLICY_DATA_ON_NON_DEFAULT_DURING_VOICE_CALL = 1;

    @SystemApi
    public static final int MOBILE_DATA_POLICY_MMS_ALWAYS_ALLOWED = 2;
    public static final String MODEM_ACTIVITY_RESULT_KEY = "controller_activity";
    public static final int MULTISIM_ALLOWED = 0;
    public static final int MULTISIM_NOT_SUPPORTED_BY_CARRIER = 2;
    public static final int MULTISIM_NOT_SUPPORTED_BY_HARDWARE = 1;
    public static final long NETWORK_CLASS_BITMASK_2G = 32843;
    public static final long NETWORK_CLASS_BITMASK_3G = 93108;
    public static final long NETWORK_CLASS_BITMASK_4G = 397312;
    public static final long NETWORK_CLASS_BITMASK_5G = 524288;

    @Deprecated
    public static final int NETWORK_MODE_CDMA_EVDO = 4;

    @Deprecated
    public static final int NETWORK_MODE_CDMA_NO_EVDO = 5;

    @Deprecated
    public static final int NETWORK_MODE_EVDO_NO_CDMA = 6;
    public static final int NETWORK_MODE_GLOBAL = 7;
    public static final int NETWORK_MODE_GSM_ONLY = 1;
    public static final int NETWORK_MODE_GSM_UMTS = 3;

    @Deprecated
    public static final int NETWORK_MODE_LTE_CDMA_EVDO = 8;

    @Deprecated
    public static final int NETWORK_MODE_LTE_CDMA_EVDO_GSM_WCDMA = 10;
    public static final int NETWORK_MODE_LTE_GSM_WCDMA = 9;
    public static final int NETWORK_MODE_LTE_ONLY = 11;
    public static final int NETWORK_MODE_LTE_TDSCDMA = 15;

    @Deprecated
    public static final int NETWORK_MODE_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 22;
    public static final int NETWORK_MODE_LTE_TDSCDMA_GSM = 17;
    public static final int NETWORK_MODE_LTE_TDSCDMA_GSM_WCDMA = 20;
    public static final int NETWORK_MODE_LTE_TDSCDMA_WCDMA = 19;
    public static final int NETWORK_MODE_LTE_WCDMA = 12;
    public static final int NETWORK_MODE_NR_LTE = 24;

    @Deprecated
    public static final int NETWORK_MODE_NR_LTE_CDMA_EVDO = 25;

    @Deprecated
    public static final int NETWORK_MODE_NR_LTE_CDMA_EVDO_GSM_WCDMA = 27;
    public static final int NETWORK_MODE_NR_LTE_GSM_WCDMA = 26;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA = 29;

    @Deprecated
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 33;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_GSM = 30;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_GSM_WCDMA = 32;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_WCDMA = 31;
    public static final int NETWORK_MODE_NR_LTE_WCDMA = 28;
    public static final int NETWORK_MODE_NR_ONLY = 23;

    @Deprecated
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

    @Deprecated
    public static final long NETWORK_STANDARDS_FAMILY_BITMASK_3GPP2 = 10360;

    @Deprecated
    public static final int NETWORK_TYPE_1xRTT = 7;
    public static final long NETWORK_TYPE_BITMASK_1xRTT = 64;

    @Deprecated
    public static final long NETWORK_TYPE_BITMASK_CDMA = 8;
    public static final long NETWORK_TYPE_BITMASK_EDGE = 2;

    @Deprecated
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

    @Deprecated
    public static final long NETWORK_TYPE_BITMASK_LTE_CA = 262144;
    public static final long NETWORK_TYPE_BITMASK_NR = 524288;
    public static final long NETWORK_TYPE_BITMASK_TD_SCDMA = 65536;
    public static final long NETWORK_TYPE_BITMASK_UMTS = 4;
    public static final long NETWORK_TYPE_BITMASK_UNKNOWN = 0;

    @Deprecated
    public static final int NETWORK_TYPE_CDMA = 4;
    public static final int NETWORK_TYPE_EDGE = 2;

    @Deprecated
    public static final int NETWORK_TYPE_EHRPD = 14;

    @Deprecated
    public static final int NETWORK_TYPE_EVDO_0 = 5;

    @Deprecated
    public static final int NETWORK_TYPE_EVDO_A = 6;

    @Deprecated
    public static final int NETWORK_TYPE_EVDO_B = 12;
    public static final int NETWORK_TYPE_GPRS = 1;
    public static final int NETWORK_TYPE_GSM = 16;
    public static final int NETWORK_TYPE_HSDPA = 8;
    public static final int NETWORK_TYPE_HSPA = 10;
    public static final int NETWORK_TYPE_HSPAP = 15;
    public static final int NETWORK_TYPE_HSUPA = 9;

    @Deprecated
    public static final int NETWORK_TYPE_IDEN = 11;
    public static final int NETWORK_TYPE_IWLAN = 18;
    public static final int NETWORK_TYPE_LTE = 13;
    public static final int NETWORK_TYPE_LTE_CA = 19;
    public static final int NETWORK_TYPE_NR = 20;
    public static final int NETWORK_TYPE_TD_SCDMA = 17;
    public static final int NETWORK_TYPE_UMTS = 3;
    public static final int NETWORK_TYPE_UNKNOWN = 0;

    @SystemApi
    public static final int NR_DUAL_CONNECTIVITY_DISABLE = 2;

    @SystemApi
    public static final int NR_DUAL_CONNECTIVITY_DISABLE_IMMEDIATE = 3;

    @SystemApi
    public static final int NR_DUAL_CONNECTIVITY_ENABLE = 1;
    private static final long NULL_TELEPHONY_THROW_NO_CB = 182185642;
    public static final int OTASP_NEEDED = 2;
    public static final int OTASP_NOT_NEEDED = 3;
    public static final int OTASP_SIM_UNPROVISIONED = 5;
    public static final int OTASP_UNINITIALIZED = 0;
    public static final int OTASP_UNKNOWN = 1;
    public static final String PHONE_PROCESS_NAME = "com.android.phone";

    @Deprecated
    public static final int PHONE_TYPE_CDMA = 2;
    public static final int PHONE_TYPE_GSM = 1;
    public static final int PHONE_TYPE_IMS = 5;
    public static final int PHONE_TYPE_NONE = 0;
    public static final int PHONE_TYPE_SIP = 3;
    public static final int PHONE_TYPE_THIRD_PARTY = 4;
    public static final int PREMIUM_CAPABILITY_PRIORITIZE_LATENCY = 34;

    @SystemApi
    public static final int PREPARE_UNATTENDED_REBOOT_ERROR = 2;

    @SystemApi
    public static final int PREPARE_UNATTENDED_REBOOT_PIN_REQUIRED = 1;

    @SystemApi
    public static final int PREPARE_UNATTENDED_REBOOT_SUCCESS = 0;
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

    @SystemApi
    public static final int RADIO_POWER_OFF = 0;

    @SystemApi
    public static final int RADIO_POWER_ON = 1;

    @SystemApi
    public static final int RADIO_POWER_REASON_CARRIER = 2;

    @SystemApi
    public static final int RADIO_POWER_REASON_NEARBY_DEVICE = 3;

    @SystemApi
    public static final int RADIO_POWER_REASON_THERMAL = 1;

    @SystemApi
    public static final int RADIO_POWER_REASON_USER = 0;

    @SystemApi
    public static final int RADIO_POWER_UNAVAILABLE = 2;
    public static final String SEM_CALL_EXTRA_CALL_FORWARDING_NUMBER = "com.samsung.telephony.extra.CALL_FORWARDING_REDIRECT_NUMBER";
    public static final String SEM_CALL_EXTRA_CALL_FORWARDING_NUMBER_PRESENTATION = "com.samsung.telephony.extra.CALL_FORWARDING_PRESENTATION";
    public static final String SEM_CALL_EXTRA_CMC_CALL_STATE = "com.samsung.telephony.extra.CMC_CALL_STATE";
    public static final String SEM_CALL_EXTRA_CMC_EXTERNAL_CALL = "com.samsung.telephony.extra.CMC_EXTERNAL_CALL";
    public static final String SEM_CALL_EXTRA_CMC_PRIMARY_DEVICE_CALL_CONNECT_TIME = "com.samsung.telephony.extra.CMC_PD_CALL_CONNECT_TIME";
    public static final String SEM_CALL_EXTRA_CMC_PRIMARY_DEVICE_CALL_TYPE = "com.samsung.telephony.extra.CMC_PD_CALL_TYPE";
    public static final String SEM_CALL_EXTRA_CMC_PULLABLE = "com.samsung.telephony.extra.CMC_PULLABLE";
    public static final String SEM_CALL_EXTRA_DUAL_NUMBER = "com.samsung.telephony.extra.DUAL_NUMBER";
    public static final String SEM_CALL_EXTRA_INCOMING_CONFERENCE_CALL = "com.samsung.telephony.extra.MT_CONFERENCE";
    public static final String SEM_CALL_EXTRA_IS_TWO_PHONE_MODE = "com.samsung.telephony.extra.IS_TWO_PHONE_MODE";
    public static final String SEM_CALL_EXTRA_PHOTO_RING_SERVICE_PARAMETER = "com.samsung.telephony.extra.PHOTO_RING_AVAILABLE";
    public static final String SEM_CALL_EXTRA_SATELLITE_CALL = "com.samsung.telephony.extra.SATELLITE_CALL";
    public static final String SEM_CALL_EXTRA_SEM_CMC_TYPE = "com.samsung.telephony.extra.CMC_TYPE";
    public static final String SEM_CALL_EXTRA_SHOW_ME_SERVICE_PARAMETER = "com.samsung.telephony.extra.ALERT_INFO";
    public static final String SEM_CALL_EXTRA_START_CALL_WITH_PREFERRED_DOMAIN = "com.samsung.telephony.extra.START_CALL_WITH_DOMAIN";
    public static final String SEM_CALL_EXTRA_TELECOM_CALL_ID = "com.samsung.telephony.extra.ims.IMSDC_TELECOM_CALL_ID";
    public static final String SEM_CALL_EXTRA_VALIDATION_STATE = "com.samsung.telephony.extra.ims.VERSTAT";
    public static final String SEM_CALL_EXTRA_VCS_ACTION = "com.samsung.telephony.extra.ims.VCS_ACTION";
    public static final String SEM_CALL_EXTRA_VCS_DURATION = "com.samsung.telephony.extra.ims.VCS_DURATION";
    public static final String SEM_CALL_EXTRA_VCS_SCREEN_SHARING_ONOFF = "com.samsung.telephony.extra.ims.VCS_SCREENSHARING_ONOFF";
    public static final String SEM_CALL_EXTRA_VCS_SLIDING_STAGE = "com.samsung.telephony.extra.ims.VCS_SLIDING_STAGE";
    public static final String SEM_CALL_EXTRA_VCS_TIMESTAMP = "com.samsung.telephony.extra.ims.VCS_TIMESTAMP";
    public static final String SEM_CALL_EXTRA_VCS_X_POS = "com.samsung.telephony.extra.ims.VCS_X_POS";
    public static final String SEM_CALL_EXTRA_VCS_Y_POS = "com.samsung.telephony.extra.ims.VCS_Y_POS";
    public static final String SEM_CALL_EXTRA_VIDEO_CALL_RECORDING_STATE = "com.samsung.telephony.extra.VT_RECORDING_STATE";
    public static final String SEM_CALL_EXTRA_WAITING_TONE_PLAY_TYPE = "com.samsung.telephony.extra.CALL_WAITING_TONE_SIGNAL";
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
    public static final String SEM_EVENT_CALL_CMC_PRIMARY_DEVICE_CONNECTED_TIME = "com.samsung.telephony.event.EVENT_CALL_CMC_PRIMARY_DEVICE_CONNECTED_TIME";
    public static final String SEM_EVENT_CALL_CMC_SECONDARY_DEVICE_PULL_COMPLETED = "com.samsung.telephony.event.EVENT_CALL_CMC_SECONDARY_DEVICE_PULL_COMPLETED";
    public static final String SEM_EVENT_CALL_CMC_SECONDARY_DEVICE_REQUEST_TYPE = "com.samsung.telephony.event.EVENT_CALL_CMC_SECONDARY_DEVICE_REQUEST_TYPE";
    public static final String SEM_EVENT_IMSDC_UPDATE_TELECOM_CALLID = "com.samsung.telephony.event.IMSDC_UPDATETELECOMCALLID";
    public static final String SEM_EVENT_VCS_SCREEN_SHARING = "com.samsung.telephony.event.VCS_SCREENSHARING";
    public static final String SEM_EVENT_VCS_TOUCH_SCREEN = "com.samsung.telephony.event.VCS_TOUCHSCREEN";
    public static final String SEM_EXTRA_ASSISTED_DIAL_FROM = "com.samsung.telephony.extra.EXTRA_ASSISTED_DIAL_FROM";
    public static final String SEM_EXTRA_CAN_TRANSFER_CALL = "com.samsung.telephony.extra.CAN_TRANSFER_CALL";
    public static final String SEM_EXTRA_CMC_BOUND_SESSION_ID = "com.samsung.telephony.extra.CMC_BOUND_SESSION_ID";
    public static final String SEM_EXTRA_CMC_CALL_SD_REQUEST_TYPE = "com.samsung.telephony.extra.CMC_CALL_REQUEST_TYPE";
    public static final String SEM_EXTRA_CMC_CALL_TYPE = "com.samsung.telephony.extra.CMC_CALL_TYPE";
    public static final String SEM_EXTRA_CMC_DEVICE_ID = "com.samsung.telephony.extra.CMC_DEVICE_ID";
    public static final String SEM_EXTRA_CMC_DIAL_FROM = "com.samsung.telephony.extra.CMC_DIAL_FROM";
    public static final String SEM_EXTRA_CMC_DIAL_TO = "com.samsung.telephony.extra.CMC_DIAL_TO";
    public static final String SEM_EXTRA_CMC_PHONE_ID = "com.samsung.telephony.extra.CMC_PHONE_ID";
    public static final String SEM_EXTRA_CMC_REPLACE_CALL_ID = "com.samsung.telephony.extra.CMC_REPLACE_CALL_ID";
    public static final String SEM_EXTRA_CMC_SD_CALL_MANAGE = "com.samsung.telephony.extra.CMC_CALL_MANAGE";
    public static final String SEM_EXTRA_CMC_SD_DTMF_KEY = "com.samsung.telephony.extra.CMC_CS_DTMF_KEY";
    public static final String SEM_EXTRA_CMC_SERVICE_TYPE = "com.samsung.telephony.extra.CMC_SERVICE_TYPE";
    public static final String SEM_EXTRA_CMC_SESSION_ID = "com.samsung.telephony.extra.CMC_SESSION_ID";
    public static final String SEM_EXTRA_DIAL_CONFERENCE_CALL = "com.samsung.telephony.extra.DIAL_CONFERENCE_CALL";
    public static final String SEM_EXTRA_FORWARDED_CALL = "com.samsung.telephony.extra.SEM_EXTRA_FORWARDED_CALL";
    public static final String SEM_EXTRA_SKT_CONFERENCE_CALL_SUPPORT = "com.samsung.telephony.extra.SKT_CONFERENCE_CALL_SUPPORT";
    public static final String SEM_EXTRA_START_CALL_WITH_EMERGENCY_SERVICE_CATEGORY = "com.samsung.telephony.extra.START_CALL_WITH_EMERGENCY_SERVICE_CATEGORY";
    public static final String SEM_EXTRA_VCRBT_CAUSE = "com.samsung.telephony.extra.VCRBT_CAUSE";
    public static final String SEM_EXTRA_VCRBT_REASON_PROTOCOL = "com.samsung.telephony.extra.VCRBT_REASON_PROTOCOL";
    public static final String SEM_EXTRA_VCRBT_TEXT_DESCRIPTION = "com.samsung.telephony.extra.TEXT_DESCRIPTION";
    public static final String SEM_EXTRA_VIDEO_CRBT = "com.samsung.telephony.extra.VIDEO_CRBT";
    public static final String SEM_EXTRA_VIDEO_CRT_IS_ALERTING = "com.samsung.telephony.extra.VIDEO_CRT_IS_ALERTING";
    public static final String SEM_EXTRA_VIDEO_CRT_MT = "com.samsung.telephony.extra.VIDEO_CRT_MT";
    public static final int SEM_NR_MODE_NSA = 1;
    public static final int SEM_NR_MODE_SA = 2;
    public static final int SEM_NR_MODE_SA_NSA = 0;
    public static final String SEM_PREFERRED_DOMAIN_CS = "CS";
    public static final String SEM_PREFERRED_DOMAIN_PS = "PS";

    @Deprecated(forRemoval = true, since = "17.0")
    public static final int SEM_SET_SATELLITE_RESULT_INVALID_STATE = 2;

    @Deprecated(forRemoval = true, since = "17.0")
    public static final int SEM_SET_SATELLITE_RESULT_MODEM_ERROR = 1;

    @Deprecated(forRemoval = true, since = "17.0")
    public static final int SEM_SET_SATELLITE_RESULT_RADIOS_OFF_ERROR = 3;

    @Deprecated(forRemoval = true, since = "17.0")
    public static final int SEM_SET_SATELLITE_RESULT_SUCCESS = 0;
    public static final String SEM_VALIDATION_STATE_FAILED = "TN-Validation-Failed";
    public static final String SEM_VALIDATION_STATE_NONE = "No-TN-Validation";
    public static final String SEM_VALIDATION_STATE_PASSED = "TN-Validation-Passed";
    public static final int SEM_VIDEO_CALL_RECORDING_STATE_IN_PROGRESS = 1;
    public static final int SEM_VIDEO_CALL_RECORDING_STATE_NOT_IN_PROGRESS = 0;
    public static final int SEM_WAITING_TONE_PLAY_TYPE_APP = 7;
    public static final int SEM_WAITING_TONE_PLAY_TYPE_NETWORK = 0;

    @SystemApi
    public static final int SET_CARRIER_RESTRICTION_ERROR = 2;

    @SystemApi
    public static final int SET_CARRIER_RESTRICTION_NOT_SUPPORTED = 1;

    @SystemApi
    public static final int SET_CARRIER_RESTRICTION_SUCCESS = 0;
    public static final int SET_OPPORTUNISTIC_SUB_INACTIVE_SUBSCRIPTION = 2;
    public static final int SET_OPPORTUNISTIC_SUB_NO_OPPORTUNISTIC_SUB_AVAILABLE = 3;
    public static final int SET_OPPORTUNISTIC_SUB_REMOTE_SERVICE_EXCEPTION = 4;
    public static final int SET_OPPORTUNISTIC_SUB_SUCCESS = 0;
    public static final int SET_OPPORTUNISTIC_SUB_VALIDATION_FAILED = 1;

    @SystemApi
    public static final int SET_SIM_POWER_STATE_ALREADY_IN_STATE = 1;

    @SystemApi
    public static final int SET_SIM_POWER_STATE_MODEM_ERROR = 2;

    @SystemApi
    public static final int SET_SIM_POWER_STATE_NOT_SUPPORTED = 4;

    @SystemApi
    public static final int SET_SIM_POWER_STATE_SIM_ERROR = 3;

    @SystemApi
    public static final int SET_SIM_POWER_STATE_SUCCESS = 0;
    private static final int SIMSLOT1 = 0;
    private static final int SIMSLOT2 = 1;

    @SystemApi
    public static final int SIM_ACTIVATION_STATE_ACTIVATED = 2;

    @SystemApi
    public static final int SIM_ACTIVATION_STATE_ACTIVATING = 1;

    @SystemApi
    public static final int SIM_ACTIVATION_STATE_DEACTIVATED = 3;

    @SystemApi
    public static final int SIM_ACTIVATION_STATE_RESTRICTED = 4;

    @SystemApi
    public static final int SIM_ACTIVATION_STATE_UNKNOWN = 0;
    public static final int SIM_STATE_ABSENT = 1;
    public static final int SIM_STATE_CARD_IO_ERROR = 8;
    public static final int SIM_STATE_CARD_RESTRICTED = 9;
    public static final int SIM_STATE_DETECTED = 16;

    @SystemApi
    public static final int SIM_STATE_LOADED = 10;
    public static final int SIM_STATE_NETWORK_LOCKED = 4;
    public static final int SIM_STATE_NETWORK_SUBSET_LOCKED = 13;
    public static final int SIM_STATE_NOT_READY = 6;
    public static final int SIM_STATE_PERM_DISABLED = 7;
    public static final int SIM_STATE_PERSO_LOCKED = 12;
    public static final int SIM_STATE_PIN_REQUIRED = 2;

    @SystemApi
    public static final int SIM_STATE_PRESENT = 11;
    public static final int SIM_STATE_PUK_REQUIRED = 3;
    public static final int SIM_STATE_READY = 5;
    public static final int SIM_STATE_REGIONAL_LOCKED = 15;
    public static final int SIM_STATE_SIM_SERVICE_PROVIDER_LOCKED = 14;
    public static final int SIM_STATE_UNKNOWN = 0;

    @SystemApi
    public static final int SRVCC_STATE_HANDOVER_CANCELED = 3;

    @SystemApi
    public static final int SRVCC_STATE_HANDOVER_COMPLETED = 1;

    @SystemApi
    public static final int SRVCC_STATE_HANDOVER_FAILED = 2;

    @SystemApi
    public static final int SRVCC_STATE_HANDOVER_NONE = -1;

    @SystemApi
    public static final int SRVCC_STATE_HANDOVER_STARTED = 0;

    @SystemApi
    public static final int STOP_REASON_EMERGENCY_SMS_SENT = 4;

    @SystemApi
    public static final int STOP_REASON_NORMAL_SMS_SENT = 2;

    @SystemApi
    public static final int STOP_REASON_OUTGOING_EMERGENCY_CALL_INITIATED = 3;

    @SystemApi
    public static final int STOP_REASON_OUTGOING_NORMAL_CALL_INITIATED = 1;

    @SystemApi
    public static final int STOP_REASON_TIMER_EXPIRED = 5;

    @SystemApi
    public static final int STOP_REASON_UNKNOWN = 0;

    @SystemApi
    public static final int STOP_REASON_USER_ACTION = 6;
    private static final String TAG = "TelephonyManager";

    @SystemApi
    public static final int THERMAL_MITIGATION_RESULT_INVALID_STATE = 3;

    @SystemApi
    public static final int THERMAL_MITIGATION_RESULT_MODEM_ERROR = 1;

    @SystemApi
    public static final int THERMAL_MITIGATION_RESULT_MODEM_NOT_AVAILABLE = 2;

    @SystemApi
    public static final int THERMAL_MITIGATION_RESULT_SUCCESS = 0;

    @SystemApi
    public static final int THERMAL_MITIGATION_RESULT_UNKNOWN_ERROR = 4;
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
    public static boolean isSelecttelecomDF = false;
    private static final int mOpportunisticActiveSubsCount = 2;
    private static IPhoneSubInfo sIPhoneSubInfo = null;
    private static ISemPhoneSubInfo sISemPhoneSubInfo = null;
    private static ISemTelephony sISemTelephony = null;
    private static ISms sISms = null;
    private static ISub sISub = null;
    private static ITelephony sITelephony = null;
    private static boolean sServiceHandleCacheEnabled = true;
    private final Context mContext;
    private Document mDocument;
    private PropertyInvalidatedCache<PhoneAccountHandle, Integer> mPhoneAccountHandleToSubIdCache;
    private final int mSubId;
    private SubscriptionManager mSubscriptionManager;
    private TelephonyRegistryManager mTelephonyRegistryMgr;
    private TelephonyScanManager mTelephonyScanManager;
    private static final Object sCacheLock = new Object();
    private static final DeathRecipient sServiceDeath = new DeathRecipient();
    private static TelephonyManager sInstance = new TelephonyManager();
    private static boolean mIsCheckedMdec = false;
    private static boolean mIsInstalledMdec = false;
    public static final String EXTRA_STATE_IDLE = PhoneConstants.State.IDLE.toString();
    public static final String EXTRA_STATE_RINGING = PhoneConstants.State.RINGING.toString();
    public static final String EXTRA_STATE_OFFHOOK = PhoneConstants.State.OFFHOOK.toString();
    private static final int[] NETWORK_TYPES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    public static final int DEFAULT_PREFERRED_NETWORK_MODE = RILConstants.PREFERRED_NETWORK_MODE;
    public static final Pair HAL_VERSION_UNKNOWN = new Pair(-1, -1);
    public static final Pair HAL_VERSION_UNSUPPORTED = new Pair(-2, -2);

    @Retention(RetentionPolicy.SOURCE)
    public @interface AllowedNetworkTypesReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthenticationFailureReason {
    }

    @SystemApi
    public static class BootstrapAuthenticationCallback {
        public void onAuthenticationFailure(int i) {
        }

        public void onKeysAvailable(byte[] bArr, String str) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallComposerStatus {
    }

    @SystemApi
    public interface CallForwardingInfoCallback {
        public static final int RESULT_ERROR_FDN_CHECK_FAILURE = 2;
        public static final int RESULT_ERROR_NOT_SUPPORTED = 3;
        public static final int RESULT_ERROR_UNKNOWN = 1;
        public static final int RESULT_SUCCESS = 0;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CallForwardingError {
        }

        void onCallForwardingInfoAvailable(CallForwardingInfo callForwardingInfo);

        void onError(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallWaitingStatus {
    }

    @SystemApi
    public interface CarrierPrivilegesCallback {
        void onCarrierPrivilegesChanged(Set<String> set, Set<Integer> set2);

        default void onCarrierServiceChanged(String str, int i) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CarrierRestrictionStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface CdmaRoamingMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface CdmaSubscription {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CellBroadcastResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataEnabledChangedReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataEnabledReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DefaultSubscriptionSelectType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EmergencyCallbackModeStopReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EmergencyCallbackModeType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnableNrDualConnectivityResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnableVoNrResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface EriIconIndex {
    }

    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface EriIconMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HalService {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IncludeLocationData {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IsMultiSimSupportedResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MobileDataPolicy {
    }

    public enum MultiSimVariants {
        DSDS,
        DSDA,
        TSTS,
        UNKNOWN
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkSelectionMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkTypeBitMask {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NrDualConnectivityState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrefNetworkMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PremiumCapability {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrepareUnattendedRebootResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PurchasePremiumCapabilityResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RadioInterfaceCapability {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RadioPowerReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemEnableVoNrResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetCarrierRestrictionResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetOpportunisticSubscriptionResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetSatelliteModeResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetSimPowerStateResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface SimCombinationWarningType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SimPowerState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SimState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UpdateAvailableNetworksResult {
    }

    public static abstract class UssdResponseCallback {
        public void onReceiveUssdResponse(TelephonyManager telephonyManager, String str, CharSequence charSequence) {
        }

        public void onReceiveUssdResponseFailed(TelephonyManager telephonyManager, String str, int i) {
        }
    }

    public interface WifiCallingChoices {
        public static final int ALWAYS_USE = 0;
        public static final int ASK_EVERY_TIME = 1;
        public static final int NEVER_USE = 2;
    }

    private long checkNetworkTypeBitmask(long j) {
        return (j & 262144) != 0 ? (j ^ 262144) | 4096 : j;
    }

    public static long getAllNetworkTypesBitmask() {
        return 916479L;
    }

    public static long getBitMaskForNetworkType(int i) {
        switch (i) {
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

    @SystemApi
    public static long getMaxNumberVerificationTimeoutMillis() {
        return 60000L;
    }

    public static long getMaximumCallComposerPictureSize() {
        return 80000L;
    }

    private int getSimApplicationStateFromSimState(int i) {
        if (i == 0 || i == 1) {
            return 0;
        }
        if (i == 5) {
            return 6;
        }
        if (i == 8 || i == 9) {
            return 0;
        }
        return i;
    }

    private int getSimCardStateFromSimState(int i) {
        if (i == 0 || i == 1 || i == 8 || i == 9) {
            return i;
        }
        return 11;
    }

    private static boolean isKeyEnabled(int i, int i2) {
        return ((i >> (i2 - 1)) & 1) == 1;
    }

    public static boolean isNetworkTypeValid(int i) {
        return i >= 0 && i <= 20;
    }

    @SystemApi
    @Deprecated
    public void answerRingingCall() {
    }

    @SystemApi
    @Deprecated
    public boolean endCall() {
        return false;
    }

    @SystemApi
    @Deprecated
    public boolean isVisualVoicemailEnabled(PhoneAccountHandle phoneAccountHandle) {
        return false;
    }

    @SystemApi
    @Deprecated
    public void setVisualVoicemailEnabled(PhoneAccountHandle phoneAccountHandle, boolean z) {
    }

    @SystemApi
    @Deprecated
    public void silenceRinger() {
    }

    public static String srvccStateToString(int i) {
        if (i == -1) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 0) {
            return "STARTED";
        }
        if (i == 1) {
            return "COMPLETED";
        }
        if (i == 2) {
            return "FAILED";
        }
        if (i == 3) {
            return "CANCELED";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public TelephonyManager(Context context) {
        this(context, Integer.MAX_VALUE);
    }

    public TelephonyManager(Context context, int i) {
        this.mDocument = null;
        this.mPhoneAccountHandleToSubIdCache = new PropertyInvalidatedCache<PhoneAccountHandle, Integer>(4, CACHE_KEY_PHONE_ACCOUNT_TO_SUBID) { // from class: android.telephony.TelephonyManager.1
            @Override // android.app.PropertyInvalidatedCache
            public Integer recompute(PhoneAccountHandle phoneAccountHandle) {
                try {
                    ITelephony iTelephony = TelephonyManager.getITelephony();
                    if (iTelephony != null) {
                        return Integer.valueOf(iTelephony.getSubIdForPhoneAccountHandle(phoneAccountHandle, TelephonyManager.this.mContext.getOpPackageName(), TelephonyManager.this.mContext.getAttributionTag()));
                    }
                    return -1;
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            }
        };
        this.mSubId = i;
        Context mergeAttributionAndRenouncedPermissions = mergeAttributionAndRenouncedPermissions(context.getApplicationContext(), context);
        this.mContext = mergeAttributionAndRenouncedPermissions;
        this.mSubscriptionManager = SubscriptionManager.from(mergeAttributionAndRenouncedPermissions);
    }

    private TelephonyManager() {
        this.mDocument = null;
        this.mPhoneAccountHandleToSubIdCache = new PropertyInvalidatedCache<PhoneAccountHandle, Integer>(4, CACHE_KEY_PHONE_ACCOUNT_TO_SUBID) { // from class: android.telephony.TelephonyManager.1
            @Override // android.app.PropertyInvalidatedCache
            public Integer recompute(PhoneAccountHandle phoneAccountHandle) {
                try {
                    ITelephony iTelephony = TelephonyManager.getITelephony();
                    if (iTelephony != null) {
                        return Integer.valueOf(iTelephony.getSubIdForPhoneAccountHandle(phoneAccountHandle, TelephonyManager.this.mContext.getOpPackageName(), TelephonyManager.this.mContext.getAttributionTag()));
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

    private Context mergeAttributionAndRenouncedPermissions(Context context, Context context2) {
        if (context == null) {
            return context2;
        }
        Context createAttributionContext = !Objects.equals(context2.getAttributionTag(), context.getAttributionTag()) ? context.createAttributionContext(context2.getAttributionTag()) : context;
        Set<String> renouncedPermissions = context2.getAttributionSource().getRenouncedPermissions();
        if (renouncedPermissions.isEmpty()) {
            return createAttributionContext;
        }
        if (context.getParams() != null) {
            return createAttributionContext.createContext(new ContextParams.Builder(context.getParams()).setRenouncedPermissions(renouncedPermissions).build());
        }
        return createAttributionContext.createContext(new ContextParams.Builder().setRenouncedPermissions(renouncedPermissions).build());
    }

    private String getOpPackageName() {
        Context context = this.mContext;
        if (context != null) {
            return context.getOpPackageName();
        }
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return null;
        }
        try {
            return iTelephony.getCurrentPackageName();
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    private String getAttributionTag() {
        Context context = this.mContext;
        if (context != null) {
            return context.getAttributionTag();
        }
        return null;
    }

    private Set<String> getRenouncedPermissions() {
        Context context = this.mContext;
        if (context != null) {
            return context.getAttributionSource().getRenouncedPermissions();
        }
        return Collections.EMPTY_SET;
    }

    private static void runOnBackgroundThread(Runnable runnable) {
        try {
            BackgroundThread.getExecutor().execute(runnable);
        } catch (RejectedExecutionException e) {
            throw new IllegalStateException("Failed to post a callback from the caller's thread context.", e);
        }
    }

    public MultiSimVariants getMultiSimConfiguration() {
        String orElse = TelephonyProperties.multi_sim_config().orElse("");
        if (orElse.equals("dsds")) {
            return MultiSimVariants.DSDS;
        }
        if (orElse.equals("dsda")) {
            return MultiSimVariants.DSDA;
        }
        if (orElse.equals("tsts")) {
            return MultiSimVariants.TSTS;
        }
        return MultiSimVariants.UNKNOWN;
    }

    @Deprecated
    public int getPhoneCount() {
        return getActiveModemCount();
    }

    public int getActiveModemCount() {
        int ordinal = getMultiSimConfiguration().ordinal();
        if (ordinal == 0 || ordinal == 1) {
            return 2;
        }
        if (ordinal == 2) {
            return 3;
        }
        if (ordinal != 3) {
            return 1;
        }
        int i = (isDeviceVoiceCapable() || isSmsCapable() || isDataCapable()) ? 1 : 0;
        if (i == 0 && checkCmcInstalled(this.mContext)) {
            return 1;
        }
        return i;
    }

    private static boolean checkCmcInstalled(Context context) {
        if (context != null && !mIsCheckedMdec) {
            mIsCheckedMdec = true;
            try {
                context.getPackageManager().getPackageInfo(AsPackageName.CMC, 0);
                mIsInstalledMdec = true;
            } catch (PackageManager.NameNotFoundException unused) {
                com.android.telephony.Rlog.d(TAG, "mdecservice package NameNotFoundException");
            }
        }
        return mIsInstalledMdec;
    }

    public int getSupportedModemCount() {
        return TelephonyProperties.max_active_modems().orElse(Integer.valueOf(getActiveModemCount())).intValue();
    }

    @SystemApi
    public int getMaxNumberOfSimultaneouslyActiveSims() {
        return getMultiSimConfiguration().ordinal() != 1 ? 1 : 2;
    }

    public static TelephonyManager from(Context context) {
        return (TelephonyManager) context.getSystemService("phone");
    }

    public TelephonyManager createForSubscriptionId(int i) {
        return new TelephonyManager(this.mContext, i);
    }

    public TelephonyManager createForPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle) {
        int subscriptionId = getSubscriptionId(phoneAccountHandle);
        if (SubscriptionManager.isValidSubscriptionId(subscriptionId)) {
            return new TelephonyManager(this.mContext, subscriptionId);
        }
        return null;
    }

    public boolean isMultiSimEnabled() {
        return getPhoneCount() > 1;
    }

    public String getDeviceSoftwareVersion() {
        return getDeviceSoftwareVersion(getSlotIndex());
    }

    @SystemApi
    public String getDeviceSoftwareVersion(int i) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return null;
        }
        try {
            return iTelephony.getDeviceSoftwareVersionForSlot(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @Deprecated
    public String getDeviceId() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getDeviceIdWithFeature(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @Deprecated
    public String getDeviceId(int i) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getDeviceIdForPhone(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public String getImei() {
        return getImei(getSlotIndex());
    }

    public String getImei(int i) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return null;
        }
        try {
            return iTelephony.getImeiForSlot(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public String getTypeAllocationCode() {
        return getTypeAllocationCode(getSlotIndex());
    }

    public String getTypeAllocationCode(int i) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return null;
        }
        try {
            return iTelephony.getTypeAllocationCodeForSlot(i);
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @Deprecated
    public String getMeid() {
        if (Flags.cleanupCdma()) {
            return null;
        }
        return getMeid(getSlotIndex());
    }

    @Deprecated
    public String getMeid(int i) {
        ITelephony iTelephony;
        if (Flags.cleanupCdma() || (iTelephony = getITelephony()) == null) {
            return null;
        }
        try {
            String meidForSlot = iTelephony.getMeidForSlot(i, getOpPackageName(), getAttributionTag());
            if (!TextUtils.isEmpty(meidForSlot)) {
                return meidForSlot;
            }
            Log.d(TAG, "getMeid: return null because MEID is not available");
            return null;
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @Deprecated
    public String getManufacturerCode() {
        if (Flags.cleanupCdma()) {
            return null;
        }
        return getManufacturerCode(getSlotIndex());
    }

    @Deprecated
    public String getManufacturerCode(int i) {
        ITelephony iTelephony;
        if (Flags.cleanupCdma() || (iTelephony = getITelephony()) == null) {
            return null;
        }
        try {
            return iTelephony.getManufacturerCodeForSlot(i);
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public String getNai() {
        return getNaiBySubscriberId(getSubId());
    }

    private String getNaiBySubscriberId(int i) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            String naiForSubscriber = subscriberInfoService.getNaiForSubscriber(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            if (Log.isLoggable(TAG, 2)) {
                com.android.telephony.Rlog.v(TAG, "Nai = " + naiForSubscriber);
            }
            return naiForSubscriber;
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @Deprecated
    public CellLocation getCellLocation() {
        logWithCallerInfo("getCellLocation");
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                com.android.telephony.Rlog.d(TAG, "getCellLocation returning null because telephony is null");
                return null;
            }
            CellLocation asCellLocation = iTelephony.getCellLocation(this.mContext.getOpPackageName(), this.mContext.getAttributionTag()).asCellLocation();
            if (asCellLocation != null && !asCellLocation.isEmpty()) {
                return asCellLocation;
            }
            com.android.telephony.Rlog.d(TAG, "getCellLocation returning null because CellLocation is empty or phone type doesn't match CellLocation type");
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "getCellLocation returning null due to RemoteException " + e);
            return null;
        }
    }

    public CellLocation getCellLocationBySubId(int i) {
        logWithCallerInfo("getCellLocationBySubId");
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony == null) {
                com.android.telephony.Rlog.d(TAG, "getCellLocationBySubId returning null because SemTelephonyService is null");
                return null;
            }
            CellLocation asCellLocation = iSemTelephony.getCellLocationBySubId(i, this.mContext.getOpPackageName(), getAttributionTag()).asCellLocation();
            if (asCellLocation != null && !asCellLocation.isEmpty()) {
                return asCellLocation;
            }
            com.android.telephony.Rlog.d(TAG, "getCellLocationBySubId returning null because CellLocation is empty or phone type doesn't match CellLocation type");
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "getCellLocationBySubId returning null due to RemoteException " + e);
            return null;
        }
    }

    public CellLocation getCellLocationForPhone(int i) {
        logWithCallerInfo("getCellLocationForPhone");
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony == null) {
                com.android.telephony.Rlog.d(TAG, "getCellLocationForPhone returning null because SemTelephonyService is null");
                return null;
            }
            CellLocation asCellLocation = iSemTelephony.getCellLocationForPhone(i, this.mContext.getOpPackageName(), getAttributionTag()).asCellLocation();
            if (asCellLocation != null && !asCellLocation.isEmpty()) {
                return asCellLocation;
            }
            com.android.telephony.Rlog.d(TAG, "getCellLocationForPhone returning null because CellLocation is empty or phone type doesn't match CellLocation type");
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "getCellLocationForPhone returning null due to RemoteException " + e);
            return null;
        }
    }

    @Deprecated
    public List<NeighboringCellInfo> getNeighboringCellInfo() {
        logWithCallerInfo("getNeighboringCellInfo");
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getNeighboringCellInfo(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    public int getCurrentPhoneType() {
        return getCurrentPhoneType(getSubId());
    }

    @SystemApi
    public int getCurrentPhoneType(int i) {
        return getCurrentPhoneTypeForSlot(i == -1 ? 0 : SubscriptionManager.getPhoneId(i));
    }

    public int getCurrentPhoneTypeForSlot(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getActivePhoneTypeForSlot(i);
            }
            return getPhoneTypeFromProperty(i);
        } catch (RemoteException unused) {
            return getPhoneTypeFromProperty(i);
        } catch (NullPointerException unused2) {
            return getPhoneTypeFromProperty(i);
        }
    }

    public int getPhoneType() {
        if (isDeviceVoiceCapable() || isDataCapable()) {
            return getCurrentPhoneType();
        }
        return 0;
    }

    private int getPhoneTypeFromProperty(int i) {
        Integer num = (Integer) getTelephonyProperty(i, TelephonyProperties.current_active_phone(), (Object) null);
        if (num != null) {
            return num.intValue();
        }
        return getPhoneTypeFromNetworkType(i);
    }

    private int getPhoneTypeFromNetworkType(int i) {
        Integer num = (Integer) getTelephonyProperty(i, TelephonyProperties.default_network(), (Object) null);
        if (num != null) {
            return getPhoneType(num.intValue());
        }
        return 0;
    }

    public static int getPhoneType(int i) {
        if (i != 11) {
            if (i != 21) {
                if (i != 24) {
                    if (i != 25) {
                        switch (i) {
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                                break;
                            default:
                                return 1;
                        }
                    }
                }
            }
            return 2;
        }
        return TelephonyProperties.lte_on_cdma_device().orElse(0).intValue() == 1 ? 2 : 1;
    }

    public String getNetworkOperatorName() {
        return getNetworkOperatorName(getSubId());
    }

    public String getNetworkOperatorName(int i) {
        return (String) getTelephonyProperty(SubscriptionManager.getPhoneId(i), TelephonyProperties.operator_alpha(), "");
    }

    public String getNetworkOperator() {
        return getNetworkOperatorForPhone(getPhoneId());
    }

    public String getNetworkOperator(int i) {
        return getNetworkOperatorForPhone(SubscriptionManager.getPhoneId(i));
    }

    public String getNetworkOperatorForPhone(int i) {
        return (String) getTelephonyProperty(i, TelephonyProperties.operator_numeric(), "");
    }

    public String getNetworkSpecifier() {
        return String.valueOf(getSubId());
    }

    public PersistableBundle getCarrierConfig() {
        CarrierConfigManager carrierConfigManager = (CarrierConfigManager) this.mContext.getSystemService(CarrierConfigManager.class);
        if (carrierConfigManager == null) {
            throw new UnsupportedOperationException("getCarrierConfig is unsupported without android.hardware.telephony.subscription");
        }
        return carrierConfigManager.getConfigForSubId(getSubId());
    }

    public boolean isNetworkRoaming() {
        return isNetworkRoaming(getSubId());
    }

    public boolean isNetworkRoaming(int i) {
        return ((Boolean) getTelephonyProperty(SubscriptionManager.getPhoneId(i), (List<boolean>) TelephonyProperties.operator_is_roaming(), false)).booleanValue();
    }

    public String getNetworkCountryIso() {
        return getNetworkCountryIso(getSlotIndex());
    }

    public String getNetworkCountryIso(int i) {
        if (i != Integer.MAX_VALUE) {
            try {
                if (!SubscriptionManager.isValidSlotIndex(i)) {
                    throw new IllegalArgumentException("invalid slot index " + i);
                }
            } catch (RemoteException unused) {
                return "";
            }
        }
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return "";
        }
        return iTelephony.getNetworkCountryIsoForPhone(i);
    }

    @Deprecated
    public String getNetworkCountryIsoForPhone(int i) {
        return getNetworkCountryIso(i);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static int[] getAllNetworkTypes() {
        return (int[]) NETWORK_TYPES.clone();
    }

    @Deprecated
    public int getNetworkType() {
        return getNetworkType(getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
    }

    public int getNetworkType(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getNetworkTypeForSubscriber(i, getOpPackageName(), getAttributionTag());
            }
        } catch (RemoteException | NullPointerException unused) {
        }
        return 0;
    }

    public int semGetNetworkType(int i) {
        return getNetworkType(i);
    }

    public int getDataNetworkType() {
        return getDataNetworkType(getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
    }

    public int getDataNetworkType(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                Log.e(TAG, "getDataNetworkType: ITelephony interface is not up yet");
                return 0;
            }
            return iTelephony.getDataNetworkTypeForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException e) {
            Log.e(TAG, "getDataNetworkType: " + e.getMessage());
            return 0;
        }
    }

    public int getVoiceNetworkType() {
        return getVoiceNetworkType(getSubId());
    }

    public int getVoiceNetworkType(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getVoiceNetworkTypeForSubscriber(i, getOpPackageName(), getAttributionTag());
            }
        } catch (RemoteException | NullPointerException unused) {
        }
        return 0;
    }

    public String getNetworkTypeName() {
        return getNetworkTypeName(getNetworkType());
    }

    public static String getNetworkTypeName(int i) {
        switch (i) {
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
                return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public boolean hasIccCard() {
        return hasIccCard(getSlotIndex());
    }

    public boolean hasIccCard(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            return iTelephony.hasIccCardUsingSlotIndex(i);
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    public boolean semHasIccCard(int i) {
        return hasIccCard(i);
    }

    public int getSimState() {
        int simStateIncludingLoaded = getSimStateIncludingLoaded();
        if (simStateIncludingLoaded == 10) {
            return 5;
        }
        if (simStateIncludingLoaded == 12 || simStateIncludingLoaded == 13 || simStateIncludingLoaded == 14 || simStateIncludingLoaded == 15) {
            return 4;
        }
        if (simStateIncludingLoaded == 16) {
            return 6;
        }
        return simStateIncludingLoaded;
    }

    private int getSimStateIncludingLoaded() {
        int slotIndex = getSlotIndex();
        if (slotIndex < 0) {
            for (int i = 0; i < getPhoneCount(); i++) {
                int simState = getSimState(i);
                if (simState != 1) {
                    com.android.telephony.Rlog.d(TAG, "getSimState: default sim:" + slotIndex + ", sim state for slotIndex=" + i + " is " + simState + ", return state as unknown");
                    return 0;
                }
            }
            com.android.telephony.Rlog.d(TAG, "getSimState: default sim:" + slotIndex + ", all SIMs absent, return state as absent");
            return 1;
        }
        return getSimStateForSlotIndex(slotIndex);
    }

    @SystemApi
    public int getSimCardState() {
        return getSimCardStateFromSimState(getSimState());
    }

    @SystemApi
    @Deprecated
    public int getSimCardState(int i) {
        return getSimCardStateFromSimState(getSimState(getLogicalSlotIndex(i, getFirstActivePortIndex(i))));
    }

    @SystemApi
    public int getSimCardState(int i, int i2) {
        return getSimCardStateFromSimState(getSimState(getLogicalSlotIndex(i, i2)));
    }

    private int getLogicalSlotIndex(int i, int i2) {
        UiccSlotInfo uiccSlotInfo;
        UiccSlotInfo[] uiccSlotsInfo = getUiccSlotsInfo();
        if (uiccSlotsInfo == null || i < 0 || i >= uiccSlotsInfo.length || (uiccSlotInfo = uiccSlotsInfo[i]) == null) {
            return -1;
        }
        for (UiccPortInfo uiccPortInfo : uiccSlotInfo.getPorts()) {
            if (uiccPortInfo.getPortIndex() == i2) {
                return uiccPortInfo.getLogicalSlotIndex();
            }
        }
        return -1;
    }

    @SystemApi
    public int getSimApplicationState() {
        return getSimApplicationStateFromSimState(getSimStateIncludingLoaded());
    }

    @SystemApi
    @Deprecated
    public int getSimApplicationState(int i) {
        return getSimApplicationStateFromSimState(getSimStateForSlotIndex(getLogicalSlotIndex(i, getFirstActivePortIndex(i))));
    }

    @SystemApi
    public int getSimApplicationState(int i, int i2) {
        return getSimApplicationStateFromSimState(getSimStateForSlotIndex(getLogicalSlotIndex(i, i2)));
    }

    @SystemApi
    public boolean isApplicationOnUicc(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isApplicationOnUicc(getSubId(), i);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isApplicationOnUicc", e);
            return false;
        }
    }

    public int getSimState(int i) {
        int simStateForSlotIndex = getSimStateForSlotIndex(i);
        if (simStateForSlotIndex == 10) {
            return 5;
        }
        if (simStateForSlotIndex == 12 || simStateForSlotIndex == 13 || simStateForSlotIndex == 14 || simStateForSlotIndex == 15) {
            return 4;
        }
        if (simStateForSlotIndex == 16) {
            return 6;
        }
        return simStateForSlotIndex;
    }

    public String getSimOperator() {
        return getSimOperatorNumeric();
    }

    public String getSimOperator(int i) {
        return getSimOperatorNumeric(i);
    }

    public String getSimOperatorNumeric() {
        int i = this.mSubId;
        if (!SubscriptionManager.isUsableSubIdValue(i)) {
            i = SubscriptionManager.getDefaultDataSubscriptionId();
            if (!SubscriptionManager.isUsableSubIdValue(i)) {
                i = SubscriptionManager.getDefaultSmsSubscriptionId();
                if (!SubscriptionManager.isUsableSubIdValue(i)) {
                    i = SubscriptionManager.getDefaultVoiceSubscriptionId();
                    if (!SubscriptionManager.isUsableSubIdValue(i)) {
                        i = SubscriptionManager.getDefaultSubscriptionId();
                    }
                }
            }
        }
        return getSimOperatorNumeric(i);
    }

    public String getSimOperatorNumeric(int i) {
        return getSimOperatorNumericForPhone(SubscriptionManager.getPhoneId(i));
    }

    public String getSimOperatorNumericForPhone(int i) {
        return (String) getTelephonyProperty(i, TelephonyProperties.icc_operator_numeric(), "");
    }

    public String getSimOperatorName() {
        return getSimOperatorNameForPhone(getPhoneId());
    }

    public String getSimOperatorName(int i) {
        return getSimOperatorNameForPhone(SubscriptionManager.getPhoneId(i));
    }

    public String getSimOperatorNameForPhone(int i) {
        return (String) getTelephonyProperty(i, TelephonyProperties.icc_operator_alpha(), "");
    }

    public String getSimCountryIso() {
        return getSimCountryIsoForPhone(getPhoneId());
    }

    public static String getSimCountryIso(int i) {
        return getSimCountryIsoForPhone(SubscriptionManager.getPhoneId(i));
    }

    public static String getSimCountryIsoForPhone(int i) {
        return (String) getTelephonyProperty(i, TelephonyProperties.icc_operator_iso_country(), "");
    }

    public String getSimSerialNumber() {
        return getSimSerialNumber(getSubId());
    }

    public String getSimSerialNumber(int i) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIccSerialNumberForSubscriber(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    public boolean isLteCdmaEvdoGsmWcdmaEnabled() {
        return getLteOnCdmaMode(getSubId()) == 1;
    }

    public int getLteOnCdmaMode(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return -1;
            }
            return iTelephony.getLteOnCdmaModeForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return -1;
        }
    }

    public int getCardIdForDefaultEuicc() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return -2;
            }
            return iTelephony.getCardIdForDefaultEuicc(this.mSubId, this.mContext.getOpPackageName());
        } catch (RemoteException unused) {
            return -2;
        }
    }

    public List<UiccCardInfo> getUiccCardsInfo() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                Log.e(TAG, "Error in getUiccCardsInfo: unable to connect to Telephony service.");
                return new ArrayList();
            }
            return iTelephony.getUiccCardsInfo(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            Log.e(TAG, "Error in getUiccCardsInfo: " + e);
            return new ArrayList();
        }
    }

    @SystemApi
    public UiccSlotInfo[] getUiccSlotsInfo() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getUiccSlotsInfo(this.mContext.getOpPackageName());
        } catch (RemoteException unused) {
            return null;
        }
    }

    public void refreshUiccProfile() {
        try {
            getITelephony().refreshUiccProfile(this.mSubId);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.w(TAG, "RemoteException", e);
        }
    }

    @SystemApi
    @Deprecated
    public boolean switchSlots(int[] iArr) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            return iTelephony.switchSlots(iArr);
        } catch (RemoteException unused) {
            return false;
        }
    }

    private static boolean isSlotMappingValid(Collection<UiccSlotMapping> collection) {
        Iterator it = ((Map) collection.stream().collect(Collectors.groupingBy(new Function() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((UiccSlotMapping) obj).getLogicalSlotIndex());
            }
        }))).entrySet().iterator();
        while (it.hasNext()) {
            if (((List) ((Map.Entry) it.next()).getValue()).size() > 1) {
                return false;
            }
        }
        Iterator it2 = ((Map) collection.stream().collect(Collectors.groupingBy(new Function() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                List asList;
                asList = Arrays.asList(Integer.valueOf(r1.getPhysicalSlotIndex()), Integer.valueOf(((UiccSlotMapping) obj).getPortIndex()));
                return asList;
            }
        }))).entrySet().iterator();
        while (it2.hasNext()) {
            if (((List) ((Map.Entry) it2.next()).getValue()).size() > 1) {
                return false;
            }
        }
        return true;
    }

    @SystemApi
    public void setSimSlotMapping(Collection<UiccSlotMapping> collection) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (isSlotMappingValid(collection)) {
                    if (!iTelephony.setSimSlotMapping(new ArrayList(collection))) {
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
        HashMap hashMap = new HashMap();
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                for (UiccSlotMapping uiccSlotMapping : iTelephony.getSlotsMapping(this.mContext.getOpPackageName())) {
                    hashMap.put(Integer.valueOf(uiccSlotMapping.getLogicalSlotIndex()), Integer.valueOf(uiccSlotMapping.getPhysicalSlotIndex()));
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "getSlotsMapping RemoteException", e);
        }
        return hashMap;
    }

    @SystemApi
    public Collection<UiccSlotMapping> getSimSlotMapping() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSlotsMapping(this.mContext.getOpPackageName());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public String getSubscriberId() {
        return getSubscriberId(getSubId());
    }

    public String getSubscriberId(int i) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getSubscriberIdForSubscriber(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    public ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int i) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                com.android.telephony.Rlog.e(TAG, "IMSI error: Subscriber Info is null");
                return null;
            }
            int subId = getSubId(SubscriptionManager.getDefaultDataSubscriptionId());
            if (i != 1 && i != 2) {
                throw new IllegalArgumentException("IMSI error: Invalid key type");
            }
            ImsiEncryptionInfo carrierInfoForImsiEncryption = subscriberInfoService.getCarrierInfoForImsiEncryption(subId, i, this.mContext.getOpPackageName());
            if (carrierInfoForImsiEncryption == null && isImsiEncryptionRequired(subId, i)) {
                com.android.telephony.Rlog.e(TAG, "IMSI error: key is required but not found");
                throw new IllegalArgumentException("IMSI error: key is required but not found");
            }
            return carrierInfoForImsiEncryption;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCarrierInfoForImsiEncryption RemoteException" + e);
            return null;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCarrierInfoForImsiEncryption NullPointerException" + e2);
            return null;
        }
    }

    @SystemApi
    public void resetCarrierKeysForImsiEncryption() {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                throw new RuntimeException("IMSI error: Subscriber Info is null");
            }
            subscriberInfoService.resetCarrierKeysForImsiEncryption(getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#getCarrierInfoForImsiEncryption RemoteException" + e);
        }
    }

    private boolean isImsiEncryptionRequired(int i, int i2) {
        PersistableBundle configForSubId;
        CarrierConfigManager carrierConfigManager = (CarrierConfigManager) this.mContext.getSystemService("carrier_config");
        if (carrierConfigManager == null || (configForSubId = carrierConfigManager.getConfigForSubId(i)) == null) {
            return false;
        }
        return isKeyEnabled(configForSubId.getInt(CarrierConfigManager.IMSI_KEY_AVAILABILITY_INT), i2);
    }

    public void setCarrierInfoForImsiEncryption(ImsiEncryptionInfo imsiEncryptionInfo) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return;
            }
            subscriberInfoService.setCarrierInfoForImsiEncryption(this.mSubId, this.mContext.getOpPackageName(), imsiEncryptionInfo);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setCarrierInfoForImsiEncryption RemoteException", e);
        } catch (NullPointerException unused) {
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
        public @interface CallComposerError {
        }

        public CallComposerException(int i, IOException iOException) {
            this.mErrorCode = i;
            this.mIOException = iOException;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public IOException getIOException() {
            return this.mIOException;
        }
    }

    public void uploadCallComposerPicture(final Path path, final String str, final Executor executor, final OutcomeReceiver<ParcelUuid, CallComposerException> outcomeReceiver) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        if (!((RoleManager) this.mContext.getSystemService(RoleManager.class)).isRoleHeld("android.app.role.DIALER")) {
            throw new SecurityException("You must hold RoleManager.ROLE_DIALER to do this");
        }
        executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                TelephonyManager.this.lambda$uploadCallComposerPicture$1(path, outcomeReceiver, str, executor);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadCallComposerPicture$1(Path path, final OutcomeReceiver outcomeReceiver, String str, Executor executor) {
        try {
            if (Looper.getMainLooper().isCurrentThread()) {
                Log.w(TAG, "Uploading call composer picture on main thread! hic sunt dracones!");
            }
            if (Files.size(path) > getMaximumCallComposerPictureSize()) {
                outcomeReceiver.onError(new CallComposerException(2, null));
                return;
            }
            final InputStream newInputStream = Files.newInputStream(path, new OpenOption[0]);
            try {
                uploadCallComposerPicture(newInputStream, str, executor, new OutcomeReceiver<ParcelUuid, CallComposerException>(this) { // from class: android.telephony.TelephonyManager.2
                    @Override // android.os.OutcomeReceiver
                    public void onResult(ParcelUuid parcelUuid) {
                        try {
                            newInputStream.close();
                        } catch (IOException unused) {
                            Log.e(TelephonyManager.TAG, "Error closing file input stream when uploading call composer pic");
                        }
                        outcomeReceiver.onResult(parcelUuid);
                    }

                    @Override // android.os.OutcomeReceiver
                    public void onError(CallComposerException callComposerException) {
                        try {
                            newInputStream.close();
                        } catch (IOException unused) {
                            Log.e(TelephonyManager.TAG, "Error closing file input stream when uploading call composer pic");
                        }
                        outcomeReceiver.onError(callComposerException);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Got exception calling into stream-version of uploadCallComposerPicture: " + e);
                try {
                    newInputStream.close();
                } catch (IOException unused) {
                    Log.e(TAG, "Error closing file input stream when uploading call composer pic");
                }
            }
        } catch (IOException e2) {
            Log.e(TAG, "IOException when uploading call composer pic:" + e2);
            outcomeReceiver.onError(new CallComposerException(5, e2));
        }
    }

    public void uploadCallComposerPicture(final InputStream inputStream, String str, Executor executor, final OutcomeReceiver<ParcelUuid, CallComposerException> outcomeReceiver) {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new IllegalStateException("Telephony service not available.");
        }
        try {
            ParcelFileDescriptor[] createReliablePipe = ParcelFileDescriptor.createReliablePipe();
            final ParcelFileDescriptor parcelFileDescriptor = createReliablePipe[1];
            ParcelFileDescriptor parcelFileDescriptor2 = createReliablePipe[0];
            final ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
            try {
                iTelephony.uploadCallComposerPicture(getSubId(), this.mContext.getOpPackageName(), str, parcelFileDescriptor2, new AnonymousClass3(this, null, executor, outcomeReceiver));
            } catch (RemoteException e) {
                Log.e(TAG, "Remote exception uploading call composer pic:" + e);
                e.rethrowAsRuntimeException();
            }
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyManager.lambda$uploadCallComposerPicture$3(inputStream, outcomeReceiver, parcelFileDescriptor, autoCloseOutputStream);
                }
            });
        } catch (IOException e2) {
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    OutcomeReceiver.this.onError(new TelephonyManager.CallComposerException(5, e2));
                }
            });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$3, reason: invalid class name */
    class AnonymousClass3 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(TelephonyManager telephonyManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i != -1) {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(new TelephonyManager.CallComposerException(i, null));
                    }
                });
                return;
            }
            final ParcelUuid parcelUuid = (ParcelUuid) bundle.getParcelable(TelephonyManager.KEY_CALL_COMPOSER_PICTURE_HANDLE, ParcelUuid.class);
            if (parcelUuid == null) {
                Log.e(TelephonyManager.TAG, "Got null uuid without an error while uploading call composer pic");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.TelephonyManager$3$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(new TelephonyManager.CallComposerException(0, null));
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.TelephonyManager$3$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    OutcomeReceiver.this.onResult(parcelUuid);
                }
            });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x002b, code lost:
    
        android.util.Log.e(android.telephony.TelephonyManager.TAG, "Read too many bytes from call composer pic stream: " + r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003f, code lost:
    
        r11.onError(new android.telephony.TelephonyManager.CallComposerException(2, null));
        r12.closeWithError("too large");
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0051, code lost:
    
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0052, code lost:
    
        android.util.Log.e(android.telephony.TelephonyManager.TAG, "Error closing fd pipe: " + r10);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ void lambda$uploadCallComposerPicture$3(java.io.InputStream r10, android.os.OutcomeReceiver r11, android.os.ParcelFileDescriptor r12, java.io.OutputStream r13) {
        /*
            java.lang.String r0 = "Error closing fd pipe: "
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            boolean r1 = r1.isCurrentThread()
            java.lang.String r2 = "TelephonyManager"
            if (r1 == 0) goto L13
            java.lang.String r1 = "Uploading call composer picture on main thread! hic sunt dracones!"
            android.util.Log.w(r2, r1)
        L13:
            r1 = 16384(0x4000, float:2.2959E-41)
            byte[] r1 = new byte[r1]
            r3 = 0
            r4 = r3
        L19:
            int r5 = r10.read(r1)     // Catch: java.lang.Throwable -> L8e java.io.IOException -> L90
            if (r5 >= 0) goto L21
            goto Lc7
        L21:
            int r4 = r4 + r5
            long r6 = (long) r4
            long r8 = getMaximumCallComposerPictureSize()     // Catch: java.lang.Throwable -> L8e
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 <= 0) goto L65
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8e
            r10.<init>()     // Catch: java.lang.Throwable -> L8e
            java.lang.String r1 = "Read too many bytes from call composer pic stream: "
            r10.append(r1)     // Catch: java.lang.Throwable -> L8e
            r10.append(r4)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L8e
            android.util.Log.e(r2, r10)     // Catch: java.lang.Throwable -> L8e
            android.telephony.TelephonyManager$CallComposerException r10 = new android.telephony.TelephonyManager$CallComposerException     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L8e
            r1 = 2
            r3 = 0
            r10.<init>(r1, r3)     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L8e
            r11.onError(r10)     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L8e
            java.lang.String r10 = "too large"
            r12.closeWithError(r10)     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L8e
            goto Lc7
        L51:
            r10 = move-exception
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8e
            r11.<init>()     // Catch: java.lang.Throwable -> L8e
            r11.append(r0)     // Catch: java.lang.Throwable -> L8e
            r11.append(r10)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r10 = r11.toString()     // Catch: java.lang.Throwable -> L8e
            android.util.Log.e(r2, r10)     // Catch: java.lang.Throwable -> L8e
            goto Lc7
        L65:
            r13.write(r1, r3, r5)     // Catch: java.io.IOException -> L69 java.lang.Throwable -> L8e
            goto L19
        L69:
            r10 = move-exception
            android.telephony.TelephonyManager$CallComposerException r1 = new android.telephony.TelephonyManager$CallComposerException     // Catch: java.lang.Throwable -> L8e
            r3 = 1
            r1.<init>(r3, r10)     // Catch: java.lang.Throwable -> L8e
            r11.onError(r1)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r10 = "remote end closed"
            r12.closeWithError(r10)     // Catch: java.io.IOException -> L7a java.lang.Throwable -> L8e
            goto Lc7
        L7a:
            r10 = move-exception
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8e
            r11.<init>()     // Catch: java.lang.Throwable -> L8e
            r11.append(r0)     // Catch: java.lang.Throwable -> L8e
            r11.append(r10)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r10 = r11.toString()     // Catch: java.lang.Throwable -> L8e
            android.util.Log.e(r2, r10)     // Catch: java.lang.Throwable -> L8e
            goto Lc7
        L8e:
            r10 = move-exception
            goto Lcb
        L90:
            r10 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8e
            r1.<init>()     // Catch: java.lang.Throwable -> L8e
            java.lang.String r3 = "IOException reading from input while uploading pic: "
            r1.append(r3)     // Catch: java.lang.Throwable -> L8e
            r1.append(r10)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L8e
            android.util.Log.e(r2, r1)     // Catch: java.lang.Throwable -> L8e
            android.telephony.TelephonyManager$CallComposerException r1 = new android.telephony.TelephonyManager$CallComposerException     // Catch: java.lang.Throwable -> L8e
            r3 = 4
            r1.<init>(r3, r10)     // Catch: java.lang.Throwable -> L8e
            r11.onError(r1)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r10 = "input closed"
            r12.closeWithError(r10)     // Catch: java.lang.Throwable -> L8e java.io.IOException -> Lb4
            goto Lc7
        Lb4:
            r10 = move-exception
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8e
            r11.<init>()     // Catch: java.lang.Throwable -> L8e
            r11.append(r0)     // Catch: java.lang.Throwable -> L8e
            r11.append(r10)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r10 = r11.toString()     // Catch: java.lang.Throwable -> L8e
            android.util.Log.e(r2, r10)     // Catch: java.lang.Throwable -> L8e
        Lc7:
            r13.close()     // Catch: java.io.IOException -> Lca
        Lca:
            return
        Lcb:
            r13.close()     // Catch: java.io.IOException -> Lce
        Lce:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.TelephonyManager.lambda$uploadCallComposerPicture$3(java.io.InputStream, android.os.OutcomeReceiver, android.os.ParcelFileDescriptor, java.io.OutputStream):void");
    }

    public String getGroupIdLevel1() {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getGroupIdLevel1ForSubscriber(getSubId(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public String getGroupIdLevel1(int i) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getGroupIdLevel1ForSubscriber(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    public String getGroupIdLevel2() {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getGroupIdLevel2ForSubscriber(getSubId(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @Deprecated
    public String getLine1Number() {
        return getLine1Number(getSubId());
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getLine1Number(int r5) {
        /*
            r4 = this;
            r0 = 0
            com.android.internal.telephony.ITelephony r1 = getITelephony()     // Catch: java.lang.Throwable -> L18
            if (r1 == 0) goto L18
            android.content.Context r2 = r4.mContext     // Catch: java.lang.Throwable -> L18
            java.lang.String r2 = r2.getOpPackageName()     // Catch: java.lang.Throwable -> L18
            android.content.Context r3 = r4.mContext     // Catch: java.lang.Throwable -> L18
            java.lang.String r3 = r3.getAttributionTag()     // Catch: java.lang.Throwable -> L18
            java.lang.String r1 = r1.getLine1NumberForDisplay(r5, r2, r3)     // Catch: java.lang.Throwable -> L18
            goto L19
        L18:
            r1 = r0
        L19:
            if (r1 == 0) goto L1c
            return r1
        L1c:
            com.android.internal.telephony.IPhoneSubInfo r1 = getSubscriberInfoService()     // Catch: java.lang.Throwable -> L34
            if (r1 != 0) goto L23
            return r0
        L23:
            android.content.Context r2 = r4.mContext     // Catch: java.lang.Throwable -> L34
            java.lang.String r2 = r2.getOpPackageName()     // Catch: java.lang.Throwable -> L34
            android.content.Context r4 = r4.mContext     // Catch: java.lang.Throwable -> L34
            java.lang.String r4 = r4.getAttributionTag()     // Catch: java.lang.Throwable -> L34
            java.lang.String r4 = r1.getLine1NumberForSubscriber(r5, r2, r4)     // Catch: java.lang.Throwable -> L34
            return r4
        L34:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.TelephonyManager.getLine1Number(int):java.lang.String");
    }

    @Deprecated
    public boolean setLine1NumberForDisplay(String str, String str2) {
        return setLine1NumberForDisplay(getSubId(), str, str2);
    }

    public boolean setLine1NumberForDisplay(int i, String str, String str2) {
        try {
            this.mSubscriptionManager.setCarrierPhoneNumber(i, str2 == null ? "" : str2);
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setLine1NumberForDisplayForSubscriber(i, str, str2);
            }
            return false;
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    public String getLine1AlphaTag() {
        return getLine1AlphaTag(getSubId());
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0018 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getLine1AlphaTag(int r5) {
        /*
            r4 = this;
            r0 = 0
            com.android.internal.telephony.ITelephony r1 = getITelephony()     // Catch: java.lang.Throwable -> L14
            if (r1 == 0) goto L14
            java.lang.String r2 = r4.getOpPackageName()     // Catch: java.lang.Throwable -> L14
            java.lang.String r3 = r4.getAttributionTag()     // Catch: java.lang.Throwable -> L14
            java.lang.String r1 = r1.getLine1AlphaTagForDisplay(r5, r2, r3)     // Catch: java.lang.Throwable -> L14
            goto L15
        L14:
            r1 = r0
        L15:
            if (r1 == 0) goto L18
            return r1
        L18:
            com.android.internal.telephony.IPhoneSubInfo r1 = getSubscriberInfoService()     // Catch: java.lang.Throwable -> L2c
            if (r1 != 0) goto L1f
            return r0
        L1f:
            java.lang.String r2 = r4.getOpPackageName()     // Catch: java.lang.Throwable -> L2c
            java.lang.String r4 = r4.getAttributionTag()     // Catch: java.lang.Throwable -> L2c
            java.lang.String r4 = r1.getLine1AlphaTagForSubscriber(r5, r2, r4)     // Catch: java.lang.Throwable -> L2c
            return r4
        L2c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.TelephonyManager.getLine1AlphaTag(int):java.lang.String");
    }

    @Deprecated
    public String[] getMergedSubscriberIds() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getMergedSubscriberIds(getSubId(), getOpPackageName(), getAttributionTag());
            }
            return null;
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    public String[] getMergedImsisFromGroup() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                String[] mergedImsisFromGroup = iTelephony.getMergedImsisFromGroup(getSubId(), getOpPackageName());
                if (mergedImsisFromGroup != null) {
                    return mergedImsisFromGroup;
                }
            }
        } catch (RemoteException unused) {
        }
        return new String[0];
    }

    public String getMsisdn() {
        return getMsisdn(getSubId());
    }

    public String getMsisdn(int i) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getMsisdnForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public String getVoiceMailNumber() {
        return getVoiceMailNumber(getSubId());
    }

    public String getVoiceMailNumber(int i) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getVoiceMailNumberForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public boolean setVoiceMailNumber(String str, String str2) {
        return setVoiceMailNumber(getSubId(), str, str2);
    }

    public boolean setVoiceMailNumber(int i, String str, String str2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setVoiceMailNumber(i, str, str2);
            }
            return false;
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    @SystemApi
    public Bundle getVisualVoicemailSettings() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getVisualVoicemailSettings(this.mContext.getOpPackageName(), this.mSubId);
            }
            return null;
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public String getVisualVoicemailPackageName() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getVisualVoicemailPackageName(this.mContext.getOpPackageName(), getAttributionTag(), getSubId());
            }
            return null;
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public void setVisualVoicemailSmsFilterSettings(VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) {
        if (visualVoicemailSmsFilterSettings == null) {
            disableVisualVoicemailSmsFilter(this.mSubId);
        } else {
            enableVisualVoicemailSmsFilter(this.mSubId, visualVoicemailSmsFilterSettings);
        }
    }

    public void sendVisualVoicemailSms(String str, int i, String str2, PendingIntent pendingIntent) {
        sendVisualVoicemailSmsForSubscriber(this.mSubId, str, i, str2, pendingIntent);
    }

    public void enableVisualVoicemailSmsFilter(int i, VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) {
        if (visualVoicemailSmsFilterSettings == null) {
            throw new IllegalArgumentException("Settings cannot be null");
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.enableVisualVoicemailSmsFilter(this.mContext.getOpPackageName(), i, visualVoicemailSmsFilterSettings);
            }
        } catch (RemoteException | NullPointerException unused) {
        }
    }

    public void disableVisualVoicemailSmsFilter(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.disableVisualVoicemailSmsFilter(this.mContext.getOpPackageName(), i);
            }
        } catch (RemoteException | NullPointerException unused) {
        }
    }

    public VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getVisualVoicemailSmsFilterSettings(this.mContext.getOpPackageName(), i);
            }
            return null;
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getActiveVisualVoicemailSmsFilterSettings(i);
            }
            return null;
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public void sendVisualVoicemailSmsForSubscriber(int i, String str, int i2, String str2, PendingIntent pendingIntent) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.sendVisualVoicemailSmsForSubscriber(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), i, str, i2, str2, pendingIntent);
            }
        } catch (RemoteException unused) {
        }
    }

    @SystemApi
    public void setVoiceActivationState(int i) {
        setVoiceActivationState(getSubId(), i);
    }

    public void setVoiceActivationState(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setVoiceActivationState(i, i2);
            }
        } catch (RemoteException | NullPointerException unused) {
        }
    }

    @SystemApi
    public void setDataActivationState(int i) {
        setDataActivationState(getSubId(), i);
    }

    public void setDataActivationState(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setDataActivationState(i, i2);
            }
        } catch (RemoteException | NullPointerException unused) {
        }
    }

    @SystemApi
    public int getVoiceActivationState() {
        return getVoiceActivationState(getSubId());
    }

    public int getVoiceActivationState(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getVoiceActivationState(i, getOpPackageName());
            }
            return 0;
        } catch (RemoteException | NullPointerException unused) {
            return 0;
        }
    }

    @SystemApi
    public int getDataActivationState() {
        return getDataActivationState(getSubId());
    }

    public int getDataActivationState(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getDataActivationState(i, getOpPackageName());
            }
            return 0;
        } catch (RemoteException | NullPointerException unused) {
            return 0;
        }
    }

    public int getVoiceMessageCount() {
        return getVoiceMessageCount(getSubId());
    }

    public int getVoiceMessageCount(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return 0;
            }
            return iTelephony.getVoiceMessageCountForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return 0;
        }
    }

    public int semGetVoiceMessageCount() {
        return getVoiceMessageCount();
    }

    public String getVoiceMailAlphaTag() {
        return getVoiceMailAlphaTag(getSubId());
    }

    public String getVoiceMailAlphaTag(int i) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getVoiceMailAlphaTagForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public void sendDialerSpecialCode(String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return;
            }
            iTelephony.sendDialerSpecialCode(this.mContext.getOpPackageName(), str);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#sendDialerSpecialCode RemoteException" + e);
        }
    }

    @Deprecated
    public String getIsimImpi() {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIsimImpi(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    public String getImsPrivateUserIdentity() {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                com.android.telephony.Rlog.e(TAG, "getImsPrivateUserIdentity(): IPhoneSubInfo instance is NULL");
                throw new RuntimeException("IMPI error: Subscriber Info is null");
            }
            return subscriberInfoService.getImsPrivateUserIdentity(getSubId(), getOpPackageName(), getAttributionTag());
        } catch (RemoteException | IllegalArgumentException | NullPointerException e) {
            com.android.telephony.Rlog.e(TAG, "getImsPrivateUserIdentity() Exception = " + e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @SystemApi
    public String getIsimDomain() {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIsimDomain(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @Deprecated
    public String[] getIsimImpu() {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIsimImpu(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public String[] semGetIsimImpu() {
        return getIsimImpu();
    }

    @SystemApi
    public List<Uri> getImsPublicUserIdentities() {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                throw new RuntimeException("IMPU error: Subscriber Info is null");
            }
            return subscriberInfoService.getImsPublicUserIdentities(getSubId(), getOpPackageName());
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getImsPublicUserIdentities Exception = " + e);
            e.rethrowAsRuntimeException();
            return Collections.EMPTY_LIST;
        } catch (IllegalArgumentException | NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getImsPublicUserIdentities Exception = " + e2);
            return Collections.EMPTY_LIST;
        }
    }

    @Deprecated
    public int getCallState() {
        TelecomManager telecomManager;
        Context context = this.mContext;
        if (context == null || (telecomManager = (TelecomManager) context.getSystemService(TelecomManager.class)) == null) {
            return 0;
        }
        return telecomManager.getCallState();
    }

    public int getCallStateForSubscription() {
        return getCallState(getSubId());
    }

    public int getCallState(int i) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return 0;
        }
        try {
            return iTelephony.getCallStateForSubscription(i, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public int semGetCallState(int i) {
        return getCallState(i);
    }

    public int getDataActivity() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return 0;
            }
            return iTelephony.getDataActivityForSubId(getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
        } catch (RemoteException | NullPointerException unused) {
            return 0;
        }
    }

    public int getDataState() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return 0;
            }
            int dataStateForSubId = iTelephony.getDataStateForSubId(getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
            if (dataStateForSubId != 4) {
                return dataStateForSubId;
            }
            if (Compatibility.isChangeEnabled(GET_DATA_STATE_R_VERSION)) {
                return dataStateForSubId;
            }
            return 2;
        } catch (RemoteException | NullPointerException unused) {
            return 0;
        }
    }

    public int semGetDataState(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return 0;
            }
            int dataStateForSubId = iTelephony.getDataStateForSubId(i);
            if (dataStateForSubId == 4) {
                if (!Compatibility.isChangeEnabled(GET_DATA_STATE_R_VERSION)) {
                    return 2;
                }
            }
            return dataStateForSubId;
        } catch (RemoteException | NullPointerException unused) {
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ITelephony getITelephony() {
        if (!sServiceHandleCacheEnabled) {
            return ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
        }
        if (sITelephony == null) {
            ITelephony asInterface = ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
            synchronized (sCacheLock) {
                if (sITelephony == null && asInterface != null) {
                    try {
                        sITelephony = asInterface;
                        asInterface.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception unused) {
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
            ISemTelephony asInterface = ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
            synchronized (sCacheLock) {
                if (sISemTelephony == null && asInterface != null) {
                    try {
                        sISemTelephony = asInterface;
                        asInterface.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception unused) {
                        sISemTelephony = null;
                    }
                }
            }
        }
        return sISemTelephony;
    }

    private IOns getIOns() {
        return IOns.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getOpportunisticNetworkServiceRegisterer().get());
    }

    @Deprecated
    public void listen(PhoneStateListener phoneStateListener, int i) {
        if (this.mContext == null) {
            return;
        }
        boolean z = getITelephony() != null;
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            Set<String> renouncedPermissions = getRenouncedPermissions();
            telephonyRegistryManager.listenFromListener(this.mSubId, renouncedPermissions.contains(Manifest.permission.ACCESS_FINE_LOCATION), renouncedPermissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION), getOpPackageName(), getAttributionTag(), phoneStateListener, i, z);
        } else {
            com.android.telephony.Rlog.w(TAG, "telephony registry not ready.");
        }
    }

    @SystemApi
    @Deprecated
    public int getCdmaEnhancedRoamingIndicatorDisplayNumber() {
        if (Flags.cleanupCdma()) {
            return -1;
        }
        return getCdmaEriIconIndex(getSubId());
    }

    @Deprecated
    public int getCdmaEriIconIndex(int i) {
        if (Flags.cleanupCdma()) {
            return -1;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return -1;
            }
            return iTelephony.getCdmaEriIconIndexForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return -1;
        }
    }

    @Deprecated
    public int getCdmaEriIconMode(int i) {
        if (Flags.cleanupCdma()) {
            return -1;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return -1;
            }
            return iTelephony.getCdmaEriIconModeForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return -1;
        }
    }

    @Deprecated
    public String getCdmaEriText() {
        if (Flags.cleanupCdma()) {
            return null;
        }
        return getCdmaEriText(getSubId());
    }

    @Deprecated
    public String getCdmaEriText(int i) {
        if (Flags.cleanupCdma()) {
            return null;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getCdmaEriTextForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    private boolean hasCapability(String str, int i) {
        Context context = this.mContext;
        if (context == null || context.getPackageManager().hasSystemFeature(str)) {
            return true;
        }
        if (SystemProperties.getInt("ro.vendor.api_level", Build.VERSION.DEVICE_INITIAL_SDK_INT) < 202404) {
            return this.mContext.getResources().getBoolean(i);
        }
        return false;
    }

    @Deprecated
    public boolean isVoiceCapable() {
        Context context = this.mContext;
        if (context == null) {
            return true;
        }
        return context.getResources().getBoolean(R.bool.config_voice_capable);
    }

    public boolean isDeviceVoiceCapable() {
        return hasCapability(PackageManager.FEATURE_TELEPHONY_CALLING, R.bool.config_voice_capable);
    }

    @Deprecated
    public boolean isSmsCapable() {
        boolean z;
        ActivityManager activityManager;
        ComponentName componentName;
        String simOperator;
        if (TelephonyFeatures.IS_TABLET) {
            if ("XAA".equals(SystemProperties.get("ro.boot.carrierid", "")) || "N14".equals(SystemProperties.get("ro.boot.carrierid", "")) || "VZW".equals(TelephonyFeatures.getSalesCode()) || "USC".equals(TelephonyFeatures.getSalesCode()) || "ATT".equals(TelephonyFeatures.getSalesCode()) || "CHA".equals(TelephonyFeatures.getSalesCode()) || "CCT".equals(TelephonyFeatures.getSalesCode()) || "DSA".equals(TelephonyFeatures.getSalesCode()) || "DSG".equals(TelephonyFeatures.getSalesCode()) || "DSH".equals(TelephonyFeatures.getSalesCode()) || "TFN".equals(TelephonyFeatures.getSalesCode())) {
                z = true;
            } else if (TelephonyFeatures.isSubOperatorSpecific(getPhoneId(), "VZW", "USC", "ATT", "CHA", "CCT", "DSA", "DSG", "DSH", "TFN") && (simOperator = getSimOperator()) != null && simOperator.length() > 4) {
                z = Arrays.asList("310", "311", "312", "313", "314", "315", "316").contains(simOperator.substring(0, 3));
            }
            if (!z && this.mContext != null) {
                if ("VZW".equals(TelephonyFeatures.getSalesCode()) && "SM-X117U".equalsIgnoreCase(SystemProperties.get("ro.product.model", ""))) {
                    com.android.telephony.Rlog.d(TAG, "SMS support Tablet Model");
                    return true;
                }
                com.android.telephony.Rlog.d(TAG, "US No SMS Tablet Model");
                if ("ATT".equals(TelephonyFeatures.getSubOperatorName(getPhoneId())) && (activityManager = (ActivityManager) this.mContext.getSystemService("activity")) != null && ActivityManager.isSystemReady()) {
                    List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
                    if (runningTasks.isEmpty() || (componentName = runningTasks.get(0).topActivity) == null) {
                        return false;
                    }
                    com.android.telephony.Rlog.d(TAG, "getTopPackageName = " + componentName.getPackageName());
                    if ("com.android.vending".equals(componentName.getPackageName())) {
                        return true;
                    }
                }
                return false;
            }
            return hasCapability(PackageManager.FEATURE_TELEPHONY_MESSAGING, R.bool.config_sms_capable);
        }
        z = false;
        if (!z) {
        }
        return hasCapability(PackageManager.FEATURE_TELEPHONY_MESSAGING, R.bool.config_sms_capable);
    }

    public boolean setDefaultSmsApplicationByForce(String str) {
        com.android.telephony.Rlog.i(TAG, "setDefaultSmsApplicationByForce is called. package = " + str);
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return false;
            }
            return semSubscriberInfoService.setDefaultSmsApplicationByForce(str);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setDefaultSmsApplicationByForce is fail due to RemoteException. " + e);
            return false;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setDefaultSmsApplicationByForce is fail due to NullPointerException. " + e2);
            return false;
        }
    }

    public boolean isDeviceSmsCapable() {
        return isSmsCapable();
    }

    public List<CellInfo> getAllCellInfo() {
        logWithCallerInfo("getAllCellInfo");
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getAllCellInfo(getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public List<CellInfo> getAllCellInfoBySubId(int i) {
        logWithCallerInfo("getAllCellInfoBySubId");
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony == null) {
                com.android.telephony.Rlog.d(TAG, "getAllCellInfoBySubId returning null because SemTelephonyService is null");
                return null;
            }
            return iSemTelephony.getAllCellInfoBySubId(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "getAllCellInfoBySubId is fail due to RemoteException. " + e);
            return null;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.d(TAG, "getAllCellInfoBySubId is fail due to NullPointerException. " + e2);
            return null;
        }
    }

    public List<CellInfo> getAllCellInfoForPhone(int i) {
        logWithCallerInfo("getAllCellInfoForPhone");
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony == null) {
                com.android.telephony.Rlog.d(TAG, "getAllCellInfoForPhone returning null because SemTelephonyService is null");
                return null;
            }
            return iSemTelephony.getAllCellInfoForPhone(i, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "getAllCellInfoForPhone is fail due to RemoteException. " + e);
            return null;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.d(TAG, "getAllCellInfoForPhone is fail due to NullPointerException. " + e2);
            return null;
        }
    }

    public static abstract class CellInfoCallback {
        public static final int ERROR_MODEM_ERROR = 2;
        public static final int ERROR_TIMEOUT = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CellInfoCallbackError {
        }

        public abstract void onCellInfo(List<CellInfo> list);

        public void onError(int i, Throwable th) {
            onCellInfo(new ArrayList());
        }
    }

    public void requestCellInfoUpdate(final Executor executor, final CellInfoCallback cellInfoCallback) {
        logWithCallerInfo("requestCellInfoUpdate");
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Telephony is null");
                }
            } else {
                iTelephony.requestCellInfoUpdate(getSubId(), new AnonymousClass4(this, executor, cellInfoCallback), getOpPackageName(), getAttributionTag());
            }
        } catch (RemoteException e) {
            runOnBackgroundThread(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda25
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

        AnonymousClass4(TelephonyManager telephonyManager, Executor executor, CellInfoCallback cellInfoCallback) {
            this.val$executor = executor;
            this.val$callback = cellInfoCallback;
        }

        @Override // android.telephony.ICellInfoCallback
        public void onCellInfo(final List<CellInfo> list) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$4$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyManager.CellInfoCallback.this.onCellInfo(list);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ICellInfoCallback
        public void onError(final int i, final String str, final String str2) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyManager.CellInfoCallback.this.onError(i, TelephonyManager.createThrowableByClassName(str, str2));
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @SystemApi
    public void requestCellInfoUpdate(WorkSource workSource, final Executor executor, final CellInfoCallback cellInfoCallback) {
        logWithCallerInfo("requestCellInfoUpdate");
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Telephony is null");
                }
            } else {
                iTelephony.requestCellInfoUpdateWithWorkSource(getSubId(), new AnonymousClass5(this, executor, cellInfoCallback), getOpPackageName(), getAttributionTag(), workSource);
            }
        } catch (RemoteException e) {
            runOnBackgroundThread(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda24
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

        AnonymousClass5(TelephonyManager telephonyManager, Executor executor, CellInfoCallback cellInfoCallback) {
            this.val$executor = executor;
            this.val$callback = cellInfoCallback;
        }

        @Override // android.telephony.ICellInfoCallback
        public void onCellInfo(final List<CellInfo> list) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyManager.CellInfoCallback.this.onCellInfo(list);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ICellInfoCallback
        public void onError(final int i, final String str, final String str2) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$5$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyManager.CellInfoCallback.this.onError(i, TelephonyManager.createThrowableByClassName(str, str2));
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Throwable createThrowableByClassName(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            return (Throwable) Class.forName(str).getConstructor(String.class).newInstance(str2);
        } catch (ClassCastException | ReflectiveOperationException unused) {
            return new RuntimeException(str + ": " + str2);
        }
    }

    public void setCellInfoListRate(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCellInfoListRate(i, i2);
            }
        } catch (RemoteException | NullPointerException unused) {
        }
    }

    public String getMmsUserAgent() {
        String str = "SAMSUNG-ANDROID-MMS/" + Build.MODEL;
        SemCarrierFeature semCarrierFeature = SemCarrierFeature.getInstance();
        int phoneId = SubscriptionManager.getPhoneId(getSubId());
        String upperCase = SmsManager.getSmsManagerForContextAndSubscriptionId(this.mContext, getSubId()).getMnoName().toUpperCase();
        String string = upperCase.contains("GENERICIR92_US") ? Settings.System.getString(this.mContext.getContentResolver(), Settings.System.MMS_USER_AGENT) : null;
        String str2 = "";
        if (TextUtils.isEmpty(string)) {
            string = semCarrierFeature.getString(getPhoneId(), "CarrierFeature_Message_UserAgent", "", true);
        }
        if (TextUtils.isEmpty(string)) {
            string = getValuefromCSC("MessageUserAgent", getSimOperator(), getGroupIdLevel1(), phoneId);
            com.android.telephony.Rlog.i(TAG, "Customer.xml userAgent:" + string);
        }
        String str3 = Build.MODEL;
        if ("ggsm-tmo-ua".equals(semCarrierFeature.getString(getPhoneId(), "CarrierFeature_Message_EnableMmsUaUapAutoCreate", "", true))) {
            StringBuffer stringBuffer = new StringBuffer();
            if (str3.length() > 0) {
                stringBuffer.append(str3);
            }
            stringBuffer.append('-');
            String str4 = Build.VERSION.INCREMENTAL;
            if (str4.length() > 0) {
                stringBuffer.append(str4);
            }
            string = String.format("SAMSUNG-ANDROID-MMS/%s", stringBuffer);
        }
        if (!TextUtils.isEmpty(string)) {
            str = string;
        }
        String str5 = SystemProperties.get("ro.build.version.incremental");
        if (upperCase.contains("ATT_US")) {
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                if (SmsApplication.isDefaultSmsApplication(this.mContext, "com.google.android.apps.messaging")) {
                    str2 = packageManager.getPackageInfo("com.google.android.apps.messaging", 128).versionName;
                }
            } catch (PackageManager.NameNotFoundException unused) {
                com.android.telephony.Rlog.e(TAG, "com.google.android.apps.messaging package NameNotFoundException");
            }
            if (!str3.contains("SAMSUNG")) {
                str3 = "SAMSUNG-" + Build.MODEL;
            }
            if (TextUtils.isEmpty(str2)) {
                str = str3 + "/" + str5 + " Mozilla/5.0 SMM-MMS/1.2.0";
            } else {
                str = str3 + "/" + str5 + "/" + str2 + " Mozilla/5.0 SMM-MMS/1.2.0";
            }
        }
        if (str != null) {
            return str;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getMmsUserAgent(getSubId());
            }
        } catch (RemoteException | NullPointerException unused2) {
        }
        return null;
    }

    public String getMmsUAProfUrl() {
        SemCarrierFeature semCarrierFeature = SemCarrierFeature.getInstance();
        int phoneId = SubscriptionManager.getPhoneId(getSubId());
        String upperCase = SmsManager.getSmsManagerForContextAndSubscriptionId(this.mContext, getSubId()).getMnoName().toUpperCase();
        com.android.telephony.Rlog.i(TAG, "Mno = " + upperCase + " phoneId: " + phoneId);
        String string = upperCase.contains("GENERICIR92_US") ? Settings.System.getString(this.mContext.getContentResolver(), Settings.System.MMS_X_WAP_PROFILE_URL) : null;
        if (TextUtils.isEmpty(string)) {
            string = semCarrierFeature.getString(getPhoneId(), "CarrierFeature_Message_UaProfUrl", "", true);
            com.android.telephony.Rlog.i(TAG, "carrierFeature uapUrl:" + string);
        }
        if (TextUtils.isEmpty(string)) {
            string = getValuefromCSC("MessageUaProfUrl", getSimOperator(), getGroupIdLevel1(), phoneId);
            com.android.telephony.Rlog.i(TAG, "Customer.xml uapUrl:" + string);
        }
        if (TextUtils.isEmpty(string)) {
            string = "http://wap.samsungmobile.com/uaprof/SAMSUNGUAPROF.xml";
        }
        if (string != null) {
            com.android.telephony.Rlog.i("MMS_UA" + phoneId, "sUaProfUrl:" + string);
            return string;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getMmsUAProfUrl(getSubId());
            }
        } catch (RemoteException | NullPointerException unused) {
        }
        return null;
    }

    private int getFirstActivePortIndex(int i) {
        UiccSlotInfo uiccSlotInfo;
        UiccSlotInfo[] uiccSlotsInfo = getUiccSlotsInfo();
        if (uiccSlotsInfo == null || i < 0 || i >= uiccSlotsInfo.length || (uiccSlotInfo = uiccSlotsInfo[i]) == null) {
            return -1;
        }
        Optional<UiccPortInfo> findFirst = uiccSlotInfo.getPorts().stream().filter(new Predicate() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean isActive;
                isActive = ((UiccPortInfo) obj).isActive();
                return isActive;
            }
        }).findFirst();
        if (findFirst.isPresent()) {
            return findFirst.get().getPortIndex();
        }
        return -1;
    }

    @Deprecated
    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(String str) {
        return iccOpenLogicalChannel(getSubId(), str, -1);
    }

    @SystemApi
    @Deprecated
    public IccOpenLogicalChannelResponse iccOpenLogicalChannelBySlot(int i, String str, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            IccLogicalChannelRequest iccLogicalChannelRequest = new IccLogicalChannelRequest();
            iccLogicalChannelRequest.slotIndex = i;
            iccLogicalChannelRequest.portIndex = getFirstActivePortIndex(i);
            iccLogicalChannelRequest.aid = str;
            iccLogicalChannelRequest.p2 = i2;
            iccLogicalChannelRequest.callingPackage = getOpPackageName();
            iccLogicalChannelRequest.binder = new Binder();
            return iTelephony.iccOpenLogicalChannel(iccLogicalChannelRequest);
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    public IccOpenLogicalChannelResponse iccOpenLogicalChannelByPort(int i, int i2, String str, int i3) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                IccLogicalChannelRequest iccLogicalChannelRequest = new IccLogicalChannelRequest();
                iccLogicalChannelRequest.slotIndex = i;
                iccLogicalChannelRequest.portIndex = i2;
                iccLogicalChannelRequest.aid = str;
                iccLogicalChannelRequest.p2 = i3;
                iccLogicalChannelRequest.callingPackage = getOpPackageName();
                iccLogicalChannelRequest.binder = new Binder();
                return iTelephony.iccOpenLogicalChannel(iccLogicalChannelRequest);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(String str, int i) {
        return iccOpenLogicalChannel(getSubId(), str, i);
    }

    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(int i, String str, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            IccLogicalChannelRequest iccLogicalChannelRequest = new IccLogicalChannelRequest();
            iccLogicalChannelRequest.subId = i;
            iccLogicalChannelRequest.callingPackage = getOpPackageName();
            iccLogicalChannelRequest.aid = str;
            iccLogicalChannelRequest.p2 = i2;
            iccLogicalChannelRequest.binder = new Binder();
            return iTelephony.iccOpenLogicalChannel(iccLogicalChannelRequest);
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    @Deprecated
    public boolean iccCloseLogicalChannelBySlot(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            IccLogicalChannelRequest iccLogicalChannelRequest = new IccLogicalChannelRequest();
            iccLogicalChannelRequest.slotIndex = i;
            iccLogicalChannelRequest.portIndex = getFirstActivePortIndex(i);
            iccLogicalChannelRequest.channel = i2;
            return iTelephony.iccCloseLogicalChannel(iccLogicalChannelRequest);
        } catch (RemoteException | NullPointerException unused) {
            return false;
        } catch (IllegalStateException e) {
            com.android.telephony.Rlog.e(TAG, "iccCloseLogicalChannel IllegalStateException", e);
            return false;
        }
    }

    @SystemApi
    public void iccCloseLogicalChannelByPort(int i, int i2, int i3) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                IccLogicalChannelRequest iccLogicalChannelRequest = new IccLogicalChannelRequest();
                iccLogicalChannelRequest.slotIndex = i;
                iccLogicalChannelRequest.portIndex = i2;
                iccLogicalChannelRequest.channel = i3;
                iTelephony.iccCloseLogicalChannel(iccLogicalChannelRequest);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean iccCloseLogicalChannel(int i) {
        try {
            return iccCloseLogicalChannel(getSubId(), i);
        } catch (IllegalStateException e) {
            com.android.telephony.Rlog.e(TAG, "iccCloseLogicalChannel IllegalStateException", e);
            return false;
        }
    }

    public boolean iccCloseLogicalChannel(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            IccLogicalChannelRequest iccLogicalChannelRequest = new IccLogicalChannelRequest();
            iccLogicalChannelRequest.subId = i;
            iccLogicalChannelRequest.channel = i2;
            return iTelephony.iccCloseLogicalChannel(iccLogicalChannelRequest);
        } catch (RemoteException | NullPointerException unused) {
            return false;
        } catch (IllegalStateException e) {
            com.android.telephony.Rlog.e(TAG, "iccCloseLogicalChannel IllegalStateException", e);
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public String iccTransmitApduLogicalChannelBySlot(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduLogicalChannelByPort(i, getFirstActivePortIndex(i), i2, i3, i4, i5, i6, i7, str);
            }
            return null;
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    public String iccTransmitApduLogicalChannelByPort(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduLogicalChannelByPort(i, i2, i3, i4, i5, i6, i7, i8, str);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, String str) {
        return iccTransmitApduLogicalChannel(getSubId(), i, i2, i3, i4, i5, i6, str);
    }

    public String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduLogicalChannel(i, i2, i3, i4, i5, i6, i7, str);
            }
            return "";
        } catch (RemoteException | NullPointerException unused) {
            return "";
        }
    }

    @SystemApi
    @Deprecated
    public String iccTransmitApduBasicChannelBySlot(int i, int i2, int i3, int i4, int i5, int i6, String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduBasicChannelByPort(i, getFirstActivePortIndex(i), getOpPackageName(), i2, i3, i4, i5, i6, str);
            }
            return null;
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    public String iccTransmitApduBasicChannelByPort(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduBasicChannelByPort(i, i2, getOpPackageName(), i3, i4, i5, i6, i7, str);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public String iccTransmitApduBasicChannel(int i, int i2, int i3, int i4, int i5, String str) {
        return iccTransmitApduBasicChannel(getSubId(), i, i2, i3, i4, i5, str);
    }

    public String iccTransmitApduBasicChannel(int i, int i2, int i3, int i4, int i5, int i6, String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduBasicChannel(i, getOpPackageName(), i2, i3, i4, i5, i6, str);
            }
            return "";
        } catch (RemoteException | NullPointerException unused) {
            return "";
        }
    }

    public byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, String str) {
        return iccExchangeSimIO(getSubId(), i, i2, i3, i4, i5, str);
    }

    public byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, int i6, String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccExchangeSimIO(i, i2, i3, i4, i5, i6, str);
            }
            return null;
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public String sendEnvelopeWithStatus(String str) {
        return sendEnvelopeWithStatus(getSubId(), str);
    }

    public String sendEnvelopeWithStatus(int i, String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.sendEnvelopeWithStatus(i, str);
            }
            return "";
        } catch (RemoteException | NullPointerException unused) {
            return "";
        }
    }

    @Deprecated
    public String nvReadItem(int i) {
        if (Flags.cleanupCdma()) {
            return "";
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.nvReadItem(i);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "nvReadItem RemoteException", e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "nvReadItem NPE", e2);
        }
        return "";
    }

    @Deprecated
    public boolean nvWriteItem(int i, String str) {
        if (Flags.cleanupCdma()) {
            return false;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.nvWriteItem(i, str);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "nvWriteItem RemoteException", e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "nvWriteItem NPE", e2);
        }
        return false;
    }

    @Deprecated
    public boolean nvWriteCdmaPrl(byte[] bArr) {
        if (Flags.cleanupCdma()) {
            return false;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.nvWriteCdmaPrl(bArr);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "nvWriteCdmaPrl RemoteException", e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "nvWriteCdmaPrl NPE", e2);
        }
        return false;
    }

    @Deprecated
    public boolean nvResetConfig(int i) {
        if (Flags.cleanupCdma() && i != 1) {
            return false;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (i == 1) {
                    return iTelephony.rebootModem(getSlotIndex());
                }
                if (i != 3) {
                    com.android.telephony.Rlog.e(TAG, "nvResetConfig unsupported reset type");
                } else {
                    return iTelephony.resetModemConfig(getSlotIndex());
                }
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "nvResetConfig RemoteException", e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "nvResetConfig NPE", e2);
        }
        return false;
    }

    @SystemApi
    @Deprecated
    public boolean resetRadioConfig() {
        if (Flags.cleanupCdma()) {
            return false;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.resetModemConfig(getSlotIndex());
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "resetRadioConfig RemoteException", e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "resetRadioConfig NPE", e2);
        }
        return false;
    }

    @SystemApi
    @Deprecated
    public boolean rebootRadio() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.rebootModem(getSlotIndex());
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "rebootRadio RemoteException", e);
            return false;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "rebootRadio NPE", e2);
            return false;
        }
    }

    public void rebootModem() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            if (!iTelephony.rebootModem(getSlotIndex())) {
                throw new RuntimeException("Couldn't reboot modem (it may be not supported)");
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "rebootRadio RemoteException", e);
            throw e.rethrowAsRuntimeException();
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

    private int getSubId(int i) {
        return SubscriptionManager.isUsableSubIdValue(this.mSubId) ? this.mSubId : i;
    }

    private int getPhoneId() {
        return SubscriptionManager.getPhoneId(getSubId());
    }

    private int getPhoneId(int i) {
        return SubscriptionManager.getPhoneId(getSubId(i));
    }

    public int getSlotIndex() {
        int slotIndex = SubscriptionManager.getSlotIndex(getSubId());
        if (slotIndex == -1) {
            return Integer.MAX_VALUE;
        }
        return slotIndex;
    }

    /* renamed from: android.telephony.TelephonyManager$6, reason: invalid class name */
    class AnonymousClass6 extends INumberVerificationCallback.Stub {
        final /* synthetic */ NumberVerificationCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass6(TelephonyManager telephonyManager, Executor executor, NumberVerificationCallback numberVerificationCallback) {
            this.val$executor = executor;
            this.val$callback = numberVerificationCallback;
        }

        @Override // com.android.internal.telephony.INumberVerificationCallback
        public void onCallReceived(final String str) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final NumberVerificationCallback numberVerificationCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$6$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NumberVerificationCallback.this.onCallReceived(str);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.internal.telephony.INumberVerificationCallback
        public void onVerificationFailed(final int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final NumberVerificationCallback numberVerificationCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$6$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        NumberVerificationCallback.this.onVerificationFailed(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @SystemApi
    public void requestNumberVerification(PhoneNumberRange phoneNumberRange, long j, final Executor executor, final NumberVerificationCallback numberVerificationCallback) {
        if (executor == null) {
            throw new NullPointerException("Executor must be non-null");
        }
        if (numberVerificationCallback == null) {
            throw new NullPointerException("Callback must be non-null");
        }
        AnonymousClass6 anonymousClass6 = new AnonymousClass6(this, executor, numberVerificationCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Telephony is null");
                }
            } else {
                iTelephony.requestNumberVerification(phoneNumberRange, j, anonymousClass6, getOpPackageName());
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "requestNumberVerification RemoteException", e);
            runOnBackgroundThread(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            NumberVerificationCallback.this.onVerificationFailed(0);
                        }
                    });
                }
            });
        }
    }

    private static <T> List<T> updateTelephonyProperty(List<T> list, int i, T t) {
        ArrayList arrayList = new ArrayList(list);
        while (arrayList.size() <= i) {
            arrayList.add(null);
        }
        arrayList.set(i, t);
        return arrayList;
    }

    public static int getIntAtIndex(ContentResolver contentResolver, String str, int i) throws Settings.SettingNotFoundException {
        String str2;
        String string = Settings.Global.getString(contentResolver, str);
        if (string != null) {
            String[] split = string.split(",");
            if (i >= 0 && i < split.length && (str2 = split[i]) != null) {
                try {
                    return Integer.parseInt(str2);
                } catch (NumberFormatException unused) {
                }
            }
        }
        throw new Settings.SettingNotFoundException(str);
    }

    public static boolean putIntAtIndex(ContentResolver contentResolver, String str, int i, int i2) {
        String str2;
        String string = Settings.Global.getString(contentResolver, str);
        if (i == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("putIntAtIndex index == MAX_VALUE index=" + i);
        }
        if (i < 0) {
            throw new IllegalArgumentException("putIntAtIndex index < 0 index=" + i);
        }
        String[] split = string != null ? string.split(",") : null;
        String str3 = "";
        for (int i3 = 0; i3 < i; i3++) {
            if (split == null || i3 >= split.length) {
                str2 = "";
            } else {
                str2 = split[i3];
            }
            str3 = str3 + str2 + ",";
        }
        String str4 = str3 + i2;
        if (split != null) {
            while (true) {
                i++;
                if (i >= split.length) {
                    break;
                }
                str4 = str4 + "," + split[i];
            }
        }
        return Settings.Global.putString(contentResolver, str, str4);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0019, code lost:
    
        if (r1 != null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getTelephonyProperty(int r1, java.lang.String r2, java.lang.String r3) {
        /*
            java.lang.String r2 = android.os.SystemProperties.get(r2)
            if (r2 == 0) goto L1c
            int r0 = r2.length()
            if (r0 <= 0) goto L1c
            java.lang.String r0 = ","
            java.lang.String[] r2 = r2.split(r0)
            if (r1 < 0) goto L1c
            int r0 = r2.length
            if (r1 >= r0) goto L1c
            r1 = r2[r1]
            if (r1 == 0) goto L1c
            goto L1d
        L1c:
            r1 = 0
        L1d:
            if (r1 != 0) goto L20
            return r3
        L20:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.TelephonyManager.getTelephonyProperty(int, java.lang.String, java.lang.String):java.lang.String");
    }

    public static String semGetTelephonyProperty(int i, String str, String str2) {
        String korDomesticPropForDS = SemTelephonyUtils.getKorDomesticPropForDS(str, i);
        if (korDomesticPropForDS != null) {
            return getTelephonyProperty(korDomesticPropForDS, str2);
        }
        return getTelephonyProperty(i, str, str2);
    }

    private static <T> T getTelephonyProperty(int i, List<T> list, T t) {
        T t2 = (i < 0 || i >= list.size()) ? null : list.get(i);
        return t2 != null ? t2 : t;
    }

    public static String getTelephonyProperty(String str, String str2) {
        String str3 = SystemProperties.get(str);
        return TextUtils.isEmpty(str3) ? str2 : str3;
    }

    public int getSimCount() {
        return getPhoneCount();
    }

    @SystemApi
    public String getIsimIst() {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIsimIst(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @Deprecated
    public String[] getIsimPcscf() {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIsimPcscf(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    public List<String> getImsPcscfAddresses() {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                throw new RuntimeException("P-CSCF error: Subscriber Info is null");
            }
            return subscriberInfoService.getImsPcscfAddresses(getSubId(), getOpPackageName());
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getImsPcscfAddresses Exception = " + e);
            e.rethrowAsRuntimeException();
            return Collections.EMPTY_LIST;
        } catch (IllegalArgumentException | NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getImsPcscfAddresses Exception = " + e2);
            return Collections.EMPTY_LIST;
        }
    }

    public String getIccAuthentication(int i, int i2, String str) {
        return getIccAuthentication(getSubId(), i, i2, str);
    }

    public String getIccAuthentication(int i, int i2, int i3, String str) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIccSimChallengeResponse(i, i2, i3, str, getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public String[] getForbiddenPlmns() {
        return getForbiddenPlmns(getSubId(), 2);
    }

    public String[] getForbiddenPlmns(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getForbiddenPlmns(i, i2, this.mContext.getOpPackageName(), getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public int setForbiddenPlmns(List<String> list) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return -1;
            }
            return iTelephony.setForbiddenPlmns(getSubId(), 2, list, getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setForbiddenPlmns RemoteException: " + e.getMessage());
            return -1;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setForbiddenPlmns NullPointerException: " + e2.getMessage());
            return -1;
        }
    }

    @Deprecated
    public String getSimServiceTable(int i) {
        try {
            IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                com.android.telephony.Rlog.e(TAG, "getSimServiceTable(): IPhoneSubInfo is null");
                return null;
            }
            if (i == 5) {
                return subscriberInfoService.getIsimIst(getSubId());
            }
            if (i == 2) {
                return subscriberInfoService.getSimServiceTable(getSubId(), 2);
            }
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getSimServiceTable(): RemoteException=" + e.getMessage());
            return null;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getSimServiceTable(): NullPointerException=" + e2.getMessage());
            return null;
        }
    }

    @SystemApi
    public void getSimServiceTable(int i, Executor executor, final OutcomeReceiver<byte[], Exception> outcomeReceiver) {
        String simServiceTable;
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
        if (subscriberInfoService == null) {
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    OutcomeReceiver.this.onError(new RuntimeException("getSimServiceTable: Subscriber Info is null"));
                }
            });
            return;
        }
        try {
            if (i == 5) {
                simServiceTable = subscriberInfoService.getIsimIst(getSubId());
            } else {
                simServiceTable = i == 2 ? subscriberInfoService.getSimServiceTable(getSubId(), 2) : null;
            }
            if (simServiceTable == null) {
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda27
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onResult(new byte[0]);
                    }
                });
            } else {
                final byte[] hexStringToBytes = IccUtils.hexStringToBytes(simServiceTable);
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda28
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onResult(hexStringToBytes);
                    }
                });
            }
        } catch (Exception e) {
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    OutcomeReceiver.this.onError(e);
                }
            });
        }
    }

    @SystemApi
    public void resetIms(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.resetIms(i);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "toggleImsOnOff, RemoteException: " + e.getMessage());
        }
    }

    public void enableIms(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.enableIms(i);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "enableIms, RemoteException: " + e.getMessage());
        }
    }

    public void disableIms(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.disableIms(i);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "disableIms, RemoteException: " + e.getMessage());
        }
    }

    public IImsRegistration getImsRegistration(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getImsRegistration(i, i2);
            }
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getImsRegistration, RemoteException: " + e.getMessage());
            return null;
        }
    }

    public IImsConfig getImsConfig(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getImsConfig(i, i2);
            }
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getImsRegistration, RemoteException: " + e.getMessage());
            return null;
        }
    }

    public void setImsRegistrationState(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setImsRegistrationState(z);
            }
        } catch (RemoteException unused) {
        }
    }

    @Deprecated
    public int getPreferredNetworkType(int i) {
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
            if (getITelephony() != null) {
                return r0.getAllowedNetworkTypesBitmask(getSubId());
            }
            return 0L;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getAllowedNetworkTypesBitmask RemoteException", e);
            return 0L;
        }
    }

    @SystemApi
    @Deprecated
    public long getAllowedNetworkTypes() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getAllowedNetworkTypesForReason(getSubId(), 2);
            }
            return -1L;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getAllowedNetworkTypes RemoteException", e);
            return -1L;
        }
    }

    public void setNetworkSelectionModeAutomatic() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setNetworkSelectionModeAutomatic(getSubId());
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setNetworkSelectionModeAutomatic RemoteException", e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setNetworkSelectionModeAutomatic NPE", e2);
        }
    }

    public CellNetworkScanResult getAvailableNetworks() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCellNetworkScanResults(getSubId(), getOpPackageName(), getAttributionTag());
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getAvailableNetworks RemoteException", e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getAvailableNetworks NPE", e2);
        }
        return new CellNetworkScanResult(4, (List<OperatorInfo>) null);
    }

    public NetworkScan requestNetworkScan(NetworkScanRequest networkScanRequest, Executor executor, TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        return requestNetworkScan(2, networkScanRequest, executor, networkScanCallback);
    }

    public NetworkScan requestNetworkScan(int i, NetworkScanRequest networkScanRequest, Executor executor, TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        synchronized (sCacheLock) {
            if (this.mTelephonyScanManager == null) {
                this.mTelephonyScanManager = new TelephonyScanManager();
            }
        }
        return this.mTelephonyScanManager.requestNetworkScan(getSubId(), i != 2, networkScanRequest, executor, networkScanCallback, getOpPackageName(), getAttributionTag());
    }

    @Deprecated
    public NetworkScan requestNetworkScan(NetworkScanRequest networkScanRequest, TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        return requestNetworkScan(networkScanRequest, AsyncTask.SERIAL_EXECUTOR, networkScanCallback);
    }

    public boolean setNetworkSelectionModeManual(String str, boolean z) {
        return setNetworkSelectionModeManual(new OperatorInfo("", "", str), z);
    }

    public boolean setNetworkSelectionModeManual(String str, boolean z, int i) {
        return setNetworkSelectionModeManual(new OperatorInfo("", "", str, i), z);
    }

    public boolean setNetworkSelectionModeManual(OperatorInfo operatorInfo, boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setNetworkSelectionModeManual(getSubId(), operatorInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setNetworkSelectionModeManual RemoteException", e);
            return false;
        }
    }

    public int getNetworkSelectionMode() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getNetworkSelectionMode(getSubId());
            }
            return 0;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getNetworkSelectionMode RemoteException", e);
            return 0;
        }
    }

    public String getManualNetworkSelectionPlmn() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null && isManualNetworkSelectionAllowed()) {
                return iTelephony.getManualNetworkSelectionPlmn(getSubId());
            }
            return "";
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getManualNetworkSelectionPlmn RemoteException", e);
            return "";
        }
    }

    @SystemApi
    public boolean isInEmergencySmsMode() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isInEmergencySmsMode();
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isInEmergencySmsMode RemoteException", e);
            return false;
        }
    }

    @Deprecated
    public boolean setPreferredNetworkType(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setAllowedNetworkTypesForReason(i, 0, RadioAccessFamily.getRafFromNetworkType(i2));
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setPreferredNetworkType RemoteException", e);
        }
        return false;
    }

    @SystemApi
    @Deprecated
    public boolean setPreferredNetworkTypeBitmask(long j) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setAllowedNetworkTypesForReason(getSubId(), 0, checkNetworkTypeBitmask(j));
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setPreferredNetworkTypeBitmask RemoteException", e);
        }
        return false;
    }

    @SystemApi
    @Deprecated
    public boolean setAllowedNetworkTypes(long j) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            return iTelephony.setAllowedNetworkTypesForReason(getSubId(), 2, checkNetworkTypeBitmask(j));
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setAllowedNetworkTypes RemoteException", e);
            return false;
        }
    }

    public void setAllowedNetworkTypesForReason(int i, long j) {
        if (!isValidAllowedNetworkTypesReason(i)) {
            throw new IllegalArgumentException("invalid AllowedNetworkTypesReason.");
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setAllowedNetworkTypesForReason(getSubId(), i, checkNetworkTypeBitmask(j));
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setAllowedNetworkTypesForReason RemoteException", e);
            e.rethrowFromSystemServer();
        }
    }

    public long getAllowedNetworkTypesForReason(int i) {
        if (!isValidAllowedNetworkTypesReason(i)) {
            throw new IllegalArgumentException("invalid AllowedNetworkTypesReason.");
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getAllowedNetworkTypesForReason(getSubId(), i);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getAllowedNetworkTypesForReason RemoteException", e);
            e.rethrowFromSystemServer();
            return -1L;
        }
    }

    public static boolean isValidAllowedNetworkTypesReason(int i) {
        if (i == 0 || i == 1 || i == 2 || i == 3) {
            return true;
        }
        if (i != 4) {
            return false;
        }
        return TelephonyUtils.IS_DEBUGGABLE;
    }

    public static String convertNetworkTypeBitmaskToString(final long j) {
        String str = (String) IntStream.rangeClosed(1, 20).filter(new IntPredicate() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda4
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return TelephonyManager.lambda$convertNetworkTypeBitmaskToString$15(j, i);
            }
        }).mapToObj(new IntFunction() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda5
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                String networkTypeName;
                networkTypeName = TelephonyManager.getNetworkTypeName(i);
                return networkTypeName;
            }
        }).collect(Collectors.joining(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER));
        return TextUtils.isEmpty(str) ? "UNKNOWN" : str;
    }

    static /* synthetic */ boolean lambda$convertNetworkTypeBitmaskToString$15(long j, int i) {
        return (j & getBitMaskForNetworkType(i)) == getBitMaskForNetworkType(i);
    }

    public boolean setPreferredNetworkTypeToGlobal() {
        return setPreferredNetworkTypeToGlobal(getSubId());
    }

    public boolean setPreferredNetworkTypeToGlobal(int i) {
        boolean z = false;
        int supportedRat = getSupportedRat(i != -1 ? SubscriptionManager.getPhoneId(i) : 0);
        if (supportedRat != -1) {
            boolean z2 = (supportedRat & 16) == 16;
            boolean z3 = (supportedRat & 64) == 64;
            if (TelephonyFeatures.IS_EXYNOS && z2) {
                com.android.telephony.Rlog.d(TAG, "setPreferredNetworkTypeToGlobal - ignore supportTdscdma");
            } else {
                z = z2;
            }
            int i2 = (z && z3) ? 33 : z ? 22 : !z3 ? 10 : -1;
            if (i2 != -1) {
                com.android.telephony.Rlog.d(TAG, "setPreferredNetworkTypeToGlobal - subId: " + i + ". Use extended global mode network type: " + i2);
                return setPreferredNetworkType(i, i2);
            }
        }
        com.android.telephony.Rlog.d(TAG, "setPreferredNetworkTypeToGlobal - subId: " + i);
        return setPreferredNetworkType(i, 27);
    }

    @SystemApi
    public boolean isTetheringApnRequired() {
        return isTetheringApnRequired(getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
    }

    public boolean isTetheringApnRequired(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isTetheringApnRequiredForSubscriber(i);
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "hasMatchedTetherApnSetting RemoteException", e);
            return false;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "hasMatchedTetherApnSetting NPE", e2);
            return false;
        }
    }

    public boolean hasCarrierPrivileges() {
        return hasCarrierPrivileges(getSubId());
    }

    public boolean hasCarrierPrivileges(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "hasCarrierPrivileges: no Telephony service");
                return false;
            }
            int carrierPrivilegeStatus = iTelephony.getCarrierPrivilegeStatus(i);
            if (carrierPrivilegeStatus == 0) {
                return false;
            }
            if (carrierPrivilegeStatus == 1) {
                return true;
            }
            com.android.telephony.Rlog.e(TAG, "hasCarrierPrivileges: " + carrierPrivilegeStatus);
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "hasCarrierPrivileges RemoteException", e);
            return false;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "hasCarrierPrivileges NPE", e2);
            return false;
        }
    }

    public boolean setOperatorBrandOverride(String str) {
        return setOperatorBrandOverride(getSubId(), str);
    }

    public boolean setOperatorBrandOverride(int i, String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setOperatorBrandOverride(i, str);
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setOperatorBrandOverride RemoteException", e);
            return false;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setOperatorBrandOverride NPE", e2);
            return false;
        }
    }

    public boolean setRoamingOverride(List<String> list, List<String> list2, List<String> list3, List<String> list4) {
        return setRoamingOverride(getSubId(), list, list2, list3, list4);
    }

    public boolean setRoamingOverride(int i, List<String> list, List<String> list2, List<String> list3, List<String> list4) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setRoamingOverride(i, list, list2, list3, list4);
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setRoamingOverride RemoteException", e);
            return false;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setRoamingOverride NPE", e2);
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public String getCdmaMdn() {
        if (Flags.cleanupCdma()) {
            return null;
        }
        return getCdmaMdn(getSubId());
    }

    @SystemApi
    @Deprecated
    public String getCdmaMdn(int i) {
        if (Flags.cleanupCdma()) {
            return null;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getCdmaMdn(i);
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    @Deprecated
    public String getCdmaMin() {
        if (Flags.cleanupCdma()) {
            return null;
        }
        return getCdmaMin(getSubId());
    }

    @SystemApi
    @Deprecated
    public String getCdmaMin(int i) {
        if (Flags.cleanupCdma()) {
            return null;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getCdmaMin(i);
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    @SystemApi
    public int checkCarrierPrivilegesForPackage(String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.checkCarrierPrivilegesForPackage(getSubId(), str);
            }
            return 0;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "checkCarrierPrivilegesForPackage RemoteException", e);
            return 0;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "checkCarrierPrivilegesForPackage NPE", e2);
            return 0;
        }
    }

    @SystemApi
    public int checkCarrierPrivilegesForPackageAnyPhone(String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.checkCarrierPrivilegesForPackageAnyPhone(str);
            }
            return 0;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "checkCarrierPrivilegesForPackageAnyPhone RemoteException", e);
            return 0;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "checkCarrierPrivilegesForPackageAnyPhone NPE", e2);
            return 0;
        }
    }

    public int semCheckCarrierPrivilegesForPackageAnyPhone(String str) {
        return checkCarrierPrivilegesForPackageAnyPhone(str);
    }

    @SystemApi
    public List<String> getCarrierPackageNamesForIntent(Intent intent) {
        return getCarrierPackageNamesForIntentAndPhone(intent, getPhoneId());
    }

    @SystemApi
    public List<String> getCarrierPackageNamesForIntentAndPhone(Intent intent, int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierPackageNamesForIntentAndPhone(intent, i);
            }
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCarrierPackageNamesForIntentAndPhone RemoteException", e);
            return null;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCarrierPackageNamesForIntentAndPhone NPE", e2);
            return null;
        }
    }

    @SystemApi
    public String getCarrierServicePackageName() {
        return getCarrierServicePackageNameForLogicalSlot(getPhoneId());
    }

    @SystemApi
    public String getCarrierServicePackageNameForLogicalSlot(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierServicePackageNameForLogicalSlot(i);
            }
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCarrierServicePackageNameForLogicalSlot RemoteException", e);
            return null;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCarrierServicePackageNameForLogicalSlot NPE", e2);
            return null;
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public Set<String> getPackagesWithCarrierPrivileges() {
        HashSet hashSet = new HashSet();
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                hashSet.addAll(iTelephony.getPackagesWithCarrierPrivileges(getPhoneId()));
                return hashSet;
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getPackagesWithCarrierPrivileges RemoteException", e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getPackagesWithCarrierPrivileges NPE", e2);
        }
        return hashSet;
    }

    @SystemApi
    public List<String> getCarrierPrivilegedPackagesForAllActiveSubscriptions() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getPackagesWithCarrierPrivilegesForAllPhones();
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCarrierPrivilegedPackagesForAllActiveSubscriptions RemoteException", e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCarrierPrivilegedPackagesForAllActiveSubscriptions NPE", e2);
        }
        return Collections.EMPTY_LIST;
    }

    public void setCallComposerStatus(int i) {
        if (com.android.server.telecom.flags.Flags.businessCallComposer()) {
            if (i > 2 || i < 0) {
                throw new IllegalArgumentException("requested status is invalid");
            }
        } else if (i > 1 || i < 0) {
            throw new IllegalArgumentException("requested status is invalid");
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCallComposerStatus(getSubId(), i);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setCallComposerStatus", e);
            e.rethrowFromSystemServer();
        }
    }

    public int getCallComposerStatus() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCallComposerStatus(getSubId());
            }
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getCallComposerStatus", e);
            e.rethrowFromSystemServer();
            return 0;
        }
    }

    @SystemApi
    public void dial(String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.dial(str);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#dial", e);
        }
    }

    public void dial(int i, String str) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                iSemTelephony.dialForSubscriber(i, str);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ISemTelephony#dialForSubscriber", e);
        }
    }

    @SystemApi
    @Deprecated
    public void call(String str, String str2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.call(str, str2);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#call", e);
        }
    }

    @SystemApi
    @Deprecated
    public boolean isOffhook() {
        return ((TelecomManager) this.mContext.getSystemService(Context.TELECOM_SERVICE)).isInCall();
    }

    @SystemApi
    @Deprecated
    public boolean isRinging() {
        return ((TelecomManager) this.mContext.getSystemService(Context.TELECOM_SERVICE)).isRinging();
    }

    @SystemApi
    @Deprecated
    public boolean isIdle() {
        return !((TelecomManager) this.mContext.getSystemService(Context.TELECOM_SERVICE)).isInCall();
    }

    @SystemApi
    @Deprecated
    public boolean isRadioOn() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isRadioOnWithFeature(getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isRadioOn", e);
            return false;
        }
    }

    @SystemApi
    public boolean supplyPin(String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.supplyPinForSubscriber(getSubId(), str);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyPinForSubscriber", e);
            return false;
        }
    }

    @SystemApi
    public boolean supplyPuk(String str, String str2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.supplyPukForSubscriber(getSubId(), str, str2);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyPukForSubscriber", e);
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public int[] supplyPinReportResult(String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.supplyPinReportResultForSubscriber(getSubId(), str);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyPinReportResultForSubscriber", e);
        }
        return new int[0];
    }

    @SystemApi
    @Deprecated
    public int[] supplyPukReportResult(String str, String str2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.supplyPukReportResultForSubscriber(getSubId(), str, str2);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyPukReportResultForSubscriber", e);
        }
        return new int[0];
    }

    @SystemApi
    public PinResult supplyIccLockPin(String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                int[] supplyPinReportResultForSubscriber = iTelephony.supplyPinReportResultForSubscriber(getSubId(), str);
                return new PinResult(supplyPinReportResultForSubscriber[0], supplyPinReportResultForSubscriber[1]);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyIccLockPin", e);
            e.rethrowFromSystemServer();
            return PinResult.getDefaultFailedResult();
        }
    }

    @SystemApi
    public PinResult supplyIccLockPuk(String str, String str2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                int[] supplyPukReportResultForSubscriber = iTelephony.supplyPukReportResultForSubscriber(getSubId(), str, str2);
                return new PinResult(supplyPukReportResultForSubscriber[0], supplyPukReportResultForSubscriber[1]);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#supplyIccLockPuk", e);
            e.rethrowFromSystemServer();
            return PinResult.getDefaultFailedResult();
        }
    }

    public void sendUssdRequest(String str, final UssdResponseCallback ussdResponseCallback, Handler handler) {
        Preconditions.checkNotNull(ussdResponseCallback, "UssdResponseCallback cannot be null.");
        ResultReceiver resultReceiver = new ResultReceiver(this, handler) { // from class: android.telephony.TelephonyManager.7
            @Override // android.os.ResultReceiver
            protected void onReceiveResult(int i, Bundle bundle) {
                com.android.telephony.Rlog.d(TelephonyManager.TAG, "USSD:" + i);
                Preconditions.checkNotNull(bundle, "ussdResponse cannot be null.");
                UssdResponse ussdResponse = (UssdResponse) bundle.getParcelable(TelephonyManager.USSD_RESPONSE, UssdResponse.class);
                if (i == 100) {
                    ussdResponseCallback.onReceiveUssdResponse(this, ussdResponse.getUssdRequest(), ussdResponse.getReturnMessage());
                } else {
                    ussdResponseCallback.onReceiveUssdResponseFailed(this, ussdResponse.getUssdRequest(), i);
                }
            }
        };
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.handleUssdRequest(getSubId(), str, resultReceiver);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#sendUSSDCode", e);
            UssdResponse ussdResponse = new UssdResponse(str, "");
            Bundle bundle = new Bundle();
            bundle.putParcelable(USSD_RESPONSE, ussdResponse);
            resultReceiver.send(-2, bundle);
        }
    }

    public boolean isConcurrentVoiceAndDataSupported() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            return iTelephony.isConcurrentVoiceAndDataAllowed(getSubId());
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isConcurrentVoiceAndDataAllowed", e);
            return false;
        }
    }

    @SystemApi
    public boolean handlePinMmi(String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.handlePinMmi(str);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#handlePinMmi", e);
            return false;
        }
    }

    @SystemApi
    public boolean handlePinMmiForSubscriber(int i, String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.handlePinMmiForSubscriber(i, str);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#handlePinMmi", e);
            return false;
        }
    }

    public boolean semHandlePinMmiForSubscriber(int i, String str) {
        return handlePinMmiForSubscriber(i, str);
    }

    @SystemApi
    public void toggleRadioOnOff() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.toggleRadioOnOff();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#toggleRadioOnOff", e);
        }
    }

    @SystemApi
    @Deprecated
    public boolean setRadio(boolean z) {
        try {
            if (z) {
                clearRadioPowerOffForReason(0);
                return true;
            }
            requestRadioPowerOffForReason(0);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error calling ".concat(z ? "clearRadioPowerOffForReason" : "requestRadioPowerOffForReason"), e);
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public boolean setRadioPower(boolean z) {
        try {
            if (z) {
                clearRadioPowerOffForReason(0);
                return true;
            }
            requestRadioPowerOffForReason(0);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error calling ".concat(z ? "clearRadioPowerOffForReason" : "requestRadioPowerOffForReason"), e);
            return false;
        }
    }

    @SystemApi
    public void requestRadioPowerOffForReason(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (!iTelephony.requestRadioPowerOffForReason(getSubId(), i)) {
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
    public void clearRadioPowerOffForReason(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (!iTelephony.clearRadioPowerOffForReason(getSubId(), i)) {
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
        HashSet hashSet = new HashSet();
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                hashSet.addAll(iTelephony.getRadioPowerOffReasons(getSubId(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag()));
                return hashSet;
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getRadioPowerOffReasons", e);
            e.rethrowAsRuntimeException();
            return hashSet;
        }
    }

    @SystemApi
    public void shutdownAllRadios() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.shutdownMobileRadios();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#shutdownAllRadios", e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public boolean isAnyRadioPoweredOn() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.needMobileRadioShutdown();
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getRadioPowerState(getSlotIndex(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            return 2;
        } catch (RemoteException unused) {
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.enableDataConnectivity(getOpPackageName());
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.disableDataConnectivity(getOpPackageName());
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isDataConnectivityPossible(getSubId(SubscriptionManager.getActiveDataSubscriptionId()));
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.needsOtaServiceProvisioning();
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getMobileProvisioningUrl();
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#getMobileProvisioningUrl RemoteException" + e);
            return null;
        }
    }

    public boolean isOpportunisticSubscription() {
        SubscriptionInfo subscriptionInfo;
        SubscriptionInfo subscriptionInfo2;
        SubscriptionManager subscriptionManager = this.mSubscriptionManager;
        if (subscriptionManager != null && subscriptionManager.getActiveSubscriptionInfoCount() == 2) {
            int[] subId = SubscriptionManager.getSubId(0);
            int[] subId2 = SubscriptionManager.getSubId(1);
            if (ArrayUtils.isEmpty(subId) || ArrayUtils.isEmpty(subId2)) {
                subscriptionInfo = null;
                subscriptionInfo2 = null;
            } else {
                subscriptionInfo = this.mSubscriptionManager.getActiveSubscriptionInfo(subId[0]);
                subscriptionInfo2 = this.mSubscriptionManager.getActiveSubscriptionInfo(subId2[0]);
            }
            if (subscriptionInfo != null && subscriptionInfo2 != null && subscriptionInfo.getGroupUuid() != null && subscriptionInfo.getGroupUuid().equals(subscriptionInfo2.getGroupUuid()) && (subscriptionInfo2.isOpportunistic() || subscriptionInfo.isOpportunistic())) {
                Log.d(TAG, "isOpportunisticSubscription true");
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public void setDataEnabled(boolean z) {
        if (isOpportunisticSubscription() && (("DSA".equals(TelephonyFeatures.getSalesCode()) && TelephonyFeatures.isMainOperatorSpecific(1, "DSG")) || TelephonyFeatures.isSubOperatorSpecific(1, "CHA", "CCT") || TelephonyFeatures.isSubOperatorSpecific(0, "CHA", "CCT"))) {
            int activeDataSubscriptionId = SubscriptionManager.getActiveDataSubscriptionId();
            if (SubscriptionManager.isUsableSubIdValue(activeDataSubscriptionId)) {
                setDataEnabled(activeDataSubscriptionId, z);
                return;
            }
        }
        setDataEnabled(getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), z);
    }

    @SystemApi
    @Deprecated
    public void setDataEnabled(int i, boolean z) {
        try {
            setDataEnabledForReason(i, 0, z);
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
        } catch (IllegalStateException e) {
            Log.e(TAG, "Error calling #isDataEnabled, returning default (false).", e);
            return false;
        }
    }

    public boolean isDataRoamingEnabled() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isDataRoamingEnabled(getSubId(SubscriptionManager.getDefaultDataSubscriptionId()));
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isDataRoamingEnabled", e);
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public int getCdmaRoamingMode() {
        if (Flags.cleanupCdma()) {
            return -1;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCdmaRoamingMode(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getCdmaRoamingMode", e);
            e.rethrowFromSystemServer();
            return -1;
        }
    }

    @SystemApi
    @Deprecated
    public void setCdmaRoamingMode(int i) {
        if (Flags.cleanupCdma()) {
            return;
        }
        if (getPhoneType() != 2) {
            throw new IllegalStateException("Phone does not support CDMA.");
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (!iTelephony.setCdmaRoamingMode(getSubId(), i)) {
                    throw new IllegalStateException("radio is unavailable.");
                }
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setCdmaRoamingMode", e);
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public int getCdmaSubscriptionMode() {
        if (Flags.cleanupCdma()) {
            return -1;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCdmaSubscriptionMode(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getCdmaSubscriptionMode", e);
            e.rethrowFromSystemServer();
            return 0;
        }
    }

    @SystemApi
    @Deprecated
    public void setCdmaSubscriptionMode(int i) {
        if (Flags.cleanupCdma()) {
            return;
        }
        if (getPhoneType() != 2) {
            throw new IllegalStateException("Phone does not support CDMA.");
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (!iTelephony.setCdmaSubscriptionMode(getSubId(), i)) {
                    throw new IllegalStateException("radio is unavailable.");
                }
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setCdmaSubscriptionMode", e);
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDataRoamingEnabled(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setDataRoamingEnabled(getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), z);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setDataRoamingEnabled", e);
        }
    }

    @SystemApi
    @Deprecated
    public boolean getDataEnabled(int i) {
        try {
            return isDataEnabledForReason(i, 0);
        } catch (RuntimeException e) {
            Log.e(TAG, "Error calling isDataEnabledForReason e:" + e);
            return false;
        }
    }

    public int invokeOemRilRequestRawForPhone(int i, byte[] bArr, byte[] bArr2) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.invokeOemRilRequestRawForPhone(i, bArr, bArr2);
            }
            return -1;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "invokeOemRilRequestRawForPhone is fail due to RemoteException. " + e);
            return -1;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "invokeOemRilRequestRawForPhone is fail due to NullPointerException. " + e2);
            return -1;
        }
    }

    public int invokeOemRilRequestRawForSubscriber(int i, byte[] bArr, byte[] bArr2) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.invokeOemRilRequestRawForSubscriber(i, bArr, bArr2);
            }
            return -1;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "invokeOemRilRequestRawForSubscriber is fail due to RemoteException. " + e);
            return -1;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "invokeOemRilRequestRawForSubscriber is fail due to NullPointerException. " + e2);
            return -1;
        }
    }

    @SystemApi
    @Deprecated
    public void enableVideoCalling(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.enableVideoCalling(z);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#enableVideoCalling", e);
        }
    }

    @SystemApi
    @Deprecated
    public boolean isVideoCallingEnabled() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isVideoCallingEnabled(getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isVideoCallingEnabled", e);
            return false;
        }
    }

    public boolean canChangeDtmfToneLength() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.canChangeDtmfToneLength(this.mSubId, getOpPackageName(), getAttributionTag());
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isWorldPhone(this.mSubId, getOpPackageName(), getAttributionTag());
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
        try {
            Context context = this.mContext;
            TelecomManager telecomManager = context != null ? (TelecomManager) context.getSystemService(TelecomManager.class) : null;
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isRttSupported(this.mSubId);
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isHearingAidCompatibilitySupported();
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isHearingAidCompatibilitySupported", e);
            return false;
        } catch (SecurityException e2) {
            Log.e(TAG, "Permission error calling ITelephony#isHearingAidCompatibilitySupported", e2);
            return false;
        }
    }

    @Deprecated
    public boolean isImsRegistered(int i) {
        try {
            return getITelephony().isImsRegistered(i);
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    @Deprecated
    public boolean isImsRegistered() {
        try {
            return getITelephony().isImsRegistered(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    @Deprecated
    public boolean isVolteAvailable() {
        try {
            return getITelephony().isAvailable(getSubId(), 1, 0);
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    @Deprecated
    public boolean isVideoTelephonyAvailable() {
        try {
            return getITelephony().isVideoTelephonyAvailable(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    @Deprecated
    public boolean isWifiCallingAvailable() {
        try {
            return getITelephony().isWifiCallingAvailable(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    @Deprecated
    public int getImsRegTechnologyForMmTel() {
        try {
            return getITelephony().getImsRegTechnologyForMmTel(getSubId());
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public void setSimOperatorNumericForPhone(int i, String str) {
        if (SubscriptionManager.isValidPhoneId(i)) {
            TelephonyProperties.icc_operator_numeric(updateTelephonyProperty(TelephonyProperties.icc_operator_numeric(), i, str));
        }
    }

    public void setSimOperatorNameForPhone(int i, String str) {
        if (SubscriptionManager.isValidPhoneId(i)) {
            TelephonyProperties.icc_operator_alpha(updateTelephonyProperty(TelephonyProperties.icc_operator_alpha(), i, str));
        }
    }

    public void setSimCountryIso(String str) {
        setSimCountryIsoForPhone(getPhoneId(), str);
    }

    public void setSimCountryIsoForPhone(int i, String str) {
        if (SubscriptionManager.isValidPhoneId(i)) {
            TelephonyProperties.icc_operator_iso_country(updateTelephonyProperty(TelephonyProperties.icc_operator_iso_country(), i, str));
        }
    }

    public void setSimState(String str) {
        setSimStateForPhone(getPhoneId(), str);
    }

    public void setSimStateForPhone(int i, String str) {
        if (SubscriptionManager.isValidPhoneId(i)) {
            TelephonyProperties.sim_state(updateTelephonyProperty(TelephonyProperties.sim_state(), i, str));
        }
    }

    @SystemApi
    @Deprecated
    public void setSimPowerState(int i) {
        setSimPowerStateForSlot(getSlotIndex(), i);
    }

    @SystemApi
    @Deprecated
    public void setSimPowerStateForSlot(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setSimPowerStateForSlot(i, i2);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setSimPowerStateForSlot", e);
        } catch (SecurityException e2) {
            Log.e(TAG, "Permission error calling ITelephony#setSimPowerStateForSlot", e2);
        }
    }

    @SystemApi
    public void setSimPowerState(int i, Executor executor, Consumer<Integer> consumer) {
        setSimPowerStateForSlot(getSlotIndex(), i, executor, consumer);
    }

    @SystemApi
    public void setSimPowerStateForSlot(int i, int i2, final Executor executor, final Consumer<Integer> consumer) {
        if (i2 != 0 && i2 != 1 && i2 != 2) {
            throw new IllegalArgumentException("requested SIM state is invalid");
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("Telephony is null.");
            }
            AnonymousClass8 anonymousClass8 = new AnonymousClass8(this, executor, consumer);
            if (iTelephony == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Telephony is null");
                }
            } else {
                iTelephony.setSimPowerStateForSlotWithCallback(i, i2, anonymousClass8);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setSimPowerStateForSlot", e);
            runOnBackgroundThread(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda8
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

        AnonymousClass8(TelephonyManager telephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$8$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$8$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public void setBasebandVersionForPhone(int i, String str) {
        if (SubscriptionManager.isValidPhoneId(i)) {
            TelephonyProperties.baseband_version(updateTelephonyProperty(TelephonyProperties.baseband_version(), i, str));
        }
    }

    public String getBasebandVersion() {
        return getBasebandVersionForPhone(getPhoneId());
    }

    public String getBasebandVersionForPhone(int i) {
        return (String) getTelephonyProperty(i, TelephonyProperties.baseband_version(), "");
    }

    public void setPhoneType(int i, int i2) {
        if (SubscriptionManager.isValidPhoneId(i)) {
            TelephonyProperties.current_active_phone(updateTelephonyProperty(TelephonyProperties.current_active_phone(), i, Integer.valueOf(i2)));
        }
    }

    public String getOtaSpNumberSchemaForPhone(int i, String str) {
        return SubscriptionManager.isValidPhoneId(i) ? (String) getTelephonyProperty(i, TelephonyProperties.otasp_num_schema(), str) : str;
    }

    public boolean getSmsReceiveCapableForPhone(int i, boolean z) {
        return SubscriptionManager.isValidPhoneId(i) ? ((Boolean) getTelephonyProperty(i, TelephonyProperties.sms_receive(), Boolean.valueOf(z))).booleanValue() : z;
    }

    public boolean getSmsSendCapableForPhone(int i, boolean z) {
        return SubscriptionManager.isValidPhoneId(i) ? ((Boolean) getTelephonyProperty(i, TelephonyProperties.sms_send(), Boolean.valueOf(z))).booleanValue() : z;
    }

    @SystemApi
    public ComponentName getAndUpdateDefaultRespondViaMessageApplication() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getDefaultRespondViaMessageApplication(getSubId(), true);
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getDefaultRespondViaMessageApplication(getSubId(), false);
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error in getDefaultRespondViaMessageApplication: " + e);
            return null;
        }
    }

    public void setNetworkOperatorNameForPhone(int i, String str) {
        if (SubscriptionManager.isValidPhoneId(i)) {
            List updateTelephonyProperty = updateTelephonyProperty(TelephonyProperties.operator_alpha(), i, str);
            try {
                TelephonyProperties.operator_alpha(updateTelephonyProperty);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "setNetworkOperatorNameForPhone: ", e);
                int size = updateTelephonyProperty.size();
                int i2 = (91 - size) / size;
                for (int i3 = 0; i3 < updateTelephonyProperty.size(); i3++) {
                    if (updateTelephonyProperty.get(i3) != null) {
                        updateTelephonyProperty.set(i3, TextUtils.truncateStringForUtf8Storage((String) updateTelephonyProperty.get(i3), i2));
                    }
                }
                TelephonyProperties.operator_alpha(updateTelephonyProperty);
                Log.e(TAG, "successfully truncated operator_alpha: " + updateTelephonyProperty);
            }
        }
    }

    public void setNetworkOperatorNumericForPhone(int i, String str) {
        if (SubscriptionManager.isValidPhoneId(i)) {
            TelephonyProperties.operator_numeric(updateTelephonyProperty(TelephonyProperties.operator_numeric(), i, str));
        }
    }

    public void setNetworkRoamingForPhone(int i, boolean z) {
        if (SubscriptionManager.isValidPhoneId(i)) {
            logWithCallerInfo("setNetworkRoamingForPhone - phoneId: " + i + ", isRoaming: " + z);
            TelephonyProperties.operator_is_roaming(updateTelephonyProperty(TelephonyProperties.operator_is_roaming(), i, Boolean.valueOf(z)));
            return;
        }
        logWithCallerInfo("setNetworkRoamingForPhone - Invalid phoneId: " + i);
    }

    public void setDataNetworkType(int i) {
        setDataNetworkTypeForPhone(getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId()), i);
    }

    public void setDataNetworkTypeForPhone(int i, int i2) {
        if (SubscriptionManager.isValidPhoneId(i)) {
            TelephonyProperties.data_network_type(updateTelephonyProperty(TelephonyProperties.data_network_type(), i, ServiceState.rilRadioTechnologyToString(i2)));
        }
    }

    public int getSubIdForPhoneAccount(PhoneAccount phoneAccount) {
        if (phoneAccount == null || !phoneAccount.hasCapabilities(4)) {
            return -1;
        }
        return getSubscriptionId(phoneAccount.getAccountHandle());
    }

    public PhoneAccountHandle getPhoneAccountHandle() {
        return getPhoneAccountHandleForSubscriptionId(getSubId());
    }

    public PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getPhoneAccountHandleForSubscriptionId(i);
            }
            return null;
        } catch (RemoteException unused) {
            return null;
        }
    }

    public int getSubscriptionId(PhoneAccountHandle phoneAccountHandle) {
        return this.mPhoneAccountHandleToSubIdCache.query(phoneAccountHandle).intValue();
    }

    public void factoryReset(int i) {
        try {
            Log.d(TAG, "factoryReset: subId=" + i);
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.factoryReset(i, getOpPackageName());
            }
        } catch (RemoteException unused) {
        }
    }

    @SystemApi
    public void resetSettings() {
        try {
            Log.d(TAG, "resetSettings: subId=" + getSubId());
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                resetNetworkSettings(getPhoneId());
                iTelephony.factoryReset(getSubId(), getOpPackageName());
                semSetNrMode(getPhoneId(), 240, false);
            }
        } catch (RemoteException unused) {
        }
    }

    @SystemApi
    public Locale getSimLocale() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            String simLocaleForSubscriber = iTelephony.getSimLocaleForSubscriber(getSubId());
            if (TextUtils.isEmpty(simLocaleForSubscriber)) {
                return null;
            }
            return Locale.forLanguageTag(simLocaleForSubscriber);
        } catch (RemoteException unused) {
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
        public @interface ModemActivityInfoError {
        }

        public ModemActivityInfoException(int i) {
            this.mErrorCode = i;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Override // java.lang.Throwable
        public String toString() {
            int i = this.mErrorCode;
            if (i == 0) {
                return "ERROR_UNKNOWN";
            }
            if (i == 1) {
                return "ERROR_PHONE_NOT_AVAILABLE";
            }
            if (i == 2) {
                return "ERROR_INVALID_INFO_RECEIVED";
            }
            if (i == 3) {
                return "ERROR_MODEM_RESPONSE_ERROR";
            }
            return DevicePolicyResources.UNDEFINED;
        }
    }

    @SystemApi
    public void requestModemActivityInfo(Executor executor, final OutcomeReceiver<ModemActivityInfo, ModemActivityInfoException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        AnonymousClass9 anonymousClass9 = new AnonymousClass9(this, null, executor, outcomeReceiver);
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                StringBuilder sb = new StringBuilder(this.mContext.getOpPackageName());
                if (!SemTelephonyUtils.SHIP_BUILD) {
                    sb.append(":");
                    sb.append(Debug.getCaller());
                }
                iSemTelephony.requestModemActivityInfo(anonymousClass9, sb.toString());
                return;
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getModemActivityInfo", e);
        }
        executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                OutcomeReceiver.this.onError(new TelephonyManager.ModemActivityInfoException(1));
            }
        });
    }

    /* renamed from: android.telephony.TelephonyManager$9, reason: invalid class name */
    class AnonymousClass9 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass9(TelephonyManager telephonyManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            if (bundle == null) {
                Log.w(TelephonyManager.TAG, "requestModemActivityInfo: received null bundle");
                sendErrorToListener(0);
                return;
            }
            bundle.setDefusable(true);
            if (bundle.containsKey("exception")) {
                sendErrorToListener(bundle.getInt("exception"));
                return;
            }
            if (!bundle.containsKey("controller_activity")) {
                Log.w(TelephonyManager.TAG, "requestModemActivityInfo: Bundle did not contain expected key");
                sendErrorToListener(0);
                return;
            }
            Parcelable parcelable = bundle.getParcelable("controller_activity");
            if (!(parcelable instanceof ModemActivityInfo)) {
                Log.w(TelephonyManager.TAG, "requestModemActivityInfo: Bundle contained something that wasn't a ModemActivityInfo.");
                sendErrorToListener(0);
                return;
            }
            ModemActivityInfo modemActivityInfo = (ModemActivityInfo) parcelable;
            if (!modemActivityInfo.isValid()) {
                Log.w(TelephonyManager.TAG, "requestModemActivityInfo: Received an invalid ModemActivityInfo");
                sendErrorToListener(2);
            } else {
                Log.d(TelephonyManager.TAG, "requestModemActivityInfo: Sending result to app: " + modemActivityInfo);
                sendResultToListener(modemActivityInfo);
            }
        }

        private void sendResultToListener(final ModemActivityInfo modemActivityInfo) {
            final Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$callback;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$9$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$9$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            OutcomeReceiver.this.onResult(r2);
                        }
                    });
                }
            });
        }

        private void sendErrorToListener(int i) {
            final ModemActivityInfoException modemActivityInfoException = new ModemActivityInfoException(i);
            final Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$callback;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$9$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$9$$ExternalSyntheticLambda2
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

    public ServiceState getServiceState(int i) {
        return getServiceStateForSlot(SubscriptionManager.getSlotIndex(getSubId()), i != 2, i == 0);
    }

    private ServiceState getServiceStateForSlot(int i, boolean z, boolean z2) {
        int i2;
        try {
            try {
                ITelephony iTelephony = getITelephony();
                if (iTelephony == null) {
                    return null;
                }
                i2 = i;
                try {
                    return iTelephony.getServiceStateForSlot(i2, z, z2, getOpPackageName(), getAttributionTag());
                } catch (NullPointerException unused) {
                    AnomalyReporter.reportAnomaly(UUID.fromString("e2bed88e-def9-476e-bd71-3e572a8de6d1"), "getServiceStateForSlot " + i2 + " NPE");
                    return null;
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Error calling ITelephony#getServiceStateForSlot", e);
                return null;
            }
        } catch (NullPointerException unused2) {
            i2 = i;
        }
    }

    public ServiceState getServiceStateForSubscriber(int i) {
        return getServiceStateForSlot(SubscriptionManager.getSlotIndex(i), false, false);
    }

    public ServiceState getServiceStateForSlot(int i) {
        return getServiceStateForSlot(i, false, false);
    }

    public Uri getVoicemailRingtoneUri(PhoneAccountHandle phoneAccountHandle) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getVoicemailRingtoneUri(phoneAccountHandle);
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setVoicemailRingtoneUri(getOpPackageName(), phoneAccountHandle, uri);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setVoicemailRingtoneUri", e);
        }
    }

    public boolean isVoicemailVibrationEnabled(PhoneAccountHandle phoneAccountHandle) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isVoicemailVibrationEnabled(phoneAccountHandle);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isVoicemailVibrationEnabled", e);
            return false;
        }
    }

    @Deprecated
    public void setVoicemailVibrationEnabled(PhoneAccountHandle phoneAccountHandle, boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setVoicemailVibrationEnabled(getOpPackageName(), phoneAccountHandle, z);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isVoicemailVibrationEnabled", e);
        }
    }

    public int getSimCarrierId() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSubscriptionCarrierId(getSubId());
            }
            return -1;
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public CharSequence getSimCarrierIdName() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSubscriptionCarrierName(getSubId());
            }
            return null;
        } catch (RemoteException unused) {
            return null;
        }
    }

    public int getSimSpecificCarrierId() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSubscriptionSpecificCarrierId(getSubId());
            }
            return -1;
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public CharSequence getSimSpecificCarrierIdName() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSubscriptionSpecificCarrierName(getSubId());
            }
            return null;
        } catch (RemoteException unused) {
            return null;
        }
    }

    public int getCarrierIdFromSimMccMnc() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierIdFromMccMnc(getSlotIndex(), getSimOperator(), true);
            }
            return -1;
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public int getCarrierIdFromMccMnc(String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierIdFromMccMnc(getSlotIndex(), str, false);
            }
            return -1;
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public List<String> getCertsFromCarrierPrivilegeAccessRules() {
        List<String> list = null;
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                list = iTelephony.getCertsFromCarrierPrivilegeAccessRules(getSubId());
            }
        } catch (RemoteException unused) {
        }
        return list == null ? Collections.EMPTY_LIST : list;
    }

    @SystemApi
    public String getAidForAppType(int i) {
        return getAidForAppType(getSubId(), i);
    }

    public String getAidForAppType(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getAidForAppType(i, i2);
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

    public String getEsn(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getEsn(i);
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getEsn", e);
            return null;
        }
    }

    @SystemApi
    @Deprecated
    public String getCdmaPrlVersion() {
        if (Flags.cleanupCdma()) {
            return null;
        }
        return getCdmaPrlVersion(getSubId());
    }

    @Deprecated
    public String getCdmaPrlVersion(int i) {
        if (Flags.cleanupCdma()) {
            return null;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCdmaPrlVersion(i);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getCdmaPrlVersion", e);
        }
        return null;
    }

    @SystemApi
    public List<TelephonyHistogram> getTelephonyHistograms() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getTelephonyHistograms();
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getTelephonyHistograms", e);
            return null;
        }
    }

    @SystemApi
    @Deprecated
    public int setAllowedCarriers(int i, List<CarrierIdentifier> list) {
        if (list != null && SubscriptionManager.isValidPhoneId(i) && setCarrierRestrictionRules(CarrierRestrictionRules.newBuilder().setAllowedCarriers(list).setDefaultCarrierRestriction(list.isEmpty() ? 1 : 0).build()) == 0) {
            return list.size();
        }
        return -1;
    }

    @SystemApi
    public int setCarrierRestrictionRules(CarrierRestrictionRules carrierRestrictionRules) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setAllowedCarriers(carrierRestrictionRules);
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
    public List<CarrierIdentifier> getAllowedCarriers(int i) {
        CarrierRestrictionRules carrierRestrictionRules;
        if (SubscriptionManager.isValidPhoneId(i) && (carrierRestrictionRules = getCarrierRestrictionRules()) != null) {
            return carrierRestrictionRules.getAllowedCarriers();
        }
        return new ArrayList(0);
    }

    @SystemApi
    public CarrierRestrictionRules getCarrierRestrictionRules() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getAllowedCarriers();
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

    public void getCarrierRestrictionStatus(Executor executor, Consumer<Integer> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        AnonymousClass10 anonymousClass10 = new AnonymousClass10(this, executor, consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.getCarrierRestrictionStatus(anonymousClass10, getOpPackageName());
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCarrierRestrictionStatus: RemoteException = " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.TelephonyManager$10, reason: invalid class name */
    class AnonymousClass10 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass10(TelephonyManager telephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$10$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$10$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public List<String> getShaIdFromAllowList(String str, int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getShaIdFromAllowList(str, i);
            }
            return Collections.EMPTY_LIST;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getShaIdFromAllowList: RemoteException = " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    @Deprecated
    public void setCarrierDataEnabled(boolean z) {
        try {
            setDataEnabledForReason(2, z);
        } catch (RuntimeException e) {
            Log.e(TAG, "Error calling setDataEnabledForReason e:" + e);
        }
    }

    @SystemApi
    @Deprecated
    public void setRadioEnabled(boolean z) {
        if (z) {
            clearRadioPowerOffForReason(2);
        } else {
            requestRadioPowerOffForReason(2);
        }
    }

    public int setVoNrEnabled(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setVoNrEnabled(getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), z);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setVoNrEnabled", e);
            return 4;
        }
    }

    public int semSetVoNrEnabled(boolean z) {
        return setVoNrEnabled(z);
    }

    public boolean isVoNrEnabled() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isVoNrEnabled(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isVoNrEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean semIsVoNrEnabled() {
        return isVoNrEnabled();
    }

    @SystemApi
    public void reportDefaultNetworkStatus(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.carrierActionReportDefaultNetworkStatus(getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), z);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#carrierActionReportDefaultNetworkStatus", e);
        }
    }

    @SystemApi
    public void resetAllCarrierActions() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.carrierActionResetAll(getSubId(SubscriptionManager.getDefaultDataSubscriptionId()));
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#carrierActionResetAll", e);
        }
        setRadioEnabled(true);
    }

    @Deprecated
    public void setPolicyDataEnabled(boolean z) {
        try {
            setDataEnabledForReason(1, z);
        } catch (RuntimeException e) {
            Log.e(TAG, "Error calling setDataEnabledForReason e:" + e);
        }
    }

    public void setDataEnabledForReason(int i, boolean z) {
        setDataEnabledForReason(getSubId(), i, z);
    }

    private void setDataEnabledForReason(int i, int i2, boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setDataEnabledForReason(i, i2, z, getOpPackageName());
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Telephony#setDataEnabledForReason RemoteException", e);
            e.rethrowFromSystemServer();
        }
    }

    public boolean isDataEnabledForReason(int i) {
        return isDataEnabledForReason(getSubId(), i);
    }

    private boolean isDataEnabledForReason(int i, int i2) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isDataEnabledForReason(i, i2);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Telephony#isDataEnabledForReason RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public List<ClientRequestStats> getClientRequestStats(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getClientRequestStats(getOpPackageName(), getAttributionTag(), i);
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

    public boolean getEmergencyCallbackMode(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            return iTelephony.getEmergencyCallbackMode(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#getEmergencyCallbackMode", e);
            return false;
        }
    }

    public boolean isManualNetworkSelectionAllowed() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isManualNetworkSelectionAllowed(getSubId());
            }
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#isManualNetworkSelectionAllowed", e);
            return true;
        }
    }

    public SignalStrength getSignalStrength() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSignalStrength(getSubId());
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isDataEnabled(subId);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error isDataConnectionAllowed", e);
            return false;
        }
    }

    public boolean isDataCapable() {
        return hasCapability(PackageManager.FEATURE_TELEPHONY_DATA, R.bool.config_mobile_data_capable);
    }

    @Deprecated
    public void setCarrierTestOverride(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCarrierTestOverride(getSubId(), str, str2, str3, str4, str5, str6, str7, null, null);
            }
        } catch (RemoteException unused) {
        }
    }

    public void setCarrierTestOverride(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCarrierTestOverride(getSubId(), str, str2, str3, str4, str5, str6, str7, str8, str9);
            }
        } catch (RemoteException unused) {
        }
    }

    public int getCarrierIdListVersion() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierIdListVersion(getSubId());
            }
            return -1;
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public int getNumberOfModemsWithSimultaneousDataConnections() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getNumberOfModemsWithSimultaneousDataConnections(getSubId(), getOpPackageName(), getAttributionTag());
            }
            return 0;
        } catch (RemoteException unused) {
            return 0;
        }
    }

    @SystemApi
    public boolean setOpportunisticNetworkState(boolean z) {
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        try {
            IOns iOns = getIOns();
            if (iOns != null) {
                return iOns.setEnable(z, opPackageName);
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "enableOpportunisticNetwork RemoteException", e);
            return false;
        }
    }

    @SystemApi
    public boolean isOpportunisticNetworkEnabled() {
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        try {
            IOns iOns = getIOns();
            if (iOns != null) {
                return iOns.isEnabled(opPackageName);
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "enableOpportunisticNetwork RemoteException", e);
            return false;
        }
    }

    public long getSupportedRadioAccessFamily() {
        try {
            if (getITelephony() != null) {
                return r2.getRadioAccessFamily(getSlotIndex(), getOpPackageName());
            }
            return 0L;
        } catch (RemoteException | NullPointerException unused) {
            return 0L;
        }
    }

    @SystemApi
    public void notifyOtaEmergencyNumberDbInstalled() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.notifyOtaEmergencyNumberDbInstalled();
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "notifyOtaEmergencyNumberDatabaseInstalled RemoteException", e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void updateOtaEmergencyNumberDbFilePath(ParcelFileDescriptor parcelFileDescriptor) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.updateOtaEmergencyNumberDbFilePath(parcelFileDescriptor);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "updateOtaEmergencyNumberDbFilePath RemoteException", e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void resetOtaEmergencyNumberDbFilePath() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.resetOtaEmergencyNumberDbFilePath();
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "resetOtaEmergencyNumberDbFilePath RemoteException", e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public boolean isEmergencyAssistanceEnabled() {
        this.mContext.enforceCallingOrSelfPermission(Manifest.permission.READ_PRIVILEGED_PHONE_STATE, "isEmergencyAssistanceEnabled");
        return true;
    }

    @SystemApi
    public String getEmergencyAssistancePackageName() {
        if (!isEmergencyAssistanceEnabled() || !isVoiceCapable()) {
            throw new IllegalStateException("isEmergencyAssistanceEnabled() is false or device not voice capable.");
        }
        return ((RoleManager) this.mContext.getSystemService(RoleManager.class)).getEmergencyRoleHolder(this.mContext.getUserId());
    }

    public Map<Integer, List<EmergencyNumber>> getEmergencyNumberList() {
        HashMap hashMap = new HashMap();
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getEmergencyNumberList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "getEmergencyNumberList RemoteException", e);
            e.rethrowAsRuntimeException();
            return hashMap;
        }
    }

    public Map<Integer, List<EmergencyNumber>> getEmergencyNumberList(int i) {
        HashMap hashMap = new HashMap();
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return filterEmergencyNumbersByCategories(iTelephony.getEmergencyNumberList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag()), i);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "getEmergencyNumberList with Categories RemoteException", e);
            e.rethrowAsRuntimeException();
            return hashMap;
        }
    }

    public Map<Integer, List<EmergencyNumber>> filterEmergencyNumbersByCategories(Map<Integer, List<EmergencyNumber>> map, int i) {
        HashMap hashMap = new HashMap();
        if (map != null) {
            for (Integer num : map.keySet()) {
                List<EmergencyNumber> list = map.get(num);
                ArrayList arrayList = new ArrayList();
                for (EmergencyNumber emergencyNumber : list) {
                    if (emergencyNumber.isInEmergencyServiceCategories(i)) {
                        arrayList.add(emergencyNumber);
                    }
                }
                hashMap.put(num, arrayList);
            }
        }
        return hashMap;
    }

    public boolean isEmergencyNumber(String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isEmergencyNumber(str, true);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "isEmergencyNumber RemoteException", e);
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    public boolean isEmergencyNumber(int i, String str) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.isEmergencyNumberBySubId(i, str, true);
            }
            throw new IllegalStateException("SemTelephonyService is null");
        } catch (RemoteException e) {
            Log.e(TAG, "isEmergencyNumberBySubId RemoteException", e);
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public boolean isPotentialEmergencyNumber(String str) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isEmergencyNumber(str, false);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "isEmergencyNumber RemoteException", e);
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    @Deprecated
    public boolean isPotentialEmergencyNumber(int i, String str) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.isEmergencyNumberBySubId(i, str, false);
            }
            throw new IllegalStateException("SemTelephonyService is null");
        } catch (RemoteException e) {
            Log.e(TAG, "isEmergencyNumberBySubId RemoteException", e);
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    @SystemApi
    public int getEmergencyNumberDbVersion() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getEmergencyNumberDbVersion(getSubId());
            }
            return -1;
        } catch (RemoteException e) {
            Log.e(TAG, "getEmergencyNumberDbVersion RemoteException", e);
            e.rethrowAsRuntimeException();
            return -1;
        }
    }

    public void setPreferredOpportunisticDataSubscription(int i, boolean z, final Executor executor, final Consumer<Integer> consumer) {
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        try {
            IOns iOns = getIOns();
            if (iOns == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Opportunistic Network Service is null");
                }
                throw new RemoteException("Null Opportunistic Network Service!");
            }
            iOns.setPreferredDataSubscriptionId(i, z, new AnonymousClass11(this, executor, consumer), opPackageName);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setPreferredOpportunisticDataSubscription RemoteException", e);
            if (executor == null || consumer == null) {
                return;
            }
            runOnBackgroundThread(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            TelephonyManager.lambda$setPreferredOpportunisticDataSubscription$20(r1);
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

        AnonymousClass11(TelephonyManager telephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.ISetOpportunisticDataCallback
        public void onComplete(final int i) {
            if (this.val$executor == null || this.val$callback == null) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$11$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(Integer.valueOf(i));
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    static /* synthetic */ void lambda$setPreferredOpportunisticDataSubscription$20(Consumer consumer) {
        if (Compatibility.isChangeEnabled(CALLBACK_ON_MORE_ERROR_CODE_CHANGE)) {
            consumer.accept(4);
        } else {
            consumer.accept(2);
        }
    }

    public int getPreferredOpportunisticDataSubscription() {
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        Context context2 = this.mContext;
        String attributionTag = context2 != null ? context2.getAttributionTag() : null;
        try {
            IOns iOns = getIOns();
            if (iOns != null) {
                return iOns.getPreferredDataSubscriptionId(opPackageName, attributionTag);
            }
            return -1;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getPreferredDataSubscriptionId RemoteException", e);
            return -1;
        }
    }

    public void updateAvailableNetworks(List<AvailableNetworkInfo> list, final Executor executor, final Consumer<Integer> consumer) {
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        Objects.requireNonNull(list, "availableNetworks must not be null.");
        try {
            IOns iOns = getIOns();
            if (iOns == null) {
                if (Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new IllegalStateException("Opportunistic Network Service is null");
                }
                throw new RemoteException("Null Opportunistic Network Service!");
            }
            iOns.updateAvailableNetworks(list, new AnonymousClass12(this, executor, consumer), opPackageName);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "updateAvailableNetworks RemoteException", e);
            if (executor == null || consumer == null) {
                return;
            }
            runOnBackgroundThread(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            TelephonyManager.lambda$updateAvailableNetworks$22(r1);
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

        AnonymousClass12(TelephonyManager telephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IUpdateAvailableNetworksCallback
        public void onComplete(final int i) {
            final Consumer consumer;
            final Executor executor = this.val$executor;
            if (executor == null || (consumer = this.val$callback) == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$12$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$12$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            r1.accept(Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    static /* synthetic */ void lambda$updateAvailableNetworks$22(Consumer consumer) {
        if (Compatibility.isChangeEnabled(CALLBACK_ON_MORE_ERROR_CODE_CHANGE)) {
            consumer.accept(9);
        } else {
            consumer.accept(1);
        }
    }

    @SystemApi
    public boolean enableModemForSlot(int i, boolean z) {
        if (this.mContext != null) {
            com.android.telephony.Rlog.i(TAG, "enableModemForSlot is called. package: " + this.mContext.getOpPackageName() + " , enable: " + z + " , slotIndex: " + i);
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.enableModemForSlot(i, z);
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "enableModem RemoteException", e);
            return false;
        }
    }

    public boolean isModemEnabledForSlot(int i) {
        if (this.mContext != null) {
            com.android.telephony.Rlog.i(TAG, "isModemEnabledForSlot is called. package: " + this.mContext.getOpPackageName() + " , slotIndex: " + i);
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isModemEnabledForSlot(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "enableModem RemoteException", e);
            return false;
        }
    }

    @SystemApi
    public void setMultiSimCarrierRestriction(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setMultiSimCarrierRestriction(z);
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isMultiSimSupported(getOpPackageName(), getAttributionTag());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "isMultiSimSupported RemoteException", e);
        }
        return 1;
    }

    public void switchMultiSimConfig(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.switchMultiSimConfig(i);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "switchMultiSimConfig RemoteException", e);
        }
    }

    public boolean doesSwitchMultiSimConfigTriggerReboot() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.doesSwitchMultiSimConfigTriggerReboot(getSubId(), getOpPackageName(), getAttributionTag());
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

    public Pair<Integer, Integer> getHalVersion(int i) {
        ITelephony iTelephony;
        try {
            iTelephony = getITelephony();
        } catch (RemoteException e) {
            Log.e(TAG, "getHalVersion() RemoteException", e);
            e.rethrowAsRuntimeException();
        }
        if (iTelephony != null) {
            int halVersion = iTelephony.getHalVersion(i);
            if (halVersion != -1) {
                return new Pair<>(Integer.valueOf(halVersion / 100), Integer.valueOf(halVersion % 100));
            }
            return HAL_VERSION_UNKNOWN;
        }
        throw new IllegalStateException("telephony service is null.");
    }

    @SystemApi
    public int getCarrierPrivilegeStatus(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierPrivilegeStatusForUid(getSubId(), i);
            }
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "getCarrierPrivilegeStatus RemoteException", e);
            return 0;
        }
    }

    public List<ApnSetting> getDevicePolicyOverrideApns(Context context) {
        Cursor query = context.getContentResolver().query(Telephony.Carriers.DPC_URI, null, null, null, null);
        try {
            if (query == null) {
                List<ApnSetting> list = Collections.EMPTY_LIST;
                if (query != null) {
                    query.close();
                }
                return list;
            }
            ArrayList arrayList = new ArrayList();
            query.moveToPosition(-1);
            while (query.moveToNext()) {
                arrayList.add(ApnSetting.makeApnSetting(query));
            }
            if (query != null) {
                query.close();
            }
            return arrayList;
        } catch (Throwable th) {
            if (query == null) {
                throw th;
            }
            try {
                query.close();
                throw th;
            } catch (Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }

    public int addDevicePolicyOverrideApn(Context context, ApnSetting apnSetting) {
        Uri insert = context.getContentResolver().insert(Telephony.Carriers.DPC_URI, apnSetting.toContentValues());
        if (insert == null) {
            return -1;
        }
        try {
            return Integer.parseInt(insert.getLastPathSegment());
        } catch (NumberFormatException unused) {
            com.android.telephony.Rlog.e(TAG, "Failed to parse inserted override APN id: " + insert.getLastPathSegment());
            return -1;
        }
    }

    public boolean modifyDevicePolicyOverrideApn(Context context, int i, ApnSetting apnSetting) {
        return context.getContentResolver().update(Uri.withAppendedPath(Telephony.Carriers.DPC_URI, Integer.toString(i)), apnSetting.toContentValues(), null, null) > 0;
    }

    @SystemApi
    public boolean isDataEnabledForApn(int i) {
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isDataEnabledForApn(i, getSubId(), opPackageName);
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#isDataEnabledForApn RemoteException" + e);
            return false;
        }
    }

    @SystemApi
    public boolean isApnMetered(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isApnMetered(i, getSubId());
            }
            return true;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#isApnMetered RemoteException" + e);
            return true;
        }
    }

    @SystemApi
    public void setSystemSelectionChannels(List<RadioAccessSpecifier> list, Executor executor, Consumer<Boolean> consumer) {
        Objects.requireNonNull(list, "Specifiers must not be null.");
        Objects.requireNonNull(executor, "Executor must not be null.");
        Objects.requireNonNull(consumer, "Callback must not be null.");
        setSystemSelectionChannelsInternal(list, executor, consumer);
    }

    @SystemApi
    public void setSystemSelectionChannels(List<RadioAccessSpecifier> list) {
        Objects.requireNonNull(list, "Specifiers must not be null.");
        setSystemSelectionChannelsInternal(list, null, null);
    }

    /* renamed from: android.telephony.TelephonyManager$13, reason: invalid class name */
    class AnonymousClass13 extends IBooleanConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass13(TelephonyManager telephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IBooleanConsumer
        public void accept(final boolean z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$13$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(Boolean.valueOf(z));
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private void setSystemSelectionChannelsInternal(List<RadioAccessSpecifier> list, Executor executor, Consumer<Boolean> consumer) {
        AnonymousClass13 anonymousClass13 = consumer == null ? null : new AnonymousClass13(this, executor, consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setSystemSelectionChannels(list, getSubId(), anonymousClass13);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#setSystemSelectionChannels RemoteException" + e);
        }
    }

    @SystemApi
    public List<RadioAccessSpecifier> getSystemSelectionChannels() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSystemSelectionChannels(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#getSystemSelectionChannels RemoteException" + e);
            return new ArrayList();
        }
    }

    @SystemApi
    public boolean matchesCurrentSimOperator(String str, int i, String str2) {
        ITelephony iTelephony;
        try {
            if (str.equals(getSimOperator()) && (iTelephony = getITelephony()) != null) {
                return iTelephony.isMvnoMatched(getSlotIndex(), i, str2);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#matchesCurrentSimOperator RemoteException" + e);
        }
        return false;
    }

    /* renamed from: android.telephony.TelephonyManager$14, reason: invalid class name */
    class AnonymousClass14 extends ICallForwardingInfoCallback.Stub {
        final /* synthetic */ CallForwardingInfoCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass14(TelephonyManager telephonyManager, Executor executor, CallForwardingInfoCallback callForwardingInfoCallback) {
            this.val$executor = executor;
            this.val$callback = callForwardingInfoCallback;
        }

        @Override // com.android.internal.telephony.ICallForwardingInfoCallback
        public void onCallForwardingInfoAvailable(final CallForwardingInfo callForwardingInfo) {
            Executor executor = this.val$executor;
            final CallForwardingInfoCallback callForwardingInfoCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$14$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$14$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            TelephonyManager.CallForwardingInfoCallback.this.onCallForwardingInfoAvailable(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.ICallForwardingInfoCallback
        public void onError(final int i) {
            Executor executor = this.val$executor;
            final CallForwardingInfoCallback callForwardingInfoCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$14$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$14$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            TelephonyManager.CallForwardingInfoCallback.this.onError(r2);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void getCallForwarding(int i, Executor executor, CallForwardingInfoCallback callForwardingInfoCallback) {
        if (i < 0 || i > 5) {
            throw new IllegalArgumentException("callForwardingReason is out of range");
        }
        AnonymousClass14 anonymousClass14 = new AnonymousClass14(this, executor, callForwardingInfoCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.getCallForwarding(getSubId(), i, anonymousClass14);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCallForwarding RemoteException", e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void setCallForwarding(CallForwardingInfo callForwardingInfo, Executor executor, Consumer<Integer> consumer) {
        if (callForwardingInfo == null) {
            throw new IllegalArgumentException("callForwardingInfo is null");
        }
        int reason = callForwardingInfo.getReason();
        if (reason < 0 || reason > 5) {
            throw new IllegalArgumentException("callForwardingReason is out of range");
        }
        if (callForwardingInfo.isEnabled()) {
            if (callForwardingInfo.getNumber() == null) {
                throw new IllegalArgumentException("callForwarding number is null");
            }
            if (reason == 2 && callForwardingInfo.getTimeoutSeconds() <= 0) {
                throw new IllegalArgumentException("callForwarding timeout isn't positive");
            }
        }
        if (consumer != null) {
            Objects.requireNonNull(executor);
        }
        AnonymousClass15 anonymousClass15 = new AnonymousClass15(this, executor, consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCallForwarding(getSubId(), callForwardingInfo, anonymousClass15);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setCallForwarding RemoteException", e);
            e.rethrowAsRuntimeException();
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setCallForwarding NPE", e2);
            throw e2;
        }
    }

    /* renamed from: android.telephony.TelephonyManager$15, reason: invalid class name */
    class AnonymousClass15 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass15(TelephonyManager telephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$15$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$15$$ExternalSyntheticLambda1
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
    public void getCallWaitingStatus(Executor executor, Consumer<Integer> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        AnonymousClass16 anonymousClass16 = new AnonymousClass16(this, executor, consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.getCallWaitingStatus(getSubId(), anonymousClass16);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCallWaitingStatus RemoteException", e);
            e.rethrowAsRuntimeException();
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCallWaitingStatus NPE", e2);
            throw e2;
        }
    }

    /* renamed from: android.telephony.TelephonyManager$16, reason: invalid class name */
    class AnonymousClass16 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass16(TelephonyManager telephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$16$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$16$$ExternalSyntheticLambda0
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
    public void setCallWaitingEnabled(boolean z, Executor executor, Consumer<Integer> consumer) {
        if (consumer != null) {
            Objects.requireNonNull(executor);
        }
        AnonymousClass17 anonymousClass17 = new AnonymousClass17(this, executor, consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCallWaitingStatus(getSubId(), z, anonymousClass17);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setCallWaitingStatus RemoteException", e);
            e.rethrowAsRuntimeException();
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setCallWaitingStatus NPE", e2);
            throw e2;
        }
    }

    /* renamed from: android.telephony.TelephonyManager$17, reason: invalid class name */
    class AnonymousClass17 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass17(TelephonyManager telephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$17$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$17$$ExternalSyntheticLambda0
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
    public void setMobileDataPolicyEnabled(int i, boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setMobileDataPolicyEnabled(getSubId(), i, z);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#setMobileDataPolicyEnabled RemoteException" + e);
        }
    }

    @SystemApi
    public boolean isMobileDataPolicyEnabled(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isMobileDataPolicyEnabled(getSubId(), i);
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#isMobileDataPolicyEnabled RemoteException" + e);
            return false;
        }
    }

    @SystemApi
    public boolean isIccLockEnabled() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isIccLockEnabled(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "isIccLockEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @SystemApi
    public PinResult setIccLockEnabled(boolean z, String str) {
        Preconditions.checkNotNull(str, "setIccLockEnabled pin can't be null.");
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                int iccLockEnabled = iTelephony.setIccLockEnabled(getSubId(), z, str);
                if (iccLockEnabled == Integer.MAX_VALUE) {
                    return new PinResult(0, 0);
                }
                if (iccLockEnabled < 0) {
                    return PinResult.getDefaultFailedResult();
                }
                return new PinResult(1, iccLockEnabled);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "setIccLockEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return PinResult.getDefaultFailedResult();
        }
    }

    @SystemApi
    public PinResult changeIccLockPin(String str, String str2) {
        Preconditions.checkNotNull(str, "changeIccLockPin oldPin can't be null.");
        Preconditions.checkNotNull(str2, "changeIccLockPin newPin can't be null.");
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                int changeIccLockPassword = iTelephony.changeIccLockPassword(getSubId(), str, str2);
                if (changeIccLockPassword == Integer.MAX_VALUE) {
                    return new PinResult(0, 0);
                }
                if (changeIccLockPassword < 0) {
                    return PinResult.getDefaultFailedResult();
                }
                return new PinResult(1, changeIccLockPassword);
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
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.userActivity();
            }
        } catch (RemoteException e) {
            Log.w(TAG, "notifyUserActivity exception: " + e.getMessage());
        }
    }

    @SystemApi
    public int setNrDualConnectivityState(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setNrDualConnectivityState(getSubId(), i);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setNrDualConnectivityState RemoteException", e);
            e.rethrowFromSystemServer();
            return 4;
        }
    }

    @SystemApi
    public boolean isNrDualConnectivityEnabled() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isNrDualConnectivityEnabled(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isNRDualConnectivityEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    private static class DeathRecipient implements IBinder.DeathRecipient {
        private DeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            TelephonyManager.resetServiceCache();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void resetServiceCache() {
        synchronized (sCacheLock) {
            ITelephony iTelephony = sITelephony;
            if (iTelephony != null) {
                iTelephony.asBinder().unlinkToDeath(sServiceDeath, 0);
                sITelephony = null;
            }
            ISemTelephony iSemTelephony = sISemTelephony;
            if (iSemTelephony != null) {
                iSemTelephony.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISemTelephony = null;
            }
            ISub iSub = sISub;
            if (iSub != null) {
                iSub.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISub = null;
                SubscriptionManager.clearCaches();
            }
            ISms iSms = sISms;
            if (iSms != null) {
                iSms.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISms = null;
            }
            IPhoneSubInfo iPhoneSubInfo = sIPhoneSubInfo;
            if (iPhoneSubInfo != null) {
                iPhoneSubInfo.asBinder().unlinkToDeath(sServiceDeath, 0);
                sIPhoneSubInfo = null;
            }
            ISemPhoneSubInfo iSemPhoneSubInfo = sISemPhoneSubInfo;
            if (iSemPhoneSubInfo != null) {
                iSemPhoneSubInfo.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISemPhoneSubInfo = null;
            }
        }
    }

    static IPhoneSubInfo getSubscriberInfoService() {
        if (!sServiceHandleCacheEnabled) {
            return IPhoneSubInfo.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getPhoneSubServiceRegisterer().get());
        }
        if (sIPhoneSubInfo == null) {
            IPhoneSubInfo asInterface = IPhoneSubInfo.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getPhoneSubServiceRegisterer().get());
            synchronized (sCacheLock) {
                if (sIPhoneSubInfo == null && asInterface != null) {
                    try {
                        sIPhoneSubInfo = asInterface;
                        asInterface.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception unused) {
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
            ISemPhoneSubInfo asInterface = ISemPhoneSubInfo.Stub.asInterface(ServiceManager.getService("isemphonesubinfo"));
            synchronized (sCacheLock) {
                if (sISemPhoneSubInfo == null && asInterface != null) {
                    try {
                        sISemPhoneSubInfo = asInterface;
                        asInterface.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception unused) {
                        sISemPhoneSubInfo = null;
                    }
                }
            }
        }
        return sISemPhoneSubInfo;
    }

    static ISub getSubscriptionService() {
        if (!sServiceHandleCacheEnabled) {
            return ISub.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getSubscriptionServiceRegisterer().get());
        }
        if (sISub == null) {
            ISub asInterface = ISub.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getSubscriptionServiceRegisterer().get());
            synchronized (sCacheLock) {
                if (sISub == null && asInterface != null) {
                    try {
                        sISub = asInterface;
                        asInterface.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception unused) {
                        sISub = null;
                    }
                }
            }
        }
        return sISub;
    }

    static ISms getSmsService() {
        if (!sServiceHandleCacheEnabled) {
            return ISms.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getSmsServiceRegisterer().get());
        }
        if (sISms == null) {
            ISms asInterface = ISms.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getSmsServiceRegisterer().get());
            synchronized (sCacheLock) {
                if (sISms == null && asInterface != null) {
                    try {
                        sISms = asInterface;
                        asInterface.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception unused) {
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

    public static void setupITelephonyForTest(ITelephony iTelephony) {
        sITelephony = iTelephony;
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

    public static void setupISmsForTest(ISms iSms) {
        synchronized (sCacheLock) {
            sISms = iSms;
        }
    }

    public boolean canConnectTo5GInDsdsMode() {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return true;
        }
        try {
            return iTelephony.canConnectTo5GInDsdsMode();
        } catch (RemoteException | NullPointerException unused) {
            return true;
        }
    }

    public List<String> getEquivalentHomePlmns() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getEquivalentHomePlmns(getSubId(), this.mContext.getOpPackageName(), getAttributionTag());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#getEquivalentHomePlmns RemoteException" + e);
            return Collections.EMPTY_LIST;
        }
    }

    public boolean isRadioInterfaceCapabilitySupported(String str) {
        if (str == null) {
            return false;
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isRadioInterfaceCapabilitySupported(str);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#isRadioInterfaceCapabilitySupported RemoteException" + e);
            return false;
        }
    }

    @SystemApi
    public int sendThermalMitigationRequest(ThermalMitigationRequest thermalMitigationRequest) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.sendThermalMitigationRequest(getSubId(), thermalMitigationRequest, getOpPackageName());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            Log.e(TAG, "Telephony#thermalMitigationRequest RemoteException", e);
            e.rethrowFromSystemServer();
            return 4;
        }
    }

    public void registerTelephonyCallback(Executor executor, TelephonyCallback telephonyCallback) {
        registerTelephonyCallback(getLocationData(), executor, telephonyCallback);
    }

    private int getLocationData() {
        boolean contains = getRenouncedPermissions().contains(Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean contains2 = getRenouncedPermissions().contains(Manifest.permission.ACCESS_FINE_LOCATION);
        if (contains) {
            return 0;
        }
        return contains2 ? 1 : 2;
    }

    public void registerTelephonyCallback(int i, Executor executor, TelephonyCallback telephonyCallback) {
        boolean z;
        Context context = this.mContext;
        if (context == null) {
            throw new IllegalStateException("telephony service is null.");
        }
        if (executor == null || telephonyCallback == null) {
            throw new IllegalArgumentException("TelephonyCallback and executor must be non-null");
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) context.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        this.mTelephonyRegistryMgr = telephonyRegistryManager;
        if (telephonyRegistryManager != null) {
            boolean z2 = false;
            if (i != 2) {
                z = false;
                z2 = true;
            } else {
                z = false;
            }
            telephonyRegistryManager.registerTelephonyCallback(z2, i == 0 ? true : z, executor, this.mSubId, getOpPackageName(), getAttributionTag(), telephonyCallback, getITelephony() != null ? true : z);
            return;
        }
        throw new IllegalStateException("telephony service is null.");
    }

    public void unregisterTelephonyCallback(TelephonyCallback telephonyCallback) {
        if (this.mContext == null) {
            throw new IllegalStateException("telephony service is null.");
        }
        if (telephonyCallback.callback == null) {
            return;
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(TelephonyRegistryManager.class);
        this.mTelephonyRegistryMgr = telephonyRegistryManager;
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.unregisterTelephonyCallback(this.mSubId, getOpPackageName(), getAttributionTag(), telephonyCallback, getITelephony() != null);
            return;
        }
        throw new IllegalStateException("telephony service is null.");
    }

    @SystemApi
    public void bootstrapAuthenticationRequest(int i, Uri uri, UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier, boolean z, Executor executor, final BootstrapAuthenticationCallback bootstrapAuthenticationCallback) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyManager.BootstrapAuthenticationCallback.this.onAuthenticationFailure(2);
                    }
                });
            } else {
                iTelephony.bootstrapAuthenticationRequest(getSubId(), i, uri, uaSecurityProtocolIdentifier, z, new AnonymousClass18(this, executor, bootstrapAuthenticationCallback));
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#bootstrapAuthenticationRequest", e);
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyManager.BootstrapAuthenticationCallback.this.onAuthenticationFailure(2);
                }
            });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$18, reason: invalid class name */
    class AnonymousClass18 extends IBootstrapAuthenticationCallback.Stub {
        final /* synthetic */ BootstrapAuthenticationCallback val$callback;
        final /* synthetic */ Executor val$e;

        AnonymousClass18(TelephonyManager telephonyManager, Executor executor, BootstrapAuthenticationCallback bootstrapAuthenticationCallback) {
            this.val$e = executor;
            this.val$callback = bootstrapAuthenticationCallback;
        }

        @Override // android.telephony.IBootstrapAuthenticationCallback
        public void onKeysAvailable(int i, final byte[] bArr, final String str) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$e;
                final BootstrapAuthenticationCallback bootstrapAuthenticationCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$18$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyManager.BootstrapAuthenticationCallback.this.onKeysAvailable(bArr, str);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.IBootstrapAuthenticationCallback
        public void onAuthenticationFailure(int i, final int i2) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$e;
                final BootstrapAuthenticationCallback bootstrapAuthenticationCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$18$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyManager.BootstrapAuthenticationCallback.this.onAuthenticationFailure(i2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void setSignalStrengthUpdateRequest(SignalStrengthUpdateRequest signalStrengthUpdateRequest) {
        Objects.requireNonNull(signalStrengthUpdateRequest, "request must not be null");
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setSignalStrengthUpdateRequest(getSubId(), signalStrengthUpdateRequest, getOpPackageName());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#setSignalStrengthUpdateRequest", e);
        }
    }

    public void clearSignalStrengthUpdateRequest(SignalStrengthUpdateRequest signalStrengthUpdateRequest) {
        Objects.requireNonNull(signalStrengthUpdateRequest, "request must not be null");
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.clearSignalStrengthUpdateRequest(getSubId(), signalStrengthUpdateRequest, getOpPackageName());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling ITelephony#clearSignalStrengthUpdateRequest", e);
        }
    }

    @SystemApi
    public PhoneCapability getPhoneCapability() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getPhoneCapability();
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            if (getActiveModemCount() > 1) {
                return PhoneCapability.DEFAULT_DSDS_CAPABILITY;
            }
            return PhoneCapability.DEFAULT_SSSS_CAPABILITY;
        }
    }

    @SystemApi
    public int prepareForUnattendedReboot() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.prepareForUnattendedReboot();
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
        public @interface NetworkSlicingError {
        }

        public NetworkSlicingException(int i) {
            this.mErrorCode = i;
        }

        @Override // java.lang.Throwable
        public String toString() {
            int i = this.mErrorCode;
            if (i == 1) {
                return "ERROR_TIMEOUT";
            }
            if (i == 2) {
                return "ERROR_MODEM_ERROR";
            }
            return DevicePolicyResources.UNDEFINED;
        }
    }

    public class TimeoutException extends NetworkSlicingException {
        public TimeoutException(int i) {
            super(i);
        }
    }

    public class ModemErrorException extends NetworkSlicingException {
        public ModemErrorException(int i) {
            super(i);
        }
    }

    public void getNetworkSlicingConfiguration(Executor executor, OutcomeReceiver<NetworkSlicingConfig, NetworkSlicingException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            iTelephony.getSlicingConfig(new AnonymousClass19(null, executor, outcomeReceiver));
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.TelephonyManager$19, reason: invalid class name */
    class AnonymousClass19 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass19(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveResult$0(OutcomeReceiver outcomeReceiver, int i) {
            outcomeReceiver.onError(TelephonyManager.this.new TimeoutException(i));
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 1) {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$19$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyManager.AnonymousClass19.this.lambda$onReceiveResult$0(outcomeReceiver, i);
                    }
                });
            } else if (i == 2) {
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.TelephonyManager$19$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyManager.AnonymousClass19.this.lambda$onReceiveResult$1(outcomeReceiver2, i);
                    }
                });
            } else {
                final NetworkSlicingConfig networkSlicingConfig = (NetworkSlicingConfig) bundle.getParcelable(TelephonyManager.KEY_SLICING_CONFIG_HANDLE, NetworkSlicingConfig.class);
                Executor executor3 = this.val$executor;
                final OutcomeReceiver outcomeReceiver3 = this.val$callback;
                executor3.execute(new Runnable() { // from class: android.telephony.TelephonyManager$19$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onResult(networkSlicingConfig);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveResult$1(OutcomeReceiver outcomeReceiver, int i) {
            outcomeReceiver.onError(TelephonyManager.this.new ModemErrorException(i));
        }
    }

    public static String convertPremiumCapabilityToString(int i) {
        if (i == 34) {
            return "PRIORITIZE_LATENCY";
        }
        return "UNKNOWN (" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public boolean isPremiumCapabilityAvailableForPurchase(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            return iTelephony.isPremiumCapabilityAvailableForPurchase(i, getSubId());
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    public static String convertPurchaseResultToString(int i) {
        switch (i) {
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
                return "UNKNOWN (" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public void purchasePremiumCapability(int i, Executor executor, Consumer<Integer> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        AnonymousClass20 anonymousClass20 = new AnonymousClass20(this, executor, consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                consumer.accept(11);
            } else {
                iTelephony.purchasePremiumCapability(i, anonymousClass20, getSubId());
            }
        } catch (RemoteException unused) {
            consumer.accept(11);
        }
    }

    /* renamed from: android.telephony.TelephonyManager$20, reason: invalid class name */
    class AnonymousClass20 extends IIntegerConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass20(TelephonyManager telephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$20$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(Integer.valueOf(i));
                }
            });
        }
    }

    @SystemApi
    public CellIdentity getLastKnownCellIdentity() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            return iTelephony.getLastKnownCellIdentity(getSubId(), getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return null;
        }
    }

    @SystemApi
    public void setVoiceServiceStateOverride(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("Telephony service is null");
            }
            iTelephony.setVoiceServiceStateOverride(getSubId(), z, getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void registerCarrierPrivilegesCallback(int i, Executor executor, CarrierPrivilegesCallback carrierPrivilegesCallback) {
        Context context = this.mContext;
        if (context == null) {
            throw new IllegalStateException("Telephony service is null");
        }
        if (executor == null || carrierPrivilegesCallback == null) {
            throw new IllegalArgumentException("CarrierPrivilegesCallback and executor must be non-null");
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) context.getSystemService(TelephonyRegistryManager.class);
        this.mTelephonyRegistryMgr = telephonyRegistryManager;
        if (telephonyRegistryManager == null) {
            throw new IllegalStateException("Telephony registry service is null");
        }
        telephonyRegistryManager.addCarrierPrivilegesCallback(i, executor, carrierPrivilegesCallback);
    }

    @SystemApi
    public void unregisterCarrierPrivilegesCallback(CarrierPrivilegesCallback carrierPrivilegesCallback) {
        Context context = this.mContext;
        if (context == null) {
            throw new IllegalStateException("Telephony service is null");
        }
        if (carrierPrivilegesCallback == null) {
            throw new IllegalArgumentException("CarrierPrivilegesCallback must be non-null");
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) context.getSystemService(TelephonyRegistryManager.class);
        this.mTelephonyRegistryMgr = telephonyRegistryManager;
        if (telephonyRegistryManager == null) {
            throw new IllegalStateException("Telephony registry service is null");
        }
        telephonyRegistryManager.removeCarrierPrivilegesCallback(carrierPrivilegesCallback);
    }

    public void setRemovableEsimAsDefaultEuicc(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setRemovableEsimAsDefaultEuicc(z, getOpPackageName());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error in setRemovableEsimAsDefault: " + e);
        }
    }

    public boolean isRemovableEsimDefaultEuicc() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isRemovableEsimDefaultEuicc(getOpPackageName());
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Error in isRemovableEsimDefaultEuicc: " + e);
            return false;
        }
    }

    public static int getSimStateForSlotIndex(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSimStateForSlotIndex(i);
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

            public Builder setTelecomDumpsysCollectionEnabled(boolean z) {
                this.mCollectTelecomDumpsys = z;
                return this;
            }

            public Builder setTelephonyDumpsysCollectionEnabled(boolean z) {
                this.mCollectTelephonyDumpsys = z;
                return this;
            }

            public Builder setLogcatCollectionStartTimeMillis(long j) {
                this.mLogcatStartTimeMillis = j;
                return this;
            }

            public EmergencyCallDiagnosticData build() {
                return new EmergencyCallDiagnosticData(this.mCollectTelecomDumpsys, this.mCollectTelephonyDumpsys, this.mLogcatStartTimeMillis);
            }
        }

        private EmergencyCallDiagnosticData(boolean z, boolean z2, long j) {
            this.mCollectTelecomDumpsys = z;
            this.mCollectTelephonyDumpsys = z2;
            this.mLogcatStartTimeMillis = j;
            this.mCollectLogcat = j != sUnsetLogcatStartTime;
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
            return "EmergencyCallDiagnosticData{mCollectTelecomDumpsys=" + this.mCollectTelecomDumpsys + ", mCollectTelephonyDumpsys=" + this.mCollectTelephonyDumpsys + ", mCollectLogcat=" + this.mCollectLogcat + ", mLogcatStartTimeMillis=" + this.mLogcatStartTimeMillis + '}';
        }
    }

    @SystemApi
    public void persistEmergencyCallDiagnosticData(String str, EmergencyCallDiagnosticData emergencyCallDiagnosticData) {
        try {
            ITelephony asInterface = ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
            if (asInterface != null) {
                asInterface.persistEmergencyCallDiagnosticData(str, emergencyCallDiagnosticData.isLogcatCollectionEnabled(), emergencyCallDiagnosticData.getLogcatCollectionStartTimeMillis(), emergencyCallDiagnosticData.isTelecomDumpsysCollectionEnabled(), emergencyCallDiagnosticData.isTelephonyDumpsysCollectionEnabled());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error while persistEmergencyCallDiagnosticData: " + e);
        }
    }

    public void setNullCipherAndIntegrityEnabled(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setNullCipherAndIntegrityEnabled(z);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setNullCipherAndIntegrityEnabled RemoteException", e);
            e.rethrowFromSystemServer();
        }
    }

    public boolean isNullCipherAndIntegrityPreferenceEnabled() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isNullCipherAndIntegrityPreferenceEnabled();
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isNullCipherAndIntegrityPreferenceEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return true;
        }
    }

    @SystemApi
    public void setEnableCellularIdentifierDisclosureNotifications(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setEnableCellularIdentifierDisclosureNotifications(z);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setEnableCellularIdentifierDisclosureNotifications RemoteException", e);
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isCellularIdentifierDisclosureNotificationsEnabled() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isCellularIdentifierDisclosureNotificationsEnabled();
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isCellularIdentifierDisclosureNotificationsEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @SystemApi
    public void setNullCipherNotificationsEnabled(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setNullCipherNotificationsEnabled(z);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setEnableNullCipherNotifications RemoteException", e);
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isNullCipherNotificationsEnabled() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isNullCipherNotificationsEnabled();
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isNullCipherNotificationsEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @SystemApi
    public List<CellBroadcastIdRange> getCellBroadcastIdRanges() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCellBroadcastIdRanges(getSubId());
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return new ArrayList();
        }
    }

    /* renamed from: android.telephony.TelephonyManager$21, reason: invalid class name */
    class AnonymousClass21 extends IIntegerConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass21(TelephonyManager telephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$21$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(Integer.valueOf(i));
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @SystemApi
    public void setCellBroadcastIdRanges(List<CellBroadcastIdRange> list, Executor executor, Consumer<Integer> consumer) {
        AnonymousClass21 anonymousClass21 = consumer == null ? null : new AnonymousClass21(this, executor, consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCellBroadcastIdRanges(getSubId(), list, anonymousClass21);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isDomainSelectionSupported() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isDomainSelectionSupported();
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.w(TAG, "RemoteException", e);
            return false;
        }
    }

    public boolean isAospDomainSelectionService() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isAospDomainSelectionService();
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.w(TAG, "RemoteException", e);
            return false;
        }
    }

    public String getPrimaryImei() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "getPrimaryImei(): IPhoneSubInfo instance is NULL");
                throw new IllegalStateException("Telephony service not available.");
            }
            return iTelephony.getPrimaryImei(getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getPrimaryImei() RemoteException : " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    public String getSecondaryImei() {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "getSecondaryImei(): ISemTelephony instance is NULL");
                throw new IllegalStateException("SemTelephony service not available.");
            }
            return iSemTelephony.getSecondaryImei(getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getSecondaryImei() RemoteException : " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    public String semGetSatelliteImei() {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "semGetSatelliteImei(): ISemTelephony instance is NULL");
                throw new IllegalStateException("SemTelephony service not available.");
            }
            return iSemTelephony.semGetSatelliteImei(getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semGetSatelliteImei() RemoteException : " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    public static String simStateToString(int i) {
        switch (i) {
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
                return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public void setTestEuiccUiComponent(ComponentName componentName) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "setTestEuiccUiComponent(): ITelephony instance is NULL");
                throw new IllegalStateException("Telephony service not available.");
            }
            iTelephony.setTestEuiccUiComponent(componentName);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setTestEuiccUiComponent() RemoteException : " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    public ComponentName getTestEuiccUiComponent() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "getTestEuiccUiComponent(): ITelephony instance is NULL");
                throw new IllegalStateException("Telephony service not available.");
            }
            return iTelephony.getTestEuiccUiComponent();
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getTestEuiccUiComponent() RemoteException : " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public int getCarrierIdFromCarrierIdentifier(CarrierIdentifier carrierIdentifier) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierIdFromIdentifier(carrierIdentifier);
            }
            return -1;
        } catch (RemoteException unused) {
            return -1;
        }
    }

    private void logWithCallerInfo(String str) {
        com.android.telephony.Rlog.d(TAG, str + " / caller PID: " + Process.myPid() + ", UID: " + Process.myUid() + ", TID: " + Process.myTid());
    }

    public boolean hasCall(String str) {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return false;
            }
            return semSubscriberInfoService.hasCall(str);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "hasCall is fail due to RemoteException. " + e);
            return false;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "hasCall is fail due to NullPointerException. " + e2);
            return false;
        }
    }

    public void reloadTestEmergencyNumber() {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                iSemTelephony.reloadTestEmergencyNumber();
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "reloadTestEmergencyNumber is fail due to RemoteException. " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "reloadTestEmergencyNumber is fail due to NullPointerException. " + e2);
        }
    }

    public boolean setDrxMode(int i) {
        if (Binder.getCallingUid() != 1000) {
            Log.e(TAG, "setDrxMode was accessed by non privileged user");
            throw new SecurityException("setDrxMode was accessed by non privileged user");
        }
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return false;
            }
            return semSubscriberInfoService.setDrxMode(i);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setDrxMode is fail due to RemoteException. " + e);
            return false;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setDrxMode is fail due to NullPointerException. " + e2);
            return false;
        }
    }

    public int getDrxMode() {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return 0;
            }
            return semSubscriberInfoService.getDrxMode();
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getDrxMode is fail due to RemoteException. " + e);
            return 0;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getDrxMode is fail due to NullPointerException. " + e2);
            return 0;
        }
    }

    public boolean semSetNrMode(int i) {
        return semSetNrMode(Integer.MAX_VALUE, i, false);
    }

    public boolean semSetNrMode(int i, int i2, boolean z) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.setNrMode(i, i2, z, this.mContext.getOpPackageName());
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semSetNrMode is fail due to RemoteException. " + e);
            return false;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "semSetNrMode is fail due to NullPointerException. " + e2);
            return false;
        }
    }

    public int semGetNrMode() {
        return semGetNrMode(Integer.MAX_VALUE);
    }

    public int semGetNrMode(int i) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.getNrMode(i);
            }
            return -1;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semGetNrMode is fail due to RemoteException. " + e);
            return -1;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "semGetNrMode is fail due to NullPointerException. " + e2);
            return -1;
        }
    }

    public int getSupportedRat(int i) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.getVendorConfigState(i).getDataAsInt(VendorConfigurationState.CONFIG_SUPPORTED_RAT);
            }
            return -1;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getSupportedRat is fail due to RemoteException. " + e);
            return -1;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getSupportedRat is fail due to NullPointerException. " + e2);
            return -1;
        }
    }

    public ServiceState semGetServiceState(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getServiceStateForSlot(i, false, false, getOpPackageName(), getAttributionTag());
            }
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semGetServiceState is fail due to RemoteException. " + e);
            return null;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "semGetServiceState is fail due to NullPointerException. " + e2);
            return null;
        }
    }

    public int semGetDataServiceState() {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return 0;
            }
            return semSubscriberInfoService.getDataServiceState();
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semGetDataServiceState is fail due to RemoteException. " + e);
            return 0;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "semGetDataServiceState is fail due to NullPointerException. " + e2);
            return 0;
        }
    }

    public int semGetDataServiceState(int i) {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return 0;
            }
            return semSubscriberInfoService.getDataServiceStateUsingSubId(i);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semGetDataServiceState is fail due to RemoteException. " + e);
            return 0;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "semGetDataServiceState is fail due to NullPointerException. " + e2);
            return 0;
        }
    }

    public boolean semIsVideoCall() {
        boolean z = false;
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                z = iSemTelephony.isVideoCall();
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semIsVideoCall is fail due to RemoteException. " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "semIsVideoCall is fail due to NullPointerException. " + e2);
        }
        com.android.telephony.Rlog.d(TAG, "semIsVideoCall - retVal: " + z);
        return z;
    }

    public boolean semIsInEmergencyCallbackMode() {
        return TelephonyProperties.in_ecm_mode().orElse(false).booleanValue();
    }

    public boolean semIsMmiForSubscriber(int i, String str) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.isMmiForSubscriber(i, str);
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semIsMmiForSubscriber is fail due to RemoteException. " + e);
            return false;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "semIsMmiForSubscriber is fail due to NullPointerException. " + e2);
            return false;
        }
    }

    public SemNetworkQualityInfo SemGetNetworkQualityInfo() {
        return SemGetNetworkQualityInfo(getPhoneId());
    }

    public SemNetworkQualityInfo SemGetNetworkQualityInfo(int i) {
        char c;
        String simOperatorNumericForPhone = getSimOperatorNumericForPhone(i);
        SemNetworkQualityInfo semNetworkQualityInfo = new SemNetworkQualityInfo();
        try {
            String mobileQualityInformation = getISemTelephony().getMobileQualityInformation(i, this.mContext.getOpPackageName(), getAttributionTag());
            String[] split = mobileQualityInformation.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
            try {
                String ipAddressFromLinkProp = getISemTelephony().getIpAddressFromLinkProp("MOBILE");
                if (!SemTelephonyUtils.SHIP_BUILD) {
                    com.android.telephony.Rlog.d(TAG, "mobileInfo[" + mobileQualityInformation + "] length(" + split.length + NavigationBarInflaterView.KEY_CODE_END);
                    StringBuilder sb = new StringBuilder("mobileIP[");
                    sb.append(ipAddressFromLinkProp);
                    sb.append(NavigationBarInflaterView.SIZE_MOD_END);
                    com.android.telephony.Rlog.d(TAG, sb.toString());
                }
                if (TelephonyFeatures.isMainOperatorSpecific(i, "SKT")) {
                    semNetworkQualityInfo.put("ip", ipAddressFromLinkProp, "2");
                } else if (TelephonyFeatures.isMainOperatorSpecific(i, "KTT")) {
                    semNetworkQualityInfo.put("internet_ip", ipAddressFromLinkProp, "2");
                }
                for (String str : split) {
                    String[] split2 = str.split(":");
                    if (SemTelephonyUtils.SHIP_BUILD) {
                        c = 0;
                    } else {
                        c = 0;
                        com.android.telephony.Rlog.d(TAG, "getMobileQualityInfo elements[" + split2[0] + ":" + split2[1] + ":" + split2[2] + NavigationBarInflaterView.SIZE_MOD_END);
                    }
                    if (TelephonyFeatures.isMainOperatorSpecific(i, "SKT") || (TelephonyFeatures.isMainOperatorSpecific(i, "KOO") && simOperatorNumericForPhone.equals("45005"))) {
                        split2[c] = changeNetworkInformationString(split2[c]);
                    } else {
                        split2[2] = "2";
                    }
                    semNetworkQualityInfo.put(split2[c], split2[1], split2[2]);
                }
                return semNetworkQualityInfo;
            } catch (RemoteException e) {
                com.android.telephony.Rlog.e(TAG, "getIpAddressFromLinkProp() - RemoteException occured : " + e);
                return null;
            }
        } catch (RemoteException e2) {
            com.android.telephony.Rlog.e(TAG, "getMobileQualityInformation() - RemoteException occured : " + e2);
            return null;
        }
    }

    private String changeNetworkInformationString(String str) {
        String lowerCase = str.toLowerCase(Locale.ROOT);
        if (lowerCase.equals("earfcn")) {
            lowerCase = "earfcn_downlink";
        } else if (lowerCase.equals("earfcn_ul")) {
            lowerCase = "earfcn_uplink";
        } else if (lowerCase.equals("pusch")) {
            lowerCase = "tx_power";
        } else if (lowerCase.equals("rrc_state")) {
            lowerCase = "rrc";
        } else if (lowerCase.equals("s1_ca")) {
            lowerCase = Credentials.CERTIFICATE_USAGE_CA;
        } else if (lowerCase.startsWith("s1")) {
            lowerCase = lowerCase.replace("s1", XmlTags.TAG_SESSION);
        } else if (lowerCase.startsWith("n1")) {
            lowerCase = lowerCase.replace("n1", "neighborset");
        } else if (lowerCase.startsWith("n2")) {
            lowerCase = lowerCase.replace("n2", "neighborset").concat("_2");
        } else if (lowerCase.startsWith("n3")) {
            lowerCase = lowerCase.replace("n3", "neighborset").concat("_3");
        }
        return lowerCase.endsWith("_earfcn") ? lowerCase.replace("earfcn", "freq") : lowerCase;
    }

    public static byte[] semStringToGsm8BitPacked(String str) {
        return GsmAlphabet.stringToGsm8BitPacked(str);
    }

    public static int semCharToGsm(char c, boolean z) throws Exception {
        return GsmAlphabet.charToGsm(c, z);
    }

    public static int semFindGsmSeptetLimitIndex(String str, int i, int i2, int i3, int i4) {
        return GsmAlphabet.findGsmSeptetLimitIndex(str, i, i2, i3, i4);
    }

    public static char semConvertEachCharacter(char c) {
        return SemGsmAlphabet.convertEachCharacter(c);
    }

    public boolean semGetSdnAvailable() {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.getSdnAvailable();
            }
            return true;
        } catch (RemoteException | NullPointerException unused) {
            return true;
        }
    }

    public boolean semIsSimFdnEnabled() {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.isSimFDNEnabledForSubscriber(getSubId());
            }
            return false;
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    public void semClearMwiNotificationAndVoicemailCount(int i) {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return;
            }
            semSubscriberInfoService.clearMwiNotificationAndVoicemailCount(i, getOpPackageName());
        } catch (RemoteException | NullPointerException unused) {
        }
    }

    private String getValuefromCSC(String str, String str2, String str3, int i) {
        Node search;
        String value;
        String value2;
        if (this.mDocument == null) {
            initDocument(i);
        }
        String networkName = getNetworkName(str2, str3);
        com.android.telephony.Rlog.i(TAG, "getValuefromCSC : type = " + str + ", NetworkName = " + networkName);
        Node search2 = search("Settings.Messages.MMS");
        if (search2 == null) {
            com.android.telephony.Rlog.d(TAG, "getValuefromCSC : return null by not found Settings.Messages.MMS");
            return null;
        }
        NodeList searchList = searchList(search2, "MMSView");
        if (searchList == null) {
            com.android.telephony.Rlog.d(TAG, "getValuefromCSC : return null by not found MMSView");
            return null;
        }
        if (networkName != null && !TextUtils.isEmpty(networkName)) {
            for (int i2 = 0; i2 < searchList.getLength(); i2++) {
                if (networkName.equals(getValue(search(searchList.item(i2), "NetworkName"))) && (value2 = getValue(search(searchList.item(i2), str))) != null && !TextUtils.isEmpty(value2)) {
                    com.android.telephony.Rlog.d(TAG, "getValuefromCSC : Found " + str + " = " + value2);
                    return value2;
                }
            }
        }
        for (int i3 = 0; i3 < searchList.getLength(); i3++) {
            if ("Network Name".equals(getValue(search(searchList.item(i3), "NetworkName"))) && (value = getValue(search(searchList.item(i3), str))) != null && !TextUtils.isEmpty(value)) {
                com.android.telephony.Rlog.d(TAG, "getValuefromCSC : Default Found " + str + " = " + value);
                return value;
            }
        }
        if (searchList.getLength() < 1 || searchList.item(0) == null || (search = search(searchList.item(0), str)) == null) {
            return null;
        }
        String value3 = getValue(search);
        com.android.telephony.Rlog.d(TAG, "getValuefromCSC: " + str + " = " + value3);
        return value3;
    }

    private String getNetworkName(String str, String str2) {
        boolean z;
        com.android.telephony.Rlog.d(TAG, "getNetworkName: MCCMNC = " + str + "  gid1 = " + str2);
        if (TextUtils.isEmpty(str)) {
            com.android.telephony.Rlog.d(TAG, "getNetworkName: MCCMNC is null");
            return null;
        }
        Node search = search("GeneralInfo");
        if (search == null) {
            com.android.telephony.Rlog.d(TAG, "getNetworkName: No GenralInfo node");
            return null;
        }
        NodeList searchList = searchList(search, "NetworkInfo");
        if (searchList == null || searchList.getLength() == 0) {
            com.android.telephony.Rlog.d(TAG, "getNetworkName: No NetworkInfo node");
            return null;
        }
        for (int i = 0; i < searchList.getLength(); i++) {
            if (str.equals(getValue(search(searchList.item(i), "MCCMNC")))) {
                Node search2 = !TextUtils.isEmpty(str2) ? search(searchList.item(i), "SubsetCode") : null;
                if (search2 != null) {
                    String value = getValue(search2);
                    if (value != null) {
                        int length = value.length();
                        if (str2.length() >= length && str2.substring(0, length).equalsIgnoreCase(value)) {
                            z = true;
                            if (search2 != null || z) {
                                com.android.telephony.Rlog.d(TAG, "getNetworkName: Found matched network name by " + str);
                                return getValue(search(searchList.item(i), "NetworkName"));
                            }
                        }
                    } else {
                        continue;
                    }
                }
                z = false;
                if (search2 != null) {
                }
                com.android.telephony.Rlog.d(TAG, "getNetworkName: Found matched network name by " + str);
                return getValue(search(searchList.item(i), "NetworkName"));
            }
        }
        return null;
    }

    private Node search(String str) {
        Document document;
        if (str == null || (document = this.mDocument) == null) {
            return null;
        }
        Node documentElement = document.getDocumentElement();
        StringTokenizer stringTokenizer = new StringTokenizer(str, MediaMetrics.SEPARATOR);
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            if (documentElement == null) {
                return null;
            }
            documentElement = search(documentElement, nextToken);
        }
        return documentElement;
    }

    private static Node search(Node node, String str) {
        NodeList childNodes;
        if (node != null && (childNodes = node.getChildNodes()) != null) {
            int length = childNodes.getLength();
            for (int i = 0; i < length; i++) {
                Node item = childNodes.item(i);
                if (item.getNodeName().equals(str)) {
                    return item;
                }
            }
        }
        return null;
    }

    private NodeList searchList(Node node, String str) {
        Document document;
        if (node != null && (document = this.mDocument) != null) {
            try {
                Element createElement = document.createElement(node.getNodeName());
                NodeList childNodes = node.getChildNodes();
                if (childNodes != null) {
                    int length = childNodes.getLength();
                    for (int i = 0; i < length; i++) {
                        Node item = childNodes.item(i);
                        if (item.getNodeName().equals(str)) {
                            try {
                                createElement.appendChild(item.cloneNode(true));
                            } catch (Exception e) {
                                com.android.telephony.Rlog.e(TAG, "Exception : " + e.getMessage());
                            }
                        }
                    }
                }
                return createElement.getChildNodes();
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private static String getValue(Node node) {
        if (node == null) {
            return null;
        }
        if (node.getChildNodes().getLength() > 1) {
            StringBuilder sb = new StringBuilder();
            int length = node.getChildNodes().getLength();
            for (int i = 0; i < length; i++) {
                sb.append(node.getChildNodes().item(i).getNodeValue());
            }
            return sb.toString();
        }
        return node.getFirstChild().getNodeValue();
    }

    private void initDocument(int i) {
        com.android.telephony.Rlog.i(TAG, "initDocument");
        String customerPath = getCustomerPath(i);
        if (!TextUtils.isEmpty(customerPath) && new File(customerPath).exists()) {
            this.mDocument = load(customerPath);
        } else if (new File("/data/omc/customer.xml").exists()) {
            this.mDocument = load("/data/omc/customer.xml");
        } else {
            this.mDocument = load("/system/csc/customer.xml");
        }
    }

    private Document load(String str) {
        try {
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            File file = new File(str);
            if (!file.exists()) {
                com.android.telephony.Rlog.e(TAG, "File didn't exist");
                return null;
            }
            try {
                Document parse = newDocumentBuilder.parse(file);
                com.android.telephony.Rlog.d(TAG, "load done form : " + str);
                return parse;
            } catch (IOException | SAXException e) {
                com.android.telephony.Rlog.e(TAG, "Exception : " + e.getMessage());
                return null;
            }
        } catch (ParserConfigurationException unused) {
            com.android.telephony.Rlog.e(TAG, "ParserConfigurationException is occurred");
            return null;
        }
    }

    private static String getOmcCustomerPath() {
        String str = SemSystemProperties.get("persist.sys.omc_path", "");
        if (str == null) {
            return null;
        }
        return str + "/customer.xml";
    }

    private static String getOmcCustomerPathV2(int i) {
        String str = SemSystemProperties.get("persist.sys.omcnw_path", "");
        String str2 = SemSystemProperties.get("persist.sys.omcnw_path2", "");
        if (i == 1) {
            str = str2;
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Log.i("MMS_UA" + i, "omcNwPath =" + str);
        return str + "/customer.xml";
    }

    private static String getCustomerPath(int i) {
        String omcCustomerPathV2 = getOmcCustomerPathV2(i);
        String omcCustomerPath = getOmcCustomerPath();
        if (TextUtils.isEmpty(omcCustomerPathV2) || !new File(omcCustomerPathV2).exists()) {
            if (!TextUtils.isEmpty(omcCustomerPath) && new File(omcCustomerPath).exists()) {
                Log.i("MMS_UA" + i, "omc_path =" + omcCustomerPath);
                omcCustomerPathV2 = omcCustomerPath;
            } else {
                omcCustomerPathV2 = "/system/csc/customer.xml";
            }
        }
        Log.i("MMS_UA" + i, "customer_path =" + omcCustomerPathV2);
        return omcCustomerPathV2;
    }

    public static boolean isSelectTelecomDF() {
        return isSelecttelecomDF;
    }

    public String[] getHomePlmns() {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return null;
            }
            return semSubscriberInfoService.getHomePlmns(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public String getSubscriberIdForUiccAppType(int i) {
        return getSubscriberIdForUiccAppType(getSubId(), i);
    }

    public String getSubscriberIdForUiccAppType(int i, int i2) {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return null;
            }
            return semSubscriberInfoService.getSubscriberIdForUiccAppType(i, i2, this.mContext.getOpPackageName());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public byte[] getPsismsc() {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return null;
            }
            return semSubscriberInfoService.getPsismsc(getOpPackageName());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public byte[] getPsismsc(int i) {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return null;
            }
            return semSubscriberInfoService.getPsismscWithPhoneId(i, getOpPackageName());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public boolean isGbaSupported() {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return false;
            }
            return semSubscriberInfoService.isGbaSupported();
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    public boolean isGbaSupported(int i) {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return false;
            }
            return semSubscriberInfoService.isGbaSupportedForSubscriber(i);
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    public byte[] getRand() {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return null;
            }
            return semSubscriberInfoService.getRand(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public String getBtid() {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return null;
            }
            return semSubscriberInfoService.getBtid(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public String getKeyLifetime() {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return null;
            }
            return semSubscriberInfoService.getKeyLifetime(getSubId());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public void setGbaBootstrappingParams(byte[] bArr, String str, String str2) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                iSemTelephony.setGbaBootstrappingParams(getSubId(), bArr, str, str2);
            }
        } catch (RemoteException | NullPointerException unused) {
        }
    }

    public String getGroupIdLevel2(int i) {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                return null;
            }
            return semSubscriberInfoService.getGroupIdLevel2ForSubscriber(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    public void setEPSLOCI(byte[] bArr) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                iSemTelephony.setEPSLOCI(bArr);
            }
        } catch (RemoteException | NullPointerException unused) {
        }
    }

    public String checkCallControl(String str) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.checkCallControl(getSubId(), str);
            }
        } catch (RemoteException | NullPointerException unused) {
        }
        return str;
    }

    public void setPcoValue(int i) {
        setPcoValue(getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), i);
    }

    public void setPcoValue(int i, int i2) {
        try {
            ISemPhoneSubInfo semSubscriberInfoService = getSemSubscriberInfoService();
            if (semSubscriberInfoService == null) {
                com.android.telephony.Rlog.e(TAG, "setPcoValue error: Subscriber Info is null");
            } else {
                semSubscriberInfoService.setPcoValue(i, i2, this.mContext.getOpPackageName());
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setPcoValue RemoteException " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setPcoValue NullPointerException " + e2);
        }
    }

    public void semSetAllowDataDuringCall(int i) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                iSemTelephony.setAllowDataDuringCall(i);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semSetAllowDataDuringCall is fail due to RemoteException. " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "semSetAllowDataDuringCall is fail due to NullPointerException. " + e2);
        }
    }

    public boolean semSetVoNRMode(int i, int i2) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.setVoNRMode(i, i2);
            }
            return false;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semSetVoNRMode is fail due to RemoteException. " + e);
            return false;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "semSetVoNRMode is fail due to NullPointerException. " + e2);
            return false;
        }
    }

    public int semGetVoNRMode(int i) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.getVoNRMode(i);
            }
            return -1;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semGetVoNRMode is fail due to RemoteException. " + e);
            return -1;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "semGetVoNRMode is fail due to NullPointerException. " + e2);
            return -1;
        }
    }

    public long getNextRetryTime() {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "getNextRetryTime(): ISemTelephony instance is NULL");
                throw new IllegalStateException("SemTelephony service not available.");
            }
            long nextRetryTime = iSemTelephony.getNextRetryTime();
            com.android.telephony.Rlog.e(TAG, "getNextRetryTime(): " + nextRetryTime);
            return nextRetryTime;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getNextRetryTime() RemoteException : " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    public byte[] simCheck(int i) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "simCheck(): ISemTelephony instance is NULL");
                throw new IllegalStateException("SemTelephony service not available.");
            }
            return iSemTelephony.simCheck(i);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "simCheck() RemoteException : " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    public String getLastNetworkCountryIso() {
        return getLastNetworkCountryIso(getSlotIndex());
    }

    public String getLastNetworkCountryIso(int i) {
        if (i != Integer.MAX_VALUE) {
            try {
                if (!SubscriptionManager.isValidSlotIndex(i)) {
                    throw new IllegalArgumentException("invalid slot index " + i);
                }
            } catch (RemoteException unused) {
                return "";
            }
        }
        ISemTelephony iSemTelephony = getISemTelephony();
        if (iSemTelephony == null) {
            return "";
        }
        return iSemTelephony.getLastNetworkCountryIsoForPhone(i);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void semRequestSatelliteMode(int i, boolean z, Executor executor, final Consumer<Integer> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "semRequestSatelliteMode() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda9
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                r1.accept(2);
                            }
                        });
                    }
                });
            } else {
                iSemTelephony.semRequestSatelliteMode(i, z, new AnonymousClass22(this, executor, consumer));
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semRequestSatelliteMode is fail. " + e);
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda30
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

        AnonymousClass22(TelephonyManager telephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.TelephonyManager$22$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$22$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public SemSatelliteState semGetSatelliteState(int i) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.semGetSatelliteState(i);
            }
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semGetSatelliteState is fail. " + e);
            return null;
        }
    }

    private void resetNetworkSettings(int i) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                iSemTelephony.resetNetworkSettings(i);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "resetNetworkSettings is fail due to RemoteException. " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "resetNetworkSettings is fail due to NullPointerException. " + e2);
        }
    }

    public int getCpaiModelVersion(int i, byte[] bArr) {
        if (!TelephonyFeatures.supportCpai()) {
            com.android.telephony.Rlog.d(TAG, "getCpaiModelVersion - not support cpai");
            return -1;
        }
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.getCpaiModelVersion(i, bArr);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCpaiModelVersion is fail due to RemoteException. " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCpaiModelVersion is fail due to NullPointerException. " + e2);
        }
        return -1;
    }

    public int execCpaiModelUpdate(int i, int i2) {
        if (!TelephonyFeatures.supportCpai()) {
            com.android.telephony.Rlog.d(TAG, "execCpaiModelUpdate - not support cpai");
            return -1;
        }
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.execCpaiModelUpdate(i, i2);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "execCpaiModelUpdate is fail due to RemoteException. " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "execCpaiModelUpdate is fail due to NullPointerException. " + e2);
        }
        return -1;
    }

    public int getCpaiFeatureInfo(int i, int i2, byte[] bArr) {
        if (!TelephonyFeatures.supportCpai()) {
            com.android.telephony.Rlog.d(TAG, "getCpaiFeatureInfo - not support cpai");
            return -1;
        }
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.getCpaiFeatureInfo(i, i2, bArr);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCpaiFeatureInfo is fail due to RemoteException. " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCpaiFeatureInfo is fail due to NullPointerException. " + e2);
        }
        return -1;
    }

    public int cfrmCpaiFeatureInfo(int i, int i2, int i3, String str) {
        if (!TelephonyFeatures.supportCpai()) {
            com.android.telephony.Rlog.d(TAG, "cfrmCpaiFeatureInfo - not support cpai");
            return -1;
        }
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.cfrmCpaiFeatureInfo(i, i2, i3, str);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "cfrmCpaiFeatureInfo is fail due to RemoteException. " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "cfrmCpaiFeatureInfo is fail due to NullPointerException. " + e2);
        }
        return -1;
    }

    public int setCpaiDataGathering(int i, int i2, int i3, int i4, int i5) {
        if (!TelephonyFeatures.supportCpai()) {
            com.android.telephony.Rlog.d(TAG, "setCpaiDataGathering - not support cpai");
            return -1;
        }
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.setCpaiDataGathering(i, i2, i3, i4, i5);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setCpaiDataGathering is fail due to RemoteException. " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setCpaiDataGathering is fail due to NullPointerException. " + e2);
        }
        return -1;
    }

    public int evtCpaiDataGathering(int i, int i2, byte[] bArr) {
        if (!TelephonyFeatures.supportCpai()) {
            com.android.telephony.Rlog.d(TAG, "evtCpaiDataGathering - not support cpai");
            return -1;
        }
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.evtCpaiDataGathering(i, i2, bArr);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "evtCpaiDataGathering is fail due to RemoteException. " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "evtCpaiDataGathering is fail due to NullPointerException. " + e2);
        }
        return -1;
    }

    public int setCpaiDevAppMessage(int i, int i2, int i3, String str) {
        if (!TelephonyFeatures.supportCpai()) {
            com.android.telephony.Rlog.d(TAG, "setCpaiDevAppMessage - not support cpai");
            return -1;
        }
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.setCpaiDevAppMessage(i, i2, i3, str);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setCpaiDevAppMessage is fail due to RemoteException. " + e);
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setCpaiDevAppMessage is fail due to NullPointerException. " + e2);
        }
        return -1;
    }
}
