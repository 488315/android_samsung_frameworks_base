package android.view.accessibility;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.samsung.android.share.SemShareConstants;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class A11yLogger {
    public static final ComponentName COMPONENT_NAME_ACCESSIBILITY_HOMEPAGE_SHORTCUT;
    public static final ComponentName COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT;
    public static final ComponentName COMPONENT_NAME_ASSISTANT_MENU;
    public static final ComponentName COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT;
    public static final ComponentName COMPONENT_NAME_COLOR_LENS_SHORTCUT;
    public static final ComponentName COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT;
    public static final ComponentName COMPONENT_NAME_GOOGLE_SOUND_NOTIFICATION_SHORTCUT;
    public static final ComponentName COMPONENT_NAME_HIGH_CONTRAST_FONT_SHORTCUT;
    public static final ComponentName COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT;
    public static final ComponentName COMPONENT_NAME_LIVE_TRANSCRIBE;
    public static final ComponentName COMPONENT_NAME_MAGNIFIER_CAMERA_SHORTCUT;
    public static final ComponentName COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT;
    public static final ComponentName COMPONENT_NAME_RELUMINO_SHORTCUT;
    public static final ComponentName COMPONENT_NAME_SAMSUNG_TALKBACK;
    public static final ComponentName COMPONENT_NAME_SPEAK_KEYBOARD_INPUT_ALOUD;
    public static final ComponentName COMPONENT_NAME_UNIVERSAL_SWITCH;
    public static final ComponentName COMPONENT_NAME_VOICE_ACCESS;
    private static final boolean DEBUG = true;
    public static final String PACKAGE_NAME_ACCESSIBILITY = "com.samsung.accessibility";
    public static final String PACKAGE_NAME_HONEYBOARD = "com.samsung.android.honeyboard";
    public static final String PACKAGE_NAME_LIVE_TRANSCRIBE = "com.google.audio.hearing.visualization.accessibility.scribe";
    public static final String PACKAGE_NAME_SAMSUNG_TALKBACK = "com.samsung.android.accessibility.talkback";
    public static final String PACKAGE_NAME_SETTINGS = "com.android.settings";
    public static final String SA_ACCESSIBILITY_MAGIFICATION_TRIPLE_TAP = "A11Y9004";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_CHANGE_MODE = "AMFC";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_CHANGE_SIZE_FULL = "A11Y3194";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_CHANGE_SIZE_WINDOW = "A11Y3188";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_IGNORE = "AMFI";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL = "A11Y3190";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ALLOW_DIAGONAL_SCROLLING = "A11Y3195";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ALLOW_DIAGONAL_SCROLLING_SETTING = "A11YS3195";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_CLOSE = "A11Y3199";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_SIZE_EDIT = "A11Y3186";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_SIZE_LARGE = "A11Y3193";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_SIZE_MEDIUM = "A11Y3192";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_SIZE_SMALL = "A11Y3191";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ZOOM_IN = "A11Y3198";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ZOOM_OUT = "A11Y3196";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ZOOM_SLIDER = "A11Y3197";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ZOOM_SLIDER_SETTING = "A11YS3197";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_RESIZE = "AMFR";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_WINDOW_SIZE_DONE = "A11Y3187";
    public static final String SA_ACCESSIBILITY_SETUPWIZARD_TWO_FINGER = "A11Y9006";
    public static final String SA_ACCESSIBILITY_SETUPWIZARD_VOLUME_UP_DOWN = "A11Y9005";
    public static final String SA_ACCESSIBILITY_SHORTCUT_ACCESSIBILITY_BUTTON = "A11Y9001";
    public static final String SA_ACCESSIBILITY_SHORTCUT_SIDE_KEY_VOLUME_UP = "A11Y9002";
    public static final String SA_ACCESSIBILITY_SHORTCUT_VOLUME_UP_DOWN = "A11Y9003";
    public static final String SA_ACCESSIBILITY_STATUS_OFF = "Off";
    public static final String SA_ACCESSIBILITY_STATUS_ON = "On";
    public static final int SA_ACCESSIBILITY_VALUE_OFF = 1;
    public static final int SA_ACCESSIBILITY_VALUE_ON = 1000;
    private static final String SA_ACTION = "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY";
    private static final String SA_PACKAGE = "com.sec.android.diagmonagent";
    private static final String SA_PACKAGE_NAME = "com.samsung.accessibility";
    private static final int SA_SUPPORT_VERSION = 540000000;
    private static final String SA_TRACKING_ID = "4G4-399-1009910";
    private static final String TAG = "A11yLogger";
    public static final HashMap<String, String> shortcutMap;

    public static boolean checkVersionOfDMA(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0);
            Slog.d("Validation", "dma pkg : " + packageInfo.versionCode);
        } catch (Exception unused) {
        }
        return packageInfo.versionCode >= SA_SUPPORT_VERSION;
    }

    public static void insertLog(Context context, String str) {
        insertLog(context, str, null, null);
    }

    public static void insertLog(Context context, String str, String str2) {
        insertLog(context, str, str2, null);
    }

    public static void insertLog(Context context, String str, HashMap<String, String> map) {
        insertLog(context, str, null, map);
    }

    public static void insertLog(Context context, String str, String str2, HashMap<String, String> map) {
        if (checkVersionOfDMA(context)) {
            insertSALog(context, str, str2, map);
        }
    }

    private static void insertSALog(Context context, String str, String str2, HashMap<String, String> map) {
        Slog.d(TAG, "insertSALog id : " + str + ", dimension : " + map);
        Bundle bundle = new Bundle();
        bundle.putString(SemShareConstants.DMA_SURVEY_FEATURE_TRACKING_ID, SA_TRACKING_ID);
        bundle.putString("feature", str);
        bundle.putString("type", SemShareConstants.SURVEY_CONTENT_TYPE_VALUE);
        bundle.putString(SemShareConstants.SURVEY_EXTRA_OWN_PACKAGE, "com.samsung.accessibility");
        if (str2 != null) {
            bundle.putString(SemShareConstants.SURVEY_CONTENT_EXTRA, str2);
        }
        if (map != null) {
            bundle.putSerializable(SemShareConstants.SURVEY_CONTENT_DIMENSION, map);
        }
        Intent intent = new Intent();
        intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
        intent.putExtras(bundle);
        intent.setPackage("com.sec.android.diagmonagent");
        context.sendBroadcastAsUser(intent, UserHandle.ALL);
    }

    public static void insertShortcutSaLog(Context context, int i, String str) {
        String str2;
        if (i == 2) {
            str2 = SA_ACCESSIBILITY_SHORTCUT_VOLUME_UP_DOWN;
        } else if (i != 512) {
            str2 = SA_ACCESSIBILITY_SHORTCUT_ACCESSIBILITY_BUTTON;
        } else {
            str2 = SA_ACCESSIBILITY_SHORTCUT_SIDE_KEY_VOLUME_UP;
        }
        String str3 = shortcutMap.get(str);
        if (TextUtils.isEmpty(str3)) {
            str3 = "Others";
        }
        insertLog(context, str2, createDimension(str3));
    }

    static {
        ComponentName componentNameCreateRelative = ComponentName.createRelative("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.AccessibilityHomepageActivityShortcut");
        COMPONENT_NAME_ACCESSIBILITY_HOMEPAGE_SHORTCUT = componentNameCreateRelative;
        ComponentName componentName = new ComponentName(PACKAGE_NAME_SAMSUNG_TALKBACK, "com.samsung.android.marvin.talkback.TalkBackService");
        COMPONENT_NAME_SAMSUNG_TALKBACK = componentName;
        ComponentName componentNameCreateRelative2 = ComponentName.createRelative("com.samsung.android.honeyboard", ".settings.swipetouchandfeedback.speakkeyboardinputaloud.SpeakKeyboardInputAloudShortcut");
        COMPONENT_NAME_SPEAK_KEYBOARD_INPUT_ALOUD = componentNameCreateRelative2;
        ComponentName componentNameCreateRelative3 = ComponentName.createRelative("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.MagnifierCameraShortcut");
        COMPONENT_NAME_MAGNIFIER_CAMERA_SHORTCUT = componentNameCreateRelative3;
        ComponentName componentNameCreateRelative4 = ComponentName.createRelative("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.HighContrastFontsShortcut");
        COMPONENT_NAME_HIGH_CONTRAST_FONT_SHORTCUT = componentNameCreateRelative4;
        ComponentName componentNameCreateRelative5 = ComponentName.createRelative("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.ColorLensShortcut");
        COMPONENT_NAME_COLOR_LENS_SHORTCUT = componentNameCreateRelative5;
        ComponentName componentNameCreateRelative6 = ComponentName.createRelative("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.ColorAdjustmentShortcut");
        COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT = componentNameCreateRelative6;
        ComponentName componentNameCreateRelative7 = ComponentName.createRelative("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.ReluminoShortcut");
        COMPONENT_NAME_RELUMINO_SHORTCUT = componentNameCreateRelative7;
        ComponentName componentNameCreateRelative8 = ComponentName.createRelative("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.AmplifyShortcut");
        COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT = componentNameCreateRelative8;
        ComponentName componentNameCreateRelative9 = ComponentName.createRelative("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.MuteAllShortcut");
        COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT = componentNameCreateRelative9;
        ComponentName componentName2 = new ComponentName(PACKAGE_NAME_LIVE_TRANSCRIBE, "com.google.audio.hearing.visualization.accessibility.dolphin.ui.visualizer.TimelineActivity");
        COMPONENT_NAME_GOOGLE_SOUND_NOTIFICATION_SHORTCUT = componentName2;
        ComponentName componentNameCreateRelative10 = ComponentName.createRelative(PACKAGE_NAME_LIVE_TRANSCRIBE, ".SpeechToTextAccessibilityService");
        COMPONENT_NAME_LIVE_TRANSCRIBE = componentNameCreateRelative10;
        ComponentName componentNameCreateRelative11 = ComponentName.createRelative(PACKAGE_NAME_LIVE_TRANSCRIBE, ".MainActivity");
        COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT = componentNameCreateRelative11;
        ComponentName componentNameCreateRelative12 = ComponentName.createRelative("com.samsung.accessibility", ".universalswitch.UniversalSwitchService");
        COMPONENT_NAME_UNIVERSAL_SWITCH = componentNameCreateRelative12;
        ComponentName componentNameCreateRelative13 = ComponentName.createRelative("com.samsung.accessibility", ".assistantmenu.serviceframework.AssistantMenuService");
        COMPONENT_NAME_ASSISTANT_MENU = componentNameCreateRelative13;
        ComponentName componentName3 = new ComponentName("com.google.android.apps.accessibility.voiceaccess", "com.google.android.apps.accessibility.voiceaccess.JustSpeakService");
        COMPONENT_NAME_VOICE_ACCESS = componentName3;
        ComponentName componentNameCreateRelative14 = ComponentName.createRelative("com.samsung.accessibility", ".shortcut.InteractionControlShortcut");
        COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT = componentNameCreateRelative14;
        HashMap<String, String> map = new HashMap<>();
        shortcutMap = map;
        map.put(componentNameCreateRelative.flattenToString(), "Accessibility");
        map.put(componentName.flattenToString(), "Talkback");
        map.put(componentNameCreateRelative3.flattenToString(), "Magnifier");
        map.put("com.android.server.accessibility.MagnificationController", "Magnification");
        map.put(AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString(), "ColorInversion");
        map.put(componentNameCreateRelative4.flattenToString(), "HighContrastFonts");
        map.put(AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString(), "ColorCorrection");
        map.put(componentNameCreateRelative7.flattenToString(), "Relumino");
        map.put(componentNameCreateRelative6.flattenToString(), "ColorAdjustment");
        map.put(componentNameCreateRelative5.flattenToString(), "ColorLens");
        map.put(AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME.flattenToString(), "ReduceBrightness");
        map.put(componentNameCreateRelative2.flattenToString(), "SpeakKeyboardInputAloud");
        map.put(componentNameCreateRelative8.flattenToString(), "AmplifyAmbientSound");
        map.put(componentNameCreateRelative9.flattenToString(), "MuteAllSounds");
        map.put(componentName2.flattenToString(), "SoundNotification");
        map.put(componentNameCreateRelative10.flattenToString(), "LiveTranscribe");
        map.put(componentNameCreateRelative11.flattenToString(), "GoogleLiveTranscribe");
        map.put(componentNameCreateRelative12.flattenToString(), "UniversalSwitch");
        map.put(componentNameCreateRelative13.flattenToString(), "AssistantMenu");
        map.put(componentName3.flattenToString(), "VoiceAccess");
        map.put(componentNameCreateRelative14.flattenToString(), "InteractionControl");
    }

    public static HashMap<String, String> createDimension(String str) {
        String str2;
        HashMap<String, String> map = new HashMap<>();
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            str2 = "y";
        } else {
            str2 = "n";
        }
        map.put("function", str);
        map.put("coverScreen", str2);
        return map;
    }
}
