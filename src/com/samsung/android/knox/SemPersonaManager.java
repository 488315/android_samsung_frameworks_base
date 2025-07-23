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
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.om.SamsungThemeConstants;
import android.content.pm.ApplicationInfo;
import android.content.pm.ISystemPersonaObserver;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.R;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.feature.SemFloatingFeature;
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

/* loaded from: classes6.dex */
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
    public static final int CONTAINER_TYPE_NONE = 0;
    public static final int CONTAINER_TYPE_PREMIUM = 4;
    public static final int CONTAINER_TYPE_PRIME = 3;
    public static final int CONTAINER_TYPE_SECURE_FOLDER = 2;
    public static final String CUSTOM_BADGE_ICON = "custom-badge-icon";
    public static final String CUSTOM_CONTAINER_ICON = "custom-container-icon";
    public static final String CUSTOM_NAME_ICON = "custom-name-icon";
    public static final String CUSTOM_PERSONAL_MODEL_LABEL = "custom-name-personal-mode";
    private static final boolean DEBUG = false;
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
    public static final int KA_SCHEMA_VERSION = 8;
    public static final int KNOX_CONFIG_CONTAINER_VERSION = 30;
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
    public static final String PROPERTY_UCM_WPC_PROVISIONED = "persist.sys.knox.UCM_WPC";
    public static final int REMOVE_OP_SUCCESS = 0;
    public static final String SANITIZE_DATA_LOCKSCREEN = "knox-sanitize-data-lockscreen";
    public static final String SECUREFOLDER_ICON_CLASS_SWITCH_TO_HOME = "com.samsung.knox.securefolder.switcher.SwitchToPersonalIcon";
    public static String SECURE_FOLDER_NAME = "secure-folder";
    public static final boolean SEC_PRODUCT_FEATURE_KNOX_SUPPORT_CONTAINER = true;
    private static final boolean SEC_PRODUCT_FEATURE_KNOX_SUPPORT_DUAL_DAR = true;
    public static final boolean SEC_PRODUCT_FEATURE_KNOX_SUPPORT_MDM = true;
    private static final boolean SEC_PRODUCT_FEATURE_KNOX_SUPPORT_UCS = true;
    public static final String SETUP_WIZARD_PKG_NAME = "com.sec.knox.setup";
    public static final String SHORTCUTS = "Shortcuts";
    public static final String SMS = "Sms";
    private static String TAG = "SemPersonaManager";
    public static final int TIMA_COMPROMISED_TYPE1 = 65548;
    public static final int TIMA_COMPROMISED_TYPE2 = 65549;
    public static final int TIMA_COMPROMISED_TYPE3 = 65550;
    public static final int TIMA_COMPROMISED_TYPE4 = 65551;
    public static final int TIMA_VALIDATION_SUCCESS = 0;
    private static final int UNLAUNCHABLE_REASON_PWD_EXPIRED = 1;
    public static final int WHEN_PHONE_RESTART_LOCK_TIMEOUT = -1;
    public static final int WHEN_SCREEN_TURNS_OFF_LOCK_TIMEOUT = 0;
    private static KeyguardManager mKeyguardManager;
    private static ArrayList<Bundle> mMoveToInfo;
    private static SemPersonaManager personaManager;
    private static SemRemoteContentManager rcpManager;
    private final Context mContext;
    private final ISemPersonaManager mService;
    private static final String ADAPT_SOUND_PACKAGE_NAME = "com.sec.hearingadjust";
    static final String[] SHORTCUT_FILTER = {ADAPT_SOUND_PACKAGE_NAME};
    private static final String[] SETTINGS_INTENT_FORWARD_BLOCKLIST_FOR_SF = {Settings.ACTION_USAGE_ACCESS_SETTINGS, Settings.ACTION_ADD_ACCOUNT, Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION};
    public static final String[] excludedPackages = {getFloatingPackageName("SEC_FLOATING_FEATURE_MESSAGE_CONFIG_PACKAGE_NAME", SamsungThemeConstants.LEGACY_MESSAGE_PACKAGE_NAME), "com.android.settings", "com.sec.knox.knoxsetupwizardclient", "com.sec.chaton", "com.sec.pcw", "com.samsung.android.knox.containercore", "com.sec.watchon.phone", "com.sec.android.automotive.drivelink", "com.samsung.android.app.lifetimes", "com.sec.android.app.shealth", AsPackageName.VOICENOTE, "com.sec.android.app.kidshome", "com.sec.knox.app.container", "com.sec.knox.containeragent", "com.sec.android.app.samsungapps", "tv.peel.smartremote", "com.skt.prod.phonebook", "com.sec.enterprise.knox.express", "com.google.android.apps.walletnfcrel", "com.samsung.android.voc", "com.skt.tservice", "com.sktelecom.minit", "com.skt.prod.dialer", "com.skt.skaf.A000VODBOX", "com.skt.skaf.OA00050017", "com.skt.skaf.A000Z00040", "com.skt.skaf.OA00026910", "com.skt.skaf.l001mtm091", "com.skt.prod.phonebook", "com.skt.smartbill", "com.skt.tbagplus", "com.sktelecom.tguard", "com.skt.tdatacoupon", "com.skb.btvmobile", "com.iloen.melon", "com.nate.android.portalmini", "com.tms", "com.skmc.okcashbag.home_google", "com.elevenst", "com.elevenst.deals", "com.moent.vas", "com.skmnc.gifticon", "com.skt.tmaphot", "com.skplanet.mbuzzer", "com.skt.tgift", "com.sktelecom.tsmartpay", "com.cyworld.camera", "com.kt.android.showtouch", "com.kt.wificm", "com.ktshow.cs", "com.kt.olleh.storefront", "com.kth.kshop", "com.show.greenbill", "com.estsoft.alyac", "com.kt.accessory", "kt.navi", "com.olleh.android.oc2", "com.kt.ollehfamilybox", "com.kt.otv", "com.olleh.webtoon", "com.kt.shodoc", "com.ktmusic.geniemusic", "com.ktcs.whowho", "com.kt.apptong", "com.mtelo.ktAPP", "com.kt.bellringolleh", "com.kt.mpay", "com.kt.aljjapackplus", "com.lguplus.appstore", "com.uplus.onphone", "com.lguplus.mobile.cs", "lg.uplusbox", "com.lgu.app.appbundle", "lgt.call", "com.mnet.app", "com.lguplus.usimsvcm", "com.lguplus.navi", "com.lguplus.paynow", "com.uplus.movielte", "com.estsoft.alyac", "com.lguplus.ltealive", "com.uplus.ipagent", "com.lguplus.homeiot", "com.uplus.baseballhdtv", "com.lgu", "com.lgt.tmoney", "com.lguplus.smartotp", "net.daum.android.map", "com.sds.mms.ui", "com.navitime.local.naviwalk", "jp.id_credit_sp.android", "jp.id_credit_sp.android.devappli", "com.nttdocomo.android.dpoint", "com.nttdocomo.android.voicetranslation", "com.nttdocomo.android.moneyrecord", "com.kddi.android.videopass", "com.nttdocomo.android.photocollection", AsPackageName.SYSTEMUI, "com.sec.sprint.wfcstub", "com.sec.sprint.wfc", "com.oculus.horizon", "com.samsung.android.app.watchmanager", "com.samsung.android.spay", "com.sec.android.easyMover", "com.samsung.android.wms", "com.samsung.android.gear360manager", "com.samsung.android.samsunggear360manager", "com.samsung.android.video360", "com.samsung.android.app.vrsetupwizard", "com.oculus.horizon", "com.samsung.android.game.gamehome", "com.samsung.android.globalroaming", "com.samsung.android.visionintelligence", "com.samsung.android.oneconnect", UploaderBroadcaster.UPLOADER_PACKAGENAME};
    public static final String[] approvedPackages = {"com.android.chrome", "com.google.android.apps", "com.google.android.apps.plus", "com.google.android.apps.docs", "com.google.android.gm", "com.google.android.googlequicksearchbox", "com.google.android.talk", "com.google.android.apps.maps", "com.google.android.apps.books", "com.google.android.play.games", "com.google.android.music", "com.google.android.videos", "com.google.android.apps.magazines", "com.google.android.youtube", "com.samsung.android.app.memo", "com.sec.keystringscreen", "com.infraware.polarisoffice5", "com.microsoft.office.excel", "com.microsoft.office.powerpoint", "com.microsoft.office.word", "com.hancom.androidpc.viewer.launcher", "com.hancom.office.editor", "com.whatsapp", "com.tencent.mm", "com.facebook.katana", "com.facebook.orca", "com.instagram.android", "com.skype.raider", "com.microsoft.office.onenote", "com.microsoft.skydrive", "com.samsung.android.contacts", "com.sec.android.app.myfiles", SemShareConstants.GALLERY_PACKAGE, "com.samsung.android.app.notes", "com.samsung.android.calendar", "com.samsung.android.email.provider", "com.sec.android.app.camera", "com.sec.android.app.sbrowser"};
    public static final String[] mdmPackages = {"com.samsung.mdmtest1", "com.samsung.mdmtest2", "com.samsung.edmtest", "com.samsung.edmtest1", "com.samsung.edmtest2", "com.samsung.containertool"};
    public static final String SECUREFOLDER_PACKAGE = "com.samsung.knox.securefolder";
    private static List<String> skipPackagesListForNotification = Arrays.asList("android", SECUREFOLDER_PACKAGE);
    private static ISemPersonaManager _instance = null;
    private static final Object pmInstanceLock = new Object();

    public static boolean isKioskModeEnabled(Context context) {
        return false;
    }

    public static boolean isKnoxVersionSupported(KnoxContainerVersion knoxContainerVersion) {
        return knoxContainerVersion != null;
    }

    public static boolean isPremiumContainer(int i) {
        return false;
    }

    public static final boolean isSepLiteDevice(Context context) {
        return false;
    }

    public static void removePartialContainer() {
    }

    public String getRCPDataPolicy(String str, String str2) {
        return null;
    }

    public String getRCPDataPolicyForUser(int i, String str, String str2) {
        return null;
    }

    public boolean isKioskModeEnabled(int i) {
        return false;
    }

    public boolean isKnoxKeyguardShown() {
        return false;
    }

    public enum AppType {
        IME("TYPE_IME"),
        INSTALLER_ALLOWLIST("installerAllowlist"),
        DISABLED_LAUNCHERS("disabledLaunchers"),
        COM_DISABLED_OWNER_LAUNCHERS("comDisabledOwnerLaunchers");

        private final String mName;

        AppType(String str) {
            this.mName = str;
        }

        public String getName() {
            return this.mName;
        }

        public AppType fromName(String str) {
            for (AppType appType : values()) {
                if (appType.mName.equals(str)) {
                    return appType;
                }
            }
            return null;
        }
    }

    public SemPersonaManager(Context context, ISemPersonaManager iSemPersonaManager) {
        this.mService = iSemPersonaManager;
        this.mContext = context;
    }

    public enum KnoxContainerVersion {
        KNOX_CONTAINER_VERSION_NONE,
        KNOX_CONTAINER_VERSION_3_11_0;

        @Override // java.lang.Enum
        public String toString() {
            if (ordinal() == 1) {
                return "3.11.0";
            }
            return "N/A";
        }

        public int getVersionNumber() {
            return ordinal() != 1 ? -1 : 3110;
        }
    }

    public int getKnoxId(int i, boolean z) {
        List<Integer> knoxIds = getKnoxIds(z);
        if (knoxIds != null && knoxIds.size() != 0) {
            Log.d(TAG, "getKnoxIds not null ");
            if (i == 2) {
                Iterator<Integer> it = knoxIds.iterator();
                while (it.hasNext()) {
                    int intValue = it.next().intValue();
                    if (isSecureFolderId(intValue)) {
                        return intValue;
                    }
                }
            }
        }
        return -1;
    }

    public static Bundle getKnoxInfo() {
        return KnoxInfoImpl.getKnoxInfo();
    }

    public static boolean shouldForwardSettingIntentForSecureFolder(String str) {
        for (String str2 : SETTINGS_INTENT_FORWARD_BLOCKLIST_FOR_SF) {
            if (str2.equals(str)) {
                return false;
            }
        }
        return true;
    }

    public static int getSecureFolderId(Context context) {
        SemPersonaManager personaService = getPersonaService(context);
        if (personaService == null) {
            Log.e(TAG, "Failed to get SemPersonaManagerService in getSecureFolderId");
            return -1300;
        }
        Integer num = -1;
        for (Integer num2 : personaService.getKnoxIds(true)) {
            Log.i(TAG, "personaId = " + num2);
            if (checkContainerType(num2.intValue(), 131072)) {
                if (num.intValue() == -1) {
                    num = num2;
                }
                if (num2.intValue() < num.intValue()) {
                    num = num2;
                }
                Log.i(TAG, "SecureFolder personaId = " + num2);
            }
        }
        return num.intValue() == -1 ? ERROR_INVAILD_CONTAINER_ID : num.intValue();
    }

    public static boolean isDoEnabled(int i) {
        if (i != 0) {
            return false;
        }
        return SystemProperties.getBoolean(PROPERTY_DEVICE_OWNER_EXISTS, false);
    }

    public static boolean isKnoxId(int i) {
        if (i == 0 || isDualAppId(i)) {
            return false;
        }
        return checkContainerType(i, 32) || isSecureFolderId(i);
    }

    private static boolean checkContainerType(int i, int i2) {
        String str = SystemProperties.get(PROPERTY_KNOX_CONTAINER_INFO);
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

    private static boolean checkContainerType(int i) {
        String str = SystemProperties.get(PROPERTY_KNOX_CONTAINER_INFO);
        if (str != null && str.length() > 0) {
            for (String str2 : str.split(":")) {
                String[] split = str2.split(",");
                if (split != null && split.length == 2 && (Integer.parseInt(split[1]) & i) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static KnoxContainerVersion getKnoxContainerVersion() {
        Bundle knoxInfo = getKnoxInfo();
        if (knoxInfo != null && knoxInfo.getString("version") != null && !knoxInfo.getString("version").equals("")) {
            return KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_11_0;
        }
        return KnoxContainerVersion.KNOX_CONTAINER_VERSION_NONE;
    }

    public static boolean setPackageSettingInstalled(String str, boolean z, int i) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().setPackageSettingInstalled(str, z, i);
        } catch (RemoteException e) {
            Log.e(TAG, "setPackageSettingInstalled failed!", e);
            return false;
        }
    }

    public static void processProfileNameChange(ContentResolver contentResolver, int i, String str, String str2) {
        try {
            if (isKnoxId(i)) {
                Log.i(TAG, "processProfileNameChange is called for userId = " + i + ", oldName - " + str + ", newName - " + str2);
                StringBuilder sb = new StringBuilder("caller_id_to_show_");
                sb.append(str);
                int intForUser = Settings.System.getIntForUser(contentResolver, sb.toString(), 0, 0);
                Log.i(TAG, "processProfileNameChange isCallerToShow = " + intForUser);
                Settings.System.putIntForUser(contentResolver, "caller_id_to_show_" + str2, intForUser, 0);
                Log.i(TAG, "processProfileNameChange update is done...");
                return;
            }
            Log.i(TAG, "processProfileNameChange ignoring for userId- " + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isDarDualEncryptionEnabled(int i) {
        UserInfo userInfo = getUserInfo(i);
        return userInfo != null && (userInfo.flags & 100663296) > 0;
    }

    public static boolean isDarDualEncrypted(int i) {
        State currentState;
        if (isDarDualEncryptionEnabled(i)) {
            return !StorageManager.isCeStorageUnlocked(i) || (currentState = StateMachine.getCurrentState(i)) == State.DEVICE_LOCK_DATA_LOCK || currentState == State.DEVICE_UNLOCK_DATA_LOCK;
        }
        return false;
    }

    public static boolean isDualDARNativeCrypto(int i) {
        UserInfo userInfo = getUserInfo(i);
        return userInfo != null && (userInfo.flags & 33554432) == 33554432;
    }

    public static boolean isDualDARCustomCrypto(int i) {
        UserInfo userInfo = getUserInfo(i);
        return userInfo != null && (userInfo.flags & 67108864) == 67108864;
    }

    public int setDualDARProfile(Bundle bundle) {
        Log.d(TAG, "setDualDARProfile() " + bundle);
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return -1;
        }
        try {
            return iSemPersonaManager.setDualDARProfile(bundle);
        } catch (Exception e) {
            Log.w(TAG, "setDualDARProfile Remote exception", e);
            return -1;
        }
    }

    public Bundle getDualDARProfile() {
        Log.d(TAG, "getDualDARProfile");
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return null;
        }
        try {
            return iSemPersonaManager.getDualDARProfile();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public static boolean getUCMDAREncryption() {
        Log.i(TAG, "getUCMDAREncryption");
        return SystemProperties.getBoolean(PROPERTY_UCM_WPC_PROVISIONED, false);
    }

    public int setUCMProfile(Bundle bundle) {
        Log.d(TAG, "setUCMProfile() " + bundle);
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return -1;
        }
        try {
            return iSemPersonaManager.setUCMProfile(bundle);
        } catch (Exception e) {
            Log.w(TAG, "setUCMProfile Remote exception", e);
            return -1;
        }
    }

    public Bundle getUCMProfile() {
        Log.d(TAG, "getUCMProfile");
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return null;
        }
        try {
            return iSemPersonaManager.getUCMProfile();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public int resetUCMProfile() {
        Log.d(TAG, "resetUCMProfile() ");
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return -1;
        }
        try {
            return iSemPersonaManager.resetUCMProfile();
        } catch (Exception e) {
            Log.w(TAG, "resetUCMProfile Remote exception", e);
            return -1;
        }
    }

    public boolean registerSystemPersonaObserver(ISystemPersonaObserver iSystemPersonaObserver) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.registerSystemPersonaObserver(iSystemPersonaObserver);
        } catch (RemoteException e) {
            Log.w(TAG, "Could not registerSystemPersonaObserver a callback", e);
            return false;
        }
    }

    public boolean launchPersonaHome(int i) {
        boolean z;
        if (i == -1) {
            i = 0;
            z = false;
        } else {
            z = true;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.user_handle", i);
        ContainerProxy.sendCommand(ContainerProxy.COMMAND_SWITCH_PROFILE, bundle);
        return z;
    }

    public boolean isFOTAUpgrade() {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.isFOTAUpgrade();
        } catch (RemoteException e) {
            Log.w(TAG, "Could not get FOTAUpgrade", e);
            return false;
        }
    }

    private KeyguardManager getKeyguardManager() {
        if (mKeyguardManager == null) {
            mKeyguardManager = (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
        }
        return mKeyguardManager;
    }

    public List<Integer> getKnoxIds(boolean z) {
        ArrayList arrayList = new ArrayList();
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                for (UserInfo userInfo : iSemPersonaManager.getProfiles(0, false)) {
                    if (!userInfo.isDualAppProfile() && (!z || userInfo.isEnabled())) {
                        arrayList.add(Integer.valueOf(userInfo.id));
                    }
                }
            } catch (RemoteException e) {
                Log.w(TAG, "Could not getKnoxIds", e);
            }
        }
        return arrayList;
    }

    public boolean exists(int i) {
        return checkContainerType(i, 32) || isSecureFolderId(i);
    }

    public static boolean isDeviceOrProfileOwnerEnabled() {
        return checkContainerType(32) || SystemProperties.getBoolean(PROPERTY_DEVICE_OWNER_EXISTS, false);
    }

    public IRCPInterface getRCPInterface() {
        Log.d(TAG, "in getRCPInterface");
        SemRemoteContentManager semRemoteContentManager = (SemRemoteContentManager) this.mContext.getSystemService("rcp");
        if (semRemoteContentManager != null) {
            IRCPInterface rCPInterface = semRemoteContentManager.getRCPInterface();
            Log.d(TAG, "in getRCPInterface rcpInterface: " + rCPInterface);
            return rCPInterface;
        }
        Log.e(TAG, "Received getRCPInterface as null from bridge manager");
        return null;
    }

    public static boolean isKnoxVersionSupported(int i) {
        return i > 0 && getKnoxContainerVersion().getVersionNumber() >= i;
    }

    public void setAppSeparationDefaultPolicy(int i) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.setAppSeparationDefaultPolicy(i);
            } catch (RemoteException e) {
                Log.d(TAG, "Could not call setAppSeparationDefaultPolicy , inside SemPersonaManager with exception:", e);
            }
        }
    }

    public static boolean isSupported(Context context, String str, String str2, int i) {
        Bundle knoxInfo = getKnoxInfo();
        if ("default".equals(str)) {
            return false;
        }
        if (!"2.0".equals(knoxInfo.getString("version")) || !isKnoxId(i)) {
            return true;
        }
        if ("default".equals(str)) {
            return false;
        }
        if (str.equals(MOVE_FILE_TO_CONTAINER)) {
            return isMoveFilesToContainerAllowed(i);
        }
        if (str.equals(MOVE_FILE_TO_OWNER)) {
            return isMoveFilesToOwnerAllowed(i);
        }
        return true;
    }

    public static Bundle getKnoxInfoForApp(Context context, String str) {
        return KnoxInfoImpl.getCachedKnoxInfo(context, str);
    }

    public static Bundle getKnoxInfoForApp(Context context) {
        return KnoxInfoImpl.getKnoxInfoForApp(context);
    }

    public static Bundle exchangeData(Context context, Bundle bundle) {
        Log.d(TAG, "ERROR | exchangeData is deprecated  // move to knox for contact");
        if (bundle == null || "RequestProxy".equals(bundle.getString("action"))) {
            return null;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putInt("result", 0);
        return bundle2;
    }

    private static SemRemoteContentManager getRCPManager(Context context) {
        synchronized (SemPersonaManager.class) {
            if (rcpManager == null) {
                rcpManager = (SemRemoteContentManager) context.getSystemService("rcp");
            }
        }
        return rcpManager;
    }

    private static String getFloatingPackageName(String str, String str2) {
        try {
            return SemFloatingFeature.getInstance().getString(str, str2);
        } catch (Exception e) {
            Log.e(TAG, "getFloatingPackageName failed", e);
            return str2;
        }
    }

    public boolean isInstallableAppInContainer(Context context, int i, String str, int i2) {
        if (context != null && isKnoxId(i)) {
            return isInstallableAppInContainer(context, i, str);
        }
        return false;
    }

    public boolean isInstallableAppInContainer(Context context, int i, String str) {
        ApplicationInfo applicationInfo;
        if (str == null || "".equalsIgnoreCase(str) || PerfettoProtoLogImpl.NULL_STRING.equalsIgnoreCase(str) || !isUserManaged()) {
            return false;
        }
        for (String str2 : excludedPackages) {
            if (str2.equalsIgnoreCase(str)) {
                return false;
            }
        }
        for (String str3 : mdmPackages) {
            if (str3.equalsIgnoreCase(str)) {
                return false;
            }
        }
        boolean z = false;
        for (String str4 : approvedPackages) {
            if (str4.equalsIgnoreCase(str)) {
                z = true;
            }
        }
        try {
            Iterator<String> it = getSecureFolderPolicy("DisallowPackage", i).iterator();
            while (it.hasNext()) {
                if (it.next().equalsIgnoreCase(str)) {
                    return false;
                }
            }
            if (!z) {
                Iterator<String> it2 = getSecureFolderPolicy("AllowPackage", i).iterator();
                while (it2.hasNext()) {
                    if (it2.next().equalsIgnoreCase(str)) {
                        z = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!z) {
            try {
                applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (applicationInfo == null) {
                return false;
            }
            Bundle bundle = applicationInfo.metaData;
            boolean z2 = bundle != null && bundle.getBoolean("com.samsung.android.multiuser.install_only_owner", false);
            if (bundle != null && z2) {
                Log.d(TAG, "isOnlyForOwner() true - " + str);
                return false;
            }
            if ((applicationInfo.flags & 1) == 1 || (applicationInfo.flags & 128) == 128) {
                Log.d(TAG, "has System flag() true - " + str);
                return false;
            }
        }
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.isPossibleAddAppsToContainer(str, i);
            } catch (RemoteException e2) {
                Log.d(TAG, "Could not get isPossibleAddAppsToContainer , inside SemPersonaManager with exception:", e2);
            }
        }
        return false;
    }

    public void lockPersona(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.user_handle", i);
        ContainerProxy.sendCommand(ContainerProxy.COMMAND_LOCK_PROFILE, bundle);
    }

    public boolean getPersonaUserHasBeenShutdownBefore(int i) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.getPersonaUserHasBeenShutdownBefore(i);
        } catch (RemoteException e) {
            Log.w(TAG, "failed to getUserStateForKnox", e);
            return false;
        }
    }

    private HashMap<Integer, Integer> getContainerInfo() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        String str = SystemProperties.get(PROPERTY_KNOX_CONTAINER_INFO);
        Log.d("API test", "getContainerInfo: value is " + str);
        if (str != null && str.length() > 0) {
            for (String str2 : str.split(":")) {
                String[] split = str2.split(",");
                if (split != null && split.length == 2) {
                    hashMap.put(Integer.valueOf(Integer.parseInt(split[0])), Integer.valueOf(Integer.parseInt(split[1])));
                }
            }
        }
        return hashMap;
    }

    public int getFocusedKnoxId() {
        int focusedUser = getFocusedUser();
        if (isKnoxId(focusedUser)) {
            return focusedUser;
        }
        return 0;
    }

    public int getFocusedUserId() {
        return getFocusedUser();
    }

    public int getFocusedUser() {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.getFocusedUser();
            } catch (RemoteException e) {
                Log.w(TAG, "getFocusedUser error", e);
            }
        }
        return UserHandle.getCallingUserId();
    }

    public static String getPersonaName(Context context, final int i) {
        if (SemDesktopModeManager.LAUNCHER_PACKAGE.equals(context.getPackageName()) || CoreSaConstant.PACKAGE_NAME_RECENTS.equals(context.getPackageName())) {
            return getWorkName(context, i);
        }
        return ((DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB, new Supplier() { // from class: com.samsung.android.knox.SemPersonaManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                String containerName;
                containerName = SemPersonaManager.getContainerName(null, null, i);
                return containerName;
            }
        });
    }

    public boolean isProfileNameCustomized(int i) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                return iSemPersonaManager.getProfileName(i) != null;
            } catch (RemoteException e) {
                Log.d(TAG, "Failed to call Persona service", e);
            }
        }
        return false;
    }

    public static boolean isNotificationSanitizePolicyForSF(Context context, int i, String str) {
        if (!isSecureFolderId(i)) {
            return false;
        }
        if (skipPackagesListForNotification.contains(str)) {
            Log.i(TAG, "Dont sanitize notification for: " + str);
            return false;
        }
        try {
            int intForUser = Settings.Secure.getIntForUser(context.getContentResolver(), "notifications_master_activation", 0, i);
            Log.i(TAG, "masterSettingsVal: " + intForUser);
            return intForUser == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getKnoxNameChangedAsUser(int i) {
        Log.d(TAG, "We will never get null from ui.name");
        UserInfo userInfo = getUserInfo(i);
        if (userInfo == null) {
            return null;
        }
        String str = userInfo.name;
        if (str.equals("KNOX") || str.equals("KNOX II")) {
            return null;
        }
        return str;
    }

    public static boolean isSecureFolderId() {
        return checkContainerType(UserHandle.getCallingUserId(), 131072);
    }

    public static boolean isSecureFolderId(int i) {
        return checkContainerType(i, 131072);
    }

    public static boolean isSystemApp(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                if ((applicationInfo.flags & 1) != 0 || (applicationInfo.flags & 128) != 0) {
                    return true;
                }
                if (applicationInfo.uid < 10000) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Drawable getCustomReverseBadgeForCustomContainer(UserHandle userHandle, int i, Context context) {
        int identifier = userHandle.getIdentifier();
        if (i <= 0) {
            i = context.getResources().getDisplayMetrics().densityDpi;
        }
        if (isSecureFolderId(identifier)) {
            return Resources.getSystem().getDrawableForDensity(R.drawable.ic_sf_reverse_badge_bottom, i);
        }
        if (isDualAppId(identifier)) {
            return Resources.getSystem().getDrawableForDensity(R.drawable.ic_reverse_dualapp_corner, i);
        }
        if (isAppSeparationUserId(identifier)) {
            return Resources.getSystem().getDrawableForDensity(R.drawable.screen_badge_270_separated, i);
        }
        return Resources.getSystem().getDrawableForDensity(R.drawable.ws_reverse_ic_screen_work_bottom, i);
    }

    public static Pair<Boolean, Drawable> getCustomBadgeForCustomContainer(UserHandle userHandle, int i, Context context) {
        if (isSecureFolderId(userHandle.getIdentifier())) {
            return new Pair<>(true, Resources.getSystem().getDrawableForDensity(R.drawable.sf_screen_badge, i));
        }
        if (isAppSeparationUserId(userHandle.getIdentifier())) {
            return new Pair<>(true, Resources.getSystem().getDrawableForDensity(R.drawable.screen_badge_separated, i));
        }
        if (isKnoxId(userHandle.getIdentifier())) {
            return new Pair<>(true, Resources.getSystem().getDrawableForDensity(R.drawable.ws_ic_screen_work_bottom, i));
        }
        return new Pair<>(false, null);
    }

    public static Pair<Boolean, Drawable> getNotificationBadge(UserHandle userHandle, int i, Context context) {
        byte[] customResource = getCustomResource(userHandle.getIdentifier(), CUSTOM_BADGE_ICON);
        if (customResource != null) {
            return new Pair<>(true, new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(customResource, 0, customResource.length)));
        }
        if (getUserInfo(userHandle.getIdentifier()) == null) {
            Log.i(TAG, "getNotificationBadge/getUserInfo is null, user id is " + userHandle);
            return new Pair<>(false, null);
        }
        if (isSecureFolderId(userHandle.getIdentifier())) {
            Drawable drawableForDensity = Resources.getSystem().getDrawableForDensity(R.drawable.stat_sys_secure_folder, i);
            drawableForDensity.setTint(Resources.getSystem().getColor(R.color.SecureFolder_notification_badge, null));
            return new Pair<>(true, drawableForDensity);
        }
        if (isAppSeparationUserId(userHandle.getIdentifier())) {
            Drawable drawableForDensity2 = Resources.getSystem().getDrawableForDensity(R.drawable.knox_basic_separated, i);
            drawableForDensity2.setTint(Resources.getSystem().getColor(R.color.SeparatedApps_notification_badge, null));
            return new Pair<>(true, drawableForDensity2);
        }
        return new Pair<>(false, null);
    }

    public static SemPersonaManager getPersonaService(Context context) {
        SemPersonaManager semPersonaManager;
        if (context == null || (semPersonaManager = (SemPersonaManager) context.getSystemService("persona")) == null || semPersonaManager.mService == null) {
            return null;
        }
        return semPersonaManager;
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
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.isFotaUpgradeVersionChanged();
        } catch (RemoteException e) {
            Log.w(TAG, "Could not get isFotaUpgradeVersionChanged", e);
            return false;
        }
    }

    public boolean isKnoxActivated() {
        return getKnoxIds(false).size() > 0;
    }

    public boolean isUserManaged() {
        return isSecureFolderMetaDataEnabled();
    }

    private boolean isSecureFolderMetaDataEnabled() {
        Bundle bundle;
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(SECUREFOLDER_PACKAGE, 128);
            if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
                if (bundle.getBoolean("com.samsung.knox.securefolder.enable", false)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "please add proper log here", e);
            return false;
        }
    }

    public ArrayList<Bundle> getMoveToKnoxMenuList(Context context) {
        ArrayList<Bundle> arrayList = new ArrayList<>();
        if (context == null) {
            throw new NullPointerException("appContext cannot be null");
        }
        if (isKnoxVersionSupported(230) && this.mService != null) {
            try {
                return (ArrayList) this.mService.getMoveToKnoxMenuList(context.getUserId());
            } catch (RemoteException e) {
                Log.d(TAG, "Failed to call Persona service:getMoveToKnoxMenuList", e);
            }
        }
        return arrayList;
    }

    public boolean isKnoxReachedToMax() {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            List<UserInfo> profiles = iSemPersonaManager.getProfiles(0, true);
            if (profiles != null && profiles.size() >= 2) {
                int i = 0;
                for (UserInfo userInfo : profiles) {
                    i++;
                }
                if (i >= 2) {
                    return true;
                }
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "getProfiles failed", e);
            return false;
        }
    }

    public HashMap<Integer, String> getAllKnoxNamesAndIds(boolean z) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        List<Integer> knoxIds = getKnoxIds(z);
        if (knoxIds != null && knoxIds.size() != 0) {
            for (Integer num : knoxIds) {
                hashMap.put(num, userManager.getUserInfo(num.intValue()).name);
            }
        }
        return hashMap;
    }

    public static boolean isPkgAllowedToListenKnoxNoti(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo != null && (applicationInfo.flags & 1) == 0) {
                if (context.getPackageManager().checkPermission(Manifest.permission.READ_KNOX_NOTIFICATION, str) == 0) {
                    return true;
                }
            } else if (applicationInfo != null && (applicationInfo.flags & 1) == 1) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public boolean startActivityThroughPersona(Intent intent) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.startActivityThroughPersona(intent);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not startActivityThroughPersona", e);
            return false;
        }
    }

    public boolean broadcastIntentThroughPersona(Intent intent, int i) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.broadcastIntentThroughPersona(intent, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not broadcastIntentThroughPersona", e);
            return false;
        }
    }

    public static UserInfo getUserInfo(int i) {
        if (getPersonaService() == null) {
            return null;
        }
        try {
            for (UserInfo userInfo : getPersonaService().getProfiles(0, true)) {
                if (userInfo.id == i) {
                    return userInfo;
                }
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Could not getUserInfo", e);
        }
        return null;
    }

    public static boolean isSamsungWorkspace(int i) {
        return i != 0 && isSecureFolderId(i);
    }

    public static void setFocusedLauncherId(int i) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().setFocusedLauncherId(i);
            } catch (RemoteException e) {
                Log.e(TAG, "setFocusedLauncherId failed", e);
            }
        }
    }

    public int getFocusedLauncherId() {
        if (getPersonaService() == null) {
            return -1;
        }
        try {
            return getPersonaService().getFocusedLauncherId();
        } catch (RemoteException e) {
            Log.e(TAG, "getFocusedLauncherId failed", e);
            return -1;
        }
    }

    public static boolean setAttributes(int i, int i2) {
        if (getPersonaService() == null) {
            return false;
        }
        if (1073741824 == i2) {
            try {
                Log.e(TAG, "setAttributes DualDAR");
            } catch (RemoteException e) {
                Log.e(TAG, "setAttributes failed", e);
                return false;
            }
        }
        return getPersonaService().setAttributes(i, i2);
    }

    public static int getAttributes(int i) {
        if (getPersonaService() == null) {
            return -1;
        }
        try {
            return getPersonaService().getAttributes(i);
        } catch (RemoteException e) {
            Log.e(TAG, "getAttributes failed", e);
            return -1;
        }
    }

    public static boolean clearAttributes(int i, int i2) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().clearAttributes(i, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "clearAttributes failed", e);
            return false;
        }
    }

    public static void sendContainerEvent(Context context, int i, int i2) {
        sendContainerEvent(context, i, i2, null);
    }

    public static void sendContainerEvent(Context context, int i, int i2, Bundle bundle) {
        Intent intent = new Intent(ContainerStateReceiver.ACTION_CONTAINER_STATE_RECEIVER);
        if (i2 == 9) {
            intent.addFlags(335544320);
        } else {
            intent.addFlags(268435456);
        }
        intent.putExtra(ContainerStateReceiver.EXTRA_CONTIANER_ID, i);
        intent.putExtra(ContainerStateReceiver.EXTRA_CONTIANER_EVENT_ID, i2);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.sendBroadcastAsUser(intent, UserHandle.ALL);
    }

    public static String getKnoxCorePackageName() {
        return "com.samsung.android.knox.containercore";
    }

    public static ComponentName getKnoxAdminReceiver() {
        return new ComponentName("com.samsung.android.knox.containercore", CONTAINER_CORE_ADMIN_RECEIVER);
    }

    public static boolean isContainerService(ComponentName componentName) {
        return getKnoxAdminReceiver().equals(componentName);
    }

    public static boolean isContainerService(Context context, int i) {
        if (UserHandle.getAppId(Binder.getCallingUid()) == 5250) {
            return true;
        }
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().isContainerService(i);
        } catch (RemoteException e) {
            Log.w(TAG, "isContainerService error", e);
            return false;
        }
    }

    public static boolean isContainerServicebyUID(int i) {
        return isContainerCorePackageUID(UserHandle.getAppId(i));
    }

    private static boolean isContainerCorePackageUID(int i) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().isContainerCorePackageUID(i);
        } catch (RemoteException e) {
            Log.w(TAG, "isContainerCorePackageUID error", e);
            return false;
        }
    }

    public Intent createConfirmProfileCredentialIntent(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (!isKnoxId(i)) {
            return null;
        }
        Intent createConfirmDeviceCredentialIntent = getKeyguardManager().createConfirmDeviceCredentialIntent(charSequence, charSequence2, i);
        if (createConfirmDeviceCredentialIntent != null) {
            createConfirmDeviceCredentialIntent.setAction(INTENT_ACTION_CONFIRM_DEVICE_CREDENTIAL_WITH_USER);
        }
        return createConfirmDeviceCredentialIntent;
    }

    public static Intent createChangeCredentialIntent(int i, IntentSender intentSender) {
        Intent intent = new Intent(ACTION_CHANGE_CREDENTIAL_SCREEN);
        intent.putExtra(EXTRA_UNLAUNCHABLE_REASON, 1);
        intent.putExtra("android.intent.extra.user_handle", i);
        intent.putExtra("android.intent.extra.INTENT", intentSender);
        intent.setPackage(getKnoxCorePackageName());
        return intent;
    }

    public static Intent createLockdownIntent(int i) {
        Intent intent = new Intent(ACTION_LOCKDOWN_SCREEN);
        intent.putExtra("android.intent.extra.user_handle", i);
        intent.setFlags(335544320);
        intent.setPackage("com.samsung.android.knox.containercore");
        return intent;
    }

    private static String getWorkProfileName(final Context context, int i) {
        return ((DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB, new Supplier() { // from class: com.samsung.android.knox.SemPersonaManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                String string;
                string = Context.this.getString(R.string.work_profile_name);
                return string;
            }
        });
    }

    private static String getWorkName(final Context context, int i) {
        try {
            return ((DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB, new Supplier() { // from class: com.samsung.android.knox.SemPersonaManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    String string;
                    string = Context.this.getString(R.string.work_name);
                    return string;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isKnoxIcon(String str, String str2) {
        if (SECUREFOLDER_PACKAGE.equals(str) && str2 != null && str2.contains(ICON_CLASS_SECUREFOLDER_FILE_STORE)) {
            return true;
        }
        return "android".equals(str) && str2 != null && str2.contains(ICON_CLASS_FOR_INTENT_FORWARD_TO_PROFILE) && !str2.equals(ICON_CLASS_FOR_INTENT_FORWARD_TO_PROFILE);
    }

    public static byte[] getCustomResource(int i, String str) {
        if (getPersonaService() != null) {
            try {
                String customResource = getPersonaService().getCustomResource(i, str);
                if (customResource != null && !customResource.isEmpty()) {
                    return readECFile(customResource);
                }
                return null;
            } catch (RemoteException e) {
                Log.e(TAG, "getCustomResource failed", e);
            } catch (IOException e2) {
                Log.e(TAG, "getCustomResource failed", e2);
            }
        }
        return null;
    }

    public static boolean setCustomName(int i, String str) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().setProfileName(i, str);
        } catch (Exception e) {
            Log.e(TAG, "getCustomName failed", e);
            return false;
        }
    }

    public static boolean setPersonalModeName(int i, String str) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().setPersonalModeName(i, str);
        } catch (Exception e) {
            Log.e(TAG, "getPersonalModeName failed", e);
            return false;
        }
    }

    public static Drawable getDrawableCustomBadge(Context context, int i) {
        if (getPersonaService() == null) {
            return null;
        }
        try {
            byte[] customResource = getCustomResource(i, CUSTOM_BADGE_ICON);
            if (customResource != null) {
                return new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(customResource, 0, customResource.length));
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "getDrawableCustomBadge failed", e);
            return null;
        }
    }

    private static byte[] readECFile(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return null;
        }
        File file = new File(str);
        FileInputStream fileInputStream = new FileInputStream(file);
        long length = file.length();
        if (length > 2147483647L) {
            throw new IOException("The file is too big");
        }
        int i = (int) length;
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int read = fileInputStream.read(bArr, i2, i - i2);
            if (read < 0) {
                break;
            }
            i2 += read;
        }
        if (i2 < i) {
            throw new IOException("The file was not completely read: " + file.getName());
        }
        fileInputStream.close();
        return bArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0036 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getKnoxIcon(int r5) {
        /*
            com.samsung.android.knox.ISemPersonaManager r0 = getPersonaService()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            com.samsung.android.knox.ISemPersonaManager r0 = getPersonaService()     // Catch: android.os.RemoteException -> L2b
            r2 = 0
            r3 = 1
            java.util.List r0 = r0.getProfiles(r2, r3)     // Catch: android.os.RemoteException -> L2b
            java.util.Iterator r0 = r0.iterator()     // Catch: android.os.RemoteException -> L2b
            r2 = r1
        L17:
            boolean r3 = r0.hasNext()     // Catch: android.os.RemoteException -> L29
            if (r3 == 0) goto L34
            java.lang.Object r3 = r0.next()     // Catch: android.os.RemoteException -> L29
            android.content.pm.UserInfo r3 = (android.content.pm.UserInfo) r3     // Catch: android.os.RemoteException -> L29
            int r4 = r3.id     // Catch: android.os.RemoteException -> L29
            if (r4 != r5) goto L17
            r2 = r3
            goto L17
        L29:
            r0 = move-exception
            goto L2d
        L2b:
            r0 = move-exception
            r2 = r1
        L2d:
            java.lang.String r3 = com.samsung.android.knox.SemPersonaManager.TAG
            java.lang.String r4 = "Could not getUserInfo"
            android.util.Log.w(r3, r4, r0)
        L34:
            if (r2 != 0) goto L37
            return r1
        L37:
            boolean r0 = r2.isSecureFolder()
            if (r0 == 0) goto L44
            java.lang.String r0 = "com.samsung.knox.securefolder"
            byte[] r5 = getKnoxIcon(r0, r1, r5)
            return r5
        L44:
            boolean r0 = r2.isManagedProfile()
            if (r0 != 0) goto L4b
            return r1
        L4b:
            byte[] r5 = getKnoxIcon(r1, r1, r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.SemPersonaManager.getKnoxIcon(int):byte[]");
    }

    public static byte[] getKnoxIcon(String str, String str2, int i) {
        if (getPersonaService() != null) {
            try {
                if (SECUREFOLDER_PACKAGE.equals(str) && str2 != null && str2.contains(ICON_CLASS_SECUREFOLDER_FILE_STORE)) {
                    return null;
                }
                return getPersonaService().getKnoxIcon(str, str2, i);
            } catch (RemoteException e) {
                Log.e(TAG, "getKnoxIcon failed", e);
            }
        }
        return null;
    }

    public static Drawable getSecureFolderIcon(Context context) {
        try {
            String stringForUser = Settings.Secure.getStringForUser(context.getContentResolver(), Settings.Secure.SECURE_FOLDER_IMAGE_NAME, 0);
            if (stringForUser != null && !stringForUser.isEmpty()) {
                return context.getPackageManager().getApplicationIcon(SECUREFOLDER_PACKAGE);
            }
            context.getPackageManager();
            return context.getPackageManager().semGetApplicationIconForIconTray(SECUREFOLDER_PACKAGE, 32);
        } catch (Exception e) {
            Log.e(TAG, "Exception in getSecureFolderIcon : " + e.getMessage());
            return null;
        }
    }

    public String getContainerName(int i, Context context) {
        UserInfo userInfo;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isSecureFolderId(i)) {
            return (String) context.getPackageManager().getPackageInfo(SECUREFOLDER_PACKAGE, 0).applicationInfo.loadLabel(context.getPackageManager());
        }
        if (isDualAppId(i)) {
            return (String) context.getPackageManager().getPackageInfo("com.samsung.android.da.daagent", 0).applicationInfo.loadLabel(context.getPackageManager());
        }
        if (isSepLiteDevice(this.mContext) && (userInfo = ((UserManager) this.mContext.getSystemService("user")).getUserInfo(i)) != null) {
            return userInfo.name;
        }
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return null;
        }
        try {
            return iSemPersonaManager.getContainerName(i);
        } catch (RemoteException e2) {
            Log.d(TAG, "Failed to call Persona service", e2);
            return null;
        }
    }

    public static String getContainerName(String str, String str2, int i) {
        String containerName;
        Log.i(TAG, "START getContainerName packageName = " + str + ", className = " + str2 + ", userId = " + i);
        ISemPersonaManager personaService = getPersonaService();
        try {
            if (personaService == null) {
                return null;
            }
            if ("android".equals(str) && ICON_CLASS_FOR_SECUREFOLDER_FORWARD_TO_PROFILE.equals(str2)) {
                containerName = personaService.getSecureFolderName();
            } else {
                containerName = personaService.getContainerName(i);
            }
            return containerName;
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call Persona service", e);
            return null;
        } finally {
            Log.i(TAG, "END getContainerName packageName");
        }
    }

    public static ComponentName getAdminComponentName(int i) {
        if (getPersonaService() == null) {
            return null;
        }
        try {
            return getPersonaService().getAdminComponentName(i);
        } catch (RemoteException e) {
            Log.e(TAG, "getAdminComponentName failed", e);
            return null;
        }
    }

    public void addAppPackageNameToAllowList(int i, List<String> list) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().addAppPackageNameToAllowList(i, list);
            } catch (RemoteException e) {
                Log.e(TAG, "addAppPackageNameToAllowList failed", e);
            }
        }
    }

    public static Bundle getAppSeparationConfig() {
        if (getPersonaService() != null) {
            try {
                Bundle separationConfigfromCache = getPersonaService().getSeparationConfigfromCache();
                if (separationConfigfromCache == null) {
                    return null;
                }
                return separationConfigfromCache;
            } catch (RemoteException e) {
                Log.e(TAG, "getAppSeparationConfig failed", e);
            }
        }
        return null;
    }

    public static boolean isAppSeparationUserId(int i) {
        if (i == 0) {
            return false;
        }
        return checkContainerType(i, 1073741824);
    }

    public boolean isInSeparatedAppsOnly(String str) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().isInSeparatedAppsOnly(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Exception has occurred in isInSeparatedAppsOnly: " + e.getMessage());
            return false;
        }
    }

    public List<String> getSeparatedAppsList() {
        ArrayList arrayList = new ArrayList();
        if (getPersonaService() != null) {
            try {
                return getPersonaService().getSeparatedAppsList();
            } catch (RemoteException unused) {
                Log.e(TAG, "Exception has occured in getSeparatedAppsList");
            }
        }
        return arrayList;
    }

    public List<ResolveInfo> getUpdatedListWithAppSeparation(List<ResolveInfo> list) {
        ArrayList arrayList = new ArrayList();
        if (getPersonaService() != null) {
            try {
                return getPersonaService().getUpdatedListWithAppSeparation(list);
            } catch (RemoteException unused) {
                Log.e(TAG, "Exception has occurred in getUpdatedListWithAppSeparationList");
            }
        }
        return arrayList;
    }

    public boolean isAppSeparationPresent() {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().isAppSeparationPresent();
        } catch (RemoteException e) {
            Log.e(TAG, "Exception has occured in isAppSeparationPresent: " + e.getMessage());
            return false;
        }
    }

    public static void refreshLockTimer(int i) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().refreshLockTimer(i);
            } catch (RemoteException e) {
                Log.e(TAG, "refreshLockTimer failed", e);
            }
        }
    }

    public static boolean isExternalStorageEnabled(int i) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().isExternalStorageEnabled(i);
        } catch (RemoteException e) {
            Log.e(TAG, "isExternalStorageEnabled failed", e);
            return false;
        }
    }

    public static boolean isKnoxWindowExist(int i, int i2, int i3) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().isKnoxWindowExist(i, i2, i3);
        } catch (RemoteException e) {
            Log.e(TAG, "isKnoxWindowExist failed", e);
            return false;
        }
    }

    public static boolean updatePersonaCache(String str, String str2) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().updatePersonaCache(str, str2);
        } catch (RemoteException e) {
            Log.e(TAG, "updatePersonaCache failed", e);
            return false;
        }
    }

    public static String getPersonaCacheValue(String str) {
        if (getPersonaService() == null) {
            return null;
        }
        try {
            return getPersonaService().getPersonaCacheValue(str);
        } catch (RemoteException e) {
            Log.e(TAG, "getPersonaCacheValue failed", e);
            return null;
        }
    }

    public static void hideMultiWindows(int i) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().hideMultiWindows(i);
            } catch (RemoteException e) {
                Log.e(TAG, "hideMultiWindows failed", e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x007e A[Catch: Exception -> 0x0087, TRY_LEAVE, TryCatch #2 {Exception -> 0x0087, blocks: (B:6:0x0009, B:8:0x0011, B:10:0x0036, B:18:0x003c, B:21:0x005d, B:14:0x007e, B:24:0x0073), top: B:5:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean shouldBlockUsbInterface(int r6, android.hardware.usb.UsbInterface r7) {
        /*
            java.lang.String r0 = "Knox:: claimInterface : calling isPackageAllowedToAccessExternalSdcard allowed-"
            java.lang.String r1 = "Knox:: claimInterface : calling isPackageAllowedToAccessExternalSdcard for user- "
            java.lang.String r2 = "Knox:: claimInterface : request for user -"
            r3 = 0
            if (r7 == 0) goto L8f
            int r4 = r7.getInterfaceClass()     // Catch: java.lang.Exception -> L87
            r5 = 8
            if (r4 != r5) goto L8f
            java.lang.String r4 = com.samsung.android.knox.SemPersonaManager.TAG     // Catch: java.lang.Exception -> L87
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L87
            r5.<init>(r2)     // Catch: java.lang.Exception -> L87
            r5.append(r6)     // Catch: java.lang.Exception -> L87
            java.lang.String r2 = " and interface reuqest -"
            r5.append(r2)     // Catch: java.lang.Exception -> L87
            int r7 = r7.getInterfaceClass()     // Catch: java.lang.Exception -> L87
            r5.append(r7)     // Catch: java.lang.Exception -> L87
            java.lang.String r7 = r5.toString()     // Catch: java.lang.Exception -> L87
            android.util.Log.d(r4, r7)     // Catch: java.lang.Exception -> L87
            r7 = 220(0xdc, float:3.08E-43)
            boolean r7 = isKnoxVersionSupported(r7)     // Catch: java.lang.Exception -> L87
            if (r7 == 0) goto L8f
            android.sec.enterprise.IEDMProxy r7 = android.sec.enterprise.EnterpriseDeviceManager.EDMProxyServiceHelper.getService()     // Catch: java.lang.Exception -> L87
            if (r7 == 0) goto L7b
            int r2 = android.os.Binder.getCallingUid()     // Catch: android.os.RemoteException -> L71 java.lang.Exception -> L87
            java.lang.String r4 = com.samsung.android.knox.SemPersonaManager.TAG     // Catch: android.os.RemoteException -> L71 java.lang.Exception -> L87
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L71 java.lang.Exception -> L87
            r5.<init>(r1)     // Catch: android.os.RemoteException -> L71 java.lang.Exception -> L87
            r5.append(r6)     // Catch: android.os.RemoteException -> L71 java.lang.Exception -> L87
            java.lang.String r1 = " and callingUid-"
            r5.append(r1)     // Catch: android.os.RemoteException -> L71 java.lang.Exception -> L87
            r5.append(r2)     // Catch: android.os.RemoteException -> L71 java.lang.Exception -> L87
            java.lang.String r1 = r5.toString()     // Catch: android.os.RemoteException -> L71 java.lang.Exception -> L87
            android.util.Log.d(r4, r1)     // Catch: android.os.RemoteException -> L71 java.lang.Exception -> L87
            boolean r6 = r7.isPackageAllowedToAccessExternalSdcard(r6, r2)     // Catch: android.os.RemoteException -> L71 java.lang.Exception -> L87
            java.lang.String r7 = com.samsung.android.knox.SemPersonaManager.TAG     // Catch: android.os.RemoteException -> L6f java.lang.Exception -> L87
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L6f java.lang.Exception -> L87
            r1.<init>(r0)     // Catch: android.os.RemoteException -> L6f java.lang.Exception -> L87
            r1.append(r6)     // Catch: android.os.RemoteException -> L6f java.lang.Exception -> L87
            java.lang.String r0 = r1.toString()     // Catch: android.os.RemoteException -> L6f java.lang.Exception -> L87
            android.util.Log.d(r7, r0)     // Catch: android.os.RemoteException -> L6f java.lang.Exception -> L87
            goto L7c
        L6f:
            r7 = move-exception
            goto L73
        L71:
            r7 = move-exception
            r6 = r3
        L73:
            java.lang.String r0 = com.samsung.android.knox.SemPersonaManager.TAG     // Catch: java.lang.Exception -> L87
            java.lang.String r1 = "doBind(): isPackageAllowedToAccessExternalSdcard on EDMProxy failed! "
            android.util.Log.w(r0, r1, r7)     // Catch: java.lang.Exception -> L87
            goto L7c
        L7b:
            r6 = r3
        L7c:
            if (r6 != 0) goto L8f
            java.lang.String r6 = com.samsung.android.knox.SemPersonaManager.TAG     // Catch: java.lang.Exception -> L87
            java.lang.String r7 = "Knox:: claimInterface : blocking claim interface request"
            android.util.Log.d(r6, r7)     // Catch: java.lang.Exception -> L87
            r6 = 1
            return r6
        L87:
            r6 = move-exception
            java.lang.String r7 = com.samsung.android.knox.SemPersonaManager.TAG
            java.lang.String r0 = "claimInterface exception "
            android.util.Log.w(r7, r0, r6)
        L8f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.SemPersonaManager.shouldBlockUsbInterface(int, android.hardware.usb.UsbInterface):boolean");
    }

    public boolean bindCoreServiceAsUser(ComponentName componentName, Intent intent, ServiceConnection serviceConnection, int i, UserHandle userHandle) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            Context context = this.mContext;
            IServiceConnection serviceDispatcher = context.getServiceDispatcher(serviceConnection, context.getMainThreadHandler(), i);
            intent.prepareToLeaveProcess(this.mContext);
            return getPersonaService().bindCoreServiceAsUser(componentName, this.mContext.getIApplicationThread(), this.mContext.getActivityToken(), intent, serviceDispatcher, i, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void hideScrim() {
        Log.e(TAG, "KNOX_UNBUNDLING::deprecated api = hideScrim()");
    }

    public void sendRequestKeyStatus(int i) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().sendRequestKeyStatus(i);
            } catch (RemoteException e) {
                Log.e(TAG, "sendRequestKeyStatus failed", e);
            }
        }
    }

    public int getCurrentContainerType() {
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        UserInfo userInfo = getUserInfo(userId);
        if (isSecureFolderId(userId)) {
            return 2;
        }
        if (userInfo == null || !userInfo.isManagedProfile() || isPremiumContainer(userId)) {
            return isPremiumContainer(userId) ? 4 : 0;
        }
        return 3;
    }

    public int getContainerTypeForUserId(int i) {
        UserInfo userInfo = getUserInfo(i);
        if (isSecureFolderId(i)) {
            return 2;
        }
        if (userInfo == null || !userInfo.isManagedProfile() || isPremiumContainer(i)) {
            return isPremiumContainer(i) ? 4 : 0;
        }
        return 3;
    }

    public static boolean isKnoxProfileActivePasswordSufficientForParent(int i) {
        if (getPersonaService() == null) {
            return true;
        }
        try {
            return getPersonaService().isKnoxProfileActivePasswordSufficientForParent(i);
        } catch (RemoteException e) {
            Log.e(TAG, "isKnoxProfileActivePasswordSufficientForParent failed", e);
            return true;
        }
    }

    public static boolean isPasswordSufficientAfterKnoxProfileUnification(int i) {
        if (getPersonaService() == null) {
            return true;
        }
        try {
            return getPersonaService().isPasswordSufficientAfterKnoxProfileUnification(i);
        } catch (RemoteException e) {
            Log.e(TAG, "isPasswordSufficientAfterKnoxProfileUnification failed", e);
            return true;
        }
    }

    public static void drawKnoxAppBadge(final Context context, final AppWidgetHostView appWidgetHostView, final UserHandle userHandle) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.samsung.android.knox.SemPersonaManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ImageView imageView = new ImageView(Context.this);
                    Drawable userBadgeForDensity = Context.this.getPackageManager().getUserBadgeForDensity(userHandle, 0);
                    if (userBadgeForDensity != null) {
                        imageView.setImageDrawable(userBadgeForDensity);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(userBadgeForDensity.getIntrinsicWidth(), userBadgeForDensity.getIntrinsicHeight());
                        layoutParams.gravity = 85;
                        appWidgetHostView.addView(imageView, layoutParams);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000L);
    }

    public static boolean appliedPasswordPolicy(int i) {
        if (isKnoxId(i) && getPersonaService() != null) {
            try {
                return getPersonaService().appliedPasswordPolicy(i);
            } catch (RemoteException e) {
                Log.e(TAG, "appliedPasswordPolicy failed", e);
            }
        }
        return false;
    }

    public boolean isShareClipboardDataToOwnerAllowed(int i) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.isShareClipboardDataToOwnerAllowed(i);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call Persona Policy service", e);
            return false;
        }
    }

    public boolean isShareClipboardDataToContainerAllowed(int i) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.isShareClipboardDataToContainerAllowed(i);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call Persona Policy service", e);
            return false;
        }
    }

    public static boolean isMoveFilesToContainerAllowed(int i) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().isMoveFilesToContainerAllowed(i);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call Persona Policy service", e);
            return false;
        }
    }

    public static boolean isMoveFilesToOwnerAllowed(int i) {
        if (getPersonaService() == null) {
            return false;
        }
        try {
            return getPersonaService().isMoveFilesToOwnerAllowed(i);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call Persona Policy service", e);
            return false;
        }
    }

    public static void logDpmsKA(Bundle bundle) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().logDpmsKA(bundle);
            } catch (RemoteException e) {
                Log.d(TAG, "logDpmsKA", e);
            }
        }
    }

    public List<String> getSecureFolderPolicy(String str, int i) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return null;
        }
        try {
            return iSemPersonaManager.getSecureFolderPolicy(str, i);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call Persona Policy service", e);
            return null;
        }
    }

    public ArrayList<ComponentName> getExcludeComponentList(boolean z, boolean z2) {
        ArrayList<ComponentName> arrayList = new ArrayList<>();
        if (z) {
            int secureFolderId = getSecureFolderId(this.mContext);
            List<String> secureFolderPolicy = getSecureFolderPolicy(BLOCKED_SHARING_COMP_COMMON, secureFolderId);
            if (secureFolderPolicy != null && secureFolderPolicy.size() > 0) {
                arrayList.addAll(getComponentsFromPolicy(secureFolderPolicy));
            }
            if (z2) {
                List<String> secureFolderPolicy2 = getSecureFolderPolicy(BLOCKED_SHARING_COMP_FOR_SECUREFOLDER, secureFolderId);
                if (secureFolderPolicy2 != null && secureFolderPolicy2.size() > 0) {
                    arrayList.addAll(getComponentsFromPolicy(secureFolderPolicy2));
                    return arrayList;
                }
            } else {
                List<String> secureFolderPolicy3 = getSecureFolderPolicy(BLOCKED_SHARING_COMP_FOR_OWNER, secureFolderId);
                if (secureFolderPolicy3 != null && secureFolderPolicy3.size() > 0) {
                    arrayList.addAll(getComponentsFromPolicy(secureFolderPolicy3));
                }
            }
        }
        return arrayList;
    }

    private ArrayList<ComponentName> getComponentsFromPolicy(List<String> list) {
        ArrayList<ComponentName> arrayList = new ArrayList<>();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String[] split = it.next().split("/");
            arrayList.add(new ComponentName(split[0], split[1]));
        }
        return arrayList;
    }

    public boolean setRCPDataPolicy(String str, String str2, String str3) {
        try {
            ISemPersonaManager iSemPersonaManager = this.mService;
            if (iSemPersonaManager != null) {
                return iSemPersonaManager.setRCPDataPolicy(str, str2, str3);
            }
            Log.d(TAG, "in PersonaPolicyManager, setRCPDataPolicy() is not called...");
            return false;
        } catch (RemoteException e) {
            Log.d(TAG, "Could not get setRCPDataPolicy , inside PersonaPolicyManager with exception:", e);
            return false;
        }
    }

    public boolean setSecureFolderPolicy(String str, List<String> list, int i) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.setSecureFolderPolicy(str, list, i);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call Persona Policy service", e);
            return false;
        }
    }

    public void CMFALock(int i) {
        Log.d(TAG, "CMFALock userId : " + i);
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.CMFALock(i);
            } catch (RemoteException e) {
                Log.d(TAG, "Failed to call CMFALock", e);
            }
        }
    }

    public void CMFAUnLock(int i) {
        Log.d(TAG, "CMFAUnLock userId : " + i);
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.CMFAUnLock(i);
            } catch (RemoteException e) {
                Log.d(TAG, "Failed to call CMFAUnLock", e);
            }
        }
    }

    public void updateProfileActivityTimeFromKnox(int i, long j) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.updateProfileActivityTimeFromKnox(i, j);
            } catch (RemoteException e) {
                Log.d(TAG, "Failed to call Persona Policy service", e);
            }
        }
    }

    public void startCountrySelectionActivity(boolean z) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.startCountrySelectionActivity(z);
            } catch (RemoteException e) {
                Log.d(TAG, "Failed to call startCountrySelectionActivity", e);
            }
        }
    }

    public void startTermsActivity() {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.startTermsActivity();
            } catch (RemoteException e) {
                Log.d(TAG, "Failed to call startTermsActivity", e);
            }
        }
    }

    public void postPwdChangeNotificationForDeviceOwner(int i) {
        if (getPersonaService() != null) {
            try {
                getPersonaService().postPwdChangeNotificationForDeviceOwner(i);
            } catch (RemoteException e) {
                Log.d(TAG, "Failed to call Persona Policy service", e);
            }
        }
    }

    public static boolean getRestriction(String str, int i) {
        Log.d(TAG, "getRestriction " + str + " " + i);
        return true;
    }

    public static boolean isDualAppId(int i) {
        return SemDualAppManager.isDualAppId(i);
    }

    public boolean sendKnoxForesightBroadcast(Intent intent) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.sendKnoxForesightBroadcast(intent);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call sendKnoxForesightBroadcast", e);
            return false;
        }
    }

    public boolean hasLicensePermission(int i, String str) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.hasLicensePermission(i, str);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call hasLicensePermission", e);
            return false;
        }
    }

    public static IBasicCommand getKnoxForesightService() {
        if (getPersonaService() == null) {
            return null;
        }
        try {
            return getPersonaService().getKnoxForesightService();
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call Persona Policy service", e);
            return null;
        }
    }

    public boolean setDdmPolicy(String str, int i) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.setDdmPolicy(str, i);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call setDdmPolicy", e);
            return false;
        }
    }

    public void registerDdmBroadcastReceiver(IntentFilter intentFilter, Uri uri, int i) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager != null) {
            try {
                iSemPersonaManager.registerDdmBroadcastReceiver(intentFilter, uri, i);
            } catch (RemoteException e) {
                Log.d(TAG, "Failed to call setDdmPolicy", e);
            }
        }
    }

    public boolean shouldBlockCommand(String str, String str2, int i) {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.shouldBlockCommand(str, str2, i);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call shouldBlockCommand", e);
            return false;
        }
    }

    public boolean isUsbDebuggingAllowed() {
        ISemPersonaManager iSemPersonaManager = this.mService;
        if (iSemPersonaManager == null) {
            return false;
        }
        try {
            return iSemPersonaManager.isUsbDebuggingAllowed();
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call isUsbDebuggingAllowed", e);
            return false;
        }
    }
}
