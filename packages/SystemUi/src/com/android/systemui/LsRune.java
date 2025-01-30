package com.android.systemui;

import android.R;
import android.app.ActivityThread;
import android.app.Application;
import android.os.Build;
import android.os.FactoryTest;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.TelephonyFeatures;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SafeUIState;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LsRune extends Rune {
    public static final boolean AOD_BRIGHTNESS_CONTROL;
    public static final boolean AOD_DISABLE_CLOCK_TRANSITION;
    public static final boolean AOD_FULLSCREEN;
    public static final boolean AOD_HYSTERESIS_BRIGHTNESS;
    public static final boolean AOD_LIGHT_REVEAL;
    public static final boolean AOD_SAFEMODE;
    public static final boolean AOD_SELF_POKE_DRAW_LOCK;
    public static final boolean AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT;
    public static final boolean AOD_SUB_DISPLAY_COVER;
    public static final boolean AOD_SUB_DISPLAY_LOCK;
    public static final boolean AOD_SUB_FULLSCREEN;
    public static final boolean AOD_TSP_CONTROL;
    public static final boolean COVER_ADJUST_REFRESH_RATE;
    public static final boolean COVER_DEBUG_CAMERA_COVER_ON_SIDE_COVER;
    public static final boolean COVER_SAFEMODE;
    public static final boolean COVER_SUPPORTED;
    public static final boolean COVER_VIRTUAL_DISPLAY;
    public static final boolean KEYGUARD_ALLOW_ROTATION;
    public static final boolean KEYGUARD_DCM_LIVE_UX;
    public static final boolean KEYGUARD_DELAY_NOTIFY_DRAWN_PREMIUM_WATCH;
    public static final boolean KEYGUARD_EM_TOKEN_CAPTURE_WINDOW;
    public static final boolean KEYGUARD_ENABLE_DEFAULT_ROTATION;
    public static final boolean KEYGUARD_EXTRA_USER_PRESENT;
    public static final boolean KEYGUARD_FBE;
    public static final boolean KEYGUARD_HOMEHUB;
    public static final boolean KEYGUARD_LOCK_SITUATION_VOLUME;
    public static final boolean KEYGUARD_PERFORMANCE_BIO_UNLOCK_BOOSTER;
    public static final boolean KEYGUARD_PERFORMANCE_SCREEN_ON;
    public static final boolean KEYGUARD_SUB_DISPLAY_COVER;
    public static final boolean KEYGUARD_SUB_DISPLAY_LARGE_FRONT;
    public static final boolean KEYGUARD_SUB_DISPLAY_LOCK;
    public static final boolean KEYGUARD_SUB_DISPLAY_ROTATIONAL;
    public static final boolean LOCKUI_AOD_PACKAGE_AVAILABLE;
    public static final boolean LOCKUI_BLUR;
    public static final boolean LOCKUI_BOTTOM_USIM_TEXT;
    public static final boolean LOCKUI_CAPTURED_BLUR;
    public static final boolean LOCKUI_ECO_BATTERY;
    public static final boolean LOCKUI_HELP_TEXT_FOR_CHN;
    public static final boolean LOCKUI_MULTI_USER;
    public static final boolean LOCKUI_SUB_DISPLAY_COVER;
    public static final boolean LOCKUI_SUB_DISPLAY_LOCK;
    public static final boolean PLUGIN_LOCK_LSM;
    public static final boolean PLUGIN_LOCK_MULTIPLE_ACTIVATION;
    public static final boolean SECURITY_ARROW_VIEW;
    public static final boolean SECURITY_BACKGROUND_AUTHENTICATION;
    public static final boolean SECURITY_BIOMETRICS_TABLET;
    public static final boolean SECURITY_BLOCK_CARRIER_TEXT_WHEN_SIM_NOT_READY;
    public static final boolean SECURITY_BLUR;
    public static final boolean SECURITY_BOUNCER_WINDOW;
    public static final boolean SECURITY_CAPTURED_BLUR;
    public static final boolean SECURITY_CLEAR_NO_SIM_DEFAULT_TEXT;
    public static final boolean SECURITY_COLOR_CURVE_BLUR;
    public static final boolean SECURITY_DCM_USIM_TEXT;
    public static final boolean SECURITY_DEAD_ZONE;
    public static final boolean SECURITY_DEFAULT_LANDSCAPE;
    public static final boolean SECURITY_DIRECT_CALL_TO_ECC;
    public static final boolean SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE;
    public static final boolean SECURITY_DISAPPEAR_DEFAULT_CARRIER_TEXT;
    public static final boolean SECURITY_EMERGENCY_BUTTON_KOR;
    public static final boolean SECURITY_ESIM;
    public static final boolean SECURITY_FACTORY_RESET_WITHOUT_UI;
    public static final boolean SECURITY_FINGERPRINT_GUIDE_POPUP;
    public static final boolean SECURITY_FINGERPRINT_IN_DISPLAY;
    public static final boolean SECURITY_FINGERPRINT_IN_DISPLAY_OPTICAL;
    public static final boolean SECURITY_HAPTIC_FEEDBACK_ON_DC_MOTOR;
    public static final boolean SECURITY_HIDE_EMERGENCY_BUTTON_BY_SIMSTATE;
    public static final boolean SECURITY_KOR_USIM_TEXT;
    public static final boolean SECURITY_KTT_USIM_TEXT;
    public static final boolean SECURITY_LGU_USIM_TEXT;
    public static final boolean SECURITY_NAVBAR_ENABLED;
    public static final boolean SECURITY_NOT_REQUIRE_SIMPUK_CODE;
    public static final boolean SECURITY_OPEN_THEME;
    public static final boolean SECURITY_PUNCH_HOLE_FACE_VI;
    public static final boolean SECURITY_SIM_PERM_DISABLED;
    public static final boolean SECURITY_SIM_PERSO_LOCK;
    public static final boolean SECURITY_SIM_UNLOCK_TOAST;
    public static final boolean SECURITY_SKT_USIM_TEXT;
    public static final boolean SECURITY_SPR_USIM_TEXT;
    public static final boolean SECURITY_SUB_DISPLAY_COVER;
    public static final boolean SECURITY_SUB_DISPLAY_LOCK;
    public static final boolean SECURITY_SWIPE_BOUNCER;
    public static final boolean SECURITY_UNPACK;
    public static final boolean SECURITY_USE_CDMA_CARD_TEXT;
    public static final boolean SECURITY_VZW_INSTRUCTION;
    public static final boolean SECURITY_WARNING_WIPE_OUT_MESSAGE;
    public static final boolean SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN;
    public static final boolean SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING;
    public static final boolean SUBSCREEN_UI;
    public static final boolean SUBSCREEN_WATCHFACE;
    public static final boolean SUPPORT_LARGE_FRONT_SUB_DISPLAY;
    public static final String VALUE_CONFIG_CARRIER_SECURITY_POLICY;
    public static final String VALUE_CONFIG_CARRIER_TEXT_POLICY;
    public static final String VALUE_LOCK_POLICY;
    public static final String VALUE_SUB_DISPLAY_POLICY;
    public static final boolean WALLPAPER_BLUR;
    public static final boolean WALLPAPER_CACHED_WALLPAPER;
    public static final boolean WALLPAPER_CAPTURED_BLUR;
    public static final boolean WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER;
    public static final boolean WALLPAPER_FESTIVAL_WALLPAPER;
    public static final boolean WALLPAPER_MAISON_MARGIELA_EDITION;
    public static final boolean WALLPAPER_PLAY_GIF;
    public static final boolean WALLPAPER_ROTATABLE_WALLPAPER;
    public static final boolean WALLPAPER_SUB_DISPLAY_MODE;
    public static final boolean WALLPAPER_SUB_WATCHFACE;
    public static final boolean WALLPAPER_SUPPORT_DLS_SNAPSHOT;
    public static final boolean WALLPAPER_SUPPORT_SUIT_CASE;
    public static final boolean WALLPAPER_VIDEO_PLAY_RANDOM_POSITION;
    public static final boolean WALLPAPER_VIDEO_WALLPAPER;
    public static final boolean WALLPAPER_VIRTUAL_DISPLAY;

    /* JADX WARN: Can't wrap try/catch for region: R(106:0|1|(1:255)(1:5)|6|(1:254)(1:10)|11|(1:253)(1:15)|16|(1:252)(1:22)|23|(1:251)(1:27)|28|(1:250)(1:34)|35|(1:249)(1:39)|40|(1:248)(1:44)|45|(1:47)|48|(1:50)(1:247)|(1:246)(1:55)|56|(1:58)(1:245)|59|(1:244)(1:63)|64|(2:66|(76:71|72|(1:241)(1:76)|77|(1:79)|80|(2:82|(68:84|85|(1:238)(1:91)|92|93|94|95|96|97|(2:99|(56:101|102|(1:230)(1:108)|109|(1:229)(1:114)|115|(5:117|(3:121|(1:123)(1:125)|124)|126|(1:128)(1:227)|(46:130|131|(1:133)(1:226)|134|(1:136)(1:225)|137|(1:139)(1:224)|140|(1:223)(1:144)|145|(1:222)(1:149)|150|(1:221)(1:154)|155|(2:157|(23:159|160|(1:218)(1:166)|167|(1:217)(1:172)|173|(1:216)(1:177)|178|(1:215)(1:182)|183|(1:214)(1:187)|188|(1:213)(1:191)|192|(1:194)(1:212)|195|(1:211)(1:198)|199|(1:201)(1:210)|202|(1:209)(1:205)|206|207))(1:220)|219|160|(1:162)|218|167|(0)|217|173|(1:175)|216|178|(1:180)|215|183|(0)|214|188|(0)|213|192|(0)(0)|195|(0)|211|199|(0)(0)|202|(0)|209|206|207))|228|131|(0)(0)|134|(0)(0)|137|(0)(0)|140|(1:142)|223|145|(1:147)|222|150|(1:152)|221|155|(0)(0)|219|160|(0)|218|167|(0)|217|173|(0)|216|178|(0)|215|183|(0)|214|188|(0)|213|192|(0)(0)|195|(0)|211|199|(0)(0)|202|(0)|209|206|207)(1:231))|233|102|(2:104|106)|230|109|(1:111)|229|115|(0)|228|131|(0)(0)|134|(0)(0)|137|(0)(0)|140|(0)|223|145|(0)|222|150|(0)|221|155|(0)(0)|219|160|(0)|218|167|(0)|217|173|(0)|216|178|(0)|215|183|(0)|214|188|(0)|213|192|(0)(0)|195|(0)|211|199|(0)(0)|202|(0)|209|206|207))(1:240)|239|85|(1:87)|238|92|93|94|95|96|97|(0)|233|102|(0)|230|109|(0)|229|115|(0)|228|131|(0)(0)|134|(0)(0)|137|(0)(0)|140|(0)|223|145|(0)|222|150|(0)|221|155|(0)(0)|219|160|(0)|218|167|(0)|217|173|(0)|216|178|(0)|215|183|(0)|214|188|(0)|213|192|(0)(0)|195|(0)|211|199|(0)(0)|202|(0)|209|206|207))(1:243)|242|72|(0)|241|77|(0)|80|(0)(0)|239|85|(0)|238|92|93|94|95|96|97|(0)|233|102|(0)|230|109|(0)|229|115|(0)|228|131|(0)(0)|134|(0)(0)|137|(0)(0)|140|(0)|223|145|(0)|222|150|(0)|221|155|(0)(0)|219|160|(0)|218|167|(0)|217|173|(0)|216|178|(0)|215|183|(0)|214|188|(0)|213|192|(0)(0)|195|(0)|211|199|(0)(0)|202|(0)|209|206|207|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x03ca, code lost:
    
        android.util.Log.e(r4, "Fail to get service array");
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x03c8, code lost:
    
        r4 = "DeviceType";
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x03de  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x040b  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x042c  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0481  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0491  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x049e  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x04c3  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x04d2  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x04e8  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x04f5  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0506  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x051a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0554  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0573  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x05b1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x05bc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:194:0x05d1  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x05d8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:201:0x05fc  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0603 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x05fe  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x05d3  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x04ff  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x04a0  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0493  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0483  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0370  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0362  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x03b1 A[Catch: Exception -> 0x03ca, TryCatch #0 {Exception -> 0x03ca, blocks: (B:97:0x03ac, B:99:0x03b1, B:231:0x03c2), top: B:96:0x03ac }] */
    static {
        CharSequence charSequence;
        boolean z;
        String str;
        boolean z2;
        boolean z3;
        String str2;
        boolean z4;
        boolean z5;
        boolean contains;
        CharSequence charSequence2;
        boolean z6;
        Application currentApplication;
        Application currentApplication2;
        String string = SemCscFeature.getInstance().getString("CscFeature_LockScreen_ConfigCarrierTextPolicy");
        VALUE_CONFIG_CARRIER_TEXT_POLICY = string;
        String string2 = SemCscFeature.getInstance().getString("CscFeature_LockScreen_ConfigCarrierSecurityPolicy");
        VALUE_CONFIG_CARRIER_SECURITY_POLICY = string2;
        String string3 = SemCscFeature.getInstance().getString("CscFeature_LockScreen_ConfigEmergencyCallPolicy");
        String string4 = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
        VALUE_SUB_DISPLAY_POLICY = string4;
        String string5 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_POLICY_LIST");
        VALUE_LOCK_POLICY = string5;
        boolean contains2 = string4.contains("LOCKSCREEN");
        KEYGUARD_SUB_DISPLAY_LOCK = contains2;
        KEYGUARD_SUB_DISPLAY_COVER = string4.contains("COVER");
        KEYGUARD_SUB_DISPLAY_ROTATIONAL = string4.contains("ROTATIONAL");
        KEYGUARD_DCM_LIVE_UX = string.contains("UseDCMSimLockText");
        KEYGUARD_FBE = DeviceType.isFbeSupported();
        KEYGUARD_LOCK_SITUATION_VOLUME = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_SITUATION_EXTENSION").contains("TRUE");
        KEYGUARD_EXTRA_USER_PRESENT = SemCscFeature.getInstance().getString("CscFeature_COMMON_ConfigImplicitBroadcasts").equals("VZW");
        KEYGUARD_DELAY_NOTIFY_DRAWN_PREMIUM_WATCH = contains2 && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CLOCK_SUPPORT_PREMIUM_WATCH");
        Integer.parseInt("2");
        KEYGUARD_HOMEHUB = !TextUtils.isEmpty(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_HOMEHUB"));
        KEYGUARD_ALLOW_ROTATION = !string5.contains("DISALLOW_DEFAULT_ROTATION") && DeviceType.isTablet();
        KEYGUARD_ENABLE_DEFAULT_ROTATION = !string5.contains("DISALLOW_DEFAULT_ROTATION") && (DeviceType.isTablet() || string5.contains("ENABLE_DEFAULT_ROTATION"));
        SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_WEARABLE_AR");
        KEYGUARD_EM_TOKEN_CAPTURE_WINDOW = !Build.IS_DEBUGGABLE;
        KEYGUARD_PERFORMANCE_BIO_UNLOCK_BOOSTER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_POLICY_LIST").contains("bio_unlock_booster") || SystemProperties.getBoolean("persist.keyguard.enable_bio_unlock_booster", false);
        boolean contains3 = string4.contains("LARGESCREEN");
        KEYGUARD_SUB_DISPLAY_LARGE_FRONT = contains3;
        KEYGUARD_PERFORMANCE_SCREEN_ON = (DeviceType.isTablet() || contains2 || contains3 || SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM").contains("aodversion")) ? false : true;
        SECURITY_OPEN_THEME = true;
        boolean z7 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        SECURITY_BLUR = z7;
        boolean z8 = !z7 && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR");
        SECURITY_CAPTURED_BLUR = z8;
        boolean z9 = !z8;
        SECURITY_BOUNCER_WINDOW = z9;
        SECURITY_COLOR_CURVE_BLUR = z9;
        SECURITY_SWIPE_BOUNCER = "US".equals(SystemProperties.get("ro.csc.countryiso_code", ""));
        boolean contains4 = string4.contains("LOCKSCREEN");
        SECURITY_SUB_DISPLAY_LOCK = contains4;
        SECURITY_SUB_DISPLAY_COVER = string4.contains("COVER");
        SECURITY_ARROW_VIEW = contains4 || DeviceType.isTablet();
        if (DeviceType.supportDeadzone == -1) {
            DeviceType.supportDeadzone = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_TSP_STATE_MANAGER").contains("deadzone_v2") ? 1 : 0;
        }
        SECURITY_DEAD_ZONE = (!(DeviceType.supportDeadzone == 1) || DeviceType.isTablet() || contains4) ? false : true;
        SECURITY_DEFAULT_LANDSCAPE = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_COMMON_CONFIG_PREFERRED_USER_ROTATION", 0) == 1;
        SECURITY_NAVBAR_ENABLED = BasicRune.NAVBAR_ENABLED;
        SECURITY_HAPTIC_FEEDBACK_ON_DC_MOTOR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_DC_MOTOR_HAPTIC_FEEDBACK");
        SECURITY_UNPACK = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK");
        SECURITY_EMERGENCY_BUTTON_KOR = string.contains("DisplayUsimText");
        SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE = string3.contains("DisableEmergencyCallWhenOffline");
        SECURITY_HIDE_EMERGENCY_BUTTON_BY_SIMSTATE = string2.contains("DisableEMCallButtonBySimState") || string5.contains("DisableEMCallButtonBySimState");
        SECURITY_DIRECT_CALL_TO_ECC = string3.contains("DirectCall");
        SECURITY_FINGERPRINT_IN_DISPLAY = true;
        SECURITY_FINGERPRINT_IN_DISPLAY_OPTICAL = true;
        SECURITY_BACKGROUND_AUTHENTICATION = false;
        SECURITY_PUNCH_HOLE_FACE_VI = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_PUNCHHOLE_VI").contains("face");
        boolean isTablet = DeviceType.isTablet();
        SECURITY_BIOMETRICS_TABLET = isTablet;
        SECURITY_SIM_PERSO_LOCK = TelephonyFeatures.isCountrySpecific(0, new String[]{"KOR"});
        SECURITY_SIM_UNLOCK_TOAST = string2.contains("DisplayUnlockToast");
        SECURITY_ESIM = DeviceType.isSupportESim();
        SECURITY_DISAPPEAR_DEFAULT_CARRIER_TEXT = string2.contains("UseTMOSIMPINLock");
        boolean contains5 = string.contains("DisplayUsimText");
        SECURITY_KOR_USIM_TEXT = contains5;
        SECURITY_SKT_USIM_TEXT = string.contains("UseSKTSimText");
        SECURITY_KTT_USIM_TEXT = string.contains("UseKTTSimText");
        SECURITY_LGU_USIM_TEXT = string.contains("UseLGTSimText");
        boolean contains6 = string.contains("UseDCMSimLockText");
        SECURITY_DCM_USIM_TEXT = contains6;
        boolean contains7 = string.contains("UseKDDISimText");
        SECURITY_SPR_USIM_TEXT = string.contains("UseDefaultPlmnValueFromNetwork");
        string.contains("UseFixedPlmnValueForUSC");
        SECURITY_USE_CDMA_CARD_TEXT = string.contains("UseCdmaCardText");
        if (string.contains("BlockCarrierTextWhenSimNotReady")) {
            charSequence = "aodversion";
        } else {
            charSequence = "aodversion";
            if (!VALUE_LOCK_POLICY.contains("BlockCarrierTextWhenSimNotReady") && !contains5 && !DeviceType.isWiFiOnlyDevice()) {
                z = false;
                SECURITY_BLOCK_CARRIER_TEXT_WHEN_SIM_NOT_READY = z;
                SECURITY_CLEAR_NO_SIM_DEFAULT_TEXT = !string.contains("ClearEmergencyCallWhenNoSim") || contains7 || contains6;
                SECURITY_SIM_PERM_DISABLED = string2.contains("SupportSimPermanentDisable");
                SECURITY_NOT_REQUIRE_SIMPUK_CODE = string2.contains("UseTMOSIMPINLock");
                SECURITY_WARNING_WIPE_OUT_MESSAGE = string2.contains("FactoryResetProtectionWarning");
                SECURITY_FACTORY_RESET_WITHOUT_UI = string2.contains("FactoryResetWithoutUI");
                SECURITY_VZW_INSTRUCTION = string2.contains("FactoryResetProtectionWarning");
                int i = DeviceType.supportTablet;
                SECURITY_FINGERPRINT_GUIDE_POPUP = isTablet;
                if (contains4) {
                    SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CLOCK_SUPPORT_PREMIUM_WATCH");
                }
                string.contains("UseLGTSimText");
                LOCKUI_BOTTOM_USIM_TEXT = string.contains("DisplayUsimText");
                LOCKUI_HELP_TEXT_FOR_CHN = string2.contains("UseSamsungAccountAuth");
                String str3 = VALUE_SUB_DISPLAY_POLICY;
                LOCKUI_SUB_DISPLAY_LOCK = str3.contains("LOCKSCREEN");
                LOCKUI_SUB_DISPLAY_COVER = str3.contains("COVER");
                LOCKUI_AOD_PACKAGE_AVAILABLE = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM").contains(charSequence);
                LOCKUI_BLUR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG")) {
                    str = "SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR";
                    if (SemFloatingFeature.getInstance().getBoolean(str)) {
                        z2 = true;
                        LOCKUI_CAPTURED_BLUR = z2;
                        LOCKUI_MULTI_USER = (DeviceType.isTablet() || !Rune.SYSUI_MULTI_USER || FactoryTest.isFactoryBinary()) ? false : true;
                        LOCKUI_ECO_BATTERY = !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_DISABLE_ECO_BATTERY");
                        currentApplication2 = ActivityThread.currentApplication();
                        String str4 = "DeviceType";
                        Log.i(str4, "isSupportBrightnessControl: context : " + currentApplication2);
                        if (currentApplication2 != null) {
                            if (currentApplication2.getResources().getIntArray(R.array.config_minimumBrightnessCurveNits).length > 2) {
                                z3 = true;
                                AOD_BRIGHTNESS_CONTROL = z3;
                                str2 = VALUE_SUB_DISPLAY_POLICY;
                                AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT = !str2.contains("AOD") && (str2.contains("LOCKSCREEN") || str2.contains("WATCHFACE"));
                                boolean contains8 = str2.contains("COVER");
                                AOD_SUB_DISPLAY_COVER = contains8;
                                AOD_SUB_DISPLAY_LOCK = str2.contains("LOCKSCREEN");
                                z4 = (!SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM").contains("activeclock") || SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "").contains("clocktransition") || contains8) ? false : true;
                                AOD_DISABLE_CLOCK_TRANSITION = z4;
                                AOD_SAFEMODE = SafeUIState.isSysUiSafeModeEnabled();
                                if (!z4) {
                                    if (DeviceType.supportLightReveal == -1 && (currentApplication = ActivityThread.currentApplication()) != null) {
                                        DeviceType.supportLightReveal = currentApplication.getResources().getDimensionPixelSize(R.dimen.resolver_profile_tab_margin) != 0 ? 1 : 0;
                                        Log.i(str4, "isSupportLightReveal: supportLightReveal=" + DeviceType.supportLightReveal + " context=" + currentApplication);
                                    }
                                    if (DeviceType.supportLightReveal == 1) {
                                        z5 = true;
                                        AOD_LIGHT_REVEAL = z5;
                                        boolean z10 = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1) < 1;
                                        AOD_FULLSCREEN = z10;
                                        AOD_SUB_FULLSCREEN = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1) != 3;
                                        AOD_HYSTERESIS_BRIGHTNESS = z10;
                                        AOD_TSP_CONTROL = Build.VERSION.SEM_FIRST_SDK_INT < 33;
                                        AOD_SELF_POKE_DRAW_LOCK = SystemProperties.getBoolean("persist.enable.self.poke.draw_lock", true);
                                        COVER_SUPPORTED = DeviceType.isCoverSupported();
                                        COVER_SAFEMODE = SafeUIState.isSysUiSafeModeEnabled();
                                        boolean contains9 = str2.contains("VIRTUAL_DISPLAY");
                                        COVER_VIRTUAL_DISPLAY = contains9;
                                        COVER_ADJUST_REFRESH_RATE = !contains9 && Integer.parseInt("2") > 0;
                                        COVER_DEBUG_CAMERA_COVER_ON_SIDE_COVER = !Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.camera_cover_on_side_cover", false);
                                        contains = str2.contains("COVER");
                                        SUBSCREEN_UI = contains;
                                        boolean z11 = !contains && str2.contains("WATCHFACE");
                                        SUBSCREEN_WATCHFACE = z11;
                                        if (contains) {
                                            charSequence2 = "LARGESCREEN";
                                        } else {
                                            charSequence2 = "LARGESCREEN";
                                            if (str2.contains(charSequence2)) {
                                                z6 = true;
                                                SUPPORT_LARGE_FRONT_SUB_DISPLAY = z6;
                                                SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN = !z11 && Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.subscreen_activity_on_main", false);
                                                SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING = !z11 && z6 && SystemProperties.getBoolean("debug.subscreen_plugin_disconnect_when_unfolding", false);
                                                WALLPAPER_VIDEO_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("VIDEO");
                                                WALLPAPER_VIDEO_PLAY_RANDOM_POSITION = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LOCKSCREEN_SUPPORT_VIDEO_PLAY_RANDOM_POSITION");
                                                boolean z12 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
                                                WALLPAPER_BLUR = z12;
                                                WALLPAPER_CAPTURED_BLUR = z12 && SemFloatingFeature.getInstance().getBoolean(str);
                                                WALLPAPER_ROTATABLE_WALLPAPER = !SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("ROTATABLE") || DeviceType.isTablet();
                                                boolean contains10 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("LID");
                                                WALLPAPER_SUB_DISPLAY_MODE = contains10;
                                                WALLPAPER_FESTIVAL_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("FESTIVAL");
                                                boolean contains11 = str2.contains("WATCHFACE");
                                                WALLPAPER_SUB_WATCHFACE = contains11;
                                                boolean contains12 = str2.contains("VIRTUAL_DISPLAY");
                                                WALLPAPER_VIRTUAL_DISPLAY = contains12;
                                                boolean contains13 = str2.contains(charSequence2);
                                                WALLPAPER_CACHED_WALLPAPER = (contains10 || contains11 || contains12) ? false : true;
                                                WALLPAPER_PLAY_GIF = !contains11 || contains12;
                                                WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER = SystemProperties.getInt("ro.build.version.oneui", 0) < 50100;
                                                WALLPAPER_SUPPORT_SUIT_CASE = !contains13 || z10;
                                                WALLPAPER_MAISON_MARGIELA_EDITION = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigSpecialEdition").contains("MaisonMargiela");
                                                WALLPAPER_SUPPORT_DLS_SNAPSHOT = SystemProperties.getInt("ro.build.version.oneui", 0) >= 60101;
                                                PLUGIN_LOCK_MULTIPLE_ACTIVATION = !contains11 || contains12;
                                                PLUGIN_LOCK_LSM = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigDynamicLockScreenPolicy").contains("MagazineLockScreen");
                                            }
                                        }
                                        z6 = false;
                                        SUPPORT_LARGE_FRONT_SUB_DISPLAY = z6;
                                        SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN = !z11 && Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.subscreen_activity_on_main", false);
                                        SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING = !z11 && z6 && SystemProperties.getBoolean("debug.subscreen_plugin_disconnect_when_unfolding", false);
                                        WALLPAPER_VIDEO_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("VIDEO");
                                        WALLPAPER_VIDEO_PLAY_RANDOM_POSITION = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LOCKSCREEN_SUPPORT_VIDEO_PLAY_RANDOM_POSITION");
                                        boolean z122 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
                                        WALLPAPER_BLUR = z122;
                                        WALLPAPER_CAPTURED_BLUR = z122 && SemFloatingFeature.getInstance().getBoolean(str);
                                        WALLPAPER_ROTATABLE_WALLPAPER = !SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("ROTATABLE") || DeviceType.isTablet();
                                        boolean contains102 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("LID");
                                        WALLPAPER_SUB_DISPLAY_MODE = contains102;
                                        WALLPAPER_FESTIVAL_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("FESTIVAL");
                                        boolean contains112 = str2.contains("WATCHFACE");
                                        WALLPAPER_SUB_WATCHFACE = contains112;
                                        boolean contains122 = str2.contains("VIRTUAL_DISPLAY");
                                        WALLPAPER_VIRTUAL_DISPLAY = contains122;
                                        boolean contains132 = str2.contains(charSequence2);
                                        WALLPAPER_CACHED_WALLPAPER = (contains102 || contains112 || contains122) ? false : true;
                                        WALLPAPER_PLAY_GIF = !contains112 || contains122;
                                        WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER = SystemProperties.getInt("ro.build.version.oneui", 0) < 50100;
                                        WALLPAPER_SUPPORT_SUIT_CASE = !contains132 || z10;
                                        WALLPAPER_MAISON_MARGIELA_EDITION = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigSpecialEdition").contains("MaisonMargiela");
                                        WALLPAPER_SUPPORT_DLS_SNAPSHOT = SystemProperties.getInt("ro.build.version.oneui", 0) >= 60101;
                                        PLUGIN_LOCK_MULTIPLE_ACTIVATION = !contains112 || contains122;
                                        PLUGIN_LOCK_LSM = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigDynamicLockScreenPolicy").contains("MagazineLockScreen");
                                    }
                                }
                                z5 = false;
                                AOD_LIGHT_REVEAL = z5;
                                if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1) < 1) {
                                }
                                AOD_FULLSCREEN = z10;
                                AOD_SUB_FULLSCREEN = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1) != 3;
                                AOD_HYSTERESIS_BRIGHTNESS = z10;
                                AOD_TSP_CONTROL = Build.VERSION.SEM_FIRST_SDK_INT < 33;
                                AOD_SELF_POKE_DRAW_LOCK = SystemProperties.getBoolean("persist.enable.self.poke.draw_lock", true);
                                COVER_SUPPORTED = DeviceType.isCoverSupported();
                                COVER_SAFEMODE = SafeUIState.isSysUiSafeModeEnabled();
                                boolean contains92 = str2.contains("VIRTUAL_DISPLAY");
                                COVER_VIRTUAL_DISPLAY = contains92;
                                COVER_ADJUST_REFRESH_RATE = !contains92 && Integer.parseInt("2") > 0;
                                COVER_DEBUG_CAMERA_COVER_ON_SIDE_COVER = !Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.camera_cover_on_side_cover", false);
                                contains = str2.contains("COVER");
                                SUBSCREEN_UI = contains;
                                if (contains) {
                                }
                                SUBSCREEN_WATCHFACE = z11;
                                if (contains) {
                                }
                                z6 = false;
                                SUPPORT_LARGE_FRONT_SUB_DISPLAY = z6;
                                SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN = !z11 && Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.subscreen_activity_on_main", false);
                                SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING = !z11 && z6 && SystemProperties.getBoolean("debug.subscreen_plugin_disconnect_when_unfolding", false);
                                WALLPAPER_VIDEO_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("VIDEO");
                                WALLPAPER_VIDEO_PLAY_RANDOM_POSITION = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LOCKSCREEN_SUPPORT_VIDEO_PLAY_RANDOM_POSITION");
                                boolean z1222 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
                                WALLPAPER_BLUR = z1222;
                                WALLPAPER_CAPTURED_BLUR = z1222 && SemFloatingFeature.getInstance().getBoolean(str);
                                WALLPAPER_ROTATABLE_WALLPAPER = !SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("ROTATABLE") || DeviceType.isTablet();
                                boolean contains1022 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("LID");
                                WALLPAPER_SUB_DISPLAY_MODE = contains1022;
                                WALLPAPER_FESTIVAL_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("FESTIVAL");
                                boolean contains1122 = str2.contains("WATCHFACE");
                                WALLPAPER_SUB_WATCHFACE = contains1122;
                                boolean contains1222 = str2.contains("VIRTUAL_DISPLAY");
                                WALLPAPER_VIRTUAL_DISPLAY = contains1222;
                                boolean contains1322 = str2.contains(charSequence2);
                                WALLPAPER_CACHED_WALLPAPER = (contains1022 || contains1122 || contains1222) ? false : true;
                                WALLPAPER_PLAY_GIF = !contains1122 || contains1222;
                                WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER = SystemProperties.getInt("ro.build.version.oneui", 0) < 50100;
                                WALLPAPER_SUPPORT_SUIT_CASE = !contains1322 || z10;
                                WALLPAPER_MAISON_MARGIELA_EDITION = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigSpecialEdition").contains("MaisonMargiela");
                                WALLPAPER_SUPPORT_DLS_SNAPSHOT = SystemProperties.getInt("ro.build.version.oneui", 0) >= 60101;
                                PLUGIN_LOCK_MULTIPLE_ACTIVATION = !contains1122 || contains1222;
                                PLUGIN_LOCK_LSM = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigDynamicLockScreenPolicy").contains("MagazineLockScreen");
                            }
                            Log.e(str4, "getBrightnessValues not matched!");
                        }
                        z3 = false;
                        AOD_BRIGHTNESS_CONTROL = z3;
                        str2 = VALUE_SUB_DISPLAY_POLICY;
                        AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT = !str2.contains("AOD") && (str2.contains("LOCKSCREEN") || str2.contains("WATCHFACE"));
                        boolean contains82 = str2.contains("COVER");
                        AOD_SUB_DISPLAY_COVER = contains82;
                        AOD_SUB_DISPLAY_LOCK = str2.contains("LOCKSCREEN");
                        if (!SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM").contains("activeclock")) {
                        }
                        AOD_DISABLE_CLOCK_TRANSITION = z4;
                        AOD_SAFEMODE = SafeUIState.isSysUiSafeModeEnabled();
                        if (!z4) {
                        }
                        z5 = false;
                        AOD_LIGHT_REVEAL = z5;
                        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1) < 1) {
                        }
                        AOD_FULLSCREEN = z10;
                        AOD_SUB_FULLSCREEN = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1) != 3;
                        AOD_HYSTERESIS_BRIGHTNESS = z10;
                        AOD_TSP_CONTROL = Build.VERSION.SEM_FIRST_SDK_INT < 33;
                        AOD_SELF_POKE_DRAW_LOCK = SystemProperties.getBoolean("persist.enable.self.poke.draw_lock", true);
                        COVER_SUPPORTED = DeviceType.isCoverSupported();
                        COVER_SAFEMODE = SafeUIState.isSysUiSafeModeEnabled();
                        boolean contains922 = str2.contains("VIRTUAL_DISPLAY");
                        COVER_VIRTUAL_DISPLAY = contains922;
                        COVER_ADJUST_REFRESH_RATE = !contains922 && Integer.parseInt("2") > 0;
                        COVER_DEBUG_CAMERA_COVER_ON_SIDE_COVER = !Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.camera_cover_on_side_cover", false);
                        contains = str2.contains("COVER");
                        SUBSCREEN_UI = contains;
                        if (contains) {
                        }
                        SUBSCREEN_WATCHFACE = z11;
                        if (contains) {
                        }
                        z6 = false;
                        SUPPORT_LARGE_FRONT_SUB_DISPLAY = z6;
                        SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN = !z11 && Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.subscreen_activity_on_main", false);
                        SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING = !z11 && z6 && SystemProperties.getBoolean("debug.subscreen_plugin_disconnect_when_unfolding", false);
                        WALLPAPER_VIDEO_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("VIDEO");
                        WALLPAPER_VIDEO_PLAY_RANDOM_POSITION = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LOCKSCREEN_SUPPORT_VIDEO_PLAY_RANDOM_POSITION");
                        boolean z12222 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
                        WALLPAPER_BLUR = z12222;
                        WALLPAPER_CAPTURED_BLUR = z12222 && SemFloatingFeature.getInstance().getBoolean(str);
                        WALLPAPER_ROTATABLE_WALLPAPER = !SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("ROTATABLE") || DeviceType.isTablet();
                        boolean contains10222 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("LID");
                        WALLPAPER_SUB_DISPLAY_MODE = contains10222;
                        WALLPAPER_FESTIVAL_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("FESTIVAL");
                        boolean contains11222 = str2.contains("WATCHFACE");
                        WALLPAPER_SUB_WATCHFACE = contains11222;
                        boolean contains12222 = str2.contains("VIRTUAL_DISPLAY");
                        WALLPAPER_VIRTUAL_DISPLAY = contains12222;
                        boolean contains13222 = str2.contains(charSequence2);
                        WALLPAPER_CACHED_WALLPAPER = (contains10222 || contains11222 || contains12222) ? false : true;
                        WALLPAPER_PLAY_GIF = !contains11222 || contains12222;
                        WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER = SystemProperties.getInt("ro.build.version.oneui", 0) < 50100;
                        WALLPAPER_SUPPORT_SUIT_CASE = !contains13222 || z10;
                        WALLPAPER_MAISON_MARGIELA_EDITION = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigSpecialEdition").contains("MaisonMargiela");
                        WALLPAPER_SUPPORT_DLS_SNAPSHOT = SystemProperties.getInt("ro.build.version.oneui", 0) >= 60101;
                        PLUGIN_LOCK_MULTIPLE_ACTIVATION = !contains11222 || contains12222;
                        PLUGIN_LOCK_LSM = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigDynamicLockScreenPolicy").contains("MagazineLockScreen");
                    }
                } else {
                    str = "SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR";
                }
                z2 = false;
                LOCKUI_CAPTURED_BLUR = z2;
                LOCKUI_MULTI_USER = (DeviceType.isTablet() || !Rune.SYSUI_MULTI_USER || FactoryTest.isFactoryBinary()) ? false : true;
                LOCKUI_ECO_BATTERY = !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_DISABLE_ECO_BATTERY");
                currentApplication2 = ActivityThread.currentApplication();
                String str42 = "DeviceType";
                Log.i(str42, "isSupportBrightnessControl: context : " + currentApplication2);
                if (currentApplication2 != null) {
                }
                z3 = false;
                AOD_BRIGHTNESS_CONTROL = z3;
                str2 = VALUE_SUB_DISPLAY_POLICY;
                AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT = !str2.contains("AOD") && (str2.contains("LOCKSCREEN") || str2.contains("WATCHFACE"));
                boolean contains822 = str2.contains("COVER");
                AOD_SUB_DISPLAY_COVER = contains822;
                AOD_SUB_DISPLAY_LOCK = str2.contains("LOCKSCREEN");
                if (!SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM").contains("activeclock")) {
                }
                AOD_DISABLE_CLOCK_TRANSITION = z4;
                AOD_SAFEMODE = SafeUIState.isSysUiSafeModeEnabled();
                if (!z4) {
                }
                z5 = false;
                AOD_LIGHT_REVEAL = z5;
                if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1) < 1) {
                }
                AOD_FULLSCREEN = z10;
                AOD_SUB_FULLSCREEN = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1) != 3;
                AOD_HYSTERESIS_BRIGHTNESS = z10;
                AOD_TSP_CONTROL = Build.VERSION.SEM_FIRST_SDK_INT < 33;
                AOD_SELF_POKE_DRAW_LOCK = SystemProperties.getBoolean("persist.enable.self.poke.draw_lock", true);
                COVER_SUPPORTED = DeviceType.isCoverSupported();
                COVER_SAFEMODE = SafeUIState.isSysUiSafeModeEnabled();
                boolean contains9222 = str2.contains("VIRTUAL_DISPLAY");
                COVER_VIRTUAL_DISPLAY = contains9222;
                COVER_ADJUST_REFRESH_RATE = !contains9222 && Integer.parseInt("2") > 0;
                COVER_DEBUG_CAMERA_COVER_ON_SIDE_COVER = !Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.camera_cover_on_side_cover", false);
                contains = str2.contains("COVER");
                SUBSCREEN_UI = contains;
                if (contains) {
                }
                SUBSCREEN_WATCHFACE = z11;
                if (contains) {
                }
                z6 = false;
                SUPPORT_LARGE_FRONT_SUB_DISPLAY = z6;
                SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN = !z11 && Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.subscreen_activity_on_main", false);
                SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING = !z11 && z6 && SystemProperties.getBoolean("debug.subscreen_plugin_disconnect_when_unfolding", false);
                WALLPAPER_VIDEO_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("VIDEO");
                WALLPAPER_VIDEO_PLAY_RANDOM_POSITION = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LOCKSCREEN_SUPPORT_VIDEO_PLAY_RANDOM_POSITION");
                boolean z122222 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
                WALLPAPER_BLUR = z122222;
                WALLPAPER_CAPTURED_BLUR = z122222 && SemFloatingFeature.getInstance().getBoolean(str);
                WALLPAPER_ROTATABLE_WALLPAPER = !SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("ROTATABLE") || DeviceType.isTablet();
                boolean contains102222 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("LID");
                WALLPAPER_SUB_DISPLAY_MODE = contains102222;
                WALLPAPER_FESTIVAL_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("FESTIVAL");
                boolean contains112222 = str2.contains("WATCHFACE");
                WALLPAPER_SUB_WATCHFACE = contains112222;
                boolean contains122222 = str2.contains("VIRTUAL_DISPLAY");
                WALLPAPER_VIRTUAL_DISPLAY = contains122222;
                boolean contains132222 = str2.contains(charSequence2);
                WALLPAPER_CACHED_WALLPAPER = (contains102222 || contains112222 || contains122222) ? false : true;
                WALLPAPER_PLAY_GIF = !contains112222 || contains122222;
                WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER = SystemProperties.getInt("ro.build.version.oneui", 0) < 50100;
                WALLPAPER_SUPPORT_SUIT_CASE = !contains132222 || z10;
                WALLPAPER_MAISON_MARGIELA_EDITION = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigSpecialEdition").contains("MaisonMargiela");
                WALLPAPER_SUPPORT_DLS_SNAPSHOT = SystemProperties.getInt("ro.build.version.oneui", 0) >= 60101;
                PLUGIN_LOCK_MULTIPLE_ACTIVATION = !contains112222 || contains122222;
                PLUGIN_LOCK_LSM = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigDynamicLockScreenPolicy").contains("MagazineLockScreen");
            }
        }
        z = true;
        SECURITY_BLOCK_CARRIER_TEXT_WHEN_SIM_NOT_READY = z;
        SECURITY_CLEAR_NO_SIM_DEFAULT_TEXT = !string.contains("ClearEmergencyCallWhenNoSim") || contains7 || contains6;
        SECURITY_SIM_PERM_DISABLED = string2.contains("SupportSimPermanentDisable");
        SECURITY_NOT_REQUIRE_SIMPUK_CODE = string2.contains("UseTMOSIMPINLock");
        SECURITY_WARNING_WIPE_OUT_MESSAGE = string2.contains("FactoryResetProtectionWarning");
        SECURITY_FACTORY_RESET_WITHOUT_UI = string2.contains("FactoryResetWithoutUI");
        SECURITY_VZW_INSTRUCTION = string2.contains("FactoryResetProtectionWarning");
        int i2 = DeviceType.supportTablet;
        SECURITY_FINGERPRINT_GUIDE_POPUP = isTablet;
        if (contains4) {
        }
        string.contains("UseLGTSimText");
        LOCKUI_BOTTOM_USIM_TEXT = string.contains("DisplayUsimText");
        LOCKUI_HELP_TEXT_FOR_CHN = string2.contains("UseSamsungAccountAuth");
        String str32 = VALUE_SUB_DISPLAY_POLICY;
        LOCKUI_SUB_DISPLAY_LOCK = str32.contains("LOCKSCREEN");
        LOCKUI_SUB_DISPLAY_COVER = str32.contains("COVER");
        LOCKUI_AOD_PACKAGE_AVAILABLE = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM").contains(charSequence);
        LOCKUI_BLUR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG")) {
        }
        z2 = false;
        LOCKUI_CAPTURED_BLUR = z2;
        LOCKUI_MULTI_USER = (DeviceType.isTablet() || !Rune.SYSUI_MULTI_USER || FactoryTest.isFactoryBinary()) ? false : true;
        LOCKUI_ECO_BATTERY = !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_DISABLE_ECO_BATTERY");
        currentApplication2 = ActivityThread.currentApplication();
        String str422 = "DeviceType";
        Log.i(str422, "isSupportBrightnessControl: context : " + currentApplication2);
        if (currentApplication2 != null) {
        }
        z3 = false;
        AOD_BRIGHTNESS_CONTROL = z3;
        str2 = VALUE_SUB_DISPLAY_POLICY;
        AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT = !str2.contains("AOD") && (str2.contains("LOCKSCREEN") || str2.contains("WATCHFACE"));
        boolean contains8222 = str2.contains("COVER");
        AOD_SUB_DISPLAY_COVER = contains8222;
        AOD_SUB_DISPLAY_LOCK = str2.contains("LOCKSCREEN");
        if (!SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM").contains("activeclock")) {
        }
        AOD_DISABLE_CLOCK_TRANSITION = z4;
        AOD_SAFEMODE = SafeUIState.isSysUiSafeModeEnabled();
        if (!z4) {
        }
        z5 = false;
        AOD_LIGHT_REVEAL = z5;
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1) < 1) {
        }
        AOD_FULLSCREEN = z10;
        AOD_SUB_FULLSCREEN = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1) != 3;
        AOD_HYSTERESIS_BRIGHTNESS = z10;
        AOD_TSP_CONTROL = Build.VERSION.SEM_FIRST_SDK_INT < 33;
        AOD_SELF_POKE_DRAW_LOCK = SystemProperties.getBoolean("persist.enable.self.poke.draw_lock", true);
        COVER_SUPPORTED = DeviceType.isCoverSupported();
        COVER_SAFEMODE = SafeUIState.isSysUiSafeModeEnabled();
        boolean contains92222 = str2.contains("VIRTUAL_DISPLAY");
        COVER_VIRTUAL_DISPLAY = contains92222;
        COVER_ADJUST_REFRESH_RATE = !contains92222 && Integer.parseInt("2") > 0;
        COVER_DEBUG_CAMERA_COVER_ON_SIDE_COVER = !Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.camera_cover_on_side_cover", false);
        contains = str2.contains("COVER");
        SUBSCREEN_UI = contains;
        if (contains) {
        }
        SUBSCREEN_WATCHFACE = z11;
        if (contains) {
        }
        z6 = false;
        SUPPORT_LARGE_FRONT_SUB_DISPLAY = z6;
        SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN = !z11 && Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.subscreen_activity_on_main", false);
        SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING = !z11 && z6 && SystemProperties.getBoolean("debug.subscreen_plugin_disconnect_when_unfolding", false);
        WALLPAPER_VIDEO_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("VIDEO");
        WALLPAPER_VIDEO_PLAY_RANDOM_POSITION = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LOCKSCREEN_SUPPORT_VIDEO_PLAY_RANDOM_POSITION");
        boolean z1222222 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        WALLPAPER_BLUR = z1222222;
        WALLPAPER_CAPTURED_BLUR = z1222222 && SemFloatingFeature.getInstance().getBoolean(str);
        WALLPAPER_ROTATABLE_WALLPAPER = !SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("ROTATABLE") || DeviceType.isTablet();
        boolean contains1022222 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("LID");
        WALLPAPER_SUB_DISPLAY_MODE = contains1022222;
        WALLPAPER_FESTIVAL_WALLPAPER = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE").contains("FESTIVAL");
        boolean contains1122222 = str2.contains("WATCHFACE");
        WALLPAPER_SUB_WATCHFACE = contains1122222;
        boolean contains1222222 = str2.contains("VIRTUAL_DISPLAY");
        WALLPAPER_VIRTUAL_DISPLAY = contains1222222;
        boolean contains1322222 = str2.contains(charSequence2);
        WALLPAPER_CACHED_WALLPAPER = (contains1022222 || contains1122222 || contains1222222) ? false : true;
        WALLPAPER_PLAY_GIF = !contains1122222 || contains1222222;
        WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER = SystemProperties.getInt("ro.build.version.oneui", 0) < 50100;
        WALLPAPER_SUPPORT_SUIT_CASE = !contains1322222 || z10;
        WALLPAPER_MAISON_MARGIELA_EDITION = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigSpecialEdition").contains("MaisonMargiela");
        WALLPAPER_SUPPORT_DLS_SNAPSHOT = SystemProperties.getInt("ro.build.version.oneui", 0) >= 60101;
        PLUGIN_LOCK_MULTIPLE_ACTIVATION = !contains1122222 || contains1222222;
        PLUGIN_LOCK_LSM = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigDynamicLockScreenPolicy").contains("MagazineLockScreen");
    }
}
