package android.content.pm;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.AppDetailsActivity;
import android.app.PackageDeleteObserver;
import android.app.PropertyInvalidatedCache;
import android.companion.virtual.VirtualDeviceManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.dex.ArtManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.storage.VolumeInfo;
import android.permission.PermissionManager;
import android.util.AndroidException;
import android.util.Log;
import com.android.internal.pm.parsing.PackageInfoCommonUtils;
import com.android.internal.pm.parsing.PackageParser2;
import com.android.internal.pm.parsing.PackageParserException;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.ArrayUtils;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public abstract class PackageManager {

    @SystemApi
    public static final String ACTION_REQUEST_PERMISSIONS = "android.content.pm.action.REQUEST_PERMISSIONS";

    @SystemApi
    public static final String ACTION_REQUEST_PERMISSIONS_FOR_OTHER = "android.content.pm.action.REQUEST_PERMISSIONS_FOR_OTHER";
    public static final boolean APPLY_DEFAULT_TO_DEVICE_PROTECTED_STORAGE = true;
    public static final boolean APP_ENUMERATION_ENABLED_BY_DEFAULT = true;

    @SystemApi
    public static final int APP_METADATA_SOURCE_APK = 1;

    @SystemApi
    public static final int APP_METADATA_SOURCE_INSTALLER = 2;

    @SystemApi
    public static final int APP_METADATA_SOURCE_SYSTEM_IMAGE = 3;

    @SystemApi
    public static final int APP_METADATA_SOURCE_UNKNOWN = 0;
    public static final int CERT_INPUT_RAW_X509 = 0;
    public static final int CERT_INPUT_SHA256 = 1;
    public static final int COMPONENT_ENABLED_STATE_DEFAULT = 0;
    public static final int COMPONENT_ENABLED_STATE_DISABLED = 2;
    public static final int COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED = 4;
    public static final int COMPONENT_ENABLED_STATE_DISABLED_USER = 3;
    public static final int COMPONENT_ENABLED_STATE_ENABLED = 1;

    @SystemApi
    public static final int DELETE_ALL_USERS = 2;
    public static final int DELETE_APP_SEPARATION_ONE_USER_APP = 268435456;
    public static final int DELETE_ARCHIVE = 16;
    public static final int DELETE_CHATTY = Integer.MIN_VALUE;
    public static final int DELETE_DONT_KILL_APP = 8;

    @SystemApi
    public static final int DELETE_FAILED_ABORTED = -5;
    public static final int DELETE_FAILED_APP_PINNED = -7;

    @SystemApi
    public static final int DELETE_FAILED_DEVICE_POLICY_MANAGER = -2;
    public static final int DELETE_FAILED_FOR_CHILD_PROFILE = -8;

    @SystemApi
    public static final int DELETE_FAILED_INTERNAL_ERROR = -1;

    @SystemApi
    public static final int DELETE_FAILED_OWNER_BLOCKED = -4;
    public static final int DELETE_FAILED_USED_SHARED_LIBRARY = -6;
    public static final int DELETE_FAILED_USER_RESTRICTED = -3;

    @SystemApi
    public static final int DELETE_KEEP_DATA = 1;

    @SystemApi
    public static final int DELETE_SUCCEEDED = 1;
    public static final int DELETE_SYSTEM_APP = 4;
    public static final String DISABLED_APP_CALLER_AUTO_DISABLER = "auto_disabler";
    public static final int DONT_KILL_APP = 1;
    public static final boolean ENABLE_SHARED_UID_MIGRATION = true;
    public static final String EXTRA_FAILURE_EXISTING_PACKAGE = "android.content.pm.extra.FAILURE_EXISTING_PACKAGE";
    public static final String EXTRA_FAILURE_EXISTING_PERMISSION = "android.content.pm.extra.FAILURE_EXISTING_PERMISSION";

    @Deprecated
    public static final String EXTRA_INTENT_FILTER_VERIFICATION_HOSTS = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_HOSTS";

    @Deprecated
    public static final String EXTRA_INTENT_FILTER_VERIFICATION_ID = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_ID";

    @Deprecated
    public static final String EXTRA_INTENT_FILTER_VERIFICATION_PACKAGE_NAME = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_PACKAGE_NAME";

    @Deprecated
    public static final String EXTRA_INTENT_FILTER_VERIFICATION_URI_SCHEME = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_URI_SCHEME";
    public static final int EXTRA_KNOX_GET_ONLY_PREFERRED = 131072;
    public static final String EXTRA_MOVE_ID = "android.content.pm.extra.MOVE_ID";
    public static final String EXTRA_PACKAGE_MONITOR_CALLBACK_RESULT = "android.content.pm.extra.EXTRA_PACKAGE_MONITOR_CALLBACK_RESULT";

    @SystemApi
    public static final String EXTRA_REQUEST_PERMISSIONS_DEVICE_ID = "android.content.pm.extra.REQUEST_PERMISSIONS_DEVICE_ID";

    @SystemApi
    public static final String EXTRA_REQUEST_PERMISSIONS_LEGACY_ACCESS_PERMISSION_NAMES = "android.content.pm.extra.REQUEST_PERMISSIONS_LEGACY_ACCESS_PERMISSION_NAMES";

    @SystemApi
    public static final String EXTRA_REQUEST_PERMISSIONS_NAMES = "android.content.pm.extra.REQUEST_PERMISSIONS_NAMES";

    @SystemApi
    public static final String EXTRA_REQUEST_PERMISSIONS_RESULTS = "android.content.pm.extra.REQUEST_PERMISSIONS_RESULTS";
    public static final String EXTRA_USER_ACTION_REQUIRED = "android.content.pm.extra.USER_ACTION_REQUIRED";
    public static final String EXTRA_VERIFICATION_ID = "android.content.pm.extra.VERIFICATION_ID";
    public static final String EXTRA_VERIFICATION_INSTALLER_PACKAGE = "android.content.pm.extra.VERIFICATION_INSTALLER_PACKAGE";
    public static final String EXTRA_VERIFICATION_INSTALLER_UID = "android.content.pm.extra.VERIFICATION_INSTALLER_UID";
    public static final String EXTRA_VERIFICATION_INSTALL_FLAGS = "android.content.pm.extra.VERIFICATION_INSTALL_FLAGS";
    public static final String EXTRA_VERIFICATION_LONG_VERSION_CODE = "android.content.pm.extra.VERIFICATION_LONG_VERSION_CODE";
    public static final String EXTRA_VERIFICATION_PACKAGE_NAME = "android.content.pm.extra.VERIFICATION_PACKAGE_NAME";
    public static final String EXTRA_VERIFICATION_RESULT = "android.content.pm.extra.VERIFICATION_RESULT";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final String EXTRA_VERIFICATION_ROOT_HASH = "android.content.pm.extra.VERIFICATION_ROOT_HASH";
    public static final String EXTRA_VERIFICATION_URI = "android.content.pm.extra.VERIFICATION_URI";

    @Deprecated
    public static final String EXTRA_VERIFICATION_VERSION_CODE = "android.content.pm.extra.VERIFICATION_VERSION_CODE";
    public static final String FEATURE_ACTIVITIES_ON_SECONDARY_DISPLAYS = "android.software.activities_on_secondary_displays";
    public static final String FEATURE_ADOPTABLE_STORAGE = "android.software.adoptable_storage";
    public static final String FEATURE_APP_COMPAT_OVERRIDES = "android.software.app_compat_overrides";
    public static final String FEATURE_APP_ENUMERATION = "android.software.app_enumeration";
    public static final String FEATURE_APP_WIDGETS = "android.software.app_widgets";
    public static final String FEATURE_ASSIST_GESTURE = "android.hardware.sensor.assist";
    public static final String FEATURE_AUDIO_LOW_LATENCY = "android.hardware.audio.low_latency";
    public static final String FEATURE_AUDIO_OUTPUT = "android.hardware.audio.output";
    public static final String FEATURE_AUDIO_PRO = "android.hardware.audio.pro";
    public static final String FEATURE_AUDIO_SPATIAL_HEADTRACKING_LOW_LATENCY = "android.hardware.audio.spatial.headtracking.low_latency";
    public static final String FEATURE_AUTOFILL = "android.software.autofill";
    public static final String FEATURE_AUTOMOTIVE = "android.hardware.type.automotive";
    public static final String FEATURE_BACKUP = "android.software.backup";
    public static final String FEATURE_BARCODE_EMULATOR = "com.sec.feature.barcode_emulator";
    public static final String FEATURE_BLUETOOTH = "android.hardware.bluetooth";
    public static final String FEATURE_BLUETOOTH_LE = "android.hardware.bluetooth_le";

    @SystemApi
    public static final String FEATURE_BROADCAST_RADIO = "android.hardware.broadcastradio";
    public static final String FEATURE_CAMERA = "android.hardware.camera";
    public static final String FEATURE_CAMERA_ANY = "android.hardware.camera.any";
    public static final String FEATURE_CAMERA_AR = "android.hardware.camera.ar";
    public static final String FEATURE_CAMERA_AUTOFOCUS = "android.hardware.camera.autofocus";
    public static final String FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING = "android.hardware.camera.capability.manual_post_processing";
    public static final String FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR = "android.hardware.camera.capability.manual_sensor";
    public static final String FEATURE_CAMERA_CAPABILITY_RAW = "android.hardware.camera.capability.raw";
    public static final String FEATURE_CAMERA_CONCURRENT = "android.hardware.camera.concurrent";
    public static final String FEATURE_CAMERA_EXTERNAL = "android.hardware.camera.external";
    public static final String FEATURE_CAMERA_FLASH = "android.hardware.camera.flash";
    public static final String FEATURE_CAMERA_FRONT = "android.hardware.camera.front";
    public static final String FEATURE_CAMERA_LEVEL_FULL = "android.hardware.camera.level.full";
    public static final String FEATURE_CANT_SAVE_STATE = "android.software.cant_save_state";
    public static final String FEATURE_CAR_DISPLAY_COMPATIBILITY = "android.software.car.display_compatibility";
    public static final String FEATURE_CAR_SPLITSCREEN_MULTITASKING = "android.software.car.splitscreen_multitasking";
    public static final String FEATURE_CAR_TEMPLATES_HOST = "android.software.car.templates_host";
    public static final String FEATURE_COMMUNAL_MODE = "android.software.communal_mode";
    public static final String FEATURE_COMPANION_DEVICE_SETUP = "android.software.companion_device_setup";

    @Deprecated
    public static final String FEATURE_CONNECTION_SERVICE = "android.software.connectionservice";
    public static final String FEATURE_CONSUMER_IR = "android.hardware.consumerir";
    public static final String FEATURE_CONTEXTUAL_SEARCH_HELPER = "android.software.contextualsearch";

    @SystemApi
    public static final String FEATURE_CONTEXT_HUB = "android.hardware.context_hub";
    public static final String FEATURE_CONTROLS = "android.software.controls";
    public static final String FEATURE_COVER = "com.sec.feature.cover";
    public static final String FEATURE_COVER_CLEAR = "com.sec.feature.cover.clearcover";
    public static final String FEATURE_COVER_FLIP = "com.sec.feature.cover.flip";
    public static final String FEATURE_COVER_NFCLED = "com.sec.feature.cover.nfcledcover";
    public static final String FEATURE_COVER_SVIEW = "com.sec.feature.cover.sview";
    public static final String FEATURE_CREDENTIALS = "android.software.credentials";
    public static final String FEATURE_CTS = "android.software.cts";
    public static final String FEATURE_DEVICE_ADMIN = "android.software.device_admin";
    public static final String FEATURE_DEVICE_ID_ATTESTATION = "android.software.device_id_attestation";
    public static final String FEATURE_DEVICE_LOCK = "android.software.device_lock";
    public static final String FEATURE_DEVICE_UNIQUE_ATTESTATION = "android.hardware.device_unique_attestation";
    public static final String FEATURE_DREAM_OVERLAY = "android.software.dream_overlay";
    public static final String FEATURE_EMBEDDED = "android.hardware.type.embedded";

    @SystemApi
    public static final String FEATURE_EROFS = "android.software.erofs";

    @SystemApi
    public static final String FEATURE_EROFS_LEGACY = "android.software.erofs_legacy";
    public static final String FEATURE_ETHERNET = "android.hardware.ethernet";
    public static final String FEATURE_EXPANDED_PICTURE_IN_PICTURE = "android.software.expanded_picture_in_picture";
    public static final String FEATURE_FACE = "android.hardware.biometrics.face";
    public static final String FEATURE_FAKETOUCH = "android.hardware.faketouch";
    public static final String FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT = "android.hardware.faketouch.multitouch.distinct";
    public static final String FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND = "android.hardware.faketouch.multitouch.jazzhand";
    public static final String FEATURE_FELICA = "android.hardware.felica";
    public static final String FEATURE_FILE_BASED_ENCRYPTION = "android.software.file_based_encryption";
    public static final String FEATURE_FINGERPRINT = "android.hardware.fingerprint";
    public static final String FEATURE_FREEFORM_WINDOW_MANAGEMENT = "android.software.freeform_window_management";
    public static final String FEATURE_GAMEPAD = "android.hardware.gamepad";

    @SystemApi
    public static final String FEATURE_GAME_SERVICE = "android.software.game_service";
    public static final String FEATURE_HARDWARE_KEYSTORE = "android.hardware.hardware_keystore";
    public static final String FEATURE_HDMI_CEC = "android.hardware.hdmi.cec";
    public static final String FEATURE_HIFI_SENSORS = "android.hardware.sensor.hifi_sensors";
    public static final String FEATURE_HOME_SCREEN = "android.software.home_screen";
    public static final String FEATURE_IDENTITY_CREDENTIAL_HARDWARE = "android.hardware.identity_credential";
    public static final String FEATURE_IDENTITY_CREDENTIAL_HARDWARE_DIRECT_ACCESS = "android.hardware.identity_credential_direct_access";

    @SystemApi
    public static final String FEATURE_INCREMENTAL_DELIVERY = "android.software.incremental_delivery";
    public static final String FEATURE_INPUT_METHODS = "android.software.input_methods";
    public static final String FEATURE_IPSEC_TUNNELS = "android.software.ipsec_tunnels";
    public static final String FEATURE_IPSEC_TUNNEL_MIGRATION = "android.software.ipsec_tunnel_migration";
    public static final String FEATURE_IRIS = "android.hardware.biometrics.iris";
    public static final String FEATURE_KEYSTORE_APP_ATTEST_KEY = "android.hardware.keystore.app_attest_key";
    public static final String FEATURE_KEYSTORE_LIMITED_USE_KEY = "android.hardware.keystore.limited_use_key";
    public static final String FEATURE_KEYSTORE_SINGLE_USE_KEY = "android.hardware.keystore.single_use_key";
    public static final String FEATURE_LEANBACK = "android.software.leanback";
    public static final String FEATURE_LEANBACK_ONLY = "android.software.leanback_only";
    public static final String FEATURE_LIVE_TV = "android.software.live_tv";
    public static final String FEATURE_LIVE_WALLPAPER = "android.software.live_wallpaper";
    public static final String FEATURE_LOCATION = "android.hardware.location";
    public static final String FEATURE_LOCATION_GPS = "android.hardware.location.gps";
    public static final String FEATURE_LOCATION_NETWORK = "android.hardware.location.network";
    public static final String FEATURE_LOWPAN = "android.hardware.lowpan";
    public static final String FEATURE_MANAGED_PROFILES = "android.software.managed_users";
    public static final String FEATURE_MANAGED_USERS = "android.software.managed_users";
    public static final String FEATURE_MICROPHONE = "android.hardware.microphone";
    public static final String FEATURE_MIDI = "android.software.midi";
    public static final String FEATURE_MOTIONRECOGNITION_SERVICE = "com.sec.feature.motionrecognition_service";
    public static final String FEATURE_NFC = "android.hardware.nfc";
    public static final String FEATURE_NFC_ANY = "android.hardware.nfc.any";
    public static final String FEATURE_NFC_BEAM = "android.sofware.nfc.beam";
    public static final String FEATURE_NFC_CHARGING = "android.hardware.nfc.charging";

    @Deprecated
    public static final String FEATURE_NFC_HCE = "android.hardware.nfc.hce";
    public static final String FEATURE_NFC_HOST_CARD_EMULATION = "android.hardware.nfc.hce";
    public static final String FEATURE_NFC_HOST_CARD_EMULATION_NFCF = "android.hardware.nfc.hcef";
    public static final String FEATURE_NFC_OFF_HOST_CARD_EMULATION_ESE = "android.hardware.nfc.ese";
    public static final String FEATURE_NFC_OFF_HOST_CARD_EMULATION_UICC = "android.hardware.nfc.uicc";
    public static final String FEATURE_OPENGLES_DEQP_LEVEL = "android.software.opengles.deqp.level";
    public static final String FEATURE_OPENGLES_EXTENSION_PACK = "android.hardware.opengles.aep";
    public static final String FEATURE_PC = "android.hardware.type.pc";
    public static final String FEATURE_PICTURE_IN_PICTURE = "android.software.picture_in_picture";
    public static final String FEATURE_PRINTING = "android.software.print";
    public static final String FEATURE_RAM_LOW = "android.hardware.ram.low";
    public static final String FEATURE_RAM_NORMAL = "android.hardware.ram.normal";

    @SystemApi
    public static final String FEATURE_REBOOT_ESCROW = "android.hardware.reboot_escrow";
    public static final String FEATURE_ROTARY_ENCODER_LOW_RES = "android.hardware.rotaryencoder.lowres";
    public static final String FEATURE_SCONTEXT_LITE = "com.sec.feature.scontext_lite";
    public static final String FEATURE_SCREEN_LANDSCAPE = "android.hardware.screen.landscape";
    public static final String FEATURE_SCREEN_PORTRAIT = "android.hardware.screen.portrait";
    public static final String FEATURE_SDK_SANDBOX_WORK_PROFILE_INSTALL = "android.software.sdksandbox.sdk_install_work_profile";
    public static final String FEATURE_SECURELY_REMOVES_USERS = "android.software.securely_removes_users";
    public static final String FEATURE_SECURE_LOCK_SCREEN = "android.software.secure_lock_screen";
    public static final String FEATURE_SECURITY_MODEL_COMPATIBLE = "android.hardware.security.model.compatible";
    public static final String FEATURE_SENSORHUB = "com.sec.feature.sensorhub";
    public static final String FEATURE_SENSOR_ACCELEROMETER = "android.hardware.sensor.accelerometer";
    public static final String FEATURE_SENSOR_ACCELEROMETER_LIMITED_AXES = "android.hardware.sensor.accelerometer_limited_axes";
    public static final String FEATURE_SENSOR_ACCELEROMETER_LIMITED_AXES_UNCALIBRATED = "android.hardware.sensor.accelerometer_limited_axes_uncalibrated";
    public static final String FEATURE_SENSOR_AMBIENT_TEMPERATURE = "android.hardware.sensor.ambient_temperature";
    public static final String FEATURE_SENSOR_BAROMETER = "android.hardware.sensor.barometer";
    public static final String FEATURE_SENSOR_COMPASS = "android.hardware.sensor.compass";
    public static final String FEATURE_SENSOR_DYNAMIC_HEAD_TRACKER = "android.hardware.sensor.dynamic.head_tracker";
    public static final String FEATURE_SENSOR_GYROSCOPE = "android.hardware.sensor.gyroscope";
    public static final String FEATURE_SENSOR_GYROSCOPE_LIMITED_AXES = "android.hardware.sensor.gyroscope_limited_axes";
    public static final String FEATURE_SENSOR_GYROSCOPE_LIMITED_AXES_UNCALIBRATED = "android.hardware.sensor.gyroscope_limited_axes_uncalibrated";
    public static final String FEATURE_SENSOR_HEADING = "android.hardware.sensor.heading";
    public static final String FEATURE_SENSOR_HEART_RATE = "android.hardware.sensor.heartrate";
    public static final String FEATURE_SENSOR_HEART_RATE_ECG = "android.hardware.sensor.heartrate.ecg";
    public static final String FEATURE_SENSOR_HINGE_ANGLE = "android.hardware.sensor.hinge_angle";
    public static final String FEATURE_SENSOR_LIGHT = "android.hardware.sensor.light";
    public static final String FEATURE_SENSOR_PROXIMITY = "android.hardware.sensor.proximity";
    public static final String FEATURE_SENSOR_RELATIVE_HUMIDITY = "android.hardware.sensor.relative_humidity";
    public static final String FEATURE_SENSOR_STEP_COUNTER = "android.hardware.sensor.stepcounter";
    public static final String FEATURE_SENSOR_STEP_DETECTOR = "android.hardware.sensor.stepdetector";
    public static final String FEATURE_SE_OMAPI_ESE = "android.hardware.se.omapi.ese";
    public static final String FEATURE_SE_OMAPI_SD = "android.hardware.se.omapi.sd";
    public static final String FEATURE_SE_OMAPI_UICC = "android.hardware.se.omapi.uicc";
    public static final String FEATURE_SIP = "android.software.sip";
    public static final String FEATURE_SIP_VOIP = "android.software.sip.voip";
    public static final String FEATURE_SLICES_DISABLED = "android.software.slices_disabled";
    public static final String FEATURE_STRONGBOX_KEYSTORE = "android.hardware.strongbox_keystore";
    public static final String FEATURE_TELECOM = "android.software.telecom";
    public static final String FEATURE_TELEPHONY = "android.hardware.telephony";
    public static final String FEATURE_TELEPHONY_CALLING = "android.hardware.telephony.calling";

    @SystemApi
    public static final String FEATURE_TELEPHONY_CARRIERLOCK = "android.hardware.telephony.carrierlock";
    public static final String FEATURE_TELEPHONY_CDMA = "android.hardware.telephony.cdma";
    public static final String FEATURE_TELEPHONY_DATA = "android.hardware.telephony.data";
    public static final String FEATURE_TELEPHONY_EUICC = "android.hardware.telephony.euicc";
    public static final String FEATURE_TELEPHONY_EUICC_MEP = "android.hardware.telephony.euicc.mep";
    public static final String FEATURE_TELEPHONY_GSM = "android.hardware.telephony.gsm";
    public static final String FEATURE_TELEPHONY_IMS = "android.hardware.telephony.ims";

    @SystemApi
    public static final String FEATURE_TELEPHONY_IMS_SINGLE_REGISTRATION = "android.hardware.telephony.ims.singlereg";
    public static final String FEATURE_TELEPHONY_MBMS = "android.hardware.telephony.mbms";
    public static final String FEATURE_TELEPHONY_MESSAGING = "android.hardware.telephony.messaging";
    public static final String FEATURE_TELEPHONY_RADIO_ACCESS = "android.hardware.telephony.radio.access";
    public static final String FEATURE_TELEPHONY_SATELLITE = "android.hardware.telephony.satellite";
    public static final String FEATURE_TELEPHONY_SUBSCRIPTION = "android.hardware.telephony.subscription";

    @Deprecated
    public static final String FEATURE_TELEVISION = "android.hardware.type.television";
    public static final String FEATURE_THREAD_NETWORK = "android.hardware.thread_network";
    public static final String FEATURE_TOUCHSCREEN = "android.hardware.touchscreen";
    public static final String FEATURE_TOUCHSCREEN_MULTITOUCH = "android.hardware.touchscreen.multitouch";
    public static final String FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT = "android.hardware.touchscreen.multitouch.distinct";
    public static final String FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND = "android.hardware.touchscreen.multitouch.jazzhand";
    public static final String FEATURE_TUNER = "android.hardware.tv.tuner";
    public static final String FEATURE_USB_ACCESSORY = "android.hardware.usb.accessory";
    public static final String FEATURE_USB_HOST = "android.hardware.usb.host";
    public static final String FEATURE_UWB = "android.hardware.uwb";
    public static final String FEATURE_VERIFIED_BOOT = "android.software.verified_boot";

    @SystemApi
    public static final String FEATURE_VIRTUALIZATION_FRAMEWORK = "android.software.virtualization_framework";
    public static final String FEATURE_VOICE_RECOGNIZERS = "android.software.voice_recognizers";
    public static final String FEATURE_VR_HEADTRACKING = "android.hardware.vr.headtracking";

    @Deprecated
    public static final String FEATURE_VR_MODE = "android.software.vr.mode";
    public static final String FEATURE_VR_MODE_HIGH_PERFORMANCE = "android.hardware.vr.high_performance";
    public static final String FEATURE_VULKAN_DEQP_LEVEL = "android.software.vulkan.deqp.level";
    public static final String FEATURE_VULKAN_HARDWARE_COMPUTE = "android.hardware.vulkan.compute";
    public static final String FEATURE_VULKAN_HARDWARE_LEVEL = "android.hardware.vulkan.level";
    public static final String FEATURE_VULKAN_HARDWARE_VERSION = "android.hardware.vulkan.version";
    public static final String FEATURE_WALLET_LOCATION_BASED_SUGGESTIONS = "android.software.wallet_location_based_suggestions";
    public static final String FEATURE_WATCH = "android.hardware.type.watch";
    public static final String FEATURE_WEBVIEW = "android.software.webview";
    public static final String FEATURE_WIFI = "android.hardware.wifi";
    public static final String FEATURE_WIFI_AWARE = "android.hardware.wifi.aware";
    public static final String FEATURE_WIFI_DIRECT = "android.hardware.wifi.direct";
    public static final String FEATURE_WIFI_PASSPOINT = "android.hardware.wifi.passpoint";
    public static final String FEATURE_WIFI_RTT = "android.hardware.wifi.rtt";
    public static final String FEATURE_WINDOW_MAGNIFICATION = "android.software.window_magnification";
    public static final long FILTER_APPLICATION_QUERY = 135549675;

    @SystemApi
    public static final int FLAGS_PERMISSION_RESERVED_PERMISSION_CONTROLLER = -268435456;
    public static final int FLAGS_PERMISSION_RESTRICTION_ANY_EXEMPT = 14336;

    @SystemApi
    public static final int FLAG_PERMISSION_APPLY_RESTRICTION = 16384;

    @SystemApi
    public static final int FLAG_PERMISSION_AUTO_REVOKED = 131072;

    @SystemApi
    public static final int FLAG_PERMISSION_GRANTED_BY_DEFAULT = 32;

    @SystemApi
    public static final int FLAG_PERMISSION_GRANTED_BY_ROLE = 32768;

    @SystemApi
    public static final int FLAG_PERMISSION_ONE_TIME = 65536;

    @SystemApi
    public static final int FLAG_PERMISSION_POLICY_FIXED = 4;

    @SystemApi
    public static final int FLAG_PERMISSION_RESTRICTION_INSTALLER_EXEMPT = 2048;

    @SystemApi
    public static final int FLAG_PERMISSION_RESTRICTION_SYSTEM_EXEMPT = 4096;

    @SystemApi
    public static final int FLAG_PERMISSION_RESTRICTION_UPGRADE_EXEMPT = 8192;

    @SystemApi
    public static final int FLAG_PERMISSION_REVIEW_REQUIRED = 64;

    @SystemApi
    public static final int FLAG_PERMISSION_REVOKED_COMPAT = 8;

    @SystemApi
    @Deprecated
    public static final int FLAG_PERMISSION_REVOKE_ON_UPGRADE = 8;

    @SystemApi
    public static final int FLAG_PERMISSION_REVOKE_WHEN_REQUESTED = 128;

    @SystemApi
    public static final int FLAG_PERMISSION_SELECTED_LOCATION_ACCURACY = 524288;

    @SystemApi
    public static final int FLAG_PERMISSION_SYSTEM_FIXED = 16;

    @SystemApi
    public static final int FLAG_PERMISSION_USER_FIXED = 2;

    @SystemApi
    public static final int FLAG_PERMISSION_USER_SENSITIVE_WHEN_DENIED = 512;

    @SystemApi
    public static final int FLAG_PERMISSION_USER_SENSITIVE_WHEN_GRANTED = 256;

    @SystemApi
    public static final int FLAG_PERMISSION_USER_SET = 1;
    public static final int FLAG_PERMISSION_WHITELIST_INSTALLER = 2;
    public static final int FLAG_PERMISSION_WHITELIST_SYSTEM = 1;
    public static final int FLAG_PERMISSION_WHITELIST_UPGRADE = 4;

    @SystemApi
    public static final int FLAG_SUSPEND_QUARANTINED = 1;
    public static final int GET_ACTIVITIES = 1;
    public static final String GET_APP_LIST_PERMISSION = "com.samsung.android.permission.GET_APP_LIST";

    @Deprecated
    public static final int GET_ATTRIBUTIONS = Integer.MIN_VALUE;
    public static final long GET_ATTRIBUTIONS_LONG = 2147483648L;
    public static final int GET_CONFIGURATIONS = 16384;

    @Deprecated
    public static final int GET_DISABLED_COMPONENTS = 512;

    @Deprecated
    public static final int GET_DISABLED_UNTIL_USED_COMPONENTS = 32768;
    public static final int GET_GIDS = 256;
    public static final int GET_INSTRUMENTATION = 16;

    @Deprecated
    public static final int GET_INTENT_FILTERS = 32;
    public static final int GET_META_DATA = 128;
    public static final int GET_PERMISSIONS = 4096;
    public static final int GET_PROVIDERS = 8;
    public static final int GET_RECEIVERS = 2;
    public static final int GET_RESOLVED_FILTER = 64;
    public static final int GET_SERVICES = 4;
    public static final int GET_SHARED_LIBRARY_FILES = 1024;

    @Deprecated
    public static final int GET_SIGNATURES = 64;
    public static final int GET_SIGNING_CERTIFICATES = 134217728;

    @Deprecated
    public static final int GET_UNINSTALLED_PACKAGES = 8192;
    public static final int GET_URI_PERMISSION_PATTERNS = 2048;
    public static final int ICON_TRAY_DEFAULT_MODE = 0;
    public static final int ICON_TRAY_SQUICLE_MODE = 1;
    public static final String INSTALLTOSDCARD_ENABLED_STATE = "installToSdCardState";
    public static final int INSTALL_ACTIVATION_FAILED = -128;
    public static final int INSTALL_ALLOCATE_AGGRESSIVE = 32768;
    public static final int INSTALL_ALLOW_DOWNGRADE = 1048576;
    public static final int INSTALL_ALLOW_TEST = 4;
    public static final int INSTALL_ALL_USERS = 64;
    public static final int INSTALL_ALL_WHITELIST_RESTRICTED_PERMISSIONS = 4194304;
    public static final int INSTALL_APEX = 131072;
    public static final int INSTALL_ARCHIVED = 134217728;
    public static final int INSTALL_BYPASS_LOW_TARGET_SDK_BLOCK = 16777216;
    public static final int INSTALL_DEVELOPMENT_FORCE_NON_STAGED_APEX_UPDATE = 1;
    public static final int INSTALL_DIRECTLY = 33554432;
    public static final int INSTALL_DISABLE_ALLOWED_APEX_UPDATE_CHECK = 8388608;
    public static final int INSTALL_DISABLE_VERIFICATION = 524288;
    public static final int INSTALL_DONT_KILL_APP = 4096;
    public static final int INSTALL_ENABLE_ROLLBACK = 262144;
    public static final int INSTALL_EXTERNAL = 8;
    public static final int INSTALL_FAILED_ABORTED = -115;
    public static final int INSTALL_FAILED_ADP_VERSION_LOCKED = -3005;

    @SystemApi
    public static final int INSTALL_FAILED_ALREADY_EXISTS = -1;
    public static final int INSTALL_FAILED_AUTH_ASKSTOKEN = -3004;
    public static final int INSTALL_FAILED_BAD_DEX_METADATA = -117;
    public static final int INSTALL_FAILED_BAD_PERMISSION_GROUP = -127;
    public static final int INSTALL_FAILED_BAD_SIGNATURE = -118;
    public static final int INSTALL_FAILED_BLOCKED_CROSS_DOWN = -3006;

    @SystemApi
    public static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;

    @SystemApi
    public static final int INSTALL_FAILED_CONTAINER_ERROR = -18;

    @SystemApi
    public static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;
    public static final int INSTALL_FAILED_DEPRECATED_SDK_VERSION = -29;

    @SystemApi
    public static final int INSTALL_FAILED_DEXOPT = -11;

    @SystemApi
    public static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;
    public static final int INSTALL_FAILED_DUPLICATE_PERMISSION = -112;
    public static final int INSTALL_FAILED_DUPLICATE_PERMISSION_GROUP = -126;

    @SystemApi
    public static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;

    @SystemApi
    public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;

    @SystemApi
    public static final int INSTALL_FAILED_INVALID_APK = -2;

    @SystemApi
    public static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;

    @SystemApi
    public static final int INSTALL_FAILED_INVALID_URI = -3;

    @SystemApi
    public static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;
    public static final int INSTALL_FAILED_MISSING_ASKSTOKEN = -3003;
    public static final int INSTALL_FAILED_MISSING_CERTIFICATION = -3002;

    @SystemApi
    public static final int INSTALL_FAILED_MISSING_FEATURE = -17;

    @SystemApi
    public static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;
    public static final int INSTALL_FAILED_MISSING_SPLIT = -28;
    public static final int INSTALL_FAILED_MULTIPACKAGE_INCONSISTENCY = -120;
    public static final int INSTALL_FAILED_MULTI_ARCH_NOT_MATCH_ALL_NATIVE_ABIS = -131;

    @SystemApi
    public static final int INSTALL_FAILED_NEWER_SDK = -14;
    public static final int INSTALL_FAILED_NO_MATCHING_ABIS = -113;

    @SystemApi
    public static final int INSTALL_FAILED_NO_SHARED_USER = -6;

    @SystemApi
    public static final int INSTALL_FAILED_OLDER_SDK = -12;
    public static final int INSTALL_FAILED_OTHER_STAGED_SESSION_IN_PROGRESS = -119;

    @SystemApi
    public static final int INSTALL_FAILED_PACKAGE_CHANGED = -23;

    @SystemApi
    public static final int INSTALL_FAILED_PERMISSION_MODEL_DOWNGRADE = -26;
    public static final int INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE = -129;
    public static final int INSTALL_FAILED_PROCESS_NOT_DEFINED = -122;
    public static final int INSTALL_FAILED_REJECTED_BY_BUILDTYPE = -3001;
    public static final int INSTALL_FAILED_REJECTED_BY_DATE = -3000;

    @SystemApi
    public static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;

    @SystemApi
    public static final int INSTALL_FAILED_SANDBOX_VERSION_DOWNGRADE = -27;
    public static final int INSTALL_FAILED_SESSION_INVALID = -116;
    public static final int INSTALL_FAILED_SHARED_LIBRARY_BAD_CERTIFICATE_DIGEST = -130;

    @SystemApi
    public static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;
    public static final int INSTALL_FAILED_SIGNATURE_NOT_APPROVED_FOR_MDM = -51;

    @SystemApi
    public static final int INSTALL_FAILED_TEST_ONLY = -15;
    public static final int INSTALL_FAILED_UID_CHANGED = -24;
    public static final int INSTALL_FAILED_UNKNOWN_SOURCES_FOR_MDM = -50;

    @SystemApi
    public static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;
    public static final int INSTALL_FAILED_USER_RESTRICTED = -111;

    @SystemApi
    public static final int INSTALL_FAILED_VERIFICATION_FAILURE = -22;

    @SystemApi
    public static final int INSTALL_FAILED_VERIFICATION_TIMEOUT = -21;
    public static final int INSTALL_FAILED_VERSION_DOWNGRADE = -25;
    public static final int INSTALL_FAILED_WRONG_INSTALLED_VERSION = -121;
    public static final int INSTALL_FORCE_PERMISSION_PROMPT = 1024;
    public static final int INSTALL_FORCE_VOLUME_UUID = 512;
    public static final int INSTALL_FROM_ADB = 32;
    public static final int INSTALL_FROM_MANAGED_USER_OR_PROFILE = 67108864;
    public static final int INSTALL_FULL_APP = 16384;
    public static final int INSTALL_GRANT_ALL_REQUESTED_PERMISSIONS = 256;
    public static final int INSTALL_IGNORE_DEXOPT_PROFILE = 268435456;
    public static final int INSTALL_INSTANT_APP = 2048;
    public static final int INSTALL_INTERNAL = 16;

    @SystemApi
    public static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;

    @SystemApi
    public static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;

    @SystemApi
    public static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;

    @SystemApi
    public static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;

    @SystemApi
    public static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;

    @SystemApi
    public static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;

    @SystemApi
    public static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;

    @SystemApi
    public static final int INSTALL_PARSE_FAILED_NOT_APK = -100;

    @SystemApi
    public static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;
    public static final int INSTALL_PARSE_FAILED_RESOURCES_ARSC_COMPRESSED = -124;
    public static final int INSTALL_PARSE_FAILED_SKIPPED = -125;

    @SystemApi
    public static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;
    public static final int INSTALL_REASON_DEVICE_RESTORE = 2;
    public static final int INSTALL_REASON_DEVICE_SETUP = 3;
    public static final int INSTALL_REASON_POLICY = 1;
    public static final int INSTALL_REASON_ROLLBACK = 5;
    public static final int INSTALL_REASON_UNKNOWN = 0;
    public static final int INSTALL_REASON_USER = 4;
    public static final int INSTALL_REPLACE_EXISTING = 2;
    public static final int INSTALL_REQUEST_DOWNGRADE = 128;
    public static final int INSTALL_REQUEST_UPDATE_OWNERSHIP = 33554432;
    public static final int INSTALL_SCENARIO_BULK = 2;
    public static final int INSTALL_SCENARIO_BULK_SECONDARY = 3;
    public static final int INSTALL_SCENARIO_DEFAULT = 0;
    public static final int INSTALL_SCENARIO_FAST = 1;
    public static final int INSTALL_SKIP_DEXOPT = 67108864;
    public static final int INSTALL_STAGED = 2097152;

    @SystemApi
    public static final int INSTALL_SUCCEEDED = 1;
    public static final int INSTALL_UNARCHIVE = 1073741824;
    public static final int INSTALL_UNARCHIVE_DRAFT = 536870912;
    public static final int INSTALL_UNKNOWN = 0;
    public static final int INSTALL_VIRTUAL_PRELOAD = 65536;

    @SystemApi
    @Deprecated
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_ALWAYS = 2;

    @SystemApi
    @Deprecated
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_ALWAYS_ASK = 4;

    @SystemApi
    @Deprecated
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_ASK = 1;

    @SystemApi
    @Deprecated
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_NEVER = 3;

    @SystemApi
    @Deprecated
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_UNDEFINED = 0;

    @SystemApi
    @Deprecated
    public static final int INTENT_FILTER_VERIFICATION_FAILURE = -1;

    @SystemApi
    @Deprecated
    public static final int INTENT_FILTER_VERIFICATION_SUCCESS = 1;

    @SystemApi
    @Deprecated
    public static final int MASK_PERMISSION_FLAGS = 255;
    public static final int MASK_PERMISSION_FLAGS_ALL = 261119;
    public static final int MATCH_ALL = 131072;

    @SystemApi
    public static final int MATCH_ANY_USER = 4194304;
    public static final int MATCH_APEX = 1073741824;
    public static final long MATCH_ARCHIVED_PACKAGES = 4294967296L;

    @SystemApi
    @Deprecated
    public static final int MATCH_CLONE_PROFILE = 536870912;

    @SystemApi
    public static final long MATCH_CLONE_PROFILE_LONG = 17179869184L;

    @Deprecated
    public static final int MATCH_DEBUG_TRIAGED_MISSING = 268435456;
    public static final int MATCH_DEFAULT_ONLY = 65536;
    public static final int MATCH_DIRECT_BOOT_AUTO = 268435456;
    public static final int MATCH_DIRECT_BOOT_AWARE = 524288;
    public static final int MATCH_DIRECT_BOOT_UNAWARE = 262144;
    public static final int MATCH_DISABLED_AUTOMATICALLY_COMPONENTS = 536870912;
    public static final int MATCH_DISABLED_COMPONENTS = 512;
    public static final int MATCH_DISABLED_UNTIL_USED_COMPONENTS = 32768;
    public static final int MATCH_EXPLICITLY_VISIBLE_ONLY = 33554432;

    @SystemApi
    public static final int MATCH_FACTORY_ONLY = 2097152;

    @SystemApi
    public static final int MATCH_HIDDEN_UNTIL_INSTALLED_COMPONENTS = 536870912;

    @SystemApi
    public static final int MATCH_INSTANT = 8388608;
    public static final int MATCH_KNOWN_PACKAGES = 4202496;
    public static final long MATCH_QUARANTINED_COMPONENTS = 8589934592L;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int MATCH_STATIC_SHARED_AND_SDK_LIBRARIES = 67108864;
    public static final int MATCH_SYSTEM_ONLY = 1048576;
    public static final int MATCH_UNINSTALLED_PACKAGES = 8192;
    public static final int MATCH_VISIBLE_TO_INSTANT_APP_ONLY = 16777216;
    public static final long MAXIMUM_VERIFICATION_TIMEOUT = 3600000;

    @SystemApi
    public static final int MODULE_APEX_NAME = 1;

    @Deprecated
    public static final int MOVE_EXTERNAL_MEDIA = 2;
    public static final int MOVE_FAILED_3RD_PARTY_NOT_ALLOWED_ON_INTERNAL = -9;
    public static final int MOVE_FAILED_DEVICE_ADMIN = -8;
    public static final int MOVE_FAILED_DOESNT_EXIST = -2;
    public static final int MOVE_FAILED_INSUFFICIENT_STORAGE = -1;
    public static final int MOVE_FAILED_INTERNAL_ERROR = -6;
    public static final int MOVE_FAILED_INVALID_LOCATION = -5;
    public static final int MOVE_FAILED_LOCKED_USER = -10;
    public static final int MOVE_FAILED_OPERATION_PENDING = -7;
    public static final int MOVE_FAILED_SYSTEM_PACKAGE = -3;

    @Deprecated
    public static final int MOVE_INTERNAL = 1;
    public static final int MOVE_SUCCEEDED = -100;
    public static final int NOTIFY_PACKAGE_USE_ACTIVITY = 0;
    public static final int NOTIFY_PACKAGE_USE_BACKUP = 5;
    public static final int NOTIFY_PACKAGE_USE_BROADCAST_RECEIVER = 3;
    public static final int NOTIFY_PACKAGE_USE_CONTENT_PROVIDER = 4;
    public static final int NOTIFY_PACKAGE_USE_CROSS_PACKAGE = 6;
    public static final int NOTIFY_PACKAGE_USE_FOREGROUND_SERVICE = 2;
    public static final int NOTIFY_PACKAGE_USE_INSTRUMENTATION = 7;
    public static final int NOTIFY_PACKAGE_USE_REASONS_COUNT = 8;
    public static final int NOTIFY_PACKAGE_USE_SERVICE = 1;
    public static final int NO_NATIVE_LIBRARIES = -114;
    public static final int ONLY_IF_NO_MATCH_FOUND = 4;
    public static final int PERMISSION_DENIED = -1;
    public static final int PERMISSION_GRANTED = 0;
    public static final String PROPERTY_ALLOW_ADB_BACKUP = "android.backup.ALLOW_ADB_BACKUP";
    public static final String PROPERTY_ANDROID_SAFETY_LABEL = "android.content.PROPERTY_ANDROID_SAFETY_LABEL";
    public static final String PROPERTY_COMPAT_OVERRIDE_LANDSCAPE_TO_PORTRAIT = "android.camera.PROPERTY_COMPAT_OVERRIDE_LANDSCAPE_TO_PORTRAIT";
    public static final String PROPERTY_LEGACY_UPDATE_OWNERSHIP_DENYLIST = "android.app.PROPERTY_LEGACY_UPDATE_OWNERSHIP_DENYLIST";
    public static final String PROPERTY_MEDIA_CAPABILITIES = "android.media.PROPERTY_MEDIA_CAPABILITIES";
    public static final String PROPERTY_NO_APP_DATA_STORAGE = "android.internal.PROPERTY_NO_APP_DATA_STORAGE";
    public static final String PROPERTY_SELF_CERTIFIED_NETWORK_CAPABILITIES = "android.net.PROPERTY_SELF_CERTIFIED_NETWORK_CAPABILITIES";
    public static final String PROPERTY_SPECIAL_USE_FGS_SUBTYPE = "android.app.PROPERTY_SPECIAL_USE_FGS_SUBTYPE";

    @SystemApi
    public static final int RESTRICTION_HIDE_FROM_SUGGESTIONS = 1;

    @SystemApi
    public static final int RESTRICTION_HIDE_NOTIFICATIONS = 2;

    @SystemApi
    public static final int RESTRICTION_NONE = 0;

    @SystemApi
    public static final int ROLLBACK_DATA_POLICY_RESTORE = 0;

    @SystemApi
    public static final int ROLLBACK_DATA_POLICY_RETAIN = 2;

    @SystemApi
    public static final int ROLLBACK_DATA_POLICY_WIPE = 1;

    @SystemApi
    public static final int ROLLBACK_USER_IMPACT_HIGH = 1;

    @SystemApi
    public static final int ROLLBACK_USER_IMPACT_LOW = 0;

    @SystemApi
    public static final int ROLLBACK_USER_IMPACT_ONLY_MANUAL = 2;
    public static final int SEM_FEATURE_APPICON_ADAPTIVEICON_SHADOW = 2;
    public static final int SEM_FEATURE_APPICON_COLOR_NO_ADAPTIVE = 4;
    public static final int SEM_FEATURE_APPICON_COLOR_ONLY_BG = 8;
    public static final int SEM_FEATURE_APPICON_HAS_ICON_CONTAINER = 1;
    public static final String SEM_FEATURE_BARCODE_EMULATOR = "com.sec.feature.barcode_emulator";
    public static final String SEM_FEATURE_COCKTAIL_PANEL = "com.sec.feature.cocktailpanel";
    public static final String SEM_FEATURE_CONTEXT_LITE = "com.sec.feature.scontext_lite";
    public static final String SEM_FEATURE_COVER_SVIEW = "com.sec.feature.cover.sview";
    public static final String SEM_FEATURE_DEVICE_CATEGORY_PHONE = "com.samsung.feature.device_category_phone";
    public static final String SEM_FEATURE_DEVICE_CATEGORY_PHONE_HIGH_END = "com.samsung.feature.device_category_phone_high_end";
    public static final String SEM_FEATURE_DEVICE_CATEGORY_PHONE_LOW_END = "com.samsung.feature.device_category_phone_low_end";
    public static final String SEM_FEATURE_DEVICE_CATEGORY_TABLET = "com.samsung.feature.device_category_tablet";
    public static final String SEM_FEATURE_DEVICE_CATEGORY_TABLET_HIGH_END = "com.samsung.feature.device_category_tablet_high_end";
    public static final String SEM_FEATURE_DEVICE_CATEGORY_TABLET_LOW_END = "com.samsung.feature.device_category_tablet_low_end";
    public static final String SEM_FEATURE_DUAL_SCREEN = "com.sec.feature.dual_lcd";

    @Deprecated
    public static final String SEM_FEATURE_FINDO = "com.sec.feature.findo";
    public static final String SEM_FEATURE_FOLDER_TYPE = "com.sec.feature.folder_type";
    public static final String SEM_FEATURE_HOVERING_UI = "com.sec.feature.hovering_ui";

    @Deprecated(forRemoval = true, since = "13.0")
    public static final String SEM_FEATURE_MIRRORLINK_FW = "com.samsung.feature.mirrorlink_fw";
    public static final String SEM_FEATURE_MOTION_RECOGNITION_SERVICE = "com.sec.feature.motionrecognition_service";
    public static final String SEM_FEATURE_MST = "com.sec.feature.support_mst";
    public static final String SEM_FEATURE_SAMSUNG_EXPERIENCE_MOBILE = "com.samsung.feature.samsung_experience_mobile";
    public static final String SEM_FEATURE_SAMSUNG_EXPERIENCE_MOBILE_LITE = "com.samsung.feature.samsung_experience_mobile_lite";
    public static final String SEM_FEATURE_SENSOR_HUB = "com.sec.feature.sensorhub";
    public static final String SEM_FEATURE_SPEN = "com.sec.feature.spen_usp";
    public static final String SEM_FEATURE_WIFI_DISPLAY = "com.sec.feature.wfd_support";
    public static final int SEM_FLAG_PERMISSION_USER_FIXED = 2;
    public static final int SEM_FLAG_PERMISSION_USER_SET = 1;
    public static final int SEM_ICON_ARCHIVED = 512;
    public static final int SEM_ICON_MASK_COLORTHEME = 32;
    public static final int SEM_ICON_MASK_DAY = 64;
    public static final int SEM_ICON_MASK_LOCKSCREEN_SHORTCUT = 256;
    public static final int SEM_ICON_MASK_NIGHT = 128;
    public static final int SEM_ICON_MASK_SQUIRCLE = 16;
    public static final int SEM_ICON_TRAY_DEFAULT_MODE = 0;
    public static final int SEM_ICON_TRAY_SQUIRCLE_MODE = 1;
    public static final int SEM_ICON_TRAY_SQUIRCLE_MODE_IGNORE_THEME = 2;
    public static final int SEM_INSTALL_FAILED_ALREADY_EXISTS = -1;
    public static final int SEM_INSTALL_FAILED_INTERNAL_ERROR = -110;
    public static final int SEM_INSTALL_FAILED_INVALID_APK = -2;
    public static final int SEM_INSTALL_FAILED_TEST_ONLY = -15;
    public static final int SEM_INSTALL_REPLACE_EXISTING = 2;
    public static final int SEM_INSTALL_SUCCEEDED = 1;
    public static final int SEM_LIVEICON_DEFAULT = 0;
    public static final int SEM_LIVEICON_LOCKSCREEN_SHORTCUT = 1;
    public static final int SEM_MASK_PERMISSION_FLAGS = 255;
    public static final int SIGNATURE_FIRST_NOT_SIGNED = -1;
    public static final int SIGNATURE_MATCH = 0;
    public static final int SIGNATURE_NEITHER_SIGNED = 1;
    public static final int SIGNATURE_NO_MATCH = -3;
    public static final int SIGNATURE_SECOND_NOT_SIGNED = -2;
    public static final int SIGNATURE_UNKNOWN_PACKAGE = -4;
    public static final int SKIP_CURRENT_PROFILE = 2;
    public static final int SYNCHRONOUS = 2;

    @SystemApi
    public static final int SYSTEM_APP_STATE_HIDDEN_UNTIL_INSTALLED_HIDDEN = 0;

    @SystemApi
    public static final int SYSTEM_APP_STATE_HIDDEN_UNTIL_INSTALLED_VISIBLE = 1;

    @SystemApi
    public static final int SYSTEM_APP_STATE_INSTALLED = 2;

    @SystemApi
    public static final int SYSTEM_APP_STATE_UNINSTALLED = 3;
    public static final String SYSTEM_SHARED_LIBRARY_SERVICES = "android.ext.services";
    public static final String SYSTEM_SHARED_LIBRARY_SHARED = "android.ext.shared";
    private static final String TAG = "PackageManager";
    private static final String TAG_SPEG = "SPEG";
    public static final int TYPE_ACTIVITY = 1;
    public static final int TYPE_APPLICATION = 5;
    public static final int TYPE_PROVIDER = 4;
    public static final int TYPE_RECEIVER = 2;
    public static final int TYPE_SERVICE = 3;
    public static final int TYPE_UNKNOWN = 0;
    public static final int UNINSTALL_REASON_UNKNOWN = 0;
    public static final int UNINSTALL_REASON_USER_TYPE = 1;
    public static final int USER_MIN_ASPECT_RATIO_16_9 = 4;
    public static final int USER_MIN_ASPECT_RATIO_3_2 = 5;
    public static final int USER_MIN_ASPECT_RATIO_4_3 = 3;
    public static final int USER_MIN_ASPECT_RATIO_APP_DEFAULT = 7;
    public static final int USER_MIN_ASPECT_RATIO_DISPLAY_SIZE = 2;
    public static final int USER_MIN_ASPECT_RATIO_FULLSCREEN = 6;
    public static final int USER_MIN_ASPECT_RATIO_SPLIT_SCREEN = 1;
    public static final int USER_MIN_ASPECT_RATIO_UNSET = 0;
    public static final int VERIFICATION_ALLOW = 1;
    public static final int VERIFICATION_ALLOW_WITHOUT_SUFFICIENT = 2;
    public static final int VERIFICATION_REJECT = -1;
    public static final int VERSION_CODE_HIGHEST = -1;
    private static final PropertyInvalidatedCache<ApplicationInfoQuery, ApplicationInfo> sApplicationInfoCache;
    private static final PropertyInvalidatedCache.AutoCorker sCacheAutoCorker;
    private static final PropertyInvalidatedCache<PackageInfoQuery, PackageInfo> sPackageInfoCache;
    public static final String APP_DETAILS_ACTIVITY_CLASS_NAME = AppDetailsActivity.class.getName();
    public static final List<Certificate> TRUST_ALL = Collections.singletonList(null);
    public static final List<Certificate> TRUST_NONE = Collections.singletonList(null);

    @Retention(RetentionPolicy.SOURCE)
    public @interface AppMetadataSource {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplicationInfoFlagsBits {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CertificateInputType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ComponentInfoFlagsBits {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ComponentType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeleteFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DevelopmentInstallFlags {
    }

    @SystemApi
    public static abstract class DexModuleRegisterCallback {
        public abstract void onDexModuleRegistered(String str, boolean z, String str2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DistractionRestriction {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnabledFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnabledState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InstallFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InstallReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InstallScenario {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InstalledModulesFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InstrumentationInfoFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModuleInfoFlags {
    }

    public @interface NotifyReason {
    }

    @FunctionalInterface
    public interface OnChecksumsReadyListener {
        void onChecksumsReady(List<ApkChecksum> list);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PackageInfoFlagsBits {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionGroupInfoFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionInfoFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionWhitelistFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PropertyLocation {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResolveInfoFlagsBits {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RollbackDataPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RollbackImpactLevel {
    }

    public interface SemFreeStorageNotifyListener {
        void onRemoveCompleted(String str, boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SignatureResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SuspendedFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemAppState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UninstallReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserMinAspectRatio {
    }

    public abstract void addCrossProfileIntentFilter(IntentFilter intentFilter, int i, int i2, int i3);

    @SystemApi
    public abstract void addOnPermissionsChangeListener(OnPermissionsChangedListener onPermissionsChangedListener);

    @Deprecated
    public abstract void addPackageToPreferred(String str);

    public abstract boolean addPermission(PermissionInfo permissionInfo);

    public abstract boolean addPermissionAsync(PermissionInfo permissionInfo);

    @Deprecated
    public abstract void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName);

    @SystemApi
    public abstract boolean arePermissionsIndividuallyControlled();

    public abstract boolean canRequestPackageInstalls();

    public abstract String[] canonicalToCurrentPackageNames(String[] strArr);

    public abstract int checkPermission(String str, String str2);

    public abstract int checkSignatures(int i, int i2);

    public abstract int checkSignatures(String str, String str2);

    public abstract void clearApplicationUserData(String str, IPackageDataObserver iPackageDataObserver);

    public abstract void clearCrossProfileIntentFilters(int i);

    public abstract void clearInstantAppCookie();

    @Deprecated
    public abstract void clearPackagePreferredActivities(String str);

    public abstract String[] currentToCanonicalPackageNames(String[] strArr);

    public abstract void deleteApplicationCacheFiles(String str, IPackageDataObserver iPackageDataObserver);

    public abstract void deleteApplicationCacheFilesAsUser(String str, int i, IPackageDataObserver iPackageDataObserver);

    public abstract void deletePackage(String str, IPackageDeleteObserver iPackageDeleteObserver, int i);

    public abstract void deletePackageAsUser(String str, IPackageDeleteObserver iPackageDeleteObserver, int i, int i2);

    public abstract void extendVerificationTimeout(int i, int i2, long j);

    public abstract void flushPackageRestrictionsAsUser(int i);

    public abstract void freeStorage(String str, long j, IntentSender intentSender);

    public abstract void freeStorageAndNotify(String str, long j, IPackageDataObserver iPackageDataObserver);

    public abstract Drawable getActivityBanner(ComponentName componentName) throws NameNotFoundException;

    public abstract Drawable getActivityBanner(Intent intent) throws NameNotFoundException;

    public abstract Drawable getActivityIcon(ComponentName componentName) throws NameNotFoundException;

    public abstract Drawable getActivityIcon(Intent intent) throws NameNotFoundException;

    public abstract ActivityInfo getActivityInfo(ComponentName componentName, int i) throws NameNotFoundException;

    public abstract Drawable getActivityLogo(ComponentName componentName) throws NameNotFoundException;

    public abstract Drawable getActivityLogo(Intent intent) throws NameNotFoundException;

    @SystemApi
    public abstract List<IntentFilter> getAllIntentFilters(String str);

    public abstract List<PermissionGroupInfo> getAllPermissionGroups(int i);

    public abstract Drawable getApplicationBanner(ApplicationInfo applicationInfo);

    public abstract Drawable getApplicationBanner(String str) throws NameNotFoundException;

    public abstract int getApplicationEnabledSetting(String str);

    public abstract boolean getApplicationHiddenSettingAsUser(String str, UserHandle userHandle);

    public abstract Drawable getApplicationIcon(ApplicationInfo applicationInfo);

    public abstract Drawable getApplicationIcon(String str) throws NameNotFoundException;

    public abstract ApplicationInfo getApplicationInfo(String str, int i) throws NameNotFoundException;

    public abstract ApplicationInfo getApplicationInfoAsUser(String str, int i, int i2) throws NameNotFoundException;

    public abstract CharSequence getApplicationLabel(ApplicationInfo applicationInfo);

    public abstract Drawable getApplicationLogo(ApplicationInfo applicationInfo);

    public abstract Drawable getApplicationLogo(String str) throws NameNotFoundException;

    public abstract Intent getCarLaunchIntentForPackage(String str);

    public abstract ChangedPackages getChangedPackages(int i);

    public abstract int getComponentEnabledSetting(ComponentName componentName);

    public abstract Drawable getDefaultActivityIcon();

    @SystemApi
    public abstract String getDefaultBrowserPackageNameAsUser(int i);

    public abstract Drawable getDrawable(String str, int i, ApplicationInfo applicationInfo);

    public abstract ComponentName getHomeActivities(List<ResolveInfo> list);

    public abstract int getInstallReason(String str, UserHandle userHandle);

    public abstract List<ApplicationInfo> getInstalledApplications(int i);

    public abstract List<ApplicationInfo> getInstalledApplicationsAsUser(int i, int i2);

    public abstract List<PackageInfo> getInstalledPackages(int i);

    @SystemApi
    public abstract List<PackageInfo> getInstalledPackagesAsUser(int i, int i2);

    @Deprecated
    public abstract String getInstallerPackageName(String str);

    public abstract String getInstantAppAndroidId(String str, UserHandle userHandle);

    public abstract byte[] getInstantAppCookie();

    public abstract int getInstantAppCookieMaxBytes();

    public abstract int getInstantAppCookieMaxSize();

    @SystemApi
    public abstract Drawable getInstantAppIcon(String str);

    @SystemApi
    public abstract ComponentName getInstantAppInstallerComponent();

    @SystemApi
    public abstract ComponentName getInstantAppResolverSettingsComponent();

    @SystemApi
    public abstract List<InstantAppInfo> getInstantApps();

    public abstract InstrumentationInfo getInstrumentationInfo(ComponentName componentName, int i) throws NameNotFoundException;

    @SystemApi
    @Deprecated
    public abstract List<IntentFilterVerificationInfo> getIntentFilterVerifications(String str);

    @SystemApi
    @Deprecated
    public abstract int getIntentVerificationStatusAsUser(String str, int i);

    public abstract KeySet getKeySetByAlias(String str, String str2);

    public abstract Intent getLaunchIntentForPackage(String str);

    public abstract Intent getLeanbackLaunchIntentForPackage(String str);

    public abstract int getMoveStatus(int i);

    public abstract String getNameForUid(int i);

    public abstract String[] getNamesForUids(int[] iArr);

    public abstract List<VolumeInfo> getPackageCandidateVolumes(ApplicationInfo applicationInfo);

    public abstract VolumeInfo getPackageCurrentVolume(ApplicationInfo applicationInfo);

    public abstract int[] getPackageGids(String str) throws NameNotFoundException;

    public abstract int[] getPackageGids(String str, int i) throws NameNotFoundException;

    public abstract PackageInfo getPackageInfo(VersionedPackage versionedPackage, int i) throws NameNotFoundException;

    public abstract PackageInfo getPackageInfo(String str, int i) throws NameNotFoundException;

    public abstract PackageInfo getPackageInfoAsUser(String str, int i, int i2) throws NameNotFoundException;

    public abstract PackageInstaller getPackageInstaller();

    @Deprecated
    public abstract void getPackageSizeInfoAsUser(String str, int i, IPackageStatsObserver iPackageStatsObserver);

    public abstract int getPackageUid(String str, int i) throws NameNotFoundException;

    public abstract int getPackageUidAsUser(String str, int i) throws NameNotFoundException;

    public abstract int getPackageUidAsUser(String str, int i, int i2) throws NameNotFoundException;

    public abstract String[] getPackagesForUid(int i);

    public abstract List<PackageInfo> getPackagesHoldingPermissions(String[] strArr, int i);

    @SystemApi
    public abstract int getPermissionFlags(String str, String str2, UserHandle userHandle);

    public abstract PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws NameNotFoundException;

    public abstract PermissionInfo getPermissionInfo(String str, int i) throws NameNotFoundException;

    @Deprecated
    public abstract int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String str);

    @Deprecated
    public abstract List<PackageInfo> getPreferredPackages(int i);

    public abstract List<VolumeInfo> getPrimaryStorageCandidateVolumes();

    public abstract VolumeInfo getPrimaryStorageCurrentVolume();

    public abstract ProviderInfo getProviderInfo(ComponentName componentName, int i) throws NameNotFoundException;

    public abstract ActivityInfo getReceiverInfo(ComponentName componentName, int i) throws NameNotFoundException;

    public abstract Resources getResourcesForActivity(ComponentName componentName) throws NameNotFoundException;

    public abstract Resources getResourcesForApplication(ApplicationInfo applicationInfo) throws NameNotFoundException;

    public abstract Resources getResourcesForApplication(String str) throws NameNotFoundException;

    @Deprecated
    public abstract Resources getResourcesForApplicationAsUser(String str, int i) throws NameNotFoundException;

    public abstract ServiceInfo getServiceInfo(ComponentName componentName, int i) throws NameNotFoundException;

    public abstract String getServicesSystemSharedLibraryPackageName();

    public abstract List<SharedLibraryInfo> getSharedLibraries(int i);

    public abstract List<SharedLibraryInfo> getSharedLibrariesAsUser(int i, int i2);

    public abstract String getSharedSystemSharedLibraryPackageName();

    public abstract KeySet getSigningKeySet(String str);

    public abstract FeatureInfo[] getSystemAvailableFeatures();

    public abstract String[] getSystemSharedLibraryNames();

    public abstract CharSequence getText(String str, int i, ApplicationInfo applicationInfo);

    public abstract int getUidForSharedUser(String str) throws NameNotFoundException;

    public abstract Drawable getUserBadgeForDensity(UserHandle userHandle, int i);

    public abstract Drawable getUserBadgeForDensityNoBackground(UserHandle userHandle, int i);

    public abstract Drawable getUserBadgedDrawableForDensity(Drawable drawable, UserHandle userHandle, Rect rect, int i);

    public abstract Drawable getUserBadgedIcon(Drawable drawable, UserHandle userHandle);

    public abstract CharSequence getUserBadgedLabel(CharSequence charSequence, UserHandle userHandle);

    public abstract VerifierDeviceIdentity getVerifierDeviceIdentity();

    public abstract XmlResourceParser getXml(String str, int i, ApplicationInfo applicationInfo);

    @SystemApi
    public abstract void grantRuntimePermission(String str, String str2, UserHandle userHandle);

    public abstract boolean hasSystemFeature(String str);

    public abstract boolean hasSystemFeature(String str, int i);

    @SystemApi
    @Deprecated
    public abstract int installExistingPackage(String str) throws NameNotFoundException;

    @SystemApi
    @Deprecated
    public abstract int installExistingPackage(String str, int i) throws NameNotFoundException;

    @Deprecated
    public abstract int installExistingPackageAsUser(String str, int i) throws NameNotFoundException;

    public abstract boolean isInstantApp();

    public abstract boolean isInstantApp(String str);

    public abstract boolean isPackageAutoDisabled(String str, int i);

    public abstract boolean isPackageAvailable(String str);

    public abstract boolean isPackageSuspendedForUser(String str, int i);

    public abstract boolean isPermissionRevokedByPolicy(String str, String str2);

    public abstract boolean isSafeMode();

    public abstract boolean isSignedBy(String str, KeySet keySet);

    public abstract boolean isSignedByExactly(String str, KeySet keySet);

    public abstract boolean isUpgrade();

    public abstract boolean isWirelessConsentModeEnabled();

    public abstract Drawable loadItemIcon(PackageItemInfo packageItemInfo, ApplicationInfo applicationInfo);

    public abstract Drawable loadUnbadgedItemIcon(PackageItemInfo packageItemInfo, ApplicationInfo applicationInfo);

    public abstract int movePackage(String str, VolumeInfo volumeInfo);

    public abstract int movePrimaryStorage(VolumeInfo volumeInfo);

    public abstract List<ResolveInfo> queryBroadcastReceivers(Intent intent, int i);

    public abstract List<ResolveInfo> queryBroadcastReceiversAsUser(Intent intent, int i, int i2);

    public abstract List<ProviderInfo> queryContentProviders(String str, int i, int i2);

    public abstract List<InstrumentationInfo> queryInstrumentation(String str, int i);

    public abstract List<ResolveInfo> queryIntentActivities(Intent intent, int i);

    public abstract List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, int i, int i2);

    public abstract List<ResolveInfo> queryIntentActivityOptions(ComponentName componentName, Intent[] intentArr, Intent intent, int i);

    public abstract List<ResolveInfo> queryIntentContentProviders(Intent intent, int i);

    public abstract List<ResolveInfo> queryIntentContentProvidersAsUser(Intent intent, int i, int i2);

    public abstract List<ResolveInfo> queryIntentServices(Intent intent, int i);

    public abstract List<ResolveInfo> queryIntentServicesAsUser(Intent intent, int i, int i2);

    public abstract List<PermissionInfo> queryPermissionsByGroup(String str, int i) throws NameNotFoundException;

    @SystemApi
    public abstract void registerDexModule(String str, DexModuleRegisterCallback dexModuleRegisterCallback);

    public abstract void registerMoveCallback(MoveCallback moveCallback, Handler handler);

    @SystemApi
    public abstract void removeOnPermissionsChangeListener(OnPermissionsChangedListener onPermissionsChangedListener);

    @Deprecated
    public abstract void removePackageFromPreferred(String str);

    public abstract void removePermission(String str);

    @Deprecated
    public abstract void replacePreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName);

    public abstract ResolveInfo resolveActivity(Intent intent, int i);

    public abstract ResolveInfo resolveActivityAsUser(Intent intent, int i, int i2);

    public abstract ProviderInfo resolveContentProvider(String str, int i);

    public abstract ProviderInfo resolveContentProviderAsUser(String str, int i, int i2);

    public abstract ResolveInfo resolveService(Intent intent, int i);

    public abstract ResolveInfo resolveServiceAsUser(Intent intent, int i, int i2);

    @SystemApi
    public abstract void revokeRuntimePermission(String str, String str2, UserHandle userHandle);

    public abstract void setApplicationCategoryHint(String str, int i);

    public abstract void setApplicationEnabledSetting(String str, int i, int i2);

    public abstract boolean setApplicationHiddenSettingAsUser(String str, boolean z, UserHandle userHandle);

    public abstract void setComponentEnabledSetting(ComponentName componentName, int i, int i2);

    @SystemApi
    public abstract boolean setDefaultBrowserPackageNameAsUser(String str, int i);

    public abstract void setInstallerPackageName(String str, String str2);

    public abstract boolean setInstantAppCookie(byte[] bArr);

    @SystemApi
    public abstract void setUpdateAvailable(String str, boolean z);

    public abstract boolean shouldShowRequestPermissionRationale(String str);

    public abstract void unregisterMoveCallback(MoveCallback moveCallback);

    public abstract void updateInstantAppCookie(byte[] bArr);

    @SystemApi
    @Deprecated
    public abstract boolean updateIntentVerificationStatusAsUser(String str, int i, int i2);

    @SystemApi
    public abstract void updatePermissionFlags(String str, String str2, int i, int i2, UserHandle userHandle);

    @SystemApi
    @Deprecated
    public abstract void verifyIntentFilter(int i, int i2, List<String> list);

    public abstract void verifyPendingInstall(int i, int i2);

    public static class NameNotFoundException extends AndroidException {
        public NameNotFoundException() {
        }

        public NameNotFoundException(String name) {
            super(name);
        }
    }

    public static final class Property implements Parcelable {
        public static final Parcelable.Creator<Property> CREATOR = new Parcelable.Creator<Property>() { // from class: android.content.pm.PackageManager.Property.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Property createFromParcel(Parcel source) {
                String name = source.readString();
                int type = source.readInt();
                String packageName = source.readString();
                String className = source.readString();
                if (type == 1) {
                    return new Property(name, source.readBoolean(), packageName, className);
                }
                if (type == 2) {
                    return new Property(name, source.readFloat(), packageName, className);
                }
                if (type == 3) {
                    return new Property(name, source.readInt(), false, packageName, className);
                }
                if (type == 4) {
                    return new Property(name, source.readInt(), true, packageName, className);
                }
                if (type == 5) {
                    return new Property(name, source.readString(), packageName, className);
                }
                return null;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Property[] newArray(int size) {
                return new Property[size];
            }
        };
        private static final int TYPE_BOOLEAN = 1;
        private static final int TYPE_FLOAT = 2;
        private static final int TYPE_INTEGER = 3;
        private static final int TYPE_RESOURCE = 4;
        private static final int TYPE_STRING = 5;
        private boolean mBooleanValue;
        private final String mClassName;
        private float mFloatValue;
        private int mIntegerValue;
        private final String mName;
        private final String mPackageName;
        private String mStringValue;
        private final int mType;

        public Property(String name, int type, String packageName, String className) {
            if (type < 1 || type > 5) {
                throw new IllegalArgumentException("Invalid type");
            }
            this.mName = (String) Objects.requireNonNull(name);
            this.mType = type;
            this.mPackageName = (String) Objects.requireNonNull(packageName);
            this.mClassName = className;
        }

        public Property(String name, boolean value, String packageName, String className) {
            this(name, 1, packageName, className);
            this.mBooleanValue = value;
        }

        public Property(String name, float value, String packageName, String className) {
            this(name, 2, packageName, className);
            this.mFloatValue = value;
        }

        public Property(String name, int value, boolean isResource, String packageName, String className) {
            this(name, isResource ? 4 : 3, packageName, className);
            this.mIntegerValue = value;
        }

        public Property(String name, String value, String packageName, String className) {
            this(name, 5, packageName, className);
            this.mStringValue = value;
        }

        public int getType() {
            return this.mType;
        }

        public String getName() {
            return this.mName;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public String getClassName() {
            return this.mClassName;
        }

        public boolean getBoolean() {
            return this.mBooleanValue;
        }

        public boolean isBoolean() {
            return this.mType == 1;
        }

        public float getFloat() {
            return this.mFloatValue;
        }

        public boolean isFloat() {
            return this.mType == 2;
        }

        public int getInteger() {
            if (this.mType == 3) {
                return this.mIntegerValue;
            }
            return 0;
        }

        public boolean isInteger() {
            return this.mType == 3;
        }

        public int getResourceId() {
            if (this.mType == 4) {
                return this.mIntegerValue;
            }
            return 0;
        }

        public boolean isResourceId() {
            return this.mType == 4;
        }

        public String getString() {
            return this.mStringValue;
        }

        public boolean isString() {
            return this.mType == 5;
        }

        public Bundle toBundle(Bundle outBundle) {
            Bundle b = (outBundle == null || outBundle == Bundle.EMPTY) ? new Bundle() : outBundle;
            if (this.mType == 1) {
                b.putBoolean(this.mName, this.mBooleanValue);
            } else if (this.mType == 2) {
                b.putFloat(this.mName, this.mFloatValue);
            } else if (this.mType == 3) {
                b.putInt(this.mName, this.mIntegerValue);
            } else if (this.mType == 4) {
                b.putInt(this.mName, this.mIntegerValue);
            } else if (this.mType == 5) {
                b.putString(this.mName, this.mStringValue);
            }
            return b;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mName);
            dest.writeInt(this.mType);
            dest.writeString(this.mPackageName);
            dest.writeString(this.mClassName);
            if (this.mType == 1) {
                dest.writeBoolean(this.mBooleanValue);
                return;
            }
            if (this.mType == 2) {
                dest.writeFloat(this.mFloatValue);
                return;
            }
            if (this.mType == 3) {
                dest.writeInt(this.mIntegerValue);
            } else if (this.mType == 4) {
                dest.writeInt(this.mIntegerValue);
            } else if (this.mType == 5) {
                dest.writeString(this.mStringValue);
            }
        }
    }

    public static final class ComponentEnabledSetting implements Parcelable {
        public static final Parcelable.Creator<ComponentEnabledSetting> CREATOR = new Parcelable.Creator<ComponentEnabledSetting>() { // from class: android.content.pm.PackageManager.ComponentEnabledSetting.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ComponentEnabledSetting[] newArray(int size) {
                return new ComponentEnabledSetting[size];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ComponentEnabledSetting createFromParcel(Parcel in) {
                return new ComponentEnabledSetting(in);
            }
        };
        private final ComponentName mComponentName;
        private final int mEnabledFlags;
        private final int mEnabledState;
        private final String mPackageName;

        public ComponentEnabledSetting(ComponentName componentName, int newState, int flags) {
            this.mPackageName = null;
            this.mComponentName = (ComponentName) Objects.requireNonNull(componentName);
            this.mEnabledState = newState;
            this.mEnabledFlags = flags;
        }

        public ComponentEnabledSetting(String packageName, int newState, int flags) {
            this.mPackageName = (String) Objects.requireNonNull(packageName);
            this.mComponentName = null;
            this.mEnabledState = newState;
            this.mEnabledFlags = flags;
        }

        public String getPackageName() {
            if (isComponent()) {
                return this.mComponentName.getPackageName();
            }
            return this.mPackageName;
        }

        public String getClassName() {
            if (isComponent()) {
                return this.mComponentName.getClassName();
            }
            return null;
        }

        public boolean isComponent() {
            return this.mComponentName != null;
        }

        public ComponentName getComponentName() {
            return this.mComponentName;
        }

        public int getEnabledState() {
            return this.mEnabledState;
        }

        public int getEnabledFlags() {
            return this.mEnabledFlags;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            byte flg = this.mPackageName != null ? (byte) (0 | 1) : (byte) 0;
            if (this.mComponentName != null) {
                flg = (byte) (flg | 2);
            }
            dest.writeByte(flg);
            if (this.mPackageName != null) {
                dest.writeString(this.mPackageName);
            }
            if (this.mComponentName != null) {
                dest.writeTypedObject(this.mComponentName, flags);
            }
            dest.writeInt(this.mEnabledState);
            dest.writeInt(this.mEnabledFlags);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        ComponentEnabledSetting(Parcel in) {
            byte flg = in.readByte();
            String packageName = (flg & 1) == 0 ? null : in.readString();
            ComponentName componentName = (flg & 2) == 0 ? null : (ComponentName) in.readTypedObject(ComponentName.CREATOR);
            int enabledState = in.readInt();
            int enabledFlags = in.readInt();
            this.mPackageName = packageName;
            this.mComponentName = componentName;
            this.mEnabledState = enabledState;
            AnnotationValidations.validate((Class<? extends Annotation>) EnabledState.class, (Annotation) null, this.mEnabledState);
            this.mEnabledFlags = enabledFlags;
            AnnotationValidations.validate((Class<? extends Annotation>) EnabledFlags.class, (Annotation) null, this.mEnabledFlags);
        }

        @Deprecated
        private void __metadata() {
        }
    }

    @SystemApi
    public interface OnPermissionsChangedListener {
        void onPermissionsChanged(int i);

        default void onPermissionsChanged(int uid, String persistentDeviceId) {
            Objects.requireNonNull(persistentDeviceId);
            if (Objects.equals(persistentDeviceId, VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT)) {
                onPermissionsChanged(uid);
            }
        }
    }

    static {
        String str = PermissionManager.CACHE_KEY_PACKAGE_INFO;
        sApplicationInfoCache = new PropertyInvalidatedCache<ApplicationInfoQuery, ApplicationInfo>(32, str, "getApplicationInfo") { // from class: android.content.pm.PackageManager.2
            @Override // android.app.PropertyInvalidatedCache
            public ApplicationInfo recompute(ApplicationInfoQuery query) {
                return PackageManager.getApplicationInfoAsUserUncached(query.packageName, query.flags, query.userId);
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean resultEquals(ApplicationInfo cached, ApplicationInfo fetched) {
                return true;
            }
        };
        sCacheAutoCorker = new PropertyInvalidatedCache.AutoCorker(PermissionManager.CACHE_KEY_PACKAGE_INFO);
        sPackageInfoCache = new PropertyInvalidatedCache<PackageInfoQuery, PackageInfo>(64, str, "getPackageInfo") { // from class: android.content.pm.PackageManager.3
            @Override // android.app.PropertyInvalidatedCache
            public PackageInfo recompute(PackageInfoQuery query) {
                return PackageManager.getPackageInfoAsUserUncached(query.packageName, query.flags, query.userId);
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean resultEquals(PackageInfo cached, PackageInfo fetched) {
                return true;
            }
        };
    }

    public static class Flags {
        final long mValue;

        protected Flags(long value) {
            this.mValue = value;
        }

        public long getValue() {
            return this.mValue;
        }
    }

    public static final class PackageInfoFlags extends Flags {
        private PackageInfoFlags(long value) {
            super(value);
        }

        public static PackageInfoFlags of(long value) {
            return new PackageInfoFlags(value);
        }
    }

    public static final class ApplicationInfoFlags extends Flags {
        private ApplicationInfoFlags(long value) {
            super(value);
        }

        public static ApplicationInfoFlags of(long value) {
            return new ApplicationInfoFlags(value);
        }
    }

    public static final class ComponentInfoFlags extends Flags {
        private ComponentInfoFlags(long value) {
            super(value);
        }

        public static ComponentInfoFlags of(long value) {
            return new ComponentInfoFlags(value);
        }
    }

    public static final class ResolveInfoFlags extends Flags {
        private ResolveInfoFlags(long value) {
            super(value);
        }

        public static ResolveInfoFlags of(long value) {
            return new ResolveInfoFlags(value);
        }
    }

    public int getUserId() {
        return UserHandle.myUserId();
    }

    @Deprecated
    public PackageManager() {
    }

    public PackageInfo getPackageInfo(String packageName, PackageInfoFlags flags) throws NameNotFoundException {
        throw new UnsupportedOperationException("getPackageInfo not implemented in subclass");
    }

    public PackageInfo getPackageInfo(VersionedPackage versionedPackage, PackageInfoFlags flags) throws NameNotFoundException {
        throw new UnsupportedOperationException("getPackageInfo not implemented in subclass");
    }

    public PackageInfo getPackageInfoAsUser(String packageName, PackageInfoFlags flags, int userId) throws NameNotFoundException {
        throw new UnsupportedOperationException("getPackageInfoAsUser not implemented in subclass");
    }

    public IntentSender getLaunchIntentSenderForPackage(String packageName) {
        throw new UnsupportedOperationException("getLaunchIntentSenderForPackage not implementedin subclass");
    }

    public int[] getPackageGids(String packageName, PackageInfoFlags flags) throws NameNotFoundException {
        throw new UnsupportedOperationException("getPackageGids not implemented in subclass");
    }

    public int getPackageUid(String packageName, PackageInfoFlags flags) throws NameNotFoundException {
        throw new UnsupportedOperationException("getPackageUid not implemented in subclass");
    }

    @SystemApi
    public int getPackageUidAsUser(String packageName, PackageInfoFlags flags, int userId) throws NameNotFoundException {
        throw new UnsupportedOperationException("getPackageUidAsUser not implemented in subclass");
    }

    public boolean isSpeg(int uid) {
        String packageName = getNameForUid(uid);
        return isSpeg(packageName, UserHandle.getUserId(uid));
    }

    public boolean isSpeg(String packageName, int userId) {
        if (packageName == null) {
            return false;
        }
        int pos = packageName.indexOf(":");
        if (pos > 0) {
            packageName = packageName.substring(0, pos);
        }
        long ident = Binder.clearCallingIdentity();
        try {
            ApplicationInfo info = getApplicationInfoAsUser(packageName, 0, userId);
            Binder.restoreCallingIdentity(ident);
            String sourceDir = info.getCodePath();
            if (sourceDir == null) {
                return false;
            }
            try {
                return new File(sourceDir, "base.speg" + info.uid).exists();
            } catch (SecurityException e) {
                Log.w("SPEG", "No permission to check status for uid " + info.uid);
                return false;
            }
        } catch (NameNotFoundException | AbstractMethodError | IllegalArgumentException e2) {
            Binder.restoreCallingIdentity(ident);
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(ident);
            throw th;
        }
    }

    public void getPlatformPermissionsForGroup(String permissionGroupName, Executor executor, Consumer<List<String>> callback) {
    }

    public void getGroupOfPlatformPermission(String permissionName, Executor executor, Consumer<String> callback) {
    }

    public ApplicationInfo getApplicationInfo(String packageName, ApplicationInfoFlags flags) throws NameNotFoundException {
        throw new UnsupportedOperationException("getApplicationInfo not implemented in subclass");
    }

    public ApplicationInfo getApplicationInfoAsUser(String packageName, ApplicationInfoFlags flags, int userId) throws NameNotFoundException {
        throw new UnsupportedOperationException("getApplicationInfoAsUser not implemented in subclass");
    }

    @SystemApi
    public ApplicationInfo getApplicationInfoAsUser(String packageName, int flags, UserHandle user) throws NameNotFoundException {
        return getApplicationInfoAsUser(packageName, flags, user.getIdentifier());
    }

    @SystemApi
    public ApplicationInfo getApplicationInfoAsUser(String packageName, ApplicationInfoFlags flags, UserHandle user) throws NameNotFoundException {
        return getApplicationInfoAsUser(packageName, flags, user.getIdentifier());
    }

    public int getTargetSdkVersion(String packageName) throws NameNotFoundException {
        throw new UnsupportedOperationException();
    }

    public ActivityInfo getActivityInfo(ComponentName component, ComponentInfoFlags flags) throws NameNotFoundException {
        throw new UnsupportedOperationException("getActivityInfo not implemented in subclass");
    }

    public ActivityInfo getReceiverInfo(ComponentName component, ComponentInfoFlags flags) throws NameNotFoundException {
        throw new UnsupportedOperationException("getReceiverInfo not implemented in subclass");
    }

    public ServiceInfo getServiceInfo(ComponentName component, ComponentInfoFlags flags) throws NameNotFoundException {
        throw new UnsupportedOperationException("getServiceInfo not implemented in subclass");
    }

    public ProviderInfo getProviderInfo(ComponentName component, ComponentInfoFlags flags) throws NameNotFoundException {
        throw new UnsupportedOperationException("getProviderInfo not implemented in subclass");
    }

    public ModuleInfo getModuleInfo(String packageName, int flags) throws NameNotFoundException {
        throw new UnsupportedOperationException("getModuleInfo not implemented in subclass");
    }

    public List<ModuleInfo> getInstalledModules(int flags) {
        throw new UnsupportedOperationException("getInstalledModules not implemented in subclass");
    }

    public List<PackageInfo> getInstalledPackages(PackageInfoFlags flags) {
        throw new UnsupportedOperationException("getInstalledPackages not implemented in subclass");
    }

    @SystemApi
    public PersistableBundle getAppMetadata(String packageName) throws NameNotFoundException {
        throw new UnsupportedOperationException("getAppMetadata not implemented in subclass");
    }

    @SystemApi
    public int getAppMetadataSource(String packageName) throws NameNotFoundException {
        throw new UnsupportedOperationException("getAppMetadataSource not implemented in subclass");
    }

    public List<PackageInfo> getPackagesHoldingPermissions(String[] permissions, PackageInfoFlags flags) {
        throw new UnsupportedOperationException("getPackagesHoldingPermissions not implemented in subclass");
    }

    @SystemApi
    public List<PackageInfo> getInstalledPackagesAsUser(PackageInfoFlags flags, int userId) {
        throw new UnsupportedOperationException("getApplicationInfoAsUser not implemented in subclass");
    }

    @SystemApi
    public String getPermissionControllerPackageName() {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public String getSdkSandboxPackageName() {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    @SystemApi
    public void revokeRuntimePermission(String packageName, String permName, UserHandle user, String reason) {
        revokeRuntimePermission(packageName, permName, user);
    }

    public Set<String> getWhitelistedRestrictedPermissions(String packageName, int whitelistFlag) {
        return Collections.emptySet();
    }

    public boolean addWhitelistedRestrictedPermission(String packageName, String permName, int whitelistFlags) {
        return false;
    }

    public boolean removeWhitelistedRestrictedPermission(String packageName, String permName, int whitelistFlags) {
        return false;
    }

    public boolean setAutoRevokeWhitelisted(String packageName, boolean whitelisted) {
        return false;
    }

    public boolean isAutoRevokeWhitelisted(String packageName) {
        return false;
    }

    public CharSequence getBackgroundPermissionOptionLabel() {
        return "";
    }

    public Intent buildRequestPermissionsIntent(String[] permissions) {
        if (ArrayUtils.isEmpty(permissions)) {
            throw new IllegalArgumentException("permission cannot be null or empty");
        }
        Intent intent = new Intent(ACTION_REQUEST_PERMISSIONS);
        intent.putExtra(EXTRA_REQUEST_PERMISSIONS_NAMES, permissions);
        intent.setPackage(getPermissionControllerPackageName());
        return intent;
    }

    public List<ApplicationInfo> getInstalledApplications(ApplicationInfoFlags flags) {
        throw new UnsupportedOperationException("getInstalledApplications not implemented in subclass");
    }

    public List<ApplicationInfo> getInstalledApplicationsAsUser(ApplicationInfoFlags flags, int userId) {
        throw new UnsupportedOperationException("getInstalledApplicationsAsUser not implemented in subclass");
    }

    public List<SharedLibraryInfo> getSharedLibraries(PackageInfoFlags flags) {
        throw new UnsupportedOperationException("getSharedLibraries() not implemented in subclass");
    }

    public List<SharedLibraryInfo> getSharedLibrariesAsUser(PackageInfoFlags flags, int userId) {
        throw new UnsupportedOperationException("getSharedLibrariesAsUser() not implemented in subclass");
    }

    @SystemApi
    public List<SharedLibraryInfo> getDeclaredSharedLibraries(String packageName, int flags) {
        throw new UnsupportedOperationException("getDeclaredSharedLibraries() not implemented in subclass");
    }

    @SystemApi
    public List<SharedLibraryInfo> getDeclaredSharedLibraries(String packageName, PackageInfoFlags flags) {
        throw new UnsupportedOperationException("getDeclaredSharedLibraries() not implemented in subclass");
    }

    public ResolveInfo resolveActivity(Intent intent, ResolveInfoFlags flags) {
        throw new UnsupportedOperationException("resolveActivity not implemented in subclass");
    }

    public ResolveInfo resolveActivityAsUser(Intent intent, ResolveInfoFlags flags, int userId) {
        throw new UnsupportedOperationException("resolveActivityAsUser not implemented in subclass");
    }

    public List<ResolveInfo> queryIntentActivities(Intent intent, ResolveInfoFlags flags) {
        throw new UnsupportedOperationException("queryIntentActivities not implemented in subclass");
    }

    public List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, ResolveInfoFlags flags, int userId) {
        throw new UnsupportedOperationException("queryIntentActivitiesAsUser not implemented in subclass");
    }

    @SystemApi
    public List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, int flags, UserHandle user) {
        return queryIntentActivitiesAsUser(intent, flags, user.getIdentifier());
    }

    @SystemApi
    public List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, ResolveInfoFlags flags, UserHandle user) {
        return queryIntentActivitiesAsUser(intent, flags, user.getIdentifier());
    }

    public List<ResolveInfo> queryIntentActivityOptions(ComponentName caller, List<Intent> specifics, Intent intent, ResolveInfoFlags flags) {
        throw new UnsupportedOperationException("queryIntentActivityOptions not implemented in subclass");
    }

    public List<ResolveInfo> queryBroadcastReceivers(Intent intent, ResolveInfoFlags flags) {
        throw new UnsupportedOperationException("queryBroadcastReceivers not implemented in subclass");
    }

    @SystemApi
    public List<ResolveInfo> queryBroadcastReceiversAsUser(Intent intent, int flags, UserHandle userHandle) {
        return queryBroadcastReceiversAsUser(intent, flags, userHandle.getIdentifier());
    }

    @SystemApi
    public List<ResolveInfo> queryBroadcastReceiversAsUser(Intent intent, ResolveInfoFlags flags, UserHandle userHandle) {
        return queryBroadcastReceiversAsUser(intent, flags, userHandle.getIdentifier());
    }

    public List<ResolveInfo> queryBroadcastReceiversAsUser(Intent intent, ResolveInfoFlags flags, int userId) {
        throw new UnsupportedOperationException("queryBroadcastReceiversAsUser not implemented in subclass");
    }

    @Deprecated
    public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int flags, int userId) {
        if (VMRuntime.getRuntime().getTargetSdkVersion() >= 26) {
            throw new UnsupportedOperationException("Shame on you for calling the hidden API queryBroadcastReceivers(). Shame!");
        }
        Log.d(TAG, "Shame on you for calling the hidden API queryBroadcastReceivers(). Shame!");
        return queryBroadcastReceiversAsUser(intent, flags, userId);
    }

    public ResolveInfo resolveService(Intent intent, ResolveInfoFlags flags) {
        throw new UnsupportedOperationException("resolveService not implemented in subclass");
    }

    public ResolveInfo resolveServiceAsUser(Intent intent, ResolveInfoFlags flags, int userId) {
        throw new UnsupportedOperationException("resolveServiceAsUser not implemented in subclass");
    }

    public List<ResolveInfo> queryIntentServices(Intent intent, ResolveInfoFlags flags) {
        throw new UnsupportedOperationException("queryIntentServices not implemented in subclass");
    }

    public List<ResolveInfo> queryIntentServicesAsUser(Intent intent, ResolveInfoFlags flags, int userId) {
        throw new UnsupportedOperationException("queryIntentServicesAsUser not implemented in subclass");
    }

    @SystemApi
    public List<ResolveInfo> queryIntentServicesAsUser(Intent intent, int flags, UserHandle user) {
        return queryIntentServicesAsUser(intent, flags, user.getIdentifier());
    }

    @SystemApi
    public List<ResolveInfo> queryIntentServicesAsUser(Intent intent, ResolveInfoFlags flags, UserHandle user) {
        return queryIntentServicesAsUser(intent, flags, user.getIdentifier());
    }

    protected List<ResolveInfo> queryIntentContentProvidersAsUser(Intent intent, ResolveInfoFlags flags, int userId) {
        throw new UnsupportedOperationException("queryIntentContentProvidersAsUser not implemented in subclass");
    }

    @SystemApi
    public List<ResolveInfo> queryIntentContentProvidersAsUser(Intent intent, int flags, UserHandle user) {
        return queryIntentContentProvidersAsUser(intent, flags, user.getIdentifier());
    }

    @SystemApi
    public List<ResolveInfo> queryIntentContentProvidersAsUser(Intent intent, ResolveInfoFlags flags, UserHandle user) {
        return queryIntentContentProvidersAsUser(intent, flags, user.getIdentifier());
    }

    public List<ResolveInfo> queryIntentContentProviders(Intent intent, ResolveInfoFlags flags) {
        throw new UnsupportedOperationException("queryIntentContentProviders not implemented in subclass");
    }

    public ProviderInfo resolveContentProvider(String authority, ComponentInfoFlags flags) {
        throw new UnsupportedOperationException("resolveContentProvider not implemented in subclass");
    }

    public ProviderInfo resolveContentProviderAsUser(String providerName, ComponentInfoFlags flags, int userId) {
        throw new UnsupportedOperationException("resolveContentProviderAsUser not implemented in subclass");
    }

    public List<ProviderInfo> queryContentProviders(String processName, int uid, ComponentInfoFlags flags) {
        throw new UnsupportedOperationException("queryContentProviders not implemented in subclass");
    }

    public List<ProviderInfo> queryContentProviders(String processName, int uid, int flags, String metaDataKey) {
        return queryContentProviders(processName, uid, flags);
    }

    public List<ProviderInfo> queryContentProviders(String processName, int uid, ComponentInfoFlags flags, String metaDataKey) {
        return queryContentProviders(processName, uid, flags);
    }

    public Resources getResourcesForApplication(ApplicationInfo app, Configuration configuration) throws NameNotFoundException {
        return getResourcesForApplication(app);
    }

    public PackageInfo getPackageArchiveInfo(String archiveFilePath, int flags) {
        return getPackageArchiveInfo(archiveFilePath, PackageInfoFlags.of(flags));
    }

    public PackageInfo getPackageArchiveInfo(String archiveFilePath, PackageInfoFlags flags) {
        File apkFile = new File(archiveFilePath);
        long flagsBits = flags.getValue();
        if ((flagsBits & 786432) == 0) {
            flagsBits |= 786432;
        }
        int parserFlags = 0;
        if ((134217792 & flagsBits) != 0) {
            parserFlags = 0 | 32;
        }
        PackageParser2 parser2 = new PackageParser2(null, null, null, new PackageParser2.Callback() { // from class: android.content.pm.PackageManager.1
            @Override // com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback
            public boolean hasFeature(String feature) {
                return PackageManager.this.hasSystemFeature(feature);
            }

            @Override // com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback
            public Set<String> getHiddenApiWhitelistedApps() {
                return Collections.emptySet();
            }

            @Override // com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback
            public Set<String> getInstallConstraintsAllowlist() {
                return Collections.emptySet();
            }

            @Override // com.android.internal.pm.parsing.PackageParser2.Callback
            public boolean isChangeEnabled(long changeId, ApplicationInfo appInfo) {
                return false;
            }
        });
        try {
            ParsedPackage pp = parser2.parsePackage(apkFile, parserFlags, false);
            return PackageInfoCommonUtils.generate(pp, flagsBits, UserHandle.myUserId());
        } catch (PackageParserException e) {
            Log.w(TAG, "Failure to parse package archive apkFile= " + apkFile);
            return null;
        }
    }

    public InstallSourceInfo getInstallSourceInfo(String packageName) throws NameNotFoundException {
        throw new UnsupportedOperationException("getInstallSourceInfo not implemented");
    }

    public boolean isAppArchivable(String packageName) throws NameNotFoundException {
        throw new UnsupportedOperationException("isAppArchivable not implemented");
    }

    public void freeStorageAndNotify(long freeStorageSize, IPackageDataObserver observer) {
        freeStorageAndNotify(null, freeStorageSize, observer);
    }

    public void freeStorage(long freeStorageSize, IntentSender pi) {
        freeStorage(null, freeStorageSize, pi);
    }

    @Deprecated
    public void getPackageSizeInfo(String packageName, IPackageStatsObserver observer) {
        getPackageSizeInfoAsUser(packageName, getUserId(), observer);
    }

    @Deprecated
    public void addPreferredActivityAsUser(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    @SystemApi
    public void replacePreferredActivity(IntentFilter filter, int match, List<ComponentName> set, ComponentName activity) {
        replacePreferredActivity(filter, match, (ComponentName[]) set.toArray(new ComponentName[0]), activity);
    }

    @Deprecated
    public void replacePreferredActivityAsUser(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void addUniquePreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
        throw new UnsupportedOperationException("addUniquePreferredActivity not implemented in subclass");
    }

    public void setComponentEnabledSettings(List<ComponentEnabledSetting> settings) {
        throw new UnsupportedOperationException("setComponentEnabledSettings not implementedin subclass");
    }

    @SystemApi
    public void setSyntheticAppDetailsActivityEnabled(String packageName, boolean enabled) {
        throw new UnsupportedOperationException("setSyntheticAppDetailsActivityEnabled not implemented");
    }

    public boolean getSyntheticAppDetailsActivityEnabled(String packageName) {
        throw new UnsupportedOperationException("getSyntheticAppDetailsActivityEnabled not implemented");
    }

    @SystemApi
    public void setSystemAppState(String packageName, int state) {
        throw new RuntimeException("Not implemented. Must override in a subclass");
    }

    @SystemApi
    public String[] setDistractingPackageRestrictions(String[] packages, int restrictionFlags) {
        throw new UnsupportedOperationException("setDistractingPackageRestrictions not implemented");
    }

    @SystemApi
    @Deprecated
    public String[] setPackagesSuspended(String[] packageNames, boolean suspended, PersistableBundle appExtras, PersistableBundle launcherExtras, String dialogMessage) {
        throw new UnsupportedOperationException("setPackagesSuspended not implemented");
    }

    @SystemApi
    public String[] setPackagesSuspended(String[] packageNames, boolean suspended, PersistableBundle appExtras, PersistableBundle launcherExtras, SuspendDialogInfo dialogInfo) {
        throw new UnsupportedOperationException("setPackagesSuspended not implemented");
    }

    @SystemApi
    public String[] setPackagesSuspended(String[] packageNames, boolean suspended, PersistableBundle appExtras, PersistableBundle launcherExtras, SuspendDialogInfo dialogInfo, int flags) {
        throw new UnsupportedOperationException("setPackagesSuspended not implemented");
    }

    @SystemApi
    public String[] getUnsuspendablePackages(String[] packageNames) {
        throw new UnsupportedOperationException("getUnsuspendablePackages not implemented");
    }

    public boolean isPackageSuspended(String packageName) throws NameNotFoundException {
        throw new UnsupportedOperationException("isPackageSuspended not implemented");
    }

    public boolean isPackageSuspended() {
        throw new UnsupportedOperationException("isPackageSuspended not implemented");
    }

    public Bundle getSuspendedPackageAppExtras() {
        throw new UnsupportedOperationException("getSuspendedPackageAppExtras not implemented");
    }

    public String getSuspendingPackage(String suspendedPackage) {
        throw new UnsupportedOperationException("getSuspendingPackage not implemented");
    }

    public boolean isPackageStopped(String packageName) throws NameNotFoundException {
        throw new UnsupportedOperationException("isPackageStopped not implemented");
    }

    public boolean isPackageQuarantined(String packageName) throws NameNotFoundException {
        throw new UnsupportedOperationException("isPackageQuarantined not implemented");
    }

    public static boolean isMoveStatusFinished(int status) {
        return status < 0 || status > 100;
    }

    public static abstract class MoveCallback {
        public abstract void onStatusChanged(int i, int i2, long j);

        public void onCreated(int moveId, Bundle extras) {
        }
    }

    public int movePackageToSd(String packageName, String volumeUuid, IMemorySaverPackageMoveObserver observer) {
        throw new UnsupportedOperationException("movePackageToSd not implemented in subclass");
    }

    public boolean isDeviceUpgrading() {
        return false;
    }

    public boolean removeCrossProfileIntentFilter(IntentFilter filter, int sourceUserId, int targetUserId, int flags) {
        throw new UnsupportedOperationException("removeCrossProfileIntentFilter not implemented in subclass");
    }

    public static String installStatusToString(int status, String msg) {
        String str = installStatusToString(status);
        if (msg != null) {
            return str + ": " + msg;
        }
        return str;
    }

    public static String installStatusToString(int status) {
        switch (status) {
            case INSTALL_FAILED_MULTI_ARCH_NOT_MATCH_ALL_NATIVE_ABIS /* -131 */:
                return "INSTALL_FAILED_MULTI_ARCH_NOT_MATCH_ALL_NATIVE_ABIS";
            case INSTALL_FAILED_SHARED_LIBRARY_BAD_CERTIFICATE_DIGEST /* -130 */:
                return "INSTALL_FAILED_SHARED_LIBRARY_BAD_CERTIFICATE_DIGEST";
            case INSTALL_FAILED_PROCESS_NOT_DEFINED /* -122 */:
                return "INSTALL_FAILED_PROCESS_NOT_DEFINED";
            case INSTALL_FAILED_WRONG_INSTALLED_VERSION /* -121 */:
                return "INSTALL_FAILED_WRONG_INSTALLED_VERSION";
            case INSTALL_FAILED_BAD_SIGNATURE /* -118 */:
                return "INSTALL_FAILED_BAD_SIGNATURE";
            case INSTALL_FAILED_BAD_DEX_METADATA /* -117 */:
                return "INSTALL_FAILED_BAD_DEX_METADATA";
            case INSTALL_FAILED_SESSION_INVALID /* -116 */:
                return "INSTALL_FAILED_SESSION_INVALID";
            case INSTALL_FAILED_ABORTED /* -115 */:
                return "INSTALL_FAILED_ABORTED";
            case -113:
                return "INSTALL_FAILED_NO_MATCHING_ABIS";
            case INSTALL_FAILED_DUPLICATE_PERMISSION /* -112 */:
                return "INSTALL_FAILED_DUPLICATE_PERMISSION";
            case INSTALL_FAILED_USER_RESTRICTED /* -111 */:
                return "INSTALL_FAILED_USER_RESTRICTED";
            case -110:
                return "INSTALL_FAILED_INTERNAL_ERROR";
            case -109:
                return "INSTALL_PARSE_FAILED_MANIFEST_EMPTY";
            case -108:
                return "INSTALL_PARSE_FAILED_MANIFEST_MALFORMED";
            case -107:
                return "INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID";
            case -106:
                return "INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME";
            case -105:
                return "INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING";
            case -104:
                return "INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES";
            case -103:
                return "INSTALL_PARSE_FAILED_NO_CERTIFICATES";
            case -102:
                return "INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION";
            case -101:
                return "INSTALL_PARSE_FAILED_BAD_MANIFEST";
            case -100:
                return "INSTALL_PARSE_FAILED_NOT_APK";
            case -29:
                return "INSTALL_FAILED_DEPRECATED_SDK_VERSION";
            case -28:
                return "INSTALL_FAILED_MISSING_SPLIT";
            case -25:
                return "INSTALL_FAILED_VERSION_DOWNGRADE";
            case -24:
                return "INSTALL_FAILED_UID_CHANGED";
            case -23:
                return "INSTALL_FAILED_PACKAGE_CHANGED";
            case -22:
                return "INSTALL_FAILED_VERIFICATION_FAILURE";
            case -21:
                return "INSTALL_FAILED_VERIFICATION_TIMEOUT";
            case -20:
                return "INSTALL_FAILED_MEDIA_UNAVAILABLE";
            case -19:
                return "INSTALL_FAILED_INVALID_INSTALL_LOCATION";
            case -18:
                return "INSTALL_FAILED_CONTAINER_ERROR";
            case -17:
                return "INSTALL_FAILED_MISSING_FEATURE";
            case -16:
                return "INSTALL_FAILED_CPU_ABI_INCOMPATIBLE";
            case -15:
                return "INSTALL_FAILED_TEST_ONLY";
            case -14:
                return "INSTALL_FAILED_NEWER_SDK";
            case -13:
                return "INSTALL_FAILED_CONFLICTING_PROVIDER";
            case -12:
                return "INSTALL_FAILED_OLDER_SDK";
            case -11:
                return "INSTALL_FAILED_DEXOPT";
            case -10:
                return "INSTALL_FAILED_REPLACE_COULDNT_DELETE";
            case -9:
                return "INSTALL_FAILED_MISSING_SHARED_LIBRARY";
            case -8:
                return "INSTALL_FAILED_SHARED_USER_INCOMPATIBLE";
            case -7:
                return "INSTALL_FAILED_UPDATE_INCOMPATIBLE";
            case -6:
                return "INSTALL_FAILED_NO_SHARED_USER";
            case -5:
                return "INSTALL_FAILED_DUPLICATE_PACKAGE";
            case -4:
                return "INSTALL_FAILED_INSUFFICIENT_STORAGE";
            case -3:
                return "INSTALL_FAILED_INVALID_URI";
            case -2:
                return "INSTALL_FAILED_INVALID_APK";
            case -1:
                return "INSTALL_FAILED_ALREADY_EXISTS";
            case 1:
                return "INSTALL_SUCCEEDED";
            default:
                return Integer.toString(status);
        }
    }

    public static int installStatusToPublicStatus(int status) {
        switch (status) {
            case INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE /* -129 */:
                return 2;
            case INSTALL_FAILED_BAD_SIGNATURE /* -118 */:
                return 4;
            case INSTALL_FAILED_BAD_DEX_METADATA /* -117 */:
                return 4;
            case INSTALL_FAILED_ABORTED /* -115 */:
                return 3;
            case -113:
                return 7;
            case INSTALL_FAILED_DUPLICATE_PERMISSION /* -112 */:
                return 5;
            case INSTALL_FAILED_USER_RESTRICTED /* -111 */:
                return 7;
            case -110:
                return 1;
            case -109:
                return 4;
            case -108:
                return 4;
            case -107:
                return 4;
            case -106:
                return 4;
            case -105:
                return 4;
            case -104:
                return 4;
            case -103:
                return 4;
            case -102:
                return 4;
            case -101:
                return 4;
            case -100:
                return 4;
            case -29:
                return 7;
            case -28:
                return 7;
            case -26:
                return 4;
            case -25:
                return 4;
            case -24:
                return 4;
            case -23:
                return 4;
            case -22:
                return 3;
            case -21:
                return 3;
            case -20:
                return 6;
            case -19:
                return 6;
            case -18:
                return 6;
            case -17:
                return 7;
            case -16:
                return 7;
            case -15:
                return 4;
            case -14:
                return 7;
            case -13:
                return 5;
            case -12:
                return 7;
            case -11:
                return 4;
            case -10:
                return 5;
            case -9:
                return 7;
            case -8:
                return 5;
            case -7:
                return 5;
            case -6:
                return 5;
            case -5:
                return 5;
            case -4:
                return 6;
            case -3:
                return 4;
            case -2:
                return 4;
            case -1:
                return 5;
            case 1:
                return 0;
            default:
                return 1;
        }
    }

    public static String deleteStatusToString(int status, String msg) {
        String str = deleteStatusToString(status);
        if (msg != null) {
            return str + ": " + msg;
        }
        return str;
    }

    public static String deleteStatusToString(int status) {
        switch (status) {
            case -7:
                return "DELETE_FAILED_APP_PINNED";
            case -6:
                return "DELETE_FAILED_USED_SHARED_LIBRARY";
            case -5:
                return "DELETE_FAILED_ABORTED";
            case -4:
                return "DELETE_FAILED_OWNER_BLOCKED";
            case -3:
                return "DELETE_FAILED_USER_RESTRICTED";
            case -2:
                return "DELETE_FAILED_DEVICE_POLICY_MANAGER";
            case -1:
                return "DELETE_FAILED_INTERNAL_ERROR";
            case 0:
            default:
                return Integer.toString(status);
            case 1:
                return "DELETE_SUCCEEDED";
        }
    }

    public static int deleteStatusToPublicStatus(int status) {
        switch (status) {
        }
        return 1;
    }

    public static String permissionFlagToString(int flag) {
        switch (flag) {
            case 1:
                return "USER_SET";
            case 2:
                return "USER_FIXED";
            case 4:
                return "POLICY_FIXED";
            case 8:
                return "REVOKED_COMPAT";
            case 16:
                return "SYSTEM_FIXED";
            case 32:
                return "GRANTED_BY_DEFAULT";
            case 64:
                return "REVIEW_REQUIRED";
            case 128:
                return "REVOKE_WHEN_REQUESTED";
            case 256:
                return "USER_SENSITIVE_WHEN_GRANTED";
            case 512:
                return "USER_SENSITIVE_WHEN_DENIED";
            case 2048:
                return "RESTRICTION_INSTALLER_EXEMPT";
            case 4096:
                return "RESTRICTION_SYSTEM_EXEMPT";
            case 8192:
                return "RESTRICTION_UPGRADE_EXEMPT";
            case 16384:
                return "APPLY_RESTRICTION";
            case 32768:
                return "GRANTED_BY_ROLE";
            case 65536:
                return "ONE_TIME";
            case 131072:
                return "AUTO_REVOKED";
            default:
                return Integer.toString(flag);
        }
    }

    public static class LegacyPackageDeleteObserver extends PackageDeleteObserver {
        private final IPackageDeleteObserver mLegacy;

        public LegacyPackageDeleteObserver(IPackageDeleteObserver legacy) {
            this.mLegacy = legacy;
        }

        @Override // android.app.PackageDeleteObserver
        public void onPackageDeleted(String basePackageName, int returnCode, String msg) {
            if (this.mLegacy == null) {
                return;
            }
            try {
                this.mLegacy.packageDeleted(basePackageName, returnCode);
            } catch (RemoteException e) {
            }
        }
    }

    @SystemApi
    public static final class UninstallCompleteCallback implements Parcelable {
        public static final Parcelable.Creator<UninstallCompleteCallback> CREATOR = new Parcelable.Creator<UninstallCompleteCallback>() { // from class: android.content.pm.PackageManager.UninstallCompleteCallback.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public UninstallCompleteCallback createFromParcel(Parcel source) {
                return new UninstallCompleteCallback(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public UninstallCompleteCallback[] newArray(int size) {
                return new UninstallCompleteCallback[size];
            }
        };
        private IPackageDeleteObserver2 mBinder;

        @Retention(RetentionPolicy.SOURCE)
        public @interface DeleteStatus {
        }

        public UninstallCompleteCallback(IBinder binder) {
            this.mBinder = IPackageDeleteObserver2.Stub.asInterface(binder);
        }

        private UninstallCompleteCallback(Parcel in) {
            this.mBinder = IPackageDeleteObserver2.Stub.asInterface(in.readStrongBinder());
        }

        @SystemApi
        public void onUninstallComplete(String packageName, int resultCode, String errorMessage) {
            try {
                this.mBinder.onPackageDeleted(packageName, resultCode, errorMessage);
            } catch (RemoteException e) {
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStrongBinder(this.mBinder.asBinder());
        }
    }

    public boolean applyRuntimePermissionsForMdm(String pkgName, List<String> permissions, int grant, int userId) {
        throw new UnsupportedOperationException("applyRuntimePermissionsForMDM not implemented in subclass");
    }

    public boolean applyRuntimePermissionsForAllApplicationsForMdm(int grant, int userId) {
        throw new UnsupportedOperationException("applyRuntimePermissionsForAllApplicationsForMDM not implemented in subclass");
    }

    public List<String> getRequestedRuntimePermissionsForMdm(String pkgName) {
        throw new UnsupportedOperationException("getRequestedRuntimePermissionsForMDM not implemented in subclass");
    }

    @SystemApi
    public ArtManager getArtManager() {
        throw new UnsupportedOperationException("getArtManager not implemented in subclass");
    }

    @SystemApi
    public void setHarmfulAppWarning(String packageName, CharSequence warning) {
        throw new UnsupportedOperationException("setHarmfulAppWarning not implemented in subclass");
    }

    @SystemApi
    public CharSequence getHarmfulAppWarning(String packageName) {
        throw new UnsupportedOperationException("getHarmfulAppWarning not implemented in subclass");
    }

    public boolean hasSigningCertificate(String packageName, byte[] certificate, int type) {
        throw new UnsupportedOperationException("hasSigningCertificate not implemented in subclass");
    }

    public boolean hasSigningCertificate(int uid, byte[] certificate, int type) {
        throw new UnsupportedOperationException("hasSigningCertificate not implemented in subclass");
    }

    public void requestChecksums(String packageName, boolean includeSplits, int required, List<Certificate> trustedInstallers, OnChecksumsReadyListener onChecksumsReadyListener) throws CertificateEncodingException, NameNotFoundException {
        throw new UnsupportedOperationException("requestChecksums not implemented in subclass");
    }

    public String getDefaultTextClassifierPackageName() {
        throw new UnsupportedOperationException("getDefaultTextClassifierPackageName not implemented in subclass");
    }

    public String getSystemTextClassifierPackageName() {
        throw new UnsupportedOperationException("getSystemTextClassifierPackageName not implemented in subclass");
    }

    public String getAttentionServicePackageName() {
        throw new UnsupportedOperationException("getAttentionServicePackageName not implemented in subclass");
    }

    public String getRotationResolverPackageName() {
        throw new UnsupportedOperationException("getRotationResolverPackageName not implemented in subclass");
    }

    public String getWellbeingPackageName() {
        throw new UnsupportedOperationException("getWellbeingPackageName not implemented in subclass");
    }

    public String getAppPredictionServicePackageName() {
        throw new UnsupportedOperationException("getAppPredictionServicePackageName not implemented in subclass");
    }

    public String getSystemCaptionsServicePackageName() {
        throw new UnsupportedOperationException("getSystemCaptionsServicePackageName not implemented in subclass");
    }

    public String getSetupWizardPackageName() {
        throw new UnsupportedOperationException("getSetupWizardPackageName not implemented in subclass");
    }

    @Deprecated
    public final String getContentCaptureServicePackageName() {
        throw new UnsupportedOperationException("getContentCaptureServicePackageName is deprecated");
    }

    @SystemApi
    public String getIncidentReportApproverPackageName() {
        throw new UnsupportedOperationException("getIncidentReportApproverPackageName not implemented in subclass");
    }

    public boolean isPackageStateProtected(String packageName, int userId) {
        throw new UnsupportedOperationException("isPackageStateProtected not implemented in subclass");
    }

    @SystemApi
    public void sendDeviceCustomizationReadyBroadcast() {
        throw new UnsupportedOperationException("sendDeviceCustomizationReadyBroadcast not implemented in subclass");
    }

    public boolean isAutoRevokeWhitelisted() {
        throw new UnsupportedOperationException("isAutoRevokeWhitelisted not implemented in subclass");
    }

    public boolean isDefaultApplicationIcon(Drawable drawable) {
        int resId = drawable instanceof AdaptiveIconDrawable ? ((AdaptiveIconDrawable) drawable).getSourceDrawableResId() : 0;
        return resId == 17301651 || resId == 17304561;
    }

    public void setMimeGroup(String mimeGroup, Set<String> mimeTypes) {
        throw new UnsupportedOperationException("setMimeGroup not implemented in subclass");
    }

    public Set<String> getMimeGroup(String mimeGroup) {
        throw new UnsupportedOperationException("getMimeGroup not implemented in subclass");
    }

    public Property getProperty(String propertyName, String packageName) throws NameNotFoundException {
        throw new UnsupportedOperationException("getProperty not implemented in subclass");
    }

    public Property getProperty(String propertyName, ComponentName component) throws NameNotFoundException {
        throw new UnsupportedOperationException("getProperty not implemented in subclass");
    }

    public Property getPropertyAsUser(String propertyName, String packageName, String className, int userId) throws NameNotFoundException {
        throw new UnsupportedOperationException("getPropertyAsUser not implemented in subclass");
    }

    public List<Property> queryApplicationProperty(String propertyName) {
        throw new UnsupportedOperationException("qeuryApplicationProperty not implemented in subclass");
    }

    public List<Property> queryActivityProperty(String propertyName) {
        throw new UnsupportedOperationException("qeuryActivityProperty not implemented in subclass");
    }

    public List<Property> queryProviderProperty(String propertyName) {
        throw new UnsupportedOperationException("qeuryProviderProperty not implemented in subclass");
    }

    public List<Property> queryReceiverProperty(String propertyName) {
        throw new UnsupportedOperationException("qeuryReceiverProperty not implemented in subclass");
    }

    public List<Property> queryServiceProperty(String propertyName) {
        throw new UnsupportedOperationException("qeuryServiceProperty not implemented in subclass");
    }

    public boolean canPackageQuery(String sourcePackageName, String targetPackageName) throws NameNotFoundException {
        throw new UnsupportedOperationException("canPackageQuery not implemented in subclass");
    }

    public boolean[] canPackageQuery(String sourcePackageName, String[] targetPackageNames) throws NameNotFoundException {
        throw new UnsupportedOperationException("canPackageQuery not implemented in subclass");
    }

    public void makeProviderVisible(int recipientUid, String visibleAuthority) {
        try {
            ActivityThread.getPackageManager().makeProviderVisible(recipientUid, visibleAuthority);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void makeUidVisible(int recipientUid, int visibleUid) {
        throw new UnsupportedOperationException("makeUidVisible not implemented in subclass");
    }

    public ArchivedPackageInfo getArchivedPackage(String packageName) {
        throw new UnsupportedOperationException("getArchivedPackage not implemented in subclass");
    }

    private static final class ApplicationInfoQuery {
        final long flags;
        final String packageName;
        final int userId;

        ApplicationInfoQuery(String packageName, long flags, int userId) {
            this.packageName = packageName;
            this.flags = flags;
            this.userId = userId;
        }

        public String toString() {
            return String.format("ApplicationInfoQuery(packageName=\"%s\", flags=%s, userId=%s)", this.packageName, Long.valueOf(this.flags), Integer.valueOf(this.userId));
        }

        public int hashCode() {
            int hash = Objects.hashCode(this.packageName);
            return (((hash * 13) + Objects.hashCode(Long.valueOf(this.flags))) * 13) + Objects.hashCode(Integer.valueOf(this.userId));
        }

        public boolean equals(Object rval) {
            if (rval == null) {
                return false;
            }
            try {
                ApplicationInfoQuery other = (ApplicationInfoQuery) rval;
                return Objects.equals(this.packageName, other.packageName) && this.flags == other.flags && this.userId == other.userId;
            } catch (ClassCastException e) {
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ApplicationInfo getApplicationInfoAsUserUncached(String packageName, long flags, int userId) {
        try {
            return ActivityThread.getPackageManager().getApplicationInfo(packageName, flags, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static ApplicationInfo getApplicationInfoAsUserCached(String packageName, long flags, int userId) {
        return sApplicationInfoCache.query(new ApplicationInfoQuery(packageName, flags, userId));
    }

    public static void disableApplicationInfoCache() {
        sApplicationInfoCache.disableLocal();
    }

    public static void invalidatePackageInfoCache() {
        sCacheAutoCorker.autoCork();
    }

    private static final class PackageInfoQuery {
        final long flags;
        final String packageName;
        final int userId;

        PackageInfoQuery(String packageName, long flags, int userId) {
            this.packageName = packageName;
            this.flags = flags;
            this.userId = userId;
        }

        public String toString() {
            return String.format("PackageInfoQuery(packageName=\"%s\", flags=%s, userId=%s)", this.packageName, Long.valueOf(this.flags), Integer.valueOf(this.userId));
        }

        public int hashCode() {
            int hash = Objects.hashCode(this.packageName);
            return (((hash * 13) + Objects.hashCode(Long.valueOf(this.flags))) * 13) + Objects.hashCode(Integer.valueOf(this.userId));
        }

        public boolean equals(Object rval) {
            if (rval == null) {
                return false;
            }
            try {
                PackageInfoQuery other = (PackageInfoQuery) rval;
                return Objects.equals(this.packageName, other.packageName) && this.flags == other.flags && this.userId == other.userId;
            } catch (ClassCastException e) {
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PackageInfo getPackageInfoAsUserUncached(String packageName, long flags, int userId) {
        try {
            return ActivityThread.getPackageManager().getPackageInfo(packageName, flags, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static PackageInfo getPackageInfoAsUserCached(String packageName, long flags, int userId) {
        return sPackageInfoCache.query(new PackageInfoQuery(packageName, flags, userId));
    }

    public static void disablePackageInfoCache() {
        sPackageInfoCache.disableLocal();
    }

    public static void corkPackageInfoCache() {
        PropertyInvalidatedCache.corkInvalidations(PermissionManager.CACHE_KEY_PACKAGE_INFO);
    }

    public static void uncorkPackageInfoCache() {
        PropertyInvalidatedCache.uncorkInvalidations(PermissionManager.CACHE_KEY_PACKAGE_INFO);
    }

    public IBinder getHoldLockToken() {
        try {
            return ActivityThread.getPackageManager().getHoldLockToken();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void holdLock(IBinder token, int durationMs) {
        try {
            ActivityThread.getPackageManager().holdLock(token, durationMs);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeepUninstalledPackages(List<String> packageList) {
        try {
            ActivityThread.getPackageManager().setKeepUninstalledPackages(packageList);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUnknownSourcePackage(String packageName) {
        throw new UnsupportedOperationException("isUnknownSourcePackage not implemented in subclass");
    }

    public List<PackageInfo> getUnknownSourcePackages(int flags) {
        throw new UnsupportedOperationException("getUnknownSourcePackagesAsUser not implemented in subclass");
    }

    @SystemApi
    public boolean canUserUninstall(String packageName, UserHandle user) {
        throw new UnsupportedOperationException("canUserUninstall not implemented in subclass");
    }

    @SystemApi
    public boolean shouldShowNewAppInstalledNotification() {
        throw new UnsupportedOperationException("isShowNewAppInstalledNotificationEnabled not implemented in subclass");
    }

    public void relinquishUpdateOwnership(String targetPackage) {
        throw new UnsupportedOperationException("relinquishUpdateOwnership not implemented in subclass");
    }

    public void registerPackageMonitorCallback(IRemoteCallback callback, int userId) {
        throw new UnsupportedOperationException("registerPackageMonitorCallback not implemented in subclass");
    }

    public void unregisterPackageMonitorCallback(IRemoteCallback callback) {
        throw new UnsupportedOperationException("unregisterPackageMonitorCallback not implemented in subclass");
    }

    public <T> T parseAndroidManifest(File apkFile, Function<XmlResourceParser, T> parserFunction) throws IOException {
        throw new UnsupportedOperationException("parseAndroidManifest not implemented in subclass");
    }

    public <T> T parseAndroidManifest(ParcelFileDescriptor apkFileDescriptor, Function<XmlResourceParser, T> parserFunction) throws IOException {
        throw new UnsupportedOperationException("parseAndroidManifest not implemented in subclass");
    }

    public TypedArray extractPackageItemInfoAttributes(PackageItemInfo info, String name, String rootTag, int[] attributes) {
        throw new UnsupportedOperationException("parseServiceMetadata not implemented in subclass");
    }

    public int semGetSystemFeatureLevel(String name) {
        throw new UnsupportedOperationException("semGetSystemFeatureLevel not implemented in subclass");
    }

    public Drawable semGetActivityIconForIconTray(ComponentName activityName, int mode) throws NameNotFoundException {
        throw new UnsupportedOperationException("semGetActivityIconForIconTray not implemented in subclass");
    }

    public CharSequence semGetCscPackageItemText(String packageItemName) {
        throw new UnsupportedOperationException("semGetCscPackageItemText not implemented in subclass");
    }

    public Drawable semGetCscPackageItemIcon(String packageItemName) {
        throw new UnsupportedOperationException("semGetCscPackageItemIcon not implemented in subclass");
    }

    public Drawable semGetApplicationIconForIconTray(ApplicationInfo info, int mode) {
        throw new UnsupportedOperationException("semGetApplicationIconForIconTray not implemented in subclass");
    }

    public boolean isPermissionRevokedByUserFixed(String permission, String packageName) {
        return semIsPermissionRevokedByUserFixed(permission, packageName);
    }

    public boolean semIsPermissionRevokedByUserFixed(String permission, String packageName) {
        throw new UnsupportedOperationException("semIsPermissionRevokedByUserFixed not implemented in subclass");
    }

    public Drawable semGetApplicationIconForIconTray(String packageName, int mode) throws NameNotFoundException {
        throw new UnsupportedOperationException("semGetApplicationIconForIconTray not implemented in subclass");
    }

    public boolean semCheckComponentMetadataForIconTray(String packageName, String componentName) {
        throw new UnsupportedOperationException("semCheckComponentMetadataForIconTray not implemented in subclass");
    }

    public boolean semShouldPackIntoIconTray(String packageName) {
        throw new UnsupportedOperationException("semShouldPackIntoIconTray not implemented in subclass");
    }

    public int semGetAppIconFeatures(String packageName) {
        throw new UnsupportedOperationException("semShouldPackIntoIconTray not implemented in subclass");
    }

    public Drawable semGetDrawableForIconTray(Drawable icon, int mode) {
        throw new UnsupportedOperationException("semGetDrawableForIconTray not implemented in subclass");
    }

    public Drawable semGetDrawableForIconTray(Drawable icon, int mode, String packageName, int density) {
        throw new UnsupportedOperationException("semGetDrawableForIconTray not implemented in subclass");
    }

    public void semFreeStorageAndNotify(long freeStorageSize, SemFreeStorageNotifyListener listener) {
        SemPackageDataObserver spdo = new SemPackageDataObserver(listener);
        freeStorageAndNotify(null, freeStorageSize, spdo);
    }

    public class SemPackageDataObserver extends IPackageDataObserver.Stub {
        private SemFreeStorageNotifyListener mListener;

        public SemPackageDataObserver(SemFreeStorageNotifyListener listener) {
            this.mListener = listener;
        }

        @Override // android.content.pm.IPackageDataObserver
        public void onRemoveCompleted(String packageName, boolean succeeded) {
            if (this.mListener != null) {
                this.mListener.onRemoveCompleted(packageName, succeeded);
            }
        }
    }

    public CharSequence getCSCPackageItemText(String packageItemName) {
        return semGetCscPackageItemText(packageItemName);
    }

    public Drawable getCSCPackageItemIcon(String packageItemName) {
        return semGetCscPackageItemIcon(packageItemName);
    }

    public List<ResolveInfo> semQueryIntentActivitiesAsUser(Intent intent, int flags, int userId) {
        return queryIntentActivitiesAsUser(intent, flags, userId);
    }

    public void semUpdatePermissionFlags(String permissionName, String packageName, int flagMask, int flagValues, UserHandle user) {
        updatePermissionFlags(permissionName, packageName, flagMask, flagValues, user);
    }

    public int semGetPermissionFlags(String permissionName, String packageName, UserHandle user) {
        return getPermissionFlags(permissionName, packageName, user);
    }

    public void semRevokeRuntimePermission(String packageName, String permissionName, UserHandle user) {
        revokeRuntimePermission(packageName, permissionName, user);
    }

    public void semGrantRuntimePermission(String packageName, String permissionName, UserHandle user) {
        grantRuntimePermission(packageName, permissionName, user);
    }

    public String[] semSetPackagesSuspended(String[] packageNames, boolean suspended, PersistableBundle appExtras, PersistableBundle launcherExtras, SemSuspendDialogInfo semDialogInfo) {
        SuspendDialogInfo dialogInfo = null;
        if (semDialogInfo != null) {
            SuspendDialogInfo.Builder builder = new SuspendDialogInfo.Builder();
            if (semDialogInfo.getTitleResId() != 0) {
                builder.setTitle(semDialogInfo.getTitleResId());
            }
            if (semDialogInfo.getDialogMessageResId() != 0) {
                builder.setMessage(semDialogInfo.getDialogMessageResId());
            }
            if (semDialogInfo.getDialogMessage() != null) {
                builder.setMessage(semDialogInfo.getDialogMessage());
            }
            if (semDialogInfo.getNeutralButtonTextResId() != 0) {
                builder.setNeutralButtonText(semDialogInfo.getNeutralButtonTextResId());
            }
            dialogInfo = builder.build();
        }
        return setPackagesSuspended(packageNames, suspended, appExtras, launcherExtras, dialogInfo);
    }

    public String[] semGetUnsuspendablePackages(String[] packageNames) {
        return getUnsuspendablePackages(packageNames);
    }

    public PackageInfo semGetPackageInfoAsUser(String packageName, int flags, int userId) throws NameNotFoundException {
        throw new UnsupportedOperationException("semGetPackageInfoAsUser not implemented in subclass");
    }

    public List<PackageInfo> semGetInstalledPackagesAsUser(PackageInfoFlags flags, int userId) {
        return getInstalledPackagesAsUser(flags, userId);
    }

    public Drawable loadItemIcon(PackageItemInfo itemInfo, ApplicationInfo appInfo, boolean forIconContainer, int mode) {
        throw new UnsupportedOperationException("loadItemIcon not implemented in subclass");
    }

    public Drawable loadUnbadgedItemIcon(PackageItemInfo itemInfo, ApplicationInfo appInfo, boolean forIconContainer, int mode) {
        throw new UnsupportedOperationException("loadUnbadgedItemIcon not implemented in subclass");
    }

    public Drawable getMonetizeBadgedIcon(Drawable icon) {
        throw new UnsupportedOperationException("getMonetizeBadgedIcon not implemented in subclass");
    }

    public boolean shouldAppSupportBadgeIcon(String packageName, int userId) {
        throw new UnsupportedOperationException("shouldAppSupportBadgeIcon not implemented in subclass");
    }

    public void setAppCategoryHintUser(String pkgName, int category) {
        throw new UnsupportedOperationException("setAppCategoryHintUser not implemented in subclass");
    }

    public void clearAppCategoryHintUser(String pkgName) {
        throw new UnsupportedOperationException("setAppCategoryHintUser not implemented in subclass");
    }

    public Map<String, String> getAppCategoryHintUserMap() {
        throw new UnsupportedOperationException("getAppCategoryHintUserMap not implemented in subclass");
    }

    public Map<String, String[]> getAppCategoryInfos(String pkgName) {
        throw new UnsupportedOperationException("getAppCategoryInfos not implemented in subclass");
    }
}
