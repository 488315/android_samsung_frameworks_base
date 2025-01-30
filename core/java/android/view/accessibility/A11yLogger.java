package android.view.accessibility;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.PackageInfo;
import android.p009os.Bundle;
import android.p009os.UserHandle;
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
  public static final String PACKAGE_NAME_LIVE_TRANSCRIBE =
      "com.google.audio.hearing.visualization.accessibility.scribe";
  public static final String PACKAGE_NAME_SAMSUNG_TALKBACK =
      "com.samsung.android.accessibility.talkback";
  public static final String PACKAGE_NAME_SETTINGS = "com.android.settings";
  public static final String SA_ACCESSIBILITY_MAGIFICATION_TRIPLE_TAP = "A11Y9004";
  public static final String SA_ACCESSIBILITY_MAGNIFICATION_CHANGE_MODE = "AMFC";
  public static final String SA_ACCESSIBILITY_MAGNIFICATION_CHANGE_SIZE_FULL = "A11Y3194";
  public static final String SA_ACCESSIBILITY_MAGNIFICATION_CHANGE_SIZE_WINDOW = "A11Y3188";
  public static final String SA_ACCESSIBILITY_MAGNIFICATION_IGNORE = "AMFI";
  public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL = "A11Y3190";
  public static final String SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ALLOW_DIAGONAL_SCROLLING =
      "A11Y3195";

  /* renamed from: SA_ACCESSIBILITY_MAGNIFICATION_PANNEL_ALLOW_DIAGONAL_SCROLLING_SETTING */
  public static final String f575xbe4867bb = "A11YS3195";
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
  public static final String SA_ACCESSIBILITY_SETUPWIZARD_DIRECT_ACCESS = "A11Y9005";
  public static final String SA_ACCESSIBILITY_SETUPWIZARD_TWO_FINGER = "A11Y9006";
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
  public static final HashMap<String, String> shortcutMap;

  public static boolean checkVersionOfDMA(Context context) {
    try {
      PackageInfo packageInfo =
          context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0);
      Slog.m113d("Validation", "dma pkg : " + packageInfo.versionCode);
      return packageInfo.versionCode >= 540000000;
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
    Slog.m113d(TAG, "insertSALog id : " + id + ", dimension : " + dimension);
    Bundle bundle = new Bundle();
    bundle.putString(SemShareConstants.DMA_SURVEY_FEATURE_TRACKING_ID, SA_TRACKING_ID);
    bundle.putString("feature", id);
    bundle.putString("type", SemShareConstants.SURVEY_CONTENT_TYPE_VALUE);
    bundle.putString(SemShareConstants.SURVERY_EXTRA_OWN_PACKAGE, "com.samsung.accessibility");
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
    if (shortcutType == 1) {
      id = SA_ACCESSIBILITY_SHORTCUT_VOLUME_UP_DOWN;
    } else if (shortcutType == 2) {
      id = SA_ACCESSIBILITY_SHORTCUT_SIDE_KEY_VOLUME_UP;
    }
    String function = shortcutMap.get(targetName);
    if (TextUtils.isEmpty(function)) {
      function = "Others";
    }
    insertLog(context, id, createDimension(function));
  }

  static {
    ComponentName createRelative =
        ComponentName.createRelative(
            "com.android.settings",
            "com.samsung.android.settings.accessibility.shortcut.AccessibilityHomepageActivityShortcut");
    COMPONENT_NAME_ACCESSIBILITY_HOMEPAGE_SHORTCUT = createRelative;
    ComponentName componentName =
        new ComponentName(
            PACKAGE_NAME_SAMSUNG_TALKBACK, "com.samsung.android.marvin.talkback.TalkBackService");
    COMPONENT_NAME_SAMSUNG_TALKBACK = componentName;
    ComponentName createRelative2 =
        ComponentName.createRelative(
            "com.samsung.android.honeyboard",
            ".settings.swipetouchandfeedback.speakkeyboardinputaloud.SpeakKeyboardInputAloudShortcut");
    COMPONENT_NAME_SPEAK_KEYBOARD_INPUT_ALOUD = createRelative2;
    ComponentName createRelative3 =
        ComponentName.createRelative(
            "com.android.settings",
            "com.samsung.android.settings.accessibility.shortcut.MagnifierCameraShortcut");
    COMPONENT_NAME_MAGNIFIER_CAMERA_SHORTCUT = createRelative3;
    ComponentName createRelative4 =
        ComponentName.createRelative(
            "com.android.settings",
            "com.samsung.android.settings.accessibility.shortcut.HighContrastFontsShortcut");
    COMPONENT_NAME_HIGH_CONTRAST_FONT_SHORTCUT = createRelative4;
    ComponentName createRelative5 =
        ComponentName.createRelative(
            "com.android.settings",
            "com.samsung.android.settings.accessibility.shortcut.ColorLensShortcut");
    COMPONENT_NAME_COLOR_LENS_SHORTCUT = createRelative5;
    ComponentName createRelative6 =
        ComponentName.createRelative(
            "com.android.settings",
            "com.samsung.android.settings.accessibility.shortcut.ColorAdjustmentShortcut");
    COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT = createRelative6;
    ComponentName createRelative7 =
        ComponentName.createRelative(
            "com.android.settings",
            "com.samsung.android.settings.accessibility.shortcut.ReluminoShortcut");
    COMPONENT_NAME_RELUMINO_SHORTCUT = createRelative7;
    ComponentName createRelative8 =
        ComponentName.createRelative(
            "com.android.settings",
            "com.samsung.android.settings.accessibility.shortcut.AmplifyShortcut");
    COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT = createRelative8;
    ComponentName createRelative9 =
        ComponentName.createRelative(
            "com.android.settings",
            "com.samsung.android.settings.accessibility.shortcut.MuteAllShortcut");
    COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT = createRelative9;
    ComponentName componentName2 =
        new ComponentName(
            PACKAGE_NAME_LIVE_TRANSCRIBE,
            "com.google.audio.hearing.visualization.accessibility.dolphin.ui.visualizer.TimelineActivity");
    COMPONENT_NAME_GOOGLE_SOUND_NOTIFICATION_SHORTCUT = componentName2;
    ComponentName createRelative10 =
        ComponentName.createRelative(
            PACKAGE_NAME_LIVE_TRANSCRIBE, ".SpeechToTextAccessibilityService");
    COMPONENT_NAME_LIVE_TRANSCRIBE = createRelative10;
    ComponentName createRelative11 =
        ComponentName.createRelative(PACKAGE_NAME_LIVE_TRANSCRIBE, ".MainActivity");
    COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT = createRelative11;
    ComponentName createRelative12 =
        ComponentName.createRelative(
            "com.samsung.accessibility", ".universalswitch.UniversalSwitchService");
    COMPONENT_NAME_UNIVERSAL_SWITCH = createRelative12;
    ComponentName createRelative13 =
        ComponentName.createRelative(
            "com.samsung.accessibility", ".assistantmenu.serviceframework.AssistantMenuService");
    COMPONENT_NAME_ASSISTANT_MENU = createRelative13;
    ComponentName componentName3 =
        new ComponentName(
            "com.google.android.apps.accessibility.voiceaccess",
            "com.google.android.apps.accessibility.voiceaccess.JustSpeakService");
    COMPONENT_NAME_VOICE_ACCESS = componentName3;
    ComponentName createRelative14 =
        ComponentName.createRelative(
            "com.samsung.accessibility", ".shortcut.InteractionControlShortcut");
    COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT = createRelative14;
    HashMap<String, String> hashMap = new HashMap<>();
    shortcutMap = hashMap;
    hashMap.put(createRelative.flattenToString(), "Accessibility");
    hashMap.put(componentName.flattenToString(), "Talkback");
    hashMap.put(createRelative3.flattenToString(), "Magnifier");
    hashMap.put("com.android.server.accessibility.MagnificationController", "Magnification");
    hashMap.put(
        AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString(),
        "ColorInversion");
    hashMap.put(createRelative4.flattenToString(), "HighContrastFonts");
    hashMap.put(
        AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString(),
        "ColorCorrection");
    hashMap.put(createRelative7.flattenToString(), "Relumino");
    hashMap.put(createRelative6.flattenToString(), "ColorAdjustment");
    hashMap.put(createRelative5.flattenToString(), "ColorLens");
    hashMap.put(
        AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME.flattenToString(),
        "ReduceBrightness");
    hashMap.put(createRelative2.flattenToString(), "SpeakKeyboardInputAloud");
    hashMap.put(createRelative8.flattenToString(), "AmplifyAmbientSound");
    hashMap.put(createRelative9.flattenToString(), "MuteAllSounds");
    hashMap.put(componentName2.flattenToString(), "SoundNotification");
    hashMap.put(createRelative10.flattenToString(), "LiveTranscribe");
    hashMap.put(createRelative11.flattenToString(), "GoogleLiveTranscribe");
    hashMap.put(createRelative12.flattenToString(), "UniversalSwitch");
    hashMap.put(createRelative13.flattenToString(), "AssistantMenu");
    hashMap.put(componentName3.flattenToString(), "VoiceAccess");
    hashMap.put(createRelative14.flattenToString(), "InteractionControl");
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
