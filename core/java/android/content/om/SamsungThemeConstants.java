package android.content.om;

import android.security.KeyChain;
import com.samsung.android.aod.AODManager;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.emergencymode.SemEmergencyConstants;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class SamsungThemeConstants {
    public static final String ACTION_THEME_APPLY = "com.samsung.android.theme.themecenter.THEME_APPLY";
    public static final String CATEGORY_ZIPPED_LOCALE_OVERLAY = "zipped-overlay";
    public static final String CURRENT_SEC_ACTIVE_THEME_PACKAGE = "current_sec_active_themepackage";
    public static final String CURRENT_SEC_APPICON_THEME_PACKAGE = "current_sec_appicon_themepackage";
    public static final String DATA_APP_DIR = "/data/app";
    public static final String DATA_OVERLAY_DIR = "/data/overlays";
    public static final boolean DEBUG_THEMES = true;
    public static final String FILIPINO_LOCALE_CODE = "fil";
    public static final boolean IS_SAMSUNG_APK_OPTIMIZATION_ENABLED = true;
    public static final boolean IS_SAMSUNG_THEMES_ENABLED = true;
    public static final String LEGACY_CALENDAR_PACKAGE_NAME = "com.android.calendar";
    public static final String LEGACY_CONTACT_PACKAGE_NAME = "com.android.contacts";
    public static final String LEGACY_MESSAGE_PACKAGE_NAME = "com.android.mms";
    public static final String METADATA_ZIPPED_LOCALE_OVERLAY = "com.samsung.android.zippedOverlay";
    public static final String PACKAGE_NAME_FOR_SKIP_THEME_APPICON = "android.content.cts";
    public static final String PATH_LOCAL_TEMP = "/data/local/tmp";
    public static final String PATH_OVERLAY = "/data/overlays";
    public static final String PATH_OVERLAY_CURRENT_LOCALE_APKS = "/data/overlays/current_locale_apks/files";
    public static final String PATH_OVERLAY_CURRENT_STYLE = "/data/overlays/currentstyle";
    public static final String PATH_OVERLAY_REAPPLY = "/data/overlays/currentstyle/reapply";
    public static final String PATH_THEMEPARK_ICON = "/data/overlays/themepark/icons/";
    public static final String PATH_THEMEPARK_OVERLAY = "/data/overlays/themepark/";
    public static final String PATH_THEMEPARK_STATE_CHECK = "/data/overlays/themepark/state_applied.txt";
    public static final String PATH_THEME_PREFERENCES = "/data/overlays/preferences/samsung.andorid.themes.component_preference.xml";
    public static final String PENDING_MIGRATED_PACKAGES_UNINSTALL = "pending_migrated_packages_uninstall.txt";
    public static final String PERMISSION_OVERLAY_COMPONENT = "com.samsung.android.permission.SAMSUNG_OVERLAY_COMPONENT";
    public static final String PERMISSION_OVERLAY_LANGUAGE = "com.samsung.android.permission.SAMSUNG_OVERLAY_LANGUAGE";
    public static final String PERMISSION_OVERLAY_THEME = "com.samsung.android.permission.SAMSUNG_OVERLAY_THEME";
    public static final String PERMISSION_SAMSUNG_OVERLAY = "com.samsung.android.permission.SAMSUNG_OVERLAY_";
    public static final String PREFIX_THEMEPARK = "com.samsung.themedesigner";
    public static final String PREFIX_THEMEPARK_CATEGORY = "com.samsung.android.themedesigner";
    public static final String PREFIX_THEMEPARK_OVERLAY = "com.samsung.themedesigner.OV";
    public static final String TAGALOG_LOCALE_CODE = "tl";
    public static final String THEMESTORE_PACKAGE_NAME = "com.samsung.android.themestore";
    public static final String THEME_OVERLAY_DIR = "/data/overlays/style";
    public static final HashSet<String> changeableApps = new HashSet<>(Arrays.asList(SemDesktopModeManager.LAUNCHER_PACKAGE, "com.sec.android.app.eventnotification", "com.samsung.tmowfc.wfcpref", CoreSaConstant.PACKAGE_NAME_RECENTS, "com.sec.android.app.FlashBarService", "com.android.nfc", "com.samsung.felicalock", "com.android.apps.tag", "com.samsung.app.newtrim", "com.adnroid.dreams.phototable", "com.policydm", "com.samsung.android.securitylogagent", "com.sec.android.app.SecSetupWizard", "com.samsung.safetyinformation", "com.sec.app.samsungprinterservice", "com.samsung.spg", "com.sec.android.app.capabilitymanager", "com.sec.android.app.wallpaperchooser", "com.bst.airmessage", "com.sec.android.app.simsettingmgr", "com.sec.android.app.simcardmanagement", "com.sec.android.widgetapp.dualsimwidget", "com.sec.android.app.irsettings", "com.samsung.android.app.shareaccessibilitysettings", "com.google.android.marvin.talkback", "com.samsung.android.SettingsReceiver", "com.sec.android.app.popupuireceiver", "com.sec.android.wallpapercropper2", "com.samsung.android.MtpApplication", AsPackageName.SOUNDALIVE_FOR_KARAOKE, "com.samsung.android.app.galaxylabs", "com.sec.android.mimage.photoretouching", "com.sec.android.mimage.gear360editor", AsPackageName.RINGTONE_PICKER, "com.samsung.android.slinkcloud", SemEmergencyConstants.EMERGENCY_LAUNCHER, "com.samsung.hongbaoassistant", "com.sec.android.app.firewall", "com.bst.spamcall", "com.sec.app.screenrecorder", "com.samsung.android.bixbytouch", "com.samsung.android.mateagent", "com.samsung.android.dynamiclock", AsPackageName.BLUETOOTH, "com.samsung.android.app.telephonyui.netsettings", "com.samsung.theme", "com.sec.winset", "com.sec.sesl.tester", "com.samsung.advancedcalling", "com.android.certinstaller", KeyChain.ACCOUNT_TYPE, "com.sec.android.app.quicktool", "com.sec.unifiedwfc", "com.samsung.advancedcalling", SemPersonaManager.SECUREFOLDER_PACKAGE, "com.samsung.android.tencentwifisecurity", "com.samsung.android.app.smartcapture", "com.samsung.android.app.clipboardedge", "com.android.certinstaller", KeyChain.ACCOUNT_TYPE, "com.samsung.android.fmm", "com.sec.android.widgetapp.easymodecontactswidget", "com.samsung.android.vdc", "com.samsung.android.app.dofviewer"));
    public static HashMap<String, String> overlayTargetMap = new HashMap<String, String>() { // from class: android.content.om.SamsungThemeConstants.1
        {
            put("fwk", "android");
            put(AsPackageName.VOICENOTE, null);
            put("com.sec.android.app.music", null);
            put("com.samsung.android.video", null);
            put("com.sec.android.app.vepreload", null);
            put("com.samsung.app.highlightplayer", null);
            put("com.sec.android.app.clipvideo", null);
            put("com.samsung.android.scloud.backup", "com.samsung.android.scloud");
            put("com.sec.android.widgetapp.ap.hero.accuweather", "com.sec.android.daemonapp");
            put("com.samsung.android.qconnect", null);
            put("com.samsung.android.app.omcagent", null);
            put("com.samsung.android.app.dtv.dmb", null);
            put(AsPackageName.DMB, null);
            put("com.samsung.android.smartmirroring", null);
            put("com.samsung.android.samsungpass", null);
            put("com.samsung.android.app.dofviewer", null);
            put("com.samsung.android.app.appsedge", CoreSaConstant.PACKAGE_NAME_RECENTS);
            put(CoreSaConstant.PACKAGE_NAME_EDGE_SERVICE, CoreSaConstant.PACKAGE_NAME_RECENTS);
        }
    };
    public static final String LEGACY_INCALLUI_PACKAGE_NAME = "com.android.incallui";
    public static ArrayList<String> immortalApps = new ArrayList<>(Arrays.asList("android", AsPackageName.SYSTEMUI, "com.android.nfc", "com.samsung.android.universalswitch", AsPackageName.RINGTONE_PICKER, LEGACY_INCALLUI_PACKAGE_NAME, "com.android.phone", AODManager.AOD_PACKAGE_NAME, CoreSaConstant.PACKAGE_NAME_RECENTS, "com.sec.android.app.safetyassurance", "com.samsung.android.incallui", "com.samsung.android.scloud", AsPackageName.BLUETOOTH, "com.samsung.android.mateagent", "com.samsung.android.dynamiclock", "com.samsung.android.messaging", "com.android.frameworks.gofservicetests", "com.samsung.android.localeoverlaymanager", "com.samsung.android.app.dressroom", "com.samsung.android.fast"));
    public static final String THEMECENTER_PACKAGE_NAME = "com.samsung.android.themecenter";
    public static ArrayList<String> protectedApps = new ArrayList<>(Arrays.asList(THEMECENTER_PACKAGE_NAME, "com.samsung.android.localeoverlaymanager"));
    public static ArrayList<String> ignoreAppIconThemeHosts = new ArrayList<>(Arrays.asList("com.nttdocomo.android.dhome", "com.nttdocomo.android.homezozo"));
    public static ArrayList<String> ignoreAppIconThemeList = new ArrayList<>(Arrays.asList("SamsungElectronics.Tokyo2020.appicon", "com.liquidanimation.DarkSideRC.appicon", "D.OlympicGamesTokyo2020Theme.appicon"));
    public static ArrayList<String> nonAdaptiveIconPkgList = new ArrayList<>(Arrays.asList("com.sec.android.app.camera", SemPersonaManager.SECUREFOLDER_PACKAGE, SemPersonaManager.APPSEPARATION_PACKAGE));
    public static final String THEME_OVERLAY_SAMSUNG_KEYBOARD_POSTFIX = ".honeyboard.apk";
    public static ArrayList<String> allowPostfixForCover = new ArrayList<>(Arrays.asList(THEME_OVERLAY_SAMSUNG_KEYBOARD_POSTFIX));
    public static final String THEME_OVERLAY_SYSTEMUI_POSTFIX = ".systemui.apk";
    public static final String THEME_OVERLAY_LOCK_POSTFIX = ".lock.apk";
    public static ArrayList<String> allowSystemUIForCover = new ArrayList<>(Arrays.asList(THEME_OVERLAY_SYSTEMUI_POSTFIX, THEME_OVERLAY_LOCK_POSTFIX));

    public static boolean localeDirsChanged(String[] overlayDir, String[] resDir) {
        Collection<? extends String> set1 = Arrays.asList(overlayDir == null ? new String[0] : overlayDir);
        Collection<? extends String> set2 = Arrays.asList(resDir == null ? new String[0] : resDir);
        HashSet<String> union = new HashSet<>();
        union.addAll(set1);
        union.addAll(set2);
        HashSet<String> intersection = new HashSet<>();
        intersection.addAll(set1);
        intersection.retainAll(set2);
        union.removeAll(intersection);
        Iterator<String> it = union.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (s.startsWith(PATH_OVERLAY_CURRENT_LOCALE_APKS)) {
                return true;
            }
        }
        return false;
    }
}
