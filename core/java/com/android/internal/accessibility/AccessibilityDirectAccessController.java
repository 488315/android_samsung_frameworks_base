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
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
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
import android.view.accessibility.AccessibilityManager;
import com.android.internal.R;
import com.android.internal.accessibility.AccessibilityDirectAccessController;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class AccessibilityDirectAccessController {
    private static final String TAG = "AccessibilityDirectAccessController";
    private static final AudioAttributes VIBRATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(11).build();
    private static Map<ComponentName, ToggleableFrameworkFeatureInfo> sFrameworkShortcutFeaturesMap;
    private AlertDialog mAlertDialog;
    private final Context mContext;
    public FrameworkObjectProvider mFrameworkObjectProvider = new FrameworkObjectProvider();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public static Map<ComponentName, ToggleableFrameworkFeatureInfo> getFrameworkShortcutFeaturesMap() {
        if (sFrameworkShortcutFeaturesMap == null) {
            Map<ComponentName, ToggleableFrameworkFeatureInfo> featuresMap = new ArrayMap<>(2);
            featuresMap.put(AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME, new ToggleableFrameworkFeatureInfo(Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, "1", "0", R.string.color_inversion_feature_name));
            featuresMap.put(AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME, new ToggleableFrameworkFeatureInfo(Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED, "1", "0", R.string.color_correction_feature_name));
            sFrameworkShortcutFeaturesMap = Collections.unmodifiableMap(featuresMap);
        }
        return sFrameworkShortcutFeaturesMap;
    }

    public AccessibilityDirectAccessController(Context context) {
        this.mContext = context;
    }

    public void performAccessibilityDirectAccess() {
        Slog.d(TAG, "Accessibility direct access activated");
        final ContentResolver cr = this.mContext.getContentResolver();
        final int userId = ActivityManager.getCurrentUser();
        int dialogAlreadyShown = Settings.Secure.getIntForUser(cr, "accessibility_direct_access_dialog_shown", 0, userId);
        String directAccessTargetService = Settings.Secure.getStringForUser(cr, Settings.Secure.ACCESSIBILITY_DIRECT_ACCESS_TARGET_SERVICE, userId);
        if (directAccessTargetService == null && AccessibilityUtils.isSetupWizard(this.mContext)) {
            Slog.d(TAG, "Accessibility directAccessTargetService == null");
            Settings.Secure.putStringForUser(cr, Settings.Secure.ACCESSIBILITY_DIRECT_ACCESS_TARGET_SERVICE, AccessibilityShortcutController.TALKBACK_SE, userId);
            directAccessTargetService = AccessibilityShortcutController.TALKBACK_SE;
        }
        if (TextUtils.isEmpty(directAccessTargetService) || (isKeyguardLocked() && dialogAlreadyShown == 0)) {
            Slog.d(TAG, "Accessibility direct access isEmpty");
            return;
        }
        Vibrator vibrator = (Vibrator) this.mContext.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (ShortcutUtils.isSupportDCMotorHapticFeedback(vibrator)) {
                ShortcutUtils.vibrateDCMotorHapticFeedback(this.mContext, vibrator);
            } else {
                long[] vibePattern = ArrayUtils.convertToLongArray(this.mContext.getResources().getIntArray(R.array.config_longPressVibePattern));
                vibrator.vibrate(vibePattern, -1, VIBRATION_ATTRIBUTES);
            }
        }
        if (dialogAlreadyShown == 0) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.internal.accessibility.AccessibilityDirectAccessController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityDirectAccessController.this.lambda$performAccessibilityDirectAccess$0(userId, cr);
                }
            }, 0L);
            return;
        }
        if (directAccessTargetService != null && directAccessTargetService.contains("com.samsung.accessibility/com.samsung.accessibility.shortcut.InteractionControlShortcut") && AccessibilityUtils.isAccessControlEnabled(this.mContext)) {
            Slog.d(TAG, "Interaction Control is activated");
            AccessibilityUtils.turnOffAccessControl(this.mContext);
        } else if (directAccessTargetService != null && directAccessTargetService.contains("com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService") && AccessibilityUtils.isAccessibilityServiceEnabled(this.mContext, "com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService")) {
            Slog.d(TAG, "Universal switch is activated");
            AccessibilityUtils.setAccessibilityServiceState(this.mContext, ComponentName.unflattenFromString("com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService"), false, userId);
        } else {
            if (this.mAlertDialog != null) {
                this.mAlertDialog.dismiss();
                this.mAlertDialog = null;
            }
            this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).performAccessibilityDirectAccess();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performAccessibilityDirectAccess$0(int userId, ContentResolver cr) {
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
        Settings.Secure.putIntForUser(cr, "accessibility_direct_access_dialog_shown", 1, userId);
    }

    private AlertDialog createShortcutWarningDialog(final int userId) {
        List<AccessibilityTarget> targets = AccessibilityTargetHelper.getTargets(this.mContext, 512);
        if (targets.size() == 0) {
            return null;
        }
        AlertDialog alertDialog = this.mFrameworkObjectProvider.getAlertDialogBuilder(this.mFrameworkObjectProvider.getSystemUiContext()).setTitle(getShortcutWarningTitle(targets)).setMessage(getShortcutWarningMessage(targets)).setCancelable(false).setPositiveButton(R.string.accessibility_shortcut_use, (DialogInterface.OnClickListener) null).setNegativeButton(R.string.accessibility_shortcut_dont_use, new DialogInterface.OnClickListener() { // from class: com.android.internal.accessibility.AccessibilityDirectAccessController$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                AccessibilityDirectAccessController.this.lambda$createShortcutWarningDialog$1(userId, dialogInterface, i);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.internal.accessibility.AccessibilityDirectAccessController$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                AccessibilityDirectAccessController.this.lambda$createShortcutWarningDialog$2(userId, dialogInterface);
            }
        }).create();
        return alertDialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$1(int userId, DialogInterface d, int which) {
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), Settings.Secure.ACCESSIBILITY_DIRECT_ACCESS_TARGET_SERVICE, "", userId);
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_direct_access_dialog_shown", 0, userId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$2(int userId, DialogInterface d) {
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_direct_access_dialog_shown", 0, userId);
    }

    private String getShortcutWarningTitle(List<AccessibilityTarget> targets) {
        if (targets.size() == 1 && AccessibilityShortcutController.TALKBACK_SE.equals(targets.get(0).getId())) {
            if (AccessibilityUtils.isSideKeySupported()) {
                return this.mContext.getString(R.string.accessibility_direct_access_warning_title_samsung_side_key_talkback, targets.get(0).getLabel());
            }
            return this.mContext.getString(R.string.accessibility_direct_access_warning_title_samsung_talkback, targets.get(0).getLabel());
        }
        if (AccessibilityUtils.isSideKeySupported()) {
            return this.mContext.getString(R.string.accessibility_direct_access_warning_title_samsung_side_key);
        }
        return this.mContext.getString(R.string.accessibility_direct_access_warning_title_samsung);
    }

    private String getShortcutWarningMessage(List<AccessibilityTarget> targets) {
        if (targets.size() == 1) {
            if (AccessibilityUtils.isSideKeySupported()) {
                return this.mContext.getString(R.string.accessibility_direct_access_single_service_warning_samsung_side_key, targets.get(0).getLabel()) + " " + this.mContext.getString(R.string.accessibility_select_different_function);
            }
            return this.mContext.getString(R.string.accessibility_direct_access_single_service_warning_samsung, targets.get(0).getLabel()) + " " + this.mContext.getString(R.string.accessibility_select_different_function);
        }
        StringBuilder sb = new StringBuilder();
        for (AccessibilityTarget target : targets) {
            sb.append(this.mContext.getString(R.string.accessibility_shortcut_multiple_service_list, target.getLabel()));
        }
        if (AccessibilityUtils.isSideKeySupported()) {
            return this.mContext.getString(R.string.accessibility_direct_access_multiple_service_warning_samsung_side_key) + "\n" + sb.toString() + "\n" + this.mContext.getString(R.string.accessibility_select_different_function);
        }
        return this.mContext.getString(R.string.accessibility_direct_access_multiple_service_warning_samsung) + "\n" + sb.toString() + "\n" + this.mContext.getString(R.string.accessibility_select_different_function);
    }

    private AccessibilityServiceInfo getInfoForTargetService() {
        ComponentName targetComponentName = getShortcutTargetComponentName();
        if (targetComponentName == null) {
            return null;
        }
        AccessibilityManager accessibilityManager = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext);
        return accessibilityManager.getInstalledServiceInfoWithComponentName(targetComponentName);
    }

    private String getShortcutFeatureDescription() {
        ComponentName targetComponentName = getShortcutTargetComponentName();
        if (targetComponentName == null) {
            return null;
        }
        ToggleableFrameworkFeatureInfo frameworkFeatureInfo = getFrameworkShortcutFeaturesMap().get(targetComponentName);
        if (frameworkFeatureInfo != null) {
            return frameworkFeatureInfo.getLabel(this.mContext);
        }
        AccessibilityServiceInfo serviceInfo = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getInstalledServiceInfoWithComponentName(targetComponentName);
        if (serviceInfo == null) {
            return null;
        }
        PackageManager pm = this.mContext.getPackageManager();
        String label = serviceInfo.getResolveInfo().loadLabel(pm).toString();
        CharSequence summary = serviceInfo.loadSummary(pm);
        if (TextUtils.isEmpty(summary)) {
            return label;
        }
        return String.format("%s\n%s", label, summary);
    }

    private boolean hasFeatureLeanback() {
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
        Ringtone tone = this.mFrameworkObjectProvider.getRingtone(this.mContext, Settings.System.DEFAULT_NOTIFICATION_URI);
        if (tone != null) {
            tone.setAudioAttributes(new AudioAttributes.Builder().setUsage(audioAttributesUsage).build());
            tone.play();
        }
    }

    private boolean performTtsPrompt(AlertDialog alertDialog) {
        String serviceName = getShortcutFeatureDescription();
        AccessibilityServiceInfo serviceInfo = getInfoForTargetService();
        if (TextUtils.isEmpty(serviceName) || serviceInfo == null || (serviceInfo.flags & 1024) == 0) {
            return false;
        }
        final TtsPrompt tts = new TtsPrompt(serviceName);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.internal.accessibility.AccessibilityDirectAccessController$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AccessibilityDirectAccessController.TtsPrompt.this.dismiss();
            }
        });
        return true;
    }

    private ComponentName getShortcutTargetComponentName() {
        List<String> shortcutTargets = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getAccessibilityShortcutTargets(512);
        if (shortcutTargets.size() != 1) {
            return null;
        }
        Slog.d(TAG, "shortcutTargets.get(0) : " + shortcutTargets.get(0));
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
            if (AccessibilityUtils.isSideKeySupported()) {
                this.mText = AccessibilityDirectAccessController.this.mContext.getString(R.string.accessibility_direct_access_spoken_feedback_side_key, serviceName);
            } else {
                this.mText = AccessibilityDirectAccessController.this.mContext.getString(R.string.accessibility_direct_access_spoken_feedback, serviceName);
            }
            this.mTts = AccessibilityDirectAccessController.this.mFrameworkObjectProvider.getTextToSpeech(AccessibilityDirectAccessController.this.mContext, this);
        }

        public void dismiss() {
            this.mDismiss = true;
            AccessibilityDirectAccessController.this.mHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda2(), this.mTts));
        }

        @Override // android.speech.tts.TextToSpeech.OnInitListener
        public void onInit(int status) {
            if (status != 0) {
                Slog.d(AccessibilityDirectAccessController.TAG, "Tts init fail, status=" + Integer.toString(status));
                AccessibilityDirectAccessController.this.playNotificationTone();
            } else {
                AccessibilityDirectAccessController.this.mHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda1(), this));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void play() {
            if (this.mDismiss) {
                return;
            }
            int status = this.mTts.speak(this.mText, 0, null, null);
            if (status != 0) {
                Slog.d(AccessibilityDirectAccessController.TAG, "Tts play fail");
                AccessibilityDirectAccessController.this.playNotificationTone();
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
                if (voice != null && voice.getFeatures() != null && !voice.getFeatures().contains(TextToSpeech.Engine.KEY_FEATURE_NOT_INSTALLED)) {
                    voiceDataInstalled = true;
                }
                if (voiceDataInstalled) {
                    AccessibilityDirectAccessController.this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.internal.accessibility.AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((AccessibilityDirectAccessController.TtsPrompt) obj).play();
                        }
                    }, this));
                    return;
                }
            }
            if (this.mRetryCount == 0) {
                Slog.d(AccessibilityDirectAccessController.TAG, "Tts not ready to speak.");
                AccessibilityDirectAccessController.this.playNotificationTone();
            } else {
                this.mRetryCount--;
                AccessibilityDirectAccessController.this.mHandler.sendMessageDelayed(PooledLambda.obtainMessage(new AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda1(), this), 1000L);
            }
        }
    }

    public static class ToggleableFrameworkFeatureInfo {
        private final int mLabelStringResourceId;
        private final String mSettingKey;
        private final String mSettingOffValue;
        private final String mSettingOnValue;

        ToggleableFrameworkFeatureInfo(String settingKey, String settingOnValue, String settingOffValue, int labelStringResourceId) {
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

    public static class FrameworkObjectProvider {
        public AccessibilityManager getAccessibilityManagerInstance(Context context) {
            return AccessibilityManager.getInstance(context);
        }

        public AlertDialog.Builder getAlertDialogBuilder(Context context) {
            boolean isNight = (context.getResources().getConfiguration().uiMode & 48) == 32;
            return new AlertDialog.Builder(isNight ? new ContextThemeWrapper(context, 16974120) : context);
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

    private boolean isKeyguardLocked() {
        KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
        return keyguardManager != null && keyguardManager.isKeyguardLocked();
    }
}
