package com.android.server.power.batterysaver;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.IndentingPrintWriter;
import android.util.proto.ProtoOutputStream;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ConcurrentUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import com.android.server.power.Slog;
import com.android.server.power.batterysaver.BatterySaverPolicy;
import java.io.PrintWriter;
import java.time.Duration;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BatterySaverStateMachine {
    public static final long STICKY_DISABLED_NOTIFY_TIMEOUT_MS = Duration.ofHours(12).toMillis();
    public int mBatteryLevel;
    public final BatterySaverController mBatterySaverController;
    public final boolean mBatterySaverStickyBehaviourDisabled;
    public final boolean mBatterySaverTurnedOffNotificationEnabled;
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
    public final AnonymousClass1 mSettingsObserver = new ContentObserver() { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine.1
        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            synchronized (BatterySaverStateMachine.this.mLock) {
                BatterySaverStateMachine.this.refreshSettingsLocked();
            }
        }
    };
    public final BatterySaverStateMachine$$ExternalSyntheticLambda0 mThresholdChangeLogger = new BatterySaverStateMachine$$ExternalSyntheticLambda0(this, 2);
    public int mState = 1;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.power.batterysaver.BatterySaverStateMachine$1] */
    public BatterySaverStateMachine(Object obj, Context context, BatterySaverController batterySaverController) {
        this.mLock = obj;
        this.mContext = context;
        this.mBatterySaverController = batterySaverController;
        this.mBatterySaverStickyBehaviourDisabled = context.getResources().getBoolean(R.bool.config_batterySaver_full_deferFullBackup);
        this.mBatterySaverTurnedOffNotificationEnabled = context.getResources().getBoolean(R.bool.config_batterySaver_full_disableAnimation);
        this.mDynamicPowerSavingsDefaultDisableThreshold = context.getResources().getInteger(R.integer.config_longPressOnBackBehavior);
    }

    public final Notification buildNotification(int i, int i2, long j, String str) {
        Resources resources = this.mContext.getResources();
        Intent intent = new Intent("android.settings.BATTERY_SAVER_SETTINGS");
        intent.setFlags(268468224);
        PendingIntent activity = PendingIntent.getActivity(this.mContext, 0, intent, 201326592);
        String string = resources.getString(i);
        String string2 = resources.getString(i2);
        return new Notification.Builder(this.mContext, str).setSmallIcon(R.drawable.ic_chevron_start).setContentTitle(string).setContentText(string2).setContentIntent(activity).setStyle(new Notification.BigTextStyle().bigText(string2)).setOnlyAlertOnce(true).setAutoCancel(true).setTimeoutAfter(j).build();
    }

    public final void dump(PrintWriter printWriter) {
        boolean z;
        boolean z2;
        boolean z3;
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println();
        indentingPrintWriter.println("Battery saver state machine:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.print("Enabled=");
                indentingPrintWriter.println(this.mBatterySaverController.isEnabled());
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("full=");
                BatterySaverController batterySaverController = this.mBatterySaverController;
                synchronized (batterySaverController.mLock) {
                    z = batterySaverController.mFullEnabledRaw;
                }
                indentingPrintWriter.println(z);
                indentingPrintWriter.print("adaptive=");
                BatterySaverController batterySaverController2 = this.mBatterySaverController;
                synchronized (batterySaverController2.mLock) {
                    z2 = batterySaverController2.mAdaptiveEnabledRaw;
                }
                indentingPrintWriter.print(z2);
                BatterySaverController batterySaverController3 = this.mBatterySaverController;
                synchronized (batterySaverController3.mLock) {
                    z3 = batterySaverController3.mAdaptiveEnabledRaw;
                }
                if (z3) {
                    indentingPrintWriter.print(" (advertise=");
                    indentingPrintWriter.print(this.mBatterySaverController.mBatterySaverPolicy.shouldAdvertiseIsEnabled());
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
                indentingPrintWriter.print("mBatterySaverTurnedOffNotificationEnabled=");
                indentingPrintWriter.println(this.mBatterySaverTurnedOffNotificationEnabled);
                indentingPrintWriter.print("mDynamicPowerSavingsDefaultDisableThreshold=");
                indentingPrintWriter.println(this.mDynamicPowerSavingsDefaultDisableThreshold);
                indentingPrintWriter.print("mDynamicPowerSavingsDisableThreshold=");
                indentingPrintWriter.println(this.mDynamicPowerSavingsDisableThreshold);
                indentingPrintWriter.print("mDynamicPowerSavingsEnableBatterySaver=");
                indentingPrintWriter.println(this.mDynamicPowerSavingsEnableBatterySaver);
                indentingPrintWriter.print("mLastAdaptiveBatterySaverChangedExternallyElapsed=");
                indentingPrintWriter.println(this.mLastAdaptiveBatterySaverChangedExternallyElapsed);
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final void dumpProto(ProtoOutputStream protoOutputStream) {
        boolean z;
        boolean z2;
        synchronized (this.mLock) {
            long start = protoOutputStream.start(1146756268082L);
            protoOutputStream.write(1133871366145L, this.mBatterySaverController.isEnabled());
            protoOutputStream.write(1159641169938L, this.mState);
            BatterySaverController batterySaverController = this.mBatterySaverController;
            synchronized (batterySaverController.mLock) {
                z = batterySaverController.mFullEnabledRaw;
            }
            protoOutputStream.write(1133871366158L, z);
            BatterySaverController batterySaverController2 = this.mBatterySaverController;
            synchronized (batterySaverController2.mLock) {
                z2 = batterySaverController2.mAdaptiveEnabledRaw;
            }
            protoOutputStream.write(1133871366159L, z2);
            protoOutputStream.write(1133871366160L, this.mBatterySaverController.mBatterySaverPolicy.shouldAdvertiseIsEnabled());
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

    public final void enableBatterySaverLocked(int i, boolean z, boolean z2) {
        String str;
        switch (i) {
            case 0:
                str = "Percentage Auto ON";
                break;
            case 1:
                str = "Percentage Auto OFF";
                break;
            case 2:
                str = "Manual ON";
                break;
            case 3:
                str = "Manual OFF";
                break;
            case 4:
                str = "Sticky restore";
                break;
            case 5:
                str = "Interactivity changed";
                break;
            case 6:
                str = "Policy changed";
                break;
            case 7:
                str = "Plugged in";
                break;
            case 8:
                str = "Setting changed";
                break;
            case 9:
                str = "Dynamic Warning Auto ON";
                break;
            case 10:
                str = "Dynamic Warning Auto OFF";
                break;
            case 11:
                str = "Adaptive Power Savings changed";
                break;
            case 12:
                str = "timeout";
                break;
            case 13:
                str = "Full Power Savings changed";
                break;
            default:
                str = VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown reason: ");
                break;
        }
        enableBatterySaverLocked(str, z, z2, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v9 */
    public final void enableBatterySaverLocked(String str, boolean z, boolean z2, int i) {
        boolean z3;
        Slog.d("BatterySaverStateMachine", ActiveServices$$ExternalSyntheticOutline0.m(i, str, "(", ")", FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("enableBatterySaver: enable=", z, " manual=", z2, " reason=")));
        BatterySaverController batterySaverController = this.mBatterySaverController;
        synchronized (batterySaverController.mLock) {
            z3 = batterySaverController.mFullEnabledRaw;
        }
        if (z3 == z) {
            return;
        }
        this.mLastChangedIntReason = i;
        this.mLastChangedStrReason = str;
        this.mSettingBatterySaverEnabled = z;
        if (!this.mEmergencyModeEnabled && !this.mUltraPowerSavingModeEnabled) {
            putGlobalSetting("low_power", z ? 1 : 0);
            if (z2) {
                ?? r4 = (this.mBatterySaverStickyBehaviourDisabled || !z) ? 0 : 1;
                this.mSettingBatterySaverEnabledSticky = r4;
                putGlobalSetting("low_power_sticky", r4);
            }
        }
        batterySaverController.enableBatterySaver(z, i);
        if (i == 9 || i == 0) {
            Flags.updateAutoTurnOnNotificationStringAndAction();
            triggerDynamicModeNotificationV2();
        } else {
            if (z) {
                return;
            }
            runOnBgThread(new BatterySaverStateMachine$$ExternalSyntheticLambda5(this, 1992));
        }
    }

    public final void ensureNotificationChannelExists(NotificationManager notificationManager, String str, int i) {
        NotificationChannel notificationChannel = new NotificationChannel(str, this.mContext.getText(i), 3);
        notificationChannel.setSound(null, null);
        notificationChannel.setBlockable(true);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public int getGlobalSetting(String str, int i) {
        return Settings.Global.getInt(this.mContext.getContentResolver(), str, i);
    }

    public int getState() {
        int i;
        synchronized (this.mLock) {
            i = this.mState;
        }
        return i;
    }

    public void putGlobalSetting(String str, int i) {
        Slog.d("BatterySaverStateMachine", "putGlobalSetting: " + str + ":" + i);
        Settings.Global.putInt(this.mContext.getContentResolver(), str, i);
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
        this.mEmergencyModeEnabled = Settings.System.getInt(this.mContext.getContentResolver(), "emergency_mode", 0) == 1;
        this.mUltraPowerSavingModeEnabled = Settings.System.getInt(this.mContext.getContentResolver(), "ultra_powersaving_mode", 0) == 1;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("[api] refreshSettingsLocked: lowPowerModeEnabled: ", " emergencyModeEnabled: ", z);
        m.append(this.mEmergencyModeEnabled);
        m.append(" ultraPowerSavingModeEnabled: ");
        BatteryService$$ExternalSyntheticOutline0.m(m, this.mUltraPowerSavingModeEnabled, " lowPowerModeEnabledSticky: ", z2, " lowPowerModeTriggerLevel: ");
        BatteryService$$ExternalSyntheticOutline0.m(m, globalSetting, "BatterySaverStateMachine");
        setSettingsLocked(z || this.mEmergencyModeEnabled || this.mUltraPowerSavingModeEnabled, z2, globalSetting, z4, globalSetting4, globalSetting2, z3, globalSetting3);
    }

    public void runOnBgThread(Runnable runnable) {
        BackgroundThread.getHandler().post(runnable);
    }

    public void runOnBgThreadLazy(Runnable runnable, int i) {
        Handler handler = BackgroundThread.getHandler();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, i);
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
                runOnBgThread(new BatterySaverStateMachine$$ExternalSyntheticLambda5(this, 1993));
            }
            if (z5) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("lp:", " em:", z);
                m.append(this.mEmergencyModeEnabled);
                m.append(" ul:");
                m.append(this.mUltraPowerSavingModeEnabled);
                enableBatterySaverLocked(m.toString(), z, true, 8);
            }
        }
    }

    public final void systemReady() {
        BatterySaverController batterySaverController = this.mBatterySaverController;
        batterySaverController.getClass();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        intentFilter.addAction("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED");
        batterySaverController.mContext.registerReceiver(batterySaverController.mReceiver, intentFilter);
        batterySaverController.mHandler.obtainMessage(2, 0, 0).sendToTarget();
        final BatterySaverPolicy batterySaverPolicy = batterySaverController.mBatterySaverPolicy;
        ConcurrentUtils.wtfIfLockHeld("BatterySaverPolicy", batterySaverPolicy.mLock);
        batterySaverPolicy.mContentResolver.registerContentObserver(Settings.Global.getUriFor("battery_saver_constants"), false, batterySaverPolicy);
        batterySaverPolicy.mContentResolver.registerContentObserver(Settings.Global.getUriFor("battery_saver_device_specific_constants"), false, batterySaverPolicy);
        AccessibilityManager accessibilityManager = (AccessibilityManager) batterySaverPolicy.mContext.getSystemService(AccessibilityManager.class);
        accessibilityManager.addAccessibilityStateChangeListener(new AccessibilityManager.AccessibilityStateChangeListener() { // from class: com.android.server.power.batterysaver.BatterySaverPolicy$$ExternalSyntheticLambda2
            @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
            public final void onAccessibilityStateChanged(boolean z) {
                BatterySaverPolicy.this.mAccessibilityEnabled.update(z);
            }
        });
        BatterySaverPolicy.PolicyBoolean policyBoolean = batterySaverPolicy.mAccessibilityEnabled;
        boolean isEnabled = accessibilityManager.isEnabled();
        synchronized (BatterySaverPolicy.this.mLock) {
            policyBoolean.mValue = isEnabled;
        }
        UiModeManager uiModeManager = (UiModeManager) batterySaverPolicy.mContext.getSystemService(UiModeManager.class);
        uiModeManager.addOnProjectionStateChangedListener(1, batterySaverPolicy.mContext.getMainExecutor(), batterySaverPolicy.mOnProjectionStateChangedListener);
        BatterySaverPolicy.PolicyBoolean policyBoolean2 = batterySaverPolicy.mAutomotiveProjectionActive;
        boolean z = uiModeManager.getActiveProjectionTypes() != 0;
        synchronized (BatterySaverPolicy.this.mLock) {
            policyBoolean2.mValue = z;
        }
        DeviceConfig.addOnPropertiesChangedListener("battery_saver", batterySaverPolicy.mContext.getMainExecutor(), batterySaverPolicy);
        batterySaverPolicy.mLastDeviceConfigProperties = DeviceConfig.getProperties("battery_saver", new String[0]);
        batterySaverPolicy.onChange(true, null);
    }

    public void triggerDynamicModeNotification() {
        runOnBgThread(new BatterySaverStateMachine$$ExternalSyntheticLambda0(this, 3));
    }

    public void triggerDynamicModeNotificationV2() {
        runOnBgThread(new BatterySaverStateMachine$$ExternalSyntheticLambda0(this, 0));
    }

    public void triggerStickyDisabledNotification() {
        if (this.mBatterySaverTurnedOffNotificationEnabled) {
            runOnBgThread(new BatterySaverStateMachine$$ExternalSyntheticLambda0(this, 1));
        }
    }

    public final void updateStateLocked(boolean z) {
        int i = this.mState;
        if (i == 1) {
            if (!z) {
                Slog.e("BatterySaverStateMachine", "Tried to disable BS when it's already OFF");
                return;
            }
            enableBatterySaverLocked(2, true, true);
            runOnBgThread(new BatterySaverStateMachine$$ExternalSyntheticLambda5(this, 1993));
            this.mState = 2;
            return;
        }
        if (i == 2) {
            if (z) {
                Slog.e("BatterySaverStateMachine", "Tried to enable BS when it's already MANUAL_ON");
                return;
            } else {
                enableBatterySaverLocked(3, false, true);
                this.mState = 1;
                return;
            }
        }
        if (i == 3) {
            if (this.mIsPowered) {
                enableBatterySaverLocked(7, false, false);
                this.mState = 1;
                return;
            } else if (z) {
                Slog.e("BatterySaverStateMachine", "Tried to enable BS when it's already AUTO_ON");
                return;
            } else {
                enableBatterySaverLocked(3, false, true);
                this.mState = 4;
                return;
            }
        }
        if (i == 4) {
            if (!z) {
                Slog.e("BatterySaverStateMachine", "Tried to disable BS when it's already AUTO_SNOOZED");
                return;
            } else {
                enableBatterySaverLocked(2, true, true);
                this.mState = 2;
                return;
            }
        }
        if (i == 5) {
            Slog.e("BatterySaverStateMachine", "Tried to manually change BS state from PENDING_STICKY_ON");
            return;
        }
        String str = "Unknown state: " + this.mState;
        int i2 = Slog.$r8$clinit;
        android.util.Slog.wtf("BatterySaverStateMachine", str);
    }
}
