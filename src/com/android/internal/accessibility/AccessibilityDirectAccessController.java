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
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.Collections;
import java.util.Iterator;
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
            ArrayMap arrayMap = new ArrayMap(2);
            arrayMap.put(AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME, new ToggleableFrameworkFeatureInfo(Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, "1", "0", R.string.color_inversion_feature_name));
            arrayMap.put(AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME, new ToggleableFrameworkFeatureInfo(Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED, "1", "0", R.string.color_correction_feature_name));
            sFrameworkShortcutFeaturesMap = Collections.unmodifiableMap(arrayMap);
        }
        return sFrameworkShortcutFeaturesMap;
    }

    public AccessibilityDirectAccessController(Context context) {
        this.mContext = context;
    }

    public void performAccessibilityDirectAccess() {
        Slog.d(TAG, "Accessibility direct access activated");
        final ContentResolver contentResolver = this.mContext.getContentResolver();
        final int currentUser = ActivityManager.getCurrentUser();
        int intForUser = Settings.Secure.getIntForUser(contentResolver, "accessibility_direct_access_dialog_shown", 0, currentUser);
        String stringForUser = Settings.Secure.getStringForUser(contentResolver, Settings.Secure.ACCESSIBILITY_DIRECT_ACCESS_TARGET_SERVICE, currentUser);
        if (stringForUser == null && AccessibilityUtils.isSetupWizard(this.mContext)) {
            Slog.d(TAG, "Accessibility directAccessTargetService == null");
            stringForUser = AccessibilityShortcutController.TALKBACK_SE;
            Settings.Secure.putStringForUser(contentResolver, Settings.Secure.ACCESSIBILITY_DIRECT_ACCESS_TARGET_SERVICE, AccessibilityShortcutController.TALKBACK_SE, currentUser);
        }
        if (TextUtils.isEmpty(stringForUser) || (isKeyguardLocked() && intForUser == 0)) {
            Slog.d(TAG, "Accessibility direct access isEmpty");
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
        if (intForUser == 0) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.internal.accessibility.AccessibilityDirectAccessController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityDirectAccessController.this.lambda$performAccessibilityDirectAccess$0(currentUser, contentResolver);
                }
            }, 0L);
            return;
        }
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
        this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).performAccessibilityDirectAccess();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performAccessibilityDirectAccess$0(int i, ContentResolver contentResolver) {
        AlertDialog createShortcutWarningDialog = createShortcutWarningDialog(i);
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
        Settings.Secure.putIntForUser(contentResolver, "accessibility_direct_access_dialog_shown", 1, i);
    }

    private AlertDialog createShortcutWarningDialog(final int i) {
        Context systemUiContext;
        List<AccessibilityTarget> targets = AccessibilityTargetHelper.getTargets(this.mContext, 512);
        if (targets.size() == 0) {
            return null;
        }
        FrameworkObjectProvider frameworkObjectProvider = this.mFrameworkObjectProvider;
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            systemUiContext = AccessibilityUtils.getSubDisplayContext(this.mContext);
        } else {
            systemUiContext = this.mFrameworkObjectProvider.getSystemUiContext();
        }
        return frameworkObjectProvider.getAlertDialogBuilder(systemUiContext).setTitle(getShortcutWarningTitle(targets)).setMessage(getShortcutWarningMessage(targets)).setCancelable(false).setPositiveButton(R.string.accessibility_shortcut_use, (DialogInterface.OnClickListener) null).setNegativeButton(R.string.accessibility_shortcut_dont_use, new DialogInterface.OnClickListener() { // from class: com.android.internal.accessibility.AccessibilityDirectAccessController$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                AccessibilityDirectAccessController.this.lambda$createShortcutWarningDialog$1(i, dialogInterface, i2);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.internal.accessibility.AccessibilityDirectAccessController$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                AccessibilityDirectAccessController.this.lambda$createShortcutWarningDialog$2(i, dialogInterface);
            }
        }).create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$1(int i, DialogInterface dialogInterface, int i2) {
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), Settings.Secure.ACCESSIBILITY_DIRECT_ACCESS_TARGET_SERVICE, "", i);
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_direct_access_dialog_shown", 0, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createShortcutWarningDialog$2(int i, DialogInterface dialogInterface) {
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_direct_access_dialog_shown", 0, i);
    }

    private String getShortcutWarningTitle(List<AccessibilityTarget> list) {
        if (list.size() == 1 && AccessibilityShortcutController.TALKBACK_SE.equals(list.get(0).getId())) {
            if (AccessibilityUtils.isSideKeySupported()) {
                return this.mContext.getString(R.string.accessibility_direct_access_warning_title_samsung_side_key_talkback, list.get(0).getLabel());
            }
            return this.mContext.getString(R.string.accessibility_direct_access_warning_title_samsung_talkback, list.get(0).getLabel());
        }
        if (AccessibilityUtils.isSideKeySupported()) {
            return this.mContext.getString(R.string.accessibility_direct_access_warning_title_samsung_side_key);
        }
        return this.mContext.getString(R.string.accessibility_direct_access_warning_title_samsung);
    }

    private String getShortcutWarningMessage(List<AccessibilityTarget> list) {
        if (list.size() == 1) {
            if (AccessibilityUtils.isSideKeySupported()) {
                return this.mContext.getString(R.string.accessibility_direct_access_single_service_warning_samsung_side_key, list.get(0).getLabel()) + " " + this.mContext.getString(R.string.accessibility_select_different_function);
            }
            return this.mContext.getString(R.string.accessibility_direct_access_single_service_warning_samsung, list.get(0).getLabel()) + " " + this.mContext.getString(R.string.accessibility_select_different_function);
        }
        StringBuilder sb = new StringBuilder();
        Iterator<AccessibilityTarget> it = list.iterator();
        while (it.hasNext()) {
            sb.append(this.mContext.getString(R.string.accessibility_shortcut_multiple_service_list, it.next().getLabel()));
        }
        if (AccessibilityUtils.isSideKeySupported()) {
            return this.mContext.getString(R.string.accessibility_direct_access_multiple_service_warning_samsung_side_key) + ShaderAssembler.NEWLINE + sb.toString() + ShaderAssembler.NEWLINE + this.mContext.getString(R.string.accessibility_select_different_function);
        }
        return this.mContext.getString(R.string.accessibility_direct_access_multiple_service_warning_samsung) + ShaderAssembler.NEWLINE + sb.toString() + ShaderAssembler.NEWLINE + this.mContext.getString(R.string.accessibility_select_different_function);
    }

    private AccessibilityServiceInfo getInfoForTargetService() {
        ComponentName shortcutTargetComponentName = getShortcutTargetComponentName();
        if (shortcutTargetComponentName == null) {
            return null;
        }
        return this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getInstalledServiceInfoWithComponentName(shortcutTargetComponentName);
    }

    private String getShortcutFeatureDescription() {
        ComponentName shortcutTargetComponentName = getShortcutTargetComponentName();
        if (shortcutTargetComponentName == null) {
            return null;
        }
        ToggleableFrameworkFeatureInfo toggleableFrameworkFeatureInfo = getFrameworkShortcutFeaturesMap().get(shortcutTargetComponentName);
        if (toggleableFrameworkFeatureInfo != null) {
            return toggleableFrameworkFeatureInfo.getLabel(this.mContext);
        }
        AccessibilityServiceInfo installedServiceInfoWithComponentName = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getInstalledServiceInfoWithComponentName(shortcutTargetComponentName);
        if (installedServiceInfoWithComponentName == null) {
            return null;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        String charSequence = installedServiceInfoWithComponentName.getResolveInfo().loadLabel(packageManager).toString();
        CharSequence loadSummary = installedServiceInfoWithComponentName.loadSummary(packageManager);
        return TextUtils.isEmpty(loadSummary) ? charSequence : String.format("%s\n%s", charSequence, loadSummary);
    }

    private boolean hasFeatureLeanback() {
        return this.mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playNotificationTone() {
        int i = hasFeatureLeanback() ? 11 : 10;
        Ringtone ringtone = this.mFrameworkObjectProvider.getRingtone(this.mContext, Settings.System.DEFAULT_NOTIFICATION_URI);
        if (ringtone != null) {
            ringtone.setAudioAttributes(new AudioAttributes.Builder().setUsage(i).build());
            ringtone.play();
        }
    }

    private boolean performTtsPrompt(AlertDialog alertDialog) {
        String shortcutFeatureDescription = getShortcutFeatureDescription();
        AccessibilityServiceInfo infoForTargetService = getInfoForTargetService();
        if (TextUtils.isEmpty(shortcutFeatureDescription) || infoForTargetService == null || (infoForTargetService.flags & 1024) == 0) {
            return false;
        }
        final TtsPrompt ttsPrompt = new TtsPrompt(shortcutFeatureDescription);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.internal.accessibility.AccessibilityDirectAccessController$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AccessibilityDirectAccessController.TtsPrompt.this.dismiss();
            }
        });
        return true;
    }

    private ComponentName getShortcutTargetComponentName() {
        List<String> accessibilityShortcutTargets = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getAccessibilityShortcutTargets(512);
        if (accessibilityShortcutTargets.size() != 1) {
            return null;
        }
        Slog.d(TAG, "shortcutTargets.get(0) : " + accessibilityShortcutTargets.get(0));
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
            if (AccessibilityUtils.isSideKeySupported()) {
                this.mText = AccessibilityDirectAccessController.this.mContext.getString(R.string.accessibility_direct_access_spoken_feedback_side_key, str);
            } else {
                this.mText = AccessibilityDirectAccessController.this.mContext.getString(R.string.accessibility_direct_access_spoken_feedback, str);
            }
            this.mTts = AccessibilityDirectAccessController.this.mFrameworkObjectProvider.getTextToSpeech(AccessibilityDirectAccessController.this.mContext, this);
        }

        public void dismiss() {
            this.mDismiss = true;
            AccessibilityDirectAccessController.this.mHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda2(), this.mTts));
        }

        @Override // android.speech.tts.TextToSpeech.OnInitListener
        public void onInit(int i) {
            if (i != 0) {
                Slog.d(AccessibilityDirectAccessController.TAG, "Tts init fail, status=" + Integer.toString(i));
                AccessibilityDirectAccessController.this.playNotificationTone();
                return;
            }
            AccessibilityDirectAccessController.this.mHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda1(), this));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void play() {
            if (this.mDismiss || this.mTts.speak(this.mText, 0, null, null) == 0) {
                return;
            }
            Slog.d(AccessibilityDirectAccessController.TAG, "Tts play fail");
            AccessibilityDirectAccessController.this.playNotificationTone();
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
                AccessibilityDirectAccessController.this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.internal.accessibility.AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((AccessibilityDirectAccessController.TtsPrompt) obj).play();
                    }
                }, this));
                return;
            }
            int i = this.mRetryCount;
            if (i == 0) {
                Slog.d(AccessibilityDirectAccessController.TAG, "Tts not ready to speak.");
                AccessibilityDirectAccessController.this.playNotificationTone();
            } else {
                this.mRetryCount = i - 1;
                AccessibilityDirectAccessController.this.mHandler.sendMessageDelayed(PooledLambda.obtainMessage(new AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda1(), this), 1000L);
            }
        }
    }

    public static class ToggleableFrameworkFeatureInfo {
        private final int mLabelStringResourceId;
        private final String mSettingKey;
        private final String mSettingOffValue;
        private final String mSettingOnValue;

        ToggleableFrameworkFeatureInfo(String str, String str2, String str3, int i) {
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

    public static class FrameworkObjectProvider {
        public AccessibilityManager getAccessibilityManagerInstance(Context context) {
            return AccessibilityManager.getInstance(context);
        }

        public AlertDialog.Builder getAlertDialogBuilder(Context context) {
            if ((context.getResources().getConfiguration().uiMode & 48) == 32) {
                context = new ContextThemeWrapper(context, 16974120);
            }
            return new AlertDialog.Builder(context);
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

    private boolean isKeyguardLocked() {
        KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
        if (keyguardManager == null || !keyguardManager.isKeyguardLocked()) {
            return false;
        }
        return !AccessibilityUtils.isFoldedLargeCoverScreen() || keyguardManager.isKeyguardSecure();
    }
}
