package com.android.systemui.power;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.media.NotificationPlayer;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.android.systemui.power.dialog.BatteryHealthInterruptionDialog;
import com.android.systemui.power.dialog.BatterySwellingLowTempDialog;
import com.android.systemui.power.dialog.HappenedOverheatShutdownDialog;
import com.android.systemui.power.dialog.HvChargerEnableDialog;
import com.android.systemui.power.dialog.IncompatibleChargerDialog;
import com.android.systemui.power.dialog.IncompleteChargerDialog;
import com.android.systemui.power.dialog.OverheatDialog;
import com.android.systemui.power.dialog.PdChargerAlertDialog;
import com.android.systemui.power.dialog.PowerUiDialog;
import com.android.systemui.power.dialog.SafeModeDialog;
import com.android.systemui.power.dialog.UsbDamageProtectionDialog;
import com.android.systemui.power.dialog.WaterProtectionDialog;
import com.android.systemui.power.dialog.WillOverheatShutdownDialog;
import com.android.systemui.power.dialog.WirelessFodDialog;
import com.android.systemui.power.notification.PowerUiNotification;
import com.android.systemui.power.notification.PowerUiNotificationFactory;
import com.android.systemui.power.sound.PowerUiSound;
import com.android.systemui.power.sound.PowerUiSoundFactory;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemGateConfig;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.presence.ServiceTuple;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecPowerNotificationWarnings implements SecWarningsUI {
    public final AudioManager mAudioManager;
    public boolean mAutomaticTestMode;
    public int mBatteryHealth;
    public AlertDialog mBatteryHealthInterruptionDialog;
    public final AnonymousClass4 mBatteryHealthInterruptionTask;
    public int mBatteryLevel;
    public final Intent mBatterySettings;
    public int mBatteryStatus;
    public int mBucket;
    public long mChargingTime;
    public int mChargingType;
    public Toast mConnectedChargerChangedToast;
    public final Context mContext;
    public int mCurrentBatteryMode;
    public boolean mDoNotShowChargingNotice;
    public boolean mFTAMode;
    public final AnonymousClass2 mFoldStateListener;
    public AlertDialog mHVchargerEnablePopupDialog;
    public final Handler mHandler;
    public HandlerWrapper mHandlerWrapper;
    public AlertDialog mIncompatibleChargerDialog;
    public boolean mIsHiccupState;
    public boolean mIsInCall;
    public boolean mIsMaximumProtectionEnabled;
    public boolean mIsTemperatureHiccupState;
    public boolean mIsUnintentionalPopupShowing;
    public boolean mIsWaterDetected;
    public int mMaximumThresholdValue;
    public final NotificationManager mNotificationManager;
    public final NotificationPlayer mNotificationPlayer;
    public int mOldBatteryLevel;
    public int mOldChargingType;
    public String mOptimizationChargingFinishTime;
    public AlertDialog mOverheatNoticeDialog;
    public AlertDialog mOverheatShutdownHappenedDialog;
    public final AnonymousClass9 mOverheatShutdownTask;
    public boolean mPlaySound;
    public final PowerManager mPowerManager;
    public AlertDialog mSafeModeDialog;
    public boolean mShowChargingNotice;
    public AlertDialog mSlowByChargerConnectionInfoDialog;
    public final Intent mSmartManagerBatterySettings;
    public int mSuperFastCharger;
    public AlertDialog mSwellingDialog;
    public final AnonymousClass5 mTemperatureLimitAlertTask;
    public WindowManager.LayoutParams mUnintentionalLCDOnWindowLp;
    public UnintentionalLcdOnView mUnintentionalLcdOnWindow;
    public AlertDialog mUsbDamageProtectionAlertDialog;
    public final AnonymousClass21 mUsbDamageProtectionAlertTask;
    public PowerManager.WakeLock mUsbDamageProtectionPartialWakeLock;
    public final Vibrator mVibrator;
    public boolean mWarning;
    public long mWarningTriggerTimeMs;
    public AlertDialog mWaterProtectionAlertDialog;
    public final AnonymousClass18 mWaterProtectionAlertTask;
    public PowerManager.WakeLock mWaterProtectionPartialWakeLock;
    public AlertDialog mWillOverheatShutdownWarningDialog;
    public WindowManager mWindowManager;
    public AlertDialog mWirelessFodAlertDialog;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Receiver extends BroadcastReceiver {
        public /* synthetic */ Receiver(SecPowerNotificationWarnings secPowerNotificationWarnings, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("PowerUI.Notification", "Received " + action);
            if ("PNW.batteryInfo".equals(action)) {
                SecPowerNotificationWarnings secPowerNotificationWarnings = SecPowerNotificationWarnings.this;
                secPowerNotificationWarnings.mContext.startActivityAsUser(secPowerNotificationWarnings.mBatterySettings, UserHandle.CURRENT);
                return;
            }
            if ("PNW.dismissedWarning".equals(action)) {
                SecPowerNotificationWarnings secPowerNotificationWarnings2 = SecPowerNotificationWarnings.this;
                secPowerNotificationWarnings2.mWarning = false;
                secPowerNotificationWarnings2.updateNotification();
                secPowerNotificationWarnings2.restoreScreenTimeOutIfNeeded();
                return;
            }
            if ("PNW.powerMode".equals(action)) {
                SecPowerNotificationWarnings secPowerNotificationWarnings3 = SecPowerNotificationWarnings.this;
                if (secPowerNotificationWarnings3.mCurrentBatteryMode == 2) {
                    return;
                }
                secPowerNotificationWarnings3.mWarning = false;
                secPowerNotificationWarnings3.updateNotification();
                SecPowerNotificationWarnings secPowerNotificationWarnings4 = SecPowerNotificationWarnings.this;
                secPowerNotificationWarnings4.mContext.startActivityAsUser(secPowerNotificationWarnings4.mSmartManagerBatterySettings, UserHandle.CURRENT);
                return;
            }
            if ("PNW.abnormalPadNoThanks".equals(action)) {
                SecPowerNotificationWarnings secPowerNotificationWarnings5 = SecPowerNotificationWarnings.this;
                secPowerNotificationWarnings5.getClass();
                Log.i("PowerUI.Notification", "handleAbnormalPadNotiNoThanks");
                Log.i("PowerUI.Notification", "dismissAbnormalPadNotification");
                secPowerNotificationWarnings5.cancelNotification(8);
                SharedPreferences sharedPreferences = secPowerNotificationWarnings5.mContext.getSharedPreferences("com.android.systemui.abnormal_pad", 0);
                if (sharedPreferences != null) {
                    Log.i("PowerUI.Notification", "User clicked Do_not_show_again, so we set preference.");
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putBoolean("DoNotShowAbnormalPadNoti", true);
                    edit.commit();
                    return;
                }
                return;
            }
            if ("com.samsung.systemui.power.action.ACTION_BATTERY_LOW_CLOSE_BUTTON".equals(action)) {
                SecPowerNotificationWarnings secPowerNotificationWarnings6 = SecPowerNotificationWarnings.this;
                secPowerNotificationWarnings6.mWarning = false;
                secPowerNotificationWarnings6.updateNotification();
                secPowerNotificationWarnings6.restoreScreenTimeOutIfNeeded();
                return;
            }
            if ("com.samsung.intent.action.EMERGENCY_STATE_CHANGED".equals(action)) {
                int i = SecPowerNotificationWarnings.this.mCurrentBatteryMode;
                int intExtra = intent.getIntExtra("reason", 0);
                if (intExtra == 3) {
                    SecPowerNotificationWarnings.this.mCurrentBatteryMode = 2;
                } else if (intExtra == 5) {
                    SecPowerNotificationWarnings.this.mCurrentBatteryMode = 0;
                }
                SecPowerNotificationWarnings secPowerNotificationWarnings7 = SecPowerNotificationWarnings.this;
                if (!secPowerNotificationWarnings7.mWarning || i == secPowerNotificationWarnings7.mCurrentBatteryMode) {
                    return;
                }
                secPowerNotificationWarnings7.updateNotification();
                return;
            }
            if ("com.samsung.systemui.power.action.ACTION_BATTERY_OVER_HEAT".equals(action)) {
                final SecPowerNotificationWarnings secPowerNotificationWarnings8 = SecPowerNotificationWarnings.this;
                if (secPowerNotificationWarnings8.mOverheatNoticeDialog == null) {
                    AlertDialog popupDialog = secPowerNotificationWarnings8.getPopupDialog(7);
                    secPowerNotificationWarnings8.mOverheatNoticeDialog = popupDialog;
                    if (popupDialog == null) {
                        return;
                    }
                    popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.8
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            SecPowerNotificationWarnings.this.mOverheatNoticeDialog = null;
                        }
                    });
                    secPowerNotificationWarnings8.mOverheatNoticeDialog.getWindow().setType(2009);
                    secPowerNotificationWarnings8.mOverheatNoticeDialog.show();
                    return;
                }
                return;
            }
            if ("com.samsung.systemui.power.action.ACTION_BATTERY_SAFE_MODE".equals(action)) {
                final SecPowerNotificationWarnings secPowerNotificationWarnings9 = SecPowerNotificationWarnings.this;
                secPowerNotificationWarnings9.getClass();
                Log.d("PowerUI.Notification", "showSafeModePopUp()");
                if (secPowerNotificationWarnings9.mSafeModeDialog == null) {
                    AlertDialog popupDialog2 = secPowerNotificationWarnings9.getPopupDialog(10);
                    secPowerNotificationWarnings9.mSafeModeDialog = popupDialog2;
                    if (popupDialog2 == null) {
                        return;
                    }
                    popupDialog2.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.12
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            SecPowerNotificationWarnings.this.mSafeModeDialog = null;
                        }
                    });
                    secPowerNotificationWarnings9.mSafeModeDialog.show();
                    return;
                }
                return;
            }
            if ("com.sec.factory.app.factorytest.FTA_ON".equals(action)) {
                SecPowerNotificationWarnings.this.mFTAMode = true;
                return;
            }
            if ("com.sec.factory.app.factorytest.FTA_OFF".equals(action)) {
                SecPowerNotificationWarnings.this.mFTAMode = false;
                return;
            }
            if ("com.samsung.systemui.power.action.ACTION_AUTOMATIC_TEST_MODE_ON".equals(action)) {
                SecPowerNotificationWarnings secPowerNotificationWarnings10 = SecPowerNotificationWarnings.this;
                secPowerNotificationWarnings10.mAutomaticTestMode = true;
                AlertDialog alertDialog = secPowerNotificationWarnings10.mSlowByChargerConnectionInfoDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    secPowerNotificationWarnings10.mSlowByChargerConnectionInfoDialog = null;
                    return;
                }
                return;
            }
            if ("com.samsung.systemui.power.action.ACTION_AUTOMATIC_TEST_MODE_OFF".equals(action)) {
                SecPowerNotificationWarnings.this.mAutomaticTestMode = false;
                return;
            }
            if ("com.samsung.android.systemui.action.DELETED_CHARGING_NOTI".equals(action)) {
                SecPowerNotificationWarnings.this.mDoNotShowChargingNotice = true;
            } else if ("com.samsung.android.sm.ACTION_OPTIMIZED_CHARGING_NOTI_DISMISSED".equals(action)) {
                SecPowerNotificationWarnings secPowerNotificationWarnings11 = SecPowerNotificationWarnings.this;
                secPowerNotificationWarnings11.cancelNotification(10);
                secPowerNotificationWarnings11.mOptimizationChargingFinishTime = "";
            }
        }

        private Receiver() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.power.SecPowerNotificationWarnings$4] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.power.SecPowerNotificationWarnings$5] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.power.SecPowerNotificationWarnings$9] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.power.SecPowerNotificationWarnings$18] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.power.SecPowerNotificationWarnings$21] */
    public SecPowerNotificationWarnings(Context context) {
        Receiver receiver = new Receiver(this, 0);
        this.mIsInCall = false;
        this.mBatterySettings = new Intent("com.samsung.android.sm.ACTION_BATTERY").setFlags(478150656);
        this.mSmartManagerBatterySettings = new Intent("com.samsung.android.sm.ACTION_POWER_MODE_SETTINGS").setFlags(478150656);
        this.mShowChargingNotice = false;
        this.mIsMaximumProtectionEnabled = false;
        this.mMaximumThresholdValue = 0;
        this.mSuperFastCharger = 0;
        this.mChargingType = 0;
        this.mOldChargingType = 0;
        this.mOldBatteryLevel = 0;
        this.mChargingTime = 0L;
        this.mBatteryLevel = 0;
        this.mBatteryStatus = 1;
        this.mBatteryHealth = 1;
        this.mIsWaterDetected = false;
        this.mIsHiccupState = false;
        this.mIsTemperatureHiccupState = false;
        this.mIsUnintentionalPopupShowing = false;
        this.mFTAMode = false;
        this.mAutomaticTestMode = false;
        this.mHandlerWrapper = null;
        this.mDoNotShowChargingNotice = false;
        this.mBatteryHealthInterruptionTask = new Runnable() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.4
            @Override // java.lang.Runnable
            public final void run() {
                SecPowerNotificationWarnings.this.showBatteryHealthInterruptionWarning();
            }
        };
        this.mTemperatureLimitAlertTask = new Runnable() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.5
            @Override // java.lang.Runnable
            public final void run() {
                SecPowerNotificationWarnings.this.showBatteryHealthInterruptionPopUp();
            }
        };
        this.mOverheatShutdownTask = new Runnable() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.9
            @Override // java.lang.Runnable
            public final void run() {
                SharedPreferences sharedPreferences = SecPowerNotificationWarnings.this.mContext.getSharedPreferences("com.android.systemui.power_overheat_shutdown_happened", 0);
                if (sharedPreferences != null) {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putBoolean("OverheatShutdownHappened", true);
                    edit.commit();
                }
                Log.d("PowerUI.Notification", "overheat shutdown - productType = in_house");
                SecPowerNotificationWarnings.this.mContext.sendBroadcast(new Intent("com.android.systemui.power.action.ACTION_REQUEST_SHUTDOWN"));
            }
        };
        this.mWaterProtectionAlertTask = new Runnable() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.18
            @Override // java.lang.Runnable
            public final void run() {
                SecPowerNotificationWarnings secPowerNotificationWarnings = SecPowerNotificationWarnings.this;
                secPowerNotificationWarnings.showWaterProtectionAlertDialog(secPowerNotificationWarnings.mIsWaterDetected);
            }
        };
        this.mUsbDamageProtectionAlertTask = new Runnable() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.21
            @Override // java.lang.Runnable
            public final void run() {
                SecPowerNotificationWarnings.this.showUsbDamageProtectionAlertDialog();
            }
        };
        this.mContext = context;
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mVibrator = (Vibrator) context.getSystemService("vibrator");
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mNotificationPlayer = new NotificationPlayer("PowerUI.Notification");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("PNW.batteryInfo");
        intentFilter.addAction("PNW.powerMode");
        intentFilter.addAction("PNW.abnormalPadNoThanks");
        intentFilter.addAction("PNW.dismissedWarning");
        intentFilter.addAction("com.samsung.systemui.power.action.ACTION_BATTERY_LOW_CLOSE_BUTTON");
        intentFilter.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        intentFilter.addAction("com.samsung.systemui.power.action.ACTION_BATTERY_OVER_HEAT");
        intentFilter.addAction("com.samsung.systemui.power.action.ACTION_BATTERY_SAFE_MODE");
        intentFilter.addAction("com.sec.factory.app.factorytest.FTA_ON");
        intentFilter.addAction("com.sec.factory.app.factorytest.FTA_OFF");
        intentFilter.addAction("com.samsung.systemui.power.action.ACTION_AUTOMATIC_TEST_MODE_ON");
        intentFilter.addAction("com.samsung.systemui.power.action.ACTION_AUTOMATIC_TEST_MODE_OFF");
        if (PowerUiRune.POLICY_CHARGING_NOTIFICATION) {
            intentFilter.addAction("com.samsung.android.systemui.action.DELETED_CHARGING_NOTI");
        }
        if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION) {
            intentFilter.addAction("com.samsung.android.sm.ACTION_OPTIMIZED_CHARGING_NOTI_DISMISSED");
        }
        SecPowerNotificationWarnings secPowerNotificationWarnings = SecPowerNotificationWarnings.this;
        secPowerNotificationWarnings.mContext.registerReceiverAsUser(receiver, UserHandle.ALL, intentFilter, "android.permission.DEVICE_POWER", secPowerNotificationWarnings.mHandler, 2);
        final ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_LOW_POWER_MODE), false, new ContentObserver(handler) { // from class: com.android.systemui.power.SecPowerNotificationWarnings.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                int i = Settings.Global.getInt(contentResolver, SettingsHelper.INDEX_LOW_POWER_MODE, 0);
                SecPowerNotificationWarnings secPowerNotificationWarnings2 = SecPowerNotificationWarnings.this;
                int i2 = secPowerNotificationWarnings2.mCurrentBatteryMode;
                if (i != 0) {
                    secPowerNotificationWarnings2.mCurrentBatteryMode = 1;
                } else {
                    secPowerNotificationWarnings2.mCurrentBatteryMode = 0;
                }
                if (secPowerNotificationWarnings2.mWarning && i2 != secPowerNotificationWarnings2.mCurrentBatteryMode) {
                    secPowerNotificationWarnings2.updateNotification();
                }
                Intent intent = new Intent("com.samsung.android.sm.action.ACTION_ACTIVE_TILE_SERVICE");
                intent.setPackage(PowerUiConstants.DC_PACKAGE_NAME);
                intent.putExtra("extra_type", "power_mode");
                try {
                    SecPowerNotificationWarnings.this.mContext.startService(intent);
                } catch (IllegalStateException | SecurityException e) {
                    Log.e("PowerUI.Notification", "Error", e);
                }
            }
        });
        int i = Settings.Global.getInt(contentResolver, SettingsHelper.INDEX_LOW_POWER_MODE, 0);
        if (SemEmergencyManager.isEmergencyMode(context)) {
            this.mCurrentBatteryMode = 2;
        } else if (i != 0) {
            this.mCurrentBatteryMode = 1;
        } else {
            this.mCurrentBatteryMode = 0;
        }
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
            SemWindowManager.getInstance().registerFoldStateListener(new SemWindowManager.FoldStateListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.2
                public final void onFoldStateChanged(boolean z) {
                    if (SecPowerNotificationWarnings.this.mIsUnintentionalPopupShowing) {
                        Log.d("PowerUI.Notification", "Fold state has been changed so dismiss UnintentionalLcdOnView");
                        SecPowerNotificationWarnings.this.dismissUnintentionalLcdOnWindow();
                    }
                }

                public final void onTableModeChanged(boolean z) {
                }
            }, (Handler) null);
        }
    }

    public final void cancelNotification(int i) {
        PowerUiNotification notification2 = PowerUiNotificationFactory.getNotification(i, this.mContext);
        if (notification2 == null) {
            ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "Illegal notification type : ", "PowerUI.Notification");
            return;
        }
        notification2.mNotificationManager = this.mNotificationManager;
        notification2.setInformation(getSecBatteryStatsSnapshot());
        notification2.dismissNotification();
    }

    public final synchronized void dismissUnintentionalLcdOnWindow() {
        try {
            if (this.mIsUnintentionalPopupShowing) {
                Log.d("PowerUI.Notification", "dismissUnintentionalLcdOnWindow");
                UnintentionalLcdOnView unintentionalLcdOnView = this.mUnintentionalLcdOnWindow;
                if (unintentionalLcdOnView != null) {
                    this.mWindowManager.removeView(unintentionalLcdOnView);
                    this.mUnintentionalLcdOnWindow.setVisibility(8);
                    UnintentionalLcdOnView unintentionalLcdOnView2 = this.mUnintentionalLcdOnWindow;
                    if (unintentionalLcdOnView2.mTouchListener == this) {
                        unintentionalLcdOnView2.mTouchListener = null;
                    }
                    this.mUnintentionalLcdOnWindow = null;
                    this.mIsUnintentionalPopupShowing = false;
                }
                this.mWindowManager = null;
                this.mUnintentionalLCDOnWindowLp = null;
                String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final AlertDialog getPopupDialog(int i) {
        PowerUiDialog incompleteChargerDialog;
        Context context = this.mContext;
        switch (i) {
            case 2:
                incompleteChargerDialog = new IncompleteChargerDialog(context);
                break;
            case 3:
                incompleteChargerDialog = new BatterySwellingLowTempDialog(context);
                break;
            case 4:
                incompleteChargerDialog = new IncompatibleChargerDialog(context);
                break;
            case 5:
                incompleteChargerDialog = new PdChargerAlertDialog(context);
                break;
            case 6:
                incompleteChargerDialog = new BatteryHealthInterruptionDialog(context);
                break;
            case 7:
                incompleteChargerDialog = new OverheatDialog(context);
                break;
            case 8:
                incompleteChargerDialog = new WillOverheatShutdownDialog(context);
                break;
            case 9:
                incompleteChargerDialog = new HappenedOverheatShutdownDialog(context);
                break;
            case 10:
                incompleteChargerDialog = new SafeModeDialog(context);
                break;
            case 11:
                incompleteChargerDialog = new WirelessFodDialog(context);
                break;
            case 12:
                incompleteChargerDialog = new WaterProtectionDialog(context);
                break;
            case 13:
                incompleteChargerDialog = new UsbDamageProtectionDialog(context);
                break;
            case 14:
                incompleteChargerDialog = new HvChargerEnableDialog(context);
                break;
            default:
                incompleteChargerDialog = null;
                break;
        }
        if (incompleteChargerDialog == null) {
            ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "Illegal dialog type : ", "PowerUI.Notification");
            return null;
        }
        incompleteChargerDialog.setInformation(getSecBatteryStatsSnapshot());
        if (incompleteChargerDialog.checkCondition()) {
            return incompleteChargerDialog.getDialog();
        }
        return null;
    }

    public final SecBatterySnapshot getSecBatteryStatsSnapshot() {
        return new SecBatterySnapshot(this.mBatteryLevel, this.mCurrentBatteryMode, this.mChargingTime, this.mChargingType, this.mBatteryHealth, this.mIsHiccupState, this.mAutomaticTestMode, this.mOptimizationChargingFinishTime);
    }

    public final void playPowerSound(int i) {
        PowerUiSound powerUiSound = PowerUiSoundFactory.getPowerUiSound(i, this.mContext);
        powerUiSound.mNotificationPlayer = this.mNotificationPlayer;
        powerUiSound.mVibrator = this.mVibrator;
        powerUiSound.mIsInCall = this.mIsInCall;
        powerUiSound.mChargingType = this.mSuperFastCharger;
        powerUiSound.mAudioManager = this.mAudioManager;
        powerUiSound.playSoundAndVibration();
    }

    public final void restoreScreenTimeOutIfNeeded() {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("powerui_prefs", 0);
        if (sharedPreferences != null) {
            String string = sharedPreferences.getString("ScreenTimeOut", ":");
            try {
                String[] split = string.split(":");
                if (split.length >= 2) {
                    int parseInt = Integer.parseInt(split[0]);
                    int parseInt2 = Integer.parseInt(split[1]);
                    Log.d("PowerUI.Notification", "restoreScreenTimeOut : saved value = " + string + ", screenTimeOut = " + parseInt + " userId = " + parseInt2);
                    if (parseInt > 30000) {
                        Log.i("PowerUI.Notification", "restoreScreenTimeOut - restore user value to : " + parseInt);
                        Settings.System.putIntForUser(this.mContext.getContentResolver(), "screen_off_timeout", parseInt, parseInt2);
                    }
                }
            } catch (Error | Exception e) {
                Log.e("PowerUI.Notification", "restoreScreenTimeOutIfNeeded ", e);
            }
            sharedPreferences.edit().remove("ScreenTimeOut").commit();
        }
    }

    public final void showAdaptiveProtectionNotification(String str) {
        Context context = this.mContext;
        String str2 = "";
        try {
            Date parse = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(str);
            if (parse != null) {
                str2 = DateFormat.getTimeFormat(context).format(Long.valueOf(parse.getTime()));
            }
        } catch (ParseException e) {
            Log.w("PowerUi.PowerUtils", "ParseException", e);
        }
        this.mOptimizationChargingFinishTime = str2;
        showNotification(10);
    }

    public final void showBatteryHealthInterruptionPopUp() {
        int i = this.mBatteryHealth;
        Handler handler = this.mHandler;
        if (i == 8) {
            handler.removeCallbacks(this.mBatteryHealthInterruptionTask);
        }
        handler.removeCallbacks(this.mTemperatureLimitAlertTask);
        if (this.mBatteryHealthInterruptionDialog != null) {
            Log.d("PowerUI.Notification", "mBatteryHealthInterruptionDialog is not null");
            int i2 = this.mBatteryHealth;
            int i3 = i2 == 6 ? R.string.battery_health_interruption_by_terminal_open_text : i2 == 8 ? DeviceType.isTablet() ? R.string.battery_health_interruption_by_limit_high_temperature_text_tablet : R.string.battery_health_interruption_by_limit_high_temperature_text_phone : 0;
            if (i3 == 0) {
                Log.e("PowerUI.Notification", "status is NotCharging but health is wrong value");
                return;
            }
            AlertDialog alertDialog = this.mBatteryHealthInterruptionDialog;
            String string = this.mContext.getString(i3);
            AlertController alertController = alertDialog.mAlert;
            alertController.mMessage = string;
            TextView textView = alertController.mMessageView;
            if (textView != null) {
                textView.setText(string);
            }
        } else {
            AlertDialog popupDialog = getPopupDialog(6);
            this.mBatteryHealthInterruptionDialog = popupDialog;
            if (popupDialog == null) {
                return;
            }
            popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.3
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings = SecPowerNotificationWarnings.this;
                    secPowerNotificationWarnings.mBatteryHealthInterruptionDialog = null;
                    if (secPowerNotificationWarnings.mBatteryStatus == 4) {
                        secPowerNotificationWarnings.mHandler.postDelayed(secPowerNotificationWarnings.mBatteryHealthInterruptionTask, 60000L);
                    }
                }
            });
            this.mBatteryHealthInterruptionDialog.show();
        }
        if (this.mBatteryHealth != 8) {
            playPowerSound(4);
        } else {
            playPowerSound(5);
            handler.postDelayed(this.mTemperatureLimitAlertTask, 1600L);
        }
    }

    public final void showBatteryHealthInterruptionWarning() {
        Log.d("PowerUI.Notification", "showBatteryHealthInterruptionWarning()");
        if (PowerUtils.isShutdownOn(this.mContext)) {
            Log.d("PowerUI.Notification", "don't show Battery health interruption warning while Shutdown is ON");
            return;
        }
        int i = this.mBatteryHealth;
        if (i == 8 || i == 6) {
            if (i == 6) {
                cancelNotification(5);
            }
            showBatteryHealthInterruptionPopUp();
            return;
        }
        if (this.mBatteryHealthInterruptionDialog != null) {
            Handler handler = this.mHandler;
            handler.removeCallbacks(this.mBatteryHealthInterruptionTask);
            handler.removeCallbacks(this.mTemperatureLimitAlertTask);
            AlertDialog alertDialog = this.mBatteryHealthInterruptionDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
        }
        showNotification(5);
    }

    public final void showChargingNotice() {
        int i;
        AlertDialog alertDialog;
        AlertDialog alertDialog2;
        boolean z = this.mIsMaximumProtectionEnabled;
        int i2 = this.mMaximumThresholdValue;
        long j = this.mChargingTime;
        this.mIsMaximumProtectionEnabled = BatteryProtectionUtil.isMaximumProtectionEnabled(this.mContext);
        this.mMaximumThresholdValue = Settings.Global.getInt(this.mContext.getContentResolver(), "battery_protection_threshold", Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE);
        if (PowerUiRune.BATTERY_CHARGING_ESTIMATE_TIME) {
            Intent registerReceiver = this.mContext.registerReceiver(null, new IntentFilter("com.samsung.server.BatteryService.action.SEC_BATTERY_REMAINING_CHARGING_TIME_CHANGED"));
            this.mChargingTime = registerReceiver != null ? registerReceiver.getLongExtra("remaining_charging_time", 0L) : 0L;
        } else {
            this.mChargingTime = 0L;
        }
        if (this.mOldChargingType == 9 && this.mChargingType != 9 && (alertDialog2 = this.mSlowByChargerConnectionInfoDialog) != null) {
            alertDialog2.dismiss();
            this.mSlowByChargerConnectionInfoDialog = null;
        }
        if (!PowerUiRune.SPECIFIC_POWER_REQUEST_BY_VZW && this.mOldChargingType == 8 && this.mChargingType != 8 && (alertDialog = this.mSlowByChargerConnectionInfoDialog) != null) {
            alertDialog.dismiss();
            this.mSlowByChargerConnectionInfoDialog = null;
        }
        if (PowerUiRune.POLICY_CHARGING_NOTIFICATION) {
            if (this.mDoNotShowChargingNotice && z == this.mIsMaximumProtectionEnabled && i2 == this.mMaximumThresholdValue && (i = this.mChargingType) != 8 && i != 9) {
                Log.d("PowerUI.Notification", "showChargingNotice - User swipe charging notification, so return!");
                return;
            }
            this.mDoNotShowChargingNotice = false;
        }
        if (this.mShowChargingNotice && this.mOldBatteryLevel == this.mBatteryLevel && this.mOldChargingType == this.mChargingType && j == this.mChargingTime && z == this.mIsMaximumProtectionEnabled && i2 == this.mMaximumThresholdValue) {
            Log.d("PowerUI.Notification", "showChargingNotice - There is no change charging status, so return!");
            return;
        }
        Slog.d("PowerUI.Notification", "showChargingNotice - oldBatteryLevel : " + this.mOldBatteryLevel + " , BatteryLevel : " + this.mBatteryLevel + " / oldChargingType : " + this.mOldChargingType + " , ChargingType : " + this.mChargingType + " / oldChargingTime : " + j + ", ChargingTime : " + this.mChargingTime + " / oldMaximumEnabled : " + z + " , MaximumEnabled : " + this.mIsMaximumProtectionEnabled + " / oldMaximumThreshold : " + i2 + " , MaximumThreshold : " + this.mMaximumThresholdValue);
        int i3 = this.mOldChargingType;
        showNotification(2);
        int i4 = this.mChargingType;
        if (i4 == 8) {
            if (i3 != 8) {
                Log.w("PowerUI.Notification", "Show slow charging toast");
                Context context = this.mContext;
                Toast.makeText(context, context.getString(R.string.battery_slow_charging_toast_text), 0).show();
                turnOnScreen();
            }
        } else if (i4 == 9 && i3 != 9) {
            Log.i("PowerUI.Notification", "showIncompleteChargerConnectionInfoPopUp()");
            if (this.mSlowByChargerConnectionInfoDialog == null) {
                AlertDialog popupDialog = getPopupDialog(2);
                this.mSlowByChargerConnectionInfoDialog = popupDialog;
                if (popupDialog != null) {
                    popupDialog.setOnDismissListener(new SecPowerNotificationWarnings$$ExternalSyntheticLambda1(this, 1));
                    this.mSlowByChargerConnectionInfoDialog.show();
                    turnOnScreen();
                }
            }
        }
        this.mShowChargingNotice = true;
    }

    public final void showChargingTypeSwitchedNotice(boolean z) {
        if (SemGateConfig.isGateEnabled()) {
            Log.d("PowerUI.Notification", "GATE tool is running so don't show Charging type switched notice");
            return;
        }
        String string = z ? this.mContext.getString(R.string.battery_cable_charging_from_wireless_to_cable_text) : this.mContext.getString(R.string.battery_wireless_charging_from_cable_to_wireless_text);
        Log.d("PowerUI.Notification", "showChargingTypeSwitchedToast()");
        if (this.mConnectedChargerChangedToast == null) {
            this.mConnectedChargerChangedToast = Toast.makeText(this.mContext, "", 0);
        }
        Toast toast = this.mConnectedChargerChangedToast;
        if (toast != null) {
            toast.setText(string);
            this.mConnectedChargerChangedToast.setDuration(0);
            this.mConnectedChargerChangedToast.show();
        } else {
            Toast.makeText(this.mContext, string, 0).show();
        }
        this.mDoNotShowChargingNotice = false;
    }

    public final void showIncompatibleChargerNotice() {
        if (PowerUtils.isShutdownOn(this.mContext)) {
            Log.d("PowerUI.Notification", "don't show Incompatible charging warning while Shutdown is ON");
            return;
        }
        if (this.mFTAMode) {
            Log.d("PowerUI.Notification", "FTA Mode is ON so don't show Incompatible charging warning");
            return;
        }
        Log.d("PowerUI.Notification", "showIncompatibleChargerWarning()");
        this.mWarning = false;
        updateNotification();
        restoreScreenTimeOutIfNeeded();
        showNotification(3);
        Log.d("PowerUI.Notification", "showIncompatibleChargerNotification()");
        if (this.mIncompatibleChargerDialog == null) {
            AlertDialog popupDialog = getPopupDialog(4);
            this.mIncompatibleChargerDialog = popupDialog;
            if (popupDialog == null) {
                return;
            }
            popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.13
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    SecPowerNotificationWarnings.this.mIncompatibleChargerDialog = null;
                }
            });
            this.mIncompatibleChargerDialog.show();
        }
    }

    public final void showLowBatteryWarning(boolean z) {
        if (PowerUtils.isShutdownOn(this.mContext)) {
            Log.d("PowerUI.Notification", "Shutdown is ON");
            return;
        }
        if (this.mFTAMode && this.mBucket != -2) {
            Log.d("PowerUI.Notification", "FTA Mode is ON and Not critical Low battery");
            return;
        }
        Log.i("PowerUI.Notification", "show low battery warning: level=" + this.mBatteryLevel + " [" + this.mBucket + "] playSound=" + z);
        this.mPlaySound = z;
        this.mWarning = true;
        updateNotification();
        if (this.mBucket == -2) {
            SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("powerui_prefs", 0);
            int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_off_timeout", PluginLockInstancePolicy.DISABLED_BY_SUB_USER, ActivityManager.semGetCurrentUser());
            if (intForUser <= 30000) {
                Log.d("PowerUI.Notification", "screen time out is shorter than default value, so we do nothing !!");
                return;
            }
            if (sharedPreferences != null) {
                String str = intForUser + ":" + ActivityManager.semGetCurrentUser();
                Log.d("PowerUI.Notification", "1.backupAndResetScreenTimeOut backup screen timeout : " + intForUser + " value : " + str);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("ScreenTimeOut", str);
                edit.commit();
                Log.d("PowerUI.Notification", "2.backupAndResetScreenTimeOut set default timeout!!");
                Settings.System.putIntForUser(this.mContext.getContentResolver(), "screen_off_timeout", PluginLockInstancePolicy.DISABLED_BY_SUB_USER, -2);
            }
        }
    }

    public final void showNotification(int i) {
        PowerUiNotification notification2 = PowerUiNotificationFactory.getNotification(i, this.mContext);
        if (notification2 == null) {
            ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "Illegal notification type : ", "PowerUI.Notification");
            return;
        }
        notification2.mNotificationManager = this.mNotificationManager;
        notification2.setInformation(getSecBatteryStatsSnapshot());
        notification2.showNotification();
    }

    public final void showUnintentionalLcdOnNotice() {
        boolean z = ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class))).mShowing;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("showUnintentionallyLcdOnNotice() - isLock ? ", "PowerUI.Notification", z);
        synchronized (this) {
            try {
                if (this.mIsUnintentionalPopupShowing) {
                    return;
                }
                Log.d("PowerUI.Notification", "showUnintentionalLcdOnWindow");
                if (z) {
                    this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
                }
                if (this.mUnintentionalLCDOnWindowLp == null) {
                    this.mUnintentionalLCDOnWindowLp = new WindowManager.LayoutParams(-1, -1, 2021, 256, -3);
                }
                boolean z2 = LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && SemWindowManager.getInstance().isFolded();
                if (z2) {
                    this.mUnintentionalLCDOnWindowLp.layoutInDisplayCutoutMode = 3;
                } else {
                    WindowManager.LayoutParams layoutParams = this.mUnintentionalLCDOnWindowLp;
                    layoutParams.screenOrientation = 1;
                    layoutParams.layoutInDisplayCutoutMode = 1;
                }
                this.mUnintentionalLCDOnWindowLp.setFitInsetsSides(0);
                WindowManager.LayoutParams layoutParams2 = this.mUnintentionalLCDOnWindowLp;
                layoutParams2.inputFeatures = 2 | layoutParams2.inputFeatures;
                if (!z) {
                    layoutParams2.semSetScreenTimeout(10000L);
                    this.mUnintentionalLCDOnWindowLp.semSetScreenDimDuration(0L);
                }
                this.mUnintentionalLCDOnWindowLp.setTitle("UnintentionalLcdOn");
                if (this.mWindowManager == null || this.mUnintentionalLcdOnWindow == null) {
                    Log.d("PowerUI.Notification", "showUnintentionalLcdOnWindow addview - isCover = " + z2);
                    if (z2) {
                        Context subDisplayContext = PowerUtils.getSubDisplayContext(this.mContext);
                        this.mWindowManager = (WindowManager) subDisplayContext.getSystemService("window");
                        this.mUnintentionalLcdOnWindow = (UnintentionalLcdOnView) View.inflate(subDisplayContext, R.layout.sc_unintentional_lcd_on_window, null);
                    } else {
                        this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
                        this.mUnintentionalLcdOnWindow = (UnintentionalLcdOnView) View.inflate(this.mContext, R.layout.unintentional_lcd_on_window, null);
                    }
                    this.mUnintentionalLcdOnWindow.setCoverState(z2);
                    this.mWindowManager.addView(this.mUnintentionalLcdOnWindow, this.mUnintentionalLCDOnWindowLp);
                    this.mUnintentionalLcdOnWindow.mTouchListener = this;
                }
                this.mIsUnintentionalPopupShowing = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void showUsbDamageProtectionAlertDialog() {
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("show UsbDamageProtectionAlertDialog - mIsTemperatureHiccupState = "), this.mIsTemperatureHiccupState, "PowerUI.Notification");
        Handler handler = this.mHandler;
        handler.removeCallbacks(this.mUsbDamageProtectionAlertTask);
        if (this.mUsbDamageProtectionPartialWakeLock == null) {
            PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "PowerUI.Notification USB damage");
            this.mUsbDamageProtectionPartialWakeLock = newWakeLock;
            newWakeLock.acquire();
        }
        if (this.mUsbDamageProtectionAlertDialog == null) {
            AlertDialog popupDialog = getPopupDialog(13);
            this.mUsbDamageProtectionAlertDialog = popupDialog;
            if (popupDialog == null) {
                return;
            }
            popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.19
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings = SecPowerNotificationWarnings.this;
                    secPowerNotificationWarnings.mUsbDamageProtectionAlertDialog = null;
                    secPowerNotificationWarnings.stopPowerSound(1600);
                }
            });
            this.mUsbDamageProtectionAlertDialog.show();
            this.mUsbDamageProtectionAlertDialog.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.20
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings = SecPowerNotificationWarnings.this;
                    if (secPowerNotificationWarnings.mIsTemperatureHiccupState) {
                        return;
                    }
                    secPowerNotificationWarnings.mContext.sendBroadcast(new Intent("com.samsung.systemui.power.action.WATER_POPUP_DISMISSED"));
                    SecPowerNotificationWarnings secPowerNotificationWarnings2 = SecPowerNotificationWarnings.this;
                    secPowerNotificationWarnings2.mHandler.removeCallbacks(secPowerNotificationWarnings2.mUsbDamageProtectionAlertTask);
                    PowerManager.WakeLock wakeLock = SecPowerNotificationWarnings.this.mUsbDamageProtectionPartialWakeLock;
                    if (wakeLock != null) {
                        wakeLock.release();
                        SecPowerNotificationWarnings.this.mUsbDamageProtectionPartialWakeLock = null;
                    }
                    AlertDialog alertDialog = SecPowerNotificationWarnings.this.mUsbDamageProtectionAlertDialog;
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                }
            });
            turnOnScreen();
        }
        playPowerSound(7);
        handler.postDelayed(this.mUsbDamageProtectionAlertTask, 1600L);
    }

    public final void showWaterProtectionAlertDialog(boolean z) {
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("show WaterProtectionAlertDialog - isWaterDetected = ", " mIsWaterDetected = ", z);
        m.append(this.mIsWaterDetected);
        m.append(" mIsHiccupState = ");
        m.append(this.mIsHiccupState);
        m.append("mWaterProtectionAlertDialog : ");
        m.append(this.mWaterProtectionAlertDialog);
        Log.d("PowerUI.Notification", m.toString());
        this.mIsWaterDetected = z;
        Handler handler = this.mHandler;
        handler.removeCallbacks(this.mWaterProtectionAlertTask);
        if (this.mWaterProtectionPartialWakeLock == null) {
            PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "PowerUI.Notification");
            this.mWaterProtectionPartialWakeLock = newWakeLock;
            newWakeLock.acquire();
        }
        if (this.mWaterProtectionAlertDialog == null) {
            if (!z && !this.mIsHiccupState) {
                Log.w("PowerUI.Notification", "Wrong executed, so return");
                PowerManager.WakeLock wakeLock = this.mWaterProtectionPartialWakeLock;
                if (wakeLock != null) {
                    wakeLock.release();
                    this.mWaterProtectionPartialWakeLock = null;
                    return;
                }
                return;
            }
            AlertDialog popupDialog = getPopupDialog(12);
            this.mWaterProtectionAlertDialog = popupDialog;
            if (popupDialog == null) {
                return;
            }
            popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.16
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    Log.d("PowerUI.Notification", "mWaterProtectionAlertDialog onDismiss");
                    SecPowerNotificationWarnings secPowerNotificationWarnings = SecPowerNotificationWarnings.this;
                    secPowerNotificationWarnings.mWaterProtectionAlertDialog = null;
                    secPowerNotificationWarnings.stopPowerSound(1600);
                }
            });
            this.mWaterProtectionAlertDialog.show();
            if (this.mIsHiccupState) {
                this.mWaterProtectionAlertDialog.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.17
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        if (SecPowerNotificationWarnings.this.mIsHiccupState) {
                            return;
                        }
                        Log.d("PowerUI.Notification", "mWaterProtectionAlertDialog onClick");
                        SecPowerNotificationWarnings secPowerNotificationWarnings = SecPowerNotificationWarnings.this;
                        secPowerNotificationWarnings.mHandler.removeCallbacks(secPowerNotificationWarnings.mWaterProtectionAlertTask);
                        SecPowerNotificationWarnings.this.mContext.sendBroadcast(new Intent("com.samsung.systemui.power.action.WATER_POPUP_DISMISSED"));
                        PowerManager.WakeLock wakeLock2 = SecPowerNotificationWarnings.this.mWaterProtectionPartialWakeLock;
                        if (wakeLock2 != null) {
                            wakeLock2.release();
                            SecPowerNotificationWarnings.this.mWaterProtectionPartialWakeLock = null;
                        }
                        AlertDialog alertDialog = SecPowerNotificationWarnings.this.mWaterProtectionAlertDialog;
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                    }
                });
            }
            turnOnScreen();
        }
        playPowerSound(6);
        handler.postDelayed(this.mWaterProtectionAlertTask, 1600L);
    }

    public final void showWillOverheatShutdownWarning() {
        if (PowerUtils.isShutdownOn(this.mContext)) {
            Log.d("PowerUI.Notification", "don't show Overheat shutdown warning while Shutdown is ON");
            return;
        }
        Log.d("PowerUI.Notification", "showWillOverheatShutdownWarning()");
        Log.d("PowerUI.Notification", "dismissOverheatShutdownHappenedPopUp()");
        AlertDialog alertDialog = this.mOverheatShutdownHappenedDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (this.mWillOverheatShutdownWarningDialog == null) {
            AlertDialog popupDialog = getPopupDialog(8);
            this.mWillOverheatShutdownWarningDialog = popupDialog;
            if (popupDialog == null) {
                return;
            }
            popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.6
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    SecPowerNotificationWarnings.this.mWillOverheatShutdownWarningDialog = null;
                }
            });
            this.mWillOverheatShutdownWarningDialog.show();
            this.mWillOverheatShutdownWarningDialog.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertDialog alertDialog2 = SecPowerNotificationWarnings.this.mWillOverheatShutdownWarningDialog;
                    if (alertDialog2 != null) {
                        alertDialog2.dismiss();
                    }
                }
            });
            turnOnScreen();
        }
    }

    public final void stopPowerSound(int i) {
        PowerUiSound powerUiSound = PowerUiSoundFactory.getPowerUiSound(i, this.mContext);
        NotificationPlayer notificationPlayer = this.mNotificationPlayer;
        powerUiSound.mNotificationPlayer = notificationPlayer;
        powerUiSound.mVibrator = this.mVibrator;
        powerUiSound.mIsInCall = this.mIsInCall;
        powerUiSound.mChargingType = this.mSuperFastCharger;
        powerUiSound.mAudioManager = this.mAudioManager;
        if (notificationPlayer != null) {
            notificationPlayer.stop();
        }
        Vibrator vibrator = powerUiSound.mVibrator;
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    public final void turnOnScreen() {
        PowerManager powerManager = this.mPowerManager;
        if (powerManager == null) {
            Log.e("PowerUI.Notification", "turnOnScreen : fail to get PowerManager reference");
        } else {
            powerManager.wakeUp(SystemClock.uptimeMillis(), this.mContext.getOpPackageName());
        }
    }

    public final void updateNotification() {
        Notification.Builder builder;
        Slog.d("PowerUI.Notification", "updateNotification mWarning = " + this.mWarning + " mPlaySound = " + this.mPlaySound);
        if (!this.mWarning) {
            cancelNotification(1);
            return;
        }
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mCustomSdkMonitor;
        if (customSdkMonitor == null || (customSdkMonitor.mHideNotificationMessages & 1) != 0) {
            Log.d("PowerUI.Notification", "We do not show warning notifications due to KNOX.");
            return;
        }
        PowerUiNotification notification2 = PowerUiNotificationFactory.getNotification(1, this.mContext);
        if (notification2 == null) {
            Log.e("PowerUI.Notification", "Illegal notification type : 1");
            builder = null;
        } else {
            notification2.mNotificationManager = this.mNotificationManager;
            notification2.setInformation(getSecBatteryStatsSnapshot());
            builder = notification2.getBuilder();
        }
        if (builder == null) {
            return;
        }
        builder.setWhen(this.mWarningTriggerTimeMs);
        if (this.mPlaySound) {
            playPowerSound(3);
        }
        builder.setOnlyAlertOnce(!this.mPlaySound);
        this.mPlaySound = false;
        Notification build = builder.build();
        NotificationManager notificationManager = this.mNotificationManager;
        UserHandle userHandle = UserHandle.ALL;
        notificationManager.cancelAsUser("low_battery", 2, userHandle);
        this.mNotificationManager.notifyAsUser("low_battery", 3, build, userHandle);
    }
}
