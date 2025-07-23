package com.android.internal.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.os.Vibrator;
import android.provider.Settings;
import android.provider.SettingsStringUtil;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.A11yLogger;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.display.feature.flags.Flags;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class AccessibilityShortcutController {
    static final String ACTION_LAUNCH_REMOVE_EXTRA_DIM_DIALOG = "com.android.systemui.action.LAUNCH_REMOVE_EXTRA_DIM_DIALOG";
    public static final String LIVE_TRANSCRIBE_CUSTOM_TILE_NAME = "custom(com.google.audio.hearing.visualization.accessibility.scribe/.service.ScribeTileService)";
    public static final String LIVE_TRANSCRIBE_TILE_NAME = "com.google.audio.hearing.visualization.accessibility.scribe/.service.ScribeTileService";
    public static final String MAGNIFICATION_CONTROLLER_NAME = "com.android.server.accessibility.MagnificationController";
    public static final String SOUND_NOTIFICATION_CUSTOM_TILE_NAME = "custom(com.google.audio.hearing.visualization.accessibility.scribe/com.google.audio.hearing.visualization.accessibility.dolphin.service.DolphinTileService)";
    public static final String SOUND_NOTIFICATION_TILE_NAME = "com.google.audio.hearing.visualization.accessibility.scribe/com.google.audio.hearing.visualization.accessibility.dolphin.service.DolphinTileService";
    private static final String TAG = "AccessibilityShortcutController";
    public static final String TALKBACK_SE = "com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService";
    private static Map<ComponentName, FrameworkFeatureInfo> sFrameworkShortcutFeaturesMap;
    private AlertDialog mAlertDialog;
    private final Context mContext;
    private boolean mEnabledOnLockScreen;
    public FrameworkObjectProvider mFrameworkObjectProvider = new FrameworkObjectProvider();
    private final Handler mHandler;
    private boolean mIsShortcutEnabled;
    private int mUserId;
    public final UserSetupCompleteObserver mUserSetupCompleteObserver;
    public static final ComponentName COLOR_INVERSION_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "ColorInversion");
    public static final ComponentName DALTONIZER_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "Daltonizer");
    public static final ComponentName MAGNIFICATION_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "Magnification");
    public static final ComponentName ONE_HANDED_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "OneHandedMode");
    public static final ComponentName REDUCE_BRIGHT_COLORS_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "ReduceBrightColors");
    public static final ComponentName FONT_SIZE_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "FontSize");
    public static final ComponentName AUTOCLICK_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "Autoclick");
    public static final ComponentName HIGH_CONTRAST_FONTS_COMPONENT_NAME = new ComponentName("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.HighContrastFontsShortcut");
    public static final ComponentName COLOR_LENS_COMPONENT_NAME = new ComponentName("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.ColorLensShortcut");
    public static final ComponentName SOUND_NOTIFICATION_COMPONENT_NAME = new ComponentName(A11yLogger.PACKAGE_NAME_LIVE_TRANSCRIBE, "com.google.audio.hearing.visualization.accessibility.dolphin.ui.visualizer.TimelineActivity");
    public static final ComponentName LIVE_TRANSCRIBE_COMPONENT_NAME = new ComponentName(A11yLogger.PACKAGE_NAME_LIVE_TRANSCRIBE, "com.google.audio.hearing.visualization.accessibility.scribe.MainActivity");
    public static final ComponentName ACCESSIBILITY_BUTTON_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "AccessibilityButton");
    public static final ComponentName ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "HearingAids");
    public static final ComponentName ACCESSIBILITY_HEARING_AIDS_TILE_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "HearingDevicesTile");
    public static final ComponentName COLOR_INVERSION_TILE_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "ColorInversionTile");
    public static final ComponentName DALTONIZER_TILE_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "ColorCorrectionTile");
    public static final ComponentName ONE_HANDED_TILE_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "OneHandedModeTile");
    public static final ComponentName REDUCE_BRIGHT_COLORS_TILE_SERVICE_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "ReduceBrightColorsTile");
    public static final ComponentName FONT_SIZE_TILE_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "FontSizeTile");
    public static final ComponentName HIGH_CONTRAST_FONTS_TILE_COMPONENT_NAME = new ComponentName("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.HighContrastFontsShortcutTile");
    public static final ComponentName COLOR_LENS_TILE_COMPONENT_NAME = new ComponentName("com.android.settings", "com.samsung.android.settings.accessibility.shortcut.ColorLensShortcutTile");
    private static final AudioAttributes VIBRATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(11).build();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DialogStatus {
        public static final int NOT_SHOWN = 0;
        public static final int SHOWN = 1;
    }

    public static Map<ComponentName, FrameworkFeatureInfo> getFrameworkShortcutFeaturesMap() {
        if (sFrameworkShortcutFeaturesMap == null) {
            ArrayMap arrayMap = new ArrayMap(8);
            arrayMap.put(COLOR_INVERSION_COMPONENT_NAME, new ToggleableFrameworkFeatureInfo(Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, "1", "0", R.string.color_inversion_feature_name));
            arrayMap.put(DALTONIZER_COMPONENT_NAME, new ToggleableFrameworkFeatureInfo(Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED, "1", "0", R.string.color_correction_feature_name));
            arrayMap.put(AUTOCLICK_COMPONENT_NAME, new ToggleableFrameworkFeatureInfo(Settings.Secure.ACCESSIBILITY_AUTOCLICK_ENABLED, "1", "0", R.string.autoclick_feature_name));
            if (RoSystemProperties.SUPPORT_ONE_HANDED_MODE) {
                arrayMap.put(ONE_HANDED_COMPONENT_NAME, new ToggleableFrameworkFeatureInfo(Settings.Secure.ONE_HANDED_MODE_ACTIVATED, "1", "0", R.string.one_handed_mode_feature_name));
            }
            arrayMap.put(REDUCE_BRIGHT_COLORS_COMPONENT_NAME, new ExtraDimFrameworkFeatureInfo(Settings.Secure.REDUCE_BRIGHT_COLORS_ACTIVATED, "1", "0", R.string.reduce_bright_colors_feature_name));
            arrayMap.put(ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME, new LaunchableFrameworkFeatureInfo(R.string.hearing_aids_feature_name));
            sFrameworkShortcutFeaturesMap = Collections.unmodifiableMap(arrayMap);
        }
        return sFrameworkShortcutFeaturesMap;
    }

    public AccessibilityShortcutController(Context context, Handler handler, int i) {
        this.mContext = context;
        this.mHandler = handler;
        this.mUserId = i;
        this.mUserSetupCompleteObserver = new UserSetupCompleteObserver(handler, i);
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.android.internal.accessibility.AccessibilityShortcutController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Collection<Uri> collection, int i2, int i3) {
                if (i3 == AccessibilityShortcutController.this.mUserId) {
                    AccessibilityShortcutController.this.onSettingsChanged();
                }
            }
        };
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE), false, contentObserver, -1);
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.ACCESSIBILITY_SHORTCUT_ON_LOCK_SCREEN), false, contentObserver, -1);
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN), false, contentObserver, -1);
        setCurrentUser(this.mUserId);
    }

    public void setCurrentUser(int i) {
        this.mUserId = i;
        onSettingsChanged();
        this.mUserSetupCompleteObserver.onUserSwitched(i);
    }

    public boolean isAccessibilityShortcutAvailable(boolean z) {
        KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
        if (!this.mIsShortcutEnabled) {
            return false;
        }
        if (!z || this.mEnabledOnLockScreen) {
            return true;
        }
        return AccessibilityUtils.isFoldedLargeCoverScreen() && !keyguardManager.isKeyguardSecure();
    }

    public void onSettingsChanged() {
        boolean hasShortcutTarget = hasShortcutTarget();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mEnabledOnLockScreen = Settings.Secure.getIntForUser(contentResolver, Settings.Secure.ACCESSIBILITY_SHORTCUT_ON_LOCK_SCREEN, Settings.Secure.getIntForUser(contentResolver, Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 0, this.mUserId), this.mUserId) == 1;
        this.mIsShortcutEnabled = hasShortcutTarget;
    }

    public void performAccessibilityShortcut() {
        Slog.d(TAG, "Accessibility shortcut activated");
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int currentUser = ActivityManager.getCurrentUser();
        String stringForUser = Settings.Secure.getStringForUser(contentResolver, Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, currentUser);
        if (stringForUser == null && AccessibilityUtils.isSetupWizard(this.mContext)) {
            Slog.d(TAG, "Accessibility shortcutTargetService == null");
            A11yLogger.insertLog(this.mContext, A11yLogger.SA_ACCESSIBILITY_SETUPWIZARD_VOLUME_UP_DOWN);
            stringForUser = TALKBACK_SE;
            Settings.Secure.putStringForUser(contentResolver, Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, TALKBACK_SE, currentUser);
        }
        if (TextUtils.isEmpty(stringForUser)) {
            Slog.d(TAG, "Accessibility shortcut target isEmpty");
            return;
        }
        Vibrator vibrator = (Vibrator) this.mContext.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (ShortcutUtils.isSupportDCMotorHapticFeedback(vibrator)) {
                ShortcutUtils.vibrateDCMotorHapticFeedback(this.mContext, vibrator);
            } else {
                vibrator.vibrate(ArrayUtils.convertToLongArray(this.mContext.getResources().getIntArray(R.array.config_longPressVibePattern)), -1, VIBRATION_ATTRIBUTES);
            }
        }
        if (shouldShowDialog()) {
            AlertDialog createShortcutWarningDialog = createShortcutWarningDialog(currentUser);
            this.mAlertDialog = createShortcutWarningDialog;
            if (createShortcutWarningDialog == null) {
                return;
            }
            if (!performTtsPrompt(createShortcutWarningDialog)) {
                playNotificationTone();
            }
            Window window = this.mAlertDialog.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.type = 2009;
            window.setAttributes(attributes);
            this.mAlertDialog.show();
            Settings.Secure.putIntForUser(contentResolver, Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 1, currentUser);
            return;
        }
        enableDefaultHardwareShortcut(currentUser);
        if (stringForUser != null && stringForUser.contains("com.samsung.accessibility/com.samsung.accessibility.shortcut.InteractionControlShortcut") && AccessibilityUtils.isAccessControlEnabled(this.mContext)) {
            Slog.d(TAG, "Interaction Control is activated");
            AccessibilityUtils.turnOffAccessControl(this.mContext);
            return;
        }
        if (stringForUser != null && stringForUser.contains("com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService") && AccessibilityUtils.isAccessibilityServiceEnabled(this.mContext, "com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService")) {
            Slog.d(TAG, "Universal switch is activated");
            AccessibilityUtils.setAccessibilityServiceState(this.mContext, ComponentName.unflattenFromString("com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService"), false, currentUser);
            return;
        }
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mAlertDialog = null;
        }
        showToast();
        this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).performAccessibilityShortcut(getDisplayId(), 2, null);
    }

    private boolean shouldShowDialog() {
        return !hasFeatureLeanback() && Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 0, ActivityManager.getCurrentUser()) == 0;
    }

    private void showToast() {
        AccessibilityServiceInfo infoForTargetService = getInfoForTargetService();
        if (infoForTargetService == null) {
            return;
        }
        String shortcutFeatureDescription = getShortcutFeatureDescription(false);
        if (shortcutFeatureDescription == null) {
            return;
        }
        boolean z = (infoForTargetService.flags & 256) != 0;
        boolean isServiceEnabled = isServiceEnabled(infoForTargetService);
        if (infoForTargetService.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion > 29 && z && isServiceEnabled) {
            return;
        }
        this.mFrameworkObjectProvider.makeToastFromText(this.mContext, String.format(this.mContext.getString(isServiceEnabled ? R.string.accessibility_shortcut_disabling_service : R.string.accessibility_shortcut_enabling_service), shortcutFeatureDescription), 1).show();
    }

    private AlertDialog createShortcutWarningDialog(final int i) {
        Context systemUiContext;
        List<AccessibilityTarget> targets = AccessibilityTargetHelper.getTargets(this.mContext, 2);
        if (targets.size() == 0) {
            return null;
        }
        final AccessibilityManager accessibilityManagerInstance = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext);
        FrameworkObjectProvider frameworkObjectProvider = this.mFrameworkObjectProvider;
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            systemUiContext = AccessibilityUtils.getSubDisplayContext(this.mContext);
        } else {
            systemUiContext = this.mFrameworkObjectProvider.getSystemUiContext();
        }
        return frameworkObjectProvider.getAlertDialogBuilder(systemUiContext).setTitle(getShortcutWarningTitle(targets)).setMessage(getShortcutWarningMessage(targets)).setCancelable(false).setPositiveButton(R.string.accessibility_shortcut_use, new DialogInterface.OnClickListener() { // from class: com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                AccessibilityShortcutController.this.lambda$createShortcutWarningDialog$0(i, dialogInterface, i2);
            }
        }).setNegativeButton(R.string.accessibility_shortcut_dont_use, new DialogInterface.OnClickListener() { // from class: com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                AccessibilityShortcutController.this.lambda$createShortcutWarningDialog$1(i, accessibilityManagerInstance, dialogInterface, i2);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                AccessibilityShortcutController.this.lambda$createShortcutWarningDialog$2(i, dialogInterface);
            }
        }).create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$0(int i, DialogInterface dialogInterface, int i2) {
        enableDefaultHardwareShortcut(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$1(int i, AccessibilityManager accessibilityManager, DialogInterface dialogInterface, int i2) {
        accessibilityManager.enableShortcutsForTargets(false, 2, ShortcutUtils.getShortcutTargetsFromSettings(this.mContext, 2, i), i);
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 0, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$2(int i, DialogInterface dialogInterface) {
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 0, i);
    }

    private String getShortcutWarningTitle(List<AccessibilityTarget> list) {
        if (list.size() == 1 && TALKBACK_SE.equals(list.get(0).getId())) {
            return this.mContext.getString(R.string.accessibility_shortcut_warning_title_samsung_talkback, list.get(0).getLabel());
        }
        return this.mContext.getString(R.string.accessibility_shortcut_warning_title_samsung);
    }

    private String getShortcutWarningMessage(List<AccessibilityTarget> list) {
        if (list.size() == 1) {
            return this.mContext.getString(R.string.accessibility_shortcut_single_service_warning_samsung, list.get(0).getLabel());
        }
        StringBuilder sb = new StringBuilder();
        Iterator<AccessibilityTarget> it = list.iterator();
        while (it.hasNext()) {
            sb.append(this.mContext.getString(R.string.accessibility_shortcut_multiple_service_list, it.next().getLabel()));
        }
        return this.mContext.getString(R.string.accessibility_shortcut_multiple_service_warning_samsung) + ShaderAssembler.NEWLINE + sb.toString() + ShaderAssembler.NEWLINE + this.mContext.getString(R.string.accessibility_select_different_function);
    }

    private AccessibilityServiceInfo getInfoForTargetService() {
        ComponentName shortcutTargetComponentName = getShortcutTargetComponentName();
        if (shortcutTargetComponentName == null) {
            return null;
        }
        return this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getInstalledServiceInfoWithComponentName(shortcutTargetComponentName);
    }

    private String getShortcutFeatureDescription(boolean z) {
        ComponentName shortcutTargetComponentName = getShortcutTargetComponentName();
        if (shortcutTargetComponentName == null) {
            return null;
        }
        FrameworkFeatureInfo frameworkFeatureInfo = getFrameworkShortcutFeaturesMap().get(shortcutTargetComponentName);
        if (frameworkFeatureInfo != null) {
            return frameworkFeatureInfo.getLabel(this.mContext);
        }
        AccessibilityServiceInfo installedServiceInfoWithComponentName = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getInstalledServiceInfoWithComponentName(shortcutTargetComponentName);
        if (installedServiceInfoWithComponentName == null) {
            return null;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        String charSequence = installedServiceInfoWithComponentName.getResolveInfo().loadLabel(packageManager).toString();
        CharSequence loadSummary = installedServiceInfoWithComponentName.loadSummary(packageManager);
        return (!z || TextUtils.isEmpty(loadSummary)) ? charSequence : String.format("%s\n%s", charSequence, loadSummary);
    }

    private boolean isServiceEnabled(AccessibilityServiceInfo accessibilityServiceInfo) {
        return this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getEnabledAccessibilityServiceList(-1, this.mUserId).contains(accessibilityServiceInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasFeatureLeanback() {
        return this.mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playNotificationTone() {
        int i = hasFeatureLeanback() ? 11 : 10;
        Ringtone ringtone = this.mFrameworkObjectProvider.getRingtone(this.mContext, Uri.parse("file://" + this.mContext.getString(R.string.config_defaultAccessibilityNotificationSound)));
        if (ringtone == null) {
            ringtone = this.mFrameworkObjectProvider.getRingtone(this.mContext, Settings.System.DEFAULT_NOTIFICATION_URI);
        }
        if (ringtone != null) {
            ringtone.setAudioAttributes(new AudioAttributes.Builder().setUsage(i).build());
            ringtone.play();
        }
    }

    private void enableDefaultHardwareShortcut(int i) {
        AccessibilityManager accessibilityManagerInstance = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext);
        if (Settings.Secure.getStringForUser(this.mContext.getContentResolver(), Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, i) != null) {
            return;
        }
        String string = this.mContext.getString(R.string.config_defaultAccessibilityService);
        ComponentName unflattenFromString = TextUtils.isEmpty(string) ? null : ComponentName.unflattenFromString(string);
        if (unflattenFromString == null) {
            return;
        }
        accessibilityManagerInstance.enableShortcutsForTargets(true, 2, Set.of(unflattenFromString.flattenToString()), i);
    }

    private boolean performTtsPrompt(AlertDialog alertDialog) {
        String shortcutFeatureDescription = getShortcutFeatureDescription(false);
        AccessibilityServiceInfo infoForTargetService = getInfoForTargetService();
        if (TextUtils.isEmpty(shortcutFeatureDescription) || infoForTargetService == null || (infoForTargetService.flags & 1024) == 0) {
            return false;
        }
        final TtsPrompt ttsPrompt = new TtsPrompt(shortcutFeatureDescription);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AccessibilityShortcutController.TtsPrompt.this.dismiss();
            }
        });
        return true;
    }

    private boolean hasShortcutTarget() {
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, this.mUserId);
        if (stringForUser == null && AccessibilityUtils.isSetupWizard(this.mContext)) {
            stringForUser = TALKBACK_SE;
        }
        return !TextUtils.isEmpty(stringForUser);
    }

    private ComponentName getShortcutTargetComponentName() {
        List<String> accessibilityShortcutTargets = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getAccessibilityShortcutTargets(2);
        if (accessibilityShortcutTargets.size() != 1) {
            return null;
        }
        return ComponentName.unflattenFromString(accessibilityShortcutTargets.get(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    class TtsPrompt implements TextToSpeech.OnInitListener {
        private static final int RETRY_MILLIS = 1000;
        private boolean mDismiss;
        private final CharSequence mText;
        private TextToSpeech mTts;
        private int mRetryCount = 3;
        private boolean mLanguageReady = false;

        TtsPrompt(String str) {
            this.mText = AccessibilityShortcutController.this.mContext.getString(R.string.accessibility_shortcut_spoken_feedback, str);
            this.mTts = AccessibilityShortcutController.this.mFrameworkObjectProvider.getTextToSpeech(AccessibilityShortcutController.this.mContext, this);
        }

        public void dismiss() {
            this.mDismiss = true;
            AccessibilityShortcutController.this.mHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda2(), this.mTts));
        }

        @Override // android.speech.tts.TextToSpeech.OnInitListener
        public void onInit(int i) {
            if (i != 0) {
                Slog.d(AccessibilityShortcutController.TAG, "Tts init fail, status=" + Integer.toString(i));
                AccessibilityShortcutController.this.playNotificationTone();
                return;
            }
            AccessibilityShortcutController.this.mHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityShortcutController$TtsPrompt$$ExternalSyntheticLambda1(), this));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void play() {
            if (this.mDismiss || this.mTts.speak(this.mText, 0, null, null) == 0) {
                return;
            }
            Slog.d(AccessibilityShortcutController.TAG, "Tts play fail");
            AccessibilityShortcutController.this.playNotificationTone();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void waitForTtsReady() {
            Voice voice;
            if (this.mDismiss) {
                return;
            }
            if (!this.mLanguageReady) {
                int language = this.mTts.setLanguage(Locale.getDefault());
                this.mLanguageReady = (language == -1 || language == -2) ? false : true;
            }
            if (this.mLanguageReady && (voice = this.mTts.getVoice()) != null && voice.getFeatures() != null && !voice.getFeatures().contains(TextToSpeech.Engine.KEY_FEATURE_NOT_INSTALLED)) {
                AccessibilityShortcutController.this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.internal.accessibility.AccessibilityShortcutController$TtsPrompt$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((AccessibilityShortcutController.TtsPrompt) obj).play();
                    }
                }, this));
                return;
            }
            int i = this.mRetryCount;
            if (i == 0) {
                Slog.d(AccessibilityShortcutController.TAG, "Tts not ready to speak.");
                AccessibilityShortcutController.this.playNotificationTone();
            } else {
                this.mRetryCount = i - 1;
                AccessibilityShortcutController.this.mHandler.sendMessageDelayed(PooledLambda.obtainMessage(new AccessibilityShortcutController$TtsPrompt$$ExternalSyntheticLambda1(), this), 1000L);
            }
        }
    }

    public class UserSetupCompleteObserver extends ContentObserver {
        private boolean mIsRegistered;
        private int mUserId;

        UserSetupCompleteObserver(Handler handler, int i) {
            super(handler);
            this.mIsRegistered = false;
            this.mUserId = i;
            if (isUserSetupComplete()) {
                return;
            }
            registerObserver();
        }

        private boolean isUserSetupComplete() {
            return Settings.Secure.getIntForUser(AccessibilityShortcutController.this.mContext.getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0, this.mUserId) == 1;
        }

        private void registerObserver() {
            if (this.mIsRegistered) {
                return;
            }
            AccessibilityShortcutController.this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.USER_SETUP_COMPLETE), false, this, this.mUserId);
            this.mIsRegistered = true;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (isUserSetupComplete()) {
                unregisterObserver();
                setEmptyShortcutTargetIfNeeded();
            }
        }

        private void unregisterObserver() {
            if (this.mIsRegistered) {
                AccessibilityShortcutController.this.mContext.getContentResolver().unregisterContentObserver(this);
                this.mIsRegistered = false;
            }
        }

        private void setEmptyShortcutTargetIfNeeded() {
            if (AccessibilityShortcutController.this.hasFeatureLeanback()) {
                return;
            }
            ContentResolver contentResolver = AccessibilityShortcutController.this.mContext.getContentResolver();
            if (Settings.Secure.getStringForUser(contentResolver, Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, this.mUserId) != null) {
                return;
            }
            String string = AccessibilityShortcutController.this.mContext.getString(R.string.config_defaultAccessibilityService);
            List<AccessibilityServiceInfo> enabledAccessibilityServiceList = AccessibilityShortcutController.this.mFrameworkObjectProvider.getAccessibilityManagerInstance(AccessibilityShortcutController.this.mContext).getEnabledAccessibilityServiceList(-1, this.mUserId);
            for (int size = enabledAccessibilityServiceList.size() - 1; size >= 0; size--) {
                if (TextUtils.equals(string, enabledAccessibilityServiceList.get(size).getId())) {
                    return;
                }
            }
            Settings.Secure.putStringForUser(contentResolver, Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, "", this.mUserId);
        }

        void onUserSwitched(int i) {
            if (this.mUserId == i) {
                return;
            }
            unregisterObserver();
            this.mUserId = i;
            if (isUserSetupComplete()) {
                return;
            }
            registerObserver();
        }
    }

    public static abstract class FrameworkFeatureInfo {
        private final int mLabelStringResourceId;
        private final String mSettingKey;
        private final String mSettingOffValue;
        private final String mSettingOnValue;

        FrameworkFeatureInfo(String str, String str2, String str3, int i) {
            this.mSettingKey = str;
            this.mSettingOnValue = str2;
            this.mSettingOffValue = str3;
            this.mLabelStringResourceId = i;
        }

        public String getSettingKey() {
            return this.mSettingKey;
        }

        public String getSettingOnValue() {
            return this.mSettingOnValue;
        }

        public String getSettingOffValue() {
            return this.mSettingOffValue;
        }

        public String getLabel(Context context) {
            return context.getString(this.mLabelStringResourceId);
        }
    }

    public static class ToggleableFrameworkFeatureInfo extends FrameworkFeatureInfo {
        ToggleableFrameworkFeatureInfo(String str, String str2, String str3, int i) {
            super(str, str2, str3, i);
        }
    }

    public static class LaunchableFrameworkFeatureInfo extends FrameworkFeatureInfo {
        LaunchableFrameworkFeatureInfo(int i) {
            super(null, null, null, i);
        }
    }

    public static class ExtraDimFrameworkFeatureInfo extends FrameworkFeatureInfo {
        ExtraDimFrameworkFeatureInfo(String str, String str2, String str3, int i) {
            super(str, str2, str3, i);
        }

        public boolean activateShortcut(Context context, int i) {
            if (Flags.evenDimmer() && context.getResources().getBoolean(R.bool.config_evenDimmerEnabled)) {
                launchExtraDimDialog(context);
                return true;
            }
            SettingsStringUtil.SettingStringHelper settingStringHelper = new SettingsStringUtil.SettingStringHelper(context.getContentResolver(), getSettingKey(), i);
            if (!TextUtils.equals(getSettingOnValue(), settingStringHelper.read())) {
                settingStringHelper.write(getSettingOnValue());
                return true;
            }
            settingStringHelper.write(getSettingOffValue());
            return false;
        }

        private void launchExtraDimDialog(Context context) {
            Intent intent = new Intent(AccessibilityShortcutController.ACTION_LAUNCH_REMOVE_EXTRA_DIM_DIALOG);
            intent.setFlags(268435456);
            intent.setPackage(context.getString(17039418));
            context.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
        }
    }

    public static class FrameworkObjectProvider {
        public AccessibilityManager getAccessibilityManagerInstance(Context context) {
            return AccessibilityManager.getInstance(context);
        }

        public AlertDialog.Builder getAlertDialogBuilder(Context context) {
            return new AlertDialog.Builder(context, (context.getResources().getConfiguration().uiMode & 48) == 32 ? 16974545 : 16974546);
        }

        public Toast makeToastFromText(Context context, CharSequence charSequence, int i) {
            return Toast.makeText(new ContextThemeWrapper(context, 16974123), charSequence, i);
        }

        public Context getSystemUiContext() {
            return ActivityThread.currentActivityThread().getSystemUiContext();
        }

        public TextToSpeech getTextToSpeech(Context context, TextToSpeech.OnInitListener onInitListener) {
            return new TextToSpeech(context, onInitListener);
        }

        public Ringtone getRingtone(Context context, Uri uri) {
            return RingtoneManager.getRingtone(context, uri);
        }
    }

    private int getDisplayId() {
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            return 1;
        }
        return AccessibilityUtils.isDesktopWindowing(this.mContext) ? 2 : 0;
    }
}
