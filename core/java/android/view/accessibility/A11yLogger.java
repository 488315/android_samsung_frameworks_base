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
    private static final boolean DEBUG = true;
    public static final String PACKAGE_NAME_ACCESSIBILITY = "com.samsung.accessibility";
    public static final String PACKAGE_NAME_HONEYBOARD = "com.samsung.android.honeyboard";
    public static final String PACKAGE_NAME_SETTINGS = "com.android.settings";
    public static final String SA_ACCESSIBILITY_MAGIFICATION_TRIPLE_TAP = "A11Y9004";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_CHANGE_MODE = "AMFC";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_CHANGE_SIZE_FULL = "A11Y3194";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_CHANGE_SIZE_WINDOW = "A11Y3188";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_IGNORE = "AMFI";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL = "A11Y3190";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ALLOW_DIAGONAL_SCROLLING =
            "A11Y3195";
    public static final String
            SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ALLOW_DIAGONAL_SCROLLING_SETTING = "A11YS3195";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_CLOSE = "A11Y3199";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_SIZE_EDIT = "A11Y3186";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_SIZE_LARGE = "A11Y3193";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_SIZE_MEDIUM = "A11Y3192";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_SIZE_SMALL = "A11Y3191";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ZOOM_IN = "A11Y3198";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ZOOM_OUT = "A11Y3196";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ZOOM_SLIDER = "A11Y3197";
    public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ZOOM_SLIDER_SETTING =
            "A11YS3197";
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
    private static final String SA_ACTION =
            "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY";
    private static final String SA_PACKAGE = "com.sec.android.diagmonagent";
    private static final String SA_PACKAGE_NAME = "com.samsung.accessibility";
    private static final int SA_SUPPORT_VERSION = 540000000;
    private static final String SA_TRACKING_ID = "4G4-399-1009910";
    private static final String TAG = "A11yLogger";
    public static final ComponentName COMPONENT_NAME_ACCESSIBILITY_HOMEPAGE_SHORTCUT =
            ComponentName.createRelative(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.AccessibilityHomepageActivityShortcut");
    public static final String PACKAGE_NAME_SAMSUNG_TALKBACK =
            "com.samsung.android.accessibility.talkback";
    public static final ComponentName COMPONENT_NAME_SAMSUNG_TALKBACK =
            new ComponentName(
                    PACKAGE_NAME_SAMSUNG_TALKBACK,
                    "com.samsung.android.marvin.talkback.TalkBackService");
    public static final ComponentName COMPONENT_NAME_SPEAK_KEYBOARD_INPUT_ALOUD =
            ComponentName.createRelative(
                    "com.samsung.android.honeyboard",
                    ".settings.swipetouchandfeedback.speakkeyboardinputaloud.SpeakKeyboardInputAloudShortcut");
    public static final ComponentName COMPONENT_NAME_MAGNIFIER_CAMERA_SHORTCUT =
            ComponentName.createRelative(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.MagnifierCameraShortcut");
    public static final ComponentName COMPONENT_NAME_HIGH_CONTRAST_FONT_SHORTCUT =
            ComponentName.createRelative(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.HighContrastFontsShortcut");
    public static final ComponentName COMPONENT_NAME_COLOR_LENS_SHORTCUT =
            ComponentName.createRelative(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.ColorLensShortcut");
    public static final ComponentName COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT =
            ComponentName.createRelative(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.ColorAdjustmentShortcut");
    public static final ComponentName COMPONENT_NAME_RELUMINO_SHORTCUT =
            ComponentName.createRelative(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.ReluminoShortcut");
    public static final ComponentName COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT =
            ComponentName.createRelative(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.AmplifyShortcut");
    public static final ComponentName COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT =
            ComponentName.createRelative(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.MuteAllShortcut");
    public static final String PACKAGE_NAME_LIVE_TRANSCRIBE =
            "com.google.audio.hearing.visualization.accessibility.scribe";
    public static final ComponentName COMPONENT_NAME_GOOGLE_SOUND_NOTIFICATION_SHORTCUT =
            new ComponentName(
                    PACKAGE_NAME_LIVE_TRANSCRIBE,
                    "com.google.audio.hearing.visualization.accessibility.dolphin.ui.visualizer.TimelineActivity");
    public static final ComponentName COMPONENT_NAME_LIVE_TRANSCRIBE =
            ComponentName.createRelative(
                    PACKAGE_NAME_LIVE_TRANSCRIBE, ".SpeechToTextAccessibilityService");
    public static final ComponentName COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT =
            ComponentName.createRelative(PACKAGE_NAME_LIVE_TRANSCRIBE, ".MainActivity");
    public static final ComponentName COMPONENT_NAME_UNIVERSAL_SWITCH =
            ComponentName.createRelative(
                    "com.samsung.accessibility", ".universalswitch.UniversalSwitchService");
    public static final ComponentName COMPONENT_NAME_ASSISTANT_MENU =
            ComponentName.createRelative(
                    "com.samsung.accessibility",
                    ".assistantmenu.serviceframework.AssistantMenuService");
    public static final ComponentName COMPONENT_NAME_VOICE_ACCESS =
            new ComponentName(
                    "com.google.android.apps.accessibility.voiceaccess",
                    "com.google.android.apps.accessibility.voiceaccess.JustSpeakService");
    public static final ComponentName COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT =
            ComponentName.createRelative(
                    "com.samsung.accessibility", ".shortcut.InteractionControlShortcut");
    public static final HashMap<String, String> shortcutMap = new HashMap<>();

    public static boolean checkVersionOfDMA(Context context) {
        try {
            PackageInfo packageInfo =
                    context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0);
            Slog.d("Validation", "dma pkg : " + packageInfo.versionCode);
            return packageInfo.versionCode >= SA_SUPPORT_VERSION;
        } catch (Exception e) {
            return false;
        }
    }

    public static void insertLog(Context context, String id) {
        insertLog(context, id, null, null);
    }

    public static void insertLog(Context context, String id, String extra) {
        insertLog(context, id, extra, null);
    }

    public static void insertLog(Context context, String id, HashMap<String, String> dimension) {
        insertLog(context, id, null, dimension);
    }

    public static void insertLog(
            Context context, String id, String extra, HashMap<String, String> dimension) {
        if (checkVersionOfDMA(context)) {
            insertSALog(context, id, extra, dimension);
        }
    }

    private static void insertSALog(
            Context context, String id, String extra, HashMap<String, String> dimension) {
        Slog.d(TAG, "insertSALog id : " + id + ", dimension : " + dimension);
        Bundle bundle = new Bundle();
        bundle.putString(SemShareConstants.DMA_SURVEY_FEATURE_TRACKING_ID, SA_TRACKING_ID);
        bundle.putString("feature", id);
        bundle.putString("type", SemShareConstants.SURVEY_CONTENT_TYPE_VALUE);
        bundle.putString(SemShareConstants.SURVEY_EXTRA_OWN_PACKAGE, "com.samsung.accessibility");
        if (extra != null) {
            bundle.putString(SemShareConstants.SURVEY_CONTENT_EXTRA, extra);
        }
        if (dimension != null) {
            bundle.putSerializable(SemShareConstants.SURVEY_CONTENT_DIMENSION, dimension);
        }
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
        broadcastIntent.putExtras(bundle);
        broadcastIntent.setPackage("com.sec.android.diagmonagent");
        context.sendBroadcastAsUser(broadcastIntent, UserHandle.ALL);
    }

    public static void insertShortcutSaLog(Context context, int shortcutType, String targetName) {
        String id = SA_ACCESSIBILITY_SHORTCUT_ACCESSIBILITY_BUTTON;
        if (shortcutType == 2) {
            id = SA_ACCESSIBILITY_SHORTCUT_VOLUME_UP_DOWN;
        } else if (shortcutType == 512) {
            id = SA_ACCESSIBILITY_SHORTCUT_SIDE_KEY_VOLUME_UP;
        }
        String function = shortcutMap.get(targetName);
        if (TextUtils.isEmpty(function)) {
            function = "Others";
        }
        insertLog(context, id, createDimension(function));
    }

    static {
        shortcutMap.put(
                COMPONENT_NAME_ACCESSIBILITY_HOMEPAGE_SHORTCUT.flattenToString(), "Accessibility");
        shortcutMap.put(COMPONENT_NAME_SAMSUNG_TALKBACK.flattenToString(), "Talkback");
        shortcutMap.put(COMPONENT_NAME_MAGNIFIER_CAMERA_SHORTCUT.flattenToString(), "Magnifier");
        shortcutMap.put(
                "com.android.server.accessibility.MagnificationController", "Magnification");
        shortcutMap.put(
                AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString(),
                "ColorInversion");
        shortcutMap.put(
                COMPONENT_NAME_HIGH_CONTRAST_FONT_SHORTCUT.flattenToString(), "HighContrastFonts");
        shortcutMap.put(
                AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString(),
                "ColorCorrection");
        shortcutMap.put(COMPONENT_NAME_RELUMINO_SHORTCUT.flattenToString(), "Relumino");
        shortcutMap.put(
                COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT.flattenToString(), "ColorAdjustment");
        shortcutMap.put(COMPONENT_NAME_COLOR_LENS_SHORTCUT.flattenToString(), "ColorLens");
        shortcutMap.put(
                AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME
                        .flattenToString(),
                "ReduceBrightness");
        shortcutMap.put(
                COMPONENT_NAME_SPEAK_KEYBOARD_INPUT_ALOUD.flattenToString(),
                "SpeakKeyboardInputAloud");
        shortcutMap.put(
                COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT.flattenToString(),
                "AmplifyAmbientSound");
        shortcutMap.put(COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT.flattenToString(), "MuteAllSounds");
        shortcutMap.put(
                COMPONENT_NAME_GOOGLE_SOUND_NOTIFICATION_SHORTCUT.flattenToString(),
                "SoundNotification");
        shortcutMap.put(COMPONENT_NAME_LIVE_TRANSCRIBE.flattenToString(), "LiveTranscribe");
        shortcutMap.put(
                COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT.flattenToString(),
                "GoogleLiveTranscribe");
        shortcutMap.put(COMPONENT_NAME_UNIVERSAL_SWITCH.flattenToString(), "UniversalSwitch");
        shortcutMap.put(COMPONENT_NAME_ASSISTANT_MENU.flattenToString(), "AssistantMenu");
        shortcutMap.put(COMPONENT_NAME_VOICE_ACCESS.flattenToString(), "VoiceAccess");
        shortcutMap.put(
                COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT.flattenToString(),
                "InteractionControl");
    }

    public static HashMap<String, String> createDimension(String function) {
        String coverScreen;
        HashMap<String, String> dimension = new HashMap<>();
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            coverScreen = "y";
        } else {
            coverScreen = "n";
        }
        dimension.put("function", function);
        dimension.put("coverScreen", coverScreen);
        return dimension;
    }
}
