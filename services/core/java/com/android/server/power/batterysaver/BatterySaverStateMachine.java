package com.android.server.power.batterysaver;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.BatterySaverPolicyConfig;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.IndentingPrintWriter;
import android.util.proto.ProtoOutputStream;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.EventLogTags;
import com.android.server.power.Slog;
import java.io.PrintWriter;
import java.time.Duration;

/* loaded from: classes3.dex */
public class BatterySaverStateMachine {
    public static final long STICKY_DISABLED_NOTIFY_TIMEOUT_MS = Duration.ofHours(12).toMillis();
    public int mBatteryLevel;
    public final BatterySaverController mBatterySaverController;
    public final boolean mBatterySaverStickyBehaviourDisabled;
    public boolean mBatteryStatusSet;
    public boolean mBootCompleted;
    public final Context mContext;
    public final int mDynamicPowerSavingsDefaultDisableThreshold;
    public int mDynamicPowerSavingsDisableThreshold;
    public boolean mDynamicPowerSavingsEnableBatterySaver;
    public boolean mEmergencyModeEnabled;
    public boolean mIsBatteryLevelLow;
    public boolean mIsPowered;
    public long mLastAdaptiveBatterySaverChangedExternallyElapsed;
    public int mLastChangedIntReason;
    public String mLastChangedStrReason;
    public final Object mLock;
    public int mSettingAutomaticBatterySaver;
    public boolean mSettingBatterySaverEnabled;
    public boolean mSettingBatterySaverEnabledSticky;
    public boolean mSettingBatterySaverStickyAutoDisableEnabled;
    public int mSettingBatterySaverStickyAutoDisableThreshold;
    public int mSettingBatterySaverTriggerThreshold;
    public boolean mSettingsLoaded;
    public boolean mUltraPowerSavingModeEnabled;
    public final ContentObserver mSettingsObserver = new ContentObserver(null) { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            synchronized (BatterySaverStateMachine.this.mLock) {
                BatterySaverStateMachine.this.refreshSettingsLocked();
            }
        }
    };
    public final Runnable mThresholdChangeLogger = new Runnable() { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            BatterySaverStateMachine.this.lambda$new$1();
        }
    };
    public int mState = 1;

    public final void doAutoBatterySaverLocked() {
    }

    public BatterySaverStateMachine(Object obj, Context context, BatterySaverController batterySaverController) {
        this.mLock = obj;
        this.mContext = context;
        this.mBatterySaverController = batterySaverController;
        this.mBatterySaverStickyBehaviourDisabled = context.getResources().getBoolean(R.bool.config_automatic_brightness_available);
        this.mDynamicPowerSavingsDefaultDisableThreshold = context.getResources().getInteger(R.integer.config_jobSchedulerUserGracePeriod);
    }

    public final boolean isAutomaticModeActiveLocked() {
        return this.mSettingAutomaticBatterySaver == 0 && this.mSettingBatterySaverTriggerThreshold > 0;
    }

    public final boolean isInAutomaticLowZoneLocked() {
        return this.mIsBatteryLevelLow;
    }

    public final boolean isDynamicModeActiveLocked() {
        return this.mSettingAutomaticBatterySaver == 1 && this.mDynamicPowerSavingsEnableBatterySaver;
    }

    public final boolean isInDynamicLowZoneLocked() {
        return this.mBatteryLevel <= this.mDynamicPowerSavingsDisableThreshold;
    }

    public void onBootCompleted() {
        runOnBgThread(new Runnable() { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                BatterySaverStateMachine.this.lambda$onBootCompleted$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootCompleted$0() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_sticky"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_trigger_level"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("automatic_power_save_mode"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("dynamic_power_savings_enabled"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("dynamic_power_savings_disable_threshold"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_sticky_auto_disable_enabled"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_sticky_auto_disable_level"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(Settings.System.getUriFor("emergency_mode"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(Settings.System.getUriFor("ultra_powersaving_mode"), false, this.mSettingsObserver, 0);
        synchronized (this.mLock) {
            if (getGlobalSetting("low_power_sticky", 0) != 0) {
                this.mState = 5;
            }
            this.mBootCompleted = true;
            refreshSettingsLocked();
            doAutoBatterySaverLocked();
        }
    }

    public void runOnBgThread(Runnable runnable) {
        BackgroundThread.getHandler().post(runnable);
    }

    public void runOnBgThreadLazy(Runnable runnable, int i) {
        Handler handler = BackgroundThread.getHandler();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, i);
    }

    public final void refreshSettingsLocked() {
        boolean z = getGlobalSetting("low_power", 0) != 0;
        boolean z2 = getGlobalSetting("low_power_sticky", 0) != 0;
        boolean z3 = getGlobalSetting("dynamic_power_savings_enabled", 0) != 0;
        int globalSetting = getGlobalSetting("low_power_trigger_level", 0);
        int globalSetting2 = getGlobalSetting("automatic_power_save_mode", 0);
        int globalSetting3 = getGlobalSetting("dynamic_power_savings_disable_threshold", this.mDynamicPowerSavingsDefaultDisableThreshold);
        boolean z4 = getGlobalSetting("low_power_sticky_auto_disable_enabled", 1) != 0;
        int globalSetting4 = getGlobalSetting("low_power_sticky_auto_disable_level", 90);
        this.mEmergencyModeEnabled = getSystemSetting("emergency_mode", 0) == 1;
        this.mUltraPowerSavingModeEnabled = getSystemSetting("ultra_powersaving_mode", 0) == 1;
        Slog.m72d("BatterySaverStateMachine", "[api] refreshSettingsLocked: lowPowerModeEnabled: " + z + " emergencyModeEnabled: " + this.mEmergencyModeEnabled + " ultraPowerSavingModeEnabled: " + this.mUltraPowerSavingModeEnabled + " lowPowerModeEnabledSticky: " + z2 + " lowPowerModeTriggerLevel: " + globalSetting);
        setSettingsLocked(z || this.mEmergencyModeEnabled || this.mUltraPowerSavingModeEnabled, z2, globalSetting, z4, globalSetting4, globalSetting2, z3, globalSetting3);
    }

    public void setSettingsLocked(boolean z, boolean z2, int i, boolean z3, int i2, int i3, boolean z4, int i4) {
        this.mSettingsLoaded = true;
        int max = Math.max(i2, i);
        boolean z5 = this.mSettingBatterySaverEnabled != z;
        boolean z6 = this.mSettingBatterySaverEnabledSticky != z2;
        boolean z7 = this.mSettingBatterySaverTriggerThreshold != i;
        boolean z8 = this.mSettingBatterySaverStickyAutoDisableEnabled != z3;
        boolean z9 = this.mSettingBatterySaverStickyAutoDisableThreshold != max;
        boolean z10 = this.mSettingAutomaticBatterySaver != i3;
        boolean z11 = this.mDynamicPowerSavingsDisableThreshold != i4;
        boolean z12 = this.mDynamicPowerSavingsEnableBatterySaver != z4;
        if (z5 || z6 || z7 || z10 || z8 || z9 || z11 || z12) {
            this.mSettingBatterySaverEnabled = z;
            this.mSettingBatterySaverEnabledSticky = z2;
            this.mSettingBatterySaverTriggerThreshold = i;
            this.mSettingBatterySaverStickyAutoDisableEnabled = z3;
            this.mSettingBatterySaverStickyAutoDisableThreshold = max;
            this.mSettingAutomaticBatterySaver = i3;
            this.mDynamicPowerSavingsDisableThreshold = i4;
            this.mDynamicPowerSavingsEnableBatterySaver = z4;
            if (z7) {
                runOnBgThreadLazy(this.mThresholdChangeLogger, 2000);
            }
            if (!this.mSettingBatterySaverStickyAutoDisableEnabled) {
                hideStickyDisabledNotification();
            }
            if (z5) {
                enableBatterySaverLocked(z, true, 8, "lp:" + z + " em:" + this.mEmergencyModeEnabled + " ul:" + this.mUltraPowerSavingModeEnabled);
                return;
            }
            doAutoBatterySaverLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        EventLogTags.writeBatterySaverSetting(this.mSettingBatterySaverTriggerThreshold);
    }

    public void setBatteryStatus(boolean z, int i, boolean z2) {
        synchronized (this.mLock) {
            boolean z3 = true;
            this.mBatteryStatusSet = true;
            boolean z4 = this.mIsPowered != z;
            boolean z5 = this.mBatteryLevel != i;
            if (this.mIsBatteryLevelLow == z2) {
                z3 = false;
            }
            if (z4 || z5 || z3) {
                this.mIsPowered = z;
                this.mBatteryLevel = i;
                this.mIsBatteryLevelLow = z2;
                doAutoBatterySaverLocked();
            }
        }
    }

    public BatterySaverPolicyConfig getFullBatterySaverPolicy() {
        BatterySaverPolicyConfig policyLocked;
        synchronized (this.mLock) {
            policyLocked = this.mBatterySaverController.getPolicyLocked(2);
        }
        return policyLocked;
    }

    public boolean setFullBatterySaverPolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) {
        boolean fullPolicyLocked;
        synchronized (this.mLock) {
            fullPolicyLocked = this.mBatterySaverController.setFullPolicyLocked(batterySaverPolicyConfig, 13);
        }
        return fullPolicyLocked;
    }

    public boolean isUltraPowerSavingModeEnabled() {
        return this.mEmergencyModeEnabled || this.mUltraPowerSavingModeEnabled;
    }

    public boolean setAdaptiveBatterySaverEnabled(boolean z) {
        boolean adaptivePolicyEnabledLocked;
        synchronized (this.mLock) {
            this.mLastAdaptiveBatterySaverChangedExternallyElapsed = SystemClock.elapsedRealtime();
            adaptivePolicyEnabledLocked = this.mBatterySaverController.setAdaptivePolicyEnabledLocked(z, 11);
        }
        return adaptivePolicyEnabledLocked;
    }

    public boolean setAdaptiveBatterySaverPolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) {
        boolean adaptivePolicyLocked;
        synchronized (this.mLock) {
            this.mLastAdaptiveBatterySaverChangedExternallyElapsed = SystemClock.elapsedRealtime();
            adaptivePolicyLocked = this.mBatterySaverController.setAdaptivePolicyLocked(batterySaverPolicyConfig, 11);
        }
        return adaptivePolicyLocked;
    }

    public final void updateStateLocked(boolean z, boolean z2) {
        if (z || (this.mBootCompleted && this.mSettingsLoaded && this.mBatteryStatusSet)) {
            int i = this.mState;
            if (i == 1) {
                if (z) {
                    if (!z2) {
                        Slog.m74e("BatterySaverStateMachine", "Tried to disable BS when it's already OFF");
                        return;
                    }
                    enableBatterySaverLocked(true, true, 2);
                    hideStickyDisabledNotification();
                    this.mState = 2;
                    return;
                }
                if (isAutomaticModeActiveLocked() && isInAutomaticLowZoneLocked()) {
                    enableBatterySaverLocked(true, false, 0);
                    hideStickyDisabledNotification();
                    this.mState = 3;
                    return;
                } else {
                    if (isDynamicModeActiveLocked() && isInDynamicLowZoneLocked()) {
                        enableBatterySaverLocked(true, false, 9);
                        hideStickyDisabledNotification();
                        this.mState = 3;
                        return;
                    }
                    return;
                }
            }
            if (i == 2) {
                if (z) {
                    if (z2) {
                        Slog.m74e("BatterySaverStateMachine", "Tried to enable BS when it's already MANUAL_ON");
                        return;
                    } else {
                        enableBatterySaverLocked(false, true, 3);
                        this.mState = 1;
                        return;
                    }
                }
                if (this.mIsPowered) {
                    enableBatterySaverLocked(false, false, 7);
                    if (this.mSettingBatterySaverEnabledSticky && !this.mBatterySaverStickyBehaviourDisabled) {
                        this.mState = 5;
                        return;
                    } else {
                        this.mState = 1;
                        return;
                    }
                }
                return;
            }
            if (i == 3) {
                if (this.mIsPowered) {
                    enableBatterySaverLocked(false, false, 7);
                    this.mState = 1;
                    return;
                }
                if (z) {
                    if (z2) {
                        Slog.m74e("BatterySaverStateMachine", "Tried to enable BS when it's already AUTO_ON");
                        return;
                    } else {
                        enableBatterySaverLocked(false, true, 3);
                        this.mState = 4;
                        return;
                    }
                }
                if (isAutomaticModeActiveLocked() && !isInAutomaticLowZoneLocked()) {
                    enableBatterySaverLocked(false, false, 1);
                    this.mState = 1;
                    return;
                } else if (isDynamicModeActiveLocked() && !isInDynamicLowZoneLocked()) {
                    enableBatterySaverLocked(false, false, 10);
                    this.mState = 1;
                    return;
                } else {
                    if (isAutomaticModeActiveLocked() || isDynamicModeActiveLocked()) {
                        return;
                    }
                    enableBatterySaverLocked(false, false, 8);
                    this.mState = 1;
                    return;
                }
            }
            if (i == 4) {
                if (z) {
                    if (!z2) {
                        Slog.m74e("BatterySaverStateMachine", "Tried to disable BS when it's already AUTO_SNOOZED");
                        return;
                    } else {
                        enableBatterySaverLocked(true, true, 2);
                        this.mState = 2;
                        return;
                    }
                }
                if (this.mIsPowered || ((isAutomaticModeActiveLocked() && !isInAutomaticLowZoneLocked()) || ((isDynamicModeActiveLocked() && !isInDynamicLowZoneLocked()) || !(isAutomaticModeActiveLocked() || isDynamicModeActiveLocked())))) {
                    this.mState = 1;
                    return;
                }
                return;
            }
            if (i != 5) {
                Slog.wtf("BatterySaverStateMachine", "Unknown state: " + this.mState);
                return;
            }
            if (z) {
                Slog.m74e("BatterySaverStateMachine", "Tried to manually change BS state from PENDING_STICKY_ON");
                return;
            }
            boolean z3 = this.mSettingBatterySaverStickyAutoDisableEnabled && this.mBatteryLevel >= this.mSettingBatterySaverStickyAutoDisableThreshold;
            if ((this.mBatterySaverStickyBehaviourDisabled || !this.mSettingBatterySaverEnabledSticky) || z3) {
                this.mState = 1;
                setStickyActive(false);
                triggerStickyDisabledNotification();
            } else {
                if (this.mIsPowered) {
                    return;
                }
                enableBatterySaverLocked(true, true, 4);
                this.mState = 2;
            }
        }
    }

    public int getState() {
        int i;
        synchronized (this.mLock) {
            i = this.mState;
        }
        return i;
    }

    public void setBatterySaverEnabledManually(boolean z) {
        Slog.m72d("BatterySaverStateMachine", "setBatterySaverEnabledManually: enabled=" + z);
        synchronized (this.mLock) {
            updateStateLocked(true, z);
        }
    }

    public final void enableBatterySaverLocked(boolean z, boolean z2, int i) {
        enableBatterySaverLocked(z, z2, i, BatterySaverController.reasonToString(i));
    }

    public final void enableBatterySaverLocked(boolean z, boolean z2, int i, String str) {
        Slog.m72d("BatterySaverStateMachine", "enableBatterySaver: enable=" + z + " manual=" + z2 + " reason=" + str + "(" + i + ")");
        if (this.mBatterySaverController.isFullEnabled() == z) {
            return;
        }
        this.mLastChangedIntReason = i;
        this.mLastChangedStrReason = str;
        this.mSettingBatterySaverEnabled = z;
        if (!this.mEmergencyModeEnabled && !this.mUltraPowerSavingModeEnabled) {
            putGlobalSetting("low_power", z ? 1 : 0);
            if (z2) {
                setStickyActive(!this.mBatterySaverStickyBehaviourDisabled && z);
            }
        }
        this.mBatterySaverController.enableBatterySaver(z, i);
        if (i == 9 || i == 0) {
            triggerDynamicModeNotification();
        } else {
            if (z) {
                return;
            }
            hideDynamicModeNotification();
        }
    }

    public void triggerDynamicModeNotification() {
        runOnBgThread(new Runnable() { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BatterySaverStateMachine.this.lambda$triggerDynamicModeNotification$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$triggerDynamicModeNotification$2() {
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        ensureNotificationChannelExists(notificationManager, "dynamic_mode_notification", R.string.ime_action_send);
        notificationManager.notifyAsUser("BatterySaverStateMachine", 1992, buildNotification("dynamic_mode_notification", R.string.immersive_cling_description, R.string.imei, "android.settings.BATTERY_SAVER_SETTINGS", 0L), UserHandle.ALL);
    }

    public void triggerStickyDisabledNotification() {
        runOnBgThread(new Runnable() { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BatterySaverStateMachine.this.lambda$triggerStickyDisabledNotification$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$triggerStickyDisabledNotification$3() {
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        ensureNotificationChannelExists(notificationManager, "battery_saver_channel", R.string.config_defaultCredentialManagerHybridService);
        notificationManager.notifyAsUser("BatterySaverStateMachine", 1993, buildNotification("battery_saver_channel", R.string.config_defaultDisplayCompatHostActivity, R.string.config_defaultContextualSearchLegacyEnabled, "android.settings.BATTERY_SAVER_SETTINGS", STICKY_DISABLED_NOTIFY_TIMEOUT_MS), UserHandle.ALL);
    }

    public final void ensureNotificationChannelExists(NotificationManager notificationManager, String str, int i) {
        NotificationChannel notificationChannel = new NotificationChannel(str, this.mContext.getText(i), 3);
        notificationChannel.setSound(null, null);
        notificationChannel.setBlockable(true);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public final Notification buildNotification(String str, int i, int i2, String str2, long j) {
        Resources resources = this.mContext.getResources();
        Intent intent = new Intent(str2);
        intent.setFlags(268468224);
        PendingIntent activity = PendingIntent.getActivity(this.mContext, 0, intent, 201326592);
        String string = resources.getString(i);
        String string2 = resources.getString(i2);
        return new Notification.Builder(this.mContext, str).setSmallIcon(R.drawable.ic_accessibility_magnification).setContentTitle(string).setContentText(string2).setContentIntent(activity).setStyle(new Notification.BigTextStyle().bigText(string2)).setOnlyAlertOnce(true).setAutoCancel(true).setTimeoutAfter(j).build();
    }

    public final void hideDynamicModeNotification() {
        hideNotification(1992);
    }

    public final void hideStickyDisabledNotification() {
        hideNotification(1993);
    }

    public final void hideNotification(final int i) {
        runOnBgThread(new Runnable() { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BatterySaverStateMachine.this.lambda$hideNotification$4(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideNotification$4(int i) {
        ((NotificationManager) this.mContext.getSystemService(NotificationManager.class)).cancelAsUser("BatterySaverStateMachine", i, UserHandle.ALL);
    }

    public final void setStickyActive(boolean z) {
        this.mSettingBatterySaverEnabledSticky = z;
        putGlobalSetting("low_power_sticky", z ? 1 : 0);
    }

    public void putGlobalSetting(String str, int i) {
        Slog.m72d("BatterySaverStateMachine", "putGlobalSetting: " + str + XmlUtils.STRING_ARRAY_SEPARATOR + i);
        Settings.Global.putInt(this.mContext.getContentResolver(), str, i);
    }

    public int getGlobalSetting(String str, int i) {
        return Settings.Global.getInt(this.mContext.getContentResolver(), str, i);
    }

    public final int getSystemSetting(String str, int i) {
        return Settings.System.getInt(this.mContext.getContentResolver(), str, i);
    }

    public void dump(PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println();
        indentingPrintWriter.println("Battery saver state machine:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            indentingPrintWriter.print("Enabled=");
            indentingPrintWriter.println(this.mBatterySaverController.isEnabled());
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("full=");
            indentingPrintWriter.println(this.mBatterySaverController.isFullEnabled());
            indentingPrintWriter.print("adaptive=");
            indentingPrintWriter.print(this.mBatterySaverController.isAdaptiveEnabled());
            if (this.mBatterySaverController.isAdaptiveEnabled()) {
                indentingPrintWriter.print(" (advertise=");
                indentingPrintWriter.print(this.mBatterySaverController.getBatterySaverPolicy().shouldAdvertiseIsEnabled());
                indentingPrintWriter.print(")");
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.print("mState=");
            indentingPrintWriter.println(this.mState);
            indentingPrintWriter.print("mLastChangedIntReason=");
            indentingPrintWriter.println(this.mLastChangedIntReason);
            indentingPrintWriter.print("mLastChangedStrReason=");
            indentingPrintWriter.println(this.mLastChangedStrReason);
            indentingPrintWriter.print("mBootCompleted=");
            indentingPrintWriter.println(this.mBootCompleted);
            indentingPrintWriter.print("mSettingsLoaded=");
            indentingPrintWriter.println(this.mSettingsLoaded);
            indentingPrintWriter.print("mBatteryStatusSet=");
            indentingPrintWriter.println(this.mBatteryStatusSet);
            indentingPrintWriter.print("mIsPowered=");
            indentingPrintWriter.println(this.mIsPowered);
            indentingPrintWriter.print("mBatteryLevel=");
            indentingPrintWriter.println(this.mBatteryLevel);
            indentingPrintWriter.print("mIsBatteryLevelLow=");
            indentingPrintWriter.println(this.mIsBatteryLevelLow);
            indentingPrintWriter.print("mSettingAutomaticBatterySaver=");
            indentingPrintWriter.println(this.mSettingAutomaticBatterySaver);
            indentingPrintWriter.print("mSettingBatterySaverEnabled=");
            indentingPrintWriter.println(this.mSettingBatterySaverEnabled);
            indentingPrintWriter.print("mSettingBatterySaverEnabledSticky=");
            indentingPrintWriter.println(this.mSettingBatterySaverEnabledSticky);
            indentingPrintWriter.print("mSettingBatterySaverStickyAutoDisableEnabled=");
            indentingPrintWriter.println(this.mSettingBatterySaverStickyAutoDisableEnabled);
            indentingPrintWriter.print("mSettingBatterySaverStickyAutoDisableThreshold=");
            indentingPrintWriter.println(this.mSettingBatterySaverStickyAutoDisableThreshold);
            indentingPrintWriter.print("mSettingBatterySaverTriggerThreshold=");
            indentingPrintWriter.println(this.mSettingBatterySaverTriggerThreshold);
            indentingPrintWriter.print("mBatterySaverStickyBehaviourDisabled=");
            indentingPrintWriter.println(this.mBatterySaverStickyBehaviourDisabled);
            indentingPrintWriter.print("mDynamicPowerSavingsDefaultDisableThreshold=");
            indentingPrintWriter.println(this.mDynamicPowerSavingsDefaultDisableThreshold);
            indentingPrintWriter.print("mDynamicPowerSavingsDisableThreshold=");
            indentingPrintWriter.println(this.mDynamicPowerSavingsDisableThreshold);
            indentingPrintWriter.print("mDynamicPowerSavingsEnableBatterySaver=");
            indentingPrintWriter.println(this.mDynamicPowerSavingsEnableBatterySaver);
            indentingPrintWriter.print("mLastAdaptiveBatterySaverChangedExternallyElapsed=");
            indentingPrintWriter.println(this.mLastAdaptiveBatterySaverChangedExternallyElapsed);
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void dumpProto(ProtoOutputStream protoOutputStream, long j) {
        synchronized (this.mLock) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1133871366145L, this.mBatterySaverController.isEnabled());
            protoOutputStream.write(1159641169938L, this.mState);
            protoOutputStream.write(1133871366158L, this.mBatterySaverController.isFullEnabled());
            protoOutputStream.write(1133871366159L, this.mBatterySaverController.isAdaptiveEnabled());
            protoOutputStream.write(1133871366160L, this.mBatterySaverController.getBatterySaverPolicy().shouldAdvertiseIsEnabled());
            protoOutputStream.write(1133871366146L, this.mBootCompleted);
            protoOutputStream.write(1133871366147L, this.mSettingsLoaded);
            protoOutputStream.write(1133871366148L, this.mBatteryStatusSet);
            protoOutputStream.write(1133871366150L, this.mIsPowered);
            protoOutputStream.write(1120986464263L, this.mBatteryLevel);
            protoOutputStream.write(1133871366152L, this.mIsBatteryLevelLow);
            protoOutputStream.write(1159641169939L, this.mSettingAutomaticBatterySaver);
            protoOutputStream.write(1133871366153L, this.mSettingBatterySaverEnabled);
            protoOutputStream.write(1133871366154L, this.mSettingBatterySaverEnabledSticky);
            protoOutputStream.write(1120986464267L, this.mSettingBatterySaverTriggerThreshold);
            protoOutputStream.write(1133871366156L, this.mSettingBatterySaverStickyAutoDisableEnabled);
            protoOutputStream.write(1120986464269L, this.mSettingBatterySaverStickyAutoDisableThreshold);
            protoOutputStream.write(1120986464276L, this.mDynamicPowerSavingsDefaultDisableThreshold);
            protoOutputStream.write(1120986464277L, this.mDynamicPowerSavingsDisableThreshold);
            protoOutputStream.write(1133871366166L, this.mDynamicPowerSavingsEnableBatterySaver);
            protoOutputStream.write(1112396529681L, this.mLastAdaptiveBatterySaverChangedExternallyElapsed);
            protoOutputStream.end(start);
        }
    }
}
