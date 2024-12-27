package com.android.internal.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
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
import android.view.accessibility.Flags;
import android.widget.Toast;

import com.android.internal.R;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.function.pooled.PooledLambda;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class AccessibilityShortcutController {
    public static final String LIVE_TRANSCRIBE_CUSTOM_TILE_NAME =
            "custom(com.google.audio.hearing.visualization.accessibility.scribe/.service.ScribeTileService)";
    public static final String LIVE_TRANSCRIBE_TILE_NAME =
            "com.google.audio.hearing.visualization.accessibility.scribe/.service.ScribeTileService";
    public static final String MAGNIFICATION_CONTROLLER_NAME =
            "com.android.server.accessibility.MagnificationController";
    public static final String SOUND_NOTIFICATION_CUSTOM_TILE_NAME =
            "custom(com.google.audio.hearing.visualization.accessibility.scribe/com.google.audio.hearing.visualization.accessibility.dolphin.service.DolphinTileService)";
    public static final String SOUND_NOTIFICATION_TILE_NAME =
            "com.google.audio.hearing.visualization.accessibility.scribe/com.google.audio.hearing.visualization.accessibility.dolphin.service.DolphinTileService";
    private static final String TAG = "AccessibilityShortcutController";
    public static final String TALKBACK_SE =
            "com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService";
    private static Map<ComponentName, FrameworkFeatureInfo> sFrameworkShortcutFeaturesMap;
    private AlertDialog mAlertDialog;
    private final Context mContext;
    private boolean mEnabledOnLockScreen;
    public FrameworkObjectProvider mFrameworkObjectProvider = new FrameworkObjectProvider();
    private final Handler mHandler;
    private boolean mIsShortcutEnabled;
    private int mUserId;
    private final UserSetupCompleteObserver mUserSetupCompleteObserver;
    public static final ComponentName COLOR_INVERSION_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "ColorInversion");
    public static final ComponentName DALTONIZER_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "Daltonizer");
    public static final ComponentName MAGNIFICATION_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "Magnification");
    public static final ComponentName ONE_HANDED_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "OneHandedMode");
    public static final ComponentName REDUCE_BRIGHT_COLORS_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "ReduceBrightColors");
    public static final ComponentName FONT_SIZE_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "FontSize");
    public static final ComponentName HIGH_CONTRAST_FONTS_COMPONENT_NAME =
            new ComponentName(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.HighContrastFontsShortcut");
    public static final ComponentName COLOR_LENS_COMPONENT_NAME =
            new ComponentName(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.ColorLensShortcut");
    public static final ComponentName SOUND_NOTIFICATION_COMPONENT_NAME =
            new ComponentName(
                    A11yLogger.PACKAGE_NAME_LIVE_TRANSCRIBE,
                    "com.google.audio.hearing.visualization.accessibility.dolphin.ui.visualizer.TimelineActivity");
    public static final ComponentName LIVE_TRANSCRIBE_COMPONENT_NAME =
            new ComponentName(
                    A11yLogger.PACKAGE_NAME_LIVE_TRANSCRIBE,
                    "com.google.audio.hearing.visualization.accessibility.scribe.MainActivity");
    public static final ComponentName ACCESSIBILITY_BUTTON_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "AccessibilityButton");
    public static final ComponentName ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "HearingAids");
    public static final ComponentName ACCESSIBILITY_HEARING_AIDS_TILE_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "HearingDevicesTile");
    public static final ComponentName COLOR_INVERSION_TILE_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "ColorInversionTile");
    public static final ComponentName DALTONIZER_TILE_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "ColorCorrectionTile");
    public static final ComponentName ONE_HANDED_TILE_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "OneHandedModeTile");
    public static final ComponentName REDUCE_BRIGHT_COLORS_TILE_SERVICE_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "ReduceBrightColorsTile");
    public static final ComponentName FONT_SIZE_TILE_COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "FontSizeTile");
    public static final ComponentName HIGH_CONTRAST_FONTS_TILE_COMPONENT_NAME =
            new ComponentName(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.HighContrastFontsShortcutTile");
    public static final ComponentName COLOR_LENS_TILE_COMPONENT_NAME =
            new ComponentName(
                    "com.android.settings",
                    "com.samsung.android.settings.accessibility.shortcut.ColorLensShortcutTile");
    private static final AudioAttributes VIBRATION_ATTRIBUTES =
            new AudioAttributes.Builder().setContentType(4).setUsage(11).build();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DialogStatus {
        public static final int NOT_SHOWN = 0;
        public static final int SHOWN = 1;
    }

    public static Map<ComponentName, FrameworkFeatureInfo> getFrameworkShortcutFeaturesMap() {
        if (sFrameworkShortcutFeaturesMap == null) {
            Map<ComponentName, FrameworkFeatureInfo> featuresMap = new ArrayMap<>(4);
            featuresMap.put(
                    COLOR_INVERSION_COMPONENT_NAME,
                    new ToggleableFrameworkFeatureInfo(
                            Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED,
                            "1",
                            "0",
                            R.string.color_inversion_feature_name));
            featuresMap.put(
                    DALTONIZER_COMPONENT_NAME,
                    new ToggleableFrameworkFeatureInfo(
                            Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED,
                            "1",
                            "0",
                            R.string.color_correction_feature_name));
            if (RoSystemProperties.SUPPORT_ONE_HANDED_MODE) {
                featuresMap.put(
                        ONE_HANDED_COMPONENT_NAME,
                        new ToggleableFrameworkFeatureInfo(
                                Settings.Secure.ONE_HANDED_MODE_ACTIVATED,
                                "1",
                                "0",
                                R.string.one_handed_mode_feature_name));
            }
            featuresMap.put(
                    REDUCE_BRIGHT_COLORS_COMPONENT_NAME,
                    new ToggleableFrameworkFeatureInfo(
                            Settings.Secure.REDUCE_BRIGHT_COLORS_ACTIVATED,
                            "1",
                            "0",
                            R.string.reduce_bright_colors_feature_name));
            featuresMap.put(
                    ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME,
                    new LaunchableFrameworkFeatureInfo(R.string.hearing_aids_feature_name));
            sFrameworkShortcutFeaturesMap = Collections.unmodifiableMap(featuresMap);
        }
        return sFrameworkShortcutFeaturesMap;
    }

    public AccessibilityShortcutController(Context context, Handler handler, int initialUserId) {
        this.mContext = context;
        this.mHandler = handler;
        this.mUserId = initialUserId;
        this.mUserSetupCompleteObserver = new UserSetupCompleteObserver(handler, initialUserId);
        ContentObserver co =
                new ContentObserver(
                        handler) { // from class:
                                   // com.android.internal.accessibility.AccessibilityShortcutController.1
                    @Override // android.database.ContentObserver
                    public void onChange(
                            boolean selfChange, Collection<Uri> uris, int flags, int userId) {
                        if (userId == AccessibilityShortcutController.this.mUserId) {
                            AccessibilityShortcutController.this.onSettingsChanged();
                        }
                    }
                };
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor(
                                Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE),
                        false,
                        co,
                        -1);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor(
                                Settings.Secure.ACCESSIBILITY_SHORTCUT_ON_LOCK_SCREEN),
                        false,
                        co,
                        -1);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor(
                                Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN),
                        false,
                        co,
                        -1);
        setCurrentUser(this.mUserId);
    }

    public void setCurrentUser(int currentUserId) {
        this.mUserId = currentUserId;
        onSettingsChanged();
        this.mUserSetupCompleteObserver.onUserSwitched(currentUserId);
    }

    public boolean isAccessibilityShortcutAvailable(boolean phoneLocked) {
        return this.mIsShortcutEnabled && (!phoneLocked || this.mEnabledOnLockScreen);
    }

    public void onSettingsChanged() {
        boolean hasShortcutTarget = hasShortcutTarget();
        ContentResolver cr = this.mContext.getContentResolver();
        int dialogAlreadyShown =
                Settings.Secure.getIntForUser(
                        cr, Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 0, this.mUserId);
        this.mEnabledOnLockScreen =
                Settings.Secure.getIntForUser(
                                cr,
                                Settings.Secure.ACCESSIBILITY_SHORTCUT_ON_LOCK_SCREEN,
                                dialogAlreadyShown,
                                this.mUserId)
                        == 1;
        this.mIsShortcutEnabled = hasShortcutTarget;
    }

    public void performAccessibilityShortcut() {
        Slog.d(TAG, "Accessibility shortcut activated");
        ContentResolver cr = this.mContext.getContentResolver();
        int userId = ActivityManager.getCurrentUser();
        String shortcutTargetService =
                Settings.Secure.getStringForUser(
                        cr, Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, userId);
        if (shortcutTargetService == null && AccessibilityUtils.isSetupWizard(this.mContext)) {
            Slog.d(TAG, "Accessibility shortcutTargetService == null");
            A11yLogger.insertLog(
                    this.mContext, A11yLogger.SA_ACCESSIBILITY_SETUPWIZARD_VOLUME_UP_DOWN);
            Settings.Secure.putStringForUser(
                    cr, Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE, TALKBACK_SE, userId);
            shortcutTargetService = TALKBACK_SE;
        }
        if (TextUtils.isEmpty(shortcutTargetService)) {
            Slog.d(TAG, "Accessibility shortcut target isEmpty");
            return;
        }
        Vibrator vibrator = (Vibrator) this.mContext.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (ShortcutUtils.isSupportDCMotorHapticFeedback(vibrator)) {
                ShortcutUtils.vibrateDCMotorHapticFeedback(this.mContext, vibrator);
            } else {
                long[] vibePattern =
                        ArrayUtils.convertToLongArray(
                                this.mContext
                                        .getResources()
                                        .getIntArray(R.array.config_longPressVibePattern));
                vibrator.vibrate(vibePattern, -1, VIBRATION_ATTRIBUTES);
            }
        }
        if (shouldShowDialog()) {
            this.mAlertDialog = createShortcutWarningDialog(userId);
            if (this.mAlertDialog == null) {
                return;
            }
            if (!performTtsPrompt(this.mAlertDialog)) {
                playNotificationTone();
            }
            Window w = this.mAlertDialog.getWindow();
            WindowManager.LayoutParams attr = w.getAttributes();
            attr.type = 2009;
            w.setAttributes(attr);
            this.mAlertDialog.show();
            Settings.Secure.putIntForUser(
                    cr, Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 1, userId);
            return;
        }
        if (Flags.restoreA11yShortcutTargetService()) {
            enableDefaultHardwareShortcut(userId);
        }
        if (shortcutTargetService != null
                && shortcutTargetService.contains(
                        "com.samsung.accessibility/com.samsung.accessibility.shortcut.InteractionControlShortcut")
                && AccessibilityUtils.isAccessControlEnabled(this.mContext)) {
            Slog.d(TAG, "Interaction Control is activated");
            AccessibilityUtils.turnOffAccessControl(this.mContext);
            return;
        }
        if (shortcutTargetService != null
                && shortcutTargetService.contains(
                        "com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService")
                && AccessibilityUtils.isAccessibilityServiceEnabled(
                        this.mContext,
                        "com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService")) {
            Slog.d(TAG, "Universal switch is activated");
            AccessibilityUtils.setAccessibilityServiceState(
                    this.mContext,
                    ComponentName.unflattenFromString(
                            "com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService"),
                    false,
                    userId);
            return;
        }
        if (Flags.restoreA11yShortcutTargetService()) {
            enableDefaultHardwareShortcut(userId);
        }
        if (this.mAlertDialog != null) {
            this.mAlertDialog.dismiss();
            this.mAlertDialog = null;
        }
        showToast();
        this.mFrameworkObjectProvider
                .getAccessibilityManagerInstance(this.mContext)
                .performAccessibilityShortcut();
    }

    private boolean shouldShowDialog() {
        if (hasFeatureLeanback()) {
            return false;
        }
        ContentResolver cr = this.mContext.getContentResolver();
        int userId = ActivityManager.getCurrentUser();
        int dialogAlreadyShown =
                Settings.Secure.getIntForUser(
                        cr, Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN, 0, userId);
        return dialogAlreadyShown == 0;
    }

    private void showToast() {
        int i;
        AccessibilityServiceInfo serviceInfo = getInfoForTargetService();
        if (serviceInfo == null) {
            return;
        }
        String serviceName = getShortcutFeatureDescription(false);
        if (serviceName == null) {
            return;
        }
        boolean requestA11yButton = (serviceInfo.flags & 256) != 0;
        boolean isServiceEnabled = isServiceEnabled(serviceInfo);
        if (serviceInfo.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion > 29
                && requestA11yButton
                && isServiceEnabled) {
            return;
        }
        Context context = this.mContext;
        if (isServiceEnabled) {
            i = R.string.accessibility_shortcut_disabling_service;
        } else {
            i = R.string.accessibility_shortcut_enabling_service;
        }
        String toastMessageFormatString = context.getString(i);
        String toastMessage = String.format(toastMessageFormatString, serviceName);
        Toast warningToast =
                this.mFrameworkObjectProvider.makeToastFromText(this.mContext, toastMessage, 1);
        warningToast.show();
    }

    private AlertDialog createShortcutWarningDialog(final int userId) {
        List<AccessibilityTarget> targets = AccessibilityTargetHelper.getTargets(this.mContext, 2);
        if (targets.size() == 0) {
            return null;
        }
        final AccessibilityManager am =
                this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext);
        AlertDialog alertDialog =
                this.mFrameworkObjectProvider
                        .getAlertDialogBuilder(this.mFrameworkObjectProvider.getSystemUiContext())
                        .setTitle(getShortcutWarningTitle(targets))
                        .setMessage(getShortcutWarningMessage(targets))
                        .setCancelable(false)
                        .setPositiveButton(
                                R.string.accessibility_shortcut_use,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        AccessibilityShortcutController.this
                                                .lambda$createShortcutWarningDialog$0(
                                                        userId, dialogInterface, i);
                                    }
                                })
                        .setNegativeButton(
                                R.string.accessibility_shortcut_dont_use,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda2
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        AccessibilityShortcutController.this
                                                .lambda$createShortcutWarningDialog$1(
                                                        userId, am, dialogInterface, i);
                                    }
                                })
                        .setOnCancelListener(
                                new DialogInterface
                                        .OnCancelListener() { // from class:
                                                              // com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda3
                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        AccessibilityShortcutController.this
                                                .lambda$createShortcutWarningDialog$2(
                                                        userId, dialogInterface);
                                    }
                                })
                        .create();
        return alertDialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$0(
            int userId, DialogInterface d, int which) {
        enableDefaultHardwareShortcut(userId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$1(
            int userId, AccessibilityManager am, DialogInterface d, int which) {
        Set<String> targetServices =
                ShortcutUtils.getShortcutTargetsFromSettings(this.mContext, 2, userId);
        if (Flags.migrateEnableShortcuts()) {
            am.enableShortcutsForTargets(false, 2, targetServices, userId);
        } else {
            Settings.Secure.putStringForUser(
                    this.mContext.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE,
                    "",
                    userId);
            ShortcutUtils.updateInvisibleToggleAccessibilityServiceEnableState(
                    this.mContext, targetServices, userId);
        }
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN,
                0,
                userId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$2(
            int userId, DialogInterface d) {
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN,
                0,
                userId);
    }

    private String getShortcutWarningTitle(List<AccessibilityTarget> targets) {
        if (targets.size() == 1 && TALKBACK_SE.equals(targets.get(0).getId())) {
            return this.mContext.getString(
                    R.string.accessibility_shortcut_warning_title_samsung_talkback,
                    targets.get(0).getLabel());
        }
        return this.mContext.getString(R.string.accessibility_shortcut_warning_title_samsung);
    }

    private String getShortcutWarningMessage(List<AccessibilityTarget> targets) {
        if (targets.size() == 1) {
            return this.mContext.getString(
                    R.string.accessibility_shortcut_single_service_warning_samsung,
                    targets.get(0).getLabel());
        }
        StringBuilder sb = new StringBuilder();
        for (AccessibilityTarget target : targets) {
            sb.append(
                    this.mContext.getString(
                            R.string.accessibility_shortcut_multiple_service_list,
                            target.getLabel()));
        }
        return this.mContext.getString(
                        R.string.accessibility_shortcut_multiple_service_warning_samsung)
                + "\n"
                + sb.toString()
                + "\n"
                + this.mContext.getString(R.string.accessibility_select_different_function);
    }

    private AccessibilityServiceInfo getInfoForTargetService() {
        ComponentName targetComponentName = getShortcutTargetComponentName();
        if (targetComponentName == null) {
            return null;
        }
        AccessibilityManager accessibilityManager =
                this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext);
        return accessibilityManager.getInstalledServiceInfoWithComponentName(targetComponentName);
    }

    private String getShortcutFeatureDescription(boolean includeSummary) {
        ComponentName targetComponentName = getShortcutTargetComponentName();
        if (targetComponentName == null) {
            return null;
        }
        FrameworkFeatureInfo frameworkFeatureInfo =
                getFrameworkShortcutFeaturesMap().get(targetComponentName);
        if (frameworkFeatureInfo != null) {
            return frameworkFeatureInfo.getLabel(this.mContext);
        }
        AccessibilityServiceInfo serviceInfo =
                this.mFrameworkObjectProvider
                        .getAccessibilityManagerInstance(this.mContext)
                        .getInstalledServiceInfoWithComponentName(targetComponentName);
        if (serviceInfo == null) {
            return null;
        }
        PackageManager pm = this.mContext.getPackageManager();
        String label = serviceInfo.getResolveInfo().loadLabel(pm).toString();
        CharSequence summary = serviceInfo.loadSummary(pm);
        if (!includeSummary || TextUtils.isEmpty(summary)) {
            return label;
        }
        return String.format("%s\n%s", label, summary);
    }

    private boolean isServiceEnabled(AccessibilityServiceInfo serviceInfo) {
        AccessibilityManager accessibilityManager =
                this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext);
        return accessibilityManager.getEnabledAccessibilityServiceList(-1).contains(serviceInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasFeatureLeanback() {
        return this.mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playNotificationTone() {
        int audioAttributesUsage;
        if (hasFeatureLeanback()) {
            audioAttributesUsage = 11;
        } else {
            audioAttributesUsage = 10;
        }
        Uri ringtoneUri =
                Uri.parse(
                        "file://"
                                + this.mContext.getString(
                                        R.string.config_defaultAccessibilityNotificationSound));
        Ringtone tone = this.mFrameworkObjectProvider.getRingtone(this.mContext, ringtoneUri);
        if (tone == null) {
            tone =
                    this.mFrameworkObjectProvider.getRingtone(
                            this.mContext, Settings.System.DEFAULT_NOTIFICATION_URI);
        }
        if (tone != null) {
            tone.setAudioAttributes(
                    new AudioAttributes.Builder().setUsage(audioAttributesUsage).build());
            tone.play();
        }
    }

    private void enableDefaultHardwareShortcut(int userId) {
        AccessibilityManager accessibilityManager =
                this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext);
        String targetServices =
                Settings.Secure.getStringForUser(
                        this.mContext.getContentResolver(),
                        Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE,
                        userId);
        if (targetServices != null) {
            return;
        }
        String defaultService =
                this.mContext.getString(R.string.config_defaultAccessibilityService);
        ComponentName defaultServiceComponent =
                TextUtils.isEmpty(defaultService)
                        ? null
                        : ComponentName.unflattenFromString(defaultService);
        if (defaultServiceComponent == null) {
            return;
        }
        if (Flags.migrateEnableShortcuts()) {
            accessibilityManager.enableShortcutsForTargets(
                    true, 2, Set.of(defaultServiceComponent.flattenToString()), userId);
        } else {
            Settings.Secure.putStringForUser(
                    this.mContext.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE,
                    defaultServiceComponent.flattenToString(),
                    userId);
        }
    }

    private boolean performTtsPrompt(AlertDialog alertDialog) {
        String serviceName = getShortcutFeatureDescription(false);
        AccessibilityServiceInfo serviceInfo = getInfoForTargetService();
        if (TextUtils.isEmpty(serviceName)
                || serviceInfo == null
                || (serviceInfo.flags & 1024) == 0) {
            return false;
        }
        final TtsPrompt tts = new TtsPrompt(serviceName);
        alertDialog.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.android.internal.accessibility.AccessibilityShortcutController$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        AccessibilityShortcutController.TtsPrompt.this.dismiss();
                    }
                });
        return true;
    }

    private boolean hasShortcutTarget() {
        String shortcutTargets =
                Settings.Secure.getStringForUser(
                        this.mContext.getContentResolver(),
                        Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE,
                        this.mUserId);
        if (shortcutTargets == null && AccessibilityUtils.isSetupWizard(this.mContext)) {
            shortcutTargets = TALKBACK_SE;
        }
        return !TextUtils.isEmpty(shortcutTargets);
    }

    private ComponentName getShortcutTargetComponentName() {
        List<String> shortcutTargets =
                this.mFrameworkObjectProvider
                        .getAccessibilityManagerInstance(this.mContext)
                        .getAccessibilityShortcutTargets(2);
        if (shortcutTargets.size() != 1) {
            return null;
        }
        return ComponentName.unflattenFromString(shortcutTargets.get(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    class TtsPrompt implements TextToSpeech.OnInitListener {
        private static final int RETRY_MILLIS = 1000;
        private boolean mDismiss;
        private final CharSequence mText;
        private TextToSpeech mTts;
        private int mRetryCount = 3;
        private boolean mLanguageReady = false;

        TtsPrompt(String serviceName) {
            this.mText =
                    AccessibilityShortcutController.this.mContext.getString(
                            R.string.accessibility_shortcut_spoken_feedback, serviceName);
            this.mTts =
                    AccessibilityShortcutController.this.mFrameworkObjectProvider.getTextToSpeech(
                            AccessibilityShortcutController.this.mContext, this);
        }

        public void dismiss() {
            this.mDismiss = true;
            AccessibilityShortcutController.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda2(),
                            this.mTts));
        }

        @Override // android.speech.tts.TextToSpeech.OnInitListener
        public void onInit(int status) {
            if (status != 0) {
                Slog.d(
                        AccessibilityShortcutController.TAG,
                        "Tts init fail, status=" + Integer.toString(status));
                AccessibilityShortcutController.this.playNotificationTone();
            } else {
                AccessibilityShortcutController.this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new AccessibilityShortcutController$TtsPrompt$$ExternalSyntheticLambda1(),
                                this));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void play() {
            if (this.mDismiss) {
                return;
            }
            int status = this.mTts.speak(this.mText, 0, null, null);
            if (status != 0) {
                Slog.d(AccessibilityShortcutController.TAG, "Tts play fail");
                AccessibilityShortcutController.this.playNotificationTone();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void waitForTtsReady() {
            if (this.mDismiss) {
                return;
            }
            boolean voiceDataInstalled = false;
            if (!this.mLanguageReady) {
                int status = this.mTts.setLanguage(Locale.getDefault());
                this.mLanguageReady = (status == -1 || status == -2) ? false : true;
            }
            if (this.mLanguageReady) {
                Voice voice = this.mTts.getVoice();
                if (voice != null
                        && voice.getFeatures() != null
                        && !voice.getFeatures()
                                .contains(TextToSpeech.Engine.KEY_FEATURE_NOT_INSTALLED)) {
                    voiceDataInstalled = true;
                }
                if (voiceDataInstalled) {
                    AccessibilityShortcutController.this.mHandler.sendMessage(
                            PooledLambda.obtainMessage(
                                    new Consumer() { // from class:
                                                     // com.android.internal.accessibility.AccessibilityShortcutController$TtsPrompt$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            ((AccessibilityShortcutController.TtsPrompt) obj)
                                                    .play();
                                        }
                                    },
                                    this));
                    return;
                }
            }
            if (this.mRetryCount == 0) {
                Slog.d(AccessibilityShortcutController.TAG, "Tts not ready to speak.");
                AccessibilityShortcutController.this.playNotificationTone();
            } else {
                this.mRetryCount--;
                AccessibilityShortcutController.this.mHandler.sendMessageDelayed(
                        PooledLambda.obtainMessage(
                                new AccessibilityShortcutController$TtsPrompt$$ExternalSyntheticLambda1(),
                                this),
                        1000L);
            }
        }
    }

    private class UserSetupCompleteObserver extends ContentObserver {
        private boolean mIsRegistered;
        private int mUserId;

        UserSetupCompleteObserver(Handler handler, int userId) {
            super(handler);
            this.mIsRegistered = false;
            this.mUserId = userId;
            if (!isUserSetupComplete()) {
                registerObserver();
            }
        }

        private boolean isUserSetupComplete() {
            return Settings.Secure.getIntForUser(
                            AccessibilityShortcutController.this.mContext.getContentResolver(),
                            Settings.Secure.USER_SETUP_COMPLETE,
                            0,
                            this.mUserId)
                    == 1;
        }

        private void registerObserver() {
            if (this.mIsRegistered) {
                return;
            }
            AccessibilityShortcutController.this
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor(Settings.Secure.USER_SETUP_COMPLETE),
                            false,
                            this,
                            this.mUserId);
            this.mIsRegistered = true;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            if (isUserSetupComplete()) {
                unregisterObserver();
                setEmptyShortcutTargetIfNeeded();
            }
        }

        private void unregisterObserver() {
            if (!this.mIsRegistered) {
                return;
            }
            AccessibilityShortcutController.this
                    .mContext
                    .getContentResolver()
                    .unregisterContentObserver(this);
            this.mIsRegistered = false;
        }

        private void setEmptyShortcutTargetIfNeeded() {
            if (AccessibilityShortcutController.this.hasFeatureLeanback()) {
                return;
            }
            ContentResolver contentResolver =
                    AccessibilityShortcutController.this.mContext.getContentResolver();
            String shortcutTargets =
                    Settings.Secure.getStringForUser(
                            contentResolver,
                            Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE,
                            this.mUserId);
            if (shortcutTargets != null) {
                return;
            }
            String defaultShortcutTarget =
                    AccessibilityShortcutController.this.mContext.getString(
                            R.string.config_defaultAccessibilityService);
            List<AccessibilityServiceInfo> enabledServices =
                    AccessibilityShortcutController.this
                            .mFrameworkObjectProvider
                            .getAccessibilityManagerInstance(
                                    AccessibilityShortcutController.this.mContext)
                            .getEnabledAccessibilityServiceList(-1);
            for (int i = enabledServices.size() - 1; i >= 0; i--) {
                if (TextUtils.equals(defaultShortcutTarget, enabledServices.get(i).getId())) {
                    return;
                }
            }
            Settings.Secure.putStringForUser(
                    contentResolver,
                    Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE,
                    "",
                    this.mUserId);
        }

        void onUserSwitched(int userId) {
            if (this.mUserId == userId) {
                return;
            }
            unregisterObserver();
            this.mUserId = userId;
            if (!isUserSetupComplete()) {
                registerObserver();
            }
        }
    }

    public abstract static class FrameworkFeatureInfo {
        private final int mLabelStringResourceId;
        private final String mSettingKey;
        private final String mSettingOffValue;
        private final String mSettingOnValue;

        FrameworkFeatureInfo(
                String settingKey,
                String settingOnValue,
                String settingOffValue,
                int labelStringResourceId) {
            this.mSettingKey = settingKey;
            this.mSettingOnValue = settingOnValue;
            this.mSettingOffValue = settingOffValue;
            this.mLabelStringResourceId = labelStringResourceId;
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
        ToggleableFrameworkFeatureInfo(
                String settingKey,
                String settingOnValue,
                String settingOffValue,
                int labelStringResourceId) {
            super(settingKey, settingOnValue, settingOffValue, labelStringResourceId);
        }
    }

    public static class LaunchableFrameworkFeatureInfo extends FrameworkFeatureInfo {
        LaunchableFrameworkFeatureInfo(int labelStringResourceId) {
            super(null, null, null, labelStringResourceId);
        }
    }

    public static class FrameworkObjectProvider {
        public AccessibilityManager getAccessibilityManagerInstance(Context context) {
            return AccessibilityManager.getInstance(context);
        }

        public AlertDialog.Builder getAlertDialogBuilder(Context context) {
            boolean inNightMode = (context.getResources().getConfiguration().uiMode & 48) == 32;
            int themeId = inNightMode ? 16974545 : 16974546;
            return new AlertDialog.Builder(context, themeId);
        }

        public Toast makeToastFromText(Context context, CharSequence charSequence, int duration) {
            return Toast.makeText(
                    new ContextThemeWrapper(context, 16974123), charSequence, duration);
        }

        public Context getSystemUiContext() {
            return ActivityThread.currentActivityThread().getSystemUiContext();
        }

        public TextToSpeech getTextToSpeech(Context ctx, TextToSpeech.OnInitListener listener) {
            return new TextToSpeech(ctx, listener);
        }

        public Ringtone getRingtone(Context ctx, Uri uri) {
            return RingtoneManager.getRingtone(ctx, uri);
        }
    }
}
