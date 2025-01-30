package com.samsung.android.knox;

import android.Manifest;
import android.app.IServiceConnection;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.appwidget.AppWidgetHostView;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IRCPInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.p001om.SamsungThemeConstants;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.ISystemPersonaObserver;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.content.p002pm.UserInfo;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbInterface;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.ContainerStateReceiver;
import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.SystemProperties;
import android.p009os.UserHandle;
import android.p009os.UserManager;
import android.p009os.storage.StorageManager;
import android.provider.Settings;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.util.Log;
import android.util.Pair;
import android.view.inputmethod.SemInputMethodManagerUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.C4337R;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.ims.options.SemCapabilities;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.analytics.util.UploaderBroadcaster;
import com.samsung.android.knox.dar.ddar.fsm.State;
import com.samsung.android.knox.dar.ddar.fsm.StateMachine;
import com.samsung.android.share.SemShareConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class SemPersonaManager {
    public static final String ACCESS_TYPE_BLUETOOTH = "bluetooth";
    public static final String ACCESS_TYPE_SDCARD = "sdcard";
    public static final String ACTION_CHANGE_CREDENTIAL_SCREEN = "com.samsung.android.knox.CHANGE_CREDENTIAL_SCREEN";
    public static final String ACTION_CONFIRM_PROFILE_CREDENTIAL_WITH_USER = "com.samsung.android.knox.COMFIRM_CREDENTIAL";
    public static final String ACTION_LOCKDOWN_SCREEN = "com.samsung.android.knox.LOCKDOWN_SCREEN";
    private static final String ACTION_SWITCH_PROFILE = "com.samsung.android.knox.ACTION_SWITCH_PROFILE";
    public static final String APPSEPARATION_PACKAGE = "com.samsung.android.appseparation";
    public static final String APP_SEPARATION_APP_LIST = "APP_SEPARATION_APP_LIST";
    public static final String APP_SEPARATION_COEXISTENCE_LIST = "APP_SEPARATION_COEXISTANCE_LIST";
    public static final String APP_SEPARATION_OUTSIDE = "APP_SEPARATION_OUTSIDE";
    public static final int ATTR_HAS_PREMIUM_CONTAINER_LICENSE_ACTIVATED = 1073741824;
    public static final String BLOCKED_SHARING_COMP_COMMON = "blockedcompcommon";
    public static final String BLOCKED_SHARING_COMP_FOR_OWNER = "blockedcompknox";
    public static final String BLOCKED_SHARING_COMP_FOR_SECUREFOLDER = "blockedcompsecurefolder";
    public static final String BOOKMARKS = "Bookmarks";
    public static final String CALLER_DISPLAY_NAME = "caller_display_name";
    public static final String CALLER_PHONE_NUMBER = "caller_phone_number";
    public static final String CALLER_PHOTO = "caller_photo";
    public static final String CALL_LOG = "CallLog";
    public static final String CLIPBOARD = "Clipboard";
    public static final String CONTACT_OWNER_ID = "contact_owner_id";
    public static final int CONTAINER_COM_TYPE = 3;
    public static final String CONTAINER_CORE_ADMIN_RECEIVER = "com.samsung.android.knox.containercore.KnoxAdminCommandReceiver";
    public static final String CONTAINER_CORE_PACKAGE = "com.samsung.android.knox.containercore";
    public static final int CONTAINER_DEFAULT_TYPE = 1;
    public static final String CONTAINER_DESKTOP_PACKAGE = "com.samsung.android.knox.containerdesktop";
    public static final int CONTAINER_LWC_TYPE = 2;
    public static final int CONTAINER_TYPE_CONTAINER_ONLY = 6;
    public static final int CONTAINER_TYPE_LEGACY = 5;
    public static final int CONTAINER_TYPE_MY_KNOX = 1;
    public static final int CONTAINER_TYPE_NONE = 0;
    public static final int CONTAINER_TYPE_PREMIUM = 4;
    public static final int CONTAINER_TYPE_PRIME = 3;
    public static final int CONTAINER_TYPE_SECURE_FOLDER = 2;
    public static final String CUSTOM_BADGE_ICON = "custom-badge-icon";
    public static final String CUSTOM_CONTAINER_ICON = "custom-container-icon";
    public static final String CUSTOM_NAME_ICON = "custom-name-icon";
    public static final String CUSTOM_PERSONAL_MODEL_LABEL = "custom-name-personal-mode";
    public static final String DEFAULT_APPS = "DefaultApps";
    public static final int DEFAULT_SDP_ACTIVATION_TIME = 5000;
    public static final String ENABLE_EULA = "enable_eula";
    public static final int ERROR_CREATE_PERSONA_ADMIN_ACTIVATION_FAILED = -1009;
    public static final int ERROR_CREATE_PERSONA_ADMIN_INSTALLATION_FAILED = -1008;
    public static final int ERROR_CREATE_PERSONA_EC_MAX_PERSONA_LIMIT_REACHED = -1015;
    public static final int ERROR_CREATE_PERSONA_EMERGENCY_MODE_FAILED = -1031;
    public static final int ERROR_CREATE_PERSONA_FILESYSTEM_ERROR = -1011;
    public static final int ERROR_CREATE_PERSONA_GENERATE_CMK_FAILED = -1034;
    public static final int ERROR_CREATE_PERSONA_HANDLER_INSTALLATION_FAILED = -1006;
    public static final int ERROR_CREATE_PERSONA_INTERNAL_ERROR = -1014;
    public static final int ERROR_CREATE_PERSONA_MAX_PERSONA_LIMIT_REACHED = -1012;
    public static final int ERROR_CREATE_PERSONA_NO_HANDLER_APK = -1002;
    public static final int ERROR_CREATE_PERSONA_NO_NAME = -1001;
    public static final int ERROR_CREATE_PERSONA_NO_PERSONA_ADMIN_APK = -1004;
    public static final int ERROR_CREATE_PERSONA_NO_PERSONA_TYPE = -1005;
    public static final int ERROR_CREATE_PERSONA_NO_SETUPWIZARD_APK = -1003;
    public static final int ERROR_CREATE_PERSONA_RUNTIME_PERMISSION_GRANT = -1035;
    public static final int ERROR_CREATE_PERSONA_SECURE_FOLDER_MAX_PERSONA_LIMIT_REACHED = -1013;
    public static final int ERROR_CREATE_PERSONA_SETUPWIZARD_INSTALLATION_FAILED = -1007;
    public static final int ERROR_CREATE_PERSONA_SUB_USER_FAILED = -1027;
    public static final int ERROR_CREATE_PERSONA_SYSTEM_APP_INSTALLATION_FAILED = -1010;
    public static final int ERROR_CREATE_PERSONA_TIMA_PWD_KEY_FAILED = -1033;
    public static final int ERROR_CREATE_PERSONA_USER_INFO_INVALID = -1032;
    public static final int ERROR_INVAILD_CONTAINER_ID = -1301;
    public static final int ERROR_NO_PERSONA_SERVICE = -1300;
    public static final int ERROR_PERSONA_APP_INSTALLATION_FAILED = -2001;
    public static final int ERROR_REMOVE_NOT_PERSONA_OWNER = -1203;
    public static final int ERROR_REMOVE_PERSONA_FAILED = -1201;
    public static final int ERROR_REMOVE_PERSONA_NOT_EXIST = -1202;
    public static final int ERROR_SWITCH_EQUALS_CURRENT_USER = -1105;
    public static final int ERROR_SWITCH_INVALID_PERSONA_ID = -1100;
    public static final int ERROR_SWITCH_OUTSIDE_PERSONA_GROUP = -1106;
    public static final int ERROR_SWITCH_PERSONA_FILESYSTEM = -1103;
    public static final int ERROR_SWITCH_PERSONA_HANDLER_NOT_RESPONDING = -1104;
    public static final int ERROR_SWITCH_PERSONA_LOCKED = -1102;
    public static final int ERROR_SWITCH_PERSONA_NOT_INITIALIZED = -1101;
    private static final String EXTRA_UNLAUNCHABLE_REASON = "unlaunchable_reason";
    private static final int FLAG_BASE = 1;
    public static final int FLAG_DUAL_DAR = 100663296;
    public static final int FLAG_DUAL_DAR_CUSTOM_CRYPTO = 67108864;
    public static final int FLAG_DUAL_DAR_SAMSUNG_CRYPTO = 33554432;
    public static final int FLAG_EC_ENABLED = 65536;
    public static final int FLAG_SECURE_FOLDER_CONTAINER = 8192;
    public static final String FOLDERCONTAINER_PKG_NAME = "com.sec.knox.foldercontainer";
    public static final String FRAMEWORK_PACKAGE = "android";
    public static final String HOME_SCREEN_WALLPAPER = "custom-home-screen-wallpaper";
    public static final String ICON_CLASS_FOR_INTENT_FORWARD_TO_PARENT = "com.android.internal.app.ForwardIntentToParent";
    public static final String ICON_CLASS_FOR_INTENT_FORWARD_TO_PROFILE = "com.android.internal.app.ForwardIntentToManagedProfile";
    public static final String ICON_CLASS_FOR_SECUREFOLDER_FORWARD_TO_PROFILE = "com.android.internal.app.ForwardIntentToManagedProfile4";
    public static final String ICON_CLASS_SECUREFOLDER_FILE_STORE = "switcher.B2CStoreFilesActivity";
    public static final int IMMEDIATELY_LOCK_TIMEOUT = -2;
    public static final String INTENT_ACCESS_EXT_SDCARD = "com.sec.knox.container.access.extsdcard";
    public static final String INTENT_ACTION_CHANGE_PASSWORD = "com.samsung.android.knox.intent.action.CHANGE_PASSWORD";
    public static final String INTENT_ACTION_CONFIRM_DEVICE_CREDENTIAL_WITH_USER = "com.samsung.android.knox.intent.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER";
    public static final String INTENT_ACTION_CONTAINER_REMOVAL_STARTED = "com.sec.knox.container.action.containerremovalstarted";
    public static final String INTENT_ACTION_CREATE_SECURE_FOLDER = "com.sec.knox.action.CREATE_SECURE_FOLDER";
    public static final String INTENT_ACTION_KNOX_LICENSE_ACATIVATE_DIALOG_INTERNAL = "com.samsung.android.knox.intent.action.KNOX_LICENSE_ACATIVATE_DIALOG_INTERNAL";
    public static final String INTENT_ACTION_LAUNCH_INFO = "com.sec.knox.container.action.launchinfo";
    public static final String INTENT_ACTION_NFC_POLICY = "com.samsung.android.knox.nfc.policy";
    public static final String INTENT_ACTION_NOTIFY_APPSEPARATION = "com.samsung.android.knox.intent.action.NOTIFY_APPSEPARATION_INTERNAL";
    public static final String INTENT_ACTION_OBSERVER = "com.sec.knox.container.action.observer";
    public static final String INTENT_ACTION_SDP_TIMEOUT = "com.sec.knox.container.INTENT_KNOX_SDP_ACTIVATED";
    public static final String INTENT_CATEGORY_OBSERVER_CONTAINERID = "com.sec.knox.container.category.observer.containerid";
    public static final String INTENT_CATEGORY_OBSERVER_ONATTRIBUTECHANGE = "com.sec.knox.container.category.observer.onattributechange";
    public static final String INTENT_CATEGORY_OBSERVER_ONKEYGUARDSTATECHANGED = "com.sec.knox.container.category.observer.onkeyguardstatechanged";
    public static final String INTENT_CATEGORY_OBSERVER_ONPERSONASWITCH = "com.sec.knox.container.category.observer.onpersonaswitch";
    public static final String INTENT_CATEGORY_OBSERVER_ONSESSIONEXPIRED = "com.sec.knox.container.category.observer.onsessionexpired";
    public static final String INTENT_CATEGORY_OBSERVER_ONSTATECHANGE = "com.sec.knox.container.category.observer.onstatechange";
    public static final String INTENT_CONTAINER_NEED_RESTART = "com.sec.knox.container.need.restart";
    public static final String INTENT_EXTRA_CONTAINER_ID = "containerId";
    public static final String INTENT_EXTRA_OBSERVER_ATTRIBUTE = "com.sec.knox.container.extra.observer.attribute";
    public static final String INTENT_EXTRA_OBSERVER_ATTRIBUTE_STATE = "com.sec.knox.container.extra.observer.attribute.state";
    public static final String INTENT_EXTRA_OBSERVER_KEYGUARDSTATE = "com.sec.knox.container.extra.observer.keyguardstate";
    public static final String INTENT_EXTRA_OBSERVER_NEWSTATE = "com.sec.knox.container.extra.observer.newstate";
    public static final String INTENT_EXTRA_OBSERVER_PREVIOUSSTATE = "com.sec.knox.container.extra.observer.previousstate";
    public static final String INTENT_EXTRA_UPDATED_VALUE = "com.sec.knox.container.extra.updated.value";
    public static final String INTENT_PERMISSION_LAUNCH_INFO = "com.samsung.container.LAUNCH_INFO";
    public static final String INTENT_PERMISSION_OBSERVER = "com.samsung.container.OBSERVER";
    public static final String INTENT_PERMISSION_RECEIVE_KNOX_APPS_UPDATE = "com.sec.knox.container.permission.RECEIVE_KNOX_APPS_UPDATE";
    public static final int KA_AS_SCHEMA_VERSION = 1;
    public static final int KA_SCHEMA_VERSION = 5;
    public static final String KNOX_SETTINGS_SYNC_PREFIX = "knox_container_sync_";
    public static final String LOCK_SCREEN_WALLPAPER = "custom-lock-screen-wallpaper";
    public static final String MANAGED_PROVISIONING_PACKAGE = "com.android.managedprovisioning";
    public static final int MAX_PERSONA_ALLOWED = 2;
    public static final int MAX_PERSONA_ALLOWED_SECURE_FOLDER = 1;
    public static final int MAX_PERSONA_ID = 200;
    public static final int MAX_SECURE_FOLDER_ID = 160;
    public static final int MINIMUM_SCREEN_OFF_TIMEOUT = 5000;
    public static final int MIN_PERSONA_ID = 100;
    public static final int MIN_SECURE_FOLDER_ID = 150;
    public static final String MOVE_FILE_TO_CONTAINER = "move-file-to-container";
    public static final String MOVE_FILE_TO_OWNER = "move-file-to-owner";
    public static final int MOVE_TO_APP_TYPE_GALLERY = 1;
    public static final int MOVE_TO_APP_TYPE_MUSIC = 3;
    public static final int MOVE_TO_APP_TYPE_MYFILES = 4;
    public static final int MOVE_TO_APP_TYPE_VIDEO = 2;
    public static final int MOVE_TO_CONTAINER_TYPE_ENTERPRISE_CONTAINER = 1000;
    public static final int MOVE_TO_CONTAINER_TYPE_KNOX = 1001;
    public static final int MOVE_TO_CONTAINER_TYPE_SECURE_FOLDER = 1002;
    public static final int MOVE_TO_PERSONAL_TYPE_KNOX = 1004;
    public static final int MOVE_TO_PERSONAL_TYPE_SECURE_FOLDER = 1003;
    public static final String NOTIFICATIONS = "Notifications";
    public static final String PERMISSION_KEYGUARD_ACCESS = "com.sec.knox.container.keyguard.ACCESS";
    public static final String PERMISSION_PERIPHERAL_POLICY_UPDATE = "com.sec.knox.container.peripheral.POLICY_UPDATE";
    public static final String PERSONA_CACHE_RESET_ON_REBOOT = "knoxid.reset_on_reboot";
    public static final String PERSONA_ID = "persona_id";
    public static final String PERSONA_POLICY_SERVICE = "persona_policy";
    public static final int PERSONA_TIMA_ECRPTFS_INDEX1 = 100;
    public static final int PERSONA_TIMA_ECRPTFS_INDEX2 = 102;
    public static final int PERSONA_TIMA_PASSWORDHINT_INDEX = 104;
    public static final int PERSONA_TIMA_PASSWORD_INDEX1 = 101;
    public static final int PERSONA_TIMA_PASSWORD_INDEX2 = 103;
    public static final String PERSONA_VALIDATOR_HANDLER = "persona_validator";
    public static final String PROPERTY_DEVICE_OWNER_EXISTS = "persist.sys.knox.device_owner";
    public static final String PROPERTY_KNOX_CONTAINER_INFO = "persist.sys.knox.userinfo";
    public static final String PROPERTY_SECURE_FOLDER_AVAILABLE = "persist.sys.knox.secure_folder_state_available";
    public static final int REMOVE_OP_SUCCESS = 0;
    public static final String SANITIZE_DATA_LOCKSCREEN = "knox-sanitize-data-lockscreen";
    public static final String SECUREFOLDER_ICON_CLASS_SWITCH_TO_HOME = "com.samsung.knox.securefolder.switcher.SwitchToPersonalIcon";
    private static final String SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION = "29";
    public static final boolean SEC_PRODUCT_FEATURE_KNOX_SUPPORT_CONTAINER = true;
    private static final boolean SEC_PRODUCT_FEATURE_KNOX_SUPPORT_DUAL_DAR = true;
    public static final boolean SEC_PRODUCT_FEATURE_KNOX_SUPPORT_MDM = true;
    public static final String SETUP_WIZARD_PKG_NAME = "com.sec.knox.setup";
    public static final String SHORTCUTS = "Shortcuts";
    public static final String SMS = "Sms";
    public static final int TIMA_COMPROMISED_TYPE1 = 65548;
    public static final int TIMA_COMPROMISED_TYPE2 = 65549;
    public static final int TIMA_COMPROMISED_TYPE3 = 65550;
    public static final int TIMA_COMPROMISED_TYPE4 = 65551;
    public static final int TIMA_VALIDATION_SUCCESS = 0;
    private static final int UNLAUNCHABLE_REASON_PWD_EXPIRED = 1;
    public static final int WHEN_PHONE_RESTART_LOCK_TIMEOUT = -1;
    public static final int WHEN_SCREEN_TURNS_OFF_LOCK_TIMEOUT = 0;
    private static KeyguardManager mKeyguardManager;
    private final Context mContext;
    private final ISemPersonaManager mService;
    private static String TAG = "SemPersonaManager";
    private static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static String SECURE_FOLDER_NAME = "secure-folder";
    private static ArrayList<Bundle> mMoveToInfo = null;
    private static SemPersonaManager personaManager = null;
    private static SemRemoteContentManager rcpManager = null;
    private static final String ADAPT_SOUND_PACKAGE_NAME = "com.sec.hearingadjust";
    static final String[] SHORTCUT_FILTER = {ADAPT_SOUND_PACKAGE_NAME};
    private static final String[] SETTINGS_INTENT_FORWARD_BLOCKLIST_FOR_SF = {Settings.ACTION_USAGE_ACCESS_SETTINGS, Settings.ACTION_ADD_ACCOUNT, Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION};
    public static final String[] excludedPackages = {getFloatingPackageName("SEC_FLOATING_FEATURE_MESSAGE_CONFIG_PACKAGE_NAME", SamsungThemeConstants.LEGACY_MESSAGE_PACKAGE_NAME), "com.android.settings", "com.sec.knox.knoxsetupwizardclient", "com.sec.chaton", "com.sec.pcw", "com.samsung.android.knox.containercore", "com.sec.watchon.phone", "com.sec.android.automotive.drivelink", "com.samsung.android.app.lifetimes", "com.sec.android.app.shealth", AsPackageName.VOICENOTE, "com.sec.android.app.kidshome", "com.sec.knox.app.container", "com.sec.knox.containeragent", "com.sec.android.app.samsungapps", "tv.peel.smartremote", "com.skt.prod.phonebook", "com.sec.enterprise.knox.express", "com.google.android.apps.walletnfcrel", "com.samsung.android.voc", "com.skt.tservice", "com.sktelecom.minit", "com.skt.prod.dialer", "com.skt.skaf.A000VODBOX", "com.skt.skaf.OA00050017", "com.skt.skaf.A000Z00040", "com.skt.skaf.OA00026910", "com.skt.skaf.l001mtm091", "com.skt.prod.phonebook", "com.skt.smartbill", "com.skt.tbagplus", "com.sktelecom.tguard", "com.skt.tdatacoupon", "com.skb.btvmobile", "com.iloen.melon", "com.nate.android.portalmini", "com.tms", "com.skmc.okcashbag.home_google", "com.elevenst", "com.elevenst.deals", "com.moent.vas", "com.skmnc.gifticon", "com.skt.tmaphot", "com.skplanet.mbuzzer", "com.skt.tgift", "com.sktelecom.tsmartpay", "com.cyworld.camera", "com.kt.android.showtouch", "com.kt.wificm", "com.ktshow.cs", "com.kt.olleh.storefront", "com.kth.kshop", "com.show.greenbill", "com.estsoft.alyac", "com.kt.accessory", "kt.navi", "com.olleh.android.oc2", "com.kt.ollehfamilybox", "com.kt.otv", "com.olleh.webtoon", "com.kt.shodoc", "com.ktmusic.geniemusic", "com.ktcs.whowho", "com.kt.apptong", "com.mtelo.ktAPP", "com.kt.bellringolleh", "com.kt.mpay", "com.kt.aljjapackplus", "com.lguplus.appstore", "com.uplus.onphone", "com.lguplus.mobile.cs", "lg.uplusbox", "com.lgu.app.appbundle", "lgt.call", "com.mnet.app", "com.lguplus.usimsvcm", "com.lguplus.navi", "com.lguplus.paynow", "com.uplus.movielte", "com.estsoft.alyac", "com.lguplus.ltealive", "com.uplus.ipagent", "com.lguplus.homeiot", "com.uplus.baseballhdtv", "com.lgu", "com.lgt.tmoney", "com.lguplus.smartotp", "net.daum.android.map", "com.sds.mms.ui", "com.navitime.local.naviwalk", "jp.id_credit_sp.android", "jp.id_credit_sp.android.devappli", "com.nttdocomo.android.dpoint", "com.nttdocomo.android.voicetranslation", "com.nttdocomo.android.moneyrecord", "com.kddi.android.videopass", "com.nttdocomo.android.photocollection", AsPackageName.SYSTEMUI, "com.sec.sprint.wfcstub", "com.sec.sprint.wfc", "com.oculus.horizon", "com.samsung.android.app.watchmanager", "com.samsung.android.spay", "com.sec.android.easyMover", "com.samsung.android.wms", "com.samsung.android.gear360manager", "com.samsung.android.samsunggear360manager", "com.samsung.android.video360", "com.samsung.android.app.vrsetupwizard", "com.oculus.horizon", "com.samsung.android.game.gamehome", "com.samsung.android.globalroaming", "com.samsung.android.visionintelligence", "com.samsung.android.oneconnect", UploaderBroadcaster.UPLOADER_PACKAGENAME};
    public static final String[] approvedPackages = {"com.android.chrome", "com.google.android.apps", "com.google.android.apps.plus", "com.google.android.apps.docs", "com.google.android.gm", SemInputMethodManagerUtils.PACKAGE_GOOGLE_VOICE, "com.google.android.talk", "com.google.android.apps.maps", "com.google.android.apps.books", "com.google.android.play.games", "com.google.android.music", "com.google.android.videos", "com.google.android.apps.magazines", "com.google.android.youtube", "com.samsung.android.app.memo", "com.sec.keystringscreen", "com.infraware.polarisoffice5", "com.microsoft.office.excel", "com.microsoft.office.powerpoint", "com.microsoft.office.word", "com.hancom.androidpc.viewer.launcher", "com.hancom.office.editor", "com.whatsapp", "com.tencent.mm", "com.facebook.katana", "com.facebook.orca", "com.instagram.android", "com.skype.raider", "com.microsoft.office.onenote", "com.microsoft.skydrive", "com.samsung.android.contacts", "com.sec.android.app.myfiles", SemShareConstants.GALLERY_PACKAGE, "com.samsung.android.app.notes", "com.samsung.android.calendar", "com.samsung.android.email.provider", "com.sec.android.app.camera", "com.sec.android.app.sbrowser"};
    public static final String[] mdmPackages = {"com.samsung.mdmtest1", "com.samsung.mdmtest2", "com.samsung.edmtest", "com.samsung.edmtest1", "com.samsung.edmtest2", "com.samsung.containertool"};
    public static final String SECUREFOLDER_PACKAGE = "com.samsung.knox.securefolder";
    private static List<String> skipPackagesListForNotification = Arrays.asList("android", SECUREFOLDER_PACKAGE);
    private static ISemPersonaManager _instance = null;
    private static final Object pmInstanceLock = new Object();

    public enum AppType {
        IME("TYPE_IME"),
        INSTALLER_ALLOWLIST("installerAllowlist"),
        DISABLED_LAUNCHERS("disabledLaunchers"),
        COM_DISABLED_OWNER_LAUNCHERS("comDisabledOwnerLaunchers");

        private final String mName;

        AppType(String name) {
            this.mName = name;
        }

        public String getName() {
            return this.mName;
        }

        public AppType fromName(String name) {
            for (AppType type : values()) {
                if (type.mName.equals(name)) {
                    return type;
                }
            }
            return null;
        }
    }

    public SemPersonaManager(Context context, ISemPersonaManager service) {
        this.mService = service;
        this.mContext = context;
    }

    /* renamed from: com.samsung.android.knox.SemPersonaManager$2 */
    static /* synthetic */ class C51392 {

        /* renamed from: $SwitchMap$com$samsung$android$knox$SemPersonaManager$KnoxContainerVersion */
        static final /* synthetic */ int[] f3013x68039741;

        static {
            int[] iArr = new int[KnoxContainerVersion.values().length];
            f3013x68039741 = iArr;
            try {
                iArr[KnoxContainerVersion.KNOX_CONTAINER_VERSION_1_0_0.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_0_0.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_1_0.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_2_0.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_3_0.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_3_1.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_4_0.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_4_1.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_5_0.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_5_1.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_5_2.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_6_0.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_6_1.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_7_0.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_7_1.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_8_0.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_9_0.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_0_0.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_1_0.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_2_0.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_2_1.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_3_0.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_4_0.ordinal()] = 23;
            } catch (NoSuchFieldError e23) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_4_1.ordinal()] = 24;
            } catch (NoSuchFieldError e24) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_5_0.ordinal()] = 25;
            } catch (NoSuchFieldError e25) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_6_0.ordinal()] = 26;
            } catch (NoSuchFieldError e26) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_7_0.ordinal()] = 27;
            } catch (NoSuchFieldError e27) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_7_1.ordinal()] = 28;
            } catch (NoSuchFieldError e28) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_8_0.ordinal()] = 29;
            } catch (NoSuchFieldError e29) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_9_0.ordinal()] = 30;
            } catch (NoSuchFieldError e30) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_10_0.ordinal()] = 31;
            } catch (NoSuchFieldError e31) {
            }
            try {
                f3013x68039741[KnoxContainerVersion.KNOX_CONTAINER_VERSION_NONE.ordinal()] = 32;
            } catch (NoSuchFieldError e32) {
            }
        }
    }

    public enum KnoxContainerVersion {
        KNOX_CONTAINER_VERSION_NONE,
        KNOX_CONTAINER_VERSION_1_0_0,
        KNOX_CONTAINER_VERSION_2_0_0,
        KNOX_CONTAINER_VERSION_2_1_0,
        KNOX_CONTAINER_VERSION_2_2_0,
        KNOX_CONTAINER_VERSION_2_3_0,
        KNOX_CONTAINER_VERSION_2_3_1,
        KNOX_CONTAINER_VERSION_2_4_0,
        KNOX_CONTAINER_VERSION_2_4_1,
        KNOX_CONTAINER_VERSION_2_5_0,
        KNOX_CONTAINER_VERSION_2_5_1,
        KNOX_CONTAINER_VERSION_2_5_2,
        KNOX_CONTAINER_VERSION_2_6_0,
        KNOX_CONTAINER_VERSION_2_6_1,
        KNOX_CONTAINER_VERSION_2_7_0,
        KNOX_CONTAINER_VERSION_2_7_1,
        KNOX_CONTAINER_VERSION_2_8_0,
        KNOX_CONTAINER_VERSION_2_9_0,
        KNOX_CONTAINER_VERSION_3_0_0,
        KNOX_CONTAINER_VERSION_3_1_0,
        KNOX_CONTAINER_VERSION_3_2_0,
        KNOX_CONTAINER_VERSION_3_2_1,
        KNOX_CONTAINER_VERSION_3_3_0,
        KNOX_CONTAINER_VERSION_3_4_0,
        KNOX_CONTAINER_VERSION_3_4_1,
        KNOX_CONTAINER_VERSION_3_5_0,
        KNOX_CONTAINER_VERSION_3_6_0,
        KNOX_CONTAINER_VERSION_3_7_0,
        KNOX_CONTAINER_VERSION_3_7_1,
        KNOX_CONTAINER_VERSION_3_8_0,
        KNOX_CONTAINER_VERSION_3_9_0,
        KNOX_CONTAINER_VERSION_3_10_0;

        @Override // java.lang.Enum
        public String toString() {
            switch (C51392.f3013x68039741[ordinal()]) {
            }
            return "3.8.0";
        }

        public int getVersionNumber() {
            switch (C51392.f3013x68039741[ordinal()]) {
            }
            return 380;
        }
    }

    public int getKnoxId(int containerType, boolean onlyActive) {
        List<Integer> ids = getKnoxIds(onlyActive);
        if (ids == null || ids.size() == 0) {
            return -1;
        }
        Log.m94d(TAG, "getKnoxIds not null ");
        switch (containerType) {
            case 2:
                Iterator<Integer> it = ids.iterator();
                while (it.hasNext()) {
                    int id = it.next().intValue();
                    if (isSecureFolderId(id)) {
                        return id;
                    }
                }
            default:
                return -1;
        }
    }

    public int getKioskId() {
        return -1;
    }

    public boolean isKnoxKeyguardShown() {
        return false;
    }

    public boolean isKioskModeEnabled(int containerId) {
        return false;
    }

    public static Bundle getKnoxInfo() {
        return KnoxInfoImpl.getKnoxInfo();
    }

    public static boolean shouldForwardSettingIntentForSecureFolder(String action) {
        for (String blockedAction : SETTINGS_INTENT_FORWARD_BLOCKLIST_FOR_SF) {
            if (blockedAction.equals(action)) {
                return false;
            }
        }
        return true;
    }

    public static int getSecureFolderId(Context context) {
        SemPersonaManager pm = getPersonaService(context);
        if (pm == null) {
            Log.m96e(TAG, "Failed to get SemPersonaManagerService in getSecureFolderId");
            return -1300;
        }
        List<Integer> personaIds = pm.getKnoxIds(true);
        Integer tempPersonaId = -1;
        for (Integer personaId : personaIds) {
            Log.m98i(TAG, "personaId = " + personaId);
            if (checkContainerType(personaId.intValue(), 131072)) {
                if (tempPersonaId.intValue() == -1) {
                    tempPersonaId = personaId;
                }
                if (personaId.intValue() < tempPersonaId.intValue()) {
                    tempPersonaId = personaId;
                }
                Log.m98i(TAG, "SecureFolder personaId = " + personaId);
            }
        }
        return tempPersonaId.intValue() == -1 ? ERROR_INVAILD_CONTAINER_ID : tempPersonaId.intValue();
    }

    public static boolean isDoEnabled(int userId) {
        if (userId != 0) {
            return false;
        }
        return SystemProperties.getBoolean(PROPERTY_DEVICE_OWNER_EXISTS, false);
    }

    public static boolean isPremiumContainer(int userId) {
        return false;
    }

    public static boolean isKnoxId(int userId) {
        if (userId == 0 || isDualAppId(userId)) {
            return false;
        }
        return checkContainerType(userId, 32);
    }

    private static boolean checkContainerType(int containerId, int flag) {
        String value = SystemProperties.get(PROPERTY_KNOX_CONTAINER_INFO);
        if (value != null && value.length() > 0) {
            String[] arr = value.split(":");
            for (String str : arr) {
                String[] info = str.split(",");
                if (info != null && info.length == 2) {
                    int id = Integer.parseInt(info[0]);
                    int flags = Integer.parseInt(info[1]);
                    if (id == containerId && (flag & flags) > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkContainerType(int flag) {
        String value = SystemProperties.get(PROPERTY_KNOX_CONTAINER_INFO);
        if (value != null && value.length() > 0) {
            String[] arr = value.split(":");
            for (String str : arr) {
                String[] info = str.split(",");
                if (info != null && info.length == 2) {
                    int flags = Integer.parseInt(info[1]);
                    if ((flag & flags) > 0) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public static KnoxContainerVersion getKnoxContainerVersion() {
        KnoxContainerVersion containerVersion = KnoxContainerVersion.KNOX_CONTAINER_VERSION_NONE;
        Bundle mKnoxInfo = getKnoxInfo();
        if (mKnoxInfo != null) {
            if ("2.0".equals(mKnoxInfo.getString("version"))) {
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 0) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_0_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 1) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_1_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 2) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_2_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 3) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_3_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 4) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_3_1;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 5) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_4_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 6) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_4_1;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 7) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_5_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 8) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_5_1;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 9) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_5_2;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 10) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_6_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 11) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_6_1;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 12) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_7_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 13) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_7_1;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 14) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_8_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 15) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_9_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 16) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_0_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 17) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_1_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 18) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_2_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 19) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_2_1;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 20) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_3_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 21) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_4_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 22) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_4_1;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 23) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_5_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 24) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_6_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 25) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_7_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 26) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_7_1;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 27) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_8_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 28) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_9_0;
                }
                if (Integer.parseInt(SEC_PRODUCT_FEATURE_KNOX_CONFIG_CONTAINER_VERSION) == 29) {
                    return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_10_0;
                }
                return containerVersion;
            }
            if ("1.0".equals(mKnoxInfo.getString("version"))) {
                Log.m98i(TAG, "mKnoxInfo returns 1.0");
                return KnoxContainerVersion.KNOX_CONTAINER_VERSION_1_0_0;
            }
            Log.m98i(TAG, "mKnoxInfo is empty");
            return KnoxContainerVersion.KNOX_CONTAINER_VERSION_NONE;
        }
        return containerVersion;
    }

    public static final boolean isSepLiteDevice(Context context) {
        return false;
    }

    public static boolean setPackageSettingInstalled(String packageName, boolean installed, int userId) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().setPackageSettingInstalled(packageName, installed, userId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "setPackageSettingInstalled failed!", re);
                return false;
            }
        }
        return false;
    }

    public static void processProfileNameChange(ContentResolver c, int userId, String oldName, String newName) {
        try {
            if (isKnoxId(userId)) {
                Log.m98i(TAG, "processProfileNameChange is called for userId = " + userId + ", oldName - " + oldName + ", newName - " + newName);
                int isCallerToShow = Settings.System.getIntForUser(c, "caller_id_to_show_" + oldName, 0, 0);
                Log.m98i(TAG, "processProfileNameChange isCallerToShow = " + isCallerToShow);
                Settings.System.putIntForUser(c, "caller_id_to_show_" + newName, isCallerToShow, 0);
                Log.m98i(TAG, "processProfileNameChange update is done...");
            } else {
                Log.m98i(TAG, "processProfileNameChange ignoring for userId- " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isDarDualEncryptionEnabled(int userId) {
        UserInfo ui = getUserInfo(userId);
        return ui != null && (ui.flags & 100663296) > 0;
    }

    public static boolean isDarDualEncrypted(int userId) {
        if (!isDarDualEncryptionEnabled(userId)) {
            return false;
        }
        if (!StorageManager.isUserKeyUnlocked(userId)) {
            return true;
        }
        State currentState = StateMachine.getCurrentState(userId);
        return currentState == State.DEVICE_LOCK_DATA_LOCK || currentState == State.DEVICE_UNLOCK_DATA_LOCK;
    }

    public static boolean isDualDARNativeCrypto(int userId) {
        UserInfo ui = getUserInfo(userId);
        return ui != null && (ui.flags & 33554432) == 33554432;
    }

    public static boolean isDualDARCustomCrypto(int userId) {
        UserInfo ui = getUserInfo(userId);
        return ui != null && (ui.flags & 67108864) == 67108864;
    }

    public int setDualDARProfile(Bundle config) {
        Log.m94d(TAG, "setDualDARProfile() " + config);
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.setDualDARProfile(config);
            } catch (Exception e) {
                Log.m103w(TAG, "setDualDARProfile Remote exception", e);
                return -1;
            }
        }
        return -1;
    }

    public Bundle getDualDARProfile() {
        Log.m94d(TAG, "getDualDARProfile");
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.getDualDARProfile();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with enterprise policy service", e);
                return null;
            }
        }
        return null;
    }

    public boolean registerSystemPersonaObserver(ISystemPersonaObserver mSystemPersonaObserver) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.registerSystemPersonaObserver(mSystemPersonaObserver);
            } catch (RemoteException re) {
                Log.m103w(TAG, "Could not registerSystemPersonaObserver a callback", re);
                return false;
            }
        }
        return false;
    }

    public boolean launchPersonaHome(int personaId) {
        boolean result = true;
        if (personaId == -1) {
            result = false;
            personaId = 0;
        }
        Bundle data = new Bundle();
        data.putInt("android.intent.extra.user_handle", personaId);
        ContainerProxy.sendCommand(ContainerProxy.COMMAND_SWITCH_PROFILE, data);
        return result;
    }

    public boolean isFOTAUpgrade() {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.isFOTAUpgrade();
            } catch (RemoteException re) {
                Log.m103w(TAG, "Could not get FOTAUpgrade", re);
                return false;
            }
        }
        return false;
    }

    private KeyguardManager getKeyguardManager() {
        if (mKeyguardManager == null) {
            mKeyguardManager = (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
        }
        return mKeyguardManager;
    }

    public List<Integer> getKnoxIds(boolean onlyActiveList) {
        List<Integer> personaIds = new ArrayList<>();
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                List<UserInfo> users = iSemPersonaManager.getProfiles(0, false);
                for (UserInfo ui : users) {
                    if (!ui.isDualAppProfile() && (!onlyActiveList || ui.isEnabled())) {
                        personaIds.add(Integer.valueOf(ui.f54id));
                    }
                }
            } catch (RemoteException re) {
                Log.m103w(TAG, "Could not getKnoxIds", re);
            }
        }
        return personaIds;
    }

    public boolean exists(int containerId) {
        return checkContainerType(containerId, 32);
    }

    public static boolean isDeviceOrProfileOwnerEnabled() {
        boolean isDoEnabled = SystemProperties.getBoolean(PROPERTY_DEVICE_OWNER_EXISTS, false);
        return checkContainerType(32) || isDoEnabled;
    }

    public IRCPInterface getRCPInterface() {
        Log.m94d(TAG, "in getRCPInterface");
        SemRemoteContentManager rcpm = (SemRemoteContentManager) this.mContext.getSystemService("rcp");
        if (rcpm != null) {
            IRCPInterface rcpInterface = rcpm.getRCPInterface();
            Log.m94d(TAG, "in getRCPInterface rcpInterface: " + rcpInterface);
            return rcpInterface;
        }
        Log.m96e(TAG, "Received getRCPInterface as null from bridge manager");
        return null;
    }

    public static boolean isKnoxVersionSupported(int version) {
        KnoxContainerVersion currentVersion;
        if (version > 0 && (currentVersion = getKnoxContainerVersion()) != null && currentVersion.getVersionNumber() >= version) {
            return true;
        }
        return false;
    }

    public static boolean isKnoxVersionSupported(KnoxContainerVersion version) {
        KnoxContainerVersion currentVersion;
        if (version != null && (currentVersion = getKnoxContainerVersion()) != null && currentVersion.compareTo(version) >= 0) {
            return true;
        }
        return false;
    }

    public void setAppSeparationDefaultPolicy(int userId) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.setAppSeparationDefaultPolicy(userId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Could not call setAppSeparationDefaultPolicy , inside SemPersonaManager with exception:", re);
            }
        }
    }

    public static boolean isSupported(Context context, String tag, String packageName, int userId) {
        Bundle versionInfo = getKnoxInfo();
        if ("default".equals(tag)) {
            return false;
        }
        if ("2.0".equals(versionInfo.getString("version")) && isKnoxId(userId)) {
            if ("default".equals(tag)) {
                return false;
            }
            if (tag.equals(MOVE_FILE_TO_CONTAINER)) {
                return isMoveFilesToContainerAllowed(userId);
            }
            if (tag.equals(MOVE_FILE_TO_OWNER)) {
                return isMoveFilesToOwnerAllowed(userId);
            }
            return true;
        }
        return true;
    }

    public static boolean isKioskModeEnabled(Context context) {
        return false;
    }

    public static Bundle getKnoxInfoForApp(Context ctx, String req) {
        return KnoxInfoImpl.getCachedKnoxInfo(ctx, req);
    }

    public static Bundle getKnoxInfoForApp(Context ctx) {
        return KnoxInfoImpl.getKnoxInfoForApp(ctx);
    }

    public static Bundle exchangeData(Context ctx, Bundle bundle) {
        Log.m94d(TAG, "ERROR | exchangeData is deprecated  // move to knox for contact");
        if (bundle == null || "RequestProxy".equals(bundle.getString("action"))) {
            return null;
        }
        Bundle ret = new Bundle();
        ret.putInt("result", 0);
        return ret;
    }

    private static SemRemoteContentManager getRCPManager(Context context) {
        synchronized (SemPersonaManager.class) {
            if (rcpManager == null) {
                rcpManager = (SemRemoteContentManager) context.getSystemService("rcp");
            }
        }
        return rcpManager;
    }

    private static String getFloatingPackageName(String mFloatingConfig, String mDefaultPkgName) {
        try {
            String mPkgName = SemFloatingFeature.getInstance().getString(mFloatingConfig, mDefaultPkgName);
            return mPkgName;
        } catch (Exception e) {
            Log.m97e(TAG, "getFloatingPackageName failed", e);
            return mDefaultPkgName;
        }
    }

    public boolean isInstallableAppInContainer(Context appContext, int containerUserId, String appPackageName, int appUserId) {
        if (appContext == null || !isKnoxId(containerUserId)) {
            return false;
        }
        return isInstallableAppInContainer(appContext, containerUserId, appPackageName);
    }

    public boolean isInstallableAppInContainer(Context appContext, int containerId, String pkgName) {
        boolean isOnlyForOwner;
        boolean isApprovedPackages = false;
        if (pkgName == null || "".equalsIgnoreCase(pkgName) || SemCapabilities.FEATURE_TAG_NULL.equalsIgnoreCase(pkgName) || !isUserManaged()) {
            return false;
        }
        for (String pkg : excludedPackages) {
            if (pkg.equalsIgnoreCase(pkgName)) {
                return false;
            }
        }
        for (String pkg2 : mdmPackages) {
            if (pkg2.equalsIgnoreCase(pkgName)) {
                return false;
            }
        }
        for (String pkg3 : approvedPackages) {
            if (pkg3.equalsIgnoreCase(pkgName)) {
                isApprovedPackages = true;
            }
        }
        try {
            List<String> disallowPackageList = getSecureFolderPolicy("DisallowPackage", containerId);
            for (String pkg4 : disallowPackageList) {
                if (pkg4.equalsIgnoreCase(pkgName)) {
                    return false;
                }
            }
            if (!isApprovedPackages) {
                List<String> allowPackageList = getSecureFolderPolicy("AllowPackage", containerId);
                for (String pkg5 : allowPackageList) {
                    if (pkg5.equalsIgnoreCase(pkgName)) {
                        isApprovedPackages = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isApprovedPackages) {
            try {
                ApplicationInfo appInfo = appContext.getPackageManager().getApplicationInfo(pkgName, 128);
                if (appInfo == null) {
                    return false;
                }
                Bundle b = appInfo.metaData;
                if (b == null || !b.getBoolean("com.samsung.android.multiuser.install_only_owner", false)) {
                    isOnlyForOwner = false;
                } else {
                    isOnlyForOwner = true;
                }
                if (b == null || !isOnlyForOwner) {
                    if ((appInfo.flags & 1) == 1 || (appInfo.flags & 128) == 128) {
                        Log.m94d(TAG, "has System flag() true - " + pkgName);
                        return false;
                    }
                } else {
                    Log.m94d(TAG, "isOnlyForOwner() true - " + pkgName);
                    return false;
                }
            } catch (PackageManager.NameNotFoundException e2) {
                return false;
            }
        }
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.isPossibleAddAppsToContainer(pkgName, containerId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Could not get isPossibleAddAppsToContainer , inside SemPersonaManager with exception:", re);
            }
        }
        return false;
    }

    public void lockPersona(int personaId) {
        Bundle b = new Bundle();
        b.putInt("android.intent.extra.user_handle", personaId);
        ContainerProxy.sendCommand(ContainerProxy.COMMAND_LOCK_PROFILE, b);
    }

    public boolean getPersonaUserHasBeenShutdownBefore(int personaId) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.getPersonaUserHasBeenShutdownBefore(personaId);
            } catch (RemoteException re) {
                Log.m103w(TAG, "failed to getUserStateForKnox", re);
                return false;
            }
        }
        return false;
    }

    private HashMap<Integer, Integer> getContainerInfo() {
        HashMap<Integer, Integer> existUser = new HashMap<>();
        String value = SystemProperties.get(PROPERTY_KNOX_CONTAINER_INFO);
        Log.m94d("API test", "getContainerInfo: value is " + value);
        if (value != null && value.length() > 0) {
            String[] arr = value.split(":");
            for (String str : arr) {
                String[] info = str.split(",");
                if (info != null && info.length == 2) {
                    int userId = Integer.parseInt(info[0]);
                    int flag = Integer.parseInt(info[1]);
                    existUser.put(Integer.valueOf(userId), Integer.valueOf(flag));
                }
            }
        }
        return existUser;
    }

    public int getFocusedKnoxId() {
        return getFocusedUser();
    }

    public int getFocusedUser() {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.getFocusedUser();
            } catch (RemoteException re) {
                Log.m103w(TAG, "getFocusedUser error", re);
            }
        }
        return UserHandle.getCallingUserId();
    }

    public static String getPersonaName(Context ctx, final int personaId) {
        if (SemDesktopModeManager.LAUNCHER_PACKAGE.equals(ctx.getPackageName()) || CoreSaConstant.PACKAGE_NAME_RECENTS.equals(ctx.getPackageName())) {
            return getWorkName(ctx, personaId);
        }
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
        String profile_name = devicePolicyManager.getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB, new Supplier() { // from class: com.samsung.android.knox.SemPersonaManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                String containerName;
                containerName = SemPersonaManager.getContainerName(null, null, personaId);
                return containerName;
            }
        });
        return profile_name;
    }

    public boolean isProfileNameCustomized(int userId) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.getProfileName(userId) != null;
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona service", re);
            }
        }
        return false;
    }

    public static boolean isNotificationSanitizePolicyForSF(Context context, int userId, String packageName) {
        if (!isSecureFolderId(userId)) {
            return false;
        }
        if (skipPackagesListForNotification.contains(packageName)) {
            Log.m98i(TAG, "Dont sanitize notification for: " + packageName);
            return false;
        }
        try {
            int masterSettingsVal = Settings.Secure.getIntForUser(context.getContentResolver(), "notifications_master_activation", 0, userId);
            Log.m98i(TAG, "masterSettingsVal: " + masterSettingsVal);
            return masterSettingsVal == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getKnoxNameChangedAsUser(int personaId) {
        Log.m94d(TAG, "We will never get null from ui.name");
        UserInfo ui = getUserInfo(personaId);
        if (ui == null) {
            return null;
        }
        String name = ui.name;
        if (name.equals("KNOX") || name.equals("KNOX II")) {
            return null;
        }
        return name;
    }

    public static boolean isSecureFolderId() {
        int userId = UserHandle.getCallingUserId();
        return checkContainerType(userId, 131072);
    }

    public static boolean isSecureFolderId(int id) {
        return checkContainerType(id, 131072);
    }

    public static boolean isSystemApp(Context ctx, String pkgName) {
        try {
            ApplicationInfo appInfo = ctx.getPackageManager().getApplicationInfo(pkgName, 0);
            if (appInfo == null) {
                return false;
            }
            if ((appInfo.flags & 1) == 0 && (appInfo.flags & 128) == 0) {
                if (appInfo.uid >= 10000) {
                    return false;
                }
                return true;
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Drawable getCustomReverseBadgeForCustomContainer(UserHandle user, int density, Context locContext) {
        int userId = user.getIdentifier();
        if (density <= 0) {
            density = locContext.getResources().getDisplayMetrics().densityDpi;
        }
        if (isSecureFolderId(userId)) {
            return Resources.getSystem().getDrawableForDensity(C4337R.drawable.ic_sf_reverse_badge_bottom, density);
        }
        if (isDualAppId(userId)) {
            return Resources.getSystem().getDrawableForDensity(C4337R.drawable.ic_reverse_dualapp_corner, density);
        }
        if (isAppSeparationUserId(userId)) {
            return Resources.getSystem().getDrawableForDensity(C4337R.drawable.screen_badge_270_separated, density);
        }
        return Resources.getSystem().getDrawableForDensity(C4337R.drawable.ws_reverse_ic_screen_work_bottom, density);
    }

    public static Pair<Boolean, Drawable> getCustomBadgeForCustomContainer(UserHandle user, int density, Context locContext) {
        if (isSecureFolderId(user.getIdentifier())) {
            return new Pair<>(true, Resources.getSystem().getDrawableForDensity(C4337R.drawable.ic_sf_badge_bottom, density));
        }
        if (isAppSeparationUserId(user.getIdentifier())) {
            return new Pair<>(true, Resources.getSystem().getDrawableForDensity(C4337R.drawable.screen_badge_separated, density));
        }
        if (isKnoxId(user.getIdentifier())) {
            return new Pair<>(true, Resources.getSystem().getDrawableForDensity(C4337R.drawable.ws_ic_screen_work_bottom, density));
        }
        return new Pair<>(false, null);
    }

    public static Pair<Boolean, Drawable> getNotificationBadge(UserHandle user, int density, Context locContext) {
        byte[] badge = getCustomResource(user.getIdentifier(), CUSTOM_BADGE_ICON);
        if (badge != null) {
            return new Pair<>(true, new BitmapDrawable(locContext.getResources(), BitmapFactory.decodeByteArray(badge, 0, badge.length)));
        }
        UserInfo userInfo = getUserInfo(user.getIdentifier());
        if (userInfo == null) {
            Log.m98i(TAG, "getNotificationBadge/getUserInfo is null, user id is " + user);
            return new Pair<>(false, null);
        }
        if (isSecureFolderId(user.getIdentifier())) {
            Drawable rawDrawable = Resources.getSystem().getDrawableForDensity(C4337R.drawable.stat_sys_secure_folder, density);
            rawDrawable.setTint(Resources.getSystem().getColor(C4337R.color.SecureFolder_notification_badge, null));
            return new Pair<>(true, rawDrawable);
        }
        if (isAppSeparationUserId(user.getIdentifier())) {
            Drawable rawDrawable2 = Resources.getSystem().getDrawableForDensity(C4337R.drawable.knox_basic_separated, density);
            rawDrawable2.setTint(Resources.getSystem().getColor(C4337R.color.SeparatedApps_notification_badge, null));
            return new Pair<>(true, rawDrawable2);
        }
        return new Pair<>(false, null);
    }

    public static SemPersonaManager getPersonaService(Context context) {
        SemPersonaManager pm;
        if (context != null && (pm = (SemPersonaManager) context.getSystemService("persona")) != null && pm.mService != null) {
            return pm;
        }
        return null;
    }

    public static ISemPersonaManager getPersonaService() {
        if (_instance == null) {
            synchronized (pmInstanceLock) {
                if (_instance == null) {
                    _instance = ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
                }
            }
        }
        return _instance;
    }

    public boolean isFotaUpgradeVersionChanged() {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.isFotaUpgradeVersionChanged();
            } catch (RemoteException re) {
                Log.m103w(TAG, "Could not get isFotaUpgradeVersionChanged", re);
                return false;
            }
        }
        return false;
    }

    public boolean isKnoxActivated() {
        return getKnoxIds(false).size() > 0;
    }

    public boolean isUserManaged() {
        return isSecureFolderMetaDataEnabled();
    }

    private boolean isSecureFolderMetaDataEnabled() {
        Bundle b;
        try {
            ApplicationInfo ai = this.mContext.getPackageManager().getApplicationInfo(SECUREFOLDER_PACKAGE, 128);
            if (ai == null || (b = ai.metaData) == null) {
                return false;
            }
            return b.getBoolean("com.samsung.knox.securefolder.enable", false);
        } catch (Exception e) {
            Log.m97e(TAG, "please add proper log here", e);
            return false;
        }
    }

    public ArrayList<Bundle> getMoveToKnoxMenuList(Context appContext) {
        ArrayList<Bundle> returnList = new ArrayList<>();
        if (appContext == null) {
            throw new NullPointerException("appContext cannot be null");
        }
        if (!isKnoxVersionSupported(230)) {
            return returnList;
        }
        if (this.mService != null) {
            try {
                int appUserId = appContext.getUserId();
                return (ArrayList) this.mService.getMoveToKnoxMenuList(appUserId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona service:getMoveToKnoxMenuList", re);
            }
        }
        return returnList;
    }

    public boolean isKnoxReachedToMax() {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            List<UserInfo> users = iSemPersonaManager.getProfiles(0, true);
            if (users == null || users.size() < 2) {
                return false;
            }
            int knoxSize = 0;
            for (UserInfo userInfo : users) {
                knoxSize++;
            }
            if (knoxSize < 2) {
                return false;
            }
            return true;
        } catch (RemoteException e) {
            Log.m97e(TAG, "getProfiles failed", e);
            return false;
        }
    }

    public HashMap<Integer, String> getAllKnoxNamesAndIds(boolean onlyActive) {
        HashMap<Integer, String> nameandids = new HashMap<>();
        UserManager um = (UserManager) this.mContext.getSystemService("user");
        List<Integer> personaIds = getKnoxIds(onlyActive);
        if (personaIds != null && personaIds.size() != 0) {
            Iterator<Integer> it = personaIds.iterator();
            while (it.hasNext()) {
                int personaId = it.next().intValue();
                nameandids.put(Integer.valueOf(personaId), um.getUserInfo(personaId).name);
            }
        }
        return nameandids;
    }

    public static boolean isPkgAllowedToListenKnoxNoti(Context context, String pkgName) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(pkgName, 0);
            if (appInfo != null && (appInfo.flags & 1) == 0) {
                if (context.getPackageManager().checkPermission(Manifest.permission.READ_KNOX_NOTIFICATION, pkgName) != 0) {
                    if (DEBUG) {
                        Log.m94d(TAG, "com.samsung.permission.READ_KNOX_NOTIFICATION not granted");
                    }
                } else {
                    if (DEBUG) {
                        Log.m94d(TAG, "com.samsung.permission.READ_KNOX_NOTIFICATION granted");
                    }
                    return true;
                }
            } else if (appInfo != null && (appInfo.flags & 1) == 1) {
                if (DEBUG) {
                    Log.m94d(TAG, "Application under /system partition");
                }
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public boolean startActivityThroughPersona(Intent intent) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.startActivityThroughPersona(intent);
            } catch (RemoteException re) {
                Log.m97e(TAG, "Could not startActivityThroughPersona", re);
                return false;
            }
        }
        return false;
    }

    public boolean broadcastIntentThroughPersona(Intent intent, int userId) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.broadcastIntentThroughPersona(intent, userId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "Could not broadcastIntentThroughPersona", re);
                return false;
            }
        }
        return false;
    }

    public static UserInfo getUserInfo(int userId) {
        if (getPersonaService() == null) {
            return null;
        }
        try {
            List<UserInfo> users = getPersonaService().getProfiles(0, true);
            for (UserInfo ui : users) {
                if (ui.f54id == userId) {
                    return ui;
                }
            }
        } catch (RemoteException re) {
            Log.m103w(TAG, "Could not getUserInfo", re);
        }
        return null;
    }

    public static boolean isSamsungWorkspace(int userId) {
        if (userId == 0 || !isSecureFolderId(userId)) {
            return false;
        }
        return true;
    }

    public static void setFocusedLauncherId(int userId) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().setFocusedLauncherId(userId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "setFocusedLauncherId failed", re);
            }
        }
    }

    public int getFocusedLauncherId() {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().getFocusedLauncherId();
            } catch (RemoteException re) {
                Log.m97e(TAG, "getFocusedLauncherId failed", re);
                return -1;
            }
        }
        return -1;
    }

    public static boolean setAttributes(int userId, int attr) {
        if (getPersonaService() != null) {
            if (1073741824 == attr) {
                try {
                    Log.m96e(TAG, "setAttributes DualDAR");
                } catch (RemoteException re) {
                    Log.m97e(TAG, "setAttributes failed", re);
                    return false;
                }
            }
            return getPersonaService().setAttributes(userId, attr);
        }
        return false;
    }

    public static int getAttributes(int userId) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().getAttributes(userId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "getAttributes failed", re);
                return -1;
            }
        }
        return -1;
    }

    public static boolean clearAttributes(int userId, int attr) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().clearAttributes(userId, attr);
            } catch (RemoteException re) {
                Log.m97e(TAG, "clearAttributes failed", re);
                return false;
            }
        }
        return false;
    }

    public static void sendContainerEvent(Context c, int userHandle, int containerEvent) {
        sendContainerEvent(c, userHandle, containerEvent, null);
    }

    public static void sendContainerEvent(Context c, int userHandle, int containerEvent, Bundle params) {
        Intent intent = new Intent(ContainerStateReceiver.ACTION_CONTAINER_STATE_RECEIVER);
        if (containerEvent == 9) {
            intent.addFlags(335544320);
        } else {
            intent.addFlags(268435456);
        }
        intent.putExtra(ContainerStateReceiver.EXTRA_CONTIANER_ID, userHandle);
        intent.putExtra(ContainerStateReceiver.EXTRA_CONTIANER_EVENT_ID, containerEvent);
        if (params != null) {
            intent.putExtras(params);
        }
        c.sendBroadcastAsUser(intent, UserHandle.ALL);
    }

    public static String getKnoxCorePackageName() {
        return "com.samsung.android.knox.containercore";
    }

    public static ComponentName getKnoxAdminReceiver() {
        return new ComponentName("com.samsung.android.knox.containercore", CONTAINER_CORE_ADMIN_RECEIVER);
    }

    public static boolean isContainerService(ComponentName name) {
        return getKnoxAdminReceiver().equals(name);
    }

    public static boolean isContainerService(Context ctx, int pid) {
        int uid = Binder.getCallingUid();
        if (UserHandle.getAppId(uid) == 5250) {
            return true;
        }
        if (getPersonaService() != null) {
            try {
                return getPersonaService().isContainerService(pid);
            } catch (RemoteException re) {
                Log.m103w(TAG, "isContainerService error", re);
                return false;
            }
        }
        return false;
    }

    public static boolean isContainerServicebyUID(int uid) {
        return isContainerCorePackageUID(UserHandle.getAppId(uid));
    }

    private static boolean isContainerCorePackageUID(int uid) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().isContainerCorePackageUID(uid);
            } catch (RemoteException re) {
                Log.m103w(TAG, "isContainerCorePackageUID error", re);
                return false;
            }
        }
        return false;
    }

    public Intent createConfirmProfileCredentialIntent(CharSequence title, CharSequence description, int userId) {
        if (!isKnoxId(userId)) {
            return null;
        }
        Intent intent = getKeyguardManager().createConfirmDeviceCredentialIntent(title, description, userId);
        if (intent != null) {
            intent.setAction(INTENT_ACTION_CONFIRM_DEVICE_CREDENTIAL_WITH_USER);
        }
        return intent;
    }

    public static Intent createChangeCredentialIntent(int userid, IntentSender target) {
        Intent intent = new Intent(ACTION_CHANGE_CREDENTIAL_SCREEN);
        intent.putExtra(EXTRA_UNLAUNCHABLE_REASON, 1);
        intent.putExtra("android.intent.extra.user_handle", userid);
        intent.putExtra("android.intent.extra.INTENT", target);
        intent.setPackage(getKnoxCorePackageName());
        return intent;
    }

    public static Intent createLockdownIntent(int userid) {
        Intent intent = new Intent(ACTION_LOCKDOWN_SCREEN);
        intent.putExtra("android.intent.extra.user_handle", userid);
        intent.setFlags(335544320);
        intent.setPackage("com.samsung.android.knox.containercore");
        return intent;
    }

    private static String getWorkProfileName(final Context ctx, int userId) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
        String profile_name = devicePolicyManager.getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB, new Supplier() { // from class: com.samsung.android.knox.SemPersonaManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                String string;
                string = Context.this.getString(C4337R.string.work_profile_name);
                return string;
            }
        });
        return profile_name;
    }

    private static String getWorkName(final Context ctx, int userId) {
        try {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
            String profile_name = devicePolicyManager.getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB, new Supplier() { // from class: com.samsung.android.knox.SemPersonaManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    String string;
                    string = Context.this.getString(C4337R.string.work_name);
                    return string;
                }
            });
            return profile_name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isKnoxIcon(String packageName, String className) {
        if (SECUREFOLDER_PACKAGE.equals(packageName) && className != null && className.contains(ICON_CLASS_SECUREFOLDER_FILE_STORE)) {
            return true;
        }
        if ("android".equals(packageName)) {
            return (className == null || !className.contains(ICON_CLASS_FOR_INTENT_FORWARD_TO_PROFILE) || className.equals(ICON_CLASS_FOR_INTENT_FORWARD_TO_PROFILE)) ? false : true;
        }
        return false;
    }

    public static byte[] getCustomResource(int userId, String resourceType) {
        if (getPersonaService() != null) {
            try {
                String filePath = getPersonaService().getCustomResource(userId, resourceType);
                if (filePath != null && !filePath.isEmpty()) {
                    return readECFile(filePath);
                }
                return null;
            } catch (RemoteException re) {
                Log.m97e(TAG, "getCustomResource failed", re);
            } catch (IOException e) {
                Log.m97e(TAG, "getCustomResource failed", e);
            }
        }
        return null;
    }

    public static boolean setCustomName(int userId, String value) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            boolean ret = getPersonaService().setProfileName(userId, value);
            return ret;
        } catch (Exception e) {
            Log.m97e(TAG, "getCustomName failed", e);
            return false;
        }
    }

    public static boolean setPersonalModeName(int userId, String value) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            boolean ret = getPersonaService().setPersonalModeName(userId, value);
            return ret;
        } catch (Exception e) {
            Log.m97e(TAG, "getPersonalModeName failed", e);
            return false;
        }
    }

    public static Drawable getDrawableCustomBadge(Context locContext, int userId) {
        if (getPersonaService() != null) {
            try {
                byte[] badge = getCustomResource(userId, CUSTOM_BADGE_ICON);
                if (badge != null) {
                    Resources mRes = locContext.getResources();
                    return new BitmapDrawable(mRes, BitmapFactory.decodeByteArray(badge, 0, badge.length));
                }
                return null;
            } catch (Exception e) {
                Log.m97e(TAG, "getDrawableCustomBadge failed", e);
                return null;
            }
        }
        return null;
    }

    private static byte[] readECFile(String fileName) throws IOException {
        int numRead;
        if (fileName == null || fileName.length() == 0) {
            return null;
        }
        File file = new File(fileName);
        FileInputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > 2147483647L) {
            throw new IOException("The file is too big");
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("The file was not completely read: " + file.getName());
        }
        is.close();
        return bytes;
    }

    public static byte[] getKnoxIcon(int userId) {
        if (getPersonaService() == null) {
            return null;
        }
        UserInfo ui = null;
        try {
            List<UserInfo> users = getPersonaService().getProfiles(0, true);
            for (UserInfo i : users) {
                if (i.f54id == userId) {
                    ui = i;
                }
            }
        } catch (RemoteException re) {
            Log.m103w(TAG, "Could not getUserInfo", re);
        }
        if (ui == null || !ui.isManagedProfile()) {
            return null;
        }
        if (ui.isSecureFolder()) {
            return getKnoxIcon(SECUREFOLDER_PACKAGE, null, userId);
        }
        return getKnoxIcon(null, null, userId);
    }

    public static byte[] getKnoxIcon(String packageName, String className, int userId) {
        if (getPersonaService() != null) {
            try {
                if (SECUREFOLDER_PACKAGE.equals(packageName) && className != null && className.contains(ICON_CLASS_SECUREFOLDER_FILE_STORE)) {
                    return null;
                }
                return getPersonaService().getKnoxIcon(packageName, className, userId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "getKnoxIcon failed", re);
            }
        }
        return null;
    }

    public static Drawable getSecureFolderIcon(Context context) {
        String iconName;
        Drawable sfDrawable = null;
        try {
            iconName = Settings.Secure.getStringForUser(context.getContentResolver(), Settings.Secure.SECURE_FOLDER_IMAGE_NAME, 0);
        } catch (Exception e) {
            Log.m96e(TAG, "Exception in getSecureFolderIcon : " + e.getMessage());
        }
        if (iconName != null && !iconName.isEmpty()) {
            sfDrawable = context.getPackageManager().getApplicationIcon(SECUREFOLDER_PACKAGE);
            return sfDrawable;
        }
        context.getPackageManager();
        sfDrawable = context.getPackageManager().semGetApplicationIconForIconTray(SECUREFOLDER_PACKAGE, 32);
        return sfDrawable;
    }

    public String getContainerName(int userId, Context context) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isSecureFolderId(userId)) {
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(SECUREFOLDER_PACKAGE, 0);
            String name = (String) pkgInfo.applicationInfo.loadLabel(context.getPackageManager());
            return name;
        }
        if (isDualAppId(userId)) {
            PackageInfo pkgInfo2 = context.getPackageManager().getPackageInfo("com.samsung.android.da.daagent", 0);
            String name2 = (String) pkgInfo2.applicationInfo.loadLabel(context.getPackageManager());
            return name2;
        }
        if (isSepLiteDevice(this.mContext)) {
            UserManager mUm = (UserManager) this.mContext.getSystemService("user");
            UserInfo uInfo = mUm.getUserInfo(userId);
            if (uInfo != null) {
                return uInfo.name;
            }
        }
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.getContainerName(userId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona service", re);
                return null;
            }
        }
        return null;
    }

    public static String getContainerName(String packageName, String className, int userId) {
        Log.m98i(TAG, "START getContainerName packageName = " + packageName + ", className = " + className + ", userId = " + userId);
        ISemPersonaManager service = getPersonaService();
        try {
            if (service == null) {
                return null;
            }
            return ("android".equals(packageName) && ICON_CLASS_FOR_SECUREFOLDER_FORWARD_TO_PROFILE.equals(className)) ? service.getSecureFolderName() : service.getContainerName(userId);
        } catch (RemoteException re) {
            Log.m95d(TAG, "Failed to call Persona service", re);
            return null;
        } finally {
            Log.m98i(TAG, "END getContainerName packageName");
        }
    }

    public static ComponentName getAdminComponentName(int containerId) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().getAdminComponentName(containerId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "getAdminComponentName failed", re);
                return null;
            }
        }
        return null;
    }

    public void addAppPackageNameToAllowList(int containerId, List<String> appInstallationList) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().addAppPackageNameToAllowList(containerId, appInstallationList);
            } catch (RemoteException re) {
                Log.m97e(TAG, "addAppPackageNameToAllowList failed", re);
            }
        }
    }

    public static void removePartialContainer() {
    }

    public static Bundle getAppSeparationConfig() {
        if (getPersonaService() != null) {
            try {
                Bundle config = getPersonaService().getSeparationConfigfromCache();
                if (config == null) {
                    return null;
                }
                return config;
            } catch (RemoteException re) {
                Log.m97e(TAG, "getAppSeparationConfig failed", re);
            }
        }
        return null;
    }

    public static boolean isAppSeparationUserId(Context context, int userId) {
        UserManager um = (UserManager) context.getSystemService("user");
        UserInfo uInfo = um.getUserInfo(userId);
        if (uInfo != null && uInfo.isUserTypeAppSeparation()) {
            return true;
        }
        return false;
    }

    public static boolean isAppSeparationUserId(int userId) {
        if (userId == 0) {
            return false;
        }
        return checkContainerType(userId, 1073741824);
    }

    public boolean isInSeparatedAppsOnly(String pkgName) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            boolean isInSeparatedAppsOnly = getPersonaService().isInSeparatedAppsOnly(pkgName);
            return isInSeparatedAppsOnly;
        } catch (RemoteException re) {
            Log.m96e(TAG, "Exception has occurred in isInSeparatedAppsOnly: " + re.getMessage());
            return false;
        }
    }

    public List<String> getSeparatedAppsList() {
        List<String> separationApps = new ArrayList<>();
        if (getPersonaService() != null) {
            try {
                return getPersonaService().getSeparatedAppsList();
            } catch (RemoteException e) {
                Log.m96e(TAG, "Exception has occured in getSeparatedAppsList");
                return separationApps;
            }
        }
        return separationApps;
    }

    public List<ResolveInfo> getUpdatedListWithAppSeparation(List<ResolveInfo> originalList) {
        List<ResolveInfo> updatedList = new ArrayList<>();
        if (getPersonaService() != null) {
            try {
                return getPersonaService().getUpdatedListWithAppSeparation(originalList);
            } catch (RemoteException e) {
                Log.m96e(TAG, "Exception has occurred in getUpdatedListWithAppSeparationList");
                return updatedList;
            }
        }
        return updatedList;
    }

    public boolean isAppSeparationPresent() {
        if (getPersonaService() != null) {
            try {
                boolean result = getPersonaService().isAppSeparationPresent();
                return result;
            } catch (RemoteException re) {
                Log.m96e(TAG, "Exception has occured in isAppSeparationPresent: " + re.getMessage());
                return false;
            }
        }
        return false;
    }

    public static void refreshLockTimer(int userId) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().refreshLockTimer(userId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "refreshLockTimer failed", re);
            }
        }
    }

    public static boolean isExternalStorageEnabled(int containerId) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().isExternalStorageEnabled(containerId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "isExternalStorageEnabled failed", re);
                return false;
            }
        }
        return false;
    }

    public static boolean isKnoxWindowExist(int containerId, int visibleFlag, int stackId) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().isKnoxWindowExist(containerId, visibleFlag, stackId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "isKnoxWindowExist failed", re);
                return false;
            }
        }
        return false;
    }

    public static boolean updatePersonaCache(String key, String value) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().updatePersonaCache(key, value);
            } catch (RemoteException re) {
                Log.m97e(TAG, "updatePersonaCache failed", re);
                return false;
            }
        }
        return false;
    }

    public static String getPersonaCacheValue(String key) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().getPersonaCacheValue(key);
            } catch (RemoteException re) {
                Log.m97e(TAG, "getPersonaCacheValue failed", re);
                return null;
            }
        }
        return null;
    }

    public static void hideMultiWindows(int containerId) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().hideMultiWindows(containerId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "hideMultiWindows failed", re);
            }
        }
    }

    public static boolean shouldBlockUsbInterface(int userId, UsbInterface intf) {
        if (intf != null) {
            try {
                if (intf.getInterfaceClass() == 8) {
                    Log.m94d(TAG, "Knox:: claimInterface : request for user -" + userId + " and interface reuqest -" + intf.getInterfaceClass());
                    if (isKnoxVersionSupported(220)) {
                        IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
                        boolean allowed = false;
                        if (lService != null) {
                            try {
                                int callingUid = Binder.getCallingUid();
                                Log.m94d(TAG, "Knox:: claimInterface : calling isPackageAllowedToAccessExternalSdcard for user- " + userId + " and callingUid-" + callingUid);
                                allowed = lService.isPackageAllowedToAccessExternalSdcard(userId, callingUid);
                                Log.m94d(TAG, "Knox:: claimInterface : calling isPackageAllowedToAccessExternalSdcard allowed-" + allowed);
                            } catch (RemoteException re) {
                                Log.m103w(TAG, "doBind(): isPackageAllowedToAccessExternalSdcard on EDMProxy failed! ", re);
                            }
                        }
                        if (!allowed) {
                            Log.m94d(TAG, "Knox:: claimInterface : blocking claim interface request");
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            } catch (Exception e) {
                Log.m103w(TAG, "claimInterface exception ", e);
                return false;
            }
        }
        return false;
    }

    public boolean bindCoreServiceAsUser(ComponentName admin, Intent serviceIntent, ServiceConnection conn, int flags, UserHandle targetUser) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            Context context = this.mContext;
            try {
                IServiceConnection sd = context.getServiceDispatcher(conn, context.getMainThreadHandler(), flags);
                try {
                    serviceIntent.prepareToLeaveProcess(this.mContext);
                    return getPersonaService().bindCoreServiceAsUser(admin, this.mContext.getIApplicationThread(), this.mContext.getActivityToken(), serviceIntent, sd, flags, targetUser.getIdentifier());
                } catch (RemoteException e) {
                    re = e;
                    throw re.rethrowFromSystemServer();
                }
            } catch (RemoteException e2) {
                re = e2;
            }
        } catch (RemoteException e3) {
            re = e3;
        }
    }

    public static void hideScrim() {
        Log.m96e(TAG, "KNOX_UNBUNDLING::deprecated api = hideScrim()");
    }

    public void sendRequestKeyStatus(int containerId) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().sendRequestKeyStatus(containerId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "sendRequestKeyStatus failed", re);
            }
        }
    }

    public int getCurrentContainerType() {
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        UserInfo uInfo = getUserInfo(userId);
        if (isSecureFolderId(userId)) {
            return 2;
        }
        if (uInfo != null && uInfo.isManagedProfile() && !isPremiumContainer(userId)) {
            return 3;
        }
        if (isPremiumContainer(userId)) {
            return 4;
        }
        return 0;
    }

    public int getContainerTypeForUserId(int userId) {
        UserInfo uInfo = getUserInfo(userId);
        if (isSecureFolderId(userId)) {
            return 2;
        }
        if (uInfo != null && uInfo.isManagedProfile() && !isPremiumContainer(userId)) {
            return 3;
        }
        if (isPremiumContainer(userId)) {
            return 4;
        }
        return 0;
    }

    public static boolean isKnoxProfileActivePasswordSufficientForParent(int userId) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().isKnoxProfileActivePasswordSufficientForParent(userId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "isKnoxProfileActivePasswordSufficientForParent failed", re);
                return true;
            }
        }
        return true;
    }

    public static boolean isPasswordSufficientAfterKnoxProfileUnification(int profileUser) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().isPasswordSufficientAfterKnoxProfileUnification(profileUser);
            } catch (RemoteException re) {
                Log.m97e(TAG, "isPasswordSufficientAfterKnoxProfileUnification failed", re);
                return true;
            }
        }
        return true;
    }

    public static void drawKnoxAppBadge(final Context context, final AppWidgetHostView view, final UserHandle user) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.samsung.android.knox.SemPersonaManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ImageView dualAppBadge = new ImageView(Context.this);
                    PackageManager pm = Context.this.getPackageManager();
                    Drawable badgeicon = pm.getUserBadgeForDensity(user, 0);
                    if (badgeicon != null) {
                        dualAppBadge.setImageDrawable(badgeicon);
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(badgeicon.getIntrinsicWidth(), badgeicon.getIntrinsicHeight());
                        params.gravity = 85;
                        view.addView(dualAppBadge, params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000L);
    }

    public static boolean appliedPasswordPolicy(int userId) {
        if (isKnoxId(userId) && getPersonaService() != null) {
            try {
                return getPersonaService().appliedPasswordPolicy(userId);
            } catch (RemoteException re) {
                Log.m97e(TAG, "appliedPasswordPolicy failed", re);
            }
        }
        return false;
    }

    public boolean isShareClipboardDataToOwnerAllowed(int userId) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.isShareClipboardDataToOwnerAllowed(userId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona Policy service", re);
                return false;
            }
        }
        return false;
    }

    public boolean isShareClipboardDataToContainerAllowed(int userId) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.isShareClipboardDataToContainerAllowed(userId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona Policy service", re);
                return false;
            }
        }
        return false;
    }

    public static boolean isMoveFilesToContainerAllowed(int userId) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().isMoveFilesToContainerAllowed(userId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona Policy service", re);
                return false;
            }
        }
        return false;
    }

    public static boolean isMoveFilesToOwnerAllowed(int userId) {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().isMoveFilesToOwnerAllowed(userId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona Policy service", re);
                return false;
            }
        }
        return false;
    }

    public static void logDpmsKA(Bundle b) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().logDpmsKA(b);
            } catch (RemoteException re) {
                Log.m95d(TAG, "logDpmsKA", re);
            }
        }
    }

    public List<String> getSecureFolderPolicy(String key, int userId) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.getSecureFolderPolicy(key, userId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona Policy service", re);
                return null;
            }
        }
        return null;
    }

    public ArrayList<ComponentName> getExcludeComponentList(boolean isForSecureFolder, boolean fromKnox) {
        ArrayList<ComponentName> ret = new ArrayList<>();
        if (isForSecureFolder) {
            int sfId = getSecureFolderId(this.mContext);
            List<String> blockedCompCommon = getSecureFolderPolicy(BLOCKED_SHARING_COMP_COMMON, sfId);
            if (blockedCompCommon != null && blockedCompCommon.size() > 0) {
                ret.addAll(getComponentsFromPolicy(blockedCompCommon));
            }
            if (fromKnox) {
                List<String> blockedCompSecurefolder = getSecureFolderPolicy(BLOCKED_SHARING_COMP_FOR_SECUREFOLDER, sfId);
                if (blockedCompSecurefolder != null && blockedCompSecurefolder.size() > 0) {
                    ret.addAll(getComponentsFromPolicy(blockedCompSecurefolder));
                }
            } else {
                List<String> blockedCompOwner = getSecureFolderPolicy(BLOCKED_SHARING_COMP_FOR_OWNER, sfId);
                if (blockedCompOwner != null && blockedCompOwner.size() > 0) {
                    ret.addAll(getComponentsFromPolicy(blockedCompOwner));
                }
            }
        }
        return ret;
    }

    private ArrayList<ComponentName> getComponentsFromPolicy(List<String> blockedComp) {
        ArrayList<ComponentName> compArrary = new ArrayList<>();
        for (String policy : blockedComp) {
            String[] comp = policy.split("/");
            compArrary.add(new ComponentName(comp[0], comp[1]));
        }
        return compArrary;
    }

    public String getRCPDataPolicy(String appName, String policyProperty) {
        return null;
    }

    public String getRCPDataPolicyForUser(int uid, String appName, String policyProperty) {
        return null;
    }

    public boolean setRCPDataPolicy(String appName, String policyProperty, String value) {
        try {
            ISemPersonaManager iSemPersonaManager = this.mService;
            if (iSemPersonaManager != null) {
                return iSemPersonaManager.setRCPDataPolicy(appName, policyProperty, value);
            }
            Log.m94d(TAG, "in PersonaPolicyManager, setRCPDataPolicy() is not called...");
            return false;
        } catch (RemoteException re) {
            Log.m95d(TAG, "Could not get setRCPDataPolicy , inside PersonaPolicyManager with exception:", re);
            return false;
        }
    }

    public boolean setSecureFolderPolicy(String key, List<String> pkgList, int userId) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.setSecureFolderPolicy(key, pkgList, userId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona Policy service", re);
                return false;
            }
        }
        return false;
    }

    public void CMFALock(int userId) {
        Log.m94d(TAG, "CMFALock userId : " + userId);
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.CMFALock(userId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call CMFALock", re);
            }
        }
    }

    public void CMFAUnLock(int userId) {
        Log.m94d(TAG, "CMFAUnLock userId : " + userId);
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.CMFAUnLock(userId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call CMFAUnLock", re);
            }
        }
    }

    public void updateProfileActivityTimeFromKnox(int userId, long eventTime) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.updateProfileActivityTimeFromKnox(userId, eventTime);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona Policy service", re);
            }
        }
    }

    public void startCountrySelectionActivity(boolean isUnified) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.startCountrySelectionActivity(isUnified);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call startCountrySelectionActivity", re);
            }
        }
    }

    public void startTermsActivity() {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.startTermsActivity();
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call startTermsActivity", re);
            }
        }
    }

    public void postPwdChangeNotificationForDeviceOwner(int userId) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().postPwdChangeNotificationForDeviceOwner(userId);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona Policy service", re);
            }
        }
    }

    public static boolean getRestriction(String property, int userId) {
        Log.m94d(TAG, "getRestriction " + property + " " + userId);
        return true;
    }

    public static boolean isDualAppId(int userId) {
        return SemDualAppManager.isDualAppId(userId);
    }

    public boolean sendKnoxForesightBroadcast(Intent intent) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.sendKnoxForesightBroadcast(intent);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call sendKnoxForesightBroadcast", re);
                return false;
            }
        }
        return false;
    }

    public boolean hasLicensePermission(int uid, String permission) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.hasLicensePermission(uid, permission);
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call hasLicensePermission", re);
                return false;
            }
        }
        return false;
    }

    public static IBasicCommand getKnoxForesightService() {
        if (getPersonaService() != null) {
            try {
                return getPersonaService().getKnoxForesightService();
            } catch (RemoteException re) {
                Log.m95d(TAG, "Failed to call Persona Policy service", re);
                return null;
            }
        }
        return null;
    }
}
