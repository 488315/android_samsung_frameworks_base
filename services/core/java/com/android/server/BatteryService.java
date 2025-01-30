package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.BroadcastOptions;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.hardware.health.HealthInfo;
import android.hardware.input.InputManager;
import android.metrics.LogMaker;
import android.os.BatteryManagerInternal;
import android.os.BatteryProperty;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBatteryPropertiesRegistrar;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.sysprop.PowerProperties;
import android.telephony.TelephonyManager;
import android.util.EventLog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.IBatteryStats;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.server.battery.BattFeatures;
import com.android.server.battery.BattInfoManager;
import com.android.server.battery.BattUtils;
import com.android.server.battery.BatteryLogger;
import com.android.server.battery.sleepcharging.SleepChargingManager;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.health.HealthInfoCallback;
import com.android.server.health.HealthServiceWrapper;
import com.android.server.health.Utils;
import com.android.server.lights.LightsManager;
import com.android.server.lights.LogicalLight;
import com.android.server.am.BatteryStatsService;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.ShutdownThread;
import com.android.server.power.Slog;
import com.att.iqi.lib.IQIManager;
import com.att.iqi.lib.metrics.p019hw.HW0E;
import com.att.iqi.lib.metrics.p019hw.HW12;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import vendor.samsung.hardware.health.SehHealthInfo;

/* loaded from: classes.dex */
public final class BatteryService extends SystemService {
    public static final int DEFAULT_PROTECTION_THRESHOLD_LEVEL;
    public static final boolean EUREKA_PROJECT;
    public static final boolean FEATURE_FULL_BATTERY_CYCLE;
    public static final boolean FEATURE_SUPPORTED_DAILY_BOARD;
    public static final boolean FEATURE_WIRELESS_FAST_CHARGER_CONTROL;
    public static final String PRODUCT_NAME;
    public static final String TAG = "BatteryService";
    public final String HEALTH_INSTANCE_VENDOR;
    public boolean isVideoCall;
    public ActivityManagerInternal mActivityManagerInternal;
    public boolean mActivityManagerReady;
    public final int mAdaptiveFastChargingOffset;
    public boolean mAdaptiveFastChargingSettingsEnable;
    public AdaptiveFastChargingSettingsObserver mAdaptiveFastChargingSettingsObserver;
    public String mAfcDisableSysFs;
    public BroadcastReceiver mAudioModeChangeReceiver;
    public BattInfoManager mBattInfoManager;
    public BroadcastReceiver mBattSlateModeControlReceiver;
    public int mBatteryCapacity;
    public Bundle mBatteryChangedOptions;
    public boolean mBatteryInputSuspended;
    public boolean mBatteryLevelCritical;
    public boolean mBatteryLevelLow;
    public ArrayDeque mBatteryLevelsEventQueue;
    public long mBatteryMaxCurrent;
    public long mBatteryMaxTemp;
    public int mBatteryNearlyFullLevel;
    public Bundle mBatteryOptions;
    public BatteryPropertiesRegistrar mBatteryPropertiesRegistrar;
    public final IBatteryStats mBatteryStats;
    public int mBatteryType;
    public int mBatteryUsageSinceLastAsocUpdate;
    public BinderService mBinderService;
    public boolean mBootCompleted;
    public BroadcastReceiver mBootCompletedReceiver;
    public Handler mCallHandler;
    public HandlerThread mCallHandlerThread;
    public int mChargeStartLevel;
    public long mChargeStartTime;
    public final Context mContext;
    public int mCriticalBatteryLevel;
    public long mCurrentBatteryUsage;
    public Calendar mCurrentCalendar;
    public DateChangedReceiver mDateChangedReceiver;
    public BroadcastReceiver mDexReceiver;
    public int mDischargeStartLevel;
    public long mDischargeStartTime;
    public boolean mEnableIqi;
    public BroadcastReceiver mFastWirelessAutoModeReceiver;
    public long mFullBatteryDuration;
    public long mFullBatteryStartTime;
    public boolean mFullCapacityEnable;
    public FullCapacityEnableSettingsObserver mFullCapacityEnableSettingsObserver;
    public final Handler mHandler;
    public final Handler mHandlerForBatteryInfoBackUp;
    public HealthInfo mHealthInfo;
    public HealthServiceWrapper mHealthServiceWrapper;
    public BroadcastReceiver mHiccupControlReceiver;
    public BroadcastReceiver mIntentReceiver;
    public int mInvalidCharger;
    public boolean mIsAuthQrEqualsEfs;
    public boolean mIsFirstIntentSended;
    public boolean mIsHiccupPopupShowing;
    public boolean mIsSbpFgQrEqualsEfs;
    public boolean mIsSkipActionBatteryChanged;
    public boolean mIsUnlockedBootCompleted;
    public boolean mIsWirelessTxSupported;
    public int mLastBatteryChargeType;
    public int mLastBatteryCurrentEvent;
    public int mLastBatteryCurrentNow;
    public int mLastBatteryCycleCount;
    public int mLastBatteryEvent;
    public boolean mLastBatteryEventWaterInConnector;
    public int mLastBatteryHealth;
    public int mLastBatteryHighVoltageCharger;
    public int mLastBatteryLevel;
    public long mLastBatteryLevelChangedSentMs;
    public boolean mLastBatteryLevelCritical;
    public int mLastBatteryOnline;
    public boolean mLastBatteryPowerSharingOnline;
    public boolean mLastBatteryPresent;
    public int mLastBatteryStatus;
    public int mLastBatteryTemperature;
    public int mLastBatteryVoltage;
    public int mLastCharingState;
    public int mLastDeterioration;
    public int mLastInvalidCharger;
    public int mLastLowBatteryWarningLevel;
    public int mLastMaxChargingCurrent;
    public int mLastMaxChargingVoltage;
    public int mLastPlugType;
    public int mLastSecPlugTypeSummary;
    public final SehHealthInfo mLastSehHealthInfo;
    public boolean mLastTxEventRxConnected;
    public boolean mLastTxEventTxEnabled;
    public int mLastWcTxId;
    public boolean mLastWirelessBackPackChargingStatus;
    public boolean mLastWirelessChargingStatus;
    public boolean mLastWirelessPinDetected;
    public int mLastWirelessPowerSharingExternelEvent;
    public int mLastWirelessPowerSharingTxEvent;
    public boolean mLastchargerPogoOnline;
    public int mLatestWirelessChargingMode;
    public Led mLed;
    public boolean mLedChargingSettingsEnable;
    public boolean mLedLowBatterySettingsEnable;
    public LedSettingsObserver mLedSettingsObserver;
    public boolean mLifeExtender;
    public LifeExtenderSettingsObserver mLifeExtenderSettingsObserver;
    public final Object mLock;
    public final Object mLockBatteryInfoBackUp;
    public int mLongBatteryRetryCnt;
    public int mLowBatteryCloseWarningLevel;
    public int mLowBatteryWarningLevel;
    public int mLtcHighSocDuration;
    public int mLtcHighThreshold;
    public int mLtcReleaseThreshold;
    public String mManufactureDate;
    public MetricsLogger mMetricsLogger;
    public boolean mNotifyWirelessEnabled;
    public boolean mPassThroughSettingsEnable;
    public PassThroughSettingsObserver mPassThroughSettingsObserver;
    public int mPlugType;
    public int mPogoCondition;
    public int mPogoDockState;
    public Bundle mPowerOptions;
    public int mProtectBatteryMode;
    public int mProtectionThreshold;
    public int mRefreshRateModeSetting;
    public RefreshRateModeSettingsObserver mRefreshRateModeSettingsObserver;
    public BroadcastReceiver mRequestOtgChargeBlockReceiver;
    public String mRfCalDate;
    public final Runnable mSaveBatteryMaxCurrentRunnable;
    public final Runnable mSaveBatteryMaxTempRunnable;
    public final Runnable mSaveBatteryUsageRunnable;
    public long mSavedBatteryAsoc;
    public int mSavedBatteryBeginningDate;
    public long mSavedBatteryMaxCurrent;
    public long mSavedBatteryMaxTemp;
    public long mSavedBatteryUsage;
    public long mSavedBatteryUsageForSbpFg;
    public int mSavedDiffWeek;
    public long mSavedFullBatteryDuration;
    public boolean mScreenOn;
    public int mSecPlugTypeSummary;
    public SehHealthInfo mSehHealthInfo;
    public SemInputDeviceManager mSemInputDeviceManager;
    public boolean mSentLowBatteryBroadcast;
    public int mSequence;
    public boolean mShouldCheckFirstUseDateRegularly;
    public int mShutdownBatteryTemperature;
    public Handler mSkipActionBatteryChangedHandler;
    public BroadcastReceiver mSleepChargingDismissReceiver;
    public Handler mSleepChargingHandler;
    public SleepChargingManager mSleepChargingManager;
    public final int mSuperFastChargingOffset;
    public boolean mSuperFastChargingSettingsEnable;
    public SuperFastChargingSettingsObserver mSuperFastChargingSettingsObserver;
    public int mTxBatteryLimit;
    public TxBatteryLimitSettingsObserver mTxBatteryLimitSettingsObserver;
    public final Runnable mUpdateBatteryAsocRunnable;
    public final Runnable mUpdateBatteryUsageExtenderRunnable;
    public final Runnable mUpdateBatteryUsageFullCapacityEnableRunnable;
    public boolean mUpdatesStopped;
    public boolean mWasUsedWirelessFastChargerPreviously;
    public WcParamInfoSettingsObserver mWcParamInfoSettingsObserver;
    public final int mWcParamOffset;
    public int mWcTxId;
    public final int mWirelessFastChargingOffset;
    public boolean mWirelessFastChargingSettingsEnable;
    public WirelessFastChargingSettingsObserver mWirelessFastChargingSettingsObserver;
    public BroadcastReceiver mWirelessPowerSharingReceiver;

    /* renamed from: tm */
    public TelephonyManager f1615tm;
    public static final String TAG_SS = "[SS]" + BatteryService.class.getSimpleName();
    public static final String[] DUMPSYS_ARGS = {"--checkin", "--unplugged"};
    public static final String[] ADAPTIVE_FAST_CHARGING_DISABLE_SYSFS_PATHS = {"/sys/class/sec/switch/afc_disable", "sys/class/sec/afc/afc_disable"};
    public static final boolean FEATURE_HICCUP_CONTROL = isFileSupported("/sys/class/sec/switch/hiccup");
    public static final String PACKAGE_DEVICE_CARE = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME");
    public static String ACTION_ENTER_DESK_MODE = "com.samsung.android.desktopmode.action.ENTER_DESKTOP_MODE";
    public static String ACTION_EXIT_DESK_MODE = "com.samsung.android.desktopmode.action.EXIT_DESKTOP_MODE";
    public static final boolean FEATURE_SAVE_BATTERY_CYCLE = isFileSupported("/sys/class/power_supply/battery/battery_cycle");

    static {
        String trim = SystemProperties.get("ro.product.vendor.device", "NONE").trim();
        PRODUCT_NAME = trim;
        boolean z = trim.startsWith("e1") || trim.startsWith("e2") || trim.startsWith("e3");
        EUREKA_PROJECT = z;
        FEATURE_FULL_BATTERY_CYCLE = z || BattFeatures.isSupport("BFSU");
        FEATURE_SUPPORTED_DAILY_BOARD = isSupportedDailyBoard();
        DEFAULT_PROTECTION_THRESHOLD_LEVEL = Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE;
        FEATURE_WIRELESS_FAST_CHARGER_CONTROL = isFileSupported("/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl");
    }

    public final void activateSleepChargingManager(boolean z) {
        String str = TAG_SS;
        Slog.m76i(str, "[activateSleepChargingManager]activate:" + z);
        if (z) {
            if (this.mSleepChargingManager != null) {
                Slog.m74e(str, "[activateSleepChargingManager]activated multiple times => ignored");
                return;
            }
            createSleepChargingHandler();
            SleepChargingManager sleepChargingManager = new SleepChargingManager(this.mContext, this.mSleepChargingHandler, this.mProtectionThreshold);
            this.mSleepChargingManager = sleepChargingManager;
            HealthInfo healthInfo = this.mHealthInfo;
            if (healthInfo != null) {
                sleepChargingManager.updateChargingInfo(this.mPlugType, this.mChargeStartTime, healthInfo.batteryLevel, healthInfo.batteryChargeTimeToFullNowSeconds);
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.android.sm.ACTION_OPTIMIZED_CHARGING_NOTI_DISMISSED");
            createSleepChargingDismissReceiver();
            this.mContext.registerReceiver(this.mSleepChargingDismissReceiver, intentFilter, "com.samsung.permission.WRITE_SM_DATA", null);
            return;
        }
        BroadcastReceiver broadcastReceiver = this.mSleepChargingDismissReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mSleepChargingDismissReceiver = null;
        }
        SleepChargingManager sleepChargingManager2 = this.mSleepChargingManager;
        if (sleepChargingManager2 != null) {
            sleepChargingManager2.end();
            this.mSleepChargingManager = null;
        }
        Handler handler = this.mSleepChargingHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.mSleepChargingHandler = null;
        }
    }

    public final void createSleepChargingHandler() {
        Slog.m72d(TAG_SS, "[createSleepChargingHandler]");
        this.mSleepChargingHandler = new Handler(Looper.myLooper()) { // from class: com.android.server.BatteryService.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                String str;
                String str2;
                String str3;
                Slog.m76i(BatteryService.TAG_SS, "[bs_handleMessage]msg:" + message.what);
                if (BatteryService.this.mProtectBatteryMode != 4) {
                    Slog.m74e(BatteryService.TAG_SS, "[bs_handleMessage]Currently, Not Battery Adaptive Protect Mode");
                    return;
                }
                int i = message.what;
                if (i != 1) {
                    str = "";
                    if (i == 2) {
                        BatteryService.this.setSleepCharging(false);
                        r1 = message.arg1 == 1;
                        str2 = "off";
                    } else if (i == 3) {
                        str = (String) message.obj;
                        str2 = "update";
                    } else {
                        str3 = "";
                        Intent intent = new Intent("com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING");
                        intent.setFlags(16777216);
                        intent.putExtra("sleep_charging_event", str);
                        intent.putExtra("sleep_charging_finish_time", str3);
                        intent.putExtra("is_sleep_charging_complete_success", r1);
                        Slog.m76i(BatteryService.TAG_SS, "[bs_handleMessage]extraEvent:" + str + " ,extraSleepChargingFinishTime:" + str3 + " ,isSleepChargingCompleteSuccess:" + r1);
                        BatteryService.sendBroadcastToExplicitPackage(BatteryService.this.mContext, intent, "com.android.systemui");
                        BatteryService.sendBroadcastToExplicitPackage(BatteryService.this.mContext, intent, BatteryService.PACKAGE_DEVICE_CARE);
                    }
                } else {
                    BatteryService.this.setSleepCharging(true);
                    str = (String) message.obj;
                    str2 = "on";
                }
                String str4 = str;
                str = str2;
                str3 = str4;
                Intent intent2 = new Intent("com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING");
                intent2.setFlags(16777216);
                intent2.putExtra("sleep_charging_event", str);
                intent2.putExtra("sleep_charging_finish_time", str3);
                intent2.putExtra("is_sleep_charging_complete_success", r1);
                Slog.m76i(BatteryService.TAG_SS, "[bs_handleMessage]extraEvent:" + str + " ,extraSleepChargingFinishTime:" + str3 + " ,isSleepChargingCompleteSuccess:" + r1);
                BatteryService.sendBroadcastToExplicitPackage(BatteryService.this.mContext, intent2, "com.android.systemui");
                BatteryService.sendBroadcastToExplicitPackage(BatteryService.this.mContext, intent2, BatteryService.PACKAGE_DEVICE_CARE);
            }
        };
    }

    public final void createSleepChargingDismissReceiver() {
        Slog.m72d(TAG_SS, "[createSleepChargingDismissReceiver]");
        this.mSleepChargingDismissReceiver = new BroadcastReceiver() { // from class: com.android.server.BatteryService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Slog.m76i(BatteryService.TAG_SS, "[SleepChargingDismissReceiver_onReceive]action:" + action);
                if (BatteryService.this.mSleepChargingManager != null) {
                    BatteryService.this.mSleepChargingManager.updateDismiss();
                }
            }
        };
    }

    public class FullCapacityEnableSettingsObserver extends ContentObserver {
        public FullCapacityEnableSettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.FullCapacityEnableSettingsObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (BatteryService.this.mLock) {
                        ContentResolver contentResolver = BatteryService.this.mContext.getContentResolver();
                        if (BatteryService.this.mLifeExtender) {
                            Slog.m72d(BatteryService.TAG, "!@battery life extender used before!");
                            BatteryService.this.mLifeExtender = false;
                            BatteryService.this.mHandlerForBatteryInfoBackUp.post(BatteryService.this.mUpdateBatteryUsageExtenderRunnable);
                            Settings.System.putIntForUser(contentResolver, "protect_battery", 0, -2);
                        }
                        if (BattFeatures.SUPPORT_ECO_BATTERY) {
                            int i = BatteryService.this.mProtectBatteryMode;
                            BatteryService.this.mProtectBatteryMode = Settings.Global.getInt(contentResolver, "protect_battery", 0);
                            String str = i + " => " + BatteryService.this.mProtectBatteryMode;
                            Slog.m76i(BatteryService.TAG_SS, "Battery Protect Mode Changed: " + str);
                            BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "Battery Protect Mode Changed", str);
                            BatteryService.this.writeProtectBatteryValues();
                            if (BatteryService.this.mIsUnlockedBootCompleted) {
                                if (BatteryService.this.mProtectBatteryMode == 4) {
                                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.FullCapacityEnableSettingsObserver.1.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            BatteryService.this.activateSleepChargingManager(true);
                                        }
                                    });
                                } else {
                                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.FullCapacityEnableSettingsObserver.1.2
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            BatteryService.this.activateSleepChargingManager(false);
                                        }
                                    });
                                }
                            } else {
                                Slog.m79w(BatteryService.TAG_SS, "Battery Protect Mode Changed before UnlockedBootCompleted => ignored");
                            }
                        } else {
                            BatteryService.this.mFullCapacityEnable = Settings.Global.getInt(contentResolver, "protect_battery", 0) == 1;
                            Slog.m72d(BatteryService.TAG_SS, "!@mFullCapacityEnable Settings = " + BatteryService.this.mFullCapacityEnable);
                            BatteryService.this.mHandlerForBatteryInfoBackUp.post(BatteryService.this.mUpdateBatteryUsageFullCapacityEnableRunnable);
                        }
                    }
                }
            });
        }
    }

    public final void setCallWirelessPowerSharingExternelEvent(boolean z) {
        if (z) {
            TelephonyManager telephonyManager = this.f1615tm;
            if (telephonyManager != null) {
                this.isVideoCall = telephonyManager.semIsVideoCall();
                Slog.m72d(TAG, "isVideoCall: " + this.isVideoCall);
            }
            Slog.m72d(TAG, "call start, isVideoCall: " + this.isVideoCall);
            if (this.isVideoCall) {
                setWirelessPowerSharingExternelEventInternal(1, 1);
                return;
            } else {
                setWirelessPowerSharingExternelEventInternal(4, 4);
                return;
            }
        }
        Slog.m72d(TAG, "call end, isVideoCall: " + this.isVideoCall);
        if (this.isVideoCall) {
            setWirelessPowerSharingExternelEventInternal(1, 0);
        } else {
            setWirelessPowerSharingExternelEventInternal(4, 0);
        }
        this.isVideoCall = false;
    }

    public final void startCallThread() {
        HandlerThread handlerThread = new HandlerThread("CallThread");
        this.mCallHandlerThread = handlerThread;
        handlerThread.start();
        this.mCallHandler = new Handler(this.mCallHandlerThread.getLooper()) { // from class: com.android.server.BatteryService.10
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i != 0) {
                    if (i == 1) {
                        BatteryService.this.setCallWirelessPowerSharingExternelEvent(false);
                        return;
                    } else if (i != 2) {
                        return;
                    }
                }
                BatteryService.this.setCallWirelessPowerSharingExternelEvent(true);
            }
        };
    }

    public class LedSettingsObserver extends ContentObserver {
        public LedSettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            synchronized (BatteryService.this.mLock) {
                ContentResolver contentResolver = BatteryService.this.mContext.getContentResolver();
                boolean z2 = true;
                BatteryService.this.mLedChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_charing", 1, -2) == 1;
                BatteryService batteryService = BatteryService.this;
                if (Settings.System.getIntForUser(contentResolver, "led_indicator_low_battery", 1, -2) != 1) {
                    z2 = false;
                }
                batteryService.mLedLowBatterySettingsEnable = z2;
                Slog.m72d(BatteryService.TAG, "Led Charging Settings = " + BatteryService.this.mLedChargingSettingsEnable);
                Slog.m72d(BatteryService.TAG, "Led Low Battery Settings = " + BatteryService.this.mLedLowBatterySettingsEnable);
                BatteryService.this.mLed.updateLightsLocked();
            }
        }
    }

    public class AdaptiveFastChargingSettingsObserver extends ContentObserver {
        public AdaptiveFastChargingSettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.AdaptiveFastChargingSettingsObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (BatteryService.this.mLock) {
                        ContentResolver contentResolver = BatteryService.this.mContext.getContentResolver();
                        BatteryService batteryService = BatteryService.this;
                        boolean z2 = true;
                        if (Settings.System.getIntForUser(contentResolver, "adaptive_fast_charging", 1, -2) != 1) {
                            z2 = false;
                        }
                        batteryService.mAdaptiveFastChargingSettingsEnable = z2;
                        Slog.m72d(BatteryService.TAG, "AdaptiveFastCharging Settings = " + BatteryService.this.mAdaptiveFastChargingSettingsEnable);
                        BatteryService batteryService2 = BatteryService.this;
                        batteryService2.setAdaptiveFastCharging(batteryService2.mAdaptiveFastChargingSettingsEnable);
                    }
                }
            });
        }
    }

    public class SuperFastChargingSettingsObserver extends ContentObserver {
        public SuperFastChargingSettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.SuperFastChargingSettingsObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (BatteryService.this.mLock) {
                        ContentResolver contentResolver = BatteryService.this.mContext.getContentResolver();
                        BatteryService batteryService = BatteryService.this;
                        boolean z2 = true;
                        if (Settings.System.getIntForUser(contentResolver, "super_fast_charging", 1, -2) != 1) {
                            z2 = false;
                        }
                        batteryService.mSuperFastChargingSettingsEnable = z2;
                        Slog.m72d(BatteryService.TAG, "SuperFastCharging Settings = " + BatteryService.this.mSuperFastChargingSettingsEnable);
                        BatteryService batteryService2 = BatteryService.this;
                        batteryService2.setSuperFastCharging(batteryService2.mSuperFastChargingSettingsEnable);
                    }
                }
            });
        }
    }

    public class PassThroughSettingsObserver extends ContentObserver {
        public PassThroughSettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.PassThroughSettingsObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (BatteryService.this.mLock) {
                        BatteryService.this.mPassThroughSettingsEnable = Settings.System.getIntForUser(BatteryService.this.mContext.getContentResolver(), "pass_through", 0, -2) == 1;
                        Slog.m72d(BatteryService.TAG, "PassThrough Settings = " + BatteryService.this.mPassThroughSettingsEnable);
                        BatteryService batteryService = BatteryService.this;
                        batteryService.setPassThrough(batteryService.mPassThroughSettingsEnable);
                    }
                }
            });
        }
    }

    public class WirelessFastChargingSettingsObserver extends ContentObserver {
        public WirelessFastChargingSettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.WirelessFastChargingSettingsObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (BatteryService.this.mLock) {
                        ContentResolver contentResolver = BatteryService.this.mContext.getContentResolver();
                        BatteryService batteryService = BatteryService.this;
                        boolean z2 = true;
                        if (Settings.System.getIntForUser(contentResolver, "wireless_fast_charging", 1, -2) != 1) {
                            z2 = false;
                        }
                        batteryService.mWirelessFastChargingSettingsEnable = z2;
                        Slog.m72d(BatteryService.TAG, "WirelessFastCharging Settings = " + BatteryService.this.mWirelessFastChargingSettingsEnable);
                        BatteryService batteryService2 = BatteryService.this;
                        batteryService2.setWirelessFastCharging(batteryService2.mWirelessFastChargingSettingsEnable);
                    }
                }
            });
        }
    }

    public class RefreshRateModeSettingsObserver extends ContentObserver {
        public RefreshRateModeSettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.RefreshRateModeSettingsObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (BatteryService.this.mLock) {
                        BatteryService.this.mRefreshRateModeSetting = Settings.Secure.getIntForUser(BatteryService.this.mContext.getContentResolver(), "refresh_rate_mode", 0, -2);
                        Slog.m72d(BatteryService.TAG, "RefreshRateMode Setting = " + BatteryService.this.mRefreshRateModeSetting);
                        BatteryService batteryService = BatteryService.this;
                        batteryService.setRefreshRateMode(batteryService.mRefreshRateModeSetting);
                    }
                }
            });
        }
    }

    public class LifeExtenderSettingsObserver extends ContentObserver {
        public LifeExtenderSettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.LifeExtenderSettingsObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (BatteryService.this.mLock) {
                        BatteryService.this.mLifeExtender = Settings.System.getIntForUser(BatteryService.this.mContext.getContentResolver(), "protect_battery", 0, -2) == 1;
                        Slog.m72d(BatteryService.TAG, "!@mLifeExtender Settings changed = " + BatteryService.this.mLifeExtender);
                        BatteryService.this.mHandlerForBatteryInfoBackUp.post(BatteryService.this.mUpdateBatteryUsageExtenderRunnable);
                    }
                }
            });
        }
    }

    public class TxBatteryLimitSettingsObserver extends ContentObserver {
        public TxBatteryLimitSettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.TxBatteryLimitSettingsObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (BatteryService.this.mLock) {
                        BatteryService.this.mTxBatteryLimit = Settings.System.getIntForUser(BatteryService.this.mContext.getContentResolver(), "tx_battery_limit", 30, -2);
                        Slog.m72d(BatteryService.TAG, "Tx Battery Limit Settings = " + BatteryService.this.mTxBatteryLimit);
                        BatteryService batteryService = BatteryService.this;
                        batteryService.setWirelessPowerSharingTxBatteryLimit(batteryService.mTxBatteryLimit);
                    }
                }
            });
        }
    }

    public final void writeProtectBatteryValues() {
        Slog.m76i(TAG_SS, "writeProtectBatteryValues:" + this.mProtectBatteryMode);
        int i = this.mProtectBatteryMode;
        if (i == 0) {
            fileWriteInt("/sys/class/power_supply/battery/batt_full_capacity", 100);
            fileWriteInt("/efs/Battery/batt_full_capacity", 100);
            fileWriteInt("/sys/class/power_supply/battery/batt_soc_rechg", 0);
            fileWriteInt("/efs/Battery/batt_soc_rechg", 0);
            return;
        }
        if (i == 1) {
            fileWriteString("/sys/class/power_supply/battery/batt_full_capacity", this.mProtectionThreshold + " OPTION");
            fileWriteInt("/efs/Battery/batt_full_capacity", this.mProtectionThreshold);
            fileWriteInt("/sys/class/power_supply/battery/batt_soc_rechg", 0);
            fileWriteInt("/efs/Battery/batt_soc_rechg", 0);
            return;
        }
        if (i != 2) {
            if (i == 3 || i == 4) {
                fileWriteInt("/sys/class/power_supply/battery/batt_full_capacity", 100);
                fileWriteInt("/efs/Battery/batt_full_capacity", 100);
                fileWriteInt("/sys/class/power_supply/battery/batt_soc_rechg", 1);
                fileWriteInt("/efs/Battery/batt_soc_rechg", 1);
                return;
            }
            return;
        }
        fileWriteString("/sys/class/power_supply/battery/batt_full_capacity", this.mProtectionThreshold + " HIGHSOC");
        fileWriteInt("/sys/class/power_supply/battery/batt_soc_rechg", 0);
        fileWriteInt("/efs/Battery/batt_soc_rechg", 0);
    }

    public final void setSleepCharging(boolean z) {
        Slog.m76i(TAG_SS, "[setSleepCharging]on:" + z);
        if (z) {
            fileWriteString("/sys/class/power_supply/battery/batt_full_capacity", this.mProtectionThreshold + " SLEEP");
            return;
        }
        fileWriteInt("/sys/class/power_supply/battery/batt_full_capacity", 100);
    }

    public class WcParamInfoSettingsObserver extends ContentObserver {
        public WcParamInfoSettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.WcParamInfoSettingsObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (BatteryService.this.mLock) {
                        String readFromFile = BatteryService.this.readFromFile("/sys/class/power_supply/battery/wc_param_info");
                        int intForUser = Settings.System.getIntForUser(BatteryService.this.mContext.getContentResolver(), "wireless_wc_write", 0, -2);
                        if (BatteryService.this.mWcParamOffset != -1 && readFromFile != null && intForUser == 1) {
                            Slog.m72d(BatteryService.TAG, "wireless_wc_write onchanged");
                            BatteryService.this.setWcParamInfo(readFromFile);
                        }
                    }
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BatteryService(Context context) {
        super(context);
        this.mIsHiccupPopupShowing = false;
        this.isVideoCall = false;
        this.mLock = new Object();
        this.mLockBatteryInfoBackUp = new Object();
        this.mLastSehHealthInfo = new SehHealthInfo();
        this.mSequence = 1;
        this.mLastPlugType = -1;
        this.mLastSecPlugTypeSummary = -1;
        this.mSentLowBatteryBroadcast = false;
        this.mBatteryChangedOptions = BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeferralPolicy(2).toBundle();
        this.mPowerOptions = BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeliveryGroupMatchingKey("android", "android.intent.action.ACTION_POWER_CONNECTED").setDeferralPolicy(2).toBundle();
        this.mBatteryOptions = BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeliveryGroupMatchingKey("android", "android.intent.action.BATTERY_OKAY").setDeferralPolicy(2).toBundle();
        this.mBootCompleted = false;
        this.mIsUnlockedBootCompleted = false;
        this.mScreenOn = true;
        this.mLedChargingSettingsEnable = true;
        this.mLedLowBatterySettingsEnable = true;
        this.mAdaptiveFastChargingSettingsEnable = true;
        this.mSuperFastChargingSettingsEnable = true;
        this.mPassThroughSettingsEnable = false;
        this.mPogoDockState = 0;
        this.mPogoCondition = 0;
        this.mLastBatteryEventWaterInConnector = false;
        this.mLastTxEventTxEnabled = true;
        this.mLastTxEventRxConnected = false;
        this.mIsWirelessTxSupported = false;
        this.mBatteryCapacity = 280000;
        this.mActivityManagerReady = false;
        this.mSavedBatteryMaxTemp = -1L;
        this.mSavedBatteryMaxCurrent = -1L;
        this.mSavedBatteryAsoc = -1L;
        this.mSavedBatteryUsage = -1L;
        this.mSavedBatteryUsageForSbpFg = -1L;
        this.mSavedFullBatteryDuration = -1L;
        this.mBatteryMaxTemp = -1L;
        this.mBatteryMaxCurrent = -1L;
        this.mCurrentBatteryUsage = 0L;
        this.mBatteryUsageSinceLastAsocUpdate = 0;
        this.mFullBatteryStartTime = -1L;
        this.mFullBatteryDuration = 0L;
        this.mLongBatteryRetryCnt = 0;
        this.mSavedDiffWeek = -1;
        this.mLifeExtender = false;
        this.mFullCapacityEnable = false;
        this.mLastDeterioration = 0;
        this.mIsFirstIntentSended = false;
        this.mLastWirelessPinDetected = false;
        this.mNotifyWirelessEnabled = false;
        this.HEALTH_INSTANCE_VENDOR = "default";
        this.mSavedBatteryBeginningDate = 0;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        this.mSemInputDeviceManager = null;
        this.mLatestWirelessChargingMode = 0;
        this.mWcTxId = 0;
        this.mLastWcTxId = -1;
        this.mProtectionThreshold = 80;
        this.mShouldCheckFirstUseDateRegularly = false;
        this.mIsSkipActionBatteryChanged = false;
        this.mEnableIqi = false;
        this.mBootCompletedReceiver = new BroadcastReceiver() { // from class: com.android.server.BatteryService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                Slog.m76i(BatteryService.TAG_SS, "[mBootCompletedReceiver]action:" + action);
                if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                    BatteryService.this.mIsUnlockedBootCompleted = true;
                    if (BatteryService.this.mProtectBatteryMode == 4) {
                        BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                BatteryService.this.activateSleepChargingManager(true);
                            }
                        });
                    } else {
                        Slog.m74e(BatteryService.TAG_SS, "Currently, Not Battery Adaptive Protect Mode");
                    }
                }
            }
        };
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.BatteryService.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action.equals("android.intent.action.SCREEN_ON")) {
                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            synchronized (BatteryService.this.mLock) {
                                BatteryService.this.mScreenOn = true;
                                if (PowerManagerUtil.SEC_FEATURE_BATTERY_NOTIFY_SCREEN_STATE) {
                                    BatteryService.this.sendScreenState();
                                }
                                BatteryService.this.mLed.updateLightsLocked();
                            }
                        }
                    });
                    return;
                }
                if (action.equals("android.intent.action.SCREEN_OFF")) {
                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.4.2
                        @Override // java.lang.Runnable
                        public void run() {
                            synchronized (BatteryService.this.mLock) {
                                BatteryService.this.mScreenOn = false;
                                if (PowerManagerUtil.SEC_FEATURE_BATTERY_NOTIFY_SCREEN_STATE) {
                                    BatteryService.this.sendScreenState();
                                }
                                BatteryService.this.mLed.updateLightsLocked();
                            }
                        }
                    });
                    return;
                }
                if (action.equals("android.intent.action.USER_SWITCHED")) {
                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.4.3
                        @Override // java.lang.Runnable
                        public void run() {
                            ContentResolver contentResolver = BatteryService.this.mContext.getContentResolver();
                            synchronized (BatteryService.this.mLock) {
                                boolean z = true;
                                BatteryService.this.mLedChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_charing", 1, -2) == 1;
                                BatteryService.this.mLedLowBatterySettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_low_battery", 1, -2) == 1;
                                BatteryService batteryService = BatteryService.this;
                                if (Settings.System.getIntForUser(contentResolver, "show_wireless_charger_menu", 0, -2) != 1) {
                                    z = false;
                                }
                                batteryService.mWasUsedWirelessFastChargerPreviously = z;
                                Slog.m72d(BatteryService.TAG, "ACTION_USER_SWITCHED: Led Charging: " + BatteryService.this.mLedChargingSettingsEnable + " Led Low Battery:" + BatteryService.this.mLedLowBatterySettingsEnable + " wfc: " + BatteryService.this.mWasUsedWirelessFastChargerPreviously);
                            }
                            synchronized (BatteryService.this.mLock) {
                                BatteryService.this.mLed.updateLightsLocked();
                                if (PowerManagerUtil.SEC_FEATURE_USE_AFC) {
                                    BatteryService.this.updateAdaptiveFastChargingSetting(contentResolver);
                                }
                            }
                        }
                    });
                    return;
                }
                if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                    BatteryService.this.mHandlerForBatteryInfoBackUp.post(BatteryService.this.mSaveBatteryUsageRunnable);
                    synchronized (BatteryService.this.mLock) {
                        if (BatteryService.FEATURE_FULL_BATTERY_CYCLE) {
                            BatteryService.this.logFullBatteryDurationLocked(true);
                        }
                    }
                    if (BatteryService.this.mEnableIqi) {
                        SystemProperties.set("persist.sys.shutdown_received", "true");
                    }
                }
            }
        };
        this.mBattSlateModeControlReceiver = new BroadcastReceiver() { // from class: com.android.server.BatteryService.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals("com.sec.intent.action.BATT_SLATE_MODE_CHANGE")) {
                    BatteryService.fileWriteInt("/sys/class/power_supply/battery/batt_slate_mode", intent.getIntExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 0));
                }
            }
        };
        this.mRequestOtgChargeBlockReceiver = new BroadcastReceiver() { // from class: com.android.server.BatteryService.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals("android.intent.action.REQUEST_OTG_CHARGE_BLOCK")) {
                    final boolean booleanExtra = intent.getBooleanExtra("OTG_CHARGE_BLOCK", false);
                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (BatteryService.this.setOTGEnableDisable(booleanExtra)) {
                                Slog.m72d(BatteryService.TAG, "success to set otgEnable as " + booleanExtra);
                                BatteryService.this.sendOTGIntentLocked();
                                return;
                            }
                            Slog.m72d(BatteryService.TAG, "fail to set otgEnable");
                        }
                    });
                }
            }
        };
        this.mHiccupControlReceiver = new BroadcastReceiver() { // from class: com.android.server.BatteryService.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action.equals("com.samsung.systemui.power.action.WATER_POPUP_DISMISSED")) {
                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (BatteryService.this.setHiccupDisable()) {
                                Slog.m72d(BatteryService.TAG, "success to disable hiccup");
                            } else {
                                Slog.m72d(BatteryService.TAG, "fail to disable hiccup");
                            }
                            BatteryService.this.mIsHiccupPopupShowing = false;
                        }
                    });
                } else if (action.equals("com.samsung.systemui.power.action.USB_DAMAGE_POPUP_SHOW")) {
                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.7.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Slog.m72d(BatteryService.TAG, "notify the misc event");
                            BatteryService.this.setResponseHiccupEvent();
                            BatteryService.this.mIsHiccupPopupShowing = true;
                        }
                    });
                }
            }
        };
        this.mWirelessPowerSharingReceiver = new BroadcastReceiver() { // from class: com.android.server.BatteryService.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                final boolean booleanExtra;
                if (!intent.getAction().equals("com.samsung.android.sm.ACTION_WIRELESS_POWER_SHARING") || BatteryService.this.mLastTxEventTxEnabled == (booleanExtra = intent.getBooleanExtra("enable", false))) {
                    return;
                }
                BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (BatteryService.this.setWirelessPowerSharing(booleanExtra)) {
                            Slog.m72d(BatteryService.TAG, "success to set wirelssPowerSharingEnable as " + booleanExtra);
                            return;
                        }
                        Slog.m72d(BatteryService.TAG, "fail to disable wirelssPowerSharingEnable");
                    }
                });
            }
        };
        this.mDexReceiver = new BroadcastReceiver() { // from class: com.android.server.BatteryService.9
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action.equals(BatteryService.ACTION_ENTER_DESK_MODE)) {
                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.9.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Slog.m72d(BatteryService.TAG, "Dex Start");
                            BatteryService.this.setWirelessPowerSharingExternelEventInternal(2, 2);
                        }
                    });
                } else if (action.equals(BatteryService.ACTION_EXIT_DESK_MODE)) {
                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.9.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Slog.m72d(BatteryService.TAG, "Dex Exit");
                            BatteryService.this.setWirelessPowerSharingExternelEventInternal(2, 0);
                        }
                    });
                }
            }
        };
        this.mAudioModeChangeReceiver = new BroadcastReceiver() { // from class: com.android.server.BatteryService.11
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals("android.samsung.media.action.AUDIO_MODE")) {
                    final int intExtra = intent.getIntExtra("android.samsung.media.extra.AUDIO_MODE", 0);
                    Slog.m72d(BatteryService.TAG, "audio_mode : " + intExtra);
                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.11.1
                        @Override // java.lang.Runnable
                        public void run() {
                            int i = intExtra;
                            if (i == 2) {
                                BatteryService.this.mCallHandler.sendMessageDelayed(BatteryService.this.mCallHandler.obtainMessage(0), 500L);
                            } else if (i == 0) {
                                BatteryService.this.mCallHandler.removeMessages(0);
                                BatteryService.this.mCallHandler.sendMessage(BatteryService.this.mCallHandler.obtainMessage(1));
                            } else if (i == 3) {
                                BatteryService.this.mCallHandler.removeMessages(0);
                                BatteryService.this.mCallHandler.sendMessage(BatteryService.this.mCallHandler.obtainMessage(2));
                            }
                        }
                    });
                }
            }
        };
        this.mFastWirelessAutoModeReceiver = new BroadcastReceiver() { // from class: com.android.server.BatteryService.12
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals("com.samsung.android.sm.ACTION_FAST_WIRELESS_CHARGING_CONTROL")) {
                    final boolean booleanExtra = intent.getBooleanExtra("write", false);
                    BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.12.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Slog.m72d(BatteryService.TAG, "fastWirelessAutoModeEnable : " + booleanExtra);
                            BatteryService.this.setWirelessFastCharging(booleanExtra ^ true);
                        }
                    });
                }
            }
        };
        this.mWirelessFastChargingSettingsEnable = true;
        this.mWasUsedWirelessFastChargerPreviously = false;
        this.mRefreshRateModeSetting = 0;
        this.mSaveBatteryUsageRunnable = new Runnable() { // from class: com.android.server.BatteryService.38
            /* JADX WARN: Removed duplicated region for block: B:21:0x00a3 A[Catch: all -> 0x0113, TryCatch #0 {, blocks: (B:13:0x0021, B:15:0x0031, B:17:0x003d, B:19:0x009d, B:21:0x00a3, B:23:0x00af, B:25:0x00bb, B:26:0x00c5, B:28:0x00cd, B:29:0x00d5, B:31:0x00d9, B:32:0x0105, B:33:0x0111, B:36:0x0049, B:38:0x005f, B:39:0x0076, B:41:0x007e, B:42:0x0090), top: B:12:0x0021 }] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() {
                long j;
                long readBatteryInfo;
                synchronized (BatteryService.this.mLock) {
                    j = BatteryService.this.mCurrentBatteryUsage;
                    BatteryService.this.mCurrentBatteryUsage = 0L;
                }
                if (j <= 0) {
                    return;
                }
                synchronized (BatteryService.this.mLockBatteryInfoBackUp) {
                    if (BatteryService.this.mBattInfoManager.isDualAuth()) {
                        if (BatteryService.this.mBattInfoManager.isAuthAvailable()) {
                            BatteryService.this.mBattInfoManager.dischargeLevelData.addValueAndSave(j);
                        }
                    } else {
                        BatteryService batteryService = BatteryService.this;
                        batteryService.mSavedBatteryUsage = batteryService.readBatteryUsageFromEfsLocked("/efs/FactoryApp/batt_discharge_level") + j;
                        if (BatteryService.this.mBatteryType == 10) {
                            BatteryService batteryService2 = BatteryService.this;
                            batteryService2.saveBatteryInfo("/sys/class/power_supply/sec_auth/batt_discharge_level", batteryService2.mSavedBatteryUsage);
                            BatteryService batteryService3 = BatteryService.this;
                            batteryService3.saveBatteryInfo("/efs/FactoryApp/batt_discharge_level", batteryService3.mSavedBatteryUsage);
                        } else if (BatteryService.this.mBatteryType == 20) {
                            readBatteryInfo = BatteryService.this.readBatteryInfo("/sys/class/power_supply/sbp-fg/cycle");
                            BatteryService.this.saveBatteryInfo("/efs/FactoryApp/batt_discharge_level", readBatteryInfo * 100);
                            if (BatteryService.FEATURE_SAVE_BATTERY_CYCLE) {
                                if (BatteryService.this.mBattInfoManager.isDualAuth()) {
                                    if (BatteryService.this.mBattInfoManager.isAuthAvailable()) {
                                        BatteryService.this.mBattInfoManager.setCycle();
                                    }
                                } else if (BatteryService.this.mBatteryType == 20) {
                                    BatteryService.this.saveBatteryInfo("/sys/class/power_supply/battery/battery_cycle", readBatteryInfo);
                                } else if (BatteryService.FEATURE_FULL_BATTERY_CYCLE) {
                                    BatteryService.this.saveBatteryInfo("/sys/class/power_supply/battery/battery_cycle", (BatteryService.this.mSavedBatteryUsage / 100) + " " + (BatteryService.this.mSavedFullBatteryDuration / 720));
                                } else {
                                    BatteryService batteryService4 = BatteryService.this;
                                    batteryService4.saveBatteryInfo("/sys/class/power_supply/battery/battery_cycle", batteryService4.mSavedBatteryUsage / 100);
                                }
                            }
                        } else {
                            BatteryService batteryService5 = BatteryService.this;
                            batteryService5.saveBatteryInfo("/efs/FactoryApp/batt_discharge_level", batteryService5.mSavedBatteryUsage);
                        }
                    }
                    readBatteryInfo = -1;
                    if (BatteryService.FEATURE_SAVE_BATTERY_CYCLE) {
                    }
                }
            }
        };
        this.mSaveBatteryMaxTempRunnable = new Runnable() { // from class: com.android.server.BatteryService.39
            @Override // java.lang.Runnable
            public void run() {
                long j;
                synchronized (BatteryService.this.mLock) {
                    j = BatteryService.this.mBatteryMaxTemp;
                }
                synchronized (BatteryService.this.mLockBatteryInfoBackUp) {
                    if (BatteryService.this.mSavedBatteryMaxTemp < 0) {
                        BatteryService batteryService = BatteryService.this;
                        batteryService.mSavedBatteryMaxTemp = batteryService.readBatteryMaxTempFromEfsLocked();
                    }
                    if (BatteryService.this.mSavedBatteryMaxTemp < j) {
                        BatteryService.this.mSavedBatteryMaxTemp = j;
                        BatteryService batteryService2 = BatteryService.this;
                        batteryService2.saveBatteryInfo("/efs/FactoryApp/max_temp", batteryService2.mSavedBatteryMaxTemp);
                    }
                }
            }
        };
        this.mSaveBatteryMaxCurrentRunnable = new Runnable() { // from class: com.android.server.BatteryService.40
            @Override // java.lang.Runnable
            public void run() {
                long j;
                synchronized (BatteryService.this.mLock) {
                    j = BatteryService.this.mBatteryMaxCurrent;
                }
                synchronized (BatteryService.this.mLockBatteryInfoBackUp) {
                    if (BatteryService.this.mSavedBatteryMaxCurrent < 0) {
                        BatteryService batteryService = BatteryService.this;
                        batteryService.mSavedBatteryMaxCurrent = batteryService.readBatteryMaxCurrentFromEfsLocked();
                    }
                    if (BatteryService.this.mSavedBatteryMaxCurrent < j) {
                        BatteryService.this.mSavedBatteryMaxCurrent = j;
                        BatteryService batteryService2 = BatteryService.this;
                        batteryService2.saveBatteryInfo("/efs/FactoryApp/max_current", batteryService2.mSavedBatteryMaxCurrent);
                    }
                }
            }
        };
        this.mUpdateBatteryAsocRunnable = new Runnable() { // from class: com.android.server.BatteryService.41
            @Override // java.lang.Runnable
            public void run() {
                synchronized (BatteryService.this.mLockBatteryInfoBackUp) {
                    if (BatteryService.this.mBattInfoManager.isDualAuth()) {
                        if (BatteryService.this.mBattInfoManager.isAuthAvailable()) {
                            BatteryService.this.mBattInfoManager.asocData.updateAndSet();
                        }
                    } else {
                        long readBatteryInfo = BatteryService.this.readBatteryInfo("/sys/class/power_supply/battery/fg_asoc");
                        Slog.m72d(BatteryService.TAG, "!@currentAsoc: " + readBatteryInfo);
                        BatteryService batteryService = BatteryService.this;
                        batteryService.mSavedBatteryAsoc = batteryService.readBatteryAsocFromEfsLocked();
                        if (BatteryService.this.mSavedBatteryAsoc < 0) {
                            BatteryService batteryService2 = BatteryService.this;
                            batteryService2.mSavedBatteryAsoc = batteryService2.initializeSavedAsoc(readBatteryInfo);
                        }
                        if (readBatteryInfo >= 0 && readBatteryInfo < BatteryService.this.mSavedBatteryAsoc && BatteryService.this.mSavedBatteryAsoc - readBatteryInfo < 10) {
                            BatteryService.this.mSavedBatteryAsoc--;
                            BatteryService batteryService3 = BatteryService.this;
                            batteryService3.saveBatteryInfo("/efs/FactoryApp/asoc", batteryService3.mSavedBatteryAsoc);
                            if (BatteryService.this.mBatteryType == 10) {
                                BatteryService batteryService4 = BatteryService.this;
                                batteryService4.saveBatteryInfo("/sys/class/power_supply/sec_auth/asoc", batteryService4.mSavedBatteryAsoc);
                            }
                        }
                        BatteryService batteryService5 = BatteryService.this;
                        batteryService5.saveBatteryInfo("/sys/class/power_supply/battery/fg_asoc", batteryService5.mSavedBatteryAsoc);
                    }
                }
            }
        };
        this.mUpdateBatteryUsageExtenderRunnable = new Runnable() { // from class: com.android.server.BatteryService.42
            /* JADX WARN: Removed duplicated region for block: B:11:0x003e A[Catch: all -> 0x005c, TryCatch #0 {, blocks: (B:4:0x0007, B:8:0x001e, B:11:0x003e, B:13:0x0050, B:14:0x005a, B:21:0x002d), top: B:3:0x0007 }] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() {
                long j;
                synchronized (BatteryService.this.mLockBatteryInfoBackUp) {
                    long readBatteryUsageFromEfsLocked = BatteryService.this.readBatteryUsageFromEfsLocked("/efs/FactoryApp/batt_discharge_level");
                    if (BatteryService.this.mLifeExtender) {
                        if (readBatteryUsageFromEfsLocked < 1000000) {
                            j = 1000000 + readBatteryUsageFromEfsLocked;
                            Slog.m72d(BatteryService.TAG, "!@ + 10000 cycle");
                            if (readBatteryUsageFromEfsLocked != j) {
                                BatteryService.this.saveBatteryInfo("/efs/FactoryApp/batt_discharge_level", j);
                                BatteryService.this.mSavedBatteryUsage = j;
                                if (BatteryService.FEATURE_SAVE_BATTERY_CYCLE) {
                                    BatteryService.this.saveBatteryInfo("/sys/class/power_supply/battery/battery_cycle", j / 100);
                                }
                            }
                        }
                        j = readBatteryUsageFromEfsLocked;
                        if (readBatteryUsageFromEfsLocked != j) {
                        }
                    } else {
                        if (readBatteryUsageFromEfsLocked >= 1000000) {
                            j = readBatteryUsageFromEfsLocked - 1000000;
                            Slog.m72d(BatteryService.TAG, "!@ - 10000 cycle");
                            if (readBatteryUsageFromEfsLocked != j) {
                            }
                        }
                        j = readBatteryUsageFromEfsLocked;
                        if (readBatteryUsageFromEfsLocked != j) {
                        }
                    }
                }
            }
        };
        this.mUpdateBatteryUsageFullCapacityEnableRunnable = new Runnable() { // from class: com.android.server.BatteryService.43
            @Override // java.lang.Runnable
            public void run() {
                if (BatteryService.this.mFullCapacityEnable) {
                    BatteryService.fileWriteInt("/sys/class/power_supply/battery/batt_full_capacity", 85);
                    BatteryService.this.saveBatteryInfo("/efs/Battery/batt_full_capacity", 85L);
                } else {
                    BatteryService.fileWriteInt("/sys/class/power_supply/battery/batt_full_capacity", 100);
                    BatteryService.this.saveBatteryInfo("/efs/Battery/batt_full_capacity", 100L);
                }
            }
        };
        this.mContext = context;
        Handler handler = new Handler(true);
        this.mHandler = handler;
        this.mHandlerForBatteryInfoBackUp = new Handler(true);
        this.mLed = new Led(context, (LightsManager) getLocalService(LightsManager.class));
        this.mBatteryStats = BatteryStatsService.getService();
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mCriticalBatteryLevel = context.getResources().getInteger(R.integer.config_defaultNotificationLedOn);
        int integer = context.getResources().getInteger(R.integer.config_minMillisBetweenInputUserActivityEvents);
        this.mLowBatteryWarningLevel = integer;
        this.mLowBatteryCloseWarningLevel = integer + context.getResources().getInteger(R.integer.config_minDreamOverlayDurationMs);
        this.mShutdownBatteryTemperature = context.getResources().getInteger(R.integer.config_sidefpsSkipWaitForPowerVendorAcquireMessage);
        this.mBatteryLevelsEventQueue = new ArrayDeque();
        this.mMetricsLogger = new MetricsLogger();
        this.mLedSettingsObserver = new LedSettingsObserver();
        this.mAdaptiveFastChargingSettingsObserver = new AdaptiveFastChargingSettingsObserver();
        if (PowerManagerUtil.SEC_FEATURE_USE_SFC) {
            this.mSuperFastChargingSettingsObserver = new SuperFastChargingSettingsObserver();
        }
        if (PowerManagerUtil.SEC_FLOATING_FEATURE_BATTERY_SUPPORT_PASS_THROUGH) {
            this.mPassThroughSettingsObserver = new PassThroughSettingsObserver();
        }
        boolean z = PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_AFC;
        if (z) {
            this.mWirelessFastChargingSettingsObserver = new WirelessFastChargingSettingsObserver();
            if (PowerManagerUtil.SEC_FEATURE_WA_LCD_FLICKERING_WITH_VRR) {
                this.mRefreshRateModeSettingsObserver = new RefreshRateModeSettingsObserver();
            }
        }
        if (PowerManagerUtil.SEC_FEATURE_BATTERY_LIFE_EXTENDER) {
            this.mLifeExtenderSettingsObserver = new LifeExtenderSettingsObserver();
        }
        if (PowerManagerUtil.SEC_FEATURE_BATTERY_FULL_CAPACITY || BattFeatures.SUPPORT_ECO_BATTERY) {
            this.mFullCapacityEnableSettingsObserver = new FullCapacityEnableSettingsObserver();
        }
        boolean z2 = PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING;
        if (z2) {
            this.mTxBatteryLimitSettingsObserver = new TxBatteryLimitSettingsObserver();
        }
        context.registerReceiver(this.mBootCompletedReceiver, new IntentFilter("android.intent.action.BOOT_COMPLETED"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        BattInfoManager battInfoManager = new BattInfoManager(context);
        this.mBattInfoManager = battInfoManager;
        if (battInfoManager.isDualAuth()) {
            this.mBattInfoManager.init();
        } else {
            int batteryType = getBatteryType();
            this.mBatteryType = batteryType;
            if (batteryType == 10) {
                syncBatteryInfoAuthEfs();
            } else if (batteryType == 20) {
                syncBatteryInfoSbpFgEfs();
            }
            initBatteryInfo();
        }
        context.registerReceiver(this.mIntentReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.REQUEST_OTG_CHARGE_BLOCK");
        context.registerReceiver(this.mRequestOtgChargeBlockReceiver, intentFilter2, "com.sec.permission.OTG_CHARGE_BLOCK", null);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.sec.intent.action.BATT_SLATE_MODE_CHANGE");
        context.registerReceiverAsUser(this.mBattSlateModeControlReceiver, UserHandle.ALL, intentFilter3, "com.sec.permission.OTG_CHARGE_BLOCK", null);
        if (FEATURE_HICCUP_CONTROL) {
            IntentFilter intentFilter4 = new IntentFilter();
            intentFilter4.addAction("com.samsung.systemui.power.action.WATER_POPUP_DISMISSED");
            intentFilter4.addAction("com.samsung.systemui.power.action.USB_DAMAGE_POPUP_SHOW");
            context.registerReceiver(this.mHiccupControlReceiver, intentFilter4);
        }
        if (z2) {
            IntentFilter intentFilter5 = new IntentFilter();
            intentFilter5.addAction("com.samsung.android.sm.ACTION_WIRELESS_POWER_SHARING");
            context.registerReceiverAsUser(this.mWirelessPowerSharingReceiver, UserHandle.ALL, intentFilter5, "com.samsung.android.permission.wirelesspowersharing", null);
            if (PowerManagerUtil.SEC_FEATURE_DEX_DUAL_VIEW) {
                IntentFilter intentFilter6 = new IntentFilter();
                intentFilter6.addAction(ACTION_ENTER_DESK_MODE);
                intentFilter6.addAction(ACTION_EXIT_DESK_MODE);
                context.registerReceiverAsUser(this.mDexReceiver, UserHandle.ALL, intentFilter6, null, null);
            }
        }
        if (z) {
            IntentFilter intentFilter7 = new IntentFilter();
            intentFilter7.addAction("android.samsung.media.action.AUDIO_MODE");
            context.registerReceiverAsUser(this.mAudioModeChangeReceiver, UserHandle.ALL, intentFilter7, null, null);
            this.f1615tm = (TelephonyManager) context.getSystemService("phone");
            startCallThread();
        }
        if (PowerManagerUtil.SEC_FEATURE_SUPPORT_WIRELESS_NIGHT_MODE) {
            IntentFilter intentFilter8 = new IntentFilter();
            intentFilter8.addAction("com.samsung.android.sm.ACTION_FAST_WIRELESS_CHARGING_CONTROL");
            context.registerReceiverAsUser(this.mFastWirelessAutoModeReceiver, UserHandle.ALL, intentFilter8, "com.samsung.android.permission.FAST_WIRELESS_CHARGING_CONTROL", null);
        }
        if (new File("/sys/devices/virtual/switch/invalid_charger/state").exists()) {
            new UEventObserver() { // from class: com.android.server.BatteryService.13
                public void onUEvent(UEventObserver.UEvent uEvent) {
                    boolean equals = "1".equals(uEvent.get("SWITCH_STATE"));
                    synchronized (BatteryService.this.mLock) {
                        if (BatteryService.this.mInvalidCharger != equals) {
                            BatteryService.this.mInvalidCharger = equals ? 1 : 0;
                        }
                    }
                }
            }.startObserving("DEVPATH=/devices/virtual/switch/invalid_charger");
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null && packageManager.hasSystemFeature("att.devicehealth.support")) {
            this.mEnableIqi = true;
        }
        if (PowerManagerUtil.SEC_FEATURE_USE_AFC) {
            String[] strArr = ADAPTIVE_FAST_CHARGING_DISABLE_SYSFS_PATHS;
            this.mAfcDisableSysFs = isFileSupported(strArr[0]) ? strArr[0] : strArr[1];
            Slog.m72d(TAG, "!@ mAfcDisableSysFs : " + this.mAfcDisableSysFs);
        }
        int i = SystemProperties.getInt("ro.boot.cm.param.offset", -1);
        this.mWirelessFastChargingOffset = i;
        this.mAdaptiveFastChargingOffset = i != -1 ? i + 1 : -1;
        this.mSuperFastChargingOffset = SystemProperties.getInt("ro.boot.pd.param.offset", -1);
        int i2 = SystemProperties.getInt("ro.boot.wc.param.offset", -1);
        this.mWcParamOffset = i2;
        String readFromFile = readFromFile("/sys/class/power_supply/battery/wc_param_info");
        if (i2 != -1 && readFromFile != null) {
            this.mWcParamInfoSettingsObserver = new WcParamInfoSettingsObserver();
        }
        IntentFilter intentFilter9 = new IntentFilter();
        intentFilter9.addAction("android.intent.action.TIME_SET");
        context.registerReceiver(new TimeChangedReceiver(), intentFilter9, null, handler);
        IntentFilter intentFilter10 = new IntentFilter();
        intentFilter10.addAction("com.sec.android.app.secsetupwizard.SETUPWIZARD_COMPLETE");
        intentFilter10.addAction("com.sec.android.app.setupwizard.SETUPWIZARD_COMPLETE");
        context.registerReceiver(new SetupWizardCompleteReceiver(), intentFilter10, null, handler);
        if (!this.mBattInfoManager.isDualAuth() && this.mBatteryType == 10) {
            String readFromFile2 = readFromFile("/sys/class/power_supply/sec_auth/fai_expired");
            String str = TAG;
            Slog.m76i(str, "[BatteryService]faiExpiredStr:" + readFromFile2);
            BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "Check FAI Expiered When Boot", "faiExpiredStr:" + readFromFile2);
            if (!"1".equals(readFromFile2)) {
                if (!"0".equals(readFromFile2)) {
                    writeToFile("/sys/class/power_supply/sec_auth/fai_expired", "0");
                }
                IntentFilter intentFilter11 = new IntentFilter();
                intentFilter11.addAction("android.intent.action.DATE_CHANGED");
                DateChangedReceiver dateChangedReceiver = new DateChangedReceiver();
                this.mDateChangedReceiver = dateChangedReceiver;
                context.registerReceiver(dateChangedReceiver, intentFilter11, null, handler);
                Slog.m76i(str, "[BatteryService]DateChangedReceiver Registered For FAI Expired");
            }
        }
        this.mBatteryInputSuspended = ((Boolean) PowerProperties.battery_input_suspended().orElse(Boolean.FALSE)).booleanValue();
        BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "BatteryService Created", "");
        this.mSkipActionBatteryChangedHandler = new Handler(Looper.myLooper()) { // from class: com.android.server.BatteryService.14
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                Slog.m72d(BatteryService.TAG, "SkipActionBatteryChangedHandler : MSG_RECOVER_SEND_ACTION_BATTERY_CHANGED");
                BatteryService.this.mSkipActionBatteryChangedHandler.removeCallbacksAndMessages(null);
                synchronized (BatteryService.this.mLock) {
                    if (BatteryService.this.mIsSkipActionBatteryChanged) {
                        BatteryService.this.sendBatteryChangedIntentLocked();
                        BatteryService.this.mIsSkipActionBatteryChanged = false;
                    }
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.os.IBinder, com.android.server.BatteryService$BatteryPropertiesRegistrar] */
    @Override // com.android.server.SystemService
    public void onStart() {
        registerHealthCallback();
        BinderService binderService = new BinderService();
        this.mBinderService = binderService;
        publishBinderService("battery", binderService);
        ?? batteryPropertiesRegistrar = new BatteryPropertiesRegistrar();
        this.mBatteryPropertiesRegistrar = batteryPropertiesRegistrar;
        publishBinderService("batteryproperties", batteryPropertiesRegistrar);
        publishLocalService(BatteryManagerInternal.class, new LocalService());
        checkLongLifeBattery();
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        int i2;
        String str = TAG;
        Slog.m72d(str, "[onBootPhase]phase:" + i);
        if (i == 550) {
            synchronized (this.mLock) {
                this.mActivityManagerReady = true;
                ContentObserver contentObserver = new ContentObserver(this.mHandler) { // from class: com.android.server.BatteryService.15
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        synchronized (BatteryService.this.mLock) {
                            BatteryService.this.updateBatteryWarningLevelLocked();
                        }
                    }
                };
                ContentResolver contentResolver = this.mContext.getContentResolver();
                contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_trigger_level"), false, contentObserver, -1);
                updateBatteryWarningLevelLocked();
                registerContentObserver(contentResolver);
                SemInputDeviceManager semInputDeviceManager = (SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService");
                this.mSemInputDeviceManager = semInputDeviceManager;
                if (semInputDeviceManager != null && (i2 = this.mLatestWirelessChargingMode) != 0) {
                    semInputDeviceManager.setWirelessChargingMode(11, i2);
                    this.mSemInputDeviceManager.setWirelessChargingMode(1, this.mLatestWirelessChargingMode);
                    Slog.m72d(str, "setWirelessChargingMode(All): " + this.mLatestWirelessChargingMode);
                }
            }
            return;
        }
        if (i == 1000) {
            ContentResolver contentResolver2 = this.mContext.getContentResolver();
            this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.16
                @Override // java.lang.Runnable
                public void run() {
                    Slog.m72d(BatteryService.TAG, "!@bootCompleted");
                    synchronized (BatteryService.this.mLock) {
                        BatteryService.this.mBootCompleted = true;
                        BatteryService.this.mLed.updateLightsLocked();
                    }
                }
            });
            synchronized (this.mLock) {
                sendDeteriorationIntentLocked(true);
            }
            if (this.mEnableIqi) {
                boolean z = SystemProperties.getBoolean("persist.sys.shutdown_received", true);
                SystemProperties.set("persist.sys.shutdown_received", "false");
                if (!z) {
                    this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.BatteryService.17
                        @Override // java.lang.Runnable
                        public void run() {
                            IQIManager iQIManager = IQIManager.getInstance();
                            if (iQIManager == null || !iQIManager.shouldSubmitMetric(HW12.f1755ID)) {
                                return;
                            }
                            HW12 hw12 = new HW12();
                            hw12.setCause((byte) 0);
                            hw12.setProcessor((byte) 0);
                            iQIManager.submitMetric(hw12);
                            if ("eng".equals(Build.TYPE)) {
                                Slog.m72d(BatteryService.TAG, "submit HW12");
                            }
                        }
                    }, 50000L);
                }
            }
            if (FEATURE_HICCUP_CONTROL && this.mIsHiccupPopupShowing) {
                int i3 = this.mLastBatteryEvent;
                if ((i3 & 32) != 0 || (i3 & IInstalld.FLAG_FORCE) != 0) {
                    Slog.m72d(str, "bootCompleted and HiccupPopup");
                    fileWriteInt("/sys/class/power_supply/battery/batt_misc_event", this.mLastBatteryEvent);
                }
            }
            if (PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING) {
                this.mIsWirelessTxSupported = isSupportedWirelessTx();
            }
            if (PowerManagerUtil.SEC_FEATURE_BATTERY_LIFE_EXTENDER) {
                this.mLifeExtender = Settings.System.getIntForUser(contentResolver2, "protect_battery", 0, -2) == 1;
                Slog.m72d(str, "!@mLifeExtender Settings = " + this.mLifeExtender + " mLifeExtenderSettingsObserver register");
                contentResolver2.registerContentObserver(Settings.System.getUriFor("protect_battery"), false, this.mLifeExtenderSettingsObserver, -1);
                this.mHandlerForBatteryInfoBackUp.post(this.mUpdateBatteryUsageExtenderRunnable);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0037 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0036 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateBatteryDate() {
        boolean z;
        boolean z2;
        boolean z3;
        int i;
        int i2;
        boolean isFileSupported;
        String currentCalenderStr;
        try {
        } catch (Exception e) {
            Slog.m74e(TAG, "[updateBatteryDate]Exception");
            e.printStackTrace();
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) == 1) {
            z = true;
            Slog.m72d(TAG, "[updateBatteryDate]isSetupWizardCompleted:" + z);
            if (z) {
                return;
            }
            try {
                z2 = SystemProperties.getBoolean("persist.sys.setupwizard.user_setup_complete", false);
            } catch (Exception e2) {
                Slog.m74e(TAG, "[updateBatteryDate]Exception");
                e2.printStackTrace();
                z2 = false;
            }
            Slog.m72d(TAG, "[updateBatteryDate]isSetupWizardCompletedByUser:" + z2);
            try {
            } catch (Exception e3) {
                Slog.m74e(TAG, "[updateBatteryDate]Exception");
                e3.printStackTrace();
            }
            if (SystemProperties.getInt("persist.sys.setupwizard.jig_on_wifisetup", 0) == 1) {
                z3 = true;
                String str = TAG;
                Slog.m72d(str, "[updateBatteryDate]isSetupWizardSkipWifi:" + z3);
                i = this.mBatteryType;
                if (i != 10) {
                    if (!z2) {
                        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "BatteryFirstUseDate", "isSetupWizardCompletedByUser:" + z2);
                        this.mShouldCheckFirstUseDateRegularly = false;
                        return;
                    }
                    if (z3) {
                        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "BatteryFirstUseDate", "isSetupWizardSkipWifi:" + z3);
                        this.mShouldCheckFirstUseDateRegularly = false;
                        return;
                    }
                    boolean z4 = ((int) readBatteryInfo("/sys/class/power_supply/sec_auth/use_date_wlock")) == 2;
                    Slog.m72d(str, "[updateBatteryDate]isDateLockLocked:" + z4);
                    if (z4) {
                        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "BatteryFirstUseDate", "isDateLockLocked:" + z4);
                        this.mShouldCheckFirstUseDateRegularly = false;
                        return;
                    }
                } else if (i == 20) {
                    if (!z2) {
                        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "BatteryFirstUseDate", "isSetupWizardCompletedByUser:" + z2);
                        this.mShouldCheckFirstUseDateRegularly = false;
                        return;
                    }
                    if (z3) {
                        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "BatteryFirstUseDate", "isSetupWizardSkipWifi:" + z3);
                        this.mShouldCheckFirstUseDateRegularly = false;
                        return;
                    }
                }
                i2 = this.mBatteryType;
                if (i2 != 10) {
                    String readFromFile = readFromFile("/sys/class/power_supply/sec_auth/first_use_date");
                    isFileSupported = readFromFile != null && readFromFile.length() == 8 && readFromFile.startsWith("20");
                    Slog.m72d(str, "[updateBatteryDate]isFirstUseDateExist:" + isFileSupported);
                    if (isFileSupported) {
                        this.mShouldCheckFirstUseDateRegularly = false;
                    }
                } else if (i2 == 20) {
                    String readFromFile2 = readFromFile("/sys/class/power_supply/sbp-fg/first_use_date");
                    isFileSupported = readFromFile2 != null && readFromFile2.length() == 8 && readFromFile2.startsWith("20");
                    Slog.m72d(str, "[updateBatteryDate]isFirstUseDateExist:" + isFileSupported);
                    if (isFileSupported) {
                        this.mShouldCheckFirstUseDateRegularly = false;
                    }
                } else {
                    isFileSupported = isFileSupported("/efs/FactoryApp/batt_beginning_date");
                }
                if (isFileSupported) {
                    int i3 = this.mBatteryType;
                    if (i3 == 10) {
                        currentCalenderStr = BattUtils.getCurrentNetworkDateStr();
                    } else if (i3 == 20) {
                        currentCalenderStr = BattUtils.getCurrentNetworkDateStr();
                    } else {
                        currentCalenderStr = BattUtils.getCurrentCalenderStr();
                    }
                    Slog.m72d(str, "[updateBatteryDate]currentBatteryDate:" + currentCalenderStr);
                    if (currentCalenderStr != null && currentCalenderStr.length() == 8 && writeBatteryDate(currentCalenderStr)) {
                        Slog.m72d(str, "!@[B_DATE] succeed : " + currentCalenderStr);
                        return;
                    }
                    Slog.m72d(str, "!@[B_DATE] writing fail");
                    return;
                }
                Slog.m72d(str, "!@[B_DATE] isEfsExist");
                return;
            }
            z3 = false;
            String str2 = TAG;
            Slog.m72d(str2, "[updateBatteryDate]isSetupWizardSkipWifi:" + z3);
            i = this.mBatteryType;
            if (i != 10) {
            }
            i2 = this.mBatteryType;
            if (i2 != 10) {
            }
            if (isFileSupported) {
            }
        }
        z = false;
        Slog.m72d(TAG, "[updateBatteryDate]isSetupWizardCompleted:" + z);
        if (z) {
        }
    }

    public final boolean writeBatteryDate(String str) {
        int parseInt;
        String str2 = SystemProperties.get("ril.rfcal_date");
        String str3 = SystemProperties.get("ril.manufacturedate");
        String str4 = TAG;
        Slog.m72d(str4, "[writeBatteryDate]strRfCalDate:" + str2 + " ,strManufactureDate:" + str3);
        try {
            int parseInt2 = Integer.parseInt(str);
            if (str2 != null) {
                str2 = str2.replace(".", "");
            }
            if (str2 != null && str2.length() == 8) {
                parseInt = Integer.parseInt(str2);
                Slog.m72d(str4, "!@[B_DATE] rfcal date will be used for compare");
            } else if (str3 != null && str3.length() == 8) {
                parseInt = Integer.parseInt(str3);
                Slog.m72d(str4, "!@[B_DATE] manufacture date will be used for compare");
            } else {
                Slog.m72d(str4, "!@[B_DATE] fail - no date for compare");
                int i = this.mBatteryType;
                if (i == 10 || i == 20) {
                    this.mShouldCheckFirstUseDateRegularly = false;
                }
                return false;
            }
            if (parseInt <= parseInt2) {
                this.mSavedBatteryBeginningDate = parseInt2;
                fileWriteInt("/efs/FactoryApp/batt_beginning_date", parseInt2);
                int i2 = this.mBatteryType;
                if (i2 == 10) {
                    fileWriteInt("/sys/class/power_supply/sec_auth/first_use_date", parseInt2);
                    this.mShouldCheckFirstUseDateRegularly = false;
                } else if (i2 == 20) {
                    fileWriteInt("/sys/class/power_supply/sbp-fg/first_use_date", parseInt2);
                    this.mShouldCheckFirstUseDateRegularly = false;
                }
                BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "BatteryFirstUseDate", "write batteryDate:" + parseInt2);
                return true;
            }
            Slog.m72d(str4, "!@[B_DATE] date error");
            return false;
        } catch (NumberFormatException e) {
            Slog.m72d(TAG, "NumberFormatException");
            e.printStackTrace();
            return false;
        }
    }

    public final class TimeChangedReceiver extends BroadcastReceiver {
        public TimeChangedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Slog.m72d(BatteryService.TAG, "!@[B_DATE] Time Changed !!");
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.TimeChangedReceiver.1
                @Override // java.lang.Runnable
                public void run() {
                    if (BatteryService.this.mBattInfoManager.isDualAuth()) {
                        if (BatteryService.this.mBattInfoManager.isAuthAvailable() && BatteryService.this.mBattInfoManager.firstUseDateData.shouldCheck) {
                            BatteryService.this.mBattInfoManager.firstUseDateData.updateDate();
                            return;
                        }
                        return;
                    }
                    BatteryService.this.updateBatteryDate();
                }
            });
        }
    }

    public final class SetupWizardCompleteReceiver extends BroadcastReceiver {
        public SetupWizardCompleteReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Slog.m72d(BatteryService.TAG, "!@[B_DATE] SetupWizard is completed !!");
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.SetupWizardCompleteReceiver.1
                @Override // java.lang.Runnable
                public void run() {
                    if (BatteryService.this.mBattInfoManager.isDualAuth()) {
                        if (BatteryService.this.mBattInfoManager.isAuthAvailable() && BatteryService.this.mBattInfoManager.firstUseDateData.shouldCheck) {
                            BatteryService.this.mBattInfoManager.firstUseDateData.updateDate();
                            return;
                        }
                        return;
                    }
                    BatteryService.this.updateBatteryDate();
                }
            });
        }
    }

    public final class DateChangedReceiver extends BroadcastReceiver {
        public DateChangedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Slog.m72d(BatteryService.TAG, "!@[B_DATE] Date Changed !!");
            BatteryService.this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.DateChangedReceiver.1
                @Override // java.lang.Runnable
                public void run() {
                    BatteryService.this.checkFaiExpired();
                }
            });
        }
    }

    public final void checkFaiExpired() {
        DateChangedReceiver dateChangedReceiver;
        LocalDate convertDateStringToLocalDate = BattUtils.convertDateStringToLocalDate(readFromFile("/sys/class/power_supply/sec_auth/first_use_date"));
        String str = TAG;
        Slog.m72d(str, "[checkFaiExpired]firstUseDate:" + convertDateStringToLocalDate);
        if (convertDateStringToLocalDate == null) {
            return;
        }
        LocalDate plusDays = convertDateStringToLocalDate.plusDays(14L);
        LocalDate now = LocalDate.now();
        Slog.m72d(str, "[checkFaiExpired]currentDate:" + now + " ,thresholdDate:" + plusDays);
        if (now.isBefore(plusDays)) {
            return;
        }
        writeToFile("/sys/class/power_supply/sec_auth/fai_expired", "1");
        boolean equals = "1".equals(readFromFile("/sys/class/power_supply/sec_auth/fai_expired"));
        Slog.m72d(str, "[checkFaiExpired]check write result - isFaiExpired:" + equals);
        if (equals && (dateChangedReceiver = this.mDateChangedReceiver) != null) {
            this.mContext.unregisterReceiver(dateChangedReceiver);
            this.mDateChangedReceiver = null;
        }
        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "FAI Expired Written", "isFaiExpired:" + equals);
    }

    public final void checkLongLifeBattery() {
        if (!isFileSupported("/efs/FactoryApp/batt_after_manufactured")) {
            fileWriteInt("/efs/FactoryApp/batt_after_manufactured", 0);
            fileWriteInt("/sys/class/power_supply/battery/batt_after_manufactured", 0);
            this.mSavedDiffWeek = 0;
        } else {
            String readFromFile = readFromFile("/efs/FactoryApp/batt_after_manufactured");
            if (readFromFile != null) {
                try {
                    if (Integer.parseInt(readFromFile) >= 0) {
                        Slog.m72d(TAG, "!@[LLB] Write weeklyDiff EFS ->  Sys : " + readFromFile);
                        fileWriteInt("/sys/class/power_supply/battery/batt_after_manufactured", Integer.parseInt(readFromFile));
                        this.mSavedDiffWeek = Integer.parseInt(readFromFile);
                    }
                } catch (NumberFormatException e) {
                    Slog.m72d(TAG, "!@[LLB] can not change. value: " + readFromFile + ", e: " + e);
                    return;
                }
            }
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.18
            @Override // java.lang.Runnable
            public void run() {
                synchronized (BatteryService.this.mLock) {
                    BatteryService.this.mLongBatteryRetryCnt++;
                }
                int checkLongLifeBatteryInternal = BatteryService.this.checkLongLifeBatteryInternal();
                if (checkLongLifeBatteryInternal == 1) {
                    Slog.m72d(BatteryService.TAG, "!@[LLB] success to check weekly diff ");
                    return;
                }
                if (checkLongLifeBatteryInternal == 2) {
                    Slog.m72d(BatteryService.TAG, "!@[LLB] Calc error! check date!  ");
                    return;
                }
                if (checkLongLifeBatteryInternal != 3) {
                    return;
                }
                Slog.m72d(BatteryService.TAG, "!@[LLB] Faild to get property values, longBatteryRetryCnt= " + BatteryService.this.mLongBatteryRetryCnt);
                if (BatteryService.this.mLongBatteryRetryCnt < 5) {
                    BatteryService.this.mHandler.postDelayed(this, 3000L);
                } else {
                    Slog.m72d(BatteryService.TAG, "!@[LLB] Faild to calc checkLongLifeBatteryInternal, Do not try anymore");
                }
            }
        });
    }

    public final int checkLongLifeBatteryInternal() {
        String substring;
        String substring2;
        String substring3;
        int i;
        String str = SystemProperties.get("ril.rfcal_date");
        this.mManufactureDate = SystemProperties.get("ril.manufacturedate");
        if (str != null) {
            this.mRfCalDate = str.replace(".", "");
        }
        String str2 = this.mRfCalDate;
        if (str2 != null && str2.length() == 8) {
            substring = this.mRfCalDate.substring(0, 4);
            substring2 = this.mRfCalDate.substring(4, 6);
            substring3 = this.mRfCalDate.substring(6, 8);
            Slog.m72d(TAG, "!@[LLB] rfcal_date " + this.mRfCalDate);
        } else {
            String str3 = this.mManufactureDate;
            if (str3 != null && str3.length() == 8) {
                substring = this.mManufactureDate.substring(0, 4);
                substring2 = this.mManufactureDate.substring(4, 6);
                substring3 = this.mManufactureDate.substring(6, 8);
                Slog.m72d(TAG, "!@[LLB] manufacture_date " + this.mManufactureDate);
            } else {
                Slog.m72d(TAG, "!@[LLB] mRfCalDate is null!!! manufacture_date is also null!!!  we can not check weekly diff");
                return 3;
            }
        }
        this.mCurrentCalendar = Calendar.getInstance();
        GregorianCalendar gregorianCalendar = new GregorianCalendar(Integer.parseInt(substring), Integer.parseInt(substring2), Integer.parseInt(substring3));
        if (Integer.parseInt(substring) == 0) {
            return 3;
        }
        int i2 = this.mCurrentCalendar.get(1) - gregorianCalendar.get(1);
        int i3 = gregorianCalendar.get(3) - 4;
        int i4 = this.mCurrentCalendar.get(3);
        if (i2 == 0) {
            i = i4 - i3;
            if (i > 0) {
                Slog.m72d(TAG, "!@[LLB] same year diff_Week= " + i);
            } else {
                Slog.m72d(TAG, "!@[LLB] same year but error month!!!");
                return 2;
            }
        } else {
            if (i2 < 0) {
                Slog.m72d(TAG, "!@[LLB] error year");
                return 2;
            }
            i = i4 + ((i2 - 1) * 52) + (52 - i3);
        }
        if (checkWeeklyDiffIsValid("/efs/FactoryApp/batt_after_manufactured", i)) {
            fileWriteInt("/sys/class/power_supply/battery/batt_after_manufactured", i);
            fileWriteInt("/efs/FactoryApp/batt_after_manufactured", i);
            this.mSavedDiffWeek = i;
        }
        return 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0089 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean checkWeeklyDiffIsValid(String str, int i) {
        int i2;
        if (str == null) {
            Slog.m72d(TAG, "!@[LLB] " + str + " path string is nul");
            return false;
        }
        String readFromFile = readFromFile(str);
        if (readFromFile == null) {
            Slog.m72d(TAG, "!@[LLB] " + str + " is null, It looks first time, just make it.");
            i2 = 0;
        } else {
            try {
                i2 = Integer.parseInt(readFromFile);
            } catch (NumberFormatException unused) {
                i2 = -1;
            }
            try {
                Slog.m72d(TAG, "!@[LLB] EFS values: " + i2 + ", Diff_week: " + i);
            } catch (NumberFormatException unused2) {
                Slog.m74e(TAG, "!@[LLB] !@[BatteryInfo] " + str + " : data is \"" + readFromFile + "\"");
                if (i2 >= i) {
                }
            }
        }
        return i2 >= i;
    }

    public final void registerContentObserver(ContentResolver contentResolver) {
        this.mLedChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_charing", 1, -2) == 1;
        this.mLedLowBatterySettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_low_battery", 1, -2) == 1;
        String str = TAG;
        Slog.m72d(str, "!@Led Charging Settings = " + this.mLedChargingSettingsEnable);
        Slog.m72d(str, "!@Led Low Battery Settings = " + this.mLedLowBatterySettingsEnable);
        contentResolver.registerContentObserver(Settings.System.getUriFor("led_indicator_charing"), false, this.mLedSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("led_indicator_low_battery"), false, this.mLedSettingsObserver, -1);
        if (PowerManagerUtil.SEC_FEATURE_USE_AFC) {
            updateAdaptiveFastChargingSetting(contentResolver);
            contentResolver.registerContentObserver(Settings.System.getUriFor("adaptive_fast_charging"), false, this.mAdaptiveFastChargingSettingsObserver, -1);
        }
        if (PowerManagerUtil.SEC_FEATURE_USE_SFC) {
            this.mSuperFastChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "super_fast_charging", 1, -2) == 1;
            Slog.m72d(str, "!@SuperFastCharging Settings = " + this.mSuperFastChargingSettingsEnable);
            setSuperFastCharging(this.mSuperFastChargingSettingsEnable);
            contentResolver.registerContentObserver(Settings.System.getUriFor("super_fast_charging"), false, this.mSuperFastChargingSettingsObserver, -1);
        }
        if (PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_AFC) {
            this.mWasUsedWirelessFastChargerPreviously = Settings.System.getIntForUser(contentResolver, "show_wireless_charger_menu", 0, -2) == 1;
            this.mWirelessFastChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "wireless_fast_charging", 1, -2) == 1;
            Slog.m72d(str, "!@WirelessFastCharging Settings = " + this.mWirelessFastChargingSettingsEnable);
            setWirelessFastCharging(this.mWirelessFastChargingSettingsEnable);
            contentResolver.registerContentObserver(Settings.System.getUriFor("wireless_fast_charging"), false, this.mWirelessFastChargingSettingsObserver, -1);
            if (PowerManagerUtil.SEC_FEATURE_WA_LCD_FLICKERING_WITH_VRR) {
                this.mRefreshRateModeSetting = Settings.Secure.getIntForUser(contentResolver, "refresh_rate_mode", 0, -2);
                Slog.m72d(str, "!@RefreshRateMode Setting = " + this.mRefreshRateModeSetting);
                setRefreshRateMode(this.mRefreshRateModeSetting);
                contentResolver.registerContentObserver(Settings.Secure.getUriFor("refresh_rate_mode"), false, this.mRefreshRateModeSettingsObserver, -1);
            }
        }
        if (PowerManagerUtil.SEC_FLOATING_FEATURE_BATTERY_SUPPORT_PASS_THROUGH) {
            this.mPassThroughSettingsEnable = Settings.System.getIntForUser(contentResolver, "pass_through", 0, -2) == 1;
            Slog.m72d(str, "!@mPassThroughSettingsEnable Settings = " + this.mPassThroughSettingsEnable);
            setPassThrough(this.mPassThroughSettingsEnable);
            contentResolver.registerContentObserver(Settings.System.getUriFor("pass_through"), false, this.mPassThroughSettingsObserver, -1);
        }
        if (BattFeatures.SUPPORT_ECO_BATTERY) {
            contentResolver.registerContentObserver(Settings.Global.getUriFor("protect_battery"), false, this.mFullCapacityEnableSettingsObserver, -1);
            ContentResolver contentResolver2 = this.mContext.getContentResolver();
            this.mProtectBatteryMode = Settings.Global.getInt(contentResolver2, "protect_battery", 0);
            writeProtectBatteryValues();
            Slog.m72d(TAG_SS, "mProtectBatteryMode:" + this.mProtectBatteryMode);
            this.mProtectionThreshold = Settings.Global.getInt(contentResolver2, "battery_protection_threshold", DEFAULT_PROTECTION_THRESHOLD_LEVEL);
            Slog.m72d(str, "mProtectionThreshold:" + this.mProtectionThreshold);
            this.mLtcHighThreshold = Settings.Global.getInt(contentResolver2, "ltc_highsoc_threshold", 95);
            Slog.m72d(str, "mLtcHighThreshold:" + this.mLtcHighThreshold);
            fileWriteInt("/efs/Battery/batt_ltc_highsoc_threshold", this.mLtcHighThreshold);
            this.mLtcHighSocDuration = Settings.Global.getInt(contentResolver2, "ltc_highsoc_duration", FrameworkStatsLog.SETTING_SNAPSHOT);
            Slog.m72d(str, "mLtcHighSocDuration:" + this.mLtcHighSocDuration);
            fileWriteInt("/efs/Battery/batt_ltc_highsoc_duration", this.mLtcHighSocDuration);
            this.mLtcReleaseThreshold = Settings.Global.getInt(contentResolver2, "ltc_release_threshold", 75);
            Slog.m72d(str, "mLtcReleaseThreshold:" + this.mLtcReleaseThreshold);
            fileWriteInt("/efs/Battery/batt_ltc_release_threshold", this.mLtcReleaseThreshold);
        } else if (PowerManagerUtil.SEC_FEATURE_BATTERY_FULL_CAPACITY) {
            this.mLifeExtender = Settings.System.getIntForUser(contentResolver, "protect_battery", 0, -2) == 1;
            this.mHandlerForBatteryInfoBackUp.post(this.mUpdateBatteryUsageExtenderRunnable);
            this.mFullCapacityEnable = Settings.Global.getInt(contentResolver, "protect_battery", 0) == 1;
            Slog.m72d(str, "!@mFullCapacityEnable Settings = " + this.mFullCapacityEnable);
            this.mHandlerForBatteryInfoBackUp.post(this.mUpdateBatteryUsageFullCapacityEnableRunnable);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("protect_battery"), false, this.mFullCapacityEnableSettingsObserver, -1);
        }
        if (PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING) {
            this.mTxBatteryLimit = Settings.System.getIntForUser(contentResolver, "tx_battery_limit", 30, -2);
            Slog.m72d(str, "!@Tx Battery Limit Settings = " + this.mTxBatteryLimit);
            setWirelessPowerSharingTxBatteryLimit(this.mTxBatteryLimit);
            contentResolver.registerContentObserver(Settings.System.getUriFor("tx_battery_limit"), false, this.mTxBatteryLimitSettingsObserver, -1);
        }
        String readFromFile = readFromFile("/sys/class/power_supply/battery/wc_param_info");
        if (this.mWcParamOffset == -1 || readFromFile == null) {
            return;
        }
        Settings.System.putInt(this.mContext.getContentResolver(), "wireless_wc_write", 0);
        contentResolver.registerContentObserver(Settings.System.getUriFor("wireless_wc_write"), false, this.mWcParamInfoSettingsObserver, -1);
        setWcParamInfo(readFromFile);
    }

    public final void registerHealthCallback() {
        traceBegin("HealthInitWrapper");
        try {
            try {
                this.mHealthServiceWrapper = HealthServiceWrapper.create(new HealthInfoCallback() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda5
                    @Override // com.android.server.health.HealthInfoCallback
                    public final void update(SehHealthInfo sehHealthInfo) {
                        BatteryService.this.update(sehHealthInfo);
                    }
                });
                traceEnd();
                traceBegin("HealthInitWaitUpdate");
                long uptimeMillis = SystemClock.uptimeMillis();
                synchronized (this.mLock) {
                    while (this.mHealthInfo == null) {
                        Slog.m76i(TAG, "health: Waited " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms for callbacks. Waiting another 1000 ms...");
                        try {
                            this.mLock.wait(1000L);
                        } catch (InterruptedException unused) {
                            Slog.m76i(TAG, "health: InterruptedException when waiting for update.  Continuing...");
                        }
                    }
                }
                Slog.m76i(TAG, "health: Waited " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms and received the update.");
            } catch (RemoteException e) {
                Slog.m74e(TAG, "health: cannot register callback. (RemoteException)");
                throw e.rethrowFromSystemServer();
            } catch (NoSuchElementException e2) {
                Slog.m74e(TAG, "health: cannot register callback. (no supported health HAL service)");
                throw e2;
            }
        } finally {
            traceEnd();
        }
    }

    public final void updateBatteryWarningLevelLocked() {
        this.mContext.getContentResolver();
        this.mContext.getResources().getInteger(R.integer.config_minMillisBetweenInputUserActivityEvents);
        int i = this.mLowBatteryWarningLevel;
        this.mLastLowBatteryWarningLevel = i;
        int i2 = this.mCriticalBatteryLevel;
        if (i < i2) {
            this.mLowBatteryWarningLevel = i2;
        }
        this.mLowBatteryCloseWarningLevel = this.mLowBatteryWarningLevel + this.mContext.getResources().getInteger(R.integer.config_minDreamOverlayDurationMs);
        lambda$setChargerAcOnline$3(true);
    }

    public final boolean isPoweredLocked(int i) {
        HealthInfo healthInfo = this.mHealthInfo;
        if (healthInfo.batteryStatus == 1) {
            return true;
        }
        int i2 = i & 1;
        if (i2 != 0 && healthInfo.chargerAcOnline) {
            return true;
        }
        if ((i & 2) != 0 && healthInfo.chargerUsbOnline) {
            return true;
        }
        if ((i & 4) != 0 && healthInfo.chargerWirelessOnline) {
            return true;
        }
        if ((i & 8) == 0 || !healthInfo.chargerDockOnline) {
            return i2 != 0 && this.mSehHealthInfo.chargerPogoOnline;
        }
        return true;
    }

    public final void setWirelessFastCharging(boolean z) {
        int i = this.mWirelessFastChargingOffset;
        if (i != -1) {
            this.mHealthServiceWrapper.sehWriteEnableToParam(i, !z, "wfc");
        }
        if (z) {
            fileWriteInt("/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl", 2);
        } else {
            fileWriteInt("/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl", 1);
        }
    }

    public final void setRefreshRateMode(int i) {
        if (i == 0) {
            fileWriteInt("/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl", 5);
        } else {
            fileWriteInt("/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl", 6);
        }
    }

    public final void setWcParamInfo(String str) {
        this.mHealthServiceWrapper.sehWriteEnableToParam(-2, true, "wc param");
        Settings.System.putInt(this.mContext.getContentResolver(), "wireless_wc_write", 0);
    }

    public final boolean shouldSendBatteryLowLocked() {
        int i;
        int i2;
        boolean z = this.mPlugType != 0;
        boolean z2 = this.mLastPlugType != 0;
        if (z) {
            return false;
        }
        HealthInfo healthInfo = this.mHealthInfo;
        if (healthInfo.batteryStatus == 1 || (i = healthInfo.batteryLevel) > (i2 = this.mLowBatteryWarningLevel)) {
            return false;
        }
        return z2 || this.mLastBatteryLevel > i2 || i > this.mLastLowBatteryWarningLevel;
    }

    public final boolean shouldShutdownLocked() {
        SemEmergencyManager semEmergencyManager;
        HealthInfo healthInfo = this.mHealthInfo;
        int i = healthInfo.batteryCapacityLevel;
        if (i != -1) {
            return i == 1;
        }
        int i2 = healthInfo.batteryLevel;
        if (i2 > 0 || !healthInfo.batteryPresent) {
            return false;
        }
        if (i2 != 0 || !this.mBootCompleted) {
            return false;
        }
        if (healthInfo.batteryCurrentAverageMicroamps >= 0 && isPoweredLocked(15)) {
            Slog.m72d(TAG, "!@ BatteryService plug type: 0x" + Integer.toHexString(this.mSecPlugTypeSummary) + " battery current avg: " + this.mHealthInfo.batteryCurrentAverageMicroamps + ", so doesn't shutdown");
            return false;
        }
        if (!CoreRune.EM_SUPPORTED || (semEmergencyManager = SemEmergencyManager.getInstance(this.mContext)) == null || !semEmergencyManager.isEmergencyMode()) {
            return true;
        }
        Slog.m72d(TAG, "Emergency mode is on so doesn't shutdown");
        return false;
    }

    public final void shutdownIfNoPowerLocked() {
        if (shouldShutdownLocked()) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.19
                @Override // java.lang.Runnable
                public void run() {
                    IQIManager iQIManager;
                    if (BatteryService.this.mActivityManagerInternal.isSystemReady()) {
                        ShutdownThread.systemShutdown(BatteryService.this.getUiContext(), "no power");
                        if (BatteryService.this.mEnableIqi && (iQIManager = IQIManager.getInstance()) != null && iQIManager.shouldSubmitMetric(HW0E.f1754ID)) {
                            HW0E hw0e = new HW0E();
                            hw0e.setEvent((byte) 1);
                            iQIManager.submitMetric(hw0e);
                            if ("eng".equals(Build.TYPE)) {
                                Slog.m72d(BatteryService.TAG, "submit HW0E");
                            }
                        }
                        Slog.m72d(BatteryService.TAG, "!@ BatteryService No power and call shutdown thread");
                    }
                }
            });
        }
        if (this.mHealthInfo.batteryLevel == 0) {
            if (this.mBootCompleted && this.mActivityManagerInternal.isSystemReady()) {
                return;
            }
            Slog.m72d(TAG, "!@ BatteryService mBootCompleted: " + this.mBootCompleted + " am.isSystemReady: " + this.mActivityManagerInternal.isSystemReady() + ", so doesn't shutdown");
        }
    }

    public final void update(SehHealthInfo sehHealthInfo) {
        HealthInfo healthInfo = sehHealthInfo.aospHealthInfo;
        traceBegin("HealthInfoUpdate");
        Trace.traceCounter(131072L, "BatteryChargeCounter", healthInfo.batteryChargeCounterUah);
        Trace.traceCounter(131072L, "BatteryCurrent", healthInfo.batteryCurrentMicroamps);
        Trace.traceCounter(131072L, "PlugType", plugType(sehHealthInfo));
        Trace.traceCounter(131072L, "BatteryStatus", healthInfo.batteryStatus);
        synchronized (this.mLock) {
            if (!this.mUpdatesStopped) {
                this.mHealthInfo = healthInfo;
                this.mSehHealthInfo = sehHealthInfo;
                lambda$setChargerAcOnline$3(false);
                this.mLock.notifyAll();
            } else {
                Utils.copySehV1Battery(this.mLastSehHealthInfo, sehHealthInfo);
            }
        }
        traceEnd();
    }

    public static int plugType(SehHealthInfo sehHealthInfo) {
        HealthInfo healthInfo = sehHealthInfo.aospHealthInfo;
        if (healthInfo.chargerAcOnline) {
            return 1;
        }
        if (healthInfo.chargerWirelessOnline) {
            return 4;
        }
        if (healthInfo.chargerUsbOnline) {
            return 2;
        }
        if (sehHealthInfo.chargerPogoOnline) {
            return 1;
        }
        return healthInfo.chargerDockOnline ? 8 : 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x0139, code lost:
    
        if (r3.batteryCurrentNow == r0.mLastBatteryCurrentNow) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0169, code lost:
    
        if (r0.mWcTxId == r0.mLastWcTxId) goto L225;
     */
    /* renamed from: processValuesLocked, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void lambda$setChargerAcOnline$3(boolean z) {
        BatteryService batteryService;
        long j;
        boolean z2;
        int i;
        int i2;
        int i3;
        boolean z3;
        int i4;
        int i5;
        int i6;
        int i7;
        HealthInfo healthInfo = this.mHealthInfo;
        this.mBatteryLevelCritical = healthInfo.batteryStatus != 1 && healthInfo.batteryLevel <= this.mCriticalBatteryLevel;
        int plugType = plugType(this.mSehHealthInfo);
        this.mPlugType = plugType;
        if (plugType != 0 || this.mSehHealthInfo.chargerOtgOnline) {
            this.mSecPlugTypeSummary = 0;
            HealthInfo healthInfo2 = this.mHealthInfo;
            if (healthInfo2.chargerAcOnline) {
                this.mSecPlugTypeSummary = 0 | 1;
            }
            if (healthInfo2.chargerUsbOnline) {
                this.mSecPlugTypeSummary |= 2;
            }
            if (healthInfo2.chargerWirelessOnline) {
                this.mSecPlugTypeSummary |= 4;
            }
            SehHealthInfo sehHealthInfo = this.mSehHealthInfo;
            if (sehHealthInfo.chargerOtgOnline) {
                this.mSecPlugTypeSummary |= 65536;
            }
            if (sehHealthInfo.chargerPogoOnline) {
                this.mSecPlugTypeSummary |= 1;
            }
        } else {
            this.mSecPlugTypeSummary = 0;
        }
        try {
            IBatteryStats iBatteryStats = this.mBatteryStats;
            HealthInfo healthInfo3 = this.mHealthInfo;
            int i8 = healthInfo3.batteryStatus;
            int i9 = healthInfo3.batteryHealth;
            int i10 = healthInfo3.batteryLevel;
            int i11 = healthInfo3.batteryTemperatureTenthsCelsius;
            int i12 = healthInfo3.batteryVoltageMillivolts;
            int i13 = healthInfo3.batteryChargeCounterUah;
            int i14 = healthInfo3.batteryFullChargeUah;
            long j2 = healthInfo3.batteryChargeTimeToFullNowSeconds;
            SehHealthInfo sehHealthInfo2 = this.mSehHealthInfo;
            iBatteryStats.setBatteryState(i8, i9, plugType, i10, i11, i12, i13, i14, j2, sehHealthInfo2.batteryEvent, sehHealthInfo2.batteryOnline, sehHealthInfo2.batteryCurrentEvent, sehHealthInfo2.wirelessPowerSharingTxEvent, sehHealthInfo2.chargerOtgOnline);
        } catch (RemoteException unused) {
        }
        shutdownIfNoPowerLocked();
        if (BattFeatures.SUPPORT_ECO_BATTERY) {
            batteryService = this;
            SleepChargingManager sleepChargingManager = batteryService.mSleepChargingManager;
            if (sleepChargingManager != null) {
                int i15 = batteryService.mPlugType;
                long j3 = batteryService.mChargeStartTime;
                HealthInfo healthInfo4 = batteryService.mHealthInfo;
                sleepChargingManager.updateChargingInfo(i15, j3, healthInfo4.batteryLevel, healthInfo4.batteryChargeTimeToFullNowSeconds);
            }
        } else {
            batteryService = this;
        }
        if (batteryService.mHealthInfo.chargerWirelessOnline) {
            batteryService.mWcTxId = BattUtils.readNodeAsInt("/sys/class/power_supply/battery/wc_tx_id");
        } else {
            batteryService.mWcTxId = 0;
        }
        if (!z) {
            HealthInfo healthInfo5 = batteryService.mHealthInfo;
            if (healthInfo5.batteryStatus == batteryService.mLastBatteryStatus && healthInfo5.batteryHealth == batteryService.mLastBatteryHealth && healthInfo5.batteryPresent == batteryService.mLastBatteryPresent && healthInfo5.batteryLevel == batteryService.mLastBatteryLevel && batteryService.mPlugType == batteryService.mLastPlugType && (((i4 = healthInfo5.batteryVoltageMillivolts) == (i5 = batteryService.mLastBatteryVoltage) || Math.abs(i4 - i5) <= 50) && ((i6 = batteryService.mHealthInfo.batteryTemperatureTenthsCelsius) == (i7 = batteryService.mLastBatteryTemperature) || Math.abs(i6 - i7) <= 10))) {
                HealthInfo healthInfo6 = batteryService.mHealthInfo;
                if (healthInfo6.maxChargingCurrentMicroamps == batteryService.mLastMaxChargingCurrent && healthInfo6.maxChargingVoltageMicrovolts == batteryService.mLastMaxChargingVoltage) {
                    SehHealthInfo sehHealthInfo3 = batteryService.mSehHealthInfo;
                    if (sehHealthInfo3.batteryOnline == batteryService.mLastBatteryOnline) {
                        if (sehHealthInfo3.batteryChargeType == batteryService.mLastBatteryChargeType) {
                            if (sehHealthInfo3.batteryPowerSharingOnline == batteryService.mLastBatteryPowerSharingOnline) {
                                if (sehHealthInfo3.batteryHighVoltageCharger == batteryService.mLastBatteryHighVoltageCharger) {
                                    if (healthInfo6.batteryStatus == 2) {
                                    }
                                    if (sehHealthInfo3.chargerPogoOnline == batteryService.mLastchargerPogoOnline) {
                                        if (sehHealthInfo3.batteryEvent == batteryService.mLastBatteryEvent) {
                                            if (sehHealthInfo3.batteryCurrentEvent == batteryService.mLastBatteryCurrentEvent) {
                                                if (batteryService.mSecPlugTypeSummary == batteryService.mLastSecPlugTypeSummary) {
                                                    if (batteryService.mInvalidCharger == batteryService.mLastInvalidCharger) {
                                                        if (healthInfo6.batteryCycleCount == batteryService.mLastBatteryCycleCount) {
                                                            if (healthInfo6.chargingState == batteryService.mLastCharingState) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int i16 = batteryService.mPlugType;
        int i17 = batteryService.mLastPlugType;
        if (i16 != i17) {
            if (i17 == 0) {
                batteryService.mChargeStartLevel = batteryService.mHealthInfo.batteryLevel;
                batteryService.mChargeStartTime = SystemClock.elapsedRealtime();
                LogMaker logMaker = new LogMaker(1417);
                logMaker.setType(4);
                logMaker.addTaggedData(1421, Integer.valueOf(batteryService.mPlugType));
                logMaker.addTaggedData(1418, Integer.valueOf(batteryService.mHealthInfo.batteryLevel));
                batteryService.mMetricsLogger.write(logMaker);
                if (batteryService.mDischargeStartTime != 0 && batteryService.mDischargeStartLevel != batteryService.mHealthInfo.batteryLevel) {
                    j = SystemClock.elapsedRealtime() - batteryService.mDischargeStartTime;
                    EventLog.writeEvent(2730, Long.valueOf(j), Integer.valueOf(batteryService.mDischargeStartLevel), Integer.valueOf(batteryService.mHealthInfo.batteryLevel));
                    batteryService.mDischargeStartTime = 0L;
                    z2 = true;
                    BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "Battery PlugType Changed", "mLastPlugType:" + batteryService.mLastPlugType + " ,mPlugType:" + batteryService.mPlugType + " ,mHealthInfo.batteryLevel:" + batteryService.mHealthInfo.batteryLevel + " ,mHealthInfo.batteryChargeTimeToFullNowSeconds:" + batteryService.mHealthInfo.batteryChargeTimeToFullNowSeconds);
                }
            } else if (i16 == 0) {
                batteryService.mDischargeStartTime = SystemClock.elapsedRealtime();
                batteryService.mDischargeStartLevel = batteryService.mHealthInfo.batteryLevel;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                long j4 = batteryService.mChargeStartTime;
                long j5 = elapsedRealtime - j4;
                if (j4 != 0 && j5 != 0) {
                    LogMaker logMaker2 = new LogMaker(1417);
                    logMaker2.setType(5);
                    logMaker2.addTaggedData(1421, Integer.valueOf(batteryService.mLastPlugType));
                    logMaker2.addTaggedData(1420, Long.valueOf(j5));
                    logMaker2.addTaggedData(1418, Integer.valueOf(batteryService.mChargeStartLevel));
                    logMaker2.addTaggedData(1419, Integer.valueOf(batteryService.mHealthInfo.batteryLevel));
                    batteryService.mMetricsLogger.write(logMaker2);
                }
                batteryService.mChargeStartTime = 0L;
            }
            j = 0;
            z2 = false;
            BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "Battery PlugType Changed", "mLastPlugType:" + batteryService.mLastPlugType + " ,mPlugType:" + batteryService.mPlugType + " ,mHealthInfo.batteryLevel:" + batteryService.mHealthInfo.batteryLevel + " ,mHealthInfo.batteryChargeTimeToFullNowSeconds:" + batteryService.mHealthInfo.batteryChargeTimeToFullNowSeconds);
        } else {
            j = 0;
            z2 = false;
        }
        if (batteryService.mDischargeStartLevel <= 0 && (i3 = batteryService.mHealthInfo.batteryLevel) > 0) {
            batteryService.mDischargeStartLevel = i3;
        }
        HealthInfo healthInfo7 = batteryService.mHealthInfo;
        int i18 = healthInfo7.batteryStatus;
        if (i18 != batteryService.mLastBatteryStatus || healthInfo7.batteryHealth != batteryService.mLastBatteryHealth || healthInfo7.batteryPresent != batteryService.mLastBatteryPresent || batteryService.mPlugType != batteryService.mLastPlugType) {
            EventLog.writeEvent(2723, Integer.valueOf(i18), Integer.valueOf(batteryService.mHealthInfo.batteryHealth), Integer.valueOf(batteryService.mHealthInfo.batteryPresent ? 1 : 0), Integer.valueOf(batteryService.mPlugType), batteryService.mHealthInfo.batteryTechnology);
            SystemProperties.set("debug.tracing.battery_status", Integer.toString(batteryService.mHealthInfo.batteryStatus));
            SystemProperties.set("debug.tracing.plug_type", Integer.toString(batteryService.mPlugType));
        }
        int i19 = batteryService.mHealthInfo.batteryLevel;
        if (i19 != batteryService.mLastBatteryLevel) {
            EventLog.writeEvent(2722, Integer.valueOf(i19), Integer.valueOf(batteryService.mHealthInfo.batteryVoltageMillivolts), Integer.valueOf(batteryService.mHealthInfo.batteryTemperatureTenthsCelsius));
            if (batteryService.mHealthInfo.batteryLevel < batteryService.mLastBatteryLevel) {
                long j6 = batteryService.mCurrentBatteryUsage + (r8 - r4);
                batteryService.mCurrentBatteryUsage = j6;
                if (j6 >= 10) {
                    batteryService.mHandlerForBatteryInfoBackUp.post(batteryService.mSaveBatteryUsageRunnable);
                }
                batteryService.mBatteryUsageSinceLastAsocUpdate += batteryService.mLastBatteryLevel - batteryService.mHealthInfo.batteryLevel;
            }
            if (batteryService.mBattInfoManager.isDualAuth()) {
                if (batteryService.mBattInfoManager.isAuthAvailable() && (batteryService.mBattInfoManager.asocData.isValueInvalid() || batteryService.mBatteryUsageSinceLastAsocUpdate >= 100)) {
                    batteryService.mHandlerForBatteryInfoBackUp.post(batteryService.mUpdateBatteryAsocRunnable);
                    batteryService.mBatteryUsageSinceLastAsocUpdate = 0;
                }
            } else if (batteryService.mSavedBatteryAsoc < 0 || batteryService.mBatteryUsageSinceLastAsocUpdate >= 100) {
                batteryService.mHandlerForBatteryInfoBackUp.post(batteryService.mUpdateBatteryAsocRunnable);
                batteryService.mBatteryUsageSinceLastAsocUpdate = 0;
            }
        }
        if (batteryService.mBatteryLevelCritical && !batteryService.mLastBatteryLevelCritical && batteryService.mPlugType == 0) {
            j = SystemClock.elapsedRealtime() - batteryService.mDischargeStartTime;
            z2 = true;
        }
        if (!batteryService.mBatteryLevelLow) {
            if (batteryService.mPlugType == 0) {
                HealthInfo healthInfo8 = batteryService.mHealthInfo;
                if (healthInfo8.batteryStatus != 1 && healthInfo8.batteryLevel <= batteryService.mLowBatteryWarningLevel) {
                    batteryService.mBatteryLevelLow = true;
                }
            }
        } else if (batteryService.mPlugType != 0) {
            batteryService.mBatteryLevelLow = false;
        } else {
            int i20 = batteryService.mHealthInfo.batteryLevel;
            if (i20 >= batteryService.mLowBatteryCloseWarningLevel) {
                batteryService.mBatteryLevelLow = false;
            } else if (z && i20 >= batteryService.mLowBatteryWarningLevel) {
                batteryService.mBatteryLevelLow = false;
            }
        }
        batteryService.mSequence++;
        if (FEATURE_WIRELESS_FAST_CHARGER_CONTROL && batteryService.mSehHealthInfo.batteryOnline == 100 && !batteryService.mWasUsedWirelessFastChargerPreviously) {
            batteryService.mWasUsedWirelessFastChargerPreviously = true;
            Slog.m72d(TAG, "enable wireless charger menu in setting");
            Settings.System.putIntForUser(batteryService.mContext.getContentResolver(), "show_wireless_charger_menu", 1, -2);
            batteryService.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.21
                @Override // java.lang.Runnable
                public void run() {
                    Slog.m76i(BatteryService.TAG, "send intent to device care for showing menu");
                    Intent intent = new Intent("com.sec.android.settings.ENABLE_WIRELESS_CHARGER_MENU");
                    intent.setFlags(16777216);
                    BatteryService.sendBroadcastToExplicitPackage(BatteryService.this.mContext, intent, BatteryService.PACKAGE_DEVICE_CARE);
                }
            });
        }
        int i21 = batteryService.mPlugType;
        if (i21 != 0 && ((i2 = batteryService.mLastPlugType) == 0 || i2 == -1)) {
            final Intent intent = new Intent("android.intent.action.ACTION_POWER_CONNECTED");
            intent.setFlags(67108864);
            intent.putExtra("seq", batteryService.mSequence);
            batteryService.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.22
                @Override // java.lang.Runnable
                public void run() {
                    BatteryService.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, null, BatteryService.this.mPowerOptions);
                    if (BatteryService.FEATURE_SUPPORTED_DAILY_BOARD) {
                        Intent intent2 = (Intent) intent.clone();
                        intent2.addFlags(32);
                        BatteryService.sendBroadcastToExplicitPackage(BatteryService.this.mContext, intent2, "com.samsung.android.homemode");
                    }
                    if ("VZW".equals(SemCscFeature.getInstance().getString("CscFeature_COMMON_ConfigImplicitBroadcasts"))) {
                        BatteryService.sendBroadcastToExplicitPackage(BatteryService.this.mContext, intent, "com.verizon.mips.services");
                    }
                    String deviceSecurityPackageName = BatteryService.this.getDeviceSecurityPackageName();
                    if (deviceSecurityPackageName != null) {
                        BatteryService.sendBroadcastToExplicitPackage(BatteryService.this.mContext, intent, deviceSecurityPackageName);
                    }
                }
            });
        } else if (i21 == 0 && ((i = batteryService.mLastPlugType) != 0 || i == -1)) {
            final Intent intent2 = new Intent("android.intent.action.ACTION_POWER_DISCONNECTED");
            intent2.setFlags(67108864);
            intent2.putExtra("seq", batteryService.mSequence);
            batteryService.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.23
                @Override // java.lang.Runnable
                public void run() {
                    BatteryService.this.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL, null, BatteryService.this.mPowerOptions);
                    if (BatteryService.FEATURE_SUPPORTED_DAILY_BOARD) {
                        Intent intent3 = (Intent) intent2.clone();
                        intent3.addFlags(32);
                        BatteryService.sendBroadcastToExplicitPackage(BatteryService.this.mContext, intent3, "com.samsung.android.homemode");
                    }
                    if ("VZW".equals(SemCscFeature.getInstance().getString("CscFeature_COMMON_ConfigImplicitBroadcasts"))) {
                        BatteryService.sendBroadcastToExplicitPackage(BatteryService.this.mContext, intent2, "com.verizon.mips.services");
                    }
                    String deviceSecurityPackageName = BatteryService.this.getDeviceSecurityPackageName();
                    if (deviceSecurityPackageName != null) {
                        BatteryService.sendBroadcastToExplicitPackage(BatteryService.this.mContext, intent2, deviceSecurityPackageName);
                    }
                }
            });
        }
        if (shouldSendBatteryLowLocked()) {
            batteryService.mSentLowBatteryBroadcast = true;
            final Intent intent3 = new Intent("android.intent.action.BATTERY_LOW");
            intent3.setFlags(67108864);
            intent3.putExtra("seq", batteryService.mSequence);
            batteryService.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.24
                @Override // java.lang.Runnable
                public void run() {
                    BatteryService.this.mContext.sendBroadcastAsUser(intent3, UserHandle.ALL, null, BatteryService.this.mBatteryOptions);
                }
            });
        } else if (batteryService.mSentLowBatteryBroadcast && batteryService.mHealthInfo.batteryLevel >= batteryService.mLowBatteryCloseWarningLevel) {
            batteryService.mSentLowBatteryBroadcast = false;
            final Intent intent4 = new Intent("android.intent.action.BATTERY_OKAY");
            intent4.setFlags(67108864);
            intent4.putExtra("seq", batteryService.mSequence);
            batteryService.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.25
                @Override // java.lang.Runnable
                public void run() {
                    BatteryService.this.mContext.sendBroadcastAsUser(intent4, UserHandle.ALL, null, BatteryService.this.mBatteryOptions);
                }
            });
        }
        HealthInfo healthInfo9 = batteryService.mHealthInfo;
        int i22 = healthInfo9.batteryStatus;
        if (i22 == 2 && batteryService.mSehHealthInfo.batteryHighVoltageCharger < 3 && !healthInfo9.chargerWirelessOnline) {
            if (i22 != batteryService.mLastBatteryStatus) {
                Slog.m72d(TAG, "skip send broadcast ACTION_BATTERY_CHANGED for 250ms");
                batteryService.mIsSkipActionBatteryChanged = true;
                batteryService.mSkipActionBatteryChangedHandler.sendEmptyMessageDelayed(1, 250L);
            }
        } else if (batteryService.mIsSkipActionBatteryChanged) {
            batteryService.mIsSkipActionBatteryChanged = false;
            batteryService.mSkipActionBatteryChangedHandler.removeCallbacksAndMessages(null);
        }
        if (!batteryService.mIsSkipActionBatteryChanged) {
            sendBatteryChangedIntentLocked();
        }
        sendBatteryEventIntentLocked();
        if (batteryService.mLastBatteryLevel != batteryService.mHealthInfo.batteryLevel || batteryService.mLastPlugType != batteryService.mPlugType) {
            sendBatteryLevelChangedIntentLocked();
        }
        batteryService.sendDeteriorationIntentLocked(false);
        batteryService.mLed.updateLightsLocked();
        if (z2 && j != 0) {
            batteryService.logOutlierLocked(j);
        }
        HealthInfo healthInfo10 = batteryService.mHealthInfo;
        batteryService.mLastBatteryStatus = healthInfo10.batteryStatus;
        batteryService.mLastBatteryHealth = healthInfo10.batteryHealth;
        batteryService.mLastBatteryPresent = healthInfo10.batteryPresent;
        batteryService.mLastBatteryLevel = healthInfo10.batteryLevel;
        batteryService.mLastPlugType = batteryService.mPlugType;
        batteryService.mLastBatteryVoltage = healthInfo10.batteryVoltageMillivolts;
        batteryService.mLastBatteryTemperature = healthInfo10.batteryTemperatureTenthsCelsius;
        batteryService.mLastMaxChargingCurrent = healthInfo10.maxChargingCurrentMicroamps;
        batteryService.mLastMaxChargingVoltage = healthInfo10.maxChargingVoltageMicrovolts;
        batteryService.mLastBatteryLevelCritical = batteryService.mBatteryLevelCritical;
        batteryService.mLastInvalidCharger = batteryService.mInvalidCharger;
        batteryService.mLastBatteryCycleCount = healthInfo10.batteryCycleCount;
        batteryService.mLastCharingState = healthInfo10.chargingState;
        SehHealthInfo sehHealthInfo4 = batteryService.mSehHealthInfo;
        batteryService.mLastBatteryOnline = sehHealthInfo4.batteryOnline;
        batteryService.mLastBatteryChargeType = sehHealthInfo4.batteryChargeType;
        batteryService.mLastBatteryPowerSharingOnline = sehHealthInfo4.batteryPowerSharingOnline;
        batteryService.mLastBatteryHighVoltageCharger = sehHealthInfo4.batteryHighVoltageCharger;
        batteryService.mLastBatteryCurrentNow = sehHealthInfo4.batteryCurrentNow;
        batteryService.mLastchargerPogoOnline = sehHealthInfo4.chargerPogoOnline;
        batteryService.mLastBatteryEvent = sehHealthInfo4.batteryEvent;
        batteryService.mLastBatteryCurrentEvent = sehHealthInfo4.batteryCurrentEvent;
        batteryService.mLastSecPlugTypeSummary = batteryService.mSecPlugTypeSummary;
        batteryService.mLastWcTxId = batteryService.mWcTxId;
        if (FEATURE_FULL_BATTERY_CYCLE) {
            z3 = false;
            batteryService.logFullBatteryDurationLocked(false);
        } else {
            z3 = false;
        }
        if (PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING) {
            sendWirelessPowerSharingIntentLocked();
        }
        if (FEATURE_HICCUP_CONTROL && batteryService.mBootCompleted && batteryService.mIsHiccupPopupShowing) {
            int i23 = batteryService.mSehHealthInfo.batteryEvent;
            if ((i23 & 32) != 0) {
                fileWriteInt("/sys/class/power_supply/battery/batt_misc_event", i23);
            }
        }
        int i24 = batteryService.mHealthInfo.batteryTemperatureTenthsCelsius;
        if (i24 > batteryService.mBatteryMaxTemp) {
            batteryService.mBatteryMaxTemp = i24;
            batteryService.mHandlerForBatteryInfoBackUp.post(batteryService.mSaveBatteryMaxTempRunnable);
        }
        int i25 = batteryService.mSehHealthInfo.batteryCurrentNow;
        if (i25 > batteryService.mBatteryMaxCurrent) {
            batteryService.mBatteryMaxCurrent = i25;
            batteryService.mHandlerForBatteryInfoBackUp.post(batteryService.mSaveBatteryMaxCurrentRunnable);
        }
        boolean z4 = batteryService.mHealthInfo.chargerWirelessOnline;
        if (z4 != batteryService.mLastWirelessChargingStatus) {
            batteryService.mLastWirelessChargingStatus = z4;
            batteryService.setWirelessChargingState(z4);
        }
        boolean z5 = (batteryService.mSehHealthInfo.batteryEvent & 64) != 0 ? true : z3;
        if (batteryService.mLastWirelessPinDetected != z5) {
            batteryService.mLastWirelessPinDetected = z5;
            batteryService.setWirelessChargingState(z5);
        }
        boolean z6 = (batteryService.mSehHealthInfo.batteryEvent & 2) != 0 ? true : z3;
        if (batteryService.mLastWirelessBackPackChargingStatus != z6) {
            if (batteryService.mHealthInfo.chargerWirelessOnline && z6) {
                String str = TAG;
                Slog.m72d(str, "notify wireless backpack on");
                batteryService.mLatestWirelessChargingMode = 3;
                SemInputDeviceManager semInputDeviceManager = batteryService.mSemInputDeviceManager;
                if (semInputDeviceManager != null) {
                    semInputDeviceManager.setWirelessChargingMode(11, 3);
                    batteryService.mSemInputDeviceManager.setWirelessChargingMode(1, batteryService.mLatestWirelessChargingMode);
                    Slog.m72d(str, "setWirelessChargingMode(All): " + batteryService.mLatestWirelessChargingMode);
                }
            }
            batteryService.mLastWirelessBackPackChargingStatus = z6;
        }
    }

    public final void sendBatteryChangedIntentLocked() {
        int i;
        int i2;
        final Intent intent = new Intent("android.intent.action.BATTERY_CHANGED");
        intent.addFlags(1610612736);
        final Intent intent2 = new Intent("android.intent.action.DOCK_EVENT");
        intent2.addFlags(536870912);
        int i3 = this.mPogoCondition;
        int i4 = this.mPogoDockState;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.mSehHealthInfo.chargerPogoOnline) {
            HealthInfo healthInfo = this.mHealthInfo;
            if (healthInfo.chargerAcOnline || healthInfo.chargerUsbOnline || healthInfo.chargerWirelessOnline) {
                this.mPogoCondition = 2;
            } else {
                this.mPogoCondition = 1;
            }
            this.mPogoDockState = 1;
        } else {
            this.mPogoDockState = 0;
            this.mPogoCondition = 0;
        }
        if (i4 != this.mPogoDockState || i3 != this.mPogoCondition) {
            if (this.mActivityManagerReady) {
                i = Settings.Global.getInt(contentResolver, "device_provisioned", 0);
                i2 = Settings.System.getInt(this.mContext.getContentResolver(), "kids_home_mode", 0);
            } else {
                i = 0;
                i2 = 0;
            }
            if (i != 0 && i2 == 0) {
                intent2.putExtra("android.intent.extra.DOCK_STATE", this.mPogoDockState);
                intent2.putExtra("pogo_plugged", this.mPogoCondition);
                this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.26
                    @Override // java.lang.Runnable
                    public void run() {
                        Slog.m72d(BatteryService.TAG, "Sending ACTION_DOCK_EVENT. dock_state:" + BatteryService.this.mPogoDockState + ", pogo_plugged:" + BatteryService.this.mPogoCondition);
                        ActivityManager.broadcastStickyIntent(intent2, -1);
                    }
                });
            } else {
                Slog.m72d(TAG, "device_provisioned: " + i + "kids_home_mode: " + i2);
            }
        }
        int iconLocked = getIconLocked(this.mHealthInfo.batteryLevel);
        boolean z = this.mSehHealthInfo.batteryHighVoltageCharger != 0;
        intent.putExtra("seq", this.mSequence);
        intent.putExtra("status", this.mHealthInfo.batteryStatus);
        intent.putExtra("health", this.mHealthInfo.batteryHealth);
        intent.putExtra("present", this.mHealthInfo.batteryPresent);
        intent.putExtra("level", this.mHealthInfo.batteryLevel);
        intent.putExtra("battery_low", this.mSentLowBatteryBroadcast);
        intent.putExtra("scale", 100);
        intent.putExtra("icon-small", iconLocked);
        intent.putExtra("plugged", this.mPlugType);
        intent.putExtra("voltage", this.mHealthInfo.batteryVoltageMillivolts);
        intent.putExtra("temperature", this.mHealthInfo.batteryTemperatureTenthsCelsius);
        intent.putExtra("technology", this.mHealthInfo.batteryTechnology);
        intent.putExtra("invalid_charger", this.mInvalidCharger);
        intent.putExtra("max_charging_current", this.mHealthInfo.maxChargingCurrentMicroamps);
        intent.putExtra("max_charging_voltage", this.mHealthInfo.maxChargingVoltageMicrovolts);
        intent.putExtra("charge_counter", this.mHealthInfo.batteryChargeCounterUah);
        intent.putExtra("android.os.extra.CYCLE_COUNT", this.mHealthInfo.batteryCycleCount);
        intent.putExtra("android.os.extra.CHARGING_STATUS", this.mHealthInfo.chargingState);
        intent.putExtra("online", this.mSehHealthInfo.batteryOnline);
        intent.putExtra("charge_type", this.mSehHealthInfo.batteryChargeType);
        intent.putExtra("power_sharing", this.mSehHealthInfo.batteryPowerSharingOnline);
        intent.putExtra("hv_charger", z);
        intent.putExtra("charger_type", this.mSehHealthInfo.batteryHighVoltageCharger);
        intent.putExtra("capacity", this.mBatteryCapacity);
        intent.putExtra("current_now", this.mSehHealthInfo.batteryCurrentNow);
        intent.putExtra("pogo_plugged", this.mPogoCondition);
        intent.putExtra("misc_event", this.mSehHealthInfo.batteryEvent);
        intent.putExtra("current_event", this.mSehHealthInfo.batteryCurrentEvent);
        Slog.m72d(TAG, "Sending ACTION_BATTERY_CHANGED: level:" + this.mHealthInfo.batteryLevel + ", status:" + this.mHealthInfo.batteryStatus + ", health:" + this.mHealthInfo.batteryHealth + ", remain:" + this.mHealthInfo.batteryChargeTimeToFullNowSeconds + ", ac:" + this.mHealthInfo.chargerAcOnline + ", usb:" + this.mHealthInfo.chargerUsbOnline + ", wireless:" + this.mHealthInfo.chargerWirelessOnline + ", pogo:" + this.mSehHealthInfo.chargerPogoOnline + ", misc:0x" + Integer.toHexString(this.mSehHealthInfo.batteryEvent) + ", charge_type:" + this.mSehHealthInfo.batteryChargeType + ", charger_type:" + this.mSehHealthInfo.batteryHighVoltageCharger + ", voltage:" + this.mHealthInfo.batteryVoltageMillivolts + ", temperature:" + this.mHealthInfo.batteryTemperatureTenthsCelsius + ", online:" + this.mSehHealthInfo.batteryOnline + ", charging_status:" + this.mHealthInfo.chargingState + ", cycle_count:" + this.mHealthInfo.batteryCycleCount + ", current_avg:" + this.mHealthInfo.batteryCurrentAverageMicroamps + ", ps:" + this.mSehHealthInfo.batteryPowerSharingOnline + ", hvc:" + z + ", capacity:" + this.mBatteryCapacity + ", current_event:0x" + Integer.toHexString(this.mSehHealthInfo.batteryCurrentEvent) + ", current_now:" + this.mSehHealthInfo.batteryCurrentNow + ", mcc:" + this.mHealthInfo.maxChargingCurrentMicroamps + ", mcv:" + this.mHealthInfo.maxChargingVoltageMicrovolts + ", cc:" + this.mHealthInfo.batteryChargeCounterUah + ", present:" + this.mHealthInfo.batteryPresent + ", scale:100, technology:" + this.mHealthInfo.batteryTechnology);
        this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BatteryService.this.lambda$sendBatteryChangedIntentLocked$0(intent);
            }
        });
        if (this.mShouldCheckFirstUseDateRegularly && SystemClock.elapsedRealtime() > 180000) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.27
                @Override // java.lang.Runnable
                public void run() {
                    BatteryService.this.updateBatteryDate();
                }
            });
        }
        if (this.mBattInfoManager.isDualAuth() && SystemClock.elapsedRealtime() > 90000 && this.mBattInfoManager.isAuthAvailable()) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.28
                @Override // java.lang.Runnable
                public void run() {
                    if (BatteryService.this.mBattInfoManager.firstUseDateData.shouldCheck) {
                        BatteryService.this.mBattInfoManager.firstUseDateData.updateDate();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendBatteryChangedIntentLocked$0(Intent intent) {
        ActivityManager.broadcastStickyIntent(intent, -1, this.mBatteryChangedOptions, -1);
    }

    public final void sendBatteryEventIntentLocked() {
        int i = this.mSehHealthInfo.batteryEvent;
        if (i == this.mLastBatteryEvent && this.mSecPlugTypeSummary == this.mLastSecPlugTypeSummary && this.mWcTxId == this.mLastWcTxId) {
            return;
        }
        final boolean z = (i & 1) != 0;
        if (this.mLastBatteryEventWaterInConnector != z) {
            final Intent intent = new Intent("com.samsung.server.BatteryService.action.SEC_BATTERY_WATER_IN_CONNECTOR");
            intent.addFlags(603979776);
            intent.putExtra("water", z);
            this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.29
                @Override // java.lang.Runnable
                public void run() {
                    Slog.m72d(BatteryService.TAG, "Sending ACTION_SEC_BATTERY_WATER_IN_CONNECTOR. water : " + z);
                    ActivityManager.broadcastStickyIntent(intent, -1);
                }
            });
            this.mLastBatteryEventWaterInConnector = z;
        }
        if (this.mSehHealthInfo.batteryEvent == this.mLastBatteryEvent && this.mSecPlugTypeSummary == this.mLastSecPlugTypeSummary) {
            return;
        }
        final Intent intent2 = new Intent("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT");
        intent2.addFlags(603979776);
        intent2.putExtra("misc_event", this.mSehHealthInfo.batteryEvent);
        intent2.putExtra("sec_plug_type", this.mSecPlugTypeSummary);
        intent2.putExtra("wc_tx_id", this.mWcTxId);
        this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.30
            @Override // java.lang.Runnable
            public void run() {
                Slog.m72d(BatteryService.TAG, "misc_event:0x" + Integer.toHexString(BatteryService.this.mSehHealthInfo.batteryEvent) + ", sec plug type:" + BatteryService.this.mSecPlugTypeSummary + ", wc_tx_id:" + BatteryService.this.mWcTxId);
                ActivityManager.broadcastStickyIntent(intent2, -1);
            }
        });
    }

    public final void sendDeteriorationIntentLocked(boolean z) {
        if ("r0q,r0s,g0q,g0s,b0q,b0s".contains(Build.DEVICE)) {
            sendBroadcastDeterioration(z);
        } else {
            sendBroadcastDeteriorationLegacy(z);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sendBroadcastDeterioration(boolean z) {
        int i;
        double d;
        boolean z2;
        final int i2;
        String readFromFile;
        String readFromFile2;
        if (isFileSupported("/efs/FactoryApp/cisd_data") && (readFromFile2 = readFromFile("/efs/FactoryApp/cisd_data", z)) != null) {
            try {
                i = Integer.parseInt(readFromFile2.split(" ")[10].trim());
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                Slog.m75e(TAG, "sendDeteriorationIntentLocked highSwellingCnt", e);
            }
            if (isFileSupported("/efs/FactoryApp/bsoh") && (readFromFile = readFromFile("/efs/FactoryApp/bsoh", z)) != null) {
                try {
                    d = Double.parseDouble(readFromFile.trim());
                } catch (NumberFormatException e2) {
                    Slog.m75e(TAG, "sendDeteriorationIntentLocked nfe", e2);
                }
                z2 = true;
                if (i <= 5000 || Double.compare(d, 55.0d) < 0) {
                    Slog.m74e(TAG, "sendBroadcastDeterioration hsc : " + i + ", bsoh : " + d);
                    i2 = 15;
                } else {
                    i2 = 1;
                }
                if (!z) {
                    if (this.mLastDeterioration != i2 && this.mBootCompleted && i2 == 15) {
                        r1 = this.mIsFirstIntentSended ? 10L : 10000L;
                        this.mLastDeterioration = i2;
                    } else {
                        z2 = false;
                        r1 = 10;
                    }
                }
                if (z2) {
                    return;
                }
                final Intent intent = new Intent("com.samsung.server.BatteryService.action.ACTION_POPUP_BATTERY_DETERIORATION");
                intent.putExtra("deterioration", i2);
                intent.addFlags(268435456);
                intent.setPackage(PACKAGE_DEVICE_CARE);
                this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.BatteryService.31
                    @Override // java.lang.Runnable
                    public void run() {
                        BatteryService.this.mIsFirstIntentSended = true;
                        Slog.m72d(BatteryService.TAG, "Sending ACTION_POPUP_BATTERY_DETERIORATION : " + i2);
                        ActivityManager.broadcastStickyIntent(intent, -1);
                    }
                }, r1);
                return;
            }
            d = 101.0d;
            z2 = true;
            if (i <= 5000) {
            }
            Slog.m74e(TAG, "sendBroadcastDeterioration hsc : " + i + ", bsoh : " + d);
            i2 = 15;
            if (!z) {
            }
            if (z2) {
            }
        }
        i = -1;
        if (isFileSupported("/efs/FactoryApp/bsoh")) {
            d = Double.parseDouble(readFromFile.trim());
            z2 = true;
            if (i <= 5000) {
            }
            Slog.m74e(TAG, "sendBroadcastDeterioration hsc : " + i + ", bsoh : " + d);
            i2 = 15;
            if (!z) {
            }
            if (z2) {
            }
        }
        d = 101.0d;
        z2 = true;
        if (i <= 5000) {
        }
        Slog.m74e(TAG, "sendBroadcastDeterioration hsc : " + i + ", bsoh : " + d);
        i2 = 15;
        if (!z) {
        }
        if (z2) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0022, code lost:
    
        if (r7.mIsFirstIntentSended == false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:3:0x0011, code lost:
    
        if (r0 != 0) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sendBroadcastDeteriorationLegacy(boolean z) {
        final int i = (this.mSehHealthInfo.batteryEvent & 983040) >> 16;
        boolean z2 = true;
        long j = 10000;
        if (!z) {
            if (this.mLastDeterioration != i) {
                if (!this.mBootCompleted || i != 15) {
                    z2 = false;
                }
                j = 10;
                this.mLastDeterioration = i;
            }
            z2 = false;
            j = 10;
        }
        if (z2) {
            final Intent intent = new Intent("com.samsung.server.BatteryService.action.ACTION_POPUP_BATTERY_DETERIORATION");
            intent.putExtra("deterioration", i);
            intent.addFlags(268435456);
            intent.setPackage(PACKAGE_DEVICE_CARE);
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.BatteryService.32
                @Override // java.lang.Runnable
                public void run() {
                    BatteryService.this.mIsFirstIntentSended = true;
                    Slog.m72d(BatteryService.TAG, "Sending ACTION_POPUP_BATTERY_DETERIORATION : " + i);
                    ActivityManager.broadcastStickyIntent(intent, -1);
                }
            }, j);
        }
    }

    public final void sendOTGIntentLocked() {
        final Intent intent = new Intent("android.intent.action.RESPONSE_OTG_CHARGE_BLOCK");
        this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.33
            @Override // java.lang.Runnable
            public void run() {
                Slog.m72d(BatteryService.TAG, "Sending RESPONSE_OTG_CHARGE_BLOCK.");
                ActivityManager.broadcastStickyIntent(intent, -1);
            }
        });
    }

    public final void sendWirelessPowerSharingIntentLocked() {
        SemInputDeviceManager semInputDeviceManager;
        synchronized (this.mLock) {
            final int i = this.mSehHealthInfo.wirelessPowerSharingTxEvent;
            final boolean z = (i & 1) != 0;
            if (this.mLastTxEventTxEnabled != z) {
                final Intent intent = new Intent("com.samsung.server.BatteryService.action.WIRELESS_POWER_SHARING_ENABLED");
                intent.putExtra("enabled", z);
                intent.addFlags(268435456);
                intent.setPackage(PACKAGE_DEVICE_CARE);
                this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.34
                    @Override // java.lang.Runnable
                    public void run() {
                        Slog.m72d(BatteryService.TAG, "Sending ACTION_WIRELESS_POWER_SHARING_ENABLED. enabled : " + z);
                        ActivityManager.broadcastStickyIntent(intent, -1);
                    }
                });
                this.mLastTxEventTxEnabled = z;
                int i2 = z ? 1 : 0;
                this.mLatestWirelessChargingMode = i2;
                SemInputDeviceManager semInputDeviceManager2 = this.mSemInputDeviceManager;
                if (semInputDeviceManager2 != null) {
                    semInputDeviceManager2.setWirelessChargingMode(11, i2);
                    Slog.m72d(TAG, "setWirelessChargingMode(DEVID_SPEN): " + this.mLatestWirelessChargingMode);
                }
                if (this.mIsWirelessTxSupported && this.mBootCompleted && (semInputDeviceManager = this.mSemInputDeviceManager) != null) {
                    semInputDeviceManager.setWirelessChargingMode(1, this.mLatestWirelessChargingMode);
                    Slog.m72d(TAG, "setWirelessChargingMode(TSP): " + this.mLatestWirelessChargingMode);
                }
            }
            final boolean z2 = (i & 2) != 0;
            if (this.mLastTxEventRxConnected != z2) {
                final Intent intent2 = new Intent("com.samsung.server.BatteryService.action.WIRELESS_POWER_SHARING_CONNECTED");
                intent2.putExtra("connected", z2);
                intent2.addFlags(268435456);
                intent2.setPackage(PACKAGE_DEVICE_CARE);
                this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.35
                    @Override // java.lang.Runnable
                    public void run() {
                        Slog.m72d(BatteryService.TAG, "Sending ACTION_WIRELESS_POWER_SHARING_CONNECTED. connected : " + z2);
                        BatteryService.this.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
                        try {
                            if (z2) {
                                BatteryService.this.mBatteryStats.noteStartTxPowerSharing();
                            } else {
                                BatteryService.this.mBatteryStats.noteStopTxPowerSharing();
                            }
                        } catch (RemoteException unused) {
                            Slog.m74e(BatteryService.TAG, "Failed to note battery stats in BatteryService");
                        }
                    }
                });
                this.mLastTxEventRxConnected = z2;
            }
            if (i != this.mLastWirelessPowerSharingTxEvent) {
                final Intent intent3 = new Intent("com.samsung.server.BatteryService.action.WIRELESS_POWER_SHARING_TX_EVENT");
                intent3.putExtra("tx_event", i);
                intent3.addFlags(268435456);
                this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.36
                    @Override // java.lang.Runnable
                    public void run() {
                        Slog.m72d(BatteryService.TAG, "tx_event:0x" + Integer.toHexString(i));
                        ActivityManager.broadcastStickyIntent(intent3, -1);
                        Intent intent4 = (Intent) intent3.clone();
                        intent4.setPackage(BatteryService.PACKAGE_DEVICE_CARE);
                        ActivityManager.broadcastStickyIntent(intent4, -1);
                    }
                });
                this.mLastWirelessPowerSharingTxEvent = i;
            }
        }
    }

    public final void sendBatteryLevelChangedIntentLocked() {
        Bundle bundle = new Bundle();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        bundle.putInt("seq", this.mSequence);
        bundle.putInt("status", this.mHealthInfo.batteryStatus);
        bundle.putInt("health", this.mHealthInfo.batteryHealth);
        bundle.putBoolean("present", this.mHealthInfo.batteryPresent);
        bundle.putInt("level", this.mHealthInfo.batteryLevel);
        bundle.putBoolean("battery_low", this.mSentLowBatteryBroadcast);
        bundle.putInt("scale", 100);
        bundle.putInt("plugged", this.mPlugType);
        bundle.putInt("voltage", this.mHealthInfo.batteryVoltageMillivolts);
        bundle.putInt("temperature", this.mHealthInfo.batteryTemperatureTenthsCelsius);
        bundle.putInt("charge_counter", this.mHealthInfo.batteryChargeCounterUah);
        bundle.putLong("android.os.extra.EVENT_TIMESTAMP", elapsedRealtime);
        bundle.putInt("android.os.extra.CYCLE_COUNT", this.mHealthInfo.batteryCycleCount);
        bundle.putInt("android.os.extra.CHARGING_STATUS", this.mHealthInfo.chargingState);
        boolean isEmpty = this.mBatteryLevelsEventQueue.isEmpty();
        this.mBatteryLevelsEventQueue.add(bundle);
        if (this.mBatteryLevelsEventQueue.size() > 100) {
            this.mBatteryLevelsEventQueue.removeFirst();
        }
        if (isEmpty) {
            long j = this.mLastBatteryLevelChangedSentMs;
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    BatteryService.this.sendEnqueuedBatteryLevelChangedEvents();
                }
            }, elapsedRealtime - j > 60000 ? 0L : (j + 60000) - elapsedRealtime);
        }
    }

    public final void sendEnqueuedBatteryLevelChangedEvents() {
        ArrayList<? extends Parcelable> arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList<>(this.mBatteryLevelsEventQueue);
            this.mBatteryLevelsEventQueue.clear();
        }
        Intent intent = new Intent("android.intent.action.BATTERY_LEVEL_CHANGED");
        intent.addFlags(16777216);
        intent.putParcelableArrayListExtra("android.os.extra.EVENTS", arrayList);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "android.permission.BATTERY_STATS");
        this.mLastBatteryLevelChangedSentMs = SystemClock.elapsedRealtime();
    }

    public final void logBatteryStatsLocked() {
        DropBoxManager dropBoxManager;
        File file;
        String str;
        StringBuilder sb;
        FileOutputStream fileOutputStream;
        IBinder service = ServiceManager.getService("batterystats");
        if (service == null || (dropBoxManager = (DropBoxManager) this.mContext.getSystemService("dropbox")) == null || !dropBoxManager.isTagEnabled("BATTERY_DISCHARGE_INFO")) {
            return;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                file = new File("/data/system/batterystats.dump");
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (RemoteException e) {
                    e = e;
                } catch (IOException e2) {
                    e = e2;
                }
            } catch (Throwable th) {
                th = th;
            }
        } catch (RemoteException e3) {
            e = e3;
            file = null;
        } catch (IOException e4) {
            e = e4;
            file = null;
        } catch (Throwable th2) {
            th = th2;
            file = null;
        }
        try {
            service.dump(fileOutputStream.getFD(), DUMPSYS_ARGS);
            FileUtils.sync(fileOutputStream);
            dropBoxManager.addFile("BATTERY_DISCHARGE_INFO", file, 2);
            try {
                fileOutputStream.close();
            } catch (IOException unused) {
                Slog.m74e(TAG, "failed to close dumpsys output stream");
            }
        } catch (RemoteException e5) {
            e = e5;
            fileOutputStream2 = fileOutputStream;
            Slog.m75e(TAG, "failed to dump battery service", e);
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused2) {
                    Slog.m74e(TAG, "failed to close dumpsys output stream");
                }
            }
            if (file == null || file.delete()) {
                return;
            }
            str = TAG;
            sb = new StringBuilder();
            sb.append("failed to delete temporary dumpsys file: ");
            sb.append(file.getAbsolutePath());
            Slog.m74e(str, sb.toString());
        } catch (IOException e6) {
            e = e6;
            fileOutputStream2 = fileOutputStream;
            Slog.m75e(TAG, "failed to write dumpsys file", e);
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused3) {
                    Slog.m74e(TAG, "failed to close dumpsys output stream");
                }
            }
            if (file == null || file.delete()) {
                return;
            }
            str = TAG;
            sb = new StringBuilder();
            sb.append("failed to delete temporary dumpsys file: ");
            sb.append(file.getAbsolutePath());
            Slog.m74e(str, sb.toString());
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused4) {
                    Slog.m74e(TAG, "failed to close dumpsys output stream");
                }
            }
            if (file == null) {
                throw th;
            }
            if (file.delete()) {
                throw th;
            }
            Slog.m74e(TAG, "failed to delete temporary dumpsys file: " + file.getAbsolutePath());
            throw th;
        }
        if (file.delete()) {
            return;
        }
        str = TAG;
        sb = new StringBuilder();
        sb.append("failed to delete temporary dumpsys file: ");
        sb.append(file.getAbsolutePath());
        Slog.m74e(str, sb.toString());
    }

    public final void logOutlierLocked(long j) {
        String str;
        String str2;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.mActivityManagerReady) {
            str = Settings.Global.getString(contentResolver, "battery_discharge_threshold");
            str2 = Settings.Global.getString(contentResolver, "battery_discharge_duration_threshold");
        } else {
            str = null;
            str2 = null;
        }
        if (str == null || str2 == null) {
            return;
        }
        try {
            long parseLong = Long.parseLong(str2);
            int parseInt = Integer.parseInt(str);
            if (j > parseLong || this.mDischargeStartLevel - this.mHealthInfo.batteryLevel < parseInt) {
                return;
            }
            logBatteryStatsLocked();
        } catch (NumberFormatException unused) {
            Slog.m74e(TAG, "Invalid DischargeThresholds GService string: " + str2 + " or " + str);
        }
    }

    public final int getIconLocked(int i) {
        int i2 = this.mHealthInfo.batteryStatus;
        if (i2 == 2) {
            return 17304234;
        }
        if (i2 == 3) {
            return 17304220;
        }
        if (i2 == 4 || i2 == 5) {
            return (!isPoweredLocked(15) || this.mHealthInfo.batteryLevel < 100) ? 17304220 : 17304234;
        }
        return 17304248;
    }

    public final String getDeviceSecurityPackageName() {
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SECURITY_CONFIG_DEVICEMONITOR_PACKAGE_NAME", "com.samsung.android.sm.devicesecurity");
        try {
            this.mContext.getPackageManager().getPackageInfo(string, 128);
            return string;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public boolean setOTGEnableDisable(boolean z) {
        return writeToFile("/sys/class/power_supply/otg/online", 0L, z);
    }

    public void updateAdaptiveFastChargingSetting(ContentResolver contentResolver) {
        this.mAdaptiveFastChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "adaptive_fast_charging", 1, -2) == 1;
        Slog.m72d(TAG, "!@AdaptiveFastCharging Settings = " + this.mAdaptiveFastChargingSettingsEnable);
        setAdaptiveFastCharging(this.mAdaptiveFastChargingSettingsEnable);
    }

    public void setAdaptiveFastCharging(boolean z) {
        this.mHealthServiceWrapper.sehWriteEnableToParam(this.mAdaptiveFastChargingOffset, !z, "afc");
        if (writeToFile(this.mAfcDisableSysFs, 0L, !z)) {
            Slog.m72d(TAG, "success to set AFC sysfs as " + z);
            return;
        }
        Slog.m72d(TAG, "fail to set AFC sysfs");
    }

    public void setSuperFastCharging(boolean z) {
        this.mHealthServiceWrapper.sehWriteEnableToParam(this.mSuperFastChargingOffset, !z, "sfc");
        if (writeToFile("/sys/class/power_supply/battery/pd_disable", 0L, !z)) {
            Slog.m72d(TAG, "success to set SFC sysfs as " + z);
            return;
        }
        Slog.m72d(TAG, "fail to set SFC sysfs");
    }

    public void setPassThrough(boolean z) {
        if (writeToFile("/sys/class/power_supply/battery/pass_through", 0L, z)) {
            Slog.m72d(TAG, "success to set PassThrough sysfs as " + z);
            return;
        }
        Slog.m72d(TAG, "fail to set PassThrough sysfs");
    }

    public boolean setHiccupDisable() {
        return fileWriteString("/sys/class/sec/switch/hiccup", "DISABLE");
    }

    public void setResponseHiccupEvent() {
        fileWriteInt("/sys/class/power_supply/battery/batt_misc_event", IInstalld.FLAG_FORCE);
    }

    public boolean setWirelessPowerSharing(boolean z) {
        return writeToFile("/sys/class/power_supply/battery/wc_tx_en", 0L, z);
    }

    public final void setWirelessPowerSharingExternelEventInternal(int i, int i2) {
        synchronized (this.mLock) {
            Slog.m76i(TAG, "setWirelessPowerSharingExternelEventInternal packageNum: " + i + " value: " + i2);
            int i3 = this.mLastWirelessPowerSharingExternelEvent;
            int i4 = ((~i) & i3) | i2;
            if (i4 != i3) {
                fileWriteInt("/sys/class/power_supply/battery/ext_event", i4);
                this.mLastWirelessPowerSharingExternelEvent = i4;
            }
        }
    }

    public final void setWirelessPowerSharingTxBatteryLimit(int i) {
        fileWriteInt("/sys/class/power_supply/battery/wc_tx_stop_capacity", i);
    }

    public boolean isSupportedWirelessTx() {
        InputManager inputManager = InputManager.getInstance();
        return (inputManager == null || (inputManager.semCheckInputFeature() & 16) == 0) ? false : true;
    }

    public void setWirelessChargingState(boolean z) {
        String str = TAG;
        Slog.m72d(str, "wirelessChargingState: " + z + ", notifyWirelessEnabled: " + this.mNotifyWirelessEnabled);
        if (z && !this.mNotifyWirelessEnabled) {
            Slog.m72d(str, "notify wireless on");
            this.mLatestWirelessChargingMode = 1;
            this.mNotifyWirelessEnabled = true;
            writeToFile("/sys/class/sec/switch/wireless", 0L, true);
        } else if (!z && this.mNotifyWirelessEnabled && !this.mLastWirelessChargingStatus && !this.mLastWirelessPinDetected) {
            Slog.m72d(str, "notify wireless off");
            this.mLatestWirelessChargingMode = 0;
            this.mNotifyWirelessEnabled = false;
            writeToFile("/sys/class/sec/switch/wireless", 0L, false);
        }
        SemInputDeviceManager semInputDeviceManager = this.mSemInputDeviceManager;
        if (semInputDeviceManager != null) {
            semInputDeviceManager.setWirelessChargingMode(11, this.mLatestWirelessChargingMode);
            this.mSemInputDeviceManager.setWirelessChargingMode(1, this.mLatestWirelessChargingMode);
            Slog.m72d(str, "setWirelessChargingMode(All): " + this.mLatestWirelessChargingMode);
        }
    }

    public void sendScreenState() {
        fileWriteInt("/sys/class/power_supply/battery/lcd", this.mScreenOn ? 1 : 0);
    }

    public final boolean fileWriteString(String str, String str2) {
        FileOutputStream fileOutputStream;
        IOException e;
        if (!new File(str).exists()) {
            Slog.m72d(TAG, "fileWriteString : file not found:" + str);
            return false;
        }
        if (str2 == null) {
            Slog.m74e(TAG, "fileWriteString : value null");
            return false;
        }
        Slog.m76i(TAG, "fileWriteString : " + str + "  value : " + str2);
        try {
            try {
                fileOutputStream = new FileOutputStream(new File(str));
            } catch (FileNotFoundException unused) {
                Slog.m72d(TAG, "fileWriteString : FileNotFoundException");
                return false;
            }
        } catch (IOException e2) {
            fileOutputStream = null;
            e = e2;
        }
        try {
            fileOutputStream.write(str2.getBytes());
            fileOutputStream.close();
            return true;
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            try {
                fileOutputStream.close();
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            return false;
        }
    }

    public boolean writeToFile(String str, long j, boolean z) {
        RandomAccessFile randomAccessFile;
        IOException e;
        if (!new File(str).exists()) {
            Slog.m72d(TAG, str + " is not found");
            return false;
        }
        try {
            try {
                randomAccessFile = new RandomAccessFile(new File(str), "rw");
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                return false;
            }
        } catch (IOException e3) {
            randomAccessFile = null;
            e = e3;
        }
        try {
            randomAccessFile.seek(j);
            randomAccessFile.write((z ? "1" : "0").getBytes());
            randomAccessFile.close();
            return true;
        } catch (IOException e4) {
            e = e4;
            e.printStackTrace();
            try {
                randomAccessFile.close();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            return false;
        }
    }

    public static void fileWriteInt(String str, int i) {
        Slog.m76i(TAG, "fileWriteInt : " + str + "  value : " + i);
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
            try {
                fileOutputStream2.write(Integer.toString(i).getBytes());
                fileOutputStream2.close();
            } catch (IOException e) {
                e = e;
                fileOutputStream = fileOutputStream2;
                e.printStackTrace();
                try {
                    fileOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } catch (FileNotFoundException unused) {
        } catch (IOException e3) {
            e = e3;
        }
    }

    public static void sendBroadcastToExplicitPackage(Context context, Intent intent, String str) {
        Slog.m72d(TAG, "sendBroadcastToExplicitPackage: " + intent + " -> " + str);
        Intent intent2 = (Intent) intent.clone();
        intent2.setPackage(str);
        context.sendBroadcastAsUser(intent2, UserHandle.ALL);
    }

    public static boolean isSupportedDailyBoard() {
        String[] split;
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DAILYBOARD");
        if (string == null || (split = string.split(",")) == null) {
            return false;
        }
        for (String str : split) {
            if (str.equalsIgnoreCase("TA")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFileSupported(String str) {
        if (new File(str).exists()) {
            return true;
        }
        Slog.m72d(TAG, str + " is not found");
        return false;
    }

    public final int getBatteryType() {
        int i;
        long readBatteryInfo = readBatteryInfo("/sys/class/power_supply/sec_auth/presence");
        long readBatteryInfo2 = readBatteryInfo("/sys/class/power_supply/sec_auth/batt_auth");
        String str = TAG;
        Slog.m72d(str, "[getBatteryType]presence:" + readBatteryInfo + " ,auth:" + readBatteryInfo2);
        boolean isFileSupported = isFileSupported("/sys/class/power_supply/sbp-fg/type");
        StringBuilder sb = new StringBuilder();
        sb.append("[getBatteryType]sbp_fg:");
        sb.append(isFileSupported);
        Slog.m72d(str, sb.toString());
        if (readBatteryInfo == 1 && readBatteryInfo2 == 1) {
            String readFromFile = readFromFile("/sys/class/power_supply/sec_auth/qr_code");
            String readFromFile2 = readFromFile("/efs/FactoryApp/HwParamBattQR");
            if (readFromFile != null) {
                this.mIsAuthQrEqualsEfs = readFromFile.equals(readFromFile2);
                Slog.m76i(str, "[getBatteryType]mIsAuthQrEqualsEfs:" + this.mIsAuthQrEqualsEfs);
                BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "getBatteryType QR", "mIsAuthQrEqualsEfs:" + this.mIsAuthQrEqualsEfs + " ,authQR:" + readFromFile + " ,efsQR:" + readFromFile2);
                i = 10;
            } else {
                i = 13;
            }
        } else if (readBatteryInfo == 0 && readBatteryInfo2 == 0) {
            i = 11;
        } else if (readBatteryInfo == 1 && readBatteryInfo2 == 0) {
            i = 12;
        } else if (isFileSupported) {
            String readFromFile3 = readFromFile("/sys/class/power_supply/sbp-fg/qr_code");
            String readFromFile4 = readFromFile("/efs/FactoryApp/HwParamBattQR");
            if (readFromFile3 != null) {
                this.mIsSbpFgQrEqualsEfs = readFromFile3.equals(readFromFile4);
                Slog.m76i(str, "[getBatteryType]SBP-FG mIsSbpFgQrEqualsEfs:" + this.mIsSbpFgQrEqualsEfs);
            } else {
                Slog.m76i(str, "[getBatteryType]SBP-FG QR read failed!!");
            }
            i = 20;
        } else {
            i = 0;
        }
        Slog.m76i(str, "[getBatteryType]type:" + i);
        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "BatteryType", "type:" + i);
        return i;
    }

    public final void syncBatteryInfoAuthEfs() {
        long j;
        String str = TAG;
        Slog.m76i(str, "[syncBatteryInfoAuthEfs]mIsAuthQrEqualsEfs:" + this.mIsAuthQrEqualsEfs);
        String readFromFile = readFromFile("/sys/class/power_supply/sec_auth/first_use_date");
        if (readFromFile != null && readFromFile.length() == 8 && readFromFile.startsWith("20")) {
            Slog.m76i(str, "[syncBatteryInfoAuthEfs]authFirstUseDate valid => sync to efs");
            saveBatteryInfo("/efs/FactoryApp/batt_beginning_date", readFromFile);
            this.mShouldCheckFirstUseDateRegularly = false;
        } else {
            Slog.m76i(str, "[syncBatteryInfoAuthEfs]authFirstUseDate invalid => Not sync to efs");
            this.mShouldCheckFirstUseDateRegularly = true;
        }
        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "syncBatteryInfoAuthEfs", "authFirstUseDate:" + readFromFile + " ,mShouldCheckFirstUseDateRegularly:" + this.mShouldCheckFirstUseDateRegularly);
        if (this.mIsAuthQrEqualsEfs) {
            long readBatteryUsageFromEfsLocked = readBatteryUsageFromEfsLocked("/efs/FactoryApp/batt_discharge_level");
            long readBatteryInfo = readBatteryInfo("/sys/class/power_supply/sec_auth/batt_discharge_level");
            if (readBatteryInfo == 16777215 || readBatteryInfo <= readBatteryUsageFromEfsLocked) {
                Slog.m72d(str, "[syncBatteryInfoAuthEfs]efsDischargeLevel is worse");
                j = readBatteryUsageFromEfsLocked;
            } else {
                Slog.m72d(str, "[syncBatteryInfoAuthEfs]authDischargeLevel is worse");
                j = readBatteryInfo;
            }
            saveBatteryInfo("/efs/FactoryApp/batt_discharge_level", j);
            saveBatteryInfo("/sys/class/power_supply/sec_auth/batt_discharge_level", j);
            if (FEATURE_FULL_BATTERY_CYCLE) {
                long readBatteryUsageFromEfsLocked2 = readBatteryUsageFromEfsLocked("/efs/FactoryApp/batt_full_status_usage");
                long readBatteryInfo2 = readBatteryInfo("/sys/class/power_supply/sec_auth/batt_full_status_usage");
                if (readBatteryInfo2 == 16777215 || readBatteryInfo2 <= readBatteryUsageFromEfsLocked2) {
                    Slog.m72d(str, "[syncBatteryInfoAuthEfs]efsFullStatusUsage is worse");
                } else {
                    Slog.m72d(str, "[syncBatteryInfoAuthEfs]authFullStatusUsage is worse");
                    readBatteryUsageFromEfsLocked2 = readBatteryInfo2;
                }
                saveBatteryInfo("/efs/FactoryApp/batt_full_status_usage", readBatteryUsageFromEfsLocked2);
                saveBatteryInfo("/sys/class/power_supply/sec_auth/batt_full_status_usage", readBatteryUsageFromEfsLocked2);
            }
            long readBatteryInfo3 = readBatteryInfo("/sys/class/power_supply/sec_auth/asoc");
            long readBatteryInfo4 = readBatteryInfo("/efs/FactoryApp/asoc");
            if (readBatteryInfo3 < 0 || readBatteryInfo3 > 100 || readBatteryInfo3 >= readBatteryInfo4) {
                Slog.m72d(str, "[syncBatteryInfoAuthEfs]efsAsoc is worse");
                readBatteryInfo3 = readBatteryInfo4;
            } else {
                Slog.m72d(str, "[syncBatteryInfoAuthEfs]authAsoc is worse");
            }
            saveBatteryInfo("/efs/FactoryApp/asoc", readBatteryInfo3);
            saveBatteryInfo("/sys/class/power_supply/sec_auth/asoc", readBatteryInfo3);
            return;
        }
        String readFromFile2 = readFromFile("/sys/class/power_supply/sec_auth/qr_code");
        if (readFromFile2 != null) {
            saveBatteryInfo("/efs/FactoryApp/HwParamBattQR", readFromFile2);
        } else {
            Slog.m74e(str, "[syncBatteryInfoAuthEfs]QR read fail");
            saveBatteryInfo("/efs/FactoryApp/HwParamBattQR", "");
        }
        long readBatteryInfo5 = readBatteryInfo("/sys/class/power_supply/sec_auth/batt_discharge_level");
        long j2 = 1;
        if (readBatteryInfo5 == 16777215 || readBatteryInfo5 < 0) {
            saveBatteryInfo("/sys/class/power_supply/sec_auth/batt_discharge_level", 1L);
            readBatteryInfo5 = 1;
        }
        saveBatteryInfo("/efs/FactoryApp/batt_discharge_level", readBatteryInfo5);
        long readBatteryInfo6 = readBatteryInfo("/sys/class/power_supply/sec_auth/batt_full_status_usage");
        if (readBatteryInfo6 == 16777215 || readBatteryInfo6 < 0) {
            saveBatteryInfo("/sys/class/power_supply/sec_auth/batt_full_status_usage", 1L);
        } else {
            j2 = readBatteryInfo6;
        }
        saveBatteryInfo("/efs/FactoryApp/batt_full_status_usage", j2);
        long readBatteryInfo7 = readBatteryInfo("/sys/class/power_supply/sec_auth/asoc");
        if (readBatteryInfo7 == 65535 || readBatteryInfo7 < 0) {
            readBatteryInfo7 = 100;
            saveBatteryInfo("/sys/class/power_supply/sec_auth/asoc", 100L);
        }
        saveBatteryInfo("/efs/FactoryApp/asoc", readBatteryInfo7);
    }

    public final void syncBatteryInfoSbpFgEfs() {
        String str = TAG;
        Slog.m76i(str, "[syncBatteryInfoSbpFgEfs]mIsSbpFgQrEqualsEfs:" + this.mIsSbpFgQrEqualsEfs);
        String readFromFile = readFromFile("/sys/class/power_supply/sbp-fg/first_use_date");
        if (readFromFile != null && readFromFile.length() == 8 && readFromFile.startsWith("20")) {
            Slog.m76i(str, "[syncBatteryInfoSbpFgEfs]sbpfgFirstUseDate valid => sync to efs");
            saveBatteryInfo("/efs/FactoryApp/batt_beginning_date", readFromFile);
            this.mShouldCheckFirstUseDateRegularly = false;
        } else {
            Slog.m76i(str, "[syncBatteryInfoSbpFgEfs]sbpfgFirstUseDate invalid => Not sync to efs");
            saveBatteryInfo("/efs/FactoryApp/batt_beginning_date", "FFFF");
            this.mShouldCheckFirstUseDateRegularly = true;
        }
        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "syncBatteryInfoSbpFgEfs", "sbpfgFirstUseDate:" + readFromFile + " ,mShouldCheckFirstUseDateRegularly:" + this.mShouldCheckFirstUseDateRegularly);
        if (this.mIsSbpFgQrEqualsEfs) {
            return;
        }
        String readFromFile2 = readFromFile("/sys/class/power_supply/sbp-fg/qr_code");
        if (readFromFile2 != null) {
            saveBatteryInfo("/efs/FactoryApp/HwParamBattQR", readFromFile2);
        } else {
            Slog.m74e(str, "[syncBatteryInfoSbpFgEfs]QR read fail");
            saveBatteryInfo("/efs/FactoryApp/HwParamBattQR", "");
        }
    }

    public final void initBatteryInfo() {
        this.mHandlerForBatteryInfoBackUp.post(new Runnable() { // from class: com.android.server.BatteryService.37
            @Override // java.lang.Runnable
            public void run() {
                long j;
                synchronized (BatteryService.this.mLockBatteryInfoBackUp) {
                    Slog.m72d(BatteryService.TAG, "!@[BatteryInfo] initBatteryInfo()");
                    if (BatteryService.this.mBatteryType == 20) {
                        j = BatteryService.this.readBatteryInfo("/sys/class/power_supply/sbp-fg/cycle");
                        long j2 = j * 100;
                        BatteryService.this.saveBatteryInfo("/efs/FactoryApp/batt_discharge_level", j2);
                        BatteryService.this.mSavedBatteryUsageForSbpFg = j2;
                    } else {
                        BatteryService batteryService = BatteryService.this;
                        batteryService.mSavedBatteryUsage = batteryService.readBatteryUsageFromEfsLocked("/efs/FactoryApp/batt_discharge_level");
                        j = -1;
                    }
                    boolean z = BatteryService.FEATURE_FULL_BATTERY_CYCLE;
                    if (z) {
                        BatteryService batteryService2 = BatteryService.this;
                        batteryService2.mSavedFullBatteryDuration = batteryService2.readBatteryUsageFromEfsLocked("/efs/FactoryApp/batt_full_status_usage");
                    }
                    if (BatteryService.FEATURE_SAVE_BATTERY_CYCLE) {
                        if (BatteryService.this.mBatteryType == 20) {
                            BatteryService.this.saveBatteryInfo("/sys/class/power_supply/battery/battery_cycle", j);
                        } else if (z) {
                            BatteryService.this.saveBatteryInfo("/sys/class/power_supply/battery/battery_cycle", (BatteryService.this.mSavedBatteryUsage / 100) + " " + (BatteryService.this.mSavedFullBatteryDuration / 720));
                        } else {
                            BatteryService batteryService3 = BatteryService.this;
                            batteryService3.saveBatteryInfo("/sys/class/power_supply/battery/battery_cycle", batteryService3.mSavedBatteryUsage / 100);
                        }
                    }
                    BatteryService batteryService4 = BatteryService.this;
                    batteryService4.mSavedBatteryMaxCurrent = batteryService4.readBatteryMaxCurrentFromEfsLocked();
                    BatteryService batteryService5 = BatteryService.this;
                    batteryService5.mSavedBatteryMaxTemp = batteryService5.readBatteryMaxTempFromEfsLocked();
                }
            }
        });
    }

    public final long readBatteryUsageFromEfsLocked(String str) {
        long readBatteryInfo = readBatteryInfo(str);
        if (readBatteryInfo > 0) {
            return readBatteryInfo;
        }
        saveBatteryInfo(str, 1L);
        return 1L;
    }

    public final void logFullBatteryDurationLocked(boolean z) {
        if (this.mHealthInfo.batteryLevel != 100) {
            if (this.mFullBatteryStartTime != -1) {
                this.mFullBatteryDuration = SystemClock.elapsedRealtime() - this.mFullBatteryStartTime;
                Slog.m72d(TAG, "logFullBatteryDurationLocked : save duration, mFullBatteryDuration=" + this.mFullBatteryDuration);
                final long j = this.mFullBatteryDuration;
                this.mFullBatteryDuration = 0L;
                this.mFullBatteryStartTime = -1L;
                this.mHandlerForBatteryInfoBackUp.post(new Runnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        BatteryService.this.lambda$logFullBatteryDurationLocked$2(j);
                    }
                });
                return;
            }
            return;
        }
        if (this.mFullBatteryStartTime == -1) {
            this.mFullBatteryStartTime = SystemClock.elapsedRealtime();
        }
        this.mFullBatteryDuration = SystemClock.elapsedRealtime() - this.mFullBatteryStartTime;
        String str = TAG;
        Slog.m72d(str, "logFullBatteryDurationLocked : mFullBatteryStartTime=" + this.mFullBatteryStartTime + " mFullBatteryDuration=" + this.mFullBatteryDuration);
        final long j2 = this.mFullBatteryDuration;
        if (j2 >= 600000 || z) {
            Slog.m72d(str, "logFullBatteryDurationLocked : save duration, mFullBatteryDuration=" + this.mFullBatteryDuration);
            this.mFullBatteryDuration = 0L;
            this.mFullBatteryStartTime = SystemClock.elapsedRealtime();
            this.mHandlerForBatteryInfoBackUp.post(new Runnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BatteryService.this.lambda$logFullBatteryDurationLocked$1(j2);
                }
            });
        }
    }

    /* renamed from: saveFullBatteryDuration, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final void lambda$logFullBatteryDurationLocked$2(long j) {
        synchronized (this.mLockBatteryInfoBackUp) {
            String str = TAG;
            Slog.m72d(str, "saveFullBatteryDuration : duration=" + j);
            if (this.mBattInfoManager.isDualAuth()) {
                if (this.mBattInfoManager.isAuthAvailable()) {
                    this.mBattInfoManager.fullStatusUsageData.addValueAndSave(j / 60000);
                }
            } else {
                long readBatteryUsageFromEfsLocked = readBatteryUsageFromEfsLocked("/efs/FactoryApp/batt_full_status_usage") + (j / 60000);
                this.mSavedFullBatteryDuration = readBatteryUsageFromEfsLocked;
                saveBatteryInfo("/efs/FactoryApp/batt_full_status_usage", readBatteryUsageFromEfsLocked);
                if (this.mBatteryType == 10) {
                    saveBatteryInfo("/sys/class/power_supply/sec_auth/batt_full_status_usage", this.mSavedFullBatteryDuration);
                }
                Slog.m72d(str, "saveFullBatteryDuration : mSavedFullBatteryDuration(min)=" + this.mSavedFullBatteryDuration);
                if (FEATURE_SAVE_BATTERY_CYCLE) {
                    if (this.mBattInfoManager.isDualAuth()) {
                        if (this.mBattInfoManager.isAuthAvailable()) {
                            this.mBattInfoManager.setCycle();
                        }
                    } else {
                        saveBatteryInfo("/sys/class/power_supply/battery/battery_cycle", (this.mSavedBatteryUsage / 100) + " " + (this.mSavedFullBatteryDuration / 720));
                    }
                }
            }
        }
    }

    public final long readBatteryMaxTempFromEfsLocked() {
        long readBatteryInfo = readBatteryInfo("/efs/FactoryApp/max_temp");
        if (readBatteryInfo >= 0) {
            return readBatteryInfo;
        }
        saveBatteryInfo("/efs/FactoryApp/max_temp", -1L);
        return -1L;
    }

    public final long readBatteryMaxCurrentFromEfsLocked() {
        long readBatteryInfo = readBatteryInfo("/efs/FactoryApp/max_current");
        if (readBatteryInfo >= 0) {
            return readBatteryInfo;
        }
        saveBatteryInfo("/efs/FactoryApp/max_current", -1L);
        return -1L;
    }

    public final long initializeSavedAsoc(long j) {
        long j2 = j < 0 ? -1L : 100L;
        Slog.m72d(TAG, "!@initializeSavedAsoc: " + j2);
        saveBatteryInfo("/efs/FactoryApp/asoc", j2);
        if (this.mBatteryType == 10) {
            saveBatteryInfo("/sys/class/power_supply/sec_auth/asoc", j2);
        }
        return j2;
    }

    public final long readBatteryAsocFromEfsLocked() {
        if (!isFileSupported("/efs/FactoryApp/asoc")) {
            Slog.m72d(TAG, "!@readBatteryAsocFromEfsLocked: not exist");
            return -1L;
        }
        return readBatteryInfo("/efs/FactoryApp/asoc");
    }

    public final long readBatteryInfo(String str) {
        if (str == null) {
            return -1L;
        }
        String readFromFile = readFromFile(str);
        if (readFromFile == null) {
            Slog.m72d(TAG, "!@[BatteryInfo] " + str + " : data is null.");
            return -1L;
        }
        try {
            return Long.parseLong(readFromFile);
        } catch (NumberFormatException unused) {
            Slog.m74e(TAG, "!@[BatteryInfo] " + str + " : data is \"" + readFromFile + "\"");
            return -1L;
        }
    }

    public final String readFromFile(String str) {
        return readFromFile(str, true);
    }

    public final String readFromFile(String str, boolean z) {
        String str2;
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2 = null;
        String str3 = null;
        try {
            randomAccessFile = new RandomAccessFile(new File(str), "r");
        } catch (IOException unused) {
            str2 = null;
        }
        try {
            str3 = randomAccessFile.readLine();
            randomAccessFile.close();
            if (z) {
                Slog.m72d(TAG, "!@[BatteryInfo] readFromFile " + str + ": " + str3);
            }
            if (!"/efs/FactoryApp/batt_discharge_level".equals(str) && !"/efs/FactoryApp/batt_full_status_usage".equals(str)) {
                return str3;
            }
            FileUtils.setPermissions(str, FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, 1000, 1007);
            return str3;
        } catch (IOException unused2) {
            str2 = str3;
            randomAccessFile2 = randomAccessFile;
            Slog.m74e(TAG, "!@[BatteryInfo] IOException in readFromFile:" + str);
            if (randomAccessFile2 != null) {
                try {
                    randomAccessFile2.close();
                } catch (Exception unused3) {
                    Slog.m74e(TAG, "!@[BatteryInfo] Exception in readFromFile" + str);
                }
            }
            return str2;
        }
    }

    public final int saveBatteryInfo(String str, long j) {
        return writeToFile(str, j);
    }

    public final int saveBatteryInfo(String str, String str2) {
        return writeToFile(str, str2);
    }

    public final int writeToFile(String str, long j) {
        return writeToFile(str, Long.toString(j));
    }

    public final int writeToFile(String str, String str2) {
        char c;
        RandomAccessFile randomAccessFile = null;
        try {
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(new File(str), "rw");
            try {
                randomAccessFile2.seek(0L);
                randomAccessFile2.writeBytes(str2 + System.getProperty("line.separator"));
                Slog.m72d(TAG, "!@[BatteryInfo] writeToFile " + str + ": " + str2);
                randomAccessFile2.close();
                switch (str.hashCode()) {
                    case -729583085:
                        if (str.equals("/efs/FactoryApp/batt_discharge_level")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 247185424:
                        if (str.equals("/efs/FactoryApp/batt_full_status_usage")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 911734546:
                        if (str.equals("/efs/FactoryApp/asoc")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1200499602:
                        if (str.equals("/efs/FactoryApp/HwParamBattQR")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1597920260:
                        if (str.equals("/efs/FactoryApp/batt_beginning_date")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                if (c == 0 || c == 1 || c == 2) {
                    FileUtils.setPermissions(str, FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, 1000, 1007);
                } else if (c == 3 || c == 4) {
                    FileUtils.setPermissions(str, FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, 1000, 1000);
                }
                return 0;
            } catch (IOException unused) {
                randomAccessFile = randomAccessFile2;
                Slog.m74e(TAG, "!@[BatteryInfo] IOException in writeToFile");
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (Exception unused2) {
                        Slog.m74e(TAG, "!@[BatteryInfo] Exception in writeToFile");
                    }
                }
                return -1;
            }
        } catch (IOException unused3) {
        }
    }

    public class Shell extends ShellCommand {
        public Shell() {
        }

        public int onCommand(String str) {
            return BatteryService.this.onShellCommand(this, str);
        }

        public void onHelp() {
            BatteryService.dumpHelp(getOutPrintWriter());
        }
    }

    public static void dumpHelp(PrintWriter printWriter) {
        printWriter.println("Battery service (battery) commands:");
        printWriter.println("  help");
        printWriter.println("    Print this help text.");
        printWriter.println("  get [-f] [ac|usb|wireless|dock|status|level|temp|present|counter|invalid]");
        printWriter.println("  set [-f] [ac|usb|wireless|dock|status|level|temp|present|counter|invalid] <value>");
        printWriter.println("    Force a battery property value, freezing battery state.");
        printWriter.println("    -f: force a battery change broadcast be sent, prints new sequence.");
        printWriter.println("  unplug [-f]");
        printWriter.println("    Force battery unplugged, freezing battery state.");
        printWriter.println("    -f: force a battery change broadcast be sent, prints new sequence.");
        printWriter.println("  reset [-f]");
        printWriter.println("    Unfreeze battery state, returning to current hardware values.");
        printWriter.println("    -f: force a battery change broadcast be sent, prints new sequence.");
        if (Build.IS_DEBUGGABLE) {
            printWriter.println("  suspend_input");
            printWriter.println("    Suspend charging even if plugged in. ");
        }
    }

    public int parseOptions(Shell shell) {
        int i = 0;
        while (true) {
            String nextOption = shell.getNextOption();
            if (nextOption == null) {
                return i;
            }
            if ("-f".equals(nextOption)) {
                i |= 1;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onShellCommand(Shell shell, String str) {
        char c;
        char c2;
        char c3;
        boolean z;
        if (str == null) {
            return shell.handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = shell.getOutPrintWriter();
        switch (str.hashCode()) {
            case -840325209:
                if (str.equals("unplug")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -541966841:
                if (str.equals("suspend_input")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -11780572:
                if (str.equals("sleeptime")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 102230:
                if (str.equals("get")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 113762:
                if (str.equals("set")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 108404047:
                if (str.equals("reset")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1544962441:
                if (str.equals("sleepstatus")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                int parseOptions = parseOptions(shell);
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                unplugBattery((parseOptions & 1) != 0, outPrintWriter);
                return 0;
            case 1:
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                suspendBatteryInput();
                return 0;
            case 2:
            case 6:
                String str2 = TAG_SS;
                Slog.m74e(str2, "[onShellCommand]cmd:" + str);
                StringBuilder sb = new StringBuilder();
                sb.append("[onShellCommand]Build.TYPE:");
                String str3 = Build.TYPE;
                sb.append(str3);
                Slog.m74e(str2, sb.toString());
                if ("user".equals(str3)) {
                    Slog.m74e(str2, "user build cannot use this cmd");
                    outPrintWriter.println("user build cannot use this cmd");
                    return 0;
                }
                if (this.mSleepChargingManager == null) {
                    Slog.m74e(str2, "Curretly, Not Adaptive Mode");
                    outPrintWriter.println("Curretly, Not Adaptive Mode");
                    return 0;
                }
                if ("sleeptime".equals(str)) {
                    String nextArg = shell.getNextArg();
                    Slog.m74e(str2, "sleeptime input weekType:" + nextArg);
                    String nextArg2 = shell.getNextArg();
                    Slog.m74e(str2, "sleeptime input confidence:" + nextArg2);
                    String nextArg3 = shell.getNextArg();
                    Slog.m74e(str2, "sleeptime input bedTime:" + nextArg3);
                    String nextArg4 = shell.getNextArg();
                    Slog.m74e(str2, "sleeptime input wakeupTime:" + nextArg4);
                    if (nextArg == null || nextArg.isEmpty() || nextArg2 == null || nextArg2.isEmpty() || nextArg3 == null || nextArg3.isEmpty() || nextArg4 == null || nextArg4.isEmpty()) {
                        Slog.m74e(str2, "Args Error");
                        outPrintWriter.println("Args Error");
                        return 0;
                    }
                    this.mSleepChargingManager.modifySleepPatternsForTest(nextArg, nextArg2, nextArg3, nextArg4);
                    return 0;
                }
                if (!"sleepstatus".equals(str)) {
                    return 0;
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append("[" + TAG + "]\n");
                sb2.append("mProtectBatteryMode:" + this.mProtectBatteryMode + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                sb2.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                sb2.append(this.mSleepChargingManager.getInfoAll(true));
                outPrintWriter.println(sb2.toString());
                return 0;
            case 3:
                String nextArg5 = shell.getNextArg();
                if (nextArg5 == null) {
                    outPrintWriter.println("No property specified");
                    return -1;
                }
                switch (nextArg5.hashCode()) {
                    case -1000044642:
                        if (nextArg5.equals("wireless")) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -892481550:
                        if (nextArg5.equals("status")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -318277445:
                        if (nextArg5.equals("present")) {
                            c2 = 2;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 3106:
                        if (nextArg5.equals("ac")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 116100:
                        if (nextArg5.equals("usb")) {
                            c2 = 4;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 3088947:
                        if (nextArg5.equals("dock")) {
                            c2 = 5;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 3556308:
                        if (nextArg5.equals("temp")) {
                            c2 = 6;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 102865796:
                        if (nextArg5.equals("level")) {
                            c2 = 7;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 957830652:
                        if (nextArg5.equals("counter")) {
                            c2 = '\b';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1959784951:
                        if (nextArg5.equals("invalid")) {
                            c2 = '\t';
                            break;
                        }
                        c2 = 65535;
                        break;
                    default:
                        c2 = 65535;
                        break;
                }
                switch (c2) {
                    case 0:
                        outPrintWriter.println(this.mHealthInfo.chargerWirelessOnline);
                        return 0;
                    case 1:
                        outPrintWriter.println(this.mHealthInfo.batteryStatus);
                        return 0;
                    case 2:
                        outPrintWriter.println(this.mHealthInfo.batteryPresent);
                        return 0;
                    case 3:
                        outPrintWriter.println(this.mHealthInfo.chargerAcOnline);
                        return 0;
                    case 4:
                        outPrintWriter.println(this.mHealthInfo.chargerUsbOnline);
                        return 0;
                    case 5:
                        outPrintWriter.println(this.mHealthInfo.chargerDockOnline);
                        return 0;
                    case 6:
                        outPrintWriter.println(this.mHealthInfo.batteryTemperatureTenthsCelsius);
                        return 0;
                    case 7:
                        outPrintWriter.println(this.mHealthInfo.batteryLevel);
                        return 0;
                    case '\b':
                        outPrintWriter.println(this.mHealthInfo.batteryChargeCounterUah);
                        return 0;
                    case '\t':
                        outPrintWriter.println(this.mInvalidCharger);
                        return 0;
                    default:
                        outPrintWriter.println("Unknown get option: " + nextArg5);
                        return 0;
                }
            case 4:
                int parseOptions2 = parseOptions(shell);
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                String nextArg6 = shell.getNextArg();
                if (nextArg6 == null) {
                    outPrintWriter.println("No property specified");
                    return -1;
                }
                String nextArg7 = shell.getNextArg();
                if (nextArg7 == null) {
                    outPrintWriter.println("No value specified");
                    return -1;
                }
                try {
                    if (!this.mUpdatesStopped) {
                        Utils.copySehV1Battery(this.mLastSehHealthInfo, this.mSehHealthInfo);
                    }
                    switch (nextArg6.hashCode()) {
                        case -1000044642:
                            if (nextArg6.equals("wireless")) {
                                c3 = 3;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case -892481550:
                            if (nextArg6.equals("status")) {
                                c3 = 5;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case -318277445:
                            if (nextArg6.equals("present")) {
                                c3 = 0;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 3106:
                            if (nextArg6.equals("ac")) {
                                c3 = 1;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 116100:
                            if (nextArg6.equals("usb")) {
                                c3 = 2;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 3088947:
                            if (nextArg6.equals("dock")) {
                                c3 = 4;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 3556308:
                            if (nextArg6.equals("temp")) {
                                c3 = '\b';
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 102865796:
                            if (nextArg6.equals("level")) {
                                c3 = 6;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 957830652:
                            if (nextArg6.equals("counter")) {
                                c3 = 7;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 1959784951:
                            if (nextArg6.equals("invalid")) {
                                c3 = '\t';
                                break;
                            }
                            c3 = 65535;
                            break;
                        default:
                            c3 = 65535;
                            break;
                    }
                    switch (c3) {
                        case 0:
                            this.mHealthInfo.batteryPresent = Integer.parseInt(nextArg7) != 0;
                            z = true;
                            break;
                        case 1:
                            this.mHealthInfo.chargerAcOnline = Integer.parseInt(nextArg7) != 0;
                            z = true;
                            break;
                        case 2:
                            this.mHealthInfo.chargerUsbOnline = Integer.parseInt(nextArg7) != 0;
                            z = true;
                            break;
                        case 3:
                            this.mHealthInfo.chargerWirelessOnline = Integer.parseInt(nextArg7) != 0;
                            z = true;
                            break;
                        case 4:
                            this.mHealthInfo.chargerDockOnline = Integer.parseInt(nextArg7) != 0;
                            z = true;
                            break;
                        case 5:
                            this.mHealthInfo.batteryStatus = Integer.parseInt(nextArg7);
                            z = true;
                            break;
                        case 6:
                            this.mHealthInfo.batteryLevel = Integer.parseInt(nextArg7);
                            z = true;
                            break;
                        case 7:
                            this.mHealthInfo.batteryChargeCounterUah = Integer.parseInt(nextArg7);
                            z = true;
                            break;
                        case '\b':
                            this.mHealthInfo.batteryTemperatureTenthsCelsius = Integer.parseInt(nextArg7);
                            z = true;
                            break;
                        case '\t':
                            this.mInvalidCharger = Integer.parseInt(nextArg7);
                            z = true;
                            break;
                        default:
                            outPrintWriter.println("Unknown set option: " + nextArg6);
                            z = false;
                            break;
                    }
                    if (!z) {
                        return 0;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mUpdatesStopped = true;
                        lambda$unplugBattery$5((parseOptions2 & 1) != 0, outPrintWriter);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (NumberFormatException unused) {
                    outPrintWriter.println("Bad value: " + nextArg7);
                    return -1;
                }
            case 5:
                int parseOptions3 = parseOptions(shell);
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                resetBattery((parseOptions3 & 1) != 0, outPrintWriter);
                return 0;
            default:
                return shell.handleDefaultCommands(str);
        }
    }

    public final void setChargerAcOnline(boolean z, final boolean z2) {
        if (!this.mUpdatesStopped) {
            Utils.copySehV1Battery(this.mLastSehHealthInfo, this.mSehHealthInfo);
        }
        this.mHealthInfo.chargerAcOnline = z;
        this.mUpdatesStopped = true;
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda8
            public final void runOrThrow() {
                BatteryService.this.lambda$setChargerAcOnline$3(z2);
            }
        });
    }

    public final void setBatteryLevel(int i, final boolean z) {
        if (!this.mUpdatesStopped) {
            Utils.copySehV1Battery(this.mLastSehHealthInfo, this.mSehHealthInfo);
        }
        this.mHealthInfo.batteryLevel = i;
        this.mUpdatesStopped = true;
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda7
            public final void runOrThrow() {
                BatteryService.this.lambda$setBatteryLevel$4(z);
            }
        });
    }

    public final void unplugBattery(final boolean z, final PrintWriter printWriter) {
        if (!this.mUpdatesStopped) {
            Utils.copySehV1Battery(this.mLastSehHealthInfo, this.mSehHealthInfo);
        }
        HealthInfo healthInfo = this.mHealthInfo;
        healthInfo.chargerAcOnline = false;
        healthInfo.chargerUsbOnline = false;
        healthInfo.chargerWirelessOnline = false;
        healthInfo.chargerDockOnline = false;
        this.mUpdatesStopped = true;
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda6
            public final void runOrThrow() {
                BatteryService.this.lambda$unplugBattery$5(z, printWriter);
            }
        });
    }

    public final void resetBattery(final boolean z, final PrintWriter printWriter) {
        if (this.mUpdatesStopped) {
            this.mUpdatesStopped = false;
            Utils.copySehV1Battery(this.mSehHealthInfo, this.mLastSehHealthInfo);
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda4
                public final void runOrThrow() {
                    BatteryService.this.lambda$resetBattery$6(z, printWriter);
                }
            });
        }
        if (this.mBatteryInputSuspended) {
            PowerProperties.battery_input_suspended(Boolean.FALSE);
            this.mBatteryInputSuspended = false;
        }
    }

    public final void suspendBatteryInput() {
        if (!Build.IS_DEBUGGABLE) {
            throw new SecurityException("battery suspend_input is only supported on debuggable builds");
        }
        PowerProperties.battery_input_suspended(Boolean.TRUE);
        this.mBatteryInputSuspended = true;
    }

    /* renamed from: processValuesLocked, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final void lambda$unplugBattery$5(boolean z, PrintWriter printWriter) {
        lambda$setChargerAcOnline$3(z);
        if (printWriter == null || !z) {
            return;
        }
        printWriter.println(this.mSequence);
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x0461, code lost:
    
        if ("-a".equals(r14[0]) == false) goto L52;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dumpInternal(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (this.mLock) {
            if (strArr != null) {
                if (strArr.length != 0 && !"-a".equals(strArr[0])) {
                    new Shell().exec(this.mBinderService, (FileDescriptor) null, fileDescriptor, (FileDescriptor) null, strArr, (ShellCallback) null, new ResultReceiver(null));
                }
            }
            printWriter.println("Current Battery Service state:");
            if (this.mUpdatesStopped) {
                printWriter.println("  (UPDATES STOPPED -- use 'reset' to restart)");
            }
            printWriter.println("  AC powered: " + this.mHealthInfo.chargerAcOnline);
            printWriter.println("  USB powered: " + this.mHealthInfo.chargerUsbOnline);
            printWriter.println("  Wireless powered: " + this.mHealthInfo.chargerWirelessOnline);
            printWriter.println("  Dock powered: " + this.mHealthInfo.chargerDockOnline);
            printWriter.println("  Max charging current: " + this.mHealthInfo.maxChargingCurrentMicroamps);
            printWriter.println("  Max charging voltage: " + this.mHealthInfo.maxChargingVoltageMicrovolts);
            printWriter.println("  Charge counter: " + this.mHealthInfo.batteryChargeCounterUah);
            printWriter.println("  status: " + this.mHealthInfo.batteryStatus);
            printWriter.println("  health: " + this.mHealthInfo.batteryHealth);
            printWriter.println("  present: " + this.mHealthInfo.batteryPresent);
            printWriter.println("  level: " + this.mHealthInfo.batteryLevel);
            printWriter.println("  scale: 100");
            printWriter.println("  voltage: " + this.mHealthInfo.batteryVoltageMillivolts);
            printWriter.println("  temperature: " + this.mHealthInfo.batteryTemperatureTenthsCelsius);
            printWriter.println("  technology: " + this.mHealthInfo.batteryTechnology);
            printWriter.println("  batteryMiscEvent: " + this.mSehHealthInfo.batteryEvent);
            printWriter.println("  batteryCurrentEvent: " + this.mSehHealthInfo.batteryCurrentEvent);
            printWriter.println("  mSecPlugTypeSummary: " + this.mSecPlugTypeSummary);
            printWriter.println("  LED Charging: " + this.mLedChargingSettingsEnable);
            printWriter.println("  LED Low Battery: " + this.mLedLowBatterySettingsEnable);
            if (this.mSehHealthInfo.batteryCurrentNow != Integer.MIN_VALUE) {
                printWriter.println("  current now: " + this.mSehHealthInfo.batteryCurrentNow);
            }
            if (this.mHealthInfo.batteryChargeCounterUah != Integer.MIN_VALUE) {
                printWriter.println("  charge counter: " + this.mHealthInfo.batteryChargeCounterUah);
            }
            printWriter.println("  Adaptive Fast Charging Settings: " + this.mAdaptiveFastChargingSettingsEnable);
            printWriter.println("  Super Fast Charging Settings: " + PowerManagerUtil.SEC_FEATURE_USE_SFC);
            printWriter.println("FEATURE_WIRELESS_FAST_CHARGER_CONTROL: " + FEATURE_WIRELESS_FAST_CHARGER_CONTROL);
            printWriter.println("  mWasUsedWirelessFastChargerPreviously: " + this.mWasUsedWirelessFastChargerPreviously);
            printWriter.println("  mWirelessFastChargingSettingsEnable: " + this.mWirelessFastChargingSettingsEnable);
            printWriter.println("LLB CAL: " + this.mRfCalDate);
            printWriter.println("LLB MAN: " + this.mManufactureDate);
            if (this.mCurrentCalendar != null) {
                printWriter.println("LLB CURRENT: YEAR" + this.mCurrentCalendar.get(1) + "M" + (this.mCurrentCalendar.get(2) + 1) + "D" + this.mCurrentCalendar.get(5));
                StringBuilder sb = new StringBuilder();
                sb.append("LLB DIFF: ");
                sb.append(this.mSavedDiffWeek);
                printWriter.println(sb.toString());
            }
            printWriter.println("  mSavedBatteryBeginningDate: " + this.mSavedBatteryBeginningDate);
            printWriter.println("SEC_FEATURE_BATTERY_FULL_CAPACITY: " + PowerManagerUtil.SEC_FEATURE_BATTERY_FULL_CAPACITY);
            printWriter.println("  mFullCapacityEnable: " + this.mFullCapacityEnable);
            printWriter.println("FEATURE_HICCUP_CONTROL: " + FEATURE_HICCUP_CONTROL);
            printWriter.println("FEATURE_SUPPORTED_DAILY_BOARD: " + FEATURE_SUPPORTED_DAILY_BOARD);
            printWriter.println("SEC_FEATURE_BATTERY_LIFE_EXTENDER: " + PowerManagerUtil.SEC_FEATURE_BATTERY_LIFE_EXTENDER);
            printWriter.println("SEC_FEATURE_USE_WIRELESS_POWER_SHARING: " + PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING);
            if (BattFeatures.SUPPORT_ECO_BATTERY) {
                printWriter.println(" mProtectBatteryMode: " + this.mProtectBatteryMode);
                printWriter.println(" mProtectionThreshold: " + this.mProtectionThreshold);
                printWriter.println(" mLtcHighThreshold: " + this.mLtcHighThreshold);
                printWriter.println(" mLtcHighSocDuration: " + this.mLtcHighSocDuration);
                printWriter.println(" mLtcReleaseThreshold: " + this.mLtcReleaseThreshold);
                if (this.mSleepChargingManager != null) {
                    printWriter.println("[Battery Adaptive Protect Mode]");
                    printWriter.println(this.mSleepChargingManager.getInfoAll(true));
                } else {
                    printWriter.println("[Not Battery Adaptive Protect Mode]");
                }
            }
        }
        synchronized (this.mLockBatteryInfoBackUp) {
            if (strArr != null) {
                if (strArr.length != 0) {
                }
            }
            printWriter.println("BatteryInfoBackUp");
            printWriter.println("  mSavedBatteryMaxTemp: " + this.mSavedBatteryMaxTemp);
            printWriter.println("  mSavedBatteryMaxCurrent: " + this.mSavedBatteryMaxCurrent);
            if (this.mBattInfoManager.isDualAuth()) {
                printWriter.println("  dischargeLevel: " + Arrays.toString(this.mBattInfoManager.dischargeLevelData.currentValues));
                printWriter.println("  fullStatusUsage: " + Arrays.toString(this.mBattInfoManager.fullStatusUsageData.currentValues));
                printWriter.println("  asoc: " + Arrays.toString(this.mBattInfoManager.asocData.currentValues));
            } else {
                if (this.mBatteryType == 20) {
                    printWriter.println("  mSavedBatteryUsage: " + this.mSavedBatteryUsageForSbpFg);
                } else {
                    printWriter.println("  mSavedBatteryUsage: " + this.mSavedBatteryUsage);
                }
                printWriter.println("  mSavedFullStatusDuration: " + this.mSavedFullBatteryDuration);
                printWriter.println("  mSavedBatteryAsoc: " + this.mSavedBatteryAsoc);
            }
            String readFromFile = readFromFile("/efs/FactoryApp/bsoh");
            if (readFromFile != null && !readFromFile.isEmpty()) {
                printWriter.println("  mSavedBatteryBsoh: " + readFromFile);
            }
            printWriter.println("  FEATURE_SAVE_BATTERY_CYCLE: " + FEATURE_SAVE_BATTERY_CYCLE);
        }
    }

    public final void dumpProto(FileDescriptor fileDescriptor) {
        int i;
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        synchronized (this.mLock) {
            protoOutputStream.write(1133871366145L, this.mUpdatesStopped);
            HealthInfo healthInfo = this.mHealthInfo;
            if (healthInfo.chargerAcOnline) {
                i = 1;
            } else if (healthInfo.chargerUsbOnline) {
                i = 2;
            } else if (healthInfo.chargerWirelessOnline) {
                i = 4;
            } else {
                i = healthInfo.chargerDockOnline ? 8 : 0;
            }
            protoOutputStream.write(1159641169922L, i);
            protoOutputStream.write(1120986464259L, this.mHealthInfo.maxChargingCurrentMicroamps);
            protoOutputStream.write(1120986464260L, this.mHealthInfo.maxChargingVoltageMicrovolts);
            protoOutputStream.write(1120986464261L, this.mHealthInfo.batteryChargeCounterUah);
            protoOutputStream.write(1159641169926L, this.mHealthInfo.batteryStatus);
            protoOutputStream.write(1159641169927L, this.mHealthInfo.batteryHealth);
            protoOutputStream.write(1133871366152L, this.mHealthInfo.batteryPresent);
            protoOutputStream.write(1120986464265L, this.mHealthInfo.batteryLevel);
            protoOutputStream.write(1120986464266L, 100);
            protoOutputStream.write(1120986464267L, this.mHealthInfo.batteryVoltageMillivolts);
            protoOutputStream.write(1120986464268L, this.mHealthInfo.batteryTemperatureTenthsCelsius);
            protoOutputStream.write(1138166333453L, this.mHealthInfo.batteryTechnology);
        }
        protoOutputStream.flush();
    }

    public static void traceBegin(String str) {
        Trace.traceBegin(524288L, str);
    }

    public static void traceEnd() {
        Trace.traceEnd(524288L);
    }

    public final class Led {
        public final int mBatteryFullARGB;
        public final int mBatteryLedOff;
        public final int mBatteryLedOn;
        public final LogicalLight mBatteryLight;
        public final int mBatteryLowARGB;
        public final int mBatteryLowBehavior;
        public final int mBatteryMediumARGB;
        public int mLedStatus = 0;

        public Led(Context context, LightsManager lightsManager) {
            this.mBatteryLight = lightsManager.getLight(3);
            this.mBatteryLowARGB = context.getResources().getInteger(R.integer.config_phonenumber_compare_min_match);
            this.mBatteryMediumARGB = context.getResources().getInteger(R.integer.config_pinnerHomePinBytes);
            this.mBatteryFullARGB = context.getResources().getInteger(R.integer.config_pauseRotationWhenUnfolding_hingeEventTimeout);
            this.mBatteryLedOn = context.getResources().getInteger(R.integer.config_pdp_reject_retry_delay_ms);
            this.mBatteryLedOff = context.getResources().getInteger(R.integer.config_pauseRotationWhenUnfolding_maxHingeAngle);
            BatteryService.this.mBatteryNearlyFullLevel = context.getResources().getInteger(R.integer.config_pinnerWebviewPinBytes);
            this.mBatteryLowBehavior = context.getResources().getInteger(R.integer.config_pictureInPictureMaxNumberOfActions);
        }

        public void updateLightsLocked() {
            if (this.mBatteryLight == null || !BatteryService.this.mBootCompleted) {
                return;
            }
            int i = BatteryService.this.mHealthInfo.batteryLevel;
            int i2 = BatteryService.this.mHealthInfo.batteryStatus;
            int i3 = BatteryService.this.mHealthInfo.batteryHealth;
            if (4 == i2 && ((3 == i3 || 6 == i3 || 7 == i3 || 8 == i3) && BatteryService.this.mLedChargingSettingsEnable)) {
                if (11 != this.mLedStatus) {
                    this.mBatteryLight.setFlashing(0, 11, 0, 0);
                    this.mLedStatus = 11;
                    Slog.m72d(BatteryService.TAG, "turn on LED for not charging");
                    return;
                }
                Slog.m72d(BatteryService.TAG, "stay LED for not charging");
                return;
            }
            if (2 == i2 && !BatteryService.this.mScreenOn && BatteryService.this.mLedChargingSettingsEnable) {
                if (10 != this.mLedStatus) {
                    this.mBatteryLight.setFlashing(0, 10, 0, 0);
                    this.mLedStatus = 10;
                    Slog.m72d(BatteryService.TAG, "turn on LED for charging");
                    return;
                }
                Slog.m72d(BatteryService.TAG, "stay LED for charging");
                return;
            }
            if (5 == i2 && !BatteryService.this.mScreenOn && BatteryService.this.mLedChargingSettingsEnable) {
                if (14 != this.mLedStatus) {
                    this.mBatteryLight.setFlashing(0, 14, 0, 0);
                    this.mLedStatus = 14;
                    Slog.m72d(BatteryService.TAG, "turn on LED for fully charged");
                    return;
                }
                Slog.m72d(BatteryService.TAG, "stay LED for fully charged");
                return;
            }
            if (i <= BatteryService.this.mLowBatteryWarningLevel && !BatteryService.this.mScreenOn && BatteryService.this.mLedLowBatterySettingsEnable) {
                if (13 != this.mLedStatus) {
                    this.mBatteryLight.setFlashing(0, 13, 0, 0);
                    this.mLedStatus = 13;
                    Slog.m72d(BatteryService.TAG, "turn on LED for low battery");
                    return;
                }
                Slog.m72d(BatteryService.TAG, "stay LED for low battery");
                return;
            }
            if (this.mLedStatus != 0) {
                this.mBatteryLight.turnOff();
                this.mLedStatus = 0;
                Slog.m72d(BatteryService.TAG, "turn off LED");
            }
        }
    }

    public final class BinderService extends Binder {
        public BinderService() {
        }

        @Override // android.os.Binder
        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(BatteryService.this.mContext, BatteryService.TAG, printWriter)) {
                if (strArr.length > 0 && "--proto".equals(strArr[0])) {
                    BatteryService.this.dumpProto(fileDescriptor);
                } else {
                    BatteryService.this.dumpInternal(fileDescriptor, printWriter, strArr);
                }
            }
        }

        public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            BatteryService.this.new Shell().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    public final class BatteryPropertiesRegistrar extends IBatteryPropertiesRegistrar.Stub {
        public BatteryPropertiesRegistrar() {
        }

        public int getProperty(int i, BatteryProperty batteryProperty) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    BatteryService.this.mContext.enforceCallingPermission("android.permission.BATTERY_STATS", null);
                    break;
            }
            return BatteryService.this.mHealthServiceWrapper.getProperty(i, batteryProperty);
        }

        public void scheduleUpdate() {
            BatteryService.this.mHealthServiceWrapper.scheduleUpdate();
        }
    }

    public final class LocalService extends BatteryManagerInternal {
        public LocalService() {
        }

        public boolean isPowered(int i) {
            boolean isPoweredLocked;
            synchronized (BatteryService.this.mLock) {
                isPoweredLocked = BatteryService.this.isPoweredLocked(i);
            }
            return isPoweredLocked;
        }

        public int getPlugType() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mPlugType;
            }
            return i;
        }

        public int getBatteryLevel() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mHealthInfo.batteryLevel;
            }
            return i;
        }

        public int getBatteryChargeCounter() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mHealthInfo.batteryChargeCounterUah;
            }
            return i;
        }

        public int getBatteryFullCharge() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mHealthInfo.batteryFullChargeUah;
            }
            return i;
        }

        public int getBatteryHealth() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mHealthInfo.batteryHealth;
            }
            return i;
        }

        public boolean getBatteryLevelLow() {
            boolean z;
            synchronized (BatteryService.this.mLock) {
                z = BatteryService.this.mBatteryLevelLow;
            }
            return z;
        }

        public int getInvalidCharger() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mInvalidCharger;
            }
            return i;
        }

        public void setChargerAcOnline(boolean z, boolean z2) {
            BatteryService.this.setChargerAcOnline(z, z2);
        }

        public void setBatteryLevel(int i, boolean z) {
            BatteryService.this.setBatteryLevel(i, z);
        }

        public void unplugBattery(boolean z) {
            BatteryService.this.unplugBattery(z, null);
        }

        public void resetBattery(boolean z) {
            BatteryService.this.resetBattery(z, null);
        }

        public void suspendBatteryInput() {
            BatteryService.this.suspendBatteryInput();
        }

        public boolean getBatteryLevelCritical() {
            boolean z;
            synchronized (BatteryService.this.mLock) {
                z = BatteryService.this.mBatteryLevelCritical;
            }
            return z;
        }

        public void setWirelessPowerSharingExternelEvent(int i, int i2) {
            Slog.m76i(BatteryService.TAG, "setWirelessPowerSharingExternelEvent packageNum: " + i + " value: " + i2);
            BatteryService.this.setWirelessPowerSharingExternelEventInternal(i, i2);
        }

        public int getBatteryOnline() {
            return BatteryService.this.mSehHealthInfo.batteryOnline;
        }

        public int getBatteryLevelRaw() {
            return BattUtils.readNodeAsInt("/sys/class/power_supply/battery/batt_read_raw_soc");
        }
    }
}
