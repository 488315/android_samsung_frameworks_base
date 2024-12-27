package com.android.server.notification;

import android.R;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.IRingtonePlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserManager;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import com.android.internal.config.sysui.SystemUiSystemPropertiesFlags;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.lights.LightsManager;
import com.android.server.lights.LogicalLight;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.sepunion.SemGoodCatchManager;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NotificationAttentionHelper {
    static final Map NOTIFICATION_AVALANCHE_TRIGGER_EXTRAS;
    public static SemGoodCatchManager mSemAudioGoodCatchManager;
    public AccessibilityManager mAccessibilityManager;
    public LogicalLight mAttentionLight;
    public final AnonymousClass1 mAudioGoodCatchStateListener;
    public AudioManager mAudioManager;
    public int mCallState;
    public final Context mContext;
    public boolean mDisableNotificationEffects;
    public boolean mDisableVibration;
    public final EasyMuteController mEasyMuteController;
    public final Handler mHandler;
    public boolean mHasLight;
    public final AudioAttributes mInCallNotificationAudioAttributes;
    public final Uri mInCallNotificationUri;
    public final float mInCallNotificationVolume;
    public final AnonymousClass5 mIntentReceiver;
    public boolean mIsAutomotive;
    public KeyguardManager mKeyguardManager;
    public final NotificationManagerService.AnonymousClass2 mNMP;
    public final AnonymousClass1 mNotiGoodCatchStateListener;
    public SemGoodCatchManager mNotiSemGoodCatchManager;
    public boolean mNotificationEffectsEnabledForAutomotive;
    public LogicalLight mNotificationLight;
    public boolean mNotificationPulseEnabled;
    public final PackageManager mPackageManager;
    public final PreferencesHelper mPreferencesHelper;
    public final SettingsObserver mSettingsObserver;
    public String mSoundNotificationKey;
    public boolean mSystemReady;
    public final TelephonyManager mTelephonyManager;
    public final NotificationUsageStats mUsageStats;
    public final boolean mUseAttentionLight;
    public boolean mUserPresent;
    public String mVibrateNotificationKey;
    public VibratorHelper mVibratorHelper;
    public final ZenModeHelper mZenModeHelper;
    public static final boolean DEBUG = Log.isLoggable("NotifAttentionHelper", 3);
    public static final boolean DEBUG_INTERRUPTIVENESS =
            SystemProperties.getBoolean("debug.notification.interruptiveness", false);
    static final Set NOTIFICATION_AVALANCHE_TRIGGER_INTENTS =
            Set.of(
                    "android.intent.action.AIRPLANE_MODE",
                    "android.intent.action.BOOT_COMPLETED",
                    "android.intent.action.USER_SWITCHED",
                    "android.intent.action.MANAGED_PROFILE_AVAILABLE");
    public final ArrayList mLights = new ArrayList();
    public boolean mInCallStateOffHook = false;
    public boolean mScreenOn = true;
    public Binder mCallNotificationToken = null;
    public int mIsMutedByWearableApps = 0;
    public int mVibrationIndex = 50034;
    public boolean mIsEnableAlertByCall = false;
    public boolean mGoodCatchSoundPlayedOn = false;
    public boolean mGoodCatchNotiMutedOn = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    abstract class PolitenessStrategy {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public static final Uri NOTIFICATION_LIGHT_PULSE_URI =
                Settings.System.getUriFor("led_indicator_missed_event");

        static {
            Settings.System.getUriFor("notification_cooldown_enabled");
            Settings.System.getUriFor("notification_cooldown_all");
            Settings.System.getUriFor("notification_cooldown_vibrate_unlocked");
        }

        public SettingsObserver() {
            super(null);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (NOTIFICATION_LIGHT_PULSE_URI.equals(uri)) {
                boolean z2 =
                        Settings.System.getIntForUser(
                                        NotificationAttentionHelper.this.mContext
                                                .getContentResolver(),
                                        "led_indicator_missed_event",
                                        0,
                                        -2)
                                != 0;
                NotificationAttentionHelper notificationAttentionHelper =
                        NotificationAttentionHelper.this;
                if (notificationAttentionHelper.mNotificationPulseEnabled != z2) {
                    notificationAttentionHelper.mNotificationPulseEnabled = z2;
                    notificationAttentionHelper.updateLightsLocked();
                }
            }
            Flags.politeNotifications();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Signals {
        public final boolean isCurrentProfile;
        public final int listenerHints;

        public Signals(int i, boolean z) {
            this.isCurrentProfile = z;
            this.listenerHints = i;
        }
    }

    static {
        Boolean bool = Boolean.FALSE;
        NOTIFICATION_AVALANCHE_TRIGGER_EXTRAS =
                Map.of(
                        "android.intent.action.AIRPLANE_MODE",
                        new Pair(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, bool),
                        "android.intent.action.MANAGED_PROFILE_AVAILABLE",
                        new Pair("android.intent.extra.QUIET_MODE", bool));
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.notification.NotificationAttentionHelper$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.notification.NotificationAttentionHelper$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.notification.NotificationAttentionHelper$5] */
    public NotificationAttentionHelper(
            Context context,
            LightsManager lightsManager,
            AccessibilityManager accessibilityManager,
            PackageManager packageManager,
            UserManager userManager,
            NotificationUsageStats notificationUsageStats,
            NotificationManagerService.AnonymousClass2 anonymousClass2,
            ZenModeHelper zenModeHelper,
            SystemUiSystemPropertiesFlags.FlagResolver flagResolver,
            PreferencesHelper preferencesHelper,
            Handler handler) {
        final int i = 0;
        this.mAudioGoodCatchStateListener =
                new SemGoodCatchManager.OnStateChangeListener(
                        this) { // from class:
                                // com.android.server.notification.NotificationAttentionHelper.1
                    public final /* synthetic */ NotificationAttentionHelper this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onStart(String str) {
                        switch (i) {
                            case 0:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStart(), ", str, "NotifAttentionHelper");
                                this.this$0.mGoodCatchSoundPlayedOn = true;
                                break;
                            default:
                                Log.d("NotifAttentionHelper", "onStart(), " + str);
                                if ("noti_muted".equals(str)) {
                                    this.this$0.mGoodCatchNotiMutedOn = true;
                                    break;
                                }
                                break;
                        }
                    }

                    public final void onStop(String str) {
                        switch (i) {
                            case 0:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStop(),", str, "NotifAttentionHelper");
                                this.this$0.mGoodCatchSoundPlayedOn = false;
                                break;
                            default:
                                Log.d("NotifAttentionHelper", "onStop()," + str);
                                if ("noti_muted".equals(str)) {
                                    this.this$0.mGoodCatchNotiMutedOn = false;
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mNotiGoodCatchStateListener =
                new SemGoodCatchManager.OnStateChangeListener(
                        this) { // from class:
                                // com.android.server.notification.NotificationAttentionHelper.1
                    public final /* synthetic */ NotificationAttentionHelper this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onStart(String str) {
                        switch (i2) {
                            case 0:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStart(), ", str, "NotifAttentionHelper");
                                this.this$0.mGoodCatchSoundPlayedOn = true;
                                break;
                            default:
                                Log.d("NotifAttentionHelper", "onStart(), " + str);
                                if ("noti_muted".equals(str)) {
                                    this.this$0.mGoodCatchNotiMutedOn = true;
                                    break;
                                }
                                break;
                        }
                    }

                    public final void onStop(String str) {
                        switch (i2) {
                            case 0:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStop(),", str, "NotifAttentionHelper");
                                this.this$0.mGoodCatchSoundPlayedOn = false;
                                break;
                            default:
                                Log.d("NotifAttentionHelper", "onStop()," + str);
                                if ("noti_muted".equals(str)) {
                                    this.this$0.mGoodCatchNotiMutedOn = false;
                                    break;
                                }
                                break;
                        }
                    }
                };
        new ArrayList();
        this.mIntentReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.notification.NotificationAttentionHelper.5
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        if (action.equals("android.intent.action.SCREEN_ON")) {
                            NotificationAttentionHelper notificationAttentionHelper =
                                    NotificationAttentionHelper.this;
                            notificationAttentionHelper.mScreenOn = true;
                            notificationAttentionHelper.updateLightsLocked();
                        } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                            NotificationAttentionHelper notificationAttentionHelper2 =
                                    NotificationAttentionHelper.this;
                            notificationAttentionHelper2.mScreenOn = false;
                            notificationAttentionHelper2.mUserPresent = false;
                            notificationAttentionHelper2.updateLightsLocked();
                        } else if (action.equals("android.intent.action.PHONE_STATE")) {
                            NotificationAttentionHelper.this.mInCallStateOffHook =
                                    TelephonyManager.EXTRA_STATE_OFFHOOK.equals(
                                            intent.getStringExtra(
                                                    LauncherConfigurationInternal
                                                            .KEY_STATE_BOOLEAN));
                            NotificationAttentionHelper.this.updateLightsLocked();
                        } else if (action.equals("android.intent.action.USER_PRESENT")) {
                            NotificationAttentionHelper notificationAttentionHelper3 =
                                    NotificationAttentionHelper.this;
                            notificationAttentionHelper3.mUserPresent = true;
                            LogicalLight logicalLight =
                                    notificationAttentionHelper3.mNotificationLight;
                            if (logicalLight != null) {
                                logicalLight.turnOff();
                            }
                        } else if (action.equals("android.intent.action.USER_ADDED")
                                || action.equals("android.intent.action.USER_REMOVED")
                                || action.equals("android.intent.action.USER_SWITCHED")
                                || action.equals("android.intent.action.USER_UNLOCKED")) {
                            NotificationAttentionHelper.this.loadUserSettings();
                        } else if ("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED"
                                .equals(action)) {
                            NotificationAttentionHelper notificationAttentionHelper4 =
                                    NotificationAttentionHelper.this;
                            if (notificationAttentionHelper4.mNotiSemGoodCatchManager == null) {
                                NotificationAttentionHelper notificationAttentionHelper5 =
                                        NotificationAttentionHelper.this;
                                notificationAttentionHelper4.mNotiSemGoodCatchManager =
                                        new SemGoodCatchManager(
                                                notificationAttentionHelper5.mContext,
                                                "NotificationManagerService",
                                                new String[] {"noti_muted"},
                                                notificationAttentionHelper5
                                                        .mNotiGoodCatchStateListener);
                                Log.d(
                                        "NotifAttentionHelper",
                                        "mNotiSemGoodCatchManager is created");
                            }
                            if (NotificationAttentionHelper.mSemAudioGoodCatchManager == null) {
                                NotificationAttentionHelper notificationAttentionHelper6 =
                                        NotificationAttentionHelper.this;
                                NotificationAttentionHelper.mSemAudioGoodCatchManager =
                                        new SemGoodCatchManager(
                                                notificationAttentionHelper6.mContext,
                                                "NotificationManagerService",
                                                new String[] {"playback"},
                                                notificationAttentionHelper6
                                                        .mAudioGoodCatchStateListener);
                                Log.d(
                                        "NotifAttentionHelper",
                                        "SemAudioGoodCatchManager is created");
                            }
                        }
                        Flags.crossAppPoliteNotifications();
                    }
                };
        this.mContext = context;
        this.mPackageManager = packageManager;
        this.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mAccessibilityManager = accessibilityManager;
        this.mNMP = anonymousClass2;
        this.mUsageStats = notificationUsageStats;
        this.mZenModeHelper = zenModeHelper;
        this.mVibratorHelper = new VibratorHelper(context);
        this.mNotificationLight = lightsManager.getLight(4);
        this.mAttentionLight = lightsManager.getLight(5);
        Resources resources = context.getResources();
        this.mUseAttentionLight = resources.getBoolean(R.bool.config_use_voip_mode_for_ims);
        this.mPreferencesHelper = preferencesHelper;
        this.mHandler = handler;
        this.mHasLight = new File("/sys/class/sec/led/led_pattern").isFile();
        this.mEasyMuteController = new EasyMuteController(context);
        if (Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) == 0) {
            this.mDisableNotificationEffects = true;
        }
        this.mInCallNotificationUri =
                Uri.parse(
                        "file://" + resources.getString(R.string.elapsed_time_short_format_mm_ss));
        this.mInCallNotificationAudioAttributes =
                new AudioAttributes.Builder().setContentType(4).setUsage(2).build();
        this.mInCallNotificationVolume = resources.getFloat(R.dimen.config_qsTileStrokeWidthActive);
        Flags.politeNotifications();
        this.mSettingsObserver = new SettingsObserver();
        loadUserSettings();
    }

    public static String callStateToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                        i, "CALL_STATE_UNKNOWN_")
                                : "CALL_STATE_OFFHOOK"
                        : "CALL_STATE_RINGING"
                : "CALL_STATE_IDLE";
    }

    public static boolean isNotificationForCurrentUser(
            NotificationRecord notificationRecord, Signals signals) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int currentUser = ActivityManager.getCurrentUser();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return notificationRecord.sbn.getUserId() == -1
                    || notificationRecord.sbn.getUserId() == currentUser
                    || signals.isCurrentProfile;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:352:0x01ec, code lost:

       if (isInsistentUpdate(r33) == false) goto L160;
    */
    /* JADX WARN: Code restructure failed: missing block: B:523:0x01e6, code lost:

       if ((r3.sbn.getNotification().flags & 4) != 0) goto L128;
    */
    /* JADX WARN: Removed duplicated region for block: B:115:0x062c  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0632  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0682  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0684  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x06b7  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x06cc  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x07d9  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x07e0  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x08c4  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x08c7  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0881  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0887  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0889  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0883  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0824  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0841  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x07e3  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x07db  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x07bb  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x07be  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0782  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0786  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x07cf  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x06b9  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0634  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x062e  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x0180 A[Catch: all -> 0x0537, TRY_LEAVE, TryCatch #4 {, blocks: (B:332:0x0156, B:337:0x0179, B:339:0x0180), top: B:331:0x0156 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x028b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int buzzBeepBlinkLocked(
            final com.android.server.notification.NotificationRecord r33,
            com.android.server.notification.NotificationAttentionHelper.Signals r34) {
        /*
            Method dump skipped, instructions count: 2288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.NotificationAttentionHelper.buzzBeepBlinkLocked(com.android.server.notification.NotificationRecord,"
                    + " com.android.server.notification.NotificationAttentionHelper$Signals):int");
    }

    public final void clearEffectsLocked(String str) {
        if (str.equals(this.mSoundNotificationKey)) {
            clearSoundLocked();
        }
        if (str.equals(this.mVibrateNotificationKey)) {
            clearVibrateLocked();
        }
        if (this.mLights.remove(str)) {
            updateLightsLocked();
        }
    }

    public final void clearSoundLocked() {
        EasyMuteController easyMuteController = this.mEasyMuteController;
        this.mSoundNotificationKey = null;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                IRingtonePlayer ringtonePlayer = this.mAudioManager.getRingtonePlayer();
                if (ringtonePlayer != null) {
                    ringtonePlayer.stopAsync();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (easyMuteController == null) {
                    return;
                }
            } catch (RemoteException e) {
                Log.e("NotifAttentionHelper", "Failed clearSoundLocked: " + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (easyMuteController == null) {
                    return;
                }
            }
            easyMuteController.unregisterListener();
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (easyMuteController != null) {
                easyMuteController.unregisterListener();
            }
            throw th;
        }
    }

    public final void clearVibrateLocked() {
        this.mVibrateNotificationKey = null;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mVibratorHelper.mVibrator.cancel(-15);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String disableNotificationEffects(NotificationRecord notificationRecord, int i) {
        AudioAttributes audioAttributes;
        if (this.mDisableNotificationEffects) {
            return "booleanState";
        }
        if ((i & 1) != 0) {
            return "listenerHints";
        }
        if (notificationRecord != null
                && (audioAttributes = notificationRecord.mAttributes) != null) {
            if ((i & 2) != 0 && audioAttributes.getUsage() != 6) {
                return "listenerNoti";
            }
            if ((i & 4) != 0 && notificationRecord.mAttributes.getUsage() == 6) {
                return "listenerCall";
            }
        }
        if (this.mCallState == 0 || this.mZenModeHelper.mFiltering.isCall(notificationRecord)) {
            return null;
        }
        return "callState";
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("\n  Notification attention state:");
        printWriter.print("    ");
        printWriter.println("  mSoundNotificationKey=" + this.mSoundNotificationKey);
        printWriter.print("    ");
        printWriter.println("  mVibrateNotificationKey=" + this.mVibrateNotificationKey);
        printWriter.print("    ");
        printWriter.println("  mDisableNotificationEffects=" + this.mDisableNotificationEffects);
        printWriter.print("    ");
        printWriter.println("  mCallState=" + callStateToString(this.mCallState));
        printWriter.print("    ");
        printWriter.println("  mSystemReady=" + this.mSystemReady);
        printWriter.print("    ");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder("  mNotificationPulseEnabled="),
                this.mNotificationPulseEnabled,
                printWriter);
        int size = this.mLights.size();
        if (size > 0) {
            printWriter.print("    ");
            printWriter.println("  Lights List:");
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    printWriter.print("  > ");
                } else {
                    printWriter.print("    ");
                }
                printWriter.println((String) this.mLights.get(i));
            }
            printWriter.println("  ");
        }
    }

    public PolitenessStrategy getPolitenessStrategy() {
        return null;
    }

    public VibratorHelper getVibratorHelper() {
        return this.mVibratorHelper;
    }

    public final boolean isInCall() {
        int modeInternal;
        return this.mInCallStateOffHook
                || (modeInternal = this.mAudioManager.getModeInternal()) == 2
                || modeInternal == 3;
    }

    public final boolean isInsistentUpdate(NotificationRecord notificationRecord) {
        NotificationRecord notificationByKey;
        if (Objects.equals(notificationRecord.sbn.getKey(), this.mSoundNotificationKey)
                || Objects.equals(notificationRecord.sbn.getKey(), this.mVibrateNotificationKey)) {
            String str = this.mSoundNotificationKey;
            NotificationManagerService.AnonymousClass2 anonymousClass2 = this.mNMP;
            NotificationRecord notificationByKey2 = anonymousClass2.getNotificationByKey(str);
            if ((notificationByKey2 != null
                            && notificationByKey2.mAttributes.getUsage() == 6
                            && (notificationByKey2.sbn.getNotification().flags & 4) != 0)
                    || ((notificationByKey =
                                            anonymousClass2.getNotificationByKey(
                                                    this.mVibrateNotificationKey))
                                    != null
                            && notificationByKey.mAttributes.getUsage() == 6
                            && (notificationByKey.sbn.getNotification().flags & 4) != 0)) {
                return true;
            }
        }
        return false;
    }

    public final void loadUserSettings() {
        boolean z =
                Settings.System.getIntForUser(
                                this.mContext.getContentResolver(),
                                "notification_light_pulse",
                                0,
                                -2)
                        != 0;
        if (this.mNotificationPulseEnabled != z) {
            this.mNotificationPulseEnabled = z;
            updateLightsLocked();
        }
        Flags.politeNotifications();
    }

    public final void sendAccessibilityEvent(NotificationRecord notificationRecord) {
        if (this.mAccessibilityManager.isEnabled()) {
            Notification notification = notificationRecord.sbn.getNotification();
            String packageName = notificationRecord.sbn.getPackageName();
            AccessibilityEvent obtain = AccessibilityEvent.obtain(64);
            obtain.setPackageName(packageName);
            obtain.setClassName(Notification.class.getName());
            int i = notificationRecord.mPackageVisibility;
            if (i == -1000) {
                i = notification.visibility;
            }
            int identifier = notificationRecord.sbn.getUser().getIdentifier();
            if (identifier < 0 || !this.mKeyguardManager.isDeviceLocked(identifier) || i == 1) {
                obtain.setParcelableData(notification);
            } else {
                obtain.setParcelableData(notification.publicVersion);
            }
            CharSequence charSequence = notification.tickerText;
            if (!TextUtils.isEmpty(charSequence)) {
                obtain.getText().add(charSequence);
            }
            this.mAccessibilityManager.sendAccessibilityEvent(obtain);
        }
    }

    public void setAccessibilityManager(AccessibilityManager accessibilityManager) {
        this.mAccessibilityManager = accessibilityManager;
    }

    public void setAudioManager(AudioManager audioManager) {
        this.mAudioManager = audioManager;
    }

    public void setInCallStateOffHook(boolean z) {
        this.mInCallStateOffHook = z;
    }

    public void setIsAutomotive(boolean z) {
        this.mIsAutomotive = z;
    }

    public void setKeyguardManager(KeyguardManager keyguardManager) {
        this.mKeyguardManager = keyguardManager;
    }

    public void setLights(LogicalLight logicalLight) {
        this.mNotificationLight = logicalLight;
        this.mAttentionLight = logicalLight;
        this.mNotificationPulseEnabled = true;
        this.mHasLight = true;
    }

    public void setNotificationEffectsEnabledForAutomotive(boolean z) {
        this.mNotificationEffectsEnabledForAutomotive = z;
    }

    public void setScreenOn(boolean z) {
        this.mScreenOn = z;
    }

    public void setSystemReady(boolean z) {
        this.mSystemReady = z;
    }

    public void setUserPresent(boolean z) {}

    public void setVibratorHelper(VibratorHelper vibratorHelper) {
        this.mVibratorHelper = vibratorHelper;
    }

    public final void updateLightsLocked() {
        if (this.mNotificationLight == null) {
            return;
        }
        NotificationRecord notificationRecord = null;
        while (notificationRecord == null && !this.mLights.isEmpty()) {
            ArrayList arrayList = this.mLights;
            String str = (String) arrayList.get(arrayList.size() - 1);
            NotificationRecord notificationByKey = this.mNMP.getNotificationByKey(str);
            if (notificationByKey == null) {
                Slog.wtfStack("NotifAttentionHelper", "LED Notification does not exist: " + str);
                this.mLights.remove(str);
            }
            notificationRecord = notificationByKey;
        }
        if (notificationRecord == null || isInCall() || this.mScreenOn) {
            this.mNotificationLight.turnOff();
            return;
        }
        NotificationRecord.Light light = notificationRecord.mLight;
        if (light == null || !this.mNotificationPulseEnabled) {
            return;
        }
        this.mNotificationLight.setFlashing(light.color, 1, light.onMs, light.offMs);
    }

    public final void vibrate(
            NotificationRecord notificationRecord, VibrationEffect vibrationEffect, boolean z) {
        StringBuilder sb = new StringBuilder("Notification (");
        sb.append(notificationRecord.sbn.getOpPkg());
        sb.append(" ");
        sb.append(notificationRecord.sbn.getUid());
        sb.append(") ");
        sb.append(z ? "(Delayed)" : "");
        this.mVibratorHelper.vibrate(
                vibrationEffect, notificationRecord.mAttributes, sb.toString());
    }
}
